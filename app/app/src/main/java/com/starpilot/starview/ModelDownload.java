package com.starpilot.starview;

import android.content.Context;
import android.net.Uri;
import java.io.File;
import java.util.Arrays;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

public final class ModelDownload {
   private static final String BASE = "https://raw.githubusercontent.com/tsk-go/openpilot/starview-models/";
   public static final String DET_NAME = "yolox_s_384x640_fp16.lite";
   public static final String DET_SHA = "bca6acaba042dba16a5020521c707603d579f2061165dd9aa76929211744a587";
   public static final long DET_SIZE = 17995664L;
   public static final ModelDownload INSTANCE = new ModelDownload();
   private static final String TAG = "StarView/Models";
   private static volatile boolean busy;
   private static volatile long lastTryMs;
   private static volatile boolean notThere;
   private static volatile String status = "detector model not downloaded yet";

   // $VF: synthetic method
   public static void $r8$lambda$292wnnsNIHxxiISSDtUaOVzMhYU(Context var0) {
      ensure$lambda$7(var0);
   }

   // $VF: synthetic method
   public static CharSequence $r8$lambda$619_0I77uf5OgqEvTdR77XkLP80(byte var0) {
      return importDetector$lambda$3(var0);
   }

   // $VF: synthetic method
   public static CharSequence $r8$lambda$qFCx3c2v4zCNTlw_nYOFVjaQ_Ys/* $VF was: $r8$lambda$qFCx3c2v4zCNTlw_nYOFVjaQ_Ys*/(byte var0) {
      return ensure$lambda$7$lambda$6(var0);
   }

   private ModelDownload() {
   }

   private final void ensure(Context var1) {
      long var4 = System.currentTimeMillis();
      if (!busy) {
         long var6 = lastTryMs;
         long var2;
         if (notThere) {
            var2 = 600000L;
         } else {
            var2 = 60000L;
         }

         if (var4 - var6 >= var2) {
            busy = true;
            lastTryMs = var4;
            Thread var8 = new Thread(new ModelDownload$$ExternalSyntheticLambda0(var1), "starview-model-download");
            var8.setDaemon(true);
            var8.start();
            return;
         }
      }
   }

