package com.starpilot.starview;

import java.util.HashSet;
import kotlin.jvm.functions.Function1;
import org.json.JSONObject;

// recovered: R8's synthetic lambda class (used by methods taken from Vineflower)
public final class GxRecordingsPage$$ExternalSyntheticLambda24 implements Function1 {
   public final GxRecordingsPage f$0;
   public final HashSet f$1;

   public GxRecordingsPage$$ExternalSyntheticLambda24(GxRecordingsPage var1, HashSet var2) {
      this.f$0 = var1;
      this.f$1 = var2;
   }

   @Override
   public final Object invoke(Object var1) {
      return GxRecordingsPage.loadScreens$lambda$91(this.f$0, this.f$1, (JSONObject)var1);
   }
}
