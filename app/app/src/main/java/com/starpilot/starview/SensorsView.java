package com.starpilot.starview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.view.View;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;

/* compiled from: SensorsActivity.kt */
public final class SensorsView extends View {
    private double[] anchor;
    private final Paint big;
    private final Map<String, Integer> colors;
    private final float d;
    private final Paint dim;
    private final ArrayList<float[]> dr;
    private final int drColor;
    private final Paint fill;
    private final Paint grid;
    private final Paint head;
    private final HashMap<String, Long> lastAdd;
    private double lat0;
    private final Paint line;
    private double lon0;
    private final Paint panel;
    private final LinkedHashMap<String, ArrayList<float[]>> trails;
    private final Paint txt;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SensorsView(Context ctx) {
        super(ctx);
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        this.d = getResources().getDisplayMetrics().density;
        this.lat0 = Double.NaN;
        this.lon0 = Double.NaN;
        this.trails = new LinkedHashMap<>();
        this.lastAdd = new HashMap<>();
        this.dr = new ArrayList<>();
        this.colors = MapsKt.mapOf(TuplesKt.to("ublox", Integer.valueOf(Color.rgb(111, 227, 161))), TuplesKt.to("android", Integer.valueOf(Color.rgb(90, 170, 255))), TuplesKt.to("qcomdiag", Integer.valueOf(Color.rgb(200, 140, 255))), TuplesKt.to("map", -1));
        this.drColor = Color.rgb(255, 170, 60);
        Paint $this$txt_u24lambda_u240 = new Paint(1);
        $this$txt_u24lambda_u240.setColor(Color.rgb(223, 230, 243));
        $this$txt_u24lambda_u240.setTextSize(14 * this.d);
        this.txt = $this$txt_u24lambda_u240;
        Paint $this$dim_u24lambda_u241 = new Paint(this.txt);
        $this$dim_u24lambda_u241.setColor(Color.rgb(140, 155, 180));
        $this$dim_u24lambda_u241.setTextSize(this.d * 12.5f);
        this.dim = $this$dim_u24lambda_u241;
        Paint $this$head_u24lambda_u242 = new Paint(this.txt);
        $this$head_u24lambda_u242.setColor(-1);
        $this$head_u24lambda_u242.setTextSize(16 * this.d);
        $this$head_u24lambda_u242.setFakeBoldText(true);
        this.head = $this$head_u24lambda_u242;
        Paint $this$big_u24lambda_u243 = new Paint(this.txt);
        $this$big_u24lambda_u243.setTextSize(22 * this.d);
        $this$big_u24lambda_u243.setFakeBoldText(true);
        this.big = $this$big_u24lambda_u243;
        Paint $this$line_u24lambda_u244 = new Paint(1);
        $this$line_u24lambda_u244.setStyle(Paint.Style.STROKE);
        $this$line_u24lambda_u244.setStrokeWidth(this.d * 2.5f);
        $this$line_u24lambda_u244.setStrokeJoin(Paint.Join.ROUND);
        this.line = $this$line_u24lambda_u244;
        this.fill = new Paint(1);
        Paint $this$grid_u24lambda_u245 = new Paint();
        $this$grid_u24lambda_u245.setColor(Color.rgb(32, 38, 52));
        $this$grid_u24lambda_u245.setStrokeWidth(1.0f);
        this.grid = $this$grid_u24lambda_u245;
        Paint $this$panel_u24lambda_u246 = new Paint();
        $this$panel_u24lambda_u246.setColor(Color.rgb(18, 22, 31));
        this.panel = $this$panel_u24lambda_u246;
        setBackgroundColor(Color.rgb(11, 13, 18));
    }

    private final Telemetry getT() {
        return Link.INSTANCE.getTelemetry();
    }

    public final void reset() {
        this.lat0 = Double.NaN;
        this.lon0 = Double.NaN;
        this.trails.clear();
        this.lastAdd.clear();
        this.dr.clear();
        this.anchor = null;
        Telemetry t = getT();
        if (t != null) {
            t.resetTurns();
        }
        invalidate();
    }

