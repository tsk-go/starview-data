package com.starpilot.starview;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import com.starpilot.starview.Projection;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.io.ByteStreamsKt;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntProgression;
import kotlin.ranges.RangesKt;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import org.tensorflow.lite.Delegate;
import org.tensorflow.lite.Interpreter;
import org.tensorflow.lite.gpu.GpuDelegate;
import org.tensorflow.lite.gpu.GpuDelegateFactory;

/* compiled from: LayoutSeg.kt */
public final class LayoutSeg {
    public static final float CELL = 0.5f;
    public static final Companion Companion = new Companion(null);
    public static final int DARK = 55;
    public static final String FILE = "layout_segformer_b0.lite";
    public static final float FP = 200.0f;
    private static final int[] GROUP;
    public static final int GX = 80;
    public static final int GY = 80;
    public static final int HR = 144;
    private static final float[] MEAN;
    public static final int MH = 56;
    public static final int MW = 160;
    public static final int NC = 19;
    public static final int OH = 224;
    public static final int OW = 640;
    private static final float[] STD;
    private static final String TAG = "StarView/Layout";
    private volatile String accel;
    private final int[] blockCls;
    private final int[] blockRow;
    private volatile boolean busy;
    private final byte[] cls;
    private final Context ctx;
    private volatile boolean dark;
    private volatile boolean enabled;
    private float feedH;
    private GpuDelegate gpu;
    private volatile String gpuError;
    private volatile Grid grid;
    private final ByteBuffer inBuf;
    private Interpreter interp;
    private long lastFeedNs;
    private final float[] lg;
    private long loadedMtime;
    private final Object lock;
    private int[] lut;
    private String lutKey;
    private volatile double ms;
    private final ByteBuffer outBuf;
    private long pendingNs;
    private final float[] px;
    private volatile boolean running;
    private volatile String status;
    private Thread worker;

    /* compiled from: LayoutSeg.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final int[] getGROUP() {
            return LayoutSeg.GROUP;
        }
    }

    public LayoutSeg(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        this.ctx = ctx;
        this.enabled = true;
        this.status = "off";
        this.accel = "-";
        this.gpuError = "";
        this.px = new float[430080];
        this.lut = new int[0];
        this.lutKey = "";
        this.feedH = 1.22f;
        this.lock = new Object();
        ByteBuffer order = ByteBuffer.allocateDirect(1720320).order(ByteOrder.nativeOrder());
        Intrinsics.checkNotNullExpressionValue(order, "order(...)");
        this.inBuf = order;
        ByteBuffer order2 = ByteBuffer.allocateDirect(680960).order(ByteOrder.nativeOrder());
        Intrinsics.checkNotNullExpressionValue(order2, "order(...)");
        this.outBuf = order2;
        this.cls = new byte[8960];
        this.lg = new float[170240];
        this.blockRow = new int[160];
        this.blockCls = new int[160];
    }

    static {
        int[] iArr = new int[19];
        int i = 0;
        while (true) {
            int i2 = 3;
            if (i >= 19) {
                GROUP = iArr;
                MEAN = new float[]{0.485f, 0.456f, 0.406f};
                STD = new float[]{0.229f, 0.224f, 0.225f};
                return;
            }
            switch (i) {
                case 0:
                    i2 = 0;
                    break;
                case 1:
                    i2 = 1;
                    break;
                case 2:
                case 3:
                case 4:
                    break;
                case 5:
                case 6:
                case 7:
                default:
                    i2 = 255;
                    break;
                case 8:
                case 9:
                    i2 = 2;
                    break;
            }
            iArr[i] = i2;
            i++;
        }
    }

    /* compiled from: LayoutSeg.kt */
    public static final class Grid {
        private final long atNs;
        private final byte[] g;

        public Grid(byte[] g, long atNs) {
            Intrinsics.checkNotNullParameter(g, "g");
            this.g = g;
            this.atNs = atNs;
        }

        public final long getAtNs() {
            return this.atNs;
        }

        public final byte[] getG() {
            return this.g;
        }
    }

    public final Grid getGrid() {
        return this.grid;
    }

    public final boolean getEnabled() {
        return this.enabled;
    }

    public final void setEnabled(boolean z) {
        this.enabled = z;
    }

    public final String getStatus() {
        return this.status;
    }

