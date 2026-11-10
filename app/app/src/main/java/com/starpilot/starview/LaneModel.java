package com.starpilot.starview;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import java.io.File;
import java.nio.Buffer;
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
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.StringsKt;
import org.tensorflow.lite.Interpreter;
import org.tensorflow.lite.gpu.GpuDelegate;
import org.tensorflow.lite.gpu.GpuDelegateFactory.Options;

public final class LaneModel {
   public static final LaneModel.Companion Companion = new LaneModel.Companion(null);
   public static final long EVERY_MS = 2000L;
   public static final String FILE = "lane_count.lite";
   public static final int FULL_H = 500;
   public static final int H = 400;
   private static final float[] MEAN = new float[]{0.485F, 0.456F, 0.406F};
   private static final float[] STD = new float[]{0.229F, 0.224F, 0.225F};
   public static final float SURE = 0.6F;
   private static final String TAG = "StarView/LaneModel";
   public static final int W = 800;
   private volatile String accel;
   private volatile LaneModel.Answer answer;
   private volatile boolean busy;
   private final Context ctx;
   private volatile boolean enabled;
   private GpuDelegate gpu;
   private final Lazy input$delegate;
   private Interpreter interp;
   private long lastMs;
   private long loadedMtime;
   private final float[][] out;
   private final int[] px;
   private volatile String status;

   // $VF: synthetic method
   public static ByteBuffer $r8$lambda$2Kss2NNgyWkgejcFF7ztDyHuWDQ() {
      return input_delegate$lambda$0();
   }

   // $VF: synthetic method
   public static void $r8$lambda$Kl7M_sAZB_8DQnBMPlmu_kZYJOM(LaneModel var0, Bitmap var1) {
      feed$lambda$1(var0, var1);
   }

   public LaneModel(Context var1) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      super();
      this.ctx = var1;
      this.status = "off";
      this.accel = "-";
      this.enabled = true;
      this.input$delegate = LazyKt.lazy(new LaneModel$$ExternalSyntheticLambda0());
      float[][] var3 = new float[1][];

      for (int var2 = 0; var2 < 1; var2++) {
         var3[var2] = new float[8];
      }

