package com.starpilot.starview;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.json.JSONArray;
import org.json.JSONObject;

public final class RtkStatus {
   public static final RtkStatus.Companion Companion = new RtkStatus.Companion(null);
   private final double actionAgeS;
   private final String actionOp;
   private final String actionState;
   private final double altM;
   private final RtkCalibration calibration;
   private final boolean connected;
   private final Integer correctionAgeS;
   private final String firmware;
   private final String fixType;
   private final boolean fresh;
   private final String fusion;
   private final double hAccM;
   private final double headingDeg;
   private final int heard;
   private final String imuInit;
   private final String insInit;
   private final double lat;
   private final double lon;
   private final String module;
   private final String mount;
   private final List<String> mountErrors;
   private final String mountInit;
   private final boolean mountKeeping;
   private final double mountKeptYaw;
   private final double mountPitchDeg;
   private final double mountRollDeg;
   private final double mountYawDeg;
   private final Double rtcmAgeS;
   private final long rtcmBytes;
   private final String rtk;
   private final List<RtkSensor> sensors;
   private final Integer speedFeedDelayMs;
   private final double speedFeedHz;
   private final boolean speedFeedOn;
   private final String speedInit;
   private final double speedMs;
   private final int used;

   public RtkStatus(
      boolean var1,
      boolean var2,
      String var3,
      String var4,
      int var5,
      int var6,
      double var7,
      double var9,
      double var11,
      double var13,
      double var15,
      double var17,
      Integer var19,
      Double var20,
      long var21,
      String var23,
      String var24,
      String var25,
      String var26,
      String var27,
      String var28,
      List<RtkSensor> var29,
      String var30,
      String var31,
      boolean var32,
      double var33,
      Integer var35,
      String var36,
      String var37,
      double var38,
      double var40,
      double var42,
      double var44,
      List<String> var46,
      RtkCalibration var47,
      double var48,
      boolean var50
   ) {
      Intrinsics.checkNotNullParameter(var3, "rtk");
      Intrinsics.checkNotNullParameter(var4, "fixType");
      Intrinsics.checkNotNullParameter(var23, "fusion");
      Intrinsics.checkNotNullParameter(var24, "mount");
      Intrinsics.checkNotNullParameter(var25, "imuInit");
      Intrinsics.checkNotNullParameter(var26, "insInit");
      Intrinsics.checkNotNullParameter(var27, "mountInit");
      Intrinsics.checkNotNullParameter(var28, "speedInit");
      Intrinsics.checkNotNullParameter(var29, "sensors");
      Intrinsics.checkNotNullParameter(var30, "firmware");
      Intrinsics.checkNotNullParameter(var31, "module");
      Intrinsics.checkNotNullParameter(var36, "actionOp");
      Intrinsics.checkNotNullParameter(var37, "actionState");
      Intrinsics.checkNotNullParameter(var46, "mountErrors");
      super();
      this.connected = var1;
      this.fresh = var2;
      this.rtk = var3;
      this.fixType = var4;
      this.used = var5;
      this.heard = var6;
      this.lat = var7;
      this.lon = var9;
      this.altM = var11;
      this.hAccM = var13;
      this.speedMs = var15;
      this.headingDeg = var17;
      this.correctionAgeS = var19;
      this.rtcmAgeS = var20;
      this.rtcmBytes = var21;
      this.fusion = var23;
      this.mount = var24;
      this.imuInit = var25;
      this.insInit = var26;
      this.mountInit = var27;
      this.speedInit = var28;
      this.sensors = var29;
      this.firmware = var30;
      this.module = var31;
      this.speedFeedOn = var32;
      this.speedFeedHz = var33;
      this.speedFeedDelayMs = var35;
      this.actionOp = var36;
      this.actionState = var37;
      this.actionAgeS = var38;
      this.mountYawDeg = var40;
      this.mountPitchDeg = var42;
      this.mountRollDeg = var44;
      this.mountErrors = var46;
      this.calibration = var47;
      this.mountKeptYaw = var48;
      this.mountKeeping = var50;
   }