    public final double getMs() {
        return this.ms;
    }

    public final String getAccel() {
        return this.accel;
    }

    public final String getGpuError() {
        return this.gpuError;
    }

    public final File modelFile() {
        return new File(this.ctx.getFilesDir(), "layout_segformer_b0.lite");
    }

    public final boolean getHasModel() {
        return modelFile().length() > 1000000;
    }

    public final String importModel(Uri uri) {
        Intrinsics.checkNotNullParameter(uri, "uri");
        try {
            File tmp = new File(this.ctx.getFilesDir(), "layout_segformer_b0.lite.part");
            InputStream openInputStream = this.ctx.getContentResolver().openInputStream(uri);
            Intrinsics.checkNotNull(openInputStream);
            FileInputStream fileInputStream = openInputStream;
            try {
                InputStream i = fileInputStream;
                fileInputStream = new FileOutputStream(tmp);
                try {
                    FileOutputStream o = fileInputStream;
                    ByteStreamsKt.copyTo$default(i, o, 0, 2, null);
                    CloseableKt.closeFinally(fileInputStream, null);
                    CloseableKt.closeFinally(fileInputStream, null);
                    fileInputStream = new FileInputStream(tmp);
                    try {
                        FileInputStream it = fileInputStream;
                        byte[] b = new byte[8];
                        it.read(b);
                        CloseableKt.closeFinally(fileInputStream, null);
                        if (tmp.length() >= 1000000 && Intrinsics.areEqual(new String(b, 4, 4, Charsets.UTF_8), "TFL3")) {
                            close();
                            tmp.renameTo(modelFile());
                            this.status = "model imported";
                            return null;
                        }
                        tmp.delete();
                        return "That file isn't the layout model (.lite)";
                    } finally {
                        try {
                            throw th;
                        } finally {
                        }
                    }
                } finally {
                }
            } finally {
            }
        } catch (Exception e) {
            return "Import failed: " + e.getMessage();
        }
    }

    public final boolean getDark() {
        return this.dark;
    }

    public final boolean wantsFrame(long nowNs) {
        return this.enabled && getHasModel() && !this.busy && !StringsKt.startsWith$default(this.status, "error", false, 2, (Object) null) && nowNs - this.lastFeedNs > 250000000;
    }

