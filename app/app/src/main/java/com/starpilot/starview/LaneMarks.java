package com.starpilot.starview;

import com.starpilot.starview.Projection;
import java.util.Arrays;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;

/* compiled from: LaneMarks.kt */
public final class LaneMarks {
    public static final int DASHED = 2;
    public static final int NONE = 1;
    public static final int SOLID = 3;
    public static final int UNKNOWN = 0;
    private final float[] dbl;
    private volatile String debug;
    private volatile long lastNs;
    private volatile Mark[] marks;
    private final int[] prof;
    private final int[] profU;
    private final int[] profV;
    private final float[][] score;
    private final int[] tmp;
    private final float[] yel;
    public static final Companion Companion = new Companion(null);
    private static final String[] NAMES = {"?", "none", "dash", "solid"};

    /* compiled from: LaneMarks.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public LaneMarks() {
        Mark[] markArr = new Mark[4];
        for (int i = 0; i < 4; i++) {
            markArr[i] = new Mark(0, false, false);
        }
        this.marks = markArr;
        this.debug = "waiting";
        float[][] fArr = new float[4][];
        for (int i2 = 0; i2 < 4; i2++) {
            fArr[i2] = new float[4];
        }
        this.score = fArr;
        this.yel = new float[4];
        this.dbl = new float[4];
        this.prof = new int[25];
        this.profU = new int[25];
        this.profV = new int[25];
        this.tmp = new int[25];
    }

    /* compiled from: LaneMarks.kt */
    public static final class Mark {
        private final boolean double;
        private final int kind;
        private final boolean yellow;

        public Mark(int kind, boolean yellow, boolean z) {
            this.kind = kind;
            this.yellow = yellow;
            this.double = z;
        }

        public final boolean getDouble() {
            return this.double;
        }

        public final int getKind() {
            return this.kind;
        }

        public final boolean getYellow() {
            return this.yellow;
        }
    }

    public final Mark[] getMarks() {
        return this.marks;
    }

    public final String getDebug() {
        return this.debug;
    }

    public final long getLastNs() {
        return this.lastNs;
    }

