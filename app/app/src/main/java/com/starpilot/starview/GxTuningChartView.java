package com.starpilot.starview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.ranges.RangesKt;

/* compiled from: GxTuning.kt */
final class GxTuningChartView extends View {
    private final Paint axis;
    private final float d;
    private final Paint grid;
    private final Paint label;
    private final Path path;
    private final int rangeDigits;
    private final double rangeFloor;
    private final double rangeMult;
    private final double roundDiv;
    private final List<GxTuningSample> samples;
    private final List<Pair<String, Integer>> series;
    private final Paint stroke;
    private final String unit;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GxTuningChartView(Context ctx, List<Pair<String, Integer>> series, String unit, int rangeDigits, double rangeFloor, double rangeMult, double roundDiv, List<GxTuningSample> samples) {
        super(ctx);
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(series, "series");
        Intrinsics.checkNotNullParameter(unit, "unit");
        Intrinsics.checkNotNullParameter(samples, "samples");
        this.series = series;
        this.unit = unit;
        this.rangeDigits = rangeDigits;
        this.rangeFloor = rangeFloor;
        this.rangeMult = rangeMult;
        this.roundDiv = roundDiv;
        this.samples = samples;
        this.d = ctx.getResources().getDisplayMetrics().density;
        Paint $this$grid_u24lambda_u240 = new Paint(1);
        $this$grid_u24lambda_u240.setStrokeWidth(this.d);
        $this$grid_u24lambda_u240.setColor(Color.argb(64, 128, 128, 128));
        this.grid = $this$grid_u24lambda_u240;
        Paint $this$axis_u24lambda_u241 = new Paint(1);
        $this$axis_u24lambda_u241.setStrokeWidth(this.d);
        $this$axis_u24lambda_u241.setColor(Color.rgb(170, 170, 170));
        this.axis = $this$axis_u24lambda_u241;
        Paint $this$label_u24lambda_u242 = new Paint(1);
        $this$label_u24lambda_u242.setColor(CarUi.Companion.getDIM());
        $this$label_u24lambda_u242.setTextSize(13 * this.d);
        this.label = $this$label_u24lambda_u242;
        Paint $this$stroke_u24lambda_u243 = new Paint(1);
        $this$stroke_u24lambda_u243.setStyle(Paint.Style.STROKE);
        $this$stroke_u24lambda_u243.setStrokeWidth(this.d * 2.2f);
        $this$stroke_u24lambda_u243.setStrokeJoin(Paint.Join.ROUND);
        $this$stroke_u24lambda_u243.setStrokeCap(Paint.Cap.ROUND);
        this.stroke = $this$stroke_u24lambda_u243;
        this.path = new Path();
        setBackgroundColor(Color.rgb(6, 8, 11));
    }

    private final String fmt(double v) {
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        String format = String.format(Locale.US, "%." + this.rangeDigits + "f", Arrays.copyOf(new Object[]{Double.valueOf(v)}, 1));
        Intrinsics.checkNotNullExpressionValue(format, "format(...)");
        return format;
    }

    @Override // android.view.View
    protected void onDraw(Canvas c) {
        String format;
        Intrinsics.checkNotNullParameter(c, "c");
        super.onDraw(c);
        float w = getWidth();
        float h = getHeight();
        float f = 10;
        float pad = f * this.d;
        int n = this.samples.size();
        if (n <= 1) {
            this.label.setTextAlign(Paint.Align.CENTER);
            float f2 = 2;
            c.drawText("Waiting for enough live samples...", w / f2, h / f2, this.label);
            return;
        }
        double maxAbs = 0.0d;
        for (GxTuningSample s : this.samples) {
            Iterator<Pair<String, Integer>> it = this.series.iterator();
            while (it.hasNext()) {
                String k = it.next().component1();
                Double d = s.getV().get(k);
                double v = Math.abs(d != null ? d.doubleValue() : 0.0d);
                if (v > maxAbs) {
                    maxAbs = v;
                }
            }
        }
        double half = Math.max(this.rangeFloor, Math.ceil((this.rangeMult * maxAbs) * this.roundDiv) / this.roundDiv);
        double max = half;
        int n2 = n;
        double min = -half;
        double span = Math.max(1.0E-6d, max - min);
        int i = 3;
        int i2 = 1;
        Iterator it2 = CollectionsKt.listOf((Object[]) new Double[]{Double.valueOf(max * 0.5d), Double.valueOf(0.0d), Double.valueOf(0.5d * min)}).iterator();
        while (it2.hasNext()) {
            double gv = ((Number) it2.next()).doubleValue();
            double max2 = max;
            double max3 = min;
            float f3 = h;
            c.drawLine(0.0f, onDraw$y(pad, max, max3, span, f3, gv), w, onDraw$y(pad, max2, max3, span, f3, gv), ((gv > 0.0d ? 1 : (gv == 0.0d ? 0 : -1)) == 0 ? i2 : 0) != 0 ? this.axis : this.grid);
            i = i;
            f = f;
            h = h;
            min = min;
            n2 = n2;
            i2 = i2;
            max = max2;
        }
        int i3 = i;
        double min2 = min;
        double max4 = max;
        int n3 = n2;
        int i4 = i2;
        float f4 = f;
        float h2 = h;
        String suffix = (this.unit.length() > 0 ? i4 : 0) != 0 ? " " + this.unit : "";
        this.label.setTextAlign(Paint.Align.LEFT);
        float f5 = 4;
        c.drawText(fmt(max4) + suffix, this.d * f5, pad + (this.d * f4), this.label);
        c.drawText("0", this.d * f5, onDraw$y(pad, max4, min2, span, h2, 0.0d) - (i3 * this.d), this.label);
        double min3 = min2;
        c.drawText(fmt(min3) + suffix, this.d * f5, h2 - (5 * this.d), this.label);
        double d2 = 0.0d;
        double age = Math.max(0.0d, (System.currentTimeMillis() / 1000.0d) - ((GxTuningSample) CollectionsKt.last((List) this.samples)).getTs());
        this.label.setTextAlign(Paint.Align.RIGHT);
        if (age < 1.0d) {
            format = Math.round(1000 * age) + " ms";
        } else {
            StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
            format = String.format(Locale.US, "%.1f s", Arrays.copyOf(new Object[]{Double.valueOf(age)}, i4));
            Intrinsics.checkNotNullExpressionValue(format, "format(...)");
        }
        c.drawText("-" + format, w - (f5 * this.d), pad + (f4 * this.d), this.label);
        for (Pair<String, Integer> pair : this.series) {
            String k2 = pair.component1();
            int color = pair.component2().intValue();
            this.stroke.setColor(color);
            this.path.reset();
            int i5 = 0;
            while (i5 < n3) {
                float x = (i5 / (n3 - 1)) * w;
                Double d3 = this.samples.get(i5).getV().get(k2);
                String suffix2 = suffix;
                double d4 = d2;
                double min4 = min3;
                float yy = onDraw$y(pad, max4, min3, span, h2, d3 != null ? d3.doubleValue() : d2);
                Path path = this.path;
                if (i5 == 0) {
                    path.moveTo(x, yy);
                } else {
                    path.lineTo(x, yy);
                }
                i5++;
                suffix = suffix2;
                d2 = d4;
                min3 = min4;
            }
            c.drawPath(this.path, this.stroke);
            suffix = suffix;
        }
    }

    private static final float onDraw$y(float pad, double max, double min, double span, float h, double v) {
        return (float) (pad + (((max - RangesKt.coerceIn(v, min, max)) / span) * (h - (2 * pad))));
    }
}
