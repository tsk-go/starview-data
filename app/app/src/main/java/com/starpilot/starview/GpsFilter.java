package com.starpilot.starview;

import java.util.Arrays;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;

/* compiled from: GpsFilter.kt */
public final class GpsFilter {
    public static final Companion Companion = new Companion(null);
    public static final double LEVER_M = 2.2d;
    private double[] anc;
    private long ancMs;
    private double avgE;
    private double avgN;
    private double avgW;
    private double cE;
    private double cN;
    private double[] cand;
    private int candN;
    private long candStartMs;
    private double fE;
    private double fN;
    private boolean fOK;
    private double holdE;
    private double holdN;
    private double holdW;
    private volatile double lastRejectM;
    private volatile long rejected;
    private double lat0 = Double.NaN;
    private double lon0 = Double.NaN;
    private double kx = 1.0d;
    private double[] ancOdo = new double[4];
    private double ancAcc = 10.0d;
    private double phi = Double.NaN;
    private double[] candOdo = new double[4];
    private long lastUbloxMs = -1000000;
    private long stoppedSinceMs = -1;
    private float lastMovingBearing = Float.NaN;
    private volatile String status = "waiting for the GPS";
    private double[] fOdo = new double[4];

    /* compiled from: GpsFilter.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: GpsFilter.kt */
    public static final class Out {
        private final float acc;
        private final float bearing;
        private final String kind;
        private final double lat;
        private final double lon;
        private final float speed;

        public Out(double lat, double lon, float bearing, float speed, float acc, String kind) {
            Intrinsics.checkNotNullParameter(kind, "kind");
            this.lat = lat;
            this.lon = lon;
            this.bearing = bearing;
            this.speed = speed;
            this.acc = acc;
            this.kind = kind;
        }

        public final float getAcc() {
            return this.acc;
        }

        public final float getBearing() {
            return this.bearing;
        }

        public final String getKind() {
            return this.kind;
        }

        public final double getLat() {
            return this.lat;
        }

        public final double getLon() {
            return this.lon;
        }

        public final float getSpeed() {
            return this.speed;
        }
    }

    private final double[] toEn(double lat, double lon) {
        if (Double.isNaN(this.lat0)) {
            this.lat0 = lat;
            this.lon0 = lon;
            this.kx = Math.cos(Math.toRadians(lat)) * 111320.0d;
        }
        return new double[]{(lon - this.lon0) * this.kx, (lat - this.lat0) * 110540.0d};
    }

    private final double[] toLl(double e, double n) {
        return new double[]{this.lat0 + (n / 110540.0d), this.lon0 + (e / this.kx)};
    }

    public final String getStatus() {
        return this.status;
    }

    public final long getRejected() {
        return this.rejected;
    }

    public final double getLastRejectM() {
        return this.lastRejectM;
    }

    private final double[] moved(double[] o, double[] from) {
        double dx = o[0] - from[0];
        double dy = o[1] - from[1];
        if (Double.isNaN(this.phi)) {
            return new double[]{0.0d, 0.0d};
        }
        double c = Math.cos(this.phi);
        double s = Math.sin(this.phi);
        return new double[]{(c * dx) - (s * dy), (s * dx) + (c * dy)};
    }

    private final double[] predictEn(double[] odo) {
        double[] a = this.anc;
        if (a == null) {
            return null;
        }
        double[] m = moved(odo, this.ancOdo);
        return new double[]{a[0] + m[0], a[1] + m[1]};
    }

    private final float drBearing(double[] odo) {
        return Double.isNaN(this.phi) ? this.lastMovingBearing : (float) ((((90.0d - Math.toDegrees(this.phi + odo[2])) % 360.0d) + 360.0d) % 360.0d);
    }

    public final void onSpeed(long tMs, float vEgo, double[] odo) {
        Intrinsics.checkNotNullParameter(odo, "odo");
        if (vEgo < 0.1f) {
            if (this.stoppedSinceMs < 0) {
                this.stoppedSinceMs = tMs;
                this.avgW = 0.0d;
                double[] p = predictEn(odo);
                if (p != null) {
                    this.holdE = p[0];
                    this.holdN = p[1];
                    this.holdW = 1.0d;
                    return;
                }
                this.holdW = 0.0d;
                return;
            }
            return;
        }
        if (vEgo > 0.3f && this.stoppedSinceMs >= 0) {
            this.stoppedSinceMs = -1L;
            if (this.holdW <= 0.0d || this.anc == null) {
                return;
            }
            this.anc = new double[]{this.holdE, this.holdN};
            double[] copyOf = Arrays.copyOf(odo, odo.length);
            Intrinsics.checkNotNullExpressionValue(copyOf, "copyOf(...)");
            this.ancOdo = copyOf;
        }
    }

