package com.starpilot.starview;

import android.widget.EditText;
import android.widget.FrameLayout;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Ref.IntRef;
import kotlin.jvm.internal.Ref.ObjectRef;

// recovered: R8's synthetic lambda class (used by methods taken from Vineflower)
public final class SettingsActivity$$ExternalSyntheticLambda12 implements Runnable {
   public final EditText f$0;
   public final ObjectRef f$1;
   public final IntRef f$2;
   public final Function1 f$3;
   public final GalaxyApi f$4;
   public final SettingsActivity f$5;
   public final FrameLayout f$6;

   public SettingsActivity$$ExternalSyntheticLambda12(
      EditText var1, ObjectRef var2, IntRef var3, Function1 var4, GalaxyApi var5, SettingsActivity var6, FrameLayout var7
   ) {
      this.f$0 = var1;
      this.f$1 = var2;
      this.f$2 = var3;
      this.f$3 = var4;
      this.f$4 = var5;
      this.f$5 = var6;
      this.f$6 = var7;
   }

   @Override
   public final void run() {
      SettingsActivity.togglesPage$lambda$132(this.f$0, this.f$1, this.f$2, this.f$3, this.f$4, this.f$5, this.f$6);
   }
}