   // $VF: synthetic method
   public RtkStatus(
      boolean var1,
      boolean var2,
      String var3,
      String var4,
      int var5,
      int var6,
      double var7,
      double var9,
      double var11,
      double var13,
      double var15,
      double var17,
      Integer var19,
      Double var20,
      long var21,
      String var23,
      String var24,
      String var25,
      String var26,
      String var27,
      String var28,
      List var29,
      String var30,
      String var31,
      boolean var32,
      double var33,
      Integer var35,
      String var36,
      String var37,
      double var38,
      double var40,
      double var42,
      double var44,
      List var46,
      RtkCalibration var47,
      double var48,
      boolean var50,
      int var51,
      int var52,
      DefaultConstructorMarker var53
   ) {
      if ((var51 & 131072) != 0) {
         var25 = "";
      }

      if ((var51 & 262144) != 0) {
         var26 = "";
      }

      if ((var51 & 524288) != 0) {
         var27 = "";
      }

      if ((var51 & 1048576) != 0) {
         var28 = "";
      }

      if ((var51 & 2097152) != 0) {
         var29 = CollectionsKt.emptyList();
      }

      if ((var51 & 4194304) != 0) {
         var30 = "";
      }

      if ((var51 & 8388608) != 0) {
         var31 = "";
      }

      if ((var51 & 16777216) != 0) {
         var32 = false;
      }

      if ((var51 & 33554432) != 0) {
         var33 = 0.0;
      }

      if ((var51 & 67108864) != 0) {
         var35 = null;
      }

      if ((var51 & 134217728) != 0) {
         var36 = "";
      }

      if ((var51 & 268435456) != 0) {
         var37 = "";
      }

      if ((var51 & 536870912) != 0) {
         var38 = 0.0;
      }

      if ((var51 & 1073741824) != 0) {
         var40 = Double.NaN;
      }

      if ((var51 & -2147483648) != 0) {
         var42 = Double.NaN;
      }

      if ((var52 & 1) != 0) {
         var44 = Double.NaN;
      }

      if ((var52 & 2) != 0) {
         var46 = CollectionsKt.emptyList();
      }

      if ((var52 & 4) != 0) {
         var47 = null;
      }

      if ((var52 & 8) != 0) {
         var48 = Double.NaN;
      }

      if ((var52 & 16) != 0) {
         var50 = false;
      }

      this(
         var1,
         var2,
         var3,
         var4,
         var5,
         var6,
         var7,
         var9,
         var11,
         var13,
         var15,
         var17,
         var19,
         var20,
         var21,
         var23,
         var24,
         var25,
         var26,
         var27,
         var28,
         var29,
         var30,
         var31,
         var32,
         var33,
         var35,
         var36,
         var37,
         var38,
         var40,
         var42,
         var44,
         var46,
         var47,
         var48,
         var50
      );
   }

   // $VF: synthetic method
   public static RtkStatus copy$default(
      RtkStatus var0,
      boolean var1,
      boolean var2,
      String var3,
      String var4,
      int var5,
      int var6,
      double var7,
      double var9,
      double var11,
      double var13,
      double var15,
      double var17,
      Integer var19,
      Double var20,
      long var21,
      String var23,
      String var24,
      String var25,
      String var26,
      String var27,
      String var28,
      List var29,
      String var30,
      String var31,
      boolean var32,
      double var33,
      Integer var35,
      String var36,
      String var37,
      double var38,
      double var40,
      double var42,
      double var44,
      List var46,
      RtkCalibration var47,
      double var48,
      boolean var50,
      int var51,
      int var52,
      Object var53
   ) {
      if ((var51 & 1) != 0) {
         var1 = var0.connected;
      }

      if ((var51 & 2) != 0) {
         var2 = var0.fresh;
      }

      if ((var51 & 4) != 0) {
         var3 = var0.rtk;
      }

      if ((var51 & 8) != 0) {
         var4 = var0.fixType;
      }

      if ((var51 & 16) != 0) {
         var5 = var0.used;
      }

      if ((var51 & 32) != 0) {
         var6 = var0.heard;
      }

      if ((var51 & 64) != 0) {
         var7 = var0.lat;
      }

      if ((var51 & 128) != 0) {
         var9 = var0.lon;
      }

      if ((var51 & 256) != 0) {
         var11 = var0.altM;
      }

      if ((var51 & 512) != 0) {
         var13 = var0.hAccM;
      }

      if ((var51 & 1024) != 0) {
         var15 = var0.speedMs;
      }

      if ((var51 & 2048) != 0) {
         var17 = var0.headingDeg;
      }

      if ((var51 & 4096) != 0) {
         var19 = var0.correctionAgeS;
      }

      if ((var51 & 8192) != 0) {
         var20 = var0.rtcmAgeS;
      }

      if ((var51 & 16384) != 0) {
         var21 = var0.rtcmBytes;
      }

      if ((var51 & 32768) != 0) {
         var23 = var0.fusion;
      }

      if ((65536 & var51) != 0) {
         var24 = var0.mount;
      }

      if ((var51 & 131072) != 0) {
         var25 = var0.imuInit;
      }

      if ((var51 & 262144) != 0) {
         var26 = var0.insInit;
      }

      if ((var51 & 524288) != 0) {
         var27 = var0.mountInit;
      }

      if ((var51 & 1048576) != 0) {
         var28 = var0.speedInit;
      }

      if ((var51 & 2097152) != 0) {
         var29 = var0.sensors;
      }

      if ((var51 & 4194304) != 0) {
         var30 = var0.firmware;
      }

      if ((var51 & 8388608) != 0) {
         var31 = var0.module;
      }

      if ((var51 & 16777216) != 0) {
         var32 = var0.speedFeedOn;
      }

      if ((var51 & 33554432) != 0) {
         var33 = var0.speedFeedHz;
      }

      if ((var51 & 67108864) != 0) {
         var35 = var0.speedFeedDelayMs;
      }

      if ((134217728 & var51) != 0) {
         var36 = var0.actionOp;
      }

      if ((var51 & 268435456) != 0) {
         var37 = var0.actionState;
      }

      if ((var51 & 536870912) != 0) {
         var38 = var0.actionAgeS;
      }

      if ((var51 & 1073741824) != 0) {
         var40 = var0.mountYawDeg;
      }

      if ((var51 & -2147483648) != 0) {
         var42 = var0.mountPitchDeg;
      }

      if ((var52 & 1) != 0) {
         var44 = var0.mountRollDeg;
      }

      if ((var52 & 2) != 0) {
         var46 = var0.mountErrors;
      }

      if ((var52 & 4) != 0) {
         var47 = var0.calibration;
      }

      if ((var52 & 8) != 0) {
         var48 = var0.mountKeptYaw;
      }

      if ((var52 & 16) != 0) {
         var50 = var0.mountKeeping;
      }

      return var0.copy(
         var1,
         var2,
         var3,
         var4,
         var5,
         var6,
         var7,
         var9,
         var11,
         var13,
         var15,
         var17,
         var19,
         var20,
         var21,
         var23,
         var24,
         var25,
         var26,
         var27,
         var28,
         var29,
         var30,
         var31,
         var32,
         var33,
         var35,
         var36,
         var37,
         var38,
         var40,
         var42,
         var44,
         var46,
         var47,
         var48,
         var50
      );
   }

