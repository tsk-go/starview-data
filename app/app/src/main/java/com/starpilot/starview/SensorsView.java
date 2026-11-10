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
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import java.util.Map.Entry;
import kotlin.Pair;
import kotlin.jvm.internal.Ref.FloatRef;

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

    /* VF: from Vineflower (jadx: ) */
    public SensorsView(Context var1) {
        super(var1);
        Intrinsics.checkNotNullParameter(var1, "ctx");
        this.d = this.getResources().getDisplayMetrics().density;
        this.lat0 = Double.NaN;
        this.lon0 = Double.NaN;
        this.trails = new LinkedHashMap<>();
        this.lastAdd = new HashMap<>();
        this.dr = new ArrayList<>();
        this.colors = MapsKt.mapOf(
            new Pair[]{
                TuplesKt.to("ublox", Color.rgb(111, 227, 161)),
                TuplesKt.to("android", Color.rgb(90, 170, 255)),
                TuplesKt.to("qcomdiag", Color.rgb(200, 140, 255)),
                TuplesKt.to("map", -1)
            }
        );
        this.drColor = Color.rgb(255, 170, 60);
        Paint var2 = new Paint(1);
        var2.setColor(Color.rgb(223, 230, 243));
        var2.setTextSize(14 * this.d);
        this.txt = var2;
        Paint var3 = new Paint(this.txt);
        var3.setColor(Color.rgb(140, 155, 180));
        var3.setTextSize(this.d * 12.5F);
        this.dim = var3;
        Paint var4 = new Paint(this.txt);
        var4.setColor(-1);
        var4.setTextSize(16 * this.d);
        var4.setFakeBoldText(true);
        this.head = var4;
        Paint var5 = new Paint(this.txt);
        var5.setTextSize(22 * this.d);
        var5.setFakeBoldText(true);
        this.big = var5;
        Paint var6 = new Paint(1);
        var6.setStyle(android.graphics.Paint.Style.STROKE);
        var6.setStrokeWidth(this.d * 2.5F);
        var6.setStrokeJoin(android.graphics.Paint.Join.ROUND);
        this.line = var6;
        this.fill = new Paint(1);
        Paint var7 = new Paint();
        var7.setColor(Color.rgb(32, 38, 52));
        var7.setStrokeWidth(1.0F);
        this.grid = var7;
        Paint var8 = new Paint();
        var8.setColor(Color.rgb(18, 22, 31));
        this.panel = var8;
        this.setBackgroundColor(Color.rgb(11, 13, 18));
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
    private final String shortName(String src) {
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
            // the u-blox fix first, else the first one
            next = null;
            int bestKey = Integer.MAX_VALUE;
            for (Object o : arrayList7) {
                int k = !Intrinsics.areEqual(((GpsFixInfo) o).getSource(), "ublox") ? 1 : 0;
                if (k < bestKey) {
                    bestKey = k;
                    next = o;
                }
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

    /* VF: from Vineflower (jadx: Removed duplicated region;incorrect) */
    private final void drawMap(Canvas var1, RectF var2, Telemetry var3, long var4) {
        float var12 = 14;
        var1.drawRoundRect(var2, this.d * var12, this.d * var12, this.panel);
        Collection var23 = this.trails.values();
        Intrinsics.checkNotNullExpressionValue(var23, "<get-values>(...)");
        List var64 = CollectionsKt.plus(CollectionsKt.flatten(var23), this.dr);
        if (var64.isEmpty()) {
            var1.drawText("waiting for a GPS fix…", var2.left + 16 * this.d, var2.top + 30 * this.d, this.dim);
        } else {
            Iterator var24 = var64.iterator();
            if (!var24.hasNext()) {
                throw new NoSuchElementException();
            } else {
                float var8 = ((float[])var24.next())[0];

                while (var24.hasNext()) {
                    var8 = Math.min(var8, ((float[])var24.next())[0]);
                }

                var24 = var64.iterator();
                if (!var24.hasNext()) {
                    throw new NoSuchElementException();
                } else {
                    float var9 = ((float[])var24.next())[0];

                    while (var24.hasNext()) {
                        var9 = Math.max(var9, ((float[])var24.next())[0]);
                    }

                    var24 = var64.iterator();
                    if (!var24.hasNext()) {
                        throw new NoSuchElementException();
                    } else {
                        float var10 = ((float[])var24.next())[1];

                        while (var24.hasNext()) {
                            var10 = Math.min(var10, ((float[])var24.next())[1]);
                        }

                        var24 = var64.iterator();
                        if (!var24.hasNext()) {
                            throw new NoSuchElementException();
                        } else {
                            float var11 = ((float[])var24.next())[1];

                            while (var24.hasNext()) {
                                var11 = Math.max(var11, ((float[])var24.next())[1]);
                            }

                            float var17 = Math.max(60.0F, Math.max(var9 - var8, var11 - var10)) * 1.15F;
                            float var14 = 2;
                            float var13 = (var8 + var9) / var14;
                            var8 = (var10 + var11) / var14;
                            float var15 = 18;
                            var9 = var15 * this.d;
                            var10 = Math.min(var2.width() - var14 * var9, var2.height() - var14 * var9) / var17;
                            var14 = var2.centerX();
                            var9 = var2.centerY();
                            var1.save();
                            var1.clipRect(var2);
                            int var20 = 7;
                            Iterable var78 = CollectionsKt.listOf(new Float[]{5.0F, 10.0F, 20.0F, 50.0F, 100.0F, 200.0F, 500.0F, 1000.0F, 2000.0F, 5000.0F});
                            boolean var22 = false;
                            var24 = var78.iterator();
                            var12 = var8;
                            var8 = var14;

                            while (var24.hasNext()) {
                                Object var25 = (java.util.Map.Entry)var24.next();
                                boolean var21;
                                if (((Number)var25).floatValue() * var10 > 60 * this.d) {
                                    var21 = true;
                                } else {
                                    var21 = false;
                                }

                                if (var21) {
                                    float var16 = ((Number)var25).floatValue();

                                    for (float var51 = (float)Math.floor((var13 - var17) / var16) * var16; var51 < var13 + var17; var51 += var16) {
                                        var1.drawLine(drawMap$X(var8, var13, var10, var51), var2.top, drawMap$X(var8, var13, var10, var51), var2.bottom, this.grid);
                                    }

                                    for (float var52 = (float)Math.floor((var8 - var17) / var16) * var16; var52 < var12 + var17; var52 += var16) {
                                        var1.drawLine(var2.left, drawMap$Y(var9, var12, var10, var52), var2.right, drawMap$Y(var9, var12, var10, var52), this.grid);
                                    }

                                    drawMap$poly(this, var1, var8, var13, var10, var9, var12, this.dr, this.drColor, true);

                                    for (Object var25$it : (Iterable) this.trails.entrySet()) {
                                        var25 = (java.util.Map.Entry) var25$it;
                                        String var80 = (String)var25.getKey();
                                        var25 = (ArrayList)var25.getValue();
                                        if (!Intrinsics.areEqual(var80, "map")) {
                                            List var86 = (List)var25;
                                            Integer var81 = this.colors.get(var80);
                                            if (var81 != null) {
                                                var20 = var81;
                                            } else {
                                                var20 = -3355444;
                                            }

                                            drawMap$poly(this, var1, var8, var13, var10, var9, var12, var86, var20, false);
                                        }
                                    }

                                    ArrayList var66 = this.trails.get("map");
                                    if (var66 != null) {
                                        this.line.setStrokeWidth(this.d * 1.6F);
                                        drawMap$poly(this, var1, var8, var13, var10, var9, var12, var66, -1, false);
                                        this.line.setStrokeWidth(this.d * 2.5F);
                                        Unit var67 = Unit.INSTANCE;
                                        Unit var68 = Unit.INSTANCE;
                                    }

                                    float var18 = GpsBridge.INSTANCE.getOutBearing();
                                    int var58;
                                    if (!Float.isInfinite(var18) && !Float.isNaN(var18)) {
                                        var58 = 1;
                                    } else {
                                        var58 = 0;
                                    }

                                    if ((var58 != 0)) {
                                        double var6 = GpsBridge.INSTANCE.getOutLat();
                                        if (!Double.isInfinite(var6) && !Double.isNaN(var6)) {
                                            var58 = (((boolean)true) ? 1 : 0);
                                        } else {
                                            var58 = (((boolean)false) ? 1 : 0);
                                        }

                                        if (var58 && !Double.isNaN(this.lat0)) {
                                            float[] var69 = this.en(GpsBridge.INSTANCE.getOutLat(), GpsBridge.INSTANCE.getOutLon());
                                            var14 = drawMap$X(var8, var13, var10, var69[0]);
                                            var17 = drawMap$Y(var9, var12, var10, var69[1]);
                                            var1.save();
                                            var1.rotate(var18, var14, var17);
                                            Path var70 = new Path();
                                            var70.moveTo(var14, var17 - 11 * this.d);
                                            var18 = this.d;
                                            float var19 = 7;
                                            var70.lineTo(var18 * 6.5F + var14, this.d * var19 + var17);
                                            var70.lineTo(var14, this.d * 3.5F + var17);
                                            var70.lineTo(var14 - this.d * 6.5F, var19 * this.d + var17);
                                            var70.close();
                                            this.fill.setColor(-1);
                                            this.fill.setAlpha(255);
                                            var1.drawPath(var70, this.fill);
                                            var1.restore();
                                        }
                                    }

                                    Iterator var26 = var3.getGpsFixes().entrySet().iterator();

                                    while (var26.hasNext()) {
                                        GpsFixInfo var82 = (GpsFixInfo)((java.util.Map.Entry)var26.next()).getValue();
                                        if (var82.getHasFix() && var4 - var82.getAtNs() <= 3000000000L && !Double.isNaN(this.lat0)) {
                                            float[] var87 = this.en(var82.getLat(), var82.getLon());
                                            Integer var71 = this.colors.get(var82.getSource());
                                            if (var71 != null) {
                                                var58 = var71;
                                            } else {
                                                var58 = -3355444;
                                            }

                                            this.fill.setColor(var58);
                                            this.fill.setAlpha(40);
                                            var1.drawCircle(
                                                drawMap$X(var8, var13, var10, var87[0]),
                                                drawMap$Y(var9, var12, var10, var87[1]),
                                                Math.max(4 * this.d, var82.getHAcc() * var10),
                                                this.fill
                                            );
                                            this.fill.setAlpha(255);
                                            var1.drawCircle(drawMap$X(var8, var13, var10, var87[0]), drawMap$Y(var9, var12, var10, var87[1]), 5 * this.d, this.fill);
                                        }
                                    }

                                    float[] var27 = CollectionsKt.lastOrNull(this.dr);
                                    if (var27 != null) {
                                        this.fill.setColor(this.drColor);
                                        var1.drawCircle(drawMap$X(var8, var13, var10, var27[0]), drawMap$Y(var9, var12, var10, var27[1]), this.d * 4.5F, this.fill);
                                        Unit var28 = Unit.INSTANCE;
                                        Unit var29 = Unit.INSTANCE;
                                    }

                                    var1.restore();
                                    var8 = var2.top;
                                    var9 = 22;
                                    var12 = this.d;
                                    Iterator var30 = this.trails.keySet().iterator();

                                    for (var8 += var12 * var9; var30.hasNext(); var8 += this.d * var15) {
                                        var23 = (Collection)var30.next();
                                        Intrinsics.checkNotNullExpressionValue(var23, "next(...)");
                                        Object var73 = (String)var23;
                                        Paint var83 = this.fill;
                                        Integer var88 = this.colors.get(var73);
                                        if (var88 != null) {
                                            var58 = var88;
                                        } else {
                                            var58 = -3355444;
                                        }

                                        var83.setColor(var58);
                                        var12 = var2.left;
                                        var14 = this.d;
                                        var13 = 5;
                                        var1.drawCircle(var12 + var14 * var15, var8 - this.d * var13, var13 * this.d, this.fill);
                                        var1.drawText(this.shortName(((String) var73)), var2.left + 30 * this.d, var8, this.dim);
                                    }

                                    if (!this.dr.isEmpty()) {
                                        this.fill.setColor(this.drColor);
                                        var11 = var2.left;
                                        var12 = this.d;
                                        var13 = 5;
                                        var1.drawCircle(var11 + var12 * var15, var8 - this.d * var13, var13 * this.d, this.fill);
                                        var1.drawText("wheels + gyro only (no GPS after its start point)", var2.left + 30 * this.d, var8, this.dim);
                                    } else {
                                        var1.drawText("dead reckoning starts once the car moves", var2.left + var12 * this.d, var8, this.dim);
                                    }

                                    this.line.setColor(Color.rgb(200, 210, 230));
                                    this.line.setPathEffect(null);
                                    var11 = var2.left;
                                    var8 = 16;
                                    var1.drawLine(
                                        this.d * var8 + var11,
                                        var2.bottom - this.d * var8,
                                        var2.left + this.d * var8 + var16 * var10,
                                        var2.bottom - this.d * var8,
                                        this.line
                                    );
                                    String var31;
                                    StringBuilder var74;
                                    if (var16 >= 1000.0F) {
                                        var58 = (int)(var16 / 1000);
                                        var74 = new StringBuilder().append(var58);
                                        var31 = " km";
                                    } else {
                                        var58 = (int)var16;
                                        var74 = new StringBuilder().append(var58);
                                        var31 = " m";
                                    }

                                    var1.drawText(var74.append(var31).toString(), var2.left + var8 * this.d, var2.bottom - var9 * this.d, this.dim);
                                    var1.drawText("N ↑", var2.right - 40 * this.d, var2.top + 24 * this.d, this.head);
                                    return;
                                }
                            }

                            throw new NoSuchElementException("Collection contains no element matching the predicate.");
                        }
                    }
                }
            }
        }
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

    /* VF: from Vineflower (jadx: ) */
    private final void drawInfo(Canvas var1, RectF var2, Telemetry var3, long var4) {
        String var22 = null; String var34 = null; // declarations Vineflower left out
        boolean var12;
        FloatRef var14;
        float var47;
        label114: {
            float var8 = 14;
            var1.drawRoundRect(var2, this.d * var8, var8 * this.d, this.panel);
            var14 = new FloatRef();
            var14.element = var2.top + 28 * this.d;
            var47 = var2.left + 16 * this.d;
            var12 = var3.isMetric() ^ true;
            drawInfo$ln(this, var1, var47, var14, "Turned since Reset  (+ left, − right)", this.head, 30.0F);
            String var17 = drawInfo$deg(var3.getTurnGyroDeg());
            String var15 = drawInfo$deg(var3.getTurnSteerDeg());
            String var20 = drawInfo$deg(var3.getTurnGpsDeg());
            String var16 = drawInfo$deg(GpsBridge.INSTANCE.getTurnSentDeg());
            drawInfo$ln(this, var1, var47, var14, "gyro " + var17 + "    steering " + var15 + "    GPS " + var20 + "    map " + var16, this.big, 26.0F);
            String var21 = var3.getTurnGpsSrc();
            if (var21 != null) {
                var15 = this.shortName(var21);
                var22 = var15;
                if (var15 != null) {
                    break label114;
                }
            }

            var22 = "waiting";
        }

        drawInfo$ln(
            this,
            var1,
            var47,
            var14,
            "all count only above 3.4 mph (map = the heading the tablet's map gets).  One full circle ≈ ±360° on each.  GPS course: " + var22,
            this.dim,
            28.0F
        );
        drawInfo$ln(this, var1, var47, var14, "GPS", this.head, 24.0F);
        Set var23 = var3.getGpsFixes().keySet();
        Intrinsics.checkNotNullExpressionValue(var23, "<get-keys>(...)");
        List var24 = CollectionsKt.sorted(var23);
        if (var24.isEmpty()) {
            drawInfo$ln$default(this, var1, var47, var14, "no GPS messages from the comma", this.dim, 0.0F, 64, null);
        }

        Iterator var54 = var24.iterator();

        while (true) {
            boolean var13 = var54.hasNext();
            boolean var11 = false;
            if (!var13) {
                label92: {
                    var14.element = var14.element + 4 * this.d;
                    String var33 = GpsBridge.INSTANCE.getOutKind();
                    switch (var33.hashCode()) {
                        case 3214:
                            if (var33.equals("dr")) {
                                var34 = "wheels + gyro (GPS gap or ignored)";
                                break label92;
                            }
                            break;
                        case 3198773:
                            if (var33.equals("held")) {
                                var34 = "held still (car stopped)";
                                break label92;
                            }
                            break;
                        case 3314326:
                            if (var33.equals("last")) {
                                var34 = "last known position";
                                break label92;
                            }
                            break;
                        case 3322092:
                            if (var33.equals("live")) {
                                var34 = "the GPS (checked)";
                                break label92;
                            }
                    }

                    var34 = "nothing yet";
                }

                float var48 = GpsBridge.INSTANCE.getOutBearing();
                boolean var52;
                if (!Float.isInfinite(var48) && !Float.isNaN(var48)) {
                    var52 = true;
                } else {
                    var52 = false;
                }

                String var56;
                if (var52) {
                    String var55 = String.format("%.0f", Arrays.copyOf(new Object[]{GpsBridge.INSTANCE.getOutBearing()}, 1));
                    Intrinsics.checkNotNullExpressionValue(var55, "format(...)");
                    var56 = "   ·   heading " + var55 + "°";
                } else {
                    var56 = "";
                }

                String var63 = GpsBridge.INSTANCE.getFilter().getStatus();
                var4 = GpsBridge.INSTANCE.getFilter().getRejected();
                drawInfo$ln(this, var1, var47, var14, "The map gets: " + var34 + var56 + "   ·   " + var63 + "   ·   ignored jumps: " + var4, this.txt, 24.0F);
                var14.element = var14.element + 6 * this.d;
                drawInfo$ln(this, var1, var47, var14, "Comma motion sensors", this.head, 24.0F);
                float[] var35 = var3.getPoseGyro();
                String var57 = drawInfo$f1((float)Math.toDegrees((double)Odometry.INSTANCE.getGyroSign() * var35[2]));
                String var36;
                if (var3.getPoseOK() && var3.getPoseValid()) {
                    var36 = "OK";
                } else {
                    var36 = "NOT OK";
                }

                var63 = String.format("%.0f", Arrays.copyOf(new Object[]{var3.hz("livePose")}, 1));
                Intrinsics.checkNotNullExpressionValue(var63, "format(...)");
                drawInfo$ln$default(
                    this, var1, var47, var14, "turn rate (locationd, bias removed): " + var57 + "°/s   " + var36 + "   " + var63 + " Hz", this.dim, 0.0F, 64, null
                );
                String var37 = drawInfo$f2(var3.getPoseAccel()[0]);
                var63 = drawInfo$f2(var3.getPoseAccel()[1]);
                String var58 = drawInfo$f2(var3.getPoseAccel()[2]);
                drawInfo$ln$default(
                    this, var1, var47, var14, "acceleration (locationd): fwd " + var37 + "  side " + var63 + "  vert " + var58 + " m/s²", this.dim, 0.0F, 64, null
                );
                String var73 = drawInfo$f2(var3.getGyroRaw()[0]);
                var63 = drawInfo$f2(var3.getGyroRaw()[1]);
                String var69 = drawInfo$f2(var3.getGyroRaw()[2]);
                String var59 = var3.getGyroSrc();
                String var38 = String.format("%.0f", Arrays.copyOf(new Object[]{var3.hz("gyroscope")}, 1));
                Intrinsics.checkNotNullExpressionValue(var38, "format(...)");
                drawInfo$ln$default(
                    this,
                    var1,
                    var47,
                    var14,
                    "raw gyroscope: " + var73 + " " + var63 + " " + var69 + " rad/s  " + var59 + "  " + var38 + " Hz",
                    this.dim,
                    0.0F,
                    64,
                    null
                );
                var63 = drawInfo$f2(var3.getAccelRaw()[0]);
                String var60 = drawInfo$f2(var3.getAccelRaw()[1]);
                var69 = drawInfo$f2(var3.getAccelRaw()[2]);
                String var39 = var3.getAccelSrc();
                var73 = String.format("%.0f", Arrays.copyOf(new Object[]{var3.hz("accelerometer")}, 1));
                Intrinsics.checkNotNullExpressionValue(var73, "format(...)");
                drawInfo$ln(
                    this, var1, var47, var14, "raw accelerometer: " + var63 + " " + var60 + " " + var69 + " m/s²  " + var39 + "  " + var73 + " Hz", this.dim, 28.0F
                );
                drawInfo$ln(this, var1, var47, var14, "Car", this.head, 24.0F);
                var48 = var3.getVEgo();
                StringBuilder var41;
                if (var12) {
                    String var40 = drawInfo$f1(var48 * 2.237F);
                    var41 = new StringBuilder().append(var40).append(" mph");
                } else {
                    String var42 = drawInfo$f1(var48 * 3.6F);
                    var41 = new StringBuilder().append(var42).append(" km/h");
                }

                String var61 = var41.toString();
                String var43 = drawInfo$f1(var3.getSteeringAngleDeg());
                String var45 = drawInfo$f1((float)Math.toDegrees(var3.getYawRate()));
                drawInfo$ln$default(
                    this, var1, var47, var14, "wheel speed " + var61 + "   steering " + var43 + "°   yaw rate " + var45 + "°/s", this.dim, 0.0F, 64, null
                );
                String var44 = Odometry.INSTANCE.getYawSrc();
                drawInfo$ln$default(this, var1, var47, var14, "dead reckoning turns with: " + var44, this.dim, 0.0F, 64, null);
                return;
            }

            String var19 = (String)var54.next();
            GpsFixInfo var62 = var3.getGpsFixes().get(var19);
            if (var62 != null) {
                double var6 = (var4 - var62.getAtNs()) / 1.0E9;
                if (var6 > 3.0) {
                    var11 = true;
                }

                float var9 = var62.getSpeed();
                StringBuilder var26;
                if (var12) {
                    String var25 = drawInfo$f1(var9 * 2.237F);
                    var26 = new StringBuilder().append(var25).append(" mph");
                } else {
                    String var27 = drawInfo$f1(var9 * 3.6F);
                    var26 = new StringBuilder().append(var27).append(" km/h");
                }

                String var68 = var26.toString();
                String var18 = this.shortName(var62.getSource());
                var19 = String.format("%.1f", Arrays.copyOf(new Object[]{var3.hz(var19)}, 1));
                Intrinsics.checkNotNullExpressionValue(var19, "format(...)");
                String var28;
                if (var11) {
                    var28 = String.format("silent for %.0f s", Arrays.copyOf(new Object[]{var6}, 1));
                    Intrinsics.checkNotNullExpressionValue(var28, "format(...)");
                } else if (!var62.getHasFix()) {
                    int var10 = var62.getSats();
                    var28 = "NO FIX (" + var10 + " sats)";
                } else {
                    int var50 = var62.getSats();
                    String var29 = drawInfo$f1(var62.getHAcc());
                    var28 = "FIX  " + var50 + " sats  ±" + var29 + " m";
                }

                var18 = var18 + "  ·  " + var19 + " Hz  ·  " + var28;
                Paint var30 = this.txt;
                int var51;
                if (!var11 && var62.getHasFix()) {
                    Integer var76 = this.colors.get(var62.getSource());
                    if (var76 != null) {
                        var51 = var76;
                    } else {
                        var51 = Color.rgb(223, 230, 243);
                    }
                } else {
                    var51 = Color.rgb(255, 150, 120);
                }

                var30.setColor(var51);
                Unit var77 = Unit.INSTANCE;
                drawInfo$ln(this, var1, var47, var14, var18, var30, 19.0F);
                this.txt.setColor(Color.rgb(223, 230, 243));
                if (var62.getHasFix() && !var11) {
                    String var31 = drawInfo$f1(var62.getBearing());
                    var18 = drawInfo$f1(var62.getBAcc());
                    String var32 = String.format(
                        "   %.6f, %.6f   " + var68 + "   course " + var31 + "° ±" + var18 + "°", Arrays.copyOf(new Object[]{var62.getLat(), var62.getLon()}, 2)
                    );
                    Intrinsics.checkNotNullExpressionValue(var32, "format(...)");
                    drawInfo$ln(this, var1, var47, var14, var32, this.dim, 22.0F);
                } else {
                    var14.element = var14.element + 4 * this.d;
                }
            }
        }
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
