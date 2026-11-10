package com.starpilot.starview;

import kotlin.collections.ArrayDeque;

public final class Rate {
   private final ArrayDeque<Long> q = new ArrayDeque<>();

   private final void trim(long var1) {
      while (!this.q.isEmpty() && var1 - this.q.first().longValue() > 5000000000L) {
         this.q.removeFirst();
      }
   }

   public final float hz(long param1) {
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
      // 01: monitorenter
      // 02: aload 0
      // 03: lload 1
      // 04: invokespecial com/starpilot/starview/Rate.trim (J)V
      // 07: aload 0
      // 08: getfield com/starpilot/starview/Rate.q Lkotlin/collections/ArrayDeque;
      // 0b: invokevirtual kotlin/collections/ArrayDeque.size ()I
      // 0e: istore 4
      // 10: iload 4
      // 12: i2f
      // 13: ldc 5.0
      // 15: fdiv
      // 16: fstore 3
      // 17: aload 0
      // 18: monitorexit
      // 19: fload 3
      // 1a: freturn
      // 1b: astore 5
      // 1d: aload 0
      // 1e: monitorexit
      // 1f: aload 5
      // 21: athrow
   }

   public final void tick(long param1) {
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
      // 01: monitorenter
      // 02: aload 0
      // 03: getfield com/starpilot/starview/Rate.q Lkotlin/collections/ArrayDeque;
      // 06: lload 1
      // 07: invokestatic java/lang/Long.valueOf (J)Ljava/lang/Long;
      // 0a: invokevirtual kotlin/collections/ArrayDeque.addLast (Ljava/lang/Object;)V
      // 0d: aload 0
      // 0e: lload 1
      // 0f: invokespecial com/starpilot/starview/Rate.trim (J)V
      // 12: aload 0
      // 13: monitorexit
      // 14: return
      // 15: astore 3
      // 16: aload 0
      // 17: monitorexit
      // 18: aload 3
      // 19: athrow
   }
}
