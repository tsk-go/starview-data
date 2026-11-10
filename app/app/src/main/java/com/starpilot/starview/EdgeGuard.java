package com.starpilot.starview;

import android.view.View;

public final class EdgeGuard {
   public static final EdgeGuard INSTANCE = new EdgeGuard();

   private EdgeGuard() {
   }

   public final boolean inSystemGestureZone(View param1, float param2, float param3) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index 2 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:385)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:551)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1051)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:501)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
      //
      // Bytecode:
      // 000: aload 1
      // 001: ldc "v"
      // 003: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
      // 006: aload 1
      // 007: invokevirtual android/view/View.getResources ()Landroid/content/res/Resources;
      // 00a: invokevirtual android/content/res/Resources.getDisplayMetrics ()Landroid/util/DisplayMetrics;
      // 00d: getfield android/util/DisplayMetrics.density F
      // 010: fstore 4
      // 012: bipush 64
      // 014: i2f
      // 015: fload 4
      // 017: fmul
      // 018: fstore 9
      // 01a: bipush 28
      // 01c: i2f
      // 01d: fload 4
      // 01f: fmul
      // 020: fstore 8
      // 022: bipush 36
      // 024: i2f
      // 025: fload 4
      // 027: fmul
      // 028: fstore 7
      // 02a: fload 9
      // 02c: fstore 4
      // 02e: fload 8
      // 030: fstore 5
      // 032: aload 1
      // 033: invokevirtual android/view/View.getRootWindowInsets ()Landroid/view/WindowInsets;
      // 036: astore 11
      // 038: fload 9
      // 03a: fstore 6
      // 03c: fload 8
      // 03e: fstore 5
      // 040: fload 7
      // 042: fstore 4
      // 044: aload 11
      // 046: ifnull 0bb
      // 049: fload 9
      // 04b: fstore 4
      // 04d: fload 8
      // 04f: fstore 5
      // 051: aload 11
      // 053: invokevirtual android/view/WindowInsets.getSystemGestureInsets ()Landroid/graphics/Insets;
      // 056: astore 11
      // 058: fload 9
      // 05a: fstore 6
      // 05c: fload 8
      // 05e: fstore 5
      // 060: fload 7
      // 062: fstore 4
      // 064: aload 11
      // 066: ifnull 0bb
      // 069: fload 9
      // 06b: fstore 4
      // 06d: fload 8
      // 06f: fstore 5
      // 071: fload 9
      // 073: aload 11
      // 075: getfield android/graphics/Insets.bottom I
      // 078: i2f
      // 079: fconst_2
      // 07a: fmul
      // 07b: invokestatic java/lang/Math.max (FF)F
      // 07e: fstore 6
      // 080: fload 6
      // 082: fstore 4
      // 084: fload 8
      // 086: fstore 5
      // 088: fload 8
      // 08a: aload 11
      // 08c: getfield android/graphics/Insets.left I
      // 08f: i2f
      // 090: aload 11
      // 092: getfield android/graphics/Insets.right I
      // 095: i2f
      // 096: invokestatic java/lang/Math.max (FF)F
      // 099: invokestatic java/lang/Math.max (FF)F
      // 09c: fstore 8
      // 09e: fload 6
      // 0a0: fstore 4
      // 0a2: fload 8
      // 0a4: fstore 5
      // 0a6: fload 7
      // 0a8: aload 11
      // 0aa: getfield android/graphics/Insets.top I
      // 0ad: i2f
      // 0ae: invokestatic java/lang/Math.max (FF)F
      // 0b1: fstore 9
      // 0b3: fload 9
      // 0b5: fstore 4
      // 0b7: fload 8
      // 0b9: fstore 5
      // 0bb: fload 4
      // 0bd: fstore 7
      // 0bf: goto 0c8
      // 0c2: astore 11
      // 0c4: fload 4
      // 0c6: fstore 6
      // 0c8: bipush 2
      // 0c9: newarray 10
      // 0cb: astore 11
      // 0cd: aload 1
      // 0ce: aload 11
      // 0d0: invokevirtual android/view/View.getLocationOnScreen ([I)V
      // 0d3: bipush 0
      // 0d4: istore 10
      // 0d6: aload 11
      // 0d8: bipush 0
      // 0d9: iaload
      // 0da: i2f
      // 0db: fload 2
      // 0dc: fadd
      // 0dd: fstore 2
      // 0de: aload 11
      // 0e0: bipush 1
      // 0e1: iaload
      // 0e2: i2f
      // 0e3: fload 3
      // 0e4: fadd
      // 0e5: fstore 3
      // 0e6: aload 1
      // 0e7: invokevirtual android/view/View.getResources ()Landroid/content/res/Resources;
      // 0ea: invokevirtual android/content/res/Resources.getDisplayMetrics ()Landroid/util/DisplayMetrics;
      // 0ed: astore 1
      // 0ee: fload 3
      // 0ef: aload 1
      // 0f0: getfield android/util/DisplayMetrics.heightPixels I
      // 0f3: i2f
      // 0f4: fload 6
      // 0f6: fsub
      // 0f7: fcmpl
      // 0f8: ifgt 116
      // 0fb: fload 3
      // 0fc: fload 7
      // 0fe: fcmpg
      // 0ff: iflt 116
      // 102: fload 2
      // 103: fload 5
      // 105: fcmpg
      // 106: iflt 116
      // 109: fload 2
      // 10a: aload 1
      // 10b: getfield android/util/DisplayMetrics.widthPixels I
      // 10e: i2f
      // 10f: fload 5
      // 111: fsub
      // 112: fcmpl
      // 113: ifle 119
      // 116: bipush 1
      // 117: istore 10
      // 119: iload 10
      // 11b: ireturn
   }
}