   public final boolean component1() {
      return this.connected;
   }

   public final double component10() {
      return this.hAccM;
   }

   public final double component11() {
      return this.speedMs;
   }

   public final double component12() {
      return this.headingDeg;
   }

   public final Integer component13() {
      return this.correctionAgeS;
   }

   public final Double component14() {
      return this.rtcmAgeS;
   }

   public final long component15() {
      return this.rtcmBytes;
   }

   public final String component16() {
      return this.fusion;
   }

   public final String component17() {
      return this.mount;
   }

   public final String component18() {
      return this.imuInit;
   }

   public final String component19() {
      return this.insInit;
   }

   public final boolean component2() {
      return this.fresh;
   }

   public final String component20() {
      return this.mountInit;
   }

   public final String component21() {
      return this.speedInit;
   }

   public final List<RtkSensor> component22() {
      return this.sensors;
   }

   public final String component23() {
      return this.firmware;
   }

   public final String component24() {
      return this.module;
   }

   public final boolean component25() {
      return this.speedFeedOn;
   }

   public final double component26() {
      return this.speedFeedHz;
   }

   public final Integer component27() {
      return this.speedFeedDelayMs;
   }

   public final String component28() {
      return this.actionOp;
   }

   public final String component29() {
      return this.actionState;
   }

   public final String component3() {
      return this.rtk;
   }

   public final double component30() {
      return this.actionAgeS;
   }

   public final double component31() {
      return this.mountYawDeg;
   }

   public final double component32() {
      return this.mountPitchDeg;
   }

   public final double component33() {
      return this.mountRollDeg;
   }

   public final List<String> component34() {
      return this.mountErrors;
   }

   public final RtkCalibration component35() {
      return this.calibration;
   }

   public final double component36() {
      return this.mountKeptYaw;
   }

   public final boolean component37() {
      return this.mountKeeping;
   }

   public final String component4() {
      return this.fixType;
   }

   public final int component5() {
      return this.used;
   }

   public final int component6() {
      return this.heard;
   }

   public final double component7() {
      return this.lat;
   }

   public final double component8() {
      return this.lon;
   }

   public final double component9() {
      return this.altM;
   }

   public final RtkStatus copy(
      boolean var1,
      boolean var2,
      String var3,
      String var4,
      int var5,
      int var6,
      double var7,
      double var9,
      double var11,
      double var13,
      double var15,
      double var17,
      Integer var19,
      Double var20,
      long var21,
      String var23,
      String var24,
      String var25,
      String var26,
      String var27,
      String var28,
      List<RtkSensor> var29,
      String var30,
      String var31,
      boolean var32,
      double var33,
      Integer var35,
      String var36,
      String var37,
      double var38,
      double var40,
      double var42,
      double var44,
      List<String> var46,
      RtkCalibration var47,
      double var48,
      boolean var50
   ) {
      Intrinsics.checkNotNullParameter(var3, "rtk");
      Intrinsics.checkNotNullParameter(var4, "fixType");
      Intrinsics.checkNotNullParameter(var23, "fusion");
      Intrinsics.checkNotNullParameter(var24, "mount");
      Intrinsics.checkNotNullParameter(var25, "imuInit");
      Intrinsics.checkNotNullParameter(var26, "insInit");
      Intrinsics.checkNotNullParameter(var27, "mountInit");
      Intrinsics.checkNotNullParameter(var28, "speedInit");
      Intrinsics.checkNotNullParameter(var29, "sensors");
      Intrinsics.checkNotNullParameter(var30, "firmware");
      Intrinsics.checkNotNullParameter(var31, "module");
      Intrinsics.checkNotNullParameter(var36, "actionOp");
      Intrinsics.checkNotNullParameter(var37, "actionState");
      Intrinsics.checkNotNullParameter(var46, "mountErrors");
      return new RtkStatus(
         var1,
         var2,
         var3,
         var4,
         var5,
         var6,
         var7,
         var9,
         var11,
         var13,
         var15,
         var17,
         var19,
         var20,
         var21,
         var23,
         var24,
         var25,
         var26,
         var27,
         var28,
         var29,
         var30,
         var31,
         var32,
         var33,
         var35,
         var36,
         var37,
         var38,
         var40,
         var42,
         var44,
         var46,
         var47,
         var48,
         var50
      );
   }

