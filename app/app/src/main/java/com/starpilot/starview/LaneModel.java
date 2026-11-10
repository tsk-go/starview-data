package com.starpilot.starview;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import java.io.File;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Locale;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.collections.ArraysKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.StringsKt;
import org.tensorflow.lite.Delegate;
import org.tensorflow.lite.Interpreter;
import org.tensorflow.lite.gpu.GpuDelegate;
import org.tensorflow.lite.gpu.GpuDelegateFactory;

/* compiled from: LaneModel.kt */
public final class LaneModel {
    public static final long EVERY_MS = 2000;
    public static final String FILE = "lane_count.lite";
    public static final int FULL_H = 500;
    public static final int H = 400;
    public static final float SURE = 0.6f;
    private static final String TAG = "StarView/LaneModel";
    public static final int W = 800;
    private volatile String accel;
    private volatile Answer answer;
    private volatile boolean busy;
    private final Context ctx;
    private volatile boolean enabled;
    private GpuDelegate gpu;

    /* renamed from: input$delegate, reason: from kotlin metadata */
    private final Lazy input$delegate;
    private Interpreter interp;
    private long lastMs;
    private long loadedMtime;
    private final float[][] out;
    private final int[] px;
    private volatile String status;
    public static final Companion Companion = new Companion(null);
    private static final float[] MEAN = {0.485f, 0.456f, 0.406f};
    private static final float[] STD = {0.229f, 0.224f, 0.225f};

    /* compiled from: LaneModel.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public LaneModel(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        this.ctx = ctx;
        this.status = "off";
        this.accel = "-";
        this.enabled = true;
        this.input$delegate = LazyKt.lazy(new Function0() { // from class: com.starpilot.starview.LaneModel$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                ByteBuffer input_delegate$lambda$0;
                input_delegate$lambda$0 = LaneModel.input_delegate$lambda$0();
                return input_delegate$lambda$0;
            }
        });
        float[][] fArr = new float[1][];
        for (int i = 0; i < 1; i++) {
            fArr[i] = new float[8];
        }
        this.out = fArr;
        this.px = new int[400000];
    }

    /* compiled from: LaneModel.kt */
    public static final class Answer {
        private final long atMs;
        private final double ms;
        private final float[] pL;
        private final float[] pR;

        public Answer(float[] pL, float[] pR, long atMs, double ms) {
            Intrinsics.checkNotNullParameter(pL, "pL");
            Intrinsics.checkNotNullParameter(pR, "pR");
            this.pL = pL;
            this.pR = pR;
            this.atMs = atMs;
            this.ms = ms;
        }

        public final long getAtMs() {
            return this.atMs;
        }

        public final double getMs() {
            return this.ms;
        }

        public final float[] getPL() {
            return this.pL;
        }

        public final float[] getPR() {
            return this.pR;
        }

        public final int getLeft() {
            Object maxElem$iv;
            Iterable $this$maxByOrNull$iv = ArraysKt.getIndices(this.pL);
            Iterator iterator$iv = $this$maxByOrNull$iv.iterator();
            if (iterator$iv.hasNext()) {
                maxElem$iv = iterator$iv.next();
                if (iterator$iv.hasNext()) {
                    int it = ((Number) maxElem$iv).intValue();
                    float maxValue$iv = this.pL[it];
                    do {
                        Object e$iv = iterator$iv.next();
                        int it2 = ((Number) e$iv).intValue();
                        float v$iv = this.pL[it2];
                        if (Float.compare(maxValue$iv, v$iv) < 0) {
                            maxElem$iv = e$iv;
                            maxValue$iv = v$iv;
                        }
                    } while (iterator$iv.hasNext());
                }
            } else {
                maxElem$iv = null;
            }
            Integer num = (Integer) maxElem$iv;
            if (num != null) {
                return num.intValue();
            }
            return 0;
        }

