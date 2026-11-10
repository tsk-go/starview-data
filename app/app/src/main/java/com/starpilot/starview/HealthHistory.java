package com.starpilot.starview;

import java.util.List;
import kotlin.collections.ArrayDeque;
import kotlin.collections.CollectionsKt;

public final class HealthHistory {
   public static final HealthHistory INSTANCE = new HealthHistory();
   private static long lastMs;
   private static final ArrayDeque<HealthHistory.Point> ring = new ArrayDeque<>();

   private HealthHistory() {
   }

   public final void add(int param1, float param2) {
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
      // 00: invokestatic java/lang/System.currentTimeMillis ()J
      // 03: lstore 3
      // 04: lload 3
      // 05: getstatic com/starpilot/starview/HealthHistory.lastMs J
      // 08: lsub
      // 09: ldc2_w 10000
      // 0c: lcmp
      // 0d: ifge 11
      // 10: return
      // 11: lload 3
      // 12: putstatic com/starpilot/starview/HealthHistory.lastMs J
      // 15: getstatic com/starpilot/starview/HealthHistory.ring Lkotlin/collections/ArrayDeque;
      // 18: astore 5
      // 1a: aload 5
      // 1c: monitorenter
      // 1d: getstatic com/starpilot/starview/HealthHistory.ring Lkotlin/collections/ArrayDeque;
      // 20: astore 7
      // 22: new com/starpilot/starview/HealthHistory$Point
      // 25: astore 6
      // 27: aload 6
      // 29: lload 3
      // 2a: iload 1
      // 2b: fload 2
      // 2c: invokespecial com/starpilot/starview/HealthHistory$Point.<init> (JIF)V
      // 2f: aload 7
      // 31: aload 6
      // 33: invokevirtual kotlin/collections/ArrayDeque.addLast (Ljava/lang/Object;)V
      // 36: getstatic com/starpilot/starview/HealthHistory.ring Lkotlin/collections/ArrayDeque;
      // 39: invokevirtual kotlin/collections/ArrayDeque.size ()I
      // 3c: sipush 720
      // 3f: if_icmple 4c
      // 42: getstatic com/starpilot/starview/HealthHistory.ring Lkotlin/collections/ArrayDeque;
      // 45: invokevirtual kotlin/collections/ArrayDeque.removeFirst ()Ljava/lang/Object;
      // 48: pop
      // 49: goto 36
      // 4c: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 4f: astore 6
      // 51: aload 5
      // 53: monitorexit
      // 54: return
      // 55: astore 6
      // 57: aload 5
      // 59: monitorexit
      // 5a: aload 6
      // 5c: athrow
   }

   public final List<HealthHistory.Point> points() {
      ArrayDeque var1 = ring;
      List var2;
      synchronized (var1) {
         var2 = CollectionsKt.toList(ring);
      }

      return var2;
   }

   public static final class Point {
      private final int memPct;
      private final long tMs;
      private final float tempC;

      public Point(long var1, int var3, float var4) {
         this.tMs = var1;
         this.memPct = var3;
         this.tempC = var4;
      }

      public final int getMemPct() {
         return this.memPct;
      }

      public final long getTMs() {
         return this.tMs;
      }

      public final float getTempC() {
         return this.tempC;
      }
   }
}
