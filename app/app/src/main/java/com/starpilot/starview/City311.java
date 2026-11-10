package com.starpilot.starview;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import kotlin.Lazy;
import kotlin.LazyKt;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;

public final class City311 {
   public static final City311 INSTANCE = new City311();
   private static final ConcurrentHashMap<String, Boolean> done = new ConcurrentHashMap<>();
   private static final Lazy http$delegate = LazyKt.lazy(new City311$$ExternalSyntheticLambda0());
   private static final ExecutorService pool = Executors.newSingleThreadExecutor();

   // $VF: synthetic method
   public static OkHttpClient $r8$lambda$05pu4trxH8mcidqF5S97FtiT0vA() {
      return http_delegate$lambda$0();
   }

   // $VF: synthetic method
   public static void $r8$lambda$VJSRPtT0Xf9QnVcLf41vgj1jYU4(int var0, int var1, String var2) {
      want$lambda$2(var0, var1, var2);
   }

   private City311() {
   }

   private final OkHttpClient getHttp() {
      return (OkHttpClient)http$delegate.getValue();
   }

   private static final OkHttpClient http_delegate$lambda$0() {
      return new Builder().callTimeout(30L, TimeUnit.SECONDS).build();
   }

   private static final void want$lambda$2(int param0, int param1, String param2) {
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
      // 000: new java/text/SimpleDateFormat
      // 003: astore 8
      // 005: aload 8
      // 007: ldc "yyyy-MM-dd'T'00:00:00"
      // 009: getstatic java/util/Locale.US Ljava/util/Locale;
      // 00c: invokespecial java/text/SimpleDateFormat.<init> (Ljava/lang/String;Ljava/util/Locale;)V
      // 00f: new java/util/Date
      // 012: astore 7
      // 014: aload 7
      // 016: invokestatic java/lang/System.currentTimeMillis ()J
      // 019: ldc2_w 3888000000
      // 01c: lsub
      // 01d: invokespecial java/util/Date.<init> (J)V
      // 020: aload 8
      // 022: aload 7
      // 024: invokevirtual java/text/SimpleDateFormat.format (Ljava/util/Date;)Ljava/lang/String;
      // 027: astore 8
      // 029: iload 0
      // 02a: i2d
      // 02b: ldc2_w 0.01
      // 02e: dmul
      // 02f: dstore 5
      // 031: iload 1
      // 032: i2d
      // 033: ldc2_w 0.01
      // 036: dmul
      // 037: dstore 3
      // 038: new java/lang/StringBuilder
      // 03b: astore 7
      // 03d: aload 7
      // 03f: invokespecial java/lang/StringBuilder.<init> ()V
      // 042: aload 7
      // 044: ldc "descriptor='Pothole' AND status='Open' AND created_date>'"
      // 046: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 049: aload 8
      // 04b: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 04e: ldc "' AND latitude between "
      // 050: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 053: dload 5
      // 055: invokevirtual java/lang/StringBuilder.append (D)Ljava/lang/StringBuilder;
      // 058: ldc " and "
      // 05a: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 05d: dload 5
      // 05f: ldc2_w 0.01
      // 062: dadd
      // 063: invokevirtual java/lang/StringBuilder.append (D)Ljava/lang/StringBuilder;
      // 066: ldc " AND longitude between "
      // 068: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 06b: dload 3
      // 06c: invokevirtual java/lang/StringBuilder.append (D)Ljava/lang/StringBuilder;
      // 06f: ldc " and "
      // 071: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 074: ldc2_w 0.01
      // 077: dload 3
      // 078: dadd
      // 079: invokevirtual java/lang/StringBuilder.append (D)Ljava/lang/StringBuilder;
      // 07c: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 07f: astore 8
      // 081: new okhttp3/HttpUrl$Builder
      // 084: astore 7
      // 086: aload 7
      // 088: invokespecial okhttp3/HttpUrl$Builder.<init> ()V
      // 08b: aload 7
      // 08d: ldc "https"
      // 08f: invokevirtual okhttp3/HttpUrl$Builder.scheme (Ljava/lang/String;)Lokhttp3/HttpUrl$Builder;
      // 092: ldc "data.cityofnewyork.us"
      // 094: invokevirtual okhttp3/HttpUrl$Builder.host (Ljava/lang/String;)Lokhttp3/HttpUrl$Builder;
      // 097: ldc "resource/erm2-nwe9.json"
      // 099: invokevirtual okhttp3/HttpUrl$Builder.addPathSegments (Ljava/lang/String;)Lokhttp3/HttpUrl$Builder;
      // 09c: ldc "$select"
      // 09e: ldc "latitude,longitude"
      // 0a0: invokevirtual okhttp3/HttpUrl$Builder.addQueryParameter (Ljava/lang/String;Ljava/lang/String;)Lokhttp3/HttpUrl$Builder;
      // 0a3: ldc "$where"
      // 0a5: aload 8
      // 0a7: invokevirtual okhttp3/HttpUrl$Builder.addQueryParameter (Ljava/lang/String;Ljava/lang/String;)Lokhttp3/HttpUrl$Builder;
      // 0aa: ldc "$limit"
      // 0ac: ldc "500"
      // 0ae: invokevirtual okhttp3/HttpUrl$Builder.addQueryParameter (Ljava/lang/String;Ljava/lang/String;)Lokhttp3/HttpUrl$Builder;
      // 0b1: invokevirtual okhttp3/HttpUrl$Builder.build ()Lokhttp3/HttpUrl;
      // 0b4: astore 8
      // 0b6: getstatic com/starpilot/starview/City311.INSTANCE Lcom/starpilot/starview/City311;
      // 0b9: invokespecial com/starpilot/starview/City311.getHttp ()Lokhttp3/OkHttpClient;
      // 0bc: astore 9
      // 0be: new okhttp3/Request$Builder
      // 0c1: astore 7
      // 0c3: aload 7
      // 0c5: invokespecial okhttp3/Request$Builder.<init> ()V
      // 0c8: aload 9
      // 0ca: aload 7
      // 0cc: aload 8
      // 0ce: invokevirtual okhttp3/Request$Builder.url (Lokhttp3/HttpUrl;)Lokhttp3/Request$Builder;
      // 0d1: invokevirtual okhttp3/Request$Builder.build ()Lokhttp3/Request;
      // 0d4: invokevirtual okhttp3/OkHttpClient.newCall (Lokhttp3/Request;)Lokhttp3/Call;
      // 0d7: invokeinterface okhttp3/Call.execute ()Lokhttp3/Response; 1
      // 0dc: checkcast java/io/Closeable
      // 0df: astore 7
      // 0e1: aload 7
      // 0e3: checkcast okhttp3/Response
      // 0e6: astore 9
      // 0e8: aload 9
      // 0ea: invokevirtual okhttp3/Response.isSuccessful ()Z
      // 0ed: ifeq 173
      // 0f0: aload 9
      // 0f2: invokevirtual okhttp3/Response.body ()Lokhttp3/ResponseBody;
      // 0f5: astore 8
      // 0f7: aload 8
      // 0f9: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNull (Ljava/lang/Object;)V
      // 0fc: aload 8
      // 0fe: invokevirtual okhttp3/ResponseBody.string ()Ljava/lang/String;
      // 101: astore 8
      // 103: aload 7
      // 105: aconst_null
      // 106: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 109: new org/json/JSONArray
      // 10c: astore 7
      // 10e: aload 7
      // 110: aload 8
      // 112: invokespecial org/json/JSONArray.<init> (Ljava/lang/String;)V
      // 115: bipush 0
      // 116: istore 0
      // 117: aload 7
      // 119: invokevirtual org/json/JSONArray.length ()I
      // 11c: istore 1
      // 11d: iload 0
      // 11e: iload 1
      // 11f: if_icmpge 170
      // 122: aload 7
      // 124: iload 0
      // 125: invokevirtual org/json/JSONArray.getJSONObject (I)Lorg/json/JSONObject;
      // 128: astore 8
      // 12a: aload 8
      // 12c: ldc_w "latitude"
      // 12f: invokevirtual org/json/JSONObject.optDouble (Ljava/lang/String;)D
      // 132: dstore 3
      // 133: aload 8
      // 135: ldc_w "longitude"
      // 138: invokevirtual org/json/JSONObject.optDouble (Ljava/lang/String;)D
      // 13b: dstore 5
      // 13d: dload 3
      // 13e: invokestatic java/lang/Double.isNaN (D)Z
      // 141: ifne 16a
      // 144: dload 5
      // 146: invokestatic java/lang/Double.isNaN (D)Z
      // 149: ifne 16a
      // 14c: getstatic com/starpilot/starview/IdeaSpots.INSTANCE Lcom/starpilot/starview/IdeaSpots;
      // 14f: ldc_w "pothole"
      // 152: dload 3
      // 153: dload 5
      // 155: ldc2_w NaN
      // 158: dconst_0
      // 159: ldc_w "NYC 311"
      // 15c: lconst_0
      // 15d: ldc2_w 8.0
      // 160: ldc_w "nyc311"
      // 163: bipush 80
      // 165: aconst_null
      // 166: invokestatic com/starpilot/starview/IdeaSpots.record$default (Lcom/starpilot/starview/IdeaSpots;Ljava/lang/String;DDDDLjava/lang/String;JDLjava/lang/String;ILjava/lang/Object;)Lcom/starpilot/starview/IdeaSpots$Spot;
      // 169: pop
      // 16a: iinc 0 1
      // 16d: goto 11d
      // 170: goto 1bf
      // 173: new java/io/IOException
      // 176: astore 8
      // 178: aload 9
      // 17a: invokevirtual okhttp3/Response.code ()I
      // 17d: istore 0
      // 17e: new java/lang/StringBuilder
      // 181: astore 9
      // 183: aload 9
      // 185: invokespecial java/lang/StringBuilder.<init> ()V
      // 188: aload 8
      // 18a: aload 9
      // 18c: ldc_w "HTTP "
      // 18f: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 192: iload 0
      // 193: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
      // 196: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 199: invokespecial java/io/IOException.<init> (Ljava/lang/String;)V
      // 19c: aload 8
      // 19e: athrow
      // 19f: astore 9
      // 1a1: aload 9
      // 1a3: athrow
      // 1a4: astore 8
      // 1a6: aload 7
      // 1a8: aload 9
      // 1aa: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 1ad: aload 8
      // 1af: athrow
      // 1b0: astore 7
      // 1b2: goto 1b7
      // 1b5: astore 7
      // 1b7: getstatic com/starpilot/starview/City311.done Ljava/util/concurrent/ConcurrentHashMap;
      // 1ba: aload 2
      // 1bb: invokevirtual java/util/concurrent/ConcurrentHashMap.remove (Ljava/lang/Object;)Ljava/lang/Object;
      // 1be: pop
      // 1bf: return
   }

   public final void want(double var1, double var3) {
      int var6 = 0;
      boolean var5;
      if (40.49 <= var1 && var1 <= 40.92) {
         var5 = 1;
      } else {
         var5 = 0;
      }

      if (var5) {
         var5 = (boolean)var6;
         if (-74.27 <= var3) {
            var5 = (boolean)var6;
            if (var3 <= -73.68) {
               var5 = (boolean)1;
            }
         }

         if (var5) {
            var6 = (int)Math.floor(var1 / 0.01);
            var5 = (int)Math.floor(var3 / 0.01);
            String var7 = var6 + "_" + var5;
            if (done.putIfAbsent(var7, true) != null) {
               return;
            }

            pool.execute(new City311$$ExternalSyntheticLambda1(var6, var5, var7));
            return;
         }
      }
   }
}
