package com.starpilot.starview;

import java.io.File;
import java.lang.Thread.UncaughtExceptionHandler;

// $VF: synthetic class
public final class MainActivity$$ExternalSyntheticLambda28 implements UncaughtExceptionHandler {
   public final File f$0;
   public final String f$1;
   public final UncaughtExceptionHandler f$2;

   // $VF: synthetic method
   public MainActivity$$ExternalSyntheticLambda28(File var1, String var2, UncaughtExceptionHandler var3) {
      this.f$0 = var1;
      this.f$1 = var2;
      this.f$2 = var3;
   }

   @Override
   public final void uncaughtException(Thread var1, Throwable var2) {
      MainActivity.$r8$lambda$WgzZx8DnHhwkyEyNQgUUc2K__Uw(this.f$0, this.f$1, this.f$2, var1, var2);
   }
}
