package com.k2fsa.sherpa.onnx;

import java.util.Arrays;
import java.util.Map;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class GenerationConfig {
   private Map<String, String> extra;
   private int numSteps;
   private float[] referenceAudio;
   private int referenceSampleRate;
   private String referenceText;
   private int sid;
   private float silenceScale;
   private float speed;

   public GenerationConfig() {
      this(0.0F, 0.0F, 0, null, 0, null, 0, null, 255, null);
   }

   public GenerationConfig(float var1, float var2, int var3, float[] var4, int var5, String var6, int var7, Map<String, String> var8) {
      this.silenceScale = var1;
      this.speed = var2;
      this.sid = var3;
      this.referenceAudio = var4;
      this.referenceSampleRate = var5;
      this.referenceText = var6;
      this.numSteps = var7;
      this.extra = var8;
   }

   // $VF: synthetic method
   public GenerationConfig(float var1, float var2, int var3, float[] var4, int var5, String var6, int var7, Map var8, int var9, DefaultConstructorMarker var10) {
      if ((var9 & 1) != 0) {
         var1 = 0.2F;
      }

      if ((var9 & 2) != 0) {
         var2 = 1.0F;
      }

      byte var11 = 0;
      if ((var9 & 4) != 0) {
         var3 = 0;
      }

      Object var12 = null;
      if ((var9 & 8) != 0) {
         var4 = null;
      }

      if ((var9 & 16) != 0) {
         var5 = var11;
      }

      if ((var9 & 32) != 0) {
         var6 = null;
      }

      if ((var9 & 64) != 0) {
         var7 = 5;
      }

      if ((var9 & 128) != 0) {
         var8 = (Map)var12;
      }

      this(var1, var2, var3, var4, var5, var6, var7, var8);
   }

   // $VF: synthetic method
   public static GenerationConfig copy$default(
      GenerationConfig var0, float var1, float var2, int var3, float[] var4, int var5, String var6, int var7, Map var8, int var9, Object var10
   ) {
      if ((var9 & 1) != 0) {
         var1 = var0.silenceScale;
      }

      if ((var9 & 2) != 0) {
         var2 = var0.speed;
      }

      if ((var9 & 4) != 0) {
         var3 = var0.sid;
      }

      if ((var9 & 8) != 0) {
         var4 = var0.referenceAudio;
      }

      if ((var9 & 16) != 0) {
         var5 = var0.referenceSampleRate;
      }

      if ((var9 & 32) != 0) {
         var6 = var0.referenceText;
      }

      if ((var9 & 64) != 0) {
         var7 = var0.numSteps;
      }

      if ((var9 & 128) != 0) {
         var8 = var0.extra;
      }

      return var0.copy(var1, var2, var3, var4, var5, var6, var7, var8);
   }

   public final float component1() {
      return this.silenceScale;
   }

   public final float component2() {
      return this.speed;
   }

   public final int component3() {
      return this.sid;
   }

   public final float[] component4() {
      return this.referenceAudio;
   }

   public final int component5() {
      return this.referenceSampleRate;
   }

   public final String component6() {
      return this.referenceText;
   }

   public final int component7() {
      return this.numSteps;
   }

   public final Map<String, String> component8() {
      return this.extra;
   }

   public final GenerationConfig copy(float var1, float var2, int var3, float[] var4, int var5, String var6, int var7, Map<String, String> var8) {
      return new GenerationConfig(var1, var2, var3, var4, var5, var6, var7, var8);
   }

   @Override
   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof GenerationConfig)) {
         return false;
      } else {
         var1 = var1;
         if (Float.compare(this.silenceScale, var1.silenceScale) != 0) {
            return false;
         } else if (Float.compare(this.speed, var1.speed) != 0) {
            return false;
         } else if (this.sid != var1.sid) {
            return false;
         } else if (!Intrinsics.areEqual(this.referenceAudio, var1.referenceAudio)) {
            return false;
         } else if (this.referenceSampleRate != var1.referenceSampleRate) {
            return false;
         } else if (!Intrinsics.areEqual(this.referenceText, var1.referenceText)) {
            return false;
         } else {
            return this.numSteps != var1.numSteps ? false : Intrinsics.areEqual(this.extra, var1.extra);
         }
      }
   }

   public final Map<String, String> getExtra() {
      return this.extra;
   }

   public final int getNumSteps() {
      return this.numSteps;
   }

   public final float[] getReferenceAudio() {
      return this.referenceAudio;
   }

   public final int getReferenceSampleRate() {
      return this.referenceSampleRate;
   }

   public final String getReferenceText() {
      return this.referenceText;
   }

   public final int getSid() {
      return this.sid;
   }

   public final float getSilenceScale() {
      return this.silenceScale;
   }

   public final float getSpeed() {
      return this.speed;
   }

   @Override
   public int hashCode() {
      int var4 = Float.hashCode(this.silenceScale);
      int var5 = Float.hashCode(this.speed);
      int var6 = Integer.hashCode(this.sid);
      float[] var9 = this.referenceAudio;
      int var3 = 0;
      int var1;
      if (var9 == null) {
         var1 = 0;
      } else {
         var1 = Arrays.hashCode(this.referenceAudio);
      }

      int var7 = Integer.hashCode(this.referenceSampleRate);
      int var2;
      if (this.referenceText == null) {
         var2 = 0;
      } else {
         var2 = this.referenceText.hashCode();
      }

      int var8 = Integer.hashCode(this.numSteps);
      if (this.extra != null) {
         var3 = this.extra.hashCode();
      }

      return ((((((var4 * 31 + var5) * 31 + var6) * 31 + var1) * 31 + var7) * 31 + var2) * 31 + var8) * 31 + var3;
   }

   public final void setExtra(Map<String, String> var1) {
      this.extra = var1;
   }

   public final void setNumSteps(int var1) {
      this.numSteps = var1;
   }

   public final void setReferenceAudio(float[] var1) {
      this.referenceAudio = var1;
   }

   public final void setReferenceSampleRate(int var1) {
      this.referenceSampleRate = var1;
   }

   public final void setReferenceText(String var1) {
      this.referenceText = var1;
   }

   public final void setSid(int var1) {
      this.sid = var1;
   }

   public final void setSilenceScale(float var1) {
      this.silenceScale = var1;
   }

   public final void setSpeed(float var1) {
      this.speed = var1;
   }

   @Override
   public String toString() {
      float var1 = this.silenceScale;
      float var2 = this.speed;
      int var3 = this.sid;
      String var6 = Arrays.toString(this.referenceAudio);
      int var5 = this.referenceSampleRate;
      String var7 = this.referenceText;
      int var4 = this.numSteps;
      Map var8 = this.extra;
      return "GenerationConfig(silenceScale="
         + var1
         + ", speed="
         + var2
         + ", sid="
         + var3
         + ", referenceAudio="
         + var6
         + ", referenceSampleRate="
         + var5
         + ", referenceText="
         + var7
         + ", numSteps="
         + var4
         + ", extra="
         + var8
         + ")";
   }
}
