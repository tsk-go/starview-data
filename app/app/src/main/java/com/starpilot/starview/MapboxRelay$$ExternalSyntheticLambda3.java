package com.starpilot.starview;

import java.io.OutputStream;
import java.net.Socket;

// $VF: synthetic class
public final class MapboxRelay$$ExternalSyntheticLambda3 implements Runnable {
   public final Socket f$0;
   public final OutputStream f$1;
   public final Socket f$2;

   // $VF: synthetic method
   public MapboxRelay$$ExternalSyntheticLambda3(Socket var1, OutputStream var2, Socket var3) {
      this.f$0 = var1;
      this.f$1 = var2;
      this.f$2 = var3;
   }

   @Override
   public final void run() {
      MapboxRelay.$r8$lambda$6X9Uu12WK_8thLxJuvDSac0J62Q(this.f$0, this.f$1, this.f$2);
   }
}
