package com.starpilot.starview;

import kotlin.jvm.functions.Function1;

// recovered: R8's synthetic lambda class (used by methods taken from Vineflower)
public final class SceneView$$ExternalSyntheticLambda12 implements Function1 {
   public final float[] f$0;
   public final Function1 f$1;

   public SceneView$$ExternalSyntheticLambda12(float[] var1, Function1 var2) {
      this.f$0 = var1;
      this.f$1 = var2;
   }

   @Override
   public final Object invoke(Object var1) {
      return SceneView.cleanRoad$lambda$52(this.f$0, this.f$1, (Integer)var1);
   }
}