    private final boolean holding(long tMs) {
        return this.stoppedSinceMs >= 0 && tMs - this.stoppedSinceMs >= 1000;
    }

    public final boolean isHolding(long tMs) {
        return holding(tMs);
    }

    private final void advance(double[] odo) {
        if (!Double.isNaN(this.phi)) {
            double[] m = moved(odo, this.fOdo);
            this.fE += m[0];
            this.fN += m[1];
        }
        double[] copyOf = Arrays.copyOf(odo, odo.length);
        Intrinsics.checkNotNullExpressionValue(copyOf, "copyOf(...)");
        this.fOdo = copyOf;
        this.fE += this.cE * 0.1d;
        this.fN += this.cN * 0.1d;
        this.cE *= 0.9d;
        this.cN *= 0.9d;
    }

    public final double[] fused(long tMs, double[] odo) {
        Intrinsics.checkNotNullParameter(odo, "odo");
        if (!this.fOK || Double.isNaN(this.phi) || this.anc == null || tMs - this.ancMs > 60000) {
            return null;
        }
        advance(odo);
        if (holding(tMs) && this.holdW > 0.0d) {
            this.fE = this.holdE;
            this.fN = this.holdN;
            this.cE = 0.0d;
            this.cN = 0.0d;
        }
        double[] ll = toLl(this.fE, this.fN);
        float brg = holding(tMs) ? this.lastMovingBearing : drBearing(odo);
        return new double[]{ll[0], ll[1], brg, (tMs - this.ancMs) / 1000.0d};
    }

    public final double[] predict(long tMs, double[] odo) {
        double[] p;
        Intrinsics.checkNotNullParameter(odo, "odo");
        if (this.anc == null || tMs - this.ancMs > 120000) {
            return null;
        }
        if (holding(tMs) && this.holdW > 0.0d) {
            double[] ll = toLl(this.holdE, this.holdN);
            return new double[]{ll[0], ll[1], this.lastMovingBearing, 0.0d};
        }
        if ((Double.isNaN(this.phi) && odo[3] - this.ancOdo[3] > 5.0d) || (p = predictEn(odo)) == null) {
            return null;
        }
        double[] ll2 = toLl(p[0], p[1]);
        return new double[]{ll2[0], ll2[1], drBearing(odo), odo[3] - this.ancOdo[3]};
    }

    public static /* synthetic */ Out onFix$default(GpsFilter gpsFilter, long j, double d, double d2, float f, float f2, float f3, float f4, String str, double[] dArr, float f5, double d3, int i, Object obj) {
        double d4;
        if ((i & 1024) == 0) {
            d4 = d3;
        } else {
            d4 = Double.NaN;
        }
        return gpsFilter.onFix(j, d, d2, f, f2, f3, f4, str, dArr, f5, d4);
    }

