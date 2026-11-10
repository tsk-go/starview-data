package com.starpilot.starview;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.StringsKt;

public final class DetMasks {
   public static final DetMasks INSTANCE = new DetMasks();
   private static final boolean[] keep = new boolean[2];
   private static final ArrayList<float[]>[] polys = new ArrayList[]{new ArrayList(), new ArrayList()};
   private static SharedPreferences prefs;
   private static volatile int version;

   // $VF: synthetic method
   public static CharSequence $r8$lambda$DSAHqfui9duR05SlWmMvHIlJUBU(float var0) {
      return encode$lambda$5$lambda$4(var0);
   }

   // $VF: synthetic method
   public static CharSequence $r8$lambda$ftRvvo9bMhL1PEPWv_Guoj_pyqk(float[] var0) {
      return encode$lambda$5(var0);
   }

   private DetMasks() {
   }

   private static final CharSequence encode$lambda$5(float[] var0) {
      Intrinsics.checkNotNullParameter(var0, "p");
      return ArraysKt.joinToString$default(var0, ",", null, null, 0, null, new DetMasks$$ExternalSyntheticLambda0(), 30, null);
   }

   private static final CharSequence encode$lambda$5$lambda$4(float var0) {
      StringCompanionObject var1 = StringCompanionObject.INSTANCE;
      String var2 = String.format(Locale.US, "%.4f", Arrays.copyOf(new Object[]{var0}, 1));
      Intrinsics.checkNotNullExpressionValue(var2, "format(...)");
      return var2;
   }

   private final int i(boolean var1) {
      return var1;
   }

   private final String key(boolean var1) {
      String var2;
      if (var1) {
         var2 = "det_mask_wide";
      } else {
         var2 = "det_mask_road";
      }

      return var2;
   }

   public final List<float[]> decode(String var1) {
      Intrinsics.checkNotNullParameter(var1, "s");
      Iterable var8 = StringsKt.split$default(var1, new char[]{'|'}, false, 0, 6, null);
      var var6 = new ArrayList();
      Iterator var5 = var8.iterator();

      while (var5.hasNext()) {
         Iterable var4 = StringsKt.split$default((String & CharSequence)var5.next(), new char[]{','}, false, 0, 6, null);
         var var3 = new ArrayList();
         Iterator var10 = var4.iterator();

         while (var10.hasNext()) {
            Float var7 = StringsKt.toFloatOrNull((String)var10.next());
            if (var7 != null) {
               var3.add(var7);
            }
         }

         List var11 = var3;
         List var9 = var11;
         boolean var2;
         if (var11.size() >= 6 && var11.size() % 2 == 0) {
            var2 = true;
         } else {
            var2 = false;
         }

         float[] var12 = null;
         if (!var2) {
            var9 = null;
         }

         if (var9 != null) {
            var12 = CollectionsKt.toFloatArray(var9);
         }

         if (var12 != null) {
            var6.add(var12);
         }
      }

      return var6;
   }

   public final String encode(List<float[]> var1) {
      Intrinsics.checkNotNullParameter(var1, "l");
      return CollectionsKt.joinToString$default(var1, "|", null, null, 0, null, new DetMasks$$ExternalSyntheticLambda1(), 30, null);
   }

