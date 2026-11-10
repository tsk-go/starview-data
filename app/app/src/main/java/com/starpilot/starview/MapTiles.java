package com.starpilot.starview;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ConcurrentHashMap.KeySetView;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.Pair;
import kotlin.Triple;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;

public final class MapTiles {
   public static final MapTiles.Companion Companion = new MapTiles.Companion(null);
   private static final long DISK_MAX = 262144000L;
   private static final Map<String, String> MAPBOX_STYLES;
   private static final long STREET_TTL_MS = 604800000L;
   private static final long TRAFFIC_TTL_MS = 900000L;
   private final ThreadPoolExecutor ahead;
   private final File dir;
   private final AtomicInteger direct;
   private volatile long directDownUntil;
   private volatile String directKey;
   private final ConcurrentHashMap<String, Long> failedUntil;
   private volatile int failures;
   private volatile String host;
   private final OkHttpClient http;
   private volatile String lastError;
   private final ConcurrentHashMap<String, Long> madeAt;
   private final OkHttpClient mapbox;
   private final LruCache<String, Bitmap> mem;
   private final Function0<Unit> onLoaded;
   private final KeySetView<String, Boolean> pending;
   private final ThreadPoolExecutor pool;
   private volatile Set<String> wanted;

   // $VF: synthetic method
   public static void $r8$lambda$42tG8tT1TdqwcI6xdC_OHeAbuj0/* $VF was: $r8$lambda$42tG8tT1TdqwcI6xdC_OHeAbuj0*/(
      MapTiles var0, String var1, int var2, int var3, int var4, String var5
   ) {
      prefetch$lambda$2(var0, var1, var2, var3, var4, var5);
   }

