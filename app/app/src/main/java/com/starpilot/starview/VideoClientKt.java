package com.starpilot.starview;

import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref.IntRef;

public final class VideoClientKt {
   private static final Lazy hevcDecoder$delegate = LazyKt.lazy(new VideoClientKt$$ExternalSyntheticLambda0());

   // $VF: synthetic method
   public static boolean $r8$lambda$DG4jYSmLMp61z59xh2CltJsPdWs() {
      return hevcDecoder_delegate$lambda$2();
   }

   public static final boolean getHevcDecoder() {
      return (Boolean)hevcDecoder$delegate.getValue();
   }

   private static final boolean hevcDecoder_delegate$lambda$2() {
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
      // 00: new android/media/MediaCodecList
      // 03: astore 5
      // 05: aload 5
      // 07: bipush 0
      // 08: invokespecial android/media/MediaCodecList.<init> (I)V
      // 0b: aload 5
      // 0d: invokevirtual android/media/MediaCodecList.getCodecInfos ()[Landroid/media/MediaCodecInfo;
      // 10: astore 5
      // 12: aload 5
      // 14: ldc "getCodecInfos(...)"
      // 16: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 19: aload 5
      // 1b: checkcast [Ljava/lang/Object;
      // 1e: astore 5
      // 20: aload 5
      // 22: arraylength
      // 23: istore 2
      // 24: bipush 0
      // 25: istore 0
      // 26: iload 0
      // 27: iload 2
      // 28: if_icmpge 98
      // 2b: aload 5
      // 2d: iload 0
      // 2e: aaload
      // 2f: checkcast android/media/MediaCodecInfo
      // 32: astore 6
      // 34: aload 6
      // 36: invokevirtual android/media/MediaCodecInfo.isEncoder ()Z
      // 39: ifne 86
      // 3c: aload 6
      // 3e: invokevirtual android/media/MediaCodecInfo.getSupportedTypes ()[Ljava/lang/String;
      // 41: astore 6
      // 43: aload 6
      // 45: ldc "getSupportedTypes(...)"
      // 47: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 4a: aload 6
      // 4c: checkcast [Ljava/lang/Object;
      // 4f: astore 6
      // 51: aload 6
      // 53: arraylength
      // 54: istore 3
      // 55: bipush 0
      // 56: istore 1
      // 57: iload 1
      // 58: iload 3
      // 59: if_icmpge 7b
      // 5c: aload 6
      // 5e: iload 1
      // 5f: aaload
      // 60: checkcast java/lang/String
      // 63: ldc "video/hevc"
      // 65: bipush 1
      // 66: invokestatic kotlin/text/StringsKt.equals (Ljava/lang/String;Ljava/lang/String;Z)Z
      // 69: istore 4
      // 6b: iload 4
      // 6d: ifeq 75
      // 70: bipush 1
      // 71: istore 1
      // 72: goto 7d
      // 75: iinc 1 1
      // 78: goto 57
      // 7b: bipush 0
      // 7c: istore 1
      // 7d: iload 1
      // 7e: ifeq 86
      // 81: bipush 1
      // 82: istore 1
      // 83: goto 88
      // 86: bipush 0
      // 87: istore 1
      // 88: iload 1
      // 89: ifeq 92
      // 8c: bipush 1
      // 8d: istore 4
      // 8f: goto 9b
      // 92: iinc 0 1
      // 95: goto 26
      // 98: bipush 0
      // 99: istore 4
      // 9b: goto a3
      // 9e: astore 5
      // a0: bipush 0
      // a1: istore 4
      // a3: iload 4
      // a5: ireturn
   }

   public static final byte[] nalOf(byte[] var0, int var1, boolean var2) {
      Intrinsics.checkNotNullParameter(var0, "d");
      int var3 = 0;
      int var5 = var0.length;
      IntRef var6 = new IntRef();
      var6.element = -1;
      IntRef var7 = new IntRef();

      while (var3 + 3 < var5) {
         if (var0[var3] == 0 && var0[var3 + 1] == 0 && (var0[var3 + 2] == 1 || var0[var3 + 2] == 0 && var3 + 3 < var5 && var0[var3 + 3] == 1)) {
            byte var4;
            if (var0[var3 + 2] == 1) {
               var4 = 3;
            } else {
               var4 = 4;
            }

            byte[] var8 = nalOf$flush(var6, var7, var5, var0, var2, var1, var3);
            if (var8 != null) {
               return var8;
            }

            var6.element = var3;
            var7.element = var4;
            var3 += var4;
         } else {
            var3++;
         }
      }

      return nalOf$flush(var6, var7, var5, var0, var2, var1, var5);
   }

   // $VF: synthetic method
   public static byte[] nalOf$default(byte[] var0, int var1, boolean var2, int var3, Object var4) {
      if ((var3 & 4) != 0) {
         var2 = false;
      }

      return nalOf(var0, var1, var2);
   }

   private static final byte[] nalOf$flush(IntRef var0, IntRef var1, int var2, byte[] var3, boolean var4, int var5, int var6) {
      int var7 = var0.element;
      Object var8 = null;
      if (var7 >= 0 && var0.element + var1.element < var2) {
         var2 = var3[var0.element + var1.element] & 255;
         if (var4) {
            var2 = var2 >> 1 & 63;
         } else {
            var2 &= 31;
         }

         byte[] var9 = (byte[])var8;
         if (var2 == var5) {
            var9 = ArraysKt.copyOfRange(var3, var0.element, var6);
         }

         return var9;
      } else {
         return null;
      }
   }
}
