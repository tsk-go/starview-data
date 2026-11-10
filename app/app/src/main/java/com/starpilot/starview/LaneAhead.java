package com.starpilot.starview;

import com.starpilot.starview.Hpms;
import com.starpilot.starview.LaneBrain;
import com.starpilot.starview.Nav;
import com.starpilot.starview.OsmRoads;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import kotlin.Pair;
import kotlin.Triple;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.ArrayDeque;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;

/* compiled from: LaneAhead.kt */
public final class LaneAhead {
    public static final Companion Companion = new Companion(null);
    private volatile boolean busy;
    private volatile Drop drop;
    private long lastLookMs;
    private Function2<? super String, ? super Boolean, Unit> onEvent;
    private Function3<? super Double, ? super Double, ? super Double, ? extends List<Sample>> profileSource;
    private volatile Triple<Double, ? extends List<Sample>, Integer> result;
    private volatile Stage stage = Stage.NONE;
    private volatile List<Sample> profile = CollectionsKt.emptyList();
    private double lastLookOdo = -1.0E9d;
    private final ArrayDeque<double[]> gaps = new ArrayDeque<>();
    private final ExecutorService worker = Executors.newSingleThreadExecutor(new ThreadFactory() { // from class: com.starpilot.starview.LaneAhead$$ExternalSyntheticLambda1
        @Override // java.util.concurrent.ThreadFactory
        public final Thread newThread(Runnable runnable) {
            Thread worker$lambda$6;
            worker$lambda$6 = LaneAhead.worker$lambda$6(runnable);
            return worker$lambda$6;
        }
    });

    /* compiled from: LaneAhead.kt */
    public enum Stage {
        NONE,
        WATCHING,
        PREPARE,
        URGENT;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<Stage> getEntries() {
            return $ENTRIES;
        }
    }

    /* compiled from: LaneAhead.kt */
    public static final class Drop {
        private boolean announced;
        private final double atOdo;
        private final int from;
        private boolean prepared;
        private final String road;
        private int side;
        private String sideWhy;
        private final int to;

        public Drop(double atOdo, int from, int to, String road) {
            Intrinsics.checkNotNullParameter(road, "road");
            this.atOdo = atOdo;
            this.from = from;
            this.to = to;
            this.road = road;
            this.sideWhy = "";
        }

        public final double getAtOdo() {
            return this.atOdo;
        }

        public final int getFrom() {
            return this.from;
        }

        public final String getRoad() {
            return this.road;
        }

        public final int getTo() {
            return this.to;
        }

        public final int getSide() {
            return this.side;
        }

        public final String getSideWhy() {
            return this.sideWhy;
        }

        public final void setSide(int i) {
            this.side = i;
        }

        public final void setSideWhy(String str) {
            Intrinsics.checkNotNullParameter(str, "<set-?>");
            this.sideWhy = str;
        }

        public final boolean getAnnounced() {
            return this.announced;
        }

        public final boolean getPrepared() {
            return this.prepared;
        }

        public final void setAnnounced(boolean z) {
            this.announced = z;
        }

        public final void setPrepared(boolean z) {
            this.prepared = z;
        }
    }

    /* compiled from: LaneAhead.kt */
    public static final class Sample {
        private final double d;
        private final double dir;
        private final int lanes;
        private final double lat;
        private final double lon;
        private final String name;

        public Sample(double d, int lanes, double lat, double lon, double dir, String name) {
            Intrinsics.checkNotNullParameter(name, "name");
            this.d = d;
            this.lanes = lanes;
            this.lat = lat;
            this.lon = lon;
            this.dir = dir;
            this.name = name;
        }

        public final double getD() {
            return this.d;
        }

        public final double getDir() {
            return this.dir;
        }

        public final int getLanes() {
            return this.lanes;
        }

        public final double getLat() {
            return this.lat;
        }

        public final double getLon() {
            return this.lon;
        }

        public final String getName() {
            return this.name;
        }
    }

    public final Drop getDrop() {
        return this.drop;
    }

    public final Stage getStage() {
        return this.stage;
    }

    public final List<Sample> getProfile() {
        return this.profile;
    }

    public final Function2<String, Boolean, Unit> getOnEvent() {
        return this.onEvent;
    }

    public final void setOnEvent(Function2<? super String, ? super Boolean, Unit> function2) {
        this.onEvent = function2;
    }

    public final Function3<Double, Double, Double, List<Sample>> getProfileSource() {
        return this.profileSource;
    }

    public final void setProfileSource(Function3<? super Double, ? super Double, ? super Double, ? extends List<Sample>> function3) {
        this.profileSource = function3;
    }

    public final void reset() {
        this.drop = null;
        this.stage = Stage.NONE;
        this.profile = CollectionsKt.emptyList();
        this.lastLookOdo = -1.0E9d;
        this.gaps.clear();
        this.result = null;
    }

    public final double distance(double odo) {
        Drop it = this.drop;
        if (it != null) {
            return it.getAtOdo() - odo;
        }
        return Double.NaN;
    }

