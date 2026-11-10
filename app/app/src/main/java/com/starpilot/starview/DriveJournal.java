package com.starpilot.starview;

import android.content.Context;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.json.JSONArray;
import org.json.JSONObject;

public final class DriveJournal {
   public static final DriveJournal INSTANCE = new DriveJournal();
   private static final int KEEP = 3000;
   private static File file;
   private static long lastFeed;
   private static final JournalLogic logic = new JournalLogic();

   // $VF: synthetic method
   public static CharSequence $r8$lambda$Z8fqNJm09VQcz3NnqKH7RcLXaWI(JSONObject var0) {
      return onState$lambda$6$lambda$2(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$rhP4qrnQqPNX_fzFyOjdQH0OSpc(File var0, String var1) {
      onState$lambda$6$lambda$5(var0, var1);
   }

   private DriveJournal() {
   }

   private final List<JSONObject> objects(JSONArray var1) {
      Iterable var3 = RangesKt.until(0, var1.length());
      var var2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var3, 10));
      Iterator var4 = var3.iterator();

      while (var4.hasNext()) {
         var2.add(var1.getJSONObject(((IntIterator)var4).nextInt()));
      }

      return var2;
   }

   private static final CharSequence onState$lambda$6$lambda$2(JSONObject var0) {
      Intrinsics.checkNotNullParameter(var0, "it");
      return var0 + "\n";
   }

   private static final void onState$lambda$6$lambda$5(File param0, String param1) {
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
      // 00: getstatic com/starpilot/starview/DriveJournal.INSTANCE Lcom/starpilot/starview/DriveJournal;
      // 03: astore 2
      // 04: aload 2
      // 05: monitorenter
      // 06: getstatic com/starpilot/starview/DriveJournal.INSTANCE Lcom/starpilot/starview/DriveJournal;
      // 09: astore 3
      // 0a: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 0d: astore 4
      // 0f: aload 0
      // 10: aload 1
      // 11: aconst_null
      // 12: bipush 2
      // 13: aconst_null
      // 14: invokestatic kotlin/io/FilesKt.appendText$default (Ljava/io/File;Ljava/lang/String;Ljava/nio/charset/Charset;ILjava/lang/Object;)V
      // 17: aload 3
      // 18: aload 0
      // 19: invokespecial com/starpilot/starview/DriveJournal.trim (Ljava/io/File;)V
      // 1c: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 1f: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 22: pop
      // 23: goto 33
      // 26: astore 1
      // 27: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 2a: astore 0
      // 2b: aload 1
      // 2c: invokestatic kotlin/ResultKt.createFailure (Ljava/lang/Throwable;)Ljava/lang/Object;
      // 2f: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 32: pop
      // 33: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 36: astore 0
      // 37: aload 2
      // 38: monitorexit
      // 39: return
      // 3a: astore 0
      // 3b: aload 2
      // 3c: monitorexit
      // 3d: aload 0
      // 3e: athrow
   }

   // $VF: synthetic method
   public static List read$default(DriveJournal var0, String var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = null;
      }

