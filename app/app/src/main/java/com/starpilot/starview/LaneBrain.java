package com.starpilot.starview;

import android.content.Context;
import com.starpilot.starview.LaneMarks;
import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import kotlin.Pair;
import kotlin.Triple;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.ArrayDeque;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.Grouping;
import kotlin.collections.GroupingKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import org.json.JSONObject;

/* compiled from: LaneBrain.kt */
public final class LaneBrain {
    public static final Companion Companion = new Companion(null);
    public static final int N = 6;
    public static final double SHOW = 0.35d;
    private static final double[] W;
    private long bsmMs;
    private final ArrayDeque<double[]> camLog;
    private final ArrayDeque<long[]> carsL;
    private final ArrayDeque<long[]> carsR;
    private long carsUsedMs;
    private double driveS;
    private long entryMs;
    private int entrySide;
    private double heading;
    private long holdUntil;
    private RoadInfo info;
    private double knownS;
    private double lastBlurOdo;
    private long lastCameraMs;
    private int lastChangeDir;
    private long lastChangeMs;
    private long lastMs;
    private volatile long modelMs;
    private volatile String modelText;
    private int nChanges;
    private int nOpMissed;
    private int nRamps;
    private int nTurns;
    private int nUndone;
    private double odo;
    private Function0<Unit> onCountsChanged;
    private Function1<? super Event, Unit> onEvent;
    private final ArrayDeque<Long> oncoming;
    private boolean opCounted;
    private int opDir;
    private long opSince;
    private Pending pending;
    private String prevAnswer;
    private float prevC;
    private long prevMs;
    private float prevYL;
    private float prevYR;
    private double[][] q;
    private Road road;
    private long roadChangeMs;
    private long rtkCheckMs;
    private long rtkDoubtMs;
    private final ArrayDeque<double[]> rtkLog;
    private long straightSince;
    private final Vote vDashL;
    private final Vote vDashR;
    private final Vote vEdgeL;
    private final Vote vEdgeR;
    private final Vote vOuterL;
    private final Vote vOuterR;
    private final Vote vSolidR;
    private final Vote vYellow;
    private double[][] p = Companion.prior();
    private RoadModel model = new RoadModel(0, "", 0.0d, 0, 0, "", "", false, false);
    private final ArrayDeque<Event> events = new ArrayDeque<>();
    private final ArrayDeque<Event> why = new ArrayDeque<>();
    private final HashMap<Long, Integer> myCounts = new HashMap<>();
    private final LaneMemory memory = new LaneMemory();
    private final ArrayDeque<double[]> hist = new ArrayDeque<>();
    private float laneW = 3.5f;

    public LaneBrain() {
        double[][] dArr = new double[6][];
        for (int i = 0; i < 6; i++) {
            dArr[i] = new double[6];
        }
        this.q = dArr;
        this.prevAnswer = "";
        this.rtkLog = new ArrayDeque<>();
        this.camLog = new ArrayDeque<>();
        this.vYellow = new Vote();
        this.vDashL = new Vote();
        this.vDashR = new Vote();
        this.vSolidR = new Vote();
        this.vOuterL = new Vote();
        this.vOuterR = new Vote();
        this.vEdgeL = new Vote();
        this.vEdgeR = new Vote();
        this.carsL = new ArrayDeque<>();
        this.carsR = new ArrayDeque<>();
        this.oncoming = new ArrayDeque<>();
        this.modelText = "";
    }

    /* compiled from: LaneBrain.kt */
    public static final class Road {
        private final boolean highway;
        private final long id;
        private final String kind;
        private final int lanesOurWay;
        private final String name;
        private final boolean oneway;

        public Road(long id, String name, String kind, boolean oneway, boolean highway, int lanesOurWay) {
            Intrinsics.checkNotNullParameter(name, "name");
            Intrinsics.checkNotNullParameter(kind, "kind");
            this.id = id;
            this.name = name;
            this.kind = kind;
            this.oneway = oneway;
            this.highway = highway;
            this.lanesOurWay = lanesOurWay;
        }

        public final boolean getHighway() {
            return this.highway;
        }

        public final long getId() {
            return this.id;
        }

        public final String getKind() {
            return this.kind;
        }

        public final int getLanesOurWay() {
            return this.lanesOurWay;
        }

        public final String getName() {
            return this.name;
        }

        public final boolean getOneway() {
            return this.oneway;
        }

        public final boolean getRamp() {
            return StringsKt.endsWith$default(this.kind, "_link", false, 2, (Object) null);
        }
    }

    /* compiled from: LaneBrain.kt */
    public static final class RoadInfo {
        private final boolean bridge;
        private final int extraL;
        private final int extraR;
        private final String extraWhy;
        private final int hpmsLanes;
        private final String hpmsName;
        private final boolean hpmsRamp;
        private final boolean hpmsSure;
        private final String kindL;
        private final String kindR;
        private final double navDist;
        private final int navStraight;
        private final boolean tunnel;

        public RoadInfo() {
            this(0, null, false, false, false, false, 0, 0, null, null, null, 0, 0.0d, 8191, null);
        }

        public RoadInfo(int hpmsLanes, String hpmsName, boolean hpmsRamp, boolean tunnel, boolean bridge, boolean hpmsSure, int extraL, int extraR, String kindL, String kindR, String extraWhy, int navStraight, double navDist) {
            Intrinsics.checkNotNullParameter(hpmsName, "hpmsName");
            Intrinsics.checkNotNullParameter(kindL, "kindL");
            Intrinsics.checkNotNullParameter(kindR, "kindR");
            Intrinsics.checkNotNullParameter(extraWhy, "extraWhy");
            this.hpmsLanes = hpmsLanes;
            this.hpmsName = hpmsName;
            this.hpmsRamp = hpmsRamp;
            this.tunnel = tunnel;
            this.bridge = bridge;
            this.hpmsSure = hpmsSure;
            this.extraL = extraL;
            this.extraR = extraR;
            this.kindL = kindL;
            this.kindR = kindR;
            this.extraWhy = extraWhy;
            this.navStraight = navStraight;
            this.navDist = navDist;
        }

        public /* synthetic */ RoadInfo(int i, String str, boolean z, boolean z2, boolean z3, boolean z4, int i2, int i3, String str2, String str3, String str4, int i4, double d, int i5, DefaultConstructorMarker defaultConstructorMarker) {
            this((i5 & 1) != 0 ? 0 : i, (i5 & 2) != 0 ? "" : str, (i5 & 4) != 0 ? false : z, (i5 & 8) != 0 ? false : z2, (i5 & 16) != 0 ? false : z3, (i5 & 32) != 0 ? true : z4, (i5 & 64) != 0 ? 0 : i2, (i5 & 128) != 0 ? 0 : i3, (i5 & 256) != 0 ? "" : str2, (i5 & 512) != 0 ? "" : str3, (i5 & 1024) == 0 ? str4 : "", (i5 & 2048) == 0 ? i4 : 0, (i5 & 4096) != 0 ? Double.NaN : d);
        }

        public final boolean getBridge() {
            return this.bridge;
        }

        public final int getHpmsLanes() {
            return this.hpmsLanes;
        }

        public final String getHpmsName() {
            return this.hpmsName;
        }

        public final boolean getHpmsRamp() {
            return this.hpmsRamp;
        }

        public final boolean getTunnel() {
            return this.tunnel;
        }

        public final boolean getHpmsSure() {
            return this.hpmsSure;
        }

        public final int getExtraL() {
            return this.extraL;
        }

        public final int getExtraR() {
            return this.extraR;
        }

        public final String getExtraWhy() {
            return this.extraWhy;
        }

        public final String getKindL() {
            return this.kindL;
        }

        public final String getKindR() {
            return this.kindR;
        }

        public final double getNavDist() {
            return this.navDist;
        }

        public final int getNavStraight() {
            return this.navStraight;
        }
    }

    /* compiled from: LaneBrain.kt */
    public static final class Frame {
        private final boolean bsmL;
        private final boolean bsmR;
        private final int carsLeft;
        private final int carsRight;
        private final float edgeL;
        private final float edgeR;
        private final int entrySide;
        private final RoadInfo info;
        private final LaneMarks.Mark leftMark;
        private final long nowMs;
        private final boolean oncomingLeft;
        private final int opChange;
        private final boolean opFinishing;
        private final float opLcProb;
        private final float pL;
        private final float pL2;
        private final float pR;
        private final float pR2;
        private final LaneMarks.Mark rightMark;
        private final Road road;
        private final double rtkOffset;
        private final double rtkSigma;
        private final long rtkWay;
        private final float vEgo;
        private final float yL;
        private final float yL2;
        private final float yR;
        private final float yR2;
        private final float yawRate;

        public Frame(long nowMs, float vEgo, float yawRate, float yL, float yR, float pL, float pR, float edgeL, float edgeR, LaneMarks.Mark leftMark, LaneMarks.Mark rightMark, Road road, int entrySide, int opChange, boolean opFinishing, int carsLeft, int carsRight, boolean oncomingLeft, float yL2, float yR2, float pL2, float pR2, boolean bsmL, boolean bsmR, double rtkOffset, double rtkSigma, long rtkWay, float opLcProb, RoadInfo info) {
            this.nowMs = nowMs;
            this.vEgo = vEgo;
            this.yawRate = yawRate;
            this.yL = yL;
            this.yR = yR;
            this.pL = pL;
            this.pR = pR;
            this.edgeL = edgeL;
            this.edgeR = edgeR;
            this.leftMark = leftMark;
            this.rightMark = rightMark;
            this.road = road;
            this.entrySide = entrySide;
            this.opChange = opChange;
            this.opFinishing = opFinishing;
            this.carsLeft = carsLeft;
            this.carsRight = carsRight;
            this.oncomingLeft = oncomingLeft;
            this.yL2 = yL2;
            this.yR2 = yR2;
            this.pL2 = pL2;
            this.pR2 = pR2;
            this.bsmL = bsmL;
            this.bsmR = bsmR;
            this.rtkOffset = rtkOffset;
            this.rtkSigma = rtkSigma;
            this.rtkWay = rtkWay;
            this.opLcProb = opLcProb;
            this.info = info;
        }

