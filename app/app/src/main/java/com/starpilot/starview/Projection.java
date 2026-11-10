package com.starpilot.starview;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

/* compiled from: Projection.kt */
public final class Projection {
    public static final double CLIP_MARGIN = 500.0d;
    public static final double HEIGHT_INIT = 1.22d;
    private int frameH;
    private int frameW;
    private double fullH;
    private double fullW;
    private boolean valid;
    private double videoTx;
    private double videoTy;
    private double viewH;
    private double viewW;
    private boolean wide;
    private double xOffset;
    private double yOffset;
    public static final Companion Companion = new Companion(null);
    private static final Companion.Cam AR_OX_FCAM = new Companion.Cam(1928, 1208, 2648.0d);
    private static final Companion.Cam AR_OX_ECAM = new Companion.Cam(1928, 1208, 567.0d);
    private static final Companion.Cam OS_FCAM = new Companion.Cam(1344, 760, 1141.5d);
    private static final Companion.Cam OS_ECAM = new Companion.Cam(1344, 760, 425.25d);
    private static final double[] VIEW_FROM_DEVICE = {0.0d, 1.0d, 0.0d, 0.0d, 0.0d, 1.0d, 1.0d, 0.0d, 0.0d};
    private double[] rpy = {0.0d, 0.0d, 0.0d};
    private double pathOffsetZ = 1.22d;
    private double zoom = 1.0d;
    private double videoScale = 1.0d;
    private double[] full = new double[9];
    private double[] calib = new double[9];
    private Companion.Cam camNow = OS_FCAM;

    /* compiled from: Projection.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* compiled from: Projection.kt */
        public static final /* data */ class Cam {
            private final double f;
            private final int h;
            private final int w;

            public static /* synthetic */ Cam copy$default(Cam cam, int i, int i2, double d, int i3, Object obj) {
                if ((i3 & 1) != 0) {
                    i = cam.w;
                }
                if ((i3 & 2) != 0) {
                    i2 = cam.h;
                }
                if ((i3 & 4) != 0) {
                    d = cam.f;
                }
                return cam.copy(i, i2, d);
            }

            /* renamed from: component1, reason: from getter */
            public final int getW() {
                return this.w;
            }

            /* renamed from: component2, reason: from getter */
            public final int getH() {
                return this.h;
            }

            /* renamed from: component3, reason: from getter */
            public final double getF() {
                return this.f;
            }

            public final Cam copy(int w, int h, double f) {
                return new Cam(w, h, f);
            }

            public boolean equals(Object other) {
                if (this == other) {
                    return true;
                }
                if (!(other instanceof Cam)) {
                    return false;
                }
                Cam cam = (Cam) other;
                return this.w == cam.w && this.h == cam.h && Double.compare(this.f, cam.f) == 0;
            }

            public int hashCode() {
                return (((Integer.hashCode(this.w) * 31) + Integer.hashCode(this.h)) * 31) + Double.hashCode(this.f);
            }

            public String toString() {
                return "Cam(w=" + this.w + ", h=" + this.h + ", f=" + this.f + ")";
            }

            public Cam(int w, int h, double f) {
                this.w = w;
                this.h = h;
                this.f = f;
            }

            public final double getCx() {
                return this.w / 2.0d;
            }

            public final double getCy() {
                return this.h / 2.0d;
            }

            public final double getF() {
                return this.f;
            }

            public final int getH() {
                return this.h;
            }

            public final int getW() {
                return this.w;
            }
        }

        public final Cam getAR_OX_ECAM() {
            return Projection.AR_OX_ECAM;
        }

        public final Cam getAR_OX_FCAM() {
            return Projection.AR_OX_FCAM;
        }

        public final Cam getOS_ECAM() {
            return Projection.OS_ECAM;
        }

        public final Cam getOS_FCAM() {
            return Projection.OS_FCAM;
        }

        public final double[] getVIEW_FROM_DEVICE() {
            return Projection.VIEW_FROM_DEVICE;
        }

