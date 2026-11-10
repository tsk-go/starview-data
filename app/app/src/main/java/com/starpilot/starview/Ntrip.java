package com.starpilot.starview;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.ConnectivityManager.NetworkCallback;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;
import kotlin.Unit;
import kotlin.collections.ArrayDeque;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import org.json.JSONObject;

public final class Ntrip {
   private static final long GGA_EVERY_MS = 10000L;
   public static final Ntrip INSTANCE = new Ntrip();
   private static final long KEEP_MS = 120000L;
   private static final String NEARER_BASE = "a nearer base";
   public static final String PREFS = "starview_ntrip";
   private static final long QUIET_HANGUP_MS = 90000L;
   private static final String RETRY_MAIN = "time to try the main caster again";
   private static final long SEND_EVERY_MS = 250L;
   private static final String TAG = "StarView/Ntrip";
   private static final HashMap<String, Long> badBases = new HashMap<>();
   private static volatile long bytesIn;
   private static boolean commaGoneNoted;
   private static volatile int generation;
   private static final ArrayDeque<String> history = new ArrayDeque<>();
   private static volatile long lastDataMs;
   private static volatile String lastGga;
   private static String lastSaid;
   private static volatile File logFile;
   private static List<NtripCore.Mount> mounts = CollectionsKt.emptyList();
   private static long mountsAtMs;
   private static String mountsHost = "";
   private static volatile NetworkCallback netCb;
   private static volatile Network netNow;
   private static volatile RtkStatus rtk;
   private static volatile long rtkAtMs;
   private static volatile Socket socket;
   private static volatile String source = "";
   private static volatile String state = "off";
   private static volatile Thread thread;
   private static volatile long wantedAtMs;

