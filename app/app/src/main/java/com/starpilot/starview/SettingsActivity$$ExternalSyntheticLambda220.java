package com.starpilot.starview;

import android.content.SharedPreferences;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Ref.BooleanRef;
import kotlin.jvm.internal.Ref.ObjectRef;

// recovered: R8's synthetic lambda class (used by methods taken from Vineflower)
public final class SettingsActivity$$ExternalSyntheticLambda220 implements Function0 {
   public final LinearLayout f$0;
   public final SettingsActivity f$1;
   public final ObjectRef f$10;
   public final List f$11;
   public final ObjectRef f$2;
   public final SharedPreferences f$3;
   public final ObjectRef f$4;
   public final AtomicBoolean f$5;
   public final TextView f$6;
   public final BooleanRef f$7;
   public final TextView f$8;
   public final ObjectRef f$9;

   public SettingsActivity$$ExternalSyntheticLambda220(
      LinearLayout var1,
      SettingsActivity var2,
      ObjectRef var3,
      SharedPreferences var4,
      ObjectRef var5,
      AtomicBoolean var6,
      TextView var7,
      BooleanRef var8,
      TextView var9,
      ObjectRef var10,
      ObjectRef var11,
      List var12
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
      this.f$10 = var11;
      this.f$11 = var12;
   }

   @Override
   public final Object invoke() {
      return SettingsActivity.tabletVoiceStudioPage$lambda$397(
         this.f$0, this.f$1, this.f$2, this.f$3, this.f$4, this.f$5, this.f$6, this.f$7, this.f$8, this.f$9, this.f$10, this.f$11
      );
   }
}
