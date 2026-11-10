package com.starpilot.starview;

import android.content.Context;
import android.net.Uri;
import com.k2fsa.sherpa.onnx.OfflineTts;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.io.TextStreamsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.OkHttpClient.Builder;
import org.json.JSONObject;

public final class VoiceStudio {
   private static final String HF = "https://huggingface.co/rhasspy/piper-voices/resolve/main/";
   public static final VoiceStudio INSTANCE = new VoiceStudio();
   private static final Lazy http$delegate = LazyKt.lazy(new VoiceStudio$$ExternalSyntheticLambda3());
   private static volatile boolean loaded;
   private static OfflineTts tts;
   private static String ttsKey = "";

   // $VF: synthetic method
   public static OkHttpClient $r8$lambda$1LMOINOsjc9BQVSFDcFbvYMBXL4() {
      return http_delegate$lambda$0();
   }

   // $VF: synthetic method
   public static Comparable $r8$lambda$APD_Zo2opaNjgGSMX1h6Y0Zp7Bo(VoiceStudio.Voice var0) {
      return catalog$lambda$8(var0);
   }

   // $VF: synthetic method
   public static Comparable $r8$lambda$BS4N5Mu89xHXNtbZhrkd8n20DVo(VoiceStudio.Voice var0) {
      return catalog$lambda$7(var0);
   }

   // $VF: synthetic method
   public static Comparable $r8$lambda$WvWzdxhQ2yiGnV1y3E8JDI_aSuI/* $VF was: $r8$lambda$WvWzdxhQ2yiGnV1y3E8JDI_aSuI*/(Map var0, VoiceStudio.Voice var1) {
      return catalog$lambda$9(var0, var1);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$ctAQ7udyLllW6isSb1y2kigGsB8(File var0) {
      return downloadedBytes$lambda$10(var0);
   }

   private VoiceStudio() {
   }

   private final File catFile(Context var1) {
      return new File(var1.getFilesDir(), "piper/voices.json");
   }

   // $VF: synthetic method
   public static List catalog$default(VoiceStudio var0, Context var1, boolean var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = false;
      }

      return var0.catalog(var1, var2);
   }

   private static final Comparable catalog$lambda$7(VoiceStudio.Voice var0) {
      Intrinsics.checkNotNullParameter(var0, "it");
      return Intrinsics.areEqual(var0.getRegion(), "US") ^ true;
   }

   private static final Comparable catalog$lambda$8(VoiceStudio.Voice var0) {
      Intrinsics.checkNotNullParameter(var0, "it");
      return var0.getName();
   }

   private static final Comparable catalog$lambda$9(Map var0, VoiceStudio.Voice var1) {
      Intrinsics.checkNotNullParameter(var1, "it");
      Integer var2 = (Integer)var0.get(var1.getQuality());
      if (var2 == null) {
         var2 = 9;
      }

      return var2;
   }

   private static final boolean downloadedBytes$lambda$10(File var0) {
      Intrinsics.checkNotNullParameter(var0, "it");
      boolean var1;
      if (var0.isFile() && Intrinsics.areEqual(var0.getName(), "model.onnx")) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private final OfflineTts engine(Context param1, String param2, VoiceStudio.Style param3) {
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
      // 001: monitorenter
      // 002: aload 0
      // 003: aload 1
      // 004: invokevirtual com/starpilot/starview/VoiceStudio.load (Landroid/content/Context;)Ljava/lang/String;
      // 007: astore 8
      // 009: aload 8
      // 00b: ifnonnull 138
      // 00e: aload 3
      // 00f: invokevirtual com/starpilot/starview/VoiceStudio$Style.getExpressive ()F
      // 012: fstore 6
      // 014: aload 3
      // 015: invokevirtual com/starpilot/starview/VoiceStudio$Style.getRhythm ()F
      // 018: fstore 4
      // 01a: aload 3
      // 01b: invokevirtual com/starpilot/starview/VoiceStudio$Style.getPace ()F
      // 01e: fstore 5
      // 020: new java/lang/StringBuilder
      // 023: astore 8
      // 025: aload 8
      // 027: invokespecial java/lang/StringBuilder.<init> ()V
      // 02a: aload 8
      // 02c: aload 2
      // 02d: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 030: ldc "|"
      // 032: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 035: fload 6
      // 037: invokevirtual java/lang/StringBuilder.append (F)Ljava/lang/StringBuilder;
      // 03a: ldc "|"
      // 03c: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 03f: fload 4
      // 041: invokevirtual java/lang/StringBuilder.append (F)Ljava/lang/StringBuilder;
      // 044: ldc "|"
      // 046: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 049: fload 5
      // 04b: invokevirtual java/lang/StringBuilder.append (F)Ljava/lang/StringBuilder;
      // 04e: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 051: astore 8
      // 053: getstatic com/starpilot/starview/VoiceStudio.tts Lcom/k2fsa/sherpa/onnx/OfflineTts;
      // 056: astore 9
      // 058: aload 9
      // 05a: ifnull 076
      // 05d: getstatic com/starpilot/starview/VoiceStudio.ttsKey Ljava/lang/String;
      // 060: aload 8
      // 062: invokestatic kotlin/jvm/internal/Intrinsics.areEqual (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 065: istore 7
      // 067: iload 7
      // 069: ifeq 071
      // 06c: aload 0
      // 06d: monitorexit
      // 06e: aload 9
      // 070: areturn
      // 071: aload 9
      // 073: invokevirtual com/k2fsa/sherpa/onnx/OfflineTts.release ()V
      // 076: aload 0
      // 077: aload 1
      // 078: aload 2
      // 079: invokespecial com/starpilot/starview/VoiceStudio.mdir (Landroid/content/Context;Ljava/lang/String;)Ljava/io/File;
      // 07c: astore 12
      // 07e: new com/k2fsa/sherpa/onnx/OfflineTtsConfig
      // 081: astore 2
      // 082: new com/k2fsa/sherpa/onnx/OfflineTtsModelConfig
      // 085: astore 10
      // 087: new com/k2fsa/sherpa/onnx/OfflineTtsVitsModelConfig
      // 08a: astore 9
      // 08c: new java/io/File
      // 08f: astore 11
      // 091: aload 11
      // 093: aload 12
      // 095: ldc "model.onnx"
      // 097: invokespecial java/io/File.<init> (Ljava/io/File;Ljava/lang/String;)V
      // 09a: aload 11
      // 09c: invokevirtual java/io/File.getAbsolutePath ()Ljava/lang/String;
      // 09f: astore 11
      // 0a1: aload 11
      // 0a3: ldc "getAbsolutePath(...)"
      // 0a5: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 0a8: new java/io/File
      // 0ab: astore 13
      // 0ad: aload 13
      // 0af: aload 12
      // 0b1: ldc "tokens.txt"
      // 0b3: invokespecial java/io/File.<init> (Ljava/io/File;Ljava/lang/String;)V
      // 0b6: aload 13
      // 0b8: invokevirtual java/io/File.getAbsolutePath ()Ljava/lang/String;
      // 0bb: astore 12
      // 0bd: aload 12
      // 0bf: ldc "getAbsolutePath(...)"
      // 0c1: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 0c4: new java/io/File
      // 0c7: astore 13
      // 0c9: aload 13
      // 0cb: aload 0
      // 0cc: aload 1
      // 0cd: invokespecial com/starpilot/starview/VoiceStudio.kit (Landroid/content/Context;)Ljava/io/File;
      // 0d0: ldc "espeak-ng-data"
      // 0d2: invokespecial java/io/File.<init> (Ljava/io/File;Ljava/lang/String;)V
      // 0d5: aload 13
      // 0d7: invokevirtual java/io/File.getAbsolutePath ()Ljava/lang/String;
      // 0da: astore 1
      // 0db: aload 1
      // 0dc: ldc "getAbsolutePath(...)"
      // 0de: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 0e1: aload 9
      // 0e3: aload 11
      // 0e5: aconst_null
      // 0e6: aload 12
      // 0e8: aload 1
      // 0e9: aconst_null
      // 0ea: aload 3
      // 0eb: invokevirtual com/starpilot/starview/VoiceStudio$Style.getExpressive ()F
      // 0ee: aload 3
      // 0ef: invokevirtual com/starpilot/starview/VoiceStudio$Style.getRhythm ()F
      // 0f2: aload 3
      // 0f3: invokevirtual com/starpilot/starview/VoiceStudio$Style.getPace ()F
      // 0f6: bipush 18
      // 0f8: aconst_null
      // 0f9: invokespecial com/k2fsa/sherpa/onnx/OfflineTtsVitsModelConfig.<init> (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;FFFILkotlin/jvm/internal/DefaultConstructorMarker;)V
      // 0fc: aload 10
      // 0fe: aload 9
      // 100: aconst_null
      // 101: aconst_null
      // 102: aconst_null
      // 103: aconst_null
      // 104: aconst_null
      // 105: aconst_null
      // 106: bipush 4
      // 107: bipush 0
      // 108: ldc_w "cpu"
      // 10b: sipush 382
      // 10e: aconst_null
      // 10f: invokespecial com/k2fsa/sherpa/onnx/OfflineTtsModelConfig.<init> (Lcom/k2fsa/sherpa/onnx/OfflineTtsVitsModelConfig;Lcom/k2fsa/sherpa/onnx/OfflineTtsMatchaModelConfig;Lcom/k2fsa/sherpa/onnx/OfflineTtsKokoroModelConfig;Lcom/k2fsa/sherpa/onnx/OfflineTtsZipVoiceModelConfig;Lcom/k2fsa/sherpa/onnx/OfflineTtsKittenModelConfig;Lcom/k2fsa/sherpa/onnx/OfflineTtsPocketModelConfig;Lcom/k2fsa/sherpa/onnx/OfflineTtsSupertonicModelConfig;IZLjava/lang/String;ILkotlin/jvm/internal/DefaultConstructorMarker;)V
      // 112: aload 2
      // 113: aload 10
      // 115: aconst_null
      // 116: aconst_null
      // 117: bipush 0
      // 118: fconst_0
      // 119: bipush 30
      // 11b: aconst_null
      // 11c: invokespecial com/k2fsa/sherpa/onnx/OfflineTtsConfig.<init> (Lcom/k2fsa/sherpa/onnx/OfflineTtsModelConfig;Ljava/lang/String;Ljava/lang/String;IFILkotlin/jvm/internal/DefaultConstructorMarker;)V
      // 11f: new com/k2fsa/sherpa/onnx/OfflineTts
      // 122: astore 1
      // 123: aload 1
      // 124: aconst_null
      // 125: aload 2
      // 126: bipush 1
      // 127: aconst_null
      // 128: invokespecial com/k2fsa/sherpa/onnx/OfflineTts.<init> (Landroid/content/res/AssetManager;Lcom/k2fsa/sherpa/onnx/OfflineTtsConfig;ILkotlin/jvm/internal/DefaultConstructorMarker;)V
      // 12b: aload 1
      // 12c: putstatic com/starpilot/starview/VoiceStudio.tts Lcom/k2fsa/sherpa/onnx/OfflineTts;
      // 12f: aload 8
      // 131: putstatic com/starpilot/starview/VoiceStudio.ttsKey Ljava/lang/String;
      // 134: aload 0
      // 135: monitorexit
      // 136: aload 1
      // 137: areturn
      // 138: new java/lang/IllegalStateException
      // 13b: astore 1
      // 13c: aload 1
      // 13d: aload 8
      // 13f: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
      // 142: aload 1
      // 143: athrow
      // 144: astore 1
      // 145: aload 0
      // 146: monitorexit
      // 147: aload 1
      // 148: athrow
   }

   private final OkHttpClient getHttp() {
      return (OkHttpClient)http$delegate.getValue();
   }

   private static final OkHttpClient http_delegate$lambda$0() {
      return new Builder().connectTimeout(20L, TimeUnit.SECONDS).readTimeout(60L, TimeUnit.SECONDS).build();
   }

   private final File kit(Context var1) {
      return new File(var1.getFilesDir(), "voicekit");
   }

   private final File mdir(Context var1, String var2) {
      return new File(var1.getFilesDir(), "piper/" + var2);
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public final List<VoiceStudio.Voice> catalog(Context var1, boolean var2) {
      Intrinsics.checkNotNullParameter(var1, "c");
      File var6 = this.catFile(var1);
      if (var2 || !var6.isFile()) {
         File var23 = var6.getParentFile();
         if (var23 != null) {
            var23.mkdirs();
         }

         var var24 = this.getHttp()
            .newCall(new okhttp3.Request.Builder().url("https://huggingface.co/rhasspy/piper-voices/resolve/main/voices.json").build())
            .execute();

         try {
            Response var30 = var24;
            if (!var30.isSuccessful()) {
               int var27 = var30.code();
               StringBuilder var37 = new StringBuilder();
               IOException var29 = new IOException(var37.append("Hugging Face said ").append(var27).toString());
               throw var29;
            }

            ResponseBody var31 = var30.body();
            Intrinsics.checkNotNull(var31);
            FilesKt.writeText$default(var6, var31.string(), null, 2, null);
            Unit var32 = Unit.INSTANCE;
         } catch (Throwable var22) {
            Throwable var7 = var22;

            try {
               throw var7;
            } finally {
               CloseableKt.closeFinally(var24, var22);
            }
         }

         CloseableKt.closeFinally(var24, null);
      }

      JSONObject var9 = new JSONObject(FilesKt.readText$default(var6, null, 1, null));
      ArrayList var8 = new ArrayList();
      Iterator var11 = var9.keys();
      Intrinsics.checkNotNullExpressionValue(var11, "keys(...)");

      while (var11.hasNext()) {
         String var10 = (String)var11.next();
         JSONObject var12 = var9.getJSONObject(var10);
         JSONObject var13 = var12.getJSONObject("language");
         if (Intrinsics.areEqual(var13.optString("family"), "en")) {
            JSONObject var14 = var12.getJSONObject("files");
            Iterator var15 = var14.keys();
            Intrinsics.checkNotNullExpressionValue(var15, "keys(...)");
            String var28 = "";
            String var25 = "";
            long var4 = 0L;

            while (var15.hasNext()) {
               String var33 = (String)var15.next();
               Intrinsics.checkNotNull(var33);
               if (StringsKt.endsWith$default(var33, ".onnx", false, 2, null)) {
                  var4 = var14.getJSONObject(var33).optLong("size_bytes");
                  var28 = var33;
               }

               if (StringsKt.endsWith$default(var33, ".onnx.json", false, 2, null)) {
                  var25 = var33;
               }
            }

            JSONObject var16 = var12.optJSONObject("speaker_id_map");
            ArrayList var34 = new ArrayList();
            if (var16 != null) {
               var15 = var16.keys();
               Intrinsics.checkNotNullExpressionValue(var15, "keys(...)");

               while (var15.hasNext()) {
                  String var40 = (String)var15.next();
                  var34.add(TuplesKt.to(var40, var16.getInt(var40)));
               }
            }

            Intrinsics.checkNotNull(var10);
            String var41 = var12.optString("name");
            Intrinsics.checkNotNullExpressionValue(var41, "optString(...)");
            String var43 = var13.optString("region");
            Intrinsics.checkNotNullExpressionValue(var43, "optString(...)");
            String var39 = var12.optString("quality");
            Intrinsics.checkNotNullExpressionValue(var39, "optString(...)");
            int var3 = var12.optInt("num_speakers", 1);
            Iterable var35 = CollectionsKt.sortedWith(var34, new Comparator() {
               @Override
               public final int compare(T var1, T var2x) {
                  return ComparisonsKt.compareValues((T)((Integer & Comparable)((Pair)var1).getSecond()), (T)((Integer & Comparable)((Pair)var2x).getSecond()));
               }
            });
            var var44 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var35, 10));
            Iterator var38 = var35.iterator();

            while (var38.hasNext()) {
               var44.add((String)((Pair)var38.next()).getFirst());
            }

            List var36 = var44;
            var8.add(new VoiceStudio.Voice(var10, var41, var43, var39, var3, var36, var28, var25, var4));
         }
      }

      Map var26 = MapsKt.mapOf(new Pair[]{TuplesKt.to("high", 0), TuplesKt.to("medium", 1), TuplesKt.to("low", 2), TuplesKt.to("x_low", 3)});
      return CollectionsKt.sortedWith(
         var8,
         ComparisonsKt.compareBy(
            new Function1[]{
               new VoiceStudio$$ExternalSyntheticLambda0(), new VoiceStudio$$ExternalSyntheticLambda1(), new VoiceStudio$$ExternalSyntheticLambda2(var26)
            }
         )
      );
   }

