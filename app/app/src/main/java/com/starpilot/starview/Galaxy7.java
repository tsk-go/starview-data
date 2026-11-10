package com.starpilot.starview;

import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.OkHttpClient.Builder;
import org.json.JSONObject;

public final class Galaxy7 {
   public static final Galaxy7.Companion Companion = new Galaxy7.Companion(null);
   private static volatile Galaxy7 inst;
   private final String base;
   private final OkHttpClient client;
   private volatile String cookie;
   private final String host;
   private final ExecutorService io;
   private final MediaType json;
   private final Handler main;
   private final Lazy slow$delegate;

   // $VF: synthetic method
   public static void $r8$lambda$MBVHSTtJICIfvivWgOIvMaYv7gA(Function2 var0, Pair var1) {
      postImage$lambda$12$lambda$11(var0, var1);
   }

   // $VF: synthetic method
   public static OkHttpClient $r8$lambda$_RbKyNTrXwA4zMRCBcbda7HfWs8(Galaxy7 var0) {
      return slow_delegate$lambda$7(var0);
   }

   // $VF: synthetic method
   public static JSONObject $r8$lambda$a35MVQjt7TjRkhhg8WJA0lhJr1w(Galaxy7 var0, String var1, JSONObject var2) {
      return post$lambda$14(var0, var1, var2);
   }

