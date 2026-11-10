package com.starpilot.starview;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref.ObjectRef;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public final class GalaxyApi {
   public static final GalaxyApi.Companion Companion = new GalaxyApi.Companion(null);
   private static final MediaType JSON = MediaType.Companion.get("application/json; charset=utf-8");
   public static final long PENDING_MS = 10000L;
   private static final String TAG = "StarView/Galaxy";
   private static volatile GalaxyApi inst;
   private final File cacheFile;
   private List<GalaxyApi.Category> catalog;
   private final OkHttpClient client;
   private final Context ctx;
   private final String host;
   private String lastValuesJson;
   private final Handler main;
   private Function0<Unit> onChanged;
   private final HashMap<String, Pair<Object, Long>> pending;
   private final HashMap<String, Object> values;
   private volatile long valuesLoadedAt;
   private volatile int version;
   private volatile int writeGen;

   // $VF: synthetic method
   public static void $r8$lambda$_ufo50C3aMoYhrJd7MbMsnHC9_k/* $VF was: $r8$lambda$_ufo50C3aMoYhrJd7MbMsnHC9_k*/(GalaxyApi var0, String var1, Function1 var2) {
      options$lambda$32(var0, var1, var2);
   }

   // $VF: synthetic method
   public static void $r8$lambda$0IWXI_f48d26y9YYRJDuyqDOxYo/* $VF was: $r8$lambda$0IWXI_f48d26y9YYRJDuyqDOxYo*/(GalaxyApi var0, int var1, Function1 var2) {
      refreshValues$lambda$16(var0, var1, var2);
   }

   // $VF: synthetic method
   public static void $r8$lambda$81f0XuoYx2mXSAxKppt6HNTgNvw(Function2 var0, ObjectRef var1, ObjectRef var2) {
      post$lambda$30$lambda$29(var0, var1, var2);
   }

   // $VF: synthetic method
   public static void $r8$lambda$MjKYZrH6tiAFLvLhVQBKMraNpeE(Function1 var0) {
      refreshValues$lambda$16$lambda$15(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$NwGn9Hb_zLt4rwX72uRQggoVR5M(GalaxyApi var0, ObjectRef var1, String var2, Object var3, Function1 var4) {
      put$lambda$24$lambda$23(var0, var1, var2, var3, var4);
   }

   // $VF: synthetic method
   public static void $r8$lambda$OqtApHVnXG4XJI2NjOn_4KjnLjw(Function1 var0, ArrayList var1) {
      options$lambda$32$lambda$31(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$Os44NSMAQ0inMLj0XoZbeaXb7pM(JSONObject var0, GalaxyApi var1, String var2) {
      put$lambda$24$lambda$22$lambda$21$lambda$20(var0, var1, var2);
   }

   // $VF: synthetic method
   public static void $r8$lambda$PEpAf0xDEKMLTKbZE3_mjEbEnOI/* $VF was: $r8$lambda$PEpAf0xDEKMLTKbZE3_mjEbEnOI*/(
      String var0, Object var1, Map var2, GalaxyApi var3, Function1 var4
   ) {
      put$lambda$24(var0, var1, var2, var3, var4);
   }

   // $VF: synthetic method
   public static void $r8$lambda$ae4a5TXqPttG37uAbvdsorookmc(GalaxyApi var0) {
      loadCatalog$lambda$9(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$eBG80m4bYw2AFWpLDQcbTYAHm4A(GalaxyApi var0, String var1, JSONObject var2, Function2 var3) {
      post$lambda$30(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static void $r8$lambda$hmKahP08qvskcCaQFkU4opJDAuA(GalaxyApi var0, List var1) {
      loadCatalog$lambda$9$lambda$8(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$ictFfJyBH_eP1MnRLCUKmiBodEw/* $VF was: $r8$lambda$ictFfJyBH_eP1MnRLCUKmiBodEw*/(
      GalaxyApi var0, int var1, Function1 var2, HashMap var3
   ) {
      refreshValues$lambda$16$lambda$14(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static void $r8$lambda$xbdndkmk7PIvjRN3f2sdJdzKxQY(GalaxyApi var0) {
      loadCatalog$lambda$10(var0);
   }

   public GalaxyApi(Context var1, String var2) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      Intrinsics.checkNotNullParameter(var2, "host");
      super();
      this.ctx = var1;
      this.host = var2;
      this.client = new Builder().connectTimeout(4L, TimeUnit.SECONDS).readTimeout(12L, TimeUnit.SECONDS).build();
      this.main = new Handler(Looper.getMainLooper());
      this.cacheFile = new File(this.ctx.getFilesDir(), "galaxy_catalog.json");
      this.catalog = CollectionsKt.emptyList();
      this.values = new HashMap<>();
      this.lastValuesJson = "";
      this.pending = new HashMap<>();
   }

   private final void applyPending(HashMap<String, Object> var1) {
      long var2 = System.currentTimeMillis();
      Iterator var4 = this.pending.entrySet().iterator();

      while (var4.hasNext()) {
         Entry var5 = (Entry)var4.next();
         Pair var6 = TuplesKt.to(var5.getKey(), var5.getValue());
         var5 = (Entry)var6.component1();
         Intrinsics.checkNotNullExpressionValue(var5, "component1(...)");
         String var8 = (String)var5;
         Object var9 = var6.component2();
         Intrinsics.checkNotNullExpressionValue(var9, "component2(...)");
         var9 = (Pair)var9;
         if (var2 - ((Number)var9.getSecond()).longValue() <= 10000L && !Intrinsics.areEqual(this.norm(var1.get(var8)), this.norm(var9.getFirst()))) {
            var1.put(var8, var9.getFirst());
         } else {
            var4.remove();
         }
      }
   }

   private final String getBase() {
      String var1 = this.host;
      return "http://" + var1 + ":8082";
   }

   private static final void loadCatalog$lambda$10(GalaxyApi var0) {
      Function0 var1 = var0.onChanged;
      if (var1 != null) {
         var1.invoke();
      }
   }

   private static final void loadCatalog$lambda$9(GalaxyApi var0) {
      try {
         String var2 = var0.get("/assets/components/tools/device_settings_layout.json");
         List var1 = var0.parseCatalog(var2);
         if (!var1.isEmpty()) {
            FilesKt.writeText$default(var0.cacheFile, var2, null, 2, null);
            Handler var3 = var0.main;
            GalaxyApi$$ExternalSyntheticLambda6 var5 = new GalaxyApi$$ExternalSyntheticLambda6(var0, var1);
            var3.post(var5);
         }
      } catch (Exception var4) {
         Log.w("StarView/Galaxy", "catalog: " + var4);
      }
   }

   private static final void loadCatalog$lambda$9$lambda$8(GalaxyApi var0, List var1) {
      var0.catalog = var1;
      Function0 var2 = var0.onChanged;
      if (var2 != null) {
         var2.invoke();
      }
   }

   private final String norm(Object var1) {
      if (var1 == null) {
         var1 = "";
      } else {
         boolean var5 = var1 instanceof Boolean;
         String var7 = "1";
         if (var5) {
            if ((Boolean)var1) {
               var1 = "1";
            } else {
               var1 = "0";
            }
         } else if (var1 instanceof Number) {
            double var2 = ((Number)var1).doubleValue();
            boolean var4;
            if (var2 == Math.floor(var2)) {
               var4 = true;
            } else {
               var4 = false;
            }

            if (var4 && !Double.isInfinite(var2)) {
               var1 = String.valueOf((long)var2);
            } else {
               var1 = String.valueOf(var2);
            }
         } else {
            String var6 = StringsKt.trim(var1.toString()).toString();
            var1 = var6.toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(var1, "toLowerCase(...)");
            if (Intrinsics.areEqual(var1, "true")) {
               var1 = var7;
            } else if (Intrinsics.areEqual(var1, "false")) {
               var1 = "0";
            } else {
               Double var10 = StringsKt.toDoubleOrNull(var6);
               var1 = var6;
               if (var10 != null) {
                  var7 = this.norm(var10.doubleValue());
                  var1 = var7;
                  if (var7 == null) {
                     var1 = var6;
                  }
               }
            }
         }
      }

      return var1;
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   private static final void options$lambda$32(GalaxyApi var0, String var1, Function1 var2) {
      ArrayList var7 = new ArrayList();

      label103: {
         JSONArray var20;
         label104: {
            Object var8;
            try {
               String var5 = var0.get(var1);
               JSONTokener var6 = new JSONTokener(var5);
               var8 = var6.nextValue();
               if (var8 instanceof JSONArray) {
                  var20 = (JSONArray)var8;
                  break label104;
               }
            } catch (Exception var19) {
               Log.w("StarView/Galaxy", "options " + var1 + ": " + var19);
               break label103;
            }

            label91: {
               JSONArray var21;
               try {
                  if (!(var8 instanceof JSONObject)) {
                     break label91;
                  }

                  var21 = ((JSONObject)var8).optJSONArray("options");
               } catch (Exception var18) {
                  Log.w("StarView/Galaxy", "options " + var1 + ": " + var18);
                  break label103;
               }

               var20 = var21;
               if (var21 == null) {
                  try {
                     var21 = ((JSONObject)var8).optJSONArray("items");
                  } catch (Exception var17) {
                     Log.w("StarView/Galaxy", "options " + var1 + ": " + var17);
                     break label103;
                  }

                  var20 = var21;
                  if (var21 == null) {
                     try {
                        var21 = ((JSONObject)var8).optJSONArray("makes");
                     } catch (Exception var16) {
                        Log.w("StarView/Galaxy", "options " + var1 + ": " + var16);
                        break label103;
                     }

                     var20 = var21;
                     if (var21 == null) {
                        try {
                           var21 = ((JSONObject)var8).optJSONArray("models");
                        } catch (Exception var15) {
                           Log.w("StarView/Galaxy", "options " + var1 + ": " + var15);
                           break label103;
                        }

                        var20 = var21;
                        if (var21 == null) {
                           try {
                              var20 = new JSONArray();
                           } catch (Exception var14) {
                              Log.w("StarView/Galaxy", "options " + var1 + ": " + var14);
                              break label103;
                           }
                        }
                     }
                  }
               }
               break label104;
            }

            try {
               var20 = new JSONArray();
            } catch (Exception var13) {
               Log.w("StarView/Galaxy", "options " + var1 + ": " + var13);
               break label103;
            }
         }

         int var3 = 0;

         int var4;
         try {
            var4 = var20.length();
         } catch (Exception var11) {
            Log.w("StarView/Galaxy", "options " + var1 + ": " + var11);
            break label103;
         }

         for (; var3 < var4; var3++) {
            Pair var26;
            label66: {
               try {
                  var26 = (Pair)var20.get(var3);
                  if (var26 instanceof JSONObject) {
                     var26 = TuplesKt.to(
                        ((JSONObject)var26).opt("value").toString(), ((JSONObject)var26).optString("label", ((JSONObject)var26).opt("value").toString())
                     );
                     break label66;
                  }
               } catch (Exception var12) {
                  Log.w("StarView/Galaxy", "options " + var1 + ": " + var12);
                  break;
               }

               try {
                  var26 = TuplesKt.to(var26.toString(), var26.toString());
               } catch (Exception var10) {
                  Log.w("StarView/Galaxy", "options " + var1 + ": " + var10);
                  break;
               }
            }

            try {
               var7.add(var26);
            } catch (Exception var9) {
               Log.w("StarView/Galaxy", "options " + var1 + ": " + var9);
               break;
            }
         }
      }

      var0.main.post(new GalaxyApi$$ExternalSyntheticLambda0(var2, var7));
   }

   private static final void options$lambda$32$lambda$31(Function1 var0, ArrayList var1) {
      var0.invoke(var1);
   }

   private final List<GalaxyApi.Category> parseCatalog(String var1) {
      JSONArray var3 = new JSONArray(var1);
      Iterable var13 = RangesKt.until(0, var3.length());
      boolean var2 = false;
      var var7 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var13, 10));
      Iterator var6 = var13.iterator();

      while (var6.hasNext()) {
         JSONObject var9 = var3.getJSONObject(((IntIterator)var6).nextInt());
         JSONArray var5 = var9.optJSONArray("params");
         JSONArray var4 = var5;
         if (var5 == null) {
            var4 = new JSONArray();
         }

         String var8 = var9.optString("name");
         Intrinsics.checkNotNullExpressionValue(var8, "optString(...)");
         String var16 = var9.optString("icon");
         Intrinsics.checkNotNullExpressionValue(var16, "optString(...)");
         Iterable var15 = RangesKt.until(0, var4.length());
         var var10 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var15, 10));
         Iterator var11 = var15.iterator();

         while (var11.hasNext()) {
            JSONObject var12 = var4.getJSONObject(((IntIterator)var11).nextInt());
            Intrinsics.checkNotNullExpressionValue(var12, "getJSONObject(...)");
            var10.add(new GalaxyApi.Param(var12));
         }

         List var14 = var10;
         var7.add(new GalaxyApi.Category(var8, var16, var14));
      }

      return var7;
   }

   // $VF: synthetic method
   public static void post$default(GalaxyApi var0, String var1, JSONObject var2, Function2 var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = null;
      }

      var0.post(var1, var2, var3);
   }

   private static final void post$lambda$30(GalaxyApi param0, String param1, JSONObject param2, Function2 param3) {
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
      // 000: new kotlin/jvm/internal/Ref$ObjectRef
      // 003: dup
      // 004: invokespecial kotlin/jvm/internal/Ref$ObjectRef.<init> ()V
      // 007: astore 6
      // 009: new kotlin/jvm/internal/Ref$ObjectRef
      // 00c: dup
      // 00d: invokespecial kotlin/jvm/internal/Ref$ObjectRef.<init> ()V
      // 010: astore 5
      // 012: new okhttp3/Request$Builder
      // 015: astore 7
      // 017: aload 7
      // 019: invokespecial okhttp3/Request$Builder.<init> ()V
      // 01c: aload 0
      // 01d: invokespecial com/starpilot/starview/GalaxyApi.getBase ()Ljava/lang/String;
      // 020: astore 8
      // 022: new java/lang/StringBuilder
      // 025: astore 9
      // 027: aload 9
      // 029: invokespecial java/lang/StringBuilder.<init> ()V
      // 02c: aload 7
      // 02e: aload 9
      // 030: aload 8
      // 032: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 035: aload 1
      // 036: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 039: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 03c: invokevirtual okhttp3/Request$Builder.url (Ljava/lang/String;)Lokhttp3/Request$Builder;
      // 03f: astore 1
      // 040: getstatic okhttp3/RequestBody.Companion Lokhttp3/RequestBody$Companion;
      // 043: astore 7
      // 045: aload 2
      // 046: ifnonnull 054
      // 049: new org/json/JSONObject
      // 04c: astore 2
      // 04d: aload 2
      // 04e: invokespecial org/json/JSONObject.<init> ()V
      // 051: goto 054
      // 054: aload 2
      // 055: invokevirtual org/json/JSONObject.toString ()Ljava/lang/String;
      // 058: astore 2
      // 059: aload 2
      // 05a: ldc_w "toString(...)"
      // 05d: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 060: aload 1
      // 061: aload 7
      // 063: aload 2
      // 064: getstatic com/starpilot/starview/GalaxyApi.JSON Lokhttp3/MediaType;
      // 067: invokevirtual okhttp3/RequestBody$Companion.create (Ljava/lang/String;Lokhttp3/MediaType;)Lokhttp3/RequestBody;
      // 06a: invokevirtual okhttp3/Request$Builder.post (Lokhttp3/RequestBody;)Lokhttp3/Request$Builder;
      // 06d: invokevirtual okhttp3/Request$Builder.build ()Lokhttp3/Request;
      // 070: astore 1
      // 071: aload 0
      // 072: getfield com/starpilot/starview/GalaxyApi.client Lokhttp3/OkHttpClient;
      // 075: aload 1
      // 076: invokevirtual okhttp3/OkHttpClient.newCall (Lokhttp3/Request;)Lokhttp3/Call;
      // 079: invokeinterface okhttp3/Call.execute ()Lokhttp3/Response; 1
      // 07e: checkcast java/io/Closeable
      // 081: astore 7
      // 083: aload 7
      // 085: checkcast okhttp3/Response
      // 088: astore 8
      // 08a: aload 8
      // 08c: invokevirtual okhttp3/Response.body ()Lokhttp3/ResponseBody;
      // 08f: astore 1
      // 090: aload 1
      // 091: ifnull 09f
      // 094: aload 1
      // 095: invokevirtual okhttp3/ResponseBody.string ()Ljava/lang/String;
      // 098: astore 2
      // 099: aload 2
      // 09a: astore 1
      // 09b: aload 2
      // 09c: ifnonnull 0a3
      // 09f: ldc_w ""
      // 0a2: astore 1
      // 0a3: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 0a6: astore 2
      // 0a7: new org/json/JSONObject
      // 0aa: astore 2
      // 0ab: aload 2
      // 0ac: aload 1
      // 0ad: invokespecial org/json/JSONObject.<init> (Ljava/lang/String;)V
      // 0b0: aload 2
      // 0b1: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 0b4: astore 1
      // 0b5: goto 0c5
      // 0b8: astore 2
      // 0b9: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 0bc: astore 1
      // 0bd: aload 2
      // 0be: invokestatic kotlin/ResultKt.createFailure (Ljava/lang/Throwable;)Ljava/lang/Object;
      // 0c1: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 0c4: astore 1
      // 0c5: aload 1
      // 0c6: astore 2
      // 0c7: aload 1
      // 0c8: invokestatic kotlin/Result.isFailure-impl (Ljava/lang/Object;)Z
      // 0cb: ifeq 0d0
      // 0ce: aconst_null
      // 0cf: astore 2
      // 0d0: aload 5
      // 0d2: aload 2
      // 0d3: putfield kotlin/jvm/internal/Ref$ObjectRef.element Ljava/lang/Object;
      // 0d6: aload 8
      // 0d8: invokevirtual okhttp3/Response.isSuccessful ()Z
      // 0db: ifne 137
      // 0de: aload 5
      // 0e0: getfield kotlin/jvm/internal/Ref$ObjectRef.element Ljava/lang/Object;
      // 0e3: checkcast org/json/JSONObject
      // 0e6: astore 1
      // 0e7: aload 1
      // 0e8: ifnull 112
      // 0eb: aload 1
      // 0ec: ldc_w "error"
      // 0ef: invokevirtual org/json/JSONObject.optString (Ljava/lang/String;)Ljava/lang/String;
      // 0f2: astore 1
      // 0f3: aload 1
      // 0f4: ifnull 112
      // 0f7: aload 1
      // 0f8: checkcast java/lang/CharSequence
      // 0fb: astore 2
      // 0fc: aload 2
      // 0fd: astore 1
      // 0fe: aload 2
      // 0ff: invokestatic kotlin/text/StringsKt.isBlank (Ljava/lang/CharSequence;)Z
      // 102: ifeq 107
      // 105: aconst_null
      // 106: astore 1
      // 107: aload 1
      // 108: checkcast java/lang/String
      // 10b: astore 2
      // 10c: aload 2
      // 10d: astore 1
      // 10e: aload 2
      // 10f: ifnonnull 131
      // 112: aload 8
      // 114: invokevirtual okhttp3/Response.code ()I
      // 117: istore 4
      // 119: new java/lang/StringBuilder
      // 11c: astore 1
      // 11d: aload 1
      // 11e: invokespecial java/lang/StringBuilder.<init> ()V
      // 121: aload 1
      // 122: ldc_w "HTTP "
      // 125: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 128: iload 4
      // 12a: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
      // 12d: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 130: astore 1
      // 131: aload 6
      // 133: aload 1
      // 134: putfield kotlin/jvm/internal/Ref$ObjectRef.element Ljava/lang/Object;
      // 137: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 13a: astore 1
      // 13b: aload 7
      // 13d: aconst_null
      // 13e: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 141: goto 16a
      // 144: astore 2
      // 145: aload 2
      // 146: athrow
      // 147: astore 1
      // 148: aload 7
      // 14a: aload 2
      // 14b: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 14e: aload 1
      // 14f: athrow
      // 150: astore 7
      // 152: aload 7
      // 154: invokevirtual java/lang/Exception.getMessage ()Ljava/lang/String;
      // 157: astore 2
      // 158: aload 2
      // 159: astore 1
      // 15a: aload 2
      // 15b: ifnonnull 164
      // 15e: aload 7
      // 160: invokevirtual java/lang/Exception.toString ()Ljava/lang/String;
      // 163: astore 1
      // 164: aload 6
      // 166: aload 1
      // 167: putfield kotlin/jvm/internal/Ref$ObjectRef.element Ljava/lang/Object;
      // 16a: aload 0
      // 16b: getfield com/starpilot/starview/GalaxyApi.main Landroid/os/Handler;
      // 16e: new com/starpilot/starview/GalaxyApi$$ExternalSyntheticLambda1
      // 171: dup
      // 172: aload 3
      // 173: aload 6
      // 175: aload 5
      // 177: invokespecial com/starpilot/starview/GalaxyApi$$ExternalSyntheticLambda1.<init> (Lkotlin/jvm/functions/Function2;Lkotlin/jvm/internal/Ref$ObjectRef;Lkotlin/jvm/internal/Ref$ObjectRef;)V
      // 17a: invokevirtual android/os/Handler.post (Ljava/lang/Runnable;)Z
      // 17d: pop
      // 17e: return
   }

   private static final void post$lambda$30$lambda$29(Function2 var0, ObjectRef var1, ObjectRef var2) {
      var0.invoke(var1.element, var2.element);
   }

   // $VF: synthetic method
   public static void put$default(GalaxyApi var0, String var1, Object var2, Map var3, Function1 var4, int var5, Object var6) {
      if ((var5 & 4) != 0) {
         var3 = MapsKt.emptyMap();
      }

      if ((var5 & 8) != 0) {
         var4 = null;
      }

      var0.put(var1, var2, var3, var4);
   }

   private static final void put$lambda$24(String param0, Object param1, Map param2, GalaxyApi param3, Function1 param4) {
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
      // 000: new kotlin/jvm/internal/Ref$ObjectRef
      // 003: dup
      // 004: invokespecial kotlin/jvm/internal/Ref$ObjectRef.<init> ()V
      // 007: astore 8
      // 009: new org/json/JSONObject
      // 00c: astore 7
      // 00e: aload 7
      // 010: invokespecial org/json/JSONObject.<init> ()V
      // 013: aload 7
      // 015: ldc_w "key"
      // 018: aload 0
      // 019: invokevirtual org/json/JSONObject.put (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
      // 01c: astore 9
      // 01e: aload 1
      // 01f: ifnonnull 02a
      // 022: getstatic org/json/JSONObject.NULL Ljava/lang/Object;
      // 025: astore 7
      // 027: goto 02d
      // 02a: aload 1
      // 02b: astore 7
      // 02d: aload 9
      // 02f: ldc_w "value"
      // 032: aload 7
      // 034: invokevirtual org/json/JSONObject.put (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
      // 037: astore 7
      // 039: aload 2
      // 03a: invokeinterface java/util/Map.entrySet ()Ljava/util/Set; 1
      // 03f: invokeinterface java/util/Set.iterator ()Ljava/util/Iterator; 1
      // 044: astore 9
      // 046: aload 9
      // 048: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 04d: ifeq 073
      // 050: aload 9
      // 052: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 057: checkcast java/util/Map$Entry
      // 05a: astore 2
      // 05b: aload 7
      // 05d: aload 2
      // 05e: invokeinterface java/util/Map$Entry.getKey ()Ljava/lang/Object; 1
      // 063: checkcast java/lang/String
      // 066: aload 2
      // 067: invokeinterface java/util/Map$Entry.getValue ()Ljava/lang/Object; 1
      // 06c: invokevirtual org/json/JSONObject.put (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
      // 06f: pop
      // 070: goto 046
      // 073: new okhttp3/Request$Builder
      // 076: astore 9
      // 078: aload 9
      // 07a: invokespecial okhttp3/Request$Builder.<init> ()V
      // 07d: aload 3
      // 07e: invokespecial com/starpilot/starview/GalaxyApi.getBase ()Ljava/lang/String;
      // 081: astore 10
      // 083: new java/lang/StringBuilder
      // 086: astore 2
      // 087: aload 2
      // 088: invokespecial java/lang/StringBuilder.<init> ()V
      // 08b: aload 9
      // 08d: aload 2
      // 08e: aload 10
      // 090: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 093: ldc_w "/api/params"
      // 096: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 099: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 09c: invokevirtual okhttp3/Request$Builder.url (Ljava/lang/String;)Lokhttp3/Request$Builder;
      // 09f: astore 9
      // 0a1: getstatic okhttp3/RequestBody.Companion Lokhttp3/RequestBody$Companion;
      // 0a4: astore 2
      // 0a5: aload 7
      // 0a7: invokevirtual org/json/JSONObject.toString ()Ljava/lang/String;
      // 0aa: astore 7
      // 0ac: aload 7
      // 0ae: ldc_w "toString(...)"
      // 0b1: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 0b4: aload 9
      // 0b6: aload 2
      // 0b7: aload 7
      // 0b9: getstatic com/starpilot/starview/GalaxyApi.JSON Lokhttp3/MediaType;
      // 0bc: invokevirtual okhttp3/RequestBody$Companion.create (Ljava/lang/String;Lokhttp3/MediaType;)Lokhttp3/RequestBody;
      // 0bf: invokevirtual okhttp3/Request$Builder.put (Lokhttp3/RequestBody;)Lokhttp3/Request$Builder;
      // 0c2: invokevirtual okhttp3/Request$Builder.build ()Lokhttp3/Request;
      // 0c5: astore 2
      // 0c6: aload 3
      // 0c7: getfield com/starpilot/starview/GalaxyApi.client Lokhttp3/OkHttpClient;
      // 0ca: aload 2
      // 0cb: invokevirtual okhttp3/OkHttpClient.newCall (Lokhttp3/Request;)Lokhttp3/Call;
      // 0ce: invokeinterface okhttp3/Call.execute ()Lokhttp3/Response; 1
      // 0d3: checkcast java/io/Closeable
      // 0d6: astore 9
      // 0d8: aload 9
      // 0da: checkcast okhttp3/Response
      // 0dd: astore 10
      // 0df: aload 10
      // 0e1: invokevirtual okhttp3/Response.body ()Lokhttp3/ResponseBody;
      // 0e4: astore 2
      // 0e5: aload 2
      // 0e6: ifnull 0f7
      // 0e9: aload 2
      // 0ea: invokevirtual okhttp3/ResponseBody.string ()Ljava/lang/String;
      // 0ed: astore 7
      // 0ef: aload 7
      // 0f1: astore 2
      // 0f2: aload 7
      // 0f4: ifnonnull 0fb
      // 0f7: ldc_w ""
      // 0fa: astore 2
      // 0fb: aload 10
      // 0fd: invokevirtual okhttp3/Response.isSuccessful ()Z
      // 100: istore 6
      // 102: iload 6
      // 104: ifne 199
      // 107: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 10a: astore 7
      // 10c: new org/json/JSONObject
      // 10f: astore 7
      // 111: aload 7
      // 113: aload 2
      // 114: invokespecial org/json/JSONObject.<init> (Ljava/lang/String;)V
      // 117: aload 7
      // 119: ldc_w "error"
      // 11c: invokevirtual org/json/JSONObject.optString (Ljava/lang/String;)Ljava/lang/String;
      // 11f: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 122: astore 2
      // 123: goto 135
      // 126: astore 7
      // 128: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 12b: astore 2
      // 12c: aload 7
      // 12e: invokestatic kotlin/ResultKt.createFailure (Ljava/lang/Throwable;)Ljava/lang/Object;
      // 131: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 134: astore 2
      // 135: aload 2
      // 136: astore 7
      // 138: aload 2
      // 139: invokestatic kotlin/Result.isFailure-impl (Ljava/lang/Object;)Z
      // 13c: ifeq 142
      // 13f: aconst_null
      // 140: astore 7
      // 142: aload 7
      // 144: checkcast java/lang/String
      // 147: astore 2
      // 148: aload 2
      // 149: ifnull 16d
      // 14c: aload 2
      // 14d: checkcast java/lang/CharSequence
      // 150: astore 7
      // 152: aload 7
      // 154: astore 2
      // 155: aload 7
      // 157: invokestatic kotlin/text/StringsKt.isBlank (Ljava/lang/CharSequence;)Z
      // 15a: ifeq 15f
      // 15d: aconst_null
      // 15e: astore 2
      // 15f: aload 2
      // 160: checkcast java/lang/String
      // 163: astore 7
      // 165: aload 7
      // 167: astore 2
      // 168: aload 7
      // 16a: ifnonnull 18c
      // 16d: aload 10
      // 16f: invokevirtual okhttp3/Response.code ()I
      // 172: istore 5
      // 174: new java/lang/StringBuilder
      // 177: astore 2
      // 178: aload 2
      // 179: invokespecial java/lang/StringBuilder.<init> ()V
      // 17c: aload 2
      // 17d: ldc_w "HTTP "
      // 180: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 183: iload 5
      // 185: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
      // 188: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 18b: astore 2
      // 18c: aload 8
      // 18e: aload 2
      // 18f: putfield kotlin/jvm/internal/Ref$ObjectRef.element Ljava/lang/Object;
      // 192: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 195: astore 2
      // 196: goto 1f9
      // 199: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 19c: astore 7
      // 19e: new org/json/JSONObject
      // 1a1: astore 7
      // 1a3: aload 7
      // 1a5: aload 2
      // 1a6: invokespecial org/json/JSONObject.<init> (Ljava/lang/String;)V
      // 1a9: aload 7
      // 1ab: ldc_w "updated"
      // 1ae: invokevirtual org/json/JSONObject.optJSONObject (Ljava/lang/String;)Lorg/json/JSONObject;
      // 1b1: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 1b4: astore 2
      // 1b5: goto 1c7
      // 1b8: astore 7
      // 1ba: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 1bd: astore 2
      // 1be: aload 7
      // 1c0: invokestatic kotlin/ResultKt.createFailure (Ljava/lang/Throwable;)Ljava/lang/Object;
      // 1c3: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 1c6: astore 2
      // 1c7: aload 2
      // 1c8: astore 7
      // 1ca: aload 2
      // 1cb: invokestatic kotlin/Result.isFailure-impl (Ljava/lang/Object;)Z
      // 1ce: ifeq 1d4
      // 1d1: aconst_null
      // 1d2: astore 7
      // 1d4: aload 7
      // 1d6: checkcast org/json/JSONObject
      // 1d9: astore 2
      // 1da: aload 2
      // 1db: ifnull 1f9
      // 1de: aload 3
      // 1df: getfield com/starpilot/starview/GalaxyApi.main Landroid/os/Handler;
      // 1e2: astore 7
      // 1e4: new com/starpilot/starview/GalaxyApi$$ExternalSyntheticLambda4
      // 1e7: astore 10
      // 1e9: aload 10
      // 1eb: aload 2
      // 1ec: aload 3
      // 1ed: aload 0
      // 1ee: invokespecial com/starpilot/starview/GalaxyApi$$ExternalSyntheticLambda4.<init> (Lorg/json/JSONObject;Lcom/starpilot/starview/GalaxyApi;Ljava/lang/String;)V
      // 1f1: aload 7
      // 1f3: aload 10
      // 1f5: invokevirtual android/os/Handler.post (Ljava/lang/Runnable;)Z
      // 1f8: pop
      // 1f9: aload 9
      // 1fb: aconst_null
      // 1fc: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 1ff: goto 22d
      // 202: astore 2
      // 203: aload 2
      // 204: athrow
      // 205: astore 7
      // 207: aload 9
      // 209: aload 2
      // 20a: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 20d: aload 7
      // 20f: athrow
      // 210: astore 9
      // 212: aload 9
      // 214: invokevirtual java/lang/Exception.getMessage ()Ljava/lang/String;
      // 217: astore 7
      // 219: aload 7
      // 21b: astore 2
      // 21c: aload 7
      // 21e: ifnonnull 227
      // 221: aload 9
      // 223: invokevirtual java/lang/Exception.toString ()Ljava/lang/String;
      // 226: astore 2
      // 227: aload 8
      // 229: aload 2
      // 22a: putfield kotlin/jvm/internal/Ref$ObjectRef.element Ljava/lang/Object;
      // 22d: aload 3
      // 22e: getfield com/starpilot/starview/GalaxyApi.main Landroid/os/Handler;
      // 231: new com/starpilot/starview/GalaxyApi$$ExternalSyntheticLambda5
      // 234: dup
      // 235: aload 3
      // 236: aload 8
      // 238: aload 0
      // 239: aload 1
      // 23a: aload 4
      // 23c: invokespecial com/starpilot/starview/GalaxyApi$$ExternalSyntheticLambda5.<init> (Lcom/starpilot/starview/GalaxyApi;Lkotlin/jvm/internal/Ref$ObjectRef;Ljava/lang/String;Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)V
      // 23f: invokevirtual android/os/Handler.post (Ljava/lang/Runnable;)Z
      // 242: pop
      // 243: return
   }

   private static final void put$lambda$24$lambda$22$lambda$21$lambda$20(JSONObject var0, GalaxyApi var1, String var2) {
      Iterator var4 = var0.keys();
      Intrinsics.checkNotNullExpressionValue(var4, "keys(...)");

      while (var4.hasNext()) {
         String var5 = (String)var4.next();
         Object var3;
         if (var0.isNull(var5)) {
            var3 = null;
         } else {
            var3 = var0.get(var5);
         }

         var1.values.put(var5, var3);
         if (!Intrinsics.areEqual(var5, var2)) {
            var1.pending.remove(var5);
         }
      }

      var1.version++;
      var1.lastValuesJson = "";
   }

   private static final void put$lambda$24$lambda$23(GalaxyApi var0, ObjectRef var1, String var2, Object var3, Function1 var4) {
      var0.writeGen++;
      if (var1.element != null) {
         var0.pending.remove(var2);
         refreshValues$default(var0, null, 1, null);
      } else {
         var0.pending.put(var2, TuplesKt.to(var3, System.currentTimeMillis()));
      }

      Function0 var5 = var0.onChanged;
      if (var5 != null) {
         var5.invoke();
      }

      if (var4 != null) {
         var4.invoke(var1.element);
      }
   }

   // $VF: synthetic method
   public static void refreshValues$default(GalaxyApi var0, Function1 var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = null;
      }

      var0.refreshValues(var1);
   }

   private static final void refreshValues$lambda$16(GalaxyApi param0, int param1, Function1 param2) {
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
      // 000: new org/json/JSONObject
      // 003: astore 10
      // 005: aload 10
      // 007: aload 0
      // 008: ldc_w "/api/params/all"
      // 00b: invokevirtual com/starpilot/starview/GalaxyApi.get (Ljava/lang/String;)Ljava/lang/String;
      // 00e: invokespecial org/json/JSONObject.<init> (Ljava/lang/String;)V
      // 011: new java/util/HashMap
      // 014: astore 6
      // 016: aload 6
      // 018: invokespecial java/util/HashMap.<init> ()V
      // 01b: aload 10
      // 01d: invokevirtual org/json/JSONObject.keys ()Ljava/util/Iterator;
      // 020: astore 8
      // 022: aload 8
      // 024: ldc_w "keys(...)"
      // 027: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 02a: aload 8
      // 02c: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 031: ifeq 06f
      // 034: aload 8
      // 036: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 03b: checkcast java/lang/String
      // 03e: astore 9
      // 040: aload 6
      // 042: checkcast java/util/Map
      // 045: astore 7
      // 047: aload 10
      // 049: aload 9
      // 04b: invokevirtual org/json/JSONObject.isNull (Ljava/lang/String;)Z
      // 04e: ifeq 057
      // 051: aconst_null
      // 052: astore 5
      // 054: goto 060
      // 057: aload 10
      // 059: aload 9
      // 05b: invokevirtual org/json/JSONObject.get (Ljava/lang/String;)Ljava/lang/Object;
      // 05e: astore 5
      // 060: aload 7
      // 062: aload 9
      // 064: aload 5
      // 066: invokeinterface java/util/Map.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
      // 06b: pop
      // 06c: goto 02a
      // 06f: getstatic com/starpilot/starview/DriveState.INSTANCE Lcom/starpilot/starview/DriveState;
      // 072: invokevirtual com/starpilot/starview/DriveState.onroad ()Ljava/lang/Boolean;
      // 075: astore 7
      // 077: bipush 0
      // 078: istore 4
      // 07a: bipush 0
      // 07b: istore 3
      // 07c: aload 7
      // 07e: ifnull 0b4
      // 081: aload 6
      // 083: checkcast java/util/Map
      // 086: ldc_w "IsOnroad"
      // 089: aload 7
      // 08b: invokeinterface java/util/Map.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
      // 090: pop
      // 091: aload 6
      // 093: checkcast java/util/Map
      // 096: astore 5
      // 098: aload 7
      // 09a: invokevirtual java/lang/Boolean.booleanValue ()Z
      // 09d: ifne 0a2
      // 0a0: bipush 1
      // 0a1: istore 3
      // 0a2: aload 5
      // 0a4: ldc_w "IsOffroad"
      // 0a7: iload 3
      // 0a8: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 0ab: invokeinterface java/util/Map.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
      // 0b0: pop
      // 0b1: goto 15b
      // 0b4: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 0b7: astore 5
      // 0b9: aload 6
      // 0bb: checkcast java/util/Map
      // 0be: astore 5
      // 0c0: aload 0
      // 0c1: ldc_w "/api/params?key=IsOnroad"
      // 0c4: invokevirtual com/starpilot/starview/GalaxyApi.get (Ljava/lang/String;)Ljava/lang/String;
      // 0c7: checkcast java/lang/CharSequence
      // 0ca: invokestatic kotlin/text/StringsKt.trim (Ljava/lang/CharSequence;)Ljava/lang/CharSequence;
      // 0cd: invokevirtual java/lang/Object.toString ()Ljava/lang/String;
      // 0d0: astore 7
      // 0d2: aload 7
      // 0d4: ldc_w "1"
      // 0d7: invokestatic kotlin/jvm/internal/Intrinsics.areEqual (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 0da: ifne 0fb
      // 0dd: aload 7
      // 0df: ldc_w "true"
      // 0e2: invokestatic kotlin/jvm/internal/Intrinsics.areEqual (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 0e5: ifne 0fb
      // 0e8: aload 7
      // 0ea: ldc_w "True"
      // 0ed: invokestatic kotlin/jvm/internal/Intrinsics.areEqual (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 0f0: ifeq 0f6
      // 0f3: goto 0fb
      // 0f6: bipush 0
      // 0f7: istore 3
      // 0f8: goto 0fd
      // 0fb: bipush 1
      // 0fc: istore 3
      // 0fd: aload 5
      // 0ff: ldc_w "IsOnroad"
      // 102: iload 3
      // 103: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 106: invokeinterface java/util/Map.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
      // 10b: pop
      // 10c: aload 6
      // 10e: checkcast java/util/Map
      // 111: astore 5
      // 113: iload 4
      // 115: istore 3
      // 116: aload 6
      // 118: ldc_w "IsOnroad"
      // 11b: invokevirtual java/util/HashMap.get (Ljava/lang/Object;)Ljava/lang/Object;
      // 11e: bipush 1
      // 11f: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 122: invokestatic kotlin/jvm/internal/Intrinsics.areEqual (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 125: ifne 12a
      // 128: bipush 1
      // 129: istore 3
      // 12a: aload 5
      // 12c: ldc_w "IsOffroad"
      // 12f: iload 3
      // 130: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 133: invokeinterface java/util/Map.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
      // 138: pop
      // 139: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 13c: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 13f: astore 5
      // 141: goto 155
      // 144: astore 5
      // 146: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 149: astore 7
      // 14b: aload 5
      // 14d: invokestatic kotlin/ResultKt.createFailure (Ljava/lang/Throwable;)Ljava/lang/Object;
      // 150: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 153: astore 5
      // 155: aload 5
      // 157: invokestatic kotlin/Result.box-impl (Ljava/lang/Object;)Lkotlin/Result;
      // 15a: pop
      // 15b: aload 0
      // 15c: getfield com/starpilot/starview/GalaxyApi.main Landroid/os/Handler;
      // 15f: astore 7
      // 161: new com/starpilot/starview/GalaxyApi$$ExternalSyntheticLambda8
      // 164: astore 5
      // 166: aload 5
      // 168: aload 0
      // 169: iload 1
      // 16a: aload 2
      // 16b: aload 6
      // 16d: invokespecial com/starpilot/starview/GalaxyApi$$ExternalSyntheticLambda8.<init> (Lcom/starpilot/starview/GalaxyApi;ILkotlin/jvm/functions/Function1;Ljava/util/HashMap;)V
      // 170: aload 7
      // 172: aload 5
      // 174: invokevirtual android/os/Handler.post (Ljava/lang/Runnable;)Z
      // 177: pop
      // 178: goto 1a8
      // 17b: astore 5
      // 17d: ldc "StarView/Galaxy"
      // 17f: new java/lang/StringBuilder
      // 182: dup
      // 183: invokespecial java/lang/StringBuilder.<init> ()V
      // 186: ldc_w "values: "
      // 189: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 18c: aload 5
      // 18e: invokevirtual java/lang/StringBuilder.append (Ljava/lang/Object;)Ljava/lang/StringBuilder;
      // 191: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 194: invokestatic android/util/Log.w (Ljava/lang/String;Ljava/lang/String;)I
      // 197: pop
      // 198: aload 0
      // 199: getfield com/starpilot/starview/GalaxyApi.main Landroid/os/Handler;
      // 19c: new com/starpilot/starview/GalaxyApi$$ExternalSyntheticLambda9
      // 19f: dup
      // 1a0: aload 2
      // 1a1: invokespecial com/starpilot/starview/GalaxyApi$$ExternalSyntheticLambda9.<init> (Lkotlin/jvm/functions/Function1;)V
      // 1a4: invokevirtual android/os/Handler.post (Ljava/lang/Runnable;)Z
      // 1a7: pop
      // 1a8: return
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private static final void refreshValues$lambda$16$lambda$14(GalaxyApi var0, int var1, Function1 var2, HashMap var3) {
      var0.valuesLoadedAt = System.currentTimeMillis();
      int var4 = var0.writeGen;
      Boolean var7 = true;
      if (var1 != var4) {
         if (var2 != null) {
            var2.invoke(var7);
         }
      } else {
         var0.applyPending(var3);

         Object var15;
         label71:
         try {
            var15 = Result.Companion;
            Intrinsics.checkNotNull(var3, "null cannot be cast to non-null type kotlin.collections.Map<*, *>");
            var15 = new JSONObject(var3);
            var15 = KtCompat.resultOf(var15.toString());
         } catch (Throwable var9) {
            var15 = Result.Companion;
            var15 = KtCompat.resultOf(ResultKt.createFailure(var9));
            break label71;
         }

         Object var6 = var15;
         if (KtCompat.isFailure(var15)) {
            var6 = "";
         }

         Intrinsics.checkNotNullExpressionValue(var6, "getOrDefault(...)");
         var15 = (String)var6;
         var1 = ((CharSequence)var15).length();
         boolean var14 = false;
         boolean var12;
         if (var1 == 0) {
            var12 = true;
         } else {
            var12 = false;
         }

         label65: {
            if (!var12) {
               var12 = var14;
               if (Intrinsics.areEqual(var15, var0.lastValuesJson)) {
                  break label65;
               }
            }

            var12 = true;
         }

         if (var12) {
            var0.lastValuesJson = (String)var15;
            var0.values.clear();
            var0.values.putAll(var3);
            var0.version++;
            Function0 var10 = var0.onChanged;
            if (var10 != null) {
               var10.invoke();
            }
         }

         if (var2 != null) {
            var2.invoke(var7);
         }
      }
   }

   private static final void refreshValues$lambda$16$lambda$15(Function1 var0) {
      if (var0 != null) {
         var0.invoke(false);
      }
   }

   public final boolean bool(String var1) {
      Intrinsics.checkNotNullParameter(var1, "key");
      Object var3 = this.values.get(var1);
      boolean var2;
      if (var3 instanceof Boolean) {
         var2 = (Boolean)var3;
      } else {
         if (var3 instanceof Number) {
            if (((Number)var3).intValue() != 0) {
               var2 = true;
               return var2;
            }
         } else if (var3 instanceof String) {
            Set var4 = SetsKt.setOf(new String[]{"1", "true", "yes", "on"});
            var3 = StringsKt.trim((String & CharSequence)var3).toString().toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(var3, "toLowerCase(...)");
            var2 = var4.contains(var3);
            return var2;
         }

         var2 = false;
      }

      return var2;
   }

   public final Double double_(String var1) {
      Intrinsics.checkNotNullParameter(var1, "key");
      Object var4 = this.values.get(var1);
      double var2;
      if (var4 instanceof Number) {
         var2 = ((Number)var4).doubleValue();
      } else {
         if (var4 instanceof String) {
            return StringsKt.toDoubleOrNull((String)var4);
         }

         if (!(var4 instanceof Boolean)) {
            return null;
         }

         if ((Boolean)var4) {
            var2 = 1.0;
         } else {
            var2 = 0.0;
         }
      }

      return var2;
   }

   public final String get(String param1) {
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
      // 00: aload 1
      // 01: ldc_w "path"
      // 04: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
      // 07: aload 0
      // 08: getfield com/starpilot/starview/GalaxyApi.client Lokhttp3/OkHttpClient;
      // 0b: astore 4
      // 0d: new okhttp3/Request$Builder
      // 10: dup
      // 11: invokespecial okhttp3/Request$Builder.<init> ()V
      // 14: astore 5
      // 16: aload 0
      // 17: invokespecial com/starpilot/starview/GalaxyApi.getBase ()Ljava/lang/String;
      // 1a: astore 3
      // 1b: aload 4
      // 1d: aload 5
      // 1f: new java/lang/StringBuilder
      // 22: dup
      // 23: invokespecial java/lang/StringBuilder.<init> ()V
      // 26: aload 3
      // 27: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 2a: aload 1
      // 2b: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 2e: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 31: invokevirtual okhttp3/Request$Builder.url (Ljava/lang/String;)Lokhttp3/Request$Builder;
      // 34: invokevirtual okhttp3/Request$Builder.build ()Lokhttp3/Request;
      // 37: invokevirtual okhttp3/OkHttpClient.newCall (Lokhttp3/Request;)Lokhttp3/Call;
      // 3a: invokeinterface okhttp3/Call.execute ()Lokhttp3/Response; 1
      // 3f: checkcast java/io/Closeable
      // 42: astore 4
      // 44: aload 4
      // 46: checkcast okhttp3/Response
      // 49: astore 5
      // 4b: aload 5
      // 4d: invokevirtual okhttp3/Response.isSuccessful ()Z
      // 50: ifeq 74
      // 53: aload 5
      // 55: invokevirtual okhttp3/Response.body ()Lokhttp3/ResponseBody;
      // 58: astore 1
      // 59: aload 1
      // 5a: ifnull 68
      // 5d: aload 1
      // 5e: invokevirtual okhttp3/ResponseBody.string ()Ljava/lang/String;
      // 61: astore 3
      // 62: aload 3
      // 63: astore 1
      // 64: aload 3
      // 65: ifnonnull 6c
      // 68: ldc_w ""
      // 6b: astore 1
      // 6c: aload 4
      // 6e: aconst_null
      // 6f: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 72: aload 1
      // 73: areturn
      // 74: new java/lang/IllegalStateException
      // 77: astore 3
      // 78: aload 5
      // 7a: invokevirtual okhttp3/Response.code ()I
      // 7d: istore 2
      // 7e: new java/lang/StringBuilder
      // 81: astore 5
      // 83: aload 5
      // 85: invokespecial java/lang/StringBuilder.<init> ()V
      // 88: aload 3
      // 89: aload 5
      // 8b: ldc_w "HTTP "
      // 8e: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 91: iload 2
      // 92: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
      // 95: ldc_w " "
      // 98: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 9b: aload 1
      // 9c: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 9f: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // a2: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
      // a5: aload 3
      // a6: athrow
      // a7: astore 3
      // a8: aload 3
      // a9: athrow
      // aa: astore 1
      // ab: aload 4
      // ad: aload 3
      // ae: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // b1: aload 1
      // b2: athrow
   }

   public final List<GalaxyApi.Category> getCatalog() {
      return this.catalog;
   }

   public final boolean getMetric() {
      return this.bool("IsMetric");
   }

   public final Function0<Unit> getOnChanged() {
      return this.onChanged;
   }

   public final HashMap<String, Object> getValues() {
      return this.values;
   }

   public final long getValuesLoadedAt() {
      return this.valuesLoadedAt;
   }

   public final int getVersion() {
      return this.version;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public final void loadCatalog() {
      if (this.catalog.isEmpty() && this.cacheFile.exists()) {
         GalaxyApi var5;
         label33:
         try {
            var5 = Result.Companion;
            var5 = this;
            var5.catalog = var5.parseCatalog(FilesKt.readText$default(var5.cacheFile, null, 1, null));
            var5 = (GalaxyApi)KtCompat.resultOf(Unit.INSTANCE);
         } catch (Throwable var4) {
            Result.Companion var1 = Result.Companion;
            var5 = (GalaxyApi)KtCompat.resultOf(ResultKt.createFailure(var4));
            break label33;
         }

         Throwable var8 = KtCompat.exceptionOrNull(var5);
         if (var8 != null) {
            Log.w("StarView/Galaxy", "cached catalog: " + var8);
         }
      }

      new Thread(new GalaxyApi$$ExternalSyntheticLambda10(this)).start();
      if (!this.catalog.isEmpty()) {
         this.main.post(new GalaxyApi$$ExternalSyntheticLambda11(this));
      }
   }

   public final void options(String var1, Function1<? super List<Pair<String, String>>, Unit> var2) {
      Intrinsics.checkNotNullParameter(var1, "path");
      Intrinsics.checkNotNullParameter(var2, "then");
      new Thread(new GalaxyApi$$ExternalSyntheticLambda2(this, var1, var2)).start();
   }

   public final void post(String var1, JSONObject var2, Function2<? super String, ? super JSONObject, Unit> var3) {
      Intrinsics.checkNotNullParameter(var1, "path");
      Intrinsics.checkNotNullParameter(var3, "then");
      new Thread(new GalaxyApi$$ExternalSyntheticLambda3(this, var1, var2, var3)).start();
   }

   public final void put(String var1, Object var2, Map<String, ? extends Object> var3, Function1<? super String, Unit> var4) {
      Intrinsics.checkNotNullParameter(var1, "key");
      Intrinsics.checkNotNullParameter(var3, "extra");
      this.values.put(var1, var2);
      this.version++;
      this.lastValuesJson = "";
      this.writeGen++;
      this.pending.put(var1, TuplesKt.to(var2, System.currentTimeMillis()));
      Function0 var5 = this.onChanged;
      if (var5 != null) {
         var5.invoke();
      }

      new Thread(new GalaxyApi$$ExternalSyntheticLambda12(var1, var2, var3, this, var4)).start();
   }

   public final void refreshValues(Function1<? super Boolean, Unit> var1) {
      new Thread(new GalaxyApi$$ExternalSyntheticLambda7(this, this.writeGen, var1)).start();
   }

   public final void setOnChanged(Function0<Unit> var1) {
      this.onChanged = var1;
   }

   public final void setValuesLoadedAt(long var1) {
      this.valuesLoadedAt = var1;
   }

   public final void setVersion(int var1) {
      this.version = var1;
   }

   public final String string(String var1) {
      Intrinsics.checkNotNullParameter(var1, "key");
      Object var3 = this.values.get(var1);
      if (var3 != null) {
         String var2 = var3.toString();
         if (var2 != null) {
            return var2;
         }
      }

      return "";
   }

   public static final class Category {
      private final String icon;
      private final String name;
      private final List<GalaxyApi.Param> params;

      public Category(String var1, String var2, List<GalaxyApi.Param> var3) {
         Intrinsics.checkNotNullParameter(var1, "name");
         Intrinsics.checkNotNullParameter(var2, "icon");
         Intrinsics.checkNotNullParameter(var3, "params");
         super();
         this.name = var1;
         this.icon = var2;
         this.params = var3;
      }

      public final String getIcon() {
         return this.icon;
      }

      public final String getName() {
         return this.name;
      }

      public final List<GalaxyApi.Param> getParams() {
         return this.params;
      }
   }

   public static final class Companion {
      private Companion() {
      }

      // $VF: synthetic method
      public Companion(DefaultConstructorMarker var1) {
         this();
      }

      public final GalaxyApi current() {
         return GalaxyApi.inst;
      }

      public final GalaxyApi get(Context var1, String var2) {
         Intrinsics.checkNotNullParameter(var1, "ctx");
         Intrinsics.checkNotNullParameter(var2, "host");
         GalaxyApi var3 = GalaxyApi.inst;
         if (var3 != null && Intrinsics.areEqual(var3.host, var2)) {
            return var3;
         } else {
            var1 = var1.getApplicationContext();
            Intrinsics.checkNotNullExpressionValue(var1, "getApplicationContext(...)");
            GalaxyApi var6 = new GalaxyApi(var1, var2);
            GalaxyApi.Companion var5 = GalaxyApi.Companion;
            GalaxyApi.inst = var6;
            return var6;
         }
      }
   }

   public static final class Param {
      private final String dataType;
      private final String description;
      private final boolean isParentToggle;
      private final String key;
      private final String label;
      private final JSONObject o;
      private final List<Pair<String, String>> options;
      private final String parentKey;
      private final String tier;
      private final String uiType;
      private final String unit;
      private final String unitType;

      public Param(JSONObject var1) {
         Intrinsics.checkNotNullParameter(var1, "o");
         super();
         this.o = var1;
         String var6 = this.o.optString("key");
         Intrinsics.checkNotNullExpressionValue(var6, "optString(...)");
         this.key = var6;
         String var7 = this.o.optString("label", this.key);
         Intrinsics.checkNotNullExpressionValue(var7, "optString(...)");
         this.label = var7;
         var var8 = this.o.optString("description", "");
         boolean var2 = StringsKt.isBlank(var8);
         Collection var3 = null;
         if (var2) {
            var8 = null;
         }

         this.description = var8;
         String var9 = this.o.optString("data_type", "");
         Intrinsics.checkNotNullExpressionValue(var9, "optString(...)");
         this.dataType = var9;
         String var10 = this.o.optString("ui_type", "");
         Intrinsics.checkNotNullExpressionValue(var10, "optString(...)");
         this.uiType = var10;
         CharSequence var4 = this.o.optString("parent_key", "");
         CharSequence var11 = var4;
         if (StringsKt.isBlank(var4)) {
            var11 = null;
         }

         this.parentKey = (String)var11;
         this.isParentToggle = this.o.optBoolean("is_parent_toggle", false);
         String var12 = this.o.optString("settings_tier", "simple");
         Intrinsics.checkNotNullExpressionValue(var12, "optString(...)");
         this.tier = var12;
         var var13 = this.o.optString("unit_type", "");
         if (StringsKt.isBlank(var13)) {
            var13 = var3;
         }

         this.unitType = var13;
         String var14 = this.o.optString("unit", "");
         Intrinsics.checkNotNullExpressionValue(var14, "optString(...)");
         this.unit = var14;
         JSONArray var15 = this.o.optJSONArray("options");
         List var16;
         if (var15 != null) {
            Iterable var18 = RangesKt.until(0, var15.length());
            var3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var18, 10));
            Iterator var5 = var18.iterator();

            while (var5.hasNext()) {
               JSONObject var19 = var15.getJSONObject(((IntIterator)var5).nextInt());
               var3.add(TuplesKt.to(var19.opt("value").toString(), var19.optString("label", var19.opt("value").toString())));
            }

            var16 = (List)var3;
         } else {
            var16 = CollectionsKt.emptyList();
         }

         this.options = var16;
      }

      public final String getDataType() {
         return this.dataType;
      }

      public final String getDescription() {
         return this.description;
      }

      public final String getKey() {
         return this.key;
      }

      public final String getLabel() {
         return this.label;
      }

      public final JSONObject getO() {
         return this.o;
      }

      public final List<Pair<String, String>> getOptions() {
         return this.options;
      }

      public final String getParentKey() {
         return this.parentKey;
      }

      public final String getTier() {
         return this.tier;
      }

      public final String getUiType() {
         return this.uiType;
      }

      public final String getUnit() {
         return this.unit;
      }

      public final String getUnitType() {
         return this.unitType;
      }

      public final boolean has(String var1) {
         Intrinsics.checkNotNullParameter(var1, "name");
         boolean var2;
         if (this.o.has(var1) && !this.o.isNull(var1)) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }

      public final boolean isParentToggle() {
         return this.isParentToggle;
      }

      public final double num(String var1, double var2) {
         Intrinsics.checkNotNullParameter(var1, "name");
         if (this.o.has(var1) && !this.o.isNull(var1)) {
            var2 = this.o.optDouble(var1, var2);
         }

         return var2;
      }

      public final String str(String var1) {
         Intrinsics.checkNotNullParameter(var1, "name");
         if (this.has(var1)) {
            var1 = this.o.optString(var1);
         } else {
            var1 = null;
         }

         return var1;
      }

      public final List<String> strList(String var1) {
         Intrinsics.checkNotNullParameter(var1, "name");
         JSONArray var4 = this.o.optJSONArray(var1);
         List var5;
         if (var4 != null) {
            Iterable var3 = RangesKt.until(0, var4.length());
            var var2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var3, 10));
            Iterator var6 = var3.iterator();

            while (var6.hasNext()) {
               var2.add(var4.optString(((IntIterator)var6).nextInt()));
            }

            var5 = var2;
         } else {
            var5 = CollectionsKt.emptyList();
         }

         return var5;
      }
   }
}
