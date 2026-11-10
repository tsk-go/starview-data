package com.k2fsa.sherpa.onnx;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class OfflineTtsVitsModelConfig {
   private String dataDir;
   private String dictDir;
   private float lengthScale;
   private String lexicon;
   private String model;
   private float noiseScale;
   private float noiseScaleW;
   private String tokens;

   public OfflineTtsVitsModelConfig() {
      this(null, null, null, null, null, 0.0F, 0.0F, 0.0F, 255, null);
   }

   public OfflineTtsVitsModelConfig(String var1, String var2, String var3, String var4, String var5, float var6, float var7, float var8) {
      Intrinsics.checkNotNullParameter(var1, "model");
      Intrinsics.checkNotNullParameter(var2, "lexicon");
      Intrinsics.checkNotNullParameter(var3, "tokens");
      Intrinsics.checkNotNullParameter(var4, "dataDir");
      Intrinsics.checkNotNullParameter(var5, "dictDir");
      super();
      this.model = var1;
      this.lexicon = var2;
      this.tokens = var3;
      this.dataDir = var4;
      this.dictDir = var5;
      this.noiseScale = var6;
      this.noiseScaleW = var7;
      this.lengthScale = var8;
   }

   // $VF: synthetic method
   public OfflineTtsVitsModelConfig(
      String var1, String var2, String var3, String var4, String var5, float var6, float var7, float var8, int var9, DefaultConstructorMarker var10
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
         var6 = 0.667F;
      }

      if ((var9 & 64) != 0) {
         var7 = 0.8F;
      }

      if ((var9 & 128) != 0) {
         var8 = 1.0F;
      }

      this(var1, var2, var3, var4, var5, var6, var7, var8);
   }

   // $VF: synthetic method
   public static OfflineTtsVitsModelConfig copy$default(
      OfflineTtsVitsModelConfig var0,
      String var1,
      String var2,
      String var3,
      String var4,
      String var5,
      float var6,
      float var7,
      float var8,
      int var9,
      Object var10
   ) {
      if ((var9 & 1) != 0) {
         var1 = var0.model;
      }

      if ((var9 & 2) != 0) {
         var2 = var0.lexicon;
      }

      if ((var9 & 4) != 0) {
         var3 = var0.tokens;
      }

      if ((var9 & 8) != 0) {
         var4 = var0.dataDir;
      }

      if ((var9 & 16) != 0) {
         var5 = var0.dictDir;
      }

      if ((var9 & 32) != 0) {
         var6 = var0.noiseScale;
      }

      if ((var9 & 64) != 0) {
         var7 = var0.noiseScaleW;
      }

      if ((var9 & 128) != 0) {
         var8 = var0.lengthScale;
      }

      return var0.copy(var1, var2, var3, var4, var5, var6, var7, var8);
   }

   public final String component1() {
      return this.model;
   }

   public final String component2() {
      return this.lexicon;
   }

   public final String component3() {
      return this.tokens;
   }

   public final String component4() {
      return this.dataDir;
   }

   public final String component5() {
      return this.dictDir;
   }

   public final float component6() {
      return this.noiseScale;
   }

   public final float component7() {
      return this.noiseScaleW;
   }

   public final float component8() {
      return this.lengthScale;
   }

   public final OfflineTtsVitsModelConfig copy(String var1, String var2, String var3, String var4, String var5, float var6, float var7, float var8) {
      Intrinsics.checkNotNullParameter(var1, "model");
      Intrinsics.checkNotNullParameter(var2, "lexicon");
      Intrinsics.checkNotNullParameter(var3, "tokens");
      Intrinsics.checkNotNullParameter(var4, "dataDir");
      Intrinsics.checkNotNullParameter(var5, "dictDir");
      return new OfflineTtsVitsModelConfig(var1, var2, var3, var4, var5, var6, var7, var8);
   }

   @Override
   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof OfflineTtsVitsModelConfig)) {
         return false;
      } else {
         var1 = var1;
         if (!Intrinsics.areEqual(this.model, var1.model)) {
            return false;
         } else if (!Intrinsics.areEqual(this.lexicon, var1.lexicon)) {
            return false;
         } else if (!Intrinsics.areEqual(this.tokens, var1.tokens)) {
            return false;
         } else if (!Intrinsics.areEqual(this.dataDir, var1.dataDir)) {
            return false;
         } else if (!Intrinsics.areEqual(this.dictDir, var1.dictDir)) {
            return false;
         } else if (Float.compare(this.noiseScale, var1.noiseScale) != 0) {
            return false;
         } else {
            return Float.compare(this.noiseScaleW, var1.noiseScaleW) != 0 ? false : Float.compare(this.lengthScale, var1.lengthScale) == 0;
         }
      }
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

   public final String getModel() {
      return this.model;
   }

   public final float getNoiseScale() {
      return this.noiseScale;
   }

   public final float getNoiseScaleW() {
      return this.noiseScaleW;
   }

   public final String getTokens() {
      return this.tokens;
   }

   @Override
   public int hashCode() {
      return (
               (
                        (
                                 (((this.model.hashCode() * 31 + this.lexicon.hashCode()) * 31 + this.tokens.hashCode()) * 31 + this.dataDir.hashCode()) * 31
                                    + this.dictDir.hashCode()
                              )
                              * 31
                           + Float.hashCode(this.noiseScale)
                     )
                     * 31
                  + Float.hashCode(this.noiseScaleW)
            )
            * 31
         + Float.hashCode(this.lengthScale);
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

   public final void setModel(String var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.model = var1;
   }

   public final void setNoiseScale(float var1) {
      this.noiseScale = var1;
   }

   public final void setNoiseScaleW(float var1) {
      this.noiseScaleW = var1;
   }

   public final void setTokens(String var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.tokens = var1;
   }

   @Override
   public String toString() {
      String var6 = this.model;
      String var5 = this.lexicon;
      String var7 = this.tokens;
      String var8 = this.dataDir;
      String var4 = this.dictDir;
      float var3 = this.noiseScale;
      float var1 = this.noiseScaleW;
      float var2 = this.lengthScale;
      return "OfflineTtsVitsModelConfig(model="
         + var6
         + ", lexicon="
         + var5
         + ", tokens="
         + var7
         + ", dataDir="
         + var8
         + ", dictDir="
         + var4
         + ", noiseScale="
         + var3
         + ", noiseScaleW="
         + var1
         + ", lengthScale="
         + var2
         + ")";
   }
}
