package com.starpilot.starview;

import android.media.Image;
import android.media.MediaCodec;
import android.media.MediaFormat;
import android.media.MediaCodec.BufferInfo;
import android.view.Surface;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okhttp3.OkHttpClient.Builder;
import okio.ByteString;

public final class VideoClient {
   public static final VideoClient.Companion Companion = new VideoClient.Companion(null);
   public static final int HDR = 28;
   private static final String TAG = "StarView/Video";
   private int cfgH;
   private boolean cfgHevc;
   private int cfgW;
   private volatile double decodeMs;
   private Thread decoderThread;
   private volatile long dropped;
   private volatile double fps;
   private long fpsT0;
   private int fpsWin;
   private volatile long frames;
   private final OkHttpClient http;
   private volatile int lastH;
   private volatile int lastW;
   private volatile boolean needKey;
   private volatile Function1<? super Image, Unit> onImage;
   private final Function2<Integer, Integer, Unit> onSize;
   private final Function1<String, Unit> onStatus;
   private final LinkedBlockingQueue<VideoClient.Frame> queue;
   private volatile boolean running;
   private volatile Surface surface;
   private WebSocket ws;

   // $VF: synthetic method
   public static void $r8$lambda$dT_eQNtVFJiN_ROfdGt6fW1j1JM/* $VF was: $r8$lambda$dT_eQNtVFJiN_ROfdGt6fW1j1JM*/(VideoClient var0) {
      connect$lambda$0(var0);
   }

   public VideoClient(Function1<? super String, Unit> var1, Function2<? super Integer, ? super Integer, Unit> var2) {
      Intrinsics.checkNotNullParameter(var1, "onStatus");
      Intrinsics.checkNotNullParameter(var2, "onSize");
      super();
      this.onStatus = var1;
      this.onSize = var2;
      this.http = new Builder().pingInterval(5L, TimeUnit.SECONDS).readTimeout(0L, TimeUnit.MILLISECONDS).build();
      this.queue = new LinkedBlockingQueue<>(6);
      this.fpsT0 = System.nanoTime();
      this.needKey = true;
   }

   private static final void connect$lambda$0(VideoClient var0) {
      var0.decodeLoop();
   }

