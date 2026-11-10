package com.starpilot.starview;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Choreographer;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.Surface;
import android.view.SurfaceView;
import android.view.ViewConfiguration;
import com.google.android.filament.Box;
import com.google.android.filament.Camera;
import com.google.android.filament.ColorGrading;
import com.google.android.filament.Engine;
import com.google.android.filament.EntityManager;
import com.google.android.filament.IndexBuffer;
import com.google.android.filament.IndirectLight;
import com.google.android.filament.LightManager;
import com.google.android.filament.Material;
import com.google.android.filament.MaterialInstance;
import com.google.android.filament.RenderableManager;
import com.google.android.filament.Renderer;
import com.google.android.filament.Scene;
import com.google.android.filament.Skybox;
import com.google.android.filament.SwapChain;
import com.google.android.filament.Texture;
import com.google.android.filament.TextureSampler;
import com.google.android.filament.ToneMapper;
import com.google.android.filament.TransformManager;
import com.google.android.filament.VertexBuffer;
import com.google.android.filament.View;
import com.google.android.filament.Viewport;
import com.google.android.filament.android.DisplayHelper;
import com.google.android.filament.android.UiHelper;
import com.google.android.filament.gltfio.AssetLoader;
import com.google.android.filament.gltfio.FilamentAsset;
import com.google.android.filament.gltfio.ResourceLoader;
import com.google.android.filament.gltfio.UbershaderProvider;
import com.google.android.filament.utils.KTX1Loader;
import com.google.android.filament.utils.Utils;
import com.starpilot.starview.CarMemory;
import com.starpilot.starview.Detector;
import com.starpilot.starview.LaneBrain;
import com.starpilot.starview.LaneMarks;
import com.starpilot.starview.LaneTracks;
import com.starpilot.starview.LayoutSeg;
import com.starpilot.starview.OsmRoads;
import com.starpilot.starview.RoadShape;
import com.starpilot.starview.SceneView;
import com.starpilot.starview.Traffic;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Pair;
import kotlin.Triple;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.ArrayDeque;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.collections.SetsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.io.ByteStreamsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;

/* compiled from: SceneView.kt */
public final class SceneView extends SurfaceView {
    public static final Companion Companion = new Companion(null);
    public static final float DEFAULT_DISTANCE = 26.0f;
    public static final float DEFAULT_TILT = 17.0f;
    private static final String TAG = "StarView/Scene";
    private final double BRIDGE_H;
    private final double MAP_R;
    private final int MR;
    private final double SIDE_W;
    private final double TAPER;
    private final float XMIN;
    private boolean alwaysDark;
    private AssetLoader assetLoader;
    private final HashMap<String, double[]> baseLanesMem;
    private int bufSel;
    private Camera camera;
    private volatile CarMemory carMemory;
    private long crossAtNs;
    private final HashMap<String, List<double[]>> crossCache;
    private final HashMap<Long, List<double[]>> crossNow;
    private int curN;
    private final boolean[] curbFit;
    private final int[] curbHold;
    private final double[] curbOffS;
    private final double[][] curbS;
    private final double[] dashX;
    private final double[] dashY;
    private final Palette day;
    private Detector detector;
    private DisplayHelper displayHelper;
    private float distance;
    private final float[][] edgeF;
    private float edgeOk;
    private final float[][] edgeS;
    private Car ego;
    private Engine engine;
    private boolean engineAlive;
    private volatile String failure;
    private final double fovDeg;
    private final SceneView$frameCallback$1 frameCallback;
    private final String[] genericKinds;
    private final ArrayList<Car> genericPool;
    private boolean gesturing;
    private volatile double gpsLag;
    private ColorGrading grading;
    private Boolean gradingGrey;
    private final ArrayList<Pair<VertexBuffer, IndexBuffer>> groundBuffers;
    private int groundEnt;
    private Material groundMaterial;
    private MaterialInstance groundMi;
    private Set<Long> hintChain;
    private long hintChainOf;
    private LaneHint hintNow;
    private Set<Long> hintSame;
    private final double horizonShift;
    private IndirectLight ibl;
    private final ByteBuffer[] ibufs;
    private volatile LaneHint laneHint;
    private int laneIdx;
    private boolean laneLinesOn;
    private volatile LaneMarks laneMarks;
    private volatile LaneMemory laneMemory;
    private long laneSeenAt;
    private int laneVote;
    private long laneVoteSince;
    private double laneW;
    private float[] lastEdgeX;
    private long lastFrameNs;
    private boolean lastRibbonEnabled;
    private long lastRibbonFrame;
    private UiStatus lastRibbonStatus;
    private volatile LayoutSeg layout;
    private boolean layoutDark;
    private int layoutEnt;
    private LayoutSeg.Grid layoutGrid;
    private volatile boolean layoutHideRoad;
    private IndexBuffer layoutIb;
    private Material layoutMaterial;
    private MaterialInstance layoutMi;
    private boolean layoutPaintedHide;
    private Texture layoutTex;
    private VertexBuffer layoutVb;
    private double leadGap;
    private long leadGapMs;
    private final HashMap<Long, Double> learnedW;
    private final Runnable longPressRun;
    private final OsmRoads.Align mapAlign;
    private double mapCarOff;
    private boolean mapLanes;
    private volatile String mapStatus;
    private final int maxIdx;
    private final int maxVerts;
    private final float[] memAge;
    private final byte[] memGrp;
    private final float[] memScore;
    private final double[] mx;
    private final double[] my;
    private int nIdx;
    private int nVerts;
    private boolean night;
    private final Palette nightP;
    private final List<Pair<String, Integer>> objectKinds;
    private double odoDist;
    private long odoNs;
    private double odoX;
    private double odoY;
    private double odoYaw;
    private Function0<Unit> onLongPress;
    private Function0<Unit> onTap;
    private Function2<? super Float, ? super Float, Unit> onViewChanged;
    private long oncomingSeenMs;
    private boolean paletteDirty;
    private final ArrayDeque<Pt> parked;
    private long parkedAtMs;
    private LaneTracks.PathState pathStateNow;
    private final int poolPerKind;
    private boolean pressArmed;
    private float pressX;
    private float pressY;
    private float pulseAccel;
    private long pulseLastNs;
    private double pulsePhase;
    private int readAssetSize;
    private boolean ready;
    private Renderer renderer;
    private ResourceLoader resourceLoader;
    private int ribbonEntity;
    private IndexBuffer ribbonIb;
    private Material ribbonMaterial;
    private VertexBuffer ribbonVb;
    private final HashMap<Long, RoadShape.Rounded> roundCache;
    private boolean running;
    private final ScaleGestureDetector scale;
    private Scene scene;
    private int shadowEnt;
    private Material shadowMaterial;
    private MaterialInstance shadowMi;
    private final double[] shapeC;
    private final double[] shapeOff;
    private boolean shapeOk;
    private double shiftE;
    private int shiftJumps;
    private double shiftN;
    private volatile boolean showTraffic;
    private volatile int shownCars;
    private final float[][] sideA;
    private final float[][] sideG;
    private Skybox skybox;
    private final float slop;
    private final byte[] smoothTmp;
    private final ArrayDeque<Snap> snaps;
    private int sun;
    private SwapChain swapChain;
    private Telemetry t;
    private final GestureDetector taps;
    private final ByteBuffer texBuf;
    private long texNs;
    private double texX;
    private double texY;
    private double texYaw;
    private double tiltBoost;
    private float tiltDeg;
    private double tiltTarget;
    private final LaneTracks tracks;
    private final Traffic traffic;
    private double turnA;
    private float twoFingerY;
    private UiHelper uiHelper;
    private UiStatus uiStatus;
    private final ByteBuffer[] vbufs;
    private View view;
    private int viewH;
    private int viewW;
    private volatile String yawSrc;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Type inference failed for: r5v6, types: [com.starpilot.starview.SceneView$frameCallback$1] */
    public SceneView(Context ctx, AttributeSet attrs) {
        super(ctx, attrs);
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        this.uiStatus = UiStatus.DISENGAGED;
        this.distance = 26.0f;
        this.tiltDeg = 17.0f;
        this.fovDeg = 36.0d;
        this.day = new Palette(Color.rgb(244, 245, 247), Color.rgb(180, 185, 194), Color.rgb(224, 176, 42), Color.rgb(229, 59, 59), Color.rgb(59, 139, 245), Color.rgb(185, 191, 202), Color.rgb(240, 138, 46), Color.rgb(154, 159, 168), 2.6f, 1.0f, new float[]{1.0f, 0.98f, 0.95f});
        this.nightP = new Palette(Color.rgb(11, 13, 18), Color.rgb(107, 114, 126), Color.rgb(201, 154, 30), Color.rgb(214, 51, 51), Color.rgb(59, 139, 245), Color.rgb(74, 80, 91), Color.rgb(224, 124, 34), Color.rgb(122, 128, 139), 0.9f, 0.35f, new float[]{0.75f, 0.8f, 1.0f});
        this.alwaysDark = true;
        this.paletteDirty = true;
        this.twoFingerY = Float.NaN;
        this.scale = new ScaleGestureDetector(ctx, new ScaleGestureDetector.SimpleOnScaleGestureListener() { // from class: com.starpilot.starview.SceneView$scale$1
            @Override // android.view.ScaleGestureDetector.SimpleOnScaleGestureListener, android.view.ScaleGestureDetector.OnScaleGestureListener
            public boolean onScale(ScaleGestureDetector d) {
                Intrinsics.checkNotNullParameter(d, "d");
                SceneView.this.setDistance(SceneView.this.getDistance() / d.getScaleFactor());
                SceneView.this.gesturing = true;
                return true;
            }
        });
        this.taps = new GestureDetector(ctx, new GestureDetector.SimpleOnGestureListener() { // from class: com.starpilot.starview.SceneView$taps$1
            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
            public boolean onSingleTapConfirmed(MotionEvent e) {
                Intrinsics.checkNotNullParameter(e, "e");
                Function0<Unit> onTap = SceneView.this.getOnTap();
                if (onTap == null) {
                    return true;
                }
                onTap.invoke();
                return true;
            }

            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
            public boolean onDoubleTap(MotionEvent e) {
                Intrinsics.checkNotNullParameter(e, "e");
                SceneView.this.setDistance(26.0f);
                SceneView.this.setTiltDeg(17.0f);
                Function2<Float, Float, Unit> onViewChanged = SceneView.this.getOnViewChanged();
                if (onViewChanged == null) {
                    return true;
                }
                onViewChanged.invoke(Float.valueOf(SceneView.this.getDistance()), Float.valueOf(SceneView.this.getTiltDeg()));
                return true;
            }

            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public boolean onDown(MotionEvent e) {
                Intrinsics.checkNotNullParameter(e, "e");
                return true;
            }
        });
        this.slop = ViewConfiguration.get(ctx).getScaledTouchSlop();
        this.longPressRun = new Runnable() { // from class: com.starpilot.starview.SceneView$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                SceneView.longPressRun$lambda$0(SceneView.this);
            }
        };
        this.maxVerts = 65536;
        this.maxIdx = this.maxVerts * 3;
        ByteBuffer[] byteBufferArr = new ByteBuffer[2];
        for (int i = 0; i < 2; i++) {
            byteBufferArr[i] = ByteBuffer.allocateDirect(this.maxVerts * 16).order(ByteOrder.nativeOrder());
        }
        this.vbufs = byteBufferArr;
        ByteBuffer[] byteBufferArr2 = new ByteBuffer[2];
        for (int i2 = 0; i2 < 2; i2++) {
            byteBufferArr2[i2] = ByteBuffer.allocateDirect(this.maxIdx * 4).order(ByteOrder.nativeOrder());
        }
        this.ibufs = byteBufferArr2;
        this.lastRibbonFrame = -2L;
        this.genericPool = new ArrayList<>();
        this.genericKinds = new String[]{"car_sedan.glb", "car_suv.glb", "car_hatch.glb", "car_coupe.glb"};
        this.poolPerKind = 7;
        this.objectKinds = CollectionsKt.listOf((Object[]) new Pair[]{TuplesKt.to("truck.glb", 4), TuplesKt.to("bus.glb", 2), TuplesKt.to("person.glb", 8), TuplesKt.to("bicycle.glb", 3), TuplesKt.to("motorcycle.glb", 2), TuplesKt.to("traffic_light.glb", 3), TuplesKt.to("stop_sign.glb", 3)});
        this.traffic = new Traffic();
        Utils.init();
        this.failure = "";
        this.groundBuffers = new ArrayList<>();
        this.showTraffic = true;
        this.hintChain = SetsKt.emptySet();
        this.hintSame = SetsKt.emptySet();
        this.layoutDark = true;
        this.MR = 100;
        this.XMIN = -10.0f;
        this.snaps = new ArrayDeque<>();
        this.memGrp = new byte[this.MR * 80];
        this.memAge = new float[this.MR * 80];
        this.memScore = new float[this.MR * 80];
        ByteBuffer order = ByteBuffer.allocateDirect(this.MR * 80 * 4).order(ByteOrder.nativeOrder());
        Intrinsics.checkNotNullExpressionValue(order, "order(...)");
        this.texBuf = order;
        this.smoothTmp = new byte[this.MR * 80];
        this.frameCallback = new Choreographer.FrameCallback() { // from class: com.starpilot.starview.SceneView$frameCallback$1
            /* JADX WARN: Code restructure failed: missing block: B:12:0x0031, code lost:
            
                r0 = r5.this$0.swapChain;
             */
            @Override // android.view.Choreographer.FrameCallback
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public void doFrame(long frameTimeNanos) {
                boolean z;
                boolean z2;
                UiHelper uiHelper;
                SwapChain sc;
                Renderer renderer;
                Renderer renderer2;
                View view;
                Renderer renderer3;
                z = SceneView.this.running;
                if (z) {
                    Choreographer.getInstance().postFrameCallback(this);
                    z2 = SceneView.this.ready;
                    if (z2) {
                        uiHelper = SceneView.this.uiHelper;
                        Renderer renderer4 = null;
                        if (uiHelper == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("uiHelper");
                            uiHelper = null;
                        }
                        if (uiHelper.isReadyToRender() && sc != null) {
                            SceneView.this.updateScene(frameTimeNanos);
                            renderer = SceneView.this.renderer;
                            if (renderer == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("renderer");
                                renderer = null;
                            }
                            if (renderer.beginFrame(sc, frameTimeNanos)) {
                                renderer2 = SceneView.this.renderer;
                                if (renderer2 == null) {
                                    Intrinsics.throwUninitializedPropertyAccessException("renderer");
                                    renderer2 = null;
                                }
                                view = SceneView.this.view;
                                if (view == null) {
                                    Intrinsics.throwUninitializedPropertyAccessException("view");
                                    view = null;
                                }
                                renderer2.render(view);
                                renderer3 = SceneView.this.renderer;
                                if (renderer3 == null) {
                                    Intrinsics.throwUninitializedPropertyAccessException("renderer");
                                } else {
                                    renderer4 = renderer3;
                                }
                                renderer4.endFrame();
                            }
                        }
                    }
                }
            }
        };
        this.leadGap = 99.0d;
        this.pathStateNow = LaneTracks.PathState.OFF;
        this.tracks = new LaneTracks(0.0f, 1, null);
        float[][] fArr = new float[2][];
        for (int i3 = 0; i3 < 2; i3++) {
            fArr[i3] = new float[0];
        }
        this.edgeS = fArr;
        float[][] fArr2 = new float[2][];
        for (int i4 = 0; i4 < 2; i4++) {
            fArr2[i4] = new float[0];
        }
        this.sideA = fArr2;
        float[][] fArr3 = new float[2][];
        for (int i5 = 0; i5 < 2; i5++) {
            fArr3[i5] = new float[0];
        }
        this.sideG = fArr3;
        this.SIDE_W = 3.0d;
        this.parked = new ArrayDeque<>();
        double[][] dArr = new double[2][];
        for (int i6 = 0; i6 < 2; i6++) {
            dArr[i6] = new double[0];
        }
        this.curbS = dArr;
        this.curbHold = new int[2];
        this.curbFit = new boolean[2];
        this.curbOffS = new double[2];
        float[][] fArr4 = new float[2][];
        for (int i7 = 0; i7 < 2; i7++) {
            fArr4[i7] = new float[0];
        }
        this.edgeF = fArr4;
        this.shapeC = new double[3];
        this.shapeOff = new double[2];
        this.mapAlign = new OsmRoads.Align();
        this.gpsLag = 0.2d;
        this.yawSrc = "steering";
        this.mapStatus = "";
        this.mx = new double[512];
        this.my = new double[512];
        this.shiftJumps = -1;
        this.learnedW = new HashMap<>();
        this.MAP_R = 140.0d;
        this.lastEdgeX = new float[0];
        this.laneW = 3.6d;
        this.baseLanesMem = new HashMap<>();
        this.laneVote = -1;
        this.laneIdx = -1;
        this.TAPER = 45.0d;
        this.BRIDGE_H = 5.5d;
        this.roundCache = new HashMap<>();
        this.crossCache = new HashMap<>();
        this.crossNow = new HashMap<>();
        this.laneLinesOn = true;
        this.dashX = new double[512];
        this.dashY = new double[512];
    }

    public /* synthetic */ SceneView(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet);
    }

    public final Telemetry getT() {
        return this.t;
    }

    public final void setT(Telemetry telemetry) {
        this.t = telemetry;
    }

    public final boolean getNight() {
        return this.night;
    }

    public final void setNight(boolean v) {
        if (this.night != v) {
            this.night = v;
            this.paletteDirty = true;
        }
    }

    public final UiStatus getUiStatus() {
        return this.uiStatus;
    }

    public final void setUiStatus(UiStatus uiStatus) {
        Intrinsics.checkNotNullParameter(uiStatus, "<set-?>");
        this.uiStatus = uiStatus;
    }

    public final Function0<Unit> getOnTap() {
        return this.onTap;
    }

    public final void setOnTap(Function0<Unit> function0) {
        this.onTap = function0;
    }

    public final Function0<Unit> getOnLongPress() {
        return this.onLongPress;
    }

    public final void setOnLongPress(Function0<Unit> function0) {
        this.onLongPress = function0;
    }

    public final Function2<Float, Float, Unit> getOnViewChanged() {
        return this.onViewChanged;
    }

    public final void setOnViewChanged(Function2<? super Float, ? super Float, Unit> function2) {
        this.onViewChanged = function2;
    }

    public final float getDistance() {
        return this.distance;
    }

    public final void setDistance(float v) {
        this.distance = RangesKt.coerceIn(v, 15.0f, 90.0f);
    }

    public final float getTiltDeg() {
        return this.tiltDeg;
    }

    public final void setTiltDeg(float v) {
        this.tiltDeg = RangesKt.coerceIn(v, 10.0f, 60.0f);
    }

    /* compiled from: SceneView.kt */
    private static final class Palette {
        private final int bg;
        private final int dot;
        private final int edge;
        private final float iblIntensity;
        private final int lane;
        private final int laneAlt;
        private final int pathExp;
        private final int pathOff;
        private final int pathOn;
        private final float[] sunColor;
        private final float sunIntensity;

        public Palette(int bg, int lane, int laneAlt, int edge, int pathOn, int pathOff, int pathExp, int dot, float sunIntensity, float iblIntensity, float[] sunColor) {
            Intrinsics.checkNotNullParameter(sunColor, "sunColor");
            this.bg = bg;
            this.lane = lane;
            this.laneAlt = laneAlt;
            this.edge = edge;
            this.pathOn = pathOn;
            this.pathOff = pathOff;
            this.pathExp = pathExp;
            this.dot = dot;
            this.sunIntensity = sunIntensity;
            this.iblIntensity = iblIntensity;
            this.sunColor = sunColor;
        }

        public final int getBg() {
            return this.bg;
        }

        public final int getEdge() {
            return this.edge;
        }

        public final int getLane() {
            return this.lane;
        }

        public final int getLaneAlt() {
            return this.laneAlt;
        }

        public final int getPathExp() {
            return this.pathExp;
        }

        public final int getPathOff() {
            return this.pathOff;
        }

        public final int getPathOn() {
            return this.pathOn;
        }

        public final int getDot() {
            return this.dot;
        }

        public final float getIblIntensity() {
            return this.iblIntensity;
        }

        public final float[] getSunColor() {
            return this.sunColor;
        }

        public final float getSunIntensity() {
            return this.sunIntensity;
        }
    }

    public final boolean getAlwaysDark() {
        return this.alwaysDark;
    }

    public final void setAlwaysDark(boolean v) {
        if (this.alwaysDark != v) {
            this.alwaysDark = v;
            this.paletteDirty = true;
        }
    }

    private final Palette getPal() {
        return (this.night || this.alwaysDark) ? this.nightP : this.day;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void longPressRun$lambda$0(SceneView this$0) {
        if (this$0.pressArmed) {
            this$0.pressArmed = false;
            Function0<Unit> function0 = this$0.onLongPress;
            if (function0 != null) {
                function0.invoke();
            }
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent e) {
        Intrinsics.checkNotNullParameter(e, "e");
        switch (e.getActionMasked()) {
            case 0:
                this.pressX = e.getX();
                this.pressY = e.getY();
                this.pressArmed = !EdgeGuard.INSTANCE.inSystemGestureZone(this, e.getX(), e.getY());
                removeCallbacks(this.longPressRun);
                if (this.pressArmed) {
                    postDelayed(this.longPressRun, 800L);
                    break;
                }
                break;
            case 1:
            case 3:
            case 5:
                this.pressArmed = false;
                removeCallbacks(this.longPressRun);
                break;
            case 2:
                if (this.pressArmed) {
                    float f = 2;
                    if (Math.abs(e.getX() - this.pressX) > this.slop * f || Math.abs(e.getY() - this.pressY) > this.slop * f) {
                        this.pressArmed = false;
                        removeCallbacks(this.longPressRun);
                        break;
                    }
                }
                break;
        }
        this.scale.onTouchEvent(e);
        if (e.getPointerCount() >= 2) {
            float y = (e.getY(0) + e.getY(1)) / 2.0f;
            if (!Float.isNaN(this.twoFingerY) && e.getActionMasked() == 2 && !this.scale.isInProgress()) {
                setTiltDeg(this.tiltDeg + ((y - this.twoFingerY) * 0.15f));
                this.gesturing = true;
            }
            this.twoFingerY = y;
        } else {
            this.twoFingerY = Float.NaN;
        }
        if (e.getActionMasked() == 1 || e.getActionMasked() == 3) {
            if (this.gesturing) {
                this.gesturing = false;
                Function2<? super Float, ? super Float, Unit> function2 = this.onViewChanged;
                if (function2 != null) {
                    function2.invoke(Float.valueOf(this.distance), Float.valueOf(this.tiltDeg));
                }
            }
            this.twoFingerY = Float.NaN;
        }
        if (e.getPointerCount() < 2 && !this.gesturing) {
            this.taps.onTouchEvent(e);
        }
        return true;
    }

    private final ByteBuffer getVbuf() {
        return this.vbufs[this.bufSel];
    }

    private final ByteBuffer getIbuf() {
        return this.ibufs[this.bufSel];
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: SceneView.kt */
    final class Car {
        private final FilamentAsset asset;
        private final MaterialInstance body;
        private long firstShownMs;
        private long flipSince;
        private final MaterialInstance head;
        private double heading;
        private long id;
        private boolean inScene;
        private final String kind;
        private final MaterialInstance lampG;
        private final MaterialInstance lampR;
        private int lampShown;
        private final MaterialInstance lampY;
        private long lastUsed;
        private final double length;
        private float painted;
        private final MaterialInstance sigL;
        private final MaterialInstance sigR;
        private final MaterialInstance tail;
        private final MaterialInstance tailL;
        private final MaterialInstance tailR;
        final /* synthetic */ SceneView this$0;
        private boolean tinted;
        private double wx;
        private double wy;
        private double x;
        private double y;

        public Car(SceneView this$0, FilamentAsset asset, double length, String kind) {
            Intrinsics.checkNotNullParameter(asset, "asset");
            Intrinsics.checkNotNullParameter(kind, "kind");
            this.this$0 = this$0;
            this.asset = asset;
            this.length = length;
            this.kind = kind;
            this.body = mi("Body");
            this.painted = Float.NaN;
            this.lampR = mi("LampR");
            this.lampY = mi("LampY");
            this.lampG = mi("LampG");
            this.lampShown = -2;
            this.tail = mi("TailLight");
            this.tailL = mi("TailLightL");
            this.tailR = mi("TailLightR");
            this.head = mi("HeadLight");
            this.sigL = mi("SignalL");
            this.sigR = mi("SignalR");
            this.x = Double.NaN;
            this.id = Long.MIN_VALUE;
            this.wx = Double.NaN;
            this.wy = Double.NaN;
        }

        public final FilamentAsset getAsset() {
            return this.asset;
        }

        public final String getKind() {
            return this.kind;
        }

        public final double getLength() {
            return this.length;
        }

        public final MaterialInstance getBody() {
            return this.body;
        }

        public final void grey() {
            MaterialInstance materialInstance = this.body;
            if (materialInstance != null) {
                materialInstance.setParameter("baseColorFactor", 0.5f, 0.51f, 0.52f, 1.0f);
            }
        }

        public final void remembered(boolean on) {
            if (on == this.tinted || this.body == null) {
                return;
            }
            this.tinted = on;
            if (on) {
                this.body.setParameter("baseColorFactor", 0.55f, 0.68f, 0.88f, 1.0f);
            } else {
                grey();
            }
        }

        public final MaterialInstance getLampG() {
            return this.lampG;
        }

        public final MaterialInstance getLampR() {
            return this.lampR;
        }

        public final MaterialInstance getLampY() {
            return this.lampY;
        }

        public final void lamp(int state) {
            if (state == this.lampShown) {
                return;
            }
            this.lampShown = state;
            MaterialInstance materialInstance = this.lampR;
            if (materialInstance != null) {
                materialInstance.setParameter("emissiveFactor", state == 0 ? 3.0f : 0.0f, (state == 0 ? 3.0f : 0.0f) * 0.05f, 0.0f);
            }
            MaterialInstance materialInstance2 = this.lampY;
            if (materialInstance2 != null) {
                materialInstance2.setParameter("emissiveFactor", state != 1 ? 0.0f : 3.0f, state == 1 ? 1.6f : 0.0f, 0.0f);
            }
            MaterialInstance materialInstance3 = this.lampG;
            if (materialInstance3 != null) {
                materialInstance3.setParameter("emissiveFactor", 0.0f, state == 2 ? 2.6f : 0.0f, state == 2 ? 1.2f : 0.0f);
            }
        }

        public final MaterialInstance getHead() {
            return this.head;
        }

        public final MaterialInstance getSigL() {
            return this.sigL;
        }

        public final MaterialInstance getSigR() {
            return this.sigR;
        }

        public final MaterialInstance getTail() {
            return this.tail;
        }

        public final MaterialInstance getTailL() {
            return this.tailL;
        }

        public final MaterialInstance getTailR() {
            return this.tailR;
        }

        public final boolean getInScene() {
            return this.inScene;
        }

        public final void setInScene(boolean z) {
            this.inScene = z;
        }

        public final double getHeading() {
            return this.heading;
        }

        public final double getX() {
            return this.x;
        }

        public final double getY() {
            return this.y;
        }

        public final void setHeading(double d) {
            this.heading = d;
        }

        public final void setX(double d) {
            this.x = d;
        }

        public final void setY(double d) {
            this.y = d;
        }

        public final long getId() {
            return this.id;
        }

        public final long getLastUsed() {
            return this.lastUsed;
        }

        public final void setId(long j) {
            this.id = j;
        }

        public final void setLastUsed(long j) {
            this.lastUsed = j;
        }

        public final long getFlipSince() {
            return this.flipSince;
        }

        public final void setFlipSince(long j) {
            this.flipSince = j;
        }

        public final double getWx() {
            return this.wx;
        }

        public final double getWy() {
            return this.wy;
        }

        public final void setWx(double d) {
            this.wx = d;
        }

        public final void setWy(double d) {
            this.wy = d;
        }

        public final long getFirstShownMs() {
            return this.firstShownMs;
        }

        public final void setFirstShownMs(long j) {
            this.firstShownMs = j;
        }

        private final MaterialInstance mi(String name) {
            Object element$iv;
            Object[] materialInstances = this.asset.getInstance().getMaterialInstances();
            Intrinsics.checkNotNullExpressionValue(materialInstances, "getMaterialInstances(...)");
            Object[] $this$firstOrNull$iv = materialInstances;
            int length = $this$firstOrNull$iv.length;
            int i = 0;
            while (true) {
                if (i < length) {
                    element$iv = $this$firstOrNull$iv[i];
                    MaterialInstance it = (MaterialInstance) element$iv;
                    if (Intrinsics.areEqual(it.getName(), name)) {
                        break;
                    }
                    i++;
                } else {
                    element$iv = null;
                    break;
                }
            }
            return (MaterialInstance) element$iv;
        }

        public final void show(boolean on) {
            if (on == this.inScene) {
                return;
            }
            Scene scene = null;
            Scene scene2 = this.this$0.scene;
            if (on) {
                if (scene2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("scene");
                } else {
                    scene = scene2;
                }
                scene.addEntities(this.asset.getEntities());
            } else {
                if (scene2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("scene");
                } else {
                    scene = scene2;
                }
                scene.removeEntities(this.asset.getEntities());
            }
            this.inScene = on;
        }

        public final void place(double px, double py, double hdg) {
            double a = 3.141592653589793d - hdg;
            double c = Math.cos(a);
            double s = Math.sin(a);
            float[] m = {(float) c, 0.0f, (float) (-s), 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, (float) s, 0.0f, (float) c, 0.0f, (float) py, 0.0f, (float) (-px), 1.0f};
            Engine engine = this.this$0.engine;
            if (engine == null) {
                Intrinsics.throwUninitializedPropertyAccessException("engine");
                engine = null;
            }
            TransformManager tm = engine.getTransformManager();
            Intrinsics.checkNotNullExpressionValue(tm, "getTransformManager(...)");
            tm.setTransform(tm.getInstance(this.asset.getRoot()), m);
        }

        public final void lights(boolean brake, boolean running, boolean left, boolean right, boolean blinkOn) {
            float tr;
            float l;
            if (brake) {
                tr = 1.6f;
            } else {
                tr = running ? 0.35f : 0.0f;
            }
            MaterialInstance materialInstance = this.tail;
            if (materialInstance != null) {
                materialInstance.setParameter("emissiveFactor", tr, 0.03f * tr, 0.02f * tr);
            }
            lights$side(tr, this.tailL, left && blinkOn);
            lights$side(tr, this.tailR, right && blinkOn);
            float h = running ? 1.2f : 0.0f;
            MaterialInstance materialInstance2 = this.head;
            if (materialInstance2 != null) {
                materialInstance2.setParameter("emissiveFactor", h, 0.97f * h, 0.9f * h);
            }
            float r = 1.4f;
            if (!left || !blinkOn) {
                l = 0.0f;
            } else {
                l = 1.4f;
            }
            if (!right || !blinkOn) {
                r = 0.0f;
            }
            MaterialInstance materialInstance3 = this.sigL;
            if (materialInstance3 != null) {
                materialInstance3.setParameter("emissiveFactor", l, l * 0.55f, 0.0f);
            }
            MaterialInstance materialInstance4 = this.sigR;
            if (materialInstance4 != null) {
                materialInstance4.setParameter("emissiveFactor", r, 0.55f * r, 0.0f);
            }
        }

        private static final void lights$side(float tr, MaterialInstance mi, boolean blink) {
            if (mi == null) {
                return;
            }
            if (blink) {
                mi.setParameter("emissiveFactor", 1.6f, 0.7f, 0.02f);
            } else {
                mi.setParameter("emissiveFactor", tr, 0.03f * tr, 0.02f * tr);
            }
        }
    }

    public final Detector getDetector() {
        return this.detector;
    }

    public final void setDetector(Detector detector) {
        this.detector = detector;
    }

    @Override // android.view.SurfaceView, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!this.engineAlive) {
            try {
                setupFilament();
                this.engineAlive = true;
            } catch (Throwable e) {
                Log.e("StarView/Scene", "Filament setup failed", e);
                this.failure = e.toString();
            }
        }
        startRendering();
    }

    public final String getFailure() {
        return this.failure;
    }

    public final void setFailure(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.failure = str;
    }

    @Override // android.view.SurfaceView, android.view.View
    protected void onDetachedFromWindow() {
        stopRendering();
        if (this.engineAlive) {
            destroyFilament();
        }
        super.onDetachedFromWindow();
    }

    private final void destroyFilament() {
        this.engineAlive = false;
        this.ready = false;
        try {
            UiHelper uiHelper = this.uiHelper;
            Engine engine = null;
            if (uiHelper == null) {
                Intrinsics.throwUninitializedPropertyAccessException("uiHelper");
                uiHelper = null;
            }
            uiHelper.detach();
            SwapChain it = this.swapChain;
            if (it != null) {
                Engine engine2 = this.engine;
                if (engine2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("engine");
                    engine2 = null;
                }
                engine2.destroySwapChain(it);
            }
            this.swapChain = null;
            Engine engine3 = this.engine;
            if (engine3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("engine");
                engine3 = null;
            }
            engine3.flushAndWait();
            Iterator<Car> it2 = this.genericPool.iterator();
            Intrinsics.checkNotNullExpressionValue(it2, "iterator(...)");
            while (it2.hasNext()) {
                Car next = it2.next();
                Intrinsics.checkNotNullExpressionValue(next, "next(...)");
                Car c = next;
                c.show(false);
                AssetLoader assetLoader = this.assetLoader;
                if (assetLoader == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("assetLoader");
                    assetLoader = null;
                }
                assetLoader.destroyAsset(c.getAsset());
            }
            this.genericPool.clear();
            Car it3 = this.ego;
            if (it3 != null) {
                it3.show(false);
                AssetLoader assetLoader2 = this.assetLoader;
                if (assetLoader2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("assetLoader");
                    assetLoader2 = null;
                }
                assetLoader2.destroyAsset(it3.getAsset());
            }
            this.ego = null;
            ResourceLoader resourceLoader = this.resourceLoader;
            if (resourceLoader == null) {
                Intrinsics.throwUninitializedPropertyAccessException("resourceLoader");
                resourceLoader = null;
            }
            resourceLoader.destroy();
            AssetLoader assetLoader3 = this.assetLoader;
            if (assetLoader3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("assetLoader");
                assetLoader3 = null;
            }
            assetLoader3.destroy();
            Engine engine4 = this.engine;
            if (engine4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("engine");
                engine4 = null;
            }
            RenderableManager rm = engine4.getRenderableManager();
            Intrinsics.checkNotNullExpressionValue(rm, "getRenderableManager(...)");
            int[] iArr = {this.ribbonEntity, this.groundEnt, this.shadowEnt, this.layoutEnt};
            for (int i = 0; i < 4; i++) {
                int e = iArr[i];
                Scene scene = this.scene;
                if (scene == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("scene");
                    scene = null;
                }
                scene.removeEntity(e);
                rm.destroy(e);
                EntityManager.get().destroy(e);
            }
            Engine engine5 = this.engine;
            if (engine5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("engine");
                engine5 = null;
            }
            VertexBuffer vertexBuffer = this.layoutVb;
            if (vertexBuffer == null) {
                Intrinsics.throwUninitializedPropertyAccessException("layoutVb");
                vertexBuffer = null;
            }
            engine5.destroyVertexBuffer(vertexBuffer);
            Engine engine6 = this.engine;
            if (engine6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("engine");
                engine6 = null;
            }
            IndexBuffer indexBuffer = this.layoutIb;
            if (indexBuffer == null) {
                Intrinsics.throwUninitializedPropertyAccessException("layoutIb");
                indexBuffer = null;
            }
            engine6.destroyIndexBuffer(indexBuffer);
            Engine engine7 = this.engine;
            if (engine7 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("engine");
                engine7 = null;
            }
            MaterialInstance materialInstance = this.layoutMi;
            if (materialInstance == null) {
                Intrinsics.throwUninitializedPropertyAccessException("layoutMi");
                materialInstance = null;
            }
            engine7.destroyMaterialInstance(materialInstance);
            Engine engine8 = this.engine;
            if (engine8 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("engine");
                engine8 = null;
            }
            Texture texture = this.layoutTex;
            if (texture == null) {
                Intrinsics.throwUninitializedPropertyAccessException("layoutTex");
                texture = null;
            }
            engine8.destroyTexture(texture);
            Engine engine9 = this.engine;
            if (engine9 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("engine");
                engine9 = null;
            }
            Material material = this.layoutMaterial;
            if (material == null) {
                Intrinsics.throwUninitializedPropertyAccessException("layoutMaterial");
                material = null;
            }
            engine9.destroyMaterial(material);
            Engine engine10 = this.engine;
            if (engine10 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("engine");
                engine10 = null;
            }
            VertexBuffer vertexBuffer2 = this.ribbonVb;
            if (vertexBuffer2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ribbonVb");
                vertexBuffer2 = null;
            }
            engine10.destroyVertexBuffer(vertexBuffer2);
            Engine engine11 = this.engine;
            if (engine11 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("engine");
                engine11 = null;
            }
            IndexBuffer indexBuffer2 = this.ribbonIb;
            if (indexBuffer2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ribbonIb");
                indexBuffer2 = null;
            }
            engine11.destroyIndexBuffer(indexBuffer2);
            Iterator<Pair<VertexBuffer, IndexBuffer>> it4 = this.groundBuffers.iterator();
            Intrinsics.checkNotNullExpressionValue(it4, "iterator(...)");
            while (it4.hasNext()) {
                Pair<VertexBuffer, IndexBuffer> next2 = it4.next();
                Intrinsics.checkNotNullExpressionValue(next2, "next(...)");
                Pair<VertexBuffer, IndexBuffer> pair = next2;
                VertexBuffer vb = pair.component1();
                IndexBuffer ib = pair.component2();
                Engine engine12 = this.engine;
                if (engine12 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("engine");
                    engine12 = null;
                }
                engine12.destroyVertexBuffer(vb);
                Engine engine13 = this.engine;
                if (engine13 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("engine");
                    engine13 = null;
                }
                engine13.destroyIndexBuffer(ib);
            }
            this.groundBuffers.clear();
            Engine engine14 = this.engine;
            if (engine14 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("engine");
                engine14 = null;
            }
            MaterialInstance materialInstance2 = this.groundMi;
            if (materialInstance2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("groundMi");
                materialInstance2 = null;
            }
            engine14.destroyMaterialInstance(materialInstance2);
            Engine engine15 = this.engine;
            if (engine15 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("engine");
                engine15 = null;
            }
            MaterialInstance materialInstance3 = this.shadowMi;
            if (materialInstance3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("shadowMi");
                materialInstance3 = null;
            }
            engine15.destroyMaterialInstance(materialInstance3);
            Engine engine16 = this.engine;
            if (engine16 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("engine");
                engine16 = null;
            }
            Material material2 = this.groundMaterial;
            if (material2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("groundMaterial");
                material2 = null;
            }
            engine16.destroyMaterial(material2);
            Engine engine17 = this.engine;
            if (engine17 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("engine");
                engine17 = null;
            }
            Material material3 = this.shadowMaterial;
            if (material3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("shadowMaterial");
                material3 = null;
            }
            engine17.destroyMaterial(material3);
            Engine engine18 = this.engine;
            if (engine18 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("engine");
                engine18 = null;
            }
            Material material4 = this.ribbonMaterial;
            if (material4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ribbonMaterial");
                material4 = null;
            }
            engine18.destroyMaterial(material4);
            Scene scene2 = this.scene;
            if (scene2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("scene");
                scene2 = null;
            }
            scene2.removeEntity(this.sun);
            Engine engine19 = this.engine;
            if (engine19 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("engine");
                engine19 = null;
            }
            engine19.getLightManager().destroy(this.sun);
            EntityManager.get().destroy(this.sun);
            IndirectLight it5 = this.ibl;
            if (it5 != null) {
                Engine engine20 = this.engine;
                if (engine20 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("engine");
                    engine20 = null;
                }
                engine20.destroyIndirectLight(it5);
            }
            this.ibl = null;
            Skybox it6 = this.skybox;
            if (it6 != null) {
                Engine engine21 = this.engine;
                if (engine21 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("engine");
                    engine21 = null;
                }
                engine21.destroySkybox(it6);
            }
            this.skybox = null;
            ColorGrading it7 = this.grading;
            if (it7 != null) {
                Engine engine22 = this.engine;
                if (engine22 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("engine");
                    engine22 = null;
                }
                engine22.destroyColorGrading(it7);
            }
            this.grading = null;
            this.gradingGrey = null;
            Engine engine23 = this.engine;
            if (engine23 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("engine");
                engine23 = null;
            }
            Renderer renderer = this.renderer;
            if (renderer == null) {
                Intrinsics.throwUninitializedPropertyAccessException("renderer");
                renderer = null;
            }
            engine23.destroyRenderer(renderer);
            Engine engine24 = this.engine;
            if (engine24 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("engine");
                engine24 = null;
            }
            View view = this.view;
            if (view == null) {
                Intrinsics.throwUninitializedPropertyAccessException("view");
                view = null;
            }
            engine24.destroyView(view);
            Engine engine25 = this.engine;
            if (engine25 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("engine");
                engine25 = null;
            }
            Scene scene3 = this.scene;
            if (scene3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("scene");
                scene3 = null;
            }
            engine25.destroyScene(scene3);
            Camera camera = this.camera;
            if (camera == null) {
                Intrinsics.throwUninitializedPropertyAccessException("camera");
                camera = null;
            }
            int ce = camera.getEntity();
            Engine engine26 = this.engine;
            if (engine26 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("engine");
                engine26 = null;
            }
            engine26.destroyCameraComponent(ce);
            EntityManager.get().destroy(ce);
            Engine engine27 = this.engine;
            if (engine27 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("engine");
            } else {
                engine = engine27;
            }
            engine.destroy();
        } catch (Exception e2) {
            Log.w("StarView/Scene", "teardown: " + e2);
        }
    }

    @Override // android.view.SurfaceView, android.view.View
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        if (visibility != 0 || getVisibility() != 0) {
            stopRendering();
        } else {
            startRendering();
        }
    }

    @Override // android.view.View
    protected void onVisibilityChanged(android.view.View changedView, int visibility) {
        Intrinsics.checkNotNullParameter(changedView, "changedView");
        super.onVisibilityChanged(changedView, visibility);
        if (changedView == this) {
            if (visibility != 0 || getWindowVisibility() != 0) {
                stopRendering();
            } else {
                startRendering();
            }
        }
    }

    private final void setupFilament() {
        Engine engine;
        this.engine = Engine.create();
        Engine engine2 = this.engine;
        UiHelper uiHelper = null;
        if (engine2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("engine");
            engine2 = null;
        }
        this.renderer = engine2.createRenderer();
        Engine engine3 = this.engine;
        if (engine3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("engine");
            engine3 = null;
        }
        this.scene = engine3.createScene();
        Engine engine4 = this.engine;
        if (engine4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("engine");
            engine4 = null;
        }
        this.view = engine4.createView();
        Engine engine5 = this.engine;
        if (engine5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("engine");
            engine5 = null;
        }
        this.camera = engine5.createCamera(EntityManager.get().create());
        View view = this.view;
        if (view == null) {
            Intrinsics.throwUninitializedPropertyAccessException("view");
            view = null;
        }
        Scene scene = this.scene;
        if (scene == null) {
            Intrinsics.throwUninitializedPropertyAccessException("scene");
            scene = null;
        }
        view.setScene(scene);
        View view2 = this.view;
        if (view2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("view");
            view2 = null;
        }
        Camera camera = this.camera;
        if (camera == null) {
            Intrinsics.throwUninitializedPropertyAccessException("camera");
            camera = null;
        }
        view2.setCamera(camera);
        Camera camera2 = this.camera;
        if (camera2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("camera");
            camera2 = null;
        }
        camera2.setExposure(1.0f);
        applyGrading(false);
        View view3 = this.view;
        if (view3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("view");
            view3 = null;
        }
        view3.setAntiAliasing(View.AntiAliasing.NONE);
        View view4 = this.view;
        if (view4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("view");
            view4 = null;
        }
        View.MultiSampleAntiAliasingOptions $this$setupFilament_u24lambda_u246 = new View.MultiSampleAntiAliasingOptions();
        $this$setupFilament_u24lambda_u246.enabled = true;
        $this$setupFilament_u24lambda_u246.sampleCount = 4;
        view4.setMultiSampleAntiAliasingOptions($this$setupFilament_u24lambda_u246);
        View view5 = this.view;
        if (view5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("view");
            view5 = null;
        }
        view5.setShadowType(View.ShadowType.PCF);
        View view6 = this.view;
        if (view6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("view");
            view6 = null;
        }
        View.BloomOptions $this$setupFilament_u24lambda_u247 = new View.BloomOptions();
        $this$setupFilament_u24lambda_u247.enabled = true;
        $this$setupFilament_u24lambda_u247.strength = 0.08f;
        $this$setupFilament_u24lambda_u247.threshold = true;
        view6.setBloomOptions($this$setupFilament_u24lambda_u247);
        View view7 = this.view;
        if (view7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("view");
            view7 = null;
        }
        View.DynamicResolutionOptions $this$setupFilament_u24lambda_u248 = new View.DynamicResolutionOptions();
        $this$setupFilament_u24lambda_u248.enabled = false;
        view7.setDynamicResolutionOptions($this$setupFilament_u24lambda_u248);
        this.sun = EntityManager.get().create();
        LightManager.Builder castShadows = new LightManager.Builder(LightManager.Type.DIRECTIONAL).color(1.0f, 0.98f, 0.95f).intensity(2.6f).direction(-0.35f, -1.0f, -0.45f).castShadows(true);
        LightManager.ShadowOptions $this$setupFilament_u24lambda_u249 = new LightManager.ShadowOptions();
        $this$setupFilament_u24lambda_u249.mapSize = 2048;
        $this$setupFilament_u24lambda_u249.shadowFar = 120.0f;
        $this$setupFilament_u24lambda_u249.shadowNearHint = 1.0f;
        $this$setupFilament_u24lambda_u249.shadowFarHint = 120.0f;
        LightManager.Builder shadowOptions = castShadows.shadowOptions($this$setupFilament_u24lambda_u249);
        Engine engine6 = this.engine;
        if (engine6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("engine");
            engine6 = null;
        }
        shadowOptions.build(engine6, this.sun);
        Scene scene2 = this.scene;
        if (scene2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("scene");
            scene2 = null;
        }
        scene2.addEntity(this.sun);
        try {
            KTX1Loader kTX1Loader = KTX1Loader.INSTANCE;
            Engine engine7 = this.engine;
            if (engine7 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("engine");
                engine = null;
            } else {
                engine = engine7;
            }
            IndirectLight it = KTX1Loader.createIndirectLight$default(kTX1Loader, engine, readAsset("ibl/day_ibl.ktx"), null, 4, null).getIndirectLight();
            if (it != null) {
                it.setIntensity(1.0f);
                Scene scene3 = this.scene;
                if (scene3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("scene");
                    scene3 = null;
                }
                scene3.setIndirectLight(it);
            } else {
                it = null;
            }
            this.ibl = it;
        } catch (Exception e) {
            Log.w("StarView/Scene", "no IBL: " + e);
        }
        Material.Builder payload = new Material.Builder().payload(readAsset("materials/ribbon.filamat"), this.readAssetSize);
        Engine engine8 = this.engine;
        if (engine8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("engine");
            engine8 = null;
        }
        this.ribbonMaterial = payload.build(engine8);
        Material.Builder payload2 = new Material.Builder().payload(readAsset("materials/ground.filamat"), this.readAssetSize);
        Engine engine9 = this.engine;
        if (engine9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("engine");
            engine9 = null;
        }
        this.groundMaterial = payload2.build(engine9);
        Material material = this.groundMaterial;
        if (material == null) {
            Intrinsics.throwUninitializedPropertyAccessException("groundMaterial");
            material = null;
        }
        this.groundMi = material.createInstance();
        Material.Builder payload3 = new Material.Builder().payload(readAsset("materials/shadow.filamat"), this.readAssetSize);
        Engine engine10 = this.engine;
        if (engine10 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("engine");
            engine10 = null;
        }
        this.shadowMaterial = payload3.build(engine10);
        Material material2 = this.shadowMaterial;
        if (material2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("shadowMaterial");
            material2 = null;
        }
        MaterialInstance it2 = material2.createInstance();
        Intrinsics.checkNotNullExpressionValue(it2, "createInstance(...)");
        it2.setParameter("color", 0.0f, 0.0f, 0.0f, 0.35f);
        this.shadowMi = it2;
        buildGround();
        buildLayout();
        buildRibbonMesh();
        Engine engine11 = this.engine;
        if (engine11 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("engine");
            engine11 = null;
        }
        Engine engine12 = this.engine;
        if (engine12 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("engine");
            engine12 = null;
        }
        this.assetLoader = new AssetLoader(engine11, new UbershaderProvider(engine12), EntityManager.get());
        Engine engine13 = this.engine;
        if (engine13 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("engine");
            engine13 = null;
        }
        this.resourceLoader = new ResourceLoader(engine13);
        Car it3 = loadCar("ego_sienna.glb");
        if (it3 != null) {
            it3.show(true);
        } else {
            it3 = null;
        }
        this.ego = it3;
        for (String k : this.genericKinds) {
            int i = this.poolPerKind;
            for (int i2 = 0; i2 < i; i2++) {
                Car it4 = loadCar(k);
                if (it4 != null) {
                    it4.grey();
                    this.genericPool.add(it4);
                }
            }
        }
        for (Pair<String, Integer> pair : this.objectKinds) {
            String k2 = pair.component1();
            int n = pair.component2().intValue();
            for (int i3 = 0; i3 < n; i3++) {
                Car it5 = loadCar(k2);
                if (it5 != null) {
                    it5.grey();
                    this.genericPool.add(it5);
                }
            }
        }
        this.uiHelper = new UiHelper(UiHelper.ContextErrorPolicy.DONT_CHECK);
        UiHelper uiHelper2 = this.uiHelper;
        if (uiHelper2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("uiHelper");
            uiHelper2 = null;
        }
        uiHelper2.setRenderCallback(new UiHelper.RendererCallback() { // from class: com.starpilot.starview.SceneView$setupFilament$10
            @Override // com.google.android.filament.android.UiHelper.RendererCallback
            public void onNativeWindowChanged(Surface surface) {
                SwapChain it6;
                DisplayHelper displayHelper;
                Renderer renderer;
                Intrinsics.checkNotNullParameter(surface, "surface");
                it6 = SceneView.this.swapChain;
                Renderer renderer2 = null;
                if (it6 != null) {
                    Engine engine14 = SceneView.this.engine;
                    if (engine14 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("engine");
                        engine14 = null;
                    }
                    engine14.destroySwapChain(it6);
                }
                SceneView sceneView = SceneView.this;
                Engine engine15 = SceneView.this.engine;
                if (engine15 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("engine");
                    engine15 = null;
                }
                sceneView.swapChain = engine15.createSwapChain(surface);
                displayHelper = SceneView.this.displayHelper;
                if (displayHelper == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("displayHelper");
                    displayHelper = null;
                }
                renderer = SceneView.this.renderer;
                if (renderer == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("renderer");
                } else {
                    renderer2 = renderer;
                }
                displayHelper.attach(renderer2, SceneView.this.getDisplay());
            }

            @Override // com.google.android.filament.android.UiHelper.RendererCallback
            public void onDetachedFromSurface() {
                DisplayHelper displayHelper;
                SwapChain it6;
                displayHelper = SceneView.this.displayHelper;
                if (displayHelper == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("displayHelper");
                    displayHelper = null;
                }
                displayHelper.detach();
                it6 = SceneView.this.swapChain;
                if (it6 != null) {
                    SceneView sceneView = SceneView.this;
                    Engine engine14 = sceneView.engine;
                    if (engine14 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("engine");
                        engine14 = null;
                    }
                    engine14.destroySwapChain(it6);
                    Engine engine15 = sceneView.engine;
                    if (engine15 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("engine");
                        engine15 = null;
                    }
                    engine15.flushAndWait();
                    sceneView.swapChain = null;
                }
            }

            @Override // com.google.android.filament.android.UiHelper.RendererCallback
            public void onResized(int width, int height) {
                View view8;
                SceneView.this.viewW = width;
                SceneView.this.viewH = height;
                view8 = SceneView.this.view;
                if (view8 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("view");
                    view8 = null;
                }
                view8.setViewport(new Viewport(0, 0, width, height));
                SceneView.this.setProjection();
            }
        });
        this.displayHelper = new DisplayHelper(getContext());
        UiHelper uiHelper3 = this.uiHelper;
        if (uiHelper3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("uiHelper");
        } else {
            uiHelper = uiHelper3;
        }
        uiHelper.attachTo(this);
        this.ready = true;
        applyPalette();
    }

    private final void applyGrading(boolean grey) {
        if (Intrinsics.areEqual(Boolean.valueOf(grey), this.gradingGrey)) {
            return;
        }
        this.gradingGrey = Boolean.valueOf(grey);
        ColorGrading old = this.grading;
        ColorGrading.Builder saturation = new ColorGrading.Builder().toneMapper(new ToneMapper.Linear()).saturation(grey ? 0.0f : 1.0f);
        Engine engine = this.engine;
        Engine engine2 = null;
        if (engine == null) {
            Intrinsics.throwUninitializedPropertyAccessException("engine");
            engine = null;
        }
        this.grading = saturation.build(engine);
        View view = this.view;
        if (view == null) {
            Intrinsics.throwUninitializedPropertyAccessException("view");
            view = null;
        }
        view.setColorGrading(this.grading);
        if (old != null) {
            Engine engine3 = this.engine;
            if (engine3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("engine");
            } else {
                engine2 = engine3;
            }
            engine2.destroyColorGrading(old);
        }
    }

    private final ByteBuffer readAsset(String name) {
        InputStream open = getContext().getAssets().open(name);
        try {
            InputStream s = open;
            Intrinsics.checkNotNull(s);
            byte[] bytes = ByteStreamsKt.readBytes(s);
            this.readAssetSize = bytes.length;
            ByteBuffer it = ByteBuffer.allocateDirect(bytes.length).order(ByteOrder.nativeOrder()).put(bytes);
            it.flip();
            Intrinsics.checkNotNullExpressionValue(it, "also(...)");
            CloseableKt.closeFinally(open, null);
            return it;
        } finally {
        }
    }

    private final Car loadCar(String name) {
        try {
            ByteBuffer buf = readAsset("models/" + name);
            AssetLoader assetLoader = this.assetLoader;
            if (assetLoader == null) {
                Intrinsics.throwUninitializedPropertyAccessException("assetLoader");
                assetLoader = null;
            }
            FilamentAsset asset = assetLoader.createAsset(buf);
            if (asset == null) {
                throw new IllegalStateException("createAsset");
            }
            ResourceLoader resourceLoader = this.resourceLoader;
            if (resourceLoader == null) {
                Intrinsics.throwUninitializedPropertyAccessException("resourceLoader");
                resourceLoader = null;
            }
            resourceLoader.loadResources(asset);
            asset.releaseSourceData();
            Box bb = asset.getBoundingBox();
            Intrinsics.checkNotNullExpressionValue(bb, "getBoundingBox(...)");
            double length = bb.getHalfExtent()[2] * 2;
            Engine engine = this.engine;
            if (engine == null) {
                Intrinsics.throwUninitializedPropertyAccessException("engine");
                engine = null;
            }
            RenderableManager rm = engine.getRenderableManager();
            for (int e : asset.getEntities()) {
                int i = rm.getInstance(e);
                if (i != 0) {
                    rm.setCastShadows(i, true);
                    rm.setReceiveShadows(i, false);
                }
            }
            return new Car(this, asset, length, name);
        } catch (Exception e2) {
            Log.e("StarView/Scene", "load " + name + " failed: " + e2);
            return null;
        }
    }

    private final void buildGround() {
        MaterialInstance materialInstance = this.groundMi;
        MaterialInstance materialInstance2 = null;
        if (materialInstance == null) {
            Intrinsics.throwUninitializedPropertyAccessException("groundMi");
            materialInstance = null;
        }
        this.groundEnt = quad(0.0f, materialInstance, 4, false);
        MaterialInstance materialInstance3 = this.shadowMi;
        if (materialInstance3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("shadowMi");
        } else {
            materialInstance2 = materialInstance3;
        }
        this.shadowEnt = quad(0.01f, materialInstance2, 5, true);
    }

    private final int quad(float y, MaterialInstance mi, int priority, boolean receive) {
        Scene scene;
        Float valueOf = Float.valueOf(400.0f);
        VertexBuffer.Builder attribute = new VertexBuffer.Builder().bufferCount(1).vertexCount(4).attribute(VertexBuffer.VertexAttribute.POSITION, 0, VertexBuffer.AttributeType.FLOAT3, 0, 12);
        Engine engine = this.engine;
        if (engine == null) {
            Intrinsics.throwUninitializedPropertyAccessException("engine");
            engine = null;
        }
        VertexBuffer vb = attribute.build(engine);
        Intrinsics.checkNotNullExpressionValue(vb, "build(...)");
        ByteBuffer v = ByteBuffer.allocateDirect(48).order(ByteOrder.nativeOrder());
        for (Pair pair : CollectionsKt.listOf((Object[]) new Pair[]{TuplesKt.to(Float.valueOf(-400.0f), valueOf), TuplesKt.to(valueOf, valueOf), TuplesKt.to(valueOf, Float.valueOf(-400.0f)), TuplesKt.to(Float.valueOf(-400.0f), Float.valueOf(-400.0f))})) {
            float x = ((Number) pair.component1()).floatValue();
            float z = ((Number) pair.component2()).floatValue();
            v.putFloat(x);
            v.putFloat(y);
            v.putFloat(z);
        }
        v.flip();
        Engine engine2 = this.engine;
        if (engine2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("engine");
            engine2 = null;
        }
        vb.setBufferAt(engine2, 0, v);
        IndexBuffer.Builder bufferType = new IndexBuffer.Builder().indexCount(6).bufferType(IndexBuffer.Builder.IndexType.USHORT);
        Engine engine3 = this.engine;
        if (engine3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("engine");
            engine3 = null;
        }
        IndexBuffer ib = bufferType.build(engine3);
        Intrinsics.checkNotNullExpressionValue(ib, "build(...)");
        ByteBuffer i = ByteBuffer.allocateDirect(12).order(ByteOrder.nativeOrder());
        short[] sArr = {0, 2, 1, 0, 3, 2};
        for (int i2 = 0; i2 < 6; i2++) {
            short k = sArr[i2];
            i.putShort(k);
        }
        i.flip();
        Engine engine4 = this.engine;
        if (engine4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("engine");
            engine4 = null;
        }
        ib.setBuffer(engine4, i);
        this.groundBuffers.add(TuplesKt.to(vb, ib));
        int e = EntityManager.get().create();
        RenderableManager.Builder priority2 = new RenderableManager.Builder(1).boundingBox(new Box(0.0f, y, 0.0f, 400.0f, 0.1f, 400.0f)).geometry(0, RenderableManager.PrimitiveType.TRIANGLES, vb, ib, 0, 6).material(0, mi).castShadows(false).receiveShadows(receive).culling(false).priority(priority);
        Engine engine5 = this.engine;
        if (engine5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("engine");
            engine5 = null;
        }
        priority2.build(engine5, e);
        Scene scene2 = this.scene;
        if (scene2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("scene");
            scene = null;
        } else {
            scene = scene2;
        }
        scene.addEntity(e);
        return e;
    }

    public final boolean getShowTraffic() {
        return this.showTraffic;
    }

    public final void setShowTraffic(boolean z) {
        this.showTraffic = z;
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

    private final boolean centreLineOnLeft() {
        try {
            LaneBrain tr = LaneBrain.Live.INSTANCE.getBrain();
            LaneBrain.Road road = tr != null ? tr.getRoad() : null;
            if (road == null || road.getOneway() || road.getHighway()) {
                return false;
            }
            return tr.lanesLeft() == 0;
        } catch (Exception e) {
            return false;
        }
    }

    public final LaneMemory getLaneMemory() {
        return this.laneMemory;
    }

    public final void setLaneMemory(LaneMemory laneMemory) {
        this.laneMemory = laneMemory;
    }

    /* compiled from: SceneView.kt */
    public static final class LaneHint {
        private final long atNs;
        private final double conf;
        private final double edge;
        private final int lane;
        private final int total;
        private final long wayId;

        public LaneHint(long wayId, int lane, int total, double conf, double edge, long atNs) {
            this.wayId = wayId;
            this.lane = lane;
            this.total = total;
            this.conf = conf;
            this.edge = edge;
            this.atNs = atNs;
        }

        public final long getAtNs() {
            return this.atNs;
        }

        public final double getConf() {
            return this.conf;
        }

        public final double getEdge() {
            return this.edge;
        }

        public final int getLane() {
            return this.lane;
        }

        public final int getTotal() {
            return this.total;
        }

        public final long getWayId() {
            return this.wayId;
        }
    }

    public final LaneHint getLaneHint() {
        return this.laneHint;
    }

    public final void setLaneHint(LaneHint laneHint) {
        this.laneHint = laneHint;
    }

    private final LaneHint hintFor(OsmRoads.Way w) {
        LaneHint it = this.hintNow;
        if (it != null) {
            if (it.getWayId() == w.getId() || (it.getWayId() == this.hintChainOf && this.hintChain.contains(Long.valueOf(w.getId())))) {
                return it;
            }
        }
        return null;
    }

    private final int lanesOf(OsmRoads.Way w) {
        LaneHint it = this.hintNow;
        if (it != null) {
            if (!((it.getWayId() == w.getId() || (it.getWayId() == this.hintChainOf && this.hintSame.contains(Long.valueOf(w.getId())))) && it.getTotal() > 0)) {
                it = null;
            }
            if (it != null) {
                return it.getTotal();
            }
        }
        return w.getLaneCount();
    }

    public final CarMemory getCarMemory() {
        return this.carMemory;
    }

    public final void setCarMemory(CarMemory carMemory) {
        this.carMemory = carMemory;
    }

    /* compiled from: SceneView.kt */
    private static final class Snap {
        private final double dist;
        private final byte[] g;
        private final double px;
        private final double py;
        private final long tNs;
        private final double yaw;

        public Snap(byte[] g, double px, double py, double yaw, long tNs, double dist) {
            Intrinsics.checkNotNullParameter(g, "g");
            this.g = g;
            this.px = px;
            this.py = py;
            this.yaw = yaw;
            this.tNs = tNs;
            this.dist = dist;
        }

        public final double getDist() {
            return this.dist;
        }

        public final byte[] getG() {
            return this.g;
        }

        public final double getPx() {
            return this.px;
        }

        public final double getPy() {
            return this.py;
        }

        public final long getTNs() {
            return this.tNs;
        }

        public final double getYaw() {
            return this.yaw;
        }
    }

    private final void buildLayout() {
        VertexBuffer vertexBuffer;
        IndexBuffer indexBuffer;
        Scene scene;
        Material.Builder payload = new Material.Builder().payload(readAsset("materials/layout.filamat"), this.readAssetSize);
        Engine engine = this.engine;
        if (engine == null) {
            Intrinsics.throwUninitializedPropertyAccessException("engine");
            engine = null;
        }
        this.layoutMaterial = payload.build(engine);
        Material material = this.layoutMaterial;
        if (material == null) {
            Intrinsics.throwUninitializedPropertyAccessException("layoutMaterial");
            material = null;
        }
        this.layoutMi = material.createInstance();
        Texture.Builder format = new Texture.Builder().width(80).height(this.MR).levels(1).sampler(Texture.Sampler.SAMPLER_2D).format(Texture.InternalFormat.SRGB8_A8);
        Engine engine2 = this.engine;
        if (engine2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("engine");
            engine2 = null;
        }
        this.layoutTex = format.build(engine2);
        ByteBuffer clear = ByteBuffer.allocateDirect(this.MR * 80 * 4).order(ByteOrder.nativeOrder());
        Texture texture = this.layoutTex;
        if (texture == null) {
            Intrinsics.throwUninitializedPropertyAccessException("layoutTex");
            texture = null;
        }
        Engine engine3 = this.engine;
        if (engine3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("engine");
            engine3 = null;
        }
        texture.setImage(engine3, 0, new Texture.PixelBufferDescriptor(clear, Texture.Format.RGBA, Texture.Type.UBYTE));
        MaterialInstance materialInstance = this.layoutMi;
        if (materialInstance == null) {
            Intrinsics.throwUninitializedPropertyAccessException("layoutMi");
            materialInstance = null;
        }
        Texture texture2 = this.layoutTex;
        if (texture2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("layoutTex");
            texture2 = null;
        }
        materialInstance.setParameter("tex", texture2, new TextureSampler(TextureSampler.MinFilter.LINEAR, TextureSampler.MagFilter.LINEAR, TextureSampler.WrapMode.CLAMP_TO_EDGE));
        MaterialInstance materialInstance2 = this.layoutMi;
        if (materialInstance2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("layoutMi");
            materialInstance2 = null;
        }
        materialInstance2.setParameter("opacity", 0.0f);
        VertexBuffer.Builder attribute = new VertexBuffer.Builder().bufferCount(1).vertexCount(4).attribute(VertexBuffer.VertexAttribute.POSITION, 0, VertexBuffer.AttributeType.FLOAT3, 0, 20).attribute(VertexBuffer.VertexAttribute.UV0, 0, VertexBuffer.AttributeType.FLOAT2, 12, 20);
        Engine engine4 = this.engine;
        if (engine4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("engine");
            engine4 = null;
        }
        this.layoutVb = attribute.build(engine4);
        ByteBuffer v = ByteBuffer.allocateDirect(80).order(ByteOrder.nativeOrder());
        for (Pair pair : CollectionsKt.listOf((Object[]) new Pair[]{TuplesKt.to(Float.valueOf(this.XMIN), Float.valueOf(-20.0f)), TuplesKt.to(Float.valueOf(this.XMIN), Float.valueOf(20.0f)), TuplesKt.to(Float.valueOf(40.0f), Float.valueOf(20.0f)), TuplesKt.to(Float.valueOf(40.0f), Float.valueOf(-20.0f))})) {
            float x = ((Number) pair.component1()).floatValue();
            float y = ((Number) pair.component2()).floatValue();
            v.putFloat(y);
            v.putFloat(0.005f);
            v.putFloat(-x);
            v.putFloat((y + 20.0f) / (2 * 20.0f));
            v.putFloat((x - this.XMIN) / (40.0f - this.XMIN));
        }
        v.flip();
        VertexBuffer vertexBuffer2 = this.layoutVb;
        if (vertexBuffer2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("layoutVb");
            vertexBuffer2 = null;
        }
        Engine engine5 = this.engine;
        if (engine5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("engine");
            engine5 = null;
        }
        vertexBuffer2.setBufferAt(engine5, 0, v);
        IndexBuffer.Builder bufferType = new IndexBuffer.Builder().indexCount(6).bufferType(IndexBuffer.Builder.IndexType.USHORT);
        Engine engine6 = this.engine;
        if (engine6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("engine");
            engine6 = null;
        }
        this.layoutIb = bufferType.build(engine6);
        ByteBuffer i = ByteBuffer.allocateDirect(12).order(ByteOrder.nativeOrder());
        short[] sArr = {0, 1, 2, 0, 2, 3};
        for (int i2 = 0; i2 < 6; i2++) {
            short k = sArr[i2];
            i.putShort(k);
        }
        i.flip();
        IndexBuffer indexBuffer2 = this.layoutIb;
        if (indexBuffer2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("layoutIb");
            indexBuffer2 = null;
        }
        Engine engine7 = this.engine;
        if (engine7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("engine");
            engine7 = null;
        }
        indexBuffer2.setBuffer(engine7, i);
        this.layoutEnt = EntityManager.get().create();
        RenderableManager.Builder boundingBox = new RenderableManager.Builder(1).boundingBox(new Box(0.0f, 0.0f, (-40.0f) / 2, 60.0f, 0.1f, 60.0f));
        RenderableManager.PrimitiveType primitiveType = RenderableManager.PrimitiveType.TRIANGLES;
        VertexBuffer vertexBuffer3 = this.layoutVb;
        if (vertexBuffer3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("layoutVb");
            vertexBuffer = null;
        } else {
            vertexBuffer = vertexBuffer3;
        }
        IndexBuffer indexBuffer3 = this.layoutIb;
        if (indexBuffer3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("layoutIb");
            indexBuffer = null;
        } else {
            indexBuffer = indexBuffer3;
        }
        RenderableManager.Builder geometry = boundingBox.geometry(0, primitiveType, vertexBuffer, indexBuffer, 0, 6);
        MaterialInstance materialInstance3 = this.layoutMi;
        if (materialInstance3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("layoutMi");
            materialInstance3 = null;
        }
        RenderableManager.Builder priority = geometry.material(0, materialInstance3).castShadows(false).receiveShadows(false).culling(false).priority(5);
        Engine engine8 = this.engine;
        if (engine8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("engine");
            engine8 = null;
        }
        priority.build(engine8, this.layoutEnt);
        Scene scene2 = this.scene;
        if (scene2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("scene");
            scene = null;
        } else {
            scene = scene2;
        }
        scene.addEntity(this.layoutEnt);
    }

    private final void updateLayout(long nowNs, Telemetry t) {
        String str;
        String str2;
        MaterialInstance materialInstance;
        boolean dark;
        String str3;
        String str4;
        Engine engine;
        long tNs;
        double ageS;
        double[] it = Odometry.INSTANCE.at(nowNs);
        this.odoX = it[0];
        this.odoY = it[1];
        this.odoYaw = it[2];
        this.odoDist = it[3];
        this.odoNs = nowNs;
        this.yawSrc = Odometry.INSTANCE.getYawSrc();
        while (true) {
            try {
                OsmRoads.Fix f = OsmRoads.INSTANCE.getFixes().poll();
                if (f == null) {
                    break;
                }
                Long sent = f.getMonoNs() != 0 ? Odometry.INSTANCE.monoToLocal(f.getMonoNs()) : null;
                if (sent != null) {
                    tNs = sent.longValue();
                    ageS = this.gpsLag;
                } else {
                    tNs = f.getTNs();
                    double ageS2 = f.getAgeS();
                    ageS = (!Double.isInfinite(ageS2) && !Double.isNaN(ageS2) ? f.getAgeS() : 0.0d) + this.gpsLag;
                }
                long tFix = tNs - ((long) (ageS * 1.0E9d));
                double[] a = odoAt(tFix);
                double[] b = odoAt(tFix - 100000000);
                double[] c = odoAt(100000000 + tFix);
                this.mapAlign.add(a[1], a[2], a[3], f, nowNs, (c[1] - b[1]) / 0.2d, (c[2] - b[2]) / 0.2d);
                double lr = this.mapAlign.getLagResid();
                if (((Double.isInfinite(lr) || Double.isNaN(lr)) ? false : true) && t.getVEgo() > 6.0f) {
                    this.gpsLag = RangesKt.coerceIn(this.gpsLag + (0.02d * lr), 0.0d, 0.8d);
                }
            } catch (Exception e) {
                Log.e("StarView/Scene", "map align", e);
                this.mapAlign.reset();
                this.mapStatus = StringsKt.take("map error: " + e, 90);
            }
        }
        if (this.mapAlign.getOk()) {
            if (this.mapAlign.getGuess().length() == 0) {
                GpsBridge.INSTANCE.setFit(this.mapAlign.getTh(), this.mapAlign.getTx(), this.mapAlign.getTy(), OsmRoads.INSTANCE.getLat0(), OsmRoads.INSTANCE.getLon0(), this.mapAlign.getRejectSince() != 0);
            }
        }
        collectParked(nowNs, t);
        LayoutSeg lay = this.layout;
        if (lay == null) {
            str = "opacity";
            str2 = "layoutMi";
        } else if (!lay.getEnabled()) {
            str = "opacity";
            str2 = "layoutMi";
        } else {
            LayoutSeg.Grid g = lay.getGrid();
            boolean dark2 = this.night || this.alwaysDark;
            if (g == null || g == this.layoutGrid) {
                dark = dark2;
                str3 = "opacity";
                str4 = "layoutMi";
                if ((dark != this.layoutDark || this.layoutHideRoad != this.layoutPaintedHide) && !this.snaps.isEmpty()) {
                    paint(dark);
                }
            } else {
                this.layoutGrid = g;
                Snap last = this.snaps.lastOrNull();
                boolean still = last != null && this.odoDist - last.getDist() < 1.0d;
                if (still) {
                    Intrinsics.checkNotNull(last);
                    if (nowNs - last.getTNs() <= 2000000000) {
                        dark = dark2;
                        str3 = "opacity";
                        str4 = "layoutMi";
                        combine(nowNs);
                        smooth();
                        paint(dark);
                    }
                }
                dark = dark2;
                str4 = "layoutMi";
                str3 = "opacity";
                this.snaps.addLast(new Snap(g.getG(), this.odoX, this.odoY, this.odoYaw, nowNs, this.odoDist));
                while (true) {
                    if (this.snaps.size() <= 48 && (this.snaps.isEmpty() || ((nowNs - this.snaps.first().getTNs() <= 10000000000L || this.odoDist - this.snaps.first().getDist() <= 30.0d) && nowNs - this.snaps.first().getTNs() <= 120000000000L))) {
                        break;
                    } else {
                        this.snaps.removeFirst();
                    }
                }
                combine(nowNs);
                smooth();
                paint(dark);
            }
            if (this.snaps.isEmpty()) {
                MaterialInstance materialInstance2 = this.layoutMi;
                if (materialInstance2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException(str4);
                    materialInstance2 = null;
                }
                materialInstance2.setParameter(str3, 0.0f);
                return;
            }
            String str5 = str3;
            float tNs2 = (nowNs - this.snaps.last().getTNs()) / 1.0E9f;
            MaterialInstance materialInstance3 = this.layoutMi;
            if (materialInstance3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException(str4);
                materialInstance3 = null;
            }
            materialInstance3.setParameter(str5, 0.0f);
            double dxw = this.texX - this.odoX;
            double dyw = this.texY - this.odoY;
            double cn = Math.cos(this.odoYaw);
            double sn = Math.sin(this.odoYaw);
            double lx = (cn * dxw) + (sn * dyw);
            double ly = ((-sn) * dxw) + (cn * dyw);
            double dth = this.texYaw - this.odoYaw;
            float c2 = (float) Math.cos(dth);
            float s = (float) Math.sin(dth);
            float[] it2 = {c2, 0.0f, -s, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, s, 0.0f, c2, 0.0f, (float) (-ly), 0.0f, (float) (-lx), 1.0f};
            Engine engine2 = this.engine;
            if (engine2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("engine");
                engine = null;
            } else {
                engine = engine2;
            }
            TransformManager tm = engine.getTransformManager();
            Intrinsics.checkNotNullExpressionValue(tm, "getTransformManager(...)");
            int it3 = tm.getInstance(this.layoutEnt);
            if (it3 == 0) {
                tm.create(this.layoutEnt);
                it3 = tm.getInstance(this.layoutEnt);
            }
            tm.setTransform(it3, it2);
            return;
        }
        if (this.snaps.isEmpty()) {
            materialInstance = null;
        } else {
            this.snaps.clear();
            materialInstance = null;
            this.layoutGrid = null;
        }
        MaterialInstance materialInstance4 = this.layoutMi;
        if (materialInstance4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException(str2);
            materialInstance4 = materialInstance;
        }
        materialInstance4.setParameter(str, 0.0f);
    }

    private final void combine(long nowNs) {
        float oy;
        float c;
        float cell;
        float ox;
        double dx;
        float age;
        int cs;
        long j = nowNs;
        this.texX = this.odoX;
        this.texY = this.odoY;
        this.texYaw = this.odoYaw;
        this.texNs = j;
        float age2 = 0.5f;
        float half = (80 * 0.5f) / 2;
        Arrays.fill(this.memGrp, (byte) -1);
        Arrays.fill(this.memScore, Float.MAX_VALUE);
        Arrays.fill(this.memAge, 0.0f);
        Iterator it = this.snaps.iterator();
        while (it.hasNext()) {
            Snap sn = (Snap) it.next();
            float age3 = (j - sn.getTNs()) / 1.0E9f;
            double dx2 = this.texX - sn.getPx();
            Iterator it2 = it;
            double dy = this.texY - sn.getPy();
            double c0 = Math.cos(sn.getYaw());
            float age4 = age3;
            double s0 = Math.sin(sn.getYaw());
            float ox2 = (float) ((c0 * dx2) + (s0 * dy));
            float oy2 = (float) (((-s0) * dx2) + (c0 * dy));
            double dth = this.texYaw - sn.getYaw();
            float c2 = (float) Math.cos(dth);
            float s = (float) Math.sin(dth);
            byte[] g = sn.getG();
            int r = 0;
            int i = this.MR;
            while (r < i) {
                double dth2 = dth;
                float x = this.XMIN + ((r + 0.5f) * age2);
                int col = 0;
                while (col < 80) {
                    Snap sn2 = sn;
                    float yl = half - ((col + 0.5f) * age2);
                    float xs = (ox2 + (c2 * x)) - (s * yl);
                    if (xs < 0.0f) {
                        oy = oy2;
                        c = c2;
                        cell = age2;
                        ox = ox2;
                        dx = dx2;
                        age = age4;
                    } else {
                        ox = ox2;
                        float ox3 = xs / age2;
                        int rs = (int) ox3;
                        dx = dx2;
                        if (rs >= 80) {
                            oy = oy2;
                            c = c2;
                            cell = age2;
                            age = age4;
                        } else {
                            float it3 = (half - (((s * x) + oy2) + (c2 * yl))) / age2;
                            if (it3 < 0.0f) {
                                cell = age2;
                                cs = -1;
                            } else {
                                cell = age2;
                                cs = (int) it3;
                            }
                            if (cs >= 0) {
                                if (cs >= 80) {
                                    oy = oy2;
                                    c = c2;
                                    age = age4;
                                } else {
                                    int grp = g[(rs * 80) + cs] & 255;
                                    if (grp == 255) {
                                        oy = oy2;
                                        c = c2;
                                        age = age4;
                                    } else {
                                        float ys = (s * x) + oy2 + (c2 * yl);
                                        int k = (r * 80) + col;
                                        oy = oy2;
                                        float oy3 = (xs * xs) + (ys * ys);
                                        c = c2;
                                        age = age4;
                                        float score = ((float) Math.sqrt(oy3)) + (Math.min(age, 10.0f) * 3.0f);
                                        if (score < this.memScore[k]) {
                                            this.memScore[k] = score;
                                            this.memGrp[k] = (byte) grp;
                                            this.memAge[k] = age;
                                        }
                                    }
                                }
                            } else {
                                oy = oy2;
                                c = c2;
                                age = age4;
                            }
                        }
                    }
                    col++;
                    age4 = age;
                    sn = sn2;
                    ox2 = ox;
                    dx2 = dx;
                    age2 = cell;
                    c2 = c;
                    oy2 = oy;
                }
                float cell2 = age2;
                r++;
                dth = dth2;
                age2 = cell2;
            }
            float cell3 = age2;
            j = nowNs;
            it = it2;
            age2 = cell3;
        }
        for (int col2 = 0; col2 < 80; col2++) {
            int last = -1;
            int i2 = this.MR;
            for (int r2 = 0; r2 < i2; r2++) {
                int k2 = (r2 * 80) + col2;
                if ((this.memGrp[k2] & 255) != 255) {
                    if (last >= 0) {
                        int i3 = r2 - last;
                        boolean z = false;
                        if (2 <= i3 && i3 < 14) {
                            z = true;
                        }
                        if (z && this.memGrp[k2] == this.memGrp[(last * 80) + col2]) {
                            float a = Math.max(this.memAge[k2], this.memAge[(last * 80) + col2]);
                            for (int q = last + 1; q < r2; q++) {
                                this.memGrp[(q * 80) + col2] = this.memGrp[k2];
                                this.memAge[(q * 80) + col2] = a;
                            }
                        }
                    }
                    last = r2;
                }
            }
        }
    }

    private final void smooth() {
        byte b;
        int i = 5;
        int[] cnt = new int[5];
        int r = 0;
        int i2 = this.MR;
        while (r < i2) {
            int c = 0;
            while (c < 80) {
                Arrays.fill(cnt, 0);
                int dr = -1;
                while (true) {
                    if (dr >= 2) {
                        break;
                    }
                    int dc = -1;
                    for (int i3 = 2; dc < i3; i3 = 2) {
                        if (dr != 0 || dc != 0) {
                            int rr = r + dr;
                            int cc = c + dc;
                            if (rr >= 0 && rr < this.MR && cc >= 0 && cc < 80) {
                                int gv = this.memGrp[(rr * 80) + cc] & 255;
                                int i4 = gv > 3 ? 4 : gv;
                                cnt[i4] = cnt[i4] + 1;
                            }
                        }
                        dc++;
                    }
                    dr++;
                }
                int dr2 = r * 80;
                int k = dr2 + c;
                int me = this.memGrp[k] & 255;
                int best = -1;
                int q = 0;
                while (q < i) {
                    if (cnt[q] >= i && (best < 0 || cnt[q] > cnt[best])) {
                        best = q;
                    }
                    q++;
                    i = 5;
                }
                byte[] bArr = this.smoothTmp;
                if (best >= 0 && best < 4) {
                    b = (byte) best;
                } else {
                    b = (best == 4 && me <= 3 && cnt[4] == 8) ? (byte) -1 : this.memGrp[k];
                }
                bArr[k] = b;
                c++;
                i = 5;
            }
            r++;
            i = 5;
        }
        System.arraycopy(this.smoothTmp, 0, this.memGrp, 0, this.memGrp.length);
    }

    public final boolean getLayoutHideRoad() {
        return this.layoutHideRoad;
    }

    public final void setLayoutHideRoad(boolean z) {
        this.layoutHideRoad = z;
    }

    private final void paint(boolean dark) {
        int i;
        this.layoutDark = dark;
        this.layoutPaintedHide = this.layoutHideRoad;
        int i2 = 3;
        int i3 = 1;
        int[][] col = dark ? new int[][]{new int[]{44, 49, 59, 235}, new int[]{72, 79, 92, 240}, new int[]{31, 68, 44, 235}, new int[]{7, 8, 11, 245}} : new int[][]{new int[]{179, 185, 195, 240}, new int[]{247, 248, 250, 245}, new int[]{163, 209, 146, 235}, new int[]{126, 133, 145, 245}};
        int n = this.MR * 80;
        this.texBuf.clear();
        int k = 0;
        while (k < n) {
            int grp = this.memGrp[k] & 255;
            if (grp <= i2) {
                if (grp == 0 && this.layoutHideRoad) {
                    i = i2;
                } else {
                    int r = k / 80;
                    int cc = k % 80;
                    float border = RangesKt.coerceIn(Math.min(Math.min(r, (this.MR - i3) - r), Math.min(cc, (80 - 1) - cc)) / 12.0f, 0.0f, 1.0f);
                    int[] c = col[grp];
                    float fade = RangesKt.coerceIn((10.0f - this.memAge[k]) / 4.0f, 0.0f, 1.0f) * border;
                    this.texBuf.put((byte) c[0]);
                    this.texBuf.put((byte) c[i3]);
                    this.texBuf.put((byte) c[2]);
                    i = 3;
                    this.texBuf.put((byte) (c[3] * fade));
                    k++;
                    i2 = i;
                    i3 = 1;
                }
            } else {
                i = i2;
            }
            this.texBuf.putInt(0);
            k++;
            i2 = i;
            i3 = 1;
        }
        this.texBuf.flip();
        Texture texture = this.layoutTex;
        Engine engine = null;
        if (texture == null) {
            Intrinsics.throwUninitializedPropertyAccessException("layoutTex");
            texture = null;
        }
        Engine engine2 = this.engine;
        if (engine2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("engine");
        } else {
            engine = engine2;
        }
        texture.setImage(engine, 0, new Texture.PixelBufferDescriptor(this.texBuf, Texture.Format.RGBA, Texture.Type.UBYTE));
    }

    private final void buildRibbonMesh() {
        VertexBuffer vertexBuffer;
        IndexBuffer indexBuffer;
        VertexBuffer.Builder normalized = new VertexBuffer.Builder().bufferCount(1).vertexCount(this.maxVerts).attribute(VertexBuffer.VertexAttribute.POSITION, 0, VertexBuffer.AttributeType.FLOAT3, 0, 16).attribute(VertexBuffer.VertexAttribute.COLOR, 0, VertexBuffer.AttributeType.UBYTE4, 12, 16).normalized(VertexBuffer.VertexAttribute.COLOR);
        Engine engine = this.engine;
        Scene scene = null;
        if (engine == null) {
            Intrinsics.throwUninitializedPropertyAccessException("engine");
            engine = null;
        }
        this.ribbonVb = normalized.build(engine);
        IndexBuffer.Builder bufferType = new IndexBuffer.Builder().indexCount(this.maxIdx).bufferType(IndexBuffer.Builder.IndexType.UINT);
        Engine engine2 = this.engine;
        if (engine2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("engine");
            engine2 = null;
        }
        this.ribbonIb = bufferType.build(engine2);
        getVbuf().clear();
        for (int k = 0; k < 3; k++) {
            getVbuf().putFloat(0.0f);
            getVbuf().putFloat(0.0f);
            getVbuf().putFloat(0.0f);
            getVbuf().putInt(0);
        }
        getVbuf().flip();
        VertexBuffer vertexBuffer2 = this.ribbonVb;
        if (vertexBuffer2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ribbonVb");
            vertexBuffer2 = null;
        }
        Engine engine3 = this.engine;
        if (engine3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("engine");
            engine3 = null;
        }
        vertexBuffer2.setBufferAt(engine3, 0, getVbuf());
        getIbuf().clear();
        getIbuf().putInt(0);
        getIbuf().putInt(1);
        getIbuf().putInt(2);
        getIbuf().flip();
        IndexBuffer indexBuffer2 = this.ribbonIb;
        if (indexBuffer2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ribbonIb");
            indexBuffer2 = null;
        }
        Engine engine4 = this.engine;
        if (engine4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("engine");
            engine4 = null;
        }
        indexBuffer2.setBuffer(engine4, getIbuf());
        this.ribbonEntity = EntityManager.get().create();
        RenderableManager.Builder boundingBox = new RenderableManager.Builder(1).boundingBox(new Box(0.0f, 0.0f, -100.0f, 60.0f, 12.0f, 200.0f));
        RenderableManager.PrimitiveType primitiveType = RenderableManager.PrimitiveType.TRIANGLES;
        VertexBuffer vertexBuffer3 = this.ribbonVb;
        if (vertexBuffer3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ribbonVb");
            vertexBuffer = null;
        } else {
            vertexBuffer = vertexBuffer3;
        }
        IndexBuffer indexBuffer3 = this.ribbonIb;
        if (indexBuffer3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ribbonIb");
            indexBuffer = null;
        } else {
            indexBuffer = indexBuffer3;
        }
        RenderableManager.Builder geometry = boundingBox.geometry(0, primitiveType, vertexBuffer, indexBuffer, 0, 3);
        Material material = this.ribbonMaterial;
        if (material == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ribbonMaterial");
            material = null;
        }
        RenderableManager.Builder priority = geometry.material(0, material.getDefaultInstance()).castShadows(false).receiveShadows(false).culling(false).priority(6);
        Engine engine5 = this.engine;
        if (engine5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("engine");
            engine5 = null;
        }
        priority.build(engine5, this.ribbonEntity);
        Scene scene2 = this.scene;
        if (scene2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("scene");
        } else {
            scene = scene2;
        }
        scene.addEntity(this.ribbonEntity);
    }

    private final void applyPalette() {
        int i;
        int i2;
        int i3;
        MaterialInstance materialInstance;
        if (this.ready) {
            Palette p = getPal();
            Triple<Float, Float, Float> linear = Companion.linear(p.getBg());
            float r = linear.component1().floatValue();
            float g = linear.component2().floatValue();
            float b = linear.component3().floatValue();
            Skybox old = this.skybox;
            Skybox.Builder color = new Skybox.Builder().color(r, g, b, 1.0f);
            Engine engine = this.engine;
            if (engine == null) {
                Intrinsics.throwUninitializedPropertyAccessException("engine");
                engine = null;
            }
            this.skybox = color.build(engine);
            Scene scene = this.scene;
            if (scene == null) {
                Intrinsics.throwUninitializedPropertyAccessException("scene");
                scene = null;
            }
            scene.setSkybox(this.skybox);
            if (old != null) {
                Engine engine2 = this.engine;
                if (engine2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("engine");
                    engine2 = null;
                }
                engine2.destroySkybox(old);
            }
            Renderer renderer = this.renderer;
            if (renderer == null) {
                Intrinsics.throwUninitializedPropertyAccessException("renderer");
                renderer = null;
            }
            Renderer.ClearOptions $this$applyPalette_u24lambda_u2426 = new Renderer.ClearOptions();
            $this$applyPalette_u24lambda_u2426.clearColor = new double[]{r, g, b, 1.0d};
            $this$applyPalette_u24lambda_u2426.clear = true;
            renderer.setClearOptions($this$applyPalette_u24lambda_u2426);
            Companion companion = Companion;
            if (this.night || this.alwaysDark) {
                i = 29;
                i2 = 36;
                i3 = 26;
            } else {
                i = 230;
                i2 = 234;
                i3 = 228;
            }
            Triple<Float, Float, Float> linear2 = companion.linear(Color.rgb(i3, i, i2));
            float gr = linear2.component1().floatValue();
            float gg = linear2.component2().floatValue();
            float gb = linear2.component3().floatValue();
            MaterialInstance materialInstance2 = this.groundMi;
            if (materialInstance2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("groundMi");
                materialInstance = null;
            } else {
                materialInstance = materialInstance2;
            }
            materialInstance.setParameter("color", gr, gg, gb, 1.0f);
            Engine engine3 = this.engine;
            if (engine3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("engine");
                engine3 = null;
            }
            LightManager lm = engine3.getLightManager();
            Intrinsics.checkNotNullExpressionValue(lm, "getLightManager(...)");
            int li = lm.getInstance(this.sun);
            lm.setIntensity(li, p.getSunIntensity());
            lm.setColor(li, p.getSunColor()[0], p.getSunColor()[1], p.getSunColor()[2]);
            IndirectLight indirectLight = this.ibl;
            if (indirectLight != null) {
                indirectLight.setIntensity(p.getIblIntensity());
            }
            this.paletteDirty = false;
            this.lastRibbonFrame = -2L;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setProjection() {
        if (this.viewW == 0 || this.viewH == 0) {
            return;
        }
        double aspect = this.viewW / this.viewH;
        double open = aspect < 1.6d ? Math.pow(1.6d / aspect, 0.6d) : 1.0d;
        double h = Math.tan(Math.toRadians(this.fovDeg / 2)) * 0.5d * open;
        double w = (this.viewW * h) / this.viewH;
        Camera camera = this.camera;
        if (camera == null) {
            Intrinsics.throwUninitializedPropertyAccessException("camera");
            camera = null;
        }
        double d = 1;
        camera.setProjection(Camera.Projection.PERSPECTIVE, -w, w, (this.horizonShift + d) * (-h), h * (d - this.horizonShift), 0.5d, 400.0d);
    }

    private final void startRendering() {
        if (this.running || this.engine == null) {
            return;
        }
        this.running = true;
        Choreographer.getInstance().postFrameCallback(this.frameCallback);
    }

    private final void stopRendering() {
        this.running = false;
        Choreographer.getInstance().removeFrameCallback(this.frameCallback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:255:0x0717, code lost:
    
        if (((-3.0d > r1 || r1 > 8.0d) ? 0 : r4) != 0) goto L259;
     */
    /* JADX WARN: Code restructure failed: missing block: B:395:0x0b94, code lost:
    
        if (kotlin.jvm.internal.Intrinsics.areEqual(r20.getKind(), r9) != false) goto L381;
     */
    /* JADX WARN: Failed to find 'out' block for switch in B:266:0x0743. Please report as an issue. */
    /* JADX WARN: Removed duplicated region for block: B:118:0x0318  */
    /* JADX WARN: Removed duplicated region for block: B:122:0x033b  */
    /* JADX WARN: Removed duplicated region for block: B:218:0x0610 A[EDGE_INSN: B:218:0x0610->B:219:0x0610 BREAK  A[LOOP:3: B:120:0x0328->B:162:0x0328], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:222:0x0635  */
    /* JADX WARN: Removed duplicated region for block: B:259:0x0721 A[LOOP:6: B:238:0x0670->B:259:0x0721, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:260:0x071f A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:271:0x0785  */
    /* JADX WARN: Removed duplicated region for block: B:276:0x07e2  */
    /* JADX WARN: Removed duplicated region for block: B:278:0x0822  */
    /* JADX WARN: Removed duplicated region for block: B:283:0x0862  */
    /* JADX WARN: Removed duplicated region for block: B:294:0x0a04  */
    /* JADX WARN: Removed duplicated region for block: B:297:0x0a0e  */
    /* JADX WARN: Removed duplicated region for block: B:304:0x0910  */
    /* JADX WARN: Removed duplicated region for block: B:330:0x0840 A[ADDED_TO_REGION, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:331:0x0813  */
    /* JADX WARN: Removed duplicated region for block: B:334:0x07d4  */
    /* JADX WARN: Removed duplicated region for block: B:350:0x0a84  */
    /* JADX WARN: Removed duplicated region for block: B:386:0x0b56  */
    /* JADX WARN: Removed duplicated region for block: B:398:0x0ba6  */
    /* JADX WARN: Removed duplicated region for block: B:401:0x0ba9 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:410:0x0bd6  */
    /* JADX WARN: Removed duplicated region for block: B:443:0x031a  */
    /* JADX WARN: Removed duplicated region for block: B:456:0x02e7 A[LOOP:11: B:446:0x02ac->B:456:0x02e7, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:457:0x02e5 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:464:0x0212  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x020d  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0243  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x025e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateScene(long nowNs) {
        double d;
        long nowMs;
        boolean z;
        List emptyList;
        List list;
        long nowMs2;
        boolean z2;
        List emptyList2;
        Function1 pathHeading;
        Iterator<Traffic.Vehicle> it;
        LaneMarks laneMarks;
        boolean latActive;
        Function1 pathHeading2;
        boolean latActive2;
        Iterator<Traffic.Vehicle> it2;
        Iterator<Detector.Obj> it3;
        Iterator<Car> it4;
        Iterable $this$any$iv;
        String str;
        String str2;
        boolean z3;
        ArrayList placed;
        HashSet used;
        ArrayList placed2;
        HashSet claimed;
        SceneView sceneView;
        Iterator<Detector.Obj> it5;
        String str3;
        String str4;
        Function1 pathHeading3;
        String str5;
        String kindName;
        int i;
        int i2;
        HashSet used2;
        ArrayList placed3;
        int i3;
        int i4;
        Car car;
        Car car2;
        HashSet claimed2;
        HashSet used3;
        ArrayList placed4;
        double oy;
        Function1 pathHeading4;
        double doubleValue;
        double oy2;
        double atan2;
        Car car3;
        boolean z4;
        char c;
        char c2;
        String str6;
        String str7;
        Telemetry t;
        Detector.Obj seen;
        HashSet claimed3;
        float fov;
        HashSet used4;
        ModelSnapshot m;
        double dt;
        boolean latActive3;
        HashSet claimed4;
        Function1 pathHeading5;
        Telemetry t2;
        LaneMarks.Mark[] mk;
        Iterable $this$any$iv2;
        boolean z5;
        Iterable emptyList3;
        double rel = this.lastFrameNs == 0 ? 0.016666666666666666d : RangesKt.coerceIn((nowNs - this.lastFrameNs) / 1.0E9d, 0.001d, 0.1d);
        this.lastFrameNs = nowNs;
        if (this.paletteDirty) {
            applyPalette();
        }
        Telemetry telemetry = this.t;
        if (telemetry == null) {
            return;
        }
        Telemetry t3 = telemetry;
        updateLayout(nowNs, t3);
        setupCamera(t3);
        final ModelSnapshot m2 = t3.getModel();
        boolean started = t3.getStarted();
        boolean latActive4 = t3.getEnabled() || t3.getAolEnabled();
        if (m2 == null || !started) {
            if (this.lastRibbonFrame != -1) {
                this.lastRibbonFrame = -1L;
                clearRibbons();
            }
        } else if (m2.getFrameId() != this.lastRibbonFrame || this.uiStatus != this.lastRibbonStatus || latActive4 != this.lastRibbonEnabled) {
            this.lastRibbonFrame = m2.getFrameId();
            this.lastRibbonStatus = this.uiStatus;
            this.lastRibbonEnabled = latActive4;
            rebuildRibbons(m2, t3, latActive4);
        }
        long nowMs3 = System.currentTimeMillis();
        boolean blinkOn = (nowMs3 / ((long) 400)) % ((long) 2) == 0;
        double d2 = 1;
        double k = d2 - Math.exp((-rel) / 0.09d);
        Car e = this.ego;
        if (e != null) {
            e.place(2.3d - (e.getLength() / 2), 0.0d, 0.0d);
            e.lights(t3.getBrakePressed() || t3.getBrakeLights(), this.night, t3.getLeftBlinker(), t3.getRightBlinker(), blinkOn);
            Unit unit = Unit.INSTANCE;
            Unit unit2 = Unit.INSTANCE;
        }
        if (started && this.showTraffic) {
            d = d2;
            nowMs = nowMs3;
            z = true;
            emptyList = this.traffic.update(t3, nowMs, t3.s("radar_tracks"), true);
        } else {
            d = d2;
            nowMs = nowMs3;
            z = true;
            emptyList = CollectionsKt.emptyList();
        }
        List seenVehicles = emptyList;
        if (started && this.showTraffic && t3.s("car_memory")) {
            List list2 = seenVehicles;
            CarMemory carMemory = this.carMemory;
            if (carMemory == null || (emptyList3 = carMemory.getGhosts()) == null) {
                emptyList3 = CollectionsKt.emptyList();
            }
            Iterable $this$map$iv = emptyList3;
            int $i$f$map = 0;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            for (Object item$iv$iv : $this$map$iv) {
                CarMemory.Car g = (CarMemory.Car) item$iv$iv;
                Traffic.Vehicle v = new Traffic.Vehicle((-1000000) - g.getId());
                v.setX(g.getX());
                v.setY(g.getY());
                v.setVRel(g.getVRel());
                v.setVAbs(g.getVAbs());
                v.setRemembered(z);
                v.setKind((int) (g.getId() & 3));
                destination$iv$iv.add(v);
                $this$map$iv = $this$map$iv;
                $i$f$map = $i$f$map;
            }
            list = CollectionsKt.plus((Collection) list2, destination$iv$iv);
        } else {
            list = seenVehicles;
        }
        List vehicles = list;
        final SceneView sceneView2 = this;
        Detector det = sceneView2.detector;
        if (det == null || !det.getRunning()) {
            nowMs2 = nowMs;
        } else {
            nowMs2 = nowMs;
            if (nowMs2 - det.getObjectsAtMs() < 700) {
                z2 = z;
                boolean camFresh = z2;
                if (!started && camFresh && sceneView2.showTraffic) {
                    Intrinsics.checkNotNull(det);
                    emptyList2 = det.getObjects();
                } else {
                    emptyList2 = CollectionsKt.emptyList();
                }
                List objs = emptyList2;
                float fov2 = det == null ? det.getFovTan() : 0.5f;
                HashSet used5 = new HashSet();
                HashSet claimed5 = new HashSet();
                pathHeading = new Function1() { // from class: com.starpilot.starview.SceneView$$ExternalSyntheticLambda13
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        double updateScene$lambda$30;
                        updateScene$lambda$30 = SceneView.updateScene$lambda$30(ModelSnapshot.this, sceneView2, ((Double) obj).doubleValue());
                        return Double.valueOf(updateScene$lambda$30);
                    }
                };
                Detector det2 = det;
                double kh = d - Math.exp((-rel) / 0.35d);
                it = vehicles.iterator();
                while (it.hasNext()) {
                    if (it.next().getVAbs() < -3.0f) {
                        sceneView2.oncomingSeenMs = nowMs2;
                    }
                }
                for (Detector.Obj o : objs) {
                    if (updateScene$vehicle(o.getCls()) && o.getVelValid() && o.getVx() < -3.0f) {
                        sceneView2.oncomingSeenMs = nowMs2;
                    }
                }
                laneMarks = sceneView2.laneMarks;
                if (laneMarks != null || (mk = laneMarks.getMarks()) == null) {
                    latActive = latActive4;
                    pathHeading2 = pathHeading;
                    latActive2 = false;
                } else {
                    latActive = latActive4;
                    Iterable $this$any$iv3 = new IntRange(0, 1);
                    if (($this$any$iv3 instanceof Collection) && ((Collection) $this$any$iv3).isEmpty()) {
                        pathHeading2 = pathHeading;
                        latActive2 = false;
                    } else {
                        Iterator it6 = $this$any$iv3.iterator();
                        while (true) {
                            if (!it6.hasNext()) {
                                pathHeading2 = pathHeading;
                                latActive2 = false;
                                break;
                            }
                            int element$iv = ((IntIterator) it6).nextInt();
                            if (mk[element$iv].getYellow()) {
                                $this$any$iv2 = $this$any$iv3;
                                pathHeading2 = pathHeading;
                                if (mk[element$iv].getKind() == 3 || mk[element$iv].getKind() == 2) {
                                    z5 = true;
                                    if (!z5) {
                                        latActive2 = true;
                                        break;
                                    } else {
                                        pathHeading = pathHeading2;
                                        $this$any$iv3 = $this$any$iv2;
                                    }
                                }
                            } else {
                                $this$any$iv2 = $this$any$iv3;
                                pathHeading2 = pathHeading;
                            }
                            z5 = false;
                            if (!z5) {
                            }
                        }
                    }
                }
                boolean yellowLeft = latActive2;
                boolean twoWay = !yellowLeft || nowMs2 - sceneView2.oncomingSeenMs < 60000;
                boolean z6 = t3.getVEgo() <= 3.0f;
                ArrayList placed5 = new ArrayList();
                it2 = vehicles.iterator();
                int drawn = 0;
                while (true) {
                    String str8 = "truck.glb";
                    String str9 = "bus.glb";
                    if (it2.hasNext()) {
                        break;
                    }
                    Traffic.Vehicle v2 = it2.next();
                    Detector.Obj seen2 = null;
                    Iterator<Detector.Obj> it7 = objs.iterator();
                    while (true) {
                        if (!it7.hasNext()) {
                            str6 = str9;
                            str7 = str8;
                            t = t3;
                            seen = seen2;
                            break;
                        }
                        Detector.Obj o2 = it7.next();
                        if (updateScene$vehicle(o2.getCls())) {
                            Detector.Obj seen3 = seen2;
                            if (claimed5.contains(Long.valueOf(o2.getId()))) {
                                seen2 = seen3;
                            } else {
                                str6 = str9;
                                str7 = str8;
                                Iterator<Detector.Obj> it8 = it7;
                                t = t3;
                                if (updateScene$sameCar(o2.getX(), o2.getY(), v2.getX(), v2.getY())) {
                                    seen = o2;
                                    break;
                                }
                                seen2 = seen3;
                                str9 = str6;
                                str8 = str7;
                                t3 = t;
                                it7 = it8;
                            }
                        }
                    }
                    if (seen != null) {
                        claimed5.add(Long.valueOf(seen.getId()));
                        claimed3 = claimed5;
                    } else if (!camFresh || v2.getLead() || Math.abs(v2.getVAbs()) >= 2.0f || v2.getX() >= 70.0d) {
                        claimed3 = claimed5;
                    } else {
                        claimed3 = claimed5;
                        if (Math.abs(v2.getY()) < v2.getX() * fov2 * 0.9d) {
                            fov = fov2;
                            used4 = used5;
                            m = m2;
                            dt = rel;
                            latActive3 = latActive;
                            claimed4 = claimed3;
                            pathHeading5 = pathHeading2;
                            t2 = t;
                            used5 = used4;
                            latActive = latActive3;
                            pathHeading2 = pathHeading5;
                            fov2 = fov;
                            t3 = t2;
                            m2 = m;
                            rel = dt;
                            claimed5 = claimed4;
                            sceneView2 = this;
                        }
                    }
                    String kindName2 = seen != null && seen.getCls() == 7 ? str7 : (!(seen != null && seen.getCls() == 5) || v2.getX() <= 8.0d) ? sceneView2.genericKinds[v2.getKind() & 3] : str6;
                    double yEst = seen != null ? seen.getY() : v2.getY();
                    if (v2.getLead() || !updateScene$occupied(placed5, v2.getX() + 2.3d, yEst, updateScene$halfLenOf(kindName2))) {
                        placed5.add(new double[]{v2.getX() + updateScene$halfLenOf(kindName2), yEst, updateScene$halfLenOf(kindName2)});
                        latActive3 = latActive;
                        pathHeading5 = pathHeading2;
                        claimed4 = claimed3;
                        used4 = used5;
                        fov = fov2;
                        m = m2;
                        t2 = t;
                        dt = rel;
                        Car car4 = updateScene$slotNear(this, used5, nowMs2, v2.getId(), kindName2, v2.getX() + 2.3d, yEst);
                        if (car4 != null || (car4 = slotFor(v2.getId(), kindName2, nowMs2)) != null) {
                            used4.add(car4);
                            int drawn2 = drawn + 1;
                            boolean standingV = !v2.getLead() ? Math.abs(v2.getVAbs()) >= 1.5f : Math.abs(v2.getVAbs()) >= 0.8f || t2.getVEgo() >= 0.5f;
                            long nowMs4 = nowMs2;
                            double y = updateScene$snapY(this, m, v2.getX(), seen != null ? seen.getY() : v2.getY(), standingV);
                            ArrayList placed6 = placed5;
                            Detector det3 = det2;
                            double hdg = updateScene$vehHeading(t2, pathHeading5, twoWay, this, m, seen, v2.getX(), v2.getVAbs(), y);
                            double rel2 = hdg - Math.atan2(y, v2.getX());
                            updateScene$move(this, dt, k, nowMs4, kh, car4, v2.getX() + (((car4.getLength() * Math.abs(Math.cos(rel2))) + (Math.abs(Math.sin(rel2)) * 1.9d)) / 2), y, hdg, standingV && t2.getVEgo() < 25.0f);
                            car4.remembered(v2.getRemembered());
                            car4.lights(v2.getBrake(), this.night, false, false, false);
                            car4.show(true);
                            drawn = drawn2;
                            used5 = used4;
                            sceneView2 = this;
                            latActive = latActive3;
                            pathHeading2 = pathHeading5;
                            fov2 = fov;
                            t3 = t2;
                            m2 = m;
                            rel = dt;
                            claimed5 = claimed4;
                            det2 = det3;
                            placed5 = placed6;
                            nowMs2 = nowMs4;
                        }
                    } else {
                        fov = fov2;
                        used4 = used5;
                        m = m2;
                        dt = rel;
                        latActive3 = latActive;
                        claimed4 = claimed3;
                        pathHeading5 = pathHeading2;
                        t2 = t;
                    }
                    used5 = used4;
                    latActive = latActive3;
                    pathHeading2 = pathHeading5;
                    fov2 = fov;
                    t3 = t2;
                    m2 = m;
                    rel = dt;
                    claimed5 = claimed4;
                    sceneView2 = this;
                }
                String str10 = "bus.glb";
                HashSet claimed6 = claimed5;
                String str11 = "truck.glb";
                HashSet used6 = used5;
                Telemetry t4 = t3;
                ModelSnapshot m3 = m2;
                double dt2 = rel;
                long nowMs5 = nowMs2;
                ArrayList placed7 = placed5;
                SceneView sceneView3 = sceneView2;
                Function1 pathHeading6 = pathHeading2;
                char c3 = 1;
                it3 = objs.iterator();
                while (it3.hasNext()) {
                    Detector.Obj o3 = it3.next();
                    HashSet claimed7 = claimed6;
                    if (!claimed7.contains(Long.valueOf(o3.getId()))) {
                        if (updateScene$vehicle(o3.getCls())) {
                            List $this$any$iv4 = vehicles;
                            int $i$f$any = 0;
                            if (($this$any$iv4 instanceof Collection) && $this$any$iv4.isEmpty()) {
                                c = 0;
                            } else {
                                Iterator it9 = $this$any$iv4.iterator();
                                while (true) {
                                    if (it9.hasNext()) {
                                        Object element$iv2 = it9.next();
                                        Traffic.Vehicle it10 = (Traffic.Vehicle) element$iv2;
                                        int $i$f$any2 = $i$f$any;
                                        Iterator it11 = it9;
                                        if (!updateScene$sameCar(o3.getX(), o3.getY(), it10.getX(), it10.getY()) && (Math.abs(o3.getX() - it10.getX()) >= (it10.getX() * 0.2d) + 4.0d || Math.abs(o3.getY() - it10.getY()) >= (it10.getX() * 0.06d) + 2.2d)) {
                                            if (it10.getLead() && Math.abs(o3.getY() - it10.getY()) < 1.8d) {
                                                double x = o3.getX() - it10.getX();
                                            }
                                            c2 = 0;
                                            if (c2 == 0) {
                                                c = c3;
                                            } else {
                                                $i$f$any = $i$f$any2;
                                                it9 = it11;
                                            }
                                        }
                                        c2 = c3;
                                        if (c2 == 0) {
                                        }
                                    } else {
                                        c = 0;
                                    }
                                }
                            }
                            if (c != 0) {
                                used = used6;
                                placed2 = placed7;
                                claimed = claimed7;
                                sceneView = sceneView3;
                                it5 = it3;
                                str3 = str10;
                                str4 = str11;
                                pathHeading3 = pathHeading6;
                            }
                        }
                        switch (o3.getCls()) {
                            case 0:
                                str5 = "person.glb";
                                kindName = str5;
                                if (updateScene$vehicle(o3.getCls())) {
                                    i = 3;
                                    i2 = 2;
                                } else if (updateScene$occupied(placed7, o3.getCx(), o3.getCy(), updateScene$halfLenOf(kindName))) {
                                    used = used6;
                                    placed2 = placed7;
                                    claimed = claimed7;
                                    sceneView = sceneView3;
                                    it5 = it3;
                                    str3 = str10;
                                    str4 = str11;
                                    pathHeading3 = pathHeading6;
                                    break;
                                } else {
                                    double cx = o3.getCx();
                                    double cy = o3.getCy();
                                    double updateScene$halfLenOf = updateScene$halfLenOf(kindName);
                                    i = 3;
                                    double[] dArr = new double[3];
                                    dArr[0] = cx;
                                    dArr[c3] = cy;
                                    i2 = 2;
                                    dArr[2] = updateScene$halfLenOf;
                                    placed7.add(dArr);
                                }
                                if (updateScene$vehicle(o3.getCls())) {
                                    used2 = used6;
                                    placed3 = placed7;
                                    it5 = it3;
                                    str3 = str10;
                                    str4 = str11;
                                    i3 = i;
                                    i4 = i2;
                                    car = null;
                                } else {
                                    placed3 = placed7;
                                    str3 = str10;
                                    used2 = used6;
                                    it5 = it3;
                                    String str12 = str11;
                                    i3 = i;
                                    str4 = str12;
                                    i4 = i2;
                                    car = updateScene$slotNear(this, used6, nowMs5, -o3.getId(), kindName, o3.getCx(), o3.getCy());
                                }
                                if (car == null || (car = slotFor(-o3.getId(), kindName, nowMs5)) != null) {
                                    Car car5 = car;
                                    used2.add(car5);
                                    int drawn3 = drawn + 1;
                                    double ox = o3.getX();
                                    double oy3 = o3.getY();
                                    double hd = o3.getHeading();
                                    if (updateScene$vehicle(o3.getCls())) {
                                        car2 = car5;
                                        claimed2 = claimed7;
                                        used3 = used2;
                                        int i5 = i3;
                                        placed4 = placed3;
                                        if (o3.getCls() == 1) {
                                            oy = oy3;
                                        } else if (o3.getCls() == i5) {
                                            oy = oy3;
                                        } else if (o3.getCls() == 0) {
                                            if (o3.getMoving()) {
                                                atan2 = hd;
                                                oy2 = oy3;
                                            } else {
                                                oy2 = oy3;
                                                atan2 = Math.atan2(oy2, ox) + 3.141592653589793d;
                                            }
                                            updateScene$move$default(this, dt2, k, nowMs5, kh, car2, ox, oy2, atan2, false, 512, null);
                                            pathHeading4 = pathHeading6;
                                        } else if (o3.getCls() == 9 || o3.getCls() == 11) {
                                            updateScene$move$default(this, dt2, k, nowMs5, kh, car2, ox, oy3, 3.141592653589793d, false, 512, null);
                                            pathHeading4 = pathHeading6;
                                        } else {
                                            updateScene$move$default(this, dt2, k, nowMs5, kh, car2, ox, oy3, 0.0d, false, 512, null);
                                            pathHeading4 = pathHeading6;
                                        }
                                        double length = ox + (car2.getLength() / 2);
                                        if (o3.getMoving()) {
                                            pathHeading4 = pathHeading6;
                                            doubleValue = hd;
                                        } else {
                                            pathHeading4 = pathHeading6;
                                            doubleValue = ((Number) pathHeading4.invoke(Double.valueOf(ox))).doubleValue();
                                        }
                                        updateScene$move$default(this, dt2, k, nowMs5, kh, car2, length, oy, doubleValue, false, 512, null);
                                    } else {
                                        double h = updateScene$vehHeading(t4, pathHeading6, twoWay, this, m3, o3, ox, Float.NaN, oy3);
                                        double b = Math.atan2(oy3, ox);
                                        double rel3 = h - b;
                                        double depth = ((car5.getLength() * Math.abs(Math.cos(rel3))) + (Math.abs(Math.sin(rel3)) * 1.9d)) / i4;
                                        if (o3.getMoving() || (o3.getVelValid() && Math.abs(o3.getVx()) >= 1.5f)) {
                                            if (!updateScene$atPictureEdge(ox, oy3) && !updateScene$inParking(this, m3, ox, oy3)) {
                                                z4 = false;
                                                boolean standing = z4;
                                                double cx2 = (Math.cos(b) * depth) + ox;
                                                used3 = used2;
                                                car2 = car5;
                                                claimed2 = claimed7;
                                                placed4 = placed3;
                                                updateScene$move(this, dt2, k, nowMs5, kh, car2, cx2, updateScene$snapY(this, m3, cx2, (Math.sin(b) * depth) + oy3, standing), h, standing);
                                                pathHeading4 = pathHeading6;
                                            }
                                        }
                                        z4 = true;
                                        boolean standing2 = z4;
                                        double cx22 = (Math.cos(b) * depth) + ox;
                                        used3 = used2;
                                        car2 = car5;
                                        claimed2 = claimed7;
                                        placed4 = placed3;
                                        updateScene$move(this, dt2, k, nowMs5, kh, car2, cx22, updateScene$snapY(this, m3, cx22, (Math.sin(b) * depth) + oy3, standing2), h, standing2);
                                        pathHeading4 = pathHeading6;
                                    }
                                    if (o3.getCls() != 9) {
                                        car3 = car2;
                                        car3.lamp(o3.getLamp());
                                    } else {
                                        car3 = car2;
                                    }
                                    car3.remembered(false);
                                    car3.lights(false, this.night, false, false, false);
                                    car3.show(true);
                                    used6 = used3;
                                    pathHeading6 = pathHeading4;
                                    sceneView3 = this;
                                    c3 = 1;
                                    it3 = it5;
                                    str11 = str4;
                                    drawn = drawn3;
                                    claimed6 = claimed2;
                                    str10 = str3;
                                    placed7 = placed4;
                                    break;
                                } else {
                                    sceneView = this;
                                    claimed = claimed7;
                                    used = used2;
                                    placed2 = placed3;
                                    pathHeading3 = pathHeading6;
                                    break;
                                }
                                break;
                            case 1:
                                str5 = "bicycle.glb";
                                kindName = str5;
                                if (updateScene$vehicle(o3.getCls())) {
                                }
                                if (updateScene$vehicle(o3.getCls())) {
                                }
                                if (car == null) {
                                    break;
                                }
                                Car car52 = car;
                                used2.add(car52);
                                int drawn32 = drawn + 1;
                                double ox2 = o3.getX();
                                double oy32 = o3.getY();
                                double hd2 = o3.getHeading();
                                if (updateScene$vehicle(o3.getCls())) {
                                }
                                if (o3.getCls() != 9) {
                                }
                                car3.remembered(false);
                                car3.lights(false, this.night, false, false, false);
                                car3.show(true);
                                used6 = used3;
                                pathHeading6 = pathHeading4;
                                sceneView3 = this;
                                c3 = 1;
                                it3 = it5;
                                str11 = str4;
                                drawn = drawn32;
                                claimed6 = claimed2;
                                str10 = str3;
                                placed7 = placed4;
                                break;
                            case 2:
                                kindName = sceneView3.genericKinds[(int) (o3.getId() & 3)];
                                if (updateScene$vehicle(o3.getCls())) {
                                }
                                if (updateScene$vehicle(o3.getCls())) {
                                }
                                if (car == null) {
                                }
                                Car car522 = car;
                                used2.add(car522);
                                int drawn322 = drawn + 1;
                                double ox22 = o3.getX();
                                double oy322 = o3.getY();
                                double hd22 = o3.getHeading();
                                if (updateScene$vehicle(o3.getCls())) {
                                }
                                if (o3.getCls() != 9) {
                                }
                                car3.remembered(false);
                                car3.lights(false, this.night, false, false, false);
                                car3.show(true);
                                used6 = used3;
                                pathHeading6 = pathHeading4;
                                sceneView3 = this;
                                c3 = 1;
                                it3 = it5;
                                str11 = str4;
                                drawn = drawn322;
                                claimed6 = claimed2;
                                str10 = str3;
                                placed7 = placed4;
                                break;
                            case 3:
                                str5 = "motorcycle.glb";
                                kindName = str5;
                                if (updateScene$vehicle(o3.getCls())) {
                                }
                                if (updateScene$vehicle(o3.getCls())) {
                                }
                                if (car == null) {
                                }
                                Car car5222 = car;
                                used2.add(car5222);
                                int drawn3222 = drawn + 1;
                                double ox222 = o3.getX();
                                double oy3222 = o3.getY();
                                double hd222 = o3.getHeading();
                                if (updateScene$vehicle(o3.getCls())) {
                                }
                                if (o3.getCls() != 9) {
                                }
                                car3.remembered(false);
                                car3.lights(false, this.night, false, false, false);
                                car3.show(true);
                                used6 = used3;
                                pathHeading6 = pathHeading4;
                                sceneView3 = this;
                                c3 = 1;
                                it3 = it5;
                                str11 = str4;
                                drawn = drawn3222;
                                claimed6 = claimed2;
                                str10 = str3;
                                placed7 = placed4;
                                break;
                            case 4:
                            case 6:
                            case 8:
                            case 10:
                            default:
                                used = used6;
                                placed2 = placed7;
                                claimed = claimed7;
                                sceneView = sceneView3;
                                it5 = it3;
                                str3 = str10;
                                str4 = str11;
                                pathHeading3 = pathHeading6;
                                break;
                            case 5:
                                kindName = str10;
                                if (updateScene$vehicle(o3.getCls())) {
                                }
                                if (updateScene$vehicle(o3.getCls())) {
                                }
                                if (car == null) {
                                }
                                Car car52222 = car;
                                used2.add(car52222);
                                int drawn32222 = drawn + 1;
                                double ox2222 = o3.getX();
                                double oy32222 = o3.getY();
                                double hd2222 = o3.getHeading();
                                if (updateScene$vehicle(o3.getCls())) {
                                }
                                if (o3.getCls() != 9) {
                                }
                                car3.remembered(false);
                                car3.lights(false, this.night, false, false, false);
                                car3.show(true);
                                used6 = used3;
                                pathHeading6 = pathHeading4;
                                sceneView3 = this;
                                c3 = 1;
                                it3 = it5;
                                str11 = str4;
                                drawn = drawn32222;
                                claimed6 = claimed2;
                                str10 = str3;
                                placed7 = placed4;
                                break;
                            case 7:
                                kindName = str11;
                                if (updateScene$vehicle(o3.getCls())) {
                                }
                                if (updateScene$vehicle(o3.getCls())) {
                                }
                                if (car == null) {
                                }
                                Car car522222 = car;
                                used2.add(car522222);
                                int drawn322222 = drawn + 1;
                                double ox22222 = o3.getX();
                                double oy322222 = o3.getY();
                                double hd22222 = o3.getHeading();
                                if (updateScene$vehicle(o3.getCls())) {
                                }
                                if (o3.getCls() != 9) {
                                }
                                car3.remembered(false);
                                car3.lights(false, this.night, false, false, false);
                                car3.show(true);
                                used6 = used3;
                                pathHeading6 = pathHeading4;
                                sceneView3 = this;
                                c3 = 1;
                                it3 = it5;
                                str11 = str4;
                                drawn = drawn322222;
                                claimed6 = claimed2;
                                str10 = str3;
                                placed7 = placed4;
                                break;
                            case 9:
                                str5 = "traffic_light.glb";
                                kindName = str5;
                                if (updateScene$vehicle(o3.getCls())) {
                                }
                                if (updateScene$vehicle(o3.getCls())) {
                                }
                                if (car == null) {
                                }
                                Car car5222222 = car;
                                used2.add(car5222222);
                                int drawn3222222 = drawn + 1;
                                double ox222222 = o3.getX();
                                double oy3222222 = o3.getY();
                                double hd222222 = o3.getHeading();
                                if (updateScene$vehicle(o3.getCls())) {
                                }
                                if (o3.getCls() != 9) {
                                }
                                car3.remembered(false);
                                car3.lights(false, this.night, false, false, false);
                                car3.show(true);
                                used6 = used3;
                                pathHeading6 = pathHeading4;
                                sceneView3 = this;
                                c3 = 1;
                                it3 = it5;
                                str11 = str4;
                                drawn = drawn3222222;
                                claimed6 = claimed2;
                                str10 = str3;
                                placed7 = placed4;
                                break;
                            case 11:
                                str5 = "stop_sign.glb";
                                kindName = str5;
                                if (updateScene$vehicle(o3.getCls())) {
                                }
                                if (updateScene$vehicle(o3.getCls())) {
                                }
                                if (car == null) {
                                }
                                Car car52222222 = car;
                                used2.add(car52222222);
                                int drawn32222222 = drawn + 1;
                                double ox2222222 = o3.getX();
                                double oy32222222 = o3.getY();
                                double hd2222222 = o3.getHeading();
                                if (updateScene$vehicle(o3.getCls())) {
                                }
                                if (o3.getCls() != 9) {
                                }
                                car3.remembered(false);
                                car3.lights(false, this.night, false, false, false);
                                car3.show(true);
                                used6 = used3;
                                pathHeading6 = pathHeading4;
                                sceneView3 = this;
                                c3 = 1;
                                it3 = it5;
                                str11 = str4;
                                drawn = drawn32222222;
                                claimed6 = claimed2;
                                str10 = str3;
                                placed7 = placed4;
                                break;
                        }
                    } else {
                        used = used6;
                        placed2 = placed7;
                        claimed = claimed7;
                        sceneView = sceneView3;
                        it5 = it3;
                        str3 = str10;
                        str4 = str11;
                        pathHeading3 = pathHeading6;
                    }
                    used6 = used;
                    pathHeading6 = pathHeading3;
                    sceneView3 = sceneView;
                    it3 = it5;
                    str11 = str4;
                    claimed6 = claimed;
                    str10 = str3;
                    placed7 = placed2;
                    c3 = 1;
                }
                HashSet used7 = used6;
                ArrayList placed8 = placed7;
                SceneView sceneView4 = sceneView3;
                String str13 = str10;
                String str14 = str11;
                it4 = sceneView4.genericPool.iterator();
                Intrinsics.checkNotNullExpressionValue(it4, "iterator(...)");
                while (it4.hasNext()) {
                    Car next = it4.next();
                    Intrinsics.checkNotNullExpressionValue(next, "next(...)");
                    Car c4 = next;
                    if (!used7.contains(c4) && c4.getInScene()) {
                        if (Double.isNaN(c4.getWx()) || nowMs5 - c4.getLastUsed() >= 30000) {
                            placed = placed8;
                        } else {
                            Pair<Double, Double> fromWorld = sceneView4.fromWorld(c4.getWx(), c4.getWy());
                            double cx3 = fromWorld.component1().doubleValue();
                            double cy2 = fromWorld.component2().doubleValue();
                            if (cx3 > -10.0d) {
                                c4.setX(cx3);
                                c4.setY(cy2);
                                c4.place(cx3, cy2, c4.getHeading());
                                placed8.add(new double[]{cx3, cy2, updateScene$halfLenOf(c4.getKind())});
                            } else {
                                placed = placed8;
                            }
                        }
                        if (nowMs5 - c4.getLastUsed() > 700) {
                            c4.show(false);
                            c4.setId(Long.MIN_VALUE);
                            c4.setX(Double.NaN);
                            c4.setWx(Double.NaN);
                            c4.setFlipSince(0L);
                            placed8 = placed;
                        } else {
                            placed8 = placed;
                        }
                    }
                }
                Iterable $this$filter$iv = sceneView4.genericPool;
                Collection destination$iv$iv2 = new ArrayList();
                for (Object element$iv$iv : $this$filter$iv) {
                    Car it12 = (Car) element$iv$iv;
                    if (!it12.getInScene() || Double.isNaN(it12.getX())) {
                        str = str14;
                        str2 = str13;
                    } else {
                        if (StringsKt.startsWith$default(it12.getKind(), "car_", false, 2, (Object) null)) {
                            str = str14;
                        } else {
                            str = str14;
                            if (!Intrinsics.areEqual(it12.getKind(), str)) {
                                str2 = str13;
                            }
                        }
                        str2 = str13;
                        z3 = true;
                        if (!z3) {
                            destination$iv$iv2.add(element$iv$iv);
                        }
                        str14 = str;
                        str13 = str2;
                    }
                    z3 = false;
                    if (!z3) {
                    }
                    str14 = str;
                    str13 = str2;
                }
                Iterable $this$sortedBy$iv = (List) destination$iv$iv2;
                List<Car> vehicleModels = CollectionsKt.sortedWith($this$sortedBy$iv, new Comparator() { // from class: com.starpilot.starview.SceneView$updateScene$$inlined$sortedBy$1
                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // java.util.Comparator
                    public final int compare(T t5, T t6) {
                        SceneView.Car it13 = (SceneView.Car) t5;
                        SceneView.Car it14 = (SceneView.Car) t6;
                        return ComparisonsKt.compareValues(Long.valueOf(it13.getFirstShownMs()), Long.valueOf(it14.getFirstShownMs()));
                    }
                });
                ArrayList keep = new ArrayList();
                for (Car c5 : vehicleModels) {
                    double hl = updateScene$halfLenOf(c5.getKind());
                    ArrayList $this$any$iv5 = keep;
                    if (($this$any$iv5 instanceof Collection) && $this$any$iv5.isEmpty()) {
                        $this$any$iv = null;
                    } else {
                        Iterator it13 = $this$any$iv5.iterator();
                        while (true) {
                            if (it13.hasNext()) {
                                Object element$iv3 = it13.next();
                                Car o4 = (Car) element$iv3;
                                if (((Math.abs(o4.getX() - c5.getX()) >= (hl + updateScene$halfLenOf(o4.getKind())) * 0.92d || Math.abs(o4.getY() - c5.getY()) >= 1.7d) ? null : 1) != null) {
                                    $this$any$iv = 1;
                                }
                            } else {
                                $this$any$iv = null;
                            }
                        }
                    }
                    if ($this$any$iv != null) {
                        c5.show(false);
                        c5.setX(Double.NaN);
                        c5.setWx(Double.NaN);
                        c5.setId(Long.MIN_VALUE);
                        Integer.valueOf(drawn);
                        drawn--;
                    } else {
                        Boolean.valueOf(keep.add(c5));
                    }
                }
                sceneView4.shownCars = drawn;
            }
        }
        z2 = false;
        boolean camFresh2 = z2;
        if (!started) {
        }
        emptyList2 = CollectionsKt.emptyList();
        List objs2 = emptyList2;
        float fov22 = det == null ? det.getFovTan() : 0.5f;
        HashSet used52 = new HashSet();
        HashSet claimed52 = new HashSet();
        pathHeading = new Function1() { // from class: com.starpilot.starview.SceneView$$ExternalSyntheticLambda13
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                double updateScene$lambda$30;
                updateScene$lambda$30 = SceneView.updateScene$lambda$30(ModelSnapshot.this, sceneView2, ((Double) obj).doubleValue());
                return Double.valueOf(updateScene$lambda$30);
            }
        };
        Detector det22 = det;
        double kh2 = d - Math.exp((-rel) / 0.35d);
        it = vehicles.iterator();
        while (it.hasNext()) {
        }
        while (r1.hasNext()) {
        }
        laneMarks = sceneView2.laneMarks;
        if (laneMarks != null) {
        }
        latActive = latActive4;
        pathHeading2 = pathHeading;
        latActive2 = false;
        boolean yellowLeft2 = latActive2;
        if (yellowLeft2) {
        }
        if (t3.getVEgo() <= 3.0f) {
        }
        ArrayList placed52 = new ArrayList();
        it2 = vehicles.iterator();
        int drawn4 = 0;
        while (true) {
            String str82 = "truck.glb";
            String str92 = "bus.glb";
            if (it2.hasNext()) {
            }
        }
        String str102 = "bus.glb";
        HashSet claimed62 = claimed52;
        String str112 = "truck.glb";
        HashSet used62 = used52;
        Telemetry t42 = t3;
        ModelSnapshot m32 = m2;
        double dt22 = rel;
        long nowMs52 = nowMs2;
        ArrayList placed72 = placed52;
        SceneView sceneView32 = sceneView2;
        Function1 pathHeading62 = pathHeading2;
        char c32 = 1;
        it3 = objs2.iterator();
        while (it3.hasNext()) {
        }
        HashSet used72 = used62;
        ArrayList placed82 = placed72;
        SceneView sceneView42 = sceneView32;
        String str132 = str102;
        String str142 = str112;
        it4 = sceneView42.genericPool.iterator();
        Intrinsics.checkNotNullExpressionValue(it4, "iterator(...)");
        while (it4.hasNext()) {
        }
        Iterable $this$filter$iv2 = sceneView42.genericPool;
        Collection destination$iv$iv22 = new ArrayList();
        while (r14.hasNext()) {
        }
        Iterable $this$sortedBy$iv2 = (List) destination$iv$iv22;
        List<Car> vehicleModels2 = CollectionsKt.sortedWith($this$sortedBy$iv2, new Comparator() { // from class: com.starpilot.starview.SceneView$updateScene$$inlined$sortedBy$1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.Comparator
            public final int compare(T t5, T t6) {
                SceneView.Car it132 = (SceneView.Car) t5;
                SceneView.Car it14 = (SceneView.Car) t6;
                return ComparisonsKt.compareValues(Long.valueOf(it132.getFirstShownMs()), Long.valueOf(it14.getFirstShownMs()));
            }
        });
        ArrayList keep2 = new ArrayList();
        while (r3.hasNext()) {
        }
        sceneView42.shownCars = drawn4;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final double updateScene$lambda$30(ModelSnapshot $m, SceneView this$0, double x) {
        if ($m != null) {
            return this$0.headingAt($m, x);
        }
        return 0.0d;
    }

    static /* synthetic */ void updateScene$move$default(SceneView sceneView, double d, double d2, long j, double d3, Car car, double d4, double d5, double d6, boolean z, int i, Object obj) {
        updateScene$move(sceneView, d, d2, j, d3, car, d4, d5, d6, (i & 512) != 0 ? false : z);
    }

    private static final void updateScene$move(SceneView this$0, double dt, double k, long nowMs, double kh, Car car, double tx0, double ty0, double th, boolean standing) {
        double cx;
        double cy;
        double d;
        double gy;
        double d2;
        if (standing) {
            Pair<Double, Double> world = this$0.toWorld(tx0, ty0);
            double gx = world.component1().doubleValue();
            double gy2 = world.component2().doubleValue();
            if (Double.isNaN(car.getWx())) {
                car.setWx(gx);
                car.setWy(gy2);
            } else {
                double dx = gx - car.getWx();
                double dy = gy2 - car.getWy();
                if ((dx * dx) + (dy * dy) > 2.25d) {
                    d = 1;
                    gy = -dt;
                    d2 = 1.0d;
                } else {
                    d = 1;
                    gy = -dt;
                    d2 = 6.0d;
                }
                double g = d - Math.exp(gy / d2);
                car.setWx(car.getWx() + (dx * g));
                car.setWy(car.getWy() + (dy * g));
            }
            Pair<Double, Double> fromWorld = this$0.fromWorld(car.getWx(), car.getWy());
            double cx2 = fromWorld.component1().doubleValue();
            double cy2 = fromWorld.component2().doubleValue();
            cx = cy2;
            cy = cx2;
        } else {
            car.setWx(Double.NaN);
            cx = ty0;
            cy = tx0;
        }
        double ty = car.getX();
        if (Double.isNaN(ty)) {
            car.setX(cy);
            car.setY(cx);
            car.setHeading(th);
        }
        if (standing) {
            car.setX(cy);
            car.setY(cx);
        } else {
            car.setX(car.getX() + ((cy - car.getX()) * k));
            car.setY(car.getY() + ((cx - car.getY()) * k));
        }
        double dh = th - car.getHeading();
        while (dh > 3.141592653589793d) {
            dh -= 6.283185307179586d;
        }
        while (dh < -3.141592653589793d) {
            dh += 6.283185307179586d;
        }
        if (Math.abs(dh) > 2.0d) {
            if (car.getFlipSince() == 0) {
                car.setFlipSince(nowMs);
            }
            if (nowMs - car.getFlipSince() > 600) {
                car.setHeading(th);
                car.setFlipSince(0L);
            }
        } else {
            car.setFlipSince(0L);
            car.setHeading(car.getHeading() + (dh * kh));
        }
        car.place(car.getX(), car.getY(), car.getHeading());
    }

    private static final boolean updateScene$vehicle(int c) {
        return c == 2 || c == 5 || c == 7;
    }

    private static final double updateScene$leftEdgeAt(ModelSnapshot m, double x) {
        if (m != null) {
            if (!(m.getEdgeX().length == 0)) {
                float[] xs = m.getEdgeX()[0];
                float[] ys = m.getEdgeY()[0];
                if (xs.length >= 2) {
                    if ((m.getEdgeStd().length == 0) || m.getEdgeStd()[0] <= 0.8f) {
                        if (x <= xs[0]) {
                            return ys[0];
                        }
                        int length = xs.length;
                        for (int i = 1; i < length; i++) {
                            if (xs[i] >= x) {
                                double f = (x - xs[i - 1]) / Math.max(0.01f, xs[i] - xs[i - 1]);
                                return ys[i - 1] + ((ys[i] - ys[i - 1]) * f);
                            }
                        }
                        return Double.NaN;
                    }
                }
                return Double.NaN;
            }
        }
        return Double.NaN;
    }

    private static final double updateScene$interp(float[] xa, Function1<? super Integer, Double> function1, int n, double x) {
        if (n < 2) {
            return Double.NaN;
        }
        if (x <= xa[0]) {
            return function1.invoke(0).doubleValue();
        }
        for (int i = 1; i < n; i++) {
            if (xa[i] >= x) {
                double f = (x - xa[i - 1]) / Math.max(0.01f, xa[i] - xa[i - 1]);
                return function1.invoke(Integer.valueOf(i - 1)).doubleValue() + ((function1.invoke(Integer.valueOf(i)).doubleValue() - function1.invoke(Integer.valueOf(i - 1)).doubleValue()) * f);
            }
        }
        int i2 = n - 1;
        return function1.invoke(Integer.valueOf(i2)).doubleValue();
    }

    private static final double updateScene$curbAt(ModelSnapshot m, SceneView this$0, int e, double x) {
        if (m == null) {
            return Double.NaN;
        }
        if ((m.getEdgeX().length == 0) || this$0.curbS[e].length != m.getEdgeX()[0].length) {
            return Double.NaN;
        }
        if (!this$0.curbFit[e] && this$0.edgeOk <= 0.3f) {
            return Double.NaN;
        }
        final double[] c = this$0.curbS[e];
        return updateScene$interp(m.getEdgeX()[0], new Function1() { // from class: com.starpilot.starview.SceneView$$ExternalSyntheticLambda9
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                double updateScene$curbAt$lambda$33;
                updateScene$curbAt$lambda$33 = SceneView.updateScene$curbAt$lambda$33(c, ((Integer) obj).intValue());
                return Double.valueOf(updateScene$curbAt$lambda$33);
            }
        }, c.length, x);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final double updateScene$curbAt$lambda$33(double[] $c, int it) {
        return $c[it];
    }

    private static final double updateScene$edgeAtX(SceneView this$0, ModelSnapshot m, int e, double x) {
        if (this$0.shapeOk) {
            return this$0.shapeY(x) + this$0.shapeOff[e];
        }
        if (m == null) {
            return Double.NaN;
        }
        if ((m.getEdgeX().length == 0) || this$0.edgeF[e].length != m.getEdgeX()[0].length) {
            return Double.NaN;
        }
        final float[] c = this$0.edgeF[e];
        return updateScene$interp(m.getEdgeX()[0], new Function1() { // from class: com.starpilot.starview.SceneView$$ExternalSyntheticLambda10
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                double updateScene$edgeAtX$lambda$34;
                updateScene$edgeAtX$lambda$34 = SceneView.updateScene$edgeAtX$lambda$34(c, ((Integer) obj).intValue());
                return Double.valueOf(updateScene$edgeAtX$lambda$34);
            }
        }, c.length, x);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final double updateScene$edgeAtX$lambda$34(float[] $c, int it) {
        return $c[it];
    }

    private static final double[] updateScene$laneCentres(ModelSnapshot m, SceneView this$0, double x) {
        if (m == null) {
            return new double[0];
        }
        ArrayList ys = new ArrayList();
        Iterator<LaneTracks.Line> it = this$0.tracks.getLines().iterator();
        Intrinsics.checkNotNullExpressionValue(it, "iterator(...)");
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            LaneTracks.Line next = it.next();
            Intrinsics.checkNotNullExpressionValue(next, "next(...)");
            LaneTracks.Line l = next;
            if (l.getAlpha() >= 0.3f) {
                LaneMarks.Mark mark = l.getMark();
                if (!(mark != null && mark.getKind() == 1)) {
                    ys.add(Double.valueOf(l.at((float) x)));
                }
            }
        }
        CollectionsKt.sort(ys);
        ArrayList out = new ArrayList();
        int size = ys.size();
        for (int i = 1; i < size; i++) {
            double doubleValue = ((Number) ys.get(i)).doubleValue();
            Object obj = ys.get(i - 1);
            Intrinsics.checkNotNullExpressionValue(obj, "get(...)");
            double w = doubleValue - ((Number) obj).doubleValue();
            if (2.4d <= w && w <= 5.0d) {
                double doubleValue2 = ((Number) ys.get(i)).doubleValue();
                Object obj2 = ys.get(i - 1);
                Intrinsics.checkNotNullExpressionValue(obj2, "get(...)");
                out.add(Double.valueOf((doubleValue2 + ((Number) obj2).doubleValue()) / 2));
            }
        }
        return CollectionsKt.toDoubleArray(out);
    }

    private static final double updateScene$snapY(SceneView this$0, ModelSnapshot m, double x, double y, boolean standing) {
        if (standing) {
            int e = 0;
            while (e < 2) {
                double out = e == 0 ? -1.0d : 1.0d;
                double edge = updateScene$edgeAtX(this$0, m, e, x);
                double curb = updateScene$curbAt(m, this$0, e, x);
                if (!Double.isNaN(edge) && !Double.isNaN(curb) && (curb - edge) * out > 1.5d && (y - edge) * out > 0.3d) {
                    return curb - (1.05d * out);
                }
                e++;
            }
        }
        double[] cs = updateScene$laneCentres(m, this$0, x);
        double best = Double.NaN;
        for (double c : cs) {
            if (Math.abs(c - y) < 2.0d && (Double.isNaN(best) || Math.abs(c - y) < Math.abs(best - y))) {
                best = c;
            }
        }
        return Double.isNaN(best) ? y : y + ((best - y) * 0.8d);
    }

    private static final boolean updateScene$inParking(SceneView this$0, ModelSnapshot m, double x, double y) {
        int e = 0;
        while (e < 2) {
            double out = e == 0 ? -1.0d : 1.0d;
            double edge = updateScene$edgeAtX(this$0, m, e, x);
            double curb = updateScene$curbAt(m, this$0, e, x);
            if (!Double.isNaN(edge) && !Double.isNaN(curb) && (curb - edge) * out > 1.5d && (y - edge) * out > 0.3d) {
                return true;
            }
            e++;
        }
        return false;
    }

    private static final boolean updateScene$atPictureEdge(double x, double y) {
        return Math.abs(Math.atan2(y, x)) > 0.9d;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final double updateScene$vehHeading(Telemetry t, Function1<? super Double, Double> function1, boolean twoWay, SceneView this$0, ModelSnapshot m, Detector.Obj o, double x, float radarV, double y) {
        float hd = o != null ? o.getHeading() : Float.NaN;
        boolean oncoming = true;
        boolean crossing = t.getVEgo() < 8.0f && o != null && o.getMoving() && o.getSpeed() > 4.0f && !Float.isNaN(hd) && Math.abs((float) Math.sin((double) hd)) > 0.7f && !updateScene$inParking(this$0, m, x, y) && !updateScene$atPictureEdge(x, y);
        if (crossing) {
            return hd;
        }
        double road = function1.invoke(Double.valueOf(x)).doubleValue();
        if (Float.isNaN(radarV) || Math.abs(radarV) <= 2.0f) {
            if (o == null || !o.getVelValid() || o.getVx() >= -3.0f) {
                if (o == null || !o.getVelValid() || o.getVx() <= 3.0f) {
                    double le = updateScene$edgeAtX(this$0, m, 0, x);
                    if (!twoWay || Double.isNaN(le) || y >= 0.5d + le) {
                        oncoming = false;
                    }
                } else {
                    oncoming = false;
                }
            }
        } else if (radarV >= 0.0f) {
            oncoming = false;
        }
        return (oncoming ? 3.141592653589793d : 0.0d) + road;
    }

    private static final boolean updateScene$sameCar(double ox, double oy, double vx, double vy) {
        if (Math.abs(ox - vx) < (0.15d * vx) + 3.0d && Math.abs(oy - vy) < (0.06d * vx) + 1.8d) {
            return true;
        }
        if (vx < 20.0d) {
            return false;
        }
        double db = Math.abs(Math.atan2(oy, ox) - Math.atan2(vy, vx));
        return db < 0.045d && ox > 0.6d * vx && ox < 1.5d * vx;
    }

    private static final Car updateScene$slotNear(SceneView this$0, HashSet<Car> hashSet, long nowMs, long id, String kindName, double tx, double ty) {
        Iterator<Car> it;
        Iterator<Car> it2 = this$0.genericPool.iterator();
        Intrinsics.checkNotNullExpressionValue(it2, "iterator(...)");
        while (it2.hasNext()) {
            Car c = it2.next();
            Intrinsics.checkNotNullExpressionValue(c, "next(...)");
            if (c.getId() == id) {
                return null;
            }
        }
        Car best = null;
        double bd = Double.MAX_VALUE;
        Iterator<Car> it3 = this$0.genericPool.iterator();
        Intrinsics.checkNotNullExpressionValue(it3, "iterator(...)");
        while (it3.hasNext()) {
            Car next = it3.next();
            Intrinsics.checkNotNullExpressionValue(next, "next(...)");
            Car c2 = next;
            if (hashSet.contains(c2) || !c2.getInScene() || Double.isNaN(c2.getX())) {
                it = it3;
            } else if (nowMs - c2.getLastUsed() <= 900 || !Double.isNaN(c2.getWx())) {
                if (Intrinsics.areEqual(c2.getKind(), kindName)) {
                    it = it3;
                } else {
                    it = it3;
                    if (StringsKt.startsWith$default(c2.getKind(), "car_", false, 2, (Object) null)) {
                        if (!StringsKt.startsWith$default(kindName, "car_", false, 2, (Object) null)) {
                        }
                    }
                }
                double dx = Math.abs(c2.getX() - tx);
                double dy = Math.abs(c2.getY() - ty);
                if (dx <= (0.12d * tx) + 3.5d && dy <= (0.04d * tx) + 2.0d) {
                    double d = dx + dy;
                    if (d < bd) {
                        bd = d;
                        best = c2;
                        it3 = it;
                    }
                }
            } else {
                it = it3;
            }
            it3 = it;
        }
        if (best != null) {
            Car it4 = best;
            it4.setId(id);
            it4.setLastUsed(nowMs);
        }
        return best;
    }

    private static final boolean updateScene$occupied(ArrayList<double[]> arrayList, double cx, double cy, double halfLen) {
        ArrayList<double[]> $this$any$iv = arrayList;
        if (($this$any$iv instanceof Collection) && $this$any$iv.isEmpty()) {
            return false;
        }
        for (Object element$iv : $this$any$iv) {
            double[] p = (double[]) element$iv;
            if (((Math.abs(p[0] - cx) >= (p[2] + halfLen) * 0.95d || Math.abs(p[1] - cy) >= 1.7d) ? null : 1) != null) {
                return true;
            }
        }
        return false;
    }

    private static final double updateScene$halfLenOf(String kind) {
        if (Intrinsics.areEqual(kind, "truck.glb")) {
            return 7.0d;
        }
        return Intrinsics.areEqual(kind, "bus.glb") ? 6.0d : 2.3d;
    }

    public final int getShownCars() {
        return this.shownCars;
    }

    public final void setShownCars(int i) {
        this.shownCars = i;
    }

    private final double facingAngle(int face, double y) {
        switch (face) {
            case 1:
                return 3.141592653589793d;
            case 2:
                return y > 0.0d ? -1.5707963267948966d : 1.5707963267948966d;
            default:
                return 0.0d;
        }
    }

    private final Car slotFor(long id, String kindName, long nowMs) {
        Iterator<Car> it = this.genericPool.iterator();
        Intrinsics.checkNotNullExpressionValue(it, "iterator(...)");
        while (true) {
            long j = Long.MIN_VALUE;
            if (!it.hasNext()) {
                boolean vehicleKind = StringsKt.startsWith$default(kindName, "car_", false, 2, (Object) null) || Intrinsics.areEqual(kindName, "truck.glb") || Intrinsics.areEqual(kindName, "bus.glb");
                Car car = null;
                Iterator<Car> it2 = this.genericPool.iterator();
                Intrinsics.checkNotNullExpressionValue(it2, "iterator(...)");
                while (true) {
                    if (!it2.hasNext()) {
                        break;
                    }
                    Car next = it2.next();
                    Intrinsics.checkNotNullExpressionValue(next, "next(...)");
                    Car c = next;
                    if (c.getId() == Long.MIN_VALUE && Intrinsics.areEqual(c.getKind(), kindName)) {
                        car = c;
                        break;
                    }
                }
                if (car == null && vehicleKind) {
                    Iterator<Car> it3 = this.genericPool.iterator();
                    Intrinsics.checkNotNullExpressionValue(it3, "iterator(...)");
                    while (true) {
                        if (!it3.hasNext()) {
                            break;
                        }
                        Car next2 = it3.next();
                        Intrinsics.checkNotNullExpressionValue(next2, "next(...)");
                        Car c2 = next2;
                        if (c2.getId() == j) {
                            Iterator<Car> it4 = it3;
                            if (StringsKt.startsWith$default(c2.getKind(), "car_", false, 2, (Object) null)) {
                                car = c2;
                                break;
                            }
                            it3 = it4;
                        }
                        j = Long.MIN_VALUE;
                    }
                }
                if (car == null) {
                    Car oldest = null;
                    Iterator<Car> it5 = this.genericPool.iterator();
                    Intrinsics.checkNotNullExpressionValue(it5, "iterator(...)");
                    while (it5.hasNext()) {
                        Car next3 = it5.next();
                        Intrinsics.checkNotNullExpressionValue(next3, "next(...)");
                        Car c3 = next3;
                        if (!Double.isNaN(c3.getWx()) && nowMs - c3.getLastUsed() > 700) {
                            if (!Intrinsics.areEqual(c3.getKind(), kindName)) {
                                if (vehicleKind) {
                                    if (!StringsKt.startsWith$default(c3.getKind(), "car_", false, 2, (Object) null)) {
                                    }
                                }
                            }
                            if (oldest == null || c3.getLastUsed() < oldest.getLastUsed()) {
                                oldest = c3;
                            }
                        }
                    }
                    if (oldest != null) {
                        Car it6 = oldest;
                        it6.show(false);
                        car = it6;
                    }
                }
                if (car != null) {
                    Car it7 = car;
                    it7.setId(id);
                    it7.setLastUsed(nowMs);
                    it7.setX(Double.NaN);
                    it7.setWx(Double.NaN);
                    it7.setFirstShownMs(nowMs);
                }
                return car;
            }
            Car next4 = it.next();
            Intrinsics.checkNotNullExpressionValue(next4, "next(...)");
            Car c4 = next4;
            if (c4.getId() == id) {
                if (Intrinsics.areEqual(c4.getKind(), kindName)) {
                    c4.setLastUsed(nowMs);
                    return c4;
                }
                c4.show(false);
                c4.setId(Long.MIN_VALUE);
            }
        }
    }

    static /* synthetic */ void setupCamera$default(SceneView sceneView, Telemetry telemetry, int i, Object obj) {
        if ((i & 1) != 0) {
            telemetry = null;
        }
        sceneView.setupCamera(telemetry);
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x005f, code lost:
    
        if (r38.tiltTarget < 4.0d) goto L22;
     */
    /* JADX WARN: Removed duplicated region for block: B:21:0x009f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final void setupCamera(Telemetry t) {
        Camera camera;
        Lead it;
        long nowMs = System.currentTimeMillis();
        if (t != null && (it = t.getLeadOne()) != null && it.getStatus()) {
            this.leadGap = it.getDRel();
            this.leadGapMs = nowMs;
        }
        double gap = nowMs - this.leadGapMs < 3000 ? this.leadGap : 99.0d;
        double want = RangesKt.coerceIn((8.0d - gap) / 6.0d, 0.0d, 1.0d) * 16.0d;
        if (Math.abs(want - this.tiltTarget) <= 4.0d) {
            if (want == 0.0d) {
            }
            this.tiltBoost += (this.tiltTarget - this.tiltBoost) * 0.012d;
            double tilt = Math.toRadians(this.tiltDeg + this.tiltBoost);
            double d = this.distance;
            double look = 0.42d * d;
            double camX = look - (Math.cos(tilt) * d);
            double camZ = (Math.sin(tilt) * d) + 0.3d;
            camera = this.camera;
            if (camera == null) {
                Intrinsics.throwUninitializedPropertyAccessException("camera");
                camera = null;
            }
            camera.lookAt(0.0d, camZ, -camX, 0.0d, 0.0d, -look, 0.0d, 1.0d, 0.0d);
        }
        this.tiltTarget = want;
        this.tiltBoost += (this.tiltTarget - this.tiltBoost) * 0.012d;
        double tilt2 = Math.toRadians(this.tiltDeg + this.tiltBoost);
        double d2 = this.distance;
        double look2 = 0.42d * d2;
        double camX2 = look2 - (Math.cos(tilt2) * d2);
        double camZ2 = (Math.sin(tilt2) * d2) + 0.3d;
        camera = this.camera;
        if (camera == null) {
        }
        camera.lookAt(0.0d, camZ2, -camX2, 0.0d, 0.0d, -look2, 0.0d, 1.0d, 0.0d);
    }

    private final double headingAt(ModelSnapshot m, double x) {
        if (m == null) {
            return 0.0d;
        }
        float[] xs = m.getPosX();
        float[] ys = m.getPosY();
        if (xs.length < 2) {
            return 0.0d;
        }
        int i = 1;
        while (i < xs.length - 1 && xs[i] < x) {
            i++;
        }
        double dx = xs[i] - xs[i - 1];
        double dy = ys[i] - ys[i - 1];
        if (dx > 0.001d) {
            return Math.atan2(dy, dx);
        }
        return 0.0d;
    }

    private final void clearRibbons() {
        VertexBuffer vertexBuffer;
        IndexBuffer indexBuffer;
        this.bufSel = 1 - this.bufSel;
        getVbuf().clear();
        getIbuf().clear();
        this.nVerts = 0;
        this.nIdx = 0;
        for (int k = 0; k < 3; k++) {
            putVertex(0.0f, -10.0f, 0.0f, 0);
        }
        putTri(0, 1, 2);
        getVbuf().flip();
        getIbuf().flip();
        VertexBuffer vertexBuffer2 = this.ribbonVb;
        if (vertexBuffer2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ribbonVb");
            vertexBuffer2 = null;
        }
        Engine engine = this.engine;
        if (engine == null) {
            Intrinsics.throwUninitializedPropertyAccessException("engine");
            engine = null;
        }
        vertexBuffer2.setBufferAt(engine, 0, getVbuf());
        IndexBuffer indexBuffer2 = this.ribbonIb;
        if (indexBuffer2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ribbonIb");
            indexBuffer2 = null;
        }
        Engine engine2 = this.engine;
        if (engine2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("engine");
            engine2 = null;
        }
        indexBuffer2.setBuffer(engine2, getIbuf());
        Engine engine3 = this.engine;
        if (engine3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("engine");
            engine3 = null;
        }
        RenderableManager rm = engine3.getRenderableManager();
        int renderableManager = rm.getInstance(this.ribbonEntity);
        RenderableManager.PrimitiveType primitiveType = RenderableManager.PrimitiveType.TRIANGLES;
        VertexBuffer vertexBuffer3 = this.ribbonVb;
        if (vertexBuffer3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ribbonVb");
            vertexBuffer = null;
        } else {
            vertexBuffer = vertexBuffer3;
        }
        IndexBuffer indexBuffer3 = this.ribbonIb;
        if (indexBuffer3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ribbonIb");
            indexBuffer = null;
        } else {
            indexBuffer = indexBuffer3;
        }
        rm.setGeometryAt(renderableManager, 0, primitiveType, vertexBuffer, indexBuffer, 0, 3);
    }

    private final void beginRibbons() {
        this.bufSel = 1 - this.bufSel;
        this.nVerts = 0;
        this.nIdx = 0;
        getVbuf().clear();
        getIbuf().clear();
    }

    private final void endRibbons() {
        VertexBuffer vertexBuffer;
        IndexBuffer indexBuffer;
        if (this.nIdx == 0) {
            clearRibbons();
            return;
        }
        getVbuf().flip();
        getIbuf().flip();
        VertexBuffer vertexBuffer2 = this.ribbonVb;
        if (vertexBuffer2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ribbonVb");
            vertexBuffer2 = null;
        }
        Engine engine = this.engine;
        if (engine == null) {
            Intrinsics.throwUninitializedPropertyAccessException("engine");
            engine = null;
        }
        vertexBuffer2.setBufferAt(engine, 0, getVbuf());
        IndexBuffer indexBuffer2 = this.ribbonIb;
        if (indexBuffer2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ribbonIb");
            indexBuffer2 = null;
        }
        Engine engine2 = this.engine;
        if (engine2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("engine");
            engine2 = null;
        }
        indexBuffer2.setBuffer(engine2, getIbuf());
        Engine engine3 = this.engine;
        if (engine3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("engine");
            engine3 = null;
        }
        RenderableManager rm = engine3.getRenderableManager();
        int renderableManager = rm.getInstance(this.ribbonEntity);
        RenderableManager.PrimitiveType primitiveType = RenderableManager.PrimitiveType.TRIANGLES;
        VertexBuffer vertexBuffer3 = this.ribbonVb;
        if (vertexBuffer3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ribbonVb");
            vertexBuffer = null;
        } else {
            vertexBuffer = vertexBuffer3;
        }
        IndexBuffer indexBuffer3 = this.ribbonIb;
        if (indexBuffer3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ribbonIb");
            indexBuffer = null;
        } else {
            indexBuffer = indexBuffer3;
        }
        rm.setGeometryAt(renderableManager, 0, primitiveType, vertexBuffer, indexBuffer, 0, this.nIdx);
    }

    static /* synthetic */ void ribbon$default(SceneView sceneView, float[] fArr, float[] fArr2, float[] fArr3, double d, int i, int i2, int i3, float f, Function1 function1, int i4, Object obj) {
        float f2;
        Function1 function12;
        int i5 = (i4 & 32) != 0 ? 0 : i2;
        int length = (i4 & 64) != 0 ? fArr.length - 1 : i3;
        if ((i4 & 128) == 0) {
            f2 = f;
        } else {
            f2 = 0.02f;
        }
        if ((i4 & 256) == 0) {
            function12 = function1;
        } else {
            function12 = null;
        }
        sceneView.ribbon(fArr, fArr2, fArr3, d, i, i5, length, f2, function12);
    }

    private final void ribbon(float[] xs, float[] ys, float[] zs, double half, int argb, int from, int to, float lift, Function1<? super Integer, Float> alphaAt) {
        Float orNull;
        float[] fArr = xs;
        float[] fArr2 = ys;
        float[] fArr3 = zs;
        Function1<? super Integer, Float> function1 = alphaAt;
        int end = Math.min(to, Math.min(fArr.length, fArr2.length) - 1);
        if (end - from < 1) {
            return;
        }
        int n = (end - from) + 1;
        if (this.nVerts + (n * 2) <= this.maxVerts && this.nIdx + ((n - 1) * 6) <= this.maxIdx) {
            Triple<Float, Float, Float> linear = Companion.linear(argb);
            float lr = linear.component1().floatValue();
            float lg = linear.component2().floatValue();
            float lb = linear.component3().floatValue();
            int base = this.nVerts;
            int i = from;
            if (i <= end) {
                while (true) {
                    float x = fArr[i];
                    float y = fArr2[i];
                    float z = ((fArr3 == null || (orNull = ArraysKt.getOrNull(fArr3, i)) == null) ? 0.0f : orNull.floatValue()) + lift;
                    float a = RangesKt.coerceIn((Color.alpha(argb) / 255.0f) * (function1 != null ? function1.invoke(Integer.valueOf(i)).floatValue() : 1.0f), 0.0f, 1.0f);
                    int col = packColor(lr * a, lg * a, lb * a, a);
                    float lb2 = lb;
                    putVertex((float) (y - half), z, -x, col);
                    putVertex((float) (y + half), z, -x, col);
                    if (i == end) {
                        break;
                    }
                    i++;
                    fArr = xs;
                    fArr2 = ys;
                    fArr3 = zs;
                    lb = lb2;
                    function1 = alphaAt;
                }
            }
            int i2 = n - 1;
            for (int i3 = 0; i3 < i2; i3++) {
                int a2 = (i3 * 2) + base;
                int b = a2 + 1;
                int c = a2 + 2;
                int d = a2 + 3;
                putTri(a2, c, b);
                putTri(b, c, d);
            }
        }
    }

    private final void putVertex(float x, float y, float z, int col) {
        getVbuf().putFloat(x);
        getVbuf().putFloat(y);
        getVbuf().putFloat(z);
        getVbuf().putInt(col);
        this.nVerts++;
    }

    private final void putTri(int a, int b, int c) {
        getIbuf().putInt(a);
        getIbuf().putInt(b);
        getIbuf().putInt(c);
        this.nIdx += 3;
    }

    private final int packColor(float r, float g, float b, float a) {
        float f = 255;
        int ri = (int) (RangesKt.coerceIn(r, 0.0f, 1.0f) * f);
        int gi = (int) (RangesKt.coerceIn(g, 0.0f, 1.0f) * f);
        int bi = (int) (RangesKt.coerceIn(b, 0.0f, 1.0f) * f);
        int ai2 = (int) (RangesKt.coerceIn(a, 0.0f, 1.0f) * f);
        return (ai2 << 24) | (bi << 16) | (gi << 8) | ri;
    }

    private final int groundAt(double x, double yRight) {
        int g;
        if (this.snaps.isEmpty()) {
            return -1;
        }
        double c0 = Math.cos(this.odoYaw);
        double s0 = Math.sin(this.odoYaw);
        double yl = -yRight;
        double wx = (this.odoX + (c0 * x)) - (s0 * yl);
        double wy = this.odoY + (s0 * x) + (c0 * yl);
        double dx = wx - this.texX;
        double dy = wy - this.texY;
        double ct = Math.cos(this.texYaw);
        double st = Math.sin(this.texYaw);
        double tx = (ct * dx) + (st * dy);
        double ty = ((-st) * dx) + (ct * dy);
        double d = 0.5f;
        int r = (int) Math.floor((tx - this.XMIN) / d);
        double ty2 = 20.0f;
        int col = (int) Math.floor(((-ty) + ty2) / d);
        if (r < 0 || r >= this.MR || col < 0 || col >= 80 || (g = this.memGrp[(r * 80) + col] & 255) > 3) {
            return -1;
        }
        return g;
    }

    /* compiled from: SceneView.kt */
    private static final class Pt {
        private final int side;
        private final long tNs;
        private final double wx;
        private final double wy;

        public Pt(double wx, double wy, long tNs, int side) {
            this.wx = wx;
            this.wy = wy;
            this.tNs = tNs;
            this.side = side;
        }

        public final int getSide() {
            return this.side;
        }

        public final long getTNs() {
            return this.tNs;
        }

        public final double getWx() {
            return this.wx;
        }

        public final double getWy() {
            return this.wy;
        }
    }

    private final double shapeY(double x) {
        double d;
        double d2;
        if (x >= 0.0d) {
            d = this.shapeC[0] + (this.shapeC[1] * x);
            d2 = this.shapeC[2] * x;
        } else {
            d = this.shapeC[0];
            d2 = this.shapeC[1];
        }
        return d + (d2 * x);
    }

    private final void fitRoadShape(float[] xs0, int n0) {
        double[] B;
        int nPts;
        int i = n0;
        double[][] A = new double[3][];
        for (int i2 = 0; i2 < 3; i2++) {
            A[i2] = new double[3];
        }
        double[] B2 = new double[3];
        int nPts2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            double x = xs0[i3];
            if (x > 90.0d) {
                break;
            }
            double y = (this.edgeS[0][i3] + this.edgeS[1][i3]) / 2.0d;
            double w = 1.0d / ((x / 25.0d) + 1.0d);
            double[] p = {1.0d, x, x * x};
            for (int r = 0; r < 3; r++) {
                B2[r] = B2[r] + (p[r] * w * y);
                for (int c = 0; c < 3; c++) {
                    double[] dArr = A[r];
                    dArr[c] = dArr[c] + (p[r] * w * p[c]);
                }
            }
            nPts2++;
        }
        if (nPts2 < 5) {
            this.shapeOk = false;
            return;
        }
        for (int r2 = 0; r2 < 3; r2++) {
            double[] dArr2 = A[r2];
            dArr2[r2] = dArr2[r2] + ((r2 + 1) * 1.0E-6d);
        }
        double[] dArr3 = A[2];
        dArr3[2] = dArr3[2] * 1.3d;
        for (int c2 = 0; c2 < 3; c2++) {
            int piv = c2;
            for (int r3 = c2 + 1; r3 < 3; r3++) {
                if (Math.abs(A[r3][c2]) > Math.abs(A[piv][c2])) {
                    piv = r3;
                }
            }
            double[] tA = A[c2];
            A[c2] = A[piv];
            A[piv] = tA;
            double tB = B2[c2];
            B2[c2] = B2[piv];
            B2[piv] = tB;
            if (Math.abs(A[c2][c2]) < 1.0E-12d) {
                this.shapeOk = false;
                return;
            }
            for (int r4 = 0; r4 < 3; r4++) {
                if (r4 != c2) {
                    double f = A[r4][c2] / A[c2][c2];
                    for (int k = c2; k < 3; k++) {
                        double[] dArr4 = A[r4];
                        dArr4[k] = dArr4[k] - (A[c2][k] * f);
                    }
                    B2[r4] = B2[r4] - (B2[c2] * f);
                }
            }
        }
        double c0 = B2[0] / A[0][0];
        double c1 = B2[1] / A[1][1];
        double c22 = RangesKt.coerceIn(B2[2] / A[2][2], -0.033d, 0.033d);
        double k2 = this.shapeOk ? 0.3d : 1.0d;
        double[] dArr5 = this.shapeC;
        dArr5[0] = dArr5[0] + ((c0 - this.shapeC[0]) * k2);
        double[] dArr6 = this.shapeC;
        dArr6[1] = dArr6[1] + ((c1 - this.shapeC[1]) * k2);
        double[] dArr7 = this.shapeC;
        int i4 = 2;
        dArr7[2] = dArr7[2] + ((c22 - this.shapeC[2]) * k2);
        int e = 0;
        while (e < i4) {
            ArrayList d = new ArrayList();
            int i5 = 0;
            while (true) {
                if (i5 >= i) {
                    B = B2;
                    nPts = nPts2;
                    break;
                }
                double x2 = xs0[i5];
                if (x2 > 50.0d) {
                    B = B2;
                    nPts = nPts2;
                    break;
                } else {
                    d.add(Double.valueOf(this.edgeS[e][i5] - ((this.shapeC[0] + (this.shapeC[1] * x2)) + ((this.shapeC[2] * x2) * x2))));
                    i5++;
                    i = n0;
                    B2 = B2;
                    nPts2 = nPts2;
                }
            }
            if (d.isEmpty()) {
                this.shapeOk = false;
                return;
            }
            CollectionsKt.sort(d);
            Object obj = d.get(d.size() / 2);
            Intrinsics.checkNotNullExpressionValue(obj, "get(...)");
            double med = ((Number) obj).doubleValue();
            this.shapeOff[e] = this.shapeOk ? this.shapeOff[e] + ((med - this.shapeOff[e]) * 0.3d) : med;
            e++;
            i = n0;
            i4 = 2;
            B2 = B;
            nPts2 = nPts;
        }
        this.shapeOk = true;
    }

    private final Pair<Double, Double> toWorld(double x, double yRight) {
        double c = Math.cos(this.odoYaw);
        double s = Math.sin(this.odoYaw);
        double yl = -yRight;
        return new Pair<>(Double.valueOf((this.odoX + (c * x)) - (s * yl)), Double.valueOf(this.odoY + (s * x) + (c * yl)));
    }

    private final Pair<Double, Double> fromWorld(double wx, double wy) {
        double c = Math.cos(this.odoYaw);
        double s = Math.sin(this.odoYaw);
        double dx = wx - this.odoX;
        double dy = wy - this.odoY;
        return new Pair<>(Double.valueOf((c * dx) + (s * dy)), Double.valueOf(-(((-s) * dx) + (c * dy))));
    }

    private final void collectParked(long nowNs, Telemetry t) {
        Detector det = this.detector;
        if (det == null || det.getObjectsAtMs() == this.parkedAtMs) {
            return;
        }
        this.parkedAtMs = det.getObjectsAtMs();
        while (!this.parked.isEmpty() && nowNs - this.parked.first().getTNs() > 30000000000L) {
            this.parked.removeFirst();
        }
        if (t.getVEgo() < 2.0f) {
            return;
        }
        Iterator<Detector.Obj> it = det.getObjects().iterator();
        while (it.hasNext()) {
            Detector.Obj o = it.next();
            if (o.getCls() == 2 || o.getCls() == 5 || o.getCls() == 7) {
                if (o.getVelValid() && Math.abs(o.getVx()) <= 1.2f) {
                    double cx = o.getCx();
                    double cy = o.getCy();
                    if (cx >= 2.0d && cx <= 35.0d && Math.abs(cy) >= 2.3d) {
                        if (Math.abs(cy) <= 12.0d) {
                            int side = cy < 0.0d ? 0 : 1;
                            Pair<Double, Double> world = toWorld(cx, (side == 0 ? -1.15d : 1.15d) + cy);
                            double wx = world.component1().doubleValue();
                            double wy = world.component2().doubleValue();
                            Detector det2 = det;
                            Iterator<Detector.Obj> it2 = it;
                            this.parked.addLast(new Pt(wx, wy, nowNs, side));
                            if (this.parked.size() > 400) {
                                this.parked.removeFirst();
                            }
                            det = det2;
                            it = it2;
                        }
                    }
                }
            }
        }
    }

    static /* synthetic */ void cleanRoad$default(SceneView sceneView, ModelSnapshot modelSnapshot, float f, boolean z, int i, Object obj) {
        if ((i & 4) != 0) {
            z = true;
        }
        sceneView.cleanRoad(modelSnapshot, f, z);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:215:0x04c6  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final void cleanRoad(ModelSnapshot m, float vNow, boolean draw) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        float f;
        int e;
        float[] tgtG;
        int e2;
        int road;
        int n0;
        int curb;
        float[] rr;
        float[] rl;
        float[] xs;
        char c;
        char c2;
        double x;
        ArrayList offs;
        double ex;
        SceneView sceneView = this;
        int i11 = 2;
        if (m.getEdgeX().length < 2 || m.getEdgeY()[0].length < 4) {
            return;
        }
        int i12 = 1;
        if (m.getEdgeY()[1].length != m.getEdgeY()[0].length) {
            return;
        }
        float[] xs0 = m.getEdgeX()[0];
        int n02 = xs0.length;
        sceneView.lastEdgeX = xs0;
        for (int e3 = 0; e3 < 2; e3++) {
            if (sceneView.edgeS[e3].length != n02) {
                float[][] fArr = sceneView.edgeS;
                float[] fArr2 = m.getEdgeY()[e3];
                float[] copyOf = Arrays.copyOf(fArr2, fArr2.length);
                Intrinsics.checkNotNullExpressionValue(copyOf, "copyOf(...)");
                fArr[e3] = copyOf;
                sceneView.sideA[e3] = new float[n02];
                sceneView.sideG[e3] = new float[n02];
                double[][] dArr = sceneView.curbS;
                double[] dArr2 = new double[n02];
                for (int i13 = 0; i13 < n02; i13++) {
                    dArr2[i13] = sceneView.edgeS[e3][i13];
                }
                dArr[e3] = dArr2;
            }
        }
        float ke = vNow < 0.5f ? 0.0f : vNow < 3.0f ? 0.08f : 0.2f;
        for (int i14 = 0; i14 < n02; i14++) {
            for (int e4 = 0; e4 < 2; e4++) {
                float[] fArr3 = sceneView.edgeS[e4];
                fArr3[i14] = fArr3[i14] + ((m.getEdgeY()[e4][i14] - sceneView.edgeS[e4][i14]) * ke);
            }
        }
        float[] edgeStd = m.getEdgeStd();
        float f2 = edgeStd.length > 0 ? edgeStd[0] : 1.0f;
        float[] edgeStd2 = m.getEdgeStd();
        float std = Math.min(f2, 1 < edgeStd2.length ? edgeStd2[1] : 1.0f);
        sceneView.edgeOk += (RangesKt.coerceIn((1.3f - std) / 0.8f, 0.0f, 1.0f) - sceneView.edgeOk) * 0.2f;
        sceneView.fitRoadShape(xs0, n02);
        for (int e5 = 0; e5 < 2; e5++) {
            if (sceneView.edgeF[e5].length != n02) {
                sceneView.edgeF[e5] = new float[n02];
            }
            for (int i15 = 0; i15 < n02; i15++) {
                sceneView.edgeF[e5][i15] = sceneView.shapeOk ? (float) (sceneView.shapeY(xs0[i15]) + sceneView.shapeOff[e5]) : sceneView.edgeS[e5][i15];
            }
        }
        double farX = 0.0d;
        int e6 = 0;
        while (true) {
            if (e6 >= i11) {
                break;
            }
            double out = e6 != 0 ? 1.0d : -1.0d;
            ArrayList offs2 = new ArrayList();
            double lastRoad = -8.0d;
            double farX2 = farX;
            while (lastRoad <= 30.0d) {
                double farX3 = farX2;
                double x2 = lastRoad;
                int e7 = e6;
                ArrayList offs3 = offs2;
                double farX4 = cleanRoad$edgeAt(this, xs0, n02, e6, Math.max(0.0d, lastRoad));
                double lastRoad2 = -1.0d;
                double d = 0.0d;
                while (true) {
                    if (d > 9.0d) {
                        farX2 = farX3;
                        offs = offs3;
                        break;
                    }
                    double farX5 = farX3;
                    double yy = farX4 + (out * d);
                    int g = sceneView.groundAt(x2, yy);
                    if (g == 0) {
                        lastRoad2 = d;
                        ex = farX4;
                    } else if (i12 <= g && g < 4) {
                        ex = farX4;
                        double ex2 = yy + (out * 0.5d);
                        int groundAt = sceneView.groundAt(x2, ex2);
                        if (1 <= groundAt && groundAt < 4) {
                            if (lastRoad2 < 0.0d || d - lastRoad2 > 3.0d || out * yy < 2.0d) {
                                farX2 = farX5;
                                offs = offs3;
                            } else {
                                offs = offs3;
                                offs.add(Double.valueOf(d - 0.25d));
                                farX2 = Math.max(farX5, x2);
                            }
                        }
                    } else {
                        ex = farX4;
                    }
                    d += 0.5d;
                    farX3 = farX5;
                    offs3 = offs3;
                    farX4 = ex;
                    i12 = 1;
                }
                lastRoad = x2 + 1.0d;
                e6 = e7;
                offs2 = offs;
                i12 = 1;
            }
            double cx = lastRoad;
            int e8 = e6;
            ArrayList offs4 = offs2;
            Iterator it = sceneView.parked.iterator();
            double farX6 = farX2;
            while (it.hasNext()) {
                Pt p = (Pt) it.next();
                if (p.getSide() != e8) {
                    x = cx;
                } else {
                    Pair<Double, Double> fromWorld = sceneView.fromWorld(p.getWx(), p.getWy());
                    double cx2 = fromWorld.component1().doubleValue();
                    double cy = fromWorld.component2().doubleValue();
                    if (cx2 < -15.0d) {
                        x = cx;
                    } else if (cx2 > 40.0d) {
                        x = cx;
                    } else {
                        x = cx;
                        double off = (cy - cleanRoad$edgeAt(this, xs0, n02, e8, Math.max(0.0d, cx2))) * out;
                        if (-0.5d <= off && off <= 9.0d) {
                            offs4.add(Double.valueOf(Math.max(0.0d, off)));
                            farX6 = Math.max(farX6, cx2);
                            cx = x;
                        }
                    }
                }
                cx = x;
            }
            if (offs4.size() >= 4) {
                CollectionsKt.sort(offs4);
                Object obj = offs4.get(offs4.size() / 2);
                Intrinsics.checkNotNullExpressionValue(obj, "get(...)");
                double med = ((Number) obj).doubleValue();
                if (vNow >= 0.5f || !sceneView.curbFit[e8]) {
                    double diff = med - sceneView.curbOffS[e8];
                    if (!sceneView.curbFit[e8]) {
                        sceneView.curbOffS[e8] = med;
                    } else if (Math.abs(diff) > 0.4d) {
                        double[] dArr3 = sceneView.curbOffS;
                        dArr3[e8] = dArr3[e8] + RangesKt.coerceIn(diff, -0.025d, 0.025d);
                    }
                }
                sceneView.curbHold[e8] = 0;
                sceneView.curbFit[e8] = true;
                c2 = 0;
            } else {
                int[] iArr = sceneView.curbHold;
                iArr[e8] = iArr[e8] + 1;
                if (sceneView.curbHold[e8] > 200) {
                    c2 = 0;
                    if (vNow >= 0.5f) {
                        sceneView.curbFit[e8] = false;
                        double[] dArr4 = sceneView.curbOffS;
                        dArr4[e8] = dArr4[e8] * 0.995d;
                    }
                } else {
                    c2 = 0;
                }
            }
            for (int i16 = 0; i16 < n02; i16++) {
                sceneView.curbS[e8][i16] = sceneView.edgeF[e8][i16] + (sceneView.curbOffS[e8] * out);
            }
            e6 = e8 + 1;
            farX = farX6;
            i12 = 1;
            i11 = 2;
        }
        if (draw) {
            if (sceneView.curbFit[0] || sceneView.curbFit[1] || sceneView.edgeOk >= 0.05f) {
                int n = 1;
                while (n < n02 && xs0[n] <= 110.0f) {
                    n++;
                }
                final float[] xs2 = new float[n + 1];
                float[] rl2 = new float[n + 1];
                float[] rr2 = new float[n + 1];
                Pair<Float, Float> cleanRoad$lr = cleanRoad$lr(sceneView, 0);
                float l = cleanRoad$lr.component1().floatValue();
                float r = cleanRoad$lr.component2().floatValue();
                xs2[0] = -12.0f;
                if (sceneView.shapeOk) {
                    double back = sceneView.shapeY(-12.0d) - sceneView.shapeY(xs0[0]);
                    rl2[0] = ((float) back) + l;
                    rr2[0] = ((float) back) + r;
                } else {
                    rl2[0] = l;
                    rr2[0] = r;
                }
                Unit unit = Unit.INSTANCE;
                for (int i17 = 0; i17 < n; i17++) {
                    xs2[i17 + 1] = xs0[i17];
                    Pair<Float, Float> cleanRoad$lr2 = cleanRoad$lr(sceneView, i17);
                    float l2 = cleanRoad$lr2.component1().floatValue();
                    float r2 = cleanRoad$lr2.component2().floatValue();
                    rl2[i17 + 1] = l2;
                    rr2[i17 + 1] = r2;
                }
                boolean dark = sceneView.night || sceneView.alwaysDark;
                int road2 = dark ? Color.rgb(46, 51, 60) : Color.rgb(163, 169, 178);
                if (dark) {
                    i = 113;
                    i2 = 125;
                    i3 = 106;
                } else {
                    i = 247;
                    i2 = 249;
                    i3 = 246;
                }
                int curb2 = Color.rgb(i3, i, i2);
                if (dark) {
                    i4 = 23;
                    i5 = 28;
                    i6 = 20;
                } else {
                    i4 = 131;
                    i5 = 141;
                    i6 = 124;
                }
                int curbFace = Color.rgb(i6, i4, i5);
                if (dark) {
                    i7 = 75;
                    i8 = 86;
                    i9 = 69;
                } else {
                    i7 = 215;
                    i8 = 221;
                    i9 = 211;
                }
                int walk = Color.rgb(i9, i7, i8);
                int grass = dark ? Color.rgb(38, 64, 46) : Color.rgb(191, 219, 178);
                int i18 = 0;
                if (sceneView.curbFit[0]) {
                    i10 = 1;
                } else {
                    i10 = 1;
                    if (!sceneView.curbFit[1]) {
                        f = sceneView.edgeOk;
                        final float trust = f;
                        float[] xs02 = xs0;
                        Function1 fade = new Function1() { // from class: com.starpilot.starview.SceneView$$ExternalSyntheticLambda11
                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj2) {
                                float cleanRoad$lambda$49;
                                cleanRoad$lambda$49 = SceneView.cleanRoad$lambda$49(trust, xs2, ((Integer) obj2).intValue());
                                return Float.valueOf(cleanRoad$lambda$49);
                            }
                        };
                        fillBetween(xs2, rl2, rr2, Companion.withAlpha(road2, 255), 0.02f, fade);
                        e = 0;
                        while (e < 2) {
                            double out2 = e == 0 ? -1.0d : 1.0d;
                            float[] tgtA = new float[n + 1];
                            float[] tgtG2 = new float[n + 1];
                            int i19 = 0;
                            if (0 <= n) {
                                while (true) {
                                    double x3 = i19 == 0 ? -6.0d : xs2[i19];
                                    if (x3 > 38.0d) {
                                        tgtA[i19] = -1.0f;
                                        tgtG = tgtG2;
                                        e2 = e;
                                        road = road2;
                                        n0 = n02;
                                        curb = curb2;
                                        rr = rr2;
                                        rl = rl2;
                                        xs = xs2;
                                        c = 0;
                                    } else {
                                        curb = curb2;
                                        double ey = e == 0 ? rl2[i19] : rr2[i19];
                                        n0 = n02;
                                        double[] dArr5 = {0.5d, 1.2d, 1.9d, 2.6d};
                                        road = road2;
                                        int road3 = i18;
                                        rr = rr2;
                                        int walkN = 0;
                                        xs = xs2;
                                        int seen = 0;
                                        rl = rl2;
                                        int grassN = 0;
                                        while (road3 < 4) {
                                            double d2 = dArr5[road3];
                                            float[] tgtG3 = tgtG2;
                                            int e9 = e;
                                            switch (sceneView.groundAt(x3, ey + (out2 * d2))) {
                                                case -1:
                                                    Unit unit2 = Unit.INSTANCE;
                                                    break;
                                                case 0:
                                                default:
                                                    Integer.valueOf(seen);
                                                    seen++;
                                                    break;
                                                case 1:
                                                    walkN++;
                                                    Integer.valueOf(seen);
                                                    seen++;
                                                    break;
                                                case 2:
                                                    grassN++;
                                                    Integer.valueOf(seen);
                                                    seen++;
                                                    break;
                                            }
                                            road3++;
                                            e = e9;
                                            tgtG2 = tgtG3;
                                        }
                                        tgtG = tgtG2;
                                        e2 = e;
                                        if (walkN + grassN >= 2) {
                                            c = 0;
                                            tgtA[i19] = 1.0f;
                                            tgtG[i19] = grassN > walkN ? 1.0f : 0.0f;
                                        } else {
                                            c = 0;
                                            if (seen >= 2) {
                                                tgtA[i19] = 0.0f;
                                            } else {
                                                tgtA[i19] = -1.0f;
                                            }
                                        }
                                    }
                                    if (i19 != n) {
                                        i19++;
                                        curb2 = curb;
                                        rr2 = rr;
                                        rl2 = rl;
                                        n02 = n0;
                                        xs2 = xs;
                                        road2 = road;
                                        e = e2;
                                        tgtG2 = tgtG;
                                    }
                                }
                            } else {
                                tgtG = tgtG2;
                                e2 = e;
                                road = road2;
                                n0 = n02;
                                curb = curb2;
                                rr = rr2;
                                rl = rl2;
                                xs = xs2;
                                c = 0;
                            }
                            final float[] a = sceneView.sideA[e2];
                            float[] g2 = sceneView.sideG[e2];
                            int i20 = 0;
                            if (0 <= n) {
                                while (true) {
                                    int k = i20 == 0 ? i18 : i20 - 1;
                                    if (k < a.length) {
                                        float tgt = (tgtA[i20] >= 0.0f || !sceneView.curbFit[e2] || xs[i20] >= 35.0f) ? tgtA[i20] : 0.8f;
                                        if (tgt >= 0.0f) {
                                            a[k] = a[k] + ((tgt - a[k]) * 0.25f);
                                            g2[k] = g2[k] + ((tgtG[i20] - g2[k]) * 0.25f);
                                        } else {
                                            a[k] = a[k] * 0.98f;
                                        }
                                    }
                                    if (i20 != n) {
                                        i20++;
                                    }
                                }
                            }
                            final Function1 fade2 = fade;
                            Function1 alpha = new Function1() { // from class: com.starpilot.starview.SceneView$$ExternalSyntheticLambda12
                                @Override // kotlin.jvm.functions.Function1
                                public final Object invoke(Object obj2) {
                                    float cleanRoad$lambda$52;
                                    cleanRoad$lambda$52 = SceneView.cleanRoad$lambda$52(a, fade2, ((Integer) obj2).intValue());
                                    return Float.valueOf(cleanRoad$lambda$52);
                                }
                            };
                            int curb3 = curb;
                            int i21 = n + 1;
                            float[] cy2 = new float[i21];
                            int i22 = i18;
                            while (i22 < i21) {
                                cy2[i22] = (float) ((e2 == 0 ? rl[i22] : rr[i22]) + (((sceneView.SIDE_W / 2) + 0.25d) * out2));
                                i22++;
                                n = n;
                                a = a;
                            }
                            int n2 = n;
                            int e10 = e2;
                            int road4 = road;
                            int n03 = n0;
                            float[] xs03 = xs02;
                            int i23 = i10;
                            float[] rr3 = rr;
                            float[] rl3 = rl;
                            ribbon$default(this, xs, cy2, null, sceneView.SIDE_W / 2, Companion.withAlpha(((CollectionsKt.averageOfFloat(ArraysKt.take(g2, Math.min(g2.length, 12))) > 0.5d ? 1 : (CollectionsKt.averageOfFloat(ArraysKt.take(g2, Math.min(g2.length, 12))) == 0.5d ? 0 : -1)) > 0 ? i10 : i18) != 0 ? grass : walk, 255), 0, 0, 0.03f, alpha, 96, null);
                            int i24 = n2 + 1;
                            float[] top = new float[i24];
                            for (int i25 = i18; i25 < i24; i25++) {
                                top[i25] = (float) ((e10 == 0 ? rl3[i25] : rr3[i25]) + (0.13d * out2));
                            }
                            int i26 = i18;
                            float[] xs3 = xs;
                            int i27 = n2 + 1;
                            float[] face = new float[i27];
                            for (int i28 = i26; i28 < i27; i28++) {
                                face[i28] = (float) ((e10 == 0 ? rl3[i28] : rr3[i28]) + (0.02d * out2));
                            }
                            ribbon$default(this, xs3, top, null, 0.12d, Companion.withAlpha(curb3, 255), 0, 0, 0.05f, fade2, 96, null);
                            ribbon$default(this, xs3, face, null, 0.035d, Companion.withAlpha(curbFace, 255), 0, 0, 0.055f, fade2, 96, null);
                            e = e10 + 1;
                            sceneView = this;
                            i18 = i26;
                            xs2 = xs3;
                            curb2 = curb3;
                            n = n2;
                            road2 = road4;
                            n02 = n03;
                            xs02 = xs03;
                            rr2 = rr3;
                            fade = fade2;
                            i10 = i23;
                            rl2 = rl3;
                        }
                    }
                }
                f = 1.0f;
                final float trust2 = f;
                float[] xs022 = xs0;
                Function1 fade3 = new Function1() { // from class: com.starpilot.starview.SceneView$$ExternalSyntheticLambda11
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj2) {
                        float cleanRoad$lambda$49;
                        cleanRoad$lambda$49 = SceneView.cleanRoad$lambda$49(trust2, xs2, ((Integer) obj2).intValue());
                        return Float.valueOf(cleanRoad$lambda$49);
                    }
                };
                fillBetween(xs2, rl2, rr2, Companion.withAlpha(road2, 255), 0.02f, fade3);
                e = 0;
                while (e < 2) {
                }
            }
        }
    }

    private static final double cleanRoad$edgeAt(SceneView this$0, float[] xs0, int n0, int e, double x) {
        if (this$0.shapeOk) {
            return this$0.shapeY(x) + this$0.shapeOff[e];
        }
        if (x <= xs0[0]) {
            return this$0.edgeF[e][0];
        }
        for (int i = 1; i < n0; i++) {
            if (xs0[i] >= x) {
                double f = (x - xs0[i - 1]) / Math.max(0.01f, xs0[i] - xs0[i - 1]);
                return this$0.edgeF[e][i - 1] + ((this$0.edgeF[e][i] - this$0.edgeF[e][i - 1]) * f);
            }
        }
        return this$0.edgeF[e][n0 - 1];
    }

    private static final Pair<Float, Float> cleanRoad$lr(SceneView this$0, int i) {
        float l = (float) this$0.curbS[0][i];
        float r = (float) this$0.curbS[1][i];
        if (r - l < 3.0f) {
            float c = (l + r) / 2;
            l = c - 1.5f;
            r = c + 1.5f;
        }
        return new Pair<>(Float.valueOf(l), Float.valueOf(r));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final float cleanRoad$lambda$49(float $trust, float[] $xs, int i) {
        return (1.0f - RangesKt.coerceIn(($xs[i] - 85.0f) / 25.0f, 0.0f, 1.0f)) * $trust;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final float cleanRoad$lambda$52(float[] $a, Function1 $fade, int i) {
        boolean z = false;
        int k = i == 0 ? 0 : i - 1;
        int i2 = k - 1;
        float prev = i2 >= 0 && i2 < $a.length ? $a[i2] : $a[k];
        int i3 = k + 1;
        if (i3 >= 0 && i3 < $a.length) {
            z = true;
        }
        float next = z ? $a[i3] : $a[k];
        return RangesKt.coerceIn((((2 * $a[k]) + prev) + next) / 4.0f, 0.0f, 1.0f) * ((Number) $fade.invoke(Integer.valueOf(i))).floatValue();
    }

    public final double getGpsLag() {
        return this.gpsLag;
    }

    public final void setGpsLag(double d) {
        this.gpsLag = d;
    }

    private final double[] odoAt(long t) {
        double[] it = Odometry.INSTANCE.at(t);
        return new double[]{t, it[0], it[1], it[2]};
    }

    public final String getYawSrc() {
        return this.yawSrc;
    }

    public final void setYawSrc(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.yawSrc = str;
    }

    public final String getMapStatus() {
        return this.mapStatus;
    }

    public final void setMapStatus(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.mapStatus = str;
    }

    /* compiled from: SceneView.kt */
    private static final class Curbs {
        private final double lHi;
        private final double lLo;
        private final double rHi;
        private final double rLo;
        private final String txt;

        public Curbs(double lLo, double lHi, double rLo, double rHi, String txt) {
            Intrinsics.checkNotNullParameter(txt, "txt");
            this.lLo = lLo;
            this.lHi = lHi;
            this.rLo = rLo;
            this.rHi = rHi;
            this.txt = txt;
        }

        public final double getLHi() {
            return this.lHi;
        }

        public final double getLLo() {
            return this.lLo;
        }

        public final double getRHi() {
            return this.rHi;
        }

        public final double getRLo() {
            return this.rLo;
        }

        public final String getTxt() {
            return this.txt;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x004b, code lost:
    
        if (r12 == null) goto L18;
     */
    /* JADX WARN: Removed duplicated region for block: B:141:0x02a5  */
    /* JADX WARN: Removed duplicated region for block: B:164:0x0311  */
    /* JADX WARN: Removed duplicated region for block: B:170:0x036e  */
    /* JADX WARN: Removed duplicated region for block: B:208:0x03d9  */
    /* JADX WARN: Removed duplicated region for block: B:219:0x0438  */
    /* JADX WARN: Removed duplicated region for block: B:235:0x028d  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x0127  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final Curbs curbs(ModelSnapshot m) {
        List emptyList;
        double dE;
        List objs;
        String str;
        String str2;
        ArrayList pk;
        Iterator it;
        ArrayList ppl;
        double d;
        int i;
        StringBuilder sb;
        String format;
        StringBuilder append;
        String str3;
        String str4;
        List objs2;
        Iterator<Detector.Obj> it2;
        int i2 = 2;
        double[] lo = new double[2];
        boolean z = false;
        for (int i3 = 0; i3 < 2; i3++) {
            lo[i3] = Double.NaN;
        }
        double[] hi = new double[2];
        for (int i4 = 0; i4 < 2; i4++) {
            hi[i4] = Double.NaN;
        }
        boolean z2 = true;
        StringBuilder[] how = {new StringBuilder(), new StringBuilder()};
        Detector d2 = this.detector;
        if (d2 != null) {
            emptyList = (!d2.getRunning() || System.currentTimeMillis() - d2.getObjectsAtMs() >= 700) ? null : d2.getObjects();
        }
        emptyList = CollectionsKt.emptyList();
        List objs3 = emptyList;
        int e = 0;
        while (e < i2) {
            double out = e == 0 ? -1.0d : 1.0d;
            if (!(this.edgeS[e].length == 0 ? z2 : z) && this.lastEdgeX.length == this.edgeS[e].length) {
                float[] edgeStd = m.getEdgeStd();
                if (((e < 0 || e >= edgeStd.length) ? z : z2 ? edgeStd[e] : 9.0f) < 1.0f) {
                    ArrayList l = new ArrayList();
                    int length = this.lastEdgeX.length;
                    for (int i5 = 0; i5 < length; i5++) {
                        float x = this.lastEdgeX[i5];
                        if (x >= 3.0f) {
                            if (x > 15.0f) {
                                break;
                            }
                            l.add(Double.valueOf(this.edgeS[e][i5] * out));
                        }
                    }
                    if (!l.isEmpty()) {
                        CollectionsKt.sort(l);
                        Object obj = l.get(l.size() / i2);
                        Double it3 = (Double) obj;
                        Intrinsics.checkNotNull(it3);
                        double doubleValue = it3.doubleValue();
                        if (!(0.4d <= doubleValue && doubleValue <= 12.0d)) {
                            obj = null;
                        }
                        Double d3 = (Double) obj;
                        double dE2 = d3 != null ? d3.doubleValue() : Double.NaN;
                        dE = dE2;
                        boolean blocked = false;
                        String str5 = "format(...)";
                        String str6 = "%.1f";
                        if (Double.isInfinite(dE) && !Double.isNaN(dE)) {
                            objs = objs3;
                            str = "format(...)";
                            str2 = "%.1f";
                        } else {
                            Iterator<Detector.Obj> it4 = objs3.iterator();
                            while (it4.hasNext()) {
                                Detector.Obj o = it4.next();
                                boolean blocked2 = blocked;
                                if (o.getCls() == i2 || o.getCls() == 5 || o.getCls() == 7) {
                                    float cx = o.getCx();
                                    if (-5.0f <= cx && cx <= 25.0f) {
                                        objs2 = objs3;
                                        it2 = it4;
                                        double cy = o.getCy() * out;
                                        if ((1.2d <= cy && cy <= dE + 1.0d) && (!o.getVelValid() || Math.abs(o.getVx()) < 1.5f)) {
                                            blocked = true;
                                            objs3 = objs2;
                                            it4 = it2;
                                            i2 = 2;
                                        }
                                    } else {
                                        objs2 = objs3;
                                        it2 = it4;
                                    }
                                } else {
                                    objs2 = objs3;
                                    it2 = it4;
                                }
                                blocked = blocked2;
                                objs3 = objs2;
                                it4 = it2;
                                i2 = 2;
                            }
                            objs = objs3;
                            Iterator it5 = this.parked.iterator();
                            while (it5.hasNext()) {
                                Pt p = (Pt) it5.next();
                                if (p.getSide() == e) {
                                    str4 = str6;
                                    str3 = str5;
                                    Pair<Double, Double> fromWorld = fromWorld(p.getWx(), p.getWy());
                                    double cx2 = fromWorld.component1().doubleValue();
                                    double cy2 = fromWorld.component2().doubleValue();
                                    if ((-15.0d <= cx2 && cx2 <= 35.0d) && out * cy2 < dE + 1.5d) {
                                        blocked = true;
                                        str6 = str4;
                                        str5 = str3;
                                    }
                                } else {
                                    str3 = str5;
                                    str4 = str6;
                                }
                                str6 = str4;
                                str5 = str3;
                            }
                            String str7 = str5;
                            String str8 = str6;
                            curbs$lower(lo, e, dE);
                            if (!blocked) {
                                curbs$lower(lo, e, dE - 0.5d);
                                curbs$upper(hi, e, 0.6d + dE);
                                sb = how[e];
                                str2 = str8;
                                String format2 = String.format(str2, Arrays.copyOf(new Object[]{Double.valueOf(dE)}, 1));
                                str = str7;
                                Intrinsics.checkNotNullExpressionValue(format2, str);
                                append = new StringBuilder().append("edge ").append(format2);
                                format = " clear";
                            } else {
                                str2 = str8;
                                str = str7;
                                sb = how[e];
                                format = String.format(str2, Arrays.copyOf(new Object[]{Double.valueOf(dE)}, 1));
                                Intrinsics.checkNotNullExpressionValue(format, str);
                                append = new StringBuilder().append("edge ");
                            }
                            sb.append(append.append(format).toString());
                        }
                        pk = new ArrayList();
                        it = this.parked.iterator();
                        while (it.hasNext()) {
                            Pt p2 = (Pt) it.next();
                            if (p2.getSide() == e) {
                                boolean blocked3 = blocked;
                                double[] hi2 = hi;
                                double wx = p2.getWx();
                                double dE3 = dE;
                                double dE4 = p2.getWy();
                                Pair<Double, Double> fromWorld2 = fromWorld(wx, dE4);
                                double cx3 = fromWorld2.component1().doubleValue();
                                double cy3 = fromWorld2.component2().doubleValue();
                                if ((-15.0d <= cx3 && cx3 <= 35.0d) && out * cy3 > 1.0d) {
                                    pk.add(Double.valueOf(out * cy3));
                                }
                                hi = hi2;
                                blocked = blocked3;
                                dE = dE3;
                            }
                        }
                        double[] hi3 = hi;
                        double dE5 = dE;
                        if (pk.size() >= 3) {
                            CollectionsKt.sort(pk);
                            Object obj2 = pk.get(pk.size() / 2);
                            Intrinsics.checkNotNullExpressionValue(obj2, "get(...)");
                            double v = ((Number) obj2).doubleValue();
                            if (v < 12.0d) {
                                curbs$lower(lo, e, v);
                                StringBuilder sb2 = how[e];
                                String format3 = String.format(str2, Arrays.copyOf(new Object[]{Double.valueOf(v)}, 1));
                                Intrinsics.checkNotNullExpressionValue(format3, str);
                                sb2.append(" parked " + format3);
                            }
                        }
                        ppl = new ArrayList();
                        for (Detector.Obj o2 : objs) {
                            if (o2.getCls() == 0) {
                                float cx4 = o2.getCx();
                                if (0.0f <= cx4 && cx4 <= 30.0f) {
                                    if (!o2.getVelValid() || Math.abs(o2.getVy()) < 0.6f) {
                                        double d4 = o2.getCy() * out;
                                        if (d4 > 1.5d && (Double.isNaN(dE5) || d4 > dE5 - 0.3d)) {
                                            ppl.add(Double.valueOf(d4));
                                        }
                                    }
                                }
                            }
                        }
                        if (!ppl.isEmpty()) {
                            CollectionsKt.sort(ppl);
                            double v2 = ((Double) ppl.get(ppl.size() >= 2 ? 1 : 0)).doubleValue() - 0.2d;
                            curbs$upper(hi3, e, v2);
                            StringBuilder sb3 = how[e];
                            String format4 = String.format(str2, Arrays.copyOf(new Object[]{Double.valueOf(v2 + 0.2d)}, 1));
                            Intrinsics.checkNotNullExpressionValue(format4, str);
                            sb3.append(" people <" + format4);
                        }
                        d = lo[e];
                        if (Double.isInfinite(d) && !Double.isNaN(d)) {
                            double d5 = hi3[e];
                            if (((Double.isInfinite(d5) || Double.isNaN(d5)) ? false : true) && lo[e] > hi3[e]) {
                                i = 2;
                                double c = (lo[e] + hi3[e]) / 2;
                                lo[e] = c - 0.2d;
                                hi3[e] = 0.2d + c;
                                e++;
                                i2 = i;
                                hi = hi3;
                                objs3 = objs;
                                z = false;
                                z2 = true;
                            }
                        }
                        i = 2;
                        e++;
                        i2 = i;
                        hi = hi3;
                        objs3 = objs;
                        z = false;
                        z2 = true;
                    }
                }
            }
            dE = Double.NaN;
            boolean blocked4 = false;
            String str52 = "format(...)";
            String str62 = "%.1f";
            if (Double.isInfinite(dE) && !Double.isNaN(dE)) {
            }
            pk = new ArrayList();
            it = this.parked.iterator();
            while (it.hasNext()) {
            }
            double[] hi32 = hi;
            double dE52 = dE;
            if (pk.size() >= 3) {
            }
            ppl = new ArrayList();
            while (r4.hasNext()) {
            }
            if (!ppl.isEmpty()) {
            }
            d = lo[e];
            if (Double.isInfinite(d) && !Double.isNaN(d)) {
            }
            i = 2;
            e++;
            i2 = i;
            hi = hi32;
            objs3 = objs;
            z = false;
            z2 = true;
        }
        double[] hi4 = hi;
        return new Curbs(lo[0], hi4[0], lo[1], hi4[1], "curbs L " + curbs$side(how, 0) + "  R " + curbs$side(how, 1));
    }

    private static final void curbs$lower(double[] lo, int e, double v) {
        if ((Double.isInfinite(v) || Double.isNaN(v)) ? false : true) {
            lo[e] = Double.isNaN(lo[e]) ? v : Math.max(lo[e], v);
        }
    }

    private static final void curbs$upper(double[] hi, int e, double v) {
        if ((Double.isInfinite(v) || Double.isNaN(v)) ? false : true) {
            hi[e] = Double.isNaN(hi[e]) ? v : Math.min(hi[e], v);
        }
    }

    private static final String curbs$side(StringBuilder[] how, int e) {
        if (how[e].length() == 0) {
            return "?";
        }
        String sb = how[e].toString();
        Intrinsics.checkNotNullExpressionValue(sb, "toString(...)");
        return StringsKt.trim((CharSequence) sb).toString();
    }

    private final int baseLanes(OsmRoads.Way w) {
        String name = w.getName();
        if (name.length() == 0) {
            name = "#" + w.getId();
        }
        String key = name;
        int n = lanesOf(w);
        double[] m = this.baseLanesMem.get(key);
        if (m == null || n < m[0] || this.odoDist - m[1] > 1000.0d) {
            if (this.baseLanesMem.size() > 200) {
                this.baseLanesMem.clear();
            }
            this.baseLanesMem.put(key, new double[]{n, this.odoDist});
            return n;
        }
        if (n == ((int) m[0])) {
            m[1] = this.odoDist;
        }
        return (int) m[0];
    }

    private final double leftEdge(OsmRoads.Way w) {
        LaneHint hintFor = hintFor(w);
        if (hintFor != null) {
            Double valueOf = Double.valueOf(hintFor.getEdge());
            double it = valueOf.doubleValue();
            if (!((Double.isInfinite(it) || Double.isNaN(it)) ? false : true)) {
                valueOf = null;
            }
            if (valueOf != null) {
                return valueOf.doubleValue();
            }
        }
        return ((-baseLanes(w)) * this.laneW) / 2;
    }

    private final boolean laneMapped(OsmRoads.Way w) {
        return w.getHighway() && w.getOneway() != 0;
    }

    private final double lineDist(ModelSnapshot m, int i) {
        if (i < m.getLaneY().length && i < m.getLaneX().length) {
            float[] laneProb = m.getLaneProb();
            if ((i >= 0 && i < laneProb.length ? laneProb[i] : 0.0f) >= 0.3f) {
                if (!(m.getLaneX()[i].length == 0)) {
                    float[] xs = m.getLaneX()[i];
                    int j = 0;
                    while (j < xs.length - 1 && xs[j] < 10.0f) {
                        j++;
                    }
                    Double valueOf = Double.valueOf(Math.abs(m.getLaneY()[i][j]));
                    double it = valueOf.doubleValue();
                    if (!(0.5d <= it && it <= 3.0d)) {
                        valueOf = null;
                    }
                    if (valueOf != null) {
                        return valueOf.doubleValue();
                    }
                    return Double.NaN;
                }
            }
        }
        return Double.NaN;
    }

    private final double edgeDist(ModelSnapshot m, int e) {
        if (!(this.edgeS[e].length == 0) && this.lastEdgeX.length == this.edgeS[e].length) {
            float[] edgeStd = m.getEdgeStd();
            if ((e >= 0 && e < edgeStd.length ? edgeStd[e] : 9.0f) <= 1.5f) {
                double out = e == 0 ? -1.0d : 1.0d;
                ArrayList l = new ArrayList();
                int length = this.lastEdgeX.length;
                for (int i = 0; i < length; i++) {
                    float x = this.lastEdgeX[i];
                    if (x >= 3.0f) {
                        if (x > 15.0f) {
                            break;
                        }
                        l.add(Double.valueOf(this.edgeS[e][i] * out));
                    }
                }
                if (l.isEmpty()) {
                    return Double.NaN;
                }
                CollectionsKt.sort(l);
                Object obj = l.get(l.size() / 2);
                Double it = (Double) obj;
                Intrinsics.checkNotNull(it);
                double doubleValue = it.doubleValue();
                if (!(0.4d <= doubleValue && doubleValue <= 25.0d)) {
                    obj = null;
                }
                Double d = (Double) obj;
                if (d != null) {
                    return d.doubleValue();
                }
                return Double.NaN;
            }
        }
        return Double.NaN;
    }

    private final int cameraLane(ModelSnapshot m, int lanes, boolean oneway) {
        LaneMarks.Mark[] marks;
        LaneMarks laneMarks = this.laneMarks;
        LaneMarks.Mark mk = (laneMarks == null || (marks = laneMarks.getMarks()) == null) ? null : (LaneMarks.Mark) ArraysKt.getOrNull(marks, 1);
        boolean yellowLeft = mk != null && mk.getYellow() && (mk.getKind() == 3 || mk.getKind() == 2);
        double dl = lineDist(m, 1);
        double dr = lineDist(m, 2);
        if ((Double.isInfinite(dl) || Double.isNaN(dl)) ? false : true) {
            if ((Double.isInfinite(dr) || Double.isNaN(dr)) ? false : true) {
                double d = dl + dr;
                if (2.7d <= d && d <= 4.2d) {
                    this.laneW += ((dl + dr) - this.laneW) * 0.01d;
                }
            }
        }
        int est = OsmRoads.INSTANCE.laneIndex(lanes, yellowLeft, dl, oneway ? edgeDist(m, 0) : Double.NaN, dr, edgeDist(m, 1), cameraLane$dashed(this, 1), cameraLane$dashed(this, 2));
        long now = System.nanoTime();
        if (est >= 0) {
            this.laneSeenAt = now;
            if (est != this.laneVote) {
                this.laneVote = est;
                this.laneVoteSince = now;
            }
            if (now - this.laneVoteSince > (this.laneIdx < 0 ? 500000000L : 1500000000L)) {
                this.laneIdx = this.laneVote;
            }
        } else if (now - this.laneSeenAt > 5000000000L) {
            this.laneIdx = -1;
        }
        if (this.laneIdx >= 0) {
            return RangesKt.coerceAtMost(this.laneIdx, lanes - 1);
        }
        return -1;
    }

    private static final boolean cameraLane$dashed(SceneView this$0, int i) {
        LaneMarks.Mark[] marks;
        LaneMarks.Mark it;
        LaneMarks laneMarks = this$0.laneMarks;
        return (laneMarks == null || (marks = laneMarks.getMarks()) == null || (it = (LaneMarks.Mark) ArraysKt.getOrNull(marks, i)) == null || it.getKind() != 2 || it.getYellow()) ? false : true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:162:0x07b9, code lost:
    
        if (r2 == null) goto L245;
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x0030, code lost:
    
        if ((java.lang.System.nanoTime() - r0.getAtNs() < 2000000000 && r0.getConf() >= 0.5d) != false) goto L13;
     */
    /* JADX WARN: Removed duplicated region for block: B:101:0x0357  */
    /* JADX WARN: Removed duplicated region for block: B:103:0x035c  */
    /* JADX WARN: Removed duplicated region for block: B:108:0x037a  */
    /* JADX WARN: Removed duplicated region for block: B:111:0x0395  */
    /* JADX WARN: Removed duplicated region for block: B:114:0x03bb  */
    /* JADX WARN: Removed duplicated region for block: B:120:0x0458  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x0479  */
    /* JADX WARN: Removed duplicated region for block: B:126:0x04bf  */
    /* JADX WARN: Removed duplicated region for block: B:132:0x0527  */
    /* JADX WARN: Removed duplicated region for block: B:136:0x0584  */
    /* JADX WARN: Removed duplicated region for block: B:186:0x08c5  */
    /* JADX WARN: Removed duplicated region for block: B:189:0x08c8 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:540:0x0587  */
    /* JADX WARN: Removed duplicated region for block: B:542:0x0534  */
    /* JADX WARN: Removed duplicated region for block: B:544:0x0542  */
    /* JADX WARN: Removed duplicated region for block: B:545:0x04e1  */
    /* JADX WARN: Removed duplicated region for block: B:555:0x04d0  */
    /* JADX WARN: Removed duplicated region for block: B:556:0x047c  */
    /* JADX WARN: Removed duplicated region for block: B:560:0x0464  */
    /* JADX WARN: Removed duplicated region for block: B:562:0x03dc  */
    /* JADX WARN: Removed duplicated region for block: B:567:0x0410  */
    /* JADX WARN: Removed duplicated region for block: B:572:0x039e  */
    /* JADX WARN: Removed duplicated region for block: B:573:0x038c  */
    /* JADX WARN: Removed duplicated region for block: B:575:0x0366  */
    /* JADX WARN: Removed duplicated region for block: B:576:0x0359  */
    /* JADX WARN: Removed duplicated region for block: B:582:0x0269  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x0247  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x02bb  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final boolean drawMapRoads(ModelSnapshot m, float vEgo) {
        String str;
        String str2;
        String str3;
        boolean z;
        SceneView sceneView;
        int i;
        String str4;
        double w;
        Integer num;
        String str5;
        String str6;
        double yaw;
        String str7;
        String str8;
        OsmRoads.Match on;
        String str9;
        double w2;
        Object camTxt;
        double gpsSays;
        String str10;
        String str11;
        String str12;
        String str13;
        String str14;
        String str15;
        boolean oursOnTop;
        HashSet ours;
        List pieces;
        Function2 toCar;
        List drawn;
        double ta;
        String gpsTxt;
        Object camTxt2;
        OsmRoads.Way way;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        final IdentityHashMap bands;
        int walk;
        OsmRoads.Match on2;
        int curb;
        final HashSet ours2;
        double yaw2;
        HashMap bridged;
        OsmRoads.Way ourWay;
        final IdentityHashMap bands2;
        int i9;
        int i10;
        int i11;
        int i12;
        int i13;
        int i14;
        int i15;
        Object element$iv;
        Iterator<Map.Entry<Long, List<double[]>>> it;
        List drawn2;
        Function2 toCar2;
        List drawn3;
        Object element$iv2;
        HashSet ours3;
        Iterator<OsmRoads.Way> it2;
        ArrayList answer$iv;
        Iterator<OsmRoads.Way> it3;
        List pieces2;
        Function2 toCar3;
        String gpsTxt2;
        Object camTxt3;
        StringBuilder append;
        List<double[]> list;
        OsmRoads.Way way2;
        Iterable $this$filterTo$iv$iv;
        int $i$f$filterTo;
        boolean z2;
        boolean bothSeen;
        String str16;
        double d;
        boolean hw;
        double gpsSays2;
        int cam;
        LaneHint it4;
        String str17;
        int i16;
        int li;
        double lLo;
        double lHi;
        double rLo;
        double rHi;
        Pair band;
        double w3;
        Pair band2;
        Pair pair;
        double lo;
        double hi;
        double coerceIn;
        double l;
        double doubleValue;
        StringBuilder sb;
        String str18;
        String sb2;
        final SceneView sceneView2 = this;
        OsmRoads.Fix f = OsmRoads.INSTANCE.getLastFix();
        LaneHint it5 = sceneView2.laneHint;
        if (it5 != null) {
        }
        it5 = null;
        sceneView2.hintNow = it5;
        if (!sceneView2.mapAlign.getOk()) {
            str = "GPS ±";
            str2 = "%.0f";
            str3 = "format(...)";
            z = false;
            sceneView = sceneView2;
            i = 1;
        } else {
            if (f != null) {
                if (sceneView2.shiftJumps != sceneView2.mapAlign.getJumps()) {
                    sceneView2.shiftJumps = sceneView2.mapAlign.getJumps();
                    sceneView2.shiftE = 0.0d;
                    sceneView2.shiftN = 0.0d;
                    sceneView2.turnA = 0.0d;
                }
                Pair apply$default = OsmRoads.Align.apply$default(sceneView2.mapAlign, sceneView2.odoX, sceneView2.odoY, 0.0d, 0.0d, 0.0d, 28, null);
                double ge = ((Number) apply$default.component1()).doubleValue();
                double gn = ((Number) apply$default.component2()).doubleValue();
                double yaw3 = (sceneView2.mapAlign.getTh() + sceneView2.odoYaw) - sceneView2.turnA;
                OsmRoads.Match on3 = OsmRoads.INSTANCE.matchSeg(ge + sceneView2.shiftE, gn + sceneView2.shiftN, yaw3);
                Object camTxt4 = "";
                if (on3 == null) {
                    w = 0.0d;
                } else if (on3.getWay().getHighway()) {
                    w = sceneView2.lanesOf(on3.getWay()) * sceneView2.laneW;
                } else {
                    Double d2 = sceneView2.learnedW.get(Long.valueOf(on3.getWay().getId()));
                    w = d2 != null ? d2.doubleValue() : on3.getWay().getWidth();
                }
                long j = 0;
                if (on3 != null) {
                    int j2 = 0;
                    while (true) {
                        Object camTxt5 = camTxt4;
                        if (j2 >= m.getPosX().length - 1 || m.getPosX()[j2] >= 20.0f) {
                            break;
                        }
                        j2++;
                        camTxt4 = camTxt5;
                    }
                    boolean straight = !(m.getPosY().length == 0) && Math.abs(m.getPosY()[j2]) < 0.6f;
                    if (vEgo <= 2.0f || !straight) {
                        num = 0;
                        str5 = "GPS ±";
                    } else {
                        double gpsSays3 = on3.getDir() - yaw3;
                        num = 0;
                        str5 = "GPS ±";
                        double d3 = Math.IEEEremainder(gpsSays3, on3.getWay().getOneway() == 0 ? 3.141592653589793d : 6.283185307179586d);
                        double d4 = (on3.getWay().getOneway() == 0 || Math.abs(d3) <= 1.5707963267948966d) ? d3 : Math.IEEEremainder(on3.getDir() - yaw3, 3.141592653589793d);
                        if (Math.abs(d4) < Math.toRadians(25.0d)) {
                            sceneView2.turnA += (-0.05d) * d4;
                        }
                    }
                    if (vEgo > 0.5f) {
                        sceneView2.turnA = RangesKt.coerceIn(sceneView2.turnA, -0.6d, 0.6d) * 0.9995d;
                    }
                    Curbs cb = curbs(m);
                    double lHi2 = cb.getLHi();
                    if ((Double.isInfinite(lHi2) || Double.isNaN(lHi2)) ? false : true) {
                        double rHi2 = cb.getRHi();
                        if ((Double.isInfinite(rHi2) || Double.isNaN(rHi2)) ? false : true) {
                            double lLo2 = cb.getLLo();
                            if ((Double.isInfinite(lLo2) || Double.isNaN(lLo2)) ? false : true) {
                                double rLo2 = cb.getRLo();
                                if ((Double.isInfinite(rLo2) || Double.isNaN(rLo2)) ? false : true) {
                                    bothSeen = true;
                                    if (bothSeen) {
                                        str6 = "%.0f";
                                        str16 = "format(...)";
                                        double lLo3 = cb.getLLo();
                                        if ((Double.isInfinite(lLo3) || Double.isNaN(lLo3)) ? false : true) {
                                            double rLo3 = cb.getRLo();
                                            if ((Double.isInfinite(rLo3) || Double.isNaN(rLo3)) ? false : true) {
                                                d = 0.4d + cb.getLLo() + cb.getRLo();
                                            }
                                        }
                                        d = Double.NaN;
                                    } else {
                                        str6 = "%.0f";
                                        str16 = "format(...)";
                                        double d5 = 2;
                                        d = ((cb.getLLo() + cb.getLHi()) / d5) + ((cb.getRLo() + cb.getRHi()) / d5);
                                    }
                                    double wSeen = d;
                                    hw = on3.getWay().getHighway();
                                    if (!hw) {
                                        if ((Double.isInfinite(wSeen) || Double.isNaN(wSeen)) ? false : true) {
                                            if (bothSeen || wSeen > w) {
                                                w = RangesKt.coerceIn(w + ((wSeen - w) * 0.05d), on3.getWay().getWidth() - 3.0d, on3.getWay().getWidth() + 5.0d);
                                                sceneView2.learnedW.put(Long.valueOf(on3.getWay().getId()), Double.valueOf(w));
                                                if (sceneView2.learnedW.size() > 500) {
                                                    sceneView2.learnedW.clear();
                                                }
                                            }
                                            double right = on3.rightOf(yaw3);
                                            str9 = "%.1f";
                                            double gpsSays4 = right - ((sceneView2.shiftE * Math.sin(yaw3)) - (sceneView2.shiftN * Math.cos(yaw3)));
                                            sceneView2.mapCarOff = right;
                                            int nl = sceneView2.lanesOf(on3.getWay());
                                            boolean ow = on3.getWay().getOneway() != 0;
                                            if (hw) {
                                                gpsSays2 = gpsSays4;
                                            } else {
                                                gpsSays2 = gpsSays4;
                                                if (nl < 2) {
                                                    cam = -1;
                                                    int cam2 = cam;
                                                    it4 = sceneView2.hintFor(on3.getWay());
                                                    if (it4 == null) {
                                                        str17 = "  ";
                                                        i16 = RangesKt.coerceIn(it4.getLane(), 0, nl - 1);
                                                    } else {
                                                        str17 = "  ";
                                                        i16 = cam2;
                                                    }
                                                    li = i16;
                                                    OsmRoads osmRoads = OsmRoads.INSTANCE;
                                                    if (hw) {
                                                        lLo = cb.getLLo();
                                                        lHi = cb.getLHi();
                                                        rLo = cb.getRLo();
                                                        rHi = cb.getRHi();
                                                    } else {
                                                        rLo = Double.NaN;
                                                        rHi = Double.NaN;
                                                        lLo = Double.NaN;
                                                        lHi = Double.NaN;
                                                    }
                                                    band = osmRoads.laneBand(w, ow, lLo, lHi, rLo, rHi);
                                                    if (!hw || band.getSecond().doubleValue() - band.getFirst().doubleValue() >= 1.5d) {
                                                        if (li >= 0 || !sceneView2.laneMapped(on3.getWay())) {
                                                            w3 = w;
                                                            band2 = band;
                                                            if (li >= 0) {
                                                                double it6 = OsmRoads.INSTANCE.laneCenter(hw ? w3 : on3.getWay().getLanesWidth(), nl, li, ow);
                                                                pair = TuplesKt.to(Double.valueOf(it6), Double.valueOf(it6));
                                                            }
                                                        } else {
                                                            w3 = w;
                                                            double w4 = li;
                                                            double it7 = sceneView2.leftEdge(on3.getWay()) + ((w4 + 0.5d) * sceneView2.laneW);
                                                            pair = TuplesKt.to(Double.valueOf(it7), Double.valueOf(it7));
                                                        }
                                                        lo = pair.component1().doubleValue();
                                                        hi = pair.component2().doubleValue();
                                                        if (hi - lo < 1.5d) {
                                                            yaw = yaw3;
                                                            on = on3;
                                                            coerceIn = (lo + hi) / 2;
                                                        } else {
                                                            yaw = yaw3;
                                                            on = on3;
                                                            coerceIn = RangesKt.coerceIn(right, lo, hi);
                                                        }
                                                        double target = coerceIn;
                                                        double d6 = target - right;
                                                        double k = li >= 0 ? 0.25d : vEgo > 0.5f ? 0.05d : 0.02d;
                                                        sceneView2.shiftE += k * d6 * Math.sin(yaw);
                                                        sceneView2.shiftN += (-k) * d6 * Math.cos(yaw);
                                                        l = Math.hypot(sceneView2.shiftE, sceneView2.shiftN);
                                                        if (l > 12.0d) {
                                                            sceneView2.shiftE *= 12.0d / l;
                                                            sceneView2.shiftN *= 12.0d / l;
                                                        }
                                                        String str19 = sceneView2.yawSrc;
                                                        double d7 = sceneView2.gpsLag;
                                                        if (f.getMonoNs() == 0) {
                                                            Double valueOf = Double.valueOf(f.getAgeS());
                                                            double it8 = valueOf.doubleValue();
                                                            if (!((Double.isInfinite(it8) || Double.isNaN(it8)) ? false : true)) {
                                                                valueOf = null;
                                                            }
                                                            if (valueOf != null) {
                                                                doubleValue = valueOf.doubleValue();
                                                                String format = String.format(str9, Arrays.copyOf(new Object[]{Double.valueOf(d7 + doubleValue)}, 1));
                                                                str8 = str16;
                                                                Intrinsics.checkNotNullExpressionValue(format, str8);
                                                                if (!hw || nl >= 2) {
                                                                    if (li < 0) {
                                                                        sb = new StringBuilder().append(li + 1);
                                                                        str18 = " of ";
                                                                    } else {
                                                                        sb = new StringBuilder();
                                                                        str18 = "? of ";
                                                                    }
                                                                    StringBuilder append2 = new StringBuilder().append("lane ").append(sb.append(str18).append(nl).toString()).append(" (from left)  ").append(on.getWay().getKind());
                                                                    str7 = str17;
                                                                    sb2 = append2.append(str7).toString();
                                                                } else {
                                                                    sb2 = "";
                                                                    str7 = str17;
                                                                }
                                                                camTxt = "  yaw " + str19 + "  GPS delay " + format + " s  " + sb2 + (!hw ? "" : cb.getTxt());
                                                                gpsSays = gpsSays2;
                                                                w2 = w3;
                                                            }
                                                        }
                                                        doubleValue = 0.0d;
                                                        String format2 = String.format(str9, Arrays.copyOf(new Object[]{Double.valueOf(d7 + doubleValue)}, 1));
                                                        str8 = str16;
                                                        Intrinsics.checkNotNullExpressionValue(format2, str8);
                                                        if (hw) {
                                                        }
                                                        if (li < 0) {
                                                        }
                                                        StringBuilder append22 = new StringBuilder().append("lane ").append(sb.append(str18).append(nl).toString()).append(" (from left)  ").append(on.getWay().getKind());
                                                        str7 = str17;
                                                        sb2 = append22.append(str7).toString();
                                                        camTxt = "  yaw " + str19 + "  GPS delay " + format2 + " s  " + sb2 + (!hw ? "" : cb.getTxt());
                                                        gpsSays = gpsSays2;
                                                        w2 = w3;
                                                    } else {
                                                        w3 = w;
                                                        band2 = band;
                                                    }
                                                    pair = band2;
                                                    lo = pair.component1().doubleValue();
                                                    hi = pair.component2().doubleValue();
                                                    if (hi - lo < 1.5d) {
                                                    }
                                                    double target2 = coerceIn;
                                                    double d62 = target2 - right;
                                                    double k2 = li >= 0 ? 0.25d : vEgo > 0.5f ? 0.05d : 0.02d;
                                                    sceneView2.shiftE += k2 * d62 * Math.sin(yaw);
                                                    sceneView2.shiftN += (-k2) * d62 * Math.cos(yaw);
                                                    l = Math.hypot(sceneView2.shiftE, sceneView2.shiftN);
                                                    if (l > 12.0d) {
                                                    }
                                                    String str192 = sceneView2.yawSrc;
                                                    double d72 = sceneView2.gpsLag;
                                                    if (f.getMonoNs() == 0) {
                                                    }
                                                    doubleValue = 0.0d;
                                                    String format22 = String.format(str9, Arrays.copyOf(new Object[]{Double.valueOf(d72 + doubleValue)}, 1));
                                                    str8 = str16;
                                                    Intrinsics.checkNotNullExpressionValue(format22, str8);
                                                    if (hw) {
                                                    }
                                                    if (li < 0) {
                                                    }
                                                    StringBuilder append222 = new StringBuilder().append("lane ").append(sb.append(str18).append(nl).toString()).append(" (from left)  ").append(on.getWay().getKind());
                                                    str7 = str17;
                                                    sb2 = append222.append(str7).toString();
                                                    camTxt = "  yaw " + str192 + "  GPS delay " + format22 + " s  " + sb2 + (!hw ? "" : cb.getTxt());
                                                    gpsSays = gpsSays2;
                                                    w2 = w3;
                                                }
                                            }
                                            cam = sceneView2.cameraLane(m, nl, ow);
                                            int cam22 = cam;
                                            it4 = sceneView2.hintFor(on3.getWay());
                                            if (it4 == null) {
                                            }
                                            li = i16;
                                            OsmRoads osmRoads2 = OsmRoads.INSTANCE;
                                            if (hw) {
                                            }
                                            band = osmRoads2.laneBand(w, ow, lLo, lHi, rLo, rHi);
                                            if (hw) {
                                            }
                                            if (li >= 0) {
                                            }
                                            w3 = w;
                                            band2 = band;
                                            if (li >= 0) {
                                            }
                                            pair = band2;
                                            lo = pair.component1().doubleValue();
                                            hi = pair.component2().doubleValue();
                                            if (hi - lo < 1.5d) {
                                            }
                                            double target22 = coerceIn;
                                            double d622 = target22 - right;
                                            double k22 = li >= 0 ? 0.25d : vEgo > 0.5f ? 0.05d : 0.02d;
                                            sceneView2.shiftE += k22 * d622 * Math.sin(yaw);
                                            sceneView2.shiftN += (-k22) * d622 * Math.cos(yaw);
                                            l = Math.hypot(sceneView2.shiftE, sceneView2.shiftN);
                                            if (l > 12.0d) {
                                            }
                                            String str1922 = sceneView2.yawSrc;
                                            double d722 = sceneView2.gpsLag;
                                            if (f.getMonoNs() == 0) {
                                            }
                                            doubleValue = 0.0d;
                                            String format222 = String.format(str9, Arrays.copyOf(new Object[]{Double.valueOf(d722 + doubleValue)}, 1));
                                            str8 = str16;
                                            Intrinsics.checkNotNullExpressionValue(format222, str8);
                                            if (hw) {
                                            }
                                            if (li < 0) {
                                            }
                                            StringBuilder append2222 = new StringBuilder().append("lane ").append(sb.append(str18).append(nl).toString()).append(" (from left)  ").append(on.getWay().getKind());
                                            str7 = str17;
                                            sb2 = append2222.append(str7).toString();
                                            camTxt = "  yaw " + str1922 + "  GPS delay " + format222 + " s  " + sb2 + (!hw ? "" : cb.getTxt());
                                            gpsSays = gpsSays2;
                                            w2 = w3;
                                        }
                                    }
                                    double right2 = on3.rightOf(yaw3);
                                    str9 = "%.1f";
                                    double gpsSays42 = right2 - ((sceneView2.shiftE * Math.sin(yaw3)) - (sceneView2.shiftN * Math.cos(yaw3)));
                                    sceneView2.mapCarOff = right2;
                                    int nl2 = sceneView2.lanesOf(on3.getWay());
                                    if (on3.getWay().getOneway() != 0) {
                                    }
                                    if (hw) {
                                    }
                                    cam = sceneView2.cameraLane(m, nl2, ow);
                                    int cam222 = cam;
                                    it4 = sceneView2.hintFor(on3.getWay());
                                    if (it4 == null) {
                                    }
                                    li = i16;
                                    OsmRoads osmRoads22 = OsmRoads.INSTANCE;
                                    if (hw) {
                                    }
                                    band = osmRoads22.laneBand(w, ow, lLo, lHi, rLo, rHi);
                                    if (hw) {
                                    }
                                    if (li >= 0) {
                                    }
                                    w3 = w;
                                    band2 = band;
                                    if (li >= 0) {
                                    }
                                    pair = band2;
                                    lo = pair.component1().doubleValue();
                                    hi = pair.component2().doubleValue();
                                    if (hi - lo < 1.5d) {
                                    }
                                    double target222 = coerceIn;
                                    double d6222 = target222 - right2;
                                    double k222 = li >= 0 ? 0.25d : vEgo > 0.5f ? 0.05d : 0.02d;
                                    sceneView2.shiftE += k222 * d6222 * Math.sin(yaw);
                                    sceneView2.shiftN += (-k222) * d6222 * Math.cos(yaw);
                                    l = Math.hypot(sceneView2.shiftE, sceneView2.shiftN);
                                    if (l > 12.0d) {
                                    }
                                    String str19222 = sceneView2.yawSrc;
                                    double d7222 = sceneView2.gpsLag;
                                    if (f.getMonoNs() == 0) {
                                    }
                                    doubleValue = 0.0d;
                                    String format2222 = String.format(str9, Arrays.copyOf(new Object[]{Double.valueOf(d7222 + doubleValue)}, 1));
                                    str8 = str16;
                                    Intrinsics.checkNotNullExpressionValue(format2222, str8);
                                    if (hw) {
                                    }
                                    if (li < 0) {
                                    }
                                    StringBuilder append22222 = new StringBuilder().append("lane ").append(sb.append(str18).append(nl2).toString()).append(" (from left)  ").append(on.getWay().getKind());
                                    str7 = str17;
                                    sb2 = append22222.append(str7).toString();
                                    camTxt = "  yaw " + str19222 + "  GPS delay " + format2222 + " s  " + sb2 + (!hw ? "" : cb.getTxt());
                                    gpsSays = gpsSays2;
                                    w2 = w3;
                                }
                            }
                        }
                    }
                    bothSeen = false;
                    if (bothSeen) {
                    }
                    double wSeen2 = d;
                    hw = on3.getWay().getHighway();
                    if (!hw) {
                    }
                    double right22 = on3.rightOf(yaw3);
                    str9 = "%.1f";
                    double gpsSays422 = right22 - ((sceneView2.shiftE * Math.sin(yaw3)) - (sceneView2.shiftN * Math.cos(yaw3)));
                    sceneView2.mapCarOff = right22;
                    int nl22 = sceneView2.lanesOf(on3.getWay());
                    if (on3.getWay().getOneway() != 0) {
                    }
                    if (hw) {
                    }
                    cam = sceneView2.cameraLane(m, nl22, ow);
                    int cam2222 = cam;
                    it4 = sceneView2.hintFor(on3.getWay());
                    if (it4 == null) {
                    }
                    li = i16;
                    OsmRoads osmRoads222 = OsmRoads.INSTANCE;
                    if (hw) {
                    }
                    band = osmRoads222.laneBand(w, ow, lLo, lHi, rLo, rHi);
                    if (hw) {
                    }
                    if (li >= 0) {
                    }
                    w3 = w;
                    band2 = band;
                    if (li >= 0) {
                    }
                    pair = band2;
                    lo = pair.component1().doubleValue();
                    hi = pair.component2().doubleValue();
                    if (hi - lo < 1.5d) {
                    }
                    double target2222 = coerceIn;
                    double d62222 = target2222 - right22;
                    double k2222 = li >= 0 ? 0.25d : vEgo > 0.5f ? 0.05d : 0.02d;
                    sceneView2.shiftE += k2222 * d62222 * Math.sin(yaw);
                    sceneView2.shiftN += (-k2222) * d62222 * Math.cos(yaw);
                    l = Math.hypot(sceneView2.shiftE, sceneView2.shiftN);
                    if (l > 12.0d) {
                    }
                    String str192222 = sceneView2.yawSrc;
                    double d72222 = sceneView2.gpsLag;
                    if (f.getMonoNs() == 0) {
                    }
                    doubleValue = 0.0d;
                    String format22222 = String.format(str9, Arrays.copyOf(new Object[]{Double.valueOf(d72222 + doubleValue)}, 1));
                    str8 = str16;
                    Intrinsics.checkNotNullExpressionValue(format22222, str8);
                    if (hw) {
                    }
                    if (li < 0) {
                    }
                    StringBuilder append222222 = new StringBuilder().append("lane ").append(sb.append(str18).append(nl22).toString()).append(" (from left)  ").append(on.getWay().getKind());
                    str7 = str17;
                    sb2 = append222222.append(str7).toString();
                    camTxt = "  yaw " + str192222 + "  GPS delay " + format22222 + " s  " + sb2 + (!hw ? "" : cb.getTxt());
                    gpsSays = gpsSays2;
                    w2 = w3;
                } else {
                    num = 0;
                    str5 = "GPS ±";
                    str6 = "%.0f";
                    yaw = yaw3;
                    str7 = "  ";
                    str8 = "format(...)";
                    on = on3;
                    str9 = "%.1f";
                    w2 = w;
                    camTxt = "";
                    gpsSays = Double.NaN;
                }
                if (vEgo > 0.5f && sceneView2.laneIdx < 0) {
                    sceneView2.shiftE *= 0.999d;
                    sceneView2.shiftN *= 0.999d;
                }
                long rj = sceneView2.mapAlign.getRejectSince();
                if (rj != 0) {
                    str10 = str6;
                    String format3 = String.format(str10, Arrays.copyOf(new Object[]{Double.valueOf(sceneView2.mapAlign.getRejectMiss())}, 1));
                    Intrinsics.checkNotNullExpressionValue(format3, str8);
                    String format4 = String.format(str10, Arrays.copyOf(new Object[]{Double.valueOf((System.nanoTime() - rj) / 1.0E9d)}, 1));
                    Intrinsics.checkNotNullExpressionValue(format4, str8);
                    str11 = "GPS " + format3 + " m off - ignored " + format4 + " s, wheels+gyro  ";
                } else {
                    str10 = str6;
                    str11 = "";
                }
                String gpsTxt3 = str11;
                String str20 = sceneView2.mapAlign.getGuess().length() > 0 ? "parked: lined up with " + sceneView2.mapAlign.getGuess() + str7 : "";
                String format5 = String.format(str9, Arrays.copyOf(new Object[]{Float.valueOf(f.getHAcc())}, 1));
                Intrinsics.checkNotNullExpressionValue(format5, str8);
                String gpsSrc = OsmRoads.INSTANCE.getGpsSrc();
                String format6 = String.format(str10, Arrays.copyOf(new Object[]{Double.valueOf(sceneView2.mapAlign.getSpread())}, 1));
                Intrinsics.checkNotNullExpressionValue(format6, str8);
                String format7 = String.format(str9, Arrays.copyOf(new Object[]{Double.valueOf(sceneView2.mapAlign.getResid())}, 1));
                Intrinsics.checkNotNullExpressionValue(format7, str8);
                if (on != null) {
                    OsmRoads.Match it9 = on;
                    String name = it9.getWay().getName();
                    if (name.length() == 0) {
                        name = it9.getWay().getKind();
                    }
                    String str21 = it9.getWay().getOneway() != 0 ? " (one-way)" : "";
                    str12 = format7;
                    str13 = gpsSrc;
                    String format8 = String.format(str9, Arrays.copyOf(new Object[]{Double.valueOf(it9.rightOf(yaw))}, 1));
                    Intrinsics.checkNotNullExpressionValue(format8, str8);
                    String format9 = String.format(str9, Arrays.copyOf(new Object[]{Double.valueOf(gpsSays)}, 1));
                    Intrinsics.checkNotNullExpressionValue(format9, str8);
                    String format10 = String.format(str9, Arrays.copyOf(new Object[]{Double.valueOf(w2)}, 1));
                    Intrinsics.checkNotNullExpressionValue(format10, str8);
                    str14 = format6;
                    String format11 = String.format(str10, Arrays.copyOf(new Object[]{Double.valueOf(Math.toDegrees(sceneView2.turnA))}, 1));
                    Intrinsics.checkNotNullExpressionValue(format11, str8);
                    str15 = "on: " + ((Object) name) + str21 + " " + format8 + " m right of middle (GPS " + format9 + ", " + format10 + " m wide)  turn " + format11 + "°" + camTxt;
                } else {
                    str12 = format7;
                    str13 = gpsSrc;
                    str14 = format6;
                }
                str15 = "no street under the car";
                sceneView2.mapStatus = gpsTxt3 + str20 + str5 + format5 + " m " + str13 + "  track " + str14 + " m  fit " + str12 + " m  " + str15;
                final double ta2 = sceneView2.turnA;
                Function2 toCar4 = new Function2() { // from class: com.starpilot.starview.SceneView$$ExternalSyntheticLambda2
                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        Pair drawMapRoads$lambda$72;
                        drawMapRoads$lambda$72 = SceneView.drawMapRoads$lambda$72(SceneView.this, ta2, ((Double) obj).doubleValue(), ((Double) obj2).doubleValue());
                        return drawMapRoads$lambda$72;
                    }
                };
                Iterable $this$filter$iv = OsmRoads.INSTANCE.near(ge, gn, sceneView2.MAP_R + 40.0d);
                int $i$f$filter = 0;
                Collection destination$iv$iv = new ArrayList();
                for (Object element$iv$iv : $this$filter$iv) {
                    Iterable $this$filter$iv2 = $this$filter$iv;
                    int $i$f$filter2 = $i$f$filter;
                    if (!Intrinsics.areEqual(((OsmRoads.Way) element$iv$iv).getKind(), "sidewalk")) {
                        destination$iv$iv.add(element$iv$iv);
                    }
                    $this$filter$iv = $this$filter$iv2;
                    $i$f$filter = $i$f$filter2;
                }
                List nearWays = (List) destination$iv$iv;
                List $this$filter$iv3 = nearWays;
                int $i$f$filter3 = 0;
                Collection destination$iv$iv2 = new ArrayList();
                Iterable $this$filterTo$iv$iv2 = $this$filter$iv3;
                int $i$f$filterTo2 = 0;
                for (Object element$iv$iv2 : $this$filterTo$iv$iv2) {
                    OsmRoads.Way it10 = (OsmRoads.Way) element$iv$iv2;
                    Iterable $this$filter$iv4 = $this$filter$iv3;
                    int $i$f$filter4 = $i$f$filter3;
                    if (it10.getMaxE() >= ge - sceneView2.MAP_R) {
                        $this$filterTo$iv$iv = $this$filterTo$iv$iv2;
                        $i$f$filterTo = $i$f$filterTo2;
                        if (it10.getMinE() <= ge + sceneView2.MAP_R && it10.getMaxN() >= gn - sceneView2.MAP_R && it10.getMinN() <= gn + sceneView2.MAP_R) {
                            z2 = true;
                            if (!z2) {
                                destination$iv$iv2.add(element$iv$iv2);
                            }
                            $this$filterTo$iv$iv2 = $this$filterTo$iv$iv;
                            $i$f$filterTo2 = $i$f$filterTo;
                            $this$filter$iv3 = $this$filter$iv4;
                            $i$f$filter3 = $i$f$filter4;
                        }
                    } else {
                        $this$filterTo$iv$iv = $this$filterTo$iv$iv2;
                        $i$f$filterTo = $i$f$filterTo2;
                    }
                    z2 = false;
                    if (!z2) {
                    }
                    $this$filterTo$iv$iv2 = $this$filterTo$iv$iv;
                    $i$f$filterTo2 = $i$f$filterTo;
                    $this$filter$iv3 = $this$filter$iv4;
                    $i$f$filter3 = $i$f$filter4;
                }
                List drawn4 = (List) destination$iv$iv2;
                if (on != null && (way2 = on.getWay()) != null) {
                    j = way2.getId();
                }
                if (j != sceneView2.hintChainOf) {
                    sceneView2.hintChainNow(drawn4, on != null ? on.getWay() : null);
                }
                List pieces3 = OsmRoads.INSTANCE.carFrame(RoadShape.INSTANCE.rounded(drawn4, nearWays, ge, gn, sceneView2.MAP_R, sceneView2.roundCache), toCar4, sceneView2.MAP_R, 8.0d);
                OsmRoads.Way ourWay2 = on != null ? on.getWay() : null;
                HashSet ours4 = new HashSet();
                if (ourWay2 != null) {
                    for (OsmRoads.Way w5 : drawn4) {
                        if (w5.getId() != ourWay2.getId()) {
                            if (Intrinsics.areEqual(w5.getKind(), ourWay2.getKind())) {
                                if ((ourWay2.getName().length() > 0) && OsmRoads.INSTANCE.sameStreet(w5.getName(), ourWay2.getName())) {
                                }
                            }
                        }
                        ours4.add(Long.valueOf(w5.getId()));
                    }
                }
                boolean onHw = ourWay2 != null && ourWay2.getHighway();
                HashMap bridged2 = new HashMap();
                boolean oursOnTop2 = false;
                if (ourWay2 != null) {
                    ta = ta2;
                    if (System.nanoTime() - sceneView2.crossAtNs > 500000000) {
                        sceneView2.crossAtNs = System.nanoTime();
                        sceneView2.crossNow.clear();
                        List $this$filter$iv5 = drawn4;
                        Collection destination$iv$iv3 = new ArrayList();
                        for (Object element$iv$iv3 : $this$filter$iv5) {
                            boolean oursOnTop3 = oursOnTop2;
                            Iterable $this$filter$iv6 = $this$filter$iv5;
                            if (ours4.contains(Long.valueOf(((OsmRoads.Way) element$iv$iv3).getId()))) {
                                destination$iv$iv3.add(element$iv$iv3);
                            }
                            oursOnTop2 = oursOnTop3;
                            $this$filter$iv5 = $this$filter$iv6;
                        }
                        oursOnTop = oursOnTop2;
                        List<OsmRoads.Way> ourList = (List) destination$iv$iv3;
                        Iterator<OsmRoads.Way> it11 = drawn4.iterator();
                        while (it11.hasNext()) {
                            OsmRoads.Way w6 = it11.next();
                            if (!ours4.contains(Long.valueOf(w6.getId()))) {
                                ArrayList xs = new ArrayList();
                                for (OsmRoads.Way o : ourList) {
                                    List ourList2 = ourList;
                                    Map $this$getOrPut$iv = sceneView2.crossCache;
                                    if (w6.getId() < o.getId()) {
                                        gpsTxt2 = gpsTxt3;
                                        camTxt3 = camTxt;
                                        pieces2 = pieces3;
                                        toCar3 = toCar4;
                                        it3 = it11;
                                        append = new StringBuilder().append(w6.getId()).append(":").append(o.getId());
                                    } else {
                                        it3 = it11;
                                        pieces2 = pieces3;
                                        toCar3 = toCar4;
                                        gpsTxt2 = gpsTxt3;
                                        camTxt3 = camTxt;
                                        append = new StringBuilder().append(o.getId()).append(":").append(w6.getId());
                                    }
                                    String sb3 = append.toString();
                                    List<double[]> list2 = $this$getOrPut$iv.get(sb3);
                                    if (list2 == null) {
                                        list = RoadShape.INSTANCE.crossings(w6, o);
                                        $this$getOrPut$iv.put(sb3, list);
                                    } else {
                                        list = list2;
                                    }
                                    xs.addAll(list);
                                    ourList = ourList2;
                                    camTxt = camTxt3;
                                    gpsTxt3 = gpsTxt2;
                                    toCar4 = toCar3;
                                    it11 = it3;
                                    pieces3 = pieces2;
                                }
                                List ourList3 = ourList;
                                Iterator<OsmRoads.Way> it12 = it11;
                                List pieces4 = pieces3;
                                Function2 toCar5 = toCar4;
                                String gpsTxt4 = gpsTxt3;
                                Object camTxt6 = camTxt;
                                if (!xs.isEmpty()) {
                                    sceneView2.crossNow.put(Long.valueOf(w6.getId()), xs);
                                }
                                ourList = ourList3;
                                camTxt = camTxt6;
                                gpsTxt3 = gpsTxt4;
                                toCar4 = toCar5;
                                it11 = it12;
                                pieces3 = pieces4;
                            }
                        }
                        pieces = pieces3;
                        toCar = toCar4;
                        gpsTxt = gpsTxt3;
                        camTxt2 = camTxt;
                        if (sceneView2.crossNow.isEmpty()) {
                            ours = ours4;
                            drawn3 = drawn4;
                            way = null;
                        } else {
                            HashMap ends = new HashMap();
                            Iterator<OsmRoads.Way> it13 = drawn4.iterator();
                            while (it13.hasNext()) {
                                OsmRoads.Way w7 = it13.next();
                                if (!ours4.contains(Long.valueOf(w7.getId())) && w7.getE().length >= 2) {
                                    Iterator it14 = CollectionsKt.listOf((Object[]) new Integer[]{num, Integer.valueOf(w7.getE().length - 1)}).iterator();
                                    while (it14.hasNext()) {
                                        int i17 = ((Number) it14.next()).intValue();
                                        HashMap $this$getOrPut$iv2 = ends;
                                        Long valueOf2 = Long.valueOf(drawMapRoads$k(w7.getE()[i17], w7.getN()[i17]));
                                        Object value$iv = $this$getOrPut$iv2.get(valueOf2);
                                        if (value$iv == null) {
                                            ours3 = ours4;
                                            it2 = it13;
                                            answer$iv = new ArrayList(2);
                                            $this$getOrPut$iv2.put(valueOf2, answer$iv);
                                        } else {
                                            ours3 = ours4;
                                            it2 = it13;
                                            answer$iv = value$iv;
                                        }
                                        ((List) answer$iv).add(w7);
                                        ours4 = ours3;
                                        it13 = it2;
                                    }
                                }
                            }
                            ours = ours4;
                            Set<Long> keySet = sceneView2.crossNow.keySet();
                            Intrinsics.checkNotNullExpressionValue(keySet, "<get-keys>(...)");
                            List xs2 = CollectionsKt.toList(keySet);
                            int i18 = 0;
                            while (i18 < 2) {
                                int it15 = i18;
                                int i19 = 0;
                                ArrayList nextFront = new ArrayList();
                                Iterator it16 = xs2.iterator();
                                while (it16.hasNext()) {
                                    Object next = it16.next();
                                    Intrinsics.checkNotNullExpressionValue(next, "next(...)");
                                    long id = ((Number) next).longValue();
                                    Iterable $this$firstOrNull$iv = drawn4;
                                    Iterator it17 = $this$firstOrNull$iv.iterator();
                                    while (true) {
                                        if (!it17.hasNext()) {
                                            element$iv2 = null;
                                            break;
                                        }
                                        element$iv2 = it17.next();
                                        if (((OsmRoads.Way) element$iv2).getId() == id) {
                                            break;
                                        }
                                    }
                                    OsmRoads.Way w8 = (OsmRoads.Way) element$iv2;
                                    if (w8 != null) {
                                        List front = xs2;
                                        List xs3 = sceneView2.crossNow.get(Long.valueOf(id));
                                        if (xs3 == null) {
                                            xs2 = front;
                                        } else {
                                            int it18 = it15;
                                            Iterator it19 = CollectionsKt.listOf((Object[]) new Integer[]{num, Integer.valueOf(w8.getE().length - 1)}).iterator();
                                            while (it19.hasNext()) {
                                                int i20 = ((Number) it19.next()).intValue();
                                                Iterator it20 = it19;
                                                int i21 = i19;
                                                Iterator it21 = it16;
                                                List drawn5 = drawn4;
                                                List list3 = (List) ends.get(Long.valueOf(drawMapRoads$k(w8.getE()[i20], w8.getN()[i20])));
                                                if (list3 == null) {
                                                    list3 = CollectionsKt.emptyList();
                                                }
                                                Iterator it22 = list3.iterator();
                                                while (it22.hasNext()) {
                                                    OsmRoads.Way u = (OsmRoads.Way) it22.next();
                                                    if (u.getId() == id || sceneView2.crossNow.containsKey(Long.valueOf(u.getId()))) {
                                                        ends = ends;
                                                    } else {
                                                        HashMap ends2 = ends;
                                                        Iterator it23 = it22;
                                                        long id2 = id;
                                                        if (StringsKt.endsWith$default(u.getKind(), "_link", false, 2, (Object) null) != StringsKt.endsWith$default(w8.getKind(), "_link", false, 2, (Object) null)) {
                                                            it22 = it23;
                                                            ends = ends2;
                                                            id = id2;
                                                        } else {
                                                            sceneView2.crossNow.put(Long.valueOf(u.getId()), xs3);
                                                            nextFront.add(Long.valueOf(u.getId()));
                                                            it22 = it23;
                                                            ends = ends2;
                                                            id = id2;
                                                        }
                                                    }
                                                }
                                                ends = ends;
                                                i19 = i21;
                                                it19 = it20;
                                                drawn4 = drawn5;
                                                it16 = it21;
                                            }
                                            xs2 = front;
                                            it15 = it18;
                                            ends = ends;
                                        }
                                    }
                                }
                                xs2 = nextFront;
                                i18++;
                                ends = ends;
                            }
                            drawn3 = drawn4;
                            way = null;
                        }
                        if (sceneView2.crossCache.size() > 20000) {
                            sceneView2.crossCache.clear();
                        }
                        drawn = drawn3;
                        sceneView2.hintChainNow(drawn, on != null ? on.getWay() : way);
                    } else {
                        oursOnTop = false;
                        ours = ours4;
                        pieces = pieces3;
                        toCar = toCar4;
                        drawn = drawn4;
                        gpsTxt = gpsTxt3;
                        camTxt2 = camTxt;
                        way = null;
                    }
                } else {
                    oursOnTop = false;
                    ours = ours4;
                    pieces = pieces3;
                    toCar = toCar4;
                    drawn = drawn4;
                    ta = ta2;
                    gpsTxt = gpsTxt3;
                    camTxt2 = camTxt;
                    way = null;
                }
                if (ourWay2 != null) {
                    Iterator<Map.Entry<Long, List<double[]>>> it24 = sceneView2.crossNow.entrySet().iterator();
                    while (it24.hasNext()) {
                        Map.Entry<Long, List<double[]>> next2 = it24.next();
                        long id3 = next2.getKey().longValue();
                        List xs4 = next2.getValue();
                        Iterable $this$firstOrNull$iv2 = drawn;
                        Iterator it25 = $this$firstOrNull$iv2.iterator();
                        while (true) {
                            if (!it25.hasNext()) {
                                element$iv = way;
                                break;
                            }
                            element$iv = it25.next();
                            if (((OsmRoads.Way) element$iv).getId() == id3) {
                                break;
                            }
                        }
                        OsmRoads.Way w9 = (OsmRoads.Way) element$iv;
                        if (w9 == null) {
                            it = it24;
                            drawn2 = drawn;
                            toCar2 = toCar;
                        } else if (onHw) {
                            HashMap hashMap = bridged2;
                            Long valueOf3 = Long.valueOf(id3);
                            List $this$map$iv = xs4;
                            Collection destination$iv$iv4 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                            for (Object item$iv$iv : $this$map$iv) {
                                double[] it26 = (double[]) item$iv$iv;
                                Iterator<Map.Entry<Long, List<double[]>>> it27 = it24;
                                Pair<Double, Double> invoke = toCar.invoke(Double.valueOf(it26[0]), Double.valueOf(it26[1]));
                                double x = invoke.component1().doubleValue();
                                double y = invoke.component2().doubleValue();
                                destination$iv$iv4.add(new double[]{x, y});
                                drawn = drawn;
                                it24 = it27;
                                xs4 = xs4;
                                id3 = id3;
                            }
                            it = it24;
                            drawn2 = drawn;
                            toCar2 = toCar;
                            hashMap.put(valueOf3, (List) destination$iv$iv4);
                        } else {
                            it = it24;
                            drawn2 = drawn;
                            toCar2 = toCar;
                            if (w9.getHighway()) {
                                oursOnTop = true;
                                toCar = toCar2;
                                drawn = drawn2;
                                it24 = it;
                                way = null;
                            }
                        }
                        toCar = toCar2;
                        drawn = drawn2;
                        it24 = it;
                        way = null;
                    }
                }
                List drawn6 = drawn;
                Function2 toCar6 = toCar;
                boolean oursOnTop4 = oursOnTop;
                boolean dark = sceneView2.night || sceneView2.alwaysDark;
                if (dark) {
                    i2 = 51;
                    i3 = 60;
                    i4 = 46;
                } else {
                    i2 = 169;
                    i3 = 178;
                    i4 = 163;
                }
                final int road = Color.rgb(i4, i2, i3);
                final int curb2 = dark ? Color.rgb(106, 113, 125) : Color.rgb(246, 247, 249);
                if (dark) {
                    i5 = 75;
                    i6 = 86;
                    i7 = 69;
                } else {
                    i5 = 215;
                    i6 = 221;
                    i7 = 211;
                }
                final int walk2 = Color.rgb(i7, i5, i6);
                int i22 = 1;
                final Set urban = SetsKt.setOf((Object[]) new String[]{"residential", "tertiary", "secondary", "primary", "unclassified", "living_street", "tertiary_link", "secondary_link", "primary_link"});
                List pieces5 = pieces;
                final IdentityHashMap bands3 = sceneView2.bandsFor(pieces5, new Function1() { // from class: com.starpilot.starview.SceneView$$ExternalSyntheticLambda3
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        Pair drawMapRoads$lambda$82;
                        drawMapRoads$lambda$82 = SceneView.drawMapRoads$lambda$82(SceneView.this, (OsmRoads.Piece) obj);
                        return drawMapRoads$lambda$82;
                    }
                });
                drawMapRoads$pass(pieces5, bridged2, sceneView2, new Function1() { // from class: com.starpilot.starview.SceneView$$ExternalSyntheticLambda4
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        Unit drawMapRoads$lambda$83;
                        drawMapRoads$lambda$83 = SceneView.drawMapRoads$lambda$83(urban, sceneView2, walk2, bands3, (OsmRoads.Piece) obj);
                        return drawMapRoads$lambda$83;
                    }
                });
                drawMapRoads$pass(pieces5, bridged2, sceneView2, new Function1() { // from class: com.starpilot.starview.SceneView$$ExternalSyntheticLambda5
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        Unit drawMapRoads$lambda$84;
                        drawMapRoads$lambda$84 = SceneView.drawMapRoads$lambda$84(SceneView.this, curb2, bands3, (OsmRoads.Piece) obj);
                        return drawMapRoads$lambda$84;
                    }
                });
                drawMapRoads$pass(pieces5, bridged2, sceneView2, new Function1() { // from class: com.starpilot.starview.SceneView$$ExternalSyntheticLambda6
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        Unit drawMapRoads$lambda$85;
                        drawMapRoads$lambda$85 = SceneView.drawMapRoads$lambda$85(SceneView.this, road, bands3, (OsmRoads.Piece) obj);
                        return drawMapRoads$lambda$85;
                    }
                });
                sceneView2.mapLanes = false;
                if (sceneView2.laneLinesOn) {
                    final int white = dark ? Color.rgb(214, 218, 224) : -1;
                    final int yellow = dark ? Color.rgb(217, 174, 43) : Color.rgb(242, 194, 48);
                    yaw2 = yaw;
                    final boolean engaged = sceneView2.pathStateNow == LaneTracks.PathState.ENGAGED;
                    i8 = 0;
                    curb = curb2;
                    bridged = bridged2;
                    bands = bands3;
                    walk = walk2;
                    ours2 = ours;
                    final OsmRoads.Match match = on;
                    on2 = on;
                    ourWay = ourWay2;
                    drawMapRoads$pass(pieces5, bridged, sceneView2, new Function1() { // from class: com.starpilot.starview.SceneView$$ExternalSyntheticLambda7
                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj) {
                            Unit drawMapRoads$lambda$88;
                            drawMapRoads$lambda$88 = SceneView.drawMapRoads$lambda$88(SceneView.this, match, engaged, yellow, white, bands, (OsmRoads.Piece) obj);
                            return drawMapRoads$lambda$88;
                        }
                    });
                } else {
                    i8 = 0;
                    bands = bands3;
                    walk = walk2;
                    on2 = on;
                    curb = curb2;
                    ours2 = ours;
                    yaw2 = yaw;
                    bridged = bridged2;
                    ourWay = ourWay2;
                }
                if (oursOnTop4) {
                    bands2 = bands;
                    drawMapRoads$pass(pieces5, bridged, sceneView2, new Function1() { // from class: com.starpilot.starview.SceneView$$ExternalSyntheticLambda8
                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj) {
                            Unit drawMapRoads$lambda$89;
                            drawMapRoads$lambda$89 = SceneView.drawMapRoads$lambda$89(ours2, sceneView2, road, bands2, (OsmRoads.Piece) obj);
                            return drawMapRoads$lambda$89;
                        }
                    });
                } else {
                    bands2 = bands;
                }
                if (bridged.isEmpty()) {
                    return true;
                }
                double ourHalf = ourWay != null ? ((onHw ? sceneView2.lanesOf(ourWay) * sceneView2.laneW : ourWay.getWidth()) / 2.0d) + 3.0d : 10.0d;
                if (dark) {
                    i9 = 64;
                    i10 = 74;
                    i11 = 58;
                } else {
                    i9 = 189;
                    i10 = 197;
                    i11 = 184;
                }
                int deck = Color.rgb(i11, i9, i10);
                if (dark) {
                    i12 = 34;
                    i13 = 41;
                    i14 = 30;
                } else {
                    i12 = 117;
                    i13 = 128;
                    i14 = 110;
                }
                int side = Color.rgb(i14, i12, i13);
                int edge = dark ? Color.rgb(112, 119, 131) : Color.rgb(244, 245, 247);
                for (OsmRoads.Piece p : pieces5) {
                    List xs5 = (List) bridged.get(Long.valueOf(p.getWay().getId()));
                    if (xs5 != null) {
                        sceneView2.load(p);
                        if (sceneView2.curN >= 2) {
                            double[] h = RoadShape.INSTANCE.heights(sceneView2.mx, sceneView2.my, sceneView2.curN, xs5, ourHalf + 2.0d, 80.0d, sceneView2.BRIDGE_H);
                            int length = h.length;
                            int i23 = i8;
                            while (true) {
                                if (i23 >= length) {
                                    i15 = i22;
                                    break;
                                }
                                double element$iv3 = h[i23];
                                if ((element$iv3 > 0.3d ? i22 : i8) != 0) {
                                    i15 = i8;
                                    break;
                                }
                                i23++;
                            }
                            if (i15 != 0) {
                                polyRibbon$default(this, sceneView2.curN, drawMapRoads$mid(sceneView2, p), drawMapRoads$half(sceneView2, p), road, 0.03f, sceneView2.MAP_R, null, null, null, null, 960, null);
                                sceneView2 = this;
                                bands2 = bands2;
                                pieces5 = pieces5;
                                toCar6 = toCar6;
                                urban = urban;
                                ours2 = ours2;
                                ourWay = ourWay;
                                bridged = bridged;
                                road = road;
                                i22 = 1;
                            } else {
                                IdentityHashMap bands4 = bands2;
                                Function2 toCar7 = toCar6;
                                List pieces6 = pieces5;
                                Set urban2 = urban;
                                HashSet ours5 = ours2;
                                OsmRoads.Way ourWay3 = ourWay;
                                HashMap bridged3 = bridged;
                                int road2 = road;
                                double ta3 = ta;
                                Object camTxt7 = camTxt2;
                                String gpsTxt5 = gpsTxt;
                                int i24 = i8;
                                double yaw4 = yaw2;
                                int curb3 = curb;
                                int walk3 = walk;
                                OsmRoads.Match on4 = on2;
                                List drawn7 = drawn6;
                                int i25 = this.curN;
                                double[] near = new double[i25];
                                for (int i26 = i24; i26 < i25; i26++) {
                                    near[i26] = h[i26] > 1.0d ? RangesKt.coerceIn((this.mx[i26] - 4.0d) / 26.0d, 0.25d, 1.0d) : 1.0d;
                                }
                                int i27 = this.curN;
                                double[] shade = new double[i27];
                                for (int i28 = i24; i28 < i27; i28++) {
                                    shade[i28] = (h[i28] / this.BRIDGE_H) * 0.28d;
                                }
                                polyRibbon$default(this, this.curN, drawMapRoads$mid(this, p), drawMapRoads$half(this, p) + 1.2d, -16777216, 0.05f, this.MAP_R, null, shade, null, null, 768, null);
                                bridgeSides(this.curN, drawMapRoads$mid(this, p), drawMapRoads$half(this, p) + 0.45d, h, side, this.MAP_R, near);
                                polyRibbon$default(this, this.curN, drawMapRoads$mid(this, p), drawMapRoads$half(this, p) + 0.45d, edge, 0.06f, this.MAP_R, h, near, null, null, 768, null);
                                polyRibbon$default(this, this.curN, drawMapRoads$mid(this, p), drawMapRoads$half(this, p), deck, 0.07f, this.MAP_R, h, near, null, null, 768, null);
                                sceneView2 = this;
                                bands2 = bands4;
                                pieces5 = pieces6;
                                toCar6 = toCar7;
                                drawn6 = drawn7;
                                on2 = on4;
                                ta = ta3;
                                urban = urban2;
                                ours2 = ours5;
                                yaw2 = yaw4;
                                walk = walk3;
                                ourWay = ourWay3;
                                gpsTxt = gpsTxt5;
                                bridged = bridged3;
                                curb = curb3;
                                i8 = i24;
                                camTxt2 = camTxt7;
                                road = road2;
                                i22 = 1;
                            }
                        } else {
                            i22 = 1;
                        }
                    }
                }
                return true;
            }
            str = "GPS ±";
            str2 = "%.0f";
            str3 = "format(...)";
            z = false;
            sceneView = sceneView2;
            i = 1;
        }
        if (f == null) {
            str4 = "waiting for GPS";
        } else {
            String format12 = String.format(str2, Arrays.copyOf(new Object[]{Float.valueOf(f.getHAcc())}, i));
            Intrinsics.checkNotNullExpressionValue(format12, str3);
            str4 = str + format12 + " m, waiting to line up";
        }
        sceneView.mapStatus = str4;
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Pair drawMapRoads$lambda$72(SceneView this$0, double $ta, double e, double n) {
        Pair<Double, Double> inverse = this$0.mapAlign.inverse(e - this$0.shiftE, n - this$0.shiftN);
        double ox = inverse.component1().doubleValue();
        double oy = inverse.component2().doubleValue();
        Pair<Double, Double> fromWorld = this$0.fromWorld(ox, oy);
        double x = fromWorld.component1().doubleValue();
        double y = fromWorld.component2().doubleValue();
        return OsmRoads.INSTANCE.turn(x, y, $ta);
    }

    private static final long drawMapRoads$k(double e, double n) {
        return (Math.round(e * 10.0d) * 4000003) + Math.round(10.0d * n);
    }

    private static final double drawMapRoads$half(SceneView this$0, OsmRoads.Piece p) {
        double width;
        if (p.getWay().getHighway()) {
            width = this$0.lanesOf(p.getWay()) * this$0.laneW;
        } else {
            Double d = this$0.learnedW.get(Long.valueOf(p.getWay().getId()));
            if (d == null) {
                d = this$0.hintChain.contains(Long.valueOf(p.getWay().getId())) ? this$0.learnedW.get(Long.valueOf(this$0.hintChainOf)) : null;
                if (d == null) {
                    width = p.getWay().getWidth();
                }
            }
            width = d.doubleValue();
        }
        return width / 2.0d;
    }

    private static final void drawMapRoads$pass(List<OsmRoads.Piece> list, HashMap<Long, List<double[]>> hashMap, SceneView this$0, Function1<? super OsmRoads.Piece, Unit> function1) {
        for (OsmRoads.Piece p : list) {
            if (!hashMap.containsKey(Long.valueOf(p.getWay().getId()))) {
                this$0.load(p);
                if (this$0.curN >= 2) {
                    function1.invoke(p);
                }
            }
        }
    }

    private static final double drawMapRoads$dir(OsmRoads.Piece p) {
        return p.getWay().getOneway() == -1 ? -1.0d : 1.0d;
    }

    private static final double drawMapRoads$mid(SceneView this$0, OsmRoads.Piece p) {
        if (this$0.laneMapped(p.getWay())) {
            return drawMapRoads$dir(p) * (this$0.leftEdge(p.getWay()) + drawMapRoads$half(this$0, p));
        }
        return 0.0d;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Pair drawMapRoads$lambda$82(SceneView this$0, OsmRoads.Piece p) {
        Intrinsics.checkNotNullParameter(p, "p");
        return TuplesKt.to(Double.valueOf(drawMapRoads$mid(this$0, p)), Double.valueOf(drawMapRoads$half(this$0, p)));
    }

    private static final double[] drawMapRoads$cOf(IdentityHashMap<OsmRoads.Piece, Pair<double[], double[]>> identityHashMap, OsmRoads.Piece p) {
        Pair<double[], double[]> pair = identityHashMap.get(p);
        if (pair != null) {
            return pair.getFirst();
        }
        return null;
    }

    private static final double[] drawMapRoads$hOf(IdentityHashMap<OsmRoads.Piece, Pair<double[], double[]>> identityHashMap, OsmRoads.Piece p) {
        Pair<double[], double[]> pair = identityHashMap.get(p);
        if (pair != null) {
            return pair.getSecond();
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit drawMapRoads$lambda$83(Set $urban, SceneView this$0, int $walk, IdentityHashMap $bands, OsmRoads.Piece p) {
        Intrinsics.checkNotNullParameter(p, "p");
        if ($urban.contains(p.getWay().getKind())) {
            polyRibbon$default(this$0, this$0.curN, 0.0d, this$0.SIDE_W + 0.25d, $walk, 0.02f, this$0.MAP_R, null, null, new double[this$0.curN], drawMapRoads$hOf($bands, p), 192, null);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit drawMapRoads$lambda$84(SceneView this$0, int $curb, IdentityHashMap $bands, OsmRoads.Piece p) {
        Intrinsics.checkNotNullParameter(p, "p");
        polyRibbon$default(this$0, this$0.curN, 0.0d, 0.25d, $curb, 0.025f, this$0.MAP_R, null, null, drawMapRoads$cOf($bands, p), drawMapRoads$hOf($bands, p), 192, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit drawMapRoads$lambda$85(SceneView this$0, int $road, IdentityHashMap $bands, OsmRoads.Piece p) {
        Intrinsics.checkNotNullParameter(p, "p");
        polyRibbon$default(this$0, this$0.curN, 0.0d, 0.0d, $road, 0.03f, this$0.MAP_R, null, null, drawMapRoads$cOf($bands, p), drawMapRoads$hOf($bands, p), 192, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0109  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x01e5  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x01de  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Unit drawMapRoads$lambda$88(SceneView this$0, OsmRoads.Match $on, boolean $engaged, int $yellow, int $white, IdentityHashMap $bands, OsmRoads.Piece p) {
        int i;
        int col;
        int nearK;
        double[] per;
        int baseCol;
        int nearK2;
        int col2;
        double d;
        double d2;
        Object minElem$iv;
        Intrinsics.checkNotNullParameter(p, "p");
        if (!this$0.laneMapped(p.getWay())) {
            return Unit.INSTANCE;
        }
        double d3 = drawMapRoads$dir(p);
        double l = this$0.leftEdge(p.getWay());
        int n = this$0.lanesOf(p.getWay());
        boolean ours = $on != null && p.getWay().getId() == $on.getWay().getId();
        int i2 = n + 1;
        double[] offs = new double[i2];
        int i3 = 0;
        while (i3 < i2) {
            offs[i3] = i3 == 0 ? l + 0.3d : i3 == n ? (l + (n * this$0.laneW)) - 0.3d : l + (i3 * this$0.laneW);
            i3++;
        }
        if (ours && $engaged) {
            Iterable $this$minByOrNull$iv = ArraysKt.getIndices(offs);
            Iterator iterator$iv = $this$minByOrNull$iv.iterator();
            if (iterator$iv.hasNext()) {
                minElem$iv = iterator$iv.next();
                if (iterator$iv.hasNext()) {
                    int it = ((Number) minElem$iv).intValue();
                    double minValue$iv = Math.abs(offs[it] - this$0.mapCarOff);
                    do {
                        Object e$iv = iterator$iv.next();
                        int it2 = ((Number) e$iv).intValue();
                        double v$iv = Math.abs(offs[it2] - this$0.mapCarOff);
                        if (Double.compare(minValue$iv, v$iv) > 0) {
                            minElem$iv = e$iv;
                            minValue$iv = v$iv;
                        }
                    } while (iterator$iv.hasNext());
                }
            } else {
                minElem$iv = null;
            }
            Integer num = (Integer) minElem$iv;
            if (num != null) {
                int it3 = num.intValue();
                int it4 = Math.abs(offs[it3] - this$0.mapCarOff) < this$0.laneW ? 1 : 0;
                if (it4 == 0) {
                    num = null;
                }
                if (num != null) {
                    i = num.intValue();
                    int nearK3 = i;
                    double[] bc = drawMapRoads$cOf($bands, p);
                    double[] bh = drawMapRoads$hOf($bands, p);
                    if (0 > n) {
                        int k = 0;
                        while (true) {
                            int baseCol2 = k == 0 ? $yellow : $white;
                            int col3 = k == nearK3 ? LaneTracks.Companion.effortColor(baseCol2, this$0.tracks.getEffort()) : baseCol2;
                            double hw = ((k == 0 || k == n) ? 0.08d : 0.07d) + ((k != nearK3 || this$0.tracks.getEffort() <= 0.55f) ? 0.0d : 0.03d);
                            if (bc == null || bh == null) {
                                col = col3;
                                nearK = nearK3;
                                per = null;
                            } else {
                                int i4 = this$0.curN;
                                double[] dArr = new double[i4];
                                int i5 = 0;
                                while (i5 < i4) {
                                    double left = bc[i5] - (bh[i5] * d3);
                                    if (k == 0) {
                                        d2 = left + (d3 * 0.3d);
                                    } else if (k == n) {
                                        d2 = (bc[i5] + (bh[i5] * d3)) - (d3 * 0.3d);
                                    } else {
                                        baseCol = baseCol2;
                                        nearK2 = nearK3;
                                        col2 = col3;
                                        d = left + (k * d3 * this$0.laneW);
                                        dArr[i5] = d;
                                        i5++;
                                        nearK3 = nearK2;
                                        baseCol2 = baseCol;
                                        col3 = col2;
                                    }
                                    col2 = col3;
                                    d = d2;
                                    baseCol = baseCol2;
                                    nearK2 = nearK3;
                                    dArr[i5] = d;
                                    i5++;
                                    nearK3 = nearK2;
                                    baseCol2 = baseCol;
                                    col3 = col2;
                                }
                                col = col3;
                                nearK = nearK3;
                                per = dArr;
                            }
                            int k2 = k;
                            int nearK4 = nearK;
                            int n2 = n;
                            double d4 = d3;
                            int k3 = col;
                            this$0.polyLine(this$0.curN, offs[k] * d3, hw, k3, 0.04f, this$0.MAP_R, (k == 0 || k == n) ? false : true, per);
                            if (k2 == n2) {
                                break;
                            }
                            k = k2 + 1;
                            n = n2;
                            nearK3 = nearK4;
                            d3 = d4;
                        }
                    }
                    if (ours) {
                        this$0.mapLanes = true;
                    }
                    return Unit.INSTANCE;
                }
            }
        }
        i = -1;
        int nearK32 = i;
        double[] bc2 = drawMapRoads$cOf($bands, p);
        double[] bh2 = drawMapRoads$hOf($bands, p);
        if (0 > n) {
        }
        if (ours) {
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit drawMapRoads$lambda$89(HashSet $ours, SceneView this$0, int $road, IdentityHashMap $bands, OsmRoads.Piece p) {
        Intrinsics.checkNotNullParameter(p, "p");
        if ($ours.contains(Long.valueOf(p.getWay().getId()))) {
            polyRibbon$default(this$0, this$0.curN, 0.0d, 0.0d, $road, 0.045f, this$0.MAP_R, null, null, drawMapRoads$cOf($bands, p), drawMapRoads$hOf($bands, p), 192, null);
        }
        return Unit.INSTANCE;
    }

    private final void hintChainNow(List<OsmRoads.Way> drawn, OsmRoads.Way startWay) {
        OsmRoads.Way way;
        Object element$iv;
        Pair<OsmRoads.Way, Integer> continuation;
        Object element$iv2;
        if (startWay != null) {
            List<OsmRoads.Way> $this$firstOrNull$iv = drawn;
            Iterator it = $this$firstOrNull$iv.iterator();
            while (true) {
                if (!it.hasNext()) {
                    element$iv2 = null;
                    break;
                }
                element$iv2 = it.next();
                OsmRoads.Way it2 = (OsmRoads.Way) element$iv2;
                OsmRoads.Way it3 = it2.getId() == startWay.getId() ? 1 : null;
                if (it3 != null) {
                    break;
                }
            }
            way = (OsmRoads.Way) element$iv2;
        } else {
            way = null;
        }
        OsmRoads.Way start = way;
        if (start == null) {
            this.hintChain = SetsKt.emptySet();
            this.hintSame = SetsKt.emptySet();
            this.hintChainOf = 0L;
            return;
        }
        HashSet chain = new HashSet();
        HashSet same = new HashSet();
        for (int end = 0; end < 2; end++) {
            OsmRoads.Way w = start;
            int e = end;
            for (int step = 0; step < 6 && (continuation = continuation(w, e, drawn)) != null; step++) {
                OsmRoads.Way u = continuation.component1();
                int ue = continuation.component2().intValue();
                if (u.getId() != start.getId() && chain.add(Long.valueOf(u.getId()))) {
                    w = u;
                    e = 1 - ue;
                }
            }
        }
        Iterator it4 = chain.iterator();
        Intrinsics.checkNotNullExpressionValue(it4, "iterator(...)");
        while (it4.hasNext()) {
            Object next = it4.next();
            Intrinsics.checkNotNullExpressionValue(next, "next(...)");
            long id = ((Number) next).longValue();
            List<OsmRoads.Way> $this$firstOrNull$iv2 = drawn;
            Iterator it5 = $this$firstOrNull$iv2.iterator();
            while (true) {
                if (!it5.hasNext()) {
                    element$iv = null;
                    break;
                }
                element$iv = it5.next();
                OsmRoads.Way it6 = (OsmRoads.Way) element$iv;
                OsmRoads.Way it7 = it6.getId() == id ? 1 : null;
                if (it7 != null) {
                    break;
                }
            }
            OsmRoads.Way u2 = (OsmRoads.Way) element$iv;
            if (u2 != null) {
                if (u2.getLaneCount() == start.getLaneCount()) {
                    same.add(Long.valueOf(id));
                } else {
                    Pair<OsmRoads.Way, Integer> continuation2 = continuation(u2, 0, drawn);
                    OsmRoads.Way a = continuation2 != null ? continuation2.getFirst() : null;
                    Pair<OsmRoads.Way, Integer> continuation3 = continuation(u2, 1, drawn);
                    OsmRoads.Way b = continuation3 != null ? continuation3.getFirst() : null;
                    if (a != null && b != null && a.getLaneCount() == start.getLaneCount() && b.getLaneCount() == start.getLaneCount()) {
                        same.add(Long.valueOf(id));
                    }
                }
            }
        }
        this.hintChain = chain;
        this.hintSame = same;
        this.hintChainOf = start.getId();
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x00e4 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:59:0x01aa A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final Pair<OsmRoads.Way, Integer> continuation(OsmRoads.Way w, int end, List<OsmRoads.Way> drawn) {
        int i;
        int j;
        double x;
        double y;
        double ol;
        boolean sameStreet;
        int i2;
        int a;
        int j2;
        double x2;
        double y2;
        double ol2;
        int ue = 2;
        if (w.getE().length < 2) {
            return null;
        }
        int i3 = end == 0 ? 0 : w.getE().length - 1;
        int j3 = end == 0 ? 1 : w.getE().length - 2;
        double x3 = w.getE()[i3];
        double y3 = w.getN()[i3];
        double ox = x3 - w.getE()[j3];
        double oy = y3 - w.getN()[j3];
        double ol3 = RangesKt.coerceAtLeast(Math.hypot(ox, oy), 1.0E-6d);
        double ox2 = ox / ol3;
        double oy2 = oy / ol3;
        Pair best = null;
        double bestCos = Math.cos(Math.toRadians(35.0d));
        for (OsmRoads.Way u : drawn) {
            if (u.getId() != w.getId() && u.getE().length >= ue) {
                if (laneMapped(u) == laneMapped(w)) {
                    if (u.getHighway() != w.getHighway()) {
                        i = ue;
                        j = j3;
                        x = x3;
                        y = y3;
                        ol = ol3;
                    } else {
                        if (!(w.getName().length() > 0)) {
                            if (!(u.getName().length() > 0)) {
                                sameStreet = Intrinsics.areEqual(u.getKind(), w.getKind());
                                if (sameStreet) {
                                    j = j3;
                                    x = x3;
                                    y = y3;
                                    ol = ol3;
                                    i = 2;
                                } else {
                                    int ue2 = 0;
                                    while (ue2 < 2) {
                                        if (ue2 == 0) {
                                            a = 0;
                                            i2 = 1;
                                        } else {
                                            i2 = 1;
                                            a = u.getE().length - 1;
                                        }
                                        int bb = ue2 == 0 ? i2 : u.getE().length - 2;
                                        if (Math.abs(u.getE()[a] - x3) > 0.5d) {
                                            j2 = j3;
                                            x2 = x3;
                                            y2 = y3;
                                            ol2 = ol3;
                                        } else if (Math.abs(u.getN()[a] - y3) > 0.5d) {
                                            j2 = j3;
                                            x2 = x3;
                                            y2 = y3;
                                            ol2 = ol3;
                                        } else {
                                            j2 = j3;
                                            x2 = x3;
                                            double ix = u.getE()[bb] - u.getE()[a];
                                            double iy = u.getN()[bb] - u.getN()[a];
                                            y2 = y3;
                                            ol2 = ol3;
                                            double il = RangesKt.coerceAtLeast(Math.hypot(ix, iy), 1.0E-6d);
                                            double c = (ox2 * (ix / il)) + (oy2 * (iy / il));
                                            if (c > bestCos) {
                                                bestCos = c;
                                                best = TuplesKt.to(u, Integer.valueOf(ue2));
                                            }
                                        }
                                        ue2++;
                                        y3 = y2;
                                        ol3 = ol2;
                                        j3 = j2;
                                        x3 = x2;
                                    }
                                    ue = 2;
                                }
                            }
                        }
                        sameStreet = OsmRoads.INSTANCE.sameStreet(u.getName(), w.getName());
                        if (sameStreet) {
                        }
                    }
                    ue = i;
                    y3 = y;
                    ol3 = ol;
                    j3 = j;
                    x3 = x;
                }
            }
            i = ue;
            j = j3;
            x = x3;
            y = y3;
            ol = ol3;
            ue = i;
            y3 = y;
            ol3 = ol;
            j3 = j;
            x3 = x;
        }
        return best;
    }

    private final IdentityHashMap<OsmRoads.Piece, Pair<double[], double[]>> bandsFor(List<OsmRoads.Piece> pieces, Function1<? super OsmRoads.Piece, Pair<Double, Double>> own) {
        double[] hs;
        OsmRoads.Piece p;
        int n;
        Pair ja;
        List<OsmRoads.Piece> list = pieces;
        IdentityHashMap out = new IdentityHashMap();
        IdentityHashMap mine = new IdentityHashMap();
        for (OsmRoads.Piece p2 : pieces) {
            mine.put(p2, own.invoke(p2));
        }
        Iterator<OsmRoads.Piece> it = pieces.iterator();
        while (it.hasNext()) {
            OsmRoads.Piece p3 = it.next();
            int n2 = Math.min(p3.getX().length, this.mx.length);
            if (n2 >= 2) {
                Object obj = mine.get(p3);
                Intrinsics.checkNotNull(obj);
                Pair pair = (Pair) obj;
                double c0 = ((Number) pair.component1()).doubleValue();
                double h0 = ((Number) pair.component2()).doubleValue();
                Pair ja2 = bandsFor$joint(list, mine, this, p3, 0);
                Pair jb = bandsFor$joint(list, mine, this, p3, 1);
                double[] s = new double[n2];
                int i = 1;
                while (i < n2) {
                    s[i] = s[i - 1] + Math.hypot(p3.getX()[i] - p3.getX()[i - 1], p3.getY()[i] - p3.getY()[i - 1]);
                    i++;
                    mine = mine;
                    it = it;
                    out = out;
                }
                IdentityHashMap out2 = out;
                IdentityHashMap mine2 = mine;
                Iterator<OsmRoads.Piece> it2 = it;
                double total = s[n2 - 1];
                double[] cs = new double[n2];
                double[] hs2 = new double[n2];
                int i2 = 0;
                while (i2 < n2) {
                    double c = c0;
                    double h = h0;
                    if (ja2 != null) {
                        hs = hs2;
                        p = p3;
                        n = n2;
                        double k = 1 - bandsFor$ease(this, s[i2]);
                        c += (ja2.getFirst().doubleValue() - c0) * k;
                        h += (ja2.getSecond().doubleValue() - h0) * k;
                    } else {
                        hs = hs2;
                        p = p3;
                        n = n2;
                    }
                    if (jb != null) {
                        ja = ja2;
                        double k2 = 1 - bandsFor$ease(this, total - s[i2]);
                        c += (jb.getFirst().doubleValue() - c0) * k2;
                        h += (jb.getSecond().doubleValue() - h0) * k2;
                    } else {
                        ja = ja2;
                    }
                    cs[i2] = c;
                    hs[i2] = h;
                    i2++;
                    ja2 = ja;
                    n2 = n;
                    p3 = p;
                    hs2 = hs;
                }
                out2.put(p3, TuplesKt.to(cs, hs2));
                list = pieces;
                mine = mine2;
                it = it2;
                out = out2;
            } else {
                list = pieces;
            }
        }
        return out;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0056  */
    /* JADX WARN: Removed duplicated region for block: B:18:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static final boolean bandsFor$sameStreet(SceneView this$0, OsmRoads.Way a, OsmRoads.Way b) {
        boolean sameStreet;
        if (a.getHighway() != b.getHighway() || this$0.laneMapped(a) != this$0.laneMapped(b)) {
            return false;
        }
        if (!(a.getName().length() > 0)) {
            if (!(b.getName().length() > 0)) {
                sameStreet = Intrinsics.areEqual(a.getKind(), b.getKind());
                return !sameStreet;
            }
        }
        sameStreet = OsmRoads.INSTANCE.sameStreet(a.getName(), b.getName());
        if (!sameStreet) {
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:68:0x019b, code lost:
    
        if (r2 == 1) goto L55;
     */
    /* JADX WARN: Removed duplicated region for block: B:62:0x01c7  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x01c9  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static final Pair<Double, Double> bandsFor$joint(List<OsmRoads.Piece> list, IdentityHashMap<OsmRoads.Piece, Pair<Double, Double>> identityHashMap, SceneView this$0, OsmRoads.Piece p, int end) {
        boolean z;
        boolean z2;
        int qe;
        double ol;
        OsmRoads.Piece piece = p;
        int n = p.getX().length;
        int i = 2;
        if (n < 2) {
            return null;
        }
        int i2 = end == 0 ? 0 : n - 1;
        int j = end == 0 ? 1 : n - 2;
        double ox = p.getX()[i2] - p.getX()[j];
        double oy = p.getY()[i2] - p.getY()[j];
        int i3 = i2;
        double ol2 = RangesKt.coerceAtLeast(Math.hypot(ox, oy), 1.0E-6d);
        double ox2 = ox / ol2;
        double oy2 = oy / ol2;
        Pair best = null;
        double bc = Math.cos(Math.toRadians(35.0d));
        for (OsmRoads.Piece q : list) {
            if (q == piece || q.getX().length < i || !bandsFor$sameStreet(this$0, p.getWay(), q.getWay())) {
                piece = p;
                n = n;
                ol2 = ol2;
                i = 2;
            } else {
                int qe2 = 0;
                while (qe2 < i) {
                    int a = qe2 == 0 ? 0 : q.getX().length - 1;
                    int n2 = n;
                    int n3 = qe2 == 0 ? 1 : q.getX().length - i;
                    if (Math.abs(q.getX()[a] - p.getX()[i3]) > 0.5d) {
                        qe = qe2;
                        ol = ol2;
                    } else if (Math.abs(q.getY()[a] - p.getY()[i3]) > 0.5d) {
                        qe = qe2;
                        ol = ol2;
                    } else {
                        qe = qe2;
                        double ix = q.getX()[n3] - q.getX()[a];
                        double iy = q.getY()[n3] - q.getY()[a];
                        ol = ol2;
                        double il = RangesKt.coerceAtLeast(Math.hypot(ix, iy), 1.0E-6d);
                        double c = (ox2 * (ix / il)) + (oy2 * (iy / il));
                        if (c > bc) {
                            bc = c;
                            best = TuplesKt.to(q, Integer.valueOf(qe));
                        }
                    }
                    qe2 = qe + 1;
                    n = n2;
                    ol2 = ol;
                    i = 2;
                }
                piece = p;
                i = 2;
            }
        }
        if (best == null) {
            return null;
        }
        OsmRoads.Piece q2 = (OsmRoads.Piece) best.component1();
        int qe3 = ((Number) best.component2()).intValue();
        Pair<Double, Double> pair = identityHashMap.get(q2);
        Intrinsics.checkNotNull(pair);
        Pair<Double, Double> pair2 = pair;
        double cq = pair2.component1().doubleValue();
        double hq = pair2.component2().doubleValue();
        if (end != 1 || qe3 != 0) {
            z2 = end == 0 ? true : true;
            z = false;
            boolean sameDir = z;
            Pair<Double, Double> pair3 = identityHashMap.get(p);
            Intrinsics.checkNotNull(pair3);
            Pair<Double, Double> pair4 = pair3;
            double cp = pair4.component1().doubleValue();
            double hp = pair4.component2().doubleValue();
            double d = 2;
            return TuplesKt.to(Double.valueOf((cp + (!sameDir ? cq : -cq)) / d), Double.valueOf((hp + hq) / d));
        }
        z = z2;
        boolean sameDir2 = z;
        Pair<Double, Double> pair32 = identityHashMap.get(p);
        Intrinsics.checkNotNull(pair32);
        Pair<Double, Double> pair42 = pair32;
        double cp2 = pair42.component1().doubleValue();
        double hp2 = pair42.component2().doubleValue();
        double d2 = 2;
        return TuplesKt.to(Double.valueOf((cp2 + (!sameDir2 ? cq : -cq)) / d2), Double.valueOf((hp2 + hq) / d2));
    }

    private static final double bandsFor$ease(SceneView this$0, double d) {
        double t = RangesKt.coerceIn(d / this$0.TAPER, 0.0d, 1.0d);
        return t * t * (3 - (2 * t));
    }

    private final void load(OsmRoads.Piece p) {
        int n = Math.min(p.getX().length, this.mx.length);
        for (int i = 0; i < n; i++) {
            this.mx[i] = p.getX()[i];
            this.my[i] = p.getY()[i];
        }
        this.curN = n;
    }

    private final void bridgeSides(int n, double off, double half, double[] h, int argb, double fadeR, double[] ak) {
        float lb;
        float lr;
        Iterator it;
        int i;
        double sd;
        int i2;
        int base;
        float lg;
        Triple<Float, Float, Float> linear = Companion.linear(argb);
        float lr2 = linear.component1().floatValue();
        float lg2 = linear.component2().floatValue();
        float lb2 = linear.component3().floatValue();
        int i3 = 0;
        Iterator it2 = CollectionsKt.listOf((Object[]) new Double[]{Double.valueOf(-1.0d), Double.valueOf(1.0d)}).iterator();
        while (it2.hasNext()) {
            double sd2 = ((Number) it2.next()).doubleValue();
            int i4 = 0;
            int i5 = n - 1;
            while (i4 < i5) {
                if (h[i4] >= 0.05d || h[i4 + 1] >= 0.05d) {
                    if (this.nVerts + 4 <= this.maxVerts && this.nIdx + 6 <= this.maxIdx) {
                        int base2 = this.nVerts;
                        int k = i4;
                        int i6 = i4 + 1;
                        if (k <= i6) {
                            while (true) {
                                int i0 = Math.max(i3, k - 1);
                                int i1 = Math.min(n - 1, k + 1);
                                Iterator it3 = it2;
                                i = i4;
                                double tx = this.mx[i1] - this.mx[i0];
                                it = it3;
                                double ty = this.my[i1] - this.my[i0];
                                i2 = i5;
                                base = base2;
                                lb = lb2;
                                double l = RangesKt.coerceAtLeast(Math.hypot(tx, ty), 1.0E-6d);
                                double tx2 = tx / l;
                                double nx = -(ty / l);
                                double cx = this.mx[k] + ((off + (sd2 * half)) * nx);
                                double cy = this.my[k] + ((off + (sd2 * half)) * tx2);
                                sd = sd2;
                                double d = Math.hypot(this.mx[k], this.my[k]);
                                float a = RangesKt.coerceIn((1.0f - RangesKt.coerceIn((float) ((d - (0.7d * fadeR)) / (0.3d * fadeR)), 0.0f, 1.0f)) * ((float) ak[k]), 0.0f, 1.0f);
                                lr = lr2;
                                float lr3 = lb * a;
                                int col = packColor(lr2 * a, lg2 * a, lr3, a);
                                double d2 = h[k] + 0.06d;
                                putVertex((float) cy, (float) d2, (float) (-cx), col);
                                lg = lg2;
                                putVertex((float) cy, (float) Math.max(0.0d, h[k] - 1.1d), (float) (-cx), col);
                                if (k == i6) {
                                    break;
                                }
                                k++;
                                lg2 = lg;
                                i5 = i2;
                                base2 = base;
                                lb2 = lb;
                                i4 = i;
                                it2 = it;
                                sd2 = sd;
                                lr2 = lr;
                                i3 = 0;
                            }
                        } else {
                            lb = lb2;
                            lr = lr2;
                            it = it2;
                            i = i4;
                            sd = sd2;
                            i2 = i5;
                            base = base2;
                            lg = lg2;
                        }
                        int base3 = base;
                        putTri(base3, base + 2, base + 1);
                        putTri(base3 + 1, base3 + 2, base3 + 3);
                    }
                    return;
                }
                lb = lb2;
                lr = lr2;
                it = it2;
                i = i4;
                sd = sd2;
                i2 = i5;
                lg = lg2;
                i4 = i + 1;
                lg2 = lg;
                i5 = i2;
                lb2 = lb;
                it2 = it;
                sd2 = sd;
                lr2 = lr;
                i3 = 0;
            }
            i3 = 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void polyLine(int n, double off, double half, int argb, float lift, double fadeR, boolean dashed, double[] per) {
        double sCar;
        float lb;
        double s;
        double sCar2;
        double uy;
        float lb2;
        Double orNull;
        if (!dashed) {
            if (per != null) {
                polyRibbon$default(this, n, 0.0d, half, argb, lift, fadeR, null, null, per, null, 704, null);
                return;
            } else {
                polyRibbon$default(this, n, off, half, argb, lift, fadeR, null, null, null, null, 960, null);
                return;
            }
        }
        if (n < 2) {
            return;
        }
        for (int i = 0; i < n; i++) {
            int i0 = Math.max(0, i - 1);
            int i1 = Math.min(n - 1, i + 1);
            double tx = this.mx[i1] - this.mx[i0];
            double ty = this.my[i1] - this.my[i0];
            double l = RangesKt.coerceAtLeast(Math.hypot(tx, ty), 1.0E-6d);
            double tx2 = tx / l;
            double ty2 = ty / l;
            double o = (per == null || (orNull = ArraysKt.getOrNull(per, i)) == null) ? off : orNull.doubleValue();
            this.dashX[i] = this.mx[i] - (ty2 * o);
            this.dashY[i] = this.my[i] + (tx2 * o);
        }
        double s2 = 0.0d;
        double sCar3 = 0.0d;
        double dCar = Double.MAX_VALUE;
        for (int i2 = 0; i2 < n; i2++) {
            if (i2 > 0) {
                s2 += Math.hypot(this.dashX[i2] - this.dashX[i2 - 1], this.dashY[i2] - this.dashY[i2 - 1]);
            }
            double dc = Math.hypot(this.dashX[i2], this.dashY[i2]);
            if (dc < dCar) {
                dCar = dc;
                sCar3 = s2;
            }
        }
        Triple<Float, Float, Float> linear = Companion.linear(argb);
        float lr = linear.component1().floatValue();
        float lg = linear.component2().floatValue();
        float lb3 = linear.component3().floatValue();
        double s3 = 0.0d;
        int i3 = 0;
        int i4 = n - 1;
        while (i3 < i4) {
            double ax = this.dashX[i3];
            double ay = this.dashY[i3];
            double dCar2 = dCar;
            double dCar3 = this.dashX[i3 + 1] - ax;
            int i5 = i4;
            double dy = this.dashY[i3 + 1] - ay;
            int i6 = i3;
            double len = Math.hypot(dCar3, dy);
            if (len >= 0.001d) {
                double ux = dCar3 / len;
                double uy2 = dy / len;
                double b = -uy2;
                double a = 0.0d;
                while (a < len) {
                    double nx = b;
                    double b2 = Math.min(len, a + 0.5d);
                    double len2 = len;
                    double g = (((((s3 + ((a + b2) / 2)) - sCar3) + this.odoDist) % 9.0d) + 9.0d) % 9.0d;
                    if (g >= 3.0d) {
                        s = s3;
                        sCar2 = sCar3;
                        uy = uy2;
                        lb2 = lb3;
                    } else {
                        if (this.nVerts + 4 <= this.maxVerts && this.nIdx + 6 <= this.maxIdx) {
                            double x0 = ax + (ux * a);
                            s = s3;
                            double y0 = ay + (uy2 * a);
                            double x1 = ax + (ux * b2);
                            double y1 = ay + (uy2 * b2);
                            double dd = Math.hypot(x0, y0);
                            sCar2 = sCar3;
                            double sCar4 = (dd - (0.7d * fadeR)) / (0.3d * fadeR);
                            float al = (Color.alpha(argb) / 255.0f) * (1.0f - RangesKt.coerceIn((float) sCar4, 0.0f, 1.0f));
                            int col = packColor(lr * al, lg * al, lb3 * al, al);
                            int base = this.nVerts;
                            uy = uy2;
                            putVertex((float) (y0 - (ux * half)), lift, (float) (-(x0 - (nx * half))), col);
                            lb2 = lb3;
                            putVertex((float) ((ux * half) + y0), lift, (float) (-(x0 + (nx * half))), col);
                            putVertex((float) (y1 - (ux * half)), lift, (float) (-(x1 - (nx * half))), col);
                            putVertex((float) (y1 + (ux * half)), lift, (float) (-(x1 + (nx * half))), col);
                            putTri(base, base + 2, base + 1);
                            putTri(base + 1, base + 2, base + 3);
                        }
                        return;
                    }
                    a = b2;
                    lb3 = lb2;
                    b = nx;
                    len = len2;
                    s3 = s;
                    uy2 = uy;
                    sCar3 = sCar2;
                }
                sCar = sCar3;
                lb = lb3;
                s3 += len;
            } else {
                sCar = sCar3;
                lb = lb3;
            }
            i3 = i6 + 1;
            lb3 = lb;
            dCar = dCar2;
            i4 = i5;
            sCar3 = sCar;
        }
    }

    static /* synthetic */ void polyRibbon$default(SceneView sceneView, int i, double d, double d2, int i2, float f, double d3, double[] dArr, double[] dArr2, double[] dArr3, double[] dArr4, int i3, Object obj) {
        double[] dArr5;
        double[] dArr6;
        double[] dArr7 = (i3 & 64) != 0 ? null : dArr;
        double[] dArr8 = (i3 & 128) != 0 ? null : dArr2;
        if ((i3 & 256) == 0) {
            dArr5 = dArr3;
        } else {
            dArr5 = null;
        }
        if ((i3 & 512) == 0) {
            dArr6 = dArr4;
        } else {
            dArr6 = null;
        }
        sceneView.polyRibbon(i, d, d2, i2, f, d3, dArr7, dArr8, dArr5, dArr6);
    }

    private final void polyRibbon(int n, double off, double half, int argb, float lift, double fadeR, double[] h, double[] ak, double[] cOff, double[] cHalf) {
        Double orNull;
        Double orNull2;
        int i = n;
        double[] dArr = cHalf;
        if (i < 2 || this.nVerts + (i * 2) > this.maxVerts || this.nIdx + ((i - 1) * 6) > this.maxIdx) {
            return;
        }
        Triple<Float, Float, Float> linear = Companion.linear(argb);
        float lr = linear.component1().floatValue();
        float lg = linear.component2().floatValue();
        float lb = linear.component3().floatValue();
        int base = this.nVerts;
        int i2 = 0;
        while (i2 < i) {
            int i0 = Math.max(0, i2 - 1);
            int i1 = Math.min(i - 1, i2 + 1);
            double tx = this.mx[i1] - this.mx[i0];
            double ty = this.my[i1] - this.my[i0];
            double l = RangesKt.coerceAtLeast(Math.hypot(tx, ty), 1.0E-6d);
            double tx2 = tx / l;
            double nx = -(ty / l);
            double d = Math.hypot(this.mx[i2], this.my[i2]);
            float f = 0.0f;
            float a = RangesKt.coerceIn((Color.alpha(argb) / 255.0f) * (1.0f - RangesKt.coerceIn((float) ((d - (0.7d * fadeR)) / (0.3d * fadeR)), 0.0f, 1.0f)) * (ak != null ? (float) ak[i2] : 1.0f), 0.0f, 1.0f);
            int col = packColor(lr * a, lg * a, lb * a, a);
            double d2 = 0.0d;
            double o = off + ((cOff == null || (orNull2 = ArraysKt.getOrNull(cOff, i2)) == null) ? 0.0d : orNull2.doubleValue());
            if (dArr != null && (orNull = ArraysKt.getOrNull(dArr, i2)) != null) {
                d2 = orNull.doubleValue();
            }
            double hw = half + d2;
            double cx = this.mx[i2] + (nx * o);
            double cy = this.my[i2] + (tx2 * o);
            if (h != null) {
                f = (float) h[i2];
            }
            float y = lift + f;
            putVertex((float) (cy - (tx2 * hw)), y, (float) (-(cx - (nx * hw))), col);
            putVertex((float) (cy + (tx2 * hw)), y, (float) (-(cx + (nx * hw))), col);
            i2++;
            i = n;
            dArr = cHalf;
            lb = lb;
        }
        int i3 = n - 1;
        for (int i4 = 0; i4 < i3; i4++) {
            int a2 = (i4 * 2) + base;
            putTri(a2, a2 + 2, a2 + 1);
            putTri(a2 + 1, a2 + 2, a2 + 3);
        }
    }

    private final void fillBetween(float[] xs, float[] yl, float[] yr, int argb, float lift, Function1<? super Integer, Float> alphaAt) {
        int n = Math.min(xs.length, Math.min(yl.length, yr.length));
        if (n < 2 || this.nVerts + (n * 2) > this.maxVerts) {
            return;
        }
        if (this.nIdx + ((n - 1) * 6) > this.maxIdx) {
            return;
        }
        Triple<Float, Float, Float> linear = Companion.linear(argb);
        float lr = linear.component1().floatValue();
        float lgc = linear.component2().floatValue();
        float lb = linear.component3().floatValue();
        int base = this.nVerts;
        for (int i = 0; i < n; i++) {
            float a = RangesKt.coerceIn((Color.alpha(argb) / 255.0f) * alphaAt.invoke(Integer.valueOf(i)).floatValue(), 0.0f, 1.0f);
            int col = packColor(lr * a, lgc * a, lb * a, a);
            putVertex(yl[i], lift, -xs[i], col);
            putVertex(yr[i], lift, -xs[i], col);
        }
        int i2 = n - 1;
        for (int i3 = 0; i3 < i2; i3++) {
            int a2 = (i3 * 2) + base;
            putTri(a2, a2 + 2, a2 + 1);
            putTri(a2 + 1, a2 + 2, a2 + 3);
        }
    }

    private final float[] shownY(LaneTracks.Line l) {
        float[] xs = l.getX();
        float[] ys = l.getY();
        if (!this.shapeOk) {
            return ys;
        }
        ArrayList d = new ArrayList();
        int length = xs.length;
        for (int q = 0; q < length; q++) {
            double xq = xs[q];
            if (xq >= 3.0d) {
                if (xq > 40.0d) {
                    break;
                }
                d.add(Double.valueOf(ys[q] - shapeY(xq)));
            }
        }
        int q2 = d.size();
        if (q2 < 3) {
            return ys;
        }
        CollectionsKt.sort(d);
        Object obj = d.get(d.size() / 2);
        Intrinsics.checkNotNullExpressionValue(obj, "get(...)");
        double off = ((Number) obj).doubleValue();
        int length2 = xs.length;
        float[] fArr = new float[length2];
        for (int i = 0; i < length2; i++) {
            fArr[i] = (float) (shapeY(xs[i]) + off);
        }
        return fArr;
    }

    /* JADX WARN: Code restructure failed: missing block: B:195:0x06fd, code lost:
    
        r2 = java.util.Arrays.copyOf(r5, r7);
        r1 = r64;
        kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r2, r1);
        r11 = java.util.Arrays.copyOf(r6, r7);
        kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r11, r1);
        r38 = r1;
        r39 = r3;
        r34 = r5;
        r41 = r6;
        r35 = r7;
        r36 = r8;
        r42 = r10;
        r37 = r0;
        ribbon(r2, r11, null, 0.06d, r31, r4, r39, 0.08f, new com.starpilot.starview.SceneView$$ExternalSyntheticLambda15(r5));
     */
    /* JADX WARN: Code restructure failed: missing block: B:370:0x03c7, code lost:
    
        r9 = r15;
        ribbon(r50, r3, null, r46 + 0.01d, r41, r2, r5, 0.08f, r2);
     */
    /* JADX WARN: Removed duplicated region for block: B:12:0x00a1  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x04d9  */
    /* JADX WARN: Removed duplicated region for block: B:232:0x07e2  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x07ef  */
    /* JADX WARN: Removed duplicated region for block: B:313:0x021b  */
    /* JADX WARN: Removed duplicated region for block: B:316:0x0234  */
    /* JADX WARN: Removed duplicated region for block: B:326:0x0293  */
    /* JADX WARN: Removed duplicated region for block: B:340:0x044e A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:343:0x02fb A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:385:0x02ec A[ADDED_TO_REGION, EDGE_INSN: B:385:0x02ec->B:338:0x02ec BREAK  A[LOOP:15: B:324:0x0281->B:335:0x02ab], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:388:0x023a  */
    /* JADX WARN: Removed duplicated region for block: B:389:0x0229  */
    /* JADX WARN: Removed duplicated region for block: B:418:0x04c9  */
    /* JADX WARN: Removed duplicated region for block: B:419:0x007c  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x08be  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x0a8d  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x007a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final void rebuildRibbons(ModelSnapshot m, Telemetry t, boolean latActive) {
        boolean z;
        boolean mapped;
        String str;
        LaneTracks.PathState state;
        int i;
        SceneView sceneView;
        String str2;
        LaneMemory mem;
        Telemetry telemetry;
        String str3;
        float f;
        final SceneView sceneView2;
        int i2;
        Telemetry telemetry2;
        LaneTracks.PathState state2;
        int rgb;
        int n;
        int color;
        int i3;
        int i4;
        boolean mapped2;
        float[] rx;
        float[] ry;
        int nn;
        float[] xs0;
        float phase;
        String str4;
        int i5;
        String str5;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        float[] fArr;
        double d;
        float[] rx2;
        float[] ry2;
        int j;
        float x;
        int nn2;
        Iterator<LaneTracks.Line> it;
        LaneTracks.Line nearL;
        LaneTracks.Line leftL;
        LaneTracks.Line rightL;
        int alpha;
        LaneTracks.PathState state3;
        float[] offs;
        float[] ry3;
        String str6;
        String str7;
        Iterator<LaneTracks.Line> it2;
        float[] xs;
        int i11;
        int i12;
        int i13;
        SceneView sceneView3;
        SceneView sceneView4;
        int e;
        SceneView sceneView5 = this;
        Palette p = getPal();
        beginRibbons();
        LaneTracks laneTracks = sceneView5.tracks;
        LaneMarks laneMarks = sceneView5.laneMarks;
        laneTracks.update(m, t, 0.05f, true, laneMarks != null ? laneMarks.getMarks() : null);
        sceneView5.pathStateNow = LaneTracks.Companion.pathState(t);
        sceneView5.laneLinesOn = t.s("lane_lines");
        int nn3 = 0;
        int i14 = 1;
        if (t.s("map_roads")) {
            try {
                z = sceneView5.drawMapRoads(m, t.getVEgo());
            } catch (Exception e2) {
                Log.e("StarView/Scene", "map draw", e2);
                sceneView5.mapStatus = StringsKt.take("map error: " + e2, 90);
                z = false;
            }
            if (z) {
                mapped = true;
                sceneView5.cleanRoad(m, t.getVEgo(), mapped);
                sceneView5.layoutHideRoad = mapped;
                LaneTracks.PathState state4 = sceneView5.pathStateNow;
                int i15 = 220;
                String str8 = "lane_lines";
                int i16 = 2;
                if (t.s("lane_lines")) {
                    str = "copyOf(...)";
                    state = state4;
                    i = 1;
                    sceneView = sceneView5;
                    str2 = str8;
                } else if (mapped && sceneView5.mapLanes) {
                    str = "copyOf(...)";
                    state = state4;
                    i = 1;
                    sceneView = sceneView5;
                    str2 = str8;
                } else {
                    if (t.s("lane_centering_cue")) {
                        i5 = 176;
                        str5 = "copyOf(...)";
                        i6 = LaneCentering.direction$default(LaneCentering.INSTANCE, t, 0L, 2, null);
                    } else {
                        i5 = 176;
                        str5 = "copyOf(...)";
                        i6 = 0;
                    }
                    int lcDir = i6;
                    boolean dark = sceneView5.night || sceneView5.alwaysDark;
                    int white = dark ? Color.rgb(214, 218, 224) : -1;
                    if (dark) {
                        i7 = 174;
                        i8 = 43;
                        i9 = 217;
                    } else {
                        i7 = 194;
                        i8 = 48;
                        i9 = 242;
                    }
                    int yellow = Color.rgb(i9, i7, i8);
                    Triple<LaneTracks.Line, LaneTracks.Line, LaneTracks.Line> inner = sceneView5.tracks.inner();
                    LaneTracks.Line leftL2 = inner.component1();
                    LaneTracks.Line rightL2 = inner.component2();
                    LaneTracks.Line nearL2 = inner.component3();
                    boolean centreLeft = centreLineOnLeft();
                    Iterator<LaneTracks.Line> it3 = sceneView5.tracks.getLines().iterator();
                    Intrinsics.checkNotNullExpressionValue(it3, "iterator(...)");
                    while (it3.hasNext()) {
                        LaneTracks.Line next = it3.next();
                        Intrinsics.checkNotNullExpressionValue(next, "next(...)");
                        LaneTracks.Line l = next;
                        if (l.getAlpha() >= 0.03f) {
                            LaneMarks.Mark mk = l.getMark();
                            int kind = mk != null ? mk.getKind() : nn3;
                            if (kind == i14) {
                                i14 = i14;
                                i15 = 220;
                                nn3 = 0;
                                i16 = 2;
                                i5 = 176;
                            } else if (!mapped || kind == i16 || kind == 3) {
                                float[] xs2 = l.getX();
                                int i17 = (l == leftL2 || l == rightL2) ? 1 : nn3;
                                float[] ys = sceneView5.shownY(l);
                                int i18 = ((lcDir <= 0 || l != rightL2) && (lcDir >= 0 || l != leftL2)) ? nn3 : 1;
                                int i19 = (!centreLeft || l != leftL2 || mk == null || !(kind == i16 || kind == 3) || (mk.getYellow() && !mk.getDouble() && kind == i16)) ? nn3 : 1;
                                int base = i18 != 0 ? Color.rgb(nn3, i5, i15) : (i19 == 0 && (mk == null || kind == 0 || !mk.getYellow())) ? white : yellow;
                                int base2 = (l == nearL2 && state4 == LaneTracks.PathState.ENGAGED) ? LaneTracks.Companion.effortColor(base, sceneView5.tracks.getEffort()) : base;
                                int alpha2 = RangesKt.coerceIn((int) ((kind == 0 ? 150 : 255) * l.getAlpha()), nn3, 255);
                                int col = Companion.withAlpha(base2, alpha2);
                                int i20 = (i19 == 0 && (mk == null || kind == 0 || !mk.getDouble())) ? nn3 : 1;
                                if (i19 == 0 && i20 == 0) {
                                    if (kind == 0) {
                                        i10 = i17;
                                    } else if (kind == i16) {
                                        i10 = 1;
                                    }
                                    int i21 = i10;
                                    if (i20 == 0) {
                                        fArr = new float[i16];
                                        fArr[nn3] = -0.11f;
                                        fArr[1] = 0.11f;
                                    } else {
                                        fArr = new float[1];
                                        fArr[nn3] = 0.0f;
                                    }
                                    float[] offs2 = fArr;
                                    double d2 = offs2.length != 2 ? 0.05d : 0.07d;
                                    if (l == nearL2 && sceneView5.tracks.getEffort() > 0.55f && state4 == LaneTracks.PathState.ENGAGED) {
                                        d = 0.03d;
                                        double half = d2 + d;
                                        rx2 = new float[230];
                                        ry2 = new float[230];
                                        int i22 = 0;
                                        j = 1;
                                        x = Math.max(0.0f, xs2[0]);
                                        nn2 = 0;
                                        while (true) {
                                            int j2 = j;
                                            it = it3;
                                            if (x <= Math.min(110.0f, ArraysKt.last(xs2)) || nn2 >= 230) {
                                                break;
                                            }
                                            int j3 = j2;
                                            while (j3 < xs2.length - 1 && xs2[j3] < x) {
                                                j3++;
                                            }
                                            float f2 = RangesKt.coerceIn((x - xs2[j3 - 1]) / Math.max(0.01f, xs2[j3] - xs2[j3 - 1]), 0.0f, 1.0f);
                                            rx2[nn2] = x;
                                            ry2[nn2] = ys[j3 - 1] + ((ys[j3] - ys[j3 - 1]) * f2);
                                            nn2++;
                                            x += 0.5f;
                                            j = j3;
                                            it3 = it;
                                            xs2 = xs2;
                                            kind = kind;
                                        }
                                        int kind2 = kind;
                                        float[] xs3 = xs2;
                                        int i23 = 1;
                                        Unit unit = Unit.INSTANCE;
                                        if (nn2 < 2) {
                                            final float[] lx = Arrays.copyOf(rx2, nn2);
                                            Intrinsics.checkNotNullExpressionValue(lx, str5);
                                            Function1 lineFade = new Function1() { // from class: com.starpilot.starview.SceneView$$ExternalSyntheticLambda14
                                                @Override // kotlin.jvm.functions.Function1
                                                public final Object invoke(Object obj) {
                                                    float rebuildRibbons$lambda$95;
                                                    rebuildRibbons$lambda$95 = SceneView.rebuildRibbons$lambda$95(lx, ((Integer) obj).intValue());
                                                    return Float.valueOf(rebuildRibbons$lambda$95);
                                                }
                                            };
                                            int length = offs2.length;
                                            int i24 = 0;
                                            while (i24 < length) {
                                                float o = offs2[i24];
                                                float[] yy = new float[nn2];
                                                float[] lx2 = lx;
                                                for (int i25 = i22; i25 < nn2; i25++) {
                                                    yy[i25] = ry2[i25] + o;
                                                }
                                                int kind3 = kind2;
                                                if (i21 == 0) {
                                                    it2 = it;
                                                    i11 = length;
                                                    nearL = nearL2;
                                                    leftL = leftL2;
                                                    rightL = rightL2;
                                                    alpha = alpha2;
                                                    state3 = state4;
                                                    str7 = str5;
                                                    xs = xs3;
                                                    i12 = i24;
                                                    i13 = i23;
                                                    offs = offs2;
                                                    str6 = str8;
                                                    ry3 = ry2;
                                                    ribbon$default(this, lx2, yy, null, half, col, 0, 0, 0.08f, lineFade, 96, null);
                                                    sceneView3 = sceneView5;
                                                    nn2 = nn2;
                                                } else {
                                                    nearL = nearL2;
                                                    leftL = leftL2;
                                                    rightL = rightL2;
                                                    alpha = alpha2;
                                                    state3 = state4;
                                                    int nn4 = nn2;
                                                    offs = offs2;
                                                    ry3 = ry2;
                                                    str6 = str8;
                                                    str7 = str5;
                                                    it2 = it;
                                                    xs = xs3;
                                                    i11 = length;
                                                    i12 = i24;
                                                    i13 = i23;
                                                    float phase2 = (float) (sceneView5.odoDist % 9.0d);
                                                    int a = 0;
                                                    while (true) {
                                                        nn2 = nn4;
                                                        if (a >= nn2) {
                                                            break;
                                                        }
                                                        float pp = (lx2[a] + phase2) % 9.0f;
                                                        if (pp < 3.0f) {
                                                            int e3 = a;
                                                            while (e3 + 1 < nn2 && (lx2[e3 + 1] + phase2) % 9.0f >= pp && (lx2[e3 + 1] + phase2) % 9.0f < 3.0f) {
                                                                e3++;
                                                            }
                                                            sceneView4 = sceneView5;
                                                            e = e3 + 1;
                                                            Unit unit2 = Unit.INSTANCE;
                                                        } else {
                                                            sceneView4 = sceneView5;
                                                            e = a + 1;
                                                            Integer.valueOf(a);
                                                        }
                                                        a = e;
                                                        sceneView5 = sceneView4;
                                                        nn4 = nn2;
                                                    }
                                                    sceneView3 = sceneView5;
                                                }
                                                int i26 = i12 + 1;
                                                sceneView5 = sceneView3;
                                                leftL2 = leftL;
                                                length = i11;
                                                i23 = i13;
                                                offs2 = offs;
                                                ry2 = ry3;
                                                nearL2 = nearL;
                                                rightL2 = rightL;
                                                alpha2 = alpha;
                                                state4 = state3;
                                                str5 = str7;
                                                str8 = str6;
                                                i22 = 0;
                                                i24 = i26;
                                                xs3 = xs;
                                                it = it2;
                                                lx = lx2;
                                                kind2 = kind3;
                                            }
                                            it3 = it;
                                            i14 = i23;
                                            i15 = 220;
                                            nn3 = 0;
                                            i16 = 2;
                                            i5 = 176;
                                        } else {
                                            it3 = it;
                                            i14 = 1;
                                            i15 = 220;
                                            nn3 = 0;
                                            i16 = 2;
                                            i5 = 176;
                                        }
                                    }
                                    d = 0.0d;
                                    double half2 = d2 + d;
                                    rx2 = new float[230];
                                    ry2 = new float[230];
                                    int i222 = 0;
                                    j = 1;
                                    x = Math.max(0.0f, xs2[0]);
                                    nn2 = 0;
                                    while (true) {
                                        int j22 = j;
                                        it = it3;
                                        if (x <= Math.min(110.0f, ArraysKt.last(xs2))) {
                                            break;
                                        } else {
                                            break;
                                        }
                                        float f22 = RangesKt.coerceIn((x - xs2[j3 - 1]) / Math.max(0.01f, xs2[j3] - xs2[j3 - 1]), 0.0f, 1.0f);
                                        rx2[nn2] = x;
                                        ry2[nn2] = ys[j3 - 1] + ((ys[j3] - ys[j3 - 1]) * f22);
                                        nn2++;
                                        x += 0.5f;
                                        j = j3;
                                        it3 = it;
                                        xs2 = xs2;
                                        kind = kind;
                                    }
                                    int kind22 = kind;
                                    float[] xs32 = xs2;
                                    int i232 = 1;
                                    Unit unit3 = Unit.INSTANCE;
                                    if (nn2 < 2) {
                                    }
                                }
                                i10 = nn3;
                                int i212 = i10;
                                if (i20 == 0) {
                                }
                                float[] offs22 = fArr;
                                if (offs22.length != 2) {
                                }
                                if (l == nearL2) {
                                    d = 0.03d;
                                    double half22 = d2 + d;
                                    rx2 = new float[230];
                                    ry2 = new float[230];
                                    int i2222 = 0;
                                    j = 1;
                                    x = Math.max(0.0f, xs2[0]);
                                    nn2 = 0;
                                    while (true) {
                                        int j222 = j;
                                        it = it3;
                                        if (x <= Math.min(110.0f, ArraysKt.last(xs2))) {
                                        }
                                        float f222 = RangesKt.coerceIn((x - xs2[j3 - 1]) / Math.max(0.01f, xs2[j3] - xs2[j3 - 1]), 0.0f, 1.0f);
                                        rx2[nn2] = x;
                                        ry2[nn2] = ys[j3 - 1] + ((ys[j3] - ys[j3 - 1]) * f222);
                                        nn2++;
                                        x += 0.5f;
                                        j = j3;
                                        it3 = it;
                                        xs2 = xs2;
                                        kind = kind;
                                    }
                                    int kind222 = kind;
                                    float[] xs322 = xs2;
                                    int i2322 = 1;
                                    Unit unit32 = Unit.INSTANCE;
                                    if (nn2 < 2) {
                                    }
                                }
                                d = 0.0d;
                                double half222 = d2 + d;
                                rx2 = new float[230];
                                ry2 = new float[230];
                                int i22222 = 0;
                                j = 1;
                                x = Math.max(0.0f, xs2[0]);
                                nn2 = 0;
                                while (true) {
                                    int j2222 = j;
                                    it = it3;
                                    if (x <= Math.min(110.0f, ArraysKt.last(xs2))) {
                                    }
                                    float f2222 = RangesKt.coerceIn((x - xs2[j3 - 1]) / Math.max(0.01f, xs2[j3] - xs2[j3 - 1]), 0.0f, 1.0f);
                                    rx2[nn2] = x;
                                    ry2[nn2] = ys[j3 - 1] + ((ys[j3] - ys[j3 - 1]) * f2222);
                                    nn2++;
                                    x += 0.5f;
                                    j = j3;
                                    it3 = it;
                                    xs2 = xs2;
                                    kind = kind;
                                }
                                int kind2222 = kind;
                                float[] xs3222 = xs2;
                                int i23222 = 1;
                                Unit unit322 = Unit.INSTANCE;
                                if (nn2 < 2) {
                                }
                            }
                        } else {
                            i14 = i14;
                            i15 = 220;
                            nn3 = 0;
                            i16 = 2;
                            i5 = 176;
                        }
                    }
                    state = state4;
                    i = i14;
                    sceneView = sceneView5;
                    str2 = str8;
                    str = str5;
                }
                mem = sceneView.laneMemory;
                if (mem == null) {
                    telemetry = t;
                    str3 = str2;
                    if (telemetry.s(str3) && telemetry.s("lane_memory_lines")) {
                        if (mapped && sceneView.mapLanes) {
                            f = 0.0f;
                        } else {
                            Triple<LaneTracks.Line, LaneTracks.Line, LaneTracks.Line> inner2 = sceneView.tracks.inner();
                            LaneTracks.Line leftL3 = inner2.component1();
                            LaneTracks.Line rightL3 = inner2.component2();
                            Iterable $this$filter$iv = sceneView.tracks.getLines();
                            Collection destination$iv$iv = new ArrayList();
                            for (Object element$iv$iv : $this$filter$iv) {
                                LaneTracks.Line it4 = (LaneTracks.Line) element$iv$iv;
                                if ((it4.getAlpha() > 0.2f ? i : 0) != 0) {
                                    destination$iv$iv.add(element$iv$iv);
                                }
                            }
                            Iterable $this$map$iv = (List) destination$iv$iv;
                            Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                            for (Object item$iv$iv : $this$map$iv) {
                                LaneTracks.Line it5 = (LaneTracks.Line) item$iv$iv;
                                destination$iv$iv2.add(Float.valueOf(it5.getY0()));
                            }
                            Iterable seen = (List) destination$iv$iv2;
                            int i27 = (sceneView.night || sceneView.alwaysDark) ? i : 0;
                            for (Pair<Float, Float> pair : mem.getLines()) {
                                float y = pair.component1().floatValue();
                                float conf = pair.component2().floatValue();
                                if (conf >= 0.45f) {
                                    Iterable $this$any$iv = seen;
                                    if (!($this$any$iv instanceof Collection) || !((Collection) $this$any$iv).isEmpty()) {
                                        Iterator it6 = $this$any$iv.iterator();
                                        while (true) {
                                            if (!it6.hasNext()) {
                                                i4 = 0;
                                                break;
                                            }
                                            float it7 = ((Number) it6.next()).floatValue();
                                            if ((Math.abs(it7 - y) < 0.8f ? i : 0) != 0) {
                                                i4 = i;
                                                break;
                                            }
                                        }
                                    } else {
                                        i4 = 0;
                                    }
                                    if (i4 == 0) {
                                        LaneTracks.Line line = y < 0.0f ? leftL3 : rightL3;
                                        if (line != null) {
                                            LaneTracks.Line ref = line;
                                            float[] ys0 = sceneView.shownY(ref);
                                            float[] xs02 = ref.getX();
                                            if (xs02.length >= 2) {
                                                float off = y - ref.getY0();
                                                int i28 = 140;
                                                final float[] rx3 = new float[140];
                                                float[] ry4 = new float[140];
                                                int j4 = 1;
                                                int nn5 = 0;
                                                float x2 = Math.max(0.0f, xs02[0]);
                                                while (x2 <= Math.min(70.0f, ArraysKt.last(xs02)) && nn5 < i28) {
                                                    int j5 = j4;
                                                    while (j5 < xs02.length - 1 && xs02[j5] < x2) {
                                                        j5++;
                                                    }
                                                    float fq = RangesKt.coerceIn((x2 - xs02[j5 - 1]) / Math.max(0.01f, xs02[j5] - xs02[j5 - 1]), 0.0f, 1.0f);
                                                    rx3[nn5] = x2;
                                                    ry4[nn5] = ys0[j5 - 1] + ((ys0[j5] - ys0[j5 - 1]) * fq) + off;
                                                    nn5++;
                                                    x2 += 0.5f;
                                                    j4 = j5;
                                                    i28 = 140;
                                                    ref = ref;
                                                }
                                                Unit unit4 = Unit.INSTANCE;
                                                if (nn5 >= 2) {
                                                    int col2 = Companion.withAlpha(i27 != 0 ? Color.rgb(176, 184, 196) : -1, RangesKt.coerceIn((int) (110 * conf), 30, 110));
                                                    float phase3 = (float) (sceneView.odoDist % 9.0d);
                                                    int a2 = 0;
                                                    while (a2 < nn5) {
                                                        float pp2 = (rx3[a2] + phase3) % 9.0f;
                                                        if (pp2 < 3.0f) {
                                                            int e4 = a2;
                                                            while (e4 + 1 < nn5 && (rx3[e4 + 1] + phase3) % 9.0f >= pp2 && (rx3[e4 + 1] + phase3) % 9.0f < 3.0f) {
                                                                e4++;
                                                            }
                                                            mapped2 = mapped;
                                                            int e5 = e4;
                                                            rx = rx3;
                                                            ry = ry4;
                                                            nn = nn5;
                                                            xs0 = xs02;
                                                            phase = phase3;
                                                            str4 = str;
                                                            Unit unit5 = Unit.INSTANCE;
                                                            a2 = e5 + 1;
                                                        } else {
                                                            mapped2 = mapped;
                                                            int a3 = a2;
                                                            rx = rx3;
                                                            ry = ry4;
                                                            nn = nn5;
                                                            xs0 = xs02;
                                                            phase = phase3;
                                                            str4 = str;
                                                            a2 = a3 + 1;
                                                            Integer.valueOf(a3);
                                                        }
                                                        rx3 = rx;
                                                        nn5 = nn;
                                                        xs02 = xs0;
                                                        mapped = mapped2;
                                                        str = str4;
                                                        ry4 = ry;
                                                        phase3 = phase;
                                                    }
                                                    sceneView = this;
                                                    mapped = mapped;
                                                } else {
                                                    sceneView = this;
                                                    mapped = mapped;
                                                }
                                            } else {
                                                sceneView = this;
                                                mapped = mapped;
                                            }
                                        }
                                    }
                                } else {
                                    sceneView = this;
                                    mapped = mapped;
                                }
                            }
                            f = 0.0f;
                        }
                        if (telemetry.s(str3) || !telemetry.s("lane_change_lane")) {
                            sceneView2 = this;
                        } else {
                            sceneView2 = this;
                            if (sceneView2.tracks.getChangeAlpha() > 0.02f) {
                                final float[] bx = new float[121];
                                for (int i29 = 0; i29 < 121; i29++) {
                                    bx[i29] = i29 * 0.5f;
                                }
                                int length2 = bx.length;
                                float[] by = new float[length2];
                                for (int i30 = 0; i30 < length2; i30++) {
                                    by[i30] = sceneView2.tracks.targetCenter(bx[i30], new Function1() { // from class: com.starpilot.starview.SceneView$$ExternalSyntheticLambda16
                                        @Override // kotlin.jvm.functions.Function1
                                        public final Object invoke(Object obj) {
                                            float[] rebuildRibbons$lambda$101;
                                            rebuildRibbons$lambda$101 = SceneView.rebuildRibbons$lambda$101(SceneView.this, (LaneTracks.Line) obj);
                                            return rebuildRibbons$lambda$101;
                                        }
                                    });
                                }
                                int length3 = by.length;
                                int i31 = 0;
                                while (true) {
                                    if (i31 >= length3) {
                                        i3 = i;
                                        break;
                                    }
                                    float element$iv = by[i31];
                                    if (((Float.isInfinite(element$iv) || Float.isNaN(element$iv)) ? 0 : i) == 0) {
                                        i3 = 0;
                                        break;
                                    }
                                    i31++;
                                }
                                if (i3 != 0) {
                                    int a4 = (int) (70 * sceneView2.tracks.getChangeAlpha());
                                    i2 = 0;
                                    telemetry2 = telemetry;
                                    ribbon$default(this, bx, by, null, sceneView2.tracks.getLaneWidth() * 0.4d, Companion.withAlpha(Color.rgb(0, 176, 220), a4), 0, 0, 0.07f, new Function1() { // from class: com.starpilot.starview.SceneView$$ExternalSyntheticLambda17
                                        @Override // kotlin.jvm.functions.Function1
                                        public final Object invoke(Object obj) {
                                            float rebuildRibbons$lambda$103;
                                            rebuildRibbons$lambda$103 = SceneView.rebuildRibbons$lambda$103(bx, ((Integer) obj).intValue());
                                            return Float.valueOf(rebuildRibbons$lambda$103);
                                        }
                                    }, 96, null);
                                } else {
                                    i2 = 0;
                                    telemetry2 = telemetry;
                                }
                                if (telemetry2.s("model_ui")) {
                                    if (latActive) {
                                        state2 = state;
                                        rgb = state2 == LaneTracks.PathState.OVERRIDE ? Color.rgb(138, 143, 150) : sceneView2.uiStatus == UiStatus.EXPERIMENTAL ? p.getPathExp() : p.getPathOn();
                                    } else {
                                        rgb = p.getPathOff();
                                        state2 = state;
                                    }
                                    int color2 = rgb;
                                    double half3 = RangesKt.coerceIn(telemetry2.pFloat("PathWidth", 1.0f) * 0.5d, 0.3d, 1.2d);
                                    int n2 = m.getPosX().length;
                                    long nowNs = System.nanoTime();
                                    float dt = sceneView2.pulseLastNs == 0 ? f : RangesKt.coerceIn((nowNs - sceneView2.pulseLastNs) / 1.0E9f, f, 0.2f);
                                    sceneView2.pulseLastNs = nowNs;
                                    sceneView2.pulseAccel += (dt / (dt + 0.25f)) * (t.getAEgo() - sceneView2.pulseAccel);
                                    final float broken = telemetry2.s("path_pulse") ? PathPulse.INSTANCE.broken(sceneView2.pulseAccel) : f;
                                    sceneView2.pulsePhase = PathPulse.INSTANCE.advance(sceneView2.pulsePhase, sceneView2.pulseAccel, broken, dt);
                                    float f3 = 0.01f;
                                    if (broken < 0.01f) {
                                        n = n2;
                                        color = color2;
                                    } else if (n2 < 2) {
                                        n = n2;
                                        color = color2;
                                    } else {
                                        float end = Math.min(120.0f, ArraysKt.last(m.getPosX()));
                                        int cnt = Math.max(2, ((int) (end / 0.5f)) + 1);
                                        final float[] px = new float[cnt];
                                        for (int i32 = i2; i32 < cnt; i32++) {
                                            px[i32] = i32 * 0.5f;
                                        }
                                        float[] py = new float[cnt];
                                        int i33 = i2;
                                        while (i33 < cnt) {
                                            float x3 = px[i33];
                                            int j6 = 1;
                                            while (j6 < n2 - 1 && m.getPosX()[j6] < x3) {
                                                j6++;
                                            }
                                            float f4 = RangesKt.coerceIn((x3 - m.getPosX()[j6 - 1]) / Math.max(f3, m.getPosX()[j6] - m.getPosX()[j6 - 1]), 0.0f, 1.0f);
                                            py[i33] = m.getPosY()[j6 - 1] + ((m.getPosY()[j6] - m.getPosY()[j6 - 1]) * f4);
                                            i33++;
                                            f3 = 0.01f;
                                        }
                                        final float far = Math.max(1.0f, ArraysKt.last(m.getPosX()));
                                        ribbon$default(this, px, py, null, half3, Companion.withAlpha(color2, 235), 0, 0, 0.09f, new Function1() { // from class: com.starpilot.starview.SceneView$$ExternalSyntheticLambda1
                                            @Override // kotlin.jvm.functions.Function1
                                            public final Object invoke(Object obj) {
                                                float rebuildRibbons$lambda$105;
                                                rebuildRibbons$lambda$105 = SceneView.rebuildRibbons$lambda$105(px, far, sceneView2, broken, ((Integer) obj).intValue());
                                                return Float.valueOf(rebuildRibbons$lambda$105);
                                            }
                                        }, 96, null);
                                    }
                                    final int n3 = n;
                                    ribbon$default(this, m.getPosX(), m.getPosY(), null, half3, Companion.withAlpha(color, 235), 0, 0, 0.09f, new Function1() { // from class: com.starpilot.starview.SceneView$$ExternalSyntheticLambda18
                                        @Override // kotlin.jvm.functions.Function1
                                        public final Object invoke(Object obj) {
                                            float rebuildRibbons$lambda$104;
                                            rebuildRibbons$lambda$104 = SceneView.rebuildRibbons$lambda$104(n3, ((Integer) obj).intValue());
                                            return Float.valueOf(rebuildRibbons$lambda$104);
                                        }
                                    }, 96, null);
                                }
                                endRibbons();
                            }
                        }
                        i2 = 0;
                        telemetry2 = telemetry;
                        if (telemetry2.s("model_ui")) {
                        }
                        endRibbons();
                    }
                } else {
                    telemetry = t;
                    str3 = str2;
                }
                f = 0.0f;
                if (telemetry.s(str3)) {
                }
                sceneView2 = this;
                i2 = 0;
                telemetry2 = telemetry;
                if (telemetry2.s("model_ui")) {
                }
                endRibbons();
            }
        }
        mapped = false;
        sceneView5.cleanRoad(m, t.getVEgo(), mapped);
        sceneView5.layoutHideRoad = mapped;
        LaneTracks.PathState state42 = sceneView5.pathStateNow;
        int i152 = 220;
        String str82 = "lane_lines";
        int i162 = 2;
        if (t.s("lane_lines")) {
        }
        mem = sceneView.laneMemory;
        if (mem == null) {
        }
        f = 0.0f;
        if (telemetry.s(str3)) {
        }
        sceneView2 = this;
        i2 = 0;
        telemetry2 = telemetry;
        if (telemetry2.s("model_ui")) {
        }
        endRibbons();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final float rebuildRibbons$lambda$95(float[] $lx, int q) {
        return 1.0f - RangesKt.coerceIn(($lx[q] - 85.0f) / 25.0f, 0.0f, 1.0f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final float rebuildRibbons$lambda$100(float[] $rx, int q) {
        return 1.0f - RangesKt.coerceIn($rx[q] / 70.0f, 0.0f, 1.0f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final float[] rebuildRibbons$lambda$101(SceneView this$0, LaneTracks.Line l) {
        Intrinsics.checkNotNullParameter(l, "l");
        return this$0.shownY(l);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final float rebuildRibbons$lambda$103(float[] $bx, int q) {
        return 1.0f - RangesKt.coerceIn($bx[q] / 60.0f, 0.0f, 1.0f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final float rebuildRibbons$lambda$104(int $n, int i) {
        return 1.0f - ((i / Math.max(1, $n - 1)) * 0.75f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final float rebuildRibbons$lambda$105(float[] $px, float $far, SceneView this$0, float $broken, int q) {
        return (1.0f - (($px[q] / $far) * 0.75f)) * PathPulse.INSTANCE.alpha($px[q], this$0.pulsePhase, $broken);
    }

    /* compiled from: SceneView.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final int withAlpha(int c, int a) {
            return Color.argb(RangesKt.coerceIn(a, 0, 255), Color.red(c), Color.green(c), Color.blue(c));
        }

        private static final float linear$ch(int v) {
            float s = v / 255.0f;
            return s <= 0.04045f ? s / 12.92f : (float) Math.pow((0.055f + s) / 1.055f, 2.4d);
        }

        public final Triple<Float, Float, Float> linear(int c) {
            return new Triple<>(Float.valueOf(linear$ch(Color.red(c))), Float.valueOf(linear$ch(Color.green(c))), Float.valueOf(linear$ch(Color.blue(c))));
        }
    }
}
