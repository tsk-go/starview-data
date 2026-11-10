package com.starpilot.starview;

import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.json.JSONObject;

/* compiled from: TurnSignal.kt */
public final class TurnSignal {
    public static final double ARM_M = 400.0d;
    public static final double BASE_M = 15.0d;
    public static final Companion Companion = new Companion(null);
    public static final double HEADING_MIN = 0.5235987755982988d;
    public static final double HEADING_YAW = 1.0995574287564276d;
    public static final double LAW_M = 30.5d;
    public static final double LEAD_S = 12.0d;
    public static final double LOW_LEAD_S = 5.0d;
    public static final long MAX_HOLD_MS = 180000;
    public static final double MAX_M = 250.0d;
    public static final double MIN_M = 30.0d;
    public static final float STEER_BACK = 0.25f;
    public static final float STEER_TURN = 90.0f;
    public static final double TURNED = 0.9424777960769379d;
    public static final double YAW_BACK = 0.33d;
    private long calmSince;
    private int dir;
    private boolean gyroSeen;
    private double heading;
    private int holdSide;
    private boolean laneHold;
    private long lastMs;
    private boolean moved;
    private long navGoneSince;
    private long rightSince;
    private long since;
    private float steerPeak;
    private double tKnown;
    private double travelled;
    private long wrongSince;
    private double yawPeak;
    private State state = State.IDLE;
    private String street = "";
    private String result = "";
    private double distM = Double.NaN;
    private double startedAtM = Double.NaN;
    private double gap = Double.NaN;
    private String key = "";
    private String skipKey = "";
    private double stamp = Double.NaN;
    private double dKnown = Double.NaN;

