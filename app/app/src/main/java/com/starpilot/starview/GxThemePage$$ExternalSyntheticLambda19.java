package com.starpilot.starview;

import android.view.View;
import android.widget.EditText;
import java.util.ArrayList;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Ref.BooleanRef;

// recovered: R8's synthetic lambda class (used by methods taken from Vineflower)
public final class GxThemePage$$ExternalSyntheticLambda19 implements Function1 {
   public final View f$0;
   public final GxThemePage f$1;
   public final ArrayList f$2;
   public final float[] f$3;
   public final ArrayList f$4;
   public final ArrayList f$5;
   public final BooleanRef f$6;
   public final EditText f$7;

   public GxThemePage$$ExternalSyntheticLambda19(
      View var1, GxThemePage var2, ArrayList var3, float[] var4, ArrayList var5, ArrayList var6, BooleanRef var7, EditText var8
   ) {
      this.f$0 = var1;
      this.f$1 = var2;
      this.f$2 = var3;
      this.f$3 = var4;
      this.f$4 = var5;
      this.f$5 = var6;
      this.f$6 = var7;
      this.f$7 = var8;
   }

   @Override
   public final Object invoke(Object var1) {
      return GxThemePage.gxThemeColorDialog$lambda$168(this.f$0, this.f$1, this.f$2, this.f$3, this.f$4, this.f$5, this.f$6, this.f$7, (Boolean)var1);
   }
}
