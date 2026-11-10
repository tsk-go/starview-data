package com.starpilot.starview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;
import androidx.core.graphics.ColorUtils;
import com.starpilot.starview.Detector;
import com.starpilot.starview.LaneTracks;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Pair;
import kotlin.Triple;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import kotlin.ranges.RangesKt;
import kotlin.reflect.KFunction;
import kotlin.text.StringsKt;
import android.graphics.Bitmap.Config;
import android.graphics.Paint.Align;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;

/* compiled from: OverlayView.kt */
public final class OverlayView extends View {
    public static final Companion_ Companion = new Companion_(null);
    private final Bitmap ambBmp;
    private final Paint ambFade;
    private final float[] ambNow;
    private final Paint ambPaint;
    private float blendThrottle;
    private long blinkPhase;
    private final Paint detBg;
    private final Paint detBox;
    private final Paint detText;
    private Detector detector;
    private final Paint fill;
    private long lastDrawNs;
    private Function0<Unit> onResized;
    private final Path path;
    private final Projection proj;
    private boolean sceneMode;
    private final Paint stroke;
    public Telemetry t;
    private final Paint text;
    private final Paint textOutline;
    private final float[] tmp;
    private float torqueSmooth;
    private final LaneTracks tracks;
    private UiStatus uiStatus;

    /* VF: from Vineflower (jadx: ) */
    public OverlayView(Context var1, AttributeSet var2) {
        super(var1, var2);
        Intrinsics.checkNotNullParameter(var1, "ctx");
        this.proj = new Projection();
        this.uiStatus = UiStatus.DISENGAGED;
        Paint var4 = new Paint(1);
        var4.setStyle(android.graphics.Paint.Style.FILL);
        this.fill = var4;
        Paint var5 = new Paint(1);
        var5.setStyle(android.graphics.Paint.Style.STROKE);
        this.stroke = var5;
        Paint var6 = new Paint(1);
        var6.setColor(-1);
        var6.setTextAlign(android.graphics.Paint.Align.CENTER);
        var6.setTypeface(Typeface.DEFAULT_BOLD);
        this.text = var6;
        Paint var7 = new Paint(1);
        var7.setColor(-16777216);
        var7.setTextAlign(android.graphics.Paint.Align.CENTER);
        var7.setStyle(android.graphics.Paint.Style.STROKE);
        var7.setStrokeWidth(3.0F);
        var7.setTypeface(Typeface.DEFAULT_BOLD);
        this.textOutline = var7;
        this.path = new Path();
        this.tmp = new float[2];
        this.blendThrottle = 1.0F;
        this.tracks = new LaneTracks(0.0F, 1, null);
        Bitmap var8 = Bitmap.createBitmap(16, 1, android.graphics.Bitmap.Config.ARGB_8888);
        Intrinsics.checkNotNullExpressionValue(var8, "createBitmap(...)");
        this.ambBmp = var8;
        this.ambPaint = new Paint(2);
        this.ambFade = new Paint();
        float[] var9 = new float[96];

        for (int var3 = 0; var3 < 96; var3++) {
            var9[var3] = -1.0F;
        }

        this.ambNow = var9;
        Paint var10 = new Paint(1);
        var10.setStyle(android.graphics.Paint.Style.STROKE);
        var10.setStrokeWidth(4.0F);
        this.detBox = var10;
        Paint var11 = new Paint(1);
        var11.setColor(-1);
        var11.setTextSize(26.0F);
        var11.setFakeBoldText(true);
        this.detText = var11;
        Paint var12 = new Paint();
        var12.setStyle(android.graphics.Paint.Style.FILL);
        this.detBg = var12;
    }

    public /* synthetic */ OverlayView(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
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

    public final Projection getProj() {
        return this.proj;
    }

    public final UiStatus getUiStatus() {
        return this.uiStatus;
    }

    public final void setUiStatus(UiStatus uiStatus) {
        Intrinsics.checkNotNullParameter(uiStatus, "<set-?>");
        this.uiStatus = uiStatus;
    }

    private final float dp(float v) {
        return getResources().getDisplayMetrics().density * v;
    }

    @Override // android.view.View
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Rect b;
        this.proj.setViewW(w);
        this.proj.setViewH(h);
        if (Build.VERSION.SDK_INT >= 30) {
            Object systemService = getContext().getSystemService("window");
            Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.view.WindowManager");
            b = ((WindowManager) systemService).getMaximumWindowMetrics().getBounds();
        } else {
            b = new Rect(0, 0, getResources().getDisplayMetrics().widthPixels, getResources().getDisplayMetrics().heightPixels);
        }
        Intrinsics.checkNotNull(b);
        this.proj.setFullW(Math.max(b.width(), b.height()));
        this.proj.setFullH(Math.min(b.width(), b.height()));
        this.proj.update();
        Function0<Unit> function0 = this.onResized;
        if (function0 != null) {
            function0.invoke();
        }
    }

    public final Function0<Unit> getOnResized() {
        return this.onResized;
    }

    public final void setOnResized(Function0<Unit> function0) {
        this.onResized = function0;
    }

    public final void setFrameSize(int w, int h) {
        if (w == this.proj.getFrameW() && h == this.proj.getFrameH()) {
            return;
        }
        this.proj.setFrameW(w);
        this.proj.setFrameH(h);
        this.proj.update();
    }

