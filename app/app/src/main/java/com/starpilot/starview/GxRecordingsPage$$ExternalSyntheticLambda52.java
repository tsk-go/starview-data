package com.starpilot.starview;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Ref.ObjectRef;

// recovered: R8's synthetic lambda class (used by methods taken from Vineflower)
public final class GxRecordingsPage$$ExternalSyntheticLambda52 implements Function1 {
   public final GxRecordingsPage f$0;
   public final ObjectRef f$1;

   public GxRecordingsPage$$ExternalSyntheticLambda52(GxRecordingsPage var1, ObjectRef var2) {
      this.f$0 = var1;
      this.f$1 = var2;
   }

   @Override
   public final Object invoke(Object var1) {
      return GxRecordingsPage.loadRoutes$lambda$16(this.f$0, this.f$1, (String)var1);
   }
}
