package com.starpilot.starview;

import java.util.concurrent.CountDownLatch;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Ref.ObjectRef;

// recovered: R8's synthetic lambda class (used by methods taken from Vineflower)
public final class FilesActivity$$ExternalSyntheticLambda18 implements Runnable {
   public final FilesActivity f$0;
   public final boolean f$1;
   public final boolean f$2;
   public final String f$3;
   public final String f$4;
   public final String f$5;
   public final ObjectRef f$6;
   public final Function1 f$7;
   public final CountDownLatch f$8;

   public FilesActivity$$ExternalSyntheticLambda18(
      FilesActivity var1, boolean var2, boolean var3, String var4, String var5, String var6, ObjectRef var7, Function1 var8, CountDownLatch var9
   ) {
      this.f$0 = var1;
      this.f$1 = var2;
      this.f$2 = var3;
      this.f$3 = var4;
      this.f$4 = var5;
      this.f$5 = var6;
      this.f$6 = var7;
      this.f$7 = var8;
      this.f$8 = var9;
   }

   @Override
   public final void run() {
      FilesActivity.askClash$lambda$60(this.f$0, this.f$1, this.f$2, this.f$3, this.f$4, this.f$5, this.f$6, this.f$7, this.f$8);
   }
}
