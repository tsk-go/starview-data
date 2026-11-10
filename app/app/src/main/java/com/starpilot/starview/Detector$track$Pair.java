package com.starpilot.starview;

import kotlin.jvm.internal.Intrinsics;

public final class Detector$track$Pair {
   private final float cost;
   private final Detector.Det d;
   private final Detector.Track tr;

   public Detector$track$Pair(Detector.Track var1, Detector.Det var2, float var3) {
      Intrinsics.checkNotNullParameter(var1, "tr");
      Intrinsics.checkNotNullParameter(var2, "d");
      super();
      this.tr = var1;
      this.d = var2;
      this.cost = var3;
   }

   public final float getCost() {
      return this.cost;
   }

   public final Detector.Det getD() {
      return this.d;
   }

   public final Detector.Track getTr() {
      return this.tr;
   }
}
