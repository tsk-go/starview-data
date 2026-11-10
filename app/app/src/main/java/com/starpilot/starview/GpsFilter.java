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

    /* VF: from Vineflower (jadx: Removed duplicated region;incorrect) */
    public final GpsFilter.Out onFix(
        long var1, double var3, double var5, float var7, float var8, float var9, float var10, String var11, double[] var12, float var13, double var14
    ) {
        Intrinsics.checkNotNullParameter(var11, "src");
        Intrinsics.checkNotNullParameter(var12, "odo");
        boolean var28;
        if (!Double.isInfinite(var3) && !Double.isNaN(var3)) {
            var28 = true;
        } else {
            var28 = false;
        }

        if (var28) {
            if (!Double.isInfinite(var5) && !Double.isNaN(var5)) {
                var28 = (boolean)true;
            } else {
                var28 = (boolean)false;
            }

            if (var28 && (!(Math.abs(var3) < 1.0E-6) || !(Math.abs(var5) < 1.0E-6))) {
                if (!Float.isInfinite(var7) && !Float.isNaN(var7)) {
                    var28 = (boolean)true;
                } else {
                    var28 = (boolean)false;
                }

                double var16;
                if (var28 && var7 > 0.0F) {
                    var16 = var7;
                } else {
                    var16 = 10.0;
                }

                if (var16 > 50.0) {
                    var11 = String.format("ignoring a fix with ±%.0f m", Arrays.copyOf(new Object[]{var16}, 1));
                    Intrinsics.checkNotNullExpressionValue(var11, "format(...)");
                    this.status = var11;
                    return null;
                }

                if (Intrinsics.areEqual(var11, "ublox")) {
                    this.lastUbloxMs = var1;
                } else if (var1 - this.lastUbloxMs < 2000L) {
                    return null;
                }

                double[] var32 = this.toEn(var3, var5);
                boolean var29;
                if (this.anc != null && var1 - this.ancMs < 120000L) {
                    var29 = true;
                } else {
                    var29 = false;
                }

                label246: {
                    if (var29) {
                        double[] var41 = this.predictEn(var12);
                        Intrinsics.checkNotNull(var41);
                        double var24 = var12[3];
                        double var20 = this.ancOdo[3];
                        double var22 = Math.max(25.0, 2.5 * var16);
                        double var18;
                        if (Double.isNaN(this.phi)) {
                            var18 = 1.0;
                        } else {
                            var18 = 0.08;
                        }

                        double var26 = Math.hypot(var32[0] - var41[0], var32[1] - var41[1]);
                        if (var26 <= var22 + var18 * (var24 - var20)) {
                            var28 = (boolean)true;
                            break label246;
                        }

                        this.rejected++;
                        this.lastRejectM = var26;
                        var11 = String.format("ignored a jump of %.0f m", Arrays.copyOf(new Object[]{var26}, 1));
                        Intrinsics.checkNotNullExpressionValue(var11, "format(...)");
                        this.status = var11;
                    }

                    var28 = (boolean)false;
                }

                if (var28) {
                    boolean var71 = false;
                    var28 = var71;
                } else {
                    label236: {
                        double[] var43 = this.cand;
                        if (var43 != null) {
                            GpsFilter var34 = this;
                            double[] var33 = var34.moved(var12, var34.candOdo);
                            double var56 = 2;
                            double var53;
                            if (Double.isNaN(var34.phi)) {
                                var53 = var12[3] - var34.candOdo[3];
                            } else {
                                var53 = (var12[3] - var34.candOdo[3]) * 0.05;
                            }

                            if (Math.hypot(var32[0] - (var43[0] + var33[0]), var32[1] - (var43[1] + var33[1])) <= var56 * var16 + 15.0 + var53) {
                                var28 = (boolean)true;
                            } else {
                                var28 = (boolean)false;
                            }

                            if (var28) {
                                var28 = (boolean)true;
                                break label236;
                            }
                        }

                        var28 = (boolean)false;
                    }

                    if (!var28) {
                        this.cand = var32;
                        double[] var44 = Arrays.copyOf(var12, var12.length);
                        Intrinsics.checkNotNullExpressionValue(var44, "copyOf(...)");
                        this.candOdo = var44;
                        this.candStartMs = var1;
                        this.candN = 1;
                    } else {
                        this.cand = var32;
                        double[] var45 = Arrays.copyOf(var12, var12.length);
                        Intrinsics.checkNotNullExpressionValue(var45, "copyOf(...)");
                        this.candOdo = var45;
                        this.candN++;
                    }

                    if (var29) {
                        var28 = (short)5000;
                    } else {
                        var28 = (short)4000;
                    }

                    if (!(var16 <= 30.0) || this.candN < 8 || var1 - this.candStartMs < var28) {
                        if (!var29) {
                            var1 = (var1 - this.candStartMs) / 1000;
                            this.status = "checking the GPS (" + var1 + " s steady)";
                        }

                        return null;
                    }

                    boolean var30 = true;
                    if (var29) {
                        var28 /= 1000;
                        var11 = "GPS moved over to a new position it kept for " + var28 + " s";
                    } else {
                        var11 = "GPS trusted";
                    }

                    this.status = var11;
                    if (this.holding(var1)) {
                        this.holdW = 0.0;
                    }

                    var28 = (boolean)true;
                }

                this.cand = null;
                this.anc = var32;
                double[] var47 = Arrays.copyOf(var12, var12.length);
                Intrinsics.checkNotNullExpressionValue(var47, "copyOf(...)");
                this.ancOdo = var47;
                this.ancMs = var1;
                this.ancAcc = var16;
                if (StringsKt.startsWith(this.status, "checking", false)
                    || StringsKt.startsWith(this.status, "waiting", false)
                    || StringsKt.startsWith(this.status, "ignor", false)) {
                    this.status = "GPS trusted";
                }

                if (!Double.isInfinite(var14) && !Double.isNaN(var14)) {
                    var29 = true;
                } else {
                    var29 = false;
                }

                if (!var29) {
                    var14 = 0.0;
                }

                boolean var31 = Double.isNaN(this.phi);
                if (!Float.isInfinite(var10) && !Float.isNaN(var10)) {
                    var29 = true;
                } else {
                    var29 = false;
                }

                double var54;
                if (var29 && var10 > 0.0F) {
                    var54 = var10;
                } else {
                    var54 = 10.0;
                }

                double var57 = 25.0;
                var10 = 1.5F;
                if (var31) {
                    var7 = 1.5F;
                } else {
                    var7 = 3.0F;
                }

                label195: {
                    if (var8 > var7) {
                        if (var31) {
                            var7 = var10;
                        } else {
                            var7 = 2.0F;
                        }

                        if (var13 > var7) {
                            if (!Float.isInfinite(var9) && !Float.isNaN(var9)) {
                                var29 = true;
                            } else {
                                var29 = false;
                            }

                            if (var29) {
                                if (!var31) {
                                    var57 = 20.0;
                                }

                                if (var54 <= var57 && Math.abs(var14) < 0.4) {
                                    var29 = true;
                                    break label195;
                                }
                            }
                        }
                    }

                    var29 = false;
                }

                var57 = 0.2;
                if (var29) {
                    var7 = (float)(var9 + Math.toDegrees(Math.atan(2.2 * var14 / Math.max(1.5, (double)var13))));
                    var14 = Math.toRadians(90.0 - var7) - var12[2];
                    var54 = RangesKt.coerceIn(8.0 / var54 * (8.0 / var54), 0.1, 1.0);
                    if (!Double.isNaN(this.phi)) {
                        var14 = this.phi + Math.atan2(Math.sin(var14 - this.phi), Math.cos(var14 - this.phi)) * (var54 * 0.2);
                    }

                    this.phi = var14;
                    this.lastMovingBearing = (var7 % 360.0F + 360.0F) % 360.0F;
                } else {
                    var7 = var9;
                }

                if (this.fOK && !var28 && !Double.isNaN(this.phi)) {
                    this.advance(var12);
                    if (var16 <= 5.0) {
                        var14 = 0.35;
                    } else if (var16 <= 15.0) {
                        var14 = var57;
                    } else {
                        var14 = 0.1;
                    }

                    this.cE = (var32[0] - this.fE - this.cE) * var14 + this.cE;
                    this.cN = (var32[1] - this.fN - this.cN) * var14 + this.cN;
                } else {
                    this.fE = var32[0];
                    this.fN = var32[1];
                    this.cE = 0.0;
                    this.cN = 0.0;
                    double[] var48 = Arrays.copyOf(var12, var12.length);
                    Intrinsics.checkNotNullExpressionValue(var48, "copyOf(...)");
                    this.fOdo = var48;
                    this.fOK = true;
                }

                if (this.holding(var1)) {
                    var3 = 1.0 / Math.max(1.0, var16 * var16);
                    this.avgE = (this.avgE * this.avgW + var32[0] * var3) / (this.avgW + var3);
                    this.avgN = (this.avgN * this.avgW + var32[1] * var3) / (this.avgW + var3);
                    this.avgW += var3;
                    if (this.holdW <= 0.0) {
                        this.holdE = this.avgE;
                        this.holdN = this.avgN;
                        this.holdW = 1.0;
                    } else if (var1 - this.stoppedSinceMs > 5000L && Math.hypot(this.avgE - this.holdE, this.avgN - this.holdN) > 10.0) {
                        this.holdE = this.holdE + (this.avgE - this.holdE) * 0.02;
                        this.holdN = this.holdN + (this.avgN - this.holdN) * 0.02;
                    }

                    this.fE = this.holdE;
                    this.fN = this.holdN;
                    this.cE = 0.0;
                    this.cN = 0.0;
                    double[] var49 = this.toLl(this.holdE, this.holdN);
                    return new GpsFilter.Out(var49[0], var49[1], this.lastMovingBearing, 0.0F, (float)var16, "held");
                }

                if (!var29) {
                    var7 = this.drBearing(var12);
                }

                return new GpsFilter.Out(var3, var5, var7, var8, (float)var16, "live");
            }
        }

        return null;
    }
}
