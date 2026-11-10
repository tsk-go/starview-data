package com.starpilot.starview;

import com.starpilot.starview.Hpms;
import com.starpilot.starview.LaneBrain;
import com.starpilot.starview.LaneMarks;
import com.starpilot.starview.LaneRamps;
import com.starpilot.starview.Nav;
import com.starpilot.starview.OsmRoads;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Pair;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import kotlin.jvm.internal.Ref.BooleanRef;
import kotlin.jvm.internal.Ref.IntRef;

/* compiled from: LaneFeed.kt */
public final class LaneFeed {
    public static final Companion Companion = new Companion(null);
    private volatile Hpms.Match hpms;
    private LaneBrain.RoadInfo info;
    private long lastRoadMs;
    private int pendingEntry;
    private LaneBrain.Road road;
    private OsmRoads.Way way;
    private long lastFrameId = -1;
    private double lastYaw = Double.NaN;
    private volatile List<LaneRamps.Ramp> ramps = CollectionsKt.emptyList();

    public final List<LaneRamps.Ramp> getRamps() {
        return this.ramps;
    }

    public final Hpms.Match getHpms() {
        return this.hpms;
    }

    /* VF: from Vineflower (jadx: Removed duplicated region;incorrect) */
    public final LaneBrain.Frame frame(Telemetry var1, LaneMarks var2, long var3) {
        Intrinsics.checkNotNullParameter(var1, "t");
        ModelSnapshot var37 = var1.getModel();
        if (var37 == null) {
            return null;
        } else if (var37.getFrameId() == this.lastFrameId) {
            return null;
        } else {
            this.lastFrameId = var37.getFrameId();
            float var9 = var1.getPoseYawZ();
            boolean var27 = Float.isInfinite(var9);
            int var22 = 0;
            boolean var21;
            if (!var27 && !Float.isNaN(var9)) {
                var21 = true;
            } else {
                var21 = false;
            }

            if (var21 && System.nanoTime() - var1.getPoseAtNs() < 500000000L) {
                var21 = (boolean)true;
            } else {
                var21 = (boolean)false;
            }

            if (var21) {
                var9 = var1.getPoseYawZ() * Odometry.INSTANCE.getGyroSign();
            } else {
                if (var1.getSteerRatioLive() > 5.0F) {
                    var9 = var1.getSteerRatioLive();
                } else {
                    var9 = 15.5F;
                }

                var9 = (float)(var1.getVEgo() * Math.tan(Math.toRadians(var1.getSteeringAngleDeg() - var1.getAngleOffsetDeg()) / var9) / 3.03);
            }

            if (var3 - this.lastRoadMs > 500L) {
                this.lastRoadMs = var3;
                this.matchRoad(var1);
            }

            int var25;
            LaneMarks.Mark[] var34;
            label156: {
                var25 = this.pendingEntry;
                this.pendingEntry = 0;
                if (var2 != null) {
                    if (System.nanoTime() - var2.getLastNs() < 1000000000L) {
                        var21 = (boolean)true;
                    } else {
                        var21 = (boolean)false;
                    }

                    if (!var21) {
                        var2 = null;
                    }

                    if (var2 != null) {
                        var34 = var2.getMarks();
                        break label156;
                    }
                }

                var34 = null;
            }

            IntRef var32 = new IntRef();
            IntRef var33 = new IntRef();
            BooleanRef var35 = new BooleanRef();
            BooleanRef var42;
            if (var1.getVEgo() > 3.0F) {
                RadarTrack[] var36 = var1.getRadar();
                int var23 = var36.length;
                byte var24 = 0;
                IntRef var39 = var32;
                var32 = var33;
                BooleanRef var60 = var35;
                RadarTrack[] var62 = var36;

                for (int var51 = var24; var51 < var23; var51++) {
                    RadarTrack var67 = var62[var51];
                    if (var67.getMeasured()) {
                        frame$place(var39, var32, var60, var37, var67.getDRel() + 2.3F, -var67.getYRel(), var1.getVEgo() + var67.getVRel());
                    }
                }

                IntRef var63 = var39;
                Lead[] var40 = new Lead[2];
                var40[var22] = var1.getLeadLeft();
                Lead var68 = var1.getLeadRight();
                var21 = true;
                var40[1] = var68;

                for (Lead var69 : CollectionsKt.listOf(var40)) {
                    if (var69.getStatus()) {
                        frame$place(var63, var32, var60, var37, var69.getDRel() + 2.3F, -var69.getYRel(), var69.getVLead());
                    }
                }

                var42 = var60;
                var33 = var32;
                var32 = var63;
            } else {
                var42 = var35;
                var21 = true;
            }

            LaneFeed.Rtk var70 = this.rtkOffset(var3, var1.getVEgo());
            float var16 = var1.getVEgo();
            float var14 = frame$lineAt(var37, ((var21) ? 1 : 0), 3.0F);
            float var15 = frame$lineAt(var37, 2, 3.0F);
            float[] var64 = var37.getLaneProb();
            if (var21 < var64.length) {
                var22 = ((var21) ? 1 : 0);
            } else {
                var22 = (byte)0;
            }

            float var10;
            if (var22 != 0) {
                var10 = var64[((var21) ? 1 : 0)];
            } else {
                var10 = 0.0F;
            }

            float[] var65 = var37.getLaneProb();
            if (2 < var65.length) {
                var22 = ((var21) ? 1 : 0);
            } else {
                var22 = (byte)0;
            }

            float var11;
            if (var22 != 0) {
                var11 = var65[2];
            } else {
                var11 = 0.0F;
            }

            float var18 = frame$edge(var37, 0);
            float var20 = frame$edge(var37, ((var21) ? 1 : 0));
            LaneMarks.Mark var66;
            if (var34 != null) {
                var66 = ArraysKt.getOrNull(var34, var21);
            } else {
                var66 = null;
            }

            LaneMarks.Mark var61;
            if (var34 != null) {
                var61 = ArraysKt.getOrNull(var34, 2);
            } else {
                var61 = null;
            }

            LaneBrain.Road var38 = this.road;
            if (var37.getLaneChanging()) {
                var22 = var37.getLaneChangeDir();
            } else {
                var22 = 0;
            }

            if (!var37.getLaneChanging() || !var37.getLaneChangeFinishing()) {
                var21 = false;
            }

            int var57 = var32.element;
            int var26 = var33.element;
            var27 = var42.element;
            float var19 = frame$lineAt(var37, 0, 3.0F);
            float var17 = frame$lineAt(var37, 3, 3.0F);
            float[] var43 = var37.getLaneProb();
            boolean var55;
            if (var43.length > 0) {
                var55 = true;
            } else {
                var55 = false;
            }

            float var12;
            if (var55) {
                var12 = var43[0];
            } else {
                var12 = 0.0F;
            }

            float[] var44 = var37.getLaneProb();
            if (3 < var44.length) {
                var55 = true;
            } else {
                var55 = false;
            }

            float var13;
            if (var55) {
                var13 = var44[3];
            } else {
                var13 = 0.0F;
            }

            boolean var29 = var1.getLeftBlindspot();
            boolean var28 = var1.getRightBlindspot();
            double var7 = Double.NaN;
            double var5;
            if (var70 != null) {
                var5 = var70.getOffset();
            } else {
                var5 = Double.NaN;
            }

            if (var70 != null) {
                var7 = var70.getSigma();
            }

            long var30;
            if (var70 != null) {
                var30 = var70.getWay();
            } else {
                var30 = 0L;
            }

            return new LaneBrain.Frame(
                var3,
                var16,
                var9,
                var14,
                var15,
                var10,
                var11,
                var18,
                var20,
                var66,
                var61,
                var38,
                var25,
                var22,
                (boolean)var21,
                var57,
                var26,
                var27,
                var19,
                var17,
                var12,
                var13,
                var29,
                var28,
                var5,
                var7,
                var30,
                var37.getLaneChangeProb(),
                this.info
            );
        }
    }

