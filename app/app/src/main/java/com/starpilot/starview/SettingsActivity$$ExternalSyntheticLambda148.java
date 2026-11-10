package com.starpilot.starview;

import android.net.Uri;
import androidx.activity.result.ActivityResultCallback;

// recovered: R8's synthetic lambda class (used by methods taken from Vineflower)
public final class SettingsActivity$$ExternalSyntheticLambda148 implements ActivityResultCallback {
   public final SettingsActivity f$0;

   public SettingsActivity$$ExternalSyntheticLambda148(SettingsActivity var1) {
      this.f$0 = var1;
   }

   @Override
   public final void onActivityResult(Object var1) {
      SettingsActivity.pickModel$lambda$2(this.f$0, (Uri)var1);
   }
}
