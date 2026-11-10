package com.starpilot.starview;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ViewParent;
import android.widget.ImageView;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

/* compiled from: ZoomImageView.kt */
public final class ZoomImageView extends ImageView {
    private Matrix base;
    private final GestureDetector gestures;
    private final Matrix m;
    private final float maxZoom;
    private final ScaleGestureDetector scaler;
    private float zoom;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ZoomImageView(Context ctx) {
        super(ctx);
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        this.m = new Matrix();
        this.base = new Matrix();
        this.zoom = 1.0f;
        this.maxZoom = 6.0f;
        setScaleType(ImageView.ScaleType.MATRIX);
        this.scaler = new ScaleGestureDetector(ctx, new ScaleGestureDetector.SimpleOnScaleGestureListener() { // from class: com.starpilot.starview.ZoomImageView$scaler$1
            @Override // android.view.ScaleGestureDetector.SimpleOnScaleGestureListener, android.view.ScaleGestureDetector.OnScaleGestureListener
            public boolean onScale(ScaleGestureDetector d) {
                Intrinsics.checkNotNullParameter(d, "d");
                ZoomImageView.this.zoomBy(d.getScaleFactor(), d.getFocusX(), d.getFocusY());
                return true;
            }
        });
        this.gestures = new GestureDetector(ctx, new GestureDetector.SimpleOnGestureListener() { // from class: com.starpilot.starview.ZoomImageView$gestures$1
            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public boolean onDown(MotionEvent e) {
                Intrinsics.checkNotNullParameter(e, "e");
                return true;
            }

            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float dx, float dy) {
                float f;
                Matrix matrix;
                Intrinsics.checkNotNullParameter(e2, "e2");
                f = ZoomImageView.this.zoom;
                if (f <= 1.01f) {
                    return false;
                }
                matrix = ZoomImageView.this.m;
                matrix.postTranslate(-dx, -dy);
                ZoomImageView.this.clamp();
                return true;
            }

            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
            public boolean onDoubleTap(MotionEvent e) {
                float f;
                Intrinsics.checkNotNullParameter(e, "e");
                f = ZoomImageView.this.zoom;
                if (f > 1.01f) {
                    ZoomImageView.this.fit();
                    return true;
                }
                ZoomImageView.this.zoomBy(2.5f, e.getX(), e.getY());
                return true;
            }
        });
    }

    @Override // android.widget.ImageView
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        post(new Runnable() { // from class: com.starpilot.starview.ZoomImageView$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ZoomImageView.this.fit();
            }
        });
    }

    @Override // android.view.View
    protected void onSizeChanged(int w, int h, int ow, int oh) {
        super.onSizeChanged(w, h, ow, oh);
        fit();
    }

    public final void fit() {
        Drawable d = getDrawable();
        if (d == null || getWidth() == 0 || getHeight() == 0) {
            return;
        }
        Matrix $this$fit_u24lambda_u241 = new Matrix();
        $this$fit_u24lambda_u241.setRectToRect(new RectF(0.0f, 0.0f, d.getIntrinsicWidth(), d.getIntrinsicHeight()), new RectF(0.0f, 0.0f, getWidth(), getHeight()), Matrix.ScaleToFit.CENTER);
        this.base = $this$fit_u24lambda_u241;
        this.m.set(this.base);
        this.zoom = 1.0f;
        setImageMatrix(this.m);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:11:0x0063  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x006f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void clamp() {
        float dx;
        float width;
        float f;
        float dx2;
        float dy;
        float height;
        float f2;
        Drawable d = getDrawable();
        if (d == null) {
            return;
        }
        RectF r = new RectF(0.0f, 0.0f, d.getIntrinsicWidth(), d.getIntrinsicHeight());
        this.m.mapRect(r);
        if (r.width() <= getWidth()) {
            width = getWidth() / 2.0f;
            f = r.centerX();
        } else {
            float dx3 = r.left;
            float dx4 = dx3 > 0.0f ? -r.left : 0.0f;
            if (r.right >= getWidth()) {
                dx = dx4;
                dx2 = r.height();
                if (dx2 > getHeight()) {
                    height = getHeight() / 2.0f;
                    f2 = r.centerY();
                } else {
                    float dy2 = r.top;
                    float dy3 = dy2 > 0.0f ? -r.top : 0.0f;
                    if (r.bottom >= getHeight()) {
                        dy = dy3;
                        this.m.postTranslate(dx, dy);
                        setImageMatrix(this.m);
                    }
                    height = getHeight();
                    f2 = r.bottom;
                }
                dy = height - f2;
                this.m.postTranslate(dx, dy);
                setImageMatrix(this.m);
            }
            width = getWidth();
            f = r.right;
        }
        dx = width - f;
        dx2 = r.height();
        if (dx2 > getHeight()) {
        }
        dy = height - f2;
        this.m.postTranslate(dx, dy);
        setImageMatrix(this.m);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zoomBy(float f0, float fx, float fy) {
        float f = RangesKt.coerceIn(this.zoom * f0, 1.0f, this.maxZoom) / this.zoom;
        this.zoom *= f;
        this.m.postScale(f, f, fx, fy);
        clamp();
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent e) {
        ViewParent parent;
        Intrinsics.checkNotNullParameter(e, "e");
        this.scaler.onTouchEvent(e);
        this.gestures.onTouchEvent(e);
        if (this.zoom > 1.01f && (parent = getParent()) != null) {
            parent.requestDisallowInterceptTouchEvent(true);
        }
        return true;
    }
}