    /* VF: from Vineflower (jadx: Removed duplicated region;incorrect) */
    public final boolean updateCalibration() {
        float[] var3 = this.getT().getCalRpy();
        boolean var1;
        if (this.proj.getRpy()[0] == var3[0]) {
            var1 = true;
        } else {
            var1 = false;
        }

        boolean var2;
        label40: {
            if (var1) {
                if (this.proj.getRpy()[1] == var3[1]) {
                    var1 = true;
                } else {
                    var1 = false;
                }

                if (var1) {
                    if (this.proj.getRpy()[2] == var3[2]) {
                        var1 = true;
                    } else {
                        var1 = false;
                    }

                    if (var1) {
                        if (this.proj.getPathOffsetZ() == this.getT().getCalHeight()) {
                            var1 = true;
                        } else {
                            var1 = false;
                        }

                        if (var1) {
                            var2 = false;
                            break label40;
                        }
                    }
                }
            }

            var2 = true;
        }

        if (var2) {
            this.proj.setRpy(new double[]{var3[0], var3[1], var3[2]});
            this.proj.setPathOffsetZ(this.getT().getCalHeight());
            this.proj.update();
        }

        return var2;
    }

    public final boolean getSceneMode() {
        return this.sceneMode;
    }

    public final void setSceneMode(boolean z) {
        this.sceneMode = z;
    }

    @Override // android.view.View
    protected void onDraw(Canvas c) {
        Intrinsics.checkNotNullParameter(c, "c");
        if (this.t == null) {
            return;
        }
        long now = System.nanoTime();
        float dt = this.lastDrawNs == 0 ? 0.05f : RangesKt.coerceIn((now - this.lastDrawNs) / 1.0E9f, 0.001f, 0.25f);
        this.lastDrawNs = now;
        this.blinkPhase = now / 1000000;
        if (!this.sceneMode) {
            drawAmbient(c);
        }
        if (!this.sceneMode && getT().getStarted() && this.proj.getValid() && getT().getCalValid() && getT().s("model_ui")) {
            drawModel(c, dt);
        }
        if (!this.sceneMode && getT().getStarted() && this.detector != null && getT().s("detector_boxes")) {
            drawDetections(c);
        }
        drawSignalFlashes(c);
        if (getT().s("status_border")) {
            drawBorder(c);
        }
        if (!getT().getStarted() || !getT().s("torque_bar")) {
            return;
        }
        drawTorqueBar(c, dt);
    }

