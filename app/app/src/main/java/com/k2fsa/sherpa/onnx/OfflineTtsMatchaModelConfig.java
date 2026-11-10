package com.k2fsa.sherpa.onnx;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class OfflineTtsMatchaModelConfig {
   private String acousticModel;
   private String dataDir;
   private String dictDir;
   private float lengthScale;
   private String lexicon;
   private float noiseScale;
   private String tokens;
   private String vocoder;

   public OfflineTtsMatchaModelConfig() {
      this(null, null, null, null, null, null, 0.0F, 0.0F, 255, null);
   }

   public OfflineTtsMatchaModelConfig(String var1, String var2, String var3, String var4, String var5, String var6, float var7, float var8) {
      Intrinsics.checkNotNullParameter(var1, "acousticModel");
      Intrinsics.checkNotNullParameter(var2, "vocoder");
      Intrinsics.checkNotNullParameter(var3, "lexicon");
      Intrinsics.checkNotNullParameter(var4, "tokens");
      Intrinsics.checkNotNullParameter(var5, "dataDir");
      Intrinsics.checkNotNullParameter(var6, "dictDir");
      super();
      this.acousticModel = var1;
      this.vocoder = var2;
      this.lexicon = var3;
      this.tokens = var4;
      this.dataDir = var5;
      this.dictDir = var6;
      this.noiseScale = var7;
      this.lengthScale = var8;
   }

   // $VF: synthetic method
   public OfflineTtsMatchaModelConfig(
      String var1, String var2, String var3, String var4, String var5, String var6, float var7, float var8, int var9, DefaultConstructorMarker var10
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
         var7 = 1.0F;
      }

      if ((var9 & 128) != 0) {
         var8 = 1.0F;
      }

      this(var1, var2, var3, var4, var5, var6, var7, var8);
   }

   // $VF: synthetic method
   public static OfflineTtsMatchaModelConfig copy$default(
      OfflineTtsMatchaModelConfig var0,
      String var1,
      String var2,
      String var3,
      String var4,
      String var5,
      String var6,
      float var7,
      float var8,
      int var9,
      Object var10
   ) {
      if ((var9 & 1) != 0) {
         var1 = var0.acousticModel;
      }

      if ((var9 & 2) != 0) {
         var2 = var0.vocoder;
      }

      if ((var9 & 4) != 0) {
         var3 = var0.lexicon;
      }

      if ((var9 & 8) != 0) {
         var4 = var0.tokens;
      }

      if ((var9 & 16) != 0) {
         var5 = var0.dataDir;
      }

      if ((var9 & 32) != 0) {
         var6 = var0.dictDir;
      }

      if ((var9 & 64) != 0) {
         var7 = var0.noiseScale;
      }

      if ((var9 & 128) != 0) {
         var8 = var0.lengthScale;
      }

      return var0.copy(var1, var2, var3, var4, var5, var6, var7, var8);
   }

   public final String component1() {
      return this.acousticModel;
   }

   public final String component2() {
      return this.vocoder;
   }

   public final String component3() {
      return this.lexicon;
   }

   public final String component4() {
      return this.tokens;
   }

   public final String component5() {
      return this.dataDir;
   }

   public final String component6() {
      return this.dictDir;
   }

   public final float component7() {
      return this.noiseScale;
   }

   public final float component8() {
      return this.lengthScale;
   }

   public final OfflineTtsMatchaModelConfig copy(String var1, String var2, String var3, String var4, String var5, String var6, float var7, float var8) {
      Intrinsics.checkNotNullParameter(var1, "acousticModel");
      Intrinsics.checkNotNullParameter(var2, "vocoder");
      Intrinsics.checkNotNullParameter(var3, "lexicon");
      Intrinsics.checkNotNullParameter(var4, "tokens");
      Intrinsics.checkNotNullParameter(var5, "dataDir");
      Intrinsics.checkNotNullParameter(var6, "dictDir");
      return new OfflineTtsMatchaModelConfig(var1, var2, var3, var4, var5, var6, var7, var8);
   }

   @Override
   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof OfflineTtsMatchaModelConfig)) {
         return false;
      } else {
         var1 = var1;
         if (!Intrinsics.areEqual(this.acousticModel, var1.acousticModel)) {
            return false;
         } else if (!Intrinsics.areEqual(this.vocoder, var1.vocoder)) {
            return false;
         } else if (!Intrinsics.areEqual(this.lexicon, var1.lexicon)) {
            return false;
         } else if (!Intrinsics.areEqual(this.tokens, var1.tokens)) {
            return false;
         } else if (!Intrinsics.areEqual(this.dataDir, var1.dataDir)) {
            return false;
         } else if (!Intrinsics.areEqual(this.dictDir, var1.dictDir)) {
            return false;
         } else {
            return Float.compare(this.noiseScale, var1.noiseScale) != 0 ? false : Float.compare(this.lengthScale, var1.lengthScale) == 0;
         }
      }
   }

   public final String getAcousticModel() {
      return this.acousticModel;
   }

   public final String getDataDir() {
      return this.dataDir;
   }

   public final String getDictDir() {
      return this.dictDir;
   }

   public final float getLengthScale() {
      return this.lengthScale;
   }

   public final String getLexicon() {
      return this.lexicon;
   }

   public final float getNoiseScale() {
      return this.noiseScale;
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
                                 (((this.acousticModel.hashCode() * 31 + this.vocoder.hashCode()) * 31 + this.lexicon.hashCode()) * 31 + this.tokens.hashCode())
                                       * 31
                                    + this.dataDir.hashCode()
                              )
                              * 31
                           + this.dictDir.hashCode()
                     )
                     * 31
                  + Float.hashCode(this.noiseScale)
            )
            * 31
         + Float.hashCode(this.lengthScale);
   }

   public final void setAcousticModel(String var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.acousticModel = var1;
   }

   public final void setDataDir(String var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.dataDir = var1;
   }

   public final void setDictDir(String var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.dictDir = var1;
   }

   public final void setLengthScale(float var1) {
      this.lengthScale = var1;
   }

   public final void setLexicon(String var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.lexicon = var1;
   }

   public final void setNoiseScale(float var1) {
      this.noiseScale = var1;
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
      String var6 = this.acousticModel;
      String var8 = this.vocoder;
      String var3 = this.lexicon;
      String var5 = this.tokens;
      String var4 = this.dataDir;
      String var7 = this.dictDir;
      float var2 = this.noiseScale;
      float var1 = this.lengthScale;
      return "OfflineTtsMatchaModelConfig(acousticModel="
         + var6
         + ", vocoder="
         + var8
         + ", lexicon="
         + var3
         + ", tokens="
         + var5
         + ", dataDir="
         + var4
         + ", dictDir="
         + var7
         + ", noiseScale="
         + var2
         + ", lengthScale="
         + var1
         + ")";
   }
}
