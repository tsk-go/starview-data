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

    /* JADX WARN: Removed duplicated region for block: B:118:0x01ec A[Catch: Exception -> 0x0220, SecurityException -> 0x0222, TryCatch #6 {SecurityException -> 0x0222, Exception -> 0x0220, blocks: (B:55:0x012c, B:57:0x0139, B:58:0x0145, B:60:0x014b, B:67:0x015e, B:69:0x0168, B:75:0x0177, B:76:0x017a, B:78:0x0180, B:84:0x018f, B:85:0x0192, B:87:0x0198, B:93:0x01a7, B:95:0x01aa, B:116:0x01d5, B:118:0x01ec, B:119:0x021d), top: B:54:0x012c }] */
    /* JADX WARN: Removed duplicated region for block: B:122:0x020c  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x00b6  */
    /* JADX WARN: Removed duplicated region for block: B:149:0x0085  */
    /* JADX WARN: Removed duplicated region for block: B:154:0x0079  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0077  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0082  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00e0 A[ADDED_TO_REGION, RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00e1  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0107 A[Catch: Exception -> 0x0224, SecurityException -> 0x0247, TRY_LEAVE, TryCatch #5 {SecurityException -> 0x0247, Exception -> 0x0224, blocks: (B:46:0x00f9, B:47:0x0101, B:49:0x0107), top: B:45:0x00f9 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final void push(double lat0, double lon0, double alt, float speed, float bearing0, float hAcc0, float vAcc, float bAcc, float sAcc, boolean live, boolean dr, boolean held) {
        float hAcc;
        float bearing;
        Context ctx;
        LocationManager lm;
        Iterator<String> it;
        double[] rtk = RtkPosition.INSTANCE.now(System.currentTimeMillis(), vEgoNow);
        double lat = rtk != null ? rtk[0] : lat0;
        double lon = rtk != null ? rtk[1] : lon0;
        if (rtk != null) {
            double accM = RtkPosition.INSTANCE.getAccM();
            hAcc = !Double.isInfinite(accM) && !Double.isNaN(accM) ? RangesKt.coerceAtLeast((float) RtkPosition.INSTANCE.getAccM(), 0.01f) : 1.0f;
        } else {
            hAcc = hAcc0;
        }
        if (rtk != null) {
            double heading = RtkPosition.INSTANCE.getHeading();
            if ((Double.isInfinite(heading) || Double.isNaN(heading)) ? false : true) {
                bearing = (float) RtkPosition.INSTANCE.getHeading();
                usingRtk = rtk == null;
                outLat = lat;
                outLon = lon;
                outKind = !held ? "held" : dr ? "dr" : live ? "live" : "last";
                outBearing = bearing;
                if ((!live || dr) && !held && vEgoNow > 1.5f) {
                    if (Float.isInfinite(bearing) && !Float.isNaN(bearing)) {
                        float b = turnSentLast;
                        if (!Float.isNaN(b)) {
                            turnSentDeg -= (((bearing - b) + 540.0f) % 360.0f) - 180.0f;
                        }
                        turnSentLast = bearing;
                        ctx = app;
                        if (ctx == null || !enabled(ctx) || (lm = lm()) == null || !setup(lm)) {
                            return;
                        }
                        try {
                            it = providers().iterator();
                            while (it.hasNext()) {
                                String p = it.next();
                                Location $this$push_u24lambda_u242 = new Location(p);
                                Iterator<String> it2 = it;
                                $this$push_u24lambda_u242.setLatitude(lat);
                                $this$push_u24lambda_u242.setLongitude(lon);
                                double lat2 = lat;
                                try {
                                    $this$push_u24lambda_u242.setAltitude(alt);
                                    double[] rtk2 = rtk;
                                    try {
                                        $this$push_u24lambda_u242.setSpeed(RangesKt.coerceAtLeast(speed, 0.0f));
                                        if (!Float.isNaN(bearing)) {
                                            $this$push_u24lambda_u242.setBearing(((bearing % 360.0f) + 360.0f) % 360.0f);
                                        }
                                        $this$push_u24lambda_u242.setAccuracy((!(!Float.isInfinite(hAcc) && !Float.isNaN(hAcc)) || hAcc <= 0.0f) ? 5.0f : hAcc);
                                        if (((Float.isInfinite(vAcc) || Float.isNaN(vAcc)) ? false : true) && vAcc > 0.0f) {
                                            $this$push_u24lambda_u242.setVerticalAccuracyMeters(vAcc);
                                        }
                                        if (((Float.isInfinite(bAcc) || Float.isNaN(bAcc)) ? false : true) && bAcc > 0.0f) {
                                            $this$push_u24lambda_u242.setBearingAccuracyDegrees(bAcc);
                                        }
                                        if (((Float.isInfinite(sAcc) || Float.isNaN(sAcc)) ? false : true) && sAcc > 0.0f) {
                                            $this$push_u24lambda_u242.setSpeedAccuracyMetersPerSecond(sAcc);
                                        }
                                        $this$push_u24lambda_u242.setTime(System.currentTimeMillis());
                                        $this$push_u24lambda_u242.setElapsedRealtimeNanos(SystemClock.elapsedRealtimeNanos());
                                        lm.setTestProviderLocation(p, $this$push_u24lambda_u242);
                                        rtk = rtk2;
                                        it = it2;
                                        lat = lat2;
                                    } catch (SecurityException e) {
                                        ready = false;
                                        status = "not allowed yet: make StarView the mock location app (see below)";
                                        return;
                                    } catch (Exception e2) {
                                        e = e2;
                                        ready = false;
                                        Log.w("StarView/Gps", "push: " + e);
                                        return;
                                    }
                                } catch (SecurityException e3) {
                                    ready = false;
                                    status = "not allowed yet: make StarView the mock location app (see below)";
                                    return;
                                } catch (Exception e4) {
                                    e = e4;
                                    ready = false;
                                    Log.w("StarView/Gps", "push: " + e);
                                    return;
                                }
                            }
                            lastFixMs = System.currentTimeMillis();
                            fixes++;
                            status = !usingRtk ? "sending the RTK receiver's position (" + RtkPosition.INSTANCE.getKind() + ") to this tablet" : held ? "car stopped: holding the position still" : live ? "sending the comma's live GPS to this tablet" : dr ? "GPS gap: sending wheel speed + gyro position (dead reckoning)" : "car off / no live fix: sending the comma's last known position";
                            return;
                        } catch (SecurityException e5) {
                        } catch (Exception e6) {
                            e = e6;
                        }
                    }
                }
                turnSentLast = Float.NaN;
                ctx = app;
                if (ctx == null) {
                    return;
                }
                it = providers().iterator();
                while (it.hasNext()) {
                }
                lastFixMs = System.currentTimeMillis();
                fixes++;
                status = !usingRtk ? "sending the RTK receiver's position (" + RtkPosition.INSTANCE.getKind() + ") to this tablet" : held ? "car stopped: holding the position still" : live ? "sending the comma's live GPS to this tablet" : dr ? "GPS gap: sending wheel speed + gyro position (dead reckoning)" : "car off / no live fix: sending the comma's last known position";
                return;
            }
        }
        bearing = bearing0;
        usingRtk = rtk == null;
        outLat = lat;
        outLon = lon;
        outKind = !held ? "held" : dr ? "dr" : live ? "live" : "last";
        outBearing = bearing;
        if (!live) {
        }
        if (Float.isInfinite(bearing) && !Float.isNaN(bearing)) {
        }
        turnSentLast = Float.NaN;
        ctx = app;
        if (ctx == null) {
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
