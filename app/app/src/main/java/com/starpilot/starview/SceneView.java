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
import android.view.Choreographer.FrameCallback;
import android.view.GestureDetector.OnGestureListener;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;
import java.util.Map.Entry;

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

    /* VF: from Vineflower (jadx: Type inference failed) */
    public SceneView(Context var1, AttributeSet var2) {
        super(var1, var2);
        Intrinsics.checkNotNullParameter(var1, "ctx");
        this.uiStatus = UiStatus.DISENGAGED;
        this.distance = 26.0F;
        this.tiltDeg = 17.0F;
        this.fovDeg = 36.0;
        int var7 = Color.rgb(244, 245, 247);
        int var8 = Color.rgb(180, 185, 194);
        int var6 = Color.rgb(224, 176, 42);
        int var9 = Color.rgb(229, 59, 59);
        int var3 = Color.rgb(59, 139, 245);
        int var4 = Color.rgb(185, 191, 202);
        int var10 = Color.rgb(240, 138, 46);
        int var5 = Color.rgb(154, 159, 168);
        Integer var19 = 3;
        this.day = new SceneView.Palette(var7, var8, var6, var9, var3, var4, var10, var5, 2.6F, 1.0F, new float[]{1.0F, 0.98F, 0.95F});
        this.nightP = new SceneView.Palette(
            Color.rgb(11, 13, 18),
            Color.rgb(107, 114, 126),
            Color.rgb(201, 154, 30),
            Color.rgb(214, 51, 51),
            Color.rgb(59, 139, 245),
            Color.rgb(74, 80, 91),
            Color.rgb(224, 124, 34),
            Color.rgb(122, 128, 139),
            0.9F,
            0.35F,
            new float[]{0.75F, 0.8F, 1.0F}
        );
        this.alwaysDark = true;
        this.paletteDirty = true;
        this.twoFingerY = Float.NaN;
        this.scale = new ScaleGestureDetector(var1, (OnScaleGestureListener)(new SimpleOnScaleGestureListener() {
            final SceneView this$0 = SceneView.this;

            public boolean onScale(ScaleGestureDetector var1) {
                Intrinsics.checkNotNullParameter(var1, "d");
                this.this$0.setDistance(this.this$0.getDistance() / var1.getScaleFactor());
                this.this$0.gesturing = true;
                return true;
            }
        }));
        this.taps = new GestureDetector(var1, (OnGestureListener)(new SimpleOnGestureListener() {
            final SceneView this$0 = SceneView.this;

            public boolean onDoubleTap(MotionEvent var1) {
                Intrinsics.checkNotNullParameter(var1, "e");
                this.this$0.setDistance(26.0F);
                this.this$0.setTiltDeg(17.0F);
                Function2 var2x = this.this$0.getOnViewChanged();
                if (var2x != null) {
                    var2x.invoke(this.this$0.getDistance(), this.this$0.getTiltDeg());
                }

                return true;
            }

            public boolean onDown(MotionEvent var1) {
                Intrinsics.checkNotNullParameter(var1, "e");
                return true;
            }

            public boolean onSingleTapConfirmed(MotionEvent var1) {
                Intrinsics.checkNotNullParameter(var1, "e");
                Function0 var2x = this.this$0.getOnTap();
                if (var2x != null) {
                    var2x.invoke();
                }

                return true;
            }
        }));
        this.slop = ViewConfiguration.get(var1).getScaledTouchSlop();
        this.longPressRun = new SceneView$$ExternalSyntheticLambda0(this);
        this.maxVerts = 65536;
        this.maxIdx = this.maxVerts * 3;
        Integer var12 = 2;
        ByteBuffer[] var11 = new ByteBuffer[2];

        for (int var20 = 0; var20 < 2; var20++) {
            var11[var20] = ByteBuffer.allocateDirect(this.maxVerts * 16).order(ByteOrder.nativeOrder());
        }

        this.vbufs = var11;
        var11 = new ByteBuffer[2];

        for (int var21 = 0; var21 < 2; var21++) {
            var11[var21] = ByteBuffer.allocateDirect(this.maxIdx * 4).order(ByteOrder.nativeOrder());
        }

        this.ibufs = var11;
        this.lastRibbonFrame = -2L;
        this.genericPool = new ArrayList<>();
        this.genericKinds = new String[]{"car_sedan.glb", "car_suv.glb", "car_hatch.glb", "car_coupe.glb"};
        this.poolPerKind = 7;
        this.objectKinds = CollectionsKt.listOf(
            new Pair[]{
                TuplesKt.to("truck.glb", 4),
                TuplesKt.to("bus.glb", var12),
                TuplesKt.to("person.glb", 8),
                TuplesKt.to("bicycle.glb", var19),
                TuplesKt.to("motorcycle.glb", var12),
                TuplesKt.to("traffic_light.glb", var19),
                TuplesKt.to("stop_sign.glb", var19)
            }
        );
        this.traffic = new Traffic();
        Utils.init();
        this.failure = "";
        this.groundBuffers = new ArrayList<>();
        this.showTraffic = true;
        this.hintChain = SetsKt.emptySet();
        this.hintSame = SetsKt.emptySet();
        this.layoutDark = true;
        this.MR = 100;
        this.XMIN = -10.0F;
        this.snaps = new ArrayDeque<>();
        this.memGrp = new byte[this.MR * 80];
        this.memAge = new float[this.MR * 80];
        this.memScore = new float[this.MR * 80];
        ByteBuffer var13 = ByteBuffer.allocateDirect(this.MR * 80 * 4).order(ByteOrder.nativeOrder());
        Intrinsics.checkNotNullExpressionValue(var13, "order(...)");
        this.texBuf = var13;
        this.smoothTmp = new byte[this.MR * 80];
        this.frameCallback = new FrameCallback() {
            final SceneView this$0 = SceneView.this;

            public void doFrame(long var1) {
                if (this.this$0.running) {
                    Choreographer.getInstance().postFrameCallback(this);
                    if (this.this$0.ready) {
                        UiHelper var4x = this.this$0.uiHelper;
                        Object var5x = null;
                        UiHelper var3x = var4x;
                        if (var4x == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("uiHelper");
                            var3x = null;
                        }

                        if (var3x.isReadyToRender()) {
                            SwapChain var6x = this.this$0.swapChain;
                            if (var6x == null) {
                                return;
                            }

                            this.this$0.updateScene(var1);
                            Renderer var10x = this.this$0.renderer;
                            Renderer var7x = var10x;
                            if (var10x == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("renderer");
                                var7x = null;
                            }

                            if (var7x.beginFrame(var6x, var1)) {
                                Renderer var11x = this.this$0.renderer;
                                Renderer var8x = var11x;
                                if (var11x == null) {
                                    Intrinsics.throwUninitializedPropertyAccessException("renderer");
                                    var8x = null;
                                }

                                View var13x = this.this$0.view;
                                View var12x = var13x;
                                if (var13x == null) {
                                    Intrinsics.throwUninitializedPropertyAccessException("view");
                                    var12x = null;
                                }

                                var8x.render(var12x);
                                Renderer var9x = this.this$0.renderer;
                                if (var9x == null) {
                                    Intrinsics.throwUninitializedPropertyAccessException("renderer");
                                    var9x = (Renderer)var5x;
                                }

                                var9x.endFrame();
                            }

                            return;
                        }
                    }
                }
            }
        };
        this.leadGap = 99.0;
        this.pathStateNow = LaneTracks.PathState.OFF;
        this.tracks = new LaneTracks(0.0F, 1, null);
        float[][] var14 = new float[2][];

        for (int var22 = 0; var22 < 2; var22++) {
            var14[var22] = new float[0];
        }

        this.edgeS = var14;
        float[][] var15 = new float[2][];

        for (int var23 = 0; var23 < 2; var23++) {
            var15[var23] = new float[0];
        }

        this.sideA = var15;
        float[][] var16 = new float[2][];

        for (int var24 = 0; var24 < 2; var24++) {
            var16[var24] = new float[0];
        }

        this.sideG = var16;
        this.SIDE_W = 3.0;
        this.parked = new ArrayDeque<>();
        double[][] var17 = new double[2][];

        for (int var25 = 0; var25 < 2; var25++) {
            var17[var25] = new double[0];
        }

        this.curbS = var17;
        this.curbHold = new int[2];
        this.curbFit = new boolean[2];
        this.curbOffS = new double[2];
        float[][] var18 = new float[2][];

        for (int var26 = 0; var26 < 2; var26++) {
            var18[var26] = new float[0];
        }

        this.edgeF = var18;
        this.shapeC = new double[3];
        this.shapeOff = new double[2];
        this.mapAlign = new OsmRoads.Align();
        this.gpsLag = 0.2;
        this.yawSrc = "steering";
        this.mapStatus = "";
        this.mx = new double[512];
        this.my = new double[512];
        this.shiftJumps = -1;
        this.learnedW = new HashMap<>();
        this.MAP_R = 140.0;
        this.lastEdgeX = new float[0];
        this.laneW = 3.6;
        this.baseLanesMem = new HashMap<>();
        this.laneVote = -1;
        this.laneIdx = -1;
        this.TAPER = 45.0;
        this.BRIDGE_H = 5.5;
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
            IndirectLight it = kTX1Loader.createIndirectLight(engine, readAsset("ibl/day_ibl.ktx"), new KTX1Loader.Options()).getIndirectLight();
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

    /* VF: from Vineflower (jadx: Code restructure failed;Removed duplicated region;incorrect) */
    public final void updateScene(long var1) {
        Object var141 = null; List var88 = null; // declarations Vineflower left out
        double var3;
        if (this.lastFrameNs == 0L) {
            var3 = 0.016666666666666666;
        } else {
            var3 = RangesKt.coerceIn((var1 - this.lastFrameNs) / 1.0E9, 0.001, 0.1);
        }

        this.lastFrameNs = var1;
        if (this.paletteDirty) {
            this.applyPalette();
        }

        Telemetry var35 = this.t;
        if (var35 != null) {
            this.updateLayout(var1, var35);
            this.setupCamera(var35);
            ModelSnapshot var36 = var35.getModel();
            boolean var26 = var35.getStarted();
            boolean var23;
            if (!var35.getEnabled() && !var35.getAolEnabled()) {
                var23 = false;
            } else {
                var23 = true;
            }

            if (var36 != null && var26) {
                if (var36.getFrameId() != this.lastRibbonFrame || this.uiStatus != this.lastRibbonStatus || var23 != this.lastRibbonEnabled) {
                    this.lastRibbonFrame = var36.getFrameId();
                    this.lastRibbonStatus = this.uiStatus;
                    this.lastRibbonEnabled = var23;
                    this.rebuildRibbons(var36, var35, var23);
                }
            } else if (this.lastRibbonFrame != -1L) {
                this.lastRibbonFrame = -1L;
                this.clearRibbons();
            }

            var1 = System.currentTimeMillis();
            boolean var24;
            if (var1 / 400 % 2 == 0L) {
                var24 = true;
            } else {
                var24 = false;
            }

            double var5 = 1;
            double var7 = var5 - Math.exp(-var3 / 0.09);
            SceneView.Car var29 = this.ego;
            if (var29 != null) {
                var29.place(2.3 - var29.getLength() / 2, 0.0, 0.0);
                boolean var25;
                if (!var35.getBrakePressed() && !var35.getBrakeLights()) {
                    var25 = false;
                } else {
                    var25 = true;
                }

                var29.lights(var25, this.night, var35.getLeftBlinker(), var35.getRightBlinker(), var24);
                Unit var84 = Unit.INSTANCE;
                Unit var85 = Unit.INSTANCE;
            }

            List var86;
            if (var26 && this.showTraffic) {
                var86 = this.traffic.update(var35, var1, var35.s("radar_tracks"), true);
            } else {
                var86 = CollectionsKt.emptyList();
            }

            List var40;
            if (var26 && this.showTraffic && var35.s("car_memory")) {
                Collection var31;
                label557: {
                    var31 = var86;
                    CarMemory var87 = this.carMemory;
                    if (var87 != null) {
                        List var30 = var87.getGhosts();
                        var88 = var30;
                        if (var30 != null) {
                            break label557;
                        }
                    }

                    var88 = CollectionsKt.emptyList();
                }

                Iterable var89 = var88;
                var24 = false;
                var var32 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var89, 10));

                for (CarMemory.Car var34 : var89) {
                    Traffic.Vehicle var33 = new Traffic.Vehicle(-1000000L - var34.getId());
                    var33.setX(var34.getX());
                    var33.setY(var34.getY());
                    var33.setVRel(var34.getVRel());
                    var33.setVAbs(var34.getVAbs());
                    var33.setRemembered(true);
                    var33.setKind((int)(var34.getId() & 3L));
                    var32.add(var33);
                }

                List var90 = var32;
                var40 = CollectionsKt.plus(var31, var90);
            } else {
                var40 = var86;
            }

            Detector var39 = this.detector;
            int var21;
            if (var39 != null && var39.isRunning() && var1 - var39.getObjectsAtMs() < 700L) {
                var21 = 1;
            } else {
                var21 = 0;
            }

            List var38;
            if (var26 && var21 && this.showTraffic) {
                Intrinsics.checkNotNull(var39);
                var38 = var39.getObjects();
            } else {
                var38 = CollectionsKt.emptyList();
            }

            float var19;
            if (var39 != null) {
                var19 = var39.getFovTan();
            } else {
                var19 = 0.5F;
            }

            HashSet var108 = new HashSet();
            HashSet var112 = new HashSet();
            SceneView$$ExternalSyntheticLambda13 var91 = new SceneView$$ExternalSyntheticLambda13(var36, this);
            double var9 = var5 - Math.exp(-var3 / 0.35);
            Iterator var98 = var40.iterator();

            while (var98.hasNext()) {
                if (((Traffic.Vehicle)var98.next()).getVAbs() < -3.0F) {
                    this.oncomingSeenMs = var1;
                }
            }

            for (Detector.Obj var99 : var38) {
                if (updateScene$vehicle(var99.getCls()) && var99.getVelValid() && var99.getVx() < -3.0F) {
                    this.oncomingSeenMs = var1;
                }
            }

            int var20;
            label522: {
                LaneMarks var100 = this.laneMarks;
                if (var100 != null) {
                    LaneMarks.Mark[] var124 = var100.getMarks();
                    if (var124 != null) {
                        Iterable var101 = new IntRange(0, 1);
                        if (!(var101 instanceof Collection) || !((Collection)var101).isEmpty()) {
                            Iterator var129 = var101.iterator();

                            while (var129.hasNext()) {
                                var20 = ((IntIterator)var129).nextInt();
                                boolean var65;
                                if (!var124[var20].getYellow() || var124[var20].getKind() != 3 && var124[var20].getKind() != 2) {
                                    var65 = false;
                                } else {
                                    var65 = true;
                                }

                                if (var65) {
                                    var20 = 1;
                                    break label522;
                                }
                            }

                            var20 = 0;
                            break label522;
                        }

                        var20 = 0;
                        break label522;
                    }
                }

                var20 = 0;
            }

            if (!var20 && var1 - this.oncomingSeenMs >= 60000L) {
                var24 = false;
            } else {
                var24 = true;
            }

            if (var35.getVEgo() > 3.0F) {
                boolean var79 = true;
            } else {
                boolean var80 = false;
            }

            ArrayList var125 = new ArrayList();
            Iterator var43 = var40.iterator();
            var20 = 0;
            SceneView$$ExternalSyntheticLambda13 var102 = var91;
            HashSet var92 = var112;

            while (true) {
                boolean var81 = var43.hasNext();
                String var130 = "truck.glb";
                String var37 = "bus.glb";
                if (!var81) {
                    var130 = "bus.glb";
                    HashSet var138 = var92;
                    String var142 = "truck.glb";
                    ArrayList var93 = var125;
                    SceneView var109 = this;
                    Iterator var139 = var38.iterator();
                    SceneView$$ExternalSyntheticLambda13 var140 = var102;
                    String var126 = var142;
                    HashSet var103 = var108;

                    while (var139.hasNext()) {
                        Detector.Obj var146 = (Detector.Obj)var139.next();
                        if (!var138.contains(var146.getId())) {
                            if (updateScene$vehicle(var146.getCls())) {
                                var var117 = var40;
                                var23 = false;
                                if (var117 instanceof Collection && ((Collection)var117).isEmpty()) {
                                    var21 = (((boolean)false) ? 1 : 0);
                                } else {
                                    Iterator var118 = var117.iterator();

                                    while (true) {
                                        if (!var118.hasNext()) {
                                            var21 = (((boolean)false) ? 1 : 0);
                                            break;
                                        }

                                        label422: {
                                            label421: {
                                                Traffic.Vehicle var143 = (Traffic.Vehicle)var118.next();
                                                if (!updateScene$sameCar(var146.getX(), var146.getY(), var143.getX(), var143.getY())
                                                    && (
                                                        !(Math.abs(var146.getX() - var143.getX()) < var143.getX() * 0.2 + 4.0)
                                                            || !(Math.abs(var146.getY() - var143.getY()) < var143.getX() * 0.06 + 2.2)
                                                    )) {
                                                    if (!var143.getLead() || !(Math.abs(var146.getY() - var143.getY()) < 1.8)) {
                                                        break label421;
                                                    }

                                                    var5 = var146.getX() - var143.getX();
                                                    if (-3.0 <= var5 && var5 <= 8.0) {
                                                        var21 = (((boolean)true) ? 1 : 0);
                                                    } else {
                                                        var21 = (((boolean)false) ? 1 : 0);
                                                    }

                                                    if (!var21) {
                                                        break label421;
                                                    }
                                                }

                                                var21 = (((boolean)true) ? 1 : 0);
                                                break label422;
                                            }

                                            var21 = (((boolean)false) ? 1 : 0);
                                        }

                                        if ((var21 != 0)) {
                                            var21 = (((boolean)true) ? 1 : 0);
                                            break;
                                        }
                                    }
                                }

                                if ((var21 != 0)) {
                                    continue;
                                }
                            }

                            String var119;
                            switch (var146.getCls()) {
                                case 0:
                                    var119 = "person.glb";
                                    break;
                                case 1:
                                    var119 = "bicycle.glb";
                                    break;
                                case 2:
                                    var119 = var109.genericKinds[(int)(var146.getId() & 3L)];
                                    break;
                                case 3:
                                    var119 = "motorcycle.glb";
                                    break;
                                case 4:
                                case 6:
                                case 8:
                                case 10:
                                default:
                                    continue;
                                case 5:
                                    var119 = var130;
                                    break;
                                case 7:
                                    var119 = var126;
                                    break;
                                case 9:
                                    var119 = "traffic_light.glb";
                                    break;
                                case 11:
                                    var119 = "stop_sign.glb";
                            }

                            if (updateScene$vehicle(var146.getCls())) {
                                if (updateScene$occupied(var93, var146.getCx(), var146.getCy(), updateScene$halfLenOf(var119))) {
                                    continue;
                                }

                                var93.add(new double[]{var146.getCx(), var146.getCy(), updateScene$halfLenOf(var119)});
                            }

                            SceneView.Car var110;
                            if (updateScene$vehicle(var146.getCls())) {
                                var110 = updateScene$slotNear(this, var103, var1, -var146.getId(), var119, var146.getCx(), var146.getCy());
                            } else {
                                var110 = null;
                            }

                            SceneView.Car var144 = var110;
                            if (var110 == null) {
                                SceneView.Car var111 = this.slotFor(-var146.getId(), var119, var1);
                                var144 = var111;
                                if (var111 == null) {
                                    var109 = this;
                                    continue;
                                }
                            }

                            var103.add(var144);
                            double var59 = var146.getX();
                            double var57 = var146.getY();
                            var5 = var146.getHeading();
                            if (updateScene$vehicle(var146.getCls())) {
                                var5 = updateScene$vehHeading(var35, var140, var24, this, var36, var146, var59, Float.NaN, var57);
                                double var61 = Math.atan2(var57, var59);
                                double var17 = var5 - var61;
                                var17 = (var144.getLength() * Math.abs(Math.cos(var17)) + Math.abs(Math.sin(var17)) * 1.9) / 2;
                                if ((var146.getMoving() || var146.getVelValid() && !(Math.abs(var146.getVx()) < 1.5F))
                                    && !updateScene$atPictureEdge(var59, var57)
                                    && !updateScene$inParking(this, var36, var59, var57)) {
                                    var23 = false;
                                } else {
                                    var23 = true;
                                }

                                var59 = Math.cos(var61) * var17 + var59;
                                updateScene$move(
                                    this,
                                    var3,
                                    var7,
                                    var1,
                                    var9,
                                    var144,
                                    var59,
                                    updateScene$snapY(this, var36, var59, Math.sin(var61) * var17 + var57, var23),
                                    var5,
                                    var23
                                );
                            } else if (var146.getCls() == 1 || var146.getCls() == 3) {
                                double var62 = var144.getLength() / 2;
                                if (!var146.getMoving()) {
                                    var5 = ((Number)var140.invoke(var59)).doubleValue();
                                }

                                updateScene$move$default(this, var3, var7, var1, var9, var144, var59 + var62, var57, var5, false, 512, null);
                            } else if (var146.getCls() == 0) {
                                if (!var146.getMoving()) {
                                    var5 = Math.atan2(var57, var59) + Math.PI;
                                }

                                updateScene$move$default(this, var3, var7, var1, var9, var144, var59, var57, var5, false, 512, null);
                            } else if (var146.getCls() != 9 && var146.getCls() != 11) {
                                updateScene$move$default(this, var3, var7, var1, var9, var144, var59, var57, 0.0, false, 512, null);
                            } else {
                                updateScene$move$default(this, var3, var7, var1, var9, var144, var59, var57, Math.PI, false, 512, null);
                            }

                            if (var146.getCls() == 9) {
                                var144.lamp(var146.getLamp());
                            }

                            var144.remembered(false);
                            var144.lights(false, this.night, false, false, false);
                            var144.show(true);
                            var109 = this;
                            var20++;
                        }
                    }

                    Iterator var120 = var109.genericPool.iterator();
                    Intrinsics.checkNotNullExpressionValue(var120, "iterator(...)");

                    while (var120.hasNext()) {
                        SceneView.Car var133 = (SceneView.Car)var120.next();
                        Intrinsics.checkNotNullExpressionValue(var133, "next(...)");
                        var133 = var133;
                        if (!var103.contains(var133) && var133.getInScene()) {
                            if (!Double.isNaN(var133.getWx()) && var1 - var133.getLastUsed() < 30000L) {
                                Pair var136 = var109.fromWorld(var133.getWx(), var133.getWy());
                                var3 = ((Number)var136.component1()).doubleValue();
                                var5 = ((Number)var136.component2()).doubleValue();
                                if (var3 > -10.0) {
                                    var133.setX(var3);
                                    var133.setY(var5);
                                    var133.place(var3, var5, var133.getHeading());
                                    var93.add(new double[]{var3, var5, updateScene$halfLenOf(var133.getKind())});
                                    continue;
                                }
                            }

                            if (var1 - var133.getLastUsed() > 700L) {
                                var133.show(false);
                                var133.setId(Long.MIN_VALUE);
                                var133.setX(Double.NaN);
                                var133.setWx(Double.NaN);
                                var133.setFlipSince(0L);
                            }
                        }
                    }

                    Iterable var104 = var109.genericPool;
                    var var94 = new ArrayList();

                    for (Object var121 : var104) {
                        SceneView.Car var135 = (SceneView.Car)var121;
                        if (!var135.getInScene()
                            || Double.isNaN(var135.getX())
                            || !StringsKt.startsWith(var135.getKind(), "car_", false)
                                && !Intrinsics.areEqual(var135.getKind(), var126)
                                && !Intrinsics.areEqual(var135.getKind(), var130)) {
                            var21 = (((boolean)false) ? 1 : 0);
                        } else {
                            var21 = (((boolean)true) ? 1 : 0);
                        }

                        if ((var21 != 0)) {
                            var94.add(var121);
                        }
                    }

                    List var95 = var94;
                    List var106 = CollectionsKt.sortedWith(var95, new Comparator() {
                        @Override
                        public final int compare(T var1, T var2) {
                            return ComparisonsKt.compareValues(((SceneView.Car)var1).getFirstShownMs(), ((SceneView.Car)var2).getFirstShownMs());
                        }
                    });
                    ArrayList var96 = new ArrayList();
                    Iterator var122 = var106.iterator();
                    var21 = var20;

                    while (var122.hasNext()) {
                        SceneView.Car var107 = (SceneView.Car)var122.next();
                        var3 = updateScene$halfLenOf(var107.getKind());
                        var125 = var96;
                        boolean var68;
                        if (var125 instanceof Collection && var125.isEmpty()) {
                            var68 = false;
                        } else {
                            Iterator var132 = var125.iterator();

                            while (true) {
                                if (!var132.hasNext()) {
                                    var68 = false;
                                    break;
                                }

                                SceneView.Car var128 = (SceneView.Car)var132.next();
                                if (Math.abs(var128.getX() - var107.getX()) < (var3 + updateScene$halfLenOf(var128.getKind())) * 0.92
                                    && Math.abs(var128.getY() - var107.getY()) < 1.7) {
                                    var68 = true;
                                } else {
                                    var68 = false;
                                }

                                if (var68) {
                                    var68 = true;
                                    break;
                                }
                            }
                        }

                        if (var68) {
                            var107.show(false);
                            var107.setX(Double.NaN);
                            var107.setWx(Double.NaN);
                            var107.setId(Long.MIN_VALUE);
                            var21--;
                        } else {
                            var96.add(var107);
                        }
                    }

                    var109.shownCars = (((var21 != 0)) ? 1 : 0);
                    return;
                }

                Traffic.Vehicle var44 = (Traffic.Vehicle)var43.next();
                var112 = null;
                Iterator var41 = var38.iterator();

                while (true) {
                    if (!var41.hasNext()) {
                        var141 = var112;
                        break;
                    }

                    Detector.Obj var42 = (Detector.Obj)var41.next();
                    if (updateScene$vehicle(var42.getCls())
                        && !var92.contains(var42.getId())
                        && updateScene$sameCar(var42.getX(), var42.getY(), var44.getX(), var44.getY())) {
                        var141 = var42;
                        break;
                    }
                }

                if (var141 != null) {
                    var92.add(var141.getId());
                } else if (var21
                    && !var44.getLead()
                    && Math.abs(var44.getVAbs()) < 2.0F
                    && var44.getX() < 70.0
                    && Math.abs(var44.getY()) < var44.getX() * var19 * 0.9) {
                    continue;
                }

                boolean var22;
                if (var141 != null && var141.getCls() == 7) {
                    var22 = true;
                } else {
                    var22 = false;
                }

                String var114;
                if (var22) {
                    var114 = var130;
                } else {
                    if (var141 != null && var141.getCls() == 5) {
                        var22 = true;
                    } else {
                        var22 = false;
                    }

                    if (var22 && var44.getX() > 8.0) {
                        var114 = var37;
                    } else {
                        var114 = this.genericKinds[var44.getKind() & 3];
                    }
                }

                if (var141 != null) {
                    var5 = var141.getY();
                } else {
                    var5 = var44.getY();
                }

                if (var44.getLead() || !updateScene$occupied(var125, var44.getX() + 2.3, var5, updateScene$halfLenOf(var114))) {
                    var125.add(new double[]{var44.getX() + updateScene$halfLenOf(var114), var5, updateScene$halfLenOf(var114)});
                    long var27 = var44.getId();
                    double var11 = var44.getX();
                    SceneView.Car var145 = updateScene$slotNear(this, var108, var1, var27, var114, var11 + 2.3, var5);
                    SceneView.Car var137 = var145;
                    if (var145 == null) {
                        SceneView.Car var115 = this.slotFor(var44.getId(), var114, var1);
                        var137 = var115;
                        if (var115 == null) {
                            continue;
                        }
                    }

                    var108.add(var137);
                    if (var44.getLead() ? !(Math.abs(var44.getVAbs()) < 0.8F) || !(var35.getVEgo() < 0.5F) : !(Math.abs(var44.getVAbs()) < 1.5F)) {
                        var81 = false;
                    } else {
                        var81 = true;
                    }

                    var11 = var44.getX();
                    if (var141 != null) {
                        var5 = var141.getY();
                    } else {
                        var5 = var44.getY();
                    }

                    var5 = updateScene$snapY(this, var36, var11, var5, var81);
                    var11 = updateScene$vehHeading(var35, var102, var24, this, var36, var141, var44.getX(), var44.getVAbs(), var5);
                    double var13 = var11 - Math.atan2(var5, var44.getX());
                    double var15 = (var137.getLength() * Math.abs(Math.cos(var13)) + Math.abs(Math.sin(var13)) * 1.9) / 2;
                    var13 = var44.getX();
                    if (var81 && var35.getVEgo() < 25.0F) {
                        var81 = true;
                    } else {
                        var81 = false;
                    }

                    updateScene$move(this, var3, var7, var1, var9, var137, var13 + var15, var5, var11, var81);
                    var137.remembered(var44.getRemembered());
                    var137.lights(var44.getBrake(), this.night, false, false, false);
                    var137.show(true);
                    var20++;
                    var92 = var92;
                }
            }
        }
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
                    if (StringsKt.startsWith(c2.getKind(), "car_", false)) {
                        if (!StringsKt.startsWith(kindName, "car_", false)) {
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
                boolean vehicleKind = StringsKt.startsWith(kindName, "car_", false) || Intrinsics.areEqual(kindName, "truck.glb") || Intrinsics.areEqual(kindName, "bus.glb");
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
                            if (StringsKt.startsWith(c2.getKind(), "car_", false)) {
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
                                    if (!StringsKt.startsWith(c3.getKind(), "car_", false)) {
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

    /* VF: from Vineflower (jadx: Code restructure failed;Removed duplicated region;incorrect) */
    private final void setupCamera(Telemetry var1) {
        long var11 = System.currentTimeMillis();
        if (var1 != null) {
            Lead var14 = var1.getLeadOne();
            if (var14 != null && var14.getStatus()) {
                this.leadGap = var14.getDRel();
                this.leadGapMs = var11;
            }
        }

        double var2;
        if (var11 - this.leadGapMs < 3000L) {
            var2 = this.leadGap;
        } else {
            var2 = 99.0;
        }

        label33: {
            var2 = RangesKt.coerceIn((8.0 - var2) / 6.0, 0.0, 1.0) * 16.0;
            if (!(Math.abs(var2 - this.tiltTarget) > 4.0)) {
                boolean var10;
                if (var2 == 0.0) {
                    var10 = true;
                } else {
                    var10 = false;
                }

                if (!var10 || !(this.tiltTarget < 4.0)) {
                    break label33;
                }
            }

            this.tiltTarget = var2;
        }

        this.tiltBoost = this.tiltBoost + (this.tiltTarget - this.tiltBoost) * 0.012;
        double var8 = Math.toRadians(this.tiltDeg + this.tiltBoost);
        var2 = this.distance;
        double var4 = 0.42 * var2;
        double var6 = Math.cos(var8);
        var8 = Math.sin(var8);
        Camera var13 = this.camera;
        Camera var15 = var13;
        if (var13 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("camera");
            var15 = null;
        }

        var15.lookAt(0.0, var8 * var2 + 0.3, -(var4 - var6 * var2), 0.0, 0.0, -var4, 0.0, 1.0, 0.0);
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

    /* VF: from Vineflower (jadx: Removed duplicated region;incorrect) */
    private final void cleanRoad(ModelSnapshot var1, float var2, boolean var3) {
        if (((Object[])var1.getEdgeX()).length >= 2 && var1.getEdgeY()[0].length >= 4 && var1.getEdgeY()[1].length == var1.getEdgeY()[0].length) {
            float[] var44 = var1.getEdgeX()[0];
            int var28 = var44.length;
            this.lastEdgeX = var44;

            for (int var20 = 0; var20 < 2; var20++) {
                if (this.edgeS[var20].length != var28) {
                    float[][] var37 = this.edgeS;
                    float[] var38 = var1.getEdgeY()[var20];
                    var38 = Arrays.copyOf(var38, var38.length);
                    Intrinsics.checkNotNullExpressionValue(var38, "copyOf(...)");
                    var37[var20] = var38;
                    this.sideA[var20] = new float[var28];
                    this.sideG[var20] = new float[var28];
                    double[][] var144 = this.curbS;
                    double[] var138 = new double[var28];

                    for (int var21 = 0; var21 < var28; var21++) {
                        var138[var21] = this.edgeS[var20][var21];
                    }

                    var144[var20] = var138;
                }
            }

            float var18;
            if (var2 < 0.5F) {
                var18 = 0.0F;
            } else if (var2 < 3.0F) {
                var18 = 0.08F;
            } else {
                var18 = 0.2F;
            }

            for (int var84 = 0; var84 < var28; var84++) {
                for (int var96 = 0; var96 < 2; var96++) {
                    float[] var139 = this.edgeS[var96];
                    var139[var84] += (var1.getEdgeY()[var96][var84] - this.edgeS[var96][var84]) * var18;
                }
            }

            float[] var140 = var1.getEdgeStd();
            int var85;
            if (var140.length > 0) {
                var85 = 1;
            } else {
                var85 = 0;
            }

            if ((var85 != 0)) {
                var18 = var140[0];
            } else {
                var18 = 1.0F;
            }

            float[] var47 = var1.getEdgeStd();
            if (1 < var47.length) {
                var85 = (((boolean)true) ? 1 : 0);
            } else {
                var85 = (((boolean)false) ? 1 : 0);
            }

            float var19;
            if ((var85 != 0)) {
                var19 = var47[1];
            } else {
                var19 = 1.0F;
            }

            var18 = Math.min(var18, var19);
            this.edgeOk = this.edgeOk + (RangesKt.coerceIn((1.3F - var18) / 0.8F, 0.0F, 1.0F) - this.edgeOk) * 0.2F;
            this.fitRoadShape(var44, var28);

            for (int var87 = 0; var87 < 2; var87++) {
                if (this.edgeF[var87].length != var28) {
                    this.edgeF[var87] = new float[var28];
                }

                for (int var97 = 0; var97 < var28; var97++) {
                    float[] var48 = this.edgeF[var87];
                    if (this.shapeOk) {
                        var18 = (float)(this.shapeY(var44[var97]) + this.shapeOff[var87]);
                    } else {
                        var18 = this.edgeS[var87][var97];
                    }

                    var48[var97] = var18;
                }
            }

            double var4 = 0.0;
            var85 = 0;

            while (true) {
                double var10 = -1.0;
                if (var85 >= 2) {
                    if (var3 && (this.curbFit[0] || this.curbFit[1] || !(this.edgeOk < 0.05F))) {
                        int var27 = 1;

                        while (var27 < var28 && var44[var27] <= 110.0F) {
                            var27++;
                        }

                        float[] var54 = new float[var27 + 1];
                        var140 = new float[var27 + 1];
                        float[] var147 = new float[var27 + 1];
                        Pair var39 = cleanRoad$lr(this, 0);
                        var2 = ((Number)var39.component1()).floatValue();
                        var18 = ((Number)var39.component2()).floatValue();
                        var54[0] = -12.0F;
                        if (this.shapeOk) {
                            var4 = this.shapeY(-12.0) - this.shapeY(var44[0]);
                            var140[0] = (float)var4 + var2;
                            var147[0] = (float)var4 + var18;
                        } else {
                            var140[0] = var2;
                            var147[0] = var18;
                        }

                        Unit var148 = Unit.INSTANCE;

                        for (int var89 = 0; var89 < var27; var89++) {
                            var54[var89 + 1] = var44[var89];
                            var39 = cleanRoad$lr(this, var89);
                            var2 = ((Number)var39.component1()).floatValue();
                            var18 = ((Number)var39.component2()).floatValue();
                            var140[var89 + 1] = var2;
                            var147[var89 + 1] = var18;
                        }

                        int var22;
                        if (!this.night && !this.alwaysDark) {
                            var22 = 0;
                        } else {
                            var22 = 1;
                        }

                        if ((var22 != 0)) {
                            var85 = Color.rgb(46, 51, 60);
                        } else {
                            var85 = Color.rgb(163, 169, 178);
                        }

                        int var104 = (((var85 != 0)) ? 1 : 0);
                        short var23;
                        int var24;
                        short var91;
                        if ((var22 != 0)) {
                            var91 = 113;
                            var24 = 125;
                            var23 = 106;
                        } else {
                            var91 = 247;
                            var24 = 249;
                            var23 = 246;
                        }

                        int var29 = Color.rgb(var23, var91, var24);
                        int var116;
                        if ((var22 != 0)) {
                            var91 = 23;
                            var24 = (short)28;
                            var116 = 20;
                        } else {
                            var91 = 131;
                            var24 = (short)141;
                            var116 = 124;
                        }

                        int var36 = Color.rgb(var116, var91, var24);
                        if ((var22 != 0)) {
                            var116 = (short)75;
                            var24 = (short)86;
                            var91 = 69;
                        } else {
                            var116 = (short)215;
                            var24 = (short)221;
                            var91 = 211;
                        }

                        int var35 = Color.rgb(var91, var116, var24);
                        int var25;
                        if ((var22 != 0)) {
                            var25 = Color.rgb(38, 64, 46);
                        } else {
                            var25 = Color.rgb(191, 219, 178);
                        }

                        boolean[] var150 = this.curbFit;
                        byte var94 = 0;
                        if (!var150[0] && !this.curbFit[1]) {
                            var2 = this.edgeOk;
                        } else {
                            var2 = 1.0F;
                        }

                        byte var26 = 1;
                        SceneView$$ExternalSyntheticLambda11 var43 = new SceneView$$ExternalSyntheticLambda11(var2, var54);
                        var22 = Companion.withAlpha((((var85 != 0)) ? 1 : 0), 255);
                        this.fillBetween(var54, var140, var147, (((var22 != 0)) ? 1 : 0), 0.02F, var43);
                        var24 = 0;
                        var116 = var29;

                        for (var22 = var28; var24 < 2; var94 = var94) {
                            if (var24 == 0) {
                                var4 = -1.0;
                            } else {
                                var4 = 1.0;
                            }

                            float[] var46 = new float[var27 + 1];
                            float[] var45 = new float[var27 + 1];
                            int var32 = 0;
                            int var137;
                            float[] var152;
                            if (var27 > 0) {
                                var137 = var24;
                                var22 = var104;
                                var152 = var54;
                                var116 = (((var22 != 0)) ? 1 : 0);
                                var24 = var116;
                            } else {
                                float[] var151 = var54;
                                var28 = var104;
                                var104 = var24;
                                float[] var55 = var45;

                                while (true) {
                                    double var71;
                                    if (var32 == 0) {
                                        var71 = -6.0;
                                    } else {
                                        var71 = var151[var32];
                                    }

                                    if (var71 > 38.0) {
                                        var46[var32] = -1.0F;
                                    } else {
                                        if (var104 == 0) {
                                            var2 = var140[var32];
                                        } else {
                                            var2 = var147[var32];
                                        }

                                        double var76 = var2;
                                        int var33 = var94;
                                        var24 = 0;
                                        int var34 = 0;

                                        for (var29 = 0; var33 < 4; var33++) {
                                            int var30 = var24;
                                            var137 = var29;
                                            switch (this.groundAt(var71, var76 + var4 * new double[]{0.5, 1.2, 1.9, 2.6}[var33])) {
                                                case -1:
                                                    Unit var40 = Unit.INSTANCE;
                                                    continue;
                                                case 0:
                                                    break;
                                                case 1:
                                                    var30 = var24 + 1;
                                                    var137 = var29;
                                                    break;
                                                case 2:
                                                    var137 = var29 + 1;
                                                    var30 = var24;
                                                    break;
                                                default:
                                                    var137 = var29;
                                                    var30 = var24;
                                            }

                                            var34++;
                                            var24 = var30;
                                            var29 = var137;
                                        }

                                        if (var24 + var29 >= 2) {
                                            var46[var32] = 1.0F;
                                            if (var29 > var24) {
                                                var2 = 1.0F;
                                            } else {
                                                var2 = 0.0F;
                                            }

                                            var55[var32] = var2;
                                        } else if (var34 >= 2) {
                                            var46[var32] = 0.0F;
                                        } else {
                                            var46[var32] = -1.0F;
                                        }
                                    }

                                    float[] var41 = var140;
                                    float[] var42 = var147;
                                    var24 = var116;
                                    var147 = var147;
                                    var140 = var140;
                                    var116 = (((var22 != 0)) ? 1 : 0);
                                    var152 = var151;
                                    var22 = var28;
                                    var137 = var104;
                                    var45 = var55;
                                    if (var32 == var27) {
                                        break;
                                    }

                                    var32++;
                                    var116 = var116;
                                    var147 = var42;
                                    var140 = var41;
                                    var22 = var22;
                                    var151 = var151;
                                }
                            }

                            float[] var56 = this.sideA[var137];
                            float[] var154 = this.sideG[var137];
                            var104 = 0;
                            if (var27 <= 0) {
                                while (true) {
                                    if (var104 == 0) {
                                        var28 = var94;
                                    } else {
                                        var28 = var104 - 1;
                                    }

                                    if (var28 < var56.length) {
                                        if (var46[var104] < 0.0F && this.curbFit[var137] && var152[var104] < 35.0F) {
                                            var2 = 0.8F;
                                        } else {
                                            var2 = var46[var104];
                                        }

                                        if (var2 >= 0.0F) {
                                            var56[var28] += (var2 - var56[var28]) * 0.25F;
                                            var154[var28] += (var45[var104] - var154[var28]) * 0.25F;
                                        } else {
                                            var56[var28] *= 0.98F;
                                        }
                                    }

                                    if (var104 == var27) {
                                        break;
                                    }

                                    var104++;
                                }
                            }

                            SceneView$$ExternalSyntheticLambda12 var156 = new SceneView$$ExternalSyntheticLambda12(var56, var43);
                            var28 = var27 + 1;
                            float[] var157 = new float[var28];

                            for (int var108 = var94; var108 < var28; var108++) {
                                if (var137 == 0) {
                                    var2 = var140[var108];
                                } else {
                                    var2 = var147[var108];
                                }

                                var157[var108] = (float)(var2 + (this.SIDE_W / 2 + 0.25) * var4);
                            }

                            int var109;
                            if (CollectionsKt.averageOfFloat(ArraysKt.take(var154, Math.min(var154.length, 12))) > 0.5) {
                                var109 = var26;
                            } else {
                                var109 = var94;
                            }

                            double var72 = this.SIDE_W / 2;
                            SceneView.Companion var57 = Companion;
                            if (var109 != 0) {
                                var109 = var25;
                            } else {
                                var109 = var35;
                            }

                            ribbon$default(this, var152, var157, null, var72, var57.withAlpha(var109, 255), 0, 0, 0.03F, var156, 96, null);
                            var28 = var27 + 1;
                            var154 = new float[var28];

                            for (int var111 = var94; var111 < var28; var111++) {
                                if (var137 == 0) {
                                    var2 = var140[var111];
                                } else {
                                    var2 = var147[var111];
                                }

                                var154[var111] = (float)(var2 + 0.13 * var4);
                            }

                            var28 = var27 + 1;
                            float[] var58 = new float[var28];

                            for (int var112 = var94; var112 < var28; var112++) {
                                if (var137 == 0) {
                                    var2 = var140[var112];
                                } else {
                                    var2 = var147[var112];
                                }

                                var58[var112] = (float)(var2 + 0.02 * var4);
                            }

                            ribbon$default(this, var152, var154, null, 0.12, Companion.withAlpha(var24, 255), 0, 0, 0.05F, var43, 96, null);
                            ribbon$default(this, var152, var58, null, 0.035, Companion.withAlpha(var36, 255), 0, 0, 0.055F, var43, 96, null);
                            var29 = var137 + 1;
                            var54 = var152;
                            var104 = (((var22 != 0)) ? 1 : 0);
                            var22 = var116;
                            var24 = var29;
                            var116 = var24;
                        }

                        return;
                    }

                    return;
                }

                if ((var85 != 0)) {
                    var10 = 1.0;
                }

                ArrayList var49 = new ArrayList();

                for (double var6 = -8.0; var6 <= 30.0; var6++) {
                    double var8 = Math.max(0.0, var6);
                    double var12 = cleanRoad$edgeAt(this, var44, var28, (((var85 != 0)) ? 1 : 0), var8);
                    double var14 = -1.0;

                    for (double var73 = 0.0; var73 <= 9.0; var73 += 0.5) {
                        double var16 = var12 + var10 * var73;
                        int var98 = this.groundAt(var6, var16);
                        if (var98 == 0) {
                            var14 = var73;
                        } else {
                            int var99;
                            if (1 <= var98 && var98 < 4) {
                                var99 = 1;
                            } else {
                                var99 = 0;
                            }

                            if ((var99 != 0)) {
                                var99 = this.groundAt(var6, var16 + var10 * 0.5);
                                boolean var101;
                                if (1 <= var99 && var99 < 4) {
                                    var101 = true;
                                } else {
                                    var101 = false;
                                }

                                if (var101) {
                                    if (var14 >= 0.0 && var73 - var14 <= 3.0 && var10 * var16 >= 2.0) {
                                        var49.add(var73 - 0.25);
                                        var4 = Math.max(var4, var6);
                                    }
                                    break;
                                }
                            }
                        }
                    }
                }

                for (SceneView.Pt var145 : this.parked) {
                    if (var145.getSide() == var85) {
                        Pair var146 = this.fromWorld(var145.getWx(), var145.getWy());
                        double var74 = ((Number)var146.component1()).doubleValue();
                        double var77 = ((Number)var146.component2()).doubleValue();
                        if (!(var74 < -15.0) && !(var74 > 40.0)) {
                            var77 = (var77 - cleanRoad$edgeAt(this, var44, var28, (((var85 != 0)) ? 1 : 0), Math.max(0.0, var74))) * var10;
                            boolean var102;
                            if (-0.5 <= var77 && var77 <= 9.0) {
                                var102 = true;
                            } else {
                                var102 = false;
                            }

                            if (var102) {
                                var49.add(Math.max(0.0, var77));
                                var4 = Math.max(var4, var74);
                            }
                        }
                    }
                }

                if (var49.size() < 4) {
                    int[] var52 = this.curbHold;
                    var52[(((var85 != 0)) ? 1 : 0)]++;
                    if (this.curbHold[(((var85 != 0)) ? 1 : 0)] > 200 && var2 >= 0.5F) {
                        this.curbFit[(((var85 != 0)) ? 1 : 0)] = false;
                        double[] var53 = this.curbOffS;
                        var53[(((var85 != 0)) ? 1 : 0)] *= 0.995;
                    }
                } else {
                    CollectionsKt.sort(var49);
                    Object var50 = var49.get(var49.size() / 2);
                    Intrinsics.checkNotNullExpressionValue(var50, "get(...)");
                    double var70 = ((Number)var50).doubleValue();
                    if (var2 >= 0.5F || !this.curbFit[(((var85 != 0)) ? 1 : 0)]) {
                        double var75 = var70 - this.curbOffS[(((var85 != 0)) ? 1 : 0)];
                        if (!this.curbFit[(((var85 != 0)) ? 1 : 0)]) {
                            this.curbOffS[(((var85 != 0)) ? 1 : 0)] = var70;
                        } else if (Math.abs(var75) > 0.4) {
                            var50 = this.curbOffS;
                            ((Object[])var50)[(((var85 != 0)) ? 1 : 0)] += RangesKt.coerceIn(var75, -0.025, 0.025);
                        }
                    }

                    this.curbHold[(((var85 != 0)) ? 1 : 0)] = 0;
                    this.curbFit[(((var85 != 0)) ? 1 : 0)] = true;
                }

                for (int var103 = 0; var103 < var28; var103++) {
                    this.curbS[(((var85 != 0)) ? 1 : 0)][var103] = this.edgeF[var85][var103] + this.curbOffS[var85] * var10;
                }

                var85++;
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

    /* VF: from Vineflower (jadx: Code restructure failed;Removed duplicated region;incorrect) */
    private final SceneView.Curbs curbs(ModelSnapshot var1) {
        double[] var23 = new double[2];

        for (int var11 = 0; var11 < 2; var11++) {
            var23[var11] = Double.NaN;
        }

        double[] var17 = new double[2];

        for (int var42 = 0; var42 < 2; var42++) {
            var17[var42] = Double.NaN;
        }

        StringBuilder[] var22;
        List var58;
        label303: {
            var22 = new StringBuilder[]{new StringBuilder(), new StringBuilder()};
            Detector var15 = this.detector;
            if (var15 != null) {
                List var16;
                if (var15.isRunning() && System.currentTimeMillis() - var15.getObjectsAtMs() < 700L) {
                    var16 = var15.getObjects();
                } else {
                    var16 = null;
                }

                var58 = var16;
                if (var16 != null) {
                    break label303;
                }
            }

            var58 = CollectionsKt.emptyList();
        }

        for (int var12 = 0; var12 < 2; var12++) {
            double var4;
            if (var12 == 0) {
                var4 = -1.0;
            } else {
                var4 = 1.0;
            }

            boolean var43;
            if (this.edgeS[var12].length == 0) {
                var43 = true;
            } else {
                var43 = false;
            }

            double var2;
            label194: {
                if (!var43 && this.lastEdgeX.length == this.edgeS[var12].length) {
                    float[] var61 = var1.getEdgeStd();
                    if (var12 >= 0 && var12 < var61.length) {
                        var43 = (boolean)true;
                    } else {
                        var43 = (boolean)false;
                    }

                    float var10;
                    if (var43) {
                        var10 = var61[var12];
                    } else {
                        var10 = 9.0F;
                    }

                    if (var10 < 1.0F) {
                        ArrayList var62 = new ArrayList();
                        var43 = false;

                        for (int var13 = this.lastEdgeX.length; var43 < var13; var43++) {
                            var10 = this.lastEdgeX[((var43) ? 1 : 0)];
                            if (!(var10 < 3.0F)) {
                                if (var10 > 15.0F) {
                                    break;
                                }

                                var62.add(this.edgeS[var12][((var43) ? 1 : 0)] * var4);
                            }
                        }

                        if (!var62.isEmpty()) {
                            CollectionsKt.sort(var62);
                            var61 = (float[])var62.get(var62.size() / 2);
                            Double var18 = (Double)var61;
                            Intrinsics.checkNotNull(var18);
                            var2 = var18;
                            boolean var46;
                            if (0.4 <= var2 && var2 <= 12.0) {
                                var46 = true;
                            } else {
                                var46 = false;
                            }

                            if (!var46) {
                                var61 = null;
                            }

                            Double var64 = (Double)var61;
                            if (var64 != null) {
                                var2 = var64;
                            } else {
                                var2 = Double.NaN;
                            }
                            break label194;
                        }
                    }
                }

                var2 = Double.NaN;
            }

            boolean var53 = false;
            boolean var14 = false;
            boolean var47;
            if (!Double.isInfinite(var2) && !Double.isNaN(var2)) {
                var47 = true;
            } else {
                var47 = false;
            }

            String var19 = "format(...)";
            String var20 = "%.1f";
            String var66;
            if (!var47) {
                var66 = "format(...)";
                var19 = "%.1f";
            } else {
                Iterator var65 = var58.iterator();
                var47 = var14;

                while (var65.hasNext()) {
                    Detector.Obj var67 = (Detector.Obj)var65.next();
                    if (var67.getCls() == 2 || var67.getCls() == 5 || var67.getCls() == 7) {
                        float var40 = var67.getCx();
                        if (-5.0F <= var40 && var40 <= 25.0F) {
                            var53 = true;
                        } else {
                            var53 = false;
                        }

                        if (var53) {
                            double var6 = var67.getCy() * var4;
                            if (1.2 <= var6 && var6 <= var2 + 1.0) {
                                var53 = true;
                            } else {
                                var53 = false;
                            }

                            if (var53 && (!var67.getVelValid() || Math.abs(var67.getVx()) < 1.5F)) {
                                var47 = (boolean)true;
                            }
                        }
                    }
                }

                Iterator var21 = this.parked.iterator();
                var66 = var19;

                while (var21.hasNext()) {
                    SceneView.Pt var74 = (SceneView.Pt)var21.next();
                    if (var74.getSide() == var12) {
                        Pair var75 = this.fromWorld(var74.getWx(), var74.getWy());
                        double var32 = ((Number)var75.component1()).doubleValue();
                        double var8 = ((Number)var75.component2()).doubleValue();
                        if (-15.0 <= var32 && var32 <= 35.0) {
                            var53 = true;
                        } else {
                            var53 = false;
                        }

                        if (var53 && var4 * var8 < var2 + 1.5) {
                            var47 = (boolean)true;
                        }
                    }
                }

                curbs$lower(var23, var12, var2);
                StringBuilder var80;
                StringBuilder var84;
                if (!var47) {
                    curbs$lower(var23, var12, var2 - 0.5);
                    curbs$upper(var17, var12, 0.6 + var2);
                    var84 = var22[var12];
                    String var76 = String.format(var20, Arrays.copyOf(new Object[]{var2}, 1));
                    Intrinsics.checkNotNullExpressionValue(var76, var19);
                    var80 = new StringBuilder().append("edge ").append(var76);
                    var19 = " clear";
                } else {
                    var84 = var22[var12];
                    var19 = String.format(var20, Arrays.copyOf(new Object[]{var2}, 1));
                    Intrinsics.checkNotNullExpressionValue(var19, var19);
                    var80 = new StringBuilder().append("edge ");
                }

                var84.append(var80.append(var19).toString());
                var19 = var20;
                var58 = var58;
            }

            ArrayList var81 = new ArrayList();

            for (SceneView.Pt var85 : this.parked) {
                if (var85.getSide() == var12) {
                    Pair var86 = this.fromWorld(var85.getWx(), var85.getWy());
                    double var33 = ((Number)var86.component1()).doubleValue();
                    double var37 = ((Number)var86.component2()).doubleValue();
                    if (-15.0 <= var33 && var33 <= 35.0) {
                        var53 = true;
                    } else {
                        var53 = false;
                    }

                    if (var53 && var4 * var37 > 1.0) {
                        var81.add(var4 * var37);
                    }
                }
            }

            if (var81.size() >= 3) {
                CollectionsKt.sort(var81);
                Object var70 = var81.get(var81.size() / 2);
                Intrinsics.checkNotNullExpressionValue(var70, "get(...)");
                double var34 = ((Number)var70).doubleValue();
                if (var34 < 12.0) {
                    curbs$lower(var23, var12, var34);
                    StringBuilder var82 = var22[var12];
                    var70 = String.format(var19, Arrays.copyOf(new Object[]{var34}, 1));
                    Intrinsics.checkNotNullExpressionValue(var70, var66);
                    var82.append(" parked " + var70);
                }
            }

            ArrayList var72 = new ArrayList();

            for (Detector.Obj var87 : var58) {
                if (var87.getCls() == 0) {
                    float var41 = var87.getCx();
                    if (0.0F <= var41 && var41 <= 30.0F) {
                        var47 = (boolean)true;
                    } else {
                        var47 = (boolean)false;
                    }

                    if (var47 && (!var87.getVelValid() || Math.abs(var87.getVy()) < 0.6F)) {
                        double var35 = var87.getCy() * var4;
                        if (var35 > 1.5 && (Double.isNaN(var2) || var35 > var2 - 0.3)) {
                            var72.add(var35);
                        }
                    }
                }
            }

            if (!var72.isEmpty()) {
                CollectionsKt.sort(var72);
                if (var72.size() >= 2) {
                    var47 = true;
                } else {
                    var47 = false;
                }

                var2 = (Double)var72.get(((var47) ? 1 : 0)) - 0.2;
                curbs$upper(var17, var12, var2);
                StringBuilder var73 = var22[var12];
                var19 = String.format(var19, Arrays.copyOf(new Object[]{var2 + 0.2}, 1));
                Intrinsics.checkNotNullExpressionValue(var19, var66);
                var73.append(" people <" + var19);
            }

            var2 = var23[var12];
            boolean var51;
            if (!Double.isInfinite(var2) && !Double.isNaN(var2)) {
                var51 = true;
            } else {
                var51 = false;
            }

            if (var51) {
                var2 = var17[var12];
                if (!Double.isInfinite(var2) && !Double.isNaN(var2)) {
                    var51 = true;
                } else {
                    var51 = false;
                }

                if (var51 && var23[var12] > var17[var12]) {
                    var2 = (var23[var12] + var17[var12]) / 2;
                    var23[var12] = var2 - 0.2;
                    var17[var12] = 0.2 + var2;
                }
            }
        }

        double var31 = var23[0];
        double var30 = var17[0];
        double var38 = var23[1];
        double var36 = var17[1];
        String var24 = curbs$side(var22, 0);
        String var60 = curbs$side(var22, 1);
        return new SceneView.Curbs(var31, var30, var38, var36, "curbs L " + var24 + "  R " + var60);
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

    /* VF: from Vineflower (jadx: Code restructure failed;Removed duplicated region;incorrect) */
    private final boolean drawMapRoads(ModelSnapshot var1, float var2) {
        Object var150 = null; String var194 = null; // declarations Vineflower left out
        SceneView.LaneHint var39;
        OsmRoads.Fix var41;
        Integer var54;
        label828: {
            var41 = OsmRoads.INSTANCE.getLastFix();
            var39 = this.laneHint;
            var54 = 0;
            if (var39 != null) {
                boolean var23;
                if (System.nanoTime() - var39.getAtNs() < 2000000000L && var39.getConf() >= 0.5) {
                    var23 = true;
                } else {
                    var23 = false;
                }

                if (var23) {
                    break label828;
                }
            }

            var39 = null;
        }

        this.hintNow = var39;
        if (this.mapAlign.getOk() && var41 != null) {
            if (this.shiftJumps != this.mapAlign.getJumps()) {
                this.shiftJumps = this.mapAlign.getJumps();
                this.shiftE = 0.0;
                this.shiftN = 0.0;
                this.turnA = 0.0;
            }

            Pair var171 = OsmRoads.Align.apply$default(this.mapAlign, this.odoX, this.odoY, 0.0, 0.0, 0.0, 28, null);
            double var17 = ((Number)var171.component1()).doubleValue();
            double var19 = ((Number)var171.component2()).doubleValue();
            double var13 = this.mapAlign.getTh() + this.odoYaw - this.turnA;
            OsmRoads.Match var50 = OsmRoads.INSTANCE.matchSeg(var17 + this.shiftE, var19 + this.shiftN, var13);
            String var172 = "";
            double var3;
            if (var50 != null) {
                if (var50.getWay().getHighway()) {
                    var3 = this.lanesOf(var50.getWay()) * this.laneW;
                } else {
                    Double var40 = this.learnedW.get(var50.getWay().getId());
                    if (var40 != null) {
                        var3 = var40;
                    } else {
                        var3 = var50.getWay().getWidth();
                    }
                }
            } else {
                var3 = 0.0;
            }

            long var35 = 0L;
            String var42 = "";
            double var98;
            String var176;
            if (var50 != null) {
                int var108 = 0;

                while (var108 < var1.getPosX().length - 1 && var1.getPosX()[var108] < 20.0F) {
                    var108++;
                }

                boolean var24;
                if (var1.getPosY().length == 0) {
                    var24 = true;
                } else {
                    var24 = false;
                }

                int var109;
                if (!var24 && Math.abs(var1.getPosY()[var108]) < 0.6F) {
                    var109 = 1;
                } else {
                    var109 = 0;
                }

                if (var2 > 2.0F && var109) {
                    double var7 = var50.getDir();
                    if (var50.getWay().getOneway() == 0) {
                        var98 = Math.PI;
                    } else {
                        var98 = Math.PI * 2;
                    }

                    var98 = Math.IEEEremainder(var7 - var13, var98);
                    if (var50.getWay().getOneway() != 0 && Math.abs(var98) > Math.PI / 2) {
                        var98 = Math.IEEEremainder(var50.getDir() - var13, Math.PI);
                    }

                    if (Math.abs(var98) < Math.toRadians(25.0)) {
                        this.turnA += -0.05 * var98;
                    }
                }

                if (var2 > 0.5F) {
                    this.turnA = RangesKt.coerceIn(this.turnA, -0.6, 0.6) * 0.9995;
                }

                SceneView.Curbs var184 = this.curbs(var1);
                var98 = var184.getLHi();
                if (!Double.isInfinite(var98) && !Double.isNaN(var98)) {
                    var109 = (((boolean)true) ? 1 : 0);
                } else {
                    var109 = (((boolean)false) ? 1 : 0);
                }

                label788: {
                    if ((var109 != 0)) {
                        var98 = var184.getRHi();
                        if (!Double.isInfinite(var98) && !Double.isNaN(var98)) {
                            var109 = (((boolean)true) ? 1 : 0);
                        } else {
                            var109 = (((boolean)false) ? 1 : 0);
                        }

                        if ((var109 != 0)) {
                            var98 = var184.getLLo();
                            if (!Double.isInfinite(var98) && !Double.isNaN(var98)) {
                                var109 = (((boolean)true) ? 1 : 0);
                            } else {
                                var109 = (((boolean)false) ? 1 : 0);
                            }

                            if ((var109 != 0)) {
                                var98 = var184.getRLo();
                                if (!Double.isInfinite(var98) && !Double.isNaN(var98)) {
                                    var109 = (((boolean)true) ? 1 : 0);
                                } else {
                                    var109 = (((boolean)false) ? 1 : 0);
                                }

                                if ((var109 != 0)) {
                                    var109 = (((boolean)true) ? 1 : 0);
                                    break label788;
                                }
                            }
                        }
                    }

                    var109 = (((boolean)false) ? 1 : 0);
                }

                if ((var109 != 0)) {
                    var98 = var184.getLLo();
                    double var9 = var184.getLHi();
                    double var101 = 2;
                    var98 = (var98 + var9) / var101 + (var184.getRLo() + var184.getRHi()) / var101;
                } else {
                    label881: {
                        var98 = var184.getLLo();
                        if (!Double.isInfinite(var98) && !Double.isNaN(var98)) {
                            var24 = true;
                        } else {
                            var24 = false;
                        }

                        if (var24) {
                            var98 = var184.getRLo();
                            if (!Double.isInfinite(var98) && !Double.isNaN(var98)) {
                                var24 = true;
                            } else {
                                var24 = false;
                            }

                            if (var24) {
                                var98 = 0.4 + (var184.getLLo() + var184.getRLo());
                                break label881;
                            }
                        }

                        var98 = Double.NaN;
                    }
                }

                boolean var38 = var50.getWay().getHighway();
                if (!var38) {
                    if (!Double.isInfinite(var98) && !Double.isNaN(var98)) {
                        var24 = true;
                    } else {
                        var24 = false;
                    }

                    if (var24 && (var109 || var98 > var3)) {
                        var98 = RangesKt.coerceIn(var3 + (var98 - var3) * 0.05, var50.getWay().getWidth() - 3.0, var50.getWay().getWidth() + 5.0);
                        this.learnedW.put(var50.getWay().getId(), var98);
                        var3 = var98;
                        if (this.learnedW.size() > 500) {
                            this.learnedW.clear();
                            var3 = var98;
                        }
                    }
                }

                double var21 = var50.rightOf(var13);
                double var15 = var21 - (this.shiftE * Math.sin(var13) - this.shiftN * Math.cos(var13));
                this.mapCarOff = var21;
                int var25 = this.lanesOf(var50.getWay());
                boolean var37;
                if (var50.getWay().getOneway() != 0) {
                    var37 = true;
                } else {
                    var37 = false;
                }

                if (!var38 && var25 < 2) {
                    var109 = -1;
                } else {
                    var109 = this.cameraLane(var1, var25, var37);
                }

                SceneView.LaneHint var59 = this.hintFor(var50.getWay());
                if (var59 != null) {
                    var109 = RangesKt.coerceIn(var59.getLane(), 0, var25 - 1);
                }

                OsmRoads var60 = OsmRoads.INSTANCE;
                double var11;
                double var102;
                double var106;
                if (var38) {
                    var98 = Double.NaN;
                    var106 = Double.NaN;
                    var102 = Double.NaN;
                    var11 = Double.NaN;
                } else {
                    var102 = var184.getLLo();
                    var11 = var184.getLHi();
                    var98 = var184.getRLo();
                    var106 = var184.getRHi();
                }

                Pair var61 = var60.laneBand(var3, var37, var102, var11, var98, var106);
                if (var38 || !(((Number)var61.getSecond()).doubleValue() - ((Number)var61.getFirst()).doubleValue() < 1.5)) {
                    if (var109 >= 0 && this.laneMapped(var50.getWay())) {
                        var98 = this.leftEdge(var50.getWay()) + (var109 + 0.5) * this.laneW;
                        var61 = TuplesKt.to(var98, var98);
                    } else if (var109 >= 0) {
                        OsmRoads var62 = OsmRoads.INSTANCE;
                        if (var38) {
                            var98 = var3;
                        } else {
                            var98 = var50.getWay().getLanesWidth();
                        }

                        var98 = var62.laneCenter(var98, var25, (((var109 != 0)) ? 1 : 0), var37);
                        var61 = TuplesKt.to(var98, var98);
                    }
                }

                var102 = ((Number)var61.component1()).doubleValue();
                var98 = ((Number)var61.component2()).doubleValue();
                if (var98 - var102 < 1.5) {
                    var98 = (var102 + var98) / 2;
                } else {
                    var98 = RangesKt.coerceIn(var21, var102, var98);
                }

                var102 = var98 - var21;
                if (var109 >= 0) {
                    var98 = 0.25;
                } else if (var2 > 0.5F) {
                    var98 = 0.05;
                } else {
                    var98 = 0.02;
                }

                this.shiftE = this.shiftE + var98 * var102 * Math.sin(var13);
                this.shiftN = this.shiftN + -var98 * var102 * Math.cos(var13);
                var98 = Math.hypot(this.shiftE, this.shiftN);
                if (var98 > 12.0) {
                    this.shiftE *= 12.0 / var98;
                    this.shiftN *= 12.0 / var98;
                }

                String var43;
                label717: {
                    var43 = this.yawSrc;
                    var102 = this.gpsLag;
                    if (var41.getMonoNs() == 0L) {
                        Double var63 = var41.getAgeS();
                        var98 = var63.doubleValue();
                        if (!Double.isInfinite(var98) && !Double.isNaN(var98)) {
                            var24 = true;
                        } else {
                            var24 = false;
                        }

                        if (!var24) {
                            var63 = null;
                        }

                        if (var63 != null) {
                            var98 = var63;
                            break label717;
                        }
                    }

                    var98 = 0.0;
                }

                String var44 = String.format("%.1f", Arrays.copyOf(new Object[]{var102 + var98}, 1));
                Intrinsics.checkNotNullExpressionValue(var44, "format(...)");
                String var66;
                if (!var38 && var25 < 2) {
                    var66 = "";
                } else {
                    String var64;
                    StringBuilder var173;
                    if (var109 >= 0) {
                        var173 = new StringBuilder().append(var109 + 1);
                        var64 = " of ";
                    } else {
                        var173 = new StringBuilder();
                        var64 = "? of ";
                    }

                    String var65 = var173.append(var64).append(var25).toString();
                    String var174 = var50.getWay().getKind();
                    var66 = "lane " + var65 + " (from left)  " + var174 + "  ";
                }

                String var175;
                if (var38) {
                    var175 = "";
                } else {
                    var175 = var184.getTxt();
                }

                var176 = "  yaw " + var43 + "  GPS delay " + var44 + " s  " + var66 + var175;
                var98 = var15;
            } else {
                var176 = "";
                var98 = Double.NaN;
            }

            if (var2 > 0.5F && this.laneIdx < 0) {
                this.shiftE *= 0.999;
                this.shiftN *= 0.999;
            }

            long var33 = this.mapAlign.getRejectSince();
            String var186;
            if (var33 != 0L) {
                Object[] var67 = Arrays.copyOf(new Object[]{this.mapAlign.getRejectMiss()}, 1);
                String var68 = String.format("%.0f", var67);
                Intrinsics.checkNotNullExpressionValue(var68, "format(...)");
                var186 = String.format("%.0f", Arrays.copyOf(new Object[]{(System.nanoTime() - var33) / 1.0E9}, 1));
                Intrinsics.checkNotNullExpressionValue(var186, "format(...)");
                var186 = "GPS " + var68 + " m off - ignored " + var186 + " s, wheels+gyro  ";
            } else {
                var186 = "";
            }

            int var116;
            if (this.mapAlign.getGuess().length() > 0) {
                var116 = 1;
            } else {
                var116 = 0;
            }

            String var70;
            if ((var116 != 0)) {
                String var69 = this.mapAlign.getGuess();
                var70 = "parked: lined up with " + var69 + "  ";
            } else {
                var70 = "";
            }

            String var45;
            String var46;
            String var211;
            String var214;
            label698: {
                var45 = String.format("%.1f", Arrays.copyOf(new Object[]{var41.getHAcc()}, 1));
                Intrinsics.checkNotNullExpressionValue(var45, "format(...)");
                var211 = OsmRoads.INSTANCE.getGpsSrc();
                var46 = String.format("%.0f", Arrays.copyOf(new Object[]{this.mapAlign.getSpread()}, 1));
                Intrinsics.checkNotNullExpressionValue(var46, "format(...)");
                var214 = String.format("%.1f", Arrays.copyOf(new Object[]{this.mapAlign.getResid()}, 1));
                Intrinsics.checkNotNullExpressionValue(var214, "format(...)");
                if (var50 != null) {
                    var41 = var50.getWay().getName();
                    if (var41.length() == 0) {
                        var116 = (((boolean)true) ? 1 : 0);
                    } else {
                        var116 = (((boolean)false) ? 1 : 0);
                    }

                    if ((var116 != 0)) {
                        var41 = var50.getWay().getKind();
                    }

                    if (var50.getWay().getOneway() != 0) {
                        var42 = " (one-way)";
                    }

                    String var49 = String.format("%.1f", Arrays.copyOf(new Object[]{var50.rightOf(var13)}, 1));
                    Intrinsics.checkNotNullExpressionValue(var49, "format(...)");
                    String var48 = String.format("%.1f", Arrays.copyOf(new Object[]{var98}, 1));
                    Intrinsics.checkNotNullExpressionValue(var48, "format(...)");
                    String var47 = String.format("%.1f", Arrays.copyOf(new Object[]{var3}, 1));
                    Intrinsics.checkNotNullExpressionValue(var47, "format(...)");
                    String var51 = String.format("%.0f", Arrays.copyOf(new Object[]{Math.toDegrees(this.turnA)}, 1));
                    Intrinsics.checkNotNullExpressionValue(var51, "format(...)");
                    var42 = "on: " + var41 + var42 + " " + var49 + " m right of middle (GPS " + var48 + ", " + var47 + " m wide)  turn " + var51 + "°" + var176;
                    var194 = var42;
                    if (var42 != null) {
                        break label698;
                    }
                }

                var194 = "no street under the car";
            }

            this.mapStatus = var186 + var70 + "GPS ±" + var45 + " m " + var211 + "  track " + var46 + " m  fit " + var214 + " m  " + var194;
            var3 = this.turnA;
            SceneView$$ExternalSyntheticLambda2 var212 = new SceneView$$ExternalSyntheticLambda2(this, var3);
            Iterable var71 = OsmRoads.INSTANCE.near(var17, var19, this.MAP_R + 40.0);
            boolean var166 = false;
            var var200 = new ArrayList();

            for (Object var215 : var71) {
                if (!Intrinsics.areEqual(((OsmRoads.Way)var215).getKind(), "sidewalk")) {
                    var200.add(var215);
                }
            }

            List var201 = var200;
            Iterable var72 = var201;
            var166 = false;
            var var216 = new ArrayList();
            boolean var170 = false;

            for (Object var224 : var72) {
                OsmRoads.Way var234 = (OsmRoads.Way)var224;
                if (var234.getMaxE() >= var17 - this.MAP_R
                    && var234.getMinE() <= var17 + this.MAP_R
                    && var234.getMaxN() >= var19 - this.MAP_R
                    && var234.getMinN() <= var19 + this.MAP_R) {
                    var116 = (((boolean)true) ? 1 : 0);
                } else {
                    var116 = (((boolean)false) ? 1 : 0);
                }

                if ((var116 != 0)) {
                    var216.add(var224);
                }
            }

            List var73 = var216;
            var33 = var35;
            if (var50 != null) {
                OsmRoads.Way var196 = var50.getWay();
                var33 = var35;
                if (var196 != null) {
                    var33 = var196.getId();
                }
            }

            if (var33 != this.hintChainOf) {
                OsmRoads.Way var197;
                if (var50 != null) {
                    var197 = var50.getWay();
                } else {
                    var197 = null;
                }

                this.hintChainNow(var73, var197);
            }

            List var217 = OsmRoads.INSTANCE
                .carFrame(RoadShape.INSTANCE.rounded(var73, var201, var17, var19, this.MAP_R, this.roundCache), var212, this.MAP_R, 8.0);
            OsmRoads.Way var223;
            if (var50 != null) {
                var223 = var50.getWay();
            } else {
                var223 = null;
            }

            HashSet var198 = new HashSet();
            if (var223 != null) {
                for (OsmRoads.Way var202 : var73) {
                    if (var202.getId() != var223.getId()) {
                        if (!Intrinsics.areEqual(var202.getKind(), var223.getKind())) {
                            continue;
                        }

                        if (var223.getName().length() > 0) {
                            var116 = (((boolean)true) ? 1 : 0);
                        } else {
                            var116 = (((boolean)false) ? 1 : 0);
                        }

                        if (!var116 || !OsmRoads.INSTANCE.sameStreet(var202.getName(), var223.getName())) {
                            continue;
                        }
                    }

                    var198.add(var202.getId());
                }
            }

            int var131;
            if (var223 != null && var223.getHighway()) {
                var131 = 1;
            } else {
                var131 = 0;
            }

            HashMap var258 = new HashMap();
            var116 = (((boolean)false) ? 1 : 0);
            SceneView$$ExternalSyntheticLambda2 var182;
            HashSet var209;
            List var213;
            if (var223 != null) {
                if (System.nanoTime() - this.crossAtNs > 500000000L) {
                    this.crossAtNs = System.nanoTime();
                    this.crossNow.clear();
                    Iterable var203 = var73;
                    var var226 = new ArrayList();

                    for (Object var243 : var203) {
                        if (var198.contains(((OsmRoads.Way)var243).getId())) {
                            var226.add(var243);
                        }
                    }

                    List var227 = var226;
                    Iterator var236 = var73.iterator();
                    var213 = var217;

                    while (var236.hasNext()) {
                        OsmRoads.Way var52 = (OsmRoads.Way)var236.next();
                        if (!var198.contains(var52.getId())) {
                            ArrayList var250 = new ArrayList();

                            for (OsmRoads.Way var55 : var227) {
                                Map var53 = this.crossCache;
                                StringBuilder var237;
                                if (var52.getId() < var55.getId()) {
                                    var35 = var52.getId();
                                    var33 = var55.getId();
                                    var237 = new StringBuilder().append(var35).append(":").append(var33);
                                } else {
                                    var35 = var55.getId();
                                    var33 = var52.getId();
                                    var237 = new StringBuilder().append(var35).append(":").append(var33);
                                }

                                String var56 = var237.toString();
                                Object var238 = var53.get(var56);
                                if (var238 == null) {
                                    var238 = RoadShape.INSTANCE.crossings(var52, var55);
                                    var53.put(var56, var238);
                                }

                                var250.addAll((Collection)var238);
                            }

                            if (!var250.isEmpty()) {
                                this.crossNow.put(var52.getId(), var250);
                            }

                            var236 = var236;
                            var176 = var176;
                        }
                    }

                    if (this.crossNow.isEmpty()) {
                        var209 = var198;
                    } else {
                        HashMap var245 = new HashMap();
                        Iterator var178 = var73.iterator();

                        while (var178.hasNext()) {
                            OsmRoads.Way var261 = (OsmRoads.Way)var178.next();
                            HashSet var204 = var198;
                            Iterator var188 = var178;
                            if (!var198.contains(var261.getId())) {
                                var204 = var198;
                                var188 = var178;
                                if (var261.getE().length >= 2) {
                                    Iterator var251 = CollectionsKt.listOf(new Integer[]{var54, var261.getE().length - 1}).iterator();

                                    while (true) {
                                        var204 = var198;
                                        var188 = var178;
                                        if (!var251.hasNext()) {
                                            break;
                                        }

                                        var116 = ((Number)var251.next()).intValue();
                                        Map var205 = var245;
                                        Long var266 = drawMapRoads$k(var261.getE()[(((var116 != 0)) ? 1 : 0)], var261.getN()[var116]);
                                        var186 = (String)var205.get(var266);
                                        if (var186 == null) {
                                            var186 = new ArrayList(2);
                                            var205.put(var266, var186);
                                        }

                                        ((List)var186).add(var261);
                                    }
                                }
                            }

                            var198 = var204;
                            var178 = var188;
                        }

                        Set var179 = this.crossNow.keySet();
                        Intrinsics.checkNotNullExpressionValue(var179, "<get-keys>(...)");
                        var39 = CollectionsKt.toList(var179);
                        var116 = 0;
                        List var190 = var73;

                        for (HashMap var74 = var245; var116 < 2; var116++) {
                            var166 = false;
                            ArrayList var262 = new ArrayList();

                            for (Object var42$it : (List)var39) {
                                var42 = (String) var42$it;
                                Intrinsics.checkNotNullExpressionValue(var42, "next(...)");
                                var33 = ((Number)var42).longValue();
                                Iterator var252 = var190.iterator();

                                label581: {
                                    while (var252.hasNext()) {
                                        var42 = (String)var252.next();
                                        boolean var27;
                                        if (((OsmRoads.Way)var42).getId() == var33) {
                                            var27 = true;
                                        } else {
                                            var27 = false;
                                        }

                                        if (var27) {
                                            break label581;
                                        }
                                    }

                                    var42 = null;
                                }

                                OsmRoads.Way var270 = (OsmRoads.Way)var42;
                                if (var270 != null) {
                                    List var271 = this.crossNow.get(var33);
                                    if (var271 != null) {
                                        var252 = CollectionsKt.listOf(new Integer[]{var54, var270.getE().length - 1}).iterator();

                                        while (var252.hasNext()) {
                                            int var141 = ((Number)var252.next()).intValue();
                                            List var267 = (List)var74.get(drawMapRoads$k(var270.getE()[var141], var270.getN()[var141]));
                                            List var191 = var267;
                                            if (var267 == null) {
                                                var191 = CollectionsKt.emptyList();
                                            }

                                            for (OsmRoads.Way var268 : var191) {
                                                if (var268.getId() != var33
                                                    && !this.crossNow.containsKey(var268.getId())
                                                    && StringsKt.endsWith(var268.getKind(), "_link", false)
                                                        == StringsKt.endsWith(var270.getKind(), "_link", false)) {
                                                    this.crossNow.put(var268.getId(), var271);
                                                    var262.add(var268.getId());
                                                }
                                            }
                                        }

                                        var190 = var190;
                                    }
                                }
                            }

                            var39 = var262;
                        }

                        var73 = var190;
                        var209 = var198;
                    }

                    if (this.crossCache.size() > 20000) {
                        this.crossCache.clear();
                    }

                    OsmRoads.Way var181;
                    if (var50 != null) {
                        var181 = var50.getWay();
                    } else {
                        var181 = null;
                    }

                    this.hintChainNow(var73, var181);
                    var116 = var116;
                    var182 = var212;
                } else {
                    var116 = (((boolean)false) ? 1 : 0);
                    var209 = var198;
                    var182 = var212;
                    var213 = var217;
                }
            } else {
                var116 = (((boolean)false) ? 1 : 0);
                var213 = var217;
                var209 = var198;
                var182 = var212;
            }

            int var26 = var116;
            if (var223 != null) {
                Iterator var228 = this.crossNow.entrySet().iterator();
                SceneView$$ExternalSyntheticLambda2 var221 = var182;
                Iterator var183 = var228;

                while (true) {
                    var26 = var116;
                    if (!var183.hasNext()) {
                        break;
                    }

                    java.util.Map.Entry var229 = (java.util.Map.Entry)var183.next();
                    var35 = ((Number)var229.getKey()).longValue();
                    List var239 = (List)var229.getValue();
                    Iterator var247 = var73.iterator();

                    label544: {
                        while (var247.hasNext()) {
                            var46 = (String)var247.next();
                            boolean var136;
                            if (((OsmRoads.Way)var46).getId() == var35) {
                                var136 = true;
                            } else {
                                var136 = false;
                            }

                            if (var136) {
                                break label544;
                            }
                        }

                        var46 = null;
                    }

                    OsmRoads.Way var248 = (OsmRoads.Way)var46;
                    if (var248 != null) {
                        if ((var131 != 0)) {
                            Map var249 = var258;
                            Iterable var232 = var239;
                            var var254 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var232, 10));

                            for (double[] var241 : var232) {
                                Pair var242 = (Pair)var221.invoke(var241[0], var241[1]);
                                var254.add(new double[]{((Number)var242.component1()).doubleValue(), ((Number)var242.component2()).doubleValue()});
                            }

                            List var233 = var254;
                            var249.put(var35, var233);
                        } else {
                            Iterator var231 = var183;
                            var239 = var73;
                            var73 = var73;
                            var183 = var183;
                            if (var248.getHighway()) {
                                var116 = (((boolean)true) ? 1 : 0);
                                var73 = var239;
                                var183 = var231;
                            }
                        }
                    }
                }
            }

            int var137;
            if (!this.night && !this.alwaysDark) {
                var137 = 0;
            } else {
                var137 = 1;
            }

            short var28;
            short var124;
            short var142;
            if ((var137 != 0)) {
                var124 = 51;
                var28 = 60;
                var142 = 46;
            } else {
                var124 = 169;
                var28 = 178;
                var142 = 163;
            }

            int var30 = Color.rgb(var142, var124, var28);
            if ((var137 != 0)) {
                var124 = Color.rgb(106, 113, 125);
            } else {
                var124 = Color.rgb(246, 247, 249);
            }

            short var29;
            if ((var137 != 0)) {
                var142 = (short)75;
                var29 = 86;
                var28 = (short)69;
            } else {
                var142 = (short)215;
                var29 = 221;
                var28 = (short)211;
            }

            var29 = Color.rgb(var28, var142, var29);
            Set var264 = SetsKt.setOf(
                new String[]{"residential", "tertiary", "secondary", "primary", "unclassified", "living_street", "tertiary_link", "secondary_link", "primary_link"}
            );
            SceneView$$ExternalSyntheticLambda3 var75 = new SceneView$$ExternalSyntheticLambda3(this);
            IdentityHashMap var255 = this.bandsFor(var213, var75);
            drawMapRoads$pass(var213, var258, this, new SceneView$$ExternalSyntheticLambda4(var264, this, var29, var255));
            drawMapRoads$pass(var213, var258, this, new SceneView$$ExternalSyntheticLambda5(this, var124, var255));
            drawMapRoads$pass(var213, var258, this, new SceneView$$ExternalSyntheticLambda6(this, var30, var255));
            this.mapLanes = false;
            if (this.laneLinesOn) {
                if ((var137 != 0)) {
                    var142 = Color.rgb(214, 218, 224);
                } else {
                    var142 = -1;
                }

                if ((var137 != 0)) {
                    var28 = Color.rgb(217, 174, 43);
                } else {
                    var28 = Color.rgb(242, 194, 48);
                }

                if (this.pathStateNow == LaneTracks.PathState.ENGAGED) {
                    var166 = true;
                } else {
                    var166 = false;
                }

                drawMapRoads$pass(var213, var258, this, new SceneView$$ExternalSyntheticLambda7(this, var50, var166, var28, var142, var255));
            }

            int var154 = 0;
            HashMap var76 = var258;
            if ((var26 != 0)) {
                drawMapRoads$pass(var213, var258, this, new SceneView$$ExternalSyntheticLambda8(var209, this, var30, var255));
            }

            if (!var258.isEmpty()) {
                if (var223 != null) {
                    if ((var131 != 0)) {
                        var98 = this.lanesOf(var223) * this.laneW;
                    } else {
                        var98 = var223.getWidth();
                    }

                    var98 = var98 / 2.0 + 3.0;
                } else {
                    var98 = 10.0;
                }

                short var147;
                if ((var137 != 0)) {
                    var147 = 64;
                    var131 = 74;
                    var26 = 58;
                } else {
                    var147 = 189;
                    var131 = 197;
                    var26 = 184;
                }

                int var31 = Color.rgb(var26, var147, var131);
                int var133;
                byte var140;
                if ((var137 != 0)) {
                    var133 = 34;
                    var147 = 41;
                    var140 = 30;
                } else {
                    var133 = 117;
                    var147 = 128;
                    var140 = 110;
                }

                int var32 = Color.rgb(var140, var133, var147);
                if ((var137 != 0)) {
                    var137 = Color.rgb(112, 119, 131);
                } else {
                    var137 = Color.rgb(244, 245, 247);
                }

                Iterator var257 = var213.iterator();
                byte var126 = (byte)var154;
                var133 = var30;

                while (var257.hasNext()) {
                    OsmRoads.Piece var256 = (OsmRoads.Piece)var257.next();
                    List var259 = (List)var76.get(var256.getWay().getId());
                    if (var259 != null) {
                        this.load(var256);
                        if (this.curN >= 2) {
                            double[] var265 = RoadShape.INSTANCE.heights(this.mx, this.my, this.curN, var259, var98 + 2.0, 80.0, this.BRIDGE_H);
                            var30 = var265.length;

                            label502: {
                                for (int var149 = var126; var149 < var30; var149++) {
                                    if (var265[var149] > 0.3) {
                                        var154 = (byte)1;
                                    } else {
                                        var154 = var126;
                                    }

                                    if (var154 != 0) {
                                        var150 = var126;
                                        break label502;
                                    }
                                }

                                var150 = 1;
                            }

                            if (var150) {
                                polyRibbon$default(
                                    this,
                                    this.curN,
                                    drawMapRoads$mid(this, var256),
                                    drawMapRoads$half(this, var256),
                                    var133,
                                    0.03F,
                                    this.MAP_R,
                                    null,
                                    null,
                                    null,
                                    null,
                                    960,
                                    null
                                );
                            } else {
                                var154 = this.curN;
                                double[] var260 = new double[var154];

                                for (int var151 = var126; var151 < var154; var151++) {
                                    double var107;
                                    if (var265[var151] > 1.0) {
                                        var107 = RangesKt.coerceIn((this.mx[var151] - 4.0) / 26.0, 0.25, 1.0);
                                    } else {
                                        var107 = 1.0;
                                    }

                                    var260[var151] = var107;
                                }

                                var154 = this.curN;
                                double[] var269 = new double[var154];

                                for (int var152 = var126; var152 < var154; var152++) {
                                    var269[var152] = var265[var152] / this.BRIDGE_H * 0.28;
                                }

                                polyRibbon$default(
                                    this,
                                    this.curN,
                                    drawMapRoads$mid(this, var256),
                                    drawMapRoads$half(this, var256) + 1.2,
                                    -16777216,
                                    0.05F,
                                    this.MAP_R,
                                    null,
                                    var269,
                                    null,
                                    null,
                                    768,
                                    null
                                );
                                this.bridgeSides(this.curN, drawMapRoads$mid(this, var256), drawMapRoads$half(this, var256) + 0.45, var265, var32, this.MAP_R, var260);
                                polyRibbon$default(
                                    this,
                                    this.curN,
                                    drawMapRoads$mid(this, var256),
                                    drawMapRoads$half(this, var256) + 0.45,
                                    (((var137 != 0)) ? 1 : 0),
                                    0.06F,
                                    this.MAP_R,
                                    var265,
                                    var260,
                                    null,
                                    null,
                                    768,
                                    null
                                );
                                polyRibbon$default(
                                    this,
                                    this.curN,
                                    drawMapRoads$mid(this, var256),
                                    drawMapRoads$half(this, var256),
                                    var31,
                                    0.07F,
                                    this.MAP_R,
                                    var265,
                                    var260,
                                    null,
                                    null,
                                    768,
                                    null
                                );
                            }
                        }
                    }
                }
            }

            return true;
        } else {
            String var57;
            if (var41 == null) {
                var57 = "waiting for GPS";
            } else {
                String var58 = String.format("%.0f", Arrays.copyOf(new Object[]{var41.getHAcc()}, 1));
                Intrinsics.checkNotNullExpressionValue(var58, "format(...)");
                var57 = "GPS ±" + var58 + " m, waiting to line up";
            }

            this.mapStatus = var57;
            return false;
        }
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

    /* VF: from Vineflower (jadx: Removed duplicated region;incorrect) */
    public static final Unit drawMapRoads$lambda$88(
        SceneView var0, OsmRoads.Match var1, boolean var2, int var3, int var4, IdentityHashMap var5, OsmRoads.Piece var6
    ) {
        Intrinsics.checkNotNullParameter(var6, "p");
        if (!var0.laneMapped(var6.getWay())) {
            return Unit.INSTANCE;
        } else {
            double var11 = drawMapRoads$dir(var6);
            double var9 = var0.leftEdge(var6.getWay());
            int var21 = var0.lanesOf(var6.getWay());
            boolean var20;
            if (var1 != null && var6.getWay().getId() == var1.getWay().getId()) {
                var20 = true;
            } else {
                var20 = false;
            }

            int var18 = var21 + 1;
            double[] var27 = new double[var18];

            for (int var17 = 0; var17 < var18; var17++) {
                double var7;
                if (var17 == 0) {
                    var7 = var9 + 0.3;
                } else if (var17 == var21) {
                    var7 = var9 + var21 * var0.laneW - 0.3;
                } else {
                    var7 = var9 + var17 * var0.laneW;
                }

                var27[var17] = var7;
            }

            int var43;
            label142: {
                if (var20 && var2) {
                    Iterator var28 = ArraysKt.getIndices(var27).iterator();
                    Object var29;
                    if (!var28.hasNext()) {
                        var29 = null;
                    } else {
                        var29 = (Integer)var28.next();
                        if (var28.hasNext()) {
                            double var34 = Math.abs(var27[var29.intValue()] - var0.mapCarOff);
                            Object var25 = var29;

                            do {
                                Object var26 = var28.next();
                                double var13 = Math.abs(var27[((Number)var26).intValue()] - var0.mapCarOff);
                                var9 = var34;
                                var29 = (Integer)var25;
                                if (Double.compare(var34, var13) > 0) {
                                    var29 = (Integer)var26;
                                    var9 = var13;
                                }

                                var34 = var9;
                                var25 = var29;
                            } while (var28.hasNext());
                        }
                    }

                    var29 = var29;
                    if (var29 != null) {
                        boolean var42;
                        if (Math.abs(var27[var29.intValue()] - var0.mapCarOff) < var0.laneW) {
                            var42 = true;
                        } else {
                            var42 = false;
                        }

                        if (!var42) {
                            var29 = null;
                        }

                        if (var29 != null) {
                            var43 = var29;
                            break label142;
                        }
                    }
                }

                var43 = -1;
            }

            double[] var46 = drawMapRoads$cOf(var5, var6);
            double[] var33 = drawMapRoads$hOf(var5, var6);
            if (var21 <= 0) {
                int var22 = 0;
                var9 = var11;

                while (true) {
                    int var19;
                    if (var22 == 0) {
                        var19 = var3;
                    } else {
                        var19 = var4;
                    }

                    if (var22 == var43) {
                        var18 = LaneTracks.Companion.effortColor(var19, var0.tracks.getEffort());
                    } else {
                        var18 = var19;
                    }

                    if (var22 != 0 && var22 != var21) {
                        var11 = 0.07;
                    } else {
                        var11 = 0.08;
                    }

                    double var41;
                    if (var22 == var43 && var0.tracks.getEffort() > 0.55F) {
                        var41 = 0.03;
                    } else {
                        var41 = 0.0;
                    }

                    double[] var31;
                    if (var46 != null && var33 != null) {
                        int var24 = var0.curN;
                        var31 = new double[var24];

                        for (int var23 = 0; var23 < var24; var23++) {
                            double var35 = var46[var23] - var33[var23] * var9;
                            if (var22 == 0) {
                                var35 += var9 * 0.3;
                            } else if (var22 == var21) {
                                var35 = var46[var23] + var33[var23] * var9 - var9 * 0.3;
                            } else {
                                var35 += var22 * var9 * var0.laneW;
                            }

                            var31[var23] = var35;
                        }
                    } else {
                        var31 = null;
                    }

                    var19 = var0.curN;
                    double var37 = var27[var22];
                    double var15 = var0.MAP_R;
                    if (var22 != 0 && var22 != var21) {
                        var2 = true;
                    } else {
                        var2 = false;
                    }

                    var0.polyLine(var19, var37 * var9, var11 + var41, var18, 0.04F, var15, var2, var31);
                    if (var22 == var21) {
                        break;
                    }

                    var22++;
                }
            }

            if (var20) {
                var0.mapLanes = true;
            }

            return Unit.INSTANCE;
        }
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

    /* VF: from Vineflower (jadx: Removed duplicated region;incorrect) */
    private final Pair<OsmRoads.Way, Integer> continuation(OsmRoads.Way var1, int var2, List<OsmRoads.Way> var3) {
        int var22 = var1.getE().length;
        int var24 = 2;
        if (var22 < 2) {
            return null;
        } else {
            int var23;
            if (var2 == 0) {
                var23 = 0;
            } else {
                var23 = var1.getE().length - 1;
            }

            if (var2 == 0) {
                var22 = 1;
            } else {
                var22 = var1.getE().length - 2;
            }

            double var4 = var1.getE()[var23];
            double var6 = var1.getN()[var23];
            double var12 = var4 - var1.getE()[var22];
            double var10 = var6 - var1.getN()[var22];
            double var8 = RangesKt.coerceAtLeast(Math.hypot(var12, var10), 1.0E-6);
            double var16 = var12 / var8;
            double var18 = var10 / var8;
            OsmRoads.Way var26 = null;
            var10 = Math.cos(Math.toRadians(35.0));
            Iterator var27 = var3.iterator();
            Pair var32 = var26;
            byte var28 = (byte)var24;

            while (var27.hasNext()) {
                var26 = (OsmRoads.Way)var27.next();
                if (var26.getId() != var1.getId()
                    && var26.getE().length >= var28
                    && this.laneMapped(var26) == this.laneMapped(var1)
                    && var26.getHighway() == var1.getHighway()) {
                    boolean var29;
                    if (var1.getName().length() > 0) {
                        var29 = true;
                    } else {
                        var29 = false;
                    }

                    boolean var25;
                    label88: {
                        if (!var29) {
                            if (var26.getName().length() > 0) {
                                var29 = (boolean)true;
                            } else {
                                var29 = (boolean)false;
                            }

                            if (!var29) {
                                var25 = Intrinsics.areEqual(var26.getKind(), var1.getKind());
                                break label88;
                            }
                        }

                        var25 = OsmRoads.INSTANCE.sameStreet(var26.getName(), var1.getName());
                    }

                    if (var25) {
                        var29 = false;

                        while (var29 < 2) {
                            if (!var29) {
                                var23 = 0;
                            } else {
                                var23 = var26.getE().length - 1;
                            }

                            if (!var29) {
                                var24 = 1;
                            } else {
                                var24 = var26.getE().length - 2;
                            }

                            if (!(Math.abs(var26.getE()[var23] - var4) > 0.5)) {
                                if (Math.abs(var26.getN()[var23] - var6) > 0.5) {
                                    var12 = var10;
                                } else {
                                    double var20 = var26.getE()[var24] - var26.getE()[var23];
                                    double var14 = var26.getN()[var24] - var26.getN()[var23];
                                    var12 = RangesKt.coerceAtLeast(Math.hypot(var20, var14), 1.0E-6);
                                    var14 = var16 * (var20 / var12) + var18 * (var14 / var12);
                                    var12 = var10;
                                    if (var14 > var10) {
                                        var12 = var14;
                                        var32 = TuplesKt.to(var26, var29);
                                    }
                                }
                            } else {
                                var12 = var10;
                            }

                            var29++;
                            var10 = var12;
                        }

                        var28 = 2;
                    } else {
                        var28 = 2;
                    }
                }
            }

            return var32;
        }
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

    /* VF: from Vineflower (jadx: Removed duplicated region;incorrect) */
    private static final boolean bandsFor$sameStreet(SceneView var0, OsmRoads.Way var1, OsmRoads.Way var2) {
        boolean var6 = var1.getHighway();
        boolean var4 = var2.getHighway();
        boolean var5 = false;
        if (var6 == var4 && var0.laneMapped(var1) == var0.laneMapped(var2)) {
            boolean var3;
            if (var1.getName().length() > 0) {
                var3 = true;
            } else {
                var3 = false;
            }

            label29: {
                if (!var3) {
                    if (var2.getName().length() > 0) {
                        var3 = true;
                    } else {
                        var3 = false;
                    }

                    if (!var3) {
                        var4 = Intrinsics.areEqual(var1.getKind(), var2.getKind());
                        break label29;
                    }
                }

                var4 = OsmRoads.INSTANCE.sameStreet(var1.getName(), var2.getName());
            }

            if (var4) {
                return true;
            }
        }

        return var5;
    }

    /* VF: from Vineflower (jadx: Code restructure failed;Removed duplicated region;incorrect) */
    private static final Pair<Double, Double> bandsFor$joint(
        List<OsmRoads.Piece> var0, IdentityHashMap<OsmRoads.Piece, Pair<Double, Double>> var1, SceneView var2, OsmRoads.Piece var3, int var4
    ) {
        int var21 = var3.getX().length;
        if (var21 < 2) {
            return null;
        } else {
            int var20;
            if (var4 == 0) {
                var20 = 0;
            } else {
                var20 = var21 - 1;
            }

            int var19;
            if (var4 == 0) {
                var19 = 1;
            } else {
                var19 = var21 - 2;
            }

            double var9 = var3.getX()[var20] - var3.getX()[var19];
            double var7 = var3.getY()[var20] - var3.getY()[var19];
            double var5 = RangesKt.coerceAtLeast(Math.hypot(var9, var7), 1.0E-6);
            double var13 = var9 / var5;
            double var15 = var7 / var5;
            OsmRoads.Piece var24 = null;
            var7 = Math.cos(Math.toRadians(35.0));
            Iterator var25 = var0.iterator();
            Pair var26 = var24;

            while (var25.hasNext()) {
                var24 = (OsmRoads.Piece)var25.next();
                if (var24 != var3 && var24.getX().length >= 2 && bandsFor$sameStreet(var2, var3.getWay(), var24.getWay())) {
                    var21 = 0;

                    while (var21 < 2) {
                        int var22;
                        if (var21 == 0) {
                            var22 = 0;
                        } else {
                            var22 = var24.getX().length - 1;
                        }

                        int var23;
                        if (var21 == 0) {
                            var23 = 1;
                        } else {
                            var23 = var24.getX().length - 2;
                        }

                        if (!(Math.abs(var24.getX()[var22] - var3.getX()[var20]) > 0.5)) {
                            if (Math.abs(var24.getY()[var22] - var3.getY()[var20]) > 0.5) {
                                var9 = var7;
                            } else {
                                double var17 = var24.getX()[var23] - var24.getX()[var22];
                                var9 = var24.getY()[var23] - var24.getY()[var22];
                                double var11 = RangesKt.coerceAtLeast(Math.hypot(var17, var9), 1.0E-6);
                                var11 = var13 * (var17 / var11) + var15 * (var9 / var11);
                                var9 = var7;
                                if (var11 > var7) {
                                    var9 = var11;
                                    var26 = TuplesKt.to(var24, var21);
                                }
                            }
                        } else {
                            var9 = var7;
                        }

                        var21++;
                        var7 = var9;
                    }
                }
            }

            if (var26 == null) {
                return null;
            } else {
                OsmRoads.Piece var31 = (OsmRoads.Piece)var26.component1();
                var19 = ((Number)var26.component2()).intValue();
                Object var27 = var1.get(var31);
                Intrinsics.checkNotNull(var27);
                var27 = (Pair)var27;
                var5 = ((Number)var27.component1()).doubleValue();
                var9 = ((Number)var27.component2()).doubleValue();
                boolean var32;
                if ((var4 != 1 || var19 != 0) && (var4 != 0 || var19 != 1)) {
                    var32 = false;
                } else {
                    var32 = true;
                }

                var27 = var1.get(var3);
                Intrinsics.checkNotNull(var27);
                var27 = (Pair)var27;
                double var40 = ((Number)var27.component1()).doubleValue();
                var7 = ((Number)var27.component2()).doubleValue();
                if (!var32) {
                    var5 = -var5;
                }

                var13 = 2;
                return TuplesKt.to((var40 + var5) / var13, (var7 + var9) / var13);
            }
        }
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

    /* VF: from Vineflower (jadx: Code restructure failed;Removed duplicated region;incorrect) */
    private final void rebuildRibbons(ModelSnapshot var1, Telemetry var2, boolean var3) {
        SceneView var30 = this;
        SceneView.Palette var43 = this.getPal();
        this.beginRibbons();
        LaneTracks var32 = this.tracks;
        LaneMarks var31 = this.laneMarks;
        LaneMarks.Mark[] var111;
        if (var31 != null) {
            var111 = var31.getMarks();
        } else {
            var111 = null;
        }

        int var17;
        boolean var104;
        label641: {
            var32.update(var1, var2, 0.05F, true, var111);
            this.pathStateNow = LaneTracks.Companion.pathState(var2);
            this.laneLinesOn = var2.s("lane_lines");
            var104 = var2.s("map_roads");
            var17 = 1;
            if (var104) {
                try {
                    var104 = var30.drawMapRoads(var1, var2.getVEgo());
                } catch (Exception var46) {
                    Log.e("StarView/Scene", "map draw", var46);
                    this.mapStatus = StringsKt.take("map error: " + var46, 90);
                    var104 = false;
                }

                if (var104) {
                    var104 = true;
                    break label641;
                }
            }

            var104 = false;
        }

        float var8 = var2.getVEgo();
        boolean var27;
        if (!var104) {
            var27 = true;
        } else {
            var27 = false;
        }

        this.cleanRoad(var1, var8, var27);
        this.layoutHideRoad = var104;
        LaneTracks.PathState var33 = this.pathStateNow;
        var27 = var2.s("lane_lines");
        String var121 = "lane_lines";
        int var65;
        String var112;
        LaneTracks.PathState var122;
        String var123;
        if (var27) {
            if (var104 && this.mapLanes) {
                var112 = "copyOf(...)";
                var122 = var33;
                var65 = 1;
                var123 = "lane_lines";
            } else {
                int var18;
                if (var2.s("lane_centering_cue")) {
                    var18 = LaneCentering.direction$default(LaneCentering.INSTANCE, var2, 0L, 2, null);
                } else {
                    var18 = 0;
                }

                var112 = "copyOf(...)";
                int var13;
                if (!this.night && !this.alwaysDark) {
                    var13 = 0;
                } else {
                    var13 = 1;
                }

                int var14;
                if ((var13 != 0)) {
                    var14 = Color.rgb(214, 218, 224);
                } else {
                    var14 = -1;
                }

                short var15;
                short var16;
                if ((var13 != 0)) {
                    var15 = 174;
                    var13 = 43;
                    var16 = 217;
                } else {
                    var15 = 194;
                    var13 = 48;
                    var16 = 242;
                }

                int var24 = Color.rgb(var16, var15, var13);
                Triple var34 = this.tracks.inner();
                LaneTracks.Line var36 = (LaneTracks.Line)var34.component1();
                LaneTracks.Line var38 = (LaneTracks.Line)var34.component2();
                LaneTracks.Line var37 = (LaneTracks.Line)var34.component3();
                var27 = this.centreLineOnLeft();
                Iterator var35 = this.tracks.getLines().iterator();
                Intrinsics.checkNotNullExpressionValue(var35, "iterator(...)");
                SceneView var124 = this;
                var65 = var17;
                Iterator var107 = var35;

                while (var107.hasNext()) {
                    var35 = (Iterator)var107.next();
                    Intrinsics.checkNotNullExpressionValue(var35, "next(...)");
                    LaneTracks.Line var41 = (LaneTracks.Line)var35;
                    if (!(var41.getAlpha() < 0.03F)) {
                        LaneMarks.Mark var129 = var41.getMark();
                        if (var129 != null) {
                            var16 = var129.getKind();
                        } else {
                            var16 = 0;
                        }

                        if (var16 != var65 && (!var104 || var16 == 2 || var16 == 3)) {
                            float[] var39 = var41.getX();
                            boolean var82;
                            if (var41 != var36 && var41 != var38) {
                                var82 = false;
                            } else {
                                var82 = true;
                            }

                            float[] var42 = var124.shownY(var41);
                            int var66;
                            if ((var18 <= 0 || var41 != var38) && (var18 >= 0 || var41 != var36)) {
                                var66 = 0;
                            } else {
                                var66 = 1;
                            }

                            boolean var94;
                            if (!var27 || var41 != var36 || var129 == null || var16 != 2 && var16 != 3 || var129.getYellow() && !var129.getDouble() && var16 == 2) {
                                var94 = false;
                            } else {
                                var94 = true;
                            }

                            if ((var66 != 0)) {
                                var66 = Color.rgb(0, 176, 220);
                            } else if (!var94 && (var129 == null || var16 == 0 || !var129.getYellow())) {
                                var66 = var14;
                            } else {
                                var66 = var24;
                            }

                            if (var41 == var37 && var33 == LaneTracks.PathState.ENGAGED) {
                                var66 = LaneTracks.Companion.effortColor((((var66 != 0)) ? 1 : 0), var124.tracks.getEffort());
                            }

                            short var19;
                            if (var16 == 0) {
                                var19 = 150;
                            } else {
                                var19 = 255;
                            }

                            int var21 = RangesKt.coerceIn((int)(var19 * var41.getAlpha()), 0, 255);
                            int var25 = Companion.withAlpha((((var66 != 0)) ? 1 : 0), var21);
                            boolean var68;
                            if (var94 || var129 != null && var16 != 0 && var129.getDouble()) {
                                var68 = true;
                            } else {
                                var68 = false;
                            }

                            label556: {
                                if (!var94 && !var68) {
                                    if (var16 == 0) {
                                        break label556;
                                    }

                                    if (var16 == 2) {
                                        var82 = true;
                                        break label556;
                                    }
                                }

                                var82 = false;
                            }

                            float[] var130;
                            if (var68) {
                                var130 = new float[]{-0.11F, 0.11F};
                            } else {
                                var130 = new float[]{0.0F};
                            }

                            float[] var40 = var130;
                            double var4;
                            if (var130.length == 2) {
                                var4 = 0.05;
                            } else {
                                var4 = 0.07;
                            }

                            double var6;
                            if (var41 == var37 && var124.tracks.getEffort() > 0.55F && var33 == LaneTracks.PathState.ENGAGED) {
                                var6 = 0.03;
                            } else {
                                var6 = 0.0;
                            }

                            var4 += var6;
                            float[] var44 = new float[230];
                            float[] var154 = new float[230];
                            SceneView var131 = var124;
                            var8 = Math.max(0.0F, var39[0]);
                            var94 = true;
                            var68 = false;

                            for (float[] var132 = var39; var8 <= Math.min(110.0F, ArraysKt.last(var132)) && var68 < 230; var8 += 0.5F) {
                                while (var94 < var132.length - 1 && var132[((var94) ? 1 : 0)] < var8) {
                                    var94++;
                                }

                                float var9 = RangesKt.coerceIn((var8 - var132[var94 - 1]) / Math.max(0.01F, var132[((var94) ? 1 : 0)] - var132[var94 - 1]), 0.0F, 1.0F);
                                var44[((var68) ? 1 : 0)] = var8;
                                var154[((var68) ? 1 : 0)] = var42[var94 - 1] + (var42[((var94) ? 1 : 0)] - var42[var94 - 1]) * var9;
                                var68++;
                            }

                            int var96 = 1;
                            Unit var149 = Unit.INSTANCE;
                            if (var68 >= 2) {
                                var42 = Arrays.copyOf(var44, var68);
                                Intrinsics.checkNotNullExpressionValue(var42, var112);
                                SceneView$$ExternalSyntheticLambda14 var160 = new SceneView$$ExternalSyntheticLambda14(var42);
                                int var22 = var130.length;
                                int var20 = 0;
                                SceneView var113 = var124;
                                float[] var125 = var154;

                                for (float[] var155 = var42; var20 < var22; var20++) {
                                    var8 = var40[var20];
                                    var42 = new float[((var68) ? 1 : 0)];

                                    for (int var97 = 0; var97 < var68; var97++) {
                                        var42[var97] = var125[var97] + var8;
                                    }

                                    if (!var82) {
                                        ribbon$default(this, var155, var42, null, var4, var25, 0, 0, 0.08F, var160, 96, null);
                                    } else {
                                        float var57 = (float)(var113.odoDist % 9.0);
                                        var96 = 0;

                                        while (var96 < var68) {
                                            var8 = (var155[var96] + var57) % 9.0F;
                                            if (var8 < 3.0F) {
                                                int var23 = var96;

                                                while (var23 + 1 < var68 && (var155[var23 + 1] + var57) % 9.0F >= var8 && (var155[var23 + 1] + var57) % 9.0F < 3.0F) {
                                                    var23++;
                                                }

                                                if (var23 > var96) {
                                                    this.ribbon(var155, var42, null, var4 + 0.01, var25, var96, var23, 0.08F, var160);
                                                }

                                                var96 = var23 + 1;
                                                Unit var45 = Unit.INSTANCE;
                                            } else {
                                                var96++;
                                            }
                                        }
                                    }
                                }

                                var65 = var96;
                                var124 = var113;
                                var112 = var112;
                            } else {
                                var65 = 1;
                            }
                        }
                    }
                }

                var30 = var124;
                var123 = var121;
                var122 = var33;
            }
        } else {
            var112 = "copyOf(...)";
            var122 = var33;
            var65 = 1;
            var123 = "lane_lines";
        }

        LaneMemory var126 = var30.laneMemory;
        if (var126 != null && var2.s(var123) && var2.s("lane_memory_lines") && (!var104 || !var30.mapLanes)) {
            Triple var135 = var30.tracks.inner();
            LaneTracks.Line var140 = (LaneTracks.Line)var135.component1();
            LaneTracks.Line var141 = (LaneTracks.Line)var135.component2();
            Iterable var142 = var30.tracks.getLines();
            var var136 = new ArrayList();

            for (Object var142$it : (Iterable) var142) {
                var142 = (Iterable) var142$it;
                byte var72;
                if (((LaneTracks.Line)var142).getAlpha() > 0.2F) {
                    var72 = (byte)var65;
                } else {
                    var72 = 0;
                }

                if (var72 != 0) {
                    var136.add(var142);
                }
            }

            List var137 = var136;
            var142 = var137;
            var var138 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var142, 10));
            Iterator var145 = var142.iterator();

            while (var145.hasNext()) {
                var138.add(((LaneTracks.Line)var145.next()).getY0());
            }

            List var153 = var138;
            byte var91;
            if (!var30.night && !var30.alwaysDark) {
                var91 = 0;
            } else {
                var91 = (byte)var65;
            }

            Iterator var156 = var126.getLines().iterator();
            String var127 = var112;

            while (var156.hasNext()) {
                Pair var114 = (Pair)var156.next();
                float var11 = ((Number)var114.component1()).floatValue();
                float var58 = ((Number)var114.component2()).floatValue();
                if (!(var58 < 0.45F)) {
                    var var115 = var153;
                    int var74;
                    if (var115 instanceof Collection && ((Collection)var115).isEmpty()) {
                        var74 = 0;
                    } else {
                        label695: {
                            Iterator var116 = var115.iterator();

                            while (var116.hasNext()) {
                                if (Math.abs(((Number)var116.next()).floatValue() - var11) < 0.8F) {
                                    var74 = (byte)var65;
                                } else {
                                    var74 = (byte)0;
                                }

                                if (var74 != 0) {
                                    var74 = var65;
                                    break label695;
                                }
                            }

                            var74 = 0;
                        }
                    }

                    if (var74 == 0) {
                        LaneTracks.Line var117;
                        if (var11 < 0.0F) {
                            var117 = var140;
                        } else {
                            var117 = var141;
                        }

                        if (var117 != null) {
                            float[] var159 = var30.shownY(var117);
                            float[] var151 = var117.getX();
                            if (var151.length >= 2) {
                                float var10 = var117.getY0();
                                float[] var139 = new float[140];
                                float[] var146 = new float[140];
                                SceneView var161 = var30;
                                var8 = Math.max(0.0F, var151[0]);
                                int var83 = 1;

                                for (var74 = 0; var8 <= Math.min(70.0F, ArraysKt.last(var151)) && var74 < 140; var8 += 0.5F) {
                                    while (var83 < var151.length - 1 && var151[var83] < var8) {
                                        var83++;
                                    }

                                    float var12 = RangesKt.coerceIn((var8 - var151[var83 - 1]) / Math.max(0.01F, var151[var83] - var151[var83 - 1]), 0.0F, 1.0F);
                                    var139[var74] = var8;
                                    var146[var74] = var159[var83 - 1] + (var159[var83] - var159[var83 - 1]) * var12 + (var11 - var10);
                                    var74++;
                                }

                                Unit var118 = Unit.INSTANCE;
                                if (var74 >= 2) {
                                    SceneView.Companion var119 = Companion;
                                    if (var91 != 0) {
                                        var83 = Color.rgb(176, 184, 196);
                                    } else {
                                        var83 = -1;
                                    }

                                    int var102 = var119.withAlpha(var83, RangesKt.coerceIn((int)(110 * var58), 30, 110));
                                    var8 = (float)(var30.odoDist % 9.0);
                                    var17 = (byte)0;
                                    var83 = var74;
                                    float[] var108 = var146;
                                    var74 = var17;

                                    while (var74 < var83) {
                                        var58 = (var139[var74] + var8) % 9.0F;
                                        if (var58 < 3.0F) {
                                            var17 = var74;

                                            while (var17 + 1 < var83 && (var139[var17 + 1] + var8) % 9.0F >= var58 && (var139[var17 + 1] + var8) % 9.0F < 3.0F) {
                                                var17++;
                                            }

                                            if (var17 > var74) {
                                                var151 = Arrays.copyOf(var139, var83);
                                                Intrinsics.checkNotNullExpressionValue(var151, var127);
                                                float[] var147 = Arrays.copyOf(var108, var83);
                                                Intrinsics.checkNotNullExpressionValue(var147, var127);
                                                this.ribbon(var151, var147, null, 0.06, var102, var74, var17, 0.08F, new SceneView$$ExternalSyntheticLambda15(var139));
                                            }

                                            Unit var148 = Unit.INSTANCE;
                                            var74 = var17 + 1;
                                        } else {
                                            var74++;
                                        }
                                    }

                                    var30 = this;
                                } else {
                                    var30 = this;
                                }
                            } else {
                                var30 = this;
                            }
                        }
                    }
                } else {
                    var30 = this;
                }
            }
        }

        if (var2.s(var123) && var2.s("lane_change_lane") && this.tracks.getChangeAlpha() > 0.02F) {
            float[] var109 = new float[121];

            for (int var77 = 0; var77 < 121; var77++) {
                var109[var77] = var77 * 0.5F;
            }

            int var86 = var109.length;
            float[] var120 = new float[var86];

            for (int var78 = 0; var78 < var86; var78++) {
                var120[var78] = this.tracks.targetCenter(var109[var78], new SceneView$$ExternalSyntheticLambda16(this));
            }

            for (float var55 : var120) {
                byte var87;
                if (!Float.isInfinite(var55) && !Float.isNaN(var55)) {
                    var87 = (byte)var65;
                } else {
                    var87 = 0;
                }

                if (var87 == 0) {
                    var65 = 0;
                    break;
                }
            }

            if (var65 != 0) {
                var65 = (int)(70 * this.tracks.getChangeAlpha());
                ribbon$default(
                    this,
                    var109,
                    var120,
                    null,
                    this.tracks.getLaneWidth() * 0.4,
                    Companion.withAlpha(Color.rgb(0, 176, 220), var65),
                    0,
                    0,
                    0.07F,
                    new SceneView$$ExternalSyntheticLambda17(var109),
                    96,
                    null
                );
            }
        }

        if (var2.s("model_ui")) {
            if (!var3) {
                var65 = var43.getPathOff();
            } else if (var122 == LaneTracks.PathState.OVERRIDE) {
                var65 = Color.rgb(138, 143, 150);
            } else if (this.uiStatus == UiStatus.EXPERIMENTAL) {
                var65 = var43.getPathExp();
            } else {
                var65 = var43.getPathOn();
            }

            double var49 = RangesKt.coerceIn(var2.pFloat("PathWidth", 1.0F) * 0.5, 0.3, 1.2);
            int var93 = var1.getPosX().length;
            long var28 = System.nanoTime();
            float var60;
            if (this.pulseLastNs == 0L) {
                var60 = 0.0F;
            } else {
                var60 = RangesKt.coerceIn((float)(var28 - this.pulseLastNs) / 1.0E9F, 0.0F, 0.2F);
            }

            this.pulseLastNs = var28;
            this.pulseAccel = this.pulseAccel + var60 / (var60 + 0.25F) * (var2.getAEgo() - this.pulseAccel);
            if (var2.s("path_pulse")) {
                var8 = PathPulse.INSTANCE.broken(this.pulseAccel);
            } else {
                var8 = 0.0F;
            }

            this.pulsePhase = PathPulse.INSTANCE.advance(this.pulsePhase, this.pulseAccel, var8, var60);
            if (!(var8 < 0.01F) && var93 >= 2) {
                var17 = Math.max(2, (int)(Math.min(120.0F, ArraysKt.last(var1.getPosX())) / 0.5F) + 1);
                float[] var47 = new float[var17];

                for (int var80 = 0; var80 < var17; var80++) {
                    var47[var80] = var80 * 0.5F;
                }

                float[] var110 = new float[var17];

                for (int var81 = 0; var81 < var17; var81++) {
                    var60 = var47[var81];
                    int var88 = 1;

                    while (var88 < var93 - 1 && var1.getPosX()[var88] < var60) {
                        var88++;
                    }

                    var60 = RangesKt.coerceIn((var60 - var1.getPosX()[var88 - 1]) / Math.max(0.01F, var1.getPosX()[var88] - var1.getPosX()[var88 - 1]), 0.0F, 1.0F);
                    var110[var81] = var1.getPosY()[var88 - 1] + (var1.getPosY()[var88] - var1.getPosY()[var88 - 1]) * var60;
                }

                var60 = Math.max(1.0F, ArraysKt.last(var1.getPosX()));
                ribbon$default(
                    this,
                    var47,
                    var110,
                    null,
                    var49,
                    Companion.withAlpha(var65, 235),
                    0,
                    0,
                    0.09F,
                    new SceneView$$ExternalSyntheticLambda1(var47, var60, this, var8),
                    96,
                    null
                );
            } else {
                ribbon$default(
                    this,
                    var1.getPosX(),
                    var1.getPosY(),
                    null,
                    var49,
                    Companion.withAlpha(var65, 235),
                    0,
                    0,
                    0.09F,
                    new SceneView$$ExternalSyntheticLambda18(var93),
                    96,
                    null
                );
            }
        }

        this.endRibbons();
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
