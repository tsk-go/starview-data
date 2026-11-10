package com.starpilot.starview;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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

/* compiled from: RtkCore.kt */
public final /* data */ class RtkStatus {
    public static final Companion Companion = new Companion(null);
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

    public static /* synthetic */ RtkStatus copy$default(RtkStatus rtkStatus, boolean z, boolean z2, String str, String str2, int i, int i2, double d, double d2, double d3, double d4, double d5, double d6, Integer num, Double d7, long j, String str3, String str4, String str5, String str6, String str7, String str8, List list, String str9, String str10, boolean z3, double d8, Integer num2, String str11, String str12, double d9, double d10, double d11, double d12, List list2, RtkCalibration rtkCalibration, double d13, boolean z4, int i3, int i4, Object obj) {
        boolean z5 = (i3 & 1) != 0 ? rtkStatus.connected : z;
        boolean z6 = (i3 & 2) != 0 ? rtkStatus.fresh : z2;
        String str13 = (i3 & 4) != 0 ? rtkStatus.rtk : str;
        String str14 = (i3 & 8) != 0 ? rtkStatus.fixType : str2;
        int i5 = (i3 & 16) != 0 ? rtkStatus.used : i;
        int i6 = (i3 & 32) != 0 ? rtkStatus.heard : i2;
        double d14 = (i3 & 64) != 0 ? rtkStatus.lat : d;
        double d15 = (i3 & 128) != 0 ? rtkStatus.lon : d2;
        double d16 = (i3 & 256) != 0 ? rtkStatus.altM : d3;
        double d17 = (i3 & 512) != 0 ? rtkStatus.hAccM : d4;
        double d18 = (i3 & 1024) != 0 ? rtkStatus.speedMs : d5;
        double d19 = (i3 & 2048) != 0 ? rtkStatus.headingDeg : d6;
        Integer num3 = (i3 & 4096) != 0 ? rtkStatus.correctionAgeS : num;
        Double d20 = (i3 & 8192) != 0 ? rtkStatus.rtcmAgeS : d7;
        Integer num4 = num3;
        long j2 = (i3 & 16384) != 0 ? rtkStatus.rtcmBytes : j;
        String str15 = (i3 & 32768) != 0 ? rtkStatus.fusion : str3;
        String str16 = (65536 & i3) != 0 ? rtkStatus.mount : str4;
        String str17 = (i3 & 131072) != 0 ? rtkStatus.imuInit : str5;
        String str18 = (i3 & 262144) != 0 ? rtkStatus.insInit : str6;
        String str19 = (i3 & 524288) != 0 ? rtkStatus.mountInit : str7;
        String str20 = (i3 & 1048576) != 0 ? rtkStatus.speedInit : str8;
        List list3 = (i3 & 2097152) != 0 ? rtkStatus.sensors : list;
        String str21 = (i3 & 4194304) != 0 ? rtkStatus.firmware : str9;
        String str22 = (i3 & 8388608) != 0 ? rtkStatus.module : str10;
        String str23 = str15;
        boolean z7 = (i3 & 16777216) != 0 ? rtkStatus.speedFeedOn : z3;
        double d21 = (i3 & 33554432) != 0 ? rtkStatus.speedFeedHz : d8;
        Integer num5 = (i3 & 67108864) != 0 ? rtkStatus.speedFeedDelayMs : num2;
        return rtkStatus.copy(z5, z6, str13, str14, i5, i6, d14, d15, d16, d17, d18, d19, num4, d20, j2, str23, str16, str17, str18, str19, str20, list3, str21, str22, z7, d21, num5, (134217728 & i3) != 0 ? rtkStatus.actionOp : str11, (i3 & 268435456) != 0 ? rtkStatus.actionState : str12, (i3 & 536870912) != 0 ? rtkStatus.actionAgeS : d9, (i3 & 1073741824) != 0 ? rtkStatus.mountYawDeg : d10, (i3 & Integer.MIN_VALUE) != 0 ? rtkStatus.mountPitchDeg : d11, (i4 & 1) != 0 ? rtkStatus.mountRollDeg : d12, (i4 & 2) != 0 ? rtkStatus.mountErrors : list2, (i4 & 4) != 0 ? rtkStatus.calibration : rtkCalibration, (i4 & 8) != 0 ? rtkStatus.mountKeptYaw : d13, (i4 & 16) != 0 ? rtkStatus.mountKeeping : z4);
    }














    public final List<RtkSensor> component22() {
        return this.sensors;
    }












    public final List<String> component34() {
        return this.mountErrors;
    }









    public final RtkStatus copy(boolean connected, boolean fresh, String rtk, String fixType, int used, int heard, double lat, double lon, double altM, double hAccM, double speedMs, double headingDeg, Integer correctionAgeS, Double rtcmAgeS, long rtcmBytes, String fusion, String mount, String imuInit, String insInit, String mountInit, String speedInit, List<RtkSensor> sensors, String firmware, String module, boolean speedFeedOn, double speedFeedHz, Integer speedFeedDelayMs, String actionOp, String actionState, double actionAgeS, double mountYawDeg, double mountPitchDeg, double mountRollDeg, List<String> mountErrors, RtkCalibration calibration, double mountKeptYaw, boolean mountKeeping) {
        Intrinsics.checkNotNullParameter(rtk, "rtk");
        Intrinsics.checkNotNullParameter(fixType, "fixType");
        Intrinsics.checkNotNullParameter(fusion, "fusion");
        Intrinsics.checkNotNullParameter(mount, "mount");
        Intrinsics.checkNotNullParameter(imuInit, "imuInit");
        Intrinsics.checkNotNullParameter(insInit, "insInit");
        Intrinsics.checkNotNullParameter(mountInit, "mountInit");
        Intrinsics.checkNotNullParameter(speedInit, "speedInit");
        Intrinsics.checkNotNullParameter(sensors, "sensors");
        Intrinsics.checkNotNullParameter(firmware, "firmware");
        Intrinsics.checkNotNullParameter(module, "module");
        Intrinsics.checkNotNullParameter(actionOp, "actionOp");
        Intrinsics.checkNotNullParameter(actionState, "actionState");
        Intrinsics.checkNotNullParameter(mountErrors, "mountErrors");
        return new RtkStatus(connected, fresh, rtk, fixType, used, heard, lat, lon, altM, hAccM, speedMs, headingDeg, correctionAgeS, rtcmAgeS, rtcmBytes, fusion, mount, imuInit, insInit, mountInit, speedInit, sensors, firmware, module, speedFeedOn, speedFeedHz, speedFeedDelayMs, actionOp, actionState, actionAgeS, mountYawDeg, mountPitchDeg, mountRollDeg, mountErrors, calibration, mountKeptYaw, mountKeeping);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof RtkStatus)) {
            return false;
        }
        RtkStatus rtkStatus = (RtkStatus) other;
        return this.connected == rtkStatus.connected && this.fresh == rtkStatus.fresh && Intrinsics.areEqual(this.rtk, rtkStatus.rtk) && Intrinsics.areEqual(this.fixType, rtkStatus.fixType) && this.used == rtkStatus.used && this.heard == rtkStatus.heard && Double.compare(this.lat, rtkStatus.lat) == 0 && Double.compare(this.lon, rtkStatus.lon) == 0 && Double.compare(this.altM, rtkStatus.altM) == 0 && Double.compare(this.hAccM, rtkStatus.hAccM) == 0 && Double.compare(this.speedMs, rtkStatus.speedMs) == 0 && Double.compare(this.headingDeg, rtkStatus.headingDeg) == 0 && Intrinsics.areEqual(this.correctionAgeS, rtkStatus.correctionAgeS) && Intrinsics.areEqual((Object) this.rtcmAgeS, (Object) rtkStatus.rtcmAgeS) && this.rtcmBytes == rtkStatus.rtcmBytes && Intrinsics.areEqual(this.fusion, rtkStatus.fusion) && Intrinsics.areEqual(this.mount, rtkStatus.mount) && Intrinsics.areEqual(this.imuInit, rtkStatus.imuInit) && Intrinsics.areEqual(this.insInit, rtkStatus.insInit) && Intrinsics.areEqual(this.mountInit, rtkStatus.mountInit) && Intrinsics.areEqual(this.speedInit, rtkStatus.speedInit) && Intrinsics.areEqual(this.sensors, rtkStatus.sensors) && Intrinsics.areEqual(this.firmware, rtkStatus.firmware) && Intrinsics.areEqual(this.module, rtkStatus.module) && this.speedFeedOn == rtkStatus.speedFeedOn && Double.compare(this.speedFeedHz, rtkStatus.speedFeedHz) == 0 && Intrinsics.areEqual(this.speedFeedDelayMs, rtkStatus.speedFeedDelayMs) && Intrinsics.areEqual(this.actionOp, rtkStatus.actionOp) && Intrinsics.areEqual(this.actionState, rtkStatus.actionState) && Double.compare(this.actionAgeS, rtkStatus.actionAgeS) == 0 && Double.compare(this.mountYawDeg, rtkStatus.mountYawDeg) == 0 && Double.compare(this.mountPitchDeg, rtkStatus.mountPitchDeg) == 0 && Double.compare(this.mountRollDeg, rtkStatus.mountRollDeg) == 0 && Intrinsics.areEqual(this.mountErrors, rtkStatus.mountErrors) && Intrinsics.areEqual(this.calibration, rtkStatus.calibration) && Double.compare(this.mountKeptYaw, rtkStatus.mountKeptYaw) == 0 && this.mountKeeping == rtkStatus.mountKeeping;
    }

    public int hashCode() {
        return (((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((Boolean.hashCode(this.connected) * 31) + Boolean.hashCode(this.fresh)) * 31) + this.rtk.hashCode()) * 31) + this.fixType.hashCode()) * 31) + Integer.hashCode(this.used)) * 31) + Integer.hashCode(this.heard)) * 31) + Double.hashCode(this.lat)) * 31) + Double.hashCode(this.lon)) * 31) + Double.hashCode(this.altM)) * 31) + Double.hashCode(this.hAccM)) * 31) + Double.hashCode(this.speedMs)) * 31) + Double.hashCode(this.headingDeg)) * 31) + (this.correctionAgeS == null ? 0 : this.correctionAgeS.hashCode())) * 31) + (this.rtcmAgeS == null ? 0 : this.rtcmAgeS.hashCode())) * 31) + Long.hashCode(this.rtcmBytes)) * 31) + this.fusion.hashCode()) * 31) + this.mount.hashCode()) * 31) + this.imuInit.hashCode()) * 31) + this.insInit.hashCode()) * 31) + this.mountInit.hashCode()) * 31) + this.speedInit.hashCode()) * 31) + this.sensors.hashCode()) * 31) + this.firmware.hashCode()) * 31) + this.module.hashCode()) * 31) + Boolean.hashCode(this.speedFeedOn)) * 31) + Double.hashCode(this.speedFeedHz)) * 31) + (this.speedFeedDelayMs == null ? 0 : this.speedFeedDelayMs.hashCode())) * 31) + this.actionOp.hashCode()) * 31) + this.actionState.hashCode()) * 31) + Double.hashCode(this.actionAgeS)) * 31) + Double.hashCode(this.mountYawDeg)) * 31) + Double.hashCode(this.mountPitchDeg)) * 31) + Double.hashCode(this.mountRollDeg)) * 31) + this.mountErrors.hashCode()) * 31) + (this.calibration != null ? this.calibration.hashCode() : 0)) * 31) + Double.hashCode(this.mountKeptYaw)) * 31) + Boolean.hashCode(this.mountKeeping);
    }

    public String toString() {
        return "RtkStatus(connected=" + this.connected + ", fresh=" + this.fresh + ", rtk=" + this.rtk + ", fixType=" + this.fixType + ", used=" + this.used + ", heard=" + this.heard + ", lat=" + this.lat + ", lon=" + this.lon + ", altM=" + this.altM + ", hAccM=" + this.hAccM + ", speedMs=" + this.speedMs + ", headingDeg=" + this.headingDeg + ", correctionAgeS=" + this.correctionAgeS + ", rtcmAgeS=" + this.rtcmAgeS + ", rtcmBytes=" + this.rtcmBytes + ", fusion=" + this.fusion + ", mount=" + this.mount + ", imuInit=" + this.imuInit + ", insInit=" + this.insInit + ", mountInit=" + this.mountInit + ", speedInit=" + this.speedInit + ", sensors=" + this.sensors + ", firmware=" + this.firmware + ", module=" + this.module + ", speedFeedOn=" + this.speedFeedOn + ", speedFeedHz=" + this.speedFeedHz + ", speedFeedDelayMs=" + this.speedFeedDelayMs + ", actionOp=" + this.actionOp + ", actionState=" + this.actionState + ", actionAgeS=" + this.actionAgeS + ", mountYawDeg=" + this.mountYawDeg + ", mountPitchDeg=" + this.mountPitchDeg + ", mountRollDeg=" + this.mountRollDeg + ", mountErrors=" + this.mountErrors + ", calibration=" + this.calibration + ", mountKeptYaw=" + this.mountKeptYaw + ", mountKeeping=" + this.mountKeeping + ")";
    }

    public RtkStatus(boolean connected, boolean fresh, String rtk, String fixType, int used, int heard, double lat, double lon, double altM, double hAccM, double speedMs, double headingDeg, Integer correctionAgeS, Double rtcmAgeS, long rtcmBytes, String fusion, String mount, String imuInit, String insInit, String mountInit, String speedInit, List<RtkSensor> sensors, String firmware, String module, boolean speedFeedOn, double speedFeedHz, Integer speedFeedDelayMs, String actionOp, String actionState, double actionAgeS, double mountYawDeg, double mountPitchDeg, double mountRollDeg, List<String> mountErrors, RtkCalibration calibration, double mountKeptYaw, boolean mountKeeping) {
        Intrinsics.checkNotNullParameter(rtk, "rtk");
        Intrinsics.checkNotNullParameter(fixType, "fixType");
        Intrinsics.checkNotNullParameter(fusion, "fusion");
        Intrinsics.checkNotNullParameter(mount, "mount");
        Intrinsics.checkNotNullParameter(imuInit, "imuInit");
        Intrinsics.checkNotNullParameter(insInit, "insInit");
        Intrinsics.checkNotNullParameter(mountInit, "mountInit");
        Intrinsics.checkNotNullParameter(speedInit, "speedInit");
        Intrinsics.checkNotNullParameter(sensors, "sensors");
        Intrinsics.checkNotNullParameter(firmware, "firmware");
        Intrinsics.checkNotNullParameter(module, "module");
        Intrinsics.checkNotNullParameter(actionOp, "actionOp");
        Intrinsics.checkNotNullParameter(actionState, "actionState");
        Intrinsics.checkNotNullParameter(mountErrors, "mountErrors");
        this.connected = connected;
        this.fresh = fresh;
        this.rtk = rtk;
        this.fixType = fixType;
        this.used = used;
        this.heard = heard;
        this.lat = lat;
        this.lon = lon;
        this.altM = altM;
        this.hAccM = hAccM;
        this.speedMs = speedMs;
        this.headingDeg = headingDeg;
        this.correctionAgeS = correctionAgeS;
        this.rtcmAgeS = rtcmAgeS;
        this.rtcmBytes = rtcmBytes;
        this.fusion = fusion;
        this.mount = mount;
        this.imuInit = imuInit;
        this.insInit = insInit;
        this.mountInit = mountInit;
        this.speedInit = speedInit;
        this.sensors = sensors;
        this.firmware = firmware;
        this.module = module;
        this.speedFeedOn = speedFeedOn;
        this.speedFeedHz = speedFeedHz;
        this.speedFeedDelayMs = speedFeedDelayMs;
        this.actionOp = actionOp;
        this.actionState = actionState;
        this.actionAgeS = actionAgeS;
        this.mountYawDeg = mountYawDeg;
        this.mountPitchDeg = mountPitchDeg;
        this.mountRollDeg = mountRollDeg;
        this.mountErrors = mountErrors;
        this.calibration = calibration;
        this.mountKeptYaw = mountKeptYaw;
        this.mountKeeping = mountKeeping;
    }

    public /* synthetic */ RtkStatus(boolean z, boolean z2, String str, String str2, int i, int i2, double d, double d2, double d3, double d4, double d5, double d6, Integer num, Double d7, long j, String str3, String str4, String str5, String str6, String str7, String str8, List list, String str9, String str10, boolean z3, double d8, Integer num2, String str11, String str12, double d9, double d10, double d11, double d12, List list2, RtkCalibration rtkCalibration, double d13, boolean z4, int i3, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        this(z, z2, str, str2, i, i2, d, d2, d3, d4, d5, d6, num, d7, j, str3, str4, (i3 & 131072) != 0 ? "" : str5, (i3 & 262144) != 0 ? "" : str6, (i3 & 524288) != 0 ? "" : str7, (i3 & 1048576) != 0 ? "" : str8, (i3 & 2097152) != 0 ? CollectionsKt.emptyList() : list, (i3 & 4194304) != 0 ? "" : str9, (i3 & 8388608) != 0 ? "" : str10, (i3 & 16777216) != 0 ? false : z3, (i3 & 33554432) != 0 ? 0.0d : d8, (i3 & 67108864) != 0 ? null : num2, (i3 & 134217728) != 0 ? "" : str11, (i3 & 268435456) != 0 ? "" : str12, (i3 & 536870912) != 0 ? 0.0d : d9, (i3 & 1073741824) != 0 ? Double.NaN : d10, (i3 & Integer.MIN_VALUE) != 0 ? Double.NaN : d11, (i4 & 1) != 0 ? Double.NaN : d12, (i4 & 2) != 0 ? CollectionsKt.emptyList() : list2, (i4 & 4) != 0 ? null : rtkCalibration, (i4 & 8) != 0 ? Double.NaN : d13, (i4 & 16) != 0 ? false : z4);
    }

    public final boolean getConnected() {
        return this.connected;
    }

    public final String getFixType() {
        return this.fixType;
    }

    public final boolean getFresh() {
        return this.fresh;
    }

    public final int getHeard() {
        return this.heard;
    }

    public final String getRtk() {
        return this.rtk;
    }

    public final int getUsed() {
        return this.used;
    }

    public final double getAltM() {
        return this.altM;
    }

    public final double getHAccM() {
        return this.hAccM;
    }

    public final double getHeadingDeg() {
        return this.headingDeg;
    }

    public final double getLat() {
        return this.lat;
    }

    public final double getLon() {
        return this.lon;
    }

    public final double getSpeedMs() {
        return this.speedMs;
    }

    public final Integer getCorrectionAgeS() {
        return this.correctionAgeS;
    }

    public final String getFusion() {
        return this.fusion;
    }

    public final String getMount() {
        return this.mount;
    }

    public final Double getRtcmAgeS() {
        return this.rtcmAgeS;
    }

    public final long getRtcmBytes() {
        return this.rtcmBytes;
    }

    public final String getImuInit() {
        return this.imuInit;
    }

    public final String getInsInit() {
        return this.insInit;
    }

    public final String getMountInit() {
        return this.mountInit;
    }

    public final String getSpeedInit() {
        return this.speedInit;
    }

    public final String getFirmware() {
        return this.firmware;
    }

    public final String getModule() {
        return this.module;
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

    public final double getActionAgeS() {
        return this.actionAgeS;
    }

    public final String getActionOp() {
        return this.actionOp;
    }

    public final String getActionState() {
        return this.actionState;
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

    public final RtkCalibration getCalibration() {
        return this.calibration;
    }

    public final List<String> getMountErrors() {
        return this.mountErrors;
    }

    public final boolean getMountKeeping() {
        return this.mountKeeping;
    }

    public final double getMountKeptYaw() {
        return this.mountKeptYaw;
    }

    public final boolean getMountKept() {
        double d = this.mountKeptYaw;
        return (Double.isInfinite(d) || Double.isNaN(d)) ? false : true;
    }

    public final boolean getHasFix() {
        if (!this.fresh || Intrinsics.areEqual(this.fixType, "none") || Intrinsics.areEqual(this.fixType, "time only")) {
            return false;
        }
        double d = this.lat;
        if (!((Double.isInfinite(d) || Double.isNaN(d)) ? false : true)) {
            return false;
        }
        double d2 = this.lon;
        return !Double.isInfinite(d2) && !Double.isNaN(d2);
    }

    public final String getWords() {
        return !this.connected ? "RTK receiver unplugged" : !this.fresh ? "RTK receiver silent" : Intrinsics.areEqual(this.rtk, "fixed") ? "RTK Fixed" : Intrinsics.areEqual(this.rtk, "float") ? "RTK Float" : Intrinsics.areEqual(this.fixType, "dead reckoning") ? "Dead reckoning" : Intrinsics.areEqual(this.fixType, "none") ? "Searching" : "3D Fix";
    }

    /* compiled from: RtkCore.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* VF: from Vineflower (jadx: Removed duplicated region;incorrect) */
        public final RtkStatus parse(JSONObject var1) {
        Integer var64 = null; String var66 = null; // declarations Vineflower left out
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

        private static final double parse$d(JSONObject $o, String k) {
            if (!$o.has(k) || $o.isNull(k)) {
                return Double.NaN;
            }
            return $o.optDouble(k, Double.NaN);
        }

        public final String accuracy(double m, boolean metric) {
            if (!((Double.isInfinite(m) || Double.isNaN(m)) ? false : true)) {
                return "–";
            }
            if (metric && m < 1.0d) {
                String format = String.format(Locale.US, "%.1f cm", Arrays.copyOf(new Object[]{Double.valueOf(100 * m)}, 1));
                Intrinsics.checkNotNullExpressionValue(format, "format(...)");
                return format;
            }
            if (metric) {
                String format2 = String.format(Locale.US, "%.1f m", Arrays.copyOf(new Object[]{Double.valueOf(m)}, 1));
                Intrinsics.checkNotNullExpressionValue(format2, "format(...)");
                return format2;
            }
            if (m >= 0.3048d) {
                return Math.round(m / 0.3048d) + " ft";
            }
            String format3 = String.format(Locale.US, "%.1f in", Arrays.copyOf(new Object[]{Double.valueOf(m / 0.0254d)}, 1));
            Intrinsics.checkNotNullExpressionValue(format3, "format(...)");
            return format3;
        }
    }
}
