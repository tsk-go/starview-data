package com.starpilot.starview;

import android.app.Activity;
import com.google.mlkit.vision.barcode.common.Barcode;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

// $VF: synthetic class
public final class PairScanner$$ExternalSyntheticLambda0 implements Function1 {
   public final Activity f$0;
   public final Function0 f$1;

   // $VF: synthetic method
   public PairScanner$$ExternalSyntheticLambda0(Activity var1, Function0 var2) {
      this.f$0 = var1;
      this.f$1 = var2;
   }

   @Override
   public final Object invoke(Object var1) {
      return PairScanner.$r8$lambda$anqJeywV8THAv9oyitEk65Xd_oo(this.f$0, this.f$1, (Barcode)var1);
   }
}
