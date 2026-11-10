package com.starpilot.starview;

import android.media.AudioRecord;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.ArrayDeque;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;

/* compiled from: IdeasSense.kt */
public final class SirenEar {
    private static double bandAvg;
    private static int loudFrames;
    private static Thread thread;
    private static volatile boolean want;
    private static volatile boolean wantHorn;
    public static final SirenEar INSTANCE = new SirenEar();
    private static volatile String status = "off";

    private SirenEar() {
    }

    public final boolean getWantHorn() {
        return wantHorn;
    }

    public final void setWantHorn(boolean z) {
        wantHorn = z;
    }

    public final String getStatus() {
        return status;
    }

    public final void want(boolean on) {
        want = on || wantHorn;
        if (on && thread == null) {
            Thread $this$want_u24lambda_u241 = new Thread(new Runnable() { // from class: com.starpilot.starview.SirenEar$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    SirenEar.want$lambda$0();
                }
            }, "siren-ear");
            $this$want_u24lambda_u241.setDaemon(true);
            $this$want_u24lambda_u241.setPriority(1);
            $this$want_u24lambda_u241.start();
            thread = $this$want_u24lambda_u241;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void want$lambda$0() {
        INSTANCE.run();
    }

    /* recovered by hand from the bytecode (jadx lost its try/finally) */
    private final void run() {
        int min = AudioRecord.getMinBufferSize(8000, 16, 2);
        AudioRecord rec;
        try {
            rec = new AudioRecord(1, 8000, 16, 2, Math.max(min, 512 * 4));
        } catch (Exception e) {
            status = "no microphone: " + e.getMessage();
            thread = null;
            return;
        }
        if (rec.getState() != 1) {
            status = "microphone not allowed";
            thread = null;
            rec.release();
            return;
        }
        short[] buf = new short[512];
        double[] re = new double[512];
        double[] im = new double[512];
        ArrayDeque peaks = new ArrayDeque();
        try {
            rec.startRecording();
            status = "listening";
            while (want) {
                int got = 0;
                while (got < 512) {
                    int k = rec.read(buf, got, 512 - got);
                    if (k <= 0) {
                        break;
                    }
                    got += k;
                }
                for (int i = 0; i < 512; i++) {
                    re[i] = buf[i] * (0.5d - (Math.cos((i * 6.283185307179586d) / (512 - 1)) * 0.5d));
                    im[i] = 0.0d;
                }
                fft(re, im);
                int lo = (512 * 500) / 8000;
                int hi = (512 * 1800) / 8000;
                double best = 0.0d;
                int bi = 0;
                double[] mags = new double[(hi - lo) + 1];
                for (int k2 = lo; k2 <= hi; k2++) {
                    double m = (re[k2] * re[k2]) + (im[k2] * im[k2]);
                    mags[k2 - lo] = m;
                    if (m > best) {
                        best = m;
                        bi = k2;
                    }
                }
                List<Double> sorted = ArraysKt.sorted(mags);
                double med = sorted.get(mags.length / 2).doubleValue();
                double d = (best <= 40 * med || best <= 1.0E8d) ? Double.NaN : (bi * 8000) / 512;
                peaks.addLast(Double.valueOf(d));
                if (wantHorn) {
                    double e6 = 0.0d;
                    int k3 = (512 * 300) / 8000;
                    int top = (512 * 700) / 8000;
                    for (; k3 <= top; k3++) {
                        e6 += (re[k3] * re[k3]) + (im[k3] * im[k3]);
                    }
                    if (bandAvg == 0.0d) {
                        bandAvg = e6;
                    }
                    if (e6 <= 12 * bandAvg || e6 <= 5.0E9d) {
                        int i3 = loudFrames;
                        if (3 <= i3 && i3 < 31) {
                            Ideas.INSTANCE.getMore().setHornAtMs(System.currentTimeMillis());
                        }
                        loudFrames = 0;
                        bandAvg += (e6 - bandAvg) * 0.05d;
                    } else {
                        loudFrames++;
                    }
                }
                while (peaks.size() > 32) {
                    peaks.removeFirst();
                }
                if (peaks.size() == 32) {
                    Collection destination$iv$iv = new ArrayList();
                    for (Object element$iv$iv : peaks) {
                        if (!Double.isNaN(((Number) element$iv$iv).doubleValue())) {
                            destination$iv$iv.add(element$iv$iv);
                        }
                    }
                    List tones = (List) destination$iv$iv;
                    if (tones.size() >= 24) {
                        boolean smooth = true;
                        double prev = Double.NaN;
                        Iterator it2 = peaks.iterator();
                        while (it2.hasNext()) {
                            double f = ((Number) it2.next()).doubleValue();
                            if (!Double.isNaN(f) && !Double.isNaN(prev) && Math.abs(f - prev) > 160.0d) {
                                smooth = false;
                            }
                            if (!Double.isNaN(f)) {
                                prev = f;
                            }
                        }
                        if (smooth && CollectionsKt.maxOrThrow((Iterable<Double>) tones) - CollectionsKt.minOrThrow((Iterable<Double>) tones) > 220.0d) {
                            Ideas.INSTANCE.getSense().setSirenUntil(System.currentTimeMillis() + 6000);
                        }
                    }
                }
            }
        } catch (Exception e) {
            status = "error: " + e.getMessage();
        } finally {
            try {
                rec.stop();
            } catch (Exception e2) {
            }
            rec.release();
            thread = null;
            status = "off";
        }
    }

    private final void fft(double[] re, double[] im) {
        int n = re.length;
        int j = 0;
        for (int i = 1; i < n; i++) {
            int bit = n >> 1;
            while (true) {
                int i2 = j & bit;
                j ^= bit;
                if (i2 == 0) {
                    break;
                } else {
                    bit >>= 1;
                }
            }
            if (i < j) {
                double tr = re[i];
                re[i] = re[j];
                re[j] = tr;
                double ti = im[i];
                im[i] = im[j];
                im[j] = ti;
            }
        }
        for (int len = 2; len <= n; len <<= 1) {
            double ang = (-6.283185307179586d) / len;
            double wr = Math.cos(ang);
            double wi = Math.sin(ang);
            int i3 = 0;
            while (i3 < n) {
                double cr = 1.0d;
                double ci = 0.0d;
                int n2 = n;
                int n3 = len / 2;
                for (int k = 0; k < n3; k++) {
                    double ur = re[i3 + k];
                    double ui = im[i3 + k];
                    double vr = (re[(i3 + k) + (len / 2)] * cr) - (im[(i3 + k) + (len / 2)] * ci);
                    double vi = (re[i3 + k + (len / 2)] * ci) + (im[i3 + k + (len / 2)] * cr);
                    re[i3 + k] = ur + vr;
                    im[i3 + k] = ui + vi;
                    re[i3 + k + (len / 2)] = ur - vr;
                    im[i3 + k + (len / 2)] = ui - vi;
                    double nr = (cr * wr) - (ci * wi);
                    ci = (cr * wi) + (ci * wr);
                    cr = nr;
                }
                i3 += len;
                n = n2;
            }
        }
    }
}