    public final String text(double odo, boolean metric) {
        Drop d = this.drop;
        if (d == null) {
            return "";
        }
        double m = d.getAtOdo() - odo;
        String far = Companion.dist(m, metric);
        if (this.stage == Stage.URGENT && d.getSide() != 0) {
            String sideName = Companion.sideName(d.getSide());
            String lowerCase = Companion.sideName(-d.getSide()).toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
            return sideName + " lane ends - merge " + lowerCase + " now";
        }
        if (d.getSide() != 0) {
            return Companion.sideName(d.getSide()) + " lane ends in " + far;
        }
        return d.getFrom() + " → " + d.getTo() + " lanes in " + far;
    }

    /* VF: from Vineflower (jadx: Removed duplicated region;incorrect) */
    public final void update(long var1, double var3, double var5, double var7, double var9, float var11, LaneBrain var12, LaneBrain.Frame var13) {
        String var32 = null; // declarations Vineflower left out
        Intrinsics.checkNotNullParameter(var12, "b");
        this.sideFromCamera(var3, var12, var13);
        LaneAhead.Drop var21 = this.drop;
        Boolean var20 = true;
        if (var21 != null) {
            double var14 = var21.getAtOdo() - var3;
            if (var14 < -60.0) {
                if (var21.getSide() != 0) {
                    var12.roadLaneEnded(var21.getSide(), var1);
                }

                Function2 var22 = this.onEvent;
                if (var22 != null) {
                    int var19 = var21.getFrom();
                    int var18 = var21.getTo();
                    String var26;
                    if (var21.getSide() != 0) {
                        String var25 = Companion.sideName(var21.getSide()).toLowerCase(Locale.ROOT);
                        Intrinsics.checkNotNullExpressionValue(var25, "toLowerCase(...)");
                        var26 = " (" + var25 + " lane ended)";
                    } else {
                        var26 = "";
                    }

                    var22.invoke("passed: " + var19 + " → " + var18 + " lanes" + var26, var20);
                }

                this.drop = null;
                this.stage = LaneAhead.Stage.NONE;
            } else {
                int var37;
                label130: {
                    if (var21.getSide() != 0) {
                        Pair var27 = var12.sideLanes(var21.getSide());
                        var37 = ((Number)var27.component1()).intValue();
                        double var16 = ((Number)var27.component2()).doubleValue();
                        boolean var36;
                        if (var37 == 0 && var16 >= 0.6) {
                            var36 = true;
                        } else {
                            var36 = false;
                        }

                        if (var36) {
                            var37 = 1;
                            break label130;
                        }
                    }

                    var37 = 0;
                }

                double var34 = Math.max(183.0, var11 * 8.0);
                LaneAhead.Stage var28;
                if (var14 < 61.0 && (var37 != 0)) {
                    var28 = LaneAhead.Stage.URGENT;
                } else if (var14 < var34) {
                    var28 = LaneAhead.Stage.PREPARE;
                } else {
                    var28 = LaneAhead.Stage.WATCHING;
                }

                this.stage = var28;
                if (this.stage == LaneAhead.Stage.PREPARE && !var21.getPrepared()) {
                    var21.setPrepared(true);
                    Function2 var45 = this.onEvent;
                    if (var45 != null) {
                        String var23 = Companion.dist(var14, false);
                        String var44 = this.text(var3, false);
                        String var29;
                        if ((var37 != 0)) {
                            var29 = " - we're in it";
                        } else {
                            var29 = "";
                        }

                        var45.invoke("lane ends ahead, " + var23 + ": " + var44 + var29, var20);
                    }
                }
            }
        }

        Triple var30 = this.result;
        if (var30 != null) {
            double var33 = ((Number)var30.component1()).doubleValue();
            List var43 = (List)var30.component2();
            int var38 = ((Number)var30.component3()).intValue();
            this.result = null;
            this.apply(var33, var43, var38, var3);
        }

        if (!(var11 < 2.0F)) {
            int var39;
            if (!Double.isInfinite(var5) && !Double.isNaN(var5)) {
                var39 = 1;
            } else {
                var39 = 0;
            }

            if ((var39 != 0)) {
                if (!Double.isInfinite(var7) && !Double.isNaN(var7)) {
                    var39 = (((boolean)true) ? 1 : 0);
                } else {
                    var39 = (((boolean)false) ? 1 : 0);
                }

                if ((var39 != 0)) {
                    if (!Double.isInfinite(var9) && !Double.isNaN(var9)) {
                        var39 = (((boolean)true) ? 1 : 0);
                    } else {
                        var39 = (((boolean)false) ? 1 : 0);
                    }

                    if ((var39 != 0) && !this.busy) {
                        if (var3 - this.lastLookOdo < 150.0 && var1 - this.lastLookMs < 20000L) {
                            return;
                        }

                        label92: {
                            this.lastLookOdo = var3;
                            this.lastLookMs = var1;
                            LaneBrain.RoadInfo var31 = var12.getInfo();
                            if (var31 != null) {
                                var32 = var31.getHpmsName();
                                if (var32 != null) {
                                    break label92;
                                }
                            }

                            var32 = "";
                        }

                        if (Intrinsics.areEqual(var12.getModel().getSrc(), "HPMS")) {
                            var39 = var12.getModel().getLanes();
                        } else {
                            var39 = 0;
                        }

                        Function3 var24 = this.profileSource;
                        if (var24 != null) {
                            this.apply(var3, (List<LaneAhead.Sample>)var24.invoke(var5, var7, var9), var39, var3);
                            return;
                        }

                        this.busy = true;
                        this.worker.execute(new LaneAhead$$ExternalSyntheticLambda0(this, var5, var7, var9, var3, var39, var32));
                        return;
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void update$lambda$4(LaneAhead this$0, double $lat, double $lon, double $headingRad, double $odo, int $cur, String $prefer) {
        Iterable iterable;
        Hpms.Match m;
        double d;
        String str;
        Hpms.Section s;
        Hpms.Section s2;
        try {
            try {
                iterable = this$0.routePath($lat, $lon);
                if (iterable == null) {
                    iterable = this$0.roadPath($lat, $lon, $headingRad);
                }
            } finally {
                this$0.busy = false;
            }
        } catch (Exception e) {
            iterable = null;
        }
        Iterable path = iterable;
        if (path != null) {
            Double valueOf = Double.valueOf($odo);
            Iterable $this$map$iv = path;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            for (Object item$iv$iv : $this$map$iv) {
                Pair pair = (Pair) item$iv$iv;
                double d0 = ((Number) pair.component1()).doubleValue();
                double[] p = (double[]) pair.component2();
                try {
                    m = Hpms.INSTANCE.match(p[0], p[1], p[2], false, (r24 & 16) != 0 ? 45.0d : 0.0d, (r24 & 32) != 0 ? "" : $prefer);
                } catch (Exception e2) {
                    m = null;
                }
                int ourLanes = (m == null || (s2 = m.getS()) == null) ? 0 : s2.ourLanes();
                double d2 = p[0];
                double d3 = p[1];
                d = p[2];
                if (m == null || (s = m.getS()) == null || (str = s.getName()) == null) {
                    str = "";
                }
                destination$iv$iv.add(new Sample(d0, ourLanes, d2, d3, d, str));
            }
            this$0.result = new Triple<>(valueOf, (List) destination$iv$iv, Integer.valueOf($cur));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Thread worker$lambda$6(Runnable r) {
        Thread $this$worker_u24lambda_u246_u24lambda_u245 = new Thread(r, "lane-ahead");
        $this$worker_u24lambda_u246_u24lambda_u245.setDaemon(true);
        return $this$worker_u24lambda_u246_u24lambda_u245;
    }

    private final void apply(double lookOdo, List<Sample> samples, int curLanes, double odo) {
        String name;
        this.profile = samples;
        Triple found = Companion.findDrop(samples, curLanes);
        Drop cur = this.drop;
        Object obj = null;
        if (found == null) {
            if (cur == null || cur.getAtOdo() - odo <= 300.0d) {
                return;
            }
            this.drop = null;
            this.stage = Stage.NONE;
            return;
        }
        double dm = ((Number) found.component1()).doubleValue();
        int from = ((Number) found.component2()).intValue();
        int to = ((Number) found.component3()).intValue();
        double at = lookOdo + dm;
        if (at - odo < 30.0d) {
            return;
        }
        if (cur == null || Math.abs(cur.getAtOdo() - at) >= 200.0d || cur.getFrom() != from || cur.getTo() != to) {
            if (cur == null || cur.getAtOdo() - odo >= 300.0d) {
                List<Sample> $this$firstOrNull$iv = samples;
                Iterator it = $this$firstOrNull$iv.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    Object element$iv = it.next();
                    if (((Sample) element$iv).getName().length() > 0) {
                        obj = element$iv;
                        break;
                    }
                }
                Sample sample = (Sample) obj;
                Drop it2 = new Drop(at, from, to, (sample == null || (name = sample.getName()) == null) ? "" : name);
                Function2<? super String, ? super Boolean, Unit> function2 = this.onEvent;
                if (function2 != null) {
                    function2.invoke("ahead: " + from + " → " + to + " lanes in " + Companion.dist(at - odo, false) + " (HPMS" + (it2.getRoad().length() > 0 ? ", " + it2.getRoad() : "") + ")", true);
                }
                this.drop = it2;
                this.stage = Stage.WATCHING;
            }
        }
    }

    public final Pair<Integer, String> need(LaneBrain b) {
        Intrinsics.checkNotNullParameter(b, "b");
        Drop d = this.drop;
        if (d == null) {
            return TuplesKt.to(0, "");
        }
        if (d.getSide() == 0 || (this.stage != Stage.PREPARE && this.stage != Stage.URGENT)) {
            return TuplesKt.to(0, "");
        }
        Pair<Integer, Double> sideLanes = b.sideLanes(d.getSide());
        int n = sideLanes.component1().intValue();
        double c = sideLanes.component2().doubleValue();
        if (n != 0 || c < 0.6d) {
            return TuplesKt.to(0, "");
        }
        Pair<Integer, Double> sideLanes2 = b.sideLanes(-d.getSide());
        int o = sideLanes2.component1().intValue();
        double co = sideLanes2.component2().doubleValue();
        if (o < 1 || co < 0.5d) {
            return TuplesKt.to(0, "");
        }
        Integer valueOf = Integer.valueOf(-d.getSide());
        String lowerCase = Companion.sideName(d.getSide()).toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
        return TuplesKt.to(valueOf, lowerCase + " lane ends ahead (" + d.getFrom() + " → " + d.getTo() + " lanes)");
    }

    /* VF: from Vineflower (jadx: Removed duplicated region;incorrect) */
    private final void sideFromCamera(double var1, LaneBrain var3, LaneBrain.Frame var4) {
        LaneAhead.Drop var18 = this.drop;
        if (var4 != null) {
            float var15 = var4.getEdgeL();
            int var16;
            if (!Float.isInfinite(var15) && !Float.isNaN(var15)) {
                var16 = 1;
            } else {
                var16 = 0;
            }

            double var5;
            double var11;
            label273: {
                var11 = Double.NaN;
                if ((var16 != 0)) {
                    var15 = var4.getYL();
                    if (!Float.isInfinite(var15) && !Float.isNaN(var15)) {
                        var16 = (((boolean)true) ? 1 : 0);
                    } else {
                        var16 = (((boolean)false) ? 1 : 0);
                    }

                    if ((var16 != 0) && var4.getPL() > 0.4F) {
                        var5 = var4.getEdgeL() - Math.abs(var4.getYL());
                        break label273;
                    }
                }

                var5 = Double.NaN;
            }

            var15 = var4.getEdgeR();
            if (!Float.isInfinite(var15) && !Float.isNaN(var15)) {
                var16 = (((boolean)true) ? 1 : 0);
            } else {
                var16 = (((boolean)false) ? 1 : 0);
            }

            double var7;
            label257: {
                if ((var16 != 0)) {
                    var15 = var4.getYR();
                    if (!Float.isInfinite(var15) && !Float.isNaN(var15)) {
                        var16 = (((boolean)true) ? 1 : 0);
                    } else {
                        var16 = (((boolean)false) ? 1 : 0);
                    }

                    if ((var16 != 0) && var4.getPR() > 0.4F) {
                        var7 = var4.getEdgeR() - Math.abs(var4.getYR());
                        break label257;
                    }
                }

                var7 = Double.NaN;
            }

            var15 = var4.getYL();
            if (!Float.isInfinite(var15) && !Float.isNaN(var15)) {
                var16 = (((boolean)true) ? 1 : 0);
            } else {
                var16 = (((boolean)false) ? 1 : 0);
            }

            double var9 = var11;
            if ((var16 != 0)) {
                var15 = var4.getYR();
                if (!Float.isInfinite(var15) && !Float.isNaN(var15)) {
                    var16 = (((boolean)true) ? 1 : 0);
                } else {
                    var16 = (((boolean)false) ? 1 : 0);
                }

                var9 = var11;
                if ((var16 != 0)) {
                    var9 = var11;
                    if (var4.getPL() > 0.4F) {
                        var9 = var11;
                        if (var4.getPR() > 0.4F) {
                            var9 = var4.getYR() - var4.getYL();
                        }
                    }
                }
            }

            if (var4.getOpChange() != 0) {
                this.gaps.clear();
            } else {
                if (this.gaps.isEmpty() || var1 - this.gaps.last()[0] >= 5.0) {
                    this.gaps.addLast(new double[]{var1, var5, var7, var9, var4.getYL(), var4.getYR()});
                }

                while (!this.gaps.isEmpty() && var1 - ((double[])this.gaps.first())[0] > 220.0) {
                    this.gaps.removeFirst();
                }

                if (var18 != null && var18.getSide() == 0 && !(var18.getAtOdo() - var1 > 450.0) && this.gaps.size() >= 20) {
                    var7 = var3.getLaneW();
                    var5 = sideFromCamera$trend(this, 1);
                    var1 = sideFromCamera$trend(this, 2);
                    var11 = sideFromCamera$trend(this, 3);
                    double var13 = sideFromCamera$trend(this, 4);
                    var9 = sideFromCamera$trend(this, 5);
                    if (!Double.isInfinite(var1) && !Double.isNaN(var1)) {
                        var16 = (((boolean)true) ? 1 : 0);
                    } else {
                        var16 = (((boolean)false) ? 1 : 0);
                    }

                    if ((var16 != 0) && (var1 != 0) < var7 * -0.6 && !(var5 < var7 * -0.3)) {
                        var16 = 1;
                    } else {
                        boolean var38;
                        if (!Double.isInfinite(var5) && !Double.isNaN(var5)) {
                            var38 = true;
                        } else {
                            var38 = false;
                        }

                        if (var38 && var5 < var7 * -0.6 && !(var1 < -0.3 * var7)) {
                            var16 = -1;
                        } else {
                            label296: {
                                if (!Double.isInfinite(var11) && !Double.isNaN(var11)) {
                                    var38 = true;
                                } else {
                                    var38 = false;
                                }

                                if (var38 && var11 < -0.5) {
                                    if (!Double.isInfinite(var9) && !Double.isNaN(var9)) {
                                        var38 = true;
                                    } else {
                                        var38 = false;
                                    }

                                    if (var38) {
                                        if (!Double.isInfinite(var13) && !Double.isNaN(var13)) {
                                            var38 = true;
                                        } else {
                                            var38 = false;
                                        }

                                        if (var38 && -var9 > Math.abs(var13) + 0.3) {
                                            var16 = 1;
                                            break label296;
                                        }
                                    }
                                }

                                if (!Double.isInfinite(var11) && !Double.isNaN(var11)) {
                                    var38 = true;
                                } else {
                                    var38 = false;
                                }

                                if (var38 && var11 < -0.5) {
                                    if (!Double.isInfinite(var9) && !Double.isNaN(var9)) {
                                        var38 = true;
                                    } else {
                                        var38 = false;
                                    }

                                    if (var38) {
                                        if (!Double.isInfinite(var13) && !Double.isNaN(var13)) {
                                            var38 = true;
                                        } else {
                                            var38 = false;
                                        }

                                        if (var38 && var13 > Math.abs(var9) + 0.3) {
                                            var16 = -1;
                                            break label296;
                                        }
                                    }
                                }

                                var16 = 0;
                            }
                        }
                    }

                    if (var16 != 0) {
                        if (var16 <= 0) {
                            var1 = var5;
                        }

                        boolean var17;
                        if (!Double.isInfinite(var1) && !Double.isNaN(var1)) {
                            var17 = true;
                        } else {
                            var17 = false;
                        }

                        if (var17 && var1 < var7 * -0.6) {
                            var17 = true;
                        } else {
                            var17 = false;
                        }

                        var18.setSide(var16);
                        String var20;
                        if (var17) {
                            var20 = "road edge closing in";
                        } else {
                            var20 = "our lane narrowing";
                        }

                        var18.setSideWhy(var20);
                        Function2 var22 = this.onEvent;
                        if (var22 != null) {
                            String var21 = Companion.sideName(var16).toLowerCase(Locale.ROOT);
                            Intrinsics.checkNotNullExpressionValue(var21, "toLowerCase(...)");
                            String var47 = var18.getSideWhy();
                            var22.invoke("the " + var21 + " lane ends (camera: " + var47 + ")", true);
                        }
                    }
                }
            }
        }
    }

    private static final double sideFromCamera$trend(LaneAhead this$0, int k) {
        Iterable $this$mapNotNull$iv = CollectionsKt.take(this$0.gaps, this$0.gaps.size() / 3);
        Collection destination$iv$iv = new ArrayList();
        Iterator it = $this$mapNotNull$iv.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Object element$iv$iv$iv = it.next();
            double[] it2 = (double[]) element$iv$iv$iv;
            Double valueOf = Double.valueOf(it2[k]);
            double v = valueOf.doubleValue();
            Double d = (Double.isInfinite(v) || Double.isNaN(v)) ? false : true ? valueOf : null;
            if (d != null) {
                destination$iv$iv.add(d);
            }
        }
        List old = (List) destination$iv$iv;
        Iterable $this$mapNotNull$iv2 = CollectionsKt.takeLast(this$0.gaps, this$0.gaps.size() / 3);
        Collection destination$iv$iv2 = new ArrayList();
        for (Object element$iv$iv$iv2 : $this$mapNotNull$iv2) {
            double[] it3 = (double[]) element$iv$iv$iv2;
            Double valueOf2 = Double.valueOf(it3[k]);
            double v2 = valueOf2.doubleValue();
            if (!((Double.isInfinite(v2) || Double.isNaN(v2)) ? false : true)) {
                valueOf2 = null;
            }
            if (valueOf2 != null) {
                destination$iv$iv2.add(valueOf2);
            }
        }
        List list = (List) destination$iv$iv2;
        if (old.size() < 4 || list.size() < 4) {
            return Double.NaN;
        }
        return CollectionsKt.averageOfDouble(list) - CollectionsKt.averageOfDouble(old);
    }

    private final List<Pair<Double, double[]>> routePath(double lat, double lon) {
        Nav.Line l = Nav.INSTANCE.getLine();
        if (l == null) {
            return null;
        }
        double a = Nav.INSTANCE.getAlong();
        int i = 1;
        if (!((Double.isInfinite(a) || Double.isNaN(a)) ? false : true) || l.getSize() < 2) {
            return null;
        }
        double dist = ((Number) Nav.Line.locate$default(l, lat, lon, 0, 4, null).component1()).doubleValue();
        if (((Double.isInfinite(dist) || Double.isNaN(dist)) ? false : true) && dist <= 40.0d) {
            ArrayList out = new ArrayList();
            double s = 100.0d;
            while (s <= 1600.0d && a + s < l.getLength()) {
                int i2 = RangesKt.coerceIn(l.indexAt(a + s), i, l.getSize() - i);
                double t = RangesKt.coerceIn(((a + s) - l.getCum()[i2 - 1]) / Math.max(0.01d, l.getCum()[i2] - l.getCum()[i2 - 1]), 0.0d, 1.0d);
                double x = l.getMx()[i2 - 1] + ((l.getMx()[i2] - l.getMx()[i2 - 1]) * t);
                double y = l.getMy()[i2 - 1] + ((l.getMy()[i2] - l.getMy()[i2 - 1]) * t);
                double plat = Nav.INSTANCE.latOf(y);
                double plon = Nav.INSTANCE.lonOf(x);
                ArrayList out2 = out;
                double dir = Math.atan2(-(l.getMy()[i2] - l.getMy()[i2 - 1]), l.getMx()[i2] - l.getMx()[i2 - 1]);
                out2.add(TuplesKt.to(Double.valueOf(s), new double[]{plat, plon, dir}));
                s += 100.0d;
                out = out2;
                dist = dist;
                i = 1;
            }
            ArrayList out3 = out;
            return !out3.isEmpty() ? out3 : null;
        }
        return null;
    }

    /* VF: from Vineflower (jadx: Code restructure failed;Removed duplicated region;incorrect) */
    private final List<Pair<Double, double[]>> roadPath(double var1, double var3, double var5) {
        if (Double.isNaN(OsmRoads.INSTANCE.getLat0())) {
            return null;
        } else {
            Object var18 = OsmRoads.INSTANCE.toLocal(var1, var3);
            double var9 = ((Number)((Pair) var18).component1()).doubleValue();
            var3 = ((Number)((Pair) var18).component2()).doubleValue();
            OsmRoads.Match var50 = OsmRoads.INSTANCE.matchSeg(var9, var3, var5);
            if (var50 != null) {
                int var13;
                if (var50.getDist() < 20.0) {
                    var13 = 1;
                } else {
                    var13 = 0;
                }

                if ((var13 == 0)) {
                    var50 = null;
                }

                if (var50 != null) {
                    Object var21 = var50.getWay();
                    if (StringsKt.endsWith(((OsmRoads.Way) var21).getKind(), "_link", false)) {
                        return null;
                    }

                    ArrayList var25 = new ArrayList();
                    String var51 = "_link";
                    List var20 = roadPath$ordered(((Way) var21), var9, var3, var5);
                    HashSet var19 = new HashSet();
                    var3 = 0.0;

                    String var24;
                    while (true) {
                        var19.add(((OsmRoads.Way) var21).getId());

                        for (double[] var23 : ((double[]) var20)) {
                            if (!var25.isEmpty()) {
                                var3 += Math.hypot(var23[0] - ((double[])CollectionsKt.last(var25))[0], var23[1] - ((double[])CollectionsKt.last(var25))[1]);
                            }

                            var25.add(var23);
                        }

                        var24 = "get(...)";
                        if (var3 > 1050.0 || var25.size() < 2) {
                            break;
                        }

                        double[] var26 = ((double[]) CollectionsKt.last(var25));
                        Object var68 = var25.get(var25.size() - 2);
                        Intrinsics.checkNotNullExpressionValue(var68, "get(...)");
                        var68 = (double[])var68;
                        var1 = var26[1];
                        var5 = (double)((Object[])var68)[1];
                        var1 = Math.atan2(var1 - var5, var26[0] - ((Object[])var68)[0]);
                        Iterable var73 = OsmRoads.INSTANCE.near(var26[0], var26[1], 15.0);
                        boolean var16 = false;
                        var var28 = new ArrayList();

                        for (Object var30 : var73) {
                            label179: {
                                OsmRoads.Way var29 = (OsmRoads.Way)var30;
                                if (!var19.contains(var29.getId()) && !StringsKt.endsWith(var29.getKind(), var51, false)) {
                                    if (((OsmRoads.Way) var21).getName().length() > 0) {
                                        var13 = (((boolean)true) ? 1 : 0);
                                    } else {
                                        var13 = (((boolean)false) ? 1 : 0);
                                    }

                                    boolean var17;
                                    if ((var13 != 0)) {
                                        var17 = OsmRoads.INSTANCE.sameStreet(var29.getName(), ((OsmRoads.Way) var21).getName());
                                    } else {
                                        var17 = Intrinsics.areEqual(var29.getKind(), ((OsmRoads.Way) var21).getKind());
                                    }

                                    if (var17) {
                                        var13 = (((boolean)true) ? 1 : 0);
                                        break label179;
                                    }
                                }

                                var13 = (((boolean)false) ? 1 : 0);
                            }

                            if ((var13 != 0)) {
                                var28.add(var30);
                            }
                        }

                        List var65 = var28;
                        var73 = var65;
                        var var76 = new ArrayList();

                        for (OsmRoads.Way var52 : (Iterable<OsmRoads.Way>) var73) {
                            label200: {
                                if (Math.hypot(var52.getE()[0] - var26[0], var52.getN()[0] - var26[1]) < 15.0) {
                                    var13 = 0;
                                } else {
                                    if (!(Math.hypot(var52.getE()[var52.getE().length - 1] - var26[0], var52.getN()[var52.getN().length - 1] - var26[1]) < 15.0)) {
                                        var18 = null;
                                        break label200;
                                    }

                                    var13 = var52.getE().length - 1;
                                }

                                if (var52.getOneway() != 0) {
                                    boolean var14;
                                    if (var52.getOneway() > 0) {
                                        var14 = true;
                                    } else {
                                        var14 = false;
                                    }

                                    boolean var15;
                                    if (var13 == 0) {
                                        var15 = true;
                                    } else {
                                        var15 = false;
                                    }

                                    if (var14 != var15) {
                                        var18 = null;
                                        break label200;
                                    }
                                }

                                int var48;
                                if (var13 == 0) {
                                    var48 = 1;
                                } else {
                                    var48 = var52.getE().length - 2;
                                }

                                double var7 = Math.abs(Math.atan2(var52.getN()[var48] - var52.getN()[var13], var52.getE()[var48] - var52.getE()[var13]) - var1);
                                var5 = var7;
                                if (var7 > Math.PI) {
                                    var5 = (Math.PI * 2) - var7;
                                }

                                if (var5 > Math.toRadians(35.0)) {
                                    var18 = null;
                                } else {
                                    var18 = TuplesKt.to(var52, var5);
                                }
                            }

                            if (var18 != null) {
                                var76.add(var18);
                            }
                        }

                        List var54 = var76;
                        Iterable var55 = var54;
                        Iterator var75 = var55.iterator();
                        if (!var75.hasNext()) {
                            var18 = null;
                        } else {
                            var18 = (Pair)var75.next();
                            if (var75.hasNext()) {
                                var5 = ((Number)((Pair) var18).getSecond()).doubleValue();
                                var21 = var18;

                                do {
                                    var68 = var75.next();
                                    double var11 = ((Number)((Pair)var68).getSecond()).doubleValue();
                                    double var39 = var5;
                                    var18 = var21;
                                    if (Double.compare(var5, var11) > 0) {
                                        var18 = (Pair)var68;
                                        var39 = var11;
                                    }

                                    var5 = var39;
                                    var21 = var18;
                                } while (var75.hasNext());
                            }
                        }

                        var18 = var18;
                        if (var18 == null) {
                            break;
                        }

                        var21 = (OsmRoads.Way)((Pair) var18).getFirst();
                        if (var21 == null) {
                            break;
                        }

                        var68 = CollectionsKt.drop(roadPath$ordered(((Way) var21), var26[0], var26[1], var1), 1);
                        var51 = var51;
                        var20 = (List)var68;
                    }

                    ArrayList var67 = new ArrayList();
                    double var40 = 0.0;
                    double var42 = 100.0;
                    int var49 = 1;
                    var13 = var25.size();
                    var1 = var42;

                    for (String var58 = var24; var49 < var13; var49++) {
                        Object var60 = var25.get(var49 - 1);
                        Intrinsics.checkNotNullExpressionValue(var60, var58);
                        double[] var64 = (double[])var60;
                        var60 = var25.get(var49);
                        Intrinsics.checkNotNullExpressionValue(var60, var58);
                        var60 = (double[])var60;
                        var9 = Math.hypot(((Object[])var60)[0] - var64[0], ((Object[])var60)[1] - var64[1]);
                        if (!(var9 < 0.01)) {
                            while (var40 + var9 >= var1 && var1 <= 1000.0) {
                                var42 = (var1 - var40) / var9;
                                Pair var72 = OsmRoads.INSTANCE
                                    .toLatLon(var64[0] + (((Object[])var60)[0] - var64[0]) * var42, var64[1] + (((Object[])var60)[1] - var64[1]) * var42);
                                var67.add(
                                    TuplesKt.to(
                                        var1,
                                        new double[]{
                                            ((Number)var72.component1()).doubleValue(),
                                            ((Number)var72.component2()).doubleValue(),
                                            Math.atan2(((Object[])var60)[1] - var64[1], ((Object[])var60)[0] - var64[0])
                                        }
                                    )
                                );
                                var1 += 100.0;
                            }

                            var40 += var9;
                        }
                    }

                    ArrayList var59;
                    if (!var67.isEmpty()) {
                        var59 = var67;
                    } else {
                        var59 = null;
                    }

                    return var59;
                }
            }

            return null;
        }
    }

    private static final List<double[]> roadPath$ordered(OsmRoads.Way way, double fromE, double fromN, double dir) {
        Object minElem$iv;
        char c = 1;
        char c2 = 0;
        Iterator<Integer> it = RangesKt.until(0, way.getE().length - 1).iterator();
        double d = 0.0d;
        while (it.hasNext()) {
            int it2 = ((IntIterator) it).nextInt();
            d += ((way.getE()[it2 + 1] - way.getE()[it2]) * Math.cos(dir)) + ((way.getN()[it2 + 1] - way.getN()[it2]) * Math.sin(dir));
        }
        boolean fwd = d >= 0.0d;
        double[] e = way.getE();
        Iterable idx = CollectionsKt.toList(fwd ? ArraysKt.getIndices(e) : RangesKt.reversed(ArraysKt.getIndices(e)));
        Iterable $this$map$iv = idx;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        for (Object item$iv$iv : $this$map$iv) {
            int it3 = ((Number) item$iv$iv).intValue();
            destination$iv$iv.add(new double[]{way.getE()[it3], way.getN()[it3]});
        }
        List pts = (List) destination$iv$iv;
        Iterable $this$minByOrNull$iv = CollectionsKt.getIndices(pts);
        Iterator iterator$iv = $this$minByOrNull$iv.iterator();
        if (iterator$iv.hasNext()) {
            minElem$iv = iterator$iv.next();
            if (iterator$iv.hasNext()) {
                int it4 = ((Number) minElem$iv).intValue();
                double minValue$iv = Math.hypot(((double[]) pts.get(it4))[0] - fromE, ((double[]) pts.get(it4))[1] - fromN);
                while (true) {
                    Object e$iv = iterator$iv.next();
                    int it5 = ((Number) e$iv).intValue();
                    double v$iv = Math.hypot(((double[]) pts.get(it5))[c2] - fromE, ((double[]) pts.get(it5))[c] - fromN);
                    if (Double.compare(minValue$iv, v$iv) > 0) {
                        minElem$iv = e$iv;
                        minValue$iv = v$iv;
                    }
                    if (!iterator$iv.hasNext()) {
                        break;
                    }
                    c2 = 0;
                    c = 1;
                }
            }
        } else {
            minElem$iv = null;
        }
        Integer num = (Integer) minElem$iv;
        int start = num != null ? num.intValue() : 0;
        int s = (start >= pts.size() - 1 || ((((double[]) pts.get(start))[0] - fromE) * Math.cos(dir)) + ((((double[]) pts.get(start))[1] - fromN) * Math.sin(dir)) >= 0.0d) ? start : start + 1;
        return CollectionsKt.plus((Collection) CollectionsKt.listOf(new double[]{fromE, fromN}), (Iterable) CollectionsKt.drop(pts, s));
    }

    /* compiled from: LaneAhead.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final String sideName(int s) {
            return s < 0 ? "Left" : "Right";
        }

        public final String dist(double m, boolean metric) {
            if (metric) {
                if (m < 1000.0d) {
                    return (((int) (Math.max(0.0d, m) / 10)) * 10) + " m";
                }
                StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
                String format = String.format(Locale.US, "%.1f km", Arrays.copyOf(new Object[]{Double.valueOf(m / 1000)}, 1));
                Intrinsics.checkNotNullExpressionValue(format, "format(...)");
                return format;
            }
            if (m * 3.28084d >= 1500.0d) {
                StringCompanionObject stringCompanionObject2 = StringCompanionObject.INSTANCE;
                String format2 = String.format(Locale.US, "%.1f mi", Arrays.copyOf(new Object[]{Double.valueOf(m / 1609.34d)}, 1));
                Intrinsics.checkNotNullExpressionValue(format2, "format(...)");
                return format2;
            }
            return (((int) ((Math.max(0.0d, m) * 3.28084d) / 50)) * 50) + " ft";
        }

        public final Triple<Double, Integer, Integer> findDrop(List<Sample> s, int cur) {
            int intValue;
            boolean z;
            Intrinsics.checkNotNullParameter(s, "s");
            List<Sample> $this$filter$iv = s;
            Collection destination$iv$iv = new ArrayList();
            for (Object element$iv$iv : $this$filter$iv) {
                Sample it = (Sample) element$iv$iv;
                if (it.getLanes() > 0) {
                    destination$iv$iv.add(element$iv$iv);
                }
            }
            List known = (List) destination$iv$iv;
            if (known.size() < 4) {
                return null;
            }
            if (cur > 0) {
                intValue = cur;
            } else {
                Iterable $this$map$iv = CollectionsKt.take(known, 2);
                Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                for (Object item$iv$iv : $this$map$iv) {
                    Sample it2 = (Sample) item$iv$iv;
                    destination$iv$iv2.add(Integer.valueOf(it2.getLanes()));
                }
                Integer num = (Integer) CollectionsKt.maxOrNull(destination$iv$iv2);
                if (num == null) {
                    return null;
                }
                intValue = num.intValue();
            }
            int before = intValue;
            int k = 0;
            int size = known.size();
            while (k < size) {
                int n = ((Sample) known.get(k)).getLanes();
                if (n > before) {
                    before = n;
                } else if (n != before) {
                    Iterable $this$takeWhile$iv = CollectionsKt.drop(known, k);
                    ArrayList list$iv = new ArrayList();
                    for (Object item$iv : $this$takeWhile$iv) {
                        Sample it3 = (Sample) item$iv;
                        Sample it4 = it3.getD() - ((Sample) known.get(k)).getD() <= 300.0d ? 1 : null;
                        if (it4 == null) {
                            break;
                        }
                        list$iv.add(item$iv);
                    }
                    ArrayList run = list$iv;
                    if (run.size() >= 3 && ((Sample) CollectionsKt.last((List) run)).getD() - ((Sample) known.get(k)).getD() >= 200.0d) {
                        ArrayList $this$any$iv = run;
                        if (!($this$any$iv instanceof Collection) || !$this$any$iv.isEmpty()) {
                            Iterator it5 = $this$any$iv.iterator();
                            while (true) {
                                if (!it5.hasNext()) {
                                    z = false;
                                    break;
                                }
                                Object element$iv = it5.next();
                                Sample it6 = (Sample) element$iv;
                                if (it6.getLanes() >= before) {
                                    z = true;
                                    break;
                                }
                            }
                        } else {
                            z = false;
                        }
                        if (!z) {
                            double prevD = k > 0 ? ((Sample) known.get(k - 1)).getD() : 0.0d;
                            if (((Sample) known.get(k)).getD() - prevD > 250.0d) {
                                return null;
                            }
                            Double valueOf = Double.valueOf((((Sample) known.get(k)).getD() + prevD) / 2);
                            Integer valueOf2 = Integer.valueOf(before);
                            Iterator it7 = run.iterator();
                            if (!it7.hasNext()) {
                                throw new NoSuchElementException();
                            }
                            Sample it8 = (Sample) it7.next();
                            Integer valueOf3 = Integer.valueOf(it8.getLanes());
                            while (it7.hasNext()) {
                                Sample it9 = (Sample) it7.next();
                                Integer valueOf4 = Integer.valueOf(it9.getLanes());
                                if (valueOf3.compareTo(valueOf4) < 0) {
                                    valueOf3 = valueOf4;
                                }
                            }
                            return new Triple<>(valueOf, valueOf2, valueOf3);
                        }
                    }
                } else {
                    continue;
                }
                k++;
            }
            return null;
        }
    }
}
