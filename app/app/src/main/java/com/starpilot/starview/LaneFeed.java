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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:102:0x02a3  */
    /* JADX WARN: Removed duplicated region for block: B:103:0x0292  */
    /* JADX WARN: Removed duplicated region for block: B:104:0x027b  */
    /* JADX WARN: Removed duplicated region for block: B:105:0x0272  */
    /* JADX WARN: Removed duplicated region for block: B:106:0x025f  */
    /* JADX WARN: Removed duplicated region for block: B:107:0x0256  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x021c  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x020d  */
    /* JADX WARN: Removed duplicated region for block: B:111:0x01ff  */
    /* JADX WARN: Removed duplicated region for block: B:112:0x01e8  */
    /* JADX WARN: Removed duplicated region for block: B:113:0x01e1  */
    /* JADX WARN: Removed duplicated region for block: B:114:0x01d4  */
    /* JADX WARN: Removed duplicated region for block: B:115:0x01cd  */
    /* JADX WARN: Removed duplicated region for block: B:116:0x01a3  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00ea  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x01cb  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x01d0  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x01df  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x01e4  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x01f6  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0203  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0217  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x0254  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x0259  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x026f  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x0276  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x028b  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x0296  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x029e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final LaneBrain.Frame frame(Telemetry t, LaneMarks marks, long nowMs) {
        LaneMarks.Mark[] markArr;
        int i;
        Ref.BooleanRef booleanRef;
        Ref.IntRef intRef;
        Ref.IntRef intRef2;
        int i2;
        int i3;
        RadarTrack[] radarTrackArr;
        Ref.BooleanRef booleanRef2;
        Ref.IntRef intRef3;
        Ref.IntRef intRef4;
        boolean z;
        Intrinsics.checkNotNullParameter(t, "t");
        ModelSnapshot model = t.getModel();
        if (model == null || model.getFrameId() == this.lastFrameId) {
            return null;
        }
        this.lastFrameId = model.getFrameId();
        float poseYawZ = t.getPoseYawZ();
        boolean z2 = false;
        float poseYawZ2 = ((!Float.isInfinite(poseYawZ) && !Float.isNaN(poseYawZ)) == true && System.nanoTime() - t.getPoseAtNs() < 500000000) == true ? t.getPoseYawZ() * Odometry.INSTANCE.getGyroSign() : (float) ((t.getVEgo() * Math.tan(Math.toRadians(t.getSteeringAngleDeg() - t.getAngleOffsetDeg()) / (t.getSteerRatioLive() > 5.0f ? t.getSteerRatioLive() : 15.5f))) / 3.03d);
        if (nowMs - this.lastRoadMs > 500) {
            this.lastRoadMs = nowMs;
            matchRoad(t);
        }
        int i4 = this.pendingEntry;
        this.pendingEntry = 0;
        if (marks != null) {
            LaneMarks laneMarks = (((System.nanoTime() - marks.getLastNs()) > 1000000000L ? 1 : ((System.nanoTime() - marks.getLastNs()) == 1000000000L ? 0 : -1)) < 0 ? 1 : null) != null ? marks : null;
            if (laneMarks != null) {
                markArr = laneMarks.getMarks();
                Ref.IntRef intRef5 = new Ref.IntRef();
                Ref.IntRef intRef6 = new Ref.IntRef();
                Ref.BooleanRef booleanRef3 = new Ref.BooleanRef();
                if (t.getVEgo() <= 3.0f) {
                    RadarTrack[] radar = t.getRadar();
                    int length = radar.length;
                    i = i4;
                    int i5 = 0;
                    while (i5 < length) {
                        RadarTrack radarTrack = radar[i5];
                        if (radarTrack.getMeasured()) {
                            radarTrackArr = radar;
                            i3 = length;
                            booleanRef2 = booleanRef3;
                            intRef3 = intRef6;
                            intRef4 = intRef5;
                            z = z2;
                            frame$place(intRef5, intRef6, booleanRef3, model, radarTrack.getDRel() + 2.3f, -radarTrack.getYRel(), t.getVEgo() + radarTrack.getVRel());
                        } else {
                            i3 = length;
                            radarTrackArr = radar;
                            booleanRef2 = booleanRef3;
                            intRef3 = intRef6;
                            intRef4 = intRef5;
                            z = z2;
                        }
                        i5++;
                        z2 = z;
                        radar = radarTrackArr;
                        length = i3;
                        booleanRef3 = booleanRef2;
                        intRef6 = intRef3;
                        intRef5 = intRef4;
                    }
                    booleanRef = booleanRef3;
                    intRef = intRef6;
                    intRef2 = intRef5;
                    Lead[] leadArr = new Lead[2];
                    leadArr[z2 ? 1 : 0] = t.getLeadLeft();
                    int i6 = 1;
                    leadArr[1] = t.getLeadRight();
                    for (Lead lead : CollectionsKt.listOf((Object[]) leadArr)) {
                        if (lead.getStatus()) {
                            frame$place(intRef2, intRef, booleanRef, model, lead.getDRel() + 2.3f, -lead.getYRel(), lead.getVLead());
                            i6 = i6;
                        }
                    }
                    i2 = i6;
                } else {
                    i = i4;
                    booleanRef = booleanRef3;
                    intRef = intRef6;
                    intRef2 = intRef5;
                    i2 = 1;
                }
                Rtk rtkOffset = rtkOffset(nowMs, t.getVEgo());
                float vEgo = t.getVEgo();
                float frame$lineAt = frame$lineAt(model, i2, 3.0f);
                float frame$lineAt2 = frame$lineAt(model, 2, 3.0f);
                float[] laneProb = model.getLaneProb();
                float f = (i2 >= laneProb.length ? i2 : 0) == 0 ? laneProb[i2] : 0.0f;
                float[] laneProb2 = model.getLaneProb();
                float f2 = (2 >= laneProb2.length ? i2 : 0) == 0 ? laneProb2[2] : 0.0f;
                float frame$edge = frame$edge(model, 0);
                float frame$edge2 = frame$edge(model, i2);
                LaneMarks.Mark mark = markArr == null ? (LaneMarks.Mark) ArraysKt.getOrNull(markArr, i2) : null;
                LaneMarks.Mark mark2 = markArr == null ? (LaneMarks.Mark) ArraysKt.getOrNull(markArr, 2) : null;
                LaneBrain.Road road = this.road;
                int laneChangeDir = !model.getLaneChanging() ? model.getLaneChangeDir() : 0;
                int i7 = (model.getLaneChanging() || !model.getLaneChangeFinishing()) ? 0 : i2;
                float f3 = poseYawZ2;
                int i8 = intRef2.element;
                int i9 = intRef.element;
                boolean z3 = booleanRef.element;
                float frame$lineAt3 = frame$lineAt(model, 0, 3.0f);
                float frame$lineAt4 = frame$lineAt(model, 3, 3.0f);
                float[] laneProb3 = model.getLaneProb();
                float f4 = !(laneProb3.length <= 0) ? laneProb3[0] : 0.0f;
                float[] laneProb4 = model.getLaneProb();
                return new LaneBrain.Frame(nowMs, vEgo, f3, frame$lineAt, frame$lineAt2, f, f2, frame$edge, frame$edge2, mark, mark2, road, i, laneChangeDir, i7, i8, i9, z3, frame$lineAt3, frame$lineAt4, f4, !(3 >= laneProb4.length) ? laneProb4[3] : 0.0f, t.getLeftBlindspot(), t.getRightBlindspot(), rtkOffset == null ? rtkOffset.getOffset() : Double.NaN, rtkOffset != null ? rtkOffset.getSigma() : Double.NaN, rtkOffset == null ? rtkOffset.getWay() : 0L, model.getLaneChangeProb(), this.info);
            }
        }
        markArr = null;
        Ref.IntRef intRef52 = new Ref.IntRef();
        Ref.IntRef intRef62 = new Ref.IntRef();
        Ref.BooleanRef booleanRef32 = new Ref.BooleanRef();
        if (t.getVEgo() <= 3.0f) {
        }
        Rtk rtkOffset2 = rtkOffset(nowMs, t.getVEgo());
        float vEgo2 = t.getVEgo();
        float frame$lineAt5 = frame$lineAt(model, i2, 3.0f);
        float frame$lineAt22 = frame$lineAt(model, 2, 3.0f);
        float[] laneProb5 = model.getLaneProb();
        if ((i2 >= laneProb5.length ? i2 : 0) == 0) {
        }
        float[] laneProb22 = model.getLaneProb();
        if ((2 >= laneProb22.length ? i2 : 0) == 0) {
        }
        float frame$edge3 = frame$edge(model, 0);
        float frame$edge22 = frame$edge(model, i2);
        if (markArr == null) {
        }
        if (markArr == null) {
        }
        LaneBrain.Road road2 = this.road;
        if (!model.getLaneChanging()) {
        }
        if (model.getLaneChanging()) {
        }
        float f32 = poseYawZ2;
        int i82 = intRef2.element;
        int i92 = intRef.element;
        boolean z32 = booleanRef.element;
        float frame$lineAt32 = frame$lineAt(model, 0, 3.0f);
        float frame$lineAt42 = frame$lineAt(model, 3, 3.0f);
        float[] laneProb32 = model.getLaneProb();
        if (!(laneProb32.length <= 0)) {
        }
        float[] laneProb42 = model.getLaneProb();
        return new LaneBrain.Frame(nowMs, vEgo2, f32, frame$lineAt5, frame$lineAt22, f, f2, frame$edge3, frame$edge22, mark, mark2, road2, i, laneChangeDir, i7, i82, i92, z32, frame$lineAt32, frame$lineAt42, f4, !(3 >= laneProb42.length) ? laneProb42[3] : 0.0f, t.getLeftBlindspot(), t.getRightBlindspot(), rtkOffset2 == null ? rtkOffset2.getOffset() : Double.NaN, rtkOffset2 != null ? rtkOffset2.getSigma() : Double.NaN, rtkOffset2 == null ? rtkOffset2.getWay() : 0L, model.getLaneChangeProb(), this.info);
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

    /* JADX WARN: Code restructure failed: missing block: B:26:0x00a6, code lost:
    
        if ((r0.getDist() < 15.0d) != false) goto L31;
     */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00ee  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00f9  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x0184  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x01b5  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x01b7  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0188  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final void matchOwnMap() {
        int i;
        Hpms.Match match;
        OsmRoads.Way w;
        List<LaneRamps.Ramp> emptyList;
        OsmRoads.Way prev;
        OsmRoads.Way w2;
        boolean z;
        int ourWay;
        String kind;
        OsmRoads.Fix f = OsmRoads.INSTANCE.getLastFix();
        if (f == null || Double.isNaN(OsmRoads.INSTANCE.getLat0()) || System.nanoTime() - f.getTNs() > 3000000000L) {
            this.road = null;
            this.way = null;
            this.ramps = CollectionsKt.emptyList();
            this.hpms = null;
            return;
        }
        if (f.getSpeed() > 3.0f) {
            float bearingDeg = f.getBearingDeg();
            if ((Float.isInfinite(bearingDeg) || Float.isNaN(bearingDeg)) ? false : true) {
                this.lastYaw = 1.5707963267948966d - Math.toRadians(f.getBearingDeg());
            }
        }
        double yaw = this.lastYaw;
        if (Double.isNaN(yaw)) {
            return;
        }
        Pair<Double, Double> local = OsmRoads.INSTANCE.toLocal(f.getLat(), f.getLon());
        double e = local.component1().doubleValue();
        double n = local.component2().doubleValue();
        OsmRoads.Match it = OsmRoads.INSTANCE.matchSeg(e, n, yaw);
        if (it != null) {
        }
        it = null;
        OsmRoads.Match mt = it;
        OsmRoads.Way w3 = mt != null ? mt.getWay() : null;
        try {
            i = 2;
        } catch (Exception e2) {
            i = 2;
        }
        try {
            match = Hpms.INSTANCE.match(f.getLat(), f.getLon(), yaw, (w3 == null || (kind = w3.getKind()) == null) ? null : Boolean.valueOf(StringsKt.endsWith(kind, "_link", false)), (r24 & 16) != 0 ? 45.0d : 0.0d, (r24 & 32) != 0 ? "" : null);
        } catch (Exception e3) {
            match = null;
            this.hpms = match;
            if (w3 != null) {
            }
        }
        this.hpms = match;
        if (w3 != null) {
            this.road = null;
            this.way = null;
            this.ramps = CollectionsKt.emptyList();
            return;
        }
        if (!w3.getHighway() || StringsKt.endsWith(w3.getKind(), "_link", false)) {
            w = w3;
            emptyList = CollectionsKt.emptyList();
        } else {
            try {
                w = w3;
            } catch (Exception e4) {
                w = w3;
            }
            try {
                emptyList = LaneRamps.INSTANCE.scan(OsmRoads.INSTANCE.near(e, n, 450.0d), w, e, n, yaw);
            } catch (Exception e5) {
                emptyList = CollectionsKt.emptyList();
                this.ramps = emptyList;
                prev = this.way;
                if (prev != null) {
                    this.pendingEntry = Companion.rampSide(prev, w, e, n, yaw);
                }
                w2 = w;
                this.way = w2;
                if (w2.getLanes() > 0) {
                }
                z = true;
                this.road = new LaneBrain.Road(w2.getId(), w2.getName(), w2.getKind(), w2.getOneway() != 0 ? z : false, w2.getHighway(), ourWay);
            }
        }
        this.ramps = emptyList;
        prev = this.way;
        if (prev != null && prev.getId() != w.getId() && StringsKt.endsWith(prev.getKind(), "_link", false) && w.getHighway() && !StringsKt.endsWith(w.getKind(), "_link", false)) {
            this.pendingEntry = Companion.rampSide(prev, w, e, n, yaw);
        }
        w2 = w;
        this.way = w2;
        if (w2.getLanes() > 0) {
            ourWay = 0;
        } else {
            if (w2.getOneway() == 0) {
                int lanes = w2.getLanes() / i;
                z = true;
                ourWay = Math.max(1, lanes);
                this.road = new LaneBrain.Road(w2.getId(), w2.getName(), w2.getKind(), w2.getOneway() != 0 ? z : false, w2.getHighway(), ourWay);
            }
            ourWay = w2.getLanes();
        }
        z = true;
        this.road = new LaneBrain.Road(w2.getId(), w2.getName(), w2.getKind(), w2.getOneway() != 0 ? z : false, w2.getHighway(), ourWay);
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