      this.out = var3;
      this.px = new int[400000];
   }

   private final void close() {
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
      // 01: getfield com/starpilot/starview/LaneModel.interp Lorg/tensorflow/lite/Interpreter;
      // 04: astore 1
      // 05: aload 1
      // 06: ifnull 11
      // 09: aload 1
      // 0a: invokevirtual org/tensorflow/lite/Interpreter.close ()V
      // 0d: goto 11
      // 10: astore 1
      // 11: aload 0
      // 12: aconst_null
      // 13: putfield com/starpilot/starview/LaneModel.interp Lorg/tensorflow/lite/Interpreter;
      // 16: aload 0
      // 17: getfield com/starpilot/starview/LaneModel.gpu Lorg/tensorflow/lite/gpu/GpuDelegate;
      // 1a: astore 1
      // 1b: aload 1
      // 1c: ifnull 27
      // 1f: aload 1
      // 20: invokevirtual org/tensorflow/lite/gpu/GpuDelegate.close ()V
      // 23: goto 27
      // 26: astore 1
      // 27: aload 0
      // 28: aconst_null
      // 29: putfield com/starpilot/starview/LaneModel.gpu Lorg/tensorflow/lite/gpu/GpuDelegate;
      // 2c: return
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private final Interpreter ensure() {
      Interpreter var1 = this.interp;
      if (var1 != null) {
         if (this.modelFile().lastModified() == this.loadedMtime) {
            return var1;
         }

         this.close();
      }

      this.loadedMtime = this.modelFile().lastModified();
      if (!this.getHasModel()) {
         this.status = "no model imported";
         return null;
      } else {
         this.status = "loading…";
         byte[] var2 = FilesKt.readBytes(this.modelFile());
         ByteBuffer var35 = ByteBuffer.allocateDirect(var2.length).order(ByteOrder.nativeOrder());
         var35.put(var2);
         ((Buffer)var35).rewind();

         label199: {
            Options var3;
            try {
               org.tensorflow.lite.gpu.GpuDelegate.Options var37 = new org.tensorflow.lite.gpu.GpuDelegate.Options();
               var3 = var37.setPrecisionLossAllowed(true).setQuantizedModelsAllowed(true).setInferencePreference(1);
            } catch (Throwable var34) {
               Log.w("StarView/LaneModel", "GPU delegate: " + var34);
               break label199;
            }

            try {
               var38 = new GpuDelegate(var3);
            } catch (Throwable var33) {
               Log.w("StarView/LaneModel", "GPU delegate: " + var33);
               break label199;
            }

            try {
               org.tensorflow.lite.Interpreter.Options var40 = new org.tensorflow.lite.Interpreter.Options();
               Interpreter var41 = new Interpreter(var35, var40.addDelegate(var38));
               this.gpu = var38;
               this.accel = "gpu";
               this.interp = var41;
               return var41;
            } catch (Throwable var32) {
               label200: {
                  Throwable var4 = var32;

                  try {
                     StringBuilder var39 = new StringBuilder();
                     Log.w("StarView/LaneModel", var39.append("GPU: ").append(var4).toString());
                  } catch (Throwable var31) {
                     Log.w("StarView/LaneModel", "GPU delegate: " + var31);
                     break label200;
                  }

                  label179:
                  try {
                     var38.close();
                     break label179;
                  } finally {
                     break label179;
                  }
               }
            }
         }

         var1 = new Interpreter(var35, new org.tensorflow.lite.Interpreter.Options().setNumThreads(2));
         this.accel = "cpu";
         this.interp = var1;
         return var1;
      }
   }

   private static final void feed$lambda$1(LaneModel param0, Bitmap param1) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index -1 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:385)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:551)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1064)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:565)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
      //
      // Bytecode:
      // 00: aload 0
      // 01: aload 1
      // 02: invokespecial com/starpilot/starview/LaneModel.run (Landroid/graphics/Bitmap;)V
      // 05: aload 1
      // 06: invokevirtual android/graphics/Bitmap.recycle ()V
      // 09: aload 0
      // 0a: bipush 0
      // 0b: putfield com/starpilot/starview/LaneModel.busy Z
      // 0e: goto 60
      // 11: astore 3
      // 12: new java/lang/StringBuilder
      // 15: astore 2
      // 16: aload 2
      // 17: invokespecial java/lang/StringBuilder.<init> ()V
      // 1a: ldc "StarView/LaneModel"
      // 1c: aload 2
      // 1d: ldc_w "run: "
      // 20: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 23: aload 3
      // 24: invokevirtual java/lang/StringBuilder.append (Ljava/lang/Object;)Ljava/lang/StringBuilder;
      // 27: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 2a: invokestatic android/util/Log.w (Ljava/lang/String;Ljava/lang/String;)I
      // 2d: pop
      // 2e: aload 3
      // 2f: invokevirtual java/lang/Throwable.getMessage ()Ljava/lang/String;
      // 32: astore 2
      // 33: aload 2
      // 34: ifnull 41
      // 37: aload 2
      // 38: bipush 80
      // 3a: invokestatic kotlin/text/StringsKt.take (Ljava/lang/String;I)Ljava/lang/String;
      // 3d: astore 2
      // 3e: goto 43
      // 41: aconst_null
      // 42: astore 2
      // 43: new java/lang/StringBuilder
      // 46: astore 3
      // 47: aload 3
      // 48: invokespecial java/lang/StringBuilder.<init> ()V
      // 4b: aload 0
      // 4c: aload 3
      // 4d: ldc_w "error: "
      // 50: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 53: aload 2
      // 54: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 57: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 5a: putfield com/starpilot/starview/LaneModel.status Ljava/lang/String;
      // 5d: goto 05
      // 60: return
      // 61: astore 2
      // 62: aload 1
      // 63: invokevirtual android/graphics/Bitmap.recycle ()V
      // 66: aload 0
      // 67: bipush 0
      // 68: putfield com/starpilot/starview/LaneModel.busy Z
      // 6b: aload 2
      // 6c: athrow
   }

   private final ByteBuffer getInput() {
      Object var1 = this.input$delegate.getValue();
      Intrinsics.checkNotNullExpressionValue(var1, "getValue(...)");
      return (ByteBuffer)var1;
   }

   private static final ByteBuffer input_delegate$lambda$0() {
      return ByteBuffer.allocateDirect(3840000).order(ByteOrder.nativeOrder());
   }

   private final void run(Bitmap var1) {
      Interpreter var7 = this.ensure();
      if (var7 != null) {
         long var5 = System.nanoTime();
         Bitmap var8 = Bitmap.createScaledBitmap(var1, 800, 500, true);
         Intrinsics.checkNotNullExpressionValue(var8, "createScaledBitmap(...)");
         var8.getPixels(this.px, 0, 800, 0, 0, 800, 500);
         if (!Intrinsics.areEqual(var8, var1)) {
            var8.recycle();
         }

         ((Buffer)this.getInput()).rewind();
         FloatBuffer var9 = this.getInput().asFloatBuffer();

         for (int var2 = 100; var2 < 500; var2++) {
            for (int var3 = 0; var3 < 800; var3++) {
               int var4 = this.px[var2 * 800 + var3];
               var9.put(((var4 >> 16 & 0xFF) / 255.0F - MEAN[0]) / STD[0]);
               var9.put(((var4 >> 8 & 0xFF) / 255.0F - MEAN[1]) / STD[1]);
               var9.put(((var4 & 0xFF) / 255.0F - MEAN[2]) / STD[2]);
            }
         }

         ((Buffer)this.getInput()).rewind();
         var7.run(this.getInput(), this.out);
         float[] var10 = this.out[0];
         this.answer = new LaneModel.Answer(
            ArraysKt.copyOfRange(var10, 0, 4), ArraysKt.copyOfRange(var10, 4, 8), System.currentTimeMillis(), (System.nanoTime() - var5) / 1000000.0
         );
         String var11 = this.accel;
         LaneModel.Answer var12 = this.answer;
         Intrinsics.checkNotNull(var12);
         String var13 = String.format("%.0f", Arrays.copyOf(new Object[]{var12.getMs()}, 1));
         Intrinsics.checkNotNullExpressionValue(var13, "format(...)");
         this.status = "running (" + var11 + ", " + var13 + " ms)";
      }
   }

   // $VF: synthetic method
   public static boolean wants$default(LaneModel var0, long var1, int var3, Object var4) {
      if ((var3 & 1) != 0) {
         var1 = System.currentTimeMillis();
      }

      return var0.wants(var1);
   }

   public final void feed(Bitmap var1) {
      Intrinsics.checkNotNullParameter(var1, "bmp");
      if (this.busy) {
         var1.recycle();
      } else {
         this.busy = true;
         this.lastMs = System.currentTimeMillis();
         Thread var2 = new Thread(new LaneModel$$ExternalSyntheticLambda1(this, var1), "starview-lanemodel");
         var2.setPriority(3);
         var2.setDaemon(true);
         var2.start();
      }
   }

   public final String getAccel() {
      return this.accel;
   }

   public final LaneModel.Answer getAnswer() {
      return this.answer;
   }

   public final boolean getEnabled() {
      return this.enabled;
   }

   public final boolean getHasModel() {
      boolean var1;
      if (this.modelFile().length() > 1000000L) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public final String getStatus() {
      return this.status;
   }

   public final String importModel(Uri var1) {
      Intrinsics.checkNotNullParameter(var1, "uri");
      return LaneModelKt.importLaneModel(this.ctx, var1);
   }

   public final File modelFile() {
      return new File(this.ctx.getFilesDir(), "lane_count.lite");
   }

   public final void setEnabled(boolean var1) {
      this.enabled = var1;
   }

   public final void shutdown() {
      this.enabled = false;
      this.answer = null;
   }

   public final boolean wants(long var1) {
      boolean var5 = this.enabled;
      boolean var4 = false;
      boolean var3 = var4;
      if (var5) {
         var3 = var4;
         if (this.getHasModel()) {
            var3 = var4;
            if (!this.busy) {
               var3 = var4;
               if (var1 - this.lastMs >= 2000L) {
                  var3 = var4;
                  if (!StringsKt.startsWith$default(this.status, "error", false, 2, null)) {
                     var3 = true;
                  }
               }
            }
         }
      }

      return var3;
   }

   public static final class Answer {
      private final long atMs;
      private final double ms;
      private final float[] pL;
      private final float[] pR;

      public Answer(float[] var1, float[] var2, long var3, double var5) {
         Intrinsics.checkNotNullParameter(var1, "pL");
         Intrinsics.checkNotNullParameter(var2, "pR");
         super();
         this.pL = var1;
         this.pR = var2;
         this.atMs = var3;
         this.ms = var5;
      }

      public final String code() {
         StringCompanionObject var1 = StringCompanionObject.INSTANCE;
         String var2 = String.format(Locale.US, "%d,%d,%.2f,%.2f", Arrays.copyOf(new Object[]{this.getLeft(), this.getRight(), this.getQL(), this.getQR()}, 4));
         Intrinsics.checkNotNullExpressionValue(var2, "format(...)");
         return var2;
      }

      public final long getAtMs() {
         return this.atMs;
      }

      public final int getLeft() {
         Iterator var8 = ArraysKt.getIndices(this.pL).iterator();
         Object var5;
         if (!var8.hasNext()) {
            var5 = null;
         } else {
            var5 = (Integer)var8.next();
            if (var8.hasNext()) {
               int var4 = var5.intValue();
               float var1 = this.pL[var4];
               Object var6 = var5;

               do {
                  Object var7 = var8.next();
                  var4 = ((Number)var7).intValue();
                  float var3 = this.pL[var4];
                  var5 = (Integer)var6;
                  float var2 = var1;
                  if (Float.compare(var1, var3) < 0) {
                     var5 = (Integer)var7;
                     var2 = var3;
                  }

                  var6 = var5;
                  var1 = var2;
               } while (var8.hasNext());
            }
         }

         var5 = var5;
         int var10;
         if (var5 != null) {
            var10 = var5;
         } else {
            var10 = 0;
         }

         return var10;
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

      public final float getQL() {
         return this.pL[this.getLeft()];
      }

      public final float getQR() {
         return this.pR[this.getRight()];
      }

      public final int getRight() {
         Iterator var8 = ArraysKt.getIndices(this.pR).iterator();
         Object var5;
         if (!var8.hasNext()) {
            var5 = null;
         } else {
            var5 = (Integer)var8.next();
            if (var8.hasNext()) {
               int var4 = var5.intValue();
               float var2 = this.pR[var4];
               Object var6 = var5;

               do {
                  Object var7 = var8.next();
                  var4 = ((Number)var7).intValue();
                  float var3 = this.pR[var4];
                  var5 = (Integer)var6;
                  float var1 = var2;
                  if (Float.compare(var2, var3) < 0) {
                     var5 = (Integer)var7;
                     var1 = var3;
                  }

                  var6 = var5;
                  var2 = var1;
               } while (var8.hasNext());
            }
         }

         var5 = var5;
         int var10;
         if (var5 != null) {
            var10 = var5;
         } else {
            var10 = 0;
         }

         return var10;
      }

      public final String text() {
         int var3 = this.getLeft();
         String var6 = "3+";
         String var5;
         if (var3 == 3) {
            var5 = "3+";
         } else {
            var5 = String.valueOf(this.getLeft());
         }

         if (this.getRight() != 3) {
            var6 = String.valueOf(this.getRight());
         }

         float var1 = this.getQL();
         float var2 = 100;
         int var4 = (int)(var1 * var2);
         var3 = (int)(this.getQR() * var2);
         return var5 + " left · " + var6 + " right (" + var4 + "% / " + var3 + "%)";
      }
   }

   public static final class Companion {
      private Companion() {
      }

      // $VF: synthetic method
      public Companion(DefaultConstructorMarker var1) {
         this();
      }
   }
}
