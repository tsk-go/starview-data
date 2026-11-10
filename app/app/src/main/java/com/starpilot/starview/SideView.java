package com.starpilot.starview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import android.graphics.Paint.Style;

/* compiled from: SideCam.kt */
public final class SideView extends View {
    private final Paint p;
    private final Paint ring;
    private String side;
    public Telemetry t;

    /* VF: from Vineflower (jadx: ) */
    public SideView(Context var1, AttributeSet var2) {
        super(var1, var2);
        Intrinsics.checkNotNullParameter(var1, "ctx");
        this.side = "left";
        this.p = new Paint(3);
        Paint var3 = new Paint(1);
        var3.setStyle(android.graphics.Paint.Style.STROKE);
        this.ring = var3;
    }

    public /* synthetic */ SideView(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
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

    public final String getSide() {
        return this.side;
    }

    public final void setSide(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.side = str;
    }

    @Override // android.view.View
    protected void onDraw(Canvas c) {
        Intrinsics.checkNotNullParameter(c, "c");
        if (this.t == null) {
            return;
        }
        Bitmap img = SideCam.INSTANCE.getImages().get(this.side);
        float s = Math.min(getWidth(), getHeight());
        float cx = getWidth() / 2.0f;
        float cy = getHeight() / 2.0f;
        boolean fresh = img != null && System.currentTimeMillis() - SideCam.INSTANCE.getImageAtMs() < 1500;
        if (!fresh) {
            this.p.setColor(Color.argb(150, 0, 0, 0));
            c.drawCircle(cx, cy, s / 2, this.p);
        } else {
            Intrinsics.checkNotNull(img);
            float f = 2;
            c.drawBitmap(img, (Rect) null, new RectF(cx - (s / f), cy - (s / f), (s / f) + cx, (s / f) + cy), this.p);
        }
        boolean warning = Intrinsics.areEqual(this.side, "left") ? getT().getLeftBlindspot() : getT().getRightBlindspot();
        this.ring.setStrokeWidth(((3.0f * s) / 600.0f) * 2.0f);
        this.ring.setColor(warning ? Color.rgb(255, 60, 60) : Color.rgb(94, 229, 238));
        float f2 = 2;
        c.drawCircle(cx, cy, (s / f2) - (this.ring.getStrokeWidth() / f2), this.ring);
    }
}
