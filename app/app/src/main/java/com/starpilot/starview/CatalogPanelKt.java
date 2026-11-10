package com.starpilot.starview;

import android.view.View;
import android.widget.TextView;
import kotlin.jvm.internal.Intrinsics;

public final class CatalogPanelKt {
   // $VF: synthetic method
   public static final void access$txt(TextView var0, CharSequence var1) {
      txt(var0, var1);
   }

   // $VF: synthetic method
   public static final void access$vis(View var0, boolean var1) {
      vis(var0, var1);
   }

   private static final void txt(TextView var0, CharSequence var1) {
      if (!Intrinsics.areEqual(var0.getText().toString(), var1.toString())) {
         var0.setText(var1);
      }
   }

   private static final void vis(View var0, boolean var1) {
      byte var2;
      if (var1) {
         var2 = 0;
      } else {
         var2 = 8;
      }

      if (var0.getVisibility() != var2) {
         var0.setVisibility(var2);
      }
   }
}
