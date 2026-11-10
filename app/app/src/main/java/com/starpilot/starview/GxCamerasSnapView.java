package com.starpilot.starview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.view.View;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: GxCameras.kt */
class GxCamerasSnapView extends View {
    private final Paint bg;
    private Bitmap bmp;
    private final float density;
    private final Paint paint;
    private String placeholder;
    private final RectF rect;
    private float s;

    /* VF: from Vineflower (jadx: ) */
    public GxCamerasSnapView(Context var1) {
        super(var1);
        Intrinsics.checkNotNullParameter(var1, "ctx");
        this.density = var1.getResources().getDisplayMetrics().density;
        this.placeholder = "Loading camera snapshot...";
        this.rect = new RectF();
        this.s = 1.0F;
        this.paint = new Paint(3);
        Paint var2 = new Paint();
        var2.setColor(Color.rgb(34, 34, 34));
        this.bg = var2;
    }

    public final float getDensity() {
        return this.density;
    }

    public final Bitmap getBmp() {
        return this.bmp;
    }

    public final void setBmp(Bitmap v) {
        this.bmp = v;
        requestLayout();
        invalidate();
    }

    public final String getPlaceholder() {
        return this.placeholder;
    }

    public final void setPlaceholder(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.placeholder = str;
    }

    public final float getCw() {
        Bitmap it = this.bmp;
        if (it != null) {
            return Math.min(it.getWidth(), 1280);
        }
        return 640.0f;
    }

    public final float getCh() {
        int gxCamRound;
        Bitmap it = this.bmp;
        if (it == null) {
            return 480.0f;
        }
        gxCamRound = GxCamerasKt.gxCamRound((((float) Math.min(it.getWidth(), 1280)) * it.getHeight()) / it.getWidth());
        return gxCamRound;
    }

    protected final RectF getRect() {
        return this.rect;
    }

    protected final float getS() {
        return this.s;
    }

    protected final void setS(float f) {
        this.s = f;
    }

    protected final Paint getPaint() {
        return this.paint;
    }

    public final float u(float dp) {
        return (this.density * dp) / this.s;
    }

    @Override // android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int gxCamRound;
        int w = View.MeasureSpec.getSize(widthMeasureSpec);
        float maxH = getResources().getDisplayMetrics().heightPixels * 0.72f;
        gxCamRound = GxCamerasKt.gxCamRound(Math.min((w * getCh()) / getCw(), maxH));
        setMeasuredDimension(w, gxCamRound);
    }

    private final void fit() {
        this.s = Math.min(getWidth() / getCw(), getHeight() / getCh());
        float dw = getCw() * this.s;
        float dh = getCh() * this.s;
        float f = 2;
        this.rect.set((getWidth() - dw) / f, (getHeight() - dh) / f, (getWidth() + dw) / f, (getHeight() + dh) / f);
    }

    public final float cx(float x) {
        return (x - this.rect.left) / this.s;
    }

    public final float cy(float y) {
        return (y - this.rect.top) / this.s;
    }

    @Override // android.view.View
    protected void onDraw(Canvas c) {
        Intrinsics.checkNotNullParameter(c, "c");
        fit();
        c.drawRect(this.rect, this.bg);
        Bitmap b = this.bmp;
        if (b == null) {
            this.paint.reset();
            this.paint.setAntiAlias(true);
            this.paint.setColor(Color.rgb(136, 136, 136));
            this.paint.setTextSize(18 * this.density);
            this.paint.setTextAlign(Paint.Align.CENTER);
            this.paint.setTypeface(Typeface.MONOSPACE);
            c.drawText(this.placeholder, getWidth() / 2.0f, getHeight() / 2.0f, this.paint);
            return;
        }
        c.save();
        c.translate(this.rect.left, this.rect.top);
        c.scale(this.s, this.s);
        c.save();
        c.scale(-1.0f, 1.0f, getCw() / 2, 0.0f);
        this.paint.reset();
        this.paint.setFilterBitmap(true);
        c.drawBitmap(b, (Rect) null, new RectF(0.0f, 0.0f, getCw(), getCh()), this.paint);
        c.restore();
        this.paint.reset();
        this.paint.setAntiAlias(true);
        float bh = u(32.0f);
        this.paint.setColor(Color.argb(166, 0, 0, 0));
        this.paint.setStyle(Paint.Style.FILL);
        c.drawRect(0.0f, 0.0f, getCw(), bh, this.paint);
        this.paint.setColor(-1);
        this.paint.setTextSize(u(13.0f));
        this.paint.setTypeface(Typeface.DEFAULT_BOLD);
        this.paint.setTextAlign(Paint.Align.CENTER);
        float f = 4;
        c.drawText("LEFT SIDE OF VEHICLE - LEFT HERE", getCw() / f, bh * 0.66f, this.paint);
        c.drawText("RIGHT SIDE OF VEHICLE - RIGHT HERE", (getCw() * 3) / f, 0.66f * bh, this.paint);
        drawOverlay(c, b);
        c.restore();
    }

    public void drawOverlay(Canvas c, Bitmap b) {
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(b, "b");
    }

    protected final void drawCrop(Canvas c, Bitmap b, RectF src, RectF dst) {
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(b, "b");
        Intrinsics.checkNotNullParameter(src, "src");
        Intrinsics.checkNotNullParameter(dst, "dst");
        if (src.width() <= 0.0f || src.height() <= 0.0f) {
            return;
        }
        float l = Math.max(src.left, 0.0f);
        float t = Math.max(src.top, 0.0f);
        float r = Math.min(src.right, b.getWidth());
        float btm = Math.min(src.bottom, b.getHeight());
        if (r <= l || btm <= t) {
            return;
        }
        float sx = dst.width() / src.width();
        float sy = dst.height() / src.height();
        RectF d = new RectF(dst.left + ((l - src.left) * sx), dst.top + ((t - src.top) * sy), dst.left + ((r - src.left) * sx), dst.top + ((btm - src.top) * sy));
        this.paint.reset();
        this.paint.setFilterBitmap(true);
        c.drawBitmap(b, new Rect((int) l, (int) t, (int) r, (int) btm), d, this.paint);
    }

    protected final void dot(Canvas c, float x, float y, float r, int fill, Integer stroke) {
        Intrinsics.checkNotNullParameter(c, "c");
        this.paint.reset();
        this.paint.setAntiAlias(true);
        this.paint.setStyle(Paint.Style.FILL);
        this.paint.setColor(fill);
        c.drawCircle(x, y, r, this.paint);
        if (stroke != null) {
            this.paint.setStyle(Paint.Style.STROKE);
            this.paint.setColor(stroke.intValue());
            this.paint.setStrokeWidth(u(2.0f));
            c.drawCircle(x, y, r, this.paint);
        }
    }
}