   @Override
   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof RtkStatus)) {
         return false;
      } else {
         var1 = var1;
         if (this.connected != var1.connected) {
            return false;
         } else if (this.fresh != var1.fresh) {
            return false;
         } else if (!Intrinsics.areEqual(this.rtk, var1.rtk)) {
            return false;
         } else if (!Intrinsics.areEqual(this.fixType, var1.fixType)) {
            return false;
         } else if (this.used != var1.used) {
            return false;
         } else if (this.heard != var1.heard) {
            return false;
         } else if (Double.compare(this.lat, var1.lat) != 0) {
            return false;
         } else if (Double.compare(this.lon, var1.lon) != 0) {
            return false;
         } else if (Double.compare(this.altM, var1.altM) != 0) {
            return false;
         } else if (Double.compare(this.hAccM, var1.hAccM) != 0) {
            return false;
         } else if (Double.compare(this.speedMs, var1.speedMs) != 0) {
            return false;
         } else if (Double.compare(this.headingDeg, var1.headingDeg) != 0) {
            return false;
         } else if (!Intrinsics.areEqual(this.correctionAgeS, var1.correctionAgeS)) {
            return false;
         } else if (!Intrinsics.areEqual(this.rtcmAgeS, var1.rtcmAgeS)) {
            return false;
         } else if (this.rtcmBytes != var1.rtcmBytes) {
            return false;
         } else if (!Intrinsics.areEqual(this.fusion, var1.fusion)) {
            return false;
         } else if (!Intrinsics.areEqual(this.mount, var1.mount)) {
            return false;
         } else if (!Intrinsics.areEqual(this.imuInit, var1.imuInit)) {
            return false;
         } else if (!Intrinsics.areEqual(this.insInit, var1.insInit)) {
            return false;
         } else if (!Intrinsics.areEqual(this.mountInit, var1.mountInit)) {
            return false;
         } else if (!Intrinsics.areEqual(this.speedInit, var1.speedInit)) {
            return false;
         } else if (!Intrinsics.areEqual(this.sensors, var1.sensors)) {
            return false;
         } else if (!Intrinsics.areEqual(this.firmware, var1.firmware)) {
            return false;
         } else if (!Intrinsics.areEqual(this.module, var1.module)) {
            return false;
         } else if (this.speedFeedOn != var1.speedFeedOn) {
            return false;
         } else if (Double.compare(this.speedFeedHz, var1.speedFeedHz) != 0) {
            return false;
         } else if (!Intrinsics.areEqual(this.speedFeedDelayMs, var1.speedFeedDelayMs)) {
            return false;
         } else if (!Intrinsics.areEqual(this.actionOp, var1.actionOp)) {
            return false;
         } else if (!Intrinsics.areEqual(this.actionState, var1.actionState)) {
            return false;
         } else if (Double.compare(this.actionAgeS, var1.actionAgeS) != 0) {
            return false;
         } else if (Double.compare(this.mountYawDeg, var1.mountYawDeg) != 0) {
            return false;
         } else if (Double.compare(this.mountPitchDeg, var1.mountPitchDeg) != 0) {
            return false;
         } else if (Double.compare(this.mountRollDeg, var1.mountRollDeg) != 0) {
            return false;
         } else if (!Intrinsics.areEqual(this.mountErrors, var1.mountErrors)) {
            return false;
         } else if (!Intrinsics.areEqual(this.calibration, var1.calibration)) {
            return false;
         } else {
            return Double.compare(this.mountKeptYaw, var1.mountKeptYaw) != 0 ? false : this.mountKeeping == var1.mountKeeping;
         }
      }
   }

   public final double getActionAgeS() {
      return this.actionAgeS;
   }

   public final String getActionOp() {
      return this.actionOp;
   }

   public final String getActionState() {
      return this.actionState;
   }

   public final double getAltM() {
      return this.altM;
   }

   public final RtkCalibration getCalibration() {
      return this.calibration;
   }

   public final boolean getConnected() {
      return this.connected;
   }

   public final Integer getCorrectionAgeS() {
      return this.correctionAgeS;
   }

   public final String getFirmware() {
      return this.firmware;
   }

   public final String getFixType() {
      return this.fixType;
   }

   public final boolean getFresh() {
      return this.fresh;
   }

   public final String getFusion() {
      return this.fusion;
   }

   public final double getHAccM() {
      return this.hAccM;
   }

   public final boolean getHasFix() {
      boolean var6 = this.fresh;
      boolean var5 = false;
      boolean var4 = var5;
      if (var6) {
         var4 = var5;
         if (!Intrinsics.areEqual(this.fixType, "none")) {
            var4 = var5;
            if (!Intrinsics.areEqual(this.fixType, "time only")) {
               double var1 = this.lat;
               boolean var3;
               if (!Double.isInfinite(var1) && !Double.isNaN(var1)) {
                  var3 = true;
               } else {
                  var3 = false;
               }

               var4 = var5;
               if (var3) {
                  var1 = this.lon;
                  if (!Double.isInfinite(var1) && !Double.isNaN(var1)) {
                     var3 = true;
                  } else {
                     var3 = false;
                  }

                  var4 = var5;
                  if (var3) {
                     var4 = true;
                  }
               }
            }
         }
      }

      return var4;
   }

   public final double getHeadingDeg() {
      return this.headingDeg;
   }

   public final int getHeard() {
      return this.heard;
   }

   public final String getImuInit() {
      return this.imuInit;
   }

   public final String getInsInit() {
      return this.insInit;
   }

   public final double getLat() {
      return this.lat;
   }

   public final double getLon() {
      return this.lon;
   }

   public final String getModule() {
      return this.module;
   }

   public final String getMount() {
      return this.mount;
   }

   public final List<String> getMountErrors() {
      return this.mountErrors;
   }

   public final String getMountInit() {
      return this.mountInit;
   }

   public final boolean getMountKeeping() {
      return this.mountKeeping;
   }

   public final boolean getMountKept() {
      double var1 = this.mountKeptYaw;
      boolean var3;
      if (!Double.isInfinite(var1) && !Double.isNaN(var1)) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public final double getMountKeptYaw() {
      return this.mountKeptYaw;
   }

   public final double getMountPitchDeg() {
      return this.mountPitchDeg;
   }

   public final double getMountRollDeg() {
      return this.mountRollDeg;
   }

   public final double getMountYawDeg() {
      return this.mountYawDeg;
   }

   public final Double getRtcmAgeS() {
      return this.rtcmAgeS;
   }

   public final long getRtcmBytes() {
      return this.rtcmBytes;
   }

   public final String getRtk() {
      return this.rtk;
   }

   public final List<RtkSensor> getSensors() {
      return this.sensors;
   }

   public final Integer getSpeedFeedDelayMs() {
      return this.speedFeedDelayMs;
   }

   public final double getSpeedFeedHz() {
      return this.speedFeedHz;
   }

   public final boolean getSpeedFeedOn() {
      return this.speedFeedOn;
   }

   public final String getSpeedInit() {
      return this.speedInit;
   }

   public final double getSpeedMs() {
      return this.speedMs;
   }

   public final int getUsed() {
      return this.used;
   }

   public final String getWords() {
      String var1;
      if (!this.connected) {
         var1 = "RTK receiver unplugged";
      } else if (!this.fresh) {
         var1 = "RTK receiver silent";
      } else if (Intrinsics.areEqual(this.rtk, "fixed")) {
         var1 = "RTK Fixed";
      } else if (Intrinsics.areEqual(this.rtk, "float")) {
         var1 = "RTK Float";
      } else if (Intrinsics.areEqual(this.fixType, "dead reckoning")) {
         var1 = "Dead reckoning";
      } else if (Intrinsics.areEqual(this.fixType, "none")) {
         var1 = "Searching";
      } else {
         var1 = "3D Fix";
      }

      return var1;
   }

   @Override
   public int hashCode() {
      int var7 = Boolean.hashCode(this.connected);
      int var16 = Boolean.hashCode(this.fresh);
      int var14 = this.rtk.hashCode();
      int var6 = this.fixType.hashCode();
      int var9 = Integer.hashCode(this.used);
      int var15 = Integer.hashCode(this.heard);
      int var11 = Double.hashCode(this.lat);
      int var10 = Double.hashCode(this.lon);
      int var13 = Double.hashCode(this.altM);
      int var12 = Double.hashCode(this.hAccM);
      int var5 = Double.hashCode(this.speedMs);
      int var8 = Double.hashCode(this.headingDeg);
      Integer var36 = this.correctionAgeS;
      int var4 = 0;
      int var1;
      if (var36 == null) {
         var1 = 0;
      } else {
         var1 = this.correctionAgeS.hashCode();
      }

      int var2;
      if (this.rtcmAgeS == null) {
         var2 = 0;
      } else {
         var2 = this.rtcmAgeS.hashCode();
      }

      int var19 = Long.hashCode(this.rtcmBytes);
      int var27 = this.fusion.hashCode();
      int var24 = this.mount.hashCode();
      int var25 = this.imuInit.hashCode();
      int var21 = this.insInit.hashCode();
      int var22 = this.mountInit.hashCode();
      int var26 = this.speedInit.hashCode();
      int var23 = this.sensors.hashCode();
      int var28 = this.firmware.hashCode();
      int var20 = this.module.hashCode();
      int var17 = Boolean.hashCode(this.speedFeedOn);
      int var18 = Double.hashCode(this.speedFeedHz);
      int var3;
      if (this.speedFeedDelayMs == null) {
         var3 = 0;
      } else {
         var3 = this.speedFeedDelayMs.hashCode();
      }

      int var31 = this.actionOp.hashCode();
      int var34 = this.actionState.hashCode();
      int var29 = Double.hashCode(this.actionAgeS);
      int var33 = Double.hashCode(this.mountYawDeg);
      int var32 = Double.hashCode(this.mountPitchDeg);
      int var35 = Double.hashCode(this.mountRollDeg);
      int var30 = this.mountErrors.hashCode();
      if (this.calibration != null) {
         var4 = this.calibration.hashCode();
      }

      return (
               (
                        (
                                 (
                                          (
                                                   (
                                                            (
                                                                     (
                                                                              (
                                                                                       (
                                                                                                (
                                                                                                         (
                                                                                                                  (
                                                                                                                           (
                                                                                                                                    (
                                                                                                                                             (
                                                                                                                                                      (
                                                                                                                                                               (
                                                                                                                                                                        (
                                                                                                                                                                                 (
                                                                                                                                                                                          (
                                                                                                                                                                                                   (
                                                                                                                                                                                                            (
                                                                                                                                                                                                                     (
                                                                                                                                                                                                                              (
                                                                                                                                                                                                                                       (
                                                                                                                                                                                                                                                (
                                                                                                                                                                                                                                                         (
                                                                                                                                                                                                                                                                  (
                                                                                                                                                                                                                                                                           (
                                                                                                                                                                                                                                                                                    (
                                                                                                                                                                                                                                                                                             (
                                                                                                                                                                                                                                                                                                      (
                                                                                                                                                                                                                                                                                                               (
                                                                                                                                                                                                                                                                                                                        (
                                                                                                                                                                                                                                                                                                                                 var7
                                                                                                                                                                                                                                                                                                                                       * 31
                                                                                                                                                                                                                                                                                                                                    + var16
                                                                                                                                                                                                                                                                                                                              )
                                                                                                                                                                                                                                                                                                                              * 31
                                                                                                                                                                                                                                                                                                                           + var14
                                                                                                                                                                                                                                                                                                                     )
                                                                                                                                                                                                                                                                                                                     * 31
                                                                                                                                                                                                                                                                                                                  + var6
                                                                                                                                                                                                                                                                                                            )
                                                                                                                                                                                                                                                                                                            * 31
                                                                                                                                                                                                                                                                                                         + var9
                                                                                                                                                                                                                                                                                                   )
                                                                                                                                                                                                                                                                                                   * 31
                                                                                                                                                                                                                                                                                                + var15
                                                                                                                                                                                                                                                                                          )
                                                                                                                                                                                                                                                                                          * 31
                                                                                                                                                                                                                                                                                       + var11
                                                                                                                                                                                                                                                                                 )
                                                                                                                                                                                                                                                                                 * 31
                                                                                                                                                                                                                                                                              + var10
                                                                                                                                                                                                                                                                        )
                                                                                                                                                                                                                                                                        * 31
                                                                                                                                                                                                                                                                     + var13
                                                                                                                                                                                                                                                               )
                                                                                                                                                                                                                                                               * 31
                                                                                                                                                                                                                                                            + var12
                                                                                                                                                                                                                                                      )
                                                                                                                                                                                                                                                      * 31
                                                                                                                                                                                                                                                   + var5
                                                                                                                                                                                                                                             )
                                                                                                                                                                                                                                             * 31
                                                                                                                                                                                                                                          + var8
                                                                                                                                                                                                                                    )
                                                                                                                                                                                                                                    * 31
                                                                                                                                                                                                                                 + var1
                                                                                                                                                                                                                           )
                                                                                                                                                                                                                           * 31
                                                                                                                                                                                                                        + var2
                                                                                                                                                                                                                  )
                                                                                                                                                                                                                  * 31
                                                                                                                                                                                                               + var19
                                                                                                                                                                                                         )
                                                                                                                                                                                                         * 31
                                                                                                                                                                                                      + var27
                                                                                                                                                                                                )
                                                                                                                                                                                                * 31
                                                                                                                                                                                             + var24
                                                                                                                                                                                       )
                                                                                                                                                                                       * 31
                                                                                                                                                                                    + var25
                                                                                                                                                                              )
                                                                                                                                                                              * 31
                                                                                                                                                                           + var21
                                                                                                                                                                     )
                                                                                                                                                                     * 31
                                                                                                                                                                  + var22
                                                                                                                                                            )
                                                                                                                                                            * 31
                                                                                                                                                         + var26
                                                                                                                                                   )
                                                                                                                                                   * 31
                                                                                                                                                + var23
                                                                                                                                          )
                                                                                                                                          * 31
                                                                                                                                       + var28
                                                                                                                                 )
                                                                                                                                 * 31
                                                                                                                              + var20
                                                                                                                        )
                                                                                                                        * 31
                                                                                                                     + var17
                                                                                                               )
                                                                                                               * 31
                                                                                                            + var18
                                                                                                      )
                                                                                                      * 31
                                                                                                   + var3
                                                                                             )
                                                                                             * 31
                                                                                          + var31
                                                                                    )
                                                                                    * 31
                                                                                 + var34
                                                                           )
                                                                           * 31
                                                                        + var29
                                                                  )
                                                                  * 31
                                                               + var33
                                                         )
                                                         * 31
                                                      + var32
                                                )
                                                * 31
                                             + var35
                                       )
                                       * 31
                                    + var30
                              )
                              * 31
                           + var4
                     )
                     * 31
                  + Double.hashCode(this.mountKeptYaw)
            )
            * 31
         + Boolean.hashCode(this.mountKeeping);
   }

   @Override
   public String toString() {
      boolean var28 = this.connected;
      boolean var29 = this.fresh;
      String var48 = this.rtk;
      String var35 = this.fixType;
      int var25 = this.used;
      int var26 = this.heard;
      double var1 = this.lat;
      double var5 = this.lon;
      double var11 = this.altM;
      double var21 = this.hAccM;
      double var17 = this.speedMs;
      double var9 = this.headingDeg;
      Integer var40 = this.correctionAgeS;
      Double var39 = this.rtcmAgeS;
      long var31 = this.rtcmBytes;
      String var34 = this.fusion;
      String var36 = this.mount;
      String var44 = this.imuInit;
      String var43 = this.insInit;
      String var47 = this.mountInit;
      String var45 = this.speedInit;
      List var42 = this.sensors;
      String var33 = this.firmware;
      String var50 = this.module;
      boolean var27 = this.speedFeedOn;
      double var3 = this.speedFeedHz;
      Integer var49 = this.speedFeedDelayMs;
      String var38 = this.actionOp;
      String var37 = this.actionState;
      double var7 = this.actionAgeS;
      double var19 = this.mountYawDeg;
      double var23 = this.mountPitchDeg;
      double var13 = this.mountRollDeg;
      List var46 = this.mountErrors;
      RtkCalibration var41 = this.calibration;
      double var15 = this.mountKeptYaw;
      boolean var30 = this.mountKeeping;
      return "RtkStatus(connected="
         + var28
         + ", fresh="
         + var29
         + ", rtk="
         + var48
         + ", fixType="
         + var35
         + ", used="
         + var25
         + ", heard="
         + var26
         + ", lat="
         + var1
         + ", lon="
         + var5
         + ", altM="
         + var11
         + ", hAccM="
         + var21
         + ", speedMs="
         + var17
         + ", headingDeg="
         + var9
         + ", correctionAgeS="
         + var40
         + ", rtcmAgeS="
         + var39
         + ", rtcmBytes="
         + var31
         + ", fusion="
         + var34
         + ", mount="
         + var36
         + ", imuInit="
         + var44
         + ", insInit="
         + var43
         + ", mountInit="
         + var47
         + ", speedInit="
         + var45
         + ", sensors="
         + var42
         + ", firmware="
         + var33
         + ", module="
         + var50
         + ", speedFeedOn="
         + var27
         + ", speedFeedHz="
         + var3
         + ", speedFeedDelayMs="
         + var49
         + ", actionOp="
         + var38
         + ", actionState="
         + var37
         + ", actionAgeS="
         + var7
         + ", mountYawDeg="
         + var19
         + ", mountPitchDeg="
         + var23
         + ", mountRollDeg="
         + var13
         + ", mountErrors="
         + var46
         + ", calibration="
         + var41
         + ", mountKeptYaw="
         + var15
         + ", mountKeeping="
         + var30
         + ")";
   }

   public static final class Companion {
      private Companion() {
      }

      // $VF: synthetic method
      public Companion(DefaultConstructorMarker var1) {
         this();
      }

      private static final double parse$d(JSONObject var0, String var1) {
         boolean var6 = var0.has(var1);
         double var4 = Double.NaN;
         double var2 = var4;
         if (var6) {
            var2 = var4;
            if (!var0.isNull(var1)) {
               var2 = var0.optDouble(var1, Double.NaN);
            }
         }

         return var2;
      }

      public final String accuracy(double var1, boolean var3) {
         boolean var4;
         if (!Double.isInfinite(var1) && !Double.isNaN(var1)) {
            var4 = true;
         } else {
            var4 = false;
         }

         String var7;
         if (!var4) {
            var7 = "–";
         } else if (var3 && var1 < 1.0) {
            var7 = String.format(Locale.US, "%.1f cm", Arrays.copyOf(new Object[]{100 * var1}, 1));
            Intrinsics.checkNotNullExpressionValue(var7, "format(...)");
         } else if (var3) {
            var7 = String.format(Locale.US, "%.1f m", Arrays.copyOf(new Object[]{var1}, 1));
            Intrinsics.checkNotNullExpressionValue(var7, "format(...)");
         } else if (var1 < 0.3048) {
            var7 = String.format(Locale.US, "%.1f in", Arrays.copyOf(new Object[]{var1 / 0.0254}, 1));
            Intrinsics.checkNotNullExpressionValue(var7, "format(...)");
         } else {
            long var5 = Math.round(var1 / 0.3048);
            var7 = var5 + " ft";
         }

         return var7;
      }

      public final RtkStatus parse(JSONObject var1) {
         if (var1 == null) {
            return null;
         } else {
            boolean var32 = var1.optBoolean("connected");
            boolean var31 = var1.optBoolean("fresh");
            String var43 = var1.optString("rtk", "none");
            Intrinsics.checkNotNullExpressionValue(var43, "optString(...)");
            String var42 = var1.optString("fixType", "none");
            Intrinsics.checkNotNullExpressionValue(var42, "optString(...)");
            int var27 = var1.optInt("used", 0);
            int var28 = var1.optInt("heard", 0);
            double var16 = parse$d(var1, "lat");
            double var10 = parse$d(var1, "lon");
            double var2 = parse$d(var1, "altM");
            double var18 = parse$d(var1, "hAccM");
            double var12 = parse$d(var1, "speedMs");
            double var14 = parse$d(var1, "headingDeg");
            Integer var35;
            if (var1.has("correctionAgeS") && !var1.isNull("correctionAgeS")) {
               var35 = var1.optInt("correctionAgeS");
            } else {
               var35 = null;
            }

            Double var36 = parse$d(var1, "rtcmAgeS");
            double var4 = var36.doubleValue();
            boolean var29;
            if (!Double.isInfinite(var4) && !Double.isNaN(var4)) {
               var29 = true;
            } else {
               var29 = false;
            }

            if (!Boolean.valueOf(var29)) {
               var36 = null;
            }

            long var33 = var1.optLong("rtcmBytes", 0L);
            String var45 = var1.optString("fusion", "");
            Intrinsics.checkNotNullExpressionValue(var45, "optString(...)");
            String var46 = var1.optString("mount", "");
            Intrinsics.checkNotNullExpressionValue(var46, "optString(...)");
            String var47 = var1.optString("imuInit", "");
            Intrinsics.checkNotNullExpressionValue(var47, "optString(...)");
            String var49 = var1.optString("insInit", "");
            Intrinsics.checkNotNullExpressionValue(var49, "optString(...)");
            String var44 = var1.optString("mountInit", "");
            Intrinsics.checkNotNullExpressionValue(var44, "optString(...)");
            String var48 = var1.optString("speedInit", "");
            Intrinsics.checkNotNullExpressionValue(var48, "optString(...)");
            JSONArray var39 = var1.optJSONArray("sensors");
            List var59;
            if (var39 != null) {
               Iterable var38 = RangesKt.until(0, var39.length());
               var29 = false;
               var var37 = new ArrayList();
               Iterator var40 = var38.iterator();

               while (var40.hasNext()) {
                  int var26 = ((IntIterator)var40).nextInt();
                  RtkSensor var41 = RtkSensor.Companion.parse(var39.optJSONObject(var26));
                  if (var41 != null) {
                     var37.add(var41);
                  }
               }

               var59 = var37;
            } else {
               var59 = CollectionsKt.emptyList();
            }

            String var50 = var1.optString("firmware", "");
            Intrinsics.checkNotNullExpressionValue(var50, "optString(...)");
            String var51 = var1.optString("module", "");
            Intrinsics.checkNotNullExpressionValue(var51, "optString(...)");
            JSONObject var60 = var1.optJSONObject("speedFeed");
            if (var60 != null && var60.optBoolean("on")) {
               var29 = true;
            } else {
               var29 = false;
            }

            label111: {
               var60 = var1.optJSONObject("speedFeed");
               if (var60 != null) {
                  Double var62 = var60.optDouble("hz", 0.0);
                  var4 = var62.doubleValue();
                  boolean var56;
                  if (!Double.isInfinite(var4) && !Double.isNaN(var4)) {
                     var56 = true;
                  } else {
                     var56 = false;
                  }

                  if (!var56) {
                     var62 = null;
                  }

                  if (var62 != null) {
                     var4 = var62;
                     break label111;
                  }
               }

               var4 = 0.0;
            }

            label100: {
               var60 = var1.optJSONObject("speedFeed");
               if (var60 != null) {
                  if (!var60.has("delayMs")) {
                     var60 = null;
                  }

                  if (var60 != null) {
                     var64 = var60.optInt("delayMs");
                     break label100;
                  }
               }

               var64 = null;
            }

            label94: {
               JSONObject var65 = var1.optJSONObject("action");
               if (var65 != null) {
                  var66 = var65.optString("op", "");
                  if (var66 != null) {
                     break label94;
                  }
               }

               var66 = "";
            }

            String var68;
            label89: {
               JSONObject var67 = var1.optJSONObject("action");
               if (var67 != null) {
                  var68 = var67.optString("state", "");
                  if (var68 != null) {
                     break label89;
                  }
               }

               var68 = "";
            }

            JSONObject var69 = var1.optJSONObject("action");
            double var6;
            if (var69 != null) {
               var6 = var69.optDouble("ageS", 0.0);
            } else {
               var6 = 0.0;
            }

            double var24 = parse$d(var1, "mountYawDeg");
            double var20 = parse$d(var1, "mountPitchDeg");
            double var22 = parse$d(var1, "mountRollDeg");
            JSONArray var52 = var1.optJSONArray("mountErrors");
            List var71;
            if (var52 != null) {
               boolean var30 = false;
               Iterable var53 = RangesKt.until(0, var52.length());
               var var70 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var53, 10));
               Iterator var73 = var53.iterator();

               while (var73.hasNext()) {
                  var70.add(var52.optString(((IntIterator)var73).nextInt()));
               }

               var71 = var70;
            } else {
               var71 = CollectionsKt.emptyList();
            }

            RtkCalibration var74 = RtkCalibration.Companion.parse(var1.optJSONObject("calibration"));
            JSONObject var72 = var1.optJSONObject("mountKept");
            double var8 = Double.NaN;
            if (var72 != null) {
               var8 = var72.optDouble("yaw", Double.NaN);
            }

            return new RtkStatus(
               var32,
               var31,
               var43,
               var42,
               var27,
               var28,
               var16,
               var10,
               var2,
               var18,
               var12,
               var14,
               var35,
               var36,
               var33,
               var45,
               var46,
               var47,
               var49,
               var44,
               var48,
               var59,
               var50,
               var51,
               var29,
               var4,
               var64,
               var66,
               var68,
               var6,
               var24,
               var20,
               var22,
               var71,
               var74,
               var8,
               var1.optBoolean("mountKeeping")
            );
         }
      }
   }
}