    /* JADX WARN: Removed duplicated region for block: B:104:0x02cf  */
    /* JADX WARN: Removed duplicated region for block: B:107:0x02d8  */
    /* JADX WARN: Removed duplicated region for block: B:126:0x0315  */
    /* JADX WARN: Removed duplicated region for block: B:132:0x03a2 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:141:0x040d  */
    /* JADX WARN: Removed duplicated region for block: B:153:0x04c6  */
    /* JADX WARN: Removed duplicated region for block: B:164:0x0390  */
    /* JADX WARN: Removed duplicated region for block: B:167:0x02d2  */
    /* JADX WARN: Removed duplicated region for block: B:169:0x02c5  */
    /* JADX WARN: Removed duplicated region for block: B:171:0x02a0  */
    /* JADX WARN: Removed duplicated region for block: B:176:0x0222  */
    /* JADX WARN: Removed duplicated region for block: B:178:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:180:0x01d2  */
    /* JADX WARN: Removed duplicated region for block: B:181:0x01b7  */
    /* JADX WARN: Removed duplicated region for block: B:186:0x0247  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x013a  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x01a1  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x01cf  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x029d  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x02ba  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Out onFix(long tMs, double lat, double lon, float hAcc, float speed, float bearing, float bAcc, String src, double[] odo, float vEgo, double yawRate) {
        boolean accept;
        String str;
        double[] dArr;
        double acc;
        double[] dArr2;
        long j;
        boolean agrees;
        boolean first;
        double d;
        double bA;
        boolean courseOk;
        String str2;
        double acc2;
        float body;
        double atan2;
        boolean agrees2;
        Intrinsics.checkNotNullParameter(src, "src");
        Intrinsics.checkNotNullParameter(odo, "odo");
        if ((Double.isInfinite(lat) || Double.isNaN(lat)) ? false : true) {
            if ((Double.isInfinite(lon) || Double.isNaN(lon)) ? false : true) {
                if (Math.abs(lat) < 1.0E-6d && Math.abs(lon) < 1.0E-6d) {
                    return null;
                }
                double acc3 = (!(!Float.isInfinite(hAcc) && !Float.isNaN(hAcc)) || hAcc <= 0.0f) ? 10.0d : hAcc;
                if (acc3 > 50.0d) {
                    String format = String.format("ignoring a fix with ±%.0f m", Arrays.copyOf(new Object[]{Double.valueOf(acc3)}, 1));
                    Intrinsics.checkNotNullExpressionValue(format, "format(...)");
                    this.status = format;
                    return null;
                }
                if (Intrinsics.areEqual(src, "ublox")) {
                    this.lastUbloxMs = tMs;
                } else if (tMs - this.lastUbloxMs < 2000) {
                    return null;
                }
                double[] p = toEn(lat, lon);
                double[] a = this.anc;
                boolean fresh = a != null && tMs - this.ancMs < 120000;
                if (fresh) {
                    double[] q = predictEn(odo);
                    Intrinsics.checkNotNull(q);
                    double driven = odo[3] - this.ancOdo[3];
                    double allow = Math.max(25.0d, 2.5d * acc3) + ((Double.isNaN(this.phi) ? 1.0d : 0.08d) * driven);
                    double off = Math.hypot(p[0] - q[0], p[1] - q[1]);
                    if (off > allow) {
                        this.rejected++;
                        this.lastRejectM = off;
                        String format2 = String.format("ignored a jump of %.0f m", Arrays.copyOf(new Object[]{Double.valueOf(off)}, 1));
                        Intrinsics.checkNotNullExpressionValue(format2, "format(...)");
                        this.status = format2;
                    } else {
                        accept = true;
                        if (!accept) {
                            str = "GPS trusted";
                            dArr = odo;
                            acc = acc3;
                            dArr2 = null;
                            j = tMs;
                            agrees = false;
                        } else {
                            double[] c = this.cand;
                            if (c != null) {
                                GpsFilter $this$onFix_u24lambda_u240 = this;
                                double[] m = $this$onFix_u24lambda_u240.moved(odo, $this$onFix_u24lambda_u240.candOdo);
                                str = "GPS trusted";
                                double slack = (2 * acc3) + 15.0d + (Double.isNaN($this$onFix_u24lambda_u240.phi) ? odo[3] - $this$onFix_u24lambda_u240.candOdo[3] : (odo[3] - $this$onFix_u24lambda_u240.candOdo[3]) * 0.05d);
                                if (Math.hypot(p[0] - (c[0] + m[0]), p[1] - (c[1] + m[1])) <= slack) {
                                    agrees2 = true;
                                    if (agrees2) {
                                        this.cand = p;
                                        dArr = odo;
                                        double[] copyOf = Arrays.copyOf(dArr, dArr.length);
                                        Intrinsics.checkNotNullExpressionValue(copyOf, "copyOf(...)");
                                        this.candOdo = copyOf;
                                        j = tMs;
                                        this.candStartMs = j;
                                        this.candN = 1;
                                    } else {
                                        j = tMs;
                                        dArr = odo;
                                        this.cand = p;
                                        double[] copyOf2 = Arrays.copyOf(dArr, dArr.length);
                                        Intrinsics.checkNotNullExpressionValue(copyOf2, "copyOf(...)");
                                        this.candOdo = copyOf2;
                                        this.candN++;
                                    }
                                    int need = !fresh ? 5000 : 4000;
                                    if (acc3 > 30.0d && this.candN >= 8) {
                                        acc = acc3;
                                        if (j - this.candStartMs >= need) {
                                            accept = true;
                                            this.status = fresh ? "GPS moved over to a new position it kept for " + (need / 1000) + " s" : str;
                                            if (holding(tMs)) {
                                                this.holdW = 0.0d;
                                            }
                                            agrees = true;
                                            dArr2 = null;
                                        }
                                    }
                                    if (fresh) {
                                        this.status = "checking the GPS (" + ((j - this.candStartMs) / 1000) + " s steady)";
                                        return null;
                                    }
                                    return null;
                                }
                            } else {
                                str = "GPS trusted";
                            }
                            agrees2 = false;
                            if (agrees2) {
                            }
                            if (!fresh) {
                            }
                            if (acc3 > 30.0d) {
                            }
                            if (fresh) {
                            }
                        }
                        this.cand = dArr2;
                        this.anc = p;
                        double[] copyOf3 = Arrays.copyOf(dArr, dArr.length);
                        Intrinsics.checkNotNullExpressionValue(copyOf3, "copyOf(...)");
                        this.ancOdo = copyOf3;
                        this.ancMs = j;
                        double acc4 = acc;
                        this.ancAcc = acc4;
                        if (!StringsKt.startsWith$default(this.status, "checking", false, 2, (Object) null) || StringsKt.startsWith$default(this.status, "waiting", false, 2, (Object) null) || StringsKt.startsWith$default(this.status, "ignor", false, 2, (Object) null)) {
                            this.status = str;
                        }
                        double w = !(Double.isInfinite(yawRate) && !Double.isNaN(yawRate)) ? yawRate : 0.0d;
                        first = Double.isNaN(this.phi);
                        if (Float.isInfinite(bAcc) && !Float.isNaN(bAcc)) {
                            d = 25.0d;
                        } else {
                            d = 25.0d;
                            if (bAcc > 0.0f) {
                                bA = bAcc;
                                if (speed > (first ? 1.5f : 3.0f)) {
                                    if (vEgo > (first ? 1.5f : 2.0f)) {
                                        if ((Float.isInfinite(bearing) || Float.isNaN(bearing)) ? false : true) {
                                            if (!first) {
                                                d = 20.0d;
                                            }
                                            if (bA <= d && Math.abs(w) < 0.4d) {
                                                courseOk = true;
                                                double d2 = 0.2d;
                                                if (courseOk) {
                                                    str2 = "copyOf(...)";
                                                    acc2 = acc4;
                                                    body = bearing;
                                                } else {
                                                    str2 = "copyOf(...)";
                                                    body = (float) (bearing + Math.toDegrees(Math.atan((2.2d * w) / Math.max(1.5d, vEgo))));
                                                    double m2 = Math.toRadians(90.0d - body) - dArr[2];
                                                    double k = RangesKt.coerceIn((8.0d / bA) * (8.0d / bA), 0.1d, 1.0d) * 0.2d;
                                                    if (Double.isNaN(this.phi)) {
                                                        atan2 = m2;
                                                        acc2 = acc4;
                                                    } else {
                                                        acc2 = acc4;
                                                        atan2 = this.phi + (Math.atan2(Math.sin(m2 - this.phi), Math.cos(m2 - this.phi)) * k);
                                                    }
                                                    this.phi = atan2;
                                                    this.lastMovingBearing = ((body % 360.0f) + 360.0f) % 360.0f;
                                                }
                                                if (this.fOK || agrees || Double.isNaN(this.phi)) {
                                                    this.fE = p[0];
                                                    this.fN = p[1];
                                                    this.cE = 0.0d;
                                                    this.cN = 0.0d;
                                                    double[] copyOf4 = Arrays.copyOf(dArr, dArr.length);
                                                    Intrinsics.checkNotNullExpressionValue(copyOf4, str2);
                                                    this.fOdo = copyOf4;
                                                    this.fOK = true;
                                                } else {
                                                    advance(dArr);
                                                    if (acc2 <= 5.0d) {
                                                        d2 = 0.35d;
                                                    } else if (acc2 > 15.0d) {
                                                        d2 = 0.1d;
                                                    }
                                                    double k2 = d2;
                                                    this.cE = (((p[0] - this.fE) - this.cE) * k2) + this.cE;
                                                    this.cN = (((p[1] - this.fN) - this.cN) * k2) + this.cN;
                                                }
                                                if (!holding(tMs)) {
                                                    double w2 = 1.0d / Math.max(1.0d, acc2 * acc2);
                                                    this.avgE = ((this.avgE * this.avgW) + (p[0] * w2)) / (this.avgW + w2);
                                                    this.avgN = ((this.avgN * this.avgW) + (p[1] * w2)) / (this.avgW + w2);
                                                    this.avgW += w2;
                                                    if (this.holdW <= 0.0d) {
                                                        this.holdE = this.avgE;
                                                        this.holdN = this.avgN;
                                                        this.holdW = 1.0d;
                                                    } else if (tMs - this.stoppedSinceMs > 5000 && Math.hypot(this.avgE - this.holdE, this.avgN - this.holdN) > 10.0d) {
                                                        this.holdE += (this.avgE - this.holdE) * 0.02d;
                                                        this.holdN += (this.avgN - this.holdN) * 0.02d;
                                                    }
                                                    this.fE = this.holdE;
                                                    this.fN = this.holdN;
                                                    this.cE = 0.0d;
                                                    this.cN = 0.0d;
                                                    double[] ll = toLl(this.holdE, this.holdN);
                                                    return new Out(ll[0], ll[1], this.lastMovingBearing, 0.0f, (float) acc2, "held");
                                                }
                                                double acc5 = acc2;
                                                float brg = courseOk ? body : drBearing(dArr);
                                                return new Out(lat, lon, brg, speed, (float) acc5, "live");
                                            }
                                        }
                                    }
                                }
                                courseOk = false;
                                double d22 = 0.2d;
                                if (courseOk) {
                                }
                                if (this.fOK) {
                                }
                                this.fE = p[0];
                                this.fN = p[1];
                                this.cE = 0.0d;
                                this.cN = 0.0d;
                                double[] copyOf42 = Arrays.copyOf(dArr, dArr.length);
                                Intrinsics.checkNotNullExpressionValue(copyOf42, str2);
                                this.fOdo = copyOf42;
                                this.fOK = true;
                                if (!holding(tMs)) {
                                }
                            }
                        }
                        bA = 10.0d;
                        if (speed > (first ? 1.5f : 3.0f)) {
                        }
                        courseOk = false;
                        double d222 = 0.2d;
                        if (courseOk) {
                        }
                        if (this.fOK) {
                        }
                        this.fE = p[0];
                        this.fN = p[1];
                        this.cE = 0.0d;
                        this.cN = 0.0d;
                        double[] copyOf422 = Arrays.copyOf(dArr, dArr.length);
                        Intrinsics.checkNotNullExpressionValue(copyOf422, str2);
                        this.fOdo = copyOf422;
                        this.fOK = true;
                        if (!holding(tMs)) {
                        }
                    }
                }
                accept = false;
                if (!accept) {
                }
                this.cand = dArr2;
                this.anc = p;
                double[] copyOf32 = Arrays.copyOf(dArr, dArr.length);
                Intrinsics.checkNotNullExpressionValue(copyOf32, "copyOf(...)");
                this.ancOdo = copyOf32;
                this.ancMs = j;
                double acc42 = acc;
                this.ancAcc = acc42;
                if (!StringsKt.startsWith$default(this.status, "checking", false, 2, (Object) null)) {
                }
                this.status = str;
                if (!(Double.isInfinite(yawRate) && !Double.isNaN(yawRate))) {
                }
                first = Double.isNaN(this.phi);
                if (Float.isInfinite(bAcc) && !Float.isNaN(bAcc)) {
                }
                bA = 10.0d;
                if (speed > (first ? 1.5f : 3.0f)) {
                }
                courseOk = false;
                double d2222 = 0.2d;
                if (courseOk) {
                }
                if (this.fOK) {
                }
                this.fE = p[0];
                this.fN = p[1];
                this.cE = 0.0d;
                this.cN = 0.0d;
                double[] copyOf4222 = Arrays.copyOf(dArr, dArr.length);
                Intrinsics.checkNotNullExpressionValue(copyOf4222, str2);
                this.fOdo = copyOf4222;
                this.fOK = true;
                if (!holding(tMs)) {
                }
            }
        }
        return null;
    }
}
