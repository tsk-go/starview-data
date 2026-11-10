package com.starpilot.starview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

/* compiled from: GxLogs.kt */
final class GxLogsSpark extends View {
    private final Paint paint;
    private List<Double> values;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GxLogsSpark(Context ctx) {
        super(ctx);
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        this.values = CollectionsKt.emptyList();
        Paint $this$paint_u24lambda_u240 = new Paint(1);
        $this$paint_u24lambda_u240.setStyle(Paint.Style.STROKE);
        $this$paint_u24lambda_u240.setColor(CarUi.Companion.getACCENT());
        $this$paint_u24lambda_u240.setStrokeWidth(ctx.getResources().getDisplayMetrics().density * 2.5f);
        $this$paint_u24lambda_u240.setStrokeJoin(Paint.Join.ROUND);
        this.paint = $this$paint_u24lambda_u240;
    }

    public final List<Double> getValues() {
        return this.values;
    }

    public final void setValues(List<Double> v) {
        Intrinsics.checkNotNullParameter(v, "v");
        this.values = v;
        invalidate();
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        super.onDraw(canvas);
        if (this.values.size() < 2) {
            return;
        }
        float w = getWidth();
        float h = getHeight();
        Path p = new Path();
        Iterator<T> it = this.values.iterator();
        int i = 0;
        while (it.hasNext()) {
            int i2 = i;
            i++;
            double v = ((Number) it.next()).doubleValue();
            float x = (i2 * w) / 29.0f;
            float y = h - ((float) ((RangesKt.coerceIn(v, 0.0d, 100.0d) / 100.0d) * h));
            if (i2 == 0) {
                p.moveTo(x, y);
            } else {
                p.lineTo(x, y);
            }
        }
        canvas.drawPath(p, this.paint);
    }
}
