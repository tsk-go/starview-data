package com.starpilot.starview;

import kotlin.jvm.functions.Function1;

// recovered: R8's synthetic lambda class (used by methods taken from Vineflower)
public final class SceneView$$ExternalSyntheticLambda13 implements Function1 {
   public final ModelSnapshot f$0;
   public final SceneView f$1;

   public SceneView$$ExternalSyntheticLambda13(ModelSnapshot var1, SceneView var2) {
      this.f$0 = var1;
      this.f$1 = var2;
   }

   @Override
   public final Object invoke(Object var1) {
      return SceneView.updateScene$lambda$30(this.f$0, this.f$1, (Double)var1);
   }
}
