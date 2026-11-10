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

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries($VALUES);

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

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterative(DepthRegionTraversal.java:31)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visit(SwitchOverStringVisitor.java:60)
     */
    /* JADX WARN: Removed duplicated region for block: B:24:0x005b A[ORIG_RETURN, RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:5:0x004c A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final String getLabel() {
        String str = this.kind;
        switch (str.hashCode()) {
            case -934396624:
                return !str.equals("return") ? "" : "BACK";
            case 3127582:
                if (str.equals("exit")) {
                    return "ROUTE";
                }
                break;
            case 3433489:
                if (str.equals("pass")) {
                    return "PASS";
                }
                break;
            case 3556498:
                if (str.equals("test")) {
                    return "TEST";
                }
                break;
            case 103785528:
                if (str.equals("merge")) {
                    return "LANE ENDS";
                }
                break;
            case 104069929:
                if (str.equals("model")) {
                    return "MODEL";
                }
                break;
            case 108704329:
                if (!str.equals("route")) {
                }
                break;
        }
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

    /* JADX WARN: Code restructure failed: missing block: B:384:0x073d, code lost:
    
        if (r35.getLanesTotal() < 3) goto L419;
     */
    /* JADX WARN: Removed duplicated region for block: B:387:0x0756 A[LOOP:1: B:373:0x06f6->B:387:0x0756, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:388:0x0751 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void update(In i) {
        String str;
        String str2;
        boolean z;
        boolean z2;
        boolean z3;
        String str3;
        Intrinsics.checkNotNullParameter(i, "i");
        long nowMs = i.getNowMs();
        float f = 0.5f;
        float coerceIn = this.lastMs == 0 ? 0.0f : RangesKt.coerceIn((nowMs - this.lastMs) / 1000.0f, 0.0f, 0.5f);
        this.lastMs = nowMs;
        this.beepNow = false;
        float[] pred = i.getPred();
        int[] iArr = {-1, 1};
        int i2 = 0;
        while (true) {
            if (i2 >= 2) {
                break;
            }
            int i3 = iArr[i2];
            int i4 = i3 >= 0 ? 4 : 3;
            float max = (pred == null || pred.length < 32) ? 0.0f : Math.max(pred[i4 + 8], pred[i4 + 16]);
            int i5 = i2;
            float exp = 1.0f - ((float) Math.exp((-coerceIn) / f));
            float[] fArr = this.ema;
            int idx = idx(i3);
            fArr[idx] = fArr[idx] + ((max - this.ema[idx(i3)]) * exp);
            if (this.ema[idx(i3)] <= 0.6f) {
                this.highSince[idx(i3)] = 0;
            } else if (this.highSince[idx(i3)] == 0) {
                this.highSince[idx(i3)] = nowMs;
            }
            i2 = i5 + 1;
            f = 0.5f;
        }
        if (i.getLaneChanging()) {
            if (i.getLaneChangeDir() != 0) {
                if (this.lcDir == 0 && i.getLaneChangeDir() < 0 && this.owedRight == 0) {
                    this.homeLeft = i.getLeftLanes();
                }
                this.lcDir = i.getLaneChangeDir();
            }
        } else if (this.lcDir != 0) {
            if (this.lcDir < 0) {
                if (i.getReturnOn()) {
                    this.owedRight = Math.min(this.owedRight + 1, 3);
                    this.owedAt = nowMs;
                }
            } else if (this.owedRight > 0) {
                this.owedRight--;
            }
            this.lcDir = 0;
        }
        if (this.owedRight > 0 && nowMs - this.owedAt > 180000) {
            this.owedRight = 0;
        }
        if (this.owedRight > 0 && this.homeLeft >= 0 && i.getLeftLanes() >= this.homeLeft && this.lcDir == 0) {
            this.owedRight = 0;
        }
        if (this.owedRight == 0 && this.lcDir == 0) {
            this.homeLeft = -1;
        }
        if (returnCond(i, nowMs)) {
            if (this.returnSince == 0) {
                this.returnSince = nowMs;
            }
            this.returnLostSince = 0L;
        } else {
            this.returnSince = 0L;
            if (this.returnLostSince == 0) {
                this.returnLostSince = nowMs;
            }
        }
        if (i.getMergeDir() != 0 && (!Intrinsics.areEqual(this.kind, "merge") || i.getMergeDir() == this.dir)) {
            this.mergeLostSince = 0L;
        } else if (this.mergeLostSince == 0) {
            this.mergeLostSince = nowMs;
        }
        if (i.getRouteDir() == 0 || i.getRouteDir() != this.routeSinceDir) {
            this.routeSinceDir = i.getRouteDir();
            this.routeSince = i.getRouteDir() != 0 ? nowMs : 0L;
        } else if (this.routeSince == 0) {
            this.routeSince = nowMs;
        }
        if (i.getRouteDir() != 0 && (!Intrinsics.areEqual(this.kind, "route") || i.getRouteDir() == this.dir)) {
            this.routeLostSince = 0L;
        } else if (this.routeLostSince == 0) {
            this.routeLostSince = nowMs;
        }
        if (passCond(i)) {
            if (this.passSince == 0) {
                this.passSince = nowMs;
            }
            this.passLostSince = 0L;
        } else {
            this.passSince = 0L;
            if (this.passLostSince == 0) {
                this.passLostSince = nowMs;
            }
        }
        boolean z4 = (!i.getEngaged() || i.getVEgo() < i.getMinLcSpeed() || i.getLaneChanging() || i.getLeftBlinker() || i.getRightBlinker() || i.getBrake() || i.getBusy()) ? false : true;
        Integer num = null;
        String str4 = null;
        String str5 = "pass";
        switch (WhenMappings.$EnumSwitchMapping$0[this.state.ordinal()]) {
            case 1:
            case 2:
                if (this.state == State.RESULT && nowMs - this.since > 4000) {
                    this.state = State.IDLE;
                }
                if ((this.state == State.IDLE || (this.exitRetryAt != 0 && nowMs >= this.exitRetryAt)) && z4 && exitDue(i, nowMs)) {
                    if (!Intrinsics.areEqual(i.getExitKey(), this.exitKey)) {
                        this.exitKey = i.getExitKey();
                        this.exitTries = 0;
                    }
                    this.exitTries++;
                    this.exitRetryAt = 0L;
                    ask(i.getExitDir(), i.getExitWhy(), nowMs, "exit");
                    this.state = State.SIGNALING;
                    this.holdSide = this.dir;
                    this.confirmedBy = "route";
                    return;
                }
                if (this.state == State.IDLE && z4) {
                    int[] iArr2 = {-1, 1};
                    int length = iArr2.length;
                    int i6 = 0;
                    while (true) {
                        if (i6 < length) {
                            int i7 = iArr2[i6];
                            int[] iArr3 = iArr2;
                            str = str5;
                            if (this.highSince[idx(i7)] != 0) {
                                if (nowMs - this.highSince[idx(i7)] >= 1000 && nowMs >= this.cool[idx(i7)]) {
                                    if (i7 > 0 && i.getHighway()) {
                                        break;
                                    }
                                    z3 = true;
                                    if (z3) {
                                        i6++;
                                        iArr2 = iArr3;
                                        str5 = str;
                                    } else {
                                        num = Integer.valueOf(i7);
                                    }
                                }
                            }
                            z3 = false;
                            if (z3) {
                            }
                        } else {
                            str = str5;
                        }
                    }
                    Integer num2 = num;
                    if (num2 != null) {
                        int intValue = num2.intValue();
                        String lowerCase = side(num2.intValue()).toLowerCase(Locale.ROOT);
                        Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
                        str2 = str;
                        ask(intValue, "the driving model wants to move " + lowerCase, nowMs, "model");
                        z = false;
                        z2 = true;
                    } else {
                        str2 = str;
                        if (i.getRouteDir() != 0 && this.routeSince != 0 && nowMs - this.routeSince >= 2000 && nowMs >= this.cool[idx(i.getRouteDir())]) {
                            ask(i.getRouteDir(), i.getRouteWhy(), nowMs, "route");
                            z = false;
                            z2 = true;
                        } else if (i.getMergeDir() == 0 || nowMs < this.cool[idx(i.getMergeDir())]) {
                            if (this.passSince == 0 || nowMs - this.passSince < 5000) {
                                z = false;
                            } else {
                                z = false;
                                if (nowMs >= this.cool[0] && !i.getLeftBsm()) {
                                    ask(-1, "slower car ahead at " + speed(i.getLeadV(), i.getMetric()) + " - your set speed is " + speed(i.getVSet(), i.getMetric()), nowMs, "pass");
                                    z2 = true;
                                }
                            }
                            if (this.returnSince == 0 || nowMs - this.returnSince < 3000) {
                                z2 = true;
                            } else {
                                z2 = true;
                                if (nowMs >= this.cool[1]) {
                                    ask(1, "you passed - the right lane is clear, move back right", nowMs, "return");
                                }
                            }
                        } else {
                            ask(i.getMergeDir(), i.getMergeWhy(), nowMs, "merge");
                            z = false;
                            z2 = true;
                        }
                    }
                    this.autoAsk = (this.state != State.ASKING || !i.getAuto() || !i.getCommaConfirms() || Intrinsics.areEqual(this.kind, str2) || Intrinsics.areEqual(this.kind, "return") || Intrinsics.areEqual(this.kind, "merge")) ? z : z2;
                }
                Unit unit = Unit.INSTANCE;
                return;
            case 3:
                if (this.test) {
                    if (nudged(i, nowMs)) {
                        confirm(nowMs, "nudge");
                        return;
                    }
                    if (nowMs - this.since > 12000) {
                        this.state = State.IDLE;
                        this.test = false;
                        return;
                    } else {
                        if (nowMs - this.lastBeep >= 2000) {
                            this.beepNow = true;
                            this.lastBeep = nowMs;
                            return;
                        }
                        return;
                    }
                }
                if (i.getEngaged() && !i.getLaneChanging() && !i.getBrake()) {
                    if (!(this.dir < 0 ? i.getRightBlinker() : i.getLeftBlinker())) {
                        if (this.dir < 0 ? i.getLeftBsm() : i.getRightBsm()) {
                            this.state = State.WAITING;
                            if (this.firstWait == 0) {
                                this.firstWait = nowMs;
                            }
                            this.waitSince = this.firstWait;
                            this.clearSince = 0L;
                            return;
                        }
                        if (nowMs - this.since > 12000) {
                            this.state = State.IDLE;
                            this.cool[idx(this.dir)] = 30000 + nowMs;
                            return;
                        }
                        if (Intrinsics.areEqual(this.kind, "pass") && this.passLostSince != 0 && nowMs - this.passLostSince >= 2000) {
                            this.state = State.IDLE;
                            this.cool[0] = 20000 + nowMs;
                            return;
                        }
                        if (Intrinsics.areEqual(this.kind, "return") && this.returnLostSince != 0 && nowMs - this.returnLostSince >= 2000) {
                            this.state = State.IDLE;
                            this.cool[1] = 20000 + nowMs;
                            return;
                        }
                        if (Intrinsics.areEqual(this.kind, "merge") && this.mergeLostSince != 0 && nowMs - this.mergeLostSince >= 2000) {
                            this.state = State.IDLE;
                            this.cool[idx(this.dir)] = 20000 + nowMs;
                            return;
                        }
                        if (routeGone(nowMs)) {
                            this.state = State.IDLE;
                            this.cool[idx(this.dir)] = 20000 + nowMs;
                            return;
                        }
                        if (nudged(i, nowMs)) {
                            confirm(nowMs, "nudge");
                            return;
                        }
                        if (this.autoAsk && nowMs - this.since >= 3000) {
                            confirm(nowMs, "auto");
                            return;
                        }
                        if (nowMs - this.lastBeep >= 2000) {
                            this.beepNow = true;
                            this.lastBeep = nowMs;
                        }
                        Unit unit2 = Unit.INSTANCE;
                        return;
                    }
                }
                this.state = State.IDLE;
                this.cool[idx(this.dir)] = 15000 + nowMs;
                return;
            case 4:
                if (i.getEngaged() && !i.getLaneChanging() && !i.getBrake() && i.getVEgo() >= i.getMinLcSpeed()) {
                    if (!(this.dir < 0 ? i.getRightBlinker() : i.getLeftBlinker())) {
                        if (nowMs - this.waitSince > (Intrinsics.areEqual(this.kind, "model") ? 30000L : 60000L)) {
                            this.state = State.IDLE;
                            this.cool[idx(this.dir)] = 30000 + nowMs;
                            return;
                        }
                        if (Intrinsics.areEqual(this.kind, "pass") && this.passLostSince != 0 && nowMs - this.passLostSince >= 2000) {
                            this.state = State.IDLE;
                            this.cool[0] = 20000 + nowMs;
                            return;
                        }
                        if (Intrinsics.areEqual(this.kind, "return") && this.owedRight == 0) {
                            this.state = State.IDLE;
                            return;
                        }
                        if (Intrinsics.areEqual(this.kind, "exit") && exitGone(i)) {
                            this.state = State.IDLE;
                            this.exitDoneKey = this.exitKey;
                            return;
                        }
                        if (routeGone(nowMs)) {
                            this.state = State.IDLE;
                            this.cool[idx(this.dir)] = 20000 + nowMs;
                            return;
                        }
                        if (!this.armed && nudged(i, nowMs)) {
                            this.armed = true;
                            this.confirmedBy = "nudge";
                        }
                        if (this.dir < 0 ? i.getLeftBsm() : i.getRightBsm()) {
                            this.clearSince = 0L;
                        } else {
                            if (this.clearSince == 0) {
                                this.clearSince = nowMs;
                            }
                            if (nowMs - this.clearSince >= 1000) {
                                if (this.armed) {
                                    this.state = State.SIGNALING;
                                    this.since = nowMs;
                                    this.holdSide = this.dir;
                                } else {
                                    this.state = State.ASKING;
                                    this.since = nowMs;
                                    this.lastBeep = 0L;
                                    this.reasked = true;
                                }
                            }
                        }
                        Unit unit3 = Unit.INSTANCE;
                        return;
                    }
                }
                this.state = State.IDLE;
                this.cool[idx(this.dir)] = 15000 + nowMs;
                return;
            case 5:
                if (this.test) {
                    this.holdSide = this.dir;
                    if (nowMs - this.since > 8000) {
                        finish("test done - the blinker was held 8 seconds. openpilot only changes lanes while driving", nowMs);
                        this.test = false;
                        return;
                    }
                    return;
                }
                boolean leftBsm = this.dir < 0 ? i.getLeftBsm() : i.getRightBsm();
                if (leftBsm && !i.getLaneChanging() && i.getEngaged() && !i.getBrake()) {
                    this.state = State.WAITING;
                    if (this.firstWait == 0) {
                        this.firstWait = nowMs;
                    }
                    this.waitSince = this.firstWait;
                    this.clearSince = 0L;
                    this.armed = true;
                    this.holdSide = 0;
                    return;
                }
                if (!i.getEngaged()) {
                    str4 = "openpilot disengaged - stopped";
                } else if (i.getBrake()) {
                    str4 = "brake pressed - stopped";
                } else if (i.getVEgo() < i.getMinLcSpeed()) {
                    str4 = "too slow for a lane change - stopped";
                } else {
                    if (this.dir < 0 ? i.getRightBlinker() : i.getLeftBlinker()) {
                        str4 = "other blinker on - stopped";
                    } else if (Intrinsics.areEqual(this.kind, "exit") && exitGone(i)) {
                        this.exitDoneKey = this.exitKey;
                        str4 = "the exit is behind us - not taken";
                    } else if (!i.getLaneChanging() && routeGone(nowMs)) {
                        this.cool[idx(this.dir)] = 20000 + nowMs;
                        str4 = "already in a lane for the route - stopped";
                    } else if (!Intrinsics.areEqual(this.kind, "exit") || nowMs - this.since <= 6000) {
                        if (nowMs - this.since > 8000) {
                            if (leftBsm) {
                                String lowerCase2 = side(this.dir).toLowerCase(Locale.ROOT);
                                Intrinsics.checkNotNullExpressionValue(lowerCase2, "toLowerCase(...)");
                                str4 = "a car stayed in the " + lowerCase2 + " blind spot - not changed";
                            } else {
                                str4 = i.getCommaConfirms() ? "openpilot didn't start it (lane too narrow or lane changes off)" : "openpilot didn't start it (lane too narrow, lane changes off, or it wants a nudge)";
                            }
                        }
                    } else if (this.exitTries < 5) {
                        this.exitRetryAt = 1500 + nowMs;
                        str4 = "openpilot hasn't moved over yet - signaling again";
                    } else {
                        this.exitDoneKey = this.exitKey;
                        str4 = "openpilot didn't take the exit - take it yourself";
                    }
                }
                String str6 = str4;
                if (str6 != null) {
                    finish(str6, nowMs);
                    return;
                }
                this.holdSide = this.dir;
                if (i.getLaneChanging() && i.getLaneChangeDir() == this.dir) {
                    this.state = State.CHANGING;
                    this.since = nowMs;
                    this.holdSide = 0;
                }
                Unit unit4 = Unit.INSTANCE;
                return;
            case 6:
                this.holdSide = 0;
                if (!i.getLaneChanging()) {
                    if (Intrinsics.areEqual(this.kind, "exit")) {
                        this.exitDoneKey = this.exitKey;
                        this.exitRetryAt = 0L;
                    }
                    if (Intrinsics.areEqual(this.kind, "exit")) {
                        str3 = "moved over for the exit";
                    } else {
                        String lowerCase3 = side(this.dir).toLowerCase(Locale.ROOT);
                        Intrinsics.checkNotNullExpressionValue(lowerCase3, "toLowerCase(...)");
                        str3 = "changed lanes " + lowerCase3;
                    }
                    finish(str3, nowMs);
                    this.cool[idx(this.dir)] = (Intrinsics.areEqual(this.kind, "pass") ? 45000 : 10000) + nowMs;
                } else if (nowMs - this.since > 15000) {
                    finish("lane change took too long", nowMs);
                }
                Unit unit5 = Unit.INSTANCE;
                return;
            default:
                throw new NoWhenBranchMatchedException();
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

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Code restructure failed: missing block: B:15:0x0041, code lost:
        
            if (r4.equals("right") == false) goto L59;
         */
        /* JADX WARN: Code restructure failed: missing block: B:16:0x006b, code lost:
        
            r6 = 1;
         */
        /* JADX WARN: Code restructure failed: missing block: B:17:0x0078, code lost:
        
            r17 = r20.optDouble("maneuverDistance", Double.NaN);
         */
        /* JADX WARN: Code restructure failed: missing block: B:18:0x0084, code lost:
        
            if (java.lang.Double.isInfinite(r17) != false) goto L41;
         */
        /* JADX WARN: Code restructure failed: missing block: B:20:0x008a, code lost:
        
            if (java.lang.Double.isNaN(r17) != false) goto L41;
         */
        /* JADX WARN: Code restructure failed: missing block: B:21:0x008c, code lost:
        
            r3 = true;
         */
        /* JADX WARN: Code restructure failed: missing block: B:22:0x008d, code lost:
        
            if (r3 != false) goto L43;
         */
        /* JADX WARN: Code restructure failed: missing block: B:24:0x0090, code lost:
        
            r1 = r20.optString("maneuverPrimaryText");
         */
        /* JADX WARN: Code restructure failed: missing block: B:25:0x009a, code lost:
        
            if (kotlin.jvm.internal.Intrinsics.areEqual(r2, "off ramp") == false) goto L46;
         */
        /* JADX WARN: Code restructure failed: missing block: B:26:0x009c, code lost:
        
            r7 = "exit";
         */
        /* JADX WARN: Code restructure failed: missing block: B:27:0x009e, code lost:
        
            r3 = r7;
         */
        /* JADX WARN: Code restructure failed: missing block: B:28:0x009f, code lost:
        
            if (r21 == false) goto L49;
         */
        /* JADX WARN: Code restructure failed: missing block: B:29:0x00a1, code lost:
        
            r5 = new java.lang.StringBuilder().append(((int) (r17 / 10)) * 10);
            r7 = " m";
         */
        /* JADX WARN: Code restructure failed: missing block: B:30:0x00cc, code lost:
        
            r5 = r5.append(r7).toString();
         */
        /* JADX WARN: Code restructure failed: missing block: B:31:0x00d6, code lost:
        
            if (r6 >= 0) goto L53;
         */
        /* JADX WARN: Code restructure failed: missing block: B:32:0x00d8, code lost:
        
            r9 = "left";
         */
        /* JADX WARN: Code restructure failed: missing block: B:33:0x00d9, code lost:
        
            kotlin.jvm.internal.Intrinsics.checkNotNull(r1);
         */
        /* JADX WARN: Code restructure failed: missing block: B:34:0x00e3, code lost:
        
            if (kotlin.text.StringsKt.isBlank(r1) != false) goto L56;
         */
        /* JADX WARN: Code restructure failed: missing block: B:35:0x00e5, code lost:
        
            r8 = " - " + r1;
         */
        /* JADX WARN: Code restructure failed: missing block: B:38:0x00f9, code lost:
        
            r8 = "";
         */
        /* JADX WARN: Code restructure failed: missing block: B:39:0x00b4, code lost:
        
            r5 = new java.lang.StringBuilder().append(((int) ((3.28084d * r17) / 50)) * 50);
            r7 = " ft";
         */
        /* JADX WARN: Code restructure failed: missing block: B:41:0x0049, code lost:
        
            if (r4.equals("left") == false) goto L59;
         */
        /* JADX WARN: Code restructure failed: missing block: B:42:0x0077, code lost:
        
            r6 = -1;
         */
        /* JADX WARN: Code restructure failed: missing block: B:44:0x0053, code lost:
        
            if (r4.equals("slightLeft") == false) goto L59;
         */
        /* JADX WARN: Code restructure failed: missing block: B:46:0x005d, code lost:
        
            if (r4.equals("slightRight") == false) goto L59;
         */
        /* JADX WARN: Code restructure failed: missing block: B:48:0x0067, code lost:
        
            if (r4.equals("sharpRight") == false) goto L59;
         */
        /* JADX WARN: Code restructure failed: missing block: B:50:0x0073, code lost:
        
            if (r4.equals("sharpLeft") == false) goto L59;
         */
        /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
        java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
        	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
        	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Exit exitNeed(JSONObject nav, boolean metric) {
            if (nav != null) {
                boolean z = false;
                if (nav.optBoolean("valid", false)) {
                    String type = nav.optString("maneuverType");
                    String mod = nav.optString("maneuverModifier");
                    String str = "fork";
                    if ((Intrinsics.areEqual(type, "off ramp") || Intrinsics.areEqual(type, "fork")) && mod != null) {
                        String str2 = "right";
                        switch (mod.hashCode()) {
                            case -1572118767:
                                break;
                            case -1485380526:
                                break;
                            case -711874727:
                                break;
                            case -438788310:
                                break;
                            case 3317767:
                                break;
                            case 108511772:
                                break;
                        }
                        return null;
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

        /* JADX WARN: Removed duplicated region for block: B:25:0x0074 A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:27:? A[LOOP:0: B:14:0x002b->B:27:?, LOOP_END, SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final boolean arrowToward(Nav.Lane l, int dir) {
            Iterable $this$any$iv;
            String a;
            Intrinsics.checkNotNullParameter(l, "l");
            if (l.getArrows().isEmpty()) {
                return true;
            }
            Iterable $this$any$iv2 = l.getArrows();
            if (!($this$any$iv2 instanceof Collection) || !((Collection) $this$any$iv2).isEmpty()) {
                Iterator it = $this$any$iv2.iterator();
                while (true) {
                    if (it.hasNext()) {
                        Object element$iv = it.next();
                        String a2 = (String) element$iv;
                        if (!Intrinsics.areEqual(a2, "none")) {
                            String str = a2;
                            if (!(dir > 0 ? StringsKt.contains((CharSequence) str, (CharSequence) "right", false) : StringsKt.contains((CharSequence) str, (CharSequence) "left", false) || StringsKt.contains((CharSequence) a2, (CharSequence) "uturn", false))) {
                                a = null;
                                if (a == null) {
                                    $this$any$iv = 1;
                                    break;
                                }
                            }
                        }
                        a = 1;
                        if (a == null) {
                        }
                    } else {
                        $this$any$iv = null;
                        break;
                    }
                }
            } else {
                $this$any$iv = null;
            }
            return $this$any$iv != null;
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

        /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
        java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
        	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
        	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
         */
        public final int routeSoon(JSONObject nav) {
            String optString;
            if (nav == null || !nav.optBoolean("valid", false)) {
                return 0;
            }
            String type = nav.optString("maneuverType");
            if (!Intrinsics.areEqual(type, "off ramp") && !Intrinsics.areEqual(type, "fork") && !Intrinsics.areEqual(type, "turn")) {
                return 0;
            }
            double dist = nav.optDouble("maneuverDistance", Double.NaN);
            if (!((Double.isInfinite(dist) || Double.isNaN(dist)) ? false : true) || dist > 3200.0d || (optString = nav.optString("maneuverModifier")) == null) {
                return 0;
            }
            switch (optString.hashCode()) {
                case -1572118767:
                    if (!optString.equals("sharpLeft")) {
                    }
                    break;
                case -1485380526:
                    if (!optString.equals("sharpRight")) {
                    }
                    break;
                case -711874727:
                    if (!optString.equals("slightRight")) {
                    }
                    break;
                case -438788310:
                    if (!optString.equals("slightLeft")) {
                    }
                    break;
                case 3317767:
                    if (!optString.equals("left")) {
                    }
                    break;
                case 108511772:
                    if (!optString.equals("right")) {
                    }
                    break;
            }
            return 0;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ Pair routeNeed$default(Companion companion, JSONObject jSONObject, LaneBrain laneBrain, boolean z, List list, int i, Object obj) {
            if ((i & 8) != 0) {
                list = CollectionsKt.emptyList();
            }
            return companion.routeNeed(jSONObject, laneBrain, z, list);
        }

        /* JADX WARN: Code restructure failed: missing block: B:21:0x0061, code lost:
        
            if (r9.equals("right") == false) goto L37;
         */
        /* JADX WARN: Code restructure failed: missing block: B:22:0x0086, code lost:
        
            r1 = 1;
         */
        /* JADX WARN: Code restructure failed: missing block: B:23:0x0096, code lost:
        
            r2 = r1;
            r13 = r32.optDouble("maneuverDistance", Double.NaN);
         */
        /* JADX WARN: Code restructure failed: missing block: B:24:0x00a5, code lost:
        
            if (java.lang.Double.isInfinite(r13) != false) goto L44;
         */
        /* JADX WARN: Code restructure failed: missing block: B:26:0x00ab, code lost:
        
            if (java.lang.Double.isNaN(r13) != false) goto L44;
         */
        /* JADX WARN: Code restructure failed: missing block: B:27:0x00ad, code lost:
        
            r1 = true;
         */
        /* JADX WARN: Code restructure failed: missing block: B:28:0x00b0, code lost:
        
            if (r1 == false) goto L92;
         */
        /* JADX WARN: Code restructure failed: missing block: B:30:0x00b6, code lost:
        
            if (r13 > 1600.0d) goto L92;
         */
        /* JADX WARN: Code restructure failed: missing block: B:32:0x00bf, code lost:
        
            if (r13 >= 150.0d) goto L51;
         */
        /* JADX WARN: Code restructure failed: missing block: B:33:0x00c1, code lost:
        
            r1 = 0;
         */
        /* JADX WARN: Code restructure failed: missing block: B:36:0x00c9, code lost:
        
            r1 = r33.sideLanes(r2);
            r9 = r1.component1().intValue();
            r20 = r1.component2().doubleValue();
         */
        /* JADX WARN: Code restructure failed: missing block: B:37:0x00e3, code lost:
        
            if (r9 < 1) goto L89;
         */
        /* JADX WARN: Code restructure failed: missing block: B:39:0x00ec, code lost:
        
            if (r20 >= 0.6d) goto L56;
         */
        /* JADX WARN: Code restructure failed: missing block: B:40:0x00ee, code lost:
        
            r1 = 0;
         */
        /* JADX WARN: Code restructure failed: missing block: B:43:0x00f7, code lost:
        
            r1 = ourLaneAt$default(r31, r35, r33, r2, 0.0d, false, 24, null);
         */
        /* JADX WARN: Code restructure failed: missing block: B:44:0x0122, code lost:
        
            if (r1 == null) goto L61;
         */
        /* JADX WARN: Code restructure failed: missing block: B:45:0x0124, code lost:
        
            r2 = 1;
         */
        /* JADX WARN: Code restructure failed: missing block: B:46:0x0129, code lost:
        
            if (r1.getOk() != true) goto L62;
         */
        /* JADX WARN: Code restructure failed: missing block: B:47:0x012b, code lost:
        
            r16 = true;
         */
        /* JADX WARN: Code restructure failed: missing block: B:48:0x0131, code lost:
        
            if (r16 == false) goto L66;
         */
        /* JADX WARN: Code restructure failed: missing block: B:52:0x013c, code lost:
        
            if (r34 == false) goto L70;
         */
        /* JADX WARN: Code restructure failed: missing block: B:53:0x013e, code lost:
        
            r1 = new java.lang.StringBuilder().append(((int) (r13 / 100)) / 10.0d);
            r3 = " km";
         */
        /* JADX WARN: Code restructure failed: missing block: B:54:0x0151, code lost:
        
            r1 = r1.append(r3).toString();
         */
        /* JADX WARN: Code restructure failed: missing block: B:56:0x0198, code lost:
        
            if (kotlin.jvm.internal.Intrinsics.areEqual(r15, "off ramp") == false) goto L77;
         */
        /* JADX WARN: Code restructure failed: missing block: B:57:0x019a, code lost:
        
            r7 = "exit";
         */
        /* JADX WARN: Code restructure failed: missing block: B:58:0x01a9, code lost:
        
            r3 = r7;
            r4 = java.lang.Integer.valueOf(r2);
         */
        /* JADX WARN: Code restructure failed: missing block: B:59:0x01ae, code lost:
        
            if (r2 >= 0) goto L84;
         */
        /* JADX WARN: Code restructure failed: missing block: B:60:0x01b0, code lost:
        
            r6 = "left";
         */
        /* JADX WARN: Code restructure failed: missing block: B:61:0x01b5, code lost:
        
            if (r9 <= r2) goto L87;
         */
        /* JADX WARN: Code restructure failed: missing block: B:62:0x01b7, code lost:
        
            r12 = "s";
         */
        /* JADX WARN: Code restructure failed: missing block: B:65:0x01b3, code lost:
        
            r6 = "right";
         */
        /* JADX WARN: Code restructure failed: missing block: B:67:0x01a3, code lost:
        
            if (kotlin.jvm.internal.Intrinsics.areEqual(r15, "fork") == false) goto L80;
         */
        /* JADX WARN: Code restructure failed: missing block: B:68:0x01a5, code lost:
        
            r7 = "fork";
         */
        /* JADX WARN: Code restructure failed: missing block: B:69:0x01a7, code lost:
        
            r7 = "turn";
         */
        /* JADX WARN: Code restructure failed: missing block: B:71:0x015e, code lost:
        
            if (r13 >= 400.0d) goto L73;
         */
        /* JADX WARN: Code restructure failed: missing block: B:72:0x0160, code lost:
        
            r1 = new java.lang.StringBuilder().append(((int) ((3.28084d * r13) / 50)) * 50);
            r3 = " ft";
         */
        /* JADX WARN: Code restructure failed: missing block: B:73:0x0178, code lost:
        
            r1 = new java.lang.StringBuilder().append(((int) ((r13 / 1609.34d) * 10)) / 10.0d);
            r3 = " mi";
         */
        /* JADX WARN: Code restructure failed: missing block: B:74:0x012f, code lost:
        
            r16 = false;
         */
        /* JADX WARN: Code restructure failed: missing block: B:75:0x012e, code lost:
        
            r2 = 1;
         */
        /* JADX WARN: Code restructure failed: missing block: B:76:0x01f9, code lost:
        
            r1 = 0;
         */
        /* JADX WARN: Code restructure failed: missing block: B:77:0x0205, code lost:
        
            r1 = 0;
         */
        /* JADX WARN: Code restructure failed: missing block: B:78:0x00af, code lost:
        
            r1 = false;
         */
        /* JADX WARN: Code restructure failed: missing block: B:79:0x0090, code lost:
        
            r1 = 0;
         */
        /* JADX WARN: Code restructure failed: missing block: B:83:0x0068, code lost:
        
            if (r9.equals("left") == false) goto L37;
         */
        /* JADX WARN: Code restructure failed: missing block: B:84:0x0095, code lost:
        
            r1 = -1;
         */
        /* JADX WARN: Code restructure failed: missing block: B:86:0x0071, code lost:
        
            if (r9.equals("slightLeft") == false) goto L37;
         */
        /* JADX WARN: Code restructure failed: missing block: B:88:0x007a, code lost:
        
            if (r9.equals("slightRight") == false) goto L37;
         */
        /* JADX WARN: Code restructure failed: missing block: B:90:0x0083, code lost:
        
            if (r9.equals("sharpRight") == false) goto L37;
         */
        /* JADX WARN: Code restructure failed: missing block: B:92:0x008e, code lost:
        
            if (r9.equals("sharpLeft") == false) goto L37;
         */
        /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
        java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
        	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
        	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Pair<Integer, String> routeNeed(JSONObject nav, LaneBrain tr, boolean metric, List<Nav.Lane> lanes) {
            Integer num;
            Intrinsics.checkNotNullParameter(tr, "tr");
            Intrinsics.checkNotNullParameter(lanes, "lanes");
            String str = "";
            if (nav == null) {
                num = 0;
            } else {
                if (nav.optBoolean("valid", false)) {
                    String type = nav.optString("maneuverType");
                    String mod = nav.optString("maneuverModifier");
                    if (!Intrinsics.areEqual(type, "off ramp") && !Intrinsics.areEqual(type, "fork") && !Intrinsics.areEqual(type, "turn")) {
                        return TuplesKt.to(0, "");
                    }
                    if (mod != null) {
                        switch (mod.hashCode()) {
                            case -1572118767:
                                break;
                            case -1485380526:
                                break;
                            case -711874727:
                                break;
                            case -438788310:
                                break;
                            case 3317767:
                                break;
                            case 108511772:
                                break;
                        }
                        return TuplesKt.to(0, "");
                    }
                    Integer num2 = 0;
                    return TuplesKt.to(num2, "");
                }
                num = 0;
            }
            return TuplesKt.to(num, "");
        }
    }
}
