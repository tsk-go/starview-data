package com.starpilot.starview;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioTrack;
import android.media.AudioTrack.Builder;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

public final class AmyVoice {
   public static final AmyVoice INSTANCE = new AmyVoice();
   private static Context app;
   private static volatile long busyUntil;
   private static final HashMap<String, float[]> cache = new HashMap<>();
   private static volatile int demoGen;
   private static volatile File dirC;
   private static final ExecutorService exec = Executors.newSingleThreadExecutor();
   private static volatile int gen;
   private static final Handler main = new Handler(Looper.getMainLooper());
   private static volatile long probedAt;
   private static volatile AudioTrack track;

   // $VF: synthetic method
   public static List $r8$lambda$7FC8GQa5b_kJNJaII_Gwt_CO4wA/* $VF was: $r8$lambda$7FC8GQa5b_kJNJaII_Gwt_CO4wA*/(List var0) {
      return play$lambda$22(var0);
   }

   // $VF: synthetic method
   public static List $r8$lambda$HNfNzZP56dY1ICBPZk6EYNcKffI() {
      return ping$lambda$25();
   }

   // $VF: synthetic method
   public static void $r8$lambda$P7DMIotd5lDZd6Et8_aEoKrEsqk(int var0, int var1, List var2) {
      demo$step$next$lambda$27(var0, var1, var2);
   }

