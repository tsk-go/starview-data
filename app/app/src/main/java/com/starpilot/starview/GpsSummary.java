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

        /* VF: from Vineflower (jadx: Removed duplicated region;incorrect) */
        public final GpsSummary of(Collection<GpsFixInfo> var1, List<GnssSat> var2, long var3, boolean var5, long var6) {
            Intrinsics.checkNotNullParameter(var1, "fixes");
            Intrinsics.checkNotNullParameter(var2, "sats");
            Iterable var22 = var1;
            var var27 = new ArrayList();

            for (Object var23 : var22) {
                boolean var12;
                if (var6 - ((GpsFixInfo)var23).getAtNs() < 5000000000L) {
                    var12 = true;
                } else {
                    var12 = false;
                }

                if (var12) {
                    var27.add(var23);
                }
            }

            List var24 = var27;
            var22 = var24;
            var var28 = new ArrayList();

            for (Object var22$it : (Iterable) var22) {
                var22 = (Iterable) var22$it;
                Object var25 = (GpsFixInfo)var22;
                boolean var44;
                if (Intrinsics.areEqual(((GpsFixInfo) var25).getService(), "gpsLocationExternal") && Intrinsics.areEqual(((GpsFixInfo) var25).getSource(), "ublox")) {
                    var44 = true;
                } else {
                    var44 = false;
                }

                if (var44) {
                    var28.add(var22);
                }
            }

            List var29 = var28;
            Iterator var80 = var29.iterator();
            Object var30;
            if (!var80.hasNext()) {
                var30 = null;
            } else {
                var30 = (Collection)var80.next();
                if (var80.hasNext()) {
                    long var16 = ((GpsFixInfo)var30).getAtNs();
                    var22 = ((Iterable) var30);

                    do {
                        Object var66 = var80.next();
                        long var20 = ((GpsFixInfo)var66).getAtNs();
                        var30 = var22;
                        long var18 = var16;
                        if (var16 < var20) {
                            var30 = (Collection)var66;
                            var18 = var20;
                        }

                        var22 = ((Iterable) var30);
                        var16 = var18;
                    } while (var80.hasNext());
                }
            }

            var30 = (GpsFixInfo)var30;
            GpsFixInfo var61 = (GpsFixInfo)var30;
            if (var30 == null) {
                var22 = var24;
                var30 = new ArrayList();

                for (Object var67 : var22) {
                    if (Intrinsics.areEqual(((GpsFixInfo)var67).getService(), "gpsLocation")) {
                        var30.add(var67);
                    }
                }

                List var33 = (List)var30;
                Iterator var75 = var33.iterator();
                if (!var75.hasNext()) {
                    var30 = null;
                } else {
                    var30 = (Collection)var75.next();
                    if (var75.hasNext()) {
                        long var55 = ((GpsFixInfo)var30).getAtNs();
                        var22 = ((Iterable) var30);

                        do {
                            Object var68 = var75.next();
                            long var56 = ((GpsFixInfo)var68).getAtNs();
                            var30 = var22;
                            long var54 = var55;
                            if (var55 < var56) {
                                var30 = (Collection)var68;
                                var54 = var56;
                            }

                            var22 = ((Iterable) var30);
                            var55 = var54;
                        } while (var75.hasNext());
                    }
                }

                var61 = (GpsFixInfo)var30;
            }

            if (var6 - var3 < 5000000000L) {
                Iterable var37 = var2;
                var var35 = new ArrayList();

                for (Object var38 : var37) {
                    boolean var45;
                    if (((GnssSat)var38).getCno() > 0) {
                        var45 = true;
                    } else {
                        var45 = false;
                    }

                    if (var45) {
                        var35.add(var38);
                    }
                }

                var2 = var35;
            } else {
                var2 = CollectionsKt.emptyList();
            }

            int var46;
            if (var61 != null && var61.getHasFix()) {
                var46 = 1;
            } else {
                var46 = 0;
            }

            String var36;
            if ((var46 != 0)) {
                var36 = "fix";
            } else if (var61 != null || !var2.isEmpty()) {
                var36 = "searching";
            } else if (!var5) {
                var36 = "off";
            } else {
                var36 = "waiting";
            }

            boolean var13;
            if (var61 != null && var61.getHasFix()) {
                var13 = true;
            } else {
                var13 = false;
            }

            float var8;
            if (var13) {
                Intrinsics.checkNotNull(var61);
                var8 = var61.getHAcc();
            } else {
                var8 = Float.NaN;
            }

            int var15 = var2.size();
            if (!var2.isEmpty()) {
                var var70 = var2;
                if (var70 instanceof Collection && ((Collection)var70).isEmpty()) {
                    var46 = 0;
                } else {
                    var46 = 0;
                    Iterator var71 = var70.iterator();

                    while (var71.hasNext()) {
                        if (((GnssSat)var71.next()).getUsed()) {
                            int var14 = var46 + 1;
                            var46 = var14;
                            if (var14 < 0) {
                                CollectionsKt.throwCountOverflow();
                                var46 = var14;
                            }
                        }
                    }
                }
            } else if (var13) {
                Intrinsics.checkNotNull(var61);
                var46 = var61.getSats();
            } else {
                var46 = 0;
            }

            float var9;
            if (!var2.isEmpty()) {
                Iterable var76 = var2;
                var var72 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var76, 10));
                Iterator var77 = var76.iterator();

                while (var77.hasNext()) {
                    var72.add(((GnssSat)var77.next()).getCno());
                }

                List var73 = var72;
                var9 = (float)CollectionsKt.averageOfInt(var73);
            } else {
                var9 = Float.NaN;
            }

            List var74 = CollectionsKt.sortedWith(var2, new Comparator() {
                final Comparator $this_thenByDescending = new Comparator() {
                @Override
                public final int compare(Object var1, Object var2) {
                    return ComparisonsKt.compareValues(((GnssSat)var2).getUsed(), ((GnssSat)var1).getUsed());
                }
            };

                @Override
                public final int compare(Object var1, Object var2x) {
                    int var3x = this.$this_thenByDescending.compare(var1, var2x);
                    if (var3x == 0) {
                        var3x = ComparisonsKt.compareValues(((GnssSat)var2x).getCno(), ((GnssSat)var1).getCno());
                    }

                    return var3x;
                }
            });
            Iterable var78 = var2;
            var var40 = new ArrayList();

            for (Object var79 : var78) {
                GnssSat var26 = (GnssSat)var79;
                int var48 = var26.getElevation();
                int var49;
                if (var48 >= 0 && var48 < 91) {
                    var49 = 1;
                } else {
                    var49 = 0;
                }

                label164: {
                    if ((var49 != 0)) {
                        var49 = var26.getAzimuth();
                        boolean var51;
                        if (var49 >= 0 && var49 < 361) {
                            var51 = true;
                        } else {
                            var51 = false;
                        }

                        if (var51) {
                            var49 = (((boolean)true) ? 1 : 0);
                            break label164;
                        }
                    }

                    var49 = (((boolean)false) ? 1 : 0);
                }

                if ((var49 != 0)) {
                    var40.add(var79);
                }
            }

            var2 = var40;
            float var10;
            if (var13) {
                Intrinsics.checkNotNull(var61);
                var10 = var61.getSpeed();
            } else {
                var10 = Float.NaN;
            }

            float var43;
            label151: {
                if (var13) {
                    Intrinsics.checkNotNull(var61);
                    if (var61.getSpeed() > 1.0F) {
                        var43 = var61.getBearing();
                        boolean var53;
                        if (!Float.isInfinite(var43) && !Float.isNaN(var43)) {
                            var53 = true;
                        } else {
                            var53 = false;
                        }

                        if (var53) {
                            var43 = var61.getBearing();
                            break label151;
                        }
                    }
                }

                var43 = Float.NaN;
            }

            if (var13) {
                Intrinsics.checkNotNull(var61);
                var3 = var61.getUnixMs();
            } else {
                var3 = 0L;
            }

            return new GpsSummary(var36, var8, var15, var46, var9, var74, var2, var10, var43, var3);
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
