package com.starpilot.starview;

import kotlin.jvm.functions.Function2;
import org.json.JSONObject;

// recovered: R8's synthetic lambda class (used by methods taken from Vineflower)
public final class ConnectionsView$$ExternalSyntheticLambda5 implements Function2 {
   public final ConnectionsView f$0;

   public ConnectionsView$$ExternalSyntheticLambda5(ConnectionsView var1) {
      this.f$0 = var1;
   }

   @Override
   public final Object invoke(Object var1, Object var2) {
      return ConnectionsView.refresh$lambda$20(this.f$0, (JSONObject)var1, (String)var2);
   }
}
