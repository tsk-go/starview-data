package com.starpilot.starview;

import java.util.Comparator;
import kotlin.jvm.functions.Function2;

// recovered: R8's synthetic lambda class (used by methods taken from Vineflower)
public final class GxLogsPage$$ExternalSyntheticLambda38 implements Comparator {
   public final Function2 f$0;

   public GxLogsPage$$ExternalSyntheticLambda38(Function2 var1) {
      this.f$0 = var1;
   }

   @Override
   public final int compare(Object var1, Object var2) {
      return GxLogsPage.monPaintTable$lambda$92(this.f$0, var1, var2);
   }
}
