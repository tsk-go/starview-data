package com.starpilot.starview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewParent;
import com.starpilot.starview.DriveTrack;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.view.GestureDetector.OnGestureListener;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;

/* compiled from: TrackMapView.kt */
public final class TrackMapView extends View {
    private double cx;
    private double cy;
    private final float d;
    private final Paint dot;
    private boolean fitted;
    private final GestureDetector gest;
    private final Paint line;
    private final double[] mx;
    private final double[] my;
    private long nowMs;
    private final Function1<Long, Unit> onPick;
    private final List<DriveTrack.Pt> pts;
    private final Paint ring;
    private final ScaleGestureDetector scaler;
    private final Paint seg;
    private final long segStartMs;
    private final Paint tileP;
    private final MapTiles tiles;
    private final Paint txt;
    private double zoom;

    /* VF: from Vineflower (jadx: ) */
    public TrackMapView(Context var1, List<DriveTrack.Pt> var2, long var3, Function1<? super Long, Unit> var5) {
        super(var1);
        Intrinsics.checkNotNullParameter(var1, "ctx");
        Intrinsics.checkNotNullParameter(var2, "pts");
        Intrinsics.checkNotNullParameter(var5, "onPick");
        this.pts = var2;
        this.segStartMs = var3;
        this.onPick = var5;
        this.d = this.getResources().getDisplayMetrics().density;
        MapTiles var9 = new MapTiles(var1, new TrackMapView$$ExternalSyntheticLambda1(this));
        var9.setHost(Link.INSTANCE.getHost());
        var9.setDirectKey(Nav.INSTANCE.getTileKey());
        this.tiles = var9;
        int var8 = this.pts.size();
        double[] var10 = new double[var8];
        byte var7 = 0;

        for (int var6 = 0; var6 < var8; var6++) {
            var10[var6] = Nav.INSTANCE.mercX(this.pts.get(var6).getLon());
        }

        this.mx = var10;
        var8 = this.pts.size();
        double[] var11 = new double[var8];

        for (int var17 = var7; var17 < var8; var17++) {
            var11[var17] = Nav.INSTANCE.mercY(this.pts.get(var17).getLat());
        }

        this.my = var11;
        this.cx = 0.5;
        this.cy = 0.5;
        this.zoom = 14.0;
        this.nowMs = Long.MIN_VALUE;
        Paint var12 = new Paint(1);
        var12.setStyle(android.graphics.Paint.Style.STROKE);
        var12.setStrokeWidth(this.d * 5.0F);
        var12.setColor(-13664019);
        var12.setStrokeCap(android.graphics.Paint.Cap.ROUND);
        var12.setStrokeJoin(android.graphics.Paint.Join.ROUND);
        this.line = var12;
        Paint var13 = new Paint(1);
        var13.setColor(-1);
        var13.setStyle(android.graphics.Paint.Style.FILL);
        this.seg = var13;
        Paint var14 = new Paint(1);
        var14.setColor(-16121);
        var14.setStyle(android.graphics.Paint.Style.FILL);
        this.dot = var14;
        Paint var15 = new Paint(1);
        var15.setColor(-16777216);
        var15.setStyle(android.graphics.Paint.Style.STROKE);
        var15.setStrokeWidth(this.d * 2.0F);
        this.ring = var15;
        Paint var16 = new Paint(1);
        var16.setColor(-1);
        var16.setTextSize(this.d * 13.0F);
        var16.setShadowLayer(this.d * 3.0F, 0.0F, 0.0F, -16777216);
        this.txt = var16;
        this.tileP = new Paint(2);
        this.scaler = new ScaleGestureDetector(var1, (OnScaleGestureListener)(new SimpleOnScaleGestureListener() {
            final TrackMapView this$0 = TrackMapView.this;

            public boolean onScale(ScaleGestureDetector var1) {
                Intrinsics.checkNotNullParameter(var1, "det");
                this.this$0.zoom = RangesKt.coerceIn(this.this$0.zoom + Math.log(var1.getScaleFactor()) / Math.log(2.0), 3.0, 18.5);
                this.this$0.invalidate();
                return true;
            }
        }));
        this.gest = new GestureDetector(var1, (OnGestureListener)(new SimpleOnGestureListener() {
            final TrackMapView this$0 = TrackMapView.this;

            public boolean onDoubleTap(MotionEvent var1) {
                Intrinsics.checkNotNullParameter(var1, "e");
                this.this$0.zoom = RangesKt.coerceAtMost(this.this$0.zoom + 1, 18.5);
                this.this$0.invalidate();
                return true;
            }

            public boolean onDown(MotionEvent var1) {
                Intrinsics.checkNotNullParameter(var1, "e");
                return true;
            }

            public boolean onScroll(MotionEvent var1, MotionEvent var2x, float var3x, float var4) {
                Intrinsics.checkNotNullParameter(var2x, "e2");
                this.this$0.cx = this.this$0.cx + var3x / this.this$0.world();
                this.this$0.cy = this.this$0.cy + var4 / this.this$0.world();
                this.this$0.invalidate();
                return true;
            }

            public boolean onSingleTapConfirmed(MotionEvent var1) {
                Intrinsics.checkNotNullParameter(var1, "e");
                int var6 = -1;
                float var2x = this.this$0.d * 40.0F;
                int var5x = 0;
                int var7x = this.this$0.pts.size();

                while (var5x < var7x) {
                    float var4 = (float)Math.hypot(this.this$0.sx(this.this$0.mx[var5x]) - var1.getX(), this.this$0.sy(this.this$0.my[var5x]) - var1.getY());
                    float var3x = var2x;
                    if (var4 < var2x) {
                        var3x = var4;
                        var6 = var5x;
                    }

                    var5x++;
                    var2x = var3x;
                }

                if (var6 >= 0) {
                    this.this$0.setNowMs(this.this$0.pts.get(var6).getT());
                    this.this$0.onPick.invoke(this.this$0.pts.get(var6).getT());
                }

                return true;
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tiles$lambda$1(final TrackMapView this$0) {
        this$0.post(new Runnable() { // from class: com.starpilot.starview.TrackMapView$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this$0.invalidate();
            }
        });
        return Unit.INSTANCE;
    }

    public final long getNowMs() {
        return this.nowMs;
    }

    public final void setNowMs(long v) {
        this.nowMs = v;
        invalidate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final double world() {
        return Math.pow(2.0d, this.zoom) * 512.0d;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final float sx(double x) {
        return (float) (((x - this.cx) * world()) + (getWidth() / 2.0d));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final float sy(double y) {
        return (float) (((y - this.cy) * world()) + (getHeight() / 2.0d));
    }

    private final void fit() {
        if (this.pts.isEmpty() || getWidth() == 0) {
            return;
        }
        double x0 = ArraysKt.minOrThrow(this.mx);
        double x1 = ArraysKt.maxOrThrow(this.mx);
        double y0 = ArraysKt.minOrThrow(this.my);
        double y1 = ArraysKt.maxOrThrow(this.my);
        double d = 2;
        this.cx = (x0 + x1) / d;
        this.cy = (y0 + y1) / d;
        double width = getWidth() - (this.d * 60);
        double max = Math.max(x1 - x0, 1.0E-7d);
        double d2 = 512;
        double zx = Math.log(width / (max * d2)) / Math.log(2.0d);
        double zy = Math.log((getHeight() - (r10 * this.d)) / (Math.max(y1 - y0, 1.0E-7d) * d2)) / Math.log(2.0d);
        this.zoom = RangesKt.coerceIn(Math.min(zx, zy), 3.0d, 17.5d);
        this.fitted = true;
    }

    @Override // android.view.View
    protected void onDraw(Canvas c) {
        HashSet wanted;
        Object obj;
        int tx0;
        Object minElem$iv;
        int z;
        int n;
        String style;
        int tx02;
        int tx;
        Intrinsics.checkNotNullParameter(c, "c");
        if (!this.fitted) {
            fit();
        }
        c.drawColor(-15000028);
        int z2 = RangesKt.coerceIn((int) Math.floor(this.zoom), 0, 18);
        int n2 = 1 << z2;
        double scale = world() / (n2 * 512.0d);
        int ty1 = (int) Math.floor((this.cx - ((getWidth() / 2.0d) / world())) * n2);
        int tx1 = (int) Math.floor((this.cx + ((getWidth() / 2.0d) / world())) * n2);
        int ty0 = (int) Math.floor((this.cy - ((getHeight() / 2.0d) / world())) * n2);
        int ty12 = (int) Math.floor((this.cy + ((getHeight() / 2.0d) / world())) * n2);
        HashSet wanted2 = new HashSet();
        String style2 = "dark";
        int ty = ty0;
        if (ty <= ty12) {
            while (true) {
                int tx2 = ty1;
                if (tx2 <= tx1) {
                    while (true) {
                        if (ty < 0) {
                            wanted = wanted2;
                            style = style2;
                            tx02 = ty1;
                            tx = tx2;
                            obj = null;
                            tx0 = ty12;
                        } else if (ty >= n2) {
                            wanted = wanted2;
                            style = style2;
                            tx02 = ty1;
                            tx = tx2;
                            obj = null;
                            tx0 = ty12;
                        } else {
                            int wx = ((tx2 % n2) + n2) % n2;
                            wanted2.add(MapTiles.Companion.key(style2, z2, wx, ty));
                            Bitmap b = this.tiles.get(style2, z2, wx, ty);
                            if (b == null) {
                                wanted = wanted2;
                                style = style2;
                                tx02 = ty1;
                                tx = tx2;
                                obj = null;
                                tx0 = ty12;
                            } else {
                                style = style2;
                                tx02 = ty1;
                                tx = tx2;
                                float l = sx(tx2 / n2);
                                tx0 = ty12;
                                wanted = wanted2;
                                float t = sy(ty / n2);
                                float s = (float) (512 * scale);
                                obj = null;
                                c.drawBitmap(b, (Rect) null, new RectF(l, t, l + s + 0.6f, t + s + 0.6f), this.tileP);
                            }
                        }
                        int tx3 = tx;
                        if (tx3 == tx1) {
                            break;
                        }
                        tx2 = tx3 + 1;
                        ty12 = tx0;
                        ty1 = tx02;
                        style2 = style;
                        wanted2 = wanted;
                    }
                } else {
                    wanted = wanted2;
                    style = style2;
                    tx02 = ty1;
                    obj = null;
                    tx0 = ty12;
                }
                if (ty == tx0) {
                    break;
                }
                ty++;
                ty12 = tx0;
                ty1 = tx02;
                style2 = style;
                wanted2 = wanted;
            }
        } else {
            wanted = wanted2;
            obj = null;
            tx0 = ty12;
        }
        this.tiles.setWanted(wanted);
        if (this.pts.isEmpty()) {
            c.drawText("No path for this drive (the tablet wasn't recording one)", 16 * this.d, getHeight() / 2.0f, this.txt);
            return;
        }
        Path p = new Path();
        p.moveTo(sx(this.mx[0]), sy(this.my[0]));
        int size = this.pts.size();
        for (int i = 1; i < size; i++) {
            p.lineTo(sx(this.mx[i]), sy(this.my[i]));
        }
        c.drawPath(p, this.line);
        long lastSeg = -1;
        int i2 = 0;
        int size2 = this.pts.size();
        while (i2 < size2) {
            int ty13 = tx0;
            long sg = (this.pts.get(i2).getT() - this.segStartMs) / 60000;
            if (sg != lastSeg) {
                z = z2;
                n = n2;
                c.drawCircle(sx(this.mx[i2]), sy(this.my[i2]), this.d * 3.2f, this.seg);
                lastSeg = sg;
            } else {
                z = z2;
                n = n2;
            }
            i2++;
            tx0 = ty13;
            z2 = z;
            n2 = n;
        }
        float f = 8;
        c.drawText("Start", sx(this.mx[0]) + (this.d * f), sy(this.my[0]) - (f * this.d), this.txt);
        if (this.nowMs != Long.MIN_VALUE) {
            Iterable $this$minByOrNull$iv = CollectionsKt.getIndices(this.pts);
            Iterator iterator$iv = $this$minByOrNull$iv.iterator();
            if (iterator$iv.hasNext()) {
                minElem$iv = iterator$iv.next();
                if (iterator$iv.hasNext()) {
                    int it = ((Number) minElem$iv).intValue();
                    long minValue$iv = Math.abs(this.pts.get(it).getT() - this.nowMs);
                    while (true) {
                        Object e$iv = iterator$iv.next();
                        int it2 = ((Number) e$iv).intValue();
                        int tx12 = tx1;
                        int ty02 = ty0;
                        long v$iv = Math.abs(this.pts.get(it2).getT() - this.nowMs);
                        if (minValue$iv > v$iv) {
                            minElem$iv = e$iv;
                            minValue$iv = v$iv;
                        }
                        if (!iterator$iv.hasNext()) {
                            break;
                        }
                        tx1 = tx12;
                        ty0 = ty02;
                    }
                }
            } else {
                minElem$iv = null;
            }
            Integer num = (Integer) minElem$iv;
            if (num != null) {
                int i3 = num.intValue();
                c.drawCircle(sx(this.mx[i3]), sy(this.my[i3]), this.d * 8.0f, this.dot);
                c.drawCircle(sx(this.mx[i3]), sy(this.my[i3]), this.d * 8.0f, this.ring);
            }
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent e) {
        Intrinsics.checkNotNullParameter(e, "e");
        this.scaler.onTouchEvent(e);
        if (!this.scaler.isInProgress()) {
            this.gest.onTouchEvent(e);
        }
        ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(true);
        }
        return true;
    }
}
