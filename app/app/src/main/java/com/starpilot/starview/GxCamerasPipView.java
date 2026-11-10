package com.starpilot.starview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.ViewParent;
import kotlin.Pair;
import kotlin.Triple;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

/* compiled from: GxCameras.kt */
final class GxCamerasPipView extends GxCamerasSnapView {
    private String armSide;
    private float downX;
    private float downY;
    private float[] drag;
    private boolean dragLeft;
    private boolean isC4;
    private float[] leftC;
    private boolean moved;
    private Function0<Unit> onChanged;
    private Function0<Unit> onPlaced;
    private Function1<? super String, Unit> onWrongHalf;
    private float[] rightC;
    private boolean tracking;
    private float zoom;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GxCamerasPipView(Context ctx) {
        super(ctx);
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        this.zoom = 240.0f;
        this.onChanged = new Function0() { // from class: com.starpilot.starview.GxCamerasPipView$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit unit;
                unit = Unit.INSTANCE;
                return unit;
            }
        };
        this.onPlaced = new Function0() { // from class: com.starpilot.starview.GxCamerasPipView$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit unit;
                unit = Unit.INSTANCE;
                return unit;
            }
        };
        this.onWrongHalf = new Function1() { // from class: com.starpilot.starview.GxCamerasPipView$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit onWrongHalf$lambda$2;
                onWrongHalf$lambda$2 = GxCamerasPipView.onWrongHalf$lambda$2((String) obj);
                return onWrongHalf$lambda$2;
            }
        };
    }

    public final float[] getLeftC() {
        return this.leftC;
    }

    public final void setLeftC(float[] fArr) {
        this.leftC = fArr;
    }

    public final float[] getRightC() {
        return this.rightC;
    }

    public final void setRightC(float[] fArr) {
        this.rightC = fArr;
    }

    public final float getZoom() {
        return this.zoom;
    }

    public final void setZoom(float f) {
        this.zoom = f;
    }

    public final String getArmSide() {
        return this.armSide;
    }

    public final void setArmSide(String str) {
        this.armSide = str;
    }

    /* renamed from: isC4, reason: from getter */
    public final boolean getIsC4() {
        return this.isC4;
    }

    public final void setC4(boolean z) {
        this.isC4 = z;
    }

    public final Function0<Unit> getOnChanged() {
        return this.onChanged;
    }

    public final void setOnChanged(Function0<Unit> function0) {
        Intrinsics.checkNotNullParameter(function0, "<set-?>");
        this.onChanged = function0;
    }

    public final Function0<Unit> getOnPlaced() {
        return this.onPlaced;
    }

    public final void setOnPlaced(Function0<Unit> function0) {
        Intrinsics.checkNotNullParameter(function0, "<set-?>");
        this.onPlaced = function0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onWrongHalf$lambda$2(String it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return Unit.INSTANCE;
    }

    public final Function1<String, Unit> getOnWrongHalf() {
        return this.onWrongHalf;
    }

    public final void setOnWrongHalf(Function1<? super String, Unit> function1) {
        Intrinsics.checkNotNullParameter(function1, "<set-?>");
        this.onWrongHalf = function1;
    }

    private final float nW() {
        return getBmp() != null ? r0.getWidth() : 1920;
    }

    private final float nH() {
        return getBmp() != null ? r0.getHeight() : 1080;
    }

    @Override // com.starpilot.starview.GxCamerasSnapView
    public void drawOverlay(Canvas c, Bitmap b) {
        int i;
        int i2;
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(b, "b");
        float f = 2;
        float half = this.zoom / f;
        float[] fArr = this.leftC;
        i = GxCamerasKt.GX_CAM_BLUE;
        int color = 0;
        float[] fArr2 = this.rightC;
        i2 = GxCamerasKt.GX_CAM_ORANGE;
        for (Triple triple : CollectionsKt.listOf((Object[]) new Triple[]{new Triple(fArr, Integer.valueOf(i), "LEFT SIDE OF VEHICLE - LEFT HERE"), new Triple(fArr2, Integer.valueOf(i2), "RIGHT SIDE OF VEHICLE - RIGHT HERE")})) {
            float[] center = (float[]) triple.component1();
            int color2 = ((Number) triple.component2()).intValue();
            String label = (String) triple.component3();
            if (center != null) {
                if (this.isC4) {
                    c4(c, b, center, color2);
                } else {
                    c3(c, b, center, color2);
                }
                dot(c, center[color], center[1], u(6.0f), -1, Integer.valueOf(color2));
                getPaint().reset();
                getPaint().setAntiAlias(true);
                getPaint().setColor(color2);
                getPaint().setTextSize(u(13.0f));
                getPaint().setTypeface(Typeface.create(Typeface.MONOSPACE, 1));
                getPaint().setTextAlign(Paint.Align.CENTER);
                getPaint().setShadowLayer(u(3.0f), 0.0f, 0.0f, -16777216);
                c.drawText(label, center[0], (center[1] - half) - u(8.0f), getPaint());
                color = 0;
            }
        }
        String arm = this.armSide;
        if (arm != null) {
            getPaint().reset();
            getPaint().setAntiAlias(true);
            getPaint().setColor(-1);
            getPaint().setTextSize(u(15.0f));
            getPaint().setTypeface(Typeface.create(Typeface.MONOSPACE, 1));
            getPaint().setTextAlign(Paint.Align.CENTER);
            getPaint().setShadowLayer(u(3.0f), 0.0f, 0.0f, -16777216);
            c.drawText(Intrinsics.areEqual(arm, "left") ? "Tap LEFT HERE for the LEFT SIDE OF VEHICLE" : "Tap RIGHT HERE for the RIGHT SIDE OF VEHICLE", getCw() / f, getCh() - u(18.0f), getPaint());
        }
    }

    private final void c3(Canvas c, Bitmap b, float[] p, int color) {
        Pair pair = TuplesKt.to(Float.valueOf(p[0]), Float.valueOf(p[1]));
        float x = ((Number) pair.component1()).floatValue();
        float y = ((Number) pair.component2()).floatValue();
        float f = 2;
        float half = this.zoom / f;
        float ncx = ((getCw() - x) * nW()) / getCw();
        float ncy = (nH() * y) / getCh();
        float nz = (this.zoom * nW()) / getCw();
        Path clip = new Path();
        clip.addCircle(x, y, half, Path.Direction.CW);
        c.save();
        c.clipPath(clip);
        getPaint().reset();
        getPaint().setColor(-16777216);
        c.drawCircle(x, y, half, getPaint());
        c.scale(-1.0f, 1.0f, x, 0.0f);
        drawCrop(c, b, new RectF(ncx - (nz / f), ncy - (nz / f), (nz / f) + ncx, (nz / f) + ncy), new RectF(x - half, y - half, x + half, y + half));
        c.restore();
        getPaint().reset();
        getPaint().setAntiAlias(true);
        getPaint().setStyle(Paint.Style.STROKE);
        getPaint().setColor(color);
        getPaint().setStrokeWidth(u(2.5f));
        c.drawCircle(x, y, half, getPaint());
    }

    private final void c4(Canvas c, Bitmap b, float[] p, int color) {
        Pair pair = TuplesKt.to(Float.valueOf(p[0]), Float.valueOf(p[1]));
        float x = ((Number) pair.component1()).floatValue();
        float y = ((Number) pair.component2()).floatValue();
        float w = Math.max(60.0f, Math.min(this.zoom * 1.1f, getCw() * 0.55f));
        float h = w / 1.9833333f;
        if (h > getCh() * 0.5f) {
            h = getCh() * 0.5f;
            w = h * 1.9833333f;
        }
        float f = 2;
        RectF dst = new RectF(x - (w / f), y - (h / f), (w / f) + x, (h / f) + y);
        float ncx = ((getCw() - x) * nW()) / getCw();
        float ncy = (nH() * y) / getCh();
        float nz = (this.zoom * nW()) / getCw();
        float sh = nz / (w / h);
        float r = Math.min(w, h) * 0.22f;
        Path $this$c4_u24lambda_u244 = new Path();
        $this$c4_u24lambda_u244.addRoundRect(dst, r, r, Path.Direction.CW);
        c.save();
        c.clipPath($this$c4_u24lambda_u244);
        getPaint().reset();
        getPaint().setColor(-16777216);
        c.drawRect(dst, getPaint());
        c.scale(-1.0f, 1.0f, x, 0.0f);
        drawCrop(c, b, new RectF(ncx - (nz / f), ncy - (sh / f), ncx + (nz / f), (sh / f) + ncy), dst);
        c.restore();
        getPaint().reset();
        getPaint().setAntiAlias(true);
        getPaint().setStyle(Paint.Style.STROKE);
        getPaint().setColor(color);
        getPaint().setStrokeWidth(u(2.5f));
        c.drawRoundRect(dst, r, r, getPaint());
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent e) {
        int gxCamRound;
        int gxCamRound2;
        Intrinsics.checkNotNullParameter(e, "e");
        if (getBmp() == null) {
            return false;
        }
        float x = cx(e.getX());
        float y = cy(e.getY());
        switch (e.getActionMasked()) {
            case 0:
                this.drag = null;
                if (this.armSide == null) {
                    float r = u(36.0f);
                    float[] l = this.leftC;
                    float[] rc = this.rightC;
                    if (l != null && ((float) Math.hypot(l[0] - x, l[1] - y)) < r) {
                        this.drag = l;
                        this.dragLeft = true;
                    } else if (rc != null && ((float) Math.hypot(rc[0] - x, rc[1] - y)) < r) {
                        this.drag = rc;
                        this.dragLeft = false;
                    }
                    if (this.drag == null) {
                        return false;
                    }
                }
                this.tracking = true;
                this.moved = false;
                this.downX = e.getX();
                this.downY = e.getY();
                ViewParent parent = getParent();
                if (parent != null) {
                    parent.requestDisallowInterceptTouchEvent(true);
                }
                return true;
            case 1:
                if (!this.tracking) {
                    return false;
                }
                this.tracking = false;
                ViewParent parent2 = getParent();
                if (parent2 != null) {
                    parent2.requestDisallowInterceptTouchEvent(false);
                }
                if (this.drag != null) {
                    this.drag = null;
                    if (this.moved) {
                        this.onChanged.invoke();
                    }
                    return true;
                }
                String arm = this.armSide;
                if (arm == null || x < 0.0f || y < 0.0f || x > getCw() || y > getCh()) {
                    return true;
                }
                boolean leftHalf = x < getCw() / ((float) 2);
                if ((Intrinsics.areEqual(arm, "left") && !leftHalf) || (Intrinsics.areEqual(arm, "right") && leftHalf)) {
                    this.onWrongHalf.invoke(arm);
                    invalidate();
                    return true;
                }
                gxCamRound = GxCamerasKt.gxCamRound(x);
                gxCamRound2 = GxCamerasKt.gxCamRound(y);
                float[] p = {gxCamRound, gxCamRound2};
                if (Intrinsics.areEqual(arm, "left")) {
                    this.leftC = p;
                } else {
                    this.rightC = p;
                }
                this.armSide = null;
                this.onPlaced.invoke();
                return true;
            case 2:
                if (!this.tracking) {
                    return false;
                }
                if (!this.moved && ((float) Math.hypot(e.getX() - this.downX, e.getY() - this.downY)) > 10 * getDensity()) {
                    this.moved = true;
                }
                float[] d = this.drag;
                if (this.moved && d != null) {
                    d[0] = this.dragLeft ? RangesKt.coerceIn(x, 0.0f, (getCw() / 2) - 1) : RangesKt.coerceIn(x, getCw() / 2, getCw());
                    d[1] = RangesKt.coerceIn(y, 0.0f, getCh());
                    invalidate();
                }
                return true;
            case 3:
                this.tracking = false;
                this.drag = null;
                ViewParent parent3 = getParent();
                if (parent3 != null) {
                    parent3.requestDisallowInterceptTouchEvent(false);
                }
                return true;
            default:
                return this.tracking;
        }
    }
}
