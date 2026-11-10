package com.starpilot.starview;

import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Ref.ObjectRef;

// recovered: R8's synthetic lambda class (used by methods taken from Vineflower)
public final class LaneFixDialog$$ExternalSyntheticLambda12 implements Function0 {
   public final LaneFixDialog.Road f$0;
   public final LaneRamps.Ramp f$1;
   public final ObjectRef f$2;
   public final ObjectRef f$3;

   public LaneFixDialog$$ExternalSyntheticLambda12(LaneFixDialog.Road var1, LaneRamps.Ramp var2, ObjectRef var3, ObjectRef var4) {
      this.f$0 = var1;
      this.f$1 = var2;
      this.f$2 = var3;
      this.f$3 = var4;
   }

   @Override
   public final Object invoke() {
      return LaneFixDialog.show$lambda$26(this.f$0, this.f$1, this.f$2, this.f$3);
   }
}
