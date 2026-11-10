package com.starpilot.starview;

import android.view.View;
import android.view.View.OnLongClickListener;
import java.util.List;

// recovered: R8's synthetic lambda class (used by methods taken from Vineflower)
public final class LanePicturesActivity$$ExternalSyntheticLambda10 implements OnLongClickListener {
   public final LanePicturesActivity f$0;
   public final String f$1;
   public final List f$2;

   public LanePicturesActivity$$ExternalSyntheticLambda10(LanePicturesActivity var1, String var2, List var3) {
      this.f$0 = var1;
      this.f$1 = var2;
      this.f$2 = var3;
   }

   public final boolean onLongClick(View var1) {
      return LanePicturesActivity.listDrives$lambda$43$lambda$42(this.f$0, this.f$1, this.f$2, var1);
   }
}
