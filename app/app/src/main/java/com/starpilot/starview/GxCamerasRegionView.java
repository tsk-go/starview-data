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

    /* VF: from Vineflower (jadx: ) */
    public GxCamerasRegionView(Context var1) {
        super(var1);
        Intrinsics.checkNotNullParameter(var1, "ctx");
        this.left = new ArrayList<>();
        this.right = new ArrayList<>();
        this.onChanged = new GxCamerasRegionView$$ExternalSyntheticLambda0();
        this.onClose = new GxCamerasRegionView$$ExternalSyntheticLambda1();
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

    /* VF: from Vineflower (jadx: Removed duplicated region;incorrect) */
    public boolean onTouchEvent(MotionEvent var1) {
        Intrinsics.checkNotNullParameter(var1, "e");
        if (this.getBmp() == null) {
            return false;
        } else {
            float var3 = RangesKt.coerceIn(this.cx(var1.getX()), 0.0F, this.getCw());
            float var2 = RangesKt.coerceIn(this.cy(var1.getY()), 0.0F, this.getCh());
            switch (var1.getActionMasked()) {
                case 0:
                    String var6;
                    float[] var13;
                    label102: {
                        var6 = this.annotating;
                        ArrayList var12;
                        if (Intrinsics.areEqual(var6, "left")) {
                            var12 = this.left;
                        } else {
                            if (!Intrinsics.areEqual(var6, "right")) {
                                float[] var5 = this.hit(var3, var2, this.left);
                                var13 = var5;
                                if (var5 != null) {
                                    break label102;
                                }
                            }

                            var12 = this.right;
                        }

                        var13 = this.hit(var3, var2, var12);
                    }

                    this.drag = var13;
                    if (var6 == null && this.drag == null) {
                        return false;
                    } else {
                        this.tracking = true;
                        this.moved = false;
                        this.downX = var1.getX();
                        this.downY = var1.getY();
                        ViewParent var10 = this.getParent();
                        if (var10 != null) {
                            var10.requestDisallowInterceptTouchEvent(true);
                        }

                        return true;
                    }
                case 1:
                    if (!this.tracking) {
                        return false;
                    } else {
                        this.tracking = false;
                        ViewParent var4 = this.getParent();
                        if (var4 != null) {
                            var4.requestDisallowInterceptTouchEvent(false);
                        }

                        if (this.moved) {
                            if (this.drag != null) {
                                this.onChanged.invoke();
                            }

                            this.drag = null;
                            return true;
                        } else {
                            this.drag = null;
                            String var11 = this.annotating;
                            if (var11 == null) {
                                return true;
                            } else {
                                if (!(this.cx(var1.getX()) < 0.0F)
                                    && !(this.cy(var1.getY()) < 0.0F)
                                    && !(this.cx(var1.getX()) > this.getCw())
                                    && !(this.cy(var1.getY()) > this.getCh())) {
                                    ArrayList var9;
                                    if (Intrinsics.areEqual(var11, "left")) {
                                        var9 = this.left;
                                    } else {
                                        var9 = this.right;
                                    }

                                    if (var9.size() >= 3 && (float)Math.hypot(var3 - ((float[])var9.get(0))[0], var2 - ((float[])var9.get(0))[1]) < this.u(26.0F)) {
                                        this.onClose.invoke();
                                        return true;
                                    }

                                    if (var9.size() >= 64) {
                                        return true;
                                    }

                                    var9.add(new float[]{GxCamerasKt.gxCamRound(var3), GxCamerasKt.gxCamRound(var2)});
                                    this.onChanged.invoke();
                                    return true;
                                }

                                return true;
                            }
                        }
                    }
                case 2:
                    if (!this.tracking) {
                        return false;
                    }

                    if (!this.moved && (float)Math.hypot(var1.getX() - this.downX, var1.getY() - this.downY) > 10 * this.getDensity()) {
                        this.moved = true;
                    }

                    float[] var8 = this.drag;
                    if (this.moved && var8 != null) {
                        var8[0] = var3;
                        var8[1] = var2;
                        this.invalidate();
                    }

                    return true;
                case 3:
                    this.tracking = false;
                    this.drag = null;
                    ViewParent var7 = this.getParent();
                    if (var7 != null) {
                        var7.requestDisallowInterceptTouchEvent(false);
                    }

                    return true;
                default:
                    return this.tracking;
            }
        }
    }
}
