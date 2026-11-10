package com.starpilot.starview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.Image;
import android.util.Log;
import com.starpilot.starview.Detector;
import com.starpilot.starview.ModelDownload;
import com.starpilot.starview.Projection;
import java.io.File;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicReference;
import kotlin.Pair;
import kotlin.Triple;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.ArrayDeque;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.collections.MapsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import org.tensorflow.lite.Delegate;
import org.tensorflow.lite.Interpreter;
import org.tensorflow.lite.gpu.GpuDelegate;

/* compiled from: Detector.kt */
public final class Detector {
    public static final int AMB = 16;
    public static final String ASSET = "det/yolox_s_384x640_fp16.lite";
    public static final float HIGH = 0.35f;
    public static final int IN_H = 384;
    public static final int IN_W = 640;
    public static final float LOW = 0.15f;
    public static final int NC = 80;
    private static final String TAG = "StarView/Det";
    public static final int WEAK_MAX = 6;
    private volatile String accel;
    private final Context ctx;
    private volatile long detsAtNs;
    private boolean firstRun;
    private volatile double firstRunMs;
    private volatile float fovTan;
    private volatile double fps;
    private long fpsT0;
    private int fpsWin;
    private GpuDelegate gpu;
    private volatile String gpuError;
    private long ideasDayMs;
    private volatile double inferMs;
    private final ByteBuffer input;
    private Interpreter interp;
    private volatile LaneMarks laneMarks;
    private volatile LaneModel laneModel;
    private volatile Bitmap lastClean;
    private volatile LayoutSeg layout;
    private volatile double loadMs;
    private final Object lock;
    private final boolean[][] maskBits;
    private final long[] maskKey;
    private final int nThreads;
    private long nextId;
    private volatile List<Obj> objects;
    private volatile long objectsAtMs;
    private final CarOrient orient;
    private int[] outOrder;
    private final List<Triple<Integer, Integer, ByteBuffer>> outs;
    private int padKey;
    private final float[] pix;
    private final ExecutorService pool;
    private volatile double prepMs;
    private final Source roadSrc;
    private volatile boolean running;
    private final SchoolBus schoolBus;
    private volatile Function1<? super Bitmap, Unit> shotReq;
    private volatile boolean shownWide;
    private volatile Bitmap snap;
    private volatile int snapWant;
    private volatile boolean snapWide;
    private volatile String status;
    private volatile Telemetry telemetry;
    private volatile boolean useOrient;
    private volatile long wideFrameNs;
    private volatile Function1<? super Bitmap, Unit> wideReq;
    private final Source wideSrc;
    private float[] wiperAvg;
    private boolean wiperDark;
    private final ArrayDeque<Long> wiperTimes;
    private Thread worker;
    public static final Companion Companion = new Companion(null);
    private static final Map<Integer, String> NAMES = MapsKt.mapOf(TuplesKt.to(0, "person"), TuplesKt.to(1, "bike"), TuplesKt.to(2, "car"), TuplesKt.to(3, "moto"), TuplesKt.to(5, "bus"), TuplesKt.to(7, "truck"), TuplesKt.to(9, "light"), TuplesKt.to(11, "stop"));

    /* compiled from: Detector.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final Map<Integer, String> getNAMES() {
            return Detector.NAMES;
        }
    }

    public Detector(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        this.ctx = ctx;
        this.objects = CollectionsKt.emptyList();
        this.fovTan = 0.5f;
        this.status = "off";
        this.accel = "-";
        this.gpuError = "";
        this.orient = new CarOrient(this.ctx);
        DetMasks.INSTANCE.init(this.ctx);
        char c = 1;
        this.useOrient = true;
        char c2 = 0;
        this.roadSrc = new Source(false);
        this.wideSrc = new Source(true);
        this.lock = new Object();
        ByteBuffer order = ByteBuffer.allocateDirect(2949120).order(ByteOrder.nativeOrder());
        Intrinsics.checkNotNullExpressionValue(order, "order(...)");
        this.input = order;
        Object[] $this$map$iv = {new int[]{48, 80}, new int[]{24, 40}, new int[]{12, 20}};
        Collection destination$iv$iv = new ArrayList($this$map$iv.length);
        int length = $this$map$iv.length;
        int i = 0;
        while (i < length) {
            Object item$iv$iv = $this$map$iv[i];
            int[] iArr = (int[]) item$iv$iv;
            int h = iArr[c2];
            int w = iArr[c];
            destination$iv$iv.add(new Triple(Integer.valueOf(h), Integer.valueOf(w), ByteBuffer.allocateDirect(h * w * 85 * 4).order(ByteOrder.nativeOrder())));
            i++;
            $this$map$iv = $this$map$iv;
            c = 1;
            c2 = 0;
        }
        this.outs = (List) destination$iv$iv;
        this.fpsT0 = System.nanoTime();
        this.firstRun = true;
        this.pix = new float[737280];
        this.nThreads = 4;
        this.pool = Executors.newFixedThreadPool(this.nThreads, new ThreadFactory() { // from class: com.starpilot.starview.Detector$$ExternalSyntheticLambda0
            @Override // java.util.concurrent.ThreadFactory
            public final Thread newThread(Runnable runnable) {
                Thread pool$lambda$19;
                pool$lambda$19 = Detector.pool$lambda$19(runnable);
                return pool$lambda$19;
            }
        });
        this.padKey = -1;
        this.maskKey = new long[]{-1, -1};
        this.maskBits = new boolean[2][];
        this.nextId = 1L;
        this.schoolBus = new SchoolBus();
        this.wiperTimes = new ArrayDeque<>();
    }

    /* compiled from: Detector.kt */
    public static final class Det {
        private final int cls;
        private final long id;
        private final float score;
        private final float x0;
        private final float x1;
        private final float y0;
        private final float y1;

        public Det(int cls, float score, float x0, float y0, float x1, float y1, long id) {
            this.cls = cls;
            this.score = score;
            this.x0 = x0;
            this.y0 = y0;
            this.x1 = x1;
            this.y1 = y1;
            this.id = id;
        }

        public /* synthetic */ Det(int i, float f, float f2, float f3, float f4, float f5, long j, int i2, DefaultConstructorMarker defaultConstructorMarker) {
            this(i, f, f2, f3, f4, f5, (i2 & 64) != 0 ? 0L : j);
        }

        public final int getCls() {
            return this.cls;
        }

        public final long getId() {
            return this.id;
        }

        public final float getScore() {
            return this.score;
        }

        public final float getX0() {
            return this.x0;
        }

        public final float getX1() {
            return this.x1;
        }

        public final float getY0() {
            return this.y0;
        }

        public final float getY1() {
            return this.y1;
        }

        public final String getName() {
            String str = Detector.Companion.getNAMES().get(Integer.valueOf(this.cls));
            return str == null ? "?" : str;
        }
    }

    /* compiled from: Detector.kt */
    public static final class Obj {
        private final int cls;
        private final float cx;
        private final float cy;
        private final int facing;
        private final float heading;
        private final long id;
        private final int kind;
        private final int lamp;
        private final boolean moving;
        private final float speed;
        private final boolean velValid;
        private final float vx;
        private final float vy;
        private final float w;
        private final boolean wideCam;
        private final float x;
        private final float y;

        public Obj(long id, int cls, float x, float y, float w, int facing, int lamp, int kind, boolean wideCam, float heading, float cx, float cy, boolean moving, float speed, float vx, float vy, boolean velValid) {
            this.id = id;
            this.cls = cls;
            this.x = x;
            this.y = y;
            this.w = w;
            this.facing = facing;
            this.lamp = lamp;
            this.kind = kind;
            this.wideCam = wideCam;
            this.heading = heading;
            this.cx = cx;
            this.cy = cy;
            this.moving = moving;
            this.speed = speed;
            this.vx = vx;
            this.vy = vy;
            this.velValid = velValid;
        }

        public /* synthetic */ Obj(long j, int i, float f, float f2, float f3, int i2, int i3, int i4, boolean z, float f4, float f5, float f6, boolean z2, float f7, float f8, float f9, boolean z3, int i5, DefaultConstructorMarker defaultConstructorMarker) {
            this(j, i, f, f2, f3, i2, i3, i4, (i5 & 256) != 0 ? false : z, (i5 & 512) != 0 ? Float.NaN : f4, (i5 & 1024) != 0 ? f : f5, (i5 & 2048) != 0 ? f2 : f6, (i5 & 4096) != 0 ? false : z2, (i5 & 8192) != 0 ? 0.0f : f7, (i5 & 16384) != 0 ? 0.0f : f8, (32768 & i5) != 0 ? 0.0f : f9, (i5 & 65536) != 0 ? false : z3);
        }

        public final int getCls() {
            return this.cls;
        }

        public final int getFacing() {
            return this.facing;
        }

        public final long getId() {
            return this.id;
        }

        public final int getKind() {
            return this.kind;
        }

        public final int getLamp() {
            return this.lamp;
        }

        public final float getW() {
            return this.w;
        }

        public final boolean getWideCam() {
            return this.wideCam;
        }

        public final float getX() {
            return this.x;
        }

        public final float getY() {
            return this.y;
        }

        public final float getHeading() {
            return this.heading;
        }

        public final float getCx() {
            return this.cx;
        }

        public final float getCy() {
            return this.cy;
        }

        public final boolean getMoving() {
            return this.moving;
        }

        public final float getSpeed() {
            return this.speed;
        }

        public final boolean getVelValid() {
            return this.velValid;
        }

        public final float getVx() {
            return this.vx;
        }

        public final float getVy() {
            return this.vy;
        }
    }

    public final List<Obj> getObjects() {
        return this.objects;
    }

    public final long getObjectsAtMs() {
        return this.objectsAtMs;
    }

    public final void setObjects(List<Obj> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.objects = list;
    }

    public final void setObjectsAtMs(long j) {
        this.objectsAtMs = j;
    }

    public final float getFovTan() {
        return this.fovTan;
    }

    public final void setFovTan(float f) {
        this.fovTan = f;
    }

    public final Telemetry getTelemetry() {
        return this.telemetry;
    }

    public final void setTelemetry(Telemetry telemetry) {
        this.telemetry = telemetry;
    }

    public final boolean getShownWide() {
        return this.shownWide;
    }

    public final void setShownWide(boolean z) {
        this.shownWide = z;
    }

    public final String getAccel() {
        return this.accel;
    }

    public final double getInferMs() {
        return this.inferMs;
    }

    public final double getPrepMs() {
        return this.prepMs;
    }

    public final String getStatus() {
        return this.status;
    }

    public final void setAccel(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.accel = str;
    }

    public final void setInferMs(double d) {
        this.inferMs = d;
    }

    public final void setPrepMs(double d) {
        this.prepMs = d;
    }

    public final void setStatus(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.status = str;
    }

    public final double getFps() {
        return this.fps;
    }

    public final String getGpuError() {
        return this.gpuError;
    }

    public final void setFps(double d) {
        this.fps = d;
    }

    public final void setGpuError(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.gpuError = str;
    }

    public final LayoutSeg getLayout() {
        return this.layout;
    }

    public final void setLayout(LayoutSeg layoutSeg) {
        this.layout = layoutSeg;
    }

    public final LaneMarks getLaneMarks() {
        return this.laneMarks;
    }

    public final void setLaneMarks(LaneMarks laneMarks) {
        this.laneMarks = laneMarks;
    }

    public final boolean getUseOrient() {
        return this.useOrient;
    }

    public final void setUseOrient(boolean z) {
        this.useOrient = z;
    }

    public final double getOrientMs() {
        return this.orient.getMs();
    }

    public final String getOrientError() {
        return this.orient.getFailed();
    }

    public final List<Det> getDets() {
        return (this.shownWide ? this.wideSrc : this.roadSrc).getDets();
    }

    public final long getDetsAtNs() {
        return this.detsAtNs;
    }

    public final void setDetsAtNs(long j) {
        this.detsAtNs = j;
    }

    public final int getSnapWant() {
        return this.snapWant;
    }

    public final void setSnapWant(int i) {
        this.snapWant = i;
    }

    public final Bitmap getSnap() {
        return this.snap;
    }

    public final boolean getSnapWide() {
        return this.snapWide;
    }

    public final void setSnap(Bitmap bitmap) {
        this.snap = bitmap;
    }

    public final void setSnapWide(boolean z) {
        this.snapWide = z;
    }

    public final Function1<Bitmap, Unit> getShotReq() {
        return this.shotReq;
    }

    public final void setShotReq(Function1<? super Bitmap, Unit> function1) {
        this.shotReq = function1;
    }

    public final Function1<Bitmap, Unit> getWideReq() {
        return this.wideReq;
    }

    public final void setWideReq(Function1<? super Bitmap, Unit> function1) {
        this.wideReq = function1;
    }

    public final long getWideFrameNs() {
        return this.wideFrameNs;
    }

    public final void setWideFrameNs(long j) {
        this.wideFrameNs = j;
    }

    public final boolean wideLive() {
        return System.nanoTime() - this.wideFrameNs < 1000000000;
    }

    public final Bitmap getLastClean() {
        return this.lastClean;
    }

    public final void setLastClean(Bitmap bitmap) {
        this.lastClean = bitmap;
    }

    public final LaneModel getLaneModel() {
        return this.laneModel;
    }

    public final void setLaneModel(LaneModel laneModel) {
        this.laneModel = laneModel;
    }

    private final Bitmap colour(Frame f) {
        int h = (f.getH() * 640) / f.getW();
        int[] px = new int[640 * h];
        for (int j = 0; j < h; j++) {
            int sy = (f.getH() * j) / h;
            int yRow = f.getYStride() * sy;
            int uvRow = (sy >> 1) * f.getUvStride();
            int i = 0;
            while (i < 640) {
                int sx = (f.getW() * i) / 640;
                int yy = (f.getY()[yRow + sx] & 255) - 16;
                int ui = ((sx >> 1) * f.getUvPixStride()) + uvRow;
                int uu = (f.getU()[ui] & 255) - 128;
                int vv = (f.getV()[ui] & 255) - 128;
                float c = yy * 1.164f;
                int sy2 = sy;
                int r = RangesKt.coerceIn((int) ((vv * 1.596f) + c), 0, 255);
                int yRow2 = yRow;
                int g = RangesKt.coerceIn((int) ((c - (uu * 0.392f)) - (vv * 0.813f)), 0, 255);
                int b = RangesKt.coerceIn((int) ((uu * 2.017f) + c), 0, 255);
                px[(j * 640) + i] = (r << 16) | (-16777216) | (g << 8) | b;
                i++;
                yRow = yRow2;
                sy = sy2;
                uvRow = uvRow;
            }
        }
        Bitmap createBitmap = Bitmap.createBitmap(px, 640, h, Bitmap.Config.ARGB_8888);
        Intrinsics.checkNotNullExpressionValue(createBitmap, "createBitmap(...)");
        return createBitmap;
    }

    private final void drawPath(Bitmap bmp, Frame f) {
        ModelSnapshot m;
        Telemetry t = this.telemetry;
        if (t == null || (m = t.getModel()) == null) {
            return;
        }
        Path.INSTANCE.pathOn(bmp, f.getW(), f.getH(), t.getCalRpy(), t.getCalHeight(), m.getPosX(), m.getPosY(), m.getPosZ());
    }

    /* compiled from: Detector.kt */
    public static final class Path {
        public static final Path INSTANCE = new Path();

        private Path() {
        }