    /* VF: from Vineflower (jadx: Removed duplicated region;incorrect) */
    private final void drawModel(Canvas var1, float var2) {
        ModelSnapshot var35 = this.getT().getModel();
        if (var35 != null) {
            int var21 = var35.getPosX().length;
            if (var21 >= 2) {
                Lead var33 = this.getT().getLeadOne();
                boolean var29 = this.getT().isMetric();
                Pair var34 = drawModel$custom(this, "PathWidth", 6.1F);
                boolean var32 = (Boolean)var34.component1();
                float var17 = ((Number)var34.component2()).floatValue();
                var34 = drawModel$custom(this, "LaneLinesWidth", 4.0F);
                boolean var30 = (Boolean)var34.component1();
                float var20 = ((Number)var34.component2()).floatValue();
                var34 = drawModel$custom(this, "RoadEdgesWidth", 2.0F);
                boolean var31 = (Boolean)var34.component1();
                float var19 = ((Number)var34.component2()).floatValue();
                var34 = drawModel$custom(this, "PathEdgeWidth", 20.0F);
                var34.component1();
                float var18 = ((Number)var34.component2()).floatValue();
                double var3;
                if (var32) {
                    var3 = this.halfM(var17, var29);
                } else {
                    var3 = 0.9;
                }

                double var7 = 0.025;
                double var5;
                if (var30) {
                    var5 = this.smallHalfM(var20, var29);
                } else {
                    var5 = 0.025;
                }

                if (var31) {
                    var7 = this.smallHalfM(var19, var29);
                }

                if (this.getT().s("path_edges")) {
                    var18 = RangesKt.coerceIn(var18 / 100.0F, 0.0F, 1.0F);
                } else {
                    var18 = 0.0F;
                }

                double var11 = RangesKt.coerceIn(var35.getPosX()[var21 - 1], 10.0, 100.0);
                double var46;
                if (var33.getStatus()) {
                    var46 = var33.getDRel() * 2.0;
                    var46 = RangesKt.coerceIn(var46 - Math.min(var46 * 0.35, 10.0), 0.0, var11);
                } else {
                    var46 = var11;
                }

                int var28 = this.lastIdxBelow(var35.getPosX(), var46);
                double var13 = this.proj.getPathOffsetZ();
                int var26 = Companion.parseColor(Telemetry.pStr$default(this.getT(), "LaneLinesColor", null, 2, null), -1);
                int var23;
                if (this.getT().s("lane_centering_cue")) {
                    var23 = LaneCentering.direction$default(LaneCentering.INSTANCE, this.getT(), 0L, 2, null);
                } else {
                    var23 = 0;
                }

                LaneTracks.update$default(this.tracks, var35, this.getT(), 0.05F, false, null, 16, null);
                LaneTracks.PathState var70 = LaneTracks.Companion.pathState(this.getT());
                if (this.getT().s("lane_lines")) {
                    Triple var62 = this.tracks.inner();
                    LaneTracks.Line var37 = (LaneTracks.Line)var62.component1();
                    LaneTracks.Line var36 = (LaneTracks.Line)var62.component2();
                    LaneTracks.Line var63 = (LaneTracks.Line)var62.component3();
                    Iterator var38 = this.tracks.getLines().iterator();
                    Intrinsics.checkNotNullExpressionValue(var38, "iterator(...)");

                    while (var38.hasNext()) {
                        Object var39 = var38.next();
                        Intrinsics.checkNotNullExpressionValue(var39, "next(...)");
                        LaneTracks.Line var40 = (LaneTracks.Line)var39;
                        int var27 = MathKt.roundToInt(var40.getAlpha() * 0.7F * 255);
                        if (var27 > 2) {
                            boolean var24;
                            if (var40 == var63 && var70 == LaneTracks.PathState.ENGAGED) {
                                var24 = true;
                            } else {
                                var24 = false;
                            }

                            double var15 = var40.getAlpha();
                            if (var24 && this.tracks.getEffort() > 0.55F) {
                                var46 = 1.8;
                            } else {
                                var46 = 1.0;
                            }

                            var39 = this.proj
                                .ribbon(var40.getX(), var40.getY(), var40.getZ(), var15 * var5 * var46, 0.0, this.lastIdxBelow(var40.getX(), var11), true);
                            if (var39 != null) {
                                int var22;
                                if ((var23 <= 0 || var40 != var36) && (var23 >= 0 || var40 != var37)) {
                                    var22 = 0;
                                } else {
                                    var22 = 1;
                                }

                                if ((var22 != 0)) {
                                    var22 = Color.rgb(0, 176, 220);
                                } else {
                                    var22 = var26;
                                }

                                int var25 = var22;
                                if (var24) {
                                    var25 = LaneTracks.Companion.effortColor(var22, this.tracks.getEffort());
                                }

                                this.fill.setShader(null);
                                Paint var41 = this.fill;
                                OverlayView.Companion_ var77 = Companion;
                                if (var24 && this.tracks.getEffort() > 0.55F) {
                                    var22 = Math.max(var27, 220);
                                } else {
                                    var22 = var27;
                                }

                                var41.setColor(var77.withAlpha(var25, var22));
                                this.drawPoly(var1, (float[])var39, this.fill);
                            }
                        }
                    }
                }

                if (this.getT().s("road_edges")) {
                    var21 = 0;

                    for (int var58 = ((Object[])var35.getEdgeX()).length; var21 < var58; var21++) {
                        if (var21 < var35.getEdgeStd().length) {
                            var17 = var35.getEdgeStd()[var21];
                        } else {
                            var17 = 1.0F;
                        }

                        var23 = MathKt.roundToInt(RangesKt.coerceIn(1.0F - var17, 0.0F, 1.0F) * 255);
                        if (var23 > 2) {
                            float[] var64 = this.proj
                                .ribbon(
                                    var35.getEdgeX()[var21],
                                    var35.getEdgeY()[var21],
                                    var35.getEdgeZ()[var21],
                                    var7,
                                    0.0,
                                    this.lastIdxBelow(var35.getEdgeX()[var21], var11),
                                    true
                                );
                            if (var64 != null) {
                                this.fill.setShader(null);
                                this.fill.setColor(Color.argb(var23, 255, 0, 0));
                                this.drawPoly(var1, var64, this.fill);
                            }
                        }
                    }
                }

                float[] var65 = this.proj.ribbon(var35.getPosX(), var35.getPosY(), var35.getPosZ(), var3 * (1 - var18), var13, var28, false);
                if (var65 != null) {
                    this.drawPathFill(var1, var65, var35, var28, var2);
                    if (var18 > 0.0F) {
                        float[] var71 = this.proj.ribbon(var35.getPosX(), var35.getPosY(), var35.getPosZ(), var3, var13, var28, false);
                        if (var71 != null) {
                            this.drawPathEdges(var1, var71, var65);
                        }
                    }
                }

                if (this.getT().s("radar_tracks")) {
                    float[] var74 = this.getT().getTracks();
                    this.fill.setShader(null);
                    byte var54 = 0;
                    ModelSnapshot var72 = var35;

                    for (float[] var73 = var74; var54 + 1 < var73.length; var54 += 2) {
                        var18 = var73[var54];
                        var17 = var73[var54 + 1];
                        var23 = this.lastIdxBelow(var72.getPosX(), var18);
                        var74 = var72.getPosZ();
                        boolean var59;
                        if (var23 >= 0 && var23 < var74.length) {
                            var59 = true;
                        } else {
                            var59 = false;
                        }

                        if (var59) {
                            var2 = var74[var23];
                        } else {
                            var2 = 0.0F;
                        }

                        var3 = var2;
                        if (this.proj.map(var18, -var17, var3 + var13, this.tmp)) {
                            var17 = RangesKt.coerceIn(this.tmp[0], 0.0F, this.getWidth());
                            var2 = RangesKt.coerceIn(this.tmp[1], 0.0F, this.getHeight());
                            this.fill.setColor(Color.argb(170, 0, 0, 0));
                            var1.drawCircle(var17, var2, this.dp(4.5F), this.fill);
                            this.fill.setColor(Color.argb(230, 255, 40, 40));
                            var1.drawCircle(var17, var2, this.dp(3.5F), this.fill);
                        }
                    }
                }

                if (this.getT().s("lead_marker")) {
                    var2 = this.getT().pInt("LeadDetectionThreshold", 35) / 100.0F;
                    if (this.getT().getLeadOne().getStatus()) {
                        var33 = this.getT().getLeadOne();
                        if (this.getT().getLeadOne().getModelProb() < var2) {
                            var21 = -1;
                        } else {
                            var21 = Color.rgb(201, 34, 49);
                        }

                        this.drawLead(var1, var33, var13, var21, true);
                    }

                    if (this.getT().getLeadTwo().getStatus() && Math.abs(this.getT().getLeadTwo().getDRel() - this.getT().getLeadOne().getDRel()) > 3.0) {
                        this.drawLead(var1, this.getT().getLeadTwo(), var13, Color.rgb(201, 34, 49), false);
                    }
                }
            }
        }
    }

