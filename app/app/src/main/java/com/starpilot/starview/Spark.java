package com.starpilot.starview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

/* compiled from: JournalPages.kt */
public final class Spark extends View {
    private final int color;
    private final List<String> labels;
    private final float maxV;
    private final Paint p;
    private final List<Float> values;
    private final Float warnAt;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Spark(Context ctx, List<Float> values, float maxV, int color, Float warnAt, List<String> labels) {
        super(ctx);
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(values, "values");
        Intrinsics.checkNotNullParameter(labels, "labels");
        this.values = values;
        this.maxV = maxV;
        this.color = color;
        this.warnAt = warnAt;
        this.labels = labels;
        this.p = new Paint(1);
    }

    public /* synthetic */ Spark(Context context, List list, float f, int i, Float f2, List list2, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, list, f, i, (i2 & 16) != 0 ? null : f2, (i2 & 32) != 0 ? CollectionsKt.emptyList() : list2);
    }

    @Override // android.view.View
    protected void onDraw(Canvas c) {
        int i;
        Intrinsics.checkNotNullParameter(c, "c");
        float d = getResources().getDisplayMetrics().density;
        float w = getWidth();
        float h = getHeight() - (!this.labels.isEmpty() ? 18 * d : 0.0f);
        this.p.setColor(Color.rgb(34, 40, 55));
        this.p.setStyle(Paint.Style.FILL);
        float f = 10;
        c.drawRoundRect(0.0f, 0.0f, w, h, f * d, f * d, this.p);
        if (this.warnAt != null) {
            this.p.setColor(Color.rgb(120, 60, 40));
            this.p.setStrokeWidth(d);
            float y = h - ((this.warnAt.floatValue() / this.maxV) * h);
            c.drawLine(0.0f, y, w, y, this.p);
        }
        Iterable $this$filter$iv = this.values;
        Collection destination$iv$iv = new ArrayList();
        Iterator it = $this$filter$iv.iterator();
        while (true) {
            i = 1;
            if (!it.hasNext()) {
                break;
            }
            Object element$iv$iv = it.next();
            float it2 = ((Number) element$iv$iv).floatValue();
            if ((Float.isInfinite(it2) || Float.isNaN(it2)) ? false : true) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        List vals = (List) destination$iv$iv;
        if (this.values.size() >= 2 && !vals.isEmpty()) {
            Path path = new Path();
            boolean started = false;
            Iterator<T> it3 = this.values.iterator();
            int i2 = 0;
            while (it3.hasNext()) {
                int i3 = i2;
                i2 += i;
                float v = ((Number) it3.next()).floatValue();
                if (((Float.isInfinite(v) || Float.isNaN(v)) ? 0 : i) == 0) {
                    started = false;
                } else {
                    float x = (i3 * w) / (this.values.size() - i);
                    float y2 = (h - (RangesKt.coerceIn(v / this.maxV, 0.0f, 1.0f) * (h - (6 * d)))) - (3 * d);
                    if (started) {
                        path.lineTo(x, y2);
                        i = 1;
                    } else {
                        path.moveTo(x, y2);
                        started = true;
                        i = 1;
                    }
                }
            }
            this.p.setStyle(Paint.Style.STROKE);
            this.p.setStrokeWidth(2.5f * d);
            this.p.setColor(this.color);
            c.drawPath(path, this.p);
            this.p.setStyle(Paint.Style.FILL);
        }
        if (!this.labels.isEmpty()) {
            this.p.setColor(Color.rgb(150, 160, 180));
            this.p.setTextSize(12 * d);
            int i4 = 0;
            for (String l : this.labels) {
                int i5 = i4;
                i4++;
                c.drawText(l, this.labels.size() == 1 ? 0.0f : (i5 * (w - this.p.measureText(l))) / (this.labels.size() - 1), (14 * d) + h, this.p);
            }
        }
    }
}