        public final int getRight() {
            Object maxElem$iv;
            Iterable $this$maxByOrNull$iv = ArraysKt.getIndices(this.pR);
            Iterator iterator$iv = $this$maxByOrNull$iv.iterator();
            if (iterator$iv.hasNext()) {
                maxElem$iv = iterator$iv.next();
                if (iterator$iv.hasNext()) {
                    int it = ((Number) maxElem$iv).intValue();
                    float maxValue$iv = this.pR[it];
                    do {
                        Object e$iv = iterator$iv.next();
                        int it2 = ((Number) e$iv).intValue();
                        float v$iv = this.pR[it2];
                        if (Float.compare(maxValue$iv, v$iv) < 0) {
                            maxElem$iv = e$iv;
                            maxValue$iv = v$iv;
                        }
                    } while (iterator$iv.hasNext());
                }
            } else {
                maxElem$iv = null;
            }
            Integer num = (Integer) maxElem$iv;
            if (num != null) {
                return num.intValue();
            }
            return 0;
        }

        public final float getQL() {
            return this.pL[getLeft()];
        }

        public final float getQR() {
            return this.pR[getRight()];
        }

        public final String text() {
            float f = 100;
            return (getLeft() == 3 ? "3+" : String.valueOf(getLeft())) + " left · " + (getRight() != 3 ? String.valueOf(getRight()) : "3+") + " right (" + ((int) (getQL() * f)) + "% / " + ((int) (getQR() * f)) + "%)";
        }

        public final String code() {
            StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
            String format = String.format(Locale.US, "%d,%d,%.2f,%.2f", Arrays.copyOf(new Object[]{Integer.valueOf(getLeft()), Integer.valueOf(getRight()), Float.valueOf(getQL()), Float.valueOf(getQR())}, 4));
            Intrinsics.checkNotNullExpressionValue(format, "format(...)");
            return format;
        }
    }

    public final Answer getAnswer() {
        return this.answer;
    }

    public final String getStatus() {
        return this.status;
    }

    public final String getAccel() {
        return this.accel;
    }

    public final boolean getEnabled() {
        return this.enabled;
    }

    public final void setEnabled(boolean z) {
        this.enabled = z;
    }

    public final File modelFile() {
        return new File(this.ctx.getFilesDir(), "lane_count.lite");
    }

    public final boolean getHasModel() {
        return modelFile().length() > 1000000;
    }

    public final String importModel(Uri uri) {
        Intrinsics.checkNotNullParameter(uri, "uri");
        return LaneModelKt.importLaneModel(this.ctx, uri);
    }