    private static final Pair<Boolean, Float> drawModel$custom(OverlayView this$0, String key, float def) {
        float v = this$0.getT().pFloat(key, def);
        return new Pair<>(Boolean.valueOf(Math.abs(v - def) > 0.001f), Float.valueOf(v));
    }

    private final int lastIdxBelow(float[] x, double maxD) {
        int idx = x.length - 1;
        int length = x.length;
        for (int i = 0; i < length; i++) {
            if (x[i] > maxD) {
                int idx2 = Math.max(0, i - 1);
                return idx2;
            }
        }
        return idx;
    }

    private final double halfM(float v, boolean metric) {
        double d = v;
        if (!metric) {
            d *= 0.3048d;
        }
        return d / 2.0d;
    }

    private final double smallHalfM(float v, boolean metric) {
        double d;
        double d2 = v;
        if (metric) {
            d = 200.0d;
        } else {
            d2 *= 0.0254d;
            d = 2.0d;
        }
        return d2 / d;
    }

    private final void drawPoly(Canvas c, float[] pts, Paint p) {
        this.path.rewind();
        this.path.moveTo(pts[0], pts[1]);
        for (int i = 2; i < pts.length; i += 2) {
            this.path.lineTo(pts[i], pts[i + 1]);
        }
        this.path.close();
        c.drawPath(this.path, p);
    }

    private final RectF polyBounds(float[] pts) {
        float minY = Float.MAX_VALUE;
        float maxY = -3.4028235E38f;
        for (int i = 1; i < pts.length; i += 2) {
            minY = Math.min(minY, pts[i]);
            maxY = Math.max(maxY, pts[i]);
        }
        return new RectF(0.0f, minY, getWidth(), maxY);
    }

    private final Shader vGradient(RectF b, int[] colors, float[] pos) {
        return new LinearGradient(0.0f, b.bottom, 0.0f, b.top, colors, pos, Shader.TileMode.CLAMP);
    }

    private final void drawPathFill(Canvas c, float[] poly, ModelSnapshot m, int maxIdx, float dt) {
        RectF b = polyBounds(poly);
        int i = 3;
        if (LaneTracks.Companion.pathState(getT()) == LaneTracks.PathState.OVERRIDE) {
            this.fill.setShader(vGradient(b, new int[]{Color.argb(150, 160, 164, 170), Color.argb(110, 160, 164, 170), Color.argb(20, 160, 164, 170)}, new float[]{0.0f, 0.5f, 1.0f}));
            drawPoly(c, poly, this.fill);
            this.fill.setShader(null);
            return;
        }
        boolean accelPath = getT().s("acceleration_path");
        float f = 0.0f;
        float f2 = 1.0f;
        float throttleTarget = getT().getAllowThrottle() ? 1.0f : 0.0f;
        this.blendThrottle += (throttleTarget - this.blendThrottle) * (dt / (dt + 0.25f));
        char c2 = 2;
        char c3 = 0;
        if (getT().s("rainbow_path")) {
            float hueBase = (float) ((((System.nanoTime() / 1.0E9d) * 5.0d) * getT().getVEgo()) % 360.0d);
            int[] cols = new int[12];
            int i2 = 0;
            while (i2 < 12) {
                float[] hsl = new float[i];
                hsl[c3] = ((i2 * 10.0f) + hueBase) % 360.0f;
                hsl[1] = f2;
                hsl[c2] = 0.5f;
                cols[i2] = Companion.withAlpha(ColorUtils.HSLToColor(hsl), RangesKt.coerceIn(127 - (i2 * 9), 25, 127));
                i2++;
                c3 = 0;
                c2 = 2;
                i = 3;
                f2 = 1.0f;
            }
            Paint paint = this.fill;
            float[] fArr = new float[12];
            for (int i3 = 0; i3 < 12; i3++) {
                fArr[i3] = i3 / 11.0f;
            }
            paint.setShader(vGradient(b, cols, fArr));
        } else if (accelPath && getT().getExperimentalMode()) {
            int k = poly.length / 4;
            ArrayList cols2 = new ArrayList();
            ArrayList pos = new ArrayList();
            float h = RangesKt.coerceAtLeast(b.height(), 1.0f);
            int i4 = 0;
            while (i4 < k) {
                float yScreen = poly[(i4 * 2) + 1];
                float lin = 1.0f - ((yScreen - b.top) / h);
                float[] accelX = m.getAccelX();
                int min = Math.min(i4, maxIdx);
                float ai2 = min >= 0 && min < accelX.length ? accelX[min] : f;
                float hue = RangesKt.coerceIn((35.0f * ai2) + 60.0f, f, 120.0f);
                float sat = Math.min(Math.abs(1.5f * ai2), 1.0f);
                float light = ((-0.32999998f) * sat) + 0.95f;
                int i5 = i4;
                float alpha = interp(lin, 0.375f, 0.75f, 0.4f, 0.0f);
                int col = ColorUtils.HSLToColor(new float[]{hue, sat, light});
                float p = RangesKt.coerceIn(1.0f - ((yScreen - b.top) / h), 0.0f, 1.0f);
                pos = pos;
                if (pos.isEmpty() || p > ((Number) CollectionsKt.last((List) pos)).floatValue()) {
                    cols2.add(Integer.valueOf(Companion.withAlpha(col, MathKt.roundToInt(255 * alpha))));
                    pos.add(Float.valueOf(p));
                }
                i4 = i5 + 1;
                f = 0.0f;
            }
            if (cols2.size() >= 2) {
                this.fill.setShader(vGradient(b, CollectionsKt.toIntArray(cols2), CollectionsKt.toFloatArray(pos)));
            } else {
                this.fill.setShader(null);
                this.fill.setColor(Color.argb(30, 255, 255, 255));
            }
        } else if (!accelPath) {
            int base = Companion.parseColor(Telemetry.pStr$default(getT(), "PathColor", null, 2, null), Color.rgb(48, 255, 156));
            this.fill.setShader(vGradient(b, new int[]{Companion.withAlpha(base, 255), Companion.withAlpha(base, 140), Companion.withAlpha(base, 26)}, new float[]{0.0f, 0.5f, 1.0f}));
        } else {
            int[] th = {Color.argb(102, 13, 248, 122), Color.argb(89, 114, 255, 92), Color.argb(0, 114, 255, 92)};
            int[] no = {Color.argb(102, 242, 242, 242), Color.argb(89, 242, 242, 242), Color.argb(0, 242, 242, 242)};
            int[] cols3 = new int[3];
            int i6 = 0;
            for (int i7 = 3; i6 < i7; i7 = 3) {
                cols3[i6] = ColorUtils.blendARGB(no[i6], th[i6], this.blendThrottle);
                i6++;
            }
            this.fill.setShader(vGradient(b, cols3, new float[]{0.0f, 0.5f, 1.0f}));
        }
        drawPoly(c, poly, this.fill);
        this.fill.setShader(null);
    }

