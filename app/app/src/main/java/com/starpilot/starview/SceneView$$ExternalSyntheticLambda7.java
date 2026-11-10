package com.starpilot.starview;

import java.util.IdentityHashMap;
import kotlin.jvm.functions.Function1;

// recovered: R8's synthetic lambda class (used by methods taken from Vineflower)
public final class SceneView$$ExternalSyntheticLambda7 implements Function1 {
   public final SceneView f$0;
   public final OsmRoads.Match f$1;
   public final boolean f$2;
   public final int f$3;
   public final int f$4;
   public final IdentityHashMap f$5;

   public SceneView$$ExternalSyntheticLambda7(SceneView var1, OsmRoads.Match var2, boolean var3, int var4, int var5, IdentityHashMap var6) {
      this.f$0 = var1;
      this.f$1 = var2;
      this.f$2 = var3;
      this.f$3 = var4;
      this.f$4 = var5;
      this.f$5 = var6;
   }

   @Override
   public final Object invoke(Object var1) {
      return SceneView.drawMapRoads$lambda$88(this.f$0, this.f$1, this.f$2, this.f$3, this.f$4, this.f$5, (OsmRoads.Piece)var1);
   }
}
