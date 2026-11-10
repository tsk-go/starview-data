package com.starpilot.starview;

import android.content.Context;
import android.util.Log;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import kotlin.io.ByteStreamsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.tensorflow.lite.Interpreter;

/* compiled from: CarOrient.kt */
public final class CarOrient {
    public static final String ASSET = "det/car_orient_96.lite";
    private static final float[] CENTRES;
    public static final int NB = 16;
    public static final int R = 96;
    private static final String TAG = "StarView/Orient";
    private final Context ctx;
    private volatile String failed;
    private final ByteBuffer inBuf;
    private Interpreter interp;
    private volatile double ms;
    private final float[][] out;
    private final float[] pad;
    private final float[] px;
    public static final Companion Companion = new Companion(null);
    private static final float[] MEAN = {0.485f, 0.456f, 0.406f};
    private static final float[] STD = {0.229f, 0.224f, 0.225f};

    /* compiled from: CarOrient.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final float[] getCENTRES() {
            return CarOrient.CENTRES;
        }
    }

    public CarOrient(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        this.ctx = ctx;
        this.failed = "";
        ByteBuffer order = ByteBuffer.allocateDirect(110592).order(ByteOrder.nativeOrder());
        Intrinsics.checkNotNullExpressionValue(order, "order(...)");
        this.inBuf = order;
        this.px = new float[27648];
        float[][] fArr = new float[1][];
        for (int i = 0; i < 1; i++) {
            fArr[i] = new float[16];
        }
        this.out = fArr;
        float[] fArr2 = new float[3];
        for (int i2 = 0; i2 < 3; i2++) {
            fArr2[i2] = (0.44705883f - MEAN[i2]) / STD[i2];
        }
        this.pad = fArr2;
    }

    static {
        float[] fArr = new float[16];
        for (int i = 0; i < 16; i++) {
            fArr[i] = (float) (((((i + 0.5d) * 2) * 3.141592653589793d) / 16) - 3.141592653589793d);
        }
        CENTRES = fArr;
    }

    public final String getFailed() {
        return this.failed;
    }

    public final double getMs() {
        return this.ms;
    }

    private final Interpreter ensure() {
        Interpreter it = this.interp;
        if (it != null) {
            return it;
        }
        if (this.failed.length() > 0) {
            return null;
        }
        try {
            InputStream open = this.ctx.getAssets().open("det/car_orient_96.lite");
            try {
                InputStream it2 = open;
                Intrinsics.checkNotNull(it2);
                byte[] bytes = ByteStreamsKt.readBytes(it2);
                CloseableKt.closeFinally(open, null);
                ByteBuffer buf = ByteBuffer.allocateDirect(bytes.length).order(ByteOrder.nativeOrder());
                buf.put(bytes);
                buf.rewind();
                Interpreter it3 = new Interpreter(buf, new Interpreter.Options().setNumThreads(2));
                this.interp = it3;
                return it3;
            } finally {
            }
        } catch (Throwable e) {
            Log.w("StarView/Orient", "load: " + e);
            String message = e.getMessage();
            if (message == null) {
                message = e.toString();
            }
            this.failed = message;
            return null;
        }
    }

    public final void close() {
        try {
            Interpreter interpreter = this.interp;
            if (interpreter != null) {
                interpreter.close();
            }
        } catch (Throwable th) {
        }
        this.interp = null;
    }

    public static /* synthetic */ float[] classify$default(CarOrient carOrient, byte[] bArr, byte[] bArr2, byte[] bArr3, int i, int i2, int i3, int i4, int i5, float f, float f2, float f3, float f4, float f5, int i6, Object obj) {
        float f6;
        if ((i6 & 4096) == 0) {
            f6 = f5;
        } else {
            f6 = 1.0f;
        }
        return carOrient.classify(bArr, bArr2, bArr3, i, i2, i3, i4, i5, f, f2, f3, f4, f6);
    }

