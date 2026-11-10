package com.starpilot.starview;

import android.app.Dialog;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

// recovered: R8's synthetic lambda class (used by methods taken from Vineflower)
public final class LaneFixDialog$$ExternalSyntheticLambda14 implements Function0 {
   public final Dialog f$0;
   public final Function1 f$1;

   public LaneFixDialog$$ExternalSyntheticLambda14(Dialog var1, Function1 var2) {
      this.f$0 = var1;
      this.f$1 = var2;
   }

   @Override
   public final Object invoke() {
      return LaneFixDialog.show$lambda$30(this.f$0, this.f$1);
   }
}
