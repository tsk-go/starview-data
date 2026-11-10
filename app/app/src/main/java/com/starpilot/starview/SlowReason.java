package com.starpilot.starview;

import java.util.Arrays;
import java.util.Locale;
import kotlin.Unit;
import kotlin.collections.ArrayDeque;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import kotlin.text.StringsKt;

/* compiled from: SlowReason.kt */
public final class SlowReason {
    public static final float BRAKE_A = -0.45f;
    public static final SlowReason INSTANCE = new SlowReason();

    /* compiled from: SlowReason.kt */
    public enum Kind {
        FCW,
        LEAD,
        MODEL,
        STOP,
        CURVE,
        LIMIT,
        TARGET,
        COAST,
        OTHER;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<Kind> getEntries() {
            return $ENTRIES;
        }
    }

    private SlowReason() {
    }

    /* compiled from: SlowReason.kt */
    public static final class In {
        private final float aTarget;
        private final float actAccel;
        private final float approachStop;
        private final boolean brakePressed;
        private final boolean cscControlling;
        private final float cscSpeed;
        private final boolean engaged;
        private final boolean experimental;
        private final boolean fcw;
        private final boolean forceCoast;
        private final boolean forcingStop;
        private final float forcingStopLength;
        private final boolean gasPressed;
        private final Lead lead0;
        private final Lead lead1;
        private final boolean metric;
        private final float planVCruise;
        private final boolean pulseGlideCoasting;
        private final boolean redLight;
        private final float setSpeed;
        private final float slcLimit;
        private final float slcOffset;
        private final String slcSource;
        private final String source;
        private final boolean stopSign;
        private final boolean trackingLead;
        private final float vEgo;

        public In(boolean engaged, float vEgo, float aTarget, float actAccel, boolean brakePressed, boolean gasPressed, String source, boolean fcw, Lead lead0, Lead lead1, boolean experimental, boolean forcingStop, float forcingStopLength, boolean redLight, boolean stopSign, float approachStop, boolean cscControlling, float cscSpeed, float slcLimit, float slcOffset, String slcSource, float planVCruise, float setSpeed, boolean pulseGlideCoasting, boolean forceCoast, boolean metric, boolean trackingLead) {
            Intrinsics.checkNotNullParameter(source, "source");
            Intrinsics.checkNotNullParameter(lead0, "lead0");
            Intrinsics.checkNotNullParameter(lead1, "lead1");
            Intrinsics.checkNotNullParameter(slcSource, "slcSource");
            this.engaged = engaged;
            this.vEgo = vEgo;
            this.aTarget = aTarget;
            this.actAccel = actAccel;
            this.brakePressed = brakePressed;
            this.gasPressed = gasPressed;
            this.source = source;
            this.fcw = fcw;
            this.lead0 = lead0;
            this.lead1 = lead1;
            this.experimental = experimental;
            this.forcingStop = forcingStop;
            this.forcingStopLength = forcingStopLength;
            this.redLight = redLight;
            this.stopSign = stopSign;
            this.approachStop = approachStop;
            this.cscControlling = cscControlling;
            this.cscSpeed = cscSpeed;
            this.slcLimit = slcLimit;
            this.slcOffset = slcOffset;
            this.slcSource = slcSource;
            this.planVCruise = planVCruise;
            this.setSpeed = setSpeed;
            this.pulseGlideCoasting = pulseGlideCoasting;
            this.forceCoast = forceCoast;
            this.metric = metric;
            this.trackingLead = trackingLead;
        }

        public /* synthetic */ In(boolean z, float f, float f2, float f3, boolean z2, boolean z3, String str, boolean z4, Lead lead, Lead lead2, boolean z5, boolean z6, float f4, boolean z7, boolean z8, float f5, boolean z9, float f6, float f7, float f8, String str2, float f9, float f10, boolean z10, boolean z11, boolean z12, boolean z13, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(z, f, f2, f3, z2, z3, str, z4, lead, lead2, z5, z6, f4, z7, z8, f5, z9, f6, f7, f8, str2, f9, f10, z10, z11, z12, (i & 67108864) != 0 ? false : z13);
        }

        public final float getATarget() {
            return this.aTarget;
        }

