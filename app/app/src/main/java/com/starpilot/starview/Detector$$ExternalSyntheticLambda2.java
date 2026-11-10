package com.starpilot.starview;

import kotlin.jvm.functions.Function1;

// recovered: R8's synthetic lambda class (used by methods taken from Vineflower)
public final class Detector$$ExternalSyntheticLambda2 implements Function1 {
   public final Detector f$0;
   public final boolean f$1;
   public final Detector.Frame f$2;

   public Detector$$ExternalSyntheticLambda2(Detector var1, boolean var2, Detector.Frame var3) {
      this.f$0 = var1;
      this.f$1 = var2;
      this.f$2 = var3;
   }

   @Override
   public final Object invoke(Object var1) {
      return Detector.track$lambda$28(this.f$0, this.f$1, this.f$2, (Detector.Track)var1);
   }
}
