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

        /* JADX WARN: Removed duplicated region for block: B:102:0x022d  */
        /* JADX WARN: Removed duplicated region for block: B:111:0x029b  */
        /* JADX WARN: Removed duplicated region for block: B:125:0x02c7  */
        /* JADX WARN: Removed duplicated region for block: B:128:0x02ca A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:135:0x02d3  */
        /* JADX WARN: Removed duplicated region for block: B:137:0x02e1  */
        /* JADX WARN: Removed duplicated region for block: B:147:0x030e  */
        /* JADX WARN: Removed duplicated region for block: B:151:0x0316  */
        /* JADX WARN: Removed duplicated region for block: B:154:0x02dd  */
        /* JADX WARN: Removed duplicated region for block: B:155:0x026d  */
        /* JADX WARN: Removed duplicated region for block: B:171:0x0216  */
        /* JADX WARN: Removed duplicated region for block: B:174:0x01cf  */
        /* JADX WARN: Removed duplicated region for block: B:91:0x01c5  */
        /* JADX WARN: Removed duplicated region for block: B:94:0x01de  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final GpsSummary of(Collection<GpsFixInfo> fixes, List<GnssSat> sats, long satsAtNs, boolean started, long nowNs) {
            Object maxElem$iv;
            List emptyList;
            boolean z;
            boolean has;
            float f;
            int i;
            float f2;
            float f3;
            float f4;
            long j;
            boolean z2;
            int count$iv;
            Object maxElem$iv2;
            Intrinsics.checkNotNullParameter(fixes, "fixes");
            Intrinsics.checkNotNullParameter(sats, "sats");
            Collection<GpsFixInfo> $this$filter$iv = fixes;
            Collection destination$iv$iv = new ArrayList();
            for (Object element$iv$iv : $this$filter$iv) {
                GpsFixInfo it = (GpsFixInfo) element$iv$iv;
                if (nowNs - it.getAtNs() < 5000000000L) {
                    destination$iv$iv.add(element$iv$iv);
                }
            }
            Iterable fresh = (List) destination$iv$iv;
            Iterable $this$filter$iv2 = fresh;
            Collection destination$iv$iv2 = new ArrayList();
            for (Object element$iv$iv2 : $this$filter$iv2) {
                GpsFixInfo it2 = (GpsFixInfo) element$iv$iv2;
                if (Intrinsics.areEqual(it2.getService(), "gpsLocationExternal") && Intrinsics.areEqual(it2.getSource(), "ublox")) {
                    destination$iv$iv2.add(element$iv$iv2);
                }
            }
            Iterable $this$maxByOrNull$iv = (List) destination$iv$iv2;
            Iterator iterator$iv = $this$maxByOrNull$iv.iterator();
            if (iterator$iv.hasNext()) {
                maxElem$iv = iterator$iv.next();
                if (iterator$iv.hasNext()) {
                    GpsFixInfo it3 = (GpsFixInfo) maxElem$iv;
                    long maxValue$iv = it3.getAtNs();
                    do {
                        Object e$iv = iterator$iv.next();
                        GpsFixInfo it4 = (GpsFixInfo) e$iv;
                        long v$iv = it4.getAtNs();
                        if (maxValue$iv < v$iv) {
                            maxElem$iv = e$iv;
                            maxValue$iv = v$iv;
                        }
                    } while (iterator$iv.hasNext());
                }
            } else {
                maxElem$iv = null;
            }
            GpsFixInfo gpsFixInfo = (GpsFixInfo) maxElem$iv;
            if (gpsFixInfo == null) {
                Iterable $this$filter$iv3 = fresh;
                Collection destination$iv$iv3 = new ArrayList();
                for (Object element$iv$iv3 : $this$filter$iv3) {
                    GpsFixInfo it5 = (GpsFixInfo) element$iv$iv3;
                    if (Intrinsics.areEqual(it5.getService(), "gpsLocation")) {
                        destination$iv$iv3.add(element$iv$iv3);
                    }
                }
                Iterable $this$maxByOrNull$iv2 = (List) destination$iv$iv3;
                Iterator iterator$iv2 = $this$maxByOrNull$iv2.iterator();
                if (iterator$iv2.hasNext()) {
                    maxElem$iv2 = iterator$iv2.next();
                    if (iterator$iv2.hasNext()) {
                        GpsFixInfo it6 = (GpsFixInfo) maxElem$iv2;
                        long maxValue$iv2 = it6.getAtNs();
                        do {
                            Object e$iv2 = iterator$iv2.next();
                            GpsFixInfo it7 = (GpsFixInfo) e$iv2;
                            long v$iv2 = it7.getAtNs();
                            if (maxValue$iv2 < v$iv2) {
                                maxElem$iv2 = e$iv2;
                                maxValue$iv2 = v$iv2;
                            }
                        } while (iterator$iv2.hasNext());
                    }
                } else {
                    maxElem$iv2 = null;
                }
                gpsFixInfo = (GpsFixInfo) maxElem$iv2;
            }
            GpsFixInfo fix = gpsFixInfo;
            if (nowNs - satsAtNs < 5000000000L) {
                List<GnssSat> $this$filter$iv4 = sats;
                Collection destination$iv$iv4 = new ArrayList();
                for (Object element$iv$iv4 : $this$filter$iv4) {
                    GnssSat it8 = (GnssSat) element$iv$iv4;
                    if (it8.getCno() > 0) {
                        destination$iv$iv4.add(element$iv$iv4);
                    }
                }
                emptyList = (List) destination$iv$iv4;
            } else {
                emptyList = CollectionsKt.emptyList();
            }
            List live = emptyList;
            String state = fix != null && fix.getHasFix() ? "fix" : (fix == null && live.isEmpty()) ? !started ? "off" : "waiting" : "searching";
            if (fix != null && fix.getHasFix()) {
                z = true;
                has = z;
                if (has) {
                    f = Float.NaN;
                } else {
                    Intrinsics.checkNotNull(fix);
                    f = fix.getHAcc();
                }
                int size = live.size();
                if (live.isEmpty()) {
                    List $this$count$iv = live;
                    if (($this$count$iv instanceof Collection) && $this$count$iv.isEmpty()) {
                        count$iv = 0;
                    } else {
                        count$iv = 0;
                        for (Object element$iv : $this$count$iv) {
                            GnssSat it9 = (GnssSat) element$iv;
                            if (it9.getUsed() && (count$iv = count$iv + 1) < 0) {
                                CollectionsKt.throwCountOverflow();
                            }
                        }
                    }
                    i = count$iv;
                } else if (has) {
                    Intrinsics.checkNotNull(fix);
                    i = fix.getSats();
                } else {
                    i = 0;
                }
                if (live.isEmpty()) {
                    List $this$map$iv = live;
                    Collection destination$iv$iv5 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                    for (Object item$iv$iv : $this$map$iv) {
                        GnssSat it10 = (GnssSat) item$iv$iv;
                        destination$iv$iv5.add(Integer.valueOf(it10.getCno()));
                    }
                    f2 = (float) CollectionsKt.averageOfInt((List) destination$iv$iv5);
                } else {
                    f2 = Float.NaN;
                }
                final Comparator comparator = new Comparator() { // from class: com.starpilot.starview.GpsSummary$Companion$of$$inlined$compareByDescending$1
                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // java.util.Comparator
                    public final int compare(T t, T t2) {
                        GnssSat it11 = (GnssSat) t2;
                        Boolean valueOf = Boolean.valueOf(it11.getUsed());
                        GnssSat it12 = (GnssSat) t;
                        return ComparisonsKt.compareValues(valueOf, Boolean.valueOf(it12.getUsed()));
                    }
                };
                List sortedWith = CollectionsKt.sortedWith(live, new Comparator() { // from class: com.starpilot.starview.GpsSummary$Companion$of$$inlined$thenByDescending$1
                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // java.util.Comparator
                    public final int compare(T t, T t2) {
                        int previousCompare = comparator.compare(t, t2);
                        if (previousCompare != 0) {
                            return previousCompare;
                        }
                        GnssSat it11 = (GnssSat) t2;
                        Integer valueOf = Integer.valueOf(it11.getCno());
                        GnssSat it12 = (GnssSat) t;
                        return ComparisonsKt.compareValues(valueOf, Integer.valueOf(it12.getCno()));
                    }
                });
                List $this$filter$iv5 = live;
                Collection destination$iv$iv6 = new ArrayList();
                for (Object element$iv$iv5 : $this$filter$iv5) {
                    GnssSat it11 = (GnssSat) element$iv$iv5;
                    int elevation = it11.getElevation();
                    if (elevation >= 0 && elevation < 91) {
                        int azimuth = it11.getAzimuth();
                        if (azimuth >= 0 && azimuth < 361) {
                            z2 = true;
                            if (!z2) {
                                destination$iv$iv6.add(element$iv$iv5);
                            }
                        }
                    }
                    z2 = false;
                    if (!z2) {
                    }
                }
                ArrayList arrayList = (List) destination$iv$iv6;
                if (has) {
                    f3 = Float.NaN;
                } else {
                    Intrinsics.checkNotNull(fix);
                    f3 = fix.getSpeed();
                }
                if (has) {
                    Intrinsics.checkNotNull(fix);
                    if (fix.getSpeed() > 1.0f) {
                        float bearing = fix.getBearing();
                        if ((Float.isInfinite(bearing) || Float.isNaN(bearing)) ? false : true) {
                            f4 = fix.getBearing();
                            if (has) {
                                Intrinsics.checkNotNull(fix);
                                j = fix.getUnixMs();
                            } else {
                                j = 0;
                            }
                            return new GpsSummary(state, f, size, i, f2, sortedWith, arrayList, f3, f4, j);
                        }
                    }
                }
                f4 = Float.NaN;
                if (has) {
                }
                return new GpsSummary(state, f, size, i, f2, sortedWith, arrayList, f3, f4, j);
            }
            z = false;
            has = z;
            if (has) {
            }
            int size2 = live.size();
            if (live.isEmpty()) {
            }
            if (live.isEmpty()) {
            }
            final Comparator comparator2 = new Comparator() { // from class: com.starpilot.starview.GpsSummary$Companion$of$$inlined$compareByDescending$1
                /* JADX WARN: Multi-variable type inference failed */
                @Override // java.util.Comparator
                public final int compare(T t, T t2) {
                    GnssSat it112 = (GnssSat) t2;
                    Boolean valueOf = Boolean.valueOf(it112.getUsed());
                    GnssSat it12 = (GnssSat) t;
                    return ComparisonsKt.compareValues(valueOf, Boolean.valueOf(it12.getUsed()));
                }
            };
            List sortedWith2 = CollectionsKt.sortedWith(live, new Comparator() { // from class: com.starpilot.starview.GpsSummary$Companion$of$$inlined$thenByDescending$1
                /* JADX WARN: Multi-variable type inference failed */
                @Override // java.util.Comparator
                public final int compare(T t, T t2) {
                    int previousCompare = comparator2.compare(t, t2);
                    if (previousCompare != 0) {
                        return previousCompare;
                    }
                    GnssSat it112 = (GnssSat) t2;
                    Integer valueOf = Integer.valueOf(it112.getCno());
                    GnssSat it12 = (GnssSat) t;
                    return ComparisonsKt.compareValues(valueOf, Integer.valueOf(it12.getCno()));
                }
            });
            List $this$filter$iv52 = live;
            Collection destination$iv$iv62 = new ArrayList();
            while (r13.hasNext()) {
            }
            ArrayList arrayList2 = (List) destination$iv$iv62;
            if (has) {
            }
            if (has) {
            }
            f4 = Float.NaN;
            if (has) {
            }
            return new GpsSummary(state, f, size2, i, f2, sortedWith2, arrayList2, f3, f4, j);
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