    private static final float frame$lineAt(ModelSnapshot m, int i, float x) {
        if (i >= m.getLaneX().length || i >= m.getLaneY().length) {
            return Float.NaN;
        }
        float[] xs = m.getLaneX()[i];
        float[] ys = m.getLaneY()[i];
        if (xs.length < 2) {
            return Float.NaN;
        }
        int j = 1;
        while (j < xs.length - 1 && xs[j] < x) {
            j++;
        }
        float f = RangesKt.coerceIn((x - xs[j - 1]) / Math.max(0.01f, xs[j] - xs[j - 1]), 0.0f, 1.0f);
        return ys[j - 1] + ((ys[j] - ys[j - 1]) * f);
    }

    private static final float frame$edge(ModelSnapshot m, int e) {
        if (e < m.getEdgeX().length) {
            float[] edgeStd = m.getEdgeStd();
            if ((e >= 0 && e < edgeStd.length ? edgeStd[e] : 9.0f) <= 1.5f) {
                float[] xs = m.getEdgeX()[e];
                float[] ys = m.getEdgeY()[e];
                ArrayList l = new ArrayList();
                int length = xs.length;
                for (int i = 0; i < length; i++) {
                    if (xs[i] >= 3.0f) {
                        if (xs[i] > 15.0f) {
                            break;
                        }
                        l.add(Float.valueOf(Math.abs(ys[i])));
                    }
                }
                if (l.isEmpty()) {
                    return Float.NaN;
                }
                CollectionsKt.sort(l);
                Object obj = l.get(l.size() / 2);
                Float it = (Float) obj;
                Intrinsics.checkNotNull(it);
                float floatValue = it.floatValue();
                if (!(0.4f <= floatValue && floatValue <= 25.0f)) {
                    obj = null;
                }
                Float f = (Float) obj;
                if (f != null) {
                    return f.floatValue();
                }
                return Float.NaN;
            }
        }
        return Float.NaN;
    }

