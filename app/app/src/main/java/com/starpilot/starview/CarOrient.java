package com.starpilot.starview;

import android.content.Context;
import android.util.Log;
import java.io.InputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import kotlin.io.ByteStreamsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.tensorflow.lite.Interpreter;
import org.tensorflow.lite.Interpreter.Options;

public final class CarOrient {
   public static final String ASSET = "det/car_orient_96.lite";
   private static final float[] CENTRES;
   public static final CarOrient.Companion Companion = new CarOrient.Companion(null);
   private static final float[] MEAN = new float[]{0.485F, 0.456F, 0.406F};
   public static final int NB = 16;
   public static final int R = 96;
   private static final float[] STD = new float[]{0.229F, 0.224F, 0.225F};
   private static final String TAG = "StarView/Orient";
   private final Context ctx;
   private volatile String failed;
   private final ByteBuffer inBuf;
   private Interpreter interp;
   private volatile double ms;
   private final float[][] out;
   private final float[] pad;
   private final float[] px;

   static {
      float[] var1 = new float[16];

      for (int var0 = 0; var0 < 16; var0++) {
         var1[var0] = (float)((var0 + 0.5) * 2 * Math.PI / 16 - Math.PI);
      }

      CENTRES = var1;
   }

   public CarOrient(Context var1) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      super();
      this.ctx = var1;
      this.failed = "";
      ByteBuffer var4 = ByteBuffer.allocateDirect(110592).order(ByteOrder.nativeOrder());
      Intrinsics.checkNotNullExpressionValue(var4, "order(...)");
      this.inBuf = var4;
      this.px = new float[27648];
      float[][] var5 = new float[1][];
      byte var3 = 0;

      for (int var2 = 0; var2 < 1; var2++) {
         var5[var2] = new float[16];
      }

      this.out = var5;
      float[] var6 = new float[3];

      for (int var7 = var3; var7 < 3; var7++) {
         var6[var7] = (0.44705883F - MEAN[var7]) / STD[var7];
      }