    private final void drawPathEdges(Canvas c, float[] outer, float[] inner) {
        float[] strip = new float[outer.length + inner.length];
        System.arraycopy(outer, 0, strip, 0, outer.length);
        int k = inner.length / 2;
        for (int i = 0; i < k; i++) {
            int j = (k - 1) - i;
            strip[outer.length + (i * 2)] = inner[j * 2];
            strip[outer.length + (i * 2) + 1] = inner[(j * 2) + 1];
        }
        int base = Companion.parseColor(Telemetry.pStr$default(getT(), "PathEdgesColor", null, 2, null), Color.rgb(0, 255, 0));
        RectF b = polyBounds(strip);
        this.fill.setShader(vGradient(b, new int[]{Companion.withAlpha(base, 102), Companion.withAlpha(base, 89), Companion.withAlpha(base, 0)}, new float[]{0.0f, 0.5f, 1.0f}));
        this.path.rewind();
        this.path.setFillType(Path.FillType.EVEN_ODD);
        this.path.moveTo(outer[0], outer[1]);
        for (int i2 = 2; i2 < outer.length; i2 += 2) {
            this.path.lineTo(outer[i2], outer[i2 + 1]);
        }
        this.path.close();
        this.path.moveTo(inner[0], inner[1]);
        for (int i3 = 2; i3 < inner.length; i3 += 2) {
            this.path.lineTo(inner[i3], inner[i3 + 1]);
        }
        this.path.close();
        c.drawPath(this.path, this.fill);
        this.path.setFillType(Path.FillType.WINDING);
        this.fill.setShader(null);
    }

    private final void drawAmbient(Canvas c) {
        Detector d;
        Projection p;
        float top;
        float bot;
        float top2;
        float bot2;
        Projection p2 = this.proj;
        if (p2.getValid() && p2.getFrameW() != 0) {
            float l = (float) p2.getVideoTx();
            float top3 = (float) p2.getVideoTy();
            float r = ((float) (p2.getFrameW() * p2.getVideoScale())) + l;
            float bot3 = ((float) (p2.getFrameH() * p2.getVideoScale())) + top3;
            float f = 2.0f;
            if ((top3 >= 2.0f || bot3 <= getHeight() - 2.0f) && (d = this.detector) != null) {
                int[] amb = d.ambient(p2.getWide());
                if (amb == null && (amb = d.ambient(!p2.getWide())) == null) {
                    return;
                }
                int half = 0;
                while (half < 2) {
                    float y0 = half == 0 ? 0.0f : bot3;
                    float y1 = half == 0 ? top3 : getHeight();
                    if (y1 - y0 >= f) {
                        int i = 0;
                        while (i < 16) {
                            int col = amb[(half * 16) + i];
                            int k = ((half * 16) + i) * 3;
                            float tr = Color.red(col) * 0.8f;
                            float tg = Color.green(col) * 0.8f;
                            float tb = Color.blue(col) * 0.8f;
                            Projection p3 = p2;
                            if (this.ambNow[k] < 0.0f) {
                                this.ambNow[k] = tr;
                                this.ambNow[k + 1] = tg;
                                this.ambNow[k + 2] = tb;
                                top2 = top3;
                                bot2 = bot3;
                            } else {
                                float[] fArr = this.ambNow;
                                top2 = top3;
                                fArr[k] = fArr[k] + ((tr - this.ambNow[k]) * 0.08f);
                                float[] fArr2 = this.ambNow;
                                int i2 = k + 1;
                                bot2 = bot3;
                                fArr2[i2] = fArr2[i2] + ((tg - this.ambNow[k + 1]) * 0.08f);
                                float[] fArr3 = this.ambNow;
                                int i3 = k + 2;
                                fArr3[i3] = fArr3[i3] + ((tb - this.ambNow[k + 2]) * 0.08f);
                            }
                            this.ambBmp.setPixel(i, 0, Color.rgb((int) this.ambNow[k], (int) this.ambNow[k + 1], (int) this.ambNow[k + 2]));
                            i++;
                            p2 = p3;
                            top3 = top2;
                            bot3 = bot2;
                        }
                        p = p2;
                        top = top3;
                        bot = bot3;
                        c.drawBitmap(this.ambBmp, (Rect) null, new RectF(l, y0, r, y1), this.ambPaint);
                        float from = half == 0 ? top : bot;
                        float to = half != 0 ? getHeight() : 0.0f;
                        this.ambFade.setShader(new LinearGradient(0.0f, from, 0.0f, from + ((to - from) * 0.9f), Color.argb(40, 0, 0, 0), -16777216, Shader.TileMode.CLAMP));
                        c.drawRect(0.0f, y0, getWidth(), y1, this.ambFade);
                    } else {
                        p = p2;
                        top = top3;
                        bot = bot3;
                    }
                    half++;
                    p2 = p;
                    top3 = top;
                    bot3 = bot;
                    f = 2.0f;
                }
            }
        }
    }

