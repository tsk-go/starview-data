package com.starpilot.starview;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Ref.ObjectRef;

// recovered: R8's synthetic lambda class (used by methods taken from Vineflower)
public final class GalaxySettingsView$$ExternalSyntheticLambda20 implements Function1 {
   public final GalaxyRow f$0;
   public final GalaxySettingsView f$1;
   public final ObjectRef f$2;

   public GalaxySettingsView$$ExternalSyntheticLambda20(GalaxyRow var1, GalaxySettingsView var2, ObjectRef var3) {
      this.f$0 = var1;
      this.f$1 = var2;
      this.f$2 = var3;
   }

   @Override
   public final Object invoke(Object var1) {
      return GalaxySettingsView.selectRow$lambda$46(this.f$0, this.f$1, this.f$2, (Integer)var1);
   }
}
