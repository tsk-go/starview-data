package com.starpilot.starview;

import android.widget.ScrollView;

// recovered: R8's synthetic lambda class (used by methods taken from Vineflower)
public final class MapPane$$ExternalSyntheticLambda35 implements Runnable {
   public final ScrollView f$0;
   public final MapPane f$1;
   public final int f$2;

   public MapPane$$ExternalSyntheticLambda35(ScrollView var1, MapPane var2, int var3) {
      this.f$0 = var1;
      this.f$1 = var2;
      this.f$2 = var3;
   }

   @Override
   public final void run() {
      MapPane.fillSheet$lambda$87(this.f$0, this.f$1, this.f$2);
   }
}
