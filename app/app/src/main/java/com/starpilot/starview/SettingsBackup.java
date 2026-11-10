package com.starpilot.starview;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore.Downloads;
import android.util.Log;
import java.io.Closeable;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.Result.Companion;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.io.ByteStreamsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import org.json.JSONArray;
import org.json.JSONObject;

public final class SettingsBackup {
   public static final String AUTO_NAME = "starview-settings-auto.json";
   public static final String FOLDER = "Download/StarView";
   private static final String FORMAT = "starview-settings";
   public static final SettingsBackup INSTANCE = new SettingsBackup();
   private static final String META = "starview_backup_meta";
   private static final Map<String, Set<String>> SKIP = MapsKt.mapOf(
      new Pair[]{
         TuplesKt.to("starview", SetsKt.setOf(new String[]{"term_key", "paired_dongle", "paired_hosts"})),
         TuplesKt.to("starview_ntrip", SetsKt.setOf(new String[]{"pass", "b_pass"}))
      }
   );
   private static long lastAutoMs;
   private static final Handler main = new Handler(Looper.getMainLooper());
   private static Runnable pending;

   // $VF: synthetic method
   public static void $r8$lambda$Ct4jniwW35aU3lHtSC59v_c_aZs(Function0 var0, DialogInterface var1) {
      restoreFrom$lambda$28(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$EDovBsJ__qbbom_ZJdgNxHj3PNc/* $VF was: $r8$lambda$EDovBsJ__qbbom_ZJdgNxHj3PNc*/(Context var0) {
      auto$lambda$18(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$ET2LwschJeWh2Zr03UK6rhA3vm8(Function0 var0, DialogInterface var1, int var2) {
      restoreFrom$lambda$27(var0, var1, var2);
   }

   // $VF: synthetic method
   public static void $r8$lambda$RFYnvnbbd8opLIuxhtuyBmcqV5s(Function0 var0, DialogInterface var1) {
      restoreFrom$lambda$24(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$gprPKvrfoNl3VPfiCD0oWk7HZUc(Activity var0, JSONObject var1, Function0 var2, DialogInterface var3, int var4) {
      restoreFrom$lambda$26(var0, var1, var2, var3, var4);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$hQ0A1uEt1k5RQk8sztp9DDmqJz4() {
      return restoreFrom$lambda$23();
   }

   // $VF: synthetic method
   public static void $r8$lambda$kgaKAaLa6gelb0DmGp_zx4c9Tpk/* $VF was: $r8$lambda$kgaKAaLa6gelb0DmGp_zx4c9Tpk*/(Context var0) {
      auto$lambda$17(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$oeDuPbM9g0TjXmkblzpT1WMTZcQ(Function0 var0, DialogInterface var1) {
      restoreFrom$lambda$26$lambda$25(var0, var1);
   }

   private SettingsBackup() {
   }

   private static final void auto$lambda$17(Context var0) {
      pending = null;
      SettingsBackup var1 = INSTANCE;
      Intrinsics.checkNotNull(var0);
      var1.auto(var0);
   }

   private static final void auto$lambda$18(Context var0) {
      try {
         SettingsBackup var2 = INSTANCE;
         Intrinsics.checkNotNull(var0);
         JSONObject var3 = var2.export(var0);
         int var1 = var3.getJSONObject("prefs").toString().hashCode();
         SharedPreferences var8 = var0.getSharedPreferences("starview_backup_meta", 0);
         if (var8.getInt("sig", 0) == var1 && var8.contains("sig")) {
            return;
         }

         INSTANCE.save(var0, "starview-settings-auto.json", var3);
         SimpleDateFormat var4 = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
         Date var5 = new Date();
         String var10 = var4.format(var5);
         SettingsBackup var9 = INSTANCE;
         StringBuilder var6 = new StringBuilder();
         var9.save(var0, var6.append("starview-settings-").append(var10).append(".json").toString(), var3);
         INSTANCE.prune(var0);
         var8.edit().putInt("sig", var1).putLong("at", System.currentTimeMillis()).apply();
      } catch (Exception var7) {
         Log.w("StarView/Backup", "auto backup: " + var7);
      }
   }

   private final Uri downloadsUri(long var1) {
      Uri var3 = ContentUris.withAppendedId(Downloads.EXTERNAL_CONTENT_URI, var1);
      Intrinsics.checkNotNullExpressionValue(var3, "withAppendedId(...)");
      return var3;
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private final Uri ownRow(Context var1, String var2) {
      SharedPreferences var3 = var1.getSharedPreferences("starview_backup_meta", 0);
      String var4 = "row_" + var2;
      Object var5 = null;
      String var102 = var3.getString(var4, null);
      if (var102 != null) {
         Uri var6 = Uri.parse(var102);
         SettingsBackup var103 = INSTANCE;

         label504: {
            try {
               Companion var105 = Result.Companion;
               var106 = var1.getContentResolver().query(var6, new String[]{"_id"}, null, null, null);
            } catch (Throwable var97) {
               Companion var110 = Result.Companion;
               var104 = KtCompat.resultOf(ResultKt.createFailure(var97));
               break label504;
            }

            Boolean var108;
            if (var106 != null) {
               try {
                  var112 = (Closeable & Cursor)var106;
               } catch (Throwable var96) {
                  Companion var111 = Result.Companion;
                  var104 = KtCompat.resultOf(ResultKt.createFailure(var96));
                  break label504;
               }

               try {
                  var108 = ((Cursor)var112).moveToFirst();
               } catch (Throwable var95) {
                  Throwable var107 = var95;

                  try {
                     throw var107;
                  } finally {
                     try {
                        CloseableKt.closeFinally(var112, var107);
                     } catch (Throwable var92) {
                        Companion var113 = Result.Companion;
                        var104 = KtCompat.resultOf(ResultKt.createFailure(var92));
                        break label504;
                     }
                  }
               }

               try {
                  CloseableKt.closeFinally(var112, null);
               } catch (Throwable var94) {
                  Companion var114 = Result.Companion;
                  var104 = KtCompat.resultOf(ResultKt.createFailure(var94));
                  break label504;
               }
            } else {
               var108 = null;
            }

            label484:
            try {
               var104 = KtCompat.resultOf(var108);
            } catch (Throwable var91) {
               Companion var115 = Result.Companion;
               var104 = KtCompat.resultOf(ResultKt.createFailure(var91));
               break label484;
            }
         }

         var4 = (String)var104;
         if (KtCompat.isFailure(var104)) {
            var4 = null;
         }

         if (Intrinsics.areEqual(var4, true)) {
            return var6;
         }
      }

      Cursor var100 = var1.getContentResolver()
         .query(Downloads.EXTERNAL_CONTENT_URI, new String[]{"_id"}, "_display_name=? AND relative_path=?", new String[]{var2, "Download/StarView/"}, null);
      Uri var98 = (Uri)var5;
      if (var100 != null) {
         var var101 = (Closeable & Cursor)var100;

         label474: {
            try {
               Cursor var99 = (Cursor)var101;
               if (var99.moveToFirst()) {
                  var98 = INSTANCE.downloadsUri(var99.getLong(0));
                  break label474;
               }
            } catch (Throwable var90) {
               Throwable var109 = var90;

               try {
                  throw var109;
               } finally {
                  CloseableKt.closeFinally(var101, var90);
               }
            }

            var98 = null;
         }

         CloseableKt.closeFinally(var101, null);
      }

      return var98;
   }

   private final List<String> prefFiles(Context var1) {
      File[] var4 = new File(var1.getApplicationInfo().dataDir, "shared_prefs").listFiles();
      if (var4 != null) {
         var var6 = new ArrayList();

         for (File var7 : var4) {
            String var5 = var7.getName();
            Intrinsics.checkNotNullExpressionValue(var5, "getName(...)");
            if (StringsKt.endsWith$default(var5, ".xml", false, 2, null)) {
               var6.add(var7);
            }
         }

         List var8 = var6;
         Iterable var14 = var8;
         var var9 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var14, 10));
         Iterator var19 = var14.iterator();

         while (var19.hasNext()) {
            String var15 = ((File)var19.next()).getName();
            Intrinsics.checkNotNullExpressionValue(var15, "getName(...)");
            var9.add(StringsKt.removeSuffix(var15, ".xml"));
         }

         List var10 = var9;
         Iterable var16 = var10;
         var var11 = new ArrayList();

         for (Object var17 : var16) {
            if (!Intrinsics.areEqual((String)var17, "starview_backup_meta")) {
               var11.add(var17);
            }
         }

         List var12 = var11;
         List var18 = CollectionsKt.sorted(var12);
         if (var18 != null) {
            return var18;
         }
      }

      return CollectionsKt.emptyList();
   }

   private final void prune(Context param1) {
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
      // 000: new kotlin/text/Regex
      // 003: dup
      // 004: ldc_w "starview-settings-(\\d{4}-\\d{2}-\\d{2})( \\(\\d+\\))?\\.json"
      // 007: invokespecial kotlin/text/Regex.<init> (Ljava/lang/String;)V
      // 00a: astore 4
      // 00c: new java/util/ArrayList
      // 00f: dup
      // 010: invokespecial java/util/ArrayList.<init> ()V
      // 013: astore 2
      // 014: aload 1
      // 015: invokevirtual android/content/Context.getContentResolver ()Landroid/content/ContentResolver;
      // 018: getstatic android/provider/MediaStore$Downloads.EXTERNAL_CONTENT_URI Landroid/net/Uri;
      // 01b: bipush 2
      // 01c: anewarray 128
      // 01f: dup
      // 020: bipush 0
      // 021: ldc_w "_id"
      // 024: aastore
      // 025: dup
      // 026: bipush 1
      // 027: ldc_w "_display_name"
      // 02a: aastore
      // 02b: ldc_w "relative_path=?"
      // 02e: bipush 1
      // 02f: anewarray 128
      // 032: dup
      // 033: bipush 0
      // 034: ldc_w "Download/StarView/"
      // 037: aastore
      // 038: aconst_null
      // 039: invokevirtual android/content/ContentResolver.query (Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
      // 03c: astore 3
      // 03d: aload 3
      // 03e: ifnull 0ba
      // 041: aload 3
      // 042: checkcast java/io/Closeable
      // 045: astore 3
      // 046: aload 3
      // 047: checkcast android/database/Cursor
      // 04a: astore 5
      // 04c: aload 5
      // 04e: invokeinterface android/database/Cursor.moveToNext ()Z 1
      // 053: ifeq 0a2
      // 056: aload 5
      // 058: bipush 1
      // 059: invokeinterface android/database/Cursor.getString (I)Ljava/lang/String; 2
      // 05e: astore 6
      // 060: aload 6
      // 062: ifnonnull 068
      // 065: goto 04c
      // 068: aload 4
      // 06a: aload 6
      // 06c: checkcast java/lang/CharSequence
      // 06f: invokevirtual kotlin/text/Regex.matchEntire (Ljava/lang/CharSequence;)Lkotlin/text/MatchResult;
      // 072: astore 6
      // 074: aload 6
      // 076: ifnull 09f
      // 079: aload 2
      // 07a: aload 6
      // 07c: invokeinterface kotlin/text/MatchResult.getGroupValues ()Ljava/util/List; 1
      // 081: bipush 1
      // 082: invokeinterface java/util/List.get (I)Ljava/lang/Object; 2
      // 087: getstatic com/starpilot/starview/SettingsBackup.INSTANCE Lcom/starpilot/starview/SettingsBackup;
      // 08a: aload 5
      // 08c: bipush 0
      // 08d: invokeinterface android/database/Cursor.getLong (I)J 2
      // 092: invokespecial com/starpilot/starview/SettingsBackup.downloadsUri (J)Landroid/net/Uri;
      // 095: invokestatic kotlin/TuplesKt.to (Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;
      // 098: invokevirtual java/util/ArrayList.add (Ljava/lang/Object;)Z
      // 09b: pop
      // 09c: goto 04c
      // 09f: goto 04c
      // 0a2: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 0a5: astore 4
      // 0a7: aload 3
      // 0a8: aconst_null
      // 0a9: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 0ac: goto 0ba
      // 0af: astore 1
      // 0b0: aload 1
      // 0b1: athrow
      // 0b2: astore 2
      // 0b3: aload 3
      // 0b4: aload 1
      // 0b5: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 0b8: aload 2
      // 0b9: athrow
      // 0ba: aload 2
      // 0bb: checkcast java/lang/Iterable
      // 0be: astore 4
      // 0c0: new java/util/ArrayList
      // 0c3: dup
      // 0c4: aload 4
      // 0c6: bipush 10
      // 0c8: invokestatic kotlin/collections/CollectionsKt.collectionSizeOrDefault (Ljava/lang/Iterable;I)I
      // 0cb: invokespecial java/util/ArrayList.<init> (I)V
      // 0ce: checkcast java/util/Collection
      // 0d1: astore 3
      // 0d2: aload 4
      // 0d4: invokeinterface java/lang/Iterable.iterator ()Ljava/util/Iterator; 1
      // 0d9: astore 4
      // 0db: aload 4
      // 0dd: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 0e2: ifeq 0ff
      // 0e5: aload 3
      // 0e6: aload 4
      // 0e8: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 0ed: checkcast kotlin/Pair
      // 0f0: invokevirtual kotlin/Pair.getFirst ()Ljava/lang/Object;
      // 0f3: checkcast java/lang/String
      // 0f6: invokeinterface java/util/Collection.add (Ljava/lang/Object;)Z 2
      // 0fb: pop
      // 0fc: goto 0db
      // 0ff: aload 3
      // 100: checkcast java/util/List
      // 103: astore 3
      // 104: aload 3
      // 105: checkcast java/lang/Iterable
      // 108: invokestatic kotlin/collections/CollectionsKt.distinct (Ljava/lang/Iterable;)Ljava/util/List;
      // 10b: checkcast java/lang/Iterable
      // 10e: invokestatic kotlin/collections/CollectionsKt.sortedDescending (Ljava/lang/Iterable;)Ljava/util/List;
      // 111: checkcast java/lang/Iterable
      // 114: bipush 7
      // 116: invokestatic kotlin/collections/CollectionsKt.take (Ljava/lang/Iterable;I)Ljava/util/List;
      // 119: checkcast java/lang/Iterable
      // 11c: invokestatic kotlin/collections/CollectionsKt.toSet (Ljava/lang/Iterable;)Ljava/util/Set;
      // 11f: astore 3
      // 120: aload 2
      // 121: invokevirtual java/util/ArrayList.iterator ()Ljava/util/Iterator;
      // 124: astore 2
      // 125: aload 2
      // 126: ldc_w "iterator(...)"
      // 129: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 12c: aload 2
      // 12d: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 132: ifeq 19e
      // 135: aload 2
      // 136: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 13b: astore 4
      // 13d: aload 4
      // 13f: ldc_w "next(...)"
      // 142: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 145: aload 4
      // 147: checkcast kotlin/Pair
      // 14a: astore 4
      // 14c: aload 4
      // 14e: invokevirtual kotlin/Pair.component1 ()Ljava/lang/Object;
      // 151: checkcast java/lang/String
      // 154: astore 5
      // 156: aload 4
      // 158: invokevirtual kotlin/Pair.component2 ()Ljava/lang/Object;
      // 15b: checkcast android/net/Uri
      // 15e: astore 4
      // 160: aload 3
      // 161: aload 5
      // 163: invokeinterface java/util/Set.contains (Ljava/lang/Object;)Z 2
      // 168: ifne 12c
      // 16b: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 16e: astore 5
      // 170: aload 0
      // 171: checkcast com/starpilot/starview/SettingsBackup
      // 174: astore 5
      // 176: aload 1
      // 177: invokevirtual android/content/Context.getContentResolver ()Landroid/content/ContentResolver;
      // 17a: aload 4
      // 17c: aconst_null
      // 17d: aconst_null
      // 17e: invokevirtual android/content/ContentResolver.delete (Landroid/net/Uri;Ljava/lang/String;[Ljava/lang/String;)I
      // 181: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 184: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 187: pop
      // 188: goto 12c
      // 18b: astore 4
      // 18d: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 190: astore 5
      // 192: aload 4
      // 194: invokestatic kotlin/ResultKt.createFailure (Ljava/lang/Throwable;)Ljava/lang/Object;
      // 197: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 19a: pop
      // 19b: goto 12c
      // 19e: return
   }

   // $VF: synthetic method
   public static void restoreFrom$default(SettingsBackup var0, Activity var1, Uri var2, Function0 var3, int var4, Object var5) {
      if ((var4 & 4) != 0) {
         var3 = new SettingsBackup$$ExternalSyntheticLambda0();
      }

      var0.restoreFrom(var1, var2, (Function0<Unit>)var3);
   }

   private static final Unit restoreFrom$lambda$23() {
      return Unit.INSTANCE;
   }

   private static final void restoreFrom$lambda$24(Function0 var0, DialogInterface var1) {
      var0.invoke();
   }

   private static final void restoreFrom$lambda$26(Activity var0, JSONObject var1, Function0 var2, DialogInterface var3, int var4) {
      try {
         INSTANCE.import_((Context)var0, var1);
         INSTANCE.restart(var0);
      } catch (Exception var5) {
         Builder var6 = new Builder((Context)var0);
         String var7 = var5.getMessage();
         var6.setMessage("Restore failed, nothing was changed: " + var7)
            .setPositiveButton("OK", null)
            .setOnDismissListener(new SettingsBackup$$ExternalSyntheticLambda5(var2))
            .show();
      }
   }

   private static final void restoreFrom$lambda$26$lambda$25(Function0 var0, DialogInterface var1) {
      var0.invoke();
   }

   private static final void restoreFrom$lambda$27(Function0 var0, DialogInterface var1, int var2) {
      var0.invoke();
   }

   private static final void restoreFrom$lambda$28(Function0 var0, DialogInterface var1) {
      var0.invoke();
   }

   // $VF: synthetic method
   public static String save$default(SettingsBackup var0, Context var1, String var2, JSONObject var3, int var4, Object var5) {
      if ((var4 & 4) != 0) {
         var3 = var0.export(var1);
      }

      return var0.save(var1, var2, var3);
   }

   public final void auto(Context var1) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      var1 = var1.getApplicationContext();
      long var2 = System.currentTimeMillis();
      long var4 = lastAutoMs + '\uea60' - var2;
      if (var4 > 0L) {
         if (pending == null) {
            SettingsBackup$$ExternalSyntheticLambda6 var7 = new SettingsBackup$$ExternalSyntheticLambda6(var1);
            pending = var7;
            main.postDelayed(var7, var4);
         }
      } else {
         lastAutoMs = var2;
         new Thread(new SettingsBackup$$ExternalSyntheticLambda7(var1)).start();
      }
   }

   public final JSONObject export(Context var1) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      JSONObject var5 = new JSONObject();

      label82:
      for (String var6 : this.prefFiles(var1)) {
         Set var3 = SKIP.get(var6);
         Set var4 = var3;
         if (var3 == null) {
            var4 = SetsKt.emptySet();
         }

         JSONObject var7 = new JSONObject();
         Map var15 = var1.getSharedPreferences(var6, 0).getAll();
         Intrinsics.checkNotNullExpressionValue(var15, "getAll(...)");
         Iterator var8 = MapsKt.toSortedMap(var15).entrySet().iterator();

         while (true) {
            String var9;
            while (true) {
               if (!var8.hasNext()) {
                  if (var7.length() > 0) {
                     var5.put(var6, var7);
                  }
                  continue label82;
               }

               Entry var16 = (Entry)var8.next();
               var9 = (String)var16.getKey();
               Collection var10 = (Collection)var16.getValue();
               if (!var4.contains(var9) && var10 != null) {
                  if (var10 instanceof Boolean) {
                     var18 = new JSONObject().put("b", (Boolean)var10);
                     break;
                  }

                  if (var10 instanceof Integer) {
                     var18 = new JSONObject().put("i", ((Number)var10).intValue());
                     break;
                  }

                  if (var10 instanceof Long) {
                     var18 = new JSONObject().put("l", ((Number)var10).longValue());
                     break;
                  }

                  if (var10 instanceof Float) {
                     var18 = new JSONObject().put("f", ((Number)var10).floatValue());
                     break;
                  }

                  if (var10 instanceof String) {
                     var18 = new JSONObject().put("s", var10);
                     break;
                  }

                  if (var10 instanceof Set) {
                     JSONObject var17 = new JSONObject();
                     Iterable var11 = var10;
                     var10 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var11, 10));
                     Iterator var21 = var11.iterator();

                     while (var21.hasNext()) {
                        var10.add(String.valueOf(var21.next()));
                     }

                     List var20 = (List)var10;
                     var18 = var17.put("set", new JSONArray(var20));
                     break;
                  }
               }
            }

            var7.put(var9, var18);
         }
      }

      try {
         var13 = var1.getPackageManager().getPackageInfo(var1.getPackageName(), 0).versionName;
      } catch (Exception var12) {
         var13 = "?";
      }

      JSONObject var14 = new JSONObject()
         .put("format", "starview-settings")
         .put("version", 1)
         .put("app", var13)
         .put("created", new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US).format(new Date()))
         .put("prefs", var5);
      Intrinsics.checkNotNullExpressionValue(var14, "put(...)");
      return var14;
   }

   public final int import_(Context var1, JSONObject var2) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      Intrinsics.checkNotNullParameter(var2, "j");
      if (!Intrinsics.areEqual(var2.optString("format"), "starview-settings")) {
         throw new IllegalArgumentException("not a StarView settings backup".toString());
      } else {
         var2 = var2.getJSONObject("prefs");
         LinkedHashMap var8 = new LinkedHashMap();
         Iterator var7 = var2.keys();
         Intrinsics.checkNotNullExpressionValue(var7, "keys(...)");

         label139:
         while (var7.hasNext()) {
            String var9 = (String)var7.next();
            Intrinsics.checkNotNull(var9);
            int var3 = var9.length();
            boolean var4 = true;
            boolean var20;
            if (var3 > 0) {
               var20 = true;
            } else {
               var20 = false;
            }

            if (var20 && !Intrinsics.areEqual(var9, "starview_backup_meta") && !StringsKt.contains$default(var9, '/', false, 2, null)) {
               var20 = var4;
            } else {
               var20 = false;
            }

            if (!var20) {
               throw new IllegalArgumentException("bad settings file name".toString());
            }

            JSONObject var10 = var2.getJSONObject(var9);
            Set var5 = SKIP.get(var9);
            Set var6 = var5;
            if (var5 == null) {
               var6 = SetsKt.emptySet();
            }

            ArrayList var12 = new ArrayList();
            Iterator var11 = var10.keys();
            Intrinsics.checkNotNullExpressionValue(var11, "keys(...)");

            while (true) {
               String var13;
               while (true) {
                  if (!var11.hasNext()) {
                     var8.put(var9, var12);
                     continue label139;
                  }

                  var13 = (String)var11.next();
                  if (!var6.contains(var13)) {
                     JSONObject var23 = var10.getJSONObject(var13);
                     Intrinsics.checkNotNull(var13);
                     if (var23.has("b")) {
                        var5 = var23.getBoolean("b");
                        break;
                     }

                     if (var23.has("i")) {
                        var5 = var23.getInt("i");
                        break;
                     }

                     if (var23.has("l")) {
                        var5 = var23.getLong("l");
                        break;
                     }

                     if (var23.has("f")) {
                        var5 = (float)var23.getDouble("f");
                        break;
                     }

                     if (var23.has("s")) {
                        var5 = var23.getString("s");
                        break;
                     }

                     if (var23.has("set")) {
                        JSONArray var14 = var23.getJSONArray("set");
                        Iterable var15 = RangesKt.until(0, var14.length());
                        var var24 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var15, 10));
                        Iterator var37 = var15.iterator();

                        while (var37.hasNext()) {
                           var24.add(var14.getString(((IntIterator)var37).nextInt()));
                        }

                        List var25 = var24;
                        var5 = CollectionsKt.toSet(var25);
                        break;
                     }
                  }
               }

               Intrinsics.checkNotNull(var5);
               var12.add(new SettingsBackup.Put(var13, var5));
            }
         }

         int var22 = 0;

         for (Entry var17 : var8.entrySet()) {
            String var32 = (String)var17.getKey();
            List var30 = (List)var17.getValue();
            Set var27 = SKIP.get(var32);
            Set var18 = var27;
            if (var27 == null) {
               var18 = SetsKt.emptySet();
            }

            SharedPreferences var33 = var1.getSharedPreferences(var32, 0);
            Editor var28 = var33.edit();

            for (String var36 : var33.getAll().keySet()) {
               if (!var18.contains(var36)) {
                  var28.remove(var36);
               }
            }

            for (SettingsBackup.Put var31 : var30) {
               Object var19 = var31.getV();
               if (var19 instanceof Boolean) {
                  var28.putBoolean(var31.getKey(), (Boolean)var19);
               } else if (var19 instanceof Integer) {
                  var28.putInt(var31.getKey(), ((Number)var19).intValue());
               } else if (var19 instanceof Long) {
                  var28.putLong(var31.getKey(), ((Number)var19).longValue());
               } else if (var19 instanceof Float) {
                  var28.putFloat(var31.getKey(), ((Number)var19).floatValue());
               } else if (var19 instanceof String) {
                  var28.putString(var31.getKey(), (String)var19);
               } else if (var19 instanceof Set) {
                  var28.putStringSet(var31.getKey(), (Set)var19);
               }

               var22++;
            }

            var28.commit();
         }

         var1.getSharedPreferences("starview_backup_meta", 0).edit().remove("sig").commit();
         return var22;
      }
   }

