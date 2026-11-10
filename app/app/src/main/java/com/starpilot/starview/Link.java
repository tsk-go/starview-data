package com.starpilot.starview;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.collections.ArraysKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Interceptor.Chain;
import okhttp3.OkHttpClient.Builder;

public final class Link {
   public static final int DAEMON_PORT = 8090;
   public static final int GALAXY_PORT = 8082;
   public static final Link INSTANCE = new Link();
   public static final String SESSION_HEADER = "X-StarView-Session";
   private static final Interceptor authInterceptor = new Link$$ExternalSyntheticLambda2();
   private static volatile DataClient data;
   private static volatile String host;
   private static final Lazy http$delegate = LazyKt.lazy(new Link$$ExternalSyntheticLambda1());
   private static volatile String pairedDongle = "";
   private static volatile boolean pairingRequired;
   private static final SecureRandom rng = new SecureRandom();
   private static volatile String session = "";
   private static volatile String sessionHost;
   private static volatile long sessionUntilMs;
   private static volatile Telemetry telemetry;
   private static volatile String termKey = "";

   // $VF: synthetic method
   public static CharSequence $r8$lambda$9TmVhLuO6nehd4qsRz1njMycsB4(byte var0) {
      return nonce$lambda$2(var0);
   }

   // $VF: synthetic method
   public static Response $r8$lambda$L0lVIS1qBhl3oupkdDghnNtUIwY(Chain var0) {
      return authInterceptor$lambda$8(var0);
   }

   // $VF: synthetic method
   public static OkHttpClient $r8$lambda$R9c1SHz_LubPBw6hUIjjkVCiE9A/* $VF was: $r8$lambda$R9c1SHz_LubPBw6hUIjjkVCiE9A*/() {
      return http_delegate$lambda$4();
   }

   // $VF: synthetic method
   public static CharSequence $r8$lambda$ig9yv5LA1uTyiyi53ZrcLTOqNmQ(byte var0) {
      return mac$lambda$3(var0);
   }

   private Link() {
   }

   private static final Response authInterceptor$lambda$8(Chain var0) {
      Intrinsics.checkNotNullParameter(var0, "ch");
      Request var6 = var0.request();
      String var7 = var6.url().host();
      int var3 = var6.url().port();
      boolean var4 = INSTANCE.getPaired();
      boolean var2 = true;
      if (var4) {
         boolean var1;
         if (INSTANCE.sessionFor(var7, var3).length() == 0) {
            var1 = true;
         } else {
            var1 = false;
         }

         if (var1) {
            INSTANCE.ensureSession(var7, var3);
         }
      }

      Response var5 = var0.proceed(INSTANCE.withSession(var6.newBuilder(), var7, var3).build());
      if (var5.code() == 401 && INSTANCE.getPaired()) {
         RequestBody var8 = var6.body();
         boolean var9;
         if (var8 != null && var8.isOneShot()) {
            var9 = var2;
         } else {
            var9 = false;
         }

         if (!var9) {
            INSTANCE.forgetSession();
            if (INSTANCE.ensureSession(var7, var3) != Link.Auth.OK) {
               return var5;
            }

            var5.close();
            return var0.proceed(INSTANCE.withSession(var6.newBuilder(), var7, var3).build());
         }
      }

      return var5;
   }

   // $VF: synthetic method
   public static Link.Auth ensureSession$default(Link var0, String var1, int var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = 8090;
      }

