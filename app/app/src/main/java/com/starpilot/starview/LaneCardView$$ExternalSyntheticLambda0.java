package com.starpilot.starview;

import kotlin.jvm.functions.Function1;

// recovered: R8's synthetic lambda class (used by methods taken from Vineflower)
public final class LaneCardView$$ExternalSyntheticLambda0 implements Function1 {
   public final LaneCardView f$0;

   public LaneCardView$$ExternalSyntheticLambda0(LaneCardView var1) {
      this.f$0 = var1;
   }

   @Override
   public final Object invoke(Object var1) {
      return LaneCardView.onDraw$lambda$10(this.f$0, (LaneBrain.Event)var1);
   }
}