    private final ByteBuffer getInput() {
        Object value = this.input$delegate.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
        return (ByteBuffer) value;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ByteBuffer input_delegate$lambda$0() {
        return ByteBuffer.allocateDirect(3840000).order(ByteOrder.nativeOrder());
    }

    public static /* synthetic */ boolean wants$default(LaneModel laneModel, long j, int i, Object obj) {
        if ((i & 1) != 0) {
            j = System.currentTimeMillis();
        }
        return laneModel.wants(j);
    }

    public final boolean wants(long nowMs) {
        return this.enabled && getHasModel() && !this.busy && nowMs - this.lastMs >= 2000 && !StringsKt.startsWith(this.status, "error", false);
    }

    public final void feed(final Bitmap bmp) {
        Intrinsics.checkNotNullParameter(bmp, "bmp");
        if (this.busy) {
            bmp.recycle();
            return;
        }
        this.busy = true;
        this.lastMs = System.currentTimeMillis();
        Thread $this$feed_u24lambda_u242 = new Thread(new Runnable() { // from class: com.starpilot.starview.LaneModel$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                LaneModel.feed$lambda$1(LaneModel.this, bmp);
            }
        }, "starview-lanemodel");
        $this$feed_u24lambda_u242.setPriority(3);
        $this$feed_u24lambda_u242.setDaemon(true);
        $this$feed_u24lambda_u242.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void feed$lambda$1(LaneModel this$0, Bitmap $bmp) {
        try {
            this$0.run($bmp);
        } catch (Throwable e) {
            Log.w("StarView/LaneModel", "run: " + e);
            String m = e.getMessage();
            this$0.status = "error: " + (m != null ? StringsKt.take(m, 80) : null);
        } finally {
            $bmp.recycle();
            this$0.busy = false;
        }
    }

    private final void run(Bitmap bmp) {
        Interpreter it = ensure();
        if (it == null) {
            return;
        }
        long t0 = System.nanoTime();
        Bitmap s = Bitmap.createScaledBitmap(bmp, 800, 500, true);
        Intrinsics.checkNotNullExpressionValue(s, "createScaledBitmap(...)");
        s.getPixels(this.px, 0, 800, 0, 0, 800, 500);
        if (!Intrinsics.areEqual(s, bmp)) {
            s.recycle();
        }
        getInput().rewind();
        FloatBuffer fb = getInput().asFloatBuffer();
        for (int j = 100; j < 500; j++) {
            for (int i = 0; i < 800; i++) {
                int c = this.px[(j * 800) + i];
                fb.put(((((c >> 16) & 255) / 255.0f) - MEAN[0]) / STD[0]);
                fb.put(((((c >> 8) & 255) / 255.0f) - MEAN[1]) / STD[1]);
                fb.put((((c & 255) / 255.0f) - MEAN[2]) / STD[2]);
            }
        }
        getInput().rewind();
        it.run(getInput(), this.out);
        float[] o = this.out[0];
        this.answer = new Answer(ArraysKt.copyOfRange(o, 0, 4), ArraysKt.copyOfRange(o, 4, 8), System.currentTimeMillis(), (System.nanoTime() - t0) / 1000000.0d);
        String str = this.accel;
        Answer answer = this.answer;
        Intrinsics.checkNotNull(answer);
        String format = String.format("%.0f", Arrays.copyOf(new Object[]{Double.valueOf(answer.getMs())}, 1));
        Intrinsics.checkNotNullExpressionValue(format, "format(...)");
        this.status = "running (" + str + ", " + format + " ms)";
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

    public final void shutdown() {
        this.enabled = false;
        this.answer = null;
    }

    private final Interpreter ensure() {
        Interpreter it = this.interp;
        if (it != null) {
            if (modelFile().lastModified() == this.loadedMtime) {
                return it;
            }
            close();
        }
        this.loadedMtime = modelFile().lastModified();
        if (!getHasModel()) {
            this.status = "no model imported";
            return null;
        }
        this.status = "loading…";
        byte[] bytes = FilesKt.readBytes(modelFile());
        ByteBuffer $this$ensure_u24lambda_u244 = ByteBuffer.allocateDirect(bytes.length).order(ByteOrder.nativeOrder());
        $this$ensure_u24lambda_u244.put(bytes);
        $this$ensure_u24lambda_u244.rewind();
        try {
            GpuDelegateFactory.Options o = new GpuDelegate.Options().setPrecisionLossAllowed(true).setQuantizedModelsAllowed(true).setInferencePreference(1);
            GpuDelegate d = new GpuDelegate(o);
            try {
                Interpreter i = new Interpreter($this$ensure_u24lambda_u244, new Interpreter.Options().addDelegate((Delegate) d));
                this.gpu = d;
                this.accel = "gpu";
                this.interp = i;
                return i;
            } catch (Throwable e) {
                Log.w("StarView/LaneModel", "GPU: " + e);
                try {
                    d.close();
                } catch (Throwable th) {
                }
            }
        } catch (Throwable e2) {
            Log.w("StarView/LaneModel", "GPU delegate: " + e2);
        }
        Interpreter it2 = new Interpreter($this$ensure_u24lambda_u244, new Interpreter.Options().setNumThreads(2));
        this.accel = "cpu";
        this.interp = it2;
        return it2;
    }
}
