package com.starpilot.starview;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.collections.SetsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.io.FilesKt;
import kotlin.io.TextStreamsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.DoubleCompanionObject;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.jvm.internal.Ref.IntRef;
import kotlin.jvm.internal.Ref.ObjectRef;
import kotlin.ranges.RangesKt;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import org.json.JSONArray;
import org.json.JSONObject;

public final class MapdMaps {
   public static final double BOX = 0.25;
   private static final String[] CLASSES = new String[]{
      "unknown",
      "motorway",
      "motorway_link",
      "trunk",
      "trunk_link",
      "primary",
      "primary_link",
      "secondary",
      "secondary_link",
      "tertiary",
      "tertiary_link",
      "unclassified",
      "residential",
      "living_street"
   };
   public static final int GROUP = 2;
   public static final MapdMaps INSTANCE = new MapdMaps();
   private static final String TAG = "StarView/Maps";
   private static final String URL = "https://map-data.pfeifer.dev/offline/%d/%d.tar.gz";
   private static volatile String busy = "";
   private static volatile boolean cancel;
   private static File dir;
   private static final ConcurrentHashMap<Pair<Integer, Integer>, Long> doneCache = new ConcurrentHashMap<>();
   private static final Lazy http$delegate = LazyKt.lazy(new MapdMaps$$ExternalSyntheticLambda4());
   private static volatile String lastError = "";
   private static Function0<Unit> onChange;
   private static SharedPreferences prefs;
   private static List<MapdMaps.State> states = CollectionsKt.emptyList();
   private static final ExecutorService worker = Executors.newSingleThreadExecutor(new MapdMaps$$ExternalSyntheticLambda5());

   // $VF: synthetic method
   public static Thread $r8$lambda$3G4T1SShpN3VIvM7tbQZzQaAyeo(Runnable var0) {
      return worker$lambda$17(var0);
   }

   // $VF: synthetic method
   public static OkHttpClient $r8$lambda$7f15o4K19fLeK2lGAx9svoXDiWk() {
      return http_delegate$lambda$15();
   }

   // $VF: synthetic method
   public static void $r8$lambda$8lOz__HD_KU1coUNiNS1_5hWw_Q/* $VF was: $r8$lambda$8lOz__HD_KU1coUNiNS1_5hWw_Q*/(MapdMaps.State var0, boolean var1) {
      download$lambda$20(var0, var1);
   }