    public final boolean wants(long nowNs) {
        return nowNs - this.lastNs > 200000000;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r11v20 */
    /* JADX WARN: Type inference failed for: r11v21 */
    /* JADX WARN: Type inference failed for: r11v22 */
    /* JADX WARN: Type inference failed for: r29v3 */
    /* JADX WARN: Type inference failed for: r2v20 */
    /* JADX WARN: Type inference failed for: r2v21 */
    /* JADX WARN: Type inference failed for: r2v22 */
    /* JADX WARN: Type inference failed for: r3v10 */
    /* JADX WARN: Type inference failed for: r3v12 */
    /* JADX WARN: Type inference failed for: r3v24 */
    /* JADX WARN: Type inference failed for: r3v25 */
    /* JADX WARN: Type inference failed for: r3v33 */
    /* JADX WARN: Type inference failed for: r3v37 */
    /* JADX WARN: Type inference failed for: r3v45 */
    /* JADX WARN: Type inference failed for: r3v9 */
    /* JADX WARN: Type inference failed for: r47v0 */
    /* JADX WARN: Type inference failed for: r47v1 */
    /* JADX WARN: Type inference failed for: r47v2 */
    /* JADX WARN: Type inference failed for: r47v3 */
    public final void analyze(byte[] y, byte[] u, byte[] v, int yStride, int uvStride, int uvPix, int fw, int fh, float[] rpy, ModelSnapshot m, float leadX, long nowNs) {
        LaneMarks laneMarks;
        double[] dArr;
        StringBuilder sb;
        double[] dArr2;
        double[] dArr3;
        double[] dArr4;
        double d;
        float f;
        int i;
        int i2;
        int i3;
        int i4;
        ?? r3;
        double[] dArr5;
        double[] dArr6;
        double[] dArr7;
        ?? r32;
        LaneMarks laneMarks2;
        int i5;
        double[] dArr8;
        double[] dArr9;
        int i6;
        boolean z;
        LaneMarks laneMarks3 = this;
        int i7 = fh;
        Intrinsics.checkNotNullParameter(y, "y");
        Intrinsics.checkNotNullParameter(u, "u");
        Intrinsics.checkNotNullParameter(v, "v");
        Intrinsics.checkNotNullParameter(rpy, "rpy");
        laneMarks3.lastNs = nowNs;
        if (m != null && m.getLaneX().length >= 4) {
            Projection.Companion.Cam camFor = Projection.Companion.camFor(fw, i7, false);
            double[] dArr10 = {camFor.getF(), 0.0d, camFor.getCx(), 0.0d, camFor.getF(), camFor.getCy(), 0.0d, 0.0d, 1.0d};
            double[] mul = Projection.Companion.mul(dArr10, Projection.Companion.mul(Projection.Companion.getVIEW_FROM_DEVICE(), Projection.Companion.rotFromEuler(rpy[0], rpy[1], rpy[2])));
            double[] dArr11 = new double[2];
            double[] dArr12 = new double[2];
            StringBuilder sb2 = new StringBuilder();
            double min = leadX > 3.0f ? Math.min(40.0d, leadX - 3.0d) : 40.0d;
            int i8 = 0;
            while (i8 < 4) {
                float[] laneProb = m.getLaneProb();
                float f2 = i8 >= 0 && i8 < laneProb.length ? laneProb[i8] : 0.0f;
                float[] fArr = m.getLaneX()[i8];
                float[] fArr2 = m.getLaneY()[i8];
                float[] fArr3 = m.getLaneZ()[i8];
                int i9 = 0;
                int i10 = 0;
                int i11 = 0;
                int i12 = 0;
                int i13 = 0;
                if (f2 <= 0.12f || fArr.length <= 4) {
                    laneMarks = laneMarks3;
                    dArr = mul;
                    sb = sb2;
                    dArr2 = dArr11;
                    dArr3 = dArr12;
                    dArr4 = dArr10;
                    d = min;
                    f = f2;
                    i = i8;
                    i2 = 0;
                    i3 = 0;
                    i4 = 0;
                } else {
                    double d2 = 6.0d;
                    int i14 = 1;
                    dArr4 = dArr10;
                    ?? r11 = -1;
                    int i15 = 0;
                    while (true) {
                        if (d2 > min) {
                            laneMarks = laneMarks3;
                            dArr = mul;
                            sb = sb2;
                            dArr2 = dArr11;
                            dArr3 = dArr12;
                            r3 = r11;
                            d = min;
                            f = f2;
                            i = i8;
                            break;
                        }
                        d = min;
                        int i16 = i14;
                        while (true) {
                            if (i16 >= fArr.length - 1) {
                                f = f2;
                                break;
                            }
                            f = f2;
                            if (fArr[i16] >= d2) {
                                break;
                            }
                            i16++;
                            f2 = f;
                        }
                        if (fArr[i16] < d2) {
                            laneMarks = laneMarks3;
                            dArr = mul;
                            sb = sb2;
                            dArr2 = dArr11;
                            dArr3 = dArr12;
                            r3 = r11;
                            i = i8;
                            break;
                        }
                        double d3 = d2 - fArr[i16 - 1];
                        float[] fArr4 = fArr;
                        StringBuilder sb3 = sb2;
                        float max = Math.max(0.01f, fArr[i16] - fArr[i16 - 1]);
                        int i17 = i8;
                        ?? r47 = r11;
                        double coerceIn = RangesKt.coerceIn(d3 / max, 0.0d, 1.0d);
                        double d4 = fArr2[i16 - 1] + ((fArr2[i16] - fArr2[i16 - 1]) * coerceIn);
                        double d5 = fArr3[i16 - 1] + ((fArr3[i16] - fArr3[i16 - 1]) * coerceIn);
                        if (analyze$proj(mul, d2, d4, d5, dArr11) && analyze$proj(mul, d2, d4 + 0.5d, d5, dArr12)) {
                            double d6 = (dArr12[0] - dArr11[0]) * 2.0d;
                            double d7 = (dArr12[1] - dArr11[1]) * 2.0d;
                            if (Math.abs(d6) * 0.1d >= 1.5d) {
                                boolean z2 = true;
                                int i18 = 0;
                                while (true) {
                                    double d8 = d5;
                                    if (i18 >= 25) {
                                        laneMarks2 = this;
                                        dArr5 = mul;
                                        break;
                                    }
                                    double d9 = (i18 - 12) * 0.04d;
                                    dArr5 = mul;
                                    boolean z3 = z2;
                                    int i19 = (int) (dArr11[0] + (d9 * d6));
                                    int i20 = (int) (dArr11[1] + (d9 * d7));
                                    if (i19 < 2 || i20 < 2 || i19 >= fw - 2) {
                                        break;
                                    }
                                    if (i20 >= i7 - 2) {
                                        laneMarks2 = this;
                                        break;
                                    }
                                    this.prof[i18] = y[(i20 * yStride) + i19] & 255;
                                    int i21 = ((i20 >> 1) * uvStride) + ((i19 >> 1) * uvPix);
                                    this.profU[i18] = u[i21] & 255;
                                    this.profV[i18] = v[i21] & 255;
                                    i18++;
                                    i7 = fh;
                                    d5 = d8;
                                    mul = dArr5;
                                    z2 = z3;
                                }
                                laneMarks2 = this;
                                z2 = false;
                                if (z2) {
                                    i9++;
                                    int i22 = 0;
                                    int i23 = 0;
                                    while (i23 < 25) {
                                        if (Math.abs(i23 - 12) >= 8) {
                                            z = z2;
                                            laneMarks2.tmp[i22] = laneMarks2.prof[i23];
                                            i22++;
                                        } else {
                                            z = z2;
                                        }
                                        i23++;
                                        z2 = z;
                                    }
                                    Arrays.sort(laneMarks2.tmp, 0, i22);
                                    int i24 = laneMarks2.tmp[i22 / 2];
                                    int max2 = Math.max(18, (i24 * 12) / 100);
                                    int i25 = 0;
                                    int i26 = 5;
                                    int i27 = 0;
                                    int i28 = 0;
                                    while (true) {
                                        int i29 = i22;
                                        if (i26 > 19) {
                                            break;
                                        }
                                        if (laneMarks2.prof[i26] - i24 > max2) {
                                            int i30 = i26;
                                            while (true) {
                                                dArr8 = dArr11;
                                                dArr9 = dArr12;
                                                if (i30 + 1 > 19 || laneMarks2.prof[i30 + 1] - i24 <= max2) {
                                                    break;
                                                }
                                                i30++;
                                                dArr11 = dArr8;
                                                dArr12 = dArr9;
                                            }
                                            if ((i30 - i26) + 1 <= 6) {
                                                i25++;
                                                int i31 = i26;
                                                if (i31 <= i30) {
                                                    while (true) {
                                                        i27++;
                                                        i5 = i24;
                                                        i28 += (128 - laneMarks2.profU[i31]) + (laneMarks2.profV[i31] - 128);
                                                        if (i31 == i30) {
                                                            break;
                                                        }
                                                        i31++;
                                                        i24 = i5;
                                                    }
                                                } else {
                                                    i5 = i24;
                                                }
                                            } else {
                                                i5 = i24;
                                            }
                                            i6 = i30 + 1;
                                            Unit unit = Unit.INSTANCE;
                                        } else {
                                            i5 = i24;
                                            dArr8 = dArr11;
                                            dArr9 = dArr12;
                                            i6 = i26 + 1;
                                            Integer.valueOf(i26);
                                        }
                                        i26 = i6;
                                        i22 = i29;
                                        dArr11 = dArr8;
                                        dArr12 = dArr9;
                                        i24 = i5;
                                    }
                                    dArr6 = dArr11;
                                    dArr7 = dArr12;
                                    boolean z4 = i25 > 0;
                                    if (z4) {
                                        i10++;
                                        int i32 = 0;
                                        int i33 = 0;
                                        int i34 = 0;
                                        while (true) {
                                            int i35 = max2;
                                            if (i34 >= 25) {
                                                break;
                                            }
                                            int i36 = i26;
                                            if (Math.abs(i34 - 12) >= 8) {
                                                i32 += (128 - laneMarks2.profU[i34]) + (laneMarks2.profV[i34] - 128);
                                                i33++;
                                            }
                                            i34++;
                                            i26 = i36;
                                            max2 = i35;
                                        }
                                        if ((i28 / i27) - (i32 / Math.max(1, i33)) > 22) {
                                            i12++;
                                        }
                                        if (i25 >= 2) {
                                            i15++;
                                        }
                                    }
                                    ?? r2 = z4 ? 1 : 0;
                                    if (r47 >= 0 && r2 != r47) {
                                        i11++;
                                    }
                                    r32 = r2;
                                } else {
                                    dArr6 = dArr11;
                                    dArr7 = dArr12;
                                    r32 = r47 == true ? 1 : 0;
                                }
                            } else {
                                dArr5 = mul;
                                dArr6 = dArr11;
                                dArr7 = dArr12;
                                r32 = r47 == true ? 1 : 0;
                                laneMarks2 = this;
                            }
                        } else {
                            dArr5 = mul;
                            dArr6 = dArr11;
                            dArr7 = dArr12;
                            r32 = r47 == true ? 1 : 0;
                            laneMarks2 = this;
                        }
                        d2 += 0.5d;
                        i7 = fh;
                        laneMarks3 = laneMarks2;
                        r11 = r32;
                        i14 = i16;
                        i8 = i17;
                        mul = dArr5;
                        min = d;
                        f2 = f;
                        fArr = fArr4;
                        sb2 = sb3;
                        dArr11 = dArr6;
                        dArr12 = dArr7;
                    }
                    i2 = i9;
                    i3 = i10;
                    i13 = i15;
                    i4 = i11;
                }
                float[] fArr5 = laneMarks.score[i];
                if (i2 >= 8) {
                    float f3 = i3 / i2;
                    int i37 = f3 < 0.12f ? 1 : (f3 <= 0.75f && (i4 >= 2 || f3 <= 0.5f)) ? 2 : 3;
                    int i38 = 1;
                    while (true) {
                        float f4 = 1.0f;
                        if (i38 >= 4) {
                            break;
                        }
                        float f5 = fArr5[i38];
                        if (i38 != i37) {
                            f4 = 0.0f;
                        }
                        fArr5[i38] = f5 + ((f4 - fArr5[i38]) * 0.1f);
                        i38++;
                    }
                    if (i3 >= 3) {
                        float[] fArr6 = laneMarks.yel;
                        fArr6[i] = fArr6[i] + (((i12 * 2 > i3 ? 1.0f : 0.0f) - laneMarks.yel[i]) * 0.1f);
                        float[] fArr7 = laneMarks.dbl;
                        fArr7[i] = fArr7[i] + (((i13 * 5 > i3 * 2 ? 1.0f : 0.0f) - laneMarks.dbl[i]) * 0.1f);
                    }
                } else if (f <= 0.12f) {
                    for (int i39 = 1; i39 < 4; i39++) {
                        fArr5[i39] = fArr5[i39] * 0.95f;
                    }
                }
                int i40 = 0;
                float f6 = 0.45f;
                for (int i41 = 1; i41 < 4; i41++) {
                    if (fArr5[i41] > f6) {
                        f6 = fArr5[i41];
                        i40 = i41;
                    }
                }
                Mark mark = laneMarks.marks[i];
                if (mark.getKind() != 0 && i40 != mark.getKind() && fArr5[mark.getKind()] > 0.3f && f6 - fArr5[mark.getKind()] < 0.25f) {
                    i40 = mark.getKind();
                }
                boolean z5 = !mark.getYellow() ? laneMarks.yel[i] <= 0.65f : laneMarks.yel[i] <= 0.35f;
                boolean z6 = !mark.getDouble() ? laneMarks.dbl[i] <= 0.65f : laneMarks.dbl[i] <= 0.35f;
                Mark[] markArr = laneMarks.marks;
                Object[] copyOf = Arrays.copyOf(markArr, markArr.length);
                Intrinsics.checkNotNullExpressionValue(copyOf, "copyOf(...)");
                Mark[] markArr2 = (Mark[]) copyOf;
                markArr2[i] = new Mark(i40, z5, z6);
                laneMarks.marks = markArr2;
                Mark mark2 = laneMarks.marks[i];
                StringBuilder sb4 = sb;
                sb4.append(new String[]{"LL", "L", "R", "RR"}[i]).append(':').append(NAMES[mark2.getKind()]);
                if (mark2.getKind() >= 2) {
                    if (mark2.getDouble()) {
                        sb4.append(" dbl");
                    }
                    sb4.append(mark2.getYellow() ? " yel" : " wht");
                }
                sb4.append(" (").append(i2).append(")  ");
                i8 = i + 1;
                i7 = fh;
                laneMarks3 = laneMarks;
                sb2 = sb4;
                mul = dArr;
                dArr10 = dArr4;
                min = d;
                dArr11 = dArr2;
                dArr12 = dArr3;
            }
            LaneMarks laneMarks4 = laneMarks3;
            String sb5 = sb2.toString();
            Intrinsics.checkNotNullExpressionValue(sb5, "toString(...)");
            laneMarks4.debug = StringsKt.trim((CharSequence) sb5).toString();
        }
    }

    private static final boolean analyze$proj(double[] ct, double x, double yy, double z, double[] out) {
        double[] p = Projection.Companion.mulv(ct, x, yy, z);
        if (p[2] < 0.5d) {
            return false;
        }
        out[0] = p[0] / p[2];
        out[1] = p[1] / p[2];
        return true;
    }
}
