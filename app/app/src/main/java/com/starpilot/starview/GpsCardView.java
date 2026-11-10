package com.starpilot.starview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.starpilot.starview.GpsSummary;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;

/* compiled from: GpsCardView.kt */
public final class GpsCardView extends View {
    private final Paint bg;
    private final Paint big;
    private final SimpleDateFormat clock;
    private final float d;
    private float downX;
    private float downY;
    private final Paint fill;
    private final Paint label;
    private final Paint line;
    private final Paint num;
    private final Paint panel;
    private final Path path;
    private final RectF r;
    private final Paint small;
    private final Paint status;
    public Telemetry t;
    private final Paint tag;
    private final Paint value;
    public static final Companion Companion = new Companion(null);
    private static final int CYAN = Color.rgb(0, 238, 255);

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GpsCardView(Context ctx, AttributeSet attrs) {
        super(ctx, attrs);
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        this.d = getResources().getDisplayMetrics().density;
        Paint $this$bg_u24lambda_u240 = new Paint(1);
        $this$bg_u24lambda_u240.setColor(Color.argb(205, 0, 0, 0));
        this.bg = $this$bg_u24lambda_u240;
        Paint $this$panel_u24lambda_u241 = new Paint(1);
        $this$panel_u24lambda_u241.setColor(Color.argb(255, 4, 4, 52));
        this.panel = $this$panel_u24lambda_u241;
        Paint $this$line_u24lambda_u242 = new Paint(1);
        $this$line_u24lambda_u242.setStyle(Paint.Style.STROKE);
        $this$line_u24lambda_u242.setStrokeWidth(dp(1.0f));
        $this$line_u24lambda_u242.setColor(Color.argb(150, 255, 255, 255));
        this.line = $this$line_u24lambda_u242;
        this.fill = new Paint(1);
        Paint $this$label_u24lambda_u243 = new Paint(1);
        $this$label_u24lambda_u243.setColor(-1);
        $this$label_u24lambda_u243.setTextSize(dp(12.0f));
        this.label = $this$label_u24lambda_u243;
        Paint $this$small_u24lambda_u244 = new Paint(1);
        $this$small_u24lambda_u244.setColor(Color.rgb(190, 198, 214));
        $this$small_u24lambda_u244.setTextSize(dp(11.0f));
        this.small = $this$small_u24lambda_u244;
        Paint $this$status_u24lambda_u245 = new Paint(1);
        $this$status_u24lambda_u245.setColor(-1);
        $this$status_u24lambda_u245.setTextSize(dp(22.0f));
        $this$status_u24lambda_u245.setTypeface(Typeface.DEFAULT_BOLD);
        this.status = $this$status_u24lambda_u245;
        Paint $this$big_u24lambda_u246 = new Paint(1);
        $this$big_u24lambda_u246.setColor(CYAN);
        $this$big_u24lambda_u246.setTextSize(dp(30.0f));
        $this$big_u24lambda_u246.setTypeface(Typeface.DEFAULT_BOLD);
        $this$big_u24lambda_u246.setTextAlign(Paint.Align.RIGHT);
        this.big = $this$big_u24lambda_u246;
        Paint $this$num_u24lambda_u247 = new Paint(1);
        $this$num_u24lambda_u247.setColor(-1);
        $this$num_u24lambda_u247.setTextSize(dp(10.0f));
        $this$num_u24lambda_u247.setTextAlign(Paint.Align.CENTER);
        this.num = $this$num_u24lambda_u247;
        Paint $this$tag_u24lambda_u248 = new Paint(1);
        $this$tag_u24lambda_u248.setTextSize(dp(9.0f));
        $this$tag_u24lambda_u248.setTextAlign(Paint.Align.CENTER);
        $this$tag_u24lambda_u248.setTypeface(Typeface.DEFAULT_BOLD);
        this.tag = $this$tag_u24lambda_u248;
        Paint $this$value_u24lambda_u249 = new Paint(1);
        $this$value_u24lambda_u249.setColor(CYAN);
        $this$value_u24lambda_u249.setTextSize(dp(20.0f));
        $this$value_u24lambda_u249.setTypeface(Typeface.DEFAULT_BOLD);
        $this$value_u24lambda_u249.setTextAlign(Paint.Align.CENTER);
        this.value = $this$value_u24lambda_u249;
        this.r = new RectF();
        this.path = new Path();
        this.clock = new SimpleDateFormat("h:mm:ss", Locale.US);
    }

