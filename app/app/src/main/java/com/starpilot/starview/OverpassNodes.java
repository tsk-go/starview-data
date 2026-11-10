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

public class OverpassNodes {
   private File dir;
   private final String dirName;
   private final ConcurrentHashMap<String, Boolean> done;
   private final Lazy http$delegate;
   private final String kind;
   private final ExecutorService pool;
   private final String query;

   // $VF: synthetic method
   public static void $r8$lambda$HHkU0FLWXDWLXNmWYLlexH_Mevo(OverpassNodes var0, String var1, int var2, int var3) {
      want$lambda$4(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static OkHttpClient $r8$lambda$zH2_6nLdWRze_1L3JkSf2TfKuoY/* $VF was: $r8$lambda$zH2_6nLdWRze_1L3JkSf2TfKuoY*/() {
      return http_delegate$lambda$0();
   }

   public OverpassNodes(String var1, String var2, String var3) {
      Intrinsics.checkNotNullParameter(var1, "dirName");
      Intrinsics.checkNotNullParameter(var2, "query");
      Intrinsics.checkNotNullParameter(var3, "kind");
      super();
      this.dirName = var1;
      this.query = var2;
      this.kind = var3;
      this.done = new ConcurrentHashMap<>();
      this.pool = Executors.newSingleThreadExecutor();
      this.http$delegate = LazyKt.lazy(new OverpassNodes$$ExternalSyntheticLambda0());
   }

   private final OkHttpClient getHttp() {
      return (OkHttpClient)this.http$delegate.getValue();
   }

   private static final OkHttpClient http_delegate$lambda$0() {
      return new Builder().callTimeout(40L, TimeUnit.SECONDS).build();
   }

   private static final void want$lambda$4(OverpassNodes param0, String param1, int param2, int param3) {
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
      // 003: astore 5
      // 005: aload 0
      // 006: getfield com/starpilot/starview/OverpassNodes.dir Ljava/io/File;
      // 009: astore 4
      // 00b: aload 4
      // 00d: ifnonnull 011
      // 010: return
      // 011: new java/lang/StringBuilder
      // 014: astore 6
      // 016: aload 6
      // 018: invokespecial java/lang/StringBuilder.<init> ()V
      // 01b: aload 5
      // 01d: aload 4
      // 01f: aload 6
      // 021: aload 1
      // 022: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 025: ldc ".json"
      // 027: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 02a: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 02d: invokespecial java/io/File.<init> (Ljava/io/File;Ljava/lang/String;)V
      // 030: aload 5
      // 032: invokevirtual java/io/File.isFile ()Z
      // 035: ifeq 045
      // 038: aload 5
      // 03a: aconst_null
      // 03b: bipush 1
      // 03c: aconst_null
      // 03d: invokestatic kotlin/io/FilesKt.readText$default (Ljava/io/File;Ljava/nio/charset/Charset;ILjava/lang/Object;)Ljava/lang/String;
      // 040: astore 4
      // 042: goto 150
      // 045: aload 0
      // 046: getfield com/starpilot/starview/OverpassNodes.query Ljava/lang/String;
      // 049: astore 4
      // 04b: getstatic kotlin/jvm/internal/StringCompanionObject.INSTANCE Lkotlin/jvm/internal/StringCompanionObject;
      // 04e: astore 6
      // 050: getstatic java/util/Locale.US Ljava/util/Locale;
      // 053: ldc "%.4f,%.4f,%.4f,%.4f"
      // 055: bipush 4
      // 056: anewarray 4
      // 059: dup
      // 05a: bipush 0
      // 05b: iload 2
      // 05c: i2d
      // 05d: ldc2_w 0.01
      // 060: dmul
      // 061: invokestatic java/lang/Double.valueOf (D)Ljava/lang/Double;
      // 064: aastore
      // 065: dup
      // 066: bipush 1
      // 067: iload 3
      // 068: i2d
      // 069: ldc2_w 0.01
      // 06c: dmul
      // 06d: invokestatic java/lang/Double.valueOf (D)Ljava/lang/Double;
      // 070: aastore
      // 071: dup
      // 072: bipush 2
      // 073: iload 2
      // 074: i2d
      // 075: ldc2_w 0.01
      // 078: dmul
      // 079: ldc2_w 0.01
      // 07c: dadd
      // 07d: invokestatic java/lang/Double.valueOf (D)Ljava/lang/Double;
      // 080: aastore
      // 081: dup
      // 082: bipush 3
      // 083: iload 3
      // 084: i2d
      // 085: ldc2_w 0.01
      // 088: dmul
      // 089: ldc2_w 0.01
      // 08c: dadd
      // 08d: invokestatic java/lang/Double.valueOf (D)Ljava/lang/Double;
      // 090: aastore
      // 091: bipush 4
      // 092: invokestatic java/util/Arrays.copyOf ([Ljava/lang/Object;I)[Ljava/lang/Object;
      // 095: invokestatic java/lang/String.format (Ljava/util/Locale;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
      // 098: astore 6
      // 09a: aload 6
      // 09c: ldc "format(...)"
      // 09e: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 0a1: new java/lang/StringBuilder
      // 0a4: astore 7
      // 0a6: aload 7
      // 0a8: invokespecial java/lang/StringBuilder.<init> ()V
      // 0ab: aload 7
      // 0ad: ldc "[out:json][timeout:25];"
      // 0af: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 0b2: aload 4
      // 0b4: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 0b7: ldc "("
      // 0b9: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 0bc: aload 6
      // 0be: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 0c1: ldc ");out;"
      // 0c3: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 0c6: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 0c9: astore 4
      // 0cb: new okhttp3/Request$Builder
      // 0ce: astore 6
      // 0d0: aload 6
      // 0d2: invokespecial okhttp3/Request$Builder.<init> ()V
      // 0d5: aload 6
      // 0d7: ldc "https://overpass-api.de/api/interpreter"
      // 0d9: invokevirtual okhttp3/Request$Builder.url (Ljava/lang/String;)Lokhttp3/Request$Builder;
      // 0dc: ldc "User-Agent"
      // 0de: ldc "StarView (openpilot tablet display)"
      // 0e0: invokevirtual okhttp3/Request$Builder.header (Ljava/lang/String;Ljava/lang/String;)Lokhttp3/Request$Builder;
      // 0e3: astore 7
      // 0e5: new okhttp3/FormBody$Builder
      // 0e8: astore 6
      // 0ea: aload 6
      // 0ec: aconst_null
      // 0ed: bipush 1
      // 0ee: aconst_null
      // 0ef: invokespecial okhttp3/FormBody$Builder.<init> (Ljava/nio/charset/Charset;ILkotlin/jvm/internal/DefaultConstructorMarker;)V
      // 0f2: aload 7
      // 0f4: aload 6
      // 0f6: ldc "data"
      // 0f8: aload 4
      // 0fa: invokevirtual okhttp3/FormBody$Builder.add (Ljava/lang/String;Ljava/lang/String;)Lokhttp3/FormBody$Builder;
      // 0fd: invokevirtual okhttp3/FormBody$Builder.build ()Lokhttp3/FormBody;
      // 100: checkcast okhttp3/RequestBody
      // 103: invokevirtual okhttp3/Request$Builder.post (Lokhttp3/RequestBody;)Lokhttp3/Request$Builder;
      // 106: invokevirtual okhttp3/Request$Builder.build ()Lokhttp3/Request;
      // 109: astore 4
      // 10b: aload 0
      // 10c: invokespecial com/starpilot/starview/OverpassNodes.getHttp ()Lokhttp3/OkHttpClient;
      // 10f: aload 4
      // 111: invokevirtual okhttp3/OkHttpClient.newCall (Lokhttp3/Request;)Lokhttp3/Call;
      // 114: invokeinterface okhttp3/Call.execute ()Lokhttp3/Response; 1
      // 119: checkcast java/io/Closeable
      // 11c: astore 6
      // 11e: aload 6
      // 120: checkcast okhttp3/Response
      // 123: astore 7
      // 125: aload 7
      // 127: invokevirtual okhttp3/Response.isSuccessful ()Z
      // 12a: ifeq 1b1
      // 12d: aload 7
      // 12f: invokevirtual okhttp3/Response.body ()Lokhttp3/ResponseBody;
      // 132: astore 4
      // 134: aload 4
      // 136: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNull (Ljava/lang/Object;)V
      // 139: aload 4
      // 13b: invokevirtual okhttp3/ResponseBody.string ()Ljava/lang/String;
      // 13e: astore 4
      // 140: aload 6
      // 142: aconst_null
      // 143: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 146: aload 5
      // 148: aload 4
      // 14a: aconst_null
      // 14b: bipush 2
      // 14c: aconst_null
      // 14d: invokestatic kotlin/io/FilesKt.writeText$default (Ljava/io/File;Ljava/lang/String;Ljava/nio/charset/Charset;ILjava/lang/Object;)V
      // 150: new org/json/JSONObject
      // 153: astore 5
      // 155: aload 5
      // 157: aload 4
      // 159: invokespecial org/json/JSONObject.<init> (Ljava/lang/String;)V
      // 15c: aload 5
      // 15e: ldc_w "elements"
      // 161: invokevirtual org/json/JSONObject.optJSONArray (Ljava/lang/String;)Lorg/json/JSONArray;
      // 164: astore 5
      // 166: aload 5
      // 168: ifnonnull 16c
      // 16b: return
      // 16c: bipush 0
      // 16d: istore 2
      // 16e: aload 5
      // 170: invokevirtual org/json/JSONArray.length ()I
      // 173: istore 3
      // 174: iload 2
      // 175: iload 3
      // 176: if_icmpge 1f9
      // 179: aload 5
      // 17b: iload 2
      // 17c: invokevirtual org/json/JSONArray.getJSONObject (I)Lorg/json/JSONObject;
      // 17f: astore 4
      // 181: getstatic com/starpilot/starview/IdeaSpots.INSTANCE Lcom/starpilot/starview/IdeaSpots;
      // 184: aload 0
      // 185: getfield com/starpilot/starview/OverpassNodes.kind Ljava/lang/String;
      // 188: aload 4
      // 18a: ldc_w "lat"
      // 18d: invokevirtual org/json/JSONObject.getDouble (Ljava/lang/String;)D
      // 190: aload 4
      // 192: ldc_w "lon"
      // 195: invokevirtual org/json/JSONObject.getDouble (Ljava/lang/String;)D
      // 198: ldc2_w NaN
      // 19b: dconst_0
      // 19c: aconst_null
      // 19d: lconst_0
      // 19e: ldc2_w 3.0
      // 1a1: ldc_w "osm"
      // 1a4: bipush 112
      // 1a6: aconst_null
      // 1a7: invokestatic com/starpilot/starview/IdeaSpots.record$default (Lcom/starpilot/starview/IdeaSpots;Ljava/lang/String;DDDDLjava/lang/String;JDLjava/lang/String;ILjava/lang/Object;)Lcom/starpilot/starview/IdeaSpots$Spot;
      // 1aa: pop
      // 1ab: iinc 2 1
      // 1ae: goto 174
      // 1b1: new java/io/IOException
      // 1b4: astore 4
      // 1b6: aload 7
      // 1b8: invokevirtual okhttp3/Response.code ()I
      // 1bb: istore 2
      // 1bc: new java/lang/StringBuilder
      // 1bf: astore 5
      // 1c1: aload 5
      // 1c3: invokespecial java/lang/StringBuilder.<init> ()V
      // 1c6: aload 4
      // 1c8: aload 5
      // 1ca: ldc_w "HTTP "
      // 1cd: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 1d0: iload 2
      // 1d1: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
      // 1d4: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 1d7: invokespecial java/io/IOException.<init> (Ljava/lang/String;)V
      // 1da: aload 4
      // 1dc: athrow
      // 1dd: astore 4
      // 1df: aload 4
      // 1e1: athrow
      // 1e2: astore 5
      // 1e4: aload 6
      // 1e6: aload 4
      // 1e8: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 1eb: aload 5
      // 1ed: athrow
      // 1ee: astore 4
      // 1f0: aload 0
      // 1f1: getfield com/starpilot/starview/OverpassNodes.done Ljava/util/concurrent/ConcurrentHashMap;
      // 1f4: aload 1
      // 1f5: invokevirtual java/util/concurrent/ConcurrentHashMap.remove (Ljava/lang/Object;)Ljava/lang/Object;
      // 1f8: pop
      // 1f9: return
   }

   public final void init(Context var1) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      File var2 = new File(var1.getFilesDir(), this.dirName);
      var2.mkdirs();
      this.dir = var2;
   }

   public final void want(double var1, double var3) {
      int var5 = (int)Math.floor(var1 / 0.01);
      int var6 = (int)Math.floor(var3 / 0.01);
      String var7 = var5 + "_" + var6;
      if (this.done.putIfAbsent(var7, true) == null) {
         this.pool.execute(new OverpassNodes$$ExternalSyntheticLambda1(this, var7, var5, var6));
      }
   }
}
