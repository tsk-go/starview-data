package com.k2fsa.sherpa.onnx;

import kotlin.jvm.internal.Intrinsics;

public final class GeneratedAudio {
   private final int sampleRate;
   private final float[] samples;

   public GeneratedAudio(float[] var1, int var2) {
      Intrinsics.checkNotNullParameter(var1, "samples");
      super();
      this.samples = var1;
      this.sampleRate = var2;
   }

   private final native boolean saveImpl(String var1, float[] var2, int var3);

   public final int getSampleRate() {
      return this.sampleRate;
   }

   public final float[] getSamples() {
      return this.samples;
   }

   public final boolean save(String var1) {
      Intrinsics.checkNotNullParameter(var1, "filename");
      return this.saveImpl(var1, this.samples, this.sampleRate);
   }
}
