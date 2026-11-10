package com.starpilot.starview;

import java.util.Map;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: GxTuning.kt */
final class GxTuningSample {
    private final double ts;
    private final Map<String, Double> v;

    public GxTuningSample(double ts, Map<String, Double> v) {
        Intrinsics.checkNotNullParameter(v, "v");
        this.ts = ts;
        this.v = v;
    }

    public final double getTs() {
        return this.ts;
    }

    public final Map<String, Double> getV() {
        return this.v;
    }
}
