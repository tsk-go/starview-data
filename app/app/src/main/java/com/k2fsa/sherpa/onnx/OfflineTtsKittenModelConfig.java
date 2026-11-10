package com.k2fsa.sherpa.onnx;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class OfflineTtsKittenModelConfig {
   private String dataDir;
   private float lengthScale;
   private String model;
   private String tokens;
   private String voices;

   public OfflineTtsKittenModelConfig() {
      this(null, null, null, null, 0.0F, 31, null);
   }

   public OfflineTtsKittenModelConfig(String var1, String var2, String var3, String var4, float var5) {
      Intrinsics.checkNotNullParameter(var1, "model");
      Intrinsics.checkNotNullParameter(var2, "voices");
      Intrinsics.checkNotNullParameter(var3, "tokens");
      Intrinsics.checkNotNullParameter(var4, "dataDir");
      super();
      this.model = var1;
      this.voices = var2;
      this.tokens = var3;
      this.dataDir = var4;
      this.lengthScale = var5;
   }

   // $VF: synthetic method
   public OfflineTtsKittenModelConfig(String var1, String var2, String var3, String var4, float var5, int var6, DefaultConstructorMarker var7) {
      if ((var6 & 1) != 0) {
         var1 = "";
      }

      if ((var6 & 2) != 0) {
         var2 = "";
      }

      if ((var6 & 4) != 0) {
         var3 = "";
      }

      if ((var6 & 8) != 0) {
         var4 = "";
      }

      if ((var6 & 16) != 0) {
         var5 = 1.0F;
      }

      this(var1, var2, var3, var4, var5);
   }

   // $VF: synthetic method
   public static OfflineTtsKittenModelConfig copy$default(
      OfflineTtsKittenModelConfig var0, String var1, String var2, String var3, String var4, float var5, int var6, Object var7
   ) {
      if ((var6 & 1) != 0) {
         var1 = var0.model;
      }

      if ((var6 & 2) != 0) {
         var2 = var0.voices;
      }

      if ((var6 & 4) != 0) {
         var3 = var0.tokens;
      }

      if ((var6 & 8) != 0) {
         var4 = var0.dataDir;
      }

      if ((var6 & 16) != 0) {
         var5 = var0.lengthScale;
      }

      return var0.copy(var1, var2, var3, var4, var5);
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

   public final float component5() {
      return this.lengthScale;
   }

   public final OfflineTtsKittenModelConfig copy(String var1, String var2, String var3, String var4, float var5) {
      Intrinsics.checkNotNullParameter(var1, "model");
      Intrinsics.checkNotNullParameter(var2, "voices");
      Intrinsics.checkNotNullParameter(var3, "tokens");
      Intrinsics.checkNotNullParameter(var4, "dataDir");
      return new OfflineTtsKittenModelConfig(var1, var2, var3, var4, var5);
   }

   @Override
   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof OfflineTtsKittenModelConfig)) {
         return false;
      } else {
         var1 = var1;
         if (!Intrinsics.areEqual(this.model, var1.model)) {
            return false;
         } else if (!Intrinsics.areEqual(this.voices, var1.voices)) {
            return false;
         } else if (!Intrinsics.areEqual(this.tokens, var1.tokens)) {
            return false;
         } else {
            return !Intrinsics.areEqual(this.dataDir, var1.dataDir) ? false : Float.compare(this.lengthScale, var1.lengthScale) == 0;
         }
      }
   }

   public final String getDataDir() {
      return this.dataDir;
   }

   public final float getLengthScale() {
      return this.lengthScale;
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
      return (((this.model.hashCode() * 31 + this.voices.hashCode()) * 31 + this.tokens.hashCode()) * 31 + this.dataDir.hashCode()) * 31
         + Float.hashCode(this.lengthScale);
   }

   public final void setDataDir(String var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.dataDir = var1;
   }

   public final void setLengthScale(float var1) {
      this.lengthScale = var1;
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
      String var5 = this.voices;
      String var3 = this.tokens;
      String var4 = this.dataDir;
      float var1 = this.lengthScale;
      return "OfflineTtsKittenModelConfig(model=" + var2 + ", voices=" + var5 + ", tokens=" + var3 + ", dataDir=" + var4 + ", lengthScale=" + var1 + ")";
   }
}
