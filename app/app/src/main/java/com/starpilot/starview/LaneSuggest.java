package com.starpilot.starview;

import com.starpilot.starview.Nav;
import com.starpilot.starview.TurnSignal;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import org.json.JSONObject;

/* compiled from: LaneSuggest.kt */
public final class LaneSuggest {
    public static final long AUTO_MS = 3000;
    public static final float CAR_NUDGE = 100.0f;
    public static final long CLEAR_MS = 1000;
    public static final Companion Companion = new Companion(null);
    public static final long EXIT_GAP_MS = 1500;
    public static final double EXIT_LEAD_S = 8.0d;
    public static final int EXIT_MAX_TRIES = 5;
    public static final double EXIT_MIN_M = 25.0d;
    public static final double EXIT_START_MAX_M = 320.0d;
    public static final double EXIT_START_MIN_M = 120.0d;
    public static final long EXIT_TRY_MS = 6000;
    public static final float LIGHT_NUDGE = 60.0f;
    public static final long LIGHT_NUDGE_MS = 150;
    public static final float PASS_HEADWAY_S = 4.0f;
    public static final float PASS_HELD_BACK = 1.34f;
    public static final long PASS_HOLD_MS = 5000;
    public static final float PASS_MARGIN = 2.235f;
    public static final float PASS_MAX_DIST = 120.0f;
    public static final float PASS_MIN_SPEED = 13.41f;
    public static final long RETURN_AFTER_MS = 8000;
    public static final float RETURN_FREE_S = 6.0f;
    public static final float RETURN_GAP_S = 2.0f;
    public static final long RETURN_HOLD_MS = 3000;
    public static final long RETURN_WINDOW_MS = 180000;
    public static final long ROUTE_HOLD_MS = 2000;
    public static final double TURN_LANE_M = 92.0d;
    public static final long WAIT_MODEL_MS = 30000;
    public static final long WAIT_MS = 60000;
    private boolean armed;
    private boolean autoAsk;
    private boolean beepNow;
    private long clearSince;
    private int dir;
    private long exitRetryAt;
    private int exitTries;
    private long firstWait;
    private int holdSide;
    private long lastBeep;
    private long lastMs;
    private int lcDir;
    private long lightSince;
    private long mergeLostSince;
    private float nudgeTorque;
    private long owedAt;
    private int owedRight;
    private long passLostSince;
    private long passSince;
    private boolean reasked;
    private long returnLostSince;
    private long returnSince;
    private long routeLostSince;
    private long routeSince;
    private int routeSinceDir;
    private long since;
    private boolean test;
    private long waitSince;
    private State state = State.IDLE;
    private String why = "";
    private String kind = "";
    private int homeLeft = -1;
    private String exitKey = "";
    private String exitDoneKey = "";
    private String result = "";
    private final long[] cool = new long[2];
    private final float[] ema = new float[2];
    private final long[] highSince = new long[2];
    private String confirmedBy = "";

