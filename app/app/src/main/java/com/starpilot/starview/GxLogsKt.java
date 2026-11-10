package com.starpilot.starview;

import android.graphics.Color;
import java.util.Map;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;

public final class GxLogsKt {
   private static final Map<String, String> GXLOGS_FEATURES;
   private static final int GXLOGS_RED = Color.rgb(255, 99, 99);

   static {
      Pair var35 = TuplesKt.to("starpilot.system.adj_spot_monitor_vision", "V-ASM");
      Pair var40 = TuplesKt.to("starpilot.system.speed_limit_vision", "Vision Speed Limit Controller");
      Pair var8 = TuplesKt.to("starpilot.system.speed_limit_filler", "Map speed-limit data");
      Pair var9 = TuplesKt.to("starpilot.navigation.mapd_wrapper", "Offline maps");
      Pair var25 = TuplesKt.to("starpilot.navigation.mapd", "Offline maps");
      Pair var21 = TuplesKt.to(".data.galaxy.frpc", "Galaxy remote access");
      Pair var27 = TuplesKt.to("starpilot.navigation.navigationd", "Navigation");
      Pair var14 = TuplesKt.to("system.sentryd.sentryd", "Sentry Mode");
      Pair var34 = TuplesKt.to("starpilot.system.bluetooth.daemon", "Bluetooth connections");
      Pair var36 = TuplesKt.to("starpilot.system.wheel_controls.wheel_controlsd", "Bluetooth controller actions");
      Pair var0 = TuplesKt.to("starpilot.system.model_statsd", "Model statistics");
      Pair var12 = TuplesKt.to("starpilot.system.the_galaxy.the_galaxy", "Galaxy web interface");
      Pair var41 = TuplesKt.to("starpilot.system.galaxy.galaxy", "Galaxy remote access");
      Pair var16 = TuplesKt.to("starpilot.system.device_syncd", "Galaxy settings sync");
      Pair var10 = TuplesKt.to("starpilot.starpilot_process", "StarPilot settings and features");
      Pair var37 = TuplesKt.to("selfdrive.ui.ui", "Comma display");
      Pair var24 = TuplesKt.to("selfdrive.ui.soundd", "Sounds and alerts");
      Pair var29 = TuplesKt.to("selfdrive.modeld.modeld", "Driving model");
      Pair var55 = TuplesKt.to("selfdrive.modeld.dmonitoringmodeld", "Driver monitoring model");
      Pair var56 = TuplesKt.to("selfdrive.monitoring.dmonitoringd", "Driver monitoring");
      Pair var51 = TuplesKt.to("selfdrive.controls.controlsd", "Steering and speed control");
      Pair var11 = TuplesKt.to("selfdrive.controls.plannerd", "Driving planner");
      Pair var49 = TuplesKt.to("selfdrive.controls.radard", "Lead vehicle tracking");
      Pair var45 = TuplesKt.to("selfdrive.selfdrived.selfdrived", "Driving state and engagement");
      Pair var31 = TuplesKt.to("selfdrive.car.card", "Vehicle interface");
      Pair var2 = TuplesKt.to("selfdrive.pandad.pandad", "Panda firmware management");
      Pair var50 = TuplesKt.to("selfdrive.locationd.locationd", "Vehicle positioning");
      Pair var52 = TuplesKt.to("selfdrive.locationd.calibrationd", "Camera calibration");
      Pair var26 = TuplesKt.to("selfdrive.locationd.torqued", "Steering torque calibration");
      Pair var13 = TuplesKt.to("selfdrive.locationd.paramsd", "Vehicle parameter learning");
      Pair var30 = TuplesKt.to("selfdrive.locationd.lagd", "Steering delay estimation");
      Pair var7 = TuplesKt.to("system.hardware.hardwared", "Power and temperature management");
      Pair var23 = TuplesKt.to("system.loggerd.deleter", "Recording cleanup");
      Pair var32 = TuplesKt.to("system.loggerd.uploader", "Log uploads");
      Pair var44 = TuplesKt.to("system.logmessaged", "Application logs");
      Pair var33 = TuplesKt.to("system.statsd", "System statistics");
      Pair var42 = TuplesKt.to("system.tombstoned", "Crash diagnostics");
      Pair var28 = TuplesKt.to("system.updated.updated", "Software updates");
      Pair var58 = TuplesKt.to("system.timed", "Clock synchronization");
      Pair var53 = TuplesKt.to("system.sensord.sensord", "Motion sensors");
      Pair var47 = TuplesKt.to("system.micd", "Microphone");
      Pair var48 = TuplesKt.to("system.qcomgpsd.qcomgpsd", "Onboard GPS");
      Pair var4 = TuplesKt.to("system.ubloxd.ubloxd", "External GPS");
      Pair var19 = TuplesKt.to("system.ubloxd.pigeond", "External GPS management");
      Pair var43 = TuplesKt.to("system.athena.manage_athenad", "Comma remote connection");
      Pair var57 = TuplesKt.to("system.athena.athenad", "Comma remote connection");
      Pair var46 = TuplesKt.to("system.webrtc.webrtcd", "Camera livestream");
      Pair var1 = TuplesKt.to("system.proclogd", "Process diagnostics");
      Pair var20 = TuplesKt.to("system.journald", "System log recording");
      Pair var17 = TuplesKt.to("selfdrive.ui.feedback.feedbackd", "Driver feedback");
      Pair var38 = TuplesKt.to("system.manager.manager", "Process manager");
      Pair var39 = TuplesKt.to("manager", "Process manager");
      Pair var3 = TuplesKt.to("pandad", "Vehicle CAN communication");
      Pair var18 = TuplesKt.to("camerad", "Camera capture");
      Pair var22 = TuplesKt.to("system.camerad.camerad", "Camera capture");
      Pair var15 = TuplesKt.to("loggerd", "Drive recording");
      Pair var5 = TuplesKt.to("system.loggerd.loggerd", "Drive recording");
      Pair var54 = TuplesKt.to("encoderd", "Video encoding");
      Pair var6 = TuplesKt.to("system.loggerd.encoderd", "Video encoding");
      GXLOGS_FEATURES = MapsKt.mapOf(
         new Pair[]{
            var35,
            var40,
            var8,
            var9,
            var25,
            var21,
            var27,
            var14,
            var34,
            var36,
            var0,
            var12,
            var41,
            var16,
            var10,
            var37,
            var24,
            var29,
            var55,
            var56,
            var51,
            var11,
            var49,
            var45,
            var31,
            var2,
            var50,
            var52,
            var26,
            var13,
            var30,
            var7,
            var23,
            var32,
            var44,
            var33,
            var42,
            var28,
            var58,
            var53,
            var47,
            var48,
            var4,
            var19,
            var43,
            var57,
            var46,
            var1,
            var20,
            var17,
            var38,
            var39,
            var3,
            var18,
            var22,
            var15,
            var5,
            var54,
            var6
         }
      );
   }

   // $VF: synthetic method
   public static final Map access$getGXLOGS_FEATURES$p() {
      return GXLOGS_FEATURES;
   }

   // $VF: synthetic method
   public static final int access$getGXLOGS_RED$p() {
      return GXLOGS_RED;
   }
}
