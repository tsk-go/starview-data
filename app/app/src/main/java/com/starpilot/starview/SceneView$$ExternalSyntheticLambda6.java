package com.starpilot.starview;

import java.util.IdentityHashMap;
import kotlin.jvm.functions.Function1;

// recovered: R8's synthetic lambda class (used by methods taken from Vineflower)
public final class SceneView$$ExternalSyntheticLambda6 implements Function1 {
   public final SceneView f$0;
   public final int f$1;
   public final IdentityHashMap f$2;

   public SceneView$$ExternalSyntheticLambda6(SceneView var1, int var2, IdentityHashMap var3) {
      this.f$0 = var1;
      this.f$1 = var2;
      this.f$2 = var3;
   }

   @Override
   public final Object invoke(Object var1) {
      return SceneView.drawMapRoads$lambda$85(this.f$0, this.f$1, this.f$2, (OsmRoads.Piece)var1);
   }
}