      this.pad = var6;
   }

   // $VF: synthetic method
   public static float[] classify$default(
      CarOrient var0,
      byte[] var1,
      byte[] var2,
      byte[] var3,
      int var4,
      int var5,
      int var6,
      int var7,
      int var8,
      float var9,
      float var10,
      float var11,
      float var12,
      float var13,
      int var14,
      Object var15
   ) {
      if ((var14 & 4096) != 0) {
         var13 = 1.0F;
      }

      return var0.classify(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13);
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not properly define all variable types!
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private final Interpreter ensure() {
      Interpreter var2 = this.interp;
      if (var2 != null) {
         return var2;
      } else {
         boolean var1;
         if (this.failed.length() > 0) {
            var1 = true;
         } else {
            var1 = false;
         }

         Object var3 = null;
         if (var1) {
            return null;
         } else {
            label221: {
               Throwable var5;
               label230: {
                  try {
                     var37 = this.ctx.getAssets().open("det/car_orient_96.lite");
                  } catch (Throwable var35) {
                     var5 = var35;
                     Log.w("StarView/Orient", "load: " + var35);
                     String var4 = var35.getMessage();
                     var36 = var4;
                     if (var4 != null) {
                        break label221;
                     }
                     break label230;
                  }

                  try {
                     InputStream var41 = var37;
                     Intrinsics.checkNotNull(var41);
                     var44 = ByteStreamsKt.readBytes(var41);
                  } catch (Throwable var34) {
                     Throwable var39 = var34;

                     try {
                        throw var39;
                     } finally {
                        try {
                           CloseableKt.closeFinally(var37, var39);
                           throw var5_3;
                        } catch (Throwable var32) {
                           var5 = var32;
                           Log.w("StarView/Orient", "load: " + var32);
                           String var40 = var32.getMessage();
                           var36 = var40;
                           if (var40 != null) {
                              break label221;
                           }
                           break label230;
                        }
                     }
                  }

                  label214:
                  try {
                     CloseableKt.closeFinally(var37, null);
                     ByteBuffer var43 = ByteBuffer.allocateDirect(var44.length).order(ByteOrder.nativeOrder());
                     var43.put(var44);
                     ((Buffer)var43).rewind();
                     Options var45 = new Options();
                     var2 = new Interpreter(var43, var45.setNumThreads(2));
                     this.interp = var2;
                     return var2;
                  } catch (Throwable var33) {
                     var5 = var33;
                     Log.w("StarView/Orient", "load: " + var33);
                     String var42 = var33.getMessage();
                     var36 = var42;
                     if (var42 != null) {
                        break label221;
                     }
                     break label214;
                  }
               }

               var36 = var5.toString();
            }

            this.failed = var36;
            return (Interpreter)var3;
         }
      }
   }

   public final float[] classify(
      byte[] param1,
      byte[] param2,
      byte[] param3,
      int param4,
      int param5,
      int param6,
      int param7,
      int param8,
      float param9,
      float param10,
      float param11,
      float param12,
      float param13
   ) {
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
      // 000: aload 1
      // 001: ldc "y"
      // 003: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
      // 006: aload 2
      // 007: ldc "u"
      // 009: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
      // 00c: aload 3
      // 00d: ldc "v"
      // 00f: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
      // 012: aload 0
      // 013: invokespecial com/starpilot/starview/CarOrient.ensure ()Lorg/tensorflow/lite/Interpreter;
      // 016: astore 37
      // 018: aload 37
      // 01a: ifnonnull 01f
      // 01d: aconst_null
      // 01e: areturn
      // 01f: invokestatic java/lang/System.nanoTime ()J
      // 022: lstore 35
      // 024: fload 11
      // 026: fload 9
      // 028: fsub
      // 029: ldc 0.05
      // 02b: fmul
      // 02c: fstore 18
      // 02e: ldc 0.05
      // 030: fload 12
      // 032: fload 10
      // 034: fsub
      // 035: fmul
      // 036: fstore 17
      // 038: fconst_0
      // 039: fload 9
      // 03b: fload 18
      // 03d: fsub
      // 03e: invokestatic java/lang/Math.max (FF)F
      // 041: fstore 9
      // 043: fconst_0
      // 044: fload 10
      // 046: fload 17
      // 048: fsub
      // 049: invokestatic java/lang/Math.max (FF)F
      // 04c: fstore 16
      // 04e: iload 7
      // 050: i2f
      // 051: fconst_1
      // 052: fsub
      // 053: fload 11
      // 055: fload 18
      // 057: fadd
      // 058: invokestatic java/lang/Math.min (FF)F
      // 05b: fstore 10
      // 05d: iload 8
      // 05f: i2f
      // 060: fconst_1
      // 061: fsub
      // 062: fload 12
      // 064: fload 17
      // 066: fadd
      // 067: invokestatic java/lang/Math.min (FF)F
      // 06a: fstore 11
      // 06c: fload 10
      // 06e: fload 9
      // 070: fsub
      // 071: fload 13
      // 073: fmul
      // 074: fstore 18
      // 076: fload 11
      // 078: fload 16
      // 07a: fsub
      // 07b: fstore 11
      // 07d: fload 18
      // 07f: ldc_w 8.0
      // 082: fcmpg
      // 083: iflt 419
      // 086: fload 11
      // 088: ldc_w 8.0
      // 08b: fcmpg
      // 08c: ifge 092
      // 08f: goto 419
      // 092: bipush 96
      // 094: i2f
      // 095: fstore 17
      // 097: fload 17
      // 099: fload 18
      // 09b: fload 11
      // 09d: invokestatic java/lang/Math.max (FF)F
      // 0a0: fdiv
      // 0a1: fstore 12
      // 0a3: fload 18
      // 0a5: fload 12
      // 0a7: fmul
      // 0a8: fstore 20
      // 0aa: fload 11
      // 0ac: fload 12
      // 0ae: fmul
      // 0af: fstore 19
      // 0b1: fload 17
      // 0b3: fload 20
      // 0b5: fsub
      // 0b6: fconst_2
      // 0b7: fdiv
      // 0b8: fstore 18
      // 0ba: fload 17
      // 0bc: fload 19
      // 0be: fsub
      // 0bf: fconst_2
      // 0c0: fdiv
      // 0c1: fstore 17
      // 0c3: fconst_1
      // 0c4: fload 12
      // 0c6: fload 13
      // 0c8: fmul
      // 0c9: fdiv
      // 0ca: fstore 21
      // 0cc: fconst_1
      // 0cd: fload 12
      // 0cf: fdiv
      // 0d0: fstore 22
      // 0d2: bipush 0
      // 0d3: istore 32
      // 0d5: bipush 0
      // 0d6: istore 28
      // 0d8: fload 16
      // 0da: fstore 12
      // 0dc: fload 9
      // 0de: fstore 13
      // 0e0: fload 10
      // 0e2: fstore 9
      // 0e4: fload 17
      // 0e6: fstore 10
      // 0e8: iload 28
      // 0ea: bipush 96
      // 0ec: if_icmpge 357
      // 0ef: iload 28
      // 0f1: i2f
      // 0f2: fload 10
      // 0f4: fcmpl
      // 0f5: iflt 10a
      // 0f8: iload 28
      // 0fa: i2f
      // 0fb: fload 10
      // 0fd: fload 19
      // 0ff: fadd
      // 100: fcmpg
      // 101: ifge 10a
      // 104: bipush 1
      // 105: istore 27
      // 107: goto 10d
      // 10a: bipush 0
      // 10b: istore 27
      // 10d: iload 28
      // 10f: i2f
      // 110: ldc_w 0.5
      // 113: fadd
      // 114: fload 10
      // 116: fsub
      // 117: fload 22
      // 119: fmul
      // 11a: fload 12
      // 11c: fadd
      // 11d: ldc_w 0.5
      // 120: fsub
      // 121: fstore 16
      // 123: fload 16
      // 125: f2d
      // 126: invokestatic java/lang/Math.floor (D)D
      // 129: d2f
      // 12a: f2i
      // 12b: bipush 0
      // 12c: iload 8
      // 12e: bipush 1
      // 12f: isub
      // 130: invokestatic kotlin/ranges/RangesKt.coerceIn (III)I
      // 133: istore 33
      // 135: iload 8
      // 137: bipush 1
      // 138: isub
      // 139: iload 33
      // 13b: bipush 1
      // 13c: iadd
      // 13d: invokestatic java/lang/Math.min (II)I
      // 140: istore 30
      // 142: fload 16
      // 144: fload 16
      // 146: f2d
      // 147: invokestatic java/lang/Math.floor (D)D
      // 14a: d2f
      // 14b: fsub
      // 14c: fconst_0
      // 14d: fconst_1
      // 14e: invokestatic kotlin/ranges/RangesKt.coerceIn (FFF)F
      // 151: fstore 17
      // 153: bipush 0
      // 154: istore 31
      // 156: iload 27
      // 158: istore 29
      // 15a: iload 31
      // 15c: bipush 96
      // 15e: if_icmpge 351
      // 161: iload 29
      // 163: ifeq 316
      // 166: iload 31
      // 168: i2f
      // 169: fload 18
      // 16b: fcmpg
      // 16c: iflt 316
      // 16f: iload 31
      // 171: i2f
      // 172: fload 18
      // 174: fload 20
      // 176: fadd
      // 177: fcmpl
      // 178: iflt 17e
      // 17b: goto 316
      // 17e: iload 31
      // 180: i2f
      // 181: ldc_w 0.5
      // 184: fadd
      // 185: fload 18
      // 187: fsub
      // 188: fload 21
      // 18a: fmul
      // 18b: fload 13
      // 18d: fadd
      // 18e: ldc_w 0.5
      // 191: fsub
      // 192: fstore 23
      // 194: fload 23
      // 196: f2d
      // 197: invokestatic java/lang/Math.floor (D)D
      // 19a: d2f
      // 19b: f2i
      // 19c: bipush 0
      // 19d: iload 7
      // 19f: bipush 1
      // 1a0: isub
      // 1a1: invokestatic kotlin/ranges/RangesKt.coerceIn (III)I
      // 1a4: istore 27
      // 1a6: iload 7
      // 1a8: bipush 1
      // 1a9: isub
      // 1aa: iload 27
      // 1ac: bipush 1
      // 1ad: iadd
      // 1ae: invokestatic java/lang/Math.min (II)I
      // 1b1: istore 34
      // 1b3: fload 23
      // 1b5: fload 23
      // 1b7: f2d
      // 1b8: invokestatic java/lang/Math.floor (D)D
      // 1bb: d2f
      // 1bc: fsub
      // 1bd: fconst_0
      // 1be: fconst_1
      // 1bf: invokestatic kotlin/ranges/RangesKt.coerceIn (FFF)F
      // 1c2: fstore 24
      // 1c4: aload 1
      // 1c5: iload 33
      // 1c7: iload 4
      // 1c9: imul
      // 1ca: iload 27
      // 1cc: iadd
      // 1cd: baload
      // 1ce: sipush 255
      // 1d1: iand
      // 1d2: i2f
      // 1d3: fstore 25
      // 1d5: bipush 1
      // 1d6: i2f
      // 1d7: fstore 26
      // 1d9: fload 26
      // 1db: fload 17
      // 1dd: fsub
      // 1de: fload 25
      // 1e0: fload 26
      // 1e2: fload 24
      // 1e4: fsub
      // 1e5: fmul
      // 1e6: aload 1
      // 1e7: iload 33
      // 1e9: iload 4
      // 1eb: imul
      // 1ec: iload 34
      // 1ee: iadd
      // 1ef: baload
      // 1f0: sipush 255
      // 1f3: iand
      // 1f4: i2f
      // 1f5: fload 24
      // 1f7: fmul
      // 1f8: fadd
      // 1f9: fmul
      // 1fa: aload 1
      // 1fb: iload 30
      // 1fd: iload 4
      // 1ff: imul
      // 200: iload 27
      // 202: iadd
      // 203: baload
      // 204: sipush 255
      // 207: iand
      // 208: i2f
      // 209: fload 26
      // 20b: fload 24
      // 20d: fsub
      // 20e: fmul
      // 20f: aload 1
      // 210: iload 30
      // 212: iload 4
      // 214: imul
      // 215: iload 34
      // 217: iadd
      // 218: baload
      // 219: sipush 255
      // 21c: iand
      // 21d: i2f
      // 21e: fload 24
      // 220: fmul
      // 221: fadd
      // 222: fload 17
      // 224: fmul
      // 225: fadd
      // 226: ldc_w 16.0
      // 229: fsub
      // 22a: fstore 24
      // 22c: fload 16
      // 22e: ldc_w 0.5
      // 231: fadd
      // 232: f2i
      // 233: bipush 0
      // 234: iload 8
      // 236: bipush 1
      // 237: isub
      // 238: invokestatic kotlin/ranges/RangesKt.coerceIn (III)I
      // 23b: bipush 1
      // 23c: ishr
      // 23d: iload 5
      // 23f: imul
      // 240: fload 23
      // 242: ldc_w 0.5
      // 245: fadd
      // 246: f2i
      // 247: bipush 0
      // 248: iload 7
      // 24a: bipush 1
      // 24b: isub
      // 24c: invokestatic kotlin/ranges/RangesKt.coerceIn (III)I
      // 24f: bipush 1
      // 250: ishr
      // 251: iload 6
      // 253: imul
      // 254: iadd
      // 255: istore 27
      // 257: aload 2
      // 258: iload 27
      // 25a: baload
      // 25b: sipush 255
      // 25e: iand
      // 25f: i2f
      // 260: ldc_w 128.0
      // 263: fsub
      // 264: fstore 26
      // 266: aload 3
      // 267: iload 27
      // 269: baload
      // 26a: sipush 255
      // 26d: iand
      // 26e: i2f
      // 26f: ldc_w 128.0
      // 272: fsub
      // 273: fstore 25
      // 275: fload 24
      // 277: ldc_w 1.164
      // 27a: fmul
      // 27b: ldc_w 1.596
      // 27e: fload 25
      // 280: fmul
      // 281: fadd
      // 282: fconst_0
      // 283: ldc_w 255.0
      // 286: invokestatic kotlin/ranges/RangesKt.coerceIn (FFF)F
      // 289: fstore 23
      // 28b: fload 24
      // 28d: ldc_w 1.164
      // 290: fmul
      // 291: ldc_w 0.391
      // 294: fload 26
      // 296: fmul
      // 297: fsub
      // 298: ldc_w 0.813
      // 29b: fload 25
      // 29d: fmul
      // 29e: fsub
      // 29f: fconst_0
      // 2a0: ldc_w 255.0
      // 2a3: invokestatic kotlin/ranges/RangesKt.coerceIn (FFF)F
      // 2a6: fstore 25
      // 2a8: ldc_w 1.164
      // 2ab: fload 24
      // 2ad: fmul
      // 2ae: ldc_w 2.018
      // 2b1: fload 26
      // 2b3: fmul
      // 2b4: fadd
      // 2b5: fconst_0
      // 2b6: ldc_w 255.0
      // 2b9: invokestatic kotlin/ranges/RangesKt.coerceIn (FFF)F
      // 2bc: fstore 24
      // 2be: aload 0
      // 2bf: getfield com/starpilot/starview/CarOrient.px [F
      // 2c2: iload 32
      // 2c4: fload 23
      // 2c6: ldc_w 255.0
      // 2c9: fdiv
      // 2ca: getstatic com/starpilot/starview/CarOrient.MEAN [F
      // 2cd: bipush 0
      // 2ce: faload
      // 2cf: fsub
      // 2d0: getstatic com/starpilot/starview/CarOrient.STD [F
      // 2d3: bipush 0
      // 2d4: faload
      // 2d5: fdiv
      // 2d6: fastore
      // 2d7: aload 0
      // 2d8: getfield com/starpilot/starview/CarOrient.px [F
      // 2db: iload 32
      // 2dd: bipush 1
      // 2de: iadd
      // 2df: fload 25
      // 2e1: ldc_w 255.0
      // 2e4: fdiv
      // 2e5: getstatic com/starpilot/starview/CarOrient.MEAN [F
      // 2e8: bipush 1
      // 2e9: faload
      // 2ea: fsub
      // 2eb: getstatic com/starpilot/starview/CarOrient.STD [F
      // 2ee: bipush 1
      // 2ef: faload
      // 2f0: fdiv
      // 2f1: fastore
      // 2f2: aload 0
      // 2f3: getfield com/starpilot/starview/CarOrient.px [F
      // 2f6: iload 32
      // 2f8: bipush 2
      // 2f9: iadd
      // 2fa: fload 24
      // 2fc: ldc_w 255.0
      // 2ff: fdiv
      // 300: getstatic com/starpilot/starview/CarOrient.MEAN [F
      // 303: bipush 2
      // 304: faload
      // 305: fsub
      // 306: getstatic com/starpilot/starview/CarOrient.STD [F
      // 309: bipush 2
      // 30a: faload
      // 30b: fdiv
      // 30c: fastore
      // 30d: iload 32
      // 30f: bipush 3
      // 310: iadd
      // 311: istore 27
      // 313: goto 347
      // 316: aload 0
      // 317: getfield com/starpilot/starview/CarOrient.px [F
      // 31a: iload 32
      // 31c: aload 0
      // 31d: getfield com/starpilot/starview/CarOrient.pad [F
      // 320: bipush 0
      // 321: faload
      // 322: fastore
      // 323: aload 0
      // 324: getfield com/starpilot/starview/CarOrient.px [F
      // 327: iload 32
      // 329: bipush 1
      // 32a: iadd
      // 32b: aload 0
      // 32c: getfield com/starpilot/starview/CarOrient.pad [F
      // 32f: bipush 1
      // 330: faload
      // 331: fastore
      // 332: aload 0
      // 333: getfield com/starpilot/starview/CarOrient.px [F
      // 336: iload 32
      // 338: bipush 2
      // 339: iadd
      // 33a: aload 0
      // 33b: getfield com/starpilot/starview/CarOrient.pad [F
      // 33e: bipush 2
      // 33f: faload
      // 340: fastore
      // 341: iload 32
      // 343: bipush 3
      // 344: iadd
      // 345: istore 27
      // 347: iinc 31 1
      // 34a: iload 27
      // 34c: istore 32
      // 34e: goto 15a
      // 351: iinc 28 1
      // 354: goto 0e8
      // 357: aload 0
      // 358: getfield com/starpilot/starview/CarOrient.inBuf Ljava/nio/ByteBuffer;
      // 35b: invokevirtual java/nio/ByteBuffer.rewind ()Ljava/nio/Buffer;
      // 35e: pop
      // 35f: aload 0
      // 360: getfield com/starpilot/starview/CarOrient.inBuf Ljava/nio/ByteBuffer;
      // 363: invokevirtual java/nio/ByteBuffer.asFloatBuffer ()Ljava/nio/FloatBuffer;
      // 366: aload 0
      // 367: getfield com/starpilot/starview/CarOrient.px [F
      // 36a: invokevirtual java/nio/FloatBuffer.put ([F)Ljava/nio/FloatBuffer;
      // 36d: pop
      // 36e: aload 0
      // 36f: getfield com/starpilot/starview/CarOrient.inBuf Ljava/nio/ByteBuffer;
      // 372: invokevirtual java/nio/ByteBuffer.rewind ()Ljava/nio/Buffer;
      // 375: pop
      // 376: aload 0
      // 377: getfield com/starpilot/starview/CarOrient.inBuf Ljava/nio/ByteBuffer;
      // 37a: astore 2
      // 37b: aload 0
      // 37c: getfield com/starpilot/starview/CarOrient.out [[F
      // 37f: astore 1
      // 380: aload 37
      // 382: aload 2
      // 383: aload 1
      // 384: invokevirtual org/tensorflow/lite/Interpreter.run (Ljava/lang/Object;Ljava/lang/Object;)V
      // 387: invokestatic java/lang/System.nanoTime ()J
      // 38a: lload 35
      // 38c: lsub
      // 38d: l2d
      // 38e: ldc2_w 1000000.0
      // 391: ddiv
      // 392: dstore 14
      // 394: aload 0
      // 395: getfield com/starpilot/starview/CarOrient.ms D
      // 398: dconst_0
      // 399: dcmpg
      // 39a: ifne 3a3
      // 39d: bipush 1
      // 39e: istore 4
      // 3a0: goto 3a6
      // 3a3: bipush 0
      // 3a4: istore 4
      // 3a6: iload 4
      // 3a8: ifeq 3ae
      // 3ab: goto 3bf
      // 3ae: aload 0
      // 3af: getfield com/starpilot/starview/CarOrient.ms D
      // 3b2: ldc2_w 0.9
      // 3b5: dmul
      // 3b6: ldc2_w 0.1
      // 3b9: dload 14
      // 3bb: dmul
      // 3bc: dadd
      // 3bd: dstore 14
      // 3bf: aload 0
      // 3c0: dload 14
      // 3c2: putfield com/starpilot/starview/CarOrient.ms D
      // 3c5: aload 0
      // 3c6: getfield com/starpilot/starview/CarOrient.out [[F
      // 3c9: bipush 0
      // 3ca: aaload
      // 3cb: astore 1
      // 3cc: aload 1
      // 3cd: aload 1
      // 3ce: arraylength
      // 3cf: invokestatic java/util/Arrays.copyOf ([FI)[F
      // 3d2: astore 1
      // 3d3: aload 1
      // 3d4: ldc_w "copyOf(...)"
      // 3d7: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 3da: goto 417
      // 3dd: astore 2
      // 3de: goto 3e2
      // 3e1: astore 2
      // 3e2: ldc "StarView/Orient"
      // 3e4: new java/lang/StringBuilder
      // 3e7: dup
      // 3e8: invokespecial java/lang/StringBuilder.<init> ()V
      // 3eb: ldc_w "run: "
      // 3ee: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 3f1: aload 2
      // 3f2: invokevirtual java/lang/StringBuilder.append (Ljava/lang/Object;)Ljava/lang/StringBuilder;
      // 3f5: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 3f8: invokestatic android/util/Log.w (Ljava/lang/String;Ljava/lang/String;)I
      // 3fb: pop
      // 3fc: aload 2
      // 3fd: invokevirtual java/lang/Throwable.getMessage ()Ljava/lang/String;
      // 400: astore 3
      // 401: aload 3
      // 402: astore 1
      // 403: aload 3
      // 404: ifnonnull 40c
      // 407: aload 2
      // 408: invokevirtual java/lang/Throwable.toString ()Ljava/lang/String;
      // 40b: astore 1
      // 40c: aload 0
      // 40d: aload 1
      // 40e: putfield com/starpilot/starview/CarOrient.failed Ljava/lang/String;
      // 411: aload 0
      // 412: invokevirtual com/starpilot/starview/CarOrient.close ()V
      // 415: aconst_null
      // 416: astore 1
      // 417: aload 1
      // 418: areturn
      // 419: aconst_null
      // 41a: areturn
   }

   public final void close() {
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
      // 01: getfield com/starpilot/starview/CarOrient.interp Lorg/tensorflow/lite/Interpreter;
      // 04: astore 1
      // 05: aload 1
      // 06: ifnull 11
      // 09: aload 1
      // 0a: invokevirtual org/tensorflow/lite/Interpreter.close ()V
      // 0d: goto 11
      // 10: astore 1
      // 11: aload 0
      // 12: aconst_null
      // 13: putfield com/starpilot/starview/CarOrient.interp Lorg/tensorflow/lite/Interpreter;
      // 16: return
   }

   public final String getFailed() {
      return this.failed;
   }

   public final double getMs() {
      return this.ms;
   }

   public static final class Companion {
      private Companion() {
      }

      // $VF: synthetic method
      public Companion(DefaultConstructorMarker var1) {
         this();
      }

      public final float[] getCENTRES() {
         return CarOrient.CENTRES;
      }
   }
}