   public final List<float[]> get(boolean param1) {
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
      // 02: getstatic com/starpilot/starview/DetMasks.polys [Ljava/util/ArrayList;
      // 05: aload 0
      // 06: iload 1
      // 07: invokespecial com/starpilot/starview/DetMasks.i (Z)I
      // 0a: aaload
      // 0b: checkcast java/lang/Iterable
      // 0e: astore 3
      // 0f: new java/util/ArrayList
      // 12: astore 2
      // 13: aload 2
      // 14: aload 3
      // 15: bipush 10
      // 17: invokestatic kotlin/collections/CollectionsKt.collectionSizeOrDefault (Ljava/lang/Iterable;I)I
      // 1a: invokespecial java/util/ArrayList.<init> (I)V
      // 1d: aload 2
      // 1e: checkcast java/util/Collection
      // 21: astore 2
      // 22: aload 3
      // 23: invokeinterface java/lang/Iterable.iterator ()Ljava/util/Iterator; 1
      // 28: astore 3
      // 29: aload 3
      // 2a: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 2f: ifeq 5a
      // 32: aload 3
      // 33: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 38: checkcast [F
      // 3b: astore 4
      // 3d: aload 4
      // 3f: aload 4
      // 41: arraylength
      // 42: invokestatic java/util/Arrays.copyOf ([FI)[F
      // 45: astore 4
      // 47: aload 4
      // 49: ldc "copyOf(...)"
      // 4b: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 4e: aload 2
      // 4f: aload 4
      // 51: invokeinterface java/util/Collection.add (Ljava/lang/Object;)Z 2
      // 56: pop
      // 57: goto 29
      // 5a: aload 2
      // 5b: checkcast java/util/List
      // 5e: astore 2
      // 5f: aload 0
      // 60: monitorexit
      // 61: aload 2
      // 62: areturn
      // 63: astore 2
      // 64: aload 0
      // 65: monitorexit
      // 66: aload 2
      // 67: athrow
   }

   public final int getVersion() {
      return version;
   }

   public final void init(Context param1) {
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
      // 03: ldc "ctx"
      // 05: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
      // 08: getstatic com/starpilot/starview/DetMasks.prefs Landroid/content/SharedPreferences;
      // 0b: astore 5
      // 0d: aload 5
      // 0f: ifnull 15
      // 12: aload 0
      // 13: monitorexit
      // 14: return
      // 15: aload 1
      // 16: invokevirtual android/content/Context.getApplicationContext ()Landroid/content/Context;
      // 19: ldc "starview_ui"
      // 1b: bipush 0
      // 1c: invokevirtual android/content/Context.getSharedPreferences (Ljava/lang/String;I)Landroid/content/SharedPreferences;
      // 1f: astore 6
      // 21: aload 6
      // 23: putstatic com/starpilot/starview/DetMasks.prefs Landroid/content/SharedPreferences;
      // 26: bipush 0
      // 27: istore 2
      // 28: iload 2
      // 29: bipush 2
      // 2a: if_icmpge b2
      // 2d: bipush 2
      // 2e: newarray 4
      // 30: dup
      // 31: bipush 0
      // 32: ldc 0
      // 34: bastore
      // 35: dup
      // 36: bipush 1
      // 37: ldc 1
      // 39: bastore
      // 3a: iload 2
      // 3b: baload
      // 3c: istore 4
      // 3e: getstatic com/starpilot/starview/DetMasks.polys [Ljava/util/ArrayList;
      // 41: aload 0
      // 42: iload 4
      // 44: invokespecial com/starpilot/starview/DetMasks.i (Z)I
      // 47: aaload
      // 48: astore 7
      // 4a: aload 6
      // 4c: aload 0
      // 4d: iload 4
      // 4f: invokespecial com/starpilot/starview/DetMasks.key (Z)Ljava/lang/String;
      // 52: ldc ""
      // 54: invokeinterface android/content/SharedPreferences.getString (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String; 3
      // 59: astore 5
      // 5b: aload 5
      // 5d: astore 1
      // 5e: aload 5
      // 60: ifnonnull 66
      // 63: ldc ""
      // 65: astore 1
      // 66: aload 7
      // 68: aload 0
      // 69: aload 1
      // 6a: invokevirtual com/starpilot/starview/DetMasks.decode (Ljava/lang/String;)Ljava/util/List;
      // 6d: checkcast java/util/Collection
      // 70: invokevirtual java/util/ArrayList.addAll (Ljava/util/Collection;)Z
      // 73: pop
      // 74: getstatic com/starpilot/starview/DetMasks.keep [Z
      // 77: astore 7
      // 79: aload 0
      // 7a: iload 4
      // 7c: invokespecial com/starpilot/starview/DetMasks.i (Z)I
      // 7f: istore 3
      // 80: aload 0
      // 81: iload 4
      // 83: invokespecial com/starpilot/starview/DetMasks.key (Z)Ljava/lang/String;
      // 86: astore 1
      // 87: new java/lang/StringBuilder
      // 8a: astore 5
      // 8c: aload 5
      // 8e: invokespecial java/lang/StringBuilder.<init> ()V
      // 91: aload 7
      // 93: iload 3
      // 94: aload 6
      // 96: aload 5
      // 98: aload 1
      // 99: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 9c: ldc_w "_keep"
      // 9f: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // a2: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // a5: bipush 0
      // a6: invokeinterface android/content/SharedPreferences.getBoolean (Ljava/lang/String;Z)Z 3
      // ab: bastore
      // ac: iinc 2 1
      // af: goto 28
      // b2: getstatic com/starpilot/starview/DetMasks.version I
      // b5: bipush 1
      // b6: iadd
      // b7: putstatic com/starpilot/starview/DetMasks.version I
      // ba: aload 0
      // bb: monitorexit
      // bc: return
      // bd: astore 1
      // be: aload 0
      // bf: monitorexit
      // c0: aload 1
      // c1: athrow
   }

