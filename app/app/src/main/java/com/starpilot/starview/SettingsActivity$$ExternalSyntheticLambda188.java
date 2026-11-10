package com.starpilot.starview;

import java.util.List;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Ref.ObjectRef;

// recovered: R8's synthetic lambda class (used by methods taken from Vineflower)
public final class SettingsActivity$$ExternalSyntheticLambda188 implements Function1 {
   public final Function1 f$0;
   public final List f$1;
   public final ObjectRef f$2;

   public SettingsActivity$$ExternalSyntheticLambda188(Function1 var1, List var2, ObjectRef var3) {
      this.f$0 = var1;
      this.f$1 = var2;
      this.f$2 = var3;
   }

   @Override
   public final Object invoke(Object var1) {
      return SettingsActivity.choiceCard$lambda$151(this.f$0, this.f$1, this.f$2, (Integer)var1);
   }
}
