package com.k2fsa.sherpa.onnx;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class OfflineTtsKokoroModelConfig {
   private String dataDir;
   private String dictDir;
   private String lang;
   private float lengthScale;
   private String lexicon;
   private String model;
   private String tokens;
   private String voices;

   public OfflineTtsKokoroModelConfig() {
      this(null, null, null, null, null, null, null, 0.0F, 255, null);
   }

   public OfflineTtsKokoroModelConfig(String var1, String var2, String var3, String var4, String var5, String var6, String var7, float var8) {
      Intrinsics.checkNotNullParameter(var1, "model");
      Intrinsics.checkNotNullParameter(var2, "voices");
      Intrinsics.checkNotNullParameter(var3, "tokens");
      Intrinsics.checkNotNullParameter(var4, "dataDir");
      Intrinsics.checkNotNullParameter(var5, "lexicon");
      Intrinsics.checkNotNullParameter(var6, "lang");
      Intrinsics.checkNotNullParameter(var7, "dictDir");
      super();
      this.model = var1;
      this.voices = var2;
      this.tokens = var3;
      this.dataDir = var4;
      this.lexicon = var5;
      this.lang = var6;
      this.dictDir = var7;
      this.lengthScale = var8;
   }

   // $VF: synthetic method
   public OfflineTtsKokoroModelConfig(
      String var1, String var2, String var3, String var4, String var5, String var6, String var7, float var8, int var9, DefaultConstructorMarker var10
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
         var8 = 1.0F;
      }

      this(var1, var2, var3, var4, var5, var6, var7, var8);
   }

   // $VF: synthetic method
   public static OfflineTtsKokoroModelConfig copy$default(
      OfflineTtsKokoroModelConfig var0,
      String var1,
      String var2,
      String var3,
      String var4,
      String var5,
      String var6,
      String var7,
      float var8,
      int var9,
      Object var10
   ) {
      if ((var9 & 1) != 0) {
         var1 = var0.model;
      }

      if ((var9 & 2) != 0) {
         var2 = var0.voices;
      }

      if ((var9 & 4) != 0) {
         var3 = var0.tokens;
      }

      if ((var9 & 8) != 0) {
         var4 = var0.dataDir;
      }

      if ((var9 & 16) != 0) {
         var5 = var0.lexicon;
      }

      if ((var9 & 32) != 0) {
         var6 = var0.lang;
      }

      if ((var9 & 64) != 0) {
         var7 = var0.dictDir;
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
      return this.voices;
   }

   public final String component3() {
      return this.tokens;
   }

   public final String component4() {
      return this.dataDir;
   }

   public final String component5() {
      return this.lexicon;
   }

   public final String component6() {
      return this.lang;
   }

   public final String component7() {
      return this.dictDir;
   }

   public final float component8() {
      return this.lengthScale;
   }

   public final OfflineTtsKokoroModelConfig copy(String var1, String var2, String var3, String var4, String var5, String var6, String var7, float var8) {
      Intrinsics.checkNotNullParameter(var1, "model");
      Intrinsics.checkNotNullParameter(var2, "voices");
      Intrinsics.checkNotNullParameter(var3, "tokens");
      Intrinsics.checkNotNullParameter(var4, "dataDir");
      Intrinsics.checkNotNullParameter(var5, "lexicon");
      Intrinsics.checkNotNullParameter(var6, "lang");
      Intrinsics.checkNotNullParameter(var7, "dictDir");
      return new OfflineTtsKokoroModelConfig(var1, var2, var3, var4, var5, var6, var7, var8);
   }

   @Override
   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof OfflineTtsKokoroModelConfig)) {
         return false;
      } else {
         var1 = var1;
         if (!Intrinsics.areEqual(this.model, var1.model)) {
            return false;
         } else if (!Intrinsics.areEqual(this.voices, var1.voices)) {
            return false;
         } else if (!Intrinsics.areEqual(this.tokens, var1.tokens)) {
            return false;
         } else if (!Intrinsics.areEqual(this.dataDir, var1.dataDir)) {
            return false;
         } else if (!Intrinsics.areEqual(this.lexicon, var1.lexicon)) {
            return false;
         } else if (!Intrinsics.areEqual(this.lang, var1.lang)) {
            return false;
         } else {
            return !Intrinsics.areEqual(this.dictDir, var1.dictDir) ? false : Float.compare(this.lengthScale, var1.lengthScale) == 0;
         }
      }
   }

   public final String getDataDir() {
      return this.dataDir;
   }

   public final String getDictDir() {
      return this.dictDir;
   }

   public final String getLang() {
      return this.lang;
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

   public final String getTokens() {
      return this.tokens;
   }

   public final String getVoices() {
      return this.voices;
   }

   @Override
   public int hashCode() {
      return (
               (
                        (
                                 (((this.model.hashCode() * 31 + this.voices.hashCode()) * 31 + this.tokens.hashCode()) * 31 + this.dataDir.hashCode()) * 31
                                    + this.lexicon.hashCode()
                              )
                              * 31
                           + this.lang.hashCode()
                     )
                     * 31
                  + this.dictDir.hashCode()
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

   public final void setLang(String var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.lang = var1;
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

   public final void setTokens(String var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.tokens = var1;
   }

   public final void setVoices(String var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.voices = var1;
   }

   @Override
   public String toString() {
      String var2 = this.model;
      String var6 = this.voices;
      String var7 = this.tokens;
      String var8 = this.dataDir;
      String var3 = this.lexicon;
      String var4 = this.lang;
      String var5 = this.dictDir;
      float var1 = this.lengthScale;
      return "OfflineTtsKokoroModelConfig(model="
         + var2
         + ", voices="
         + var6
         + ", tokens="
         + var7
         + ", dataDir="
         + var8
         + ", lexicon="
         + var3
         + ", lang="
         + var4
         + ", dictDir="
         + var5
         + ", lengthScale="
         + var1
         + ")";
   }
}