    public /* synthetic */ GpsCardView(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
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

    private final float dp(float v) {
        return this.d * v;
    }

    /* compiled from: GpsCardView.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final int getCYAN() {
            return GpsCardView.CYAN;
        }

        public final int cnoColor(int cno) {
            return cno < 10 ? Color.rgb(255, 32, 32) : cno < 20 ? Color.rgb(255, 128, 0) : cno < 30 ? Color.rgb(255, 238, 0) : cno < 50 ? Color.rgb(170, 255, 0) : Color.rgb(0, 230, 64);
        }

        /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
        java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
        	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
        	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterative(DepthRegionTraversal.java:31)
        	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visit(SwitchOverStringVisitor.java:60)
         */
        public final int systemColor(String sys) {
            Intrinsics.checkNotNullParameter(sys, "sys");
            switch (sys.hashCode()) {
                case 67:
                    if (sys.equals("C")) {
                        return Color.rgb(255, 200, 80);
                    }
                    break;
                case 69:
                    if (sys.equals("E")) {
                        return Color.rgb(140, 255, 140);
                    }
                    break;
                case 71:
                    if (sys.equals("G")) {
                        return Color.rgb(120, 200, 255);
                    }
                    break;
                case 74:
                    if (sys.equals("J")) {
                        return Color.rgb(220, 140, 255);
                    }
                    break;
                case 82:
                    if (sys.equals("R")) {
                        return Color.rgb(255, 110, 110);
                    }
                    break;
            }
            return Color.rgb(200, 200, 200);
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent e) {
        Intrinsics.checkNotNullParameter(e, "e");
        switch (e.getActionMasked()) {
            case 0:
                this.downX = e.getX();
                this.downY = e.getY();
                return true;
            case 1:
                if (Math.abs(e.getX() - this.downX) < dp(12.0f) && Math.abs(e.getY() - this.downY) < dp(12.0f)) {
                    performClick();
                }
                break;
            default:
                return true;
        }
    }

    @Override // android.view.View
    public boolean performClick() {
        super.performClick();
        try {
            getContext().startActivity(new Intent(getContext(), (Class<?>) SensorsActivity.class));
            return true;
        } catch (Exception e) {
            return true;
        }
    }

    private final String fit(String text, float maxW, Paint p) {
        if (maxW <= 0.0f) {
            return "";
        }
        if (p.measureText(text) <= maxW) {
            return text;
        }
        int n = text.length();
        while (n > 1 && p.measureText(text, 0, n) + p.measureText("…") > maxW) {
            n--;
        }
        String substring = text.substring(0, n);
        Intrinsics.checkNotNullExpressionValue(substring, "substring(...)");
        return StringsKt.trimEnd((CharSequence) substring).toString() + "…";
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // android.view.View
    protected void onDraw(Canvas cv) {
        String avg;
        Pair pair;
        StringBuilder append;
        String str;
        String str2;
        Intrinsics.checkNotNullParameter(cv, "cv");
        if (this.t == null) {
            return;
        }
        float w = getWidth();
        float h = getHeight();
        GpsSummary.Companion companion = GpsSummary.Companion;
        Collection<GpsFixInfo> values = getT().getGpsFixes().values();
        Intrinsics.checkNotNullExpressionValue(values, "<get-values>(...)");
        GpsSummary s = companion.of(values, getT().getGnssSats(), getT().getGnssAtNs(), getT().getStarted(), System.nanoTime());
        boolean metric = getT().isMetric();
        this.r.set(0.0f, 0.0f, w, h);
        cv.drawRoundRect(this.r, dp(14.0f), dp(14.0f), this.bg);
        float pad = dp(10.0f);
        RtkStatus k = Ntrip.INSTANCE.getRtk();
        float avgCno = s.getAvgCno();
        if ((Float.isInfinite(avgCno) || Float.isNaN(avgCno)) ? false : true) {
            String format = String.format("%.1f", Arrays.copyOf(new Object[]{Float.valueOf(s.getAvgCno())}, 1));
            Intrinsics.checkNotNullExpressionValue(format, "format(...)");
            avg = "   avg signal " + format;
        } else {
            avg = "";
        }
        if (k != null) {
            int dot = k.getFresh() ? Intrinsics.areEqual(k.getRtk(), "fixed") ? Color.rgb(0, 230, 64) : Intrinsics.areEqual(k.getRtk(), "float") ? Color.rgb(190, 230, 0) : Intrinsics.areEqual(k.getFixType(), "dead reckoning") ? Color.rgb(255, 170, 0) : k.getHasFix() ? CYAN : Color.rgb(255, 170, 0) : -7829368;
            this.fill.setColor(dot);
            str2 = "";
            cv.drawCircle(dp(9.0f) + pad, dp(26.0f), dp(8.0f), this.fill);
            String accText = k.getHasFix() ? "± " + RtkStatus.Companion.accuracy(k.getHAccM(), metric) : str2;
            if (accText.length() > 0) {
                cv.drawText(accText, w - pad, dp(37.0f), this.big);
            }
            float f = 2;
            cv.drawText(fit(k.getWords(), ((w - (f * pad)) - dp(32.0f)) - this.big.measureText(accText), this.status), dp(24.0f) + pad, dp(34.0f), this.status);
            String corr = k.getRtcmAgeS() == null ? Intrinsics.areEqual(Ntrip.INSTANCE.getState(), "off") ? "corrections off" : "no corrections" : k.getCorrectionAgeS() != null ? "corrections " + k.getCorrectionAgeS() + " s" : "corrections not used yet";
            String sensors = k.getFusion().length() > 0 ? "   sensors " + k.getFusion() : "";
            cv.drawText(fit("RTK  " + k.getUsed() + " sats   " + corr + sensors, w - (f * pad), this.small), pad, dp(54.0f), this.small);
            cv.drawText(fit("comma GPS  in view " + s.getInView() + "   in use " + s.getInUse() + avg, w - (f * pad), this.small), pad, dp(69.0f), this.small);
        } else {
            String state = s.getState();
            switch (state.hashCode()) {
                case 101397:
                    if (state.equals("fix")) {
                        pair = TuplesKt.to(Integer.valueOf(Color.rgb(0, 230, 64)), s.getInUse() >= 4 ? "3D Fix" : "Fix");
                        break;
                    }
                    pair = TuplesKt.to(-7829368, "No GPS data");
                    break;
                case 109935:
                    if (state.equals("off")) {
                        pair = TuplesKt.to(-7829368, "GPS off");
                        break;
                    }
                    pair = TuplesKt.to(-7829368, "No GPS data");
                    break;
                case 1778217274:
                    if (state.equals("searching")) {
                        pair = TuplesKt.to(Integer.valueOf(Color.rgb(255, 170, 0)), "Searching");
                        break;
                    }
                    pair = TuplesKt.to(-7829368, "No GPS data");
                    break;
                default:
                    pair = TuplesKt.to(-7829368, "No GPS data");
                    break;
            }
            int dot2 = ((Number) pair.component1()).intValue();
            String words = (String) pair.component2();
            this.fill.setColor(dot2);
            cv.drawCircle(dp(9.0f) + pad, dp(26.0f), dp(8.0f), this.fill);
            cv.drawText(words, dp(24.0f) + pad, dp(34.0f), this.status);
            float accuracyM = s.getAccuracyM();
            if ((Float.isInfinite(accuracyM) || Float.isNaN(accuracyM)) ? false : true) {
                float accuracyM2 = s.getAccuracyM();
                if (metric) {
                    append = new StringBuilder().append("± ").append(MathKt.roundToInt(accuracyM2));
                    str = " m";
                } else {
                    append = new StringBuilder().append("± ").append(MathKt.roundToInt(accuracyM2 * 3.28084f));
                    str = " ft";
                }
                String acc = append.append(str).toString();
                cv.drawText(acc, w - pad, dp(37.0f), this.big);
            } else if (Intrinsics.areEqual(s.getState(), "off")) {
                this.small.setTextAlign(Paint.Align.RIGHT);
                cv.drawText("starts when the car is on", w - pad, dp(32.0f), this.small);
                this.small.setTextAlign(Paint.Align.LEFT);
            }
            cv.drawText("In view " + s.getInView() + "   In use " + s.getInUse() + avg, pad, dp(58.0f), this.small);
        }
        float top = dp(k != null ? 76.0f : 66.0f);
        float bottom = h - dp(78.0f);
        this.r.set(pad, top, w - pad, bottom);
        cv.drawRoundRect(this.r, dp(8.0f), dp(8.0f), this.panel);
        float barTop = dp(16.0f) + top;
        float py = bottom - dp(20.0f);
        float slot = dp(19.0f);
        float f2 = 2;
        int n = Math.min(s.getBars().size(), (int) (((w - (f2 * pad)) - dp(8.0f)) / slot));
        if (n == 0) {
            this.small.setTextAlign(Paint.Align.CENTER);
            cv.drawText(Intrinsics.areEqual(s.getState(), "off") ? "The comma's GPS runs while the car is on" : "No satellites reported yet", w / f2, (top + bottom) / f2, this.small);
            this.small.setTextAlign(Paint.Align.LEFT);
        }
        float x0 = (w - (n * slot)) / f2;
        int i = 0;
        while (i < n) {
            GnssSat sat = s.getBars().get(i);
            float cx = (i * slot) + x0 + (slot / f2);
            float x02 = x0;
            float barTop2 = barTop;
            float f3 = f2;
            float bh = (py - barTop) * (RangesKt.coerceIn(sat.getCno(), 0, 50) / 50.0f);
            this.fill.setColor(Companion.cnoColor(sat.getCno()));
            float h2 = h;
            this.r.set(cx - (0.36f * slot), py - bh, cx + (0.36f * slot), py);
            cv.drawRect(this.r, this.fill);
            cv.drawText(String.valueOf(sat.getCno()), cx, (py - bh) - dp(3.0f), this.num);
            float slot2 = slot;
            this.r.set(cx - (0.47f * slot), dp(4.0f) + py, (0.47f * slot) + cx, dp(17.0f) + py);
            if (sat.getUsed()) {
                this.fill.setColor(-1);
                cv.drawRoundRect(this.r, dp(3.0f), dp(3.0f), this.fill);
                this.tag.setColor(-16777216);
            } else {
                cv.drawRoundRect(this.r, dp(3.0f), dp(3.0f), this.line);
                this.tag.setColor(-1);
            }
            cv.drawText(sat.getLabel(), cx, dp(14.0f) + py, this.tag);
            i++;
            x0 = x02;
            barTop = barTop2;
            f2 = f3;
            slot = slot2;
            h = h2;
        }
        float h3 = h;
        float f4 = f2;
        float f5 = 6.0f;
        if (s.getBars().size() > n && n > 0) {
            this.small.setTextAlign(Paint.Align.RIGHT);
            cv.drawText("+" + (s.getBars().size() - n) + " more", (w - pad) - dp(6.0f), dp(13.0f) + top, this.small);
            this.small.setTextAlign(Paint.Align.LEFT);
        }
        float rowTop = h3 - dp(70.0f);
        float rowH = dp(62.0f);
        float cell = ((w - (f4 * pad)) - dp(18.0f)) / 4;
        int k2 = 0;
        for (int i2 = 4; k2 < i2; i2 = 4) {
            this.r.set((k2 * (cell + dp(f5))) + pad, rowTop, (k2 * (cell + dp(f5))) + pad + cell, rowTop + rowH);
            cv.drawRoundRect(this.r, dp(8.0f), dp(8.0f), this.panel);
            k2++;
            f5 = 6.0f;
        }
        float cy = (rowH / f4) + rowTop;
        float rad = (Math.min(cell, rowH) / f4) - dp(4.0f);
        float sx = onDraw$cellX(pad, cell, this, 0);
        cv.drawCircle(sx, cy, rad, this.line);
        float w2 = 3;
        cv.drawCircle(sx, cy, (rad * f4) / w2, this.line);
        cv.drawCircle(sx, cy, rad / w2, this.line);
        Iterator<GnssSat> it = s.getSky().iterator();
        while (it.hasNext()) {
            GnssSat sat2 = it.next();
            float rr = ((90 - sat2.getElevation()) * rad) / 90.0f;
            Iterator<GnssSat> it2 = it;
            float top2 = top;
            float rad2 = rad;
            double a = Math.toRadians(sat2.getAzimuth());
            GpsSummary s2 = s;
            int n2 = n;
            float px = ((float) (rr * Math.sin(a))) + sx;
            float barBase = py;
            float sx2 = sx;
            float py2 = cy - ((float) (rr * Math.cos(a)));
            this.fill.setColor(Companion.systemColor(sat2.getSystem()));
            if (sat2.getUsed()) {
                cv.drawCircle(px, py2, dp(3.2f), this.fill);
                s = s2;
                it = it2;
                top = top2;
                rad = rad2;
                n = n2;
                sx = sx2;
                py = barBase;
            } else {
                this.line.setColor(this.fill.getColor());
                cv.drawCircle(px, py2, dp(3.0f), this.line);
                this.line.setColor(Color.argb(150, 255, 255, 255));
                s = s2;
                it = it2;
                top = top2;
                rad = rad2;
                n = n2;
                sx = sx2;
                py = barBase;
            }
        }
        GpsSummary s3 = s;
        float ax = onDraw$cellX(pad, cell, this, 1);
        this.small.setTextAlign(Paint.Align.CENTER);
        cv.drawText("N", ax, dp(12.0f) + rowTop, this.small);
        float bearing = s3.getBearing();
        if ((Float.isInfinite(bearing) || Float.isNaN(bearing)) ? false : true) {
            cv.save();
            cv.rotate(s3.getBearing(), ax, dp(4.0f) + cy);
            this.path.reset();
            this.path.moveTo(ax, cy - dp(16.0f));
            this.path.lineTo(dp(9.0f) + ax, dp(18.0f) + cy);
            this.path.lineTo(ax, dp(11.0f) + cy);
            this.path.lineTo(ax - dp(9.0f), dp(18.0f) + cy);
            this.path.close();
            this.fill.setColor(CYAN);
            cv.drawPath(this.path, this.fill);
            cv.restore();
            cv.drawText(MathKt.roundToInt(s3.getBearing()) + "°", ax, (rowTop + rowH) - dp(4.0f), this.small);
        } else {
            cv.drawText("–", ax, dp(8.0f) + cy, this.value);
        }
        float speed = s3.getSpeed();
        String spd = (Float.isInfinite(speed) || Float.isNaN(speed)) ? false : true ? String.valueOf(MathKt.roundToInt(s3.getSpeed() * (metric ? 3.6f : 2.23694f))) : "–";
        cv.drawText(spd, onDraw$cellX(pad, cell, this, 2), dp(6.0f) + cy, this.value);
        cv.drawText(metric ? "km/h" : "mph", onDraw$cellX(pad, cell, this, 2), (rowTop + rowH) - dp(5.0f), this.small);
        this.value.setTextSize(dp(14.0f));
        cv.drawText(s3.getUnixMs() > 0 ? this.clock.format(new Date(s3.getUnixMs())) : "–", onDraw$cellX(pad, cell, this, 3), dp(5.0f) + cy, this.value);
        this.value.setTextSize(dp(20.0f));
        cv.drawText("GPS time", onDraw$cellX(pad, cell, this, 3), (rowTop + rowH) - dp(5.0f), this.small);
        this.small.setTextAlign(Paint.Align.LEFT);
    }

    private static final float onDraw$cellX(float pad, float cell, GpsCardView this$0, int k) {
        return (k * (this$0.dp(6.0f) + cell)) + pad + (cell / 2);
    }
}