   // $VF: synthetic method
   public static void $r8$lambda$Z6J_sjI1AB4QSQdxfiANfQy3Y_s/* $VF was: $r8$lambda$Z6J_sjI1AB4QSQdxfiANfQy3Y_s*/(int var0, int var1, List var2) {
      demo$step$next$lambda$26(var0, var1, var2);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$cff_joABuhRmdvZHMP4REhesJaQ/* $VF was: $r8$lambda$cff_joABuhRmdvZHMP4REhesJaQ*/(File var0) {
      return recordings$lambda$20(var0);
   }

   // $VF: synthetic method
   public static List $r8$lambda$jqaPUS2N_A5yxuSR8KLJRZE64Eg/* $VF was: $r8$lambda$jqaPUS2N_A5yxuSR8KLJRZE64Eg*/(List var0) {
      return playClips$lambda$23(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$nja1Fmwj2D0rUptMJNy5P50zBr4(int var0, AudioTrack var1) {
      start$lambda$29(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$uNXCKLA3DMcLjTT9XO8YuQ05f2I(int var0, int var1, List var2) {
      demo$step$lambda$28(var0, var1, var2);
   }

   // $VF: synthetic method
   public static void $r8$lambda$xilqNl0Ol_s2JAvZ8Op_Wnt4oHY/* $VF was: $r8$lambda$xilqNl0Ol_s2JAvZ8Op_Wnt4oHY*/(int var0, Function0 var1, AmySynth.Style var2) {
      playWith$lambda$24(var0, var1, var2);
   }

   private AmyVoice() {
   }

   // $VF: synthetic method
   public static boolean busy$default(AmyVoice var0, long var1, int var3, Object var4) {
      if ((var3 & 1) != 0) {
         var1 = System.currentTimeMillis();
      }

      return var0.busy(var1);
   }

   private final float[] clip(String param1) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.NullPointerException: Cannot invoke "org.jetbrains.java.decompiler.code.cfg.ExceptionRangeCFG.isCircular()" because "range" is null
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.graphToStatement(DomHelper.java:84)
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:203)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 00: getstatic com/starpilot/starview/AmyVoice.cache Ljava/util/HashMap;
      // 03: astore 5
      // 05: aload 5
      // 07: monitorenter
      // 08: getstatic com/starpilot/starview/AmyVoice.cache Ljava/util/HashMap;
      // 0b: aload 1
      // 0c: invokevirtual java/util/HashMap.get (Ljava/lang/Object;)Ljava/lang/Object;
      // 0f: checkcast [F
      // 12: astore 3
      // 13: aload 3
      // 14: astore 2
      // 15: aload 3
      // 16: ifnonnull 90
      // 19: aconst_null
      // 1a: astore 4
      // 1c: getstatic com/starpilot/starview/AmyVoice.INSTANCE Lcom/starpilot/starview/AmyVoice;
      // 1f: invokespecial com/starpilot/starview/AmyVoice.dir ()Ljava/io/File;
      // 22: astore 6
      // 24: aload 4
      // 26: astore 2
      // 27: aload 6
      // 29: ifnull 90
      // 2c: new java/io/File
      // 2f: astore 3
      // 30: new java/lang/StringBuilder
      // 33: astore 2
      // 34: aload 2
      // 35: invokespecial java/lang/StringBuilder.<init> ()V
      // 38: aload 3
      // 39: aload 6
      // 3b: aload 2
      // 3c: aload 1
      // 3d: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 40: ldc ".wav"
      // 42: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 45: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 48: invokespecial java/io/File.<init> (Ljava/io/File;Ljava/lang/String;)V
      // 4b: aload 3
      // 4c: invokevirtual java/io/File.isFile ()Z
      // 4f: ifeq 55
      // 52: goto 57
      // 55: aconst_null
      // 56: astore 3
      // 57: aload 4
      // 59: astore 2
      // 5a: aload 3
      // 5b: ifnull 90
      // 5e: aload 3
      // 5f: invokestatic kotlin/io/FilesKt.readBytes (Ljava/io/File;)[B
      // 62: astore 3
      // 63: aload 4
      // 65: astore 2
      // 66: aload 3
      // 67: ifnull 90
      // 6a: getstatic com/starpilot/starview/AmySynth.INSTANCE Lcom/starpilot/starview/AmySynth;
      // 6d: aload 3
      // 6e: invokevirtual com/starpilot/starview/AmySynth.wavFloats ([B)[F
      // 71: astore 3
      // 72: aload 4
      // 74: astore 2
      // 75: aload 3
      // 76: ifnull 90
      // 79: getstatic com/starpilot/starview/AmyVoice.cache Ljava/util/HashMap;
      // 7c: checkcast java/util/Map
      // 7f: aload 1
      // 80: aload 3
      // 81: invokeinterface java/util/Map.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
      // 86: pop
      // 87: aload 3
      // 88: astore 2
      // 89: goto 90
      // 8c: astore 1
      // 8d: aload 4
      // 8f: astore 2
      // 90: aload 5
      // 92: monitorexit
      // 93: aload 2
      // 94: areturn
      // 95: astore 1
      // 96: aload 5
      // 98: monitorexit
      // 99: aload 1
      // 9a: athrow
   }

   private static final void demo$step(int var0, List<? extends Pair<? extends AmySynth.Style, ? extends List<String>>> var1, int var2) {
      if (var0 == demoGen && var2 < var1.size()) {
         INSTANCE.play((AmySynth.Style)((Pair)var1.get(var2)).getFirst(), (List<String>)((Pair)var1.get(var2)).getSecond());
         main.postDelayed(new AmyVoice$$ExternalSyntheticLambda0(var0, var2, var1), 400L);
      }
   }

   private static final void demo$step$lambda$28(int var0, int var1, List var2) {
      demo$step$next(var0, var1, var2);
   }

   private static final void demo$step$next(int var0, int var1, List<? extends Pair<? extends AmySynth.Style, ? extends List<String>>> var2) {
      if (var0 == demoGen) {
         long var3;
         Handler var7;
         Object var8;
         if (busy$default(INSTANCE, 0L, 1, null)) {
            var8 = main;
            AmyVoice$$ExternalSyntheticLambda3 var6 = new AmyVoice$$ExternalSyntheticLambda3(var0, var1, var2);
            var3 = 100L;
            var7 = (Handler)var8;
            var8 = var6;
         } else {
            Handler var9 = main;
            var8 = new AmyVoice$$ExternalSyntheticLambda4(var1, var0, var2);
            var3 = 700L;
            var7 = var9;
         }

         var7.postDelayed((Runnable)var8, var3);
      }
   }

   private static final void demo$step$next$lambda$26(int var0, int var1, List var2) {
      demo$step$next(var0, var1, var2);
   }

   private static final void demo$step$next$lambda$27(int var0, int var1, List var2) {
      demo$step(var1, var2, var0 + 1);
   }

   private final File dir() {
      File var3 = dirC;
      if (var3 != null && INSTANCE.ok(var3)) {
         return var3;
      } else {
         Context var7 = app;
         File var4 = null;
         if (var7 == null) {
            return null;
         } else {
            long var1 = System.currentTimeMillis();
            if (var1 - probedAt < 5000L) {
               return null;
            } else {
               probedAt = var1;
               List var6 = this.packs();
               Iterator var5 = var6.iterator();

               do {
                  if (!var5.hasNext()) {
                     var8 = null;
                     break;
                  }

                  var8 = var5.next();
               } while (!Intrinsics.areEqual(((AmyVoice.Pack)var8).getId(), INSTANCE.getActiveId()));

               AmyVoice.Pack var10 = (AmyVoice.Pack)var8;
               AmyVoice.Pack var9 = var10;
               if (var10 == null) {
                  var9 = CollectionsKt.firstOrNull(var6);
               }

               if (var9 != null) {
                  var4 = var9.getDir();
               }

               dirC = var4;
               return dirC;
            }
         }
      }
   }

   private final SharedPreferences getPrefs() {
      Context var1 = app;
      Intrinsics.checkNotNull(var1);
      return var1.getSharedPreferences("speech", 0);
   }

   private final boolean ok(File var1) {
      boolean var2;
      if (var1 != null && new File(var1, "ask_left.wav").isFile()) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   private static final List ping$lambda$25() {
      return CollectionsKt.emptyList();
   }

   private static final List play$lambda$22(List var0) {
      Iterable var1 = var0;
      var var3 = new ArrayList();

      for (String var2 : var1) {
         float[] var5 = INSTANCE.clip(var2);
         if (var5 != null) {
            var3.add(var5);
         }
      }

      return var3;
   }

   private static final List playClips$lambda$23(List var0) {
      return var0;
   }

   private final void playWith(AmySynth.Style var1, Function0<? extends List<float[]>> var2) {
      gen++;
      int var3 = gen;
      busyUntil = System.currentTimeMillis() + 3000;
      exec.execute(new AmyVoice$$ExternalSyntheticLambda6(var3, var2, var1));
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   private static final void playWith$lambda$24(int var0, Function0 var1, AmySynth.Style var2) {
      if (var0 == gen) {
         label56: {
            label48: {
               try {
                  var7 = (List)var1.invoke();
                  if (var7.isEmpty()) {
                     var8 = new float[0];
                     break label48;
                  }
               } catch (Exception var6) {
                  if (var0 != gen) {
                     return;
                  }
                  break label56;
               }

               try {
                  var8 = AmySynth.INSTANCE.voice(var7, Speech.INSTANCE.getSpeed(), Speech.INSTANCE.getPitch());
               } catch (Exception var4) {
                  if (var0 != gen) {
                     return;
                  }
                  break label56;
               }
            }

            try {
               var9 = AmySynth.INSTANCE.render(var2, var8, Speech.INSTANCE.getChime(), Speech.INSTANCE.getPad());
               if (var0 != gen) {
                  return;
               }
            } catch (Exception var5) {
               if (var0 != gen) {
                  return;
               }
               break label56;
            }

            try {
               INSTANCE.start(var9, var0);
               return;
            } catch (Exception var3) {
               if (var0 != gen) {
                  return;
               }
            }
         }

         busyUntil = 0L;
      }
   }

   // $VF: synthetic method
   public static int recordings$default(AmyVoice var0, AmyVoice.Pack var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = var0.active();
      }

      return var0.recordings(var1);
   }

   private static final boolean recordings$lambda$20(File var0) {
      String var1 = var0.getName();
      Intrinsics.checkNotNullExpressionValue(var1, "getName(...)");
      return StringsKt.endsWith$default(var1, ".wav", false, 2, null);
   }

   private final void release() {
      AudioTrack var1 = track;
      track = null;
      if (var1 != null) {
         try {
            var1.stop();
         } catch (Exception var4) {
         }
      }

      if (var1 != null) {
         try {
            var1.release();
         } catch (Exception var3) {
         }
      }
   }

   private final void start(short[] var1, int var2) {
      this.release();
      AudioTrack var5 = new Builder()
         .setAudioAttributes(new android.media.AudioAttributes.Builder().setUsage(4).setContentType(4).build())
         .setAudioFormat(new android.media.AudioFormat.Builder().setEncoding(2).setSampleRate(48000).setChannelMask(12).build())
         .setTransferMode(0)
         .setBufferSizeInBytes(var1.length * 2)
         .build();
      Intrinsics.checkNotNullExpressionValue(var5, "build(...)");
      var5.write(var1, 0, var1.length);
      track = var5;
      long var3 = var1.length / 2 * 1000L / '뮀';
      busyUntil = System.currentTimeMillis() + var3;
      var5.play();
      main.postDelayed(new AmyVoice$$ExternalSyntheticLambda2(var2, var5), 300 + var3);
   }

   private static final void start$lambda$29(int var0, AudioTrack var1) {
      if (var0 == gen && track == var1) {
         INSTANCE.release();
      }
   }

   public final AmyVoice.Pack active() {
      File var4 = this.dir();
      Object var2 = null;
      if (var4 == null) {
         return null;
      } else {
         Iterator var3 = this.packs().iterator();

         Object var1;
         do {
            if (!var3.hasNext()) {
               var1 = var2;
               break;
            }

            var1 = var3.next();
         } while (!Intrinsics.areEqual(((AmyVoice.Pack)var1).getDir(), var4));

         return (AmyVoice.Pack)var1;
      }
   }

   public final boolean busy(long var1) {
      boolean var3;
      if (var1 < busyUntil) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public final void delete(String var1) {
      Intrinsics.checkNotNullParameter(var1, "id");
      Iterator var3 = this.packs().iterator();

      Object var2;
      do {
         if (!var3.hasNext()) {
            var2 = null;
            break;
         }

         var2 = var3.next();
      } while (!Intrinsics.areEqual(((AmyVoice.Pack)var2).getId(), var1));

      var2 = (AmyVoice.Pack)var2;
      if (var2 != null) {
         StringsKt.startsWith$default(((AmyVoice.Pack)var2).getId(), "gen:", false, 2, null);
         FilesKt.deleteRecursively(((AmyVoice.Pack)var2).getDir());
         if (Intrinsics.areEqual(this.getActiveId(), var1)) {
            var1 = "";
         } else {
            var1 = this.getActiveId();
         }

         this.setActiveId(var1);
      }
   }

   public final void demo() {
      this.stop();
      int var1 = demoGen;
      Pair var3 = TuplesKt.to(AmySynth.Style.ASK, AmyPrompts.INSTANCE.ask(1, "exit on the right in 0.6 mi - 2 lanes to go"));
      Pair var2 = TuplesKt.to(AmySynth.Style.SHORT, CollectionsKt.listOf("sig_right"));
      Pair var4 = TuplesKt.to(AmySynth.Style.SHORT, CollectionsKt.listOf("changing"));
      demo$step(var1, CollectionsKt.listOf(new Pair[]{var3, var2, var4, TuplesKt.to(AmySynth.Style.DONE, CollectionsKt.listOf("done"))}), 0);
   }

   public final String getActiveId() {
      Context var2 = app;
      String var1 = "";
      if (var2 != null) {
         String var3 = this.getPrefs().getString("pack", "");
         if (var3 != null) {
            var1 = var3;
         }
      }

      return var1;
   }

   public final long getBusyUntil() {
      return busyUntil;
   }

   public final boolean getInstalled() {
      boolean var1;
      if (this.dir() != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public final boolean getReady() {
      return this.getInstalled();
   }

   public final void init(Context var1) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      if (app == null) {
         app = var1.getApplicationContext();
      }
   }

   public final String install(Context param1, Uri param2) {
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
      // 00e: new java/io/File
      // 011: dup
      // 012: aload 1
      // 013: invokevirtual android/content/Context.getFilesDir ()Ljava/io/File;
      // 016: ldc_w "amy.tmp"
      // 019: invokespecial java/io/File.<init> (Ljava/io/File;Ljava/lang/String;)V
      // 01c: astore 5
      // 01e: aload 5
      // 020: invokestatic kotlin/io/FilesKt.deleteRecursively (Ljava/io/File;)Z
      // 023: pop
      // 024: aload 5
      // 026: invokevirtual java/io/File.mkdirs ()Z
      // 029: pop
      // 02a: bipush 0
      // 02b: istore 3
      // 02c: aload 1
      // 02d: invokevirtual android/content/Context.getContentResolver ()Landroid/content/ContentResolver;
      // 030: aload 2
      // 031: invokevirtual android/content/ContentResolver.openInputStream (Landroid/net/Uri;)Ljava/io/InputStream;
      // 034: astore 2
      // 035: aload 2
      // 036: ifnull 1da
      // 039: aload 2
      // 03a: checkcast java/io/Closeable
      // 03d: astore 6
      // 03f: aload 6
      // 041: checkcast java/io/InputStream
      // 044: astore 2
      // 045: new java/util/zip/ZipInputStream
      // 048: astore 7
      // 04a: aload 2
      // 04b: instanceof java/io/BufferedInputStream
      // 04e: ifeq 05a
      // 051: aload 2
      // 052: checkcast java/io/BufferedInputStream
      // 055: astore 4
      // 057: goto 067
      // 05a: new java/io/BufferedInputStream
      // 05d: dup
      // 05e: aload 2
      // 05f: sipush 8192
      // 062: invokespecial java/io/BufferedInputStream.<init> (Ljava/io/InputStream;I)V
      // 065: astore 4
      // 067: aload 7
      // 069: aload 4
      // 06b: checkcast java/io/InputStream
      // 06e: invokespecial java/util/zip/ZipInputStream.<init> (Ljava/io/InputStream;)V
      // 071: aload 7
      // 073: checkcast java/io/Closeable
      // 076: astore 7
      // 078: aload 2
      // 079: astore 4
      // 07b: aload 7
      // 07d: checkcast java/util/zip/ZipInputStream
      // 080: astore 8
      // 082: aload 2
      // 083: astore 4
      // 085: aload 8
      // 087: invokevirtual java/util/zip/ZipInputStream.getNextEntry ()Ljava/util/zip/ZipEntry;
      // 08a: astore 9
      // 08c: aload 9
      // 08e: ifnonnull 116
      // 091: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 094: astore 2
      // 095: aload 7
      // 097: aconst_null
      // 098: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 09b: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 09e: astore 2
      // 09f: aload 6
      // 0a1: aconst_null
      // 0a2: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 0a5: new java/io/File
      // 0a8: dup
      // 0a9: aload 5
      // 0ab: ldc_w "ask_left.wav"
      // 0ae: invokespecial java/io/File.<init> (Ljava/io/File;Ljava/lang/String;)V
      // 0b1: invokevirtual java/io/File.isFile ()Z
      // 0b4: ifne 0c1
      // 0b7: aload 5
      // 0b9: invokestatic kotlin/io/FilesKt.deleteRecursively (Ljava/io/File;)Z
      // 0bc: pop
      // 0bd: ldc_w "That zip has no Amy recordings in it - pick StarView-Amy-voice.zip"
      // 0c0: areturn
      // 0c1: new java/io/File
      // 0c4: dup
      // 0c5: aload 1
      // 0c6: invokevirtual android/content/Context.getFilesDir ()Ljava/io/File;
      // 0c9: ldc_w "amy"
      // 0cc: invokespecial java/io/File.<init> (Ljava/io/File;Ljava/lang/String;)V
      // 0cf: astore 1
      // 0d0: aload 1
      // 0d1: invokestatic kotlin/io/FilesKt.deleteRecursively (Ljava/io/File;)Z
      // 0d4: pop
      // 0d5: aload 5
      // 0d7: aload 1
      // 0d8: invokevirtual java/io/File.renameTo (Ljava/io/File;)Z
      // 0db: ifne 0e8
      // 0de: aload 5
      // 0e0: invokestatic kotlin/io/FilesKt.deleteRecursively (Ljava/io/File;)Z
      // 0e3: pop
      // 0e4: ldc_w "Couldn't save it"
      // 0e7: areturn
      // 0e8: ldc_w "AmyVoice"
      // 0eb: new java/lang/StringBuilder
      // 0ee: dup
      // 0ef: invokespecial java/lang/StringBuilder.<init> ()V
      // 0f2: ldc_w "installed "
      // 0f5: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 0f8: iload 3
      // 0f9: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
      // 0fc: ldc_w " recordings"
      // 0ff: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 102: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 105: invokestatic android/util/Log.i (Ljava/lang/String;Ljava/lang/String;)I
      // 108: pop
      // 109: aload 0
      // 10a: ldc_w "amy"
      // 10d: invokevirtual com/starpilot/starview/AmyVoice.use (Ljava/lang/String;)V
      // 110: aconst_null
      // 111: areturn
      // 112: astore 1
      // 113: goto 1bf
      // 116: aload 9
      // 118: invokevirtual java/util/zip/ZipEntry.getName ()Ljava/lang/String;
      // 11b: astore 4
      // 11d: aload 4
      // 11f: ldc_w "getName(...)"
      // 122: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 125: aload 4
      // 127: bipush 47
      // 129: aconst_null
      // 12a: bipush 2
      // 12b: aconst_null
      // 12c: invokestatic kotlin/text/StringsKt.substringAfterLast$default (Ljava/lang/String;CLjava/lang/String;ILjava/lang/Object;)Ljava/lang/String;
      // 12f: astore 4
      // 131: aload 9
      // 133: invokevirtual java/util/zip/ZipEntry.isDirectory ()Z
      // 136: ifne 1b4
      // 139: new kotlin/text/Regex
      // 13c: astore 9
      // 13e: aload 9
      // 140: ldc_w "[a-z0-9_]+\\.wav"
      // 143: invokespecial kotlin/text/Regex.<init> (Ljava/lang/String;)V
      // 146: aload 9
      // 148: aload 4
      // 14a: checkcast java/lang/CharSequence
      // 14d: invokevirtual kotlin/text/Regex.matches (Ljava/lang/CharSequence;)Z
      // 150: ifeq 1b4
      // 153: new java/io/FileOutputStream
      // 156: astore 10
      // 158: new java/io/File
      // 15b: astore 9
      // 15d: aload 9
      // 15f: aload 5
      // 161: aload 4
      // 163: invokespecial java/io/File.<init> (Ljava/io/File;Ljava/lang/String;)V
      // 166: aload 10
      // 168: aload 9
      // 16a: invokespecial java/io/FileOutputStream.<init> (Ljava/io/File;)V
      // 16d: aload 10
      // 16f: checkcast java/io/Closeable
      // 172: astore 4
      // 174: aload 4
      // 176: checkcast java/io/FileOutputStream
      // 179: astore 10
      // 17b: aload 8
      // 17d: checkcast java/io/InputStream
      // 180: astore 9
      // 182: aload 10
      // 184: checkcast java/io/OutputStream
      // 187: astore 10
      // 189: aload 9
      // 18b: aload 10
      // 18d: bipush 0
      // 18e: bipush 2
      // 18f: aconst_null
      // 190: invokestatic kotlin/io/ByteStreamsKt.copyTo$default (Ljava/io/InputStream;Ljava/io/OutputStream;IILjava/lang/Object;)J
      // 193: pop2
      // 194: aload 4
      // 196: aconst_null
      // 197: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 19a: iinc 3 1
      // 19d: goto 082
      // 1a0: astore 1
      // 1a1: goto 1a5
      // 1a4: astore 1
      // 1a5: aload 1
      // 1a6: athrow
      // 1a7: astore 2
      // 1a8: aload 4
      // 1aa: aload 1
      // 1ab: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 1ae: aload 2
      // 1af: athrow
      // 1b0: astore 1
      // 1b1: goto 1bf
      // 1b4: goto 082
      // 1b7: astore 1
      // 1b8: goto 1bf
      // 1bb: astore 1
      // 1bc: aload 4
      // 1be: astore 2
      // 1bf: aload 1
      // 1c0: athrow
      // 1c1: astore 2
      // 1c2: aload 7
      // 1c4: aload 1
      // 1c5: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 1c8: aload 2
      // 1c9: athrow
      // 1ca: astore 1
      // 1cb: goto 1cf
      // 1ce: astore 1
      // 1cf: aload 1
      // 1d0: athrow
      // 1d1: astore 2
      // 1d2: aload 6
      // 1d4: aload 1
      // 1d5: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 1d8: aload 2
      // 1d9: athrow
      // 1da: ldc_w "Couldn't open that file"
      // 1dd: areturn
      // 1de: astore 1
      // 1df: goto 1e3
      // 1e2: astore 1
      // 1e3: aload 5
      // 1e5: invokestatic kotlin/io/FilesKt.deleteRecursively (Ljava/io/File;)Z
      // 1e8: pop
      // 1e9: aload 1
      // 1ea: invokevirtual java/lang/Exception.getMessage ()Ljava/lang/String;
      // 1ed: astore 1
      // 1ee: new java/lang/StringBuilder
      // 1f1: dup
      // 1f2: invokespecial java/lang/StringBuilder.<init> ()V
      // 1f5: ldc_w "Not a voice pack zip ("
      // 1f8: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 1fb: aload 1
      // 1fc: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 1ff: ldc_w ")"
      // 202: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 205: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 208: areturn
   }

   public final List<AmyVoice.Pack> packs() {
      Context var4 = app;
      if (var4 == null) {
         return CollectionsKt.emptyList();
      } else {
         ArrayList var5 = new ArrayList();
         File var6 = new File(var4.getFilesDir(), "amy");
         int var1 = 0;
         File var3 = var4.getExternalFilesDir(null);
         if (var3 != null) {
            var3 = new File(var3, "amy");
         } else {
            var3 = null;
         }

         Iterator var21 = CollectionsKt.listOf(new File[]{var6, var3}).iterator();

         File var7;
         do {
            if (!var21.hasNext()) {
               var3 = null;
               break;
            }

            var3 = (File)var21.next();
            var7 = var3;
         } while (!INSTANCE.ok(var7));

         var3 = var3;
         if (var3 != null) {
            var5.add(new AmyVoice.Pack("amy", "Amy · recorded on the PC", var3));
         }

         File[] var22 = new File(var4.getFilesDir(), "voices").listFiles();
         if (var22 != null) {
            var var12 = new ArrayList();

            for (int var2 = var22.length; var1 < var2; var1++) {
               File var19 = var22[var1];
               if (INSTANCE.ok(var19)) {
                  var12.add(var19);
               }
            }

            List var13 = var12;
            List var14 = CollectionsKt.sortedWith(var13, new Comparator() {
               @Override
               public final int compare(T var1, T var2x) {
                  return ComparisonsKt.compareValues(((File)var1).lastModified(), ((File)var2x).lastModified());
               }
            });
            if (var14 != null) {
               for (File var24 : var14) {
                  String var15 = var24.getName();
                  String var8 = "gen:" + var15;
                  var3 = new File(var24, "label.txt");
                  if (!var3.isFile()) {
                     var3 = null;
                  }

                  label47: {
                     if (var3 != null) {
                        String var17 = FilesKt.readText$default(var3, null, 1, null);
                        if (var17 != null) {
                           String var20 = StringsKt.trim(var17).toString();
                           var18 = var20;
                           if (var20 != null) {
                              break label47;
                           }
                        }
                     }

                     var18 = var24.getName();
                  }

                  Intrinsics.checkNotNull(var18);
                  Intrinsics.checkNotNull(var24);
                  var5.add(new AmyVoice.Pack(var8, var18, var24));
               }
            }
         }

         return var5;
      }
   }

   public final void ping() {
      this.playWith(AmySynth.Style.PING, new AmyVoice$$ExternalSyntheticLambda5());
   }

   public final void play(AmySynth.Style var1, List<String> var2) {
      Intrinsics.checkNotNullParameter(var1, "style");
      Intrinsics.checkNotNullParameter(var2, "keys");
      if (this.getInstalled()) {
         this.playWith(var1, new AmyVoice$$ExternalSyntheticLambda1(var2));
      }
   }

   public final void playClips(AmySynth.Style var1, List<float[]> var2) {
      Intrinsics.checkNotNullParameter(var1, "style");
      Intrinsics.checkNotNullParameter(var2, "clips");
      this.playWith(var1, new AmyVoice$$ExternalSyntheticLambda7(var2));
   }

   public final int recordings(AmyVoice.Pack var1) {
      if (var1 != null) {
         File var3 = var1.getDir();
         if (var3 != null) {
            File[] var4 = var3.listFiles(new AmyVoice$$ExternalSyntheticLambda8());
            if (var4 != null) {
               return var4.length;
            }
         }
      }

      return 0;
   }

   public final void setActiveId(String var1) {
      Intrinsics.checkNotNullParameter(var1, "v");
      this.getPrefs().edit().putString("pack", var1).apply();
      dirC = null;
      probedAt = 0L;
      HashMap var5 = cache;
      synchronized (var5) {
         cache.clear();
         Unit var2 = Unit.INSTANCE;
      }
   }

   public final void stop() {
      gen++;
      demoGen++;
      this.release();
      busyUntil = 0L;
   }

   public final void use(String var1) {
      Intrinsics.checkNotNullParameter(var1, "id");
      this.setActiveId(var1);
      Speech.INSTANCE.setUseAmy(true);
   }

   public static final class Pack {
      private final File dir;
      private final String id;
      private final String label;

      public Pack(String var1, String var2, File var3) {
         Intrinsics.checkNotNullParameter(var1, "id");
         Intrinsics.checkNotNullParameter(var2, "label");
         Intrinsics.checkNotNullParameter(var3, "dir");
         super();
         this.id = var1;
         this.label = var2;
         this.dir = var3;
      }

      // $VF: synthetic method
      public static AmyVoice.Pack copy$default(AmyVoice.Pack var0, String var1, String var2, File var3, int var4, Object var5) {
         if ((var4 & 1) != 0) {
            var1 = var0.id;
         }

         if ((var4 & 2) != 0) {
            var2 = var0.label;
         }

         if ((var4 & 4) != 0) {
            var3 = var0.dir;
         }

         return var0.copy(var1, var2, var3);
      }

      public final String component1() {
         return this.id;
      }

      public final String component2() {
         return this.label;
      }

      public final File component3() {
         return this.dir;
      }

      public final AmyVoice.Pack copy(String var1, String var2, File var3) {
         Intrinsics.checkNotNullParameter(var1, "id");
         Intrinsics.checkNotNullParameter(var2, "label");
         Intrinsics.checkNotNullParameter(var3, "dir");
         return new AmyVoice.Pack(var1, var2, var3);
      }

      @Override
      public boolean equals(Object var1) {
         if (this == var1) {
            return true;
         } else if (!(var1 instanceof AmyVoice.Pack)) {
            return false;
         } else {
            var1 = var1;
            if (!Intrinsics.areEqual(this.id, var1.id)) {
               return false;
            } else {
               return !Intrinsics.areEqual(this.label, var1.label) ? false : Intrinsics.areEqual(this.dir, var1.dir);
            }
         }
      }

      public final File getDir() {
         return this.dir;
      }

      public final String getId() {
         return this.id;
      }

      public final String getLabel() {
         return this.label;
      }

      @Override
      public int hashCode() {
         return (this.id.hashCode() * 31 + this.label.hashCode()) * 31 + this.dir.hashCode();
      }

      @Override
      public String toString() {
         String var2 = this.id;
         String var1 = this.label;
         File var3 = this.dir;
         return "Pack(id=" + var2 + ", label=" + var1 + ", dir=" + var3 + ")";
      }
   }
}