   // $VF: synthetic method
   public static JSONObject $r8$lambda$ckIk_MsT9Cx_bLtCgTIcUxGBM0I(Galaxy7 var0, String var1) {
      return get$lambda$13(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$eTL_oyHDW4GiSOeS9oVLbnA_SL8/* $VF was: $r8$lambda$eTL_oyHDW4GiSOeS9oVLbnA_SL8*/(Function2 var0, Pair var1) {
      async$lambda$16$lambda$15(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$gqZ_x_eQjqh4cXwC9irTC7C_VzI/* $VF was: $r8$lambda$gqZ_x_eQjqh4cXwC9irTC7C_VzI*/(Function0 var0, Galaxy7 var1, Function2 var2) {
      async$lambda$16(var0, var1, var2);
   }

   // $VF: synthetic method
   public static void $r8$lambda$yut5Pj6swEffw5Utb2GRLsgAyPA(Galaxy7 var0, String var1, JSONObject var2, Function2 var3) {
      postImage$lambda$12(var0, var1, var2, var3);
   }

   private Galaxy7(String var1) {
      this.host = var1;
      var1 = this.host;
      this.base = "http://" + var1 + ":8082";
      this.client = new Builder().connectTimeout(4L, TimeUnit.SECONDS).readTimeout(12L, TimeUnit.SECONDS).build();
      this.io = Executors.newFixedThreadPool(3);
      this.main = new Handler(Looper.getMainLooper());
      this.cookie = "";
      this.json = MediaType.Companion.get("application/json");
      this.slow$delegate = LazyKt.lazy(new Galaxy7$$ExternalSyntheticLambda1(this));
   }

   // $VF: synthetic method
   public Galaxy7(String var1, DefaultConstructorMarker var2) {
      this(var1);
   }

   private final void async(Function0<? extends JSONObject> var1, Function2<? super JSONObject, ? super String, Unit> var2) {
      this.io.execute(new Galaxy7$$ExternalSyntheticLambda4(var1, this, var2));
   }

   private static final void async$lambda$16(Function0 var0, Galaxy7 var1, Function2 var2) {
      try {
         var7 = TuplesKt.to(var0.invoke(), null);
      } catch (Galaxy7.Error var4) {
         String var3 = var4.getMessage();
         String var8 = var3;
         if (var3 == null) {
            var8 = "Galaxy error";
         }

         var7 = TuplesKt.to(null, var8);
      } catch (Exception var5) {
         String var6 = var5.getClass().getSimpleName();
         var7 = TuplesKt.to(null, "Can't reach Galaxy on the comma (" + var6 + ")");
      }

      var1.main.post(new Galaxy7$$ExternalSyntheticLambda0(var2, var7));
   }

   private static final void async$lambda$16$lambda$15(Function2 var0, Pair var1) {
      var0.invoke(var1.getFirst(), var1.getSecond());
   }

   private final JSONObject call(String param1, String param2, JSONObject param3, boolean param4) {
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
      // 001: bipush 0
      // 002: invokespecial com/starpilot/starview/Galaxy7.session (Z)V
      // 005: new okhttp3/Request$Builder
      // 008: dup
      // 009: invokespecial okhttp3/Request$Builder.<init> ()V
      // 00c: astore 8
      // 00e: aload 0
      // 00f: getfield com/starpilot/starview/Galaxy7.base Ljava/lang/String;
      // 012: astore 7
      // 014: aload 8
      // 016: new java/lang/StringBuilder
      // 019: dup
      // 01a: invokespecial java/lang/StringBuilder.<init> ()V
      // 01d: aload 7
      // 01f: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 022: aload 2
      // 023: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 026: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 029: invokevirtual okhttp3/Request$Builder.url (Ljava/lang/String;)Lokhttp3/Request$Builder;
      // 02c: ldc_w "Cookie"
      // 02f: aload 0
      // 030: getfield com/starpilot/starview/Galaxy7.cookie Ljava/lang/String;
      // 033: invokevirtual okhttp3/Request$Builder.header (Ljava/lang/String;Ljava/lang/String;)Lokhttp3/Request$Builder;
      // 036: astore 9
      // 038: aload 3
      // 039: ifnull 089
      // 03c: aload 9
      // 03e: ldc_w "Origin"
      // 041: aload 0
      // 042: getfield com/starpilot/starview/Galaxy7.base Ljava/lang/String;
      // 045: invokevirtual okhttp3/Request$Builder.header (Ljava/lang/String;Ljava/lang/String;)Lokhttp3/Request$Builder;
      // 048: astore 8
      // 04a: getstatic okhttp3/RequestBody.Companion Lokhttp3/RequestBody$Companion;
      // 04d: astore 7
      // 04f: aload 3
      // 050: invokevirtual org/json/JSONObject.toString ()Ljava/lang/String;
      // 053: astore 10
      // 055: aload 10
      // 057: ldc_w "toString(...)"
      // 05a: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 05d: aload 10
      // 05f: getstatic kotlin/text/Charsets.UTF_8 Ljava/nio/charset/Charset;
      // 062: invokevirtual java/lang/String.getBytes (Ljava/nio/charset/Charset;)[B
      // 065: astore 10
      // 067: aload 10
      // 069: ldc_w "getBytes(...)"
      // 06c: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 06f: aload 8
      // 071: aload 1
      // 072: aload 7
      // 074: aload 10
      // 076: aload 0
      // 077: getfield com/starpilot/starview/Galaxy7.json Lokhttp3/MediaType;
      // 07a: bipush 0
      // 07b: bipush 0
      // 07c: bipush 6
      // 07e: aconst_null
      // 07f: invokestatic okhttp3/RequestBody$Companion.create$default (Lokhttp3/RequestBody$Companion;[BLokhttp3/MediaType;IIILjava/lang/Object;)Lokhttp3/RequestBody;
      // 082: invokevirtual okhttp3/Request$Builder.method (Ljava/lang/String;Lokhttp3/RequestBody;)Lokhttp3/Request$Builder;
      // 085: pop
      // 086: goto 08f
      // 089: aload 9
      // 08b: invokevirtual okhttp3/Request$Builder.get ()Lokhttp3/Request$Builder;
      // 08e: pop
      // 08f: aload 0
      // 090: getfield com/starpilot/starview/Galaxy7.client Lokhttp3/OkHttpClient;
      // 093: aload 9
      // 095: invokevirtual okhttp3/Request$Builder.build ()Lokhttp3/Request;
      // 098: invokevirtual okhttp3/OkHttpClient.newCall (Lokhttp3/Request;)Lokhttp3/Call;
      // 09b: invokeinterface okhttp3/Call.execute ()Lokhttp3/Response; 1
      // 0a0: checkcast java/io/Closeable
      // 0a3: astore 9
      // 0a5: aload 9
      // 0a7: checkcast okhttp3/Response
      // 0aa: astore 10
      // 0ac: aload 10
      // 0ae: invokevirtual okhttp3/Response.body ()Lokhttp3/ResponseBody;
      // 0b1: astore 7
      // 0b3: aload 7
      // 0b5: ifnull 0c8
      // 0b8: aload 7
      // 0ba: invokevirtual okhttp3/ResponseBody.string ()Ljava/lang/String;
      // 0bd: astore 8
      // 0bf: aload 8
      // 0c1: astore 7
      // 0c3: aload 8
      // 0c5: ifnonnull 0cc
      // 0c8: ldc ""
      // 0ca: astore 7
      // 0cc: aload 10
      // 0ce: invokevirtual okhttp3/Response.code ()I
      // 0d1: istore 5
      // 0d3: aconst_null
      // 0d4: astore 8
      // 0d6: iload 5
      // 0d8: sipush 401
      // 0db: if_icmpne 0f9
      // 0de: iload 4
      // 0e0: ifeq 0f9
      // 0e3: aload 0
      // 0e4: bipush 1
      // 0e5: invokespecial com/starpilot/starview/Galaxy7.session (Z)V
      // 0e8: aload 0
      // 0e9: aload 1
      // 0ea: aload 2
      // 0eb: aload 3
      // 0ec: bipush 0
      // 0ed: invokespecial com/starpilot/starview/Galaxy7.call (Ljava/lang/String;Ljava/lang/String;Lorg/json/JSONObject;Z)Lorg/json/JSONObject;
      // 0f0: astore 1
      // 0f1: aload 9
      // 0f3: aconst_null
      // 0f4: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 0f7: aload 1
      // 0f8: areturn
      // 0f9: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 0fc: astore 1
      // 0fd: aload 0
      // 0fe: checkcast com/starpilot/starview/Galaxy7
      // 101: astore 1
      // 102: new org/json/JSONObject
      // 105: astore 1
      // 106: aload 1
      // 107: aload 7
      // 109: invokespecial org/json/JSONObject.<init> (Ljava/lang/String;)V
      // 10c: aload 1
      // 10d: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 110: astore 1
      // 111: goto 121
      // 114: astore 2
      // 115: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 118: astore 1
      // 119: aload 2
      // 11a: invokestatic kotlin/ResultKt.createFailure (Ljava/lang/Throwable;)Ljava/lang/Object;
      // 11d: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 120: astore 1
      // 121: aload 1
      // 122: astore 2
      // 123: aload 1
      // 124: invokestatic kotlin/Result.isFailure-impl (Ljava/lang/Object;)Z
      // 127: ifeq 12c
      // 12a: aconst_null
      // 12b: astore 2
      // 12c: aload 2
      // 12d: checkcast org/json/JSONObject
      // 130: astore 2
      // 131: aload 10
      // 133: invokevirtual okhttp3/Response.isSuccessful ()Z
      // 136: ifne 199
      // 139: aload 10
      // 13b: invokevirtual okhttp3/Response.code ()I
      // 13e: istore 5
      // 140: aload 2
      // 141: ifnull 16d
      // 144: aload 2
      // 145: ldc_w "error"
      // 148: invokevirtual org/json/JSONObject.optString (Ljava/lang/String;)Ljava/lang/String;
      // 14b: astore 1
      // 14c: aload 1
      // 14d: ifnull 16d
      // 150: aload 1
      // 151: checkcast java/lang/CharSequence
      // 154: astore 1
      // 155: aload 1
      // 156: invokestatic kotlin/text/StringsKt.isBlank (Ljava/lang/CharSequence;)Z
      // 159: ifeq 162
      // 15c: aload 8
      // 15e: astore 1
      // 15f: goto 162
      // 162: aload 1
      // 163: checkcast java/lang/String
      // 166: astore 2
      // 167: aload 2
      // 168: astore 1
      // 169: aload 2
      // 16a: ifnonnull 18c
      // 16d: aload 10
      // 16f: invokevirtual okhttp3/Response.code ()I
      // 172: istore 6
      // 174: new java/lang/StringBuilder
      // 177: astore 1
      // 178: aload 1
      // 179: invokespecial java/lang/StringBuilder.<init> ()V
      // 17c: aload 1
      // 17d: ldc_w "Galaxy answered HTTP "
      // 180: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 183: iload 6
      // 185: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
      // 188: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 18b: astore 1
      // 18c: new com/starpilot/starview/Galaxy7$Error
      // 18f: astore 2
      // 190: aload 2
      // 191: iload 5
      // 193: aload 1
      // 194: invokespecial com/starpilot/starview/Galaxy7$Error.<init> (ILjava/lang/String;)V
      // 197: aload 2
      // 198: athrow
      // 199: aload 2
      // 19a: astore 1
      // 19b: aload 2
      // 19c: ifnonnull 1a7
      // 19f: new org/json/JSONObject
      // 1a2: dup
      // 1a3: invokespecial org/json/JSONObject.<init> ()V
      // 1a6: astore 1
      // 1a7: aload 9
      // 1a9: aconst_null
      // 1aa: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 1ad: aload 1
      // 1ae: areturn
      // 1af: astore 2
      // 1b0: aload 2
      // 1b1: athrow
      // 1b2: astore 1
      // 1b3: aload 9
      // 1b5: aload 2
      // 1b6: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 1b9: aload 1
      // 1ba: athrow
   }

   // $VF: synthetic method
   static JSONObject call$default(Galaxy7 var0, String var1, String var2, JSONObject var3, boolean var4, int var5, Object var6) {
      if ((var5 & 8) != 0) {
         var4 = true;
      }

      return var0.call(var1, var2, var3, var4);
   }

   private static final JSONObject get$lambda$13(Galaxy7 var0, String var1) {
      return call$default(var0, "GET", var1, null, false, 8, null);
   }

   private final OkHttpClient getSlow() {
      return (OkHttpClient)this.slow$delegate.getValue();
   }

   private static final JSONObject post$lambda$14(Galaxy7 var0, String var1, JSONObject var2) {
      return call$default(var0, "POST", var1, var2, false, 8, null);
   }

   private static final void postImage$lambda$12(Galaxy7 param0, String param1, JSONObject param2, Function2 param3) {
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
      // 001: aload 1
      // 002: aload 2
      // 003: invokestatic com/starpilot/starview/Galaxy7.postImage$lambda$12$once (Lcom/starpilot/starview/Galaxy7;Ljava/lang/String;Lorg/json/JSONObject;)Lokhttp3/Response;
      // 006: astore 6
      // 008: aload 6
      // 00a: astore 5
      // 00c: aload 6
      // 00e: invokevirtual okhttp3/Response.code ()I
      // 011: sipush 401
      // 014: if_icmpne 029
      // 017: aload 6
      // 019: invokevirtual okhttp3/Response.close ()V
      // 01c: aload 0
      // 01d: bipush 1
      // 01e: invokespecial com/starpilot/starview/Galaxy7.session (Z)V
      // 021: aload 0
      // 022: aload 1
      // 023: aload 2
      // 024: invokestatic com/starpilot/starview/Galaxy7.postImage$lambda$12$once (Lcom/starpilot/starview/Galaxy7;Ljava/lang/String;Lorg/json/JSONObject;)Lokhttp3/Response;
      // 027: astore 5
      // 029: aload 5
      // 02b: checkcast java/io/Closeable
      // 02e: astore 5
      // 030: aload 5
      // 032: checkcast okhttp3/Response
      // 035: astore 2
      // 036: aload 2
      // 037: ldc_w "Content-Type"
      // 03a: aconst_null
      // 03b: bipush 2
      // 03c: aconst_null
      // 03d: invokestatic okhttp3/Response.header$default (Lokhttp3/Response;Ljava/lang/String;Ljava/lang/String;ILjava/lang/Object;)Ljava/lang/String;
      // 040: astore 1
      // 041: aload 1
      // 042: ifnull 075
      // 045: aload 1
      // 046: ldc_w ";"
      // 049: aconst_null
      // 04a: bipush 2
      // 04b: aconst_null
      // 04c: invokestatic kotlin/text/StringsKt.substringBefore$default (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/Object;)Ljava/lang/String;
      // 04f: astore 1
      // 050: aload 1
      // 051: ifnull 075
      // 054: aload 1
      // 055: checkcast java/lang/CharSequence
      // 058: invokestatic kotlin/text/StringsKt.trim (Ljava/lang/CharSequence;)Ljava/lang/CharSequence;
      // 05b: invokevirtual java/lang/Object.toString ()Ljava/lang/String;
      // 05e: astore 1
      // 05f: aload 1
      // 060: ifnull 075
      // 063: aload 1
      // 064: getstatic java/util/Locale.ROOT Ljava/util/Locale;
      // 067: invokevirtual java/lang/String.toLowerCase (Ljava/util/Locale;)Ljava/lang/String;
      // 06a: astore 1
      // 06b: aload 1
      // 06c: ldc_w "toLowerCase(...)"
      // 06f: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 072: goto 077
      // 075: aconst_null
      // 076: astore 1
      // 077: aload 2
      // 078: invokevirtual okhttp3/Response.code ()I
      // 07b: sipush 409
      // 07e: if_icmpne 08c
      // 081: aconst_null
      // 082: ldc_w "Turn the vehicle off before taking a snapshot."
      // 085: invokestatic kotlin/TuplesKt.to (Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;
      // 088: astore 1
      // 089: goto 148
      // 08c: aload 2
      // 08d: invokevirtual okhttp3/Response.isSuccessful ()Z
      // 090: istore 4
      // 092: iload 4
      // 094: ifne 115
      // 097: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 09a: astore 1
      // 09b: new org/json/JSONObject
      // 09e: astore 6
      // 0a0: aload 2
      // 0a1: invokevirtual okhttp3/Response.body ()Lokhttp3/ResponseBody;
      // 0a4: astore 1
      // 0a5: aload 1
      // 0a6: ifnull 0b4
      // 0a9: aload 1
      // 0aa: invokevirtual okhttp3/ResponseBody.string ()Ljava/lang/String;
      // 0ad: astore 2
      // 0ae: aload 2
      // 0af: astore 1
      // 0b0: aload 2
      // 0b1: ifnonnull 0b7
      // 0b4: ldc ""
      // 0b6: astore 1
      // 0b7: aload 6
      // 0b9: aload 1
      // 0ba: invokespecial org/json/JSONObject.<init> (Ljava/lang/String;)V
      // 0bd: aload 6
      // 0bf: ldc_w "error"
      // 0c2: invokevirtual org/json/JSONObject.optString (Ljava/lang/String;)Ljava/lang/String;
      // 0c5: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 0c8: astore 1
      // 0c9: goto 0d9
      // 0cc: astore 2
      // 0cd: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 0d0: astore 1
      // 0d1: aload 2
      // 0d2: invokestatic kotlin/ResultKt.createFailure (Ljava/lang/Throwable;)Ljava/lang/Object;
      // 0d5: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 0d8: astore 1
      // 0d9: aload 1
      // 0da: astore 2
      // 0db: aload 1
      // 0dc: invokestatic kotlin/Result.isFailure-impl (Ljava/lang/Object;)Z
      // 0df: ifeq 0e4
      // 0e2: aconst_null
      // 0e3: astore 2
      // 0e4: aload 2
      // 0e5: checkcast java/lang/String
      // 0e8: astore 1
      // 0e9: aload 1
      // 0ea: ifnull 108
      // 0ed: aload 1
      // 0ee: checkcast java/lang/CharSequence
      // 0f1: astore 2
      // 0f2: aload 2
      // 0f3: astore 1
      // 0f4: aload 2
      // 0f5: invokestatic kotlin/text/StringsKt.isBlank (Ljava/lang/CharSequence;)Z
      // 0f8: ifeq 0fd
      // 0fb: aconst_null
      // 0fc: astore 1
      // 0fd: aload 1
      // 0fe: checkcast java/lang/String
      // 101: astore 2
      // 102: aload 2
      // 103: astore 1
      // 104: aload 2
      // 105: ifnonnull 10c
      // 108: ldc_w "Camera frame unavailable. Try taking a new snapshot."
      // 10b: astore 1
      // 10c: aconst_null
      // 10d: aload 1
      // 10e: invokestatic kotlin/TuplesKt.to (Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;
      // 111: astore 1
      // 112: goto 148
      // 115: aload 1
      // 116: ldc_w "image/jpeg"
      // 119: invokestatic kotlin/jvm/internal/Intrinsics.areEqual (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 11c: ifne 12a
      // 11f: aconst_null
      // 120: ldc_w "Camera image is unavailable."
      // 123: invokestatic kotlin/TuplesKt.to (Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;
      // 126: astore 1
      // 127: goto 148
      // 12a: aload 2
      // 12b: invokevirtual okhttp3/Response.body ()Lokhttp3/ResponseBody;
      // 12e: astore 1
      // 12f: aload 1
      // 130: ifnull 13e
      // 133: aload 1
      // 134: invokevirtual okhttp3/ResponseBody.bytes ()[B
      // 137: astore 2
      // 138: aload 2
      // 139: astore 1
      // 13a: aload 2
      // 13b: ifnonnull 142
      // 13e: bipush 0
      // 13f: newarray 8
      // 141: astore 1
      // 142: aload 1
      // 143: aconst_null
      // 144: invokestatic kotlin/TuplesKt.to (Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;
      // 147: astore 1
      // 148: aload 5
      // 14a: aconst_null
      // 14b: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 14e: goto 1a8
      // 151: astore 1
      // 152: aload 1
      // 153: athrow
      // 154: astore 2
      // 155: aload 5
      // 157: aload 1
      // 158: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 15b: aload 2
      // 15c: athrow
      // 15d: astore 1
      // 15e: aload 1
      // 15f: invokevirtual java/lang/Object.getClass ()Ljava/lang/Class;
      // 162: invokevirtual java/lang/Class.getSimpleName ()Ljava/lang/String;
      // 165: astore 1
      // 166: aconst_null
      // 167: new java/lang/StringBuilder
      // 16a: dup
      // 16b: invokespecial java/lang/StringBuilder.<init> ()V
      // 16e: ldc "Can't reach Galaxy on the comma ("
      // 170: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 173: aload 1
      // 174: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 177: ldc ")"
      // 179: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 17c: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 17f: invokestatic kotlin/TuplesKt.to (Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;
      // 182: astore 1
      // 183: goto 1a8
      // 186: astore 1
      // 187: aconst_null
      // 188: ldc_w "Snapshot timed out. Try taking a new snapshot."
      // 18b: invokestatic kotlin/TuplesKt.to (Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;
      // 18e: astore 1
      // 18f: goto 1a8
      // 192: astore 1
      // 193: aload 1
      // 194: invokevirtual com/starpilot/starview/Galaxy7$Error.getMessage ()Ljava/lang/String;
      // 197: astore 2
      // 198: aload 2
      // 199: astore 1
      // 19a: aload 2
      // 19b: ifnonnull 1a2
      // 19e: ldc_w "Galaxy error"
      // 1a1: astore 1
      // 1a2: aconst_null
      // 1a3: aload 1
      // 1a4: invokestatic kotlin/TuplesKt.to (Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;
      // 1a7: astore 1
      // 1a8: aload 0
      // 1a9: getfield com/starpilot/starview/Galaxy7.main Landroid/os/Handler;
      // 1ac: new com/starpilot/starview/Galaxy7$$ExternalSyntheticLambda5
      // 1af: dup
      // 1b0: aload 3
      // 1b1: aload 1
      // 1b2: invokespecial com/starpilot/starview/Galaxy7$$ExternalSyntheticLambda5.<init> (Lkotlin/jvm/functions/Function2;Lkotlin/Pair;)V
      // 1b5: invokevirtual android/os/Handler.post (Ljava/lang/Runnable;)Z
      // 1b8: pop
      // 1b9: return
   }

   private static final void postImage$lambda$12$lambda$11(Function2 var0, Pair var1) {
      var0.invoke(var1.getFirst(), var1.getSecond());
   }

   private static final Response postImage$lambda$12$once(Galaxy7 var0, String var1, JSONObject var2) {
      var0.session(false);
      okhttp3.Request.Builder var4 = new okhttp3.Request.Builder();
      String var3 = var0.base;
      okhttp3.Request.Builder var9 = var4.url(var3 + var1).header("Cookie", var0.cookie).header("Origin", var0.base);
      RequestBody.Companion var5 = RequestBody.Companion;
      String var7 = var2.toString();
      Intrinsics.checkNotNullExpressionValue(var7, "toString(...)");
      byte[] var8 = var7.getBytes(Charsets.UTF_8);
      Intrinsics.checkNotNullExpressionValue(var8, "getBytes(...)");
      Request var6 = var9.post(RequestBody.Companion.create$default(var5, var8, var0.json, 0, 0, 6, null)).build();
      return var0.getSlow().newCall(var6).execute();
   }

   private final void session(boolean param1) {
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
      // 000: aload 0
      // 001: monitorenter
      // 002: aload 0
      // 003: getfield com/starpilot/starview/Galaxy7.cookie Ljava/lang/String;
      // 006: checkcast java/lang/CharSequence
      // 009: invokeinterface java/lang/CharSequence.length ()I 1
      // 00e: istore 2
      // 00f: iload 2
      // 010: ifle 018
      // 013: bipush 1
      // 014: istore 2
      // 015: goto 01a
      // 018: bipush 0
      // 019: istore 2
      // 01a: iload 2
      // 01b: ifeq 025
      // 01e: iload 1
      // 01f: ifne 025
      // 022: aload 0
      // 023: monitorexit
      // 024: return
      // 025: new okhttp3/Request$Builder
      // 028: astore 4
      // 02a: aload 4
      // 02c: invokespecial okhttp3/Request$Builder.<init> ()V
      // 02f: aload 0
      // 030: getfield com/starpilot/starview/Galaxy7.base Ljava/lang/String;
      // 033: astore 5
      // 035: new java/lang/StringBuilder
      // 038: astore 6
      // 03a: aload 6
      // 03c: invokespecial java/lang/StringBuilder.<init> ()V
      // 03f: aload 4
      // 041: aload 6
      // 043: aload 5
      // 045: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 048: ldc_w "/api/auth/session"
      // 04b: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 04e: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 051: invokevirtual okhttp3/Request$Builder.url (Ljava/lang/String;)Lokhttp3/Request$Builder;
      // 054: astore 4
      // 056: aload 0
      // 057: getfield com/starpilot/starview/Galaxy7.cookie Ljava/lang/String;
      // 05a: checkcast java/lang/CharSequence
      // 05d: invokeinterface java/lang/CharSequence.length ()I 1
      // 062: ifle 06a
      // 065: bipush 1
      // 066: istore 2
      // 067: goto 06c
      // 06a: bipush 0
      // 06b: istore 2
      // 06c: iload 2
      // 06d: ifeq 07d
      // 070: aload 4
      // 072: ldc_w "Cookie"
      // 075: aload 0
      // 076: getfield com/starpilot/starview/Galaxy7.cookie Ljava/lang/String;
      // 079: invokevirtual okhttp3/Request$Builder.header (Ljava/lang/String;Ljava/lang/String;)Lokhttp3/Request$Builder;
      // 07c: pop
      // 07d: aload 4
      // 07f: invokevirtual okhttp3/Request$Builder.build ()Lokhttp3/Request;
      // 082: astore 4
      // 084: aload 0
      // 085: getfield com/starpilot/starview/Galaxy7.client Lokhttp3/OkHttpClient;
      // 088: aload 4
      // 08a: invokevirtual okhttp3/OkHttpClient.newCall (Lokhttp3/Request;)Lokhttp3/Call;
      // 08d: invokeinterface okhttp3/Call.execute ()Lokhttp3/Response; 1
      // 092: checkcast java/io/Closeable
      // 095: astore 5
      // 097: aload 5
      // 099: checkcast okhttp3/Response
      // 09c: astore 6
      // 09e: aload 6
      // 0a0: ldc_w "Set-Cookie"
      // 0a3: invokevirtual okhttp3/Response.headers (Ljava/lang/String;)Ljava/util/List;
      // 0a6: checkcast java/lang/Iterable
      // 0a9: invokeinterface java/lang/Iterable.iterator ()Ljava/util/Iterator; 1
      // 0ae: astore 7
      // 0b0: aload 7
      // 0b2: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 0b7: ifeq 0d7
      // 0ba: aload 7
      // 0bc: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 0c1: astore 4
      // 0c3: aload 4
      // 0c5: checkcast java/lang/String
      // 0c8: ldc_w "galaxy_session="
      // 0cb: bipush 0
      // 0cc: bipush 2
      // 0cd: aconst_null
      // 0ce: invokestatic kotlin/text/StringsKt.startsWith$default (Ljava/lang/String;Ljava/lang/String;ZILjava/lang/Object;)Z
      // 0d1: ifeq 0b0
      // 0d4: goto 0da
      // 0d7: aconst_null
      // 0d8: astore 4
      // 0da: aload 4
      // 0dc: checkcast java/lang/String
      // 0df: astore 4
      // 0e1: aload 4
      // 0e3: ifnull 0f5
      // 0e6: aload 0
      // 0e7: aload 4
      // 0e9: ldc_w ";"
      // 0ec: aconst_null
      // 0ed: bipush 2
      // 0ee: aconst_null
      // 0ef: invokestatic kotlin/text/StringsKt.substringBefore$default (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/Object;)Ljava/lang/String;
      // 0f2: putfield com/starpilot/starview/Galaxy7.cookie Ljava/lang/String;
      // 0f5: aload 6
      // 0f7: invokevirtual okhttp3/Response.isSuccessful ()Z
      // 0fa: ifeq 13d
      // 0fd: aload 0
      // 0fe: getfield com/starpilot/starview/Galaxy7.cookie Ljava/lang/String;
      // 101: checkcast java/lang/CharSequence
      // 104: invokeinterface java/lang/CharSequence.length ()I 1
      // 109: ifne 111
      // 10c: bipush 1
      // 10d: istore 2
      // 10e: goto 113
      // 111: bipush 0
      // 112: istore 2
      // 113: iload 2
      // 114: ifne 12a
      // 117: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 11a: astore 4
      // 11c: aload 5
      // 11e: aconst_null
      // 11f: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 122: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 125: astore 4
      // 127: aload 0
      // 128: monitorexit
      // 129: return
      // 12a: new com/starpilot/starview/Galaxy7$Error
      // 12d: astore 4
      // 12f: aload 4
      // 131: sipush 401
      // 134: ldc_w "Galaxy gave no local session (is the tablet on the comma's own network?)"
      // 137: invokespecial com/starpilot/starview/Galaxy7$Error.<init> (ILjava/lang/String;)V
      // 13a: aload 4
      // 13c: athrow
      // 13d: new com/starpilot/starview/Galaxy7$Error
      // 140: astore 4
      // 142: aload 6
      // 144: invokevirtual okhttp3/Response.code ()I
      // 147: istore 2
      // 148: aload 6
      // 14a: invokevirtual okhttp3/Response.code ()I
      // 14d: istore 3
      // 14e: new java/lang/StringBuilder
      // 151: astore 6
      // 153: aload 6
      // 155: invokespecial java/lang/StringBuilder.<init> ()V
      // 158: aload 4
      // 15a: iload 2
      // 15b: aload 6
      // 15d: ldc_w "Galaxy sign-in failed (HTTP "
      // 160: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 163: iload 3
      // 164: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
      // 167: ldc ")"
      // 169: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 16c: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 16f: invokespecial com/starpilot/starview/Galaxy7$Error.<init> (ILjava/lang/String;)V
      // 172: aload 4
      // 174: athrow
      // 175: astore 4
      // 177: aload 4
      // 179: athrow
      // 17a: astore 6
      // 17c: aload 5
      // 17e: aload 4
      // 180: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 183: aload 6
      // 185: athrow
      // 186: astore 4
      // 188: aload 0
      // 189: monitorexit
      // 18a: aload 4
      // 18c: athrow
   }

   private static final OkHttpClient slow_delegate$lambda$7(Galaxy7 var0) {
      return var0.client.newBuilder().readTimeout(30L, TimeUnit.SECONDS).build();
   }

   public final void get(String var1, Function2<? super JSONObject, ? super String, Unit> var2) {
      Intrinsics.checkNotNullParameter(var1, "path");
      Intrinsics.checkNotNullParameter(var2, "done");
      this.async(new Galaxy7$$ExternalSyntheticLambda2(this, var1), var2);
   }

   public final JSONObject getBlocking(String var1) {
      Intrinsics.checkNotNullParameter(var1, "path");
      return call$default(this, "GET", var1, null, false, 8, null);
   }

   public final String getHost() {
      return this.host;
   }

   public final void post(String var1, JSONObject var2, Function2<? super JSONObject, ? super String, Unit> var3) {
      Intrinsics.checkNotNullParameter(var1, "path");
      Intrinsics.checkNotNullParameter(var2, "body");
      Intrinsics.checkNotNullParameter(var3, "done");
      this.async(new Galaxy7$$ExternalSyntheticLambda6(this, var1, var2), var3);
   }

   public final void postImage(String var1, JSONObject var2, Function2<byte[], ? super String, Unit> var3) {
      Intrinsics.checkNotNullParameter(var1, "path");
      Intrinsics.checkNotNullParameter(var2, "body");
      Intrinsics.checkNotNullParameter(var3, "done");
      this.io.execute(new Galaxy7$$ExternalSyntheticLambda3(this, var1, var2, var3));
   }

   public static final class Companion {
      private Companion() {
      }

      // $VF: synthetic method
      public Companion(DefaultConstructorMarker var1) {
         this();
      }

      public final Galaxy7 current() {
         String var1 = Link.INSTANCE.getHost();
         Galaxy7 var2;
         if (var1 != null) {
            var2 = Galaxy7.Companion.get(var1);
         } else {
            var2 = null;
         }

         return var2;
      }

      public final Galaxy7 get(String var1) {
         Intrinsics.checkNotNullParameter(var1, "host");
         Galaxy7 var2 = Galaxy7.inst;
         if (var2 != null && Intrinsics.areEqual(var2.getHost(), var1)) {
            return var2;
         } else {
            Galaxy7 var3 = new Galaxy7(var1, null);
            Galaxy7.Companion var4 = Galaxy7.Companion;
            Galaxy7.inst = var3;
            return var3;
         }
      }
   }

   public static final class Error extends Exception {
      private final int code;

      public Error(int var1, String var2) {
         Intrinsics.checkNotNullParameter(var2, "message");
         super(var2);
         this.code = var1;
      }

      public final int getCode() {
         return this.code;
      }
   }
}