    private final float[] en(double lat, double lon) {
        return new float[]{(float) ((lon - this.lon0) * 111320.0d * Math.cos(Math.toRadians(this.lat0))), (float) ((lat - this.lat0) * 110540.0d)};
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterative(DepthRegionTraversal.java:31)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visit(SwitchOverStringVisitor.java:60)
     */
    /* renamed from: short, reason: not valid java name */
    private final String m1087short(String src) {
        switch (src.hashCode()) {
            case -861391249:
                if (src.equals("android")) {
                    return "phone";
                }
                break;
            case 107868:
                if (src.equals("map")) {
                    return "what the map gets (filtered)";
                }
                break;
            case 111078824:
                if (src.equals("ublox")) {
                    return "comma u-blox";
                }
                break;
            case 319206107:
                if (src.equals("qcomdiag")) {
                    return "comma qcom";
                }
                break;
        }
        return src;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r14v22 */
    /* JADX WARN: Type inference failed for: r14v23 */
    /* JADX WARN: Type inference failed for: r14v8 */
    private final void update(Telemetry tm, long now) {
        Object next;
        ArrayList<float[]> arrayList;
        ArrayList<float[]> arrayList2;
        Iterator<Map.Entry<String, GpsFixInfo>> it = tm.getGpsFixes().entrySet().iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Map.Entry<String, GpsFixInfo> next2 = it.next();
            String key = next2.getKey();
            GpsFixInfo value = next2.getValue();
            if (value.getHasFix() && now - value.getAtNs() <= 3000000000L) {
                double lat = value.getLat();
                if (((Double.isInfinite(lat) || Double.isNaN(lat)) ? false : true) && Math.abs(value.getLat()) >= 1.0d) {
                    if (Double.isNaN(this.lat0)) {
                        this.lat0 = value.getLat();
                        this.lon0 = value.getLon();
                    }
                    Long l = this.lastAdd.get(key);
                    if (now - (l != null ? l.longValue() : 0L) >= 200000000) {
                        this.lastAdd.put(key, Long.valueOf(now));
                        LinkedHashMap<String, ArrayList<float[]>> linkedHashMap = this.trails;
                        String source = value.getSource();
                        ArrayList<float[]> arrayList3 = linkedHashMap.get(source);
                        if (arrayList3 == null) {
                            arrayList2 = new ArrayList<>();
                            linkedHashMap.put(source, arrayList2);
                        } else {
                            arrayList2 = arrayList3;
                        }
                        ArrayList<float[]> arrayList4 = arrayList2;
                        arrayList4.add(en(value.getLat(), value.getLon()));
                        if (arrayList4.size() > 4000) {
                            arrayList4.remove(0);
                        }
                    }
                }
            }
        }
        double outLat = GpsBridge.INSTANCE.getOutLat();
        double outLon = GpsBridge.INSTANCE.getOutLon();
        if (((Double.isInfinite(outLat) || Double.isNaN(outLat)) ? false : true) && !Double.isNaN(this.lat0)) {
            Long l2 = this.lastAdd.get("map");
            if (now - (l2 != null ? l2.longValue() : 0L) >= 200000000) {
                this.lastAdd.put("map", Long.valueOf(now));
                LinkedHashMap<String, ArrayList<float[]>> linkedHashMap2 = this.trails;
                ArrayList<float[]> arrayList5 = linkedHashMap2.get("map");
                if (arrayList5 == null) {
                    arrayList = new ArrayList<>();
                    linkedHashMap2.put("map", arrayList);
                } else {
                    arrayList = arrayList5;
                }
                ArrayList<float[]> arrayList6 = arrayList;
                arrayList6.add(en(outLat, outLon));
                if (arrayList6.size() > 4000) {
                    arrayList6.remove(0);
                }
            }
        }
        double[] at = Odometry.INSTANCE.at(now);
        if (this.anchor == null) {
            Collection<GpsFixInfo> values = tm.getGpsFixes().values();
            Intrinsics.checkNotNullExpressionValue(values, "<get-values>(...)");
            ArrayList arrayList7 = new ArrayList();
            for (Object obj : values) {
                GpsFixInfo gpsFixInfo = (GpsFixInfo) obj;
                if (Intrinsics.areEqual(gpsFixInfo.getService(), "gpsLocationExternal") && gpsFixInfo.getHasFix() && now - gpsFixInfo.getAtNs() < 1000000000 && gpsFixInfo.getSpeed() > 2.5f) {
                    arrayList7.add(obj);
                }
            }
            Iterator it2 = arrayList7.iterator();
            if (it2.hasNext()) {
                next = it2.next();
                if (it2.hasNext()) {
                    ?? r14 = !Intrinsics.areEqual(((GpsFixInfo) next).getSource(), "ublox") ? 1 : 0;
                    while (true) {
                        Object next3 = it2.next();
                        int i = !Intrinsics.areEqual(((GpsFixInfo) next3).getSource(), "ublox") ? 1 : 0;
                        int i2 = r14;
                        if (r14 > i) {
                            next = next3;
                            i2 = i;
                        }
                        if (!it2.hasNext()) {
                            break;
                        } else {
                            r14 = i2;
                        }
                    }
                }
            } else {
                next = null;
            }
            GpsFixInfo gpsFixInfo2 = (GpsFixInfo) next;
            if (gpsFixInfo2 != null && !Double.isNaN(this.lat0)) {
                float[] en = en(gpsFixInfo2.getLat(), gpsFixInfo2.getLon());
                this.anchor = new double[]{en[0], en[1], Math.toRadians(90.0d - gpsFixInfo2.getBearing()) - at[2], at[0], at[1]};
            }
        }
        double[] dArr = this.anchor;
        if (dArr != null) {
            Long l3 = this.lastAdd.get("dr");
            if (now - (l3 != null ? l3.longValue() : 0L) >= 200000000) {
                this.lastAdd.put("dr", Long.valueOf(now));
                double d = at[0] - dArr[3];
                double d2 = at[1] - dArr[4];
                double cos = Math.cos(dArr[2]);
                double sin = Math.sin(dArr[2]);
                this.dr.add(new float[]{(float) ((dArr[0] + (cos * d)) - (sin * d2)), (float) (dArr[1] + (sin * d) + (cos * d2))});
                if (this.dr.size() > 4000) {
                    this.dr.remove(0);
                }
            }
        }
    }

    @Override // android.view.View
    protected void onDraw(Canvas cv) {
        Intrinsics.checkNotNullParameter(cv, "cv");
        super.onDraw(cv);
        Telemetry tm = getT();
        long now = System.nanoTime();
        if (tm == null) {
            cv.drawText("not connected to the comma", 20 * this.d, 40 * this.d, this.txt);
            postInvalidateDelayed(500L);
            return;
        }
        update(tm, now);
        float w = getWidth();
        float h = getHeight();
        float pad = 12 * this.d;
        boolean wide = w > h;
        RectF map = wide ? new RectF(pad, pad, w * 0.55f, h - pad) : new RectF(pad, pad, w - pad, h * 0.46f);
        RectF info = wide ? new RectF((0.55f * w) + pad, pad, w - pad, h - pad) : new RectF(pad, (0.46f * h) + pad, w - pad, h - pad);
        drawMap(cv, map, tm, now);
        drawInfo(cv, info, tm, now);
        postInvalidateDelayed(100L);
    }

    /* JADX WARN: Removed duplicated region for block: B:114:0x0496  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x04d5  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x0534  */
    /* JADX WARN: Removed duplicated region for block: B:131:0x05bf  */
    /* JADX WARN: Removed duplicated region for block: B:134:0x05d1  */
    /* JADX WARN: Removed duplicated region for block: B:135:0x0562  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x03ef  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final void drawMap(Canvas cv, RectF r, Telemetry tm, long now) {
        float cx;
        float cN;
        float cE;
        Iterator<Map.Entry<String, GpsFixInfo>> it;
        float[] it2;
        Iterator<String> it3;
        StringBuilder append;
        String str;
        float f = 14;
        cv.drawRoundRect(r, this.d * f, this.d * f, this.panel);
        Collection<ArrayList<float[]>> values = this.trails.values();
        Intrinsics.checkNotNullExpressionValue(values, "<get-values>(...)");
        List all = CollectionsKt.plus((Collection) CollectionsKt.flatten(values), (Iterable) this.dr);
        if (all.isEmpty()) {
            cv.drawText("waiting for a GPS fix…", r.left + (16 * this.d), r.top + (30 * this.d), this.dim);
            return;
        }
        Iterator it4 = all.iterator();
        if (!it4.hasNext()) {
            throw new NoSuchElementException();
        }
        float[] it5 = (float[]) it4.next();
        float f2 = it5[0];
        while (it4.hasNext()) {
            float[] it6 = (float[]) it4.next();
            f2 = Math.min(f2, it6[0]);
        }
        float minE = f2;
        Iterator it7 = all.iterator();
        if (!it7.hasNext()) {
            throw new NoSuchElementException();
        }
        float[] it8 = (float[]) it7.next();
        float f3 = it8[0];
        while (it7.hasNext()) {
            float[] it9 = (float[]) it7.next();
            f3 = Math.max(f3, it9[0]);
        }
        float maxE = f3;
        Iterator it10 = all.iterator();
        if (!it10.hasNext()) {
            throw new NoSuchElementException();
        }
        float[] it11 = (float[]) it10.next();
        float f4 = it11[1];
        while (it10.hasNext()) {
            float[] it12 = (float[]) it10.next();
            f4 = Math.min(f4, it12[1]);
        }
        float minN = f4;
        Iterator it13 = all.iterator();
        if (!it13.hasNext()) {
            throw new NoSuchElementException();
        }
        float[] it14 = (float[]) it13.next();
        float f5 = it14[1];
        while (it13.hasNext()) {
            float[] it15 = (float[]) it13.next();
            f5 = Math.max(f5, it15[1]);
        }
        float maxN = f5;
        float span = Math.max(60.0f, Math.max(maxE - minE, maxN - minN)) * 1.15f;
        float f6 = 2;
        float cx2 = (minE + maxE) / f6;
        float cE2 = (minN + maxN) / f6;
        float wb = 18;
        float m = wb * this.d;
        float scale = Math.min(r.width() - (f6 * m), r.height() - (f6 * m)) / span;
        float cx3 = r.centerX();
        float cy = r.centerY();
        cv.save();
        cv.clipRect(r);
        float scale2 = 9.8E-45f;
        Iterable $this$first$iv = CollectionsKt.listOf((Object[]) new Float[]{Float.valueOf(5.0f), Float.valueOf(10.0f), Float.valueOf(20.0f), Float.valueOf(50.0f), Float.valueOf(100.0f), Float.valueOf(200.0f), Float.valueOf(500.0f), Float.valueOf(1000.0f), Float.valueOf(2000.0f), Float.valueOf(5000.0f)});
        int $i$f$first = 0;
        for (Object element$iv : $this$first$iv) {
            float it16 = ((Number) element$iv).floatValue();
            if (it16 * scale > ((float) 60) * this.d) {
                float step = ((Number) element$iv).floatValue();
                float g = ((float) Math.floor((cx2 - span) / step)) * step;
                while (g < cx2 + span) {
                    float drawMap$X = drawMap$X(cx3, cx2, scale, g);
                    float f7 = r.top;
                    float drawMap$X2 = drawMap$X(cx3, cx2, scale, g);
                    float scale3 = r.bottom;
                    cv.drawLine(drawMap$X, f7, drawMap$X2, scale3, this.grid);
                    g += step;
                    cy = cy;
                    scale = scale;
                    all = all;
                    cx3 = cx3;
                }
                float cx4 = cx3;
                float scale4 = scale;
                float cy2 = cy;
                for (float g2 = ((float) Math.floor((cE2 - span) / step)) * step; g2 < cE2 + span; g2 += step) {
                    cv.drawLine(r.left, drawMap$Y(cy2, cE2, scale4, g2), r.right, drawMap$Y(cy2, cE2, scale4, g2), this.grid);
                }
                float cN2 = cE2;
                float cE3 = cx2;
                float f8 = f;
                drawMap$poly(this, cv, cx4, cx2, scale4, cy2, cE2, this.dr, this.drColor, true);
                for (Map.Entry<String, ArrayList<float[]>> entry : this.trails.entrySet()) {
                    String src = entry.getKey();
                    ArrayList pts = entry.getValue();
                    if (!Intrinsics.areEqual(src, "map")) {
                        ArrayList arrayList = pts;
                        Integer num = this.colors.get(src);
                        drawMap$poly(this, cv, cx4, cE3, scale4, cy2, cN2, arrayList, num != null ? num.intValue() : -3355444, false);
                    }
                }
                ArrayList it17 = this.trails.get("map");
                if (it17 != null) {
                    this.line.setStrokeWidth(this.d * 1.6f);
                    drawMap$poly(this, cv, cx4, cE3, scale4, cy2, cN2, it17, -1, false);
                    this.line.setStrokeWidth(this.d * 2.5f);
                    Unit unit = Unit.INSTANCE;
                    Unit unit2 = Unit.INSTANCE;
                }
                float wb2 = GpsBridge.INSTANCE.getOutBearing();
                if ((Float.isInfinite(wb2) || Float.isNaN(wb2)) ? false : true) {
                    double outLat = GpsBridge.INSTANCE.getOutLat();
                    if (((Double.isInfinite(outLat) || Double.isNaN(outLat)) ? false : true) && !Double.isNaN(this.lat0)) {
                        float[] p = en(GpsBridge.INSTANCE.getOutLat(), GpsBridge.INSTANCE.getOutLon());
                        cx = cx4;
                        cE = cE3;
                        float px = drawMap$X(cx, cE, scale4, p[0]);
                        cN = cN2;
                        float py = drawMap$Y(cy2, cN, scale4, p[1]);
                        cv.save();
                        cv.rotate(wb2, px, py);
                        Path a = new Path();
                        a.moveTo(px, py - (11 * this.d));
                        float f9 = 7;
                        a.lineTo((this.d * 6.5f) + px, (this.d * f9) + py);
                        a.lineTo(px, (this.d * 3.5f) + py);
                        a.lineTo(px - (this.d * 6.5f), (f9 * this.d) + py);
                        a.close();
                        this.fill.setColor(-1);
                        this.fill.setAlpha(255);
                        cv.drawPath(a, this.fill);
                        cv.restore();
                        it = tm.getGpsFixes().entrySet().iterator();
                        while (it.hasNext()) {
                            GpsFixInfo f10 = it.next().getValue();
                            if (f10.getHasFix() && now - f10.getAtNs() <= 3000000000L) {
                                if (!Double.isNaN(this.lat0)) {
                                    float[] p2 = en(f10.getLat(), f10.getLon());
                                    Integer num2 = this.colors.get(f10.getSource());
                                    int col = num2 != null ? num2.intValue() : -3355444;
                                    this.fill.setColor(col);
                                    this.fill.setAlpha(40);
                                    cv.drawCircle(drawMap$X(cx, cE, scale4, p2[0]), drawMap$Y(cy2, cN, scale4, p2[1]), Math.max(4 * this.d, f10.getHAcc() * scale4), this.fill);
                                    this.fill.setAlpha(255);
                                    cv.drawCircle(drawMap$X(cx, cE, scale4, p2[0]), drawMap$Y(cy2, cN, scale4, p2[1]), 5 * this.d, this.fill);
                                    it = it;
                                }
                            }
                        }
                        it2 = (float[]) CollectionsKt.lastOrNull((List) this.dr);
                        if (it2 != null) {
                            this.fill.setColor(this.drColor);
                            cv.drawCircle(drawMap$X(cx, cE, scale4, it2[0]), drawMap$Y(cy2, cN, scale4, it2[1]), this.d * 4.5f, this.fill);
                            Unit unit3 = Unit.INSTANCE;
                            Unit unit4 = Unit.INSTANCE;
                        }
                        cv.restore();
                        float f11 = 22;
                        float ly = r.top + (this.d * f11);
                        float ly2 = ly;
                        for (it3 = this.trails.keySet().iterator(); it3.hasNext(); it3 = it3) {
                            String next = it3.next();
                            Intrinsics.checkNotNullExpressionValue(next, "next(...)");
                            String src2 = next;
                            Paint paint = this.fill;
                            Integer num3 = this.colors.get(src2);
                            paint.setColor(num3 != null ? num3.intValue() : -3355444);
                            float f12 = 5;
                            cv.drawCircle(r.left + (this.d * wb), ly2 - (this.d * f12), f12 * this.d, this.fill);
                            cv.drawText(m1087short(src2), r.left + (30 * this.d), ly2, this.dim);
                            ly2 += this.d * wb;
                        }
                        if (this.dr.isEmpty()) {
                            this.fill.setColor(this.drColor);
                            float f13 = 5;
                            cv.drawCircle(r.left + (this.d * wb), ly2 - (this.d * f13), f13 * this.d, this.fill);
                            cv.drawText("wheels + gyro only (no GPS after its start point)", r.left + (30 * this.d), ly2, this.dim);
                        } else {
                            cv.drawText("dead reckoning starts once the car moves", r.left + (f8 * this.d), ly2, this.dim);
                        }
                        float sb = step * scale4;
                        this.line.setColor(Color.rgb(200, 210, 230));
                        this.line.setPathEffect(null);
                        float f14 = 16;
                        float f15 = (this.d * f14) + r.left;
                        float f16 = r.bottom - (this.d * f14);
                        float f17 = r.left;
                        float ly3 = this.d;
                        cv.drawLine(f15, f16, f17 + (ly3 * f14) + sb, r.bottom - (this.d * f14), this.line);
                        if (step < 1000.0f) {
                            append = new StringBuilder().append((int) (step / 1000));
                            str = " km";
                        } else {
                            append = new StringBuilder().append((int) step);
                            str = " m";
                        }
                        cv.drawText(append.append(str).toString(), r.left + (f14 * this.d), r.bottom - (f11 * this.d), this.dim);
                        cv.drawText("N ↑", r.right - (40 * this.d), r.top + (24 * this.d), this.head);
                        return;
                    }
                }
                cx = cx4;
                cN = cN2;
                cE = cE3;
                it = tm.getGpsFixes().entrySet().iterator();
                while (it.hasNext()) {
                }
                it2 = (float[]) CollectionsKt.lastOrNull((List) this.dr);
                if (it2 != null) {
                }
                cv.restore();
                float f112 = 22;
                float ly4 = r.top + (this.d * f112);
                float ly22 = ly4;
                while (it3.hasNext()) {
                }
                if (this.dr.isEmpty()) {
                }
                float sb2 = step * scale4;
                this.line.setColor(Color.rgb(200, 210, 230));
                this.line.setPathEffect(null);
                float f142 = 16;
                float f152 = (this.d * f142) + r.left;
                float f162 = r.bottom - (this.d * f142);
                float f172 = r.left;
                float ly32 = this.d;
                cv.drawLine(f152, f162, f172 + (ly32 * f142) + sb2, r.bottom - (this.d * f142), this.line);
                if (step < 1000.0f) {
                }
                cv.drawText(append.append(str).toString(), r.left + (f142 * this.d), r.bottom - (f112 * this.d), this.dim);
                cv.drawText("N ↑", r.right - (40 * this.d), r.top + (24 * this.d), this.head);
                return;
            }
            float cN3 = cE2;
            float cN4 = cx2;
            float cE4 = cx3;
            int cx5 = $i$f$first;
            float $i$f$first2 = scale2;
            cy = cy;
            scale = scale;
            all = all;
            scale2 = $i$f$first2;
            $i$f$first = cx5;
            cx3 = cE4;
            cx2 = cN4;
            cE2 = cN3;
            f = f;
        }
        throw new NoSuchElementException("Collection contains no element matching the predicate.");
    }

    private static final float drawMap$X(float cx, float cE, float scale, float e) {
        return ((e - cE) * scale) + cx;
    }

    private static final float drawMap$Y(float cy, float cN, float scale, float n) {
        return cy - ((n - cN) * scale);
    }

    private static final void drawMap$poly(SensorsView this$0, Canvas $cv, float cx, float cE, float scale, float cy, float cN, List<float[]> list, int color, boolean dashed) {
        if (list.size() < 2) {
            return;
        }
        Path p = new Path();
        p.moveTo(drawMap$X(cx, cE, scale, list.get(0)[0]), drawMap$Y(cy, cN, scale, list.get(0)[1]));
        for (float[] q : CollectionsKt.drop(list, 1)) {
            p.lineTo(drawMap$X(cx, cE, scale, q[0]), drawMap$Y(cy, cN, scale, q[1]));
        }
        this$0.line.setColor(color);
        this$0.line.setPathEffect(dashed ? new DashPathEffect(new float[]{10 * this$0.d, 7 * this$0.d}, 0.0f) : null);
        $cv.drawPath(p, this$0.line);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private final void drawInfo(Canvas cv, RectF r, Telemetry tm, long now) {
        String it;
        String str;
        String str2;
        String str3;
        int i;
        float f = 14;
        cv.drawRoundRect(r, this.d * f, f * this.d, this.panel);
        Ref.FloatRef y = new Ref.FloatRef();
        y.element = r.top + (28 * this.d);
        float x = r.left + (16 * this.d);
        int i2 = 1;
        boolean mph = !tm.isMetric();
        drawInfo$ln(this, cv, x, y, "Turned since Reset  (+ left, − right)", this.head, 30.0f);
        drawInfo$ln(this, cv, x, y, "gyro " + drawInfo$deg(tm.getTurnGyroDeg()) + "    steering " + drawInfo$deg(tm.getTurnSteerDeg()) + "    GPS " + drawInfo$deg(tm.getTurnGpsDeg()) + "    map " + drawInfo$deg(GpsBridge.INSTANCE.getTurnSentDeg()), this.big, 26.0f);
        String it2 = tm.getTurnGpsSrc();
        if (it2 == null || (it = m1087short(it2)) == null) {
            it = "waiting";
        }
        drawInfo$ln(this, cv, x, y, "all count only above 3.4 mph (map = the heading the tablet's map gets).  One full circle ≈ ±360° on each.  GPS course: " + it, this.dim, 28.0f);
        drawInfo$ln(this, cv, x, y, "GPS", this.head, 24.0f);
        Set<String> keySet = tm.getGpsFixes().keySet();
        Intrinsics.checkNotNullExpressionValue(keySet, "<get-keys>(...)");
        List keys = CollectionsKt.sorted(keySet);
        if (keys.isEmpty()) {
            drawInfo$ln$default(this, cv, x, y, "no GPS messages from the comma", this.dim, 0.0f, 64, null);
        }
        Iterator it3 = keys.iterator();
        while (true) {
            if (!it3.hasNext()) {
                break;
            }
            String k = (String) it3.next();
            GpsFixInfo f2 = tm.getGpsFixes().get(k);
            if (f2 != null) {
                double age = (now - f2.getAtNs()) / 1.0E9d;
                int i3 = age > 3.0d ? i2 : 0;
                float speed = f2.getSpeed();
                String spd = (mph ? new StringBuilder().append(drawInfo$f1(speed * 2.237f)).append(" mph") : new StringBuilder().append(drawInfo$f1(speed * 3.6f)).append(" km/h")).toString();
                String m1087short = m1087short(f2.getSource());
                String format = String.format("%.1f", Arrays.copyOf(new Object[]{Float.valueOf(tm.hz(k))}, i2));
                Intrinsics.checkNotNullExpressionValue(format, "format(...)");
                if (i3 != 0) {
                    str3 = String.format("silent for %.0f s", Arrays.copyOf(new Object[]{Double.valueOf(age)}, i2));
                    Intrinsics.checkNotNullExpressionValue(str3, "format(...)");
                } else {
                    str3 = !f2.getHasFix() ? "NO FIX (" + f2.getSats() + " sats)" : "FIX  " + f2.getSats() + " sats  ±" + drawInfo$f1(f2.getHAcc()) + " m";
                }
                String head1 = m1087short + "  ·  " + format + " Hz  ·  " + str3;
                Paint $this$drawInfo_u24lambda_u2419 = this.txt;
                if (i3 == 0 && f2.getHasFix()) {
                    Integer num = this.colors.get(f2.getSource());
                    i = num != null ? num.intValue() : Color.rgb(223, 230, 243);
                    $this$drawInfo_u24lambda_u2419.setColor(i);
                    Unit unit = Unit.INSTANCE;
                    drawInfo$ln(this, cv, x, y, head1, $this$drawInfo_u24lambda_u2419, 19.0f);
                    this.txt.setColor(Color.rgb(223, 230, 243));
                    if (f2.getHasFix() || i3 != 0) {
                        Ref.FloatRef y2 = y;
                        y2.element += 4 * this.d;
                        y = y2;
                        i2 = 1;
                    } else {
                        String format2 = String.format("   %.6f, %.6f   " + spd + "   course " + drawInfo$f1(f2.getBearing()) + "° ±" + drawInfo$f1(f2.getBAcc()) + "°", Arrays.copyOf(new Object[]{Double.valueOf(f2.getLat()), Double.valueOf(f2.getLon())}, 2));
                        Intrinsics.checkNotNullExpressionValue(format2, "format(...)");
                        drawInfo$ln(this, cv, x, y, format2, this.dim, 22.0f);
                        y = y;
                        i2 = 1;
                    }
                }
                i = Color.rgb(255, 150, 120);
                $this$drawInfo_u24lambda_u2419.setColor(i);
                Unit unit2 = Unit.INSTANCE;
                drawInfo$ln(this, cv, x, y, head1, $this$drawInfo_u24lambda_u2419, 19.0f);
                this.txt.setColor(Color.rgb(223, 230, 243));
                if (f2.getHasFix()) {
                }
                Ref.FloatRef y22 = y;
                y22.element += 4 * this.d;
                y = y22;
                i2 = 1;
            }
        }
        Ref.FloatRef y3 = y;
        int i4 = i2;
        y3.element += 4 * this.d;
        String outKind = GpsBridge.INSTANCE.getOutKind();
        switch (outKind.hashCode()) {
            case 3214:
                if (outKind.equals("dr")) {
                    str = "wheels + gyro (GPS gap or ignored)";
                    break;
                }
                str = "nothing yet";
                break;
            case 3198773:
                if (outKind.equals("held")) {
                    str = "held still (car stopped)";
                    break;
                }
                str = "nothing yet";
                break;
            case 3314326:
                if (outKind.equals("last")) {
                    str = "last known position";
                    break;
                }
                str = "nothing yet";
                break;
            case 3322092:
                if (outKind.equals("live")) {
                    str = "the GPS (checked)";
                    break;
                }
                str = "nothing yet";
                break;
            default:
                str = "nothing yet";
                break;
        }
        float outBearing = GpsBridge.INSTANCE.getOutBearing();
        if (((Float.isInfinite(outBearing) || Float.isNaN(outBearing)) ? 0 : i4) != 0) {
            String format3 = String.format("%.0f", Arrays.copyOf(new Object[]{Float.valueOf(GpsBridge.INSTANCE.getOutBearing())}, i4));
            Intrinsics.checkNotNullExpressionValue(format3, "format(...)");
            str2 = "   ·   heading " + format3 + "°";
        } else {
            str2 = "";
        }
        drawInfo$ln(this, cv, x, y3, "The map gets: " + str + str2 + "   ·   " + GpsBridge.INSTANCE.getFilter().getStatus() + "   ·   ignored jumps: " + GpsBridge.INSTANCE.getFilter().getRejected(), this.txt, 24.0f);
        y3.element += 6 * this.d;
        drawInfo$ln(this, cv, x, y3, "Comma motion sensors", this.head, 24.0f);
        float[] gz = tm.getPoseGyro();
        String drawInfo$f1 = drawInfo$f1((float) Math.toDegrees(Odometry.INSTANCE.getGyroSign() * gz[2]));
        String str4 = (tm.getPoseOK() && tm.getPoseValid()) ? "OK" : "NOT OK";
        String format4 = String.format("%.0f", Arrays.copyOf(new Object[]{Float.valueOf(tm.hz("livePose"))}, i4));
        Intrinsics.checkNotNullExpressionValue(format4, "format(...)");
        drawInfo$ln$default(this, cv, x, y3, "turn rate (locationd, bias removed): " + drawInfo$f1 + "°/s   " + str4 + "   " + format4 + " Hz", this.dim, 0.0f, 64, null);
        drawInfo$ln$default(this, cv, x, y3, "acceleration (locationd): fwd " + drawInfo$f2(tm.getPoseAccel()[0]) + "  side " + drawInfo$f2(tm.getPoseAccel()[i4]) + "  vert " + drawInfo$f2(tm.getPoseAccel()[2]) + " m/s²", this.dim, 0.0f, 64, null);
        String drawInfo$f2 = drawInfo$f2(tm.getGyroRaw()[0]);
        String drawInfo$f22 = drawInfo$f2(tm.getGyroRaw()[i4]);
        String drawInfo$f23 = drawInfo$f2(tm.getGyroRaw()[2]);
        String gyroSrc = tm.getGyroSrc();
        String format5 = String.format("%.0f", Arrays.copyOf(new Object[]{Float.valueOf(tm.hz("gyroscope"))}, i4));
        Intrinsics.checkNotNullExpressionValue(format5, "format(...)");
        drawInfo$ln$default(this, cv, x, y3, "raw gyroscope: " + drawInfo$f2 + " " + drawInfo$f22 + " " + drawInfo$f23 + " rad/s  " + gyroSrc + "  " + format5 + " Hz", this.dim, 0.0f, 64, null);
        String drawInfo$f24 = drawInfo$f2(tm.getAccelRaw()[0]);
        String drawInfo$f25 = drawInfo$f2(tm.getAccelRaw()[i4]);
        String drawInfo$f26 = drawInfo$f2(tm.getAccelRaw()[2]);
        String accelSrc = tm.getAccelSrc();
        String format6 = String.format("%.0f", Arrays.copyOf(new Object[]{Float.valueOf(tm.hz("accelerometer"))}, i4));
        Intrinsics.checkNotNullExpressionValue(format6, "format(...)");
        drawInfo$ln(this, cv, x, y3, "raw accelerometer: " + drawInfo$f24 + " " + drawInfo$f25 + " " + drawInfo$f26 + " m/s²  " + accelSrc + "  " + format6 + " Hz", this.dim, 28.0f);
        drawInfo$ln(this, cv, x, y3, "Car", this.head, 24.0f);
        float vEgo = tm.getVEgo();
        String v = (mph ? new StringBuilder().append(drawInfo$f1(vEgo * 2.237f)).append(" mph") : new StringBuilder().append(drawInfo$f1(vEgo * 3.6f)).append(" km/h")).toString();
        drawInfo$ln$default(this, cv, x, y3, "wheel speed " + v + "   steering " + drawInfo$f1(tm.getSteeringAngleDeg()) + "°   yaw rate " + drawInfo$f1((float) Math.toDegrees(tm.getYawRate())) + "°/s", this.dim, 0.0f, 64, null);
        drawInfo$ln$default(this, cv, x, y3, "dead reckoning turns with: " + Odometry.INSTANCE.getYawSrc(), this.dim, 0.0f, 64, null);
    }

    private static final void drawInfo$ln(SensorsView this$0, Canvas $cv, float x, Ref.FloatRef y, String s, Paint p, float gap) {
        $cv.drawText(s, x, y.element, p);
        y.element += this$0.d * gap;
    }

    static /* synthetic */ void drawInfo$ln$default(SensorsView sensorsView, Canvas canvas, float f, Ref.FloatRef floatRef, String str, Paint paint, float f2, int i, Object obj) {
        if ((i & 32) != 0) {
            paint = sensorsView.txt;
        }
        Paint paint2 = paint;
        if ((i & 64) != 0) {
            f2 = 21.0f;
        }
        drawInfo$ln(sensorsView, canvas, f, floatRef, str, paint2, f2);
    }

    private static final String drawInfo$f1(float v) {
        if (!((Float.isInfinite(v) || Float.isNaN(v)) ? false : true)) {
            return "–";
        }
        String format = String.format("%.1f", Arrays.copyOf(new Object[]{Float.valueOf(v)}, 1));
        Intrinsics.checkNotNullExpressionValue(format, "format(...)");
        return format;
    }

    private static final String drawInfo$f2(float v) {
        if (!((Float.isInfinite(v) || Float.isNaN(v)) ? false : true)) {
            return "–";
        }
        String format = String.format("%.2f", Arrays.copyOf(new Object[]{Float.valueOf(v)}, 1));
        Intrinsics.checkNotNullExpressionValue(format, "format(...)");
        return format;
    }

    private static final String drawInfo$deg(double v) {
        String str = v >= 0.0d ? "+" : "−";
        String format = String.format("%.0f°", Arrays.copyOf(new Object[]{Double.valueOf(Math.abs(v))}, 1));
        Intrinsics.checkNotNullExpressionValue(format, "format(...)");
        return str + format;
    }
}