   public final boolean inside(float[] var1, float var2, float var3) {
      Intrinsics.checkNotNullParameter(var1, "p");
      int var9 = var1.length / 2;
      boolean var11 = false;
      int var6 = 0;

      while (var6 < var9) {
         boolean var13 = true;
         int var10 = (var6 + var9 - 1) % var9;
         float var4 = var1[var6 * 2 + 1];
         float var5 = var1[var10 * 2 + 1];
         boolean var7;
         if (var4 > var3) {
            var7 = true;
         } else {
            var7 = false;
         }

         boolean var8;
         if (var5 > var3) {
            var8 = true;
         } else {
            var8 = false;
         }

         boolean var12 = var11;
         if (var7 != var8) {
            var12 = var11;
            if (var2 < (var1[var10 * 2] - var1[var6 * 2]) * (var3 - var4) / (var5 - var4) + var1[var6 * 2]) {
               if (!var11) {
                  var11 = var13;
               } else {
                  var11 = false;
               }

               var12 = var11;
            }
         }

         var6++;
         var11 = var12;
      }

      return var11;
   }

   public final boolean keep(boolean param1) {
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
      // 02: getstatic com/starpilot/starview/DetMasks.keep [Z
      // 05: aload 0
      // 06: iload 1
      // 07: invokespecial com/starpilot/starview/DetMasks.i (Z)I
      // 0a: baload
      // 0b: istore 1
      // 0c: aload 0
      // 0d: monitorexit
      // 0e: iload 1
      // 0f: ireturn
      // 10: astore 2
      // 11: aload 0
      // 12: monitorexit
      // 13: aload 2
      // 14: athrow
   }

   public final boolean[] maskFor(boolean var1, int var2, int var3) {
      Pair var5;
      synchronized (this) {
         var5 = TuplesKt.to(INSTANCE.get(var1), keep[INSTANCE.i(var1)]);
      }

      List var4 = (List)var5.component1();
      var1 = (Boolean)var5.component2();
      boolean[] var11 = this.raster(var4, var2, var3);
      if (var11 == null) {
         return null;
      } else {
         if (var1) {
            var2 = 0;

            for (int var10 = var11.length; var2 < var10; var2++) {
               var11[var2] ^= true;
            }
         }

         return var11;
      }
   }

   public final boolean[] raster(List<float[]> var1, int var2, int var3) {
      Intrinsics.checkNotNullParameter(var1, "list");
      if (!var1.isEmpty() && var2 > 0 && var3 > 0) {
         boolean[] var15 = new boolean[var2 * var3];
         float[] var16 = new float[256];

         for (float[] var18 : var1) {
            int var13 = var18.length / 2;

            for (int var7 = 0; var7 < var3; var7++) {
               float var6 = (var7 + 0.5F) / var3;
               int var8 = 0;
               int var9 = 0;

               while (true) {
                  boolean var12 = false;
                  int var10 = 1;
                  if (var9 >= var13) {
                     ArraysKt.sort(var16, 0, var8);

                     for (byte var19 = 0; var19 + 1 < var8; var19 += 2) {
                        var10 = Math.max(0, (int)((float)Math.ceil(var16[var19] - 0.5F)));
                        int var21 = Math.min(var2 - 1, (int)((float)Math.ceil(var16[var19 + 1] - 0.5F)) - 1);
                        if (var10 <= var21) {
                           while (true) {
                              var15[var7 * var2 + var10] = true;
                              if (var10 == var21) {
                                 break;
                              }

                              var10++;
                           }
                        }
                     }
                     break;
                  }

                  int var14 = (var9 + 1) % var13;
                  float var5 = var18[var9 * 2 + 1];
                  float var4 = var18[var14 * 2 + 1];
                  if (!(var5 <= var6)) {
                     var10 = 0;
                  }

                  if (var4 <= var6) {
                     var12 = true;
                  }

                  int var11 = var8;
                  if (var10 != var12) {
                     var11 = var8;
                     if (var8 < var16.length) {
                        var16[var8] = (var18[var9 * 2] + (var6 - var5) / (var4 - var5) * (var18[var14 * 2] - var18[var9 * 2])) * var2;
                        var11 = var8 + 1;
                     }
                  }

                  var9++;
                  var8 = var11;
               }
            }
         }

         return var15;
      } else {
         return null;
      }
   }

