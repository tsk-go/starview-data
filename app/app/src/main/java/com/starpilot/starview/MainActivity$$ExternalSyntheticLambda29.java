package com.starpilot.starview;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import kotlin.jvm.internal.Ref.IntRef;
import kotlin.jvm.internal.Ref.LongRef;

// recovered: R8's synthetic lambda class (used by methods taken from Vineflower)
public final class MainActivity$$ExternalSyntheticLambda29 implements OnTouchListener {
   public final MainActivity f$0;
   public final IntRef f$1;
   public final LongRef f$2;

   public MainActivity$$ExternalSyntheticLambda29(MainActivity var1, IntRef var2, LongRef var3) {
      this.f$0 = var1;
      this.f$1 = var2;
      this.f$2 = var3;
   }

   public final boolean onTouch(View var1, MotionEvent var2) {
      return MainActivity.setupMapFrame$lambda$215(this.f$0, this.f$1, this.f$2, var1, var2);
   }
}