        public /* synthetic */ Frame(long j, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, LaneMarks.Mark mark, LaneMarks.Mark mark2, Road road, int i, int i2, boolean z, int i3, int i4, boolean z2, float f9, float f10, float f11, float f12, boolean z3, boolean z4, double d, double d2, long j2, float f13, RoadInfo roadInfo, int i5, DefaultConstructorMarker defaultConstructorMarker) {
            this(j, f, f2, f3, f4, f5, f6, f7, f8, (i5 & 512) != 0 ? null : mark, (i5 & 1024) != 0 ? null : mark2, (i5 & 2048) != 0 ? null : road, (i5 & 4096) != 0 ? 0 : i, (i5 & 8192) != 0 ? 0 : i2, (i5 & 16384) != 0 ? false : z, (32768 & i5) != 0 ? 0 : i3, (65536 & i5) != 0 ? 0 : i4, (131072 & i5) != 0 ? false : z2, (262144 & i5) != 0 ? Float.NaN : f9, (524288 & i5) != 0 ? Float.NaN : f10, (1048576 & i5) != 0 ? Float.NaN : f11, (2097152 & i5) != 0 ? Float.NaN : f12, (4194304 & i5) != 0 ? false : z3, (8388608 & i5) != 0 ? false : z4, (16777216 & i5) != 0 ? Double.NaN : d, (33554432 & i5) != 0 ? Double.NaN : d2, (67108864 & i5) != 0 ? 0L : j2, (134217728 & i5) != 0 ? Float.NaN : f13, (i5 & 268435456) != 0 ? null : roadInfo);
        }

        public final long getNowMs() {
            return this.nowMs;
        }

        public final float getVEgo() {
            return this.vEgo;
        }

        public final float getYawRate() {
            return this.yawRate;
        }

        public final float getPL() {
            return this.pL;
        }

        public final float getPR() {
            return this.pR;
        }

        public final float getYL() {
            return this.yL;
        }

        public final float getYR() {
            return this.yR;
        }

        public final float getEdgeL() {
            return this.edgeL;
        }

        public final float getEdgeR() {
            return this.edgeR;
        }

        public final LaneMarks.Mark getLeftMark() {
            return this.leftMark;
        }

        public final LaneMarks.Mark getRightMark() {
            return this.rightMark;
        }

        public final Road getRoad() {
            return this.road;
        }

        public final int getEntrySide() {
            return this.entrySide;
        }

        public final int getOpChange() {
            return this.opChange;
        }

        public final boolean getOpFinishing() {
            return this.opFinishing;
        }

        public final int getCarsLeft() {
            return this.carsLeft;
        }

        public final int getCarsRight() {
            return this.carsRight;
        }

        public final boolean getOncomingLeft() {
            return this.oncomingLeft;
        }

        public final float getPL2() {
            return this.pL2;
        }

        public final float getPR2() {
            return this.pR2;
        }

        public final float getYL2() {
            return this.yL2;
        }

        public final float getYR2() {
            return this.yR2;
        }

        public final boolean getBsmL() {
            return this.bsmL;
        }

        public final boolean getBsmR() {
            return this.bsmR;
        }

        public final double getRtkOffset() {
            return this.rtkOffset;
        }

        public final double getRtkSigma() {
            return this.rtkSigma;
        }

        public final long getRtkWay() {
            return this.rtkWay;
        }

        public final float getOpLcProb() {
            return this.opLcProb;
        }

        public final RoadInfo getInfo() {
            return this.info;
        }
    }

    /* compiled from: LaneBrain.kt */
    public static final class Event {
        private final long atMs;
        private final String text;

        public Event(long atMs, String text) {
            Intrinsics.checkNotNullParameter(text, "text");
            this.atMs = atMs;
            this.text = text;
        }

        public final long getAtMs() {
            return this.atMs;
        }

        public final String getText() {
            return this.text;
        }
    }

    /* compiled from: LaneBrain.kt */
    public static final class RoadModel {
        private final boolean bridge;
        private final double conf;
        private final int extraL;
        private final int extraR;
        private final String kindL;
        private final String kindR;
        private final int lanes;
        private final String src;
        private final boolean tunnel;

        public RoadModel(int lanes, String src, double conf, int extraL, int extraR, String kindL, String kindR, boolean tunnel, boolean bridge) {
            Intrinsics.checkNotNullParameter(src, "src");
            Intrinsics.checkNotNullParameter(kindL, "kindL");
            Intrinsics.checkNotNullParameter(kindR, "kindR");
            this.lanes = lanes;
            this.src = src;
            this.conf = conf;
            this.extraL = extraL;
            this.extraR = extraR;
            this.kindL = kindL;
            this.kindR = kindR;
            this.tunnel = tunnel;
            this.bridge = bridge;
        }

        public final double getConf() {
            return this.conf;
        }

        public final int getExtraL() {
            return this.extraL;
        }

        public final int getExtraR() {
            return this.extraR;
        }

        public final String getKindL() {
            return this.kindL;
        }

        public final String getKindR() {
            return this.kindR;
        }

        public final int getLanes() {
            return this.lanes;
        }

        public final String getSrc() {
            return this.src;
        }

        public final boolean getBridge() {
            return this.bridge;
        }

        public final boolean getTunnel() {
            return this.tunnel;
        }

        public final int getTotal() {
            if (this.lanes > 0) {
                return this.lanes + this.extraL + this.extraR;
            }
            return 0;
        }
    }

    /* compiled from: LaneBrain.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final double prior(int l, int r) {
            return LaneBrain.W[l] * LaneBrain.W[r];
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final double[][] prior() {
            double[][] dArr = new double[6][];
            for (int i = 0; i < 6; i++) {
                double[] dArr2 = new double[6];
                for (int i2 = 0; i2 < 6; i2++) {
                    dArr2[i2] = LaneBrain.Companion.prior(i, i2);
                }
                dArr[i] = dArr2;
            }
            return dArr;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final String sideName(int side) {
            return side < 0 ? "left" : "right";
        }
    }

    static {
        double[] w = new double[6];
        for (int i = 0; i < 6; i++) {
            w[i] = Math.pow(0.65d, i);
        }
        double s = ArraysKt.sum(w);
        double[] dArr = new double[6];
        for (int i2 = 0; i2 < 6; i2++) {
            dArr[i2] = w[i2] / s;
        }
        W = dArr;
    }

    public final Road getRoad() {
        return this.road;
    }

    public final RoadInfo getInfo() {
        return this.info;
    }

    public final RoadModel getModel() {
        return this.model;
    }

    public final ArrayDeque<Event> getEvents() {
        return this.events;
    }

    public final Function1<Event, Unit> getOnEvent() {
        return this.onEvent;
    }

    public final void setOnEvent(Function1<? super Event, Unit> function1) {
        this.onEvent = function1;
    }

    public final ArrayDeque<Event> getWhy() {
        return this.why;
    }

    public final HashMap<Long, Integer> getMyCounts() {
        return this.myCounts;
    }

    public final Function0<Unit> getOnCountsChanged() {
        return this.onCountsChanged;
    }

    public final void setOnCountsChanged(Function0<Unit> function0) {
        this.onCountsChanged = function0;
    }

    public final LaneMemory getMemory() {
        return this.memory;
    }

    /* compiled from: LaneBrain.kt */
    public static final class Live {
        public static final Live INSTANCE = new Live();
        private static volatile LaneBrain brain;

        private Live() {
        }

        public final LaneBrain getBrain() {
            return brain;
        }

        public final void setBrain(LaneBrain laneBrain) {
            brain = laneBrain;
        }

        public final int savedCount(Context ctx, long wayId) {
            HashMap<Long, Integer> myCounts;
            Integer num;
            Intrinsics.checkNotNullParameter(ctx, "ctx");
            LaneBrain laneBrain = brain;
            if (laneBrain != null && (myCounts = laneBrain.getMyCounts()) != null && (num = myCounts.get(Long.valueOf(wayId))) != null) {
                return num.intValue();
            }
            try {
                return new JSONObject(FilesKt.readText$default(new File(ctx.getFilesDir(), "lane_counts.json"), null, 1, null)).optInt(String.valueOf(wayId), 0);
            } catch (Exception e) {
                return 0;
            }
        }

        public final void forgetCount(Context ctx, long wayId) {
            Function0<Unit> onCountsChanged;
            Intrinsics.checkNotNullParameter(ctx, "ctx");
            LaneBrain b = brain;
            if (b != null) {
                if (b.getMyCounts().remove(Long.valueOf(wayId)) == null || (onCountsChanged = b.getOnCountsChanged()) == null) {
                    return;
                }
                onCountsChanged.invoke();
                return;
            }
            File f = new File(ctx.getFilesDir(), "lane_counts.json");
            try {
                JSONObject j = new JSONObject(FilesKt.readText$default(f, null, 1, null));
                j.remove(String.valueOf(wayId));
                String jSONObject = j.toString();
                Intrinsics.checkNotNullExpressionValue(jSONObject, "toString(...)");
                FilesKt.writeText$default(f, jSONObject, null, 2, null);
            } catch (Exception e) {
            }
        }

        public final void rememberCount(Context ctx, long wayId, int m) {
            JSONObject j;
            Intrinsics.checkNotNullParameter(ctx, "ctx");
            LaneBrain b = brain;
            if (b != null) {
                b.getMyCounts().put(Long.valueOf(wayId), Integer.valueOf(m));
                Function0<Unit> onCountsChanged = b.getOnCountsChanged();
                if (onCountsChanged != null) {
                    onCountsChanged.invoke();
                    return;
                }
                return;
            }
            File f = new File(ctx.getFilesDir(), "lane_counts.json");
            try {
                try {
                    j = new JSONObject(FilesKt.readText$default(f, null, 1, null));
                } catch (Exception e) {
                    j = new JSONObject();
                }
                j.put(String.valueOf(wayId), m);
                String jSONObject = j.toString();
                Intrinsics.checkNotNullExpressionValue(jSONObject, "toString(...)");
                FilesKt.writeText$default(f, jSONObject, null, 2, null);
            } catch (Exception e2) {
            }
        }
    }

