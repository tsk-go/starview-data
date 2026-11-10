package com.starpilot.starview;

import android.content.Context;
import java.io.File;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;

public final class MapBumps {
   public static final MapBumps INSTANCE = new MapBumps();
   private static File dir;
   private static final ConcurrentHashMap<String, Boolean> done = new ConcurrentHashMap<>();
   private static final Lazy http$delegate = LazyKt.lazy(new MapBumps$$ExternalSyntheticLambda1());
   private static final ExecutorService pool = Executors.newSingleThreadExecutor();

   // $VF: synthetic method
   public static OkHttpClient $r8$lambda$SNg0zEcCRTgwlchXGgSAM_E_3pU() {
      return http_delegate$lambda$0();
   }

   // $VF: synthetic method
   public static void $r8$lambda$s6u05NdWmJdo4mL3YxRoDo31vYY(String var0, int var1, int var2) {
      want$lambda$2(var0, var1, var2);
   }

   private MapBumps() {
   }

   private final OkHttpClient getHttp() {
      return (OkHttpClient)http$delegate.getValue();
   }

   private static final OkHttpClient http_delegate$lambda$0() {
      return new Builder().callTimeout(40L, TimeUnit.SECONDS).build();
   }

   private final void load(String param1, double param2, double param4) {
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
      // 000: new java/io/File
      // 003: astore 9
      // 005: getstatic com/starpilot/starview/MapBumps.dir Ljava/io/File;
      // 008: astore 8
      // 00a: aload 8
      // 00c: ifnonnull 010
      // 00f: return
      // 010: new java/lang/StringBuilder
      // 013: astore 10
      // 015: aload 10
      // 017: invokespecial java/lang/StringBuilder.<init> ()V
      // 01a: aload 9
      // 01c: aload 8
      // 01e: aload 10
      // 020: aload 1
      // 021: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 024: ldc ".json"
      // 026: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 029: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 02c: invokespecial java/io/File.<init> (Ljava/io/File;Ljava/lang/String;)V
      // 02f: aload 9
      // 031: invokevirtual java/io/File.isFile ()Z
      // 034: ifeq 044
      // 037: aload 9
      // 039: aconst_null
      // 03a: bipush 1
      // 03b: aconst_null
      // 03c: invokestatic kotlin/io/FilesKt.readText$default (Ljava/io/File;Ljava/nio/charset/Charset;ILjava/lang/Object;)Ljava/lang/String;
      // 03f: astore 8
      // 041: goto 12d
      // 044: getstatic kotlin/jvm/internal/StringCompanionObject.INSTANCE Lkotlin/jvm/internal/StringCompanionObject;
      // 047: astore 8
      // 049: getstatic java/util/Locale.US Ljava/util/Locale;
      // 04c: ldc "%.4f,%.4f,%.4f,%.4f"
      // 04e: bipush 4
      // 04f: anewarray 4
      // 052: dup
      // 053: bipush 0
      // 054: dload 2
      // 055: invokestatic java/lang/Double.valueOf (D)Ljava/lang/Double;
      // 058: aastore
      // 059: dup
      // 05a: bipush 1
      // 05b: dload 4
      // 05d: invokestatic java/lang/Double.valueOf (D)Ljava/lang/Double;
      // 060: aastore
      // 061: dup
      // 062: bipush 2
      // 063: dload 2
      // 064: ldc2_w 0.01
      // 067: dadd
      // 068: invokestatic java/lang/Double.valueOf (D)Ljava/lang/Double;
      // 06b: aastore
      // 06c: dup
      // 06d: bipush 3
      // 06e: dload 4
      // 070: ldc2_w 0.01
      // 073: dadd
      // 074: invokestatic java/lang/Double.valueOf (D)Ljava/lang/Double;
      // 077: aastore
      // 078: bipush 4
      // 079: invokestatic java/util/Arrays.copyOf ([Ljava/lang/Object;I)[Ljava/lang/Object;
      // 07c: invokestatic java/lang/String.format (Ljava/util/Locale;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
      // 07f: astore 8
      // 081: aload 8
      // 083: ldc "format(...)"
      // 085: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 088: new java/lang/StringBuilder
      // 08b: astore 10
      // 08d: aload 10
      // 08f: invokespecial java/lang/StringBuilder.<init> ()V
      // 092: aload 10
      // 094: ldc "[out:json][timeout:25];node[\"traffic_calming\"~\"^(bump|hump|table|cushion|yes)$\"]("
      // 096: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 099: aload 8
      // 09b: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 09e: ldc ");out;"
      // 0a0: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 0a3: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 0a6: astore 8
      // 0a8: new okhttp3/Request$Builder
      // 0ab: astore 10
      // 0ad: aload 10
      // 0af: invokespecial okhttp3/Request$Builder.<init> ()V
      // 0b2: aload 10
      // 0b4: ldc "https://overpass-api.de/api/interpreter"
      // 0b6: invokevirtual okhttp3/Request$Builder.url (Ljava/lang/String;)Lokhttp3/Request$Builder;
      // 0b9: ldc "User-Agent"
      // 0bb: ldc "StarView (openpilot tablet display)"
      // 0bd: invokevirtual okhttp3/Request$Builder.header (Ljava/lang/String;Ljava/lang/String;)Lokhttp3/Request$Builder;
      // 0c0: astore 11
      // 0c2: new okhttp3/FormBody$Builder
      // 0c5: astore 10
      // 0c7: aload 10
      // 0c9: aconst_null
      // 0ca: bipush 1
      // 0cb: aconst_null
      // 0cc: invokespecial okhttp3/FormBody$Builder.<init> (Ljava/nio/charset/Charset;ILkotlin/jvm/internal/DefaultConstructorMarker;)V
      // 0cf: aload 11
      // 0d1: aload 10
      // 0d3: ldc "data"
      // 0d5: aload 8
      // 0d7: invokevirtual okhttp3/FormBody$Builder.add (Ljava/lang/String;Ljava/lang/String;)Lokhttp3/FormBody$Builder;
      // 0da: invokevirtual okhttp3/FormBody$Builder.build ()Lokhttp3/FormBody;
      // 0dd: checkcast okhttp3/RequestBody
      // 0e0: invokevirtual okhttp3/Request$Builder.post (Lokhttp3/RequestBody;)Lokhttp3/Request$Builder;
      // 0e3: invokevirtual okhttp3/Request$Builder.build ()Lokhttp3/Request;
      // 0e6: astore 8
      // 0e8: aload 0
      // 0e9: invokespecial com/starpilot/starview/MapBumps.getHttp ()Lokhttp3/OkHttpClient;
      // 0ec: aload 8
      // 0ee: invokevirtual okhttp3/OkHttpClient.newCall (Lokhttp3/Request;)Lokhttp3/Call;
      // 0f1: invokeinterface okhttp3/Call.execute ()Lokhttp3/Response; 1
      // 0f6: checkcast java/io/Closeable
      // 0f9: astore 10
      // 0fb: aload 10
      // 0fd: checkcast okhttp3/Response
      // 100: astore 8
      // 102: aload 8
      // 104: invokevirtual okhttp3/Response.isSuccessful ()Z
      // 107: ifeq 1c3
      // 10a: aload 8
      // 10c: invokevirtual okhttp3/Response.body ()Lokhttp3/ResponseBody;
      // 10f: astore 8
      // 111: aload 8
      // 113: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNull (Ljava/lang/Object;)V
      // 116: aload 8
      // 118: invokevirtual okhttp3/ResponseBody.string ()Ljava/lang/String;
      // 11b: astore 8
      // 11d: aload 10
      // 11f: aconst_null
      // 120: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 123: aload 9
      // 125: aload 8
      // 127: aconst_null
      // 128: bipush 2
      // 129: aconst_null
      // 12a: invokestatic kotlin/io/FilesKt.writeText$default (Ljava/io/File;Ljava/lang/String;Ljava/nio/charset/Charset;ILjava/lang/Object;)V
      // 12d: new org/json/JSONObject
      // 130: astore 9
      // 132: aload 9
      // 134: aload 8
      // 136: invokespecial org/json/JSONObject.<init> (Ljava/lang/String;)V
      // 139: aload 9
      // 13b: ldc_w "elements"
      // 13e: invokevirtual org/json/JSONObject.optJSONArray (Ljava/lang/String;)Lorg/json/JSONArray;
      // 141: astore 11
      // 143: aload 11
      // 145: ifnonnull 149
      // 148: return
      // 149: bipush 0
      // 14a: istore 6
      // 14c: aload 11
      // 14e: invokevirtual org/json/JSONArray.length ()I
      // 151: istore 7
      // 153: iload 6
      // 155: iload 7
      // 157: if_icmpge 20c
      // 15a: aload 11
      // 15c: iload 6
      // 15e: invokevirtual org/json/JSONArray.getJSONObject (I)Lorg/json/JSONObject;
      // 161: astore 8
      // 163: getstatic com/starpilot/starview/IdeaSpots.INSTANCE Lcom/starpilot/starview/IdeaSpots;
      // 166: astore 10
      // 168: aload 8
      // 16a: ldc_w "lat"
      // 16d: invokevirtual org/json/JSONObject.getDouble (Ljava/lang/String;)D
      // 170: dstore 2
      // 171: aload 8
      // 173: ldc_w "lon"
      // 176: invokevirtual org/json/JSONObject.getDouble (Ljava/lang/String;)D
      // 179: dstore 4
      // 17b: aload 8
      // 17d: ldc_w "tags"
      // 180: invokevirtual org/json/JSONObject.optJSONObject (Ljava/lang/String;)Lorg/json/JSONObject;
      // 183: astore 8
      // 185: aload 8
      // 187: ifnull 19d
      // 18a: aload 8
      // 18c: ldc_w "traffic_calming"
      // 18f: invokevirtual org/json/JSONObject.optString (Ljava/lang/String;)Ljava/lang/String;
      // 192: astore 9
      // 194: aload 9
      // 196: astore 8
      // 198: aload 9
      // 19a: ifnonnull 1a2
      // 19d: ldc_w ""
      // 1a0: astore 8
      // 1a2: aload 10
      // 1a4: ldc_w "map_bump"
      // 1a7: dload 2
      // 1a8: dload 4
      // 1aa: ldc2_w NaN
      // 1ad: dconst_0
      // 1ae: aload 8
      // 1b0: lconst_0
      // 1b1: ldc2_w 3.0
      // 1b4: aconst_null
      // 1b5: sipush 336
      // 1b8: aconst_null
      // 1b9: invokestatic com/starpilot/starview/IdeaSpots.record$default (Lcom/starpilot/starview/IdeaSpots;Ljava/lang/String;DDDDLjava/lang/String;JDLjava/lang/String;ILjava/lang/Object;)Lcom/starpilot/starview/IdeaSpots$Spot;
      // 1bc: pop
      // 1bd: iinc 6 1
      // 1c0: goto 153
      // 1c3: new java/io/IOException
      // 1c6: astore 9
      // 1c8: aload 8
      // 1ca: invokevirtual okhttp3/Response.code ()I
      // 1cd: istore 6
      // 1cf: new java/lang/StringBuilder
      // 1d2: astore 8
      // 1d4: aload 8
      // 1d6: invokespecial java/lang/StringBuilder.<init> ()V
      // 1d9: aload 9
      // 1db: aload 8
      // 1dd: ldc_w "HTTP "
      // 1e0: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 1e3: iload 6
      // 1e5: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
      // 1e8: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 1eb: invokespecial java/io/IOException.<init> (Ljava/lang/String;)V
      // 1ee: aload 9
      // 1f0: athrow
      // 1f1: astore 8
      // 1f3: aload 8
      // 1f5: athrow
      // 1f6: astore 9
      // 1f8: aload 10
      // 1fa: aload 8
      // 1fc: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 1ff: aload 9
      // 201: athrow
      // 202: astore 8
      // 204: getstatic com/starpilot/starview/MapBumps.done Ljava/util/concurrent/ConcurrentHashMap;
      // 207: aload 1
      // 208: invokevirtual java/util/concurrent/ConcurrentHashMap.remove (Ljava/lang/Object;)Ljava/lang/Object;
      // 20b: pop
      // 20c: return
   }

   private static final void want$lambda$2(String var0, int var1, int var2) {
      INSTANCE.load(var0, var1 * 0.01, var2 * 0.01);
   }

   public final void init(Context var1) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      File var2 = new File(var1.getFilesDir(), "map_bumps");
      var2.mkdirs();
      dir = var2;
   }

   public final void want(double var1, double var3) {
      int var5 = (int)Math.floor(var1 / 0.01);
      int var6 = (int)Math.floor(var3 / 0.01);
      String var7 = var5 + "_" + var6;
      if (done.putIfAbsent(var7, true) == null) {
         pool.execute(new MapBumps$$ExternalSyntheticLambda0(var7, var5, var6));
      }
   }
}
