package com.starpilot.starview;

import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref.ObjectRef;
import org.json.JSONObject;

// recovered: R8's synthetic lambda class (used by methods taken from Vineflower)
public final class GxLogsPage$$ExternalSyntheticLambda34 implements Function2 {
   public final GxLogsPage f$0;
   public final String f$1;
   public final ObjectRef f$2;

   public GxLogsPage$$ExternalSyntheticLambda34(GxLogsPage var1, String var2, ObjectRef var3) {
      this.f$0 = var1;
      this.f$1 = var2;
      this.f$2 = var3;
   }

   @Override
   public final Object invoke(Object var1, Object var2) {
      return GxLogsPage.tmuxRename$lambda$57$lambda$56(this.f$0, this.f$1, this.f$2, (String)var1, (JSONObject)var2);
   }
}
