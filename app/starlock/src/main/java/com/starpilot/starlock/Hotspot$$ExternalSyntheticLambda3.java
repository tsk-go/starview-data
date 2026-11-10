package com.starpilot.starlock;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.concurrent.CountDownLatch;
import kotlin.jvm.internal.Ref.ObjectRef;

// recovered: R8's synthetic lambda class (used by methods taken from Vineflower)
public final class Hotspot$$ExternalSyntheticLambda3 implements InvocationHandler {
   public final ObjectRef f$0;
   public final CountDownLatch f$1;
   public final ObjectRef f$2;

   public Hotspot$$ExternalSyntheticLambda3(ObjectRef var1, CountDownLatch var2, ObjectRef var3) {
      this.f$0 = var1;
      this.f$1 = var2;
      this.f$2 = var3;
   }

   @Override
   public final Object invoke(Object var1, Method var2, Object[] var3) {
      return Hotspot.start$lambda$10(this.f$0, this.f$1, this.f$2, var1, var2, var3);
   }
}
