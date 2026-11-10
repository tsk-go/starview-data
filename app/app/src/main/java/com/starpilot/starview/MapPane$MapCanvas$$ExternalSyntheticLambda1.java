package com.starpilot.starview;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

// recovered: R8's synthetic lambda class (used by methods taken from Vineflower)
public final class MapPane$MapCanvas$$ExternalSyntheticLambda1 implements OnClickListener {
   public final MapPane f$0;
   public final String f$1;
   public final String f$2;
   public final double f$3;
   public final double f$4;

   public MapPane$MapCanvas$$ExternalSyntheticLambda1(MapPane var1, String var2, String var3, double var4, double var6) {
      this.f$0 = var1;
      this.f$1 = var2;
      this.f$2 = var3;
      this.f$3 = var4;
      this.f$4 = var6;
   }

   public final void onClick(DialogInterface var1, int var2) {
      MapPane.MapCanvas.dropPin$lambda$19$lambda$18$lambda$17(this.f$0, this.f$1, this.f$2, this.f$3, this.f$4, var1, var2);
   }
}
