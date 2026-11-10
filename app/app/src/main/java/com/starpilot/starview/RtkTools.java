package com.starpilot.starview;

import com.starpilot.starview.RtkCalibration;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TimeZone;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.ranges.RangesKt;
import kotlin.text.MatchResult;
import kotlin.text.Regex;
import kotlin.text.StringsKt;

/* compiled from: RtkCore.kt */
public final class RtkTools {
    public static final RtkTools INSTANCE = new RtkTools();
    private static final Map<String, String> KEY_NAMES = MapsKt.mapOf(TuplesKt.to("fusion", "Fusion"), TuplesKt.to("imuInit", "Motion sensors"), TuplesKt.to("insInit", "Position"), TuplesKt.to("mountInit", "Mount alignment"), TuplesKt.to("mount", "Mount stage"), TuplesKt.to("speedInit", "Car speed"), TuplesKt.to("speedCalib", "Speed calibration"), TuplesKt.to("fixType", "Fix"), TuplesKt.to("carHeading", "Car heading"), TuplesKt.to("tool", "Tool"));
    private static final List<Tool> TOOLS;

    /* compiled from: RtkCore.kt */
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[State.values().length];
            try {
                iArr[State.DONE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* compiled from: RtkCore.kt */
    public enum State {
        DONE,
        NOW,
        WAITING,
        OFF;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries($VALUES);

        public static EnumEntries<State> getEntries() {
            return $ENTRIES;
        }
    }

    private RtkTools() {
    }

    /* compiled from: RtkCore.kt */
    public static final /* data */ class Step {
        private final State state;
        private final String title;
        private final String words;

        public static /* synthetic */ Step copy$default(Step step, String str, State state, String str2, int i, Object obj) {
            if ((i & 1) != 0) {
                str = step.title;
            }
            if ((i & 2) != 0) {
                state = step.state;
            }
            if ((i & 4) != 0) {
                str2 = step.words;
            }
            return step.copy(str, state, str2);
        }

        /* renamed from: component1, reason: from getter */
        public final String getTitle() {
            return this.title;
        }

        /* renamed from: component2, reason: from getter */
        public final State getState() {
            return this.state;
        }

        /* renamed from: component3, reason: from getter */
        public final String getWords() {
            return this.words;
        }

        public final Step copy(String title, State state, String words) {
            Intrinsics.checkNotNullParameter(title, "title");
            Intrinsics.checkNotNullParameter(state, "state");
            Intrinsics.checkNotNullParameter(words, "words");
            return new Step(title, state, words);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Step)) {
                return false;
            }
            Step step = (Step) other;
            return Intrinsics.areEqual(this.title, step.title) && this.state == step.state && Intrinsics.areEqual(this.words, step.words);
        }

        public int hashCode() {
            return (((this.title.hashCode() * 31) + this.state.hashCode()) * 31) + this.words.hashCode();
        }

        public String toString() {
            return "Step(title=" + this.title + ", state=" + this.state + ", words=" + this.words + ")";
        }

        public Step(String title, State state, String words) {
            Intrinsics.checkNotNullParameter(title, "title");
            Intrinsics.checkNotNullParameter(state, "state");
            Intrinsics.checkNotNullParameter(words, "words");
            this.title = title;
            this.state = state;
            this.words = words;
        }

        public final State getState() {
            return this.state;
        }

        public final String getTitle() {
            return this.title;
        }

        public final String getWords() {
            return this.words;
        }
    }

