package com.starpilot.starview;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Ref.ObjectRef;

// recovered: R8's synthetic lambda class (used by methods taken from Vineflower)
public final class GxPage$$ExternalSyntheticLambda7 implements Function1 {
   public final ObjectRef f$0;
   public final Function1 f$1;

   public GxPage$$ExternalSyntheticLambda7(ObjectRef var1, Function1 var2) {
      this.f$0 = var1;
      this.f$1 = var2;
   }

   @Override
   public final Object invoke(Object var1) {
      return GxPage.choice$lambda$45(this.f$0, this.f$1, (Integer)var1);
   }
}
