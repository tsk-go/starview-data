package com.starpilot.starview;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import kotlin.ranges.RangesKt;
import kotlin.text.Charsets;

/* compiled from: AmySynth.kt */
public final class AmySynth {
    private static final double A5 = 880.0d;
    private static final double A6 = 1760.0d;
    private static final double CS7 = 2217.46d;
    private static final double E5 = 659.26d;
    private static final double E6 = 1318.51d;
    private static final double E7 = 2637.0d;
    public static final AmySynth INSTANCE = new AmySynth();
    private static final double[][] PARTIALS;
    public static final int SR = 48000;
    private static final float[] TAB;
    private static final int TN = 4096;
    public static final int VOICE_SR = 24000;

    /* compiled from: AmySynth.kt */
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Style.values().length];
            try {
                iArr[Style.ASK.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[Style.SHORT.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[Style.DONE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                iArr[Style.STOPPED.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                iArr[Style.PING.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    private AmySynth() {
    }

    /* compiled from: AmySynth.kt */
    public enum Style {
        ASK,
        SHORT,
        DONE,
        STOPPED,
        PING;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<Style> getEntries() {
            return $ENTRIES;
        }
    }

    static {
        float[] fArr = new float[4097];
        for (int i = 0; i < 4097; i++) {
            fArr[i] = (float) Math.sin((i * 6.283185307179586d) / 4096);
        }
        TAB = fArr;
        PARTIALS = new double[][]{new double[]{1.0d, 1.0d, 1.0d}, new double[]{2.0d, 0.28d, 1.6d}, new double[]{3.0d, 0.1d, 2.4d}, new double[]{2.76d, 0.06d, 3.0d}, new double[]{5.4d, 0.025d, 4.5d}};
    }

    private final float sn(double cycles) {
        double x = cycles - Math.floor(cycles);
        double p = 4096 * x;
        int i = (int) p;
        return TAB[i] + ((TAB[i + 1] - TAB[i]) * ((float) (p - i)));
    }

    /* compiled from: AmySynth.kt */
    private static final class Stereo {
        private final float[] l;
        private final float[] r;

        public Stereo(int n) {
            this.l = new float[n];
            this.r = new float[n];
        }

        public final float[] getL() {
            return this.l;
        }

        public final float[] getR() {
            return this.r;
        }
    }

    private final void delayAdd(float[] dst, float[] src, double ms, float g) {
        int d = (int) ((48000 * ms) / 1000);
        int length = dst.length;
        for (int i = d; i < length; i++) {
            dst[i] = dst[i] + (src[i - d] * g);
        }
    }

    /* compiled from: AmySynth.kt */
    private static final /* data */ class Bell {
        private final float amp;
        private final double decay;
        private final double f0;
        private final double start;




        public final Bell copy(double f0, double start, float amp, double decay) {
            return new Bell(f0, start, amp, decay);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Bell)) {
                return false;
            }
            Bell bell = (Bell) other;
            return Double.compare(this.f0, bell.f0) == 0 && Double.compare(this.start, bell.start) == 0 && Float.compare(this.amp, bell.amp) == 0 && Double.compare(this.decay, bell.decay) == 0;
        }

        public int hashCode() {
            return (((((Double.hashCode(this.f0) * 31) + Double.hashCode(this.start)) * 31) + Float.hashCode(this.amp)) * 31) + Double.hashCode(this.decay);
        }

        public String toString() {
            return "Bell(f0=" + this.f0 + ", start=" + this.start + ", amp=" + this.amp + ", decay=" + this.decay + ")";
        }

        public Bell(double f0, double start, float amp, double decay) {
            this.f0 = f0;
            this.start = start;
            this.amp = amp;
            this.decay = decay;
        }

        public final float getAmp() {
            return this.amp;
        }

        public final double getDecay() {
            return this.decay;
        }

        public final double getF0() {
            return this.f0;
        }

        public final double getStart() {
            return this.start;
        }
    }

    private final Stereo bells(int n, List<Bell> notes) {
        float[] out = new float[n];
        Iterator<Bell> it = notes.iterator();
        while (true) {
            char c = 0;
            if (!it.hasNext()) {
                break;
            }
            Bell b = it.next();
            double d = 48000;
            int s0 = (int) (b.getStart() * d);
            double[][] dArr = PARTIALS;
            int length = dArr.length;
            int i = 0;
            while (i < length) {
                double[] p = dArr[i];
                double ratio = p[c];
                int s02 = s0;
                float a = (float) p[1];
                double[][] dArr2 = dArr;
                float k = (float) Math.exp(((-b.getDecay()) * p[2]) / d);
                float e = 1.0f;
                int i2 = length;
                float ka = (float) Math.exp(-0.003472222222222222d);
                float ea = 1.0f;
                int i3 = s02;
                while (i3 < n && e > 1.0E-5f) {
                    Iterator<Bell> it2 = it;
                    int i4 = i;
                    double tn = (i3 - s02) / d;
                    double d2 = d;
                    float k2 = k;
                    float ka2 = ka;
                    double det = 1 + (sn(tn * 4.5d) * 0.0015d);
                    out[i3] = out[i3] + (sn(b.getF0() * ratio * tn * det) * a * e * (1 - ea) * b.getAmp());
                    e *= k2;
                    ea *= ka2;
                    i3++;
                    k = k2;
                    it = it2;
                    i = i4;
                    p = p;
                    d = d2;
                    ka = ka2;
                }
                i++;
                s0 = s02;
                dArr = dArr2;
                it = it;
                length = i2;
                d = d;
                c = 0;
            }
        }
        Stereo st = new Stereo(n);
        System.arraycopy(out, 0, st.getL(), 0, n);
        System.arraycopy(out, 0, st.getR(), 0, n);
        delayAdd(st.getL(), out, 23.0d, 0.22f);
        delayAdd(st.getL(), out, 61.0d, 0.12f);
        delayAdd(st.getL(), out, 113.0d, 0.06f);
        delayAdd(st.getR(), out, 31.0d, 0.22f);
        delayAdd(st.getR(), out, 73.0d, 0.12f);
        delayAdd(st.getR(), out, 127.0d, 0.06f);
        float pk = 1.0E-9f;
        for (int i5 = 0; i5 < n; i5++) {
            pk = Math.max(pk, Math.max(Math.abs(st.getL()[i5]), Math.abs(st.getR()[i5])));
        }
        float g = 0.85f / pk;
        for (int i6 = 0; i6 < n; i6++) {
            float[] l = st.getL();
            l[i6] = l[i6] * g;
            float[] r = st.getR();
            r[i6] = r[i6] * g;
        }
        return st;
    }

    private final Stereo pad(int n, double fadeOut) {
        Stereo st = new Stereo(n);
        Random rng = new Random(7L);
        double[] chord = {220.0d, 277.18d, 329.63d, 493.88d, 659.26d};
        int length = chord.length;
        int i = 0;
        int i2 = 0;
        while (i2 < length) {
            int i3 = i2;
            double f = chord[i2];
            Pair[] pairArr = new Pair[2];
            pairArr[i] = TuplesKt.to(Integer.valueOf(i), Double.valueOf(-0.9d));
            pairArr[1] = TuplesKt.to(1, Double.valueOf(0.9d));
            Iterator it = CollectionsKt.listOf((Object[]) pairArr).iterator();
            while (it.hasNext()) {
                Pair pair = (Pair) it.next();
                int side = ((Number) pair.component1()).intValue();
                double det = ((Number) pair.component2()).doubleValue();
                double ph = rng.nextDouble();
                float[] dst = side == 0 ? st.getL() : st.getR();
                float g = i3 < 3 ? 0.9f : 0.45f;
                double ff = f + det;
                double tr = (i3 * 0.07d) + 0.35d;
                int j = 0;
                while (j < n) {
                    Random rng2 = rng;
                    int i4 = i3;
                    double t = j / 48000.0d;
                    float trem = 1 + (sn((tr * t) + ph) * 0.08f);
                    double tr2 = tr;
                    double tr3 = 2;
                    dst[j] = dst[j] + ((sn((ff * t) + ph) + (sn(tr3 * ff * t) * 0.18f)) * trem * g);
                    j++;
                    it = it;
                    rng = rng2;
                    chord = chord;
                    length = length;
                    i3 = i4;
                    f = f;
                    tr = tr2;
                }
                it = it;
                f = f;
            }
            i2++;
            i = 0;
        }
        double d = 48000;
        int fo = RangesKt.coerceAtMost((int) (fadeOut * d), n);
        for (int j2 = 0; j2 < n; j2++) {
            double env = Math.min(1.0d, (j2 / d) / 0.45d);
            double env2 = env * env;
            if (j2 >= n - fo) {
                double x = ((double) (n - j2)) / fo;
                env2 *= x * x;
            }
            float[] l = st.getL();
            l[j2] = l[j2] * ((float) env2);
            float[] r = st.getR();
            r[j2] = r[j2] * ((float) env2);
        }
        return st;
    }

    private final void pluck(float[] dst, double f, double start, float amp) {
        double d = 48000;
        int s0 = (int) (start * d);
        if (s0 >= dst.length) {
            return;
        }
        float k = (float) Math.exp(-6.666666666666667E-5d);
        float e = 1.0f;
        float ka = (float) Math.exp(-0.005208333333333333d);
        float ea = 1.0f;
        for (int i = s0; i < dst.length && e > 1.0E-5f; i++) {
            dst[i] = dst[i] + (sn(((i - s0) * f) / d) * e * (1 - ea) * amp);
            e *= k;
            ea *= ka;
        }
    }

    public final float[] stretch(float[] x, int sr, float speed) {
        float[] ws;
        Intrinsics.checkNotNullParameter(x, "x");
        if (Math.abs(speed - 1.0f) >= 0.01f) {
            if (!(x.length == 0)) {
                int n = (((int) (sr * 0.03d)) / 2) * 2;
                int hs = n / 2;
                double ha = hs * speed;
                int tol = (int) (sr * 0.01d);
                int outLen = MathKt.roundToInt(x.length / speed);
                float[] pad = new float[x.length + tol + (n * 2) + (tol * 2) + 4];
                System.arraycopy(x, 0, pad, tol, x.length);
                float[] w = new float[n];
                for (int i = 0; i < n; i++) {
                    w[i] = (float) (0.5d - (Math.cos((i * 6.283185307179586d) / n) * 0.5d));
                }
                float[] out = new float[outLen + n];
                float[] ws2 = new float[outLen + n];
                int prev = tol;
                int k = 0;
                while (true) {
                    int op = k * hs;
                    if (op >= outLen) {
                        ws = ws2;
                        break;
                    }
                    ws = ws2;
                    int nominal = MathKt.roundToInt(k * ha) + tol;
                    int pos = nominal;
                    if (k > 0) {
                        int tgt = prev + hs;
                        float best = Float.NEGATIVE_INFINITY;
                        int bp = nominal;
                        int pos2 = nominal - tol;
                        int p = Math.max(0, pos2);
                        int p2 = nominal + tol;
                        int hi = Math.min((pad.length - n) - 1, p2);
                        int p3 = p;
                        while (p3 <= hi) {
                            int nominal2 = nominal;
                            float c = stretch$corr(n, pad, tgt, p3, 2);
                            if (c > best) {
                                best = c;
                                bp = p3;
                            }
                            p3 += 2;
                            nominal = nominal2;
                        }
                        float best2 = Float.NEGATIVE_INFINITY;
                        int pos3 = bp;
                        int q = Math.max(0, bp - 1);
                        int min = Math.min((pad.length - n) - 1, bp + 1);
                        if (q <= min) {
                            while (true) {
                                float c2 = stretch$corr(n, pad, tgt, q, 1);
                                if (c2 > best2) {
                                    best2 = c2;
                                    pos3 = q;
                                }
                                if (q == min) {
                                    break;
                                }
                                q++;
                            }
                        }
                        pos = pos3;
                    }
                    if (pos + n > pad.length) {
                        break;
                    }
                    for (int j = 0; j < n; j++) {
                        int i2 = op + j;
                        out[i2] = out[i2] + (pad[pos + j] * w[j]);
                        int i3 = op + j;
                        ws[i3] = ws[i3] + w[j];
                    }
                    int j2 = pos;
                    k++;
                    prev = j2;
                    ws2 = ws;
                }
                float[] fArr = new float[outLen];
                for (int i4 = 0; i4 < outLen; i4++) {
                    fArr[i4] = ws[i4] > 0.001f ? out[i4] / ws[i4] : out[i4];
                }
                return fArr;
            }
        }
        return x;
    }

    private static final float stretch$corr(int n, float[] pad, int tgt, int p, int step) {
        float c = 0.0f;
        int j = 0;
        while (j < n) {
            c += pad[p + j] * pad[tgt + j];
            j += step;
        }
        return c;
    }

    public final float[] upsample2(float[] x) {
        Intrinsics.checkNotNullParameter(x, "x");
        int length = x.length * 2;
        float[] fArr = new float[length];
        for (int i = 0; i < length; i++) {
            int i2 = i / 2;
            float a = x[i2];
            if (i % 2 != 0) {
                a = (x[Math.min(i2 + 1, x.length - 1)] + a) * 0.5f;
            }
            fArr[i] = a;
        }
        return fArr;
    }

    public static /* synthetic */ float[] voice$default(AmySynth amySynth, List list, float f, float f2, int i, Object obj) {
        if ((i & 4) != 0) {
            f2 = 0.0f;
        }
        return amySynth.voice(list, f, f2);
    }

    public final float[] voice(List<float[]> clips, float speed, float pitch) {
        float[] v;
        Intrinsics.checkNotNullParameter(clips, "clips");
        int gap = 5280;
        int i = 0;
        for (float[] it : clips) {
            i += it.length;
        }
        int total = i + (Math.max(0, clips.size() - 1) * 5280);
        float[] v2 = new float[total];
        int o = 0;
        int i2 = 0;
        for (float[] c : clips) {
            int i3 = i2;
            i2++;
            if (i3 > 0) {
                o += 5280;
            }
            System.arraycopy(c, 0, v2, o, c.length);
            o += c.length;
        }
        float sp = RangesKt.coerceIn(speed, 0.5f, 2.0f);
        if (Math.abs(pitch) > 0.05f) {
            if (!(v2.length == 0)) {
                double r = Math.pow(2.0d, RangesKt.coerceIn(pitch, -12.0f, 12.0f) / 12.0d);
                int n = (int) (v2.length / r);
                float[] fArr = new float[n];
                int i4 = 0;
                while (i4 < n) {
                    int o2 = o;
                    double t = i4 * r;
                    int k = (int) t;
                    int gap2 = gap;
                    float[] v3 = v2;
                    float f = (float) (t - k);
                    fArr[i4] = k + 1 < v2.length ? v2[k] + ((v2[k + 1] - v2[k]) * f) : v2[Math.min(k, v2.length - 1)];
                    i4++;
                    o = o2;
                    gap = gap2;
                    v2 = v3;
                }
                v = fArr;
                sp = (float) (sp / r);
                return upsample2(stretch(v, 24000, sp));
            }
        }
        v = v2;
        return upsample2(stretch(v, 24000, sp));
    }

    public static /* synthetic */ short[] render$default(AmySynth amySynth, Style style, float[] fArr, float f, float f2, int i, Object obj) {
        if ((i & 4) != 0) {
            f = 1.0f;
        }
        if ((i & 8) != 0) {
            f2 = 1.0f;
        }
        return amySynth.render(style, fArr, f, f2);
    }

    public final short[] render(Style style, float[] voice, float chimeGain, float padGain) {
        double vStart;
        double tail;
        List notes;
        boolean usePad;
        double d;
        float[] r;
        double d2;
        Intrinsics.checkNotNullParameter(style, "style");
        Intrinsics.checkNotNullParameter(voice, "voice");
        switch (WhenMappings.$EnumSwitchMapping$0[style.ordinal()]) {
            case 1:
                List notes2 = CollectionsKt.listOf(new Bell[]{new Bell(880.0d, 0.0d, 0.55f, 2.2d), new Bell(1318.51d, 0.11d, 0.48f, 2.0d), new Bell(1760.0d, 0.22d, 0.42f, 1.7d), new Bell(554.37d, 0.22d, 0.18f, 1.4d)});
                vStart = 0.62d;
                tail = 1.6d;
                notes = notes2;
                usePad = true;
                break;
            case 2:
                List notes3 = CollectionsKt.listOf(new Bell[]{new Bell(1318.51d, 0.0d, 0.45f, 2.6d), new Bell(1760.0d, 0.09d, 0.38f, 2.4d)});
                vStart = 0.3d;
                tail = 0.5d;
                notes = notes3;
                usePad = false;
                break;
            case 3:
                List notes4 = CollectionsKt.listOf(new Bell(1760.0d, 0.0d, 0.3f, 3.0d));
                vStart = 0.2d;
                tail = 1.3d;
                notes = notes4;
                usePad = false;
                break;
            case 4:
                List notes5 = CollectionsKt.listOf(new Bell[]{new Bell(1318.51d, 0.0d, 0.45f, 2.4d), new Bell(880.0d, 0.13d, 0.45f, 2.4d), new Bell(659.26d, 0.26d, 0.4f, 2.0d)});
                vStart = 0.55d;
                tail = 0.6d;
                notes = notes5;
                usePad = false;
                break;
            case 5:
                List notes6 = CollectionsKt.listOf(new Bell[]{new Bell(1318.51d, 0.0d, 0.45f, 2.6d), new Bell(1760.0d, 0.09d, 0.38f, 2.4d)});
                vStart = 0.0d;
                tail = 0.9d;
                notes = notes6;
                usePad = false;
                break;
            default:
                throw new NoWhenBranchMatchedException();
        }
        int vl = voice.length;
        double d3 = 48000;
        int vs = (int) (vStart * d3);
        int n = vs + vl + ((int) (tail * d3));
        float[] l = new float[n];
        float[] r2 = new float[n];
        if (!usePad || padGain <= 0.01f) {
            d = d3;
        } else {
            Stereo p = pad(n, 1.4d);
            int d0 = Math.max(0, vs - 4800);
            int d1 = Math.min(n, vs + vl + 7200);
            int i = 0;
            while (i < n) {
                float f = 1.0f;
                if (i >= d0) {
                    d2 = d3;
                    if (i < d1) {
                        if (i < d0 + 5760) {
                            f = 1.0f - (((i - d0) * 0.4f) / 5760);
                        } else {
                            f = 0.6f;
                            if (i >= d1 - 5760) {
                                f = (((i - (d1 - 5760)) * 0.4f) / 5760) + 0.6f;
                            }
                        }
                    }
                } else {
                    d2 = d3;
                }
                float duck = f;
                l[i] = l[i] + (p.getL()[i] * 0.045f * padGain * duck);
                r2[i] = r2[i] + (p.getR()[i] * 0.045f * padGain * duck);
                i++;
                d3 = d2;
            }
            d = d3;
        }
        Stereo b = bells(n, notes);
        float bg = (style == Style.PING ? 0.45f : 0.62f) * chimeGain;
        for (int i2 = 0; i2 < n; i2++) {
            l[i2] = l[i2] + (b.getL()[i2] * bg);
            r2[i2] = r2[i2] + (b.getR()[i2] * bg);
        }
        for (int i3 = 0; i3 < vl; i3++) {
            int i4 = vs + i3;
            l[i4] = l[i4] + (voice[i3] * 0.95f);
            int i5 = vs + i3;
            r2[i5] = r2[i5] + (voice[i3] * 0.95f);
        }
        if (style == Style.ASK || style == Style.DONE) {
            double s0 = (style == Style.ASK ? 0.15d : 0.1d) + ((vs + vl) / d);
            float[] sp = new float[n];
            r = r2;
            pluck(sp, 1760.0d, s0, 1.0f);
            pluck(sp, 2217.46d, s0 + 0.3d, 0.8f);
            pluck(sp, 2637.0d, s0 + 0.6d, 0.6f);
            float g = (style == Style.ASK ? 0.05f : 0.09f) * chimeGain;
            for (int i6 = 0; i6 < n; i6++) {
                l[i6] = l[i6] + (sp[i6] * g);
                r[i6] = r[i6] + (sp[i6] * g * 0.8f);
            }
        } else {
            r = r2;
        }
        float[] wl = new float[n];
        float[] wr = new float[n];
        for (Pair pair : (Iterable<Pair>) (Iterable) CollectionsKt.listOf(new Pair[]{TuplesKt.to(Double.valueOf(29.0d), Float.valueOf(0.16f)), TuplesKt.to(Double.valueOf(47.0d), Float.valueOf(0.12f)), TuplesKt.to(Double.valueOf(83.0d), Float.valueOf(0.08f)), TuplesKt.to(Double.valueOf(131.0d), Float.valueOf(0.05f)), TuplesKt.to(Double.valueOf(197.0d), Float.valueOf(0.03f))})) {
            double ms = ((Number) pair.component1()).doubleValue();
            float g2 = ((Number) pair.component2()).floatValue();
            delayAdd(wl, l, ms, g2);
            delayAdd(wr, r, ms * 1.13d, g2);
        }
        int fl = Math.min(n, 14400);
        float pk = 1.0E-9f;
        for (int i7 = 0; i7 < n; i7++) {
            l[i7] = l[i7] + wl[i7];
            r[i7] = r[i7] + wr[i7];
            if (i7 >= n - fl) {
                float x = ((float) (n - i7)) / fl;
                l[i7] = l[i7] * x * x;
                r[i7] = r[i7] * x * x;
            }
            pk = Math.max(pk, Math.max(Math.abs(l[i7]), Math.abs(r[i7])));
        }
        float g3 = 0.89f / pk;
        int i8 = n * 2;
        short[] sArr = new short[i8];
        int i9 = 0;
        while (i9 < i8) {
            sArr[i9] = (short) RangesKt.coerceIn((int) ((i9 % 2 == 0 ? l[i9 / 2] : r[i9 / 2]) * g3 * 32767.0f), -32768, 32767);
            i9++;
            fl = fl;
            pk = pk;
        }
        return sArr;
    }

    public final float[] wavFloats(byte[] bytes) {
        Intrinsics.checkNotNullParameter(bytes, "bytes");
        int p = 12;
        while (true) {
            if (p + 8 <= bytes.length) {
                String id = new String(bytes, p, 4, Charsets.US_ASCII);
                int len = (bytes[p + 4] & 255) | ((bytes[p + 5] & 255) << 8) | ((bytes[p + 6] & 255) << 16) | ((bytes[p + 7] & 255) << 24);
                if (Intrinsics.areEqual(id, "data")) {
                    int m = Math.min(len, (bytes.length - p) - 8) / 2;
                    float[] fArr = new float[m];
                    for (int i = 0; i < m; i++) {
                        int k = p + 8 + (i * 2);
                        fArr[i] = ((short) ((bytes[k] & 255) | (bytes[k + 1] << 8))) / 32768.0f;
                    }
                    return fArr;
                }
                p += len + 8 + (len & 1);
            } else {
                return new float[0];
            }
        }
    }
}
