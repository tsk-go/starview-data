package com.starpilot.starview;

import android.content.SharedPreferences;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Ref.ObjectRef;

// recovered: R8's synthetic lambda class (used by methods taken from Vineflower)
public final class SettingsActivity$$ExternalSyntheticLambda215 implements Function1 {
   public final ObjectRef f$0;
   public final SharedPreferences f$1;

   public SettingsActivity$$ExternalSyntheticLambda215(ObjectRef var1, SharedPreferences var2) {
      this.f$0 = var1;
      this.f$1 = var2;
   }

   @Override
   public final Object invoke(Object var1) {
      return SettingsActivity.tabletVoiceStudioPage$lambda$365(this.f$0, this.f$1, (Float)var1);
   }
}
