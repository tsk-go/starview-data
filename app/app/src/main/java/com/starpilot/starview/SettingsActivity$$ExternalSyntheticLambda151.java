package com.starpilot.starview;

import android.widget.TextView;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Ref.BooleanRef;
import kotlin.jvm.internal.Ref.IntRef;
import kotlin.jvm.internal.Ref.ObjectRef;

// recovered: R8's synthetic lambda class (used by methods taken from Vineflower)
public final class SettingsActivity$$ExternalSyntheticLambda151 implements Function0 {
   public final VoiceStudio.Voice f$0;
   public final IntRef f$1;
   public final SettingsActivity f$2;
   public final ObjectRef f$3;
   public final List f$4;
   public final TextView f$5;
   public final BooleanRef f$6;
   public final AtomicBoolean f$7;
   public final TextView f$8;
   public final ObjectRef f$9;

   public SettingsActivity$$ExternalSyntheticLambda151(
      VoiceStudio.Voice var1,
      IntRef var2,
      SettingsActivity var3,
      ObjectRef var4,
      List var5,
      TextView var6,
      BooleanRef var7,
      AtomicBoolean var8,
      TextView var9,
      ObjectRef var10
   ) {
      this.f$0 = var1;
      this.f$1 = var2;
      this.f$2 = var3;
      this.f$3 = var4;
      this.f$4 = var5;
      this.f$5 = var6;
      this.f$6 = var7;
      this.f$7 = var8;
      this.f$8 = var9;
      this.f$9 = var10;
   }

   @Override
   public final Object invoke() {
      return SettingsActivity.tabletVoiceStudioPage$lambda$397$lambda$385(
         this.f$0, this.f$1, this.f$2, this.f$3, this.f$4, this.f$5, this.f$6, this.f$7, this.f$8, this.f$9
      );
   }
}
