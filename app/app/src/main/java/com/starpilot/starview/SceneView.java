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
import android.view.Choreographer.FrameCallback;
import android.view.GestureDetector.OnGestureListener;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;
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
import com.google.android.filament.TransformManager;
import com.google.android.filament.VertexBuffer;
import com.google.android.filament.View;
import com.google.android.filament.Viewport;
import com.google.android.filament.ColorGrading.Builder;
import com.google.android.filament.IndexBuffer.Builder.IndexType;
import com.google.android.filament.LightManager.ShadowOptions;
import com.google.android.filament.RenderableManager.PrimitiveType;
import com.google.android.filament.Renderer.ClearOptions;
import com.google.android.filament.Texture.Format;
import com.google.android.filament.Texture.InternalFormat;
import com.google.android.filament.Texture.PixelBufferDescriptor;
import com.google.android.filament.Texture.Sampler;
import com.google.android.filament.Texture.Type;
import com.google.android.filament.TextureSampler.MagFilter;
import com.google.android.filament.TextureSampler.MinFilter;
import com.google.android.filament.TextureSampler.WrapMode;
import com.google.android.filament.ToneMapper.Linear;
import com.google.android.filament.VertexBuffer.AttributeType;
import com.google.android.filament.VertexBuffer.VertexAttribute;
import com.google.android.filament.View.AntiAliasing;
import com.google.android.filament.View.BloomOptions;
import com.google.android.filament.View.DynamicResolutionOptions;
import com.google.android.filament.View.MultiSampleAntiAliasingOptions;
import com.google.android.filament.View.ShadowType;
import com.google.android.filament.android.DisplayHelper;
import com.google.android.filament.android.UiHelper;
import com.google.android.filament.android.UiHelper.ContextErrorPolicy;
import com.google.android.filament.android.UiHelper.RendererCallback;
import com.google.android.filament.gltfio.AssetLoader;
import com.google.android.filament.gltfio.FilamentAsset;
import com.google.android.filament.gltfio.ResourceLoader;
import com.google.android.filament.gltfio.UbershaderProvider;
import com.google.android.filament.utils.KTX1Loader;
import com.google.android.filament.utils.Utils;
import java.io.InputStream;
import java.nio.Buffer;
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
import java.util.Map.Entry;
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

public final class SceneView extends SurfaceView {
   public static final SceneView.Companion Companion = new SceneView.Companion(null);
   public static final float DEFAULT_DISTANCE = 26.0F;
   public static final float DEFAULT_TILT = 17.0F;
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
   private final SceneView.Palette day;
   private Detector detector;
   private DisplayHelper displayHelper;
   private float distance;
   private final float[][] edgeF;
   private float edgeOk;
   private final float[][] edgeS;
   private SceneView.Car ego;
   private Engine engine;
   private boolean engineAlive;
   private volatile String failure;
   private final double fovDeg;
   private final FrameCallback frameCallback;
   private final String[] genericKinds;
   private final ArrayList<SceneView.Car> genericPool;
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
   private SceneView.LaneHint hintNow;
   private Set<Long> hintSame;
   private final double horizonShift;
   private IndirectLight ibl;
   private final ByteBuffer[] ibufs;
   private volatile SceneView.LaneHint laneHint;
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
   private final SceneView.Palette nightP;
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
   private final ArrayDeque<SceneView.Pt> parked;
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
   private final ArrayDeque<SceneView.Snap> snaps;
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

   // $VF: synthetic method
   public static void $r8$lambda$2NzaRdESRR9pNwGJrRJzxmhk_TI(SceneView var0) {
      longPressRun$lambda$0(var0);
   }

   // $VF: synthetic method
   public static float $r8$lambda$4oohRu7_UI6yo1RsxFCVPIMj3Xg/* $VF was: $r8$lambda$4oohRu7_UI6yo1RsxFCVPIMj3Xg*/(float[] var0, int var1) {
      return rebuildRibbons$lambda$100(var0, var1);
   }

   // $VF: synthetic method
   public static double $r8$lambda$5p2gz_XQvmBAUQl9gJnijrdAN_w(float[] var0, int var1) {
      return updateScene$edgeAtX$lambda$34(var0, var1);
   }