   // $VF: synthetic method
   public static MapdMaps.State $r8$lambda$_vwH22XiQpTvFheq9vK3yxkJc98(JSONObject var0, String var1) {
      return init$lambda$5(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$nSpjdysoK2oq_KzsYkjfvASPH6I/* $VF was: $r8$lambda$nSpjdysoK2oq_KzsYkjfvASPH6I*/(MapdMaps.State var0, Set var1) {
      delete$lambda$23(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$nV5yToUuGtXphIk_CkJpaRsMbGA(MapdMaps.State var0, int var1, List var2, double var3) {
      return download$lambda$20$lambda$19(var0, var1, var2, var3);
   }

   private MapdMaps() {
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   private final void changed() {
      Function0 var1;
      try {
         var1 = onChange;
      } catch (Exception var3) {
         return;
      }

      if (var1 != null) {
         try {
            var1.invoke();
         } catch (Exception var2) {
         }
      }
   }

   private final MapdMaps.Lst compositeList(MapdMaps.Msg var1, MapdMaps.Ref var2) {
      if (var2 != null && (var2.getP() & 3L) == 1L && (int)(var2.getP() >>> 32 & 7L) == 7) {
         long var3 = var1.w(var2.getS(), var2.getAt());
         return new MapdMaps.Lst(var2.getS(), var2.getAt() + 1, this.off(var3), (int)(var3 >>> 32 & 65535L), (int)(65535L & var3 >>> 48));
      } else {
         return null;
      }
   }

   private static final void delete$lambda$23(MapdMaps.State var0, Set var1) {
      Iterable var3 = states;
      var var2 = new ArrayList();

      for (var var3_it : var3) {

         var3 = var3_it;
         if (var1.contains(((MapdMaps.State)var3).getCode())) {
            var2.add(var3);
         }
      }

      List var6 = var2;
      Iterable var10 = var6;
      var var7 = new ArrayList();

      for (MapdMaps.State var14 : var10) {
         CollectionsKt.addAll(var7, INSTANCE.groupsOf(var14));
      }

      List var8 = var7;
      var1 = CollectionsKt.toSet(var8);

      for (Pair var12 : INSTANCE.groupsOf(var0)) {
         if (!var1.contains(var12)) {
            FilesKt.deleteRecursively(INSTANCE.groupDir(var12));
            doneCache.remove(var12);
         }
      }

      INSTANCE.changed();
   }

   // $VF: synthetic method
   public static void download$default(MapdMaps var0, MapdMaps.State var1, boolean var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = false;
      }

      var0.download(var1, var2);
   }

   private static final void download$lambda$20(MapdMaps.State var0, boolean var1) {
      int var3 = 0;
      cancel = false;
      lastError = "";
      Iterable var7 = INSTANCE.groupsOf(var0);
      var var6 = new ArrayList();
      Iterator var17 = var7.iterator();

      while (true) {
         boolean var5 = var17.hasNext();
         boolean var4 = true;
         if (!var5) {
            List var19 = var6;
            Iterator var18 = var19.iterator();

            for (int var12 = var3; var18.hasNext(); var12++) {
               Pair var14 = (Pair)var18.next();
               if (cancel) {
                  break;
               }

               String var20 = var0.getName();
               var3 = var19.size();
               busy = var20 + ": area " + (var12 + 1) + " of " + var3;
               INSTANCE.changed();

               try {
                  MapdMaps var10 = INSTANCE;
                  MapdMaps$$ExternalSyntheticLambda2 var22 = new MapdMaps$$ExternalSyntheticLambda2(var0, var12, var19);
                  var10.fetchGroup(var14, var22);
               } catch (Exception var11) {
                  Log.w("StarView/Maps", "group " + var14, var11);
                  var20 = var0.getName();
                  String var15 = var11.getMessage();
                  String var16;
                  if (var15 != null) {
                     var16 = StringsKt.take(var15, 60);
                  } else {
                     var16 = null;
                  }

                  lastError = var20 + ": " + var16;
               }
            }

            busy = "";
            INSTANCE.changed();
            return;
         }

         Object var8 = var17.next();
         Pair var9 = (Pair)var8;
         boolean var2 = var4;
         if (!var1) {
            if (INSTANCE.groupDone(var9) == 0L) {
               var2 = var4;
            } else {
               var2 = false;
            }
         }

         if (var2) {
            var6.add(var8);
         }
      }
   }

   private static final Unit download$lambda$20$lambda$19(MapdMaps.State var0, int var1, List var2, double var3) {
      String var6 = var0.getName();
      int var5 = var2.size();
      String var7 = String.format("%.0f", Arrays.copyOf(new Object[]{var3}, 1));
      Intrinsics.checkNotNullExpressionValue(var7, "format(...)");
      busy = var6 + ": area " + (var1 + 1) + " of " + var5 + ", " + var7 + " MB";
      INSTANCE.changed();
      return Unit.INSTANCE;
   }

   private final void fetchGroup(Pair<Integer, Integer> param1, Function1<? super Double, Unit> param2) {
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
      // 002: invokespecial com/starpilot/starview/MapdMaps.groupDir (Lkotlin/Pair;)Ljava/io/File;
      // 005: astore 4
      // 007: getstatic com/starpilot/starview/MapdMaps.dir Ljava/io/File;
      // 00a: astore 7
      // 00c: aload 1
      // 00d: invokevirtual kotlin/Pair.getFirst ()Ljava/lang/Object;
      // 010: astore 5
      // 012: aload 1
      // 013: invokevirtual kotlin/Pair.getSecond ()Ljava/lang/Object;
      // 016: astore 6
      // 018: new java/io/File
      // 01b: dup
      // 01c: aload 7
      // 01e: new java/lang/StringBuilder
      // 021: dup
      // 022: invokespecial java/lang/StringBuilder.<init> ()V
      // 025: ldc_w "tmp_"
      // 028: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 02b: aload 5
      // 02d: invokevirtual java/lang/StringBuilder.append (Ljava/lang/Object;)Ljava/lang/StringBuilder;
      // 030: ldc_w "_"
      // 033: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 036: aload 6
      // 038: invokevirtual java/lang/StringBuilder.append (Ljava/lang/Object;)Ljava/lang/StringBuilder;
      // 03b: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 03e: invokespecial java/io/File.<init> (Ljava/io/File;Ljava/lang/String;)V
      // 041: astore 6
      // 043: aload 6
      // 045: invokestatic kotlin/io/FilesKt.deleteRecursively (Ljava/io/File;)Z
      // 048: pop
      // 049: aload 6
      // 04b: invokevirtual java/io/File.mkdirs ()Z
      // 04e: pop
      // 04f: new okhttp3/Request$Builder
      // 052: dup
      // 053: invokespecial okhttp3/Request$Builder.<init> ()V
      // 056: astore 5
      // 058: getstatic kotlin/jvm/internal/StringCompanionObject.INSTANCE Lkotlin/jvm/internal/StringCompanionObject;
      // 05b: astore 7
      // 05d: getstatic java/util/Locale.US Ljava/util/Locale;
      // 060: ldc "https://map-data.pfeifer.dev/offline/%d/%d.tar.gz"
      // 062: bipush 2
      // 063: anewarray 4
      // 066: dup
      // 067: bipush 0
      // 068: aload 1
      // 069: invokevirtual kotlin/Pair.getFirst ()Ljava/lang/Object;
      // 06c: aastore
      // 06d: dup
      // 06e: bipush 1
      // 06f: aload 1
      // 070: invokevirtual kotlin/Pair.getSecond ()Ljava/lang/Object;
      // 073: aastore
      // 074: bipush 2
      // 075: invokestatic java/util/Arrays.copyOf ([Ljava/lang/Object;I)[Ljava/lang/Object;
      // 078: invokestatic java/lang/String.format (Ljava/util/Locale;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
      // 07b: astore 7
      // 07d: aload 7
      // 07f: ldc_w "format(...)"
      // 082: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 085: aload 5
      // 087: aload 7
      // 089: invokevirtual okhttp3/Request$Builder.url (Ljava/lang/String;)Lokhttp3/Request$Builder;
      // 08c: ldc_w "User-Agent"
      // 08f: ldc_w "StarView (openpilot tablet display)"
      // 092: invokevirtual okhttp3/Request$Builder.header (Ljava/lang/String;Ljava/lang/String;)Lokhttp3/Request$Builder;
      // 095: invokevirtual okhttp3/Request$Builder.build ()Lokhttp3/Request;
      // 098: astore 5
      // 09a: aload 0
      // 09b: invokespecial com/starpilot/starview/MapdMaps.getHttp ()Lokhttp3/OkHttpClient;
      // 09e: aload 5
      // 0a0: invokevirtual okhttp3/OkHttpClient.newCall (Lokhttp3/Request;)Lokhttp3/Call;
      // 0a3: invokeinterface okhttp3/Call.execute ()Lokhttp3/Response; 1
      // 0a8: checkcast java/io/Closeable
      // 0ab: astore 5
      // 0ad: aload 5
      // 0af: checkcast okhttp3/Response
      // 0b2: astore 7
      // 0b4: aload 7
      // 0b6: invokevirtual okhttp3/Response.code ()I
      // 0b9: istore 3
      // 0ba: iload 3
      // 0bb: sipush 404
      // 0be: if_icmpne 0fc
      // 0c1: aload 4
      // 0c3: invokestatic kotlin/io/FilesKt.deleteRecursively (Ljava/io/File;)Z
      // 0c6: pop
      // 0c7: aload 4
      // 0c9: invokevirtual java/io/File.mkdirs ()Z
      // 0cc: pop
      // 0cd: new java/io/File
      // 0d0: astore 2
      // 0d1: aload 2
      // 0d2: aload 4
      // 0d4: ldc_w ".done"
      // 0d7: invokespecial java/io/File.<init> (Ljava/io/File;Ljava/lang/String;)V
      // 0da: aload 2
      // 0db: invokestatic java/lang/System.currentTimeMillis ()J
      // 0de: invokestatic java/lang/String.valueOf (J)Ljava/lang/String;
      // 0e1: aconst_null
      // 0e2: bipush 2
      // 0e3: aconst_null
      // 0e4: invokestatic kotlin/io/FilesKt.writeText$default (Ljava/io/File;Ljava/lang/String;Ljava/nio/charset/Charset;ILjava/lang/Object;)V
      // 0e7: aload 6
      // 0e9: invokestatic kotlin/io/FilesKt.deleteRecursively (Ljava/io/File;)Z
      // 0ec: pop
      // 0ed: getstatic com/starpilot/starview/MapdMaps.doneCache Ljava/util/concurrent/ConcurrentHashMap;
      // 0f0: aload 1
      // 0f1: invokevirtual java/util/concurrent/ConcurrentHashMap.remove (Ljava/lang/Object;)Ljava/lang/Object;
      // 0f4: pop
      // 0f5: aload 5
      // 0f7: aconst_null
      // 0f8: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 0fb: return
      // 0fc: aload 7
      // 0fe: invokevirtual okhttp3/Response.isSuccessful ()Z
      // 101: ifeq 1a1
      // 104: aload 7
      // 106: invokevirtual okhttp3/Response.body ()Lokhttp3/ResponseBody;
      // 109: astore 7
      // 10b: aload 7
      // 10d: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNull (Ljava/lang/Object;)V
      // 110: aload 7
      // 112: invokevirtual okhttp3/ResponseBody.byteStream ()Ljava/io/InputStream;
      // 115: astore 8
      // 117: new com/starpilot/starview/MapdMaps$fetchGroup$1$counted$1
      // 11a: astore 7
      // 11c: aload 7
      // 11e: aload 2
      // 11f: aload 8
      // 121: invokespecial com/starpilot/starview/MapdMaps$fetchGroup$1$counted$1.<init> (Lkotlin/jvm/functions/Function1;Ljava/io/InputStream;)V
      // 124: getstatic com/starpilot/starview/MapdMaps.INSTANCE Lcom/starpilot/starview/MapdMaps;
      // 127: astore 8
      // 129: new java/util/zip/GZIPInputStream
      // 12c: astore 2
      // 12d: aload 2
      // 12e: aload 7
      // 130: checkcast java/io/InputStream
      // 133: ldc_w 65536
      // 136: invokespecial java/util/zip/GZIPInputStream.<init> (Ljava/io/InputStream;I)V
      // 139: aload 8
      // 13b: aload 2
      // 13c: checkcast java/io/InputStream
      // 13f: aload 6
      // 141: invokevirtual com/starpilot/starview/MapdMaps.untar$app_debug (Ljava/io/InputStream;Ljava/io/File;)V
      // 144: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 147: astore 2
      // 148: aload 5
      // 14a: aconst_null
      // 14b: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 14e: new java/io/File
      // 151: dup
      // 152: aload 6
      // 154: ldc_w ".done"
      // 157: invokespecial java/io/File.<init> (Ljava/io/File;Ljava/lang/String;)V
      // 15a: invokestatic java/lang/System.currentTimeMillis ()J
      // 15d: invokestatic java/lang/String.valueOf (J)Ljava/lang/String;
      // 160: aconst_null
      // 161: bipush 2
      // 162: aconst_null
      // 163: invokestatic kotlin/io/FilesKt.writeText$default (Ljava/io/File;Ljava/lang/String;Ljava/nio/charset/Charset;ILjava/lang/Object;)V
      // 166: aload 4
      // 168: invokevirtual java/io/File.getParentFile ()Ljava/io/File;
      // 16b: astore 2
      // 16c: aload 2
      // 16d: ifnull 175
      // 170: aload 2
      // 171: invokevirtual java/io/File.mkdirs ()Z
      // 174: pop
      // 175: aload 4
      // 177: invokestatic kotlin/io/FilesKt.deleteRecursively (Ljava/io/File;)Z
      // 17a: pop
      // 17b: getstatic com/starpilot/starview/MapdMaps.doneCache Ljava/util/concurrent/ConcurrentHashMap;
      // 17e: aload 1
      // 17f: invokevirtual java/util/concurrent/ConcurrentHashMap.remove (Ljava/lang/Object;)Ljava/lang/Object;
      // 182: pop
      // 183: aload 6
      // 185: aload 4
      // 187: invokevirtual java/io/File.renameTo (Ljava/io/File;)Z
      // 18a: ifeq 196
      // 18d: getstatic com/starpilot/starview/MapdMaps.doneCache Ljava/util/concurrent/ConcurrentHashMap;
      // 190: aload 1
      // 191: invokevirtual java/util/concurrent/ConcurrentHashMap.remove (Ljava/lang/Object;)Ljava/lang/Object;
      // 194: pop
      // 195: return
      // 196: new java/io/IOException
      // 199: dup
      // 19a: ldc_w "could not store the map files"
      // 19d: invokespecial java/io/IOException.<init> (Ljava/lang/String;)V
      // 1a0: athrow
      // 1a1: new java/io/IOException
      // 1a4: astore 1
      // 1a5: aload 7
      // 1a7: invokevirtual okhttp3/Response.code ()I
      // 1aa: istore 3
      // 1ab: new java/lang/StringBuilder
      // 1ae: astore 2
      // 1af: aload 2
      // 1b0: invokespecial java/lang/StringBuilder.<init> ()V
      // 1b3: aload 1
      // 1b4: aload 2
      // 1b5: ldc_w "HTTP "
      // 1b8: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 1bb: iload 3
      // 1bc: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
      // 1bf: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 1c2: invokespecial java/io/IOException.<init> (Ljava/lang/String;)V
      // 1c5: aload 1
      // 1c6: athrow
      // 1c7: astore 1
      // 1c8: goto 1cc
      // 1cb: astore 1
      // 1cc: aload 1
      // 1cd: athrow
      // 1ce: astore 2
      // 1cf: aload 5
      // 1d1: aload 1
      // 1d2: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 1d5: aload 2
      // 1d6: athrow
   }

   private final MapdMaps.Ref follow(MapdMaps.Msg var1, int var2, int var3) {
      long var4 = var1.w(var2, var3);
      if (var4 == 0L) {
         return null;
      } else if ((3L & var4) != 2L) {
         return new MapdMaps.Ref(var2, var4, var3 + 1 + this.off(var4));
      } else {
         var2 = (int)(var4 >>> 32);
         var3 = (int)(var4 >>> 3 & 536870911L);
         if ((4L & var4) == 0L) {
            var4 = var1.w(var2, var3);
            return new MapdMaps.Ref(var2, var4, var3 + 1 + this.off(var4));
         } else {
            var4 = var1.w(var2, var3);
            long var6 = var1.w(var2, var3 + 1);
            return new MapdMaps.Ref((int)(var4 >>> 32), var6, (int)(var4 >>> 3 & 536870911L));
         }
      }
   }

   private final OkHttpClient getHttp() {
      return (OkHttpClient)http$delegate.getValue();
   }

   private final File groupDir(Pair<Integer, Integer> var1) {
      File var2 = dir;
      Object var3 = var1.getFirst();
      Object var4 = var1.getSecond();
      return new File(var2, var3 + "/" + var4);
   }

   private static final OkHttpClient http_delegate$lambda$15() {
      return new Builder().connectTimeout(15L, TimeUnit.SECONDS).readTimeout(60L, TimeUnit.SECONDS).build();
   }

   private static final MapdMaps.State init$lambda$5(JSONObject var0, String var1) {
      JSONObject var3 = var0.getJSONObject(var1);
      JSONObject var2 = var3.getJSONObject("bounding_box");
      JSONArray var7 = var3.optJSONArray("download_rows");
      List var8;
      if (var7 != null) {
         Iterable var5 = RangesKt.until(0, var7.length());
         var var4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var5, 10));
         Iterator var10 = var5.iterator();

         while (var10.hasNext()) {
            JSONArray var6 = var7.getJSONArray(((IntIterator)var10).nextInt());
            var4.add(new int[]{var6.getInt(0), var6.getInt(1), var6.getInt(2)});
         }

         var8 = var4;
      } else {
         var8 = CollectionsKt.emptyList();
      }

      Intrinsics.checkNotNull(var1);
      String var9 = var3.getString("full_name");
      Intrinsics.checkNotNullExpressionValue(var9, "getString(...)");
      return new MapdMaps.State(var1, var9, var2.getDouble("min_lat"), var2.getDouble("min_lon"), var2.getDouble("max_lat"), var2.getDouble("max_lon"), var8);
   }

   private final int off(long var1) {
      return (int)var1 >> 2;
   }

   private final void setSelected(Set<String> var1) {
      SharedPreferences var2 = prefs;
      if (var2 != null) {
         Editor var4 = var2.edit();
         if (var4 != null) {
            Editor var3 = var4.putStringSet("states", new HashSet(var1));
            if (var3 != null) {
               var3.apply();
            }
         }
      }
   }

   private final String text(MapdMaps.Msg var1, MapdMaps.Ref var2) {
      if (var2 != null && (var2.getP() & 3L) == 1L && (int)(var2.getP() >>> 32 & 7L) == 2) {
         int var4 = (int)(var2.getP() >>> 35) - 1;
         if (var4 <= 0) {
            return "";
         } else {
            byte[] var7 = new byte[var4];
            int var5 = var1.getSeg()[var2.getS()];
            int var6 = var2.getAt();

            for (int var3 = 0; var3 < var4; var3++) {
               var7[var3] = var1.getB().get((var5 + var6) * 8 + var3);
            }

            return new String(var7, Charsets.UTF_8);
         }
      } else {
         return "";
      }
   }

   private static final void unpack$need(IntRef var0, ObjectRef<byte[]> var1, int var2) {
      if (var0.element + var2 > ((byte[])var1.element).length) {
         byte[] var3 = Arrays.copyOf((byte[])var1.element, Math.max(((byte[])var1.element).length * 2, var0.element + var2));
         Intrinsics.checkNotNullExpressionValue(var3, "copyOf(...)");
         var1.element = (T)var3;
      }
   }

   private static final boolean untar$readFully(InputStream var0, byte[] var1, int var2) {
      int var3 = 0;

      while (var3 < var2) {
         int var4 = var0.read(var1, var3, var2 - var3);
         if (var4 < 0) {
            return false;
         }

         var3 += var4;
      }

      return true;
   }

   private static final Thread worker$lambda$17(Runnable var0) {
      Thread var1 = new Thread(var0, "starview-maps");
      var1.setDaemon(true);
      var1.setPriority(1);
      return var1;
   }

   public final Pair<Boolean, File> boxFileFor(double var1, double var3) {
      double var5 = 2;
      Pair var9 = new Pair<>((int)Math.floor(var1 / var5) * 2, (int)Math.floor(var3 / var5) * 2);
      File var8 = dir;
      File var7 = null;
      if (var8 != null && this.groupDone(var9) != 0L) {
         var1 = Math.floor(var1 / 0.25) * 0.25;
         var3 = Math.floor(var3 / 0.25) * 0.25;
         var8 = this.groupDir(var9);
         StringCompanionObject var14 = StringCompanionObject.INSTANCE;
         String var15 = String.format(Locale.US, "%f_%f_%f_%f", Arrays.copyOf(new Object[]{var1, var3, var1 + 0.25, 0.25 + var3}, 4));
         Intrinsics.checkNotNullExpressionValue(var15, "format(...)");
         var8 = new File(var8, var15);
         if (var8.isFile()) {
            var7 = var8;
         }

         return new Pair<>(true, var7);
      } else {
         return new Pair<>(false, null);
      }
   }

   public final void cancelAll() {
      cancel = true;
   }

   public final void delete(MapdMaps.State var1) {
      Intrinsics.checkNotNullParameter(var1, "st");
      Set var2 = SetsKt.minus(this.selected(), var1.getCode());
      this.setSelected(var2);
      worker.execute(new MapdMaps$$ExternalSyntheticLambda3(var1, var2));
   }

   public final void download(MapdMaps.State var1, boolean var2) {
      Intrinsics.checkNotNullParameter(var1, "st");
      this.setSelected(SetsKt.plus(this.selected(), var1.getCode()));
      worker.execute(new MapdMaps$$ExternalSyntheticLambda0(var1, var2));
   }

   public final String getBusy() {
      return busy;
   }

   public final String[] getCLASSES() {
      return CLASSES;
   }

   public final String getLastError() {
      return lastError;
   }

   public final Function0<Unit> getOnChange() {
      return onChange;
   }

   public final List<MapdMaps.State> getStates() {
      return states;
   }

   public final long groupDone(Pair<Integer, Integer> var1) {
      Intrinsics.checkNotNullParameter(var1, "g");
      ConcurrentMap var6 = doneCache;
      Object var5 = var6.get(var1);
      Object var4 = var5;
      if (var5 == null) {
         long var2;
         try {
            var4 = new File(INSTANCE.groupDir(var1), ".done");
            var2 = Long.parseLong(StringsKt.trim(FilesKt.readText$default((File)var4, null, 1, null)).toString());
         } catch (Exception var7) {
            var2 = 0L;
         }

         var4 = var2;
         Object var8 = var6.putIfAbsent(var1, var4);
         if (var8 != null) {
            var4 = var8;
         }
      }

      Intrinsics.checkNotNullExpressionValue(var4, "getOrPut(...)");
      return ((Number)var4).longValue();
   }

   public final List<Pair<Integer, Integer>> groupsOf(MapdMaps.State var1) {
      Intrinsics.checkNotNullParameter(var1, "st");
      if (!var1.getRows().isEmpty()) {
         Iterable var17 = var1.getRows();
         var var12 = new ArrayList();

         for (int[] var14 : var17) {
            Iterable var15 = RangesKt.step(RangesKt.until(var14[1], var14[2]), 2);
            var var13 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var15, 10));
            Iterator var24 = var15.iterator();

            while (var24.hasNext()) {
               int var18 = ((IntIterator)var24).nextInt();
               var13.add(TuplesKt.to(var14[0], var18));
            }

            List var22 = var13;
            Iterable var23 = var22;
            CollectionsKt.addAll(var12, var23);
         }

         return var12;
      } else {
         double var2 = var1.getMinLat();
         double var4 = 2;
         int var9 = (int)Math.floor(var2 / var4);
         int var10 = (int)Math.floor(var1.getMinLon() / var4);
         int var7 = (int)Math.floor(var1.getMaxLat() / var4) * 2;
         int var8 = (int)Math.floor(var1.getMaxLon() / var4) * 2;
         int var6 = var7;
         if (var1.getMaxLat() > var7) {
            var6 = var7 + 2;
         }

         var7 = var8;
         if (var1.getMaxLon() > var8) {
            var7 = var8 + 2;
         }

         ArrayList var16 = new ArrayList();

         for (int var20 = var9 * 2; var20 < var6; var20 += 2) {
            for (int var21 = var10 * 2; var21 < var7; var21 += 2) {
               var16.add(TuplesKt.to(var20, var21));
            }
         }

         return var16;
      }
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   public final void init(Context var1) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      if (dir == null) {
         File var2 = new File(var1.getFilesDir(), "mapd");
         var2.mkdirs();
         dir = var2;
         prefs = var1.getApplicationContext().getSharedPreferences("starview_maps", 0);

         label45: {
            label36: {
               try {
                  InputStream var3 = var1.getAssets().open("maps/us_states.json");
                  Intrinsics.checkNotNullExpressionValue(var3, "open(...)");
                  Charset var10 = Charsets.UTF_8;
                  InputStreamReader var4 = new InputStreamReader(var3, var10);
                  var11 = var4;
                  if (var11 instanceof BufferedReader) {
                     var12 = (BufferedReader)var11;
                     break label36;
                  }
               } catch (Exception var8) {
                  Log.e("StarView/Maps", "states", var8);
                  var9 = CollectionsKt.emptyList();
                  break label45;
               }

               try {
                  var12 = new BufferedReader(var11, 8192);
               } catch (Exception var7) {
                  Log.e("StarView/Maps", "states", var7);
                  var9 = CollectionsKt.emptyList();
                  break label45;
               }
            }

            try {
               var16 = new JSONObject(TextStreamsKt.readText(var12));
               Iterator var13 = var16.keys();
               Intrinsics.checkNotNullExpressionValue(var13, "keys(...)");
               Sequence var14 = SequencesKt.asSequence(var13);
               MapdMaps$$ExternalSyntheticLambda1 var18 = new MapdMaps$$ExternalSyntheticLambda1(var16);
               var17 = SequencesKt.map(var14, var18);
            } catch (Exception var6) {
               Log.e("StarView/Maps", "states", var6);
               var9 = CollectionsKt.emptyList();
               break label45;
            }

            try {
               Comparator var15 = new Comparator() {
                  @Override
                  public final int compare(T var1, T var2x) {
                     return ComparisonsKt.compareValues(((MapdMaps.State)var1).getName(), ((MapdMaps.State)var2x).getName());
                  }
               };
               var9 = SequencesKt.toList(SequencesKt.sortedWith(var17, var15));
            } catch (Exception var5) {
               Log.e("StarView/Maps", "states", var5);
               var9 = CollectionsKt.emptyList();
            }
         }

         states = var9;
      }
   }

