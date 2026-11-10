package com.starpilot.starview;

import android.content.Context;
import android.net.ConnectivityManager;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.Result.Companion;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.json.JSONObject;

public final class MapboxRelay {
   public static final MapboxRelay INSTANCE = new MapboxRelay();
   public static final int PORT = 8099;
   private static final String TAG = "StarView/Relay";
   private static final AtomicInteger active = new AtomicInteger();
   private static boolean announced;
   private static volatile String commaIp;
   private static long lastAnnounceMs;
   private static final ExecutorService pool = Executors.newCachedThreadPool();
   private static volatile int served;
   private static volatile ServerSocket server;

   // $VF: synthetic method
   public static void $r8$lambda$5a48ki63XZOdp_FyU0jlmgoZzYY(ServerSocket var0) {
      start$lambda$1(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$6X9Uu12WK_8thLxJuvDSac0J62Q(Socket var0, OutputStream var1, Socket var2) {
      serve$lambda$7(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$DdRTOL7PFTqqD6dz1fLa49eOyQY(boolean var0, JSONObject var1) {
      return tick$lambda$0(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$voNx_a_T0U8sOs8X2dfKRK5easM(Socket var0) {
      accept$lambda$4(var0);
   }

   private MapboxRelay() {
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private final void accept(ServerSocket var1) {
      while (!var1.isClosed()) {
         Socket var2;
         try {
            var2 = var1.accept();
         } catch (Exception var7) {
            break;
         }

         if (Intrinsics.areEqual(var2.getInetAddress().getHostAddress(), commaIp) && active.get() < 24) {
            pool.execute(new MapboxRelay$$ExternalSyntheticLambda0(var2));
         } else {
            try {
               Companion var3 = Result.Companion;
               MapboxRelay var9 = this;
               var2.close();
               KtCompat.resultOf(Unit.INSTANCE);
            } catch (Throwable var6) {
               Companion var8 = Result.Companion;
               KtCompat.resultOf(ResultKt.createFailure(var6));
               continue;
            }
         }
      }
   }

   private static final void accept$lambda$4(Socket var0) {
      MapboxRelay var1 = INSTANCE;
      Intrinsics.checkNotNull(var0);
      var1.serve(var0);
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   private final void pipe(InputStream var1, OutputStream var2) {
      byte[] var4 = new byte[32768];

      while (true) {
         int var3;
         try {
            var3 = var1.read(var4);
         } catch (Exception var6) {
            break;
         }

         if (var3 < 0) {
            break;
         }

         try {
            var2.write(var4, 0, var3);
            var2.flush();
         } catch (Exception var5) {
            break;
         }
      }
   }

   private final String readLine(InputStream var1) {
      StringBuilder var3 = new StringBuilder();

      while (var3.length() < 1024) {
         int var2 = var1.read();
         if (var2 < 0) {
            return null;
         }

         if (var2 == 10) {
            String var4 = var3.toString();
            Intrinsics.checkNotNullExpressionValue(var4, "toString(...)");
            return StringsKt.trimEnd(var4, new char[]{'\r'});
         }

         var3.append((char)var2);
      }

      return null;
   }

   private final void serve(Socket param1) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.NullPointerException: Cannot invoke "org.jetbrains.java.decompiler.code.cfg.ExceptionRangeCFG.isCircular()" because "range" is null
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.graphToStatement(DomHelper.java:84)
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:203)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 000: getstatic com/starpilot/starview/MapboxRelay.active Ljava/util/concurrent/atomic/AtomicInteger;
      // 003: invokevirtual java/util/concurrent/atomic/AtomicInteger.incrementAndGet ()I
      // 006: pop
      // 007: aconst_null
      // 008: astore 9
      // 00a: aconst_null
      // 00b: astore 8
      // 00d: aload 8
      // 00f: astore 7
      // 011: aload 9
      // 013: astore 6
      // 015: aload 1
      // 016: sipush 15000
      // 019: invokevirtual java/net/Socket.setSoTimeout (I)V
      // 01c: aload 8
      // 01e: astore 7
      // 020: aload 9
      // 022: astore 6
      // 024: aload 1
      // 025: invokevirtual java/net/Socket.getInputStream ()Ljava/io/InputStream;
      // 028: astore 11
      // 02a: aload 8
      // 02c: astore 7
      // 02e: aload 9
      // 030: astore 6
      // 032: aload 1
      // 033: invokevirtual java/net/Socket.getOutputStream ()Ljava/io/OutputStream;
      // 036: astore 12
      // 038: aload 8
      // 03a: astore 7
      // 03c: aload 9
      // 03e: astore 6
      // 040: aload 11
      // 042: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNull (Ljava/lang/Object;)V
      // 045: aload 8
      // 047: astore 7
      // 049: aload 9
      // 04b: astore 6
      // 04d: aload 0
      // 04e: aload 11
      // 050: invokespecial com/starpilot/starview/MapboxRelay.readLine (Ljava/io/InputStream;)Ljava/lang/String;
      // 053: astore 10
      // 055: aload 10
      // 057: ifnonnull 0ac
      // 05a: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 05d: astore 5
      // 05f: aload 0
      // 060: checkcast com/starpilot/starview/MapboxRelay
      // 063: astore 5
      // 065: aconst_null
      // 066: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 069: pop
      // 06a: goto 07d
      // 06d: astore 5
      // 06f: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 072: astore 6
      // 074: aload 5
      // 076: invokestatic kotlin/ResultKt.createFailure (Ljava/lang/Throwable;)Ljava/lang/Object;
      // 079: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 07c: pop
      // 07d: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 080: astore 5
      // 082: aload 0
      // 083: checkcast com/starpilot/starview/MapboxRelay
      // 086: astore 5
      // 088: aload 1
      // 089: invokevirtual java/net/Socket.close ()V
      // 08c: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 08f: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 092: pop
      // 093: goto 0a4
      // 096: astore 1
      // 097: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 09a: astore 5
      // 09c: aload 1
      // 09d: invokestatic kotlin/ResultKt.createFailure (Ljava/lang/Throwable;)Ljava/lang/Object;
      // 0a0: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 0a3: pop
      // 0a4: getstatic com/starpilot/starview/MapboxRelay.active Ljava/util/concurrent/atomic/AtomicInteger;
      // 0a7: invokevirtual java/util/concurrent/atomic/AtomicInteger.decrementAndGet ()I
      // 0aa: pop
      // 0ab: return
      // 0ac: aload 8
      // 0ae: astore 7
      // 0b0: aload 9
      // 0b2: astore 6
      // 0b4: aload 0
      // 0b5: aload 11
      // 0b7: invokespecial com/starpilot/starview/MapboxRelay.readLine (Ljava/io/InputStream;)Ljava/lang/String;
      // 0ba: astore 5
      // 0bc: aload 5
      // 0be: ifnonnull 114
      // 0c1: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 0c4: astore 5
      // 0c6: aload 0
      // 0c7: checkcast com/starpilot/starview/MapboxRelay
      // 0ca: astore 5
      // 0cc: aconst_null
      // 0cd: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 0d0: pop
      // 0d1: goto 0e4
      // 0d4: astore 6
      // 0d6: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 0d9: astore 5
      // 0db: aload 6
      // 0dd: invokestatic kotlin/ResultKt.createFailure (Ljava/lang/Throwable;)Ljava/lang/Object;
      // 0e0: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 0e3: pop
      // 0e4: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 0e7: astore 5
      // 0e9: aload 0
      // 0ea: checkcast com/starpilot/starview/MapboxRelay
      // 0ed: astore 5
      // 0ef: aload 1
      // 0f0: invokevirtual java/net/Socket.close ()V
      // 0f3: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 0f6: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 0f9: pop
      // 0fa: goto 10c
      // 0fd: astore 5
      // 0ff: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 102: astore 1
      // 103: aload 5
      // 105: invokestatic kotlin/ResultKt.createFailure (Ljava/lang/Throwable;)Ljava/lang/Object;
      // 108: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 10b: pop
      // 10c: getstatic com/starpilot/starview/MapboxRelay.active Ljava/util/concurrent/atomic/AtomicInteger;
      // 10f: invokevirtual java/util/concurrent/atomic/AtomicInteger.decrementAndGet ()I
      // 112: pop
      // 113: return
      // 114: aload 8
      // 116: astore 7
      // 118: aload 9
      // 11a: astore 6
      // 11c: aload 5
      // 11e: checkcast java/lang/CharSequence
      // 121: invokeinterface java/lang/CharSequence.length ()I 1
      // 126: istore 2
      // 127: bipush 0
      // 128: istore 3
      // 129: iload 2
      // 12a: ifne 132
      // 12d: bipush 1
      // 12e: istore 2
      // 12f: goto 134
      // 132: bipush 0
      // 133: istore 2
      // 134: iload 2
      // 135: ifeq 452
      // 138: aload 8
      // 13a: astore 7
      // 13c: aload 9
      // 13e: astore 6
      // 140: aload 10
      // 142: checkcast java/lang/CharSequence
      // 145: bipush 1
      // 146: anewarray 251
      // 149: dup
      // 14a: bipush 0
      // 14b: ldc " "
      // 14d: aastore
      // 14e: bipush 0
      // 14f: bipush 0
      // 150: bipush 6
      // 152: aconst_null
      // 153: invokestatic kotlin/text/StringsKt.split$default (Ljava/lang/CharSequence;[Ljava/lang/String;ZIILjava/lang/Object;)Ljava/util/List;
      // 156: astore 13
      // 158: aload 8
      // 15a: astore 7
      // 15c: aload 9
      // 15e: astore 6
      // 160: aload 13
      // 162: bipush 1
      // 163: invokestatic kotlin/collections/CollectionsKt.getOrNull (Ljava/util/List;I)Ljava/lang/Object;
      // 166: checkcast java/lang/String
      // 169: astore 10
      // 16b: aload 10
      // 16d: astore 5
      // 16f: aload 10
      // 171: ifnonnull 179
      // 174: ldc_w ""
      // 177: astore 5
      // 179: aload 8
      // 17b: astore 7
      // 17d: aload 9
      // 17f: astore 6
      // 181: aload 5
      // 183: ldc_w ":"
      // 186: aconst_null
      // 187: bipush 2
      // 188: aconst_null
      // 189: invokestatic kotlin/text/StringsKt.substringBeforeLast$default (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/Object;)Ljava/lang/String;
      // 18c: astore 10
      // 18e: aload 8
      // 190: astore 7
      // 192: aload 9
      // 194: astore 6
      // 196: aload 5
      // 198: ldc_w ":"
      // 19b: aconst_null
      // 19c: bipush 2
      // 19d: aconst_null
      // 19e: invokestatic kotlin/text/StringsKt.substringAfterLast$default (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/Object;)Ljava/lang/String;
      // 1a1: invokestatic kotlin/text/StringsKt.toIntOrNull (Ljava/lang/String;)Ljava/lang/Integer;
      // 1a4: astore 5
      // 1a6: iload 3
      // 1a7: istore 2
      // 1a8: aload 8
      // 1aa: astore 7
      // 1ac: aload 9
      // 1ae: astore 6
      // 1b0: aload 13
      // 1b2: invokestatic kotlin/collections/CollectionsKt.firstOrNull (Ljava/util/List;)Ljava/lang/Object;
      // 1b5: ldc_w "CONNECT"
      // 1b8: invokestatic kotlin/jvm/internal/Intrinsics.areEqual (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 1bb: ifeq 20e
      // 1be: aload 5
      // 1c0: ifnonnull 1c8
      // 1c3: iload 3
      // 1c4: istore 2
      // 1c5: goto 20e
      // 1c8: iload 3
      // 1c9: istore 2
      // 1ca: aload 8
      // 1cc: astore 7
      // 1ce: aload 9
      // 1d0: astore 6
      // 1d2: aload 5
      // 1d4: invokevirtual java/lang/Integer.intValue ()I
      // 1d7: sipush 443
      // 1da: if_icmpne 20e
      // 1dd: aload 8
      // 1df: astore 7
      // 1e1: aload 9
      // 1e3: astore 6
      // 1e5: aload 10
      // 1e7: ldc_w "mapbox.com"
      // 1ea: invokestatic kotlin/jvm/internal/Intrinsics.areEqual (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 1ed: ifne 20c
      // 1f0: aload 8
      // 1f2: astore 7
      // 1f4: aload 9
      // 1f6: astore 6
      // 1f8: aload 10
      // 1fa: ldc_w ".mapbox.com"
      // 1fd: bipush 0
      // 1fe: bipush 2
      // 1ff: aconst_null
      // 200: invokestatic kotlin/text/StringsKt.endsWith$default (Ljava/lang/String;Ljava/lang/String;ZILjava/lang/Object;)Z
      // 203: istore 4
      // 205: iload 3
      // 206: istore 2
      // 207: iload 4
      // 209: ifeq 20e
      // 20c: bipush 1
      // 20d: istore 2
      // 20e: iload 2
      // 20f: ifne 297
      // 212: aload 8
      // 214: astore 7
      // 216: aload 9
      // 218: astore 6
      // 21a: ldc_w "HTTP/1.1 403 Forbidden\r\nContent-Length: 0\r\n\r\n"
      // 21d: getstatic kotlin/text/Charsets.UTF_8 Ljava/nio/charset/Charset;
      // 220: invokevirtual java/lang/String.getBytes (Ljava/nio/charset/Charset;)[B
      // 223: astore 5
      // 225: aload 8
      // 227: astore 7
      // 229: aload 9
      // 22b: astore 6
      // 22d: aload 5
      // 22f: ldc_w "getBytes(...)"
      // 232: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 235: aload 8
      // 237: astore 7
      // 239: aload 9
      // 23b: astore 6
      // 23d: aload 12
      // 23f: aload 5
      // 241: invokevirtual java/io/OutputStream.write ([B)V
      // 244: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 247: astore 5
      // 249: aload 0
      // 24a: checkcast com/starpilot/starview/MapboxRelay
      // 24d: astore 5
      // 24f: aconst_null
      // 250: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 253: pop
      // 254: goto 267
      // 257: astore 5
      // 259: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 25c: astore 6
      // 25e: aload 5
      // 260: invokestatic kotlin/ResultKt.createFailure (Ljava/lang/Throwable;)Ljava/lang/Object;
      // 263: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 266: pop
      // 267: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 26a: astore 5
      // 26c: aload 0
      // 26d: checkcast com/starpilot/starview/MapboxRelay
      // 270: astore 5
      // 272: aload 1
      // 273: invokevirtual java/net/Socket.close ()V
      // 276: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 279: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 27c: pop
      // 27d: goto 28f
      // 280: astore 5
      // 282: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 285: astore 1
      // 286: aload 5
      // 288: invokestatic kotlin/ResultKt.createFailure (Ljava/lang/Throwable;)Ljava/lang/Object;
      // 28b: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 28e: pop
      // 28f: getstatic com/starpilot/starview/MapboxRelay.active Ljava/util/concurrent/atomic/AtomicInteger;
      // 292: invokevirtual java/util/concurrent/atomic/AtomicInteger.decrementAndGet ()I
      // 295: pop
      // 296: return
      // 297: aload 8
      // 299: astore 7
      // 29b: aload 9
      // 29d: astore 6
      // 29f: new java/net/Socket
      // 2a2: astore 5
      // 2a4: aload 8
      // 2a6: astore 7
      // 2a8: aload 9
      // 2aa: astore 6
      // 2ac: aload 5
      // 2ae: invokespecial java/net/Socket.<init> ()V
      // 2b1: aload 8
      // 2b3: astore 7
      // 2b5: aload 9
      // 2b7: astore 6
      // 2b9: new java/net/InetSocketAddress
      // 2bc: astore 13
      // 2be: aload 8
      // 2c0: astore 7
      // 2c2: aload 9
      // 2c4: astore 6
      // 2c6: aload 13
      // 2c8: aload 10
      // 2ca: sipush 443
      // 2cd: invokespecial java/net/InetSocketAddress.<init> (Ljava/lang/String;I)V
      // 2d0: aload 8
      // 2d2: astore 7
      // 2d4: aload 9
      // 2d6: astore 6
      // 2d8: aload 5
      // 2da: aload 13
      // 2dc: checkcast java/net/SocketAddress
      // 2df: sipush 8000
      // 2e2: invokevirtual java/net/Socket.connect (Ljava/net/SocketAddress;I)V
      // 2e5: aload 8
      // 2e7: astore 7
      // 2e9: aload 9
      // 2eb: astore 6
      // 2ed: aload 5
      // 2ef: ldc_w 60000
      // 2f2: invokevirtual java/net/Socket.setSoTimeout (I)V
      // 2f5: aload 5
      // 2f7: astore 7
      // 2f9: aload 5
      // 2fb: astore 6
      // 2fd: ldc_w "HTTP/1.1 200 Connection established\r\n\r\n"
      // 300: getstatic kotlin/text/Charsets.UTF_8 Ljava/nio/charset/Charset;
      // 303: invokevirtual java/lang/String.getBytes (Ljava/nio/charset/Charset;)[B
      // 306: astore 8
      // 308: aload 5
      // 30a: astore 7
      // 30c: aload 5
      // 30e: astore 6
      // 310: aload 8
      // 312: ldc_w "getBytes(...)"
      // 315: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 318: aload 5
      // 31a: astore 7
      // 31c: aload 5
      // 31e: astore 6
      // 320: aload 12
      // 322: aload 8
      // 324: invokevirtual java/io/OutputStream.write ([B)V
      // 327: aload 5
      // 329: astore 7
      // 32b: aload 5
      // 32d: astore 6
      // 32f: aload 12
      // 331: invokevirtual java/io/OutputStream.flush ()V
      // 334: aload 5
      // 336: astore 7
      // 338: aload 5
      // 33a: astore 6
      // 33c: aload 1
      // 33d: ldc_w 60000
      // 340: invokevirtual java/net/Socket.setSoTimeout (I)V
      // 343: aload 5
      // 345: astore 7
      // 347: aload 5
      // 349: astore 6
      // 34b: getstatic com/starpilot/starview/MapboxRelay.served I
      // 34e: bipush 1
      // 34f: iadd
      // 350: putstatic com/starpilot/starview/MapboxRelay.served I
      // 353: aload 5
      // 355: astore 7
      // 357: aload 5
      // 359: astore 6
      // 35b: getstatic com/starpilot/starview/MapboxRelay.pool Ljava/util/concurrent/ExecutorService;
      // 35e: astore 9
      // 360: aload 5
      // 362: astore 7
      // 364: aload 5
      // 366: astore 6
      // 368: new com/starpilot/starview/MapboxRelay$$ExternalSyntheticLambda3
      // 36b: astore 8
      // 36d: aload 5
      // 36f: astore 7
      // 371: aload 5
      // 373: astore 6
      // 375: aload 8
      // 377: aload 5
      // 379: aload 12
      // 37b: aload 1
      // 37c: invokespecial com/starpilot/starview/MapboxRelay$$ExternalSyntheticLambda3.<init> (Ljava/net/Socket;Ljava/io/OutputStream;Ljava/net/Socket;)V
      // 37f: aload 5
      // 381: astore 7
      // 383: aload 5
      // 385: astore 6
      // 387: aload 9
      // 389: aload 8
      // 38b: invokeinterface java/util/concurrent/ExecutorService.submit (Ljava/lang/Runnable;)Ljava/util/concurrent/Future; 2
      // 390: astore 8
      // 392: aload 5
      // 394: astore 7
      // 396: aload 5
      // 398: astore 6
      // 39a: aload 5
      // 39c: invokevirtual java/net/Socket.getOutputStream ()Ljava/io/OutputStream;
      // 39f: astore 9
      // 3a1: aload 5
      // 3a3: astore 7
      // 3a5: aload 5
      // 3a7: astore 6
      // 3a9: aload 9
      // 3ab: ldc_w "getOutputStream(...)"
      // 3ae: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 3b1: aload 5
      // 3b3: astore 7
      // 3b5: aload 5
      // 3b7: astore 6
      // 3b9: aload 0
      // 3ba: aload 11
      // 3bc: aload 9
      // 3be: invokespecial com/starpilot/starview/MapboxRelay.pipe (Ljava/io/InputStream;Ljava/io/OutputStream;)V
      // 3c1: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 3c4: astore 6
      // 3c6: aload 0
      // 3c7: checkcast com/starpilot/starview/MapboxRelay
      // 3ca: astore 6
      // 3cc: aload 5
      // 3ce: invokevirtual java/net/Socket.shutdownOutput ()V
      // 3d1: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 3d4: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 3d7: pop
      // 3d8: goto 3fb
      // 3db: astore 10
      // 3dd: aload 5
      // 3df: astore 7
      // 3e1: aload 5
      // 3e3: astore 6
      // 3e5: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 3e8: astore 9
      // 3ea: aload 5
      // 3ec: astore 7
      // 3ee: aload 5
      // 3f0: astore 6
      // 3f2: aload 10
      // 3f4: invokestatic kotlin/ResultKt.createFailure (Ljava/lang/Throwable;)Ljava/lang/Object;
      // 3f7: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 3fa: pop
      // 3fb: aload 5
      // 3fd: astore 7
      // 3ff: aload 5
      // 401: astore 6
      // 403: aload 8
      // 405: invokeinterface java/util/concurrent/Future.get ()Ljava/lang/Object; 1
      // 40a: pop
      // 40b: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 40e: astore 6
      // 410: aload 0
      // 411: checkcast com/starpilot/starview/MapboxRelay
      // 414: astore 6
      // 416: aload 5
      // 418: invokevirtual java/net/Socket.close ()V
      // 41b: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 41e: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 421: pop
      // 422: goto 435
      // 425: astore 5
      // 427: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 42a: astore 6
      // 42c: aload 5
      // 42e: invokestatic kotlin/ResultKt.createFailure (Ljava/lang/Throwable;)Ljava/lang/Object;
      // 431: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 434: pop
      // 435: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 438: astore 5
      // 43a: aload 0
      // 43b: checkcast com/starpilot/starview/MapboxRelay
      // 43e: astore 5
      // 440: aload 1
      // 441: invokevirtual java/net/Socket.close ()V
      // 444: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 447: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 44a: pop
      // 44b: goto 519
      // 44e: astore 1
      // 44f: goto 50c
      // 452: goto 0ac
      // 455: astore 6
      // 457: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 45a: astore 5
      // 45c: aload 7
      // 45e: checkcast java/net/Socket
      // 461: astore 5
      // 463: aload 5
      // 465: ifnull 475
      // 468: aload 5
      // 46a: invokevirtual java/net/Socket.close ()V
      // 46d: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 470: astore 5
      // 472: goto 478
      // 475: aconst_null
      // 476: astore 5
      // 478: aload 5
      // 47a: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 47d: pop
      // 47e: goto 491
      // 481: astore 7
      // 483: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 486: astore 5
      // 488: aload 7
      // 48a: invokestatic kotlin/ResultKt.createFailure (Ljava/lang/Throwable;)Ljava/lang/Object;
      // 48d: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 490: pop
      // 491: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 494: astore 5
      // 496: aload 1
      // 497: invokevirtual java/net/Socket.close ()V
      // 49a: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 49d: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 4a0: pop
      // 4a1: goto 4b2
      // 4a4: astore 1
      // 4a5: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 4a8: astore 5
      // 4aa: aload 1
      // 4ab: invokestatic kotlin/ResultKt.createFailure (Ljava/lang/Throwable;)Ljava/lang/Object;
      // 4ae: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 4b1: pop
      // 4b2: getstatic com/starpilot/starview/MapboxRelay.active Ljava/util/concurrent/atomic/AtomicInteger;
      // 4b5: invokevirtual java/util/concurrent/atomic/AtomicInteger.decrementAndGet ()I
      // 4b8: pop
      // 4b9: aload 6
      // 4bb: athrow
      // 4bc: astore 5
      // 4be: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 4c1: astore 5
      // 4c3: aload 6
      // 4c5: checkcast java/net/Socket
      // 4c8: astore 5
      // 4ca: aload 5
      // 4cc: ifnull 4dc
      // 4cf: aload 5
      // 4d1: invokevirtual java/net/Socket.close ()V
      // 4d4: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 4d7: astore 5
      // 4d9: goto 4df
      // 4dc: aconst_null
      // 4dd: astore 5
      // 4df: aload 5
      // 4e1: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 4e4: pop
      // 4e5: goto 4f8
      // 4e8: astore 5
      // 4ea: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 4ed: astore 6
      // 4ef: aload 5
      // 4f1: invokestatic kotlin/ResultKt.createFailure (Ljava/lang/Throwable;)Ljava/lang/Object;
      // 4f4: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 4f7: pop
      // 4f8: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 4fb: astore 5
      // 4fd: aload 1
      // 4fe: invokevirtual java/net/Socket.close ()V
      // 501: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 504: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 507: pop
      // 508: goto 519
      // 50b: astore 1
      // 50c: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 50f: astore 5
      // 511: aload 1
      // 512: invokestatic kotlin/ResultKt.createFailure (Ljava/lang/Throwable;)Ljava/lang/Object;
      // 515: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 518: pop
      // 519: getstatic com/starpilot/starview/MapboxRelay.active Ljava/util/concurrent/atomic/AtomicInteger;
      // 51c: invokevirtual java/util/concurrent/atomic/AtomicInteger.decrementAndGet ()I
      // 51f: pop
      // 520: return
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private static final void serve$lambda$7(Socket var0, OutputStream var1, Socket var2) {
      MapboxRelay var3 = INSTANCE;
      InputStream var6 = var0.getInputStream();
      Intrinsics.checkNotNullExpressionValue(var6, "getInputStream(...)");
      Intrinsics.checkNotNull(var1);
      var3.pipe(var6, var1);
      MapboxRelay var7 = INSTANCE;

      try {
         Companion var8 = Result.Companion;
         var2.shutdownOutput();
         KtCompat.resultOf(Unit.INSTANCE);
      } catch (Throwable var5) {
         Companion var9 = Result.Companion;
         KtCompat.resultOf(ResultKt.createFailure(var5));
         return;
      }
   }

   private final void start() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: parsing failure!
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 00: aload 0
      // 01: monitorenter
      // 02: getstatic com/starpilot/starview/MapboxRelay.server Ljava/net/ServerSocket;
      // 05: astore 4
      // 07: bipush 0
      // 08: istore 2
      // 09: iload 2
      // 0a: istore 1
      // 0b: aload 4
      // 0d: ifnull 1e
      // 10: aload 4
      // 12: invokevirtual java/net/ServerSocket.isClosed ()Z
      // 15: istore 3
      // 16: iload 2
      // 17: istore 1
      // 18: iload 3
      // 19: ifne 1e
      // 1c: bipush 1
      // 1d: istore 1
      // 1e: iload 1
      // 1f: ifeq 25
      // 22: aload 0
      // 23: monitorexit
      // 24: return
      // 25: new java/net/ServerSocket
      // 28: astore 4
      // 2a: aload 4
      // 2c: invokespecial java/net/ServerSocket.<init> ()V
      // 2f: aload 4
      // 31: bipush 1
      // 32: invokevirtual java/net/ServerSocket.setReuseAddress (Z)V
      // 35: new java/net/InetSocketAddress
      // 38: astore 5
      // 3a: aload 5
      // 3c: sipush 8099
      // 3f: invokespecial java/net/InetSocketAddress.<init> (I)V
      // 42: aload 4
      // 44: aload 5
      // 46: checkcast java/net/SocketAddress
      // 49: bipush 16
      // 4b: invokevirtual java/net/ServerSocket.bind (Ljava/net/SocketAddress;I)V
      // 4e: aload 4
      // 50: putstatic com/starpilot/starview/MapboxRelay.server Ljava/net/ServerSocket;
      // 53: new java/lang/Thread
      // 56: astore 6
      // 58: new com/starpilot/starview/MapboxRelay$$ExternalSyntheticLambda2
      // 5b: astore 5
      // 5d: aload 5
      // 5f: aload 4
      // 61: invokespecial com/starpilot/starview/MapboxRelay$$ExternalSyntheticLambda2.<init> (Ljava/net/ServerSocket;)V
      // 64: aload 6
      // 66: aload 5
      // 68: ldc_w "starview-relay"
      // 6b: invokespecial java/lang/Thread.<init> (Ljava/lang/Runnable;Ljava/lang/String;)V
      // 6e: aload 6
      // 70: bipush 1
      // 71: invokevirtual java/lang/Thread.setDaemon (Z)V
      // 74: aload 6
      // 76: invokevirtual java/lang/Thread.start ()V
      // 79: ldc "StarView/Relay"
      // 7b: ldc_w "relay on :8099"
      // 7e: invokestatic android/util/Log.i (Ljava/lang/String;Ljava/lang/String;)I
      // 81: pop
      // 82: goto a7
      // 85: astore 4
      // 87: new java/lang/StringBuilder
      // 8a: astore 5
      // 8c: aload 5
      // 8e: invokespecial java/lang/StringBuilder.<init> ()V
      // 91: ldc "StarView/Relay"
      // 93: aload 5
      // 95: ldc_w "relay: "
      // 98: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 9b: aload 4
      // 9d: invokevirtual java/lang/StringBuilder.append (Ljava/lang/Object;)Ljava/lang/StringBuilder;
      // a0: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // a3: invokestatic android/util/Log.w (Ljava/lang/String;Ljava/lang/String;)I
      // a6: pop
      // a7: aload 0
      // a8: monitorexit
      // a9: return
      // aa: astore 4
      // ac: aload 0
      // ad: monitorexit
      // ae: aload 4
      // b0: athrow
   }

   private static final void start$lambda$1(ServerSocket var0) {
      INSTANCE.accept(var0);
   }

   private static final Unit tick$lambda$0(boolean var0, JSONObject var1) {
      Intrinsics.checkNotNullParameter(var1, "r");
      if (var1.optBoolean("ok")) {
         announced = var0;
      }

      return Unit.INSTANCE;
   }

   public final String getCommaIp() {
      return commaIp;
   }

   public final int getServed() {
      return served;
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   public final boolean online(Context var1) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      boolean var3 = false;

      try {
         ConnectivityManager var7 = (ConnectivityManager)var1.getSystemService("connectivity");
         Intrinsics.checkNotNull(var7, "null cannot be cast to non-null type android.net.ConnectivityManager");
         var7 = var7;
         var9 = var7.getNetworkCapabilities(var7.getActiveNetwork());
      } catch (Exception var6) {
         return var3;
      }

      boolean var2 = var3;
      if (var9 != null) {
         var2 = var3;

         boolean var4;
         try {
            if (!var9.hasCapability(12)) {
               return var2;
            }

            var4 = var9.hasCapability(16);
         } catch (Exception var5) {
            return var3;
         }

         var2 = var3;
         if (var4) {
            var2 = true;
         }
      }

      return var2;
   }

   public final void setCommaIp(String var1) {
      commaIp = var1;
   }

   public final void tick(Context var1, DataClient var2, String var3, long var4) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      Intrinsics.checkNotNullParameter(var2, "data");
      if (var2.getConnected() && var3 != null) {
         label37: {
            String var8;
            try {
               var8 = InetAddress.getByName(var3).getHostAddress();
            } catch (Exception var9) {
               break label37;
            }

            var3 = var8;
         }

         commaIp = var3;
         if (var4 - lastAnnounceMs >= 30000L) {
            lastAnnounceMs = var4;
            boolean var7 = this.online(var1);
            if (var7) {
               this.start();
            }

            if (var7 || announced) {
               short var6;
               if (var7) {
                  var6 = 8099;
               } else {
                  var6 = 0;
               }

               var2.request("nav_relay", MapsKt.mapOf(TuplesKt.to("port", Integer.valueOf(var6))), new MapboxRelay$$ExternalSyntheticLambda1(var7));
            }
         }
      }
   }
}