   public final void set(boolean param1, List<float[]> param2) {
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
      // 002: aload 2
      // 003: ldc_w "list"
      // 006: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
      // 009: getstatic com/starpilot/starview/DetMasks.polys [Ljava/util/ArrayList;
      // 00c: aload 0
      // 00d: iload 1
      // 00e: invokespecial com/starpilot/starview/DetMasks.i (Z)I
      // 011: aaload
      // 012: astore 5
      // 014: aload 5
      // 016: invokevirtual java/util/ArrayList.clear ()V
      // 019: aload 2
      // 01a: checkcast java/lang/Iterable
      // 01d: astore 6
      // 01f: new java/util/ArrayList
      // 022: astore 2
      // 023: aload 2
      // 024: invokespecial java/util/ArrayList.<init> ()V
      // 027: aload 2
      // 028: checkcast java/util/Collection
      // 02b: astore 2
      // 02c: aload 6
      // 02e: invokeinterface java/lang/Iterable.iterator ()Ljava/util/Iterator; 1
      // 033: astore 6
      // 035: aload 6
      // 037: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 03c: istore 4
      // 03e: bipush 1
      // 03f: istore 3
      // 040: iload 4
      // 042: ifeq 06e
      // 045: aload 6
      // 047: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 04c: astore 7
      // 04e: aload 7
      // 050: checkcast [F
      // 053: arraylength
      // 054: bipush 6
      // 056: if_icmplt 05c
      // 059: goto 05e
      // 05c: bipush 0
      // 05d: istore 3
      // 05e: iload 3
      // 05f: ifeq 035
      // 062: aload 2
      // 063: aload 7
      // 065: invokeinterface java/util/Collection.add (Ljava/lang/Object;)Z 2
      // 06a: pop
      // 06b: goto 035
      // 06e: aload 2
      // 06f: checkcast java/util/List
      // 072: astore 2
      // 073: aload 2
      // 074: checkcast java/lang/Iterable
      // 077: astore 6
      // 079: new java/util/ArrayList
      // 07c: astore 2
      // 07d: aload 2
      // 07e: aload 6
      // 080: bipush 10
      // 082: invokestatic kotlin/collections/CollectionsKt.collectionSizeOrDefault (Ljava/lang/Iterable;I)I
      // 085: invokespecial java/util/ArrayList.<init> (I)V
      // 088: aload 2
      // 089: checkcast java/util/Collection
      // 08c: astore 2
      // 08d: aload 6
      // 08f: invokeinterface java/lang/Iterable.iterator ()Ljava/util/Iterator; 1
      // 094: astore 6
      // 096: aload 6
      // 098: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 09d: ifeq 0c9
      // 0a0: aload 6
      // 0a2: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 0a7: checkcast [F
      // 0aa: astore 7
      // 0ac: aload 7
      // 0ae: aload 7
      // 0b0: arraylength
      // 0b1: invokestatic java/util/Arrays.copyOf ([FI)[F
      // 0b4: astore 7
      // 0b6: aload 7
      // 0b8: ldc "copyOf(...)"
      // 0ba: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 0bd: aload 2
      // 0be: aload 7
      // 0c0: invokeinterface java/util/Collection.add (Ljava/lang/Object;)Z 2
      // 0c5: pop
      // 0c6: goto 096
      // 0c9: aload 2
      // 0ca: checkcast java/util/List
      // 0cd: astore 2
      // 0ce: aload 5
      // 0d0: aload 2
      // 0d1: checkcast java/util/Collection
      // 0d4: invokevirtual java/util/ArrayList.addAll (Ljava/util/Collection;)Z
      // 0d7: pop
      // 0d8: getstatic com/starpilot/starview/DetMasks.prefs Landroid/content/SharedPreferences;
      // 0db: astore 2
      // 0dc: aload 2
      // 0dd: ifnull 10a
      // 0e0: aload 2
      // 0e1: invokeinterface android/content/SharedPreferences.edit ()Landroid/content/SharedPreferences$Editor; 1
      // 0e6: astore 2
      // 0e7: aload 2
      // 0e8: ifnull 10a
      // 0eb: aload 2
      // 0ec: aload 0
      // 0ed: iload 1
      // 0ee: invokespecial com/starpilot/starview/DetMasks.key (Z)Ljava/lang/String;
      // 0f1: aload 0
      // 0f2: aload 5
      // 0f4: checkcast java/util/List
      // 0f7: invokevirtual com/starpilot/starview/DetMasks.encode (Ljava/util/List;)Ljava/lang/String;
      // 0fa: invokeinterface android/content/SharedPreferences$Editor.putString (Ljava/lang/String;Ljava/lang/String;)Landroid/content/SharedPreferences$Editor; 3
      // 0ff: astore 2
      // 100: aload 2
      // 101: ifnull 10a
      // 104: aload 2
      // 105: invokeinterface android/content/SharedPreferences$Editor.apply ()V 1
      // 10a: getstatic com/starpilot/starview/DetMasks.version I
      // 10d: bipush 1
      // 10e: iadd
      // 10f: putstatic com/starpilot/starview/DetMasks.version I
      // 112: aload 0
      // 113: monitorexit
      // 114: return
      // 115: astore 2
      // 116: aload 0
      // 117: monitorexit
      // 118: aload 2
      // 119: athrow
   }

