package com.starpilot.starview;

import android.widget.EditText;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Ref.IntRef;
import kotlin.jvm.internal.Ref.ObjectRef;

// recovered: R8's synthetic lambda class (used by methods taken from Vineflower)
public final class SettingsActivity$$ExternalSyntheticLambda9 implements Function1 {
   public final IntRef f$0;
   public final EditText f$1;
   public final ObjectRef f$2;

   public SettingsActivity$$ExternalSyntheticLambda9(IntRef var1, EditText var2, ObjectRef var3) {
      this.f$0 = var1;
      this.f$1 = var2;
      this.f$2 = var3;
   }

   @Override
   public final Object invoke(Object var1) {
      return SettingsActivity.togglesPage$lambda$117(this.f$0, this.f$1, this.f$2, (Integer)var1);
   }
}
