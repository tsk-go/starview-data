package com.starpilot.starview;

import android.content.Context;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import kotlin.collections.ArrayDeque;
import kotlin.collections.CollectionsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;

public final class BlinkLog {
   public static final BlinkLog INSTANCE = new BlinkLog();
   private static final long MAX_BYTES = 262144L;
   private static final int MAX_LINES = 60;
   private static File file;
   private static final SimpleDateFormat fmt = new SimpleDateFormat("MMM d HH:mm:ss", Locale.US);
   private static final ArrayDeque<String> lines = new ArrayDeque<>();

   private BlinkLog() {
   }

   // $VF: synthetic method
   public static List recent$default(BlinkLog var0, int var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = 30;
      }

      return var0.recent(var1);
   }

   public final void add(String param1) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.NullPointerException: Cannot invoke "org.jetbrains.java.decompiler.code.cfg.ExceptionRangeCFG.isCircular()" because "range" is null
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.graphToStatement(DomHelper.java:84)
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:203)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 00: aload 0
      // 01: monitorenter
      // 02: aload 1
      // 03: ldc "text"
      // 05: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
      // 08: getstatic com/starpilot/starview/BlinkLog.fmt Ljava/text/SimpleDateFormat;
      // 0b: astore 3
      // 0c: new java/util/Date
      // 0f: astore 2
      // 10: aload 2
      // 11: invokespecial java/util/Date.<init> ()V
      // 14: aload 3
      // 15: aload 2
      // 16: invokevirtual java/text/SimpleDateFormat.format (Ljava/util/Date;)Ljava/lang/String;
      // 19: astore 3
      // 1a: getstatic com/starpilot/starview/Replay.INSTANCE Lcom/starpilot/starview/Replay;
      // 1d: invokevirtual com/starpilot/starview/Replay.getActive ()Z
      // 20: ifeq 35
      // 23: aload 1
      // 24: ldc "Drive replay"
      // 26: bipush 0
      // 27: bipush 2
      // 28: aconst_null
      // 29: invokestatic kotlin/text/StringsKt.startsWith$default (Ljava/lang/String;Ljava/lang/String;ZILjava/lang/Object;)Z
      // 2c: ifne 35
      // 2f: ldc "[replay] "
      // 31: astore 2
      // 32: goto 38
      // 35: ldc ""
      // 37: astore 2
      // 38: new java/lang/StringBuilder
      // 3b: astore 4
      // 3d: aload 4
      // 3f: invokespecial java/lang/StringBuilder.<init> ()V
      // 42: aload 4
      // 44: aload 3
      // 45: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 48: ldc "  "
      // 4a: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 4d: aload 2
      // 4e: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 51: aload 1
      // 52: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 55: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 58: astore 3
      // 59: getstatic com/starpilot/starview/BlinkLog.lines Lkotlin/collections/ArrayDeque;
      // 5c: aload 3
      // 5d: invokevirtual kotlin/collections/ArrayDeque.addLast (Ljava/lang/Object;)V
      // 60: getstatic com/starpilot/starview/BlinkLog.lines Lkotlin/collections/ArrayDeque;
      // 63: invokevirtual kotlin/collections/ArrayDeque.size ()I
      // 66: bipush 60
      // 68: if_icmple 75
      // 6b: getstatic com/starpilot/starview/BlinkLog.lines Lkotlin/collections/ArrayDeque;
      // 6e: invokevirtual kotlin/collections/ArrayDeque.removeFirst ()Ljava/lang/Object;
      // 71: pop
      // 72: goto 60
      // 75: getstatic com/starpilot/starview/BlinkLog.file Ljava/io/File;
      // 78: astore 2
      // 79: aload 2
      // 7a: ifnonnull 80
      // 7d: aload 0
      // 7e: monitorexit
      // 7f: return
      // 80: aload 2
      // 81: invokevirtual java/io/File.length ()J
      // 84: ldc2_w 262144
      // 87: lcmp
      // 88: ifle af
      // 8b: aload 2
      // 8c: getstatic com/starpilot/starview/BlinkLog.lines Lkotlin/collections/ArrayDeque;
      // 8f: checkcast java/lang/Iterable
      // 92: ldc "\n"
      // 94: checkcast java/lang/CharSequence
      // 97: aconst_null
      // 98: ldc "\n"
      // 9a: checkcast java/lang/CharSequence
      // 9d: bipush 0
      // 9e: aconst_null
      // 9f: aconst_null
      // a0: bipush 58
      // a2: aconst_null
      // a3: invokestatic kotlin/collections/CollectionsKt.joinToString$default (Ljava/lang/Iterable;Ljava/lang/CharSequence;Ljava/lang/CharSequence;Ljava/lang/CharSequence;ILjava/lang/CharSequence;Lkotlin/jvm/functions/Function1;ILjava/lang/Object;)Ljava/lang/String;
      // a6: aconst_null
      // a7: bipush 2
      // a8: aconst_null
      // a9: invokestatic kotlin/io/FilesKt.writeText$default (Ljava/io/File;Ljava/lang/String;Ljava/nio/charset/Charset;ILjava/lang/Object;)V
      // ac: goto cf
      // af: new java/lang/StringBuilder
      // b2: astore 1
      // b3: aload 1
      // b4: invokespecial java/lang/StringBuilder.<init> ()V
      // b7: aload 2
      // b8: aload 1
      // b9: aload 3
      // ba: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // bd: ldc "\n"
      // bf: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // c2: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // c5: aconst_null
      // c6: bipush 2
      // c7: aconst_null
      // c8: invokestatic kotlin/io/FilesKt.appendText$default (Ljava/io/File;Ljava/lang/String;Ljava/nio/charset/Charset;ILjava/lang/Object;)V
      // cb: goto cf
      // ce: astore 1
      // cf: aload 0
      // d0: monitorexit
      // d1: return
      // d2: astore 1
      // d3: aload 0
      // d4: monitorexit
      // d5: aload 1
      // d6: athrow
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   public final void init(Context var1) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      if (file == null) {
         file = new File(var1.getFilesDir(), "blinker_log.txt");

         try {
            var9 = file;
         } catch (Exception var8) {
            return;
         }

         if (var9 != null) {
            label57: {
               try {
                  if (var9.isFile()) {
                     break label57;
                  }
               } catch (Exception var7) {
                  return;
               }

               var9 = null;
            }

            if (var9 != null) {
               try {
                  var10 = FilesKt.readLines$default(var9, null, 1, null);
               } catch (Exception var6) {
                  return;
               }

               if (var10 != null) {
                  try {
                     var11 = CollectionsKt.takeLast(var10, 60);
                  } catch (Exception var5) {
                     return;
                  }

                  if (var11 != null) {
                     Iterator var2;
                     try {
                        var2 = var11.iterator();
                     } catch (Exception var4) {
                        return;
                     }

                     while (true) {
                        try {
                           if (!var2.hasNext()) {
                              break;
                           }

                           String var12 = (String)var2.next();
                           lines.addLast(var12);
                        } catch (Exception var3) {
                           break;
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public final List<String> recent(int param1) {
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
      // 00: aload 0
      // 01: monitorenter
      // 02: getstatic com/starpilot/starview/BlinkLog.lines Lkotlin/collections/ArrayDeque;
      // 05: checkcast java/lang/Iterable
      // 08: invokestatic kotlin/collections/CollectionsKt.toList (Ljava/lang/Iterable;)Ljava/util/List;
      // 0b: iload 1
      // 0c: invokestatic kotlin/collections/CollectionsKt.takeLast (Ljava/util/List;I)Ljava/util/List;
      // 0f: checkcast java/lang/Iterable
      // 12: invokestatic kotlin/collections/CollectionsKt.reversed (Ljava/lang/Iterable;)Ljava/util/List;
      // 15: astore 2
      // 16: aload 0
      // 17: monitorexit
      // 18: aload 2
      // 19: areturn
      // 1a: astore 2
      // 1b: aload 0
      // 1c: monitorexit
      // 1d: aload 2
      // 1e: athrow
   }
}
