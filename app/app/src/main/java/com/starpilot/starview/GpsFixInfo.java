package com.starpilot.starview;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class GpsFixInfo {
   private final double altitude;
   private final long atNs;
   private final float bAcc;
   private final float bearing;
   private final float hAcc;
   private final boolean hasFix;
   private final double lat;
   private final double lon;
   private final int sats;
   private final String service;
   private final String source;
   private final float speed;
   private final long unixMs;
   private final float vAcc;

   public GpsFixInfo(
      String var1,
      String var2,
      boolean var3,
      int var4,
      double var5,
      double var7,
      float var9,
      float var10,
      float var11,
      float var12,
      long var13,
      double var15,
      float var17,
      long var18
   ) {
      Intrinsics.checkNotNullParameter(var1, "service");
      Intrinsics.checkNotNullParameter(var2, "source");
      super();
      this.service = var1;
      this.source = var2;
      this.hasFix = var3;
      this.sats = var4;
      this.lat = var5;
      this.lon = var7;
      this.hAcc = var9;
      this.speed = var10;
      this.bearing = var11;
      this.bAcc = var12;
      this.atNs = var13;
      this.altitude = var15;
      this.vAcc = var17;
      this.unixMs = var18;
   }

   // $VF: synthetic method
   public GpsFixInfo(
      String var1,
      String var2,
      boolean var3,
      int var4,
      double var5,
      double var7,
      float var9,
      float var10,
      float var11,
      float var12,
      long var13,
      double var15,
      float var17,
      long var18,
      int var20,
      DefaultConstructorMarker var21
   ) {
      if ((var20 & 2048) != 0) {
         var15 = Double.NaN;
      }

      if ((var20 & 4096) != 0) {
         var17 = Float.NaN;
      }

      if ((var20 & 8192) != 0) {
         var18 = 0L;
      }

      this(var1, var2, var3, var4, var5, var7, var9, var10, var11, var12, var13, var15, var17, var18);
   }

   public final double getAltitude() {
      return this.altitude;
   }

   public final long getAtNs() {
      return this.atNs;
   }

   public final float getBAcc() {
      return this.bAcc;
   }

   public final float getBearing() {
      return this.bearing;
   }

   public final float getHAcc() {
      return this.hAcc;
   }

   public final boolean getHasFix() {
      return this.hasFix;
   }

   public final double getLat() {
      return this.lat;
   }

   public final double getLon() {
      return this.lon;
   }

   public final int getSats() {
      return this.sats;
   }

   public final String getService() {
      return this.service;
   }

   public final String getSource() {
      return this.source;
   }

   public final float getSpeed() {
      return this.speed;
   }

   public final long getUnixMs() {
      return this.unixMs;
   }

   public final float getVAcc() {
      return this.vAcc;
   }
}