   private final void decodeLoop() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      //
      // Bytecode:
      // 000: new android/media/MediaCodec$BufferInfo
      // 003: dup
      // 004: invokespecial android/media/MediaCodec$BufferInfo.<init> ()V
      // 007: astore 12
      // 009: aconst_null
      // 00a: astore 9
      // 00c: aload 9
      // 00e: astore 8
      // 010: aload 9
      // 012: astore 10
      // 014: aload 0
      // 015: getfield com/starpilot/starview/VideoClient.running Z
      // 018: ifeq 6ca
      // 01b: aload 9
      // 01d: astore 8
      // 01f: aload 9
      // 021: astore 10
      // 023: aload 0
      // 024: getfield com/starpilot/starview/VideoClient.queue Ljava/util/concurrent/LinkedBlockingQueue;
      // 027: ldc2_w 200
      // 02a: getstatic java/util/concurrent/TimeUnit.MILLISECONDS Ljava/util/concurrent/TimeUnit;
      // 02d: invokevirtual java/util/concurrent/LinkedBlockingQueue.poll (JLjava/util/concurrent/TimeUnit;)Ljava/lang/Object;
      // 030: checkcast com/starpilot/starview/VideoClient$Frame
      // 033: astore 13
      // 035: aload 13
      // 037: ifnonnull 061
      // 03a: aload 9
      // 03c: astore 8
      // 03e: aload 9
      // 040: astore 10
      // 042: aload 0
      // 043: checkcast com/starpilot/starview/VideoClient
      // 046: astore 11
      // 048: aload 9
      // 04a: ifnull 00c
      // 04d: aload 9
      // 04f: astore 8
      // 051: aload 9
      // 053: astore 10
      // 055: aload 11
      // 057: aload 9
      // 059: aload 12
      // 05b: invokespecial com/starpilot/starview/VideoClient.drain (Landroid/media/MediaCodec;Landroid/media/MediaCodec$BufferInfo;)V
      // 05e: goto 00c
      // 061: aload 9
      // 063: ifnull 170
      // 066: aload 9
      // 068: astore 8
      // 06a: aload 9
      // 06c: astore 10
      // 06e: aload 13
      // 070: invokevirtual com/starpilot/starview/VideoClient$Frame.getKey ()Z
      // 073: ifeq 170
      // 076: aload 9
      // 078: astore 8
      // 07a: aload 9
      // 07c: astore 10
      // 07e: aload 13
      // 080: invokevirtual com/starpilot/starview/VideoClient$Frame.getW ()I
      // 083: ifle 170
      // 086: aload 9
      // 088: astore 8
      // 08a: aload 9
      // 08c: astore 10
      // 08e: aload 13
      // 090: invokevirtual com/starpilot/starview/VideoClient$Frame.getH ()I
      // 093: ifle 170
      // 096: aload 9
      // 098: astore 8
      // 09a: aload 9
      // 09c: astore 10
      // 09e: aload 13
      // 0a0: invokevirtual com/starpilot/starview/VideoClient$Frame.getW ()I
      // 0a3: aload 0
      // 0a4: getfield com/starpilot/starview/VideoClient.cfgW I
      // 0a7: if_icmpne 0e2
      // 0aa: aload 9
      // 0ac: astore 8
      // 0ae: aload 9
      // 0b0: astore 10
      // 0b2: aload 13
      // 0b4: invokevirtual com/starpilot/starview/VideoClient$Frame.getH ()I
      // 0b7: aload 0
      // 0b8: getfield com/starpilot/starview/VideoClient.cfgH I
      // 0bb: if_icmpne 0e2
      // 0be: aload 9
      // 0c0: astore 8
      // 0c2: aload 9
      // 0c4: astore 10
      // 0c6: aload 13
      // 0c8: invokevirtual com/starpilot/starview/VideoClient$Frame.getHevc ()Z
      // 0cb: istore 5
      // 0cd: aload 9
      // 0cf: astore 8
      // 0d1: aload 9
      // 0d3: astore 10
      // 0d5: aload 0
      // 0d6: getfield com/starpilot/starview/VideoClient.cfgHevc Z
      // 0d9: istore 4
      // 0db: iload 5
      // 0dd: iload 4
      // 0df: if_icmpeq 170
      // 0e2: aload 9
      // 0e4: astore 8
      // 0e6: aload 9
      // 0e8: invokevirtual android/media/MediaCodec.stop ()V
      // 0eb: aload 9
      // 0ed: astore 8
      // 0ef: aload 9
      // 0f1: invokevirtual android/media/MediaCodec.release ()V
      // 0f4: goto 0f9
      // 0f7: astore 8
      // 0f9: aconst_null
      // 0fa: astore 11
      // 0fc: aconst_null
      // 0fd: astore 9
      // 0ff: aload 9
      // 101: astore 8
      // 103: aload 11
      // 105: astore 10
      // 107: aload 0
      // 108: getfield com/starpilot/starview/VideoClient.onStatus Lkotlin/jvm/functions/Function1;
      // 10b: astore 14
      // 10d: aload 9
      // 10f: astore 8
      // 111: aload 11
      // 113: astore 10
      // 115: aload 13
      // 117: invokevirtual com/starpilot/starview/VideoClient$Frame.getW ()I
      // 11a: istore 1
      // 11b: aload 9
      // 11d: astore 8
      // 11f: aload 11
      // 121: astore 10
      // 123: aload 13
      // 125: invokevirtual com/starpilot/starview/VideoClient$Frame.getH ()I
      // 128: istore 2
      // 129: aload 9
      // 12b: astore 8
      // 12d: aload 11
      // 12f: astore 10
      // 131: new java/lang/StringBuilder
      // 134: astore 15
      // 136: aload 9
      // 138: astore 8
      // 13a: aload 11
      // 13c: astore 10
      // 13e: aload 15
      // 140: invokespecial java/lang/StringBuilder.<init> ()V
      // 143: aload 9
      // 145: astore 8
      // 147: aload 11
      // 149: astore 10
      // 14b: aload 14
      // 14d: aload 15
      // 14f: ldc "video: new stream "
      // 151: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 154: iload 1
      // 155: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
      // 158: ldc "x"
      // 15a: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 15d: iload 2
      // 15e: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
      // 161: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 164: invokeinterface kotlin/jvm/functions/Function1.invoke (Ljava/lang/Object;)Ljava/lang/Object; 2
      // 169: pop
      // 16a: aconst_null
      // 16b: astore 8
      // 16d: goto 174
      // 170: aload 9
      // 172: astore 8
      // 174: aload 8
      // 176: ifnonnull 513
      // 179: aload 13
      // 17b: invokevirtual com/starpilot/starview/VideoClient$Frame.getKey ()Z
      // 17e: ifeq 4fe
      // 181: aload 13
      // 183: invokevirtual com/starpilot/starview/VideoClient$Frame.getHevc ()Z
      // 186: ifeq 191
      // 189: ldc_w "video/hevc"
      // 18c: astore 10
      // 18e: goto 196
      // 191: ldc_w "video/avc"
      // 194: astore 10
      // 196: aload 13
      // 198: invokevirtual com/starpilot/starview/VideoClient$Frame.getHevc ()Z
      // 19b: ifeq 2fe
      // 19e: bipush 3
      // 19f: anewarray 269
      // 1a2: dup
      // 1a3: bipush 0
      // 1a4: bipush 32
      // 1a6: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 1a9: aastore
      // 1aa: dup
      // 1ab: bipush 1
      // 1ac: bipush 33
      // 1ae: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 1b1: aastore
      // 1b2: dup
      // 1b3: bipush 2
      // 1b4: bipush 34
      // 1b6: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 1b9: aastore
      // 1ba: invokestatic kotlin/collections/CollectionsKt.listOf ([Ljava/lang/Object;)Ljava/util/List;
      // 1bd: checkcast java/lang/Iterable
      // 1c0: astore 9
      // 1c2: new java/util/ArrayList
      // 1c5: astore 11
      // 1c7: aload 11
      // 1c9: aload 9
      // 1cb: bipush 10
      // 1cd: invokestatic kotlin/collections/CollectionsKt.collectionSizeOrDefault (Ljava/lang/Iterable;I)I
      // 1d0: invokespecial java/util/ArrayList.<init> (I)V
      // 1d3: aload 11
      // 1d5: checkcast java/util/Collection
      // 1d8: astore 14
      // 1da: aload 9
      // 1dc: invokeinterface java/lang/Iterable.iterator ()Ljava/util/Iterator; 1
      // 1e1: astore 15
      // 1e3: aload 15
      // 1e5: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 1ea: ifeq 223
      // 1ed: aload 15
      // 1ef: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 1f4: checkcast java/lang/Number
      // 1f7: invokevirtual java/lang/Number.intValue ()I
      // 1fa: istore 1
      // 1fb: aload 0
      // 1fc: aload 13
      // 1fe: invokevirtual com/starpilot/starview/VideoClient$Frame.getData ()[B
      // 201: iload 1
      // 202: bipush 1
      // 203: invokespecial com/starpilot/starview/VideoClient.findNal ([BIZ)[B
      // 206: astore 11
      // 208: aload 11
      // 20a: astore 9
      // 20c: aload 11
      // 20e: ifnonnull 216
      // 211: bipush 0
      // 212: newarray 8
      // 214: astore 9
      // 216: aload 14
      // 218: aload 9
      // 21a: invokeinterface java/util/Collection.add (Ljava/lang/Object;)Z 2
      // 21f: pop
      // 220: goto 1e3
      // 223: aload 14
      // 225: checkcast java/util/List
      // 228: astore 9
      // 22a: aload 9
      // 22c: checkcast java/lang/Iterable
      // 22f: astore 11
      // 231: aload 11
      // 233: instanceof java/util/Collection
      // 236: ifeq 24b
      // 239: aload 11
      // 23b: checkcast java/util/Collection
      // 23e: invokeinterface java/util/Collection.isEmpty ()Z 1
      // 243: ifeq 24b
      // 246: bipush 1
      // 247: istore 1
      // 248: goto 27e
      // 24b: aload 11
      // 24d: invokeinterface java/lang/Iterable.iterator ()Ljava/util/Iterator; 1
      // 252: astore 11
      // 254: aload 11
      // 256: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 25b: ifeq 27c
      // 25e: aload 11
      // 260: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 265: checkcast [B
      // 268: arraylength
      // 269: ifne 271
      // 26c: bipush 1
      // 26d: istore 1
      // 26e: goto 273
      // 271: bipush 0
      // 272: istore 1
      // 273: iload 1
      // 274: ifeq 254
      // 277: bipush 0
      // 278: istore 1
      // 279: goto 27e
      // 27c: bipush 1
      // 27d: istore 1
      // 27e: iload 1
      // 27f: ifeq 285
      // 282: goto 288
      // 285: aconst_null
      // 286: astore 9
      // 288: aload 9
      // 28a: ifnull 2fb
      // 28d: aload 9
      // 28f: checkcast java/lang/Iterable
      // 292: astore 9
      // 294: aload 9
      // 296: invokeinterface java/lang/Iterable.iterator ()Ljava/util/Iterator; 1
      // 29b: astore 11
      // 29d: aload 11
      // 29f: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 2a4: ifeq 2eb
      // 2a7: aload 11
      // 2a9: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 2ae: astore 9
      // 2b0: aload 11
      // 2b2: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 2b7: ifeq 2d5
      // 2ba: aload 11
      // 2bc: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 2c1: checkcast [B
      // 2c4: astore 14
      // 2c6: aload 9
      // 2c8: checkcast [B
      // 2cb: aload 14
      // 2cd: invokestatic kotlin/collections/ArraysKt.plus ([B[B)[B
      // 2d0: astore 9
      // 2d2: goto 2b0
      // 2d5: aload 9
      // 2d7: checkcast [B
      // 2da: astore 9
      // 2dc: aload 9
      // 2de: ifnull 2fb
      // 2e1: aload 9
      // 2e3: invokestatic kotlin/collections/CollectionsKt.listOf (Ljava/lang/Object;)Ljava/util/List;
      // 2e6: astore 9
      // 2e8: goto 344
      // 2eb: new java/lang/UnsupportedOperationException
      // 2ee: astore 9
      // 2f0: aload 9
      // 2f2: ldc_w "Empty collection can't be reduced."
      // 2f5: invokespecial java/lang/UnsupportedOperationException.<init> (Ljava/lang/String;)V
      // 2f8: aload 9
      // 2fa: athrow
      // 2fb: goto 4fe
      // 2fe: aload 0
      // 2ff: aload 13
      // 301: invokevirtual com/starpilot/starview/VideoClient$Frame.getData ()[B
      // 304: bipush 7
      // 306: bipush 0
      // 307: bipush 4
      // 308: aconst_null
      // 309: invokestatic com/starpilot/starview/VideoClient.findNal$default (Lcom/starpilot/starview/VideoClient;[BIZILjava/lang/Object;)[B
      // 30c: astore 9
      // 30e: aload 9
      // 310: ifnonnull 316
      // 313: goto 4fe
      // 316: aload 0
      // 317: aload 13
      // 319: invokevirtual com/starpilot/starview/VideoClient$Frame.getData ()[B
      // 31c: bipush 8
      // 31e: bipush 0
      // 31f: bipush 4
      // 320: aconst_null
      // 321: invokestatic com/starpilot/starview/VideoClient.findNal$default (Lcom/starpilot/starview/VideoClient;[BIZILjava/lang/Object;)[B
      // 324: astore 11
      // 326: aload 11
      // 328: ifnonnull 32e
      // 32b: goto 313
      // 32e: bipush 2
      // 32f: anewarray 326
      // 332: dup
      // 333: bipush 0
      // 334: aload 9
      // 336: aastore
      // 337: dup
      // 338: bipush 1
      // 339: aload 11
      // 33b: aastore
      // 33c: checkcast [Ljava/lang/Object;
      // 33f: invokestatic kotlin/collections/CollectionsKt.listOf ([Ljava/lang/Object;)Ljava/util/List;
      // 342: astore 9
      // 344: aload 10
      // 346: invokestatic android/media/MediaCodec.createDecoderByType (Ljava/lang/String;)Landroid/media/MediaCodec;
      // 349: astore 11
      // 34b: bipush 0
      // 34c: istore 4
      // 34e: aload 10
      // 350: aload 13
      // 352: invokevirtual com/starpilot/starview/VideoClient$Frame.getW ()I
      // 355: aload 13
      // 357: invokevirtual com/starpilot/starview/VideoClient$Frame.getH ()I
      // 35a: invokestatic android/media/MediaFormat.createVideoFormat (Ljava/lang/String;II)Landroid/media/MediaFormat;
      // 35d: astore 10
      // 35f: aload 10
      // 361: ldc_w "createVideoFormat(...)"
      // 364: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 367: aload 9
      // 369: checkcast java/lang/Iterable
      // 36c: astore 14
      // 36e: bipush 0
      // 36f: istore 1
      // 370: aload 14
      // 372: invokeinterface java/lang/Iterable.iterator ()Ljava/util/Iterator; 1
      // 377: astore 14
      // 379: aload 14
      // 37b: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 380: ifeq 3c3
      // 383: aload 14
      // 385: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 38a: astore 15
      // 38c: iload 1
      // 38d: ifge 393
      // 390: invokestatic kotlin/collections/CollectionsKt.throwIndexOverflow ()V
      // 393: aload 15
      // 395: checkcast [B
      // 398: astore 15
      // 39a: new java/lang/StringBuilder
      // 39d: astore 16
      // 39f: aload 16
      // 3a1: invokespecial java/lang/StringBuilder.<init> ()V
      // 3a4: aload 10
      // 3a6: aload 16
      // 3a8: ldc_w "csd-"
      // 3ab: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 3ae: iload 1
      // 3af: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
      // 3b2: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 3b5: aload 15
      // 3b7: invokestatic java/nio/ByteBuffer.wrap ([B)Ljava/nio/ByteBuffer;
      // 3ba: invokevirtual android/media/MediaFormat.setByteBuffer (Ljava/lang/String;Ljava/nio/ByteBuffer;)V
      // 3bd: iinc 1 1
      // 3c0: goto 379
      // 3c3: getstatic android/os/Build$VERSION.SDK_INT I
      // 3c6: bipush 30
      // 3c8: if_icmplt 3d4
      // 3cb: aload 10
      // 3cd: ldc_w "low-latency"
      // 3d0: bipush 1
      // 3d1: invokevirtual android/media/MediaFormat.setInteger (Ljava/lang/String;I)V
      // 3d4: aload 10
      // 3d6: ldc_w "max-input-size"
      // 3d9: ldc_w 2097152
      // 3dc: invokevirtual android/media/MediaFormat.setInteger (Ljava/lang/String;I)V
      // 3df: aload 11
      // 3e1: aload 10
      // 3e3: aload 0
      // 3e4: getfield com/starpilot/starview/VideoClient.surface Landroid/view/Surface;
      // 3e7: aconst_null
      // 3e8: bipush 0
      // 3e9: invokevirtual android/media/MediaCodec.configure (Landroid/media/MediaFormat;Landroid/view/Surface;Landroid/media/MediaCrypto;I)V
      // 3ec: aload 11
      // 3ee: invokevirtual android/media/MediaCodec.start ()V
      // 3f1: aload 11
      // 3f3: astore 8
      // 3f5: aload 11
      // 3f7: astore 10
      // 3f9: aload 0
      // 3fa: aload 13
      // 3fc: invokevirtual com/starpilot/starview/VideoClient$Frame.getW ()I
      // 3ff: putfield com/starpilot/starview/VideoClient.cfgW I
      // 402: aload 11
      // 404: astore 8
      // 406: aload 11
      // 408: astore 10
      // 40a: aload 0
      // 40b: aload 13
      // 40d: invokevirtual com/starpilot/starview/VideoClient$Frame.getH ()I
      // 410: putfield com/starpilot/starview/VideoClient.cfgH I
      // 413: aload 11
      // 415: astore 8
      // 417: aload 11
      // 419: astore 10
      // 41b: aload 0
      // 41c: aload 13
      // 41e: invokevirtual com/starpilot/starview/VideoClient$Frame.getHevc ()Z
      // 421: putfield com/starpilot/starview/VideoClient.cfgHevc Z
      // 424: aload 11
      // 426: astore 8
      // 428: aload 11
      // 42a: astore 10
      // 42c: aload 0
      // 42d: aload 13
      // 42f: invokevirtual com/starpilot/starview/VideoClient$Frame.getW ()I
      // 432: putfield com/starpilot/starview/VideoClient.lastW I
      // 435: aload 11
      // 437: astore 8
      // 439: aload 11
      // 43b: astore 10
      // 43d: aload 0
      // 43e: aload 13
      // 440: invokevirtual com/starpilot/starview/VideoClient$Frame.getH ()I
      // 443: putfield com/starpilot/starview/VideoClient.lastH I
      // 446: aload 11
      // 448: astore 8
      // 44a: aload 11
      // 44c: astore 10
      // 44e: aload 0
      // 44f: getfield com/starpilot/starview/VideoClient.onSize Lkotlin/jvm/functions/Function2;
      // 452: aload 13
      // 454: invokevirtual com/starpilot/starview/VideoClient$Frame.getW ()I
      // 457: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 45a: aload 13
      // 45c: invokevirtual com/starpilot/starview/VideoClient$Frame.getH ()I
      // 45f: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 462: invokeinterface kotlin/jvm/functions/Function2.invoke (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
      // 467: pop
      // 468: aload 11
      // 46a: astore 8
      // 46c: aload 11
      // 46e: astore 10
      // 470: aload 0
      // 471: getfield com/starpilot/starview/VideoClient.onStatus Lkotlin/jvm/functions/Function1;
      // 474: astore 15
      // 476: aload 11
      // 478: astore 8
      // 47a: aload 11
      // 47c: astore 10
      // 47e: aload 11
      // 480: invokevirtual android/media/MediaCodec.getName ()Ljava/lang/String;
      // 483: astore 9
      // 485: aload 11
      // 487: astore 8
      // 489: aload 11
      // 48b: astore 10
      // 48d: aload 13
      // 48f: invokevirtual com/starpilot/starview/VideoClient$Frame.getW ()I
      // 492: istore 1
      // 493: aload 11
      // 495: astore 8
      // 497: aload 11
      // 499: astore 10
      // 49b: aload 13
      // 49d: invokevirtual com/starpilot/starview/VideoClient$Frame.getH ()I
      // 4a0: istore 2
      // 4a1: aload 11
      // 4a3: astore 8
      // 4a5: aload 11
      // 4a7: astore 10
      // 4a9: new java/lang/StringBuilder
      // 4ac: astore 14
      // 4ae: aload 11
      // 4b0: astore 8
      // 4b2: aload 11
      // 4b4: astore 10
      // 4b6: aload 14
      // 4b8: invokespecial java/lang/StringBuilder.<init> ()V
      // 4bb: aload 11
      // 4bd: astore 8
      // 4bf: aload 11
      // 4c1: astore 10
      // 4c3: aload 15
      // 4c5: aload 14
      // 4c7: ldc_w "video: decoder "
      // 4ca: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 4cd: aload 9
      // 4cf: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 4d2: ldc_w " "
      // 4d5: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 4d8: iload 1
      // 4d9: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
      // 4dc: ldc "x"
      // 4de: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 4e1: iload 2
      // 4e2: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
      // 4e5: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 4e8: invokeinterface kotlin/jvm/functions/Function1.invoke (Ljava/lang/Object;)Ljava/lang/Object; 2
      // 4ed: pop
      // 4ee: aload 11
      // 4f0: astore 8
      // 4f2: aload 11
      // 4f4: astore 10
      // 4f6: aload 0
      // 4f7: bipush 0
      // 4f8: putfield com/starpilot/starview/VideoClient.needKey Z
      // 4fb: goto 517
      // 4fe: aload 8
      // 500: astore 9
      // 502: goto 00c
      // 505: astore 9
      // 507: goto 78a
      // 50a: astore 9
      // 50c: aload 8
      // 50e: astore 10
      // 510: goto 6f0
      // 513: aload 8
      // 515: astore 11
      // 517: aload 11
      // 519: astore 8
      // 51b: aload 11
      // 51d: astore 10
      // 51f: aload 0
      // 520: getfield com/starpilot/starview/VideoClient.needKey Z
      // 523: ifeq 53a
      // 526: aload 11
      // 528: astore 9
      // 52a: aload 11
      // 52c: astore 8
      // 52e: aload 11
      // 530: astore 10
      // 532: aload 13
      // 534: invokevirtual com/starpilot/starview/VideoClient$Frame.getKey ()Z
      // 537: ifeq 00c
      // 53a: aload 11
      // 53c: astore 8
      // 53e: aload 11
      // 540: astore 10
      // 542: aload 0
      // 543: bipush 0
      // 544: putfield com/starpilot/starview/VideoClient.needKey Z
      // 547: aload 11
      // 549: astore 8
      // 54b: aload 11
      // 54d: astore 10
      // 54f: aload 11
      // 551: ldc2_w 50000
      // 554: invokevirtual android/media/MediaCodec.dequeueInputBuffer (J)I
      // 557: istore 2
      // 558: iload 2
      // 559: ifge 575
      // 55c: aload 11
      // 55e: astore 8
      // 560: aload 11
      // 562: astore 10
      // 564: aload 0
      // 565: aload 0
      // 566: getfield com/starpilot/starview/VideoClient.dropped J
      // 569: lconst_1
      // 56a: ladd
      // 56b: putfield com/starpilot/starview/VideoClient.dropped J
      // 56e: aload 11
      // 570: astore 9
      // 572: goto 00c
      // 575: aload 11
      // 577: astore 8
      // 579: aload 11
      // 57b: astore 10
      // 57d: aload 11
      // 57f: iload 2
      // 580: invokevirtual android/media/MediaCodec.getInputBuffer (I)Ljava/nio/ByteBuffer;
      // 583: astore 9
      // 585: aload 9
      // 587: ifnonnull 591
      // 58a: aload 11
      // 58c: astore 9
      // 58e: goto 00c
      // 591: aload 11
      // 593: astore 8
      // 595: aload 11
      // 597: astore 10
      // 599: aload 9
      // 59b: invokevirtual java/nio/ByteBuffer.clear ()Ljava/nio/Buffer;
      // 59e: pop
      // 59f: aload 11
      // 5a1: astore 8
      // 5a3: aload 11
      // 5a5: astore 10
      // 5a7: aload 9
      // 5a9: aload 13
      // 5ab: invokevirtual com/starpilot/starview/VideoClient$Frame.getData ()[B
      // 5ae: invokevirtual java/nio/ByteBuffer.put ([B)Ljava/nio/ByteBuffer;
      // 5b1: pop
      // 5b2: aload 11
      // 5b4: astore 8
      // 5b6: aload 11
      // 5b8: astore 10
      // 5ba: aload 13
      // 5bc: invokevirtual com/starpilot/starview/VideoClient$Frame.getData ()[B
      // 5bf: arraylength
      // 5c0: istore 3
      // 5c1: aload 11
      // 5c3: astore 8
      // 5c5: aload 11
      // 5c7: astore 10
      // 5c9: aload 13
      // 5cb: invokevirtual com/starpilot/starview/VideoClient$Frame.getFrameId ()J
      // 5ce: lstore 6
      // 5d0: aload 11
      // 5d2: astore 8
      // 5d4: aload 11
      // 5d6: astore 10
      // 5d8: aload 13
      // 5da: invokevirtual com/starpilot/starview/VideoClient$Frame.getKey ()Z
      // 5dd: ifeq 5e5
      // 5e0: bipush 1
      // 5e1: istore 1
      // 5e2: goto 5e7
      // 5e5: bipush 0
      // 5e6: istore 1
      // 5e7: aload 11
      // 5e9: astore 8
      // 5eb: aload 11
      // 5ed: astore 10
      // 5ef: aload 11
      // 5f1: iload 2
      // 5f2: bipush 0
      // 5f3: iload 3
      // 5f4: ldc2_w 50000
      // 5f7: lload 6
      // 5f9: lmul
      // 5fa: iload 1
      // 5fb: invokevirtual android/media/MediaCodec.queueInputBuffer (IIIJI)V
      // 5fe: aload 11
      // 600: astore 8
      // 602: aload 11
      // 604: astore 10
      // 606: invokestatic java/lang/System.nanoTime ()J
      // 609: lstore 6
      // 60b: aload 11
      // 60d: astore 8
      // 60f: aload 11
      // 611: astore 10
      // 613: aload 0
      // 614: aload 11
      // 616: aload 12
      // 618: invokespecial com/starpilot/starview/VideoClient.drain (Landroid/media/MediaCodec;Landroid/media/MediaCodec$BufferInfo;)V
      // 61b: aload 11
      // 61d: astore 8
      // 61f: aload 11
      // 621: astore 10
      // 623: aload 0
      // 624: aload 0
      // 625: getfield com/starpilot/starview/VideoClient.decodeMs D
      // 628: ldc2_w 0.9
      // 62b: dmul
      // 62c: invokestatic java/lang/System.nanoTime ()J
      // 62f: lload 6
      // 631: lsub
      // 632: l2d
      // 633: ldc2_w 1000000.0
      // 636: ddiv
      // 637: ldc2_w 0.1
      // 63a: dmul
      // 63b: dadd
      // 63c: putfield com/starpilot/starview/VideoClient.decodeMs D
      // 63f: aload 11
      // 641: astore 8
      // 643: aload 11
      // 645: astore 10
      // 647: aload 0
      // 648: aload 0
      // 649: getfield com/starpilot/starview/VideoClient.frames J
      // 64c: lconst_1
      // 64d: ladd
      // 64e: putfield com/starpilot/starview/VideoClient.frames J
      // 651: aload 11
      // 653: astore 8
      // 655: aload 11
      // 657: astore 10
      // 659: aload 0
      // 65a: aload 0
      // 65b: getfield com/starpilot/starview/VideoClient.fpsWin I
      // 65e: bipush 1
      // 65f: iadd
      // 660: putfield com/starpilot/starview/VideoClient.fpsWin I
      // 663: aload 11
      // 665: astore 8
      // 667: aload 11
      // 669: astore 10
      // 66b: invokestatic java/lang/System.nanoTime ()J
      // 66e: lstore 6
      // 670: aload 11
      // 672: astore 9
      // 674: aload 11
      // 676: astore 8
      // 678: aload 11
      // 67a: astore 10
      // 67c: lload 6
      // 67e: aload 0
      // 67f: getfield com/starpilot/starview/VideoClient.fpsT0 J
      // 682: lsub
      // 683: ldc2_w 1000000000
      // 686: lcmp
      // 687: ifle 00c
      // 68a: aload 11
      // 68c: astore 8
      // 68e: aload 11
      // 690: astore 10
      // 692: aload 0
      // 693: aload 0
      // 694: getfield com/starpilot/starview/VideoClient.fpsWin I
      // 697: i2d
      // 698: ldc2_w 1.0E9
      // 69b: dmul
      // 69c: lload 6
      // 69e: aload 0
      // 69f: getfield com/starpilot/starview/VideoClient.fpsT0 J
      // 6a2: lsub
      // 6a3: l2d
      // 6a4: ddiv
      // 6a5: putfield com/starpilot/starview/VideoClient.fps D
      // 6a8: aload 11
      // 6aa: astore 8
      // 6ac: aload 11
      // 6ae: astore 10
      // 6b0: aload 0
      // 6b1: bipush 0
      // 6b2: putfield com/starpilot/starview/VideoClient.fpsWin I
      // 6b5: aload 11
      // 6b7: astore 8
      // 6b9: aload 11
      // 6bb: astore 10
      // 6bd: aload 0
      // 6be: lload 6
      // 6c0: putfield com/starpilot/starview/VideoClient.fpsT0 J
      // 6c3: aload 11
      // 6c5: astore 9
      // 6c7: goto 00c
      // 6ca: aload 9
      // 6cc: ifnull 6dc
      // 6cf: aload 9
      // 6d1: invokevirtual android/media/MediaCodec.stop ()V
      // 6d4: goto 6dc
      // 6d7: astore 8
      // 6d9: goto 6e6
      // 6dc: aload 9
      // 6de: ifnull 6e6
      // 6e1: aload 9
      // 6e3: invokevirtual android/media/MediaCodec.release ()V
      // 6e6: goto 789
      // 6e9: astore 9
      // 6eb: goto 78a
      // 6ee: astore 9
      // 6f0: aload 10
      // 6f2: astore 8
      // 6f4: new java/lang/StringBuilder
      // 6f7: astore 11
      // 6f9: aload 10
      // 6fb: astore 8
      // 6fd: aload 11
      // 6ff: invokespecial java/lang/StringBuilder.<init> ()V
      // 702: aload 10
      // 704: astore 8
      // 706: ldc "StarView/Video"
      // 708: aload 11
      // 70a: ldc_w "decoder: "
      // 70d: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 710: aload 9
      // 712: invokevirtual java/lang/StringBuilder.append (Ljava/lang/Object;)Ljava/lang/StringBuilder;
      // 715: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 718: invokestatic android/util/Log.e (Ljava/lang/String;Ljava/lang/String;)I
      // 71b: pop
      // 71c: aload 10
      // 71e: astore 8
      // 720: aload 0
      // 721: getfield com/starpilot/starview/VideoClient.onStatus Lkotlin/jvm/functions/Function1;
      // 724: astore 11
      // 726: aload 10
      // 728: astore 8
      // 72a: aload 9
      // 72c: invokevirtual java/lang/Exception.getMessage ()Ljava/lang/String;
      // 72f: astore 9
      // 731: aload 10
      // 733: astore 8
      // 735: new java/lang/StringBuilder
      // 738: astore 12
      // 73a: aload 10
      // 73c: astore 8
      // 73e: aload 12
      // 740: invokespecial java/lang/StringBuilder.<init> ()V
      // 743: aload 10
      // 745: astore 8
      // 747: aload 11
      // 749: aload 12
      // 74b: ldc_w "video: decoder error: "
      // 74e: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 751: aload 9
      // 753: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 756: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 759: invokeinterface kotlin/jvm/functions/Function1.invoke (Ljava/lang/Object;)Ljava/lang/Object; 2
      // 75e: pop
      // 75f: aload 10
      // 761: checkcast android/media/MediaCodec
      // 764: astore 8
      // 766: aload 8
      // 768: ifnull 770
      // 76b: aload 8
      // 76d: invokevirtual android/media/MediaCodec.stop ()V
      // 770: aload 10
      // 772: checkcast android/media/MediaCodec
      // 775: astore 8
      // 777: aload 8
      // 779: ifnull 6e6
      // 77c: aload 8
      // 77e: invokevirtual android/media/MediaCodec.release ()V
      // 781: goto 6e6
      // 784: astore 8
      // 786: goto 6e6
      // 789: return
      // 78a: aload 8
      // 78c: checkcast android/media/MediaCodec
      // 78f: astore 10
      // 791: aload 10
      // 793: ifnull 79b
      // 796: aload 10
      // 798: invokevirtual android/media/MediaCodec.stop ()V
      // 79b: aload 8
      // 79d: checkcast android/media/MediaCodec
      // 7a0: astore 8
      // 7a2: aload 8
      // 7a4: ifnull 7b1
      // 7a7: aload 8
      // 7a9: invokevirtual android/media/MediaCodec.release ()V
      // 7ac: goto 7b1
      // 7af: astore 8
      // 7b1: aload 9
      // 7b3: athrow
   }

   private final void drain(MediaCodec var1, BufferInfo var2) {
      while (true) {
         int var3 = var1.dequeueOutputBuffer(var2, 0L);
         if (var3 >= 0) {
            if (this.surface == null) {
               Function1 var6 = this.onImage;
               if (var6 != null) {
                  Image var12;
                  try {
                     var12 = var1.getOutputImage(var3);
                  } catch (Exception var10) {
                     var12 = null;
                  }

                  if (var12 != null) {
                     try {
                        var6.invoke(var12);
                     } finally {
                        var12.close();
                     }
                  }
               }

               var1.releaseOutputBuffer(var3, false);
            } else {
               var1.releaseOutputBuffer(var3, true);
            }
         } else {
            if (var3 != -2) {
               return;
            }

            MediaFormat var5 = var1.getOutputFormat();
            Intrinsics.checkNotNullExpressionValue(var5, "getOutputFormat(...)");
            int var4 = var5.getInteger("width");
            var3 = var5.getInteger("height");
            if (var4 > 0 && var3 > 0) {
               this.lastW = var4;
               this.lastH = var3;
               this.onSize.invoke(var4, var3);
            }
         }
      }
   }

   private final byte[] findNal(byte[] var1, int var2, boolean var3) {
      return VideoClientKt.nalOf(var1, var2, var3);
   }

   // $VF: synthetic method
   static byte[] findNal$default(VideoClient var0, byte[] var1, int var2, boolean var3, int var4, Object var5) {
      if ((var4 & 4) != 0) {
         var3 = false;
      }

      return var0.findNal(var1, var2, var3);
   }

   public final void close() {
      this.running = false;
      WebSocket var1 = this.ws;
      if (var1 != null) {
         var1.close(1000, "bye");
      }

      this.ws = null;
      Thread var2 = this.decoderThread;
      if (var2 != null) {
         var2.join(500L);
      }

      this.decoderThread = null;
      this.queue.clear();
   }

   public final void connect(String var1, int var2, String var3, Surface var4) {
      Intrinsics.checkNotNullParameter(var1, "host");
      Intrinsics.checkNotNullParameter(var3, "cam");
      this.close();
      this.surface = var4;
      this.running = true;
      Thread var8 = new Thread(new VideoClient$$ExternalSyntheticLambda0(this), "starview-decoder");
      var8.start();
      this.decoderThread = var8;
      Link var5 = Link.INSTANCE;
      okhttp3.Request.Builder var6 = new okhttp3.Request.Builder();
      String var9;
      if (VideoClientKt.getHevcDecoder()) {
         var9 = "?hevc=1";
      } else {
         var9 = "";
      }

      Request var7 = var5.withSession(var6.url("ws://" + var1 + ":" + var2 + "/video/" + var3 + var9), var1, var2).build();
      this.ws = this.http
         .newWebSocket(
            var7,
            new WebSocketListener(this, var3) {
               final String $cam;
               final VideoClient this$0;

               {
                  this.this$0 = var1;
                  this.$cam = var2x;
               }

               @Override
               public void onClosed(WebSocket var1, int var2x, String var3x) {
                  Intrinsics.checkNotNullParameter(var1, "webSocket");
                  Intrinsics.checkNotNullParameter(var3x, "reason");
                  this.this$0.onStatus.invoke("video: closed");
               }

               @Override
               public void onFailure(WebSocket var1, Throwable var2x, Response var3x) {
                  Intrinsics.checkNotNullParameter(var1, "webSocket");
                  Intrinsics.checkNotNullParameter(var2x, "t");
                  Function1 var4x = this.this$0.onStatus;
                  String var5x = var2x.getMessage();
                  var4x.invoke("video: " + var5x);
               }

               @Override
               public void onMessage(WebSocket var1, ByteString var2x) {
                  Intrinsics.checkNotNullParameter(var1, "webSocket");
                  Intrinsics.checkNotNullParameter(var2x, "bytes");
                  ByteBuffer var17 = var2x.asByteBuffer().order(ByteOrder.LITTLE_ENDIAN);
                  if (var17.remaining() >= 28) {
                     long var8x = var17.getInt();
                     long var6x = var17.getLong();
                     long var10 = var17.getLong();
                     int var5x = var17.getInt();
                     short var3x = var17.getShort();
                     short var4x = var17.getShort();
                     byte[] var16 = new byte[var17.remaining()];
                     var17.get(var16);
                     boolean var14;
                     if ((var5x & 1) != 0) {
                        var14 = true;
                     } else {
                        var14 = false;
                     }

                     long var12 = System.nanoTime();
                     boolean var15;
                     if ((var5x & 2) != 0) {
                        var15 = true;
                     } else {
                        var15 = false;
                     }

                     VideoClient.Frame var18 = new VideoClient.Frame(
                        var8x & 4294967295L, var6x, var10, var14, var3x & '\uffff', var4x & '\uffff', var16, var12, var15
                     );
                     if (!this.this$0.queue.offer(var18)) {
                        this.this$0.queue.clear();
                        var6x = this.this$0.getDropped();
                        this.this$0.setDropped(var6x + 1L);
                        this.this$0.needKey = true;
                        var1.send("keyframe");
                     }
                  }
               }

               @Override
               public void onOpen(WebSocket var1, Response var2x) {
                  Intrinsics.checkNotNullParameter(var1, "webSocket");
                  Intrinsics.checkNotNullParameter(var2x, "response");
                  Function1 var4x = this.this$0.onStatus;
                  String var3x = this.$cam;
                  var4x.invoke("video[" + var3x + "]: connected, waiting for keyframe");
               }
            }
         );
   }

   public final double getDecodeMs() {
      return this.decodeMs;
   }

   public final long getDropped() {
      return this.dropped;
   }

   public final double getFps() {
      return this.fps;
   }

   public final long getFrames() {
      return this.frames;
   }

   public final int getLastH() {
      return this.lastH;
   }

   public final int getLastW() {
      return this.lastW;
   }

   public final Function1<Image, Unit> getOnImage() {
      return this.onImage;
   }

   public final Surface getSurface() {
      return this.surface;
   }

   public final boolean isRunning() {
      return this.running;
   }

   public final void setDecodeMs(double var1) {
      this.decodeMs = var1;
   }

   public final void setDropped(long var1) {
      this.dropped = var1;
   }

   public final void setFps(double var1) {
      this.fps = var1;
   }

   public final void setFrames(long var1) {
      this.frames = var1;
   }

   public final void setLastH(int var1) {
      this.lastH = var1;
   }

   public final void setLastW(int var1) {
      this.lastW = var1;
   }

   public final void setOnImage(Function1<? super Image, Unit> var1) {
      this.onImage = var1;
   }

   public final void setSurface(Surface var1) {
      this.surface = var1;
   }

   public static final class Companion {
      private Companion() {
      }

      // $VF: synthetic method
      public Companion(DefaultConstructorMarker var1) {
         this();
      }
   }

   public static final class Frame {
      private final byte[] data;
      private final long frameId;
      private final int h;
      private final boolean hevc;
      private final boolean key;
      private final long rxNs;
      private final long tEofNs;
      private final long unixNs;
      private final int w;

      public Frame(long var1, long var3, long var5, boolean var7, int var8, int var9, byte[] var10, long var11, boolean var13) {
         Intrinsics.checkNotNullParameter(var10, "data");
         super();
         this.frameId = var1;
         this.tEofNs = var3;
         this.unixNs = var5;
         this.key = var7;
         this.w = var8;
         this.h = var9;
         this.data = var10;
         this.rxNs = var11;
         this.hevc = var13;
      }

      // $VF: synthetic method
      public Frame(
         long var1, long var3, long var5, boolean var7, int var8, int var9, byte[] var10, long var11, boolean var13, int var14, DefaultConstructorMarker var15
      ) {
         if ((var14 & 256) != 0) {
            var13 = false;
         }

         this(var1, var3, var5, var7, var8, var9, var10, var11, var13);
      }

      public final byte[] getData() {
         return this.data;
      }

      public final long getFrameId() {
         return this.frameId;
      }

      public final int getH() {
         return this.h;
      }

      public final boolean getHevc() {
         return this.hevc;
      }

      public final boolean getKey() {
         return this.key;
      }

      public final long getRxNs() {
         return this.rxNs;
      }

      public final long getTEofNs() {
         return this.tEofNs;
      }

      public final long getUnixNs() {
         return this.unixNs;
      }

      public final int getW() {
         return this.w;
      }
   }
}
