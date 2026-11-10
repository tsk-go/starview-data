package com.starpilot.starview;

import android.widget.LinearLayout;
import java.util.List;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Ref.ObjectRef;

// recovered: R8's synthetic lambda class (used by methods taken from Vineflower)
public final class CatalogPanel$$ExternalSyntheticLambda27 implements Function1 {
   public final CatalogPanel f$0;
   public final String f$1;
   public final ObjectRef f$2;
   public final LinearLayout f$3;
   public final ObjectRef f$4;
   public final GalaxyApi.Param f$5;

   public CatalogPanel$$ExternalSyntheticLambda27(CatalogPanel var1, String var2, ObjectRef var3, LinearLayout var4, ObjectRef var5, GalaxyApi.Param var6) {
      this.f$0 = var1;
      this.f$1 = var2;
      this.f$2 = var3;
      this.f$3 = var4;
      this.f$4 = var5;
      this.f$5 = var6;
   }

   @Override
   public final Object invoke(Object var1) {
      return CatalogPanel.addDropdown$lambda$64$lambda$62$lambda$61(this.f$0, this.f$1, this.f$2, this.f$3, this.f$4, this.f$5, (List)var1);
   }
}
