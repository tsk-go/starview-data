package com.starpilot.starview;

import android.widget.TextView;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Ref.ObjectRef;

// recovered: R8's synthetic lambda class (used by methods taken from Vineflower)
public final class CatalogPanel$$ExternalSyntheticLambda30 implements Function1 {
   public final TextView f$0;
   public final ObjectRef f$1;
   public final CatalogPanel f$2;
   public final GalaxyApi.Param f$3;

   public CatalogPanel$$ExternalSyntheticLambda30(TextView var1, ObjectRef var2, CatalogPanel var3, GalaxyApi.Param var4) {
      this.f$0 = var1;
      this.f$1 = var2;
      this.f$2 = var3;
      this.f$3 = var4;
   }

   @Override
   public final Object invoke(Object var1) {
      return CatalogPanel.addDropdown$rebuild$lambda$59(this.f$0, this.f$1, this.f$2, this.f$3, (Integer)var1);
   }
}