   // $VF: synthetic method
   public static float $r8$lambda$691Dfqgfj_RR1VxAS11f3LMdUFc/* $VF was: $r8$lambda$691Dfqgfj_RR1VxAS11f3LMdUFc*/(
      float[] var0, float var1, SceneView var2, float var3, int var4
   ) {
      return rebuildRibbons$lambda$105(var0, var1, var2, var3, var4);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$GvWHzku2uyG_Aff2hd0XADfAqNY/* $VF was: $r8$lambda$GvWHzku2uyG_Aff2hd0XADfAqNY*/(
      Set var0, SceneView var1, int var2, IdentityHashMap var3, OsmRoads.Piece var4
   ) {
      return drawMapRoads$lambda$83(var0, var1, var2, var3, var4);
   }

   // $VF: synthetic method
   public static Pair $r8$lambda$Kgsj4qNoMkf2lQ8lVrXztFnTOnc(SceneView var0, double var1, double var3, double var5) {
      return drawMapRoads$lambda$72(var0, var1, var3, var5);
   }

   // $VF: synthetic method
   public static Pair $r8$lambda$MVEatnl9HPbxTVkWRrxclZUQyVY(SceneView var0, OsmRoads.Piece var1) {
      return drawMapRoads$lambda$82(var0, var1);
   }

   // $VF: synthetic method
   public static double $r8$lambda$PiXpP9yS_lvnGpn7_AkRvirwDvI(ModelSnapshot var0, SceneView var1, double var2) {
      return updateScene$lambda$30(var0, var1, var2);
   }

   // $VF: synthetic method
   public static float[] $r8$lambda$Q8l8i85KuODXQJt5R6Zxyn_STFE/* $VF was: $r8$lambda$Q8l8i85KuODXQJt5R6Zxyn_STFE*/(SceneView var0, LaneTracks.Line var1) {
      return rebuildRibbons$lambda$101(var0, var1);
   }

   // $VF: synthetic method
   public static float $r8$lambda$VwoX0tuoqee_VCPB9ki65aqvDoM/* $VF was: $r8$lambda$VwoX0tuoqee_VCPB9ki65aqvDoM*/(float[] var0, int var1) {
      return rebuildRibbons$lambda$103(var0, var1);
   }

   // $VF: synthetic method
   public static float $r8$lambda$ZC0LhQLx6a1c6MiLYPpYDwo23k4(float[] var0, Function1 var1, int var2) {
      return cleanRoad$lambda$52(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$b0znyBh1LmWIo_8DN8VsXrbAupM(SceneView var0, int var1, IdentityHashMap var2, OsmRoads.Piece var3) {
      return drawMapRoads$lambda$84(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$cyr5y8faHvctlAN8F_B8Rp71L_E/* $VF was: $r8$lambda$cyr5y8faHvctlAN8F_B8Rp71L_E*/(
      SceneView var0, int var1, IdentityHashMap var2, OsmRoads.Piece var3
   ) {
      return drawMapRoads$lambda$85(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static double $r8$lambda$gSx8L_fuj9H5zcorrlYfxeJzeWI(double[] var0, int var1) {
      return updateScene$curbAt$lambda$33(var0, var1);
   }

   // $VF: synthetic method
   public static float $r8$lambda$hKW9CIQNeqAxmM49hynuaMuVLSw(float[] var0, int var1) {
      return rebuildRibbons$lambda$95(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$mwHWByEQzX2K71Li2yrXv1THry4(
      SceneView var0, OsmRoads.Match var1, boolean var2, int var3, int var4, IdentityHashMap var5, OsmRoads.Piece var6
   ) {
      return drawMapRoads$lambda$88(var0, var1, var2, var3, var4, var5, var6);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$vh1WZzy_7rBKR_jkk6tM0jkLHXk/* $VF was: $r8$lambda$vh1WZzy_7rBKR_jkk6tM0jkLHXk*/(
      HashSet var0, SceneView var1, int var2, IdentityHashMap var3, OsmRoads.Piece var4
   ) {
      return drawMapRoads$lambda$89(var0, var1, var2, var3, var4);
   }

   // $VF: synthetic method
   public static float $r8$lambda$wfBpZA5jpN_a_Rnu_ZxDTS0EIjU/* $VF was: $r8$lambda$wfBpZA5jpN_a_Rnu_ZxDTS0EIjU*/(float var0, float[] var1, int var2) {
      return cleanRoad$lambda$49(var0, var1, var2);
   }

   // $VF: synthetic method
   public static float $r8$lambda$xng_Uk1e_mQq72CoCEIeiOefeQ8/* $VF was: $r8$lambda$xng_Uk1e_mQq72CoCEIeiOefeQ8*/(int var0, int var1) {
      return rebuildRibbons$lambda$104(var0, var1);
   }

   public SceneView(Context var1, AttributeSet var2) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      super(var1, var2);
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

         public boolean onScale(ScaleGestureDetector var1) {
            Intrinsics.checkNotNullParameter(var1, "d");
            SceneView.this.setDistance(SceneView.this.getDistance() / var1.getScaleFactor());
            SceneView.this.gesturing = true;
            return true;
         }
      }));
      this.taps = new GestureDetector(var1, (OnGestureListener)(new SimpleOnGestureListener() {

         public boolean onDoubleTap(MotionEvent var1) {
            Intrinsics.checkNotNullParameter(var1, "e");
            SceneView.this.setDistance(26.0F);
            SceneView.this.setTiltDeg(17.0F);
            Function2 var2x = SceneView.this.getOnViewChanged();
            if (var2x != null) {
               var2x.invoke(SceneView.this.getDistance(), SceneView.this.getTiltDeg());
            }

            return true;
         }

         public boolean onDown(MotionEvent var1) {
            Intrinsics.checkNotNullParameter(var1, "e");
            return true;
         }

         public boolean onSingleTapConfirmed(MotionEvent var1) {
            Intrinsics.checkNotNullParameter(var1, "e");
            Function0 var2x = SceneView.this.getOnTap();
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

         public void doFrame(long var1) {
            if (SceneView.this.running) {
               Choreographer.getInstance().postFrameCallback(this);
               if (SceneView.this.ready) {
                  UiHelper var4x = SceneView.this.uiHelper;
                  Object var5x = null;
                  UiHelper var3x = var4x;
                  if (var4x == null) {
                     Intrinsics.throwUninitializedPropertyAccessException("uiHelper");
                     var3x = null;
                  }

                  if (var3x.isReadyToRender()) {
                     SwapChain var6x = SceneView.this.swapChain;
                     if (var6x == null) {
                        return;
                     }

                     SceneView.this.updateScene(var1);
                     Renderer var10x = SceneView.this.renderer;
                     Renderer var7x = var10x;
                     if (var10x == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("renderer");
                        var7x = null;
                     }

                     if (var7x.beginFrame(var6x, var1)) {
                        Renderer var11x = SceneView.this.renderer;
                        Renderer var8x = var11x;
                        if (var11x == null) {
                           Intrinsics.throwUninitializedPropertyAccessException("renderer");
                           var8x = null;
                        }

                        View var13x = SceneView.this.view;
                        View var12x = var13x;
                        if (var13x == null) {
                           Intrinsics.throwUninitializedPropertyAccessException("view");
                           var12x = null;
                        }

                        var8x.render(var12x);
                        Renderer var9x = SceneView.this.renderer;
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

   // $VF: synthetic method
   public SceneView(Context var1, AttributeSet var2, int var3, DefaultConstructorMarker var4) {
      if ((var3 & 2) != 0) {
         var2 = null;
      }

      this(var1, var2);
   }

   private final void applyGrading(boolean var1) {
      if (!Intrinsics.areEqual(var1, this.gradingGrey)) {
         this.gradingGrey = var1;
         ColorGrading var6 = this.grading;
         Builder var3 = new Builder().toneMapper(new Linear());
         float var2;
         if (var1) {
            var2 = 0.0F;
         } else {
            var2 = 1.0F;
         }

         Builder var7 = var3.saturation(var2);
         Engine var5 = this.engine;
         Object var4 = null;
         Engine var8 = var5;
         if (var5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("engine");
            var8 = null;
         }

         this.grading = var7.build(var8);
         View var11 = this.view;
         View var9 = var11;
         if (var11 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("view");
            var9 = null;
         }

         var9.setColorGrading(this.grading);
         if (var6 != null) {
            Engine var10 = this.engine;
            if (var10 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("engine");
               var10 = (Engine)var4;
            }

            var10.destroyColorGrading(var6);
         }
      }
   }

   private final void applyPalette() {
      if (this.ready) {
         SceneView.Palette var9 = this.getPal();
         Triple var7 = Companion.linear(var9.getBg());
         float var3 = ((Number)var7.component1()).floatValue();
         float var2 = ((Number)var7.component2()).floatValue();
         float var1 = ((Number)var7.component3()).floatValue();
         Skybox var10 = this.skybox;
         com.google.android.filament.Skybox.Builder var11 = new com.google.android.filament.Skybox.Builder().color(var3, var2, var1, 1.0F);
         Engine var8 = this.engine;
         Engine var16 = var8;
         if (var8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("engine");
            var16 = null;
         }

         this.skybox = var11.build(var16);
         Scene var26 = this.scene;
         Scene var17 = var26;
         if (var26 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("scene");
            var17 = null;
         }

         var17.setSkybox(this.skybox);
         if (var10 != null) {
            var8 = this.engine;
            Engine var18 = var8;
            if (var8 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("engine");
               var18 = null;
            }

            var18.destroySkybox(var10);
         }

         Renderer var28 = this.renderer;
         Renderer var19 = var28;
         if (var28 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("renderer");
            var19 = null;
         }

         ClearOptions var29 = new ClearOptions();
         var29.clearColor = new double[]{var3, var2, var1, 1.0};
         var29.clear = true;
         var19.setClearOptions(var29);
         SceneView.Companion var20 = Companion;
         short var4;
         short var5;
         short var6;
         if (!this.night && !this.alwaysDark) {
            var4 = 230;
            var5 = 234;
            var6 = 228;
         } else {
            var4 = 29;
            var5 = 36;
            var6 = 26;
         }

         var7 = var20.linear(Color.rgb(var6, var4, var5));
         var1 = ((Number)var7.component1()).floatValue();
         var2 = ((Number)var7.component2()).floatValue();
         var3 = ((Number)var7.component3()).floatValue();
         MaterialInstance var22 = this.groundMi;
         if (var22 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("groundMi");
            var22 = null;
         }

         var22.setParameter("color", var1, var2, var3, 1.0F);
         var8 = this.engine;
         Engine var23 = var8;
         if (var8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("engine");
            var23 = null;
         }

         LightManager var24 = var23.getLightManager();
         Intrinsics.checkNotNullExpressionValue(var24, "getLightManager(...)");
         var4 = var24.getInstance(this.sun);
         var24.setIntensity(var4, var9.getSunIntensity());
         var24.setColor(var4, var9.getSunColor()[0], var9.getSunColor()[1], var9.getSunColor()[2]);
         IndirectLight var25 = this.ibl;
         if (var25 != null) {
            var25.setIntensity(var9.getIblIntensity());
         }

         this.paletteDirty = false;
         this.lastRibbonFrame = -2L;
      }
   }

   private final IdentityHashMap<OsmRoads.Piece, Pair<double[], double[]>> bandsFor(
      List<OsmRoads.Piece> var1, Function1<? super OsmRoads.Piece, Pair<Double, Double>> var2
   ) {
      IdentityHashMap var20 = new IdentityHashMap();
      IdentityHashMap var19 = new IdentityHashMap();

      for (OsmRoads.Piece var21 : var1) {
         var19.put(var21, var2.invoke(var21));
      }

      Iterator var29 = var1.iterator();
      IdentityHashMap var26 = var20;

      while (var29.hasNext()) {
         OsmRoads.Piece var30 = (OsmRoads.Piece)var29.next();
         int var17 = Math.min(var30.getX().length, this.mx.length);
         if (var17 >= 2) {
            Object var31 = var19.get(var30);
            Intrinsics.checkNotNull(var31);
            var31 = (Pair)var31;
            double var7 = ((Number)var31.component1()).doubleValue();
            double var9 = ((Number)var31.component2()).doubleValue();
            var31 = bandsFor$joint(var1, var19, this, var30, 0);
            Pair var24 = bandsFor$joint(var1, var19, this, var30, 1);
            double[] var23 = new double[var17];

            for (int var15 = 1; var15 < var17; var15++) {
               var23[var15] = var23[var15 - 1] + Math.hypot(var30.getX()[var15] - var30.getX()[var15 - 1], var30.getY()[var15] - var30.getY()[var15 - 1]);
            }

            double var11 = var23[var17 - 1];
            double[] var25 = new double[var17];
            double[] var22 = new double[var17];
            int var16 = 0;

            for (int var28 = var17; var16 < var28; var16++) {
               double var3 = var7;
               double var5 = var9;
               if (var31 != null) {
                  double var13 = 1 - bandsFor$ease(this, var23[var16]);
                  var3 = var7 + (((Number)var31.getFirst()).doubleValue() - var7) * var13;
                  var5 = var9 + (((Number)var31.getSecond()).doubleValue() - var9) * var13;
               }

               if (var24 != null) {
                  double var27 = 1 - bandsFor$ease(this, var11 - var23[var16]);
                  var3 += (((Number)var24.getFirst()).doubleValue() - var7) * var27;
                  var5 += (((Number)var24.getSecond()).doubleValue() - var9) * var27;
               }

               var25[var16] = var3;
               var22[var16] = var5;
            }

            var26.put(var30, TuplesKt.to(var25, var22));
         }
      }

      return var26;
   }

   private static final double bandsFor$ease(SceneView var0, double var1) {
      var1 = RangesKt.coerceIn(var1 / var0.TAPER, 0.0, 1.0);
      return var1 * var1 * (3 - 2 * var1);
   }

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

   private final int baseLanes(OsmRoads.Way var1) {
      var var5 = var1.getName();
      boolean var2;
      if (var5.length() == 0) {
         var2 = 1;
      } else {
         var2 = 0;
      }

      if (var2) {
         long var3 = var1.getId();
         var5 = "#" + var3;
      }

      var5 = var5;
      var2 = this.lanesOf(var1);
      double[] var6 = this.baseLanesMem.get(var5);
      if (var6 != null && !(var2 < var6[0]) && !(this.odoDist - var6[1] > 1000.0)) {
         if (var2 == (int)var6[0]) {
            var6[1] = this.odoDist;
         }

         return (int)var6[0];
      } else {
         if (this.baseLanesMem.size() > 200) {
            this.baseLanesMem.clear();
         }

         this.baseLanesMem.put(var5, new double[]{var2, this.odoDist});
         return var2;
      }
   }

   private final void beginRibbons() {
      this.bufSel = 1 - this.bufSel;
      this.nVerts = 0;
      this.nIdx = 0;
      ((Buffer)this.getVbuf()).clear();
      ((Buffer)this.getIbuf()).clear();
   }

   private final void bridgeSides(int var1, double var2, double var4, double[] var6, int var7, double var8, double[] var10) {
      Triple var32 = Companion.linear(var7);
      float var20 = ((Number)var32.component1()).floatValue();
      float var19 = ((Number)var32.component2()).floatValue();
      float var21 = ((Number)var32.component3()).floatValue();
      Iterator var56 = CollectionsKt.listOf(new Double[]{-1.0, 1.0}).iterator();

      while (var56.hasNext()) {
         double var11 = ((Number)var56.next()).doubleValue();
         var7 = 0;
         int var25 = var1 - 1;

         while (var7 < var25) {
            double var39;
            float var51;
            float var52;
            int var55;
            Iterator var57;
            if (var6[var7] < 0.05 && var6[var7 + 1] < 0.05) {
               var51 = var21;
               var21 = var19;
               var55 = var25;
               var57 = var56;
               var39 = var11;
               var52 = var20;
            } else {
               if (this.nVerts + 4 > this.maxVerts || this.nIdx + 6 > this.maxIdx) {
                  return;
               }

               int var26 = this.nVerts;
               int var29 = var7;
               int var31 = var7 + 1;
               int var30;
               int var54;
               if (var7 <= var31) {
                  var51 = var19;
                  var52 = var20;
                  var20 = var21;

                  while (true) {
                     var54 = Math.max(0, var29 - 1);
                     var55 = Math.min(var1 - 1, var29 + 1);
                     var39 = this.mx[var55];
                     double var15 = this.mx[var54];
                     var39 -= var15;
                     double var17 = this.my[var55];
                     var15 = this.my[var54];
                     var15 = var17 - var15;
                     var17 = Math.hypot(var39, var15);
                     var17 = RangesKt.coerceAtLeast(var17, 1.0E-6);
                     var39 /= var17;
                     var15 = -(var15 / var17);
                     var15 = this.mx[var29] + (var2 + var11 * var4) * var15;
                     var39 = this.my[var29] + (var2 + var11 * var4) * var39;
                     double[] var33 = this.mx;
                     var21 = RangesKt.coerceIn(
                        (1.0F - RangesKt.coerceIn((float)((Math.hypot(var33[var29], this.my[var29]) - 0.7 * var8) / (0.3 * var8)), 0.0F, 1.0F))
                           * (float)var10[var29],
                        0.0F,
                        1.0F
                     );
                     var54 = this.packColor(var52 * var21, var51 * var21, var20 * var21, var21);
                     this.putVertex((float)var39, (float)(var6[var29] + 0.06), (float)(-var15), var54);
                     var21 = (float)var39;
                     var39 = var6[var29];
                     this.putVertex(var21, (float)Math.max(0.0, var39 - 1.1), (float)(-var15), var54);
                     var21 = var51;
                     var55 = var25;
                     var30 = var26;
                     var51 = var20;
                     var54 = var7;
                     var57 = var56;
                     var39 = var11;
                     var52 = var52;
                     if (var29 == var31) {
                        break;
                     }

                     var29++;
                     var51 = var51;
                     var52 = var52;
                  }
               } else {
                  var52 = var20;
                  var39 = var11;
                  var57 = var56;
                  var54 = var7;
                  var51 = var21;
                  var30 = var26;
                  var55 = var25;
                  var21 = var19;
               }

               this.putTri(var30, var30 + 2, var30 + 1);
               this.putTri(var30 + 1, var30 + 2, var30 + 3);
               var7 = var54;
            }

            var7++;
            var19 = var21;
            var25 = var55;
            var21 = var51;
            var56 = var57;
            var11 = var39;
            var20 = var52;
         }
      }
   }

   private final void buildGround() {
      MaterialInstance var3 = this.groundMi;
      Object var2 = null;
      MaterialInstance var1 = var3;
      if (var3 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("groundMi");
         var1 = null;
      }

      this.groundEnt = this.quad(0.0F, var1, 4, false);
      var1 = this.shadowMi;
      if (var1 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("shadowMi");
         var1 = (MaterialInstance)var2;
      }

      this.shadowEnt = this.quad(0.01F, var1, 5, true);
   }

   private final void buildLayout() {
      com.google.android.filament.Material.Builder var6 = new com.google.android.filament.Material.Builder()
         .payload(this.readAsset("materials/layout.filamat"), this.readAssetSize);
      Engine var5 = this.engine;
      Engine var4 = var5;
      if (var5 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("engine");
         var4 = null;
      }

      this.layoutMaterial = var6.build(var4);
      Material var22 = this.layoutMaterial;
      Material var8 = var22;
      if (var22 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("layoutMaterial");
         var8 = null;
      }

      this.layoutMi = var8.createInstance();
      com.google.android.filament.Texture.Builder var39 = new com.google.android.filament.Texture.Builder()
         .width(80)
         .height(this.MR)
         .levels(1)
         .sampler(Sampler.SAMPLER_2D)
         .format(InternalFormat.SRGB8_A8);
      var5 = this.engine;
      var4 = var5;
      if (var5 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("engine");
         var4 = null;
      }

      this.layoutTex = var39.build(var4);
      ByteBuffer var7 = ByteBuffer.allocateDirect(this.MR * 80 * 4).order(ByteOrder.nativeOrder());
      Texture var24 = this.layoutTex;
      Texture var10 = var24;
      if (var24 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("layoutTex");
         var10 = null;
      }

      Engine var40 = this.engine;
      var5 = var40;
      if (var40 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("engine");
         var5 = null;
      }

      var10.setImage(var5, 0, new PixelBufferDescriptor(var7, Format.RGBA, Type.UBYTE));
      MaterialInstance var26 = this.layoutMi;
      MaterialInstance var11 = var26;
      if (var26 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("layoutMi");
         var11 = null;
      }

      Texture var41 = this.layoutTex;
      Texture var27 = var41;
      if (var41 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("layoutTex");
         var27 = null;
      }

      var11.setParameter("tex", var27, new TextureSampler(MinFilter.LINEAR, MagFilter.LINEAR, WrapMode.CLAMP_TO_EDGE));
      MaterialInstance var28 = this.layoutMi;
      MaterialInstance var12 = var28;
      if (var28 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("layoutMi");
         var12 = null;
      }

      var12.setParameter("opacity", 0.0F);
      com.google.android.filament.VertexBuffer.Builder var42 = new com.google.android.filament.VertexBuffer.Builder()
         .bufferCount(1)
         .vertexCount(4)
         .attribute(VertexAttribute.POSITION, 0, AttributeType.FLOAT3, 0, 20)
         .attribute(VertexAttribute.UV0, 0, AttributeType.FLOAT2, 12, 20);
      var5 = this.engine;
      var4 = var5;
      if (var5 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("engine");
         var4 = null;
      }

      this.layoutVb = var42.build(var4);
      var7 = ByteBuffer.allocateDirect(80).order(ByteOrder.nativeOrder());

      for (Pair var14 : CollectionsKt.listOf(
         new Pair[]{TuplesKt.to(this.XMIN, -20.0F), TuplesKt.to(this.XMIN, 20.0F), TuplesKt.to(40.0F, 20.0F), TuplesKt.to(40.0F, -20.0F)}
      )) {
         float var1 = ((Number)var14.component1()).floatValue();
         float var2 = ((Number)var14.component2()).floatValue();
         var7.putFloat(var2);
         var7.putFloat(0.005F);
         var7.putFloat(-var1);
         var7.putFloat((var2 + 20.0F) / (2 * 20.0F));
         var7.putFloat((var1 - this.XMIN) / (40.0F - this.XMIN));
      }

      ((Buffer)var7).flip();
      VertexBuffer var31 = this.layoutVb;
      VertexBuffer var15 = var31;
      if (var31 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("layoutVb");
         var15 = null;
      }

      Engine var43 = this.engine;
      var5 = var43;
      if (var43 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("engine");
         var5 = null;
      }

      var15.setBufferAt(var5, 0, var7);
      com.google.android.filament.IndexBuffer.Builder var44 = new com.google.android.filament.IndexBuffer.Builder().indexCount(6).bufferType(IndexType.USHORT);
      var5 = this.engine;
      var4 = var5;
      if (var5 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("engine");
         var4 = null;
      }

      this.layoutIb = var44.build(var4);
      var7 = ByteBuffer.allocateDirect(12).order(ByteOrder.nativeOrder());

      for (int var3 = 0; var3 < 6; var3++) {
         var7.putShort(new short[]{0, 1, 2, 0, 2, 3}[var3]);
      }

      ((Buffer)var7).flip();
      IndexBuffer var34 = this.layoutIb;
      IndexBuffer var17 = var34;
      if (var34 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("layoutIb");
         var17 = null;
      }

      Engine var45 = this.engine;
      var5 = var45;
      if (var45 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("engine");
         var5 = null;
      }

      var17.setBuffer(var5, var7);
      this.layoutEnt = EntityManager.get().create();
      com.google.android.filament.RenderableManager.Builder var46 = new com.google.android.filament.RenderableManager.Builder(1)
         .boundingBox(new Box(0.0F, 0.0F, -40.0F / 2, 60.0F, 0.1F, 60.0F));
      PrimitiveType var51 = PrimitiveType.TRIANGLES;
      VertexBuffer var18 = this.layoutVb;
      if (var18 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("layoutVb");
         var18 = null;
      }

      IndexBuffer var36 = this.layoutIb;
      if (var36 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("layoutIb");
         var36 = null;
      }

      com.google.android.filament.RenderableManager.Builder var47 = var46.geometry(0, var51, var18, var36, 0, 6);
      MaterialInstance var37 = this.layoutMi;
      MaterialInstance var19 = var37;
      if (var37 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("layoutMi");
         var19 = null;
      }

      com.google.android.filament.RenderableManager.Builder var48 = var47.material(0, var19)
         .castShadows(false)
         .receiveShadows(false)
         .culling(false)
         .priority(5);
      var5 = this.engine;
      var4 = var5;
      if (var5 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("engine");
         var4 = null;
      }

      var48.build(var4, this.layoutEnt);
      Scene var21 = this.scene;
      if (var21 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("scene");
         var21 = null;
      }

      var21.addEntity(this.layoutEnt);
   }

   private final void buildRibbonMesh() {
      com.google.android.filament.VertexBuffer.Builder var5 = new com.google.android.filament.VertexBuffer.Builder()
         .bufferCount(1)
         .vertexCount(this.maxVerts)
         .attribute(VertexAttribute.POSITION, 0, AttributeType.FLOAT3, 0, 16)
         .attribute(VertexAttribute.COLOR, 0, AttributeType.UBYTE4, 12, 16)
         .normalized(VertexAttribute.COLOR);
      Engine var3 = this.engine;
      Object var4 = null;
      Engine var2 = var3;
      if (var3 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("engine");
         var2 = null;
      }

      this.ribbonVb = var5.build(var2);
      com.google.android.filament.IndexBuffer.Builder var22 = new com.google.android.filament.IndexBuffer.Builder()
         .indexCount(this.maxIdx)
         .bufferType(IndexType.UINT);
      var3 = this.engine;
      var2 = var3;
      if (var3 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("engine");
         var2 = null;
      }

      this.ribbonIb = var22.build(var2);
      ((Buffer)this.getVbuf()).clear();

      for (int var1 = 0; var1 < 3; var1++) {
         this.getVbuf().putFloat(0.0F);
         this.getVbuf().putFloat(0.0F);
         this.getVbuf().putFloat(0.0F);
         this.getVbuf().putInt(0);
      }

      ((Buffer)this.getVbuf()).flip();
      VertexBuffer var15 = this.ribbonVb;
      VertexBuffer var8 = var15;
      if (var15 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ribbonVb");
         var8 = null;
      }

      Engine var23 = this.engine;
      var3 = var23;
      if (var23 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("engine");
         var3 = null;
      }

      var8.setBufferAt(var3, 0, this.getVbuf());
      ((Buffer)this.getIbuf()).clear();
      this.getIbuf().putInt(0);
      this.getIbuf().putInt(1);
      this.getIbuf().putInt(2);
      ((Buffer)this.getIbuf()).flip();
      IndexBuffer var17 = this.ribbonIb;
      IndexBuffer var9 = var17;
      if (var17 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ribbonIb");
         var9 = null;
      }

      Engine var24 = this.engine;
      var3 = var24;
      if (var24 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("engine");
         var3 = null;
      }

      var9.setBuffer(var3, this.getIbuf());
      this.ribbonEntity = EntityManager.get().create();
      com.google.android.filament.RenderableManager.Builder var6 = new com.google.android.filament.RenderableManager.Builder(1)
         .boundingBox(new Box(0.0F, 0.0F, -100.0F, 60.0F, 12.0F, 200.0F));
      PrimitiveType var25 = PrimitiveType.TRIANGLES;
      VertexBuffer var10 = this.ribbonVb;
      if (var10 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ribbonVb");
         var10 = null;
      }

      IndexBuffer var19 = this.ribbonIb;
      if (var19 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ribbonIb");
         var19 = null;
      }

      com.google.android.filament.RenderableManager.Builder var26 = var6.geometry(0, var25, var10, var19, 0, 3);
      Material var20 = this.ribbonMaterial;
      Material var11 = var20;
      if (var20 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ribbonMaterial");
         var11 = null;
      }

      com.google.android.filament.RenderableManager.Builder var27 = var26.material(0, var11.getDefaultInstance())
         .castShadows(false)
         .receiveShadows(false)
         .culling(false)
         .priority(6);
      var3 = this.engine;
      var2 = var3;
      if (var3 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("engine");
         var2 = null;
      }

      var27.build(var2, this.ribbonEntity);
      Scene var13 = this.scene;
      if (var13 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("scene");
         var13 = (Scene)var4;
      }

      var13.addEntity(this.ribbonEntity);
   }

   private final int cameraLane(ModelSnapshot var1, int var2, boolean var3) {
      LaneMarks.Mark var25;
      label90: {
         LaneMarks var19 = this.laneMarks;
         if (var19 != null) {
            LaneMarks.Mark[] var24 = var19.getMarks();
            if (var24 != null) {
               var25 = ArraysKt.getOrNull(var24, 1);
               break label90;
            }
         }

         var25 = null;
      }

      boolean var12;
      if (var25 == null || !var25.getYellow() || var25.getKind() != 3 && var25.getKind() != 2) {
         var12 = false;
      } else {
         var12 = true;
      }

      double var8 = this.lineDist(var1, 1);
      double var6 = this.lineDist(var1, 2);
      boolean var10;
      if (!Double.isInfinite(var8) && !Double.isNaN(var8)) {
         var10 = 1;
      } else {
         var10 = 0;
      }

      if (var10) {
         if (!Double.isInfinite(var6) && !Double.isNaN(var6)) {
            var10 = (boolean)1;
         } else {
            var10 = (boolean)0;
         }

         if (var10) {
            double var4 = var8 + var6;
            if (2.7 <= var4 && var4 <= 4.2) {
               var10 = (boolean)1;
            } else {
               var10 = (boolean)0;
            }

            if (var10) {
               this.laneW = this.laneW + (var8 + var6 - this.laneW) * 0.01;
            }
         }
      }

      OsmRoads var26 = OsmRoads.INSTANCE;
      double var20;
      if (var3) {
         var20 = this.edgeDist(var1, 0);
      } else {
         var20 = Double.NaN;
      }

      int var11 = var26.laneIndex(var2, var12, var8, var20, var6, this.edgeDist(var1, 1), cameraLane$dashed(this, 1), cameraLane$dashed(this, 2));
      long var15 = System.nanoTime();
      var10 = -1;
      if (var11 >= 0) {
         this.laneSeenAt = var15;
         if (var11 != this.laneVote) {
            this.laneVote = var11;
            this.laneVoteSince = var15;
         }

         long var17 = this.laneVoteSince;
         long var13;
         if (this.laneIdx < 0) {
            var13 = 500000000L;
         } else {
            var13 = 1500000000L;
         }

         if (var15 - var17 > var13) {
            this.laneIdx = this.laneVote;
         }
      } else if (var15 - this.laneSeenAt > 5000000000L) {
         this.laneIdx = -1;
      }

      if (this.laneIdx >= 0) {
         var10 = RangesKt.coerceAtMost(this.laneIdx, var2 - 1);
      }

      return var10;
   }

   private static final boolean cameraLane$dashed(SceneView var0, int var1) {
      LaneMarks var4 = var0.laneMarks;
      boolean var3 = false;
      boolean var2 = var3;
      if (var4 != null) {
         LaneMarks.Mark[] var5 = var4.getMarks();
         var2 = var3;
         if (var5 != null) {
            LaneMarks.Mark var6 = ArraysKt.getOrNull(var5, var1);
            var2 = var3;
            if (var6 != null) {
               var2 = var3;
               if (var6.getKind() == 2) {
                  var2 = var3;
                  if (!var6.getYellow()) {
                     var2 = true;
                  }
               }
            }
         }
      }

      return var2;
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   private final boolean centreLineOnLeft() {
      boolean var3 = false;

      LaneBrain var5;
      try {
         var5 = LaneBrain.Live.INSTANCE.getBrain();
      } catch (Exception var9) {
         return var3;
      }

      LaneBrain.Road var4;
      if (var5 != null) {
         try {
            var4 = var5.getRoad();
         } catch (Exception var8) {
            return var3;
         }
      } else {
         var4 = null;
      }

      boolean var2 = var3;
      if (var4 != null) {
         var2 = var3;

         try {
            if (var4.getOneway()) {
               return var2;
            }
         } catch (Exception var7) {
            return var3;
         }

         var2 = var3;

         int var1;
         try {
            if (var4.getHighway()) {
               return var2;
            }

            var1 = var5.lanesLeft();
         } catch (Exception var6) {
            return var3;
         }

         var2 = var3;
         if (var1 == 0) {
            var2 = true;
         }
      }

      return var2;
   }

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
         boolean var85;
         if (var140.length > 0) {
            var85 = 1;
         } else {
            var85 = 0;
         }

         if (var85) {
            var18 = var140[0];
         } else {
            var18 = 1.0F;
         }

         float[] var47 = var1.getEdgeStd();
         if (1 < var47.length) {
            var85 = (boolean)1;
         } else {
            var85 = (boolean)0;
         }

         float var19;
         if (var85) {
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

                  boolean var22;
                  if (!this.night && !this.alwaysDark) {
                     var22 = 0;
                  } else {
                     var22 = 1;
                  }

                  if (var22) {
                     var85 = Color.rgb(46, 51, 60);
                  } else {
                     var85 = Color.rgb(163, 169, 178);
                  }

                  int var104 = var85;
                  short var23;
                  short var24;
                  short var91;
                  if (var22) {
                     var91 = 113;
                     var24 = 125;
                     var23 = 106;
                  } else {
                     var91 = 247;
                     var24 = 249;
                     var23 = 246;
                  }

                  int var29 = Color.rgb(var23, var91, var24);
                  byte var116;
                  if (var22) {
                     var91 = 23;
                     var24 = (short)28;
                     var116 = 20;
                  } else {
                     var91 = 131;
                     var24 = (short)141;
                     var116 = 124;
                  }

                  int var36 = Color.rgb(var116, var91, var24);
                  if (var22) {
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
                  if (var22) {
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
                  var22 = Companion.withAlpha(var85, 255);
                  this.fillBetween(var54, var140, var147, var22, 0.02F, var43);
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
                        var116 = var22;
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
                           var116 = var22;
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

                     byte var109;
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
                     var104 = var22;
                     var22 = var116;
                     var24 = var29;
                     var116 = var24;
                  }

                  return;
               }

               return;
            }

            if (var85 != 0) {
               var10 = 1.0;
            }

            ArrayList var49 = new ArrayList();

            for (double var6 = -8.0; var6 <= 30.0; var6++) {
               double var8 = Math.max(0.0, var6);
               double var12 = cleanRoad$edgeAt(this, var44, var28, var85, var8);
               double var14 = -1.0;

               for (double var73 = 0.0; var73 <= 9.0; var73 += 0.5) {
                  double var16 = var12 + var10 * var73;
                  int var98 = this.groundAt(var6, var16);
                  if (var98 == 0) {
                     var14 = var73;
                  } else {
                     boolean var99;
                     if (1 <= var98 && var98 < 4) {
                        var99 = 1;
                     } else {
                        var99 = 0;
                     }

                     if (var99) {
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
                     var77 = (var77 - cleanRoad$edgeAt(this, var44, var28, var85, Math.max(0.0, var74))) * var10;
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
               var52[var85]++;
               if (this.curbHold[var85] > 200 && var2 >= 0.5F) {
                  this.curbFit[var85] = false;
                  double[] var53 = this.curbOffS;
                  var53[var85] *= 0.995;
               }
            } else {
               CollectionsKt.sort(var49);
               Object var50 = var49.get(var49.size() / 2);
               Intrinsics.checkNotNullExpressionValue(var50, "get(...)");
               double var70 = ((Number)var50).doubleValue();
               if (var2 >= 0.5F || !this.curbFit[var85]) {
                  double var75 = var70 - this.curbOffS[var85];
                  if (!this.curbFit[var85]) {
                     this.curbOffS[var85] = var70;
                  } else if (Math.abs(var75) > 0.4) {
                     var50 = this.curbOffS;
                     ((Object[])var50)[var85] += RangesKt.coerceIn(var75, -0.025, 0.025);
                  }
               }

               this.curbHold[var85] = 0;
               this.curbFit[var85] = true;
            }

            for (int var103 = 0; var103 < var28; var103++) {
               this.curbS[var85][var103] = this.edgeF[var85][var103] + this.curbOffS[var85] * var10;
            }

            var85++;
         }
      }
   }

   // $VF: synthetic method
   static void cleanRoad$default(SceneView var0, ModelSnapshot var1, float var2, boolean var3, int var4, Object var5) {
      if ((var4 & 4) != 0) {
         var3 = true;
      }

      var0.cleanRoad(var1, var2, var3);
   }

   private static final double cleanRoad$edgeAt(SceneView var0, float[] var1, int var2, int var3, double var4) {
      if (var0.shapeOk) {
         return var0.shapeY(var4) + var0.shapeOff[var3];
      } else if (var4 <= var1[0]) {
         return var0.edgeF[var3][0];
      } else {
         for (int var6 = 1; var6 < var2; var6++) {
            if (var1[var6] >= var4) {
               var4 = (var4 - var1[var6 - 1]) / Math.max(0.01F, var1[var6] - var1[var6 - 1]);
               return var0.edgeF[var3][var6 - 1] + (var0.edgeF[var3][var6] - var0.edgeF[var3][var6 - 1]) * var4;
            }
         }

         return var0.edgeF[var3][var2 - 1];
      }
   }

   private static final float cleanRoad$lambda$49(float var0, float[] var1, int var2) {
      return (1.0F - RangesKt.coerceIn((var1[var2] - 85.0F) / 25.0F, 0.0F, 1.0F)) * var0;
   }

   private static final float cleanRoad$lambda$52(float[] var0, Function1 var1, int var2) {
      boolean var7 = false;
      int var5;
      if (var2 == 0) {
         var5 = 0;
      } else {
         var5 = var2 - 1;
      }

      int var8 = var5 - 1;
      boolean var6;
      if (var8 >= 0 && var8 < var0.length) {
         var6 = true;
      } else {
         var6 = false;
      }

      float var3;
      if (var6) {
         var3 = var0[var8];
      } else {
         var3 = var0[var5];
      }

      var8 = var5 + 1;
      var6 = var7;
      if (var8 >= 0) {
         var6 = var7;
         if (var8 < var0.length) {
            var6 = true;
         }
      }

      float var4;
      if (var6) {
         var4 = var0[var8];
      } else {
         var4 = var0[var5];
      }

      return RangesKt.coerceIn((2 * var0[var5] + var3 + var4) / 4.0F, 0.0F, 1.0F) * ((Number)var1.invoke(var2)).floatValue();
   }

   private static final Pair<Float, Float> cleanRoad$lr(SceneView var0, int var1) {
      float var5 = (float)var0.curbS[0][var1];
      float var4 = (float)var0.curbS[1][var1];
      float var3 = var5;
      float var6 = var4;
      if (var4 - var5 < 3.0F) {
         var6 = (var5 + var4) / 2;
         var3 = var6 - 1.5F;
         var6++;
      }

      return new Pair<>(var3, var6);
   }

   private final void clearRibbons() {
      this.bufSel = 1 - this.bufSel;
      ((Buffer)this.getVbuf()).clear();
      ((Buffer)this.getIbuf()).clear();
      this.nVerts = 0;
      this.nIdx = 0;

      for (int var1 = 0; var1 < 3; var1++) {
         this.putVertex(0.0F, -10.0F, 0.0F, 0);
      }

      this.putTri(0, 1, 2);
      ((Buffer)this.getVbuf()).flip();
      ((Buffer)this.getIbuf()).flip();
      VertexBuffer var3 = this.ribbonVb;
      VertexBuffer var2 = var3;
      if (var3 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ribbonVb");
         var2 = null;
      }

      Engine var4 = this.engine;
      Engine var10 = var4;
      if (var4 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("engine");
         var10 = null;
      }

      var2.setBufferAt(var10, 0, this.getVbuf());
      IndexBuffer var11 = this.ribbonIb;
      IndexBuffer var7 = var11;
      if (var11 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ribbonIb");
         var7 = null;
      }

      var4 = this.engine;
      Engine var12 = var4;
      if (var4 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("engine");
         var12 = null;
      }

      var7.setBuffer(var12, this.getIbuf());
      Engine var13 = this.engine;
      Engine var8 = var13;
      if (var13 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("engine");
         var8 = null;
      }

      RenderableManager var5 = var8.getRenderableManager();
      int var6 = var5.getInstance(this.ribbonEntity);
      PrimitiveType var16 = PrimitiveType.TRIANGLES;
      var2 = this.ribbonVb;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ribbonVb");
         var2 = null;
      }

      IndexBuffer var14 = this.ribbonIb;
      if (var14 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ribbonIb");
         var14 = null;
      }

      var5.setGeometryAt(var6, 0, var16, var2, var14, 0, 3);
   }

   private final void collectParked(long var1, Telemetry var3) {
      Detector var11 = this.detector;
      if (var11 != null) {
         if (var11.getObjectsAtMs() != this.parkedAtMs) {
            this.parkedAtMs = var11.getObjectsAtMs();

            while (!this.parked.isEmpty() && var1 - this.parked.first().getTNs() > 30000000000L) {
               this.parked.removeFirst();
            }

            if (!(var3.getVEgo() < 2.0F)) {
               for (Detector.Obj var12 : var11.getObjects()) {
                  if ((var12.getCls() == 2 || var12.getCls() == 5 || var12.getCls() == 7) && var12.getVelValid() && !(Math.abs(var12.getVx()) > 1.2F)) {
                     double var6 = var12.getCx();
                     double var8 = var12.getCy();
                     if (!(var6 < 2.0) && !(var6 > 35.0) && !(Math.abs(var8) < 2.3) && !(Math.abs(var8) > 12.0)) {
                        byte var10;
                        if (var8 < 0.0) {
                           var10 = 0;
                        } else {
                           var10 = 1;
                        }

                        double var4;
                        if (var10 == 0) {
                           var4 = -1.15;
                        } else {
                           var4 = 1.15;
                        }

                        Pair var16 = this.toWorld(var6, var4 + var8);
                        var4 = ((Number)var16.component1()).doubleValue();
                        var6 = ((Number)var16.component2()).doubleValue();
                        this.parked.addLast(new SceneView.Pt(var4, var6, var1, var10));
                        if (this.parked.size() > 400) {
                           this.parked.removeFirst();
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private final void combine(long var1) {
      this.texX = this.odoX;
      this.texY = this.odoY;
      this.texYaw = this.odoYaw;
      this.texNs = var1;
      float var11 = 0.5F;
      float var16 = 80 * 0.5F / 2;
      Arrays.fill(this.memGrp, (byte)-1);
      Arrays.fill(this.memScore, Float.MAX_VALUE);
      Arrays.fill(this.memAge, 0.0F);

      for (SceneView.Snap var31 : this.snaps) {
         float var13 = (float)(var1 - var31.getTNs()) / 1.0E9F;
         double var5 = this.texX - var31.getPx();
         double var7 = this.texY - var31.getPy();
         double var3 = Math.cos(var31.getYaw());
         double var9 = var31.getYaw();
         var9 = Math.sin(var9);
         float var12 = (float)(var3 * var5 + var9 * var7);
         float var14 = (float)(-var9 * var5 + var3 * var7);
         var3 = this.texYaw - var31.getYaw();
         float var15 = (float)Math.cos(var3);
         float var17 = (float)Math.sin(var3);
         byte[] var32 = var31.getG();
         int var22 = 0;

         for (int var25 = this.MR; var22 < var25; var22++) {
            float var18 = this.XMIN + (var22 + 0.5F) * var11;

            for (int var23 = 0; var23 < 80; var23++) {
               float var20 = var16 - (var23 + 0.5F) * var11;
               float var19 = var12 + var15 * var18 - var17 * var20;
               if (!(var19 < 0.0F)) {
                  int var26 = (int)(var19 / var11);
                  if (var26 < 80) {
                     float var21 = (var16 - (var17 * var18 + var14 + var15 * var20)) / var11;
                     int var24;
                     if (var21 < 0.0F) {
                        var24 = -1;
                     } else {
                        var24 = (int)var21;
                     }

                     if (var24 >= 0 && var24 < 80) {
                        var26 = var32[var26 * 80 + var24] & 255;
                        if (var26 != 255) {
                           var20 = var17 * var18 + var14 + var15 * var20;
                           var24 = var22 * 80 + var23;
                           var19 = (float)Math.sqrt(var19 * var19 + var20 * var20);
                           var19 += Math.min(var13, 10.0F) * 3.0F;
                           if (var19 < this.memScore[var24]) {
                              this.memScore[var24] = var19;
                              this.memGrp[var24] = (byte)var26;
                              this.memAge[var24] = var13;
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      for (int var40 = 0; var40 < 80; var40++) {
         int var42 = -1;
         int var39 = 0;

         for (int var27 = this.MR; var39 < var27; var39++) {
            int var28 = var39 * 80 + var40;
            if ((this.memGrp[var28] & 255) != 255) {
               if (var42 >= 0) {
                  int var29 = var39 - var42;
                  boolean var46 = false;
                  boolean var44 = var46;
                  if (2 <= var29) {
                     var44 = var46;
                     if (var29 < 14) {
                        var44 = true;
                     }
                  }

                  if (var44 && this.memGrp[var28] == this.memGrp[var42 * 80 + var40]) {
                     var11 = Math.max(this.memAge[var28], this.memAge[var42 * 80 + var40]);
                     var42++;

                     while (var42 < var39) {
                        this.memGrp[var42 * 80 + var40] = this.memGrp[var28];
                        this.memAge[var42 * 80 + var40] = var11;
                        var42++;
                     }
                  }
               }

               var42 = var39;
            }
         }
      }
   }

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
                  var29 = 1;
               } else {
                  var29 = 0;
               }

               boolean var25;
               label88: {
                  if (!var29) {
                     if (var26.getName().length() > 0) {
                        var29 = (boolean)1;
                     } else {
                        var29 = (boolean)0;
                     }

                     if (!var29) {
                        var25 = Intrinsics.areEqual(var26.getKind(), var1.getKind());
                        break label88;
                     }
                  }

                  var25 = OsmRoads.INSTANCE.sameStreet(var26.getName(), var1.getName());
               }

               if (var25) {
                  var29 = 0;

                  while (var29 < 2) {
                     if (var29 == 0) {
                        var23 = 0;
                     } else {
                        var23 = var26.getE().length - 1;
                     }

                     if (var29 == 0) {
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
            var43 = 1;
         } else {
            var43 = 0;
         }

         double var2;
         label194: {
            if (!var43 && this.lastEdgeX.length == this.edgeS[var12].length) {
               float[] var61 = var1.getEdgeStd();
               if (var12 >= 0 && var12 < var61.length) {
                  var43 = (boolean)1;
               } else {
                  var43 = (boolean)0;
               }

               float var10;
               if (var43) {
                  var10 = var61[var12];
               } else {
                  var10 = 9.0F;
               }

               if (var10 < 1.0F) {
                  ArrayList var62 = new ArrayList();
                  var43 = 0;

                  for (int var13 = this.lastEdgeX.length; var43 < var13; var43++) {
                     var10 = this.lastEdgeX[var43];
                     if (!(var10 < 3.0F)) {
                        if (var10 > 15.0F) {
                           break;
                        }

                        var62.add(this.edgeS[var12][var43] * var4);
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
            var47 = 1;
         } else {
            var47 = 0;
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
                        var47 = (boolean)1;
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
                     var47 = (boolean)1;
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
                  var47 = (boolean)1;
               } else {
                  var47 = (boolean)0;
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
               var47 = 1;
            } else {
               var47 = 0;
            }

            var2 = (Double)var72.get(var47) - 0.2;
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

   private static final void curbs$lower(double[] var0, int var1, double var2) {
      boolean var4;
      if (!Double.isInfinite(var2) && !Double.isNaN(var2)) {
         var4 = true;
      } else {
         var4 = false;
      }

      if (var4) {
         if (!Double.isNaN(var0[var1])) {
            var2 = Math.max(var0[var1], var2);
         }

         var0[var1] = var2;
      }
   }

   private static final String curbs$side(StringBuilder[] var0, int var1) {
      boolean var2;
      if (var0[var1].length() == 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      String var3;
      if (var2) {
         var3 = "?";
      } else {
         String var4 = var0[var1].toString();
         Intrinsics.checkNotNullExpressionValue(var4, "toString(...)");
         var3 = StringsKt.trim(var4).toString();
      }

      return var3;
   }

   private static final void curbs$upper(double[] var0, int var1, double var2) {
      boolean var4;
      if (!Double.isInfinite(var2) && !Double.isNaN(var2)) {
         var4 = true;
      } else {
         var4 = false;
      }

      if (var4) {
         if (!Double.isNaN(var0[var1])) {
            var2 = Math.min(var0[var1], var2);
         }

         var0[var1] = var2;
      }
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   private final void destroyFilament() {
      int var1 = 0;
      this.engineAlive = false;
      this.ready = false;

      UiHelper var9;
      try {
         var9 = this.uiHelper;
      } catch (Exception var129) {
         Log.w("StarView/Scene", "teardown: " + var129);
         return;
      }

      Object var10 = null;
      UiHelper var8 = var9;
      if (var9 == null) {
         try {
            Intrinsics.throwUninitializedPropertyAccessException("uiHelper");
         } catch (Exception var128) {
            Log.w("StarView/Scene", "teardown: " + var128);
            return;
         }

         var8 = null;
      }

      SwapChain var11;
      try {
         var8.detach();
         var11 = this.swapChain;
      } catch (Exception var127) {
         Log.w("StarView/Scene", "teardown: " + var127);
         return;
      }

      if (var11 != null) {
         try {
            var167 = this.engine;
         } catch (Exception var126) {
            Log.w("StarView/Scene", "teardown: " + var126);
            return;
         }

         Engine var131 = var167;
         if (var167 == null) {
            try {
               Intrinsics.throwUninitializedPropertyAccessException("engine");
            } catch (Exception var125) {
               Log.w("StarView/Scene", "teardown: " + var125);
               return;
            }

            var131 = null;
         }

         try {
            var131.destroySwapChain(var11);
         } catch (Exception var124) {
            Log.w("StarView/Scene", "teardown: " + var124);
            return;
         }
      }

      try {
         this.swapChain = null;
         var168 = this.engine;
      } catch (Exception var123) {
         Log.w("StarView/Scene", "teardown: " + var123);
         return;
      }

      Engine var132 = var168;
      if (var168 == null) {
         try {
            Intrinsics.throwUninitializedPropertyAccessException("engine");
         } catch (Exception var122) {
            Log.w("StarView/Scene", "teardown: " + var122);
            return;
         }

         var132 = null;
      }

      try {
         var132.flushAndWait();
         var214 = this.genericPool.iterator();
         Intrinsics.checkNotNullExpressionValue(var214, "iterator(...)");
      } catch (Exception var121) {
         Log.w("StarView/Scene", "teardown: " + var121);
         return;
      }

      while (true) {
         boolean var7;
         try {
            var7 = var214.hasNext();
         } catch (Exception var117) {
            Log.w("StarView/Scene", "teardown: " + var117);
            break;
         }

         if (!var7) {
            try {
               this.genericPool.clear();
               var215 = this.ego;
            } catch (Exception var116) {
               Log.w("StarView/Scene", "teardown: " + var116);
               break;
            }

            if (var215 != null) {
               try {
                  var215.show(false);
                  var170 = this.assetLoader;
               } catch (Exception var115) {
                  Log.w("StarView/Scene", "teardown: " + var115);
                  break;
               }

               AssetLoader var135 = var170;
               if (var170 == null) {
                  try {
                     Intrinsics.throwUninitializedPropertyAccessException("assetLoader");
                  } catch (Exception var114) {
                     Log.w("StarView/Scene", "teardown: " + var114);
                     break;
                  }

                  var135 = null;
               }

               try {
                  var135.destroyAsset(var215.getAsset());
               } catch (Exception var113) {
                  Log.w("StarView/Scene", "teardown: " + var113);
                  break;
               }
            }

            try {
               this.ego = null;
               var171 = this.resourceLoader;
            } catch (Exception var112) {
               Log.w("StarView/Scene", "teardown: " + var112);
               break;
            }

            ResourceLoader var136 = var171;
            if (var171 == null) {
               try {
                  Intrinsics.throwUninitializedPropertyAccessException("resourceLoader");
               } catch (Exception var111) {
                  Log.w("StarView/Scene", "teardown: " + var111);
                  break;
               }

               var136 = null;
            }

            try {
               var136.destroy();
               var172 = this.assetLoader;
            } catch (Exception var110) {
               Log.w("StarView/Scene", "teardown: " + var110);
               break;
            }

            AssetLoader var137 = var172;
            if (var172 == null) {
               try {
                  Intrinsics.throwUninitializedPropertyAccessException("assetLoader");
               } catch (Exception var109) {
                  Log.w("StarView/Scene", "teardown: " + var109);
                  break;
               }

               var137 = null;
            }

            try {
               var137.destroy();
               var173 = this.engine;
            } catch (Exception var108) {
               Log.w("StarView/Scene", "teardown: " + var108);
               break;
            }

            Engine var138 = var173;
            if (var173 == null) {
               try {
                  Intrinsics.throwUninitializedPropertyAccessException("engine");
               } catch (Exception var107) {
                  Log.w("StarView/Scene", "teardown: " + var107);
                  break;
               }

               var138 = null;
            }

            int var2;
            int var3;
            int var4;
            int var5;
            try {
               var216 = var138.getRenderableManager();
               Intrinsics.checkNotNullExpressionValue(var216, "getRenderableManager(...)");
               var4 = this.ribbonEntity;
               var5 = this.groundEnt;
               var2 = this.shadowEnt;
               var3 = this.layoutEnt;
            } catch (Exception var106) {
               Log.w("StarView/Scene", "teardown: " + var106);
               break;
            }

            for (; var1 < 4; var1++) {
               int var6;
               try {
                  var6 = new int[]{var4, var5, var2, var3}[var1];
                  var174 = this.scene;
               } catch (Exception var105) {
                  Log.w("StarView/Scene", "teardown: " + var105);
                  return;
               }

               Scene var139 = var174;
               if (var174 == null) {
                  try {
                     Intrinsics.throwUninitializedPropertyAccessException("scene");
                  } catch (Exception var104) {
                     Log.w("StarView/Scene", "teardown: " + var104);
                     return;
                  }

                  var139 = null;
               }

               try {
                  var139.removeEntity(var6);
                  var216.destroy(var6);
                  EntityManager.get().destroy(var6);
               } catch (Exception var103) {
                  Log.w("StarView/Scene", "teardown: " + var103);
                  return;
               }
            }

            try {
               var175 = this.engine;
            } catch (Exception var102) {
               Log.w("StarView/Scene", "teardown: " + var102);
               break;
            }

            Engine var140 = var175;
            if (var175 == null) {
               try {
                  Intrinsics.throwUninitializedPropertyAccessException("engine");
               } catch (Exception var101) {
                  Log.w("StarView/Scene", "teardown: " + var101);
                  break;
               }

               var140 = null;
            }

            try {
               var217 = this.layoutVb;
            } catch (Exception var100) {
               Log.w("StarView/Scene", "teardown: " + var100);
               break;
            }

            VertexBuffer var176 = var217;
            if (var217 == null) {
               try {
                  Intrinsics.throwUninitializedPropertyAccessException("layoutVb");
               } catch (Exception var99) {
                  Log.w("StarView/Scene", "teardown: " + var99);
                  break;
               }

               var176 = null;
            }

            try {
               var140.destroyVertexBuffer(var176);
               var177 = this.engine;
            } catch (Exception var98) {
               Log.w("StarView/Scene", "teardown: " + var98);
               break;
            }

            Engine var141 = var177;
            if (var177 == null) {
               try {
                  Intrinsics.throwUninitializedPropertyAccessException("engine");
               } catch (Exception var97) {
                  Log.w("StarView/Scene", "teardown: " + var97);
                  break;
               }

               var141 = null;
            }

            try {
               var218 = this.layoutIb;
            } catch (Exception var96) {
               Log.w("StarView/Scene", "teardown: " + var96);
               break;
            }

            IndexBuffer var178 = var218;
            if (var218 == null) {
               try {
                  Intrinsics.throwUninitializedPropertyAccessException("layoutIb");
               } catch (Exception var95) {
                  Log.w("StarView/Scene", "teardown: " + var95);
                  break;
               }

               var178 = null;
            }

            try {
               var141.destroyIndexBuffer(var178);
               var179 = this.engine;
            } catch (Exception var94) {
               Log.w("StarView/Scene", "teardown: " + var94);
               break;
            }

            Engine var142 = var179;
            if (var179 == null) {
               try {
                  Intrinsics.throwUninitializedPropertyAccessException("engine");
               } catch (Exception var93) {
                  Log.w("StarView/Scene", "teardown: " + var93);
                  break;
               }

               var142 = null;
            }

            try {
               var219 = this.layoutMi;
            } catch (Exception var92) {
               Log.w("StarView/Scene", "teardown: " + var92);
               break;
            }

            MaterialInstance var180 = var219;
            if (var219 == null) {
               try {
                  Intrinsics.throwUninitializedPropertyAccessException("layoutMi");
               } catch (Exception var91) {
                  Log.w("StarView/Scene", "teardown: " + var91);
                  break;
               }

               var180 = null;
            }

            try {
               var142.destroyMaterialInstance(var180);
               var181 = this.engine;
            } catch (Exception var90) {
               Log.w("StarView/Scene", "teardown: " + var90);
               break;
            }

            Engine var143 = var181;
            if (var181 == null) {
               try {
                  Intrinsics.throwUninitializedPropertyAccessException("engine");
               } catch (Exception var89) {
                  Log.w("StarView/Scene", "teardown: " + var89);
                  break;
               }

               var143 = null;
            }

            try {
               var220 = this.layoutTex;
            } catch (Exception var88) {
               Log.w("StarView/Scene", "teardown: " + var88);
               break;
            }

            Texture var182 = var220;
            if (var220 == null) {
               try {
                  Intrinsics.throwUninitializedPropertyAccessException("layoutTex");
               } catch (Exception var87) {
                  Log.w("StarView/Scene", "teardown: " + var87);
                  break;
               }

               var182 = null;
            }

            try {
               var143.destroyTexture(var182);
               var183 = this.engine;
            } catch (Exception var86) {
               Log.w("StarView/Scene", "teardown: " + var86);
               break;
            }

            Engine var144 = var183;
            if (var183 == null) {
               try {
                  Intrinsics.throwUninitializedPropertyAccessException("engine");
               } catch (Exception var85) {
                  Log.w("StarView/Scene", "teardown: " + var85);
                  break;
               }

               var144 = null;
            }

            try {
               var221 = this.layoutMaterial;
            } catch (Exception var84) {
               Log.w("StarView/Scene", "teardown: " + var84);
               break;
            }

            Material var184 = var221;
            if (var221 == null) {
               try {
                  Intrinsics.throwUninitializedPropertyAccessException("layoutMaterial");
               } catch (Exception var83) {
                  Log.w("StarView/Scene", "teardown: " + var83);
                  break;
               }

               var184 = null;
            }

            try {
               var144.destroyMaterial(var184);
               var185 = this.engine;
            } catch (Exception var82) {
               Log.w("StarView/Scene", "teardown: " + var82);
               break;
            }

            Engine var145 = var185;
            if (var185 == null) {
               try {
                  Intrinsics.throwUninitializedPropertyAccessException("engine");
               } catch (Exception var81) {
                  Log.w("StarView/Scene", "teardown: " + var81);
                  break;
               }

               var145 = null;
            }

            try {
               var222 = this.ribbonVb;
            } catch (Exception var80) {
               Log.w("StarView/Scene", "teardown: " + var80);
               break;
            }

            VertexBuffer var186 = var222;
            if (var222 == null) {
               try {
                  Intrinsics.throwUninitializedPropertyAccessException("ribbonVb");
               } catch (Exception var79) {
                  Log.w("StarView/Scene", "teardown: " + var79);
                  break;
               }

               var186 = null;
            }

            try {
               var145.destroyVertexBuffer(var186);
               var187 = this.engine;
            } catch (Exception var78) {
               Log.w("StarView/Scene", "teardown: " + var78);
               break;
            }

            Engine var146 = var187;
            if (var187 == null) {
               try {
                  Intrinsics.throwUninitializedPropertyAccessException("engine");
               } catch (Exception var77) {
                  Log.w("StarView/Scene", "teardown: " + var77);
                  break;
               }

               var146 = null;
            }

            try {
               var223 = this.ribbonIb;
            } catch (Exception var76) {
               Log.w("StarView/Scene", "teardown: " + var76);
               break;
            }

            IndexBuffer var188 = var223;
            if (var223 == null) {
               try {
                  Intrinsics.throwUninitializedPropertyAccessException("ribbonIb");
               } catch (Exception var75) {
                  Log.w("StarView/Scene", "teardown: " + var75);
                  break;
               }

               var188 = null;
            }

            try {
               var146.destroyIndexBuffer(var188);
               var224 = this.groundBuffers.iterator();
               Intrinsics.checkNotNullExpressionValue(var224, "iterator(...)");
            } catch (Exception var73) {
               Log.w("StarView/Scene", "teardown: " + var73);
               break;
            }

            while (true) {
               VertexBuffer var13;
               IndexBuffer var236;
               try {
                  if (!var224.hasNext()) {
                     break;
                  }

                  Pair var147 = (Pair)var224.next();
                  Intrinsics.checkNotNullExpressionValue(var147, "next(...)");
                  var147 = var147;
                  var13 = (VertexBuffer)var147.component1();
                  var236 = (IndexBuffer)var147.component2();
                  var189 = this.engine;
               } catch (Exception var74) {
                  Log.w("StarView/Scene", "teardown: " + var74);
                  return;
               }

               Engine var149 = var189;
               if (var189 == null) {
                  try {
                     Intrinsics.throwUninitializedPropertyAccessException("engine");
                  } catch (Exception var72) {
                     Log.w("StarView/Scene", "teardown: " + var72);
                     return;
                  }

                  var149 = null;
               }

               try {
                  var149.destroyVertexBuffer(var13);
                  var190 = this.engine;
               } catch (Exception var71) {
                  Log.w("StarView/Scene", "teardown: " + var71);
                  return;
               }

               Engine var150 = var190;
               if (var190 == null) {
                  try {
                     Intrinsics.throwUninitializedPropertyAccessException("engine");
                  } catch (Exception var70) {
                     Log.w("StarView/Scene", "teardown: " + var70);
                     return;
                  }

                  var150 = null;
               }

               try {
                  var150.destroyIndexBuffer(var236);
               } catch (Exception var69) {
                  Log.w("StarView/Scene", "teardown: " + var69);
                  return;
               }
            }

            try {
               this.groundBuffers.clear();
               var191 = this.engine;
            } catch (Exception var68) {
               Log.w("StarView/Scene", "teardown: " + var68);
               break;
            }

            Engine var151 = var191;
            if (var191 == null) {
               try {
                  Intrinsics.throwUninitializedPropertyAccessException("engine");
               } catch (Exception var67) {
                  Log.w("StarView/Scene", "teardown: " + var67);
                  break;
               }

               var151 = null;
            }

            try {
               var225 = this.groundMi;
            } catch (Exception var66) {
               Log.w("StarView/Scene", "teardown: " + var66);
               break;
            }

            MaterialInstance var192 = var225;
            if (var225 == null) {
               try {
                  Intrinsics.throwUninitializedPropertyAccessException("groundMi");
               } catch (Exception var65) {
                  Log.w("StarView/Scene", "teardown: " + var65);
                  break;
               }

               var192 = null;
            }

            try {
               var151.destroyMaterialInstance(var192);
               var193 = this.engine;
            } catch (Exception var64) {
               Log.w("StarView/Scene", "teardown: " + var64);
               break;
            }

            Engine var152 = var193;
            if (var193 == null) {
               try {
                  Intrinsics.throwUninitializedPropertyAccessException("engine");
               } catch (Exception var63) {
                  Log.w("StarView/Scene", "teardown: " + var63);
                  break;
               }

               var152 = null;
            }

            try {
               var226 = this.shadowMi;
            } catch (Exception var62) {
               Log.w("StarView/Scene", "teardown: " + var62);
               break;
            }

            MaterialInstance var194 = var226;
            if (var226 == null) {
               try {
                  Intrinsics.throwUninitializedPropertyAccessException("shadowMi");
               } catch (Exception var61) {
                  Log.w("StarView/Scene", "teardown: " + var61);
                  break;
               }

               var194 = null;
            }

            try {
               var152.destroyMaterialInstance(var194);
               var195 = this.engine;
            } catch (Exception var60) {
               Log.w("StarView/Scene", "teardown: " + var60);
               break;
            }

            Engine var153 = var195;
            if (var195 == null) {
               try {
                  Intrinsics.throwUninitializedPropertyAccessException("engine");
               } catch (Exception var59) {
                  Log.w("StarView/Scene", "teardown: " + var59);
                  break;
               }

               var153 = null;
            }

            try {
               var227 = this.groundMaterial;
            } catch (Exception var58) {
               Log.w("StarView/Scene", "teardown: " + var58);
               break;
            }

            Material var196 = var227;
            if (var227 == null) {
               try {
                  Intrinsics.throwUninitializedPropertyAccessException("groundMaterial");
               } catch (Exception var57) {
                  Log.w("StarView/Scene", "teardown: " + var57);
                  break;
               }

               var196 = null;
            }

            try {
               var153.destroyMaterial(var196);
               var197 = this.engine;
            } catch (Exception var56) {
               Log.w("StarView/Scene", "teardown: " + var56);
               break;
            }

            Engine var154 = var197;
            if (var197 == null) {
               try {
                  Intrinsics.throwUninitializedPropertyAccessException("engine");
               } catch (Exception var55) {
                  Log.w("StarView/Scene", "teardown: " + var55);
                  break;
               }

               var154 = null;
            }

            try {
               var228 = this.shadowMaterial;
            } catch (Exception var54) {
               Log.w("StarView/Scene", "teardown: " + var54);
               break;
            }

            Material var198 = var228;
            if (var228 == null) {
               try {
                  Intrinsics.throwUninitializedPropertyAccessException("shadowMaterial");
               } catch (Exception var53) {
                  Log.w("StarView/Scene", "teardown: " + var53);
                  break;
               }

               var198 = null;
            }

            try {
               var154.destroyMaterial(var198);
               var199 = this.engine;
            } catch (Exception var52) {
               Log.w("StarView/Scene", "teardown: " + var52);
               break;
            }

            Engine var155 = var199;
            if (var199 == null) {
               try {
                  Intrinsics.throwUninitializedPropertyAccessException("engine");
               } catch (Exception var51) {
                  Log.w("StarView/Scene", "teardown: " + var51);
                  break;
               }

               var155 = null;
            }

            try {
               var229 = this.ribbonMaterial;
            } catch (Exception var50) {
               Log.w("StarView/Scene", "teardown: " + var50);
               break;
            }

            Material var200 = var229;
            if (var229 == null) {
               try {
                  Intrinsics.throwUninitializedPropertyAccessException("ribbonMaterial");
               } catch (Exception var49) {
                  Log.w("StarView/Scene", "teardown: " + var49);
                  break;
               }

               var200 = null;
            }

            try {
               var155.destroyMaterial(var200);
               var201 = this.scene;
            } catch (Exception var48) {
               Log.w("StarView/Scene", "teardown: " + var48);
               break;
            }

            Scene var156 = var201;
            if (var201 == null) {
               try {
                  Intrinsics.throwUninitializedPropertyAccessException("scene");
               } catch (Exception var47) {
                  Log.w("StarView/Scene", "teardown: " + var47);
                  break;
               }

               var156 = null;
            }

            try {
               var156.removeEntity(this.sun);
               var202 = this.engine;
            } catch (Exception var46) {
               Log.w("StarView/Scene", "teardown: " + var46);
               break;
            }

            Engine var157 = var202;
            if (var202 == null) {
               try {
                  Intrinsics.throwUninitializedPropertyAccessException("engine");
               } catch (Exception var45) {
                  Log.w("StarView/Scene", "teardown: " + var45);
                  break;
               }

               var157 = null;
            }

            try {
               var157.getLightManager().destroy(this.sun);
               EntityManager.get().destroy(this.sun);
               var230 = this.ibl;
            } catch (Exception var44) {
               Log.w("StarView/Scene", "teardown: " + var44);
               break;
            }

            if (var230 != null) {
               try {
                  var203 = this.engine;
               } catch (Exception var43) {
                  Log.w("StarView/Scene", "teardown: " + var43);
                  break;
               }

               Engine var158 = var203;
               if (var203 == null) {
                  try {
                     Intrinsics.throwUninitializedPropertyAccessException("engine");
                  } catch (Exception var42) {
                     Log.w("StarView/Scene", "teardown: " + var42);
                     break;
                  }

                  var158 = null;
               }

               try {
                  var158.destroyIndirectLight(var230);
               } catch (Exception var41) {
                  Log.w("StarView/Scene", "teardown: " + var41);
                  break;
               }
            }

            try {
               this.ibl = null;
               var231 = this.skybox;
            } catch (Exception var40) {
               Log.w("StarView/Scene", "teardown: " + var40);
               break;
            }

            if (var231 != null) {
               try {
                  var204 = this.engine;
               } catch (Exception var39) {
                  Log.w("StarView/Scene", "teardown: " + var39);
                  break;
               }

               Engine var159 = var204;
               if (var204 == null) {
                  try {
                     Intrinsics.throwUninitializedPropertyAccessException("engine");
                  } catch (Exception var38) {
                     Log.w("StarView/Scene", "teardown: " + var38);
                     break;
                  }

                  var159 = null;
               }

               try {
                  var159.destroySkybox(var231);
               } catch (Exception var37) {
                  Log.w("StarView/Scene", "teardown: " + var37);
                  break;
               }
            }

            try {
               this.skybox = null;
               var232 = this.grading;
            } catch (Exception var36) {
               Log.w("StarView/Scene", "teardown: " + var36);
               break;
            }

            if (var232 != null) {
               try {
                  var205 = this.engine;
               } catch (Exception var35) {
                  Log.w("StarView/Scene", "teardown: " + var35);
                  break;
               }

               Engine var160 = var205;
               if (var205 == null) {
                  try {
                     Intrinsics.throwUninitializedPropertyAccessException("engine");
                  } catch (Exception var34) {
                     Log.w("StarView/Scene", "teardown: " + var34);
                     break;
                  }

                  var160 = null;
               }

               try {
                  var160.destroyColorGrading(var232);
               } catch (Exception var33) {
                  Log.w("StarView/Scene", "teardown: " + var33);
                  break;
               }
            }

            try {
               this.grading = null;
               this.gradingGrey = null;
               var206 = this.engine;
            } catch (Exception var32) {
               Log.w("StarView/Scene", "teardown: " + var32);
               break;
            }

            Engine var161 = var206;
            if (var206 == null) {
               try {
                  Intrinsics.throwUninitializedPropertyAccessException("engine");
               } catch (Exception var31) {
                  Log.w("StarView/Scene", "teardown: " + var31);
                  break;
               }

               var161 = null;
            }

            try {
               var233 = this.renderer;
            } catch (Exception var30) {
               Log.w("StarView/Scene", "teardown: " + var30);
               break;
            }

            Renderer var207 = var233;
            if (var233 == null) {
               try {
                  Intrinsics.throwUninitializedPropertyAccessException("renderer");
               } catch (Exception var29) {
                  Log.w("StarView/Scene", "teardown: " + var29);
                  break;
               }

               var207 = null;
            }

            try {
               var161.destroyRenderer(var207);
               var208 = this.engine;
            } catch (Exception var28) {
               Log.w("StarView/Scene", "teardown: " + var28);
               break;
            }

            Engine var162 = var208;
            if (var208 == null) {
               try {
                  Intrinsics.throwUninitializedPropertyAccessException("engine");
               } catch (Exception var27) {
                  Log.w("StarView/Scene", "teardown: " + var27);
                  break;
               }

               var162 = null;
            }

            try {
               var234 = this.view;
            } catch (Exception var26) {
               Log.w("StarView/Scene", "teardown: " + var26);
               break;
            }

            View var209 = var234;
            if (var234 == null) {
               try {
                  Intrinsics.throwUninitializedPropertyAccessException("view");
               } catch (Exception var25) {
                  Log.w("StarView/Scene", "teardown: " + var25);
                  break;
               }

               var209 = null;
            }

            try {
               var162.destroyView(var209);
               var210 = this.engine;
            } catch (Exception var24) {
               Log.w("StarView/Scene", "teardown: " + var24);
               break;
            }

            Engine var163 = var210;
            if (var210 == null) {
               try {
                  Intrinsics.throwUninitializedPropertyAccessException("engine");
               } catch (Exception var23) {
                  Log.w("StarView/Scene", "teardown: " + var23);
                  break;
               }

               var163 = null;
            }

            try {
               var235 = this.scene;
            } catch (Exception var22) {
               Log.w("StarView/Scene", "teardown: " + var22);
               break;
            }

            Scene var211 = var235;
            if (var235 == null) {
               try {
                  Intrinsics.throwUninitializedPropertyAccessException("scene");
               } catch (Exception var21) {
                  Log.w("StarView/Scene", "teardown: " + var21);
                  break;
               }

               var211 = null;
            }

            try {
               var163.destroyScene(var211);
               var212 = this.camera;
            } catch (Exception var20) {
               Log.w("StarView/Scene", "teardown: " + var20);
               break;
            }

            Camera var164 = var212;
            if (var212 == null) {
               try {
                  Intrinsics.throwUninitializedPropertyAccessException("camera");
               } catch (Exception var19) {
                  Log.w("StarView/Scene", "teardown: " + var19);
                  break;
               }

               var164 = null;
            }

            try {
               var1 = var164.getEntity();
               var213 = this.engine;
            } catch (Exception var18) {
               Log.w("StarView/Scene", "teardown: " + var18);
               break;
            }

            Engine var165 = var213;
            if (var213 == null) {
               try {
                  Intrinsics.throwUninitializedPropertyAccessException("engine");
               } catch (Exception var17) {
                  Log.w("StarView/Scene", "teardown: " + var17);
                  break;
               }

               var165 = null;
            }

            try {
               var165.destroyCameraComponent(var1);
               EntityManager.get().destroy(var1);
               var166 = this.engine;
            } catch (Exception var16) {
               Log.w("StarView/Scene", "teardown: " + var16);
               break;
            }

            if (var166 == null) {
               try {
                  Intrinsics.throwUninitializedPropertyAccessException("engine");
               } catch (Exception var15) {
                  Log.w("StarView/Scene", "teardown: " + var15);
                  break;
               }

               var166 = (Engine)var10;
            }

            try {
               var166.destroy();
            } catch (Exception var14) {
               Log.w("StarView/Scene", "teardown: " + var14);
            }
            break;
         }

         SceneView.Car var12;
         try {
            Object var133 = var214.next();
            Intrinsics.checkNotNullExpressionValue(var133, "next(...)");
            var12 = (SceneView.Car)var133;
            var12.show(false);
            var169 = this.assetLoader;
         } catch (Exception var120) {
            Log.w("StarView/Scene", "teardown: " + var120);
            break;
         }

         AssetLoader var134 = var169;
         if (var169 == null) {
            try {
               Intrinsics.throwUninitializedPropertyAccessException("assetLoader");
            } catch (Exception var119) {
               Log.w("StarView/Scene", "teardown: " + var119);
               break;
            }

            var134 = null;
         }

         try {
            var134.destroyAsset(var12.getAsset());
         } catch (Exception var118) {
            Log.w("StarView/Scene", "teardown: " + var118);
            break;
         }
      }
   }

   private final boolean drawMapRoads(ModelSnapshot var1, float var2) {
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

            boolean var109;
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
               var109 = (boolean)1;
            } else {
               var109 = (boolean)0;
            }

            label788: {
               if (var109) {
                  var98 = var184.getRHi();
                  if (!Double.isInfinite(var98) && !Double.isNaN(var98)) {
                     var109 = (boolean)1;
                  } else {
                     var109 = (boolean)0;
                  }

                  if (var109) {
                     var98 = var184.getLLo();
                     if (!Double.isInfinite(var98) && !Double.isNaN(var98)) {
                        var109 = (boolean)1;
                     } else {
                        var109 = (boolean)0;
                     }

                     if (var109) {
                        var98 = var184.getRLo();
                        if (!Double.isInfinite(var98) && !Double.isNaN(var98)) {
                           var109 = (boolean)1;
                        } else {
                           var109 = (boolean)0;
                        }

                        if (var109) {
                           var109 = (boolean)1;
                           break label788;
                        }
                     }
                  }
               }

               var109 = (boolean)0;
            }

            if (var109) {
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

                  var98 = var62.laneCenter(var98, var25, var109, var37);
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

         boolean var116;
         if (this.mapAlign.getGuess().length() > 0) {
            var116 = 1;
         } else {
            var116 = 0;
         }

         String var70;
         if (var116) {
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
                  var116 = (boolean)1;
               } else {
                  var116 = (boolean)0;
               }

               if (var116) {
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
               var116 = (boolean)1;
            } else {
               var116 = (boolean)0;
            }

            if (var116) {
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
                     var116 = (boolean)1;
                  } else {
                     var116 = (boolean)0;
                  }

                  if (!var116 || !OsmRoads.INSTANCE.sameStreet(var202.getName(), var223.getName())) {
                     continue;
                  }
               }

               var198.add(var202.getId());
            }
         }

         boolean var131;
         if (var223 != null && var223.getHighway()) {
            var131 = 1;
         } else {
            var131 = 0;
         }

         HashMap var258 = new HashMap();
         var116 = (boolean)0;
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
                              Long var266 = drawMapRoads$k(var261.getE()[var116], var261.getN()[var116]);
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

                     for (var var42_it : (List)var39) {

                        var42 = var42_it;
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
                                       && StringsKt.endsWith$default(var268.getKind(), "_link", false, 2, null)
                                          == StringsKt.endsWith$default(var270.getKind(), "_link", false, 2, null)) {
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
               var116 = (boolean)0;
               var209 = var198;
               var182 = var212;
               var213 = var217;
            }
         } else {
            var116 = (boolean)0;
            var213 = var217;
            var209 = var198;
            var182 = var212;
         }

         short var26 = var116;
         if (var223 != null) {
            Iterator var228 = this.crossNow.entrySet().iterator();
            SceneView$$ExternalSyntheticLambda2 var221 = var182;
            Iterator var183 = var228;

            while (true) {
               var26 = var116;
               if (!var183.hasNext()) {
                  break;
               }

               Entry var229 = (Entry)var183.next();
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
                  if (var131) {
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
                        var116 = (boolean)1;
                        var73 = var239;
                        var183 = var231;
                     }
                  }
               }
            }
         }

         boolean var137;
         if (!this.night && !this.alwaysDark) {
            var137 = 0;
         } else {
            var137 = 1;
         }

         short var28;
         short var124;
         short var142;
         if (var137) {
            var124 = 51;
            var28 = 60;
            var142 = 46;
         } else {
            var124 = 169;
            var28 = 178;
            var142 = 163;
         }

         int var30 = Color.rgb(var142, var124, var28);
         if (var137) {
            var124 = Color.rgb(106, 113, 125);
         } else {
            var124 = Color.rgb(246, 247, 249);
         }

         short var29;
         if (var137) {
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
            if (var137) {
               var142 = Color.rgb(214, 218, 224);
            } else {
               var142 = -1;
            }

            if (var137) {
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
         if (var26) {
            drawMapRoads$pass(var213, var258, this, new SceneView$$ExternalSyntheticLambda8(var209, this, var30, var255));
         }

         if (!var258.isEmpty()) {
            if (var223 != null) {
               if (var131) {
                  var98 = this.lanesOf(var223) * this.laneW;
               } else {
                  var98 = var223.getWidth();
               }

               var98 = var98 / 2.0 + 3.0;
            } else {
               var98 = 10.0;
            }

            short var147;
            if (var137) {
               var147 = 64;
               var131 = 74;
               var26 = 58;
            } else {
               var147 = 189;
               var131 = 197;
               var26 = 184;
            }

            int var31 = Color.rgb(var26, var147, var131);
            byte var133;
            byte var140;
            if (var137) {
               var133 = 34;
               var147 = 41;
               var140 = 30;
            } else {
               var133 = 117;
               var147 = 128;
               var140 = 110;
            }

            int var32 = Color.rgb(var140, var133, var147);
            if (var137) {
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

                     if (var150 != 0) {
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
                           var137,
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

   private static final double[] drawMapRoads$cOf(IdentityHashMap<OsmRoads.Piece, Pair<double[], double[]>> var0, OsmRoads.Piece var1) {
      Pair var2 = (Pair)var0.get(var1);
      double[] var3;
      if (var2 != null) {
         var3 = (double[])var2.getFirst();
      } else {
         var3 = null;
      }

      return var3;
   }

   private static final double drawMapRoads$dir(OsmRoads.Piece var0) {
      double var1;
      if (var0.getWay().getOneway() == -1) {
         var1 = -1.0;
      } else {
         var1 = 1.0;
      }

      return var1;
   }

   private static final double[] drawMapRoads$hOf(IdentityHashMap<OsmRoads.Piece, Pair<double[], double[]>> var0, OsmRoads.Piece var1) {
      Pair var2 = (Pair)var0.get(var1);
      double[] var3;
      if (var2 != null) {
         var3 = (double[])var2.getSecond();
      } else {
         var3 = null;
      }

      return var3;
   }

   private static final double drawMapRoads$half(SceneView var0, OsmRoads.Piece var1) {
      double var2;
      if (var1.getWay().getHighway()) {
         var2 = var0.lanesOf(var1.getWay()) * var0.laneW;
      } else {
         Double var4 = var0.learnedW.get(var1.getWay().getId());
         Double var5;
         if (var4 != null) {
            var5 = var4;
         } else {
            if (var0.hintChain.contains(var1.getWay().getId())) {
               var5 = var0.learnedW.get(var0.hintChainOf);
            } else {
               var5 = null;
            }

            if (var5 == null) {
               var2 = var1.getWay().getWidth();
               return var2 / 2.0;
            }
         }

         var2 = var5;
      }

      return var2 / 2.0;
   }

   private static final long drawMapRoads$k(double var0, double var2) {
      return Math.round(var0 * 10.0) * 4000003L + Math.round(10.0 * var2);
   }

   private static final Pair drawMapRoads$lambda$72(SceneView var0, double var1, double var3, double var5) {
      Pair var7 = var0.mapAlign.inverse(var3 - var0.shiftE, var5 - var0.shiftN);
      Pair var8 = var0.fromWorld(((Number)var7.component1()).doubleValue(), ((Number)var7.component2()).doubleValue());
      var5 = ((Number)var8.component1()).doubleValue();
      var3 = ((Number)var8.component2()).doubleValue();
      return OsmRoads.INSTANCE.turn(var5, var3, var1);
   }

   private static final Pair drawMapRoads$lambda$82(SceneView var0, OsmRoads.Piece var1) {
      Intrinsics.checkNotNullParameter(var1, "p");
      return TuplesKt.to(drawMapRoads$mid(var0, var1), drawMapRoads$half(var0, var1));
   }

   private static final Unit drawMapRoads$lambda$83(Set var0, SceneView var1, int var2, IdentityHashMap var3, OsmRoads.Piece var4) {
      Intrinsics.checkNotNullParameter(var4, "p");
      if (var0.contains(var4.getWay().getKind())) {
         polyRibbon$default(
            var1, var1.curN, 0.0, var1.SIDE_W + 0.25, var2, 0.02F, var1.MAP_R, null, null, new double[var1.curN], drawMapRoads$hOf(var3, var4), 192, null
         );
      }

      return Unit.INSTANCE;
   }

   private static final Unit drawMapRoads$lambda$84(SceneView var0, int var1, IdentityHashMap var2, OsmRoads.Piece var3) {
      Intrinsics.checkNotNullParameter(var3, "p");
      polyRibbon$default(
         var0, var0.curN, 0.0, 0.25, var1, 0.025F, var0.MAP_R, null, null, drawMapRoads$cOf(var2, var3), drawMapRoads$hOf(var2, var3), 192, null
      );
      return Unit.INSTANCE;
   }

   private static final Unit drawMapRoads$lambda$85(SceneView var0, int var1, IdentityHashMap var2, OsmRoads.Piece var3) {
      Intrinsics.checkNotNullParameter(var3, "p");
      polyRibbon$default(var0, var0.curN, 0.0, 0.0, var1, 0.03F, var0.MAP_R, null, null, drawMapRoads$cOf(var2, var3), drawMapRoads$hOf(var2, var3), 192, null);
      return Unit.INSTANCE;
   }

   private static final Unit drawMapRoads$lambda$88(
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

   private static final Unit drawMapRoads$lambda$89(HashSet var0, SceneView var1, int var2, IdentityHashMap var3, OsmRoads.Piece var4) {
      Intrinsics.checkNotNullParameter(var4, "p");
      if (var0.contains(var4.getWay().getId())) {
         polyRibbon$default(
            var1, var1.curN, 0.0, 0.0, var2, 0.045F, var1.MAP_R, null, null, drawMapRoads$cOf(var3, var4), drawMapRoads$hOf(var3, var4), 192, null
         );
      }

      return Unit.INSTANCE;
   }

   private static final double drawMapRoads$mid(SceneView var0, OsmRoads.Piece var1) {
      double var2;
      if (var0.laneMapped(var1.getWay())) {
         var2 = drawMapRoads$dir(var1) * (var0.leftEdge(var1.getWay()) + drawMapRoads$half(var0, var1));
      } else {
         var2 = 0.0;
      }

      return var2;
   }

   private static final void drawMapRoads$pass(
      List<OsmRoads.Piece> var0, HashMap<Long, List<double[]>> var1, SceneView var2, Function1<? super OsmRoads.Piece, Unit> var3
   ) {
      for (OsmRoads.Piece var4 : var0) {
         if (!var1.containsKey(var4.getWay().getId())) {
            var2.load(var4);
            if (var2.curN >= 2) {
               var3.invoke(var4);
            }
         }
      }
   }

   private final double edgeDist(ModelSnapshot var1, int var2) {
      int var8 = this.edgeS[var2].length;
      boolean var9 = true;
      boolean var20;
      if (var8 == 0) {
         var20 = 1;
      } else {
         var20 = 0;
      }

      double var5 = Double.NaN;
      if (!var20 && this.lastEdgeX.length == this.edgeS[var2].length) {
         float[] var12 = var1.getEdgeStd();
         if (var2 >= 0 && var2 < var12.length) {
            var20 = (boolean)1;
         } else {
            var20 = (boolean)0;
         }

         float var7;
         if (var20) {
            var7 = var12[var2];
         } else {
            var7 = 9.0F;
         }

         if (!(var7 > 1.5F)) {
            double var3;
            if (var2 == 0) {
               var3 = -1.0;
            } else {
               var3 = 1.0;
            }

            ArrayList var13 = new ArrayList();
            var20 = 0;

            for (int var10 = this.lastEdgeX.length; var20 < var10; var20++) {
               var7 = this.lastEdgeX[var20];
               if (!(var7 < 3.0F)) {
                  if (var7 > 15.0F) {
                     break;
                  }

                  var13.add(this.edgeS[var2][var20] * var3);
               }
            }

            if (var13.isEmpty()) {
               return Double.NaN;
            }

            CollectionsKt.sort(var13);
            Double var14 = (Double)var13.get(var13.size() / 2);
            Double var11 = var14;
            Intrinsics.checkNotNull(var11);
            var3 = var11;
            boolean var16;
            if (0.4 <= var3 && var3 <= 25.0) {
               var16 = var9;
            } else {
               var16 = false;
            }

            if (!var16) {
               var14 = null;
            }

            var14 = var14;
            var3 = var5;
            if (var14 != null) {
               var3 = var14;
            }

            return var3;
         }
      }

      return Double.NaN;
   }

   private final void endRibbons() {
      if (this.nIdx == 0) {
         this.clearRibbons();
      } else {
         ((Buffer)this.getVbuf()).flip();
         ((Buffer)this.getIbuf()).flip();
         VertexBuffer var3 = this.ribbonVb;
         VertexBuffer var2 = var3;
         if (var3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ribbonVb");
            var2 = null;
         }

         Engine var4 = this.engine;
         Engine var9 = var4;
         if (var4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("engine");
            var9 = null;
         }

         var2.setBufferAt(var9, 0, this.getVbuf());
         IndexBuffer var10 = this.ribbonIb;
         IndexBuffer var6 = var10;
         if (var10 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ribbonIb");
            var6 = null;
         }

         var4 = this.engine;
         Engine var11 = var4;
         if (var4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("engine");
            var11 = null;
         }

         var6.setBuffer(var11, this.getIbuf());
         Engine var12 = this.engine;
         Engine var7 = var12;
         if (var12 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("engine");
            var7 = null;
         }

         RenderableManager var15 = var7.getRenderableManager();
         int var1 = var15.getInstance(this.ribbonEntity);
         PrimitiveType var5 = PrimitiveType.TRIANGLES;
         var2 = this.ribbonVb;
         if (var2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ribbonVb");
            var2 = null;
         }

         IndexBuffer var13 = this.ribbonIb;
         if (var13 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ribbonIb");
            var13 = null;
         }

         var15.setGeometryAt(var1, 0, var5, var2, var13, 0, this.nIdx);
      }
   }

   private final double facingAngle(int var1, double var2) {
      double var4 = 0.0;
      switch (var1) {
         case 1:
            var2 = Math.PI;
            break;
         case 2:
            if (var2 > 0.0) {
               var2 = -Math.PI / 2;
            } else {
               var2 = Math.PI / 2;
            }
            break;
         default:
            var2 = var4;
      }

      return var2;
   }

   private final void fillBetween(float[] var1, float[] var2, float[] var3, int var4, float var5, Function1<? super Integer, Float> var6) {
      int var13 = Math.min(var1.length, Math.min(var2.length, var3.length));
      if (var13 >= 2 && this.nVerts + var13 * 2 <= this.maxVerts && this.nIdx + (var13 - 1) * 6 <= this.maxIdx) {
         Triple var15 = Companion.linear(var4);
         float var9 = ((Number)var15.component1()).floatValue();
         float var7 = ((Number)var15.component2()).floatValue();
         float var10 = ((Number)var15.component3()).floatValue();
         int var12 = this.nVerts;

         for (int var11 = 0; var11 < var13; var11++) {
            float var8 = RangesKt.coerceIn(Color.alpha(var4) / 255.0F * ((Number)var6.invoke(var11)).floatValue(), 0.0F, 1.0F);
            int var14 = this.packColor(var9 * var8, var7 * var8, var10 * var8, var8);
            this.putVertex(var2[var11], var5, -var1[var11], var14);
            this.putVertex(var3[var11], var5, -var1[var11], var14);
         }

         for (int var16 = 0; var16 < var13 - 1; var16++) {
            int var17 = var16 * 2 + var12;
            this.putTri(var17, var17 + 2, var17 + 1);
            this.putTri(var17 + 1, var17 + 2, var17 + 3);
         }
      }
   }

   private final void fitRoadShape(float[] var1, int var2) {
      double[][] var17 = new double[3][];

      for (int var11 = 0; var11 < 3; var11++) {
         var17[var11] = new double[3];
      }

      double[] var16 = new double[3];
      int var12 = 0;

      for (int var27 = 0; var27 < var2; var27++) {
         double var3 = var1[var27];
         if (var3 > 90.0) {
            break;
         }

         double var5 = (this.edgeS[0][var27] + this.edgeS[1][var27]) / 2.0;
         double var7 = 1.0 / (var3 / 25.0 + 1.0);
         double[] var19 = new double[]{1.0, var3, var3 * var3};

         for (int var13 = 0; var13 < 3; var13++) {
            var16[var13] += var19[var13] * var7 * var5;

            for (int var14 = 0; var14 < 3; var14++) {
               double[] var18 = var17[var13];
               var18[var14] += var19[var13] * var7 * var19[var14];
            }
         }

         var12++;
      }

      if (var12 < 5) {
         this.shapeOk = false;
      } else {
         for (int var28 = 0; var28 < 3; var28++) {
            double[] var42 = var17[var28];
            var42[var28] += (var28 + 1) * 1.0E-6;
         }

         double[] var43 = var17[2];
         var43[2] *= 1.3;

         for (int var29 = 0; var29 < 3; var29++) {
            int var34 = var29;
            int var31 = var29 + 1;

            while (var31 < 3) {
               int var15 = var34;
               if (Math.abs(var17[var31][var29]) > Math.abs(var17[var34][var29])) {
                  var15 = var31;
               }

               var31++;
               var34 = var15;
            }

            var43 = var17[var29];
            var17[var29] = var17[var34];
            var17[var34] = var43;
            double var20 = var16[var29];
            var16[var29] = var16[var34];
            var16[var34] = var20;
            if (Math.abs(var17[var29][var29]) < 1.0E-12) {
               this.shapeOk = false;
               return;
            }

            for (int var32 = 0; var32 < 3; var32++) {
               if (var32 != var29) {
                  var20 = var17[var32][var29] / var17[var29][var29];

                  for (int var35 = var29; var35 < 3; var35++) {
                     var43 = var17[var32];
                     var43[var35] -= var17[var29][var35] * var20;
                  }

                  var16[var32] -= var16[var29] * var20;
               }
            }
         }

         double var25 = var16[0] / var17[0][0];
         double var26 = var16[1] / var17[1][1];
         double var9 = RangesKt.coerceIn(var16[2] / var17[2][2], -0.033, 0.033);
         double var22;
         if (this.shapeOk) {
            var22 = 0.3;
         } else {
            var22 = 1.0;
         }

         double[] var36 = this.shapeC;
         var36[0] += (var25 - this.shapeC[0]) * var22;
         double[] var37 = this.shapeC;
         var37[1] += (var26 - this.shapeC[1]) * var22;
         double[] var38 = this.shapeC;
         var38[2] += (var9 - this.shapeC[2]) * var22;

         for (int var30 = 0; var30 < 2; var30++) {
            ArrayList var39 = new ArrayList();

            for (int var33 = 0; var33 < var2; var33++) {
               var22 = var1[var33];
               if (var22 > 50.0) {
                  break;
               }

               var39.add(this.edgeS[var30][var33] - (this.shapeC[0] + this.shapeC[1] * var22 + this.shapeC[2] * var22 * var22));
            }

            if (var39.isEmpty()) {
               this.shapeOk = false;
               return;
            }

            CollectionsKt.sort(var39);
            var17 = (double[][])var39.get(var39.size() / 2);
            Intrinsics.checkNotNullExpressionValue(var17, "get(...)");
            var22 = ((Number)var17).doubleValue();
            double[] var41 = this.shapeOff;
            if (this.shapeOk) {
               var22 = this.shapeOff[var30] + (var22 - this.shapeOff[var30]) * 0.3;
            }

            var41[var30] = var22;
         }

         this.shapeOk = true;
      }
   }

   private final Pair<Double, Double> fromWorld(double var1, double var3) {
      double var7 = Math.cos(this.odoYaw);
      double var5 = Math.sin(this.odoYaw);
      var1 -= this.odoX;
      var3 -= this.odoY;
      return new Pair<>(var7 * var1 + var5 * var3, -(-var5 * var1 + var7 * var3));
   }

   private final ByteBuffer getIbuf() {
      return this.ibufs[this.bufSel];
   }

   private final SceneView.Palette getPal() {
      SceneView.Palette var1;
      if (!this.night && !this.alwaysDark) {
         var1 = this.day;
      } else {
         var1 = this.nightP;
      }

      return var1;
   }

   private final ByteBuffer getVbuf() {
      return this.vbufs[this.bufSel];
   }

   private final int groundAt(double var1, double var3) {
      if (this.snaps.isEmpty()) {
         return -1;
      } else {
         double var7 = Math.cos(this.odoYaw);
         double var5 = Math.sin(this.odoYaw);
         double var11 = -var3;
         var3 = this.odoX;
         double var9 = this.odoY;
         var3 = var3 + var7 * var1 - var5 * var11 - this.texX;
         double var13 = var9 + var5 * var1 + var7 * var11 - this.texY;
         var5 = Math.cos(this.texYaw);
         var1 = Math.sin(this.texYaw);
         var9 = -var1;
         var11 = this.XMIN;
         var7 = 0.5F;
         int var15 = (int)Math.floor((var5 * var3 + var1 * var13 - var11) / var7);
         int var16 = (int)Math.floor((-(var9 * var3 + var5 * var13) + 20.0F) / var7);
         if (var15 >= 0 && var15 < this.MR && var16 >= 0 && var16 < 80) {
            var15 = this.memGrp[var15 * 80 + var16] & 255;
            if (var15 > 3) {
               var15 = -1;
            }

            return var15;
         } else {
            return -1;
         }
      }
   }

   private final double headingAt(ModelSnapshot var1, double var2) {
      double var4 = 0.0;
      if (var1 == null) {
         return 0.0;
      } else {
         float[] var11 = var1.getPosX();
         float[] var12 = var1.getPosY();
         if (var11.length < 2) {
            return 0.0;
         } else {
            int var10 = 1;

            while (var10 < var11.length - 1 && var11[var10] < var2) {
               var10++;
            }

            double var8 = var11[var10] - var11[var10 - 1];
            double var6 = var12[var10] - var12[var10 - 1];
            var2 = var4;
            if (var8 > 0.001) {
               var2 = Math.atan2(var6, var8);
            }

            return var2;
         }
      }
   }

   private final void hintChainNow(List<OsmRoads.Way> var1, OsmRoads.Way var2) {
      if (var2 != null) {
         label105: {
            for (Object var8 : var1) {
               boolean var3;
               if (((OsmRoads.Way)var8).getId() == var2.getId()) {
                  var3 = true;
               } else {
                  var3 = false;
               }

               if (var3) {
                  var13 = var8;
                  break label105;
               }
            }

            var13 = null;
         }

         var2 = (OsmRoads.Way)var13;
      } else {
         var2 = null;
      }

      if (var2 == null) {
         this.hintChain = SetsKt.emptySet();
         this.hintSame = SetsKt.emptySet();
         this.hintChainOf = 0L;
      } else {
         HashSet var10 = new HashSet();
         HashSet var11 = new HashSet();

         for (int var15 = 0; var15 < 2; var15++) {
            OsmRoads.Way var18 = var2;
            int var5 = var15;

            for (int var4 = 0; var4 < 6; var4++) {
               Pair var23 = this.continuation(var18, var5, var1);
               if (var23 == null) {
                  break;
               }

               var18 = (OsmRoads.Way)var23.component1();
               var5 = ((Number)var23.component2()).intValue();
               if (var18.getId() == var2.getId() || !var10.add(var18.getId())) {
                  break;
               }

               var5 = 1 - var5;
            }
         }

         Iterator var12 = var10.iterator();
         Intrinsics.checkNotNullExpressionValue(var12, "iterator(...)");

         while (var12.hasNext()) {
            Pair var19 = (Pair)var12.next();
            Intrinsics.checkNotNullExpressionValue(var19, "next(...)");
            long var6 = ((Number)var19).longValue();
            Iterator var24 = var1.iterator();

            label77: {
               while (var24.hasNext()) {
                  var19 = var24.next();
                  boolean var16;
                  if (((OsmRoads.Way)var19).getId() == var6) {
                     var16 = true;
                  } else {
                     var16 = false;
                  }

                  if (var16) {
                     break label77;
                  }
               }

               var19 = null;
            }

            OsmRoads.Way var25 = (OsmRoads.Way)var19;
            if (var25 != null) {
               if (var25.getLaneCount() == var2.getLaneCount()) {
                  var11.add(var6);
               } else {
                  var19 = this.continuation(var25, 0, var1);
                  OsmRoads.Way var22;
                  if (var19 != null) {
                     var22 = (OsmRoads.Way)var19.getFirst();
                  } else {
                     var22 = null;
                  }

                  Pair var26 = this.continuation(var25, 1, var1);
                  OsmRoads.Way var27;
                  if (var26 != null) {
                     var27 = (OsmRoads.Way)var26.getFirst();
                  } else {
                     var27 = null;
                  }

                  if (var22 != null && var27 != null && var22.getLaneCount() == var2.getLaneCount() && var27.getLaneCount() == var2.getLaneCount()) {
                     var11.add(var6);
                  }
               }
            }
         }

         this.hintChain = var10;
         this.hintSame = var11;
         this.hintChainOf = var2.getId();
      }
   }

   private final SceneView.LaneHint hintFor(OsmRoads.Way var1) {
      SceneView.LaneHint var3 = this.hintNow;
      if (var3 != null) {
         boolean var2;
         if (var3.getWayId() == var1.getId() || var3.getWayId() == this.hintChainOf && this.hintChain.contains(var1.getId())) {
            var2 = true;
         } else {
            var2 = false;
         }

         if (var2) {
            return var3;
         }
      }

      return null;
   }

   private final boolean laneMapped(OsmRoads.Way var1) {
      boolean var2;
      if (var1.getHighway() && var1.getOneway() != 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   private final int lanesOf(OsmRoads.Way var1) {
      SceneView.LaneHint var3 = this.hintNow;
      if (var3 != null) {
         boolean var2;
         if ((var3.getWayId() == var1.getId() || var3.getWayId() == this.hintChainOf && this.hintSame.contains(var1.getId())) && var3.getTotal() > 0) {
            var2 = true;
         } else {
            var2 = false;
         }

         if (!var2) {
            var3 = null;
         }

         if (var3 != null) {
            return var3.getTotal();
         }
      }

      return var1.getLaneCount();
   }

   private final double leftEdge(OsmRoads.Way var1) {
      SceneView.LaneHint var5 = this.hintFor(var1);
      if (var5 != null) {
         Double var7 = var5.getEdge();
         double var2 = var7.doubleValue();
         boolean var4;
         if (!Double.isInfinite(var2) && !Double.isNaN(var2)) {
            var4 = true;
         } else {
            var4 = false;
         }

         if (!var4) {
            var7 = null;
         }

         if (var7 != null) {
            return var7;
         }
      }

      return -this.baseLanes(var1) * this.laneW / 2;
   }

   private final double lineDist(ModelSnapshot var1, int var2) {
      int var8 = ((Object[])var1.getLaneY()).length;
      double var3 = Double.NaN;
      if (var2 < var8 && var2 < ((Object[])var1.getLaneX()).length) {
         float[] var10 = var1.getLaneProb();
         boolean var9 = true;
         boolean var13;
         if (var2 >= 0 && var2 < var10.length) {
            var13 = 1;
         } else {
            var13 = 0;
         }

         float var7;
         if (var13) {
            var7 = var10[var2];
         } else {
            var7 = 0.0F;
         }

         if (!(var7 < 0.3F)) {
            if (var1.getLaneX()[var2].length == 0) {
               var13 = (boolean)1;
            } else {
               var13 = (boolean)0;
            }

            if (!var13) {
               var10 = var1.getLaneX()[var2];
               var13 = 0;

               while (var13 < var10.length - 1 && var10[var13] < 10.0F) {
                  var13++;
               }

               Double var11 = Math.abs((double)var1.getLaneY()[var2][var13]);
               double var5 = var11.doubleValue();
               boolean var12;
               if (0.5 <= var5 && var5 <= 3.0) {
                  var12 = var9;
               } else {
                  var12 = false;
               }

               if (!var12) {
                  var11 = null;
               }

               if (var11 != null) {
                  var3 = var11;
               }

               return var3;
            }
         }
      }

      return Double.NaN;
   }

   private final void load(OsmRoads.Piece var1) {
      int var3 = Math.min(var1.getX().length, this.mx.length);

      for (int var2 = 0; var2 < var3; var2++) {
         this.mx[var2] = var1.getX()[var2];
         this.my[var2] = var1.getY()[var2];
      }

      this.curN = var3;
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   private final SceneView.Car loadCar(String var1) {
      AssetLoader var8;
      ByteBuffer var9;
      try {
         StringBuilder var7 = new StringBuilder();
         var9 = this.readAsset(var7.append("models/").append(var1).toString());
         var8 = this.assetLoader;
      } catch (Exception var21) {
         Log.e("StarView/Scene", "load " + var1 + " failed: " + var21);
         return null;
      }

      AssetLoader var23 = var8;
      if (var8 == null) {
         try {
            Intrinsics.throwUninitializedPropertyAccessException("assetLoader");
         } catch (Exception var20) {
            Log.e("StarView/Scene", "load " + var1 + " failed: " + var20);
            return null;
         }

         var23 = null;
      }

      try {
         var33 = var23.createAsset(var9);
      } catch (Exception var19) {
         Log.e("StarView/Scene", "load " + var1 + " failed: " + var19);
         return null;
      }

      SceneView.Car var22;
      if (var33 != null) {
         try {
            var30 = this.resourceLoader;
         } catch (Exception var17) {
            Log.e("StarView/Scene", "load " + var1 + " failed: " + var17);
            return null;
         }

         ResourceLoader var24 = var30;
         if (var30 == null) {
            try {
               Intrinsics.throwUninitializedPropertyAccessException("resourceLoader");
            } catch (Exception var16) {
               Log.e("StarView/Scene", "load " + var1 + " failed: " + var16);
               return null;
            }

            var24 = null;
         }

         double var2;
         try {
            var24.loadResources(var33);
            var33.releaseSourceData();
            Box var25 = var33.getBoundingBox();
            Intrinsics.checkNotNullExpressionValue(var25, "getBoundingBox(...)");
            var2 = var25.getHalfExtent()[2] * 2;
            var31 = this.engine;
         } catch (Exception var15) {
            Log.e("StarView/Scene", "load " + var1 + " failed: " + var15);
            return null;
         }

         Engine var26 = var31;
         if (var31 == null) {
            try {
               Intrinsics.throwUninitializedPropertyAccessException("engine");
            } catch (Exception var14) {
               Log.e("StarView/Scene", "load " + var1 + " failed: " + var14);
               return null;
            }

            var26 = null;
         }

         int var5;
         try {
            var27 = var26.getRenderableManager();
            var32 = var33.getEntities();
            var5 = var32.length;
         } catch (Exception var13) {
            Log.e("StarView/Scene", "load " + var1 + " failed: " + var13);
            return null;
         }

         for (int var4 = 0; var4 < var5; var4++) {
            int var6;
            try {
               var6 = var27.getInstance(var32[var4]);
            } catch (Exception var12) {
               Log.e("StarView/Scene", "load " + var1 + " failed: " + var12);
               Object var34 = null;
               return (SceneView.Car)var34;
            }

            if (var6 != 0) {
               try {
                  var27.setCastShadows(var6, true);
                  var27.setReceiveShadows(var6, false);
               } catch (Exception var11) {
                  Log.e("StarView/Scene", "load " + var1 + " failed: " + var11);
                  Object var35 = null;
                  return (SceneView.Car)var35;
               }
            }
         }

         try {
            var28 = new SceneView.Car(this, (double)var33, var2, var1);
         } catch (Exception var10) {
            Log.e("StarView/Scene", "load " + var1 + " failed: " + var10);
            return null;
         }

         var22 = var28;
      } else {
         try {
            IllegalStateException var29 = new IllegalStateException("createAsset");
            throw var29;
         } catch (Exception var18) {
            Log.e("StarView/Scene", "load " + var1 + " failed: " + var18);
            var22 = null;
         }
      }

      return var22;
   }

   private static final void longPressRun$lambda$0(SceneView var0) {
      if (var0.pressArmed) {
         var0.pressArmed = false;
         Function0 var1 = var0.onLongPress;
         if (var1 != null) {
            var1.invoke();
         }
      }
   }

   private final double[] odoAt(long var1) {
      double[] var3 = Odometry.INSTANCE.at(var1);
      return new double[]{var1, var3[0], var3[1], var3[2]};
   }

   private final int packColor(float var1, float var2, float var3, float var4) {
      var1 = RangesKt.coerceIn(var1, 0.0F, 1.0F);
      float var5 = 255;
      int var7 = (int)(var1 * var5);
      int var8 = (int)(RangesKt.coerceIn(var2, 0.0F, 1.0F) * var5);
      int var6 = (int)(RangesKt.coerceIn(var3, 0.0F, 1.0F) * var5);
      return (int)(RangesKt.coerceIn(var4, 0.0F, 1.0F) * var5) << 24 | var6 << 16 | var8 << 8 | var7;
   }

   private final void paint(boolean var1) {
      this.layoutDark = var1;
      this.layoutPaintedHide = this.layoutHideRoad;
      int var4 = 3;
      int[][] var9;
      if (var1) {
         var9 = new int[][]{{44, 49, 59, 235}, {72, 79, 92, 240}, {31, 68, 44, 235}, {7, 8, 11, 245}};
      } else {
         var9 = new int[][]{{179, 185, 195, 240}, {247, 248, 250, 245}, {163, 209, 146, 235}, {126, 133, 145, 245}};
      }

      int var6 = this.MR;
      ((Buffer)this.texBuf).clear();

      for (int var5 = 0; var5 < var6 * 80; var5++) {
         int var7 = this.memGrp[var5] & 255;
         if (var7 > var4 || var7 == 0 && this.layoutHideRoad) {
            this.texBuf.putInt(0);
         } else {
            var4 = var5 / 80;
            int var8 = var5 % 80;
            float var2 = RangesKt.coerceIn(Math.min(Math.min(var4, this.MR - 1 - var4), Math.min(var8, 80 - 1 - var8)) / 12.0F, 0.0F, 1.0F);
            int[] var11 = var9[var7];
            float var3 = RangesKt.coerceIn((10.0F - this.memAge[var5]) / 4.0F, 0.0F, 1.0F);
            this.texBuf.put((byte)var11[0]);
            this.texBuf.put((byte)var11[1]);
            this.texBuf.put((byte)var11[2]);
            ByteBuffer var10 = this.texBuf;
            var4 = 3;
            var10.put((byte)(var11[3] * (var3 * var2)));
         }
      }

      ((Buffer)this.texBuf).flip();
      Texture var15 = this.layoutTex;
      Engine var14 = null;
      Texture var13 = var15;
      if (var15 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("layoutTex");
         var13 = null;
      }

      Engine var16 = this.engine;
      if (var16 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("engine");
      } else {
         var14 = var16;
      }

      var13.setImage(var14, 0, new PixelBufferDescriptor(this.texBuf, Format.RGBA, Type.UBYTE));
   }

   private final void polyLine(int var1, double var2, double var4, int var6, float var7, double var8, boolean var10, double[] var11) {
      if (!var10) {
         if (var11 != null) {
            polyRibbon$default(this, var1, 0.0, var4, var6, var7, var8, null, null, var11, null, 704, null);
         } else {
            polyRibbon$default(this, var1, var2, var4, var6, var7, var8, null, null, null, null, 960, null);
         }
      } else if (var1 >= 2) {
         for (int var44 = 0; var44 < var1; var44++) {
            double var52;
            double var55;
            double var57;
            label81: {
               int var45 = Math.max(0, var44 - 1);
               int var46 = Math.min(var1 - 1, var44 + 1);
               var55 = this.mx[var46] - this.mx[var45];
               var57 = this.my[var46] - this.my[var45];
               var52 = RangesKt.coerceAtLeast(Math.hypot(var55, var57), 1.0E-6);
               var55 /= var52;
               var57 /= var52;
               if (var11 != null) {
                  Double var47 = ArraysKt.getOrNull(var11, var44);
                  if (var47 != null) {
                     var52 = var47;
                     break label81;
                  }
               }

               var52 = var2;
            }

            this.dashX[var44] = this.mx[var44] - var57 * var52;
            this.dashY[var44] = this.my[var44] + var55 * var52;
         }

         double var18 = 0.0;
         double var53 = 0.0;
         var2 = Double.MAX_VALUE;
         int var66 = 0;

         while (var66 < var1) {
            double var56 = var18;
            if (var66 > 0) {
               var56 = var18 + Math.hypot(this.dashX[var66] - this.dashX[var66 - 1], this.dashY[var66] - this.dashY[var66 - 1]);
            }

            var18 = Math.hypot(this.dashX[var66], this.dashY[var66]);
            double var58 = var2;
            if (var18 < var2) {
               var58 = var18;
               var53 = var56;
            }

            var66++;
            var18 = var56;
            var2 = var58;
         }

         Triple var51 = Companion.linear(var6);
         float var42 = ((Number)var51.component1()).floatValue();
         float var41 = ((Number)var51.component2()).floatValue();
         float var40 = ((Number)var51.component3()).floatValue();
         double var59 = 0.0;
         var66 = 0;
         var1--;
         var2 = var53;

         for (double var54 = var59; var66 < var1; var66++) {
            double var26 = this.dashX[var66];
            double var28 = this.dashY[var66];
            double var20 = this.dashX[var66 + 1] - var26;
            var59 = this.dashY[var66 + 1] - var28;
            var18 = Math.hypot(var20, var59);
            if (!(var18 < 0.001)) {
               double var30 = var20 / var18;
               var59 /= var18;
               var20 = -var59;
               double var22 = 0.0;

               while (var22 < var18) {
                  double var24 = Math.min(var18, var22 + 0.5);
                  if (((var54 + (var22 + var24) / 2 - var2 + this.odoDist) % 9.0 + 9.0) % 9.0 < 3.0) {
                     if (this.nVerts + 4 > this.maxVerts || this.nIdx + 6 > this.maxIdx) {
                        return;
                     }

                     double var32 = var26 + var30 * var22;
                     double var36 = var28 + var59 * var22;
                     double var34 = var26 + var30 * var24;
                     var22 = var28 + var59 * var24;
                     double var38 = Math.hypot(var32, var36);
                     float var43 = Color.alpha(var6) / 255.0F * (1.0F - RangesKt.coerceIn((float)((var38 - 0.7 * var8) / (0.3 * var8)), 0.0F, 1.0F));
                     int var68 = this.packColor(var42 * var43, var41 * var43, var40 * var43, var43);
                     int var69 = this.nVerts;
                     this.putVertex((float)(var36 - var30 * var4), var7, (float)(-(var32 - var20 * var4)), var68);
                     this.putVertex((float)(var30 * var4 + var36), var7, (float)(-(var32 + var20 * var4)), var68);
                     this.putVertex((float)(var22 - var30 * var4), var7, (float)(-(var34 - var20 * var4)), var68);
                     this.putVertex((float)(var22 + var30 * var4), var7, (float)(-(var34 + var20 * var4)), var68);
                     this.putTri(var69, var69 + 2, var69 + 1);
                     this.putTri(var69 + 1, var69 + 2, var69 + 3);
                  }

                  var22 = var24;
               }

               var54 += var18;
            }
         }
      }
   }

   // $VF: synthetic method
   static void polyLine$default(
      SceneView var0, int var1, double var2, double var4, int var6, float var7, double var8, boolean var10, double[] var11, int var12, Object var13
   ) {
      if ((var12 & 128) != 0) {
         var11 = null;
      }

      var0.polyLine(var1, var2, var4, var6, var7, var8, var10, var11);
   }

   private final void polyRibbon(
      int var1, double var2, double var4, int var6, float var7, double var8, double[] var10, double[] var11, double[] var12, double[] var13
   ) {
      if (var1 >= 2 && this.nVerts + var1 * 2 <= this.maxVerts && this.nIdx + (var1 - 1) * 6 <= this.maxIdx) {
         Triple var35 = Companion.linear(var6);
         float var27 = ((Number)var35.component1()).floatValue();
         float var28 = ((Number)var35.component2()).floatValue();
         float var24 = ((Number)var35.component3()).floatValue();
         int var32 = this.nVerts;

         for (int var31 = 0; var31 < var1; var31++) {
            int var34 = Math.max(0, var31 - 1);
            int var33 = Math.min(var1 - 1, var31 + 1);
            double var18 = this.mx[var33] - this.mx[var34];
            double var16 = this.my[var33] - this.my[var34];
            double var14 = RangesKt.coerceAtLeast(Math.hypot(var18, var16), 1.0E-6);
            var18 /= var14;
            double var20 = -(var16 / var14);
            var14 = Math.hypot(this.mx[var31], this.my[var31]);
            float var29 = Color.alpha(var6) / 255.0F;
            float var25 = (float)((var14 - 0.7 * var8) / (0.3 * var8));
            float var26 = 0.0F;
            float var30 = RangesKt.coerceIn(var25, 0.0F, 1.0F);
            if (var11 != null) {
               var25 = (float)var11[var31];
            } else {
               var25 = 1.0F;
            }

            label46: {
               var25 = RangesKt.coerceIn(var29 * (1.0F - var30) * var25, 0.0F, 1.0F);
               var33 = this.packColor(var27 * var25, var28 * var25, var24 * var25, var25);
               var16 = 0.0;
               if (var12 != null) {
                  Double var51 = ArraysKt.getOrNull(var12, var31);
                  if (var51 != null) {
                     var14 = var51;
                     break label46;
                  }
               }

               var14 = 0.0;
            }

            double var22 = var2 + var14;
            var14 = var16;
            if (var13 != null) {
               Double var52 = ArraysKt.getOrNull(var13, var31);
               var14 = var16;
               if (var52 != null) {
                  var14 = var52;
               }
            }

            var14 = var4 + var14;
            var16 = this.mx[var31] + var20 * var22;
            var22 = this.my[var31] + var18 * var22;
            var25 = var26;
            if (var10 != null) {
               var25 = (float)var10[var31];
            }

            var25 = var7 + var25;
            this.putVertex((float)(var22 - var18 * var14), var25, (float)(-(var16 - var20 * var14)), var33);
            this.putVertex((float)(var22 + var18 * var14), var25, (float)(-(var16 + var20 * var14)), var33);
         }

         for (int var36 = 0; var36 < var1 - 1; var36++) {
            int var49 = var36 * 2 + var32;
            this.putTri(var49, var49 + 2, var49 + 1);
            this.putTri(var49 + 1, var49 + 2, var49 + 3);
         }
      }
   }

   // $VF: synthetic method
   static void polyRibbon$default(
      SceneView var0,
      int var1,
      double var2,
      double var4,
      int var6,
      float var7,
      double var8,
      double[] var10,
      double[] var11,
      double[] var12,
      double[] var13,
      int var14,
      Object var15
   ) {
      if ((var14 & 64) != 0) {
         var10 = null;
      }

      if ((var14 & 128) != 0) {
         var11 = null;
      }

      if ((var14 & 256) != 0) {
         var12 = null;
      }

      if ((var14 & 512) != 0) {
         var13 = null;
      }

      var0.polyRibbon(var1, var2, var4, var6, var7, var8, var10, var11, var12, var13);
   }

   private final void putTri(int var1, int var2, int var3) {
      this.getIbuf().putInt(var1);
      this.getIbuf().putInt(var2);
      this.getIbuf().putInt(var3);
      this.nIdx += 3;
   }

   private final void putVertex(float var1, float var2, float var3, int var4) {
      this.getVbuf().putFloat(var1);
      this.getVbuf().putFloat(var2);
      this.getVbuf().putFloat(var3);
      this.getVbuf().putInt(var4);
      this.nVerts++;
   }

   private final int quad(float var1, MaterialInstance var2, int var3, boolean var4) {
      Float var12 = 400.0F;
      com.google.android.filament.VertexBuffer.Builder var10 = new com.google.android.filament.VertexBuffer.Builder()
         .bufferCount(1)
         .vertexCount(4)
         .attribute(VertexAttribute.POSITION, 0, AttributeType.FLOAT3, 0, 12);
      Engine var9 = this.engine;
      Engine var8 = var9;
      if (var9 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("engine");
         var8 = null;
      }

      VertexBuffer var26 = var10.build(var8);
      Intrinsics.checkNotNullExpressionValue(var26, "build(...)");
      ByteBuffer var11 = ByteBuffer.allocateDirect(48).order(ByteOrder.nativeOrder());

      for (Pair var21 : CollectionsKt.listOf(
         new Pair[]{TuplesKt.to(-400.0F, var12), TuplesKt.to(var12, var12), TuplesKt.to(var12, -400.0F), TuplesKt.to(-400.0F, -400.0F)}
      )) {
         float var5 = ((Number)var21.component1()).floatValue();
         float var6 = ((Number)var21.component2()).floatValue();
         var11.putFloat(var5);
         var11.putFloat(var1);
         var11.putFloat(var6);
      }

      ((Buffer)var11).flip();
      var9 = this.engine;
      var8 = var9;
      if (var9 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("engine");
         var8 = null;
      }

      var26.setBufferAt(var8, 0, var11);
      com.google.android.filament.IndexBuffer.Builder var27 = new com.google.android.filament.IndexBuffer.Builder().indexCount(6).bufferType(IndexType.USHORT);
      var9 = this.engine;
      var8 = var9;
      if (var9 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("engine");
         var8 = null;
      }

      IndexBuffer var28 = var27.build(var8);
      Intrinsics.checkNotNullExpressionValue(var28, "build(...)");
      ByteBuffer var29 = ByteBuffer.allocateDirect(12).order(ByteOrder.nativeOrder());

      for (int var7 = 0; var7 < 6; var7++) {
         var29.putShort(new short[]{0, 2, 1, 0, 3, 2}[var7]);
      }

      ((Buffer)var29).flip();
      var9 = this.engine;
      var8 = var9;
      if (var9 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("engine");
         var8 = null;
      }

      var28.setBuffer(var8, var29);
      this.groundBuffers.add(TuplesKt.to(var26, var28));
      int var15 = EntityManager.get().create();
      com.google.android.filament.RenderableManager.Builder var25 = new com.google.android.filament.RenderableManager.Builder(1)
         .boundingBox(new Box(0.0F, var1, 0.0F, 400.0F, 0.1F, 400.0F))
         .geometry(0, PrimitiveType.TRIANGLES, var26, var28, 0, 6)
         .material(0, var2)
         .castShadows(false)
         .receiveShadows(var4)
         .culling(false)
         .priority(var3);
      var8 = this.engine;
      Engine var13 = var8;
      if (var8 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("engine");
         var13 = null;
      }

      var25.build(var13, var15);
      Scene var14 = this.scene;
      if (var14 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("scene");
         var14 = null;
      }

      var14.addEntity(var15);
      return var15;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private final ByteBuffer readAsset(String var1) {
      var var10 = this.getContext().getAssets().open(var1);

      ByteBuffer var13;
      try {
         InputStream var11 = var10;
         Intrinsics.checkNotNull(var11);
         byte[] var12 = ByteStreamsKt.readBytes(var11);
         this.readAssetSize = var12.length;
         var13 = ByteBuffer.allocateDirect(var12.length).order(ByteOrder.nativeOrder()).put(var12);
         ((Buffer)var13).flip();
         Intrinsics.checkNotNullExpressionValue(var13, "also(...)");
      } catch (Throwable var9) {
         Throwable var2 = var9;

         try {
            throw var2;
         } finally {
            CloseableKt.closeFinally(var10, var9);
         }
      }

      CloseableKt.closeFinally(var10, null);
      return var13;
   }

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

      byte var17;
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
      byte var65;
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
            boolean var13;
            if (!this.night && !this.alwaysDark) {
               var13 = 0;
            } else {
               var13 = 1;
            }

            int var14;
            if (var13) {
               var14 = Color.rgb(214, 218, 224);
            } else {
               var14 = -1;
            }

            short var15;
            short var16;
            if (var13) {
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
                     boolean var66;
                     if ((var18 <= 0 || var41 != var38) && (var18 >= 0 || var41 != var36)) {
                        var66 = 0;
                     } else {
                        var66 = 1;
                     }

                     boolean var94;
                     if (!var27 || var41 != var36 || var129 == null || var16 != 2 && var16 != 3 || var129.getYellow() && !var129.getDouble() && var16 == 2) {
                        var94 = 0;
                     } else {
                        var94 = 1;
                     }

                     if (var66) {
                        var66 = Color.rgb(0, 176, 220);
                     } else if (!var94 && (var129 == null || var16 == 0 || !var129.getYellow())) {
                        var66 = var14;
                     } else {
                        var66 = var24;
                     }

                     if (var41 == var37 && var33 == LaneTracks.PathState.ENGAGED) {
                        var66 = LaneTracks.Companion.effortColor(var66, var124.tracks.getEffort());
                     }

                     short var19;
                     if (var16 == 0) {
                        var19 = 150;
                     } else {
                        var19 = 255;
                     }

                     int var21 = RangesKt.coerceIn((int)(var19 * var41.getAlpha()), 0, 255);
                     int var25 = Companion.withAlpha(var66, var21);
                     boolean var68;
                     if (var94 || var129 != null && var16 != 0 && var129.getDouble()) {
                        var68 = 1;
                     } else {
                        var68 = 0;
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
                     var94 = 1;
                     var68 = 0;

                     for (float[] var132 = var39; var8 <= Math.min(110.0F, ArraysKt.last(var132)) && var68 < 230; var8 += 0.5F) {
                        while (var94 < var132.length - 1 && var132[var94] < var8) {
                           var94++;
                        }

                        float var9 = RangesKt.coerceIn((var8 - var132[var94 - 1]) / Math.max(0.01F, var132[var94] - var132[var94 - 1]), 0.0F, 1.0F);
                        var44[var68] = var8;
                        var154[var68] = var42[var94 - 1] + (var42[var94] - var42[var94 - 1]) * var9;
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
                           var42 = new float[var68];

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

         for (var var142_it : var142) {

            var142 = var142_it;
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
               byte var74;
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

   private static final float rebuildRibbons$lambda$100(float[] var0, int var1) {
      return 1.0F - RangesKt.coerceIn(var0[var1] / 70.0F, 0.0F, 1.0F);
   }

   private static final float[] rebuildRibbons$lambda$101(SceneView var0, LaneTracks.Line var1) {
      Intrinsics.checkNotNullParameter(var1, "l");
      return var0.shownY(var1);
   }

   private static final float rebuildRibbons$lambda$103(float[] var0, int var1) {
      return 1.0F - RangesKt.coerceIn(var0[var1] / 60.0F, 0.0F, 1.0F);
   }

   private static final float rebuildRibbons$lambda$104(int var0, int var1) {
      return 1.0F - (float)var1 / Math.max(1, var0 - 1) * 0.75F;
   }

   private static final float rebuildRibbons$lambda$105(float[] var0, float var1, SceneView var2, float var3, int var4) {
      return (1.0F - var0[var4] / var1 * 0.75F) * PathPulse.INSTANCE.alpha(var0[var4], var2.pulsePhase, var3);
   }

   private static final float rebuildRibbons$lambda$95(float[] var0, int var1) {
      return 1.0F - RangesKt.coerceIn((var0[var1] - 85.0F) / 25.0F, 0.0F, 1.0F);
   }

   private final void ribbon(
      float[] var1, float[] var2, float[] var3, double var4, int var6, int var7, int var8, float var9, Function1<? super Integer, Float> var10
   ) {
      int var20 = Math.min(var8, Math.min(var1.length, var2.length) - 1);
      if (var20 - var7 >= 1) {
         var8 = var20 - var7 + 1;
         if (this.nVerts + var8 * 2 <= this.maxVerts && this.nIdx + (var8 - 1) * 6 <= this.maxIdx) {
            Triple var22 = Companion.linear(var6);
            float var14 = ((Number)var22.component1()).floatValue();
            float var13 = ((Number)var22.component2()).floatValue();
            float var11 = ((Number)var22.component3()).floatValue();
            int var19 = this.nVerts;
            if (var7 <= var20) {
               while (true) {
                  float var12;
                  float var15;
                  float var16;
                  label40: {
                     var16 = var1[var7];
                     var15 = var2[var7];
                     if (var3 != null) {
                        Float var30 = ArraysKt.getOrNull(var3, var7);
                        if (var30 != null) {
                           var12 = var30;
                           break label40;
                        }
                     }

                     var12 = 0.0F;
                  }

                  float var17 = var12 + var9;
                  float var18 = Color.alpha(var6) / 255.0F;
                  if (var10 != null) {
                     var12 = ((Number)var10.invoke(var7)).floatValue();
                  } else {
                     var12 = 1.0F;
                  }

                  var12 = RangesKt.coerceIn(var18 * var12, 0.0F, 1.0F);
                  int var21 = this.packColor(var14 * var12, var13 * var12, var11 * var12, var12);
                  this.putVertex((float)(var15 - var4), var17, -var16, var21);
                  this.putVertex((float)(var15 + var4), var17, -var16, var21);
                  if (var7 == var20) {
                     break;
                  }

                  var7++;
               }
            }

            for (int var23 = 0; var23 < var8 - 1; var23++) {
               var20 = var23 * 2 + var19;
               int var29 = var20 + 1;
               var7 = var20 + 2;
               this.putTri(var20, var7, var29);
               this.putTri(var29, var7, var20 + 3);
            }
         }
      }
   }

   // $VF: synthetic method
   static void ribbon$default(
      SceneView var0, float[] var1, float[] var2, float[] var3, double var4, int var6, int var7, int var8, float var9, Function1 var10, int var11, Object var12
   ) {
      if ((var11 & 32) != 0) {
         var7 = 0;
      }

      if ((var11 & 64) != 0) {
         var8 = var1.length - 1;
      }

      if ((var11 & 128) != 0) {
         var9 = 0.02F;
      }

      if ((var11 & 256) != 0) {
         var10 = null;
      }

      var0.ribbon(var1, var2, var3, var4, var6, var7, var8, var9, var10);
   }

   private final void setProjection() {
      if (this.viewW != 0 && this.viewH != 0) {
         double var1 = (double)this.viewW / this.viewH;
         if (var1 < 1.6) {
            var1 = Math.pow(1.6 / var1, 0.6);
         } else {
            var1 = 1.0;
         }

         var1 = Math.tan(Math.toRadians(this.fovDeg / 2)) * 0.5 * var1;
         double var3 = this.viewW * var1 / this.viewH;
         Camera var12 = this.camera;
         Camera var11 = var12;
         if (var12 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("camera");
            var11 = null;
         }

         com.google.android.filament.Camera.Projection var15 = com.google.android.filament.Camera.Projection.PERSPECTIVE;
         double var5 = -var3;
         double var7 = -var1;
         double var9 = 1;
         var11.setProjection(var15, var5, var3, (this.horizonShift + var9) * var7, var1 * (var9 - this.horizonShift), 0.5, 400.0);
      }
   }

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

   // $VF: synthetic method
   static void setupCamera$default(SceneView var0, Telemetry var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = null;
      }

      var0.setupCamera(var1);
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   private final void setupFilament() {
      this.engine = Engine.create();
      Engine var6 = this.engine;
      Object var7 = null;
      Engine var5 = var6;
      if (var6 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("engine");
         var5 = null;
      }

      this.renderer = var5.createRenderer();
      var6 = this.engine;
      var5 = var6;
      if (var6 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("engine");
         var5 = null;
      }

      this.scene = var5.createScene();
      var6 = this.engine;
      var5 = var6;
      if (var6 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("engine");
         var5 = null;
      }

      this.view = var5.createView();
      var6 = this.engine;
      var5 = var6;
      if (var6 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("engine");
         var5 = null;
      }

      this.camera = var5.createCamera(EntityManager.get().create());
      View var51 = this.view;
      View var21 = var51;
      if (var51 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("view");
         var21 = null;
      }

      Scene var8 = this.scene;
      Scene var52 = var8;
      if (var8 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("scene");
         var52 = null;
      }

      var21.setScene(var52);
      View var53 = this.view;
      View var22 = var53;
      if (var53 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("view");
         var22 = null;
      }

      Camera var80 = this.camera;
      Camera var54 = var80;
      if (var80 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("camera");
         var54 = null;
      }

      var22.setCamera(var54);
      Camera var55 = this.camera;
      Camera var23 = var55;
      if (var55 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("camera");
         var23 = null;
      }

      var23.setExposure(1.0F);
      int var1 = 0;
      this.applyGrading(false);
      View var56 = this.view;
      View var24 = var56;
      if (var56 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("view");
         var24 = null;
      }

      var24.setAntiAliasing(AntiAliasing.NONE);
      View var57 = this.view;
      View var25 = var57;
      if (var57 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("view");
         var25 = null;
      }

      MultiSampleAntiAliasingOptions var58 = new MultiSampleAntiAliasingOptions();
      var58.enabled = true;
      var58.sampleCount = 4;
      var25.setMultiSampleAntiAliasingOptions(var58);
      View var59 = this.view;
      View var26 = var59;
      if (var59 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("view");
         var26 = null;
      }

      var26.setShadowType(ShadowType.PCF);
      View var60 = this.view;
      View var27 = var60;
      if (var60 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("view");
         var27 = null;
      }

      BloomOptions var61 = new BloomOptions();
      var61.enabled = true;
      var61.strength = 0.08F;
      var61.threshold = true;
      var27.setBloomOptions(var61);
      View var62 = this.view;
      View var28 = var62;
      if (var62 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("view");
         var28 = null;
      }

      DynamicResolutionOptions var63 = new DynamicResolutionOptions();
      var63.enabled = false;
      var28.setDynamicResolutionOptions(var63);
      this.sun = EntityManager.get().create();
      com.google.android.filament.LightManager.Builder var64 = new com.google.android.filament.LightManager.Builder(
            com.google.android.filament.LightManager.Type.DIRECTIONAL
         )
         .color(1.0F, 0.98F, 0.95F)
         .intensity(2.6F)
         .direction(-0.35F, -1.0F, -0.45F)
         .castShadows(true);
      ShadowOptions var29 = new ShadowOptions();
      var29.mapSize = 2048;
      var29.shadowFar = 120.0F;
      var29.shadowNearHint = 1.0F;
      var29.shadowFarHint = 120.0F;
      com.google.android.filament.LightManager.Builder var81 = var64.shadowOptions(var29);
      var6 = this.engine;
      var5 = var6;
      if (var6 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("engine");
         var5 = null;
      }

      var81.build(var5, this.sun);
      Scene var66 = this.scene;
      Scene var31 = var66;
      if (var66 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("scene");
         var31 = null;
      }

      var31.addEntity(this.sun);

      label209: {
         try {
            var67 = KTX1Loader.INSTANCE;
            var5 = this.engine;
         } catch (Exception var15) {
            Log.w("StarView/Scene", "no IBL: " + var15);
            break label209;
         }

         if (var5 == null) {
            try {
               Intrinsics.throwUninitializedPropertyAccessException("engine");
            } catch (Exception var14) {
               Log.w("StarView/Scene", "no IBL: " + var14);
               break label209;
            }

            var5 = null;
         }

         try {
            var68 = KTX1Loader.createIndirectLight$default(var67, var5, this.readAsset("ibl/day_ibl.ktx"), null, 4, null).getIndirectLight();
         } catch (Exception var13) {
            Log.w("StarView/Scene", "no IBL: " + var13);
            break label209;
         }

         IndirectLight var34;
         if (var68 != null) {
            try {
               var68.setIntensity(1.0F);
               var8 = this.scene;
            } catch (Exception var12) {
               Log.w("StarView/Scene", "no IBL: " + var12);
               break label209;
            }

            Scene var33 = var8;
            if (var8 == null) {
               try {
                  Intrinsics.throwUninitializedPropertyAccessException("scene");
               } catch (Exception var11) {
                  Log.w("StarView/Scene", "no IBL: " + var11);
                  break label209;
               }

               var33 = null;
            }

            try {
               var33.setIndirectLight(var68);
            } catch (Exception var10) {
               Log.w("StarView/Scene", "no IBL: " + var10);
               break label209;
            }

            var34 = var68;
         } else {
            var34 = null;
         }

         try {
            this.ibl = var34;
         } catch (Exception var9) {
            Log.w("StarView/Scene", "no IBL: " + var9);
         }
      }

      com.google.android.filament.Material.Builder var83 = new com.google.android.filament.Material.Builder()
         .payload(this.readAsset("materials/ribbon.filamat"), this.readAssetSize);
      var6 = this.engine;
      var5 = var6;
      if (var6 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("engine");
         var5 = null;
      }

      this.ribbonMaterial = var83.build(var5);
      com.google.android.filament.Material.Builder var84 = new com.google.android.filament.Material.Builder()
         .payload(this.readAsset("materials/ground.filamat"), this.readAssetSize);
      var6 = this.engine;
      var5 = var6;
      if (var6 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("engine");
         var5 = null;
      }

      this.groundMaterial = var84.build(var5);
      Material var71 = this.groundMaterial;
      Material var37 = var71;
      if (var71 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("groundMaterial");
         var37 = null;
      }

      this.groundMi = var37.createInstance();
      com.google.android.filament.Material.Builder var85 = new com.google.android.filament.Material.Builder()
         .payload(this.readAsset("materials/shadow.filamat"), this.readAssetSize);
      var6 = this.engine;
      var5 = var6;
      if (var6 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("engine");
         var5 = null;
      }

      this.shadowMaterial = var85.build(var5);
      Material var73 = this.shadowMaterial;
      Material var39 = var73;
      if (var73 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("shadowMaterial");
         var39 = null;
      }

      MaterialInstance var40 = var39.createInstance();
      Intrinsics.checkNotNullExpressionValue(var40, "createInstance(...)");
      var40.setParameter("color", 0.0F, 0.0F, 0.0F, 0.35F);
      this.shadowMi = var40;
      this.buildGround();
      this.buildLayout();
      this.buildRibbonMesh();
      var6 = this.engine;
      var5 = var6;
      if (var6 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("engine");
         var5 = null;
      }

      Engine var86 = this.engine;
      var6 = var86;
      if (var86 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("engine");
         var6 = null;
      }

      this.assetLoader = new AssetLoader(var5, new UbershaderProvider(var6), EntityManager.get());
      var6 = this.engine;
      var5 = var6;
      if (var6 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("engine");
         var5 = null;
      }

      this.resourceLoader = new ResourceLoader(var5);
      SceneView.Car var43 = this.loadCar("ego_sienna.glb");
      if (var43 != null) {
         var43.show(true);
      } else {
         var43 = null;
      }

      this.ego = var43;
      String[] var87 = this.genericKinds;

      for (int var3 = var87.length; var1 < var3; var1++) {
         String var44 = var87[var1];
         int var2 = 0;

         for (int var4 = this.poolPerKind; var2 < var4; var2++) {
            SceneView.Car var77 = this.loadCar(var44);
            if (var77 != null) {
               var77.grey();
               this.genericPool.add(var77);
            }
         }
      }

      for (Pair var88 : this.objectKinds) {
         String var78 = (String)var88.component1();
         int var17 = ((Number)var88.component2()).intValue();

         for (int var16 = 0; var16 < var17; var16++) {
            SceneView.Car var89 = this.loadCar(var78);
            if (var89 != null) {
               var89.grey();
               this.genericPool.add(var89);
            }
         }
      }

      this.uiHelper = new UiHelper(ContextErrorPolicy.DONT_CHECK);
      UiHelper var79 = this.uiHelper;
      UiHelper var46 = var79;
      if (var79 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("uiHelper");
         var46 = null;
      }

      var46.setRenderCallback(new RendererCallback() {

         @Override
         public void onDetachedFromSurface() {
            DisplayHelper var2 = SceneView.this.displayHelper;
            DisplayHelper var1x = var2;
            if (var2 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("displayHelper");
               var1x = null;
            }

            var1x.detach();
            SwapChain var4x = SceneView.this.swapChain;
            if (var4x != null) {
               SceneView var3 = SceneView.this;
               Engine var7x = var3.engine;
               Engine var5x = var7x;
               if (var7x == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("engine");
                  var5x = null;
               }

               var5x.destroySwapChain(var4x);
               Engine var8x = var3.engine;
               Engine var6x = var8x;
               if (var8x == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("engine");
                  var6x = null;
               }

               var6x.flushAndWait();
               var3.swapChain = null;
            }
         }

         @Override
         public void onNativeWindowChanged(Surface var1) {
            Intrinsics.checkNotNullParameter(var1, "surface");
            SwapChain var5x = SceneView.this.swapChain;
            Object var3 = null;
            if (var5x != null) {
               Engine var4x = SceneView.this.engine;
               Engine var2 = var4x;
               if (var4x == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("engine");
                  var2 = null;
               }

               var2.destroySwapChain(var5x);
            }

            SceneView var11 = SceneView.this;
            Engine var10 = SceneView.this.engine;
            Engine var7x = var10;
            if (var10 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("engine");
               var7x = null;
            }

            var11.swapChain = var7x.createSwapChain(var1);
            DisplayHelper var8x = SceneView.this.displayHelper;
            DisplayHelper var6x = var8x;
            if (var8x == null) {
               Intrinsics.throwUninitializedPropertyAccessException("displayHelper");
               var6x = null;
            }

            Renderer var9 = SceneView.this.renderer;
            if (var9 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("renderer");
               var9 = (Renderer)var3;
            }

            var6x.attach(var9, SceneView.this.getDisplay());
         }

         @Override
         public void onResized(int var1, int var2) {
            SceneView.this.viewW = var1;
            SceneView.this.viewH = var2;
            View var4x = SceneView.this.view;
            View var3 = var4x;
            if (var4x == null) {
               Intrinsics.throwUninitializedPropertyAccessException("view");
               var3 = null;
            }

            var3.setViewport(new Viewport(0, 0, var1, var2));
            SceneView.this.setProjection();
         }
      });
      this.displayHelper = new DisplayHelper(this.getContext());
      UiHelper var47 = this.uiHelper;
      if (var47 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("uiHelper");
         var47 = (UiHelper)var7;
      }

      var47.attachTo(this);
      this.ready = true;
      this.applyPalette();
   }

   private final double shapeY(double var1) {
      double var3;
      double var5;
      if (var1 >= 0.0) {
         var5 = this.shapeC[0] + this.shapeC[1] * var1;
         var3 = this.shapeC[2] * var1;
      } else {
         var5 = this.shapeC[0];
         var3 = this.shapeC[1];
      }

      return var5 + var3 * var1;
   }

   private final float[] shownY(LaneTracks.Line var1) {
      float[] var6 = var1.getX();
      float[] var8 = var1.getY();
      if (!this.shapeOk) {
         return var8;
      } else {
         ArrayList var7 = new ArrayList();
         int var4 = 0;

         for (int var5 = var6.length; var4 < var5; var4++) {
            double var2 = var6[var4];
            if (!(var2 < 3.0)) {
               if (var2 > 40.0) {
                  break;
               }

               var7.add(var8[var4] - this.shapeY(var2));
            }
         }

         if (var7.size() < 3) {
            return var8;
         } else {
            CollectionsKt.sort(var7);
            F var9 = (F)var7.get(var7.size() / 2);
            Intrinsics.checkNotNullExpressionValue(var9, "get(...)");
            double var11 = ((Number)var9).doubleValue();
            int var13 = var6.length;
            var9 = new float[var13];

            for (int var12 = 0; var12 < var13; var12++) {
               ((Object[])var9)[var12] = (float)(this.shapeY(var6[var12]) + var11);
            }

            return (float[])var9;
         }
      }
   }

   private final SceneView.Car slotFor(long var1, String var3, long var4) {
      Iterator var7 = this.genericPool.iterator();
      Intrinsics.checkNotNullExpressionValue(var7, "iterator(...)");

      while (var7.hasNext()) {
         SceneView.Car var8 = (SceneView.Car)var7.next();
         Intrinsics.checkNotNullExpressionValue(var8, "next(...)");
         var8 = var8;
         if (var8.getId() == var1) {
            if (Intrinsics.areEqual(var8.getKind(), var3)) {
               var8.setLastUsed(var4);
               return var8;
            }

            var8.show(false);
            var8.setId(Long.MIN_VALUE);
         }
      }

      boolean var6;
      if (!StringsKt.startsWith$default(var3, "car_", false, 2, null) && !Intrinsics.areEqual(var3, "truck.glb") && !Intrinsics.areEqual(var3, "bus.glb")) {
         var6 = false;
      } else {
         var6 = true;
      }

      Object var15 = null;
      Iterator var9 = this.genericPool.iterator();
      Intrinsics.checkNotNullExpressionValue(var9, "iterator(...)");

      do {
         var11 = (SceneView.Car)var15;
         if (!var9.hasNext()) {
            break;
         }

         Object var12 = var9.next();
         Intrinsics.checkNotNullExpressionValue(var12, "next(...)");
         var11 = (SceneView.Car)var12;
      } while (var11.getId() != Long.MIN_VALUE || !Intrinsics.areEqual(var11.getKind(), var3));

      var15 = var11;
      if (var11 == null) {
         var15 = var11;
         if (var6) {
            var9 = this.genericPool.iterator();
            Intrinsics.checkNotNullExpressionValue(var9, "iterator(...)");

            do {
               var15 = var11;
               if (!var9.hasNext()) {
                  break;
               }

               var15 = var9.next();
               Intrinsics.checkNotNullExpressionValue(var15, "next(...)");
               var15 = (SceneView.Car)var15;
            } while (
               ((SceneView.Car)var15).getId() != Long.MIN_VALUE || !StringsKt.startsWith$default(((SceneView.Car)var15).getKind(), "car_", false, 2, null)
            );
         }
      }

      SceneView.Car var19 = (SceneView.Car)var15;
      if (var15 == null) {
         SceneView.Car var13 = null;
         Iterator var10 = this.genericPool.iterator();
         Intrinsics.checkNotNullExpressionValue(var10, "iterator(...)");

         while (var10.hasNext()) {
            Object var20 = var10.next();
            Intrinsics.checkNotNullExpressionValue(var20, "next(...)");
            var20 = (SceneView.Car)var20;
            if (!Double.isNaN(((SceneView.Car)var20).getWx())
               && var4 - ((SceneView.Car)var20).getLastUsed() > 700L
               && (
                  Intrinsics.areEqual(((SceneView.Car)var20).getKind(), var3)
                     || var6 && StringsKt.startsWith$default(((SceneView.Car)var20).getKind(), "car_", false, 2, null)
               )
               && (var13 == null || ((SceneView.Car)var20).getLastUsed() < var13.getLastUsed())) {
               var13 = (SceneView.Car)var20;
            }
         }

         var19 = (SceneView.Car)var15;
         if (var13 != null) {
            var13.show(false);
            var19 = var13;
         }
      }

      if (var19 != null) {
         var19.setId(var1);
         var19.setLastUsed(var4);
         var19.setX(Double.NaN);
         var19.setWx(Double.NaN);
         var19.setFirstShownMs(var4);
      }

      return var19;
   }

   private final void smooth() {
      int[] var11 = new int[5];
      int var3 = 0;

      for (int var8 = this.MR; var3 < var8; var3++) {
         for (int var4 = 0; var4 < 80; var4++) {
            Arrays.fill(var11, 0);
            int var2 = -1;

            while (true) {
               int var7 = 1;
               if (var2 >= 2) {
                  int var9 = var3 * 80 + var4;
                  byte var10 = this.memGrp[var9];
                  int var15 = -1;
                  var2 = 0;

                  while (var2 < 5) {
                     int var17;
                     var17 = var15;
                     label81:
                     if (var11[var2] >= 5) {
                        if (var15 >= 0) {
                           var17 = var15;
                           if (var11[var2] <= var11[var15]) {
                              break label81;
                           }
                        }

                        var17 = var2;
                     }

                     var2++;
                     var15 = var17;
                  }

                  byte[] var12 = this.smoothTmp;
                  boolean var14;
                  if (var15 >= 0 && var15 < 4) {
                     var14 = (boolean)var7;
                  } else {
                     var14 = false;
                  }

                  byte var1;
                  if (var14) {
                     var1 = (byte)var15;
                  } else if (var15 == 4 && (var10 & 255) <= 3 && var11[4] == 8) {
                     var1 = -1;
                  } else {
                     var1 = this.memGrp[var9];
                  }

                  var12[var9] = var1;
                  break;
               }

               for (int var5 = -1; var5 < 2; var5++) {
                  if (var2 != 0 || var5 != 0) {
                     int var6 = var3 + var2;
                     var7 = var4 + var5;
                     if (var6 >= 0 && var6 < this.MR && var7 >= 0 && var7 < 80) {
                        var6 = this.memGrp[var6 * 80 + var7] & 255;
                        if (var6 > 3) {
                           var6 = 4;
                        }

                        var11[var6]++;
                     }
                  }
               }

               var2++;
            }
         }
      }

      System.arraycopy(this.smoothTmp, 0, this.memGrp, 0, this.memGrp.length);
   }

   private final void startRendering() {
      if (!this.running && this.engine != null) {
         this.running = true;
         Choreographer.getInstance().postFrameCallback(this.frameCallback);
      }
   }

   private final void stopRendering() {
      this.running = false;
      Choreographer.getInstance().removeFrameCallback(this.frameCallback);
   }

   private final Pair<Double, Double> toWorld(double var1, double var3) {
      double var5 = Math.cos(this.odoYaw);
      double var7 = Math.sin(this.odoYaw);
      var3 = -var3;
      return new Pair<>(this.odoX + var5 * var1 - var7 * var3, this.odoY + var7 * var1 + var5 * var3);
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   private final void updateLayout(long var1, Telemetry var3) {
      double[] var23 = Odometry.INSTANCE.at(var1);
      this.odoX = var23[0];
      boolean var19 = true;
      this.odoY = var23[1];
      this.odoYaw = var23[2];
      this.odoDist = var23[3];
      this.odoNs = var1;
      this.yawSrc = Odometry.INSTANCE.getYawSrc();

      while (true) {
         OsmRoads.Fix var24;
         try {
            var24 = OsmRoads.INSTANCE.getFixes().poll();
         } catch (Exception var31) {
            Log.e("StarView/Scene", "map align", var31);
            this.mapAlign.reset();
            this.mapStatus = StringsKt.take("map error: " + var31, 90);
            break;
         }

         if (var24 == null) {
            break;
         }

         label212: {
            try {
               if (var24.getMonoNs() != 0L) {
                  var58 = Odometry.INSTANCE.monoToLocal(var24.getMonoNs());
                  break label212;
               }
            } catch (Exception var34) {
               Log.e("StarView/Scene", "map align", var34);
               this.mapAlign.reset();
               this.mapStatus = StringsKt.take("map error: " + var34, 90);
               break;
            }

            var58 = null;
         }

         double var4;
         long var21;
         if (var58 != null) {
            try {
               var21 = var58;
               var4 = this.gpsLag;
            } catch (Exception var30) {
               Log.e("StarView/Scene", "map align", var30);
               this.mapAlign.reset();
               this.mapStatus = StringsKt.take("map error: " + var30, 90);
               break;
            }
         } else {
            boolean var18;
            label203: {
               label202: {
                  try {
                     var21 = var24.getTNs();
                     var4 = var24.getAgeS();
                     if (!Double.isInfinite(var4) && !Double.isNaN(var4)) {
                        break label202;
                     }
                  } catch (Exception var33) {
                     Log.e("StarView/Scene", "map align", var33);
                     this.mapAlign.reset();
                     this.mapStatus = StringsKt.take("map error: " + var33, 90);
                     break;
                  }

                  var18 = false;
                  break label203;
               }

               var18 = true;
            }

            if (var18) {
               try {
                  var4 = var24.getAgeS();
               } catch (Exception var29) {
                  Log.e("StarView/Scene", "map align", var29);
                  this.mapAlign.reset();
                  this.mapStatus = StringsKt.take("map error: " + var29, 90);
                  break;
               }
            } else {
               var4 = 0.0;
            }

            try {
               var4 += this.gpsLag;
            } catch (Exception var28) {
               Log.e("StarView/Scene", "map align", var28);
               this.mapAlign.reset();
               this.mapStatus = StringsKt.take("map error: " + var28, 90);
               break;
            }
         }

         var21 -= (long)(var4 * 1.0E9);

         boolean var52;
         label192: {
            label191: {
               try {
                  double[] var25 = this.odoAt(var21);
                  double[] var26 = this.odoAt(var21 - 100000000L);
                  var23 = this.odoAt(100000000L + var21);
                  this.mapAlign.add(var25[1], var25[2], var25[3], var24, var1, (var23[1] - var26[1]) / 0.2, (var23[2] - var26[2]) / 0.2);
                  var4 = this.mapAlign.getLagResid();
                  if (!Double.isInfinite(var4) && !Double.isNaN(var4)) {
                     break label191;
                  }
               } catch (Exception var32) {
                  Log.e("StarView/Scene", "map align", var32);
                  this.mapAlign.reset();
                  this.mapStatus = StringsKt.take("map error: " + var32, 90);
                  break;
               }

               var52 = false;
               break label192;
            }

            var52 = true;
         }

         if (var52) {
            try {
               if (var3.getVEgo() > 6.0F) {
                  this.gpsLag = RangesKt.coerceIn(this.gpsLag + 0.02 * var4, 0.0, 0.8);
               }
            } catch (Exception var27) {
               Log.e("StarView/Scene", "map align", var27);
               this.mapAlign.reset();
               this.mapStatus = StringsKt.take("map error: " + var27, 90);
               break;
            }
         }
      }

      if (this.mapAlign.getOk()) {
         boolean var53;
         if (this.mapAlign.getGuess().length() == 0) {
            var53 = true;
         } else {
            var53 = false;
         }

         if (var53) {
            GpsBridge var60 = GpsBridge.INSTANCE;
            double var8 = this.mapAlign.getTh();
            double var12 = this.mapAlign.getTx();
            double var6 = this.mapAlign.getTy();
            double var45 = OsmRoads.INSTANCE.getLat0();
            double var10 = OsmRoads.INSTANCE.getLon0();
            boolean var20;
            if (this.mapAlign.getRejectSince() != 0L) {
               var20 = true;
            } else {
               var20 = false;
            }

            var60.setFit(var8, var12, var6, var45, var10, var20);
         }
      }

      this.collectParked(var1, var3);
      LayoutSeg var35 = this.layout;
      if (var35 != null && var35.getEnabled()) {
         LayoutSeg.Grid var37 = var35.getGrid();
         boolean var56;
         if (!this.night && !this.alwaysDark) {
            var56 = false;
         } else {
            var56 = true;
         }

         if (var37 != null && var37 != this.layoutGrid) {
            this.layoutGrid = var37;
            SceneView.Snap var62 = this.snaps.lastOrNull();
            boolean var54;
            if (var62 != null && this.odoDist - var62.getDist() < 1.0) {
               var54 = var19;
            } else {
               var54 = false;
            }

            label224: {
               if (var54) {
                  Intrinsics.checkNotNull(var62);
                  if (var1 - var62.getTNs() <= 2000000000L) {
                     break label224;
                  }
               }

               this.snaps.addLast(new SceneView.Snap(var37.getG(), this.odoX, this.odoY, this.odoYaw, var1, this.odoDist));

               while (
                  this.snaps.size() > 48
                     || !this.snaps.isEmpty()
                        && (
                           var1 - this.snaps.first().getTNs() > 10000000000L && this.odoDist - this.snaps.first().getDist() > 30.0
                              || var1 - this.snaps.first().getTNs() > 120000000000L
                        )
               ) {
                  this.snaps.removeFirst();
               }
            }

            this.combine(var1);
            this.smooth();
            this.paint(var56);
         } else if ((var56 != this.layoutDark || this.layoutHideRoad != this.layoutPaintedHide) && !this.snaps.isEmpty()) {
            this.paint(var56);
         }

         if (this.snaps.isEmpty()) {
            MaterialInstance var65 = this.layoutMi;
            MaterialInstance var41 = var65;
            if (var65 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("layoutMi");
               var41 = null;
            }

            var41.setParameter("opacity", 0.0F);
         } else {
            float var16 = (float)(var1 - this.snaps.last().getTNs()) / 1.0E9F;
            MaterialInstance var63 = this.layoutMi;
            MaterialInstance var38 = var63;
            if (var63 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("layoutMi");
               var38 = null;
            }

            var38.setParameter("opacity", 0.0F);
            double var14 = this.texX - this.odoX;
            double var47 = this.texY - this.odoY;
            double var46 = Math.cos(this.odoYaw);
            double var49 = Math.sin(this.odoYaw);
            double var50 = -var49;
            double var48 = this.texYaw - this.odoYaw;
            var16 = (float)Math.cos(var48);
            float var17 = (float)Math.sin(var48);
            float[] var64 = new float[16];
            var64[0] = var16;
            var64[2] = -var17;
            var64[5] = 1.0F;
            var64[8] = var17;
            var64[10] = var16;
            var64[12] = (float)(-(var50 * var14 + var46 * var47));
            var64[14] = (float)(-(var46 * var14 + var49 * var47));
            var64[15] = 1.0F;
            Engine var39 = this.engine;
            if (var39 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("engine");
               var39 = null;
            }

            TransformManager var40 = var39.getTransformManager();
            Intrinsics.checkNotNullExpressionValue(var40, "getTransformManager(...)");
            int var55 = var40.getInstance(this.layoutEnt);
            if (var55 == 0) {
               var40.create(this.layoutEnt);
               var55 = var40.getInstance(this.layoutEnt);
            }

            var40.setTransform(var55, var64);
         }
      } else {
         if (!this.snaps.isEmpty()) {
            this.snaps.clear();
            this.layoutGrid = null;
         }

         MaterialInstance var61 = this.layoutMi;
         MaterialInstance var36 = var61;
         if (var61 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("layoutMi");
            var36 = null;
         }

         var36.setParameter("opacity", 0.0F);
      }
   }

   private final void updateScene(long var1) {
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
         boolean var21;
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
                           var21 = (boolean)0;
                        } else {
                           Iterator var118 = var117.iterator();

                           while (true) {
                              if (!var118.hasNext()) {
                                 var21 = (boolean)0;
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
                                          var21 = (boolean)1;
                                       } else {
                                          var21 = (boolean)0;
                                       }

                                       if (!var21) {
                                          break label421;
                                       }
                                    }

                                    var21 = (boolean)1;
                                    break label422;
                                 }

                                 var21 = (boolean)0;
                              }

                              if (var21) {
                                 var21 = (boolean)1;
                                 break;
                              }
                           }
                        }

                        if (var21) {
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
                     || !StringsKt.startsWith$default(var135.getKind(), "car_", false, 2, null)
                        && !Intrinsics.areEqual(var135.getKind(), var126)
                        && !Intrinsics.areEqual(var135.getKind(), var130)) {
                     var21 = (boolean)0;
                  } else {
                     var21 = (boolean)1;
                  }

                  if (var21) {
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

               var109.shownCars = var21;
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

   private static final boolean updateScene$atPictureEdge(double var0, double var2) {
      boolean var4;
      if (Math.abs(Math.atan2(var2, var0)) > 0.9) {
         var4 = true;
      } else {
         var4 = false;
      }

      return var4;
   }

   private static final double updateScene$curbAt(ModelSnapshot var0, SceneView var1, int var2, double var3) {
      if (var0 != null) {
         boolean var5;
         if (((Object[])var0.getEdgeX()).length == 0) {
            var5 = true;
         } else {
            var5 = false;
         }

         if (!var5 && var1.curbS[var2].length == var0.getEdgeX()[0].length && (var1.curbFit[var2] || var1.edgeOk > 0.3F)) {
            double[] var6 = var1.curbS[var2];
            return updateScene$interp(var0.getEdgeX()[0], new SceneView$$ExternalSyntheticLambda9(var6), var6.length, var3);
         }
      }

      return Double.NaN;
   }

   private static final double updateScene$curbAt$lambda$33(double[] var0, int var1) {
      return var0[var1];
   }

   private static final double updateScene$edgeAtX(SceneView var0, ModelSnapshot var1, int var2, double var3) {
      if (var0.shapeOk) {
         return var0.shapeY(var3) + var0.shapeOff[var2];
      } else {
         if (var1 != null) {
            boolean var5;
            if (((Object[])var1.getEdgeX()).length == 0) {
               var5 = true;
            } else {
               var5 = false;
            }

            if (!var5 && var0.edgeF[var2].length == var1.getEdgeX()[0].length) {
               float[] var6 = var0.edgeF[var2];
               return updateScene$interp(var1.getEdgeX()[0], new SceneView$$ExternalSyntheticLambda10(var6), var6.length, var3);
            }
         }

         return Double.NaN;
      }
   }

   private static final double updateScene$edgeAtX$lambda$34(float[] var0, int var1) {
      return var0[var1];
   }

   private static final double updateScene$halfLenOf(String var0) {
      double var1;
      if (Intrinsics.areEqual(var0, "truck.glb")) {
         var1 = 7.0;
      } else if (Intrinsics.areEqual(var0, "bus.glb")) {
         var1 = 6.0;
      } else {
         var1 = 2.3;
      }

      return var1;
   }

   private static final boolean updateScene$inParking(SceneView var0, ModelSnapshot var1, double var2, double var4) {
      for (int var12 = 0; var12 < 2; var12++) {
         double var6;
         if (var12 == 0) {
            var6 = -1.0;
         } else {
            var6 = 1.0;
         }

         double var10 = updateScene$edgeAtX(var0, var1, var12, var2);
         double var8 = updateScene$curbAt(var1, var0, var12, var2);
         if (!Double.isNaN(var10) && !Double.isNaN(var8) && (var8 - var10) * var6 > 1.5 && (var4 - var10) * var6 > 0.3) {
            return true;
         }
      }

      return false;
   }

   private static final double updateScene$interp(float[] var0, Function1<? super Integer, Double> var1, int var2, double var3) {
      if (var2 < 2) {
         return Double.NaN;
      } else if (var3 <= var0[0]) {
         return ((Number)var1.invoke(0)).doubleValue();
      } else {
         for (int var5 = 1; var5 < var2; var5++) {
            if (var0[var5] >= var3) {
               var3 = (var3 - var0[var5 - 1]) / Math.max(0.01F, var0[var5] - var0[var5 - 1]);
               return ((Number)var1.invoke(var5 - 1)).doubleValue()
                  + (((Number)var1.invoke(var5)).doubleValue() - ((Number)var1.invoke(var5 - 1)).doubleValue()) * var3;
            }
         }

         return ((Number)var1.invoke(var2 - 1)).doubleValue();
      }
   }

   private static final double updateScene$lambda$30(ModelSnapshot var0, SceneView var1, double var2) {
      if (var0 != null) {
         var2 = var1.headingAt(var0, var2);
      } else {
         var2 = 0.0;
      }

      return var2;
   }

   private static final double[] updateScene$laneCentres(ModelSnapshot var0, SceneView var1, double var2) {
      if (var0 == null) {
         return new double[0];
      } else {
         ArrayList var10 = new ArrayList();
         Iterator var11 = var1.tracks.getLines().iterator();
         Intrinsics.checkNotNullExpressionValue(var11, "iterator(...)");

         while (true) {
            boolean var7 = var11.hasNext();
            int var4 = 1;
            if (!var7) {
               CollectionsKt.sort(var10);
               ArrayList var12 = new ArrayList();
               var4 = 1;

               for (int var6 = var10.size(); var4 < var6; var4++) {
                  var2 = ((Number)var10.get(var4)).doubleValue();
                  Object var18 = var10.get(var4 - 1);
                  Intrinsics.checkNotNullExpressionValue(var18, "get(...)");
                  var2 -= ((Number)var18).doubleValue();
                  boolean var5;
                  if (2.4 <= var2 && var2 <= 5.0) {
                     var5 = true;
                  } else {
                     var5 = false;
                  }

                  if (var5) {
                     var2 = ((Number)var10.get(var4)).doubleValue();
                     var18 = var10.get(var4 - 1);
                     Intrinsics.checkNotNullExpressionValue(var18, "get(...)");
                     var12.add((var2 + ((Number)var18).doubleValue()) / 2);
                  }
               }

               return CollectionsKt.toDoubleArray(var12);
            }

            LaneTracks.Line var8 = (LaneTracks.Line)var11.next();
            Intrinsics.checkNotNullExpressionValue(var8, "next(...)");
            var8 = var8;
            if (!(var8.getAlpha() < 0.3F)) {
               LaneMarks.Mark var9 = var8.getMark();
               if (var9 == null || var9.getKind() != 1) {
                  var4 = 0;
               }

               if (!var4) {
                  var10.add((double)var8.at((float)var2));
               }
            }
         }
      }
   }

   private static final double updateScene$leftEdgeAt(ModelSnapshot var0, double var1) {
      if (var0 != null) {
         int var3 = ((Object[])var0.getEdgeX()).length;
         boolean var4 = true;
         boolean var8;
         if (var3 == 0) {
            var8 = 1;
         } else {
            var8 = 0;
         }

         if (!var8) {
            float[] var5 = var0.getEdgeX()[0];
            float[] var6 = var0.getEdgeY()[0];
            if (var5.length >= 2) {
               if (var0.getEdgeStd().length == 0) {
                  var8 = var4;
               } else {
                  var8 = (boolean)0;
               }

               if (var8 || !(var0.getEdgeStd()[0] > 0.8F)) {
                  if (var1 <= var5[0]) {
                     return var6[0];
                  }

                  var8 = 1;

                  for (int var11 = var5.length; var8 < var11; var8++) {
                     if (var5[var8] >= var1) {
                        var1 = (var1 - var5[var8 - 1]) / Math.max(0.01F, var5[var8] - var5[var8 - 1]);
                        return var6[var8 - 1] + (var6[var8] - var6[var8 - 1]) * var1;
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

   private static final void updateScene$move(
      SceneView var0, double var1, double var3, long var5, double var7, SceneView.Car var9, double var10, double var12, double var14, boolean var16
   ) {
      if (var16) {
         Pair var23 = var0.toWorld(var10, var12);
         var12 = ((Number)var23.component1()).doubleValue();
         var10 = ((Number)var23.component2()).doubleValue();
         if (Double.isNaN(var9.getWx())) {
            var9.setWx(var12);
            var9.setWy(var10);
         } else {
            double var19 = var12 - var9.getWx();
            double var21 = var10 - var9.getWy();
            if (var19 * var19 + var21 * var21 > 2.25) {
               double var17 = 1;
               var12 = -var1;
               var10 = 1.0;
               var1 = var17;
            } else {
               double var34 = 1;
               var12 = -var1;
               var10 = 6.0;
               var1 = var34;
            }

            var1 -= Math.exp(var12 / var10);
            var9.setWx(var9.getWx() + var19 * var1);
            var9.setWy(var9.getWy() + var21 * var1);
         }

         Pair var24 = var0.fromWorld(var9.getWx(), var9.getWy());
         var1 = ((Number)var24.component1()).doubleValue();
         var12 = ((Number)var24.component2()).doubleValue();
      } else {
         var9.setWx(Double.NaN);
         var1 = var10;
      }

      if (Double.isNaN(var9.getX())) {
         var9.setX(var1);
         var9.setY(var12);
         var9.setHeading(var14);
      }

      if (var16) {
         var9.setX(var1);
         var9.setY(var12);
      } else {
         var9.setX(var9.getX() + (var1 - var9.getX()) * var3);
         var9.setY(var9.getY() + (var12 - var9.getY()) * var3);
      }

      var1 = var14 - var9.getHeading();

      while (true) {
         var3 = var1;
         if (!(var1 > Math.PI)) {
            while (var3 < -Math.PI) {
               var3 += Math.PI * 2;
            }

            if (Math.abs(var3) > 2.0) {
               if (var9.getFlipSince() == 0L) {
                  var9.setFlipSince(var5);
               }

               if (var5 - var9.getFlipSince() > 600L) {
                  var9.setHeading(var14);
                  var9.setFlipSince(0L);
               }
            } else {
               var9.setFlipSince(0L);
               var9.setHeading(var9.getHeading() + var3 * var7);
            }

            var9.place(var9.getX(), var9.getY(), var9.getHeading());
            return;
         }

         var1 -= Math.PI * 2;
      }
   }

   // $VF: synthetic method
   static void updateScene$move$default(
      SceneView var0,
      double var1,
      double var3,
      long var5,
      double var7,
      SceneView.Car var9,
      double var10,
      double var12,
      double var14,
      boolean var16,
      int var17,
      Object var18
   ) {
      if ((var17 & 512) != 0) {
         var16 = false;
      }

      updateScene$move(var0, var1, var3, var5, var7, var9, var10, var12, var14, var16);
   }

   private static final boolean updateScene$occupied(ArrayList<double[]> var0, double var1, double var3, double var5) {
      var0 = var0;
      boolean var9 = var0 instanceof Collection;
      boolean var8 = false;
      if (!var9 || !var0.isEmpty()) {
         for (double[] var10 : var0) {
            boolean var7;
            if (Math.abs(var10[0] - var1) < (var10[2] + var5) * 0.95 && Math.abs(var10[1] - var3) < 1.7) {
               var7 = true;
            } else {
               var7 = false;
            }

            if (var7) {
               var8 = true;
               break;
            }
         }
      }

      return var8;
   }

   private static final boolean updateScene$sameCar(double var0, double var2, double var4, double var6) {
      double var8 = Math.abs(var0 - var4);
      boolean var10 = true;
      if (var8 < 0.15 * var4 + 3.0 && Math.abs(var2 - var6) < 0.06 * var4 + 1.8) {
         return true;
      } else if (var4 < 20.0) {
         return false;
      } else {
         if (!(Math.abs(Math.atan2(var2, var0) - Math.atan2(var6, var4)) < 0.045) || !(var0 > 0.6 * var4) || !(var0 < 1.5 * var4)) {
            var10 = false;
         }

         return var10;
      }
   }

   private static final SceneView.Car updateScene$slotNear(
      SceneView var0, HashSet<SceneView.Car> var1, long var2, long var4, String var6, double var7, double var9
   ) {
      Iterator var17 = var0.genericPool.iterator();
      Intrinsics.checkNotNullExpressionValue(var17, "iterator(...)");

      while (var17.hasNext()) {
         Object var18 = var17.next();
         Intrinsics.checkNotNullExpressionValue(var18, "next(...)");
         if (((SceneView.Car)var18).getId() == var4) {
            return null;
         }
      }

      SceneView.Car var21 = null;
      double var11 = Double.MAX_VALUE;
      Iterator var19 = var0.genericPool.iterator();
      Intrinsics.checkNotNullExpressionValue(var19, "iterator(...)");

      while (var19.hasNext()) {
         Object var22 = var19.next();
         Intrinsics.checkNotNullExpressionValue(var22, "next(...)");
         var22 = (SceneView.Car)var22;
         if (!var1.contains(var22)
            && ((SceneView.Car)var22).getInScene()
            && !Double.isNaN(((SceneView.Car)var22).getX())
            && (var2 - ((SceneView.Car)var22).getLastUsed() <= 900L || !Double.isNaN(((SceneView.Car)var22).getWx()))
            && (
               Intrinsics.areEqual(((SceneView.Car)var22).getKind(), var6)
                  || StringsKt.startsWith$default(((SceneView.Car)var22).getKind(), "car_", false, 2, null)
                     && StringsKt.startsWith$default(var6, "car_", false, 2, null)
            )) {
            double var13 = Math.abs(((SceneView.Car)var22).getX() - var7);
            double var15 = Math.abs(((SceneView.Car)var22).getY() - var9);
            if (!(var13 > 0.12 * var7 + 3.5) && !(var15 > 0.04 * var7 + 2.0)) {
               var13 += var15;
               if (var13 < var11) {
                  var11 = var13;
                  var21 = (SceneView.Car)var22;
               }
            }
         }
      }

      if (var21 != null) {
         var21.setId(var4);
         var21.setLastUsed(var2);
      }

      return var21;
   }

   private static final double updateScene$snapY(SceneView var0, ModelSnapshot var1, double var2, double var4, boolean var6) {
      if (var6) {
         for (int var13 = 0; var13 < 2; var13++) {
            double var7;
            if (var13 == 0) {
               var7 = -1.0;
            } else {
               var7 = 1.0;
            }

            double var9 = updateScene$edgeAtX(var0, var1, var13, var2);
            double var11 = updateScene$curbAt(var1, var0, var13, var2);
            if (!Double.isNaN(var9) && !Double.isNaN(var11) && (var11 - var9) * var7 > 1.5 && (var4 - var9) * var7 > 0.3) {
               return var11 - 1.05 * var7;
            }
         }
      }

      double[] var15 = updateScene$laneCentres(var1, var0, var2);
      double var18 = Double.NaN;
      int var14 = var15.length;
      int var20 = 0;

      while (var20 < var14) {
         double var19 = var15[var20];
         var2 = var18;
         label42:
         if (Math.abs(var19 - var4) < 2.0) {
            if (!Double.isNaN(var18)) {
               var2 = var18;
               if (!(Math.abs(var19 - var4) < Math.abs(var18 - var4))) {
                  break label42;
               }
            }

            var2 = var19;
         }

         var20++;
         var18 = var2;
      }

      if (Double.isNaN(var18)) {
         var2 = var4;
      } else {
         var2 = var4 + (var18 - var4) * 0.8;
      }

      return var2;
   }

   private static final double updateScene$vehHeading(
      Telemetry var0,
      Function1<? super Double, Double> var1,
      boolean var2,
      SceneView var3,
      ModelSnapshot var4,
      Detector.Obj var5,
      double var6,
      float var8,
      double var9
   ) {
      float var13;
      if (var5 != null) {
         var13 = var5.getHeading();
      } else {
         var13 = Float.NaN;
      }

      float var14 = var0.getVEgo();
      boolean var16 = true;
      boolean var15;
      if (var14 < 8.0F
         && var5 != null
         && var5.getMoving()
         && var5.getSpeed() > 4.0F
         && !Float.isNaN(var13)
         && Math.abs((float)Math.sin(var13)) > 0.7F
         && !updateScene$inParking(var3, var4, var6, var9)
         && !updateScene$atPictureEdge(var6, var9)) {
         var15 = true;
      } else {
         var15 = false;
      }

      if (var15) {
         return var13;
      } else {
         double var11 = ((Number)var1.invoke(var6)).doubleValue();
         if (!Float.isNaN(var8) && Math.abs(var8) > 2.0F) {
            if (var8 < 0.0F) {
               var15 = var16;
            } else {
               var15 = false;
            }
         } else if (var5 != null && var5.getVelValid() && var5.getVx() < -3.0F) {
            var15 = var16;
         } else if (var5 != null && var5.getVelValid() && var5.getVx() > 3.0F) {
            var15 = false;
         } else {
            var6 = updateScene$edgeAtX(var3, var4, 0, var6);
            if (var2 && !Double.isNaN(var6) && var9 < 0.5 + var6) {
               var15 = var16;
            } else {
               var15 = false;
            }
         }

         if (var15) {
            var6 = Math.PI;
         } else {
            var6 = 0.0;
         }

         return var6 + var11;
      }
   }

   // $VF: synthetic method
   static double updateScene$vehHeading$default(
      Telemetry var0,
      Function1 var1,
      boolean var2,
      SceneView var3,
      ModelSnapshot var4,
      Detector.Obj var5,
      double var6,
      float var8,
      double var9,
      int var11,
      Object var12
   ) {
      if ((var11 & 256) != 0) {
         var9 = 0.0;
      }

      return updateScene$vehHeading(var0, var1, var2, var3, var4, var5, var6, var8, var9);
   }

   private static final boolean updateScene$vehicle(int var0) {
      boolean var1;
      if (var0 != 2 && var0 != 5 && var0 != 7) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public final boolean getAlwaysDark() {
      return this.alwaysDark;
   }

   public final CarMemory getCarMemory() {
      return this.carMemory;
   }

   public final Detector getDetector() {
      return this.detector;
   }

   public final float getDistance() {
      return this.distance;
   }

   public final String getFailure() {
      return this.failure;
   }

   public final double getGpsLag() {
      return this.gpsLag;
   }

   public final SceneView.LaneHint getLaneHint() {
      return this.laneHint;
   }

   public final LaneMarks getLaneMarks() {
      return this.laneMarks;
   }

   public final LaneMemory getLaneMemory() {
      return this.laneMemory;
   }

   public final LayoutSeg getLayout() {
      return this.layout;
   }

   public final boolean getLayoutHideRoad() {
      return this.layoutHideRoad;
   }

   public final String getMapStatus() {
      return this.mapStatus;
   }

   public final boolean getNight() {
      return this.night;
   }

   public final Function0<Unit> getOnLongPress() {
      return this.onLongPress;
   }

   public final Function0<Unit> getOnTap() {
      return this.onTap;
   }

   public final Function2<Float, Float, Unit> getOnViewChanged() {
      return this.onViewChanged;
   }

   public final boolean getShowTraffic() {
      return this.showTraffic;
   }

   public final int getShownCars() {
      return this.shownCars;
   }

   public final Telemetry getT() {
      return this.t;
   }

   public final float getTiltDeg() {
      return this.tiltDeg;
   }

   public final UiStatus getUiStatus() {
      return this.uiStatus;
   }

   public final String getYawSrc() {
      return this.yawSrc;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   protected void onAttachedToWindow() {
      super.onAttachedToWindow();
      if (!this.engineAlive) {
         label19:
         try {
            this.setupFilament();
            this.engineAlive = true;
         } catch (Throwable var3) {
            Log.e("StarView/Scene", "Filament setup failed", var3);
            this.failure = var3.toString();
            break label19;
         }
      }

      this.startRendering();
   }

   protected void onDetachedFromWindow() {
      this.stopRendering();
      if (this.engineAlive) {
         this.destroyFilament();
      }

      super.onDetachedFromWindow();
   }

   public boolean onTouchEvent(MotionEvent var1) {
      Intrinsics.checkNotNullParameter(var1, "e");
      switch (var1.getActionMasked()) {
         case 0:
            this.pressX = var1.getX();
            this.pressY = var1.getY();
            this.pressArmed = EdgeGuard.INSTANCE.inSystemGestureZone((android.view.View)this, var1.getX(), var1.getY()) ^ true;
            this.removeCallbacks(this.longPressRun);
            if (this.pressArmed) {
               this.postDelayed(this.longPressRun, 800L);
            }
            break;
         case 1:
         case 3:
         case 5:
            this.pressArmed = false;
            this.removeCallbacks(this.longPressRun);
            break;
         case 2:
            if (this.pressArmed) {
               float var4 = Math.abs(var1.getX() - this.pressX);
               float var3 = this.slop;
               float var2 = 2;
               if (var4 > var3 * var2 || Math.abs(var1.getY() - this.pressY) > this.slop * var2) {
                  this.pressArmed = false;
                  this.removeCallbacks(this.longPressRun);
               }
            }
         case 4:
      }

      this.scale.onTouchEvent(var1);
      if (var1.getPointerCount() >= 2) {
         float var6 = (var1.getY(0) + var1.getY(1)) / 2.0F;
         if (!Float.isNaN(this.twoFingerY) && var1.getActionMasked() == 2 && !this.scale.isInProgress()) {
            this.setTiltDeg(this.tiltDeg + (var6 - this.twoFingerY) * 0.15F);
            this.gesturing = true;
         }

         this.twoFingerY = var6;
      } else {
         this.twoFingerY = Float.NaN;
      }

      if (var1.getActionMasked() == 1 || var1.getActionMasked() == 3) {
         if (this.gesturing) {
            this.gesturing = false;
            Function2 var5 = this.onViewChanged;
            if (var5 != null) {
               var5.invoke(this.distance, this.tiltDeg);
            }
         }

         this.twoFingerY = Float.NaN;
      }

      if (var1.getPointerCount() < 2 && !this.gesturing) {
         this.taps.onTouchEvent(var1);
      }

      return true;
   }

   protected void onVisibilityChanged(android.view.View var1, int var2) {
      Intrinsics.checkNotNullParameter(var1, "changedView");
      super.onVisibilityChanged(var1, var2);
      if (var1 == this) {
         if (var2 == 0 && this.getWindowVisibility() == 0) {
            this.startRendering();
         } else {
            this.stopRendering();
         }
      }
   }

   protected void onWindowVisibilityChanged(int var1) {
      super.onWindowVisibilityChanged(var1);
      if (var1 == 0 && this.getVisibility() == 0) {
         this.startRendering();
      } else {
         this.stopRendering();
      }
   }

   public final void setAlwaysDark(boolean var1) {
      if (this.alwaysDark != var1) {
         this.alwaysDark = var1;
         this.paletteDirty = true;
      }
   }

   public final void setCarMemory(CarMemory var1) {
      this.carMemory = var1;
   }

   public final void setDetector(Detector var1) {
      this.detector = var1;
   }

   public final void setDistance(float var1) {
      this.distance = RangesKt.coerceIn(var1, 15.0F, 90.0F);
   }

   public final void setFailure(String var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.failure = var1;
   }

   public final void setGpsLag(double var1) {
      this.gpsLag = var1;
   }

   public final void setLaneHint(SceneView.LaneHint var1) {
      this.laneHint = var1;
   }

   public final void setLaneMarks(LaneMarks var1) {
      this.laneMarks = var1;
   }

   public final void setLaneMemory(LaneMemory var1) {
      this.laneMemory = var1;
   }

   public final void setLayout(LayoutSeg var1) {
      this.layout = var1;
   }

   public final void setLayoutHideRoad(boolean var1) {
      this.layoutHideRoad = var1;
   }

   public final void setMapStatus(String var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.mapStatus = var1;
   }

   public final void setNight(boolean var1) {
      if (this.night != var1) {
         this.night = var1;
         this.paletteDirty = true;
      }
   }

   public final void setOnLongPress(Function0<Unit> var1) {
      this.onLongPress = var1;
   }

   public final void setOnTap(Function0<Unit> var1) {
      this.onTap = var1;
   }

   public final void setOnViewChanged(Function2<? super Float, ? super Float, Unit> var1) {
      this.onViewChanged = var1;
   }

   public final void setShowTraffic(boolean var1) {
      this.showTraffic = var1;
   }

   public final void setShownCars(int var1) {
      this.shownCars = var1;
   }

   public final void setT(Telemetry var1) {
      this.t = var1;
   }

   public final void setTiltDeg(float var1) {
      this.tiltDeg = RangesKt.coerceIn(var1, 10.0F, 60.0F);
   }

   public final void setUiStatus(UiStatus var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.uiStatus = var1;
   }

   public final void setYawSrc(String var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.yawSrc = var1;
   }

   private final class Car {
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
      final SceneView this$0;
      private boolean tinted;
      private double wx;
      private double wy;
      private double x;
      private double y;

      public Car(FilamentAsset var1, double var2, String param4, String var5) {
         Intrinsics.checkNotNullParameter(var2, "asset");
         Intrinsics.checkNotNullParameter(var5, "kind");
         this.this$0 = var1;
         super();
         this.asset = var2;
         this.length = var3;
         this.kind = var5;
         this.body = this.mi("Body");
         this.painted = Float.NaN;
         this.lampR = this.mi("LampR");
         this.lampY = this.mi("LampY");
         this.lampG = this.mi("LampG");
         this.lampShown = -2;
         this.tail = this.mi("TailLight");
         this.tailL = this.mi("TailLightL");
         this.tailR = this.mi("TailLightR");
         this.head = this.mi("HeadLight");
         this.sigL = this.mi("SignalL");
         this.sigR = this.mi("SignalR");
         this.x = Double.NaN;
         this.id = Long.MIN_VALUE;
         this.wx = Double.NaN;
         this.wy = Double.NaN;
      }

      private static final void lights$side(float var0, MaterialInstance var1, boolean var2) {
         if (var1 != null) {
            if (var2) {
               var1.setParameter("emissiveFactor", 1.6F, 0.7F, 0.02F);
            } else {
               var1.setParameter("emissiveFactor", var0, 0.03F * var0, 0.02F * var0);
            }
         }
      }

      private final MaterialInstance mi(String var1) {
         MaterialInstance[] var4 = this.asset.getInstance().getMaterialInstances();
         Intrinsics.checkNotNullExpressionValue(var4, "getMaterialInstances(...)");
         Object[] var5 = var4;
         int var3 = var5.length;
         int var2 = 0;

         while (true) {
            if (var2 >= var3) {
               var6 = null;
               break;
            }

            var4 = (MaterialInstance[])var5[var2];
            if (Intrinsics.areEqual(((MaterialInstance)var4).getName(), var1)) {
               var6 = var4;
               break;
            }

            var2++;
         }

         return (MaterialInstance)var6;
      }

      public final FilamentAsset getAsset() {
         return this.asset;
      }

      public final MaterialInstance getBody() {
         return this.body;
      }

      public final long getFirstShownMs() {
         return this.firstShownMs;
      }

      public final long getFlipSince() {
         return this.flipSince;
      }

      public final MaterialInstance getHead() {
         return this.head;
      }

      public final double getHeading() {
         return this.heading;
      }

      public final long getId() {
         return this.id;
      }

      public final boolean getInScene() {
         return this.inScene;
      }

      public final String getKind() {
         return this.kind;
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

      public final long getLastUsed() {
         return this.lastUsed;
      }

      public final double getLength() {
         return this.length;
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

      public final double getWx() {
         return this.wx;
      }

      public final double getWy() {
         return this.wy;
      }

      public final double getX() {
         return this.x;
      }

      public final double getY() {
         return this.y;
      }

      public final void grey() {
         MaterialInstance var1 = this.body;
         if (var1 != null) {
            var1.setParameter("baseColorFactor", 0.5F, 0.51F, 0.52F, 1.0F);
         }
      }

      public final void lamp(int var1) {
         if (var1 != this.lampShown) {
            this.lampShown = var1;
            MaterialInstance var5 = this.lampR;
            float var4 = 3.0F;
            if (var5 != null) {
               float var2;
               if (var1 == 0) {
                  var2 = 3.0F;
               } else {
                  var2 = 0.0F;
               }

               float var3;
               if (var1 == 0) {
                  var3 = 3.0F;
               } else {
                  var3 = 0.0F;
               }

               var5.setParameter("emissiveFactor", var2, var3 * 0.05F, 0.0F);
            }

            var5 = this.lampY;
            if (var5 != null) {
               float var6;
               if (var1 == 1) {
                  var6 = var4;
               } else {
                  var6 = 0.0F;
               }

               float var8;
               if (var1 == 1) {
                  var8 = 1.6F;
               } else {
                  var8 = 0.0F;
               }

               var5.setParameter("emissiveFactor", var6, var8, 0.0F);
            }

            var5 = this.lampG;
            if (var5 != null) {
               float var7;
               if (var1 == 2) {
                  var7 = 2.6F;
               } else {
                  var7 = 0.0F;
               }

               float var9;
               if (var1 == 2) {
                  var9 = 1.2F;
               } else {
                  var9 = 0.0F;
               }

               var5.setParameter("emissiveFactor", 0.0F, var7, var9);
            }
         }
      }

      public final void lights(boolean var1, boolean var2, boolean var3, boolean var4, boolean var5) {
         float var6;
         if (var1) {
            var6 = 1.6F;
         } else if (var2) {
            var6 = 0.35F;
         } else {
            var6 = 0.0F;
         }

         MaterialInstance var9 = this.tail;
         if (var9 != null) {
            var9.setParameter("emissiveFactor", var6, 0.03F * var6, 0.02F * var6);
         }

         var9 = this.tailL;
         boolean var8 = true;
         if (var3 && var5) {
            var1 = true;
         } else {
            var1 = false;
         }

         lights$side(var6, var9, var1);
         var9 = this.tailR;
         if (var4 && var5) {
            var1 = var8;
         } else {
            var1 = false;
         }

         lights$side(var6, var9, var1);
         if (var2) {
            var6 = 1.2F;
         } else {
            var6 = 0.0F;
         }

         var9 = this.head;
         if (var9 != null) {
            var9.setParameter("emissiveFactor", var6, 0.97F * var6, 0.9F * var6);
         }

         float var7 = 1.4F;
         if (var3 && var5) {
            var6 = 1.4F;
         } else {
            var6 = 0.0F;
         }

         if (!var4 || !var5) {
            var7 = 0.0F;
         }

         var9 = this.sigL;
         if (var9 != null) {
            var9.setParameter("emissiveFactor", var6, var6 * 0.55F, 0.0F);
         }

         var9 = this.sigR;
         if (var9 != null) {
            var9.setParameter("emissiveFactor", var7, 0.55F * var7, 0.0F);
         }
      }

      public final void place(double var1, double var3, double var5) {
         double var7 = Math.PI - var5;
         var5 = Math.cos(var7);
         var7 = Math.sin(var7);
         float var9 = (float)var5;
         float var12 = (float)(-var7);
         float var11 = (float)var7;
         float var13 = (float)var5;
         float var10 = (float)var3;
         float var14 = (float)(-var1);
         Engine var16 = this.this$0.engine;
         Engine var15 = var16;
         if (var16 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("engine");
            var15 = null;
         }

         TransformManager var19 = var15.getTransformManager();
         Intrinsics.checkNotNullExpressionValue(var19, "getTransformManager(...)");
         var19.setTransform(
            var19.getInstance(this.asset.getRoot()),
            new float[]{var9, 0.0F, var12, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, var11, 0.0F, var13, 0.0F, var10, 0.0F, var14, 1.0F}
         );
      }

      public final void remembered(boolean var1) {
         if (var1 != this.tinted && this.body != null) {
            this.tinted = var1;
            if (var1) {
               this.body.setParameter("baseColorFactor", 0.55F, 0.68F, 0.88F, 1.0F);
            } else {
               this.grey();
            }
         }
      }

      public final void setFirstShownMs(long var1) {
         this.firstShownMs = var1;
      }

      public final void setFlipSince(long var1) {
         this.flipSince = var1;
      }

      public final void setHeading(double var1) {
         this.heading = var1;
      }

      public final void setId(long var1) {
         this.id = var1;
      }

      public final void setInScene(boolean var1) {
         this.inScene = var1;
      }

      public final void setLastUsed(long var1) {
         this.lastUsed = var1;
      }

      public final void setWx(double var1) {
         this.wx = var1;
      }

      public final void setWy(double var1) {
         this.wy = var1;
      }

      public final void setX(double var1) {
         this.x = var1;
      }

      public final void setY(double var1) {
         this.y = var1;
      }

      public final void show(boolean var1) {
         if (var1 != this.inScene) {
            Object var3 = null;
            Object var4 = null;
            Scene var2 = this.this$0.scene;
            if (var1) {
               if (var2 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("scene");
                  var2 = (Scene)var4;
               }

               var2.addEntities(this.asset.getEntities());
            } else {
               if (var2 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("scene");
                  var2 = (Scene)var3;
               }

               var2.removeEntities(this.asset.getEntities());
            }

            this.inScene = var1;
         }
      }
   }

   public static final class Companion {
      private Companion() {
      }

      // $VF: synthetic method
      public Companion(DefaultConstructorMarker var1) {
         this();
      }

      private static final float linear$ch(int var0) {
         float var1 = var0 / 255.0F;
         if (var1 <= 0.04045F) {
            var1 /= 12.92F;
         } else {
            var1 = (float)Math.pow((0.055F + var1) / 1.055F, 2.4);
         }

         return var1;
      }

      public final Triple<Float, Float, Float> linear(int var1) {
         return new Triple<>(linear$ch(Color.red(var1)), linear$ch(Color.green(var1)), linear$ch(Color.blue(var1)));
      }

      public final int withAlpha(int var1, int var2) {
         return Color.argb(RangesKt.coerceIn(var2, 0, 255), Color.red(var1), Color.green(var1), Color.blue(var1));
      }
   }

   private static final class Curbs {
      private final double lHi;
      private final double lLo;
      private final double rHi;
      private final double rLo;
      private final String txt;

      public Curbs(double var1, double var3, double var5, double var7, String var9) {
         Intrinsics.checkNotNullParameter(var9, "txt");
         super();
         this.lLo = var1;
         this.lHi = var3;
         this.rLo = var5;
         this.rHi = var7;
         this.txt = var9;
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

   public static final class LaneHint {
      private final long atNs;
      private final double conf;
      private final double edge;
      private final int lane;
      private final int total;
      private final long wayId;

      public LaneHint(long var1, int var3, int var4, double var5, double var7, long var9) {
         this.wayId = var1;
         this.lane = var3;
         this.total = var4;
         this.conf = var5;
         this.edge = var7;
         this.atNs = var9;
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

      public Palette(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, float var9, float var10, float[] var11) {
         Intrinsics.checkNotNullParameter(var11, "sunColor");
         super();
         this.bg = var1;
         this.lane = var2;
         this.laneAlt = var3;
         this.edge = var4;
         this.pathOn = var5;
         this.pathOff = var6;
         this.pathExp = var7;
         this.dot = var8;
         this.sunIntensity = var9;
         this.iblIntensity = var10;
         this.sunColor = var11;
      }

      public final int getBg() {
         return this.bg;
      }

      public final int getDot() {
         return this.dot;
      }

      public final int getEdge() {
         return this.edge;
      }

      public final float getIblIntensity() {
         return this.iblIntensity;
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

      public final float[] getSunColor() {
         return this.sunColor;
      }

      public final float getSunIntensity() {
         return this.sunIntensity;
      }
   }

   private static final class Pt {
      private final int side;
      private final long tNs;
      private final double wx;
      private final double wy;

      public Pt(double var1, double var3, long var5, int var7) {
         this.wx = var1;
         this.wy = var3;
         this.tNs = var5;
         this.side = var7;
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

   private static final class Snap {
      private final double dist;
      private final byte[] g;
      private final double px;
      private final double py;
      private final long tNs;
      private final double yaw;

      public Snap(byte[] var1, double var2, double var4, double var6, long var8, double var10) {
         Intrinsics.checkNotNullParameter(var1, "g");
         super();
         this.g = var1;
         this.px = var2;
         this.py = var4;
         this.yaw = var6;
         this.tNs = var8;
         this.dist = var10;
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
}