    /* compiled from: TurnSignal.kt */
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[State.values().length];
            try {
                iArr[State.IDLE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[State.WAITING.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[State.SIGNALING.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                iArr[State.DONE.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* compiled from: TurnSignal.kt */
    public enum State {
        IDLE,
        WAITING,
        SIGNALING,
        DONE;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries($VALUES);

        public static EnumEntries<State> getEntries() {
            return $ENTRIES;
        }
    }

    /* compiled from: TurnSignal.kt */
    public static final class In {
        private final boolean busy;
        private final double crossGap;
        private final boolean leftBlinker;
        private final float minLcSpeed;
        private final JSONObject nav;
        private final long nowMs;
        private final boolean parked;
        private final boolean rightBlinker;
        private final float steerDeg;
        private final boolean useTurnDesires;
        private final float vEgo;
        private final boolean wrongLane;
        private final float yawRate;

        public In(long nowMs, JSONObject nav, float vEgo, float yawRate, boolean leftBlinker, boolean rightBlinker, float minLcSpeed, boolean useTurnDesires, double crossGap, boolean busy, boolean parked, float steerDeg, boolean wrongLane) {
            this.nowMs = nowMs;
            this.nav = nav;
            this.vEgo = vEgo;
            this.yawRate = yawRate;
            this.leftBlinker = leftBlinker;
            this.rightBlinker = rightBlinker;
            this.minLcSpeed = minLcSpeed;
            this.useTurnDesires = useTurnDesires;
            this.crossGap = crossGap;
            this.busy = busy;
            this.parked = parked;
            this.steerDeg = steerDeg;
            this.wrongLane = wrongLane;
        }

        public /* synthetic */ In(long j, JSONObject jSONObject, float f, float f2, boolean z, boolean z2, float f3, boolean z3, double d, boolean z4, boolean z5, float f4, boolean z6, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(j, jSONObject, f, f2, z, z2, f3, z3, (i & 256) != 0 ? Double.NaN : d, (i & 512) != 0 ? false : z4, (i & 1024) != 0 ? false : z5, (i & 2048) != 0 ? Float.NaN : f4, (i & 4096) != 0 ? false : z6);
        }

        public final long getNowMs() {
            return this.nowMs;
        }

        public final JSONObject getNav() {
            return this.nav;
        }

        public final float getVEgo() {
            return this.vEgo;
        }

        public final float getYawRate() {
            return this.yawRate;
        }

        public final boolean getLeftBlinker() {
            return this.leftBlinker;
        }

        public final boolean getRightBlinker() {
            return this.rightBlinker;
        }

        public final float getMinLcSpeed() {
            return this.minLcSpeed;
        }

        public final boolean getUseTurnDesires() {
            return this.useTurnDesires;
        }

        public final double getCrossGap() {
            return this.crossGap;
        }

        public final boolean getBusy() {
            return this.busy;
        }

        public final boolean getParked() {
            return this.parked;
        }

        public final float getSteerDeg() {
            return this.steerDeg;
        }

        public final boolean getWrongLane() {
            return this.wrongLane;
        }
    }

    public final State getState() {
        return this.state;
    }

    public final int getDir() {
        return this.dir;
    }

    public final String getStreet() {
        return this.street;
    }

    public final int getHoldSide() {
        return this.holdSide;
    }

    public final String getResult() {
        return this.result;
    }

    public final double getDistM() {
        return this.distM;
    }

    public final double getStartedAtM() {
        return this.startedAtM;
    }

    public final double getGap() {
        return this.gap;
    }

    public final boolean getLaneHold() {
        return this.laneHold;
    }

    /* compiled from: TurnSignal.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final double window(double v) {
            return RangesKt.coerceIn((Math.max(v, 0.0d) * 12.0d) + 15.0d, 30.0d, 250.0d);
        }

        /* VF: from Vineflower (jadx: ) */
        public final int turnDir(String var1, String var2) {
            Intrinsics.checkNotNullParameter(var1, "type");
            Intrinsics.checkNotNullParameter(var2, "mod");
            boolean var4 = Intrinsics.areEqual(var1, "turn");
            byte var3 = 0;
            if (var4) {
                label39: {
                    switch (var2.hashCode()) {
                        case -1572118767:
                            if (!var2.equals("sharpLeft")) {
                                return var3;
                            }
                            break;
                        case -1485380526:
                            if (!var2.equals("sharpRight")) {
                                return var3;
                            }
                            break label39;
                        case 3317767:
                            if (!var2.equals("left")) {
                                return var3;
                            }
                            break;
                        case 108511772:
                            if (!var2.equals("right")) {
                                return var3;
                            }
                            break label39;
                        default:
                            return var3;
                    }

                    var3 = -1;
                    return var3;
                }

                var3 = 1;
            }

            return var3;
        }

        public final double lastCrossGap(List<Pair<Double, String>> cross, double turnAheadM, String turnStreet) {
            Intrinsics.checkNotNullParameter(cross, "cross");
            Intrinsics.checkNotNullParameter(turnStreet, "turnStreet");
            double best = Double.NaN;
            for (Pair<Double, String> pair : cross) {
                double s = pair.component1().doubleValue();
                String name = pair.component2();
                double g = turnAheadM - s;
                if (g >= 15.0d) {
                    if (turnStreet.length() > 0) {
                        if ((name.length() > 0) && OsmRoads.INSTANCE.sameStreet(name, turnStreet)) {
                        }
                    }
                    if (Double.isNaN(best) || g < best) {
                        best = g;
                    }
                }
            }
            return best;
        }

        public final double startDistance(double v, double minLc, boolean useTurnDesires, double crossGap) {
            double s = window(v) * 0.9d;
            if (v < minLc && useTurnDesires) {
                s = Math.min(s, Math.max(35.5d, (v * 5.0d) + 10.0d));
            }
            if (((Double.isInfinite(crossGap) || Double.isNaN(crossGap)) ? false : true) && crossGap - 5.0d >= 30.5d) {
                s = Math.min(s, crossGap - 5.0d);
            }
            return Math.max(s, 31.5d);
        }
    }

    public final void update(In i) {
        String str;
        String str2;
        int navDir;
        double dEst;
        float toward;
        boolean other;
        Intrinsics.checkNotNullParameter(i, "i");
        long now = i.getNowMs();
        double dt = this.lastMs == 0 ? 0.0d : RangesKt.coerceIn((now - this.lastMs) / 1000.0d, 0.0d, 0.5d);
        this.lastMs = now;
        this.travelled += Math.max(i.getVEgo(), 0.0f) * dt;
        if (this.state == State.SIGNALING && (this.distM <= 40.0d || this.moved)) {
            this.heading += i.getYawRate() * dt;
        }
        JSONObject nav = i.getNav();
        String navKey = "";
        int navDir2 = 0;
        String navStreet = "";
        if (nav != null) {
            String type = nav.optString("maneuverType");
            String mod = nav.optString("maneuverModifier");
            navStreet = nav.optString("maneuverPrimaryText");
            navKey = type + "|" + mod + "|" + navStreet;
            Companion companion = Companion;
            Intrinsics.checkNotNull(type);
            Intrinsics.checkNotNull(mod);
            navDir2 = companion.turnDir(type, mod);
            this.navGoneSince = 0L;
        } else if (this.navGoneSince == 0) {
            this.navGoneSince = now;
        }
        boolean navGone = nav == null && now - this.navGoneSince > 3000;
        if (nav != null && (Intrinsics.areEqual(navKey, this.key) || this.state == State.IDLE || this.state == State.DONE)) {
            double st = nav.optDouble("updatedAtMonotonic", Double.NaN);
            double d = nav.optDouble("maneuverDistance", Double.NaN);
            if ((Double.isInfinite(d) || Double.isNaN(d)) ? false : true) {
                if (!(st == this.stamp) || !Intrinsics.areEqual(navKey, this.key)) {
                    this.stamp = st;
                    this.dKnown = d;
                    this.tKnown = this.travelled;
                }
            }
        }
        double dEst2 = this.dKnown - (this.travelled - this.tKnown);
        if (i.getWrongLane()) {
            this.rightSince = 0L;
            if (this.wrongSince == 0) {
                this.wrongSince = now;
            }
            if (now - this.wrongSince >= 1000) {
                this.laneHold = true;
            }
        } else {
            this.wrongSince = 0L;
            if (this.rightSince == 0) {
                this.rightSince = now;
            }
            if (now - this.rightSince >= 1500) {
                this.laneHold = false;
            }
        }
        switch (WhenMappings.$EnumSwitchMapping$0[this.state.ordinal()]) {
            case 1:
                int navDir3 = navDir2;
                String navStreet2 = navStreet;
                boolean navGone2 = false;
                if (nav != null && !Intrinsics.areEqual(navKey, this.skipKey)) {
                    this.skipKey = "";
                }
                if (navDir3 != 0 && !Intrinsics.areEqual(navKey, this.skipKey)) {
                    if ((Double.isInfinite(dEst2) || Double.isNaN(dEst2)) ? false : true) {
                        if (0.0d <= dEst2 && dEst2 <= 400.0d) {
                            navGone2 = true;
                        }
                        if (navGone2) {
                            this.state = State.WAITING;
                            this.key = navKey;
                            this.dir = navDir3;
                            this.street = navStreet2;
                            this.gap = Double.NaN;
                            this.distM = dEst2;
                            return;
                        }
                    }
                }
                return;
            case 2:
                if (navGone) {
                    str = "";
                } else {
                    if (nav == null || Intrinsics.areEqual(navKey, this.key)) {
                        this.distM = dEst2;
                        double crossGap = i.getCrossGap();
                        if ((Double.isInfinite(crossGap) || Double.isNaN(crossGap)) ? false : true) {
                            this.gap = i.getCrossGap();
                        }
                        double start = Companion.startDistance(i.getVEgo(), i.getMinLcSpeed(), i.getUseTurnDesires(), this.gap);
                        boolean other2 = this.dir < 0 ? i.getRightBlinker() : i.getLeftBlinker();
                        if (this.distM < -10.0d) {
                            this.state = State.IDLE;
                            this.skipKey = this.key;
                            this.key = "";
                            return;
                        }
                        if (this.distM <= start && this.distM > 3.0d && !i.getBusy() && !i.getParked() && !other2 && !this.laneHold) {
                            this.state = State.SIGNALING;
                            this.since = now;
                            this.heading = 0.0d;
                            this.calmSince = 0L;
                            this.moved = false;
                            this.holdSide = this.dir;
                            this.startedAtM = this.distM;
                            this.steerPeak = 0.0f;
                            this.yawPeak = 0.0d;
                            return;
                        }
                        return;
                    }
                    str = "";
                }
                this.state = State.IDLE;
                this.key = str;
                return;
            case 3:
                if (i.getParked()) {
                    this.state = State.WAITING;
                    this.holdSide = 0;
                    this.result = "in park - let go";
                    return;
                }
                if (!this.laneHold || this.moved || this.steerPeak >= 45.0f) {
                    str2 = "";
                } else {
                    str2 = "";
                    if (this.heading * (-this.dir) < Math.toRadians(15.0d)) {
                        this.state = State.WAITING;
                        this.holdSide = 0;
                        this.result = "not in a lane that turns " + (this.dir < 0 ? "left" : "right") + " - let go";
                        return;
                    }
                }
                this.holdSide = this.dir;
                this.distM = dEst2;
                if (nav != null && !Intrinsics.areEqual(navKey, this.key) && !this.moved) {
                    this.moved = true;
                    if (this.distM > 40.0d) {
                        finish("route changed - stopped", now);
                        return;
                    }
                }
                boolean other3 = this.dir < 0 ? i.getRightBlinker() : i.getLeftBlinker();
                boolean turnedEnough = this.heading * ((double) (-this.dir)) >= 0.9424777960769379d;
                if (this.street.length() > 0) {
                    str2 = " onto " + this.street;
                }
                String done = "turned" + str2;
                if (this.distM <= 40.0d || this.moved) {
                    if (Math.abs(i.getYawRate()) > 0.02f) {
                        this.gyroSeen = true;
                    }
                    float toward2 = i.getYawRate() * (-this.dir);
                    navDir = navDir2;
                    dEst = dEst2;
                    if (toward2 > this.yawPeak) {
                        toward = toward2;
                        this.yawPeak = toward;
                    } else {
                        toward = toward2;
                    }
                    other = other3;
                    double turned = this.heading * (-this.dir);
                    float steerDeg = i.getSteerDeg();
                    if (((Float.isInfinite(steerDeg) || Float.isNaN(steerDeg)) ? false : true) && i.getVEgo() > 0.5f) {
                        float w = i.getSteerDeg() * (-this.dir);
                        if (w > this.steerPeak) {
                            this.steerPeak = w;
                        }
                        if (this.steerPeak >= 90.0f && w <= Math.max(20.0f, this.steerPeak * 0.25f) && (!this.gyroSeen || turned >= 0.5235987755982988d)) {
                            finish(done, now);
                            return;
                        }
                    }
                    if (turned >= 1.0995574287564276d && this.yawPeak > 0.1d) {
                        if (toward <= this.yawPeak * 0.33d) {
                            finish(done, now);
                            return;
                        }
                    }
                } else {
                    navDir = navDir2;
                    dEst = dEst2;
                    other = other3;
                }
                if (turnedEnough) {
                    if (Math.abs(i.getYawRate()) < 0.06f) {
                        if (this.calmSince == 0) {
                            this.calmSince = now;
                        }
                        if (now - this.calmSince >= 500) {
                            finish(done, now);
                            return;
                        }
                    } else {
                        this.calmSince = 0L;
                    }
                }
                if (!other) {
                    if (!navGone || this.distM <= 40.0d) {
                        if (!turnedEnough && this.distM < -30.0d) {
                            finish("drove past the turn - stopped", now);
                            return;
                        } else {
                            if (now - this.since > 180000) {
                                finish("held 3 minutes - stopped", now);
                                return;
                            }
                            return;
                        }
                    }
                    finish("route ended - stopped", now);
                    return;
                }
                finish("other blinker on - stopped", now);
                return;
            case 4:
                this.holdSide = 0;
                if (now - this.since > 3000) {
                    this.state = State.IDLE;
                    this.key = "";
                }
                return;
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    private final void finish(String text, long now) {
        this.result = text;
        this.state = State.DONE;
        this.holdSide = 0;
        this.since = now;
        this.skipKey = this.key;
    }

    public final void cancel(long now) {
        if (this.state == State.SIGNALING || this.state == State.WAITING) {
            finish("cancelled", now);
        }
    }

    public final void reset() {
        this.state = State.IDLE;
        this.holdSide = 0;
        this.key = "";
        this.lastMs = 0L;
        this.laneHold = false;
        this.wrongSince = 0L;
        this.rightSince = 0L;
    }
}
