package com.starpilot.starview;

import android.app.Dialog;
import android.widget.CheckBox;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

// recovered: R8's synthetic lambda class (used by methods taken from Vineflower)
public final class LaneFixDialog$$ExternalSyntheticLambda2 implements Function0 {
   public final LaneFixDialog.Road f$0;
   public final CheckBox f$1;
   public final Dialog f$2;
   public final Function1 f$3;

   public LaneFixDialog$$ExternalSyntheticLambda2(LaneFixDialog.Road var1, CheckBox var2, Dialog var3, Function1 var4) {
      this.f$0 = var1;
      this.f$1 = var2;
      this.f$2 = var3;
      this.f$3 = var4;
   }

   @Override
   public final Object invoke() {
      return LaneFixDialog.show$lambda$42(this.f$0, this.f$1, this.f$2, this.f$3);
   }
}