        public final double[] mul(double[] a, double[] b) {
            Intrinsics.checkNotNullParameter(a, "a");
            Intrinsics.checkNotNullParameter(b, "b");
            double[] r = new double[9];
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    r[(i * 3) + j] = (a[i * 3] * b[j]) + (a[(i * 3) + 1] * b[j + 3]) + (a[(i * 3) + 2] * b[j + 6]);
                }
            }
            return r;
        }

        public final double[] mulv(double[] a, double x, double y, double z) {
            Intrinsics.checkNotNullParameter(a, "a");
            return new double[]{(a[0] * x) + (a[1] * y) + (a[2] * z), (a[3] * x) + (a[4] * y) + (a[5] * z), (a[6] * x) + (a[7] * y) + (a[8] * z)};
        }

        public final Cam camFor(int frameW, int frameH, boolean wide) {
            boolean os = ((double) frameW) / ((double) frameH) > 1.68d;
            Cam c = os ? wide ? getOS_ECAM() : getOS_FCAM() : wide ? getAR_OX_ECAM() : getAR_OX_FCAM();
            return new Cam(frameW, frameH, (c.getF() * frameW) / c.getW());
        }

        public final double[] groundHomographyRows(float[] rpy, float height, int frameW, int frameH, boolean wide) {
            Intrinsics.checkNotNullParameter(rpy, "rpy");
            Cam c = camFor(frameW, frameH, wide);
            double[] k = {c.getF(), 0.0d, c.getCx(), 0.0d, c.getF(), c.getCy(), 0.0d, 0.0d, 1.0d};
            double[] ct = mul(k, mul(getVIEW_FROM_DEVICE(), rotFromEuler(rpy[0], rpy[1], rpy[2])));
            return new double[]{ct[0], ct[1], height * ct[2], ct[3], ct[4], height * ct[5], ct[6], ct[7], height * ct[8]};
        }

        public final double[] inverse3(double[] m) {
            Intrinsics.checkNotNullParameter(m, "m");
            double a = m[0];
            double b = m[1];
            double c = m[2];
            double d = m[3];
            double e = m[4];
            double f = m[5];
            double g = m[6];
            double h = m[7];
            double i = m[8];
            double det = ((((e * i) - (f * h)) * a) - (((d * i) - (f * g)) * b)) + (((d * h) - (e * g)) * c);
            if (Math.abs(det) < 1.0E-12d) {
                return null;
            }
            double r = 1 / det;
            return new double[]{((e * i) - (f * h)) * r, ((c * h) - (b * i)) * r, ((b * f) - (c * e)) * r, ((f * g) - (d * i)) * r, ((a * i) - (c * g)) * r, ((c * d) - (a * f)) * r, ((d * h) - (e * g)) * r, ((b * g) - (a * h)) * r, ((a * e) - (b * d)) * r};
        }

        public final double[] fisheyeToPinhole(double du, double dv, double f) {
            double r = Math.hypot(du, dv);
            if (r < 1.0E-9d) {
                return new double[]{du, dv};
            }
            double th = r / f;
            if (th > 1.4d) {
                return null;
            }
            double s = (Math.tan(th) * f) / r;
            return new double[]{du * s, dv * s};
        }

        public final double[] pinholeToFisheye(double du, double dv, double f) {
            double r = Math.hypot(du, dv);
            if (r < 1.0E-9d) {
                return new double[]{du, dv};
            }
            double s = (Math.atan(r / f) * f) / r;
            return new double[]{du * s, dv * s};
        }

        /* compiled from: Projection.kt */
        public static final class GroundInverse {
            private final float cx;
            private final float cy;
            private final float f;
            private final boolean fisheye;
            private final double[] hInv;

            public GroundInverse(float f, float cx, float cy, double[] hInv, boolean fisheye) {
                this.f = f;
                this.cx = cx;
                this.cy = cy;
                this.hInv = hInv;
                this.fisheye = fisheye;
            }

            public /* synthetic */ GroundInverse(float f, float f2, float f3, double[] dArr, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
                this(f, f2, f3, dArr, (i & 16) != 0 ? false : z);
            }

            public final float getCx() {
                return this.cx;
            }

            public final float getCy() {
                return this.cy;
            }

            public final float getF() {
                return this.f;
            }

            public final boolean getFisheye() {
                return this.fisheye;
            }

            public final float[] undistort(float u, float v) {
                if (!this.fisheye) {
                    return new float[]{u, v};
                }
                double[] p = Projection.Companion.fisheyeToPinhole(u - this.cx, v - this.cy, this.f);
                if (p == null) {
                    return null;
                }
                return new float[]{(float) (this.cx + p[0]), (float) (this.cy + p[1])};
            }

            public final float offAxis(float u, float v) {
                float r = (float) Math.hypot(u - this.cx, v - this.cy);
                return this.fisheye ? r / this.f : (float) Math.atan(r / this.f);
            }

            public final float[] ground(float u0, float v0) {
                float[] uv;
                double[] m = this.hInv;
                if (m == null || (uv = undistort(u0, v0)) == null) {
                    return null;
                }
                float u = uv[0];
                float v = uv[1];
                double x = (m[0] * u) + (m[1] * v) + m[2];
                double y = (m[3] * u) + (m[4] * v) + m[5];
                double w = (m[6] * u) + (m[7] * v) + m[8];
                if (w < 1.0E-6d) {
                    return null;
                }
                double gx = x / w;
                if (gx <= 0.0d) {
                    return null;
                }
                return new float[]{(float) gx, (float) (y / w)};
            }
        }

        public final GroundInverse groundInverse(float[] rpy, float height, int frameW, int frameH, boolean wide) {
            Intrinsics.checkNotNullParameter(rpy, "rpy");
            Cam c = camFor(frameW, frameH, wide);
            return new GroundInverse((float) c.getF(), (float) c.getCx(), (float) c.getCy(), inverse3(groundHomographyRows(rpy, height, frameW, frameH, wide)), wide);
        }

        public final double[] rotFromEuler(double roll, double pitch, double yaw) {
            double cr = Math.cos(roll);
            double sr = Math.sin(roll);
            double cp = Math.cos(pitch);
            double sp = Math.sin(pitch);
            double cy = Math.cos(yaw);
            double sy = Math.sin(yaw);
            double[] rx = {1.0d, 0.0d, 0.0d, 0.0d, cr, -sr, 0.0d, sr, cr};
            double[] ry = {cp, 0.0d, sp, 0.0d, 1.0d, 0.0d, -sp, 0.0d, cp};
            double[] rz = {cy, -sy, 0.0d, sy, cy, 0.0d, 0.0d, 0.0d, 1.0d};
            return mul(rz, mul(ry, rx));
        }
    }

    public final int getFrameH() {
        return this.frameH;
    }

    public final int getFrameW() {
        return this.frameW;
    }

    public final void setFrameH(int i) {
        this.frameH = i;
    }

    public final void setFrameW(int i) {
        this.frameW = i;
    }

    public final boolean getWide() {
        return this.wide;
    }

    public final void setWide(boolean z) {
        this.wide = z;
    }

    public final double getViewH() {
        return this.viewH;
    }

    public final double getViewW() {
        return this.viewW;
    }

    public final void setViewH(double d) {
        this.viewH = d;
    }

    public final void setViewW(double d) {
        this.viewW = d;
    }

    public final double getFullH() {
        return this.fullH;
    }

    public final double getFullW() {
        return this.fullW;
    }

    public final void setFullH(double d) {
        this.fullH = d;
    }

    public final void setFullW(double d) {
        this.fullW = d;
    }

    public final double[] getRpy() {
        return this.rpy;
    }

    public final void setRpy(double[] dArr) {
        Intrinsics.checkNotNullParameter(dArr, "<set-?>");
        this.rpy = dArr;
    }

    public final double getPathOffsetZ() {
        return this.pathOffsetZ;
    }

    public final void setPathOffsetZ(double d) {
        this.pathOffsetZ = d;
    }

    public final double getXOffset() {
        return this.xOffset;
    }

    public final double getYOffset() {
        return this.yOffset;
    }

    public final double getZoom() {
        return this.zoom;
    }

    public final void setXOffset(double d) {
        this.xOffset = d;
    }

    public final void setYOffset(double d) {
        this.yOffset = d;
    }

    public final void setZoom(double d) {
        this.zoom = d;
    }

    public final double getVideoScale() {
        return this.videoScale;
    }

    public final double getVideoTx() {
        return this.videoTx;
    }

    public final double getVideoTy() {
        return this.videoTy;
    }

    public final void setVideoScale(double d) {
        this.videoScale = d;
    }

    public final void setVideoTx(double d) {
        this.videoTx = d;
    }

    public final void setVideoTy(double d) {
        this.videoTy = d;
    }

    public final boolean getValid() {
        return this.valid;
    }

    public final void setValid(boolean z) {
        this.valid = z;
    }

    public final Companion.Cam cam() {
        return this.frameH > 0 ? Companion.camFor(this.frameW, this.frameH, this.wide) : this.frameW == 1344 ? this.wide ? OS_ECAM : OS_FCAM : this.wide ? AR_OX_ECAM : AR_OX_FCAM;
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0098, code lost:
    
        if ((r47.frameW / r47.frameH) > 1.68d) goto L20;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void update() {
        double h;
        double[] k;
        double z;
        double yo;
        if (this.frameW != 0) {
            if (!(this.viewW == 0.0d)) {
                Companion.Cam c = cam();
                double[] k2 = {c.getF(), 0.0d, c.getCx(), 0.0d, c.getF(), c.getCy(), 0.0d, 0.0d, 1.0d};
                double[] deviceFromCalib = Companion.rotFromEuler(this.rpy[0], this.rpy[1], this.rpy[2]);
                double[] viewFromCalib = Companion.mul(VIEW_FROM_DEVICE, deviceFromCalib);
                double[] calibTransform = Companion.mul(k2, viewFromCalib);
                double[] kep = Companion.mulv(calibTransform, 1000.0d, 0.0d, 0.0d);
                double w = this.viewW;
                double h2 = this.viewH;
                double sensorW = 1344.0d;
                if (this.frameH > 0) {
                    h = h2;
                } else {
                    h = h2;
                }
                if (this.frameW != 1344) {
                    sensorW = 1928.0d;
                }
                double z2 = ((this.wide ? 2.0d : 1.1d) * sensorW) / c.getW();
                if (this.fullW > 0.0d && w < this.fullW * 0.98d) {
                    double fh = Math.max(this.fullH, 1.0d);
                    double sensorW2 = 2;
                    k = k2;
                    double zFull = Math.max(z2, Math.max(this.fullW / (c.getCx() * sensorW2), fh / (c.getCy() * sensorW2)));
                    z = Math.max((w / this.fullW) * zFull, w / (sensorW2 * c.getCx()));
                } else {
                    k = k2;
                    double d = 2;
                    z = Math.max(z2, Math.max(w / (c.getCx() * d), h / (d * c.getCy())));
                }
                double d2 = 2;
                double maxX = Math.max(0.0d, ((c.getCx() * z) - (w / d2)) - 5.0d);
                double maxY = Math.max(0.0d, ((c.getCy() * z) - (h / d2)) - 5.0d);
                double xo = 0.0d;
                if (Math.abs(kep[2]) > 1.0E-6d) {
                    double margin = -maxX;
                    xo = RangesKt.coerceIn(((kep[0] / kep[2]) - c.getCx()) * z, margin, maxX);
                    double yo2 = RangesKt.coerceIn(((kep[1] / kep[2]) - c.getCy()) * z, -maxY, maxY);
                    yo = yo2;
                } else {
                    yo = 0.0d;
                }
                this.zoom = z;
                this.xOffset = xo;
                this.yOffset = yo;
                this.videoScale = z;
                this.videoTx = ((w / d2) - xo) - (c.getCx() * z);
                this.videoTy = ((h / d2) - yo) - (c.getCy() * z);
                double[] videoTransform = {z, 0.0d, this.videoTx, 0.0d, z, this.videoTy, 0.0d, 0.0d, 1.0d};
                this.full = Companion.mul(videoTransform, calibTransform);
                this.calib = calibTransform;
                this.camNow = c;
                this.valid = true;
                return;
            }
        }
        this.valid = false;
    }

    public final boolean map(double x, double y, double z, float[] out) {
        double sx;
        double sy;
        Intrinsics.checkNotNullParameter(out, "out");
        if (this.wide) {
            double[] p = Companion.mulv(this.calib, x, y, z);
            if (p[2] < 1.0E-6d) {
                return false;
            }
            Companion.Cam c = this.camNow;
            double[] d = Companion.pinholeToFisheye((p[0] / p[2]) - c.getCx(), (p[1] / p[2]) - c.getCy(), c.getF());
            sx = ((c.getCx() + d[0]) * this.videoScale) + this.videoTx;
            double sx2 = c.getCy();
            sy = ((sx2 + d[1]) * this.videoScale) + this.videoTy;
        } else {
            double[] p2 = Companion.mulv(this.full, x, y, z);
            if (Math.abs(p2[2]) < 1.0E-6d) {
                return false;
            }
            sx = p2[0] / p2[2];
            double sx3 = p2[1];
            sy = sx3 / p2[2];
        }
        if (sx < -500.0d || sx > this.viewW + 500.0d || sy < -500.0d || sy > this.viewH + 500.0d) {
            return false;
        }
        out[0] = (float) sx;
        out[1] = (float) sy;
        return true;
    }

    public final float[] ribbon(float[] x, float[] y, float[] z, double yOff, double zOff, int maxIdx, boolean allowInvert) {
        float[] lx;
        Intrinsics.checkNotNullParameter(x, "x");
        Intrinsics.checkNotNullParameter(y, "y");
        Intrinsics.checkNotNullParameter(z, "z");
        int n = Math.min(maxIdx + 1, Math.min(x.length, Math.min(y.length, z.length)));
        if (n < 2) {
            return null;
        }
        float[] lx2 = new float[n];
        float[] ly = new float[n];
        float[] rx = new float[n];
        float[] ry = new float[n];
        float[] tmp = new float[2];
        int k = 0;
        float minY = Float.MAX_VALUE;
        int i = 0;
        while (i < n) {
            int k2 = k;
            int i2 = i;
            int n2 = n;
            float[] tmp2 = tmp;
            if (!map(x[i], y[i] - yOff, z[i] + zOff, tmp2)) {
                lx = lx2;
            } else {
                float lX = tmp2[0];
                float lY = tmp2[1];
                lx = lx2;
                if (map(x[i2], y[i2] + yOff, z[i2] + zOff, tmp2)) {
                    if (!allowInvert) {
                        if (lY <= minY) {
                            minY = lY;
                        }
                    }
                    lx[k2] = lX;
                    ly[k2] = lY;
                    rx[k2] = tmp2[0];
                    ry[k2] = tmp2[1];
                    k = k2 + 1;
                    i = i2 + 1;
                    tmp = tmp2;
                    n = n2;
                    lx2 = lx;
                }
            }
            k = k2;
            i = i2 + 1;
            tmp = tmp2;
            n = n2;
            lx2 = lx;
        }
        float[] lx3 = lx2;
        int n3 = k;
        if (n3 < 2) {
            return null;
        }
        float[] out = new float[n3 * 4];
        for (int i3 = 0; i3 < n3; i3++) {
            out[i3 * 2] = lx3[i3];
            out[(i3 * 2) + 1] = ly[i3];
        }
        for (int i4 = 0; i4 < n3; i4++) {
            int j = (n3 - 1) - i4;
            out[(n3 + i4) * 2] = rx[j];
            out[((n3 + i4) * 2) + 1] = ry[j];
        }
        return out;
    }
}