   // $VF: synthetic method
   public static void $r8$lambda$LrM9Vrjzf0l_R9OcQ8X_BqI4kPo/* $VF was: $r8$lambda$LrM9Vrjzf0l_R9OcQ8X_BqI4kPo*/(
      MapTiles var0, String var1, int var2, int var3, int var4, String var5
   ) {
      request$lambda$3(var0, var1, var2, var3, var4, var5);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$eBwtsfLZrYSwR_5HjjF3toWG4JM(File var0) {
      return prune$lambda$12(var0);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$j0gEY4iO8NHvuZGQaxrzAFTdYOE(File var0) {
      return prune$lambda$9(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$rexeZh_vIdcRQMzWPk_JzgQjhao/* $VF was: $r8$lambda$rexeZh_vIdcRQMzWPk_JzgQjhao*/(MapTiles var0) {
      _init_$lambda$0(var0);
   }

   static {
      Pair var0 = TuplesKt.to("light", "mapbox/light-v11");
      Pair var1 = TuplesKt.to("dark", "mapbox/dark-v11");
      Pair var2 = TuplesKt.to("trafficLight", "mapbox/navigation-day-v1");
      MAPBOX_STYLES = MapsKt.mapOf(new Pair[]{var0, var1, var2, TuplesKt.to("trafficDark", "mapbox/navigation-night-v1")});
   }

   public MapTiles(Context var1, Function0<Unit> var2) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      Intrinsics.checkNotNullParameter(var2, "onLoaded");
      super();
      this.onLoaded = var2;
      this.wanted = SetsKt.emptySet();
      this.lastError = "";
      this.direct = new AtomicInteger();
      this.dir = new File(var1.getCacheDir(), "maptiles");
      this.mem = new LruCache<String, Bitmap>(50331648) {
         protected int sizeOf(String var1, Bitmap var2x) {
            Intrinsics.checkNotNullParameter(var1, "key");
            Intrinsics.checkNotNullParameter(var2x, "value");
            return var2x.getByteCount();
         }
      };
      this.pending = ConcurrentHashMap.newKeySet();
      this.failedUntil = new ConcurrentHashMap<>();
      this.madeAt = new ConcurrentHashMap<>();
      this.http = new Builder()
         .addInterceptor(Link.INSTANCE.getAuthInterceptor())
         .connectTimeout(4L, TimeUnit.SECONDS)
         .readTimeout(10L, TimeUnit.SECONDS)
         .build();
      this.mapbox = new Builder().connectTimeout(3L, TimeUnit.SECONDS).readTimeout(8L, TimeUnit.SECONDS).build();
      this.pool = new ThreadPoolExecutor(6, 6, 30L, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>() {
         public boolean offer(Runnable var1) {
            Intrinsics.checkNotNullParameter(var1, "e");
            return this.offerFirst(var1);
         }
      });
      this.ahead = new ThreadPoolExecutor(2, 2, 30L, TimeUnit.SECONDS, new LinkedBlockingDeque<>(400));
      this.pool.allowCoreThreadTimeOut(true);
      this.ahead.allowCoreThreadTimeOut(true);
      Thread var3 = new Thread(new MapTiles$$ExternalSyntheticLambda1(this));
      var3.setDaemon(true);
      var3.setPriority(1);
      var3.start();
   }

   private static final void _init_$lambda$0(MapTiles var0) {
      var0.prune();
   }

   private final File file(String var1, int var2, int var3, int var4) {
      return new File(this.dir, var1 + "/" + var2 + "/" + var3 + "_" + var4 + ".png");
   }

   private final byte[] fromMapbox(String param1, int param2, int param3, int param4) {
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
      // 001: getfield com/starpilot/starview/MapTiles.directKey Ljava/lang/String;
      // 004: astore 6
      // 006: aconst_null
      // 007: astore 5
      // 009: aload 6
      // 00b: ifnonnull 010
      // 00e: aconst_null
      // 00f: areturn
      // 010: getstatic com/starpilot/starview/MapTiles.MAPBOX_STYLES Ljava/util/Map;
      // 013: aload 1
      // 014: invokeinterface java/util/Map.get (Ljava/lang/Object;)Ljava/lang/Object; 2
      // 019: checkcast java/lang/String
      // 01c: astore 7
      // 01e: aload 7
      // 020: ifnonnull 025
      // 023: aconst_null
      // 024: areturn
      // 025: invokestatic java/lang/System.currentTimeMillis ()J
      // 028: aload 0
      // 029: getfield com/starpilot/starview/MapTiles.directDownUntil J
      // 02c: lcmp
      // 02d: ifge 032
      // 030: aconst_null
      // 031: areturn
      // 032: aload 0
      // 033: getfield com/starpilot/starview/MapTiles.mapbox Lokhttp3/OkHttpClient;
      // 036: astore 9
      // 038: new okhttp3/Request$Builder
      // 03b: astore 8
      // 03d: aload 8
      // 03f: invokespecial okhttp3/Request$Builder.<init> ()V
      // 042: new java/lang/StringBuilder
      // 045: astore 1
      // 046: aload 1
      // 047: invokespecial java/lang/StringBuilder.<init> ()V
      // 04a: aload 9
      // 04c: aload 8
      // 04e: aload 1
      // 04f: ldc_w "https://api.mapbox.com/styles/v1/"
      // 052: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 055: aload 7
      // 057: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 05a: ldc_w "/tiles/512/"
      // 05d: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 060: iload 2
      // 061: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
      // 064: ldc_w "/"
      // 067: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 06a: iload 3
      // 06b: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
      // 06e: ldc_w "/"
      // 071: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 074: iload 4
      // 076: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
      // 079: ldc_w "?access_token="
      // 07c: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 07f: aload 6
      // 081: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 084: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 087: invokevirtual okhttp3/Request$Builder.url (Ljava/lang/String;)Lokhttp3/Request$Builder;
      // 08a: invokevirtual okhttp3/Request$Builder.build ()Lokhttp3/Request;
      // 08d: invokevirtual okhttp3/OkHttpClient.newCall (Lokhttp3/Request;)Lokhttp3/Call;
      // 090: invokeinterface okhttp3/Call.execute ()Lokhttp3/Response; 1
      // 095: checkcast java/io/Closeable
      // 098: astore 6
      // 09a: aload 6
      // 09c: checkcast okhttp3/Response
      // 09f: astore 1
      // 0a0: aload 1
      // 0a1: invokevirtual okhttp3/Response.code ()I
      // 0a4: sipush 401
      // 0a7: if_icmpeq 0ea
      // 0aa: aload 1
      // 0ab: invokevirtual okhttp3/Response.code ()I
      // 0ae: sipush 403
      // 0b1: if_icmpeq 0ea
      // 0b4: aload 1
      // 0b5: invokevirtual okhttp3/Response.code ()I
      // 0b8: sipush 429
      // 0bb: if_icmpne 0c1
      // 0be: goto 0ea
      // 0c1: aload 1
      // 0c2: invokevirtual okhttp3/Response.isSuccessful ()Z
      // 0c5: ifne 0cd
      // 0c8: aconst_null
      // 0c9: astore 1
      // 0ca: goto 0f8
      // 0cd: aload 1
      // 0ce: invokevirtual okhttp3/Response.body ()Lokhttp3/ResponseBody;
      // 0d1: astore 1
      // 0d2: aload 1
      // 0d3: ifnull 0c8
      // 0d6: aload 1
      // 0d7: invokevirtual okhttp3/ResponseBody.bytes ()[B
      // 0da: astore 1
      // 0db: aload 1
      // 0dc: ifnull 0c8
      // 0df: aload 0
      // 0e0: getfield com/starpilot/starview/MapTiles.direct Ljava/util/concurrent/atomic/AtomicInteger;
      // 0e3: invokevirtual java/util/concurrent/atomic/AtomicInteger.incrementAndGet ()I
      // 0e6: pop
      // 0e7: goto 0f8
      // 0ea: aload 0
      // 0eb: invokestatic java/lang/System.currentTimeMillis ()J
      // 0ee: ldc_w 300000
      // 0f1: i2l
      // 0f2: ladd
      // 0f3: putfield com/starpilot/starview/MapTiles.directDownUntil J
      // 0f6: aconst_null
      // 0f7: astore 1
      // 0f8: aload 6
      // 0fa: aconst_null
      // 0fb: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 0fe: goto 11f
      // 101: astore 1
      // 102: aload 1
      // 103: athrow
      // 104: astore 7
      // 106: aload 6
      // 108: aload 1
      // 109: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 10c: aload 7
      // 10e: athrow
      // 10f: astore 1
      // 110: aload 0
      // 111: invokestatic java/lang/System.currentTimeMillis ()J
      // 114: ldc_w 60000
      // 117: i2l
      // 118: ladd
      // 119: putfield com/starpilot/starview/MapTiles.directDownUntil J
      // 11c: aload 5
      // 11e: astore 1
      // 11f: aload 1
      // 120: areturn
   }

   private final void load(String param1, int param2, int param3, int param4, String param5, boolean param6) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.NullPointerException: Cannot invoke "org.jetbrains.java.decompiler.code.cfg.ExceptionRangeCFG.isCircular()" because "range" is null
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.graphToStatement(DomHelper.java:84)
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:203)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 000: iload 6
      // 002: ifne 022
      // 005: aload 0
      // 006: getfield com/starpilot/starview/MapTiles.wanted Ljava/util/Set;
      // 009: aload 5
      // 00b: invokeinterface java/util/Set.contains (Ljava/lang/Object;)Z 2
      // 010: istore 8
      // 012: iload 8
      // 014: ifne 022
      // 017: aload 0
      // 018: getfield com/starpilot/starview/MapTiles.pending Ljava/util/concurrent/ConcurrentHashMap$KeySetView;
      // 01b: aload 5
      // 01d: invokevirtual java/util/concurrent/ConcurrentHashMap$KeySetView.remove (Ljava/lang/Object;)Z
      // 020: pop
      // 021: return
      // 022: aload 0
      // 023: aload 1
      // 024: iload 2
      // 025: iload 3
      // 026: iload 4
      // 028: invokespecial com/starpilot/starview/MapTiles.file (Ljava/lang/String;III)Ljava/io/File;
      // 02b: astore 16
      // 02d: aload 1
      // 02e: ldc_w "traffic"
      // 031: bipush 0
      // 032: bipush 2
      // 033: aconst_null
      // 034: invokestatic kotlin/text/StringsKt.startsWith$default (Ljava/lang/String;Ljava/lang/String;ZILjava/lang/Object;)Z
      // 037: ifeq 042
      // 03a: ldc2_w 900000
      // 03d: lstore 9
      // 03f: goto 047
      // 042: ldc2_w 604800000
      // 045: lstore 9
      // 047: bipush 0
      // 048: istore 7
      // 04a: aload 16
      // 04c: invokevirtual java/io/File.exists ()Z
      // 04f: ifeq 0bf
      // 052: iload 6
      // 054: ifne 09f
      // 057: aload 0
      // 058: getfield com/starpilot/starview/MapTiles.mem Lcom/starpilot/starview/MapTiles$mem$1;
      // 05b: aload 5
      // 05d: invokevirtual com/starpilot/starview/MapTiles$mem$1.get (Ljava/lang/Object;)Ljava/lang/Object;
      // 060: ifnonnull 09f
      // 063: aload 16
      // 065: invokevirtual java/io/File.getPath ()Ljava/lang/String;
      // 068: invokestatic android/graphics/BitmapFactory.decodeFile (Ljava/lang/String;)Landroid/graphics/Bitmap;
      // 06b: astore 15
      // 06d: aload 15
      // 06f: ifnull 09f
      // 072: aload 0
      // 073: getfield com/starpilot/starview/MapTiles.mem Lcom/starpilot/starview/MapTiles$mem$1;
      // 076: aload 5
      // 078: aload 15
      // 07a: invokevirtual com/starpilot/starview/MapTiles$mem$1.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
      // 07d: pop
      // 07e: aload 0
      // 07f: getfield com/starpilot/starview/MapTiles.madeAt Ljava/util/concurrent/ConcurrentHashMap;
      // 082: checkcast java/util/Map
      // 085: aload 5
      // 087: aload 16
      // 089: invokevirtual java/io/File.lastModified ()J
      // 08c: invokestatic java/lang/Long.valueOf (J)Ljava/lang/Long;
      // 08f: invokeinterface java/util/Map.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
      // 094: pop
      // 095: aload 0
      // 096: getfield com/starpilot/starview/MapTiles.onLoaded Lkotlin/jvm/functions/Function0;
      // 099: invokeinterface kotlin/jvm/functions/Function0.invoke ()Ljava/lang/Object; 1
      // 09e: pop
      // 09f: invokestatic java/lang/System.currentTimeMillis ()J
      // 0a2: lstore 13
      // 0a4: aload 16
      // 0a6: invokevirtual java/io/File.lastModified ()J
      // 0a9: lstore 11
      // 0ab: lload 13
      // 0ad: lload 11
      // 0af: lsub
      // 0b0: lload 9
      // 0b2: lcmp
      // 0b3: ifge 0bc
      // 0b6: bipush 1
      // 0b7: istore 7
      // 0b9: goto 0bf
      // 0bc: bipush 0
      // 0bd: istore 7
      // 0bf: iload 7
      // 0c1: ifeq 0cf
      // 0c4: aload 0
      // 0c5: getfield com/starpilot/starview/MapTiles.pending Ljava/util/concurrent/ConcurrentHashMap$KeySetView;
      // 0c8: aload 5
      // 0ca: invokevirtual java/util/concurrent/ConcurrentHashMap$KeySetView.remove (Ljava/lang/Object;)Z
      // 0cd: pop
      // 0ce: return
      // 0cf: aload 0
      // 0d0: aload 1
      // 0d1: iload 2
      // 0d2: iload 3
      // 0d3: iload 4
      // 0d5: invokespecial com/starpilot/starview/MapTiles.fromMapbox (Ljava/lang/String;III)[B
      // 0d8: astore 15
      // 0da: aload 15
      // 0dc: ifnonnull 1f4
      // 0df: aload 0
      // 0e0: checkcast com/starpilot/starview/MapTiles
      // 0e3: astore 18
      // 0e5: aload 18
      // 0e7: getfield com/starpilot/starview/MapTiles.host Ljava/lang/String;
      // 0ea: astore 20
      // 0ec: aload 20
      // 0ee: ifnonnull 0fc
      // 0f1: aload 0
      // 0f2: getfield com/starpilot/starview/MapTiles.pending Ljava/util/concurrent/ConcurrentHashMap$KeySetView;
      // 0f5: aload 5
      // 0f7: invokevirtual java/util/concurrent/ConcurrentHashMap$KeySetView.remove (Ljava/lang/Object;)Z
      // 0fa: pop
      // 0fb: return
      // 0fc: aload 18
      // 0fe: getfield com/starpilot/starview/MapTiles.http Lokhttp3/OkHttpClient;
      // 101: astore 15
      // 103: new okhttp3/Request$Builder
      // 106: astore 17
      // 108: aload 17
      // 10a: invokespecial okhttp3/Request$Builder.<init> ()V
      // 10d: new java/lang/StringBuilder
      // 110: astore 19
      // 112: aload 19
      // 114: invokespecial java/lang/StringBuilder.<init> ()V
      // 117: aload 19
      // 119: ldc_w "http://"
      // 11c: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 11f: aload 20
      // 121: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 124: ldc_w ":8090/nav/tile/"
      // 127: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 12a: aload 1
      // 12b: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 12e: ldc_w "/"
      // 131: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 134: astore 1
      // 135: aload 15
      // 137: aload 17
      // 139: aload 1
      // 13a: iload 2
      // 13b: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
      // 13e: ldc_w "/"
      // 141: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 144: iload 3
      // 145: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
      // 148: ldc_w "/"
      // 14b: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 14e: iload 4
      // 150: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
      // 153: ldc_w ".png"
      // 156: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 159: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 15c: invokevirtual okhttp3/Request$Builder.url (Ljava/lang/String;)Lokhttp3/Request$Builder;
      // 15f: invokevirtual okhttp3/Request$Builder.build ()Lokhttp3/Request;
      // 162: invokevirtual okhttp3/OkHttpClient.newCall (Lokhttp3/Request;)Lokhttp3/Call;
      // 165: invokeinterface okhttp3/Call.execute ()Lokhttp3/Response; 1
      // 16a: checkcast java/io/Closeable
      // 16d: astore 17
      // 16f: aload 17
      // 171: checkcast okhttp3/Response
      // 174: astore 19
      // 176: aload 19
      // 178: invokevirtual okhttp3/Response.isSuccessful ()Z
      // 17b: ifne 1c8
      // 17e: aload 19
      // 180: invokevirtual okhttp3/Response.body ()Lokhttp3/ResponseBody;
      // 183: astore 1
      // 184: aload 1
      // 185: ifnull 1a2
      // 188: aload 1
      // 189: invokevirtual okhttp3/ResponseBody.string ()Ljava/lang/String;
      // 18c: astore 1
      // 18d: aload 1
      // 18e: ifnull 1a2
      // 191: aload 1
      // 192: sipush 200
      // 195: invokestatic kotlin/text/StringsKt.take (Ljava/lang/String;I)Ljava/lang/String;
      // 198: astore 15
      // 19a: aload 15
      // 19c: astore 1
      // 19d: aload 15
      // 19f: ifnonnull 1bf
      // 1a2: aload 19
      // 1a4: invokevirtual okhttp3/Response.code ()I
      // 1a7: istore 2
      // 1a8: new java/lang/StringBuilder
      // 1ab: astore 1
      // 1ac: aload 1
      // 1ad: invokespecial java/lang/StringBuilder.<init> ()V
      // 1b0: aload 1
      // 1b1: ldc_w "HTTP "
      // 1b4: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 1b7: iload 2
      // 1b8: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
      // 1bb: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 1be: astore 1
      // 1bf: aload 18
      // 1c1: aload 1
      // 1c2: putfield com/starpilot/starview/MapTiles.lastError Ljava/lang/String;
      // 1c5: goto 1da
      // 1c8: aload 19
      // 1ca: invokevirtual okhttp3/Response.body ()Lokhttp3/ResponseBody;
      // 1cd: astore 1
      // 1ce: aload 1
      // 1cf: ifnull 1da
      // 1d2: aload 1
      // 1d3: invokevirtual okhttp3/ResponseBody.bytes ()[B
      // 1d6: astore 1
      // 1d7: goto 1dc
      // 1da: aconst_null
      // 1db: astore 1
      // 1dc: aload 17
      // 1de: aconst_null
      // 1df: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 1e2: goto 1f7
      // 1e5: astore 15
      // 1e7: aload 15
      // 1e9: athrow
      // 1ea: astore 1
      // 1eb: aload 17
      // 1ed: aload 15
      // 1ef: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 1f2: aload 1
      // 1f3: athrow
      // 1f4: aload 15
      // 1f6: astore 1
      // 1f7: aload 1
      // 1f8: ifnonnull 22a
      // 1fb: aload 0
      // 1fc: aload 0
      // 1fd: getfield com/starpilot/starview/MapTiles.failures I
      // 200: bipush 1
      // 201: iadd
      // 202: putfield com/starpilot/starview/MapTiles.failures I
      // 205: aload 0
      // 206: getfield com/starpilot/starview/MapTiles.failedUntil Ljava/util/concurrent/ConcurrentHashMap;
      // 209: checkcast java/util/Map
      // 20c: aload 5
      // 20e: invokestatic java/lang/System.currentTimeMillis ()J
      // 211: sipush 30000
      // 214: i2l
      // 215: ladd
      // 216: invokestatic java/lang/Long.valueOf (J)Ljava/lang/Long;
      // 219: invokeinterface java/util/Map.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
      // 21e: pop
      // 21f: aload 0
      // 220: getfield com/starpilot/starview/MapTiles.pending Ljava/util/concurrent/ConcurrentHashMap$KeySetView;
      // 223: aload 5
      // 225: invokevirtual java/util/concurrent/ConcurrentHashMap$KeySetView.remove (Ljava/lang/Object;)Z
      // 228: pop
      // 229: return
      // 22a: aload 1
      // 22b: arraylength
      // 22c: bipush 8
      // 22e: if_icmple 248
      // 231: aload 1
      // 232: bipush 0
      // 233: baload
      // 234: bipush -119
      // 236: if_icmpne 248
      // 239: aload 1
      // 23a: bipush 1
      // 23b: baload
      // 23c: istore 2
      // 23d: iload 2
      // 23e: bipush 80
      // 240: if_icmpne 248
      // 243: bipush 1
      // 244: istore 2
      // 245: goto 24a
      // 248: bipush 0
      // 249: istore 2
      // 24a: iload 6
      // 24c: ifeq 2bf
      // 24f: iload 2
      // 250: ifeq 2bf
      // 253: aload 0
      // 254: getfield com/starpilot/starview/MapTiles.wanted Ljava/util/Set;
      // 257: aload 5
      // 259: invokeinterface java/util/Set.contains (Ljava/lang/Object;)Z 2
      // 25e: istore 8
      // 260: iload 8
      // 262: ifne 2bf
      // 265: aload 16
      // 267: invokevirtual java/io/File.getParentFile ()Ljava/io/File;
      // 26a: astore 15
      // 26c: aload 15
      // 26e: ifnull 277
      // 271: aload 15
      // 273: invokevirtual java/io/File.mkdirs ()Z
      // 276: pop
      // 277: new java/io/File
      // 27a: astore 18
      // 27c: aload 16
      // 27e: invokevirtual java/io/File.getPath ()Ljava/lang/String;
      // 281: astore 17
      // 283: new java/lang/StringBuilder
      // 286: astore 15
      // 288: aload 15
      // 28a: invokespecial java/lang/StringBuilder.<init> ()V
      // 28d: aload 18
      // 28f: aload 15
      // 291: aload 17
      // 293: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 296: ldc_w ".tmp"
      // 299: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 29c: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 29f: invokespecial java/io/File.<init> (Ljava/lang/String;)V
      // 2a2: aload 18
      // 2a4: aload 1
      // 2a5: invokestatic kotlin/io/FilesKt.writeBytes (Ljava/io/File;[B)V
      // 2a8: aload 18
      // 2aa: aload 16
      // 2ac: invokevirtual java/io/File.renameTo (Ljava/io/File;)Z
      // 2af: pop
      // 2b0: goto 2b4
      // 2b3: astore 1
      // 2b4: aload 0
      // 2b5: getfield com/starpilot/starview/MapTiles.pending Ljava/util/concurrent/ConcurrentHashMap$KeySetView;
      // 2b8: aload 5
      // 2ba: invokevirtual java/util/concurrent/ConcurrentHashMap$KeySetView.remove (Ljava/lang/Object;)Z
      // 2bd: pop
      // 2be: return
      // 2bf: aload 1
      // 2c0: bipush 0
      // 2c1: aload 1
      // 2c2: arraylength
      // 2c3: invokestatic android/graphics/BitmapFactory.decodeByteArray ([BII)Landroid/graphics/Bitmap;
      // 2c6: astore 15
      // 2c8: aload 15
      // 2ca: ifnonnull 2fc
      // 2cd: aload 0
      // 2ce: aload 0
      // 2cf: getfield com/starpilot/starview/MapTiles.failures I
      // 2d2: bipush 1
      // 2d3: iadd
      // 2d4: putfield com/starpilot/starview/MapTiles.failures I
      // 2d7: aload 0
      // 2d8: getfield com/starpilot/starview/MapTiles.failedUntil Ljava/util/concurrent/ConcurrentHashMap;
      // 2db: checkcast java/util/Map
      // 2de: aload 5
      // 2e0: invokestatic java/lang/System.currentTimeMillis ()J
      // 2e3: ldc_w 60000
      // 2e6: i2l
      // 2e7: ladd
      // 2e8: invokestatic java/lang/Long.valueOf (J)Ljava/lang/Long;
      // 2eb: invokeinterface java/util/Map.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
      // 2f0: pop
      // 2f1: aload 0
      // 2f2: getfield com/starpilot/starview/MapTiles.pending Ljava/util/concurrent/ConcurrentHashMap$KeySetView;
      // 2f5: aload 5
      // 2f7: invokevirtual java/util/concurrent/ConcurrentHashMap$KeySetView.remove (Ljava/lang/Object;)Z
      // 2fa: pop
      // 2fb: return
      // 2fc: aload 0
      // 2fd: getfield com/starpilot/starview/MapTiles.mem Lcom/starpilot/starview/MapTiles$mem$1;
      // 300: aload 5
      // 302: aload 15
      // 304: invokevirtual com/starpilot/starview/MapTiles$mem$1.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
      // 307: pop
      // 308: aload 0
      // 309: getfield com/starpilot/starview/MapTiles.madeAt Ljava/util/concurrent/ConcurrentHashMap;
      // 30c: checkcast java/util/Map
      // 30f: aload 5
      // 311: invokestatic java/lang/System.currentTimeMillis ()J
      // 314: invokestatic java/lang/Long.valueOf (J)Ljava/lang/Long;
      // 317: invokeinterface java/util/Map.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
      // 31c: pop
      // 31d: aload 0
      // 31e: ldc ""
      // 320: putfield com/starpilot/starview/MapTiles.lastError Ljava/lang/String;
      // 323: aload 16
      // 325: invokevirtual java/io/File.getParentFile ()Ljava/io/File;
      // 328: astore 15
      // 32a: aload 15
      // 32c: ifnull 335
      // 32f: aload 15
      // 331: invokevirtual java/io/File.mkdirs ()Z
      // 334: pop
      // 335: new java/io/File
      // 338: astore 18
      // 33a: aload 16
      // 33c: invokevirtual java/io/File.getPath ()Ljava/lang/String;
      // 33f: astore 15
      // 341: new java/lang/StringBuilder
      // 344: astore 17
      // 346: aload 17
      // 348: invokespecial java/lang/StringBuilder.<init> ()V
      // 34b: aload 18
      // 34d: aload 17
      // 34f: aload 15
      // 351: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 354: ldc_w ".tmp"
      // 357: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 35a: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 35d: invokespecial java/io/File.<init> (Ljava/lang/String;)V
      // 360: aload 18
      // 362: aload 1
      // 363: invokestatic kotlin/io/FilesKt.writeBytes (Ljava/io/File;[B)V
      // 366: aload 18
      // 368: aload 16
      // 36a: invokevirtual java/io/File.renameTo (Ljava/io/File;)Z
      // 36d: pop
      // 36e: goto 372
      // 371: astore 1
      // 372: iload 6
      // 374: ifeq 385
      // 377: aload 0
      // 378: getfield com/starpilot/starview/MapTiles.wanted Ljava/util/Set;
      // 37b: aload 5
      // 37d: invokeinterface java/util/Set.contains (Ljava/lang/Object;)Z 2
      // 382: ifeq 38f
      // 385: aload 0
      // 386: getfield com/starpilot/starview/MapTiles.onLoaded Lkotlin/jvm/functions/Function0;
      // 389: invokeinterface kotlin/jvm/functions/Function0.invoke ()Ljava/lang/Object; 1
      // 38e: pop
      // 38f: goto 3da
      // 392: astore 1
      // 393: goto 39b
      // 396: astore 1
      // 397: goto 3e6
      // 39a: astore 1
      // 39b: aload 0
      // 39c: aload 0
      // 39d: getfield com/starpilot/starview/MapTiles.failures I
      // 3a0: bipush 1
      // 3a1: iadd
      // 3a2: putfield com/starpilot/starview/MapTiles.failures I
      // 3a5: aload 1
      // 3a6: invokevirtual java/lang/Exception.getMessage ()Ljava/lang/String;
      // 3a9: astore 16
      // 3ab: aload 16
      // 3ad: astore 15
      // 3af: aload 16
      // 3b1: ifnonnull 3ba
      // 3b4: aload 1
      // 3b5: invokevirtual java/lang/Exception.toString ()Ljava/lang/String;
      // 3b8: astore 15
      // 3ba: aload 0
      // 3bb: aload 15
      // 3bd: putfield com/starpilot/starview/MapTiles.lastError Ljava/lang/String;
      // 3c0: aload 0
      // 3c1: getfield com/starpilot/starview/MapTiles.failedUntil Ljava/util/concurrent/ConcurrentHashMap;
      // 3c4: checkcast java/util/Map
      // 3c7: aload 5
      // 3c9: invokestatic java/lang/System.currentTimeMillis ()J
      // 3cc: sipush 15000
      // 3cf: i2l
      // 3d0: ladd
      // 3d1: invokestatic java/lang/Long.valueOf (J)Ljava/lang/Long;
      // 3d4: invokeinterface java/util/Map.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
      // 3d9: pop
      // 3da: aload 0
      // 3db: getfield com/starpilot/starview/MapTiles.pending Ljava/util/concurrent/ConcurrentHashMap$KeySetView;
      // 3de: aload 5
      // 3e0: invokevirtual java/util/concurrent/ConcurrentHashMap$KeySetView.remove (Ljava/lang/Object;)Z
      // 3e3: pop
      // 3e4: return
      // 3e5: astore 1
      // 3e6: aload 0
      // 3e7: getfield com/starpilot/starview/MapTiles.pending Ljava/util/concurrent/ConcurrentHashMap$KeySetView;
      // 3ea: aload 5
      // 3ec: invokevirtual java/util/concurrent/ConcurrentHashMap$KeySetView.remove (Ljava/lang/Object;)Z
      // 3ef: pop
      // 3f0: aload 1
      // 3f1: athrow
   }

   private static final void prefetch$lambda$2(MapTiles var0, String var1, int var2, int var3, int var4, String var5) {
      var0.load(var1, var2, var3, var4, var5, true);
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   private final void prune() {
      long var2;
      List var21;
      ArrayList var25;
      Iterator var32;
      try {
         Sequence var4 = FilesKt.walkTopDown(this.dir);
         MapTiles$$ExternalSyntheticLambda2 var5 = new MapTiles$$ExternalSyntheticLambda2();
         var21 = SequencesKt.toMutableList(SequencesKt.filter(var4, var5));
         var2 = System.currentTimeMillis();
         Iterable var6 = var21;
         var25 = new ArrayList();
         var25 = var25;
         var32 = var6.iterator();
      } catch (Exception var18) {
         return;
      }

      while (true) {
         boolean var1;
         Object var7;
         label110: {
            label109: {
               try {
                  if (!var32.hasNext()) {
                     break;
                  }

                  var7 = var32.next();
                  if (((File)var7).lastModified() < var2 - 2592000000L) {
                     break label109;
                  }
               } catch (Exception var19) {
                  return;
               }

               var1 = false;
               break label110;
            }

            var1 = true;
         }

         if (var1) {
            try {
               var25.add(var7);
            } catch (Exception var17) {
               return;
            }
         }
      }

      try {
         var25 = var25;
      } catch (Exception var16) {
         return;
      }

      try {
         var27 = var25.iterator();
      } catch (Exception var14) {
         return;
      }

      while (true) {
         try {
            if (!var27.hasNext()) {
               break;
            }

            ((File)var27.next()).delete();
         } catch (Exception var15) {
            return;
         }
      }

      try {
         MapTiles$$ExternalSyntheticLambda3 var28 = new MapTiles$$ExternalSyntheticLambda3();
         CollectionsKt.removeAll(var21, var28);
         var32 = var21.iterator();
      } catch (Exception var13) {
         return;
      }

      var2 = 0L;

      while (true) {
         try {
            if (!var32.hasNext()) {
               break;
            }

            var29 = (File)var32.next();
         } catch (Exception var12) {
            return;
         }

         try {
            var2 += var29.length();
         } catch (Exception var11) {
            return;
         }
      }

      if (var2 > 262144000L) {
         try {
            Iterable var22 = var21;
            Comparator var30 = new Comparator() {
               @Override
               public final int compare(T var1, T var2x) {
                  return ComparisonsKt.compareValues(((File)var1).lastModified(), ((File)var2x).lastModified());
               }
            };
            var31 = CollectionsKt.sortedWith(var22, var30).iterator();
         } catch (Exception var10) {
            return;
         }

         while (true) {
            try {
               if (!var31.hasNext()) {
                  break;
               }

               var23 = (File)var31.next();
            } catch (Exception var9) {
               break;
            }

            if (var2 <= 2.097152E8) {
               break;
            }

            try {
               var2 -= var23.length();
               var23.delete();
            } catch (Exception var8) {
               break;
            }
         }
      }
   }

   private static final boolean prune$lambda$12(File var0) {
      Intrinsics.checkNotNullParameter(var0, "it");
      return var0.exists() ^ true;
   }

   private static final boolean prune$lambda$9(File var0) {
      Intrinsics.checkNotNullParameter(var0, "it");
      return var0.isFile();
   }

   private final void request(String var1, int var2, int var3, int var4, String var5) {
      Long var8 = this.failedUntil.get(var5);
      long var6;
      if (var8 != null) {
         var6 = var8;
      } else {
         var6 = 0L;
      }

      if (var6 <= System.currentTimeMillis() && this.pending.add(var5)) {
         this.pool.execute(new MapTiles$$ExternalSyntheticLambda4(this, var1, var2, var3, var4, var5));
      }
   }

   private static final void request$lambda$3(MapTiles var0, String var1, int var2, int var3, int var4, String var5) {
      var0.load(var1, var2, var3, var4, var5, false);
   }

   public final void clearMemory() {
      this.mem.evictAll();
   }

   public final Bitmap get(String var1, int var2, int var3, int var4) {
      Intrinsics.checkNotNullParameter(var1, "style");
      String var11 = Companion.key(var1, var2, var3, var4);
      Bitmap var9 = (Bitmap)this.mem.get(var11);
      if (var9 != null) {
         if (!StringsKt.startsWith$default(var1, "traffic", false, 2, null)) {
            return var9;
         }

         long var7 = System.currentTimeMillis();
         Long var10 = this.madeAt.get(var11);
         long var5;
         if (var10 != null) {
            var5 = var10;
         } else {
            var5 = 0L;
         }

         if (var7 - var5 <= 900000L) {
            return var9;
         }
      }

      this.request(var1, var2, var3, var4, var11);
      return var9;
   }

   public final String getDirectKey() {
      return this.directKey;
   }

   public final int getFailures() {
      return this.failures;
   }

   public final String getHost() {
      return this.host;
   }

   public final String getLastError() {
      return this.lastError;
   }

   public final Set<String> getWanted() {
      return this.wanted;
   }

   public final Bitmap peek(String var1, int var2, int var3, int var4) {
      Intrinsics.checkNotNullParameter(var1, "style");
      return (Bitmap)this.mem.get(Companion.key(var1, var2, var3, var4));
   }

   public final void prefetch(String var1, Collection<Triple<Integer, Integer, Integer>> var2) {
      Intrinsics.checkNotNullParameter(var1, "style");
      Intrinsics.checkNotNullParameter(var2, "tiles");

      for (Triple var8 : var2) {
         int var4 = ((Number)var8.component1()).intValue();
         int var3 = ((Number)var8.component2()).intValue();
         int var5 = ((Number)var8.component3()).intValue();
         String var13 = Companion.key(var1, var4, var3, var5);
         if (this.mem.get(var13) == null) {
            Long var9 = this.failedUntil.get(var13);
            long var6;
            if (var9 != null) {
               var6 = var9;
            } else {
               var6 = 0L;
            }

            if (var6 <= System.currentTimeMillis() && this.pending.add(var13)) {
               try {
                  ThreadPoolExecutor var14 = this.ahead;
                  MapTiles$$ExternalSyntheticLambda0 var10 = new MapTiles$$ExternalSyntheticLambda0(this, var1, var4, var3, var5, var13);
                  var14.execute(var10);
               } catch (RejectedExecutionException var11) {
                  this.pending.remove(var13);
                  return;
               }
            }
         }
      }
   }

   public final void setDirectKey(String var1) {
      this.directKey = var1;
   }

   public final void setHost(String var1) {
      this.host = var1;
   }

   public final void setWanted(Set<String> var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.wanted = var1;
   }

   public final int takeDirectCount() {
      return this.direct.getAndSet(0);
   }

   public static final class Companion {
      private Companion() {
      }

      // $VF: synthetic method
      public Companion(DefaultConstructorMarker var1) {
         this();
      }

      public final Map<String, String> getMAPBOX_STYLES() {
         return MapTiles.MAPBOX_STYLES;
      }

      public final String key(String var1, int var2, int var3, int var4) {
         Intrinsics.checkNotNullParameter(var1, "style");
         return var1 + "/" + var2 + "/" + var3 + "/" + var4;
      }
   }
}
