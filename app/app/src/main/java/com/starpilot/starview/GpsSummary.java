package com.starpilot.starview;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: GpsSummary.kt */
public final class GpsSummary {
    public static final Companion Companion = new Companion(null);
    public static final long STALE_NS = 5000000000L;
    private final float accuracyM;
    private final float avgCno;
    private final List<GnssSat> bars;
    private final float bearing;
    private final int inUse;
    private final int inView;
    private final List<GnssSat> sky;
    private final float speed;
    private final String state;
    private final long unixMs;

    public GpsSummary(String state, float accuracyM, int inView, int inUse, float avgCno, List<GnssSat> bars, List<GnssSat> sky, float speed, float bearing, long unixMs) {
        Intrinsics.checkNotNullParameter(state, "state");
        Intrinsics.checkNotNullParameter(bars, "bars");
        Intrinsics.checkNotNullParameter(sky, "sky");
        this.state = state;
        this.accuracyM = accuracyM;
        this.inView = inView;
        this.inUse = inUse;
        this.avgCno = avgCno;
        this.bars = bars;
        this.sky = sky;
        this.speed = speed;
        this.bearing = bearing;
        this.unixMs = unixMs;
    }

    public final float getAccuracyM() {
        return this.accuracyM;
    }

    public final float getAvgCno() {
        return this.avgCno;
    }

    public final List<GnssSat> getBars() {
        return this.bars;
    }

    public final int getInUse() {
        return this.inUse;
    }

    public final int getInView() {
        return this.inView;
    }

    public final String getState() {
        return this.state;
    }

    /* compiled from: GpsSummary.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* recovered from bytecode (the decompiled version cast fixes to collections: crashed 2.84.2) */
        public final GpsSummary of(Collection<GpsFixInfo> fixes, List<GnssSat> sats, long satsAtNs, boolean on, long nowNs) {
            Intrinsics.checkNotNullParameter(fixes, "fixes");
            Intrinsics.checkNotNullParameter(sats, "sats");
            ArrayList<GpsFixInfo> recent = new ArrayList<>();
            for (GpsFixInfo f : fixes) {
                if (nowNs - f.getAtNs() < 5000000000L) {
                    recent.add(f);
                }
            }
            GpsFixInfo fix = null;
            for (GpsFixInfo f : recent) {
                if (Intrinsics.areEqual(f.getService(), "gpsLocationExternal") && Intrinsics.areEqual(f.getSource(), "ublox")
                        && (fix == null || fix.getAtNs() < f.getAtNs())) {
                    fix = f;
                }
            }
            if (fix == null) {
                for (GpsFixInfo f : recent) {
                    if (Intrinsics.areEqual(f.getService(), "gpsLocation") && (fix == null || fix.getAtNs() < f.getAtNs())) {
                        fix = f;
                    }
                }
            }
            List<GnssSat> live;
            if (nowNs - satsAtNs < 5000000000L) {
                live = new ArrayList<>();
                for (GnssSat it : sats) {
                    if (it.getCno() > 0) {
                        live.add(it);
                    }
                }
            } else {
                live = CollectionsKt.emptyList();
            }
            boolean hasFix = fix != null && fix.getHasFix();
            String state;
            if (hasFix) {
                state = "fix";
            } else if (fix != null || !live.isEmpty()) {
                state = "searching";
            } else if (!on) {
                state = "off";
            } else {
                state = "waiting";
            }
            float acc = hasFix ? fix.getHAcc() : Float.NaN;
            int inView = live.size();
            int inUse;
            if (!live.isEmpty()) {
                inUse = 0;
                for (GnssSat it : live) {
                    if (it.getUsed()) {
                        inUse++;
                        if (inUse < 0) {
                            CollectionsKt.throwCountOverflow();
                        }
                    }
                }
            } else {
                inUse = hasFix ? fix.getSats() : 0;
            }
            float avgCno;
            if (!live.isEmpty()) {
                ArrayList<Integer> cnos = new ArrayList<>(live.size());
                for (GnssSat it : live) {
                    cnos.add(Integer.valueOf(it.getCno()));
                }
                avgCno = (float) CollectionsKt.averageOfInt(cnos);
            } else {
                avgCno = Float.NaN;
            }
            List<GnssSat> bars = CollectionsKt.sortedWith(live, new Comparator<GnssSat>() {
                @Override
                public final int compare(GnssSat a, GnssSat b) {
                    int c = ComparisonsKt.compareValues(Boolean.valueOf(b.getUsed()), Boolean.valueOf(a.getUsed()));
                    return c != 0 ? c : ComparisonsKt.compareValues(Integer.valueOf(b.getCno()), Integer.valueOf(a.getCno()));
                }
            });
            ArrayList<GnssSat> sky = new ArrayList<>();
            for (GnssSat it : live) {
                int el = it.getElevation();
                int az = it.getAzimuth();
                if (el >= 0 && el < 91 && az >= 0 && az < 361) {
                    sky.add(it);
                }
            }
            float speed = hasFix ? fix.getSpeed() : Float.NaN;
            float bearing = Float.NaN;
            if (hasFix && fix.getSpeed() > 1.0f) {
                float b2 = fix.getBearing();
                if (!Float.isInfinite(b2) && !Float.isNaN(b2)) {
                    bearing = fix.getBearing();
                }
            }
            long unixMs = hasFix ? fix.getUnixMs() : 0L;
            return new GpsSummary(state, acc, inView, inUse, avgCno, bars, sky, speed, bearing, unixMs);
        }
    }

    public final float getBearing() {
        return this.bearing;
    }

    public final List<GnssSat> getSky() {
        return this.sky;
    }

    public final float getSpeed() {
        return this.speed;
    }

    public final long getUnixMs() {
        return this.unixMs;
    }
}
