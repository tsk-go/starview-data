package com.starpilot.starview;

import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: IdeasSense.kt */
public final class ShadowWatch {
    public static final ShadowWatch INSTANCE = new ShadowWatch();
    private static float lastCx = Float.NaN;
    private static volatile boolean nearCorner;
    private static float[] prev;
    private static int steps;
    private static volatile boolean wanted;

    private ShadowWatch() {
    }

    public final boolean getWanted() {
        return wanted;
    }

    public final void setWanted(boolean z) {
        wanted = z;
    }

    public final void feed(float[] grid, int w, boolean people, long nowMs) {
        Intrinsics.checkNotNullParameter(grid, "grid");
        float mean = (float) ArraysKt.average(grid);
        int i = 0;
        if (mean >= 95.0f && !people) {
            float sx = 0.0f;
            int n = 0;
            int length = grid.length / w;
            for (int j = 0; j < length; j++) {
                int i2 = 0;
                while (i2 < w) {
                    if (!(i2 < (w * 3) / 4 && w / 4 <= i2) && grid[(j * w) + i2] < 0.45f * mean) {
                        sx += i2;
                        n++;
                    }
                    i2++;
                }
            }
            float[] p = prev;
            prev = grid;
            if (n >= 3 && n <= 40) {
                if (p != null) {
                    float cx = sx / n;
                    if (!Float.isNaN(lastCx)) {
                        float abs = Math.abs(cx - lastCx);
                        if (0.3f <= abs && abs <= 3.0f) {
                            i = steps + 1;
                        }
                    }
                    steps = i;
                    lastCx = cx;
                    if (steps < 4 || !nearCorner) {
                        return;
                    }
                    Ideas.INSTANCE.getSense().setShadowAtMs(nowMs);
                    return;
                }
            }
            lastCx = Float.NaN;
            steps = 0;
            return;
        }
        prev = grid;
        steps = 0;
    }

    public final boolean getNearCorner() {
        return nearCorner;
    }

    public final void setNearCorner(boolean z) {
        nearCorner = z;
    }
}
