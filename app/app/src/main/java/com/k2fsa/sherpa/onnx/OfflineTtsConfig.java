package com.k2fsa.sherpa.onnx;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class OfflineTtsConfig {
   private int maxNumSentences;
   private OfflineTtsModelConfig model;
   private String ruleFars;
   private String ruleFsts;
   private float silenceScale;

   public OfflineTtsConfig() {
      this(null, null, null, 0, 0.0F, 31, null);
   }

   public OfflineTtsConfig(OfflineTtsModelConfig var1, String var2, String var3, int var4, float var5) {
      Intrinsics.checkNotNullParameter(var1, "model");
      Intrinsics.checkNotNullParameter(var2, "ruleFsts");
      Intrinsics.checkNotNullParameter(var3, "ruleFars");
      super();
      this.model = var1;
      this.ruleFsts = var2;
      this.ruleFars = var3;
      this.maxNumSentences = var4;
      this.silenceScale = var5;
   }

   // $VF: synthetic method
   public OfflineTtsConfig(OfflineTtsModelConfig var1, String var2, String var3, int var4, float var5, int var6, DefaultConstructorMarker var7) {
      if ((var6 & 1) != 0) {
         var1 = new OfflineTtsModelConfig(null, null, null, null, null, null, null, 0, false, null, 1023, null);
      }

      String var8 = "";
      if ((var6 & 2) != 0) {
         var2 = "";
      }

      if ((var6 & 4) != 0) {
         var3 = var8;
      }

      if ((var6 & 8) != 0) {
         var4 = 1;
      }

      if ((var6 & 16) != 0) {
         var5 = 0.2F;
      }

      this(var1, var2, var3, var4, var5);
   }

   // $VF: synthetic method
   public static OfflineTtsConfig copy$default(
      OfflineTtsConfig var0, OfflineTtsModelConfig var1, String var2, String var3, int var4, float var5, int var6, Object var7
   ) {
      if ((var6 & 1) != 0) {
         var1 = var0.model;
      }

      if ((var6 & 2) != 0) {
         var2 = var0.ruleFsts;
      }

      if ((var6 & 4) != 0) {
         var3 = var0.ruleFars;
      }

      if ((var6 & 8) != 0) {
         var4 = var0.maxNumSentences;
      }

      if ((var6 & 16) != 0) {
         var5 = var0.silenceScale;
      }

      return var0.copy(var1, var2, var3, var4, var5);
   }

   public final OfflineTtsModelConfig component1() {
      return this.model;
   }

   public final String component2() {
      return this.ruleFsts;
   }

   public final String component3() {
      return this.ruleFars;
   }

   public final int component4() {
      return this.maxNumSentences;
   }

   public final float component5() {
      return this.silenceScale;
   }

   public final OfflineTtsConfig copy(OfflineTtsModelConfig var1, String var2, String var3, int var4, float var5) {
      Intrinsics.checkNotNullParameter(var1, "model");
      Intrinsics.checkNotNullParameter(var2, "ruleFsts");
      Intrinsics.checkNotNullParameter(var3, "ruleFars");
      return new OfflineTtsConfig(var1, var2, var3, var4, var5);
   }

   @Override
   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof OfflineTtsConfig)) {
         return false;
      } else {
         var1 = var1;
         if (!Intrinsics.areEqual(this.model, var1.model)) {
            return false;
         } else if (!Intrinsics.areEqual(this.ruleFsts, var1.ruleFsts)) {
            return false;
         } else if (!Intrinsics.areEqual(this.ruleFars, var1.ruleFars)) {
            return false;
         } else {
            return this.maxNumSentences != var1.maxNumSentences ? false : Float.compare(this.silenceScale, var1.silenceScale) == 0;
         }
      }
   }

   public final int getMaxNumSentences() {
      return this.maxNumSentences;
   }

   public final OfflineTtsModelConfig getModel() {
      return this.model;
   }

   public final String getRuleFars() {
      return this.ruleFars;
   }

   public final String getRuleFsts() {
      return this.ruleFsts;
   }

   public final float getSilenceScale() {
      return this.silenceScale;
   }

   @Override
   public int hashCode() {
      return (((this.model.hashCode() * 31 + this.ruleFsts.hashCode()) * 31 + this.ruleFars.hashCode()) * 31 + Integer.hashCode(this.maxNumSentences)) * 31
         + Float.hashCode(this.silenceScale);
   }

   public final void setMaxNumSentences(int var1) {
      this.maxNumSentences = var1;
   }

   public final void setModel(OfflineTtsModelConfig var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.model = var1;
   }

   public final void setRuleFars(String var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.ruleFars = var1;
   }

   public final void setRuleFsts(String var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.ruleFsts = var1;
   }

   public final void setSilenceScale(float var1) {
      this.silenceScale = var1;
   }

   @Override
   public String toString() {
      OfflineTtsModelConfig var3 = this.model;
      String var5 = this.ruleFsts;
      String var4 = this.ruleFars;
      int var2 = this.maxNumSentences;
      float var1 = this.silenceScale;
      return "OfflineTtsConfig(model=" + var3 + ", ruleFsts=" + var5 + ", ruleFars=" + var4 + ", maxNumSentences=" + var2 + ", silenceScale=" + var1 + ")";
   }
}