   private static final void ensure$lambda$7(Context param0) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.NullPointerException: Cannot invoke "org.jetbrains.java.decompiler.code.cfg.ExceptionRangeCFG.isCircular()" because "range" is null
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.graphToStatement(DomHelper.java:84)
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:203)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 000: getstatic com/starpilot/starview/ModelDownload.INSTANCE Lcom/starpilot/starview/ModelDownload;
      // 003: aload 0
      // 004: invokespecial com/starpilot/starview/ModelDownload.file (Landroid/content/Context;)Ljava/io/File;
      // 007: invokevirtual java/io/File.getPath ()Ljava/lang/String;
      // 00a: astore 11
      // 00c: new java/io/File
      // 00f: dup
      // 010: new java/lang/StringBuilder
      // 013: dup
      // 014: invokespecial java/lang/StringBuilder.<init> ()V
      // 017: aload 11
      // 019: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 01c: ldc ".part"
      // 01e: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 021: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 024: invokespecial java/io/File.<init> (Ljava/lang/String;)V
      // 027: astore 14
      // 029: new java/net/URL
      // 02c: astore 11
      // 02e: aload 11
      // 030: ldc "https://raw.githubusercontent.com/tsk-go/openpilot/starview-models/yolox_s_384x640_fp16.lite"
      // 032: invokespecial java/net/URL.<init> (Ljava/lang/String;)V
      // 035: aload 11
      // 037: invokevirtual java/net/URL.openConnection ()Ljava/net/URLConnection;
      // 03a: astore 11
      // 03c: aload 11
      // 03e: ldc "null cannot be cast to non-null type java.net.HttpURLConnection"
      // 040: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNull (Ljava/lang/Object;Ljava/lang/String;)V
      // 043: aload 11
      // 045: checkcast java/net/HttpURLConnection
      // 048: astore 12
      // 04a: aload 12
      // 04c: sipush 15000
      // 04f: invokevirtual java/net/HttpURLConnection.setConnectTimeout (I)V
      // 052: aload 12
      // 054: sipush 30000
      // 057: invokevirtual java/net/HttpURLConnection.setReadTimeout (I)V
      // 05a: aload 12
      // 05c: invokevirtual java/net/HttpURLConnection.getResponseCode ()I
      // 05f: sipush 404
      // 062: if_icmpne 06a
      // 065: bipush 1
      // 066: istore 2
      // 067: goto 06c
      // 06a: bipush 0
      // 06b: istore 2
      // 06c: iload 2
      // 06d: putstatic com/starpilot/starview/ModelDownload.notThere Z
      // 070: aload 12
      // 072: invokevirtual java/net/HttpURLConnection.getResponseCode ()I
      // 075: sipush 200
      // 078: if_icmpeq 0b1
      // 07b: new java/lang/IllegalStateException
      // 07e: astore 11
      // 080: getstatic com/starpilot/starview/ModelDownload.notThere Z
      // 083: ifeq 08c
      // 086: ldc "not online yet"
      // 088: astore 0
      // 089: goto 0a8
      // 08c: aload 12
      // 08e: invokevirtual java/net/HttpURLConnection.getResponseCode ()I
      // 091: istore 1
      // 092: new java/lang/StringBuilder
      // 095: astore 0
      // 096: aload 0
      // 097: invokespecial java/lang/StringBuilder.<init> ()V
      // 09a: aload 0
      // 09b: ldc "server answered "
      // 09d: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 0a0: iload 1
      // 0a1: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
      // 0a4: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 0a7: astore 0
      // 0a8: aload 11
      // 0aa: aload 0
      // 0ab: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
      // 0ae: aload 11
      // 0b0: athrow
      // 0b1: ldc "SHA-256"
      // 0b3: invokestatic java/security/MessageDigest.getInstance (Ljava/lang/String;)Ljava/security/MessageDigest;
      // 0b6: astore 17
      // 0b8: lconst_0
      // 0b9: lstore 5
      // 0bb: aload 12
      // 0bd: invokevirtual java/net/HttpURLConnection.getInputStream ()Ljava/io/InputStream;
      // 0c0: checkcast java/io/Closeable
      // 0c3: astore 15
      // 0c5: aload 15
      // 0c7: checkcast java/io/InputStream
      // 0ca: astore 11
      // 0cc: bipush 0
      // 0cd: istore 2
      // 0ce: bipush 0
      // 0cf: istore 4
      // 0d1: new java/io/FileOutputStream
      // 0d4: astore 13
      // 0d6: aload 13
      // 0d8: aload 14
      // 0da: invokespecial java/io/FileOutputStream.<init> (Ljava/io/File;)V
      // 0dd: aload 13
      // 0df: checkcast java/io/Closeable
      // 0e2: astore 16
      // 0e4: aload 12
      // 0e6: astore 13
      // 0e8: aload 11
      // 0ea: astore 13
      // 0ec: iload 2
      // 0ed: istore 3
      // 0ee: aload 16
      // 0f0: checkcast java/io/FileOutputStream
      // 0f3: astore 18
      // 0f5: aload 12
      // 0f7: astore 13
      // 0f9: aload 11
      // 0fb: astore 13
      // 0fd: iload 2
      // 0fe: istore 3
      // 0ff: ldc 65536
      // 101: newarray 8
      // 103: astore 19
      // 105: iload 4
      // 107: istore 2
      // 108: aload 12
      // 10a: astore 13
      // 10c: aload 11
      // 10e: astore 13
      // 110: iload 2
      // 111: istore 3
      // 112: aload 11
      // 114: aload 19
      // 116: invokevirtual java/io/InputStream.read ([B)I
      // 119: istore 1
      // 11a: iload 1
      // 11b: iflt 1a1
      // 11e: aload 12
      // 120: astore 13
      // 122: aload 11
      // 124: astore 13
      // 126: iload 2
      // 127: istore 3
      // 128: aload 18
      // 12a: aload 19
      // 12c: bipush 0
      // 12d: iload 1
      // 12e: invokevirtual java/io/FileOutputStream.write ([BII)V
      // 131: aload 12
      // 133: astore 13
      // 135: aload 11
      // 137: astore 13
      // 139: iload 2
      // 13a: istore 3
      // 13b: aload 17
      // 13d: aload 19
      // 13f: bipush 0
      // 140: iload 1
      // 141: invokevirtual java/security/MessageDigest.update ([BII)V
      // 144: lload 5
      // 146: iload 1
      // 147: i2l
      // 148: ladd
      // 149: lstore 5
      // 14b: bipush 100
      // 14d: i2l
      // 14e: lstore 7
      // 150: lload 7
      // 152: lload 5
      // 154: lmul
      // 155: ldc2_w 17995664
      // 158: ldiv
      // 159: lstore 7
      // 15b: ldc 1048576
      // 15d: i2l
      // 15e: lstore 9
      // 160: lload 5
      // 162: lload 9
      // 164: ldiv
      // 165: lstore 9
      // 167: new java/lang/StringBuilder
      // 16a: astore 13
      // 16c: aload 13
      // 16e: invokespecial java/lang/StringBuilder.<init> ()V
      // 171: aload 13
      // 173: ldc "downloading the detector model: "
      // 175: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 178: lload 7
      // 17a: invokevirtual java/lang/StringBuilder.append (J)Ljava/lang/StringBuilder;
      // 17d: ldc "% ("
      // 17f: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 182: lload 9
      // 184: invokevirtual java/lang/StringBuilder.append (J)Ljava/lang/StringBuilder;
      // 187: ldc " of 17 MB)"
      // 189: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 18c: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 18f: putstatic com/starpilot/starview/ModelDownload.status Ljava/lang/String;
      // 192: goto 108
      // 195: astore 0
      // 196: goto 25e
      // 199: astore 0
      // 19a: goto 25e
      // 19d: astore 0
      // 19e: goto 25e
      // 1a1: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 1a4: astore 11
      // 1a6: aload 16
      // 1a8: aconst_null
      // 1a9: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 1ac: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 1af: astore 11
      // 1b1: aload 15
      // 1b3: aconst_null
      // 1b4: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 1b7: aload 17
      // 1b9: invokevirtual java/security/MessageDigest.digest ()[B
      // 1bc: astore 13
      // 1be: aload 13
      // 1c0: ldc "digest(...)"
      // 1c2: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 1c5: ldc ""
      // 1c7: checkcast java/lang/CharSequence
      // 1ca: astore 11
      // 1cc: new com/starpilot/starview/ModelDownload$$ExternalSyntheticLambda1
      // 1cf: astore 12
      // 1d1: aload 12
      // 1d3: invokespecial com/starpilot/starview/ModelDownload$$ExternalSyntheticLambda1.<init> ()V
      // 1d6: aload 13
      // 1d8: aload 11
      // 1da: aconst_null
      // 1db: aconst_null
      // 1dc: bipush 0
      // 1dd: aconst_null
      // 1de: aload 12
      // 1e0: bipush 30
      // 1e2: aconst_null
      // 1e3: invokestatic kotlin/collections/ArraysKt.joinToString$default ([BLjava/lang/CharSequence;Ljava/lang/CharSequence;Ljava/lang/CharSequence;ILjava/lang/CharSequence;Lkotlin/jvm/functions/Function1;ILjava/lang/Object;)Ljava/lang/String;
      // 1e6: astore 11
      // 1e8: lload 5
      // 1ea: ldc2_w 17995664
      // 1ed: lcmp
      // 1ee: ifne 22f
      // 1f1: aload 11
      // 1f3: ldc "bca6acaba042dba16a5020521c707603d579f2061165dd9aa76929211744a587"
      // 1f5: invokestatic kotlin/jvm/internal/Intrinsics.areEqual (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 1f8: ifeq 22f
      // 1fb: aload 14
      // 1fd: getstatic com/starpilot/starview/ModelDownload.INSTANCE Lcom/starpilot/starview/ModelDownload;
      // 200: aload 0
      // 201: invokespecial com/starpilot/starview/ModelDownload.file (Landroid/content/Context;)Ljava/io/File;
      // 204: invokevirtual java/io/File.renameTo (Ljava/io/File;)Z
      // 207: ifeq 222
      // 20a: getstatic com/starpilot/starview/ModelDownload.INSTANCE Lcom/starpilot/starview/ModelDownload;
      // 20d: aload 0
      // 20e: invokespecial com/starpilot/starview/ModelDownload.mark (Landroid/content/Context;)Ljava/io/File;
      // 211: ldc "bca6acaba042dba16a5020521c707603d579f2061165dd9aa76929211744a587"
      // 213: aconst_null
      // 214: bipush 2
      // 215: aconst_null
      // 216: invokestatic kotlin/io/FilesKt.writeText$default (Ljava/io/File;Ljava/lang/String;Ljava/nio/charset/Charset;ILjava/lang/Object;)V
      // 219: ldc_w "detector model downloaded"
      // 21c: putstatic com/starpilot/starview/ModelDownload.status Ljava/lang/String;
      // 21f: goto 2e0
      // 222: new java/lang/IllegalStateException
      // 225: astore 0
      // 226: aload 0
      // 227: ldc_w "couldn't save it"
      // 22a: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
      // 22d: aload 0
      // 22e: athrow
      // 22f: new java/lang/IllegalStateException
      // 232: astore 0
      // 233: new java/lang/StringBuilder
      // 236: astore 11
      // 238: aload 11
      // 23a: invokespecial java/lang/StringBuilder.<init> ()V
      // 23d: aload 0
      // 23e: aload 11
      // 240: ldc_w "download damaged ("
      // 243: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 246: lload 5
      // 248: invokevirtual java/lang/StringBuilder.append (J)Ljava/lang/StringBuilder;
      // 24b: ldc_w " bytes) - trying again in a minute"
      // 24e: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 251: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 254: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
      // 257: aload 0
      // 258: athrow
      // 259: astore 0
      // 25a: goto 25e
      // 25d: astore 0
      // 25e: aload 0
      // 25f: athrow
      // 260: astore 11
      // 262: aload 16
      // 264: aload 0
      // 265: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 268: aload 11
      // 26a: athrow
      // 26b: astore 0
      // 26c: goto 270
      // 26f: astore 0
      // 270: aload 0
      // 271: athrow
      // 272: astore 11
      // 274: aload 15
      // 276: aload 0
      // 277: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 27a: aload 11
      // 27c: athrow
      // 27d: astore 0
      // 27e: goto 2e5
      // 281: astore 12
      // 283: new java/lang/StringBuilder
      // 286: astore 0
      // 287: aload 0
      // 288: invokespecial java/lang/StringBuilder.<init> ()V
      // 28b: ldc "StarView/Models"
      // 28d: aload 0
      // 28e: ldc_w "detector model: "
      // 291: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 294: aload 12
      // 296: invokevirtual java/lang/StringBuilder.append (Ljava/lang/Object;)Ljava/lang/StringBuilder;
      // 299: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 29c: invokestatic android/util/Log.w (Ljava/lang/String;Ljava/lang/String;)I
      // 29f: pop
      // 2a0: aload 14
      // 2a2: invokevirtual java/io/File.delete ()Z
      // 2a5: pop
      // 2a6: aload 12
      // 2a8: invokevirtual java/lang/Exception.getMessage ()Ljava/lang/String;
      // 2ab: astore 11
      // 2ad: aload 11
      // 2af: astore 0
      // 2b0: aload 11
      // 2b2: ifnonnull 2be
      // 2b5: aload 12
      // 2b7: invokevirtual java/lang/Object.getClass ()Ljava/lang/Class;
      // 2ba: invokevirtual java/lang/Class.getSimpleName ()Ljava/lang/String;
      // 2bd: astore 0
      // 2be: new java/lang/StringBuilder
      // 2c1: astore 11
      // 2c3: aload 11
      // 2c5: invokespecial java/lang/StringBuilder.<init> ()V
      // 2c8: aload 11
      // 2ca: ldc_w "detector model not installed ("
      // 2cd: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 2d0: aload 0
      // 2d1: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 2d4: ldc_w "): download yolox_s_384x640_fp16.lite from the chat on this tablet, then Settings › Camera & view › Detector model › Import"
      // 2d7: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 2da: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 2dd: putstatic com/starpilot/starview/ModelDownload.status Ljava/lang/String;
      // 2e0: bipush 0
      // 2e1: putstatic com/starpilot/starview/ModelDownload.busy Z
      // 2e4: return
      // 2e5: bipush 0
      // 2e6: putstatic com/starpilot/starview/ModelDownload.busy Z
      // 2e9: aload 0
      // 2ea: athrow
   }

   private static final CharSequence ensure$lambda$7$lambda$6(byte var0) {
      String var1 = String.format("%02x", Arrays.copyOf(new Object[]{var0}, 1));
      Intrinsics.checkNotNullExpressionValue(var1, "format(...)");
      return var1;
   }

   private final File file(Context var1) {
      File var2 = new File(var1.getFilesDir(), "det");
      var2.mkdirs();
      return new File(var2, "yolox_s_384x640_fp16.lite");
   }

   private static final CharSequence importDetector$lambda$3(byte var0) {
      String var1 = String.format("%02x", Arrays.copyOf(new Object[]{var0}, 1));
      Intrinsics.checkNotNullExpressionValue(var1, "format(...)");
      return var1;
   }

   private final File mark(Context var1) {
      return new File(new File(var1.getFilesDir(), "det"), "yolox_s_384x640_fp16.lite.sha256");
   }

   public final File detector(Context var1) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      File var4 = this.file(var1);
      if (var4.length() == 17995664L) {
         boolean var2;
         try {
            var2 = Intrinsics.areEqual(
               StringsKt.trim(FilesKt.readText$default(this.mark(var1), null, 1, null)).toString(),
               "bca6acaba042dba16a5020521c707603d579f2061165dd9aa76929211744a587"
            );
         } catch (Exception var5) {
            var2 = false;
         }

         if (var2) {
            return var4;
         }
      }

      var1 = var1.getApplicationContext();
      Intrinsics.checkNotNullExpressionValue(var1, "getApplicationContext(...)");
      this.ensure(var1);
      return null;
   }

   public final String getStatus() {
      return status;
   }

   public final String importDetector(Context param1, Uri param2) {
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
      // 00e: aload 0
      // 00f: aload 1
      // 010: invokespecial com/starpilot/starview/ModelDownload.file (Landroid/content/Context;)Ljava/io/File;
      // 013: invokevirtual java/io/File.getPath ()Ljava/lang/String;
      // 016: astore 9
      // 018: new java/io/File
      // 01b: dup
      // 01c: new java/lang/StringBuilder
      // 01f: dup
      // 020: invokespecial java/lang/StringBuilder.<init> ()V
      // 023: aload 9
      // 025: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 028: ldc ".part"
      // 02a: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 02d: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 030: invokespecial java/io/File.<init> (Ljava/lang/String;)V
      // 033: astore 10
      // 035: ldc "SHA-256"
      // 037: invokestatic java/security/MessageDigest.getInstance (Ljava/lang/String;)Ljava/security/MessageDigest;
      // 03a: astore 13
      // 03c: lconst_0
      // 03d: lstore 4
      // 03f: aload 1
      // 040: invokevirtual android/content/Context.getContentResolver ()Landroid/content/ContentResolver;
      // 043: aload 2
      // 044: invokevirtual android/content/ContentResolver.openInputStream (Landroid/net/Uri;)Ljava/io/InputStream;
      // 047: astore 2
      // 048: aload 2
      // 049: ifnonnull 050
      // 04c: ldc_w "Couldn't open that file"
      // 04f: areturn
      // 050: aload 2
      // 051: checkcast java/io/Closeable
      // 054: astore 11
      // 056: aload 11
      // 058: checkcast java/io/InputStream
      // 05b: astore 2
      // 05c: bipush 0
      // 05d: istore 8
      // 05f: bipush 0
      // 060: istore 6
      // 062: new java/io/FileOutputStream
      // 065: astore 9
      // 067: aload 9
      // 069: aload 10
      // 06b: invokespecial java/io/FileOutputStream.<init> (Ljava/io/File;)V
      // 06e: aload 9
      // 070: checkcast java/io/Closeable
      // 073: astore 12
      // 075: aload 2
      // 076: astore 9
      // 078: iload 8
      // 07a: istore 7
      // 07c: aload 12
      // 07e: checkcast java/io/FileOutputStream
      // 081: astore 15
      // 083: aload 2
      // 084: astore 9
      // 086: iload 8
      // 088: istore 7
      // 08a: ldc 65536
      // 08c: newarray 8
      // 08e: astore 14
      // 090: aload 2
      // 091: astore 9
      // 093: iload 6
      // 095: istore 7
      // 097: aload 2
      // 098: aload 14
      // 09a: invokevirtual java/io/InputStream.read ([B)I
      // 09d: istore 3
      // 09e: iload 3
      // 09f: iflt 0cc
      // 0a2: aload 2
      // 0a3: astore 9
      // 0a5: iload 6
      // 0a7: istore 7
      // 0a9: aload 15
      // 0ab: aload 14
      // 0ad: bipush 0
      // 0ae: iload 3
      // 0af: invokevirtual java/io/FileOutputStream.write ([BII)V
      // 0b2: aload 2
      // 0b3: astore 9
      // 0b5: iload 6
      // 0b7: istore 7
      // 0b9: aload 13
      // 0bb: aload 14
      // 0bd: bipush 0
      // 0be: iload 3
      // 0bf: invokevirtual java/security/MessageDigest.update ([BII)V
      // 0c2: lload 4
      // 0c4: iload 3
      // 0c5: i2l
      // 0c6: ladd
      // 0c7: lstore 4
      // 0c9: goto 090
      // 0cc: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 0cf: astore 2
      // 0d0: aconst_null
      // 0d1: astore 2
      // 0d2: aload 12
      // 0d4: aconst_null
      // 0d5: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 0d8: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 0db: astore 9
      // 0dd: aload 11
      // 0df: aconst_null
      // 0e0: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 0e3: aload 13
      // 0e5: invokevirtual java/security/MessageDigest.digest ()[B
      // 0e8: astore 11
      // 0ea: aload 11
      // 0ec: ldc "digest(...)"
      // 0ee: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 0f1: ldc ""
      // 0f3: checkcast java/lang/CharSequence
      // 0f6: astore 12
      // 0f8: new com/starpilot/starview/ModelDownload$$ExternalSyntheticLambda2
      // 0fb: astore 9
      // 0fd: aload 9
      // 0ff: invokespecial com/starpilot/starview/ModelDownload$$ExternalSyntheticLambda2.<init> ()V
      // 102: aload 11
      // 104: aload 12
      // 106: aconst_null
      // 107: aconst_null
      // 108: bipush 0
      // 109: aconst_null
      // 10a: aload 9
      // 10c: bipush 30
      // 10e: aconst_null
      // 10f: invokestatic kotlin/collections/ArraysKt.joinToString$default ([BLjava/lang/CharSequence;Ljava/lang/CharSequence;Ljava/lang/CharSequence;ILjava/lang/CharSequence;Lkotlin/jvm/functions/Function1;ILjava/lang/Object;)Ljava/lang/String;
      // 112: astore 9
      // 114: lload 4
      // 116: ldc2_w 17995664
      // 119: lcmp
      // 11a: ifne 153
      // 11d: aload 9
      // 11f: ldc "bca6acaba042dba16a5020521c707603d579f2061165dd9aa76929211744a587"
      // 121: invokestatic kotlin/jvm/internal/Intrinsics.areEqual (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 124: ifne 12a
      // 127: goto 153
      // 12a: aload 10
      // 12c: aload 0
      // 12d: aload 1
      // 12e: invokespecial com/starpilot/starview/ModelDownload.file (Landroid/content/Context;)Ljava/io/File;
      // 131: invokevirtual java/io/File.renameTo (Ljava/io/File;)Z
      // 134: ifne 13b
      // 137: ldc_w "Couldn't save it"
      // 13a: areturn
      // 13b: aload 0
      // 13c: aload 1
      // 13d: invokespecial com/starpilot/starview/ModelDownload.mark (Landroid/content/Context;)Ljava/io/File;
      // 140: ldc "bca6acaba042dba16a5020521c707603d579f2061165dd9aa76929211744a587"
      // 142: aconst_null
      // 143: bipush 2
      // 144: aconst_null
      // 145: invokestatic kotlin/io/FilesKt.writeText$default (Ljava/io/File;Ljava/lang/String;Ljava/nio/charset/Charset;ILjava/lang/Object;)V
      // 148: ldc_w "detector model installed"
      // 14b: putstatic com/starpilot/starview/ModelDownload.status Ljava/lang/String;
      // 14e: aload 2
      // 14f: astore 1
      // 150: goto 196
      // 153: aload 10
      // 155: invokevirtual java/io/File.delete ()Z
      // 158: pop
      // 159: ldc_w "That's not yolox_s_384x640_fp16.lite (wrong size or contents)"
      // 15c: areturn
      // 15d: astore 1
      // 15e: goto 162
      // 161: astore 1
      // 162: aload 1
      // 163: athrow
      // 164: astore 2
      // 165: aload 12
      // 167: aload 1
      // 168: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 16b: aload 2
      // 16c: athrow
      // 16d: astore 2
      // 16e: aload 2
      // 16f: athrow
      // 170: astore 1
      // 171: aload 11
      // 173: aload 2
      // 174: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 177: aload 1
      // 178: athrow
      // 179: astore 9
      // 17b: aload 10
      // 17d: invokevirtual java/io/File.delete ()Z
      // 180: pop
      // 181: aload 9
      // 183: invokevirtual java/lang/Exception.getMessage ()Ljava/lang/String;
      // 186: astore 2
      // 187: aload 2
      // 188: astore 1
      // 189: aload 2
      // 18a: ifnonnull 196
      // 18d: aload 9
      // 18f: invokevirtual java/lang/Object.getClass ()Ljava/lang/Class;
      // 192: invokevirtual java/lang/Class.getSimpleName ()Ljava/lang/String;
      // 195: astore 1
      // 196: aload 1
      // 197: areturn
   }

   public final boolean installed(Context var1) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      long var5 = this.file(var1).length();
      boolean var4 = false;
      boolean var3 = var4;
      if (var5 == 17995664L) {
         boolean var2;
         try {
            var2 = Intrinsics.areEqual(
               StringsKt.trim(FilesKt.readText$default(this.mark(var1), null, 1, null)).toString(),
               "bca6acaba042dba16a5020521c707603d579f2061165dd9aa76929211744a587"
            );
         } catch (Exception var7) {
            var2 = false;
         }

         var3 = var4;
         if (var2) {
            var3 = true;
         }
      }

      return var3;
   }

   public static final class Missing extends Exception {
      public Missing(String var1) {
         Intrinsics.checkNotNullParameter(var1, "msg");
         super(var1);
      }
   }
}
