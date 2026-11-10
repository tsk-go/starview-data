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

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries($VALUES);

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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:35:0x011e  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0103  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0106  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void update(long nowMs, final double odo, final double lat, final double lon, final double headingRad, float vEgo, LaneBrain b, LaneBrain.Frame f) {
        Drop d;
        boolean z;
        int i;
        long j;
        String hpmsName;
        boolean inIt;
        double lead;
        Stage stage;
        Function2<? super String, ? super Boolean, Unit> function2;
        String str;
        Intrinsics.checkNotNullParameter(b, "b");
        sideFromCamera(odo, b, f);
        Drop d2 = this.drop;
        if (d2 != null) {
            double m = d2.getAtOdo() - odo;
            if (m < -60.0d) {
                if (d2.getSide() != 0) {
                    b.roadLaneEnded(d2.getSide(), nowMs);
                }
                Function2<? super String, ? super Boolean, Unit> function22 = this.onEvent;
                if (function22 != null) {
                    int from = d2.getFrom();
                    int to = d2.getTo();
                    if (d2.getSide() != 0) {
                        String lowerCase = Companion.sideName(d2.getSide()).toLowerCase(Locale.ROOT);
                        Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
                        str = " (" + lowerCase + " lane ended)";
                    } else {
                        str = "";
                    }
                    function22.invoke("passed: " + from + " → " + to + " lanes" + str, true);
                }
                this.drop = null;
                this.stage = Stage.NONE;
            } else {
                if (d2.getSide() != 0) {
                    Pair<Integer, Double> sideLanes = b.sideLanes(d2.getSide());
                    int n = sideLanes.component1().intValue();
                    double c = sideLanes.component2().doubleValue();
                    if (n == 0 && c >= 0.6d) {
                        inIt = true;
                        lead = Math.max(183.0d, vEgo * 8.0d);
                        if (m < 61.0d || !inIt) {
                            stage = m >= lead ? Stage.PREPARE : Stage.WATCHING;
                        } else {
                            stage = Stage.URGENT;
                        }
                        this.stage = stage;
                        if (this.stage == Stage.PREPARE && !d2.getPrepared()) {
                            d2.setPrepared(true);
                            function2 = this.onEvent;
                            if (function2 != null) {
                                function2.invoke("lane ends ahead, " + Companion.dist(m, false) + ": " + text(odo, false) + (inIt ? " - we're in it" : ""), true);
                            }
                        }
                    }
                }
                inIt = false;
                lead = Math.max(183.0d, vEgo * 8.0d);
                if (m < 61.0d) {
                }
                if (m >= lead) {
                }
                this.stage = stage;
                if (this.stage == Stage.PREPARE) {
                    d2.setPrepared(true);
                    function2 = this.onEvent;
                    if (function2 != null) {
                    }
                }
            }
        }
        Triple<Double, ? extends List<Sample>, Integer> triple = this.result;
        if (triple != null) {
            double lookOdo = triple.component1().doubleValue();
            List samples = triple.component2();
            int curLanes = triple.component3().intValue();
            this.result = null;
            d = d2;
            z = 1;
            i = 0;
            apply(lookOdo, samples, curLanes, odo);
        } else {
            d = d2;
            z = 1;
            i = 0;
        }
        if (vEgo >= 2.0f) {
            if (((Double.isInfinite(lat) || Double.isNaN(lat)) ? i : z) != 0) {
                if (((Double.isInfinite(lon) || Double.isNaN(lon)) ? i : z) != 0) {
                    if (((Double.isInfinite(headingRad) || Double.isNaN(headingRad)) ? i : z) != 0) {
                        if (this.busy) {
                            return;
                        }
                        if (odo - this.lastLookOdo < 150.0d) {
                            j = nowMs;
                            if (j - this.lastLookMs < 20000) {
                                return;
                            }
                        } else {
                            j = nowMs;
                        }
                        this.lastLookOdo = odo;
                        this.lastLookMs = j;
                        LaneBrain.RoadInfo info = b.getInfo();
                        final String prefer = (info == null || (hpmsName = info.getHpmsName()) == null) ? "" : hpmsName;
                        int cur = Intrinsics.areEqual(b.getModel().getSrc(), "HPMS") ? b.getModel().getLanes() : i;
                        Function3 src = this.profileSource;
                        if (src != null) {
                            apply(odo, src.invoke(Double.valueOf(lat), Double.valueOf(lon), Double.valueOf(headingRad)), cur, odo);
                            return;
                        }
                        this.busy = z;
                        final int i2 = cur;
                        this.worker.execute(new Runnable() { // from class: com.starpilot.starview.LaneAhead$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                LaneAhead.update$lambda$4(LaneAhead.this, lat, lon, headingRad, odo, i2, prefer);
                            }
                        });
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
        double dm = found.component1().doubleValue();
        int from = found.component2().intValue();
        int to = found.component3().intValue();
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

    /* JADX WARN: Removed duplicated region for block: B:167:0x0253  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0065  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x009c  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00a7  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00dd  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x00e3  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final void sideFromCamera(double odo, LaneBrain b, LaneBrain.Frame f) {
        double gl;
        float edgeR;
        double gr;
        float yl;
        int i;
        Drop d = this.drop;
        if (f == null) {
            return;
        }
        float edgeL = f.getEdgeL();
        double d2 = Double.NaN;
        if ((Float.isInfinite(edgeL) || Float.isNaN(edgeL)) ? false : true) {
            float yl2 = f.getYL();
            if (((Float.isInfinite(yl2) || Float.isNaN(yl2)) ? false : true) && f.getPL() > 0.4f) {
                gl = f.getEdgeL() - Math.abs(f.getYL());
                edgeR = f.getEdgeR();
                if (Float.isInfinite(edgeR) && !Float.isNaN(edgeR)) {
                    float yr = f.getYR();
                    if (((Float.isInfinite(yr) || Float.isNaN(yr)) ? false : true) && f.getPR() > 0.4f) {
                        gr = f.getEdgeR() - Math.abs(f.getYR());
                        yl = f.getYL();
                        if (Float.isInfinite(yl) && !Float.isNaN(yl)) {
                            float yr2 = f.getYR();
                            if (((Float.isInfinite(yr2) || Float.isNaN(yr2)) ? false : true) && f.getPL() > 0.4f && f.getPR() > 0.4f) {
                                d2 = f.getYR() - f.getYL();
                            }
                        }
                        double w = d2;
                        if (f.getOpChange() != 0) {
                            this.gaps.clear();
                            return;
                        }
                        if (this.gaps.isEmpty() || odo - this.gaps.last()[0] >= 5.0d) {
                            this.gaps.addLast(new double[]{odo, gl, gr, w, f.getYL(), f.getYR()});
                        }
                        while (!this.gaps.isEmpty() && odo - this.gaps.first()[0] > 220.0d) {
                            this.gaps.removeFirst();
                        }
                        if (d == null || d.getSide() != 0 || d.getAtOdo() - odo > 450.0d) {
                            return;
                        }
                        if (this.gaps.size() < 20) {
                            return;
                        }
                        double lw = b.getLaneW();
                        double dl = sideFromCamera$trend(this, 1);
                        double dr = sideFromCamera$trend(this, 2);
                        double dw = sideFromCamera$trend(this, 3);
                        double dyl = sideFromCamera$trend(this, 4);
                        double dyr = sideFromCamera$trend(this, 5);
                        if (!((Double.isInfinite(dr) || Double.isNaN(dr)) ? false : true) || dr >= lw * (-0.6d) || dl < lw * (-0.3d)) {
                            if (!((Double.isInfinite(dl) || Double.isNaN(dl)) ? false : true) || dl >= lw * (-0.6d) || dr < (-0.3d) * lw) {
                                if (((Double.isInfinite(dw) || Double.isNaN(dw)) ? false : true) && dw < -0.5d) {
                                    if ((Double.isInfinite(dyr) || Double.isNaN(dyr)) ? false : true) {
                                        if ((Double.isInfinite(dyl) || Double.isNaN(dyl)) ? false : true) {
                                            if ((-dyr) > Math.abs(dyl) + 0.3d) {
                                                i = 1;
                                            }
                                            if ((Double.isInfinite(dw) && !Double.isNaN(dw)) && dw < -0.5d) {
                                                if (Double.isInfinite(dyr) && !Double.isNaN(dyr)) {
                                                    if (((Double.isInfinite(dyl) || Double.isNaN(dyl)) ? false : true) && dyl > Math.abs(dyr) + 0.3d) {
                                                        i = -1;
                                                    }
                                                }
                                            }
                                            i = 0;
                                        }
                                    }
                                }
                                if (Double.isInfinite(dw) && !Double.isNaN(dw)) {
                                    if (Double.isInfinite(dyr) && !Double.isNaN(dyr)) {
                                    }
                                }
                                i = 0;
                            } else {
                                i = -1;
                            }
                        } else {
                            i = 1;
                        }
                        int side = i;
                        if (side != 0) {
                            double it = side > 0 ? dr : dl;
                            boolean edge = (!Double.isInfinite(it) && !Double.isNaN(it)) && it < lw * (-0.6d);
                            d.setSide(side);
                            d.setSideWhy(edge ? "road edge closing in" : "our lane narrowing");
                            Function2<? super String, ? super Boolean, Unit> function2 = this.onEvent;
                            if (function2 != null) {
                                String lowerCase = Companion.sideName(side).toLowerCase(Locale.ROOT);
                                Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
                                function2.invoke("the " + lowerCase + " lane ends (camera: " + d.getSideWhy() + ")", true);
                                return;
                            }
                            return;
                        }
                        return;
                    }
                }
                gr = Double.NaN;
                yl = f.getYL();
                if (Float.isInfinite(yl) && !Float.isNaN(yl)) {
                }
                double w2 = d2;
                if (f.getOpChange() != 0) {
                }
            }
        }
        gl = Double.NaN;
        edgeR = f.getEdgeR();
        if (Float.isInfinite(edgeR) && !Float.isNaN(edgeR)) {
        }
        gr = Double.NaN;
        yl = f.getYL();
        if (Float.isInfinite(yl) && !Float.isNaN(yl)) {
        }
        double w22 = d2;
        if (f.getOpChange() != 0) {
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

    /* JADX WARN: Code restructure failed: missing block: B:104:0x0371, code lost:
    
        r12 = r39;
     */
    /* JADX WARN: Removed duplicated region for block: B:49:0x01b6  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x01b9 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:74:0x02d5  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x02d9 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final List<Pair<Double, double[]>> roadPath(double lat, double lon, double headingRad) {
        String str;
        String str2;
        int i;
        double dir;
        double e;
        Object minElem$iv;
        OsmRoads.Way next;
        Iterable $this$mapNotNull$iv;
        String str3;
        Pair pair;
        int i0;
        HashSet seen;
        Iterable $this$filter$iv;
        int $i$f$filter;
        int $i$f$filter2;
        OsmRoads.Way way;
        if (Double.isNaN(OsmRoads.INSTANCE.getLat0())) {
            return null;
        }
        Pair<Double, Double> local = OsmRoads.INSTANCE.toLocal(lat, lon);
        double e2 = local.component1().doubleValue();
        double n = local.component2().doubleValue();
        OsmRoads.Match mt = OsmRoads.INSTANCE.matchSeg(e2, n, headingRad);
        if (mt == null) {
            return null;
        }
        OsmRoads.Match it = mt.getDist() < 20.0d ? 1 : null;
        if (it == null) {
            mt = null;
        }
        if (mt == null) {
            return null;
        }
        OsmRoads.Way way2 = mt.getWay();
        if (StringsKt.endsWith$default(way2.getKind(), "_link", false, 2, (Object) null)) {
            return null;
        }
        ArrayList line = new ArrayList();
        double dir2 = headingRad;
        String str4 = "_link";
        char c = 0;
        List pts = roadPath$ordered(way2, e2, n, dir2);
        HashSet seen2 = new HashSet();
        double total = 0.0d;
        while (true) {
            seen2.add(Long.valueOf(way2.getId()));
            for (double[] p : pts) {
                if (line.isEmpty()) {
                    way = way2;
                } else {
                    way = way2;
                    total += Math.hypot(p[c] - ((double[]) CollectionsKt.last((List) line))[c], p[1] - ((double[]) CollectionsKt.last((List) line))[1]);
                }
                line.add(p);
                way2 = way;
                c = 0;
            }
            OsmRoads.Way way3 = way2;
            str = "get(...)";
            if (total <= 1050.0d && line.size() >= 2) {
                double[] end = (double[]) CollectionsKt.last((List) line);
                Object obj = line.get(line.size() - 2);
                Intrinsics.checkNotNullExpressionValue(obj, "get(...)");
                double[] prev = (double[]) obj;
                List pts2 = pts;
                double d = end[1] - prev[1];
                double total2 = total;
                double total3 = end[0] - prev[0];
                double dir3 = Math.atan2(d, total3);
                Iterable $this$filter$iv2 = OsmRoads.INSTANCE.near(end[0], end[1], 15.0d);
                int $i$f$filter3 = 0;
                Collection destination$iv$iv = new ArrayList();
                for (Object element$iv$iv : $this$filter$iv2) {
                    OsmRoads.Way it2 = (OsmRoads.Way) element$iv$iv;
                    double[] prev2 = prev;
                    if (seen2.contains(Long.valueOf(it2.getId()))) {
                        seen = seen2;
                        $this$filter$iv = $this$filter$iv2;
                        $i$f$filter = $i$f$filter3;
                    } else {
                        seen = seen2;
                        $this$filter$iv = $this$filter$iv2;
                        $i$f$filter = $i$f$filter3;
                        if (!StringsKt.endsWith$default(it2.getKind(), str4, false, 2, (Object) null)) {
                            if (way3.getName().length() > 0 ? OsmRoads.INSTANCE.sameStreet(it2.getName(), way3.getName()) : Intrinsics.areEqual(it2.getKind(), way3.getKind())) {
                                $i$f$filter2 = 1;
                                if ($i$f$filter2 == 0) {
                                    destination$iv$iv.add(element$iv$iv);
                                }
                                $this$filter$iv2 = $this$filter$iv;
                                $i$f$filter3 = $i$f$filter;
                                prev = prev2;
                                seen2 = seen;
                            }
                        }
                    }
                    $i$f$filter2 = 0;
                    if ($i$f$filter2 == 0) {
                    }
                    $this$filter$iv2 = $this$filter$iv;
                    $i$f$filter3 = $i$f$filter;
                    prev = prev2;
                    seen2 = seen;
                }
                HashSet seen3 = seen2;
                Iterable $this$mapNotNull$iv2 = (List) destination$iv$iv;
                Collection destination$iv$iv2 = new ArrayList();
                Iterable $this$mapNotNullTo$iv$iv = $this$mapNotNull$iv2;
                for (Object element$iv$iv$iv : $this$mapNotNullTo$iv$iv) {
                    OsmRoads.Way c2 = (OsmRoads.Way) element$iv$iv$iv;
                    List pts3 = pts2;
                    Iterable $this$mapNotNullTo$iv$iv2 = $this$mapNotNullTo$iv$iv;
                    double dir4 = dir3;
                    if (Math.hypot(c2.getE()[0] - end[0], c2.getN()[0] - end[1]) < 15.0d) {
                        $this$mapNotNull$iv = $this$mapNotNull$iv2;
                        i0 = 0;
                    } else {
                        $this$mapNotNull$iv = $this$mapNotNull$iv2;
                        if (Math.hypot(c2.getE()[c2.getE().length - 1] - end[0], c2.getN()[c2.getN().length - 1] - end[1]) < 15.0d) {
                            i0 = c2.getE().length - 1;
                        } else {
                            str3 = str4;
                            pair = null;
                            if (pair != null) {
                                destination$iv$iv2.add(pair);
                            }
                            $this$mapNotNull$iv2 = $this$mapNotNull$iv;
                            pts2 = pts3;
                            $this$mapNotNullTo$iv$iv = $this$mapNotNullTo$iv$iv2;
                            dir3 = dir4;
                            str4 = str3;
                        }
                    }
                    if (c2.getOneway() != 0) {
                        if ((c2.getOneway() > 0) != (i0 == 0)) {
                            str3 = str4;
                            pair = null;
                            if (pair != null) {
                            }
                            $this$mapNotNull$iv2 = $this$mapNotNull$iv;
                            pts2 = pts3;
                            $this$mapNotNullTo$iv$iv = $this$mapNotNullTo$iv$iv2;
                            dir3 = dir4;
                            str4 = str3;
                        }
                    }
                    int i1 = i0 == 0 ? 1 : c2.getE().length - 2;
                    double d2 = Math.atan2(c2.getN()[i1] - c2.getN()[i0], c2.getE()[i1] - c2.getE()[i0]);
                    double bend = Math.abs(d2 - dir4);
                    if (bend > 3.141592653589793d) {
                        bend = 6.283185307179586d - bend;
                    }
                    str3 = str4;
                    pair = bend > Math.toRadians(35.0d) ? null : TuplesKt.to(c2, Double.valueOf(bend));
                    if (pair != null) {
                    }
                    $this$mapNotNull$iv2 = $this$mapNotNull$iv;
                    pts2 = pts3;
                    $this$mapNotNullTo$iv$iv = $this$mapNotNullTo$iv$iv2;
                    dir3 = dir4;
                    str4 = str3;
                }
                String str5 = str4;
                double dir5 = dir3;
                Iterable $this$minByOrNull$iv = (List) destination$iv$iv2;
                Iterator iterator$iv = $this$minByOrNull$iv.iterator();
                if (iterator$iv.hasNext()) {
                    minElem$iv = iterator$iv.next();
                    if (iterator$iv.hasNext()) {
                        Pair it3 = (Pair) minElem$iv;
                        double minValue$iv = ((Number) it3.getSecond()).doubleValue();
                        do {
                            Object e$iv = iterator$iv.next();
                            Pair it4 = (Pair) e$iv;
                            double v$iv = ((Number) it4.getSecond()).doubleValue();
                            if (Double.compare(minValue$iv, v$iv) > 0) {
                                minElem$iv = e$iv;
                                minValue$iv = v$iv;
                            }
                        } while (iterator$iv.hasNext());
                    }
                } else {
                    minElem$iv = null;
                }
                Pair pair2 = (Pair) minElem$iv;
                if (pair2 == null || (next = (OsmRoads.Way) pair2.getFirst()) == null) {
                    break;
                }
                pts = CollectionsKt.drop(roadPath$ordered(next, end[0], end[1], dir5), 1);
                total = total2;
                way2 = next;
                seen2 = seen3;
                dir2 = dir5;
                str4 = str5;
                c = 0;
            }
        }
        ArrayList out = new ArrayList();
        double seg = 0.0d;
        double want = 100.0d;
        int k = 1;
        int size = line.size();
        while (k < size) {
            Object obj2 = line.get(k - 1);
            Intrinsics.checkNotNullExpressionValue(obj2, str);
            double[] a = (double[]) obj2;
            Object obj3 = line.get(k);
            Intrinsics.checkNotNullExpressionValue(obj3, str);
            double[] bb = (double[]) obj3;
            double acc = seg;
            double want2 = want;
            double seg2 = Math.hypot(bb[0] - a[0], bb[1] - a[1]);
            if (seg2 >= 0.01d) {
                want = want2;
                while (acc + seg2 >= want && want <= 1000.0d) {
                    double t = (want - acc) / seg2;
                    String str6 = str;
                    int i2 = size;
                    double dir6 = dir2;
                    Pair<Double, Double> latLon = OsmRoads.INSTANCE.toLatLon(a[0] + ((bb[0] - a[0]) * t), a[1] + ((bb[1] - a[1]) * t));
                    double plat = latLon.component1().doubleValue();
                    double plon = latLon.component2().doubleValue();
                    out.add(TuplesKt.to(Double.valueOf(want), new double[]{plat, plon, Math.atan2(bb[1] - a[1], bb[0] - a[0])}));
                    want += 100.0d;
                    str = str6;
                    size = i2;
                    a = a;
                    dir2 = dir6;
                    e2 = e2;
                    bb = bb;
                }
                str2 = str;
                i = size;
                dir = dir2;
                e = e2;
                seg = acc + seg2;
            } else {
                str2 = str;
                i = size;
                dir = dir2;
                e = e2;
                seg = acc;
                want = want2;
            }
            k++;
            str = str2;
            size = i;
            dir2 = dir;
            e2 = e;
        }
        return !out.isEmpty() ? out : null;
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