    /* compiled from: RtkCore.kt */
    public static final /* data */ class Tool {
        private final boolean danger;
        private final String label;
        private final String op;
        private final boolean stoppedOnly;
        private final String what;

        public static /* synthetic */ Tool copy$default(Tool tool, String str, String str2, String str3, boolean z, boolean z2, int i, Object obj) {
            if ((i & 1) != 0) {
                str = tool.op;
            }
            if ((i & 2) != 0) {
                str2 = tool.label;
            }
            String str4 = str2;
            if ((i & 4) != 0) {
                str3 = tool.what;
            }
            String str5 = str3;
            if ((i & 8) != 0) {
                z = tool.stoppedOnly;
            }
            boolean z3 = z;
            if ((i & 16) != 0) {
                z2 = tool.danger;
            }
            return tool.copy(str, str4, str5, z3, z2);
        }

        /* renamed from: component1, reason: from getter */
        public final String getOp() {
            return this.op;
        }

        /* renamed from: component2, reason: from getter */
        public final String getLabel() {
            return this.label;
        }

        /* renamed from: component3, reason: from getter */
        public final String getWhat() {
            return this.what;
        }

        /* renamed from: component4, reason: from getter */
        public final boolean getStoppedOnly() {
            return this.stoppedOnly;
        }

        /* renamed from: component5, reason: from getter */
        public final boolean getDanger() {
            return this.danger;
        }

        public final Tool copy(String op, String label, String what, boolean stoppedOnly, boolean danger) {
            Intrinsics.checkNotNullParameter(op, "op");
            Intrinsics.checkNotNullParameter(label, "label");
            Intrinsics.checkNotNullParameter(what, "what");
            return new Tool(op, label, what, stoppedOnly, danger);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Tool)) {
                return false;
            }
            Tool tool = (Tool) other;
            return Intrinsics.areEqual(this.op, tool.op) && Intrinsics.areEqual(this.label, tool.label) && Intrinsics.areEqual(this.what, tool.what) && this.stoppedOnly == tool.stoppedOnly && this.danger == tool.danger;
        }

        public int hashCode() {
            return (((((((this.op.hashCode() * 31) + this.label.hashCode()) * 31) + this.what.hashCode()) * 31) + Boolean.hashCode(this.stoppedOnly)) * 31) + Boolean.hashCode(this.danger);
        }

        public String toString() {
            return "Tool(op=" + this.op + ", label=" + this.label + ", what=" + this.what + ", stoppedOnly=" + this.stoppedOnly + ", danger=" + this.danger + ")";
        }

        public Tool(String op, String label, String what, boolean stoppedOnly, boolean danger) {
            Intrinsics.checkNotNullParameter(op, "op");
            Intrinsics.checkNotNullParameter(label, "label");
            Intrinsics.checkNotNullParameter(what, "what");
            this.op = op;
            this.label = label;
            this.what = what;
            this.stoppedOnly = stoppedOnly;
            this.danger = danger;
        }

        public /* synthetic */ Tool(String str, String str2, String str3, boolean z, boolean z2, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, str2, str3, (i & 8) != 0 ? false : z, (i & 16) != 0 ? false : z2);
        }

        public final boolean getDanger() {
            return this.danger;
        }

        public final String getLabel() {
            return this.label;
        }

        public final String getOp() {
            return this.op;
        }

        public final boolean getStoppedOnly() {
            return this.stoppedOnly;
        }

        public final String getWhat() {
            return this.what;
        }
    }

    static {
        boolean z = false;
        TOOLS = CollectionsKt.listOf((Object[]) new Tool[]{new Tool("realign", "Recalibrate mount", "Forgets how the board sits in the car and learns it again. Use it after moving or re-taping the board, then drive with some turns (about 10 turns of 90° or more at 20+ mph).", false, false, 24, null), new Tool("hot", "Restart", "Restarts satellite tracking and keeps everything learned. The fix is back in a few seconds.", false, false, 24, null), new Tool("warm", "Warm restart", "Restarts and forgets the satellites' orbits (keeps the position and time). The fix is back in about 30 seconds.", false, false, 24, null), new Tool("cold", "Cold restart", "Restarts from nothing: no satellites, position or time. The fix takes a minute or two (longer under a roof). Only while stopped.", true, z, 16, null), new Tool("save", "Save settings", "Writes StarView's receiver settings (10 Hz, automotive, sensor fusion and mount learning on) to the receiver's own flash, so they stay when it's unplugged.", z, false, 24, null), new Tool("factory", "Factory reset", "Erases every setting on the receiver, then writes StarView's back. Its calibration starts over. Only while stopped.", true, true)});
    }

    public final List<Tool> getTOOLS() {
        return TOOLS;
    }

    public final String label(String op) {
        Object element$iv;
        String label;
        Intrinsics.checkNotNullParameter(op, "op");
        Iterable $this$firstOrNull$iv = TOOLS;
        Iterator it = $this$firstOrNull$iv.iterator();
        while (true) {
            if (it.hasNext()) {
                element$iv = it.next();
                Tool it2 = (Tool) element$iv;
                if (Intrinsics.areEqual(it2.getOp(), op)) {
                    break;
                }
            } else {
                element$iv = null;
                break;
            }
        }
        Tool tool = (Tool) element$iv;
        if (tool == null || (label = tool.getLabel()) == null) {
            return Intrinsics.areEqual(op, "speed_on") ? "Speed from the car on" : Intrinsics.areEqual(op, "speed_off") ? "Speed from the car off" : op;
        }
        return label;
    }

    /* VF: from Vineflower (jadx: ) */
    public final String lastAction(RtkStatus var1) {
        if (var1 != null) {
            int var4;
            if (var1.getActionOp().length() == 0) {
                var4 = 1;
            } else {
                var4 = 0;
            }

            if (!var4) {
                String var11;
                label40: {
                    var11 = var1.getActionState();
                    switch (var11.hashCode()) {
                        case 3089282:
                            if (var11.equals("done")) {
                                var11 = "done";
                                break label40;
                            }
                            break;
                        case 3526552:
                            if (var11.equals("sent")) {
                                var11 = "sent, waiting for the receiver";
                                break label40;
                            }
                            break;
                        case 144428797:
                            if (var11.equals("no answer")) {
                                var11 = "no answer from the receiver";
                                break label40;
                            }
                            break;
                        case 1085547216:
                            if (var11.equals("refused")) {
                                var11 = "the receiver refused it";
                                break label40;
                            }
                    }

                    var11 = var1.getActionState();
                }

                double var2 = var1.getActionAgeS();
                StringBuilder var6;
                String var7;
                if (var2 < 60.0) {
                    var4 = (int)var2;
                    var6 = new StringBuilder().append(var4);
                    var7 = " s ago";
                } else {
                    var4 = (int)(var2 / 60);
                    var6 = new StringBuilder().append(var4);
                    var7 = " min ago";
                }

                String var12 = var6.append(var7).toString();
                String var8 = this.label(var1.getActionOp());
                return var8 + ": " + var11 + ", " + var12;
            }
        }

        return "";
    }

    public final String firmware(RtkStatus k) {
        MatchResult.Destructured destructured;
        Intrinsics.checkNotNullParameter(k, "k");
        if (k.getFirmware().length() == 0) {
            return "not reported yet";
        }
        Iterable $this$filter$iv = CollectionsKt.listOf((Object[]) new String[]{k.getModule(), k.getFirmware()});
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            String it = (String) element$iv$iv;
            if (it.length() > 0) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        String name = CollectionsKt.joinToString((List) destination$iv$iv, ", ", "", "", -1, "...", null);
        Integer num = null;
        MatchResult find$default = (new Regex("HPS (\\d+)\\.(\\d+)")).find(k.getFirmware(), 0);
        if (find$default != null && (destructured = find$default.getDestructured()) != null) {
            String a = destructured.getMatch().getGroupValues().get(1);
            String b = destructured.getMatch().getGroupValues().get(2);
            num = Integer.valueOf((Integer.parseInt(a) * 100) + Integer.parseInt(b));
        }
        Integer v = num;
        return (v == null || v.intValue() >= 120) ? name : name + ": too old, its IMU may never start (flash HPS 1.30 with u-center)";
    }

    /* VF: from Vineflower (jadx: ) */
    private final RtkTools.State st(String var1) {
        switch (var1.hashCode()) {
            case -400079795:
                if (var1.equals("initializing")) {
                    return RtkTools.State.NOW;
                }
                break;
            case 109935:
                if (var1.equals("off")) {
                    return RtkTools.State.OFF;
                }
                break;
            case 3089282:
                if (var1.equals("done")) {
                    return RtkTools.State.DONE;
                }
        }

        return RtkTools.State.WAITING;
    }

    public final List<Step> steps(RtkStatus k) {
        State it;
        String str;
        String str2;
        Intrinsics.checkNotNullParameter(k, "k");
        State imu = st(k.getImuInit());
        State it2 = st(k.getInsInit());
        if (it2 == State.OFF && imu != State.DONE) {
            it2 = State.WAITING;
        }
        boolean mountDone = (Intrinsics.areEqual(k.getMountInit(), "done") && Intrinsics.areEqual(k.getMount(), "fine")) || k.getMountKept();
        if (mountDone) {
            it = State.DONE;
        } else if (!Intrinsics.areEqual(k.getMountInit(), "off") || it2 == State.DONE) {
            it = st(k.getMountInit());
            if (it == State.OFF || it == State.DONE) {
                it = State.NOW;
            }
        } else {
            it = State.WAITING;
        }
        State speed = !k.getSpeedFeedOn() ? State.OFF : Intrinsics.areEqual(k.getSpeedInit(), "done") ? State.DONE : State.NOW;
        Step[] stepArr = new Step[4];
        stepArr[0] = new Step("Motion sensors", imu, WhenMappings.$EnumSwitchMapping$0[imu.ordinal()] == 1 ? "Gyro and accelerometer running" : "Engine on and the car standing still for about 3 minutes");
        stepArr[1] = new Step("Position", it2, it2 == State.DONE ? "Sensors joined to the GNSS fix" : !k.getHasFix() ? "Needs a GNSS fix first: open sky" : it != State.DONE ? "Starts once the mount alignment is far enough" : "Drive a little under open sky");
        if (k.getMountKept()) {
            StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
            str = String.format(Locale.US, "Saved in the receiver from an earlier drive (heading %.1f°) - not learned again after a power cut", Arrays.copyOf(new Object[]{Double.valueOf(k.getMountKeptYaw())}, 1));
            Intrinsics.checkNotNullExpressionValue(str, "format(...)");
        } else {
            str = k.getMountKeeping() ? "Learned (fine) - saving it in the receiver" : it == State.DONE ? "Learned (fine) - saved in the receiver after 2 minutes" : Intrinsics.areEqual(k.getMount(), "coarse") ? "Coarse so far: keep driving, with turns" : it == State.WAITING ? "After the position step" : "Drive about 10 turns of 90° or more at 20+ mph; stops and straight stretches help too";
        }
        stepArr[2] = new Step("Mount alignment", it, str);
        if (speed == State.OFF) {
            str2 = "Off: switch on \"Speed from the car\" below";
        } else if (speed == State.DONE) {
            str2 = "Calibrated" + (k.getSpeedFeedHz() > 0.0d ? ", " + Math.round(k.getSpeedFeedHz()) + " a second" : "");
        } else {
            str2 = k.getSpeedFeedHz() <= 0.0d ? "Waiting for the car's speed (it comes with the road view's data)" : "Learning, " + Math.round(k.getSpeedFeedHz()) + " a second: just drive";
        }
        stepArr[3] = new Step("Car speed", speed, str2);
        return CollectionsKt.listOf((Object[]) stepArr);
    }

    /* VF: from Vineflower (jadx: ) */
    public final float stepProgress(RtkStatus var1, String var2) {
        Intrinsics.checkNotNullParameter(var1, "k");
        Intrinsics.checkNotNullParameter(var2, "title");
        int var5 = var2.hashCode();
        float var4 = 0.5F;
        switch (var5) {
            case -1332793732:
                if (var2.equals("Mount alignment")) {
                    if (!var1.getMountKept() && (!Intrinsics.areEqual(var1.getMountInit(), "done") || !Intrinsics.areEqual(var1.getMount(), "fine"))) {
                        if (Intrinsics.areEqual(var1.getMount(), "coarse")) {
                            return 0.75F;
                        } else {
                            if (!Intrinsics.areEqual(var1.getMount(), "learning heading")) {
                                float var17;
                                if (Intrinsics.areEqual(var1.getMount(), "learning roll/pitch")) {
                                    var17 = 0.25F;
                                } else {
                                    var17 = 0.0F;
                                }

                                return var17;
                            }

                            return var4;
                        }
                    } else {
                        return 1.0F;
                    }
                }
                break;
            case -169507621:
                if (var2.equals("Car speed")) {
                    if (!var1.getSpeedFeedOn()) {
                        return 0.0F;
                    } else if (Intrinsics.areEqual(var1.getSpeedInit(), "done")) {
                        return 1.0F;
                    } else {
                        float var16;
                        if (Intrinsics.areEqual(var1.getSpeedInit(), "initializing")) {
                            var16 = var4;
                        } else {
                            var16 = 0.1F;
                        }

                        return var16;
                    }
                }
                break;
            case -62835281:
                if (var2.equals("Motion sensors")) {
                    String var6 = var1.getImuInit();
                    if (Intrinsics.areEqual(var6, "done")) {
                        return 1.0F;
                    } else {
                        float var13;
                        if (Intrinsics.areEqual(var6, "initializing")) {
                            var13 = var4;
                        } else {
                            var13 = 0.0F;
                        }

                        return var13;
                    }
                }
                break;
            case 812449097:
                if (var2.equals("Position")) {
                    var2 = var1.getInsInit();
                    if (Intrinsics.areEqual(var2, "done")) {
                        return 1.0F;
                    } else {
                        float var8;
                        if (Intrinsics.areEqual(var2, "initializing")) {
                            var8 = var4;
                            if (!Intrinsics.areEqual(var1.getMount(), "coarse")) {
                                if (Intrinsics.areEqual(var1.getMount(), "fine")) {
                                    var8 = var4;
                                } else {
                                    var8 = 0.15F;
                                }

                                return var8;
                            }
                        } else {
                            var8 = 0.0F;
                        }

                        return var8;
                    }
                }
        }

        return 0.0F;
    }

    public final float overall(RtkStatus k) {
        Intrinsics.checkNotNullParameter(k, "k");
        if (Intrinsics.areEqual(k.getFusion(), "on")) {
            return 1.0f;
        }
        return RangesKt.coerceIn((stepProgress(k, "Motion sensors") * 0.15f) + (stepProgress(k, "Mount alignment") * 0.45f) + (stepProgress(k, "Position") * 0.25f), 0.0f, 0.95f);
    }

    /* compiled from: RtkCore.kt */
    public static final /* data */ class Need {
        private final boolean met;
        private final float progress;
        private final String title;
        private final String words;

        public static /* synthetic */ Need copy$default(Need need, String str, String str2, boolean z, float f, int i, Object obj) {
            if ((i & 1) != 0) {
                str = need.title;
            }
            if ((i & 2) != 0) {
                str2 = need.words;
            }
            if ((i & 4) != 0) {
                z = need.met;
            }
            if ((i & 8) != 0) {
                f = need.progress;
            }
            return need.copy(str, str2, z, f);
        }

        /* renamed from: component1, reason: from getter */
        public final String getTitle() {
            return this.title;
        }

        /* renamed from: component2, reason: from getter */
        public final String getWords() {
            return this.words;
        }

        /* renamed from: component3, reason: from getter */
        public final boolean getMet() {
            return this.met;
        }

        /* renamed from: component4, reason: from getter */
        public final float getProgress() {
            return this.progress;
        }

        public final Need copy(String title, String words, boolean met, float progress) {
            Intrinsics.checkNotNullParameter(title, "title");
            Intrinsics.checkNotNullParameter(words, "words");
            return new Need(title, words, met, progress);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Need)) {
                return false;
            }
            Need need = (Need) other;
            return Intrinsics.areEqual(this.title, need.title) && Intrinsics.areEqual(this.words, need.words) && this.met == need.met && Float.compare(this.progress, need.progress) == 0;
        }

        public int hashCode() {
            return (((((this.title.hashCode() * 31) + this.words.hashCode()) * 31) + Boolean.hashCode(this.met)) * 31) + Float.hashCode(this.progress);
        }

        public String toString() {
            return "Need(title=" + this.title + ", words=" + this.words + ", met=" + this.met + ", progress=" + this.progress + ")";
        }

        public Need(String title, String words, boolean met, float progress) {
            Intrinsics.checkNotNullParameter(title, "title");
            Intrinsics.checkNotNullParameter(words, "words");
            this.title = title;
            this.words = words;
            this.met = met;
            this.progress = progress;
        }

        public final boolean getMet() {
            return this.met;
        }

        public final float getProgress() {
            return this.progress;
        }

        public final String getTitle() {
            return this.title;
        }

        public final String getWords() {
            return this.words;
        }
    }

    public final List<Need> needs(RtkStatus k) {
        String str;
        Intrinsics.checkNotNullParameter(k, "k");
        RtkCalibration c = k.getCalibration();
        if (c == null) {
            return CollectionsKt.emptyList();
        }
        int turns = c.getLeft() + c.getRight();
        int fast = c.getLeftFast() + c.getRightFast();
        boolean bothWays = c.getLeft() > 0 && c.getRight() > 0;
        Need[] needArr = new Need[3];
        if (c.getStillNowS() > 0) {
            str = "Stopped now " + c.getStillNowS() + " s (longest " + c.getLongestStillS() + " s, 20 s is enough)";
        } else {
            str = "Longest stop " + c.getLongestStillS() + " s (20 s is enough)";
        }
        needArr[0] = new Need("A stop with the engine on", str, c.getLongestStillS() >= 20, RangesKt.coerceAtMost(c.getLongestStillS() / 20.0f, 1.0f));
        needArr[1] = new Need("Turns of 90°", turns + " so far: " + c.getLeft() + " left, " + c.getRight() + " right" + (turns > 0 ? "; " + fast + " at 20+ mph" : "") + " (about 10 at 20+ mph, both ways; slow corners count half here)", bothWays && ((float) fast) + (((float) (turns - fast)) * 0.5f) >= 10.0f, RangesKt.coerceAtMost((fast + ((turns - fast) * 0.5f)) / 10.0f, 1.0f));
        needArr[2] = new Need("Driving at 20+ mph", duration(c.getFastS()) + " so far (5 min is plenty)", c.getFastS() >= 300, RangesKt.coerceAtMost(c.getFastS() / 300.0f, 1.0f));
        return CollectionsKt.listOf((Object[]) needArr);
    }

    public final String duration(int s) {
        StringBuilder append;
        StringBuilder append2;
        if (s < 60) {
            append2 = new StringBuilder().append(s);
        } else {
            if (s >= 3600) {
                append = new StringBuilder().append(s / 3600).append(" h ").append((s % 3600) / 60).append(" min");
                return append.toString();
            }
            append2 = new StringBuilder().append(s / 60).append(" min ").append(s % 60);
        }
        append = append2.append(" s");
        return append.toString();
    }

    public static /* synthetic */ List timeline$default(RtkTools rtkTools, RtkStatus rtkStatus, long j, TimeZone timeZone, int i, Object obj) {
        if ((i & 4) != 0) {
            timeZone = TimeZone.getDefault();
        }
        return rtkTools.timeline(rtkStatus, j, timeZone);
    }

    public final List<String> timeline(RtkStatus k, long nowMs, TimeZone zone) {
        String str;
        Intrinsics.checkNotNullParameter(k, "k");
        Intrinsics.checkNotNullParameter(zone, "zone");
        RtkCalibration c = k.getCalibration();
        if (c == null) {
            return CollectionsKt.emptyList();
        }
        SimpleDateFormat f = new SimpleDateFormat("HH:mm:ss", Locale.US);
        f.setTimeZone(zone);
        Iterable $this$map$iv = CollectionsKt.asReversed(c.getEvents());
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        for (Object item$iv$iv : $this$map$iv) {
            RtkCalibration.Event e = (RtkCalibration.Event) item$iv$iv;
            RtkCalibration c2 = c;
            String at = f.format(new Date(nowMs - ((long) (e.getAgoS() * 1000))));
            String name = KEY_NAMES.get(e.getKey());
            if (name == null) {
                name = e.getKey();
            }
            if (Intrinsics.areEqual(e.getKey(), "tool") && Intrinsics.areEqual(e.getTo(), "speed_on")) {
                str = "Speed from the car switched on";
            } else if (Intrinsics.areEqual(e.getKey(), "tool") && Intrinsics.areEqual(e.getTo(), "speed_off")) {
                str = "Speed from the car switched off";
            } else if (Intrinsics.areEqual(e.getKey(), "tool")) {
                RtkTools rtkTools = INSTANCE;
                String to = e.getTo();
                if (to == null) {
                    to = "";
                }
                str = rtkTools.label(to) + " used";
            } else {
                String from = e.getFrom();
                if (from == null) {
                    from = "?";
                }
                String to2 = e.getTo();
                str = name + ": " + from + " → " + (to2 != null ? to2 : "?");
            }
            destination$iv$iv.add(at + "  " + str);
            c = c2;
        }
        return (List) destination$iv$iv;
    }

    public final String mountAngles(RtkStatus k) {
        Intrinsics.checkNotNullParameter(k, "k");
        double mountYawDeg = k.getMountYawDeg();
        boolean z = false;
        if (!((Double.isInfinite(mountYawDeg) || Double.isNaN(mountYawDeg)) ? false : true)) {
            return "not reported yet";
        }
        String format = String.format(Locale.US, "yaw %.1f°", Arrays.copyOf(new Object[]{Double.valueOf(k.getMountYawDeg())}, 1));
        Intrinsics.checkNotNullExpressionValue(format, "format(...)");
        List parts = CollectionsKt.mutableListOf(format);
        double mountPitchDeg = k.getMountPitchDeg();
        if ((Double.isInfinite(mountPitchDeg) || Double.isNaN(mountPitchDeg)) ? false : true) {
            String format2 = String.format(Locale.US, "pitch %.1f°", Arrays.copyOf(new Object[]{Double.valueOf(k.getMountPitchDeg())}, 1));
            Intrinsics.checkNotNullExpressionValue(format2, "format(...)");
            parts.add(format2);
        }
        double mountRollDeg = k.getMountRollDeg();
        if (!Double.isInfinite(mountRollDeg) && !Double.isNaN(mountRollDeg)) {
            z = true;
        }
        if (z) {
            String format3 = String.format(Locale.US, "roll %.1f°", Arrays.copyOf(new Object[]{Double.valueOf(k.getMountRollDeg())}, 1));
            Intrinsics.checkNotNullExpressionValue(format3, "format(...)");
            parts.add(format3);
        }
        return CollectionsKt.joinToString(parts, ", ", "", "", -1, "...", null) + (!k.getMountErrors().isEmpty() ? "  (errors: " + CollectionsKt.joinToString(k.getMountErrors(), ", ", "", "", -1, "...", null) + ")" : "");
    }

    public final String summary(RtkStatus k) {
        Object element$iv;
        Intrinsics.checkNotNullParameter(k, "k");
        if (!k.getConnected()) {
            return "Receiver unplugged";
        }
        if (k.getFusion().length() == 0) {
            return "No sensor report yet";
        }
        Iterable $this$firstOrNull$iv = steps(k);
        Iterator it = $this$firstOrNull$iv.iterator();
        while (true) {
            if (it.hasNext()) {
                element$iv = it.next();
                Step it2 = (Step) element$iv;
                if (((it2.getState() == State.NOW || it2.getState() == State.WAITING) ? 1 : null) != null) {
                    break;
                }
            } else {
                element$iv = null;
                break;
            }
        }
        Step next = (Step) element$iv;
        if (Intrinsics.areEqual(k.getFusion(), "on") && next == null) {
            return "Sensor fusion on: keeps the position in tunnels and garages";
        }
        if (Intrinsics.areEqual(k.getFusion(), "on")) {
            Intrinsics.checkNotNull(next);
            String lowerCase = next.getTitle().toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
            return "Sensor fusion on. Next: " + lowerCase;
        }
        if (Intrinsics.areEqual(k.getFusion(), "disabled")) {
            return "Sensor fusion is switched off on the receiver: Factory reset turns it back on";
        }
        if (Intrinsics.areEqual(k.getFusion(), "suspended")) {
            return "Sensor fusion paused by the receiver (bad sensor data)";
        }
        if (next == null) {
            return "Starting";
        }
        String lowerCase2 = next.getTitle().toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(lowerCase2, "toLowerCase(...)");
        return "Starting: " + lowerCase2;
    }

    public final String sensors(List<RtkSensor> list) {
        String g;
        Object answer$iv;
        Intrinsics.checkNotNullParameter(list, "list");
        if (list.isEmpty()) {
            return "none reported";
        }
        LinkedHashMap groups = new LinkedHashMap();
        for (RtkSensor s : list) {
            if (!Intrinsics.areEqual(s.getType(), "gyro temperature")) {
                if (StringsKt.startsWith(s.getType(), "gyro", false)) {
                    g = "Gyro";
                } else if (StringsKt.startsWith(s.getType(), "accel", false)) {
                    g = "Accel";
                } else if (Intrinsics.areEqual(s.getType(), "speed")) {
                    g = "Speed";
                } else {
                    g = s.getType();
                    if (g.length() > 0) {
                        StringBuilder sb = new StringBuilder();
                        char it = g.charAt(0);
                        String valueOf = String.valueOf(it);
                        Intrinsics.checkNotNull(valueOf, "null cannot be cast to non-null type java.lang.String");
                        String upperCase = valueOf.toUpperCase(Locale.ROOT);
                        Intrinsics.checkNotNullExpressionValue(upperCase, "toUpperCase(...)");
                        StringBuilder append = sb.append((Object) upperCase);
                        String substring = g.substring(1);
                        Intrinsics.checkNotNullExpressionValue(substring, "substring(...)");
                        g = append.append(substring).toString();
                    }
                }
                LinkedHashMap $this$getOrPut$iv = groups;
                Object value$iv = $this$getOrPut$iv.get(g);
                if (value$iv == null) {
                    answer$iv = new ArrayList();
                    $this$getOrPut$iv.put(g, answer$iv);
                } else {
                    answer$iv = value$iv;
                }
                ((List) answer$iv).add(s);
            }
        }
        Set entrySet = groups.entrySet();
        Intrinsics.checkNotNullExpressionValue(entrySet, "<get-entries>(...)");
        return CollectionsKt.joinToString(entrySet, "  ·  ", "", "", -1, "...", new Function1() { // from class: com.starpilot.starview.RtkTools$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                CharSequence sensors$lambda$16;
                sensors$lambda$16 = RtkTools.sensors$lambda$16((Map.Entry) obj);
                return sensors$lambda$16;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CharSequence sensors$lambda$16(Map.Entry entry) {
        Object minElem$iv;
        Intrinsics.checkNotNullParameter(entry, "<destruct>");
        Object key = entry.getKey();
        Intrinsics.checkNotNullExpressionValue(key, "component1(...)");
        String g = (String) key;
        Object value = entry.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "component2(...)");
        Iterable ss = (List) value;
        Iterable $this$mapNotNull$iv = ss;
        Collection destination$iv$iv = new ArrayList();
        Iterator it = $this$mapNotNull$iv.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Object element$iv$iv$iv = it.next();
            RtkSensor it2 = (RtkSensor) element$iv$iv$iv;
            String a = StringsKt.substringAfter(it2.getType(), ' ', "");
            Iterable $this$mapNotNull$iv2 = $this$mapNotNull$iv;
            if ((a.length() == 1 ? 1 : 0) == 0) {
                a = null;
            }
            if (a != null) {
                destination$iv$iv.add(a);
            }
            $this$mapNotNull$iv = $this$mapNotNull$iv2;
        }
        List axes = (List) destination$iv$iv;
        String name = !axes.isEmpty() ? g + " " + CollectionsKt.joinToString(axes, "/", "", "", -1, "...", null) : g;
        Iterable $this$minByOrNull$iv = ss;
        Iterator iterator$iv = $this$minByOrNull$iv.iterator();
        if (iterator$iv.hasNext()) {
            minElem$iv = iterator$iv.next();
            if (iterator$iv.hasNext()) {
                RtkSensor it3 = (RtkSensor) minElem$iv;
                String calib = it3.getCalib();
                int minValue$iv = Intrinsics.areEqual(calib, "not calibrated") ? 0 : Intrinsics.areEqual(calib, "calibrating") ? 1 : 2;
                while (true) {
                    Object e$iv = iterator$iv.next();
                    RtkSensor it4 = (RtkSensor) e$iv;
                    String g2 = g;
                    String g3 = it4.getCalib();
                    int v$iv = Intrinsics.areEqual(g3, "not calibrated") ? 0 : Intrinsics.areEqual(g3, "calibrating") ? 1 : 2;
                    if (minValue$iv > v$iv) {
                        minValue$iv = v$iv;
                        minElem$iv = e$iv;
                    }
                    if (!iterator$iv.hasNext()) {
                        break;
                    }
                    g = g2;
                }
            }
        } else {
            minElem$iv = null;
        }
        Intrinsics.checkNotNull(minElem$iv);
        RtkSensor worst = (RtkSensor) minElem$iv;
        Iterator it5 = ss.iterator();
        if (!it5.hasNext()) {
            throw new NoSuchElementException();
        }
        RtkSensor it6 = (RtkSensor) it5.next();
        int hz = it6.getHz();
        while (it5.hasNext()) {
            RtkSensor it7 = (RtkSensor) it5.next();
            int hz2 = it7.getHz();
            if (hz < hz2) {
                hz = hz2;
            }
        }
        int hz3 = hz;
        Iterable $this$flatMap$iv = ss;
        Collection destination$iv$iv2 = new ArrayList();
        for (Object element$iv$iv : $this$flatMap$iv) {
            RtkSensor it8 = (RtkSensor) element$iv$iv;
            Iterable list$iv$iv = it8.getFaults();
            CollectionsKt.addAll(destination$iv$iv2, list$iv$iv);
        }
        List faults = CollectionsKt.distinct((List) destination$iv$iv2);
        return name + " " + hz3 + " Hz: " + (!worst.getReady() ? "not ready" : worst.getCalib()) + (faults.isEmpty() ? "" : " (" + CollectionsKt.joinToString(faults, ", ", "", "", -1, "...", null) + ")");
    }
}
