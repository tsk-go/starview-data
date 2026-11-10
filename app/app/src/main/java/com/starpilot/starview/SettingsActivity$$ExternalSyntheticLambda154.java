package com.starpilot.starview;

import android.content.SharedPreferences;
import android.widget.SeekBar;
import android.widget.TextView;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Ref.IntRef;

// recovered: R8's synthetic lambda class (used by methods taken from Vineflower)
public final class SettingsActivity$$ExternalSyntheticLambda154 implements Function0 {
   public final IntRef f$0;
   public final SeekBar f$1;
   public final TextView f$2;
   public final SharedPreferences f$3;
   public final VoiceStudio.Voice f$4;

   public SettingsActivity$$ExternalSyntheticLambda154(IntRef var1, SeekBar var2, TextView var3, SharedPreferences var4, VoiceStudio.Voice var5) {
      this.f$0 = var1;
      this.f$1 = var2;
      this.f$2 = var3;
      this.f$3 = var4;
      this.f$4 = var5;
   }

   @Override
   public final Object invoke() {
      return SettingsActivity.tabletVoiceStudioPage$lambda$397$lambda$393(this.f$0, this.f$1, this.f$2, this.f$3, this.f$4);
   }
}