    public final void feed(byte[] y, byte[] u, byte[] v, int yStride, int uvStride, int uvPixStride, int fw, int fh, float[] rpy, float height, long frameNs) {
        int k;
        byte[] y2 = y;
        byte[] u2 = u;
        byte[] v2 = v;
        int i = fw;
        Intrinsics.checkNotNullParameter(y2, "y");
        Intrinsics.checkNotNullParameter(u2, "u");
        Intrinsics.checkNotNullParameter(v2, "v");
        Intrinsics.checkNotNullParameter(rpy, "rpy");
        if (this.busy) {
            return;
        }
        this.lastFeedNs = System.nanoTime();
        String format = String.format("%.3f", Arrays.copyOf(new Object[]{Float.valueOf(rpy[0])}, 1));
        Intrinsics.checkNotNullExpressionValue(format, "format(...)");
        String format2 = String.format("%.3f", Arrays.copyOf(new Object[]{Float.valueOf(rpy[1])}, 1));
        Intrinsics.checkNotNullExpressionValue(format2, "format(...)");
        String format3 = String.format("%.3f", Arrays.copyOf(new Object[]{Float.valueOf(rpy[2])}, 1));
        Intrinsics.checkNotNullExpressionValue(format3, "format(...)");
        String key = i + ":" + fh + ":" + format + ":" + format2 + ":" + format3;
        if (!Intrinsics.areEqual(key, this.lutKey)) {
            buildLut(i, fh, rpy);
            this.lutKey = key;
        }
        int[] l = this.lut;
        int k2 = 0;
        long lsum = 0;
        int ln = 0;
        IntProgression step = RangesKt.step(RangesKt.until(0, 143360), 97);
        int i2 = step.getFirst();
        int last = step.getLast();
        int step2 = step.getStep();
        if ((step2 > 0 && i2 <= last) || (step2 < 0 && last <= i2)) {
            while (true) {
                int p = l[i2];
                if (p >= 0) {
                    k = k2;
                    int k3 = y2[((p / i) * yStride) + (p % i)];
                    lsum += k3 & 255;
                    ln++;
                } else {
                    k = k2;
                }
                if (i2 == last) {
                    break;
                }
                i2 += step2;
                k2 = k;
            }
        } else {
            k = 0;
        }
        if (ln > 0 && lsum / ln < 55) {
            this.dark = true;
            this.grid = null;
            this.status = "paused: too dark";
            return;
        }
        this.dark = false;
        int i3 = 0;
        while (i3 < 143360) {
            int p2 = l[i3];
            if (p2 < 0) {
                this.px[k] = (0.45f - MEAN[0]) / STD[0];
                this.px[k + 1] = (0.45f - MEAN[1]) / STD[1];
                this.px[k + 2] = (0.45f - MEAN[2]) / STD[2];
                k += 3;
            } else {
                int sx = p2 % i;
                int sy = p2 / i;
                float yy = (y2[(sy * yStride) + sx] & 255) - 16.0f;
                int uvI = ((sy >> 1) * uvStride) + ((sx >> 1) * uvPixStride);
                float uu = (u2[uvI] & 255) - 128.0f;
                float vv = (v2[uvI] & 255) - 128.0f;
                float r = RangesKt.coerceIn((yy * 1.164f) + (1.596f * vv), 0.0f, 255.0f);
                float g = RangesKt.coerceIn(((yy * 1.164f) - (0.391f * uu)) - (0.813f * vv), 0.0f, 255.0f);
                float b = RangesKt.coerceIn((1.164f * yy) + (2.018f * uu), 0.0f, 255.0f);
                this.px[k] = ((r / 255.0f) - MEAN[0]) / STD[0];
                this.px[k + 1] = ((g / 255.0f) - MEAN[1]) / STD[1];
                this.px[k + 2] = ((b / 255.0f) - MEAN[2]) / STD[2];
                k += 3;
            }
            i3++;
            y2 = y;
            u2 = u;
            v2 = v;
            i = fw;
        }
        this.feedH = height;
        this.pendingNs = frameNs;
        this.busy = true;
        synchronized (this.lock) {
            this.lock.notifyAll();
            Unit unit = Unit.INSTANCE;
        }
    }

    private final void buildLut(int fw, int fh, float[] rpy) {
        char c;
        double[] rot;
        int i;
        char c2 = 1;
        Projection.Companion.Cam c3 = Projection.Companion.camFor(fw, fh, true);
        char c4 = 2;
        double[] rot2 = Projection.Companion.mul(Projection.Companion.getVIEW_FROM_DEVICE(), Projection.Companion.rotFromEuler(rpy[0], rpy[1], rpy[2]));
        int[] out = new int[143360];
        int j = 0;
        while (j < 224) {
            int i2 = 0;
            while (i2 < 640) {
                double d = 200.0f;
                double[] d2 = Projection.Companion.mulv(rot2, 1.0d, ((i2 + 0.5d) - 320.0d) / d, ((j + 0.5d) - 144) / d);
                if (d2[2] <= 1.0E-6d) {
                    out[(j * 640) + i2] = -1;
                    rot = rot2;
                    c = 2;
                } else {
                    c = 2;
                    double xn = d2[0] / d2[2];
                    double yn = d2[c2] / d2[2];
                    double rn = Math.hypot(xn, yn);
                    double s = rn < 1.0E-9d ? c3.getF() : (c3.getF() * Math.atan(rn)) / rn;
                    rot = rot2;
                    int sx = (int) (c3.getCx() + (xn * s));
                    int sy = (int) (c3.getCy() + (yn * s));
                    int i3 = (j * 640) + i2;
                    if (sx >= 0 && sx < fw) {
                        if (sy >= 0 && sy < fh) {
                            i = (sy * fw) + sx;
                            out[i3] = i;
                        }
                    }
                    i = -1;
                    out[i3] = i;
                }
                i2++;
                c4 = c;
                rot2 = rot;
                c2 = 1;
            }
            j++;
            c2 = 1;
        }
        this.lut = out;
    }

