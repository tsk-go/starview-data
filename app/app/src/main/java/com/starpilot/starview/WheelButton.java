package com.starpilot.starview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: HudViews.kt */
public final class WheelButton extends View {
    private final Bitmap exp;
    private boolean isDown;
    private final Paint p;
    public Telemetry t;
    private final WheelTint tint;
    private final Bitmap wheel;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WheelButton(Context ctx, AttributeSet attrs) {
        super(ctx, attrs);
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        this.wheel = BitmapFactory.decodeResource(getResources(), R.drawable.ic_wheel);
        this.exp = BitmapFactory.decodeResource(getResources(), R.drawable.ic_experimental);
        this.p = new Paint(3);
        this.tint = new WheelTint();
    }

    public /* synthetic */ WheelButton(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
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

    /* renamed from: isDown, reason: from getter */
    public final boolean getIsDown() {
        return this.isDown;
    }

    public final void setDown(boolean z) {
        this.isDown = z;
    }

    @Override // android.view.View
    protected void onDraw(Canvas c) {
        int bgColor;
        Intrinsics.checkNotNullParameter(c, "c");
        if (this.t == null) {
            return;
        }
        float s = Math.min(getWidth(), getHeight());
        float cx = getWidth() / 2.0f;
        float cy = getHeight() / 2.0f;
        boolean z = false;
        if (getT().tBool("simple_mode", false)) {
            bgColor = Color.argb(166, 0, 0, 0);
        } else if (getT().mBool("SwitchbackModeEnabled", false)) {
            bgColor = Color.rgb(139, 108, 197);
        } else if (getT().getAolEnabled() && !getT().getEnabled()) {
            bgColor = Color.rgb(10, 186, 181);
        } else if (getT().getTrafficMode() && getT().getEnabled()) {
            bgColor = Color.rgb(201, 34, 49);
        } else if (getT().getExperimentalMode() && getT().getEnabled()) {
            bgColor = Color.rgb(218, 111, 37);
        } else {
            bgColor = getT().getEnabled() ? Color.rgb(22, 127, 64) : Color.argb(166, 0, 0, 0);
        }
        this.p.setStyle(Paint.Style.FILL);
        this.p.setColor(bgColor);
        this.p.setAlpha(this.isDown ? 180 : Color.alpha(bgColor));
        float f = 2;
        c.drawCircle(cx, cy, s / f, this.p);
        WheelTint wheelTint = this.tint;
        float aEgo = getT().getAEgo();
        if (getT().s("wheel_tint") && getT().getStarted()) {
            z = true;
        }
        int col = wheelTint.update(aEgo, z, System.nanoTime());
        this.p.setColorFilter(col != -1 ? new PorterDuffColorFilter(col, PorterDuff.Mode.MULTIPLY) : null);
        Bitmap icon = getT().getExperimentalMode() ? this.exp : this.wheel;
        float iconS = 0.75f * s;
        this.p.setAlpha((getT().getEngageable() || getT().getEnabled()) ? 255 : 120);
        c.save();
        if (!getT().getExperimentalMode() && getT().s("rotating_wheel")) {
            c.rotate(-getT().getSteeringAngleDeg(), cx, cy);
        }
        c.drawBitmap(icon, (Rect) null, new RectF(cx - (iconS / f), cy - (iconS / f), (iconS / f) + cx, (iconS / f) + cy), this.p);
        c.restore();
        this.p.setAlpha(255);
        this.p.setColorFilter(null);
    }
}