   public final void deleteModel(Context var1, String var2) {
      Intrinsics.checkNotNullParameter(var1, "c");
      Intrinsics.checkNotNullParameter(var2, "key");
      FilesKt.deleteRecursively(this.mdir(var1, var2));
   }

   public final String download(Context param1, VoiceStudio.Voice param2, AtomicBoolean param3, Function2<? super Long, ? super Long, Unit> param4) {
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
      // 001: ldc_w "c"
      // 004: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
      // 007: aload 2
      // 008: ldc_w "v"
      // 00b: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
      // 00e: aload 3
      // 00f: ldc_w "cancel"
      // 012: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
      // 015: aload 4
      // 017: ldc_w "progress"
      // 01a: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
      // 01d: aload 0
      // 01e: aload 1
      // 01f: aload 2
      // 020: invokevirtual com/starpilot/starview/VoiceStudio$Voice.getKey ()Ljava/lang/String;
      // 023: invokespecial com/starpilot/starview/VoiceStudio.mdir (Landroid/content/Context;Ljava/lang/String;)Ljava/io/File;
      // 026: astore 16
      // 028: aload 16
      // 02a: invokevirtual java/io/File.mkdirs ()Z
      // 02d: pop
      // 02e: new java/io/File
      // 031: dup
      // 032: aload 16
      // 034: ldc_w "model.onnx.part"
      // 037: invokespecial java/io/File.<init> (Ljava/io/File;Ljava/lang/String;)V
      // 03a: astore 1
      // 03b: aload 0
      // 03c: invokespecial com/starpilot/starview/VoiceStudio.getHttp ()Lokhttp3/OkHttpClient;
      // 03f: astore 14
      // 041: new okhttp3/Request$Builder
      // 044: astore 15
      // 046: aload 15
      // 048: invokespecial okhttp3/Request$Builder.<init> ()V
      // 04b: aload 2
      // 04c: invokevirtual com/starpilot/starview/VoiceStudio$Voice.getJson ()Ljava/lang/String;
      // 04f: astore 17
      // 051: new java/lang/StringBuilder
      // 054: astore 13
      // 056: aload 13
      // 058: invokespecial java/lang/StringBuilder.<init> ()V
      // 05b: aload 14
      // 05d: aload 15
      // 05f: aload 13
      // 061: ldc "https://huggingface.co/rhasspy/piper-voices/resolve/main/"
      // 063: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 066: aload 17
      // 068: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 06b: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 06e: invokevirtual okhttp3/Request$Builder.url (Ljava/lang/String;)Lokhttp3/Request$Builder;
      // 071: invokevirtual okhttp3/Request$Builder.build ()Lokhttp3/Request;
      // 074: invokevirtual okhttp3/OkHttpClient.newCall (Lokhttp3/Request;)Lokhttp3/Call;
      // 077: invokeinterface okhttp3/Call.execute ()Lokhttp3/Response; 1
      // 07c: checkcast java/io/Closeable
      // 07f: astore 13
      // 081: aload 13
      // 083: checkcast okhttp3/Response
      // 086: astore 14
      // 088: aload 14
      // 08a: invokevirtual okhttp3/Response.isSuccessful ()Z
      // 08d: istore 12
      // 08f: iload 12
      // 091: ifne 0c7
      // 094: aload 14
      // 096: invokevirtual okhttp3/Response.code ()I
      // 099: istore 5
      // 09b: new java/lang/StringBuilder
      // 09e: astore 2
      // 09f: aload 2
      // 0a0: invokespecial java/lang/StringBuilder.<init> ()V
      // 0a3: aload 2
      // 0a4: ldc_w "Hugging Face said "
      // 0a7: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 0aa: iload 5
      // 0ac: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
      // 0af: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 0b2: astore 2
      // 0b3: aload 13
      // 0b5: aconst_null
      // 0b6: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 0b9: aload 2
      // 0ba: areturn
      // 0bb: astore 3
      // 0bc: aload 1
      // 0bd: astore 2
      // 0be: aload 3
      // 0bf: astore 1
      // 0c0: goto 38e
      // 0c3: astore 3
      // 0c4: goto 374
      // 0c7: aload 14
      // 0c9: invokevirtual okhttp3/Response.body ()Lokhttp3/ResponseBody;
      // 0cc: astore 14
      // 0ce: aload 14
      // 0d0: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNull (Ljava/lang/Object;)V
      // 0d3: aload 14
      // 0d5: invokevirtual okhttp3/ResponseBody.string ()Ljava/lang/String;
      // 0d8: astore 18
      // 0da: aload 13
      // 0dc: aconst_null
      // 0dd: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 0e0: new org/json/JSONObject
      // 0e3: astore 19
      // 0e5: aload 19
      // 0e7: aload 18
      // 0e9: invokespecial org/json/JSONObject.<init> (Ljava/lang/String;)V
      // 0ec: aload 0
      // 0ed: invokespecial com/starpilot/starview/VoiceStudio.getHttp ()Lokhttp3/OkHttpClient;
      // 0f0: astore 17
      // 0f2: new okhttp3/Request$Builder
      // 0f5: astore 13
      // 0f7: aload 13
      // 0f9: invokespecial okhttp3/Request$Builder.<init> ()V
      // 0fc: aload 2
      // 0fd: invokevirtual com/starpilot/starview/VoiceStudio$Voice.getOnnx ()Ljava/lang/String;
      // 100: astore 14
      // 102: new java/lang/StringBuilder
      // 105: astore 15
      // 107: aload 15
      // 109: invokespecial java/lang/StringBuilder.<init> ()V
      // 10c: aload 17
      // 10e: aload 13
      // 110: aload 15
      // 112: ldc "https://huggingface.co/rhasspy/piper-voices/resolve/main/"
      // 114: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 117: aload 14
      // 119: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 11c: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 11f: invokevirtual okhttp3/Request$Builder.url (Ljava/lang/String;)Lokhttp3/Request$Builder;
      // 122: invokevirtual okhttp3/Request$Builder.build ()Lokhttp3/Request;
      // 125: invokevirtual okhttp3/OkHttpClient.newCall (Lokhttp3/Request;)Lokhttp3/Call;
      // 128: invokeinterface okhttp3/Call.execute ()Lokhttp3/Response; 1
      // 12d: checkcast java/io/Closeable
      // 130: astore 14
      // 132: aload 14
      // 134: checkcast okhttp3/Response
      // 137: astore 13
      // 139: aload 13
      // 13b: invokevirtual okhttp3/Response.isSuccessful ()Z
      // 13e: istore 12
      // 140: iload 12
      // 142: ifne 17a
      // 145: aload 1
      // 146: astore 2
      // 147: aload 13
      // 149: invokevirtual okhttp3/Response.code ()I
      // 14c: istore 5
      // 14e: aload 1
      // 14f: astore 2
      // 150: new java/lang/StringBuilder
      // 153: astore 3
      // 154: aload 1
      // 155: astore 2
      // 156: aload 3
      // 157: invokespecial java/lang/StringBuilder.<init> ()V
      // 15a: aload 1
      // 15b: astore 2
      // 15c: aload 3
      // 15d: ldc_w "Hugging Face said "
      // 160: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 163: iload 5
      // 165: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
      // 168: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 16b: astore 3
      // 16c: aload 14
      // 16e: aconst_null
      // 16f: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 172: aload 3
      // 173: areturn
      // 174: astore 3
      // 175: aload 2
      // 176: astore 1
      // 177: goto 362
      // 17a: aload 13
      // 17c: invokevirtual okhttp3/Response.body ()Lokhttp3/ResponseBody;
      // 17f: astore 15
      // 181: aload 15
      // 183: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNull (Ljava/lang/Object;)V
      // 186: aload 15
      // 188: invokevirtual okhttp3/ResponseBody.contentLength ()J
      // 18b: invokestatic java/lang/Long.valueOf (J)Ljava/lang/Long;
      // 18e: astore 13
      // 190: aload 13
      // 192: checkcast java/lang/Number
      // 195: invokevirtual java/lang/Number.longValue ()J
      // 198: lstore 6
      // 19a: lload 6
      // 19c: lconst_0
      // 19d: lcmp
      // 19e: ifle 1a7
      // 1a1: bipush 1
      // 1a2: istore 5
      // 1a4: goto 1aa
      // 1a7: bipush 0
      // 1a8: istore 5
      // 1aa: iload 5
      // 1ac: ifeq 1b2
      // 1af: goto 1b5
      // 1b2: aconst_null
      // 1b3: astore 13
      // 1b5: aload 13
      // 1b7: ifnull 1c6
      // 1ba: aload 1
      // 1bb: astore 2
      // 1bc: aload 13
      // 1be: invokevirtual java/lang/Long.longValue ()J
      // 1c1: lstore 6
      // 1c3: goto 1cc
      // 1c6: aload 2
      // 1c7: invokevirtual com/starpilot/starview/VoiceStudio$Voice.getBytes ()J
      // 1ca: lstore 6
      // 1cc: aload 15
      // 1ce: invokevirtual okhttp3/ResponseBody.byteStream ()Ljava/io/InputStream;
      // 1d1: checkcast java/io/Closeable
      // 1d4: astore 15
      // 1d6: aload 15
      // 1d8: checkcast java/io/InputStream
      // 1db: astore 13
      // 1dd: new java/io/FileOutputStream
      // 1e0: astore 2
      // 1e1: aload 2
      // 1e2: aload 1
      // 1e3: invokespecial java/io/FileOutputStream.<init> (Ljava/io/File;)V
      // 1e6: aload 2
      // 1e7: checkcast java/io/Closeable
      // 1ea: astore 17
      // 1ec: aload 1
      // 1ed: astore 2
      // 1ee: aload 17
      // 1f0: checkcast java/io/FileOutputStream
      // 1f3: astore 21
      // 1f5: aload 1
      // 1f6: astore 2
      // 1f7: ldc_w 65536
      // 1fa: newarray 8
      // 1fc: astore 20
      // 1fe: lconst_0
      // 1ff: lstore 10
      // 201: lconst_0
      // 202: lstore 8
      // 204: aload 1
      // 205: astore 2
      // 206: aload 3
      // 207: invokevirtual java/util/concurrent/atomic/AtomicBoolean.get ()Z
      // 20a: istore 12
      // 20c: iload 12
      // 20e: ifeq 236
      // 211: aload 1
      // 212: invokevirtual java/io/File.delete ()Z
      // 215: pop
      // 216: aload 17
      // 218: aconst_null
      // 219: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 21c: aload 1
      // 21d: astore 2
      // 21e: aload 15
      // 220: aconst_null
      // 221: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 224: aload 14
      // 226: aconst_null
      // 227: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 22a: ldc_w "Cancelled"
      // 22d: areturn
      // 22e: astore 2
      // 22f: goto 352
      // 232: astore 2
      // 233: goto 342
      // 236: aload 13
      // 238: aload 20
      // 23a: invokevirtual java/io/InputStream.read ([B)I
      // 23d: istore 5
      // 23f: iload 5
      // 241: iflt 286
      // 244: aload 21
      // 246: aload 20
      // 248: bipush 0
      // 249: iload 5
      // 24b: invokevirtual java/io/FileOutputStream.write ([BII)V
      // 24e: lload 10
      // 250: iload 5
      // 252: i2l
      // 253: ladd
      // 254: lstore 10
      // 256: lload 10
      // 258: lload 8
      // 25a: lsub
      // 25b: ldc2_w 524288
      // 25e: lcmp
      // 25f: ifle 27f
      // 262: aload 4
      // 264: lload 10
      // 266: invokestatic java/lang/Long.valueOf (J)Ljava/lang/Long;
      // 269: lload 6
      // 26b: invokestatic java/lang/Long.valueOf (J)Ljava/lang/Long;
      // 26e: invokeinterface kotlin/jvm/functions/Function2.invoke (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
      // 273: pop
      // 274: lload 10
      // 276: lstore 8
      // 278: goto 204
      // 27b: astore 2
      // 27c: goto 342
      // 27f: goto 204
      // 282: astore 2
      // 283: goto 342
      // 286: aload 4
      // 288: lload 10
      // 28a: invokestatic java/lang/Long.valueOf (J)Ljava/lang/Long;
      // 28d: lload 6
      // 28f: invokestatic java/lang/Long.valueOf (J)Ljava/lang/Long;
      // 292: invokeinterface kotlin/jvm/functions/Function2.invoke (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
      // 297: pop
      // 298: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 29b: astore 2
      // 29c: aload 17
      // 29e: aconst_null
      // 29f: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 2a2: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 2a5: astore 2
      // 2a6: aload 15
      // 2a8: aconst_null
      // 2a9: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 2ac: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 2af: astore 2
      // 2b0: aload 14
      // 2b2: aconst_null
      // 2b3: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 2b6: getstatic com/starpilot/starview/PiperPrep.INSTANCE Lcom/starpilot/starview/PiperPrep;
      // 2b9: aload 19
      // 2bb: invokevirtual com/starpilot/starview/PiperPrep.metadataBytes (Lorg/json/JSONObject;)[B
      // 2be: astore 3
      // 2bf: aload 1
      // 2c0: astore 2
      // 2c1: aload 1
      // 2c2: aload 3
      // 2c3: invokestatic kotlin/io/FilesKt.appendBytes (Ljava/io/File;[B)V
      // 2c6: aload 1
      // 2c7: astore 2
      // 2c8: new java/io/File
      // 2cb: astore 3
      // 2cc: aload 1
      // 2cd: astore 2
      // 2ce: aload 3
      // 2cf: aload 16
      // 2d1: ldc "tokens.txt"
      // 2d3: invokespecial java/io/File.<init> (Ljava/io/File;Ljava/lang/String;)V
      // 2d6: aload 1
      // 2d7: astore 2
      // 2d8: aload 3
      // 2d9: getstatic com/starpilot/starview/PiperPrep.INSTANCE Lcom/starpilot/starview/PiperPrep;
      // 2dc: aload 19
      // 2de: invokevirtual com/starpilot/starview/PiperPrep.tokens (Lorg/json/JSONObject;)Ljava/lang/String;
      // 2e1: aconst_null
      // 2e2: bipush 2
      // 2e3: aconst_null
      // 2e4: invokestatic kotlin/io/FilesKt.writeText$default (Ljava/io/File;Ljava/lang/String;Ljava/nio/charset/Charset;ILjava/lang/Object;)V
      // 2e7: aload 1
      // 2e8: astore 2
      // 2e9: new java/io/File
      // 2ec: astore 3
      // 2ed: aload 1
      // 2ee: astore 2
      // 2ef: aload 3
      // 2f0: aload 16
      // 2f2: ldc_w "config.json"
      // 2f5: invokespecial java/io/File.<init> (Ljava/io/File;Ljava/lang/String;)V
      // 2f8: aload 1
      // 2f9: astore 2
      // 2fa: aload 3
      // 2fb: aload 18
      // 2fd: aconst_null
      // 2fe: bipush 2
      // 2ff: aconst_null
      // 300: invokestatic kotlin/io/FilesKt.writeText$default (Ljava/io/File;Ljava/lang/String;Ljava/nio/charset/Charset;ILjava/lang/Object;)V
      // 303: aload 1
      // 304: astore 2
      // 305: new java/io/File
      // 308: astore 3
      // 309: aload 1
      // 30a: astore 2
      // 30b: aload 3
      // 30c: aload 16
      // 30e: ldc "model.onnx"
      // 310: invokespecial java/io/File.<init> (Ljava/io/File;Ljava/lang/String;)V
      // 313: aload 1
      // 314: astore 2
      // 315: aload 1
      // 316: aload 3
      // 317: invokevirtual java/io/File.renameTo (Ljava/io/File;)Z
      // 31a: ifne 321
      // 31d: ldc_w "Couldn't save it"
      // 320: areturn
      // 321: aconst_null
      // 322: areturn
      // 323: astore 2
      // 324: aload 1
      // 325: astore 3
      // 326: aload 2
      // 327: astore 1
      // 328: aload 3
      // 329: astore 2
      // 32a: goto 38e
      // 32d: astore 3
      // 32e: goto 362
      // 331: astore 2
      // 332: goto 352
      // 335: astore 2
      // 336: goto 342
      // 339: astore 2
      // 33a: goto 342
      // 33d: astore 3
      // 33e: aload 2
      // 33f: astore 1
      // 340: aload 3
      // 341: astore 2
      // 342: aload 2
      // 343: athrow
      // 344: astore 3
      // 345: aload 17
      // 347: aload 2
      // 348: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 34b: aload 3
      // 34c: athrow
      // 34d: astore 2
      // 34e: goto 352
      // 351: astore 2
      // 352: aload 2
      // 353: athrow
      // 354: astore 3
      // 355: aload 15
      // 357: aload 2
      // 358: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 35b: aload 3
      // 35c: athrow
      // 35d: astore 3
      // 35e: goto 362
      // 361: astore 3
      // 362: aload 3
      // 363: athrow
      // 364: astore 4
      // 366: aload 1
      // 367: astore 2
      // 368: aload 14
      // 36a: aload 3
      // 36b: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 36e: aload 1
      // 36f: astore 2
      // 370: aload 4
      // 372: athrow
      // 373: astore 3
      // 374: aload 3
      // 375: athrow
      // 376: astore 4
      // 378: aload 1
      // 379: astore 2
      // 37a: aload 13
      // 37c: aload 3
      // 37d: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 380: aload 1
      // 381: astore 2
      // 382: aload 4
      // 384: athrow
      // 385: astore 1
      // 386: goto 38e
      // 389: astore 3
      // 38a: aload 1
      // 38b: astore 2
      // 38c: aload 3
      // 38d: astore 1
      // 38e: aload 2
      // 38f: invokevirtual java/io/File.delete ()Z
      // 392: pop
      // 393: aload 1
      // 394: invokevirtual java/lang/Exception.getMessage ()Ljava/lang/String;
      // 397: astore 1
      // 398: new java/lang/StringBuilder
      // 39b: dup
      // 39c: invokespecial java/lang/StringBuilder.<init> ()V
      // 39f: ldc_w "Download failed: "
      // 3a2: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 3a5: aload 1
      // 3a6: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 3a9: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 3ac: areturn
   }