    private final int interpClass(float fy, float fx) {
        int y0 = RangesKt.coerceIn((int) fy, 0, 55);
        int x0 = RangesKt.coerceIn((int) fx, 0, 159);
        int y1 = Math.min(55, y0 + 1);
        int x1 = Math.min(159, x0 + 1);
        float wy = RangesKt.coerceIn(fy - y0, 0.0f, 1.0f);
        float wx = RangesKt.coerceIn(fx - x0, 0.0f, 1.0f);
        int a = ((y0 * 160) + x0) * 19;
        int b = ((y0 * 160) + x1) * 19;
        int c = ((y1 * 160) + x0) * 19;
        int d = ((y1 * 160) + x1) * 19;
        float f = 1;
        float w00 = (f - wy) * (f - wx);
        float w01 = (f - wy) * wx;
        float w10 = (f - wx) * wy;
        float w11 = wy * wx;
        int best = 0;
        float bv = -3.4028235E38f;
        int k = 0;
        for (int i = 19; k < i; i = 19) {
            float v = (this.lg[a + k] * w00) + (this.lg[b + k] * w01) + (this.lg[c + k] * w10) + (this.lg[d + k] * w11);
            if (v > bv) {
                best = k;
                bv = v;
            }
            k++;
        }
        return best;
    }