   public final void setKeep(boolean param1, boolean param2) {
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
      // 02: getstatic com/starpilot/starview/DetMasks.keep [Z
      // 05: aload 0
      // 06: iload 1
      // 07: invokespecial com/starpilot/starview/DetMasks.i (Z)I
      // 0a: iload 2
      // 0b: bastore
      // 0c: getstatic com/starpilot/starview/DetMasks.prefs Landroid/content/SharedPreferences;
      // 0f: astore 3
      // 10: aload 3
      // 11: ifnull 52
      // 14: aload 3
      // 15: invokeinterface android/content/SharedPreferences.edit ()Landroid/content/SharedPreferences$Editor; 1
      // 1a: astore 3
      // 1b: aload 3
      // 1c: ifnull 52
      // 1f: aload 0
      // 20: iload 1
      // 21: invokespecial com/starpilot/starview/DetMasks.key (Z)Ljava/lang/String;
      // 24: astore 5
      // 26: new java/lang/StringBuilder
      // 29: astore 4
      // 2b: aload 4
      // 2d: invokespecial java/lang/StringBuilder.<init> ()V
      // 30: aload 3
      // 31: aload 4
      // 33: aload 5
      // 35: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 38: ldc_w "_keep"
      // 3b: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 3e: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 41: iload 2
      // 42: invokeinterface android/content/SharedPreferences$Editor.putBoolean (Ljava/lang/String;Z)Landroid/content/SharedPreferences$Editor; 3
      // 47: astore 3
      // 48: aload 3
      // 49: ifnull 52
      // 4c: aload 3
      // 4d: invokeinterface android/content/SharedPreferences$Editor.apply ()V 1
      // 52: getstatic com/starpilot/starview/DetMasks.version I
      // 55: bipush 1
      // 56: iadd
      // 57: putstatic com/starpilot/starview/DetMasks.version I
      // 5a: aload 0
      // 5b: monitorexit
      // 5c: return
      // 5d: astore 3
      // 5e: aload 0
      // 5f: monitorexit
      // 60: aload 3
      // 61: athrow
   }
}