    private static final void frame$place(Ref.IntRef cl, Ref.IntRef cr, Ref.BooleanRef onL, ModelSnapshot m, float d, float yRight, float vAbs) {
        if (d < 5.0f || d > 70.0f) {
            return;
        }
        float a = frame$lineAt(m, 1, d);
        float b = frame$lineAt(m, 2, d);
        boolean z = false;
        if ((Float.isInfinite(a) || Float.isNaN(a)) ? false : true) {
            if (!((Float.isInfinite(b) || Float.isNaN(b)) ? false : true)) {
                return;
            }
            float w = b - a;
            if (2.5f <= w && w <= 4.6f) {
                z = true;
            }
            if (z) {
                float k = (yRight - ((a + b) / 2.0f)) / w;
                int kr = Math.round(k);
                if (Math.abs(k - kr) > 0.3f || kr == 0) {
                    return;
                }
                if (vAbs > 3.0f) {
                    if (kr < 0) {
                        cl.element = Math.max(cl.element, -kr);
                        return;
                    } else {
                        cr.element = Math.max(cr.element, kr);
                        return;
                    }
                }
                if (vAbs < -3.0f && kr == -1) {
                    onL.element = true;
                }
            }
        }
    }

    /* compiled from: LaneFeed.kt */
    public static final class Rtk {
        private final double offset;
        private final double sigma;
        private final long way;

        public Rtk(double offset, double sigma, long way) {
            this.offset = offset;
            this.sigma = sigma;
            this.way = way;
        }

        public final double getOffset() {
            return this.offset;
        }

        public final double getSigma() {
            return this.sigma;
        }

        public final long getWay() {
            return this.way;
        }
    }

    private final Rtk rtkOffset(long nowMs, double vEgo) {
        double[] pos;
        if (Double.isNaN(OsmRoads.INSTANCE.getLat0()) || !RtkPosition.INSTANCE.fast(nowMs) || (pos = RtkPosition.INSTANCE.now(nowMs, vEgo)) == null) {
            return null;
        }
        double h = RtkPosition.INSTANCE.getHeading();
        if (((Double.isInfinite(h) || Double.isNaN(h)) ? false : true) && vEgo >= 3.0d) {
            double yaw = 1.5707963267948966d - Math.toRadians(h);
            Pair<Double, Double> local = OsmRoads.INSTANCE.toLocal(pos[0], pos[1]);
            double e = local.component1().doubleValue();
            double n = local.component2().doubleValue();
            OsmRoads.Match mt = OsmRoads.INSTANCE.matchSeg(e, n, yaw);
            if (mt != null) {
                if (!(mt.getDist() < 15.0d)) {
                    mt = null;
                }
                if (mt == null) {
                    return null;
                }
                return new Rtk(mt.rightOf(yaw), Math.max(0.2d, RtkPosition.INSTANCE.getAccM()), mt.getWay().getId());
            }
            return null;
        }
        return null;
    }

