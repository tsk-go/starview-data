package com.starpilot.starview;

import kotlin.jvm.functions.Function1;

// recovered: R8's synthetic lambda class (used by methods taken from Vineflower)
public final class SceneView$$ExternalSyntheticLambda1 implements Function1 {
   public final float[] f$0;
   public final float f$1;
   public final SceneView f$2;
   public final float f$3;

   public SceneView$$ExternalSyntheticLambda1(float[] var1, float var2, SceneView var3, float var4) {
      this.f$0 = var1;
      this.f$1 = var2;
      this.f$2 = var3;
      this.f$3 = var4;
   }

   @Override
   public final Object invoke(Object var1) {
      return SceneView.rebuildRibbons$lambda$105(this.f$0, this.f$1, this.f$2, this.f$3, (Integer)var1);
   }
}
