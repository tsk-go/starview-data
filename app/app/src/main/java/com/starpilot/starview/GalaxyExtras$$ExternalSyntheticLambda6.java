package com.starpilot.starview;

import android.content.Context;
import kotlin.jvm.functions.Function0;
import org.json.JSONObject;

// recovered: R8's synthetic lambda class (used by methods taken from Vineflower)
public final class GalaxyExtras$$ExternalSyntheticLambda6 implements Function0 {
   public final Galaxy7 f$0;
   public final JSONObject f$1;
   public final Context f$2;

   public GalaxyExtras$$ExternalSyntheticLambda6(Galaxy7 var1, JSONObject var2, Context var3) {
      this.f$0 = var1;
      this.f$1 = var2;
      this.f$2 = var3;
   }

   @Override
   public final Object invoke() {
      return GalaxyExtras.soundPacks$draw$lambda$2(this.f$0, this.f$1, this.f$2);
   }
}