        public final float getActAccel() {
            return this.actAccel;
        }

        public final boolean getBrakePressed() {
            return this.brakePressed;
        }

        public final boolean getEngaged() {
            return this.engaged;
        }

        public final boolean getGasPressed() {
            return this.gasPressed;
        }

        public final float getVEgo() {
            return this.vEgo;
        }

        public final boolean getFcw() {
            return this.fcw;
        }

        public final String getSource() {
            return this.source;
        }

        public final Lead getLead0() {
            return this.lead0;
        }

        public final Lead getLead1() {
            return this.lead1;
        }

        public final boolean getExperimental() {
            return this.experimental;
        }

        public final boolean getForcingStop() {
            return this.forcingStop;
        }

        public final float getForcingStopLength() {
            return this.forcingStopLength;
        }

        public final boolean getRedLight() {
            return this.redLight;
        }

        public final boolean getStopSign() {
            return this.stopSign;
        }

        public final float getApproachStop() {
            return this.approachStop;
        }

        public final boolean getCscControlling() {
            return this.cscControlling;
        }

        public final float getCscSpeed() {
            return this.cscSpeed;
        }

        public final float getSlcLimit() {
            return this.slcLimit;
        }

        public final float getSlcOffset() {
            return this.slcOffset;
        }

        public final String getSlcSource() {
            return this.slcSource;
        }

        public final boolean getForceCoast() {
            return this.forceCoast;
        }

        public final float getPlanVCruise() {
            return this.planVCruise;
        }

        public final boolean getPulseGlideCoasting() {
            return this.pulseGlideCoasting;
        }

        public final float getSetSpeed() {
            return this.setSpeed;
        }

        public final boolean getMetric() {
            return this.metric;
        }

        public final boolean getTrackingLead() {
            return this.trackingLead;
        }
    }

    /* compiled from: SlowReason.kt */
    public static final class Out {
        private final String detail;
        private final Kind kind;
        private final String title;

        public Out(Kind kind, String title, String detail) {
            Intrinsics.checkNotNullParameter(kind, "kind");
            Intrinsics.checkNotNullParameter(title, "title");
            Intrinsics.checkNotNullParameter(detail, "detail");
            this.kind = kind;
            this.title = title;
            this.detail = detail;
        }

        public final String getDetail() {
            return this.detail;
        }

        public final Kind getKind() {
            return this.kind;
        }

        public final String getTitle() {
            return this.title;
        }
    }

    public final boolean braking(In i) {
        Intrinsics.checkNotNullParameter(i, "i");
        return i.getEngaged() && !i.getBrakePressed() && !i.getGasPressed() && i.getVEgo() > 0.5f && (i.getATarget() <= -0.45f || i.getActAccel() <= -0.45f);
    }

    private final String spd(float ms, boolean metric) {
        StringBuilder append;
        String str;
        if (metric) {
            append = new StringBuilder().append(MathKt.roundToInt(3.6f * ms));
            str = " km/h";
        } else {
            append = new StringBuilder().append(MathKt.roundToInt(2.23694f * ms));
            str = " mph";
        }
        return append.append(str).toString();
    }

    private final String dist(float m, boolean metric) {
        StringBuilder append;
        String str;
        if (metric) {
            append = new StringBuilder().append(MathKt.roundToInt(m));
            str = " m";
        } else {
            append = new StringBuilder().append(MathKt.roundToInt(3.28084f * m));
            str = " ft";
        }
        return append.append(str).toString();
    }

