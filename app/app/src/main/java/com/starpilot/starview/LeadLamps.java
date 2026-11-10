package com.starpilot.starview;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.ArrayDeque;
import kotlin.collections.CollectionsKt;

public final class LeadLamps {
   private volatile boolean enabled;
   private long id;
   private final ArrayDeque<float[]> l = new ArrayDeque<>();
   private final ArrayDeque<float[]> r = new ArrayDeque<>();
   private long sideMs;
   private int sideNow;

   private final Pair<Integer, Float> blinks(ArrayDeque<float[]> var1) {
      int var6 = var1.size();
      Float var12 = 0.0F;
      Integer var11 = 0;
      if (var6 < 12) {
         return TuplesKt.to(var11, var12);
      } else {
         float var2 = ((float[])var1.last())[0];
         Iterable var13 = var1;
         var var15 = new ArrayList();
         Iterator var14 = var13.iterator();

         while (true) {
            boolean var10 = var14.hasNext();
            boolean var22 = true;
            if (!var10) {
               List var16 = var15;
               if (var16.size() < 10) {
                  return TuplesKt.to(var11, var12);
               } else {
                  var13 = var16;
                  var var28 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var13, 10));
                  Iterator var33 = var13.iterator();

                  while (var33.hasNext()) {
                     var28.add(((float[])var33.next())[1]);
                  }

                  List var29 = var28;
                  List var30 = CollectionsKt.sorted(var29);
                  float var3 = ((Number)var30.get(var30.size() / 10)).floatValue();
                  var2 = ((Number)var30.get(var30.size() * 9 / 10)).floatValue();
                  float var4 = var2 - var3;
                  if (var4 < 14.0F) {
                     return TuplesKt.to(var11, var4);
                  } else {
                     float var5 = (var3 + var2) / 2.0F;
                     int var7 = 0;
                     var2 = -1.0E9F;
                     if (((float[])var16.get(0))[1] > var5) {
                        var22 = true;
                     } else {
                        var22 = false;
                     }

                     Iterator var17 = var16.iterator();
                     boolean var8 = var22;

                     while (var17.hasNext()) {
                        float[] var27 = (float[])var17.next();
                        if (var27[1] > var5) {
                           var22 = true;
                        } else {
                           var22 = false;
                        }

                        int var9 = var7;
                        var3 = var2;
                        if (var22) {
                           var9 = var7;
                           var3 = var2;
                           if (!var8) {
                              label65: {
                                 var3 = var27[0] - var2;
                                 if (!(var2 < 0.0F)) {
                                    if (350.0F <= var3 && var3 <= 1100.0F) {
                                       var8 = true;
                                    } else {
                                       var8 = false;
                                    }

                                    if (!var8) {
                                       var7 = 1;
                                       break label65;
                                    }
                                 }

                                 var7++;
                              }

                              var3 = var27[0];
                              var9 = var7;
                           }
                        }

                        var7 = var9;
                        var2 = var3;
                        var8 = var22;
                     }

                     return TuplesKt.to(var7, var4);
                  }
               }
            }

            Object var31 = var14.next();
            if (!(var2 - ((float[])var31)[0] <= 2500.0F)) {
               var22 = false;
            }

            if (var22) {
               var15.add(var31);
            }
         }
      }
   }

   public final boolean anyBlink(long param1) {
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
      // 03: aload 0
      // 04: getfield com/starpilot/starview/LeadLamps.l Lkotlin/collections/ArrayDeque;
      // 07: invokespecial com/starpilot/starview/LeadLamps.blinks (Lkotlin/collections/ArrayDeque;)Lkotlin/Pair;
      // 0a: astore 6
      // 0c: aload 0
      // 0d: aload 0
      // 0e: getfield com/starpilot/starview/LeadLamps.r Lkotlin/collections/ArrayDeque;
      // 11: invokespecial com/starpilot/starview/LeadLamps.blinks (Lkotlin/collections/ArrayDeque;)Lkotlin/Pair;
      // 14: astore 5
      // 16: aload 6
      // 18: invokevirtual kotlin/Pair.getFirst ()Ljava/lang/Object;
      // 1b: checkcast java/lang/Number
      // 1e: invokevirtual java/lang/Number.intValue ()I
      // 21: bipush 2
      // 22: if_icmpge 3f
      // 25: aload 5
      // 27: invokevirtual kotlin/Pair.getFirst ()Ljava/lang/Object;
      // 2a: checkcast java/lang/Number
      // 2d: invokevirtual java/lang/Number.intValue ()I
      // 30: istore 3
      // 31: iload 3
      // 32: bipush 2
      // 33: if_icmplt 39
      // 36: goto 3f
      // 39: bipush 0
      // 3a: istore 4
      // 3c: goto 42
      // 3f: bipush 1
      // 40: istore 4
      // 42: aload 0
      // 43: monitorexit
      // 44: iload 4
      // 46: ireturn
      // 47: astore 5
      // 49: aload 0
      // 4a: monitorexit
      // 4b: aload 5
      // 4d: athrow
   }

   public final void feed(long param1, long param3, float param5, float param6) {
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
      // 001: monitorenter
      // 002: lload 1
      // 003: aload 0
      // 004: getfield com/starpilot/starview/LeadLamps.id J
      // 007: lcmp
      // 008: ifeq 01e
      // 00b: aload 0
      // 00c: lload 1
      // 00d: putfield com/starpilot/starview/LeadLamps.id J
      // 010: aload 0
      // 011: getfield com/starpilot/starview/LeadLamps.l Lkotlin/collections/ArrayDeque;
      // 014: invokevirtual kotlin/collections/ArrayDeque.clear ()V
      // 017: aload 0
      // 018: getfield com/starpilot/starview/LeadLamps.r Lkotlin/collections/ArrayDeque;
      // 01b: invokevirtual kotlin/collections/ArrayDeque.clear ()V
      // 01e: aload 0
      // 01f: getfield com/starpilot/starview/LeadLamps.l Lkotlin/collections/ArrayDeque;
      // 022: astore 10
      // 024: lload 3
      // 025: ldc2_w 10000000
      // 028: lrem
      // 029: l2f
      // 02a: fstore 7
      // 02c: bipush 0
      // 02d: istore 9
      // 02f: aload 10
      // 031: bipush 2
      // 032: newarray 6
      // 034: dup
      // 035: bipush 0
      // 036: fload 7
      // 038: fastore
      // 039: dup
      // 03a: bipush 1
      // 03b: fload 5
      // 03d: fastore
      // 03e: invokevirtual kotlin/collections/ArrayDeque.addLast (Ljava/lang/Object;)V
      // 041: aload 0
      // 042: getfield com/starpilot/starview/LeadLamps.r Lkotlin/collections/ArrayDeque;
      // 045: bipush 2
      // 046: newarray 6
      // 048: dup
      // 049: bipush 0
      // 04a: lload 3
      // 04b: ldc2_w 10000000
      // 04e: lrem
      // 04f: l2f
      // 050: fastore
      // 051: dup
      // 052: bipush 1
      // 053: fload 6
      // 055: fastore
      // 056: invokevirtual kotlin/collections/ArrayDeque.addLast (Ljava/lang/Object;)V
      // 059: aload 0
      // 05a: getfield com/starpilot/starview/LeadLamps.l Lkotlin/collections/ArrayDeque;
      // 05d: invokevirtual kotlin/collections/ArrayDeque.size ()I
      // 060: bipush 60
      // 062: if_icmple 078
      // 065: aload 0
      // 066: getfield com/starpilot/starview/LeadLamps.l Lkotlin/collections/ArrayDeque;
      // 069: invokevirtual kotlin/collections/ArrayDeque.removeFirst ()Ljava/lang/Object;
      // 06c: pop
      // 06d: aload 0
      // 06e: getfield com/starpilot/starview/LeadLamps.r Lkotlin/collections/ArrayDeque;
      // 071: invokevirtual kotlin/collections/ArrayDeque.removeFirst ()Ljava/lang/Object;
      // 074: pop
      // 075: goto 059
      // 078: aload 0
      // 079: aload 0
      // 07a: getfield com/starpilot/starview/LeadLamps.l Lkotlin/collections/ArrayDeque;
      // 07d: invokespecial com/starpilot/starview/LeadLamps.blinks (Lkotlin/collections/ArrayDeque;)Lkotlin/Pair;
      // 080: astore 11
      // 082: aload 0
      // 083: aload 0
      // 084: getfield com/starpilot/starview/LeadLamps.r Lkotlin/collections/ArrayDeque;
      // 087: invokespecial com/starpilot/starview/LeadLamps.blinks (Lkotlin/collections/ArrayDeque;)Lkotlin/Pair;
      // 08a: astore 10
      // 08c: aload 11
      // 08e: invokevirtual kotlin/Pair.getFirst ()Ljava/lang/Object;
      // 091: checkcast java/lang/Number
      // 094: invokevirtual java/lang/Number.intValue ()I
      // 097: bipush 3
      // 098: if_icmplt 0bd
      // 09b: aload 11
      // 09d: invokevirtual kotlin/Pair.getSecond ()Ljava/lang/Object;
      // 0a0: checkcast java/lang/Number
      // 0a3: invokevirtual java/lang/Number.floatValue ()F
      // 0a6: aload 10
      // 0a8: invokevirtual kotlin/Pair.getSecond ()Ljava/lang/Object;
      // 0ab: checkcast java/lang/Number
      // 0ae: invokevirtual java/lang/Number.floatValue ()F
      // 0b1: fconst_2
      // 0b2: fmul
      // 0b3: fcmpl
      // 0b4: ifle 0bd
      // 0b7: bipush -1
      // 0b8: istore 8
      // 0ba: goto 0f3
      // 0bd: iload 9
      // 0bf: istore 8
      // 0c1: aload 10
      // 0c3: invokevirtual kotlin/Pair.getFirst ()Ljava/lang/Object;
      // 0c6: checkcast java/lang/Number
      // 0c9: invokevirtual java/lang/Number.intValue ()I
      // 0cc: bipush 3
      // 0cd: if_icmplt 0f3
      // 0d0: iload 9
      // 0d2: istore 8
      // 0d4: aload 10
      // 0d6: invokevirtual kotlin/Pair.getSecond ()Ljava/lang/Object;
      // 0d9: checkcast java/lang/Number
      // 0dc: invokevirtual java/lang/Number.floatValue ()F
      // 0df: aload 11
      // 0e1: invokevirtual kotlin/Pair.getSecond ()Ljava/lang/Object;
      // 0e4: checkcast java/lang/Number
      // 0e7: invokevirtual java/lang/Number.floatValue ()F
      // 0ea: fconst_2
      // 0eb: fmul
      // 0ec: fcmpl
      // 0ed: ifle 0f3
      // 0f0: bipush 1
      // 0f1: istore 8
      // 0f3: iload 8
      // 0f5: ifeq 103
      // 0f8: aload 0
      // 0f9: iload 8
      // 0fb: putfield com/starpilot/starview/LeadLamps.sideNow I
      // 0fe: aload 0
      // 0ff: lload 3
      // 100: putfield com/starpilot/starview/LeadLamps.sideMs J
      // 103: aload 0
      // 104: monitorexit
      // 105: return
      // 106: astore 10
      // 108: aload 0
      // 109: monitorexit
      // 10a: aload 10
      // 10c: athrow
   }

   public final boolean getEnabled() {
      return this.enabled;
   }

   public final void setEnabled(boolean var1) {
      this.enabled = var1;
   }

   public final int side(long param1) {
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
      // 02: lload 1
      // 03: aload 0
      // 04: getfield com/starpilot/starview/LeadLamps.sideMs J
      // 07: lsub
      // 08: ldc2_w 1500
      // 0b: lcmp
      // 0c: ifge 17
      // 0f: aload 0
      // 10: getfield com/starpilot/starview/LeadLamps.sideNow I
      // 13: istore 3
      // 14: goto 19
      // 17: bipush 0
      // 18: istore 3
      // 19: aload 0
      // 1a: monitorexit
      // 1b: iload 3
      // 1c: ireturn
      // 1d: astore 4
      // 1f: aload 0
      // 20: monitorexit
      // 21: aload 4
      // 23: athrow
   }
}
