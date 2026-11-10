package com.starpilot.starview;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.ClipData.Item;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore.Downloads;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.inputmethod.InputMethodManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import java.io.File;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.Result.Companion;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.comparisons.ComparisonsKt;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import org.json.JSONArray;
import org.json.JSONObject;

public final class TermWeb {
   public static final TermWeb INSTANCE = new TermWeb();
   private static String loadedHost;
   private static WebView web;

   private TermWeb() {
   }

   public final void detach() {
      WebView var1 = web;
      ViewGroup var2 = null;
      ViewParent var3;
      if (var1 != null) {
         var3 = var1.getParent();
      } else {
         var3 = null;
      }

      if (var3 instanceof ViewGroup) {
         var2 = (ViewGroup)var3;
      }

      if (var2 != null) {
         var2.removeView((View)web);
      }
   }

   public final WebView get(Context var1, String var2) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      Intrinsics.checkNotNullParameter(var2, "host");
      WebView var4 = web;
      WebView var3 = var4;
      if (var4 == null) {
         var3 = new WebView(var1.getApplicationContext());
         var3.getSettings().setJavaScriptEnabled(true);
         var3.getSettings().setDomStorageEnabled(true);
         var3.getSettings().setAllowFileAccess(true);
         var3.getSettings().setAllowContentAccess(true);
         var3.getSettings().setSupportZoom(false);
         var3.getSettings().setMediaPlaybackRequiresUserGesture(false);
         var3.setFocusable(true);
         var3.setFocusableInTouchMode(true);
         var3.setWebChromeClient(new WebChromeClient());
         var3.setBackgroundColor(Color.rgb(11, 13, 18));
         var1 = var1.getApplicationContext();
         Intrinsics.checkNotNullExpressionValue(var1, "getApplicationContext(...)");
         var3.addJavascriptInterface(new TermWeb.Bridge(var1, var3), "StarView");
         web = var3;
      }

      if (!Intrinsics.areEqual(loadedHost, var2)) {
         loadedHost = var2;
         String var6 = URLEncoder.encode(var2, "UTF-8");
         var3.loadUrl("file:///android_asset/term.html#" + var6);
      }

