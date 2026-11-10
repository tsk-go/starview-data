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
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;

/* compiled from: GxLogs.kt */
final class GxLogsSpark extends View {
    private final Paint paint;
    private List<Double> values;

    /* VF: from Vineflower (jadx: ) */
    public GxLogsSpark(Context var1) {
        super(var1);
        Intrinsics.checkNotNullParameter(var1, "ctx");
        this.values = CollectionsKt.emptyList();
        Paint var2 = new Paint(1);
        var2.setStyle(android.graphics.Paint.Style.STROKE);
        var2.setColor(CarUi.Companion.getACCENT());
        var2.setStrokeWidth(var1.getResources().getDisplayMetrics().density * 2.5F);
        var2.setStrokeJoin(android.graphics.Paint.Join.ROUND);
        this.paint = var2;
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
        Iterator it = this.values.iterator();
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
