package com.starpilot.starview;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.Uri;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.collections.MapsKt;
import kotlin.io.TextStreamsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.ranges.RangesKt;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import org.json.JSONArray;
import org.json.JSONObject;

public final class RoadData {
   public static final String DEFAULT_BASE = "https://raw.githubusercontent.com/tsk-go/starview-data/main/hpms-2024/";
   public static final RoadData INSTANCE = new RoadData();
   private static final Map<Integer, String> STATES;
   private static volatile boolean busy;
   private static volatile int done;
   private static volatile String status = "";
   private static volatile boolean stop;
   private static volatile int total;

   // $VF: synthetic method
   public static void $r8$lambda$8UriXBxRzXWtmG61qy_9a6CFteg/* $VF was: $r8$lambda$8UriXBxRzXWtmG61qy_9a6CFteg*/(Context var0, Function0 var1) {
      download$lambda$10(var0, var1);
   }

   // $VF: synthetic method
   public static CharSequence $r8$lambda$Dd1i3u5wmjV8uzRPWl5aP_mXfHM(byte var0) {
      return sha256$lambda$2(var0);
   }

   static {
      Pair var42 = TuplesKt.to(1, "AL");
      Pair var48 = TuplesKt.to(2, "AK");
      Pair var20 = TuplesKt.to(4, "AZ");
      Pair var5 = TuplesKt.to(5, "AR");
      Pair var39 = TuplesKt.to(6, "CA");
      Pair var1 = TuplesKt.to(8, "CO");
      Pair var16 = TuplesKt.to(9, "CT");
      Pair var9 = TuplesKt.to(10, "DE");
      Pair var22 = TuplesKt.to(11, "DC");
      Pair var40 = TuplesKt.to(12, "FL");
      Pair var33 = TuplesKt.to(13, "GA");
      Pair var49 = TuplesKt.to(15, "HI");
      Pair var37 = TuplesKt.to(16, "ID");
      Pair var13 = TuplesKt.to(17, "IL");
      Pair var38 = TuplesKt.to(18, "IN");
      Pair var44 = TuplesKt.to(19, "IA");
      Pair var41 = TuplesKt.to(20, "KS");
      Pair var18 = TuplesKt.to(21, "KY");
      Pair var24 = TuplesKt.to(22, "LA");
      Pair var26 = TuplesKt.to(23, "ME");
      Pair var35 = TuplesKt.to(24, "MD");
      Pair var45 = TuplesKt.to(25, "MA");
      Pair var46 = TuplesKt.to(26, "MI");
      Pair var31 = TuplesKt.to(27, "MN");
      Pair var21 = TuplesKt.to(28, "MS");
      Pair var36 = TuplesKt.to(29, "MO");
      Pair var17 = TuplesKt.to(30, "MT");
      Pair var15 = TuplesKt.to(31, "NE");
      Pair var2 = TuplesKt.to(32, "NV");
      Pair var7 = TuplesKt.to(33, "NH");
      Pair var47 = TuplesKt.to(34, "NJ");
      Pair var28 = TuplesKt.to(35, "NM");
      Pair var19 = TuplesKt.to(36, "NY");
      Pair var30 = TuplesKt.to(37, "NC");
      Pair var0 = TuplesKt.to(38, "ND");
      Pair var43 = TuplesKt.to(39, "OH");
      Pair var29 = TuplesKt.to(40, "OK");
      Pair var25 = TuplesKt.to(41, "OR");
      Pair var3 = TuplesKt.to(42, "PA");
      Pair var23 = TuplesKt.to(44, "RI");
      Pair var6 = TuplesKt.to(45, "SC");
      Pair var14 = TuplesKt.to(46, "SD");
      Pair var34 = TuplesKt.to(47, "TN");
      Pair var32 = TuplesKt.to(48, "TX");
      Pair var50 = TuplesKt.to(49, "UT");
      Pair var10 = TuplesKt.to(50, "VT");
      Pair var11 = TuplesKt.to(51, "VA");
      Pair var27 = TuplesKt.to(53, "WA");
      Pair var8 = TuplesKt.to(54, "WV");
      Pair var12 = TuplesKt.to(55, "WI");
      Pair var4 = TuplesKt.to(56, "WY");
      STATES = MapsKt.mapOf(
         new Pair[]{
            var42,
            var48,
            var20,
            var5,
            var39,
            var1,
            var16,
            var9,
            var22,
            var40,
            var33,
            var49,
            var37,
            var13,
            var38,
            var44,
            var41,
            var18,
            var24,
            var26,
            var35,
            var45,
            var46,
            var31,
            var21,
            var36,
            var17,
            var15,
            var2,
            var7,
            var47,
            var28,
            var19,
            var30,
            var0,
            var43,
            var29,
            var25,
            var3,
            var23,
            var6,
            var14,
            var34,
            var32,
            var50,
            var10,
            var11,
            var27,
            var8,
            var12,
            var4,
            TuplesKt.to(72, "PR")
         }
      );
   }

   private RoadData() {
   }

