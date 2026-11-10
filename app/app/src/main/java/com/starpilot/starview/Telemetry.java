package com.starpilot.starview;

import ai.comma.cereal.Log.SensorEventData.Which;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.capnproto.PrimitiveList.Float.Reader;
import org.capnproto.SlcPeek.Slc;
import org.json.JSONObject;

public final class Telemetry {
   public static final Telemetry.Companion Companion = new Telemetry.Companion(null);
   private final long NAV_HOLD_MS;
   private final float TURN_MIN_V;
   private volatile float aEgo;
   private volatile float aTarget;
   private volatile float[] accelRaw;
   private volatile String accelSrc;
   private volatile float actAccel;
   private volatile float actTorque;
   private volatile boolean active;
   private volatile String alertSize;
   private volatile String alertStatus;
   private volatile String alertText1;
   private volatile String alertText2;
   private volatile boolean allowThrottle;
   private volatile float angleOffsetDeg;
   private volatile boolean aolEnabled;
   private volatile float approachStopLength;
   private volatile float baseDelay;
   private volatile float baseFriction;
   private volatile float baseLatAccel;
   private volatile float bearingDeg;
   private volatile boolean brakeLights;
   private volatile boolean brakePressed;
   private final ConcurrentHashMap<String, Long> byteCount;
   private volatile long calFrame;
   private volatile float calHeight;
   private volatile float[] calRpy;
   private volatile boolean calValid;
   private volatile boolean chestnutPresent;
   private volatile float cpuTempC;
   private volatile float cpuUsage;
   private volatile boolean cruiseEnabled;
   private volatile float cruiseSpeed;
   private volatile boolean cscControlling;
   private volatile float cscSpeed;
   private volatile boolean cscTraining;
   private volatile float curvature;
   private volatile float dashSpeedLimit;
   private volatile long delayAtNs;
   private volatile float desiredCurvature;
   private volatile long desiredFollowDistance;
   private volatile String deviceType;
   private volatile int dmAwareness;
   private volatile boolean dmFace;
   private volatile float dmPitch;
   private volatile boolean dmPolicyVision;
   private volatile boolean dmRhd;
   private volatile float dmYaw;
   private volatile boolean enabled;
   private volatile boolean engageable;
   private volatile boolean experimentalMode;
   private volatile boolean fcw;
   private volatile boolean forceCoast;
   private volatile boolean forcingStop;
   private volatile float forcingStopLength;
   private volatile float freeSpacePct;
   private volatile float frictionLearned;
   private volatile boolean gasPressed;
   private volatile String gear = "unknown";
   private volatile long gnssAtNs;
   private volatile List<GnssSat> gnssSats;
   private final ConcurrentHashMap<String, GpsFixInfo> gpsFixes;
   private volatile double gpsLat;
   private volatile double gpsLon;
   private volatile float[] gyroRaw;
   private volatile String gyroSrc;
   private JSONObject heldNav;
   private long heldNavMs;
   private volatile Map<String, ? extends Object> info;
   private volatile float laneWidthLeft;
   private volatile float laneWidthRight;
   private volatile long lastAthenaPing;
   private volatile long lastEventMonoNs;
   private long lastExtFixMs;
   private volatile long lastModelRxNs;
   private String lastTogglesStr;
   private volatile float latAccelLearned;
   private volatile boolean latActive;
   private volatile org.capnproto.SlcPeek.LaneCentering lc;
   private volatile long lcAtNs;
   private volatile Lead leadLeft;
   private volatile Lead leadOne;
   private volatile Lead leadRight;
   private volatile Lead leadTwo;
   private volatile float learnedDelay;
   private volatile boolean leftBlindspot;
   private volatile boolean leftBlinker;
   private volatile boolean longActive;
   private volatile long mapdAtNs;
   private volatile boolean mapdFreeway;
   private volatile int mapdLanes;
   private volatile boolean mapdMatched;
   private volatile boolean mapdOneWay;
   private volatile String mapdWayName = "";
   private volatile String mapdWayRef = "";
   private volatile float maxAccel;
   private volatile float maxTempC;
   private volatile Map<String, ? extends Object> memParams;
   private volatile int memUsage;
   private volatile ModelSnapshot model;
   private volatile boolean modelSeen;
   private final ConcurrentHashMap<String, Long> msgCount;
   private volatile int networkStrength;
   private volatile String networkType;
   private volatile String opState = "disabled";
   private volatile float outTorque;
   private volatile Map<String, ? extends Object> params;
   private volatile int paramsGeneration;
   private volatile boolean pauseLateral;
   private volatile boolean pauseLongitudinal;
   private volatile String personality;
   private volatile String planSource;
   private volatile float planVCruise;
   private volatile float[] poseAccel;
   private volatile long poseAtNs;
   private volatile float[] poseGyro;
   private volatile boolean poseOK;
   private volatile boolean poseValid;
   private volatile float poseYawZ;
   private volatile boolean pulseAndGlide;
   private volatile boolean pulseGlideCoasting;
   private volatile RadarTrack[] radar;
   private volatile long radarFrame;
   private final ConcurrentHashMap<String, Rate> rates;
   private volatile boolean redLight;
   private volatile boolean rightBlindspot;
   private volatile boolean rightBlinker;
   private volatile float roadCurvature;
   private volatile String roadName;
   private volatile float roll;
   private volatile double rttMs;
   private volatile String sensor;
   private volatile Slc slc;
   private volatile long slcAtNs;
   private volatile boolean slcChanged;
   private volatile float slcMapSpeedLimit;
   private volatile float slcMapbox;
   private volatile float slcNext;
   private volatile float slcOffset;
   private volatile float slcOverridden;
   private volatile String slcSource;
   private volatile float slcSpeedLimit;
   private volatile float slcUnconfirmed;
   private final SlowReason.Tracker slow;
   private volatile boolean standstill;
   private volatile boolean started;
   private volatile float steerRatioLive;
   private volatile float steeringAngleDeg;
   private volatile boolean steeringPressed;
   private volatile float steeringTorque;
   private volatile float stiffness;
   private volatile boolean stopSignConfirmed;
   private volatile String thermalStatus;
   private volatile Map<String, ? extends Object> toggles;
   private volatile long torqueAtNs;
   private volatile boolean torqueUseParams;
   private volatile boolean trackingLead;
   private volatile float[] tracks;
   private volatile boolean trafficMode;
   private long turnCsMono;
   private volatile double turnGpsDeg;
   private volatile String turnGpsSrc;
   private volatile double turnGyroDeg;
   private float turnLastBearing;
   private long turnPoseMono;
   private volatile double turnSteerDeg;
   public UiSettings ui;
   private volatile float vCruise;
   private volatile float vCruiseCluster;
   private volatile float vEgo;
   private volatile float vEgoCluster;
   private volatile boolean weatherDay;
   private volatile int weatherId;
   private volatile float yawRate;

   // $VF: synthetic method
   public static Long $r8$lambda$m644ecDVxbeCwWUVfRh1sxm8_bs/* $VF was: $r8$lambda$m644ecDVxbeCwWUVfRh1sxm8_bs*/(Function2 var0, Object var1, Object var2) {
      return count$lambda$2(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Long $r8$lambda$xYu3T_6QfvU8OcivZ36cGwdcL1w/* $VF was: $r8$lambda$xYu3T_6QfvU8OcivZ36cGwdcL1w*/(Function2 var0, Object var1, Object var2) {
      return count$lambda$3(var0, var1, var2);
   }

   public Telemetry() {
      this.alertText1 = "";
      this.alertText2 = "";
      this.alertSize = "none";
      this.alertStatus = "normal";
      this.personality = "standard";
      this.allowThrottle = true;
      this.planSource = "cruise";
      this.slow = new SlowReason.Tracker();
      this.thermalStatus = "green";
      this.networkType = "none";
      this.deviceType = "unknown";
      this.sensor = "unknown";
      this.calRpy = new float[]{0.0F, 0.0F, 0.0F};
      this.calHeight = 1.22F;
      this.leadOne = new Lead(false, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, false, 64, null);
      this.leadTwo = new Lead(false, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, false, 64, null);
      this.leadLeft = new Lead(false, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, false, 64, null);
      this.leadRight = new Lead(false, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, false, 64, null);
      this.dmAwareness = 100;
      this.slcSource = "";
      this.weatherDay = true;
      this.toggles = MapsKt.emptyMap();
      this.lastTogglesStr = "";
      this.roadName = "";
      this.bearingDeg = Float.NaN;
      this.poseYawZ = Float.NaN;
      this.gpsLat = Double.NaN;
      this.gpsLon = Double.NaN;
      this.tracks = new float[0];
      this.radar = new RadarTrack[0];
      this.learnedDelay = Float.NaN;
      this.frictionLearned = Float.NaN;
      this.latAccelLearned = Float.NaN;
      this.stiffness = Float.NaN;
      this.baseDelay = Float.NaN;
      this.baseFriction = Float.NaN;
      this.baseLatAccel = Float.NaN;
      this.maxAccel = Float.NaN;
      this.gpsFixes = new ConcurrentHashMap<>();
      this.gnssSats = CollectionsKt.emptyList();
      this.rates = new ConcurrentHashMap<>();
      this.poseGyro = new float[]{Float.NaN, Float.NaN, Float.NaN};
      this.poseAccel = new float[]{Float.NaN, Float.NaN, Float.NaN};
      this.accelRaw = new float[]{Float.NaN, Float.NaN, Float.NaN};
      this.accelSrc = "";
      this.gyroRaw = new float[]{Float.NaN, Float.NaN, Float.NaN};
      this.gyroSrc = "";
      this.turnLastBearing = Float.NaN;
      this.TURN_MIN_V = 1.5F;
      this.params = MapsKt.emptyMap();
      this.memParams = MapsKt.emptyMap();
      this.info = MapsKt.emptyMap();
      this.msgCount = new ConcurrentHashMap<>();
      this.byteCount = new ConcurrentHashMap<>();
      this.rttMs = -1.0;
      this.NAV_HOLD_MS = 30000L;
   }

   private static final Map<String, Object> applyParams$toMap(JSONObject var0) {
      HashMap var3 = new HashMap();
      if (var0 == null) {
         return var3;
      } else {
         Iterator var2 = var0.keys();
         Intrinsics.checkNotNullExpressionValue(var2, "keys(...)");

         while (var2.hasNext()) {
            String var1 = (String)var2.next();
            var3.put(var1, var0.opt(var1));
         }

         return var3;
      }
   }

   private static final Long count$lambda$2(Function2 var0, Object var1, Object var2) {
      return (Long)var0.invoke(var1, var2);
   }

   private static final Long count$lambda$3(Function2 var0, Object var1, Object var2) {
      return (Long)var0.invoke(var1, var2);
   }

   private final float[] fl(Reader var1) {
      int var3 = var1.size();
      float[] var4 = new float[var3];

      for (int var2 = 0; var2 < var3; var2++) {
         var4[var2] = var1.get(var2);
      }

      return var4;
   }

   // $VF: synthetic method
   public static String iStr$default(Telemetry var0, String var1, String var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = "";
      }

      return var0.iStr(var1, var2);
   }

   // $VF: synthetic method
   public static Pair navCard$default(Telemetry var0, long var1, int var3, Object var4) {
      if ((var3 & 1) != 0) {
         var1 = System.currentTimeMillis();
      }

      return var0.navCard(var1);
   }

   // $VF: synthetic method
   public static String pStr$default(Telemetry var0, String var1, String var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = "";
      }

      return var0.pStr(var1, var2);
   }

   // $VF: synthetic method
   public static Slc slcLive$default(Telemetry var0, long var1, int var3, Object var4) {
      if ((var3 & 1) != 0) {
         var1 = System.nanoTime();
      }

      return var0.slcLive(var1);
   }

   private final Boolean truthy(Object var1) {
      Object var5 = null;
      if (var1 == null) {
         var1 = (Boolean)var5;
      } else if (var1 instanceof Boolean) {
         var1 = var1;
      } else {
         boolean var4 = var1 instanceof Number;
         boolean var2 = false;
         boolean var3 = false;
         if (var4) {
            var2 = var3;
            if (((Number)var1).doubleValue() == 0.0) {
               var2 = true;
            }

            var1 = var2 ^ true;
         } else if (var1 instanceof String) {
            label66: {
               label67: {
                  String var6 = StringsKt.trim((String & CharSequence)var1).toString().toLowerCase(Locale.ROOT);
                  Intrinsics.checkNotNullExpressionValue(var6, "toLowerCase(...)");
                  switch (var6.hashCode()) {
                     case 0:
                        if (var6.equals("")) {
                           break label67;
                        }
                        break;
                     case 48:
                        if (var6.equals("0")) {
                           break label67;
                        }
                        break;
                     case 49:
                        if (var6.equals("1")) {
                           break label66;
                        }
                        break;
                     case 3521:
                        if (var6.equals("no")) {
                           break label67;
                        }
                        break;
                     case 119527:
                        if (var6.equals("yes")) {
                           break label66;
                        }
                        break;
                     case 3569038:
                        if (var6.equals("true")) {
                           break label66;
                        }
                        break;
                     case 97196323:
                        if (var6.equals("false")) {
                           break label67;
                        }
                  }

                  Double var9 = StringsKt.toDoubleOrNull((String)var1);
                  var1 = (Boolean)var5;
                  if (var9 != null) {
                     if (var9.doubleValue() == 0.0) {
                        var2 = true;
                     }

                     var1 = var2 ^ true;
                  }

                  return var1;
               }

               var1 = false;
               return var1;
            }

            var1 = true;
         } else {
            var1 = (Boolean)var5;
         }
      }

      return var1;
   }

