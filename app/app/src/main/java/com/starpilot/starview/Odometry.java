package com.starpilot.starview;

import android.content.Context;
import android.content.SharedPreferences;
import kotlin.collections.ArrayDeque;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

public final class Odometry {
   private static final long HIST_NS = 8000000000L;
   public static final Odometry INSTANCE = new Odometry();
   private static double dist;
   private static int gyroAgree;
   private static final ArrayDeque<Odometry.Pose> hist = new ArrayDeque<>();
   private static long lastMono;
   private static final Object lock = new Object();
   private static volatile SharedPreferences prefs;
   private static int savedAgree;
   private static double x;
   private static double y;
   private static double yaw;
   private static volatile String yawSrc = "steering";

   private Odometry() {
   }

   private final void saveAgree() {
      SharedPreferences var4 = prefs;
      if (var4 != null) {
         int var1 = gyroAgree;
         boolean var3 = true;
         boolean var5;
         if (var1 > 5) {
            var5 = true;
         } else {
            var5 = false;
         }

         boolean var2;
         if (savedAgree > 5) {
            var2 = true;
         } else {
            var2 = false;
         }

         if (var5 == var2) {
            if (gyroAgree < -5) {
               var5 = true;
            } else {
               var5 = false;
            }

            if (savedAgree < -5) {
               var2 = var3;
            } else {
               var2 = false;
            }

            if (var5 == var2 && Math.abs(gyroAgree - savedAgree) < 20) {
               return;
            }
         }

         savedAgree = gyroAgree;
         var4.edit().putInt("gyro_agree", gyroAgree).apply();
      }
   }

