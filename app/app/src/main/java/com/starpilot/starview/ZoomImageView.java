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
import android.view.GestureDetector.OnGestureListener;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;
import android.widget.ImageView.ScaleType;

/* compiled from: ZoomImageView.kt */
public final class ZoomImageView extends ImageView {
    private Matrix base;
    private final GestureDetector gestures;
    private final Matrix m;
    private final float maxZoom;
    private final ScaleGestureDetector scaler;
    private float zoom;

    /* VF: from Vineflower (jadx: ) */
    public ZoomImageView(Context var1) {
        super(var1);
        Intrinsics.checkNotNullParameter(var1, "ctx");
        this.m = new Matrix();
        this.base = new Matrix();
        this.zoom = 1.0F;
        this.maxZoom = 6.0F;
        this.setScaleType(ScaleType.MATRIX);
        this.scaler = new ScaleGestureDetector(var1, (OnScaleGestureListener)(new SimpleOnScaleGestureListener() {
            final ZoomImageView this$0 = ZoomImageView.this;

            public boolean onScale(ScaleGestureDetector var1) {
                Intrinsics.checkNotNullParameter(var1, "d");
                this.this$0.zoomBy(var1.getScaleFactor(), var1.getFocusX(), var1.getFocusY());
                return true;
            }
        }));
        this.gestures = new GestureDetector(var1, (OnGestureListener)(new SimpleOnGestureListener() {
            final ZoomImageView this$0 = ZoomImageView.this;

            public boolean onDoubleTap(MotionEvent var1) {
                Intrinsics.checkNotNullParameter(var1, "e");
                if (this.this$0.zoom > 1.01F) {
                    this.this$0.fit();
                } else {
                    this.this$0.zoomBy(2.5F, var1.getX(), var1.getY());
                }

                return true;
            }

            public boolean onDown(MotionEvent var1) {
                Intrinsics.checkNotNullParameter(var1, "e");
                return true;
            }

            public boolean onScroll(MotionEvent var1, MotionEvent var2, float var3, float var4) {
                Intrinsics.checkNotNullParameter(var2, "e2");
                if (this.this$0.zoom <= 1.01F) {
                    return false;
                } else {
                    this.this$0.m.postTranslate(-var3, -var4);
                    this.this$0.clamp();
                    return true;
                }
            }
        }));
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

    /* VF: from Vineflower (jadx: Removed duplicated region;incorrect) */
    public final void clamp() {
        RectF var10 = null; // declarations Vineflower left out
        Drawable var4 = this.getDrawable();
        if (var4 != null) {
            float var3;
            float var8;
            label37: {
                var10 = new RectF(0.0F, 0.0F, var4.getIntrinsicWidth(), var4.getIntrinsicHeight());
                this.m.mapRect(var10);
                float var1 = 0.0F;
                var3 = 0.0F;
                if (var10.width() <= this.getWidth()) {
                    var8 = this.getWidth() / 2.0F;
                    var1 = var10.centerX();
                } else {
                    if (var10.left > 0.0F) {
                        var1 = -var10.left;
                    }

                    if (!(var10.right < this.getWidth())) {
                        var8 = var1;
                        break label37;
                    }

                    var8 = this.getWidth();
                    var1 = var10.right;
                }

                var8 -= var1;
            }

            float var7;
            label30: {
                if (var10.height() <= this.getHeight()) {
                    var7 = this.getHeight() / 2.0F;
                    var3 = var10.centerY();
                } else {
                    var7 = var3;
                    if (var10.top > 0.0F) {
                        var7 = -var10.top;
                    }

                    if (!(var10.bottom < this.getHeight())) {
                        break label30;
                    }

                    var7 = this.getHeight();
                    var3 = var10.bottom;
                }

                var7 -= var3;
            }

            this.m.postTranslate(var8, var7);
            this.setImageMatrix(this.m);
        }
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