   public final long lastAuto(Context var1) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      return var1.getSharedPreferences("starview_backup_meta", 0).getLong("at", 0L);
   }

   public final boolean looksFresh(Context var1) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      boolean var3 = false;
      boolean var2 = var3;
      if (var1.getSharedPreferences("starview_ui", 0).getAll().isEmpty()) {
         var2 = var3;
         if (var1.getSharedPreferences("starview_hud", 0).getAll().isEmpty()) {
            var2 = true;
         }
      }

      return var2;
   }

   public final String manual(Context var1) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      String var2 = new SimpleDateFormat("yyyy-MM-dd-HHmm", Locale.US).format(new Date());
      return save$default(this, var1, "starview-settings-" + var2 + ".json", null, 4, null);
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public final JSONObject read(Context var1, Uri var2) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      Intrinsics.checkNotNullParameter(var2, "uri");
      InputStream var10 = var1.getContentResolver().openInputStream(var2);
      if (var10 != null) {
         var var11 = var10;

         try {
            var12 = new String(ByteStreamsKt.readBytes(var11), Charsets.UTF_8);
         } catch (Throwable var9) {
            Throwable var3 = var9;

            try {
               throw var3;
            } finally {
               CloseableKt.closeFinally(var11, var9);
            }
         }

         CloseableKt.closeFinally(var11, null);
         return new JSONObject(var12);
      } else {
         throw new IllegalStateException("can't read the file");
      }
   }

   public final void restart(Activity var1) {
      Intrinsics.checkNotNullParameter(var1, "act");
      Intent var2 = new Intent((Context)var1, MainActivity.class).addFlags(268468224);
      Intrinsics.checkNotNullExpressionValue(var2, "addFlags(...)");
      var1.startActivity(var2);
      var1.finishAffinity();
      Runtime.getRuntime().exit(0);
   }

   public final void restoreFrom(Activity var1, Uri var2, Function0<Unit> var3) {
      Intrinsics.checkNotNullParameter(var1, "act");
      Intrinsics.checkNotNullParameter(var2, "uri");
      Intrinsics.checkNotNullParameter(var3, "onCancel");

      try {
         var8 = this.read((Context)var1, var2);
      } catch (Exception var7) {
         var8 = null;
      }

      if (var8 != null && Intrinsics.areEqual(var8.optString("format"), "starview-settings")) {
         Builder var5 = new Builder((Context)var1).setTitle("Restore settings?");
         String var6 = var8.optString("created", "?");
         String var4 = var8.optString("app", "?");
         var5.setMessage(
               "Backup from "
                  + var6
                  + " (StarView "
                  + var4
                  + "). Every tablet setting and the driving-screen layout are replaced, then StarView starts again. The comma pairing stays as it is."
            )
            .setPositiveButton("Restore", new SettingsBackup$$ExternalSyntheticLambda2(var1, var8, var3))
            .setNegativeButton("Cancel", new SettingsBackup$$ExternalSyntheticLambda3(var3))
            .setOnCancelListener(new SettingsBackup$$ExternalSyntheticLambda4(var3))
            .show();
      } else {
         new Builder((Context)var1)
            .setMessage("That file isn't a StarView settings backup.")
            .setPositiveButton("OK", null)
            .setOnDismissListener(new SettingsBackup$$ExternalSyntheticLambda1(var3))
            .show();
      }
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public final String save(Context var1, String var2, JSONObject var3) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      Intrinsics.checkNotNullParameter(var2, "fileName");
      Intrinsics.checkNotNullParameter(var3, "j");
      String var15 = var3.toString(1);
      Intrinsics.checkNotNullExpressionValue(var15, "toString(...)");
      byte[] var4 = var15.getBytes(Charsets.UTF_8);
      Intrinsics.checkNotNullExpressionValue(var4, "getBytes(...)");
      ContentResolver var5 = var1.getContentResolver();
      Uri var16 = this.ownRow(var1, var2);
      if (var16 == null) {
         Uri var7 = Downloads.EXTERNAL_CONTENT_URI;
         ContentValues var17 = new ContentValues();
         var17.put("_display_name", var2);
         var17.put("mime_type", "application/json");
         var17.put("relative_path", "Download/StarView");
         Unit var6 = Unit.INSTANCE;
         var16 = var5.insert(var7, var17);
         if (var16 == null) {
            throw new IllegalStateException("can't create " + var2);
         }
      }

      OutputStream var19 = var5.openOutputStream(var16, "wt");
      if (var19 != null) {
         var var20 = var19;

         try {
            var20.write(var4);
            Unit var18 = Unit.INSTANCE;
         } catch (Throwable var13) {
            Throwable var14 = var13;

            try {
               throw var14;
            } finally {
               CloseableKt.closeFinally(var20, var13);
            }
         }

         CloseableKt.closeFinally(var20, null);
         var1.getSharedPreferences("starview_backup_meta", 0).edit().putString("row_" + var2, var16.toString()).apply();
         return "Download/StarView/" + var2;
      } else {
         throw new IllegalStateException("can't write " + var2);
      }
   }

   public final String saveCopy(Context param1, File param2, String param3) {
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
      // 001: ldc_w "ctx"
      // 004: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
      // 007: aload 2
      // 008: ldc_w "src"
      // 00b: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
      // 00e: aload 3
      // 00f: ldc_w "name"
      // 012: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
      // 015: aload 2
      // 016: invokevirtual java/io/File.isFile ()Z
      // 019: ifeq 155
      // 01c: aload 1
      // 01d: invokevirtual android/content/Context.getContentResolver ()Landroid/content/ContentResolver;
      // 020: astore 6
      // 022: aload 0
      // 023: aload 1
      // 024: aload 3
      // 025: invokespecial com/starpilot/starview/SettingsBackup.ownRow (Landroid/content/Context;Ljava/lang/String;)Landroid/net/Uri;
      // 028: astore 5
      // 02a: aload 5
      // 02c: astore 4
      // 02e: aload 5
      // 030: ifnonnull 093
      // 033: getstatic android/provider/MediaStore$Downloads.EXTERNAL_CONTENT_URI Landroid/net/Uri;
      // 036: astore 4
      // 038: new android/content/ContentValues
      // 03b: dup
      // 03c: invokespecial android/content/ContentValues.<init> ()V
      // 03f: astore 5
      // 041: aload 5
      // 043: ldc_w "_display_name"
      // 046: aload 3
      // 047: invokevirtual android/content/ContentValues.put (Ljava/lang/String;Ljava/lang/String;)V
      // 04a: aload 5
      // 04c: ldc_w "mime_type"
      // 04f: ldc_w "text/plain"
      // 052: invokevirtual android/content/ContentValues.put (Ljava/lang/String;Ljava/lang/String;)V
      // 055: aload 5
      // 057: ldc_w "relative_path"
      // 05a: ldc "Download/StarView"
      // 05c: invokevirtual android/content/ContentValues.put (Ljava/lang/String;Ljava/lang/String;)V
      // 05f: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 062: astore 7
      // 064: aload 6
      // 066: aload 4
      // 068: aload 5
      // 06a: invokevirtual android/content/ContentResolver.insert (Landroid/net/Uri;Landroid/content/ContentValues;)Landroid/net/Uri;
      // 06d: astore 4
      // 06f: aload 4
      // 071: ifnull 077
      // 074: goto 093
      // 077: new java/lang/IllegalStateException
      // 07a: dup
      // 07b: new java/lang/StringBuilder
      // 07e: dup
      // 07f: invokespecial java/lang/StringBuilder.<init> ()V
      // 082: ldc_w "can't create "
      // 085: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 088: aload 3
      // 089: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 08c: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 08f: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
      // 092: athrow
      // 093: aload 6
      // 095: aload 4
      // 097: ldc_w "wt"
      // 09a: invokevirtual android/content/ContentResolver.openOutputStream (Landroid/net/Uri;Ljava/lang/String;)Ljava/io/OutputStream;
      // 09d: astore 5
      // 09f: aload 5
      // 0a1: ifnull 139
      // 0a4: aload 5
      // 0a6: checkcast java/io/Closeable
      // 0a9: astore 5
      // 0ab: aload 5
      // 0ad: checkcast java/io/OutputStream
      // 0b0: astore 6
      // 0b2: new java/io/FileInputStream
      // 0b5: astore 7
      // 0b7: aload 7
      // 0b9: aload 2
      // 0ba: invokespecial java/io/FileInputStream.<init> (Ljava/io/File;)V
      // 0bd: aload 7
      // 0bf: checkcast java/io/Closeable
      // 0c2: astore 2
      // 0c3: aload 2
      // 0c4: checkcast java/io/FileInputStream
      // 0c7: checkcast java/io/InputStream
      // 0ca: aload 6
      // 0cc: bipush 0
      // 0cd: bipush 2
      // 0ce: aconst_null
      // 0cf: invokestatic kotlin/io/ByteStreamsKt.copyTo$default (Ljava/io/InputStream;Ljava/io/OutputStream;IILjava/lang/Object;)J
      // 0d2: pop2
      // 0d3: aload 2
      // 0d4: aconst_null
      // 0d5: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 0d8: aload 5
      // 0da: aconst_null
      // 0db: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 0de: aload 1
      // 0df: ldc "starview_backup_meta"
      // 0e1: bipush 0
      // 0e2: invokevirtual android/content/Context.getSharedPreferences (Ljava/lang/String;I)Landroid/content/SharedPreferences;
      // 0e5: invokeinterface android/content/SharedPreferences.edit ()Landroid/content/SharedPreferences$Editor; 1
      // 0ea: new java/lang/StringBuilder
      // 0ed: dup
      // 0ee: invokespecial java/lang/StringBuilder.<init> ()V
      // 0f1: ldc_w "row_"
      // 0f4: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 0f7: aload 3
      // 0f8: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 0fb: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 0fe: aload 4
      // 100: invokevirtual android/net/Uri.toString ()Ljava/lang/String;
      // 103: invokeinterface android/content/SharedPreferences$Editor.putString (Ljava/lang/String;Ljava/lang/String;)Landroid/content/SharedPreferences$Editor; 3
      // 108: invokeinterface android/content/SharedPreferences$Editor.apply ()V 1
      // 10d: new java/lang/StringBuilder
      // 110: dup
      // 111: invokespecial java/lang/StringBuilder.<init> ()V
      // 114: ldc_w "Download/StarView/"
      // 117: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 11a: aload 3
      // 11b: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 11e: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 121: areturn
      // 122: astore 3
      // 123: aload 3
      // 124: athrow
      // 125: astore 1
      // 126: aload 2
      // 127: aload 3
      // 128: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 12b: aload 1
      // 12c: athrow
      // 12d: astore 1
      // 12e: aload 1
      // 12f: athrow
      // 130: astore 2
      // 131: aload 5
      // 133: aload 1
      // 134: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 137: aload 2
      // 138: athrow
      // 139: new java/lang/IllegalStateException
      // 13c: dup
      // 13d: new java/lang/StringBuilder
      // 140: dup
      // 141: invokespecial java/lang/StringBuilder.<init> ()V
      // 144: ldc_w "can't write "
      // 147: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 14a: aload 3
      // 14b: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 14e: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 151: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
      // 154: athrow
      // 155: aload 2
      // 156: invokevirtual java/io/File.getName ()Ljava/lang/String;
      // 159: astore 1
      // 15a: new java/lang/IllegalArgumentException
      // 15d: dup
      // 15e: new java/lang/StringBuilder
      // 161: dup
      // 162: invokespecial java/lang/StringBuilder.<init> ()V
      // 165: aload 1
      // 166: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 169: ldc_w " doesn't exist yet"
      // 16c: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 16f: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 172: invokevirtual java/lang/Object.toString ()Ljava/lang/String;
      // 175: invokespecial java/lang/IllegalArgumentException.<init> (Ljava/lang/String;)V
      // 178: athrow
   }

   private abstract static class Op {
      private Op() {
      }

      // $VF: synthetic method
      public Op(DefaultConstructorMarker var1) {
         this();
      }

      public abstract String getKey();
   }

   private static final class Put extends SettingsBackup.Op {
      private final String key;
      private final Object v;

      public Put(String var1, Object var2) {
         Intrinsics.checkNotNullParameter(var1, "key");
         Intrinsics.checkNotNullParameter(var2, "v");
         super(null);
         this.key = var1;
         this.v = var2;
      }

      @Override
      public String getKey() {
         return this.key;
      }

      public final Object getV() {
         return this.v;
      }
   }
}
