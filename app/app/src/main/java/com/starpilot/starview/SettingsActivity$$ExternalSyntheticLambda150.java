package com.starpilot.starview;

import android.widget.TextView;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Ref.BooleanRef;
import kotlin.jvm.internal.Ref.ObjectRef;

// recovered: R8's synthetic lambda class (used by methods taken from Vineflower)
public final class SettingsActivity$$ExternalSyntheticLambda150 implements Function0 {
   public final VoiceStudio.Voice f$0;
   public final SettingsActivity f$1;
   public final AtomicBoolean f$2;
   public final TextView f$3;
   public final BooleanRef f$4;
   public final TextView f$5;
   public final ObjectRef f$6;

   public SettingsActivity$$ExternalSyntheticLambda150(
      VoiceStudio.Voice var1, SettingsActivity var2, AtomicBoolean var3, TextView var4, BooleanRef var5, TextView var6, ObjectRef var7
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
   public final Object invoke() {
      return SettingsActivity.tabletVoiceStudioPage$lambda$397$lambda$383(this.f$0, this.f$1, this.f$2, this.f$3, this.f$4, this.f$5, this.f$6);
   }
}
