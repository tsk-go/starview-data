package com.starpilot.starlock;

import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.jvm.functions.Function0;

// recovered: R8's synthetic lambda class (used by methods taken from Vineflower)
public final class Door$$ExternalSyntheticLambda1 implements Function0 {
   public final AtomicBoolean f$0;

   public Door$$ExternalSyntheticLambda1(AtomicBoolean var1) {
      this.f$0 = var1;
   }

   @Override
   public final Object invoke() {
      return Door.run$lambda$17$lambda$16$lambda$15(this.f$0);
   }
}
