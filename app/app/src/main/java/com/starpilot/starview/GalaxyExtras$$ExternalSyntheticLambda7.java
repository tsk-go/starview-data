package com.starpilot.starview;

import android.content.Context;
import kotlin.jvm.functions.Function0;
import org.json.JSONObject;

// recovered: R8's synthetic lambda class (used by methods taken from Vineflower)
public final class GalaxyExtras$$ExternalSyntheticLambda7 implements Function0 {
   public final JSONObject f$0;
   public final Context f$1;
   public final Galaxy7 f$2;
   public final String f$3;

   public GalaxyExtras$$ExternalSyntheticLambda7(JSONObject var1, Context var2, Galaxy7 var3, String var4) {
      this.f$0 = var1;
      this.f$1 = var2;
      this.f$2 = var3;
      this.f$3 = var4;
   }

   @Override
   public final Object invoke() {
      return GalaxyExtras.soundPacks$draw$lambda$4(this.f$0, this.f$1, this.f$2, this.f$3);
   }
}
