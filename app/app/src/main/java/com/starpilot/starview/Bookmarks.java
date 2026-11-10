package com.starpilot.starview;

import android.content.Context;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.json.JSONArray;
import org.json.JSONObject;

public final class Bookmarks {
   public static final Bookmarks INSTANCE = new Bookmarks();

   // $VF: synthetic method
   public static Unit $r8$lambda$Va_WGby_BytsAaZFB9tQ777CR_g/* $VF was: $r8$lambda$Va_WGby_BytsAaZFB9tQ777CR_g*/(JSONObject var0) {
      return export$lambda$10(var0);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$nWXi6L8GXgfj_KwIyYAfvKhCBzs(File var0) {
      return list$lambda$1(var0);
   }

   private Bookmarks() {
   }

   private static final Unit export$lambda$10(JSONObject var0) {
      Intrinsics.checkNotNullParameter(var0, "j");
      var0.put("exported", true);
      return Unit.INSTANCE;
   }

   private static final boolean list$lambda$1(File var0) {
      return var0.isDirectory();
   }

   private final void write(File var1, JSONObject var2) {
      File var3 = new File(var1, "bookmark.json.tmp");
      String var4 = var2.toString(2);
      Intrinsics.checkNotNullExpressionValue(var4, "toString(...)");
      FilesKt.writeText$default(var3, var4, null, 2, null);
      var3.renameTo(new File(var1, "bookmark.json"));
   }

   public final Bookmarks.Item create(Context var1, JSONObject var2) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      Intrinsics.checkNotNullParameter(var2, "snap");
      long var4 = System.currentTimeMillis();
      String var7 = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date(var4));
      File var6 = new File(this.dir(var1), var7);

      for (int var3 = 2; var6.exists(); var3++) {
         var6 = new File(this.dir(var1), var7 + "_" + var3);
      }

