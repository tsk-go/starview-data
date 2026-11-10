package com.starpilot.starview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

/* compiled from: RtkBars.kt */
public final class BarView extends View {
    private final Paint back;
    private final Paint fill;
    private float fraction;
    private final RectF r;

    /* VF: from Vineflower (jadx: ) */
    public BarView(Context var1) {
        super(var1);
        Intrinsics.checkNotNullParameter(var1, "ctx");
        Paint var2 = new Paint(1);
        var2.setColor(Color.rgb(44, 52, 64));
        this.back = var2;
        this.fill = new Paint(1);
        this.r = new RectF();
    }

    public final float getFraction() {
        return this.fraction;
    }

    public final void setFraction(float v) {
        float c = RangesKt.coerceIn(v, 0.0f, 1.0f);
        if (c == this.fraction) {
            return;
        }
        this.fraction = c;
        invalidate();
    }

    @Override // android.view.View
    protected void onDraw(Canvas c) {
        Intrinsics.checkNotNullParameter(c, "c");
        float h = getHeight();
        float w = getWidth();
        float rad = h / 2;
        this.r.set(0.0f, 0.0f, w, h);
        c.drawRoundRect(this.r, rad, rad, this.back);
        if (this.fraction <= 0.0f) {
            return;
        }
        this.fill.setColor(this.fraction >= 1.0f ? CarUi.Companion.getACCENT() : Color.rgb(96, 165, 250));
        this.r.set(0.0f, 0.0f, Math.max(h, this.fraction * w), h);
        c.drawRoundRect(this.r, rad, rad, this.fill);
    }
}