      return var3;
   }

   public final WebView getWeb() {
      return web;
   }

   public static final class Bridge {
      private final Context ctx;
      private final Lazy http$delegate;
      private final WebView web;

      // $VF: synthetic method
      public static void $r8$lambda$DQwQ2C6nkj7NvjypKbusj7CuFc0(TermWeb.Bridge var0, String var1) {
         listRecent$lambda$12(var0, var1);
      }

      // $VF: synthetic method
      public static void $r8$lambda$aeCcD4zOWmVOswWCd_m1EV7OdKM(TermWeb.Bridge var0, String var1) {
         js$lambda$1(var0, var1);
      }

      // $VF: synthetic method
      public static OkHttpClient $r8$lambda$jv7nNp2qEo_y_B7p0Isfo49lh8Q/* $VF was: $r8$lambda$jv7nNp2qEo_y_B7p0Isfo49lh8Q*/() {
         return http_delegate$lambda$0();
      }

      // $VF: synthetic method
      public static void $r8$lambda$kI4a0I2NIKXi_unMTn7_pYz2Log(String var0, TermWeb.Bridge var1) {
         saveToDownloads$lambda$17(var0, var1);
      }

      // $VF: synthetic method
      public static void $r8$lambda$usr9nZhZyq_Vgt4e1HC_Y_YrS3A(TermWeb.Bridge var0, String var1) {
         checkFile$lambda$6(var0, var1);
      }

      // $VF: synthetic method
      public static void $r8$lambda$zOAPBftLIzer0kCCJXxMPptxzjQ(TermWeb.Bridge var0) {
         showKeyboard$lambda$21(var0);
      }

      public Bridge(Context var1, WebView var2) {
         Intrinsics.checkNotNullParameter(var1, "ctx");
         Intrinsics.checkNotNullParameter(var2, "web");
         super();
         this.ctx = var1;
         this.web = var2;
         this.http$delegate = LazyKt.lazy(new TermWeb$Bridge$$ExternalSyntheticLambda5());
      }

      // $VF: Duplicated exception handlers to handle obfuscated exceptions
      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      private static final void checkFile$lambda$6(TermWeb.Bridge var0, String var1) {
         boolean var2;
         String var46;
         label258: {
            label257: {
               try {
                  Companion var6 = Result.Companion;
                  var46 = StringsKt.substringBeforeLast$default(var1, '/', null, 2, null);
                  if (var46.length() == 0) {
                     break label257;
                  }
               } catch (Throwable var38) {
                  Companion var39 = Result.Companion;
                  KtCompat.resultOf(ResultKt.createFailure(var38));
                  return;
               }

               var2 = 0;
               break label258;
            }

            var2 = 1;
         }

         if (var2) {
            var46 = "/";
         }

         JSONArray var8;
         try {
            String var7 = var46;
            var46 = StringsKt.substringAfterLast$default(var1, '/', null, 2, null);
            var8 = var0.list(var7);
         } catch (Throwable var37) {
            Companion var40 = Result.Companion;
            KtCompat.resultOf(ResultKt.createFailure(var37));
            return;
         }

         var2 = 0;

         int var3;
         try {
            var3 = var8.length();
         } catch (Throwable var35) {
            Companion var41 = Result.Companion;
            KtCompat.resultOf(ResultKt.createFailure(var35));
            return;
         }

         for (; var2 < var3; var2++) {
            try {
               JSONObject var49 = var8.getJSONObject(var2);
               if (Intrinsics.areEqual(var49.optString("name"), var46) && !var49.optBoolean("dir")) {
                  var1 = JSONObject.quote(var1);
                  long var4 = var49.optLong("size");
                  StringBuilder var48 = new StringBuilder();
                  var0.js(var48.append("window.fileSeen(").append(var1).append(", ").append(var4).append(")").toString());
                  break;
               }
            } catch (Throwable var36) {
               Companion var42 = Result.Companion;
               KtCompat.resultOf(ResultKt.createFailure(var36));
               return;
            }
         }

         try {
            KtCompat.resultOf(Unit.INSTANCE);
         } catch (Throwable var34) {
            Companion var43 = Result.Companion;
            KtCompat.resultOf(ResultKt.createFailure(var34));
            return;
         }
      }

      private final String freeName(File var1, String var2) {
         if (!new File(var1, var2).exists()) {
            return var2;
         } else {
            Integer var5 = StringsKt.lastIndexOf$default(var2, '.', 0, false, 6, null);
            boolean var3;
            if (var5.intValue() > 0) {
               var3 = 1;
            } else {
               var3 = 0;
            }

            if (!var3) {
               var5 = null;
            }

            if (var5 != null) {
               var3 = var5;
            } else {
               var3 = var2.length();
            }

            int var4 = 1;

            while (true) {
               String var6 = var2.substring(0, var3);
               Intrinsics.checkNotNullExpressionValue(var6, "substring(...)");
               String var10 = var2.substring(var3);
               Intrinsics.checkNotNullExpressionValue(var10, "substring(...)");
               if (!new File(var1, var6 + " (" + var4 + ")" + var10).exists()) {
                  String var7 = var2.substring(0, var3);
                  Intrinsics.checkNotNullExpressionValue(var7, "substring(...)");
                  var2 = var2.substring(var3);
                  Intrinsics.checkNotNullExpressionValue(var2, "substring(...)");
                  return var7 + " (" + var4 + ")" + var2;
               }

               var4++;
            }
         }
      }

      private final OkHttpClient getHttp() {
         return (OkHttpClient)this.http$delegate.getValue();
      }

      private static final OkHttpClient http_delegate$lambda$0() {
         return new Builder()
            .addInterceptor(Link.INSTANCE.getAuthInterceptor())
            .connectTimeout(5L, TimeUnit.SECONDS)
            .readTimeout(0L, TimeUnit.MILLISECONDS)
            .build();
      }

      private final boolean js(String var1) {
         return this.web.post(new TermWeb$Bridge$$ExternalSyntheticLambda3(this, var1));
      }

      private static final void js$lambda$1(TermWeb.Bridge var0, String var1) {
         var0.web.evaluateJavascript(var1, null);
      }

      private final JSONArray list(String param1) {
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
         // 00: getstatic com/starpilot/starview/Link.INSTANCE Lcom/starpilot/starview/Link;
         // 03: invokevirtual com/starpilot/starview/Link.getHost ()Ljava/lang/String;
         // 06: astore 4
         // 08: aload 4
         // 0a: ifnull ef
         // 0d: aload 0
         // 0e: invokespecial com/starpilot/starview/TermWeb$Bridge.getHttp ()Lokhttp3/OkHttpClient;
         // 11: astore 5
         // 13: new okhttp3/Request$Builder
         // 16: dup
         // 17: invokespecial okhttp3/Request$Builder.<init> ()V
         // 1a: astore 3
         // 1b: aload 1
         // 1c: invokestatic android/net/Uri.encode (Ljava/lang/String;)Ljava/lang/String;
         // 1f: astore 1
         // 20: aload 5
         // 22: aload 3
         // 23: new java/lang/StringBuilder
         // 26: dup
         // 27: invokespecial java/lang/StringBuilder.<init> ()V
         // 2a: ldc_w "http://"
         // 2d: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
         // 30: aload 4
         // 32: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
         // 35: ldc_w ":8090/fs/list?path="
         // 38: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
         // 3b: aload 1
         // 3c: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
         // 3f: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
         // 42: invokevirtual okhttp3/Request$Builder.url (Ljava/lang/String;)Lokhttp3/Request$Builder;
         // 45: invokevirtual okhttp3/Request$Builder.build ()Lokhttp3/Request;
         // 48: invokevirtual okhttp3/OkHttpClient.newCall (Lokhttp3/Request;)Lokhttp3/Call;
         // 4b: invokeinterface okhttp3/Call.execute ()Lokhttp3/Response; 1
         // 50: checkcast java/io/Closeable
         // 53: astore 4
         // 55: aload 4
         // 57: checkcast okhttp3/Response
         // 5a: astore 5
         // 5c: aload 5
         // 5e: invokevirtual okhttp3/Response.body ()Lokhttp3/ResponseBody;
         // 61: astore 1
         // 62: aload 1
         // 63: ifnull 71
         // 66: aload 1
         // 67: invokevirtual okhttp3/ResponseBody.string ()Ljava/lang/String;
         // 6a: astore 3
         // 6b: aload 3
         // 6c: astore 1
         // 6d: aload 3
         // 6e: ifnonnull 75
         // 71: ldc_w ""
         // 74: astore 1
         // 75: aload 5
         // 77: invokevirtual okhttp3/Response.isSuccessful ()Z
         // 7a: ifne c3
         // 7d: aload 1
         // 7e: checkcast java/lang/CharSequence
         // 81: astore 1
         // 82: aload 1
         // 83: invokeinterface java/lang/CharSequence.length ()I 1
         // 88: ifne 90
         // 8b: bipush 1
         // 8c: istore 2
         // 8d: goto 92
         // 90: bipush 0
         // 91: istore 2
         // 92: iload 2
         // 93: ifeq b3
         // 96: aload 5
         // 98: invokevirtual okhttp3/Response.code ()I
         // 9b: istore 2
         // 9c: new java/lang/StringBuilder
         // 9f: astore 1
         // a0: aload 1
         // a1: invokespecial java/lang/StringBuilder.<init> ()V
         // a4: aload 1
         // a5: ldc_w "HTTP "
         // a8: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
         // ab: iload 2
         // ac: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
         // af: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
         // b2: astore 1
         // b3: aload 1
         // b4: checkcast java/lang/String
         // b7: astore 1
         // b8: new java/lang/RuntimeException
         // bb: astore 3
         // bc: aload 3
         // bd: aload 1
         // be: invokespecial java/lang/RuntimeException.<init> (Ljava/lang/String;)V
         // c1: aload 3
         // c2: athrow
         // c3: new org/json/JSONObject
         // c6: astore 3
         // c7: aload 3
         // c8: aload 1
         // c9: invokespecial org/json/JSONObject.<init> (Ljava/lang/String;)V
         // cc: aload 3
         // cd: ldc_w "entries"
         // d0: invokevirtual org/json/JSONObject.getJSONArray (Ljava/lang/String;)Lorg/json/JSONArray;
         // d3: astore 1
         // d4: aload 1
         // d5: ldc_w "getJSONArray(...)"
         // d8: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
         // db: aload 4
         // dd: aconst_null
         // de: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
         // e1: aload 1
         // e2: areturn
         // e3: astore 3
         // e4: aload 3
         // e5: athrow
         // e6: astore 1
         // e7: aload 4
         // e9: aload 3
         // ea: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
         // ed: aload 1
         // ee: athrow
         // ef: new java/lang/RuntimeException
         // f2: dup
         // f3: ldc_w "not connected"
         // f6: invokespecial java/lang/RuntimeException.<init> (Ljava/lang/String;)V
         // f9: athrow
      }

      // $VF: Duplicated exception handlers to handle obfuscated exceptions
      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      private static final void listRecent$lambda$12(TermWeb.Bridge var0, String var1) {
         JSONArray var2 = new JSONArray();

         label612: {
            JSONArray var130;
            ArrayList var138;
            Iterator var143;
            try {
               Companion var3 = Result.Companion;
               var130 = var0.list(var1);
               Iterable var5 = RangesKt.until(0, var130.length());
               var138 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var5, 10));
               var138 = var138;
               var143 = var5.iterator();
            } catch (Throwable var117) {
               Companion var119 = Result.Companion;
               KtCompat.resultOf(ResultKt.createFailure(var117));
               break label612;
            }

            while (true) {
               try {
                  if (!var143.hasNext()) {
                     break;
                  }

                  var138.add(var130.getJSONObject(((IntIterator)var143).nextInt()));
               } catch (Throwable var118) {
                  Companion var120 = Result.Companion;
                  KtCompat.resultOf(ResultKt.createFailure(var118));
                  break label612;
               }
            }

            try {
               var131 = var138;
            } catch (Throwable var116) {
               Companion var121 = Result.Companion;
               KtCompat.resultOf(ResultKt.createFailure(var116));
               break label612;
            }

            try {
               Iterable var139 = var131;
               ArrayList var132 = new ArrayList();
               var133 = var132;
               var140 = var139.iterator();
            } catch (Throwable var114) {
               Companion var122 = Result.Companion;
               KtCompat.resultOf(ResultKt.createFailure(var114));
               break label612;
            }

            while (true) {
               try {
                  if (!var140.hasNext()) {
                     break;
                  }

                  var143 = (Iterator)var140.next();
                  if (!((JSONObject)var143).optBoolean("dir")) {
                     var133.add(var143);
                  }
               } catch (Throwable var115) {
                  Companion var123 = Result.Companion;
                  KtCompat.resultOf(ResultKt.createFailure(var115));
                  break label612;
               }
            }

            try {
               var134 = var133;
            } catch (Throwable var113) {
               Companion var124 = Result.Companion;
               KtCompat.resultOf(ResultKt.createFailure(var113));
               break label612;
            }

            try {
               Iterable var135 = var134;
               Comparator var141 = new Comparator() {
                  @Override
                  public final int compare(T var1, T var2x) {
                     return ComparisonsKt.compareValues(((JSONObject)var2x).optLong("mtime"), ((JSONObject)var1).optLong("mtime"));
                  }
               };
               var136 = CollectionsKt.take(CollectionsKt.sortedWith(var135, var141), 10);
            } catch (Throwable var112) {
               Companion var125 = Result.Companion;
               KtCompat.resultOf(ResultKt.createFailure(var112));
               break label612;
            }

            Iterator var6;
            try {
               var6 = var136.iterator();
            } catch (Throwable var110) {
               Companion var126 = Result.Companion;
               KtCompat.resultOf(ResultKt.createFailure(var110));
               break label612;
            }

            while (true) {
               try {
                  if (!var6.hasNext()) {
                     break;
                  }

                  JSONObject var142 = (JSONObject)var6.next();
                  JSONObject var145 = new JSONObject();
                  String var7 = StringsKt.trimEnd(var1, new char[]{'/'});
                  String var8 = var142.optString("name");
                  StringBuilder var137 = new StringBuilder();
                  var2.put(var145.put("path", var137.append(var7).append("/").append(var8).toString()).put("size", var142.optLong("size")));
               } catch (Throwable var111) {
                  Companion var127 = Result.Companion;
                  KtCompat.resultOf(ResultKt.createFailure(var111));
                  break label612;
               }
            }

            label566:
            try {
               KtCompat.resultOf(Unit.INSTANCE);
            } catch (Throwable var109) {
               Companion var128 = Result.Companion;
               KtCompat.resultOf(ResultKt.createFailure(var109));
               break label566;
            }
         }

         var1 = JSONObject.quote(var2.toString());
         var0.js("window.recentFiles(" + var1 + ")");
      }

      private static final void saveToDownloads$lambda$17(String param0, TermWeb.Bridge param1) {
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
         // 001: bipush 47
         // 003: aconst_null
         // 004: bipush 2
         // 005: aconst_null
         // 006: invokestatic kotlin/text/StringsKt.substringAfterLast$default (Ljava/lang/String;CLjava/lang/String;ILjava/lang/Object;)Ljava/lang/String;
         // 009: astore 6
         // 00b: getstatic com/starpilot/starview/Link.INSTANCE Lcom/starpilot/starview/Link;
         // 00e: invokevirtual com/starpilot/starview/Link.getHost ()Ljava/lang/String;
         // 011: astore 9
         // 013: aload 9
         // 015: ifnull 1b1
         // 018: getstatic android/os/Environment.DIRECTORY_DOWNLOADS Ljava/lang/String;
         // 01b: invokestatic android/os/Environment.getExternalStoragePublicDirectory (Ljava/lang/String;)Ljava/io/File;
         // 01e: astore 4
         // 020: aload 1
         // 021: invokespecial com/starpilot/starview/TermWeb$Bridge.getHttp ()Lokhttp3/OkHttpClient;
         // 024: astore 8
         // 026: new okhttp3/Request$Builder
         // 029: astore 5
         // 02b: aload 5
         // 02d: invokespecial okhttp3/Request$Builder.<init> ()V
         // 030: aload 0
         // 031: invokestatic android/net/Uri.encode (Ljava/lang/String;)Ljava/lang/String;
         // 034: astore 7
         // 036: new java/lang/StringBuilder
         // 039: astore 10
         // 03b: aload 10
         // 03d: invokespecial java/lang/StringBuilder.<init> ()V
         // 040: aload 8
         // 042: aload 5
         // 044: aload 10
         // 046: ldc_w "http://"
         // 049: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
         // 04c: aload 9
         // 04e: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
         // 051: ldc_w ":8090/fs/get?path="
         // 054: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
         // 057: aload 7
         // 059: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
         // 05c: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
         // 05f: invokevirtual okhttp3/Request$Builder.url (Ljava/lang/String;)Lokhttp3/Request$Builder;
         // 062: invokevirtual okhttp3/Request$Builder.build ()Lokhttp3/Request;
         // 065: invokevirtual okhttp3/OkHttpClient.newCall (Lokhttp3/Request;)Lokhttp3/Call;
         // 068: invokeinterface okhttp3/Call.execute ()Lokhttp3/Response; 1
         // 06d: checkcast java/io/Closeable
         // 070: astore 7
         // 072: aload 7
         // 074: checkcast okhttp3/Response
         // 077: astore 8
         // 079: aload 8
         // 07b: invokevirtual okhttp3/Response.isSuccessful ()Z
         // 07e: istore 3
         // 07f: bipush 1
         // 080: istore 2
         // 081: iload 3
         // 082: ifne 118
         // 085: aload 8
         // 087: invokevirtual okhttp3/Response.body ()Lokhttp3/ResponseBody;
         // 08a: astore 4
         // 08c: aload 4
         // 08e: ifnull 0e8
         // 091: aload 4
         // 093: invokevirtual okhttp3/ResponseBody.string ()Ljava/lang/String;
         // 096: astore 4
         // 098: aload 4
         // 09a: ifnull 0e8
         // 09d: aload 4
         // 09f: checkcast java/lang/CharSequence
         // 0a2: astore 4
         // 0a4: aload 4
         // 0a6: invokeinterface java/lang/CharSequence.length ()I 1
         // 0ab: ifne 0b1
         // 0ae: goto 0b3
         // 0b1: bipush 0
         // 0b2: istore 2
         // 0b3: iload 2
         // 0b4: ifeq 0d8
         // 0b7: aload 8
         // 0b9: invokevirtual okhttp3/Response.code ()I
         // 0bc: istore 2
         // 0bd: new java/lang/StringBuilder
         // 0c0: astore 4
         // 0c2: aload 4
         // 0c4: invokespecial java/lang/StringBuilder.<init> ()V
         // 0c7: aload 4
         // 0c9: ldc_w "HTTP "
         // 0cc: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
         // 0cf: iload 2
         // 0d0: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
         // 0d3: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
         // 0d6: astore 4
         // 0d8: aload 4
         // 0da: checkcast java/lang/String
         // 0dd: astore 5
         // 0df: aload 5
         // 0e1: astore 4
         // 0e3: aload 5
         // 0e5: ifnonnull 109
         // 0e8: aload 8
         // 0ea: invokevirtual okhttp3/Response.code ()I
         // 0ed: istore 2
         // 0ee: new java/lang/StringBuilder
         // 0f1: astore 4
         // 0f3: aload 4
         // 0f5: invokespecial java/lang/StringBuilder.<init> ()V
         // 0f8: aload 4
         // 0fa: ldc_w "HTTP "
         // 0fd: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
         // 100: iload 2
         // 101: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
         // 104: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
         // 107: astore 4
         // 109: new java/lang/RuntimeException
         // 10c: astore 5
         // 10e: aload 5
         // 110: aload 4
         // 112: invokespecial java/lang/RuntimeException.<init> (Ljava/lang/String;)V
         // 115: aload 5
         // 117: athrow
         // 118: aload 8
         // 11a: invokevirtual okhttp3/Response.body ()Lokhttp3/ResponseBody;
         // 11d: astore 5
         // 11f: aload 5
         // 121: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNull (Ljava/lang/Object;)V
         // 124: aload 5
         // 126: invokevirtual okhttp3/ResponseBody.bytes ()[B
         // 129: astore 5
         // 12b: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
         // 12e: astore 8
         // 130: aload 4
         // 132: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNull (Ljava/lang/Object;)V
         // 135: aload 1
         // 136: aload 4
         // 138: aload 6
         // 13a: aload 5
         // 13c: invokespecial com/starpilot/starview/TermWeb$Bridge.writeFile (Ljava/io/File;Ljava/lang/String;[B)Ljava/lang/String;
         // 13f: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
         // 142: astore 4
         // 144: goto 158
         // 147: astore 8
         // 149: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
         // 14c: astore 4
         // 14e: aload 8
         // 150: invokestatic kotlin/ResultKt.createFailure (Ljava/lang/Throwable;)Ljava/lang/Object;
         // 153: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
         // 156: astore 4
         // 158: aload 4
         // 15a: invokestatic kotlin/Result.exceptionOrNull-impl (Ljava/lang/Object;)Ljava/lang/Throwable;
         // 15d: ifnonnull 163
         // 160: goto 16d
         // 163: aload 1
         // 164: aload 6
         // 166: aload 5
         // 168: invokespecial com/starpilot/starview/TermWeb$Bridge.writeMediaStore (Ljava/lang/String;[B)Ljava/lang/String;
         // 16b: astore 4
         // 16d: aload 4
         // 16f: checkcast java/lang/String
         // 172: astore 4
         // 174: aload 7
         // 176: aconst_null
         // 177: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
         // 17a: new java/lang/StringBuilder
         // 17d: astore 5
         // 17f: aload 5
         // 181: invokespecial java/lang/StringBuilder.<init> ()V
         // 184: bipush 1
         // 185: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
         // 188: aload 5
         // 18a: ldc_w "Saved to Downloads ✓  "
         // 18d: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
         // 190: aload 4
         // 192: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
         // 195: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
         // 198: invokestatic kotlin/TuplesKt.to (Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;
         // 19b: astore 4
         // 19d: goto 1f3
         // 1a0: astore 5
         // 1a2: aload 5
         // 1a4: athrow
         // 1a5: astore 4
         // 1a7: aload 7
         // 1a9: aload 5
         // 1ab: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
         // 1ae: aload 4
         // 1b0: athrow
         // 1b1: new java/lang/RuntimeException
         // 1b4: astore 4
         // 1b6: aload 4
         // 1b8: ldc_w "not connected to the comma"
         // 1bb: invokespecial java/lang/RuntimeException.<init> (Ljava/lang/String;)V
         // 1be: aload 4
         // 1c0: athrow
         // 1c1: astore 4
         // 1c3: aload 4
         // 1c5: invokevirtual java/lang/Exception.getMessage ()Ljava/lang/String;
         // 1c8: astore 4
         // 1ca: bipush 0
         // 1cb: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
         // 1ce: new java/lang/StringBuilder
         // 1d1: dup
         // 1d2: invokespecial java/lang/StringBuilder.<init> ()V
         // 1d5: ldc_w "Couldn't save "
         // 1d8: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
         // 1db: aload 6
         // 1dd: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
         // 1e0: ldc_w ": "
         // 1e3: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
         // 1e6: aload 4
         // 1e8: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
         // 1eb: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
         // 1ee: invokestatic kotlin/TuplesKt.to (Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;
         // 1f1: astore 4
         // 1f3: aload 4
         // 1f5: invokevirtual kotlin/Pair.component1 ()Ljava/lang/Object;
         // 1f8: checkcast java/lang/Boolean
         // 1fb: invokevirtual java/lang/Boolean.booleanValue ()Z
         // 1fe: istore 3
         // 1ff: aload 4
         // 201: invokevirtual kotlin/Pair.component2 ()Ljava/lang/Object;
         // 204: checkcast java/lang/String
         // 207: astore 4
         // 209: aload 0
         // 20a: invokestatic org/json/JSONObject.quote (Ljava/lang/String;)Ljava/lang/String;
         // 20d: astore 0
         // 20e: aload 4
         // 210: invokestatic org/json/JSONObject.quote (Ljava/lang/String;)Ljava/lang/String;
         // 213: astore 4
         // 215: aload 1
         // 216: new java/lang/StringBuilder
         // 219: dup
         // 21a: invokespecial java/lang/StringBuilder.<init> ()V
         // 21d: ldc_w "window.dlDone("
         // 220: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
         // 223: aload 0
         // 224: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
         // 227: ldc ", "
         // 229: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
         // 22c: iload 3
         // 22d: invokevirtual java/lang/StringBuilder.append (Z)Ljava/lang/StringBuilder;
         // 230: ldc ", "
         // 232: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
         // 235: aload 4
         // 237: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
         // 23a: ldc ")"
         // 23c: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
         // 23f: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
         // 242: invokespecial com/starpilot/starview/TermWeb$Bridge.js (Ljava/lang/String;)Z
         // 245: pop
         // 246: return
      }

      private static final void showKeyboard$lambda$21(TermWeb.Bridge var0) {
         var0.web.requestFocus();
         Object var1 = var0.ctx.getSystemService("input_method");
         Intrinsics.checkNotNull(var1, "null cannot be cast to non-null type android.view.inputmethod.InputMethodManager");
         ((InputMethodManager)var1).showSoftInput((View)var0.web, 1);
      }

      private final String writeFile(File var1, String var2, byte[] var3) {
         var1.mkdirs();
         var2 = this.freeName(var1, var2);
         FilesKt.writeBytes(new File(var1, var2), var3);
         return var2;
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      private final String writeMediaStore(String var1, byte[] var2) {
         ContentValues var3 = new ContentValues();
         var3.put("_display_name", var1);
         Uri var12 = this.ctx.getContentResolver().insert(Downloads.EXTERNAL_CONTENT_URI, var3);
         if (var12 != null) {
            OutputStream var13 = this.ctx.getContentResolver().openOutputStream(var12);
            Intrinsics.checkNotNull(var13);
            var var14 = var13;

            try {
               var14.write(var2);
               Unit var11 = Unit.INSTANCE;
            } catch (Throwable var9) {
               Throwable var10 = var9;

               try {
                  throw var10;
               } finally {
                  CloseableKt.closeFinally(var14, var9);
               }
            }

            CloseableKt.closeFinally(var14, null);
            return var1;
         } else {
            throw new RuntimeException("Downloads not writable");
         }
      }

      @JavascriptInterface
      public final void checkFile(String var1) {
         Intrinsics.checkNotNullParameter(var1, "path");
         new Thread(new TermWeb$Bridge$$ExternalSyntheticLambda2(this, var1)).start();
      }

      @JavascriptInterface
      public final String clipboard() {
         ClipData var1 = (ClipData)this.ctx.getSystemService("clipboard");
         Intrinsics.checkNotNull(var1, "null cannot be cast to non-null type android.content.ClipboardManager");
         var1 = ((ClipboardManager)var1).getPrimaryClip();
         if (var1 != null) {
            Item var4 = var1.getItemAt(0);
            if (var4 != null) {
               CharSequence var5 = var4.coerceToText(this.ctx);
               if (var5 != null) {
                  String var2 = var5.toString();
                  if (var2 != null) {
                     return var2;
                  }
               }
            }
         }

         return "";
      }

      @JavascriptInterface
      public final String host() {
         String var2 = Link.INSTANCE.getHost();
         String var1 = var2;
         if (var2 == null) {
            var1 = "";
         }

         return var1;
      }

      @JavascriptInterface
      public final void listRecent(String var1) {
         Intrinsics.checkNotNullParameter(var1, "dir");
         new Thread(new TermWeb$Bridge$$ExternalSyntheticLambda1(this, var1)).start();
      }

      @JavascriptInterface
      public final boolean onroad() {
         Telemetry var3 = Link.INSTANCE.getTelemetry();
         boolean var2 = false;
         boolean var1 = var2;
         if (var3 != null) {
            var1 = var2;
            if (var3.getStarted()) {
               var1 = true;
            }
         }

         return var1;
      }

      @JavascriptInterface
      public final void saveToDownloads(String var1) {
         Intrinsics.checkNotNullParameter(var1, "path");
         new Thread(new TermWeb$Bridge$$ExternalSyntheticLambda4(var1, this)).start();
      }

      @JavascriptInterface
      public final String savedCommands() {
         SharedPreferences var2 = this.ctx.getSharedPreferences("starview_term", 0);
         String var1 = "";
         String var3 = var2.getString("saved", "");
         if (var3 != null) {
            var1 = var3;
         }

         return var1;
      }

      @JavascriptInterface
      public final String session() {
         Link.INSTANCE.loadKey(this.ctx);
         String var1 = Link.INSTANCE.getHost();
         if (var1 == null) {
            return "";
         } else {
            Link.ensureSession$default(Link.INSTANCE, var1, 0, 2, null);
            return Link.sessionFor$default(Link.INSTANCE, var1, 0, 2, null);
         }
      }

      @JavascriptInterface
      public final void sessionRefused() {
         Link.INSTANCE.forgetSession();
      }

      @JavascriptInterface
      public final void setClipboard(String var1) {
         Intrinsics.checkNotNullParameter(var1, "text");
         Object var2 = this.ctx.getSystemService("clipboard");
         Intrinsics.checkNotNull(var2, "null cannot be cast to non-null type android.content.ClipboardManager");
         ((ClipboardManager)var2).setPrimaryClip(ClipData.newPlainText("StarView terminal", var1));
      }

      @JavascriptInterface
      public final void setSavedCommands(String var1) {
         Intrinsics.checkNotNullParameter(var1, "json");
         this.ctx.getSharedPreferences("starview_term", 0).edit().putString("saved", var1).apply();
      }

      @JavascriptInterface
      public final void showKeyboard() {
         this.web.post(new TermWeb$Bridge$$ExternalSyntheticLambda0(this));
      }
   }
}
