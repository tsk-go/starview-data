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

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public TrackMapView(Context ctx, List<DriveTrack.Pt> pts, long segStartMs, Function1<? super Long, Unit> onPick) {
        super(ctx);
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(pts, "pts");
        Intrinsics.checkNotNullParameter(onPick, "onPick");
        this.pts = pts;
        this.segStartMs = segStartMs;
        this.onPick = onPick;
        this.d = getResources().getDisplayMetrics().density;
        MapTiles $this$tiles_u24lambda_u242 = new MapTiles(ctx, new Function0() { // from class: com.starpilot.starview.TrackMapView$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit tiles$lambda$1;
                tiles$lambda$1 = TrackMapView.tiles$lambda$1(TrackMapView.this);
                return tiles$lambda$1;
            }
        });
        $this$tiles_u24lambda_u242.setHost(Link.INSTANCE.getHost());
        $this$tiles_u24lambda_u242.setDirectKey(Nav.INSTANCE.getTileKey());
        this.tiles = $this$tiles_u24lambda_u242;
        int size = this.pts.size();
        double[] dArr = new double[size];
        for (int i = 0; i < size; i++) {
            dArr[i] = Nav.INSTANCE.mercX(this.pts.get(i).getLon());
        }
        this.mx = dArr;
        int size2 = this.pts.size();
        double[] dArr2 = new double[size2];
        for (int i2 = 0; i2 < size2; i2++) {
            dArr2[i2] = Nav.INSTANCE.mercY(this.pts.get(i2).getLat());
        }
        this.my = dArr2;
        this.cx = 0.5d;
        this.cy = 0.5d;
        this.zoom = 14.0d;
        this.nowMs = Long.MIN_VALUE;
        Paint $this$line_u24lambda_u243 = new Paint(1);
        $this$line_u24lambda_u243.setStyle(Paint.Style.STROKE);
        $this$line_u24lambda_u243.setStrokeWidth(this.d * 5.0f);
        $this$line_u24lambda_u243.setColor(-13664019);
        $this$line_u24lambda_u243.setStrokeCap(Paint.Cap.ROUND);
        $this$line_u24lambda_u243.setStrokeJoin(Paint.Join.ROUND);
        this.line = $this$line_u24lambda_u243;
        Paint $this$seg_u24lambda_u244 = new Paint(1);
        $this$seg_u24lambda_u244.setColor(-1);
        $this$seg_u24lambda_u244.setStyle(Paint.Style.FILL);
        this.seg = $this$seg_u24lambda_u244;
        Paint $this$dot_u24lambda_u245 = new Paint(1);
        $this$dot_u24lambda_u245.setColor(-16121);
        $this$dot_u24lambda_u245.setStyle(Paint.Style.FILL);
        this.dot = $this$dot_u24lambda_u245;
        Paint $this$ring_u24lambda_u246 = new Paint(1);
        $this$ring_u24lambda_u246.setColor(-16777216);
        $this$ring_u24lambda_u246.setStyle(Paint.Style.STROKE);
        $this$ring_u24lambda_u246.setStrokeWidth(this.d * 2.0f);
        this.ring = $this$ring_u24lambda_u246;
        Paint $this$txt_u24lambda_u247 = new Paint(1);
        $this$txt_u24lambda_u247.setColor(-1);
        $this$txt_u24lambda_u247.setTextSize(this.d * 13.0f);
        $this$txt_u24lambda_u247.setShadowLayer(this.d * 3.0f, 0.0f, 0.0f, -16777216);
        this.txt = $this$txt_u24lambda_u247;
        this.tileP = new Paint(2);
        this.scaler = new ScaleGestureDetector(ctx, new ScaleGestureDetector.SimpleOnScaleGestureListener() { // from class: com.starpilot.starview.TrackMapView$scaler$1
            @Override // android.view.ScaleGestureDetector.SimpleOnScaleGestureListener, android.view.ScaleGestureDetector.OnScaleGestureListener
            public boolean onScale(ScaleGestureDetector det) {
                double d;
                Intrinsics.checkNotNullParameter(det, "det");
                TrackMapView trackMapView = TrackMapView.this;
                d = TrackMapView.this.zoom;
                trackMapView.zoom = RangesKt.coerceIn(d + (Math.log(det.getScaleFactor()) / Math.log(2.0d)), 3.0d, 18.5d);
                TrackMapView.this.invalidate();
                return true;
            }
        });
        this.gest = new GestureDetector(ctx, new GestureDetector.SimpleOnGestureListener() { // from class: com.starpilot.starview.TrackMapView$gest$1
            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public boolean onDown(MotionEvent e) {
                Intrinsics.checkNotNullParameter(e, "e");
                return true;
            }

            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float dx, float dy) {
                double d;
                double world;
                double d2;
                double world2;
                Intrinsics.checkNotNullParameter(e2, "e2");
                TrackMapView trackMapView = TrackMapView.this;
                d = TrackMapView.this.cx;
                world = TrackMapView.this.world();
                trackMapView.cx = d + (dx / world);
                TrackMapView trackMapView2 = TrackMapView.this;
                d2 = TrackMapView.this.cy;
                world2 = TrackMapView.this.world();
                trackMapView2.cy = d2 + (dy / world2);
                TrackMapView.this.invalidate();
                return true;
            }

            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
            public boolean onDoubleTap(MotionEvent e) {
                double d;
                Intrinsics.checkNotNullParameter(e, "e");
                TrackMapView trackMapView = TrackMapView.this;
                d = TrackMapView.this.zoom;
                trackMapView.zoom = RangesKt.coerceAtMost(d + 1, 18.5d);
                TrackMapView.this.invalidate();
                return true;
            }

            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
            public boolean onSingleTapConfirmed(MotionEvent e) {
                float f;
                List list;
                List list2;
                Function1 function1;
                List list3;
                double[] dArr3;
                float sx;
                double[] dArr4;
                float sy;
                Intrinsics.checkNotNullParameter(e, "e");
                int best = -1;
                f = TrackMapView.this.d;
                float bd = f * 40.0f;
                list = TrackMapView.this.pts;
                int size3 = list.size();
                for (int i3 = 0; i3 < size3; i3++) {
                    TrackMapView trackMapView = TrackMapView.this;
                    dArr3 = TrackMapView.this.mx;
                    sx = trackMapView.sx(dArr3[i3]);
                    double x = sx - e.getX();
                    TrackMapView trackMapView2 = TrackMapView.this;
                    dArr4 = TrackMapView.this.my;
                    sy = trackMapView2.sy(dArr4[i3]);
                    float dd = (float) Math.hypot(x, sy - e.getY());
                    if (dd < bd) {
                        bd = dd;
                        best = i3;
                    }
                }
                if (best >= 0) {
                    TrackMapView trackMapView3 = TrackMapView.this;
                    list2 = TrackMapView.this.pts;
                    trackMapView3.setNowMs(((DriveTrack.Pt) list2.get(best)).getT());
                    function1 = TrackMapView.this.onPick;
                    list3 = TrackMapView.this.pts;
                    function1.invoke(Long.valueOf(((DriveTrack.Pt) list3.get(best)).getT()));
                    return true;
                }
                return true;
            }
        });
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
