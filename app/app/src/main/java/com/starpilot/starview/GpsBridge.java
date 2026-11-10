package com.starpilot.starview;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Process;
import android.os.SystemClock;
import android.util.Log;
import com.starpilot.starview.GpsFilter;
import java.util.Iterator;
import java.util.List;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.json.JSONObject;

/* compiled from: GpsBridge.kt */
public final class GpsBridge {
    private static final long DR_AFTER_MS = 1500;
    private static final long DR_MAX_MS = 60000;
    private static final double LOOKAHEAD_HEADING_S = 0.8d;
    private static final double LOOKAHEAD_S = 0.4d;
    private static final String TAG = "StarView/Gps";
    private static volatile Context app;
    private static volatile long doubtedAtMs;
    private static volatile double[] drXform;
    private static volatile long drXformAtMs;
    private static volatile long fixes;
    private static double lastAlt;
    private static volatile long lastDrPushMs;
    private static long lastFallbackMs;
    private static volatile long lastFixMs;
    private static long lastFusedPushMs;
    private static volatile long lastLiveMs;
    private static long lastPushMs;
    private static long lastSetupTryMs;
    private static volatile boolean ready;
    private static volatile double turnSentDeg;
    private static volatile boolean usingFallback;
    private static volatile boolean usingRtk;
    private static volatile float vEgoNow;
    public static final GpsBridge INSTANCE = new GpsBridge();
    private static volatile String status = "waiting for the comma's GPS";
    private static volatile String mode = "none";
    private static double liveOdoDist = Double.NaN;
    private static final GpsFilter filter = new GpsFilter();
    private static volatile float lastAcc = 5.0f;
    private static volatile double outLat = Double.NaN;
    private static volatile double outLon = Double.NaN;
    private static volatile String outKind = "";
    private static volatile float outBearing = Float.NaN;
    private static float turnSentLast = Float.NaN;

    private GpsBridge() {
    }

    public final String getStatus() {
        return status;
    }

    public final long getLastFixMs() {
        return lastFixMs;
    }

    public final long getFixes() {
        return fixes;
    }

    private final List<String> providers() {
        List $this$providers_u24lambda_u240 = CollectionsKt.createListBuilder();
        $this$providers_u24lambda_u240.add("gps");
        $this$providers_u24lambda_u240.add("network");
        if (Build.VERSION.SDK_INT >= 31) {
            $this$providers_u24lambda_u240.add("fused");
        }
        return CollectionsKt.build($this$providers_u24lambda_u240);
    }

    public final void init(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        app = ctx.getApplicationContext();
    }

    public final Boolean permitted() {
        Context ctx = app;
        if (ctx == null) {
            return null;
        }
        try {
            Object systemService = ctx.getSystemService("appops");
            Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.app.AppOpsManager");
            AppOpsManager ops = (AppOpsManager) systemService;
            int mode2 = ops.unsafeCheckOpNoThrow("android:mock_location", Process.myUid(), ctx.getPackageName());
            return Boolean.valueOf(mode2 == 0);
        } catch (Exception e) {
            return null;
        }
    }

    public final long getLastLiveMs() {
        return lastLiveMs;
    }

    public final boolean getUsingFallback() {
        return usingFallback;
    }

    public final String getMode() {
        return mode;
    }

    public final void fallback(String lastGpsJson, float vEgo) {
        Intrinsics.checkNotNullParameter(lastGpsJson, "lastGpsJson");
        long now = System.currentTimeMillis();
        if (now - lastLiveMs < 3000) {
            mode = "live";
            return;
        }
        if (now - lastDrPushMs < 1500) {
            mode = "dr";
            return;
        }
        if (vEgo > 2.0f) {
            if (!Intrinsics.areEqual(mode, "none")) {
                stop();
                status = "car moving but the comma has no GPS fix: tablet location withdrawn";
            }
            mode = "none";
            return;
        }
        if (now - lastFallbackMs >= 1000) {
            if (lastGpsJson.length() == 0) {
                return;
            }
            lastFallbackMs = now;
            mode = "last";
            try {
                JSONObject j = new JSONObject(lastGpsJson);
                double lat = j.optDouble("latitude", Double.NaN);
                double lon = j.optDouble("longitude", Double.NaN);
                if (Double.isNaN(lat) || Double.isNaN(lon)) {
                    return;
                }
                if (Math.abs(lat) < 1.0E-6d && Math.abs(lon) < 1.0E-6d) {
                    return;
                }
                usingFallback = true;
                push$default(this, lat, lon, j.optDouble("altitude", 0.0d), 0.0f, (float) j.optDouble("bearing", Double.NaN), 25.0f, Float.NaN, Float.NaN, Float.NaN, false, false, false, 3072, null);
            } catch (Exception e) {
            }
        }
    }

