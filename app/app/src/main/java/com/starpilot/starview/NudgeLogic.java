package com.starpilot.starview;

import kotlin.collections.ArraysKt;
import kotlin.collections.SetsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.json.JSONObject;

/* compiled from: NudgeHint.kt */
public final class NudgeLogic {
    public static final NudgeLogic INSTANCE = new NudgeLogic();
    private static final double[] KEEP_V = {0.0d, 15.0d, 30.0d};
    private static final double[] KEEP_D = {25.0d, 90.0d, 160.0d};

    /* compiled from: NudgeHint.kt */
    public enum State {
        NONE,
        SOON,
        READY,
        WAIT;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries($VALUES);

        public static EnumEntries<State> getEntries() {
            return $ENTRIES;
        }
    }

    private NudgeLogic() {
    }

    /* compiled from: NudgeHint.kt */
    public static final class Result {
        private final double distanceM;
        private final boolean right;
        private final State state;
        private final String why;
        private final double windowM;

        public Result(State state, boolean right, double distanceM, double windowM, String why) {
            Intrinsics.checkNotNullParameter(state, "state");
            Intrinsics.checkNotNullParameter(why, "why");
            this.state = state;
            this.right = right;
            this.distanceM = distanceM;
            this.windowM = windowM;
            this.why = why;
        }

        public /* synthetic */ Result(State state, boolean z, double d, double d2, String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(state, (i & 2) != 0 ? false : z, (i & 4) != 0 ? 0.0d : d, (i & 8) == 0 ? d2 : 0.0d, (i & 16) != 0 ? "" : str);
        }

        public final double getDistanceM() {
            return this.distanceM;
        }

        public final boolean getRight() {
            return this.right;
        }

        public final State getState() {
            return this.state;
        }

        public final String getWhy() {
            return this.why;
        }

        public final double getWindowM() {
            return this.windowM;
        }
    }

