package com.starpilot.starlock;

import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.jvm.functions.Function0;

// recovered: R8's synthetic lambda class (used by methods taken from Vineflower)
public final class Door$$ExternalSyntheticLambda2 implements Function0 {
   public final AtomicBoolean f$0;

   public Door$$ExternalSyntheticLambda2(AtomicBoolean var1) {
      this.f$0 = var1;
   }

   @Override
   public final Object invoke() {
      return Door.run$lambda$11$lambda$10$lambda$9(this.f$0);
   }
}