    public final Out explain(In i) {
        String it;
        Intrinsics.checkNotNullParameter(i, "i");
        String src = i.getSource().toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(src, "toLowerCase(...)");
        if (i.getFcw()) {
            return new Out(Kind.FCW, "Forward collision warning", leadText(i.getLead0(), i));
        }
        String str = null;
        String str2 = "Lead car (camera only)";
        if (StringsKt.startsWith(src, "lead", false)) {
            Lead l = (Intrinsics.areEqual(src, "lead1") || Intrinsics.areEqual(src, "lead2")) ? i.getLead1() : i.getLead0();
            boolean camOnly = !l.getRadar();
            if (camOnly && l.getModelProb() < 0.7f) {
                str2 = "Lead car? (camera only, " + MathKt.roundToInt(l.getModelProb() * 100) + "% sure)";
            } else if (!camOnly) {
                str2 = "Lead car (radar)";
            }
            String title = str2;
            return new Out(Kind.LEAD, title, leadText(l, i));
        }
        if (Intrinsics.areEqual(src, "e2e")) {
            if (i.getRedLight()) {
                str = "red light";
            } else if (i.getStopSign()) {
                str = "stop sign";
            } else if (i.getForcingStop()) {
                str = i.getForcingStopLength() > 0.0f ? "stop in " + dist(i.getForcingStopLength(), i.getMetric()) : "holding at a stop";
            } else if (i.getApproachStop() > 0.0f) {
                str = "stop ahead, " + dist(i.getApproachStop(), i.getMetric());
            }
            String what = str;
            Kind kind = Kind.MODEL;
            if (what == null || (it = "Model sees a " + what) == null) {
                it = "The model wants to slow down here - nothing else asked for it";
            }
            return new Out(kind, "Driving model (Experimental mode)", it);
        }
        Lead l0 = i.getLead0();
        if (l0.getStatus() && !i.getForcingStop() && ((i.getTrackingLead() || l0.getDRel() < Math.max(35.0f, i.getVEgo() * 3.0f)) && l0.getVRel() < 1.5f)) {
            if (!l0.getRadar() && l0.getModelProb() < 0.7f) {
                str2 = "Lead car? (camera only, " + MathKt.roundToInt(l0.getModelProb() * 100) + "% sure)";
            } else if (l0.getRadar()) {
                str2 = "Lead car (radar)";
            }
            String title2 = str2;
            return new Out(Kind.LEAD, title2, leadText(l0, i));
        }
        float target = i.getPlanVCruise();
        if (i.getForcingStop()) {
            return new Out(Kind.STOP, i.getRedLight() ? "Stopping for a red light" : i.getStopSign() ? "Stopping for a stop sign" : "Forced stop", i.getForcingStopLength() > 0.0f ? "Stop in " + dist(i.getForcingStopLength(), i.getMetric()) : "Holding at the stop");
        }
        if (i.getApproachStop() > 0.0f) {
            return new Out(Kind.STOP, "Stop ahead", "Detected stop " + dist(i.getApproachStop(), i.getMetric()) + " ahead");
        }
        if (i.getCscControlling() && i.getCscSpeed() > 0.0f) {
            return new Out(Kind.CURVE, "Curve ahead", "Curve speed controller: slowing to " + spd(i.getCscSpeed(), i.getMetric()));
        }
        if (i.getSlcLimit() > 0.0f && i.getSlcLimit() + i.getSlcOffset() < i.getVEgo() + 0.3f && Math.abs(target - (i.getSlcLimit() + i.getSlcOffset())) < 0.6f) {
            return new Out(Kind.LIMIT, "Speed limit " + spd(i.getSlcLimit(), i.getMetric()), "Target " + spd(i.getSlcLimit() + i.getSlcOffset(), i.getMetric()) + (StringsKt.isBlank(i.getSlcSource()) ? "" : " - from " + i.getSlcSource()));
        }
        if (i.getPulseGlideCoasting() || i.getForceCoast()) {
            return new Out(Kind.COAST, "Coasting", i.getForceCoast() ? "Coast is forced" : "Pulse & glide: glide phase");
        }
        if (target > 0.0f && target < i.getVEgo() - 0.3f) {
            return new Out(Kind.TARGET, "Target speed " + spd(target, i.getMetric()), (i.getSetSpeed() <= 0.0f || Math.abs(i.getSetSpeed() - target) <= 0.5f) ? "Your set speed" : "Below your set speed " + spd(i.getSetSpeed(), i.getMetric()));
        }
        return new Out(Kind.OTHER, "Planner (no reason given)", "Plan source: " + i.getSource() + (l0.getStatus() ? " · lead " + leadText(l0, i) : " · no lead") + " · target " + spd(Math.max(target, 0.0f), i.getMetric()) + (i.getExperimental() ? " · Experimental mode" : ""));
    }

