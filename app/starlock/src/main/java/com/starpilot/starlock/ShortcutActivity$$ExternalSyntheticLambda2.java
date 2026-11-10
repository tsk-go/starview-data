package com.starpilot.starlock;

import kotlin.jvm.functions.Function2;

// recovered: R8's synthetic lambda class (used by methods taken from Vineflower)
public final class ShortcutActivity$$ExternalSyntheticLambda2 implements Function2 {
   public final ShortcutActivity f$0;
   public final String f$1;

   public ShortcutActivity$$ExternalSyntheticLambda2(ShortcutActivity var1, String var2) {
      this.f$0 = var1;
      this.f$1 = var2;
   }

   @Override
   public final Object invoke(Object var1, Object var2) {
      return ShortcutActivity.onCreate$lambda$10(this.f$0, this.f$1, (Outcome)var1, (String)var2);
   }
}
