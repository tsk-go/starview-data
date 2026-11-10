package com.starpilot.starview;

import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

/* compiled from: RtkCore.kt */
public final class RtkPosition {
    private static final double AHEAD_MAX_S = 2.0d;
    public static final double MAX_ACC_M = 10.0d;
    public static final long MAX_AGE_MS = 2500;
    private static volatile long atMs;
    private static volatile long fastMs;
    public static final RtkPosition INSTANCE = new RtkPosition();
    private static volatile double lat = Double.NaN;
    private static volatile double lon = Double.NaN;
    private static volatile double heading = Double.NaN;
    private static volatile double accM = Double.NaN;
    private static volatile String kind = "none";

    private RtkPosition() {
    }

    public final double getHeading() {
        return heading;
    }

    public final void reset() {
        lat = Double.NaN;
        lon = Double.NaN;
        atMs = 0L;
        heading = Double.NaN;
        fastMs = 0L;
    }

    public final double getAccM() {
        return accM;
    }

    public final String getKind() {
        return kind;
    }

    public final boolean fast(long nowMs) {
        return fastMs > 0 && nowMs - fastMs < 600;
    }

    public final void feedFast(double lat2, double lon2, double accM2, double headingDeg, double speedMs, String kind2, long ageMs, long nowMs) {
        Intrinsics.checkNotNullParameter(kind2, "kind");
        if ((Double.isInfinite(lat2) || Double.isNaN(lat2)) ? false : true) {
            if ((Double.isInfinite(lon2) || Double.isNaN(lon2)) ? false : true) {
                if (!((Double.isInfinite(accM2) || Double.isNaN(accM2)) ? false : true) || accM2 > 10.0d) {
                    return;
                }
                long at = nowMs - RangesKt.coerceIn(ageMs, 0L, 1000L);
                lat = lat2;
                lon = lon2;
                atMs = at;
                fastMs = nowMs;
                accM = accM2;
                kind = kind2;
                if (!((Double.isInfinite(speedMs) || Double.isNaN(speedMs)) ? false : true) || speedMs <= 1.5d) {
                    return;
                }
                if ((Double.isInfinite(headingDeg) || Double.isNaN(headingDeg)) ? false : true) {
                    double d = 360;
                    heading = ((headingDeg % d) + d) % d;
                }
            }
        }
    }

    public final void feed(RtkStatus s, long nowMs) {
        if (s == null || !s.getHasFix()) {
            return;
        }
        double hAccM = s.getHAccM();
        if (!((Double.isInfinite(hAccM) || Double.isNaN(hAccM)) ? false : true) || s.getHAccM() > 10.0d || fast(nowMs)) {
            return;
        }
        lat = s.getLat();
        lon = s.getLon();
        atMs = nowMs;
        double speedMs = s.getSpeedMs();
        if (!((Double.isInfinite(speedMs) || Double.isNaN(speedMs)) ? false : true) || s.getSpeedMs() <= 1.5d) {
            return;
        }
        double headingDeg = s.getHeadingDeg();
        if ((Double.isInfinite(headingDeg) || Double.isNaN(headingDeg)) ? false : true) {
            double d = 360;
            heading = ((s.getHeadingDeg() % d) + d) % d;
        }
    }

    public final double[] now(long nowMs, double speedMs) {
        long a = atMs;
        if (a == 0 || nowMs - a > 2500) {
            return null;
        }
        double d = lat;
        if (!((Double.isInfinite(d) || Double.isNaN(d)) ? false : true)) {
            return null;
        }
        double dt = RangesKt.coerceIn((nowMs - a) / 1000.0d, 0.0d, 2.0d);
        double h = heading;
        if ((Double.isInfinite(h) || Double.isNaN(h)) ? false : true) {
            if (((Double.isInfinite(speedMs) || Double.isNaN(speedMs)) ? false : true) && speedMs > 0.3d) {
                double d2 = speedMs * dt;
                double r = Math.toRadians(h);
                double dLat = (Math.cos(r) * d2) / 111195.0d;
                double dLon = (Math.sin(r) * d2) / (Math.cos(Math.toRadians(lat)) * 111195.0d);
                return new double[]{lat + dLat, lon + dLon};
            }
        }
        return new double[]{lat, lon};
    }
}