   public final boolean downloaded(Context var1, String var2) {
      Intrinsics.checkNotNullParameter(var1, "c");
      Intrinsics.checkNotNullParameter(var2, "key");
      boolean var3;
      if (new File(this.mdir(var1, var2), "model.onnx").isFile() && new File(this.mdir(var1, var2), "tokens.txt").isFile()) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public final long downloadedBytes(Context var1) {
      Intrinsics.checkNotNullParameter(var1, "c");
      Iterator var5 = SequencesKt.filter(
            FilesKt.walk$default(new File(var1.getFilesDir(), "piper"), null, 1, null), new VoiceStudio$$ExternalSyntheticLambda4()
         )
         .iterator();
      long var2 = 0L;

      while (var5.hasNext()) {
         File var4 = (File)var5.next();
         var2 += var4.length();
      }

      return var2;
   }

   public final boolean engineInstalled(Context var1) {
      Intrinsics.checkNotNullParameter(var1, "c");
      boolean var2;
      if (new File(this.kit(var1), "lib/libsherpa-onnx-jni.so").isFile()
         && new File(this.kit(var1), "lib/libonnxruntime.so").isFile()
         && new File(this.kit(var1), "espeak-ng-data/phontab").isFile()) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public final void freeEngine() {
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
      // 02: getstatic com/starpilot/starview/VoiceStudio.tts Lcom/k2fsa/sherpa/onnx/OfflineTts;
      // 05: astore 1
      // 06: aload 1
      // 07: ifnull 0e
      // 0a: aload 1
      // 0b: invokevirtual com/k2fsa/sherpa/onnx/OfflineTts.release ()V
      // 0e: aconst_null
      // 0f: putstatic com/starpilot/starview/VoiceStudio.tts Lcom/k2fsa/sherpa/onnx/OfflineTts;
      // 12: ldc ""
      // 14: putstatic com/starpilot/starview/VoiceStudio.ttsKey Ljava/lang/String;
      // 17: aload 0
      // 18: monitorexit
      // 19: return
      // 1a: astore 1
      // 1b: aload 0
      // 1c: monitorexit
      // 1d: aload 1
      // 1e: athrow
   }

   public final String installEngine(Context param1, Uri param2, Function1<? super String, Unit> param3) {
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
      // 001: ldc_w "c"
      // 004: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
      // 007: aload 2
      // 008: ldc_w "uri"
      // 00b: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
      // 00e: aload 3
      // 00f: ldc_w "progress"
      // 012: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
      // 015: getstatic com/starpilot/starview/VoiceStudio.loaded Z
      // 018: ifeq 01f
      // 01b: ldc_w "Already installed and running - restart the app to replace it"
      // 01e: areturn
      // 01f: new java/io/File
      // 022: dup
      // 023: aload 1
      // 024: invokevirtual android/content/Context.getFilesDir ()Ljava/io/File;
      // 027: ldc_w "voicekit.tmp"
      // 02a: invokespecial java/io/File.<init> (Ljava/io/File;Ljava/lang/String;)V
      // 02d: astore 7
      // 02f: aload 7
      // 031: invokestatic kotlin/io/FilesKt.deleteRecursively (Ljava/io/File;)Z
      // 034: pop
      // 035: aload 7
      // 037: invokevirtual java/io/File.mkdirs ()Z
      // 03a: pop
      // 03b: bipush 0
      // 03c: istore 4
      // 03e: aload 1
      // 03f: invokevirtual android/content/Context.getContentResolver ()Landroid/content/ContentResolver;
      // 042: aload 2
      // 043: invokevirtual android/content/ContentResolver.openInputStream (Landroid/net/Uri;)Ljava/io/InputStream;
      // 046: astore 2
      // 047: aload 2
      // 048: ifnull 253
      // 04b: aload 2
      // 04c: checkcast java/io/Closeable
      // 04f: astore 8
      // 051: aload 8
      // 053: checkcast java/io/InputStream
      // 056: astore 2
      // 057: new java/util/zip/ZipInputStream
      // 05a: astore 9
      // 05c: aload 2
      // 05d: instanceof java/io/BufferedInputStream
      // 060: ifeq 06c
      // 063: aload 2
      // 064: checkcast java/io/BufferedInputStream
      // 067: astore 6
      // 069: goto 079
      // 06c: new java/io/BufferedInputStream
      // 06f: dup
      // 070: aload 2
      // 071: sipush 8192
      // 074: invokespecial java/io/BufferedInputStream.<init> (Ljava/io/InputStream;I)V
      // 077: astore 6
      // 079: aload 9
      // 07b: aload 6
      // 07d: checkcast java/io/InputStream
      // 080: invokespecial java/util/zip/ZipInputStream.<init> (Ljava/io/InputStream;)V
      // 083: aload 9
      // 085: checkcast java/io/Closeable
      // 088: astore 9
      // 08a: aload 2
      // 08b: astore 6
      // 08d: aload 9
      // 08f: checkcast java/util/zip/ZipInputStream
      // 092: astore 10
      // 094: aload 2
      // 095: astore 6
      // 097: aload 10
      // 099: invokevirtual java/util/zip/ZipInputStream.getNextEntry ()Ljava/util/zip/ZipEntry;
      // 09c: astore 11
      // 09e: aconst_null
      // 09f: astore 6
      // 0a1: aload 11
      // 0a3: ifnonnull 111
      // 0a6: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 0a9: astore 2
      // 0aa: aload 9
      // 0ac: aconst_null
      // 0ad: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 0b0: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 0b3: astore 2
      // 0b4: aload 8
      // 0b6: aconst_null
      // 0b7: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 0ba: new java/io/File
      // 0bd: dup
      // 0be: aload 7
      // 0c0: ldc_w "lib/libsherpa-onnx-jni.so"
      // 0c3: invokespecial java/io/File.<init> (Ljava/io/File;Ljava/lang/String;)V
      // 0c6: invokevirtual java/io/File.isFile ()Z
      // 0c9: ifeq 103
      // 0cc: new java/io/File
      // 0cf: dup
      // 0d0: aload 7
      // 0d2: ldc_w "espeak-ng-data/phontab"
      // 0d5: invokespecial java/io/File.<init> (Ljava/io/File;Ljava/lang/String;)V
      // 0d8: invokevirtual java/io/File.isFile ()Z
      // 0db: ifne 0e1
      // 0de: goto 103
      // 0e1: aload 0
      // 0e2: aload 1
      // 0e3: invokespecial com/starpilot/starview/VoiceStudio.kit (Landroid/content/Context;)Ljava/io/File;
      // 0e6: invokestatic kotlin/io/FilesKt.deleteRecursively (Ljava/io/File;)Z
      // 0e9: pop
      // 0ea: aload 7
      // 0ec: aload 0
      // 0ed: aload 1
      // 0ee: invokespecial com/starpilot/starview/VoiceStudio.kit (Landroid/content/Context;)Ljava/io/File;
      // 0f1: invokevirtual java/io/File.renameTo (Ljava/io/File;)Z
      // 0f4: ifeq 0fd
      // 0f7: aload 6
      // 0f9: astore 1
      // 0fa: goto 101
      // 0fd: ldc_w "Couldn't save it"
      // 100: astore 1
      // 101: aload 1
      // 102: areturn
      // 103: aload 7
      // 105: invokestatic kotlin/io/FilesKt.deleteRecursively (Ljava/io/File;)Z
      // 108: pop
      // 109: ldc_w "That zip isn't StarView-Voice-engine.zip"
      // 10c: areturn
      // 10d: astore 1
      // 10e: goto 23c
      // 111: aload 2
      // 112: astore 6
      // 114: aload 11
      // 116: invokevirtual java/util/zip/ZipEntry.getName ()Ljava/lang/String;
      // 119: astore 12
      // 11b: aload 2
      // 11c: astore 6
      // 11e: aload 12
      // 120: ldc_w "getName(...)"
      // 123: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 126: aload 2
      // 127: astore 6
      // 129: aload 12
      // 12b: ldc_w "voicekit/"
      // 12e: checkcast java/lang/CharSequence
      // 131: invokestatic kotlin/text/StringsKt.removePrefix (Ljava/lang/String;Ljava/lang/CharSequence;)Ljava/lang/String;
      // 134: astore 12
      // 136: aload 2
      // 137: astore 6
      // 139: aload 12
      // 13b: checkcast java/lang/CharSequence
      // 13e: invokeinterface java/lang/CharSequence.length ()I 1
      // 143: ifne 14c
      // 146: bipush 1
      // 147: istore 5
      // 149: goto 14f
      // 14c: bipush 0
      // 14d: istore 5
      // 14f: iload 5
      // 151: ifne 238
      // 154: aload 2
      // 155: astore 6
      // 157: aload 12
      // 159: checkcast java/lang/CharSequence
      // 15c: ldc_w ".."
      // 15f: checkcast java/lang/CharSequence
      // 162: bipush 0
      // 163: bipush 2
      // 164: aconst_null
      // 165: invokestatic kotlin/text/StringsKt.contains$default (Ljava/lang/CharSequence;Ljava/lang/CharSequence;ZILjava/lang/Object;)Z
      // 168: ifne 238
      // 16b: aload 2
      // 16c: astore 6
      // 16e: aload 11
      // 170: invokevirtual java/util/zip/ZipEntry.isDirectory ()Z
      // 173: ifeq 179
      // 176: goto 238
      // 179: aload 2
      // 17a: astore 6
      // 17c: new java/io/File
      // 17f: astore 11
      // 181: aload 2
      // 182: astore 6
      // 184: aload 11
      // 186: aload 7
      // 188: aload 12
      // 18a: invokespecial java/io/File.<init> (Ljava/io/File;Ljava/lang/String;)V
      // 18d: aload 2
      // 18e: astore 6
      // 190: aload 11
      // 192: invokevirtual java/io/File.getParentFile ()Ljava/io/File;
      // 195: astore 6
      // 197: aload 6
      // 199: ifnull 1a2
      // 19c: aload 6
      // 19e: invokevirtual java/io/File.mkdirs ()Z
      // 1a1: pop
      // 1a2: aload 2
      // 1a3: astore 6
      // 1a5: new java/io/FileOutputStream
      // 1a8: astore 12
      // 1aa: aload 2
      // 1ab: astore 6
      // 1ad: aload 12
      // 1af: aload 11
      // 1b1: invokespecial java/io/FileOutputStream.<init> (Ljava/io/File;)V
      // 1b4: aload 2
      // 1b5: astore 6
      // 1b7: aload 12
      // 1b9: checkcast java/io/Closeable
      // 1bc: astore 6
      // 1be: aload 6
      // 1c0: checkcast java/io/FileOutputStream
      // 1c3: astore 12
      // 1c5: aload 10
      // 1c7: checkcast java/io/InputStream
      // 1ca: astore 11
      // 1cc: aload 12
      // 1ce: checkcast java/io/OutputStream
      // 1d1: astore 12
      // 1d3: aload 11
      // 1d5: aload 12
      // 1d7: bipush 0
      // 1d8: bipush 2
      // 1d9: aconst_null
      // 1da: invokestatic kotlin/io/ByteStreamsKt.copyTo$default (Ljava/io/InputStream;Ljava/io/OutputStream;IILjava/lang/Object;)J
      // 1dd: pop2
      // 1de: aload 6
      // 1e0: aconst_null
      // 1e1: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 1e4: iload 4
      // 1e6: bipush 1
      // 1e7: iadd
      // 1e8: istore 5
      // 1ea: iload 5
      // 1ec: istore 4
      // 1ee: iload 5
      // 1f0: bipush 50
      // 1f2: irem
      // 1f3: ifne 238
      // 1f6: new java/lang/StringBuilder
      // 1f9: astore 6
      // 1fb: aload 6
      // 1fd: invokespecial java/lang/StringBuilder.<init> ()V
      // 200: aload 3
      // 201: aload 6
      // 203: ldc_w "Unpacking… "
      // 206: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 209: iload 5
      // 20b: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
      // 20e: ldc_w " files"
      // 211: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 214: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 217: invokeinterface kotlin/jvm/functions/Function1.invoke (Ljava/lang/Object;)Ljava/lang/Object; 2
      // 21c: pop
      // 21d: iload 5
      // 21f: istore 4
      // 221: goto 238
      // 224: astore 1
      // 225: goto 229
      // 228: astore 1
      // 229: aload 1
      // 22a: athrow
      // 22b: astore 2
      // 22c: aload 6
      // 22e: aload 1
      // 22f: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 232: aload 2
      // 233: athrow
      // 234: astore 1
      // 235: goto 23c
      // 238: goto 094
      // 23b: astore 1
      // 23c: aload 1
      // 23d: athrow
      // 23e: astore 2
      // 23f: aload 9
      // 241: aload 1
      // 242: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 245: aload 2
      // 246: athrow
      // 247: astore 1
      // 248: aload 1
      // 249: athrow
      // 24a: astore 2
      // 24b: aload 8
      // 24d: aload 1
      // 24e: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 251: aload 2
      // 252: athrow
      // 253: ldc_w "Couldn't open that file"
      // 256: areturn
      // 257: astore 1
      // 258: aload 7
      // 25a: invokestatic kotlin/io/FilesKt.deleteRecursively (Ljava/io/File;)Z
      // 25d: pop
      // 25e: aload 1
      // 25f: invokevirtual java/lang/Exception.getMessage ()Ljava/lang/String;
      // 262: astore 1
      // 263: new java/lang/StringBuilder
      // 266: dup
      // 267: invokespecial java/lang/StringBuilder.<init> ()V
      // 26a: ldc_w "Not the engine zip ("
      // 26d: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 270: aload 1
      // 271: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 274: ldc_w ")"
      // 277: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 27a: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 27d: areturn
   }

   public final String load(Context param1) {
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
      // 02: aload 1
      // 03: ldc_w "c"
      // 06: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
      // 09: getstatic com/starpilot/starview/VoiceStudio.loaded Z
      // 0c: istore 2
      // 0d: aconst_null
      // 0e: astore 3
      // 0f: iload 2
      // 10: ifeq 17
      // 13: aload 0
      // 14: monitorexit
      // 15: aconst_null
      // 16: areturn
      // 17: aload 0
      // 18: aload 1
      // 19: invokevirtual com/starpilot/starview/VoiceStudio.engineInstalled (Landroid/content/Context;)Z
      // 1c: ifne 25
      // 1f: aload 0
      // 20: monitorexit
      // 21: ldc_w "The voice engine isn't installed"
      // 24: areturn
      // 25: new java/io/File
      // 28: astore 4
      // 2a: aload 4
      // 2c: aload 0
      // 2d: aload 1
      // 2e: invokespecial com/starpilot/starview/VoiceStudio.kit (Landroid/content/Context;)Ljava/io/File;
      // 31: ldc_w "lib/libonnxruntime.so"
      // 34: invokespecial java/io/File.<init> (Ljava/io/File;Ljava/lang/String;)V
      // 37: aload 4
      // 39: invokevirtual java/io/File.getAbsolutePath ()Ljava/lang/String;
      // 3c: invokestatic java/lang/System.load (Ljava/lang/String;)V
      // 3f: new java/io/File
      // 42: astore 4
      // 44: aload 4
      // 46: aload 0
      // 47: aload 1
      // 48: invokespecial com/starpilot/starview/VoiceStudio.kit (Landroid/content/Context;)Ljava/io/File;
      // 4b: ldc_w "lib/libsherpa-onnx-jni.so"
      // 4e: invokespecial java/io/File.<init> (Ljava/io/File;Ljava/lang/String;)V
      // 51: aload 4
      // 53: invokevirtual java/io/File.getAbsolutePath ()Ljava/lang/String;
      // 56: invokestatic java/lang/System.load (Ljava/lang/String;)V
      // 59: bipush 1
      // 5a: putstatic com/starpilot/starview/VoiceStudio.loaded Z
      // 5d: aload 3
      // 5e: astore 1
      // 5f: goto 7f
      // 62: astore 1
      // 63: aload 1
      // 64: invokevirtual java/lang/Throwable.getMessage ()Ljava/lang/String;
      // 67: astore 1
      // 68: new java/lang/StringBuilder
      // 6b: astore 3
      // 6c: aload 3
      // 6d: invokespecial java/lang/StringBuilder.<init> ()V
      // 70: aload 3
      // 71: ldc_w "Couldn't start the voice engine: "
      // 74: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 77: aload 1
      // 78: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 7b: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 7e: astore 1
      // 7f: aload 0
      // 80: monitorexit
      // 81: aload 1
      // 82: areturn
      // 83: astore 1
      // 84: aload 0
      // 85: monitorexit
      // 86: aload 1
      // 87: athrow
   }

   public final String make(
      Context param1,
      VoiceStudio.Voice param2,
      int param3,
      VoiceStudio.Style param4,
      String param5,
      AtomicBoolean param6,
      Function2<? super Integer, ? super Integer, Unit> param7
   ) {
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
      // 000: aload 1
      // 001: ldc_w "c"
      // 004: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
      // 007: aload 2
      // 008: ldc_w "v"
      // 00b: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
      // 00e: aload 4
      // 010: ldc_w "st"
      // 013: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
      // 016: aload 5
      // 018: ldc_w "label"
      // 01b: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
      // 01e: aload 6
      // 020: ldc_w "cancel"
      // 023: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
      // 026: aload 7
      // 028: ldc_w "progress"
      // 02b: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
      // 02e: aload 2
      // 02f: invokevirtual com/starpilot/starview/VoiceStudio$Voice.getKey ()Ljava/lang/String;
      // 032: astore 14
      // 034: aload 2
      // 035: invokevirtual com/starpilot/starview/VoiceStudio$Voice.getSpeakers ()I
      // 038: bipush 1
      // 039: if_icmple 055
      // 03c: new java/lang/StringBuilder
      // 03f: dup
      // 040: invokespecial java/lang/StringBuilder.<init> ()V
      // 043: ldc_w "_s"
      // 046: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 049: iload 3
      // 04a: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
      // 04d: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 050: astore 13
      // 052: goto 059
      // 055: ldc ""
      // 057: astore 13
      // 059: invokestatic java/lang/System.currentTimeMillis ()J
      // 05c: sipush 1000
      // 05f: i2l
      // 060: ldiv
      // 061: lstore 10
      // 063: new java/lang/StringBuilder
      // 066: dup
      // 067: invokespecial java/lang/StringBuilder.<init> ()V
      // 06a: aload 14
      // 06c: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 06f: aload 13
      // 071: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 074: ldc_w "_"
      // 077: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 07a: lload 10
      // 07c: invokevirtual java/lang/StringBuilder.append (J)Ljava/lang/StringBuilder;
      // 07f: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 082: checkcast java/lang/CharSequence
      // 085: astore 13
      // 087: new kotlin/text/Regex
      // 08a: dup
      // 08b: ldc_w "[^A-Za-z0-9_-]"
      // 08e: invokespecial kotlin/text/Regex.<init> (Ljava/lang/String;)V
      // 091: aload 13
      // 093: ldc_w "_"
      // 096: invokevirtual kotlin/text/Regex.replace (Ljava/lang/CharSequence;Ljava/lang/String;)Ljava/lang/String;
      // 099: astore 16
      // 09b: aload 1
      // 09c: invokevirtual android/content/Context.getFilesDir ()Ljava/io/File;
      // 09f: astore 13
      // 0a1: new java/lang/StringBuilder
      // 0a4: dup
      // 0a5: invokespecial java/lang/StringBuilder.<init> ()V
      // 0a8: astore 14
      // 0aa: ldc_w "voices/"
      // 0ad: astore 15
      // 0af: new java/io/File
      // 0b2: dup
      // 0b3: aload 13
      // 0b5: aload 14
      // 0b7: ldc_w "voices/"
      // 0ba: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 0bd: aload 16
      // 0bf: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 0c2: ldc_w ".tmp"
      // 0c5: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 0c8: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 0cb: invokespecial java/io/File.<init> (Ljava/io/File;Ljava/lang/String;)V
      // 0ce: astore 13
      // 0d0: aload 13
      // 0d2: invokestatic kotlin/io/FilesKt.deleteRecursively (Ljava/io/File;)Z
      // 0d5: pop
      // 0d6: aload 13
      // 0d8: invokevirtual java/io/File.mkdirs ()Z
      // 0db: pop
      // 0dc: aload 13
      // 0de: astore 14
      // 0e0: aload 0
      // 0e1: aload 1
      // 0e2: invokevirtual com/starpilot/starview/VoiceStudio.phrases (Landroid/content/Context;)Ljava/util/List;
      // 0e5: astore 17
      // 0e7: aload 13
      // 0e9: astore 14
      // 0eb: aload 17
      // 0ed: checkcast java/lang/Iterable
      // 0f0: invokeinterface java/lang/Iterable.iterator ()Ljava/util/Iterator; 1
      // 0f5: astore 18
      // 0f7: bipush 0
      // 0f8: istore 9
      // 0fa: bipush 0
      // 0fb: istore 8
      // 0fd: aload 13
      // 0ff: astore 14
      // 101: aload 18
      // 103: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 108: ifeq 1d7
      // 10b: aload 13
      // 10d: astore 14
      // 10f: aload 18
      // 111: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 116: checkcast kotlin/Pair
      // 119: astore 19
      // 11b: aload 13
      // 11d: astore 14
      // 11f: aload 6
      // 121: invokevirtual java/util/concurrent/atomic/AtomicBoolean.get ()Z
      // 124: istore 12
      // 126: iload 12
      // 128: ifeq 140
      // 12b: aload 13
      // 12d: invokestatic kotlin/io/FilesKt.deleteRecursively (Ljava/io/File;)Z
      // 130: pop
      // 131: aload 0
      // 132: invokevirtual com/starpilot/starview/VoiceStudio.freeEngine ()V
      // 135: ldc_w "Cancelled"
      // 138: areturn
      // 139: astore 1
      // 13a: aload 13
      // 13c: astore 2
      // 13d: goto 2c2
      // 140: aload 13
      // 142: astore 14
      // 144: aload 2
      // 145: invokevirtual com/starpilot/starview/VoiceStudio$Voice.getKey ()Ljava/lang/String;
      // 148: astore 20
      // 14a: aload 13
      // 14c: astore 14
      // 14e: aload 19
      // 150: invokevirtual kotlin/Pair.getSecond ()Ljava/lang/Object;
      // 153: invokestatic kotlin/collections/CollectionsKt.listOf (Ljava/lang/Object;)Ljava/util/List;
      // 156: astore 21
      // 158: aload 0
      // 159: aload 1
      // 15a: aload 20
      // 15c: iload 3
      // 15d: aload 4
      // 15f: aload 21
      // 161: invokevirtual com/starpilot/starview/VoiceStudio.say (Landroid/content/Context;Ljava/lang/String;ILcom/starpilot/starview/VoiceStudio$Style;Ljava/util/List;)Ljava/util/List;
      // 164: iload 9
      // 166: invokeinterface java/util/List.get (I)Ljava/lang/Object; 2
      // 16b: checkcast [F
      // 16e: astore 14
      // 170: new java/io/File
      // 173: astore 20
      // 175: aload 19
      // 177: invokevirtual kotlin/Pair.getFirst ()Ljava/lang/Object;
      // 17a: astore 19
      // 17c: new java/lang/StringBuilder
      // 17f: astore 21
      // 181: aload 21
      // 183: invokespecial java/lang/StringBuilder.<init> ()V
      // 186: aload 21
      // 188: aload 19
      // 18a: invokevirtual java/lang/StringBuilder.append (Ljava/lang/Object;)Ljava/lang/StringBuilder;
      // 18d: ldc_w ".wav"
      // 190: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 193: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 196: astore 19
      // 198: aload 20
      // 19a: aload 13
      // 19c: aload 19
      // 19e: invokespecial java/io/File.<init> (Ljava/io/File;Ljava/lang/String;)V
      // 1a1: aload 20
      // 1a3: getstatic com/starpilot/starview/VoiceDsp.INSTANCE Lcom/starpilot/starview/VoiceDsp;
      // 1a6: aload 14
      // 1a8: sipush 24000
      // 1ab: invokevirtual com/starpilot/starview/VoiceDsp.wav16 ([FI)[B
      // 1ae: invokestatic kotlin/io/FilesKt.writeBytes (Ljava/io/File;[B)V
      // 1b1: aload 7
      // 1b3: iload 8
      // 1b5: bipush 1
      // 1b6: iadd
      // 1b7: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 1ba: aload 17
      // 1bc: invokeinterface java/util/List.size ()I 1
      // 1c1: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 1c4: invokeinterface kotlin/jvm/functions/Function2.invoke (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
      // 1c9: pop
      // 1ca: iinc 8 1
      // 1cd: goto 0fd
      // 1d0: astore 1
      // 1d1: aload 13
      // 1d3: astore 2
      // 1d4: goto 2c2
      // 1d7: new java/io/File
      // 1da: astore 6
      // 1dc: aload 6
      // 1de: aload 13
      // 1e0: ldc_w "label.txt"
      // 1e3: invokespecial java/io/File.<init> (Ljava/io/File;Ljava/lang/String;)V
      // 1e6: aload 6
      // 1e8: aload 5
      // 1ea: aconst_null
      // 1eb: bipush 2
      // 1ec: aconst_null
      // 1ed: invokestatic kotlin/io/FilesKt.writeText$default (Ljava/io/File;Ljava/lang/String;Ljava/nio/charset/Charset;ILjava/lang/Object;)V
      // 1f0: new java/io/File
      // 1f3: astore 5
      // 1f5: aload 5
      // 1f7: aload 13
      // 1f9: ldc_w "style.json"
      // 1fc: invokespecial java/io/File.<init> (Ljava/io/File;Ljava/lang/String;)V
      // 1ff: new org/json/JSONObject
      // 202: astore 6
      // 204: aload 6
      // 206: invokespecial org/json/JSONObject.<init> ()V
      // 209: aload 6
      // 20b: ldc_w "voice"
      // 20e: aload 2
      // 20f: invokevirtual com/starpilot/starview/VoiceStudio$Voice.getKey ()Ljava/lang/String;
      // 212: invokevirtual org/json/JSONObject.put (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
      // 215: ldc_w "speaker"
      // 218: iload 3
      // 219: invokevirtual org/json/JSONObject.put (Ljava/lang/String;I)Lorg/json/JSONObject;
      // 21c: ldc_w "expressive"
      // 21f: aload 4
      // 221: invokevirtual com/starpilot/starview/VoiceStudio$Style.getExpressive ()F
      // 224: f2d
      // 225: invokevirtual org/json/JSONObject.put (Ljava/lang/String;D)Lorg/json/JSONObject;
      // 228: ldc_w "rhythm"
      // 22b: aload 4
      // 22d: invokevirtual com/starpilot/starview/VoiceStudio$Style.getRhythm ()F
      // 230: f2d
      // 231: invokevirtual org/json/JSONObject.put (Ljava/lang/String;D)Lorg/json/JSONObject;
      // 234: ldc_w "pace"
      // 237: aload 4
      // 239: invokevirtual com/starpilot/starview/VoiceStudio$Style.getPace ()F
      // 23c: f2d
      // 23d: invokevirtual org/json/JSONObject.put (Ljava/lang/String;D)Lorg/json/JSONObject;
      // 240: invokevirtual org/json/JSONObject.toString ()Ljava/lang/String;
      // 243: astore 2
      // 244: aload 2
      // 245: ldc_w "toString(...)"
      // 248: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 24b: aload 5
      // 24d: aload 2
      // 24e: aconst_null
      // 24f: bipush 2
      // 250: aconst_null
      // 251: invokestatic kotlin/io/FilesKt.writeText$default (Ljava/io/File;Ljava/lang/String;Ljava/nio/charset/Charset;ILjava/lang/Object;)V
      // 254: new java/io/File
      // 257: astore 2
      // 258: aload 1
      // 259: invokevirtual android/content/Context.getFilesDir ()Ljava/io/File;
      // 25c: astore 1
      // 25d: new java/lang/StringBuilder
      // 260: astore 4
      // 262: aload 4
      // 264: invokespecial java/lang/StringBuilder.<init> ()V
      // 267: aload 2
      // 268: aload 1
      // 269: aload 4
      // 26b: aload 15
      // 26d: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 270: aload 16
      // 272: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 275: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 278: invokespecial java/io/File.<init> (Ljava/io/File;Ljava/lang/String;)V
      // 27b: aload 13
      // 27d: aload 2
      // 27e: invokevirtual java/io/File.renameTo (Ljava/io/File;)Z
      // 281: ifne 292
      // 284: aload 13
      // 286: invokestatic kotlin/io/FilesKt.deleteRecursively (Ljava/io/File;)Z
      // 289: pop
      // 28a: aload 0
      // 28b: invokevirtual com/starpilot/starview/VoiceStudio.freeEngine ()V
      // 28e: ldc_w "Couldn't save it"
      // 291: areturn
      // 292: getstatic com/starpilot/starview/AmyVoice.INSTANCE Lcom/starpilot/starview/AmyVoice;
      // 295: astore 2
      // 296: new java/lang/StringBuilder
      // 299: astore 1
      // 29a: aload 1
      // 29b: invokespecial java/lang/StringBuilder.<init> ()V
      // 29e: aload 2
      // 29f: aload 1
      // 2a0: ldc_w "gen:"
      // 2a3: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 2a6: aload 16
      // 2a8: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 2ab: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 2ae: invokevirtual com/starpilot/starview/AmyVoice.use (Ljava/lang/String;)V
      // 2b1: aload 0
      // 2b2: invokevirtual com/starpilot/starview/VoiceStudio.freeEngine ()V
      // 2b5: aconst_null
      // 2b6: areturn
      // 2b7: aload 13
      // 2b9: astore 2
      // 2ba: astore 1
      // 2bb: goto 2c2
      // 2be: astore 1
      // 2bf: aload 14
      // 2c1: astore 2
      // 2c2: aload 2
      // 2c3: invokestatic kotlin/io/FilesKt.deleteRecursively (Ljava/io/File;)Z
      // 2c6: pop
      // 2c7: aload 1
      // 2c8: invokevirtual java/lang/Throwable.getMessage ()Ljava/lang/String;
      // 2cb: astore 1
      // 2cc: new java/lang/StringBuilder
      // 2cf: astore 2
      // 2d0: aload 2
      // 2d1: invokespecial java/lang/StringBuilder.<init> ()V
      // 2d4: aload 2
      // 2d5: ldc_w "Couldn't make the voice: "
      // 2d8: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 2db: aload 1
      // 2dc: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 2df: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 2e2: astore 1
      // 2e3: aload 0
      // 2e4: invokevirtual com/starpilot/starview/VoiceStudio.freeEngine ()V
      // 2e7: aload 1
      // 2e8: areturn
      // 2e9: astore 1
      // 2ea: aload 0
      // 2eb: invokevirtual com/starpilot/starview/VoiceStudio.freeEngine ()V
      // 2ee: aload 1
      // 2ef: athrow
   }

   public final List<Pair<String, String>> phrases(Context var1) {
      Intrinsics.checkNotNullParameter(var1, "c");
      InputStream var7 = var1.getAssets().open("voice_phrases.txt");
      Intrinsics.checkNotNullExpressionValue(var7, "open(...)");
      Reader var8 = new InputStreamReader(var7, Charsets.UTF_8);
      BufferedReader var9;
      if (var8 instanceof BufferedReader) {
         var9 = (BufferedReader)var8;
      } else {
         var9 = new BufferedReader(var8, 8192);
      }

      Iterable var10 = TextStreamsKt.readLines(var9);
      var var6 = new ArrayList();
      Iterator var5 = var10.iterator();

      while (var5.hasNext()) {
         List var4 = StringsKt.split$default((String & CharSequence)var5.next(), new char[]{'|'}, false, 2, 2, null);
         int var2 = var4.size();
         List var3 = var4;
         boolean var11;
         if (var2 == 2 && !StringsKt.isBlank((CharSequence)var4.get(0))) {
            var11 = true;
         } else {
            var11 = false;
         }

         Pair var12 = null;
         if (!var11) {
            var3 = null;
         }

         if (var3 != null) {
            var12 = TuplesKt.to(var3.get(0), var3.get(1));
         }

         if (var12 != null) {
            var6.add(var12);
         }
      }

      return var6;
   }

   public final List<float[]> say(Context param1, String param2, int param3, VoiceStudio.Style param4, List<String> param5) {
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
      // 02: aload 1
      // 03: ldc_w "c"
      // 06: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
      // 09: aload 2
      // 0a: ldc_w "key"
      // 0d: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
      // 10: aload 4
      // 12: ldc_w "st"
      // 15: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
      // 18: aload 5
      // 1a: ldc_w "texts"
      // 1d: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
      // 20: aload 0
      // 21: aload 1
      // 22: aload 2
      // 23: aload 4
      // 25: invokespecial com/starpilot/starview/VoiceStudio.engine (Landroid/content/Context;Ljava/lang/String;Lcom/starpilot/starview/VoiceStudio$Style;)Lcom/k2fsa/sherpa/onnx/OfflineTts;
      // 28: astore 1
      // 29: aload 5
      // 2b: checkcast java/lang/Iterable
      // 2e: astore 4
      // 30: new java/util/ArrayList
      // 33: astore 2
      // 34: aload 2
      // 35: aload 4
      // 37: bipush 10
      // 39: invokestatic kotlin/collections/CollectionsKt.collectionSizeOrDefault (Ljava/lang/Iterable;I)I
      // 3c: invokespecial java/util/ArrayList.<init> (I)V
      // 3f: aload 2
      // 40: checkcast java/util/Collection
      // 43: astore 2
      // 44: aload 4
      // 46: invokeinterface java/lang/Iterable.iterator ()Ljava/util/Iterator; 1
      // 4b: astore 5
      // 4d: aload 5
      // 4f: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 54: ifeq 83
      // 57: aload 1
      // 58: aload 5
      // 5a: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 5f: checkcast java/lang/String
      // 62: iload 3
      // 63: fconst_1
      // 64: invokevirtual com/k2fsa/sherpa/onnx/OfflineTts.generate (Ljava/lang/String;IF)Lcom/k2fsa/sherpa/onnx/GeneratedAudio;
      // 67: astore 4
      // 69: aload 2
      // 6a: getstatic com/starpilot/starview/VoiceDsp.INSTANCE Lcom/starpilot/starview/VoiceDsp;
      // 6d: aload 4
      // 6f: invokevirtual com/k2fsa/sherpa/onnx/GeneratedAudio.getSamples ()[F
      // 72: aload 4
      // 74: invokevirtual com/k2fsa/sherpa/onnx/GeneratedAudio.getSampleRate ()I
      // 77: invokevirtual com/starpilot/starview/VoiceDsp.clean ([FI)[F
      // 7a: invokeinterface java/util/Collection.add (Ljava/lang/Object;)Z 2
      // 7f: pop
      // 80: goto 4d
      // 83: aload 2
      // 84: checkcast java/util/List
      // 87: astore 1
      // 88: aload 0
      // 89: monitorexit
      // 8a: aload 1
      // 8b: areturn
      // 8c: astore 1
      // 8d: aload 0
      // 8e: monitorexit
      // 8f: aload 1
      // 90: athrow
   }

   public static final class Style {
      private final float expressive;
      private final float pace;
      private final float rhythm;

      public Style() {
         this(0.0F, 0.0F, 0.0F, 7, null);
      }

      public Style(float var1, float var2, float var3) {
         this.expressive = var1;
         this.rhythm = var2;
         this.pace = var3;
      }

      // $VF: synthetic method
      public Style(float var1, float var2, float var3, int var4, DefaultConstructorMarker var5) {
         if ((var4 & 1) != 0) {
            var1 = 0.667F;
         }

         if ((var4 & 2) != 0) {
            var2 = 0.8F;
         }

         if ((var4 & 4) != 0) {
            var3 = 1.08F;
         }

         this(var1, var2, var3);
      }

      // $VF: synthetic method
      public static VoiceStudio.Style copy$default(VoiceStudio.Style var0, float var1, float var2, float var3, int var4, Object var5) {
         if ((var4 & 1) != 0) {
            var1 = var0.expressive;
         }

         if ((var4 & 2) != 0) {
            var2 = var0.rhythm;
         }

         if ((var4 & 4) != 0) {
            var3 = var0.pace;
         }

         return var0.copy(var1, var2, var3);
      }

      public final float component1() {
         return this.expressive;
      }

      public final float component2() {
         return this.rhythm;
      }

      public final float component3() {
         return this.pace;
      }

      public final VoiceStudio.Style copy(float var1, float var2, float var3) {
         return new VoiceStudio.Style(var1, var2, var3);
      }

      @Override
      public boolean equals(Object var1) {
         if (this == var1) {
            return true;
         } else if (!(var1 instanceof VoiceStudio.Style)) {
            return false;
         } else {
            var1 = var1;
            if (Float.compare(this.expressive, var1.expressive) != 0) {
               return false;
            } else {
               return Float.compare(this.rhythm, var1.rhythm) != 0 ? false : Float.compare(this.pace, var1.pace) == 0;
            }
         }
      }

      public final float getExpressive() {
         return this.expressive;
      }

      public final float getPace() {
         return this.pace;
      }

      public final float getRhythm() {
         return this.rhythm;
      }

      @Override
      public int hashCode() {
         return (Float.hashCode(this.expressive) * 31 + Float.hashCode(this.rhythm)) * 31 + Float.hashCode(this.pace);
      }

      @Override
      public String toString() {
         float var1 = this.expressive;
         float var3 = this.rhythm;
         float var2 = this.pace;
         return "Style(expressive=" + var1 + ", rhythm=" + var3 + ", pace=" + var2 + ")";
      }
   }

   public static final class Voice {
      private final long bytes;
      private final String json;
      private final String key;
      private final String name;
      private final String onnx;
      private final String quality;
      private final String region;
      private final List<String> speakerNames;
      private final int speakers;

      // $VF: synthetic method
      public static CharSequence $r8$lambda$TvQFOiRMlXJZPCg_JoaqsgZ2QM8/* $VF was: $r8$lambda$TvQFOiRMlXJZPCg_JoaqsgZ2QM8*/(String var0) {
         return _get_title_$lambda$1(var0);
      }

      public Voice(String var1, String var2, String var3, String var4, int var5, List<String> var6, String var7, String var8, long var9) {
         Intrinsics.checkNotNullParameter(var1, "key");
         Intrinsics.checkNotNullParameter(var2, "name");
         Intrinsics.checkNotNullParameter(var3, "region");
         Intrinsics.checkNotNullParameter(var4, "quality");
         Intrinsics.checkNotNullParameter(var6, "speakerNames");
         Intrinsics.checkNotNullParameter(var7, "onnx");
         Intrinsics.checkNotNullParameter(var8, "json");
         super();
         this.key = var1;
         this.name = var2;
         this.region = var3;
         this.quality = var4;
         this.speakers = var5;
         this.speakerNames = var6;
         this.onnx = var7;
         this.json = var8;
         this.bytes = var9;
      }

      private static final CharSequence _get_title_$lambda$1(String var0) {
         Intrinsics.checkNotNullParameter(var0, "it");
         boolean var2;
         if (var0.length() > 0) {
            var2 = true;
         } else {
            var2 = false;
         }

         if (var2) {
            StringBuilder var3 = new StringBuilder();
            char var1 = var0.charAt(0);
            String var4 = String.valueOf(var1);
            Intrinsics.checkNotNull(var4, "null cannot be cast to non-null type java.lang.String");
            var4 = var4.toUpperCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(var4, "toUpperCase(...)");
            var3 = var3.append(var4);
            var0 = var0.substring(1);
            Intrinsics.checkNotNullExpressionValue(var0, "substring(...)");
            var0 = var3.append(var0).toString();
         }

         return var0;
      }

      // $VF: synthetic method
      public static VoiceStudio.Voice copy$default(
         VoiceStudio.Voice var0,
         String var1,
         String var2,
         String var3,
         String var4,
         int var5,
         List var6,
         String var7,
         String var8,
         long var9,
         int var11,
         Object var12
      ) {
         if ((var11 & 1) != 0) {
            var1 = var0.key;
         }

         if ((var11 & 2) != 0) {
            var2 = var0.name;
         }

         if ((var11 & 4) != 0) {
            var3 = var0.region;
         }

         if ((var11 & 8) != 0) {
            var4 = var0.quality;
         }

         if ((var11 & 16) != 0) {
            var5 = var0.speakers;
         }

         if ((var11 & 32) != 0) {
            var6 = var0.speakerNames;
         }

         if ((var11 & 64) != 0) {
            var7 = var0.onnx;
         }

         if ((var11 & 128) != 0) {
            var8 = var0.json;
         }

         if ((var11 & 256) != 0) {
            var9 = var0.bytes;
         }

         return var0.copy(var1, var2, var3, var4, var5, var6, var7, var8, var9);
      }

      public final String component1() {
         return this.key;
      }

      public final String component2() {
         return this.name;
      }

      public final String component3() {
         return this.region;
      }

      public final String component4() {
         return this.quality;
      }

      public final int component5() {
         return this.speakers;
      }

      public final List<String> component6() {
         return this.speakerNames;
      }

      public final String component7() {
         return this.onnx;
      }

      public final String component8() {
         return this.json;
      }

      public final long component9() {
         return this.bytes;
      }

      public final VoiceStudio.Voice copy(String var1, String var2, String var3, String var4, int var5, List<String> var6, String var7, String var8, long var9) {
         Intrinsics.checkNotNullParameter(var1, "key");
         Intrinsics.checkNotNullParameter(var2, "name");
         Intrinsics.checkNotNullParameter(var3, "region");
         Intrinsics.checkNotNullParameter(var4, "quality");
         Intrinsics.checkNotNullParameter(var6, "speakerNames");
         Intrinsics.checkNotNullParameter(var7, "onnx");
         Intrinsics.checkNotNullParameter(var8, "json");
         return new VoiceStudio.Voice(var1, var2, var3, var4, var5, var6, var7, var8, var9);
      }

      @Override
      public boolean equals(Object var1) {
         if (this == var1) {
            return true;
         } else if (!(var1 instanceof VoiceStudio.Voice)) {
            return false;
         } else {
            var1 = var1;
            if (!Intrinsics.areEqual(this.key, var1.key)) {
               return false;
            } else if (!Intrinsics.areEqual(this.name, var1.name)) {
               return false;
            } else if (!Intrinsics.areEqual(this.region, var1.region)) {
               return false;
            } else if (!Intrinsics.areEqual(this.quality, var1.quality)) {
               return false;
            } else if (this.speakers != var1.speakers) {
               return false;
            } else if (!Intrinsics.areEqual(this.speakerNames, var1.speakerNames)) {
               return false;
            } else if (!Intrinsics.areEqual(this.onnx, var1.onnx)) {
               return false;
            } else {
               return !Intrinsics.areEqual(this.json, var1.json) ? false : this.bytes == var1.bytes;
            }
         }
      }

      public final String getAccent() {
         String var1 = this.region;
         if (Intrinsics.areEqual(var1, "US")) {
            var1 = "American";
         } else if (Intrinsics.areEqual(var1, "GB")) {
            var1 = "British";
         } else {
            var1 = this.region;
         }

         return var1;
      }

      public final long getBytes() {
         return this.bytes;
      }

      public final String getJson() {
         return this.json;
      }

      public final String getKey() {
         return this.key;
      }

      public final String getName() {
         return this.name;
      }

      public final String getOnnx() {
         return this.onnx;
      }

      public final String getQuality() {
         return this.quality;
      }

      public final String getRegion() {
         return this.region;
      }

      public final List<String> getSpeakerNames() {
         return this.speakerNames;
      }

      public final int getSpeakers() {
         return this.speakers;
      }

      public final String getTitle() {
         return CollectionsKt.joinToString$default(
            StringsKt.split$default(StringsKt.replace$default(this.name, '_', ' ', false, 4, null), new char[]{' '}, false, 0, 6, null),
            " ",
            null,
            null,
            0,
            null,
            new VoiceStudio$Voice$$ExternalSyntheticLambda0(),
            30,
            null
         );
      }

      @Override
      public int hashCode() {
         return (
                  (
                           (
                                    (
                                             (((this.key.hashCode() * 31 + this.name.hashCode()) * 31 + this.region.hashCode()) * 31 + this.quality.hashCode())
                                                   * 31
                                                + Integer.hashCode(this.speakers)
                                          )
                                          * 31
                                       + this.speakerNames.hashCode()
                                 )
                                 * 31
                              + this.onnx.hashCode()
                        )
                        * 31
                     + this.json.hashCode()
               )
               * 31
            + Long.hashCode(this.bytes);
      }

      @Override
      public String toString() {
         String var4 = this.key;
         String var7 = this.name;
         String var5 = this.region;
         String var9 = this.quality;
         int var1 = this.speakers;
         List var10 = this.speakerNames;
         String var6 = this.onnx;
         String var8 = this.json;
         long var2 = this.bytes;
         return "Voice(key="
            + var4
            + ", name="
            + var7
            + ", region="
            + var5
            + ", quality="
            + var9
            + ", speakers="
            + var1
            + ", speakerNames="
            + var10
            + ", onnx="
            + var6
            + ", json="
            + var8
            + ", bytes="
            + var2
            + ")";
      }
   }
}
