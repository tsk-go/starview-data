package com.starpilot.starview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: JournalPages.kt */
public final class Bars extends View {
    private final int color;
    private final List<String> labels;
    private final Paint p;
    private final List<String> topLabels;
    private final List<Float> values;

    /* VF: from Vineflower (jadx: ) */
    public Bars(Context var1, List<Float> var2, List<String> var3, int var4, List<String> var5) {
        super(var1);
        Intrinsics.checkNotNullParameter(var1, "ctx");
        Intrinsics.checkNotNullParameter(var2, "values");
        Intrinsics.checkNotNullParameter(var3, "labels");
        Intrinsics.checkNotNullParameter(var5, "topLabels");
        this.values = var2;
        this.labels = var3;
        this.color = var4;
        this.topLabels = var5;
        this.p = new Paint(1);
    }

    public /* synthetic */ Bars(Context context, List list, List list2, int i, List list3, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, list, list2, i, (i2 & 16) != 0 ? CollectionsKt.emptyList() : list3);
    }

    @Override // android.view.View
    protected void onDraw(Canvas c) {
        float f;
        int i;
        Intrinsics.checkNotNullParameter(c, "c");
        float d = getResources().getDisplayMetrics().density;
        float w = getWidth();
        float f2 = 20;
        float bottom = getHeight() - (f2 * d);
        float top = f2 * d;
        Iterable $this$filter$iv = this.values;
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            float it = ((Number) element$iv$iv).floatValue();
            if ((Float.isInfinite(it) || Float.isNaN(it)) ? false : true) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        Float maxOrNull = CollectionsKt.maxOrNull(destination$iv$iv);
        float m = Math.max(maxOrNull != null ? maxOrNull.floatValue() : 1.0f, 0.001f);
        float slot = w / Math.max(this.values.size(), 1);
        float bw = slot * 0.6f;
        Iterator it2 = this.values.iterator();
        int i2 = 0;
        while (it2.hasNext()) {
            int i3 = i2;
            int i4 = i2 + 1;
            float v = ((Number) it2.next()).floatValue();
            float f3 = 2;
            float x = (i3 * slot) + ((slot - bw) / f3);
            if (!((Float.isInfinite(v) || Float.isNaN(v)) ? false : true) || v <= 0.0f) {
                f = f3;
                i = i3;
                this.p.setColor(Color.rgb(50, 56, 70));
                c.drawRect(x, bottom - (f * d), x + bw, bottom, this.p);
            } else {
                float y = bottom - ((v / m) * (bottom - top));
                this.p.setColor(this.color);
                float f4 = 6;
                f = f3;
                i = i3;
                c.drawRoundRect(x, y, x + bw, bottom, f4 * d, f4 * d, this.p);
                String t = (String) CollectionsKt.getOrNull(this.topLabels, i);
                if (t != null) {
                    this.p.setColor(-1);
                    this.p.setTextSize(13 * d);
                    c.drawText(t, (x + (bw / f)) - (this.p.measureText(t) / f), y - (5 * d), this.p);
                }
            }
            String l = (String) CollectionsKt.getOrNull(this.labels, i);
            if (l != null) {
                this.p.setColor(Color.rgb(150, 160, 180));
                this.p.setTextSize(12 * d);
                c.drawText(l, (x + (bw / f)) - (this.p.measureText(l) / f), getHeight() - (4 * d), this.p);
            }
            i2 = i4;
        }
    }
}
