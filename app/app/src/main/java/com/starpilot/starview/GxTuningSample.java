package com.starpilot.starview;

import java.util.Map;
import kotlin.jvm.internal.Intrinsics;

final class GxTuningSample {
   private final double ts;
   private final Map<String, Double> v;

   public GxTuningSample(double var1, Map<String, Double> var3) {
      Intrinsics.checkNotNullParameter(var3, "v");
      super();
      this.ts = var1;
      this.v = var3;
   }

   public final double getTs() {
      return this.ts;
   }

   public final Map<String, Double> getV() {
      return this.v;
   }
}