    public final double interp(double x, double[] xs, double[] ys) {
        Intrinsics.checkNotNullParameter(xs, "xs");
        Intrinsics.checkNotNullParameter(ys, "ys");
        if (x <= xs[0]) {
            return ys[0];
        }
        int length = xs.length;
        for (int i = 1; i < length; i++) {
            if (x <= xs[i]) {
                return ys[i - 1] + (((ys[i] - ys[i - 1]) * (x - xs[i - 1])) / (xs[i] - xs[i - 1]));
            }
        }
        return ArraysKt.last(ys);
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x0092, code lost:
    
        if (r4.equals("right") != false) goto L36;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x00b0, code lost:
    
        r5 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x00b1, code lost:
    
        r7 = r37.optDouble("maneuverDistance", Double.NaN);
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x00bd, code lost:
    
        if (java.lang.Double.isNaN(r7) == false) goto L41;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x00d3, code lost:
    
        r15 = r37.optInt("sameSideLaneCount", 0);
        r20 = r37.optInt("laneCount", 0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x00df, code lost:
    
        if (r15 <= 1) goto L47;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x00e1, code lost:
    
        if (r20 <= 0) goto L46;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x00e9, code lost:
    
        if (kotlin.ranges.RangesKt.coerceAtLeast(r20 - r15, 0) > 2) goto L47;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x00eb, code lost:
    
        r9 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x00ee, code lost:
    
        r10 = interp(r38, com.starpilot.starview.NudgeLogic.KEEP_V, com.starpilot.starview.NudgeLogic.KEEP_D);
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x00fc, code lost:
    
        if (r9 == false) goto L51;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x00fe, code lost:
    
        r16 = 0.6d;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0106, code lost:
    
        r23 = r10 * r16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x0108, code lost:
    
        if (r9 == false) goto L60;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x0110, code lost:
    
        if (r37.optBoolean("activeLaneAtRoadEdge", false) == false) goto L60;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x0118, code lost:
    
        if (r37.optBoolean("hasSharedSameSideLane", false) == false) goto L60;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x0136, code lost:
    
        if (r7 <= (2.5d * r23)) goto L64;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x0150, code lost:
    
        if (r7 <= r23) goto L68;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x016a, code lost:
    
        if (r5 == false) goto L72;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x016c, code lost:
    
        if (r43 == false) goto L72;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x016e, code lost:
    
        r10 = "car in your right blind spot";
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x019c, code lost:
    
        r18 = r10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x01a8, code lost:
    
        if (r18.length() != 0) goto L93;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x01aa, code lost:
    
        r2 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x01ab, code lost:
    
        if (r2 == false) goto L95;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x01ad, code lost:
    
        r2 = com.starpilot.starview.NudgeLogic.State.READY;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x01b0, code lost:
    
        r2 = com.starpilot.starview.NudgeLogic.State.WAIT;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x0171, code lost:
    
        if (r5 != false) goto L75;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x0173, code lost:
    
        if (r42 == false) goto L75;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x0175, code lost:
    
        r10 = "car in your left blind spot";
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x0178, code lost:
    
        if (r5 == false) goto L78;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x017a, code lost:
    
        if (r40 == false) goto L78;
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x017c, code lost:
    
        r10 = "left blinker is on";
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x017f, code lost:
    
        if (r5 != false) goto L81;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x0181, code lost:
    
        if (r41 == false) goto L81;
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x0183, code lost:
    
        r10 = "right blinker is on";
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x018a, code lost:
    
        if (r48 <= 0.0d) goto L89;
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x018c, code lost:
    
        if (r5 == false) goto L85;
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x018e, code lost:
    
        r10 = r46;
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x0195, code lost:
    
        if (r10 >= r48) goto L89;
     */
    /* JADX WARN: Code restructure failed: missing block: B:85:0x0197, code lost:
    
        r10 = "no lane seen on that side yet";
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x0191, code lost:
    
        r10 = r44;
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x019a, code lost:
    
        r10 = "";
     */
    /* JADX WARN: Code restructure failed: missing block: B:88:0x0104, code lost:
    
        r16 = 1.0d;
     */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x00ed, code lost:
    
        r9 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:91:0x009d, code lost:
    
        if (r4.equals("left") == false) goto L25;
     */
    /* JADX WARN: Code restructure failed: missing block: B:92:0x00a7, code lost:
    
        r5 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:94:0x00a4, code lost:
    
        if (r4.equals("slightLeft") == false) goto L25;
     */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x00ad, code lost:
    
        if (r4.equals("slightRight") == false) goto L25;
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
    public final Result evaluate(JSONObject nav, double vEgo, boolean leftBlinker, boolean rightBlinker, boolean leftBlindspot, boolean rightBlindspot, double laneWidthLeft, double laneWidthRight, double minLaneWidthM) {
        if (nav != null) {
            boolean z = false;
            if (nav.optBoolean("valid", false)) {
                String type = nav.optString("maneuverType");
                String mod = nav.optString("maneuverModifier");
                if ((Intrinsics.areEqual(type, "off ramp") || Intrinsics.areEqual(type, "fork")) && SetsKt.setOf((Object[]) new String[]{"slightLeft", "left", "sharpLeft", "slightRight", "right", "sharpRight"}).contains(mod)) {
                    String lane = nav.optString("activeLaneDirection");
                    if (lane != null) {
                        switch (lane.hashCode()) {
                            case -711874727:
                                break;
                            case -438788310:
                                break;
                            case 3317767:
                                break;
                            case 108511772:
                                break;
                        }
                        return new Result(State.NONE, false, 0.0d, 0.0d, null, 30, null);
                    }
                    return new Result(State.NONE, false, 0.0d, 0.0d, null, 30, null);
                }
                return new Result(State.NONE, false, 0.0d, 0.0d, null, 30, null);
            }
        }
        return new Result(State.NONE, false, 0.0d, 0.0d, null, 30, null);
    }
}
