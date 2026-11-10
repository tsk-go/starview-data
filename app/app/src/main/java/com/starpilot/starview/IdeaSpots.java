package com.starpilot.starview;

import android.content.Context;
import android.os.Environment;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import kotlin.Pair;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.json.JSONObject;

public final class IdeaSpots {
   public static final IdeaSpots INSTANCE = new IdeaSpots();
   private static volatile boolean dirty;
   private static File file;
   private static long savedMs;
   private static final ArrayList<IdeaSpots.Spot> spots = new ArrayList<>();

   // $VF: synthetic method
   public static boolean $r8$lambda$CJIxEA94sTp6mb8dVUlUW17khFk(String var0, File var1) {
      return importFleet$lambda$11(var0, var1);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$HSrcimfyLtZQqnygg20Mg4FROsA(String var0, IdeaSpots.Spot var1) {
      return importFleet$lambda$13$lambda$12(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$KqkFabTjcbpjTjpRKDt_jCWmgLM/* $VF was: $r8$lambda$KqkFabTjcbpjTjpRKDt_jCWmgLM*/(File var0, String var1) {
      saveSoon$lambda$7(var0, var1);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$LcRGCj75bUJaIHv0G5ZPgLzqXmA(String var0, long var1, long var3, IdeaSpots.Spot var5) {
      return expire$lambda$14(var0, var1, var3, var5);
   }

   private IdeaSpots() {
   }

   // $VF: synthetic method
   public static List around$default(
      IdeaSpots var0,
      double var1,
      double var3,
      double var5,
      Set var7,
      double var8,
      double var10,
      double var12,
      int var14,
      boolean var15,
      int var16,
      Object var17
   ) {
      if ((var16 & 32) != 0) {
         var10 = 0.0;
      }

      if ((var16 & 64) != 0) {
         var12 = 8.0;
      }

      if ((var16 & 128) != 0) {
         var14 = 1;
      }

      if ((var16 & 256) != 0) {
         var15 = true;
      }

      return var0.around(var1, var3, var5, var7, var8, var10, var12, var14, var15);
   }

   // $VF: synthetic method
   public static void expire$default(IdeaSpots var0, String var1, long var2, long var4, int var6, Object var7) {
      if ((var6 & 4) != 0) {
         var4 = System.currentTimeMillis();
      }

      var0.expire(var1, var2, var4);
   }

   private static final boolean expire$lambda$14(String var0, long var1, long var3, IdeaSpots.Spot var5) {
      Intrinsics.checkNotNullParameter(var5, "it");
      boolean var6;
      if (Intrinsics.areEqual(var5.getKind(), var0) && var1 - var5.getLastMs() > var3) {
         var6 = true;
      } else {
         var6 = false;
      }

      return var6;
   }

   // $VF: synthetic method
   public static boolean headingOk$default(IdeaSpots var0, double var1, double var3, double var5, int var7, Object var8) {
      if ((var7 & 4) != 0) {
         var5 = 50.0;
      }

      return var0.headingOk(var1, var3, var5);
   }

   private static final boolean importFleet$lambda$11(String var0, File var1) {
      String var4 = var1.getName();
      Intrinsics.checkNotNullExpressionValue(var4, "getName(...)");
      boolean var3 = false;
      boolean var2 = var3;
      if (StringsKt.endsWith$default(var4, ".json", false, 2, null)) {
         var2 = var3;
         if (!Intrinsics.areEqual(var1.getName(), var0 + ".json")) {
            var2 = true;
         }
      }

      return var2;
   }

   private static final boolean importFleet$lambda$13$lambda$12(String var0, IdeaSpots.Spot var1) {
      Intrinsics.checkNotNullParameter(var1, "it");
      return Intrinsics.areEqual(var1.getSrc(), var0);
   }

   // $VF: synthetic method
   public static IdeaSpots.Spot record$default(
      IdeaSpots var0,
      String var1,
      double var2,
      double var4,
      double var6,
      double var8,
      String var10,
      long var11,
      double var13,
      String var15,
      int var16,
      Object var17
   ) {
      if ((var16 & 16) != 0) {
         var8 = Double.NaN;
      }

      if ((var16 & 32) != 0) {
         var10 = "";
      }

      if ((var16 & 64) != 0) {
         var11 = System.currentTimeMillis();
      }

      if ((var16 & 128) != 0) {
         var13 = 10.0;
      }

      if ((var16 & 256) != 0) {
         var15 = "me";
      }

      return var0.record(var1, var2, var4, var6, var8, var10, var11, var13, var15);
   }

   // $VF: synthetic method
   public static void saveSoon$default(IdeaSpots var0, long var1, boolean var3, int var4, Object var5) {
      if ((var4 & 1) != 0) {
         var1 = System.currentTimeMillis();
      }

      if ((var4 & 2) != 0) {
         var3 = false;
      }

      var0.saveSoon(var1, var3);
   }

   private static final void saveSoon$lambda$7(File var0, String var1) {
      try {
         String var4 = var0.getPath();
         StringBuilder var3 = new StringBuilder();
         File var2 = new File(var3.append(var4).append(".tmp").toString());
         FilesKt.writeText$default(var2, var1, null, 2, null);
         var2.renameTo(var0);
      } catch (Exception var5) {
      }
   }

   public final List<IdeaSpots.Spot> all() {
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
      // 02: new java/util/ArrayList
      // 05: astore 1
      // 06: aload 1
      // 07: getstatic com/starpilot/starview/IdeaSpots.spots Ljava/util/ArrayList;
      // 0a: checkcast java/util/Collection
      // 0d: invokespecial java/util/ArrayList.<init> (Ljava/util/Collection;)V
      // 10: aload 1
      // 11: checkcast java/util/List
      // 14: astore 1
      // 15: aload 0
      // 16: monitorexit
      // 17: aload 1
      // 18: areturn
      // 19: astore 1
      // 1a: aload 0
      // 1b: monitorexit
      // 1c: aload 1
      // 1d: athrow
   }

   public final List<IdeaSpots.Seen> around(
      double param1, double param3, double param5, Set<String> param7, double param8, double param10, double param12, int param14, boolean param15
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
      // 000: aload 0
      // 001: monitorenter
      // 002: aload 7
      // 004: ldc "kinds"
      // 006: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
      // 009: dload 5
      // 00b: invokestatic java/lang/Math.cos (D)D
      // 00e: dstore 16
      // 010: dload 5
      // 012: invokestatic java/lang/Math.sin (D)D
      // 015: dstore 18
      // 017: ldc2_w 90.0
      // 01a: dload 5
      // 01c: invokestatic java/lang/Math.toDegrees (D)D
      // 01f: dsub
      // 020: ldc2_w 360.0
      // 023: dadd
      // 024: ldc2_w 360.0
      // 027: drem
      // 028: dstore 5
      // 02a: new java/util/ArrayList
      // 02d: astore 27
      // 02f: aload 27
      // 031: invokespecial java/util/ArrayList.<init> ()V
      // 034: getstatic com/starpilot/starview/IdeaSpots.spots Ljava/util/ArrayList;
      // 037: invokevirtual java/util/ArrayList.iterator ()Ljava/util/Iterator;
      // 03a: astore 26
      // 03c: aload 26
      // 03e: ldc_w "iterator(...)"
      // 041: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 044: aload 26
      // 046: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 04b: ifeq 127
      // 04e: aload 26
      // 050: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 055: astore 28
      // 057: aload 28
      // 059: ldc_w "next(...)"
      // 05c: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 05f: aload 28
      // 061: checkcast com/starpilot/starview/IdeaSpots$Spot
      // 064: astore 28
      // 066: aload 7
      // 068: aload 28
      // 06a: invokevirtual com/starpilot/starview/IdeaSpots$Spot.getKind ()Ljava/lang/String;
      // 06d: invokeinterface java/util/Set.contains (Ljava/lang/Object;)Z 2
      // 072: ifeq 124
      // 075: aload 28
      // 077: invokevirtual com/starpilot/starview/IdeaSpots$Spot.getCount ()I
      // 07a: iload 14
      // 07c: if_icmpge 082
      // 07f: goto 044
      // 082: iload 15
      // 084: ifeq 09e
      // 087: aload 0
      // 088: aload 28
      // 08a: invokevirtual com/starpilot/starview/IdeaSpots$Spot.getHdg ()D
      // 08d: dload 5
      // 08f: dconst_0
      // 090: bipush 4
      // 091: aconst_null
      // 092: invokestatic com/starpilot/starview/IdeaSpots.headingOk$default (Lcom/starpilot/starview/IdeaSpots;DDDILjava/lang/Object;)Z
      // 095: ifeq 09b
      // 098: goto 09e
      // 09b: goto 044
      // 09e: aload 0
      // 09f: dload 1
      // 0a0: dload 3
      // 0a1: aload 28
      // 0a3: invokevirtual com/starpilot/starview/IdeaSpots$Spot.getLat ()D
      // 0a6: aload 28
      // 0a8: invokevirtual com/starpilot/starview/IdeaSpots$Spot.getLon ()D
      // 0ab: invokevirtual com/starpilot/starview/IdeaSpots.local (DDDD)Lkotlin/Pair;
      // 0ae: astore 29
      // 0b0: aload 29
      // 0b2: invokevirtual kotlin/Pair.component1 ()Ljava/lang/Object;
      // 0b5: checkcast java/lang/Number
      // 0b8: invokevirtual java/lang/Number.doubleValue ()D
      // 0bb: dstore 24
      // 0bd: aload 29
      // 0bf: invokevirtual kotlin/Pair.component2 ()Ljava/lang/Object;
      // 0c2: checkcast java/lang/Number
      // 0c5: invokevirtual java/lang/Number.doubleValue ()D
      // 0c8: dstore 22
      // 0ca: dload 24
      // 0cc: dload 16
      // 0ce: dmul
      // 0cf: dload 22
      // 0d1: dload 18
      // 0d3: dmul
      // 0d4: dadd
      // 0d5: dstore 20
      // 0d7: dload 20
      // 0d9: dload 10
      // 0db: dneg
      // 0dc: dcmpg
      // 0dd: iflt 121
      // 0e0: dload 20
      // 0e2: dload 8
      // 0e4: dcmpl
      // 0e5: ifle 0eb
      // 0e8: goto 121
      // 0eb: dload 24
      // 0ed: dload 18
      // 0ef: dmul
      // 0f0: dload 22
      // 0f2: dload 16
      // 0f4: dmul
      // 0f5: dsub
      // 0f6: dstore 22
      // 0f8: dload 22
      // 0fa: invokestatic java/lang/Math.abs (D)D
      // 0fd: dload 12
      // 0ff: dcmpl
      // 100: ifgt 11e
      // 103: new com/starpilot/starview/IdeaSpots$Seen
      // 106: astore 29
      // 108: aload 29
      // 10a: aload 28
      // 10c: dload 20
      // 10e: dload 22
      // 110: invokespecial com/starpilot/starview/IdeaSpots$Seen.<init> (Lcom/starpilot/starview/IdeaSpots$Spot;DD)V
      // 113: aload 27
      // 115: aload 29
      // 117: invokevirtual java/util/ArrayList.add (Ljava/lang/Object;)Z
      // 11a: pop
      // 11b: goto 044
      // 11e: goto 044
      // 121: goto 044
      // 124: goto 044
      // 127: aload 27
      // 129: checkcast java/util/List
      // 12c: astore 7
      // 12e: aload 7
      // 130: invokeinterface java/util/List.size ()I 1
      // 135: bipush 1
      // 136: if_icmple 14d
      // 139: new com/starpilot/starview/IdeaSpots$around$$inlined$sortBy$1
      // 13c: astore 26
      // 13e: aload 26
      // 140: invokespecial com/starpilot/starview/IdeaSpots$around$$inlined$sortBy$1.<init> ()V
      // 143: aload 7
      // 145: aload 26
      // 147: checkcast java/util/Comparator
      // 14a: invokestatic kotlin/collections/CollectionsKt.sortWith (Ljava/util/List;Ljava/util/Comparator;)V
      // 14d: aload 27
      // 14f: checkcast java/util/List
      // 152: astore 7
      // 154: aload 0
      // 155: monitorexit
      // 156: aload 7
      // 158: areturn
      // 159: astore 7
      // 15b: aload 0
      // 15c: monitorexit
      // 15d: aload 7
      // 15f: athrow
   }

   public final void clearForTest$app_debug() {
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
      // 0: aload 0
      // 1: monitorenter
      // 2: getstatic com/starpilot/starview/IdeaSpots.spots Ljava/util/ArrayList;
      // 5: invokevirtual java/util/ArrayList.clear ()V
      // 8: aload 0
      // 9: monitorexit
      // a: return
      // b: astore 1
      // c: aload 0
      // d: monitorexit
      // e: aload 1
      // f: athrow
   }

   public final int count(String param1) {
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
      // 02: aload 1
      // 03: ldc_w "kind"
      // 06: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
      // 09: getstatic com/starpilot/starview/IdeaSpots.spots Ljava/util/ArrayList;
      // 0c: checkcast java/lang/Iterable
      // 0f: astore 4
      // 11: aload 4
      // 13: instanceof java/util/Collection
      // 16: ifeq 2b
      // 19: aload 4
      // 1b: checkcast java/util/Collection
      // 1e: invokeinterface java/util/Collection.isEmpty ()Z 1
      // 23: ifeq 2b
      // 26: bipush 0
      // 27: istore 2
      // 28: goto 66
      // 2b: bipush 0
      // 2c: istore 2
      // 2d: aload 4
      // 2f: invokeinterface java/lang/Iterable.iterator ()Ljava/util/Iterator; 1
      // 34: astore 4
      // 36: aload 4
      // 38: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 3d: ifeq 66
      // 40: aload 4
      // 42: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 47: checkcast com/starpilot/starview/IdeaSpots$Spot
      // 4a: invokevirtual com/starpilot/starview/IdeaSpots$Spot.getKind ()Ljava/lang/String;
      // 4d: aload 1
      // 4e: invokestatic kotlin/jvm/internal/Intrinsics.areEqual (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 51: ifeq 36
      // 54: iload 2
      // 55: bipush 1
      // 56: iadd
      // 57: istore 3
      // 58: iload 3
      // 59: istore 2
      // 5a: iload 3
      // 5b: ifge 36
      // 5e: invokestatic kotlin/collections/CollectionsKt.throwCountOverflow ()V
      // 61: iload 3
      // 62: istore 2
      // 63: goto 36
      // 66: aload 0
      // 67: monitorexit
      // 68: iload 2
      // 69: ireturn
      // 6a: astore 1
      // 6b: aload 0
      // 6c: monitorexit
      // 6d: aload 1
      // 6e: athrow
   }

   public final void expire(String param1, long param2, long param4) {
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
      // 02: aload 1
      // 03: ldc_w "kind"
      // 06: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
      // 09: getstatic com/starpilot/starview/IdeaSpots.spots Ljava/util/ArrayList;
      // 0c: checkcast java/util/List
      // 0f: astore 6
      // 11: new com/starpilot/starview/IdeaSpots$$ExternalSyntheticLambda3
      // 14: astore 7
      // 16: aload 7
      // 18: aload 1
      // 19: lload 4
      // 1b: lload 2
      // 1c: invokespecial com/starpilot/starview/IdeaSpots$$ExternalSyntheticLambda3.<init> (Ljava/lang/String;JJ)V
      // 1f: aload 6
      // 21: aload 7
      // 23: invokestatic kotlin/collections/CollectionsKt.removeAll (Ljava/util/List;Lkotlin/jvm/functions/Function1;)Z
      // 26: ifeq 2d
      // 29: bipush 1
      // 2a: putstatic com/starpilot/starview/IdeaSpots.dirty Z
      // 2d: aload 0
      // 2e: monitorexit
      // 2f: return
      // 30: astore 1
      // 31: aload 0
      // 32: monitorexit
      // 33: aload 1
      // 34: athrow
   }

   public final int exportFleet(String param1) {
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
      // 000: aload 1
      // 001: ldc_w "name"
      // 004: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
      // 007: aload 0
      // 008: monitorenter
      // 009: getstatic com/starpilot/starview/IdeaSpots.spots Ljava/util/ArrayList;
      // 00c: checkcast java/lang/Iterable
      // 00f: astore 4
      // 011: new java/util/ArrayList
      // 014: astore 3
      // 015: aload 3
      // 016: invokespecial java/util/ArrayList.<init> ()V
      // 019: aload 3
      // 01a: checkcast java/util/Collection
      // 01d: astore 3
      // 01e: aload 4
      // 020: invokeinterface java/lang/Iterable.iterator ()Ljava/util/Iterator; 1
      // 025: astore 5
      // 027: aload 5
      // 029: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 02e: ifeq 073
      // 031: aload 5
      // 033: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 038: astore 4
      // 03a: aload 4
      // 03c: checkcast com/starpilot/starview/IdeaSpots$Spot
      // 03f: astore 6
      // 041: aload 6
      // 043: invokevirtual com/starpilot/starview/IdeaSpots$Spot.getSrc ()Ljava/lang/String;
      // 046: ldc "me"
      // 048: invokestatic kotlin/jvm/internal/Intrinsics.areEqual (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 04b: ifeq 061
      // 04e: aload 6
      // 050: invokevirtual com/starpilot/starview/IdeaSpots$Spot.getKind ()Ljava/lang/String;
      // 053: ldc_w "map_bump"
      // 056: invokestatic kotlin/jvm/internal/Intrinsics.areEqual (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 059: ifne 061
      // 05c: bipush 1
      // 05d: istore 2
      // 05e: goto 063
      // 061: bipush 0
      // 062: istore 2
      // 063: iload 2
      // 064: ifeq 027
      // 067: aload 3
      // 068: aload 4
      // 06a: invokeinterface java/util/Collection.add (Ljava/lang/Object;)Z 2
      // 06f: pop
      // 070: goto 027
      // 073: aload 3
      // 074: checkcast java/util/List
      // 077: astore 4
      // 079: aload 0
      // 07a: monitorexit
      // 07b: aload 0
      // 07c: invokevirtual com/starpilot/starview/IdeaSpots.fleetDir ()Ljava/io/File;
      // 07f: astore 5
      // 081: aload 5
      // 083: invokevirtual java/io/File.mkdirs ()Z
      // 086: pop
      // 087: new java/io/File
      // 08a: astore 3
      // 08b: new java/lang/StringBuilder
      // 08e: astore 6
      // 090: aload 6
      // 092: invokespecial java/lang/StringBuilder.<init> ()V
      // 095: aload 3
      // 096: aload 5
      // 098: aload 6
      // 09a: aload 1
      // 09b: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 09e: ldc ".json"
      // 0a0: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 0a3: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 0a6: invokespecial java/io/File.<init> (Ljava/io/File;Ljava/lang/String;)V
      // 0a9: new org/json/JSONObject
      // 0ac: astore 5
      // 0ae: aload 5
      // 0b0: invokespecial org/json/JSONObject.<init> ()V
      // 0b3: aload 5
      // 0b5: ldc_w "from"
      // 0b8: aload 1
      // 0b9: invokevirtual org/json/JSONObject.put (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
      // 0bc: ldc_w "t"
      // 0bf: invokestatic java/lang/System.currentTimeMillis ()J
      // 0c2: invokevirtual org/json/JSONObject.put (Ljava/lang/String;J)Lorg/json/JSONObject;
      // 0c5: astore 5
      // 0c7: new org/json/JSONArray
      // 0ca: astore 1
      // 0cb: aload 1
      // 0cc: invokespecial org/json/JSONArray.<init> ()V
      // 0cf: aload 4
      // 0d1: invokeinterface java/util/List.iterator ()Ljava/util/Iterator; 1
      // 0d6: astore 6
      // 0d8: aload 6
      // 0da: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 0df: ifeq 0f7
      // 0e2: aload 1
      // 0e3: aload 6
      // 0e5: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 0ea: checkcast com/starpilot/starview/IdeaSpots$Spot
      // 0ed: invokevirtual com/starpilot/starview/IdeaSpots$Spot.json ()Lorg/json/JSONObject;
      // 0f0: invokevirtual org/json/JSONArray.put (Ljava/lang/Object;)Lorg/json/JSONArray;
      // 0f3: pop
      // 0f4: goto 0d8
      // 0f7: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 0fa: astore 6
      // 0fc: aload 5
      // 0fe: ldc_w "spots"
      // 101: aload 1
      // 102: invokevirtual org/json/JSONObject.put (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
      // 105: invokevirtual org/json/JSONObject.toString ()Ljava/lang/String;
      // 108: astore 1
      // 109: aload 1
      // 10a: ldc_w "toString(...)"
      // 10d: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 110: aload 3
      // 111: aload 1
      // 112: aconst_null
      // 113: bipush 2
      // 114: aconst_null
      // 115: invokestatic kotlin/io/FilesKt.writeText$default (Ljava/io/File;Ljava/lang/String;Ljava/nio/charset/Charset;ILjava/lang/Object;)V
      // 118: aload 4
      // 11a: invokeinterface java/util/List.size ()I 1
      // 11f: istore 2
      // 120: goto 126
      // 123: astore 1
      // 124: bipush -1
      // 125: istore 2
      // 126: iload 2
      // 127: ireturn
      // 128: astore 1
      // 129: aload 0
      // 12a: monitorexit
      // 12b: aload 1
      // 12c: athrow
   }

   public final File fleetDir() {
      return new File(Environment.getExternalStorageDirectory(), "Download/StarView/fleet");
   }

   public final boolean headingOk(double var1, double var3, double var5) {
      boolean var7;
      if (!Double.isNaN(var1) && !Double.isNaN(var3) && !(Math.abs(Math.IEEEremainder(var1 - var3, 360.0)) <= var5)) {
         var7 = false;
      } else {
         var7 = true;
      }

      return var7;
   }

   public final int importFleet(String param1) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: parsing failure!
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 000: aload 1
      // 001: ldc_w "myName"
      // 004: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
      // 007: aload 0
      // 008: invokevirtual com/starpilot/starview/IdeaSpots.fleetDir ()Ljava/io/File;
      // 00b: astore 8
      // 00d: new com/starpilot/starview/IdeaSpots$$ExternalSyntheticLambda0
      // 010: astore 9
      // 012: aload 9
      // 014: aload 1
      // 015: invokespecial com/starpilot/starview/IdeaSpots$$ExternalSyntheticLambda0.<init> (Ljava/lang/String;)V
      // 018: aload 8
      // 01a: aload 9
      // 01c: invokevirtual java/io/File.listFiles (Ljava/io/FileFilter;)[Ljava/io/File;
      // 01f: astore 8
      // 021: aload 8
      // 023: astore 1
      // 024: aload 8
      // 026: ifnonnull 037
      // 029: bipush 0
      // 02a: anewarray 164
      // 02d: astore 1
      // 02e: goto 037
      // 031: astore 1
      // 032: bipush 0
      // 033: anewarray 164
      // 036: astore 1
      // 037: aload 1
      // 038: arraylength
      // 039: istore 6
      // 03b: bipush 0
      // 03c: istore 5
      // 03e: bipush 0
      // 03f: istore 2
      // 040: iload 5
      // 042: iload 6
      // 044: if_icmpge 13b
      // 047: aload 1
      // 048: iload 5
      // 04a: aaload
      // 04b: astore 8
      // 04d: iload 2
      // 04e: istore 3
      // 04f: new org/json/JSONObject
      // 052: astore 9
      // 054: iload 2
      // 055: istore 3
      // 056: aload 8
      // 058: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNull (Ljava/lang/Object;)V
      // 05b: iload 2
      // 05c: istore 3
      // 05d: aload 9
      // 05f: aload 8
      // 061: aconst_null
      // 062: bipush 1
      // 063: aconst_null
      // 064: invokestatic kotlin/io/FilesKt.readText$default (Ljava/io/File;Ljava/nio/charset/Charset;ILjava/lang/Object;)Ljava/lang/String;
      // 067: invokespecial org/json/JSONObject.<init> (Ljava/lang/String;)V
      // 06a: iload 2
      // 06b: istore 3
      // 06c: aload 9
      // 06e: ldc_w "from"
      // 071: aload 8
      // 073: invokestatic kotlin/io/FilesKt.getNameWithoutExtension (Ljava/io/File;)Ljava/lang/String;
      // 076: invokevirtual org/json/JSONObject.optString (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
      // 079: astore 8
      // 07b: iload 2
      // 07c: istore 3
      // 07d: aload 9
      // 07f: ldc_w "spots"
      // 082: invokevirtual org/json/JSONObject.optJSONArray (Ljava/lang/String;)Lorg/json/JSONArray;
      // 085: astore 9
      // 087: aload 9
      // 089: ifnonnull 08f
      // 08c: goto 135
      // 08f: iload 2
      // 090: istore 3
      // 091: aload 0
      // 092: monitorenter
      // 093: iload 2
      // 094: istore 4
      // 096: getstatic com/starpilot/starview/IdeaSpots.spots Ljava/util/ArrayList;
      // 099: checkcast java/util/List
      // 09c: astore 11
      // 09e: iload 2
      // 09f: istore 4
      // 0a1: new com/starpilot/starview/IdeaSpots$$ExternalSyntheticLambda1
      // 0a4: astore 10
      // 0a6: iload 2
      // 0a7: istore 4
      // 0a9: aload 10
      // 0ab: aload 8
      // 0ad: invokespecial com/starpilot/starview/IdeaSpots$$ExternalSyntheticLambda1.<init> (Ljava/lang/String;)V
      // 0b0: iload 2
      // 0b1: istore 4
      // 0b3: aload 11
      // 0b5: aload 10
      // 0b7: invokestatic kotlin/collections/CollectionsKt.removeAll (Ljava/util/List;Lkotlin/jvm/functions/Function1;)Z
      // 0ba: pop
      // 0bb: bipush 0
      // 0bc: istore 3
      // 0bd: iload 2
      // 0be: istore 4
      // 0c0: aload 9
      // 0c2: invokevirtual org/json/JSONArray.length ()I
      // 0c5: istore 7
      // 0c7: iload 3
      // 0c8: iload 7
      // 0ca: if_icmpge 10e
      // 0cd: iload 2
      // 0ce: istore 4
      // 0d0: getstatic com/starpilot/starview/IdeaSpots.spots Ljava/util/ArrayList;
      // 0d3: astore 12
      // 0d5: iload 2
      // 0d6: istore 4
      // 0d8: getstatic com/starpilot/starview/IdeaSpots$Spot.Companion Lcom/starpilot/starview/IdeaSpots$Spot$Companion;
      // 0db: astore 10
      // 0dd: iload 2
      // 0de: istore 4
      // 0e0: aload 9
      // 0e2: iload 3
      // 0e3: invokevirtual org/json/JSONArray.getJSONObject (I)Lorg/json/JSONObject;
      // 0e6: astore 11
      // 0e8: iload 2
      // 0e9: istore 4
      // 0eb: aload 11
      // 0ed: ldc_w "getJSONObject(...)"
      // 0f0: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 0f3: iload 2
      // 0f4: istore 4
      // 0f6: aload 12
      // 0f8: aload 10
      // 0fa: aload 11
      // 0fc: aload 8
      // 0fe: invokevirtual com/starpilot/starview/IdeaSpots$Spot$Companion.of (Lorg/json/JSONObject;Ljava/lang/String;)Lcom/starpilot/starview/IdeaSpots$Spot;
      // 101: invokevirtual java/util/ArrayList.add (Ljava/lang/Object;)Z
      // 104: pop
      // 105: iinc 2 1
      // 108: iinc 3 1
      // 10b: goto 0c7
      // 10e: iload 2
      // 10f: istore 4
      // 111: bipush 1
      // 112: putstatic com/starpilot/starview/IdeaSpots.dirty Z
      // 115: iload 2
      // 116: istore 4
      // 118: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 11b: astore 8
      // 11d: iload 2
      // 11e: istore 3
      // 11f: aload 0
      // 120: monitorexit
      // 121: goto 135
      // 124: astore 8
      // 126: iload 4
      // 128: istore 3
      // 129: aload 0
      // 12a: monitorexit
      // 12b: iload 4
      // 12d: istore 3
      // 12e: aload 8
      // 130: athrow
      // 131: astore 8
      // 133: iload 3
      // 134: istore 2
      // 135: iinc 5 1
      // 138: goto 040
      // 13b: iload 2
      // 13c: ireturn
   }

   public final void init(Context param1) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.NullPointerException: Cannot invoke "org.jetbrains.java.decompiler.code.cfg.ExceptionRangeCFG.isCircular()" because "range" is null
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.graphToStatement(DomHelper.java:84)
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:203)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 00: aload 0
      // 01: monitorenter
      // 02: aload 1
      // 03: ldc_w "ctx"
      // 06: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
      // 09: getstatic com/starpilot/starview/IdeaSpots.file Ljava/io/File;
      // 0c: astore 4
      // 0e: aload 4
      // 10: ifnull 16
      // 13: aload 0
      // 14: monitorexit
      // 15: return
      // 16: new java/io/File
      // 19: astore 4
      // 1b: aload 4
      // 1d: aload 1
      // 1e: invokevirtual android/content/Context.getApplicationContext ()Landroid/content/Context;
      // 21: invokevirtual android/content/Context.getFilesDir ()Ljava/io/File;
      // 24: ldc_w "crazy_spots.json"
      // 27: invokespecial java/io/File.<init> (Ljava/io/File;Ljava/lang/String;)V
      // 2a: aload 4
      // 2c: putstatic com/starpilot/starview/IdeaSpots.file Ljava/io/File;
      // 2f: getstatic com/starpilot/starview/IdeaSpots.file Ljava/io/File;
      // 32: astore 1
      // 33: aload 1
      // 34: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNull (Ljava/lang/Object;)V
      // 37: aload 1
      // 38: invokevirtual java/io/File.isFile ()Z
      // 3b: ifeq 41
      // 3e: goto 43
      // 41: aconst_null
      // 42: astore 1
      // 43: aload 1
      // 44: ifnull 9e
      // 47: aload 1
      // 48: aconst_null
      // 49: bipush 1
      // 4a: aconst_null
      // 4b: invokestatic kotlin/io/FilesKt.readText$default (Ljava/io/File;Ljava/nio/charset/Charset;ILjava/lang/Object;)Ljava/lang/String;
      // 4e: astore 4
      // 50: aload 4
      // 52: ifnull 9e
      // 55: new org/json/JSONArray
      // 58: astore 1
      // 59: aload 1
      // 5a: aload 4
      // 5c: invokespecial org/json/JSONArray.<init> (Ljava/lang/String;)V
      // 5f: bipush 0
      // 60: istore 2
      // 61: aload 1
      // 62: invokevirtual org/json/JSONArray.length ()I
      // 65: istore 3
      // 66: iload 2
      // 67: iload 3
      // 68: if_icmpge 9a
      // 6b: getstatic com/starpilot/starview/IdeaSpots.spots Ljava/util/ArrayList;
      // 6e: astore 6
      // 70: getstatic com/starpilot/starview/IdeaSpots$Spot.Companion Lcom/starpilot/starview/IdeaSpots$Spot$Companion;
      // 73: astore 5
      // 75: aload 1
      // 76: iload 2
      // 77: invokevirtual org/json/JSONArray.getJSONObject (I)Lorg/json/JSONObject;
      // 7a: astore 4
      // 7c: aload 4
      // 7e: ldc_w "getJSONObject(...)"
      // 81: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 84: aload 6
      // 86: aload 5
      // 88: aload 4
      // 8a: aconst_null
      // 8b: bipush 2
      // 8c: aconst_null
      // 8d: invokestatic com/starpilot/starview/IdeaSpots$Spot$Companion.of$default (Lcom/starpilot/starview/IdeaSpots$Spot$Companion;Lorg/json/JSONObject;Ljava/lang/String;ILjava/lang/Object;)Lcom/starpilot/starview/IdeaSpots$Spot;
      // 90: invokevirtual java/util/ArrayList.add (Ljava/lang/Object;)Z
      // 93: pop
      // 94: iinc 2 1
      // 97: goto 66
      // 9a: goto 9e
      // 9d: astore 1
      // 9e: aload 0
      // 9f: monitorexit
      // a0: return
      // a1: astore 1
      // a2: aload 0
      // a3: monitorexit
      // a4: aload 1
      // a5: athrow
   }

   public final Pair<Double, Double> local(double var1, double var3, double var5, double var7) {
      return new Pair<>((var7 - var3) * 111320.0 * Math.cos(Math.toRadians(var1)), (var5 - var1) * 111195.0);
   }

   public final IdeaSpots.Spot record(
      String param1, double param2, double param4, double param6, double param8, String param10, long param11, double param13, String param15
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
      // 000: aload 0
      // 001: monitorenter
      // 002: aload 1
      // 003: ldc_w "kind"
      // 006: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
      // 009: aload 10
      // 00b: ldc_w "note"
      // 00e: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
      // 011: aload 15
      // 013: ldc_w "src"
      // 016: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
      // 019: getstatic com/starpilot/starview/IdeaSpots.spots Ljava/util/ArrayList;
      // 01c: invokevirtual java/util/ArrayList.iterator ()Ljava/util/Iterator;
      // 01f: astore 21
      // 021: aload 21
      // 023: ldc_w "iterator(...)"
      // 026: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 029: aload 21
      // 02b: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 030: ifeq 164
      // 033: aload 21
      // 035: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 03a: astore 22
      // 03c: aload 22
      // 03e: ldc_w "next(...)"
      // 041: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 044: aload 22
      // 046: checkcast com/starpilot/starview/IdeaSpots$Spot
      // 049: astore 22
      // 04b: aload 22
      // 04d: invokevirtual com/starpilot/starview/IdeaSpots$Spot.getKind ()Ljava/lang/String;
      // 050: aload 1
      // 051: invokestatic kotlin/jvm/internal/Intrinsics.areEqual (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 054: ifeq 161
      // 057: aload 22
      // 059: invokevirtual com/starpilot/starview/IdeaSpots$Spot.getSrc ()Ljava/lang/String;
      // 05c: aload 15
      // 05e: invokestatic kotlin/jvm/internal/Intrinsics.areEqual (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 061: ifeq 161
      // 064: aload 0
      // 065: aload 22
      // 067: invokevirtual com/starpilot/starview/IdeaSpots$Spot.getHdg ()D
      // 06a: dload 6
      // 06c: dconst_0
      // 06d: bipush 4
      // 06e: aconst_null
      // 06f: invokestatic com/starpilot/starview/IdeaSpots.headingOk$default (Lcom/starpilot/starview/IdeaSpots;DDDILjava/lang/Object;)Z
      // 072: ifne 078
      // 075: goto 029
      // 078: aload 0
      // 079: dload 2
      // 07a: dload 4
      // 07c: aload 22
      // 07e: invokevirtual com/starpilot/starview/IdeaSpots$Spot.getLat ()D
      // 081: aload 22
      // 083: invokevirtual com/starpilot/starview/IdeaSpots$Spot.getLon ()D
      // 086: invokevirtual com/starpilot/starview/IdeaSpots.local (DDDD)Lkotlin/Pair;
      // 089: astore 23
      // 08b: aload 23
      // 08d: invokevirtual kotlin/Pair.component1 ()Ljava/lang/Object;
      // 090: checkcast java/lang/Number
      // 093: invokevirtual java/lang/Number.doubleValue ()D
      // 096: dstore 18
      // 098: aload 23
      // 09a: invokevirtual kotlin/Pair.component2 ()Ljava/lang/Object;
      // 09d: checkcast java/lang/Number
      // 0a0: invokevirtual java/lang/Number.doubleValue ()D
      // 0a3: dstore 16
      // 0a5: dload 18
      // 0a7: dload 18
      // 0a9: dmul
      // 0aa: dload 16
      // 0ac: dload 16
      // 0ae: dmul
      // 0af: dadd
      // 0b0: dload 13
      // 0b2: dload 13
      // 0b4: dmul
      // 0b5: dcmpl
      // 0b6: ifgt 15e
      // 0b9: dconst_1
      // 0ba: aload 22
      // 0bc: invokevirtual com/starpilot/starview/IdeaSpots$Spot.getCount ()I
      // 0bf: bipush 1
      // 0c0: iadd
      // 0c1: i2d
      // 0c2: ddiv
      // 0c3: dstore 6
      // 0c5: aload 22
      // 0c7: aload 22
      // 0c9: invokevirtual com/starpilot/starview/IdeaSpots$Spot.getLat ()D
      // 0cc: dload 2
      // 0cd: aload 22
      // 0cf: invokevirtual com/starpilot/starview/IdeaSpots$Spot.getLat ()D
      // 0d2: dsub
      // 0d3: dload 6
      // 0d5: dmul
      // 0d6: dadd
      // 0d7: invokevirtual com/starpilot/starview/IdeaSpots$Spot.setLat (D)V
      // 0da: aload 22
      // 0dc: aload 22
      // 0de: invokevirtual com/starpilot/starview/IdeaSpots$Spot.getLon ()D
      // 0e1: dload 4
      // 0e3: aload 22
      // 0e5: invokevirtual com/starpilot/starview/IdeaSpots$Spot.getLon ()D
      // 0e8: dsub
      // 0e9: dload 6
      // 0eb: dmul
      // 0ec: dadd
      // 0ed: invokevirtual com/starpilot/starview/IdeaSpots$Spot.setLon (D)V
      // 0f0: dload 8
      // 0f2: invokestatic java/lang/Double.isNaN (D)Z
      // 0f5: ifne 120
      // 0f8: aload 22
      // 0fa: invokevirtual com/starpilot/starview/IdeaSpots$Spot.getLateral ()D
      // 0fd: invokestatic java/lang/Double.isNaN (D)Z
      // 100: ifeq 106
      // 103: goto 119
      // 106: aload 22
      // 108: invokevirtual com/starpilot/starview/IdeaSpots$Spot.getLateral ()D
      // 10b: dload 8
      // 10d: aload 22
      // 10f: invokevirtual com/starpilot/starview/IdeaSpots$Spot.getLateral ()D
      // 112: dsub
      // 113: dload 6
      // 115: dmul
      // 116: dadd
      // 117: dstore 8
      // 119: aload 22
      // 11b: dload 8
      // 11d: invokevirtual com/starpilot/starview/IdeaSpots$Spot.setLateral (D)V
      // 120: aload 22
      // 122: aload 22
      // 124: invokevirtual com/starpilot/starview/IdeaSpots$Spot.getCount ()I
      // 127: bipush 1
      // 128: iadd
      // 129: invokevirtual com/starpilot/starview/IdeaSpots$Spot.setCount (I)V
      // 12c: aload 22
      // 12e: lload 11
      // 130: invokevirtual com/starpilot/starview/IdeaSpots$Spot.setLastMs (J)V
      // 133: aload 10
      // 135: checkcast java/lang/CharSequence
      // 138: invokeinterface java/lang/CharSequence.length ()I 1
      // 13d: ifle 146
      // 140: bipush 1
      // 141: istore 20
      // 143: goto 149
      // 146: bipush 0
      // 147: istore 20
      // 149: iload 20
      // 14b: ifeq 155
      // 14e: aload 22
      // 150: aload 10
      // 152: invokevirtual com/starpilot/starview/IdeaSpots$Spot.setNote (Ljava/lang/String;)V
      // 155: bipush 1
      // 156: putstatic com/starpilot/starview/IdeaSpots.dirty Z
      // 159: aload 0
      // 15a: monitorexit
      // 15b: aload 22
      // 15d: areturn
      // 15e: goto 029
      // 161: goto 029
      // 164: new com/starpilot/starview/IdeaSpots$Spot
      // 167: astore 21
      // 169: aload 21
      // 16b: aload 1
      // 16c: dload 2
      // 16d: dload 4
      // 16f: dload 6
      // 171: dload 8
      // 173: bipush 1
      // 174: lload 11
      // 176: aload 10
      // 178: aload 15
      // 17a: invokespecial com/starpilot/starview/IdeaSpots$Spot.<init> (Ljava/lang/String;DDDDIJLjava/lang/String;Ljava/lang/String;)V
      // 17d: getstatic com/starpilot/starview/IdeaSpots.spots Ljava/util/ArrayList;
      // 180: aload 21
      // 182: invokevirtual java/util/ArrayList.add (Ljava/lang/Object;)Z
      // 185: pop
      // 186: bipush 1
      // 187: putstatic com/starpilot/starview/IdeaSpots.dirty Z
      // 18a: aload 0
      // 18b: monitorexit
      // 18c: aload 21
      // 18e: areturn
      // 18f: astore 1
      // 190: aload 0
      // 191: monitorexit
      // 192: aload 1
      // 193: athrow
   }

   public final void saveSoon(long param1, boolean param3) {
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
      // 00: getstatic com/starpilot/starview/IdeaSpots.dirty Z
      // 03: ifeq a5
      // 06: iload 3
      // 07: ifne 19
      // 0a: lload 1
      // 0b: getstatic com/starpilot/starview/IdeaSpots.savedMs J
      // 0e: lsub
      // 0f: ldc2_w 30000
      // 12: lcmp
      // 13: ifge 19
      // 16: goto a5
      // 19: getstatic com/starpilot/starview/IdeaSpots.file Ljava/io/File;
      // 1c: astore 4
      // 1e: aload 4
      // 20: ifnonnull 24
      // 23: return
      // 24: aload 0
      // 25: monitorenter
      // 26: bipush 0
      // 27: putstatic com/starpilot/starview/IdeaSpots.dirty Z
      // 2a: new org/json/JSONArray
      // 2d: astore 6
      // 2f: aload 6
      // 31: invokespecial org/json/JSONArray.<init> ()V
      // 34: getstatic com/starpilot/starview/IdeaSpots.spots Ljava/util/ArrayList;
      // 37: invokevirtual java/util/ArrayList.iterator ()Ljava/util/Iterator;
      // 3a: astore 7
      // 3c: aload 7
      // 3e: ldc_w "iterator(...)"
      // 41: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 44: aload 7
      // 46: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 4b: ifeq 70
      // 4e: aload 7
      // 50: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 55: astore 5
      // 57: aload 5
      // 59: ldc_w "next(...)"
      // 5c: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 5f: aload 6
      // 61: aload 5
      // 63: checkcast com/starpilot/starview/IdeaSpots$Spot
      // 66: invokevirtual com/starpilot/starview/IdeaSpots$Spot.json ()Lorg/json/JSONObject;
      // 69: invokevirtual org/json/JSONArray.put (Ljava/lang/Object;)Lorg/json/JSONArray;
      // 6c: pop
      // 6d: goto 44
      // 70: aload 6
      // 72: invokevirtual org/json/JSONArray.toString ()Ljava/lang/String;
      // 75: astore 5
      // 77: aload 0
      // 78: monitorexit
      // 79: aload 5
      // 7b: ldc_w "synchronized(...)"
      // 7e: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 81: lload 1
      // 82: putstatic com/starpilot/starview/IdeaSpots.savedMs J
      // 85: new java/lang/Thread
      // 88: dup
      // 89: new com/starpilot/starview/IdeaSpots$$ExternalSyntheticLambda2
      // 8c: dup
      // 8d: aload 4
      // 8f: aload 5
      // 91: invokespecial com/starpilot/starview/IdeaSpots$$ExternalSyntheticLambda2.<init> (Ljava/io/File;Ljava/lang/String;)V
      // 94: ldc_w "crazy-spots"
      // 97: invokespecial java/lang/Thread.<init> (Ljava/lang/Runnable;Ljava/lang/String;)V
      // 9a: invokevirtual java/lang/Thread.start ()V
      // 9d: return
      // 9e: astore 4
      // a0: aload 0
      // a1: monitorexit
      // a2: aload 4
      // a4: athrow
      // a5: return
   }

   public static final class Seen {
      private final double ahead;
      private final double right;
      private final IdeaSpots.Spot spot;

      public Seen(IdeaSpots.Spot var1, double var2, double var4) {
         Intrinsics.checkNotNullParameter(var1, "spot");
         super();
         this.spot = var1;
         this.ahead = var2;
         this.right = var4;
      }

      public final double getAhead() {
         return this.ahead;
      }

      public final double getRight() {
         return this.right;
      }

      public final IdeaSpots.Spot getSpot() {
         return this.spot;
      }
   }

   public static final class Spot {
      public static final IdeaSpots.Spot.Companion Companion = new IdeaSpots.Spot.Companion(null);
      private int count;
      private double hdg;
      private final String kind;
      private long lastMs;
      private double lat;
      private double lateral;
      private double lon;
      private String note;
      private final String src;

      public Spot(String var1, double var2, double var4, double var6, double var8, int var10, long var11, String var13, String var14) {
         Intrinsics.checkNotNullParameter(var1, "kind");
         Intrinsics.checkNotNullParameter(var13, "note");
         Intrinsics.checkNotNullParameter(var14, "src");
         super();
         this.kind = var1;
         this.lat = var2;
         this.lon = var4;
         this.hdg = var6;
         this.lateral = var8;
         this.count = var10;
         this.lastMs = var11;
         this.note = var13;
         this.src = var14;
      }

      // $VF: synthetic method
      public Spot(
         String var1,
         double var2,
         double var4,
         double var6,
         double var8,
         int var10,
         long var11,
         String var13,
         String var14,
         int var15,
         DefaultConstructorMarker var16
      ) {
         if ((var15 & 256) != 0) {
            var14 = "me";
         }

         this(var1, var2, var4, var6, var8, var10, var11, var13, var14);
      }

      public final int getCount() {
         return this.count;
      }

      public final double getHdg() {
         return this.hdg;
      }

      public final String getKind() {
         return this.kind;
      }

      public final long getLastMs() {
         return this.lastMs;
      }

      public final double getLat() {
         return this.lat;
      }

      public final double getLateral() {
         return this.lateral;
      }

      public final double getLon() {
         return this.lon;
      }

      public final String getNote() {
         return this.note;
      }

      public final String getSrc() {
         return this.src;
      }

      public final JSONObject json() {
         JSONObject var4 = new JSONObject().put("k", this.kind).put("lat", this.lat).put("lon", this.lon);
         boolean var1 = Double.isNaN(this.hdg);
         Object var3 = null;
         Double var2;
         if (var1) {
            var2 = null;
         } else {
            var2 = this.hdg;
         }

         var4 = var4.put("h", var2);
         if (Double.isNaN(this.lateral)) {
            var2 = (Double)var3;
         } else {
            var2 = this.lateral;
         }

         JSONObject var6 = var4.put("x", var2).put("n", this.count).put("t", this.lastMs).put("note", this.note).put("src", this.src);
         Intrinsics.checkNotNullExpressionValue(var6, "put(...)");
         return var6;
      }

      public final void setCount(int var1) {
         this.count = var1;
      }

      public final void setHdg(double var1) {
         this.hdg = var1;
      }

      public final void setLastMs(long var1) {
         this.lastMs = var1;
      }

      public final void setLat(double var1) {
         this.lat = var1;
      }

      public final void setLateral(double var1) {
         this.lateral = var1;
      }

      public final void setLon(double var1) {
         this.lon = var1;
      }

      public final void setNote(String var1) {
         Intrinsics.checkNotNullParameter(var1, "<set-?>");
         this.note = var1;
      }

      public static final class Companion {
         private Companion() {
         }

         // $VF: synthetic method
         public Companion(DefaultConstructorMarker var1) {
            this();
         }

         // $VF: synthetic method
         public static IdeaSpots.Spot of$default(IdeaSpots.Spot.Companion var0, JSONObject var1, String var2, int var3, Object var4) {
            if ((var3 & 2) != 0) {
               var2 = null;
            }

            return var0.of(var1, var2);
         }

         public final IdeaSpots.Spot of(JSONObject var1, String var2) {
            Intrinsics.checkNotNullParameter(var1, "o");
            String var15 = var1.optString("k");
            Intrinsics.checkNotNullExpressionValue(var15, "optString(...)");
            double var3 = var1.optDouble("lat");
            double var5 = var1.optDouble("lon");
            double var7 = var1.optDouble("h", Double.NaN);
            double var9 = var1.optDouble("x", Double.NaN);
            int var11 = var1.optInt("n", 1);
            long var12 = var1.optLong("t");
            String var14 = var1.optString("note");
            Intrinsics.checkNotNullExpressionValue(var14, "optString(...)");
            String var16;
            if (var2 == null) {
               var16 = var1.optString("src", "me");
            } else {
               var16 = var2;
            }

            Intrinsics.checkNotNull(var16);
            return new IdeaSpots.Spot(var15, var3, var5, var7, var9, var11, var12, var14, var16);
         }
      }
   }
}