    public final float[] classify(byte[] y, byte[] u, byte[] v, int yStride, int uvStride, int uvPixStride, int fw, int fh, float u0, float v0, float u1, float v1, float aspK) {
        float fy;
        int y1i;
        Interpreter it;
        boolean inY;
        float cx0;
        float cy0;
        byte[] y2 = y;
        int i = fw;
        Intrinsics.checkNotNullParameter(y2, "y");
        Intrinsics.checkNotNullParameter(u, "u");
        Intrinsics.checkNotNullParameter(v, "v");
        Interpreter ensure = ensure();
        if (ensure == null) {
            return null;
        }
        Interpreter it2 = ensure;
        long t0 = System.nanoTime();
        float pw = (u1 - u0) * 0.05f;
        float ph = 0.05f * (v1 - v0);
        float cx02 = Math.max(0.0f, u0 - pw);
        float cy02 = Math.max(0.0f, v0 - ph);
        float cx1 = Math.min(i - 1.0f, u1 + pw);
        float cy1 = Math.min(fh - 1.0f, v1 + ph);
        float ph2 = (cx1 - cx02) * aspK;
        float ch = cy1 - cy02;
        if (ph2 >= 8.0f && ch >= 8.0f) {
            int i2 = 96;
            float cy12 = 96;
            float s = cy12 / Math.max(ph2, ch);
            float ow = ph2 * s;
            float oh = ch * s;
            float ox = (cy12 - ow) / 2.0f;
            float oy = (cy12 - oh) / 2.0f;
            float sx = 1.0f / (s * aspK);
            float sy = 1.0f / s;
            int k = 0;
            int i3 = 0;
            while (true) {
                float ch2 = ch;
                if (i3 >= i2) {
                    break;
                }
                boolean inY2 = ((float) i3) >= oy && ((float) i3) < oy + oh;
                float fy2 = ((((i3 + 0.5f) - oy) * sy) + cy02) - 0.5f;
                long t02 = t0;
                float oy2 = oy;
                int y0i = RangesKt.coerceIn((int) Math.floor(fy2), 0, fh - 1);
                int y1i2 = Math.min(fh - 1, y0i + 1);
                int j = i3;
                float cx12 = cx1;
                float wy = RangesKt.coerceIn(fy2 - ((float) Math.floor(fy2)), 0.0f, 1.0f);
                int i4 = 0;
                while (i4 < 96) {
                    if (!inY2 || i4 < ox) {
                        fy = fy2;
                        y1i = y1i2;
                        it = it2;
                        inY = inY2;
                        cx0 = cx02;
                        cy0 = cy02;
                    } else if (i4 >= ox + ow) {
                        fy = fy2;
                        y1i = y1i2;
                        it = it2;
                        inY = inY2;
                        cx0 = cx02;
                        cy0 = cy02;
                    } else {
                        float fx = ((((i4 + 0.5f) - ox) * sx) + cx02) - 0.5f;
                        cx0 = cx02;
                        cy0 = cy02;
                        inY = inY2;
                        int x0i = RangesKt.coerceIn((int) Math.floor(fx), 0, i - 1);
                        int x1i = Math.min(i - 1, x0i + 1);
                        float wx = RangesKt.coerceIn(fx - ((float) Math.floor(fx)), 0.0f, 1.0f);
                        it = it2;
                        float f = 1;
                        float a = ((y2[(y0i * yStride) + x0i] & 255) * (f - wx)) + ((y2[(y0i * yStride) + x1i] & 255) * wx);
                        y1i = y1i2;
                        float b = ((y2[(y1i2 * yStride) + x0i] & 255) * (f - wx)) + ((y2[(y1i2 * yStride) + x1i] & 255) * wx);
                        float yy = (((f - wy) * a) + (b * wy)) - 16.0f;
                        fy = fy2;
                        int uvI = ((RangesKt.coerceIn((int) (fy2 + 0.5f), 0, fh - 1) >> 1) * uvStride) + ((RangesKt.coerceIn((int) (fx + 0.5f), 0, i - 1) >> 1) * uvPixStride);
                        float uu = (u[uvI] & 255) - 128.0f;
                        float vv = (v[uvI] & 255) - 128.0f;
                        float rr = RangesKt.coerceIn((yy * 1.164f) + (1.596f * vv), 0.0f, 255.0f);
                        float gg = RangesKt.coerceIn(((yy * 1.164f) - (0.391f * uu)) - (0.813f * vv), 0.0f, 255.0f);
                        float bb = RangesKt.coerceIn((1.164f * yy) + (2.018f * uu), 0.0f, 255.0f);
                        this.px[k] = ((rr / 255.0f) - MEAN[0]) / STD[0];
                        this.px[k + 1] = ((gg / 255.0f) - MEAN[1]) / STD[1];
                        this.px[k + 2] = ((bb / 255.0f) - MEAN[2]) / STD[2];
                        k += 3;
                        i4++;
                        y2 = y;
                        i = fw;
                        cx02 = cx0;
                        cy02 = cy0;
                        inY2 = inY;
                        it2 = it;
                        fy2 = fy;
                        y1i2 = y1i;
                    }
                    this.px[k] = this.pad[0];
                    this.px[k + 1] = this.pad[1];
                    this.px[k + 2] = this.pad[2];
                    k += 3;
                    i4++;
                    y2 = y;
                    i = fw;
                    cx02 = cx0;
                    cy02 = cy0;
                    inY2 = inY;
                    it2 = it;
                    fy2 = fy;
                    y1i2 = y1i;
                }
                i3 = j + 1;
                y2 = y;
                i = fw;
                ch = ch2;
                t0 = t02;
                oy = oy2;
                cx1 = cx12;
                i2 = 96;
            }
            Interpreter it3 = it2;
            long t03 = t0;
            this.inBuf.rewind();
            this.inBuf.asFloatBuffer().put(this.px);
            this.inBuf.rewind();
            try {
                try {
                    it3.run(this.inBuf, this.out);
                    double dt = (System.nanoTime() - t03) / 1000000.0d;
                    this.ms = (this.ms > 0.0d ? 1 : (this.ms == 0.0d ? 0 : -1)) == 0 ? dt : (this.ms * 0.9d) + (0.1d * dt);
                    float[] fArr = this.out[0];
                    float[] copyOf = Arrays.copyOf(fArr, fArr.length);
                    Intrinsics.checkNotNullExpressionValue(copyOf, "copyOf(...)");
                    return copyOf;
                } catch (Throwable th) {
                    e = th;
                    Log.w("StarView/Orient", "run: " + e);
                    String message = e.getMessage();
                    if (message == null) {
                        message = e.toString();
                    }
                    this.failed = message;
                    close();
                    return null;
                }
            } catch (Throwable th2) {
                e = th2;
            }
        }
        return null;
    }
}