    public final int getNChanges() {
        return this.nChanges;
    }

    public final int getNUndone() {
        return this.nUndone;
    }

    public final int getNTurns() {
        return this.nTurns;
    }

    public final int getNRamps() {
        return this.nRamps;
    }

    public final int getNOpMissed() {
        return this.nOpMissed;
    }

    public final double getDriveS() {
        return this.driveS;
    }

    public final double getKnownS() {
        return this.knownS;
    }

    public final long getLastCameraMs() {
        return this.lastCameraMs;
    }

    public final long getLastChangeMs() {
        return this.lastChangeMs;
    }

    public final int getLastChangeDir() {
        return this.lastChangeDir;
    }

    public final int knownPct() {
        if (this.driveS < 1.0d) {
            return 0;
        }
        return (int) ((100 * this.knownS) / this.driveS);
    }

    public final String summary() {
        return this.nChanges + " lane change" + (this.nChanges == 1 ? "" : "s") + (this.nUndone > 0 ? " (" + this.nUndone + " undone)" : "") + (this.nOpMissed > 0 ? " (" + this.nOpMissed + " by openpilot not finished)" : "") + " · " + this.nTurns + " turn" + (this.nTurns == 1 ? "" : "s") + " · " + this.nRamps + " ramp" + (this.nRamps == 1 ? "" : "s") + " · lane known " + knownPct() + "% of " + ((int) (this.driveS / 60)) + " min";
    }

    public final double getOdo() {
        return this.odo;
    }

    public final float getLaneW() {
        return this.laneW;
    }

    private final void norm() {
        double s = 0.0d;
        for (double[] a : this.p) {
            for (double v : a) {
                s += v;
            }
        }
        if (s > 0.0d) {
            if ((Double.isInfinite(s) || Double.isNaN(s)) ? false : true) {
                double mx = 0.0d;
                for (int l = 0; l < 6; l++) {
                    for (int r = 0; r < 6; r++) {
                        this.p[l][r] = ((this.p[l][r] / s) * 0.996d) + (Companion.prior(l, r) * 0.004d);
                        if (this.p[l][r] > mx) {
                            mx = this.p[l][r];
                        }
                    }
                }
                double floor = 0.001d * mx;
                for (double[] a2 : this.p) {
                    int length = a2.length;
                    for (int j = 0; j < length; j++) {
                        if (a2[j] < floor) {
                            a2[j] = floor;
                        }
                    }
                }
                return;
            }
        }
        this.p = Companion.prior();
    }

    private final int count(int l, int r, int side) {
        return side < 0 ? l : r;
    }

    private final void note(long now, String text) {
        this.why.addFirst(new Event(now, text));
        while (this.why.size() > 6) {
            this.why.removeLast();
        }
    }

    private final void see(int side, int v, double conf, long now, String text) {
        if (now >= this.holdUntil || side(side).getFirst().intValue() == v) {
            this.lastCameraMs = now;
            double other = (1 - conf) / 5;
            double ratio = conf / other;
            for (int l = 0; l < 6; l++) {
                for (int r = 0; r < 6; r++) {
                    if (count(l, r, side) == RangesKt.coerceIn(v, 0, 5)) {
                        double[] dArr = this.p[l];
                        dArr[r] = dArr[r] * ratio;
                    }
                }
            }
            norm();
            note(now, text);
        }
    }

    private final void seeRange(int side, int lo, int hi, double conf, long now, String text) {
        if (now < this.holdUntil) {
            int intValue = side(side).getFirst().intValue();
            if (!(lo <= intValue && intValue <= hi)) {
                return;
            }
        }
        this.lastCameraMs = now;
        int inside = RangesKt.coerceAtLeast((hi - lo) + 1, 1);
        int outside = RangesKt.coerceAtLeast(6 - inside, 1);
        double ratio = (conf / inside) / ((1 - conf) / outside);
        for (int l = 0; l < 6; l++) {
            for (int r = 0; r < 6; r++) {
                int count = count(l, r, side);
                if (lo <= count && count <= hi) {
                    double[] dArr = this.p[l];
                    dArr[r] = dArr[r] * ratio;
                }
            }
        }
        norm();
        note(now, text);
    }

    private final void atLeast(int side, int n, double conf, long now, String text) {
        if (now >= this.holdUntil || side(side).getFirst().intValue() >= n) {
            this.lastCameraMs = now;
            double ratio = conf / (1 - conf);
            for (int l = 0; l < 6; l++) {
                for (int r = 0; r < 6; r++) {
                    if (count(l, r, side) >= n) {
                        double[] dArr = this.p[l];
                        dArr[r] = dArr[r] * ratio;
                    }
                }
            }
            norm();
            note(now, text);
        }
    }

    private final void moved(int dir, double sure, long now, String text) {
        double[][] q = new double[6][];
        for (int i = 0; i < 6; i++) {
            q[i] = new double[6];
        }
        for (int l = 0; l < 6; l++) {
            for (int r = 0; r < 6; r++) {
                double v = this.p[l][r];
                double[] dArr = q[l];
                dArr[r] = dArr[r] + ((1 - sure) * v);
                int nl = l + dir;
                int nr = r - dir;
                if (nl < 0) {
                    nl = 0;
                    nr = RangesKt.coerceAtMost(r + 1, 5);
                }
                if (nr < 0) {
                    nr = 0;
                    nl = RangesKt.coerceAtMost(l + 1, 5);
                }
                double[] dArr2 = q[RangesKt.coerceAtMost(nl, 5)];
                int coerceAtMost = RangesKt.coerceAtMost(nr, 5);
                dArr2[coerceAtMost] = dArr2[coerceAtMost] + (v * sure);
            }
        }
        this.p = q;
        norm();
        note(now, text);
        if (now < this.holdUntil) {
            this.holdUntil = 60000 + now;
        }
    }

    private final void lanesOnSide(int side, int d) {
        if (d == 0) {
            return;
        }
        double[][] q = new double[6][];
        for (int i = 0; i < 6; i++) {
            q[i] = new double[6];
        }
        for (int l = 0; l < 6; l++) {
            for (int r = 0; r < 6; r++) {
                int nl = side < 0 ? RangesKt.coerceIn(l + d, 0, 5) : l;
                int nr = side > 0 ? RangesKt.coerceIn(r + d, 0, 5) : r;
                double[] dArr = q[nl];
                dArr[nr] = dArr[nr] + this.p[l][r];
            }
        }
        this.p = q;
        norm();
    }

    private final void restart(int side, int v, double conf, long now, String text) {
        for (int l = 0; l < 6; l++) {
            for (int r = 0; r < 6; r++) {
                int mine = count(l, r, side);
                double[] dArr = W;
                double other = side < 0 ? dArr[r] : dArr[l];
                this.p[l][r] = (mine == v ? conf : (1 - conf) / 5) * other;
            }
        }
        norm();
        this.holdUntil = 0L;
        note(now, text);
    }

    private final void blur(double d) {
        double k = RangesKt.coerceIn((d / 300.0d) * 0.03d, 0.0d, 0.3d);
        for (int l = 0; l < 6; l++) {
            for (int r = 0; r < 6; r++) {
                this.p[l][r] = (this.p[l][r] * (1 - k)) + (Companion.prior(l, r) * k);
            }
        }
    }

    private final RoadModel roadModel(Road r, RoadInfo i) {
        Triple triple;
        double d;
        String kindR;
        String kindL;
        Integer mine = r != null ? this.myCounts.get(Long.valueOf(r.getId())) : null;
        int hp = i != null ? i.getHpmsLanes() : 0;
        if (mine != null && mine.intValue() > 0) {
            triple = new Triple(mine, "your count", Double.valueOf(0.97d));
        } else if (hp > 0) {
            Integer valueOf = Integer.valueOf(hp);
            if ((i == null || i.getHpmsSure()) ? false : true) {
                d = 0.6d;
            } else {
                if (!(r != null && r.getHighway())) {
                    if (!(i != null && i.getHpmsRamp())) {
                        d = 0.75d;
                    }
                }
                d = 0.9d;
            }
            triple = new Triple(valueOf, "HPMS", Double.valueOf(d));
        } else {
            if ((r != null ? r.getLanesOurWay() : 0) > 0) {
                Intrinsics.checkNotNull(r);
                triple = new Triple(Integer.valueOf(r.getLanesOurWay()), "map", Double.valueOf(r.getLanesOurWay() == 1 ? 0.8d : 0.55d));
            } else {
                if ((i != null ? i.getNavStraight() : 0) > 0) {
                    Intrinsics.checkNotNull(i);
                    triple = new Triple(Integer.valueOf(i.getNavStraight()), "route", Double.valueOf(0.55d));
                } else {
                    triple = new Triple(0, "", Double.valueOf(0.0d));
                }
            }
        }
        int n = ((Number) triple.component1()).intValue();
        String src = (String) triple.component2();
        double conf = ((Number) triple.component3()).doubleValue();
        return new RoadModel(n, src, conf, i != null ? i.getExtraL() : 0, i != null ? i.getExtraR() : 0, (i == null || (kindL = i.getKindL()) == null) ? "" : kindL, (i == null || (kindR = i.getKindR()) == null) ? "" : kindR, i != null && i.getTunnel(), i != null && i.getBridge());
    }

    private final double fits(int t, RoadModel m) {
        if (m.getLanes() <= 0) {
            return 1.0d;
        }
        int target = m.getTotal();
        double k = m.getConf() >= 0.95d ? 3.5d : m.getConf() >= 0.85d ? 2.5d : m.getConf() >= 0.7d ? 1.3d : 0.8d;
        double f = Math.exp((-k) * Math.abs(t - target));
        int ex = m.getExtraL() + m.getExtraR();
        return (ex <= 0 || t != m.getLanes()) ? f : Math.max(f, 0.45d);
    }

    private final void answer() {
        double s = 0.0d;
        for (int l = 0; l < 6; l++) {
            for (int r = 0; r < 6; r++) {
                double v = this.p[l][r] * fits(l + r + 1, this.model);
                this.q[l][r] = v;
                s += v;
            }
        }
        if (s > 0.0d) {
            for (double[] a : this.q) {
                int length = a.length;
                for (int j = 0; j < length; j++) {
                    a[j] = a[j] / s;
                }
            }
        }
    }

