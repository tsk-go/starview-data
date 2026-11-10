package com.starpilot.starview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.media.Image;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReference;
import kotlin.Triple;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.ArrayDeque;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.collections.MapsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.tensorflow.lite.Interpreter;
import org.tensorflow.lite.gpu.GpuDelegate;

public final class Detector {
   public static final int AMB = 16;
   public static final String ASSET = "det/yolox_s_384x640_fp16.lite";
   public static final Detector.Companion Companion = new Detector.Companion(null);
   public static final float HIGH = 0.35F;
   public static final int IN_H = 384;
   public static final int IN_W = 640;
   public static final float LOW = 0.15F;
   private static final Map<Integer, String> NAMES = MapsKt.mapOf(
      new kotlin.Pair[]{
         TuplesKt.to(0, "person"),
         TuplesKt.to(1, "bike"),
         TuplesKt.to(2, "car"),
         TuplesKt.to(3, "moto"),
         TuplesKt.to(5, "bus"),
         TuplesKt.to(7, "truck"),
         TuplesKt.to(9, "light"),
         TuplesKt.to(11, "stop")
      }
   );
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
   private volatile List<Detector.Obj> objects;
   private volatile long objectsAtMs;
   private final CarOrient orient;
   private int[] outOrder;
   private final List<Triple<Integer, Integer, ByteBuffer>> outs;
   private int padKey;
   private final float[] pix;
   private final ExecutorService pool;
   private volatile double prepMs;
   private final Detector.Source roadSrc;
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
   private final Detector.Source wideSrc;
   private float[] wiperAvg;
   private boolean wiperDark;
   private final ArrayDeque<Long> wiperTimes;
   private Thread worker;