   public final List<MapdMaps.W> readBox(byte[] var1) {
      Intrinsics.checkNotNullParameter(var1, "packed");
      MapdMaps.Msg var16 = new MapdMaps.Msg(this.unpack(var1));
      MapdMaps.Ref var21 = this.follow(var16, 0, 0);
      if (var21 == null) {
         return CollectionsKt.emptyList();
      } else {
         int var2 = (int)(var21.getP() >>> 32 & 65535L);
         MapdMaps.Lst var17 = this.compositeList(var16, this.follow(var16, var21.getS(), var21.getAt() + var2));
         if (var17 == null) {
            return CollectionsKt.emptyList();
         } else {
            ArrayList var15 = new ArrayList(var17.getCount());
            int var3 = 0;

            for (int var7 = var17.getCount(); var3 < var7; var3++) {
               int var9 = var17.getStart() + (var17.getDw() + var17.getPc()) * var3;
               int var8 = var17.getS();
               int var10 = var17.getDw();
               if (var17.getPc() >= 3) {
                  MapdMaps.Lst var14 = this.compositeList(var16, this.follow(var16, var8, var9 + var10 + 2));
                  if (var14 != null && var14.getCount() >= 2 && var14.getDw() >= 2) {
                     double[] var18 = new double[var14.getCount()];
                     double[] var19 = new double[var14.getCount()];
                     int var4 = var14.getCount();

                     for (int var5 = 0; var5 < var4; var5++) {
                        int var6 = var14.getStart() + (var14.getDw() + var14.getPc()) * var5;
                        DoubleCompanionObject var20 = DoubleCompanionObject.INSTANCE;
                        var18[var5] = Double.longBitsToDouble(var16.w(var14.getS(), var6));
                        var20 = DoubleCompanionObject.INSTANCE;
                        var19[var5] = Double.longBitsToDouble(var16.w(var14.getS(), var6 + 1));
                     }

                     int var23 = (var16.getSeg()[var8] + var9) * 8;
                     if (var10 > 5) {
                        var4 = var16.getB().get(var23 + 40) & 255;
                     } else {
                        var4 = 0;
                     }

                     boolean var11;
                     if (var10 > 5 && (var16.getB().get(var23 + 41) & 1) != 0) {
                        var11 = true;
                     } else {
                        var11 = false;
                     }

                     if (var10 > 5) {
                        var23 = var16.getB().getShort(var23 + 42) & '\uffff';
                     } else {
                        var23 = 0;
                     }

                     long var12;
                     if (var10 > 9) {
                        var12 = var16.w(var8, var9 + 9);
                     } else {
                        var12 = 0L;
                     }

                     var var27 = this.text(var16, this.follow(var16, var8, var9 + var10));
                     boolean var25;
                     if (var27.length() == 0) {
                        var25 = true;
                     } else {
                        var25 = false;
                     }

                     if (var25) {
                        var27 = INSTANCE.text(var16, INSTANCE.follow(var16, var8, var9 + var10 + 1));
                     }

                     String var31 = var27;
                     String[] var28 = CLASSES;
                     if (var23 >= 0 && var23 < var28.length) {
                        var25 = true;
                     } else {
                        var25 = false;
                     }

                     String var29;
                     if (var25) {
                        var29 = var28[var23];
                     } else {
                        var29 = "unknown";
                     }

                     var15.add(new MapdMaps.W(var12, var31, var29, var4, var11, var18, var19));
                  }
               }
            }

            return var15;
         }
      }
   }

