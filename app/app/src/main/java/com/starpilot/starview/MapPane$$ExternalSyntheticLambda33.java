package com.starpilot.starview;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

// recovered: R8's synthetic lambda class (used by methods taken from Vineflower)
public final class MapPane$$ExternalSyntheticLambda33 implements OnTouchListener {
   public final MapPane f$0;
   public final String f$1;
   public final Nav.Route f$2;

   public MapPane$$ExternalSyntheticLambda33(MapPane var1, String var2, Nav.Route var3) {
      this.f$0 = var1;
      this.f$1 = var2;
      this.f$2 = var3;
   }

   public final boolean onTouch(View var1, MotionEvent var2) {
      return MapPane.refreshBars$lambda$54(this.f$0, this.f$1, this.f$2, var1, var2);
   }
}
