package com.starpilot.starview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.ViewParent;
import java.util.ArrayList;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

/* compiled from: GxCameras.kt */
final class GxCamerasRegionView extends GxCamerasSnapView {
    private String annotating;
    private float downX;
    private float downY;
    private float[] drag;
    private final ArrayList<float[]> left;
    private boolean moved;
    private Function0<Unit> onChanged;
    private Function0<Unit> onClose;
    private final ArrayList<float[]> right;
    private boolean tracking;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GxCamerasRegionView(Context ctx) {
        super(ctx);
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        this.left = new ArrayList<>();
        this.right = new ArrayList<>();
        this.onChanged = new Function0() { // from class: com.starpilot.starview.GxCamerasRegionView$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit unit;
                unit = Unit.INSTANCE;
                return unit;
            }
        };
        this.onClose = new Function0() { // from class: com.starpilot.starview.GxCamerasRegionView$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit unit;
                unit = Unit.INSTANCE;
                return unit;
            }
        };
    }

    @Override // android.view.View
    public final ArrayList<float[]> getLeft() {
        return this.left;
    }

    @Override // android.view.View
    public final ArrayList<float[]> getRight() {
        return this.right;
    }

    public final String getAnnotating() {
        return this.annotating;
    }

    public final void setAnnotating(String str) {
        this.annotating = str;
    }

    public final Function0<Unit> getOnChanged() {
        return this.onChanged;
    }

    public final void setOnChanged(Function0<Unit> function0) {
        Intrinsics.checkNotNullParameter(function0, "<set-?>");
        this.onChanged = function0;
    }

    public final Function0<Unit> getOnClose() {
        return this.onClose;
    }

    public final void setOnClose(Function0<Unit> function0) {
        Intrinsics.checkNotNullParameter(function0, "<set-?>");
        this.onClose = function0;
    }

    @Override // com.starpilot.starview.GxCamerasSnapView
    public void drawOverlay(Canvas c, Bitmap b) {
        int i;
        int i2;
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(b, "b");
        ArrayList<float[]> arrayList = this.left;
        int argb = Color.argb(64, 13, 110, 253);
        i = GxCamerasKt.GX_CAM_BLUE;
        poly(c, arrayList, argb, i, "LEFT SIDE OF VEHICLE - LEFT HERE", Intrinsics.areEqual(this.annotating, "left"));
        ArrayList<float[]> arrayList2 = this.right;
        int argb2 = Color.argb(64, 253, 126, 20);
        i2 = GxCamerasKt.GX_CAM_ORANGE;
        poly(c, arrayList2, argb2, i2, "RIGHT SIDE OF VEHICLE - RIGHT HERE", Intrinsics.areEqual(this.annotating, "right"));
    }

    private final void poly(Canvas c, List<float[]> pts, int fill, int border, String label, boolean active) {
        if (pts.size() < 2) {
            for (float[] p : pts) {
                dot(c, p[0], p[1], u(7.0f), border, null);
            }
            return;
        }
        Path $this$poly_u24lambda_u242 = new Path();
        $this$poly_u24lambda_u242.moveTo(pts.get(0)[0], pts.get(0)[1]);
        int size = pts.size();
        for (int i = 1; i < size; i++) {
            $this$poly_u24lambda_u242.lineTo(pts.get(i)[0], pts.get(i)[1]);
        }
        int i2 = pts.size();
        if (i2 >= 3) {
            $this$poly_u24lambda_u242.close();
        }
        getPaint().reset();
        getPaint().setAntiAlias(true);
        if (pts.size() >= 3) {
            getPaint().setStyle(Paint.Style.FILL);
            getPaint().setColor(fill);
            c.drawPath($this$poly_u24lambda_u242, getPaint());
        }
        getPaint().setStyle(Paint.Style.STROKE);
        getPaint().setColor(border);
        getPaint().setStrokeWidth(u(2.5f));
        getPaint().setStrokeJoin(Paint.Join.ROUND);
        c.drawPath($this$poly_u24lambda_u242, getPaint());
        int i3 = 0;
        for (float[] p2 : pts) {
            int i4 = i3;
            int i5 = i3 + 1;
            if (active && i4 == 0 && pts.size() >= 3) {
                dot(c, p2[0], p2[1], u(14.0f), Color.argb(90, 255, 255, 255), Integer.valueOf(border));
            }
            dot(c, p2[0], p2[1], u(7.0f), -1, Integer.valueOf(border));
            i3 = i5;
        }
        if (pts.size() >= 3) {
            double d = 0.0d;
            double d2 = 0.0d;
            for (float[] it : pts) {
                d2 += it[0];
            }
            float mx = ((float) d2) / pts.size();
            for (float[] it2 : pts) {
                d += it2[1];
            }
            float my = ((float) d) / pts.size();
            getPaint().reset();
            getPaint().setAntiAlias(true);
            getPaint().setColor(-1);
            getPaint().setTextSize(u(13.0f));
            getPaint().setTypeface(Typeface.create(Typeface.MONOSPACE, 1));
            getPaint().setTextAlign(Paint.Align.CENTER);
            getPaint().setShadowLayer(u(3.0f), 0.0f, 0.0f, -16777216);
            c.drawText(label, mx, u(5.0f) + my, getPaint());
        }
    }

    private final float[] hit(float x, float y, List<float[]> pts) {
        float r = u(26.0f);
        float[] best = null;
        float bd = Float.MAX_VALUE;
        for (float[] p : pts) {
            float d = (float) Math.hypot(p[0] - x, p[1] - y);
            if (d < r && d < bd) {
                bd = d;
                best = p;
            }
        }
        return best;
    }

    /* JADX WARN: Removed duplicated region for block: B:77:0x018d  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean onTouchEvent(MotionEvent e) {
        ArrayList<float[]> arrayList;
        ViewParent parent;
        int gxCamRound;
        int gxCamRound2;
        Intrinsics.checkNotNullParameter(e, "e");
        if (getBmp() == null) {
            return false;
        }
        float x = RangesKt.coerceIn(cx(e.getX()), 0.0f, getCw());
        float y = RangesKt.coerceIn(cy(e.getY()), 0.0f, getCh());
        switch (e.getActionMasked()) {
            case 0:
                String a = this.annotating;
                if (!Intrinsics.areEqual(a, "left")) {
                    if (Intrinsics.areEqual(a, "right") || (r4 = hit(x, y, this.left)) == null) {
                        arrayList = this.right;
                    }
                    this.drag = r4;
                    if (a != null && this.drag == null) {
                        return false;
                    }
                    this.tracking = true;
                    this.moved = false;
                    this.downX = e.getX();
                    this.downY = e.getY();
                    parent = getParent();
                    if (parent != null) {
                        parent.requestDisallowInterceptTouchEvent(true);
                    }
                    return true;
                }
                arrayList = this.left;
                float[] hit = hit(x, y, arrayList);
                this.drag = hit;
                if (a != null) {
                }
                this.tracking = true;
                this.moved = false;
                this.downX = e.getX();
                this.downY = e.getY();
                parent = getParent();
                if (parent != null) {
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
                if (this.moved) {
                    if (this.drag != null) {
                        this.onChanged.invoke();
                    }
                    this.drag = null;
                    return true;
                }
                this.drag = null;
                String a2 = this.annotating;
                if (a2 == null || cx(e.getX()) < 0.0f || cy(e.getY()) < 0.0f || cx(e.getX()) > getCw() || cy(e.getY()) > getCh()) {
                    return true;
                }
                ArrayList pts = Intrinsics.areEqual(a2, "left") ? this.left : this.right;
                if (pts.size() >= 3 && ((float) Math.hypot(x - pts.get(0)[0], y - pts.get(0)[1])) < u(26.0f)) {
                    this.onClose.invoke();
                    return true;
                }
                if (pts.size() >= 64) {
                    return true;
                }
                gxCamRound = GxCamerasKt.gxCamRound(x);
                gxCamRound2 = GxCamerasKt.gxCamRound(y);
                pts.add(new float[]{gxCamRound, gxCamRound2});
                this.onChanged.invoke();
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
                    d[0] = x;
                    d[1] = y;
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