    public final Detector getDetector() {
        return this.detector;
    }

    public final void setDetector(Detector detector) {
        this.detector = detector;
    }

    /* VF: from Vineflower (jadx: Code restructure failed;incorrect) */
    private final void drawDetections(Canvas var1) {
        Detector var8 = this.detector;
        if (var8 != null) {
            if (System.nanoTime() - var8.getDetsAtNs() <= 1000000000L) {
                float var3 = (float)this.proj.getVideoScale();
                float var5 = (float)this.proj.getVideoTx();
                float var6 = (float)this.proj.getVideoTy();

                for (Detector.Det var10 : var8.getDets()) {
                    int var7;
                    label58: {
                        label57: {
                            label56: {
                                label55: {
                                    String var11 = var10.getName();
                                    switch (var11.hashCode()) {
                                        case -991716523:
                                            if (var11.equals("person")) {
                                                break label55;
                                            }
                                            break;
                                        case 97920:
                                            if (var11.equals("bus")) {
                                                break label56;
                                            }
                                            break;
                                        case 98260:
                                            if (var11.equals("car")) {
                                                var7 = Color.rgb(64, 160, 255);
                                                break label58;
                                            }
                                            break;
                                        case 3023841:
                                            if (var11.equals("bike")) {
                                                break label55;
                                            }
                                            break;
                                        case 3357597:
                                            if (var11.equals("moto")) {
                                                break label55;
                                            }
                                            break;
                                        case 3540994:
                                            if (var11.equals("stop")) {
                                                break label57;
                                            }
                                            break;
                                        case 102970646:
                                            if (var11.equals("light")) {
                                                break label57;
                                            }
                                            break;
                                        case 110640223:
                                            if (var11.equals("truck")) {
                                                break label56;
                                            }
                                    }

                                    var7 = -1;
                                    break label58;
                                }

                                var7 = Color.rgb(255, 80, 120);
                                break label58;
                            }

                            var7 = Color.rgb(255, 170, 60);
                            break label58;
                        }

                        var7 = Color.rgb(90, 220, 120);
                    }

                    this.detBox.setColor(var7);
                    this.detBg.setColor(var7);
                    float var2 = var10.getX0() * var3 + var5;
                    float var4 = var10.getY0() * var3 + var6;
                    var1.drawRect(var2, var4, var10.getX1() * var3 + var5, var10.getY1() * var3 + var6, this.detBox);
                    String var14 = var10.getName();
                    var7 = (int)(var10.getScore() * 100);
                    String var13 = var14 + " " + var7;
                    var1.drawRect(var2, var4 - 32.0F, var2 + (this.detText.measureText(var13) + 12.0F), var4, this.detBg);
                    var1.drawText(var13, 6.0F + var2, var4 - 8.0F, this.detText);
                }
            }
        }
    }

    /* VF: from Vineflower (jadx: Removed duplicated region;incorrect) */
    private final void drawLead(Canvas var1, Lead var2, double var3, int var5, boolean var6) {
        double var7;
        label43: {
            ModelSnapshot var17 = this.getT().getModel();
            if (var17 != null) {
                boolean var15;
                if (var17.getPosX().length == 0) {
                    var15 = true;
                } else {
                    var15 = false;
                }

                if (!var15) {
                    int var16 = this.lastIdxBelow(var17.getPosX(), var2.getDRel());
                    float[] var23 = var17.getPosZ();
                    if (var16 >= 0 && var16 < var23.length) {
                        var15 = true;
                    } else {
                        var15 = false;
                    }

                    float var9;
                    if (var15) {
                        var9 = var23[var16];
                    } else {
                        var9 = 0.0F;
                    }

                    var7 = var9;
                    break label43;
                }
            }

            var7 = 0.0;
        }

        if (this.proj.map(var2.getDRel(), -var2.getYRel(), var7 + var3, this.tmp)) {
            float var18 = 0.0F;
            if (var2.getDRel() < 40.0F) {
                float var10 = (1.0F - var2.getDRel() / 40.0F) * 255.0F;
                var18 = var10;
                if (var2.getVRel() < 0.0F) {
                    var18 = var10 + -var2.getVRel() / 10.0F * 255.0F;
                }

                var18 = Math.min(var18, 255.0F);
            }

            float var20 = this.getResources().getDisplayMetrics().density / 2.0F;
            float var12 = RangesKt.coerceIn(750.0F / (var2.getDRel() / 3.0F + 30.0F), 15.0F, 30.0F) * 2.35F * var20;
            var20 = RangesKt.coerceIn(this.tmp[0], 0.0F, this.getWidth() - var12 / 2);
            float var11 = Math.min(this.tmp[1], this.getHeight() - 0.6F * var12);
            float var13 = var12 / 5;
            float var14 = var12 / 10;
            this.path.rewind();
            this.path.moveTo(var20 + var12 * 1.35F + var13, var11 + var12 + var14);
            this.path.lineTo(var20, var11 - var14);
            this.path.lineTo(var20 - var12 * 1.35F - var13, var11 + var12 + var14);
            this.path.close();
            this.fill.setShader(null);
            this.fill.setColor(Color.rgb(218, 202, 37));
            var1.drawPath(this.path, this.fill);
            this.path.rewind();
            this.path.moveTo(var12 * 1.25F + var20, var11 + var12);
            this.path.lineTo(var20, var11);
            this.path.lineTo(var20 - 1.25F * var12, var11 + var12);
            this.path.close();
            this.fill.setColor(Companion.withAlpha(var5, MathKt.roundToInt(var18)));
            var1.drawPath(this.path, this.fill);
        }
    }

