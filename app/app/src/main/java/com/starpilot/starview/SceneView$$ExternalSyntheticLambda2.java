package com.starpilot.starview;

import kotlin.jvm.functions.Function2;

// recovered: R8's synthetic lambda class (used by methods taken from Vineflower)
public final class SceneView$$ExternalSyntheticLambda2 implements Function2 {
   public final SceneView f$0;
   public final double f$1;

   public SceneView$$ExternalSyntheticLambda2(SceneView var1, double var2) {
      this.f$0 = var1;
      this.f$1 = var2;
   }

   @Override
   public final Object invoke(Object var1, Object var2) {
      return SceneView.drawMapRoads$lambda$72(this.f$0, this.f$1, (Double)var1, (Double)var2);
   }
}
