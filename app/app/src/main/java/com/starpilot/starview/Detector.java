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
        return (Function1) this.shotReq;
    }

    public final void setShotReq(Function1<? super Bitmap, Unit> function1) {
        this.shotReq = function1;
    }

    public final Function1<Bitmap, Unit> getWideReq() {
        return (Function1) this.wideReq;
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

    /* recovered: the original JVM name (callers taken from Vineflower use it) */

    public final boolean isRunning() {

        return getRunning();

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
            throw KtCompat.sneaky(new ModelDownload.Missing(ModelDownload.INSTANCE.getStatus()));
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
                    if (((Number) o.getFirst()).intValue() == s[1] && ((Number) o.getSecond()).intValue() == s[2]) {
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

    public final void loop() {
        while (this.running) {
            // take turns: of the cameras with a frame waiting, the one served longest ago
            Source src = null;
            for (Source s : CollectionsKt.listOf(new Source[]{this.roadSrc, this.wideSrc})) {
                if (s.getFrame().get() != null && (src == null || s.getLastRunNs() < src.getLastRunNs())) {
                    src = s;
                }
            }
            Frame f = src != null ? src.getFrame().getAndSet(null) : null;
            if (src == null || f == null) {
                synchronized (this.lock) {
                    if (this.running && this.roadSrc.getFrame().get() == null && this.wideSrc.getFrame().get() == null) {
                        try {
                            this.lock.wait(300L);
                        } catch (InterruptedException e) {
                            throw KtCompat.sneaky(e);
                        }
                    }
                }
                continue;
            }
            src.setLastRunNs(System.nanoTime());
            try {
                ensureInterpreter();
                long t0 = System.nanoTime();
                float scale = fill(f, src.getWide());
                long t1 = System.nanoTime();
                if (this.firstRun) {
                    this.status = "first GPU run: compiling (can take a minute the first time)…";
                }
                Interpreter it = this.interp;
                Intrinsics.checkNotNull(it);
                int[] order = this.outOrder;
                Intrinsics.checkNotNull(order);
                HashMap<Integer, Object> outMap = new HashMap<>();
                for (int i = 0; i < order.length; i++) {
                    ByteBuffer b = this.outs.get(order[i]).getThird();
                    b.rewind();
                    outMap.put(Integer.valueOf(i), b);
                }
                this.input.rewind();
                it.runForMultipleInputsOutputs(new Object[]{this.input}, outMap);
                long t2 = System.nanoTime();
                if (this.firstRun) {
                    this.firstRunMs = (t2 - t1) / 1000000.0d;
                    this.firstRun = false;
                }
                track(src, decode$default(this, scale, f.getW(), f.getH(), 0.0f, 8, null), f);
                this.detsAtNs = System.nanoTime();
                if (this.snapWant == (src.getWide() ? 2 : 1)) {
                    this.snap = grey(f);
                    this.snapWide = src.getWide();
                    this.snapWant = 0;
                }
                Function1<? super Bitmap, Unit> shot = this.shotReq;
                if (!src.getWide() && shot != null) {
                    this.shotReq = null;
                    try {
                        Bitmap clean = colour(f);
                        Bitmap drawn = clean.copy(Bitmap.Config.ARGB_8888, true);
                        try {
                            Intrinsics.checkNotNull(drawn);
                            drawPath(drawn, f);
                        } catch (Exception e) {
                        }
                        this.lastClean = clean;
                        Intrinsics.checkNotNull(drawn);
                        shot.invoke(drawn);
                    } catch (Exception e2) {
                    }
                }
                LaneModel lm = this.laneModel;
                if (!src.getWide() && lm != null && LaneModel.wants$default(lm, 0L, 1, null)) {
                    try {
                        lm.feed(colour(f));
                    } catch (Exception e3) {
                    }
                }
                if (src.getWide()) {
                    this.wideFrameNs = System.nanoTime();
                    Function1<? super Bitmap, Unit> wr = this.wideReq;
                    if (wr != null) {
                        this.wideReq = null;
                        try {
                            wr.invoke(colour(f));
                        } catch (Exception e4) {
                        }
                    }
                }
                if (System.nanoTime() - src.getAmbientNs() > 250000000) {
                    src.setAmbientNs(System.nanoTime());
                    src.setAmbient(ambient(f));
                }
                LayoutSeg lay = this.layout;
                if (lay != null) {
                    Telemetry tel = this.telemetry;
                    if (src.getWide() && tel != null && tel.getCalValid() && lay.wantsFrame(System.nanoTime())) {
                        lay.feed(f.getY(), f.getU(), f.getV(), f.getYStride(), f.getUvStride(), f.getUvPixStride(), f.getW(), f.getH(), tel.getCalRpy(), tel.getCalHeight(), System.nanoTime());
                    }
                }
                LaneMarks marks = this.laneMarks;
                if (marks != null) {
                    Telemetry tel2 = this.telemetry;
                    if (!src.getWide() && tel2 != null && tel2.getCalValid() && marks.wants(System.nanoTime())) {
                        Lead ld = tel2.getLeadOne();
                        marks.analyze(f.getY(), f.getU(), f.getV(), f.getYStride(), f.getUvStride(), f.getUvPixStride(), f.getW(), f.getH(), tel2.getCalRpy(), tel2.getModel(), ld.getStatus() ? ld.getDRel() : 0.0f, System.nanoTime());
                    }
                }
                this.prepMs = this.prepMs == 0.0d ? (t1 - t0) / 1000000.0d : (this.prepMs * 0.8d) + (((t1 - t0) / 1000000.0d) * 0.2d);
                this.inferMs = this.inferMs == 0.0d ? (t2 - t1) / 1000000.0d : (this.inferMs * 0.8d) + (((t2 - t1) / 1000000.0d) * 0.2d);
                this.status = "running";
                long n = System.nanoTime();
                this.fpsWin++;
                if (n - this.fpsT0 > 2000000000) {
                    this.fps = (this.fpsWin * 1.0E9d) / (n - this.fpsT0);
                    this.fpsWin = 0;
                    this.fpsT0 = n;
                }
                src.setFpsWin(src.getFpsWin() + 1);
                if (n - src.getFpsT0() > 2000000000) {
                    src.setFps((src.getFpsWin() * 1.0E9d) / (n - src.getFpsT0()));
                    src.setFpsWin(0);
                    src.setFpsT0(n);
                }
            } catch (Throwable e5) {
                if (e5 instanceof ModelDownload.Missing) {
                    String msg = e5.getMessage();
                    this.status = msg == null ? "" : msg;
                } else {
                    Log.e(TAG, "detector: " + e5);
                    this.status = "error: " + e5.getMessage();
                }
                release();
                try {
                    Thread.sleep(3000L);
                } catch (InterruptedException e6) {
                }
            } finally {
                src.getFree().add(f);
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
                    fill$lambda$21$lambda$20 = Detector.fill$lambda$21$lambda$20(nextInt, i4, f, min, i5, zArr, fArr2, iArr, iArr2, Detector.this);
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

    /* VF: from Vineflower (jadx: Removed duplicated region;incorrect) */
    private final void track(Detector.Source var1, List<Detector.Det> var2, Detector.Frame var3) {
        Object var75 = null; // declarations Vineflower left out
        Iterable var21 = var2;
        var var20 = new ArrayList();

        for (Object var22 : var21) {
            boolean var13;
            if (((Detector.Det)var22).getScore() >= 0.35F) {
                var13 = true;
            } else {
                var13 = false;
            }

            if (var13) {
                var20.add(var22);
            }
        }

        List var27 = var20;
        Iterable var32 = var2;
        var var89 = new ArrayList();

        for (Object var23 : var32) {
            int var60;
            label295: {
                Detector.Det var67 = (Detector.Det)var23;
                Iterable var68;
                if (var67.getScore() < 0.35F) {
                    var var24 = var27;
                    if (var24 instanceof Collection && ((Collection)var24).isEmpty()) {
                        var60 = (((boolean)true) ? 1 : 0);
                    } else {
                        Iterator var106 = var24.iterator();

                        while (true) {
                            if (!var106.hasNext()) {
                                var60 = (((boolean)true) ? 1 : 0);
                                break;
                            }

                            Detector.Det var25 = (Detector.Det)var106.next();
                            var60 = this.group(var25.getCls());
                            boolean var58;
                            if (var60 == this.group(var67.getCls()) && this.iou(var25, var67) > 0.45F) {
                                var58 = true;
                            } else {
                                var58 = false;
                            }

                            if (var58) {
                                var60 = (((boolean)false) ? 1 : 0);
                                break;
                            }
                        }
                    }

                    var68 = var32;
                    if ((var60 != 0)) {
                        var60 = 1;
                        break label295;
                    }
                } else {
                    var68 = var32;
                }

                var60 = 0;
                var32 = var68;
            }

            if ((var60 != 0)) {
                var89.add(var23);
            }
        }

        List var81 = var89;
        long var15 = System.nanoTime();
        float var5;
        if (var1.getLastTrackNs() == 0L) {
            var5 = 0.05F;
        } else {
            var5 = RangesKt.coerceIn((float)(var15 - var1.getLastTrackNs()) / 1.0E9F, 0.01F, 0.5F);
        }

        var1.setLastTrackNs(var15);
        ArrayList var33 = var1.getTracks();
        ArrayList var111 = new ArrayList();
        Iterator var69 = var33.iterator();
        String var97 = "iterator(...)";
        Intrinsics.checkNotNullExpressionValue(var69, "iterator(...)");

        while (true) {
            boolean var19 = var69.hasNext();
            String var26 = "next(...)";
            if (!var19) {
                List var70 = var111;
                if (var70.size() > 1) {
                    CollectionsKt.sortWith(var70, new Comparator() {
                        @Override
                        public final int compare(Object var1, Object var2x) {
                            return ComparisonsKt.compareValues(((Detector$track$Pair)var1).getCost(), ((Detector$track$Pair)var2x).getCost());
                        }
                    });
                }

                HashSet var29 = new HashSet();
                HashSet var28 = new HashSet();
                Iterator var30 = var111.iterator();
                Intrinsics.checkNotNullExpressionValue(var30, "iterator(...)");
                ArrayList var109 = var33;
                String var71 = var26;

                while (var30.hasNext()) {
                    Object var97n = var30.next();
                    Intrinsics.checkNotNullExpressionValue(var97n, var71);
                    Object var99 = (Detector$track$Pair)var97n;
                    if (!var29.contains(((Detector$track$Pair) var99).getTr()) && !var28.contains(((Detector$track$Pair) var99).getD())) {
                        track$take(var29, var28, var5, var15, this, ((Detector$track$Pair) var99).getTr(), ((Detector$track$Pair) var99).getD(), 0.7F, true);
                    }
                }

                String var36;
                String var82;
                if (!var81.isEmpty()) {
                    Iterator var100 = var33.iterator();
                    Intrinsics.checkNotNullExpressionValue(var100, var97);
                    var36 = var71;
                    Iterator var72 = var100;

                    while (var72.hasNext()) {
                        var97 = (String)var72.next();
                        Intrinsics.checkNotNullExpressionValue(var97, var36);
                        Detector.Track var117 = (Detector.Track)(Object)var97;
                        if (!var29.contains(var117) && var117.getHits() >= this.need(var117.getCls()) && var117.getWeak() < 6) {
                            float var50 = var117.getX1() - var117.getX0();
                            float var46 = var117.getY1();
                            float var42 = var117.getY0();
                            float var51 = var117.getCx();
                            float var54 = var117.getVx();
                            float var52 = var117.getCy();
                            float var53 = var117.getVy();
                            float var56 = Math.max(Math.max(var50, var46 - var42), 24.0F);
                            Iterator var102 = var81.iterator();
                            Detector.Det var112 = null;
                            var42 = 0.6F;

                            while (var102.hasNext()) {
                                Detector.Det var115 = (Detector.Det)var102.next();
                                if (!var28.contains(var115) && this.group(var115.getCls()) == this.group(var117.getCls())) {
                                    var46 = (var115.getX1() - var115.getX0()) / Math.max(var50, 1.0F);
                                    if (!(var46 < 0.67F) && !(var46 > 1.5F)) {
                                        var46 = (float)Math.hypot(
                                                (var115.getX0() + var115.getX1()) / 2.0F - (var51 + var54 * var5),
                                                (var115.getY0() + var115.getY1()) / 2.0F - (var52 + var53 * var5)
                                            )
                                            / var56;
                                        if (var46 < var42) {
                                            var42 = var46;
                                            var112 = var115;
                                        }
                                    }
                                }
                            }

                            if (var112 != null) {
                                track$take(var29, var28, var5, var15, this, ((Track) var117), var112, 0.5F, false);
                            }
                        }
                    }

                    var82 = var97;
                } else {
                    var82 = var97;
                    var36 = var71;
                }

                Iterator var73 = var33.iterator();
                Intrinsics.checkNotNullExpressionValue(var73, var82);

                while (var73.hasNext()) {
                    var21 = (Iterable)var73.next();
                    Intrinsics.checkNotNullExpressionValue(var21, var36);
                    Detector.Track var84 = (Detector.Track)(Object)var21;
                    if (!var29.contains(var84)) {
                        var84.setMisses(var84.getMisses() + 1);
                        float var44 = var84.getVx() * var5;
                        float var49 = var84.getVy() * var5;
                        var84.setX0(var84.getX0() + var44);
                        var84.setX1(var84.getX1() + var44);
                        var84.setY0(var84.getY0() + var49);
                        var84.setY1(var84.getY1() + var49);
                        var84.setVx(var84.getVx() * 0.9F);
                        var84.setVy(var84.getVy() * 0.9F);
                    }
                }

                for (Detector.Det var37 : (Iterable<Detector.Det>) var27) {
                    if (!var28.contains(var37)) {
                        long var17 = (long)(this.nextId++);
                        Detector.Track var85 = new Detector.Track(var17, var37.getCls());
                        var85.setX0(var37.getX0());
                        var85.setY0(var37.getY0());
                        var85.setX1(var37.getX1());
                        var85.setY1(var37.getY1());
                        var85.setScore(var37.getScore());
                        var85.setHits(1);
                        var85.setLastNs(var15);
                        if (this.isVehicle(var37.getCls())) {
                            float[] var74 = var85.getKinds();
                            byte var61;
                            switch (var37.getCls()) {
                                case 5:
                                    var61 = 2;
                                    break;
                                case 6:
                                default:
                                    var61 = 0;
                                    break;
                                case 7:
                                    var61 = 1;
                            }

                            var74[var61] = 1.0F;
                        }

                        var109.add(var85);
                    }
                }

                Telemetry var38 = this.telemetry;
                float var45;
                if (var38 != null) {
                    var45 = var38.getVEgo();
                } else {
                    var45 = 0.0F;
                }

                if (var45 < 1.0F) {
                    var19 = true;
                } else {
                    var19 = false;
                }

                CollectionsKt.removeAll(var109, new Detector$$ExternalSyntheticLambda2(this, var19, var3));
                List var86 = CollectionsKt.sortedWith(var109, new Comparator() {
                    final Comparator $this_thenBy = new Comparator() {
                    @Override
                    public final int compare(Object var1, Object var2) {
                        return ComparisonsKt.compareValues(((Detector.Track)var2).getHits(), ((Detector.Track)var1).getHits());
                    }
                };

                    @Override
                    public final int compare(Object var1, Object var2) {
                        int var3 = this.$this_thenBy.compare(var1, var2);
                        if (var3 == 0) {
                            var3 = ComparisonsKt.compareValues(((Detector.Track)var1).getId(), ((Detector.Track)var2).getId());
                        }

                        return var3;
                    }
                });
                var89 = new ArrayList();

                for (Detector.Track var103 : (Iterable<Detector.Track>) var86) {
                    Iterator var113 = var89.iterator();

                    boolean var62;
                    do {
                        if (!var113.hasNext()) {
                            var75 = null;
                            break;
                        }

                        var75 = var113.next();
                        Detector.Track var116 = (Detector.Track)var75;
                        if (this.group(var116.getCls()) == this.group(var103.getCls()) && this.boxIou(var116, var103) > 0.6F) {
                            var62 = true;
                        } else {
                            var62 = false;
                        }
                    } while (!var62);

                    Detector.Track var76 = (Detector.Track)var75;
                    if (var76 == null) {
                        var89.add(var103);
                    } else if (var76.getMisses() > 0 && var103.getMisses() == 0) {
                        var76.setX0(var103.getX0());
                        var76.setY0(var103.getY0());
                        var76.setX1(var103.getX1());
                        var76.setY1(var103.getY1());
                        var76.setVx(var103.getVx());
                        var76.setVy(var103.getVy());
                        var76.setMisses(0);
                        var76.setLastNs(var103.getLastNs());
                    }
                }

                if (var89.size() < var109.size()) {
                    var109.retainAll(CollectionsKt.toSet(var89));
                }

                Iterable var77 = var109;
                var19 = false;
                var89 = new ArrayList();

                for (Object var87 : var77) {
                    byte var64;
                    label199: {
                        Detector.Track var110 = (Detector.Track)var87;
                        if (var110.getHits() >= this.need(var110.getCls())) {
                            int var14 = var110.getMisses();
                            if (var110.getHits() >= 8) {
                                var64 = 7;
                            } else {
                                var64 = 4;
                            }

                            if (var14 <= var64) {
                                var64 = 1;
                                break label199;
                            }
                        }

                        var64 = 0;
                    }

                    if ((var64 != 0)) {
                        var89.add(var87);
                    }
                }

                List var78 = var89;
                Iterable var40 = var78;
                var89 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var40, 10));

                for (Detector.Track var105 : (Iterable<Detector.Track>) var40) {
                    var89.add(
                        new Detector.Det(this.vehicleCls(var105), var105.getScore(), var105.getX0(), var105.getY0(), var105.getX1(), var105.getY1(), var105.getId())
                    );
                }

                var2 = var89;
                var1.setDets(var2);
                this.groundObjects(var1, var78, var3);
                this.mergeObjects();
                if (!var1.getWide()) {
                    try {
                        this.ideasFrame(var78, var3);
                    } catch (Exception var31) {
                    }
                }

                return;
            }

            Object var90 = var69.next();
            Intrinsics.checkNotNullExpressionValue(var90, "next(...)");
            Detector.Track var114 = (Detector.Track)var90;
            float var10 = var114.getX1() - var114.getX0();
            float var4 = var114.getY1() - var114.getY0();
            float var9 = var114.getCx();
            float var8 = var114.getVx();
            float var7 = var114.getCy();
            float var11 = var114.getVy();
            float var6 = Math.max(Math.max(var10, var4), 24.0F);
            Iterator var107 = var27.iterator();
            Iterator var34 = var107;

            while (var34.hasNext()) {
                Detector.Det var108 = (Detector.Det)var34.next();
                if (this.group(var108.getCls()) == this.group(var114.getCls())) {
                    float var12 = (var108.getX1() - var108.getX0()) / Math.max(var10, 1.0F);
                    if (!(var12 < 0.5F) && !(var12 > 2.0F)) {
                        var12 = (float)Math.hypot(
                                (var108.getX0() + var108.getX1()) / 2.0F - (var9 + var8 * var5), (var108.getY0() + var108.getY1()) / 2.0F - (var7 + var11 * var5)
                            )
                            / var6;
                        if (var12 < 0.9F) {
                            var111.add(new Detector$track$Pair(var114, var108, var12));
                        }
                    }
                }
            }

            var33 = var33;
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

    /* VF: from Vineflower (jadx: Removed duplicated region;incorrect) */
    private final void ideasFrame(List<Detector.Track> var1, Detector.Frame var2) {
        long var11 = System.currentTimeMillis();
        LeadLamps var17 = Ideas.INSTANCE.getSense().getLeadLamps();
        if (var17.getEnabled()) {
            Iterable var16 = var1;
            var var15 = new ArrayList();

            for (Object var19 : var16) {
                boolean var32;
                label276: {
                    Detector.Track var42 = (Detector.Track)var19;
                    if (this.isVehicle(var42.getCls()) && !Float.isNaN(var42.getGx())) {
                        float var3 = var42.getGx();
                        if (3.0F <= var3 && var3 <= 50.0F) {
                            var32 = true;
                        } else {
                            var32 = false;
                        }

                        if (var32 && Math.abs(var42.getGy()) < 1.5F && var42.getMisses() == 0) {
                            var32 = true;
                            break label276;
                        }
                    }

                    var32 = false;
                }

                if (var32) {
                    var15.add(var19);
                }
            }

            List var37 = var15;
            Iterator var49 = var37.iterator();
            Object var38;
            if (!var49.hasNext()) {
                var38 = null;
            } else {
                var38 = var49.next();
                if (var49.hasNext()) {
                    float var4 = ((Detector.Track)var38).getGx();

                    while (true) {
                        var16 = (Iterable)var49.next();
                        float var5 = ((Detector.Track)(Object)var16).getGx();
                        float var25 = var4;
                        if (Float.compare(var4, var5) > 0) {
                            var38 = var16;
                            var25 = var5;
                        }

                        if (!var49.hasNext()) {
                            break;
                        }

                        var4 = var25;
                    }
                }
            }

            var38 = (Detector.Track)var38;
            if (var38 != null) {
                kotlin.Pair var44 = this.sideLamps(
                    var2, ((Detector.Track)var38).getX0(), ((Detector.Track)var38).getY0(), ((Detector.Track)var38).getX1(), ((Detector.Track)var38).getY1()
                );
                float var26 = ((Number)var44.component1()).floatValue();
                float var29 = ((Number)var44.component2()).floatValue();
                var17.feed(((Detector.Track)var38).getId(), var11, var26, var29);
            }
        }

        long var13 = var11;
        if (WantIdeas.INSTANCE.getSchoolBus()) {
            Iterator var47 = var1.iterator();

            while (true) {
                var13 = var11;
                if (!var47.hasNext()) {
                    break;
                }

                Detector.Track var40 = (Detector.Track)var47.next();
                if (var40.getCls() == 5 && !Float.isNaN(var40.getGx())) {
                    float var27 = var40.getGx();
                    boolean var33;
                    if (3.0F <= var27 && var27 <= 70.0F) {
                        var33 = true;
                    } else {
                        var33 = false;
                    }

                    if (var33 && !(Math.abs(var40.getGy()) > 8.0F) && var40.getMisses() <= 0) {
                        Triple var45 = this.busLook(var2, var40.getX0(), var40.getY0(), var40.getX1(), var40.getY1());
                        float var30 = ((Number)var45.component1()).floatValue();
                        float var31 = ((Number)var45.component2()).floatValue();
                        var27 = ((Number)var45.component3()).floatValue();
                        this.schoolBus.feed(var40.getId(), var11, var40.getGx(), var30, var31, var27);
                    }
                }
            }
        }

        if (WantIdeas.INSTANCE.getWipers()) {
            this.wiperWatch(var2, var13);
        }

        boolean var10 = ShadowWatch.INSTANCE.getWanted();
        DayView var46 = Ideas.INSTANCE.getMemory().getDayView();
        if (var10 || var46.getWanted() && var13 - this.ideasDayMs > 1000L) {
            float[] var41 = new float[32 * 9];

            for (int var34 = 0; var34 < 9; var34++) {
                for (int var7 = 0; var7 < 32; var7++) {
                    int var8 = RangesKt.coerceIn((int)((var7 + 0.5F) / 32 * var2.getW()), 0, var2.getW() - 1);
                    int var9 = RangesKt.coerceIn((int)(var2.getH() / 2 + (var34 + 0.5F) / 9 * var2.getH() / 2), 0, var2.getH() - 1);
                    var41[var34 * 32 + var7] = var2.getY()[var2.getYStride() * var9 + var8] & 255;
                }
            }

            if (var10) {
                ShadowWatch var48 = ShadowWatch.INSTANCE;
                var var22 = var1;
                if (var22 instanceof Collection && ((Collection)var22).isEmpty()) {
                    var10 = false;
                } else {
                    label299: {
                        Iterator var23 = var22.iterator();

                        while (var23.hasNext()) {
                            boolean var35;
                            if (((Detector.Track)var23.next()).getCls() == 0) {
                                var35 = true;
                            } else {
                                var35 = false;
                            }

                            if (var35) {
                                var10 = true;
                                break label299;
                            }
                        }

                        var10 = false;
                    }
                }

                var48.feed(var41, 32, var10, var13);
            }

            if (var46.getWanted() && var13 - this.ideasDayMs > 1000L) {
                this.ideasDayMs = var13;
                Bitmap var24 = this.colour(var2);

                try {
                    var46.feed(var24, (float)ArraysKt.average(var41), var13);
                } finally {
                    var24.recycle();
                }
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
                    } else {
                        break;
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

    /* VF: from Vineflower (jadx: Removed duplicated region;incorrect) */
    private final void groundObjects(Detector.Source var1, List<Detector.Track> var2, Detector.Frame var3) {
        Detector.Track var158 = null; // declarations Vineflower left out
        Telemetry var30 = this.telemetry;
        if (var30 != null) {
            if (!var30.getCalValid()) {
                var1.setObjects(CollectionsKt.emptyList());
                var1.setAtMs(System.currentTimeMillis());
            } else {
                Projection.Companion.GroundInverse var31 = Projection.Companion
                    .groundInverse(var30.getCalRpy(), var30.getCalHeight(), var3.getW(), var3.getH(), var1.getWide());
                var1.setFovTan(var31.getCx() / var31.getF());
                ArrayList var33 = new ArrayList();
                ArrayList var29 = new ArrayList();
                ArrayList var32 = new ArrayList();
                Iterator var35 = var2.iterator();
                ArrayList var37 = var33;

                while (true) {
                    float var6;
                    float var9;
                    int var17;
                    int var18;
                    float var50;
                    float var62;
                    float var84;
                    label482:
                    while (true) {
                        boolean var22 = var35.hasNext();
                        var9 = 3.5F;
                        if (!var22) {
                            if (!var32.isEmpty()) {
                                long var135 = System.nanoTime();
                                Iterable var144 = var32;
                                var var138 = new ArrayList();

                                for (Object var149 : var144) {
                                    boolean var112;
                                    if (var135 - ((Detector.OCand)var149).getTr().getONs() > 150000000L) {
                                        var112 = true;
                                    } else {
                                        var112 = false;
                                    }

                                    if (var112) {
                                        var138.add(var149);
                                    }
                                }

                                List var139 = var138;

                                for (Detector.OCand var160 : (Iterable<Detector.OCand>) CollectionsKt.take(CollectionsKt.sortedWith(var139, new Comparator() {
                                    @Override
                                    public final int compare(Object var1, Object var2x) {
                                        return ComparisonsKt.compareValues(((Detector.OCand)var1).getTr().getONs(), ((Detector.OCand)var2x).getTr().getONs());
                                    }
                                }), 3)) {
                                    Detector.Track var150 = var160.getTr();
                                    float[] var140 = this.orient
                                        .classify(
                                            var3.getY(),
                                            var3.getU(),
                                            var3.getV(),
                                            var3.getYStride(),
                                            var3.getUvStride(),
                                            var3.getUvPixStride(),
                                            var3.getW(),
                                            var3.getH(),
                                            var150.getX0(),
                                            var150.getY0(),
                                            var150.getX1(),
                                            var150.getY1(),
                                            var160.getAspK()
                                        );
                                    if (var140 == null) {
                                        break;
                                    }

                                    var150.setONs(var135);
                                    var150.setON(var150.getON() + 1);

                                    for (int var113 = 0; var113 < 16; var113++) {
                                        float[] var168 = var150.getOHist();
                                        var168[var113] *= 0.85F;
                                    }

                                    for (int var114 = 0; var114 < 16; var114++) {
                                        var50 = (
                                                (
                                                            (
                                                                        this.wrap(CarOrient.Companion.getCENTRES()[var114] + var160.getBearing() + (float) (Math.PI / 2))
                                                                            + (float) Math.PI
                                                                    )
                                                                    / (float) (Math.PI * 2)
                                                                    * 16
                                                                - 0.5F
                                                        )
                                                        % 16
                                                    + 16
                                            )
                                            % 16;
                                        int var19 = (int)((float)Math.floor(var50)) % 16;
                                        var18 = (var19 + 1) % 16;
                                        var50 -= (float)Math.floor(var50);
                                        float[] var169 = var150.getOHist();
                                        var169[var19] += var140[var114] * (1 - var50);
                                        var169 = var150.getOHist();
                                        var169[var18] += var140[var114] * var50;
                                    }
                                }
                            }

                            Iterable var141 = var29;
                            var var38 = new ArrayList();

                            for (Object var151 : var141) {
                                Detector.Pend var142 = (Detector.Pend)var151;
                                boolean var115;
                                if (this.isVehicle(var142.getCls()) && var142.getCls() != 5 && !var142.getMoving() && !Float.isNaN(var142.getTr().getGx())) {
                                    var115 = true;
                                } else {
                                    var115 = false;
                                }

                                if (var115) {
                                    var38.add(var151);
                                }
                            }

                            List var148 = var38;
                            float[] var143 = new float[var148.size()];
                            var17 = 0;

                            for (int var20 = var148.size(); var17 < var20; var17++) {
                                var18 = var17 + 1;

                                for (int var21 = var148.size(); var18 < var21; var18++) {
                                    label547: {
                                        Detector.Track var39 = ((Detector.Pend)var148.get(var17)).getTr();
                                        Detector.Track var152 = ((Detector.Pend)var148.get(var18)).getTr();
                                        var62 = Math.abs(var39.getGx() - var152.getGx());
                                        var50 = Math.abs(var39.getGy() - var152.getGy());
                                        if (var62 < 3.5F) {
                                            boolean var128;
                                            if (1.8F <= var50 && var50 <= 4.3F) {
                                                var128 = true;
                                            } else {
                                                var128 = false;
                                            }

                                            if (var128) {
                                                var50 = 1.0F;
                                                break label547;
                                            }
                                        }

                                        if (var50 < 1.5F) {
                                            boolean var129;
                                            if (2.0F <= var62 && var62 <= 4.3F) {
                                                var129 = true;
                                            } else {
                                                var129 = false;
                                            }

                                            if (var129) {
                                                var50 = -1.0F;
                                                break label547;
                                            }
                                        }

                                        var50 = 0.0F;
                                    }

                                    var143[var17] += var50;
                                    var143[var18] += var50;
                                }
                            }

                            Iterator var40 = var29.iterator();
                            Intrinsics.checkNotNullExpressionValue(var40, "iterator(...)");

                            while (var40.hasNext()) {
                                Detector.Track var153 = (Detector.Track)var40.next();
                                Intrinsics.checkNotNullExpressionValue(var153, "next(...)");
                                var153 = ((Detector.Pend)(Object)var153).getTr();
                                var153.setRowEv(var153.getRowEv() * 0.93F);
                            }

                            Iterator var41 = var148.iterator();
                            var17 = 0;

                            while (true) {
                                var18 = var17;
                                if (!var41.hasNext()) {
                                    Iterator var137 = var29.iterator();
                                    Intrinsics.checkNotNullExpressionValue(var137, "iterator(...)");

                                    while (var137.hasNext()) {
                                        Detector.Pend var42 = (Detector.Pend)var137.next();
                                        Intrinsics.checkNotNullExpressionValue(var42, "next(...)");
                                        var42 = var42;
                                        Detector.Track var157 = var42.getTr();
                                        int var131 = var42.getCls();
                                        boolean var23 = var42.getMoving();
                                        float var102 = var42.getSpeed();
                                        var42.getLamp();
                                        float[] var161 = var157.getHVotes();
                                        var6 = this.atan2(var157.getGy(), var157.getGx());
                                        if (var23) {
                                            var62 = this.atan2(var157.getGvy(), var157.getGvx());
                                            var84 = (float)Math.rint(var62 / (float) (Math.PI / 2)) * (float) (Math.PI / 2);
                                            var50 = var62;
                                            if (Math.abs(this.wrap(var62 - var84)) < 0.4F) {
                                                var50 = this.wrap(var84);
                                            }
                                        } else if (this.isVehicle(var131) && var131 != 5 && this.orientReady(var157)) {
                                            var50 = this.orientHeading(var157);
                                        } else if (this.isVehicle(var131) && var161[0] + var161[1] + var161[2] + var161[3] > 2.5F) {
                                            var50 = var157.getAxisEv() + var157.getRowEv() * 1.5F;
                                            Boolean var44 = var157.getAlong();
                                            if (var50 > 0.6F) {
                                                var44 = true;
                                            } else if (var50 < -0.6F) {
                                                var44 = false;
                                            } else if (var44 == null) {
                                                if (Math.abs(var157.getGy()) < 8.0F) {
                                                    var22 = true;
                                                } else {
                                                    var22 = false;
                                                }

                                                var44 = var22;
                                            }

                                            var157.setAlong(var44);
                                            if (Intrinsics.areEqual(var157.getAlong(), true)) {
                                                if (var161[0] >= var161[2]) {
                                                    var50 = 0.0F;
                                                } else {
                                                    var50 = (float) Math.PI;
                                                }
                                            } else if (var161[1] >= var161[3]) {
                                                var50 = (float) (Math.PI / 2);
                                            } else {
                                                var50 = -(float) (Math.PI / 2);
                                            }
                                        } else {
                                            var50 = Float.NaN;
                                        }

                                        if (!Float.isNaN(var50) && this.isVehicle(var131)) {
                                            var62 = Math.abs(this.wrap(var50 - var6));
                                            byte var118;
                                            if (var62 > 1.1F && var62 < 2.05F) {
                                                var118 = 2;
                                            } else if (var62 >= 2.05F) {
                                                var118 = 1;
                                            } else {
                                                var118 = 0;
                                            }

                                            for (int var124 = 0; var124 < 3; var124++) {
                                                float[] var45 = var157.getVotes();
                                                var45[var124] *= 0.97F;
                                            }

                                            float[] var46 = var157.getVotes();
                                            var46[var118]++;
                                        }

                                        float[] var47 = var157.getVotes();
                                        byte var119;
                                        if (var47[2] > 3.0F && var47[2] > Math.max(var47[0], var47[1]) * 1.5F) {
                                            var119 = 2;
                                        } else if (var47[1] > 3.0F && var47[1] > var47[0] * 2.0F) {
                                            var119 = 1;
                                        } else {
                                            var119 = 0;
                                        }

                                        float[] var48 = var157.getLampVotes();
                                        var62 = Math.max(var48[0], Math.max(var48[1], var48[2]));
                                        byte var125;
                                        if (var62 < 0.8F) {
                                            var125 = -1;
                                        } else {
                                            boolean var126;
                                            if (var62 == var48[0]) {
                                                var126 = true;
                                            } else {
                                                var126 = false;
                                            }

                                            if (var126) {
                                                var125 = 0;
                                            } else {
                                                if (var62 == var48[1]) {
                                                    var126 = true;
                                                } else {
                                                    var126 = false;
                                                }

                                                if (var126) {
                                                    var125 = 1;
                                                } else {
                                                    var125 = 2;
                                                }
                                            }
                                        }

                                        float var94 = var157.getGx();
                                        var9 = var157.getGy();
                                        if (this.isVehicle(var131)) {
                                            float var104 = this.atan2(var157.getGy(), var157.getGx());
                                            if (Float.isNaN(var50)) {
                                                var6 = 0.0F;
                                            } else {
                                                var6 = this.wrap(var50 - var104);
                                            }

                                            switch (var131) {
                                                case 5:
                                                    var62 = 11.0F;
                                                    break;
                                                case 6:
                                                default:
                                                    var62 = 4.7F;
                                                    break;
                                                case 7:
                                                    var62 = 5.8F;
                                            }

                                            if (var131 == 5) {
                                                var84 = 2.5F;
                                            } else {
                                                var84 = 1.9F;
                                            }

                                            var6 = (Math.abs((float)Math.cos(var6)) * var62 + Math.abs((float)Math.sin(var6)) * var84) / 2.0F;
                                            var62 = var94 + (float)Math.cos(var104) * var6;
                                            var6 = var9 + (float)Math.sin(var104) * var6;
                                        } else {
                                            var6 = var9;
                                            var62 = var94;
                                        }

                                        long var136;
                                        if (var1.getWide()) {
                                            var136 = -var157.getId() - 1000000L;
                                        } else {
                                            var136 = var157.getId();
                                        }

                                        var9 = var157.getGx();
                                        var84 = var157.getGy();
                                        float var106 = var157.getGw();
                                        boolean var24 = var1.getWide();
                                        float var105 = var157.getGvx();
                                        var94 = var157.getGvy();
                                        if (var157.getVelN() >= 3) {
                                            var22 = true;
                                        } else {
                                            var22 = false;
                                        }

                                        var37.add(
                                            new Detector.Obj(
                                                var136, var131, var9, var84, var106, var119, var125, var131, var24, var50, var62, var6, var23, var102, var105, var94, var22
                                            )
                                        );
                                    }

                                    var1.setObjects(var37);
                                    var1.setAtMs(System.currentTimeMillis());
                                    return;
                                }

                                int var130 = var17 + 1;
                                Detector.Pend var155 = (Detector.Pend)var41.next();
                                var17 = var130;
                                if (var155.getMeasured()) {
                                    Detector.Track var156 = var155.getTr();
                                    var156.setRowEv(var156.getRowEv() + RangesKt.coerceIn(var143[var18], -1.0F, 1.0F));
                                    var17 = var130;
                                }
                            }
                        }

                        var158 = (Detector.Track)var35.next();
                        var18 = this.vehicleCls(var158);
                        var50 = var158.getX1() - var158.getX0();
                        var84 = var158.getY1() - var158.getY0();
                        float var10 = (var158.getX0() + var158.getX1()) / 2.0F;
                        var17 = -1;
                        var62 = Float.NaN;
                        switch (var18) {
                            case 9:
                            case 11:
                                var62 = var31.getF();
                                if (var18 == 9) {
                                    var50 = 1.0F;
                                } else {
                                    var50 = 0.75F;
                                }

                                var50 = var62 * var50 / Math.max(var84, 1.0F);
                                if (var50 < 3.0F || var50 > 120.0F) {
                                    continue;
                                }

                                if (var31.getFisheye()) {
                                    var6 = (var10 - var31.getCx()) / var31.getF();
                                    var62 = (float)Math.cos(var6);
                                    var6 = (float)Math.sin(var6);
                                    var62 *= var50;
                                    var50 = var6 * var50;
                                } else {
                                    var6 = (var10 - var31.getCx()) / var31.getF();
                                    var62 = var50;
                                    var50 = var6 * var50;
                                }

                                if (var18 == 9) {
                                    var17 = this.lampColour(var3, var158.getX0(), var158.getY0(), var158.getX1(), var158.getY1());
                                }

                                Unit var164 = Unit.INSTANCE;
                                var6 = var62;
                                var62 = var50;
                                var84 = 0.5F;
                                var50 = Float.NaN;
                                break label482;
                            case 10:
                        }

                        if (!var1.getWide()
                            || !this.isVehicle(var18)
                            || !(var158.getY1() > var3.getH() * 0.92F)
                            || !(var158.getX1() - var158.getX0() > var3.getW() * 0.4F)) {
                            float[] var34 = var31.ground(var10, Math.min(var158.getY1(), (float)var3.getH()));
                            if (var34 != null
                                && !(var34[0] < 1.5F)
                                && !(var34[0] > 100.0F)
                                && !(Math.abs(var34[1]) > 40.0F)
                                && (!(var34[0] < 4.0F) || !(Math.abs(var34[1]) < 1.8F))) {
                                var6 = var34[0];
                                float var8 = var34[1];
                                if (var31.getFisheye()) {
                                    var50 = var50 / var31.getF() * (float)Math.hypot(var34[0], var34[1]);
                                } else {
                                    var50 = var34[0] * var50 / var31.getF();
                                }

                                if (!this.isVehicle(var18) || !(var50 < 0.9F) && !(var50 > 12.0F)) {
                                    int var107;
                                    if (!(var158.getX0() < var3.getW() * 0.015F)
                                        && !(var158.getX1() > var3.getW() * 0.985F)
                                        && !(var158.getY1() > var3.getH() * 0.97F)
                                        && !(var158.getY0() < var3.getH() * 0.01F)) {
                                        var107 = 0;
                                    } else {
                                        var107 = 1;
                                    }

                                    if (this.isVehicle(var18) && (var107 == 0) && var158.getMisses() == 0) {
                                        var62 = var31.offAxis(var10, (var158.getY0() + var158.getY1()) / 2.0F);
                                        if (var31.getFisheye() && var62 > 0.05F) {
                                            var62 /= (float)Math.sin(var62);
                                        } else {
                                            var62 = 1.0F;
                                        }

                                        Detector.Cue var162 = this.vehicleCue(
                                            var3, var158.getX0(), var158.getY0(), var158.getX1(), var158.getY1(), this.atan2(var8, var6), var18, var62
                                        );
                                        var10 = var158.getAxisEv();
                                        float var11 = var162.getEv();
                                        var158.setAxisEv(var10 * 0.93F + var11);

                                        for (int var108 = 0; var108 < 4; var108++) {
                                            float[] var36 = var158.getHVotes();
                                            var36[var108] *= 0.93F;
                                        }

                                        float[] var171 = var158.getHVotes();
                                        var107 = var162.getAlongBin();
                                        var171[var107]++;
                                        var171 = var158.getHVotes();
                                        var107 = var162.getAcrossBin();
                                        var171[var107] += var162.getAcrossW();
                                        if (this.useOrient && var18 != 5 && var84 >= 16.0F) {
                                            var32.add(new Detector.OCand(var158, this.atan2(var8, var6), var62));
                                        }

                                        var62 = 0.0F;
                                    }

                                    Unit var163 = Unit.INSTANCE;
                                    var84 = var50;
                                    var17 = -1;
                                    var50 = var62;
                                    var62 = var8;
                                    break;
                                }
                            }
                        }
                    }

                    if (Float.isNaN(var158.getGx()) || var158.getMisses() == 0 && var158.getHits() <= this.need(var18)) {
                        var158.setGx(var6);
                        var158.setGy(var62);
                        var158.setGw(var84);
                    } else {
                        float var91 = RangesKt.coerceIn(0.6F - var158.getGx() * 0.01F, 0.2F, 0.6F);
                        var158.setGx(var158.getGx() + (var6 - var158.getGx()) * var91);
                        var158.setGy(var158.getGy() + (var62 - var158.getGy()) * var91);
                        var158.setGw(var158.getGw() + (var84 - var158.getGw()) * var91);
                    }

                    long var27 = System.nanoTime();
                    long var25 = (long)(Math.max(0.25F, var6 / 80.0F) * 1.0E9F);
                    if (var158.getMisses() == 0 && var6 < 90.0F) {
                        if (!Float.isNaN(var158.getMx()) && var27 - var158.getMNs() > var25) {
                            float var99 = (float)(var27 - var158.getMNs()) / 1.0E9F;
                            if (var99 < 2.5F) {
                                var84 = var30.getVEgo();
                                float var92 = (var6 - var158.getMx()) / var99;
                                var99 = (var62 - var158.getMy()) / var99;
                                var158.setGvx(var158.getGvx() + (var92 + var84 - var158.getGvx()) * 0.3F);
                                var158.setGvy(var158.getGvy() + (var99 - var158.getGvy()) * 0.3F);
                                var84 = (float)Math.hypot(var158.getGvx(), var158.getGvy());
                                if (var84 > 45.0F) {
                                    var158.setGvx(var158.getGvx() * (45.0F / var84));
                                    var158.setGvy(var158.getGvy() * (45.0F / var84));
                                }

                                var158.setVelN(var158.getVelN() + 1);
                            }

                            var158.setMx(var6);
                            var158.setMy(var62);
                            var158.setMNs(var27);
                        } else if (Float.isNaN(var158.getMx()) || var27 - var158.getMNs() > 2500000000L) {
                            var158.setMx(var6);
                            var158.setMy(var62);
                            var158.setMNs(var27);
                        }
                    }

                    var84 = (float)Math.hypot(var158.getGvx(), var158.getGvy());
                    var62 = this.atan2(var62, var6);
                    float var93 = var158.getGvx();
                    float var103 = (float)Math.cos(var62);
                    float var13 = var158.getGvy();
                    float var14 = (float)Math.sin(var62);
                    float var12 = -var158.getGvx();
                    float var101 = (float)Math.sin(var62);
                    var6 = var158.getGvy();
                    float var15 = (float)Math.cos(var62);
                    if (var1.getWide()) {
                        var62 = 0.16F;
                    } else {
                        var62 = 0.08F;
                    }

                    boolean var132;
                    label510: {
                        float var16 = RangesKt.coerceAtLeast(var158.getGx(), 0.0F);
                        var6 = (float)Math.hypot(var12 * var101 + var6 * var15, Math.max(0.0F, Math.abs(var93 * var103 + var13 * var14) - var62 * var16));
                        if (var158.getHits() >= 6) {
                            if (var1.getWide()) {
                                var62 = var9;
                            } else {
                                var62 = 3.0F;
                            }

                            if (var6 > var62) {
                                var132 = true;
                                break label510;
                            }
                        }

                        var132 = false;
                    }

                    Detector.Pend var165 = new Detector.Pend(var158, var18, var17, var132, var84, Float.isNaN(var50) ^ true);
                    var29.add(var165);
                    if (var17 >= 0) {
                        for (int var120 = 0; var120 < 3; var120++) {
                            float[] var167 = var158.getLampVotes();
                            var167[var120] *= 0.6F;
                        }

                        float[] var159 = var158.getLampVotes();
                        var159[var17]++;
                    } else {
                        for (int var111 = 0; var111 < 3; var111++) {
                            float[] var166 = var158.getLampVotes();
                            var166[var111] *= 0.8F;
                        }
                    }
                }
            }
        }
    }

    private final void mergeObjects() {
        Float valueOf;
        long now = System.currentTimeMillis();
        boolean z = false;
        Iterable $this$filter$iv = CollectionsKt.listOf(new Source[]{this.roadSrc, this.wideSrc});
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            Source it = (Source) element$iv$iv;
            boolean it2 = now - it.getAtMs() < 700;
            if (it2) {
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

    /* VF: from Vineflower (jadx: ) */
    public final Detector.Cue vehicleCue(Detector.Frame var1, float var2, float var3, float var4, float var5, float var6, int var7, float var8) {
        float var9 = Math.max((var4 - var2) / Math.max(1.0F, var5 - var3) * var8, 0.2F);
        Float var17 = 1.9F;
        Triple var18;
        switch (var7) {
            case 5:
                var18 = new Triple(11.0F, 2.5F, 3.2F);
                break;
            case 6:
            default:
                var18 = new Triple(4.7F, var17, 1.5F);
                break;
            case 7:
                var18 = new Triple(5.8F, 2.0F, var17);
        }

        float var10 = ((Number)var18.component1()).floatValue();
        float var12 = ((Number)var18.component2()).floatValue();
        float var13 = ((Number)var18.component3()).floatValue();
        float var11 = (float) Math.PI / 2;
        var8 = vehicleCue$predicted(var6, var12, var10, var13, 0.0F);
        var11 = vehicleCue$predicted(var6, var12, var10, var13, var11);
        var10 = Math.abs((float)Math.log(var11 / var8));
        var11 = Math.abs((float)Math.log(var9 / var11));
        var8 = Math.abs((float)Math.log(var9 / var8));
        if (var10 < 0.15F) {
            var8 = 0.0F;
        } else {
            var8 = RangesKt.coerceIn((var11 - var8) / var10, -1.0F, 1.0F) * Math.min(1.0F, (var10 - 0.15F) / 0.3F);
        }

        Triple var39 = this.redThirds(var1, var2, var3, var4, var5);
        var10 = ((Number)var39.component1()).floatValue();
        var11 = ((Number)var39.component2()).floatValue();
        float var24 = ((Number)var39.component3()).floatValue();
        var11 = Math.min(var10, Math.min(var11, var24));
        var10 -= var11;
        var13 = var24 - var11;
        kotlin.Pair var40 = this.headlightEnds(var1, var2, var3, var4, var5);
        var11 = ((Number)var40.component1()).floatValue();
        var12 = ((Number)var40.component2()).floatValue();
        var8 = var8;
        if (var10 > 8.0F) {
            var8 = var8;
            if (var13 > 8.0F) {
                var8 = var8;
                if (Math.min(var10, var13) > Math.max(var10, var13) * 0.5F) {
                    var8 += 0.8F;
                }
            }
        }

        var9 = var8;
        if (var11 > 50.0F) {
            var9 = var8;
            if (var12 > 50.0F) {
                var9 = var8;
                if (Math.abs(var11 - var12) < 25.0F) {
                    var9 = var8 + 0.5F;
                }
            }
        }

        var8 = var9;
        if (this.symmetry(var1, var2, var3, var4, var5) > 0.55F) {
            var8 = var9 + 0.6F;
        }

        boolean var16 = this.tailLights(var1, var2, var3, var4, var5);
        int var15 = 0;
        byte var14;
        if (var16) {
            var14 = 0;
        } else {
            var14 = 2;
        }

        if (var7 == 2) {
            var15 = this.roofProfile(var1, var2, var3, var4, var5);
        }

        byte var20 = 1;
        if (var10 > 10.0F && var10 > var13 * 1.5F) {
            var2 = 1.0F;
        } else if (var13 > 10.0F && var13 > 1.5F * var10) {
            var2 = 1.0F;
            var20 = 3;
        } else if (var11 > 40.0F && var11 > var12 + 30.0F) {
            var2 = 1.0F;
            var20 = 3;
        } else if (var12 > 40.0F && var12 > var11 + 30.0F) {
            var2 = 1.0F;
        } else if (var15 > 0) {
            var2 = 1.0F;
            var20 = 3;
        } else if (var15 < 0) {
            var2 = 1.0F;
        } else {
            var2 = 0.3F;
            if (!(var6 >= 0.0F)) {
                var20 = 3;
            }
        }

        return new Detector.Cue(var8, var14, var20, var2);
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
                    } else {
                        break;
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
        boolean $this$none$ivB = false;
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
                public final int compare(Object t, Object t2) {
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
                        if (!(it6.getCls() != d.getCls() || iou(it6, d) <= 0.45f)) {
                            $this$none$ivB = false;
                            break;
                        }
                    } else {
                        $this$none$ivB = true;
                        break;
                    }
                }
            } else {
                $this$none$ivB = true;
            }
            if ($this$none$ivB) {
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