      String var8 = var6.getName();
      var6.mkdirs();
      var2.put("id", var8);
      var2.put("at_ms", var4);
      var2.put("at", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(new Date(var4)));
      this.write(var6, var2);
      Intrinsics.checkNotNull(var8);
      return new Bookmarks.Item(var8, var6, var2);
   }

   public final void delete(Bookmarks.Item var1) {
      Intrinsics.checkNotNullParameter(var1, "item");
      FilesKt.deleteRecursively(var1.getDir());
   }

   public final File dir(Context var1) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      File var2 = new File(var1.getFilesDir(), "bookmarks");
      var2.mkdirs();
      return var2;
   }

   public final Pair<String, Integer> export(Context param1, boolean param2) {
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
      // 001: ldc "ctx"
      // 003: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
      // 006: aload 0
      // 007: aload 1
      // 008: invokevirtual com/starpilot/starview/Bookmarks.list (Landroid/content/Context;)Ljava/util/List;
      // 00b: checkcast java/lang/Iterable
      // 00e: astore 6
      // 010: new java/util/ArrayList
      // 013: astore 5
      // 015: aload 5
      // 017: invokespecial java/util/ArrayList.<init> ()V
      // 01a: aload 5
      // 01c: checkcast java/util/Collection
      // 01f: astore 5
      // 021: aload 6
      // 023: invokeinterface java/lang/Iterable.iterator ()Ljava/util/Iterator; 1
      // 028: astore 7
      // 02a: aload 7
      // 02c: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 031: ifeq 070
      // 034: aload 7
      // 036: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 03b: astore 8
      // 03d: aload 8
      // 03f: checkcast com/starpilot/starview/Bookmarks$Item
      // 042: astore 6
      // 044: iload 2
      // 045: ifeq 05d
      // 048: aload 6
      // 04a: invokevirtual com/starpilot/starview/Bookmarks$Item.getJson ()Lorg/json/JSONObject;
      // 04d: ldc "exported"
      // 04f: invokevirtual org/json/JSONObject.optBoolean (Ljava/lang/String;)Z
      // 052: ifne 058
      // 055: goto 05d
      // 058: bipush 0
      // 059: istore 3
      // 05a: goto 05f
      // 05d: bipush 1
      // 05e: istore 3
      // 05f: iload 3
      // 060: ifeq 02a
      // 063: aload 5
      // 065: aload 8
      // 067: invokeinterface java/util/Collection.add (Ljava/lang/Object;)Z 2
      // 06c: pop
      // 06d: goto 02a
      // 070: aload 5
      // 072: checkcast java/util/List
      // 075: astore 12
      // 077: aload 12
      // 079: invokeinterface java/util/List.isEmpty ()Z 1
      // 07e: ifeq 09c
      // 081: iload 2
      // 082: ifeq 08c
      // 085: ldc_w "No new bookmarks since the last export."
      // 088: astore 1
      // 089: goto 090
      // 08c: ldc_w "No bookmarks yet."
      // 08f: astore 1
      // 090: aload 1
      // 091: bipush 0
      // 092: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 095: invokestatic kotlin/TuplesKt.to (Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;
      // 098: astore 1
      // 099: goto 30f
      // 09c: new java/text/SimpleDateFormat
      // 09f: astore 5
      // 0a1: aload 5
      // 0a3: ldc_w "yyyy-MM-dd_HH-mm"
      // 0a6: getstatic java/util/Locale.US Ljava/util/Locale;
      // 0a9: invokespecial java/text/SimpleDateFormat.<init> (Ljava/lang/String;Ljava/util/Locale;)V
      // 0ac: new java/util/Date
      // 0af: astore 6
      // 0b1: aload 6
      // 0b3: invokespecial java/util/Date.<init> ()V
      // 0b6: aload 5
      // 0b8: aload 6
      // 0ba: invokevirtual java/text/SimpleDateFormat.format (Ljava/util/Date;)Ljava/lang/String;
      // 0bd: astore 6
      // 0bf: new java/lang/StringBuilder
      // 0c2: astore 5
      // 0c4: aload 5
      // 0c6: invokespecial java/lang/StringBuilder.<init> ()V
      // 0c9: aload 5
      // 0cb: ldc_w "bookmarks_"
      // 0ce: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 0d1: aload 6
      // 0d3: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 0d6: ldc_w ".zip"
      // 0d9: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 0dc: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 0df: astore 6
      // 0e1: new android/content/ContentValues
      // 0e4: astore 5
      // 0e6: aload 5
      // 0e8: invokespecial android/content/ContentValues.<init> ()V
      // 0eb: aload 5
      // 0ed: ldc_w "_display_name"
      // 0f0: aload 6
      // 0f2: invokevirtual android/content/ContentValues.put (Ljava/lang/String;Ljava/lang/String;)V
      // 0f5: aload 5
      // 0f7: ldc_w "mime_type"
      // 0fa: ldc_w "application/zip"
      // 0fd: invokevirtual android/content/ContentValues.put (Ljava/lang/String;Ljava/lang/String;)V
      // 100: aload 5
      // 102: ldc_w "relative_path"
      // 105: ldc_w "Download/StarView/Bookmarks"
      // 108: invokevirtual android/content/ContentValues.put (Ljava/lang/String;Ljava/lang/String;)V
      // 10b: aload 1
      // 10c: invokevirtual android/content/Context.getContentResolver ()Landroid/content/ContentResolver;
      // 10f: getstatic android/provider/MediaStore$Downloads.EXTERNAL_CONTENT_URI Landroid/net/Uri;
      // 112: aload 5
      // 114: invokevirtual android/content/ContentResolver.insert (Landroid/net/Uri;Landroid/content/ContentValues;)Landroid/net/Uri;
      // 117: astore 8
      // 119: aload 8
      // 11b: ifnull 33a
      // 11e: aload 1
      // 11f: invokevirtual android/content/Context.getContentResolver ()Landroid/content/ContentResolver;
      // 122: aload 8
      // 124: invokevirtual android/content/ContentResolver.openOutputStream (Landroid/net/Uri;)Ljava/io/OutputStream;
      // 127: astore 1
      // 128: aload 1
      // 129: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNull (Ljava/lang/Object;)V
      // 12c: aload 1
      // 12d: checkcast java/io/Closeable
      // 130: astore 10
      // 132: aload 10
      // 134: checkcast java/io/OutputStream
      // 137: astore 6
      // 139: new java/util/zip/ZipOutputStream
      // 13c: astore 1
      // 13d: aload 1
      // 13e: aload 6
      // 140: invokespecial java/util/zip/ZipOutputStream.<init> (Ljava/io/OutputStream;)V
      // 143: aload 1
      // 144: checkcast java/io/Closeable
      // 147: astore 11
      // 149: aload 5
      // 14b: astore 7
      // 14d: aload 8
      // 14f: astore 7
      // 151: aload 6
      // 153: astore 7
      // 155: aload 11
      // 157: checkcast java/util/zip/ZipOutputStream
      // 15a: astore 13
      // 15c: aload 5
      // 15e: astore 7
      // 160: aload 8
      // 162: astore 7
      // 164: aload 6
      // 166: astore 7
      // 168: aload 12
      // 16a: invokeinterface java/util/List.iterator ()Ljava/util/Iterator; 1
      // 16f: astore 9
      // 171: aload 8
      // 173: astore 1
      // 174: aload 5
      // 176: astore 7
      // 178: aload 1
      // 179: astore 7
      // 17b: aload 6
      // 17d: astore 7
      // 17f: aload 9
      // 181: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 186: ifeq 2bb
      // 189: aload 5
      // 18b: astore 7
      // 18d: aload 1
      // 18e: astore 7
      // 190: aload 6
      // 192: astore 7
      // 194: aload 9
      // 196: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 19b: checkcast com/starpilot/starview/Bookmarks$Item
      // 19e: astore 14
      // 1a0: aload 5
      // 1a2: astore 7
      // 1a4: aload 1
      // 1a5: astore 7
      // 1a7: aload 6
      // 1a9: astore 7
      // 1ab: aload 14
      // 1ad: invokevirtual com/starpilot/starview/Bookmarks$Item.getDir ()Ljava/io/File;
      // 1b0: invokevirtual java/io/File.listFiles ()[Ljava/io/File;
      // 1b3: astore 7
      // 1b5: aload 7
      // 1b7: astore 8
      // 1b9: aload 7
      // 1bb: ifnonnull 1cb
      // 1be: bipush 0
      // 1bf: anewarray 90
      // 1c2: astore 8
      // 1c4: goto 1cb
      // 1c7: astore 1
      // 1c8: goto 31b
      // 1cb: aload 5
      // 1cd: astore 7
      // 1cf: aload 1
      // 1d0: astore 7
      // 1d2: aload 6
      // 1d4: astore 7
      // 1d6: aload 8
      // 1d8: arraylength
      // 1d9: istore 4
      // 1db: bipush 0
      // 1dc: istore 3
      // 1dd: iload 3
      // 1de: iload 4
      // 1e0: if_icmpge 2b8
      // 1e3: aload 8
      // 1e5: iload 3
      // 1e6: aaload
      // 1e7: astore 15
      // 1e9: aload 5
      // 1eb: astore 7
      // 1ed: aload 1
      // 1ee: astore 7
      // 1f0: aload 6
      // 1f2: astore 7
      // 1f4: aload 15
      // 1f6: invokevirtual java/io/File.getName ()Ljava/lang/String;
      // 1f9: astore 16
      // 1fb: aload 5
      // 1fd: astore 7
      // 1ff: aload 1
      // 200: astore 7
      // 202: aload 6
      // 204: astore 7
      // 206: aload 16
      // 208: ldc_w "getName(...)"
      // 20b: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 20e: aload 16
      // 210: ldc_w ".tmp"
      // 213: bipush 0
      // 214: bipush 2
      // 215: aconst_null
      // 216: invokestatic kotlin/text/StringsKt.endsWith$default (Ljava/lang/String;Ljava/lang/String;ZILjava/lang/Object;)Z
      // 219: ifne 2ae
      // 21c: new java/util/zip/ZipEntry
      // 21f: astore 7
      // 221: aload 14
      // 223: invokevirtual com/starpilot/starview/Bookmarks$Item.getId ()Ljava/lang/String;
      // 226: astore 18
      // 228: aload 15
      // 22a: invokevirtual java/io/File.getName ()Ljava/lang/String;
      // 22d: astore 17
      // 22f: new java/lang/StringBuilder
      // 232: astore 16
      // 234: aload 16
      // 236: invokespecial java/lang/StringBuilder.<init> ()V
      // 239: aload 7
      // 23b: aload 16
      // 23d: aload 18
      // 23f: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 242: ldc_w "/"
      // 245: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 248: aload 17
      // 24a: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 24d: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 250: invokespecial java/util/zip/ZipEntry.<init> (Ljava/lang/String;)V
      // 253: aload 13
      // 255: aload 7
      // 257: invokevirtual java/util/zip/ZipOutputStream.putNextEntry (Ljava/util/zip/ZipEntry;)V
      // 25a: new java/io/FileInputStream
      // 25d: astore 7
      // 25f: aload 15
      // 261: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNull (Ljava/lang/Object;)V
      // 264: aload 7
      // 266: aload 15
      // 268: invokespecial java/io/FileInputStream.<init> (Ljava/io/File;)V
      // 26b: aload 7
      // 26d: checkcast java/io/Closeable
      // 270: astore 7
      // 272: aload 7
      // 274: checkcast java/io/FileInputStream
      // 277: checkcast java/io/InputStream
      // 27a: astore 15
      // 27c: aload 13
      // 27e: checkcast java/io/OutputStream
      // 281: astore 16
      // 283: aload 15
      // 285: aload 16
      // 287: bipush 0
      // 288: bipush 2
      // 289: aconst_null
      // 28a: invokestatic kotlin/io/ByteStreamsKt.copyTo$default (Ljava/io/InputStream;Ljava/io/OutputStream;IILjava/lang/Object;)J
      // 28d: pop2
      // 28e: aload 7
      // 290: aconst_null
      // 291: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 294: aload 13
      // 296: invokevirtual java/util/zip/ZipOutputStream.closeEntry ()V
      // 299: goto 2ae
      // 29c: astore 1
      // 29d: goto 2a1
      // 2a0: astore 1
      // 2a1: aload 1
      // 2a2: athrow
      // 2a3: astore 5
      // 2a5: aload 7
      // 2a7: aload 1
      // 2a8: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 2ab: aload 5
      // 2ad: athrow
      // 2ae: iinc 3 1
      // 2b1: goto 1dd
      // 2b4: astore 1
      // 2b5: goto 31b
      // 2b8: goto 174
      // 2bb: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 2be: astore 1
      // 2bf: aload 11
      // 2c1: aconst_null
      // 2c2: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 2c5: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 2c8: astore 1
      // 2c9: aload 10
      // 2cb: aconst_null
      // 2cc: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 2cf: aload 12
      // 2d1: invokeinterface java/util/List.iterator ()Ljava/util/Iterator; 1
      // 2d6: astore 5
      // 2d8: aload 5
      // 2da: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 2df: ifeq 300
      // 2e2: aload 5
      // 2e4: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 2e9: checkcast com/starpilot/starview/Bookmarks$Item
      // 2ec: astore 1
      // 2ed: new com/starpilot/starview/Bookmarks$$ExternalSyntheticLambda1
      // 2f0: dup
      // 2f1: invokespecial com/starpilot/starview/Bookmarks$$ExternalSyntheticLambda1.<init> ()V
      // 2f4: astore 6
      // 2f6: aload 0
      // 2f7: aload 1
      // 2f8: aload 6
      // 2fa: invokevirtual com/starpilot/starview/Bookmarks.update (Lcom/starpilot/starview/Bookmarks$Item;Lkotlin/jvm/functions/Function1;)V
      // 2fd: goto 2d8
      // 300: aconst_null
      // 301: aload 12
      // 303: invokeinterface java/util/List.size ()I 1
      // 308: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 30b: invokestatic kotlin/TuplesKt.to (Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;
      // 30e: astore 1
      // 30f: goto 384
      // 312: astore 1
      // 313: goto 329
      // 316: astore 1
      // 317: goto 31b
      // 31a: astore 1
      // 31b: aload 1
      // 31c: athrow
      // 31d: astore 5
      // 31f: aload 11
      // 321: aload 1
      // 322: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 325: aload 5
      // 327: athrow
      // 328: astore 1
      // 329: goto 32d
      // 32c: astore 1
      // 32d: aload 1
      // 32e: athrow
      // 32f: astore 5
      // 331: aload 10
      // 333: aload 1
      // 334: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 337: aload 5
      // 339: athrow
      // 33a: new java/lang/IllegalStateException
      // 33d: astore 5
      // 33f: new java/lang/StringBuilder
      // 342: astore 1
      // 343: aload 1
      // 344: invokespecial java/lang/StringBuilder.<init> ()V
      // 347: aload 5
      // 349: aload 1
      // 34a: ldc_w "couldn't create "
      // 34d: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 350: aload 6
      // 352: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 355: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 358: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
      // 35b: aload 5
      // 35d: athrow
      // 35e: astore 1
      // 35f: goto 363
      // 362: astore 1
      // 363: aload 1
      // 364: invokevirtual java/lang/Exception.getMessage ()Ljava/lang/String;
      // 367: astore 1
      // 368: new java/lang/StringBuilder
      // 36b: dup
      // 36c: invokespecial java/lang/StringBuilder.<init> ()V
      // 36f: ldc_w "Couldn't export: "
      // 372: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 375: aload 1
      // 376: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 379: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 37c: bipush 0
      // 37d: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 380: invokestatic kotlin/TuplesKt.to (Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;
      // 383: astore 1
      // 384: aload 1
      // 385: areturn
   }

   public final List<Bookmarks.Item> list(Context var1) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      File[] var4 = this.dir(var1).listFiles(new Bookmarks$$ExternalSyntheticLambda0());
      int var2 = 0;
      File[] var7 = var4;
      if (var4 == null) {
         var7 = new File[0];
      }

      var var9 = new ArrayList();

      for (int var3 = var7.length; var2 < var3; var2++) {
         File var6 = var7[var2];
         Bookmarks var5 = INSTANCE;
         Intrinsics.checkNotNull(var6);
         Bookmarks.Item var10 = var5.load(var6);
         if (var10 != null) {
            var9.add(var10);
         }
      }

      List var8 = var9;
      return CollectionsKt.sortedWith(var8, new Comparator() {
         @Override
         public final int compare(T var1, T var2x) {
            return ComparisonsKt.compareValues(((Bookmarks.Item)var2x).getAtMs(), ((Bookmarks.Item)var1).getAtMs());
         }
      });
   }

   public final Bookmarks.Item load(File var1) {
      Intrinsics.checkNotNullParameter(var1, "d");
      Object var2 = null;

      Bookmarks.Item var3;
      try {
         String var5 = var1.getName();
         Intrinsics.checkNotNullExpressionValue(var5, "getName(...)");
         File var4 = new File(var1, "bookmark.json");
         JSONObject var6 = new JSONObject(FilesKt.readText$default(var4, null, 1, null));
         var3 = new Bookmarks.Item(var5, var1, var6);
      } catch (Exception var7) {
         return (Bookmarks.Item)var2;
      }

      return var3;
   }

   public final JSONArray tail(File param1, int param2) {
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
      // 001: ldc_w "f"
      // 004: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
      // 007: new org/json/JSONArray
      // 00a: dup
      // 00b: invokespecial org/json/JSONArray.<init> ()V
      // 00e: astore 8
      // 010: new java/io/RandomAccessFile
      // 013: astore 7
      // 015: aload 7
      // 017: aload 1
      // 018: ldc_w "r"
      // 01b: invokespecial java/io/RandomAccessFile.<init> (Ljava/io/File;Ljava/lang/String;)V
      // 01e: aload 7
      // 020: checkcast java/io/Closeable
      // 023: astore 9
      // 025: aload 9
      // 027: checkcast java/io/RandomAccessFile
      // 02a: astore 7
      // 02c: aload 7
      // 02e: invokevirtual java/io/RandomAccessFile.length ()J
      // 031: lstore 5
      // 033: lconst_0
      // 034: lload 5
      // 036: ldc2_w 64000
      // 039: lsub
      // 03a: invokestatic java/lang/Math.max (JJ)J
      // 03d: lstore 3
      // 03e: aload 7
      // 040: lload 3
      // 041: invokevirtual java/io/RandomAccessFile.seek (J)V
      // 044: lload 5
      // 046: lload 3
      // 047: lsub
      // 048: l2i
      // 049: newarray 8
      // 04b: astore 10
      // 04d: aload 7
      // 04f: aload 10
      // 051: invokevirtual java/io/RandomAccessFile.readFully ([B)V
      // 054: new java/lang/String
      // 057: astore 1
      // 058: aload 1
      // 059: aload 10
      // 05b: getstatic kotlin/text/Charsets.UTF_8 Ljava/nio/charset/Charset;
      // 05e: invokespecial java/lang/String.<init> ([BLjava/nio/charset/Charset;)V
      // 061: aload 1
      // 062: checkcast java/lang/CharSequence
      // 065: bipush 1
      // 066: anewarray 456
      // 069: dup
      // 06a: bipush 0
      // 06b: ldc_w "\n"
      // 06e: aastore
      // 06f: bipush 0
      // 070: bipush 0
      // 071: bipush 6
      // 073: aconst_null
      // 074: invokestatic kotlin/text/StringsKt.split$default (Ljava/lang/CharSequence;[Ljava/lang/String;ZIILjava/lang/Object;)Ljava/util/List;
      // 077: checkcast java/lang/Iterable
      // 07a: astore 10
      // 07c: new java/util/ArrayList
      // 07f: astore 1
      // 080: aload 1
      // 081: invokespecial java/util/ArrayList.<init> ()V
      // 084: aload 1
      // 085: checkcast java/util/Collection
      // 088: astore 1
      // 089: aload 10
      // 08b: invokeinterface java/lang/Iterable.iterator ()Ljava/util/Iterator; 1
      // 090: astore 10
      // 092: aload 10
      // 094: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 099: ifeq 0bf
      // 09c: aload 10
      // 09e: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 0a3: astore 11
      // 0a5: aload 11
      // 0a7: checkcast java/lang/String
      // 0aa: checkcast java/lang/CharSequence
      // 0ad: invokestatic kotlin/text/StringsKt.isBlank (Ljava/lang/CharSequence;)Z
      // 0b0: ifne 0bc
      // 0b3: aload 1
      // 0b4: aload 11
      // 0b6: invokeinterface java/util/Collection.add (Ljava/lang/Object;)Z 2
      // 0bb: pop
      // 0bc: goto 092
      // 0bf: aload 1
      // 0c0: checkcast java/util/List
      // 0c3: astore 1
      // 0c4: lload 3
      // 0c5: lconst_0
      // 0c6: lcmp
      // 0c7: ifle 0d6
      // 0ca: aload 1
      // 0cb: checkcast java/lang/Iterable
      // 0ce: bipush 1
      // 0cf: invokestatic kotlin/collections/CollectionsKt.drop (Ljava/lang/Iterable;I)Ljava/util/List;
      // 0d2: astore 1
      // 0d3: goto 0d6
      // 0d6: aload 1
      // 0d7: iload 2
      // 0d8: invokestatic kotlin/collections/CollectionsKt.takeLast (Ljava/util/List;I)Ljava/util/List;
      // 0db: checkcast java/lang/Iterable
      // 0de: invokeinterface java/lang/Iterable.iterator ()Ljava/util/Iterator; 1
      // 0e3: astore 10
      // 0e5: aload 7
      // 0e7: astore 1
      // 0e8: aload 10
      // 0ea: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 0ef: ifeq 105
      // 0f2: aload 8
      // 0f4: aload 10
      // 0f6: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 0fb: checkcast java/lang/String
      // 0fe: invokevirtual org/json/JSONArray.put (Ljava/lang/Object;)Lorg/json/JSONArray;
      // 101: pop
      // 102: goto 0e8
      // 105: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 108: astore 1
      // 109: aload 9
      // 10b: aconst_null
      // 10c: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 10f: goto 129
      // 112: astore 1
      // 113: goto 117
      // 116: astore 1
      // 117: aload 1
      // 118: athrow
      // 119: astore 7
      // 11b: aload 9
      // 11d: aload 1
      // 11e: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 121: aload 7
      // 123: athrow
      // 124: astore 1
      // 125: goto 129
      // 128: astore 1
      // 129: aload 8
      // 12b: areturn
   }

   public final void update(Bookmarks.Item param1, Function1<? super JSONObject, Unit> param2) {
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
      // 00: aload 1
      // 01: ldc "item"
      // 03: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
      // 06: aload 2
      // 07: ldc_w "change"
      // 0a: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
      // 0d: aload 0
      // 0e: monitorenter
      // 0f: getstatic com/starpilot/starview/Bookmarks.INSTANCE Lcom/starpilot/starview/Bookmarks;
      // 12: aload 1
      // 13: invokevirtual com/starpilot/starview/Bookmarks$Item.getDir ()Ljava/io/File;
      // 16: invokevirtual com/starpilot/starview/Bookmarks.load (Ljava/io/File;)Lcom/starpilot/starview/Bookmarks$Item;
      // 19: astore 3
      // 1a: aload 3
      // 1b: ifnull 2c
      // 1e: aload 3
      // 1f: invokevirtual com/starpilot/starview/Bookmarks$Item.getJson ()Lorg/json/JSONObject;
      // 22: astore 4
      // 24: aload 4
      // 26: astore 3
      // 27: aload 4
      // 29: ifnonnull 31
      // 2c: aload 1
      // 2d: invokevirtual com/starpilot/starview/Bookmarks$Item.getJson ()Lorg/json/JSONObject;
      // 30: astore 3
      // 31: aload 2
      // 32: aload 3
      // 33: invokeinterface kotlin/jvm/functions/Function1.invoke (Ljava/lang/Object;)Ljava/lang/Object; 2
      // 38: pop
      // 39: getstatic com/starpilot/starview/Bookmarks.INSTANCE Lcom/starpilot/starview/Bookmarks;
      // 3c: aload 1
      // 3d: invokevirtual com/starpilot/starview/Bookmarks$Item.getDir ()Ljava/io/File;
      // 40: aload 3
      // 41: invokespecial com/starpilot/starview/Bookmarks.write (Ljava/io/File;Lorg/json/JSONObject;)V
      // 44: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 47: astore 1
      // 48: aload 0
      // 49: monitorexit
      // 4a: return
      // 4b: astore 1
      // 4c: aload 0
      // 4d: monitorexit
      // 4e: aload 1
      // 4f: athrow
   }

   public static final class Item {
      private final File dir;
      private final String id;
      private final JSONObject json;

      public Item(String var1, File var2, JSONObject var3) {
         Intrinsics.checkNotNullParameter(var1, "id");
         Intrinsics.checkNotNullParameter(var2, "dir");
         Intrinsics.checkNotNullParameter(var3, "json");
         super();
         this.id = var1;
         this.dir = var2;
         this.json = var3;
      }

      public final long getAtMs() {
         return this.json.optLong("at_ms");
      }

      public final File getDir() {
         return this.dir;
      }

      public final boolean getHasPicture() {
         return new File(this.dir, "picture.jpg").exists();
      }

      public final boolean getHasVoice() {
         File var2 = new File(this.dir, "voice.wav");
         boolean var1;
         if (var2.exists() && var2.length() > 44L) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      public final String getId() {
         return this.id;
      }

      public final JSONObject getJson() {
         return this.json;
      }

      public final String getNote() {
         return this.json.optString("note");
      }

      public final String getPlace() {
         return this.json.optString("place");
      }

      public final String getTitle() {
         JSONObject var1 = this.json.optJSONObject("lane");
         if (var1 != null) {
            String var2 = var1.optString("label");
            if (var2 != null) {
               return var2;
            }
         }

         return "";
      }

      public final String getTranscript() {
         return this.json.optString("voice_text");
      }
   }
}
