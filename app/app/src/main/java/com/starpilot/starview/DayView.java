package com.starpilot.starview;

import android.graphics.Bitmap;
import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import kotlin.collections.ArraysKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

public final class DayView {
   private File dir;
   private volatile Ideas.Here here;
   private ArrayList<double[]> index;
   private long lastLookMs;
   private double lastSaveLat;
   private double lastSaveLon;
   private volatile Bitmap showing;
   private volatile long showingAtMs;
   private volatile boolean wanted;

   private final ArrayList<double[]> loadIndex(File var1) {
      ArrayList var4 = new ArrayList();
      String[] var7 = var1.list();
      if (var7 != null) {
         for (String var5 : var7) {
            Intrinsics.checkNotNull(var5);
            List var8 = StringsKt.split$default(StringsKt.removeSuffix(var5, ".jpg"), new String[]{"_"}, false, 0, 6, null);
            if (var8.size() == 4) {
               try {
                  var4.add(
                     new double[]{
                        Double.parseDouble((String)var8.get(0)),
                        Double.parseDouble((String)var8.get(1)),
                        Double.parseDouble((String)var8.get(2)),
                        Double.parseDouble((String)var8.get(3))
                     }
                  );
               } catch (Exception var6) {
               }
            }
         }
      }

      return var4;
   }

   private final void trim(File var1, ArrayList<double[]> var2) {
      File[] var8 = var1.listFiles();
      if (var8 != null) {
         int var4 = var8.length;
         long var5 = 0L;

         for (int var3 = 0; var3 < var4; var3++) {
            File var7 = var8[var3];
            var5 += var7.length();
         }

         if (var5 >= 419430400L) {
            for (File var12 : ArraysKt.sortedWith(var8, new Comparator() {
               @Override
               public final int compare(T var1, T var2x) {
                  var1 = ((File)var1).getName();
                  Intrinsics.checkNotNullExpressionValue(var1, "getName(...)");
                  Comparable var4x = StringsKt.substringAfterLast$default(var1, '_', null, 2, null);
                  var2x = ((File)var2x).getName();
                  Intrinsics.checkNotNullExpressionValue(var2x, "getName(...)");
                  return ComparisonsKt.compareValues(var4x, StringsKt.substringAfterLast$default(var2x, (char)95, null, 2, null));
               }
            })) {
               if (var5 < 367001600L) {
                  break;
               }

               var5 -= var12.length();
               var12.delete();
            }

            synchronized (var2) {
               var2.clear();
               var2.addAll(this.loadIndex(var1));
            }
         }
      }
   }

