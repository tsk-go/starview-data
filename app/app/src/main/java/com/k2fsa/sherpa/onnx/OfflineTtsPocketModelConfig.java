package com.k2fsa.sherpa.onnx;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class OfflineTtsPocketModelConfig {
   private String decoder;
   private String encoder;
   private String lmFlow;
   private String lmMain;
   private String textConditioner;
   private String tokenScoresJson;
   private String vocabJson;
   private int voiceEmbeddingCacheCapacity;

   public OfflineTtsPocketModelConfig() {
      this(null, null, null, null, null, null, null, 0, 255, null);
   }

   public OfflineTtsPocketModelConfig(String var1, String var2, String var3, String var4, String var5, String var6, String var7, int var8) {
      Intrinsics.checkNotNullParameter(var1, "lmFlow");
      Intrinsics.checkNotNullParameter(var2, "lmMain");
      Intrinsics.checkNotNullParameter(var3, "encoder");
      Intrinsics.checkNotNullParameter(var4, "decoder");
      Intrinsics.checkNotNullParameter(var5, "textConditioner");
      Intrinsics.checkNotNullParameter(var6, "vocabJson");
      Intrinsics.checkNotNullParameter(var7, "tokenScoresJson");
      super();
      this.lmFlow = var1;
      this.lmMain = var2;
      this.encoder = var3;
      this.decoder = var4;
      this.textConditioner = var5;
      this.vocabJson = var6;
      this.tokenScoresJson = var7;
      this.voiceEmbeddingCacheCapacity = var8;
   }

   // $VF: synthetic method
   public OfflineTtsPocketModelConfig(
      String var1, String var2, String var3, String var4, String var5, String var6, String var7, int var8, int var9, DefaultConstructorMarker var10
   ) {
      if ((var9 & 1) != 0) {
         var1 = "";
      }

      if ((var9 & 2) != 0) {
         var2 = "";
      }

      if ((var9 & 4) != 0) {
         var3 = "";
      }

      if ((var9 & 8) != 0) {
         var4 = "";
      }

      if ((var9 & 16) != 0) {
         var5 = "";
      }

      if ((var9 & 32) != 0) {
         var6 = "";
      }

      if ((var9 & 64) != 0) {
         var7 = "";
      }

      if ((var9 & 128) != 0) {
         var8 = 50;
      }

      this(var1, var2, var3, var4, var5, var6, var7, var8);
   }

   // $VF: synthetic method
   public static OfflineTtsPocketModelConfig copy$default(
      OfflineTtsPocketModelConfig var0,
      String var1,
      String var2,
      String var3,
      String var4,
      String var5,
      String var6,
      String var7,
      int var8,
      int var9,
      Object var10
   ) {
      if ((var9 & 1) != 0) {
         var1 = var0.lmFlow;
      }

      if ((var9 & 2) != 0) {
         var2 = var0.lmMain;
      }

      if ((var9 & 4) != 0) {
         var3 = var0.encoder;
      }

      if ((var9 & 8) != 0) {
         var4 = var0.decoder;
      }

      if ((var9 & 16) != 0) {
         var5 = var0.textConditioner;
      }

      if ((var9 & 32) != 0) {
         var6 = var0.vocabJson;
      }

      if ((var9 & 64) != 0) {
         var7 = var0.tokenScoresJson;
      }

      if ((var9 & 128) != 0) {
         var8 = var0.voiceEmbeddingCacheCapacity;
      }

      return var0.copy(var1, var2, var3, var4, var5, var6, var7, var8);
   }

   public final String component1() {
      return this.lmFlow;
   }

   public final String component2() {
      return this.lmMain;
   }

   public final String component3() {
      return this.encoder;
   }

   public final String component4() {
      return this.decoder;
   }

   public final String component5() {
      return this.textConditioner;
   }

   public final String component6() {
      return this.vocabJson;
   }

   public final String component7() {
      return this.tokenScoresJson;
   }

   public final int component8() {
      return this.voiceEmbeddingCacheCapacity;
   }

   public final OfflineTtsPocketModelConfig copy(String var1, String var2, String var3, String var4, String var5, String var6, String var7, int var8) {
      Intrinsics.checkNotNullParameter(var1, "lmFlow");
      Intrinsics.checkNotNullParameter(var2, "lmMain");
      Intrinsics.checkNotNullParameter(var3, "encoder");
      Intrinsics.checkNotNullParameter(var4, "decoder");
      Intrinsics.checkNotNullParameter(var5, "textConditioner");
      Intrinsics.checkNotNullParameter(var6, "vocabJson");
      Intrinsics.checkNotNullParameter(var7, "tokenScoresJson");
      return new OfflineTtsPocketModelConfig(var1, var2, var3, var4, var5, var6, var7, var8);
   }

   @Override
   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof OfflineTtsPocketModelConfig)) {
         return false;
      } else {
         var1 = var1;
         if (!Intrinsics.areEqual(this.lmFlow, var1.lmFlow)) {
            return false;
         } else if (!Intrinsics.areEqual(this.lmMain, var1.lmMain)) {
            return false;
         } else if (!Intrinsics.areEqual(this.encoder, var1.encoder)) {
            return false;
         } else if (!Intrinsics.areEqual(this.decoder, var1.decoder)) {
            return false;
         } else if (!Intrinsics.areEqual(this.textConditioner, var1.textConditioner)) {
            return false;
         } else if (!Intrinsics.areEqual(this.vocabJson, var1.vocabJson)) {
            return false;
         } else {
            return !Intrinsics.areEqual(this.tokenScoresJson, var1.tokenScoresJson)
               ? false
               : this.voiceEmbeddingCacheCapacity == var1.voiceEmbeddingCacheCapacity;
         }
      }
   }

   public final String getDecoder() {
      return this.decoder;
   }

   public final String getEncoder() {
      return this.encoder;
   }

   public final String getLmFlow() {
      return this.lmFlow;
   }

   public final String getLmMain() {
      return this.lmMain;
   }

   public final String getTextConditioner() {
      return this.textConditioner;
   }

   public final String getTokenScoresJson() {
      return this.tokenScoresJson;
   }

   public final String getVocabJson() {
      return this.vocabJson;
   }

   public final int getVoiceEmbeddingCacheCapacity() {
      return this.voiceEmbeddingCacheCapacity;
   }

   @Override
   public int hashCode() {
      return (
               (
                        (
                                 (((this.lmFlow.hashCode() * 31 + this.lmMain.hashCode()) * 31 + this.encoder.hashCode()) * 31 + this.decoder.hashCode()) * 31
                                    + this.textConditioner.hashCode()
                              )
                              * 31
                           + this.vocabJson.hashCode()
                     )
                     * 31
                  + this.tokenScoresJson.hashCode()
            )
            * 31
         + Integer.hashCode(this.voiceEmbeddingCacheCapacity);
   }

   public final void setDecoder(String var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.decoder = var1;
   }

   public final void setEncoder(String var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.encoder = var1;
   }

   public final void setLmFlow(String var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.lmFlow = var1;
   }

   public final void setLmMain(String var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.lmMain = var1;
   }

   public final void setTextConditioner(String var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.textConditioner = var1;
   }

   public final void setTokenScoresJson(String var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.tokenScoresJson = var1;
   }

   public final void setVocabJson(String var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.vocabJson = var1;
   }

   public final void setVoiceEmbeddingCacheCapacity(int var1) {
      this.voiceEmbeddingCacheCapacity = var1;
   }

   @Override
   public String toString() {
      String var4 = this.lmFlow;
      String var7 = this.lmMain;
      String var2 = this.encoder;
      String var5 = this.decoder;
      String var8 = this.textConditioner;
      String var3 = this.vocabJson;
      String var6 = this.tokenScoresJson;
      int var1 = this.voiceEmbeddingCacheCapacity;
      return "OfflineTtsPocketModelConfig(lmFlow="
         + var4
         + ", lmMain="
         + var7
         + ", encoder="
         + var2
         + ", decoder="
         + var5
         + ", textConditioner="
         + var8
         + ", vocabJson="
         + var3
         + ", tokenScoresJson="
         + var6
         + ", voiceEmbeddingCacheCapacity="
         + var1
         + ")";
   }
}