   // $VF: synthetic method
   public static void $r8$lambda$MRV5fM2qJ0uahQulkeW3m1w75UU(Function2 var0, List var1) {
      loadMounts$lambda$21$lambda$19$lambda$18(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$azkaAyk377Er5o7VwR_0BYfzpWU(Ntrip.Config var0, int var1, Ntrip.Config var2, boolean var3) {
      tick$lambda$6(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$bdV9IPYMKfvUo1d6k7IyVSpwAk8(long var0, Entry var2) {
      return skipBases$lambda$9$lambda$8(var0, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$dC1HhuaMY0h4xJLGvhLR2h5M9fA(JSONObject var0) {
      return stream$lambda$16$lambda$15(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$e9USblVOzq2slP6UPGKJNsDv7mA(Handler var0, String var1, int var2, String var3, String var4, Function2 var5) {
      loadMounts$lambda$21(var0, var1, var2, var3, var4, var5);
   }

   // $VF: synthetic method
   public static void $r8$lambda$qywpcJ0vojO0xCoJQVDyvs_wpLo/* $VF was: $r8$lambda$qywpcJ0vojO0xCoJQVDyvs_wpLo*/(Function2 var0, Exception var1) {
      loadMounts$lambda$21$lambda$20(var0, var1);
   }

   private Ntrip() {
   }

   private final NtripCore.Mount backupMount(Ntrip.Config var1) {
      RtkStatus var4 = rtk;
      if (var4 == null) {
         return null;
      } else if (!var4.getHasFix()) {
         return null;
      } else {
         long var2 = System.currentTimeMillis();
         if (mounts.isEmpty() || var2 - mountsAtMs > 1800000L || !Intrinsics.areEqual(mountsHost, var1.getHost())) {
            mountsAtMs = var2;
            mountsHost = var1.getHost();

            try {
               var7 = this.fetchMounts(var1.getHost(), var1.getPort(), var1.getUser(), var1.getPass());
            } catch (Exception var5) {
               String var6 = var5.getMessage();
               this.note("backup caster's list: " + var6);
               var7 = CollectionsKt.emptyList();
            }

            mounts = var7;
         }

         return NtripCore.nearestBase$default(NtripCore.INSTANCE, mounts, var4.getLat(), var4.getLon(), 0.0, this.skipBases(var2), 8, null);
      }
   }

   private final List<NtripCore.Mount> fetchMounts(String param1, int param2, String param3, String param4) {
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
      // 00: new java/net/Socket
      // 03: dup
      // 04: invokespecial java/net/Socket.<init> ()V
      // 07: checkcast java/io/Closeable
      // 0a: astore 9
      // 0c: aload 9
      // 0e: checkcast java/net/Socket
      // 11: astore 10
      // 13: new java/net/InetSocketAddress
      // 16: astore 12
      // 18: aload 1
      // 19: checkcast java/lang/CharSequence
      // 1c: invokestatic kotlin/text/StringsKt.trim (Ljava/lang/CharSequence;)Ljava/lang/CharSequence;
      // 1f: invokevirtual java/lang/Object.toString ()Ljava/lang/String;
      // 22: astore 11
      // 24: aload 12
      // 26: aload 11
      // 28: iload 2
      // 29: invokespecial java/net/InetSocketAddress.<init> (Ljava/lang/String;I)V
      // 2c: aload 10
      // 2e: aload 12
      // 30: checkcast java/net/SocketAddress
      // 33: sipush 10000
      // 36: invokevirtual java/net/Socket.connect (Ljava/net/SocketAddress;I)V
      // 39: aload 10
      // 3b: sipush 10000
      // 3e: invokevirtual java/net/Socket.setSoTimeout (I)V
      // 41: aload 10
      // 43: invokevirtual java/net/Socket.getOutputStream ()Ljava/io/OutputStream;
      // 46: astore 11
      // 48: getstatic com/starpilot/starview/NtripCore.INSTANCE Lcom/starpilot/starview/NtripCore;
      // 4b: ldc ""
      // 4d: aload 3
      // 4e: aload 4
      // 50: aload 1
      // 51: iload 2
      // 52: bipush 0
      // 53: aconst_null
      // 54: bipush 96
      // 56: aconst_null
      // 57: invokestatic com/starpilot/starview/NtripCore.request$default (Lcom/starpilot/starview/NtripCore;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IZLjava/lang/String;ILjava/lang/Object;)Ljava/lang/String;
      // 5a: getstatic kotlin/text/Charsets.UTF_8 Ljava/nio/charset/Charset;
      // 5d: invokevirtual java/lang/String.getBytes (Ljava/nio/charset/Charset;)[B
      // 60: astore 1
      // 61: aload 1
      // 62: ldc_w "getBytes(...)"
      // 65: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 68: aload 11
      // 6a: aload 1
      // 6b: invokevirtual java/io/OutputStream.write ([B)V
      // 6e: aload 10
      // 70: invokevirtual java/net/Socket.getInputStream ()Ljava/io/InputStream;
      // 73: astore 1
      // 74: aload 1
      // 75: ldc_w "getInputStream(...)"
      // 78: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 7b: aload 1
      // 7c: invokestatic kotlin/io/ByteStreamsKt.readBytes (Ljava/io/InputStream;)[B
      // 7f: astore 4
      // 81: getstatic kotlin/text/Charsets.ISO_8859_1 Ljava/nio/charset/Charset;
      // 84: astore 3
      // 85: new java/lang/String
      // 88: astore 1
      // 89: aload 1
      // 8a: aload 4
      // 8c: aload 3
      // 8d: invokespecial java/lang/String.<init> ([BLjava/nio/charset/Charset;)V
      // 90: getstatic com/starpilot/starview/Ntrip.rtk Lcom/starpilot/starview/RtkStatus;
      // 93: astore 3
      // 94: getstatic com/starpilot/starview/NtripCore.INSTANCE Lcom/starpilot/starview/NtripCore;
      // 97: astore 4
      // 99: ldc2_w NaN
      // 9c: dstore 7
      // 9e: aload 3
      // 9f: ifnull ab
      // a2: aload 3
      // a3: invokevirtual com/starpilot/starview/RtkStatus.getLat ()D
      // a6: dstore 5
      // a8: goto b0
      // ab: ldc2_w NaN
      // ae: dstore 5
      // b0: aload 3
      // b1: ifnull ba
      // b4: aload 3
      // b5: invokevirtual com/starpilot/starview/RtkStatus.getLon ()D
      // b8: dstore 7
      // ba: aload 4
      // bc: aload 1
      // bd: dload 5
      // bf: dload 7
      // c1: invokevirtual com/starpilot/starview/NtripCore.sourcetable (Ljava/lang/String;DD)Ljava/util/List;
      // c4: astore 1
      // c5: aload 9
      // c7: aconst_null
      // c8: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // cb: aload 1
      // cc: areturn
      // cd: astore 1
      // ce: goto d2
      // d1: astore 1
      // d2: aload 1
      // d3: athrow
      // d4: astore 3
      // d5: aload 9
      // d7: aload 1
      // d8: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // db: aload 3
      // dc: athrow
   }

   private static final void loadMounts$lambda$21(Handler param0, String param1, int param2, String param3, String param4, Function2 param5) {
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
      // 000: new java/net/Socket
      // 003: astore 10
      // 005: aload 10
      // 007: invokespecial java/net/Socket.<init> ()V
      // 00a: aload 10
      // 00c: checkcast java/io/Closeable
      // 00f: astore 10
      // 011: aload 10
      // 013: checkcast java/net/Socket
      // 016: astore 11
      // 018: new java/net/InetSocketAddress
      // 01b: astore 12
      // 01d: aload 12
      // 01f: aload 1
      // 020: checkcast java/lang/CharSequence
      // 023: invokestatic kotlin/text/StringsKt.trim (Ljava/lang/CharSequence;)Ljava/lang/CharSequence;
      // 026: invokevirtual java/lang/Object.toString ()Ljava/lang/String;
      // 029: iload 2
      // 02a: invokespecial java/net/InetSocketAddress.<init> (Ljava/lang/String;I)V
      // 02d: aload 11
      // 02f: aload 12
      // 031: checkcast java/net/SocketAddress
      // 034: sipush 10000
      // 037: invokevirtual java/net/Socket.connect (Ljava/net/SocketAddress;I)V
      // 03a: aload 11
      // 03c: sipush 10000
      // 03f: invokevirtual java/net/Socket.setSoTimeout (I)V
      // 042: aload 11
      // 044: invokevirtual java/net/Socket.getOutputStream ()Ljava/io/OutputStream;
      // 047: astore 12
      // 049: getstatic com/starpilot/starview/NtripCore.INSTANCE Lcom/starpilot/starview/NtripCore;
      // 04c: ldc ""
      // 04e: aload 3
      // 04f: aload 4
      // 051: aload 1
      // 052: iload 2
      // 053: bipush 0
      // 054: aconst_null
      // 055: bipush 96
      // 057: aconst_null
      // 058: invokestatic com/starpilot/starview/NtripCore.request$default (Lcom/starpilot/starview/NtripCore;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IZLjava/lang/String;ILjava/lang/Object;)Ljava/lang/String;
      // 05b: getstatic kotlin/text/Charsets.UTF_8 Ljava/nio/charset/Charset;
      // 05e: invokevirtual java/lang/String.getBytes (Ljava/nio/charset/Charset;)[B
      // 061: astore 1
      // 062: aload 1
      // 063: ldc_w "getBytes(...)"
      // 066: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 069: aload 12
      // 06b: aload 1
      // 06c: invokevirtual java/io/OutputStream.write ([B)V
      // 06f: aload 11
      // 071: invokevirtual java/net/Socket.getInputStream ()Ljava/io/InputStream;
      // 074: astore 1
      // 075: aload 1
      // 076: ldc_w "getInputStream(...)"
      // 079: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 07c: aload 1
      // 07d: invokestatic kotlin/io/ByteStreamsKt.readBytes (Ljava/io/InputStream;)[B
      // 080: astore 3
      // 081: getstatic kotlin/text/Charsets.ISO_8859_1 Ljava/nio/charset/Charset;
      // 084: astore 1
      // 085: new java/lang/String
      // 088: astore 4
      // 08a: aload 4
      // 08c: aload 3
      // 08d: aload 1
      // 08e: invokespecial java/lang/String.<init> ([BLjava/nio/charset/Charset;)V
      // 091: aload 4
      // 093: checkcast java/lang/CharSequence
      // 096: invokestatic kotlin/text/StringsKt.lineSequence (Ljava/lang/CharSequence;)Lkotlin/sequences/Sequence;
      // 099: invokestatic kotlin/sequences/SequencesKt.firstOrNull (Lkotlin/sequences/Sequence;)Ljava/lang/Object;
      // 09c: checkcast java/lang/String
      // 09f: astore 3
      // 0a0: aload 3
      // 0a1: astore 1
      // 0a2: aload 3
      // 0a3: ifnonnull 0a9
      // 0a6: ldc ""
      // 0a8: astore 1
      // 0a9: getstatic com/starpilot/starview/NtripCore.INSTANCE Lcom/starpilot/starview/NtripCore;
      // 0ac: aload 1
      // 0ad: invokevirtual com/starpilot/starview/NtripCore.answer (Ljava/lang/String;)Lcom/starpilot/starview/NtripCore$Answer;
      // 0b0: instanceof com/starpilot/starview/NtripCore$Answer$Refused
      // 0b3: ifeq 0ed
      // 0b6: aload 4
      // 0b8: checkcast java/lang/CharSequence
      // 0bb: ldc_w "STR;"
      // 0be: checkcast java/lang/CharSequence
      // 0c1: bipush 0
      // 0c2: bipush 2
      // 0c3: aconst_null
      // 0c4: invokestatic kotlin/text/StringsKt.contains$default (Ljava/lang/CharSequence;Ljava/lang/CharSequence;ZILjava/lang/Object;)Z
      // 0c7: ifeq 0cd
      // 0ca: goto 0ed
      // 0cd: new java/lang/IllegalStateException
      // 0d0: astore 3
      // 0d1: getstatic com/starpilot/starview/NtripCore.INSTANCE Lcom/starpilot/starview/NtripCore;
      // 0d4: aload 1
      // 0d5: invokevirtual com/starpilot/starview/NtripCore.answer (Ljava/lang/String;)Lcom/starpilot/starview/NtripCore$Answer;
      // 0d8: astore 1
      // 0d9: aload 1
      // 0da: ldc_w "null cannot be cast to non-null type com.starpilot.starview.NtripCore.Answer.Refused"
      // 0dd: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNull (Ljava/lang/Object;Ljava/lang/String;)V
      // 0e0: aload 3
      // 0e1: aload 1
      // 0e2: checkcast com/starpilot/starview/NtripCore$Answer$Refused
      // 0e5: invokevirtual com/starpilot/starview/NtripCore$Answer$Refused.getWhy ()Ljava/lang/String;
      // 0e8: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
      // 0eb: aload 3
      // 0ec: athrow
      // 0ed: getstatic com/starpilot/starview/Ntrip.rtk Lcom/starpilot/starview/RtkStatus;
      // 0f0: astore 1
      // 0f1: getstatic com/starpilot/starview/NtripCore.INSTANCE Lcom/starpilot/starview/NtripCore;
      // 0f4: astore 3
      // 0f5: ldc2_w NaN
      // 0f8: dstore 8
      // 0fa: aload 1
      // 0fb: ifnull 107
      // 0fe: aload 1
      // 0ff: invokevirtual com/starpilot/starview/RtkStatus.getLat ()D
      // 102: dstore 6
      // 104: goto 10c
      // 107: ldc2_w NaN
      // 10a: dstore 6
      // 10c: aload 1
      // 10d: ifnull 116
      // 110: aload 1
      // 111: invokevirtual com/starpilot/starview/RtkStatus.getLon ()D
      // 114: dstore 8
      // 116: aload 3
      // 117: aload 4
      // 119: dload 6
      // 11b: dload 8
      // 11d: invokevirtual com/starpilot/starview/NtripCore.sourcetable (Ljava/lang/String;DD)Ljava/util/List;
      // 120: astore 1
      // 121: new com/starpilot/starview/Ntrip$$ExternalSyntheticLambda2
      // 124: astore 3
      // 125: aload 3
      // 126: aload 5
      // 128: aload 1
      // 129: invokespecial com/starpilot/starview/Ntrip$$ExternalSyntheticLambda2.<init> (Lkotlin/jvm/functions/Function2;Ljava/util/List;)V
      // 12c: aload 0
      // 12d: aload 3
      // 12e: invokevirtual android/os/Handler.post (Ljava/lang/Runnable;)Z
      // 131: pop
      // 132: aload 10
      // 134: aconst_null
      // 135: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 138: goto 157
      // 13b: astore 1
      // 13c: aload 1
      // 13d: athrow
      // 13e: astore 3
      // 13f: aload 10
      // 141: aload 1
      // 142: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 145: aload 3
      // 146: athrow
      // 147: astore 1
      // 148: aload 0
      // 149: new com/starpilot/starview/Ntrip$$ExternalSyntheticLambda3
      // 14c: dup
      // 14d: aload 5
      // 14f: aload 1
      // 150: invokespecial com/starpilot/starview/Ntrip$$ExternalSyntheticLambda3.<init> (Lkotlin/jvm/functions/Function2;Ljava/lang/Exception;)V
      // 153: invokevirtual android/os/Handler.post (Ljava/lang/Runnable;)Z
      // 156: pop
      // 157: return
   }

   private static final void loadMounts$lambda$21$lambda$19$lambda$18(Function2 var0, List var1) {
      var0.invoke(var1, null);
   }

   private static final void loadMounts$lambda$21$lambda$20(Function2 var0, Exception var1) {
      String var3 = var1.getMessage();
      String var2 = var3;
      if (var3 == null) {
         var2 = var1.getClass().getSimpleName();
      }

      var0.invoke(null, var2);
   }

   private final void note(String param1) {
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
      // 00: new java/text/SimpleDateFormat
      // 03: dup
      // 04: ldc_w "HH:mm:ss"
      // 07: getstatic java/util/Locale.US Ljava/util/Locale;
      // 0a: invokespecial java/text/SimpleDateFormat.<init> (Ljava/lang/String;Ljava/util/Locale;)V
      // 0d: new java/util/Date
      // 10: dup
      // 11: invokespecial java/util/Date.<init> ()V
      // 14: invokevirtual java/text/SimpleDateFormat.format (Ljava/util/Date;)Ljava/lang/String;
      // 17: astore 2
      // 18: new java/lang/StringBuilder
      // 1b: dup
      // 1c: invokespecial java/lang/StringBuilder.<init> ()V
      // 1f: aload 2
      // 20: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 23: ldc_w "  "
      // 26: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 29: aload 1
      // 2a: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 2d: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 30: astore 1
      // 31: getstatic com/starpilot/starview/Ntrip.history Lkotlin/collections/ArrayDeque;
      // 34: astore 2
      // 35: aload 2
      // 36: monitorenter
      // 37: getstatic com/starpilot/starview/Ntrip.history Lkotlin/collections/ArrayDeque;
      // 3a: aload 1
      // 3b: invokevirtual kotlin/collections/ArrayDeque.addFirst (Ljava/lang/Object;)V
      // 3e: getstatic com/starpilot/starview/Ntrip.history Lkotlin/collections/ArrayDeque;
      // 41: invokevirtual kotlin/collections/ArrayDeque.size ()I
      // 44: bipush 40
      // 46: if_icmple 53
      // 49: getstatic com/starpilot/starview/Ntrip.history Lkotlin/collections/ArrayDeque;
      // 4c: invokevirtual kotlin/collections/ArrayDeque.removeLast ()Ljava/lang/Object;
      // 4f: pop
      // 50: goto 3e
      // 53: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 56: astore 3
      // 57: aload 2
      // 58: monitorexit
      // 59: getstatic com/starpilot/starview/Ntrip.logFile Ljava/io/File;
      // 5c: astore 2
      // 5d: aload 2
      // 5e: ifnull f1
      // 61: new java/text/SimpleDateFormat
      // 64: astore 4
      // 66: aload 4
      // 68: ldc_w "yyyy-MM-dd "
      // 6b: getstatic java/util/Locale.US Ljava/util/Locale;
      // 6e: invokespecial java/text/SimpleDateFormat.<init> (Ljava/lang/String;Ljava/util/Locale;)V
      // 71: new java/util/Date
      // 74: astore 3
      // 75: aload 3
      // 76: invokespecial java/util/Date.<init> ()V
      // 79: aload 4
      // 7b: aload 3
      // 7c: invokevirtual java/text/SimpleDateFormat.format (Ljava/util/Date;)Ljava/lang/String;
      // 7f: astore 4
      // 81: new java/lang/StringBuilder
      // 84: astore 3
      // 85: aload 3
      // 86: invokespecial java/lang/StringBuilder.<init> ()V
      // 89: aload 2
      // 8a: aload 3
      // 8b: aload 4
      // 8d: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 90: aload 1
      // 91: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 94: ldc_w "\n"
      // 97: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 9a: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 9d: aconst_null
      // 9e: bipush 2
      // 9f: aconst_null
      // a0: invokestatic kotlin/io/FilesKt.appendText$default (Ljava/io/File;Ljava/lang/String;Ljava/nio/charset/Charset;ILjava/lang/Object;)V
      // a3: aload 2
      // a4: invokevirtual java/io/File.length ()J
      // a7: ldc2_w 60000
      // aa: lcmp
      // ab: ifle f1
      // ae: aload 2
      // af: aconst_null
      // b0: bipush 1
      // b1: aconst_null
      // b2: invokestatic kotlin/io/FilesKt.readLines$default (Ljava/io/File;Ljava/nio/charset/Charset;ILjava/lang/Object;)Ljava/util/List;
      // b5: sipush 300
      // b8: invokestatic kotlin/collections/CollectionsKt.takeLast (Ljava/util/List;I)Ljava/util/List;
      // bb: checkcast java/lang/Iterable
      // be: ldc_w "\n"
      // c1: checkcast java/lang/CharSequence
      // c4: aconst_null
      // c5: aconst_null
      // c6: bipush 0
      // c7: aconst_null
      // c8: aconst_null
      // c9: bipush 62
      // cb: aconst_null
      // cc: invokestatic kotlin/collections/CollectionsKt.joinToString$default (Ljava/lang/Iterable;Ljava/lang/CharSequence;Ljava/lang/CharSequence;Ljava/lang/CharSequence;ILjava/lang/CharSequence;Lkotlin/jvm/functions/Function1;ILjava/lang/Object;)Ljava/lang/String;
      // cf: astore 1
      // d0: new java/lang/StringBuilder
      // d3: astore 3
      // d4: aload 3
      // d5: invokespecial java/lang/StringBuilder.<init> ()V
      // d8: aload 2
      // d9: aload 3
      // da: aload 1
      // db: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // de: ldc_w "\n"
      // e1: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // e4: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // e7: aconst_null
      // e8: bipush 2
      // e9: aconst_null
      // ea: invokestatic kotlin/io/FilesKt.writeText$default (Ljava/io/File;Ljava/lang/String;Ljava/nio/charset/Charset;ILjava/lang/Object;)V
      // ed: goto f1
      // f0: astore 1
      // f1: return
      // f2: astore 1
      // f3: aload 2
      // f4: monitorexit
      // f5: aload 1
      // f6: athrow
   }

   private final String readLine(InputStream var1) {
      ByteArrayOutputStream var3 = new ByteArrayOutputStream();

      while (var3.size() < 1024) {
         int var2 = var1.read();
         if (var2 < 0 || var2 == 10) {
            break;
         }

         if (var2 != 13) {
            var3.write(var2);
         }
      }

      String var4 = var3.toString("ISO-8859-1");
      Intrinsics.checkNotNullExpressionValue(var4, "toString(...)");
      return var4;
   }

   private final String readUpTo(InputStream var1, int var2) {
      ByteArrayOutputStream var4 = new ByteArrayOutputStream();
      byte[] var5 = new byte[512];

      while (var4.size() < var2) {
         int var3;
         try {
            var3 = var1.read(var5);
         } catch (SocketTimeoutException var7) {
            var3 = -1;
         }

         if (var3 <= 0) {
            break;
         }

         var4.write(var5, 0, var3);
      }

      String var8 = var4.toString("ISO-8859-1");
      Intrinsics.checkNotNullExpressionValue(var8, "toString(...)");
      return var8;
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   private final void reopen() {
      Socket var1;
      try {
         var1 = socket;
      } catch (Exception var3) {
         return;
      }

      if (var1 != null) {
         try {
            var1.close();
         } catch (Exception var2) {
         }
      }
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   private final void restart() {
      generation++;

      Socket var1;
      try {
         var1 = socket;
      } catch (Exception var3) {
         return;
      }

      if (var1 != null) {
         try {
            var1.close();
         } catch (Exception var2) {
         }
      }
   }

   private final void run(Ntrip.Config var1, int var2, Ntrip.Config var3, boolean var4) {
      Ntrip var20 = this;
      int var5 = var2;
      NtripCore.BackupPlan var21 = null;
      if (var4) {
         var21 = new NtripCore.BackupPlan(0L, 2305843009213693951L, 1, null);
      } else {
         var21 = new NtripCore.BackupPlan(0L, 0L, 3, null);
      }

      Ntrip.Role var22 = new Ntrip.Role(var3, var21);
      if (var4) {
         var22.getPlan().toBackup(System.currentTimeMillis());
         String var46;
         if (var3 != null) {
            var46 = var3.getHost();
         } else {
            var46 = null;
         }

         this.note("using only the backup caster " + var46);
      }

      boolean var18 = true;
      int var7 = 0;
      long var10 = 5000L;
      int var9 = 0;

      while (var5 == generation) {
         NtripCore.BackupPlan var25 = var22.getPlan();
         if (var3 != null && var25.timeToRetryMain(System.currentTimeMillis())) {
            var25.toMain(System.currentTimeMillis());
            String var47 = var1.getHost();
            var20.note("trying " + var47 + " again");
         }

         if (var3 != null && var25.shouldSwitch(System.currentTimeMillis())) {
            var25.toBackup(System.currentTimeMillis());
            String var23 = var1.getHost();
            String var48 = var3.getHost();
            var20.note(var23 + " gave no RTK for 3 minutes - using the backup caster " + var48);
         }

         int var6;
         Ntrip var61;
         label258: {
            Ntrip.Config var57;
            if (var3 != null && var25.getOnBackup()) {
               NtripCore.Mount var49;
               if (StringsKt.isBlank(var3.getMount())) {
                  var49 = var20.backupMount(var3);
               } else {
                  var49 = null;
               }

               if (StringsKt.isBlank(var3.getMount()) && var49 == null) {
                  String var54;
                  if (var4) {
                     var54 = "";
                  } else {
                     String var55 = var1.getHost();
                     var54 = " - trying " + var55 + " again soon";
                  }

                  var20.setState("backup: no base within 25 miles (or no fix yet)" + var54);
                  source = "";

                  try {
                     Thread.sleep(60000L);
                  } catch (InterruptedException var27) {
                     break;
                  }

                  if (var25.timeToRetryMain(System.currentTimeMillis())) {
                     var25.toMain(System.currentTimeMillis());
                     String var56 = var1.getHost();
                     var20.note("trying " + var56 + " again");
                  }

                  var61 = var20;
                  var6 = var5;
                  break label258;
               }

               String var24;
               label230: {
                  var24 = var3.getHost();
                  var5 = var3.getPort();
                  if (var49 != null) {
                     String var58 = var49.getName();
                     var42 = var58;
                     if (var58 != null) {
                        break label230;
                     }
                  }

                  var42 = var3.getMount();
               }

               Ntrip.Config var64 = new Ntrip.Config(true, var24, var5, var42, var3.getUser(), var3.getPass());
               RtkStatus var43 = rtk;
               String var26 = var3.getHost();
               String var59 = var64.getMount();
               String var44;
               if (var49 != null && var43 != null && var43.getHasFix()) {
                  long var12 = Math.round(var49.milesFrom(var43.getLat(), var43.getLon()));
                  var44 = " · " + var12 + " mi";
               } else {
                  var44 = "";
               }

               source = "backup: " + var26 + " · " + var59 + var44;
               var57 = var64;
               if (var49 != null) {
                  var22.setBase(var49);
                  var57 = var64;
               }
            } else {
               String var41;
               if (var3 != null) {
                  String var40 = var1.getHost();
                  var41 = "main: " + var40;
               } else {
                  var41 = "";
               }

               source = var41;
               var57 = var1;
            }

            String var50 = var57.getHost();
            String var45;
            if (var18) {
               var45 = "2.0";
            } else {
               var45 = "1.0";
            }

            String var51 = "connecting to " + var50 + " (NTRIP " + var45 + ")…";
            var20 = this;
            this.setState(var51);
            long var14 = System.currentTimeMillis();
            long var34 = bytesIn;
            var5 = var2;

            label218: {
               try {
                  var20.stream(var57, var5, var18, var22);
               } catch (Exception var29) {
                  String var60 = var29.getMessage();
                  var52 = var60;
                  if (var60 == null) {
                     var52 = var29.getClass().getSimpleName();
                  }
                  break label218;
               }

               var52 = null;
            }

            long var16 = bytesIn - var34;
            if (var3 != null) {
               var34 = var22.getLastHelpMs();
               if (!var25.getOnBackup()) {
                  boolean var19;
                  if (var34 >= var14 && System.currentTimeMillis() - var34 < 30000L) {
                     var19 = true;
                  } else {
                     var19 = false;
                  }

                  if (var34 < var14) {
                     var34 = var14;
                  }

                  var25.mainResult(var19, var34);
               } else if (Intrinsics.areEqual(var52, "time to try the main caster again")) {
                  var25.toMain(System.currentTimeMillis());
                  String var53 = var1.getHost();
                  this.note("trying " + var53 + " again");
                  var61 = this;
                  var6 = var2;
                  break label258;
               }

               if (Intrinsics.areEqual(var52, "a nearer base")) {
                  this.note("moving to a nearer base");
                  var61 = this;
                  var6 = var2;
                  break label258;
               }

               var61 = this;
               var6 = var2;
               if (var25.shouldSwitch(System.currentTimeMillis())) {
                  break label258;
               }
            }

            boolean var39 = var18;
            if (var16 == 0L) {
               var39 = var18;
               if (var52 != null) {
                  var39 = var18;
                  if (!StringsKt.startsWith$default(var52, "login refused", false, 2, null)) {
                     var39 = var18;
                     if (!StringsKt.startsWith$default(var52, "account not allowed", false, 2, null)) {
                        if (!var18) {
                           var18 = true;
                        } else {
                           var18 = false;
                        }

                        var39 = var18;
                     }
                  }
               }
            }

            if (var16 > 0L) {
               var34 = (System.currentTimeMillis() - var14) / 1000;
               String var62 = NtripCore.INSTANCE.size(var16);
               this.note("connection ended after " + var34 + " s, " + var62 + " of corrections");
            }

            if (var2 != generation) {
               break;
            }

            String var63;
            if (var52 == null) {
               var63 = "caster closed the stream";
            } else {
               var63 = var52;
            }

            this.setState("reconnecting: " + var63);
            Log.w("StarView/Ntrip", state);
            boolean var8;
            if (var52 != null && StringsKt.startsWith$default(var52, "caster busy", false, 2, null)) {
               var8 = true;
            } else {
               var8 = false;
            }

            if (var8) {
               var6 = var9 + 1;
            } else {
               var6 = 0;
            }

            var9 = var6;
            var34 = System.currentTimeMillis();
            boolean var32;
            if (var52 != null && StringsKt.startsWith$default(var52, "mountpoint", false, 2, null)) {
               var32 = 1;
            } else {
               var32 = 0;
            }

            if (var32) {
               var32 = var7 + 1;
            } else {
               var32 = 0;
            }

            if (var32 > 10) {
               var10 = 600000L;
            } else if ((var52 == null || var16 > 0L) && var34 - var14 >= 30000L) {
               var10 = 3000L;
            } else if (var8) {
               if (var6 <= 15) {
                  var10 = 20000L;
               } else {
                  var10 = 60000L;
               }
            } else {
               var10 = RangesKt.coerceIn(var10 * 2, 10000L, 60000L);
            }

            try {
               Thread.sleep(var10);
            } catch (InterruptedException var28) {
               break;
            }

            var7 = var32;
            var18 = var39;
            continue;
         }

         var20 = var61;
         var5 = var6;
      }

      if (var5 == generation) {
         var20.setState("off");
      }

      thread = null;
   }

   // $VF: synthetic method
   static void run$default(Ntrip var0, Ntrip.Config var1, int var2, Ntrip.Config var3, boolean var4, int var5, Object var6) {
      if ((var5 & 4) != 0) {
         var3 = null;
      }

      if ((var5 & 8) != 0) {
         var4 = false;
      }

      var0.run(var1, var2, var3, var4);
   }

   private final void setState(String var1) {
      if (!Intrinsics.areEqual(var1, state) && !StringsKt.startsWith$default(var1, "receiving corrections", false, 2, null)) {
         this.note(var1);
      }

      state = var1;
   }

   private final Set<String> skipBases(long var1) {
      HashMap var3 = badBases;
      Set var10;
      synchronized (var3) {
         var10 = badBases.entrySet();
         Intrinsics.checkNotNullExpressionValue(var10, "<get-entries>(...)");
         Iterable var5 = var10;
         Ntrip$$ExternalSyntheticLambda0 var8 = new Ntrip$$ExternalSyntheticLambda0(var1);
         CollectionsKt.removeAll(var5, var8);
         var10 = badBases.keySet();
         Intrinsics.checkNotNullExpressionValue(var10, "<get-keys>(...)");
         var10 = CollectionsKt.toSet(var10);
      }

      return var10;
   }

   private static final boolean skipBases$lambda$9$lambda$8(long var0, Entry var2) {
      Intrinsics.checkNotNullParameter(var2, "it");
      boolean var3;
      if (((Number)var2.getValue()).longValue() < var0) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   private final void stream(Ntrip.Config param1, int param2, boolean param3, Ntrip.Role param4) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: parsing failure!
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 000: invokestatic java/lang/System.currentTimeMillis ()J
      // 003: lstore 10
      // 005: new java/net/Socket
      // 008: dup
      // 009: invokespecial java/net/Socket.<init> ()V
      // 00c: checkcast java/io/Closeable
      // 00f: astore 38
      // 011: aload 38
      // 013: checkcast java/net/Socket
      // 016: astore 32
      // 018: bipush 0
      // 019: istore 28
      // 01b: aload 32
      // 01d: putstatic com/starpilot/starview/Ntrip.socket Ljava/net/Socket;
      // 020: aload 32
      // 022: bipush 1
      // 023: invokevirtual java/net/Socket.setKeepAlive (Z)V
      // 026: aload 32
      // 028: bipush 1
      // 029: invokevirtual java/net/Socket.setTcpNoDelay (Z)V
      // 02c: new java/net/InetSocketAddress
      // 02f: astore 30
      // 031: aload 30
      // 033: aload 1
      // 034: invokevirtual com/starpilot/starview/Ntrip$Config.getHost ()Ljava/lang/String;
      // 037: aload 1
      // 038: invokevirtual com/starpilot/starview/Ntrip$Config.getPort ()I
      // 03b: invokespecial java/net/InetSocketAddress.<init> (Ljava/lang/String;I)V
      // 03e: aload 32
      // 040: aload 30
      // 042: checkcast java/net/SocketAddress
      // 045: sipush 10000
      // 048: invokevirtual java/net/Socket.connect (Ljava/net/SocketAddress;I)V
      // 04b: aload 32
      // 04d: sipush 15000
      // 050: invokevirtual java/net/Socket.setSoTimeout (I)V
      // 053: aload 32
      // 055: invokevirtual java/net/Socket.getOutputStream ()Ljava/io/OutputStream;
      // 058: astore 35
      // 05a: aload 32
      // 05c: invokevirtual java/net/Socket.getInputStream ()Ljava/io/InputStream;
      // 05f: astore 34
      // 061: getstatic com/starpilot/starview/Ntrip.rtk Lcom/starpilot/starview/RtkStatus;
      // 064: astore 30
      // 066: aload 30
      // 068: ifnull 088
      // 06b: getstatic com/starpilot/starview/NtripCore.INSTANCE Lcom/starpilot/starview/NtripCore;
      // 06e: aload 30
      // 070: invokestatic java/lang/System.currentTimeMillis ()J
      // 073: invokevirtual com/starpilot/starview/NtripCore.gga (Lcom/starpilot/starview/RtkStatus;J)Ljava/lang/String;
      // 076: astore 31
      // 078: aload 31
      // 07a: astore 30
      // 07c: aload 31
      // 07e: ifnonnull 08d
      // 081: goto 088
      // 084: astore 1
      // 085: goto b9f
      // 088: getstatic com/starpilot/starview/Ntrip.lastGga Ljava/lang/String;
      // 08b: astore 30
      // 08d: getstatic com/starpilot/starview/NtripCore.INSTANCE Lcom/starpilot/starview/NtripCore;
      // 090: aload 1
      // 091: invokevirtual com/starpilot/starview/Ntrip$Config.getMount ()Ljava/lang/String;
      // 094: aload 1
      // 095: invokevirtual com/starpilot/starview/Ntrip$Config.getUser ()Ljava/lang/String;
      // 098: aload 1
      // 099: invokevirtual com/starpilot/starview/Ntrip$Config.getPass ()Ljava/lang/String;
      // 09c: aload 1
      // 09d: invokevirtual com/starpilot/starview/Ntrip$Config.getHost ()Ljava/lang/String;
      // 0a0: aload 1
      // 0a1: invokevirtual com/starpilot/starview/Ntrip$Config.getPort ()I
      // 0a4: iload 3
      // 0a5: aload 30
      // 0a7: invokevirtual com/starpilot/starview/NtripCore.request (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IZLjava/lang/String;)Ljava/lang/String;
      // 0aa: getstatic kotlin/text/Charsets.UTF_8 Ljava/nio/charset/Charset;
      // 0ad: invokevirtual java/lang/String.getBytes (Ljava/nio/charset/Charset;)[B
      // 0b0: astore 30
      // 0b2: aload 30
      // 0b4: ldc_w "getBytes(...)"
      // 0b7: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 0ba: aload 35
      // 0bc: aload 30
      // 0be: invokevirtual java/io/OutputStream.write ([B)V
      // 0c1: aload 35
      // 0c3: invokevirtual java/io/OutputStream.flush ()V
      // 0c6: getstatic com/starpilot/starview/Ntrip.INSTANCE Lcom/starpilot/starview/Ntrip;
      // 0c9: astore 30
      // 0cb: aload 34
      // 0cd: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNull (Ljava/lang/Object;)V
      // 0d0: aload 30
      // 0d2: aload 34
      // 0d4: invokespecial com/starpilot/starview/Ntrip.readLine (Ljava/io/InputStream;)Ljava/lang/String;
      // 0d7: astore 37
      // 0d9: getstatic com/starpilot/starview/NtripCore.INSTANCE Lcom/starpilot/starview/NtripCore;
      // 0dc: aload 37
      // 0de: invokevirtual com/starpilot/starview/NtripCore.answer (Ljava/lang/String;)Lcom/starpilot/starview/NtripCore$Answer;
      // 0e1: astore 30
      // 0e3: aload 30
      // 0e5: instanceof com/starpilot/starview/NtripCore$Answer$Refused
      // 0e8: istore 29
      // 0ea: iload 29
      // 0ec: ifeq 15a
      // 0ef: aload 32
      // 0f1: sipush 2000
      // 0f4: invokevirtual java/net/Socket.setSoTimeout (I)V
      // 0f7: getstatic com/starpilot/starview/Ntrip.INSTANCE Lcom/starpilot/starview/Ntrip;
      // 0fa: aload 34
      // 0fc: sipush 2048
      // 0ff: invokespecial com/starpilot/starview/Ntrip.readUpTo (Ljava/io/InputStream;I)Ljava/lang/String;
      // 102: astore 1
      // 103: goto 10a
      // 106: astore 1
      // 107: ldc ""
      // 109: astore 1
      // 10a: getstatic com/starpilot/starview/NtripCore.INSTANCE Lcom/starpilot/starview/NtripCore;
      // 10d: aload 37
      // 10f: aload 1
      // 110: invokevirtual com/starpilot/starview/NtripCore.said (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
      // 113: astore 1
      // 114: aload 1
      // 115: getstatic com/starpilot/starview/Ntrip.lastSaid Ljava/lang/String;
      // 118: invokestatic kotlin/jvm/internal/Intrinsics.areEqual (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 11b: ifne 148
      // 11e: aload 1
      // 11f: putstatic com/starpilot/starview/Ntrip.lastSaid Ljava/lang/String;
      // 122: getstatic com/starpilot/starview/Ntrip.INSTANCE Lcom/starpilot/starview/Ntrip;
      // 125: astore 4
      // 127: new java/lang/StringBuilder
      // 12a: astore 31
      // 12c: aload 31
      // 12e: invokespecial java/lang/StringBuilder.<init> ()V
      // 131: aload 4
      // 133: aload 31
      // 135: ldc_w "caster said "
      // 138: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 13b: aload 1
      // 13c: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 13f: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 142: invokespecial com/starpilot/starview/Ntrip.note (Ljava/lang/String;)V
      // 145: goto 148
      // 148: new java/lang/IllegalStateException
      // 14b: astore 1
      // 14c: aload 1
      // 14d: aload 30
      // 14f: checkcast com/starpilot/starview/NtripCore$Answer$Refused
      // 152: invokevirtual com/starpilot/starview/NtripCore$Answer$Refused.getWhy ()Ljava/lang/String;
      // 155: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
      // 158: aload 1
      // 159: athrow
      // 15a: aload 30
      // 15c: getstatic com/starpilot/starview/NtripCore$Answer$Sourcetable.INSTANCE Lcom/starpilot/starview/NtripCore$Answer$Sourcetable;
      // 15f: invokestatic kotlin/jvm/internal/Intrinsics.areEqual (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 162: ifne b52
      // 165: aload 30
      // 167: getstatic com/starpilot/starview/NtripCore$Answer$Stream.INSTANCE Lcom/starpilot/starview/NtripCore$Answer$Stream;
      // 16a: invokestatic kotlin/jvm/internal/Intrinsics.areEqual (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 16d: ifeq b3c
      // 170: aconst_null
      // 171: astore 30
      // 173: aload 37
      // 175: checkcast java/lang/CharSequence
      // 178: invokestatic kotlin/text/StringsKt.trim (Ljava/lang/CharSequence;)Ljava/lang/CharSequence;
      // 17b: invokevirtual java/lang/Object.toString ()Ljava/lang/String;
      // 17e: astore 31
      // 180: lload 10
      // 182: lstore 14
      // 184: lload 10
      // 186: lstore 24
      // 188: lload 14
      // 18a: lstore 24
      // 18c: aload 31
      // 18e: ldc_w "HTTP/"
      // 191: bipush 0
      // 192: bipush 2
      // 193: aconst_null
      // 194: invokestatic kotlin/text/StringsKt.startsWith$default (Ljava/lang/String;Ljava/lang/String;ZILjava/lang/Object;)Z
      // 197: istore 29
      // 199: iload 29
      // 19b: ifeq 2c0
      // 19e: bipush 0
      // 19f: istore 7
      // 1a1: iload 7
      // 1a3: bipush 40
      // 1a5: if_icmpge 2b9
      // 1a8: lload 10
      // 1aa: lstore 26
      // 1ac: lload 14
      // 1ae: lstore 26
      // 1b0: getstatic com/starpilot/starview/Ntrip.INSTANCE Lcom/starpilot/starview/Ntrip;
      // 1b3: aload 34
      // 1b5: invokespecial com/starpilot/starview/Ntrip.readLine (Ljava/io/InputStream;)Ljava/lang/String;
      // 1b8: astore 33
      // 1ba: lload 10
      // 1bc: lstore 26
      // 1be: lload 14
      // 1c0: lstore 26
      // 1c2: aload 33
      // 1c4: checkcast java/lang/CharSequence
      // 1c7: invokestatic kotlin/text/StringsKt.isBlank (Ljava/lang/CharSequence;)Z
      // 1ca: ifne 2b9
      // 1cd: aload 30
      // 1cf: astore 31
      // 1d1: lload 10
      // 1d3: lstore 26
      // 1d5: lload 14
      // 1d7: lstore 26
      // 1d9: aload 33
      // 1db: ldc_w "Transfer-Encoding:"
      // 1de: bipush 1
      // 1df: invokestatic kotlin/text/StringsKt.startsWith (Ljava/lang/String;Ljava/lang/String;Z)Z
      // 1e2: ifeq 21d
      // 1e5: aload 30
      // 1e7: astore 31
      // 1e9: lload 10
      // 1eb: lstore 26
      // 1ed: lload 14
      // 1ef: lstore 26
      // 1f1: aload 33
      // 1f3: checkcast java/lang/CharSequence
      // 1f6: ldc_w "chunked"
      // 1f9: checkcast java/lang/CharSequence
      // 1fc: bipush 1
      // 1fd: invokestatic kotlin/text/StringsKt.contains (Ljava/lang/CharSequence;Ljava/lang/CharSequence;Z)Z
      // 200: ifeq 21d
      // 203: lload 10
      // 205: lstore 26
      // 207: lload 14
      // 209: lstore 26
      // 20b: new com/starpilot/starview/NtripCore$Dechunker
      // 20e: astore 31
      // 210: lload 10
      // 212: lstore 26
      // 214: lload 14
      // 216: lstore 26
      // 218: aload 31
      // 21a: invokespecial com/starpilot/starview/NtripCore$Dechunker.<init> ()V
      // 21d: lload 10
      // 21f: lstore 26
      // 221: lload 14
      // 223: lstore 26
      // 225: aload 33
      // 227: ldc_w "Content-Type:"
      // 22a: bipush 1
      // 22b: invokestatic kotlin/text/StringsKt.startsWith (Ljava/lang/String;Ljava/lang/String;Z)Z
      // 22e: ifeq 2af
      // 231: lload 10
      // 233: lstore 26
      // 235: lload 14
      // 237: lstore 26
      // 239: aload 33
      // 23b: checkcast java/lang/CharSequence
      // 23e: ldc_w "sourcetable"
      // 241: checkcast java/lang/CharSequence
      // 244: bipush 1
      // 245: invokestatic kotlin/text/StringsKt.contains (Ljava/lang/CharSequence;Ljava/lang/CharSequence;Z)Z
      // 248: ifne 24e
      // 24b: goto 2af
      // 24e: lload 10
      // 250: lstore 26
      // 252: lload 14
      // 254: lstore 26
      // 256: new java/lang/IllegalStateException
      // 259: astore 4
      // 25b: lload 10
      // 25d: lstore 26
      // 25f: lload 14
      // 261: lstore 26
      // 263: aload 1
      // 264: invokevirtual com/starpilot/starview/Ntrip$Config.getMount ()Ljava/lang/String;
      // 267: astore 1
      // 268: lload 10
      // 26a: lstore 26
      // 26c: lload 14
      // 26e: lstore 26
      // 270: new java/lang/StringBuilder
      // 273: astore 30
      // 275: lload 10
      // 277: lstore 26
      // 279: lload 14
      // 27b: lstore 26
      // 27d: aload 30
      // 27f: invokespecial java/lang/StringBuilder.<init> ()V
      // 282: lload 10
      // 284: lstore 26
      // 286: lload 14
      // 288: lstore 26
      // 28a: aload 4
      // 28c: aload 30
      // 28e: ldc_w "mountpoint \""
      // 291: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 294: aload 1
      // 295: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 298: ldc_w "\" not found on this caster"
      // 29b: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 29e: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 2a1: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
      // 2a4: lload 10
      // 2a6: lstore 26
      // 2a8: lload 14
      // 2aa: lstore 26
      // 2ac: aload 4
      // 2ae: athrow
      // 2af: iinc 7 1
      // 2b2: aload 31
      // 2b4: astore 30
      // 2b6: goto 1a1
      // 2b9: aload 30
      // 2bb: astore 33
      // 2bd: goto 2c3
      // 2c0: aconst_null
      // 2c1: astore 33
      // 2c3: lload 10
      // 2c5: lstore 24
      // 2c7: lload 14
      // 2c9: lstore 24
      // 2cb: getstatic com/starpilot/starview/Ntrip.INSTANCE Lcom/starpilot/starview/Ntrip;
      // 2ce: astore 31
      // 2d0: iload 3
      // 2d1: ifeq 2e0
      // 2d4: ldc_w "2.0"
      // 2d7: astore 30
      // 2d9: goto 2e5
      // 2dc: astore 1
      // 2dd: goto b9f
      // 2e0: ldc_w "1.0"
      // 2e3: astore 30
      // 2e5: lload 10
      // 2e7: lstore 24
      // 2e9: lload 14
      // 2eb: lstore 24
      // 2ed: aload 37
      // 2ef: checkcast java/lang/CharSequence
      // 2f2: invokestatic kotlin/text/StringsKt.trim (Ljava/lang/CharSequence;)Ljava/lang/CharSequence;
      // 2f5: invokevirtual java/lang/Object.toString ()Ljava/lang/String;
      // 2f8: bipush 40
      // 2fa: invokestatic kotlin/text/StringsKt.take (Ljava/lang/String;I)Ljava/lang/String;
      // 2fd: astore 36
      // 2ff: lload 10
      // 301: lstore 24
      // 303: lload 14
      // 305: lstore 24
      // 307: new java/lang/StringBuilder
      // 30a: astore 39
      // 30c: lload 10
      // 30e: lstore 24
      // 310: lload 14
      // 312: lstore 24
      // 314: aload 39
      // 316: invokespecial java/lang/StringBuilder.<init> ()V
      // 319: lload 10
      // 31b: lstore 24
      // 31d: lload 14
      // 31f: lstore 24
      // 321: aload 31
      // 323: aload 39
      // 325: ldc_w "connected (NTRIP "
      // 328: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 32b: aload 30
      // 32d: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 330: ldc_w ": "
      // 333: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 336: aload 36
      // 338: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 33b: ldc_w ")"
      // 33e: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 341: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 344: invokespecial com/starpilot/starview/Ntrip.note (Ljava/lang/String;)V
      // 347: lload 10
      // 349: lstore 24
      // 34b: lload 14
      // 34d: lstore 24
      // 34f: aload 32
      // 351: sipush 1000
      // 354: invokevirtual java/net/Socket.setSoTimeout (I)V
      // 357: lload 10
      // 359: lstore 24
      // 35b: lload 14
      // 35d: lstore 24
      // 35f: getstatic com/starpilot/starview/Ntrip.INSTANCE Lcom/starpilot/starview/Ntrip;
      // 362: ldc_w "connected, waiting for corrections"
      // 365: invokespecial com/starpilot/starview/Ntrip.setState (Ljava/lang/String;)V
      // 368: lconst_0
      // 369: lstore 16
      // 36b: lload 10
      // 36d: lstore 24
      // 36f: lload 14
      // 371: lstore 24
      // 373: invokestatic java/lang/System.currentTimeMillis ()J
      // 376: lstore 20
      // 378: lload 10
      // 37a: lstore 24
      // 37c: lload 14
      // 37e: lstore 24
      // 380: invokestatic java/lang/System.currentTimeMillis ()J
      // 383: lstore 18
      // 385: bipush 0
      // 386: istore 7
      // 388: lload 10
      // 38a: lstore 24
      // 38c: lload 14
      // 38e: lstore 24
      // 390: new java/io/ByteArrayOutputStream
      // 393: astore 39
      // 395: lload 10
      // 397: lstore 24
      // 399: lload 14
      // 39b: lstore 24
      // 39d: aload 39
      // 39f: invokespecial java/io/ByteArrayOutputStream.<init> ()V
      // 3a2: lload 10
      // 3a4: lstore 24
      // 3a6: lload 14
      // 3a8: lstore 24
      // 3aa: sipush 4096
      // 3ad: newarray 8
      // 3af: astore 40
      // 3b1: lload 10
      // 3b3: lstore 24
      // 3b5: lload 14
      // 3b7: lstore 24
      // 3b9: invokestatic java/lang/System.currentTimeMillis ()J
      // 3bc: lstore 22
      // 3be: bipush 0
      // 3bf: istore 8
      // 3c1: lload 10
      // 3c3: lstore 24
      // 3c5: lload 14
      // 3c7: lstore 24
      // 3c9: new kotlin/collections/ArrayDeque
      // 3cc: astore 36
      // 3ce: lload 10
      // 3d0: lstore 24
      // 3d2: lload 14
      // 3d4: lstore 24
      // 3d6: aload 36
      // 3d8: invokespecial kotlin/collections/ArrayDeque.<init> ()V
      // 3db: aload 37
      // 3dd: astore 31
      // 3df: aload 35
      // 3e1: astore 30
      // 3e3: iload 28
      // 3e5: istore 3
      // 3e6: lload 10
      // 3e8: lstore 12
      // 3ea: lload 12
      // 3ec: lstore 24
      // 3ee: lload 14
      // 3f0: lstore 24
      // 3f2: iload 2
      // 3f3: getstatic com/starpilot/starview/Ntrip.generation I
      // 3f6: if_icmpne b2d
      // 3f9: lload 12
      // 3fb: lstore 24
      // 3fd: lload 14
      // 3ff: lstore 24
      // 401: invokestatic java/lang/System.currentTimeMillis ()J
      // 404: lstore 10
      // 406: lload 10
      // 408: lload 16
      // 40a: lsub
      // 40b: ldc2_w 10000
      // 40e: lcmp
      // 40f: iflt 55d
      // 412: lload 12
      // 414: lstore 26
      // 416: lload 14
      // 418: lstore 26
      // 41a: getstatic com/starpilot/starview/Ntrip.rtk Lcom/starpilot/starview/RtkStatus;
      // 41d: astore 41
      // 41f: aload 41
      // 421: ifnull 444
      // 424: lload 12
      // 426: lstore 26
      // 428: lload 14
      // 42a: lstore 26
      // 42c: getstatic com/starpilot/starview/NtripCore.INSTANCE Lcom/starpilot/starview/NtripCore;
      // 42f: aload 41
      // 431: lload 10
      // 433: invokevirtual com/starpilot/starview/NtripCore.gga (Lcom/starpilot/starview/RtkStatus;J)Ljava/lang/String;
      // 436: astore 37
      // 438: aload 37
      // 43a: astore 35
      // 43c: aload 37
      // 43e: ifnonnull 470
      // 441: goto 444
      // 444: lload 12
      // 446: lstore 26
      // 448: lload 14
      // 44a: lstore 26
      // 44c: getstatic com/starpilot/starview/Ntrip.lastGga Ljava/lang/String;
      // 44f: astore 35
      // 451: aload 35
      // 453: ifnull 46d
      // 456: lload 12
      // 458: lstore 26
      // 45a: lload 14
      // 45c: lstore 26
      // 45e: getstatic com/starpilot/starview/NtripCore.INSTANCE Lcom/starpilot/starview/NtripCore;
      // 461: aload 35
      // 463: lload 10
      // 465: invokevirtual com/starpilot/starview/NtripCore.retime (Ljava/lang/String;J)Ljava/lang/String;
      // 468: astore 35
      // 46a: goto 470
      // 46d: aconst_null
      // 46e: astore 35
      // 470: aload 35
      // 472: ifnull 51d
      // 475: lload 12
      // 477: lstore 26
      // 479: lload 14
      // 47b: lstore 26
      // 47d: new java/lang/StringBuilder
      // 480: astore 37
      // 482: lload 12
      // 484: lstore 26
      // 486: lload 14
      // 488: lstore 26
      // 48a: aload 37
      // 48c: invokespecial java/lang/StringBuilder.<init> ()V
      // 48f: lload 12
      // 491: lstore 26
      // 493: lload 14
      // 495: lstore 26
      // 497: aload 37
      // 499: aload 35
      // 49b: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 49e: ldc_w "\r\n"
      // 4a1: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 4a4: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 4a7: getstatic kotlin/text/Charsets.UTF_8 Ljava/nio/charset/Charset;
      // 4aa: invokevirtual java/lang/String.getBytes (Ljava/nio/charset/Charset;)[B
      // 4ad: astore 37
      // 4af: lload 12
      // 4b1: lstore 26
      // 4b3: lload 14
      // 4b5: lstore 26
      // 4b7: aload 37
      // 4b9: ldc_w "getBytes(...)"
      // 4bc: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 4bf: lload 12
      // 4c1: lstore 26
      // 4c3: lload 14
      // 4c5: lstore 26
      // 4c7: aload 30
      // 4c9: aload 37
      // 4cb: invokevirtual java/io/OutputStream.write ([B)V
      // 4ce: lload 12
      // 4d0: lstore 26
      // 4d2: lload 14
      // 4d4: lstore 26
      // 4d6: aload 30
      // 4d8: invokevirtual java/io/OutputStream.flush ()V
      // 4db: lload 10
      // 4dd: lstore 16
      // 4df: aload 41
      // 4e1: ifnull 4fb
      // 4e4: lload 12
      // 4e6: lstore 26
      // 4e8: lload 14
      // 4ea: lstore 26
      // 4ec: getstatic com/starpilot/starview/NtripCore.INSTANCE Lcom/starpilot/starview/NtripCore;
      // 4ef: aload 41
      // 4f1: lload 10
      // 4f3: invokevirtual com/starpilot/starview/NtripCore.gga (Lcom/starpilot/starview/RtkStatus;J)Ljava/lang/String;
      // 4f6: astore 37
      // 4f8: goto 4fe
      // 4fb: aconst_null
      // 4fc: astore 37
      // 4fe: aload 37
      // 500: ifnull 51a
      // 503: lload 12
      // 505: lstore 26
      // 507: lload 14
      // 509: lstore 26
      // 50b: aload 35
      // 50d: putstatic com/starpilot/starview/Ntrip.lastGga Ljava/lang/String;
      // 510: lload 10
      // 512: lstore 18
      // 514: bipush 0
      // 515: istore 7
      // 517: goto 52a
      // 51a: goto 52a
      // 51d: lload 10
      // 51f: ldc2_w 10000
      // 522: lsub
      // 523: sipush 2000
      // 526: i2l
      // 527: ladd
      // 528: lstore 16
      // 52a: lload 10
      // 52c: lload 18
      // 52e: lsub
      // 52f: ldc2_w 30000
      // 532: lcmp
      // 533: ifle 556
      // 536: iload 7
      // 538: ifne 556
      // 53b: lload 12
      // 53d: lstore 26
      // 53f: lload 14
      // 541: lstore 26
      // 543: getstatic com/starpilot/starview/Ntrip.INSTANCE Lcom/starpilot/starview/Ntrip;
      // 546: ldc_w "no fresh position from the receiver for 30 s (sending the last one)"
      // 549: invokespecial com/starpilot/starview/Ntrip.note (Ljava/lang/String;)V
      // 54c: bipush 1
      // 54d: istore 7
      // 54f: lload 18
      // 551: lstore 24
      // 553: goto 561
      // 556: lload 18
      // 558: lstore 24
      // 55a: goto 561
      // 55d: lload 18
      // 55f: lstore 24
      // 561: lload 12
      // 563: lstore 26
      // 565: lload 14
      // 567: lstore 26
      // 569: aload 34
      // 56b: aload 40
      // 56d: invokevirtual java/io/InputStream.read ([B)I
      // 570: istore 9
      // 572: goto 57a
      // 575: astore 35
      // 577: bipush 0
      // 578: istore 9
      // 57a: iload 9
      // 57c: ifge 586
      // 57f: aload 38
      // 581: aconst_null
      // 582: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 585: return
      // 586: iload 9
      // 588: ifle 7de
      // 58b: aload 33
      // 58d: ifnull 5c1
      // 590: lload 12
      // 592: lstore 26
      // 594: lload 14
      // 596: lstore 26
      // 598: aload 33
      // 59a: aload 40
      // 59c: iload 9
      // 59e: aload 39
      // 5a0: checkcast java/io/OutputStream
      // 5a3: invokevirtual com/starpilot/starview/NtripCore$Dechunker.feed ([BILjava/io/OutputStream;)V
      // 5a6: lload 12
      // 5a8: lstore 26
      // 5aa: lload 14
      // 5ac: lstore 26
      // 5ae: aload 33
      // 5b0: invokevirtual com/starpilot/starview/NtripCore$Dechunker.getEnded ()Z
      // 5b3: istore 28
      // 5b5: iload 28
      // 5b7: ifeq 5d3
      // 5ba: aload 38
      // 5bc: aconst_null
      // 5bd: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 5c0: return
      // 5c1: lload 12
      // 5c3: lstore 26
      // 5c5: lload 14
      // 5c7: lstore 26
      // 5c9: aload 39
      // 5cb: aload 40
      // 5cd: bipush 0
      // 5ce: iload 9
      // 5d0: invokevirtual java/io/ByteArrayOutputStream.write ([BII)V
      // 5d3: lload 12
      // 5d5: lstore 26
      // 5d7: lload 14
      // 5d9: lstore 26
      // 5db: getstatic com/starpilot/starview/Ntrip.bytesIn J
      // 5de: iload 9
      // 5e0: i2l
      // 5e1: ladd
      // 5e2: putstatic com/starpilot/starview/Ntrip.bytesIn J
      // 5e5: lload 12
      // 5e7: lstore 26
      // 5e9: lload 14
      // 5eb: lstore 26
      // 5ed: lload 10
      // 5ef: putstatic com/starpilot/starview/Ntrip.lastDataMs J
      // 5f2: iload 8
      // 5f4: ifeq 65b
      // 5f7: lload 12
      // 5f9: lstore 26
      // 5fb: lload 14
      // 5fd: lstore 26
      // 5ff: getstatic com/starpilot/starview/Ntrip.INSTANCE Lcom/starpilot/starview/Ntrip;
      // 602: astore 37
      // 604: lload 12
      // 606: lstore 26
      // 608: lload 14
      // 60a: lstore 26
      // 60c: lload 10
      // 60e: lload 22
      // 610: lsub
      // 611: sipush 1000
      // 614: i2l
      // 615: ldiv
      // 616: lstore 18
      // 618: lload 12
      // 61a: lstore 26
      // 61c: lload 14
      // 61e: lstore 26
      // 620: new java/lang/StringBuilder
      // 623: astore 35
      // 625: lload 12
      // 627: lstore 26
      // 629: lload 14
      // 62b: lstore 26
      // 62d: aload 35
      // 62f: invokespecial java/lang/StringBuilder.<init> ()V
      // 632: bipush 0
      // 633: istore 8
      // 635: lload 12
      // 637: lstore 26
      // 639: lload 14
      // 63b: lstore 26
      // 63d: aload 37
      // 63f: aload 35
      // 641: ldc_w "corrections flowing again after "
      // 644: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 647: lload 18
      // 649: invokevirtual java/lang/StringBuilder.append (J)Ljava/lang/StringBuilder;
      // 64c: ldc_w " s"
      // 64f: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 652: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 655: invokespecial com/starpilot/starview/Ntrip.note (Ljava/lang/String;)V
      // 658: goto 65b
      // 65b: aload 4
      // 65d: ifnull 672
      // 660: lload 12
      // 662: lstore 26
      // 664: lload 14
      // 666: lstore 26
      // 668: aload 4
      // 66a: invokevirtual com/starpilot/starview/Ntrip$Role.getBackup ()Lcom/starpilot/starview/Ntrip$Config;
      // 66d: astore 35
      // 66f: goto 675
      // 672: aconst_null
      // 673: astore 35
      // 675: aload 35
      // 677: ifnull 7d3
      // 67a: lload 12
      // 67c: lstore 26
      // 67e: lload 14
      // 680: lstore 26
      // 682: aload 4
      // 684: invokevirtual com/starpilot/starview/Ntrip$Role.getPlan ()Lcom/starpilot/starview/NtripCore$BackupPlan;
      // 687: invokevirtual com/starpilot/starview/NtripCore$BackupPlan.getOnBackup ()Z
      // 68a: ifeq 7d3
      // 68d: lload 12
      // 68f: lstore 26
      // 691: lload 14
      // 693: lstore 26
      // 695: aload 4
      // 697: invokevirtual com/starpilot/starview/Ntrip$Role.getBackup ()Lcom/starpilot/starview/Ntrip$Config;
      // 69a: invokevirtual com/starpilot/starview/Ntrip$Config.getMount ()Ljava/lang/String;
      // 69d: checkcast java/lang/CharSequence
      // 6a0: invokestatic kotlin/text/StringsKt.isBlank (Ljava/lang/CharSequence;)Z
      // 6a3: ifeq 7d3
      // 6a6: lload 10
      // 6a8: lload 22
      // 6aa: lsub
      // 6ab: ldc2_w 8000
      // 6ae: lcmp
      // 6af: iflt 7d3
      // 6b2: lload 12
      // 6b4: lstore 26
      // 6b6: lload 14
      // 6b8: lstore 26
      // 6ba: aload 36
      // 6bc: lload 10
      // 6be: invokestatic java/lang/Long.valueOf (J)Ljava/lang/Long;
      // 6c1: invokevirtual kotlin/collections/ArrayDeque.addLast (Ljava/lang/Object;)V
      // 6c4: lload 12
      // 6c6: lstore 26
      // 6c8: lload 14
      // 6ca: lstore 26
      // 6cc: aload 36
      // 6ce: checkcast java/util/Collection
      // 6d1: invokeinterface java/util/Collection.isEmpty ()Z 1
      // 6d6: ifne 707
      // 6d9: lload 12
      // 6db: lstore 26
      // 6dd: lload 14
      // 6df: lstore 26
      // 6e1: lload 10
      // 6e3: aload 36
      // 6e5: invokevirtual kotlin/collections/ArrayDeque.first ()Ljava/lang/Object;
      // 6e8: checkcast java/lang/Number
      // 6eb: invokevirtual java/lang/Number.longValue ()J
      // 6ee: lsub
      // 6ef: ldc2_w 180000
      // 6f2: lcmp
      // 6f3: ifle 707
      // 6f6: lload 12
      // 6f8: lstore 26
      // 6fa: lload 14
      // 6fc: lstore 26
      // 6fe: aload 36
      // 700: invokevirtual kotlin/collections/ArrayDeque.removeFirst ()Ljava/lang/Object;
      // 703: pop
      // 704: goto 6c4
      // 707: lload 12
      // 709: lstore 26
      // 70b: lload 14
      // 70d: lstore 26
      // 70f: aload 36
      // 711: invokevirtual kotlin/collections/ArrayDeque.size ()I
      // 714: bipush 3
      // 715: if_icmpge 71b
      // 718: goto 7d3
      // 71b: lload 12
      // 71d: lstore 26
      // 71f: lload 14
      // 721: lstore 26
      // 723: getstatic com/starpilot/starview/Ntrip.badBases Ljava/util/HashMap;
      // 726: astore 4
      // 728: lload 12
      // 72a: lstore 26
      // 72c: lload 14
      // 72e: lstore 26
      // 730: aload 4
      // 732: monitorenter
      // 733: getstatic com/starpilot/starview/Ntrip.badBases Ljava/util/HashMap;
      // 736: checkcast java/util/Map
      // 739: aload 1
      // 73a: invokevirtual com/starpilot/starview/Ntrip$Config.getMount ()Ljava/lang/String;
      // 73d: lload 10
      // 73f: ldc2_w 3600000
      // 742: ladd
      // 743: invokestatic java/lang/Long.valueOf (J)Ljava/lang/Long;
      // 746: invokeinterface java/util/Map.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
      // 74b: pop
      // 74c: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 74f: astore 30
      // 751: lload 12
      // 753: lstore 26
      // 755: lload 14
      // 757: lstore 26
      // 759: aload 4
      // 75b: monitorexit
      // 75c: lload 12
      // 75e: lstore 26
      // 760: lload 14
      // 762: lstore 26
      // 764: new java/lang/IllegalStateException
      // 767: astore 4
      // 769: lload 12
      // 76b: lstore 26
      // 76d: lload 14
      // 76f: lstore 26
      // 771: aload 1
      // 772: invokevirtual com/starpilot/starview/Ntrip$Config.getMount ()Ljava/lang/String;
      // 775: astore 1
      // 776: lload 12
      // 778: lstore 26
      // 77a: lload 14
      // 77c: lstore 26
      // 77e: new java/lang/StringBuilder
      // 781: astore 30
      // 783: lload 12
      // 785: lstore 26
      // 787: lload 14
      // 789: lstore 26
      // 78b: aload 30
      // 78d: invokespecial java/lang/StringBuilder.<init> ()V
      // 790: lload 12
      // 792: lstore 26
      // 794: lload 14
      // 796: lstore 26
      // 798: aload 4
      // 79a: aload 30
      // 79c: ldc_w "base "
      // 79f: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 7a2: aload 1
      // 7a3: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 7a6: ldc_w " keeps stalling - trying the next nearest"
      // 7a9: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 7ac: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 7af: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
      // 7b2: lload 12
      // 7b4: lstore 26
      // 7b6: lload 14
      // 7b8: lstore 26
      // 7ba: aload 4
      // 7bc: athrow
      // 7bd: astore 1
      // 7be: lload 12
      // 7c0: lstore 26
      // 7c2: lload 14
      // 7c4: lstore 26
      // 7c6: aload 4
      // 7c8: monitorexit
      // 7c9: lload 12
      // 7cb: lstore 26
      // 7cd: lload 14
      // 7cf: lstore 26
      // 7d1: aload 1
      // 7d2: athrow
      // 7d3: lload 10
      // 7d5: lstore 22
      // 7d7: iload 8
      // 7d9: istore 9
      // 7db: goto 7e2
      // 7de: iload 8
      // 7e0: istore 9
      // 7e2: iload 9
      // 7e4: istore 8
      // 7e6: lload 10
      // 7e8: lload 22
      // 7ea: lsub
      // 7eb: ldc2_w 15000
      // 7ee: lcmp
      // 7ef: ifle 80f
      // 7f2: iload 9
      // 7f4: istore 8
      // 7f6: iload 9
      // 7f8: ifne 80f
      // 7fb: lload 12
      // 7fd: lstore 26
      // 7ff: lload 14
      // 801: lstore 26
      // 803: getstatic com/starpilot/starview/Ntrip.INSTANCE Lcom/starpilot/starview/Ntrip;
      // 806: ldc_w "no corrections for 15 s - waiting, not hanging up"
      // 809: invokespecial com/starpilot/starview/Ntrip.note (Ljava/lang/String;)V
      // 80c: bipush 1
      // 80d: istore 8
      // 80f: lload 10
      // 811: lload 22
      // 813: lsub
      // 814: ldc2_w 90000
      // 817: lcmp
      // 818: ifle 851
      // 81b: lload 12
      // 81d: lstore 26
      // 81f: lload 14
      // 821: lstore 26
      // 823: getstatic com/starpilot/starview/Ntrip.netNow Landroid/net/Network;
      // 826: ifnonnull 82c
      // 829: goto 851
      // 82c: lload 12
      // 82e: lstore 26
      // 830: lload 14
      // 832: lstore 26
      // 834: new java/lang/IllegalStateException
      // 837: astore 1
      // 838: lload 12
      // 83a: lstore 26
      // 83c: lload 14
      // 83e: lstore 26
      // 840: aload 1
      // 841: ldc_w "no corrections for 90 s"
      // 844: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
      // 847: lload 12
      // 849: lstore 26
      // 84b: lload 14
      // 84d: lstore 26
      // 84f: aload 1
      // 850: athrow
      // 851: aload 4
      // 853: ifnull 868
      // 856: lload 12
      // 858: lstore 26
      // 85a: lload 14
      // 85c: lstore 26
      // 85e: aload 4
      // 860: invokevirtual com/starpilot/starview/Ntrip$Role.getBackup ()Lcom/starpilot/starview/Ntrip$Config;
      // 863: astore 35
      // 865: goto 86b
      // 868: aconst_null
      // 869: astore 35
      // 86b: aload 35
      // 86d: ifnull a0e
      // 870: lload 10
      // 872: lload 14
      // 874: lsub
      // 875: ldc2_w 10000
      // 878: lcmp
      // 879: iflt a0e
      // 87c: getstatic com/starpilot/starview/Ntrip.rtk Lcom/starpilot/starview/RtkStatus;
      // 87f: astore 37
      // 881: aload 37
      // 883: ifnull 8b8
      // 886: aload 37
      // 888: invokevirtual com/starpilot/starview/RtkStatus.getRtk ()Ljava/lang/String;
      // 88b: ldc_w "fixed"
      // 88e: invokestatic kotlin/jvm/internal/Intrinsics.areEqual (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 891: ifne 8a2
      // 894: aload 37
      // 896: invokevirtual com/starpilot/starview/RtkStatus.getRtk ()Ljava/lang/String;
      // 899: ldc_w "float"
      // 89c: invokestatic kotlin/jvm/internal/Intrinsics.areEqual (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 89f: ifeq 8b8
      // 8a2: getstatic com/starpilot/starview/Ntrip.bytesIn J
      // 8a5: lconst_0
      // 8a6: lcmp
      // 8a7: ifle 8b8
      // 8aa: aload 4
      // 8ac: lload 10
      // 8ae: invokevirtual com/starpilot/starview/Ntrip$Role.setLastHelpMs (J)V
      // 8b1: goto 8b8
      // 8b4: astore 1
      // 8b5: goto b9f
      // 8b8: aload 4
      // 8ba: invokevirtual com/starpilot/starview/Ntrip$Role.getPlan ()Lcom/starpilot/starview/NtripCore$BackupPlan;
      // 8bd: invokevirtual com/starpilot/starview/NtripCore$BackupPlan.getOnBackup ()Z
      // 8c0: istore 28
      // 8c2: iload 28
      // 8c4: ifne 8f0
      // 8c7: lload 10
      // 8c9: lload 12
      // 8cb: aload 4
      // 8cd: invokevirtual com/starpilot/starview/Ntrip$Role.getLastHelpMs ()J
      // 8d0: invokestatic java/lang/Math.max (JJ)J
      // 8d3: lsub
      // 8d4: aload 4
      // 8d6: invokevirtual com/starpilot/starview/Ntrip$Role.getPlan ()Lcom/starpilot/starview/NtripCore$BackupPlan;
      // 8d9: invokevirtual com/starpilot/starview/NtripCore$BackupPlan.getSwitchMs ()J
      // 8dc: lcmp
      // 8dd: ifge 8e3
      // 8e0: goto 8f0
      // 8e3: new java/lang/IllegalStateException
      // 8e6: astore 1
      // 8e7: aload 1
      // 8e8: ldc_w "no RTK from these corrections for 3 minutes"
      // 8eb: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
      // 8ee: aload 1
      // 8ef: athrow
      // 8f0: aload 4
      // 8f2: invokevirtual com/starpilot/starview/Ntrip$Role.getPlan ()Lcom/starpilot/starview/NtripCore$BackupPlan;
      // 8f5: invokevirtual com/starpilot/starview/NtripCore$BackupPlan.getOnBackup ()Z
      // 8f8: istore 28
      // 8fa: iload 28
      // 8fc: ifeq 91b
      // 8ff: aload 4
      // 901: invokevirtual com/starpilot/starview/Ntrip$Role.getPlan ()Lcom/starpilot/starview/NtripCore$BackupPlan;
      // 904: lload 10
      // 906: invokevirtual com/starpilot/starview/NtripCore$BackupPlan.timeToRetryMain (J)Z
      // 909: ifne 90f
      // 90c: goto 91b
      // 90f: new java/lang/IllegalStateException
      // 912: astore 1
      // 913: aload 1
      // 914: ldc "time to try the main caster again"
      // 916: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
      // 919: aload 1
      // 91a: athrow
      // 91b: aload 4
      // 91d: invokevirtual com/starpilot/starview/Ntrip$Role.getBase ()Lcom/starpilot/starview/NtripCore$Mount;
      // 920: astore 41
      // 922: aload 4
      // 924: invokevirtual com/starpilot/starview/Ntrip$Role.getPlan ()Lcom/starpilot/starview/NtripCore$BackupPlan;
      // 927: invokevirtual com/starpilot/starview/NtripCore$BackupPlan.getOnBackup ()Z
      // 92a: ifeq a03
      // 92d: aload 4
      // 92f: invokevirtual com/starpilot/starview/Ntrip$Role.getBackup ()Lcom/starpilot/starview/Ntrip$Config;
      // 932: invokevirtual com/starpilot/starview/Ntrip$Config.getMount ()Ljava/lang/String;
      // 935: checkcast java/lang/CharSequence
      // 938: invokestatic kotlin/text/StringsKt.isBlank (Ljava/lang/CharSequence;)Z
      // 93b: ifeq a03
      // 93e: aload 41
      // 940: ifnull a03
      // 943: aload 37
      // 945: ifnull a03
      // 948: aload 37
      // 94a: invokevirtual com/starpilot/starview/RtkStatus.getHasFix ()Z
      // 94d: istore 28
      // 94f: iload 28
      // 951: ifeq a03
      // 954: aload 37
      // 956: invokevirtual com/starpilot/starview/RtkStatus.getLat ()D
      // 959: dstore 5
      // 95b: lload 10
      // 95d: lstore 18
      // 95f: lload 18
      // 961: lstore 14
      // 963: aload 41
      // 965: dload 5
      // 967: aload 37
      // 969: invokevirtual com/starpilot/starview/RtkStatus.getLon ()D
      // 96c: invokevirtual com/starpilot/starview/NtripCore$Mount.milesFrom (DD)D
      // 96f: ldc2_w 15.0
      // 972: dcmpl
      // 973: ifle 9fc
      // 976: lload 18
      // 978: lstore 14
      // 97a: getstatic com/starpilot/starview/NtripCore.INSTANCE Lcom/starpilot/starview/NtripCore;
      // 97d: getstatic com/starpilot/starview/Ntrip.mounts Ljava/util/List;
      // 980: aload 37
      // 982: invokevirtual com/starpilot/starview/RtkStatus.getLat ()D
      // 985: aload 37
      // 987: invokevirtual com/starpilot/starview/RtkStatus.getLon ()D
      // 98a: dconst_0
      // 98b: getstatic com/starpilot/starview/Ntrip.INSTANCE Lcom/starpilot/starview/Ntrip;
      // 98e: lload 10
      // 990: invokespecial com/starpilot/starview/Ntrip.skipBases (J)Ljava/util/Set;
      // 993: bipush 8
      // 995: aconst_null
      // 996: invokestatic com/starpilot/starview/NtripCore.nearestBase$default (Lcom/starpilot/starview/NtripCore;Ljava/util/List;DDDLjava/util/Set;ILjava/lang/Object;)Lcom/starpilot/starview/NtripCore$Mount;
      // 999: astore 35
      // 99b: aload 35
      // 99d: ifnull 9f9
      // 9a0: lload 18
      // 9a2: lstore 14
      // 9a4: aload 35
      // 9a6: invokevirtual com/starpilot/starview/NtripCore$Mount.getName ()Ljava/lang/String;
      // 9a9: aload 41
      // 9ab: invokevirtual com/starpilot/starview/NtripCore$Mount.getName ()Ljava/lang/String;
      // 9ae: invokestatic kotlin/jvm/internal/Intrinsics.areEqual (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 9b1: ifne 9f9
      // 9b4: lload 18
      // 9b6: lstore 14
      // 9b8: aload 35
      // 9ba: aload 37
      // 9bc: invokevirtual com/starpilot/starview/RtkStatus.getLat ()D
      // 9bf: aload 37
      // 9c1: invokevirtual com/starpilot/starview/RtkStatus.getLon ()D
      // 9c4: invokevirtual com/starpilot/starview/NtripCore$Mount.milesFrom (DD)D
      // 9c7: aload 41
      // 9c9: aload 37
      // 9cb: invokevirtual com/starpilot/starview/RtkStatus.getLat ()D
      // 9ce: aload 37
      // 9d0: invokevirtual com/starpilot/starview/RtkStatus.getLon ()D
      // 9d3: invokevirtual com/starpilot/starview/NtripCore$Mount.milesFrom (DD)D
      // 9d6: ldc2_w 5.0
      // 9d9: dsub
      // 9da: dcmpg
      // 9db: iflt 9e1
      // 9de: goto a12
      // 9e1: lload 18
      // 9e3: lstore 14
      // 9e5: new java/lang/IllegalStateException
      // 9e8: astore 1
      // 9e9: lload 18
      // 9eb: lstore 14
      // 9ed: aload 1
      // 9ee: ldc "a nearer base"
      // 9f0: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
      // 9f3: lload 18
      // 9f5: lstore 14
      // 9f7: aload 1
      // 9f8: athrow
      // 9f9: goto a12
      // 9fc: goto a12
      // 9ff: astore 1
      // a00: goto b9f
      // a03: lload 10
      // a05: lstore 18
      // a07: goto a12
      // a0a: astore 1
      // a0b: goto b9f
      // a0e: lload 14
      // a10: lstore 18
      // a12: lload 18
      // a14: lstore 14
      // a16: aload 39
      // a18: invokevirtual java/io/ByteArrayOutputStream.size ()I
      // a1b: ifle b22
      // a1e: lload 10
      // a20: lload 20
      // a22: lsub
      // a23: ldc2_w 250
      // a26: lcmp
      // a27: ifge a3f
      // a2a: lload 18
      // a2c: lstore 14
      // a2e: aload 39
      // a30: invokevirtual java/io/ByteArrayOutputStream.size ()I
      // a33: sipush 8000
      // a36: if_icmple a3c
      // a39: goto a3f
      // a3c: goto b22
      // a3f: lload 18
      // a41: lstore 14
      // a43: invokestatic java/util/Base64.getEncoder ()Ljava/util/Base64$Encoder;
      // a46: aload 39
      // a48: invokevirtual java/io/ByteArrayOutputStream.toByteArray ()[B
      // a4b: invokevirtual java/util/Base64$Encoder.encodeToString ([B)Ljava/lang/String;
      // a4e: astore 37
      // a50: lload 18
      // a52: lstore 14
      // a54: aload 39
      // a56: invokevirtual java/io/ByteArrayOutputStream.reset ()V
      // a59: lload 18
      // a5b: lstore 14
      // a5d: getstatic com/starpilot/starview/Link.INSTANCE Lcom/starpilot/starview/Link;
      // a60: invokevirtual com/starpilot/starview/Link.getData ()Lcom/starpilot/starview/DataClient;
      // a63: astore 35
      // a65: aload 35
      // a67: ifnonnull a8f
      // a6a: lload 18
      // a6c: lstore 14
      // a6e: getstatic com/starpilot/starview/Ntrip.commaGoneNoted Z
      // a71: ifne a8c
      // a74: lload 18
      // a76: lstore 14
      // a78: bipush 1
      // a79: putstatic com/starpilot/starview/Ntrip.commaGoneNoted Z
      // a7c: lload 18
      // a7e: lstore 14
      // a80: getstatic com/starpilot/starview/Ntrip.INSTANCE Lcom/starpilot/starview/Ntrip;
      // a83: ldc_w "comma not connected - corrections not delivered"
      // a86: invokespecial com/starpilot/starview/Ntrip.note (Ljava/lang/String;)V
      // a89: goto a8c
      // a8c: goto aca
      // a8f: lload 18
      // a91: lstore 14
      // a93: bipush 0
      // a94: putstatic com/starpilot/starview/Ntrip.commaGoneNoted Z
      // a97: lload 18
      // a99: lstore 14
      // a9b: ldc_w "b64"
      // a9e: aload 37
      // aa0: invokestatic kotlin/TuplesKt.to (Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;
      // aa3: invokestatic kotlin/collections/MapsKt.mapOf (Lkotlin/Pair;)Ljava/util/Map;
      // aa6: astore 37
      // aa8: lload 18
      // aaa: lstore 14
      // aac: new com/starpilot/starview/Ntrip$$ExternalSyntheticLambda1
      // aaf: astore 41
      // ab1: lload 18
      // ab3: lstore 14
      // ab5: aload 41
      // ab7: invokespecial com/starpilot/starview/Ntrip$$ExternalSyntheticLambda1.<init> ()V
      // aba: lload 18
      // abc: lstore 14
      // abe: aload 35
      // ac0: ldc_w "rtk_rtcm"
      // ac3: aload 37
      // ac5: aload 41
      // ac7: invokevirtual com/starpilot/starview/DataClient.request (Ljava/lang/String;Ljava/util/Map;Lkotlin/jvm/functions/Function1;)V
      // aca: lload 18
      // acc: lstore 14
      // ace: getstatic com/starpilot/starview/Ntrip.INSTANCE Lcom/starpilot/starview/Ntrip;
      // ad1: astore 41
      // ad3: lload 18
      // ad5: lstore 14
      // ad7: getstatic com/starpilot/starview/NtripCore.INSTANCE Lcom/starpilot/starview/NtripCore;
      // ada: getstatic com/starpilot/starview/Ntrip.bytesIn J
      // add: invokevirtual com/starpilot/starview/NtripCore.size (J)Ljava/lang/String;
      // ae0: astore 37
      // ae2: lload 18
      // ae4: lstore 14
      // ae6: new java/lang/StringBuilder
      // ae9: astore 35
      // aeb: lload 18
      // aed: lstore 14
      // aef: aload 35
      // af1: invokespecial java/lang/StringBuilder.<init> ()V
      // af4: lload 18
      // af6: lstore 14
      // af8: aload 41
      // afa: aload 35
      // afc: ldc_w "receiving corrections ("
      // aff: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // b02: aload 37
      // b04: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // b07: ldc_w ")"
      // b0a: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // b0d: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // b10: invokespecial com/starpilot/starview/Ntrip.setState (Ljava/lang/String;)V
      // b13: lload 18
      // b15: lstore 14
      // b17: lload 10
      // b19: lstore 20
      // b1b: lload 24
      // b1d: lstore 18
      // b1f: goto 3ea
      // b22: lload 18
      // b24: lstore 14
      // b26: lload 24
      // b28: lstore 18
      // b2a: goto 3ea
      // b2d: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // b30: astore 1
      // b31: aload 38
      // b33: aconst_null
      // b34: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // b37: return
      // b38: astore 1
      // b39: goto b9f
      // b3c: lload 10
      // b3e: lstore 14
      // b40: new kotlin/NoWhenBranchMatchedException
      // b43: astore 1
      // b44: lload 10
      // b46: lstore 14
      // b48: aload 1
      // b49: invokespecial kotlin/NoWhenBranchMatchedException.<init> ()V
      // b4c: lload 10
      // b4e: lstore 14
      // b50: aload 1
      // b51: athrow
      // b52: lload 10
      // b54: lstore 14
      // b56: new java/lang/IllegalStateException
      // b59: astore 4
      // b5b: lload 10
      // b5d: lstore 14
      // b5f: aload 1
      // b60: invokevirtual com/starpilot/starview/Ntrip$Config.getMount ()Ljava/lang/String;
      // b63: astore 30
      // b65: lload 10
      // b67: lstore 14
      // b69: new java/lang/StringBuilder
      // b6c: astore 1
      // b6d: lload 10
      // b6f: lstore 14
      // b71: aload 1
      // b72: invokespecial java/lang/StringBuilder.<init> ()V
      // b75: lload 10
      // b77: lstore 14
      // b79: aload 4
      // b7b: aload 1
      // b7c: ldc_w "mountpoint \""
      // b7f: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // b82: aload 30
      // b84: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // b87: ldc_w "\" not found on this caster"
      // b8a: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // b8d: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // b90: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
      // b93: lload 10
      // b95: lstore 14
      // b97: aload 4
      // b99: athrow
      // b9a: astore 1
      // b9b: goto b9f
      // b9e: astore 1
      // b9f: aload 1
      // ba0: athrow
      // ba1: astore 4
      // ba3: aload 38
      // ba5: aload 1
      // ba6: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // ba9: aload 4
      // bab: athrow
   }

   // $VF: synthetic method
   static void stream$default(Ntrip var0, Ntrip.Config var1, int var2, boolean var3, Ntrip.Role var4, int var5, Object var6) {
      if ((var5 & 8) != 0) {
         var4 = null;
      }

      var0.stream(var1, var2, var3, var4);
   }

   private static final Unit stream$lambda$16$lambda$15(JSONObject var0) {
      Intrinsics.checkNotNullParameter(var0, "ack");
      if (!var0.optBoolean("ok")) {
         Ntrip var1 = INSTANCE;
         String var2 = var0.optString("error");
         var1.setState("comma: " + var2);
      }

      return Unit.INSTANCE;
   }

   private static final void tick$lambda$6(Ntrip.Config var0, int var1, Ntrip.Config var2, boolean var3) {
      INSTANCE.run(var0, var1, var2, var3);
   }

   private final void watchNetwork(Context var1) {
      if (netCb == null) {
         ConnectivityManager var4 = (ConnectivityManager)var1.getSystemService(ConnectivityManager.class);
         if (var4 != null) {
            NetworkCallback var2 = new NetworkCallback() {
               public void onAvailable(Network var1) {
                  Intrinsics.checkNotNullParameter(var1, "network");
                  Network var3 = Ntrip.netNow;
                  Ntrip var2x = Ntrip.INSTANCE;
                  Ntrip.netNow = var1;
                  if (var3 != null && !Intrinsics.areEqual(var3, var1) && Ntrip.thread != null) {
                     Ntrip.INSTANCE.note("internet moved to another network - reconnecting now");
                     Ntrip.INSTANCE.reopen();
                  }
               }

               public void onLost(Network var1) {
                  Intrinsics.checkNotNullParameter(var1, "network");
                  if (Intrinsics.areEqual(var1, Ntrip.netNow) && Ntrip.thread != null) {
                     Ntrip.INSTANCE.note("internet lost - keeping the line open until it's back");
                  }

                  if (Intrinsics.areEqual(var1, Ntrip.netNow)) {
                     Ntrip var2x = Ntrip.INSTANCE;
                     Ntrip.netNow = null;
                  }
               }
            };

            try {
               var4.registerDefaultNetworkCallback(var2);
               netCb = var2;
            } catch (Exception var3) {
               Log.w("StarView/Ntrip", "network callback: " + var3);
            }
         }
      }
   }

   public final Ntrip.Config backup(Context var1) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      SharedPreferences var6 = var1.getSharedPreferences("starview_ntrip", 0);
      boolean var3 = var6.getBoolean("b_on", false);
      String var7 = var6.getString("b_host", "rtk2go.com");
      if (var7 == null) {
         var7 = "";
      }

      int var2 = var6.getInt("b_port", 2101);
      String var4 = var6.getString("b_mount", "");
      if (var4 == null) {
         var4 = "";
      }

      String var5 = var6.getString("b_user", "");
      if (var5 == null) {
         var5 = "";
      }

      String var8 = var6.getString("b_pass", "none");
      if (var8 == null) {
         var8 = "";
      }

      return new Ntrip.Config(var3, var7, var2, var4, var5, var8);
   }

   public final boolean backupOnly(Context var1) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      return var1.getSharedPreferences("starview_ntrip", 0).getBoolean("b_only", false);
   }

   public final Ntrip.Config config(Context var1) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      SharedPreferences var6 = var1.getSharedPreferences("starview_ntrip", 0);
      boolean var3 = var6.getBoolean("on", false);
      String var7 = var6.getString("host", "");
      if (var7 == null) {
         var7 = "";
      }

      int var2 = var6.getInt("port", 2101);
      String var4 = var6.getString("mount", "");
      if (var4 == null) {
         var4 = "";
      }

      String var5 = var6.getString("user", "");
      if (var5 == null) {
         var5 = "";
      }

      String var8 = var6.getString("pass", "");
      if (var8 == null) {
         var8 = "";
      }

      return new Ntrip.Config(var3, var7, var2, var4, var5, var8);
   }

   public final long getBytesIn() {
      return bytesIn;
   }

   public final ArrayDeque<String> getHistory() {
      return history;
   }

   public final long getLastDataMs() {
      return lastDataMs;
   }

   public final RtkStatus getRtk() {
      return rtk;
   }

   public final long getRtkAtMs() {
      return rtkAtMs;
   }

   public final String getSource() {
      return source;
   }

   public final String getState() {
      return state;
   }

   public final void loadMounts(String var1, int var2, String var3, String var4, Function2<? super List<NtripCore.Mount>, ? super String, Unit> var5) {
      Intrinsics.checkNotNullParameter(var1, "host");
      Intrinsics.checkNotNullParameter(var3, "user");
      Intrinsics.checkNotNullParameter(var4, "pass");
      Intrinsics.checkNotNullParameter(var5, "done");
      Thread var6 = new Thread(new Ntrip$$ExternalSyntheticLambda5(new Handler(Looper.getMainLooper()), var1, var2, var3, var4, var5));
      var6.setDaemon(true);
      var6.start();
   }

   public final List<String> recent(int var1) {
      ArrayDeque var2 = history;
      List var3;
      synchronized (var2) {
         var3 = CollectionsKt.take(history, var1);
      }

      return var3;
   }

   public final void save(Context var1, Ntrip.Config var2) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      Intrinsics.checkNotNullParameter(var2, "c");
      var1.getSharedPreferences("starview_ntrip", 0)
         .edit()
         .putBoolean("on", var2.getOn())
         .putString("host", StringsKt.trim(var2.getHost()).toString())
         .putInt("port", var2.getPort())
         .putString("mount", StringsKt.trim(var2.getMount()).toString())
         .putString("user", StringsKt.trim(var2.getUser()).toString())
         .putString("pass", var2.getPass())
         .apply();
      this.restart();
   }

   public final void saveBackup(Context var1, Ntrip.Config var2) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      Intrinsics.checkNotNullParameter(var2, "c");
      var1.getSharedPreferences("starview_ntrip", 0)
         .edit()
         .putBoolean("b_on", var2.getOn())
         .putString("b_host", StringsKt.trim(var2.getHost()).toString())
         .putInt("b_port", var2.getPort())
         .putString("b_mount", StringsKt.trim(var2.getMount()).toString())
         .putString("b_user", StringsKt.trim(var2.getUser()).toString())
         .putString("b_pass", var2.getPass())
         .apply();
      this.restart();
   }

   public final void setBackupOnly(Context var1, boolean var2) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      var1.getSharedPreferences("starview_ntrip", 0).edit().putBoolean("b_only", var2).apply();
      this.restart();
   }

   public final void tick(Context var1) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      if (logFile == null) {
         logFile = new File(var1.getFilesDir(), "ntrip_log.txt");
      }

      boolean var5;
      boolean var6;
      boolean var7;
      DataClient var12;
      int var18;
      Ntrip.Config var24;
      label151: {
         Context var11 = var1.getApplicationContext();
         Intrinsics.checkNotNullExpressionValue(var11, "getApplicationContext(...)");
         this.watchNetwork(var11);
         var24 = this.config(var1);
         var12 = Link.INSTANCE.getData();
         var7 = this.backupOnly(var1);
         var13 = this.backup(var1);
         boolean var8 = var13.getOn();
         var5 = false;
         var6 = false;
         if ((var8 || var7) && !StringsKt.isBlank(var13.getHost())) {
            var18 = var13.getPort();
            boolean var17;
            if (1 <= var18 && var18 < 65536) {
               var17 = true;
            } else {
               var17 = false;
            }

            if (var17) {
               var18 = 1;
               break label151;
            }
         }

         var18 = 0;
      }

      if (!var18) {
         var13 = null;
      }

      if (var13 != null && var7) {
         var7 = true;
      } else {
         var7 = false;
      }

      boolean var19;
      if (!var24.getComplete() && !var7) {
         var19 = false;
      } else {
         var19 = true;
      }

      boolean var22;
      label119: {
         if (var24.getOn() && var19 && var12 != null && var12.getConnected()) {
            RtkStatus var25 = rtk;
            if (var25 != null && var25.getConnected()) {
               var22 = true;
            } else {
               var22 = false;
            }

            if (var22) {
               var22 = true;
               break label119;
            }
         }

         var22 = false;
      }

      long var9 = System.currentTimeMillis();
      if (var22) {
         wantedAtMs = var9;
      }

      boolean var4;
      if (var24.getOn() && var19 && thread != null && var9 - wantedAtMs < 120000L) {
         var4 = true;
      } else {
         var4 = false;
      }

      if (!var22 && !var4) {
         if (thread != null) {
            if (var24.getOn() && var19) {
               this.note("comma or receiver gone for 2 minutes - hanging up on the caster");
            }

            this.restart();
         }

         String var15;
         if (!var24.getOn()) {
            var15 = "off";
         } else if (!var19) {
            var15 = "caster, port and mountpoint needed";
         } else if (rtk == null) {
            var15 = "no RTK receiver on the comma";
         } else {
            RtkStatus var16 = rtk;
            var19 = var6;
            if (var16 != null) {
               var19 = var6;
               if (var16.getConnected()) {
                  var19 = true;
               }
            }

            if (!var19) {
               var15 = "RTK receiver unplugged";
            } else {
               var15 = "waiting for the comma";
            }
         }

         this.setState(var15);
      } else {
         Thread var26 = thread;
         var19 = var5;
         if (var26 != null) {
            var19 = var5;
            if (var26.isAlive()) {
               var19 = true;
            }
         }

         if (!var19 && var22) {
            generation++;
            Thread var14 = new Thread(new Ntrip$$ExternalSyntheticLambda4(var24, generation, var13, var7), "starview-ntrip");
            var14.setDaemon(true);
            var14.start();
            thread = var14;
         }
      }
   }

   public final void update(JSONObject var1) {
      rtk = RtkStatus.Companion.parse(var1);
      rtkAtMs = System.currentTimeMillis();
      RtkPosition.INSTANCE.feed(rtk, rtkAtMs);
   }

   public static final class Config {
      private final String host;
      private final String mount;
      private final boolean on;
      private final String pass;
      private final int port;
      private final String user;

      public Config(boolean var1, String var2, int var3, String var4, String var5, String var6) {
         Intrinsics.checkNotNullParameter(var2, "host");
         Intrinsics.checkNotNullParameter(var4, "mount");
         Intrinsics.checkNotNullParameter(var5, "user");
         Intrinsics.checkNotNullParameter(var6, "pass");
         super();
         this.on = var1;
         this.host = var2;
         this.port = var3;
         this.mount = var4;
         this.user = var5;
         this.pass = var6;
      }

      public final boolean getComplete() {
         boolean var4 = StringsKt.isBlank(this.host);
         boolean var3 = false;
         boolean var2 = var3;
         if (!var4) {
            int var1 = this.port;
            boolean var5;
            if (1 <= var1 && var1 < 65536) {
               var5 = true;
            } else {
               var5 = false;
            }

            var2 = var3;
            if (var5) {
               var2 = var3;
               if (!StringsKt.isBlank(this.mount)) {
                  var2 = true;
               }
            }
         }

         return var2;
      }

      public final String getHost() {
         return this.host;
      }

      public final String getMount() {
         return this.mount;
      }

      public final boolean getOn() {
         return this.on;
      }

      public final String getPass() {
         return this.pass;
      }

      public final int getPort() {
         return this.port;
      }

      public final String getUser() {
         return this.user;
      }
   }

   private static final class Role {
      private final Ntrip.Config backup;
      private volatile NtripCore.Mount base;
      private volatile long lastHelpMs;
      private final NtripCore.BackupPlan plan;

      public Role(Ntrip.Config var1, NtripCore.BackupPlan var2) {
         Intrinsics.checkNotNullParameter(var2, "plan");
         super();
         this.backup = var1;
         this.plan = var2;
      }

      public final Ntrip.Config getBackup() {
         return this.backup;
      }

      public final NtripCore.Mount getBase() {
         return this.base;
      }

      public final long getLastHelpMs() {
         return this.lastHelpMs;
      }

      public final NtripCore.BackupPlan getPlan() {
         return this.plan;
      }

      public final void setBase(NtripCore.Mount var1) {
         this.base = var1;
      }

      public final void setLastHelpMs(long var1) {
         this.lastHelpMs = var1;
      }
   }
}