      return var0.read(var1);
   }

   private final void trim(File var1) {
      if (var1.length() >= 1500000L) {
         FilesKt.writeText$default(
            var1,
            CollectionsKt.joinToString$default(
               CollectionsKt.takeLast(FilesKt.readLines$default(var1, null, 1, null), 3000), "\n", null, "\n", 0, null, null, 58, null
            ),
            null,
            2,
            null
         );
      }
   }

   public final void clear() {
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
      // 00: aload 0
      // 01: monitorenter
      // 02: getstatic com/starpilot/starview/DriveJournal.file Ljava/io/File;
      // 05: astore 1
      // 06: aload 1
      // 07: ifnull 0f
      // 0a: aload 1
      // 0b: invokevirtual java/io/File.delete ()Z
      // 0e: pop
      // 0f: aload 0
      // 10: monitorexit
      // 11: return
      // 12: astore 1
      // 13: aload 0
      // 14: monitorexit
      // 15: aload 1
      // 16: athrow
   }

   public final void init(Context var1) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      if (file == null) {
         file = new File(var1.getApplicationContext().getFilesDir(), "journal.jsonl");
      }
   }

   public final void onState(Telemetry param1) {
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
      // 001: ldc "t"
      // 003: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
      // 006: invokestatic java/lang/System.currentTimeMillis ()J
      // 009: lstore 14
      // 00b: lload 14
      // 00d: getstatic com/starpilot/starview/DriveJournal.lastFeed J
      // 010: lsub
      // 011: ldc2_w 100
      // 014: lcmp
      // 015: ifge 019
      // 018: return
      // 019: lload 14
      // 01b: putstatic com/starpilot/starview/DriveJournal.lastFeed J
      // 01e: aload 1
      // 01f: invokevirtual com/starpilot/starview/Telemetry.getGpsFixes ()Ljava/util/concurrent/ConcurrentHashMap;
      // 022: invokevirtual java/util/concurrent/ConcurrentHashMap.values ()Ljava/util/Collection;
      // 025: astore 16
      // 027: aload 16
      // 029: ldc "<get-values>(...)"
      // 02b: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 02e: aload 16
      // 030: checkcast java/lang/Iterable
      // 033: astore 17
      // 035: new java/util/ArrayList
      // 038: dup
      // 039: invokespecial java/util/ArrayList.<init> ()V
      // 03c: checkcast java/util/Collection
      // 03f: astore 16
      // 041: aload 17
      // 043: invokeinterface java/lang/Iterable.iterator ()Ljava/util/Iterator; 1
      // 048: astore 17
      // 04a: aload 17
      // 04c: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 051: ifeq 075
      // 054: aload 17
      // 056: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 05b: astore 18
      // 05d: aload 18
      // 05f: checkcast com/starpilot/starview/GpsFixInfo
      // 062: invokevirtual com/starpilot/starview/GpsFixInfo.getHasFix ()Z
      // 065: ifeq 04a
      // 068: aload 16
      // 06a: aload 18
      // 06c: invokeinterface java/util/Collection.add (Ljava/lang/Object;)Z 2
      // 071: pop
      // 072: goto 04a
      // 075: aload 16
      // 077: checkcast java/util/List
      // 07a: astore 16
      // 07c: aload 16
      // 07e: checkcast java/lang/Iterable
      // 081: invokeinterface java/lang/Iterable.iterator ()Ljava/util/Iterator; 1
      // 086: astore 18
      // 088: aload 18
      // 08a: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 08f: ifne 098
      // 092: aconst_null
      // 093: astore 16
      // 095: goto 0eb
      // 098: aload 18
      // 09a: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 09f: astore 16
      // 0a1: aload 18
      // 0a3: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 0a8: ifne 0ae
      // 0ab: goto 0eb
      // 0ae: aload 16
      // 0b0: checkcast com/starpilot/starview/GpsFixInfo
      // 0b3: invokevirtual com/starpilot/starview/GpsFixInfo.getHAcc ()F
      // 0b6: fstore 6
      // 0b8: aload 18
      // 0ba: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 0bf: astore 17
      // 0c1: aload 17
      // 0c3: checkcast com/starpilot/starview/GpsFixInfo
      // 0c6: invokevirtual com/starpilot/starview/GpsFixInfo.getHAcc ()F
      // 0c9: fstore 8
      // 0cb: fload 6
      // 0cd: fstore 7
      // 0cf: fload 6
      // 0d1: fload 8
      // 0d3: invokestatic java/lang/Float.compare (FF)I
      // 0d6: ifle 0e1
      // 0d9: aload 17
      // 0db: astore 16
      // 0dd: fload 8
      // 0df: fstore 7
      // 0e1: aload 18
      // 0e3: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 0e8: ifne 20e
      // 0eb: aload 16
      // 0ed: checkcast com/starpilot/starview/GpsFixInfo
      // 0f0: astore 17
      // 0f2: getstatic com/starpilot/starview/DriveJournal.logic Lcom/starpilot/starview/JournalLogic;
      // 0f5: astore 16
      // 0f7: aload 16
      // 0f9: monitorenter
      // 0fa: getstatic com/starpilot/starview/DriveJournal.logic Lcom/starpilot/starview/JournalLogic;
      // 0fd: astore 22
      // 0ff: new com/starpilot/starview/JournalLogic$Sample
      // 102: astore 19
      // 104: aload 1
      // 105: invokevirtual com/starpilot/starview/Telemetry.getStarted ()Z
      // 108: istore 11
      // 10a: aload 1
      // 10b: invokevirtual com/starpilot/starview/Telemetry.getEnabled ()Z
      // 10e: istore 12
      // 110: aload 1
      // 111: invokevirtual com/starpilot/starview/Telemetry.getVEgo ()F
      // 114: fstore 6
      // 116: aload 1
      // 117: invokevirtual com/starpilot/starview/Telemetry.getAEgo ()F
      // 11a: fstore 7
      // 11c: aload 1
      // 11d: invokevirtual com/starpilot/starview/Telemetry.getAlertText1 ()Ljava/lang/String;
      // 120: astore 20
      // 122: aload 1
      // 123: invokevirtual com/starpilot/starview/Telemetry.getAlertText2 ()Ljava/lang/String;
      // 126: astore 21
      // 128: aload 1
      // 129: invokevirtual com/starpilot/starview/Telemetry.getAlertStatus ()Ljava/lang/String;
      // 12c: astore 18
      // 12e: aload 1
      // 12f: invokevirtual com/starpilot/starview/Telemetry.getBrakePressed ()Z
      // 132: istore 13
      // 134: aload 1
      // 135: invokevirtual com/starpilot/starview/Telemetry.getGasPressed ()Z
      // 138: istore 9
      // 13a: aload 1
      // 13b: invokevirtual com/starpilot/starview/Telemetry.getSteeringPressed ()Z
      // 13e: istore 10
      // 140: ldc2_w NaN
      // 143: dstore 4
      // 145: aload 17
      // 147: ifnull 153
      // 14a: aload 17
      // 14c: invokevirtual com/starpilot/starview/GpsFixInfo.getLat ()D
      // 14f: dstore 2
      // 150: goto 157
      // 153: ldc2_w NaN
      // 156: dstore 2
      // 157: aload 17
      // 159: ifnull 163
      // 15c: aload 17
      // 15e: invokevirtual com/starpilot/starview/GpsFixInfo.getLon ()D
      // 161: dstore 4
      // 163: aload 19
      // 165: lload 14
      // 167: iload 11
      // 169: iload 12
      // 16b: fload 6
      // 16d: fload 7
      // 16f: aload 20
      // 171: aload 21
      // 173: aload 18
      // 175: iload 13
      // 177: iload 9
      // 179: iload 10
      // 17b: dload 2
      // 17c: dload 4
      // 17e: aload 1
      // 17f: invokevirtual com/starpilot/starview/Telemetry.getRoadName ()Ljava/lang/String;
      // 182: invokespecial com/starpilot/starview/JournalLogic$Sample.<init> (JZZFFLjava/lang/String;Ljava/lang/String;Ljava/lang/String;ZZZDDLjava/lang/String;)V
      // 185: aload 22
      // 187: aload 19
      // 189: invokevirtual com/starpilot/starview/JournalLogic.feed (Lcom/starpilot/starview/JournalLogic$Sample;)V
      // 18c: getstatic com/starpilot/starview/DriveJournal.logic Lcom/starpilot/starview/JournalLogic;
      // 18f: invokevirtual com/starpilot/starview/JournalLogic.getOut ()Ljava/util/ArrayList;
      // 192: invokevirtual java/util/ArrayList.isEmpty ()Z
      // 195: istore 9
      // 197: iload 9
      // 199: ifeq 1a0
      // 19c: aload 16
      // 19e: monitorexit
      // 19f: return
      // 1a0: getstatic com/starpilot/starview/DriveJournal.logic Lcom/starpilot/starview/JournalLogic;
      // 1a3: invokevirtual com/starpilot/starview/JournalLogic.getOut ()Ljava/util/ArrayList;
      // 1a6: checkcast java/lang/Iterable
      // 1a9: astore 1
      // 1aa: ldc_w ""
      // 1ad: checkcast java/lang/CharSequence
      // 1b0: astore 17
      // 1b2: new com/starpilot/starview/DriveJournal$$ExternalSyntheticLambda0
      // 1b5: astore 18
      // 1b7: aload 18
      // 1b9: invokespecial com/starpilot/starview/DriveJournal$$ExternalSyntheticLambda0.<init> ()V
      // 1bc: aload 1
      // 1bd: aload 17
      // 1bf: aconst_null
      // 1c0: aconst_null
      // 1c1: bipush 0
      // 1c2: aconst_null
      // 1c3: aload 18
      // 1c5: bipush 30
      // 1c7: aconst_null
      // 1c8: invokestatic kotlin/collections/CollectionsKt.joinToString$default (Ljava/lang/Iterable;Ljava/lang/CharSequence;Ljava/lang/CharSequence;Ljava/lang/CharSequence;ILjava/lang/CharSequence;Lkotlin/jvm/functions/Function1;ILjava/lang/Object;)Ljava/lang/String;
      // 1cb: astore 17
      // 1cd: getstatic com/starpilot/starview/DriveJournal.logic Lcom/starpilot/starview/JournalLogic;
      // 1d0: invokevirtual com/starpilot/starview/JournalLogic.getOut ()Ljava/util/ArrayList;
      // 1d3: invokevirtual java/util/ArrayList.clear ()V
      // 1d6: getstatic com/starpilot/starview/DriveJournal.file Ljava/io/File;
      // 1d9: astore 19
      // 1db: aload 19
      // 1dd: ifnonnull 1e4
      // 1e0: aload 16
      // 1e2: monitorexit
      // 1e3: return
      // 1e4: new java/lang/Thread
      // 1e7: astore 1
      // 1e8: new com/starpilot/starview/DriveJournal$$ExternalSyntheticLambda1
      // 1eb: astore 18
      // 1ed: aload 18
      // 1ef: aload 19
      // 1f1: aload 17
      // 1f3: invokespecial com/starpilot/starview/DriveJournal$$ExternalSyntheticLambda1.<init> (Ljava/io/File;Ljava/lang/String;)V
      // 1f6: aload 1
      // 1f7: aload 18
      // 1f9: invokespecial java/lang/Thread.<init> (Ljava/lang/Runnable;)V
      // 1fc: aload 1
      // 1fd: invokevirtual java/lang/Thread.start ()V
      // 200: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 203: astore 1
      // 204: aload 16
      // 206: monitorexit
      // 207: return
      // 208: astore 1
      // 209: aload 16
      // 20b: monitorexit
      // 20c: aload 1
      // 20d: athrow
      // 20e: fload 7
      // 210: fstore 6
      // 212: goto 0b8
   }

   public final List<JSONObject> read(String param1) {
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
      // 000: getstatic com/starpilot/starview/DriveJournal.file Ljava/io/File;
      // 003: astore 3
      // 004: aload 3
      // 005: ifnonnull 00c
      // 008: invokestatic kotlin/collections/CollectionsKt.emptyList ()Ljava/util/List;
      // 00b: areturn
      // 00c: aload 0
      // 00d: monitorenter
      // 00e: getstatic com/starpilot/starview/DriveJournal.INSTANCE Lcom/starpilot/starview/DriveJournal;
      // 011: astore 4
      // 013: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 016: astore 4
      // 018: aload 3
      // 019: aconst_null
      // 01a: bipush 1
      // 01b: aconst_null
      // 01c: invokestatic kotlin/io/FilesKt.readLines$default (Ljava/io/File;Ljava/nio/charset/Charset;ILjava/lang/Object;)Ljava/util/List;
      // 01f: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 022: astore 3
      // 023: goto 034
      // 026: astore 3
      // 027: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 02a: astore 4
      // 02c: aload 3
      // 02d: invokestatic kotlin/ResultKt.createFailure (Ljava/lang/Throwable;)Ljava/lang/Object;
      // 030: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 033: astore 3
      // 034: invokestatic kotlin/collections/CollectionsKt.emptyList ()Ljava/util/List;
      // 037: astore 5
      // 039: aload 3
      // 03a: astore 4
      // 03c: aload 3
      // 03d: invokestatic kotlin/Result.isFailure-impl (Ljava/lang/Object;)Z
      // 040: ifeq 047
      // 043: aload 5
      // 045: astore 4
      // 047: aload 4
      // 049: checkcast java/util/List
      // 04c: astore 3
      // 04d: aload 0
      // 04e: monitorexit
      // 04f: aload 3
      // 050: checkcast java/lang/Iterable
      // 053: astore 3
      // 054: new java/util/ArrayList
      // 057: dup
      // 058: invokespecial java/util/ArrayList.<init> ()V
      // 05b: checkcast java/util/Collection
      // 05e: astore 5
      // 060: aload 3
      // 061: invokeinterface java/lang/Iterable.iterator ()Ljava/util/Iterator; 1
      // 066: astore 6
      // 068: aload 6
      // 06a: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 06f: ifeq 0cd
      // 072: aload 6
      // 074: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 079: checkcast java/lang/String
      // 07c: astore 3
      // 07d: getstatic com/starpilot/starview/DriveJournal.INSTANCE Lcom/starpilot/starview/DriveJournal;
      // 080: astore 4
      // 082: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 085: astore 4
      // 087: new org/json/JSONObject
      // 08a: astore 4
      // 08c: aload 4
      // 08e: aload 3
      // 08f: invokespecial org/json/JSONObject.<init> (Ljava/lang/String;)V
      // 092: aload 4
      // 094: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 097: astore 3
      // 098: goto 0aa
      // 09b: astore 4
      // 09d: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 0a0: astore 3
      // 0a1: aload 4
      // 0a3: invokestatic kotlin/ResultKt.createFailure (Ljava/lang/Throwable;)Ljava/lang/Object;
      // 0a6: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 0a9: astore 3
      // 0aa: aload 3
      // 0ab: astore 4
      // 0ad: aload 3
      // 0ae: invokestatic kotlin/Result.isFailure-impl (Ljava/lang/Object;)Z
      // 0b1: ifeq 0b7
      // 0b4: aconst_null
      // 0b5: astore 4
      // 0b7: aload 4
      // 0b9: checkcast org/json/JSONObject
      // 0bc: astore 3
      // 0bd: aload 3
      // 0be: ifnull 0ca
      // 0c1: aload 5
      // 0c3: aload 3
      // 0c4: invokeinterface java/util/Collection.add (Ljava/lang/Object;)Z 2
      // 0c9: pop
      // 0ca: goto 068
      // 0cd: aload 5
      // 0cf: checkcast java/util/List
      // 0d2: astore 3
      // 0d3: aload 3
      // 0d4: checkcast java/lang/Iterable
      // 0d7: astore 4
      // 0d9: new java/util/ArrayList
      // 0dc: dup
      // 0dd: invokespecial java/util/ArrayList.<init> ()V
      // 0e0: checkcast java/util/Collection
      // 0e3: astore 3
      // 0e4: aload 4
      // 0e6: invokeinterface java/lang/Iterable.iterator ()Ljava/util/Iterator; 1
      // 0eb: astore 5
      // 0ed: aload 5
      // 0ef: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 0f4: ifeq 134
      // 0f7: aload 5
      // 0f9: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 0fe: astore 4
      // 100: aload 4
      // 102: checkcast org/json/JSONObject
      // 105: astore 6
      // 107: aload 1
      // 108: ifnull 122
      // 10b: aload 6
      // 10d: ldc_w "k"
      // 110: invokevirtual org/json/JSONObject.optString (Ljava/lang/String;)Ljava/lang/String;
      // 113: aload 1
      // 114: invokestatic kotlin/jvm/internal/Intrinsics.areEqual (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 117: ifeq 11d
      // 11a: goto 122
      // 11d: bipush 0
      // 11e: istore 2
      // 11f: goto 124
      // 122: bipush 1
      // 123: istore 2
      // 124: iload 2
      // 125: ifeq 0ed
      // 128: aload 3
      // 129: aload 4
      // 12b: invokeinterface java/util/Collection.add (Ljava/lang/Object;)Z 2
      // 130: pop
      // 131: goto 0ed
      // 134: aload 3
      // 135: checkcast java/util/List
      // 138: astore 1
      // 139: aload 1
      // 13a: areturn
      // 13b: astore 1
      // 13c: aload 0
      // 13d: monitorexit
      // 13e: aload 1
      // 13f: athrow
   }
}