   public final void apply(ai.comma.cereal.Log.Event.Reader param1) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index 2 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:385)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:551)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1051)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:501)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
      //
      // Bytecode:
      // 0000: aload 1
      // 0001: ldc_w "evt"
      // 0004: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
      // 0007: aload 0
      // 0008: aload 1
      // 0009: invokevirtual ai/comma/cereal/Log$Event$Reader.getLogMonoTime ()J
      // 000c: putfield com/starpilot/starview/Telemetry.lastEventMonoNs J
      // 000f: aload 1
      // 0010: invokevirtual ai/comma/cereal/Log$Event$Reader.which ()Lai/comma/cereal/Log$Event$Which;
      // 0013: astore 34
      // 0015: aload 34
      // 0017: ifnonnull 0020
      // 001a: bipush -1
      // 001b: istore 18
      // 001d: goto 002b
      // 0020: getstatic com/starpilot/starview/Telemetry$WhenMappings.$EnumSwitchMapping$1 [I
      // 0023: aload 34
      // 0025: invokevirtual ai/comma/cereal/Log$Event$Which.ordinal ()I
      // 0028: iaload
      // 0029: istore 18
      // 002b: dconst_0
      // 002c: dstore 4
      // 002e: bipush 1
      // 002f: istore 24
      // 0031: bipush 1
      // 0032: istore 19
      // 0034: bipush 1
      // 0035: istore 21
      // 0037: bipush 1
      // 0038: istore 20
      // 003a: bipush 0
      // 003b: istore 22
      // 003d: iload 18
      // 003f: tableswitch 125 1 28 5213 5027 4999 4946 4925 4870 4519 4193 4157 4058 3140 3037 2934 2851 2219 2173 2148 2105 2044 1968 1608 1526 1388 1210 453 453 301 133
      // 00bc: aload 1
      // 00bd: checkcast org/capnproto/StructReader
      // 00c0: astore 1
      // 00c1: goto 16bc
      // 00c4: aload 1
      // 00c5: invokevirtual ai/comma/cereal/Log$Event$Reader.getLiveTracks ()Lai/comma/cereal/Car$RadarData$Reader;
      // 00c8: invokevirtual ai/comma/cereal/Car$RadarData$Reader.getPoints ()Lorg/capnproto/StructList$Reader;
      // 00cb: astore 1
      // 00cc: aload 1
      // 00cd: invokevirtual org/capnproto/StructList$Reader.size ()I
      // 00d0: istore 19
      // 00d2: iload 19
      // 00d4: bipush 2
      // 00d5: imul
      // 00d6: newarray 6
      // 00d8: astore 35
      // 00da: new java/util/ArrayList
      // 00dd: dup
      // 00de: iload 19
      // 00e0: invokespecial java/util/ArrayList.<init> (I)V
      // 00e3: astore 36
      // 00e5: bipush 0
      // 00e6: istore 18
      // 00e8: iload 18
      // 00ea: iload 19
      // 00ec: if_icmpge 0140
      // 00ef: aload 1
      // 00f0: iload 18
      // 00f2: invokevirtual org/capnproto/StructList$Reader.get (I)Ljava/lang/Object;
      // 00f5: checkcast ai/comma/cereal/Car$RadarData$RadarPoint$Reader
      // 00f8: astore 34
      // 00fa: aload 35
      // 00fc: iload 18
      // 00fe: bipush 2
      // 00ff: imul
      // 0100: aload 34
      // 0102: invokevirtual ai/comma/cereal/Car$RadarData$RadarPoint$Reader.getDRel ()F
      // 0105: fastore
      // 0106: aload 35
      // 0108: iload 18
      // 010a: bipush 2
      // 010b: imul
      // 010c: bipush 1
      // 010d: iadd
      // 010e: aload 34
      // 0110: invokevirtual ai/comma/cereal/Car$RadarData$RadarPoint$Reader.getYRel ()F
      // 0113: fastore
      // 0114: aload 36
      // 0116: new com/starpilot/starview/RadarTrack
      // 0119: dup
      // 011a: aload 34
      // 011c: invokevirtual ai/comma/cereal/Car$RadarData$RadarPoint$Reader.getTrackId ()J
      // 011f: aload 34
      // 0121: invokevirtual ai/comma/cereal/Car$RadarData$RadarPoint$Reader.getDRel ()F
      // 0124: aload 34
      // 0126: invokevirtual ai/comma/cereal/Car$RadarData$RadarPoint$Reader.getYRel ()F
      // 0129: aload 34
      // 012b: invokevirtual ai/comma/cereal/Car$RadarData$RadarPoint$Reader.getVRel ()F
      // 012e: aload 34
      // 0130: invokevirtual ai/comma/cereal/Car$RadarData$RadarPoint$Reader.getMeasured ()Z
      // 0133: invokespecial com/starpilot/starview/RadarTrack.<init> (JFFFZ)V
      // 0136: invokevirtual java/util/ArrayList.add (Ljava/lang/Object;)Z
      // 0139: pop
      // 013a: iinc 18 1
      // 013d: goto 00e8
      // 0140: aload 0
      // 0141: aload 35
      // 0143: putfield com/starpilot/starview/Telemetry.tracks [F
      // 0146: aload 0
      // 0147: aload 36
      // 0149: checkcast java/util/Collection
      // 014c: bipush 0
      // 014d: anewarray 747
      // 0150: invokeinterface java/util/Collection.toArray ([Ljava/lang/Object;)[Ljava/lang/Object; 2
      // 0155: checkcast [Lcom/starpilot/starview/RadarTrack;
      // 0158: putfield com/starpilot/starview/Telemetry.radar [Lcom/starpilot/starview/RadarTrack;
      // 015b: aload 0
      // 015c: getfield com/starpilot/starview/Telemetry.radarFrame J
      // 015f: lstore 32
      // 0161: aload 0
      // 0162: lconst_1
      // 0163: lload 32
      // 0165: ladd
      // 0166: putfield com/starpilot/starview/Telemetry.radarFrame J
      // 0169: goto 16e1
      // 016c: aload 1
      // 016d: invokevirtual ai/comma/cereal/Log$Event$Reader.getUbloxGnss ()Lai/comma/cereal/Log$UbloxGnss$Reader;
      // 0170: astore 1
      // 0171: aload 1
      // 0172: invokevirtual ai/comma/cereal/Log$UbloxGnss$Reader.which ()Lai/comma/cereal/Log$UbloxGnss$Which;
      // 0175: getstatic ai/comma/cereal/Log$UbloxGnss$Which.SAT_REPORT Lai/comma/cereal/Log$UbloxGnss$Which;
      // 0178: if_acmpne 01fd
      // 017b: aload 1
      // 017c: invokevirtual ai/comma/cereal/Log$UbloxGnss$Reader.getSatReport ()Lai/comma/cereal/Log$UbloxGnss$SatReport$Reader;
      // 017f: invokevirtual ai/comma/cereal/Log$UbloxGnss$SatReport$Reader.getSvs ()Lorg/capnproto/StructList$Reader;
      // 0182: astore 35
      // 0184: aload 35
      // 0186: invokevirtual org/capnproto/StructList$Reader.size ()I
      // 0189: istore 19
      // 018b: new java/util/ArrayList
      // 018e: dup
      // 018f: iload 19
      // 0191: invokespecial java/util/ArrayList.<init> (I)V
      // 0194: astore 34
      // 0196: iload 22
      // 0198: istore 18
      // 019a: iload 18
      // 019c: iload 19
      // 019e: if_icmpge 01ea
      // 01a1: aload 35
      // 01a3: iload 18
      // 01a5: invokevirtual org/capnproto/StructList$Reader.get (I)Ljava/lang/Object;
      // 01a8: checkcast ai/comma/cereal/Log$UbloxGnss$SatReport$SatInfo$Reader
      // 01ab: astore 36
      // 01ad: aload 34
      // 01af: new com/starpilot/starview/GnssSat
      // 01b2: dup
      // 01b3: aload 36
      // 01b5: invokevirtual ai/comma/cereal/Log$UbloxGnss$SatReport$SatInfo$Reader.getGnssId ()B
      // 01b8: sipush 255
      // 01bb: iand
      // 01bc: aload 36
      // 01be: invokevirtual ai/comma/cereal/Log$UbloxGnss$SatReport$SatInfo$Reader.getSvId ()B
      // 01c1: sipush 255
      // 01c4: iand
      // 01c5: aload 36
      // 01c7: invokevirtual ai/comma/cereal/Log$UbloxGnss$SatReport$SatInfo$Reader.getCno ()B
      // 01ca: sipush 255
      // 01cd: iand
      // 01ce: aload 36
      // 01d0: invokevirtual ai/comma/cereal/Log$UbloxGnss$SatReport$SatInfo$Reader.getElevationDeg ()B
      // 01d3: aload 36
      // 01d5: invokevirtual ai/comma/cereal/Log$UbloxGnss$SatReport$SatInfo$Reader.getAzimuthDeg ()S
      // 01d8: aload 36
      // 01da: invokevirtual ai/comma/cereal/Log$UbloxGnss$SatReport$SatInfo$Reader.getFlagsBitfield ()I
      // 01dd: invokespecial com/starpilot/starview/GnssSat.<init> (IIIIII)V
      // 01e0: invokevirtual java/util/ArrayList.add (Ljava/lang/Object;)Z
      // 01e3: pop
      // 01e4: iinc 18 1
      // 01e7: goto 019a
      // 01ea: aload 0
      // 01eb: aload 34
      // 01ed: checkcast java/util/List
      // 01f0: putfield com/starpilot/starview/Telemetry.gnssSats Ljava/util/List;
      // 01f3: aload 0
      // 01f4: invokestatic java/lang/System.nanoTime ()J
      // 01f7: putfield com/starpilot/starview/Telemetry.gnssAtNs J
      // 01fa: goto 01fd
      // 01fd: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 0200: astore 1
      // 0201: goto 16e1
      // 0204: aload 1
      // 0205: invokevirtual ai/comma/cereal/Log$Event$Reader.which ()Lai/comma/cereal/Log$Event$Which;
      // 0208: getstatic ai/comma/cereal/Log$Event$Which.GPS_LOCATION Lai/comma/cereal/Log$Event$Which;
      // 020b: if_acmpne 0217
      // 020e: aload 1
      // 020f: invokevirtual ai/comma/cereal/Log$Event$Reader.getGpsLocation ()Lai/comma/cereal/Log$GpsLocationData$Reader;
      // 0212: astore 34
      // 0214: goto 021d
      // 0217: aload 1
      // 0218: invokevirtual ai/comma/cereal/Log$Event$Reader.getGpsLocationExternal ()Lai/comma/cereal/Log$GpsLocationData$Reader;
      // 021b: astore 34
      // 021d: aload 1
      // 021e: invokevirtual ai/comma/cereal/Log$Event$Reader.which ()Lai/comma/cereal/Log$Event$Which;
      // 0221: getstatic ai/comma/cereal/Log$Event$Which.GPS_LOCATION_EXTERNAL Lai/comma/cereal/Log$Event$Which;
      // 0224: if_acmpne 022d
      // 0227: bipush 1
      // 0228: istore 18
      // 022a: goto 0230
      // 022d: bipush 0
      // 022e: istore 18
      // 0230: invokestatic java/lang/System.currentTimeMillis ()J
      // 0233: lstore 32
      // 0235: aload 34
      // 0237: invokevirtual ai/comma/cereal/Log$GpsLocationData$Reader.getSource ()Lai/comma/cereal/Log$GpsLocationData$SensorSource;
      // 023a: invokevirtual ai/comma/cereal/Log$GpsLocationData$SensorSource.name ()Ljava/lang/String;
      // 023d: getstatic java/util/Locale.ROOT Ljava/util/Locale;
      // 0240: invokevirtual java/lang/String.toLowerCase (Ljava/util/Locale;)Ljava/lang/String;
      // 0243: astore 35
      // 0245: aload 35
      // 0247: ldc_w "toLowerCase(...)"
      // 024a: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 024d: goto 0257
      // 0250: astore 35
      // 0252: ldc_w "?"
      // 0255: astore 35
      // 0257: iload 18
      // 0259: ifeq 0264
      // 025c: ldc_w "gpsLocationExternal "
      // 025f: astore 36
      // 0261: goto 0269
      // 0264: ldc_w "gpsLocation "
      // 0267: astore 36
      // 0269: new java/lang/StringBuilder
      // 026c: dup
      // 026d: invokespecial java/lang/StringBuilder.<init> ()V
      // 0270: aload 36
      // 0272: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 0275: aload 35
      // 0277: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 027a: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 027d: astore 38
      // 027f: aload 0
      // 0280: getfield com/starpilot/starview/Telemetry.gpsFixes Ljava/util/concurrent/ConcurrentHashMap;
      // 0283: checkcast java/util/Map
      // 0286: astore 37
      // 0288: iload 18
      // 028a: ifeq 0295
      // 028d: ldc_w "gpsLocationExternal"
      // 0290: astore 36
      // 0292: goto 029a
      // 0295: ldc_w "gpsLocation"
      // 0298: astore 36
      // 029a: aload 37
      // 029c: aload 38
      // 029e: new com/starpilot/starview/GpsFixInfo
      // 02a1: dup
      // 02a2: aload 36
      // 02a4: aload 35
      // 02a6: aload 34
      // 02a8: invokevirtual ai/comma/cereal/Log$GpsLocationData$Reader.getHasFix ()Z
      // 02ab: aload 34
      // 02ad: invokevirtual ai/comma/cereal/Log$GpsLocationData$Reader.getSatelliteCount ()B
      // 02b0: aload 34
      // 02b2: invokevirtual ai/comma/cereal/Log$GpsLocationData$Reader.getLatitude ()D
      // 02b5: aload 34
      // 02b7: invokevirtual ai/comma/cereal/Log$GpsLocationData$Reader.getLongitude ()D
      // 02ba: aload 34
      // 02bc: invokevirtual ai/comma/cereal/Log$GpsLocationData$Reader.getHorizontalAccuracy ()F
      // 02bf: aload 34
      // 02c1: invokevirtual ai/comma/cereal/Log$GpsLocationData$Reader.getSpeed ()F
      // 02c4: aload 34
      // 02c6: invokevirtual ai/comma/cereal/Log$GpsLocationData$Reader.getBearingDeg ()F
      // 02c9: aload 34
      // 02cb: invokevirtual ai/comma/cereal/Log$GpsLocationData$Reader.getBearingAccuracyDeg ()F
      // 02ce: invokestatic java/lang/System.nanoTime ()J
      // 02d1: aload 34
      // 02d3: invokevirtual ai/comma/cereal/Log$GpsLocationData$Reader.getAltitude ()D
      // 02d6: aload 34
      // 02d8: invokevirtual ai/comma/cereal/Log$GpsLocationData$Reader.getVerticalAccuracy ()F
      // 02db: aload 34
      // 02dd: invokevirtual ai/comma/cereal/Log$GpsLocationData$Reader.getUnixTimestampMillis ()J
      // 02e0: invokespecial com/starpilot/starview/GpsFixInfo.<init> (Ljava/lang/String;Ljava/lang/String;ZIDDFFFFJDFJ)V
      // 02e3: invokeinterface java/util/Map.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
      // 02e8: pop
      // 02e9: aload 0
      // 02ea: aload 38
      // 02ec: invokevirtual com/starpilot/starview/Telemetry.tick (Ljava/lang/String;)V
      // 02ef: iload 18
      // 02f1: ifeq 03a4
      // 02f4: aload 0
      // 02f5: getfield com/starpilot/starview/Telemetry.turnGpsSrc Ljava/lang/String;
      // 02f8: ifnull 0307
      // 02fb: aload 0
      // 02fc: getfield com/starpilot/starview/Telemetry.turnGpsSrc Ljava/lang/String;
      // 02ff: aload 35
      // 0301: invokestatic kotlin/jvm/internal/Intrinsics.areEqual (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 0304: ifeq 03a4
      // 0307: aload 34
      // 0309: invokevirtual ai/comma/cereal/Log$GpsLocationData$Reader.getHasFix ()Z
      // 030c: ifeq 039d
      // 030f: aload 34
      // 0311: invokevirtual ai/comma/cereal/Log$GpsLocationData$Reader.getSpeed ()F
      // 0314: aload 0
      // 0315: getfield com/starpilot/starview/Telemetry.TURN_MIN_V F
      // 0318: fcmpl
      // 0319: ifle 039d
      // 031c: aload 0
      // 031d: getfield com/starpilot/starview/Telemetry.vEgo F
      // 0320: aload 0
      // 0321: getfield com/starpilot/starview/Telemetry.TURN_MIN_V F
      // 0324: fcmpl
      // 0325: ifle 039d
      // 0328: aload 0
      // 0329: getfield com/starpilot/starview/Telemetry.gear Ljava/lang/String;
      // 032c: ldc_w "reverse"
      // 032f: invokestatic kotlin/jvm/internal/Intrinsics.areEqual (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 0332: ifne 039d
      // 0335: aload 34
      // 0337: invokevirtual ai/comma/cereal/Log$GpsLocationData$Reader.getBearingDeg ()F
      // 033a: fstore 8
      // 033c: fload 8
      // 033e: invokestatic java/lang/Float.isInfinite (F)Z
      // 0341: ifne 0353
      // 0344: fload 8
      // 0346: invokestatic java/lang/Float.isNaN (F)Z
      // 0349: ifne 0353
      // 034c: iload 20
      // 034e: istore 19
      // 0350: goto 0356
      // 0353: bipush 0
      // 0354: istore 19
      // 0356: iload 19
      // 0358: ifeq 039d
      // 035b: aload 0
      // 035c: aload 35
      // 035e: putfield com/starpilot/starview/Telemetry.turnGpsSrc Ljava/lang/String;
      // 0361: aload 0
      // 0362: getfield com/starpilot/starview/Telemetry.turnLastBearing F
      // 0365: fstore 8
      // 0367: fload 8
      // 0369: invokestatic java/lang/Float.isNaN (F)Z
      // 036c: ifne 0391
      // 036f: aload 34
      // 0371: invokevirtual ai/comma/cereal/Log$GpsLocationData$Reader.getBearingDeg ()F
      // 0374: fstore 9
      // 0376: aload 0
      // 0377: aload 0
      // 0378: getfield com/starpilot/starview/Telemetry.turnGpsDeg D
      // 037b: fload 9
      // 037d: fload 8
      // 037f: fsub
      // 0380: ldc_w 540.0
      // 0383: fadd
      // 0384: ldc_w 360.0
      // 0387: frem
      // 0388: ldc_w 180.0
      // 038b: fsub
      // 038c: f2d
      // 038d: dsub
      // 038e: putfield com/starpilot/starview/Telemetry.turnGpsDeg D
      // 0391: aload 0
      // 0392: aload 34
      // 0394: invokevirtual ai/comma/cereal/Log$GpsLocationData$Reader.getBearingDeg ()F
      // 0397: putfield com/starpilot/starview/Telemetry.turnLastBearing F
      // 039a: goto 03a4
      // 039d: aload 0
      // 039e: ldc_w NaN
      // 03a1: putfield com/starpilot/starview/Telemetry.turnLastBearing F
      // 03a4: iload 18
      // 03a6: ifeq 03b7
      // 03a9: aload 34
      // 03ab: invokevirtual ai/comma/cereal/Log$GpsLocationData$Reader.getHasFix ()Z
      // 03ae: ifeq 03b7
      // 03b1: aload 0
      // 03b2: lload 32
      // 03b4: putfield com/starpilot/starview/Telemetry.lastExtFixMs J
      // 03b7: iload 18
      // 03b9: ifne 03ca
      // 03bc: lload 32
      // 03be: aload 0
      // 03bf: getfield com/starpilot/starview/Telemetry.lastExtFixMs J
      // 03c2: lsub
      // 03c3: ldc2_w 2000
      // 03c6: lcmp
      // 03c7: ifle 042e
      // 03ca: aload 0
      // 03cb: aload 34
      // 03cd: invokevirtual ai/comma/cereal/Log$GpsLocationData$Reader.getBearingDeg ()F
      // 03d0: putfield com/starpilot/starview/Telemetry.bearingDeg F
      // 03d3: aload 34
      // 03d5: invokevirtual ai/comma/cereal/Log$GpsLocationData$Reader.getHasFix ()Z
      // 03d8: ifeq 03ed
      // 03db: aload 0
      // 03dc: aload 34
      // 03de: invokevirtual ai/comma/cereal/Log$GpsLocationData$Reader.getLatitude ()D
      // 03e1: putfield com/starpilot/starview/Telemetry.gpsLat D
      // 03e4: aload 0
      // 03e5: aload 34
      // 03e7: invokevirtual ai/comma/cereal/Log$GpsLocationData$Reader.getLongitude ()D
      // 03ea: putfield com/starpilot/starview/Telemetry.gpsLon D
      // 03ed: aload 34
      // 03ef: invokevirtual ai/comma/cereal/Log$GpsLocationData$Reader.getHasFix ()Z
      // 03f2: ifeq 042e
      // 03f5: getstatic com/starpilot/starview/GpsBridge.INSTANCE Lcom/starpilot/starview/GpsBridge;
      // 03f8: aload 34
      // 03fa: invokevirtual ai/comma/cereal/Log$GpsLocationData$Reader.getLatitude ()D
      // 03fd: aload 34
      // 03ff: invokevirtual ai/comma/cereal/Log$GpsLocationData$Reader.getLongitude ()D
      // 0402: aload 34
      // 0404: invokevirtual ai/comma/cereal/Log$GpsLocationData$Reader.getAltitude ()D
      // 0407: aload 34
      // 0409: invokevirtual ai/comma/cereal/Log$GpsLocationData$Reader.getSpeed ()F
      // 040c: aload 34
      // 040e: invokevirtual ai/comma/cereal/Log$GpsLocationData$Reader.getBearingDeg ()F
      // 0411: aload 34
      // 0413: invokevirtual ai/comma/cereal/Log$GpsLocationData$Reader.getHorizontalAccuracy ()F
      // 0416: aload 34
      // 0418: invokevirtual ai/comma/cereal/Log$GpsLocationData$Reader.getVerticalAccuracy ()F
      // 041b: aload 34
      // 041d: invokevirtual ai/comma/cereal/Log$GpsLocationData$Reader.getBearingAccuracyDeg ()F
      // 0420: aload 34
      // 0422: invokevirtual ai/comma/cereal/Log$GpsLocationData$Reader.getSpeedAccuracy ()F
      // 0425: aload 35
      // 0427: aload 1
      // 0428: invokevirtual ai/comma/cereal/Log$Event$Reader.getLogMonoTime ()J
      // 042b: invokevirtual com/starpilot/starview/GpsBridge.onFix (DDDFFFFFFLjava/lang/String;J)V
      // 042e: aload 1
      // 042f: invokevirtual ai/comma/cereal/Log$Event$Reader.which ()Lai/comma/cereal/Log$Event$Which;
      // 0432: getstatic ai/comma/cereal/Log$Event$Which.GPS_LOCATION_EXTERNAL Lai/comma/cereal/Log$Event$Which;
      // 0435: if_acmpne 04ae
      // 0438: getstatic com/starpilot/starview/OsmRoads.INSTANCE Lcom/starpilot/starview/OsmRoads;
      // 043b: astore 36
      // 043d: aload 34
      // 043f: invokevirtual ai/comma/cereal/Log$GpsLocationData$Reader.getSource ()Lai/comma/cereal/Log$GpsLocationData$SensorSource;
      // 0442: invokevirtual ai/comma/cereal/Log$GpsLocationData$SensorSource.name ()Ljava/lang/String;
      // 0445: getstatic java/util/Locale.ROOT Ljava/util/Locale;
      // 0448: invokevirtual java/lang/String.toLowerCase (Ljava/util/Locale;)Ljava/lang/String;
      // 044b: astore 37
      // 044d: aload 37
      // 044f: ldc_w "toLowerCase(...)"
      // 0452: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 0455: aload 34
      // 0457: invokevirtual ai/comma/cereal/Log$GpsLocationData$Reader.getSatelliteCount ()B
      // 045a: istore 18
      // 045c: aload 34
      // 045e: invokevirtual ai/comma/cereal/Log$GpsLocationData$Reader.getHasFix ()Z
      // 0461: ifne 046c
      // 0464: ldc_w " NO FIX"
      // 0467: astore 35
      // 0469: goto 0471
      // 046c: ldc_w ""
      // 046f: astore 35
      // 0471: new java/lang/StringBuilder
      // 0474: astore 38
      // 0476: aload 38
      // 0478: invokespecial java/lang/StringBuilder.<init> ()V
      // 047b: aload 38
      // 047d: aload 37
      // 047f: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 0482: ldc_w " "
      // 0485: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 0488: iload 18
      // 048a: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
      // 048d: ldc_w " sats"
      // 0490: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 0493: aload 35
      // 0495: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 0498: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 049b: astore 35
      // 049d: goto 04a7
      // 04a0: astore 35
      // 04a2: ldc_w ""
      // 04a5: astore 35
      // 04a7: aload 36
      // 04a9: aload 35
      // 04ab: invokevirtual com/starpilot/starview/OsmRoads.setGpsSrc (Ljava/lang/String;)V
      // 04ae: aload 34
      // 04b0: invokevirtual ai/comma/cereal/Log$GpsLocationData$Reader.getHasFix ()Z
      // 04b3: ifeq 04f2
      // 04b6: aload 1
      // 04b7: invokevirtual ai/comma/cereal/Log$Event$Reader.which ()Lai/comma/cereal/Log$Event$Which;
      // 04ba: getstatic ai/comma/cereal/Log$Event$Which.GPS_LOCATION_EXTERNAL Lai/comma/cereal/Log$Event$Which;
      // 04bd: if_acmpne 04f2
      // 04c0: getstatic com/starpilot/starview/OsmRoads.INSTANCE Lcom/starpilot/starview/OsmRoads;
      // 04c3: aload 34
      // 04c5: invokevirtual ai/comma/cereal/Log$GpsLocationData$Reader.getLatitude ()D
      // 04c8: aload 34
      // 04ca: invokevirtual ai/comma/cereal/Log$GpsLocationData$Reader.getLongitude ()D
      // 04cd: aload 34
      // 04cf: invokevirtual ai/comma/cereal/Log$GpsLocationData$Reader.getBearingDeg ()F
      // 04d2: aload 34
      // 04d4: invokevirtual ai/comma/cereal/Log$GpsLocationData$Reader.getSpeed ()F
      // 04d7: aload 34
      // 04d9: invokevirtual ai/comma/cereal/Log$GpsLocationData$Reader.getHorizontalAccuracy ()F
      // 04dc: aload 34
      // 04de: invokevirtual ai/comma/cereal/Log$GpsLocationData$Reader.getBearingAccuracyDeg ()F
      // 04e1: aload 34
      // 04e3: invokevirtual ai/comma/cereal/Log$GpsLocationData$Reader.getUnixTimestampMillis ()J
      // 04e6: aload 1
      // 04e7: invokevirtual ai/comma/cereal/Log$Event$Reader.getLogMonoTime ()J
      // 04ea: bipush 0
      // 04eb: sipush 256
      // 04ee: aconst_null
      // 04ef: invokestatic com/starpilot/starview/OsmRoads.onFix$default (Lcom/starpilot/starview/OsmRoads;DDFFFFJJZILjava/lang/Object;)V
      // 04f2: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 04f5: astore 1
      // 04f6: goto 16e1
      // 04f9: aload 1
      // 04fa: invokevirtual ai/comma/cereal/Log$Event$Reader.getMapdOut ()Lai/comma/cereal/Custom$MapdOut$Reader;
      // 04fd: astore 1
      // 04fe: aload 0
      // 04ff: aload 1
      // 0500: invokevirtual ai/comma/cereal/Custom$MapdOut$Reader.getRoadName ()Lorg/capnproto/Text$Reader;
      // 0503: invokevirtual org/capnproto/Text$Reader.toString ()Ljava/lang/String;
      // 0506: putfield com/starpilot/starview/Telemetry.roadName Ljava/lang/String;
      // 0509: aload 0
      // 050a: aload 1
      // 050b: invokevirtual ai/comma/cereal/Custom$MapdOut$Reader.getWayName ()Lorg/capnproto/Text$Reader;
      // 050e: invokevirtual org/capnproto/Text$Reader.toString ()Ljava/lang/String;
      // 0511: putfield com/starpilot/starview/Telemetry.mapdWayName Ljava/lang/String;
      // 0514: aload 0
      // 0515: aload 1
      // 0516: invokevirtual ai/comma/cereal/Custom$MapdOut$Reader.getWayRef ()Lorg/capnproto/Text$Reader;
      // 0519: invokevirtual org/capnproto/Text$Reader.toString ()Ljava/lang/String;
      // 051c: putfield com/starpilot/starview/Telemetry.mapdWayRef Ljava/lang/String;
      // 051f: aload 0
      // 0520: aload 1
      // 0521: invokevirtual ai/comma/cereal/Custom$MapdOut$Reader.getLanes ()B
      // 0524: sipush 255
      // 0527: iand
      // 0528: putfield com/starpilot/starview/Telemetry.mapdLanes I
      // 052b: aload 0
      // 052c: aload 1
      // 052d: invokevirtual ai/comma/cereal/Custom$MapdOut$Reader.getOneWay ()Z
      // 0530: putfield com/starpilot/starview/Telemetry.mapdOneWay Z
      // 0533: aload 1
      // 0534: invokevirtual ai/comma/cereal/Custom$MapdOut$Reader.getRoadContext ()Lai/comma/cereal/Custom$RoadContext;
      // 0537: invokevirtual ai/comma/cereal/Custom$RoadContext.toString ()Ljava/lang/String;
      // 053a: ldc_w "FREEWAY"
      // 053d: invokestatic kotlin/jvm/internal/Intrinsics.areEqual (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 0540: istore 23
      // 0542: goto 0549
      // 0545: astore 1
      // 0546: bipush 0
      // 0547: istore 23
      // 0549: aload 0
      // 054a: iload 23
      // 054c: putfield com/starpilot/starview/Telemetry.mapdFreeway Z
      // 054f: aload 0
      // 0550: getfield com/starpilot/starview/Telemetry.mapdWayName Ljava/lang/String;
      // 0553: checkcast java/lang/CharSequence
      // 0556: invokeinterface java/lang/CharSequence.length ()I 1
      // 055b: ifle 0564
      // 055e: bipush 1
      // 055f: istore 18
      // 0561: goto 0567
      // 0564: bipush 0
      // 0565: istore 18
      // 0567: iload 24
      // 0569: istore 23
      // 056b: iload 18
      // 056d: ifne 0597
      // 0570: aload 0
      // 0571: getfield com/starpilot/starview/Telemetry.roadName Ljava/lang/String;
      // 0574: checkcast java/lang/CharSequence
      // 0577: invokeinterface java/lang/CharSequence.length ()I 1
      // 057c: ifle 0585
      // 057f: bipush 1
      // 0580: istore 18
      // 0582: goto 0588
      // 0585: bipush 0
      // 0586: istore 18
      // 0588: iload 18
      // 058a: ifeq 0594
      // 058d: iload 24
      // 058f: istore 23
      // 0591: goto 0597
      // 0594: bipush 0
      // 0595: istore 23
      // 0597: aload 0
      // 0598: iload 23
      // 059a: putfield com/starpilot/starview/Telemetry.mapdMatched Z
      // 059d: aload 0
      // 059e: invokestatic java/lang/System.nanoTime ()J
      // 05a1: putfield com/starpilot/starview/Telemetry.mapdAtNs J
      // 05a4: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 05a7: astore 1
      // 05a8: goto 16e1
      // 05ab: aload 1
      // 05ac: invokevirtual ai/comma/cereal/Log$Event$Reader.getGyroscope ()Lai/comma/cereal/Log$SensorEventData$Reader;
      // 05af: astore 34
      // 05b1: aload 0
      // 05b2: ldc_w "gyroscope"
      // 05b5: invokevirtual com/starpilot/starview/Telemetry.tick (Ljava/lang/String;)V
      // 05b8: aload 34
      // 05ba: invokevirtual ai/comma/cereal/Log$SensorEventData$Reader.which ()Lai/comma/cereal/Log$SensorEventData$Which;
      // 05bd: astore 1
      // 05be: aload 1
      // 05bf: ifnonnull 05c8
      // 05c2: bipush -1
      // 05c3: istore 18
      // 05c5: goto 05d2
      // 05c8: getstatic com/starpilot/starview/Telemetry$WhenMappings.$EnumSwitchMapping$0 [I
      // 05cb: aload 1
      // 05cc: invokevirtual ai/comma/cereal/Log$SensorEventData$Which.ordinal ()I
      // 05cf: iaload
      // 05d0: istore 18
      // 05d2: iload 18
      // 05d4: tableswitch 24 1 2 41 29
      // 05ec: aconst_null
      // 05ed: astore 1
      // 05ee: goto 0606
      // 05f1: aload 34
      // 05f3: invokevirtual ai/comma/cereal/Log$SensorEventData$Reader.getGyroUncalibrated ()Lai/comma/cereal/Log$SensorEventData$SensorVec$Reader;
      // 05f6: invokevirtual ai/comma/cereal/Log$SensorEventData$SensorVec$Reader.getV ()Lorg/capnproto/PrimitiveList$Float$Reader;
      // 05f9: astore 1
      // 05fa: goto 0606
      // 05fd: aload 34
      // 05ff: invokevirtual ai/comma/cereal/Log$SensorEventData$Reader.getGyro ()Lai/comma/cereal/Log$SensorEventData$SensorVec$Reader;
      // 0602: invokevirtual ai/comma/cereal/Log$SensorEventData$SensorVec$Reader.getV ()Lorg/capnproto/PrimitiveList$Float$Reader;
      // 0605: astore 1
      // 0606: aload 1
      // 0607: ifnull 062e
      // 060a: aload 0
      // 060b: aload 0
      // 060c: aload 1
      // 060d: invokespecial com/starpilot/starview/Telemetry.fl (Lorg/capnproto/PrimitiveList$Float$Reader;)[F
      // 0610: putfield com/starpilot/starview/Telemetry.gyroRaw [F
      // 0613: aload 34
      // 0615: invokevirtual ai/comma/cereal/Log$SensorEventData$Reader.getSource ()Lai/comma/cereal/Log$SensorEventData$SensorSource;
      // 0618: invokevirtual ai/comma/cereal/Log$SensorEventData$SensorSource.name ()Ljava/lang/String;
      // 061b: getstatic java/util/Locale.ROOT Ljava/util/Locale;
      // 061e: invokevirtual java/lang/String.toLowerCase (Ljava/util/Locale;)Ljava/lang/String;
      // 0621: astore 1
      // 0622: aload 1
      // 0623: ldc_w "toLowerCase(...)"
      // 0626: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 0629: aload 0
      // 062a: aload 1
      // 062b: putfield com/starpilot/starview/Telemetry.gyroSrc Ljava/lang/String;
      // 062e: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 0631: astore 1
      // 0632: goto 16e1
      // 0635: aload 1
      // 0636: invokevirtual ai/comma/cereal/Log$Event$Reader.getAccelerometer ()Lai/comma/cereal/Log$SensorEventData$Reader;
      // 0639: astore 1
      // 063a: aload 0
      // 063b: ldc_w "accelerometer"
      // 063e: invokevirtual com/starpilot/starview/Telemetry.tick (Ljava/lang/String;)V
      // 0641: aload 1
      // 0642: invokevirtual ai/comma/cereal/Log$SensorEventData$Reader.which ()Lai/comma/cereal/Log$SensorEventData$Which;
      // 0645: getstatic ai/comma/cereal/Log$SensorEventData$Which.ACCELERATION Lai/comma/cereal/Log$SensorEventData$Which;
      // 0648: if_acmpne 0680
      // 064b: aload 1
      // 064c: invokevirtual ai/comma/cereal/Log$SensorEventData$Reader.getAcceleration ()Lai/comma/cereal/Log$SensorEventData$SensorVec$Reader;
      // 064f: invokevirtual ai/comma/cereal/Log$SensorEventData$SensorVec$Reader.getV ()Lorg/capnproto/PrimitiveList$Float$Reader;
      // 0652: astore 34
      // 0654: aload 34
      // 0656: ldc_w "getV(...)"
      // 0659: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 065c: aload 0
      // 065d: aload 0
      // 065e: aload 34
      // 0660: invokespecial com/starpilot/starview/Telemetry.fl (Lorg/capnproto/PrimitiveList$Float$Reader;)[F
      // 0663: putfield com/starpilot/starview/Telemetry.accelRaw [F
      // 0666: aload 1
      // 0667: invokevirtual ai/comma/cereal/Log$SensorEventData$Reader.getSource ()Lai/comma/cereal/Log$SensorEventData$SensorSource;
      // 066a: invokevirtual ai/comma/cereal/Log$SensorEventData$SensorSource.name ()Ljava/lang/String;
      // 066d: getstatic java/util/Locale.ROOT Ljava/util/Locale;
      // 0670: invokevirtual java/lang/String.toLowerCase (Ljava/util/Locale;)Ljava/lang/String;
      // 0673: astore 1
      // 0674: aload 1
      // 0675: ldc_w "toLowerCase(...)"
      // 0678: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 067b: aload 0
      // 067c: aload 1
      // 067d: putfield com/starpilot/starview/Telemetry.accelSrc Ljava/lang/String;
      // 0680: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 0683: astore 1
      // 0684: goto 16e1
      // 0687: aload 1
      // 0688: invokevirtual ai/comma/cereal/Log$Event$Reader.getLivePose ()Lai/comma/cereal/Log$LivePose$Reader;
      // 068b: astore 34
      // 068d: aload 34
      // 068f: invokevirtual ai/comma/cereal/Log$LivePose$Reader.getAngularVelocityDevice ()Lai/comma/cereal/Log$LivePose$XYZMeasurement$Reader;
      // 0692: astore 36
      // 0694: aload 34
      // 0696: invokevirtual ai/comma/cereal/Log$LivePose$Reader.getSensorsOK ()Z
      // 0699: ifeq 06d9
      // 069c: aload 36
      // 069e: invokevirtual ai/comma/cereal/Log$LivePose$XYZMeasurement$Reader.getValid ()Z
      // 06a1: ifeq 06d9
      // 06a4: aload 36
      // 06a6: invokevirtual ai/comma/cereal/Log$LivePose$XYZMeasurement$Reader.getZ ()F
      // 06a9: fstore 8
      // 06ab: fload 8
      // 06ad: invokestatic java/lang/Float.isInfinite (F)Z
      // 06b0: ifne 06c1
      // 06b3: fload 8
      // 06b5: invokestatic java/lang/Float.isNaN (F)Z
      // 06b8: ifne 06c1
      // 06bb: bipush 1
      // 06bc: istore 18
      // 06be: goto 06c4
      // 06c1: bipush 0
      // 06c2: istore 18
      // 06c4: iload 18
      // 06c6: ifeq 06d9
      // 06c9: aload 0
      // 06ca: aload 36
      // 06cc: invokevirtual ai/comma/cereal/Log$LivePose$XYZMeasurement$Reader.getZ ()F
      // 06cf: putfield com/starpilot/starview/Telemetry.poseYawZ F
      // 06d2: aload 0
      // 06d3: invokestatic java/lang/System.nanoTime ()J
      // 06d6: putfield com/starpilot/starview/Telemetry.poseAtNs J
      // 06d9: aload 0
      // 06da: ldc_w "livePose"
      // 06dd: invokevirtual com/starpilot/starview/Telemetry.tick (Ljava/lang/String;)V
      // 06e0: aload 0
      // 06e1: aload 34
      // 06e3: invokevirtual ai/comma/cereal/Log$LivePose$Reader.getSensorsOK ()Z
      // 06e6: putfield com/starpilot/starview/Telemetry.poseOK Z
      // 06e9: aload 0
      // 06ea: aload 36
      // 06ec: invokevirtual ai/comma/cereal/Log$LivePose$XYZMeasurement$Reader.getValid ()Z
      // 06ef: putfield com/starpilot/starview/Telemetry.poseValid Z
      // 06f2: aload 0
      // 06f3: bipush 3
      // 06f4: newarray 6
      // 06f6: dup
      // 06f7: bipush 0
      // 06f8: aload 36
      // 06fa: invokevirtual ai/comma/cereal/Log$LivePose$XYZMeasurement$Reader.getX ()F
      // 06fd: fastore
      // 06fe: dup
      // 06ff: bipush 1
      // 0700: aload 36
      // 0702: invokevirtual ai/comma/cereal/Log$LivePose$XYZMeasurement$Reader.getY ()F
      // 0705: fastore
      // 0706: dup
      // 0707: bipush 2
      // 0708: aload 36
      // 070a: invokevirtual ai/comma/cereal/Log$LivePose$XYZMeasurement$Reader.getZ ()F
      // 070d: fastore
      // 070e: putfield com/starpilot/starview/Telemetry.poseGyro [F
      // 0711: aload 34
      // 0713: invokevirtual ai/comma/cereal/Log$LivePose$Reader.getAccelerationDevice ()Lai/comma/cereal/Log$LivePose$XYZMeasurement$Reader;
      // 0716: astore 35
      // 0718: aload 0
      // 0719: bipush 3
      // 071a: newarray 6
      // 071c: dup
      // 071d: bipush 0
      // 071e: aload 35
      // 0720: invokevirtual ai/comma/cereal/Log$LivePose$XYZMeasurement$Reader.getX ()F
      // 0723: fastore
      // 0724: dup
      // 0725: bipush 1
      // 0726: aload 35
      // 0728: invokevirtual ai/comma/cereal/Log$LivePose$XYZMeasurement$Reader.getY ()F
      // 072b: fastore
      // 072c: dup
      // 072d: bipush 2
      // 072e: aload 35
      // 0730: invokevirtual ai/comma/cereal/Log$LivePose$XYZMeasurement$Reader.getZ ()F
      // 0733: fastore
      // 0734: putfield com/starpilot/starview/Telemetry.poseAccel [F
      // 0737: aload 0
      // 0738: getfield com/starpilot/starview/Telemetry.turnPoseMono J
      // 073b: lconst_0
      // 073c: lcmp
      // 073d: ifne 0746
      // 0740: dload 4
      // 0742: dstore 2
      // 0743: goto 0775
      // 0746: aload 1
      // 0747: invokevirtual ai/comma/cereal/Log$Event$Reader.getLogMonoTime ()J
      // 074a: aload 0
      // 074b: getfield com/starpilot/starview/Telemetry.turnPoseMono J
      // 074e: lsub
      // 074f: l2d
      // 0750: ldc2_w 1.0E9
      // 0753: ddiv
      // 0754: dstore 6
      // 0756: dload 4
      // 0758: dstore 2
      // 0759: dload 6
      // 075b: dconst_0
      // 075c: dcmpg
      // 075d: iflt 0743
      // 0760: dload 6
      // 0762: ldc2_w 0.5
      // 0765: dcmpl
      // 0766: ifle 076f
      // 0769: dload 4
      // 076b: dstore 2
      // 076c: goto 0743
      // 076f: dload 6
      // 0771: dstore 2
      // 0772: goto 0743
      // 0775: aload 0
      // 0776: aload 1
      // 0777: invokevirtual ai/comma/cereal/Log$Event$Reader.getLogMonoTime ()J
      // 077a: putfield com/starpilot/starview/Telemetry.turnPoseMono J
      // 077d: aload 34
      // 077f: invokevirtual ai/comma/cereal/Log$LivePose$Reader.getSensorsOK ()Z
      // 0782: ifeq 07e8
      // 0785: aload 36
      // 0787: invokevirtual ai/comma/cereal/Log$LivePose$XYZMeasurement$Reader.getValid ()Z
      // 078a: ifeq 07e8
      // 078d: aload 36
      // 078f: invokevirtual ai/comma/cereal/Log$LivePose$XYZMeasurement$Reader.getZ ()F
      // 0792: fstore 8
      // 0794: fload 8
      // 0796: invokestatic java/lang/Float.isInfinite (F)Z
      // 0799: ifne 07ab
      // 079c: fload 8
      // 079e: invokestatic java/lang/Float.isNaN (F)Z
      // 07a1: ifne 07ab
      // 07a4: iload 19
      // 07a6: istore 18
      // 07a8: goto 07ae
      // 07ab: bipush 0
      // 07ac: istore 18
      // 07ae: iload 18
      // 07b0: ifeq 07e8
      // 07b3: aload 0
      // 07b4: getfield com/starpilot/starview/Telemetry.vEgo F
      // 07b7: aload 0
      // 07b8: getfield com/starpilot/starview/Telemetry.TURN_MIN_V F
      // 07bb: fcmpl
      // 07bc: ifle 07e8
      // 07bf: aload 0
      // 07c0: getfield com/starpilot/starview/Telemetry.gear Ljava/lang/String;
      // 07c3: ldc_w "reverse"
      // 07c6: invokestatic kotlin/jvm/internal/Intrinsics.areEqual (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 07c9: ifne 07e8
      // 07cc: aload 0
      // 07cd: aload 0
      // 07ce: getfield com/starpilot/starview/Telemetry.turnGyroDeg D
      // 07d1: getstatic com/starpilot/starview/Odometry.INSTANCE Lcom/starpilot/starview/Odometry;
      // 07d4: invokevirtual com/starpilot/starview/Odometry.getGyroSign ()I
      // 07d7: i2f
      // 07d8: aload 36
      // 07da: invokevirtual ai/comma/cereal/Log$LivePose$XYZMeasurement$Reader.getZ ()F
      // 07dd: fmul
      // 07de: f2d
      // 07df: dload 2
      // 07e0: dmul
      // 07e1: invokestatic java/lang/Math.toDegrees (D)D
      // 07e4: dadd
      // 07e5: putfield com/starpilot/starview/Telemetry.turnGyroDeg D
      // 07e8: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 07eb: astore 1
      // 07ec: goto 16e1
      // 07ef: aload 1
      // 07f0: invokevirtual ai/comma/cereal/Log$Event$Reader.getStarpilotCarState ()Lai/comma/cereal/Custom$StarPilotCarState$Reader;
      // 07f3: astore 1
      // 07f4: aload 0
      // 07f5: aload 1
      // 07f6: invokevirtual ai/comma/cereal/Custom$StarPilotCarState$Reader.getPauseLateral ()Z
      // 07f9: putfield com/starpilot/starview/Telemetry.pauseLateral Z
      // 07fc: aload 0
      // 07fd: aload 1
      // 07fe: invokevirtual ai/comma/cereal/Custom$StarPilotCarState$Reader.getPauseLongitudinal ()Z
      // 0801: putfield com/starpilot/starview/Telemetry.pauseLongitudinal Z
      // 0804: aload 0
      // 0805: aload 1
      // 0806: invokevirtual ai/comma/cereal/Custom$StarPilotCarState$Reader.getForceCoast ()Z
      // 0809: putfield com/starpilot/starview/Telemetry.forceCoast Z
      // 080c: aload 0
      // 080d: aload 1
      // 080e: invokevirtual ai/comma/cereal/Custom$StarPilotCarState$Reader.getPulseAndGlide ()Z
      // 0811: putfield com/starpilot/starview/Telemetry.pulseAndGlide Z
      // 0814: aload 0
      // 0815: aload 1
      // 0816: invokevirtual ai/comma/cereal/Custom$StarPilotCarState$Reader.getBrakeLights ()Z
      // 0819: putfield com/starpilot/starview/Telemetry.brakeLights Z
      // 081c: aload 0
      // 081d: aload 1
      // 081e: invokevirtual ai/comma/cereal/Custom$StarPilotCarState$Reader.getDashboardSpeedLimit ()F
      // 0821: putfield com/starpilot/starview/Telemetry.dashSpeedLimit F
      // 0824: aload 0
      // 0825: aload 1
      // 0826: invokevirtual ai/comma/cereal/Custom$StarPilotCarState$Reader.getTrafficModeEnabled ()Z
      // 0829: putfield com/starpilot/starview/Telemetry.trafficMode Z
      // 082c: aload 0
      // 082d: aload 1
      // 082e: invokevirtual ai/comma/cereal/Custom$StarPilotCarState$Reader.getAlwaysOnLateralEnabled ()Z
      // 0831: putfield com/starpilot/starview/Telemetry.aolEnabled Z
      // 0834: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 0837: astore 1
      // 0838: goto 16e1
      // 083b: aload 1
      // 083c: invokevirtual ai/comma/cereal/Log$Event$Reader.getCarParams ()Lai/comma/cereal/Car$CarParams$Reader;
      // 083f: astore 1
      // 0840: aload 0
      // 0841: aload 1
      // 0842: invokevirtual ai/comma/cereal/Car$CarParams$Reader.getSteerActuatorDelay ()F
      // 0845: putfield com/starpilot/starview/Telemetry.baseDelay F
      // 0848: aload 1
      // 0849: invokevirtual ai/comma/cereal/Car$CarParams$Reader.getLateralTuning ()Lai/comma/cereal/Car$CarParams$LateralTuning$Reader;
      // 084c: astore 1
      // 084d: aload 1
      // 084e: invokevirtual ai/comma/cereal/Car$CarParams$LateralTuning$Reader.which ()Lai/comma/cereal/Car$CarParams$LateralTuning$Which;
      // 0851: getstatic ai/comma/cereal/Car$CarParams$LateralTuning$Which.TORQUE Lai/comma/cereal/Car$CarParams$LateralTuning$Which;
      // 0854: if_acmpne 0871
      // 0857: aload 0
      // 0858: aload 1
      // 0859: invokevirtual ai/comma/cereal/Car$CarParams$LateralTuning$Reader.getTorque ()Lai/comma/cereal/Car$CarParams$LateralTorqueTuning$Reader;
      // 085c: invokevirtual ai/comma/cereal/Car$CarParams$LateralTorqueTuning$Reader.getFriction ()F
      // 085f: putfield com/starpilot/starview/Telemetry.baseFriction F
      // 0862: aload 0
      // 0863: aload 1
      // 0864: invokevirtual ai/comma/cereal/Car$CarParams$LateralTuning$Reader.getTorque ()Lai/comma/cereal/Car$CarParams$LateralTorqueTuning$Reader;
      // 0867: invokevirtual ai/comma/cereal/Car$CarParams$LateralTorqueTuning$Reader.getLatAccelFactor ()F
      // 086a: putfield com/starpilot/starview/Telemetry.baseLatAccel F
      // 086d: goto 0871
      // 0870: astore 1
      // 0871: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 0874: astore 1
      // 0875: goto 16e1
      // 0878: aload 1
      // 0879: invokevirtual ai/comma/cereal/Log$Event$Reader.getLiveTorqueParameters ()Lai/comma/cereal/Log$LiveTorqueParametersData$Reader;
      // 087c: astore 1
      // 087d: aload 0
      // 087e: aload 1
      // 087f: invokevirtual ai/comma/cereal/Log$LiveTorqueParametersData$Reader.getUseParams ()Z
      // 0882: putfield com/starpilot/starview/Telemetry.torqueUseParams Z
      // 0885: aload 0
      // 0886: aload 1
      // 0887: invokevirtual ai/comma/cereal/Log$LiveTorqueParametersData$Reader.getFrictionCoefficientFiltered ()F
      // 088a: putfield com/starpilot/starview/Telemetry.frictionLearned F
      // 088d: aload 0
      // 088e: aload 1
      // 088f: invokevirtual ai/comma/cereal/Log$LiveTorqueParametersData$Reader.getLatAccelFactorFiltered ()F
      // 0892: putfield com/starpilot/starview/Telemetry.latAccelLearned F
      // 0895: aload 0
      // 0896: invokestatic java/lang/System.nanoTime ()J
      // 0899: putfield com/starpilot/starview/Telemetry.torqueAtNs J
      // 089c: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 089f: astore 1
      // 08a0: goto 16e1
      // 08a3: aload 0
      // 08a4: aload 1
      // 08a5: invokevirtual ai/comma/cereal/Log$Event$Reader.getLiveDelay ()Lai/comma/cereal/Log$LiveDelayData$Reader;
      // 08a8: invokevirtual ai/comma/cereal/Log$LiveDelayData$Reader.getLateralDelay ()F
      // 08ab: putfield com/starpilot/starview/Telemetry.learnedDelay F
      // 08ae: aload 0
      // 08af: invokestatic java/lang/System.nanoTime ()J
      // 08b2: putfield com/starpilot/starview/Telemetry.delayAtNs J
      // 08b5: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 08b8: astore 1
      // 08b9: goto 16e1
      // 08bc: aload 1
      // 08bd: invokevirtual ai/comma/cereal/Log$Event$Reader.getStarpilotLateralState ()Lai/comma/cereal/Custom$StarPilotLateralState$Reader;
      // 08c0: checkcast org/capnproto/StructReader
      // 08c3: invokestatic org/capnproto/SlcPeek.laneCentering (Lorg/capnproto/StructReader;)Lorg/capnproto/SlcPeek$LaneCentering;
      // 08c6: astore 1
      // 08c7: aload 1
      // 08c8: ifnull 16e1
      // 08cb: aload 0
      // 08cc: aload 1
      // 08cd: putfield com/starpilot/starview/Telemetry.lc Lorg/capnproto/SlcPeek$LaneCentering;
      // 08d0: aload 0
      // 08d1: invokestatic java/lang/System.nanoTime ()J
      // 08d4: putfield com/starpilot/starview/Telemetry.lcAtNs J
      // 08d7: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 08da: astore 1
      // 08db: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 08de: astore 1
      // 08df: goto 16e1
      // 08e2: astore 1
      // 08e3: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 08e6: astore 1
      // 08e7: goto 16e1
      // 08ea: aload 1
      // 08eb: invokevirtual ai/comma/cereal/Log$Event$Reader.getStarpilotPlan ()Lai/comma/cereal/Custom$StarPilotPlan$Reader;
      // 08ee: astore 1
      // 08ef: aload 0
      // 08f0: aload 1
      // 08f1: invokevirtual ai/comma/cereal/Custom$StarPilotPlan$Reader.getSlcSpeedLimit ()F
      // 08f4: putfield com/starpilot/starview/Telemetry.slcSpeedLimit F
      // 08f7: aload 0
      // 08f8: aload 1
      // 08f9: invokevirtual ai/comma/cereal/Custom$StarPilotPlan$Reader.getSlcSpeedLimitOffset ()F
      // 08fc: putfield com/starpilot/starview/Telemetry.slcOffset F
      // 08ff: aload 0
      // 0900: aload 1
      // 0901: invokevirtual ai/comma/cereal/Custom$StarPilotPlan$Reader.getSlcSpeedLimitSource ()Lorg/capnproto/Text$Reader;
      // 0904: invokevirtual org/capnproto/Text$Reader.toString ()Ljava/lang/String;
      // 0907: putfield com/starpilot/starview/Telemetry.slcSource Ljava/lang/String;
      // 090a: aload 0
      // 090b: aload 1
      // 090c: invokevirtual ai/comma/cereal/Custom$StarPilotPlan$Reader.getSlcMapboxSpeedLimit ()F
      // 090f: putfield com/starpilot/starview/Telemetry.slcMapbox F
      // 0912: aload 0
      // 0913: aload 1
      // 0914: invokevirtual ai/comma/cereal/Custom$StarPilotPlan$Reader.getSlcMapSpeedLimit ()F
      // 0917: putfield com/starpilot/starview/Telemetry.slcMapSpeedLimit F
      // 091a: aload 0
      // 091b: aload 1
      // 091c: invokevirtual ai/comma/cereal/Custom$StarPilotPlan$Reader.getUnconfirmedSlcSpeedLimit ()F
      // 091f: putfield com/starpilot/starview/Telemetry.slcUnconfirmed F
      // 0922: aload 0
      // 0923: aload 1
      // 0924: invokevirtual ai/comma/cereal/Custom$StarPilotPlan$Reader.getSpeedLimitChanged ()Z
      // 0927: putfield com/starpilot/starview/Telemetry.slcChanged Z
      // 092a: aload 0
      // 092b: aload 1
      // 092c: invokevirtual ai/comma/cereal/Custom$StarPilotPlan$Reader.getSlcOverriddenSpeed ()F
      // 092f: putfield com/starpilot/starview/Telemetry.slcOverridden F
      // 0932: aload 0
      // 0933: aload 1
      // 0934: invokevirtual ai/comma/cereal/Custom$StarPilotPlan$Reader.getSlcNextSpeedLimit ()F
      // 0937: putfield com/starpilot/starview/Telemetry.slcNext F
      // 093a: aload 0
      // 093b: aload 1
      // 093c: invokevirtual ai/comma/cereal/Custom$StarPilotPlan$Reader.getForcingStop ()Z
      // 093f: putfield com/starpilot/starview/Telemetry.forcingStop Z
      // 0942: aload 0
      // 0943: aload 1
      // 0944: invokevirtual ai/comma/cereal/Custom$StarPilotPlan$Reader.getForcingStopLength ()F
      // 0947: putfield com/starpilot/starview/Telemetry.forcingStopLength F
      // 094a: aload 0
      // 094b: aload 1
      // 094c: invokevirtual ai/comma/cereal/Custom$StarPilotPlan$Reader.getRedLight ()Z
      // 094f: putfield com/starpilot/starview/Telemetry.redLight Z
      // 0952: aload 0
      // 0953: aload 1
      // 0954: invokevirtual ai/comma/cereal/Custom$StarPilotPlan$Reader.getApproachStopLength ()F
      // 0957: putfield com/starpilot/starview/Telemetry.approachStopLength F
      // 095a: aload 0
      // 095b: aload 1
      // 095c: invokevirtual ai/comma/cereal/Custom$StarPilotPlan$Reader.getStopSignConfirmed ()Z
      // 095f: putfield com/starpilot/starview/Telemetry.stopSignConfirmed Z
      // 0962: aload 0
      // 0963: aload 1
      // 0964: invokevirtual ai/comma/cereal/Custom$StarPilotPlan$Reader.getCscSpeed ()F
      // 0967: putfield com/starpilot/starview/Telemetry.cscSpeed F
      // 096a: aload 0
      // 096b: aload 1
      // 096c: invokevirtual ai/comma/cereal/Custom$StarPilotPlan$Reader.getCscTraining ()Z
      // 096f: putfield com/starpilot/starview/Telemetry.cscTraining Z
      // 0972: aload 0
      // 0973: aload 1
      // 0974: invokevirtual ai/comma/cereal/Custom$StarPilotPlan$Reader.getCscControllingSpeed ()Z
      // 0977: putfield com/starpilot/starview/Telemetry.cscControlling Z
      // 097a: aload 0
      // 097b: aload 1
      // 097c: invokevirtual ai/comma/cereal/Custom$StarPilotPlan$Reader.getRoadCurvature ()F
      // 097f: putfield com/starpilot/starview/Telemetry.roadCurvature F
      // 0982: aload 0
      // 0983: aload 1
      // 0984: invokevirtual ai/comma/cereal/Custom$StarPilotPlan$Reader.getVCruise ()F
      // 0987: putfield com/starpilot/starview/Telemetry.planVCruise F
      // 098a: aload 0
      // 098b: aload 1
      // 098c: invokevirtual ai/comma/cereal/Custom$StarPilotPlan$Reader.getTrackingLead ()Z
      // 098f: putfield com/starpilot/starview/Telemetry.trackingLead Z
      // 0992: aload 0
      // 0993: aload 1
      // 0994: invokevirtual ai/comma/cereal/Custom$StarPilotPlan$Reader.getLaneWidthLeft ()F
      // 0997: putfield com/starpilot/starview/Telemetry.laneWidthLeft F
      // 099a: aload 0
      // 099b: aload 1
      // 099c: invokevirtual ai/comma/cereal/Custom$StarPilotPlan$Reader.getLaneWidthRight ()F
      // 099f: putfield com/starpilot/starview/Telemetry.laneWidthRight F
      // 09a2: aload 0
      // 09a3: aload 1
      // 09a4: invokevirtual ai/comma/cereal/Custom$StarPilotPlan$Reader.getDesiredFollowDistance ()J
      // 09a7: putfield com/starpilot/starview/Telemetry.desiredFollowDistance J
      // 09aa: aload 0
      // 09ab: aload 1
      // 09ac: invokevirtual ai/comma/cereal/Custom$StarPilotPlan$Reader.getPulseGlideCoasting ()Z
      // 09af: putfield com/starpilot/starview/Telemetry.pulseGlideCoasting Z
      // 09b2: aload 0
      // 09b3: invokevirtual com/starpilot/starview/Telemetry.getSp7 ()Z
      // 09b6: ifeq 0ab1
      // 09b9: aload 0
      // 09ba: getfield com/starpilot/starview/Telemetry.vCruise F
      // 09bd: fstore 8
      // 09bf: fconst_1
      // 09c0: fload 8
      // 09c2: fcmpg
      // 09c3: ifgt 09d5
      // 09c6: fload 8
      // 09c8: ldc_w 250.0
      // 09cb: fcmpg
      // 09cc: ifgt 09d5
      // 09cf: bipush 1
      // 09d0: istore 18
      // 09d2: goto 09d8
      // 09d5: bipush 0
      // 09d6: istore 18
      // 09d8: iload 18
      // 09da: ifeq 09ea
      // 09dd: aload 0
      // 09de: getfield com/starpilot/starview/Telemetry.vCruise F
      // 09e1: ldc_w 3.6
      // 09e4: fdiv
      // 09e5: fstore 8
      // 09e7: goto 09f0
      // 09ea: aload 0
      // 09eb: getfield com/starpilot/starview/Telemetry.cruiseSpeed F
      // 09ee: fstore 8
      // 09f0: aload 0
      // 09f1: getfield com/starpilot/starview/Telemetry.cruiseEnabled Z
      // 09f4: ifeq 0a05
      // 09f7: fload 8
      // 09f9: fconst_0
      // 09fa: fcmpl
      // 09fb: ifle 0a05
      // 09fe: fload 8
      // 0a00: fstore 9
      // 0a02: goto 0a08
      // 0a05: fconst_0
      // 0a06: fstore 9
      // 0a08: fload 9
      // 0a0a: fstore 8
      // 0a0c: fload 9
      // 0a0e: fconst_0
      // 0a0f: fcmpl
      // 0a10: ifle 0a30
      // 0a13: fload 9
      // 0a15: fstore 8
      // 0a17: aload 0
      // 0a18: getfield com/starpilot/starview/Telemetry.slcSpeedLimit F
      // 0a1b: fconst_0
      // 0a1c: fcmpl
      // 0a1d: ifle 0a30
      // 0a20: fload 9
      // 0a22: aload 0
      // 0a23: getfield com/starpilot/starview/Telemetry.slcSpeedLimit F
      // 0a26: aload 0
      // 0a27: getfield com/starpilot/starview/Telemetry.slcOffset F
      // 0a2a: fadd
      // 0a2b: invokestatic java/lang/Math.min (FF)F
      // 0a2e: fstore 8
      // 0a30: fload 8
      // 0a32: fstore 9
      // 0a34: fload 8
      // 0a36: fconst_0
      // 0a37: fcmpl
      // 0a38: ifle 0a5e
      // 0a3b: fload 8
      // 0a3d: fstore 9
      // 0a3f: aload 0
      // 0a40: getfield com/starpilot/starview/Telemetry.cscControlling Z
      // 0a43: ifeq 0a5e
      // 0a46: fload 8
      // 0a48: fstore 9
      // 0a4a: aload 0
      // 0a4b: getfield com/starpilot/starview/Telemetry.cscSpeed F
      // 0a4e: fconst_0
      // 0a4f: fcmpl
      // 0a50: ifle 0a5e
      // 0a53: fload 8
      // 0a55: aload 0
      // 0a56: getfield com/starpilot/starview/Telemetry.cscSpeed F
      // 0a59: invokestatic java/lang/Math.min (FF)F
      // 0a5c: fstore 9
      // 0a5e: aload 0
      // 0a5f: fload 9
      // 0a61: putfield com/starpilot/starview/Telemetry.planVCruise F
      // 0a64: aload 0
      // 0a65: aload 0
      // 0a66: getfield com/starpilot/starview/Telemetry.leadOne Lcom/starpilot/starview/Lead;
      // 0a69: invokevirtual com/starpilot/starview/Lead.getStatus ()Z
      // 0a6c: putfield com/starpilot/starview/Telemetry.trackingLead Z
      // 0a6f: aload 0
      // 0a70: getfield com/starpilot/starview/Telemetry.slcNext F
      // 0a73: fconst_0
      // 0a74: fcmpl
      // 0a75: ifle 0a91
      // 0a78: aload 0
      // 0a79: getfield com/starpilot/starview/Telemetry.slcNext F
      // 0a7c: aload 0
      // 0a7d: getfield com/starpilot/starview/Telemetry.slcSpeedLimit F
      // 0a80: fsub
      // 0a81: invokestatic java/lang/Math.abs (F)F
      // 0a84: ldc_w 0.3
      // 0a87: fcmpl
      // 0a88: ifle 0a91
      // 0a8b: bipush 1
      // 0a8c: istore 23
      // 0a8e: goto 0a94
      // 0a91: bipush 0
      // 0a92: istore 23
      // 0a94: aload 0
      // 0a95: iload 23
      // 0a97: putfield com/starpilot/starview/Telemetry.slcChanged Z
      // 0a9a: iload 23
      // 0a9c: ifeq 0aa8
      // 0a9f: aload 0
      // 0aa0: getfield com/starpilot/starview/Telemetry.slcNext F
      // 0aa3: fstore 8
      // 0aa5: goto 0aab
      // 0aa8: fconst_0
      // 0aa9: fstore 8
      // 0aab: aload 0
      // 0aac: fload 8
      // 0aae: putfield com/starpilot/starview/Telemetry.slcUnconfirmed F
      // 0ab1: aload 0
      // 0ab2: aload 1
      // 0ab3: invokevirtual ai/comma/cereal/Custom$StarPilotPlan$Reader.getWeatherId ()S
      // 0ab6: putfield com/starpilot/starview/Telemetry.weatherId I
      // 0ab9: aload 0
      // 0aba: aload 1
      // 0abb: invokevirtual ai/comma/cereal/Custom$StarPilotPlan$Reader.getWeatherDaytime ()Z
      // 0abe: putfield com/starpilot/starview/Telemetry.weatherDay Z
      // 0ac1: aload 1
      // 0ac2: invokevirtual ai/comma/cereal/Custom$StarPilotPlan$Reader.getStarpilotToggles ()Lorg/capnproto/Text$Reader;
      // 0ac5: invokevirtual org/capnproto/Text$Reader.toString ()Ljava/lang/String;
      // 0ac8: astore 34
      // 0aca: aload 34
      // 0acc: ldc_w "toString(...)"
      // 0acf: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 0ad2: aload 34
      // 0ad4: checkcast java/lang/CharSequence
      // 0ad7: invokeinterface java/lang/CharSequence.length ()I 1
      // 0adc: ifle 0ae6
      // 0adf: iload 21
      // 0ae1: istore 18
      // 0ae3: goto 0ae9
      // 0ae6: bipush 0
      // 0ae7: istore 18
      // 0ae9: iload 18
      // 0aeb: ifeq 0b5b
      // 0aee: aload 34
      // 0af0: aload 0
      // 0af1: getfield com/starpilot/starview/Telemetry.lastTogglesStr Ljava/lang/String;
      // 0af4: invokestatic kotlin/jvm/internal/Intrinsics.areEqual (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 0af7: ifne 0b5b
      // 0afa: aload 0
      // 0afb: aload 34
      // 0afd: putfield com/starpilot/starview/Telemetry.lastTogglesStr Ljava/lang/String;
      // 0b00: new org/json/JSONObject
      // 0b03: astore 1
      // 0b04: aload 1
      // 0b05: aload 34
      // 0b07: invokespecial org/json/JSONObject.<init> (Ljava/lang/String;)V
      // 0b0a: new java/util/HashMap
      // 0b0d: astore 34
      // 0b0f: aload 34
      // 0b11: invokespecial java/util/HashMap.<init> ()V
      // 0b14: aload 1
      // 0b15: invokevirtual org/json/JSONObject.keys ()Ljava/util/Iterator;
      // 0b18: astore 35
      // 0b1a: aload 35
      // 0b1c: ldc_w "keys(...)"
      // 0b1f: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 0b22: aload 35
      // 0b24: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 0b29: ifeq 0b4e
      // 0b2c: aload 35
      // 0b2e: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 0b33: checkcast java/lang/String
      // 0b36: astore 36
      // 0b38: aload 34
      // 0b3a: checkcast java/util/Map
      // 0b3d: aload 36
      // 0b3f: aload 1
      // 0b40: aload 36
      // 0b42: invokevirtual org/json/JSONObject.opt (Ljava/lang/String;)Ljava/lang/Object;
      // 0b45: invokeinterface java/util/Map.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
      // 0b4a: pop
      // 0b4b: goto 0b22
      // 0b4e: aload 0
      // 0b4f: aload 34
      // 0b51: checkcast java/util/Map
      // 0b54: putfield com/starpilot/starview/Telemetry.toggles Ljava/util/Map;
      // 0b57: goto 0b5b
      // 0b5a: astore 1
      // 0b5b: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 0b5e: astore 1
      // 0b5f: goto 16e1
      // 0b62: aload 1
      // 0b63: invokevirtual ai/comma/cereal/Log$Event$Reader.getDriverMonitoringState ()Lai/comma/cereal/Log$DriverMonitoringState$Reader;
      // 0b66: astore 1
      // 0b67: aload 0
      // 0b68: aload 1
      // 0b69: invokevirtual ai/comma/cereal/Log$DriverMonitoringState$Reader.getActivePolicy ()Lai/comma/cereal/Log$DriverMonitoringState$MonitoringPolicy;
      // 0b6c: invokevirtual ai/comma/cereal/Log$DriverMonitoringState$MonitoringPolicy.toString ()Ljava/lang/String;
      // 0b6f: ldc_w "VISION"
      // 0b72: invokestatic kotlin/jvm/internal/Intrinsics.areEqual (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 0b75: putfield com/starpilot/starview/Telemetry.dmPolicyVision Z
      // 0b78: aload 0
      // 0b79: aload 1
      // 0b7a: invokevirtual ai/comma/cereal/Log$DriverMonitoringState$Reader.getIsRHD ()Z
      // 0b7d: putfield com/starpilot/starview/Telemetry.dmRhd Z
      // 0b80: aload 1
      // 0b81: invokevirtual ai/comma/cereal/Log$DriverMonitoringState$Reader.getVisionPolicyState ()Lai/comma/cereal/Log$DriverMonitoringState$VisionPolicyState$Reader;
      // 0b84: astore 1
      // 0b85: aload 0
      // 0b86: aload 1
      // 0b87: invokevirtual ai/comma/cereal/Log$DriverMonitoringState$VisionPolicyState$Reader.getFaceDetected ()Z
      // 0b8a: putfield com/starpilot/starview/Telemetry.dmFace Z
      // 0b8d: aload 0
      // 0b8e: aload 1
      // 0b8f: invokevirtual ai/comma/cereal/Log$DriverMonitoringState$VisionPolicyState$Reader.getAwarenessPercent ()B
      // 0b92: sipush 255
      // 0b95: iand
      // 0b96: putfield com/starpilot/starview/Telemetry.dmAwareness I
      // 0b99: aload 1
      // 0b9a: invokevirtual ai/comma/cereal/Log$DriverMonitoringState$VisionPolicyState$Reader.getPose ()Lai/comma/cereal/Log$DriverMonitoringState$VisionPolicyState$Pose$Reader;
      // 0b9d: astore 1
      // 0b9e: aload 0
      // 0b9f: aload 1
      // 0ba0: invokevirtual ai/comma/cereal/Log$DriverMonitoringState$VisionPolicyState$Pose$Reader.getPitch ()F
      // 0ba3: putfield com/starpilot/starview/Telemetry.dmPitch F
      // 0ba6: aload 0
      // 0ba7: aload 1
      // 0ba8: invokevirtual ai/comma/cereal/Log$DriverMonitoringState$VisionPolicyState$Pose$Reader.getYaw ()F
      // 0bab: putfield com/starpilot/starview/Telemetry.dmYaw F
      // 0bae: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 0bb1: astore 1
      // 0bb2: goto 16e1
      // 0bb5: aload 1
      // 0bb6: invokevirtual ai/comma/cereal/Log$Event$Reader.getStarpilotRadarState ()Lai/comma/cereal/Custom$StarPilotRadarState$Reader;
      // 0bb9: astore 34
      // 0bbb: aload 34
      // 0bbd: invokevirtual ai/comma/cereal/Custom$StarPilotRadarState$Reader.getLeadLeft ()Lai/comma/cereal/Custom$StarPilotRadarState$LeadData$Reader;
      // 0bc0: astore 1
      // 0bc1: aload 0
      // 0bc2: new com/starpilot/starview/Lead
      // 0bc5: dup
      // 0bc6: aload 1
      // 0bc7: invokevirtual ai/comma/cereal/Custom$StarPilotRadarState$LeadData$Reader.getStatus ()Z
      // 0bca: aload 1
      // 0bcb: invokevirtual ai/comma/cereal/Custom$StarPilotRadarState$LeadData$Reader.getDRel ()F
      // 0bce: aload 1
      // 0bcf: invokevirtual ai/comma/cereal/Custom$StarPilotRadarState$LeadData$Reader.getYRel ()F
      // 0bd2: aload 1
      // 0bd3: invokevirtual ai/comma/cereal/Custom$StarPilotRadarState$LeadData$Reader.getVRel ()F
      // 0bd6: aload 1
      // 0bd7: invokevirtual ai/comma/cereal/Custom$StarPilotRadarState$LeadData$Reader.getVLead ()F
      // 0bda: aload 1
      // 0bdb: invokevirtual ai/comma/cereal/Custom$StarPilotRadarState$LeadData$Reader.getModelProb ()F
      // 0bde: bipush 0
      // 0bdf: bipush 64
      // 0be1: aconst_null
      // 0be2: invokespecial com/starpilot/starview/Lead.<init> (ZFFFFFZILkotlin/jvm/internal/DefaultConstructorMarker;)V
      // 0be5: putfield com/starpilot/starview/Telemetry.leadLeft Lcom/starpilot/starview/Lead;
      // 0be8: aload 34
      // 0bea: invokevirtual ai/comma/cereal/Custom$StarPilotRadarState$Reader.getLeadRight ()Lai/comma/cereal/Custom$StarPilotRadarState$LeadData$Reader;
      // 0bed: astore 1
      // 0bee: aload 0
      // 0bef: new com/starpilot/starview/Lead
      // 0bf2: dup
      // 0bf3: aload 1
      // 0bf4: invokevirtual ai/comma/cereal/Custom$StarPilotRadarState$LeadData$Reader.getStatus ()Z
      // 0bf7: aload 1
      // 0bf8: invokevirtual ai/comma/cereal/Custom$StarPilotRadarState$LeadData$Reader.getDRel ()F
      // 0bfb: aload 1
      // 0bfc: invokevirtual ai/comma/cereal/Custom$StarPilotRadarState$LeadData$Reader.getYRel ()F
      // 0bff: aload 1
      // 0c00: invokevirtual ai/comma/cereal/Custom$StarPilotRadarState$LeadData$Reader.getVRel ()F
      // 0c03: aload 1
      // 0c04: invokevirtual ai/comma/cereal/Custom$StarPilotRadarState$LeadData$Reader.getVLead ()F
      // 0c07: aload 1
      // 0c08: invokevirtual ai/comma/cereal/Custom$StarPilotRadarState$LeadData$Reader.getModelProb ()F
      // 0c0b: bipush 0
      // 0c0c: bipush 64
      // 0c0e: aconst_null
      // 0c0f: invokespecial com/starpilot/starview/Lead.<init> (ZFFFFFZILkotlin/jvm/internal/DefaultConstructorMarker;)V
      // 0c12: putfield com/starpilot/starview/Telemetry.leadRight Lcom/starpilot/starview/Lead;
      // 0c15: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 0c18: astore 1
      // 0c19: goto 16e1
      // 0c1c: aload 1
      // 0c1d: invokevirtual ai/comma/cereal/Log$Event$Reader.getRadarState ()Lai/comma/cereal/Log$RadarState$Reader;
      // 0c20: astore 34
      // 0c22: aload 34
      // 0c24: invokevirtual ai/comma/cereal/Log$RadarState$Reader.getLeadOne ()Lai/comma/cereal/Log$RadarState$LeadData$Reader;
      // 0c27: astore 1
      // 0c28: aload 0
      // 0c29: new com/starpilot/starview/Lead
      // 0c2c: dup
      // 0c2d: aload 1
      // 0c2e: invokevirtual ai/comma/cereal/Log$RadarState$LeadData$Reader.getStatus ()Z
      // 0c31: aload 1
      // 0c32: invokevirtual ai/comma/cereal/Log$RadarState$LeadData$Reader.getDRel ()F
      // 0c35: aload 1
      // 0c36: invokevirtual ai/comma/cereal/Log$RadarState$LeadData$Reader.getYRel ()F
      // 0c39: aload 1
      // 0c3a: invokevirtual ai/comma/cereal/Log$RadarState$LeadData$Reader.getVRel ()F
      // 0c3d: aload 1
      // 0c3e: invokevirtual ai/comma/cereal/Log$RadarState$LeadData$Reader.getVLead ()F
      // 0c41: aload 1
      // 0c42: invokevirtual ai/comma/cereal/Log$RadarState$LeadData$Reader.getModelProb ()F
      // 0c45: aload 1
      // 0c46: invokevirtual ai/comma/cereal/Log$RadarState$LeadData$Reader.getRadar ()Z
      // 0c49: invokespecial com/starpilot/starview/Lead.<init> (ZFFFFFZ)V
      // 0c4c: putfield com/starpilot/starview/Telemetry.leadOne Lcom/starpilot/starview/Lead;
      // 0c4f: aload 34
      // 0c51: invokevirtual ai/comma/cereal/Log$RadarState$Reader.getLeadTwo ()Lai/comma/cereal/Log$RadarState$LeadData$Reader;
      // 0c54: astore 1
      // 0c55: aload 0
      // 0c56: new com/starpilot/starview/Lead
      // 0c59: dup
      // 0c5a: aload 1
      // 0c5b: invokevirtual ai/comma/cereal/Log$RadarState$LeadData$Reader.getStatus ()Z
      // 0c5e: aload 1
      // 0c5f: invokevirtual ai/comma/cereal/Log$RadarState$LeadData$Reader.getDRel ()F
      // 0c62: aload 1
      // 0c63: invokevirtual ai/comma/cereal/Log$RadarState$LeadData$Reader.getYRel ()F
      // 0c66: aload 1
      // 0c67: invokevirtual ai/comma/cereal/Log$RadarState$LeadData$Reader.getVRel ()F
      // 0c6a: aload 1
      // 0c6b: invokevirtual ai/comma/cereal/Log$RadarState$LeadData$Reader.getVLead ()F
      // 0c6e: aload 1
      // 0c6f: invokevirtual ai/comma/cereal/Log$RadarState$LeadData$Reader.getModelProb ()F
      // 0c72: aload 1
      // 0c73: invokevirtual ai/comma/cereal/Log$RadarState$LeadData$Reader.getRadar ()Z
      // 0c76: invokespecial com/starpilot/starview/Lead.<init> (ZFFFFFZ)V
      // 0c79: putfield com/starpilot/starview/Telemetry.leadTwo Lcom/starpilot/starview/Lead;
      // 0c7c: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 0c7f: astore 1
      // 0c80: goto 16e1
      // 0c83: aload 1
      // 0c84: invokevirtual ai/comma/cereal/Log$Event$Reader.getModelV2 ()Lai/comma/cereal/Log$ModelDataV2$Reader;
      // 0c87: astore 36
      // 0c89: aload 0
      // 0c8a: invokestatic java/lang/System.nanoTime ()J
      // 0c8d: putfield com/starpilot/starview/Telemetry.lastModelRxNs J
      // 0c90: aload 0
      // 0c91: bipush 1
      // 0c92: putfield com/starpilot/starview/Telemetry.modelSeen Z
      // 0c95: aload 36
      // 0c97: invokevirtual ai/comma/cereal/Log$ModelDataV2$Reader.getLaneLines ()Lorg/capnproto/StructList$Reader;
      // 0c9a: astore 42
      // 0c9c: aload 36
      // 0c9e: invokevirtual ai/comma/cereal/Log$ModelDataV2$Reader.getRoadEdges ()Lorg/capnproto/StructList$Reader;
      // 0ca1: astore 1
      // 0ca2: aload 42
      // 0ca4: invokevirtual org/capnproto/StructList$Reader.size ()I
      // 0ca7: istore 20
      // 0ca9: aload 1
      // 0caa: invokevirtual org/capnproto/StructList$Reader.size ()I
      // 0cad: istore 19
      // 0caf: aload 36
      // 0cb1: invokevirtual ai/comma/cereal/Log$ModelDataV2$Reader.getFrameId ()I
      // 0cb4: i2l
      // 0cb5: lstore 32
      // 0cb7: aload 36
      // 0cb9: invokevirtual ai/comma/cereal/Log$ModelDataV2$Reader.getPosition ()Lai/comma/cereal/Log$XYZTData$Reader;
      // 0cbc: invokevirtual ai/comma/cereal/Log$XYZTData$Reader.getX ()Lorg/capnproto/PrimitiveList$Float$Reader;
      // 0cbf: astore 34
      // 0cc1: aload 34
      // 0cc3: ldc_w "getX(...)"
      // 0cc6: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 0cc9: aload 0
      // 0cca: aload 34
      // 0ccc: invokespecial com/starpilot/starview/Telemetry.fl (Lorg/capnproto/PrimitiveList$Float$Reader;)[F
      // 0ccf: astore 34
      // 0cd1: aload 36
      // 0cd3: invokevirtual ai/comma/cereal/Log$ModelDataV2$Reader.getPosition ()Lai/comma/cereal/Log$XYZTData$Reader;
      // 0cd6: invokevirtual ai/comma/cereal/Log$XYZTData$Reader.getY ()Lorg/capnproto/PrimitiveList$Float$Reader;
      // 0cd9: astore 35
      // 0cdb: aload 35
      // 0cdd: ldc_w "getY(...)"
      // 0ce0: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 0ce3: aload 0
      // 0ce4: aload 35
      // 0ce6: invokespecial com/starpilot/starview/Telemetry.fl (Lorg/capnproto/PrimitiveList$Float$Reader;)[F
      // 0ce9: astore 35
      // 0ceb: aload 36
      // 0ced: invokevirtual ai/comma/cereal/Log$ModelDataV2$Reader.getPosition ()Lai/comma/cereal/Log$XYZTData$Reader;
      // 0cf0: invokevirtual ai/comma/cereal/Log$XYZTData$Reader.getZ ()Lorg/capnproto/PrimitiveList$Float$Reader;
      // 0cf3: astore 37
      // 0cf5: aload 37
      // 0cf7: ldc_w "getZ(...)"
      // 0cfa: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 0cfd: aload 0
      // 0cfe: aload 37
      // 0d00: invokespecial com/starpilot/starview/Telemetry.fl (Lorg/capnproto/PrimitiveList$Float$Reader;)[F
      // 0d03: astore 37
      // 0d05: aload 36
      // 0d07: invokevirtual ai/comma/cereal/Log$ModelDataV2$Reader.getAcceleration ()Lai/comma/cereal/Log$XYZTData$Reader;
      // 0d0a: invokevirtual ai/comma/cereal/Log$XYZTData$Reader.getX ()Lorg/capnproto/PrimitiveList$Float$Reader;
      // 0d0d: astore 38
      // 0d0f: aload 38
      // 0d11: ldc_w "getX(...)"
      // 0d14: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 0d17: aload 0
      // 0d18: aload 38
      // 0d1a: invokespecial com/starpilot/starview/Telemetry.fl (Lorg/capnproto/PrimitiveList$Float$Reader;)[F
      // 0d1d: astore 39
      // 0d1f: iload 20
      // 0d21: anewarray 1802
      // 0d24: astore 38
      // 0d26: bipush 0
      // 0d27: istore 18
      // 0d29: iload 18
      // 0d2b: iload 20
      // 0d2d: if_icmpge 0d58
      // 0d30: aload 42
      // 0d32: iload 18
      // 0d34: invokevirtual org/capnproto/StructList$Reader.get (I)Ljava/lang/Object;
      // 0d37: checkcast ai/comma/cereal/Log$XYZTData$Reader
      // 0d3a: invokevirtual ai/comma/cereal/Log$XYZTData$Reader.getX ()Lorg/capnproto/PrimitiveList$Float$Reader;
      // 0d3d: astore 40
      // 0d3f: aload 40
      // 0d41: ldc_w "getX(...)"
      // 0d44: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 0d47: aload 38
      // 0d49: iload 18
      // 0d4b: aload 0
      // 0d4c: aload 40
      // 0d4e: invokespecial com/starpilot/starview/Telemetry.fl (Lorg/capnproto/PrimitiveList$Float$Reader;)[F
      // 0d51: aastore
      // 0d52: iinc 18 1
      // 0d55: goto 0d29
      // 0d58: iload 20
      // 0d5a: anewarray 1802
      // 0d5d: astore 40
      // 0d5f: bipush 0
      // 0d60: istore 18
      // 0d62: iload 18
      // 0d64: iload 20
      // 0d66: if_icmpge 0d91
      // 0d69: aload 42
      // 0d6b: iload 18
      // 0d6d: invokevirtual org/capnproto/StructList$Reader.get (I)Ljava/lang/Object;
      // 0d70: checkcast ai/comma/cereal/Log$XYZTData$Reader
      // 0d73: invokevirtual ai/comma/cereal/Log$XYZTData$Reader.getY ()Lorg/capnproto/PrimitiveList$Float$Reader;
      // 0d76: astore 41
      // 0d78: aload 41
      // 0d7a: ldc_w "getY(...)"
      // 0d7d: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 0d80: aload 40
      // 0d82: iload 18
      // 0d84: aload 0
      // 0d85: aload 41
      // 0d87: invokespecial com/starpilot/starview/Telemetry.fl (Lorg/capnproto/PrimitiveList$Float$Reader;)[F
      // 0d8a: aastore
      // 0d8b: iinc 18 1
      // 0d8e: goto 0d62
      // 0d91: iload 20
      // 0d93: anewarray 1802
      // 0d96: astore 41
      // 0d98: bipush 0
      // 0d99: istore 18
      // 0d9b: iload 18
      // 0d9d: iload 20
      // 0d9f: if_icmpge 0dca
      // 0da2: aload 42
      // 0da4: iload 18
      // 0da6: invokevirtual org/capnproto/StructList$Reader.get (I)Ljava/lang/Object;
      // 0da9: checkcast ai/comma/cereal/Log$XYZTData$Reader
      // 0dac: invokevirtual ai/comma/cereal/Log$XYZTData$Reader.getZ ()Lorg/capnproto/PrimitiveList$Float$Reader;
      // 0daf: astore 43
      // 0db1: aload 43
      // 0db3: ldc_w "getZ(...)"
      // 0db6: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 0db9: aload 41
      // 0dbb: iload 18
      // 0dbd: aload 0
      // 0dbe: aload 43
      // 0dc0: invokespecial com/starpilot/starview/Telemetry.fl (Lorg/capnproto/PrimitiveList$Float$Reader;)[F
      // 0dc3: aastore
      // 0dc4: iinc 18 1
      // 0dc7: goto 0d9b
      // 0dca: aload 36
      // 0dcc: invokevirtual ai/comma/cereal/Log$ModelDataV2$Reader.getLaneLineProbs ()Lorg/capnproto/PrimitiveList$Float$Reader;
      // 0dcf: astore 42
      // 0dd1: aload 42
      // 0dd3: ldc_w "getLaneLineProbs(...)"
      // 0dd6: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 0dd9: aload 0
      // 0dda: aload 42
      // 0ddc: invokespecial com/starpilot/starview/Telemetry.fl (Lorg/capnproto/PrimitiveList$Float$Reader;)[F
      // 0ddf: astore 42
      // 0de1: iload 19
      // 0de3: anewarray 1802
      // 0de6: astore 43
      // 0de8: bipush 0
      // 0de9: istore 18
      // 0deb: iload 18
      // 0ded: iload 19
      // 0def: if_icmpge 0e19
      // 0df2: aload 1
      // 0df3: iload 18
      // 0df5: invokevirtual org/capnproto/StructList$Reader.get (I)Ljava/lang/Object;
      // 0df8: checkcast ai/comma/cereal/Log$XYZTData$Reader
      // 0dfb: invokevirtual ai/comma/cereal/Log$XYZTData$Reader.getX ()Lorg/capnproto/PrimitiveList$Float$Reader;
      // 0dfe: astore 44
      // 0e00: aload 44
      // 0e02: ldc_w "getX(...)"
      // 0e05: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 0e08: aload 43
      // 0e0a: iload 18
      // 0e0c: aload 0
      // 0e0d: aload 44
      // 0e0f: invokespecial com/starpilot/starview/Telemetry.fl (Lorg/capnproto/PrimitiveList$Float$Reader;)[F
      // 0e12: aastore
      // 0e13: iinc 18 1
      // 0e16: goto 0deb
      // 0e19: iload 19
      // 0e1b: anewarray 1802
      // 0e1e: astore 44
      // 0e20: bipush 0
      // 0e21: istore 18
      // 0e23: iload 18
      // 0e25: iload 19
      // 0e27: if_icmpge 0e51
      // 0e2a: aload 1
      // 0e2b: iload 18
      // 0e2d: invokevirtual org/capnproto/StructList$Reader.get (I)Ljava/lang/Object;
      // 0e30: checkcast ai/comma/cereal/Log$XYZTData$Reader
      // 0e33: invokevirtual ai/comma/cereal/Log$XYZTData$Reader.getY ()Lorg/capnproto/PrimitiveList$Float$Reader;
      // 0e36: astore 45
      // 0e38: aload 45
      // 0e3a: ldc_w "getY(...)"
      // 0e3d: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 0e40: aload 44
      // 0e42: iload 18
      // 0e44: aload 0
      // 0e45: aload 45
      // 0e47: invokespecial com/starpilot/starview/Telemetry.fl (Lorg/capnproto/PrimitiveList$Float$Reader;)[F
      // 0e4a: aastore
      // 0e4b: iinc 18 1
      // 0e4e: goto 0e23
      // 0e51: iload 19
      // 0e53: anewarray 1802
      // 0e56: astore 45
      // 0e58: bipush 0
      // 0e59: istore 18
      // 0e5b: iload 18
      // 0e5d: iload 19
      // 0e5f: if_icmpge 0e89
      // 0e62: aload 1
      // 0e63: iload 18
      // 0e65: invokevirtual org/capnproto/StructList$Reader.get (I)Ljava/lang/Object;
      // 0e68: checkcast ai/comma/cereal/Log$XYZTData$Reader
      // 0e6b: invokevirtual ai/comma/cereal/Log$XYZTData$Reader.getZ ()Lorg/capnproto/PrimitiveList$Float$Reader;
      // 0e6e: astore 46
      // 0e70: aload 46
      // 0e72: ldc_w "getZ(...)"
      // 0e75: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 0e78: aload 45
      // 0e7a: iload 18
      // 0e7c: aload 0
      // 0e7d: aload 46
      // 0e7f: invokespecial com/starpilot/starview/Telemetry.fl (Lorg/capnproto/PrimitiveList$Float$Reader;)[F
      // 0e82: aastore
      // 0e83: iinc 18 1
      // 0e86: goto 0e5b
      // 0e89: aload 36
      // 0e8b: invokevirtual ai/comma/cereal/Log$ModelDataV2$Reader.getRoadEdgeStds ()Lorg/capnproto/PrimitiveList$Float$Reader;
      // 0e8e: astore 1
      // 0e8f: aload 1
      // 0e90: ldc_w "getRoadEdgeStds(...)"
      // 0e93: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 0e96: aload 0
      // 0e97: aload 1
      // 0e98: invokespecial com/starpilot/starview/Telemetry.fl (Lorg/capnproto/PrimitiveList$Float$Reader;)[F
      // 0e9b: astore 46
      // 0e9d: aload 36
      // 0e9f: invokevirtual ai/comma/cereal/Log$ModelDataV2$Reader.getLaneLineStds ()Lorg/capnproto/PrimitiveList$Float$Reader;
      // 0ea2: astore 1
      // 0ea3: aload 1
      // 0ea4: ldc_w "getLaneLineStds(...)"
      // 0ea7: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 0eaa: aload 0
      // 0eab: aload 1
      // 0eac: invokespecial com/starpilot/starview/Telemetry.fl (Lorg/capnproto/PrimitiveList$Float$Reader;)[F
      // 0eaf: astore 47
      // 0eb1: aload 36
      // 0eb3: invokevirtual ai/comma/cereal/Log$ModelDataV2$Reader.getPosition ()Lai/comma/cereal/Log$XYZTData$Reader;
      // 0eb6: invokevirtual ai/comma/cereal/Log$XYZTData$Reader.getYStd ()Lorg/capnproto/PrimitiveList$Float$Reader;
      // 0eb9: astore 1
      // 0eba: aload 1
      // 0ebb: ldc_w "getYStd(...)"
      // 0ebe: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 0ec1: aload 0
      // 0ec2: aload 1
      // 0ec3: invokespecial com/starpilot/starview/Telemetry.fl (Lorg/capnproto/PrimitiveList$Float$Reader;)[F
      // 0ec6: astore 48
      // 0ec8: aload 36
      // 0eca: invokevirtual ai/comma/cereal/Log$ModelDataV2$Reader.getMeta ()Lai/comma/cereal/Log$ModelDataV2$MetaData$Reader;
      // 0ecd: invokevirtual ai/comma/cereal/Log$ModelDataV2$MetaData$Reader.getLaneChangeState ()Lai/comma/cereal/Log$LaneChangeState;
      // 0ed0: invokevirtual ai/comma/cereal/Log$LaneChangeState.toString ()Ljava/lang/String;
      // 0ed3: ldc_w "OFF"
      // 0ed6: invokestatic kotlin/jvm/internal/Intrinsics.areEqual (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 0ed9: istore 24
      // 0edb: goto 0ee2
      // 0ede: astore 1
      // 0edf: bipush 1
      // 0ee0: istore 24
      // 0ee2: aload 36
      // 0ee4: invokevirtual ai/comma/cereal/Log$ModelDataV2$Reader.getAction ()Lai/comma/cereal/Log$ModelDataV2$Action$Reader;
      // 0ee7: invokevirtual ai/comma/cereal/Log$ModelDataV2$Action$Reader.getDesiredCurvature ()F
      // 0eea: fstore 9
      // 0eec: goto 0ef3
      // 0eef: astore 1
      // 0ef0: fconst_0
      // 0ef1: fstore 9
      // 0ef3: aload 36
      // 0ef5: invokevirtual ai/comma/cereal/Log$ModelDataV2$Reader.getMeta ()Lai/comma/cereal/Log$ModelDataV2$MetaData$Reader;
      // 0ef8: invokevirtual ai/comma/cereal/Log$ModelDataV2$MetaData$Reader.getLaneChangeState ()Lai/comma/cereal/Log$LaneChangeState;
      // 0efb: invokevirtual ai/comma/cereal/Log$LaneChangeState.toString ()Ljava/lang/String;
      // 0efe: astore 1
      // 0eff: aload 1
      // 0f00: ldc_w "LANE_CHANGE_STARTING"
      // 0f03: invokestatic kotlin/jvm/internal/Intrinsics.areEqual (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 0f06: ifne 0f20
      // 0f09: aload 1
      // 0f0a: ldc_w "LANE_CHANGE_FINISHING"
      // 0f0d: invokestatic kotlin/jvm/internal/Intrinsics.areEqual (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 0f10: istore 23
      // 0f12: iload 23
      // 0f14: ifeq 0f1a
      // 0f17: goto 0f20
      // 0f1a: bipush 0
      // 0f1b: istore 23
      // 0f1d: goto 0f23
      // 0f20: bipush 1
      // 0f21: istore 23
      // 0f23: goto 0f2a
      // 0f26: astore 1
      // 0f27: bipush 0
      // 0f28: istore 23
      // 0f2a: aload 36
      // 0f2c: invokevirtual ai/comma/cereal/Log$ModelDataV2$Reader.getMeta ()Lai/comma/cereal/Log$ModelDataV2$MetaData$Reader;
      // 0f2f: invokevirtual ai/comma/cereal/Log$ModelDataV2$MetaData$Reader.getLaneChangeDirection ()Lai/comma/cereal/Log$LaneChangeDirection;
      // 0f32: invokevirtual ai/comma/cereal/Log$LaneChangeDirection.toString ()Ljava/lang/String;
      // 0f35: astore 1
      // 0f36: aload 1
      // 0f37: ldc_w "LEFT"
      // 0f3a: invokestatic kotlin/jvm/internal/Intrinsics.areEqual (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 0f3d: ifeq 0f46
      // 0f40: bipush -1
      // 0f41: istore 18
      // 0f43: goto 0f5d
      // 0f46: aload 1
      // 0f47: ldc_w "RIGHT"
      // 0f4a: invokestatic kotlin/jvm/internal/Intrinsics.areEqual (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 0f4d: istore 25
      // 0f4f: iload 25
      // 0f51: ifeq 0f5a
      // 0f54: bipush 1
      // 0f55: istore 18
      // 0f57: goto 0f5d
      // 0f5a: bipush 0
      // 0f5b: istore 18
      // 0f5d: goto 0f64
      // 0f60: astore 1
      // 0f61: bipush 0
      // 0f62: istore 18
      // 0f64: aload 36
      // 0f66: invokevirtual ai/comma/cereal/Log$ModelDataV2$Reader.getMeta ()Lai/comma/cereal/Log$ModelDataV2$MetaData$Reader;
      // 0f69: invokevirtual ai/comma/cereal/Log$ModelDataV2$MetaData$Reader.getLaneChangeState ()Lai/comma/cereal/Log$LaneChangeState;
      // 0f6c: invokevirtual ai/comma/cereal/Log$LaneChangeState.toString ()Ljava/lang/String;
      // 0f6f: ldc_w "LANE_CHANGE_FINISHING"
      // 0f72: invokestatic kotlin/jvm/internal/Intrinsics.areEqual (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 0f75: istore 25
      // 0f77: goto 0f7e
      // 0f7a: astore 1
      // 0f7b: bipush 0
      // 0f7c: istore 25
      // 0f7e: aload 36
      // 0f80: invokevirtual ai/comma/cereal/Log$ModelDataV2$Reader.getMeta ()Lai/comma/cereal/Log$ModelDataV2$MetaData$Reader;
      // 0f83: invokevirtual ai/comma/cereal/Log$ModelDataV2$MetaData$Reader.getDesirePrediction ()Lorg/capnproto/PrimitiveList$Float$Reader;
      // 0f86: astore 1
      // 0f87: aload 1
      // 0f88: ldc_w "getDesirePrediction(...)"
      // 0f8b: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 0f8e: aload 0
      // 0f8f: aload 1
      // 0f90: invokespecial com/starpilot/starview/Telemetry.fl (Lorg/capnproto/PrimitiveList$Float$Reader;)[F
      // 0f93: astore 1
      // 0f94: goto 0f9c
      // 0f97: astore 1
      // 0f98: bipush 0
      // 0f99: newarray 6
      // 0f9b: astore 1
      // 0f9c: aload 36
      // 0f9e: invokevirtual ai/comma/cereal/Log$ModelDataV2$Reader.getMeta ()Lai/comma/cereal/Log$ModelDataV2$MetaData$Reader;
      // 0fa1: invokevirtual ai/comma/cereal/Log$ModelDataV2$MetaData$Reader.getDesireState ()Lorg/capnproto/PrimitiveList$Float$Reader;
      // 0fa4: astore 36
      // 0fa6: aload 36
      // 0fa8: invokevirtual org/capnproto/PrimitiveList$Float$Reader.size ()I
      // 0fab: bipush 4
      // 0fac: if_icmple 0fc9
      // 0faf: aload 36
      // 0fb1: bipush 3
      // 0fb2: invokevirtual org/capnproto/PrimitiveList$Float$Reader.get (I)F
      // 0fb5: fstore 10
      // 0fb7: aload 36
      // 0fb9: bipush 4
      // 0fba: invokevirtual org/capnproto/PrimitiveList$Float$Reader.get (I)F
      // 0fbd: fstore 8
      // 0fbf: fload 10
      // 0fc1: fload 8
      // 0fc3: fadd
      // 0fc4: fstore 8
      // 0fc6: goto 0fce
      // 0fc9: ldc_w NaN
      // 0fcc: fstore 8
      // 0fce: goto 0fd8
      // 0fd1: astore 36
      // 0fd3: ldc_w NaN
      // 0fd6: fstore 8
      // 0fd8: aload 0
      // 0fd9: new com/starpilot/starview/ModelSnapshot
      // 0fdc: dup
      // 0fdd: lload 32
      // 0fdf: ldc2_w 4294967295
      // 0fe2: land
      // 0fe3: aload 34
      // 0fe5: aload 35
      // 0fe7: aload 37
      // 0fe9: aload 39
      // 0feb: aload 38
      // 0fed: aload 40
      // 0fef: aload 41
      // 0ff1: aload 42
      // 0ff3: aload 43
      // 0ff5: aload 44
      // 0ff7: aload 45
      // 0ff9: aload 46
      // 0ffb: aload 47
      // 0ffd: aload 48
      // 0fff: iload 24
      // 1001: fload 9
      // 1003: iload 23
      // 1005: iload 18
      // 1007: iload 25
      // 1009: aload 1
      // 100a: fload 8
      // 100c: invokespecial com/starpilot/starview/ModelSnapshot.<init> (J[F[F[F[F[[F[[F[[F[F[[F[[F[[F[F[F[FZFZIZ[FF)V
      // 100f: putfield com/starpilot/starview/Telemetry.model Lcom/starpilot/starview/ModelSnapshot;
      // 1012: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 1015: astore 1
      // 1016: goto 16e1
      // 1019: aload 1
      // 101a: invokevirtual ai/comma/cereal/Log$Event$Reader.getLiveCalibration ()Lai/comma/cereal/Log$LiveCalibrationData$Reader;
      // 101d: astore 34
      // 101f: aload 34
      // 1021: invokevirtual ai/comma/cereal/Log$LiveCalibrationData$Reader.getRpyCalib ()Lorg/capnproto/PrimitiveList$Float$Reader;
      // 1024: invokevirtual org/capnproto/PrimitiveList$Float$Reader.size ()I
      // 1027: bipush 3
      // 1028: if_icmpne 1041
      // 102b: aload 34
      // 102d: invokevirtual ai/comma/cereal/Log$LiveCalibrationData$Reader.getRpyCalib ()Lorg/capnproto/PrimitiveList$Float$Reader;
      // 1030: astore 1
      // 1031: aload 1
      // 1032: ldc_w "getRpyCalib(...)"
      // 1035: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 1038: aload 0
      // 1039: aload 0
      // 103a: aload 1
      // 103b: invokespecial com/starpilot/starview/Telemetry.fl (Lorg/capnproto/PrimitiveList$Float$Reader;)[F
      // 103e: putfield com/starpilot/starview/Telemetry.calRpy [F
      // 1041: aload 0
      // 1042: aload 34
      // 1044: invokevirtual ai/comma/cereal/Log$LiveCalibrationData$Reader.getCalStatus ()Lai/comma/cereal/Log$LiveCalibrationData$Status;
      // 1047: invokevirtual ai/comma/cereal/Log$LiveCalibrationData$Status.toString ()Ljava/lang/String;
      // 104a: ldc_w "CALIBRATED"
      // 104d: invokestatic kotlin/jvm/internal/Intrinsics.areEqual (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 1050: putfield com/starpilot/starview/Telemetry.calValid Z
      // 1053: aload 34
      // 1055: invokevirtual ai/comma/cereal/Log$LiveCalibrationData$Reader.getHeight ()Lorg/capnproto/PrimitiveList$Float$Reader;
      // 1058: invokevirtual org/capnproto/PrimitiveList$Float$Reader.size ()I
      // 105b: ifle 106b
      // 105e: aload 0
      // 105f: aload 34
      // 1061: invokevirtual ai/comma/cereal/Log$LiveCalibrationData$Reader.getHeight ()Lorg/capnproto/PrimitiveList$Float$Reader;
      // 1064: bipush 0
      // 1065: invokevirtual org/capnproto/PrimitiveList$Float$Reader.get (I)F
      // 1068: putfield com/starpilot/starview/Telemetry.calHeight F
      // 106b: aload 0
      // 106c: getfield com/starpilot/starview/Telemetry.calFrame J
      // 106f: lstore 32
      // 1071: aload 0
      // 1072: lconst_1
      // 1073: lload 32
      // 1075: ladd
      // 1076: putfield com/starpilot/starview/Telemetry.calFrame J
      // 1079: goto 16e1
      // 107c: aload 1
      // 107d: invokevirtual ai/comma/cereal/Log$Event$Reader.getRoadCameraState ()Lai/comma/cereal/Log$FrameData$Reader;
      // 1080: invokevirtual ai/comma/cereal/Log$FrameData$Reader.getSensor ()Lai/comma/cereal/Log$FrameData$ImageSensor;
      // 1083: invokevirtual ai/comma/cereal/Log$FrameData$ImageSensor.toString ()Ljava/lang/String;
      // 1086: getstatic java/util/Locale.ROOT Ljava/util/Locale;
      // 1089: invokevirtual java/lang/String.toLowerCase (Ljava/util/Locale;)Ljava/lang/String;
      // 108c: astore 1
      // 108d: aload 1
      // 108e: ldc_w "toLowerCase(...)"
      // 1091: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 1094: aload 0
      // 1095: aload 1
      // 1096: putfield com/starpilot/starview/Telemetry.sensor Ljava/lang/String;
      // 1099: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 109c: astore 1
      // 109d: goto 16e1
      // 10a0: aload 1
      // 10a1: invokevirtual ai/comma/cereal/Log$Event$Reader.getDeviceState ()Lai/comma/cereal/Log$DeviceState$Reader;
      // 10a4: astore 1
      // 10a5: aload 1
      // 10a6: invokevirtual ai/comma/cereal/Log$DeviceState$Reader.getStarted ()Z
      // 10a9: ifeq 10ba
      // 10ac: aload 0
      // 10ad: getfield com/starpilot/starview/Telemetry.started Z
      // 10b0: ifne 10ba
      // 10b3: aload 0
      // 10b4: ldc_w NaN
      // 10b7: putfield com/starpilot/starview/Telemetry.maxAccel F
      // 10ba: aload 0
      // 10bb: aload 1
      // 10bc: invokevirtual ai/comma/cereal/Log$DeviceState$Reader.getStarted ()Z
      // 10bf: putfield com/starpilot/starview/Telemetry.started Z
      // 10c2: aload 1
      // 10c3: invokevirtual ai/comma/cereal/Log$DeviceState$Reader.getDeviceType ()Lai/comma/cereal/Log$InitData$DeviceType;
      // 10c6: invokevirtual ai/comma/cereal/Log$InitData$DeviceType.toString ()Ljava/lang/String;
      // 10c9: getstatic java/util/Locale.ROOT Ljava/util/Locale;
      // 10cc: invokevirtual java/lang/String.toLowerCase (Ljava/util/Locale;)Ljava/lang/String;
      // 10cf: astore 34
      // 10d1: aload 34
      // 10d3: ldc_w "toLowerCase(...)"
      // 10d6: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 10d9: aload 0
      // 10da: aload 34
      // 10dc: putfield com/starpilot/starview/Telemetry.deviceType Ljava/lang/String;
      // 10df: aload 1
      // 10e0: invokevirtual ai/comma/cereal/Log$DeviceState$Reader.getCpuTempC ()Lorg/capnproto/PrimitiveList$Float$Reader;
      // 10e3: invokevirtual org/capnproto/PrimitiveList$Float$Reader.size ()I
      // 10e6: ifle 10f6
      // 10e9: aload 1
      // 10ea: invokevirtual ai/comma/cereal/Log$DeviceState$Reader.getCpuTempC ()Lorg/capnproto/PrimitiveList$Float$Reader;
      // 10ed: bipush 0
      // 10ee: invokevirtual org/capnproto/PrimitiveList$Float$Reader.get (I)F
      // 10f1: fstore 8
      // 10f3: goto 10f9
      // 10f6: fconst_0
      // 10f7: fstore 8
      // 10f9: aload 0
      // 10fa: fload 8
      // 10fc: putfield com/starpilot/starview/Telemetry.cpuTempC F
      // 10ff: aload 1
      // 1100: invokevirtual ai/comma/cereal/Log$DeviceState$Reader.getCpuUsagePercent ()Lorg/capnproto/PrimitiveList$Byte$Reader;
      // 1103: invokevirtual org/capnproto/PrimitiveList$Byte$Reader.size ()I
      // 1106: ifle 1144
      // 1109: fconst_0
      // 110a: fstore 8
      // 110c: bipush 0
      // 110d: istore 18
      // 110f: aload 1
      // 1110: invokevirtual ai/comma/cereal/Log$DeviceState$Reader.getCpuUsagePercent ()Lorg/capnproto/PrimitiveList$Byte$Reader;
      // 1113: invokevirtual org/capnproto/PrimitiveList$Byte$Reader.size ()I
      // 1116: istore 19
      // 1118: iload 18
      // 111a: iload 19
      // 111c: if_icmpge 1134
      // 111f: fload 8
      // 1121: aload 1
      // 1122: invokevirtual ai/comma/cereal/Log$DeviceState$Reader.getCpuUsagePercent ()Lorg/capnproto/PrimitiveList$Byte$Reader;
      // 1125: iload 18
      // 1127: invokevirtual org/capnproto/PrimitiveList$Byte$Reader.get (I)B
      // 112a: i2f
      // 112b: fadd
      // 112c: fstore 8
      // 112e: iinc 18 1
      // 1131: goto 1118
      // 1134: fload 8
      // 1136: aload 1
      // 1137: invokevirtual ai/comma/cereal/Log$DeviceState$Reader.getCpuUsagePercent ()Lorg/capnproto/PrimitiveList$Byte$Reader;
      // 113a: invokevirtual org/capnproto/PrimitiveList$Byte$Reader.size ()I
      // 113d: i2f
      // 113e: fdiv
      // 113f: fstore 8
      // 1141: goto 1147
      // 1144: fconst_0
      // 1145: fstore 8
      // 1147: aload 0
      // 1148: fload 8
      // 114a: putfield com/starpilot/starview/Telemetry.cpuUsage F
      // 114d: aload 0
      // 114e: aload 1
      // 114f: invokevirtual ai/comma/cereal/Log$DeviceState$Reader.getMemoryUsagePercent ()B
      // 1152: putfield com/starpilot/starview/Telemetry.memUsage I
      // 1155: aload 0
      // 1156: aload 1
      // 1157: invokevirtual ai/comma/cereal/Log$DeviceState$Reader.getFreeSpacePercent ()F
      // 115a: putfield com/starpilot/starview/Telemetry.freeSpacePct F
      // 115d: aload 1
      // 115e: invokevirtual ai/comma/cereal/Log$DeviceState$Reader.getThermalStatus ()Lai/comma/cereal/Log$DeviceState$ThermalStatus;
      // 1161: invokevirtual ai/comma/cereal/Log$DeviceState$ThermalStatus.toString ()Ljava/lang/String;
      // 1164: getstatic java/util/Locale.ROOT Ljava/util/Locale;
      // 1167: invokevirtual java/lang/String.toLowerCase (Ljava/util/Locale;)Ljava/lang/String;
      // 116a: astore 34
      // 116c: aload 34
      // 116e: ldc_w "toLowerCase(...)"
      // 1171: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 1174: aload 0
      // 1175: aload 34
      // 1177: putfield com/starpilot/starview/Telemetry.thermalStatus Ljava/lang/String;
      // 117a: aload 1
      // 117b: invokevirtual ai/comma/cereal/Log$DeviceState$Reader.getNetworkType ()Lai/comma/cereal/Log$DeviceState$NetworkType;
      // 117e: invokevirtual ai/comma/cereal/Log$DeviceState$NetworkType.toString ()Ljava/lang/String;
      // 1181: getstatic java/util/Locale.ROOT Ljava/util/Locale;
      // 1184: invokevirtual java/lang/String.toLowerCase (Ljava/util/Locale;)Ljava/lang/String;
      // 1187: astore 34
      // 1189: aload 34
      // 118b: ldc_w "toLowerCase(...)"
      // 118e: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 1191: aload 0
      // 1192: aload 34
      // 1194: putfield com/starpilot/starview/Telemetry.networkType Ljava/lang/String;
      // 1197: aload 0
      // 1198: aload 1
      // 1199: invokevirtual ai/comma/cereal/Log$DeviceState$Reader.getMaxTempC ()F
      // 119c: putfield com/starpilot/starview/Telemetry.maxTempC F
      // 119f: aload 0
      // 11a0: aload 1
      // 11a1: invokevirtual ai/comma/cereal/Log$DeviceState$Reader.getNetworkStrength ()Lai/comma/cereal/Log$DeviceState$NetworkStrength;
      // 11a4: invokevirtual ai/comma/cereal/Log$DeviceState$NetworkStrength.ordinal ()I
      // 11a7: putfield com/starpilot/starview/Telemetry.networkStrength I
      // 11aa: aload 0
      // 11ab: aload 1
      // 11ac: invokevirtual ai/comma/cereal/Log$DeviceState$Reader.getLastAthenaPingTime ()J
      // 11af: putfield com/starpilot/starview/Telemetry.lastAthenaPing J
      // 11b2: aload 1
      // 11b3: invokevirtual ai/comma/cereal/Log$DeviceState$Reader.getChestnutPresent ()Z
      // 11b6: istore 23
      // 11b8: goto 11bf
      // 11bb: astore 1
      // 11bc: bipush 0
      // 11bd: istore 23
      // 11bf: aload 0
      // 11c0: iload 23
      // 11c2: putfield com/starpilot/starview/Telemetry.chestnutPresent Z
      // 11c5: aload 0
      // 11c6: getfield com/starpilot/starview/Telemetry.started Z
      // 11c9: ifne 11d1
      // 11cc: aload 0
      // 11cd: bipush 0
      // 11ce: putfield com/starpilot/starview/Telemetry.modelSeen Z
      // 11d1: getstatic com/starpilot/starview/HealthHistory.INSTANCE Lcom/starpilot/starview/HealthHistory;
      // 11d4: aload 0
      // 11d5: getfield com/starpilot/starview/Telemetry.memUsage I
      // 11d8: aload 0
      // 11d9: getfield com/starpilot/starview/Telemetry.cpuTempC F
      // 11dc: invokevirtual com/starpilot/starview/HealthHistory.add (IF)V
      // 11df: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 11e2: astore 1
      // 11e3: goto 16e1
      // 11e6: aload 1
      // 11e7: invokevirtual ai/comma/cereal/Log$Event$Reader.getLongitudinalPlan ()Lai/comma/cereal/Log$LongitudinalPlan$Reader;
      // 11ea: astore 1
      // 11eb: aload 0
      // 11ec: aload 1
      // 11ed: invokevirtual ai/comma/cereal/Log$LongitudinalPlan$Reader.getAllowThrottle ()Z
      // 11f0: putfield com/starpilot/starview/Telemetry.allowThrottle Z
      // 11f3: aload 0
      // 11f4: aload 1
      // 11f5: invokevirtual ai/comma/cereal/Log$LongitudinalPlan$Reader.getATarget ()F
      // 11f8: putfield com/starpilot/starview/Telemetry.aTarget F
      // 11fb: aload 0
      // 11fc: aload 1
      // 11fd: invokevirtual ai/comma/cereal/Log$LongitudinalPlan$Reader.getFcw ()Z
      // 1200: putfield com/starpilot/starview/Telemetry.fcw Z
      // 1203: aload 1
      // 1204: invokevirtual ai/comma/cereal/Log$LongitudinalPlan$Reader.getLongitudinalPlanSource ()Lai/comma/cereal/Log$LongitudinalPlan$LongitudinalPlanSource;
      // 1207: invokevirtual ai/comma/cereal/Log$LongitudinalPlan$LongitudinalPlanSource.toString ()Ljava/lang/String;
      // 120a: getstatic java/util/Locale.ROOT Ljava/util/Locale;
      // 120d: invokevirtual java/lang/String.toLowerCase (Ljava/util/Locale;)Ljava/lang/String;
      // 1210: astore 1
      // 1211: aload 1
      // 1212: ldc_w "toLowerCase(...)"
      // 1215: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 1218: aload 0
      // 1219: aload 1
      // 121a: putfield com/starpilot/starview/Telemetry.planSource Ljava/lang/String;
      // 121d: aload 0
      // 121e: getfield com/starpilot/starview/Telemetry.enabled Z
      // 1221: istore 30
      // 1223: aload 0
      // 1224: getfield com/starpilot/starview/Telemetry.vEgo F
      // 1227: fstore 11
      // 1229: aload 0
      // 122a: getfield com/starpilot/starview/Telemetry.aTarget F
      // 122d: fstore 16
      // 122f: aload 0
      // 1230: getfield com/starpilot/starview/Telemetry.actAccel F
      // 1233: fstore 15
      // 1235: aload 0
      // 1236: getfield com/starpilot/starview/Telemetry.brakePressed Z
      // 1239: istore 24
      // 123b: aload 0
      // 123c: getfield com/starpilot/starview/Telemetry.gasPressed Z
      // 123f: istore 26
      // 1241: aload 0
      // 1242: getfield com/starpilot/starview/Telemetry.planSource Ljava/lang/String;
      // 1245: astore 36
      // 1247: aload 0
      // 1248: getfield com/starpilot/starview/Telemetry.fcw Z
      // 124b: istore 28
      // 124d: aload 0
      // 124e: getfield com/starpilot/starview/Telemetry.leadOne Lcom/starpilot/starview/Lead;
      // 1251: astore 1
      // 1252: aload 0
      // 1253: getfield com/starpilot/starview/Telemetry.leadTwo Lcom/starpilot/starview/Lead;
      // 1256: astore 34
      // 1258: aload 0
      // 1259: getfield com/starpilot/starview/Telemetry.experimentalMode Z
      // 125c: istore 29
      // 125e: aload 0
      // 125f: getfield com/starpilot/starview/Telemetry.forcingStop Z
      // 1262: istore 23
      // 1264: aload 0
      // 1265: getfield com/starpilot/starview/Telemetry.forcingStopLength F
      // 1268: fstore 9
      // 126a: aload 0
      // 126b: getfield com/starpilot/starview/Telemetry.redLight Z
      // 126e: istore 31
      // 1270: aload 0
      // 1271: getfield com/starpilot/starview/Telemetry.stopSignConfirmed Z
      // 1274: istore 25
      // 1276: aload 0
      // 1277: getfield com/starpilot/starview/Telemetry.approachStopLength F
      // 127a: fstore 12
      // 127c: aload 0
      // 127d: getfield com/starpilot/starview/Telemetry.cscControlling Z
      // 1280: istore 27
      // 1282: aload 0
      // 1283: getfield com/starpilot/starview/Telemetry.cscSpeed F
      // 1286: fstore 14
      // 1288: aload 0
      // 1289: getfield com/starpilot/starview/Telemetry.slcSpeedLimit F
      // 128c: fstore 13
      // 128e: aload 0
      // 128f: getfield com/starpilot/starview/Telemetry.slcOffset F
      // 1292: fstore 17
      // 1294: aload 0
      // 1295: getfield com/starpilot/starview/Telemetry.slcSource Ljava/lang/String;
      // 1298: astore 35
      // 129a: aload 0
      // 129b: getfield com/starpilot/starview/Telemetry.planVCruise F
      // 129e: fstore 10
      // 12a0: aload 0
      // 12a1: getfield com/starpilot/starview/Telemetry.vCruise F
      // 12a4: fstore 8
      // 12a6: fconst_1
      // 12a7: fload 8
      // 12a9: fcmpg
      // 12aa: ifgt 12bc
      // 12ad: fload 8
      // 12af: ldc_w 250.0
      // 12b2: fcmpg
      // 12b3: ifgt 12bc
      // 12b6: bipush 1
      // 12b7: istore 18
      // 12b9: goto 12bf
      // 12bc: bipush 0
      // 12bd: istore 18
      // 12bf: iload 18
      // 12c1: ifeq 12d1
      // 12c4: aload 0
      // 12c5: getfield com/starpilot/starview/Telemetry.vCruise F
      // 12c8: ldc_w 3.6
      // 12cb: fdiv
      // 12cc: fstore 8
      // 12ce: goto 12d7
      // 12d1: aload 0
      // 12d2: getfield com/starpilot/starview/Telemetry.cruiseSpeed F
      // 12d5: fstore 8
      // 12d7: new com/starpilot/starview/SlowReason$In
      // 12da: dup
      // 12db: iload 30
      // 12dd: fload 11
      // 12df: fload 16
      // 12e1: fload 15
      // 12e3: iload 24
      // 12e5: iload 26
      // 12e7: aload 36
      // 12e9: iload 28
      // 12eb: aload 1
      // 12ec: aload 34
      // 12ee: iload 29
      // 12f0: iload 23
      // 12f2: fload 9
      // 12f4: iload 31
      // 12f6: iload 25
      // 12f8: fload 12
      // 12fa: iload 27
      // 12fc: fload 14
      // 12fe: fload 13
      // 1300: fload 17
      // 1302: aload 35
      // 1304: fload 10
      // 1306: fload 8
      // 1308: aload 0
      // 1309: getfield com/starpilot/starview/Telemetry.pulseGlideCoasting Z
      // 130c: aload 0
      // 130d: getfield com/starpilot/starview/Telemetry.forceCoast Z
      // 1310: aload 0
      // 1311: invokevirtual com/starpilot/starview/Telemetry.isMetric ()Z
      // 1314: aload 0
      // 1315: getfield com/starpilot/starview/Telemetry.trackingLead Z
      // 1318: invokespecial com/starpilot/starview/SlowReason$In.<init> (ZFFFZZLjava/lang/String;ZLcom/starpilot/starview/Lead;Lcom/starpilot/starview/Lead;ZZFZZFZFFFLjava/lang/String;FFZZZZ)V
      // 131b: astore 34
      // 131d: aload 0
      // 131e: getfield com/starpilot/starview/Telemetry.slow Lcom/starpilot/starview/SlowReason$Tracker;
      // 1321: astore 1
      // 1322: aload 1
      // 1323: monitorenter
      // 1324: aload 0
      // 1325: getfield com/starpilot/starview/Telemetry.slow Lcom/starpilot/starview/SlowReason$Tracker;
      // 1328: aload 34
      // 132a: invokestatic java/lang/System.currentTimeMillis ()J
      // 132d: invokevirtual com/starpilot/starview/SlowReason$Tracker.update (Lcom/starpilot/starview/SlowReason$In;J)V
      // 1330: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 1333: astore 34
      // 1335: aload 1
      // 1336: monitorexit
      // 1337: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 133a: astore 1
      // 133b: goto 16e1
      // 133e: astore 34
      // 1340: aload 1
      // 1341: monitorexit
      // 1342: aload 34
      // 1344: athrow
      // 1345: aload 1
      // 1346: invokevirtual ai/comma/cereal/Log$Event$Reader.getLiveParameters ()Lai/comma/cereal/Log$LiveParametersData$Reader;
      // 1349: astore 1
      // 134a: aload 0
      // 134b: aload 1
      // 134c: invokevirtual ai/comma/cereal/Log$LiveParametersData$Reader.getRoll ()F
      // 134f: putfield com/starpilot/starview/Telemetry.roll F
      // 1352: aload 0
      // 1353: aload 1
      // 1354: invokevirtual ai/comma/cereal/Log$LiveParametersData$Reader.getAngleOffsetDeg ()F
      // 1357: putfield com/starpilot/starview/Telemetry.angleOffsetDeg F
      // 135a: aload 1
      // 135b: invokevirtual ai/comma/cereal/Log$LiveParametersData$Reader.getSteerRatio ()F
      // 135e: ldc_w 5.0
      // 1361: fcmpl
      // 1362: ifle 136d
      // 1365: aload 0
      // 1366: aload 1
      // 1367: invokevirtual ai/comma/cereal/Log$LiveParametersData$Reader.getSteerRatio ()F
      // 136a: putfield com/starpilot/starview/Telemetry.steerRatioLive F
      // 136d: aload 0
      // 136e: aload 1
      // 136f: invokevirtual ai/comma/cereal/Log$LiveParametersData$Reader.getStiffnessFactor ()F
      // 1372: putfield com/starpilot/starview/Telemetry.stiffness F
      // 1375: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 1378: astore 1
      // 1379: goto 16e1
      // 137c: aload 0
      // 137d: aload 1
      // 137e: invokevirtual ai/comma/cereal/Log$Event$Reader.getCarOutput ()Lai/comma/cereal/Car$CarOutput$Reader;
      // 1381: invokevirtual ai/comma/cereal/Car$CarOutput$Reader.getActuatorsOutput ()Lai/comma/cereal/Car$CarControl$Actuators$Reader;
      // 1384: invokevirtual ai/comma/cereal/Car$CarControl$Actuators$Reader.getTorque ()F
      // 1387: putfield com/starpilot/starview/Telemetry.outTorque F
      // 138a: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 138d: astore 1
      // 138e: goto 16e1
      // 1391: aload 1
      // 1392: invokevirtual ai/comma/cereal/Log$Event$Reader.getCarControl ()Lai/comma/cereal/Car$CarControl$Reader;
      // 1395: astore 34
      // 1397: aload 34
      // 1399: invokevirtual ai/comma/cereal/Car$CarControl$Reader.getActuators ()Lai/comma/cereal/Car$CarControl$Actuators$Reader;
      // 139c: astore 1
      // 139d: aload 0
      // 139e: aload 1
      // 139f: invokevirtual ai/comma/cereal/Car$CarControl$Actuators$Reader.getTorque ()F
      // 13a2: putfield com/starpilot/starview/Telemetry.actTorque F
      // 13a5: aload 0
      // 13a6: aload 1
      // 13a7: invokevirtual ai/comma/cereal/Car$CarControl$Actuators$Reader.getAccel ()F
      // 13aa: putfield com/starpilot/starview/Telemetry.actAccel F
      // 13ad: aload 0
      // 13ae: aload 34
      // 13b0: invokevirtual ai/comma/cereal/Car$CarControl$Reader.getLatActive ()Z
      // 13b3: putfield com/starpilot/starview/Telemetry.latActive Z
      // 13b6: aload 0
      // 13b7: aload 34
      // 13b9: invokevirtual ai/comma/cereal/Car$CarControl$Reader.getLongActive ()Z
      // 13bc: putfield com/starpilot/starview/Telemetry.longActive Z
      // 13bf: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 13c2: astore 1
      // 13c3: goto 16e1
      // 13c6: aload 1
      // 13c7: invokevirtual ai/comma/cereal/Log$Event$Reader.getControlsState ()Lai/comma/cereal/Log$ControlsState$Reader;
      // 13ca: astore 1
      // 13cb: aload 0
      // 13cc: aload 1
      // 13cd: invokevirtual ai/comma/cereal/Log$ControlsState$Reader.getCurvature ()F
      // 13d0: putfield com/starpilot/starview/Telemetry.curvature F
      // 13d3: aload 0
      // 13d4: aload 1
      // 13d5: invokevirtual ai/comma/cereal/Log$ControlsState$Reader.getDesiredCurvature ()F
      // 13d8: putfield com/starpilot/starview/Telemetry.desiredCurvature F
      // 13db: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 13de: astore 1
      // 13df: goto 16e1
      // 13e2: aload 1
      // 13e3: invokevirtual ai/comma/cereal/Log$Event$Reader.getSelfdriveState ()Lai/comma/cereal/Log$SelfdriveState$Reader;
      // 13e6: astore 1
      // 13e7: aload 0
      // 13e8: aload 1
      // 13e9: invokevirtual ai/comma/cereal/Log$SelfdriveState$Reader.getEnabled ()Z
      // 13ec: putfield com/starpilot/starview/Telemetry.enabled Z
      // 13ef: aload 0
      // 13f0: aload 1
      // 13f1: invokevirtual ai/comma/cereal/Log$SelfdriveState$Reader.getActive ()Z
      // 13f4: putfield com/starpilot/starview/Telemetry.active Z
      // 13f7: aload 0
      // 13f8: aload 1
      // 13f9: invokevirtual ai/comma/cereal/Log$SelfdriveState$Reader.getEngageable ()Z
      // 13fc: putfield com/starpilot/starview/Telemetry.engageable Z
      // 13ff: aload 0
      // 1400: aload 1
      // 1401: invokevirtual ai/comma/cereal/Log$SelfdriveState$Reader.getExperimentalMode ()Z
      // 1404: putfield com/starpilot/starview/Telemetry.experimentalMode Z
      // 1407: aload 1
      // 1408: invokevirtual ai/comma/cereal/Log$SelfdriveState$Reader.getState ()Lai/comma/cereal/Log$SelfdriveState$OpenpilotState;
      // 140b: invokevirtual ai/comma/cereal/Log$SelfdriveState$OpenpilotState.toString ()Ljava/lang/String;
      // 140e: getstatic java/util/Locale.ROOT Ljava/util/Locale;
      // 1411: invokevirtual java/lang/String.toLowerCase (Ljava/util/Locale;)Ljava/lang/String;
      // 1414: astore 34
      // 1416: aload 34
      // 1418: ldc_w "toLowerCase(...)"
      // 141b: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 141e: aload 0
      // 141f: aload 34
      // 1421: putfield com/starpilot/starview/Telemetry.opState Ljava/lang/String;
      // 1424: aload 0
      // 1425: aload 1
      // 1426: invokevirtual ai/comma/cereal/Log$SelfdriveState$Reader.getAlertText1 ()Lorg/capnproto/Text$Reader;
      // 1429: invokevirtual org/capnproto/Text$Reader.toString ()Ljava/lang/String;
      // 142c: putfield com/starpilot/starview/Telemetry.alertText1 Ljava/lang/String;
      // 142f: aload 0
      // 1430: aload 1
      // 1431: invokevirtual ai/comma/cereal/Log$SelfdriveState$Reader.getAlertText2 ()Lorg/capnproto/Text$Reader;
      // 1434: invokevirtual org/capnproto/Text$Reader.toString ()Ljava/lang/String;
      // 1437: putfield com/starpilot/starview/Telemetry.alertText2 Ljava/lang/String;
      // 143a: aload 1
      // 143b: invokevirtual ai/comma/cereal/Log$SelfdriveState$Reader.getAlertSize ()Lai/comma/cereal/Log$SelfdriveState$AlertSize;
      // 143e: invokevirtual ai/comma/cereal/Log$SelfdriveState$AlertSize.toString ()Ljava/lang/String;
      // 1441: getstatic java/util/Locale.ROOT Ljava/util/Locale;
      // 1444: invokevirtual java/lang/String.toLowerCase (Ljava/util/Locale;)Ljava/lang/String;
      // 1447: astore 34
      // 1449: aload 34
      // 144b: ldc_w "toLowerCase(...)"
      // 144e: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 1451: aload 0
      // 1452: aload 34
      // 1454: putfield com/starpilot/starview/Telemetry.alertSize Ljava/lang/String;
      // 1457: aload 1
      // 1458: invokevirtual ai/comma/cereal/Log$SelfdriveState$Reader.getAlertStatus ()Lai/comma/cereal/Log$SelfdriveState$AlertStatus;
      // 145b: invokevirtual ai/comma/cereal/Log$SelfdriveState$AlertStatus.toString ()Ljava/lang/String;
      // 145e: getstatic java/util/Locale.ROOT Ljava/util/Locale;
      // 1461: invokevirtual java/lang/String.toLowerCase (Ljava/util/Locale;)Ljava/lang/String;
      // 1464: astore 34
      // 1466: aload 34
      // 1468: ldc_w "toLowerCase(...)"
      // 146b: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 146e: aload 0
      // 146f: aload 34
      // 1471: putfield com/starpilot/starview/Telemetry.alertStatus Ljava/lang/String;
      // 1474: aload 1
      // 1475: invokevirtual ai/comma/cereal/Log$SelfdriveState$Reader.getPersonality ()Lai/comma/cereal/Log$LongitudinalPersonality;
      // 1478: invokevirtual ai/comma/cereal/Log$LongitudinalPersonality.toString ()Ljava/lang/String;
      // 147b: getstatic java/util/Locale.ROOT Ljava/util/Locale;
      // 147e: invokevirtual java/lang/String.toLowerCase (Ljava/util/Locale;)Ljava/lang/String;
      // 1481: astore 1
      // 1482: aload 1
      // 1483: ldc_w "toLowerCase(...)"
      // 1486: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 1489: aload 0
      // 148a: aload 1
      // 148b: putfield com/starpilot/starview/Telemetry.personality Ljava/lang/String;
      // 148e: getstatic com/starpilot/starview/DriveJournal.INSTANCE Lcom/starpilot/starview/DriveJournal;
      // 1491: aload 0
      // 1492: invokevirtual com/starpilot/starview/DriveJournal.onState (Lcom/starpilot/starview/Telemetry;)V
      // 1495: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 1498: astore 1
      // 1499: goto 16e1
      // 149c: aload 1
      // 149d: invokevirtual ai/comma/cereal/Log$Event$Reader.getCarState ()Lai/comma/cereal/Car$CarState$Reader;
      // 14a0: astore 34
      // 14a2: aload 0
      // 14a3: aload 34
      // 14a5: invokevirtual ai/comma/cereal/Car$CarState$Reader.getVEgo ()F
      // 14a8: putfield com/starpilot/starview/Telemetry.vEgo F
      // 14ab: aload 0
      // 14ac: aload 34
      // 14ae: invokevirtual ai/comma/cereal/Car$CarState$Reader.getVEgoCluster ()F
      // 14b1: putfield com/starpilot/starview/Telemetry.vEgoCluster F
      // 14b4: aload 0
      // 14b5: aload 34
      // 14b7: invokevirtual ai/comma/cereal/Car$CarState$Reader.getAEgo ()F
      // 14ba: putfield com/starpilot/starview/Telemetry.aEgo F
      // 14bd: aload 0
      // 14be: aload 34
      // 14c0: invokevirtual ai/comma/cereal/Car$CarState$Reader.getYawRate ()F
      // 14c3: putfield com/starpilot/starview/Telemetry.yawRate F
      // 14c6: aload 0
      // 14c7: aload 34
      // 14c9: invokevirtual ai/comma/cereal/Car$CarState$Reader.getSteeringAngleDeg ()F
      // 14cc: putfield com/starpilot/starview/Telemetry.steeringAngleDeg F
      // 14cf: getstatic com/starpilot/starview/Odometry.INSTANCE Lcom/starpilot/starview/Odometry;
      // 14d2: aload 1
      // 14d3: invokevirtual ai/comma/cereal/Log$Event$Reader.getLogMonoTime ()J
      // 14d6: aload 0
      // 14d7: invokevirtual com/starpilot/starview/Odometry.onCarState (JLcom/starpilot/starview/Telemetry;)V
      // 14da: getstatic com/starpilot/starview/GpsBridge.INSTANCE Lcom/starpilot/starview/GpsBridge;
      // 14dd: astore 35
      // 14df: aload 0
      // 14e0: getfield com/starpilot/starview/Telemetry.gear Ljava/lang/String;
      // 14e3: ldc_w "reverse"
      // 14e6: invokestatic kotlin/jvm/internal/Intrinsics.areEqual (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 14e9: ifeq 14f6
      // 14ec: aload 0
      // 14ed: getfield com/starpilot/starview/Telemetry.vEgo F
      // 14f0: fneg
      // 14f1: fstore 8
      // 14f3: goto 14fc
      // 14f6: aload 0
      // 14f7: getfield com/starpilot/starview/Telemetry.vEgo F
      // 14fa: fstore 8
      // 14fc: aload 35
      // 14fe: fload 8
      // 1500: invokevirtual com/starpilot/starview/GpsBridge.onCarState (F)V
      // 1503: goto 1508
      // 1506: astore 35
      // 1508: aload 0
      // 1509: ldc_w "carState"
      // 150c: invokevirtual com/starpilot/starview/Telemetry.tick (Ljava/lang/String;)V
      // 150f: aload 0
      // 1510: getfield com/starpilot/starview/Telemetry.turnCsMono J
      // 1513: lconst_0
      // 1514: lcmp
      // 1515: ifne 151e
      // 1518: dload 4
      // 151a: dstore 2
      // 151b: goto 154b
      // 151e: aload 1
      // 151f: invokevirtual ai/comma/cereal/Log$Event$Reader.getLogMonoTime ()J
      // 1522: aload 0
      // 1523: getfield com/starpilot/starview/Telemetry.turnCsMono J
      // 1526: lsub
      // 1527: l2d
      // 1528: ldc2_w 1.0E9
      // 152b: ddiv
      // 152c: dstore 6
      // 152e: dload 4
      // 1530: dstore 2
      // 1531: dload 6
      // 1533: dconst_0
      // 1534: dcmpg
      // 1535: iflt 151b
      // 1538: dload 6
      // 153a: dconst_1
      // 153b: dcmpl
      // 153c: ifle 1545
      // 153f: dload 4
      // 1541: dstore 2
      // 1542: goto 151b
      // 1545: dload 6
      // 1547: dstore 2
      // 1548: goto 151b
      // 154b: aload 0
      // 154c: aload 1
      // 154d: invokevirtual ai/comma/cereal/Log$Event$Reader.getLogMonoTime ()J
      // 1550: putfield com/starpilot/starview/Telemetry.turnCsMono J
      // 1553: aload 0
      // 1554: getfield com/starpilot/starview/Telemetry.vEgo F
      // 1557: aload 0
      // 1558: getfield com/starpilot/starview/Telemetry.TURN_MIN_V F
      // 155b: fcmpl
      // 155c: ifle 15c5
      // 155f: aload 0
      // 1560: getfield com/starpilot/starview/Telemetry.gear Ljava/lang/String;
      // 1563: ldc_w "reverse"
      // 1566: invokestatic kotlin/jvm/internal/Intrinsics.areEqual (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 1569: ifne 15c5
      // 156c: aload 0
      // 156d: getfield com/starpilot/starview/Telemetry.steerRatioLive F
      // 1570: ldc_w 5.0
      // 1573: fcmpl
      // 1574: ifle 1581
      // 1577: aload 0
      // 1578: getfield com/starpilot/starview/Telemetry.steerRatioLive F
      // 157b: f2d
      // 157c: dstore 4
      // 157e: goto 1586
      // 1581: ldc2_w 15.5
      // 1584: dstore 4
      // 1586: aload 0
      // 1587: getfield com/starpilot/starview/Telemetry.vEgo F
      // 158a: f2d
      // 158b: dstore 6
      // 158d: aload 0
      // 158e: aload 0
      // 158f: getfield com/starpilot/starview/Telemetry.turnSteerDeg D
      // 1592: aload 0
      // 1593: getfield com/starpilot/starview/Telemetry.steeringAngleDeg F
      // 1596: aload 0
      // 1597: getfield com/starpilot/starview/Telemetry.angleOffsetDeg F
      // 159a: fsub
      // 159b: f2d
      // 159c: invokestatic java/lang/Math.toRadians (D)D
      // 159f: dload 4
      // 15a1: ddiv
      // 15a2: invokestatic java/lang/Math.tan (D)D
      // 15a5: dload 6
      // 15a7: dmul
      // 15a8: ldc2_w 3.03
      // 15ab: ddiv
      // 15ac: bipush 1
      // 15ad: i2d
      // 15ae: dload 6
      // 15b0: dload 6
      // 15b2: dmul
      // 15b3: ldc2_w 900.0
      // 15b6: ddiv
      // 15b7: dadd
      // 15b8: ddiv
      // 15b9: dload 2
      // 15ba: dmul
      // 15bb: invokestatic java/lang/Math.toDegrees (D)D
      // 15be: dadd
      // 15bf: putfield com/starpilot/starview/Telemetry.turnSteerDeg D
      // 15c2: goto 15c5
      // 15c5: bipush 1
      // 15c6: istore 18
      // 15c8: aload 0
      // 15c9: aload 34
      // 15cb: invokevirtual ai/comma/cereal/Car$CarState$Reader.getGasPressed ()Z
      // 15ce: putfield com/starpilot/starview/Telemetry.gasPressed Z
      // 15d1: aload 0
      // 15d2: aload 34
      // 15d4: invokevirtual ai/comma/cereal/Car$CarState$Reader.getBrakePressed ()Z
      // 15d7: putfield com/starpilot/starview/Telemetry.brakePressed Z
      // 15da: aload 0
      // 15db: aload 34
      // 15dd: invokevirtual ai/comma/cereal/Car$CarState$Reader.getSteeringPressed ()Z
      // 15e0: putfield com/starpilot/starview/Telemetry.steeringPressed Z
      // 15e3: aload 0
      // 15e4: aload 34
      // 15e6: invokevirtual ai/comma/cereal/Car$CarState$Reader.getSteeringTorque ()F
      // 15e9: putfield com/starpilot/starview/Telemetry.steeringTorque F
      // 15ec: aload 0
      // 15ed: aload 34
      // 15ef: invokevirtual ai/comma/cereal/Car$CarState$Reader.getLeftBlinker ()Z
      // 15f2: putfield com/starpilot/starview/Telemetry.leftBlinker Z
      // 15f5: aload 0
      // 15f6: aload 34
      // 15f8: invokevirtual ai/comma/cereal/Car$CarState$Reader.getRightBlinker ()Z
      // 15fb: putfield com/starpilot/starview/Telemetry.rightBlinker Z
      // 15fe: aload 0
      // 15ff: aload 34
      // 1601: invokevirtual ai/comma/cereal/Car$CarState$Reader.getLeftBlindspot ()Z
      // 1604: putfield com/starpilot/starview/Telemetry.leftBlindspot Z
      // 1607: aload 0
      // 1608: aload 34
      // 160a: invokevirtual ai/comma/cereal/Car$CarState$Reader.getRightBlindspot ()Z
      // 160d: putfield com/starpilot/starview/Telemetry.rightBlindspot Z
      // 1610: aload 0
      // 1611: aload 34
      // 1613: invokevirtual ai/comma/cereal/Car$CarState$Reader.getStandstill ()Z
      // 1616: putfield com/starpilot/starview/Telemetry.standstill Z
      // 1619: aload 34
      // 161b: invokevirtual ai/comma/cereal/Car$CarState$Reader.getGearShifter ()Lai/comma/cereal/Car$CarState$GearShifter;
      // 161e: invokevirtual ai/comma/cereal/Car$CarState$GearShifter.toString ()Ljava/lang/String;
      // 1621: getstatic java/util/Locale.ROOT Ljava/util/Locale;
      // 1624: invokevirtual java/lang/String.toLowerCase (Ljava/util/Locale;)Ljava/lang/String;
      // 1627: astore 1
      // 1628: aload 1
      // 1629: ldc_w "toLowerCase(...)"
      // 162c: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 162f: aload 0
      // 1630: aload 1
      // 1631: putfield com/starpilot/starview/Telemetry.gear Ljava/lang/String;
      // 1634: aload 0
      // 1635: aload 34
      // 1637: invokevirtual ai/comma/cereal/Car$CarState$Reader.getVCruiseCluster ()F
      // 163a: putfield com/starpilot/starview/Telemetry.vCruiseCluster F
      // 163d: aload 0
      // 163e: aload 34
      // 1640: invokevirtual ai/comma/cereal/Car$CarState$Reader.getVCruise ()F
      // 1643: putfield com/starpilot/starview/Telemetry.vCruise F
      // 1646: aload 34
      // 1648: invokevirtual ai/comma/cereal/Car$CarState$Reader.getCruiseState ()Lai/comma/cereal/Car$CarState$CruiseState$Reader;
      // 164b: astore 1
      // 164c: aload 0
      // 164d: aload 1
      // 164e: invokevirtual ai/comma/cereal/Car$CarState$CruiseState$Reader.getEnabled ()Z
      // 1651: putfield com/starpilot/starview/Telemetry.cruiseEnabled Z
      // 1654: aload 0
      // 1655: aload 1
      // 1656: invokevirtual ai/comma/cereal/Car$CarState$CruiseState$Reader.getSpeed ()F
      // 1659: putfield com/starpilot/starview/Telemetry.cruiseSpeed F
      // 165c: aload 0
      // 165d: getfield com/starpilot/starview/Telemetry.started Z
      // 1660: ifeq 16b5
      // 1663: aload 0
      // 1664: getfield com/starpilot/starview/Telemetry.gasPressed Z
      // 1667: ifne 16b5
      // 166a: aload 0
      // 166b: getfield com/starpilot/starview/Telemetry.aEgo F
      // 166e: fstore 8
      // 1670: fload 8
      // 1672: invokestatic java/lang/Float.isInfinite (F)Z
      // 1675: ifne 1683
      // 1678: fload 8
      // 167a: invokestatic java/lang/Float.isNaN (F)Z
      // 167d: ifne 1683
      // 1680: goto 1686
      // 1683: bipush 0
      // 1684: istore 18
      // 1686: iload 18
      // 1688: ifeq 16b5
      // 168b: aload 0
      // 168c: getfield com/starpilot/starview/Telemetry.maxAccel F
      // 168f: invokestatic java/lang/Float.isNaN (F)Z
      // 1692: ifeq 16a2
      // 1695: fconst_0
      // 1696: aload 0
      // 1697: getfield com/starpilot/starview/Telemetry.aEgo F
      // 169a: invokestatic java/lang/Math.max (FF)F
      // 169d: fstore 8
      // 169f: goto 16af
      // 16a2: aload 0
      // 16a3: getfield com/starpilot/starview/Telemetry.maxAccel F
      // 16a6: aload 0
      // 16a7: getfield com/starpilot/starview/Telemetry.aEgo F
      // 16aa: invokestatic java/lang/Math.max (FF)F
      // 16ad: fstore 8
      // 16af: aload 0
      // 16b0: fload 8
      // 16b2: putfield com/starpilot/starview/Telemetry.maxAccel F
      // 16b5: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 16b8: astore 1
      // 16b9: goto 16e1
      // 16bc: aload 1
      // 16bd: invokestatic org/capnproto/SlcPeek.slcState (Lorg/capnproto/StructReader;)Lorg/capnproto/SlcPeek$Slc;
      // 16c0: astore 1
      // 16c1: aload 1
      // 16c2: ifnull 16e1
      // 16c5: aload 0
      // 16c6: aload 1
      // 16c7: putfield com/starpilot/starview/Telemetry.slc Lorg/capnproto/SlcPeek$Slc;
      // 16ca: aload 0
      // 16cb: invokestatic java/lang/System.nanoTime ()J
      // 16ce: putfield com/starpilot/starview/Telemetry.slcAtNs J
      // 16d1: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 16d4: astore 1
      // 16d5: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 16d8: astore 1
      // 16d9: goto 16e1
      // 16dc: astore 1
      // 16dd: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 16e0: astore 1
      // 16e1: return
   }

   public final void applyParams(JSONObject var1) {
      Intrinsics.checkNotNullParameter(var1, "j");
      this.params = applyParams$toMap(var1.optJSONObject("p"));
      this.memParams = applyParams$toMap(var1.optJSONObject("m"));
      this.info = applyParams$toMap(var1.optJSONObject("i"));
      Replay.INSTANCE.update(var1.optJSONObject("r"));
      Ntrip.INSTANCE.update(var1.optJSONObject("k"));
      this.paramsGeneration++;
   }

   public final void count(String var1, int var2) {
      Intrinsics.checkNotNullParameter(var1, "name");
      this.msgCount.merge(var1, 1L, new Telemetry$$ExternalSyntheticLambda0(<unrepresentable>.INSTANCE));
      this.byteCount.merge(var1, (long)var2, new Telemetry$$ExternalSyntheticLambda1(<unrepresentable>.INSTANCE));
   }

   public final float f(String var1, float var2) {
      Intrinsics.checkNotNullParameter(var1, "key");
      if (this.ui != null) {
         var2 = this.getUi().getFloat(var1, var2);
      }

      return var2;
   }

   public final float getAEgo() {
      return this.aEgo;
   }

   public final float getATarget() {
      return this.aTarget;
   }

   public final float[] getAccelRaw() {
      return this.accelRaw;
   }

   public final String getAccelSrc() {
      return this.accelSrc;
   }

   public final float getActAccel() {
      return this.actAccel;
   }

   public final float getActTorque() {
      return this.actTorque;
   }

   public final boolean getActive() {
      return this.active;
   }

   public final String getAlertSize() {
      return this.alertSize;
   }

   public final String getAlertStatus() {
      return this.alertStatus;
   }

   public final String getAlertText1() {
      return this.alertText1;
   }

   public final String getAlertText2() {
      return this.alertText2;
   }

   public final boolean getAllowThrottle() {
      return this.allowThrottle;
   }

   public final float getAngleOffsetDeg() {
      return this.angleOffsetDeg;
   }

   public final boolean getAolEnabled() {
      return this.aolEnabled;
   }

   public final float getApproachStopLength() {
      return this.approachStopLength;
   }

   public final float getBaseDelay() {
      return this.baseDelay;
   }

   public final float getBaseFriction() {
      return this.baseFriction;
   }

   public final float getBaseLatAccel() {
      return this.baseLatAccel;
   }

   public final float getBearingDeg() {
      return this.bearingDeg;
   }

   public final boolean getBrakeLights() {
      return this.brakeLights;
   }

   public final boolean getBrakePressed() {
      return this.brakePressed;
   }

   public final ConcurrentHashMap<String, Long> getByteCount() {
      return this.byteCount;
   }

   public final long getCalFrame() {
      return this.calFrame;
   }

   public final float getCalHeight() {
      return this.calHeight;
   }

   public final float[] getCalRpy() {
      return this.calRpy;
   }

   public final boolean getCalValid() {
      return this.calValid;
   }

   public final boolean getChestnutPresent() {
      return this.chestnutPresent;
   }

   public final float getCpuTempC() {
      return this.cpuTempC;
   }

   public final float getCpuUsage() {
      return this.cpuUsage;
   }

   public final boolean getCruiseEnabled() {
      return this.cruiseEnabled;
   }

   public final float getCruiseSpeed() {
      return this.cruiseSpeed;
   }

   public final boolean getCscControlling() {
      return this.cscControlling;
   }

   public final float getCscSpeed() {
      return this.cscSpeed;
   }

   public final boolean getCscTraining() {
      return this.cscTraining;
   }

   public final float getCurvature() {
      return this.curvature;
   }

   public final float getDashSpeedLimit() {
      return this.dashSpeedLimit;
   }

   public final long getDelayAtNs() {
      return this.delayAtNs;
   }

   public final float getDesiredCurvature() {
      return this.desiredCurvature;
   }

   public final long getDesiredFollowDistance() {
      return this.desiredFollowDistance;
   }

   public final String getDeviceType() {
      return this.deviceType;
   }

   public final int getDmAwareness() {
      return this.dmAwareness;
   }

   public final boolean getDmFace() {
      return this.dmFace;
   }

   public final float getDmPitch() {
      return this.dmPitch;
   }

   public final boolean getDmPolicyVision() {
      return this.dmPolicyVision;
   }

   public final boolean getDmRhd() {
      return this.dmRhd;
   }

   public final float getDmYaw() {
      return this.dmYaw;
   }

   public final boolean getEnabled() {
      return this.enabled;
   }

   public final boolean getEngageable() {
      return this.engageable;
   }

   public final boolean getExperimentalMode() {
      return this.experimentalMode;
   }

   public final boolean getFcw() {
      return this.fcw;
   }

   public final boolean getForceCoast() {
      return this.forceCoast;
   }

   public final boolean getForcingStop() {
      return this.forcingStop;
   }

   public final float getForcingStopLength() {
      return this.forcingStopLength;
   }

   public final float getFreeSpacePct() {
      return this.freeSpacePct;
   }

   public final float getFrictionLearned() {
      return this.frictionLearned;
   }

   public final boolean getGasPressed() {
      return this.gasPressed;
   }

   public final String getGear() {
      return this.gear;
   }

   public final long getGnssAtNs() {
      return this.gnssAtNs;
   }

   public final List<GnssSat> getGnssSats() {
      return this.gnssSats;
   }

   public final ConcurrentHashMap<String, GpsFixInfo> getGpsFixes() {
      return this.gpsFixes;
   }

   public final double getGpsLat() {
      return this.gpsLat;
   }

   public final double getGpsLon() {
      return this.gpsLon;
   }

   public final float[] getGyroRaw() {
      return this.gyroRaw;
   }

   public final String getGyroSrc() {
      return this.gyroSrc;
   }

   public final Map<String, Object> getInfo() {
      return this.info;
   }

   public final float getLaneWidthLeft() {
      return this.laneWidthLeft;
   }

   public final float getLaneWidthRight() {
      return this.laneWidthRight;
   }

   public final long getLastAthenaPing() {
      return this.lastAthenaPing;
   }

   public final long getLastEventMonoNs() {
      return this.lastEventMonoNs;
   }

   public final long getLastModelRxNs() {
      return this.lastModelRxNs;
   }

   public final float getLatAccelLearned() {
      return this.latAccelLearned;
   }

   public final boolean getLatActive() {
      return this.latActive;
   }

   public final org.capnproto.SlcPeek.LaneCentering getLc() {
      return this.lc;
   }

   public final long getLcAtNs() {
      return this.lcAtNs;
   }

   public final Lead getLeadLeft() {
      return this.leadLeft;
   }

   public final Lead getLeadOne() {
      return this.leadOne;
   }

   public final Lead getLeadRight() {
      return this.leadRight;
   }

   public final Lead getLeadTwo() {
      return this.leadTwo;
   }

   public final float getLearnedDelay() {
      return this.learnedDelay;
   }

   public final boolean getLeftBlindspot() {
      return this.leftBlindspot;
   }

   public final boolean getLeftBlinker() {
      return this.leftBlinker;
   }

   public final boolean getLongActive() {
      return this.longActive;
   }

   public final long getMapdAtNs() {
      return this.mapdAtNs;
   }

   public final boolean getMapdFreeway() {
      return this.mapdFreeway;
   }

   public final int getMapdLanes() {
      return this.mapdLanes;
   }

   public final boolean getMapdMatched() {
      return this.mapdMatched;
   }

   public final boolean getMapdOneWay() {
      return this.mapdOneWay;
   }

   public final String getMapdWayName() {
      return this.mapdWayName;
   }

   public final String getMapdWayRef() {
      return this.mapdWayRef;
   }

   public final float getMaxAccel() {
      return this.maxAccel;
   }

   public final float getMaxTempC() {
      return this.maxTempC;
   }

   public final Map<String, Object> getMemParams() {
      return this.memParams;
   }

   public final int getMemUsage() {
      return this.memUsage;
   }

   public final ModelSnapshot getModel() {
      return this.model;
   }

   public final boolean getModelSeen() {
      return this.modelSeen;
   }

   public final ConcurrentHashMap<String, Long> getMsgCount() {
      return this.msgCount;
   }

   public final int getNetworkStrength() {
      return this.networkStrength;
   }

   public final String getNetworkType() {
      return this.networkType;
   }

   public final String getOpState() {
      return this.opState;
   }

   public final float getOutTorque() {
      return this.outTorque;
   }

   public final Map<String, Object> getParams() {
      return this.params;
   }

   public final int getParamsGeneration() {
      return this.paramsGeneration;
   }

   public final boolean getPauseLateral() {
      return this.pauseLateral;
   }

   public final boolean getPauseLongitudinal() {
      return this.pauseLongitudinal;
   }

   public final String getPersonality() {
      return this.personality;
   }

   public final String getPlanSource() {
      return this.planSource;
   }

   public final float getPlanVCruise() {
      return this.planVCruise;
   }

   public final float[] getPoseAccel() {
      return this.poseAccel;
   }

   public final long getPoseAtNs() {
      return this.poseAtNs;
   }

   public final float[] getPoseGyro() {
      return this.poseGyro;
   }

   public final boolean getPoseOK() {
      return this.poseOK;
   }

   public final boolean getPoseValid() {
      return this.poseValid;
   }

   public final float getPoseYawZ() {
      return this.poseYawZ;
   }

   public final boolean getPulseAndGlide() {
      return this.pulseAndGlide;
   }

   public final boolean getPulseGlideCoasting() {
      return this.pulseGlideCoasting;
   }

   public final RadarTrack[] getRadar() {
      return this.radar;
   }

   public final long getRadarFrame() {
      return this.radarFrame;
   }

   public final ConcurrentHashMap<String, Rate> getRates() {
      return this.rates;
   }

   public final boolean getRedLight() {
      return this.redLight;
   }

   public final boolean getRightBlindspot() {
      return this.rightBlindspot;
   }

   public final boolean getRightBlinker() {
      return this.rightBlinker;
   }

   public final float getRoadCurvature() {
      return this.roadCurvature;
   }

   public final String getRoadName() {
      return this.roadName;
   }

   public final float getRoll() {
      return this.roll;
   }

   public final double getRttMs() {
      return this.rttMs;
   }

   public final String getSensor() {
      return this.sensor;
   }

   public final Slc getSlc() {
      return this.slc;
   }

   public final long getSlcAtNs() {
      return this.slcAtNs;
   }

   public final boolean getSlcChanged() {
      return this.slcChanged;
   }

   public final float getSlcMapSpeedLimit() {
      return this.slcMapSpeedLimit;
   }

   public final float getSlcMapbox() {
      return this.slcMapbox;
   }

   public final float getSlcNext() {
      return this.slcNext;
   }

   public final float getSlcOffset() {
      return this.slcOffset;
   }

   public final float getSlcOverridden() {
      return this.slcOverridden;
   }

   public final String getSlcSource() {
      return this.slcSource;
   }

   public final float getSlcSpeedLimit() {
      return this.slcSpeedLimit;
   }

   public final float getSlcUnconfirmed() {
      return this.slcUnconfirmed;
   }

   public final SlowReason.Tracker getSlow() {
      return this.slow;
   }

   public final boolean getSp7() {
      return this.iBool("sp7", false);
   }

   public final boolean getStandstill() {
      return this.standstill;
   }

   public final boolean getStarted() {
      return this.started;
   }

   public final float getSteerRatioLive() {
      return this.steerRatioLive;
   }

   public final float getSteeringAngleDeg() {
      return this.steeringAngleDeg;
   }

   public final boolean getSteeringPressed() {
      return this.steeringPressed;
   }

   public final float getSteeringTorque() {
      return this.steeringTorque;
   }

   public final float getStiffness() {
      return this.stiffness;
   }

   public final boolean getStopSignConfirmed() {
      return this.stopSignConfirmed;
   }

   public final String getThermalStatus() {
      return this.thermalStatus;
   }

   public final Map<String, Object> getToggles() {
      return this.toggles;
   }

   public final long getTorqueAtNs() {
      return this.torqueAtNs;
   }

   public final boolean getTorqueUseParams() {
      return this.torqueUseParams;
   }

   public final boolean getTrackingLead() {
      return this.trackingLead;
   }

   public final float[] getTracks() {
      return this.tracks;
   }

   public final boolean getTrafficMode() {
      return this.trafficMode;
   }

   public final double getTurnGpsDeg() {
      return this.turnGpsDeg;
   }

   public final String getTurnGpsSrc() {
      return this.turnGpsSrc;
   }

   public final double getTurnGyroDeg() {
      return this.turnGyroDeg;
   }

   public final double getTurnSteerDeg() {
      return this.turnSteerDeg;
   }

   public final UiSettings getUi() {
      UiSettings var1 = this.ui;
      if (var1 != null) {
         return var1;
      } else {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         return null;
      }
   }

   public final float getVCruise() {
      return this.vCruise;
   }

   public final float getVCruiseCluster() {
      return this.vCruiseCluster;
   }

   public final float getVEgo() {
      return this.vEgo;
   }

   public final float getVEgoCluster() {
      return this.vEgoCluster;
   }

   public final boolean getWeatherDay() {
      return this.weatherDay;
   }

   public final int getWeatherId() {
      return this.weatherId;
   }

   public final float getYawRate() {
      return this.yawRate;
   }

   public final float hz(String var1) {
      Intrinsics.checkNotNullParameter(var1, "key");
      Rate var3 = this.rates.get(var1);
      float var2;
      if (var3 != null) {
         var2 = var3.hz(System.nanoTime());
      } else {
         var2 = 0.0F;
      }

      return var2;
   }

   public final boolean iBool(String var1, boolean var2) {
      Intrinsics.checkNotNullParameter(var1, "key");
      Boolean var3 = this.truthy(this.info.get(var1));
      if (var3 != null) {
         var2 = var3;
      }

      return var2;
   }

   public final String iStr(String var1, String var2) {
      Intrinsics.checkNotNullParameter(var1, "key");
      Intrinsics.checkNotNullParameter(var2, "def");
      Object var4 = this.info.get(var1);
      if (var4 != null) {
         String var3 = var4.toString();
         if (var3 != null) {
            return var3;
         }
      }

      return var2;
   }

   public final boolean isMetric() {
      return this.pBool("IsMetric", false);
   }

   public final boolean mBool(String var1, boolean var2) {
      Intrinsics.checkNotNullParameter(var1, "key");
      Boolean var3 = this.truthy(this.memParams.get(var1));
      if (var3 != null) {
         var2 = var3;
      }

      return var2;
   }

   public final int mInt(String var1, int var2) {
      Intrinsics.checkNotNullParameter(var1, "key");
      Double var3 = (Double)this.memParams.get(var1);
      if (var3 instanceof Number) {
         var2 = var3.intValue();
      } else if (var3 instanceof String) {
         var3 = StringsKt.toDoubleOrNull(StringsKt.trim((String & CharSequence)var3).toString());
         if (var3 != null) {
            var2 = (int)var3.doubleValue();
         }
      }

      return var2;
   }

   public final Pair<JSONObject, Boolean> navCard(long var1) {
      JSONObject var4 = this.navState();
      boolean var3 = false;
      if (var4 != null) {
         this.heldNav = var4;
         this.heldNavMs = var1;
         return TuplesKt.to(var4, false);
      } else {
         JSONObject var5 = this.heldNav;
         if (var5 == null) {
            return null;
         } else {
            String var6 = StringsKt.trim(pStr$default(this, "NavDestination", null, 2, null)).toString();
            if (var6.length() == 0) {
               var3 = true;
            }

            if (!var3 && !Intrinsics.areEqual(var6, "{}") && !Intrinsics.areEqual(var6, "null") && var1 - this.heldNavMs <= this.NAV_HOLD_MS) {
               return TuplesKt.to(var5, true);
            } else {
               this.heldNav = null;
               return null;
            }
         }
      }
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   public final JSONObject navState() {
      JSONObject var4 = (JSONObject)this.memParams.get("NavInstructionState");
      JSONObject var5 = null;
      if (var4 == null) {
         return null;
      } else {
         label48: {
            try {
               if (var4 instanceof JSONObject) {
                  var4 = var4;
                  break label48;
               }
            } catch (Exception var9) {
               return var5;
            }

            try {
               var4 = new JSONObject(var4.toString());
            } catch (Exception var8) {
               return var5;
            }
         }

         boolean var3 = false;
         int var1 = var3;

         label54: {
            try {
               if (!var4.optBoolean("valid", false)) {
                  break label54;
               }

               String var6 = var4.optString("maneuverPrimaryText");
               Intrinsics.checkNotNullExpressionValue(var6, "optString(...)");
               var1 = var6.length();
            } catch (Exception var7) {
               return var5;
            }

            boolean var2;
            if (var1 > 0) {
               var2 = true;
            } else {
               var2 = false;
            }

            var1 = var3;
            if (var2) {
               var1 = 1;
            }
         }

         if (var1) {
            var5 = var4;
         }

         return var5;
      }
   }

   public final boolean pBool(String var1, boolean var2) {
      Intrinsics.checkNotNullParameter(var1, "key");
      Boolean var3 = this.truthy(this.params.get(var1));
      if (var3 != null) {
         var2 = var3;
      }

      return var2;
   }

   public final float pFloat(String var1, float var2) {
      Intrinsics.checkNotNullParameter(var1, "key");
      Float var3 = (Float)this.params.get(var1);
      if (var3 instanceof Number) {
         var2 = var3.floatValue();
      } else if (var3 instanceof String) {
         var3 = StringsKt.toFloatOrNull(StringsKt.trim((String & CharSequence)var3).toString());
         if (var3 != null) {
            var2 = var3;
         }
      }

      return var2;
   }

   public final int pInt(String var1, int var2) {
      Intrinsics.checkNotNullParameter(var1, "key");
      Double var3 = (Double)this.params.get(var1);
      if (var3 instanceof Number) {
         var2 = var3.intValue();
      } else if (var3 instanceof String) {
         var3 = StringsKt.toDoubleOrNull(StringsKt.trim((String & CharSequence)var3).toString());
         if (var3 != null) {
            var2 = (int)var3.doubleValue();
         }
      } else if (var3 instanceof Boolean) {
         if ((Boolean)var3) {
            var2 = 1;
         } else {
            var2 = 0;
         }
      }

      return var2;
   }

   public final String pStr(String var1, String var2) {
      Intrinsics.checkNotNullParameter(var1, "key");
      Intrinsics.checkNotNullParameter(var2, "def");
      Object var4 = this.params.get(var1);
      if (var4 != null) {
         String var3 = var4.toString();
         if (var3 != null) {
            return var3;
         }
      }

      return var2;
   }

   public final void resetTurns() {
      this.turnGyroDeg = 0.0;
      this.turnSteerDeg = 0.0;
      this.turnGpsDeg = 0.0;
      this.turnLastBearing = Float.NaN;
      this.turnGpsSrc = null;
      GpsBridge.INSTANCE.resetTurn();
   }

   public final boolean s(String var1) {
      Intrinsics.checkNotNullParameter(var1, "key");
      boolean var2;
      if (this.ui != null) {
         var2 = this.getUi().get(var1);
      } else {
         var2 = false;
      }

      return var2;
   }

   public final void setAEgo(float var1) {
      this.aEgo = var1;
   }

   public final void setATarget(float var1) {
      this.aTarget = var1;
   }

   public final void setAccelRaw(float[] var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.accelRaw = var1;
   }

   public final void setAccelSrc(String var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.accelSrc = var1;
   }

   public final void setActAccel(float var1) {
      this.actAccel = var1;
   }

   public final void setActTorque(float var1) {
      this.actTorque = var1;
   }

   public final void setActive(boolean var1) {
      this.active = var1;
   }

   public final void setAlertSize(String var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.alertSize = var1;
   }

   public final void setAlertStatus(String var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.alertStatus = var1;
   }

   public final void setAlertText1(String var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.alertText1 = var1;
   }

   public final void setAlertText2(String var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.alertText2 = var1;
   }

   public final void setAllowThrottle(boolean var1) {
      this.allowThrottle = var1;
   }

   public final void setAngleOffsetDeg(float var1) {
      this.angleOffsetDeg = var1;
   }

   public final void setAolEnabled(boolean var1) {
      this.aolEnabled = var1;
   }

   public final void setApproachStopLength(float var1) {
      this.approachStopLength = var1;
   }

   public final void setBaseDelay(float var1) {
      this.baseDelay = var1;
   }

   public final void setBaseFriction(float var1) {
      this.baseFriction = var1;
   }

   public final void setBaseLatAccel(float var1) {
      this.baseLatAccel = var1;
   }

   public final void setBearingDeg(float var1) {
      this.bearingDeg = var1;
   }

   public final void setBrakeLights(boolean var1) {
      this.brakeLights = var1;
   }

   public final void setBrakePressed(boolean var1) {
      this.brakePressed = var1;
   }

   public final void setCalFrame(long var1) {
      this.calFrame = var1;
   }

   public final void setCalHeight(float var1) {
      this.calHeight = var1;
   }

   public final void setCalRpy(float[] var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.calRpy = var1;
   }

   public final void setCalValid(boolean var1) {
      this.calValid = var1;
   }

   public final void setChestnutPresent(boolean var1) {
      this.chestnutPresent = var1;
   }

   public final void setCpuTempC(float var1) {
      this.cpuTempC = var1;
   }

   public final void setCpuUsage(float var1) {
      this.cpuUsage = var1;
   }

   public final void setCruiseEnabled(boolean var1) {
      this.cruiseEnabled = var1;
   }

   public final void setCruiseSpeed(float var1) {
      this.cruiseSpeed = var1;
   }

   public final void setCscControlling(boolean var1) {
      this.cscControlling = var1;
   }

   public final void setCscSpeed(float var1) {
      this.cscSpeed = var1;
   }

   public final void setCscTraining(boolean var1) {
      this.cscTraining = var1;
   }

   public final void setCurvature(float var1) {
      this.curvature = var1;
   }

   public final void setDashSpeedLimit(float var1) {
      this.dashSpeedLimit = var1;
   }

   public final void setDelayAtNs(long var1) {
      this.delayAtNs = var1;
   }

   public final void setDesiredCurvature(float var1) {
      this.desiredCurvature = var1;
   }

   public final void setDesiredFollowDistance(long var1) {
      this.desiredFollowDistance = var1;
   }

   public final void setDeviceType(String var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.deviceType = var1;
   }

   public final void setDmAwareness(int var1) {
      this.dmAwareness = var1;
   }

   public final void setDmFace(boolean var1) {
      this.dmFace = var1;
   }

   public final void setDmPitch(float var1) {
      this.dmPitch = var1;
   }

   public final void setDmPolicyVision(boolean var1) {
      this.dmPolicyVision = var1;
   }

   public final void setDmRhd(boolean var1) {
      this.dmRhd = var1;
   }

   public final void setDmYaw(float var1) {
      this.dmYaw = var1;
   }

   public final void setEnabled(boolean var1) {
      this.enabled = var1;
   }

   public final void setEngageable(boolean var1) {
      this.engageable = var1;
   }

   public final void setExperimentalMode(boolean var1) {
      this.experimentalMode = var1;
   }

   public final void setFcw(boolean var1) {
      this.fcw = var1;
   }

   public final void setForceCoast(boolean var1) {
      this.forceCoast = var1;
   }

   public final void setForcingStop(boolean var1) {
      this.forcingStop = var1;
   }

   public final void setForcingStopLength(float var1) {
      this.forcingStopLength = var1;
   }

   public final void setFreeSpacePct(float var1) {
      this.freeSpacePct = var1;
   }

   public final void setFrictionLearned(float var1) {
      this.frictionLearned = var1;
   }

   public final void setGasPressed(boolean var1) {
      this.gasPressed = var1;
   }

   public final void setGear(String var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.gear = var1;
   }

   public final void setGnssAtNs(long var1) {
      this.gnssAtNs = var1;
   }

   public final void setGnssSats(List<GnssSat> var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.gnssSats = var1;
   }

   public final void setGpsLat(double var1) {
      this.gpsLat = var1;
   }

   public final void setGpsLon(double var1) {
      this.gpsLon = var1;
   }

   public final void setGyroRaw(float[] var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.gyroRaw = var1;
   }

   public final void setGyroSrc(String var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.gyroSrc = var1;
   }

   public final void setInfo(Map<String, ? extends Object> var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.info = var1;
   }

   public final void setLaneWidthLeft(float var1) {
      this.laneWidthLeft = var1;
   }

   public final void setLaneWidthRight(float var1) {
      this.laneWidthRight = var1;
   }

   public final void setLastAthenaPing(long var1) {
      this.lastAthenaPing = var1;
   }

   public final void setLastEventMonoNs(long var1) {
      this.lastEventMonoNs = var1;
   }

   public final void setLastModelRxNs(long var1) {
      this.lastModelRxNs = var1;
   }

   public final void setLatAccelLearned(float var1) {
      this.latAccelLearned = var1;
   }

   public final void setLatActive(boolean var1) {
      this.latActive = var1;
   }

   public final void setLc(org.capnproto.SlcPeek.LaneCentering var1) {
      this.lc = var1;
   }

   public final void setLcAtNs(long var1) {
      this.lcAtNs = var1;
   }

   public final void setLeadLeft(Lead var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.leadLeft = var1;
   }

   public final void setLeadOne(Lead var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.leadOne = var1;
   }

   public final void setLeadRight(Lead var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.leadRight = var1;
   }

   public final void setLeadTwo(Lead var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.leadTwo = var1;
   }

   public final void setLearnedDelay(float var1) {
      this.learnedDelay = var1;
   }

   public final void setLeftBlindspot(boolean var1) {
      this.leftBlindspot = var1;
   }

   public final void setLeftBlinker(boolean var1) {
      this.leftBlinker = var1;
   }

   public final void setLongActive(boolean var1) {
      this.longActive = var1;
   }

   public final void setMapdAtNs(long var1) {
      this.mapdAtNs = var1;
   }

   public final void setMapdFreeway(boolean var1) {
      this.mapdFreeway = var1;
   }

   public final void setMapdLanes(int var1) {
      this.mapdLanes = var1;
   }

   public final void setMapdMatched(boolean var1) {
      this.mapdMatched = var1;
   }

   public final void setMapdOneWay(boolean var1) {
      this.mapdOneWay = var1;
   }

   public final void setMapdWayName(String var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.mapdWayName = var1;
   }

   public final void setMapdWayRef(String var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.mapdWayRef = var1;
   }

   public final void setMaxAccel(float var1) {
      this.maxAccel = var1;
   }

   public final void setMaxTempC(float var1) {
      this.maxTempC = var1;
   }

   public final void setMemParams(Map<String, ? extends Object> var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.memParams = var1;
   }

   public final void setMemUsage(int var1) {
      this.memUsage = var1;
   }

   public final void setModel(ModelSnapshot var1) {
      this.model = var1;
   }

   public final void setModelSeen(boolean var1) {
      this.modelSeen = var1;
   }

   public final void setNetworkStrength(int var1) {
      this.networkStrength = var1;
   }

   public final void setNetworkType(String var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.networkType = var1;
   }

   public final void setOpState(String var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.opState = var1;
   }

   public final void setOutTorque(float var1) {
      this.outTorque = var1;
   }

   public final void setParams(Map<String, ? extends Object> var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.params = var1;
   }

   public final void setParamsGeneration(int var1) {
      this.paramsGeneration = var1;
   }

   public final void setPauseLateral(boolean var1) {
      this.pauseLateral = var1;
   }

   public final void setPauseLongitudinal(boolean var1) {
      this.pauseLongitudinal = var1;
   }

   public final void setPersonality(String var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.personality = var1;
   }

   public final void setPlanSource(String var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.planSource = var1;
   }

   public final void setPlanVCruise(float var1) {
      this.planVCruise = var1;
   }

   public final void setPoseAccel(float[] var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.poseAccel = var1;
   }

   public final void setPoseAtNs(long var1) {
      this.poseAtNs = var1;
   }

   public final void setPoseGyro(float[] var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.poseGyro = var1;
   }

   public final void setPoseOK(boolean var1) {
      this.poseOK = var1;
   }

   public final void setPoseValid(boolean var1) {
      this.poseValid = var1;
   }

   public final void setPoseYawZ(float var1) {
      this.poseYawZ = var1;
   }

   public final void setPulseAndGlide(boolean var1) {
      this.pulseAndGlide = var1;
   }

   public final void setPulseGlideCoasting(boolean var1) {
      this.pulseGlideCoasting = var1;
   }

   public final void setRadar(RadarTrack[] var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.radar = var1;
   }

   public final void setRadarFrame(long var1) {
      this.radarFrame = var1;
   }

   public final void setRedLight(boolean var1) {
      this.redLight = var1;
   }

   public final void setRightBlindspot(boolean var1) {
      this.rightBlindspot = var1;
   }

   public final void setRightBlinker(boolean var1) {
      this.rightBlinker = var1;
   }

   public final void setRoadCurvature(float var1) {
      this.roadCurvature = var1;
   }

   public final void setRoadName(String var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.roadName = var1;
   }

   public final void setRoll(float var1) {
      this.roll = var1;
   }

   public final void setRttMs(double var1) {
      this.rttMs = var1;
   }

   public final void setSensor(String var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.sensor = var1;
   }

   public final void setSlc(Slc var1) {
      this.slc = var1;
   }

   public final void setSlcAtNs(long var1) {
      this.slcAtNs = var1;
   }

   public final void setSlcChanged(boolean var1) {
      this.slcChanged = var1;
   }

   public final void setSlcMapSpeedLimit(float var1) {
      this.slcMapSpeedLimit = var1;
   }

   public final void setSlcMapbox(float var1) {
      this.slcMapbox = var1;
   }

   public final void setSlcNext(float var1) {
      this.slcNext = var1;
   }

   public final void setSlcOffset(float var1) {
      this.slcOffset = var1;
   }

   public final void setSlcOverridden(float var1) {
      this.slcOverridden = var1;
   }

   public final void setSlcSource(String var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.slcSource = var1;
   }

   public final void setSlcSpeedLimit(float var1) {
      this.slcSpeedLimit = var1;
   }

   public final void setSlcUnconfirmed(float var1) {
      this.slcUnconfirmed = var1;
   }

   public final void setStandstill(boolean var1) {
      this.standstill = var1;
   }

   public final void setStarted(boolean var1) {
      this.started = var1;
   }

   public final void setSteerRatioLive(float var1) {
      this.steerRatioLive = var1;
   }

   public final void setSteeringAngleDeg(float var1) {
      this.steeringAngleDeg = var1;
   }

   public final void setSteeringPressed(boolean var1) {
      this.steeringPressed = var1;
   }

   public final void setSteeringTorque(float var1) {
      this.steeringTorque = var1;
   }

   public final void setStiffness(float var1) {
      this.stiffness = var1;
   }

   public final void setStopSignConfirmed(boolean var1) {
      this.stopSignConfirmed = var1;
   }

   public final void setThermalStatus(String var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.thermalStatus = var1;
   }

   public final void setToggles(Map<String, ? extends Object> var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.toggles = var1;
   }

   public final void setTorqueAtNs(long var1) {
      this.torqueAtNs = var1;
   }

   public final void setTorqueUseParams(boolean var1) {
      this.torqueUseParams = var1;
   }

   public final void setTrackingLead(boolean var1) {
      this.trackingLead = var1;
   }

   public final void setTracks(float[] var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.tracks = var1;
   }

   public final void setTrafficMode(boolean var1) {
      this.trafficMode = var1;
   }

   public final void setTurnGpsDeg(double var1) {
      this.turnGpsDeg = var1;
   }

   public final void setTurnGpsSrc(String var1) {
      this.turnGpsSrc = var1;
   }

   public final void setTurnGyroDeg(double var1) {
      this.turnGyroDeg = var1;
   }

   public final void setTurnSteerDeg(double var1) {
      this.turnSteerDeg = var1;
   }

   public final void setUi(UiSettings var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.ui = var1;
   }

   public final void setVCruise(float var1) {
      this.vCruise = var1;
   }

   public final void setVCruiseCluster(float var1) {
      this.vCruiseCluster = var1;
   }

   public final void setVEgo(float var1) {
      this.vEgo = var1;
   }

   public final void setVEgoCluster(float var1) {
      this.vEgoCluster = var1;
   }

   public final void setWeatherDay(boolean var1) {
      this.weatherDay = var1;
   }

   public final void setWeatherId(int var1) {
      this.weatherId = var1;
   }

   public final void setYawRate(float var1) {
      this.yawRate = var1;
   }

   public final Slc slcLive(long var1) {
      Slc var4 = this.slc;
      if (var4 != null) {
         boolean var3;
         if (var1 - this.slcAtNs < 2000000000L) {
            var3 = true;
         } else {
            var3 = false;
         }

         if (var3) {
            return var4;
         }
      }

      return null;
   }

   public final boolean tBool(String var1, boolean var2) {
      Intrinsics.checkNotNullParameter(var1, "key");
      Boolean var3 = this.truthy(this.toggles.get(var1));
      if (var3 != null) {
         var2 = var3;
      }

      return var2;
   }

   public final float tFloat(String var1, float var2) {
      Intrinsics.checkNotNullParameter(var1, "key");
      Float var3 = (Float)this.toggles.get(var1);
      if (var3 instanceof Number) {
         var2 = var3.floatValue();
      } else if (var3 instanceof String) {
         var3 = StringsKt.toFloatOrNull((String)var3);
         if (var3 != null) {
            var2 = var3;
         }
      }

      return var2;
   }

   public final void tick(String var1) {
      Intrinsics.checkNotNullParameter(var1, "key");
      ConcurrentMap var4 = this.rates;
      Object var3 = var4.get(var1);
      Object var2 = var3;
      if (var3 == null) {
         var2 = new Rate();
         Object var5 = var4.putIfAbsent(var1, var2);
         if (var5 != null) {
            var2 = var5;
         }
      }

      ((Rate)var2).tick(System.nanoTime());
   }

   public static final class Companion {
      private Companion() {
      }

      // $VF: synthetic method
      public Companion(DefaultConstructorMarker var1) {
         this();
      }

      public final String chestnutWhy(String var1) {
         Intrinsics.checkNotNullParameter(var1, "why");
         switch (var1.hashCode()) {
            case -2048289183:
               if (var1.equals("PcieUnavailable")) {
                  var1 = "PCIe unavailable";
               }
               break;
            case -1165913466:
               if (var1.equals("UpdateFailed")) {
                  var1 = "update failed (check the USB cable)";
               }
               break;
            case -1159837099:
               if (var1.equals("NotDetected")) {
                  var1 = "not found on USB (check USB and 12 V)";
               }
               break;
            case -220507009:
               if (var1.equals("ModelError")) {
                  var1 = "model error (restart the car to retry)";
               }
               break;
            case -70825853:
               if (var1.equals("Overheated")) {
                  var1 = "overheated (needs airflow)";
               }
               break;
            case 1513838981:
               if (var1.equals("UsbSlow")) {
                  var1 = "USB link too slow (check the eGPU's cable / hub port)";
               }
         }

         return var1;
      }
   }

   // $VF: synthetic class
   public final class WhenMappings {
      public static final int[] $EnumSwitchMapping$0;
      public static final int[] $EnumSwitchMapping$1;

      static {
         int[] var0 = new int[Which.values().length];

         try {
            var0[Which.GYRO.ordinal()] = 1;
         } catch (NoSuchFieldError var31) {
         }

         try {
            var0[Which.GYRO_UNCALIBRATED.ordinal()] = 2;
         } catch (NoSuchFieldError var30) {
         }

         $EnumSwitchMapping$0 = var0;
         var0 = new int[ai.comma.cereal.Log.Event.Which.values().length];

         try {
            var0[ai.comma.cereal.Log.Event.Which.CAR_STATE.ordinal()] = 1;
         } catch (NoSuchFieldError var29) {
         }

         try {
            var0[ai.comma.cereal.Log.Event.Which.SELFDRIVE_STATE.ordinal()] = 2;
         } catch (NoSuchFieldError var28) {
         }

         try {
            var0[ai.comma.cereal.Log.Event.Which.CONTROLS_STATE.ordinal()] = 3;
         } catch (NoSuchFieldError var27) {
         }

         try {
            var0[ai.comma.cereal.Log.Event.Which.CAR_CONTROL.ordinal()] = 4;
         } catch (NoSuchFieldError var26) {
         }

         try {
            var0[ai.comma.cereal.Log.Event.Which.CAR_OUTPUT.ordinal()] = 5;
         } catch (NoSuchFieldError var25) {
         }

         try {
            var0[ai.comma.cereal.Log.Event.Which.LIVE_PARAMETERS.ordinal()] = 6;
         } catch (NoSuchFieldError var24) {
         }

         try {
            var0[ai.comma.cereal.Log.Event.Which.LONGITUDINAL_PLAN.ordinal()] = 7;
         } catch (NoSuchFieldError var23) {
         }

         try {
            var0[ai.comma.cereal.Log.Event.Which.DEVICE_STATE.ordinal()] = 8;
         } catch (NoSuchFieldError var22) {
         }

         try {
            var0[ai.comma.cereal.Log.Event.Which.ROAD_CAMERA_STATE.ordinal()] = 9;
         } catch (NoSuchFieldError var21) {
         }

         try {
            var0[ai.comma.cereal.Log.Event.Which.LIVE_CALIBRATION.ordinal()] = 10;
         } catch (NoSuchFieldError var20) {
         }

         try {
            var0[ai.comma.cereal.Log.Event.Which.MODEL_V2.ordinal()] = 11;
         } catch (NoSuchFieldError var19) {
         }

         try {
            var0[ai.comma.cereal.Log.Event.Which.RADAR_STATE.ordinal()] = 12;
         } catch (NoSuchFieldError var18) {
         }

         try {
            var0[ai.comma.cereal.Log.Event.Which.STARPILOT_RADAR_STATE.ordinal()] = 13;
         } catch (NoSuchFieldError var17) {
         }

         try {
            var0[ai.comma.cereal.Log.Event.Which.DRIVER_MONITORING_STATE.ordinal()] = 14;
         } catch (NoSuchFieldError var16) {
         }

         try {
            var0[ai.comma.cereal.Log.Event.Which.STARPILOT_PLAN.ordinal()] = 15;
         } catch (NoSuchFieldError var15) {
         }

         try {
            var0[ai.comma.cereal.Log.Event.Which.STARPILOT_LATERAL_STATE.ordinal()] = 16;
         } catch (NoSuchFieldError var14) {
         }

         try {
            var0[ai.comma.cereal.Log.Event.Which.LIVE_DELAY.ordinal()] = 17;
         } catch (NoSuchFieldError var13) {
         }

         try {
            var0[ai.comma.cereal.Log.Event.Which.LIVE_TORQUE_PARAMETERS.ordinal()] = 18;
         } catch (NoSuchFieldError var12) {
         }

         try {
            var0[ai.comma.cereal.Log.Event.Which.CAR_PARAMS.ordinal()] = 19;
         } catch (NoSuchFieldError var11) {
         }

         try {
            var0[ai.comma.cereal.Log.Event.Which.STARPILOT_CAR_STATE.ordinal()] = 20;
         } catch (NoSuchFieldError var10) {
         }

         try {
            var0[ai.comma.cereal.Log.Event.Which.LIVE_POSE.ordinal()] = 21;
         } catch (NoSuchFieldError var9) {
         }

         try {
            var0[ai.comma.cereal.Log.Event.Which.ACCELEROMETER.ordinal()] = 22;
         } catch (NoSuchFieldError var8) {
         }

         try {
            var0[ai.comma.cereal.Log.Event.Which.GYROSCOPE.ordinal()] = 23;
         } catch (NoSuchFieldError var7) {
         }

         try {
            var0[ai.comma.cereal.Log.Event.Which.MAPD_OUT.ordinal()] = 24;
         } catch (NoSuchFieldError var6) {
         }

         try {
            var0[ai.comma.cereal.Log.Event.Which.GPS_LOCATION_EXTERNAL.ordinal()] = 25;
         } catch (NoSuchFieldError var5) {
         }

         try {
            var0[ai.comma.cereal.Log.Event.Which.GPS_LOCATION.ordinal()] = 26;
         } catch (NoSuchFieldError var4) {
         }

         try {
            var0[ai.comma.cereal.Log.Event.Which.UBLOX_GNSS.ordinal()] = 27;
         } catch (NoSuchFieldError var3) {
         }

         try {
            var0[ai.comma.cereal.Log.Event.Which.LIVE_TRACKS.ordinal()] = 28;
         } catch (NoSuchFieldError var2) {
         }

         $EnumSwitchMapping$1 = var0;
      }
   }
}