    private final LaneBrain.Road mapdRoad(Telemetry t) {
        int ourWay;
        if (System.nanoTime() - t.getMapdAtNs() > 3000000000L || !t.getMapdMatched()) {
            return null;
        }
        String mapdWayName = t.getMapdWayName();
        if (mapdWayName.length() == 0) {
            mapdWayName = t.getRoadName();
        }
        String name = mapdWayName;
        boolean oneway = t.getMapdOneWay();
        boolean hw = t.getMapdFreeway();
        if (t.getMapdLanes() <= 0) {
            ourWay = 0;
        } else {
            int mapdLanes = t.getMapdLanes();
            if (!oneway) {
                mapdLanes = Math.max(1, mapdLanes / 2);
            }
            ourWay = mapdLanes;
        }
        return new LaneBrain.Road(("mapd:" + name + "|" + t.getMapdWayRef()).hashCode(), name, hw ? "motorway" : "road", oneway || hw, hw, ourWay);
    }

    private final void matchRoad(Telemetry t) {
        matchOwnMap();
        if (this.road == null) {
            this.road = mapdRoad(t);
        }
        this.info = roadInfo();
    }

    /* VF: from Vineflower (jadx: Code restructure failed;Removed duplicated region;incorrect) */
    private final void matchOwnMap() {
        Object var30 = null; Boolean var31 = null; Object var32 = null; Object var33 = null; Object var34 = null; Object var37 = null; // declarations Vineflower left out
        OsmRoads.Fix var18 = OsmRoads.INSTANCE.getLastFix();
        if (var18 != null && !Double.isNaN(OsmRoads.INSTANCE.getLat0()) && System.nanoTime() - var18.getTNs() <= 3000000000L) {
            if (var18.getSpeed() > 3.0F) {
                float var11 = var18.getBearingDeg();
                boolean var12;
                if (!Float.isInfinite(var11) && !Float.isNaN(var11)) {
                    var12 = true;
                } else {
                    var12 = false;
                }

                if (var12) {
                    this.lastYaw = (Math.PI / 2) - Math.toRadians(var18.getBearingDeg());
                }
            }

            double var7 = this.lastYaw;
            if (!Double.isNaN(var7)) {
                double var1;
                double var9;
                OsmRoads.Match var29;
                label128: {
                    Pair var16 = OsmRoads.INSTANCE.toLocal(var18.getLat(), var18.getLon());
                    var1 = ((Number)var16.component1()).doubleValue();
                    var9 = ((Number)var16.component2()).doubleValue();
                    var29 = OsmRoads.INSTANCE.matchSeg(var1, var9, var7);
                    if (var29 != null) {
                        boolean var26;
                        if (var29.getDist() < 15.0) {
                            var26 = true;
                        } else {
                            var26 = false;
                        }

                        if (var26) {
                            break label128;
                        }
                    }

                    var29 = null;
                }

                OsmRoads.Way var17;
                if (var29 != null) {
                    var17 = var29.getWay();
                } else {
                    var17 = null;
                }

                label121: {
                    label143: {
                        double var3;
                        double var5;
                        Hpms var19;
                        try {
                            var19 = Hpms.INSTANCE;
                            var3 = var18.getLat();
                            var5 = var18.getLon();
                        } catch (Exception var25) {
                            break label143;
                        }

                        label116: {
                            if (var17 != null) {
                                try {
                                    var30 = var17.getKind();
                                } catch (Exception var24) {
                                    break label143;
                                }

                                if (var30 != null) {
                                    boolean var13;
                                    try {
                                        var13 = StringsKt.endsWith(var30, "_link", false);
                                    } catch (Exception var23) {
                                        break label143;
                                    }

                                    var31 = var13;
                                    break label116;
                                }
                            }

                            var31 = null;
                        }

                        try {
                            var32 = Hpms.match$default(var19, var3, var5, var7, var31, 0.0, null, 48, null);
                            break label121;
                        } catch (Exception var22) {
                        }
                    }

                    var32 = null;
                }

                this.hpms = var32;
                if (var17 == null) {
                    this.road = null;
                    this.way = null;
                    this.ramps = CollectionsKt.emptyList();
                } else {
                    label101:
                    if (var17.getHighway() && !StringsKt.endsWith(var17.getKind(), "_link", false)) {
                        label97: {
                            try {
                                var37 = LaneRamps.INSTANCE;
                                var34 = OsmRoads.INSTANCE;
                            } catch (Exception var21) {
                                break label97;
                            }

                            try {
                                var33 = var37.scan(var34.near(var1, var9, 450.0), var17, var1, var9, var7);
                                break label101;
                            } catch (Exception var20) {
                            }
                        }

                        var33 = CollectionsKt.emptyList();
                    } else {
                        var33 = CollectionsKt.emptyList();
                    }

                    this.ramps = var33;
                    OsmRoads.Way var35 = this.way;
                    if (var35 != null
                        && var35.getId() != var17.getId()
                        && StringsKt.endsWith(var35.getKind(), "_link", false)
                        && var17.getHighway()
                        && !StringsKt.endsWith(var17.getKind(), "_link", false)) {
                        this.pendingEntry = Companion.rampSide(var35, var17, var1, var9, var7);
                    }

                    this.way = var17;
                    int var27;
                    if (var17.getLanes() <= 0) {
                        var27 = 0;
                    } else if (var17.getOneway() != 0) {
                        var27 = var17.getLanes();
                    } else {
                        var27 = Math.max(1, var17.getLanes() / 2);
                    }

                    long var14 = var17.getId();
                    String var36 = var17.getName();
                    String var38 = var17.getKind();
                    boolean var28;
                    if (var17.getOneway() != 0) {
                        var28 = true;
                    } else {
                        var28 = false;
                    }

                    this.road = new LaneBrain.Road(var14, var36, var38, var28, var17.getHighway(), var27);
                }
            }
        } else {
            this.road = null;
            this.way = null;
            this.ramps = CollectionsKt.emptyList();
            this.hpms = null;
        }
    }