    public final void start() {
        if (this.running) {
            return;
        }
        this.running = true;
        Thread it = new Thread(new Runnable() { // from class: com.starpilot.starview.LayoutSeg$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                LayoutSeg.this.loop();
            }
        }, "starview-layout");
        it.setPriority(3);
        it.setDaemon(true);
        it.start();
        this.worker = it;
    }

    public final void stop() {
        this.enabled = false;
        this.grid = null;
        synchronized (this.lock) {
            this.lock.notifyAll();
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void shutdown() {
        this.enabled = false;
        this.running = false;
        this.grid = null;
        synchronized (this.lock) {
            this.lock.notifyAll();
            Unit unit = Unit.INSTANCE;
        }
    }

    private final void close() {
        try {
            Interpreter interpreter = this.interp;
            if (interpreter != null) {
                interpreter.close();
            }
        } catch (Throwable th) {
        }
        this.interp = null;
        try {
            GpuDelegate gpuDelegate = this.gpu;
            if (gpuDelegate != null) {
                gpuDelegate.close();
            }
        } catch (Throwable th2) {
        }
        this.gpu = null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v32, types: [org.tensorflow.lite.gpu.GpuDelegate$Options] */
    /* JADX WARN: Type inference failed for: r0v33, types: [org.tensorflow.lite.gpu.GpuDelegateFactory$Options] */
    /* JADX WARN: Type inference failed for: r0v34, types: [org.tensorflow.lite.gpu.GpuDelegateFactory$Options] */
    /* JADX WARN: Type inference failed for: r8v0 */
    /* JADX WARN: Type inference failed for: r8v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r8v10 */
    /* JADX WARN: Type inference failed for: r8v11 */
    /* JADX WARN: Type inference failed for: r8v12 */
    /* JADX WARN: Type inference failed for: r8v9 */
    private final Interpreter ensure() {
        String str;
        Interpreter interpreter = this.interp;
        if (interpreter != null) {
            if (modelFile().lastModified() == this.loadedMtime) {
                return interpreter;
            }
            close();
        }
        this.loadedMtime = modelFile().lastModified();
        if (!getHasModel()) {
            this.status = "no model imported";
            return null;
        }
        this.status = "loading…";
        byte[] readBytes = FilesKt.readBytes(modelFile());
        ByteBuffer order = ByteBuffer.allocateDirect(readBytes.length).order(ByteOrder.nativeOrder());
        order.put(readBytes);
        order.rewind();
        File file = new File(this.ctx.getCacheDir(), "gpu_layout");
        file.mkdirs();
        ArrayList arrayList = new ArrayList();
        char c = 2;
        ?? r8 = 1;
        int i = 0;
        Iterator it = CollectionsKt.listOf((Object[]) new Boolean[]{true, false}).iterator();
        while (it.hasNext()) {
            boolean booleanValue = ((Boolean) it.next()).booleanValue();
            GpuDelegate gpuDelegate = null;
            try {
                GpuDelegateFactory.Options inferencePreference = new GpuDelegate.Options().setPrecisionLossAllowed(r8).setQuantizedModelsAllowed(r8).setInferencePreference(r8);
                if (booleanValue) {
                    inferencePreference.setSerializationParams(file.getAbsolutePath(), "layout_b0_v3_" + modelFile().length());
                }
                gpuDelegate = new GpuDelegate(inferencePreference);
                Interpreter interpreter2 = new Interpreter(order, new Interpreter.Options().addDelegate((Delegate) gpuDelegate));
                this.gpu = gpuDelegate;
                this.accel = booleanValue ? "gpu" : "gpu (no cache)";
                this.interp = interpreter2;
                if (arrayList.isEmpty()) {
                    str = "";
                } else {
                    Object obj = arrayList.get(i);
                    Intrinsics.checkNotNullExpressionValue(obj, "get(...)");
                    str = "cache: " + StringsKt.take((String) obj, 100);
                }
                this.gpuError = str;
                return interpreter2;
            } catch (Throwable th) {
                Throwable th2 = th;
                Log.w("StarView/Layout", "GPU (cache=" + booleanValue + "): " + th2);
                ArrayList arrayList2 = arrayList;
                String message = th2.getMessage();
                if (message == null) {
                    message = th2.toString();
                }
                arrayList2.add(StringsKt.replace$default(message, "\n", " ", false, 4, (Object) null));
                if (gpuDelegate != null) {
                    try {
                        gpuDelegate.close();
                    } catch (Throwable th3) {
                    }
                }
                if (booleanValue) {
                    File[] listFiles = file.listFiles();
                    if (listFiles != null) {
                        int length = listFiles.length;
                        int i2 = i;
                        c = c;
                        while (i2 < length) {
                            File file2 = listFiles[i2];
                            String name = file2.getName();
                            Intrinsics.checkNotNullExpressionValue(name, "getName(...)");
                            Throwable th4 = th2;
                            byte[] bArr = readBytes;
                            File file3 = file;
                            if (StringsKt.startsWith$default(name, "layout_", false, 2, (Object) null)) {
                                file2.delete();
                            }
                            i2++;
                            c = 2;
                            i = 0;
                            readBytes = bArr;
                            file = file3;
                            th2 = th4;
                        }
                        boolean z = c == true ? 1 : 0;
                        readBytes = readBytes;
                        file = file;
                        r8 = 1;
                    } else {
                        boolean z2 = c == true ? 1 : 0;
                        readBytes = readBytes;
                        file = file;
                        r8 = 1;
                    }
                } else {
                    boolean z3 = c == true ? 1 : 0;
                    readBytes = readBytes;
                    file = file;
                    r8 = 1;
                }
            }
        }
        this.gpuError = StringsKt.take((String) CollectionsKt.last((List) arrayList), 160);
        Interpreter interpreter3 = new Interpreter(order, new Interpreter.Options().setNumThreads(3));
        this.accel = "cpu";
        this.interp = interpreter3;
        return interpreter3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void loop() {
        try {
            loopInner();
        } finally {
            close();
        }
    }

    private final void loopInner() {
        Interpreter it;
        long t0;
        FloatBuffer fb;
        long t02;
        FloatBuffer fb2;
        int grp;
        while (this.running) {
            if (!this.enabled) {
                if (this.interp != null) {
                    close();
                    this.status = "off";
                }
                this.busy = false;
            }
            synchronized (this.lock) {
                if (this.running && (!this.busy || !this.enabled)) {
                    this.lock.wait(500L);
                }
                Unit unit = Unit.INSTANCE;
            }
            if (this.running && this.busy && this.enabled) {
                try {
                    Interpreter it2 = ensure();
                    if (it2 == null) {
                        LayoutSeg $this$loopInner_u24lambda_u2414 = this;
                        $this$loopInner_u24lambda_u2414.busy = false;
                        Thread.sleep(2000L);
                    } else {
                        long t03 = System.nanoTime();
                        this.inBuf.rewind();
                        this.inBuf.asFloatBuffer().put(this.px);
                        this.inBuf.rewind();
                        this.outBuf.rewind();
                        long atNs = this.pendingNs;
                        float h = this.feedH;
                        this.busy = false;
                        it2.run(this.inBuf, this.outBuf);
                        ByteBuffer b = this.outBuf;
                        b.rewind();
                        FloatBuffer fb3 = b.asFloatBuffer();
                        fb3.get(this.lg);
                        for (int p = 0; p < 8960; p++) {
                            int best = 0;
                            float bv = this.lg[p * 19];
                            for (int c = 1; c < 19; c++) {
                                float x = this.lg[(p * 19) + c];
                                if (x > bv) {
                                    bv = x;
                                    best = c;
                                }
                            }
                            this.cls[p] = (byte) best;
                        }
                        for (int col = 0; col < 160; col++) {
                            int br = -1;
                            int bc = -1;
                            int row = 53;
                            while (true) {
                                if (row < 0) {
                                    break;
                                }
                                int c2 = this.cls[(row * 160) + col];
                                if (c2 == 0 || c2 == 1 || c2 == 9) {
                                    row--;
                                } else {
                                    br = row;
                                    bc = c2;
                                }
                            }
                            this.blockRow[col] = br;
                            this.blockCls[col] = bc;
                        }
                        byte[] g = new byte[6400];
                        for (int i = 0; i < 6400; i++) {
                            g[i] = -1;
                        }
                        int r = 0;
                        while (true) {
                            if (r >= 80) {
                                this.grid = new Grid(g, atNs);
                                double dt = (System.nanoTime() - t03) / 1000000.0d;
                                this.ms = (this.ms > 0.0d ? 1 : (this.ms == 0.0d ? 0 : -1)) == 0 ? dt : (this.ms * 0.8d) + (0.2d * dt);
                                this.status = "running";
                            } else {
                                float X = (r + 0.5f) * 0.5f;
                                float f = 144;
                                float vv = (f + ((h * 200.0f) / X)) / 4.0f;
                                if (vv >= 56.0f) {
                                    it = it2;
                                    t0 = t03;
                                    fb = fb3;
                                } else if (vv < 0.0f) {
                                    it = it2;
                                    t0 = t03;
                                    fb = fb3;
                                } else {
                                    int row2 = (int) vv;
                                    int c3 = 0;
                                    for (int i2 = 80; c3 < i2; i2 = 80) {
                                        float Y = ((c3 + 0.5f) * 0.5f) - 20.0f;
                                        Interpreter it3 = it2;
                                        float uu = (((Y * 200.0f) / X) + 320.0f) / 4.0f;
                                        if (uu < 0.0f) {
                                            t02 = t03;
                                            fb2 = fb3;
                                        } else if (uu >= 160.0f) {
                                            t02 = t03;
                                            fb2 = fb3;
                                        } else {
                                            fb2 = fb3;
                                            int col2 = (int) uu;
                                            if (row2 <= this.blockRow[col2]) {
                                                t02 = t03;
                                                switch (this.blockCls[col2]) {
                                                    case 2:
                                                    case 3:
                                                    case 4:
                                                        grp = 3;
                                                        break;
                                                    case 5:
                                                    case 6:
                                                    case 7:
                                                    default:
                                                        grp = -1;
                                                        break;
                                                    case 8:
                                                        grp = 2;
                                                        break;
                                                }
                                                if (grp >= 0) {
                                                    float baseV = ((this.blockRow[col2] + 1) * 4.0f) - f;
                                                    float xb = baseV > 0.0f ? (h * 200.0f) / baseV : 999.0f;
                                                    if (X >= xb && X <= xb + 2.5f) {
                                                        g[(r * 80) + c3] = (byte) grp;
                                                    }
                                                }
                                            } else if (X <= 30.0f) {
                                                t02 = t03;
                                                int gr = GROUP[interpClass(vv - 0.5f, uu - 0.5f)];
                                                if (gr == 255) {
                                                    gr = GROUP[this.cls[(row2 * 160) + col2]];
                                                }
                                                g[(r * 80) + c3] = (byte) gr;
                                            } else {
                                                t02 = t03;
                                            }
                                        }
                                        c3++;
                                        fb3 = fb2;
                                        it2 = it3;
                                        t03 = t02;
                                    }
                                    it = it2;
                                    t0 = t03;
                                    fb = fb3;
                                }
                                r++;
                                fb3 = fb;
                                it2 = it;
                                t03 = t0;
                            }
                        }
                    }
                } catch (Throwable e) {
                    Log.e("StarView/Layout", "layout: " + e);
                    this.status = "error: " + e.getMessage();
                    this.busy = false;
                    close();
                    try {
                        Thread.sleep(3000L);
                    } catch (InterruptedException e2) {
                    }
                    this.status = "retrying";
                }
            }
        }
    }
}