    private final SharedPreferences prefs(Context ctx) {
        return ctx.getSharedPreferences("starview", 0);
    }

    public final boolean enabled(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        return prefs(ctx).getBoolean("gps_bridge", true);
    }

    public final void setEnabled(Context ctx, boolean v) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        prefs(ctx).edit().putBoolean("gps_bridge", v).apply();
        if (!v) {
            stop();
        }
    }

    private final LocationManager lm() {
        Context context = app;
        Object systemService = context != null ? context.getSystemService("location") : null;
        if (systemService instanceof LocationManager) {
            return (LocationManager) systemService;
        }
        return null;
    }

    private final synchronized boolean setup(LocationManager lm) {
        String p;
        if (ready) {
            return true;
        }
        long now = SystemClock.elapsedRealtime();
        if (now - lastSetupTryMs < 5000) {
            return false;
        }
        lastSetupTryMs = now;
        try {
            try {
                for (String p2 : providers()) {
                    try {
                        Result.Companion companion = Result.Companion;
                        GpsBridge gpsBridge = this;
                        lm.removeTestProvider(p2);
                        KtCompat.resultOf(Unit.INSTANCE);
                    } catch (Throwable th) {
                        Result.Companion companion2 = Result.Companion;
                        KtCompat.resultOf(ResultKt.createFailure(th));
                    }
                    if (Build.VERSION.SDK_INT >= 31) {
                        p = p2;
                        lm.addTestProvider(p2, false, false, false, false, true, true, true, 1, 1);
                    } else {
                        p = p2;
                        lm.addTestProvider(p, false, false, false, false, true, true, true, 1, 1);
                    }
                    lm.setTestProviderEnabled(p, true);
                }
                ready = true;
                status = "sending the comma's GPS to this tablet";
                return true;
            } catch (Exception e) {
                status = "can't add the GPS provider: " + e.getMessage();
                Log.w("StarView/Gps", "setup", e);
                return false;
            }
        } catch (SecurityException e2) {
            status = "not allowed yet: make StarView the mock location app (see below)";
            return false;
        }
    }

    public final long getLastDrPushMs() {
        return lastDrPushMs;
    }

    public final void setFit(double th, double tx, double ty, double lat0, double lon0, boolean doubted) {
        long now = System.currentTimeMillis();
        drXform = new double[]{th, tx, ty, lat0, lon0};
        drXformAtMs = now;
        if (doubted) {
            doubtedAtMs = now;
        }
    }

    public final double[] drPosition$app_debug(double[] x, double[] o, double odoDistAtFix) {
        Intrinsics.checkNotNullParameter(x, "x");
        Intrinsics.checkNotNullParameter(o, "o");
        double th = x[0];
        double c = Math.cos(th);
        double sn = Math.sin(th);
        double e = ((o[0] * c) - (o[1] * sn)) + x[1];
        double n = (o[0] * sn) + (o[1] * c) + x[2];
        double lat = x[3] + (n / 110540.0d);
        double lon = x[4] + (e / (Math.cos(Math.toRadians(x[3])) * 111320.0d));
        double brg = (((90.0d - Math.toDegrees(th + o[2])) % 360.0d) + 360.0d) % 360.0d;
        return new double[]{lat, lon, brg, !Double.isInfinite(odoDistAtFix) && !Double.isNaN(odoDistAtFix) ? o[3] - odoDistAtFix : 0.0d};
    }

    public final GpsFilter getFilter() {
        return filter;
    }

    public final double getOutLat() {
        return outLat;
    }

    public final double getOutLon() {
        return outLon;
    }

    public final String getOutKind() {
        return outKind;
    }

    public final void onCarState(float vEgo) {
        long now = System.currentTimeMillis();
        vEgoNow = vEgo;
        double[] odo = Odometry.INSTANCE.at(System.nanoTime());
        filter.onSpeed(now, Math.abs(vEgo), odo);
        double[] fu = filter.fused(now, odo);
        String str = "dr";
        if (fu != null) {
            if (now - lastFusedPushMs < 45) {
                return;
            }
            lastFusedPushMs = now;
            double[] mo = Odometry.INSTANCE.motion();
            double v = mo[0];
            double w = mo[1];
            if (v > 2.0d && !filter.isHolding(now)) {
                double b0 = Math.toRadians(fu[2]);
                double bMid = b0 - ((w * 0.4d) / 2);
                double b1 = b0 - (0.8d * w);
                double dE = v * 0.4d * Math.sin(bMid);
                double dN = v * 0.4d * Math.cos(bMid);
                fu[0] = fu[0] + (dN / 110540.0d);
                fu[1] = fu[1] + (dE / (Math.cos(Math.toRadians(fu[0])) * 111320.0d));
                fu[2] = ((Math.toDegrees(b1) % 360.0d) + 360.0d) % 360.0d;
            }
            boolean gap = fu[3] > 1.5d;
            boolean held = vEgo < 0.1f && filter.isHolding(now);
            if (gap) {
                lastDrPushMs = now;
            } else {
                str = "live";
            }
            mode = str;
            push(fu[0], fu[1], lastAlt, held ? 0.0f : Math.abs(vEgo), (float) fu[2], gap ? (float) RangesKt.coerceAtMost((fu[3] * 2.0d) + 5.0d, 50.0d) : lastAcc, Float.NaN, 3.0f, 0.3f, !gap, gap, held);
            return;
        }
        if (lastLiveMs == 0 || now - lastLiveMs < 1500) {
            return;
        }
        if (now - lastLiveMs <= 60000 && now - lastDrPushMs >= 190) {
            double[] f = filter.predict(now, odo);
            if (f != null) {
                double acc = RangesKt.coerceAtMost(5.0d + (f[3] * 0.03d), 50.0d);
                lastDrPushMs = now;
                mode = "dr";
                push$default(this, f[0], f[1], lastAlt, RangesKt.coerceAtLeast(vEgo, 0.0f), (float) f[2], (float) acc, Float.NaN, 10.0f, Float.NaN, false, true, false, 2048, null);
                return;
            }
            double[] x = drXform;
            if (x != null && now - drXformAtMs <= 60000 && !Double.isNaN(x[3])) {
                double[] p = drPosition$app_debug(x, odo, liveOdoDist);
                double acc2 = RangesKt.coerceAtMost((p[3] * 0.03d) + 5.0d, 50.0d);
                lastDrPushMs = now;
                mode = "dr";
                push$default(this, p[0], p[1], lastAlt, RangesKt.coerceAtLeast(vEgo, 0.0f), (float) p[2], (float) acc2, Float.NaN, 10.0f, Float.NaN, false, true, false, 2048, null);
            }
        }
    }

    public final void onFix(double lat, double lon, double alt, float speed, float bearing, float hAcc, float vAcc, float bAcc, float sAcc, String src, long monoNs) {
        Intrinsics.checkNotNullParameter(src, "src");
        long nowNs = System.nanoTime();
        Long monoToLocal = monoNs != 0 ? Odometry.INSTANCE.monoToLocal(monoNs) : null;
        long tFix = monoToLocal != null ? RangesKt.coerceIn(monoToLocal.longValue(), nowNs - 2000000000, nowNs) : nowNs;
        double[] odo = Odometry.INSTANCE.at(tFix);
        double[] prev = Odometry.INSTANCE.at(tFix - 200000000);
        double yawRate = Math.IEEEremainder(odo[2] - prev[2], 6.283185307179586d) / 0.2d;
        GpsFilter.Out out = filter.onFix(System.currentTimeMillis(), lat, lon, hAcc, speed, bearing, bAcc, src, odo, vEgoNow, yawRate);
        if (out == null) {
            return;
        }
        lastLiveMs = System.currentTimeMillis();
        usingFallback = false;
        lastAlt = alt;
        lastAcc = out.getAcc();
        if (filter.fused(System.currentTimeMillis(), odo) != null) {
            return;
        }
        long now = SystemClock.elapsedRealtime();
        if (now - lastPushMs < 90) {
            return;
        }
        lastPushMs = now;
        lastAlt = alt;
        liveOdoDist = odo[3];
        lastLiveMs = System.currentTimeMillis();
        usingFallback = false;
        push$default(this, out.getLat(), out.getLon(), alt, out.getSpeed(), out.getBearing(), out.getAcc(), vAcc, bAcc, sAcc, true, false, Intrinsics.areEqual(out.getKind(), "held"), 1024, null);
    }

    public final float getOutBearing() {
        return outBearing;
    }

    public final boolean getUsingRtk() {
        return usingRtk;
    }

    public final double getTurnSentDeg() {
        return turnSentDeg;
    }

    public final void resetTurn() {
        turnSentDeg = 0.0d;
        turnSentLast = Float.NaN;
    }

    static /* synthetic */ void push$default(GpsBridge gpsBridge, double d, double d2, double d3, float f, float f2, float f3, float f4, float f5, float f6, boolean z, boolean z2, boolean z3, int i, Object obj) {
        gpsBridge.push(d, d2, d3, f, f2, f3, f4, f5, f6, z, (i & 1024) != 0 ? false : z2, (i & 2048) != 0 ? false : z3);
    }

    /* VF: from Vineflower (jadx: Removed duplicated region;incorrect) */
    private final void push(
        double var1,
        double var3,
        double var5,
        float var7,
        float var8,
        float var9,
        float var10,
        float var11,
        float var12,
        boolean var13,
        boolean var14,
        boolean var15
    ) {
        Exception var78 = null; // declarations Vineflower left out
        double[] var22 = RtkPosition.INSTANCE.now(System.currentTimeMillis(), vEgoNow);
        if (var22 != null) {
            var1 = var22[0];
        }

        if (var22 != null) {
            var3 = var22[1];
        }

        if (var22 != null) {
            double var16 = RtkPosition.INSTANCE.getAccM();
            boolean var19;
            if (!Double.isInfinite(var16) && !Double.isNaN(var16)) {
                var19 = true;
            } else {
                var19 = false;
            }

            if (var19) {
                var9 = RangesKt.coerceAtLeast((float)RtkPosition.INSTANCE.getAccM(), 0.01F);
            } else {
                var9 = 1.0F;
            }
        }

        if (var22 != null) {
            double var69 = RtkPosition.INSTANCE.getHeading();
            boolean var71;
            if (!Double.isInfinite(var69) && !Double.isNaN(var69)) {
                var71 = true;
            } else {
                var71 = false;
            }

            if (var71) {
                var8 = (float)RtkPosition.INSTANCE.getHeading();
            }
        }

        boolean var20;
        if (var22 != null) {
            var20 = true;
        } else {
            var20 = false;
        }

        usingRtk = var20;
        outLat = var1;
        outLon = var3;
        String var21;
        if (var15) {
            var21 = "held";
        } else if (var14) {
            var21 = "dr";
        } else if (var13) {
            var21 = "live";
        } else {
            var21 = "last";
        }

        label386: {
            outKind = var21;
            outBearing = var8;
            if ((var13 || var14) && !var15 && vEgoNow > 1.5F) {
                boolean var72;
                if (!Float.isInfinite(var8) && !Float.isNaN(var8)) {
                    var72 = true;
                } else {
                    var72 = false;
                }

                if (var72) {
                    float var18 = turnSentLast;
                    if (!Float.isNaN(var18)) {
                        turnSentDeg -= (var8 - var18 + 540.0F) % 360.0F - 180.0F;
                    }

                    turnSentLast = var8;
                    break label386;
                }
            }

            turnSentLast = Float.NaN;
        }

        Context var77 = app;
        if (var77 != null) {
            if (this.enabled(var77)) {
                LocationManager var24 = this.lm();
                if (var24 != null) {
                    if (this.setup(var24)) {
                        label394: {
                            label378: {
                                Iterator var23;
                                try {
                                    var23 = this.providers().iterator();
                                } catch (SecurityException var67) {
                                    break label378;
                                } catch (Exception var68) {
                                    var78 = var68;
                                    break label394;
                                }

                                Iterator var81 = var23;

                                while (true) {
                                    try {
                                        if (!var81.hasNext()) {
                                            break;
                                        }
                                    } catch (SecurityException var55) {
                                        break label378;
                                    } catch (Exception var56) {
                                        var78 = var56;
                                        break label394;
                                    }

                                    String var26;
                                    try {
                                        var26 = (String)var81.next();
                                    } catch (SecurityException var53) {
                                        break label378;
                                    } catch (Exception var54) {
                                        var78 = var54;
                                        break label394;
                                    }

                                    Location var25;
                                    try {
                                    } catch (SecurityException var51) {
                                        break label378;
                                    } catch (Exception var52) {
                                        var78 = var52;
                                        break label394;
                                    }

                                    try {
                                        var25 = new Location(var26);
                                    } catch (SecurityException var49) {
                                        break label378;
                                    } catch (Exception var50) {
                                        var78 = var50;
                                        break label394;
                                    }

                                    try {
                                        var25.setLatitude(var1);
                                    } catch (SecurityException var47) {
                                        break label378;
                                    } catch (Exception var48) {
                                        var78 = var48;
                                        break label394;
                                    }

                                    try {
                                        var25.setLongitude(var3);
                                    } catch (SecurityException var45) {
                                        break label378;
                                    } catch (Exception var46) {
                                        var78 = var46;
                                        break label394;
                                    }

                                    try {
                                        var25.setAltitude(var5);
                                    } catch (SecurityException var43) {
                                        break label378;
                                    } catch (Exception var44) {
                                        var78 = var44;
                                        break label394;
                                    }

                                    try {
                                        var25.setSpeed(RangesKt.coerceAtLeast(var7, 0.0F));
                                        if (!Float.isNaN(var8)) {
                                            var25.setBearing((var8 % 360.0F + 360.0F) % 360.0F);
                                        }
                                    } catch (SecurityException var65) {
                                        break label378;
                                    } catch (Exception var66) {
                                        var78 = var66;
                                        break label394;
                                    }

                                    boolean var73;
                                    label317: {
                                        label316: {
                                            try {
                                                if (!Float.isInfinite(var9) && !Float.isNaN(var9)) {
                                                    break label316;
                                                }
                                            } catch (SecurityException var63) {
                                                break label378;
                                            } catch (Exception var64) {
                                                var78 = var64;
                                                break label394;
                                            }

                                            var73 = false;
                                            break label317;
                                        }

                                        var73 = true;
                                    }

                                    float var70;
                                    if (var73 && var9 > 0.0F) {
                                        var70 = var9;
                                    } else {
                                        var70 = 5.0F;
                                    }

                                    try {
                                        var25.setAccuracy(var70);
                                    } catch (SecurityException var41) {
                                        break label378;
                                    } catch (Exception var42) {
                                        var78 = var42;
                                        break label394;
                                    }

                                    label301: {
                                        label300: {
                                            try {
                                                if (!Float.isInfinite(var10) && !Float.isNaN(var10)) {
                                                    break label300;
                                                }
                                            } catch (SecurityException var61) {
                                                break label378;
                                            } catch (Exception var62) {
                                                var78 = var62;
                                                break label394;
                                            }

                                            var73 = false;
                                            break label301;
                                        }

                                        var73 = true;
                                    }

                                    if (var73 && var10 > 0.0F) {
                                        try {
                                            var25.setVerticalAccuracyMeters(var10);
                                        } catch (SecurityException var39) {
                                            break label378;
                                        } catch (Exception var40) {
                                            var78 = var40;
                                            break label394;
                                        }
                                    }

                                    label290: {
                                        label289: {
                                            try {
                                                if (!Float.isInfinite(var11) && !Float.isNaN(var11)) {
                                                    break label289;
                                                }
                                            } catch (SecurityException var59) {
                                                break label378;
                                            } catch (Exception var60) {
                                                var78 = var60;
                                                break label394;
                                            }

                                            var73 = false;
                                            break label290;
                                        }

                                        var73 = true;
                                    }

                                    if (var73 && var11 > 0.0F) {
                                        try {
                                            var25.setBearingAccuracyDegrees(var11);
                                        } catch (SecurityException var37) {
                                            break label378;
                                        } catch (Exception var38) {
                                            var78 = var38;
                                            break label394;
                                        }
                                    }

                                    label279: {
                                        label278: {
                                            try {
                                                if (!Float.isInfinite(var12) && !Float.isNaN(var12)) {
                                                    break label278;
                                                }
                                            } catch (SecurityException var57) {
                                                break label378;
                                            } catch (Exception var58) {
                                                var78 = var58;
                                                break label394;
                                            }

                                            var73 = false;
                                            break label279;
                                        }

                                        var73 = true;
                                    }

                                    if (var73 && var12 > 0.0F) {
                                        try {
                                            var25.setSpeedAccuracyMetersPerSecond(var12);
                                        } catch (SecurityException var35) {
                                            break label378;
                                        } catch (Exception var36) {
                                            var78 = var36;
                                            break label394;
                                        }
                                    }

                                    try {
                                        var25.setTime(System.currentTimeMillis());
                                        var25.setElapsedRealtimeNanos(SystemClock.elapsedRealtimeNanos());
                                    } catch (SecurityException var33) {
                                        break label378;
                                    } catch (Exception var34) {
                                        var78 = var34;
                                        break label394;
                                    }

                                    try {
                                        var24.setTestProviderLocation(var26, var25);
                                    } catch (SecurityException var31) {
                                        break label378;
                                    } catch (Exception var32) {
                                        var78 = var32;
                                        break label394;
                                    }
                                }

                                label232: {
                                    try {
                                        lastFixMs = System.currentTimeMillis();
                                        fixes++;
                                        if (usingRtk) {
                                            var21 = RtkPosition.INSTANCE.getKind();
                                            StringBuilder var82 = new StringBuilder();
                                            var21 = var82.append("sending the RTK receiver's position (").append(var21).append(") to this tablet").toString();
                                            break label232;
                                        }
                                    } catch (SecurityException var29) {
                                        break label378;
                                    } catch (Exception var30) {
                                        var78 = var30;
                                        break label394;
                                    }

                                    if (var15) {
                                        var21 = "car stopped: holding the position still";
                                    } else if (var13) {
                                        var21 = "sending the comma's live GPS to this tablet";
                                    } else if (var14) {
                                        var21 = "GPS gap: sending wheel speed + gyro position (dead reckoning)";
                                    } else {
                                        var21 = "car off / no live fix: sending the comma's last known position";
                                    }
                                }

                                try {
                                    status = var21;
                                    return;
                                } catch (SecurityException var27) {
                                } catch (Exception var28) {
                                    var78 = var28;
                                    break label394;
                                }
                            }

                            ready = false;
                            status = "not allowed yet: make StarView the mock location app (see below)";
                            return;
                        }

                        ready = false;
                        Log.w("StarView/Gps", "push: " + var78);
                    }
                }
            }
        }
    }

    public final void stop() {
        LocationManager lm = lm();
        if (lm == null) {
            return;
        }
        for (String p : providers()) {
            try {
                Result.Companion companion = Result.Companion;
                GpsBridge gpsBridge = this;
                lm.setTestProviderEnabled(p, false);
                lm.removeTestProvider(p);
                KtCompat.resultOf(Unit.INSTANCE);
            } catch (Throwable th) {
                Result.Companion companion2 = Result.Companion;
                KtCompat.resultOf(ResultKt.createFailure(th));
            }
        }
        ready = false;
        status = "off";
    }
}
