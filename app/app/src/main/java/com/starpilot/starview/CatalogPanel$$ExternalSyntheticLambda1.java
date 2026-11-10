package com.starpilot.starview;

import android.widget.LinearLayout;
import android.widget.TextView;
import kotlin.jvm.functions.Function0;

// recovered: R8's synthetic lambda class (used by methods taken from Vineflower)
public final class CatalogPanel$$ExternalSyntheticLambda1 implements Function0 {
   public final LinearLayout f$0;
   public final CatalogPanel f$1;
   public final GalaxyApi.Param f$2;
   public final TextView f$3;

   public CatalogPanel$$ExternalSyntheticLambda1(LinearLayout var1, CatalogPanel var2, GalaxyApi.Param var3, TextView var4) {
      this.f$0 = var1;
      this.f$1 = var2;
      this.f$2 = var3;
      this.f$3 = var4;
   }

   @Override
   public final Object invoke() {
      return CatalogPanel.addRowUnsafe$lambda$26(this.f$0, this.f$1, this.f$2, this.f$3);
   }
}
