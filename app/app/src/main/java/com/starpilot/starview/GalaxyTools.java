package com.starpilot.starview;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.json.JSONObject;

public final class GalaxyTools {
   private static final List<GalaxyTools.Tool> ALWAYS = CollectionsKt.listOf(
      new GalaxyTools.Tool[]{new GalaxyTools.Tool("Overview", "/"), new GalaxyTools.Tool("Recordings", "/recordings")}
   );
   private static final List<GalaxyTools.Tool> DEFAULT;
   public static final GalaxyTools INSTANCE = new GalaxyTools();
   private static final Set<String> PLACED_ELSEWHERE = SetsKt.setOf(new String[]{"/theme_maker", "/cameras", "/vehicle", "/device-preferences", "/system"});
   private static final String PREFS = "galaxy_tools";

   // $VF: synthetic method
   public static Unit $r8$lambda$AWg_MBgMdc0B4BMpYE901xgPq0Q(Context var0, JSONObject var1, String var2) {
      return refresh$lambda$6(var0, var1, var2);
   }

   static {
      GalaxyTools.Tool var0 = new GalaxyTools.Tool("Android Auto", "/android-auto");
      GalaxyTools.Tool var6 = new GalaxyTools.Tool("Bluetooth", "/bluetooth");
      GalaxyTools.Tool var2 = new GalaxyTools.Tool("Galaxy Access & Install", "/galaxy");
      GalaxyTools.Tool var3 = new GalaxyTools.Tool("Logs & Diagnostics", "/logs");
      GalaxyTools.Tool var1 = new GalaxyTools.Tool("Model Manager", "/manage_models");
      GalaxyTools.Tool var4 = new GalaxyTools.Tool("Model Laboratory", "/model_laboratory");
      GalaxyTools.Tool var5 = new GalaxyTools.Tool("Navigation & Maps", "/navigation");
      DEFAULT = CollectionsKt.listOf(
         new GalaxyTools.Tool[]{var0, var6, var2, var3, var1, var4, var5, new GalaxyTools.Tool("Plots & Analysis", "/tuning/plots")}
      );
   }

   private GalaxyTools() {
   }

   private final void refresh(Context var1) {
      Galaxy7 var2 = Galaxy7.Companion.current();
      if (var2 != null) {
         var2.get("/data/catalog.json", new GalaxyTools$$ExternalSyntheticLambda0(var1.getApplicationContext()));
      }
   }

   private static final Unit refresh$lambda$6(Context var0, JSONObject var1, String var2) {
      if (var1 != null) {
         GalaxyTools var3 = INSTANCE;
         var2 = var1.toString();
         Intrinsics.checkNotNullExpressionValue(var2, "toString(...)");
         if (var3.parse(var2) != null) {
            var0.getSharedPreferences("galaxy_tools", 0).edit().putString("catalog", var1.toString()).apply();
         }
      }

      return Unit.INSTANCE;
   }

   public final Set<String> getPLACED_ELSEWHERE() {
      return PLACED_ELSEWHERE;
   }

   public final int icon(String var1) {
      Intrinsics.checkNotNullParameter(var1, "path");
      int var2;
      if (Intrinsics.areEqual(var1, "/")) {
         var2 = R.drawable.ic_info;
      } else if (StringsKt.contains$default(var1, "recording", false, 2, null)) {
         var2 = R.drawable.ic_files;
      } else if (StringsKt.contains$default(var1, "bluetooth", false, 2, null)) {
         var2 = R.drawable.ic_link;
      } else if (StringsKt.contains$default(var1, "model", false, 2, null)) {
         var2 = R.drawable.ic_dev;
      } else if (StringsKt.contains$default(var1, "navigation", false, 2, null)) {
         var2 = R.drawable.ic_pin;
      } else if (StringsKt.contains$default(var1, "plots", false, 2, null) || StringsKt.contains$default(var1, "tuning", false, 2, null)) {
         var2 = R.drawable.ic_steering;
      } else if (StringsKt.contains$default(var1, "logs", false, 2, null)) {
         var2 = R.drawable.ic_terminal;
      } else if (StringsKt.contains$default(var1, "galaxy", false, 2, null)) {
         var2 = R.drawable.ic_network;
      } else if (StringsKt.contains$default(var1, "android", false, 2, null)) {
         var2 = R.drawable.ic_car;
      } else {
         var2 = R.drawable.ic_tools;
      }

      return var2;
   }

   public final List<GalaxyTools.Tool> list(Context var1) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      SharedPreferences var3 = var1.getSharedPreferences("galaxy_tools", 0);
      List var2 = null;
      String var5 = var3.getString("catalog", null);
      if (var5 != null) {
         var2 = INSTANCE.parse(var5);
      }

