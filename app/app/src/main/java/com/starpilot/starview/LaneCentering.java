package com.starpilot.starview;

import kotlin.jvm.internal.Intrinsics;
import org.capnproto.SlcPeek;

/* compiled from: LaneCentering.kt */
public final class LaneCentering {
    private static final float EPS = 1.0E-6f;
    public static final LaneCentering INSTANCE = new LaneCentering();
    private static final long MAX_AGE_NS = 400000000;
    private static final float MAX_CORRECTION = 0.01f;

    private LaneCentering() {
    }

    public static /* synthetic */ int direction$default(LaneCentering laneCentering, Telemetry telemetry, long j, int i, Object obj) {
        if ((i & 2) != 0) {
            j = System.nanoTime();
        }
        return laneCentering.direction(telemetry, j);
    }

    public final int direction(Telemetry t, long nowNs) {
        Intrinsics.checkNotNullParameter(t, "t");
        SlcPeek.LaneCentering lc = t.getLc();
        if (lc != null && nowNs - t.getLcAtNs() <= 400000000) {
            return direction(lc.version, lc.lateralActive, t.getLatActive(), lc.requested, lc.applied);
        }
        return 0;
    }

    public final int direction(int version, boolean lateralActive, boolean carLatActive, float requested, float applied) {
        if (version != 1 || !lateralActive || !carLatActive) {
            return 0;
        }
        if ((Float.isInfinite(requested) || Float.isNaN(requested)) ? false : true) {
            if (!((Float.isInfinite(applied) || Float.isNaN(applied)) ? false : true) || Math.abs(requested) > 0.01f || Math.abs(applied) > Math.abs(requested) + 1.0E-8f || requested * applied < 0.0f) {
                return 0;
            }
            if (applied > 1.0E-6f) {
                return 1;
            }
            return applied < -1.0E-6f ? -1 : 0;
        }
        return 0;
    }
}
