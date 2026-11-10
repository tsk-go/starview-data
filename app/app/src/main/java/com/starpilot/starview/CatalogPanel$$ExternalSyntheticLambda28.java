package com.starpilot.starview;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Ref.ObjectRef;

// recovered: R8's synthetic lambda class (used by methods taken from Vineflower)
public final class CatalogPanel$$ExternalSyntheticLambda28 implements Function1 {
   public final CatalogPanel f$0;
   public final GalaxyApi.Param f$1;
   public final ObjectRef f$2;

   public CatalogPanel$$ExternalSyntheticLambda28(CatalogPanel var1, GalaxyApi.Param var2, ObjectRef var3) {
      this.f$0 = var1;
      this.f$1 = var2;
      this.f$2 = var3;
   }

   @Override
   public final Object invoke(Object var1) {
      return CatalogPanel.addDropdown$rebuild$lambda$51(this.f$0, this.f$1, this.f$2, (Integer)var1);
   }
}