      this.refresh(var1);
      Collection var4 = ALWAYS;
      if (var2 == null) {
         var2 = DEFAULT;
      }

      return CollectionsKt.plus(var4, var2);
   }

   public final List<GalaxyTools.Tool> parse(String param1) {
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
      // 001: ldc_w "text"
      // 004: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
      // 007: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 00a: astore 5
      // 00c: aload 0
      // 00d: checkcast com/starpilot/starview/GalaxyTools
      // 010: astore 5
      // 012: bipush 0
      // 013: istore 3
      // 014: new org/json/JSONObject
      // 017: astore 6
      // 019: aload 6
      // 01b: aload 1
      // 01c: invokespecial org/json/JSONObject.<init> (Ljava/lang/String;)V
      // 01f: aload 6
      // 021: ldc_w "tools"
      // 024: invokevirtual org/json/JSONObject.getJSONArray (Ljava/lang/String;)Lorg/json/JSONArray;
      // 027: astore 1
      // 028: bipush 0
      // 029: aload 1
      // 02a: invokevirtual org/json/JSONArray.length ()I
      // 02d: invokestatic kotlin/ranges/RangesKt.until (II)Lkotlin/ranges/IntRange;
      // 030: checkcast java/lang/Iterable
      // 033: astore 6
      // 035: new java/util/ArrayList
      // 038: astore 7
      // 03a: aload 7
      // 03c: aload 6
      // 03e: bipush 10
      // 040: invokestatic kotlin/collections/CollectionsKt.collectionSizeOrDefault (Ljava/lang/Iterable;I)I
      // 043: invokespecial java/util/ArrayList.<init> (I)V
      // 046: aload 7
      // 048: checkcast java/util/Collection
      // 04b: astore 7
      // 04d: aload 6
      // 04f: invokeinterface java/lang/Iterable.iterator ()Ljava/util/Iterator; 1
      // 054: astore 6
      // 056: aload 6
      // 058: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 05d: ifeq 077
      // 060: aload 7
      // 062: aload 1
      // 063: aload 6
      // 065: checkcast kotlin/collections/IntIterator
      // 068: invokevirtual kotlin/collections/IntIterator.nextInt ()I
      // 06b: invokevirtual org/json/JSONArray.getJSONObject (I)Lorg/json/JSONObject;
      // 06e: invokeinterface java/util/Collection.add (Ljava/lang/Object;)Z 2
      // 073: pop
      // 074: goto 056
      // 077: aload 7
      // 079: checkcast java/util/List
      // 07c: astore 1
      // 07d: aload 1
      // 07e: checkcast java/lang/Iterable
      // 081: astore 1
      // 082: new java/util/ArrayList
      // 085: astore 6
      // 087: aload 6
      // 089: invokespecial java/util/ArrayList.<init> ()V
      // 08c: aload 6
      // 08e: checkcast java/util/Collection
      // 091: astore 6
      // 093: aload 1
      // 094: invokeinterface java/lang/Iterable.iterator ()Ljava/util/Iterator; 1
      // 099: astore 7
      // 09b: aload 5
      // 09d: astore 1
      // 09e: aload 7
      // 0a0: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 0a5: istore 4
      // 0a7: iload 4
      // 0a9: ifeq 113
      // 0ac: aload 7
      // 0ae: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 0b3: astore 5
      // 0b5: aload 5
      // 0b7: checkcast org/json/JSONObject
      // 0ba: astore 8
      // 0bc: aload 8
      // 0be: ldc "path"
      // 0c0: invokevirtual org/json/JSONObject.optString (Ljava/lang/String;)Ljava/lang/String;
      // 0c3: astore 9
      // 0c5: aload 9
      // 0c7: ldc_w "optString(...)"
      // 0ca: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 0cd: aload 9
      // 0cf: ldc "/"
      // 0d1: bipush 0
      // 0d2: bipush 2
      // 0d3: aconst_null
      // 0d4: invokestatic kotlin/text/StringsKt.startsWith$default (Ljava/lang/String;Ljava/lang/String;ZILjava/lang/Object;)Z
      // 0d7: ifeq 0fc
      // 0da: aload 8
      // 0dc: ldc_w "name"
      // 0df: invokevirtual org/json/JSONObject.optString (Ljava/lang/String;)Ljava/lang/String;
      // 0e2: astore 8
      // 0e4: aload 8
      // 0e6: ldc_w "optString(...)"
      // 0e9: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 0ec: aload 8
      // 0ee: checkcast java/lang/CharSequence
      // 0f1: invokestatic kotlin/text/StringsKt.isBlank (Ljava/lang/CharSequence;)Z
      // 0f4: ifne 0fc
      // 0f7: bipush 1
      // 0f8: istore 2
      // 0f9: goto 0fe
      // 0fc: bipush 0
      // 0fd: istore 2
      // 0fe: iload 2
      // 0ff: ifeq 10c
      // 102: aload 6
      // 104: aload 5
      // 106: invokeinterface java/util/Collection.add (Ljava/lang/Object;)Z 2
      // 10b: pop
      // 10c: goto 09e
      // 10f: astore 1
      // 110: goto 1b6
      // 113: aload 6
      // 115: checkcast java/util/List
      // 118: astore 1
      // 119: aload 1
      // 11a: checkcast java/lang/Iterable
      // 11d: astore 5
      // 11f: new java/util/ArrayList
      // 122: astore 1
      // 123: aload 1
      // 124: aload 5
      // 126: bipush 10
      // 128: invokestatic kotlin/collections/CollectionsKt.collectionSizeOrDefault (Ljava/lang/Iterable;I)I
      // 12b: invokespecial java/util/ArrayList.<init> (I)V
      // 12e: aload 1
      // 12f: checkcast java/util/Collection
      // 132: astore 1
      // 133: aload 5
      // 135: invokeinterface java/lang/Iterable.iterator ()Ljava/util/Iterator; 1
      // 13a: astore 5
      // 13c: aload 5
      // 13e: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 143: ifeq 18f
      // 146: aload 5
      // 148: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 14d: checkcast org/json/JSONObject
      // 150: astore 8
      // 152: new com/starpilot/starview/GalaxyTools$Tool
      // 155: astore 6
      // 157: aload 8
      // 159: ldc_w "name"
      // 15c: invokevirtual org/json/JSONObject.optString (Ljava/lang/String;)Ljava/lang/String;
      // 15f: astore 7
      // 161: aload 7
      // 163: ldc_w "optString(...)"
      // 166: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 169: aload 8
      // 16b: ldc "path"
      // 16d: invokevirtual org/json/JSONObject.optString (Ljava/lang/String;)Ljava/lang/String;
      // 170: astore 8
      // 172: aload 8
      // 174: ldc_w "optString(...)"
      // 177: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 17a: aload 6
      // 17c: aload 7
      // 17e: aload 8
      // 180: invokespecial com/starpilot/starview/GalaxyTools$Tool.<init> (Ljava/lang/String;Ljava/lang/String;)V
      // 183: aload 1
      // 184: aload 6
      // 186: invokeinterface java/util/Collection.add (Ljava/lang/Object;)Z 2
      // 18b: pop
      // 18c: goto 13c
      // 18f: aload 1
      // 190: checkcast java/util/List
      // 193: astore 1
      // 194: aload 1
      // 195: checkcast java/lang/Iterable
      // 198: astore 1
      // 199: new com/starpilot/starview/GalaxyTools$parse$lambda$4$$inlined$sortedBy$1
      // 19c: astore 5
      // 19e: aload 5
      // 1a0: invokespecial com/starpilot/starview/GalaxyTools$parse$lambda$4$$inlined$sortedBy$1.<init> ()V
      // 1a3: aload 1
      // 1a4: aload 5
      // 1a6: checkcast java/util/Comparator
      // 1a9: invokestatic kotlin/collections/CollectionsKt.sortedWith (Ljava/lang/Iterable;Ljava/util/Comparator;)Ljava/util/List;
      // 1ac: astore 1
      // 1ad: aload 1
      // 1ae: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 1b1: astore 1
      // 1b2: goto 1c3
      // 1b5: astore 1
      // 1b6: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 1b9: astore 5
      // 1bb: aload 1
      // 1bc: invokestatic kotlin/ResultKt.createFailure (Ljava/lang/Throwable;)Ljava/lang/Object;
      // 1bf: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 1c2: astore 1
      // 1c3: aload 1
      // 1c4: invokestatic kotlin/Result.isFailure-impl (Ljava/lang/Object;)Z
      // 1c7: ifeq 1cf
      // 1ca: aconst_null
      // 1cb: astore 1
      // 1cc: goto 1cf
      // 1cf: aload 1
      // 1d0: checkcast java/util/List
      // 1d3: areturn
   }

   public static final class Tool {
      private final String name;
      private final String path;

      public Tool(String var1, String var2) {
         Intrinsics.checkNotNullParameter(var1, "name");
         Intrinsics.checkNotNullParameter(var2, "path");
         super();
         this.name = var1;
         this.path = var2;
      }

      public final String getName() {
         return this.name;
      }

      public final String getPath() {
         return this.path;
      }
   }
}
