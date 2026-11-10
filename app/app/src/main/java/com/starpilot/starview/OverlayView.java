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

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public OverlayView(Context ctx, AttributeSet attrs) {
        super(ctx, attrs);
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        this.proj = new Projection();
        this.uiStatus = UiStatus.DISENGAGED;
        Paint $this$fill_u24lambda_u240 = new Paint(1);
        $this$fill_u24lambda_u240.setStyle(Paint.Style.FILL);
        this.fill = $this$fill_u24lambda_u240;
        Paint $this$stroke_u24lambda_u241 = new Paint(1);
        $this$stroke_u24lambda_u241.setStyle(Paint.Style.STROKE);
        this.stroke = $this$stroke_u24lambda_u241;
        Paint $this$text_u24lambda_u242 = new Paint(1);
        $this$text_u24lambda_u242.setColor(-1);
        $this$text_u24lambda_u242.setTextAlign(Paint.Align.CENTER);
        $this$text_u24lambda_u242.setTypeface(Typeface.DEFAULT_BOLD);
        this.text = $this$text_u24lambda_u242;
        Paint $this$textOutline_u24lambda_u243 = new Paint(1);
        $this$textOutline_u24lambda_u243.setColor(-16777216);
        $this$textOutline_u24lambda_u243.setTextAlign(Paint.Align.CENTER);
        $this$textOutline_u24lambda_u243.setStyle(Paint.Style.STROKE);
        $this$textOutline_u24lambda_u243.setStrokeWidth(3.0f);
        $this$textOutline_u24lambda_u243.setTypeface(Typeface.DEFAULT_BOLD);
        this.textOutline = $this$textOutline_u24lambda_u243;
        this.path = new Path();
        this.tmp = new float[2];
        this.blendThrottle = 1.0f;
        this.tracks = new LaneTracks(0.0f, 1, null);
        Bitmap createBitmap = Bitmap.createBitmap(16, 1, Bitmap.Config.ARGB_8888);
        Intrinsics.checkNotNullExpressionValue(createBitmap, "createBitmap(...)");
        this.ambBmp = createBitmap;
        this.ambPaint = new Paint(2);
        this.ambFade = new Paint();
        float[] fArr = new float[96];
        for (int i = 0; i < 96; i++) {
            fArr[i] = -1.0f;
        }
        this.ambNow = fArr;
        Paint $this$detBox_u24lambda_u246 = new Paint(1);
        $this$detBox_u24lambda_u246.setStyle(Paint.Style.STROKE);
        $this$detBox_u24lambda_u246.setStrokeWidth(4.0f);
        this.detBox = $this$detBox_u24lambda_u246;
        Paint $this$detText_u24lambda_u247 = new Paint(1);
        $this$detText_u24lambda_u247.setColor(-1);
        $this$detText_u24lambda_u247.setTextSize(26.0f);
        $this$detText_u24lambda_u247.setFakeBoldText(true);
        this.detText = $this$detText_u24lambda_u247;
        Paint $this$detBg_u24lambda_u248 = new Paint();
        $this$detBg_u24lambda_u248.setStyle(Paint.Style.FILL);
        this.detBg = $this$detBg_u24lambda_u248;
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

    /* JADX WARN: Removed duplicated region for block: B:20:0x0065  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean updateCalibration() {
        boolean changed;
        float[] rpy = getT().getCalRpy();
        if (this.proj.getRpy()[0] == ((double) rpy[0])) {
            if (this.proj.getRpy()[1] == ((double) rpy[1])) {
                if (this.proj.getRpy()[2] == ((double) rpy[2])) {
                    if (this.proj.getPathOffsetZ() == ((double) getT().getCalHeight())) {
                        changed = false;
                        if (changed) {
                            this.proj.setRpy(new double[]{rpy[0], rpy[1], rpy[2]});
                            this.proj.setPathOffsetZ(getT().getCalHeight());
                            this.proj.update();
                        }
                        return changed;
                    }
                }
            }
        }
        changed = true;
        if (changed) {
        }
        return changed;
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

    /* JADX WARN: Removed duplicated region for block: B:49:0x024b A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:71:0x023e A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final void drawModel(Canvas c, float dt) {
        int n;
        double d;
        float rew;
        float llw;
        double maxDist;
        float pwv;
        boolean metric;
        int n2;
        boolean z;
        float f;
        Shader shader;
        Shader shader2;
        int i;
        float[] outer;
        float[] poly;
        LaneTracks.Line nearL;
        double d2;
        float[] poly2;
        LaneTracks.PathState state;
        int i2;
        ModelSnapshot m = getT().getModel();
        if (m != null && (n = m.getPosX().length) >= 2) {
            Lead lead = getT().getLeadOne();
            boolean metric2 = getT().isMetric();
            Pair<Boolean, Float> drawModel$custom = drawModel$custom(this, "PathWidth", 6.1f);
            boolean cpw = drawModel$custom.component1().booleanValue();
            float pwv2 = drawModel$custom.component2().floatValue();
            Pair<Boolean, Float> drawModel$custom2 = drawModel$custom(this, "LaneLinesWidth", 4.0f);
            boolean cll = drawModel$custom2.component1().booleanValue();
            float llw2 = drawModel$custom2.component2().floatValue();
            Pair<Boolean, Float> drawModel$custom3 = drawModel$custom(this, "RoadEdgesWidth", 2.0f);
            boolean cre = drawModel$custom3.component1().booleanValue();
            float rew2 = drawModel$custom3.component2().floatValue();
            Pair<Boolean, Float> drawModel$custom4 = drawModel$custom(this, "PathEdgeWidth", 20.0f);
            drawModel$custom4.component1().booleanValue();
            float pew = drawModel$custom4.component2().floatValue();
            double pathHalf = cpw ? halfM(pwv2, metric2) : 0.9d;
            double d3 = 0.025d;
            if (cll) {
                d = smallHalfM(llw2, metric2);
            } else {
                d = 0.025d;
            }
            double laneHalf = d;
            if (cre) {
                d3 = smallHalfM(rew2, metric2);
            }
            double edgeHalf = d3;
            float edgePct = getT().s("path_edges") ? RangesKt.coerceIn(pew / 100.0f, 0.0f, 1.0f) : 0.0f;
            double unclippedMax = RangesKt.coerceIn(m.getPosX()[n - 1], 10.0d, 100.0d);
            if (!lead.getStatus()) {
                rew = rew2;
                llw = llw2;
                maxDist = unclippedMax;
            } else {
                double leadD = lead.getDRel() * 2.0d;
                rew = rew2;
                llw = llw2;
                maxDist = RangesKt.coerceIn(leadD - Math.min(leadD * 0.35d, 10.0d), 0.0d, unclippedMax);
            }
            int maxIdx = lastIdxBelow(m.getPosX(), maxDist);
            double z2 = this.proj.getPathOffsetZ();
            int laneColor = Companion.parseColor(Telemetry.pStr$default(getT(), "LaneLinesColor", null, 2, null), -1);
            int lcDir = getT().s("lane_centering_cue") ? LaneCentering.direction$default(LaneCentering.INSTANCE, getT(), 0L, 2, null) : 0;
            LaneTracks.update$default(this.tracks, m, getT(), 0.05f, false, null, 16, null);
            LaneTracks.PathState state2 = LaneTracks.Companion.pathState(getT());
            int i3 = 255;
            if (!getT().s("lane_lines")) {
                pwv = pwv2;
                metric = metric2;
                n2 = n;
            } else {
                Triple<LaneTracks.Line, LaneTracks.Line, LaneTracks.Line> inner = this.tracks.inner();
                LaneTracks.Line leftL = inner.component1();
                LaneTracks.Line rightL = inner.component2();
                LaneTracks.Line nearL2 = inner.component3();
                Iterator<LaneTracks.Line> it = this.tracks.getLines().iterator();
                Intrinsics.checkNotNullExpressionValue(it, "iterator(...)");
                while (it.hasNext()) {
                    LaneTracks.Line next = it.next();
                    Intrinsics.checkNotNullExpressionValue(next, "next(...)");
                    LaneTracks.Line l = next;
                    float pwv3 = pwv2;
                    int a = MathKt.roundToInt(l.getAlpha() * 0.7f * i3);
                    if (a > 2) {
                        boolean effortLine = l == nearL2 && state2 == LaneTracks.PathState.ENGAGED;
                        boolean metric3 = metric2;
                        int n3 = n;
                        double alpha = l.getAlpha() * laneHalf;
                        if (effortLine) {
                            nearL = nearL2;
                            if (this.tracks.getEffort() > 0.55f) {
                                d2 = 1.8d;
                                double w = alpha * d2;
                                poly2 = this.proj.ribbon(l.getX(), l.getY(), l.getZ(), w, 0.0d, lastIdxBelow(l.getX(), unclippedMax), true);
                                if (poly2 != null) {
                                    nearL2 = nearL;
                                    pwv2 = pwv3;
                                    metric2 = metric3;
                                    n = n3;
                                    i3 = 255;
                                } else {
                                    boolean lcLine = (lcDir > 0 && l == rightL) || (lcDir < 0 && l == leftL);
                                    int col = lcLine ? Color.rgb(0, 176, 220) : laneColor;
                                    if (effortLine) {
                                        col = LaneTracks.Companion.effortColor(col, this.tracks.getEffort());
                                    }
                                    this.fill.setShader(null);
                                    Paint paint = this.fill;
                                    Companion_ companion = Companion;
                                    if (effortLine) {
                                        state = state2;
                                        if (this.tracks.getEffort() > 0.55f) {
                                            i2 = Math.max(a, 220);
                                            paint.setColor(companion.withAlpha(col, i2));
                                            drawPoly(c, poly2, this.fill);
                                            nearL2 = nearL;
                                            pwv2 = pwv3;
                                            metric2 = metric3;
                                            n = n3;
                                            state2 = state;
                                            i3 = 255;
                                        }
                                    } else {
                                        state = state2;
                                    }
                                    i2 = a;
                                    paint.setColor(companion.withAlpha(col, i2));
                                    drawPoly(c, poly2, this.fill);
                                    nearL2 = nearL;
                                    pwv2 = pwv3;
                                    metric2 = metric3;
                                    n = n3;
                                    state2 = state;
                                    i3 = 255;
                                }
                            }
                        } else {
                            nearL = nearL2;
                        }
                        d2 = 1.0d;
                        double w2 = alpha * d2;
                        poly2 = this.proj.ribbon(l.getX(), l.getY(), l.getZ(), w2, 0.0d, lastIdxBelow(l.getX(), unclippedMax), true);
                        if (poly2 != null) {
                        }
                    } else {
                        pwv2 = pwv3;
                        i3 = 255;
                    }
                }
                pwv = pwv2;
                metric = metric2;
                n2 = n;
            }
            if (getT().s("road_edges")) {
                int i4 = 0;
                int length = m.getEdgeX().length;
                while (i4 < length) {
                    float std = i4 < m.getEdgeStd().length ? m.getEdgeStd()[i4] : 1.0f;
                    int a2 = MathKt.roundToInt(RangesKt.coerceIn(1.0f - std, 0.0f, 1.0f) * 255);
                    if (a2 > 2 && (poly = this.proj.ribbon(m.getEdgeX()[i4], m.getEdgeY()[i4], m.getEdgeZ()[i4], edgeHalf, 0.0d, lastIdxBelow(m.getEdgeX()[i4], unclippedMax), true)) != null) {
                        this.fill.setShader(null);
                        this.fill.setColor(Color.argb(a2, 255, 0, 0));
                        drawPoly(c, poly, this.fill);
                    }
                    i4++;
                }
                z = false;
                f = 0.0f;
                shader = null;
            } else {
                z = false;
                f = 0.0f;
                shader = null;
            }
            float[] inner2 = this.proj.ribbon(m.getPosX(), m.getPosY(), m.getPosZ(), pathHalf * (1 - edgePct), z2, maxIdx, false);
            if (inner2 != null) {
                shader2 = shader;
                drawPathFill(c, inner2, m, maxIdx, dt);
                if (edgePct > f && (outer = this.proj.ribbon(m.getPosX(), m.getPosY(), m.getPosZ(), pathHalf, z2, maxIdx, false)) != null) {
                    drawPathEdges(c, outer, inner2);
                }
            } else {
                shader2 = shader;
            }
            if (getT().s("radar_tracks")) {
                float[] tr = getT().getTracks();
                this.fill.setShader(shader2);
                int i5 = 0;
                while (i5 + 1 < tr.length) {
                    float d4 = tr[i5];
                    float yRel = tr[i5 + 1];
                    int i6 = i5 + 2;
                    float[] tr2 = tr;
                    int zi = lastIdxBelow(m.getPosX(), d4);
                    float[] posZ = m.getPosZ();
                    double zz = zi >= 0 && zi < posZ.length ? posZ[zi] : f;
                    float[] inner3 = inner2;
                    ModelSnapshot m2 = m;
                    if (this.proj.map(d4, -yRel, zz + z2, this.tmp)) {
                        float x = RangesKt.coerceIn(this.tmp[0], 0.0f, getWidth());
                        float y = RangesKt.coerceIn(this.tmp[1], 0.0f, getHeight());
                        this.fill.setColor(Color.argb(170, 0, 0, 0));
                        c.drawCircle(x, y, dp(4.5f), this.fill);
                        this.fill.setColor(Color.argb(230, 255, 40, 40));
                        c.drawCircle(x, y, dp(3.5f), this.fill);
                    }
                    i5 = i6;
                    tr = tr2;
                    m = m2;
                    inner2 = inner3;
                    f = 0.0f;
                }
            }
            if (getT().s("lead_marker")) {
                float thr = getT().pInt("LeadDetectionThreshold", 35) / 100.0f;
                if (getT().getLeadOne().getStatus()) {
                    i = 201;
                    drawLead(c, getT().getLeadOne(), z2, getT().getLeadOne().getModelProb() < thr ? -1 : Color.rgb(201, 34, 49), true);
                } else {
                    i = 201;
                }
                if (!getT().getLeadTwo().getStatus() || Math.abs(getT().getLeadTwo().getDRel() - getT().getLeadOne().getDRel()) <= 3.0d) {
                    return;
                }
                drawLead(c, getT().getLeadTwo(), z2, Color.rgb(i, 34, 49), false);
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

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0058, code lost:
    
        if (r7.equals("truck") == false) goto L42;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x00a3, code lost:
    
        r7 = android.graphics.Color.rgb(255, 170, 60);
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0061, code lost:
    
        if (r7.equals("light") == false) goto L42;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x006d, code lost:
    
        r7 = android.graphics.Color.rgb(90, 220, 120);
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x006a, code lost:
    
        if (r7.equals("stop") == false) goto L42;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x007c, code lost:
    
        if (r7.equals("moto") == false) goto L42;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x00b5, code lost:
    
        r7 = android.graphics.Color.rgb(255, 80, 120);
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0085, code lost:
    
        if (r7.equals("bike") == false) goto L42;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x00a0, code lost:
    
        if (r7.equals("bus") == false) goto L42;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x00b2, code lost:
    
        if (r7.equals("person") == false) goto L42;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final void drawDetections(Canvas c) {
        int col;
        Detector d = this.detector;
        if (d != null && System.nanoTime() - d.getDetsAtNs() <= 1000000000) {
            float s = (float) this.proj.getVideoScale();
            float tx = (float) this.proj.getVideoTx();
            float ty = (float) this.proj.getVideoTy();
            for (Detector.Det o : d.getDets()) {
                String name = o.getName();
                switch (name.hashCode()) {
                    case -991716523:
                        break;
                    case 97920:
                        break;
                    case 98260:
                        if (name.equals("car")) {
                            col = Color.rgb(64, 160, 255);
                            break;
                        }
                        col = -1;
                        break;
                    case 3023841:
                        break;
                    case 3357597:
                        break;
                    case 3540994:
                        break;
                    case 102970646:
                        break;
                    case 110640223:
                        break;
                    default:
                        col = -1;
                        break;
                }
                this.detBox.setColor(col);
                this.detBg.setColor(col);
                float l = (o.getX0() * s) + tx;
                float tp = (o.getY0() * s) + ty;
                float r = (o.getX1() * s) + tx;
                float b = (o.getY1() * s) + ty;
                c.drawRect(l, tp, r, b, this.detBox);
                String label = o.getName() + " " + ((int) (o.getScore() * 100));
                float w = this.detText.measureText(label) + 12.0f;
                c.drawRect(l, tp - 32.0f, l + w, tp, this.detBg);
                c.drawText(label, 6.0f + l, tp - 8.0f, this.detText);
                d = d;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0059 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:19:0x005a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final void drawLead(Canvas c, Lead l, double z, int color, boolean showMetrics) {
        double zAt;
        ModelSnapshot m = getT().getModel();
        if (m != null) {
            if (!(m.getPosX().length == 0)) {
                int i = lastIdxBelow(m.getPosX(), l.getDRel());
                float[] posZ = m.getPosZ();
                zAt = i >= 0 && i < posZ.length ? posZ[i] : 0.0f;
                if (this.proj.map(l.getDRel(), -l.getYRel(), zAt + z, this.tmp)) {
                    return;
                }
                float fillA = 0.0f;
                if (l.getDRel() < 40.0f) {
                    float fillA2 = (1.0f - (l.getDRel() / 40.0f)) * 255.0f;
                    float fillA3 = l.getVRel();
                    if (fillA3 < 0.0f) {
                        fillA2 += ((-l.getVRel()) / 10.0f) * 255.0f;
                    }
                    fillA = Math.min(fillA2, 255.0f);
                }
                float scale = getResources().getDisplayMetrics().density / 2.0f;
                float sz = RangesKt.coerceIn(750.0f / ((l.getDRel() / 3.0f) + 30.0f), 15.0f, 30.0f) * 2.35f * scale;
                float x = RangesKt.coerceIn(this.tmp[0], 0.0f, getWidth() - (sz / 2));
                float y = Math.min(this.tmp[1], getHeight() - (0.6f * sz));
                float gxo = sz / 5;
                float gyo = sz / 10;
                this.path.rewind();
                this.path.moveTo(x + (sz * 1.35f) + gxo, y + sz + gyo);
                this.path.lineTo(x, y - gyo);
                this.path.lineTo((x - (sz * 1.35f)) - gxo, y + sz + gyo);
                this.path.close();
                this.fill.setShader(null);
                this.fill.setColor(Color.rgb(218, 202, 37));
                c.drawPath(this.path, this.fill);
                this.path.rewind();
                this.path.moveTo((sz * 1.25f) + x, y + sz);
                this.path.lineTo(x, y);
                this.path.lineTo(x - (1.25f * sz), y + sz);
                this.path.close();
                this.fill.setColor(Companion.withAlpha(color, MathKt.roundToInt(fillA)));
                c.drawPath(this.path, this.fill);
                return;
            }
        }
        zAt = 0.0d;
        if (this.proj.map(l.getDRel(), -l.getYRel(), zAt + z, this.tmp)) {
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

    /* JADX WARN: Removed duplicated region for block: B:17:0x00dc  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x00e3  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final void drawTorqueBar(Canvas c, float dt) {
        int col;
        int i;
        boolean z;
        Paint paint;
        float f;
        float torque = RangesKt.coerceIn(getT().getOutTorque(), -1.0f, 1.0f);
        this.torqueSmooth = (0.25f * torque) + (this.torqueSmooth * 0.75f);
        float r = getWidth() * 1.25f;
        float cx = getWidth() / 2.0f;
        float cy = (getHeight() + r) - dp(22.0f);
        boolean active = getT().getEnabled() && !getT().getPauseLateral();
        this.stroke.setStrokeCap(Paint.Cap.ROUND);
        this.stroke.setColor(Color.argb(active ? 100 : 45, 255, 255, 255));
        this.stroke.setStrokeWidth(dp(5.0f));
        RectF oval = new RectF(cx - r, cy - r, cx + r, cy + r);
        c.drawArc(oval, (-90.0f) - 12.7f, 12.7f * 2, false, this.stroke);
        float u = Math.abs(this.torqueSmooth);
        if (u > 0.01f) {
            if (u > 0.75f) {
                i = 115;
            } else if (u > 0.5f) {
                i = 200;
            } else {
                col = -1;
                this.stroke.setColor(Companion.withAlpha(col, 235));
                this.stroke.setStrokeWidth(dp(9.0f));
                float sweep = 12.7f * u;
                if (this.torqueSmooth > 0.0f) {
                    z = false;
                    paint = this.stroke;
                    f = -90.0f;
                } else {
                    f = (-90.0f) - sweep;
                    z = false;
                    paint = this.stroke;
                }
                c.drawArc(oval, f, sweep, z, paint);
            }
            col = Color.rgb(255, i, 0);
            this.stroke.setColor(Companion.withAlpha(col, 235));
            this.stroke.setStrokeWidth(dp(9.0f));
            float sweep2 = 12.7f * u;
            if (this.torqueSmooth > 0.0f) {
            }
            c.drawArc(oval, f, sweep2, z, paint);
        }
        this.fill.setShader(null);
        this.fill.setColor(Color.argb(230, 182, 182, 182));
        c.drawCircle(cx, cy - r, dp(4.0f), this.fill);
        this.stroke.setStrokeCap(Paint.Cap.BUTT);
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