    private final void drawBorder(Canvas c) {
        int col = this.uiStatus.getColor();
        boolean cscOn = getT().s("csc_glow") && getT().getStarted() && (getT().getCscControlling() || getT().getCscTraining()) && getT().getCruiseEnabled();
        if (cscOn) {
            int glow = getT().getCscTraining() ? Color.rgb(112, 192, 216) : Companion.cscColor(getT().getRoadCurvature());
            float breathe = (((float) Math.sin((((System.nanoTime() / 1.0E9d) * 2) * 3.141592653589793d) / 4.5d)) * 0.25f) + 0.75f;
            col = ColorUtils.blendARGB(col, glow, 0.85f);
            float span = dp(50.0f);
            int a = MathKt.roundToInt(200 * breathe);
            this.fill.setShader(new LinearGradient(0.0f, 0.0f, span, 0.0f, Companion.withAlpha(glow, a), Companion.withAlpha(glow, 0), Shader.TileMode.CLAMP));
            c.drawRect(0.0f, 0.0f, span, getHeight(), this.fill);
            this.fill.setShader(new LinearGradient(getWidth(), 0.0f, getWidth() - span, 0.0f, Companion.withAlpha(glow, a), Companion.withAlpha(glow, 0), Shader.TileMode.CLAMP));
            c.drawRect(getWidth() - span, 0.0f, getWidth(), getHeight(), this.fill);
            this.fill.setShader(new LinearGradient(0.0f, 0.0f, 0.0f, span, Companion.withAlpha(glow, a), Companion.withAlpha(glow, 0), Shader.TileMode.CLAMP));
            c.drawRect(0.0f, 0.0f, getWidth(), span, this.fill);
            this.fill.setShader(new LinearGradient(0.0f, getHeight(), 0.0f, getHeight() - span, Companion.withAlpha(glow, a), Companion.withAlpha(glow, 0), Shader.TileMode.CLAMP));
            c.drawRect(0.0f, getHeight() - span, getWidth(), getHeight(), this.fill);
            this.fill.setShader(null);
        }
        float bw = dp(6.0f);
        this.stroke.setColor(col);
        this.stroke.setStrokeWidth(bw);
        float r = dp(26.0f);
        float f = 2;
        c.drawRoundRect(new RectF(bw / f, bw / f, getWidth() - (bw / f), getHeight() - (bw / f)), r, r, this.stroke);
    }

    private final void drawSignalFlashes(Canvas c) {
        int color;
        boolean signals;
        boolean bsm;
        RectF rect;
        if (getT().getStarted()) {
            boolean signals2 = getT().s("signal_flashes");
            boolean bsm2 = getT().s("blindspot_flashes");
            int side = 0;
            while (side < 2) {
                Telemetry t = getT();
                boolean blink = side == 0 ? t.getLeftBlinker() : t.getRightBlinker();
                Telemetry t2 = getT();
                boolean blind = side == 0 ? t2.getLeftBlindspot() : t2.getRightBlindspot();
                boolean showBlink = signals2 && blink;
                boolean showBlind = bsm2 && blind;
                if (showBlink || showBlind) {
                    int period = (showBlink && showBlind) ? 250 : 500;
                    boolean on = (this.blinkPhase / ((long) period)) % ((long) 2) == 0;
                    if (showBlink && showBlind) {
                        color = on ? Color.rgb(255, 214, 0) : Color.rgb(201, 34, 49);
                    } else if (!showBlink) {
                        color = Color.rgb(201, 34, 49);
                    } else if (on) {
                        color = Color.rgb(255, 214, 0);
                    } else {
                        signals = signals2;
                        bsm = bsm2;
                    }
                    float wdt = dp(120.0f);
                    if (side == 0) {
                        rect = new RectF(0.0f, 0.0f, wdt, getHeight());
                        signals = signals2;
                    } else {
                        signals = signals2;
                        rect = new RectF(getWidth() - wdt, 0.0f, getWidth(), getHeight());
                    }
                    bsm = bsm2;
                    this.fill.setShader(new LinearGradient(rect.left, 0.0f, rect.right, 0.0f, side == 0 ? new int[]{Companion.withAlpha(color, 170), Companion.withAlpha(color, 0)} : new int[]{Companion.withAlpha(color, 0), Companion.withAlpha(color, 170)}, (float[]) null, Shader.TileMode.CLAMP));
                    c.drawRect(rect, this.fill);
                    this.fill.setShader(null);
                } else {
                    signals = signals2;
                    bsm = bsm2;
                }
                side++;
                signals2 = signals;
                bsm2 = bsm;
            }
        }
    }