    private final String leadText(Lead l, In i) {
        String gap;
        if (!l.getStatus()) {
            return "No lead reported";
        }
        float closing = -l.getVRel();
        if (i.getVEgo() > 1.0f) {
            String format = String.format("%.1f", Arrays.copyOf(new Object[]{Float.valueOf(l.getDRel() / i.getVEgo())}, 1));
            Intrinsics.checkNotNullExpressionValue(format, "format(...)");
            gap = " · " + format + " s gap";
        } else {
            gap = "";
        }
        String rel = closing > 0.5f ? "closing " + spd(closing, i.getMetric()) : closing < -0.5f ? "pulling away" : "same speed";
        return dist(l.getDRel(), i.getMetric()) + " ahead · " + rel + gap + " · lead " + spd(l.getVLead(), i.getMetric());
    }

    /* compiled from: SlowReason.kt */
    public static final class Event {
        private long endMs;
        private float minA;
        private Out reason;
        private final long startMs;
        private float vEnd;
        private final float vStart;

        public Event(long startMs, long endMs, float vStart, float vEnd, float minA, Out reason) {
            Intrinsics.checkNotNullParameter(reason, "reason");
            this.startMs = startMs;
            this.endMs = endMs;
            this.vStart = vStart;
            this.vEnd = vEnd;
            this.minA = minA;
            this.reason = reason;
        }

        public final long getEndMs() {
            return this.endMs;
        }

        public final float getMinA() {
            return this.minA;
        }

        public final Out getReason() {
            return this.reason;
        }

        public final long getStartMs() {
            return this.startMs;
        }

        public final float getVEnd() {
            return this.vEnd;
        }

        public final float getVStart() {
            return this.vStart;
        }

        public final void setEndMs(long j) {
            this.endMs = j;
        }

        public final void setMinA(float f) {
            this.minA = f;
        }

        public final void setReason(Out out) {
            Intrinsics.checkNotNullParameter(out, "<set-?>");
            this.reason = out;
        }

        public final void setVEnd(float f) {
            this.vEnd = f;
        }
    }

    /* compiled from: SlowReason.kt */
    public static final class Tracker {
        public static final Companion Companion = new Companion(null);
        public static final long HOLD_MS = 4000;
        public static final int MAX_HISTORY = 40;
        private Event current;
        private final ArrayDeque<Event> history = new ArrayDeque<>();
        private long lastBrakeMs;
        private Function1<? super Event, Unit> onEvent;
        private Out shown;

        public final Event getCurrent() {
            return this.current;
        }

        public final Out getShown() {
            return this.shown;
        }

        public final ArrayDeque<Event> getHistory() {
            return this.history;
        }

        public final Function1<Event, Unit> getOnEvent() {
            return (Function1) this.onEvent;
        }

        public final void setOnEvent(Function1<? super Event, Unit> function1) {
            this.onEvent = function1;
        }

        /* compiled from: SlowReason.kt */
        public static final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }
        }

        public final void update(In i, long nowMs) {
            Intrinsics.checkNotNullParameter(i, "i");
            if (SlowReason.INSTANCE.braking(i)) {
                Out r = SlowReason.INSTANCE.explain(i);
                float a = Math.min(i.getATarget(), i.getActAccel());
                Event it = this.current;
                if (it == null) {
                    it = new Event(nowMs, nowMs, i.getVEgo(), i.getVEgo(), a, r);
                    this.current = it;
                }
                it.setEndMs(nowMs);
                it.setVEnd(i.getVEgo());
                if (a <= it.getMinA()) {
                    it.setMinA(a);
                    it.setReason(r);
                }
                this.shown = r;
                this.lastBrakeMs = nowMs;
                return;
            }
            Event e = this.current;
            if (e != null && nowMs - this.lastBrakeMs > 600) {
                finish(e);
                this.current = null;
            }
            if (nowMs - this.lastBrakeMs > 4000) {
                this.shown = null;
            }
        }

        private final void finish(Event e) {
            if (e.getEndMs() - e.getStartMs() < 300) {
                return;
            }
            this.history.addFirst(e);
            while (this.history.size() > 40) {
                this.history.removeLast();
            }
            Function1<? super Event, Unit> function1 = this.onEvent;
            if (function1 != null) {
                function1.invoke(e);
            }
        }
    }
}
