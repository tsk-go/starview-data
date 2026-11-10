package com.starpilot.starview;

import kotlin.jvm.internal.Intrinsics;

final class GxLogsProc {
   private final Double cpu;
   private final String feature;
   private final boolean kernel;
   private final Double mem;
   private final String name;
   private final int pid;
   private final String state;
   private final String user;

   public GxLogsProc(int var1, String var2, String var3, boolean var4, String var5, Double var6, Double var7, String var8) {
      Intrinsics.checkNotNullParameter(var2, "name");
      Intrinsics.checkNotNullParameter(var3, "user");
      Intrinsics.checkNotNullParameter(var5, "state");
      Intrinsics.checkNotNullParameter(var8, "feature");
      super();
      this.pid = var1;
      this.name = var2;
      this.user = var3;
      this.kernel = var4;
      this.state = var5;
      this.cpu = var6;
      this.mem = var7;
      this.feature = var8;
   }

   // $VF: synthetic method
   public static GxLogsProc copy$default(
      GxLogsProc var0, int var1, String var2, String var3, boolean var4, String var5, Double var6, Double var7, String var8, int var9, Object var10
   ) {
      if ((var9 & 1) != 0) {
         var1 = var0.pid;
      }

      if ((var9 & 2) != 0) {
         var2 = var0.name;
      }

      if ((var9 & 4) != 0) {
         var3 = var0.user;
      }

      if ((var9 & 8) != 0) {
         var4 = var0.kernel;
      }

      if ((var9 & 16) != 0) {
         var5 = var0.state;
      }

      if ((var9 & 32) != 0) {
         var6 = var0.cpu;
      }

      if ((var9 & 64) != 0) {
         var7 = var0.mem;
      }

      if ((var9 & 128) != 0) {
         var8 = var0.feature;
      }

      return var0.copy(var1, var2, var3, var4, var5, var6, var7, var8);
   }

   public final int component1() {
      return this.pid;
   }

   public final String component2() {
      return this.name;
   }

   public final String component3() {
      return this.user;
   }

   public final boolean component4() {
      return this.kernel;
   }

   public final String component5() {
      return this.state;
   }

   public final Double component6() {
      return this.cpu;
   }

   public final Double component7() {
      return this.mem;
   }

   public final String component8() {
      return this.feature;
   }

   public final GxLogsProc copy(int var1, String var2, String var3, boolean var4, String var5, Double var6, Double var7, String var8) {
      Intrinsics.checkNotNullParameter(var2, "name");
      Intrinsics.checkNotNullParameter(var3, "user");
      Intrinsics.checkNotNullParameter(var5, "state");
      Intrinsics.checkNotNullParameter(var8, "feature");
      return new GxLogsProc(var1, var2, var3, var4, var5, var6, var7, var8);
   }

   @Override
   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof GxLogsProc)) {
         return false;
      } else {
         var1 = var1;
         if (this.pid != var1.pid) {
            return false;
         } else if (!Intrinsics.areEqual(this.name, var1.name)) {
            return false;
         } else if (!Intrinsics.areEqual(this.user, var1.user)) {
            return false;
         } else if (this.kernel != var1.kernel) {
            return false;
         } else if (!Intrinsics.areEqual(this.state, var1.state)) {
            return false;
         } else if (!Intrinsics.areEqual(this.cpu, var1.cpu)) {
            return false;
         } else {
            return !Intrinsics.areEqual(this.mem, var1.mem) ? false : Intrinsics.areEqual(this.feature, var1.feature);
         }
      }
   }

   public final Double getCpu() {
      return this.cpu;
   }

   public final String getFeature() {
      return this.feature;
   }

   public final boolean getKernel() {
      return this.kernel;
   }

   public final Double getMem() {
      return this.mem;
   }

   public final String getName() {
      return this.name;
   }

   public final int getPid() {
      return this.pid;
   }

   public final String getState() {
      return this.state;
   }

   public final String getUser() {
      return this.user;
   }

   @Override
   public int hashCode() {
      int var4 = Integer.hashCode(this.pid);
      int var6 = this.name.hashCode();
      int var5 = this.user.hashCode();
      int var7 = Boolean.hashCode(this.kernel);
      int var3 = this.state.hashCode();
      Double var8 = this.cpu;
      int var2 = 0;
      int var1;
      if (var8 == null) {
         var1 = 0;
      } else {
         var1 = this.cpu.hashCode();
      }

      if (this.mem != null) {
         var2 = this.mem.hashCode();
      }

      return ((((((var4 * 31 + var6) * 31 + var5) * 31 + var7) * 31 + var3) * 31 + var1) * 31 + var2) * 31 + this.feature.hashCode();
   }

   @Override
   public String toString() {
      int var1 = this.pid;
      String var4 = this.name;
      String var6 = this.user;
      boolean var2 = this.kernel;
      String var7 = this.state;
      Double var3 = this.cpu;
      Double var5 = this.mem;
      String var8 = this.feature;
      return "GxLogsProc(pid="
         + var1
         + ", name="
         + var4
         + ", user="
         + var6
         + ", kernel="
         + var2
         + ", state="
         + var7
         + ", cpu="
         + var3
         + ", mem="
         + var5
         + ", feature="
         + var8
         + ")";
   }
}
