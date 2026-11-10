package com.starpilot.starview;

import kotlin.jvm.functions.Function1;

// recovered: R8's synthetic lambda class (used by methods taken from Vineflower)
public final class SceneView$$ExternalSyntheticLambda3 implements Function1 {
   public final SceneView f$0;

   public SceneView$$ExternalSyntheticLambda3(SceneView var1) {
      this.f$0 = var1;
   }

   @Override
   public final Object invoke(Object var1) {
      return SceneView.drawMapRoads$lambda$82(this.f$0, (OsmRoads.Piece)var1);
   }
}
