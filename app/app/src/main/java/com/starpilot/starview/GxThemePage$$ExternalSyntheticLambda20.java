package com.starpilot.starview;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.widget.EditText;
import kotlin.jvm.functions.Function1;

// recovered: R8's synthetic lambda class (used by methods taken from Vineflower)
public final class GxThemePage$$ExternalSyntheticLambda20 implements OnClickListener {
   public final EditText f$0;
   public final Function1 f$1;
   public final float[] f$2;

   public GxThemePage$$ExternalSyntheticLambda20(EditText var1, Function1 var2, float[] var3) {
      this.f$0 = var1;
      this.f$1 = var2;
      this.f$2 = var3;
   }

   public final void onClick(DialogInterface var1, int var2) {
      GxThemePage.gxThemeColorDialog$lambda$170(this.f$0, this.f$1, this.f$2, var1, var2);
   }
}