    /* compiled from: LaneSuggest.kt */
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[State.values().length];
            try {
                iArr[State.IDLE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[State.RESULT.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[State.ASKING.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                iArr[State.WAITING.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                iArr[State.SIGNALING.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                iArr[State.CHANGING.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* compiled from: LaneSuggest.kt */
    public enum State {
        IDLE,
        ASKING,
        WAITING,
        SIGNALING,
        CHANGING,
        RESULT;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<State> getEntries() {
            return $ENTRIES;
        }
    }

    /* compiled from: LaneSuggest.kt */
    public static final class In {
        private final boolean auto;
        private final boolean brake;
        private final boolean busy;
        private final boolean commaConfirms;
        private final boolean engaged;
        private final int exitDir;
        private final double exitDist;
        private final boolean exitInLane;
        private final String exitKey;
        private final boolean exitOn;
        private final int exitSideLanes;
        private final String exitWhy;
        private final boolean highway;
        private final int laneChangeDir;
        private final boolean laneChanging;
        private final int lanesTotal;
        private final float leadD;
        private final boolean leadOn;
        private final float leadV;
        private final boolean leftBlinker;
        private final boolean leftBsm;
        private final int leftLanes;
        private final int mergeDir;
        private final String mergeWhy;
        private final boolean metric;
        private final float minLcSpeed;
        private final long nowMs;
        private final boolean nudgeConfirm;
        private final float nudgeTorque;
        private final boolean passOn;
        private final float[] pred;
        private final float rLeadD;
        private final boolean rLeadOn;
        private final float rLeadV;
        private final boolean returnOn;
        private final boolean rightBlinker;
        private final boolean rightBsm;
        private final int rightLanes;
        private final int routeDir;
        private final int routeSoonDir;
        private final String routeWhy;
        private final boolean steeringPressed;
        private final float steeringTorque;
        private final float vEgo;
        private final float vSet;

        public In(long nowMs, boolean engaged, float vEgo, float minLcSpeed, float[] pred, boolean laneChanging, int laneChangeDir, boolean leftBlinker, boolean rightBlinker, boolean leftBsm, boolean rightBsm, boolean brake, int routeDir, String routeWhy, boolean steeringPressed, float steeringTorque, boolean nudgeConfirm, boolean auto, boolean commaConfirms, boolean busy, boolean passOn, float vSet, boolean leadOn, float leadD, float leadV, int leftLanes, int routeSoonDir, boolean metric, boolean exitOn, int exitDir, double exitDist, String exitWhy, String exitKey, int exitSideLanes, boolean exitInLane, int mergeDir, String mergeWhy, float nudgeTorque, boolean returnOn, int rightLanes, boolean rLeadOn, float rLeadD, float rLeadV, int lanesTotal, boolean highway) {
            Intrinsics.checkNotNullParameter(routeWhy, "routeWhy");
            Intrinsics.checkNotNullParameter(exitWhy, "exitWhy");
            Intrinsics.checkNotNullParameter(exitKey, "exitKey");
            Intrinsics.checkNotNullParameter(mergeWhy, "mergeWhy");
            this.nowMs = nowMs;
            this.engaged = engaged;
            this.vEgo = vEgo;
            this.minLcSpeed = minLcSpeed;
            this.pred = pred;
            this.laneChanging = laneChanging;
            this.laneChangeDir = laneChangeDir;
            this.leftBlinker = leftBlinker;
            this.rightBlinker = rightBlinker;
            this.leftBsm = leftBsm;
            this.rightBsm = rightBsm;
            this.brake = brake;
            this.routeDir = routeDir;
            this.routeWhy = routeWhy;
            this.steeringPressed = steeringPressed;
            this.steeringTorque = steeringTorque;
            this.nudgeConfirm = nudgeConfirm;
            this.auto = auto;
            this.commaConfirms = commaConfirms;
            this.busy = busy;
            this.passOn = passOn;
            this.vSet = vSet;
            this.leadOn = leadOn;
            this.leadD = leadD;
            this.leadV = leadV;
            this.leftLanes = leftLanes;
            this.routeSoonDir = routeSoonDir;
            this.metric = metric;
            this.exitOn = exitOn;
            this.exitDir = exitDir;
            this.exitDist = exitDist;
            this.exitWhy = exitWhy;
            this.exitKey = exitKey;
            this.exitSideLanes = exitSideLanes;
            this.exitInLane = exitInLane;
            this.mergeDir = mergeDir;
            this.mergeWhy = mergeWhy;
            this.nudgeTorque = nudgeTorque;
            this.returnOn = returnOn;
            this.rightLanes = rightLanes;
            this.rLeadOn = rLeadOn;
            this.rLeadD = rLeadD;
            this.rLeadV = rLeadV;
            this.lanesTotal = lanesTotal;
            this.highway = highway;
        }

        public /* synthetic */ In(long j, boolean z, float f, float f2, float[] fArr, boolean z2, int i, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, int i2, String str, boolean z8, float f3, boolean z9, boolean z10, boolean z11, boolean z12, boolean z13, float f4, boolean z14, float f5, float f6, int i3, int i4, boolean z15, boolean z16, int i5, double d, String str2, String str3, int i6, boolean z17, int i7, String str4, float f7, boolean z18, int i8, boolean z19, float f8, float f9, int i9, boolean z20, int i10, int i11, DefaultConstructorMarker defaultConstructorMarker) {
            this(j, z, f, f2, fArr, z2, i, z3, z4, z5, z6, z7, (i10 & 4096) != 0 ? 0 : i2, (i10 & 8192) != 0 ? "" : str, (i10 & 16384) != 0 ? false : z8, (32768 & i10) != 0 ? 0.0f : f3, (65536 & i10) != 0 ? true : z9, (131072 & i10) != 0 ? false : z10, (262144 & i10) != 0 ? true : z11, (524288 & i10) != 0 ? false : z12, (1048576 & i10) != 0 ? false : z13, (2097152 & i10) != 0 ? 0.0f : f4, (4194304 & i10) != 0 ? false : z14, (8388608 & i10) != 0 ? 0.0f : f5, (16777216 & i10) != 0 ? 0.0f : f6, (33554432 & i10) != 0 ? -1 : i3, (67108864 & i10) != 0 ? 0 : i4, (134217728 & i10) != 0 ? false : z15, (268435456 & i10) != 0 ? false : z16, (536870912 & i10) != 0 ? 0 : i5, (1073741824 & i10) != 0 ? Double.NaN : d, (i10 & Integer.MIN_VALUE) != 0 ? "" : str2, (i11 & 1) != 0 ? "" : str3, (i11 & 2) != 0 ? -1 : i6, (i11 & 4) != 0 ? false : z17, (i11 & 8) != 0 ? 0 : i7, (i11 & 16) != 0 ? "" : str4, (i11 & 32) != 0 ? 60.0f : f7, (i11 & 64) != 0 ? false : z18, (i11 & 128) != 0 ? -1 : i8, (i11 & 256) != 0 ? false : z19, (i11 & 512) != 0 ? 0.0f : f8, (i11 & 1024) != 0 ? 0.0f : f9, (i11 & 2048) != 0 ? -1 : i9, (i11 & 4096) != 0 ? false : z20);
        }

        public final boolean getEngaged() {
            return this.engaged;
        }

        public final float getMinLcSpeed() {
            return this.minLcSpeed;
        }

        public final long getNowMs() {
            return this.nowMs;
        }

        public final float getVEgo() {
            return this.vEgo;
        }

        public final float[] getPred() {
            return this.pred;
        }

        public final int getLaneChangeDir() {
            return this.laneChangeDir;
        }

        public final boolean getLaneChanging() {
            return this.laneChanging;
        }

        public final boolean getBrake() {
            return this.brake;
        }

        public final boolean getLeftBlinker() {
            return this.leftBlinker;
        }

        public final boolean getLeftBsm() {
            return this.leftBsm;
        }

        public final boolean getRightBlinker() {
            return this.rightBlinker;
        }

        public final boolean getRightBsm() {
            return this.rightBsm;
        }

        public final int getRouteDir() {
            return this.routeDir;
        }

        public final String getRouteWhy() {
            return this.routeWhy;
        }

        public final boolean getNudgeConfirm() {
            return this.nudgeConfirm;
        }

        public final boolean getSteeringPressed() {
            return this.steeringPressed;
        }

        public final float getSteeringTorque() {
            return this.steeringTorque;
        }

        public final boolean getAuto() {
            return this.auto;
        }

        public final boolean getCommaConfirms() {
            return this.commaConfirms;
        }

        public final boolean getBusy() {
            return this.busy;
        }

        public final float getLeadD() {
            return this.leadD;
        }

        public final boolean getLeadOn() {
            return this.leadOn;
        }

        public final float getLeadV() {
            return this.leadV;
        }

        public final boolean getPassOn() {
            return this.passOn;
        }

        public final float getVSet() {
            return this.vSet;
        }

        public final int getLeftLanes() {
            return this.leftLanes;
        }

        public final boolean getMetric() {
            return this.metric;
        }

        public final int getRouteSoonDir() {
            return this.routeSoonDir;
        }

        public final int getExitDir() {
            return this.exitDir;
        }

        public final double getExitDist() {
            return this.exitDist;
        }

        public final boolean getExitOn() {
            return this.exitOn;
        }

        public final String getExitWhy() {
            return this.exitWhy;
        }

        public final String getExitKey() {
            return this.exitKey;
        }

        public final int getExitSideLanes() {
            return this.exitSideLanes;
        }

        public final boolean getExitInLane() {
            return this.exitInLane;
        }

        public final int getMergeDir() {
            return this.mergeDir;
        }

        public final String getMergeWhy() {
            return this.mergeWhy;
        }

        public final float getNudgeTorque() {
            return this.nudgeTorque;
        }

        public final float getRLeadD() {
            return this.rLeadD;
        }

        public final boolean getRLeadOn() {
            return this.rLeadOn;
        }

        public final float getRLeadV() {
            return this.rLeadV;
        }

        public final boolean getReturnOn() {
            return this.returnOn;
        }

        public final int getRightLanes() {
            return this.rightLanes;
        }

        public final boolean getHighway() {
            return this.highway;
        }

        public final int getLanesTotal() {
            return this.lanesTotal;
        }
    }

    public final State getState() {
        return this.state;
    }

    public final int getDir() {
        return this.dir;
    }

    public final String getWhy() {
        return this.why;
    }

    public final String getKind() {
        return this.kind;
    }

    /* VF: from Vineflower (jadx: Removed duplicated region;incorrect) */
    public final String getLabel() {
        String var1 = this.kind;
        switch (var1.hashCode()) {
            case -934396624:
                if (var1.equals("return")) {
                    return "BACK";
                }
                break;
            case 3127582:
                if (var1.equals("exit")) {
                    return "ROUTE";
                }
                break;
            case 3433489:
                if (var1.equals("pass")) {
                    return "PASS";
                }
                break;
            case 3556498:
                if (var1.equals("test")) {
                    return "TEST";
                }
                break;
            case 103785528:
                if (var1.equals("merge")) {
                    return "LANE ENDS";
                }
                break;
            case 104069929:
                if (var1.equals("model")) {
                    return "MODEL";
                }
                break;
            case 108704329:
                if (var1.equals("route")) {
                    return "ROUTE";
                }
        }

        return "";
    }

    public final int getHomeLeft() {
        return this.homeLeft;
    }

    public final boolean getReasked() {
        return this.reasked;
    }

    public final boolean getArmed() {
        return this.armed;
    }

    /* renamed from: getExitTry, reason: from getter */
    public final int getExitTries() {
        return this.exitTries;
    }

    public final int getOwedRight() {
        return this.owedRight;
    }

    public final String getResult() {
        return this.result;
    }

    public final boolean getBeepNow() {
        return this.beepNow;
    }

    public final boolean getTest() {
        return this.test;
    }

    public final void testAsk(int d, String w, long now) {
        Intrinsics.checkNotNullParameter(w, "w");
        this.test = true;
        ask(d < 0 ? -1 : 1, w, now, "test");
    }

    public final int getHoldSide() {
        return this.holdSide;
    }

    public final String getConfirmedBy() {
        return this.confirmedBy;
    }

    public final boolean getAutoAsk() {
        return this.autoAsk;
    }

    public final long autoLeftMs(long now) {
        return RangesKt.coerceAtLeast((this.since + 3000) - now, 0L);
    }

    private final int idx(int d) {
        return d < 0 ? 0 : 1;
    }

    private final String side(int d) {
        return d < 0 ? "LEFT" : "RIGHT";
    }

    /* VF: from Vineflower (jadx: Code restructure failed;Removed duplicated region;incorrect) */
    public final void update(LaneSuggest.In var1) {
        Intrinsics.checkNotNullParameter(var1, "i");
        long var13 = var1.getNowMs();
        float var2;
        if (this.lastMs == 0L) {
            var2 = 0.0F;
        } else {
            var2 = RangesKt.coerceIn((float)(var13 - this.lastMs) / 1000.0F, 0.0F, 0.5F);
        }

        this.lastMs = var13;
        this.beepNow = false;
        float[] var17 = var1.getPred();
        int var5 = 0;

        while (true) {
            int var6 = 3;
            if (var5 >= 2) {
                if (var1.getLaneChanging()) {
                    if (var1.getLaneChangeDir() != 0) {
                        if (this.lcDir == 0 && var1.getLaneChangeDir() < 0 && this.owedRight == 0) {
                            this.homeLeft = var1.getLeftLanes();
                        }

                        this.lcDir = var1.getLaneChangeDir();
                    }
                } else if (this.lcDir != 0) {
                    if (this.lcDir < 0) {
                        if (var1.getReturnOn()) {
                            this.owedRight = Math.min(this.owedRight + 1, 3);
                            this.owedAt = var13;
                        }
                    } else if (this.owedRight > 0) {
                        this.owedRight--;
                    }

                    this.lcDir = 0;
                }

                if (this.owedRight > 0 && var13 - this.owedAt > 180000L) {
                    this.owedRight = 0;
                }

                if (this.owedRight > 0 && this.homeLeft >= 0 && var1.getLeftLanes() >= this.homeLeft && this.lcDir == 0) {
                    this.owedRight = 0;
                }

                if (this.owedRight == 0 && this.lcDir == 0) {
                    this.homeLeft = -1;
                }

                if (this.returnCond(var1, var13)) {
                    if (this.returnSince == 0L) {
                        this.returnSince = var13;
                    }

                    this.returnLostSince = 0L;
                } else {
                    this.returnSince = 0L;
                    if (this.returnLostSince == 0L) {
                        this.returnLostSince = var13;
                    }
                }

                if (var1.getMergeDir() == 0 || Intrinsics.areEqual(this.kind, "merge") && var1.getMergeDir() != this.dir) {
                    if (this.mergeLostSince == 0L) {
                        this.mergeLostSince = var13;
                    }
                } else {
                    this.mergeLostSince = 0L;
                }

                if (var1.getRouteDir() == 0 || var1.getRouteDir() != this.routeSinceDir) {
                    this.routeSinceDir = var1.getRouteDir();
                    long var11;
                    if (var1.getRouteDir() != 0) {
                        var11 = var13;
                    } else {
                        var11 = 0L;
                    }

                    this.routeSince = var11;
                } else if (this.routeSince == 0L) {
                    this.routeSince = var13;
                }

                if (var1.getRouteDir() == 0 || Intrinsics.areEqual(this.kind, "route") && var1.getRouteDir() != this.dir) {
                    if (this.routeLostSince == 0L) {
                        this.routeLostSince = var13;
                    }
                } else {
                    this.routeLostSince = 0L;
                }

                if (this.passCond(var1)) {
                    if (this.passSince == 0L) {
                        this.passSince = var13;
                    }

                    this.passLostSince = 0L;
                } else {
                    this.passSince = 0L;
                    if (this.passLostSince == 0L) {
                        this.passLostSince = var13;
                    }
                }

                int var28;
                if (var1.getEngaged()
                    && var1.getVEgo() >= var1.getMinLcSpeed()
                    && !var1.getLaneChanging()
                    && !var1.getLeftBlinker()
                    && !var1.getRightBlinker()
                    && !var1.getBrake()
                    && !var1.getBusy()) {
                    var28 = 1;
                } else {
                    var28 = 0;
                }

                label552: {
                    label551: {
                        LaneSuggest.State var43 = this.state;
                        var6 = LaneSuggest.WhenMappings.$EnumSwitchMapping$0[var43.ordinal()];
                        Object var19 = null;
                        Object var47 = null;
                        String var44 = "pass";
                        switch (var6) {
                            case 1:
                            case 2:
                                if (this.state == LaneSuggest.State.RESULT && var13 - this.since > 4000L) {
                                    this.state = LaneSuggest.State.IDLE;
                                }

                                if ((this.state == LaneSuggest.State.IDLE || this.exitRetryAt != 0L && var13 >= this.exitRetryAt) && (var28 != 0) && this.exitDue(var1, var13)
                                    )
                                 {
                                    if (!Intrinsics.areEqual(var1.getExitKey(), this.exitKey)) {
                                        this.exitKey = var1.getExitKey();
                                        this.exitTries = 0;
                                    }

                                    this.exitTries++;
                                    this.exitRetryAt = 0L;
                                    this.ask(var1.getExitDir(), var1.getExitWhy(), var13, "exit");
                                    this.state = LaneSuggest.State.SIGNALING;
                                    this.holdSide = this.dir;
                                    this.confirmedBy = "route";
                                    return;
                                }

                                if (this.state == LaneSuggest.State.IDLE && (var28 != 0)) {
                                    var47 = new int[]{-1, 1};
                                    int var36 = ((Object[])var47).length;
                                    var28 = 0;

                                    while (true) {
                                        if (var28 >= var36) {
                                            var47 = (Integer)var19;
                                            break;
                                        }

                                        int var8 = (int)((Object[])var47)[var28];
                                        boolean var35;
                                        if (this.highSince[this.idx(var8)] == 0L
                                            || var13 - this.highSince[this.idx(var8)] < 1000L
                                            || var13 < this.cool[this.idx(var8)]
                                            || var8 > 0 && var1.getHighway() && var1.getLanesTotal() >= 3) {
                                            var35 = false;
                                        } else {
                                            var35 = true;
                                        }

                                        if (var35) {
                                            var47 = var8;
                                            break;
                                        }

                                        var28++;
                                    }

                                    if (var47 != null) {
                                        var28 = var47;
                                        String var50 = this.side(var47).toLowerCase(Locale.ROOT);
                                        Intrinsics.checkNotNullExpressionValue(var50, "toLowerCase(...)");
                                        this.ask(var28, "the driving model wants to move " + var50, var13, "model");
                                    } else if (var1.getRouteDir() != 0
                                        && this.routeSince != 0L
                                        && var13 - this.routeSince >= 2000L
                                        && var13 >= this.cool[this.idx(var1.getRouteDir())]) {
                                        this.ask(var1.getRouteDir(), var1.getRouteWhy(), var13, "route");
                                    } else if (var1.getMergeDir() != 0 && var13 >= this.cool[this.idx(var1.getMergeDir())]) {
                                        this.ask(var1.getMergeDir(), var1.getMergeWhy(), var13, "merge");
                                    } else if (this.passSince != 0L && var13 - this.passSince >= 5000L && var13 >= this.cool[0] && !var1.getLeftBsm()) {
                                        String var51 = this.speed(var1.getLeadV(), var1.getMetric());
                                        var19 = this.speed(var1.getVSet(), var1.getMetric());
                                        this.ask(-1, "slower car ahead at " + var51 + " - your set speed is " + var19, var13, "pass");
                                    } else if (this.returnSince != 0L && var13 - this.returnSince >= 3000L && var13 >= this.cool[1]) {
                                        this.ask(1, "you passed - the right lane is clear, move back right", var13, "return");
                                    }

                                    boolean var41;
                                    if (this.state == LaneSuggest.State.ASKING
                                        && var1.getAuto()
                                        && var1.getCommaConfirms()
                                        && !Intrinsics.areEqual(this.kind, var44)
                                        && !Intrinsics.areEqual(this.kind, "return")
                                        && !Intrinsics.areEqual(this.kind, "merge")) {
                                        var41 = true;
                                    } else {
                                        var41 = false;
                                    }

                                    this.autoAsk = var41;
                                }

                                Unit var27 = Unit.INSTANCE;
                                break;
                            case 3:
                                if (this.test) {
                                    if (this.nudged(var1, var13)) {
                                        this.confirm(var13, "nudge");
                                        return;
                                    }

                                    if (var13 - this.since > 12000L) {
                                        this.state = LaneSuggest.State.IDLE;
                                        this.test = false;
                                        return;
                                    }

                                    if (var13 - this.lastBeep >= 2000L) {
                                        this.beepNow = true;
                                        this.lastBeep = var13;
                                    }

                                    return;
                                }

                                if (!var1.getEngaged() || var1.getLaneChanging() || var1.getBrake()) {
                                    break label551;
                                }

                                boolean var39;
                                if (this.dir < 0) {
                                    var39 = var1.getRightBlinker();
                                } else {
                                    var39 = var1.getLeftBlinker();
                                }

                                if (var39) {
                                    break label551;
                                }

                                if (this.dir < 0) {
                                    var39 = var1.getLeftBsm();
                                } else {
                                    var39 = var1.getRightBsm();
                                }

                                if (var39) {
                                    this.state = LaneSuggest.State.WAITING;
                                    if (this.firstWait == 0L) {
                                        this.firstWait = var13;
                                    }

                                    this.waitSince = this.firstWait;
                                    this.clearSince = 0L;
                                    return;
                                }

                                if (var13 - this.since > 12000L) {
                                    this.state = LaneSuggest.State.IDLE;
                                    this.cool[this.idx(this.dir)] = 30000 + var13;
                                    return;
                                }

                                if (Intrinsics.areEqual(this.kind, "pass") && this.passLostSince != 0L && var13 - this.passLostSince >= 2000L) {
                                    this.state = LaneSuggest.State.IDLE;
                                    this.cool[0] = 20000 + var13;
                                    return;
                                }

                                if (Intrinsics.areEqual(this.kind, "return") && this.returnLostSince != 0L && var13 - this.returnLostSince >= 2000L) {
                                    this.state = LaneSuggest.State.IDLE;
                                    this.cool[1] = 20000 + var13;
                                    return;
                                }

                                if (Intrinsics.areEqual(this.kind, "merge") && this.mergeLostSince != 0L && var13 - this.mergeLostSince >= 2000L) {
                                    this.state = LaneSuggest.State.IDLE;
                                    this.cool[this.idx(this.dir)] = 20000 + var13;
                                    return;
                                }

                                if (this.routeGone(var13)) {
                                    this.state = LaneSuggest.State.IDLE;
                                    this.cool[this.idx(this.dir)] = 20000 + var13;
                                    return;
                                }

                                if (this.nudged(var1, var13)) {
                                    this.confirm(var13, "nudge");
                                    return;
                                }

                                if (this.autoAsk && var13 - this.since >= 3000L) {
                                    this.confirm(var13, "auto");
                                    return;
                                }

                                if (var13 - this.lastBeep >= 2000L) {
                                    this.beepNow = true;
                                    this.lastBeep = var13;
                                }

                                Unit var26 = Unit.INSTANCE;
                                break;
                            case 4:
                                if (!var1.getEngaged() || var1.getLaneChanging() || var1.getBrake() || var1.getVEgo() < var1.getMinLcSpeed()) {
                                    break label552;
                                }

                                boolean var37;
                                if (this.dir < 0) {
                                    var37 = var1.getRightBlinker();
                                } else {
                                    var37 = var1.getLeftBlinker();
                                }

                                if (var37) {
                                    break label552;
                                }

                                long var15 = this.waitSince;
                                long var42;
                                if (Intrinsics.areEqual(this.kind, "model")) {
                                    var42 = 30000L;
                                } else {
                                    var42 = 60000L;
                                }

                                if (var13 - var15 > var42) {
                                    this.state = LaneSuggest.State.IDLE;
                                    this.cool[this.idx(this.dir)] = 30000 + var13;
                                    return;
                                }

                                if (Intrinsics.areEqual(this.kind, "pass") && this.passLostSince != 0L && var13 - this.passLostSince >= 2000L) {
                                    this.state = LaneSuggest.State.IDLE;
                                    this.cool[0] = 20000 + var13;
                                    return;
                                }

                                if (Intrinsics.areEqual(this.kind, "return") && this.owedRight == 0) {
                                    this.state = LaneSuggest.State.IDLE;
                                    return;
                                }

                                if (Intrinsics.areEqual(this.kind, "exit") && this.exitGone(var1)) {
                                    this.state = LaneSuggest.State.IDLE;
                                    this.exitDoneKey = this.exitKey;
                                    return;
                                }

                                if (this.routeGone(var13)) {
                                    this.state = LaneSuggest.State.IDLE;
                                    this.cool[this.idx(this.dir)] = 20000 + var13;
                                    return;
                                }

                                if (!this.armed && this.nudged(var1, var13)) {
                                    this.armed = true;
                                    this.confirmedBy = "nudge";
                                }

                                if (this.dir < 0) {
                                    var37 = var1.getLeftBsm();
                                } else {
                                    var37 = var1.getRightBsm();
                                }

                                if (var37) {
                                    this.clearSince = 0L;
                                } else {
                                    if (this.clearSince == 0L) {
                                        this.clearSince = var13;
                                    }

                                    if (var13 - this.clearSince >= 1000L) {
                                        if (this.armed) {
                                            this.state = LaneSuggest.State.SIGNALING;
                                            this.since = var13;
                                            this.holdSide = this.dir;
                                        } else {
                                            this.state = LaneSuggest.State.ASKING;
                                            this.since = var13;
                                            this.lastBeep = 0L;
                                            this.reasked = true;
                                        }
                                    }
                                }

                                Unit var25 = Unit.INSTANCE;
                                break;
                            case 5:
                                if (this.test) {
                                    this.holdSide = this.dir;
                                    if (var13 - this.since > 8000L) {
                                        this.finish("test done - the blinker was held 8 seconds. openpilot only changes lanes while driving", var13);
                                        this.test = false;
                                    }

                                    return;
                                }

                                boolean var9;
                                if (this.dir < 0) {
                                    var9 = var1.getLeftBsm();
                                } else {
                                    var9 = var1.getRightBsm();
                                }

                                if (var9 && !var1.getLaneChanging() && var1.getEngaged() && !var1.getBrake()) {
                                    this.state = LaneSuggest.State.WAITING;
                                    if (this.firstWait == 0L) {
                                        this.firstWait = var13;
                                    }

                                    this.waitSince = this.firstWait;
                                    this.clearSince = 0L;
                                    this.armed = true;
                                    this.holdSide = 0;
                                    return;
                                }

                                Object var46;
                                if (!var1.getEngaged()) {
                                    var46 = "openpilot disengaged - stopped";
                                } else if (var1.getBrake()) {
                                    var46 = "brake pressed - stopped";
                                } else if (var1.getVEgo() < var1.getMinLcSpeed()) {
                                    var46 = "too slow for a lane change - stopped";
                                } else {
                                    boolean var10;
                                    if (this.dir < 0) {
                                        var10 = var1.getRightBlinker();
                                    } else {
                                        var10 = var1.getLeftBlinker();
                                    }

                                    if (var10) {
                                        var46 = "other blinker on - stopped";
                                    } else if (Intrinsics.areEqual(this.kind, "exit") && this.exitGone(var1)) {
                                        this.exitDoneKey = this.exitKey;
                                        var46 = "the exit is behind us - not taken";
                                    } else if (!var1.getLaneChanging() && this.routeGone(var13)) {
                                        this.cool[this.idx(this.dir)] = 20000 + var13;
                                        var46 = "already in a lane for the route - stopped";
                                    } else if (Intrinsics.areEqual(this.kind, "exit") && var13 - this.since > 6000L) {
                                        if (this.exitTries < 5) {
                                            this.exitRetryAt = 1500L + var13;
                                            var46 = "openpilot hasn't moved over yet - signaling again";
                                        } else {
                                            this.exitDoneKey = this.exitKey;
                                            var46 = "openpilot didn't take the exit - take it yourself";
                                        }
                                    } else if (var13 - this.since > 8000L) {
                                        if (var9) {
                                            String var45 = this.side(this.dir).toLowerCase(Locale.ROOT);
                                            Intrinsics.checkNotNullExpressionValue(var45, "toLowerCase(...)");
                                            var46 = "a car stayed in the " + var45 + " blind spot - not changed";
                                        } else if (var1.getCommaConfirms()) {
                                            var46 = "openpilot didn't start it (lane too narrow or lane changes off)";
                                        } else {
                                            var46 = "openpilot didn't start it (lane too narrow, lane changes off, or it wants a nudge)";
                                        }
                                    } else {
                                        var46 = var47;
                                    }
                                }

                                if (var46 != null) {
                                    this.finish(((String) var46), var13);
                                    return;
                                }

                                this.holdSide = this.dir;
                                if (var1.getLaneChanging() && var1.getLaneChangeDir() == this.dir) {
                                    this.state = LaneSuggest.State.CHANGING;
                                    this.since = var13;
                                    this.holdSide = 0;
                                }

                                Unit var24 = Unit.INSTANCE;
                                break;
                            case 6:
                                this.holdSide = 0;
                                if (!var1.getLaneChanging()) {
                                    if (Intrinsics.areEqual(this.kind, "exit")) {
                                        this.exitDoneKey = this.exitKey;
                                        this.exitRetryAt = 0L;
                                    }

                                    String var20;
                                    if (Intrinsics.areEqual(this.kind, "exit")) {
                                        var20 = "moved over for the exit";
                                    } else {
                                        String var21 = this.side(this.dir).toLowerCase(Locale.ROOT);
                                        Intrinsics.checkNotNullExpressionValue(var21, "toLowerCase(...)");
                                        var20 = "changed lanes " + var21;
                                    }

                                    this.finish(var20, var13);
                                    long[] var22 = this.cool;
                                    var6 = this.idx(this.dir);
                                    if (Intrinsics.areEqual(this.kind, "pass")) {
                                        var28 = (char)45000;
                                    } else {
                                        var28 = (char)10000;
                                    }

                                    var22[var6] = var28 + var13;
                                } else if (var13 - this.since > 15000L) {
                                    this.finish("lane change took too long", var13);
                                }

                                Unit var23 = Unit.INSTANCE;
                                break;
                            default:
                                throw new NoWhenBranchMatchedException();
                        }

                        return;
                    }

                    this.state = LaneSuggest.State.IDLE;
                    this.cool[this.idx(this.dir)] = 15000 + var13;
                    return;
                }

                this.state = LaneSuggest.State.IDLE;
                this.cool[this.idx(this.dir)] = 15000 + var13;
                return;
            }

            int var7 = new int[]{-1, 1}[var5];
            if (var7 >= 0) {
                var6 = 4;
            }

            float var3;
            if (var17 != null && var17.length >= 32) {
                var3 = Math.max(var17[var6 + 8], var17[var6 + 16]);
            } else {
                var3 = 0.0F;
            }

            float var4 = (float)Math.exp(-var2 / 0.5F);
            float[] var18 = this.ema;
            var6 = this.idx(var7);
            var18[var6] += (var3 - this.ema[this.idx(var7)]) * (1.0F - var4);
            if (this.ema[this.idx(var7)] > 0.6F) {
                if (this.highSince[this.idx(var7)] == 0L) {
                    this.highSince[this.idx(var7)] = var13;
                }
            } else {
                this.highSince[this.idx(var7)] = 0L;
            }

            var5++;
        }
    }

    private final boolean nudged(In i, long now) {
        if (!i.getNudgeConfirm()) {
            this.lightSince = 0L;
            return false;
        }
        float toward = this.dir < 0 ? i.getSteeringTorque() : -i.getSteeringTorque();
        boolean pressed = i.getSteeringPressed() && toward > 0.0f && i.getNudgeTorque() <= 100.0f;
        if (toward < i.getNudgeTorque()) {
            this.lightSince = 0L;
        } else if (this.lightSince == 0) {
            this.lightSince = now;
        }
        boolean light = this.lightSince != 0 && now - this.lightSince >= 150;
        if (!pressed && !light) {
            return false;
        }
        this.nudgeTorque = i.getSteeringTorque();
        this.lightSince = 0L;
        return true;
    }

    public final float getNudgeTorque() {
        return this.nudgeTorque;
    }

    private final void ask(int d, String w, long now, String k) {
        this.state = State.ASKING;
        this.dir = d;
        this.why = w;
        this.kind = k;
        this.since = now;
        this.lastBeep = 0L;
        this.confirmedBy = "";
        this.autoAsk = false;
        this.reasked = false;
        this.armed = false;
        this.firstWait = 0L;
    }

    private final boolean passCond(In i) {
        return i.getPassOn() && i.getEngaged() && i.getVEgo() >= 13.41f && i.getVSet() > 0.0f && i.getLeadOn() && i.getLeadD() > 0.0f && i.getLeadD() <= Math.min(120.0f, Math.max(30.0f, i.getVEgo() * 4.0f)) && i.getLeadV() <= i.getVSet() - 2.235f && i.getVEgo() <= i.getVSet() - 1.34f && i.getLeftLanes() >= 1 && i.getRouteSoonDir() <= 0;
    }

    private final boolean returnCond(In i, long now) {
        if (!i.getReturnOn() || this.owedRight <= 0 || now - this.owedAt < 8000 || !i.getEngaged() || i.getVEgo() < 13.41f || i.getVSet() <= 0.0f || i.getRightLanes() < 1 || i.getRightBsm() || i.getRouteSoonDir() < 0 || i.getLaneChanging()) {
            return false;
        }
        if (!i.getRLeadOn() || i.getRLeadD() <= 0.0f) {
            return true;
        }
        boolean far = i.getRLeadD() >= i.getVEgo() * 6.0f;
        boolean keepsUp = i.getRLeadV() >= i.getVSet() - 2.235f && i.getRLeadD() >= i.getVEgo() * 2.0f;
        return far || keepsUp;
    }

    private final boolean exitDue(In i, long now) {
        if (i.getExitOn() && i.getExitDir() != 0 && i.getCommaConfirms()) {
            if (!(i.getExitKey().length() == 0) && !Intrinsics.areEqual(i.getExitKey(), this.exitDoneKey)) {
                if ((Intrinsics.areEqual(i.getExitKey(), this.exitKey) && (this.exitTries >= 5 || (this.exitRetryAt != 0 && now < this.exitRetryAt))) || i.getExitSideLanes() >= 2 || i.getExitInLane()) {
                    return false;
                }
                double d = i.getExitDist();
                return (!Double.isInfinite(d) && !Double.isNaN(d)) && d >= 25.0d && d <= RangesKt.coerceIn(((double) i.getVEgo()) * 8.0d, 120.0d, 320.0d);
            }
        }
        return false;
    }

    private final boolean exitGone(In i) {
        if (!Intrinsics.areEqual(i.getExitKey(), this.exitKey)) {
            return true;
        }
        double exitDist = i.getExitDist();
        return !(!Double.isInfinite(exitDist) && !Double.isNaN(exitDist)) || i.getExitDist() < 25.0d;
    }

    private final boolean routeGone(long now) {
        return Intrinsics.areEqual(this.kind, "route") && !this.test && this.routeLostSince != 0 && now - this.routeLostSince >= 1000;
    }

    private final String speed(float ms, boolean metric) {
        StringBuilder append;
        String str;
        if (metric) {
            append = new StringBuilder().append(Math.round(3.6f * ms));
            str = " km/h";
        } else {
            append = new StringBuilder().append(Math.round(2.23694f * ms));
            str = " mph";
        }
        return append.append(str).toString();
    }

    private final void finish(String text, long now) {
        this.holdSide = 0;
        this.state = State.RESULT;
        this.result = text;
        this.since = now;
    }

    public static /* synthetic */ void confirm$default(LaneSuggest laneSuggest, long j, String str, int i, Object obj) {
        if ((i & 2) != 0) {
            str = "tap";
        }
        laneSuggest.confirm(j, str);
    }

    public final void confirm(long now, String by) {
        Intrinsics.checkNotNullParameter(by, "by");
        if (this.state == State.ASKING) {
            this.firstWait = 0L;
            this.state = State.SIGNALING;
            this.since = now;
            this.holdSide = this.dir;
            this.confirmedBy = by;
        }
    }

    public final void reset() {
        this.state = State.IDLE;
        this.holdSide = 0;
        this.test = false;
        this.autoAsk = false;
    }

    public final void dismiss(long now) {
        if (this.state == State.ASKING || this.state == State.SIGNALING) {
            this.test = false;
        }
        switch (WhenMappings.$EnumSwitchMapping$0[this.state.ordinal()]) {
            case 3:
            case 4:
                this.state = State.IDLE;
                this.armed = false;
                this.cool[idx(this.dir)] = 60000 + now;
                if (Intrinsics.areEqual(this.kind, "return")) {
                    this.owedRight = 0;
                    break;
                }
                break;
            case 5:
                if (Intrinsics.areEqual(this.kind, "exit")) {
                    this.exitDoneKey = this.exitKey;
                    this.exitRetryAt = 0L;
                }
                finish("cancelled", now);
                break;
        }
    }

    /* compiled from: LaneSuggest.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* VF: from Vineflower (jadx: Code restructure failed;incorrect) */
        public final LaneSuggest.Companion.Exit exitNeed(JSONObject var1, boolean var2) {
            if (var1 != null) {
                int var7 = 0;
                if (var1.optBoolean("valid", false)) {
                    String var12 = var1.optString("maneuverType");
                    String var13 = var1.optString("maneuverModifier");
                    boolean var8 = Intrinsics.areEqual(var12, "off ramp");
                    String var9 = "fork";
                    if (!var8 && !Intrinsics.areEqual(var12, "fork")) {
                        return null;
                    }

                    if (var13 != null) {
                        String var11;
                        int var17;
                        label74: {
                            label73: {
                                var17 = var13.hashCode();
                                var11 = "right";
                                switch (var17) {
                                    case -1572118767:
                                        if (!var13.equals("sharpLeft")) {
                                            return null;
                                        }
                                        break;
                                    case -1485380526:
                                        if (!var13.equals("sharpRight")) {
                                            return null;
                                        }
                                        break label73;
                                    case -711874727:
                                        if (!var13.equals("slightRight")) {
                                            return null;
                                        }
                                        break label73;
                                    case -438788310:
                                        if (!var13.equals("slightLeft")) {
                                            return null;
                                        }
                                        break;
                                    case 3317767:
                                        if (!var13.equals("left")) {
                                            return null;
                                        }
                                        break;
                                    case 108511772:
                                        if (!var13.equals("right")) {
                                            return null;
                                        }
                                        break label73;
                                    default:
                                        return null;
                                }

                                var17 = -1;
                                break label74;
                            }

                            var17 = 1;
                        }

                        double var3 = var1.optDouble("maneuverDistance", Double.NaN);
                        int var6 = var7;
                        if (!Double.isInfinite(var3)) {
                            var6 = var7;
                            if (!Double.isNaN(var3)) {
                                var6 = 1;
                            }
                        }

                        if ((var6 == 0)) {
                            return null;
                        }

                        String var14 = var1.optString("maneuverPrimaryText");
                        String var16 = var9;
                        if (Intrinsics.areEqual(var12, "off ramp")) {
                            var16 = "exit";
                        }

                        StringBuilder var10;
                        if (var2) {
                            var6 = (int)(var3 / 10);
                            var10 = new StringBuilder().append(var6 * 10);
                            var9 = " m";
                        } else {
                            var6 = (int)(3.28084 * var3 / 50);
                            var10 = new StringBuilder().append(var6 * 50);
                            var9 = " ft";
                        }

                        String var15 = var10.append(var9).toString();
                        var9 = var11;
                        if (var17 < 0) {
                            var9 = "left";
                        }

                        Intrinsics.checkNotNull(var14);
                        String var22;
                        if (!StringsKt.isBlank(var14)) {
                            var22 = " - " + var14;
                        } else {
                            var22 = "";
                        }

                        return new LaneSuggest.Companion.Exit(var17, var3, var16 + " on the " + var9 + " in " + var15 + var22, var12 + "|" + var13 + "|" + var14);
                    }

                    return null;
                }
            }

            return null;
        }

        /* compiled from: LaneSuggest.kt */
        public static final class Exit {
            private final int dir;
            private final double dist;
            private final String key;
            private final String why;

            public Exit(int dir, double dist, String why, String key) {
                Intrinsics.checkNotNullParameter(why, "why");
                Intrinsics.checkNotNullParameter(key, "key");
                this.dir = dir;
                this.dist = dist;
                this.why = why;
                this.key = key;
            }

            public final int getDir() {
                return this.dir;
            }

            public final double getDist() {
                return this.dist;
            }

            public final String getKey() {
                return this.key;
            }

            public final String getWhy() {
                return this.why;
            }

            public final String getType() {
                return StringsKt.substringBefore(this.key, "|", this.key);
            }
        }

        public final boolean inLaneFor(Exit ex, LaneBrain brain, List<Nav.Lane> lanes) {
            Intrinsics.checkNotNullParameter(ex, "ex");
            Intrinsics.checkNotNullParameter(brain, "brain");
            Intrinsics.checkNotNullParameter(lanes, "lanes");
            if (brain.inExtra() == ex.getDir()) {
                return true;
            }
            Nav.Lane it = ourLaneAt$default(this, lanes, brain, ex.getDir(), 0.0d, false, 24, null);
            if (it != null) {
                return it.getOk();
            }
            Pair<Integer, Double> sideLanes = brain.sideLanes(ex.getDir());
            int k = sideLanes.component1().intValue();
            double c = sideLanes.component2().doubleValue();
            if (c < 0.6d) {
                return false;
            }
            return Intrinsics.areEqual(ex.getType(), "fork") && k == 0;
        }

        public static /* synthetic */ Nav.Lane ourLaneAt$default(Companion companion, List list, LaneBrain laneBrain, int i, double d, boolean z, int i2, Object obj) {
            if ((i2 & 8) != 0) {
                d = 0.6d;
            }
            double d2 = d;
            if ((i2 & 16) != 0) {
                z = false;
            }
            return companion.ourLaneAt(list, laneBrain, i, d2, z);
        }

        public final Nav.Lane ourLaneAt(List<Nav.Lane> lanes, LaneBrain brain, int dir, double minConf, boolean farOnly) {
            Intrinsics.checkNotNullParameter(lanes, "lanes");
            Intrinsics.checkNotNullParameter(brain, "brain");
            int n = lanes.size();
            if (n < 2 || dir == 0) {
                return null;
            }
            Pair<Integer, Double> side = brain.side(-dir);
            int kf = side.component1().intValue();
            double cf = side.component2().doubleValue();
            if (cf >= minConf && kf < n) {
                return dir > 0 ? lanes.get(kf) : lanes.get((n - 1) - kf);
            }
            if (farOnly) {
                return null;
            }
            Pair<Integer, Double> side2 = brain.side(dir);
            int kn = side2.component1().intValue();
            double cn = side2.component2().doubleValue();
            if (cn < minConf || kn >= n) {
                return null;
            }
            return dir > 0 ? lanes.get((n - 1) - kn) : lanes.get(kn);
        }

        /* VF: from Vineflower (jadx: Removed duplicated region;incorrect) */
        public final boolean arrowToward(Nav.Lane var1, int var2) {
            Intrinsics.checkNotNullParameter(var1, "l");
            boolean var6 = var1.getArrows().isEmpty();
            boolean var5 = true;
            boolean var4 = var5;
            if (!var6) {
                var var9 = var1.getArrows();
                boolean var11;
                if (var9 instanceof Collection && ((Collection)var9).isEmpty()) {
                    var11 = false;
                } else {
                    Iterator var10 = var9.iterator();

                    while (true) {
                        if (!var10.hasNext()) {
                            var11 = false;
                            break;
                        }

                        boolean var3;
                        label47: {
                            String var7 = (String)var10.next();
                            if (!Intrinsics.areEqual(var7, "none")) {
                                CharSequence var8 = var7;
                                if (var2 > 0) {
                                    var4 = StringsKt.contains(var8, "right", false);
                                } else if (!StringsKt.contains(var8, "left", false) && !StringsKt.contains(var7, "uturn", false)) {
                                    var4 = false;
                                } else {
                                    var4 = true;
                                }

                                if (!var4) {
                                    var3 = false;
                                    break label47;
                                }
                            }

                            var3 = true;
                        }

                        if (var3) {
                            var11 = true;
                            break;
                        }
                    }
                }

                if (var11) {
                    var4 = var5;
                } else {
                    var4 = false;
                }
            }

            return var4;
        }

        public final boolean notTurnLane(JSONObject nav, LaneBrain brain, Pair<Nav.Step, Double> next) {
            Nav.Lane lane;
            Intrinsics.checkNotNullParameter(brain, "brain");
            if (nav != null && next != null) {
                if (nav.optBoolean("valid", false)) {
                    TurnSignal.Companion companion = TurnSignal.Companion;
                    String optString = nav.optString("maneuverType");
                    Intrinsics.checkNotNullExpressionValue(optString, "optString(...)");
                    String optString2 = nav.optString("maneuverModifier");
                    Intrinsics.checkNotNullExpressionValue(optString2, "optString(...)");
                    int d = companion.turnDir(optString, optString2);
                    if (d == 0) {
                        return false;
                    }
                    double dist = nav.optDouble("maneuverDistance", Double.NaN);
                    if (((Double.isInfinite(dist) || Double.isNaN(dist)) ? false : true) && dist <= 92.0d) {
                        return (Math.abs(next.getSecond().doubleValue() - dist) > 60.0d || brain.inExtra() == d || (lane = ourLaneAt(next.getFirst().getLanes(), brain, d, 0.8d, true)) == null || lane.getOk() || arrowToward(lane, d)) ? false : true;
                    }
                    return false;
                }
            }
            return false;
        }

        /* VF: from Vineflower (jadx: ) */
        public final int routeSoon(JSONObject var1) {
            byte var5 = 0;
            if (var1 != null && var1.optBoolean("valid", false)) {
                String var6 = var1.optString("maneuverType");
                if (!Intrinsics.areEqual(var6, "off ramp") && !Intrinsics.areEqual(var6, "fork") && !Intrinsics.areEqual(var6, "turn")) {
                    return 0;
                } else {
                    double var2 = var1.optDouble("maneuverDistance", Double.NaN);
                    int var4;
                    if (!Double.isInfinite(var2) && !Double.isNaN(var2)) {
                        var4 = 1;
                    } else {
                        var4 = 0;
                    }

                    if ((var4 != 0) && !(var2 > 3200.0)) {
                        String var7 = var1.optString("maneuverModifier");
                        var4 = var5;
                        if (var7 != null) {
                            label90: {
                                switch (var7.hashCode()) {
                                    case -1572118767:
                                        if (!var7.equals("sharpLeft")) {
                                            return var5;
                                        }
                                        break;
                                    case -1485380526:
                                        if (!var7.equals("sharpRight")) {
                                            return var5;
                                        }
                                        break label90;
                                    case -711874727:
                                        if (!var7.equals("slightRight")) {
                                            return var5;
                                        }
                                        break label90;
                                    case -438788310:
                                        if (!var7.equals("slightLeft")) {
                                            return var5;
                                        }
                                        break;
                                    case 3317767:
                                        if (!var7.equals("left")) {
                                            return var5;
                                        }
                                        break;
                                    case 108511772:
                                        if (!var7.equals("right")) {
                                            return var5;
                                        }
                                        break label90;
                                    default:
                                        return var5;
                                }

                                var4 = -1;
                                return var4;
                            }

                            var4 = 1;
                        }

                        return var4;
                    } else {
                        return 0;
                    }
                }
            } else {
                return 0;
            }
        }

        /* VF: from Vineflower (jadx: ) */
        public static Pair routeNeed$default(LaneSuggest.Companion var0, JSONObject var1, LaneBrain var2, boolean var3, List var4, int var5, Object var6) {
            if ((var5 & 8) != 0) {
                var4 = CollectionsKt.emptyList();
            }

            return var0.routeNeed(var1, var2, var3, var4);
        }

        /* VF: from Vineflower (jadx: Code restructure failed;incorrect) */
        public final Pair<Integer, String> routeNeed(JSONObject var1, LaneBrain var2, boolean var3, List<Nav.Lane> var4) {
            Intrinsics.checkNotNullParameter(var2, "tr");
            Intrinsics.checkNotNullParameter(var4, "lanes");
            String var12 = "";
            Integer var14 = 0;
            if (var1 != null && var1.optBoolean("valid", false)) {
                String var13 = var1.optString("maneuverType");
                String var15 = var1.optString("maneuverModifier");
                if (!Intrinsics.areEqual(var13, "off ramp") && !Intrinsics.areEqual(var13, "fork") && !Intrinsics.areEqual(var13, "turn")) {
                    return TuplesKt.to(var14, "");
                } else if (var15 != null) {
                    byte var9;
                    label112: {
                        label111: {
                            switch (var15.hashCode()) {
                                case -1572118767:
                                    if (!var15.equals("sharpLeft")) {
                                        return TuplesKt.to(var14, "");
                                    }
                                    break;
                                case -1485380526:
                                    if (!var15.equals("sharpRight")) {
                                        return TuplesKt.to(var14, "");
                                    }
                                    break label111;
                                case -711874727:
                                    if (!var15.equals("slightRight")) {
                                        return TuplesKt.to(var14, "");
                                    }
                                    break label111;
                                case -438788310:
                                    if (!var15.equals("slightLeft")) {
                                        return TuplesKt.to(var14, "");
                                    }
                                    break;
                                case 3317767:
                                    if (!var15.equals("left")) {
                                        return TuplesKt.to(var14, "");
                                    }
                                    break;
                                case 108511772:
                                    if (!var15.equals("right")) {
                                        return TuplesKt.to(var14, "");
                                    }
                                    break label111;
                                default:
                                    return TuplesKt.to(var14, "");
                            }

                            var9 = -1;
                            break label112;
                        }

                        var9 = 1;
                    }

                    double var5 = var1.optDouble("maneuverDistance", Double.NaN);
                    int var10;
                    if (!Double.isInfinite(var5) && !Double.isNaN(var5)) {
                        var10 = 1;
                    } else {
                        var10 = 0;
                    }

                    if ((var10 != 0) && !(var5 > 1600.0) && !(var5 < 150.0)) {
                        Pair var16 = var2.sideLanes(var9);
                        int var11 = ((Number)var16.component1()).intValue();
                        double var7 = ((Number)var16.component2()).doubleValue();
                        if (var11 >= 1 && !(var7 < 0.6)) {
                            Nav.Lane var17 = ourLaneAt$default(this, var4, var2, var9, 0.0, false, 24, null);
                            if (var17 != null && var17.getOk()) {
                                var10 = (((boolean)true) ? 1 : 0);
                            } else {
                                var10 = (((boolean)false) ? 1 : 0);
                            }

                            if ((var10 != 0)) {
                                return TuplesKt.to(var14, "");
                            } else {
                                StringBuilder var18;
                                String var20;
                                if (var3) {
                                    var5 = (int)(var5 / 100) / 10.0;
                                    var18 = new StringBuilder().append(var5);
                                    var20 = " km";
                                } else if (var5 < 400.0) {
                                    var10 = (int)(3.28084 * var5 / 50);
                                    var18 = new StringBuilder().append(var10 * 50);
                                    var20 = " ft";
                                } else {
                                    var5 = (int)(var5 / 1609.34 * 10) / 10.0;
                                    var18 = new StringBuilder().append(var5);
                                    var20 = " mi";
                                }

                                String var27 = var18.append(var20).toString();
                                String var19;
                                if (Intrinsics.areEqual(var13, "off ramp")) {
                                    var19 = "exit";
                                } else if (Intrinsics.areEqual(var13, "fork")) {
                                    var19 = "fork";
                                } else {
                                    var19 = "turn";
                                }

                                String var21;
                                if (var9 < 0) {
                                    var21 = "left";
                                } else {
                                    var21 = "right";
                                }

                                String var22 = var12;
                                if (var11 > 1) {
                                    var22 = "s";
                                }

                                return TuplesKt.to(Integer.valueOf(var9), var19 + " on the " + var21 + " in " + var27 + " - " + var11 + " lane" + var22 + " to go");
                            }
                        } else {
                            return TuplesKt.to(var14, "");
                        }
                    } else {
                        return TuplesKt.to(var14, "");
                    }
                } else {
                    return TuplesKt.to(var14, "");
                }
            } else {
                return TuplesKt.to(var14, "");
            }
        }
    }
}