   private static final void download$lambda$10(Context param0, Function0 param1) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      //
      // Bytecode:
      // 000: getstatic com/starpilot/starview/RoadData.INSTANCE Lcom/starpilot/starview/RoadData;
      // 003: aload 0
      // 004: invokevirtual com/starpilot/starview/RoadData.onWifi (Landroid/content/Context;)Z
      // 007: ifne 01b
      // 00a: ldc "Not on Wi-Fi - the road data only downloads on Wi-Fi."
      // 00c: putstatic com/starpilot/starview/RoadData.status Ljava/lang/String;
      // 00f: bipush 0
      // 010: putstatic com/starpilot/starview/RoadData.busy Z
      // 013: aload 1
      // 014: invokeinterface kotlin/jvm/functions/Function0.invoke ()Ljava/lang/Object; 1
      // 019: pop
      // 01a: return
      // 01b: getstatic com/starpilot/starview/RoadData.INSTANCE Lcom/starpilot/starview/RoadData;
      // 01e: aload 0
      // 01f: invokevirtual com/starpilot/starview/RoadData.manifest (Landroid/content/Context;)Ljava/util/List;
      // 022: astore 8
      // 024: getstatic com/starpilot/starview/Hpms.INSTANCE Lcom/starpilot/starview/Hpms;
      // 027: invokevirtual com/starpilot/starview/Hpms.getDir ()Ljava/io/File;
      // 02a: astore 5
      // 02c: aload 5
      // 02e: astore 6
      // 030: aload 5
      // 032: ifnonnull 04b
      // 035: new java/io/File
      // 038: astore 6
      // 03a: aload 6
      // 03c: aload 0
      // 03d: invokevirtual android/content/Context.getFilesDir ()Ljava/io/File;
      // 040: ldc "hpms"
      // 042: invokespecial java/io/File.<init> (Ljava/io/File;Ljava/lang/String;)V
      // 045: aload 6
      // 047: invokevirtual java/io/File.mkdirs ()Z
      // 04a: pop
      // 04b: aload 8
      // 04d: checkcast java/lang/Iterable
      // 050: astore 7
      // 052: new java/util/ArrayList
      // 055: astore 5
      // 057: aload 5
      // 059: invokespecial java/util/ArrayList.<init> ()V
      // 05c: aload 5
      // 05e: checkcast java/util/Collection
      // 061: astore 5
      // 063: aload 7
      // 065: invokeinterface java/lang/Iterable.iterator ()Ljava/util/Iterator; 1
      // 06a: astore 11
      // 06c: aload 11
      // 06e: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 073: istore 4
      // 075: bipush 1
      // 076: istore 3
      // 077: iload 4
      // 079: ifeq 0cd
      // 07c: aload 11
      // 07e: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 083: astore 10
      // 085: aload 10
      // 087: checkcast com/starpilot/starview/RoadData$Item
      // 08a: astore 7
      // 08c: new java/io/File
      // 08f: astore 9
      // 091: aload 9
      // 093: aload 6
      // 095: aload 7
      // 097: invokevirtual com/starpilot/starview/RoadData$Item.getFile ()Ljava/lang/String;
      // 09a: invokespecial java/io/File.<init> (Ljava/io/File;Ljava/lang/String;)V
      // 09d: iload 3
      // 09e: istore 2
      // 09f: aload 9
      // 0a1: invokevirtual java/io/File.exists ()Z
      // 0a4: ifeq 0bc
      // 0a7: aload 9
      // 0a9: invokevirtual java/io/File.length ()J
      // 0ac: aload 7
      // 0ae: invokevirtual com/starpilot/starview/RoadData$Item.getBytes ()J
      // 0b1: lcmp
      // 0b2: ifeq 0ba
      // 0b5: iload 3
      // 0b6: istore 2
      // 0b7: goto 0bc
      // 0ba: bipush 0
      // 0bb: istore 2
      // 0bc: iload 2
      // 0bd: ifeq 06c
      // 0c0: aload 5
      // 0c2: aload 10
      // 0c4: invokeinterface java/util/Collection.add (Ljava/lang/Object;)Z 2
      // 0c9: pop
      // 0ca: goto 06c
      // 0cd: aload 5
      // 0cf: checkcast java/util/List
      // 0d2: astore 5
      // 0d4: aload 5
      // 0d6: invokeinterface java/util/List.size ()I 1
      // 0db: putstatic com/starpilot/starview/RoadData.total I
      // 0de: aload 5
      // 0e0: invokeinterface java/util/List.isEmpty ()Z 1
      // 0e5: istore 4
      // 0e7: iload 4
      // 0e9: ifeq 11f
      // 0ec: aload 8
      // 0ee: invokeinterface java/util/List.size ()I 1
      // 0f3: istore 2
      // 0f4: new java/lang/StringBuilder
      // 0f7: astore 0
      // 0f8: aload 0
      // 0f9: invokespecial java/lang/StringBuilder.<init> ()V
      // 0fc: aload 0
      // 0fd: ldc_w "Up to date: "
      // 100: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 103: iload 2
      // 104: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
      // 107: ldc_w " states."
      // 10a: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 10d: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 110: putstatic com/starpilot/starview/RoadData.status Ljava/lang/String;
      // 113: bipush 0
      // 114: putstatic com/starpilot/starview/RoadData.busy Z
      // 117: aload 1
      // 118: invokeinterface kotlin/jvm/functions/Function0.invoke ()Ljava/lang/Object; 1
      // 11d: pop
      // 11e: return
      // 11f: aload 5
      // 121: invokeinterface java/util/List.iterator ()Ljava/util/Iterator; 1
      // 126: astore 5
      // 128: aload 5
      // 12a: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 12f: ifeq 392
      // 132: aload 5
      // 134: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 139: checkcast com/starpilot/starview/RoadData$Item
      // 13c: astore 9
      // 13e: getstatic com/starpilot/starview/RoadData.stop Z
      // 141: istore 4
      // 143: iload 4
      // 145: ifeq 15a
      // 148: ldc_w "Stopped."
      // 14b: putstatic com/starpilot/starview/RoadData.status Ljava/lang/String;
      // 14e: bipush 0
      // 14f: putstatic com/starpilot/starview/RoadData.busy Z
      // 152: aload 1
      // 153: invokeinterface kotlin/jvm/functions/Function0.invoke ()Ljava/lang/Object; 1
      // 158: pop
      // 159: return
      // 15a: getstatic com/starpilot/starview/RoadData.INSTANCE Lcom/starpilot/starview/RoadData;
      // 15d: aload 0
      // 15e: invokevirtual com/starpilot/starview/RoadData.onWifi (Landroid/content/Context;)Z
      // 161: ifne 176
      // 164: ldc_w "Wi-Fi lost - stopped (Download again to continue)."
      // 167: putstatic com/starpilot/starview/RoadData.status Ljava/lang/String;
      // 16a: bipush 0
      // 16b: putstatic com/starpilot/starview/RoadData.busy Z
      // 16e: aload 1
      // 16f: invokeinterface kotlin/jvm/functions/Function0.invoke ()Ljava/lang/Object; 1
      // 174: pop
      // 175: return
      // 176: aload 9
      // 178: invokevirtual com/starpilot/starview/RoadData$Item.getSt ()Ljava/lang/String;
      // 17b: astore 10
      // 17d: getstatic com/starpilot/starview/RoadData.INSTANCE Lcom/starpilot/starview/RoadData;
      // 180: aload 9
      // 182: invokevirtual com/starpilot/starview/RoadData$Item.getBytes ()J
      // 185: invokevirtual com/starpilot/starview/RoadData.mb (J)Ljava/lang/String;
      // 188: astore 7
      // 18a: getstatic com/starpilot/starview/RoadData.done I
      // 18d: istore 2
      // 18e: getstatic com/starpilot/starview/RoadData.total I
      // 191: istore 3
      // 192: new java/lang/StringBuilder
      // 195: astore 11
      // 197: aload 11
      // 199: invokespecial java/lang/StringBuilder.<init> ()V
      // 19c: aload 11
      // 19e: ldc_w "Downloading "
      // 1a1: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 1a4: aload 10
      // 1a6: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 1a9: ldc_w " ("
      // 1ac: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 1af: aload 7
      // 1b1: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 1b4: ldc_w ")…  "
      // 1b7: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 1ba: iload 2
      // 1bb: bipush 1
      // 1bc: iadd
      // 1bd: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
      // 1c0: ldc_w " of "
      // 1c3: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 1c6: iload 3
      // 1c7: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
      // 1ca: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 1cd: putstatic com/starpilot/starview/RoadData.status Ljava/lang/String;
      // 1d0: aload 1
      // 1d1: invokeinterface kotlin/jvm/functions/Function0.invoke ()Ljava/lang/Object; 1
      // 1d6: pop
      // 1d7: new java/io/File
      // 1da: astore 10
      // 1dc: aload 9
      // 1de: invokevirtual com/starpilot/starview/RoadData$Item.getFile ()Ljava/lang/String;
      // 1e1: astore 11
      // 1e3: new java/lang/StringBuilder
      // 1e6: astore 7
      // 1e8: aload 7
      // 1ea: invokespecial java/lang/StringBuilder.<init> ()V
      // 1ed: aload 10
      // 1ef: aload 6
      // 1f1: aload 7
      // 1f3: aload 11
      // 1f5: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 1f8: ldc_w ".part"
      // 1fb: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 1fe: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 201: invokespecial java/io/File.<init> (Ljava/io/File;Ljava/lang/String;)V
      // 204: getstatic com/starpilot/starview/RoadData.INSTANCE Lcom/starpilot/starview/RoadData;
      // 207: astore 12
      // 209: getstatic com/starpilot/starview/RoadData.INSTANCE Lcom/starpilot/starview/RoadData;
      // 20c: aload 0
      // 20d: invokevirtual com/starpilot/starview/RoadData.base (Landroid/content/Context;)Ljava/lang/String;
      // 210: astore 7
      // 212: aload 9
      // 214: invokevirtual com/starpilot/starview/RoadData$Item.getFile ()Ljava/lang/String;
      // 217: astore 11
      // 219: new java/lang/StringBuilder
      // 21c: astore 13
      // 21e: aload 13
      // 220: invokespecial java/lang/StringBuilder.<init> ()V
      // 223: aload 12
      // 225: aload 13
      // 227: aload 7
      // 229: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 22c: aload 11
      // 22e: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 231: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 234: invokespecial com/starpilot/starview/RoadData.get (Ljava/lang/String;)Ljava/net/HttpURLConnection;
      // 237: astore 7
      // 239: aload 7
      // 23b: invokevirtual java/net/HttpURLConnection.getResponseCode ()I
      // 23e: sipush 200
      // 241: if_icmpne 35d
      // 244: aload 7
      // 246: invokevirtual java/net/HttpURLConnection.getInputStream ()Ljava/io/InputStream;
      // 249: checkcast java/io/Closeable
      // 24c: astore 11
      // 24e: aload 11
      // 250: checkcast java/io/InputStream
      // 253: astore 13
      // 255: new java/io/FileOutputStream
      // 258: astore 7
      // 25a: aload 7
      // 25c: aload 10
      // 25e: invokespecial java/io/FileOutputStream.<init> (Ljava/io/File;)V
      // 261: aload 7
      // 263: checkcast java/io/Closeable
      // 266: astore 12
      // 268: aload 12
      // 26a: checkcast java/io/FileOutputStream
      // 26d: astore 7
      // 26f: ldc_w 65536
      // 272: newarray 8
      // 274: astore 14
      // 276: getstatic com/starpilot/starview/RoadData.stop Z
      // 279: ifne 297
      // 27c: aload 13
      // 27e: aload 14
      // 280: invokevirtual java/io/InputStream.read ([B)I
      // 283: istore 2
      // 284: iload 2
      // 285: iflt 294
      // 288: aload 7
      // 28a: aload 14
      // 28c: bipush 0
      // 28d: iload 2
      // 28e: invokevirtual java/io/FileOutputStream.write ([BII)V
      // 291: goto 276
      // 294: goto 297
      // 297: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 29a: astore 7
      // 29c: aload 12
      // 29e: aconst_null
      // 29f: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 2a2: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 2a5: astore 7
      // 2a7: aload 11
      // 2a9: aconst_null
      // 2aa: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 2ad: getstatic com/starpilot/starview/RoadData.stop Z
      // 2b0: ifeq 2cb
      // 2b3: aload 10
      // 2b5: invokevirtual java/io/File.delete ()Z
      // 2b8: pop
      // 2b9: ldc_w "Stopped."
      // 2bc: putstatic com/starpilot/starview/RoadData.status Ljava/lang/String;
      // 2bf: bipush 0
      // 2c0: putstatic com/starpilot/starview/RoadData.busy Z
      // 2c3: aload 1
      // 2c4: invokeinterface kotlin/jvm/functions/Function0.invoke ()Ljava/lang/Object; 1
      // 2c9: pop
      // 2ca: return
      // 2cb: getstatic com/starpilot/starview/RoadData.INSTANCE Lcom/starpilot/starview/RoadData;
      // 2ce: aload 10
      // 2d0: invokespecial com/starpilot/starview/RoadData.sha256 (Ljava/io/File;)Ljava/lang/String;
      // 2d3: aload 9
      // 2d5: invokevirtual com/starpilot/starview/RoadData$Item.getSha ()Ljava/lang/String;
      // 2d8: invokestatic kotlin/jvm/internal/Intrinsics.areEqual (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 2db: ifeq 30f
      // 2de: new java/io/File
      // 2e1: astore 7
      // 2e3: aload 7
      // 2e5: aload 6
      // 2e7: aload 9
      // 2e9: invokevirtual com/starpilot/starview/RoadData$Item.getFile ()Ljava/lang/String;
      // 2ec: invokespecial java/io/File.<init> (Ljava/io/File;Ljava/lang/String;)V
      // 2ef: aload 10
      // 2f1: aload 7
      // 2f3: invokevirtual java/io/File.renameTo (Ljava/io/File;)Z
      // 2f6: pop
      // 2f7: getstatic com/starpilot/starview/RoadData.done I
      // 2fa: bipush 1
      // 2fb: iadd
      // 2fc: putstatic com/starpilot/starview/RoadData.done I
      // 2ff: getstatic com/starpilot/starview/Hpms.INSTANCE Lcom/starpilot/starview/Hpms;
      // 302: invokevirtual com/starpilot/starview/Hpms.reload ()V
      // 305: aload 1
      // 306: invokeinterface kotlin/jvm/functions/Function0.invoke ()Ljava/lang/Object; 1
      // 30b: pop
      // 30c: goto 128
      // 30f: aload 10
      // 311: invokevirtual java/io/File.delete ()Z
      // 314: pop
      // 315: new java/lang/IllegalStateException
      // 318: astore 0
      // 319: aload 9
      // 31b: invokevirtual com/starpilot/starview/RoadData$Item.getFile ()Ljava/lang/String;
      // 31e: astore 6
      // 320: new java/lang/StringBuilder
      // 323: astore 5
      // 325: aload 5
      // 327: invokespecial java/lang/StringBuilder.<init> ()V
      // 32a: aload 0
      // 32b: aload 5
      // 32d: aload 6
      // 32f: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 332: ldc_w ": download damaged (checksum)"
      // 335: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 338: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 33b: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
      // 33e: aload 0
      // 33f: athrow
      // 340: astore 5
      // 342: aload 5
      // 344: athrow
      // 345: astore 0
      // 346: aload 12
      // 348: aload 5
      // 34a: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 34d: aload 0
      // 34e: athrow
      // 34f: astore 0
      // 350: aload 0
      // 351: athrow
      // 352: astore 5
      // 354: aload 11
      // 356: aload 0
      // 357: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 35a: aload 5
      // 35c: athrow
      // 35d: new java/lang/IllegalStateException
      // 360: astore 0
      // 361: aload 9
      // 363: invokevirtual com/starpilot/starview/RoadData$Item.getFile ()Ljava/lang/String;
      // 366: astore 5
      // 368: aload 7
      // 36a: invokevirtual java/net/HttpURLConnection.getResponseCode ()I
      // 36d: istore 2
      // 36e: new java/lang/StringBuilder
      // 371: astore 6
      // 373: aload 6
      // 375: invokespecial java/lang/StringBuilder.<init> ()V
      // 378: aload 0
      // 379: aload 6
      // 37b: aload 5
      // 37d: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 380: ldc_w ": HTTP "
      // 383: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 386: iload 2
      // 387: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
      // 38a: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 38d: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
      // 390: aload 0
      // 391: athrow
      // 392: aload 8
      // 394: invokeinterface java/util/List.size ()I 1
      // 399: istore 2
      // 39a: new java/lang/StringBuilder
      // 39d: astore 0
      // 39e: aload 0
      // 39f: invokespecial java/lang/StringBuilder.<init> ()V
      // 3a2: aload 0
      // 3a3: ldc_w "Done: "
      // 3a6: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 3a9: iload 2
      // 3aa: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
      // 3ad: ldc_w " states."
      // 3b0: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 3b3: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 3b6: putstatic com/starpilot/starview/RoadData.status Ljava/lang/String;
      // 3b9: goto 3e2
      // 3bc: astore 0
      // 3bd: goto 3ee
      // 3c0: astore 0
      // 3c1: aload 0
      // 3c2: invokevirtual java/lang/Exception.getMessage ()Ljava/lang/String;
      // 3c5: astore 0
      // 3c6: new java/lang/StringBuilder
      // 3c9: astore 5
      // 3cb: aload 5
      // 3cd: invokespecial java/lang/StringBuilder.<init> ()V
      // 3d0: aload 5
      // 3d2: ldc_w "Couldn't download: "
      // 3d5: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 3d8: aload 0
      // 3d9: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 3dc: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 3df: putstatic com/starpilot/starview/RoadData.status Ljava/lang/String;
      // 3e2: bipush 0
      // 3e3: putstatic com/starpilot/starview/RoadData.busy Z
      // 3e6: aload 1
      // 3e7: invokeinterface kotlin/jvm/functions/Function0.invoke ()Ljava/lang/Object; 1
      // 3ec: pop
      // 3ed: return
      // 3ee: bipush 0
      // 3ef: putstatic com/starpilot/starview/RoadData.busy Z
      // 3f2: aload 1
      // 3f3: invokeinterface kotlin/jvm/functions/Function0.invoke ()Ljava/lang/Object; 1
      // 3f8: pop
      // 3f9: aload 0
      // 3fa: athrow
   }

   private final HttpURLConnection get(String var1) {
      URLConnection var2 = new URL(var1).openConnection();
      Intrinsics.checkNotNull(var2, "null cannot be cast to non-null type java.net.HttpURLConnection");
      HttpURLConnection var3 = (HttpURLConnection)var2;
      var3.setConnectTimeout(20000);
      var3.setReadTimeout(60000);
      var3.setInstanceFollowRedirects(true);
      var3.setRequestProperty("User-Agent", "StarView");
      return var3;
   }

   private final String sha256(File param1) {
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
      // 00: ldc_w "SHA-256"
      // 03: invokestatic java/security/MessageDigest.getInstance (Ljava/lang/String;)Ljava/security/MessageDigest;
      // 06: astore 3
      // 07: new java/io/FileInputStream
      // 0a: dup
      // 0b: aload 1
      // 0c: invokespecial java/io/FileInputStream.<init> (Ljava/io/File;)V
      // 0f: checkcast java/io/Closeable
      // 12: astore 1
      // 13: aload 1
      // 14: checkcast java/io/FileInputStream
      // 17: astore 5
      // 19: ldc_w 65536
      // 1c: newarray 8
      // 1e: astore 4
      // 20: aload 5
      // 22: aload 4
      // 24: invokevirtual java/io/FileInputStream.read ([B)I
      // 27: istore 2
      // 28: iload 2
      // 29: iflt 37
      // 2c: aload 3
      // 2d: aload 4
      // 2f: bipush 0
      // 30: iload 2
      // 31: invokevirtual java/security/MessageDigest.update ([BII)V
      // 34: goto 20
      // 37: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 3a: astore 4
      // 3c: aload 1
      // 3d: aconst_null
      // 3e: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 41: aload 3
      // 42: invokevirtual java/security/MessageDigest.digest ()[B
      // 45: astore 1
      // 46: aload 1
      // 47: ldc_w "digest(...)"
      // 4a: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 4d: aload 1
      // 4e: ldc ""
      // 50: checkcast java/lang/CharSequence
      // 53: aconst_null
      // 54: aconst_null
      // 55: bipush 0
      // 56: aconst_null
      // 57: new com/starpilot/starview/RoadData$$ExternalSyntheticLambda0
      // 5a: dup
      // 5b: invokespecial com/starpilot/starview/RoadData$$ExternalSyntheticLambda0.<init> ()V
      // 5e: bipush 30
      // 60: aconst_null
      // 61: invokestatic kotlin/collections/ArraysKt.joinToString$default ([BLjava/lang/CharSequence;Ljava/lang/CharSequence;Ljava/lang/CharSequence;ILjava/lang/CharSequence;Lkotlin/jvm/functions/Function1;ILjava/lang/Object;)Ljava/lang/String;
      // 64: areturn
      // 65: astore 4
      // 67: aload 4
      // 69: athrow
      // 6a: astore 3
      // 6b: aload 1
      // 6c: aload 4
      // 6e: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 71: aload 3
      // 72: athrow
   }

   private static final CharSequence sha256$lambda$2(byte var0) {
      String var1 = String.format("%02x", Arrays.copyOf(new Object[]{var0}, 1));
      Intrinsics.checkNotNullExpressionValue(var1, "format(...)");
      return var1;
   }

   public final String base(Context var1) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      SharedPreferences var2 = var1.getSharedPreferences("road_data", 0);
      String var3 = "https://raw.githubusercontent.com/tsk-go/starview-data/main/hpms-2024/";
      String var4 = var2.getString("base", "https://raw.githubusercontent.com/tsk-go/starview-data/main/hpms-2024/");
      if (var4 != null) {
         var3 = var4;
      }

      return var3;
   }

   public final void cancel() {
      stop = true;
   }

   public final void download(Context var1, Function0<Unit> var2) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      Intrinsics.checkNotNullParameter(var2, "onChange");
      if (!busy) {
         busy = true;
         stop = false;
         done = 0;
         total = 0;
         status = "Asking for the list…";
         var2.invoke();
         new Thread(new RoadData$$ExternalSyntheticLambda1(var1, var2)).start();
      }
   }

   public final boolean getBusy() {
      return busy;
   }

   public final int getDone() {
      return done;
   }

   public final Map<Integer, String> getSTATES() {
      return STATES;
   }

   public final String getStatus() {
      return status;
   }

   public final int getTotal() {
      return total;
   }

   public final String import_(Context param1, Uri param2) {
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
      // 008: ldc_w "uri"
      // 00b: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
      // 00e: aload 1
      // 00f: invokevirtual android/content/Context.getContentResolver ()Landroid/content/ContentResolver;
      // 012: aload 2
      // 013: aconst_null
      // 014: aconst_null
      // 015: aconst_null
      // 016: aconst_null
      // 017: invokevirtual android/content/ContentResolver.query (Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
      // 01a: astore 4
      // 01c: ldc ""
      // 01e: astore 7
      // 020: aconst_null
      // 021: astore 6
      // 023: aload 4
      // 025: ifnull 077
      // 028: aload 4
      // 02a: checkcast java/io/Closeable
      // 02d: astore 5
      // 02f: aload 5
      // 031: checkcast android/database/Cursor
      // 034: astore 4
      // 036: aload 4
      // 038: invokeinterface android/database/Cursor.moveToFirst ()Z 1
      // 03d: ifeq 056
      // 040: aload 4
      // 042: aload 4
      // 044: ldc_w "_display_name"
      // 047: invokeinterface android/database/Cursor.getColumnIndexOrThrow (Ljava/lang/String;)I 2
      // 04c: invokeinterface android/database/Cursor.getString (I)Ljava/lang/String; 2
      // 051: astore 4
      // 053: goto 059
      // 056: aconst_null
      // 057: astore 4
      // 059: aload 5
      // 05b: aconst_null
      // 05c: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 05f: aload 4
      // 061: astore 5
      // 063: aload 4
      // 065: ifnonnull 07b
      // 068: goto 077
      // 06b: astore 2
      // 06c: aload 2
      // 06d: athrow
      // 06e: astore 1
      // 06f: aload 5
      // 071: aload 2
      // 072: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 075: aload 1
      // 076: athrow
      // 077: ldc ""
      // 079: astore 5
      // 07b: getstatic com/starpilot/starview/Hpms.INSTANCE Lcom/starpilot/starview/Hpms;
      // 07e: invokevirtual com/starpilot/starview/Hpms.getDir ()Ljava/io/File;
      // 081: astore 8
      // 083: aload 8
      // 085: astore 4
      // 087: aload 8
      // 089: ifnonnull 0a2
      // 08c: new java/io/File
      // 08f: astore 4
      // 091: aload 4
      // 093: aload 1
      // 094: invokevirtual android/content/Context.getFilesDir ()Ljava/io/File;
      // 097: ldc "hpms"
      // 099: invokespecial java/io/File.<init> (Ljava/io/File;Ljava/lang/String;)V
      // 09c: aload 4
      // 09e: invokevirtual java/io/File.mkdirs ()Z
      // 0a1: pop
      // 0a2: new java/io/File
      // 0a5: astore 8
      // 0a7: aload 8
      // 0a9: aload 4
      // 0ab: ldc_w "import.part"
      // 0ae: invokespecial java/io/File.<init> (Ljava/io/File;Ljava/lang/String;)V
      // 0b1: aload 1
      // 0b2: invokevirtual android/content/Context.getContentResolver ()Landroid/content/ContentResolver;
      // 0b5: aload 2
      // 0b6: invokevirtual android/content/ContentResolver.openInputStream (Landroid/net/Uri;)Ljava/io/InputStream;
      // 0b9: astore 1
      // 0ba: aload 1
      // 0bb: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNull (Ljava/lang/Object;)V
      // 0be: aload 1
      // 0bf: checkcast java/io/Closeable
      // 0c2: astore 1
      // 0c3: aload 1
      // 0c4: checkcast java/io/InputStream
      // 0c7: astore 9
      // 0c9: new java/io/FileOutputStream
      // 0cc: astore 2
      // 0cd: aload 2
      // 0ce: aload 8
      // 0d0: invokespecial java/io/FileOutputStream.<init> (Ljava/io/File;)V
      // 0d3: aload 2
      // 0d4: checkcast java/io/Closeable
      // 0d7: astore 2
      // 0d8: aload 2
      // 0d9: checkcast java/io/FileOutputStream
      // 0dc: checkcast java/io/OutputStream
      // 0df: astore 10
      // 0e1: bipush 0
      // 0e2: istore 3
      // 0e3: aload 9
      // 0e5: aload 10
      // 0e7: bipush 0
      // 0e8: bipush 2
      // 0e9: aconst_null
      // 0ea: invokestatic kotlin/io/ByteStreamsKt.copyTo$default (Ljava/io/InputStream;Ljava/io/OutputStream;IILjava/lang/Object;)J
      // 0ed: pop2
      // 0ee: aload 2
      // 0ef: aconst_null
      // 0f0: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 0f3: aload 1
      // 0f4: aconst_null
      // 0f5: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 0f8: new com/starpilot/starview/Hpms$Pack
      // 0fb: astore 2
      // 0fc: aload 2
      // 0fd: aload 8
      // 0ff: invokespecial com/starpilot/starview/Hpms$Pack.<init> (Ljava/io/File;)V
      // 102: goto 108
      // 105: astore 1
      // 106: aconst_null
      // 107: astore 2
      // 108: aload 2
      // 109: ifnonnull 119
      // 10c: aload 8
      // 10e: invokevirtual java/io/File.delete ()Z
      // 111: pop
      // 112: ldc_w "Not a StarView road data file."
      // 115: astore 1
      // 116: goto 1ce
      // 119: new kotlin/text/Regex
      // 11c: astore 1
      // 11d: aload 1
      // 11e: ldc_w "hpms_([A-Z]{2})\\.bin"
      // 121: invokespecial kotlin/text/Regex.<init> (Ljava/lang/String;)V
      // 124: aload 1
      // 125: aload 5
      // 127: checkcast java/lang/CharSequence
      // 12a: bipush 0
      // 12b: bipush 2
      // 12c: aconst_null
      // 12d: invokestatic kotlin/text/Regex.find$default (Lkotlin/text/Regex;Ljava/lang/CharSequence;IILjava/lang/Object;)Lkotlin/text/MatchResult;
      // 130: astore 1
      // 131: aload 1
      // 132: ifnull 15a
      // 135: aload 1
      // 136: invokeinterface kotlin/text/MatchResult.getGroupValues ()Ljava/util/List; 1
      // 13b: astore 1
      // 13c: aload 1
      // 13d: ifnull 15a
      // 140: aload 1
      // 141: bipush 1
      // 142: invokeinterface java/util/List.get (I)Ljava/lang/Object; 2
      // 147: checkcast java/lang/String
      // 14a: astore 5
      // 14c: aload 5
      // 14e: astore 1
      // 14f: aload 5
      // 151: ifnonnull 157
      // 154: goto 15a
      // 157: goto 176
      // 15a: getstatic com/starpilot/starview/RoadData.STATES Ljava/util/Map;
      // 15d: aload 2
      // 15e: invokevirtual com/starpilot/starview/Hpms$Pack.getState ()I
      // 161: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 164: invokeinterface java/util/Map.get (Ljava/lang/Object;)Ljava/lang/Object; 2
      // 169: checkcast java/lang/String
      // 16c: astore 2
      // 16d: aload 2
      // 16e: astore 1
      // 16f: aload 2
      // 170: ifnonnull 157
      // 173: aload 7
      // 175: astore 1
      // 176: aload 1
      // 177: checkcast java/lang/CharSequence
      // 17a: invokeinterface java/lang/CharSequence.length ()I 1
      // 17f: ifne 184
      // 182: bipush 1
      // 183: istore 3
      // 184: iload 3
      // 185: ifeq 195
      // 188: aload 8
      // 18a: invokevirtual java/io/File.delete ()Z
      // 18d: pop
      // 18e: ldc_w "Unknown state in that file."
      // 191: astore 1
      // 192: goto 1ce
      // 195: new java/io/File
      // 198: astore 2
      // 199: new java/lang/StringBuilder
      // 19c: astore 5
      // 19e: aload 5
      // 1a0: invokespecial java/lang/StringBuilder.<init> ()V
      // 1a3: aload 2
      // 1a4: aload 4
      // 1a6: aload 5
      // 1a8: ldc_w "hpms_"
      // 1ab: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 1ae: aload 1
      // 1af: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 1b2: ldc_w ".bin"
      // 1b5: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 1b8: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 1bb: invokespecial java/io/File.<init> (Ljava/io/File;Ljava/lang/String;)V
      // 1be: aload 8
      // 1c0: aload 2
      // 1c1: invokevirtual java/io/File.renameTo (Ljava/io/File;)Z
      // 1c4: pop
      // 1c5: getstatic com/starpilot/starview/Hpms.INSTANCE Lcom/starpilot/starview/Hpms;
      // 1c8: invokevirtual com/starpilot/starview/Hpms.reload ()V
      // 1cb: aload 6
      // 1cd: astore 1
      // 1ce: goto 209
      // 1d1: astore 5
      // 1d3: aload 5
      // 1d5: athrow
      // 1d6: astore 4
      // 1d8: aload 2
      // 1d9: aload 5
      // 1db: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 1de: aload 4
      // 1e0: athrow
      // 1e1: astore 2
      // 1e2: aload 2
      // 1e3: athrow
      // 1e4: astore 4
      // 1e6: aload 1
      // 1e7: aload 2
      // 1e8: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 1eb: aload 4
      // 1ed: athrow
      // 1ee: astore 1
      // 1ef: aload 1
      // 1f0: invokevirtual java/lang/Exception.getMessage ()Ljava/lang/String;
      // 1f3: astore 1
      // 1f4: new java/lang/StringBuilder
      // 1f7: dup
      // 1f8: invokespecial java/lang/StringBuilder.<init> ()V
      // 1fb: ldc_w "Couldn't import: "
      // 1fe: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 201: aload 1
      // 202: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 205: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 208: astore 1
      // 209: aload 1
      // 20a: areturn
   }

   public final List<RoadData.Item> manifest(Context var1) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      String var11 = this.base(var1);
      HttpURLConnection var12 = this.get(var11 + "manifest.json");
      if (var12.getResponseCode() != 200) {
         int var2 = var12.getResponseCode();
         throw new IllegalStateException("manifest: HTTP " + var2);
      } else {
         InputStream var13 = var12.getInputStream();
         Intrinsics.checkNotNullExpressionValue(var13, "getInputStream(...)");
         Reader var14 = new InputStreamReader(var13, Charsets.UTF_8);
         BufferedReader var15;
         if (var14 instanceof BufferedReader) {
            var15 = (BufferedReader)var14;
         } else {
            var15 = new BufferedReader(var14, 8192);
         }

         JSONObject var5 = new JSONObject(TextStreamsKt.readText(var15));
         JSONArray var16 = var5.getJSONArray("files");
         Iterable var7 = RangesKt.until(0, var16.length());
         var var6 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var7, 10));
         Iterator var19 = var7.iterator();

         while (var19.hasNext()) {
            var6.add(var16.getJSONObject(((IntIterator)var19).nextInt()));
         }

         List var17 = var6;
         var7 = var17;
         var6 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var7, 10));

         for (JSONObject var10 : var7) {
            String var9 = var10.getString("state");
            Intrinsics.checkNotNullExpressionValue(var9, "getString(...)");
            String var8 = var10.getString("file");
            Intrinsics.checkNotNullExpressionValue(var8, "getString(...)");
            long var3 = var10.getLong("bytes");
            String var22 = var10.getString("sha256");
            Intrinsics.checkNotNullExpressionValue(var22, "getString(...)");
            var6.add(new RoadData.Item(var9, var8, var3, var22));
         }

         return var6;
      }
   }

   public final String mb(long var1) {
      String var4;
      if (var1 >= 1000000000L) {
         StringCompanionObject var3 = StringCompanionObject.INSTANCE;
         var4 = String.format(Locale.US, "%.1f GB", Arrays.copyOf(new Object[]{var1 / 1.0E9}, 1));
      } else {
         StringCompanionObject var5 = StringCompanionObject.INSTANCE;
         var4 = String.format(Locale.US, "%.0f MB", Arrays.copyOf(new Object[]{Math.max(1.0, var1 / 1000000.0)}, 1));
      }

      Intrinsics.checkNotNullExpressionValue(var4, "format(...)");
      return var4;
   }

   public final boolean onWifi(Context var1) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      ConnectivityManager var4 = (ConnectivityManager)var1.getSystemService("connectivity");
      Intrinsics.checkNotNull(var4, "null cannot be cast to non-null type android.net.ConnectivityManager");
      var4 = var4;
      NetworkCapabilities var6 = var4.getNetworkCapabilities(var4.getActiveNetwork());
      boolean var3 = false;
      if (var6 == null) {
         return false;
      } else {
         boolean var2 = var3;
         if (var6.hasCapability(11)) {
            var2 = var3;
            if (var6.hasTransport(1)) {
               var2 = true;
            }
         }

         return var2;
      }
   }

   public final void setBase(Context var1, String var2) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      Intrinsics.checkNotNullParameter(var2, "b");
      Editor var3 = var1.getSharedPreferences("road_data", 0).edit();
      String var4 = StringsKt.trim(var2).toString();
      if (!StringsKt.endsWith$default(var4, "/", false, 2, null)) {
         var4 = var4 + "/";
      }

      var3.putString("base", var4).apply();
   }

   public static final class Item {
      private final long bytes;
      private final String file;
      private final String sha;
      private final String st;

      public Item(String var1, String var2, long var3, String var5) {
         Intrinsics.checkNotNullParameter(var1, "st");
         Intrinsics.checkNotNullParameter(var2, "file");
         Intrinsics.checkNotNullParameter(var5, "sha");
         super();
         this.st = var1;
         this.file = var2;
         this.bytes = var3;
         this.sha = var5;
      }

      public final long getBytes() {
         return this.bytes;
      }

      public final String getFile() {
         return this.file;
      }

      public final String getSha() {
         return this.sha;
      }

      public final String getSt() {
         return this.st;
      }
   }
}