   public final void feed(Bitmap param1, float param2, long param3) {
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
      // 001: ldc "bmp"
      // 003: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
      // 006: aload 0
      // 007: getfield com/starpilot/starview/DayView.here Lcom/starpilot/starview/Ideas$Here;
      // 00a: astore 18
      // 00c: aload 18
      // 00e: ifnonnull 012
      // 011: return
      // 012: aload 0
      // 013: getfield com/starpilot/starview/DayView.dir Ljava/io/File;
      // 016: astore 17
      // 018: aload 17
      // 01a: ifnonnull 01e
      // 01d: return
      // 01e: aload 18
      // 020: invokevirtual com/starpilot/starview/Ideas$Here.getYaw ()D
      // 023: invokestatic java/lang/Double.isNaN (D)Z
      // 026: ifne 3c6
      // 029: aload 18
      // 02b: invokevirtual com/starpilot/starview/Ideas$Here.getRtk ()Ljava/lang/String;
      // 02e: ldc "none"
      // 030: invokestatic kotlin/jvm/internal/Intrinsics.areEqual (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 033: ifeq 043
      // 036: aload 18
      // 038: invokevirtual com/starpilot/starview/Ideas$Here.getV ()D
      // 03b: dconst_1
      // 03c: dcmpg
      // 03d: ifge 043
      // 040: goto 3c6
      // 043: aload 0
      // 044: getfield com/starpilot/starview/DayView.index Ljava/util/ArrayList;
      // 047: astore 16
      // 049: aload 16
      // 04b: astore 15
      // 04d: aload 16
      // 04f: ifnonnull 060
      // 052: aload 0
      // 053: aload 17
      // 055: invokespecial com/starpilot/starview/DayView.loadIndex (Ljava/io/File;)Ljava/util/ArrayList;
      // 058: astore 15
      // 05a: aload 0
      // 05b: aload 15
      // 05d: putfield com/starpilot/starview/DayView.index Ljava/util/ArrayList;
      // 060: fload 2
      // 061: ldc 90.0
      // 063: fcmpl
      // 064: ifle 1d0
      // 067: aload 18
      // 069: invokevirtual com/starpilot/starview/Ideas$Here.getV ()D
      // 06c: ldc2_w 3.0
      // 06f: dcmpl
      // 070: ifle 1d0
      // 073: aload 18
      // 075: invokevirtual com/starpilot/starview/Ideas$Here.getLat ()D
      // 078: aload 0
      // 079: getfield com/starpilot/starview/DayView.lastSaveLat D
      // 07c: dsub
      // 07d: ldc2_w 111195.0
      // 080: dmul
      // 081: aload 18
      // 083: invokevirtual com/starpilot/starview/Ideas$Here.getLon ()D
      // 086: aload 0
      // 087: getfield com/starpilot/starview/DayView.lastSaveLon D
      // 08a: dsub
      // 08b: ldc2_w 111320.0
      // 08e: dmul
      // 08f: aload 18
      // 091: invokevirtual com/starpilot/starview/Ideas$Here.getLat ()D
      // 094: invokestatic java/lang/Math.toRadians (D)D
      // 097: invokestatic java/lang/Math.cos (D)D
      // 09a: dmul
      // 09b: invokestatic java/lang/Math.hypot (DD)D
      // 09e: ldc2_w 15.0
      // 0a1: dcmpg
      // 0a2: ifge 0a6
      // 0a5: return
      // 0a6: aload 0
      // 0a7: aload 18
      // 0a9: invokevirtual com/starpilot/starview/Ideas$Here.getLat ()D
      // 0ac: putfield com/starpilot/starview/DayView.lastSaveLat D
      // 0af: aload 0
      // 0b0: aload 18
      // 0b2: invokevirtual com/starpilot/starview/Ideas$Here.getLon ()D
      // 0b5: putfield com/starpilot/starview/DayView.lastSaveLon D
      // 0b8: aload 1
      // 0b9: sipush 320
      // 0bc: aload 1
      // 0bd: invokevirtual android/graphics/Bitmap.getHeight ()I
      // 0c0: sipush 320
      // 0c3: imul
      // 0c4: aload 1
      // 0c5: invokevirtual android/graphics/Bitmap.getWidth ()I
      // 0c8: idiv
      // 0c9: bipush 1
      // 0ca: invokestatic android/graphics/Bitmap.createScaledBitmap (Landroid/graphics/Bitmap;IIZ)Landroid/graphics/Bitmap;
      // 0cd: astore 1
      // 0ce: aload 1
      // 0cf: ldc_w "createScaledBitmap(...)"
      // 0d2: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 0d5: getstatic java/util/Locale.US Ljava/util/Locale;
      // 0d8: ldc_w "%.6f_%.6f_%03d_%d.jpg"
      // 0db: bipush 4
      // 0dc: anewarray 4
      // 0df: dup
      // 0e0: bipush 0
      // 0e1: aload 18
      // 0e3: invokevirtual com/starpilot/starview/Ideas$Here.getLat ()D
      // 0e6: invokestatic java/lang/Double.valueOf (D)Ljava/lang/Double;
      // 0e9: aastore
      // 0ea: dup
      // 0eb: bipush 1
      // 0ec: aload 18
      // 0ee: invokevirtual com/starpilot/starview/Ideas$Here.getLon ()D
      // 0f1: invokestatic java/lang/Double.valueOf (D)Ljava/lang/Double;
      // 0f4: aastore
      // 0f5: dup
      // 0f6: bipush 2
      // 0f7: aload 18
      // 0f9: invokevirtual com/starpilot/starview/Ideas$Here.getHdgDeg ()D
      // 0fc: d2i
      // 0fd: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 100: aastore
      // 101: dup
      // 102: bipush 3
      // 103: lload 3
      // 104: invokestatic java/lang/Long.valueOf (J)Ljava/lang/Long;
      // 107: aastore
      // 108: bipush 4
      // 109: invokestatic java/util/Arrays.copyOf ([Ljava/lang/Object;I)[Ljava/lang/Object;
      // 10c: invokestatic java/lang/String.format (Ljava/util/Locale;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
      // 10f: astore 16
      // 111: aload 16
      // 113: ldc_w "format(...)"
      // 116: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 119: new java/io/FileOutputStream
      // 11c: astore 19
      // 11e: new java/io/File
      // 121: astore 20
      // 123: aload 20
      // 125: aload 17
      // 127: aload 16
      // 129: invokespecial java/io/File.<init> (Ljava/io/File;Ljava/lang/String;)V
      // 12c: aload 19
      // 12e: aload 20
      // 130: invokespecial java/io/FileOutputStream.<init> (Ljava/io/File;)V
      // 133: aload 19
      // 135: checkcast java/io/Closeable
      // 138: astore 16
      // 13a: aload 16
      // 13c: checkcast java/io/FileOutputStream
      // 13f: astore 19
      // 141: aload 1
      // 142: getstatic android/graphics/Bitmap$CompressFormat.JPEG Landroid/graphics/Bitmap$CompressFormat;
      // 145: bipush 60
      // 147: aload 19
      // 149: checkcast java/io/OutputStream
      // 14c: invokevirtual android/graphics/Bitmap.compress (Landroid/graphics/Bitmap$CompressFormat;ILjava/io/OutputStream;)Z
      // 14f: pop
      // 150: aload 16
      // 152: aconst_null
      // 153: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 156: goto 16c
      // 159: astore 20
      // 15b: aload 20
      // 15d: athrow
      // 15e: astore 19
      // 160: aload 16
      // 162: aload 20
      // 164: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 167: aload 19
      // 169: athrow
      // 16a: astore 16
      // 16c: aload 1
      // 16d: invokevirtual android/graphics/Bitmap.recycle ()V
      // 170: aload 15
      // 172: monitorenter
      // 173: aload 18
      // 175: invokevirtual com/starpilot/starview/Ideas$Here.getLat ()D
      // 178: dstore 5
      // 17a: aload 18
      // 17c: invokevirtual com/starpilot/starview/Ideas$Here.getLon ()D
      // 17f: dstore 7
      // 181: aload 18
      // 183: invokevirtual com/starpilot/starview/Ideas$Here.getHdgDeg ()D
      // 186: dstore 11
      // 188: lload 3
      // 189: l2d
      // 18a: dstore 9
      // 18c: aload 15
      // 18e: bipush 4
      // 18f: newarray 7
      // 191: dup
      // 192: bipush 0
      // 193: dload 5
      // 195: dastore
      // 196: dup
      // 197: bipush 1
      // 198: dload 7
      // 19a: dastore
      // 19b: dup
      // 19c: bipush 2
      // 19d: dload 11
      // 19f: dastore
      // 1a0: dup
      // 1a1: bipush 3
      // 1a2: dload 9
      // 1a4: dastore
      // 1a5: invokevirtual java/util/ArrayList.add (Ljava/lang/Object;)Z
      // 1a8: pop
      // 1a9: aload 15
      // 1ab: monitorexit
      // 1ac: aload 15
      // 1ae: invokevirtual java/util/ArrayList.size ()I
      // 1b1: sipush 200
      // 1b4: irem
      // 1b5: ifne 1c3
      // 1b8: aload 0
      // 1b9: aload 17
      // 1bb: aload 15
      // 1bd: invokespecial com/starpilot/starview/DayView.trim (Ljava/io/File;Ljava/util/ArrayList;)V
      // 1c0: goto 3c5
      // 1c3: goto 3c5
      // 1c6: astore 1
      // 1c7: goto 1cb
      // 1ca: astore 1
      // 1cb: aload 15
      // 1cd: monitorexit
      // 1ce: aload 1
      // 1cf: athrow
      // 1d0: fload 2
      // 1d1: ldc_w 45.0
      // 1d4: fcmpg
      // 1d5: ifge 3c5
      // 1d8: lload 3
      // 1d9: aload 0
      // 1da: getfield com/starpilot/starview/DayView.lastLookMs J
      // 1dd: lsub
      // 1de: ldc2_w 1000
      // 1e1: lcmp
      // 1e2: ifle 3c5
      // 1e5: aload 0
      // 1e6: lload 3
      // 1e7: putfield com/starpilot/starview/DayView.lastLookMs J
      // 1ea: aload 15
      // 1ec: monitorenter
      // 1ed: bipush 0
      // 1ee: istore 13
      // 1f0: aload 15
      // 1f2: checkcast java/lang/Iterable
      // 1f5: astore 16
      // 1f7: new java/util/ArrayList
      // 1fa: astore 1
      // 1fb: aload 1
      // 1fc: invokespecial java/util/ArrayList.<init> ()V
      // 1ff: aload 1
      // 200: checkcast java/util/Collection
      // 203: astore 1
      // 204: aload 16
      // 206: invokeinterface java/lang/Iterable.iterator ()Ljava/util/Iterator; 1
      // 20b: astore 16
      // 20d: aload 16
      // 20f: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 214: ifeq 248
      // 217: aload 16
      // 219: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 21e: astore 19
      // 220: aload 19
      // 222: checkcast [D
      // 225: astore 20
      // 227: getstatic com/starpilot/starview/IdeaSpots.INSTANCE Lcom/starpilot/starview/IdeaSpots;
      // 22a: aload 20
      // 22c: bipush 2
      // 22d: daload
      // 22e: aload 18
      // 230: invokevirtual com/starpilot/starview/Ideas$Here.getHdgDeg ()D
      // 233: ldc2_w 30.0
      // 236: invokevirtual com/starpilot/starview/IdeaSpots.headingOk (DDD)Z
      // 239: ifeq 245
      // 23c: aload 1
      // 23d: aload 19
      // 23f: invokeinterface java/util/Collection.add (Ljava/lang/Object;)Z 2
      // 244: pop
      // 245: goto 20d
      // 248: aload 1
      // 249: checkcast java/util/List
      // 24c: astore 1
      // 24d: aload 1
      // 24e: checkcast java/lang/Iterable
      // 251: astore 1
      // 252: aload 1
      // 253: invokeinterface java/lang/Iterable.iterator ()Ljava/util/Iterator; 1
      // 258: astore 19
      // 25a: aload 19
      // 25c: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 261: ifne 269
      // 264: aconst_null
      // 265: astore 1
      // 266: goto 311
      // 269: aload 19
      // 26b: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 270: astore 1
      // 271: aload 19
      // 273: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 278: ifne 27e
      // 27b: goto 311
      // 27e: aload 1
      // 27f: checkcast [D
      // 282: astore 16
      // 284: aload 16
      // 286: bipush 0
      // 287: daload
      // 288: aload 18
      // 28a: invokevirtual com/starpilot/starview/Ideas$Here.getLat ()D
      // 28d: dsub
      // 28e: ldc2_w 111195.0
      // 291: dmul
      // 292: aload 16
      // 294: bipush 1
      // 295: daload
      // 296: aload 18
      // 298: invokevirtual com/starpilot/starview/Ideas$Here.getLon ()D
      // 29b: dsub
      // 29c: ldc2_w 111320.0
      // 29f: dmul
      // 2a0: aload 18
      // 2a2: invokevirtual com/starpilot/starview/Ideas$Here.getLat ()D
      // 2a5: invokestatic java/lang/Math.toRadians (D)D
      // 2a8: invokestatic java/lang/Math.cos (D)D
      // 2ab: dmul
      // 2ac: invokestatic java/lang/Math.hypot (DD)D
      // 2af: dstore 5
      // 2b1: aload 19
      // 2b3: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 2b8: astore 16
      // 2ba: aload 16
      // 2bc: checkcast [D
      // 2bf: astore 20
      // 2c1: aload 20
      // 2c3: bipush 0
      // 2c4: daload
      // 2c5: aload 18
      // 2c7: invokevirtual com/starpilot/starview/Ideas$Here.getLat ()D
      // 2ca: dsub
      // 2cb: ldc2_w 111195.0
      // 2ce: dmul
      // 2cf: aload 20
      // 2d1: bipush 1
      // 2d2: daload
      // 2d3: aload 18
      // 2d5: invokevirtual com/starpilot/starview/Ideas$Here.getLon ()D
      // 2d8: dsub
      // 2d9: ldc2_w 111320.0
      // 2dc: dmul
      // 2dd: aload 18
      // 2df: invokevirtual com/starpilot/starview/Ideas$Here.getLat ()D
      // 2e2: invokestatic java/lang/Math.toRadians (D)D
      // 2e5: invokestatic java/lang/Math.cos (D)D
      // 2e8: dmul
      // 2e9: invokestatic java/lang/Math.hypot (DD)D
      // 2ec: dstore 9
      // 2ee: dload 5
      // 2f0: dstore 7
      // 2f2: dload 5
      // 2f4: dload 9
      // 2f6: invokestatic java/lang/Double.compare (DD)I
      // 2f9: ifle 303
      // 2fc: aload 16
      // 2fe: astore 1
      // 2ff: dload 9
      // 301: dstore 7
      // 303: aload 19
      // 305: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 30a: istore 14
      // 30c: iload 14
      // 30e: ifne 3b8
      // 311: aload 1
      // 312: checkcast [D
      // 315: astore 1
      // 316: aload 15
      // 318: monitorexit
      // 319: aload 1
      // 31a: ifnull 3b1
      // 31d: aload 1
      // 31e: bipush 0
      // 31f: daload
      // 320: aload 18
      // 322: invokevirtual com/starpilot/starview/Ideas$Here.getLat ()D
      // 325: dsub
      // 326: ldc2_w 111195.0
      // 329: dmul
      // 32a: aload 1
      // 32b: bipush 1
      // 32c: daload
      // 32d: aload 18
      // 32f: invokevirtual com/starpilot/starview/Ideas$Here.getLon ()D
      // 332: dsub
      // 333: ldc2_w 111320.0
      // 336: dmul
      // 337: aload 18
      // 339: invokevirtual com/starpilot/starview/Ideas$Here.getLat ()D
      // 33c: invokestatic java/lang/Math.toRadians (D)D
      // 33f: invokestatic java/lang/Math.cos (D)D
      // 342: dmul
      // 343: invokestatic java/lang/Math.hypot (DD)D
      // 346: ldc2_w 12.0
      // 349: dcmpg
      // 34a: ifge 3b1
      // 34d: getstatic java/util/Locale.US Ljava/util/Locale;
      // 350: ldc_w "%.6f_%.6f_%03d_%d.jpg"
      // 353: bipush 4
      // 354: anewarray 4
      // 357: dup
      // 358: bipush 0
      // 359: aload 1
      // 35a: bipush 0
      // 35b: daload
      // 35c: invokestatic java/lang/Double.valueOf (D)Ljava/lang/Double;
      // 35f: aastore
      // 360: dup
      // 361: bipush 1
      // 362: aload 1
      // 363: bipush 1
      // 364: daload
      // 365: invokestatic java/lang/Double.valueOf (D)Ljava/lang/Double;
      // 368: aastore
      // 369: dup
      // 36a: bipush 2
      // 36b: aload 1
      // 36c: bipush 2
      // 36d: daload
      // 36e: d2i
      // 36f: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 372: aastore
      // 373: dup
      // 374: bipush 3
      // 375: aload 1
      // 376: bipush 3
      // 377: daload
      // 378: d2l
      // 379: invokestatic java/lang/Long.valueOf (J)Ljava/lang/Long;
      // 37c: aastore
      // 37d: bipush 4
      // 37e: invokestatic java/util/Arrays.copyOf ([Ljava/lang/Object;I)[Ljava/lang/Object;
      // 381: invokestatic java/lang/String.format (Ljava/util/Locale;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
      // 384: astore 1
      // 385: aload 1
      // 386: ldc_w "format(...)"
      // 389: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 38c: new java/io/File
      // 38f: dup
      // 390: aload 17
      // 392: aload 1
      // 393: invokespecial java/io/File.<init> (Ljava/io/File;Ljava/lang/String;)V
      // 396: invokevirtual java/io/File.getPath ()Ljava/lang/String;
      // 399: invokestatic android/graphics/BitmapFactory.decodeFile (Ljava/lang/String;)Landroid/graphics/Bitmap;
      // 39c: astore 1
      // 39d: aload 1
      // 39e: ifnull 3ae
      // 3a1: aload 0
      // 3a2: aload 1
      // 3a3: putfield com/starpilot/starview/DayView.showing Landroid/graphics/Bitmap;
      // 3a6: aload 0
      // 3a7: lload 3
      // 3a8: putfield com/starpilot/starview/DayView.showingAtMs J
      // 3ab: goto 3c5
      // 3ae: goto 3c5
      // 3b1: goto 3c5
      // 3b4: astore 1
      // 3b5: goto 3c0
      // 3b8: dload 7
      // 3ba: dstore 5
      // 3bc: goto 2b1
      // 3bf: astore 1
      // 3c0: aload 15
      // 3c2: monitorexit
      // 3c3: aload 1
      // 3c4: athrow
      // 3c5: return
      // 3c6: return
   }

   public final Ideas.Here getHere() {
      return this.here;
   }

   public final Bitmap getShowing() {
      return this.showing;
   }

   public final long getShowingAtMs() {
      return this.showingAtMs;
   }

   public final boolean getWanted() {
      return this.wanted;
   }

   public final void init(File var1) {
      Intrinsics.checkNotNullParameter(var1, "d");
      var1.mkdirs();
      this.dir = var1;
   }

   public final void setHere(Ideas.Here var1) {
      this.here = var1;
   }

   public final void setShowing(Bitmap var1) {
      this.showing = var1;
   }

   public final void setShowingAtMs(long var1) {
      this.showingAtMs = var1;
   }

   public final void setWanted(boolean var1) {
      this.wanted = var1;
   }
}