        public final void pathOn(Bitmap bmp, int fw, int fh, float[] rpy, float height, float[] px, float[] py, float[] pz) {
            int j;
            int i;
            float[] px2 = px;
            float[] py2 = py;
            Intrinsics.checkNotNullParameter(bmp, "bmp");
            Intrinsics.checkNotNullParameter(rpy, "rpy");
            Intrinsics.checkNotNullParameter(px2, "px");
            Intrinsics.checkNotNullParameter(py2, "py");
            Intrinsics.checkNotNullParameter(pz, "pz");
            if (px2.length < 4) {
                return;
            }
            Projection.Companion.Cam c = Projection.Companion.camFor(fw, fh, false);
            double[] k = {c.getF(), 0.0d, c.getCx(), 0.0d, c.getF(), c.getCy(), 0.0d, 0.0d, 1.0d};
            double[] ct = Projection.Companion.mul(k, Projection.Companion.mul(Projection.Companion.getVIEW_FROM_DEVICE(), Projection.Companion.rotFromEuler(rpy[0], rpy[1], rpy[2])));
            double s = bmp.getWidth() / fw;
            ArrayList left = new ArrayList();
            ArrayList right = new ArrayList();
            int j2 = 0;
            int length = px2.length;
            while (j2 < length) {
                double x = px2[j2];
                if (x < 4.0d) {
                    j = j2;
                    i = length;
                } else {
                    if (x > 25.0d) {
                        break;
                    }
                    double y = py2[j2];
                    j = j2;
                    i = length;
                    double z = pz[j2] + height;
                    double[] pl = Projection.Companion.mulv(ct, x, y - 0.45d, z);
                    double[] pr = Projection.Companion.mulv(ct, x, y + 0.45d, z);
                    if (pl[2] >= 0.5d && pr[2] >= 0.5d) {
                        left.add(Float.valueOf((float) ((pl[0] / pl[2]) * s)));
                        left.add(Float.valueOf((float) ((pl[1] / pl[2]) * s)));
                        right.add(Float.valueOf((float) ((pr[0] / pr[2]) * s)));
                        right.add(Float.valueOf((float) ((pr[1] / pr[2]) * s)));
                    }
                }
                j2 = j + 1;
                px2 = px;
                py2 = py;
                length = i;
            }
            if (left.size() < 4) {
                return;
            }
            android.graphics.Path path = new android.graphics.Path();
            Object obj = left.get(0);
            Intrinsics.checkNotNullExpressionValue(obj, "get(...)");
            float floatValue = ((Number) obj).floatValue();
            Object obj2 = left.get(1);
            Intrinsics.checkNotNullExpressionValue(obj2, "get(...)");
            path.moveTo(floatValue, ((Number) obj2).floatValue());
            for (int i2 = 2; i2 < left.size(); i2 += 2) {
                Object obj3 = left.get(i2);
                Intrinsics.checkNotNullExpressionValue(obj3, "get(...)");
                float floatValue2 = ((Number) obj3).floatValue();
                Object obj4 = left.get(i2 + 1);
                Intrinsics.checkNotNullExpressionValue(obj4, "get(...)");
                path.lineTo(floatValue2, ((Number) obj4).floatValue());
            }
            for (int i3 = right.size() - 2; i3 >= 0; i3 -= 2) {
                Object obj5 = right.get(i3);
                Intrinsics.checkNotNullExpressionValue(obj5, "get(...)");
                float floatValue3 = ((Number) obj5).floatValue();
                Object obj6 = right.get(i3 + 1);
                Intrinsics.checkNotNullExpressionValue(obj6, "get(...)");
                path.lineTo(floatValue3, ((Number) obj6).floatValue());
            }
            path.close();
            Canvas canvas = new Canvas(bmp);
            Paint $this$pathOn_u24lambda_u240 = new Paint(1);
            $this$pathOn_u24lambda_u240.setColor(Color.argb(150, 55, 138, 255));
            Unit unit = Unit.INSTANCE;
            canvas.drawPath(path, $this$pathOn_u24lambda_u240);
        }
    }

    private final Bitmap grey(Frame f) {
        int w = f.getW() / 2;
        int h = f.getH() / 2;
        int[] px = new int[w * h];
        for (int y = 0; y < h; y++) {
            int row = y * 2 * f.getYStride();
            for (int x = 0; x < w; x++) {
                px[(y * w) + x] = ((f.getY()[(x * 2) + row] & 255) * 65793) | (-16777216);
            }
        }
        Bitmap createBitmap = Bitmap.createBitmap(px, w, h, Bitmap.Config.ARGB_8888);
        Intrinsics.checkNotNullExpressionValue(createBitmap, "createBitmap(...)");
        return createBitmap;
    }

    public final int[] ambient(boolean wide) {
        return (wide ? this.wideSrc : this.roadSrc).getAmbient();
    }

    private final int[] ambient(Frame f) {
        long j;
        int[] out = new int[32];
        int i = 16;
        int band = Math.max(2, f.getH() / 16);
        int half = 0;
        for (int i2 = 2; half < i2; i2 = 2) {
            int i3 = 0;
            while (i3 < i) {
                long sy = 0;
                long su = 0;
                long sv = 0;
                int n = 0;
                int x0 = (f.getW() * i3) / i;
                int x1 = ((i3 + 1) * f.getW()) / i;
                int y0 = half == 0 ? 0 : f.getH() - band;
                for (int y = y0; y < y0 + band; y += 4) {
                    int x = x0;
                    while (x < x1) {
                        int band2 = band;
                        int band3 = f.getY()[(f.getYStride() * y) + x];
                        int x02 = x0;
                        int x12 = x1;
                        sy += band3 & 255;
                        int ui = ((y / 2) * f.getUvStride()) + ((x / 2) * f.getUvPixStride());
                        if (ui >= f.getU().length || ui >= f.getV().length) {
                            j = 128;
                            su += j;
                        } else {
                            su += f.getU()[ui] & 255;
                            j = f.getV()[ui] & 255;
                        }
                        sv += j;
                        n++;
                        x += 6;
                        x0 = x02;
                        x1 = x12;
                        band = band2;
                    }
                }
                int band4 = band;
                if (n != 0) {
                    float yy = (sy / n) - 16.0f;
                    float uu = (su / n) - 128.0f;
                    float vv = (sv / n) - 128.0f;
                    int r = RangesKt.coerceIn((int) ((yy * 1.164f) + (1.596f * vv)), 0, 255);
                    int g = RangesKt.coerceIn((int) (((yy * 1.164f) - (0.392f * uu)) - (0.813f * vv)), 0, 255);
                    int b = RangesKt.coerceIn((int) ((1.164f * yy) + (2.017f * uu)), 0, 255);
                    out[(half * 16) + i3] = (r << 16) | (-16777216) | (g << 8) | b;
                }
                i3++;
                band = band4;
                i = 16;
            }
            half++;
            i = 16;
        }
        return out;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: Detector.kt */
    static final class Frame {
        private int h;
        private byte[] u;
        private int uvPixStride;
        private int uvStride;
        private byte[] v;
        private int w;
        private byte[] y;
        private int yStride;

        public Frame() {
            this(0, 0, null, null, null, 0, 0, 0, 255, null);
        }

        public Frame(int w, int h, byte[] y, byte[] u, byte[] v, int yStride, int uvStride, int uvPixStride) {
            Intrinsics.checkNotNullParameter(y, "y");
            Intrinsics.checkNotNullParameter(u, "u");
            Intrinsics.checkNotNullParameter(v, "v");
            this.w = w;
            this.h = h;
            this.y = y;
            this.u = u;
            this.v = v;
            this.yStride = yStride;
            this.uvStride = uvStride;
            this.uvPixStride = uvPixStride;
        }

        public /* synthetic */ Frame(int i, int i2, byte[] bArr, byte[] bArr2, byte[] bArr3, int i3, int i4, int i5, int i6, DefaultConstructorMarker defaultConstructorMarker) {
            this((i6 & 1) != 0 ? 0 : i, (i6 & 2) != 0 ? 0 : i2, (i6 & 4) != 0 ? new byte[0] : bArr, (i6 & 8) != 0 ? new byte[0] : bArr2, (i6 & 16) != 0 ? new byte[0] : bArr3, (i6 & 32) != 0 ? 0 : i3, (i6 & 64) != 0 ? 0 : i4, (i6 & 128) != 0 ? 0 : i5);
        }

        public final int getH() {
            return this.h;
        }

        public final byte[] getU() {
            return this.u;
        }

        public final byte[] getV() {
            return this.v;
        }

        public final int getW() {
            return this.w;
        }

        public final byte[] getY() {
            return this.y;
        }

        public final void setH(int i) {
            this.h = i;
        }

        public final void setU(byte[] bArr) {
            Intrinsics.checkNotNullParameter(bArr, "<set-?>");
            this.u = bArr;
        }

        public final void setV(byte[] bArr) {
            Intrinsics.checkNotNullParameter(bArr, "<set-?>");
            this.v = bArr;
        }

        public final void setW(int i) {
            this.w = i;
        }

        public final void setY(byte[] bArr) {
            Intrinsics.checkNotNullParameter(bArr, "<set-?>");
            this.y = bArr;
        }

        public final int getUvPixStride() {
            return this.uvPixStride;
        }

        public final int getUvStride() {
            return this.uvStride;
        }

        public final int getYStride() {
            return this.yStride;
        }

        public final void setUvPixStride(int i) {
            this.uvPixStride = i;
        }

        public final void setUvStride(int i) {
            this.uvStride = i;
        }

        public final void setYStride(int i) {
            this.yStride = i;
        }
    }

    /* compiled from: Detector.kt */
    private final class Source {
        private volatile int[] ambient;
        private long ambientNs;
        private volatile long atMs;
        private volatile List<Det> dets;
        private volatile float fovTan;
        private volatile double fps;
        private long fpsT0;
        private int fpsWin;
        private final AtomicReference<Frame> frame = new AtomicReference<>(null);
        private final ConcurrentLinkedQueue<Frame> free;
        private long lastRunNs;
        private long lastTrackNs;
        private volatile List<Obj> objects;
        private final ArrayList<Track> tracks;
        private final boolean wide;

        public Source(boolean wide) {
            this.wide = wide;
            ConcurrentLinkedQueue $this$free_u24lambda_u241 = new ConcurrentLinkedQueue();
            for (int i = 0; i < 3; i++) {
                $this$free_u24lambda_u241.add(new Frame(0, 0, null, null, null, 0, 0, 0, 255, null));
            }
            this.free = $this$free_u24lambda_u241;
            this.tracks = new ArrayList<>();
            this.dets = CollectionsKt.emptyList();
            this.objects = CollectionsKt.emptyList();
            this.fovTan = 0.5f;
            this.fpsT0 = System.nanoTime();
        }

        public final boolean getWide() {
            return this.wide;
        }

        public final AtomicReference<Frame> getFrame() {
            return this.frame;
        }

        public final ConcurrentLinkedQueue<Frame> getFree() {
            return this.free;
        }

        public final ArrayList<Track> getTracks() {
            return this.tracks;
        }

        public final List<Det> getDets() {
            return this.dets;
        }

        public final void setDets(List<Det> list) {
            Intrinsics.checkNotNullParameter(list, "<set-?>");
            this.dets = list;
        }

        public final long getAtMs() {
            return this.atMs;
        }

        public final List<Obj> getObjects() {
            return this.objects;
        }

        public final void setAtMs(long j) {
            this.atMs = j;
        }

        public final void setObjects(List<Obj> list) {
            Intrinsics.checkNotNullParameter(list, "<set-?>");
            this.objects = list;
        }

        public final float getFovTan() {
            return this.fovTan;
        }

        public final void setFovTan(float f) {
            this.fovTan = f;
        }

        public final long getLastRunNs() {
            return this.lastRunNs;
        }

        public final long getLastTrackNs() {
            return this.lastTrackNs;
        }

        public final void setLastRunNs(long j) {
            this.lastRunNs = j;
        }

        public final void setLastTrackNs(long j) {
            this.lastTrackNs = j;
        }

        public final int[] getAmbient() {
            return this.ambient;
        }

        public final long getAmbientNs() {
            return this.ambientNs;
        }

        public final void setAmbient(int[] iArr) {
            this.ambient = iArr;
        }

        public final void setAmbientNs(long j) {
            this.ambientNs = j;
        }

        public final double getFps() {
            return this.fps;
        }

        public final long getFpsT0() {
            return this.fpsT0;
        }

        public final int getFpsWin() {
            return this.fpsWin;
        }

        public final void setFps(double d) {
            this.fps = d;
        }

        public final void setFpsT0(long j) {
            this.fpsT0 = j;
        }

        public final void setFpsWin(int i) {
            this.fpsWin = i;
        }

        public final void clear() {
            this.tracks.clear();
            this.dets = CollectionsKt.emptyList();
            this.objects = CollectionsKt.emptyList();
            Frame it = this.frame.getAndSet(null);
            if (it != null) {
                this.free.add(it);
            }
        }
    }

    public final double getRoadFps() {
        return this.roadSrc.getFps();
    }

    public final double getWideFps() {
        return this.wideSrc.getFps();
    }

    /* renamed from: isRunning, reason: from getter */
    public final boolean getRunning() {
        return this.running;
    }

    public final void offer(Image img, boolean wide) {
        Intrinsics.checkNotNullParameter(img, "img");
        if (this.running) {
            Source src = wide ? this.wideSrc : this.roadSrc;
            Frame fr = src.getFree().poll();
            if (fr == null) {
                return;
            }
            try {
                Image.Plane[] p = img.getPlanes();
                fr.setW(img.getWidth());
                fr.setH(img.getHeight());
                ByteBuffer buffer = p[0].getBuffer();
                Intrinsics.checkNotNullExpressionValue(buffer, "getBuffer(...)");
                fr.setY(copy(buffer, fr.getY()));
                ByteBuffer buffer2 = p[1].getBuffer();
                Intrinsics.checkNotNullExpressionValue(buffer2, "getBuffer(...)");
                fr.setU(copy(buffer2, fr.getU()));
                ByteBuffer buffer3 = p[2].getBuffer();
                Intrinsics.checkNotNullExpressionValue(buffer3, "getBuffer(...)");
                fr.setV(copy(buffer3, fr.getV()));
                fr.setYStride(p[0].getRowStride());
                fr.setUvStride(p[1].getRowStride());
                fr.setUvPixStride(p[1].getPixelStride());
                Frame it = src.getFrame().getAndSet(fr);
                if (it != null) {
                    src.getFree().add(it);
                }
                synchronized (this.lock) {
                    this.lock.notifyAll();
                    Unit unit = Unit.INSTANCE;
                }
            } catch (Exception e) {
                src.getFree().add(fr);
                Log.w("StarView/Det", "offer: " + e);
            }
        }
    }

    public final void clearSource(boolean wide) {
        Source it = wide ? this.wideSrc : this.roadSrc;
        it.setAtMs(0L);
        mergeObjects();
    }

    private final byte[] copy(ByteBuffer b, byte[] into) {
        ByteBuffer d = b.duplicate();
        d.rewind();
        byte[] out = into.length == d.remaining() ? into : new byte[d.remaining()];
        d.get(out);
        return out;
    }

    public final void start() {
        if (this.running) {
            return;
        }
        this.running = true;
        Thread it = new Thread(new Runnable() { // from class: com.starpilot.starview.Detector$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                Detector.this.loop();
            }
        }, "starview-detector");
        it.setPriority(4);
        it.start();
        this.worker = it;
    }

    public final void shutdown() {
        stop();
        release();
        this.pool.shutdownNow();
    }

    public final void stop() {
        if (this.running) {
            this.running = false;
            synchronized (this.lock) {
                this.lock.notifyAll();
                Unit unit = Unit.INSTANCE;
            }
            Thread thread = this.worker;
            if (thread != null) {
                thread.join(2000L);
            }
            this.worker = null;
            release();
            this.roadSrc.clear();
            this.wideSrc.clear();
            this.objects = CollectionsKt.emptyList();
            this.status = "off";
        }
    }

    private final void ensureInterpreter() {
        Interpreter it;
        if (this.interp != null) {
            return;
        }
        this.status = "loading model…";
        this.firstRun = true;
        long tl = System.nanoTime();
        File detector = ModelDownload.INSTANCE.detector(this.ctx);
        if (detector == null) {
            throw new ModelDownload.Missing(ModelDownload.INSTANCE.getStatus());
        }
        byte[] bytes = FilesKt.readBytes(detector);
        ByteBuffer $this$ensureInterpreter_u24lambda_u247 = ByteBuffer.allocateDirect(bytes.length).order(ByteOrder.nativeOrder());
        $this$ensureInterpreter_u24lambda_u247.put(bytes);
        $this$ensureInterpreter_u24lambda_u247.rewind();
        try {
            File cache = new File(this.ctx.getCacheDir(), "gpu_det");
            cache.mkdirs();
            GpuDelegate d = new GpuDelegate(new GpuDelegate.Options().setPrecisionLossAllowed(true).setInferencePreference(1).setSerializationParams(cache.getAbsolutePath(), "yolox_s_384x640_fp16_v1"));
            this.interp = new Interpreter($this$ensureInterpreter_u24lambda_u247, new Interpreter.Options().addDelegate((Delegate) d));
            this.gpu = d;
            this.accel = "gpu";
        } catch (Throwable e) {
            Log.w("StarView/Det", "GPU delegate: " + e);
            String message = e.getMessage();
            if (message == null) {
                message = e.toString();
            }
            this.gpuError = message;
            try {
                GpuDelegate gpuDelegate = this.gpu;
                if (gpuDelegate != null) {
                    gpuDelegate.close();
                }
            } catch (Throwable th) {
            }
            this.gpu = null;
            this.interp = new Interpreter($this$ensureInterpreter_u24lambda_u247, new Interpreter.Options().setNumThreads(4));
            this.accel = "cpu";
        }
        Interpreter it2 = this.interp;
        Intrinsics.checkNotNull(it2);
        int outputTensorCount = it2.getOutputTensorCount();
        int[] iArr = new int[outputTensorCount];
        int i = 0;
        while (i < outputTensorCount) {
            int[] s = it2.getOutputTensor(i).shape();
            List $this$indexOfFirst$iv = this.outs;
            int index$iv = 0;
            Iterator<Triple<Integer, Integer, ByteBuffer>> it3 = $this$indexOfFirst$iv.iterator();
            while (true) {
                if (it3.hasNext()) {
                    Triple item$iv = it3.next();
                    Triple o = item$iv;
                    it = it2;
                    if (o.getFirst().intValue() == s[1] && o.getSecond().intValue() == s[2]) {
                        break;
                    }
                    index$iv++;
                    it2 = it;
                } else {
                    it = it2;
                    index$iv = -1;
                    break;
                }
            }
            iArr[i] = index$iv;
            i++;
            it2 = it;
        }
        this.outOrder = iArr;
        this.loadMs = (System.nanoTime() - tl) / 1000000.0d;
    }

