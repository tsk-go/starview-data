package com.starpilot.starview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import java.util.Arrays;
import java.util.List;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: BigUiViews.kt */
public final class DevSidebarView extends View {
    private final Paint bg;
    private final Paint box;
    public Telemetry t;
    private final Paint tp;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DevSidebarView(Context ctx, AttributeSet attrs) {
        super(ctx, attrs);
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Paint $this$box_u24lambda_u240 = new Paint(1);
        $this$box_u24lambda_u240.setStyle(Paint.Style.STROKE);
        $this$box_u24lambda_u240.setColor(Color.argb(85, 255, 255, 255));
        this.box = $this$box_u24lambda_u240;
        Paint $this$bg_u24lambda_u241 = new Paint(1);
        $this$bg_u24lambda_u241.setColor(Color.argb(200, 0, 0, 0));
        this.bg = $this$bg_u24lambda_u241;
        Paint $this$tp_u24lambda_u242 = new Paint(1);
        $this$tp_u24lambda_u242.setColor(-1);
        $this$tp_u24lambda_u242.setTextAlign(Paint.Align.CENTER);
        $this$tp_u24lambda_u242.setTypeface(Typeface.create(Typeface.DEFAULT, 1));
        this.tp = $this$tp_u24lambda_u242;
    }

    public /* synthetic */ DevSidebarView(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
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

    public static /* synthetic */ List rows$default(DevSidebarView devSidebarView, long j, int i, Object obj) {
        if ((i & 1) != 0) {
            j = System.nanoTime();
        }
        return devSidebarView.rows(j);
    }

    public final List<Pair<String, String>> rows(long nowNs) {
        boolean metric = getT().isMetric();
        float k = metric ? 1.0f : 3.28084f;
        String unit = metric ? "m/s²" : "ft/s²";
        boolean drive = getT().getStarted();
        boolean delay = drive && getT().getDelayAtNs() != 0 && nowNs - getT().getDelayAtNs() < 10000000000L;
        boolean torque = drive && getT().getTorqueAtNs() != 0 && nowNs - getT().getTorqueAtNs() < 10000000000L && getT().getTorqueUseParams();
        Pair[] pairArr = new Pair[7];
        pairArr[0] = TuplesKt.to("ACCEL", rows$shown(drive ? getT().getAEgo() * k : Float.NaN, 2, " " + unit));
        pairArr[1] = TuplesKt.to("MAX ACCEL", rows$shown(drive ? getT().getMaxAccel() * k : Float.NaN, 2, " " + unit));
        String str = delay ? "LEARNED DELAY" : "BASE DELAY";
        Telemetry t = getT();
        pairArr[2] = TuplesKt.to(str, rows$shown$default(delay ? t.getLearnedDelay() : t.getBaseDelay(), 0, null, 6, null));
        String str2 = torque ? "LEARNED FRICTION" : "BASE FRICTION";
        Telemetry t2 = getT();
        pairArr[3] = TuplesKt.to(str2, rows$shown$default(torque ? t2.getFrictionLearned() : t2.getBaseFriction(), 0, null, 6, null));
        String str3 = torque ? "LEARNED LAT ACCEL" : "BASE LAT ACCEL";
        Telemetry t3 = getT();
        pairArr[4] = TuplesKt.to(str3, rows$shown$default(torque ? t3.getLatAccelLearned() : t3.getBaseLatAccel(), 0, null, 6, null));
        pairArr[5] = TuplesKt.to("STEER RATIO", rows$shown$default((!drive || getT().getSteerRatioLive() <= 0.0f) ? Float.NaN : getT().getSteerRatioLive(), 0, null, 6, null));
        pairArr[6] = TuplesKt.to("STEER STIFF", rows$shown$default(drive ? getT().getStiffness() : Float.NaN, 0, null, 6, null));
        return CollectionsKt.listOf((Object[]) pairArr);
    }

    private static final String rows$shown(float v, int dec, String suffix) {
        if (!((Float.isInfinite(v) || Float.isNaN(v)) ? false : true)) {
            return "—";
        }
        String format = String.format("%." + dec + "f", Arrays.copyOf(new Object[]{Float.valueOf(v)}, 1));
        Intrinsics.checkNotNullExpressionValue(format, "format(...)");
        return format + suffix;
    }

    static /* synthetic */ String rows$shown$default(float f, int i, String str, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = 5;
        }
        if ((i2 & 4) != 0) {
            str = "";
        }
        return rows$shown(f, i, str);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.view.View
    protected void onDraw(Canvas c) {
        float gap;
        float dpf;
        int n;
        float rr;
        float dpf2;
        float dpf3;
        Intrinsics.checkNotNullParameter(c, "c");
        if (this.t == null) {
            return;
        }
        int i = 1;
        List<Pair> rows = rows$default(this, 0L, 1, null);
        gap = BigUiViewsKt.dpf(this, 6.0f);
        int n2 = rows.size();
        float h = (getHeight() - ((n2 - 1) * gap)) / n2;
        Paint paint = this.box;
        dpf = BigUiViewsKt.dpf(this, 1.5f);
        paint.setStrokeWidth(dpf);
        int i2 = 0;
        for (Pair r : rows) {
            int i3 = i2;
            i2 += i;
            float top = i3 * (h + gap);
            List rows2 = rows;
            RectF rect = new RectF(this.box.getStrokeWidth(), this.box.getStrokeWidth() + top, getWidth() - this.box.getStrokeWidth(), (top + h) - this.box.getStrokeWidth());
            float rr2 = Math.min(rect.height(), rect.width()) * 0.3f;
            c.drawRoundRect(rect, rr2, rr2, this.bg);
            c.drawRoundRect(rect, rr2, rr2, this.box);
            int i4 = 1;
            int i5 = 0;
            for (String line : CollectionsKt.listOf((Object[]) new String[]{r.getFirst(), r.getSecond()})) {
                int j = i5;
                i5 += i4;
                float size = h * 0.26f;
                float gap2 = gap;
                this.tp.setTextSize(size);
                while (true) {
                    float measureText = this.tp.measureText(line);
                    float width = rect.width();
                    n = n2;
                    rr = rr2;
                    dpf2 = BigUiViewsKt.dpf(this, 10.0f);
                    if (measureText <= width - dpf2) {
                        break;
                    }
                    dpf3 = BigUiViewsKt.dpf(this, 8.0f);
                    if (size <= dpf3) {
                        break;
                    }
                    size -= 1.0f;
                    this.tp.setTextSize(size);
                    n2 = n;
                    rr2 = rr;
                }
                c.drawText(line, rect.centerX(), ((j == 0 ? 0.4f : 0.78f) * h) + top, this.tp);
                gap = gap2;
                n2 = n;
                rr2 = rr;
                i4 = 1;
            }
            rows = rows2;
            i = 1;
        }
    }
}
