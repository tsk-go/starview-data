package com.starpilot.starview;

import java.util.HashMap;
import kotlin.jvm.internal.Intrinsics;

public final class JournalLogic$Companion$spots$Acc {
   private final HashMap<String, Integer> causes;
   private long lastT;
   private double lat;
   private double lon;
   private int n;
   private String road;

   public JournalLogic$Companion$spots$Acc(double var1, double var3, int var5, String var6, long var7, HashMap<String, Integer> var9) {
      Intrinsics.checkNotNullParameter(var6, "road");
      Intrinsics.checkNotNullParameter(var9, "causes");
      super();
      this.lat = var1;
      this.lon = var3;
      this.n = var5;
      this.road = var6;
      this.lastT = var7;
      this.causes = var9;
   }

   public final HashMap<String, Integer> getCauses() {
      return this.causes;
   }

   public final long getLastT() {
      return this.lastT;
   }

   public final double getLat() {
      return this.lat;
   }

   public final double getLon() {
      return this.lon;
   }

   public final int getN() {
      return this.n;
   }

   public final String getRoad() {
      return this.road;
   }

   public final void setLastT(long var1) {
      this.lastT = var1;
   }

   public final void setLat(double var1) {
      this.lat = var1;
   }

   public final void setLon(double var1) {
      this.lon = var1;
   }

   public final void setN(int var1) {
      this.n = var1;
   }

   public final void setRoad(String var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.road = var1;
   }
}