    /* VF: from Vineflower (jadx: Removed duplicated region;incorrect) */
    private final void drawTorqueBar(Canvas var1, float var2) {
        this.torqueSmooth = 0.25F * RangesKt.coerceIn(this.getT().getOutTorque(), -1.0F, 1.0F) + this.torqueSmooth * 0.75F;
        float var3 = this.getWidth() * 1.25F;
        float var4 = this.getWidth() / 2.0F;
        float var5 = this.getHeight() + var3 - this.dp(22.0F);
        int var7;
        if (this.getT().getEnabled() && !this.getT().getPauseLateral()) {
            var7 = 1;
        } else {
            var7 = 0;
        }

        this.stroke.setStrokeCap(android.graphics.Paint.Cap.ROUND);
        Paint var8 = this.stroke;
        if ((var7 != 0)) {
            var7 = (byte)100;
        } else {
            var7 = (byte)45;
        }

        var8.setColor(Color.argb(var7, 255, 255, 255));
        this.stroke.setStrokeWidth(this.dp(5.0F));
        RectF var9 = new RectF(var4 - var3, var5 - var3, var4 + var3, var5 + var3);
        var1.drawArc(var9, -90.0F - 12.7F, 12.7F * 2, false, this.stroke);
        var2 = Math.abs(this.torqueSmooth);
        if (var2 > 0.01F) {
            label30: {
                short var13;
                if (var2 > 0.75F) {
                    var13 = 115;
                } else {
                    if (!(var2 > 0.5F)) {
                        var7 = -1;
                        break label30;
                    }

                    var13 = 200;
                }

                var7 = Color.rgb(255, var13, 0);
            }

            this.stroke.setColor(Companion.withAlpha(var7, 235));
            this.stroke.setStrokeWidth(this.dp(9.0F));
            float var6 = 12.7F * var2;
            if (this.torqueSmooth > 0.0F) {
                var2 = -90.0F - var6;
                var8 = this.stroke;
            } else {
                var8 = this.stroke;
                var2 = -90.0F;
            }

            var1.drawArc(var9, var2, var6, false, var8);
        }

        this.fill.setShader(null);
        this.fill.setColor(Color.argb(230, 182, 182, 182));
        var1.drawCircle(var4, var5 - var3, this.dp(4.0F), this.fill);
        this.stroke.setStrokeCap(android.graphics.Paint.Cap.BUTT);
    }

    private final float interp(float x, float x0, float x1, float y0, float y1) {
        return x <= x0 ? y0 : x >= x1 ? y1 : (((y1 - y0) * (x - x0)) / (x1 - x0)) + y0;
    }

    /* compiled from: OverlayView.kt */
    public static final class Companion_ {
        public /* synthetic */ Companion_(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion_() {
        }

        public final int cscColor(float curvature) {
            Integer invoke;
            OverlayView$Companion$cscColor$b$1 overlayView$Companion$cscColor$b$1;
            Integer valueOf;
            Integer valueOf2;
            float f;
            float cv = Math.abs(curvature);
            float tIn = RangesKt.coerceIn((cv - 5.0E-4f) / 0.0195f, 0.0f, 1.0f);
            float tt = (0.3f * tIn) + 0.7f;
            int green = Color.rgb(34, 197, 94);
            int amber = Color.rgb(251, 191, 36);
            int orange = Color.rgb(234, 88, 12);
            int red = Color.rgb(201, 34, 49);
            KFunction b = OverlayView$Companion$cscColor$b$1.INSTANCE;
            if (tt < 0.5f) {
                overlayView$Companion$cscColor$b$1 = (Function3) b;
                valueOf = Integer.valueOf(green);
                valueOf2 = Integer.valueOf(amber);
                f = tt / 0.5f;
            } else {
                if (tt >= 0.6f) {
                    invoke = ((Function3) b).invoke((OverlayView$Companion$cscColor$b$1) Integer.valueOf(orange), Integer.valueOf(red), (Integer) Float.valueOf(RangesKt.coerceIn((tt - 0.6f) / 0.4f, 0.0f, 1.0f)));
                    return invoke.intValue();
                }
                overlayView$Companion$cscColor$b$1 = (Function3) b;
                valueOf = Integer.valueOf(amber);
                valueOf2 = Integer.valueOf(orange);
                f = (tt - 0.5f) / 0.1f;
            }
            invoke = overlayView$Companion$cscColor$b$1.invoke((OverlayView$Companion$cscColor$b$1) valueOf, valueOf2, (Integer) Float.valueOf(f));
            return invoke.intValue();
        }

        public final int withAlpha(int c, int a) {
            return Color.argb(RangesKt.coerceIn(a, 0, 255), Color.red(c), Color.green(c), Color.blue(c));
        }

        public final int parseColor(String s, int def) {
            String str;
            Intrinsics.checkNotNullParameter(s, "s");
            if (StringsKt.isBlank(s)) {
                return def;
            }
            try {
                String h = StringsKt.removePrefix(StringsKt.trim((CharSequence) s).toString(), (CharSequence) "#");
                if (h.length() == 6) {
                    str = "#" + h;
                } else {
                    if (h.length() != 8) {
                        return def;
                    }
                    String substring = h.substring(6);
                    Intrinsics.checkNotNullExpressionValue(substring, "substring(...)");
                    String substring2 = h.substring(0, 6);
                    Intrinsics.checkNotNullExpressionValue(substring2, "substring(...)");
                    str = "#" + substring + substring2;
                }
                return Color.parseColor(str);
            } catch (Exception e) {
                return def;
            }
        }
    }
}
