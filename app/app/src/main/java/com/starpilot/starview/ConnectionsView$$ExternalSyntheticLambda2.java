package com.starpilot.starview;

import kotlin.jvm.functions.Function1;
import org.json.JSONObject;

// recovered: R8's synthetic lambda class (used by methods taken from Vineflower)
public final class ConnectionsView$$ExternalSyntheticLambda2 implements Function1 {
   public final ConnectionsView f$0;

   public ConnectionsView$$ExternalSyntheticLambda2(ConnectionsView var1) {
      this.f$0 = var1;
   }

   @Override
   public final Object invoke(Object var1) {
      return ConnectionsView.refresh$lambda$10(this.f$0, (JSONObject)var1);
   }
}
