package com.starpilot.starview;

import com.starpilot.starview.Detector;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: Detector.kt */
public final class Detector$track$Pair {
    private final float cost;
    private final Detector.Det d;
    private final Detector.Track tr;

    public Detector$track$Pair(Detector.Track tr, Detector.Det d, float cost) {
        Intrinsics.checkNotNullParameter(tr, "tr");
        Intrinsics.checkNotNullParameter(d, "d");
        this.tr = tr;
        this.d = d;
        this.cost = cost;
    }

    public final float getCost() {
        return this.cost;
    }

    public final Detector.Det getD() {
        return this.d;
    }

    public final Detector.Track getTr() {
        return this.tr;
    }
}