   public final double[] at(long param1) {
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
      // 000: getstatic com/starpilot/starview/Odometry.lock Ljava/lang/Object;
      // 003: astore 28
      // 005: aload 28
      // 007: monitorenter
      // 008: getstatic com/starpilot/starview/Odometry.hist Lkotlin/collections/ArrayDeque;
      // 00b: invokevirtual kotlin/collections/ArrayDeque.isEmpty ()Z
      // 00e: ifeq 028
      // 011: aload 28
      // 013: monitorexit
      // 014: bipush 4
      // 015: newarray 7
      // 017: dup
      // 018: bipush 0
      // 019: dconst_0
      // 01a: dastore
      // 01b: dup
      // 01c: bipush 1
      // 01d: dconst_0
      // 01e: dastore
      // 01f: dup
      // 020: bipush 2
      // 021: dconst_0
      // 022: dastore
      // 023: dup
      // 024: bipush 3
      // 025: dconst_0
      // 026: dastore
      // 027: areturn
      // 028: getstatic com/starpilot/starview/Odometry.hist Lkotlin/collections/ArrayDeque;
      // 02b: invokevirtual kotlin/collections/ArrayDeque.last ()Ljava/lang/Object;
      // 02e: checkcast com/starpilot/starview/Odometry$Pose
      // 031: astore 29
      // 033: lload 1
      // 034: aload 29
      // 036: invokevirtual com/starpilot/starview/Odometry$Pose.getTNs ()J
      // 039: lcmp
      // 03a: iflt 0e2
      // 03d: lload 1
      // 03e: aload 29
      // 040: invokevirtual com/starpilot/starview/Odometry$Pose.getTNs ()J
      // 043: lsub
      // 044: l2d
      // 045: ldc2_w 1.0E9
      // 048: ddiv
      // 049: dconst_0
      // 04a: ldc2_w 0.25
      // 04d: invokestatic kotlin/ranges/RangesKt.coerceIn (DDD)D
      // 050: dstore 7
      // 052: aload 29
      // 054: invokevirtual com/starpilot/starview/Odometry$Pose.getYaw ()D
      // 057: dstore 15
      // 059: aload 29
      // 05b: invokevirtual com/starpilot/starview/Odometry$Pose.getYawRate ()D
      // 05e: dstore 5
      // 060: aload 29
      // 062: invokevirtual com/starpilot/starview/Odometry$Pose.getYaw ()D
      // 065: aload 29
      // 067: invokevirtual com/starpilot/starview/Odometry$Pose.getYawRate ()D
      // 06a: dload 7
      // 06c: dmul
      // 06d: bipush 2
      // 06e: i2d
      // 06f: ddiv
      // 070: dadd
      // 071: dstore 19
      // 073: aload 29
      // 075: invokevirtual com/starpilot/starview/Odometry$Pose.getX ()D
      // 078: dstore 3
      // 079: aload 29
      // 07b: invokevirtual com/starpilot/starview/Odometry$Pose.getV ()D
      // 07e: dstore 11
      // 080: dload 19
      // 082: invokestatic java/lang/Math.cos (D)D
      // 085: dstore 13
      // 087: aload 29
      // 089: invokevirtual com/starpilot/starview/Odometry$Pose.getY ()D
      // 08c: dstore 17
      // 08e: aload 29
      // 090: invokevirtual com/starpilot/starview/Odometry$Pose.getV ()D
      // 093: dstore 9
      // 095: dload 19
      // 097: invokestatic java/lang/Math.sin (D)D
      // 09a: dstore 19
      // 09c: aload 29
      // 09e: invokevirtual com/starpilot/starview/Odometry$Pose.getDist ()D
      // 0a1: dstore 21
      // 0a3: aload 29
      // 0a5: invokevirtual com/starpilot/starview/Odometry$Pose.getV ()D
      // 0a8: dstore 23
      // 0aa: aload 28
      // 0ac: monitorexit
      // 0ad: bipush 4
      // 0ae: newarray 7
      // 0b0: dup
      // 0b1: bipush 0
      // 0b2: dload 3
      // 0b3: dload 11
      // 0b5: dload 13
      // 0b7: dmul
      // 0b8: dload 7
      // 0ba: dmul
      // 0bb: dadd
      // 0bc: dastore
      // 0bd: dup
      // 0be: bipush 1
      // 0bf: dload 17
      // 0c1: dload 9
      // 0c3: dload 19
      // 0c5: dmul
      // 0c6: dload 7
      // 0c8: dmul
      // 0c9: dadd
      // 0ca: dastore
      // 0cb: dup
      // 0cc: bipush 2
      // 0cd: dload 15
      // 0cf: dload 5
      // 0d1: dload 7
      // 0d3: dmul
      // 0d4: dadd
      // 0d5: dastore
      // 0d6: dup
      // 0d7: bipush 3
      // 0d8: dload 21
      // 0da: dload 23
      // 0dc: dload 7
      // 0de: dmul
      // 0df: dadd
      // 0e0: dastore
      // 0e1: areturn
      // 0e2: getstatic com/starpilot/starview/Odometry.hist Lkotlin/collections/ArrayDeque;
      // 0e5: invokevirtual kotlin/collections/ArrayDeque.first ()Ljava/lang/Object;
      // 0e8: checkcast com/starpilot/starview/Odometry$Pose
      // 0eb: astore 29
      // 0ed: lload 1
      // 0ee: aload 29
      // 0f0: invokevirtual com/starpilot/starview/Odometry$Pose.getTNs ()J
      // 0f3: lcmp
      // 0f4: ifgt 12c
      // 0f7: aload 29
      // 0f9: invokevirtual com/starpilot/starview/Odometry$Pose.getX ()D
      // 0fc: dstore 9
      // 0fe: aload 29
      // 100: invokevirtual com/starpilot/starview/Odometry$Pose.getY ()D
      // 103: dstore 5
      // 105: aload 29
      // 107: invokevirtual com/starpilot/starview/Odometry$Pose.getYaw ()D
      // 10a: dstore 7
      // 10c: aload 29
      // 10e: invokevirtual com/starpilot/starview/Odometry$Pose.getDist ()D
      // 111: dstore 3
      // 112: aload 28
      // 114: monitorexit
      // 115: bipush 4
      // 116: newarray 7
      // 118: dup
      // 119: bipush 0
      // 11a: dload 9
      // 11c: dastore
      // 11d: dup
      // 11e: bipush 1
      // 11f: dload 5
      // 121: dastore
      // 122: dup
      // 123: bipush 2
      // 124: dload 7
      // 126: dastore
      // 127: dup
      // 128: bipush 3
      // 129: dload 3
      // 12a: dastore
      // 12b: areturn
      // 12c: getstatic com/starpilot/starview/Odometry.hist Lkotlin/collections/ArrayDeque;
      // 12f: invokevirtual kotlin/collections/ArrayDeque.size ()I
      // 132: bipush 1
      // 133: isub
      // 134: istore 27
      // 136: iload 27
      // 138: ifle 156
      // 13b: getstatic com/starpilot/starview/Odometry.hist Lkotlin/collections/ArrayDeque;
      // 13e: iload 27
      // 140: bipush 1
      // 141: isub
      // 142: invokevirtual kotlin/collections/ArrayDeque.get (I)Ljava/lang/Object;
      // 145: checkcast com/starpilot/starview/Odometry$Pose
      // 148: invokevirtual com/starpilot/starview/Odometry$Pose.getTNs ()J
      // 14b: lload 1
      // 14c: lcmp
      // 14d: ifle 156
      // 150: iinc 27 -1
      // 153: goto 136
      // 156: getstatic com/starpilot/starview/Odometry.hist Lkotlin/collections/ArrayDeque;
      // 159: iload 27
      // 15b: bipush 1
      // 15c: isub
      // 15d: invokevirtual kotlin/collections/ArrayDeque.get (I)Ljava/lang/Object;
      // 160: checkcast com/starpilot/starview/Odometry$Pose
      // 163: astore 30
      // 165: getstatic com/starpilot/starview/Odometry.hist Lkotlin/collections/ArrayDeque;
      // 168: iload 27
      // 16a: invokevirtual kotlin/collections/ArrayDeque.get (I)Ljava/lang/Object;
      // 16d: checkcast com/starpilot/starview/Odometry$Pose
      // 170: astore 29
      // 172: lload 1
      // 173: aload 30
      // 175: invokevirtual com/starpilot/starview/Odometry$Pose.getTNs ()J
      // 178: lsub
      // 179: l2d
      // 17a: lconst_1
      // 17b: aload 29
      // 17d: invokevirtual com/starpilot/starview/Odometry$Pose.getTNs ()J
      // 180: aload 30
      // 182: invokevirtual com/starpilot/starview/Odometry$Pose.getTNs ()J
      // 185: lsub
      // 186: invokestatic java/lang/Math.max (JJ)J
      // 189: l2d
      // 18a: ddiv
      // 18b: dstore 13
      // 18d: aload 30
      // 18f: invokevirtual com/starpilot/starview/Odometry$Pose.getX ()D
      // 192: dstore 9
      // 194: aload 29
      // 196: invokevirtual com/starpilot/starview/Odometry$Pose.getX ()D
      // 199: dstore 17
      // 19b: aload 30
      // 19d: invokevirtual com/starpilot/starview/Odometry$Pose.getX ()D
      // 1a0: dstore 19
      // 1a2: aload 30
      // 1a4: invokevirtual com/starpilot/starview/Odometry$Pose.getY ()D
      // 1a7: dstore 21
      // 1a9: aload 29
      // 1ab: invokevirtual com/starpilot/starview/Odometry$Pose.getY ()D
      // 1ae: dstore 11
      // 1b0: aload 30
      // 1b2: invokevirtual com/starpilot/starview/Odometry$Pose.getY ()D
      // 1b5: dstore 3
      // 1b6: aload 30
      // 1b8: invokevirtual com/starpilot/starview/Odometry$Pose.getYaw ()D
      // 1bb: dstore 5
      // 1bd: aload 29
      // 1bf: invokevirtual com/starpilot/starview/Odometry$Pose.getYaw ()D
      // 1c2: aload 30
      // 1c4: invokevirtual com/starpilot/starview/Odometry$Pose.getYaw ()D
      // 1c7: dsub
      // 1c8: ldc2_w 6.283185307179586
      // 1cb: invokestatic java/lang/Math.IEEEremainder (DD)D
      // 1ce: dstore 15
      // 1d0: aload 30
      // 1d2: invokevirtual com/starpilot/starview/Odometry$Pose.getDist ()D
      // 1d5: dstore 23
      // 1d7: aload 29
      // 1d9: invokevirtual com/starpilot/starview/Odometry$Pose.getDist ()D
      // 1dc: dstore 7
      // 1de: aload 30
      // 1e0: invokevirtual com/starpilot/starview/Odometry$Pose.getDist ()D
      // 1e3: dstore 25
      // 1e5: aload 28
      // 1e7: monitorexit
      // 1e8: bipush 4
      // 1e9: newarray 7
      // 1eb: dup
      // 1ec: bipush 0
      // 1ed: dload 9
      // 1ef: dload 17
      // 1f1: dload 19
      // 1f3: dsub
      // 1f4: dload 13
      // 1f6: dmul
      // 1f7: dadd
      // 1f8: dastore
      // 1f9: dup
      // 1fa: bipush 1
      // 1fb: dload 21
      // 1fd: dload 11
      // 1ff: dload 3
      // 200: dsub
      // 201: dload 13
      // 203: dmul
      // 204: dadd
      // 205: dastore
      // 206: dup
      // 207: bipush 2
      // 208: dload 5
      // 20a: dload 15
      // 20c: dload 13
      // 20e: dmul
      // 20f: dadd
      // 210: dastore
      // 211: dup
      // 212: bipush 3
      // 213: dload 23
      // 215: dload 7
      // 217: dload 25
      // 219: dsub
      // 21a: dload 13
      // 21c: dmul
      // 21d: dadd
      // 21e: dastore
      // 21f: areturn
      // 220: astore 29
      // 222: aload 28
      // 224: monitorexit
      // 225: aload 29
      // 227: athrow
   }

