package com.starpilot.starview;

import kotlin.ranges.RangesKt;

/* compiled from: PathPulse.kt */
public final class PathPulse {
    public static final float COAST_ACCEL = 0.25f;
    public static final float DIM = 0.35f;
    public static final float FULL_ACCEL = 1.5f;
    public static final PathPulse INSTANCE = new PathPulse();
    public static final double PERIOD_M = 6.0d;
    public static final double SPEED_M_S = 8.0d;

    private PathPulse() {
    }

    public final float broken(float accel) {
        return RangesKt.coerceIn((Math.abs(accel) - 0.25f) / 1.25f, 0.0f, 1.0f);
    }

    public final double advance(double phase, float accel, float broken, float dt) {
        return ((((Math.signum(accel) * 8.0d) * broken) * dt) + phase) % 6000.0d;
    }

    public final float alpha(float x, double phase, float broken) {
        if (broken <= 0.0f) {
            return 1.0f;
        }
        double c = (Math.cos(((x + phase) * 6.283185307179586d) / 6.0d) * 0.5d) + 0.5d;
        float band = (float) (c * c * c);
        float dim = 1.0f - (0.65f * broken);
        return ((1.0f - dim) * band) + dim;
    }
}