   public final Set<String> selected() {
      SharedPreferences var1 = prefs;
      if (var1 != null) {
         Set var2 = var1.getStringSet("states", SetsKt.emptySet());
         if (var2 != null) {
            return var2;
         }
      }

      return SetsKt.emptySet();
   }

   public final void setOnChange(Function0<Unit> var1) {
      onChange = var1;
   }

   public final long stateBytes(MapdMaps.State var1) {
      Intrinsics.checkNotNullParameter(var1, "st");
      Iterator var12 = this.groupsOf(var1).iterator();
      long var6 = 0L;

      while (var12.hasNext()) {
         Pair var10 = (Pair)var12.next();
         File[] var13 = INSTANCE.groupDir(var10).listFiles();
         long var8;
         if (var13 != null) {
            int var3 = var13.length;
            int var2 = 0;
            long var4 = 0L;

            while (true) {
               var8 = var4;
               if (var2 >= var3) {
                  break;
               }

               File var11 = var13[var2];
               var4 += var11.length();
               var2++;
            }
         } else {
            var8 = 0L;
         }

         var6 += var8;
      }

      return var6;
   }

   public final Pair<Integer, Long> stateStatus(MapdMaps.State var1) {
      Intrinsics.checkNotNullParameter(var1, "st");
      Iterable var7 = this.groupsOf(var1);
      var var9 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var7, 10));

      for (Pair var17 : var7) {
         var9.add(INSTANCE.groupDone(var17));
      }

      List var10 = var9;
      var7 = var10;
      boolean var4 = var7 instanceof Collection;
      long var5 = 0L;
      int var2;
      if (var4 && ((Collection)var7).isEmpty()) {
         var2 = 0;
      } else {
         var2 = 0;
         Iterator var19 = var7.iterator();

         while (var19.hasNext()) {
            boolean var3;
            if (((Number)var19.next()).longValue() > 0L) {
               var3 = 1;
            } else {
               var3 = 0;
            }

            if (var3) {
               var3 = var2 + 1;
               var2 = var3;
               if (var3 < 0) {
                  CollectionsKt.throwCountOverflow();
                  var2 = var3;
               }
            }
         }
      }

      byte var14;
      if (var2 == 0) {
         var14 = 0;
      } else if (var2 == var10.size()) {
         var14 = 2;
      } else {
         var14 = 1;
      }

      var7 = var10;
      var var11 = new ArrayList();

      for (Object var22 : var7) {
         boolean var16;
         if (((Number)var22).longValue() > 0L) {
            var16 = true;
         } else {
            var16 = false;
         }

         if (var16) {
            var11.add(var22);
         }
      }

      List var12 = var11;
      Long var13 = CollectionsKt.minOrNull(var12);
      if (var13 != null) {
         var5 = var13;
      }

      return new Pair<>(Integer.valueOf(var14), var5);
   }

   public final byte[] unpack(byte[] var1) {
      Intrinsics.checkNotNullParameter(var1, "src");
      ObjectRef var9 = new ObjectRef();
      var9.element = (T)(new byte[Math.max(64, var1.length * 3)]);
      IntRef var8 = new IntRef();
      int var3 = 0;

      label29:
      while (var3 < var1.length) {
         int var5 = var3 + 1;
         int var6 = var1[var3] & 255;
         unpack$need(var8, var9, 8);
         int var4 = 0;
         var3 = var5;

         while (true) {
            byte var2 = 0;
            if (var4 >= 8) {
               var8.element += 8;
               switch (var6) {
                  case 0:
                     var4 = (var1[var3] & 255) * 8;
                     unpack$need(var8, var9, var4);
                     Arrays.fill((byte[])var9.element, var8.element, var8.element + var4, (byte)0);
                     var8.element += var4;
                     var3++;
                     continue label29;
                  case 255:
                     var4 = var3 + 1;
                     var3 = (var1[var3] & 255) * 8;
                     unpack$need(var8, var9, var3);
                     System.arraycopy(var1, var4, var9.element, var8.element, var3);
                     var8.element += var3;
                     var3 = var4 + var3;
                  default:
                     continue label29;
               }
            }

            byte[] var10 = (byte[])var9.element;
            int var7 = var8.element;
            var5 = var3;
            if ((1 << var4 & var6) != 0) {
               var2 = var1[var3];
               var5 = var3 + 1;
            }

            var10[var7 + var4] = var2;
            var4++;
            var3 = var5;
         }
      }

      var1 = Arrays.copyOf((byte[])var9.element, var8.element);
      Intrinsics.checkNotNullExpressionValue(var1, "copyOf(...)");
      return var1;
   }

   public final void untar$app_debug(InputStream param1, File param2) {
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
      // 000: aload 1
      // 001: ldc_w "inp"
      // 004: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
      // 007: aload 2
      // 008: ldc_w "into"
      // 00b: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
      // 00e: sipush 512
      // 011: newarray 8
      // 013: astore 14
      // 015: ldc_w 65536
      // 018: newarray 8
      // 01a: astore 15
      // 01c: aload 1
      // 01d: aload 14
      // 01f: sipush 512
      // 022: invokestatic com/starpilot/starview/MapdMaps.untar$readFully (Ljava/io/InputStream;[BI)Z
      // 025: ifeq 23a
      // 028: aload 14
      // 02a: arraylength
      // 02b: istore 5
      // 02d: bipush 0
      // 02e: istore 3
      // 02f: iload 3
      // 030: iload 5
      // 032: if_icmpge 055
      // 035: aload 14
      // 037: iload 3
      // 038: baload
      // 039: ifne 042
      // 03c: bipush 1
      // 03d: istore 4
      // 03f: goto 045
      // 042: bipush 0
      // 043: istore 4
      // 045: iload 4
      // 047: ifne 04f
      // 04a: bipush 0
      // 04b: istore 3
      // 04c: goto 057
      // 04f: iinc 3 1
      // 052: goto 02f
      // 055: bipush 1
      // 056: istore 3
      // 057: iload 3
      // 058: ifne 23a
      // 05b: new java/lang/String
      // 05e: dup
      // 05f: aload 14
      // 061: bipush 0
      // 062: bipush 100
      // 064: getstatic kotlin/text/Charsets.US_ASCII Ljava/nio/charset/Charset;
      // 067: invokespecial java/lang/String.<init> ([BIILjava/nio/charset/Charset;)V
      // 06a: bipush 1
      // 06b: newarray 5
      // 06d: dup
      // 06e: bipush 0
      // 06f: bipush 0
      // 070: castore
      // 071: invokestatic kotlin/text/StringsKt.trimEnd (Ljava/lang/String;[C)Ljava/lang/String;
      // 074: astore 12
      // 076: new java/lang/String
      // 079: dup
      // 07a: aload 14
      // 07c: sipush 345
      // 07f: sipush 155
      // 082: getstatic kotlin/text/Charsets.US_ASCII Ljava/nio/charset/Charset;
      // 085: invokespecial java/lang/String.<init> ([BIILjava/nio/charset/Charset;)V
      // 088: bipush 1
      // 089: newarray 5
      // 08b: dup
      // 08c: bipush 0
      // 08d: bipush 0
      // 08e: castore
      // 08f: invokestatic kotlin/text/StringsKt.trimEnd (Ljava/lang/String;[C)Ljava/lang/String;
      // 092: astore 16
      // 094: new java/lang/String
      // 097: dup
      // 098: aload 14
      // 09a: bipush 124
      // 09c: bipush 12
      // 09e: getstatic kotlin/text/Charsets.US_ASCII Ljava/nio/charset/Charset;
      // 0a1: invokespecial java/lang/String.<init> ([BIILjava/nio/charset/Charset;)V
      // 0a4: bipush 2
      // 0a5: newarray 5
      // 0a7: dup
      // 0a8: bipush 0
      // 0a9: ldc 0
      // 0ab: castore
      // 0ac: dup
      // 0ad: bipush 1
      // 0ae: ldc_w 32
      // 0b1: castore
      // 0b2: invokestatic kotlin/text/StringsKt.trim (Ljava/lang/String;[C)Ljava/lang/String;
      // 0b5: checkcast java/lang/CharSequence
      // 0b8: astore 13
      // 0ba: aload 13
      // 0bc: invokeinterface java/lang/CharSequence.length ()I 1
      // 0c1: ifne 0c9
      // 0c4: bipush 1
      // 0c5: istore 3
      // 0c6: goto 0cb
      // 0c9: bipush 0
      // 0ca: istore 3
      // 0cb: iload 3
      // 0cc: ifeq 0d4
      // 0cf: ldc_w "0"
      // 0d2: astore 13
      // 0d4: aload 13
      // 0d6: checkcast java/lang/String
      // 0d9: bipush 8
      // 0db: invokestatic kotlin/text/CharsKt.checkRadix (I)I
      // 0de: invokestatic java/lang/Long.parseLong (Ljava/lang/String;I)J
      // 0e1: lstore 8
      // 0e3: aload 14
      // 0e5: sipush 156
      // 0e8: baload
      // 0e9: i2c
      // 0ea: istore 4
      // 0ec: aload 16
      // 0ee: checkcast java/lang/CharSequence
      // 0f1: invokeinterface java/lang/CharSequence.length ()I 1
      // 0f6: ifle 0fe
      // 0f9: bipush 1
      // 0fa: istore 3
      // 0fb: goto 100
      // 0fe: bipush 0
      // 0ff: istore 3
      // 100: iload 3
      // 101: ifeq 123
      // 104: new java/lang/StringBuilder
      // 107: dup
      // 108: invokespecial java/lang/StringBuilder.<init> ()V
      // 10b: aload 16
      // 10d: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 110: ldc_w "/"
      // 113: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 116: aload 12
      // 118: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 11b: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 11e: astore 13
      // 120: goto 127
      // 123: aload 12
      // 125: astore 13
      // 127: aload 13
      // 129: bipush 47
      // 12b: aconst_null
      // 12c: bipush 2
      // 12d: aconst_null
      // 12e: invokestatic kotlin/text/StringsKt.substringAfterLast$default (Ljava/lang/String;CLjava/lang/String;ILjava/lang/Object;)Ljava/lang/String;
      // 131: astore 16
      // 133: iload 4
      // 135: bipush 48
      // 137: if_icmpeq 13f
      // 13a: iload 4
      // 13c: ifne 183
      // 13f: aload 16
      // 141: checkcast java/lang/CharSequence
      // 144: invokeinterface java/lang/CharSequence.length ()I 1
      // 149: ifle 151
      // 14c: bipush 1
      // 14d: istore 3
      // 14e: goto 153
      // 151: bipush 0
      // 152: istore 3
      // 153: iload 3
      // 154: ifeq 183
      // 157: aload 16
      // 159: ldc_w "."
      // 15c: bipush 0
      // 15d: bipush 2
      // 15e: aconst_null
      // 15f: invokestatic kotlin/text/StringsKt.startsWith$default (Ljava/lang/String;Ljava/lang/String;ZILjava/lang/Object;)Z
      // 162: ifne 183
      // 165: aload 16
      // 167: checkcast java/lang/CharSequence
      // 16a: astore 13
      // 16c: new kotlin/text/Regex
      // 16f: dup
      // 170: ldc_w "[-0-9._]+"
      // 173: invokespecial kotlin/text/Regex.<init> (Ljava/lang/String;)V
      // 176: aload 13
      // 178: invokevirtual kotlin/text/Regex.matches (Ljava/lang/CharSequence;)Z
      // 17b: ifeq 183
      // 17e: bipush 1
      // 17f: istore 3
      // 180: goto 185
      // 183: bipush 0
      // 184: istore 3
      // 185: iload 3
      // 186: ifeq 1a9
      // 189: new java/io/BufferedOutputStream
      // 18c: dup
      // 18d: new java/io/FileOutputStream
      // 190: dup
      // 191: new java/io/File
      // 194: dup
      // 195: aload 2
      // 196: aload 16
      // 198: invokespecial java/io/File.<init> (Ljava/io/File;Ljava/lang/String;)V
      // 19b: invokespecial java/io/FileOutputStream.<init> (Ljava/io/File;)V
      // 19e: checkcast java/io/OutputStream
      // 1a1: invokespecial java/io/BufferedOutputStream.<init> (Ljava/io/OutputStream;)V
      // 1a4: astore 13
      // 1a6: goto 1ac
      // 1a9: aconst_null
      // 1aa: astore 13
      // 1ac: lload 8
      // 1ae: lstore 6
      // 1b0: lload 6
      // 1b2: lconst_0
      // 1b3: lcmp
      // 1b4: ifle 20f
      // 1b7: aload 15
      // 1b9: arraylength
      // 1ba: istore 4
      // 1bc: iload 4
      // 1be: i2l
      // 1bf: lstore 10
      // 1c1: aload 1
      // 1c2: aload 15
      // 1c4: bipush 0
      // 1c5: lload 10
      // 1c7: lload 6
      // 1c9: invokestatic java/lang/Math.min (JJ)J
      // 1cc: l2i
      // 1cd: invokevirtual java/io/InputStream.read ([BII)I
      // 1d0: istore 4
      // 1d2: iload 4
      // 1d4: iflt 1f1
      // 1d7: aload 13
      // 1d9: ifnull 1e6
      // 1dc: aload 13
      // 1de: aload 15
      // 1e0: bipush 0
      // 1e1: iload 4
      // 1e3: invokevirtual java/io/BufferedOutputStream.write ([BII)V
      // 1e6: lload 6
      // 1e8: iload 4
      // 1ea: i2l
      // 1eb: lsub
      // 1ec: lstore 6
      // 1ee: goto 1b0
      // 1f1: new java/io/EOFException
      // 1f4: astore 1
      // 1f5: aload 1
      // 1f6: ldc_w "tar cut short"
      // 1f9: invokespecial java/io/EOFException.<init> (Ljava/lang/String;)V
      // 1fc: aload 1
      // 1fd: athrow
      // 1fe: astore 1
      // 1ff: goto 203
      // 202: astore 1
      // 203: aload 13
      // 205: ifnull 20d
      // 208: aload 13
      // 20a: invokevirtual java/io/BufferedOutputStream.close ()V
      // 20d: aload 1
      // 20e: athrow
      // 20f: aload 13
      // 211: ifnull 219
      // 214: aload 13
      // 216: invokevirtual java/io/BufferedOutputStream.close ()V
      // 219: sipush 512
      // 21c: i2l
      // 21d: lstore 6
      // 21f: lload 6
      // 221: lload 8
      // 223: lload 6
      // 225: lrem
      // 226: lsub
      // 227: lload 6
      // 229: lrem
      // 22a: l2i
      // 22b: istore 3
      // 22c: iload 3
      // 22d: ifle 01c
      // 230: aload 1
      // 231: aload 14
      // 233: iload 3
      // 234: invokestatic com/starpilot/starview/MapdMaps.untar$readFully (Ljava/io/InputStream;[BI)Z
      // 237: ifne 01c
      // 23a: return
   }

   private static final class Lst {
      private final int count;
      private final int dw;
      private final int pc;
      private final int s;
      private final int start;

      public Lst(int var1, int var2, int var3, int var4, int var5) {
         this.s = var1;
         this.start = var2;
         this.count = var3;
         this.dw = var4;
         this.pc = var5;
      }

      public final int getCount() {
         return this.count;
      }

      public final int getDw() {
         return this.dw;
      }

      public final int getPc() {
         return this.pc;
      }

      public final int getS() {
         return this.s;
      }

      public final int getStart() {
         return this.start;
      }
   }

   private static final class Msg {
      private final ByteBuffer b;
      private final int[] seg;

      public Msg(byte[] var1) {
         Intrinsics.checkNotNullParameter(var1, "raw");
         super();
         ByteBuffer var5 = ByteBuffer.wrap(var1).order(ByteOrder.LITTLE_ENDIAN);
         Intrinsics.checkNotNullExpressionValue(var5, "order(...)");
         this.b = var5;
         int var4 = this.b.getInt(0) + 1;
         this.seg = new int[var4];
         int var3 = ((var4 + 1) * 4 + 7) / 8;

         for (int var2 = 0; var2 < var4; var2++) {
            this.seg[var2] = var3;
            var3 += this.b.getInt(var2 * 4 + 4);
         }
      }

      public final ByteBuffer getB() {
         return this.b;
      }

      public final int[] getSeg() {
         return this.seg;
      }

      public final long w(int var1, int var2) {
         return this.b.getLong((this.seg[var1] + var2) * 8);
      }
   }

   private static final class Ref {
      private final int at;
      private final long p;
      private final int s;

      public Ref(int var1, long var2, int var4) {
         this.s = var1;
         this.p = var2;
         this.at = var4;
      }

      public final int getAt() {
         return this.at;
      }

      public final long getP() {
         return this.p;
      }

      public final int getS() {
         return this.s;
      }
   }

   public static final class State {
      private final String code;
      private final double maxLat;
      private final double maxLon;
      private final double minLat;
      private final double minLon;
      private final String name;
      private final List<int[]> rows;

      public State(String var1, String var2, double var3, double var5, double var7, double var9, List<int[]> var11) {
         Intrinsics.checkNotNullParameter(var1, "code");
         Intrinsics.checkNotNullParameter(var2, "name");
         Intrinsics.checkNotNullParameter(var11, "rows");
         super();
         this.code = var1;
         this.name = var2;
         this.minLat = var3;
         this.minLon = var5;
         this.maxLat = var7;
         this.maxLon = var9;
         this.rows = var11;
      }

      // $VF: synthetic method
      public State(String var1, String var2, double var3, double var5, double var7, double var9, List var11, int var12, DefaultConstructorMarker var13) {
         if ((var12 & 64) != 0) {
            var11 = CollectionsKt.emptyList();
         }

         this(var1, var2, var3, var5, var7, var9, var11);
      }

      public final String getCode() {
         return this.code;
      }

      public final double getMaxLat() {
         return this.maxLat;
      }

      public final double getMaxLon() {
         return this.maxLon;
      }

      public final double getMinLat() {
         return this.minLat;
      }

      public final double getMinLon() {
         return this.minLon;
      }

      public final String getName() {
         return this.name;
      }

      public final List<int[]> getRows() {
         return this.rows;
      }
   }

   public static final class W {
      private final long id;
      private final String kind;
      private final int lanes;
      private final double[] lat;
      private final double[] lon;
      private final String name;
      private final boolean oneway;

      public W(long var1, String var3, String var4, int var5, boolean var6, double[] var7, double[] var8) {
         Intrinsics.checkNotNullParameter(var3, "name");
         Intrinsics.checkNotNullParameter(var4, "kind");
         Intrinsics.checkNotNullParameter(var7, "lat");
         Intrinsics.checkNotNullParameter(var8, "lon");
         super();
         this.id = var1;
         this.name = var3;
         this.kind = var4;
         this.lanes = var5;
         this.oneway = var6;
         this.lat = var7;
         this.lon = var8;
      }

      public final long getId() {
         return this.id;
      }

      public final String getKind() {
         return this.kind;
      }

      public final int getLanes() {
         return this.lanes;
      }

      public final double[] getLat() {
         return this.lat;
      }

      public final double[] getLon() {
         return this.lon;
      }

      public final String getName() {
         return this.name;
      }

      public final boolean getOneway() {
         return this.oneway;
      }
   }
}