    public final double[] marg(int dir) {
        double[] m = new double[6];
        for (int l = 0; l < 6; l++) {
            for (int r = 0; r < 6; r++) {
                int i = dir < 0 ? l : r;
                m[i] = m[i] + this.q[l][r];
            }
        }
        return m;
    }

    public final Pair<Integer, Double> side(int dir) {
        double[] m = marg(dir);
        int b = 0;
        for (int k = 1; k < 6; k++) {
            if (m[k] > m[b]) {
                b = k;
            }
        }
        return TuplesKt.to(Integer.valueOf(b), Double.valueOf(m[b]));
    }

    public final Pair<Integer, Double> sideLanes(int dir) {
        double[] m = marg(dir);
        if (m[0] >= 0.6d) {
            return TuplesKt.to(0, Double.valueOf(m[0]));
        }
        double atLeast = 1.0d;
        int n = 0;
        double c = m[0];
        for (int k = 1; k < 6; k++) {
            atLeast -= m[k - 1];
            if (atLeast < 0.6d) {
                break;
            }
            n = k;
            c = atLeast;
        }
        return n == 0 ? side(dir) : TuplesKt.to(Integer.valueOf(n), Double.valueOf(c));
    }

    public final int lanesLeft() {
        return side(-1).getFirst().intValue();
    }

    public final int lanesRight() {
        return side(1).getFirst().intValue();
    }

    public final Pair<Integer, Double> total() {
        double[] m = new double[12];
        for (int l = 0; l < 6; l++) {
            for (int r = 0; r < 6; r++) {
                int i = l + r + 1;
                m[i] = m[i] + this.q[l][r];
            }
        }
        int b = 1;
        int length = m.length;
        for (int k = 2; k < length; k++) {
            if (m[k] > m[b]) {
                b = k;
            }
        }
        return TuplesKt.to(Integer.valueOf(b), Double.valueOf(m[b]));
    }

    public final Pair<Integer, Double> laneFromLeft() {
        Pair<Integer, Double> side = side(-1);
        int l = side.component1().intValue();
        double c = side.component2().doubleValue();
        return TuplesKt.to(Integer.valueOf(c >= 0.35d ? l + 1 : 0), Double.valueOf(c));
    }

    public final Pair<Integer, String> laneCount() {
        int i;
        Pair<Integer, Double> pair = total();
        int t = pair.component1().intValue();
        double c = pair.component2().doubleValue();
        String str = "";
        if (this.model.getLanes() > 0 && t == this.model.getTotal()) {
            Integer valueOf = Integer.valueOf(t);
            String src = this.model.getSrc();
            if (this.model.getExtraL() + this.model.getExtraR() > 0) {
                str = " + " + extraText();
            }
            return TuplesKt.to(valueOf, src + str);
        }
        if (this.model.getLanes() > 0 && t == this.model.getLanes()) {
            return TuplesKt.to(Integer.valueOf(t), this.model.getSrc());
        }
        if (c >= 0.5d) {
            i = Integer.valueOf(t);
            str = "camera, " + ((int) (100 * c)) + "%";
        } else {
            i = 0;
        }
        return TuplesKt.to(i, str);
    }

    public final int inExtra() {
        Pair<Integer, Double> side = side(-1);
        int l = side.component1().intValue();
        double cl = side.component2().doubleValue();
        Pair<Integer, Double> side2 = side(1);
        int r = side2.component1().intValue();
        double cr = side2.component2().doubleValue();
        int t = total().component1().intValue();
        if (this.model.getLanes() <= 0 || t != this.model.getTotal()) {
            return 0;
        }
        if (this.model.getExtraR() <= 0 || r >= this.model.getExtraR() || cr < 0.5d) {
            return (this.model.getExtraL() <= 0 || l >= this.model.getExtraL() || cl < 0.5d) ? 0 : -1;
        }
        return 1;
    }

    private final String extraText() {
        String str;
        String[] strArr = new String[2];
        String str2 = null;
        if (this.model.getExtraL() > 0) {
            str = (Intrinsics.areEqual(this.model.getKindL(), "exit") ? "exit" : "merge") + " lane left";
        } else {
            str = null;
        }
        strArr[0] = str;
        if (this.model.getExtraR() > 0) {
            str2 = (Intrinsics.areEqual(this.model.getKindR(), "exit") ? "exit" : "merge") + " lane right";
        }
        strArr[1] = str2;
        return CollectionsKt.joinToString$default(CollectionsKt.listOfNotNull((Object[]) strArr), " + ", null, null, 0, null, null, 62, null);
    }

    public final String label() {
        String str;
        int n = laneFromLeft().component1().intValue();
        if (n <= 0) {
            return "Lane ?";
        }
        int ex = inExtra();
        RoadModel roadModel = this.model;
        String k = ex < 0 ? roadModel.getKindL() : roadModel.getKindR();
        if (ex != 0) {
            return (Intrinsics.areEqual(k, "exit") ? "Exit" : "Merge") + " lane (" + (ex < 0 ? "left" : "right") + " of " + this.model.getLanes() + " lanes)";
        }
        int t = laneCount().component1().intValue();
        int tt = total().component1().intValue();
        if (this.model.getLanes() <= 0 || tt != this.model.getTotal() || this.model.getExtraL() + this.model.getExtraR() <= 0) {
            return "Lane " + n + " of " + (t >= n ? String.valueOf(t) : "?");
        }
        int lane = n - this.model.getExtraL();
        if (lane > this.model.getLanes() && this.model.getExtraR() > 0) {
            return (Intrinsics.areEqual(this.model.getKindR(), "exit") ? "Exit" : "Merge") + " lane (right of " + this.model.getLanes() + " lanes)";
        }
        if (lane < 1 && this.model.getExtraL() > 0) {
            return (Intrinsics.areEqual(this.model.getKindL(), "exit") ? "Exit" : "Merge") + " lane (left of " + this.model.getLanes() + " lanes)";
        }
        int lanes = this.model.getLanes();
        String str2 = "";
        if (this.model.getExtraL() > 0) {
            str = " + " + (Intrinsics.areEqual(this.model.getKindL(), "exit") ? "exit" : "merge") + " lane ←";
        } else {
            str = "";
        }
        if (this.model.getExtraR() > 0) {
            str2 = " + " + (Intrinsics.areEqual(this.model.getKindR(), "exit") ? "exit" : "merge") + " lane →";
        }
        return "Lane " + lane + " of " + lanes + str + str2;
    }

