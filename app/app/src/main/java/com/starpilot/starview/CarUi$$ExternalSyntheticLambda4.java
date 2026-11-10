package com.starpilot.starview;

import android.view.View;
import android.view.View.OnClickListener;
import kotlin.jvm.functions.Function1;

// recovered: R8's synthetic lambda class (used by methods taken from Vineflower)
public final class CarUi$$ExternalSyntheticLambda4 implements OnClickListener {
   public final Function1 f$0;
   public final int f$1;

   public CarUi$$ExternalSyntheticLambda4(Function1 var1, int var2) {
      this.f$0 = var1;
      this.f$1 = var2;
   }

   public final void onClick(View var1) {
      CarUi.browserTabs$lambda$7$lambda$6(this.f$0, this.f$1, var1);
   }
}