      return var0.ensureSession(var1, var2);
   }

   private final OkHttpClient getHttp() {
      return (OkHttpClient)http$delegate.getValue();
   }

   private static final OkHttpClient http_delegate$lambda$4() {
      return new Builder().connectTimeout(3L, TimeUnit.SECONDS).readTimeout(3L, TimeUnit.SECONDS).build();
   }

   private final String mac(String... var1) {
      Mac var2 = Mac.getInstance("HmacSHA256");
      byte[] var3 = termKey.getBytes(Charsets.UTF_8);
      Intrinsics.checkNotNullExpressionValue(var3, "getBytes(...)");
      var2.init(new SecretKeySpec(var3, "HmacSHA256"));
      byte[] var4 = ArraysKt.joinToString$default(var1, "|", null, null, 0, null, null, 62, null).getBytes(Charsets.UTF_8);
      Intrinsics.checkNotNullExpressionValue(var4, "getBytes(...)");
      byte[] var5 = var2.doFinal(var4);
      Intrinsics.checkNotNullExpressionValue(var5, "doFinal(...)");
      return ArraysKt.joinToString$default(var5, "", null, null, 0, null, new Link$$ExternalSyntheticLambda3(), 30, null);
   }

   private static final CharSequence mac$lambda$3(byte var0) {
      String var1 = String.format("%02x", Arrays.copyOf(new Object[]{var0}, 1));
      Intrinsics.checkNotNullExpressionValue(var1, "format(...)");
      return var1;
   }

   private static final CharSequence nonce$lambda$2(byte var0) {
      String var1 = String.format("%02x", Arrays.copyOf(new Object[]{var0}, 1));
      Intrinsics.checkNotNullExpressionValue(var1, "format(...)");
      return var1;
   }

   private final boolean same(String var1, String var2) {
      byte[] var3 = var1.getBytes(Charsets.UTF_8);
      Intrinsics.checkNotNullExpressionValue(var3, "getBytes(...)");
      byte[] var4 = var2.getBytes(Charsets.UTF_8);
      Intrinsics.checkNotNullExpressionValue(var4, "getBytes(...)");
      return MessageDigest.isEqual(var3, var4);
   }

   // $VF: synthetic method
   public static String sessionFor$default(Link var0, String var1, int var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = 8090;
      }

      return var0.sessionFor(var1, var2);
   }

   // $VF: synthetic method
   public static okhttp3.Request.Builder withSession$default(Link var0, okhttp3.Request.Builder var1, String var2, int var3, int var4, Object var5) {
      if ((var4 & 4) != 0) {
         var3 = 8090;
      }

      return var0.withSession(var1, var2, var3);
   }

   public final boolean commaProofOk(String var1, String var2, String var3, String var4) {
      Intrinsics.checkNotNullParameter(var1, "dongleId");
      Intrinsics.checkNotNullParameter(var2, "cn");
      Intrinsics.checkNotNullParameter(var3, "sn");
      Intrinsics.checkNotNullParameter(var4, "proof");
      if (this.getPaired()) {
         boolean var5;
         if (var4.length() == 0) {
            var5 = true;
         } else {
            var5 = false;
         }

         if (!var5) {
            if (pairedDongle.length() > 0) {
               var5 = true;
            } else {
               var5 = false;
            }

            if (var5 && !Intrinsics.areEqual(var1, pairedDongle)) {
               return false;
            }

            return this.same(var4, this.mac("srv", var1, var2, var3));
         }
      }

      return false;
   }

   public final Link.Auth ensureSession(String param1, int param2) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.NullPointerException: Cannot invoke "org.jetbrains.java.decompiler.code.cfg.ExceptionRangeCFG.isCircular()" because "range" is null
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.graphToStatement(DomHelper.java:84)
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:203)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 000: aload 0
      // 001: monitorenter
      // 002: aload 1
      // 003: ldc_w "h"
      // 006: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
      // 009: aload 0
      // 00a: invokevirtual com/starpilot/starview/Link.getPaired ()Z
      // 00d: ifne 018
      // 010: getstatic com/starpilot/starview/Link$Auth.UNPAIRED Lcom/starpilot/starview/Link$Auth;
      // 013: astore 1
      // 014: aload 0
      // 015: monitorexit
      // 016: aload 1
      // 017: areturn
      // 018: aload 0
      // 019: aload 1
      // 01a: iload 2
      // 01b: invokevirtual com/starpilot/starview/Link.sessionFor (Ljava/lang/String;I)Ljava/lang/String;
      // 01e: checkcast java/lang/CharSequence
      // 021: invokeinterface java/lang/CharSequence.length ()I 1
      // 026: istore 3
      // 027: bipush 1
      // 028: istore 4
      // 02a: iload 3
      // 02b: ifle 033
      // 02e: bipush 1
      // 02f: istore 3
      // 030: goto 035
      // 033: bipush 0
      // 034: istore 3
      // 035: iload 3
      // 036: ifeq 041
      // 039: getstatic com/starpilot/starview/Link$Auth.OK Lcom/starpilot/starview/Link$Auth;
      // 03c: astore 1
      // 03d: aload 0
      // 03e: monitorexit
      // 03f: aload 1
      // 040: areturn
      // 041: aload 0
      // 042: invokevirtual com/starpilot/starview/Link.forgetSession ()V
      // 045: aload 0
      // 046: invokevirtual com/starpilot/starview/Link.nonce ()Ljava/lang/String;
      // 049: astore 5
      // 04b: aload 0
      // 04c: invokespecial com/starpilot/starview/Link.getHttp ()Lokhttp3/OkHttpClient;
      // 04f: astore 7
      // 051: new okhttp3/Request$Builder
      // 054: astore 6
      // 056: aload 6
      // 058: invokespecial okhttp3/Request$Builder.<init> ()V
      // 05b: new java/lang/StringBuilder
      // 05e: astore 8
      // 060: aload 8
      // 062: invokespecial java/lang/StringBuilder.<init> ()V
      // 065: aload 7
      // 067: aload 6
      // 069: aload 8
      // 06b: ldc_w "http://"
      // 06e: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 071: aload 1
      // 072: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 075: ldc_w ":"
      // 078: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 07b: iload 2
      // 07c: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
      // 07f: ldc_w "/hello?n="
      // 082: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 085: aload 5
      // 087: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 08a: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 08d: invokevirtual okhttp3/Request$Builder.url (Ljava/lang/String;)Lokhttp3/Request$Builder;
      // 090: invokevirtual okhttp3/Request$Builder.build ()Lokhttp3/Request;
      // 093: invokevirtual okhttp3/OkHttpClient.newCall (Lokhttp3/Request;)Lokhttp3/Call;
      // 096: invokeinterface okhttp3/Call.execute ()Lokhttp3/Response; 1
      // 09b: checkcast java/io/Closeable
      // 09e: astore 6
      // 0a0: aload 6
      // 0a2: checkcast okhttp3/Response
      // 0a5: astore 8
      // 0a7: aload 8
      // 0a9: invokevirtual okhttp3/Response.code ()I
      // 0ac: sipush 404
      // 0af: if_icmpne 0c0
      // 0b2: getstatic com/starpilot/starview/Link$Auth.OLD_BRIDGE Lcom/starpilot/starview/Link$Auth;
      // 0b5: astore 1
      // 0b6: aload 6
      // 0b8: aconst_null
      // 0b9: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 0bc: aload 0
      // 0bd: monitorexit
      // 0be: aload 1
      // 0bf: areturn
      // 0c0: aload 8
      // 0c2: invokevirtual okhttp3/Response.isSuccessful ()Z
      // 0c5: ifne 0d6
      // 0c8: getstatic com/starpilot/starview/Link$Auth.UNREACHABLE Lcom/starpilot/starview/Link$Auth;
      // 0cb: astore 1
      // 0cc: aload 6
      // 0ce: aconst_null
      // 0cf: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 0d2: aload 0
      // 0d3: monitorexit
      // 0d4: aload 1
      // 0d5: areturn
      // 0d6: new org/json/JSONObject
      // 0d9: astore 7
      // 0db: aload 8
      // 0dd: invokevirtual okhttp3/Response.body ()Lokhttp3/ResponseBody;
      // 0e0: astore 8
      // 0e2: aload 8
      // 0e4: ifnull 313
      // 0e7: aload 8
      // 0e9: invokevirtual okhttp3/ResponseBody.string ()Ljava/lang/String;
      // 0ec: astore 8
      // 0ee: aload 8
      // 0f0: ifnonnull 0f6
      // 0f3: goto 313
      // 0f6: aload 7
      // 0f8: aload 8
      // 0fa: invokespecial org/json/JSONObject.<init> (Ljava/lang/String;)V
      // 0fd: aload 6
      // 0ff: aconst_null
      // 100: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 103: aload 7
      // 105: ldc_w "dongleId"
      // 108: invokevirtual org/json/JSONObject.optString (Ljava/lang/String;)Ljava/lang/String;
      // 10b: astore 8
      // 10d: aload 7
      // 10f: ldc_w "sn"
      // 112: invokevirtual org/json/JSONObject.optString (Ljava/lang/String;)Ljava/lang/String;
      // 115: astore 6
      // 117: aload 8
      // 119: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNull (Ljava/lang/Object;)V
      // 11c: aload 6
      // 11e: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNull (Ljava/lang/Object;)V
      // 121: aload 7
      // 123: ldc_w "proof"
      // 126: invokevirtual org/json/JSONObject.optString (Ljava/lang/String;)Ljava/lang/String;
      // 129: astore 7
      // 12b: aload 7
      // 12d: ldc_w "optString(...)"
      // 130: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 133: aload 0
      // 134: aload 8
      // 136: aload 5
      // 138: aload 6
      // 13a: aload 7
      // 13c: invokevirtual com/starpilot/starview/Link.commaProofOk (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Z
      // 13f: ifne 14e
      // 142: bipush 1
      // 143: putstatic com/starpilot/starview/Link.pairingRequired Z
      // 146: getstatic com/starpilot/starview/Link$Auth.NOT_OUR_COMMA Lcom/starpilot/starview/Link$Auth;
      // 149: astore 1
      // 14a: aload 0
      // 14b: monitorexit
      // 14c: aload 1
      // 14d: areturn
      // 14e: new org/json/JSONObject
      // 151: astore 7
      // 153: aload 7
      // 155: invokespecial org/json/JSONObject.<init> ()V
      // 158: aload 7
      // 15a: ldc_w "sn"
      // 15d: aload 6
      // 15f: invokevirtual org/json/JSONObject.put (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
      // 162: ldc_w "cn"
      // 165: aload 5
      // 167: invokevirtual org/json/JSONObject.put (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
      // 16a: ldc_w "proof"
      // 16d: aload 0
      // 16e: bipush 4
      // 16f: anewarray 281
      // 172: dup
      // 173: bipush 0
      // 174: ldc_w "cli"
      // 177: aastore
      // 178: dup
      // 179: bipush 1
      // 17a: aload 8
      // 17c: aastore
      // 17d: dup
      // 17e: bipush 2
      // 17f: aload 6
      // 181: aastore
      // 182: dup
      // 183: bipush 3
      // 184: aload 5
      // 186: aastore
      // 187: invokespecial com/starpilot/starview/Link.mac ([Ljava/lang/String;)Ljava/lang/String;
      // 18a: invokevirtual org/json/JSONObject.put (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
      // 18d: astore 6
      // 18f: aload 0
      // 190: invokespecial com/starpilot/starview/Link.getHttp ()Lokhttp3/OkHttpClient;
      // 193: astore 5
      // 195: new okhttp3/Request$Builder
      // 198: astore 7
      // 19a: aload 7
      // 19c: invokespecial okhttp3/Request$Builder.<init> ()V
      // 19f: new java/lang/StringBuilder
      // 1a2: astore 8
      // 1a4: aload 8
      // 1a6: invokespecial java/lang/StringBuilder.<init> ()V
      // 1a9: aload 7
      // 1ab: aload 8
      // 1ad: ldc_w "http://"
      // 1b0: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 1b3: aload 1
      // 1b4: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 1b7: ldc_w ":"
      // 1ba: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 1bd: iload 2
      // 1be: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
      // 1c1: ldc_w "/auth"
      // 1c4: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 1c7: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 1ca: invokevirtual okhttp3/Request$Builder.url (Ljava/lang/String;)Lokhttp3/Request$Builder;
      // 1cd: astore 8
      // 1cf: getstatic okhttp3/RequestBody.Companion Lokhttp3/RequestBody$Companion;
      // 1d2: astore 7
      // 1d4: aload 6
      // 1d6: invokevirtual org/json/JSONObject.toString ()Ljava/lang/String;
      // 1d9: astore 6
      // 1db: aload 6
      // 1dd: ldc_w "toString(...)"
      // 1e0: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 1e3: aload 5
      // 1e5: aload 8
      // 1e7: aload 7
      // 1e9: aload 6
      // 1eb: getstatic okhttp3/MediaType.Companion Lokhttp3/MediaType$Companion;
      // 1ee: ldc_w "application/json"
      // 1f1: invokevirtual okhttp3/MediaType$Companion.get (Ljava/lang/String;)Lokhttp3/MediaType;
      // 1f4: invokevirtual okhttp3/RequestBody$Companion.create (Ljava/lang/String;Lokhttp3/MediaType;)Lokhttp3/RequestBody;
      // 1f7: invokevirtual okhttp3/Request$Builder.post (Lokhttp3/RequestBody;)Lokhttp3/Request$Builder;
      // 1fa: invokevirtual okhttp3/Request$Builder.build ()Lokhttp3/Request;
      // 1fd: invokevirtual okhttp3/OkHttpClient.newCall (Lokhttp3/Request;)Lokhttp3/Call;
      // 200: invokeinterface okhttp3/Call.execute ()Lokhttp3/Response; 1
      // 205: checkcast java/io/Closeable
      // 208: astore 5
      // 20a: aload 5
      // 20c: checkcast okhttp3/Response
      // 20f: astore 7
      // 211: aload 7
      // 213: invokevirtual okhttp3/Response.code ()I
      // 216: sipush 401
      // 219: if_icmpne 22e
      // 21c: bipush 1
      // 21d: putstatic com/starpilot/starview/Link.pairingRequired Z
      // 220: getstatic com/starpilot/starview/Link$Auth.NOT_OUR_COMMA Lcom/starpilot/starview/Link$Auth;
      // 223: astore 1
      // 224: aload 5
      // 226: aconst_null
      // 227: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 22a: aload 0
      // 22b: monitorexit
      // 22c: aload 1
      // 22d: areturn
      // 22e: aload 7
      // 230: invokevirtual okhttp3/Response.isSuccessful ()Z
      // 233: ifne 244
      // 236: getstatic com/starpilot/starview/Link$Auth.UNREACHABLE Lcom/starpilot/starview/Link$Auth;
      // 239: astore 1
      // 23a: aload 5
      // 23c: aconst_null
      // 23d: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 240: aload 0
      // 241: monitorexit
      // 242: aload 1
      // 243: areturn
      // 244: new org/json/JSONObject
      // 247: astore 6
      // 249: aload 7
      // 24b: invokevirtual okhttp3/Response.body ()Lokhttp3/ResponseBody;
      // 24e: astore 7
      // 250: aload 7
      // 252: ifnull 2f6
      // 255: aload 7
      // 257: invokevirtual okhttp3/ResponseBody.string ()Ljava/lang/String;
      // 25a: astore 7
      // 25c: aload 7
      // 25e: ifnonnull 264
      // 261: goto 2f6
      // 264: aload 6
      // 266: aload 7
      // 268: invokespecial org/json/JSONObject.<init> (Ljava/lang/String;)V
      // 26b: aload 6
      // 26d: ldc_w "session"
      // 270: invokevirtual org/json/JSONObject.optString (Ljava/lang/String;)Ljava/lang/String;
      // 273: astore 7
      // 275: aload 7
      // 277: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNull (Ljava/lang/Object;)V
      // 27a: aload 7
      // 27c: checkcast java/lang/CharSequence
      // 27f: invokeinterface java/lang/CharSequence.length ()I 1
      // 284: ifne 28d
      // 287: iload 4
      // 289: istore 3
      // 28a: goto 28f
      // 28d: bipush 0
      // 28e: istore 3
      // 28f: iload 3
      // 290: ifeq 2a1
      // 293: getstatic com/starpilot/starview/Link$Auth.UNREACHABLE Lcom/starpilot/starview/Link$Auth;
      // 296: astore 1
      // 297: aload 5
      // 299: aconst_null
      // 29a: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 29d: aload 0
      // 29e: monitorexit
      // 29f: aload 1
      // 2a0: areturn
      // 2a1: aload 7
      // 2a3: putstatic com/starpilot/starview/Link.session Ljava/lang/String;
      // 2a6: new java/lang/StringBuilder
      // 2a9: astore 7
      // 2ab: aload 7
      // 2ad: invokespecial java/lang/StringBuilder.<init> ()V
      // 2b0: aload 7
      // 2b2: aload 1
      // 2b3: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 2b6: ldc_w ":"
      // 2b9: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 2bc: iload 2
      // 2bd: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
      // 2c0: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 2c3: putstatic com/starpilot/starview/Link.sessionHost Ljava/lang/String;
      // 2c6: invokestatic java/lang/System.currentTimeMillis ()J
      // 2c9: aload 6
      // 2cb: ldc_w "expires_s"
      // 2ce: ldc2_w 3600
      // 2d1: invokevirtual org/json/JSONObject.optLong (Ljava/lang/String;J)J
      // 2d4: ldc2_w 1000
      // 2d7: lmul
      // 2d8: bipush 9
      // 2da: i2l
      // 2db: lmul
      // 2dc: bipush 10
      // 2de: i2l
      // 2df: ldiv
      // 2e0: ladd
      // 2e1: putstatic com/starpilot/starview/Link.sessionUntilMs J
      // 2e4: bipush 0
      // 2e5: putstatic com/starpilot/starview/Link.pairingRequired Z
      // 2e8: getstatic com/starpilot/starview/Link$Auth.OK Lcom/starpilot/starview/Link$Auth;
      // 2eb: astore 1
      // 2ec: aload 5
      // 2ee: aconst_null
      // 2ef: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 2f2: aload 0
      // 2f3: monitorexit
      // 2f4: aload 1
      // 2f5: areturn
      // 2f6: getstatic com/starpilot/starview/Link$Auth.UNREACHABLE Lcom/starpilot/starview/Link$Auth;
      // 2f9: astore 1
      // 2fa: aload 5
      // 2fc: aconst_null
      // 2fd: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 300: aload 0
      // 301: monitorexit
      // 302: aload 1
      // 303: areturn
      // 304: astore 6
      // 306: aload 6
      // 308: athrow
      // 309: astore 1
      // 30a: aload 5
      // 30c: aload 6
      // 30e: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 311: aload 1
      // 312: athrow
      // 313: getstatic com/starpilot/starview/Link$Auth.UNREACHABLE Lcom/starpilot/starview/Link$Auth;
      // 316: astore 1
      // 317: aload 6
      // 319: aconst_null
      // 31a: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 31d: aload 0
      // 31e: monitorexit
      // 31f: aload 1
      // 320: areturn
      // 321: astore 5
      // 323: aload 5
      // 325: athrow
      // 326: astore 1
      // 327: aload 6
      // 329: aload 5
      // 32b: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 32e: aload 1
      // 32f: athrow
      // 330: astore 1
      // 331: getstatic com/starpilot/starview/Link$Auth.UNREACHABLE Lcom/starpilot/starview/Link$Auth;
      // 334: astore 1
      // 335: aload 0
      // 336: monitorexit
      // 337: aload 1
      // 338: areturn
      // 339: astore 1
      // 33a: aload 0
      // 33b: monitorexit
      // 33c: aload 1
      // 33d: athrow
   }

   public final void forgetSession() {
      session = "";
      sessionHost = null;
      sessionUntilMs = 0L;
   }

   public final Interceptor getAuthInterceptor() {
      return authInterceptor;
   }

   public final DataClient getData() {
      return data;
   }

   public final String getHost() {
      return host;
   }

   public final boolean getPaired() {
      boolean var1;
      if (termKey.length() > 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public final String getPairedDongle() {
      return pairedDongle;
   }

   public final boolean getPairingRequired() {
      return pairingRequired;
   }

   public final Telemetry getTelemetry() {
      return telemetry;
   }

   public final String getTermKey() {
      return termKey;
   }

   public final void loadKey(Context var1) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      boolean var2;
      if (termKey.length() > 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      if (!var2) {
         SharedPreferences var5 = var1.getSharedPreferences("starview", 0);
         String var3 = "";
         String var4 = var5.getString("term_key", "");
         String var6 = var4;
         if (var4 == null) {
            var6 = "";
         }

         termKey = var6;
         String var7 = var5.getString("paired_dongle", "");
         if (var7 == null) {
            var7 = var3;
         }

         pairedDongle = var7;
      }
   }

   public final String nonce() {
      byte[] var1 = new byte[16];
      rng.nextBytes(var1);
      return ArraysKt.joinToString$default(var1, "", null, null, 0, null, new Link$$ExternalSyntheticLambda0(), 30, null);
   }

   public final boolean pairFromUri(Context var1, String var2) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      Intrinsics.checkNotNullParameter(var2, "text");

      Uri var6;
      try {
         var6 = Uri.parse(StringsKt.trim(var2).toString());
      } catch (Exception var8) {
         return false;
      }

      if (Intrinsics.areEqual(var6.getScheme(), "starview") && Intrinsics.areEqual(var6.getHost(), "pair")) {
         String var7 = var6.getQueryParameter("k");
         if (var7 == null) {
            return false;
         } else {
            if (var7.length() >= 16) {
               CharSequence var10 = var7;
               int var3 = 0;

               while (true) {
                  if (var3 >= var10.length()) {
                     var13 = true;
                     break;
                  }

                  if (!Character.isLetterOrDigit(var10.charAt(var3))) {
                     var13 = false;
                     break;
                  }

                  var3++;
               }

               if (var13) {
                  String var5 = var6.getQueryParameter("d");
                  String var4 = "";
                  var2 = var5;
                  if (var5 == null) {
                     var2 = "";
                  }

                  termKey = var7;
                  pairedDongle = var2;
                  this.forgetSession();
                  pairingRequired = false;
                  Editor var12 = var1.getSharedPreferences("starview", 0).edit().putString("term_key", var7).putString("paired_dongle", var2);
                  String var9 = var6.getQueryParameter("h");
                  if (var9 == null) {
                     var9 = var4;
                  }

                  var12.putString("paired_hosts", var9).apply();
                  return true;
               }
            }

            return false;
         }
      } else {
         return false;
      }
   }

   public final String sessionFor(String var1, int var2) {
      if (var1 != null && Intrinsics.areEqual(var1 + ":" + var2, sessionHost) && System.currentTimeMillis() < sessionUntilMs) {
         var1 = session;
      } else {
         var1 = "";
      }

      return var1;
   }

   public final void setData(DataClient var1) {
      data = var1;
   }

   public final void setHost(String var1) {
      host = var1;
   }

   public final void setPairingRequired(boolean var1) {
      pairingRequired = var1;
   }

   public final void setTelemetry(Telemetry var1) {
      telemetry = var1;
   }

   public final okhttp3.Request.Builder withSession(okhttp3.Request.Builder var1, String var2, int var3) {
      Intrinsics.checkNotNullParameter(var1, "b");
      var2 = this.sessionFor(var2, var3);
      boolean var5;
      if (var2.length() > 0) {
         var5 = true;
      } else {
         var5 = false;
      }

      if (var5) {
         var1 = var1.header("X-StarView-Session", var2);
      }

      return var1;
   }

   public static enum Auth {
      NOT_OUR_COMMA,
      OK,
      OLD_BRIDGE,
      UNPAIRED,
      UNREACHABLE;
      private static final EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(Link.Auth.$VALUES);
      private static final Link.Auth[] $VALUES = $values();

      // $VF: synthetic method
      private static final Link.Auth[] $values() {
         return new Link.Auth[]{OK, UNPAIRED, NOT_OUR_COMMA, OLD_BRIDGE, UNREACHABLE};
      }

      public static EnumEntries<Link.Auth> getEntries() {
         return $ENTRIES;
      }
   }
}