    public final void update(Frame f) {
        String str;
        Intrinsics.checkNotNullParameter(f, "f");
        long now = f.getNowMs();
        double dt = this.lastMs == 0 ? 0.0d : RangesKt.coerceIn((now - this.lastMs) / 1000.0d, 0.0d, 0.5d);
        this.lastMs = now;
        this.odo += f.getVEgo() * dt;
        float yawRate = f.getYawRate();
        if ((Float.isInfinite(yawRate) || Float.isNaN(yawRate)) ? false : true) {
            this.heading += f.getYawRate() * dt;
        }
        if (this.odo - this.lastBlurOdo >= 20.0d) {
            blur(this.odo - this.lastBlurOdo);
            this.lastBlurOdo = this.odo;
        }
        roadUpdate(f, now);
        detectTurn(f, now);
        checkOpChange(f, now);
        detectLaneChange(f, now);
        this.memory.update(this.odo, CollectionsKt.listOf((Object[]) new Pair[]{TuplesKt.to(Float.valueOf(f.getYL2()), Float.valueOf(f.getPL2())), TuplesKt.to(Float.valueOf(f.getYL()), Float.valueOf(f.getPL())), TuplesKt.to(Float.valueOf(f.getYR()), Float.valueOf(f.getPR())), TuplesKt.to(Float.valueOf(f.getYR2()), Float.valueOf(f.getPR2()))}), f.getEdgeL(), f.getEdgeR(), (f.getOpChange() == 0 && this.opDir == 0 && f.getVEgo() >= 2.0f) ? false : true);
        if (f.getVEgo() >= 2.0f) {
            paint(f, now);
            outerLines(f, now);
            edges(f, now);
            cars(f, now);
        }
        blindSpot(f, now);
        rtk(f, now);
        answer();
        if (f.getVEgo() > 1.0f) {
            this.driveS += dt;
            if (laneFromLeft().getSecond().doubleValue() >= 0.5d) {
                this.knownS += dt;
            }
        }
        String a = label();
        if (!Intrinsics.areEqual(a, this.prevAnswer)) {
            if ((this.prevAnswer.length() > 0) || !Intrinsics.areEqual(a, "Lane ?")) {
                Event firstOrNull = this.why.firstOrNull();
                if (firstOrNull == null || (str = firstOrNull.getText()) == null) {
                    str = "start";
                }
                log(now, a + "  (" + str + ")");
            }
            this.prevAnswer = a;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:40:0x01e1, code lost:
    
        if (r1 == null) goto L80;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final void roadUpdate(Frame f, long now) {
        String str;
        String str2;
        boolean z;
        double d;
        String str3;
        int i;
        String str4;
        String name;
        Road r = f.getRoad();
        Road was = this.road;
        if (r != null) {
            this.road = r;
        }
        this.info = f.getInfo();
        RoadModel old = this.model;
        this.model = roadModel(this.road, this.info);
        if (this.model.getLanes() != old.getLanes() || this.model.getExtraL() != old.getExtraL() || this.model.getExtraR() != old.getExtraR() || (r != null && was != null && !Intrinsics.areEqual(r.getName(), was.getName()))) {
            this.roadChangeMs = now;
        }
        if (old.getLanes() > 0 || this.model.getLanes() > 0) {
            if (!roadUpdate$ours(this, now, -1, this.model.getExtraL() - old.getExtraL())) {
                lanesOnSide(-1, this.model.getExtraL() - old.getExtraL());
            }
            if (!roadUpdate$ours(this, now, 1, this.model.getExtraR() - old.getExtraR())) {
                lanesOnSide(1, this.model.getExtraR() - old.getExtraR());
            }
            if (this.model.getExtraL() != old.getExtraL() || this.model.getExtraR() != old.getExtraR()) {
                if (this.model.getExtraL() + this.model.getExtraR() > old.getExtraL() + old.getExtraR()) {
                    String extraText = extraText();
                    RoadInfo roadInfo = this.info;
                    if (roadInfo == null || (str2 = roadInfo.getExtraWhy()) == null) {
                        str2 = "map";
                    }
                    str = extraText + " (" + str2 + ")";
                } else {
                    str = "extra lane ended";
                }
                note(now, str);
            }
        }
        if (r == null) {
            z = true;
        } else if (was == null || was.getId() != r.getId()) {
            if (was != null && (!Intrinsics.areEqual(was.getName(), r.getName()) || was.getHighway() != r.getHighway() || was.getRamp() != r.getRamp())) {
                this.memory.clear();
                blur(600.0d);
            }
            if (this.model.getLanes() == 1) {
                z = true;
                restart(-1, 0, 0.85d, now, this.model.getSrc() + ": 1 lane our way");
            } else {
                z = true;
            }
        } else {
            z = true;
        }
        if (f.getEntrySide() != 0) {
            this.entrySide = f.getEntrySide();
            this.entryMs = now;
            this.roadChangeMs = now;
            if (f.getEntrySide() < 0) {
                d = 0.75d;
                str3 = "joined from a left ramp";
                i = -1;
            } else {
                d = 0.6d;
                str3 = "joined from a right ramp";
                i = 1;
            }
            restart(i, 0, d, now, str3);
            this.nRamps++;
            String str5 = f.getEntrySide() < 0 ? "LEFT" : "RIGHT";
            if (r != null && (name = r.getName()) != null) {
                String str6 = name;
                if (str6.length() == 0 ? z : false) {
                    str6 = r.getKind();
                }
                str4 = str6;
            }
            str4 = "?";
            log(now, "highway entry from the " + str5 + " (" + str4 + ")");
        }
    }

    private static final boolean roadUpdate$ours(LaneBrain this$0, long $now, int side, int d) {
        return d > 0 && this$0.entrySide == side && $now - this$0.entryMs < 20000;
    }

    private final void detectTurn(Frame f, long now) {
        float yawRate = f.getYawRate();
        if ((Float.isInfinite(yawRate) || Float.isNaN(yawRate)) ? false : true) {
            this.hist.addLast(new double[]{now, this.heading, this.odo});
            while (!this.hist.isEmpty() && now - this.hist.first()[0] > 12000.0d) {
                this.hist.removeFirst();
            }
            if (Math.abs(f.getYawRate()) >= 0.05f && f.getVEgo() >= 1.0f) {
                this.straightSince = 0L;
            } else if (this.straightSince == 0) {
                this.straightSince = now;
            }
            if (f.getVEgo() > 13.5f || this.straightSince == 0 || now - this.straightSince < 1500 || this.hist.size() < 5) {
                return;
            }
            double deg = 0.0d;
            Iterator it = this.hist.iterator();
            while (it.hasNext()) {
                double[] h = (double[]) it.next();
                if (Math.abs(this.heading - h[1]) > Math.abs(deg)) {
                    deg = this.heading - h[1];
                }
            }
            double deg2 = Math.toDegrees(deg);
            if (Math.abs(deg2) < 55.0d) {
                return;
            }
            this.hist.clear();
            this.memory.clear();
            this.nTurns++;
            if (this.lastCameraMs < this.straightSince) {
                restart(deg2 > 0.0d ? -1 : 1, 0, 0.45d, now, deg2 > 0.0d ? "left turn: usually the leftmost lane" : "right turn: usually the rightmost lane");
            }
            log(now, (deg2 > 0.0d ? "LEFT" : "RIGHT") + " turn (" + ((int) Math.abs(deg2)) + "°)");
        }
    }

    /* compiled from: LaneBrain.kt */
    private static final class Pending {
        private final long atMs;
        private final int dir;

        public Pending(int dir, long atMs) {
            this.dir = dir;
            this.atMs = atMs;
        }

        public final long getAtMs() {
            return this.atMs;
        }

        public final int getDir() {
            return this.dir;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x0089  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final void detectLaneChange(Frame f, long now) {
        boolean ok;
        Pending it;
        int dir;
        if (f.getOpChange() != 0 || this.opDir != 0) {
            return;
        }
        if (f.getPL() > 0.25f && f.getPR() > 0.25f) {
            float yl = f.getYL();
            if ((Float.isInfinite(yl) || Float.isNaN(yl)) ? false : true) {
                float yr = f.getYR();
                if (((Float.isInfinite(yr) || Float.isNaN(yr)) ? false : true) && f.getYL() < 0.3f && f.getYR() > -0.3f) {
                    float yr2 = f.getYR() - f.getYL();
                    if (2.3f <= yr2 && yr2 <= 4.9f) {
                        ok = true;
                        if (ok) {
                            float w = f.getYR() - f.getYL();
                            float c = (f.getYL() + f.getYR()) / 2.0f;
                            if (Math.abs(c) < 0.6f) {
                                this.laneW += (RangesKt.coerceIn(w, 2.6f, 4.2f) - this.laneW) * 0.02f;
                            }
                            if (this.prevMs > 0 && now - this.prevMs < 700) {
                                float dc = c - this.prevC;
                                if (dc >= (-0.55f) * w || Math.abs(this.prevYL) >= 1.3f || Math.abs(f.getYR() - this.prevYL) >= 0.9f) {
                                    dir = (dc <= 0.55f * w || Math.abs(this.prevYR) >= 1.3f || Math.abs(f.getYL() - this.prevYR) >= 0.9f) ? 0 : 1;
                                } else {
                                    dir = -1;
                                }
                                if (dir != 0) {
                                    Pending pd = this.pending;
                                    if (pd != null && pd.getDir() == (-dir) && now - pd.getAtMs() < 1500) {
                                        this.pending = null;
                                        if (now - pd.getAtMs() > 300) {
                                            this.nUndone++;
                                            log(now, "lane change " + dirName(pd.getDir()) + " undone - not counted");
                                        }
                                    } else {
                                        if (pd != null) {
                                            apply(pd, "lines");
                                        }
                                        this.pending = new Pending(dir, now);
                                    }
                                }
                            }
                            this.prevC = c;
                            this.prevYL = f.getYL();
                            this.prevYR = f.getYR();
                            this.prevMs = now;
                        }
                        it = this.pending;
                        if (it != null || now - it.getAtMs() < 600) {
                        }
                        if (now - it.getAtMs() >= 1500 || ok) {
                            apply(it, "lines");
                            this.pending = null;
                            return;
                        }
                        return;
                    }
                }
            }
        }
        ok = false;
        if (ok) {
        }
        it = this.pending;
        if (it != null) {
        }
    }

    private final void checkOpChange(Frame f, long now) {
        if (f.getOpChange() != 0 && this.opDir == 0) {
            this.opDir = f.getOpChange();
            this.opSince = now;
            this.opCounted = false;
            this.pending = null;
        }
        if (this.opDir != 0 && f.getOpFinishing() && !this.opCounted) {
            this.opCounted = true;
            apply(new Pending(this.opDir, now), "openpilot");
        }
        if (f.getOpChange() == 0 && this.opDir != 0) {
            long dur = now - this.opSince;
            if (!this.opCounted) {
                float opLcProb = f.getOpLcProb();
                if (((Float.isInfinite(opLcProb) || Float.isNaN(opLcProb)) ? false : true) && f.getOpLcProb() < 0.1f) {
                    if (400 <= dur && dur < 10001) {
                        this.opCounted = true;
                        apply(new Pending(this.opDir, now), "openpilot");
                    }
                }
            }
            if (!this.opCounted) {
                this.nOpMissed++;
                log(now, "openpilot lane change " + dirName(this.opDir) + " ended without finishing - not counted");
            }
            this.opDir = 0;
            this.prevMs = 0L;
        }
    }

    private final String dirName(int d) {
        return d < 0 ? "LEFT" : "RIGHT";
    }

    private final void apply(Pending pd, String by) {
        double s = sideways(pd.getAtMs() - 4000, this.lastMs);
        double sure = 0.95d;
        if (!Intrinsics.areEqual(by, "openpilot")) {
            if (Intrinsics.areEqual(by, "rtk")) {
                sure = 0.85d;
            } else {
                if (!((Double.isInfinite(s) || Double.isNaN(s)) ? false : true)) {
                    sure = 0.9d;
                } else if (Math.abs(s) < 0.3d) {
                    log(pd.getAtMs(), "RTK saw no sideways move - lane change " + dirName(pd.getDir()) + " doubted");
                    sure = 0.5d;
                }
            }
        }
        this.lastChangeMs = pd.getAtMs();
        this.lastChangeDir = pd.getDir();
        this.nChanges++;
        moved(pd.getDir(), sure, pd.getAtMs(), "lane change " + (pd.getDir() < 0 ? "left" : "right") + (!Intrinsics.areEqual(by, "lines") ? " (" + by + ")" : ""));
        if (Intrinsics.areEqual(by, "openpilot")) {
            this.memory.shift(pd.getDir() * this.laneW);
        }
        log(pd.getAtMs(), "lane change " + dirName(pd.getDir()) + (Intrinsics.areEqual(by, "lines") ? "" : " (" + by + ")"));
    }

    private final double sideways(long from, long to) {
        char c;
        Object element$iv;
        Object obj;
        List $this$lastOrNull$iv = this.rtkLog;
        ListIterator iterator$iv = $this$lastOrNull$iv.listIterator($this$lastOrNull$iv.size());
        while (true) {
            c = 0;
            if (iterator$iv.hasPrevious()) {
                element$iv = iterator$iv.previous();
                double[] it = (double[]) element$iv;
                if (it[0] <= ((double) (from + ((long) 500))) && it[0] >= ((double) (from - ((long) 1500)))) {
                    break;
                }
            } else {
                element$iv = null;
                break;
            }
        }
        double[] a = (double[]) element$iv;
        if (a == null) {
            return Double.NaN;
        }
        List $this$lastOrNull$iv2 = this.rtkLog;
        int $i$f$lastOrNull = 0;
        ListIterator iterator$iv2 = $this$lastOrNull$iv2.listIterator($this$lastOrNull$iv2.size());
        while (true) {
            if (!iterator$iv2.hasPrevious()) {
                obj = null;
                break;
            }
            Object element$iv2 = iterator$iv2.previous();
            int $i$f$lastOrNull2 = $i$f$lastOrNull;
            if (((double[]) element$iv2)[c] <= ((double) to)) {
                obj = element$iv2;
                break;
            }
            $i$f$lastOrNull = $i$f$lastOrNull2;
            c = 0;
        }
        double[] b = (double[]) obj;
        if (b == null) {
            return Double.NaN;
        }
        if (a[2] == b[2]) {
            return (b[1] - a[1]) / this.laneW;
        }
        return Double.NaN;
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x007b  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0081  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x007e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final void camNote(Frame f, long now) {
        boolean ok;
        if (f.getPL() > 0.4f && f.getPR() > 0.4f) {
            float yl = f.getYL();
            if ((Float.isInfinite(yl) || Float.isNaN(yl)) ? false : true) {
                float yr = f.getYR();
                if (((Float.isInfinite(yr) || Float.isNaN(yr)) ? false : true) && f.getYL() < 0.3f && f.getYR() > -0.3f) {
                    float yr2 = f.getYR() - f.getYL();
                    if (2.3f <= yr2 && yr2 <= 4.9f) {
                        ok = true;
                        this.camLog.addLast(new double[]{now, !ok ? 1.0d : 0.0d, ok ? Math.abs((f.getYL() + f.getYR()) / 2.0d) : 0.0d});
                        while (!this.camLog.isEmpty() && now - this.camLog.first()[0] > 15000.0d) {
                            this.camLog.removeFirst();
                        }
                        return;
                    }
                }
            }
        }
        ok = false;
        this.camLog.addLast(new double[]{now, !ok ? 1.0d : 0.0d, ok ? Math.abs((f.getYL() + f.getYR()) / 2.0d) : 0.0d});
        while (!this.camLog.isEmpty()) {
            this.camLog.removeFirst();
        }
    }

    private final boolean cameraStayed(long from, long to) {
        Iterator it;
        int n = 0;
        int seen = 0;
        double off = 0.0d;
        Iterator it2 = this.camLog.iterator();
        while (it2.hasNext()) {
            double[] c = (double[]) it2.next();
            if (c[0] >= from) {
                it = it2;
                if (c[0] <= to) {
                    n++;
                    if (c[1] > 0.5d) {
                        seen++;
                        off = Math.max(off, c[2]);
                        it2 = it;
                    } else {
                        it2 = it;
                    }
                }
            } else {
                it = it2;
            }
            it2 = it;
        }
        return n >= 20 && ((double) seen) >= ((double) n) * 0.7d && off < 1.0d;
    }

    private final void rtk(Frame f, long now) {
        Iterable $this$any$iv;
        String doubt;
        camNote(f, now);
        double rtkOffset = f.getRtkOffset();
        boolean z = false;
        if (!((Double.isInfinite(rtkOffset) || Double.isNaN(rtkOffset)) ? false : true) || f.getRtkSigma() > 1.2d) {
            return;
        }
        this.rtkLog.addLast(new double[]{now, f.getRtkOffset(), f.getRtkWay()});
        while (!this.rtkLog.isEmpty() && now - this.rtkLog.first()[0] > 15000.0d) {
            this.rtkLog.removeFirst();
        }
        Iterable $this$any$iv2 = this.hist;
        if (!($this$any$iv2 instanceof Collection) || !((Collection) $this$any$iv2).isEmpty()) {
            Iterator<double[]> it = $this$any$iv2.iterator();
            while (true) {
                if (it.hasNext()) {
                    Object element$iv = it.next();
                    double[] it2 = (double[]) element$iv;
                    if (((((double) now) - it2[0] > 8000.0d || Math.abs(this.heading - it2[1]) <= Math.toRadians(10.0d)) ? null : 1) != null) {
                        $this$any$iv = 1;
                        break;
                    }
                } else {
                    $this$any$iv = null;
                    break;
                }
            }
        } else {
            $this$any$iv = null;
        }
        if (f.getVEgo() < 6.0f || $this$any$iv != null || this.pending != null || this.opDir != 0 || now - this.rtkCheckMs < 1000 || now - this.lastChangeMs < 10000) {
            return;
        }
        this.rtkCheckMs = now;
        double s = sideways(now - 7000, now - 1000);
        if (!Double.isInfinite(s) && !Double.isNaN(s)) {
            z = true;
        }
        if (!z || Math.abs(s) < 0.75d) {
            return;
        }
        int dir = s > 0.0d ? 1 : -1;
        if (now - this.roadChangeMs < 9000) {
            doubt = "the road changed here";
        } else {
            doubt = cameraStayed(now - ((long) 8000), now) ? "the camera saw us stay in our lane" : null;
        }
        if (doubt == null) {
            apply(new Pending(dir, now), "rtk");
        } else if (now - this.rtkDoubtMs > 10000) {
            this.rtkDoubtMs = now;
            String format = String.format("%.1f", Arrays.copyOf(new Object[]{Double.valueOf(Math.abs(s))}, 1));
            Intrinsics.checkNotNullExpressionValue(format, "format(...)");
            log(now, "RTK moved " + format + " lanes " + dirName(dir) + " on the map, but " + doubt + " - not counted");
        }
    }

    /* compiled from: LaneBrain.kt */
    private static final class Vote {
        private final ArrayDeque<long[]> q = new ArrayDeque<>();
        private long used;

        public final void clear() {
            this.q.clear();
        }

        public final ArrayDeque<long[]> getQ() {
            return this.q;
        }

        public final long getUsed() {
            return this.used;
        }

        public final void setUsed(long j) {
            this.used = j;
        }
    }

    private final Integer vote(Vote vt, int v, long now, long holdMs) {
        Object next;
        vt.getQ().addLast(new long[]{now, v});
        while (!vt.getQ().isEmpty() && now - vt.getQ().first()[0] > holdMs) {
            vt.getQ().removeFirst();
        }
        if (now - vt.getUsed() < 500 || vt.getQ().size() < 8 || now - vt.getQ().first()[0] < holdMs * 0.7d) {
            return null;
        }
        final Iterable $this$groupingBy$iv = vt.getQ();
        Iterator it = GroupingKt.eachCount(new Grouping<long[], Long>() { // from class: com.starpilot.starview.LaneBrain$vote$$inlined$groupingBy$1
            @Override // kotlin.collections.Grouping
            public Iterator<long[]> sourceIterator() {
                return $this$groupingBy$iv.iterator();
            }

            @Override // kotlin.collections.Grouping
            public Long keyOf(long[] element) {
                long[] it2 = element;
                return Long.valueOf(it2[1]);
            }
        }).entrySet().iterator();
        if (it.hasNext()) {
            next = it.next();
            if (it.hasNext()) {
                Map.Entry it2 = (Map.Entry) next;
                int intValue = ((Number) it2.getValue()).intValue();
                do {
                    Object next2 = it.next();
                    Map.Entry it3 = (Map.Entry) next2;
                    int intValue2 = ((Number) it3.getValue()).intValue();
                    if (intValue < intValue2) {
                        next = next2;
                        intValue = intValue2;
                    }
                } while (it.hasNext());
            }
        } else {
            next = null;
        }
        Map.Entry best = (Map.Entry) next;
        if (best == null || ((Number) best.getValue()).intValue() < vt.getQ().size() * 0.75d) {
            return null;
        }
        vt.setUsed(now);
        return Integer.valueOf((int) ((Number) best.getKey()).longValue());
    }

    private final boolean centred(Frame f) {
        float yl = f.getYL();
        if ((Float.isInfinite(yl) || Float.isNaN(yl)) ? false : true) {
            float yr = f.getYR();
            if (((Float.isInfinite(yr) || Float.isNaN(yr)) ? false : true) && Math.abs((f.getYL() + f.getYR()) / 2.0f) < 0.6f && this.pending == null && this.opDir == 0) {
                return true;
            }
        }
        return false;
    }

    private final boolean solidMeansEdge() {
        return (this.model.getTunnel() || this.model.getBridge()) ? false : true;
    }

    /* JADX WARN: Removed duplicated region for block: B:77:0x0133  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x0135  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final void paint(Frame f, long now) {
        boolean z;
        Integer vote;
        LaneMarks.Mark lm = f.getLeftMark();
        LaneMarks.Mark rm = f.getRightMark();
        if (lm != null && lm.getKind() != 0) {
            boolean yellow = lm.getYellow() && (lm.getKind() == 3 || lm.getKind() == 2);
            Integer vote2 = vote(this.vYellow, yellow ? 1 : 0, now, 1000L);
            if (vote2 != null && vote2.intValue() == 1) {
                see(-1, 0, 0.9d, now, "yellow line on the left");
            }
            Integer vote3 = vote(this.vDashL, (lm.getYellow() || lm.getKind() != 2) ? 0 : 1, now, 1500L);
            if (vote3 != null && vote3.intValue() == 1) {
                atLeast(-1, 1, 0.6d, now, "dashed line on the left");
            }
        }
        if (rm != null && rm.getKind() != 0) {
            Integer vote4 = vote(this.vDashR, (rm.getYellow() || rm.getKind() != 2) ? 0 : 1, now, 1500L);
            if (vote4 != null && vote4.intValue() == 1) {
                atLeast(1, 1, 0.6d, now, "dashed line on the right");
            }
            Road road = this.road;
            if ((road != null && road.getHighway()) && solidMeansEdge() && !rm.getYellow() && !rm.getDouble() && rm.getKind() == 3) {
                float edgeR = f.getEdgeR();
                if ((Float.isInfinite(edgeR) || Float.isNaN(edgeR)) ? false : true) {
                    float yr = f.getYR();
                    if (((Float.isInfinite(yr) || Float.isNaN(yr)) ? false : true) && f.getEdgeR() - Math.abs(f.getYR()) < 1.6f) {
                        z = true;
                        boolean solidEdge = z;
                        vote = vote(this.vSolidR, !solidEdge ? 1 : 0, now, 1500L);
                        if (vote == null && vote.intValue() == 1) {
                            see(1, 0, 0.75d, now, "solid edge line, road edge past it");
                        }
                        return;
                    }
                }
            }
            z = false;
            boolean solidEdge2 = z;
            vote = vote(this.vSolidR, !solidEdge2 ? 1 : 0, now, 1500L);
            if (vote == null) {
                return;
            }
            see(1, 0, 0.75d, now, "solid edge line, road edge past it");
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x003d, code lost:
    
        if ((r0 != null && r0.getYellow()) == false) goto L28;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final void outerLines(Frame f, long now) {
        Road it;
        if (centred(f)) {
            Road it2 = this.road;
            boolean z = true;
            if (it2 != null) {
                it = (it2.getOneway() || it2.getHighway()) ? 1 : null;
            } else {
                it = f.getVEgo() > 17.0f ? 1 : null;
            }
            if (it != null) {
                LaneMarks.Mark leftMark = f.getLeftMark();
            }
            z = false;
            boolean leftOurs = z;
            if (leftOurs) {
                outerLines$side(this, now, -1, this.vOuterL, f.getPL(), f.getYL(), f.getPL2(), f.getYL2(), f.getEdgeL());
            } else {
                this.vOuterL.clear();
            }
            outerLines$side(this, now, 1, this.vOuterR, f.getPR(), f.getYR(), f.getPR2(), f.getYR2(), f.getEdgeR());
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:38:0x00ad  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00b0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static final void outerLines$side(LaneBrain this$0, long $now, int s, Vote vt, float pIn, float yIn, float pOut, float yOut, float edge) {
        int reading;
        Integer vote;
        if (pIn >= 0.5f) {
            if (!((Float.isInfinite(pOut) || Float.isNaN(pOut)) ? false : true)) {
                return;
            }
            float inner = Math.abs(yIn);
            float outer = Math.abs(yOut);
            if (pOut > 0.6f) {
                if ((Float.isInfinite(yOut) || Float.isNaN(yOut)) ? false : true) {
                    float f = outer - inner;
                    if (2.3f <= f && f <= 4.6f) {
                        if (!((Float.isInfinite(edge) || Float.isNaN(edge)) ? false : true) || edge - outer > -0.3f) {
                            reading = 1;
                            vote = this$0.vote(vt, reading, $now, 1500L);
                            if (vote == null || vote.intValue() != 0) {
                                if (vote == null && vote.intValue() == 1) {
                                    this$0.atLeast(s, 1, 0.7d, $now, "a lane line one lane over on the " + Companion.sideName(s));
                                }
                                return;
                            }
                            this$0.see(s, 0, !this$0.solidMeansEdge() ? 0.75d : 0.55d, $now, "no lane line past ours on the " + Companion.sideName(s) + ", road edge right there");
                            return;
                        }
                    }
                }
            }
            if (pOut < 0.15f) {
                if (!((Float.isInfinite(edge) || Float.isNaN(edge)) ? false : true) || edge - inner >= 1.6f) {
                    return;
                }
                reading = 0;
                vote = this$0.vote(vt, reading, $now, 1500L);
                if (vote == null) {
                    this$0.see(s, 0, !this$0.solidMeansEdge() ? 0.75d : 0.55d, $now, "no lane line past ours on the " + Companion.sideName(s) + ", road edge right there");
                    return;
                }
                if (vote == null) {
                    return;
                }
                this$0.atLeast(s, 1, 0.7d, $now, "a lane line one lane over on the " + Companion.sideName(s));
            }
        }
    }

    private final void edges(Frame f, long now) {
        Road it;
        float f2;
        if (centred(f)) {
            Road it2 = this.road;
            if (it2 != null) {
                it = (it2.getOneway() || it2.getHighway()) ? 1 : null;
            } else {
                it = null;
            }
            Road road = it;
            if (this.model.getBridge()) {
                f2 = 4.2f;
            } else {
                Road road2 = this.road;
                f2 = road2 != null && road2.getHighway() ? 3.8f : 2.8f;
            }
            float smax = f2;
            if (road != null) {
                edges$side$12(this, smax, now, -1, this.vEdgeL, f.getEdgeL(), f.getYL(), f.getPL(), f.getLeftMark(), f.getPL2());
            } else {
                this.vEdgeL.clear();
            }
            edges$side$12(this, smax, now, 1, this.vEdgeR, f.getEdgeR(), f.getYR(), f.getPR(), f.getRightMark(), f.getPR2());
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:39:0x0075  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00c7  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00cf  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0108  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x00ca  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static final void edges$side$12(LaneBrain this$0, float smax, long $now, int s, Vote vt, float edge, float line, float pLine, LaneMarks.Mark mark, float pOut) {
        boolean z;
        int lo;
        int hi;
        Integer vote;
        int b;
        if (!((Float.isInfinite(edge) || Float.isNaN(edge)) ? false : true)) {
            return;
        }
        if (!((Float.isInfinite(line) || Float.isNaN(line)) ? false : true) || pLine < 0.3f) {
            return;
        }
        float g = edge - Math.abs(line);
        if (g < -0.5f) {
            return;
        }
        if (mark != null && mark.getKind() == 3 && !mark.getYellow() && !mark.getDouble() && this$0.solidMeansEdge()) {
            if (!((Float.isInfinite(pOut) || Float.isNaN(pOut)) ? false : true) || pOut < 0.3f) {
                z = true;
                boolean loneSolid = z;
                lo = loneSolid ? 0 : RangesKt.coerceAtLeast((int) Math.ceil((g - smax) / this$0.laneW), 0);
                hi = RangesKt.coerceAtMost((int) Math.floor((0.4f + g) / this$0.laneW), 5);
                if (hi < lo && (vote = this$0.vote(vt, (lo * 10) + hi, $now, 2500L)) != null) {
                    int v = vote.intValue();
                    int a = v / 10;
                    b = v % 10;
                    double c = !this$0.model.getTunnel() ? 0.5d : 0.65d;
                    if (b != 0) {
                        this$0.see(s, 0, this$0.solidMeansEdge() ? 0.65d : 0.5d, $now, "road edge right past our " + Companion.sideName(s) + " line");
                        return;
                    } else {
                        if (a == b) {
                            this$0.see(s, a, c * 0.75d, $now, Companion.sideName(s) + " road edge " + a + " lane" + (a > 1 ? "s" : "") + " away");
                            return;
                        }
                        this$0.seeRange(s, a, b, c, $now, Companion.sideName(s) + " road edge " + a + "-" + b + " lanes away (shoulder width unknown)");
                        return;
                    }
                }
                return;
            }
        }
        z = false;
        boolean loneSolid2 = z;
        lo = loneSolid2 ? 0 : RangesKt.coerceAtLeast((int) Math.ceil((g - smax) / this$0.laneW), 0);
        hi = RangesKt.coerceAtMost((int) Math.floor((0.4f + g) / this$0.laneW), 5);
        if (hi < lo) {
            return;
        }
        int v2 = vote.intValue();
        int a2 = v2 / 10;
        b = v2 % 10;
        if (!this$0.model.getTunnel()) {
        }
        if (b != 0) {
        }
    }

    private final int held(ArrayDeque<long[]> q, long now) {
        int count$iv;
        while (!q.isEmpty() && now - q.first()[0] > 2000) {
            q.removeFirst();
        }
        for (int n = 4; n > 0; n--) {
            ArrayDeque<long[]> $this$count$iv = q;
            if (($this$count$iv instanceof Collection) && $this$count$iv.isEmpty()) {
                count$iv = 0;
            } else {
                count$iv = 0;
                for (Object element$iv : $this$count$iv) {
                    long[] it = (long[]) element$iv;
                    if ((it[1] >= ((long) n)) && (count$iv = count$iv + 1) < 0) {
                        CollectionsKt.throwCountOverflow();
                    }
                }
            }
            if (count$iv >= 6) {
                return n;
            }
        }
        return 0;
    }

    private final void cars(Frame f, long now) {
        if (f.getVEgo() < 3.0f) {
            return;
        }
        if (f.getCarsLeft() > 0) {
            this.carsL.addLast(new long[]{now, f.getCarsLeft()});
        }
        if (f.getCarsRight() > 0) {
            this.carsR.addLast(new long[]{now, f.getCarsRight()});
        }
        if (f.getOncomingLeft()) {
            this.oncoming.addLast(Long.valueOf(now));
        }
        while (!this.oncoming.isEmpty() && now - this.oncoming.first().longValue() > 2000) {
            this.oncoming.removeFirst();
        }
        if (now - this.carsUsedMs < 1000) {
            return;
        }
        int l = held(this.carsL, now);
        int r = held(this.carsR, now);
        if (l > 0) {
            atLeast(-1, l, 0.65d, now, "car driving our way " + l + " lane" + (l > 1 ? "s" : "") + " to the left");
        }
        if (r > 0) {
            atLeast(1, r, 0.65d, now, "car driving our way " + r + " lane" + (r > 1 ? "s" : "") + " to the right");
        }
        if (this.oncoming.size() >= 6 && l == 0) {
            see(-1, 0, 0.7d, now, "oncoming traffic in the lane to our left");
        }
        if (l > 0 || r > 0 || this.oncoming.size() >= 6) {
            this.carsUsedMs = now;
        }
    }

    private final void blindSpot(Frame f, long now) {
        if (f.getVEgo() >= 8.0f) {
            if ((!f.getBsmL() && !f.getBsmR()) || now - this.bsmMs < 1000) {
                return;
            }
            this.bsmMs = now;
            if (f.getBsmL()) {
                atLeast(-1, 1, 0.6d, now, "car in the left blind spot");
            }
            if (f.getBsmR()) {
                atLeast(1, 1, 0.6d, now, "car in the right blind spot");
            }
        }
    }

    public final void roadLaneEnded(int side, long now) {
        lanesOnSide(side, -1);
        note(now, "the " + Companion.sideName(side) + " lane ended");
        answer();
        this.roadChangeMs = now;
        log(now, (side < 0 ? "left" : "right") + " lane ended (road data)");
    }

    public static /* synthetic */ boolean turning$default(LaneBrain laneBrain, long j, int i, Object obj) {
        if ((i & 1) != 0) {
            j = laneBrain.lastMs;
        }
        return laneBrain.turning(j);
    }

    public final boolean turning(long now) {
        Iterable $this$any$iv = this.hist;
        if (($this$any$iv instanceof Collection) && ((Collection) $this$any$iv).isEmpty()) {
            return false;
        }
        for (Object element$iv : $this$any$iv) {
            double[] it = (double[]) element$iv;
            if (((((double) now) - it[0] > 8000.0d || Math.abs(this.heading - it[1]) <= Math.toRadians(10.0d)) ? null : 1) != null) {
                return true;
            }
        }
        return false;
    }

    public static /* synthetic */ void check$default(LaneBrain laneBrain, boolean z, long j, int i, Object obj) {
        if ((i & 2) != 0) {
            j = laneBrain.lastMs;
        }
        laneBrain.check(z, j);
    }

    public final void check(boolean ok, long now) {
        int l = side(-1).component1().intValue();
        int r = side(1).component1().intValue();
        if (ok) {
            see(-1, l, 0.9d, now, "your check");
            see(1, r, 0.9d, now, "your check");
        } else {
            double[] dArr = this.p[l];
            dArr[r] = dArr[r] * 0.1d;
            norm();
            note(now, "your ✗");
        }
        answer();
    }

    /* JADX WARN: Removed duplicated region for block: B:68:0x016f  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x0187  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void setCount(int m0, int lane, boolean remember, long now, int extraL, int extraR, String kind, String kindR) {
        String str;
        int m;
        Road r;
        RoadModel roadModel;
        int m2;
        boolean z;
        StringBuilder sb;
        StringBuilder append;
        String str2;
        Intrinsics.checkNotNullParameter(kind, "kind");
        Intrinsics.checkNotNullParameter(kindR, "kindR");
        Road r2 = this.road;
        if (m0 <= 0) {
            if (r2 != null && this.myCounts.remove(Long.valueOf(r2.getId())) != null) {
                Function0<Unit> function0 = this.onCountsChanged;
                if (function0 != null) {
                    function0.invoke();
                }
                String name = r2.getName();
                if (name.length() == 0) {
                    name = r2.getKind();
                }
                log(now, "your count for " + ((Object) name) + " forgotten");
            }
            this.model = roadModel(this.road, this.info);
            answer();
            return;
        }
        if (r2 != null && remember) {
            this.myCounts.put(Long.valueOf(r2.getId()), Integer.valueOf(m0));
            Function0<Unit> function02 = this.onCountsChanged;
            if (function02 != null) {
                function02.invoke();
            }
        }
        int m3 = m0 + extraL + extraR;
        if (1 <= lane && lane <= m3) {
            int l = 0;
            while (l < 6) {
                int rr = 0;
                while (rr < 6) {
                    this.p[l][rr] = (l == lane + (-1) && rr == m3 - lane) ? 0.97d : 8.571428571428571E-4d;
                    rr++;
                }
                l++;
            }
            norm();
            this.holdUntil = (extraL + extraR > 0 ? 60000L : 120000L) + now;
            note(now, "you: lane " + lane + " of " + m3);
        }
        RoadModel rm = roadModel(this.road, this.info);
        if (extraL + extraR <= 0 || rm.getExtraL() + rm.getExtraR() != 0) {
            str = " of ";
            m = m3;
            r = r2;
            roadModel = rm;
        } else {
            String str3 = kindR;
            if (str3.length() == 0) {
                str3 = kind;
            }
            str = " of ";
            m = m3;
            r = r2;
            roadModel = new RoadModel(m0, "your count", 0.97d, extraL, extraR, kind, str3, rm.getTunnel(), rm.getBridge());
        }
        this.model = roadModel;
        answer();
        if (1 <= lane) {
            m2 = m;
            if (lane <= m2) {
                z = true;
                if (z) {
                    sb = new StringBuilder();
                    append = sb.append(m2).append(" lanes");
                } else {
                    sb = new StringBuilder();
                    append = sb.append("lane ").append(lane).append(str).append(m2);
                }
                String sb2 = append.toString();
                if (r == null && remember) {
                    String name2 = r.getName();
                    if (name2.length() == 0) {
                        r16 = true;
                    }
                    if (r16) {
                        name2 = r.getKind();
                    }
                    str2 = " - " + m0 + " remembered for " + ((Object) name2);
                } else {
                    str2 = "";
                }
                log(now, "you set " + sb2 + str2);
            }
        } else {
            m2 = m;
        }
        z = false;
        if (z) {
        }
        String sb22 = append.toString();
        if (r == null) {
        }
        str2 = "";
        log(now, "you set " + sb22 + str2);
    }

    public final String getModelText() {
        return this.modelText;
    }

    public final boolean modelFresh() {
        return (this.modelText.length() > 0) && System.currentTimeMillis() - this.modelMs < 6000;
    }

    public static /* synthetic */ void modelSaw$default(LaneBrain laneBrain, float[] fArr, float[] fArr2, long j, long j2, int i, Object obj) {
        if ((i & 8) != 0) {
            j2 = laneBrain.lastMs;
        }
        laneBrain.modelSaw(fArr, fArr2, j, j2);
    }

    public final void modelSaw(float[] pL, float[] pR, long atMs, long now) {
        String txt;
        int rr;
        Intrinsics.checkNotNullParameter(pL, "pL");
        Intrinsics.checkNotNullParameter(pR, "pR");
        if (atMs <= this.modelMs) {
            return;
        }
        this.modelMs = atMs;
        Road r = this.road;
        boolean city = (r == null || r.getHighway() || r.getRamp()) ? false : true;
        int l = modelSaw$best(pL);
        int rr2 = modelSaw$best(pR);
        float f = 100;
        String txt2 = "model: " + (l == 3 ? "3+" : String.valueOf(l)) + " left, " + (rr2 != 3 ? String.valueOf(rr2) : "3+") + " right (" + ((int) (pL[l] * f)) + "% / " + ((int) (pR[rr2] * f)) + "%)";
        this.modelText = city ? txt2 : txt2 + " - not used on highways";
        if (city) {
            if (pL[l] < 0.6f) {
                txt = txt2;
                rr = rr2;
            } else if (l == 3) {
                txt = txt2;
                rr = rr2;
                atLeast(-1, 3, 0.6d, now, txt);
            } else {
                txt = txt2;
                rr = rr2;
                see(-1, l, 0.6d, now, txt);
            }
            if (pR[rr] >= 0.6f) {
                if (rr == 3) {
                    atLeast(1, 3, 0.6d, now, txt);
                } else {
                    see(1, rr, 0.6d, now, txt);
                }
            }
            answer();
        }
    }

    private static final int modelSaw$best(float[] a) {
        Object maxElem$iv;
        Iterable $this$maxByOrNull$iv = ArraysKt.getIndices(a);
        Iterator iterator$iv = $this$maxByOrNull$iv.iterator();
        if (iterator$iv.hasNext()) {
            maxElem$iv = iterator$iv.next();
            if (iterator$iv.hasNext()) {
                int it = ((Number) maxElem$iv).intValue();
                float maxValue$iv = a[it];
                do {
                    Object e$iv = iterator$iv.next();
                    int it2 = ((Number) e$iv).intValue();
                    float v$iv = a[it2];
                    if (Float.compare(maxValue$iv, v$iv) < 0) {
                        maxElem$iv = e$iv;
                        maxValue$iv = v$iv;
                    }
                } while (iterator$iv.hasNext());
            }
        } else {
            maxElem$iv = null;
        }
        Integer num = (Integer) maxElem$iv;
        if (num != null) {
            return num.intValue();
        }
        return 0;
    }

    private final void log(long now, String text) {
        if (this.events.size() < 2 || !Intrinsics.areEqual(this.events.get(1).getText(), text) || now - this.events.get(1).getAtMs() >= 5000) {
            Event e = new Event(now, text);
            this.events.addFirst(e);
            while (this.events.size() > 80) {
                this.events.removeLast();
            }
            Function1<? super Event, Unit> function1 = this.onEvent;
            if (function1 != null) {
                function1.invoke(e);
            }
        }
    }

    public final void endDrive() {
        if (this.driveS > 30.0d) {
            Long valueOf = Long.valueOf(this.lastMs);
            long it = valueOf.longValue();
            if (!(it > 0)) {
                valueOf = null;
            }
            log(valueOf != null ? valueOf.longValue() : System.currentTimeMillis(), "drive summary: " + summary());
        }
        reset$default(this, false, 1, null);
    }

    public static /* synthetic */ void reset$default(LaneBrain laneBrain, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = true;
        }
        laneBrain.reset(z);
    }

    public final void reset(boolean keepCounts) {
        this.p = Companion.prior();
        this.q = Companion.prior();
        this.road = null;
        this.info = null;
        this.model = new RoadModel(0, "", 0.0d, 0, 0, "", "", false, false);
        this.memory.clear();
        this.why.clear();
        this.pending = null;
        this.prevMs = 0L;
        this.hist.clear();
        this.opDir = 0;
        this.lastMs = 0L;
        this.holdUntil = 0L;
        this.rtkLog.clear();
        this.prevAnswer = "";
        this.camLog.clear();
        this.roadChangeMs = 0L;
        this.rtkDoubtMs = 0L;
        for (Vote v : CollectionsKt.listOf((Object[]) new Vote[]{this.vYellow, this.vDashL, this.vDashR, this.vSolidR, this.vOuterL, this.vOuterR, this.vEdgeL, this.vEdgeR})) {
            v.clear();
        }
        this.carsL.clear();
        this.carsR.clear();
        this.oncoming.clear();
        this.nChanges = 0;
        this.nUndone = 0;
        this.nOpMissed = 0;
        this.nTurns = 0;
        this.nRamps = 0;
        this.driveS = 0.0d;
        this.knownS = 0.0d;
        this.lastCameraMs = 0L;
        if (!keepCounts) {
            this.myCounts.clear();
        }
    }
}