   // $VF: synthetic method
   public static Thread $r8$lambda$fnsA3h6UflVHrGrli5yRfiS0_Os/* $VF was: $r8$lambda$fnsA3h6UflVHrGrli5yRfiS0_Os*/(Runnable var0) {
      return pool$lambda$19(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$i7zk1OTYz4HrgO_bSd2c7UH8fE4(
      int var0, int var1, Detector.Frame var2, float var3, int var4, boolean[] var5, float[] var6, int[] var7, int[] var8, Detector var9
   ) {
      return fill$lambda$21$lambda$20(var0, var1, var2, var3, var4, var5, var6, var7, var8, var9);
   }

   // $VF: synthetic method
   public static void $r8$lambda$kkzcs7_ZRGVw5ggYLIZ_YG7V2qg/* $VF was: $r8$lambda$kkzcs7_ZRGVw5ggYLIZ_YG7V2qg*/(Detector var0) {
      start$lambda$3(var0);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$mUCpj665OUPKpCK7IBikUSJEPak(Detector var0, boolean var1, Detector.Frame var2, Detector.Track var3) {
      return track$lambda$28(var0, var1, var2, var3);
   }

   public Detector(Context var1) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      super();
      this.ctx = var1;
      this.objects = CollectionsKt.emptyList();
      this.fovTan = 0.5F;
      this.status = "off";
      this.accel = "-";
      this.gpuError = "";
      this.orient = new CarOrient(this.ctx);
      DetMasks.INSTANCE.init(this.ctx);
      this.useOrient = true;
      this.roadSrc = new Detector.Source((boolean)this, false);
      this.wideSrc = new Detector.Source((boolean)this, true);
      this.lock = new Object();
      ByteBuffer var9 = ByteBuffer.allocateDirect(2949120).order(ByteOrder.nativeOrder());
      Intrinsics.checkNotNullExpressionValue(var9, "order(...)");
      this.input = var9;
      Object[] var6 = (Object[])(new int[][]{{48, 80}, {24, 40}, {12, 20}});
      var var7 = new ArrayList(var6.length);
      int var3 = var6.length;

      for (int var2 = 0; var2 < var3; var2++) {
         int[] var8 = (int[])var6[var2];
         int var5 = var8[0];
         int var4 = var8[1];
         var7.add(new Triple<>(var5, var4, ByteBuffer.allocateDirect(var5 * var4 * 85 * 4).order(ByteOrder.nativeOrder())));
      }

      List var10 = var7;
      this.outs = var10;
      this.fpsT0 = System.nanoTime();
      this.firstRun = true;
      this.pix = new float[737280];
      this.nThreads = 4;
      this.pool = Executors.newFixedThreadPool(this.nThreads, new Detector$$ExternalSyntheticLambda0());
      this.padKey = -1;
      this.maskKey = new long[]{-1L, -1L};
      this.maskBits = new boolean[2][];
      this.nextId = 1L;
      this.schoolBus = new SchoolBus();
      this.wiperTimes = new ArrayDeque<>();
   }

   private final int[] ambient(Detector.Frame var1) {
      int[] var23 = new int[32];
      int var6 = Math.max(2, var1.getH() / 16);

      for (int var8 = 0; var8 < 2; var8++) {
         for (int var9 = 0; var9 < 16; var9++) {
            long var19 = 0L;
            long var15 = 0L;
            long var17 = 0L;
            int var12 = 0;
            int var7 = var1.getW() * var9 / 16;
            int var11 = (var9 + 1) * var1.getW() / 16;
            int var5;
            if (var8 == 0) {
               var5 = 0;
            } else {
               var5 = var1.getH() - var6;
            }

            for (int var10 = var5; var10 < var5 + var6; var10 += 4) {
               int var13 = var7;

               while (var13 < var11) {
                  long var21 = var19 + (var1.getY()[var1.getYStride() * var10 + var13] & 255);
                  int var14 = var10 / 2 * var1.getUvStride() + var13 / 2 * var1.getUvPixStride();
                  if (var14 < var1.getU().length && var14 < var1.getV().length) {
                     var15 += var1.getU()[var14] & 255;
                     var19 = var1.getV()[var14] & 255;
                  } else {
                     var19 = 128;
                     var15 += var19;
                  }

                  var17 += var19;
                  var12++;
                  var13 += 6;
                  var19 = var21;
               }
            }

            if (var12 != 0) {
               float var4 = (float)var19 / var12 - 16.0F;
               float var3 = (float)var15 / var12 - 128.0F;
               float var2 = (float)var17 / var12 - 128.0F;
               var23[var8 * 16 + var9] = RangesKt.coerceIn((int)(var4 * 1.164F + 1.596F * var2), 0, 255) << 16
                  | 0xFF000000
                  | RangesKt.coerceIn((int)(var4 * 1.164F - 0.392F * var3 - 0.813F * var2), 0, 255) << 8
                  | RangesKt.coerceIn((int)(1.164F * var4 + 2.017F * var3), 0, 255);
            }
         }
      }

      return var23;
   }

   private final float atan2(float var1, float var2) {
      return (float)Math.atan2(var1, var2);
   }

   private final float boxIou(Detector.Track var1, Detector.Track var2) {
      float var3 = Math.max(0.0F, Math.min(var1.getX1(), var2.getX1()) - Math.max(var1.getX0(), var2.getX0()))
         * Math.max(0.0F, Math.min(var1.getY1(), var2.getY1()) - Math.max(var1.getY0(), var2.getY0()));
      return var3
         / ((var1.getX1() - var1.getX0()) * (var1.getY1() - var1.getY0()) + (var2.getX1() - var2.getX0()) * (var2.getY1() - var2.getY0()) - var3 + 1.0E-6F);
   }

   private final Triple<Float, Float, Float> busLook(Detector.Frame var1, float var2, float var3, float var4, float var5) {
      int var6 = 0;
      int var7 = 0;

      int var8;
      for (var8 = 0; var6 < 6; var6++) {
         int var9 = 0;

         while (var9 < 6) {
            Triple var14 = this.rgb(
               var1,
               RangesKt.coerceIn((int)(var2 + (var4 - var2) * (var9 * 0.8F / 5.0F + 0.1F)), 0, var1.getW() - 1),
               RangesKt.coerceIn((int)(var3 + (var5 - var3) * (var6 * 0.5F / 5.0F + 0.3F)), 0, var1.getH() - 1)
            );
            int var11 = ((Number)var14.component1()).intValue();
            int var13 = ((Number)var14.component2()).intValue();
            int var12 = ((Number)var14.component3()).intValue();
            int var10 = var8 + 1;
            var8 = var7;
            if (var11 > 140) {
               var8 = var7;
               if (var13 > 105) {
                  var8 = var7;
                  if (var12 < 100) {
                     var8 = var7;
                     if (var11 - var12 > 70) {
                        var8 = var7 + 1;
                     }
                  }
               }
            }

            var9++;
            var7 = var8;
            var8 = var10;
         }
      }

      return new Triple<>(
         (float)var7 / var8, busLook$red(var2, var4, var1, var3, var5, this, 0.02F, 0.25F), busLook$red(var2, var4, var1, var3, var5, this, 0.75F, 0.98F)
      );
   }

   private static final float busLook$red(float var0, float var1, Detector.Frame var2, float var3, float var4, Detector var5, float var6, float var7) {
      float var8 = 0.0F;

      for (int var10 = 0; var10 < 3; var10++) {
         int var11 = 0;

         while (var11 < 4) {
            Triple var13 = var5.rgb(
               var2,
               RangesKt.coerceIn((int)((var1 - var0) * (var6 + (var7 - var6) * var11 / 3.0F) + var0), 0, var2.getW() - 1),
               RangesKt.coerceIn((int)((var4 - var3) * (var10 * 0.12F / 2.0F + 0.03F) + var3), 0, var2.getH() - 1)
            );
            int var12 = ((Number)var13.component1()).intValue();
            float var9 = var8;
            if (var12 - (((Number)var13.component2()).intValue() + ((Number)var13.component3()).intValue()) / 2 > 50) {
               var9 = Math.max(var8, (float)var12);
            }

            var11++;
            var8 = var9;
         }
      }

      return var8;
   }

   private final int clamp(int var1) {
      if (var1 < 0) {
         var1 = 0;
      } else {
         short var2 = 255;
         if (var1 > 255) {
            var1 = var2;
         }
      }

      return var1;
   }

   private final Bitmap colour(Detector.Frame var1) {
      int var8 = var1.getH() * 640 / var1.getW();
      int[] var12 = new int[640 * var8];

      for (int var3 = 0; var3 < var8; var3++) {
         int var6 = var1.getH() * var3 / var8;
         int var7 = var1.getYStride() * var6;
         int var4 = (var6 >> 1) * var1.getUvStride();

         for (int var5 = 0; var5 < 640; var5++) {
            int var10 = var1.getW() * var5 / 640;
            byte var9 = var1.getY()[var7 + var10];
            int var11 = (var10 >> 1) * var1.getUvPixStride() + var4;
            var10 = (var1.getU()[var11] & 255) - 128;
            var11 = (var1.getV()[var11] & 255) - 128;
            float var2 = ((var9 & 255) - 16) * 1.164F;
            var12[var3 * 640 + var5] = RangesKt.coerceIn((int)(var11 * 1.596F + var2), 0, 255) << 16
               | 0xFF000000
               | RangesKt.coerceIn((int)(var2 - var10 * 0.392F - var11 * 0.813F), 0, 255) << 8
               | RangesKt.coerceIn((int)(var10 * 2.017F + var2), 0, 255);
         }
      }

      Bitmap var13 = Bitmap.createBitmap(var12, 640, var8, Config.ARGB_8888);
      Intrinsics.checkNotNullExpressionValue(var13, "createBitmap(...)");
      return var13;
   }

   private final byte[] copy(ByteBuffer var1, byte[] var2) {
      ByteBuffer var3 = var1.duplicate();
      ((Buffer)var3).rewind();
      byte[] var4;
      if (var2.length == var3.remaining()) {
         var4 = var2;
      } else {
         var4 = new byte[var3.remaining()];
      }

      var3.get(var4);
      return var4;
   }

   private final List<Detector.Det> decode(float var1, int var2, int var3, float var4) {
      ArrayList var25 = new ArrayList();
      int[] var22 = new int[]{8, 16, 32};
      Iterator var21 = this.outs.iterator();
      int var12 = 0;

      while (var21.hasNext()) {
         int var15 = var12++;
         Triple var23 = (Triple)var21.next();
         int var11 = ((Number)var23.component1()).intValue();
         int var14 = ((Number)var23.component2()).intValue();
         ByteBuffer var24 = (ByteBuffer)var23.component3();
         ((Buffer)var24).rewind();
         FloatBuffer var26 = var24.asFloatBuffer();
         int var19 = var22[var15];
         int var13 = 0;

         for (var40 = var22; var13 < var11; var23 = var23) {
            for (int var16 = 0; var16 < var14; var16++) {
               int var20 = (var13 * var14 + var16) * 85;
               float var7 = var26.get(var20 + 4);
               if (!(var7 < var4)) {
                  int var17 = -1;
                  float var5 = 0.0F;
                  Iterator var41 = NAMES.keySet().iterator();

                  while (var41.hasNext()) {
                     int var18 = ((Number)var41.next()).intValue();
                     float var6 = var26.get(var20 + 5 + var18);
                     if (var6 > var5) {
                        var5 = var6;
                        var17 = var18;
                     }
                  }

                  var5 = var7 * var5;
                  if (var17 >= 0 && !(var5 < var4) && (var17 != 0 || !(var5 < 0.5F))) {
                     var7 = (var26.get(var20) + var16) * var19;
                     float var8 = (var26.get(var20 + 1) + var13) * var19;
                     float var9 = (float)Math.exp(RangesKt.coerceIn(var26.get(var20 + 2), -10.0F, 10.0F)) * var19;
                     float var30 = (float)Math.exp(RangesKt.coerceIn(var26.get(var20 + 3), -10.0F, 10.0F)) * var19;
                     float var10 = 2;
                     var25.add(
                        new Detector.Det(
                           var17,
                           var5,
                           Math.max(0.0F, (var7 - var9 / var10) / var1),
                           Math.max(0.0F, (var8 - var30 / var10) / var1),
                           Math.min((float)var2, (var7 + var9 / var10) / var1),
                           Math.min((float)var3, (var30 / var10 + var8) / var1),
                           0L,
                           64,
                           null
                        )
                     );
                  }
               }
            }

            var13++;
            var40 = var40;
            var21 = var21;
         }

         var22 = var40;
      }

      List var33 = var25;
      if (var33.size() > 1) {
         CollectionsKt.sortWith(var33, new Comparator() {
            @Override
            public final int compare(T var1, T var2x) {
               return ComparisonsKt.compareValues(((Detector.Det)var2x).getScore(), ((Detector.Det)var1).getScore());
            }
         });
      }

      ArrayList var34 = new ArrayList();
      Iterator var36 = var25.iterator();
      Intrinsics.checkNotNullExpressionValue(var36, "iterator(...)");

      while (var36.hasNext()) {
         Object var38 = var36.next();
         Intrinsics.checkNotNullExpressionValue(var38, "next(...)");
         var38 = (Detector.Det)var38;
         var var42 = var34;
         boolean var28;
         if (var42 instanceof Collection && var42.isEmpty()) {
            var28 = true;
         } else {
            Iterator var43 = var42.iterator();

            while (true) {
               if (!var43.hasNext()) {
                  var28 = true;
                  break;
               }

               Detector.Det var44 = (Detector.Det)var43.next();
               if (var44.getCls() == ((Detector.Det)var38).getCls() && this.iou(var44, (Detector.Det)var38) > 0.45F) {
                  var28 = true;
               } else {
                  var28 = false;
               }

               if (var28) {
                  var28 = false;
                  break;
               }
            }
         }

         if (var28) {
            var34.add(var38);
         }
      }

      return var34;
   }

   // $VF: synthetic method
   static List decode$default(Detector var0, float var1, int var2, int var3, float var4, int var5, Object var6) {
      if ((var5 & 8) != 0) {
         var4 = 0.15F;
      }

      return var0.decode(var1, var2, var3, var4);
   }

   private final void drawPath(Bitmap var1, Detector.Frame var2) {
      Telemetry var4 = this.telemetry;
      if (var4 != null) {
         ModelSnapshot var3 = var4.getModel();
         if (var3 != null) {
            Detector.Path.INSTANCE
               .pathOn(var1, var2.getW(), var2.getH(), var4.getCalRpy(), var4.getCalHeight(), var3.getPosX(), var3.getPosY(), var3.getPosZ());
         }
      }
   }

   private final void ensureInterpreter() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:385)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:551)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1057)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:572)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
      //
      // Bytecode:
      // 000: aload 0
      // 001: getfield com/starpilot/starview/Detector.interp Lorg/tensorflow/lite/Interpreter;
      // 004: ifnull 008
      // 007: return
      // 008: aload 0
      // 009: ldc_w "loading model…"
      // 00c: putfield com/starpilot/starview/Detector.status Ljava/lang/String;
      // 00f: aload 0
      // 010: bipush 1
      // 011: putfield com/starpilot/starview/Detector.firstRun Z
      // 014: invokestatic java/lang/System.nanoTime ()J
      // 017: lstore 5
      // 019: getstatic com/starpilot/starview/ModelDownload.INSTANCE Lcom/starpilot/starview/ModelDownload;
      // 01c: aload 0
      // 01d: getfield com/starpilot/starview/Detector.ctx Landroid/content/Context;
      // 020: invokevirtual com/starpilot/starview/ModelDownload.detector (Landroid/content/Context;)Ljava/io/File;
      // 023: astore 7
      // 025: aload 7
      // 027: ifnull 1e9
      // 02a: aload 7
      // 02c: invokestatic kotlin/io/FilesKt.readBytes (Ljava/io/File;)[B
      // 02f: astore 7
      // 031: aload 7
      // 033: arraylength
      // 034: invokestatic java/nio/ByteBuffer.allocateDirect (I)Ljava/nio/ByteBuffer;
      // 037: invokestatic java/nio/ByteOrder.nativeOrder ()Ljava/nio/ByteOrder;
      // 03a: invokevirtual java/nio/ByteBuffer.order (Ljava/nio/ByteOrder;)Ljava/nio/ByteBuffer;
      // 03d: astore 9
      // 03f: aload 9
      // 041: aload 7
      // 043: invokevirtual java/nio/ByteBuffer.put ([B)Ljava/nio/ByteBuffer;
      // 046: pop
      // 047: aload 9
      // 049: invokevirtual java/nio/ByteBuffer.rewind ()Ljava/nio/Buffer;
      // 04c: pop
      // 04d: new java/io/File
      // 050: astore 8
      // 052: aload 8
      // 054: aload 0
      // 055: getfield com/starpilot/starview/Detector.ctx Landroid/content/Context;
      // 058: invokevirtual android/content/Context.getCacheDir ()Ljava/io/File;
      // 05b: ldc_w "gpu_det"
      // 05e: invokespecial java/io/File.<init> (Ljava/io/File;Ljava/lang/String;)V
      // 061: aload 8
      // 063: invokevirtual java/io/File.mkdirs ()Z
      // 066: pop
      // 067: new org/tensorflow/lite/gpu/GpuDelegate
      // 06a: astore 7
      // 06c: new org/tensorflow/lite/gpu/GpuDelegate$Options
      // 06f: astore 10
      // 071: aload 10
      // 073: invokespecial org/tensorflow/lite/gpu/GpuDelegate$Options.<init> ()V
      // 076: aload 7
      // 078: aload 10
      // 07a: bipush 1
      // 07b: invokevirtual org/tensorflow/lite/gpu/GpuDelegate$Options.setPrecisionLossAllowed (Z)Lorg/tensorflow/lite/gpu/GpuDelegateFactory$Options;
      // 07e: bipush 1
      // 07f: invokevirtual org/tensorflow/lite/gpu/GpuDelegateFactory$Options.setInferencePreference (I)Lorg/tensorflow/lite/gpu/GpuDelegateFactory$Options;
      // 082: aload 8
      // 084: invokevirtual java/io/File.getAbsolutePath ()Ljava/lang/String;
      // 087: ldc_w "yolox_s_384x640_fp16_v1"
      // 08a: invokevirtual org/tensorflow/lite/gpu/GpuDelegateFactory$Options.setSerializationParams (Ljava/lang/String;Ljava/lang/String;)Lorg/tensorflow/lite/gpu/GpuDelegateFactory$Options;
      // 08d: invokespecial org/tensorflow/lite/gpu/GpuDelegate.<init> (Lorg/tensorflow/lite/gpu/GpuDelegateFactory$Options;)V
      // 090: new org/tensorflow/lite/Interpreter
      // 093: astore 10
      // 095: new org/tensorflow/lite/Interpreter$Options
      // 098: astore 8
      // 09a: aload 8
      // 09c: invokespecial org/tensorflow/lite/Interpreter$Options.<init> ()V
      // 09f: aload 10
      // 0a1: aload 9
      // 0a3: aload 8
      // 0a5: aload 7
      // 0a7: checkcast org/tensorflow/lite/Delegate
      // 0aa: invokevirtual org/tensorflow/lite/Interpreter$Options.addDelegate (Lorg/tensorflow/lite/Delegate;)Lorg/tensorflow/lite/Interpreter$Options;
      // 0ad: invokespecial org/tensorflow/lite/Interpreter.<init> (Ljava/nio/ByteBuffer;Lorg/tensorflow/lite/Interpreter$Options;)V
      // 0b0: aload 0
      // 0b1: aload 10
      // 0b3: putfield com/starpilot/starview/Detector.interp Lorg/tensorflow/lite/Interpreter;
      // 0b6: aload 0
      // 0b7: aload 7
      // 0b9: putfield com/starpilot/starview/Detector.gpu Lorg/tensorflow/lite/gpu/GpuDelegate;
      // 0bc: aload 0
      // 0bd: ldc_w "gpu"
      // 0c0: putfield com/starpilot/starview/Detector.accel Ljava/lang/String;
      // 0c3: goto 13a
      // 0c6: astore 10
      // 0c8: ldc_w "StarView/Det"
      // 0cb: new java/lang/StringBuilder
      // 0ce: dup
      // 0cf: invokespecial java/lang/StringBuilder.<init> ()V
      // 0d2: ldc_w "GPU delegate: "
      // 0d5: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 0d8: aload 10
      // 0da: invokevirtual java/lang/StringBuilder.append (Ljava/lang/Object;)Ljava/lang/StringBuilder;
      // 0dd: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 0e0: invokestatic android/util/Log.w (Ljava/lang/String;Ljava/lang/String;)I
      // 0e3: pop
      // 0e4: aload 10
      // 0e6: invokevirtual java/lang/Throwable.getMessage ()Ljava/lang/String;
      // 0e9: astore 8
      // 0eb: aload 8
      // 0ed: astore 7
      // 0ef: aload 8
      // 0f1: ifnonnull 0fb
      // 0f4: aload 10
      // 0f6: invokevirtual java/lang/Throwable.toString ()Ljava/lang/String;
      // 0f9: astore 7
      // 0fb: aload 0
      // 0fc: aload 7
      // 0fe: putfield com/starpilot/starview/Detector.gpuError Ljava/lang/String;
      // 101: aload 0
      // 102: getfield com/starpilot/starview/Detector.gpu Lorg/tensorflow/lite/gpu/GpuDelegate;
      // 105: astore 7
      // 107: aload 7
      // 109: ifnull 116
      // 10c: aload 7
      // 10e: invokevirtual org/tensorflow/lite/gpu/GpuDelegate.close ()V
      // 111: goto 116
      // 114: astore 7
      // 116: aload 0
      // 117: aconst_null
      // 118: putfield com/starpilot/starview/Detector.gpu Lorg/tensorflow/lite/gpu/GpuDelegate;
      // 11b: aload 0
      // 11c: new org/tensorflow/lite/Interpreter
      // 11f: dup
      // 120: aload 9
      // 122: new org/tensorflow/lite/Interpreter$Options
      // 125: dup
      // 126: invokespecial org/tensorflow/lite/Interpreter$Options.<init> ()V
      // 129: bipush 4
      // 12a: invokevirtual org/tensorflow/lite/Interpreter$Options.setNumThreads (I)Lorg/tensorflow/lite/Interpreter$Options;
      // 12d: invokespecial org/tensorflow/lite/Interpreter.<init> (Ljava/nio/ByteBuffer;Lorg/tensorflow/lite/Interpreter$Options;)V
      // 130: putfield com/starpilot/starview/Detector.interp Lorg/tensorflow/lite/Interpreter;
      // 133: aload 0
      // 134: ldc_w "cpu"
      // 137: putfield com/starpilot/starview/Detector.accel Ljava/lang/String;
      // 13a: aload 0
      // 13b: getfield com/starpilot/starview/Detector.interp Lorg/tensorflow/lite/Interpreter;
      // 13e: astore 7
      // 140: aload 7
      // 142: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNull (Ljava/lang/Object;)V
      // 145: aload 7
      // 147: invokevirtual org/tensorflow/lite/Interpreter.getOutputTensorCount ()I
      // 14a: istore 4
      // 14c: iload 4
      // 14e: newarray 10
      // 150: astore 8
      // 152: bipush 0
      // 153: istore 2
      // 154: iload 2
      // 155: iload 4
      // 157: if_icmpge 1d3
      // 15a: aload 7
      // 15c: iload 2
      // 15d: invokevirtual org/tensorflow/lite/Interpreter.getOutputTensor (I)Lorg/tensorflow/lite/Tensor;
      // 160: invokeinterface org/tensorflow/lite/Tensor.shape ()[I 1
      // 165: astore 9
      // 167: aload 0
      // 168: getfield com/starpilot/starview/Detector.outs Ljava/util/List;
      // 16b: astore 10
      // 16d: bipush 0
      // 16e: istore 1
      // 16f: aload 10
      // 171: invokeinterface java/util/List.iterator ()Ljava/util/Iterator; 1
      // 176: astore 10
      // 178: aload 10
      // 17a: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 17f: ifeq 1c6
      // 182: aload 10
      // 184: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 189: checkcast kotlin/Triple
      // 18c: astore 11
      // 18e: aload 11
      // 190: invokevirtual kotlin/Triple.getFirst ()Ljava/lang/Object;
      // 193: checkcast java/lang/Number
      // 196: invokevirtual java/lang/Number.intValue ()I
      // 199: aload 9
      // 19b: bipush 1
      // 19c: iaload
      // 19d: if_icmpne 1b7
      // 1a0: aload 11
      // 1a2: invokevirtual kotlin/Triple.getSecond ()Ljava/lang/Object;
      // 1a5: checkcast java/lang/Number
      // 1a8: invokevirtual java/lang/Number.intValue ()I
      // 1ab: aload 9
      // 1ad: bipush 2
      // 1ae: iaload
      // 1af: if_icmpne 1b7
      // 1b2: bipush 1
      // 1b3: istore 3
      // 1b4: goto 1b9
      // 1b7: bipush 0
      // 1b8: istore 3
      // 1b9: iload 3
      // 1ba: ifeq 1c0
      // 1bd: goto 1c8
      // 1c0: iinc 1 1
      // 1c3: goto 178
      // 1c6: bipush -1
      // 1c7: istore 1
      // 1c8: aload 8
      // 1ca: iload 2
      // 1cb: iload 1
      // 1cc: iastore
      // 1cd: iinc 2 1
      // 1d0: goto 154
      // 1d3: aload 0
      // 1d4: aload 8
      // 1d6: putfield com/starpilot/starview/Detector.outOrder [I
      // 1d9: aload 0
      // 1da: invokestatic java/lang/System.nanoTime ()J
      // 1dd: lload 5
      // 1df: lsub
      // 1e0: l2d
      // 1e1: ldc2_w 1000000.0
      // 1e4: ddiv
      // 1e5: putfield com/starpilot/starview/Detector.loadMs D
      // 1e8: return
      // 1e9: new com/starpilot/starview/ModelDownload$Missing
      // 1ec: dup
      // 1ed: getstatic com/starpilot/starview/ModelDownload.INSTANCE Lcom/starpilot/starview/ModelDownload;
      // 1f0: invokevirtual com/starpilot/starview/ModelDownload.getStatus ()Ljava/lang/String;
      // 1f3: invokespecial com/starpilot/starview/ModelDownload$Missing.<init> (Ljava/lang/String;)V
      // 1f6: athrow
   }

   private final float fill(Detector.Frame var1, boolean var2) {
      float var3 = Math.min(640.0F / var1.getW(), 384.0F / var1.getH());
      int var5 = Math.min(640, (int)(var1.getW() * var3));
      int var4 = Math.min(384, (int)(var1.getH() * var3));
      int var6 = var5 * 10000 + var4;
      if (var6 != this.padKey) {
         Arrays.fill(this.pix, 114.0F);
         this.padKey = var6;
      }

      long var7 = (long)DetMasks.INSTANCE.getVersion() << 32 | var6;
      if (this.maskKey[var2] != var7) {
         this.maskBits[var2] = DetMasks.INSTANCE.maskFor((boolean)var2, var5, var4);
         this.maskKey[var2] = var7;
      }

      boolean[] var14 = this.maskBits[var2];
      int[] var15 = new int[var5];

      for (int var19 = 0; var19 < var5; var19++) {
         var15[var19] = Math.min(var1.getW() - 1, (int)(var19 / var3));
      }

      int[] var13 = new int[var5];

      for (int var20 = 0; var20 < var5; var20++) {
         var13[var20] = (var15[var20] >> 1) * var1.getUvPixStride();
      }

      float[] var9 = this.pix;
      Iterable var10 = RangesKt.until(0, this.nThreads);
      var var11 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var10, 10));
      Iterator var12 = var10.iterator();

      while (var12.hasNext()) {
         Detector$$ExternalSyntheticLambda3 var16 = new Detector$$ExternalSyntheticLambda3(
            ((IntIterator)var12).nextInt(), var4, var1, var3, var5, var14, var9, var15, var13, this
         );
         var11.add(var16);
      }

      List var17 = var11;
      Iterator var18 = this.pool.invokeAll(var17).iterator();

      while (var18.hasNext()) {
         ((Future)var18.next()).get();
      }

      ((Buffer)this.input).rewind();
      this.input.asFloatBuffer().put(var9);
      return var3;
   }

   private static final Unit fill$lambda$21$lambda$20(
      int var0, int var1, Detector.Frame var2, float var3, int var4, boolean[] var5, float[] var6, int[] var7, int[] var8, Detector var9
   ) {
      int var12 = var0;

      while (var12 < var1) {
         int var18 = Math.min(var2.getH() - 1, (int)(var12 / var3));
         int var16 = var2.getYStride();
         int var17 = var2.getUvStride();
         int var14 = var12 * 640 * 3;
         int var13 = 0;

         while (var13 < var4) {
            if (var5 != null && var5[var12 * var4 + var13]) {
               var6[var14] = 114.0F;
               var6[var14 + 1] = 114.0F;
               var6[var14 + 2] = 114.0F;
               var0 = var14 + 3;
            } else {
               int var20 = var2.getY()[var7[var13] + var16 * var18];
               int var10 = var8[var13] + (var18 >> 1) * var17;
               int var15 = (var2.getU()[var10] & 255) - 128;
               int var11 = (var2.getV()[var10] & 255) - 128;
               int var19 = ((var20 & 0xFF) - 16) * 298;
               var10 = var11 * 409 + var19 + 128 >> 8;
               if (var10 < 0) {
                  var20 = 0;
               } else {
                  var20 = var10;
                  if (var10 > 255) {
                     var20 = 255;
                  }
               }

               var11 = var19 - var15 * 100 - var11 * 208 + 128 >> 8;
               if (var11 < 0) {
                  var10 = 0;
               } else {
                  var10 = var11;
                  if (var11 > 255) {
                     var10 = 255;
                  }
               }

               var15 = var15 * 516 + var19 + 128 >> 8;
               if (var15 < 0) {
                  var11 = 0;
               } else {
                  var11 = var15;
                  if (var15 > 255) {
                     var11 = 255;
                  }
               }

               var6[var14] = var11;
               var6[var14 + 1] = var10;
               var6[var14 + 2] = var20;
               var0 = var14 + 3;
            }

            var13++;
            var14 = var0;
         }

         var12 += var9.nThreads;
      }

      return Unit.INSTANCE;
   }

   private final Bitmap grey(Detector.Frame var1) {
      int var5 = var1.getW() / 2;
      int var4 = var1.getH() / 2;
      int[] var7 = new int[var5 * var4];

      for (int var2 = 0; var2 < var4; var2++) {
         int var6 = var1.getYStride();

         for (int var3 = 0; var3 < var5; var3++) {
            var7[var2 * var5 + var3] = (var1.getY()[var3 * 2 + var2 * 2 * var6] & 255) * 65793 | 0xFF000000;
         }
      }

      Bitmap var8 = Bitmap.createBitmap(var7, var5, var4, Config.ARGB_8888);
      Intrinsics.checkNotNullExpressionValue(var8, "createBitmap(...)");
      return var8;
   }

   private final void groundObjects(Detector.Source var1, List<Detector.Track> var2, Detector.Frame var3) {
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

                        for (Detector.OCand var160 : CollectionsKt.take(CollectionsKt.sortedWith(var139, new Comparator() {
                           @Override
                           public final int compare(T var1, T var2x) {
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
                        var153 = ((Detector.Pend)var153).getTr();
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
                           boolean var107;
                           if (!(var158.getX0() < var3.getW() * 0.015F)
                              && !(var158.getX1() > var3.getW() * 0.985F)
                              && !(var158.getY1() > var3.getH() * 0.97F)
                              && !(var158.getY0() < var3.getH() * 0.01F)) {
                              var107 = 0;
                           } else {
                              var107 = 1;
                           }

                           if (this.isVehicle(var18) && !var107 && var158.getMisses() == 0) {
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

   private final int group(int var1) {
      switch (var1) {
         case 0:
            var1 = 1;
            break;
         case 1:
         case 3:
            var1 = 2;
            break;
         case 2:
         case 5:
         case 7:
            var1 = 0;
            break;
         case 4:
         case 6:
         default:
            var1 += 3;
      }

      return var1;
   }

   private final kotlin.Pair<Float, Float> headlightEnds(Detector.Frame var1, float var2, float var3, float var4, float var5) {
      float[] var16 = new float[60];
      int var11 = 0;
      float[] var15 = new float[2];

      for (int var9 = 0; var9 < 2; var9++) {
         float var6 = 0.0F;

         for (int var10 = 0; var10 < 6; var10++) {
            int var12 = 0;

            while (var12 < 5) {
               float var8 = var12 * 0.23F / 4.0F;
               float var7;
               if (var9 == 0) {
                  var7 = 0.02F;
               } else {
                  var7 = 0.75F;
               }

               int var14 = RangesKt.coerceIn((int)(var2 + (var4 - var2) * (var8 + var7)), 0, var1.getW() - 1);
               int var13 = RangesKt.coerceIn((int)(var3 + (var5 - var3) * (var10 * 0.45F / 5.0F + 0.3F)), 0, var1.getH() - 1);
               var8 = var1.getY()[var1.getYStride() * var13 + var14] & 255;
               var13 = (var13 >> 1) * var1.getUvStride() + (var14 >> 1) * var1.getUvPixStride();
               var7 = var6;
               if (Math.abs((var1.getU()[var13] & 255) - 128) + Math.abs((var1.getV()[var13] & 255) - 128) < 40) {
                  var7 = var6;
                  if (var8 > var6) {
                     var7 = var8;
                  }
               }

               var16[var11] = var8;
               var12++;
               var11++;
               var6 = var7;
            }
         }

         var15[var9] = var6;
      }

      ArraysKt.sort(var16);
      var2 = var16[30];
      return new kotlin.Pair<>(var15[0] - var2, var15[1] - var2);
   }

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
                  float var5 = ((Detector.Track)var16).getGx();
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

   private final float iou(Detector.Det var1, Detector.Det var2) {
      float var3 = Math.max(0.0F, Math.min(var1.getX1(), var2.getX1()) - Math.max(var1.getX0(), var2.getX0()))
         * Math.max(0.0F, Math.min(var1.getY1(), var2.getY1()) - Math.max(var1.getY0(), var2.getY0()));
      return var3
         / ((var1.getX1() - var1.getX0()) * (var1.getY1() - var1.getY0()) + (var2.getX1() - var2.getX0()) * (var2.getY1() - var2.getY0()) - var3 + 1.0E-6F);
   }

   private final boolean isVehicle(int var1) {
      boolean var2;
      if (var1 != 2 && var1 != 5 && var1 != 7) {
         var2 = false;
      } else {
         var2 = true;
      }

      return var2;
   }

   private final int lampColour(Detector.Frame var1, float var2, float var3, float var4, float var5) {
      float var7 = -1.0F;
      int var12 = 0;
      int var11 = 0;
      int var10 = 0;

      for (int var8 = 0; var8 < 12; var8++) {
         int var9 = 0;

         while (var9 < 6) {
            int var15 = RangesKt.coerceIn((int)(var2 + (var4 - var2) * (var9 * 0.7F / 5.0F + 0.15F)), 0, var1.getW() - 1);
            int var14 = RangesKt.coerceIn((int)(var3 + (var5 - var3) * (var8 * 0.9F / 11.0F + 0.05F)), 0, var1.getH() - 1);
            int var13 = (var1.getY()[var1.getYStride() * var14 + var15] & 255) - 16;
            float var6 = var7;
            if (!(var13 < var7)) {
               var11 = (var14 >> 1) * var1.getUvStride() + (var15 >> 1) * var1.getUvPixStride();
               var10 = (var1.getU()[var11] & 255) - 128;
               var11 = (var1.getV()[var11] & 255) - 128;
               var14 = var13 * 298;
               var6 = var13;
               var12 = this.clamp(var11 * 409 + var14 + 128 >> 8);
               var11 = this.clamp(var14 - var10 * 100 - var11 * 208 + 128 >> 8);
               var10 = this.clamp(var10 * 516 + var14 + 128 >> 8);
            }

            var9++;
            var7 = var6;
         }
      }

      if (var7 < 60.0F) {
         return -1;
      } else {
         byte var16;
         if (var12 > var11 + 40 && var12 > var10 + 40) {
            if (var11 > var10 + 25 && var11 > var12 * 0.55F) {
               var16 = 1;
            } else {
               var16 = 0;
            }
         } else if (var11 >= var12 - 10 && var11 > var10 - 30) {
            var16 = 2;
         } else {
            var16 = -1;
         }

         return var16;
      }
   }

   private final void loop() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.NullPointerException: Cannot invoke "org.jetbrains.java.decompiler.code.cfg.ExceptionRangeCFG.isCircular()" because "range" is null
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.graphToStatement(DomHelper.java:84)
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:203)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 000: aload 0
      // 001: getfield com/starpilot/starview/Detector.running Z
      // 004: ifeq 651
      // 007: aload 0
      // 008: getfield com/starpilot/starview/Detector.roadSrc Lcom/starpilot/starview/Detector$Source;
      // 00b: astore 17
      // 00d: bipush 0
      // 00e: istore 5
      // 010: bipush 0
      // 011: istore 6
      // 013: bipush 2
      // 014: anewarray 30
      // 017: dup
      // 018: bipush 0
      // 019: aload 17
      // 01b: aastore
      // 01c: dup
      // 01d: bipush 1
      // 01e: aload 0
      // 01f: getfield com/starpilot/starview/Detector.wideSrc Lcom/starpilot/starview/Detector$Source;
      // 022: aastore
      // 023: invokestatic kotlin/collections/CollectionsKt.listOf ([Ljava/lang/Object;)Ljava/util/List;
      // 026: checkcast java/lang/Iterable
      // 029: astore 18
      // 02b: new java/util/ArrayList
      // 02e: dup
      // 02f: invokespecial java/util/ArrayList.<init> ()V
      // 032: checkcast java/util/Collection
      // 035: astore 17
      // 037: aload 18
      // 039: invokeinterface java/lang/Iterable.iterator ()Ljava/util/Iterator; 1
      // 03e: astore 18
      // 040: aload 18
      // 042: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 047: ifeq 07c
      // 04a: aload 18
      // 04c: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 051: astore 19
      // 053: aload 19
      // 055: checkcast com/starpilot/starview/Detector$Source
      // 058: invokevirtual com/starpilot/starview/Detector$Source.getFrame ()Ljava/util/concurrent/atomic/AtomicReference;
      // 05b: invokevirtual java/util/concurrent/atomic/AtomicReference.get ()Ljava/lang/Object;
      // 05e: ifnull 067
      // 061: bipush 1
      // 062: istore 4
      // 064: goto 06a
      // 067: bipush 0
      // 068: istore 4
      // 06a: iload 4
      // 06c: ifeq 040
      // 06f: aload 17
      // 071: aload 19
      // 073: invokeinterface java/util/Collection.add (Ljava/lang/Object;)Z 2
      // 078: pop
      // 079: goto 040
      // 07c: aload 17
      // 07e: checkcast java/util/List
      // 081: astore 17
      // 083: aload 17
      // 085: checkcast java/lang/Iterable
      // 088: invokeinterface java/lang/Iterable.iterator ()Ljava/util/Iterator; 1
      // 08d: astore 19
      // 08f: aload 19
      // 091: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 096: ifne 0a3
      // 099: aconst_null
      // 09a: astore 17
      // 09c: iload 5
      // 09e: istore 4
      // 0a0: goto 0fc
      // 0a3: aload 19
      // 0a5: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 0aa: astore 17
      // 0ac: aload 19
      // 0ae: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 0b3: ifne 0bd
      // 0b6: iload 5
      // 0b8: istore 4
      // 0ba: goto 0fc
      // 0bd: aload 17
      // 0bf: checkcast com/starpilot/starview/Detector$Source
      // 0c2: invokevirtual com/starpilot/starview/Detector$Source.getLastRunNs ()J
      // 0c5: lstore 10
      // 0c7: iload 6
      // 0c9: istore 4
      // 0cb: aload 19
      // 0cd: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 0d2: astore 18
      // 0d4: aload 18
      // 0d6: checkcast com/starpilot/starview/Detector$Source
      // 0d9: invokevirtual com/starpilot/starview/Detector$Source.getLastRunNs ()J
      // 0dc: lstore 14
      // 0de: lload 10
      // 0e0: lstore 12
      // 0e2: lload 10
      // 0e4: lload 14
      // 0e6: lcmp
      // 0e7: ifle 0f2
      // 0ea: aload 18
      // 0ec: astore 17
      // 0ee: lload 14
      // 0f0: lstore 12
      // 0f2: aload 19
      // 0f4: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 0f9: ifne 64a
      // 0fc: aload 17
      // 0fe: checkcast com/starpilot/starview/Detector$Source
      // 101: astore 20
      // 103: aload 20
      // 105: ifnull 122
      // 108: aload 20
      // 10a: invokevirtual com/starpilot/starview/Detector$Source.getFrame ()Ljava/util/concurrent/atomic/AtomicReference;
      // 10d: astore 17
      // 10f: aload 17
      // 111: ifnull 122
      // 114: aload 17
      // 116: aconst_null
      // 117: invokevirtual java/util/concurrent/atomic/AtomicReference.getAndSet (Ljava/lang/Object;)Ljava/lang/Object;
      // 11a: checkcast com/starpilot/starview/Detector$Frame
      // 11d: astore 18
      // 11f: goto 125
      // 122: aconst_null
      // 123: astore 18
      // 125: aload 20
      // 127: ifnull 603
      // 12a: aload 18
      // 12c: ifnonnull 132
      // 12f: goto 603
      // 132: aload 20
      // 134: invokestatic java/lang/System.nanoTime ()J
      // 137: invokevirtual com/starpilot/starview/Detector$Source.setLastRunNs (J)V
      // 13a: aload 0
      // 13b: invokespecial com/starpilot/starview/Detector.ensureInterpreter ()V
      // 13e: invokestatic java/lang/System.nanoTime ()J
      // 141: lstore 14
      // 143: aload 0
      // 144: aload 18
      // 146: aload 20
      // 148: invokevirtual com/starpilot/starview/Detector$Source.getWide ()Z
      // 14b: invokespecial com/starpilot/starview/Detector.fill (Lcom/starpilot/starview/Detector$Frame;Z)F
      // 14e: fstore 3
      // 14f: invokestatic java/lang/System.nanoTime ()J
      // 152: lstore 12
      // 154: aload 0
      // 155: getfield com/starpilot/starview/Detector.firstRun Z
      // 158: ifeq 162
      // 15b: aload 0
      // 15c: ldc_w "first GPU run: compiling (can take a minute the first time)…"
      // 15f: putfield com/starpilot/starview/Detector.status Ljava/lang/String;
      // 162: aload 0
      // 163: getfield com/starpilot/starview/Detector.interp Lorg/tensorflow/lite/Interpreter;
      // 166: astore 17
      // 168: aload 17
      // 16a: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNull (Ljava/lang/Object;)V
      // 16d: aload 0
      // 16e: getfield com/starpilot/starview/Detector.outOrder [I
      // 171: astore 22
      // 173: aload 22
      // 175: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNull (Ljava/lang/Object;)V
      // 178: new java/util/HashMap
      // 17b: astore 19
      // 17d: aload 19
      // 17f: invokespecial java/util/HashMap.<init> ()V
      // 182: bipush 0
      // 183: istore 5
      // 185: aload 22
      // 187: arraylength
      // 188: istore 6
      // 18a: iload 5
      // 18c: iload 6
      // 18e: if_icmpge 1c8
      // 191: aload 0
      // 192: getfield com/starpilot/starview/Detector.outs Ljava/util/List;
      // 195: aload 22
      // 197: iload 5
      // 199: iaload
      // 19a: invokeinterface java/util/List.get (I)Ljava/lang/Object; 2
      // 19f: checkcast kotlin/Triple
      // 1a2: invokevirtual kotlin/Triple.getThird ()Ljava/lang/Object;
      // 1a5: checkcast java/nio/ByteBuffer
      // 1a8: astore 21
      // 1aa: aload 21
      // 1ac: invokevirtual java/nio/ByteBuffer.rewind ()Ljava/nio/Buffer;
      // 1af: pop
      // 1b0: aload 19
      // 1b2: checkcast java/util/Map
      // 1b5: iload 5
      // 1b7: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 1ba: aload 21
      // 1bc: invokeinterface java/util/Map.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
      // 1c1: pop
      // 1c2: iinc 5 1
      // 1c5: goto 18a
      // 1c8: aload 0
      // 1c9: getfield com/starpilot/starview/Detector.input Ljava/nio/ByteBuffer;
      // 1cc: invokevirtual java/nio/ByteBuffer.rewind ()Ljava/nio/Buffer;
      // 1cf: pop
      // 1d0: aload 0
      // 1d1: getfield com/starpilot/starview/Detector.input Ljava/nio/ByteBuffer;
      // 1d4: astore 21
      // 1d6: aload 19
      // 1d8: checkcast java/util/Map
      // 1db: astore 19
      // 1dd: aload 17
      // 1df: bipush 1
      // 1e0: anewarray 4
      // 1e3: dup
      // 1e4: bipush 0
      // 1e5: aload 21
      // 1e7: aastore
      // 1e8: aload 19
      // 1ea: invokevirtual org/tensorflow/lite/Interpreter.runForMultipleInputsOutputs ([Ljava/lang/Object;Ljava/util/Map;)V
      // 1ed: invokestatic java/lang/System.nanoTime ()J
      // 1f0: lstore 10
      // 1f2: aload 0
      // 1f3: getfield com/starpilot/starview/Detector.firstRun Z
      // 1f6: ifeq 20d
      // 1f9: aload 0
      // 1fa: lload 10
      // 1fc: lload 12
      // 1fe: lsub
      // 1ff: l2d
      // 200: ldc2_w 1000000.0
      // 203: ddiv
      // 204: putfield com/starpilot/starview/Detector.firstRunMs D
      // 207: aload 0
      // 208: iload 4
      // 20a: putfield com/starpilot/starview/Detector.firstRun Z
      // 20d: aload 0
      // 20e: aload 20
      // 210: aload 0
      // 211: fload 3
      // 212: aload 18
      // 214: invokevirtual com/starpilot/starview/Detector$Frame.getW ()I
      // 217: aload 18
      // 219: invokevirtual com/starpilot/starview/Detector$Frame.getH ()I
      // 21c: fconst_0
      // 21d: bipush 8
      // 21f: aconst_null
      // 220: invokestatic com/starpilot/starview/Detector.decode$default (Lcom/starpilot/starview/Detector;FIIFILjava/lang/Object;)Ljava/util/List;
      // 223: aload 18
      // 225: invokespecial com/starpilot/starview/Detector.track (Lcom/starpilot/starview/Detector$Source;Ljava/util/List;Lcom/starpilot/starview/Detector$Frame;)V
      // 228: aload 0
      // 229: invokestatic java/lang/System.nanoTime ()J
      // 22c: putfield com/starpilot/starview/Detector.detsAtNs J
      // 22f: aload 0
      // 230: getfield com/starpilot/starview/Detector.snapWant I
      // 233: istore 6
      // 235: aload 20
      // 237: invokevirtual com/starpilot/starview/Detector$Source.getWide ()Z
      // 23a: ifeq 243
      // 23d: bipush 2
      // 23e: istore 5
      // 240: goto 246
      // 243: bipush 1
      // 244: istore 5
      // 246: iload 6
      // 248: iload 5
      // 24a: if_icmpne 266
      // 24d: aload 0
      // 24e: aload 0
      // 24f: aload 18
      // 251: invokespecial com/starpilot/starview/Detector.grey (Lcom/starpilot/starview/Detector$Frame;)Landroid/graphics/Bitmap;
      // 254: putfield com/starpilot/starview/Detector.snap Landroid/graphics/Bitmap;
      // 257: aload 0
      // 258: aload 20
      // 25a: invokevirtual com/starpilot/starview/Detector$Source.getWide ()Z
      // 25d: putfield com/starpilot/starview/Detector.snapWide Z
      // 260: aload 0
      // 261: iload 4
      // 263: putfield com/starpilot/starview/Detector.snapWant I
      // 266: aload 20
      // 268: invokevirtual com/starpilot/starview/Detector$Source.getWide ()Z
      // 26b: ifne 2bd
      // 26e: aload 0
      // 26f: getfield com/starpilot/starview/Detector.shotReq Lkotlin/jvm/functions/Function1;
      // 272: astore 22
      // 274: aload 22
      // 276: ifnull 2bd
      // 279: aload 0
      // 27a: aconst_null
      // 27b: putfield com/starpilot/starview/Detector.shotReq Lkotlin/jvm/functions/Function1;
      // 27e: aload 0
      // 27f: aload 18
      // 281: invokespecial com/starpilot/starview/Detector.colour (Lcom/starpilot/starview/Detector$Frame;)Landroid/graphics/Bitmap;
      // 284: astore 21
      // 286: aload 21
      // 288: getstatic android/graphics/Bitmap$Config.ARGB_8888 Landroid/graphics/Bitmap$Config;
      // 28b: bipush 1
      // 28c: invokevirtual android/graphics/Bitmap.copy (Landroid/graphics/Bitmap$Config;Z)Landroid/graphics/Bitmap;
      // 28f: astore 19
      // 291: aload 19
      // 293: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNull (Ljava/lang/Object;)V
      // 296: aload 0
      // 297: aload 19
      // 299: aload 18
      // 29b: invokespecial com/starpilot/starview/Detector.drawPath (Landroid/graphics/Bitmap;Lcom/starpilot/starview/Detector$Frame;)V
      // 29e: goto 2a3
      // 2a1: astore 17
      // 2a3: aload 0
      // 2a4: aload 21
      // 2a6: putfield com/starpilot/starview/Detector.lastClean Landroid/graphics/Bitmap;
      // 2a9: aload 19
      // 2ab: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNull (Ljava/lang/Object;)V
      // 2ae: aload 22
      // 2b0: aload 19
      // 2b2: invokeinterface kotlin/jvm/functions/Function1.invoke (Ljava/lang/Object;)Ljava/lang/Object; 2
      // 2b7: pop
      // 2b8: goto 2bd
      // 2bb: astore 17
      // 2bd: aload 20
      // 2bf: invokevirtual com/starpilot/starview/Detector$Source.getWide ()Z
      // 2c2: ifne 2ef
      // 2c5: aload 0
      // 2c6: getfield com/starpilot/starview/Detector.laneModel Lcom/starpilot/starview/LaneModel;
      // 2c9: astore 17
      // 2cb: aload 17
      // 2cd: ifnull 2ef
      // 2d0: aload 17
      // 2d2: lconst_0
      // 2d3: bipush 1
      // 2d4: aconst_null
      // 2d5: invokestatic com/starpilot/starview/LaneModel.wants$default (Lcom/starpilot/starview/LaneModel;JILjava/lang/Object;)Z
      // 2d8: istore 16
      // 2da: iload 16
      // 2dc: ifeq 2ef
      // 2df: aload 17
      // 2e1: aload 0
      // 2e2: aload 18
      // 2e4: invokespecial com/starpilot/starview/Detector.colour (Lcom/starpilot/starview/Detector$Frame;)Landroid/graphics/Bitmap;
      // 2e7: invokevirtual com/starpilot/starview/LaneModel.feed (Landroid/graphics/Bitmap;)V
      // 2ea: goto 2ef
      // 2ed: astore 17
      // 2ef: aload 20
      // 2f1: invokevirtual com/starpilot/starview/Detector$Source.getWide ()Z
      // 2f4: ifeq 321
      // 2f7: aload 0
      // 2f8: invokestatic java/lang/System.nanoTime ()J
      // 2fb: putfield com/starpilot/starview/Detector.wideFrameNs J
      // 2fe: aload 0
      // 2ff: getfield com/starpilot/starview/Detector.wideReq Lkotlin/jvm/functions/Function1;
      // 302: astore 17
      // 304: aload 17
      // 306: ifnull 321
      // 309: aload 0
      // 30a: aconst_null
      // 30b: putfield com/starpilot/starview/Detector.wideReq Lkotlin/jvm/functions/Function1;
      // 30e: aload 17
      // 310: aload 0
      // 311: aload 18
      // 313: invokespecial com/starpilot/starview/Detector.colour (Lcom/starpilot/starview/Detector$Frame;)Landroid/graphics/Bitmap;
      // 316: invokeinterface kotlin/jvm/functions/Function1.invoke (Ljava/lang/Object;)Ljava/lang/Object; 2
      // 31b: pop
      // 31c: goto 321
      // 31f: astore 17
      // 321: invokestatic java/lang/System.nanoTime ()J
      // 324: aload 20
      // 326: invokevirtual com/starpilot/starview/Detector$Source.getAmbientNs ()J
      // 329: lsub
      // 32a: ldc2_w 250000000
      // 32d: lcmp
      // 32e: ifle 344
      // 331: aload 20
      // 333: invokestatic java/lang/System.nanoTime ()J
      // 336: invokevirtual com/starpilot/starview/Detector$Source.setAmbientNs (J)V
      // 339: aload 20
      // 33b: aload 0
      // 33c: aload 18
      // 33e: invokespecial com/starpilot/starview/Detector.ambient (Lcom/starpilot/starview/Detector$Frame;)[I
      // 341: invokevirtual com/starpilot/starview/Detector$Source.setAmbient ([I)V
      // 344: aload 0
      // 345: getfield com/starpilot/starview/Detector.layout Lcom/starpilot/starview/LayoutSeg;
      // 348: astore 17
      // 34a: aload 17
      // 34c: ifnull 3af
      // 34f: aload 0
      // 350: getfield com/starpilot/starview/Detector.telemetry Lcom/starpilot/starview/Telemetry;
      // 353: astore 19
      // 355: aload 20
      // 357: invokevirtual com/starpilot/starview/Detector$Source.getWide ()Z
      // 35a: ifeq 3af
      // 35d: aload 19
      // 35f: ifnull 3af
      // 362: aload 19
      // 364: invokevirtual com/starpilot/starview/Telemetry.getCalValid ()Z
      // 367: ifeq 3af
      // 36a: aload 17
      // 36c: invokestatic java/lang/System.nanoTime ()J
      // 36f: invokevirtual com/starpilot/starview/LayoutSeg.wantsFrame (J)Z
      // 372: ifeq 3af
      // 375: aload 17
      // 377: aload 18
      // 379: invokevirtual com/starpilot/starview/Detector$Frame.getY ()[B
      // 37c: aload 18
      // 37e: invokevirtual com/starpilot/starview/Detector$Frame.getU ()[B
      // 381: aload 18
      // 383: invokevirtual com/starpilot/starview/Detector$Frame.getV ()[B
      // 386: aload 18
      // 388: invokevirtual com/starpilot/starview/Detector$Frame.getYStride ()I
      // 38b: aload 18
      // 38d: invokevirtual com/starpilot/starview/Detector$Frame.getUvStride ()I
      // 390: aload 18
      // 392: invokevirtual com/starpilot/starview/Detector$Frame.getUvPixStride ()I
      // 395: aload 18
      // 397: invokevirtual com/starpilot/starview/Detector$Frame.getW ()I
      // 39a: aload 18
      // 39c: invokevirtual com/starpilot/starview/Detector$Frame.getH ()I
      // 39f: aload 19
      // 3a1: invokevirtual com/starpilot/starview/Telemetry.getCalRpy ()[F
      // 3a4: aload 19
      // 3a6: invokevirtual com/starpilot/starview/Telemetry.getCalHeight ()F
      // 3a9: invokestatic java/lang/System.nanoTime ()J
      // 3ac: invokevirtual com/starpilot/starview/LayoutSeg.feed ([B[B[BIIIII[FFJ)V
      // 3af: aload 0
      // 3b0: getfield com/starpilot/starview/Detector.laneMarks Lcom/starpilot/starview/LaneMarks;
      // 3b3: astore 21
      // 3b5: aload 21
      // 3b7: ifnull 45d
      // 3ba: aload 0
      // 3bb: getfield com/starpilot/starview/Detector.telemetry Lcom/starpilot/starview/Telemetry;
      // 3be: astore 25
      // 3c0: aload 20
      // 3c2: invokevirtual com/starpilot/starview/Detector$Source.getWide ()Z
      // 3c5: ifne 45d
      // 3c8: aload 25
      // 3ca: ifnull 45d
      // 3cd: aload 25
      // 3cf: invokevirtual com/starpilot/starview/Telemetry.getCalValid ()Z
      // 3d2: ifeq 45d
      // 3d5: aload 21
      // 3d7: invokestatic java/lang/System.nanoTime ()J
      // 3da: invokevirtual com/starpilot/starview/LaneMarks.wants (J)Z
      // 3dd: ifeq 45d
      // 3e0: aload 25
      // 3e2: invokevirtual com/starpilot/starview/Telemetry.getLeadOne ()Lcom/starpilot/starview/Lead;
      // 3e5: astore 19
      // 3e7: aload 18
      // 3e9: invokevirtual com/starpilot/starview/Detector$Frame.getY ()[B
      // 3ec: astore 22
      // 3ee: aload 18
      // 3f0: invokevirtual com/starpilot/starview/Detector$Frame.getU ()[B
      // 3f3: astore 17
      // 3f5: aload 18
      // 3f7: invokevirtual com/starpilot/starview/Detector$Frame.getV ()[B
      // 3fa: astore 23
      // 3fc: aload 18
      // 3fe: invokevirtual com/starpilot/starview/Detector$Frame.getYStride ()I
      // 401: istore 6
      // 403: aload 18
      // 405: invokevirtual com/starpilot/starview/Detector$Frame.getUvStride ()I
      // 408: istore 7
      // 40a: aload 18
      // 40c: invokevirtual com/starpilot/starview/Detector$Frame.getUvPixStride ()I
      // 40f: istore 8
      // 411: aload 18
      // 413: invokevirtual com/starpilot/starview/Detector$Frame.getW ()I
      // 416: istore 9
      // 418: aload 18
      // 41a: invokevirtual com/starpilot/starview/Detector$Frame.getH ()I
      // 41d: istore 5
      // 41f: aload 25
      // 421: invokevirtual com/starpilot/starview/Telemetry.getCalRpy ()[F
      // 424: astore 24
      // 426: aload 25
      // 428: invokevirtual com/starpilot/starview/Telemetry.getModel ()Lcom/starpilot/starview/ModelSnapshot;
      // 42b: astore 25
      // 42d: aload 19
      // 42f: invokevirtual com/starpilot/starview/Lead.getStatus ()Z
      // 432: ifeq 43e
      // 435: aload 19
      // 437: invokevirtual com/starpilot/starview/Lead.getDRel ()F
      // 43a: fstore 3
      // 43b: goto 440
      // 43e: fconst_0
      // 43f: fstore 3
      // 440: aload 21
      // 442: aload 22
      // 444: aload 17
      // 446: aload 23
      // 448: iload 6
      // 44a: iload 7
      // 44c: iload 8
      // 44e: iload 9
      // 450: iload 5
      // 452: aload 24
      // 454: aload 25
      // 456: fload 3
      // 457: invokestatic java/lang/System.nanoTime ()J
      // 45a: invokevirtual com/starpilot/starview/LaneMarks.analyze ([B[B[BIIIII[FLcom/starpilot/starview/ModelSnapshot;FJ)V
      // 45d: aload 0
      // 45e: getfield com/starpilot/starview/Detector.prepMs D
      // 461: dconst_0
      // 462: dcmpg
      // 463: ifne 46c
      // 466: bipush 1
      // 467: istore 4
      // 469: goto 46c
      // 46c: iload 4
      // 46e: ifeq 47f
      // 471: lload 12
      // 473: lload 14
      // 475: lsub
      // 476: l2d
      // 477: ldc2_w 1000000.0
      // 47a: ddiv
      // 47b: dstore 1
      // 47c: goto 497
      // 47f: aload 0
      // 480: getfield com/starpilot/starview/Detector.prepMs D
      // 483: ldc2_w 0.8
      // 486: dmul
      // 487: lload 12
      // 489: lload 14
      // 48b: lsub
      // 48c: l2d
      // 48d: ldc2_w 1000000.0
      // 490: ddiv
      // 491: ldc2_w 0.2
      // 494: dmul
      // 495: dadd
      // 496: dstore 1
      // 497: aload 0
      // 498: dload 1
      // 499: putfield com/starpilot/starview/Detector.prepMs D
      // 49c: aload 0
      // 49d: getfield com/starpilot/starview/Detector.inferMs D
      // 4a0: dconst_0
      // 4a1: dcmpg
      // 4a2: ifne 4ab
      // 4a5: bipush 1
      // 4a6: istore 4
      // 4a8: goto 4ae
      // 4ab: bipush 0
      // 4ac: istore 4
      // 4ae: iload 4
      // 4b0: ifeq 4c1
      // 4b3: lload 10
      // 4b5: lload 12
      // 4b7: lsub
      // 4b8: l2d
      // 4b9: ldc2_w 1000000.0
      // 4bc: ddiv
      // 4bd: dstore 1
      // 4be: goto 4d9
      // 4c1: aload 0
      // 4c2: getfield com/starpilot/starview/Detector.inferMs D
      // 4c5: ldc2_w 0.8
      // 4c8: dmul
      // 4c9: lload 10
      // 4cb: lload 12
      // 4cd: lsub
      // 4ce: l2d
      // 4cf: ldc2_w 1000000.0
      // 4d2: ddiv
      // 4d3: ldc2_w 0.2
      // 4d6: dmul
      // 4d7: dadd
      // 4d8: dstore 1
      // 4d9: aload 0
      // 4da: dload 1
      // 4db: putfield com/starpilot/starview/Detector.inferMs D
      // 4de: aload 0
      // 4df: ldc_w "running"
      // 4e2: putfield com/starpilot/starview/Detector.status Ljava/lang/String;
      // 4e5: invokestatic java/lang/System.nanoTime ()J
      // 4e8: lstore 10
      // 4ea: aload 0
      // 4eb: aload 0
      // 4ec: getfield com/starpilot/starview/Detector.fpsWin I
      // 4ef: bipush 1
      // 4f0: iadd
      // 4f1: putfield com/starpilot/starview/Detector.fpsWin I
      // 4f4: lload 10
      // 4f6: aload 0
      // 4f7: getfield com/starpilot/starview/Detector.fpsT0 J
      // 4fa: lsub
      // 4fb: ldc2_w 2000000000
      // 4fe: lcmp
      // 4ff: ifle 523
      // 502: aload 0
      // 503: aload 0
      // 504: getfield com/starpilot/starview/Detector.fpsWin I
      // 507: i2d
      // 508: ldc2_w 1.0E9
      // 50b: dmul
      // 50c: lload 10
      // 50e: aload 0
      // 50f: getfield com/starpilot/starview/Detector.fpsT0 J
      // 512: lsub
      // 513: l2d
      // 514: ddiv
      // 515: putfield com/starpilot/starview/Detector.fps D
      // 518: aload 0
      // 519: bipush 0
      // 51a: putfield com/starpilot/starview/Detector.fpsWin I
      // 51d: aload 0
      // 51e: lload 10
      // 520: putfield com/starpilot/starview/Detector.fpsT0 J
      // 523: aload 20
      // 525: aload 20
      // 527: invokevirtual com/starpilot/starview/Detector$Source.getFpsWin ()I
      // 52a: bipush 1
      // 52b: iadd
      // 52c: invokevirtual com/starpilot/starview/Detector$Source.setFpsWin (I)V
      // 52f: lload 10
      // 531: aload 20
      // 533: invokevirtual com/starpilot/starview/Detector$Source.getFpsT0 ()J
      // 536: lsub
      // 537: ldc2_w 2000000000
      // 53a: lcmp
      // 53b: ifle 5e5
      // 53e: aload 20
      // 540: aload 20
      // 542: invokevirtual com/starpilot/starview/Detector$Source.getFpsWin ()I
      // 545: i2d
      // 546: ldc2_w 1.0E9
      // 549: dmul
      // 54a: lload 10
      // 54c: aload 20
      // 54e: invokevirtual com/starpilot/starview/Detector$Source.getFpsT0 ()J
      // 551: lsub
      // 552: l2d
      // 553: ddiv
      // 554: invokevirtual com/starpilot/starview/Detector$Source.setFps (D)V
      // 557: aload 20
      // 559: bipush 0
      // 55a: invokevirtual com/starpilot/starview/Detector$Source.setFpsWin (I)V
      // 55d: aload 20
      // 55f: lload 10
      // 561: invokevirtual com/starpilot/starview/Detector$Source.setFpsT0 (J)V
      // 564: goto 5e5
      // 567: astore 19
      // 569: aload 19
      // 56b: instanceof com/starpilot/starview/ModelDownload$Missing
      // 56e: ifeq 58f
      // 571: aload 19
      // 573: invokevirtual java/lang/Throwable.getMessage ()Ljava/lang/String;
      // 576: astore 19
      // 578: aload 19
      // 57a: astore 17
      // 57c: aload 19
      // 57e: ifnonnull 586
      // 581: ldc_w ""
      // 584: astore 17
      // 586: aload 0
      // 587: aload 17
      // 589: putfield com/starpilot/starview/Detector.status Ljava/lang/String;
      // 58c: goto 5d6
      // 58f: new java/lang/StringBuilder
      // 592: astore 17
      // 594: aload 17
      // 596: invokespecial java/lang/StringBuilder.<init> ()V
      // 599: ldc_w "StarView/Det"
      // 59c: aload 17
      // 59e: ldc_w "detector: "
      // 5a1: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 5a4: aload 19
      // 5a6: invokevirtual java/lang/StringBuilder.append (Ljava/lang/Object;)Ljava/lang/StringBuilder;
      // 5a9: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 5ac: invokestatic android/util/Log.e (Ljava/lang/String;Ljava/lang/String;)I
      // 5af: pop
      // 5b0: aload 19
      // 5b2: invokevirtual java/lang/Throwable.getMessage ()Ljava/lang/String;
      // 5b5: astore 17
      // 5b7: new java/lang/StringBuilder
      // 5ba: astore 19
      // 5bc: aload 19
      // 5be: invokespecial java/lang/StringBuilder.<init> ()V
      // 5c1: aload 19
      // 5c3: ldc_w "error: "
      // 5c6: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 5c9: aload 17
      // 5cb: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 5ce: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 5d1: astore 17
      // 5d3: goto 586
      // 5d6: aload 0
      // 5d7: invokespecial com/starpilot/starview/Detector.release ()V
      // 5da: ldc2_w 3000
      // 5dd: invokestatic java/lang/Thread.sleep (J)V
      // 5e0: goto 5e5
      // 5e3: astore 17
      // 5e5: aload 20
      // 5e7: invokevirtual com/starpilot/starview/Detector$Source.getFree ()Ljava/util/concurrent/ConcurrentLinkedQueue;
      // 5ea: aload 18
      // 5ec: invokevirtual java/util/concurrent/ConcurrentLinkedQueue.add (Ljava/lang/Object;)Z
      // 5ef: pop
      // 5f0: goto 000
      // 5f3: astore 17
      // 5f5: aload 20
      // 5f7: invokevirtual com/starpilot/starview/Detector$Source.getFree ()Ljava/util/concurrent/ConcurrentLinkedQueue;
      // 5fa: aload 18
      // 5fc: invokevirtual java/util/concurrent/ConcurrentLinkedQueue.add (Ljava/lang/Object;)Z
      // 5ff: pop
      // 600: aload 17
      // 602: athrow
      // 603: aload 0
      // 604: getfield com/starpilot/starview/Detector.lock Ljava/lang/Object;
      // 607: astore 17
      // 609: aload 17
      // 60b: monitorenter
      // 60c: aload 0
      // 60d: getfield com/starpilot/starview/Detector.running Z
      // 610: ifeq 637
      // 613: aload 0
      // 614: getfield com/starpilot/starview/Detector.roadSrc Lcom/starpilot/starview/Detector$Source;
      // 617: invokevirtual com/starpilot/starview/Detector$Source.getFrame ()Ljava/util/concurrent/atomic/AtomicReference;
      // 61a: invokevirtual java/util/concurrent/atomic/AtomicReference.get ()Ljava/lang/Object;
      // 61d: ifnonnull 637
      // 620: aload 0
      // 621: getfield com/starpilot/starview/Detector.wideSrc Lcom/starpilot/starview/Detector$Source;
      // 624: invokevirtual com/starpilot/starview/Detector$Source.getFrame ()Ljava/util/concurrent/atomic/AtomicReference;
      // 627: invokevirtual java/util/concurrent/atomic/AtomicReference.get ()Ljava/lang/Object;
      // 62a: ifnonnull 637
      // 62d: aload 0
      // 62e: getfield com/starpilot/starview/Detector.lock Ljava/lang/Object;
      // 631: ldc2_w 300
      // 634: invokevirtual java/lang/Object.wait (J)V
      // 637: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 63a: astore 18
      // 63c: aload 17
      // 63e: monitorexit
      // 63f: goto 000
      // 642: astore 18
      // 644: aload 17
      // 646: monitorexit
      // 647: aload 18
      // 649: athrow
      // 64a: lload 12
      // 64c: lstore 10
      // 64e: goto 0cb
      // 651: return
   }

   private final void mergeObjects() {
      long var3 = System.currentTimeMillis();
      Iterable var7 = CollectionsKt.listOf(new Detector.Source[]{this.roadSrc, this.wideSrc});
      var var6 = new ArrayList();

      for (var var7_it : var7) {

         var7 = var7_it;
         boolean var2;
         if (var3 - ((Detector.Source)var7).getAtMs() < 700L) {
            var2 = true;
         } else {
            var2 = false;
         }

         if (var2) {
            var6.add(var7);
         }
      }

      List var20 = var6;
      Iterator var16 = var20.iterator();
      Float var17;
      if (!var16.hasNext()) {
         var17 = null;
      } else {
         float var1 = ((Detector.Source)var16.next()).getFovTan();

         while (var16.hasNext()) {
            var1 = Math.max(var1, ((Detector.Source)var16.next()).getFovTan());
         }

         var17 = var1;
      }

      float var12;
      if (var17 != null) {
         var12 = var17;
      } else {
         var12 = 0.5F;
      }

      this.fovTan = var12;
      boolean var5 = var20.contains(this.roadSrc);
      var12 = this.roadSrc.getFovTan();
      var6 = new ArrayList();
      if (var5) {
         var6.addAll(this.roadSrc.getObjects());
      }

      if (var20.contains(this.wideSrc)) {
         Iterator var9 = this.wideSrc.getObjects().iterator();

         while (true) {
            boolean var14 = false;
            if (!var9.hasNext()) {
               break;
            }

            Detector.Obj var21 = (Detector.Obj)var9.next();
            if (!var5 || !(var21.getX() > 6.0F) || !(Math.abs(var21.getY()) < var21.getX() * var12 * 0.92F)) {
               var var10 = var6;
               if (!(var10 instanceof Collection) || !var10.isEmpty()) {
                  Iterator var22 = var10.iterator();

                  while (true) {
                     if (!var22.hasNext()) {
                        var14 = false;
                        break;
                     }

                     Detector.Obj var11 = (Detector.Obj)var22.next();
                     if (this.group(var11.getCls()) == this.group(var21.getCls())
                        && Math.abs(var11.getX() - var21.getX()) < var21.getX() * 0.2F + 4.0F
                        && Math.abs(var11.getY() - var21.getY()) < var21.getX() * 0.1F + 2.5F) {
                        var14 = true;
                     } else {
                        var14 = false;
                     }

                     if (var14) {
                        var14 = true;
                        break;
                     }
                  }
               }

               if (!var14) {
                  var6.add(var21);
               }
            }
         }
      }

      this.objects = var6;
      if (var20.isEmpty()) {
         var3 = 0L;
      }

      this.objectsAtMs = var3;
   }

   private final int need(int var1) {
      byte var2;
      switch (var1) {
         case 0:
            var2 = 4;
            break;
         case 2:
         case 5:
         case 7:
            var2 = 2;
            break;
         default:
            var2 = 3;
      }

      return var2;
   }

   private final float orientHeading(Detector.Track var1) {
      float[] var9 = var1.getOHist();
      int var7 = var9.length;
      int var5 = 0;
      int var4 = 0;
      int var8 = var9.length;

      while (var4 < var8) {
         int var6 = var5;
         if (var9[var4] > var9[var5]) {
            var6 = var4;
         }

         var4++;
         var5 = var6;
      }

      float var3 = 0.0F;
      float var2 = 0.0F;

      for (int var10 = -1; var10 < 2; var10++) {
         int var11 = (var5 + var10 + var7) % var7;
         var3 += var9[var11] * (float)Math.cos(CarOrient.Companion.getCENTRES()[var11]);
         var2 += var9[var11] * (float)Math.sin(CarOrient.Companion.getCENTRES()[var11]);
      }

      return this.atan2(var2, var3);
   }

   private final boolean orientReady(Detector.Track var1) {
      boolean var10 = this.useOrient;
      boolean var11 = false;
      if (var10 && var1.getON() >= 3) {
         float[] var12 = var1.getOHist();
         float var2 = 0.0F;
         int var7 = 0;
         int var6 = 0;
         int var9 = var12.length;

         while (var6 < var9) {
            var2 += var12[var6];
            int var8 = var7;
            if (var12[var6] > var12[var7]) {
               var8 = var6;
            }

            var6++;
            var7 = var8;
         }

         var6 = var12.length;
         float var3 = var12[var7];
         float var5 = var12[(var7 + 1) % var6];
         float var4 = var12[(var7 + var6 - 1) % var6];
         var10 = var11;
         if (var2 > 0.001F) {
            var10 = var11;
            if ((var3 + var5 + var4) / var2 > 0.55F) {
               var10 = true;
            }
         }

         return var10;
      } else {
         return false;
      }
   }

   private static final Thread pool$lambda$19(Runnable var0) {
      Thread var1 = new Thread(var0, "starview-det-prep");
      var1.setDaemon(true);
      return var1;
   }

   private final Triple<Float, Float, Float> redThirds(Detector.Frame var1, float var2, float var3, float var4, float var5) {
      float[] var15 = new float[3];

      for (int var6 = 0; var6 < 3; var6++) {
         float[] var14 = new float[40];
         int var8 = 0;

         for (int var7 = 0; var7 < 5; var7++) {
            for (int var9 = 0; var9 < 8; var8++) {
               int var10 = RangesKt.coerceIn((int)(var2 + (var4 - var2) * ((var6 + (var9 * 0.9F / 7.0F + 0.05F)) / 3.0F)), 0, var1.getW() - 1);
               int var12 = RangesKt.coerceIn((int)(var3 + (var5 - var3) * (var7 * 0.4F / 4.0F + 0.25F)), 0, var1.getH() - 1);
               int var11 = var1.getY()[var1.getYStride() * var12 + var10];
               var12 = (var12 >> 1) * var1.getUvStride() + (var10 >> 1) * var1.getUvPixStride();
               var10 = (var1.getU()[var12] & 255) - 128;
               int var13 = (var1.getV()[var12] & 255) - 128;
               var12 = ((var11 & 0xFF) - 16) * 298;
               var11 = this.clamp(var13 * 409 + var12 + 128 >> 8);
               var13 = this.clamp(var12 - var10 * 100 - var13 * 208 + 128 >> 8);
               var10 = this.clamp(var10 * 516 + var12 + 128 >> 8);
               var14[var8] = var11 - (var13 + var10) / 2.0F;
               var9++;
            }
         }

         ArraysKt.sort(var14);
         var15[var6] = (var14[39] + var14[38] + var14[37]) / 3.0F - var14[20];
      }

      return new Triple<>(var15[0], var15[1], var15[2]);
   }

   private final void release() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:385)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:551)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1057)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:572)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield com/starpilot/starview/Detector.interp Lorg/tensorflow/lite/Interpreter;
      // 04: astore 1
      // 05: aload 1
      // 06: ifnull 11
      // 09: aload 1
      // 0a: invokevirtual org/tensorflow/lite/Interpreter.close ()V
      // 0d: goto 11
      // 10: astore 1
      // 11: aload 0
      // 12: aconst_null
      // 13: putfield com/starpilot/starview/Detector.interp Lorg/tensorflow/lite/Interpreter;
      // 16: aload 0
      // 17: getfield com/starpilot/starview/Detector.gpu Lorg/tensorflow/lite/gpu/GpuDelegate;
      // 1a: astore 1
      // 1b: aload 1
      // 1c: ifnull 27
      // 1f: aload 1
      // 20: invokevirtual org/tensorflow/lite/gpu/GpuDelegate.close ()V
      // 23: goto 27
      // 26: astore 1
      // 27: aload 0
      // 28: aconst_null
      // 29: putfield com/starpilot/starview/Detector.gpu Lorg/tensorflow/lite/gpu/GpuDelegate;
      // 2c: aload 0
      // 2d: getfield com/starpilot/starview/Detector.orient Lcom/starpilot/starview/CarOrient;
      // 30: invokevirtual com/starpilot/starview/CarOrient.close ()V
      // 33: return
   }

   private final Triple<Integer, Integer, Integer> rgb(Detector.Frame var1, int var2, int var3) {
      int var4 = var1.getY()[var1.getYStride() * var3 + var2];
      var3 = (var3 >> 1) * var1.getUvStride() + (var2 >> 1) * var1.getUvPixStride();
      var2 = (var1.getU()[var3] & 255) - 128;
      var3 = (var1.getV()[var3] & 255) - 128;
      var4 = ((var4 & 0xFF) - 16) * 298;
      return new Triple<>(
         this.clamp(var3 * 409 + var4 + 128 >> 8), this.clamp(var4 - var2 * 100 - var3 * 208 + 128 >> 8), this.clamp(var2 * 516 + var4 + 128 >> 8)
      );
   }

   private final int roofProfile(Detector.Frame var1, float var2, float var3, float var4, float var5) {
      float var6 = var5 - var3;
      byte var11 = 0;
      if (!(var6 < 24.0F) && !(var4 - var2 < 60.0F)) {
         int var7 = 0;
         int var9 = 0;

         int var8;
         for (var8 = 0; var7 < 5; var7++) {
            for (int var10 = 0; var10 < 5; var10++) {
               int var12 = RangesKt.coerceIn((int)((var4 - var2) * (var10 * 0.8F / 4.0F + 0.1F) + var2), 0, var1.getW() - 1);
               int var13 = RangesKt.coerceIn((int)(var3 + (var7 * 0.8F / 4.0F + 0.1F) * var6), 0, var1.getH() - 1);
               var9 += var1.getY()[var1.getYStride() * var13 + var12] & 255;
               var8++;
            }
         }

         if (var9 / var8 < 45) {
            return 0;
         } else {
            var5 = roofProfile$topEdge(var2, var4, var1, var3, var6, 0.04F, 0.22F);
            var2 = roofProfile$topEdge(var2, var4, var1, var3, var6, 0.78F, 0.96F);
            if (!Float.isNaN(var5) && !Float.isNaN(var2)) {
               var2 = var5 - var2;
               byte var17;
               if (var2 > 0.12F) {
                  var17 = 1;
               } else {
                  var17 = var11;
                  if (var2 < -0.12F) {
                     var17 = -1;
                  }
               }

               return var17;
            } else {
               return 0;
            }
         }
      } else {
         return 0;
      }
   }

   private static final float roofProfile$topEdge(float var0, float var1, Detector.Frame var2, float var3, float var4, float var5, float var6) {
      ArrayList var15 = new ArrayList();

      for (int var9 = 0; var9 < 6; var9++) {
         int var13 = RangesKt.coerceIn((int)((var1 - var0) * (var5 + (var6 - var5) * var9 / 5.0F) + var0), 0, var2.getW() - 1);
         int var11 = -1;
         float var8 = Float.NaN;
         int var10 = 0;

         float var7;
         while (true) {
            var7 = var8;
            if (var10 >= 40) {
               break;
            }

            int var14 = RangesKt.coerceIn((int)(0.75F * var4 * var10 / 40 + var3), 0, var2.getH() - 1);
            int var12 = var2.getY()[var2.getYStride() * var14 + var13] & 255;
            if (var11 >= 0 && Math.abs(var12 - var11) > 28) {
               var7 = (var14 - var3) / var4;
               break;
            }

            var11 = var12;
            var10++;
         }

         if (!Float.isNaN(var7)) {
            var15.add(var7);
         }
      }

      if (var15.size() < 3) {
         return Float.NaN;
      } else {
         CollectionsKt.sort(var15);
         Object var16 = var15.get(var15.size() / 2);
         Intrinsics.checkNotNullExpressionValue(var16, "get(...)");
         return ((Number)var16).floatValue();
      }
   }

   private final kotlin.Pair<Float, Float> sideLamps(Detector.Frame var1, float var2, float var3, float var4, float var5) {
      float[] var17 = new float[2];
      int[] var16 = new int[]{0, 2};

      for (int var7 = 0; var7 < 2; var7++) {
         int var11 = var16[var7];
         float[] var18 = new float[30];
         int var9 = 0;

         for (int var8 = 0; var8 < 5; var8++) {
            for (int var10 = 0; var10 < 6; var9++) {
               int var14 = RangesKt.coerceIn((int)(var2 + (var4 - var2) * ((var11 + (var10 * 0.9F / 5.0F + 0.05F)) / 3.0F)), 0, var1.getW() - 1);
               int var13 = RangesKt.coerceIn((int)(var3 + (var5 - var3) * (var8 * 0.4F / 4.0F + 0.25F)), 0, var1.getH() - 1);
               int var12 = (var1.getY()[var1.getYStride() * var13 + var14] & 255) - 16;
               var14 = (var13 >> 1) * var1.getUvStride() + (var14 >> 1) * var1.getUvPixStride();
               byte var19 = var1.getU()[var14];
               int var15 = var1.getV()[var14];
               var14 = var12 * 298;
               var15 = this.clamp(((var15 & 0xFF) - 128) * 409 + var14 + 128 >> 8);
               float var6;
               if (var15 - this.clamp(((var19 & 255) - 128) * 516 + var14 + 128 >> 8) > 25 && var15 > 80) {
                  var6 = var12;
               } else {
                  var6 = 0.0F;
               }

               var18[var9] = var6;
               var10++;
            }
         }

         ArraysKt.sort(var18);
         var17[var7] = (var18[29] + var18[28] + var18[27]) / 3.0F;
      }

      return new kotlin.Pair<>(var17[0], var17[1]);
   }

   private static final void start$lambda$3(Detector var0) {
      var0.loop();
   }

   private final float symmetry(Detector.Frame var1, float var2, float var3, float var4, float var5) {
      float var6 = var4 - var2;
      float var7 = var5 - var3;
      if (!(var6 < 30.0F) && !(var7 < 20.0F)) {
         var5 = 0.0F;
         int var11 = 0;
         float[] var17 = new float[100];
         float[] var16 = new float[100];

         for (int var10 = 0; var10 < 10; var10++) {
            for (int var12 = 0; var12 < 10; var12++) {
               int var14 = RangesKt.coerceIn((int)(var3 + (var10 * 0.7F / 9.0F + 0.15F) * var7), 0, var1.getH() - 1);
               float var8 = var12 * 0.4F / 9.0F + 0.05F;
               int var13 = RangesKt.coerceIn((int)(var2 + var6 * var8), 0, var1.getW() - 1);
               int var15 = RangesKt.coerceIn((int)(var4 - var6 * var8), 0, var1.getW() - 1);
               var8 = var1.getY()[var1.getYStride() * var14 + var13] & 255;
               float var9 = var1.getY()[var1.getYStride() * var14 + var15] & 255;
               var17[var11] = var8;
               var16[var11] = var9;
               var5 += var8 + var9;
               var11++;
            }
         }

         var4 = var5 / (var11 * 2);
         var3 = 0.0F;
         var2 = 0.0F;

         for (int var23 = 0; var23 < var11; var23++) {
            var3 += Math.abs(var17[var23] - var16[var23]);
            var2 += Math.abs(var17[var23] - var4) + Math.abs(var16[var23] - var4);
         }

         return 1.0F - var3 / Math.max(var2, 1.0F);
      } else {
         return 0.0F;
      }
   }

   private final boolean tailLights(Detector.Frame var1, float var2, float var3, float var4, float var5) {
      float[] var14 = new float[96];
      int var7 = 0;

      for (int var6 = 0; var6 < 6; var6++) {
         for (int var8 = 0; var8 < 16; var7++) {
            int var9 = RangesKt.coerceIn((int)(var2 + (var4 - var2) * (var8 * 0.94F / 15.0F + 0.03F)), 0, var1.getW() - 1);
            int var11 = RangesKt.coerceIn((int)(var3 + (var5 - var3) * (var6 * 0.3F / 5.0F + 0.3F)), 0, var1.getH() - 1);
            int var10 = var1.getY()[var1.getYStride() * var11 + var9];
            var11 = (var11 >> 1) * var1.getUvStride() + (var9 >> 1) * var1.getUvPixStride();
            var9 = (var1.getU()[var11] & 255) - 128;
            var11 = (var1.getV()[var11] & 255) - 128;
            int var12 = ((var10 & 0xFF) - 16) * 298;
            var10 = this.clamp(var11 * 409 + var12 + 128 >> 8);
            var11 = this.clamp(var12 - var9 * 100 - var11 * 208 + 128 >> 8);
            var9 = this.clamp(var9 * 516 + var12 + 128 >> 8);
            var14[var7] = var10 - (var11 + var9) / 2.0F;
            var8++;
         }
      }

      ArraysKt.sort(var14);
      boolean var13;
      if ((var14[95] + var14[94] + var14[93] + var14[92]) / 4.0F - var14[48] > 12.0F) {
         var13 = true;
      } else {
         var13 = false;
      }

      return var13;
   }

   private final void track(Detector.Source var1, List<Detector.Det> var2, Detector.Frame var3) {
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
                  var60 = (boolean)1;
               } else {
                  Iterator var106 = var24.iterator();

                  while (true) {
                     if (!var106.hasNext()) {
                        var60 = (boolean)1;
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
                        var60 = (boolean)0;
                        break;
                     }
                  }
               }

               var68 = var32;
               if (var60) {
                  var60 = 1;
                  break label295;
               }
            } else {
               var68 = var32;
            }

            var60 = 0;
            var32 = var68;
         }

         if (var60) {
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
                  public final int compare(T var1, T var2x) {
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
               var97 = (String)var30.next();
               Intrinsics.checkNotNullExpressionValue(var97, var71);
               Detector$track$Pair var99 = (Detector$track$Pair)var97;
               if (!var29.contains(var99.getTr()) && !var28.contains(var99.getD())) {
                  track$take(var29, var28, var5, var15, this, var99.getTr(), var99.getD(), 0.7F, true);
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
                  Detector.Track var117 = (Detector.Track)var97;
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
                        track$take(var29, var28, var5, var15, this, var117, var112, 0.5F, false);
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
               Detector.Track var84 = (Detector.Track)var21;
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

            for (Detector.Det var37 : var27) {
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
            List var86 = CollectionsKt.sortedWith(var109, new Comparator(new Comparator() {
               @Override
               public final int compare(T var1, T var2) {
                  return ComparisonsKt.compareValues(((Detector.Track)var2).getHits(), ((Detector.Track)var1).getHits());
               }
            }) {
               final Comparator $this_thenBy;

               {
                  this.$this_thenBy = var1;
               }

               @Override
               public final int compare(T var1, T var2) {
                  int var3 = this.$this_thenBy.compare(var1, var2);
                  if (var3 == 0) {
                     var3 = ComparisonsKt.compareValues(((Detector.Track)var1).getId(), ((Detector.Track)var2).getId());
                  }

                  return var3;
               }
            });
            var89 = new ArrayList();

            for (Detector.Track var103 : var86) {
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

               if (var64) {
                  var89.add(var87);
               }
            }

            List var78 = var89;
            Iterable var40 = var78;
            var89 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var40, 10));

            for (Detector.Track var105 : var40) {
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

   private static final boolean track$lambda$28(Detector var0, boolean var1, Detector.Frame var2, Detector.Track var3) {
      Intrinsics.checkNotNullParameter(var3, "it");
      int var5 = var3.getMisses();
      int var6 = var3.getHits();
      int var4 = var0.need(var3.getCls());
      boolean var7 = true;
      byte var9;
      if (var6 < var4) {
         var9 = 1;
      } else if (var1) {
         var9 = 12;
      } else {
         var9 = 10;
      }

      var1 = var7;
      if (var5 <= var9) {
         var1 = var7;
         if (!(var3.getX1() < 0.0F)) {
            var1 = var7;
            if (!(var3.getX0() > var2.getW())) {
               if (var3.getY0() > var2.getH()) {
                  var1 = var7;
               } else {
                  var1 = false;
               }
            }
         }
      }

      return var1;
   }

   private static final void track$take(
      HashSet<Detector.Track> var0,
      HashSet<Detector.Det> var1,
      float var2,
      long var3,
      Detector var5,
      Detector.Track var6,
      Detector.Det var7,
      float var8,
      boolean var9
   ) {
      var0.add(var6);
      var1.add(var7);
      float var10 = (var7.getX0() + var7.getX1()) / 2.0F;
      float var11 = (var7.getY0() + var7.getY1()) / 2.0F;
      if (var6.getLastNs() != 0L) {
         var2 = RangesKt.coerceIn((float)(var3 - var6.getLastNs()) / 1.0E9F, 0.01F, 1.0F);
      }

      float var12 = var6.getCx();
      float var13 = var6.getCy();
      var6.setX0(var6.getX0() + (var7.getX0() - var6.getX0()) * var8);
      var6.setY0(var6.getY0() + (var7.getY0() - var6.getY0()) * var8);
      var6.setX1(var6.getX1() + (var7.getX1() - var6.getX1()) * var8);
      var6.setY1(var6.getY1() + (var7.getY1() - var6.getY1()) * var8);
      if (var6.getHits() > 0) {
         var6.setVx(var6.getVx() + ((var10 - var12) / var2 - var6.getVx()) * 0.5F);
         var6.setVy(var6.getVy() + ((var11 - var13) / var2 - var6.getVy()) * 0.5F);
      }

      var6.setMisses(0);
      var6.setLastNs(var3);
      byte var15 = 1;
      if (!var9) {
         var6.setWeak(var6.getWeak() + 1);
      } else {
         var6.setScore(var7.getScore());
         var6.setHits(var6.getHits() + 1);
         var6.setWeak(0);
         if (var5.isVehicle(var7.getCls())) {
            for (int var14 = 0; var14 < 3; var14++) {
               float[] var16 = var6.getKinds();
               var16[var14] *= 0.95F;
            }

            float[] var17 = var6.getKinds();
            byte var18 = var15;
            switch (var7.getCls()) {
               case 5:
                  var18 = 2;
                  break;
               case 6:
               default:
                  var18 = 0;
               case 7:
            }

            var17[var18]++;
         } else {
            var6.setCls(var7.getCls());
         }
      }
   }

   private final int vehicleCls(Detector.Track var1) {
      if (!this.isVehicle(var1.getCls())) {
         return var1.getCls();
      } else {
         float[] var6 = var1.getKinds();
         float var3 = var6[0];
         float var2 = var6[1];
         byte var5 = 2;
         var2 = var3 + var2 + var6[2];
         byte var4;
         if (var6[2] > 3.0F && var6[2] > var2 * 0.6F) {
            var4 = 5;
         } else {
            var4 = var5;
            if (var6[1] > 3.0F) {
               var4 = var5;
               if (var6[1] > 0.6F * var2) {
                  var4 = 7;
               }
            }
         }

         return var4;
      }
   }

   private final Detector.Cue vehicleCue(Detector.Frame var1, float var2, float var3, float var4, float var5, float var6, int var7, float var8) {
      float var9 = Math.max((var4 - var2) / Math.max(1.0F, var5 - var3) * var8, 0.2F);
      Float var17 = 1.9F;
      Triple var18 = null;
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

   // $VF: synthetic method
   static Detector.Cue vehicleCue$default(
      Detector var0, Detector.Frame var1, float var2, float var3, float var4, float var5, float var6, int var7, float var8, int var9, Object var10
   ) {
      if ((var9 & 128) != 0) {
         var8 = 1.0F;
      }

      return var0.vehicleCue(var1, var2, var3, var4, var5, var6, var7, var8);
   }

   private static final float vehicleCue$predicted(float var0, float var1, float var2, float var3, float var4) {
      var0 = var4 - var0;
      return (Math.abs((float)Math.cos(var0)) * var1 + Math.abs((float)Math.sin(var0)) * var2) / var3;
   }

   private final void wiperWatch(Detector.Frame var1, long var2) {
      float[] var13 = new float[24 * 14];

      for (int var5 = 0; var5 < 14; var5++) {
         for (int var6 = 0; var6 < 24; var6++) {
            var13[var5 * 24 + var6] = var1.getY()[RangesKt.coerceIn((int)((var5 + 0.5F) / 14 * var1.getH()), 0, var1.getH() - 1) * var1.getYStride()
                  + RangesKt.coerceIn((int)((var6 + 0.5F) / 24 * var1.getW()), 0, var1.getW() - 1)]
               & 255;
         }
      }

      float[] var12 = this.wiperAvg;
      float[] var14 = var12;
      if (var12 == null) {
         var14 = Arrays.copyOf(var13, var13.length);
         Intrinsics.checkNotNullExpressionValue(var14, "copyOf(...)");
         this.wiperAvg = var14;
      }

      int var21 = 0;
      int var16 = 0;
      int var8 = var13.length;

      while (var16 < var8) {
         int var7 = var21;
         if (var13[var16] < var14[var16] * 0.6F) {
            var7 = var21;
            if (var14[var16] > 30.0F) {
               var7 = var21 + 1;
            }
         }

         var16++;
         var21 = var7;
      }

      float var4 = (float)var21 / var13.length;
      boolean var11;
      if (0.08F <= var4 && var4 <= 0.6F) {
         var11 = true;
      } else {
         var11 = false;
      }

      if (var11 && !this.wiperDark) {
         this.wiperTimes.addLast(var2);

         while (this.wiperTimes.size() > 6) {
            this.wiperTimes.removeFirst();
         }
      }

      this.wiperDark = var11;
      if (!var11) {
         var16 = 0;

         for (int var22 = var13.length; var16 < var22; var16++) {
            var14[var16] += (var13[var16] - var14[var16]) * 0.1F;
         }
      }

      List var15 = CollectionsKt.toList(this.wiperTimes);
      if (var15.size() >= 3 && var2 - ((Number)var15.get(var15.size() - 3)).longValue() < 30000L) {
         Iterable var24 = RangesKt.until(1, var15.size());
         boolean var20;
         if (var24 instanceof Collection && ((Collection)var24).isEmpty()) {
            var20 = true;
         } else {
            Iterator var25 = var24.iterator();

            while (true) {
               if (!var25.hasNext()) {
                  var20 = true;
                  break;
               }

               var21 = ((IntIterator)var25).nextInt();
               long var9 = ((Number)var15.get(var21)).longValue() - ((Number)var15.get(var21 - 1)).longValue();
               if (800L <= var9 && var9 < 8001L) {
                  var20 = true;
               } else {
                  var20 = false;
               }

               if (!var20 && var21 >= var15.size() - 2) {
                  var20 = false;
               } else {
                  var20 = true;
               }

               if (!var20) {
                  var20 = false;
                  break;
               }
            }
         }

         if (var20) {
            Ideas.INSTANCE.getMore().setWipersAtMs(var2);
         }
      }
   }

   private final float wrap(float var1) {
      while (true) {
         float var2 = var1;
         if (!(var1 > Math.PI)) {
            while (var2 < -Math.PI) {
               var2 += (float) (Math.PI * 2);
            }

            return var2;
         }

         var1 -= (float) (Math.PI * 2);
      }
   }

   public final int[] ambient(boolean var1) {
      Detector.Source var2;
      if (var1) {
         var2 = this.wideSrc;
      } else {
         var2 = this.roadSrc;
      }

      return var2.getAmbient();
   }

   public final void clearSource(boolean var1) {
      Detector.Source var2;
      if (var1) {
         var2 = this.wideSrc;
      } else {
         var2 = this.roadSrc;
      }

      var2.setAtMs(0L);
      this.mergeObjects();
   }

   public final String getAccel() {
      return this.accel;
   }

   public final List<Detector.Det> getDets() {
      Detector.Source var1;
      if (this.shownWide) {
         var1 = this.wideSrc;
      } else {
         var1 = this.roadSrc;
      }

      return var1.getDets();
   }

   public final long getDetsAtNs() {
      return this.detsAtNs;
   }

   public final double getFirstRunMs() {
      return this.firstRunMs;
   }

   public final float getFovTan() {
      return this.fovTan;
   }

   public final double getFps() {
      return this.fps;
   }

   public final String getGpuError() {
      return this.gpuError;
   }

   public final double getInferMs() {
      return this.inferMs;
   }

   public final LaneMarks getLaneMarks() {
      return this.laneMarks;
   }

   public final LaneModel getLaneModel() {
      return this.laneModel;
   }

   public final Bitmap getLastClean() {
      return this.lastClean;
   }

   public final LayoutSeg getLayout() {
      return this.layout;
   }

   public final double getLoadMs() {
      return this.loadMs;
   }

   public final List<Detector.Obj> getObjects() {
      return this.objects;
   }

   public final long getObjectsAtMs() {
      return this.objectsAtMs;
   }

   public final String getOrientError() {
      return this.orient.getFailed();
   }

   public final double getOrientMs() {
      return this.orient.getMs();
   }

   public final double getPrepMs() {
      return this.prepMs;
   }

   public final double getRoadFps() {
      return this.roadSrc.getFps();
   }

   public final Function1<Bitmap, Unit> getShotReq() {
      return this.shotReq;
   }

   public final boolean getShownWide() {
      return this.shownWide;
   }

   public final Bitmap getSnap() {
      return this.snap;
   }

   public final int getSnapWant() {
      return this.snapWant;
   }

   public final boolean getSnapWide() {
      return this.snapWide;
   }

   public final String getStatus() {
      return this.status;
   }

   public final Telemetry getTelemetry() {
      return this.telemetry;
   }

   public final boolean getUseOrient() {
      return this.useOrient;
   }

   public final double getWideFps() {
      return this.wideSrc.getFps();
   }

   public final long getWideFrameNs() {
      return this.wideFrameNs;
   }

   public final Function1<Bitmap, Unit> getWideReq() {
      return this.wideReq;
   }

   public final boolean isRunning() {
      return this.running;
   }

   public final void offer(Image param1, boolean param2) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: parsing failure!
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 000: aload 1
      // 001: ldc_w "img"
      // 004: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
      // 007: aload 0
      // 008: getfield com/starpilot/starview/Detector.running Z
      // 00b: ifne 00f
      // 00e: return
      // 00f: iload 2
      // 010: ifeq 01b
      // 013: aload 0
      // 014: getfield com/starpilot/starview/Detector.wideSrc Lcom/starpilot/starview/Detector$Source;
      // 017: astore 3
      // 018: goto 020
      // 01b: aload 0
      // 01c: getfield com/starpilot/starview/Detector.roadSrc Lcom/starpilot/starview/Detector$Source;
      // 01f: astore 3
      // 020: aload 3
      // 021: invokevirtual com/starpilot/starview/Detector$Source.getFree ()Ljava/util/concurrent/ConcurrentLinkedQueue;
      // 024: invokevirtual java/util/concurrent/ConcurrentLinkedQueue.poll ()Ljava/lang/Object;
      // 027: checkcast com/starpilot/starview/Detector$Frame
      // 02a: astore 4
      // 02c: aload 4
      // 02e: ifnonnull 032
      // 031: return
      // 032: aload 1
      // 033: invokevirtual android/media/Image.getPlanes ()[Landroid/media/Image$Plane;
      // 036: astore 5
      // 038: aload 4
      // 03a: aload 1
      // 03b: invokevirtual android/media/Image.getWidth ()I
      // 03e: invokevirtual com/starpilot/starview/Detector$Frame.setW (I)V
      // 041: aload 4
      // 043: aload 1
      // 044: invokevirtual android/media/Image.getHeight ()I
      // 047: invokevirtual com/starpilot/starview/Detector$Frame.setH (I)V
      // 04a: aload 5
      // 04c: bipush 0
      // 04d: aaload
      // 04e: invokevirtual android/media/Image$Plane.getBuffer ()Ljava/nio/ByteBuffer;
      // 051: astore 1
      // 052: aload 1
      // 053: ldc_w "getBuffer(...)"
      // 056: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 059: aload 4
      // 05b: aload 0
      // 05c: aload 1
      // 05d: aload 4
      // 05f: invokevirtual com/starpilot/starview/Detector$Frame.getY ()[B
      // 062: invokespecial com/starpilot/starview/Detector.copy (Ljava/nio/ByteBuffer;[B)[B
      // 065: invokevirtual com/starpilot/starview/Detector$Frame.setY ([B)V
      // 068: aload 5
      // 06a: bipush 1
      // 06b: aaload
      // 06c: invokevirtual android/media/Image$Plane.getBuffer ()Ljava/nio/ByteBuffer;
      // 06f: astore 1
      // 070: aload 1
      // 071: ldc_w "getBuffer(...)"
      // 074: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 077: aload 4
      // 079: aload 0
      // 07a: aload 1
      // 07b: aload 4
      // 07d: invokevirtual com/starpilot/starview/Detector$Frame.getU ()[B
      // 080: invokespecial com/starpilot/starview/Detector.copy (Ljava/nio/ByteBuffer;[B)[B
      // 083: invokevirtual com/starpilot/starview/Detector$Frame.setU ([B)V
      // 086: aload 5
      // 088: bipush 2
      // 089: aaload
      // 08a: invokevirtual android/media/Image$Plane.getBuffer ()Ljava/nio/ByteBuffer;
      // 08d: astore 1
      // 08e: aload 1
      // 08f: ldc_w "getBuffer(...)"
      // 092: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 095: aload 4
      // 097: aload 0
      // 098: aload 1
      // 099: aload 4
      // 09b: invokevirtual com/starpilot/starview/Detector$Frame.getV ()[B
      // 09e: invokespecial com/starpilot/starview/Detector.copy (Ljava/nio/ByteBuffer;[B)[B
      // 0a1: invokevirtual com/starpilot/starview/Detector$Frame.setV ([B)V
      // 0a4: aload 4
      // 0a6: aload 5
      // 0a8: bipush 0
      // 0a9: aaload
      // 0aa: invokevirtual android/media/Image$Plane.getRowStride ()I
      // 0ad: invokevirtual com/starpilot/starview/Detector$Frame.setYStride (I)V
      // 0b0: aload 4
      // 0b2: aload 5
      // 0b4: bipush 1
      // 0b5: aaload
      // 0b6: invokevirtual android/media/Image$Plane.getRowStride ()I
      // 0b9: invokevirtual com/starpilot/starview/Detector$Frame.setUvStride (I)V
      // 0bc: aload 4
      // 0be: aload 5
      // 0c0: bipush 1
      // 0c1: aaload
      // 0c2: invokevirtual android/media/Image$Plane.getPixelStride ()I
      // 0c5: invokevirtual com/starpilot/starview/Detector$Frame.setUvPixStride (I)V
      // 0c8: aload 3
      // 0c9: invokevirtual com/starpilot/starview/Detector$Source.getFrame ()Ljava/util/concurrent/atomic/AtomicReference;
      // 0cc: aload 4
      // 0ce: invokevirtual java/util/concurrent/atomic/AtomicReference.getAndSet (Ljava/lang/Object;)Ljava/lang/Object;
      // 0d1: checkcast com/starpilot/starview/Detector$Frame
      // 0d4: astore 1
      // 0d5: aload 1
      // 0d6: ifnull 0e2
      // 0d9: aload 3
      // 0da: invokevirtual com/starpilot/starview/Detector$Source.getFree ()Ljava/util/concurrent/ConcurrentLinkedQueue;
      // 0dd: aload 1
      // 0de: invokevirtual java/util/concurrent/ConcurrentLinkedQueue.add (Ljava/lang/Object;)Z
      // 0e1: pop
      // 0e2: aload 0
      // 0e3: getfield com/starpilot/starview/Detector.lock Ljava/lang/Object;
      // 0e6: astore 1
      // 0e7: aload 1
      // 0e8: monitorenter
      // 0e9: aload 0
      // 0ea: getfield com/starpilot/starview/Detector.lock Ljava/lang/Object;
      // 0ed: invokevirtual java/lang/Object.notifyAll ()V
      // 0f0: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 0f3: astore 5
      // 0f5: aload 1
      // 0f6: monitorexit
      // 0f7: goto 127
      // 0fa: astore 5
      // 0fc: aload 1
      // 0fd: monitorexit
      // 0fe: aload 5
      // 100: athrow
      // 101: astore 1
      // 102: aload 3
      // 103: invokevirtual com/starpilot/starview/Detector$Source.getFree ()Ljava/util/concurrent/ConcurrentLinkedQueue;
      // 106: aload 4
      // 108: invokevirtual java/util/concurrent/ConcurrentLinkedQueue.add (Ljava/lang/Object;)Z
      // 10b: pop
      // 10c: ldc_w "StarView/Det"
      // 10f: new java/lang/StringBuilder
      // 112: dup
      // 113: invokespecial java/lang/StringBuilder.<init> ()V
      // 116: ldc_w "offer: "
      // 119: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 11c: aload 1
      // 11d: invokevirtual java/lang/StringBuilder.append (Ljava/lang/Object;)Ljava/lang/StringBuilder;
      // 120: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 123: invokestatic android/util/Log.w (Ljava/lang/String;Ljava/lang/String;)I
      // 126: pop
      // 127: return
   }

   public final void setAccel(String var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.accel = var1;
   }

   public final void setDetsAtNs(long var1) {
      this.detsAtNs = var1;
   }

   public final void setFirstRunMs(double var1) {
      this.firstRunMs = var1;
   }

   public final void setFovTan(float var1) {
      this.fovTan = var1;
   }

   public final void setFps(double var1) {
      this.fps = var1;
   }

   public final void setGpuError(String var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.gpuError = var1;
   }

   public final void setInferMs(double var1) {
      this.inferMs = var1;
   }

   public final void setLaneMarks(LaneMarks var1) {
      this.laneMarks = var1;
   }

   public final void setLaneModel(LaneModel var1) {
      this.laneModel = var1;
   }

   public final void setLastClean(Bitmap var1) {
      this.lastClean = var1;
   }

   public final void setLayout(LayoutSeg var1) {
      this.layout = var1;
   }

   public final void setLoadMs(double var1) {
      this.loadMs = var1;
   }

   public final void setObjects(List<Detector.Obj> var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.objects = var1;
   }

   public final void setObjectsAtMs(long var1) {
      this.objectsAtMs = var1;
   }

   public final void setPrepMs(double var1) {
      this.prepMs = var1;
   }

   public final void setShotReq(Function1<? super Bitmap, Unit> var1) {
      this.shotReq = var1;
   }

   public final void setShownWide(boolean var1) {
      this.shownWide = var1;
   }

   public final void setSnap(Bitmap var1) {
      this.snap = var1;
   }

   public final void setSnapWant(int var1) {
      this.snapWant = var1;
   }

   public final void setSnapWide(boolean var1) {
      this.snapWide = var1;
   }

   public final void setStatus(String var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.status = var1;
   }

   public final void setTelemetry(Telemetry var1) {
      this.telemetry = var1;
   }

   public final void setUseOrient(boolean var1) {
      this.useOrient = var1;
   }

   public final void setWideFrameNs(long var1) {
      this.wideFrameNs = var1;
   }

   public final void setWideReq(Function1<? super Bitmap, Unit> var1) {
      this.wideReq = var1;
   }

   public final void shutdown() {
      this.stop();
      this.release();
      this.pool.shutdownNow();
   }

   public final void start() {
      if (!this.running) {
         this.running = true;
         Thread var1 = new Thread(new Detector$$ExternalSyntheticLambda1(this), "starview-detector");
         var1.setPriority(4);
         var1.start();
         this.worker = var1;
      }
   }

   public final void stop() {
      if (this.running) {
         this.running = false;
         Thread var1 = (Thread)this.lock;
         synchronized (var1) {
            this.lock.notifyAll();
            Unit var2 = Unit.INSTANCE;
         }

         var1 = this.worker;
         if (var1 != null) {
            var1.join(2000L);
         }

         this.worker = null;
         this.release();
         this.roadSrc.clear();
         this.wideSrc.clear();
         this.objects = CollectionsKt.emptyList();
         this.status = "off";
      }
   }

   public final boolean wideLive() {
      boolean var1;
      if (System.nanoTime() - this.wideFrameNs < 1000000000L) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public static final class Companion {
      private Companion() {
      }

      // $VF: synthetic method
      public Companion(DefaultConstructorMarker var1) {
         this();
      }

      public final Map<Integer, String> getNAMES() {
         return Detector.NAMES;
      }
   }

   private static final class Cue {
      private final int acrossBin;
      private final float acrossW;
      private final int alongBin;
      private final float ev;

      public Cue(float var1, int var2, int var3, float var4) {
         this.ev = var1;
         this.alongBin = var2;
         this.acrossBin = var3;
         this.acrossW = var4;
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

   public static final class Det {
      private final int cls;
      private final long id;
      private final float score;
      private final float x0;
      private final float x1;
      private final float y0;
      private final float y1;

      public Det(int var1, float var2, float var3, float var4, float var5, float var6, long var7) {
         this.cls = var1;
         this.score = var2;
         this.x0 = var3;
         this.y0 = var4;
         this.x1 = var5;
         this.y1 = var6;
         this.id = var7;
      }

      // $VF: synthetic method
      public Det(int var1, float var2, float var3, float var4, float var5, float var6, long var7, int var9, DefaultConstructorMarker var10) {
         if ((var9 & 64) != 0) {
            var7 = 0L;
         }

         this(var1, var2, var3, var4, var5, var6, var7);
      }

      public final int getCls() {
         return this.cls;
      }

      public final long getId() {
         return this.id;
      }

      public final String getName() {
         String var2 = Detector.Companion.getNAMES().get(this.cls);
         String var1 = var2;
         if (var2 == null) {
            var1 = "?";
         }

         return var1;
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
   }

   private static final class Frame {
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

      public Frame(int var1, int var2, byte[] var3, byte[] var4, byte[] var5, int var6, int var7, int var8) {
         Intrinsics.checkNotNullParameter(var3, "y");
         Intrinsics.checkNotNullParameter(var4, "u");
         Intrinsics.checkNotNullParameter(var5, "v");
         super();
         this.w = var1;
         this.h = var2;
         this.y = var3;
         this.u = var4;
         this.v = var5;
         this.yStride = var6;
         this.uvStride = var7;
         this.uvPixStride = var8;
      }

      // $VF: synthetic method
      public Frame(int var1, int var2, byte[] var3, byte[] var4, byte[] var5, int var6, int var7, int var8, int var9, DefaultConstructorMarker var10) {
         if ((var9 & 1) != 0) {
            var1 = 0;
         }

         if ((var9 & 2) != 0) {
            var2 = 0;
         }

         if ((var9 & 4) != 0) {
            var3 = new byte[0];
         }

         if ((var9 & 8) != 0) {
            var4 = new byte[0];
         }

         if ((var9 & 16) != 0) {
            var5 = new byte[0];
         }

         if ((var9 & 32) != 0) {
            var6 = 0;
         }

         if ((var9 & 64) != 0) {
            var7 = 0;
         }

         if ((var9 & 128) != 0) {
            var8 = 0;
         }

         this(var1, var2, var3, var4, var5, var6, var7, var8);
      }

      public final int getH() {
         return this.h;
      }

      public final byte[] getU() {
         return this.u;
      }

      public final int getUvPixStride() {
         return this.uvPixStride;
      }

      public final int getUvStride() {
         return this.uvStride;
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

      public final int getYStride() {
         return this.yStride;
      }

      public final void setH(int var1) {
         this.h = var1;
      }

      public final void setU(byte[] var1) {
         Intrinsics.checkNotNullParameter(var1, "<set-?>");
         this.u = var1;
      }

      public final void setUvPixStride(int var1) {
         this.uvPixStride = var1;
      }

      public final void setUvStride(int var1) {
         this.uvStride = var1;
      }

      public final void setV(byte[] var1) {
         Intrinsics.checkNotNullParameter(var1, "<set-?>");
         this.v = var1;
      }

      public final void setW(int var1) {
         this.w = var1;
      }

      public final void setY(byte[] var1) {
         Intrinsics.checkNotNullParameter(var1, "<set-?>");
         this.y = var1;
      }

      public final void setYStride(int var1) {
         this.yStride = var1;
      }
   }

   private static final class OCand {
      private final float aspK;
      private final float bearing;
      private final Detector.Track tr;

      public OCand(Detector.Track var1, float var2, float var3) {
         Intrinsics.checkNotNullParameter(var1, "tr");
         super();
         this.tr = var1;
         this.bearing = var2;
         this.aspK = var3;
      }

      public final float getAspK() {
         return this.aspK;
      }

      public final float getBearing() {
         return this.bearing;
      }

      public final Detector.Track getTr() {
         return this.tr;
      }
   }

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

      public Obj(
         long var1,
         int var3,
         float var4,
         float var5,
         float var6,
         int var7,
         int var8,
         int var9,
         boolean var10,
         float var11,
         float var12,
         float var13,
         boolean var14,
         float var15,
         float var16,
         float var17,
         boolean var18
      ) {
         this.id = var1;
         this.cls = var3;
         this.x = var4;
         this.y = var5;
         this.w = var6;
         this.facing = var7;
         this.lamp = var8;
         this.kind = var9;
         this.wideCam = var10;
         this.heading = var11;
         this.cx = var12;
         this.cy = var13;
         this.moving = var14;
         this.speed = var15;
         this.vx = var16;
         this.vy = var17;
         this.velValid = var18;
      }

      // $VF: synthetic method
      public Obj(
         long var1,
         int var3,
         float var4,
         float var5,
         float var6,
         int var7,
         int var8,
         int var9,
         boolean var10,
         float var11,
         float var12,
         float var13,
         boolean var14,
         float var15,
         float var16,
         float var17,
         boolean var18,
         int var19,
         DefaultConstructorMarker var20
      ) {
         if ((var19 & 256) != 0) {
            var10 = false;
         }

         if ((var19 & 512) != 0) {
            var11 = Float.NaN;
         }

         if ((var19 & 1024) != 0) {
            var12 = var4;
         }

         if ((var19 & 2048) != 0) {
            var13 = var5;
         }

         if ((var19 & 4096) != 0) {
            var14 = false;
         }

         if ((var19 & 8192) != 0) {
            var15 = 0.0F;
         }

         if ((var19 & 16384) != 0) {
            var16 = 0.0F;
         }

         if ((32768 & var19) != 0) {
            var17 = 0.0F;
         }

         if ((var19 & 65536) != 0) {
            var18 = false;
         }

         this(var1, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13, var14, var15, var16, var17, var18);
      }

      public final int getCls() {
         return this.cls;
      }

      public final float getCx() {
         return this.cx;
      }

      public final float getCy() {
         return this.cy;
      }

      public final int getFacing() {
         return this.facing;
      }

      public final float getHeading() {
         return this.heading;
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
   }

   public static final class Path {
      public static final Detector.Path INSTANCE = new Detector.Path();

      private Path() {
      }

      public final void pathOn(Bitmap var1, int var2, int var3, float[] var4, float var5, float[] var6, float[] var7, float[] var8) {
         Intrinsics.checkNotNullParameter(var1, "bmp");
         Intrinsics.checkNotNullParameter(var4, "rpy");
         Intrinsics.checkNotNullParameter(var6, "px");
         Intrinsics.checkNotNullParameter(var7, "py");
         Intrinsics.checkNotNullParameter(var8, "pz");
         if (var6.length >= 4) {
            Projection.Companion.Cam var17 = Projection.Companion.camFor(var2, var3, false);
            double var9 = var17.getF();
            double var15 = var17.getCx();
            double var11 = var17.getF();
            double var13 = var17.getCy();
            Projection.Companion var44 = Projection.Companion;
            double[] var26 = Projection.Companion.mul(Projection.Companion.getVIEW_FROM_DEVICE(), Projection.Companion.rotFromEuler(var4[0], var4[1], var4[2]));
            double[] var20 = var44.mul(new double[]{var9, 0.0, var15, 0.0, var11, var13, 0.0, 0.0, 1.0}, var26);
            var11 = (double)var1.getWidth() / var2;
            ArrayList var45 = new ArrayList();
            ArrayList var27 = new ArrayList();
            var3 = 0;

            for (int var22 = var6.length; var3 < var22; var3++) {
               var13 = var6[var3];
               if (!(var13 < 4.0)) {
                  if (var13 > 25.0) {
                     break;
                  }

                  var15 = var7[var3];
                  var9 = (double)var8[var3] + var5;
                  double[] var18 = Projection.Companion.mulv(var20, var13, var15 - 0.45, var9);
                  double[] var19 = Projection.Companion.mulv(var20, var13, var15 + 0.45, var9);
                  if (!(var18[2] < 0.5) && !(var19[2] < 0.5)) {
                     var45.add((float)(var18[0] / var18[2] * var11));
                     var45.add((float)(var18[1] / var18[2] * var11));
                     var27.add((float)(var19[0] / var19[2] * var11));
                     var27.add((float)(var19[1] / var19[2] * var11));
                  }
               }
            }

            if (var45.size() >= 4) {
               android.graphics.Path var32 = new android.graphics.Path();
               Paint var33 = (Paint)var45.get(0);
               Intrinsics.checkNotNullExpressionValue(var33, "get(...)");
               var5 = ((Number)var33).floatValue();
               var33 = var45.get(1);
               Intrinsics.checkNotNullExpressionValue(var33, "get(...)");
               var32.moveTo(var5, ((Number)var33).floatValue());

               for (byte var23 = 2; var23 < var45.size(); var23 += 2) {
                  var33 = var45.get(var23);
                  Intrinsics.checkNotNullExpressionValue(var33, "get(...)");
                  var5 = ((Number)var33).floatValue();
                  var33 = var45.get(var23 + 1);
                  Intrinsics.checkNotNullExpressionValue(var33, "get(...)");
                  var32.lineTo(var5, ((Number)var33).floatValue());
               }

               for (int var24 = var27.size() - 2; var24 >= 0; var24 -= 2) {
                  var33 = var27.get(var24);
                  Intrinsics.checkNotNullExpressionValue(var33, "get(...)");
                  var5 = ((Number)var33).floatValue();
                  var33 = var27.get(var24 + 1);
                  Intrinsics.checkNotNullExpressionValue(var33, "get(...)");
                  var32.lineTo(var5, ((Number)var33).floatValue());
               }

               var32.close();
               Canvas var28 = new Canvas(var1);
               var33 = new Paint(1);
               var33.setColor(Color.argb(150, 55, 138, 255));
               Unit var21 = Unit.INSTANCE;
               var28.drawPath(var32, var33);
            }
         }
      }
   }

   private static final class Pend {
      private final int cls;
      private final int lamp;
      private final boolean measured;
      private final boolean moving;
      private final float speed;
      private final Detector.Track tr;

      public Pend(Detector.Track var1, int var2, int var3, boolean var4, float var5, boolean var6) {
         Intrinsics.checkNotNullParameter(var1, "tr");
         super();
         this.tr = var1;
         this.cls = var2;
         this.lamp = var3;
         this.moving = var4;
         this.speed = var5;
         this.measured = var6;
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

      public final Detector.Track getTr() {
         return this.tr;
      }
   }

   private final class Source {
      private volatile int[] ambient;
      private long ambientNs;
      private volatile long atMs;
      private volatile List<Detector.Det> dets;
      private volatile float fovTan;
      private volatile double fps;
      private long fpsT0;
      private int fpsWin;
      private final AtomicReference<Detector.Frame> frame;
      private final ConcurrentLinkedQueue<Detector.Frame> free;
      private long lastRunNs;
      private long lastTrackNs;
      private volatile List<Detector.Obj> objects;
      final Detector this$0;
      private final ArrayList<Detector.Track> tracks;
      private final boolean wide;

      public Source(boolean var1, boolean var2) {
         this.this$0 = var1;
         this.wide = var2;
         this.frame = new AtomicReference<>(null);
         ConcurrentLinkedQueue var4 = new ConcurrentLinkedQueue();

         for (int var3 = 0; var3 < 3; var3++) {
            var4.add(new Detector.Frame(0, 0, null, null, null, 0, 0, 0, 255, null));
         }

         this.free = var4;
         this.tracks = new ArrayList<>();
         this.dets = CollectionsKt.emptyList();
         this.objects = CollectionsKt.emptyList();
         this.fovTan = 0.5F;
         this.fpsT0 = System.nanoTime();
      }

      public final void clear() {
         this.tracks.clear();
         this.dets = CollectionsKt.emptyList();
         this.objects = CollectionsKt.emptyList();
         Detector.Frame var1 = this.frame.getAndSet(null);
         if (var1 != null) {
            this.free.add(var1);
         }
      }

      public final int[] getAmbient() {
         return this.ambient;
      }

      public final long getAmbientNs() {
         return this.ambientNs;
      }

      public final long getAtMs() {
         return this.atMs;
      }

      public final List<Detector.Det> getDets() {
         return this.dets;
      }

      public final float getFovTan() {
         return this.fovTan;
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

      public final AtomicReference<Detector.Frame> getFrame() {
         return this.frame;
      }

      public final ConcurrentLinkedQueue<Detector.Frame> getFree() {
         return this.free;
      }

      public final long getLastRunNs() {
         return this.lastRunNs;
      }

      public final long getLastTrackNs() {
         return this.lastTrackNs;
      }

      public final List<Detector.Obj> getObjects() {
         return this.objects;
      }

      public final ArrayList<Detector.Track> getTracks() {
         return this.tracks;
      }

      public final boolean getWide() {
         return this.wide;
      }

      public final void setAmbient(int[] var1) {
         this.ambient = var1;
      }

      public final void setAmbientNs(long var1) {
         this.ambientNs = var1;
      }

      public final void setAtMs(long var1) {
         this.atMs = var1;
      }

      public final void setDets(List<Detector.Det> var1) {
         Intrinsics.checkNotNullParameter(var1, "<set-?>");
         this.dets = var1;
      }

      public final void setFovTan(float var1) {
         this.fovTan = var1;
      }

      public final void setFps(double var1) {
         this.fps = var1;
      }

      public final void setFpsT0(long var1) {
         this.fpsT0 = var1;
      }

      public final void setFpsWin(int var1) {
         this.fpsWin = var1;
      }

      public final void setLastRunNs(long var1) {
         this.lastRunNs = var1;
      }

      public final void setLastTrackNs(long var1) {
         this.lastTrackNs = var1;
      }

      public final void setObjects(List<Detector.Obj> var1) {
         Intrinsics.checkNotNullParameter(var1, "<set-?>");
         this.objects = var1;
      }
   }

   private static final class Track {
      private Boolean along;
      private float axisEv;
      private int cls;
      private float gvx;
      private float gvy;
      private float gw;
      private float gx;
      private float gy;
      private final float[] hVotes;
      private int hits;
      private final long id;
      private final float[] kinds;
      private final float[] lampVotes;
      private long lastNs;
      private long mNs;
      private int misses;
      private float mx;
      private float my;
      private final float[] oHist;
      private int oN;
      private long oNs;
      private float rowEv;
      private float score;
      private int velN;
      private final float[] votes;
      private float vx;
      private float vy;
      private int weak;
      private float x0;
      private float x1;
      private float y0;
      private float y1;

      public Track(long var1, int var3) {
         this.id = var1;
         this.cls = var3;
         this.gx = Float.NaN;
         this.votes = new float[3];
         this.kinds = new float[3];
         this.lampVotes = new float[3];
         this.hVotes = new float[4];
         this.oHist = new float[16];
         this.mx = Float.NaN;
      }

      public final Boolean getAlong() {
         return this.along;
      }

      public final float getAxisEv() {
         return this.axisEv;
      }

      public final int getCls() {
         return this.cls;
      }

      public final float getCx() {
         return (this.x0 + this.x1) / 2.0F;
      }

      public final float getCy() {
         return (this.y0 + this.y1) / 2.0F;
      }

      public final float getGvx() {
         return this.gvx;
      }

      public final float getGvy() {
         return this.gvy;
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

      public final float[] getHVotes() {
         return this.hVotes;
      }

      public final int getHits() {
         return this.hits;
      }

      public final long getId() {
         return this.id;
      }

      public final float[] getKinds() {
         return this.kinds;
      }

      public final float[] getLampVotes() {
         return this.lampVotes;
      }

      public final long getLastNs() {
         return this.lastNs;
      }

      public final long getMNs() {
         return this.mNs;
      }

      public final int getMisses() {
         return this.misses;
      }

      public final float getMx() {
         return this.mx;
      }

      public final float getMy() {
         return this.my;
      }

      public final float[] getOHist() {
         return this.oHist;
      }

      public final int getON() {
         return this.oN;
      }

      public final long getONs() {
         return this.oNs;
      }

      public final float getRowEv() {
         return this.rowEv;
      }

      public final float getScore() {
         return this.score;
      }

      public final int getVelN() {
         return this.velN;
      }

      public final float[] getVotes() {
         return this.votes;
      }

      public final float getVx() {
         return this.vx;
      }

      public final float getVy() {
         return this.vy;
      }

      public final int getWeak() {
         return this.weak;
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

      public final void setAlong(Boolean var1) {
         this.along = var1;
      }

      public final void setAxisEv(float var1) {
         this.axisEv = var1;
      }

      public final void setCls(int var1) {
         this.cls = var1;
      }

      public final void setGvx(float var1) {
         this.gvx = var1;
      }

      public final void setGvy(float var1) {
         this.gvy = var1;
      }

      public final void setGw(float var1) {
         this.gw = var1;
      }

      public final void setGx(float var1) {
         this.gx = var1;
      }

      public final void setGy(float var1) {
         this.gy = var1;
      }

      public final void setHits(int var1) {
         this.hits = var1;
      }

      public final void setLastNs(long var1) {
         this.lastNs = var1;
      }

      public final void setMNs(long var1) {
         this.mNs = var1;
      }

      public final void setMisses(int var1) {
         this.misses = var1;
      }

      public final void setMx(float var1) {
         this.mx = var1;
      }

      public final void setMy(float var1) {
         this.my = var1;
      }

      public final void setON(int var1) {
         this.oN = var1;
      }

      public final void setONs(long var1) {
         this.oNs = var1;
      }

      public final void setRowEv(float var1) {
         this.rowEv = var1;
      }

      public final void setScore(float var1) {
         this.score = var1;
      }

      public final void setVelN(int var1) {
         this.velN = var1;
      }

      public final void setVx(float var1) {
         this.vx = var1;
      }

      public final void setVy(float var1) {
         this.vy = var1;
      }

      public final void setWeak(int var1) {
         this.weak = var1;
      }

      public final void setX0(float var1) {
         this.x0 = var1;
      }

      public final void setX1(float var1) {
         this.x1 = var1;
      }

      public final void setY0(float var1) {
         this.y0 = var1;
      }

      public final void setY1(float var1) {
         this.y1 = var1;
      }
   }
}
