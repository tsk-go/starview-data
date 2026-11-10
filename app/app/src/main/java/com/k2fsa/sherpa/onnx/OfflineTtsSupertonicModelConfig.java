package com.k2fsa.sherpa.onnx;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class OfflineTtsSupertonicModelConfig {
   private String durationPredictor;
   private String textEncoder;
   private String ttsJson;
   private String unicodeIndexer;
   private String vectorEstimator;
   private String vocoder;
   private String voiceStyle;

   public OfflineTtsSupertonicModelConfig() {
      this(null, null, null, null, null, null, null, 127, null);
   }

   public OfflineTtsSupertonicModelConfig(String var1, String var2, String var3, String var4, String var5, String var6, String var7) {
      Intrinsics.checkNotNullParameter(var1, "durationPredictor");
      Intrinsics.checkNotNullParameter(var2, "textEncoder");
      Intrinsics.checkNotNullParameter(var3, "vectorEstimator");
      Intrinsics.checkNotNullParameter(var4, "vocoder");
      Intrinsics.checkNotNullParameter(var5, "ttsJson");
      Intrinsics.checkNotNullParameter(var6, "unicodeIndexer");
      Intrinsics.checkNotNullParameter(var7, "voiceStyle");
      super();
      this.durationPredictor = var1;
      this.textEncoder = var2;
      this.vectorEstimator = var3;
      this.vocoder = var4;
      this.ttsJson = var5;
      this.unicodeIndexer = var6;
      this.voiceStyle = var7;
   }

   // $VF: synthetic method
   public OfflineTtsSupertonicModelConfig(
      String var1, String var2, String var3, String var4, String var5, String var6, String var7, int var8, DefaultConstructorMarker var9
   ) {
      if ((var8 & 1) != 0) {
         var1 = "";
      }

      if ((var8 & 2) != 0) {
         var2 = "";
      }

      if ((var8 & 4) != 0) {
         var3 = "";
      }

      if ((var8 & 8) != 0) {
         var4 = "";
      }

      if ((var8 & 16) != 0) {
         var5 = "";
      }

      if ((var8 & 32) != 0) {
         var6 = "";
      }

      if ((var8 & 64) != 0) {
         var7 = "";
      }

      this(var1, var2, var3, var4, var5, var6, var7);
   }

   // $VF: synthetic method
   public static OfflineTtsSupertonicModelConfig copy$default(
      OfflineTtsSupertonicModelConfig var0, String var1, String var2, String var3, String var4, String var5, String var6, String var7, int var8, Object var9
   ) {
      if ((var8 & 1) != 0) {
         var1 = var0.durationPredictor;
      }

      if ((var8 & 2) != 0) {
         var2 = var0.textEncoder;
      }

      if ((var8 & 4) != 0) {
         var3 = var0.vectorEstimator;
      }

      if ((var8 & 8) != 0) {
         var4 = var0.vocoder;
      }

      if ((var8 & 16) != 0) {
         var5 = var0.ttsJson;
      }

      if ((var8 & 32) != 0) {
         var6 = var0.unicodeIndexer;
      }

      if ((var8 & 64) != 0) {
         var7 = var0.voiceStyle;
      }

      return var0.copy(var1, var2, var3, var4, var5, var6, var7);
   }

   public final String component1() {
      return this.durationPredictor;
   }

   public final String component2() {
      return this.textEncoder;
   }

   public final String component3() {
      return this.vectorEstimator;
   }

   public final String component4() {
      return this.vocoder;
   }

   public final String component5() {
      return this.ttsJson;
   }

   public final String component6() {
      return this.unicodeIndexer;
   }

   public final String component7() {
      return this.voiceStyle;
   }

   public final OfflineTtsSupertonicModelConfig copy(String var1, String var2, String var3, String var4, String var5, String var6, String var7) {
      Intrinsics.checkNotNullParameter(var1, "durationPredictor");
      Intrinsics.checkNotNullParameter(var2, "textEncoder");
      Intrinsics.checkNotNullParameter(var3, "vectorEstimator");
      Intrinsics.checkNotNullParameter(var4, "vocoder");
      Intrinsics.checkNotNullParameter(var5, "ttsJson");
      Intrinsics.checkNotNullParameter(var6, "unicodeIndexer");
      Intrinsics.checkNotNullParameter(var7, "voiceStyle");
      return new OfflineTtsSupertonicModelConfig(var1, var2, var3, var4, var5, var6, var7);
   }

   @Override
   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof OfflineTtsSupertonicModelConfig)) {
         return false;
      } else {
         var1 = var1;
         if (!Intrinsics.areEqual(this.durationPredictor, var1.durationPredictor)) {
            return false;
         } else if (!Intrinsics.areEqual(this.textEncoder, var1.textEncoder)) {
            return false;
         } else if (!Intrinsics.areEqual(this.vectorEstimator, var1.vectorEstimator)) {
            return false;
         } else if (!Intrinsics.areEqual(this.vocoder, var1.vocoder)) {
            return false;
         } else if (!Intrinsics.areEqual(this.ttsJson, var1.ttsJson)) {
            return false;
         } else {
            return !Intrinsics.areEqual(this.unicodeIndexer, var1.unicodeIndexer) ? false : Intrinsics.areEqual(this.voiceStyle, var1.voiceStyle);
         }
      }
   }

   public final String getDurationPredictor() {
      return this.durationPredictor;
   }

   public final String getTextEncoder() {
      return this.textEncoder;
   }

   public final String getTtsJson() {
      return this.ttsJson;
   }

   public final String getUnicodeIndexer() {
      return this.unicodeIndexer;
   }

   public final String getVectorEstimator() {
      return this.vectorEstimator;
   }

   public final String getVocoder() {
      return this.vocoder;
   }

   public final String getVoiceStyle() {
      return this.voiceStyle;
   }

   @Override
   public int hashCode() {
      return (
               (
                        (
                                 ((this.durationPredictor.hashCode() * 31 + this.textEncoder.hashCode()) * 31 + this.vectorEstimator.hashCode()) * 31
                                    + this.vocoder.hashCode()
                              )
                              * 31
                           + this.ttsJson.hashCode()
                     )
                     * 31
                  + this.unicodeIndexer.hashCode()
            )
            * 31
         + this.voiceStyle.hashCode();
   }

   public final void setDurationPredictor(String var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.durationPredictor = var1;
   }

   public final void setTextEncoder(String var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.textEncoder = var1;
   }

   public final void setTtsJson(String var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.ttsJson = var1;
   }

   public final void setUnicodeIndexer(String var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.unicodeIndexer = var1;
   }

   public final void setVectorEstimator(String var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.vectorEstimator = var1;
   }

   public final void setVocoder(String var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.vocoder = var1;
   }

   public final void setVoiceStyle(String var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.voiceStyle = var1;
   }

   @Override
   public String toString() {
      String var2 = this.durationPredictor;
      String var3 = this.textEncoder;
      String var6 = this.vectorEstimator;
      String var5 = this.vocoder;
      String var1 = this.ttsJson;
      String var7 = this.unicodeIndexer;
      String var4 = this.voiceStyle;
      return "OfflineTtsSupertonicModelConfig(durationPredictor="
         + var2
         + ", textEncoder="
         + var3
         + ", vectorEstimator="
         + var6
         + ", vocoder="
         + var5
         + ", ttsJson="
         + var1
         + ", unicodeIndexer="
         + var7
         + ", voiceStyle="
         + var4
         + ")";
   }
}
