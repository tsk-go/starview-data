package com.starpilot.starview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import kotlin.ranges.RangesKt;

/* compiled from: ExtraViews.kt */
public final class AetherGaugeView extends View {
    private boolean active;
    private long lastActiveNs;
    private final Paint p;
    private final Path path;
    public Telemetry t;
    private final Paint tp;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AetherGaugeView(Context ctx, AttributeSet attrs) {
        super(ctx, attrs);
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        this.p = new Paint(1);
        Paint $this$tp_u24lambda_u240 = new Paint(1);
        $this$tp_u24lambda_u240.setTextAlign(Paint.Align.CENTER);
        $this$tp_u24lambda_u240.setTypeface(Typeface.DEFAULT_BOLD);
        $this$tp_u24lambda_u240.setColor(-1);
        this.tp = $this$tp_u24lambda_u240;
        this.path = new Path();
    }

    public /* synthetic */ AetherGaugeView(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet);
    }

    public final Telemetry getT() {
        Telemetry telemetry = this.t;
        if (telemetry != null) {
            return telemetry;
        }
        Intrinsics.throwUninitializedPropertyAccessException("t");
        return null;
    }

    public final void setT(Telemetry telemetry) {
        Intrinsics.checkNotNullParameter(telemetry, "<set-?>");
        this.t = telemetry;
    }

    public final boolean getActive() {
        return this.active;
    }

    private final float conv(float ms) {
        return (getT().isMetric() ? 3.6f : 2.23694f) * ms;
    }

    private final String dist(float m) {
        StringBuilder append;
        String str;
        if (getT().isMetric()) {
            append = new StringBuilder().append(MathKt.roundToInt(m));
            str = " m";
        } else {
            append = new StringBuilder().append(MathKt.roundToInt(3.28084f * m));
            str = " ft";
        }
        return append.append(str).toString();
    }

    /* compiled from: ExtraViews.kt */
    public static final /* data */ class Src {
        private final int color;
        private final boolean curve;
        private final String label;
        private final String unit;
        private final String value;

        public static /* synthetic */ Src copy$default(Src src, String str, String str2, String str3, int i, boolean z, int i2, Object obj) {
            if ((i2 & 1) != 0) {
                str = src.value;
            }
            if ((i2 & 2) != 0) {
                str2 = src.unit;
            }
            String str4 = str2;
            if ((i2 & 4) != 0) {
                str3 = src.label;
            }
            String str5 = str3;
            if ((i2 & 8) != 0) {
                i = src.color;
            }
            int i3 = i;
            if ((i2 & 16) != 0) {
                z = src.curve;
            }
            return src.copy(str, str4, str5, i3, z);
        }

        /* renamed from: component1, reason: from getter */
        public final String getValue() {
            return this.value;
        }

        /* renamed from: component2, reason: from getter */
        public final String getUnit() {
            return this.unit;
        }

        /* renamed from: component3, reason: from getter */
        public final String getLabel() {
            return this.label;
        }

        /* renamed from: component4, reason: from getter */
        public final int getColor() {
            return this.color;
        }

        /* renamed from: component5, reason: from getter */
        public final boolean getCurve() {
            return this.curve;
        }

        public final Src copy(String value, String unit, String label, int color, boolean curve) {
            Intrinsics.checkNotNullParameter(value, "value");
            Intrinsics.checkNotNullParameter(unit, "unit");
            Intrinsics.checkNotNullParameter(label, "label");
            return new Src(value, unit, label, color, curve);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Src)) {
                return false;
            }
            Src src = (Src) other;
            return Intrinsics.areEqual(this.value, src.value) && Intrinsics.areEqual(this.unit, src.unit) && Intrinsics.areEqual(this.label, src.label) && this.color == src.color && this.curve == src.curve;
        }

        public int hashCode() {
            return (((((((this.value.hashCode() * 31) + this.unit.hashCode()) * 31) + this.label.hashCode()) * 31) + Integer.hashCode(this.color)) * 31) + Boolean.hashCode(this.curve);
        }

        public String toString() {
            return "Src(value=" + this.value + ", unit=" + this.unit + ", label=" + this.label + ", color=" + this.color + ", curve=" + this.curve + ")";
        }

        public Src(String value, String unit, String label, int color, boolean curve) {
            Intrinsics.checkNotNullParameter(value, "value");
            Intrinsics.checkNotNullParameter(unit, "unit");
            Intrinsics.checkNotNullParameter(label, "label");
            this.value = value;
            this.unit = unit;
            this.label = label;
            this.color = color;
            this.curve = curve;
        }

        public final int getColor() {
            return this.color;
        }

        public final boolean getCurve() {
            return this.curve;
        }

        public final String getLabel() {
            return this.label;
        }

        public final String getUnit() {
            return this.unit;
        }

        public final String getValue() {
            return this.value;
        }
    }

    private final Src source() {
        boolean cruiseSet = getT().getCruiseEnabled() && getT().getPlanVCruise() > 0.0f;
        if (getT().getForcingStop() && !getT().getRedLight()) {
            return new Src(getT().getForcingStopLength() > 0.0f ? dist(getT().getForcingStopLength()) : "", "", "STOP", Color.rgb(255, 30, 60), false);
        }
        if (getT().getExperimentalMode() && getT().getRedLight()) {
            return new Src(dist(getT().getForcingStopLength()), "", "RED LIGHT", Color.rgb(255, 60, 60), false);
        }
        if ((getT().getCscControlling() || getT().getCscTraining()) && cruiseSet) {
            int col = getT().getCscTraining() ? Color.rgb(112, 192, 216) : OverlayView.Companion.cscColor(getT().getRoadCurvature());
            return new Src(String.valueOf(MathKt.roundToInt(conv(getT().getCscSpeed()))), getT().isMetric() ? "km/h" : "mph", getT().getCscTraining() ? "LEARNING" : "CURVE", col, true);
        }
        if (getT().getExperimentalMode() && getT().getTrackingLead() && getT().getLeadOne().getStatus()) {
            boolean stopped = getT().getLeadOne().getVLead() < 0.5f;
            return new Src(stopped ? "STOPPED" : "SLOW", "", "LEAD", stopped ? Color.rgb(255, 60, 60) : Color.rgb(255, 191, 0), false);
        }
        return null;
    }

    @Override // android.view.View
    protected void onDraw(Canvas c) {
        float rr;
        float dpf;
        float roadTop;
        float dpf2;
        float dpf3;
        float dpf4;
        float dpf5;
        float dpf6;
        Intrinsics.checkNotNullParameter(c, "c");
        if (this.t == null || !getT().getStarted()) {
            this.active = false;
            return;
        }
        Src src = source();
        long now = System.nanoTime();
        if (src != null) {
            this.lastActiveNs = now;
        }
        this.active = src != null || now - this.lastActiveNs < 500000000;
        if (src == null) {
            return;
        }
        float w = getWidth();
        float h = getHeight();
        rr = ExtraViewsKt.dpf(this, 14.0f);
        this.p.setStyle(Paint.Style.FILL);
        this.p.setColor(Color.argb(166, 0, 0, 0));
        c.drawRoundRect(new RectF(0.0f, 0.0f, w, h), rr, rr, this.p);
        this.p.setStyle(Paint.Style.STROKE);
        Paint paint = this.p;
        dpf = ExtraViewsKt.dpf(this, 2.0f);
        paint.setStrokeWidth(dpf);
        this.p.setColor(OverlayView.Companion.withAlpha(src.getColor(), 200));
        float f = 1;
        c.drawRoundRect(new RectF(1.0f, 1.0f, w - f, h - f), rr, rr, this.p);
        roadTop = ExtraViewsKt.dpf(this, 10.0f);
        float roadBot = 0.52f * h;
        float wb = 0.46f * w;
        float wt = w * 0.22f;
        float f2 = 2;
        float cx = w / f2;
        float bend = src.getCurve() ? (RangesKt.coerceIn(getT().getRoadCurvature(), -0.02f, 0.02f) / 0.02f) * w * 0.18f : 0.0f;
        this.path.rewind();
        this.path.moveTo(cx - (wb / f2), roadBot);
        this.path.lineTo((wb / f2) + cx, roadBot);
        this.path.quadTo((wt / f2) + cx + (bend * 0.5f), (roadTop + roadBot) / f2, ((wt / f2) + cx) - bend, roadTop);
        this.path.lineTo((cx - (wt / f2)) - bend, roadTop);
        this.path.quadTo((cx - (wt / f2)) + (0.5f * bend), (roadTop + roadBot) / f2, cx - (wb / f2), roadBot);
        this.path.close();
        this.p.setStyle(Paint.Style.FILL);
        this.p.setColor(OverlayView.Companion.withAlpha(src.getColor(), 90));
        c.drawPath(this.path, this.p);
        this.p.setStyle(Paint.Style.STROKE);
        Paint paint2 = this.p;
        dpf2 = ExtraViewsKt.dpf(this, 2.0f);
        paint2.setStrokeWidth(dpf2);
        this.p.setColor(src.getColor());
        c.drawPath(this.path, this.p);
        int i = 0;
        while (i < 4) {
            float f3 = (i * 0.22f) + 0.15f;
            float y = roadBot - ((roadBot - roadTop) * f3);
            float hw = (wb + ((wt - wb) * f3)) * 0.28f;
            float x = cx - ((bend * f3) * f3);
            this.path.rewind();
            float bend2 = bend;
            float w2 = w;
            dpf3 = ExtraViewsKt.dpf(this, 3.0f);
            this.path.moveTo(x - hw, dpf3 + y);
            Path path = this.path;
            dpf4 = ExtraViewsKt.dpf(this, 3.0f);
            path.lineTo(x, y - dpf4);
            dpf5 = ExtraViewsKt.dpf(this, 3.0f);
            this.path.lineTo(x + hw, dpf5 + y);
            Paint paint3 = this.p;
            dpf6 = ExtraViewsKt.dpf(this, 1.5f);
            paint3.setStrokeWidth(dpf6);
            this.p.setColor(OverlayView.Companion.withAlpha(-1, 160));
            c.drawPath(this.path, this.p);
            i++;
            bend = bend2;
            w = w2;
        }
        this.tp.setColor(-1);
        this.tp.setTextSize(src.getValue().length() > 4 ? 0.15f * h : h * 0.26f);
        c.drawText(src.getValue(), cx, 0.76f * h, this.tp);
        this.tp.setTextSize(0.1f * h);
        this.tp.setColor(OverlayView.Companion.withAlpha(-1, 180));
        c.drawText(src.getUnit().length() > 0 ? src.getUnit() : src.getLabel(), cx, 0.9f * h, this.tp);
        if (src.getUnit().length() > 0) {
            this.tp.setTextSize(0.09f * h);
            this.tp.setColor(OverlayView.Companion.withAlpha(src.getColor(), 230));
            c.drawText(src.getLabel(), cx, (0.08f * h) + roadBot, this.tp);
        }
    }
}
