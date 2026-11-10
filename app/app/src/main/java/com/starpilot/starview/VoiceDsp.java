package com.starpilot.starview;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.Charsets;

/* compiled from: AmySynth.kt */
public final class VoiceDsp {
    public static final VoiceDsp INSTANCE = new VoiceDsp();

    private VoiceDsp() {
    }

    public static /* synthetic */ float[] trim$default(VoiceDsp voiceDsp, float[] fArr, int i, double d, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            d = -45.0d;
        }
        return voiceDsp.trim(fArr, i, d);
    }

    public final float[] trim(float[] x, int sr, double db) {
        Intrinsics.checkNotNullParameter(x, "x");
        double thr = Math.pow(10.0d, db / 20);
        int w = Math.max(1, sr / 50);
        int a = 0;
        while (a < x.length && !trim$loud(x, w, thr, a)) {
            a += w / 2;
        }
        int b = x.length - w;
        while (b > a && !trim$loud(x, w, thr, Math.max(0, b))) {
            b -= w / 2;
        }
        int m = sr / 100;
        int s0 = Math.max(0, a - m);
        int s1 = Math.min(x.length, b + w + m);
        return s1 > s0 ? ArraysKt.copyOfRange(x, s0, s1) : x;
    }

    private static final boolean trim$loud(float[] $x, int w, double thr, int i0) {
        double s = 0.0d;
        int n = 0;
        for (int i = i0; i < Math.min($x.length, i0 + w); i++) {
            s += $x[i] * $x[i];
            n++;
        }
        return n > 0 && Math.sqrt(s / ((double) n)) > thr;
    }

    private final float[] biquad(float[] x, double b0, double b1, double b2, double a0, double a1, double a2) {
        float[] fArr = x;
        float[] y = new float[fArr.length];
        double x1 = 0.0d;
        double x2 = 0.0d;
        double y1 = 0.0d;
        double y2 = 0.0d;
        int i = 0;
        int length = fArr.length;
        while (i < length) {
            double xi = fArr[i];
            double yi = (((((b0 * xi) + (b1 * x1)) + (b2 * x2)) - (a1 * y1)) - (a2 * y2)) / a0;
            y[i] = (float) yi;
            x2 = x1;
            x1 = xi;
            y2 = y1;
            y1 = yi;
            i++;
            fArr = x;
        }
        return y;
    }

    public static /* synthetic */ float[] highpass$default(VoiceDsp voiceDsp, float[] fArr, int i, double d, double d2, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            d = 90.0d;
        }
        double d3 = d;
        if ((i2 & 8) != 0) {
            d2 = 0.7071d;
        }
        return voiceDsp.highpass(fArr, i, d3, d2);
    }

    public final float[] highpass(float[] x, int sr, double f, double q) {
        Intrinsics.checkNotNullParameter(x, "x");
        double w = (6.283185307179586d * f) / sr;
        double c = Math.cos(w);
        double d = 2;
        double al = Math.sin(w) / (d * q);
        double d2 = 1;
        return biquad(x, (d2 + c) / d, -(d2 + c), (d2 + c) / d, d2 + al, (-2) * c, d2 - al);
    }

    public static /* synthetic */ float[] peak$default(VoiceDsp voiceDsp, float[] fArr, int i, double d, double d2, double d3, int i2, Object obj) {
        return voiceDsp.peak(fArr, i, (i2 & 4) != 0 ? 3000.0d : d, (i2 & 8) != 0 ? 1.0d : d2, (i2 & 16) != 0 ? 2.0d : d3);
    }

    public final float[] peak(float[] x, int sr, double f, double q, double db) {
        Intrinsics.checkNotNullParameter(x, "x");
        double a = Math.pow(10.0d, db / 40);
        double w = (6.283185307179586d * f) / sr;
        double c = Math.cos(w);
        double al = Math.sin(w) / (2 * q);
        double d = 1;
        double d2 = -2;
        return biquad(x, d + (al * a), d2 * c, d - (al * a), (al / a) + d, d2 * c, d - (al / a));
    }

    public final float[] resample(float[] x, int from, int to) {
        int n;
        int i = from;
        int i2 = to;
        Intrinsics.checkNotNullParameter(x, "x");
        if (i == i2) {
            return x;
        }
        int n2 = (int) ((x.length * i2) / i);
        float[] y = new float[n2];
        double fc = Math.min(1.0d, i2 / i) * 0.95d;
        double half = 16 / fc;
        int i3 = 0;
        while (i3 < n2) {
            double t = (i3 * i) / i2;
            int k0 = (int) Math.ceil(t - half);
            int k1 = (int) Math.floor(t + half);
            double s = 0.0d;
            int k = Math.max(0, k0);
            int min = Math.min(x.length - 1, k1);
            if (k <= min) {
                while (true) {
                    n = n2;
                    double d = t - k;
                    double u = d * fc;
                    double sinc = Math.abs(u) < 1.0E-9d ? 1.0d : Math.sin(u * 3.141592653589793d) / (u * 3.141592653589793d);
                    int k12 = k1;
                    double t2 = t;
                    s += x[k] * fc * sinc * ((Math.cos((3.141592653589793d * d) / half) * 0.5d) + 0.5d);
                    if (k != min) {
                        k++;
                        n2 = n;
                        k1 = k12;
                        t = t2;
                    }
                }
            } else {
                n = n2;
            }
            y[i3] = (float) s;
            i3++;
            i = from;
            i2 = to;
            n2 = n;
        }
        return y;
    }

    public final float[] clean(float[] x, int sr) {
        Intrinsics.checkNotNullParameter(x, "x");
        return resample(peak$default(this, highpass$default(this, trim$default(this, x, sr, 0.0d, 4, null), sr, 0.0d, 0.0d, 12, null), sr, 0.0d, 0.0d, 0.0d, 28, null), sr, 24000);
    }

    public final byte[] wav16(float[] x, int sr) {
        Intrinsics.checkNotNullParameter(x, "x");
        ByteBuffer b = ByteBuffer.allocate((x.length * 2) + 44).order(ByteOrder.LITTLE_ENDIAN);
        byte[] bytes = "RIFF".getBytes(Charsets.UTF_8);
        Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
        ByteBuffer putInt = b.put(bytes).putInt((x.length * 2) + 36);
        byte[] bytes2 = "WAVEfmt ".getBytes(Charsets.UTF_8);
        Intrinsics.checkNotNullExpressionValue(bytes2, "getBytes(...)");
        putInt.put(bytes2).putInt(16).putShort((short) 1).putShort((short) 1).putInt(sr).putInt(sr * 2).putShort((short) 2).putShort((short) 16);
        byte[] bytes3 = "data".getBytes(Charsets.UTF_8);
        Intrinsics.checkNotNullExpressionValue(bytes3, "getBytes(...)");
        b.put(bytes3).putInt(x.length * 2);
        for (float v : x) {
            b.putShort((short) RangesKt.coerceIn((int) (32767.0f * v), -32768, 32767));
        }
        byte[] array = b.array();
        Intrinsics.checkNotNullExpressionValue(array, "array(...)");
        return array;
    }
}
