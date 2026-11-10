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
import androidx.core.graphics.ColorUtils;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import kotlin.ranges.RangesKt;

/* compiled from: HudViews.kt */
public final class DmView extends View {
    private final Bitmap bg;
    private final Bitmap cone;
    private float fadeF;
    private float greenF;
    private long lastNs;
    private final Paint p;
    private final Bitmap person;
    private float pitchF;
    private float rotF;
    public Telemetry t;
    private float yawF;

    /* VF: from Vineflower (jadx: ) */
    public DmView(Context var1, AttributeSet var2) {
        super(var1, var2);
        Intrinsics.checkNotNullParameter(var1, "ctx");
        this.bg = BitmapFactory.decodeResource(this.getResources(), R.drawable.dm_background);
        this.person = BitmapFactory.decodeResource(this.getResources(), R.drawable.dm_person);
        this.cone = BitmapFactory.decodeResource(this.getResources(), R.drawable.dm_cone);
        this.p = new Paint(3);
        this.greenF = 1.0F;
    }

    public /* synthetic */ DmView(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
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
        Intrinsics.checkNotNullParameter(c, "c");
        if (this.t == null) {
            return;
        }
        long now = System.nanoTime();
        float dt = this.lastNs == 0 ? 0.05f : RangesKt.coerceIn((now - this.lastNs) / 1.0E9f, 0.001f, 0.2f);
        this.lastNs = now;
        boolean hidden = (getT().s("dm_icon") && getT().getStarted()) ? false : true;
        float targetFade = hidden ? 0.0f : !getT().getDmPolicyVision() ? 0.35f : 1.0f;
        this.fadeF = onDraw$lp(dt, this.fadeF, targetFade, 0.1f);
        if (this.fadeF < 0.02f) {
            return;
        }
        float facePitch = getT().getDmPitch() + ((float) Math.toRadians(6.0d));
        float faceYaw = -getT().getDmYaw();
        this.pitchF = onDraw$lp(dt, this.pitchF, facePitch, 0.05f);
        this.yawF = onDraw$lp(dt, this.yawF, faceYaw, 0.05f);
        float f = 2;
        float rot = (float) Math.toDegrees(Math.atan2(this.pitchF * f, this.yawF));
        float diff = (((((rot - this.rotF) + 180.0f) % 360.0f) + 360.0f) % 360.0f) - 180.0f;
        this.rotF = onDraw$lp(dt, this.rotF, this.rotF + diff, 0.1f);
        this.greenF = onDraw$lp(dt, this.greenF, getT().getDmAwareness() < 95 ? 0.0f : 1.0f, 0.1f);
        float s = Math.min(getWidth(), getHeight());
        float cx = getWidth() / 2.0f;
        float cy = getHeight() / 2.0f;
        float emoji = 0.6666667f * s;
        float coneS = 0.578125f * s;
        float f2 = 255;
        this.p.setAlpha(MathKt.roundToInt(this.fadeF * f2));
        float rot2 = cy - (emoji / f);
        float diff2 = cy + (emoji / f);
        c.drawBitmap(this.bg, (Rect) null, new RectF(cx - (emoji / f), rot2, cx + (emoji / f), diff2), this.p);
        this.p.setAlpha(MathKt.roundToInt(this.fadeF * 229.5f));
        c.drawBitmap(this.person, (Rect) null, new RectF(cx - (coneS / f), cy - (coneS / f), (coneS / f) + cx, cy + (coneS / f)), this.p);
        if (getT().getDmPolicyVision()) {
            int col = ColorUtils.blendARGB(Color.rgb(255, 115, 0), Color.rgb(0, 255, 64), this.greenF);
            this.p.setColorFilter(new PorterDuffColorFilter(col, PorterDuff.Mode.SRC_IN));
            this.p.setAlpha(MathKt.roundToInt(f2 * this.fadeF));
            c.save();
            c.rotate(this.rotF - 90.0f, cx, cy);
            c.drawBitmap(this.cone, (Rect) null, new RectF(cx - (coneS / f), cy - (coneS / f), (coneS / f) + cx, (coneS / f) + cy), this.p);
            c.restore();
            this.p.setColorFilter(null);
        }
        this.p.setAlpha(255);
    }

    private static final float onDraw$lp(float dt, float cur, float target, float tau) {
        return ((target - cur) * (dt / (tau + dt))) + cur;
    }
}
