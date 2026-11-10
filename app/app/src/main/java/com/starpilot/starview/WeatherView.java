package com.starpilot.starview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ExtraViews.kt */
public final class WeatherView extends View {
    private final Paint p;
    public Telemetry t;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WeatherView(Context ctx, AttributeSet attrs) {
        super(ctx, attrs);
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        this.p = new Paint(1);
    }

    public /* synthetic */ WeatherView(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
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

    @Override // android.view.View
    protected void onDraw(Canvas c) {
        float rr;
        float dpf;
        float dpf2;
        float dpf3;
        Intrinsics.checkNotNullParameter(c, "c");
        if (this.t == null || !getT().getStarted() || getT().getWeatherId() <= 0) {
            return;
        }
        float w = getWidth();
        float h = getHeight();
        rr = ExtraViewsKt.dpf(this, 12.0f);
        this.p.setStyle(Paint.Style.FILL);
        this.p.setColor(getT().getWeatherDay() ? Color.rgb(135, 206, 235) : Color.rgb(25, 25, 112));
        c.drawRoundRect(new RectF(0.0f, 0.0f, w, h), rr, rr, this.p);
        this.p.setStyle(Paint.Style.STROKE);
        Paint paint = this.p;
        dpf = ExtraViewsKt.dpf(this, 2.0f);
        paint.setStrokeWidth(dpf);
        this.p.setColor(-16777216);
        float f = 1;
        c.drawRoundRect(new RectF(1.0f, 1.0f, w - f, h - f), rr, rr, this.p);
        int id = getT().getWeatherId();
        float f2 = 2;
        float cx = w / f2;
        float cy = h / f2;
        float r = w * 0.2f;
        this.p.setStyle(Paint.Style.FILL);
        if (!(200 <= id && id < 300)) {
            if (300 <= id && id < 600) {
                onDraw$cloud(this, c, cx, r, cy - (0.4f * r), Color.rgb(200, 200, 210));
                this.p.setColor(Color.rgb(80, 140, 255));
                Paint paint2 = this.p;
                dpf3 = ExtraViewsKt.dpf(this, 3.0f);
                paint2.setStrokeWidth(dpf3);
                this.p.setStyle(Paint.Style.STROKE);
                for (int i = -1; i < 2; i++) {
                    c.drawLine(cx + (i * r * 0.7f), cy + (r * 0.6f), (((i * r) * 0.7f) + cx) - (0.25f * r), cy + (1.5f * r), this.p);
                }
                this.p.setStyle(Paint.Style.FILL);
                return;
            }
            if (!(600 <= id && id < 700)) {
                if (700 <= id && id < 800) {
                    this.p.setColor(Color.rgb(190, 190, 200));
                    Paint paint3 = this.p;
                    dpf2 = ExtraViewsKt.dpf(this, 3.0f);
                    paint3.setStrokeWidth(dpf2);
                    this.p.setStyle(Paint.Style.STROKE);
                    for (int i2 = 0; i2 < 4; i2++) {
                        c.drawLine(cx - (1.3f * r), (i2 * r * 0.5f) + (cy - (r * 0.6f)), cx + (1.3f * r), (i2 * r * 0.5f) + (cy - (r * 0.6f)), this.p);
                    }
                    this.p.setStyle(Paint.Style.FILL);
                    return;
                }
                if (id != 800) {
                    if (!(801 <= id && id < 803)) {
                        onDraw$cloud(this, c, cx, r, cy, Color.rgb(180, 180, 190));
                        return;
                    } else {
                        onDraw$sunOrMoon(this, c, (r * 0.6f) + cx, cy - (0.6f * r), r * 0.8f);
                        onDraw$cloud(this, c, cx, r, cy + (0.2f * r), Color.rgb(235, 235, 240));
                        return;
                    }
                }
                onDraw$sunOrMoon(this, c, cx, cy, 1.1f * r);
                return;
            }
            onDraw$cloud(this, c, cx, r, cy - (0.4f * r), Color.rgb(220, 220, 230));
            this.p.setColor(-1);
            for (int i3 = -1; i3 < 2; i3++) {
                c.drawCircle((i3 * r * 0.7f) + cx, (1.1f * r) + cy, 0.18f * r, this.p);
            }
            return;
        }
        onDraw$cloud(this, c, cx, r, cy - (r * 0.3f), Color.rgb(90, 90, 110));
        this.p.setColor(Color.rgb(255, 220, 0));
        Path pt = new Path();
        pt.moveTo((r * 0.2f) + cx, cy + (0.2f * r));
        pt.lineTo(cx - (r * 0.3f), (r * 1.0f) + cy);
        pt.lineTo((0.05f * r) + cx, (r * 1.0f) + cy);
        pt.lineTo(cx - (0.3f * r), (1.7f * r) + cy);
        pt.lineTo(cx + (0.5f * r), (r * 0.8f) + cy);
        pt.lineTo((0.15f * r) + cx, cy + (0.8f * r));
        pt.close();
        c.drawPath(pt, this.p);
    }

    private static final void onDraw$cloud(WeatherView this$0, Canvas $c, float cx, float r, float y, int col) {
        this$0.p.setColor(col);
        $c.drawCircle(cx - (r * 0.7f), y, 0.75f * r, this$0.p);
        $c.drawCircle((0.1f * r) + cx, y - (0.4f * r), r * 0.9f, this$0.p);
        $c.drawCircle((0.9f * r) + cx, y, r * 0.7f, this$0.p);
        $c.drawRoundRect(new RectF(cx - (1.4f * r), y, (1.5f * r) + cx, (0.7f * r) + y), r * 0.3f, 0.3f * r, this$0.p);
    }

    private static final void onDraw$sunOrMoon(WeatherView this$0, Canvas $c, float x, float y, float rad) {
        if (!this$0.getT().getWeatherDay()) {
            this$0.p.setColor(Color.rgb(240, 240, 200));
            $c.drawCircle(x, y, rad, this$0.p);
            this$0.p.setColor(this$0.getT().getWeatherDay() ? Color.rgb(135, 206, 235) : Color.rgb(25, 25, 112));
            $c.drawCircle((0.45f * rad) + x, y - (0.3f * rad), 0.8f * rad, this$0.p);
            return;
        }
        this$0.p.setColor(Color.rgb(255, 200, 0));
        $c.drawCircle(x, y, rad, this$0.p);
    }
}
