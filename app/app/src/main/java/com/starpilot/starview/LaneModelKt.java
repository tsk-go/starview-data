package com.starpilot.starview;

import android.content.Context;
import android.net.Uri;

public final class LaneModelKt {
   public static final String importLaneModel(Context param0, Uri param1) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index 2 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:385)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:551)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1051)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:501)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
      //
      // Bytecode:
      // 000: aload 0
      // 001: ldc "ctx"
      // 003: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
      // 006: aload 1
      // 007: ldc "uri"
      // 009: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
      // 00c: new java/io/File
      // 00f: astore 6
      // 011: aload 6
      // 013: aload 0
      // 014: invokevirtual android/content/Context.getFilesDir ()Ljava/io/File;
      // 017: ldc "lane_count.lite"
      // 019: invokespecial java/io/File.<init> (Ljava/io/File;Ljava/lang/String;)V
      // 01c: new java/io/File
      // 01f: astore 5
      // 021: aload 5
      // 023: aload 0
      // 024: invokevirtual android/content/Context.getFilesDir ()Ljava/io/File;
      // 027: ldc "lane_count.lite.part"
      // 029: invokespecial java/io/File.<init> (Ljava/io/File;Ljava/lang/String;)V
      // 02c: aload 0
      // 02d: invokevirtual android/content/Context.getContentResolver ()Landroid/content/ContentResolver;
      // 030: aload 1
      // 031: invokevirtual android/content/ContentResolver.openInputStream (Landroid/net/Uri;)Ljava/io/InputStream;
      // 034: astore 0
      // 035: aload 0
      // 036: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNull (Ljava/lang/Object;)V
      // 039: aload 0
      // 03a: checkcast java/io/Closeable
      // 03d: astore 0
      // 03e: aload 0
      // 03f: checkcast java/io/InputStream
      // 042: astore 7
      // 044: new java/io/FileOutputStream
      // 047: astore 1
      // 048: aload 1
      // 049: aload 5
      // 04b: invokespecial java/io/FileOutputStream.<init> (Ljava/io/File;)V
      // 04e: aload 1
      // 04f: checkcast java/io/Closeable
      // 052: astore 1
      // 053: aload 1
      // 054: checkcast java/io/FileOutputStream
      // 057: checkcast java/io/OutputStream
      // 05a: astore 8
      // 05c: bipush 0
      // 05d: istore 3
      // 05e: aload 7
      // 060: aload 8
      // 062: bipush 0
      // 063: bipush 2
      // 064: aconst_null
      // 065: invokestatic kotlin/io/ByteStreamsKt.copyTo$default (Ljava/io/InputStream;Ljava/io/OutputStream;IILjava/lang/Object;)J
      // 068: pop2
      // 069: aload 1
      // 06a: aconst_null
      // 06b: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 06e: aload 0
      // 06f: aconst_null
      // 070: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 073: new java/io/FileInputStream
      // 076: astore 0
      // 077: aload 0
      // 078: aload 5
      // 07a: invokespecial java/io/FileInputStream.<init> (Ljava/io/File;)V
      // 07d: aload 0
      // 07e: checkcast java/io/Closeable
      // 081: astore 0
      // 082: aload 0
      // 083: checkcast java/io/FileInputStream
      // 086: astore 7
      // 088: bipush 8
      // 08a: newarray 8
      // 08c: astore 1
      // 08d: aload 7
      // 08f: aload 1
      // 090: invokevirtual java/io/FileInputStream.read ([B)I
      // 093: pop
      // 094: aload 0
      // 095: aconst_null
      // 096: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 099: aload 5
      // 09b: invokevirtual java/io/File.length ()J
      // 09e: ldc2_w 1000000
      // 0a1: lcmp
      // 0a2: iflt 188
      // 0a5: new java/lang/String
      // 0a8: astore 0
      // 0a9: aload 0
      // 0aa: aload 1
      // 0ab: bipush 4
      // 0ac: bipush 4
      // 0ad: getstatic kotlin/text/Charsets.UTF_8 Ljava/nio/charset/Charset;
      // 0b0: invokespecial java/lang/String.<init> ([BIILjava/nio/charset/Charset;)V
      // 0b3: aload 0
      // 0b4: ldc "TFL3"
      // 0b6: invokestatic kotlin/jvm/internal/Intrinsics.areEqual (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 0b9: ifne 0bf
      // 0bc: goto 188
      // 0bf: aload 5
      // 0c1: invokestatic kotlin/io/FilesKt.readBytes (Ljava/io/File;)[B
      // 0c4: astore 1
      // 0c5: aload 1
      // 0c6: arraylength
      // 0c7: invokestatic java/nio/ByteBuffer.allocateDirect (I)Ljava/nio/ByteBuffer;
      // 0ca: invokestatic java/nio/ByteOrder.nativeOrder ()Ljava/nio/ByteOrder;
      // 0cd: invokevirtual java/nio/ByteBuffer.order (Ljava/nio/ByteOrder;)Ljava/nio/ByteBuffer;
      // 0d0: astore 0
      // 0d1: aload 0
      // 0d2: aload 1
      // 0d3: invokevirtual java/nio/ByteBuffer.put ([B)Ljava/nio/ByteBuffer;
      // 0d6: pop
      // 0d7: aload 0
      // 0d8: invokevirtual java/nio/ByteBuffer.rewind ()Ljava/nio/Buffer;
      // 0db: pop
      // 0dc: new org/tensorflow/lite/Interpreter
      // 0df: astore 1
      // 0e0: aload 1
      // 0e1: aload 0
      // 0e2: invokespecial org/tensorflow/lite/Interpreter.<init> (Ljava/nio/ByteBuffer;)V
      // 0e5: aload 1
      // 0e6: checkcast java/lang/AutoCloseable
      // 0e9: astore 1
      // 0ea: aload 1
      // 0eb: checkcast org/tensorflow/lite/Interpreter
      // 0ee: astore 0
      // 0ef: aload 0
      // 0f0: bipush 0
      // 0f1: invokevirtual org/tensorflow/lite/Interpreter.getInputTensor (I)Lorg/tensorflow/lite/Tensor;
      // 0f4: invokeinterface org/tensorflow/lite/Tensor.shape ()[I 1
      // 0f9: astore 7
      // 0fb: bipush 1
      // 0fc: istore 2
      // 0fd: aload 7
      // 0ff: bipush 4
      // 100: newarray 10
      // 102: dup
      // 103: bipush 0
      // 104: bipush 1
      // 105: iastore
      // 106: dup
      // 107: bipush 1
      // 108: sipush 400
      // 10b: iastore
      // 10c: dup
      // 10d: bipush 2
      // 10e: sipush 800
      // 111: iastore
      // 112: dup
      // 113: bipush 3
      // 114: bipush 3
      // 115: iastore
      // 116: invokestatic java/util/Arrays.equals ([I[I)Z
      // 119: istore 4
      // 11b: iload 4
      // 11d: ifeq 147
      // 120: aload 0
      // 121: bipush 0
      // 122: invokevirtual org/tensorflow/lite/Interpreter.getOutputTensor (I)Lorg/tensorflow/lite/Tensor;
      // 125: invokeinterface org/tensorflow/lite/Tensor.shape ()[I 1
      // 12a: bipush 2
      // 12b: newarray 10
      // 12d: dup
      // 12e: bipush 0
      // 12f: bipush 1
      // 130: iastore
      // 131: dup
      // 132: bipush 1
      // 133: bipush 8
      // 135: iastore
      // 136: invokestatic java/util/Arrays.equals ([I[I)Z
      // 139: istore 4
      // 13b: iload 4
      // 13d: ifeq 147
      // 140: goto 149
      // 143: astore 0
      // 144: goto 156
      // 147: bipush 0
      // 148: istore 2
      // 149: aload 1
      // 14a: aconst_null
      // 14b: invokestatic kotlin/jdk7/AutoCloseableKt.closeFinally (Ljava/lang/AutoCloseable;Ljava/lang/Throwable;)V
      // 14e: goto 16b
      // 151: astore 0
      // 152: goto 156
      // 155: astore 0
      // 156: aload 0
      // 157: athrow
      // 158: astore 7
      // 15a: aload 1
      // 15b: aload 0
      // 15c: invokestatic kotlin/jdk7/AutoCloseableKt.closeFinally (Ljava/lang/AutoCloseable;Ljava/lang/Throwable;)V
      // 15f: aload 7
      // 161: athrow
      // 162: astore 0
      // 163: iload 3
      // 164: istore 2
      // 165: goto 16b
      // 168: astore 0
      // 169: iload 3
      // 16a: istore 2
      // 16b: iload 2
      // 16c: ifne 17b
      // 16f: aload 5
      // 171: invokevirtual java/io/File.delete ()Z
      // 174: pop
      // 175: ldc "That .lite file isn't the lane model (wrong input / output)"
      // 177: astore 0
      // 178: goto 191
      // 17b: aload 5
      // 17d: aload 6
      // 17f: invokevirtual java/io/File.renameTo (Ljava/io/File;)Z
      // 182: pop
      // 183: aconst_null
      // 184: astore 0
      // 185: goto 191
      // 188: aload 5
      // 18a: invokevirtual java/io/File.delete ()Z
      // 18d: pop
      // 18e: ldc "That file isn't a lane model (.lite)"
      // 190: astore 0
      // 191: goto 1d9
      // 194: astore 1
      // 195: aload 1
      // 196: athrow
      // 197: astore 5
      // 199: aload 0
      // 19a: aload 1
      // 19b: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 19e: aload 5
      // 1a0: athrow
      // 1a1: astore 6
      // 1a3: aload 6
      // 1a5: athrow
      // 1a6: astore 5
      // 1a8: aload 1
      // 1a9: aload 6
      // 1ab: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 1ae: aload 5
      // 1b0: athrow
      // 1b1: astore 5
      // 1b3: aload 5
      // 1b5: athrow
      // 1b6: astore 1
      // 1b7: aload 0
      // 1b8: aload 5
      // 1ba: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 1bd: aload 1
      // 1be: athrow
      // 1bf: astore 0
      // 1c0: aload 0
      // 1c1: invokevirtual java/lang/Exception.getMessage ()Ljava/lang/String;
      // 1c4: astore 0
      // 1c5: new java/lang/StringBuilder
      // 1c8: dup
      // 1c9: invokespecial java/lang/StringBuilder.<init> ()V
      // 1cc: ldc "Import failed: "
      // 1ce: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 1d1: aload 0
      // 1d2: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 1d5: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 1d8: astore 0
      // 1d9: aload 0
      // 1da: areturn
   }
}