    private final void release() {
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
        this.orient.close();
    }

    public final double getFirstRunMs() {
        return this.firstRunMs;
    }

    public final double getLoadMs() {
        return this.loadMs;
    }

    public final void setFirstRunMs(double d) {
        this.firstRunMs = d;
    }

    public final void setLoadMs(double d) {
        this.loadMs = d;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v2, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r4v25 */
    /* JADX WARN: Type inference failed for: r4v28 */
    /* JADX WARN: Type inference failed for: r4v4 */
    /* JADX WARN: Type inference failed for: r4v5 */
    /* JADX WARN: Type inference failed for: r4v6 */
    /* JADX WARN: Type inference failed for: r9v0 */
    /* JADX WARN: Type inference failed for: r9v1 */
    /* JADX WARN: Type inference failed for: r9v14 */
    /* JADX WARN: Type inference failed for: r9v15 */
    /* JADX WARN: Type inference failed for: r9v3 */
    /* JADX WARN: Type inference failed for: r9v4, types: [boolean, int] */
    public final void loop() {
        Source next;
        LaneModel laneModel;
        Function1<? super Bitmap, Unit> function1;
        AtomicReference<Frame> frame;
        while (this.running) {
            ?? r9 = 0;
            r9 = 0;
            r9 = 0;
            List listOf = CollectionsKt.listOf((Object[]) new Source[]{this.roadSrc, this.wideSrc});
            ArrayList arrayList = new ArrayList();
            for (Object obj : listOf) {
                if ((((Source) obj).getFrame().get() != null ? 1 : null) != null) {
                    arrayList.add(obj);
                }
            }
            Iterator it = arrayList.iterator();
            if (it.hasNext()) {
                next = it.next();
                if (it.hasNext()) {
                    long lastRunNs = ((Source) next).getLastRunNs();
                    boolean z = next;
                    while (true) {
                        Object next2 = it.next();
                        long lastRunNs2 = ((Source) next2).getLastRunNs();
                        next = z;
                        if (lastRunNs > lastRunNs2) {
                            next = next2;
                            lastRunNs = lastRunNs2;
                        }
                        if (!it.hasNext()) {
                            break;
                        }
                        r9 = r9 == true ? 1 : 0 ? 1 : 0;
                        z = next;
                    }
                }
            } else {
                next = null;
            }
            Source source = next;
            Frame andSet = (source == null || (frame = source.getFrame()) == null) ? null : frame.getAndSet(null);
            if (source == null || andSet == null) {
                synchronized (this.lock) {
                    if (this.running && this.roadSrc.getFrame().get() == null && this.wideSrc.getFrame().get() == null) {
                        this.lock.wait(300L);
                    }
                    Unit unit = Unit.INSTANCE;
                }
            } else {
                source.setLastRunNs(System.nanoTime());
                try {
                    ensureInterpreter();
                    long nanoTime = System.nanoTime();
                    float fill = fill(andSet, source.getWide());
                    long nanoTime2 = System.nanoTime();
                    if (this.firstRun) {
                        this.status = "first GPU run: compiling (can take a minute the first time)…";
                    }
                    Interpreter interpreter = this.interp;
                    Intrinsics.checkNotNull(interpreter);
                    int[] iArr = this.outOrder;
                    Intrinsics.checkNotNull(iArr);
                    HashMap hashMap = new HashMap();
                    int length = iArr.length;
                    for (int i = 0; i < length; i++) {
                        ByteBuffer third = this.outs.get(iArr[i]).getThird();
                        third.rewind();
                        hashMap.put(Integer.valueOf(i), third);
                    }
                    this.input.rewind();
                    interpreter.runForMultipleInputsOutputs(new Object[]{this.input}, hashMap);
                    long nanoTime3 = System.nanoTime();
                    if (this.firstRun) {
                        this.firstRunMs = (nanoTime3 - nanoTime2) / 1000000.0d;
                        this.firstRun = r9;
                    }
                    track(source, decode$default(this, fill, andSet.getW(), andSet.getH(), 0.0f, 8, null), andSet);
                    this.detsAtNs = System.nanoTime();
                    if (this.snapWant == (source.getWide() ? 2 : 1)) {
                        this.snap = grey(andSet);
                        this.snapWide = source.getWide();
                        this.snapWant = r9;
                    }
                    if (!source.getWide() && (function1 = this.shotReq) != null) {
                        this.shotReq = null;
                        try {
                            Bitmap colour = colour(andSet);
                            Bitmap copy = colour.copy(Bitmap.Config.ARGB_8888, true);
                            try {
                                Intrinsics.checkNotNull(copy);
                                drawPath(copy, andSet);
                            } catch (Exception e) {
                            }
                            this.lastClean = colour;
                            Intrinsics.checkNotNull(copy);
                            function1.invoke(copy);
                        } catch (Exception e2) {
                        }
                    }
                    if (!source.getWide() && (laneModel = this.laneModel) != null && LaneModel.wants$default(laneModel, 0L, 1, null)) {
                        try {
                            laneModel.feed(colour(andSet));
                        } catch (Exception e3) {
                        }
                    }
                    if (source.getWide()) {
                        this.wideFrameNs = System.nanoTime();
                        Function1<? super Bitmap, Unit> function12 = this.wideReq;
                        if (function12 != null) {
                            this.wideReq = null;
                            try {
                                function12.invoke(colour(andSet));
                            } catch (Exception e4) {
                            }
                        }
                    }
                    if (System.nanoTime() - source.getAmbientNs() > 250000000) {
                        source.setAmbientNs(System.nanoTime());
                        source.setAmbient(ambient(andSet));
                    }
                    LayoutSeg layoutSeg = this.layout;
                    if (layoutSeg != null) {
                        Telemetry telemetry = this.telemetry;
                        if (source.getWide() && telemetry != null && telemetry.getCalValid() && layoutSeg.wantsFrame(System.nanoTime())) {
                            layoutSeg.feed(andSet.getY(), andSet.getU(), andSet.getV(), andSet.getYStride(), andSet.getUvStride(), andSet.getUvPixStride(), andSet.getW(), andSet.getH(), telemetry.getCalRpy(), telemetry.getCalHeight(), System.nanoTime());
                        }
                    }
                    LaneMarks laneMarks = this.laneMarks;
                    if (laneMarks != null) {
                        Telemetry telemetry2 = this.telemetry;
                        if (!source.getWide() && telemetry2 != null && telemetry2.getCalValid() && laneMarks.wants(System.nanoTime())) {
                            Lead leadOne = telemetry2.getLeadOne();
                            laneMarks.analyze(andSet.getY(), andSet.getU(), andSet.getV(), andSet.getYStride(), andSet.getUvStride(), andSet.getUvPixStride(), andSet.getW(), andSet.getH(), telemetry2.getCalRpy(), telemetry2.getModel(), leadOne.getStatus() ? leadOne.getDRel() : 0.0f, System.nanoTime());
                        }
                    }
                    this.prepMs = (this.prepMs > 0.0d ? 1 : (this.prepMs == 0.0d ? 0 : -1)) == 0 ? true : r9 ? (nanoTime2 - nanoTime) / 1000000.0d : (this.prepMs * 0.8d) + (((nanoTime2 - nanoTime) / 1000000.0d) * 0.2d);
                    this.inferMs = (this.inferMs > 0.0d ? 1 : (this.inferMs == 0.0d ? 0 : -1)) == 0 ? (nanoTime3 - nanoTime2) / 1000000.0d : (this.inferMs * 0.8d) + (((nanoTime3 - nanoTime2) / 1000000.0d) * 0.2d);
                    this.status = "running";
                    long nanoTime4 = System.nanoTime();
                    this.fpsWin++;
                    if (nanoTime4 - this.fpsT0 > 2000000000) {
                        this.fps = (this.fpsWin * 1.0E9d) / (nanoTime4 - this.fpsT0);
                        this.fpsWin = 0;
                        this.fpsT0 = nanoTime4;
                    }
                    source.setFpsWin(source.getFpsWin() + 1);
                    if (nanoTime4 - source.getFpsT0() > 2000000000) {
                        source.setFps((source.getFpsWin() * 1.0E9d) / (nanoTime4 - source.getFpsT0()));
                        source.setFpsWin(0);
                        source.setFpsT0(nanoTime4);
                    }
                } finally {
                    try {
                    } finally {
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Thread pool$lambda$19(Runnable r) {
        Thread $this$pool_u24lambda_u2419_u24lambda_u2418 = new Thread(r, "starview-det-prep");
        $this$pool_u24lambda_u2419_u24lambda_u2418.setDaemon(true);
        return $this$pool_u24lambda_u2419_u24lambda_u2418;
    }

    private final float fill(final Frame f, boolean wide) {
        final float min = Math.min(640.0f / f.getW(), 384.0f / f.getH());
        int min2 = Math.min(640, (int) (f.getW() * min));
        int min3 = Math.min(384, (int) (f.getH() * min));
        int i = (min2 * 10000) + min3;
        if (i != this.padKey) {
            Arrays.fill(this.pix, 114.0f);
            this.padKey = i;
        }
        long version = (DetMasks.INSTANCE.getVersion() << 32) | i;
        if (this.maskKey[wide ? 1 : 0] != version) {
            this.maskBits[wide ? 1 : 0] = DetMasks.INSTANCE.maskFor(wide, min2, min3);
            this.maskKey[wide ? 1 : 0] = version;
        }
        final boolean[] zArr = this.maskBits[wide ? 1 : 0];
        final int[] iArr = new int[min2];
        for (int i2 = 0; i2 < min2; i2++) {
            iArr[i2] = Math.min(f.getW() - 1, (int) (i2 / min));
        }
        final int[] iArr2 = new int[min2];
        for (int i3 = 0; i3 < min2; i3++) {
            iArr2[i3] = (iArr[i3] >> 1) * f.getUvPixStride();
        }
        float[] fArr = this.pix;
        IntRange until = RangesKt.until(0, this.nThreads);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(until, 10));
        Iterator<Integer> it = until.iterator();
        while (it.hasNext()) {
            final int nextInt = ((IntIterator) it).nextInt();
            final int i4 = min3;
            final int i5 = min2;
            int i6 = min2;
            ArrayList arrayList2 = arrayList;
            IntRange intRange = until;
            final float[] fArr2 = fArr;
            arrayList2.add(new Callable() { // from class: com.starpilot.starview.Detector$$ExternalSyntheticLambda3
                @Override // java.util.concurrent.Callable
                public final Object call() {
                    Unit fill$lambda$21$lambda$20;
                    fill$lambda$21$lambda$20 = Detector.fill$lambda$21$lambda$20(nextInt, i4, f, min, i5, zArr, fArr2, iArr, iArr2, this);
                    return fill$lambda$21$lambda$20;
                }
            });
            arrayList = arrayList2;
            fArr = fArr;
            min3 = min3;
            min2 = i6;
            until = intRange;
            i = i;
        }
        float[] fArr3 = fArr;
        Iterator it2 = this.pool.invokeAll(arrayList).iterator();
        while (it2.hasNext()) {
            ((Future) it2.next()).get();
        }
        this.input.rewind();
        this.input.asFloatBuffer().put(fArr3);
        return min;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit fill$lambda$21$lambda$20(int $part, int $nh, Frame $f, float $r, int $nw, boolean[] $mb, float[] $a, int[] $xs, int[] $uvx, Detector this$0) {
        int i = $nw;
        int j = $part;
        while (j < $nh) {
            int sy = Math.min($f.getH() - 1, (int) (j / $r));
            int yRow = $f.getYStride() * sy;
            int uvRow = (sy >> 1) * $f.getUvStride();
            int k = j * 640 * 3;
            int i2 = 0;
            while (i2 < i) {
                if ($mb == null || !$mb[(j * i) + i2]) {
                    int yy = ($f.getY()[$xs[i2] + yRow] & 255) - 16;
                    int uvI = $uvx[i2] + uvRow;
                    int uu = ($f.getU()[uvI] & 255) - 128;
                    int vv = ($f.getV()[uvI] & 255) - 128;
                    int yv = yy * 298;
                    int rr = (((vv * 409) + yv) + 128) >> 8;
                    if (rr < 0) {
                        rr = 0;
                    } else if (rr > 255) {
                        rr = 255;
                    }
                    int gg = (((yv - (uu * 100)) - (vv * 208)) + 128) >> 8;
                    if (gg < 0) {
                        gg = 0;
                    } else if (gg > 255) {
                        gg = 255;
                    }
                    int bb = (((uu * 516) + yv) + 128) >> 8;
                    if (bb < 0) {
                        bb = 0;
                    } else if (bb > 255) {
                        bb = 255;
                    }
                    $a[k] = bb;
                    $a[k + 1] = gg;
                    $a[k + 2] = rr;
                } else {
                    $a[k] = 114.0f;
                    $a[k + 1] = 114.0f;
                    $a[k + 2] = 114.0f;
                }
                k += 3;
                i2++;
                i = $nw;
            }
            j += this$0.nThreads;
            i = $nw;
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: Detector.kt */
    static final class Track {
        private Boolean along;
        private float axisEv;
        private int cls;
        private float gvx;
        private float gvy;
        private float gw;
        private float gy;
        private int hits;
        private final long id;
        private long lastNs;
        private long mNs;
        private int misses;
        private float my;
        private int oN;
        private long oNs;
        private float rowEv;
        private float score;
        private int velN;
        private float vx;
        private float vy;
        private int weak;
        private float x0;
        private float x1;
        private float y0;
        private float y1;
        private float gx = Float.NaN;
        private final float[] votes = new float[3];
        private final float[] kinds = new float[3];
        private final float[] lampVotes = new float[3];
        private final float[] hVotes = new float[4];
        private final float[] oHist = new float[16];
        private float mx = Float.NaN;

        public Track(long id, int cls) {
            this.id = id;
            this.cls = cls;
        }

        public final int getCls() {
            return this.cls;
        }

        public final long getId() {
            return this.id;
        }

        public final void setCls(int i) {
            this.cls = i;
        }

        public final float getScore() {
            return this.score;
        }

        public final float getX0() {
            return this.x0;
        }

        public final float getX1() {
            return this.x1;
        }

        public final float getY0() {
            return this.y0;
        }

        public final float getY1() {
            return this.y1;
        }

        public final void setScore(float f) {
            this.score = f;
        }

        public final void setX0(float f) {
            this.x0 = f;
        }

        public final void setX1(float f) {
            this.x1 = f;
        }

        public final void setY0(float f) {
            this.y0 = f;
        }

        public final void setY1(float f) {
            this.y1 = f;
        }

        public final float getVx() {
            return this.vx;
        }

        public final float getVy() {
            return this.vy;
        }

        public final void setVx(float f) {
            this.vx = f;
        }

        public final void setVy(float f) {
            this.vy = f;
        }

        public final int getHits() {
            return this.hits;
        }

        public final long getLastNs() {
            return this.lastNs;
        }

        public final int getMisses() {
            return this.misses;
        }

        public final void setHits(int i) {
            this.hits = i;
        }

        public final void setLastNs(long j) {
            this.lastNs = j;
        }

        public final void setMisses(int i) {
            this.misses = i;
        }

        public final int getWeak() {
            return this.weak;
        }

        public final void setWeak(int i) {
            this.weak = i;
        }

        public final float getGw() {
            return this.gw;
        }

        public final float getGx() {
            return this.gx;
        }

        public final float getGy() {
            return this.gy;
        }

        public final void setGw(float f) {
            this.gw = f;
        }

        public final void setGx(float f) {
            this.gx = f;
        }

        public final void setGy(float f) {
            this.gy = f;
        }

        public final float[] getVotes() {
            return this.votes;
        }

        public final float[] getKinds() {
            return this.kinds;
        }

        public final float[] getLampVotes() {
            return this.lampVotes;
        }

        public final float[] getHVotes() {
            return this.hVotes;
        }

        public final float getAxisEv() {
            return this.axisEv;
        }

        public final void setAxisEv(float f) {
            this.axisEv = f;
        }

        public final float getRowEv() {
            return this.rowEv;
        }

        public final void setRowEv(float f) {
            this.rowEv = f;
        }

        public final Boolean getAlong() {
            return this.along;
        }

        public final void setAlong(Boolean bool) {
            this.along = bool;
        }

        public final float[] getOHist() {
            return this.oHist;
        }

        public final long getONs() {
            return this.oNs;
        }

        public final void setONs(long j) {
            this.oNs = j;
        }

        public final int getON() {
            return this.oN;
        }

        public final void setON(int i) {
            this.oN = i;
        }

        public final float getGvx() {
            return this.gvx;
        }

        public final float getGvy() {
            return this.gvy;
        }

        public final void setGvx(float f) {
            this.gvx = f;
        }

        public final void setGvy(float f) {
            this.gvy = f;
        }

        public final int getVelN() {
            return this.velN;
        }

        public final void setVelN(int i) {
            this.velN = i;
        }

        public final long getMNs() {
            return this.mNs;
        }

        public final float getMx() {
            return this.mx;
        }

        public final float getMy() {
            return this.my;
        }

        public final void setMNs(long j) {
            this.mNs = j;
        }

        public final void setMx(float f) {
            this.mx = f;
        }

        public final void setMy(float f) {
            this.my = f;
        }

        public final float getCx() {
            return (this.x0 + this.x1) / 2.0f;
        }

        public final float getCy() {
            return (this.y0 + this.y1) / 2.0f;
        }
    }

    private final int group(int c) {
        switch (c) {
            case 0:
                return 1;
            case 1:
            case 3:
                return 2;
            case 2:
            case 5:
            case 7:
                return 0;
            case 4:
            case 6:
            default:
                return c + 3;
        }
    }

    private final boolean isVehicle(int c) {
        return c == 2 || c == 5 || c == 7;
    }

    private final int need(int c) {
        switch (c) {
            case 0:
                return 4;
            case 2:
            case 5:
            case 7:
                return 2;
            default:
                return 3;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:253:0x0691  */
    /* JADX WARN: Removed duplicated region for block: B:256:0x0694 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00c8  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00cb A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final void track(Source src, List<Det> all, final Frame f) {
        float f2;
        String str;
        String str2;
        float f3;
        String str3;
        List byAge;
        boolean z;
        Iterator it;
        boolean still;
        Object element$iv;
        char c;
        Iterator it2;
        String str4;
        Iterable $this$filter$iv;
        boolean z2;
        boolean z3;
        Collection destination$iv$iv = new ArrayList();
        Iterator it3 = all.iterator();
        while (true) {
            f2 = 0.35f;
            if (!it3.hasNext()) {
                break;
            }
            Object element$iv$iv = it3.next();
            if (((Det) element$iv$iv).getScore() >= 0.35f) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        List<Det> raw = (List) destination$iv$iv;
        List<Det> $this$filter$iv2 = all;
        Collection destination$iv$iv2 = new ArrayList();
        for (Object element$iv$iv2 : $this$filter$iv2) {
            Det w = (Det) element$iv$iv2;
            if (w.getScore() < f2) {
                List $this$none$iv = raw;
                if (($this$none$iv instanceof Collection) && $this$none$iv.isEmpty()) {
                    $this$filter$iv = $this$filter$iv2;
                    z3 = true;
                } else {
                    Iterator it4 = $this$none$iv.iterator();
                    while (true) {
                        if (!it4.hasNext()) {
                            $this$filter$iv = $this$filter$iv2;
                            z3 = true;
                            break;
                        }
                        Object element$iv2 = it4.next();
                        Det it5 = (Det) element$iv2;
                        $this$filter$iv = $this$filter$iv2;
                        if (group(it5.getCls()) == group(w.getCls()) && iou(it5, w) > 0.45f) {
                            z3 = false;
                            break;
                        }
                        $this$filter$iv2 = $this$filter$iv;
                    }
                }
                if (z3) {
                    z2 = true;
                    if (!z2) {
                        destination$iv$iv2.add(element$iv$iv2);
                    }
                    $this$filter$iv2 = $this$filter$iv;
                    f2 = 0.35f;
                }
            } else {
                $this$filter$iv = $this$filter$iv2;
            }
            z2 = false;
            if (!z2) {
            }
            $this$filter$iv2 = $this$filter$iv;
            f2 = 0.35f;
        }
        List weak = (List) destination$iv$iv2;
        long now = System.nanoTime();
        float dt = src.getLastTrackNs() == 0 ? 0.05f : RangesKt.coerceIn((now - src.getLastTrackNs()) / 1.0E9f, 0.01f, 0.5f);
        src.setLastTrackNs(now);
        ArrayList tracks = src.getTracks();
        ArrayList pairs = new ArrayList();
        Iterator<Track> it6 = tracks.iterator();
        String str5 = "iterator(...)";
        Intrinsics.checkNotNullExpressionValue(it6, "iterator(...)");
        while (true) {
            str = "next(...)";
            if (!it6.hasNext()) {
                break;
            }
            Track next = it6.next();
            Intrinsics.checkNotNullExpressionValue(next, "next(...)");
            Track tr = next;
            float tw = tr.getX1() - tr.getX0();
            float th = tr.getY1() - tr.getY0();
            float px = tr.getCx() + (tr.getVx() * dt);
            float py = tr.getCy() + (tr.getVy() * dt);
            float norm = Math.max(Math.max(tw, th), 24.0f);
            Iterator it7 = raw.iterator();
            while (it7.hasNext()) {
                Iterator<Track> it8 = it6;
                Det d = (Det) it7.next();
                float th2 = th;
                Iterator it9 = it7;
                if (group(d.getCls()) == group(tr.getCls())) {
                    float dw = d.getX1() - d.getX0();
                    float ratio = dw / Math.max(tw, 1.0f);
                    if (ratio < 0.5f) {
                        th = th2;
                        it6 = it8;
                        it7 = it9;
                        now = now;
                    } else if (ratio > 2.0f) {
                        th = th2;
                        it6 = it8;
                        it7 = it9;
                    } else {
                        long now2 = now;
                        ArrayList tracks2 = tracks;
                        float dist = ((float) Math.hypot(((d.getX0() + d.getX1()) / 2.0f) - px, ((d.getY0() + d.getY1()) / 2.0f) - py)) / norm;
                        if (dist < 0.9f) {
                            pairs.add(new Detector$track$Pair(tr, d, dist));
                        }
                        tracks = tracks2;
                        th = th2;
                        it6 = it8;
                        it7 = it9;
                        now = now2;
                    }
                } else {
                    th = th2;
                    it6 = it8;
                    it7 = it9;
                    now = now;
                }
            }
            now = now;
        }
        long now3 = now;
        ArrayList tracks3 = tracks;
        ArrayList $this$sortBy$iv = pairs;
        if ($this$sortBy$iv.size() > 1) {
            CollectionsKt.sortWith($this$sortBy$iv, new Comparator() { // from class: com.starpilot.starview.Detector$track$$inlined$sortBy$1
                /* JADX WARN: Multi-variable type inference failed */
                @Override // java.util.Comparator
                public final int compare(T t, T t2) {
                    Detector$track$Pair it10 = (Detector$track$Pair) t;
                    Detector$track$Pair it11 = (Detector$track$Pair) t2;
                    return ComparisonsKt.compareValues(Float.valueOf(it10.getCost()), Float.valueOf(it11.getCost()));
                }
            });
        }
        HashSet usedT = new HashSet();
        HashSet usedD = new HashSet();
        Iterator it10 = pairs.iterator();
        Intrinsics.checkNotNullExpressionValue(it10, "iterator(...)");
        while (it10.hasNext()) {
            Object next2 = it10.next();
            Intrinsics.checkNotNullExpressionValue(next2, str);
            Detector$track$Pair pr = (Detector$track$Pair) next2;
            if (!usedT.contains(pr.getTr()) && !usedD.contains(pr.getD())) {
                track$take(usedT, usedD, dt, now3, this, pr.getTr(), pr.getD(), 0.7f, true);
                tracks3 = tracks3;
                pairs = pairs;
                str = str;
                str5 = str5;
            }
        }
        String str6 = str;
        String str7 = str5;
        ArrayList tracks4 = tracks3;
        long now4 = now3;
        if (weak.isEmpty()) {
            str2 = str6;
            f3 = 1.0f;
            str3 = str7;
        } else {
            Iterator<Track> it11 = tracks4.iterator();
            String str8 = str7;
            Intrinsics.checkNotNullExpressionValue(it11, str8);
            while (it11.hasNext()) {
                Track next3 = it11.next();
                String str9 = str6;
                Intrinsics.checkNotNullExpressionValue(next3, str9);
                Track tr2 = next3;
                if (usedT.contains(tr2) || tr2.getHits() < need(tr2.getCls())) {
                    weak = weak;
                    str6 = str9;
                    it11 = it11;
                } else if (tr2.getWeak() >= 6) {
                    str6 = str9;
                } else {
                    float tw2 = tr2.getX1() - tr2.getX0();
                    float th3 = tr2.getY1() - tr2.getY0();
                    float px2 = tr2.getCx() + (tr2.getVx() * dt);
                    float py2 = tr2.getCy() + (tr2.getVy() * dt);
                    float norm2 = Math.max(Math.max(tw2, th3), 24.0f);
                    Iterator it12 = weak.iterator();
                    Det best = null;
                    float bc = 0.6f;
                    while (it12.hasNext()) {
                        Det d2 = (Det) it12.next();
                        if (usedD.contains(d2)) {
                            it2 = it12;
                            str4 = str9;
                        } else if (group(d2.getCls()) != group(tr2.getCls())) {
                            it2 = it12;
                            str4 = str9;
                        } else {
                            float ratio2 = (d2.getX1() - d2.getX0()) / Math.max(tw2, 1.0f);
                            if (ratio2 < 0.67f) {
                                it2 = it12;
                                str4 = str9;
                            } else if (ratio2 > 1.5f) {
                                it2 = it12;
                                str4 = str9;
                            } else {
                                str4 = str9;
                                it2 = it12;
                                float c2 = ((float) Math.hypot(((d2.getX0() + d2.getX1()) / 2.0f) - px2, ((d2.getY0() + d2.getY1()) / 2.0f) - py2)) / norm2;
                                if (c2 < bc) {
                                    bc = c2;
                                    best = d2;
                                    it12 = it2;
                                    str9 = str4;
                                }
                            }
                        }
                        it12 = it2;
                        str9 = str4;
                    }
                    String str10 = str9;
                    if (best != null) {
                        track$take(usedT, usedD, dt, now4, this, tr2, best, 0.5f, false);
                        str8 = str8;
                        weak = weak;
                        str6 = str10;
                        it11 = it11;
                    } else {
                        weak = weak;
                        str6 = str10;
                        it11 = it11;
                    }
                }
            }
            str2 = str6;
            f3 = 1.0f;
            str3 = str8;
        }
        Iterator<Track> it13 = tracks4.iterator();
        Intrinsics.checkNotNullExpressionValue(it13, str3);
        while (it13.hasNext()) {
            Track next4 = it13.next();
            Intrinsics.checkNotNullExpressionValue(next4, str2);
            Track tr3 = next4;
            if (!usedT.contains(tr3)) {
                tr3.setMisses(tr3.getMisses() + 1);
                float sx = tr3.getVx() * dt;
                float sy = tr3.getVy() * dt;
                tr3.setX0(tr3.getX0() + sx);
                tr3.setX1(tr3.getX1() + sx);
                tr3.setY0(tr3.getY0() + sy);
                tr3.setY1(tr3.getY1() + sy);
                tr3.setVx(tr3.getVx() * 0.9f);
                tr3.setVy(tr3.getVy() * 0.9f);
            }
        }
        for (Det d3 : raw) {
            if (!usedD.contains(d3)) {
                long j = this.nextId;
                this.nextId = 1 + j;
                Track it14 = new Track(j, d3.getCls());
                it14.setX0(d3.getX0());
                it14.setY0(d3.getY0());
                it14.setX1(d3.getX1());
                it14.setY1(d3.getY1());
                it14.setScore(d3.getScore());
                it14.setHits(1);
                long now5 = now4;
                it14.setLastNs(now5);
                if (isVehicle(d3.getCls())) {
                    float[] kinds = it14.getKinds();
                    switch (d3.getCls()) {
                        case 5:
                            c = 2;
                            break;
                        case 6:
                        default:
                            c = 0;
                            break;
                        case 7:
                            c = 1;
                            break;
                    }
                    kinds[c] = f3;
                }
                tracks4.add(it14);
                now4 = now5;
            }
        }
        long now6 = now4;
        Telemetry telemetry = this.telemetry;
        final boolean still2 = (telemetry != null ? telemetry.getVEgo() : 0.0f) < f3;
        CollectionsKt.removeAll((List) tracks4, new Function1() { // from class: com.starpilot.starview.Detector$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                boolean track$lambda$28;
                track$lambda$28 = Detector.track$lambda$28(Detector.this, still2, f, (Detector.Track) obj);
                return Boolean.valueOf(track$lambda$28);
            }
        });
        final Comparator comparator = new Comparator() { // from class: com.starpilot.starview.Detector$track$$inlined$compareByDescending$1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.Comparator
            public final int compare(T t, T t2) {
                Detector.Track it15 = (Detector.Track) t2;
                Integer valueOf = Integer.valueOf(it15.getHits());
                Detector.Track it16 = (Detector.Track) t;
                return ComparisonsKt.compareValues(valueOf, Integer.valueOf(it16.getHits()));
            }
        };
        List byAge2 = CollectionsKt.sortedWith(tracks4, new Comparator() { // from class: com.starpilot.starview.Detector$track$$inlined$thenBy$1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.Comparator
            public final int compare(T t, T t2) {
                int previousCompare = comparator.compare(t, t2);
                if (previousCompare != 0) {
                    return previousCompare;
                }
                Detector.Track it15 = (Detector.Track) t;
                Long valueOf = Long.valueOf(it15.getId());
                Detector.Track it16 = (Detector.Track) t2;
                return ComparisonsKt.compareValues(valueOf, Long.valueOf(it16.getId()));
            }
        });
        ArrayList kept = new ArrayList();
        Iterator it15 = byAge2.iterator();
        while (it15.hasNext()) {
            Track t = (Track) it15.next();
            ArrayList $this$firstOrNull$iv = kept;
            Iterator it16 = $this$firstOrNull$iv.iterator();
            while (true) {
                if (it16.hasNext()) {
                    element$iv = it16.next();
                    Track it17 = (Track) element$iv;
                    it = it15;
                    still = still2;
                    if (!(group(it17.getCls()) == group(t.getCls()) && boxIou(it17, t) > 0.6f)) {
                        it15 = it;
                        still2 = still;
                    }
                } else {
                    it = it15;
                    still = still2;
                    element$iv = null;
                }
            }
            Track k = (Track) element$iv;
            if (k == null) {
                kept.add(t);
                it15 = it;
                still2 = still;
            } else if (k.getMisses() <= 0 || t.getMisses() != 0) {
                it15 = it;
                still2 = still;
            } else {
                k.setX0(t.getX0());
                k.setY0(t.getY0());
                k.setX1(t.getX1());
                k.setY1(t.getY1());
                k.setVx(t.getVx());
                k.setVy(t.getVy());
                k.setMisses(0);
                k.setLastNs(t.getLastNs());
                it15 = it;
                still2 = still;
                now6 = now6;
            }
        }
        if (kept.size() < tracks4.size()) {
            tracks4.retainAll(CollectionsKt.toSet(kept));
        }
        ArrayList $this$filter$iv3 = tracks4;
        int $i$f$filter = 0;
        Collection destination$iv$iv3 = new ArrayList();
        for (Object element$iv$iv3 : $this$filter$iv3) {
            Track it18 = (Track) element$iv$iv3;
            Iterable $this$filter$iv4 = $this$filter$iv3;
            int hits = it18.getHits();
            int $i$f$filter2 = $i$f$filter;
            int $i$f$filter3 = it18.getCls();
            if (hits >= need($i$f$filter3)) {
                byAge = byAge2;
                if (it18.getMisses() <= (it18.getHits() >= 8 ? 7 : 4)) {
                    z = true;
                    if (!z) {
                        destination$iv$iv3.add(element$iv$iv3);
                    }
                    $this$filter$iv3 = $this$filter$iv4;
                    $i$f$filter = $i$f$filter2;
                    byAge2 = byAge;
                }
            } else {
                byAge = byAge2;
            }
            z = false;
            if (!z) {
            }
            $this$filter$iv3 = $this$filter$iv4;
            $i$f$filter = $i$f$filter2;
            byAge2 = byAge;
        }
        List shown = (List) destination$iv$iv3;
        List $this$map$iv = shown;
        Collection destination$iv$iv4 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        for (Object item$iv$iv : $this$map$iv) {
            Track it19 = (Track) item$iv$iv;
            destination$iv$iv4.add(new Det(vehicleCls(it19), it19.getScore(), it19.getX0(), it19.getY0(), it19.getX1(), it19.getY1(), it19.getId()));
            $this$map$iv = $this$map$iv;
        }
        src.setDets((List) destination$iv$iv4);
        groundObjects(src, shown, f);
        mergeObjects();
        if (src.getWide()) {
            return;
        }
        try {
            ideasFrame(shown, f);
        } catch (Exception e) {
        }
    }

    private static final void track$take(HashSet<Track> hashSet, HashSet<Det> hashSet2, float dt, long now, Detector this$0, Track tr, Det d, float a, boolean strong) {
        hashSet.add(tr);
        hashSet2.add(d);
        float ncx = (d.getX0() + d.getX1()) / 2.0f;
        float ncy = (d.getY0() + d.getY1()) / 2.0f;
        float mdt = tr.getLastNs() == 0 ? dt : RangesKt.coerceIn((now - tr.getLastNs()) / 1.0E9f, 0.01f, 1.0f);
        float ocx = tr.getCx();
        float ocy = tr.getCy();
        tr.setX0(tr.getX0() + ((d.getX0() - tr.getX0()) * a));
        tr.setY0(tr.getY0() + ((d.getY0() - tr.getY0()) * a));
        tr.setX1(tr.getX1() + ((d.getX1() - tr.getX1()) * a));
        tr.setY1(tr.getY1() + ((d.getY1() - tr.getY1()) * a));
        if (tr.getHits() > 0) {
            tr.setVx(tr.getVx() + ((((ncx - ocx) / mdt) - tr.getVx()) * 0.5f));
            tr.setVy(tr.getVy() + ((((ncy - ocy) / mdt) - tr.getVy()) * 0.5f));
        }
        tr.setMisses(0);
        tr.setLastNs(now);
        char c = 1;
        if (!strong) {
            tr.setWeak(tr.getWeak() + 1);
            return;
        }
        tr.setScore(d.getScore());
        tr.setHits(tr.getHits() + 1);
        tr.setWeak(0);
        if (!this$0.isVehicle(d.getCls())) {
            tr.setCls(d.getCls());
            return;
        }
        for (int k = 0; k < 3; k++) {
            float[] kinds = tr.getKinds();
            kinds[k] = kinds[k] * 0.95f;
        }
        float[] kinds2 = tr.getKinds();
        switch (d.getCls()) {
            case 5:
                c = 2;
                break;
            case 6:
            default:
                c = 0;
                break;
            case 7:
                break;
        }
        kinds2[c] = kinds2[c] + 1.0f;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean track$lambda$28(Detector this$0, boolean $still, Frame $f, Track it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return it.getMisses() > (it.getHits() < this$0.need(it.getCls()) ? 1 : $still ? 12 : 10) || it.getX1() < 0.0f || it.getX0() > ((float) $f.getW()) || it.getY0() > ((float) $f.getH());
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x007f A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x002f A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final void ideasFrame(List<Track> shown, Frame f) {
        boolean z;
        long now;
        boolean z2;
        Object minElem$iv;
        Track it;
        long now2 = System.currentTimeMillis();
        LeadLamps ll = Ideas.INSTANCE.getSense().getLeadLamps();
        if (ll.getEnabled()) {
            List<Track> $this$filter$iv = shown;
            Collection destination$iv$iv = new ArrayList();
            for (Object element$iv$iv : $this$filter$iv) {
                Track it2 = (Track) element$iv$iv;
                if (isVehicle(it2.getCls()) && !Float.isNaN(it2.getGx())) {
                    float gx = it2.getGx();
                    if ((3.0f <= gx && gx <= 50.0f) && Math.abs(it2.getGy()) < 1.5f && it2.getMisses() == 0) {
                        it = 1;
                        if (it == null) {
                            destination$iv$iv.add(element$iv$iv);
                        }
                    }
                }
                it = null;
                if (it == null) {
                }
            }
            Iterable $this$minByOrNull$iv = (List) destination$iv$iv;
            Iterator iterator$iv = $this$minByOrNull$iv.iterator();
            if (iterator$iv.hasNext()) {
                minElem$iv = iterator$iv.next();
                if (iterator$iv.hasNext()) {
                    Track it3 = (Track) minElem$iv;
                    float minValue$iv = it3.getGx();
                    do {
                        Object e$iv = iterator$iv.next();
                        Track it4 = (Track) e$iv;
                        float v$iv = it4.getGx();
                        if (Float.compare(minValue$iv, v$iv) > 0) {
                            minElem$iv = e$iv;
                            minValue$iv = v$iv;
                        }
                    } while (iterator$iv.hasNext());
                }
            } else {
                minElem$iv = null;
            }
            Track lead = (Track) minElem$iv;
            if (lead != null) {
                Pair<Float, Float> sideLamps = sideLamps(f, lead.getX0(), lead.getY0(), lead.getX1(), lead.getY1());
                float l = sideLamps.component1().floatValue();
                float r = sideLamps.component2().floatValue();
                z = false;
                ll.feed(lead.getId(), now2, l, r);
                now2 = now2;
            } else {
                z = false;
            }
        } else {
            z = false;
        }
        if (WantIdeas.INSTANCE.getSchoolBus()) {
            for (Track b : shown) {
                if (b.getCls() == 5 && !Float.isNaN(b.getGx())) {
                    float gx2 = b.getGx();
                    if (((3.0f > gx2 || gx2 > 70.0f) ? z : true) && Math.abs(b.getGy()) <= 8.0f) {
                        if (b.getMisses() <= 0) {
                            Triple<Float, Float, Float> busLook = busLook(f, b.getX0(), b.getY0(), b.getX1(), b.getY1());
                            float yf = busLook.component1().floatValue();
                            float rl = busLook.component2().floatValue();
                            float rr = busLook.component3().floatValue();
                            this.schoolBus.feed(b.getId(), now2, b.getGx(), yf, rl, rr);
                            now2 = now2;
                            z = false;
                        }
                    }
                }
                z = false;
            }
        }
        long now3 = now2;
        if (WantIdeas.INSTANCE.getWipers()) {
            wiperWatch(f, now3);
        }
        boolean wantShadow = ShadowWatch.INSTANCE.getWanted();
        DayView dv = Ideas.INSTANCE.getMemory().getDayView();
        if (wantShadow || (dv.getWanted() && now3 - this.ideasDayMs > 1000)) {
            float[] grid = new float[32 * 9];
            for (int j = 0; j < 9; j++) {
                for (int i = 0; i < 32; i++) {
                    int u = RangesKt.coerceIn((int) (((i + 0.5f) / 32) * f.getW()), 0, f.getW() - 1);
                    int v = RangesKt.coerceIn((int) ((f.getH() / 2) + ((((j + 0.5f) / 9) * f.getH()) / 2)), 0, f.getH() - 1);
                    grid[(j * 32) + i] = f.getY()[(f.getYStride() * v) + u] & 255;
                }
            }
            if (wantShadow) {
                ShadowWatch shadowWatch = ShadowWatch.INSTANCE;
                List<Track> $this$any$iv = shown;
                if (!($this$any$iv instanceof Collection) || !$this$any$iv.isEmpty()) {
                    Iterator it5 = $this$any$iv.iterator();
                    while (true) {
                        if (!it5.hasNext()) {
                            z2 = false;
                            break;
                        }
                        Object element$iv = it5.next();
                        Track it6 = (Track) element$iv;
                        Track it7 = it6.getCls() == 0 ? 1 : null;
                        if (it7 != null) {
                            z2 = true;
                            break;
                        }
                    }
                } else {
                    z2 = false;
                }
                now = now3;
                shadowWatch.feed(grid, 32, z2, now3);
            } else {
                now = now3;
            }
            if (!dv.getWanted() || now - this.ideasDayMs <= 1000) {
                return;
            }
            this.ideasDayMs = now;
            Bitmap bmp = colour(f);
            try {
                dv.feed(bmp, (float) ArraysKt.average(grid), now);
            } finally {
                bmp.recycle();
            }
        }
    }

    private final Triple<Float, Float, Float> busLook(Frame f, float u0, float v0, float u1, float v1) {
        int yel = 0;
        int n = 0;
        for (int j = 0; j < 6; j++) {
            for (int i = 0; i < 6; i++) {
                int u = RangesKt.coerceIn((int) (u0 + ((u1 - u0) * (((i * 0.8f) / 5.0f) + 0.1f))), 0, f.getW() - 1);
                int v = RangesKt.coerceIn((int) (v0 + ((v1 - v0) * (((j * 0.5f) / 5.0f) + 0.3f))), 0, f.getH() - 1);
                Triple<Integer, Integer, Integer> rgb = rgb(f, u, v);
                int r = rgb.component1().intValue();
                int g = rgb.component2().intValue();
                int b = rgb.component3().intValue();
                n++;
                if (r > 140 && g > 105 && b < 100 && r - b > 70) {
                    yel++;
                }
            }
        }
        return new Triple<>(Float.valueOf(yel / n), Float.valueOf(busLook$red(u0, u1, f, v0, v1, this, 0.02f, 0.25f)), Float.valueOf(busLook$red(u0, u1, f, v0, v1, this, 0.75f, 0.98f)));
    }

    private static final float busLook$red(float $u0, float $u1, Frame $f, float $v0, float $v1, Detector this$0, float fx0, float fx1) {
        float best = 0.0f;
        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 4; i++) {
                int u = RangesKt.coerceIn((int) ((($u1 - $u0) * (fx0 + (((fx1 - fx0) * i) / 3.0f))) + $u0), 0, $f.getW() - 1);
                int v = RangesKt.coerceIn((int) ((($v1 - $v0) * (((j * 0.12f) / 2.0f) + 0.03f)) + $v0), 0, $f.getH() - 1);
                Triple<Integer, Integer, Integer> rgb = this$0.rgb($f, u, v);
                int r = rgb.component1().intValue();
                int g = rgb.component2().intValue();
                int b = rgb.component3().intValue();
                if (r - ((g + b) / 2) > 50) {
                    best = Math.max(best, r);
                }
            }
        }
        return best;
    }

    private final Triple<Integer, Integer, Integer> rgb(Frame f, int u, int v) {
        int yy = (f.getY()[(f.getYStride() * v) + u] & 255) - 16;
        int k = ((v >> 1) * f.getUvStride()) + ((u >> 1) * f.getUvPixStride());
        int uu = (f.getU()[k] & 255) - 128;
        int vv = (f.getV()[k] & 255) - 128;
        int yv = yy * 298;
        return new Triple<>(Integer.valueOf(clamp((((vv * 409) + yv) + 128) >> 8)), Integer.valueOf(clamp((((yv - (uu * 100)) - (vv * 208)) + 128) >> 8)), Integer.valueOf(clamp((((uu * 516) + yv) + 128) >> 8)));
    }

    private final void wiperWatch(Frame f, long now) {
        boolean z;
        float[] g = new float[24 * 14];
        for (int j = 0; j < 14; j++) {
            for (int i = 0; i < 24; i++) {
                g[(j * 24) + i] = f.getY()[(RangesKt.coerceIn((int) (((j + 0.5f) / 14) * f.getH()), 0, f.getH() - 1) * f.getYStride()) + RangesKt.coerceIn((int) (((i + 0.5f) / 24) * f.getW()), 0, f.getW() - 1)] & 255;
            }
        }
        float[] it = this.wiperAvg;
        if (it == null) {
            it = Arrays.copyOf(g, g.length);
            Intrinsics.checkNotNullExpressionValue(it, "copyOf(...)");
            this.wiperAvg = it;
        }
        int dark = 0;
        int length = g.length;
        for (int k = 0; k < length; k++) {
            if (g[k] < it[k] * 0.6f && it[k] > 30.0f) {
                dark++;
            }
        }
        float share = dark / g.length;
        boolean isDark = 0.08f <= share && share <= 0.6f;
        if (isDark && !this.wiperDark) {
            this.wiperTimes.addLast(Long.valueOf(now));
            while (this.wiperTimes.size() > 6) {
                this.wiperTimes.removeFirst();
            }
        }
        this.wiperDark = isDark;
        if (!isDark) {
            int length2 = g.length;
            for (int k2 = 0; k2 < length2; k2++) {
                it[k2] = it[k2] + ((g[k2] - it[k2]) * 0.1f);
            }
        }
        List ts = CollectionsKt.toList(this.wiperTimes);
        if (ts.size() < 3 || now - ((Number) ts.get(ts.size() - 3)).longValue() >= 30000) {
            return;
        }
        Iterable $this$all$iv = RangesKt.until(1, ts.size());
        if (!($this$all$iv instanceof Collection) || !((Collection) $this$all$iv).isEmpty()) {
            Iterator it2 = $this$all$iv.iterator();
            while (true) {
                if (it2.hasNext()) {
                    int element$iv = ((IntIterator) it2).nextInt();
                    long longValue = ((Number) ts.get(element$iv)).longValue() - ((Number) ts.get(element$iv - 1)).longValue();
                    if (((((800L > longValue ? 1 : (800L == longValue ? 0 : -1)) <= 0 && (longValue > 8001L ? 1 : (longValue == 8001L ? 0 : -1)) < 0) || element$iv < ts.size() + (-2)) ? 1 : 0) == 0) {
                        z = false;
                        break;
                    }
                } else {
                    z = true;
                    break;
                }
            }
        } else {
            z = true;
        }
        if (z) {
            Ideas.INSTANCE.getMore().setWipersAtMs(now);
        }
    }

    private final Pair<Float, Float> sideLamps(Frame f, float u0, float v0, float u1, float v1) {
        Detector detector = this;
        int i = 2;
        float[] res = new float[2];
        int i2 = 0;
        int[] iArr = {0, 2};
        int i3 = 0;
        while (true) {
            int i4 = 1;
            if (i3 < i) {
                int k = i3;
                int part = iArr[i3];
                float[] vals = new float[30];
                int n = 0;
                int j = 0;
                while (true) {
                    float f2 = 3.0f;
                    if (j < 5) {
                        int i5 = 0;
                        while (i5 < 6) {
                            int u = RangesKt.coerceIn((int) (u0 + ((u1 - u0) * ((part + (((i5 * 0.9f) / 5.0f) + 0.05f)) / f2))), i2, f.getW() - i4);
                            int v = RangesKt.coerceIn((int) (v0 + ((v1 - v0) * (((j * 0.4f) / 4.0f) + 0.25f))), i2, f.getH() - i4);
                            int yy = (f.getY()[(f.getYStride() * v) + u] & 255) - 16;
                            int uvI = ((v >> 1) * f.getUvStride()) + ((u >> 1) * f.getUvPixStride());
                            int uu = (f.getU()[uvI] & 255) - 128;
                            int vv = (f.getV()[uvI] & 255) - 128;
                            int u2 = yy * 298;
                            int[] iArr2 = iArr;
                            int r = detector.clamp((((vv * 409) + u2) + 128) >> 8);
                            int vv2 = uu * 516;
                            int bb = detector.clamp(((vv2 + u2) + 128) >> 8);
                            int n2 = n + 1;
                            vals[n] = (r - bb <= 25 || r <= 80) ? 0.0f : yy;
                            i5++;
                            i2 = 0;
                            i4 = 1;
                            f2 = 3.0f;
                            detector = this;
                            iArr = iArr2;
                            n = n2;
                        }
                        j++;
                        i2 = 0;
                        i4 = 1;
                        detector = this;
                    }
                }
                ArraysKt.sort(vals);
                res[k] = ((vals[29] + vals[28]) + vals[27]) / 3.0f;
                i3++;
                i = 2;
                i2 = 0;
                detector = this;
            } else {
                return new Pair<>(Float.valueOf(res[0]), Float.valueOf(res[1]));
            }
        }
    }

    private final int vehicleCls(Track tr) {
        if (!isVehicle(tr.getCls())) {
            return tr.getCls();
        }
        float[] k = tr.getKinds();
        float sum = k[0] + k[1] + k[2];
        if (k[2] <= 3.0f || k[2] <= sum * 0.6f) {
            return (k[1] <= 3.0f || k[1] <= 0.6f * sum) ? 2 : 7;
        }
        return 5;
    }

    private final boolean orientReady(Track tr) {
        if (!this.useOrient || tr.getON() < 3) {
            return false;
        }
        float[] h = tr.getOHist();
        float sum = 0.0f;
        int best = 0;
        int length = h.length;
        for (int k = 0; k < length; k++) {
            sum += h[k];
            if (h[k] > h[best]) {
                best = k;
            }
        }
        int k2 = h.length;
        float peak = h[best] + h[(best + 1) % k2] + h[((best + k2) - 1) % k2];
        return sum > 0.001f && peak / sum > 0.55f;
    }

    private final float orientHeading(Track tr) {
        float[] h = tr.getOHist();
        int nb = h.length;
        int best = 0;
        int length = h.length;
        for (int k = 0; k < length; k++) {
            if (h[k] > h[best]) {
                best = k;
            }
        }
        float sx = 0.0f;
        float sy = 0.0f;
        for (int d = -1; d < 2; d++) {
            int k2 = ((best + d) + nb) % nb;
            sx += h[k2] * ((float) Math.cos(CarOrient.Companion.getCENTRES()[k2]));
            sy += h[k2] * ((float) Math.sin(CarOrient.Companion.getCENTRES()[k2]));
        }
        return atan2(sy, sx);
    }

    /* compiled from: Detector.kt */
    private static final class Pend {
        private final int cls;
        private final int lamp;
        private final boolean measured;
        private final boolean moving;
        private final float speed;
        private final Track tr;

        public Pend(Track tr, int cls, int lamp, boolean moving, float speed, boolean measured) {
            Intrinsics.checkNotNullParameter(tr, "tr");
            this.tr = tr;
            this.cls = cls;
            this.lamp = lamp;
            this.moving = moving;
            this.speed = speed;
            this.measured = measured;
        }

        public final int getCls() {
            return this.cls;
        }

        public final int getLamp() {
            return this.lamp;
        }

        public final boolean getMeasured() {
            return this.measured;
        }

        public final boolean getMoving() {
            return this.moving;
        }

        public final float getSpeed() {
            return this.speed;
        }

        public final Track getTr() {
            return this.tr;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: Detector.kt */
    static final class OCand {
        private final float aspK;
        private final float bearing;
        private final Track tr;

        public OCand(Track tr, float bearing, float aspK) {
            Intrinsics.checkNotNullParameter(tr, "tr");
            this.tr = tr;
            this.bearing = bearing;
            this.aspK = aspK;
        }

        public final float getAspK() {
            return this.aspK;
        }

        public final float getBearing() {
            return this.bearing;
        }

        public final Track getTr() {
            return this.tr;
        }
    }

    /* compiled from: Detector.kt */
    private static final class Cue {
        private final int acrossBin;
        private final float acrossW;
        private final int alongBin;
        private final float ev;

        public Cue(float ev, int alongBin, int acrossBin, float acrossW) {
            this.ev = ev;
            this.alongBin = alongBin;
            this.acrossBin = acrossBin;
            this.acrossW = acrossW;
        }

        public final int getAcrossBin() {
            return this.acrossBin;
        }

        public final float getAcrossW() {
            return this.acrossW;
        }

        public final int getAlongBin() {
            return this.alongBin;
        }

        public final float getEv() {
            return this.ev;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:335:0x0a6d  */
    /* JADX WARN: Removed duplicated region for block: B:339:0x0aa1  */
    /* JADX WARN: Removed duplicated region for block: B:350:0x0b1a  */
    /* JADX WARN: Removed duplicated region for block: B:353:0x0b4a  */
    /* JADX WARN: Removed duplicated region for block: B:356:0x0b4d  */
    /* JADX WARN: Removed duplicated region for block: B:358:0x0b25  */
    /* JADX WARN: Removed duplicated region for block: B:363:0x0b08  */
    /* JADX WARN: Removed duplicated region for block: B:364:0x0a72  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x03f6  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x051d  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0547  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x05a2 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:81:0x0576 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0521  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final void groundObjects(Source src, List<Track> shown, Frame f) {
        List parked;
        float[] rowVote;
        Iterator it;
        float v;
        Boolean bool;
        int facing;
        float lm;
        int lampOut;
        float len;
        float v2;
        Telemetry t;
        float gy;
        Projection.Companion.GroundInverse hom;
        Track tr;
        float gx;
        float gw;
        int lamp;
        float hdgMeas;
        boolean moving;
        float gx2;
        float gy2;
        Track tr2;
        Detector detector = this;
        Telemetry telemetry = detector.telemetry;
        if (telemetry == null) {
            return;
        }
        Telemetry t2 = telemetry;
        if (!t2.getCalValid()) {
            src.setObjects(CollectionsKt.emptyList());
            src.setAtMs(System.currentTimeMillis());
            return;
        }
        Projection.Companion.GroundInverse hom2 = Projection.Companion.groundInverse(t2.getCalRpy(), t2.getCalHeight(), f.getW(), f.getH(), src.getWide());
        src.setFovTan(hom2.getCx() / hom2.getF());
        ArrayList out = new ArrayList();
        ArrayList pend = new ArrayList();
        ArrayList oCand = new ArrayList();
        Iterator<Track> it2 = shown.iterator();
        while (true) {
            if (!it2.hasNext()) {
                ArrayList out2 = out;
                Iterable oCand2 = oCand;
                if (!((Collection) oCand2).isEmpty()) {
                    long nowNs = System.nanoTime();
                    Iterable $this$filter$iv = oCand2;
                    Collection destination$iv$iv = new ArrayList();
                    for (Object element$iv$iv : $this$filter$iv) {
                        if (nowNs - ((OCand) element$iv$iv).getTr().getONs() > 150000000) {
                            destination$iv$iv.add(element$iv$iv);
                        }
                    }
                    Iterable $this$sortedBy$iv = (List) destination$iv$iv;
                    List<OCand> pick = CollectionsKt.take(CollectionsKt.sortedWith($this$sortedBy$iv, new Comparator() { // from class: com.starpilot.starview.Detector$groundObjects$$inlined$sortedBy$1
                        /* JADX WARN: Multi-variable type inference failed */
                        @Override // java.util.Comparator
                        public final int compare(T t3, T t4) {
                            Detector.OCand it3 = (Detector.OCand) t3;
                            Detector.OCand it4 = (Detector.OCand) t4;
                            return ComparisonsKt.compareValues(Long.valueOf(it3.getTr().getONs()), Long.valueOf(it4.getTr().getONs()));
                        }
                    }), 3);
                    for (OCand c : pick) {
                        Track tr3 = c.getTr();
                        float[] p = detector.orient.classify(f.getY(), f.getU(), f.getV(), f.getYStride(), f.getUvStride(), f.getUvPixStride(), f.getW(), f.getH(), tr3.getX0(), tr3.getY0(), tr3.getX1(), tr3.getY1(), c.getAspK());
                        if (p != null) {
                            tr3.setONs(nowNs);
                            tr3.setON(tr3.getON() + 1);
                            for (int k = 0; k < 16; k++) {
                                float[] oHist = tr3.getOHist();
                                oHist[k] = oHist[k] * 0.85f;
                            }
                            int k2 = 0;
                            while (k2 < 16) {
                                float h = detector.wrap(CarOrient.Companion.getCENTRES()[k2] + c.getBearing() + 1.5707964f);
                                float idx = ((((((h + 3.1415927f) / 6.2831855f) * 16) - 0.5f) % 16) + 16) % 16;
                                long nowNs2 = nowNs;
                                int lo = ((int) Math.floor(idx)) % 16;
                                int hi = (lo + 1) % 16;
                                float w = idx - ((float) Math.floor(idx));
                                float[] oHist2 = tr3.getOHist();
                                oHist2[lo] = oHist2[lo] + (p[k2] * (1 - w));
                                float[] oHist3 = tr3.getOHist();
                                oHist3[hi] = oHist3[hi] + (p[k2] * w);
                                k2++;
                                nowNs = nowNs2;
                            }
                        }
                    }
                }
                Iterable $this$filter$iv2 = pend;
                Collection destination$iv$iv2 = new ArrayList();
                for (Object element$iv$iv2 : $this$filter$iv2) {
                    Pend it3 = (Pend) element$iv$iv2;
                    if ((!detector.isVehicle(it3.getCls()) || it3.getCls() == 5 || it3.getMoving() || Float.isNaN(it3.getTr().getGx())) ? false : true) {
                        destination$iv$iv2.add(element$iv$iv2);
                    }
                }
                List<Pend> parked2 = (List) destination$iv$iv2;
                float[] rowVote2 = new float[parked2.size()];
                int size = parked2.size();
                for (int i = 0; i < size; i++) {
                    int size2 = parked2.size();
                    for (int j = i + 1; j < size2; j++) {
                        Track a = ((Pend) parked2.get(i)).getTr();
                        Track b = ((Pend) parked2.get(j)).getTr();
                        float dx = Math.abs(a.getGx() - b.getGx());
                        float dy = Math.abs(a.getGy() - b.getGy());
                        if (dx < 3.5f) {
                            if (1.8f <= dy && dy <= 4.3f) {
                                v2 = 1.0f;
                                rowVote2[i] = rowVote2[i] + v2;
                                rowVote2[j] = rowVote2[j] + v2;
                            }
                        }
                        if (dy < 1.5f) {
                            if (2.0f <= dx && dx <= 4.3f) {
                                v2 = -1.0f;
                                rowVote2[i] = rowVote2[i] + v2;
                                rowVote2[j] = rowVote2[j] + v2;
                            }
                        }
                        v2 = 0.0f;
                        rowVote2[i] = rowVote2[i] + v2;
                        rowVote2[j] = rowVote2[j] + v2;
                    }
                }
                Iterator it4 = pend.iterator();
                Intrinsics.checkNotNullExpressionValue(it4, "iterator(...)");
                while (it4.hasNext()) {
                    Object next = it4.next();
                    Intrinsics.checkNotNullExpressionValue(next, "next(...)");
                    Track tr4 = ((Pend) next).getTr();
                    tr4.setRowEv(tr4.getRowEv() * 0.93f);
                }
                int i2 = 0;
                for (Pend p2 : parked2) {
                    int i3 = i2;
                    i2++;
                    if (p2.getMeasured()) {
                        Track tr5 = p2.getTr();
                        tr5.setRowEv(tr5.getRowEv() + RangesKt.coerceIn(rowVote2[i3], -1.0f, 1.0f));
                    }
                }
                Iterator it5 = pend.iterator();
                Intrinsics.checkNotNullExpressionValue(it5, "iterator(...)");
                while (it5.hasNext()) {
                    Object next2 = it5.next();
                    Intrinsics.checkNotNullExpressionValue(next2, "next(...)");
                    Pend p3 = (Pend) next2;
                    Track tr6 = p3.getTr();
                    int cls = p3.getCls();
                    boolean moving2 = p3.getMoving();
                    float speed = p3.getSpeed();
                    p3.getLamp();
                    float[] hv = tr6.getHVotes();
                    float b0 = detector.atan2(tr6.getGy(), tr6.getGx());
                    if (moving2) {
                        parked = parked2;
                        v = detector.atan2(tr6.getGvy(), tr6.getGvx());
                        rowVote = rowVote2;
                        it = it5;
                        float sn = ((float) Math.rint(v / 1.5707964f)) * 1.5707964f;
                        if (Math.abs(detector.wrap(v - sn)) < 0.4f) {
                            v = detector.wrap(sn);
                        }
                    } else {
                        parked = parked2;
                        rowVote = rowVote2;
                        it = it5;
                        if (detector.isVehicle(cls) && cls != 5 && detector.orientReady(tr6)) {
                            v = detector.orientHeading(tr6);
                        } else if (!detector.isVehicle(cls) || hv[0] + hv[1] + hv[2] + hv[3] <= 2.5f) {
                            v = Float.NaN;
                        } else {
                            float score = tr6.getAxisEv() + (tr6.getRowEv() * 1.5f);
                            Boolean prev = tr6.getAlong();
                            if (score > 0.6f) {
                                bool = true;
                            } else if (score < -0.6f) {
                                bool = false;
                            } else if (prev == null) {
                                bool = Boolean.valueOf(Math.abs(tr6.getGy()) < 8.0f);
                            } else {
                                bool = prev;
                            }
                            tr6.setAlong(bool);
                            v = Intrinsics.areEqual((Object) tr6.getAlong(), (Object) true) ? hv[0] >= hv[2] ? 0.0f : 3.1415927f : hv[1] >= hv[3] ? 1.5707964f : -1.5707964f;
                        }
                    }
                    if (!Float.isNaN(v) && detector.isVehicle(cls)) {
                        float rel = Math.abs(detector.wrap(v - b0));
                        int face = (rel <= 1.1f || rel >= 2.05f) ? rel >= 2.05f ? 1 : 0 : 2;
                        int k3 = 0;
                        while (true) {
                            float rel2 = rel;
                            if (k3 < 3) {
                                float[] votes = tr6.getVotes();
                                votes[k3] = votes[k3] * 0.97f;
                                k3++;
                                rel = rel2;
                            } else {
                                float[] votes2 = tr6.getVotes();
                                votes2[face] = votes2[face] + 1.0f;
                            }
                        }
                    }
                    float[] v3 = tr6.getVotes();
                    if (v3[2] > 3.0f && v3[2] > Math.max(v3[0], v3[1]) * 1.5f) {
                        facing = 2;
                        float[] lv = tr6.getLampVotes();
                        lm = Math.max(lv[0], Math.max(lv[1], lv[2]));
                        if (lm >= 0.8f) {
                            lampOut = -1;
                        } else if (lm == lv[0]) {
                            lampOut = 0;
                        } else {
                            lampOut = (lm > lv[1] ? 1 : (lm == lv[1] ? 0 : -1)) == 0 ? 1 : 2;
                            float ccx = tr6.getGx();
                            float ccy = tr6.getGy();
                            if (detector.isVehicle(cls)) {
                                float b2 = detector.atan2(tr6.getGy(), tr6.getGx());
                                float rel3 = Float.isNaN(v) ? 0.0f : detector.wrap(v - b2);
                                switch (cls) {
                                    case 5:
                                        len = 11.0f;
                                        break;
                                    case 6:
                                    default:
                                        len = 4.7f;
                                        break;
                                    case 7:
                                        len = 5.8f;
                                        break;
                                }
                                float wid = cls == 5 ? 2.5f : 1.9f;
                                float depth = ((Math.abs((float) Math.cos(rel3)) * len) + (Math.abs((float) Math.sin(rel3)) * wid)) / 2.0f;
                                ccx += ((float) Math.cos(b2)) * depth;
                                ccy += ((float) Math.sin(b2)) * depth;
                            }
                            out2.add(new Obj(src.getWide() ? (-tr6.getId()) - 1000000 : tr6.getId(), cls, tr6.getGx(), tr6.getGy(), tr6.getGw(), facing, lampOut, cls, src.getWide(), v, ccx, ccy, moving2, speed, tr6.getGvx(), tr6.getGvy(), tr6.getVelN() >= 3));
                            detector = this;
                            parked2 = parked;
                            rowVote2 = rowVote;
                            it5 = it;
                        }
                        float ccx2 = tr6.getGx();
                        float ccy2 = tr6.getGy();
                        if (detector.isVehicle(cls)) {
                        }
                        out2.add(new Obj(src.getWide() ? (-tr6.getId()) - 1000000 : tr6.getId(), cls, tr6.getGx(), tr6.getGy(), tr6.getGw(), facing, lampOut, cls, src.getWide(), v, ccx2, ccy2, moving2, speed, tr6.getGvx(), tr6.getGvy(), tr6.getVelN() >= 3));
                        detector = this;
                        parked2 = parked;
                        rowVote2 = rowVote;
                        it5 = it;
                    }
                    facing = (v3[1] <= 3.0f || v3[1] <= v3[0] * 2.0f) ? 0 : 1;
                    float[] lv2 = tr6.getLampVotes();
                    lm = Math.max(lv2[0], Math.max(lv2[1], lv2[2]));
                    if (lm >= 0.8f) {
                    }
                    float ccx22 = tr6.getGx();
                    float ccy22 = tr6.getGy();
                    if (detector.isVehicle(cls)) {
                    }
                    out2.add(new Obj(src.getWide() ? (-tr6.getId()) - 1000000 : tr6.getId(), cls, tr6.getGx(), tr6.getGy(), tr6.getGw(), facing, lampOut, cls, src.getWide(), v, ccx22, ccy22, moving2, speed, tr6.getGvx(), tr6.getGvy(), tr6.getVelN() >= 3));
                    detector = this;
                    parked2 = parked;
                    rowVote2 = rowVote;
                    it5 = it;
                }
                src.setObjects(out2);
                src.setAtMs(System.currentTimeMillis());
                return;
            }
            Track tr7 = it2.next();
            int cls2 = detector.vehicleCls(tr7);
            float wPx = tr7.getX1() - tr7.getX0();
            ArrayList out3 = out;
            float hPx = tr7.getY1() - tr7.getY0();
            ArrayList pend2 = pend;
            float uc = (tr7.getX0() + tr7.getX1()) / 2.0f;
            int lamp2 = -1;
            float hdgMeas2 = Float.NaN;
            switch (cls2) {
                case 9:
                case 11:
                    float d = (hom2.getF() * (cls2 == 9 ? 1.0f : 0.75f)) / Math.max(hPx, 1.0f);
                    if (d < 3.0f) {
                        out = out3;
                        pend = pend2;
                        break;
                    } else if (d > 120.0f) {
                        out = out3;
                        pend = pend2;
                        break;
                    } else {
                        if (hom2.getFisheye()) {
                            float ang = (uc - hom2.getCx()) / hom2.getF();
                            gx2 = ((float) Math.cos(ang)) * d;
                            gy2 = ((float) Math.sin(ang)) * d;
                        } else {
                            gx2 = d;
                            gy2 = ((uc - hom2.getCx()) / hom2.getF()) * d;
                        }
                        if (cls2 == 9) {
                            tr2 = tr7;
                            t = t2;
                            lamp2 = lampColour(f, tr7.getX0(), tr7.getY0(), tr7.getX1(), tr7.getY1());
                        } else {
                            tr2 = tr7;
                            t = t2;
                        }
                        Unit unit = Unit.INSTANCE;
                        hom = hom2;
                        gx = gx2;
                        gy = gy2;
                        gw = 0.5f;
                        lamp = lamp2;
                        hdgMeas = Float.NaN;
                        tr = tr2;
                        if (!Float.isNaN(tr.getGx()) && (tr.getMisses() != 0 || tr.getHits() > detector.need(cls2))) {
                            float a2 = RangesKt.coerceIn(0.6f - (tr.getGx() * 0.01f), 0.2f, 0.6f);
                            tr.setGx(tr.getGx() + ((gx - tr.getGx()) * a2));
                            tr.setGy(tr.getGy() + ((gy - tr.getGy()) * a2));
                            tr.setGw(tr.getGw() + ((gw - tr.getGw()) * a2));
                            long nowNs3 = System.nanoTime();
                            ArrayList oCand3 = oCand;
                            long baseNs = (long) (Math.max(0.25f, gx / 80.0f) * 1.0E9f);
                            if (tr.getMisses() != 0 && gx < 90.0f) {
                                if (!Float.isNaN(tr.getMx()) && nowNs3 - tr.getMNs() > baseNs) {
                                    float dts = (nowNs3 - tr.getMNs()) / 1.0E9f;
                                    if (dts < 2.5f) {
                                        float ve = t.getVEgo();
                                        float vx = ((gx - tr.getMx()) / dts) + ve;
                                        float vy = (gy - tr.getMy()) / dts;
                                        tr.setGvx(tr.getGvx() + ((vx - tr.getGvx()) * 0.3f));
                                        tr.setGvy(tr.getGvy() + ((vy - tr.getGvy()) * 0.3f));
                                        float sp = (float) Math.hypot(tr.getGvx(), tr.getGvy());
                                        if (sp > 45.0f) {
                                            tr.setGvx(tr.getGvx() * (45.0f / sp));
                                            tr.setGvy(tr.getGvy() * (45.0f / sp));
                                        }
                                        tr.setVelN(tr.getVelN() + 1);
                                    }
                                    tr.setMx(gx);
                                    tr.setMy(gy);
                                    tr.setMNs(nowNs3);
                                } else if (Float.isNaN(tr.getMx()) || nowNs3 - tr.getMNs() > 2500000000L) {
                                    tr.setMx(gx);
                                    tr.setMy(gy);
                                    tr.setMNs(nowNs3);
                                }
                            }
                            float speed2 = (float) Math.hypot(tr.getGvx(), tr.getGvy());
                            float bb = detector.atan2(gy, gx);
                            float vr = (tr.getGvx() * ((float) Math.cos(bb))) + (tr.getGvy() * ((float) Math.sin(bb)));
                            float vt = ((-tr.getGvx()) * ((float) Math.sin(bb))) + (tr.getGvy() * ((float) Math.cos(bb)));
                            float radialNoise = (!src.getWide() ? 0.16f : 0.08f) * RangesKt.coerceAtLeast(tr.getGx(), 0.0f);
                            float effSpeed = (float) Math.hypot(vt, Math.max(0.0f, Math.abs(vr) - radialNoise));
                            if (tr.getHits() >= 6) {
                                if (effSpeed > (src.getWide() ? 3.5f : 3.0f)) {
                                    moving = true;
                                    pend = pend2;
                                    pend.add(new Pend(tr, cls2, lamp, moving, speed2, !Float.isNaN(hdgMeas)));
                                    if (lamp >= 0) {
                                        for (int k4 = 0; k4 < 3; k4++) {
                                            float[] lampVotes = tr.getLampVotes();
                                            lampVotes[k4] = lampVotes[k4] * 0.6f;
                                        }
                                        float[] lampVotes2 = tr.getLampVotes();
                                        lampVotes2[lamp] = lampVotes2[lamp] + 1.0f;
                                        out = out3;
                                        oCand = oCand3;
                                        t2 = t;
                                        hom2 = hom;
                                        break;
                                    } else {
                                        for (int k5 = 0; k5 < 3; k5++) {
                                            float[] lampVotes3 = tr.getLampVotes();
                                            lampVotes3[k5] = lampVotes3[k5] * 0.8f;
                                        }
                                        out = out3;
                                        oCand = oCand3;
                                        t2 = t;
                                        hom2 = hom;
                                        break;
                                    }
                                }
                            }
                            moving = false;
                            pend = pend2;
                            pend.add(new Pend(tr, cls2, lamp, moving, speed2, !Float.isNaN(hdgMeas)));
                            if (lamp >= 0) {
                            }
                        }
                        tr.setGx(gx);
                        tr.setGy(gy);
                        tr.setGw(gw);
                        long nowNs32 = System.nanoTime();
                        ArrayList oCand32 = oCand;
                        long baseNs2 = (long) (Math.max(0.25f, gx / 80.0f) * 1.0E9f);
                        if (tr.getMisses() != 0) {
                        }
                        float speed22 = (float) Math.hypot(tr.getGvx(), tr.getGvy());
                        float bb2 = detector.atan2(gy, gx);
                        float vr2 = (tr.getGvx() * ((float) Math.cos(bb2))) + (tr.getGvy() * ((float) Math.sin(bb2)));
                        float vt2 = ((-tr.getGvx()) * ((float) Math.sin(bb2))) + (tr.getGvy() * ((float) Math.cos(bb2)));
                        float radialNoise2 = (!src.getWide() ? 0.16f : 0.08f) * RangesKt.coerceAtLeast(tr.getGx(), 0.0f);
                        float effSpeed2 = (float) Math.hypot(vt2, Math.max(0.0f, Math.abs(vr2) - radialNoise2));
                        if (tr.getHits() >= 6) {
                        }
                        moving = false;
                        pend = pend2;
                        pend.add(new Pend(tr, cls2, lamp, moving, speed22, !Float.isNaN(hdgMeas)));
                        if (lamp >= 0) {
                        }
                    }
                    break;
                case 10:
                default:
                    t = t2;
                    if (!src.getWide() || !detector.isVehicle(cls2) || tr7.getY1() <= f.getH() * 0.92f || tr7.getX1() - tr7.getX0() <= f.getW() * 0.4f) {
                        float[] g = hom2.ground(uc, Math.min(tr7.getY1(), f.getH()));
                        if (g == null) {
                            out = out3;
                            pend = pend2;
                            t2 = t;
                            break;
                        } else if (g[0] < 1.5f || g[0] > 100.0f) {
                            pend = pend2;
                            out = out3;
                            t2 = t;
                            break;
                        } else if (Math.abs(g[1]) > 40.0f) {
                            out = out3;
                            pend = pend2;
                            t2 = t;
                            break;
                        } else if (g[0] < 4.0f && Math.abs(g[1]) < 1.8f) {
                            out = out3;
                            pend = pend2;
                            t2 = t;
                            break;
                        } else {
                            float gx3 = g[0];
                            float gy3 = g[1];
                            float gw2 = hom2.getFisheye() ? (wPx / hom2.getF()) * ((float) Math.hypot(g[0], g[1])) : (g[0] * wPx) / hom2.getF();
                            if (detector.isVehicle(cls2) && (gw2 < 0.9f || gw2 > 12.0f)) {
                                out = out3;
                                pend = pend2;
                                t2 = t;
                                break;
                            } else {
                                boolean cut = tr7.getX0() < ((float) f.getW()) * 0.015f || tr7.getX1() > ((float) f.getW()) * 0.985f || tr7.getY1() > ((float) f.getH()) * 0.97f || tr7.getY0() < ((float) f.getH()) * 0.01f;
                                if (detector.isVehicle(cls2) && !cut && tr7.getMisses() == 0) {
                                    float th = hom2.offAxis(uc, (tr7.getY0() + tr7.getY1()) / 2.0f);
                                    float aspK = (!hom2.getFisheye() || th <= 0.05f) ? 1.0f : th / ((float) Math.sin(th));
                                    gy = gy3;
                                    hom = hom2;
                                    gx = gx3;
                                    Cue c2 = vehicleCue(f, tr7.getX0(), tr7.getY0(), tr7.getX1(), tr7.getY1(), detector.atan2(gy3, gx3), cls2, aspK);
                                    tr = tr7;
                                    tr.setAxisEv((tr7.getAxisEv() * 0.93f) + c2.getEv());
                                    for (int k6 = 0; k6 < 4; k6++) {
                                        float[] hVotes = tr.getHVotes();
                                        hVotes[k6] = hVotes[k6] * 0.93f;
                                    }
                                    float[] hVotes2 = tr.getHVotes();
                                    int alongBin = c2.getAlongBin();
                                    hVotes2[alongBin] = hVotes2[alongBin] + 1.0f;
                                    float[] hVotes3 = tr.getHVotes();
                                    int acrossBin = c2.getAcrossBin();
                                    hVotes3[acrossBin] = hVotes3[acrossBin] + c2.getAcrossW();
                                    if (detector.useOrient && cls2 != 5 && hPx >= 16.0f) {
                                        oCand.add(new OCand(tr, detector.atan2(gy, gx), aspK));
                                    }
                                    hdgMeas2 = 0.0f;
                                } else {
                                    gy = gy3;
                                    hom = hom2;
                                    tr = tr7;
                                    gx = gx3;
                                }
                                Unit unit2 = Unit.INSTANCE;
                                gw = gw2;
                                lamp = -1;
                                hdgMeas = hdgMeas2;
                                if (!Float.isNaN(tr.getGx())) {
                                    float a22 = RangesKt.coerceIn(0.6f - (tr.getGx() * 0.01f), 0.2f, 0.6f);
                                    tr.setGx(tr.getGx() + ((gx - tr.getGx()) * a22));
                                    tr.setGy(tr.getGy() + ((gy - tr.getGy()) * a22));
                                    tr.setGw(tr.getGw() + ((gw - tr.getGw()) * a22));
                                    long nowNs322 = System.nanoTime();
                                    ArrayList oCand322 = oCand;
                                    long baseNs22 = (long) (Math.max(0.25f, gx / 80.0f) * 1.0E9f);
                                    if (tr.getMisses() != 0) {
                                    }
                                    float speed222 = (float) Math.hypot(tr.getGvx(), tr.getGvy());
                                    float bb22 = detector.atan2(gy, gx);
                                    float vr22 = (tr.getGvx() * ((float) Math.cos(bb22))) + (tr.getGvy() * ((float) Math.sin(bb22)));
                                    float vt22 = ((-tr.getGvx()) * ((float) Math.sin(bb22))) + (tr.getGvy() * ((float) Math.cos(bb22)));
                                    float radialNoise22 = (!src.getWide() ? 0.16f : 0.08f) * RangesKt.coerceAtLeast(tr.getGx(), 0.0f);
                                    float effSpeed22 = (float) Math.hypot(vt22, Math.max(0.0f, Math.abs(vr22) - radialNoise22));
                                    if (tr.getHits() >= 6) {
                                    }
                                    moving = false;
                                    pend = pend2;
                                    pend.add(new Pend(tr, cls2, lamp, moving, speed222, !Float.isNaN(hdgMeas)));
                                    if (lamp >= 0) {
                                    }
                                    break;
                                }
                                tr.setGx(gx);
                                tr.setGy(gy);
                                tr.setGw(gw);
                                long nowNs3222 = System.nanoTime();
                                ArrayList oCand3222 = oCand;
                                long baseNs222 = (long) (Math.max(0.25f, gx / 80.0f) * 1.0E9f);
                                if (tr.getMisses() != 0) {
                                }
                                float speed2222 = (float) Math.hypot(tr.getGvx(), tr.getGvy());
                                float bb222 = detector.atan2(gy, gx);
                                float vr222 = (tr.getGvx() * ((float) Math.cos(bb222))) + (tr.getGvy() * ((float) Math.sin(bb222)));
                                float vt222 = ((-tr.getGvx()) * ((float) Math.sin(bb222))) + (tr.getGvy() * ((float) Math.cos(bb222)));
                                float radialNoise222 = (!src.getWide() ? 0.16f : 0.08f) * RangesKt.coerceAtLeast(tr.getGx(), 0.0f);
                                float effSpeed222 = (float) Math.hypot(vt222, Math.max(0.0f, Math.abs(vr222) - radialNoise222));
                                if (tr.getHits() >= 6) {
                                }
                                moving = false;
                                pend = pend2;
                                pend.add(new Pend(tr, cls2, lamp, moving, speed2222, !Float.isNaN(hdgMeas)));
                                if (lamp >= 0) {
                                }
                            }
                        }
                    } else {
                        out = out3;
                        pend = pend2;
                        t2 = t;
                        break;
                    }
                    break;
            }
        }
    }

    private final void mergeObjects() {
        Float valueOf;
        long now = System.currentTimeMillis();
        boolean z = false;
        Iterable $this$filter$iv = CollectionsKt.listOf((Object[]) new Source[]{this.roadSrc, this.wideSrc});
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            Source it = (Source) element$iv$iv;
            Source it2 = now - it.getAtMs() < 700 ? 1 : null;
            if (it2 != null) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        List live = (List) destination$iv$iv;
        Iterator it3 = live.iterator();
        if (it3.hasNext()) {
            Source it4 = (Source) it3.next();
            float fovTan = it4.getFovTan();
            while (it3.hasNext()) {
                Source it5 = (Source) it3.next();
                fovTan = Math.max(fovTan, it5.getFovTan());
            }
            valueOf = Float.valueOf(fovTan);
        } else {
            valueOf = null;
        }
        this.fovTan = valueOf != null ? valueOf.floatValue() : 0.5f;
        boolean roadLive = live.contains(this.roadSrc);
        float roadTan = this.roadSrc.getFovTan();
        ArrayList keep = new ArrayList();
        if (roadLive) {
            keep.addAll(this.roadSrc.getObjects());
        }
        if (live.contains(this.wideSrc)) {
            for (Obj o : this.wideSrc.getObjects()) {
                if (!roadLive || o.getX() <= 6.0f || Math.abs(o.getY()) >= o.getX() * roadTan * 0.92f) {
                    ArrayList $this$any$iv = keep;
                    if (!($this$any$iv instanceof Collection) || !$this$any$iv.isEmpty()) {
                        Iterator it6 = $this$any$iv.iterator();
                        while (true) {
                            if (!it6.hasNext()) {
                                z = false;
                                break;
                            }
                            Object element$iv = it6.next();
                            Obj k = (Obj) element$iv;
                            if (group(k.getCls()) == group(o.getCls()) && Math.abs(k.getX() - o.getX()) < (o.getX() * 0.2f) + 4.0f && Math.abs(k.getY() - o.getY()) < (o.getX() * 0.1f) + 2.5f) {
                                z = true;
                                break;
                            }
                        }
                    }
                    if (z) {
                        z = false;
                    } else {
                        keep.add(o);
                        z = false;
                    }
                }
            }
        }
        this.objects = keep;
        this.objectsAtMs = live.isEmpty() ? 0L : now;
    }

    private final float wrap(float a) {
        float x = a;
        while (x > 3.141592653589793d) {
            x -= 6.2831855f;
        }
        while (x < -3.141592653589793d) {
            x += 6.2831855f;
        }
        return x;
    }

    private final float atan2(float y, float x) {
        return (float) Math.atan2(y, x);
    }

    /*  JADX ERROR: NullPointerException in pass: InitCodeVariables
        java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.SSAVar.getPhiList()" because "resultVar" is null
        	at jadx.core.dex.visitors.InitCodeVariables.collectConnectedVars(InitCodeVariables.java:119)
        	at jadx.core.dex.visitors.InitCodeVariables.setCodeVar(InitCodeVariables.java:82)
        	at jadx.core.dex.visitors.InitCodeVariables.initCodeVar(InitCodeVariables.java:74)
        	at jadx.core.dex.visitors.InitCodeVariables.initCodeVars(InitCodeVariables.java:48)
        	at jadx.core.dex.visitors.InitCodeVariables.visit(InitCodeVariables.java:29)
        */
    /* JADX INFO: Access modifiers changed from: private */
    public final com.starpilot.starview.Detector.Cue vehicleCue(com.starpilot.starview.Detector.Frame r32, float r33, float r34, float r35, float r36, float r37, int r38, float r39) {
        /*
            Method dump skipped, instructions count: 504
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.starpilot.starview.Detector.vehicleCue(com.starpilot.starview.Detector$Frame, float, float, float, float, float, int, float):com.starpilot.starview.Detector$Cue");
    }

    private static final float vehicleCue$predicted(float $b, float wid, float len, float hgt, float h) {
        float r = h - $b;
        return ((Math.abs((float) Math.cos(r)) * wid) + (Math.abs((float) Math.sin(r)) * len)) / hgt;
    }

    private final float symmetry(Frame f, float u0, float v0, float u1, float v1) {
        float w = u1 - u0;
        float h = v1 - v0;
        if (w < 30.0f || h < 20.0f) {
            return 0.0f;
        }
        float mean = 0.0f;
        int n = 0;
        float[] ls = new float[100];
        float[] rs = new float[100];
        for (int j = 0; j < 10; j++) {
            for (int i = 0; i < 10; i++) {
                int v = RangesKt.coerceIn((int) (v0 + ((((j * 0.7f) / 9.0f) + 0.15f) * h)), 0, f.getH() - 1);
                float fx = ((i * 0.4f) / 9.0f) + 0.05f;
                int ul = RangesKt.coerceIn((int) (u0 + (w * fx)), 0, f.getW() - 1);
                int ur = RangesKt.coerceIn((int) (u1 - (w * fx)), 0, f.getW() - 1);
                float l = f.getY()[(f.getYStride() * v) + ul] & 255;
                float r = f.getY()[(f.getYStride() * v) + ur] & 255;
                ls[n] = l;
                rs[n] = r;
                mean += l + r;
                n++;
            }
        }
        int j2 = n * 2;
        float mean2 = mean / j2;
        float d = 0.0f;
        float dev = 0.0f;
        for (int k = 0; k < n; k++) {
            d += Math.abs(ls[k] - rs[k]);
            dev += Math.abs(ls[k] - mean2) + Math.abs(rs[k] - mean2);
        }
        return 1.0f - (d / Math.max(dev, 1.0f));
    }

    private final int roofProfile(Frame f, float u0, float v0, float u1, float v1) {
        float hPx = v1 - v0;
        if (hPx < 24.0f || u1 - u0 < 60.0f) {
            return 0;
        }
        int sum = 0;
        int n = 0;
        for (int j = 0; j < 5; j++) {
            for (int i = 0; i < 5; i++) {
                int u = RangesKt.coerceIn((int) (((u1 - u0) * (((i * 0.8f) / 4.0f) + 0.1f)) + u0), 0, f.getW() - 1);
                int v = RangesKt.coerceIn((int) (v0 + ((((j * 0.8f) / 4.0f) + 0.1f) * hPx)), 0, f.getH() - 1);
                sum += f.getY()[(f.getYStride() * v) + u] & 255;
                n++;
            }
        }
        if (sum / n < 45) {
            return 0;
        }
        float left = roofProfile$topEdge(u0, u1, f, v0, hPx, 0.04f, 0.22f);
        float right = roofProfile$topEdge(u0, u1, f, v0, hPx, 0.78f, 0.96f);
        if (Float.isNaN(left) || Float.isNaN(right)) {
            return 0;
        }
        float d = left - right;
        if (d > 0.12f) {
            return 1;
        }
        return d < -0.12f ? -1 : 0;
    }

    private static final float roofProfile$topEdge(float $u0, float $u1, Frame $f, float $v0, float hPx, float fx0, float fx1) {
        ArrayList tops = new ArrayList();
        for (int i = 0; i < 6; i++) {
            int u = RangesKt.coerceIn((int) ((($u1 - $u0) * (fx0 + (((fx1 - fx0) * i) / 5.0f))) + $u0), 0, $f.getW() - 1);
            int prev = -1;
            float found = Float.NaN;
            int j = 0;
            while (true) {
                if (j >= 40) {
                    break;
                }
                int v = RangesKt.coerceIn((int) ((((0.75f * hPx) * j) / 40) + $v0), 0, $f.getH() - 1);
                int yy = $f.getY()[($f.getYStride() * v) + u] & 255;
                if (prev >= 0 && Math.abs(yy - prev) > 28) {
                    found = (v - $v0) / hPx;
                    break;
                }
                prev = yy;
                j++;
            }
            if (!Float.isNaN(found)) {
                tops.add(Float.valueOf(found));
            }
        }
        int i2 = tops.size();
        if (i2 < 3) {
            return Float.NaN;
        }
        CollectionsKt.sort(tops);
        Object obj = tops.get(tops.size() / 2);
        Intrinsics.checkNotNullExpressionValue(obj, "get(...)");
        return ((Number) obj).floatValue();
    }

    private final Pair<Float, Float> headlightEnds(Frame f, float u0, float v0, float u1, float v1) {
        float[] all = new float[60];
        int na = 0;
        float[] ends = new float[2];
        int part = 0;
        for (int i = 2; part < i; i = 2) {
            float best = 0.0f;
            for (int j = 0; j < 6; j++) {
                int i2 = 0;
                while (i2 < 5) {
                    float fx = ((i2 * 0.23f) / 4.0f) + (part == 0 ? 0.02f : 0.75f);
                    int u = RangesKt.coerceIn((int) (u0 + ((u1 - u0) * fx)), 0, f.getW() - 1);
                    int v = RangesKt.coerceIn((int) (v0 + ((v1 - v0) * (((j * 0.45f) / 5.0f) + 0.3f))), 0, f.getH() - 1);
                    float yy = f.getY()[(f.getYStride() * v) + u] & 255;
                    int k = ((v >> 1) * f.getUvStride()) + ((u >> 1) * f.getUvPixStride());
                    int sat = Math.abs((f.getU()[k] & 255) - 128) + Math.abs((f.getV()[k] & 255) - 128);
                    if (sat < 40 && yy > best) {
                        best = yy;
                    }
                    all[na] = yy;
                    i2++;
                    na++;
                }
            }
            ends[part] = best;
            part++;
        }
        ArraysKt.sort(all);
        float med = all[30];
        return new Pair<>(Float.valueOf(ends[0] - med), Float.valueOf(ends[1] - med));
    }

    private final Triple<Float, Float, Float> redThirds(Frame f, float u0, float v0, float u1, float v1) {
        Detector detector = this;
        int i = 3;
        float[] res = new float[3];
        int part = 0;
        while (true) {
            int i2 = 0;
            int i3 = 1;
            if (part < i) {
                float[] rg = new float[40];
                int n = 0;
                int j = 0;
                while (true) {
                    float f2 = 3.0f;
                    if (j < 5) {
                        int i4 = 0;
                        while (i4 < 8) {
                            int u = RangesKt.coerceIn((int) (u0 + ((u1 - u0) * ((part + (((i4 * 0.9f) / 7.0f) + 0.05f)) / f2))), i2, f.getW() - i3);
                            int v = RangesKt.coerceIn((int) (v0 + ((v1 - v0) * (((j * 0.4f) / 4.0f) + 0.25f))), i2, f.getH() - i3);
                            int yy = (f.getY()[(f.getYStride() * v) + u] & 255) - 16;
                            int uvI = ((v >> 1) * f.getUvStride()) + ((u >> 1) * f.getUvPixStride());
                            int uu = (f.getU()[uvI] & 255) - 128;
                            int vv = (f.getV()[uvI] & 255) - 128;
                            int yv = yy * 298;
                            int r = detector.clamp((((vv * 409) + yv) + 128) >> 8);
                            int g = detector.clamp((((yv - (uu * 100)) - (vv * 208)) + 128) >> 8);
                            int bb = detector.clamp((((uu * 516) + yv) + 128) >> 8);
                            rg[n] = r - ((g + bb) / 2.0f);
                            i4++;
                            n++;
                            i2 = 0;
                            i3 = 1;
                            f2 = 3.0f;
                            detector = this;
                        }
                        j++;
                        i2 = 0;
                        i3 = 1;
                        detector = this;
                    }
                }
                ArraysKt.sort(rg);
                res[part] = (((rg[39] + rg[38]) + rg[37]) / 3.0f) - rg[20];
                part++;
                i = 3;
                detector = this;
            } else {
                return new Triple<>(Float.valueOf(res[0]), Float.valueOf(res[1]), Float.valueOf(res[2]));
            }
        }
    }

    private final int clamp(int v) {
        if (v < 0) {
            return 0;
        }
        if (v > 255) {
            return 255;
        }
        return v;
    }

    private final boolean tailLights(Frame f, float u0, float v0, float u1, float v1) {
        Detector detector = this;
        float[] rg = new float[96];
        int n = 0;
        int j = 0;
        while (true) {
            int i = 0;
            int i2 = 1;
            if (j >= 6) {
                break;
            }
            int i3 = 0;
            while (i3 < 16) {
                int u = RangesKt.coerceIn((int) (u0 + ((u1 - u0) * (((i3 * 0.94f) / 15.0f) + 0.03f))), i, f.getW() - i2);
                int v = RangesKt.coerceIn((int) (v0 + ((v1 - v0) * (((j * 0.3f) / 5.0f) + 0.3f))), i, f.getH() - i2);
                int yy = (f.getY()[(f.getYStride() * v) + u] & 255) - 16;
                int uvI = ((v >> 1) * f.getUvStride()) + ((u >> 1) * f.getUvPixStride());
                int uu = (f.getU()[uvI] & 255) - 128;
                int vv = (f.getV()[uvI] & 255) - 128;
                int yv = yy * 298;
                int r = detector.clamp((((vv * 409) + yv) + 128) >> 8);
                int g = detector.clamp((((yv - (uu * 100)) - (vv * 208)) + 128) >> 8);
                int b = detector.clamp((((uu * 516) + yv) + 128) >> 8);
                rg[n] = r - ((g + b) / 2.0f);
                i3++;
                n++;
                i = 0;
                i2 = 1;
                detector = this;
            }
            j++;
            detector = this;
        }
        ArraysKt.sort(rg);
        float top = (((rg[95] + rg[94]) + rg[93]) + rg[92]) / 4.0f;
        return top - rg[48] > 12.0f;
    }

    private final int lampColour(Frame f, float u0, float v0, float u1, float v1) {
        float best = -1.0f;
        int br = 0;
        int bg = 0;
        int bb = 0;
        int j = 0;
        while (true) {
            int i = 0;
            int i2 = 1;
            if (j >= 12) {
                break;
            }
            int i3 = 0;
            while (i3 < 6) {
                int u = RangesKt.coerceIn((int) (u0 + ((u1 - u0) * (((i3 * 0.7f) / 5.0f) + 0.15f))), i, f.getW() - i2);
                int v = RangesKt.coerceIn((int) (v0 + ((v1 - v0) * (((j * 0.9f) / 11.0f) + 0.05f))), i, f.getH() - i2);
                int yy = (f.getY()[(f.getYStride() * v) + u] & 255) - 16;
                if (yy >= best) {
                    int k = ((v >> 1) * f.getUvStride()) + ((u >> 1) * f.getUvPixStride());
                    int uu = (f.getU()[k] & 255) - 128;
                    int vv = (f.getV()[k] & 255) - 128;
                    int yv = yy * 298;
                    best = yy;
                    br = clamp((((vv * 409) + yv) + 128) >> 8);
                    bg = clamp((((yv - (uu * 100)) - (vv * 208)) + 128) >> 8);
                    bb = clamp((((uu * 516) + yv) + 128) >> 8);
                }
                i3++;
                i = 0;
                i2 = 1;
            }
            j++;
        }
        if (best < 60.0f) {
            return -1;
        }
        return (br <= bg + 40 || br <= bb + 40) ? (bg < br + (-10) || bg <= bb + (-30)) ? -1 : 2 : (bg <= bb + 25 || ((float) bg) <= ((float) br) * 0.55f) ? 0 : 1;
    }

    static /* synthetic */ List decode$default(Detector detector, float f, int i, int i2, float f2, int i3, Object obj) {
        if ((i3 & 8) != 0) {
            f2 = 0.15f;
        }
        return detector.decode(f, i, i2, f2);
    }

    private final List<Det> decode(float r, int fw, int fh, float thr) {
        Iterable $this$none$iv;
        int[] strides;
        Iterator it;
        int i;
        int si;
        Triple o;
        int gh;
        int gw;
        ArrayList cand = new ArrayList();
        int[] strides2 = {8, 16, 32};
        Iterator it2 = this.outs.iterator();
        int i2 = 0;
        while (it2.hasNext()) {
            int si2 = i2;
            i2++;
            Triple o2 = (Triple) it2.next();
            int gh2 = ((Number) o2.component1()).intValue();
            int gw2 = ((Number) o2.component2()).intValue();
            ByteBuffer b = (ByteBuffer) o2.component3();
            b.rewind();
            FloatBuffer fb = b.asFloatBuffer();
            int st = strides2[si2];
            int gy = 0;
            while (gy < gh2) {
                int gx = 0;
                while (gx < gw2) {
                    int base = ((gy * gw2) + gx) * 85;
                    float obj = fb.get(base + 4);
                    if (obj < thr) {
                        strides = strides2;
                        it = it2;
                        i = i2;
                        si = si2;
                        o = o2;
                        gh = gh2;
                        gw = gw2;
                    } else {
                        int best = -1;
                        float bs = 0.0f;
                        Iterator<Integer> it3 = NAMES.keySet().iterator();
                        while (it3.hasNext()) {
                            int c = it3.next().intValue();
                            int[] strides3 = strides2;
                            float s = fb.get(base + 5 + c);
                            if (s > bs) {
                                bs = s;
                                best = c;
                                strides2 = strides3;
                            } else {
                                strides2 = strides3;
                            }
                        }
                        strides = strides2;
                        float score = obj * bs;
                        if (best < 0) {
                            it = it2;
                            i = i2;
                            si = si2;
                            o = o2;
                            gh = gh2;
                            gw = gw2;
                        } else if (score < thr) {
                            it = it2;
                            i = i2;
                            si = si2;
                            o = o2;
                            gh = gh2;
                            gw = gw2;
                        } else if (best != 0 || score >= 0.5f) {
                            it = it2;
                            float cx = (fb.get(base) + gx) * st;
                            float cy = (fb.get(base + 1) + gy) * st;
                            i = i2;
                            si = si2;
                            float w = ((float) Math.exp(RangesKt.coerceIn(fb.get(base + 2), -10.0f, 10.0f))) * st;
                            o = o2;
                            float h = ((float) Math.exp(RangesKt.coerceIn(fb.get(base + 3), -10.0f, 10.0f))) * st;
                            float f = 2;
                            gh = gh2;
                            gw = gw2;
                            cand.add(new Det(best, score, Math.max(0.0f, (cx - (w / f)) / r), Math.max(0.0f, (cy - (h / f)) / r), Math.min(fw, (cx + (w / f)) / r), Math.min(fh, ((h / f) + cy) / r), 0L, 64, null));
                        } else {
                            it = it2;
                            i = i2;
                            si = si2;
                            o = o2;
                            gh = gh2;
                            gw = gw2;
                        }
                    }
                    gx++;
                    strides2 = strides;
                    it2 = it;
                    i2 = i;
                    si2 = si;
                    o2 = o;
                    gh2 = gh;
                    gw2 = gw;
                }
                gy++;
                gh2 = gh2;
            }
        }
        ArrayList $this$sortByDescending$iv = cand;
        if ($this$sortByDescending$iv.size() > 1) {
            CollectionsKt.sortWith($this$sortByDescending$iv, new Comparator() { // from class: com.starpilot.starview.Detector$decode$$inlined$sortByDescending$1
                /* JADX WARN: Multi-variable type inference failed */
                @Override // java.util.Comparator
                public final int compare(T t, T t2) {
                    Detector.Det it4 = (Detector.Det) t2;
                    Detector.Det it5 = (Detector.Det) t;
                    return ComparisonsKt.compareValues(Float.valueOf(it4.getScore()), Float.valueOf(it5.getScore()));
                }
            });
        }
        ArrayList keep = new ArrayList();
        Iterator it4 = cand.iterator();
        Intrinsics.checkNotNullExpressionValue(it4, "iterator(...)");
        while (it4.hasNext()) {
            Object next = it4.next();
            Intrinsics.checkNotNullExpressionValue(next, "next(...)");
            Det d = (Det) next;
            ArrayList $this$none$iv2 = keep;
            if (!($this$none$iv2 instanceof Collection) || !$this$none$iv2.isEmpty()) {
                Iterator it5 = $this$none$iv2.iterator();
                while (true) {
                    if (it5.hasNext()) {
                        Object element$iv = it5.next();
                        Det it6 = (Det) element$iv;
                        if (((it6.getCls() != d.getCls() || iou(it6, d) <= 0.45f) ? null : 1) != null) {
                            $this$none$iv = null;
                            break;
                        }
                    } else {
                        $this$none$iv = 1;
                        break;
                    }
                }
            } else {
                $this$none$iv = 1;
            }
            if ($this$none$iv != null) {
                keep.add(d);
            }
        }
        return keep;
    }

    private final float boxIou(Track a, Track b) {
        float ix = Math.max(0.0f, Math.min(a.getX1(), b.getX1()) - Math.max(a.getX0(), b.getX0()));
        float iy = Math.max(0.0f, Math.min(a.getY1(), b.getY1()) - Math.max(a.getY0(), b.getY0()));
        float inter = ix * iy;
        return inter / (((((a.getX1() - a.getX0()) * (a.getY1() - a.getY0())) + ((b.getX1() - b.getX0()) * (b.getY1() - b.getY0()))) - inter) + 1.0E-6f);
    }

    private final float iou(Det a, Det b) {
        float ix = Math.max(0.0f, Math.min(a.getX1(), b.getX1()) - Math.max(a.getX0(), b.getX0()));
        float iy = Math.max(0.0f, Math.min(a.getY1(), b.getY1()) - Math.max(a.getY0(), b.getY0()));
        float inter = ix * iy;
        return inter / (((((a.getX1() - a.getX0()) * (a.getY1() - a.getY0())) + ((b.getX1() - b.getX0()) * (b.getY1() - b.getY0()))) - inter) + 1.0E-6f);
    }
}
