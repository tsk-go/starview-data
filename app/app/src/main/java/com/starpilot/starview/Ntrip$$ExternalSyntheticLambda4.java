package com.starpilot.starview;

// recovered: R8's synthetic lambda class (used by methods taken from Vineflower)
public final class Ntrip$$ExternalSyntheticLambda4 implements Runnable {
   public final Ntrip.Config f$0;
   public final int f$1;
   public final Ntrip.Config f$2;
   public final boolean f$3;

   public Ntrip$$ExternalSyntheticLambda4(Ntrip.Config var1, int var2, Ntrip.Config var3, boolean var4) {
      this.f$0 = var1;
      this.f$1 = var2;
      this.f$2 = var3;
      this.f$3 = var4;
   }

   @Override
   public final void run() {
      Ntrip.tick$lambda$6(this.f$0, this.f$1, this.f$2, this.f$3);
   }
}