   public final int getGyroSign() {
      byte var1;
      if (gyroAgree > 5) {
         var1 = 1;
      } else {
         var1 = -1;
      }

      return var1;
   }

   public final String getYawSrc() {
      return yawSrc;
   }

   public final void init(Context var1) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      SharedPreferences var2 = var1.getApplicationContext().getSharedPreferences("starview", 0);
      prefs = var2;
      gyroAgree = RangesKt.coerceIn(var2.getInt("gyro_agree", 0), -50, 50);
      savedAgree = gyroAgree;
   }

   public final Long monoToLocal(long param1) {
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
      // 00: getstatic com/starpilot/starview/Odometry.lock Ljava/lang/Object;
      // 03: astore 6
      // 05: aload 6
      // 07: monitorenter
      // 08: ldc2_w 9223372036854775807
      // 0b: lstore 3
      // 0c: getstatic com/starpilot/starview/Odometry.hist Lkotlin/collections/ArrayDeque;
      // 0f: invokevirtual kotlin/collections/ArrayDeque.iterator ()Ljava/util/Iterator;
      // 12: astore 7
      // 14: aload 7
      // 16: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 1b: ifeq 47
      // 1e: aload 7
      // 20: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 25: checkcast com/starpilot/starview/Odometry$Pose
      // 28: astore 5
      // 2a: aload 5
      // 2c: invokevirtual com/starpilot/starview/Odometry$Pose.getMono ()J
      // 2f: lconst_0
      // 30: lcmp
      // 31: ifeq 14
      // 34: lload 3
      // 35: aload 5
      // 37: invokevirtual com/starpilot/starview/Odometry$Pose.getTNs ()J
      // 3a: aload 5
      // 3c: invokevirtual com/starpilot/starview/Odometry$Pose.getMono ()J
      // 3f: lsub
      // 40: invokestatic java/lang/Math.min (JJ)J
      // 43: lstore 3
      // 44: goto 14
      // 47: lload 3
      // 48: ldc2_w 9223372036854775807
      // 4b: lcmp
      // 4c: ifne 55
      // 4f: aconst_null
      // 50: astore 5
      // 52: goto 5d
      // 55: lload 1
      // 56: lload 3
      // 57: ladd
      // 58: invokestatic java/lang/Long.valueOf (J)Ljava/lang/Long;
      // 5b: astore 5
      // 5d: aload 6
      // 5f: monitorexit
      // 60: aload 5
      // 62: areturn
      // 63: astore 5
      // 65: aload 6
      // 67: monitorexit
      // 68: aload 5
      // 6a: athrow
   }

   public final double[] motion() {
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
      // 00: getstatic com/starpilot/starview/Odometry.lock Ljava/lang/Object;
      // 03: astore 6
      // 05: aload 6
      // 07: monitorenter
      // 08: getstatic com/starpilot/starview/Odometry.hist Lkotlin/collections/ArrayDeque;
      // 0b: invokevirtual kotlin/collections/ArrayDeque.lastOrNull ()Ljava/lang/Object;
      // 0e: checkcast com/starpilot/starview/Odometry$Pose
      // 11: astore 5
      // 13: aload 5
      // 15: ifnull 36
      // 18: aload 5
      // 1a: invokevirtual com/starpilot/starview/Odometry$Pose.getV ()D
      // 1d: dstore 3
      // 1e: aload 5
      // 20: invokevirtual com/starpilot/starview/Odometry$Pose.getYawRate ()D
      // 23: dstore 1
      // 24: bipush 2
      // 25: newarray 7
      // 27: astore 5
      // 29: aload 5
      // 2b: bipush 0
      // 2c: dload 3
      // 2d: dastore
      // 2e: aload 5
      // 30: bipush 1
      // 31: dload 1
      // 32: dastore
      // 33: goto 46
      // 36: bipush 2
      // 37: newarray 7
      // 39: astore 5
      // 3b: aload 5
      // 3d: dup
      // 3e: bipush 0
      // 3f: dconst_0
      // 40: dastore
      // 41: dup
      // 42: bipush 1
      // 43: dconst_0
      // 44: dastore
      // 45: pop
      // 46: aload 6
      // 48: monitorexit
      // 49: aload 5
      // 4b: areturn
      // 4c: astore 5
      // 4e: aload 6
      // 50: monitorexit
      // 51: aload 5
      // 53: athrow
   }

   public final void onCarState(long param1, Telemetry param3) {
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
      // 000: aload 3
      // 001: ldc "t"
      // 003: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
      // 006: invokestatic java/lang/System.nanoTime ()J
      // 009: lstore 19
      // 00b: getstatic com/starpilot/starview/Odometry.lastMono J
      // 00e: lconst_0
      // 00f: lcmp
      // 010: ifne 019
      // 013: dconst_0
      // 014: dstore 4
      // 016: goto 03a
      // 019: lload 1
      // 01a: getstatic com/starpilot/starview/Odometry.lastMono J
      // 01d: lsub
      // 01e: l2d
      // 01f: ldc2_w 1.0E9
      // 022: ddiv
      // 023: dstore 6
      // 025: dload 6
      // 027: dconst_0
      // 028: dcmpg
      // 029: iflt 037
      // 02c: dload 6
      // 02e: dstore 4
      // 030: dload 6
      // 032: dconst_1
      // 033: dcmpl
      // 034: ifle 03a
      // 037: dconst_0
      // 038: dstore 4
      // 03a: lload 1
      // 03b: putstatic com/starpilot/starview/Odometry.lastMono J
      // 03e: aload 3
      // 03f: invokevirtual com/starpilot/starview/Telemetry.getGear ()Ljava/lang/String;
      // 042: ldc "reverse"
      // 044: invokestatic kotlin/jvm/internal/Intrinsics.areEqual (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 047: ifeq 058
      // 04a: aload 3
      // 04b: invokevirtual com/starpilot/starview/Telemetry.getVEgo ()F
      // 04e: f2d
      // 04f: invokestatic java/lang/Math.abs (D)D
      // 052: dneg
      // 053: dstore 8
      // 055: goto 063
      // 058: aload 3
      // 059: invokevirtual com/starpilot/starview/Telemetry.getVEgo ()F
      // 05c: f2d
      // 05d: dconst_0
      // 05e: invokestatic kotlin/ranges/RangesKt.coerceAtLeast (DD)D
      // 061: dstore 8
      // 063: aload 3
      // 064: invokevirtual com/starpilot/starview/Telemetry.getSteerRatioLive ()F
      // 067: ldc_w 5.0
      // 06a: fcmpl
      // 06b: ifle 078
      // 06e: aload 3
      // 06f: invokevirtual com/starpilot/starview/Telemetry.getSteerRatioLive ()F
      // 072: f2d
      // 073: dstore 6
      // 075: goto 07d
      // 078: ldc2_w 15.5
      // 07b: dstore 6
      // 07d: aload 3
      // 07e: invokevirtual com/starpilot/starview/Telemetry.getSteeringAngleDeg ()F
      // 081: aload 3
      // 082: invokevirtual com/starpilot/starview/Telemetry.getAngleOffsetDeg ()F
      // 085: fsub
      // 086: f2d
      // 087: invokestatic java/lang/Math.toRadians (D)D
      // 08a: dload 6
      // 08c: ddiv
      // 08d: invokestatic java/lang/Math.tan (D)D
      // 090: dload 8
      // 092: dmul
      // 093: ldc2_w 3.03
      // 096: ddiv
      // 097: dstore 6
      // 099: bipush 1
      // 09a: istore 17
      // 09c: dload 6
      // 09e: bipush 1
      // 09f: i2d
      // 0a0: dload 8
      // 0a2: dload 8
      // 0a4: dmul
      // 0a5: ldc2_w 900.0
      // 0a8: ddiv
      // 0a9: dadd
      // 0aa: ddiv
      // 0ab: dstore 10
      // 0ad: aload 3
      // 0ae: invokevirtual com/starpilot/starview/Telemetry.getPoseYawZ ()F
      // 0b1: f2d
      // 0b2: dstore 12
      // 0b4: dload 10
      // 0b6: dstore 6
      // 0b8: dload 12
      // 0ba: invokestatic java/lang/Double.isInfinite (D)Z
      // 0bd: ifne 0ce
      // 0c0: dload 12
      // 0c2: invokestatic java/lang/Double.isNaN (D)Z
      // 0c5: ifne 0ce
      // 0c8: bipush 1
      // 0c9: istore 16
      // 0cb: goto 0d1
      // 0ce: bipush 0
      // 0cf: istore 16
      // 0d1: iload 16
      // 0d3: ifeq 15c
      // 0d6: lload 19
      // 0d8: aload 3
      // 0d9: invokevirtual com/starpilot/starview/Telemetry.getPoseAtNs ()J
      // 0dc: lsub
      // 0dd: ldc2_w 500000000
      // 0e0: lcmp
      // 0e1: ifge 15c
      // 0e4: dload 8
      // 0e6: ldc2_w 3.0
      // 0e9: dcmpl
      // 0ea: ifle 131
      // 0ed: dload 10
      // 0ef: invokestatic java/lang/Math.abs (D)D
      // 0f2: ldc2_w 0.04
      // 0f5: dcmpl
      // 0f6: ifle 131
      // 0f9: dload 12
      // 0fb: invokestatic java/lang/Math.abs (D)D
      // 0fe: ldc2_w 0.04
      // 101: dcmpl
      // 102: ifle 131
      // 105: getstatic com/starpilot/starview/Odometry.gyroAgree I
      // 108: istore 18
      // 10a: dload 12
      // 10c: dload 10
      // 10e: dmul
      // 10f: dconst_0
      // 110: dcmpl
      // 111: ifle 11b
      // 114: iload 17
      // 116: istore 16
      // 118: goto 11e
      // 11b: bipush -1
      // 11c: istore 16
      // 11e: iload 18
      // 120: iload 16
      // 122: iadd
      // 123: bipush -50
      // 125: bipush 50
      // 127: invokestatic kotlin/ranges/RangesKt.coerceIn (III)I
      // 12a: putstatic com/starpilot/starview/Odometry.gyroAgree I
      // 12d: aload 0
      // 12e: invokespecial com/starpilot/starview/Odometry.saveAgree ()V
      // 131: getstatic com/starpilot/starview/Odometry.gyroAgree I
      // 134: bipush 5
      // 135: if_icmple 13f
      // 138: dload 12
      // 13a: dstore 6
      // 13c: goto 144
      // 13f: dload 12
      // 141: dneg
      // 142: dstore 6
      // 144: getstatic com/starpilot/starview/Odometry.gyroAgree I
      // 147: invokestatic java/lang/Math.abs (I)I
      // 14a: bipush 5
      // 14b: if_icmple 155
      // 14e: ldc_w "gyro"
      // 151: astore 3
      // 152: goto 159
      // 155: ldc_w "gyro (direction not learned yet)"
      // 158: astore 3
      // 159: goto 15f
      // 15c: ldc "steering"
      // 15e: astore 3
      // 15f: dload 8
      // 161: invokestatic java/lang/Math.abs (D)D
      // 164: ldc2_w 0.2
      // 167: dcmpg
      // 168: ifge 16e
      // 16b: dconst_0
      // 16c: dstore 6
      // 16e: aload 3
      // 16f: putstatic com/starpilot/starview/Odometry.yawSrc Ljava/lang/String;
      // 172: getstatic com/starpilot/starview/Odometry.lock Ljava/lang/Object;
      // 175: astore 21
      // 177: aload 21
      // 179: monitorenter
      // 17a: getstatic com/starpilot/starview/Odometry.yaw D
      // 17d: dload 6
      // 17f: dload 4
      // 181: dmul
      // 182: dadd
      // 183: putstatic com/starpilot/starview/Odometry.yaw D
      // 186: getstatic com/starpilot/starview/Odometry.x D
      // 189: getstatic com/starpilot/starview/Odometry.yaw D
      // 18c: invokestatic java/lang/Math.cos (D)D
      // 18f: dload 8
      // 191: dmul
      // 192: dload 4
      // 194: dmul
      // 195: dadd
      // 196: putstatic com/starpilot/starview/Odometry.x D
      // 199: getstatic com/starpilot/starview/Odometry.y D
      // 19c: getstatic com/starpilot/starview/Odometry.yaw D
      // 19f: invokestatic java/lang/Math.sin (D)D
      // 1a2: dload 8
      // 1a4: dmul
      // 1a5: dload 4
      // 1a7: dmul
      // 1a8: dadd
      // 1a9: putstatic com/starpilot/starview/Odometry.y D
      // 1ac: getstatic com/starpilot/starview/Odometry.dist D
      // 1af: dload 8
      // 1b1: invokestatic java/lang/Math.abs (D)D
      // 1b4: dload 4
      // 1b6: dmul
      // 1b7: dadd
      // 1b8: putstatic com/starpilot/starview/Odometry.dist D
      // 1bb: getstatic com/starpilot/starview/Odometry.hist Lkotlin/collections/ArrayDeque;
      // 1be: astore 3
      // 1bf: new com/starpilot/starview/Odometry$Pose
      // 1c2: astore 22
      // 1c4: getstatic com/starpilot/starview/Odometry.x D
      // 1c7: dstore 4
      // 1c9: getstatic com/starpilot/starview/Odometry.y D
      // 1cc: dstore 10
      // 1ce: getstatic com/starpilot/starview/Odometry.yaw D
      // 1d1: dstore 14
      // 1d3: getstatic com/starpilot/starview/Odometry.dist D
      // 1d6: dstore 12
      // 1d8: aload 22
      // 1da: lload 19
      // 1dc: dload 4
      // 1de: dload 10
      // 1e0: dload 14
      // 1e2: dload 8
      // 1e4: dload 6
      // 1e6: dload 12
      // 1e8: lload 1
      // 1e9: invokespecial com/starpilot/starview/Odometry$Pose.<init> (JDDDDDDJ)V
      // 1ec: aload 3
      // 1ed: aload 22
      // 1ef: invokevirtual kotlin/collections/ArrayDeque.addLast (Ljava/lang/Object;)V
      // 1f2: getstatic com/starpilot/starview/Odometry.hist Lkotlin/collections/ArrayDeque;
      // 1f5: invokevirtual kotlin/collections/ArrayDeque.size ()I
      // 1f8: bipush 2
      // 1f9: if_icmple 21c
      // 1fc: lload 19
      // 1fe: getstatic com/starpilot/starview/Odometry.hist Lkotlin/collections/ArrayDeque;
      // 201: invokevirtual kotlin/collections/ArrayDeque.first ()Ljava/lang/Object;
      // 204: checkcast com/starpilot/starview/Odometry$Pose
      // 207: invokevirtual com/starpilot/starview/Odometry$Pose.getTNs ()J
      // 20a: lsub
      // 20b: ldc2_w 8000000000
      // 20e: lcmp
      // 20f: ifle 21c
      // 212: getstatic com/starpilot/starview/Odometry.hist Lkotlin/collections/ArrayDeque;
      // 215: invokevirtual kotlin/collections/ArrayDeque.removeFirst ()Ljava/lang/Object;
      // 218: pop
      // 219: goto 1f2
      // 21c: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 21f: astore 3
      // 220: aload 21
      // 222: monitorexit
      // 223: return
      // 224: astore 3
      // 225: goto 229
      // 228: astore 3
      // 229: aload 21
      // 22b: monitorexit
      // 22c: aload 3
      // 22d: athrow
   }

   private static final class Pose {
      private final double dist;
      private final long mono;
      private final long tNs;
      private final double v;
      private final double x;
      private final double y;
      private final double yaw;
      private final double yawRate;

      public Pose(long var1, double var3, double var5, double var7, double var9, double var11, double var13, long var15) {
         this.tNs = var1;
         this.x = var3;
         this.y = var5;
         this.yaw = var7;
         this.v = var9;
         this.yawRate = var11;
         this.dist = var13;
         this.mono = var15;
      }

      // $VF: synthetic method
      public Pose(
         long var1, double var3, double var5, double var7, double var9, double var11, double var13, long var15, int var17, DefaultConstructorMarker var18
      ) {
         if ((var17 & 128) != 0) {
            var15 = 0L;
         }

         this(var1, var3, var5, var7, var9, var11, var13, var15);
      }

      public final double getDist() {
         return this.dist;
      }

      public final long getMono() {
         return this.mono;
      }

      public final long getTNs() {
         return this.tNs;
      }

      public final double getV() {
         return this.v;
      }

      public final double getX() {
         return this.x;
      }

      public final double getY() {
         return this.y;
      }

      public final double getYaw() {
         return this.yaw;
      }

      public final double getYawRate() {
         return this.yawRate;
      }
   }
}
