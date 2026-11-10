package com.k2fsa.sherpa.onnx;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class OfflineTtsZipVoiceModelConfig {
   private String dataDir;
   private String decoder;
   private String encoder;
   private float featScale;
   private float guidanceScale;
   private String lexicon;
   private float tShift;
   private float targetRms;
   private String tokens;
   private String vocoder;

   public OfflineTtsZipVoiceModelConfig() {
      this(null, null, null, null, null, null, 0.0F, 0.0F, 0.0F, 0.0F, 1023, null);
   }

   public OfflineTtsZipVoiceModelConfig(
      String var1, String var2, String var3, String var4, String var5, String var6, float var7, float var8, float var9, float var10
   ) {
      Intrinsics.checkNotNullParameter(var1, "tokens");
      Intrinsics.checkNotNullParameter(var2, "encoder");
      Intrinsics.checkNotNullParameter(var3, "decoder");
      Intrinsics.checkNotNullParameter(var4, "vocoder");
      Intrinsics.checkNotNullParameter(var5, "dataDir");
      Intrinsics.checkNotNullParameter(var6, "lexicon");
      super();
      this.tokens = var1;
      this.encoder = var2;
      this.decoder = var3;
      this.vocoder = var4;
      this.dataDir = var5;
      this.lexicon = var6;
      this.featScale = var7;
      this.tShift = var8;
      this.targetRms = var9;
      this.guidanceScale = var10;
   }

   // $VF: synthetic method
   public OfflineTtsZipVoiceModelConfig(
      String var1,
      String var2,
      String var3,
      String var4,
      String var5,
      String var6,
      float var7,
      float var8,
      float var9,
      float var10,
      int var11,
      DefaultConstructorMarker var12
   ) {
      if ((var11 & 1) != 0) {
         var1 = "";
      }

      if ((var11 & 2) != 0) {
         var2 = "";
      }

      if ((var11 & 4) != 0) {
         var3 = "";
      }

      if ((var11 & 8) != 0) {
         var4 = "";
      }

      if ((var11 & 16) != 0) {
         var5 = "";
      }

      if ((var11 & 32) != 0) {
         var6 = "";
      }

      if ((var11 & 64) != 0) {
         var7 = 0.1F;
      }

      if ((var11 & 128) != 0) {
         var8 = 0.5F;
      }

      if ((var11 & 256) != 0) {
         var9 = 0.1F;
      }

      if ((var11 & 512) != 0) {
         var10 = 1.0F;
      }

      this(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10);
   }

   // $VF: synthetic method
   public static OfflineTtsZipVoiceModelConfig copy$default(
      OfflineTtsZipVoiceModelConfig var0,
      String var1,
      String var2,
      String var3,
      String var4,
      String var5,
      String var6,
      float var7,
      float var8,
      float var9,
      float var10,
      int var11,
      Object var12
   ) {
      if ((var11 & 1) != 0) {
         var1 = var0.tokens;
      }

      if ((var11 & 2) != 0) {
         var2 = var0.encoder;
      }

      if ((var11 & 4) != 0) {
         var3 = var0.decoder;
      }

      if ((var11 & 8) != 0) {
         var4 = var0.vocoder;
      }

      if ((var11 & 16) != 0) {
         var5 = var0.dataDir;
      }

      if ((var11 & 32) != 0) {
         var6 = var0.lexicon;
      }

      if ((var11 & 64) != 0) {
         var7 = var0.featScale;
      }

      if ((var11 & 128) != 0) {
         var8 = var0.tShift;
      }

      if ((var11 & 256) != 0) {
         var9 = var0.targetRms;
      }

      if ((var11 & 512) != 0) {
         var10 = var0.guidanceScale;
      }

      return var0.copy(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10);
   }

   public final String component1() {
      return this.tokens;
   }

   public final float component10() {
      return this.guidanceScale;
   }

   public final String component2() {
      return this.encoder;
   }

   public final String component3() {
      return this.decoder;
   }

   public final String component4() {
      return this.vocoder;
   }

   public final String component5() {
      return this.dataDir;
   }

   public final String component6() {
      return this.lexicon;
   }

   public final float component7() {
      return this.featScale;
   }

   public final float component8() {
      return this.tShift;
   }

   public final float component9() {
      return this.targetRms;
   }

   public final OfflineTtsZipVoiceModelConfig copy(
      String var1, String var2, String var3, String var4, String var5, String var6, float var7, float var8, float var9, float var10
   ) {
      Intrinsics.checkNotNullParameter(var1, "tokens");
      Intrinsics.checkNotNullParameter(var2, "encoder");
      Intrinsics.checkNotNullParameter(var3, "decoder");
      Intrinsics.checkNotNullParameter(var4, "vocoder");
      Intrinsics.checkNotNullParameter(var5, "dataDir");
      Intrinsics.checkNotNullParameter(var6, "lexicon");
      return new OfflineTtsZipVoiceModelConfig(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10);
   }

   @Override
   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof OfflineTtsZipVoiceModelConfig)) {
         return false;
      } else {
         var1 = var1;
         if (!Intrinsics.areEqual(this.tokens, var1.tokens)) {
            return false;
         } else if (!Intrinsics.areEqual(this.encoder, var1.encoder)) {
            return false;
         } else if (!Intrinsics.areEqual(this.decoder, var1.decoder)) {
            return false;
         } else if (!Intrinsics.areEqual(this.vocoder, var1.vocoder)) {
            return false;
         } else if (!Intrinsics.areEqual(this.dataDir, var1.dataDir)) {
            return false;
         } else if (!Intrinsics.areEqual(this.lexicon, var1.lexicon)) {
            return false;
         } else if (Float.compare(this.featScale, var1.featScale) != 0) {
            return false;
         } else if (Float.compare(this.tShift, var1.tShift) != 0) {
            return false;
         } else {
            return Float.compare(this.targetRms, var1.targetRms) != 0 ? false : Float.compare(this.guidanceScale, var1.guidanceScale) == 0;
         }
      }
   }

   public final String getDataDir() {
      return this.dataDir;
   }

   public final String getDecoder() {
      return this.decoder;
   }

   public final String getEncoder() {
      return this.encoder;
   }

   public final float getFeatScale() {
      return this.featScale;
   }

   public final float getGuidanceScale() {
      return this.guidanceScale;
   }

   public final String getLexicon() {
      return this.lexicon;
   }

   public final float getTShift() {
      return this.tShift;
   }

   public final float getTargetRms() {
      return this.targetRms;
   }

   public final String getTokens() {
      return this.tokens;
   }

   public final String getVocoder() {
      return this.vocoder;
   }

   @Override
   public int hashCode() {
      return (
               (
                        (
                                 (
                                          (
                                                   (
                                                            ((this.tokens.hashCode() * 31 + this.encoder.hashCode()) * 31 + this.decoder.hashCode()) * 31
                                                               + this.vocoder.hashCode()
                                                         )
                                                         * 31
                                                      + this.dataDir.hashCode()
                                                )
                                                * 31
                                             + this.lexicon.hashCode()
                                       )
                                       * 31
                                    + Float.hashCode(this.featScale)
                              )
                              * 31
                           + Float.hashCode(this.tShift)
                     )
                     * 31
                  + Float.hashCode(this.targetRms)
            )
            * 31
         + Float.hashCode(this.guidanceScale);
   }

   public final void setDataDir(String var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.dataDir = var1;
   }

   public final void setDecoder(String var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.decoder = var1;
   }

   public final void setEncoder(String var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.encoder = var1;
   }

   public final void setFeatScale(float var1) {
      this.featScale = var1;
   }

   public final void setGuidanceScale(float var1) {
      this.guidanceScale = var1;
   }

   public final void setLexicon(String var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.lexicon = var1;
   }

   public final void setTShift(float var1) {
      this.tShift = var1;
   }

   public final void setTargetRms(float var1) {
      this.targetRms = var1;
   }

   public final void setTokens(String var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.tokens = var1;
   }

   public final void setVocoder(String var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.vocoder = var1;
   }

   @Override
   public String toString() {
      String var5 = this.tokens;
      String var6 = this.encoder;
      String var8 = this.decoder;
      String var9 = this.vocoder;
      String var10 = this.dataDir;
      String var7 = this.lexicon;
      float var1 = this.featScale;
      float var3 = this.tShift;
      float var2 = this.targetRms;
      float var4 = this.guidanceScale;
      return "OfflineTtsZipVoiceModelConfig(tokens="
         + var5
         + ", encoder="
         + var6
         + ", decoder="
         + var8
         + ", vocoder="
         + var9
         + ", dataDir="
         + var10
         + ", lexicon="
         + var7
         + ", featScale="
         + var1
         + ", tShift="
         + var3
         + ", targetRms="
         + var2
         + ", guidanceScale="
         + var4
         + ")";
   }
}