    private final LaneBrain.RoadInfo roadInfo() {
        Pair nl;
        String kL;
        Hpms.Section s;
        Hpms.Section s2;
        Hpms.Section s3;
        Hpms.Section s4;
        Hpms.Section s5;
        String name;
        Hpms.Section s6;
        int count$iv;
        int straight;
        boolean z;
        Hpms.Match h = this.hpms;
        ArrayList why = new ArrayList();
        String kR = "";
        String kL2 = "";
        int xR = 0;
        int xL = 0;
        for (LaneRamps.Ramp r : this.ramps) {
            if (r.getSide() < 0) {
                xL = 1;
                kL2 = r.getKind();
            } else {
                xR = 1;
                kR = r.getKind();
            }
            why.add("map: " + r.text());
        }
        int straight2 = 0;
        double navDist = Double.NaN;
        try {
            nl = Nav.INSTANCE.nextLanes();
        } catch (Exception e) {
            nl = null;
        }
        if (nl == null || nl.getSecond().doubleValue() >= 600.0d) {
            kL = kL2;
        } else {
            Nav.Step step = nl.component1();
            double d = nl.component2().doubleValue();
            navDist = d;
            Iterable $this$map$iv = step.getLanes();
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            for (Object item$iv$iv : $this$map$iv) {
                Nav.Lane l = (Nav.Lane) item$iv$iv;
                Iterable $this$any$iv = l.getArrows();
                String kL3 = kL2;
                if (($this$any$iv instanceof Collection) && ((Collection) $this$any$iv).isEmpty()) {
                    z = false;
                } else {
                    Iterator it = $this$any$iv.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            z = false;
                            break;
                        }
                        Object element$iv = it.next();
                        Iterable $this$any$iv2 = $this$any$iv;
                        String it2 = (String) element$iv;
                        Iterator it3 = it;
                        if (Intrinsics.areEqual(it2, "straight") || Intrinsics.areEqual(it2, "none")) {
                            z = true;
                            break;
                        }
                        $this$any$iv = $this$any$iv2;
                        it = it3;
                    }
                }
                destination$iv$iv.add(Boolean.valueOf(z));
                kL2 = kL3;
            }
            String kL4 = kL2;
            Object item$iv = (List) destination$iv$iv;
            Iterable $this$count$iv = (Iterable) item$iv;
            if (($this$count$iv instanceof Collection) && ((Collection) $this$count$iv).isEmpty()) {
                count$iv = 0;
            } else {
                count$iv = 0;
                for (Object element$iv2 : $this$count$iv) {
                    boolean it4 = ((Boolean) element$iv2).booleanValue();
                    if (it4 && (count$iv = count$iv + 1) < 0) {
                        CollectionsKt.throwCountOverflow();
                    }
                }
            }
            int straight3 = count$iv;
            if (!Intrinsics.areEqual(step.getType(), "off ramp") || d >= 320.0d || straight3 <= 0) {
                straight2 = straight3;
                kL = kL4;
            } else {
                Iterable $this$takeWhile$iv = CollectionsKt.reversed((Iterable) item$iv);
                ArrayList list$iv = new ArrayList();
                for (Object item$iv2 : $this$takeWhile$iv) {
                    Iterable $this$takeWhile$iv2 = $this$takeWhile$iv;
                    boolean it5 = ((Boolean) item$iv2).booleanValue();
                    if (it5) {
                        break;
                    }
                    list$iv.add(item$iv2);
                    $this$takeWhile$iv = $this$takeWhile$iv2;
                }
                int right = list$iv.size();
                ArrayList list$iv2 = new ArrayList();
                for (Object item$iv3 : (Iterable) item$iv) {
                    Object goesOn = item$iv;
                    boolean it6 = ((Boolean) item$iv3).booleanValue();
                    if (it6) {
                        break;
                    }
                    list$iv2.add(item$iv3);
                    item$iv = goesOn;
                }
                int left = list$iv2.size();
                if (right > xR) {
                    kR = "exit";
                    straight = straight3;
                    why.add("route: " + right + " exit-only lane" + (right > 1 ? "s" : "") + " right");
                    xR = right;
                } else {
                    straight = straight3;
                }
                if (left > xL) {
                    xL = left;
                    why.add("route: " + left + " exit-only lane" + (left <= 1 ? "" : "s") + " left");
                    kL = "exit";
                    straight2 = straight;
                } else {
                    straight2 = straight;
                    kL = kL4;
                }
            }
        }
        return new LaneBrain.RoadInfo((h == null || (s6 = h.getS()) == null) ? 0 : s6.ourLanes(), (h == null || (s5 = h.getS()) == null || (name = s5.getName()) == null) ? "" : name, (h == null || (s4 = h.getS()) == null || !s4.getRamp()) ? false : true, (h == null || (s2 = h.getS()) == null || !s2.getTunnel()) ? false : true, (h == null || (s = h.getS()) == null || !s.getBridge()) ? false : true, !((h == null || (s3 = h.getS()) == null || s3.getOurLanesSure()) ? false : true), xL, xR, kL, kR, CollectionsKt.joinToString(why, ", ", "", "", -1, "...", null), straight2, navDist);
    }

    /* compiled from: LaneFeed.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final int rampSide(OsmRoads.Way ramp, OsmRoads.Way hw, double e, double n, double yaw) {
            long j;
            Intrinsics.checkNotNullParameter(ramp, "ramp");
            Intrinsics.checkNotNullParameter(hw, "hw");
            double best = Double.MAX_VALUE;
            double ax = 0.0d;
            double ay = 0.0d;
            double dx = 0.0d;
            double dy = 0.0d;
            int length = hw.getE().length - 1;
            for (int i = 0; i < length; i++) {
                double x0 = hw.getE()[i];
                double y0 = hw.getN()[i];
                double ex = hw.getE()[i + 1] - x0;
                double ey = hw.getN()[i + 1] - y0;
                double l2 = (ex * ex) + (ey * ey);
                if (l2 >= 0.01d) {
                    double t = RangesKt.coerceIn((((e - x0) * ex) + ((n - y0) * ey)) / l2, 0.0d, 1.0d);
                    double d = Math.hypot(e - (x0 + (t * ex)), n - (y0 + (t * ey)));
                    if (d < best) {
                        best = d;
                        ax = x0;
                        ay = y0;
                        dx = ex;
                        dy = ey;
                    }
                }
            }
            if (best > 40.0d) {
                return 0;
            }
            if (hw.getOneway() < 0 || (hw.getOneway() == 0 && Math.cos(Math.atan2(dy, dx) - yaw) < 0.0d)) {
                dx = -dx;
                dy = -dy;
            }
            double len = Math.hypot(dx, dy);
            double sum = 0.0d;
            int k = 0;
            int i2 = 0;
            int length2 = ramp.getE().length;
            while (i2 < length2) {
                double px = ramp.getE()[i2];
                double py = ramp.getN()[i2];
                double best2 = best;
                double best3 = py - n;
                double dist = Math.hypot(px - e, best3);
                if (dist < 20.0d) {
                    j = 0;
                } else if (dist > 200.0d) {
                    j = 0;
                } else {
                    j = 0;
                    if (((px - e) * Math.cos(yaw)) + ((py - n) * Math.sin(yaw)) <= 0.0d) {
                        sum += (((py - ay) * dx) - ((px - ax) * dy)) / len;
                        k++;
                    }
                }
                i2++;
                best = best2;
            }
            if (k == 0) {
                return 0;
            }
            double off = sum / k;
            if (off > 4.0d) {
                return -1;
            }
            return off < -4.0d ? 1 : 0;
        }
    }
}
