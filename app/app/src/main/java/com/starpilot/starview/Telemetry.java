package com.starpilot.starview;

import ai.comma.cereal.Car;
import ai.comma.cereal.Custom;
import ai.comma.cereal.Log;
import com.starpilot.starview.SlowReason;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.BiFunction;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.capnproto.PrimitiveList;
import org.capnproto.SlcPeek;
import org.capnproto.StructList;
import org.json.JSONObject;

/* compiled from: Telemetry.kt */
public final class Telemetry {
    public static final Companion Companion = new Companion(null);
    private volatile float aEgo;
    private volatile float aTarget;
    private volatile float actAccel;
    private volatile float actTorque;
    private volatile boolean active;
    private volatile float angleOffsetDeg;
    private volatile boolean aolEnabled;
    private volatile float approachStopLength;
    private volatile boolean brakeLights;
    private volatile boolean brakePressed;
    private volatile long calFrame;
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
    private volatile boolean gasPressed;
    private volatile long gnssAtNs;
    private JSONObject heldNav;
    private long heldNavMs;
    private volatile float laneWidthLeft;
    private volatile float laneWidthRight;
    private volatile long lastAthenaPing;
    private volatile long lastEventMonoNs;
    private long lastExtFixMs;
    private volatile long lastModelRxNs;
    private volatile boolean latActive;
    private volatile SlcPeek.LaneCentering lc;
    private volatile long lcAtNs;
    private volatile boolean leftBlindspot;
    private volatile boolean leftBlinker;
    private volatile boolean longActive;
    private volatile long mapdAtNs;
    private volatile boolean mapdFreeway;
    private volatile int mapdLanes;
    private volatile boolean mapdMatched;
    private volatile boolean mapdOneWay;
    private volatile float maxTempC;
    private volatile int memUsage;
    private volatile ModelSnapshot model;
    private volatile boolean modelSeen;
    private volatile int networkStrength;
    private volatile float outTorque;
    private volatile int paramsGeneration;
    private volatile boolean pauseLateral;
    private volatile boolean pauseLongitudinal;
    private volatile float planVCruise;
    private volatile long poseAtNs;
    private volatile boolean poseOK;
    private volatile boolean poseValid;
    private volatile boolean pulseAndGlide;
    private volatile boolean pulseGlideCoasting;
    private volatile long radarFrame;
    private volatile boolean redLight;
    private volatile boolean rightBlindspot;
    private volatile boolean rightBlinker;
    private volatile float roadCurvature;
    private volatile float roll;
    private volatile SlcPeek.Slc slc;
    private volatile long slcAtNs;
    private volatile boolean slcChanged;
    private volatile float slcMapSpeedLimit;
    private volatile float slcMapbox;
    private volatile float slcNext;
    private volatile float slcOffset;
    private volatile float slcOverridden;
    private volatile float slcSpeedLimit;
    private volatile float slcUnconfirmed;
    private volatile boolean standstill;
    private volatile boolean started;
    private volatile float steerRatioLive;
    private volatile float steeringAngleDeg;
    private volatile boolean steeringPressed;
    private volatile float steeringTorque;
    private volatile boolean stopSignConfirmed;
    private volatile long torqueAtNs;
    private volatile boolean torqueUseParams;
    private volatile boolean trackingLead;
    private volatile boolean trafficMode;
    private long turnCsMono;
    private volatile double turnGpsDeg;
    private volatile String turnGpsSrc;
    private volatile double turnGyroDeg;
    private long turnPoseMono;
    private volatile double turnSteerDeg;
    public UiSettings ui;
    private volatile float vCruise;
    private volatile float vCruiseCluster;
    private volatile float vEgo;
    private volatile float vEgoCluster;
    private volatile int weatherId;
    private volatile float yawRate;
    private volatile String gear = "unknown";
    private volatile String mapdWayName = "";
    private volatile String mapdWayRef = "";
    private volatile String opState = "disabled";
    private volatile String alertText1 = "";
    private volatile String alertText2 = "";
    private volatile String alertSize = "none";
    private volatile String alertStatus = "normal";
    private volatile String personality = "standard";
    private volatile boolean allowThrottle = true;
    private volatile String planSource = "cruise";
    private final SlowReason.Tracker slow = new SlowReason.Tracker();
    private volatile String thermalStatus = "green";
    private volatile String networkType = "none";
    private volatile String deviceType = "unknown";
    private volatile String sensor = "unknown";
    private volatile float[] calRpy = {0.0f, 0.0f, 0.0f};
    private volatile float calHeight = 1.22f;
    private volatile Lead leadOne = new Lead(false, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, false, 64, null);
    private volatile Lead leadTwo = new Lead(false, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, false, 64, null);
    private volatile Lead leadLeft = new Lead(false, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, false, 64, null);
    private volatile Lead leadRight = new Lead(false, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, false, 64, null);
    private volatile int dmAwareness = 100;
    private volatile String slcSource = "";
    private volatile boolean weatherDay = true;
    private volatile Map<String, ? extends Object> toggles = MapsKt.emptyMap();
    private String lastTogglesStr = "";
    private volatile String roadName = "";
    private volatile float bearingDeg = Float.NaN;
    private volatile float poseYawZ = Float.NaN;
    private volatile double gpsLat = Double.NaN;
    private volatile double gpsLon = Double.NaN;
    private volatile float[] tracks = new float[0];
    private volatile RadarTrack[] radar = new RadarTrack[0];
    private volatile float learnedDelay = Float.NaN;
    private volatile float frictionLearned = Float.NaN;
    private volatile float latAccelLearned = Float.NaN;
    private volatile float stiffness = Float.NaN;
    private volatile float baseDelay = Float.NaN;
    private volatile float baseFriction = Float.NaN;
    private volatile float baseLatAccel = Float.NaN;
    private volatile float maxAccel = Float.NaN;
    private final ConcurrentHashMap<String, GpsFixInfo> gpsFixes = new ConcurrentHashMap<>();
    private volatile List<GnssSat> gnssSats = CollectionsKt.emptyList();
    private final ConcurrentHashMap<String, Rate> rates = new ConcurrentHashMap<>();
    private volatile float[] poseGyro = {Float.NaN, Float.NaN, Float.NaN};
    private volatile float[] poseAccel = {Float.NaN, Float.NaN, Float.NaN};
    private volatile float[] accelRaw = {Float.NaN, Float.NaN, Float.NaN};
    private volatile String accelSrc = "";
    private volatile float[] gyroRaw = {Float.NaN, Float.NaN, Float.NaN};
    private volatile String gyroSrc = "";
    private float turnLastBearing = Float.NaN;
    private final float TURN_MIN_V = 1.5f;
    private volatile Map<String, ? extends Object> params = MapsKt.emptyMap();
    private volatile Map<String, ? extends Object> memParams = MapsKt.emptyMap();
    private volatile Map<String, ? extends Object> info = MapsKt.emptyMap();
    private final ConcurrentHashMap<String, Long> msgCount = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Long> byteCount = new ConcurrentHashMap<>();
    private volatile double rttMs = -1.0d;
    private final long NAV_HOLD_MS = 30000;

    /* compiled from: Telemetry.kt */
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[Log.SensorEventData.Which.values().length];
            try {
                iArr[Log.SensorEventData.Which.GYRO.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[Log.SensorEventData.Which.GYRO_UNCALIBRATED.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[Log.Event.Which.values().length];
            try {
                iArr2[Log.Event.Which.CAR_STATE.ordinal()] = 1;
            } catch (NoSuchFieldError e3) {
            }
            try {
                iArr2[Log.Event.Which.SELFDRIVE_STATE.ordinal()] = 2;
            } catch (NoSuchFieldError e4) {
            }
            try {
                iArr2[Log.Event.Which.CONTROLS_STATE.ordinal()] = 3;
            } catch (NoSuchFieldError e5) {
            }
            try {
                iArr2[Log.Event.Which.CAR_CONTROL.ordinal()] = 4;
            } catch (NoSuchFieldError e6) {
            }
            try {
                iArr2[Log.Event.Which.CAR_OUTPUT.ordinal()] = 5;
            } catch (NoSuchFieldError e7) {
            }
            try {
                iArr2[Log.Event.Which.LIVE_PARAMETERS.ordinal()] = 6;
            } catch (NoSuchFieldError e8) {
            }
            try {
                iArr2[Log.Event.Which.LONGITUDINAL_PLAN.ordinal()] = 7;
            } catch (NoSuchFieldError e9) {
            }
            try {
                iArr2[Log.Event.Which.DEVICE_STATE.ordinal()] = 8;
            } catch (NoSuchFieldError e10) {
            }
            try {
                iArr2[Log.Event.Which.ROAD_CAMERA_STATE.ordinal()] = 9;
            } catch (NoSuchFieldError e11) {
            }
            try {
                iArr2[Log.Event.Which.LIVE_CALIBRATION.ordinal()] = 10;
            } catch (NoSuchFieldError e12) {
            }
            try {
                iArr2[Log.Event.Which.MODEL_V2.ordinal()] = 11;
            } catch (NoSuchFieldError e13) {
            }
            try {
                iArr2[Log.Event.Which.RADAR_STATE.ordinal()] = 12;
            } catch (NoSuchFieldError e14) {
            }
            try {
                iArr2[Log.Event.Which.STARPILOT_RADAR_STATE.ordinal()] = 13;
            } catch (NoSuchFieldError e15) {
            }
            try {
                iArr2[Log.Event.Which.DRIVER_MONITORING_STATE.ordinal()] = 14;
            } catch (NoSuchFieldError e16) {
            }
            try {
                iArr2[Log.Event.Which.STARPILOT_PLAN.ordinal()] = 15;
            } catch (NoSuchFieldError e17) {
            }
            try {
                iArr2[Log.Event.Which.STARPILOT_LATERAL_STATE.ordinal()] = 16;
            } catch (NoSuchFieldError e18) {
            }
            try {
                iArr2[Log.Event.Which.LIVE_DELAY.ordinal()] = 17;
            } catch (NoSuchFieldError e19) {
            }
            try {
                iArr2[Log.Event.Which.LIVE_TORQUE_PARAMETERS.ordinal()] = 18;
            } catch (NoSuchFieldError e20) {
            }
            try {
                iArr2[Log.Event.Which.CAR_PARAMS.ordinal()] = 19;
            } catch (NoSuchFieldError e21) {
            }
            try {
                iArr2[Log.Event.Which.STARPILOT_CAR_STATE.ordinal()] = 20;
            } catch (NoSuchFieldError e22) {
            }
            try {
                iArr2[Log.Event.Which.LIVE_POSE.ordinal()] = 21;
            } catch (NoSuchFieldError e23) {
            }
            try {
                iArr2[Log.Event.Which.ACCELEROMETER.ordinal()] = 22;
            } catch (NoSuchFieldError e24) {
            }
            try {
                iArr2[Log.Event.Which.GYROSCOPE.ordinal()] = 23;
            } catch (NoSuchFieldError e25) {
            }
            try {
                iArr2[Log.Event.Which.MAPD_OUT.ordinal()] = 24;
            } catch (NoSuchFieldError e26) {
            }
            try {
                iArr2[Log.Event.Which.GPS_LOCATION_EXTERNAL.ordinal()] = 25;
            } catch (NoSuchFieldError e27) {
            }
            try {
                iArr2[Log.Event.Which.GPS_LOCATION.ordinal()] = 26;
            } catch (NoSuchFieldError e28) {
            }
            try {
                iArr2[Log.Event.Which.UBLOX_GNSS.ordinal()] = 27;
            } catch (NoSuchFieldError e29) {
            }
            try {
                iArr2[Log.Event.Which.LIVE_TRACKS.ordinal()] = 28;
            } catch (NoSuchFieldError e30) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    public final UiSettings getUi() {
        UiSettings uiSettings = this.ui;
        if (uiSettings != null) {
            return uiSettings;
        }
        Intrinsics.throwUninitializedPropertyAccessException("ui");
        return null;
    }

    public final void setUi(UiSettings uiSettings) {
        Intrinsics.checkNotNullParameter(uiSettings, "<set-?>");
        this.ui = uiSettings;
    }

    public final boolean s(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        if (this.ui != null) {
            return getUi().get(key);
        }
        return false;
    }

    public final float f(String key, float def) {
        Intrinsics.checkNotNullParameter(key, "key");
        return this.ui != null ? getUi().getFloat(key, def) : def;
    }

    public final float getAEgo() {
        return this.aEgo;
    }

    public final float getVEgo() {
        return this.vEgo;
    }

    public final float getVEgoCluster() {
        return this.vEgoCluster;
    }

    public final void setAEgo(float f) {
        this.aEgo = f;
    }

    public final void setVEgo(float f) {
        this.vEgo = f;
    }

    public final void setVEgoCluster(float f) {
        this.vEgoCluster = f;
    }

    public final float getSteeringAngleDeg() {
        return this.steeringAngleDeg;
    }

    public final void setSteeringAngleDeg(float f) {
        this.steeringAngleDeg = f;
    }

    public final boolean getBrakePressed() {
        return this.brakePressed;
    }

    public final boolean getGasPressed() {
        return this.gasPressed;
    }

    public final boolean getSteeringPressed() {
        return this.steeringPressed;
    }

    public final void setBrakePressed(boolean z) {
        this.brakePressed = z;
    }

    public final void setGasPressed(boolean z) {
        this.gasPressed = z;
    }

    public final void setSteeringPressed(boolean z) {
        this.steeringPressed = z;
    }

    public final float getSteeringTorque() {
        return this.steeringTorque;
    }

    public final void setSteeringTorque(float f) {
        this.steeringTorque = f;
    }

    public final boolean getLeftBlinker() {
        return this.leftBlinker;
    }

    public final boolean getRightBlinker() {
        return this.rightBlinker;
    }

    public final void setLeftBlinker(boolean z) {
        this.leftBlinker = z;
    }

    public final void setRightBlinker(boolean z) {
        this.rightBlinker = z;
    }

    public final boolean getLeftBlindspot() {
        return this.leftBlindspot;
    }

    public final boolean getRightBlindspot() {
        return this.rightBlindspot;
    }

    public final void setLeftBlindspot(boolean z) {
        this.leftBlindspot = z;
    }

    public final void setRightBlindspot(boolean z) {
        this.rightBlindspot = z;
    }

    public final boolean getCruiseEnabled() {
        return this.cruiseEnabled;
    }

    public final float getCruiseSpeed() {
        return this.cruiseSpeed;
    }

    public final float getVCruise() {
        return this.vCruise;
    }

    public final float getVCruiseCluster() {
        return this.vCruiseCluster;
    }

    public final void setCruiseEnabled(boolean z) {
        this.cruiseEnabled = z;
    }

    public final void setCruiseSpeed(float f) {
        this.cruiseSpeed = f;
    }

    public final void setVCruise(float f) {
        this.vCruise = f;
    }

    public final void setVCruiseCluster(float f) {
        this.vCruiseCluster = f;
    }

    public final String getGear() {
        return this.gear;
    }

    public final boolean getStandstill() {
        return this.standstill;
    }

    public final void setGear(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.gear = str;
    }

    public final void setStandstill(boolean z) {
        this.standstill = z;
    }

    public final int getMapdLanes() {
        return this.mapdLanes;
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

    public final void setMapdLanes(int i) {
        this.mapdLanes = i;
    }

    public final void setMapdOneWay(boolean z) {
        this.mapdOneWay = z;
    }

    public final void setMapdWayName(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.mapdWayName = str;
    }

    public final void setMapdWayRef(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.mapdWayRef = str;
    }

    public final long getMapdAtNs() {
        return this.mapdAtNs;
    }

    public final boolean getMapdFreeway() {
        return this.mapdFreeway;
    }

    public final boolean getMapdMatched() {
        return this.mapdMatched;
    }

    public final void setMapdAtNs(long j) {
        this.mapdAtNs = j;
    }

    public final void setMapdFreeway(boolean z) {
        this.mapdFreeway = z;
    }

    public final void setMapdMatched(boolean z) {
        this.mapdMatched = z;
    }

    public final boolean getActive() {
        return this.active;
    }

    public final boolean getEnabled() {
        return this.enabled;
    }

    public final boolean getEngageable() {
        return this.engageable;
    }

    public final void setActive(boolean z) {
        this.active = z;
    }

    public final void setEnabled(boolean z) {
        this.enabled = z;
    }

    public final void setEngageable(boolean z) {
        this.engageable = z;
    }

    public final boolean getExperimentalMode() {
        return this.experimentalMode;
    }

    public final String getOpState() {
        return this.opState;
    }

    public final void setExperimentalMode(boolean z) {
        this.experimentalMode = z;
    }

    public final void setOpState(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.opState = str;
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

    public final void setAlertSize(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.alertSize = str;
    }

    public final void setAlertStatus(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.alertStatus = str;
    }

    public final void setAlertText1(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.alertText1 = str;
    }

    public final void setAlertText2(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.alertText2 = str;
    }

    public final String getPersonality() {
        return this.personality;
    }

    public final void setPersonality(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.personality = str;
    }

    public final float getCurvature() {
        return this.curvature;
    }

    public final float getDesiredCurvature() {
        return this.desiredCurvature;
    }

    public final void setCurvature(float f) {
        this.curvature = f;
    }

    public final void setDesiredCurvature(float f) {
        this.desiredCurvature = f;
    }

    public final float getActAccel() {
        return this.actAccel;
    }

    public final float getActTorque() {
        return this.actTorque;
    }

    public final float getOutTorque() {
        return this.outTorque;
    }

    public final float getRoll() {
        return this.roll;
    }

    public final void setActAccel(float f) {
        this.actAccel = f;
    }

    public final void setActTorque(float f) {
        this.actTorque = f;
    }

    public final void setOutTorque(float f) {
        this.outTorque = f;
    }

    public final void setRoll(float f) {
        this.roll = f;
    }

    public final boolean getAllowThrottle() {
        return this.allowThrottle;
    }

    public final void setAllowThrottle(boolean z) {
        this.allowThrottle = z;
    }

    public final float getATarget() {
        return this.aTarget;
    }

    public final boolean getFcw() {
        return this.fcw;
    }

    public final String getPlanSource() {
        return this.planSource;
    }

    public final void setATarget(float f) {
        this.aTarget = f;
    }

    public final void setFcw(boolean z) {
        this.fcw = z;
    }

    public final void setPlanSource(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.planSource = str;
    }

    public final float getApproachStopLength() {
        return this.approachStopLength;
    }

    public final boolean getStopSignConfirmed() {
        return this.stopSignConfirmed;
    }

    public final void setApproachStopLength(float f) {
        this.approachStopLength = f;
    }

    public final void setStopSignConfirmed(boolean z) {
        this.stopSignConfirmed = z;
    }

    public final SlowReason.Tracker getSlow() {
        return this.slow;
    }

    public final float getCpuTempC() {
        return this.cpuTempC;
    }

    public final float getCpuUsage() {
        return this.cpuUsage;
    }

    public final int getMemUsage() {
        return this.memUsage;
    }

    public final boolean getStarted() {
        return this.started;
    }

    public final void setCpuTempC(float f) {
        this.cpuTempC = f;
    }

    public final void setCpuUsage(float f) {
        this.cpuUsage = f;
    }

    public final void setMemUsage(int i) {
        this.memUsage = i;
    }

    public final void setStarted(boolean z) {
        this.started = z;
    }

    public final String getDeviceType() {
        return this.deviceType;
    }

    public final float getFreeSpacePct() {
        return this.freeSpacePct;
    }

    public final String getNetworkType() {
        return this.networkType;
    }

    public final String getThermalStatus() {
        return this.thermalStatus;
    }

    public final void setDeviceType(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.deviceType = str;
    }

    public final void setFreeSpacePct(float f) {
        this.freeSpacePct = f;
    }

    public final void setNetworkType(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.networkType = str;
    }

    public final void setThermalStatus(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.thermalStatus = str;
    }

    public final long getLastAthenaPing() {
        return this.lastAthenaPing;
    }

    public final float getMaxTempC() {
        return this.maxTempC;
    }

    public final int getNetworkStrength() {
        return this.networkStrength;
    }

    public final void setLastAthenaPing(long j) {
        this.lastAthenaPing = j;
    }

    public final void setMaxTempC(float f) {
        this.maxTempC = f;
    }

    public final void setNetworkStrength(int i) {
        this.networkStrength = i;
    }

    public final boolean getChestnutPresent() {
        return this.chestnutPresent;
    }

    public final long getLastModelRxNs() {
        return this.lastModelRxNs;
    }

    public final boolean getModelSeen() {
        return this.modelSeen;
    }

    public final void setChestnutPresent(boolean z) {
        this.chestnutPresent = z;
    }

    public final void setLastModelRxNs(long j) {
        this.lastModelRxNs = j;
    }

    public final void setModelSeen(boolean z) {
        this.modelSeen = z;
    }

    public final String getSensor() {
        return this.sensor;
    }

    public final void setSensor(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.sensor = str;
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

    public final void setCalHeight(float f) {
        this.calHeight = f;
    }

    public final void setCalRpy(float[] fArr) {
        Intrinsics.checkNotNullParameter(fArr, "<set-?>");
        this.calRpy = fArr;
    }

    public final void setCalValid(boolean z) {
        this.calValid = z;
    }

    public final long getCalFrame() {
        return this.calFrame;
    }

    public final void setCalFrame(long j) {
        this.calFrame = j;
    }

    public final ModelSnapshot getModel() {
        return this.model;
    }

    public final void setModel(ModelSnapshot modelSnapshot) {
        this.model = modelSnapshot;
    }

    public final Lead getLeadOne() {
        return this.leadOne;
    }

    public final Lead getLeadTwo() {
        return this.leadTwo;
    }

    public final void setLeadOne(Lead lead) {
        Intrinsics.checkNotNullParameter(lead, "<set-?>");
        this.leadOne = lead;
    }

    public final void setLeadTwo(Lead lead) {
        Intrinsics.checkNotNullParameter(lead, "<set-?>");
        this.leadTwo = lead;
    }

    public final Lead getLeadLeft() {
        return this.leadLeft;
    }

    public final Lead getLeadRight() {
        return this.leadRight;
    }

    public final void setLeadLeft(Lead lead) {
        Intrinsics.checkNotNullParameter(lead, "<set-?>");
        this.leadLeft = lead;
    }

    public final void setLeadRight(Lead lead) {
        Intrinsics.checkNotNullParameter(lead, "<set-?>");
        this.leadRight = lead;
    }

    public final boolean getDmFace() {
        return this.dmFace;
    }

    public final boolean getDmPolicyVision() {
        return this.dmPolicyVision;
    }

    public final boolean getDmRhd() {
        return this.dmRhd;
    }

    public final void setDmFace(boolean z) {
        this.dmFace = z;
    }

    public final void setDmPolicyVision(boolean z) {
        this.dmPolicyVision = z;
    }

    public final void setDmRhd(boolean z) {
        this.dmRhd = z;
    }

    public final int getDmAwareness() {
        return this.dmAwareness;
    }

    public final float getDmPitch() {
        return this.dmPitch;
    }

    public final float getDmYaw() {
        return this.dmYaw;
    }

    public final void setDmAwareness(int i) {
        this.dmAwareness = i;
    }

    public final void setDmPitch(float f) {
        this.dmPitch = f;
    }

    public final void setDmYaw(float f) {
        this.dmYaw = f;
    }

    public final float getSlcOffset() {
        return this.slcOffset;
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

    public final void setSlcOffset(float f) {
        this.slcOffset = f;
    }

    public final void setSlcSource(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.slcSource = str;
    }

    public final void setSlcSpeedLimit(float f) {
        this.slcSpeedLimit = f;
    }

    public final void setSlcUnconfirmed(float f) {
        this.slcUnconfirmed = f;
    }

    public final boolean getSlcChanged() {
        return this.slcChanged;
    }

    public final float getSlcNext() {
        return this.slcNext;
    }

    public final float getSlcOverridden() {
        return this.slcOverridden;
    }

    public final void setSlcChanged(boolean z) {
        this.slcChanged = z;
    }

    public final void setSlcNext(float f) {
        this.slcNext = f;
    }

    public final void setSlcOverridden(float f) {
        this.slcOverridden = f;
    }

    public final boolean getForcingStop() {
        return this.forcingStop;
    }

    public final float getForcingStopLength() {
        return this.forcingStopLength;
    }

    public final boolean getRedLight() {
        return this.redLight;
    }

    public final void setForcingStop(boolean z) {
        this.forcingStop = z;
    }

    public final void setForcingStopLength(float f) {
        this.forcingStopLength = f;
    }

    public final void setRedLight(boolean z) {
        this.redLight = z;
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

    public final float getRoadCurvature() {
        return this.roadCurvature;
    }

    public final void setCscControlling(boolean z) {
        this.cscControlling = z;
    }

    public final void setCscSpeed(float f) {
        this.cscSpeed = f;
    }

    public final void setCscTraining(boolean z) {
        this.cscTraining = z;
    }

    public final void setRoadCurvature(float f) {
        this.roadCurvature = f;
    }

    public final float getLaneWidthLeft() {
        return this.laneWidthLeft;
    }

    public final float getLaneWidthRight() {
        return this.laneWidthRight;
    }

    public final float getPlanVCruise() {
        return this.planVCruise;
    }

    public final boolean getTrackingLead() {
        return this.trackingLead;
    }

    public final void setLaneWidthLeft(float f) {
        this.laneWidthLeft = f;
    }

    public final void setLaneWidthRight(float f) {
        this.laneWidthRight = f;
    }

    public final void setPlanVCruise(float f) {
        this.planVCruise = f;
    }

    public final void setTrackingLead(boolean z) {
        this.trackingLead = z;
    }

    public final long getDesiredFollowDistance() {
        return this.desiredFollowDistance;
    }

    public final boolean getPulseGlideCoasting() {
        return this.pulseGlideCoasting;
    }

    public final boolean getWeatherDay() {
        return this.weatherDay;
    }

    public final int getWeatherId() {
        return this.weatherId;
    }

    public final void setDesiredFollowDistance(long j) {
        this.desiredFollowDistance = j;
    }

    public final void setPulseGlideCoasting(boolean z) {
        this.pulseGlideCoasting = z;
    }

    public final void setWeatherDay(boolean z) {
        this.weatherDay = z;
    }

    public final void setWeatherId(int i) {
        this.weatherId = i;
    }

    public final Map<String, Object> getToggles() {
        return this.toggles;
    }

    public final void setToggles(Map<String, ? extends Object> map) {
        Intrinsics.checkNotNullParameter(map, "<set-?>");
        this.toggles = map;
    }

    public final boolean getForceCoast() {
        return this.forceCoast;
    }

    public final boolean getPauseLateral() {
        return this.pauseLateral;
    }

    public final boolean getPauseLongitudinal() {
        return this.pauseLongitudinal;
    }

    public final void setForceCoast(boolean z) {
        this.forceCoast = z;
    }

    public final void setPauseLateral(boolean z) {
        this.pauseLateral = z;
    }

    public final void setPauseLongitudinal(boolean z) {
        this.pauseLongitudinal = z;
    }

    public final boolean getBrakeLights() {
        return this.brakeLights;
    }

    public final float getDashSpeedLimit() {
        return this.dashSpeedLimit;
    }

    public final boolean getPulseAndGlide() {
        return this.pulseAndGlide;
    }

    public final void setBrakeLights(boolean z) {
        this.brakeLights = z;
    }

    public final void setDashSpeedLimit(float f) {
        this.dashSpeedLimit = f;
    }

    public final void setPulseAndGlide(boolean z) {
        this.pulseAndGlide = z;
    }

    public final boolean getAolEnabled() {
        return this.aolEnabled;
    }

    public final boolean getTrafficMode() {
        return this.trafficMode;
    }

    public final void setAolEnabled(boolean z) {
        this.aolEnabled = z;
    }

    public final void setTrafficMode(boolean z) {
        this.trafficMode = z;
    }

    public final String getRoadName() {
        return this.roadName;
    }

    public final void setRoadName(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.roadName = str;
    }

    public final float getBearingDeg() {
        return this.bearingDeg;
    }

    public final void setBearingDeg(float f) {
        this.bearingDeg = f;
    }

    public final float getYawRate() {
        return this.yawRate;
    }

    public final void setYawRate(float f) {
        this.yawRate = f;
    }

    public final long getPoseAtNs() {
        return this.poseAtNs;
    }

    public final float getPoseYawZ() {
        return this.poseYawZ;
    }

    public final void setPoseAtNs(long j) {
        this.poseAtNs = j;
    }

    public final void setPoseYawZ(float f) {
        this.poseYawZ = f;
    }

    public final float getAngleOffsetDeg() {
        return this.angleOffsetDeg;
    }

    public final float getSteerRatioLive() {
        return this.steerRatioLive;
    }

    public final void setAngleOffsetDeg(float f) {
        this.angleOffsetDeg = f;
    }

    public final void setSteerRatioLive(float f) {
        this.steerRatioLive = f;
    }

    public final double getGpsLat() {
        return this.gpsLat;
    }

    public final double getGpsLon() {
        return this.gpsLon;
    }

    public final void setGpsLat(double d) {
        this.gpsLat = d;
    }

    public final void setGpsLon(double d) {
        this.gpsLon = d;
    }

    public final float[] getTracks() {
        return this.tracks;
    }

    public final void setTracks(float[] fArr) {
        Intrinsics.checkNotNullParameter(fArr, "<set-?>");
        this.tracks = fArr;
    }

    public final RadarTrack[] getRadar() {
        return this.radar;
    }

    public final long getRadarFrame() {
        return this.radarFrame;
    }

    public final void setRadar(RadarTrack[] radarTrackArr) {
        Intrinsics.checkNotNullParameter(radarTrackArr, "<set-?>");
        this.radar = radarTrackArr;
    }

    public final void setRadarFrame(long j) {
        this.radarFrame = j;
    }

    public final float getSlcMapSpeedLimit() {
        return this.slcMapSpeedLimit;
    }

    public final float getSlcMapbox() {
        return this.slcMapbox;
    }

    public final void setSlcMapSpeedLimit(float f) {
        this.slcMapSpeedLimit = f;
    }

    public final void setSlcMapbox(float f) {
        this.slcMapbox = f;
    }

    public final SlcPeek.Slc getSlc() {
        return this.slc;
    }

    public final long getSlcAtNs() {
        return this.slcAtNs;
    }

    public final void setSlc(SlcPeek.Slc slc) {
        this.slc = slc;
    }

    public final void setSlcAtNs(long j) {
        this.slcAtNs = j;
    }

    public static /* synthetic */ SlcPeek.Slc slcLive$default(Telemetry telemetry, long j, int i, Object obj) {
        if ((i & 1) != 0) {
            j = System.nanoTime();
        }
        return telemetry.slcLive(j);
    }

    public final SlcPeek.Slc slcLive(long nowNs) {
        SlcPeek.Slc slc = this.slc;
        if (slc != null) {
            if (nowNs - this.slcAtNs < 2000000000) {
                return slc;
            }
        }
        return null;
    }

    public final boolean getLatActive() {
        return this.latActive;
    }

    public final boolean getLongActive() {
        return this.longActive;
    }

    public final void setLatActive(boolean z) {
        this.latActive = z;
    }

    public final void setLongActive(boolean z) {
        this.longActive = z;
    }

    public final SlcPeek.LaneCentering getLc() {
        return this.lc;
    }

    public final long getLcAtNs() {
        return this.lcAtNs;
    }

    public final void setLc(SlcPeek.LaneCentering laneCentering) {
        this.lc = laneCentering;
    }

    public final void setLcAtNs(long j) {
        this.lcAtNs = j;
    }

    public final float getLearnedDelay() {
        return this.learnedDelay;
    }

    public final boolean getTorqueUseParams() {
        return this.torqueUseParams;
    }

    public final void setLearnedDelay(float f) {
        this.learnedDelay = f;
    }

    public final void setTorqueUseParams(boolean z) {
        this.torqueUseParams = z;
    }

    public final float getFrictionLearned() {
        return this.frictionLearned;
    }

    public final float getLatAccelLearned() {
        return this.latAccelLearned;
    }

    public final float getStiffness() {
        return this.stiffness;
    }

    public final void setFrictionLearned(float f) {
        this.frictionLearned = f;
    }

    public final void setLatAccelLearned(float f) {
        this.latAccelLearned = f;
    }

    public final void setStiffness(float f) {
        this.stiffness = f;
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

    public final void setBaseDelay(float f) {
        this.baseDelay = f;
    }

    public final void setBaseFriction(float f) {
        this.baseFriction = f;
    }

    public final void setBaseLatAccel(float f) {
        this.baseLatAccel = f;
    }

    public final float getMaxAccel() {
        return this.maxAccel;
    }

    public final void setMaxAccel(float f) {
        this.maxAccel = f;
    }

    public final long getDelayAtNs() {
        return this.delayAtNs;
    }

    public final long getTorqueAtNs() {
        return this.torqueAtNs;
    }

    public final void setDelayAtNs(long j) {
        this.delayAtNs = j;
    }

    public final void setTorqueAtNs(long j) {
        this.torqueAtNs = j;
    }

    public final ConcurrentHashMap<String, GpsFixInfo> getGpsFixes() {
        return this.gpsFixes;
    }

    public final long getGnssAtNs() {
        return this.gnssAtNs;
    }

    public final List<GnssSat> getGnssSats() {
        return this.gnssSats;
    }

    public final void setGnssAtNs(long j) {
        this.gnssAtNs = j;
    }

    public final void setGnssSats(List<GnssSat> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.gnssSats = list;
    }

    public final ConcurrentHashMap<String, Rate> getRates() {
        return this.rates;
    }

    public final void tick(String key) {
        Rate putIfAbsent;
        Intrinsics.checkNotNullParameter(key, "key");
        ConcurrentMap $this$getOrPut$iv = this.rates;
        Rate rate = ((Rate) $this$getOrPut$iv.get(key));
        if (rate == null && (putIfAbsent = ((Rate) $this$getOrPut$iv.putIfAbsent(key, (rate = new Rate())))) != null) {
            rate = putIfAbsent;
        }
        rate.tick(System.nanoTime());
    }

    public final float hz(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        Rate rate = this.rates.get(key);
        if (rate != null) {
            return rate.hz(System.nanoTime());
        }
        return 0.0f;
    }

    public final boolean getPoseOK() {
        return this.poseOK;
    }

    public final boolean getPoseValid() {
        return this.poseValid;
    }

    public final void setPoseOK(boolean z) {
        this.poseOK = z;
    }

    public final void setPoseValid(boolean z) {
        this.poseValid = z;
    }

    public final float[] getPoseGyro() {
        return this.poseGyro;
    }

    public final void setPoseGyro(float[] fArr) {
        Intrinsics.checkNotNullParameter(fArr, "<set-?>");
        this.poseGyro = fArr;
    }

    public final float[] getPoseAccel() {
        return this.poseAccel;
    }

    public final void setPoseAccel(float[] fArr) {
        Intrinsics.checkNotNullParameter(fArr, "<set-?>");
        this.poseAccel = fArr;
    }

    public final float[] getAccelRaw() {
        return this.accelRaw;
    }

    public final String getAccelSrc() {
        return this.accelSrc;
    }

    public final void setAccelRaw(float[] fArr) {
        Intrinsics.checkNotNullParameter(fArr, "<set-?>");
        this.accelRaw = fArr;
    }

    public final void setAccelSrc(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.accelSrc = str;
    }

    public final float[] getGyroRaw() {
        return this.gyroRaw;
    }

    public final String getGyroSrc() {
        return this.gyroSrc;
    }

    public final void setGyroRaw(float[] fArr) {
        Intrinsics.checkNotNullParameter(fArr, "<set-?>");
        this.gyroRaw = fArr;
    }

    public final void setGyroSrc(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.gyroSrc = str;
    }

    public final double getTurnGpsDeg() {
        return this.turnGpsDeg;
    }

    public final double getTurnGyroDeg() {
        return this.turnGyroDeg;
    }

    public final double getTurnSteerDeg() {
        return this.turnSteerDeg;
    }

    public final void setTurnGpsDeg(double d) {
        this.turnGpsDeg = d;
    }

    public final void setTurnGyroDeg(double d) {
        this.turnGyroDeg = d;
    }

    public final void setTurnSteerDeg(double d) {
        this.turnSteerDeg = d;
    }

    public final String getTurnGpsSrc() {
        return this.turnGpsSrc;
    }

    public final void setTurnGpsSrc(String str) {
        this.turnGpsSrc = str;
    }

    public final void resetTurns() {
        this.turnGyroDeg = 0.0d;
        this.turnSteerDeg = 0.0d;
        this.turnGpsDeg = 0.0d;
        this.turnLastBearing = Float.NaN;
        this.turnGpsSrc = null;
        GpsBridge.INSTANCE.resetTurn();
    }

    public final Map<String, Object> getParams() {
        return this.params;
    }

    public final void setParams(Map<String, ? extends Object> map) {
        Intrinsics.checkNotNullParameter(map, "<set-?>");
        this.params = map;
    }

    public final Map<String, Object> getMemParams() {
        return this.memParams;
    }

    public final void setMemParams(Map<String, ? extends Object> map) {
        Intrinsics.checkNotNullParameter(map, "<set-?>");
        this.memParams = map;
    }

    public final Map<String, Object> getInfo() {
        return this.info;
    }

    public final void setInfo(Map<String, ? extends Object> map) {
        Intrinsics.checkNotNullParameter(map, "<set-?>");
        this.info = map;
    }

    public final int getParamsGeneration() {
        return this.paramsGeneration;
    }

    public final void setParamsGeneration(int i) {
        this.paramsGeneration = i;
    }

    public final boolean iBool(String key, boolean def) {
        Intrinsics.checkNotNullParameter(key, "key");
        Boolean truthy = truthy(this.info.get(key));
        return truthy != null ? truthy.booleanValue() : def;
    }

    public final boolean getSp7() {
        return iBool("sp7", false);
    }

    public static /* synthetic */ String iStr$default(Telemetry telemetry, String str, String str2, int i, Object obj) {
        if ((i & 2) != 0) {
            str2 = "";
        }
        return telemetry.iStr(str, str2);
    }

    public final String iStr(String key, String def) {
        String obj;
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(def, "def");
        Object obj2 = this.info.get(key);
        return (obj2 == null || (obj = obj2.toString()) == null) ? def : obj;
    }

    public final ConcurrentHashMap<String, Long> getMsgCount() {
        return this.msgCount;
    }

    public final ConcurrentHashMap<String, Long> getByteCount() {
        return this.byteCount;
    }

    public final long getLastEventMonoNs() {
        return this.lastEventMonoNs;
    }

    public final void setLastEventMonoNs(long j) {
        this.lastEventMonoNs = j;
    }

    public final double getRttMs() {
        return this.rttMs;
    }

    public final void setRttMs(double d) {
        this.rttMs = d;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Long count$lambda$2(Function2 $tmp0, Object p0, Object p1) {
        return (Long) $tmp0.invoke(p0, p1);
    }

    public final void count(String name, int bytes) {
        Intrinsics.checkNotNullParameter(name, "name");
        ConcurrentHashMap<String, Long> concurrentHashMap = this.msgCount;
        final Telemetry$count$1 telemetry$count$1 = Telemetry$count$1.INSTANCE;
        concurrentHashMap.merge(name, 1L, new BiFunction() { // from class: com.starpilot.starview.Telemetry$$ExternalSyntheticLambda0
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                Long count$lambda$2;
                count$lambda$2 = Telemetry.count$lambda$2($tmp0, obj, obj2);
                return count$lambda$2;
            }
        });
        ConcurrentHashMap<String, Long> concurrentHashMap2 = this.byteCount;
        Long valueOf = Long.valueOf(bytes);
        final Telemetry$count$2 telemetry$count$2 = Telemetry$count$2.INSTANCE;
        concurrentHashMap2.merge(name, valueOf, new BiFunction() { // from class: com.starpilot.starview.Telemetry$$ExternalSyntheticLambda1
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                Long count$lambda$3;
                count$lambda$3 = Telemetry.count$lambda$3($tmp0, obj, obj2);
                return count$lambda$3;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Long count$lambda$3(Function2 $tmp0, Object p0, Object p1) {
        return (Long) $tmp0.invoke(p0, p1);
    }

    /* VF: from Vineflower (jadx: Code restructure failed;incorrect) */
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

                        return ((Boolean) var1);
                    }

                    var1 = false;
                    return ((Boolean) var1);
                }

                var1 = true;
            } else {
                var1 = (Boolean)var5;
            }
        }

        return ((Boolean) var1);
    }

    public final boolean pBool(String key, boolean def) {
        Intrinsics.checkNotNullParameter(key, "key");
        Boolean truthy = truthy(this.params.get(key));
        return truthy != null ? truthy.booleanValue() : def;
    }

    public final boolean mBool(String key, boolean def) {
        Intrinsics.checkNotNullParameter(key, "key");
        Boolean truthy = truthy(this.memParams.get(key));
        return truthy != null ? truthy.booleanValue() : def;
    }

    public final int pInt(String key, int def) {
        Intrinsics.checkNotNullParameter(key, "key");
        Object v = this.params.get(key);
        if (v instanceof Number) {
            return ((Number) v).intValue();
        }
        if (v instanceof String) {
            Double doubleOrNull = StringsKt.toDoubleOrNull(StringsKt.trim((CharSequence) v).toString());
            if (doubleOrNull != null) {
                return (int) doubleOrNull.doubleValue();
            }
        } else if (v instanceof Boolean) {
            return ((Boolean) v).booleanValue() ? 1 : 0;
        }
        return def;
    }

    public final int mInt(String key, int def) {
        Double doubleOrNull;
        Intrinsics.checkNotNullParameter(key, "key");
        Object v = this.memParams.get(key);
        return v instanceof Number ? ((Number) v).intValue() : (!(v instanceof String) || (doubleOrNull = StringsKt.toDoubleOrNull(StringsKt.trim((CharSequence) v).toString())) == null) ? def : (int) doubleOrNull.doubleValue();
    }

    public final float pFloat(String key, float def) {
        Float floatOrNull;
        Intrinsics.checkNotNullParameter(key, "key");
        Object v = this.params.get(key);
        return v instanceof Number ? ((Number) v).floatValue() : (!(v instanceof String) || (floatOrNull = StringsKt.toFloatOrNull(StringsKt.trim((CharSequence) v).toString())) == null) ? def : floatOrNull.floatValue();
    }

    public static /* synthetic */ String pStr$default(Telemetry telemetry, String str, String str2, int i, Object obj) {
        if ((i & 2) != 0) {
            str2 = "";
        }
        return telemetry.pStr(str, str2);
    }

    public final String pStr(String key, String def) {
        String obj;
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(def, "def");
        Object obj2 = this.params.get(key);
        return (obj2 == null || (obj = obj2.toString()) == null) ? def : obj;
    }

    public final boolean tBool(String key, boolean def) {
        Intrinsics.checkNotNullParameter(key, "key");
        Boolean truthy = truthy(this.toggles.get(key));
        return truthy != null ? truthy.booleanValue() : def;
    }

    public final float tFloat(String key, float def) {
        Float floatOrNull;
        Intrinsics.checkNotNullParameter(key, "key");
        Object v = this.toggles.get(key);
        return v instanceof Number ? ((Number) v).floatValue() : (!(v instanceof String) || (floatOrNull = StringsKt.toFloatOrNull((String) v)) == null) ? def : floatOrNull.floatValue();
    }

    public final boolean isMetric() {
        return pBool("IsMetric", false);
    }

    /* compiled from: Telemetry.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* VF: from Vineflower (jadx: ) */
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

    public static /* synthetic */ Pair navCard$default(Telemetry telemetry, long j, int i, Object obj) {
        if ((i & 1) != 0) {
            j = System.currentTimeMillis();
        }
        return telemetry.navCard(j);
    }

    public final Pair<JSONObject, Boolean> navCard(long nowMs) {
        JSONObject live = navState();
        if (live != null) {
            this.heldNav = live;
            this.heldNavMs = nowMs;
            return TuplesKt.to(live, false);
        }
        JSONObject held = this.heldNav;
        if (held == null) {
            return null;
        }
        String dest = StringsKt.trim((CharSequence) pStr$default(this, "NavDestination", null, 2, null)).toString();
        if ((dest.length() == 0) || Intrinsics.areEqual(dest, "{}") || Intrinsics.areEqual(dest, "null") || nowMs - this.heldNavMs > this.NAV_HOLD_MS) {
            this.heldNav = null;
            return null;
        }
        return TuplesKt.to(held, true);
    }

    public final JSONObject navState() {
        Object v = this.memParams.get("NavInstructionState");
        if (v == null) {
            return null;
        }
        try {
            JSONObject jSONObject = v instanceof JSONObject ? (JSONObject) v : new JSONObject(v.toString());
            JSONObject it = jSONObject;
            boolean z = false;
            if (it.optBoolean("valid", false)) {
                String optString = it.optString("maneuverPrimaryText");
                Intrinsics.checkNotNullExpressionValue(optString, "optString(...)");
                if (optString.length() > 0) {
                    z = true;
                }
            }
            if (z) {
                return jSONObject;
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    private static final Map<String, Object> applyParams$toMap(JSONObject o) {
        HashMap m = new HashMap();
        if (o == null) {
            return m;
        }
        Iterator<String> keys = o.keys();
        Intrinsics.checkNotNullExpressionValue(keys, "keys(...)");
        while (keys.hasNext()) {
            String k = keys.next();
            m.put(k, o.opt(k));
        }
        return m;
    }

    public final void applyParams(JSONObject j) {
        Intrinsics.checkNotNullParameter(j, "j");
        this.params = applyParams$toMap(j.optJSONObject("p"));
        this.memParams = applyParams$toMap(j.optJSONObject("m"));
        this.info = applyParams$toMap(j.optJSONObject("i"));
        Replay.INSTANCE.update(j.optJSONObject("r"));
        Ntrip.INSTANCE.update(j.optJSONObject("k"));
        this.paramsGeneration++;
    }

    private final float[] fl(PrimitiveList.Float.Reader r) {
        int size = r.size();
        float[] fArr = new float[size];
        for (int i = 0; i < size; i++) {
            fArr[i] = r.get(i);
        }
        return fArr;
    }

    /* JADX WARN: Can't wrap try/catch for region: R(43:245|(1:247)|248|249|(1:251)|252|253|(1:255)|256|257|(1:259)|260|261|(1:263)|264|265|(1:267)|268|269|(3:270|271|272)|273|274|275|(2:276|277)|(2:279|(18:281|282|283|284|(1:286)(2:312|(1:314)(1:315))|287|288|289|290|291|292|293|294|295|(2:297|298)(1:303)|299|300|301))|320|282|283|284|(0)(0)|287|288|289|290|291|292|293|294|295|(0)(0)|299|300|301) */
    /* JADX WARN: Can't wrap try/catch for region: R(44:245|(1:247)|248|249|(1:251)|252|253|(1:255)|256|257|(1:259)|260|261|(1:263)|264|265|(1:267)|268|269|(3:270|271|272)|273|274|275|276|277|(2:279|(18:281|282|283|284|(1:286)(2:312|(1:314)(1:315))|287|288|289|290|291|292|293|294|295|(2:297|298)(1:303)|299|300|301))|320|282|283|284|(0)(0)|287|288|289|290|291|292|293|294|295|(0)(0)|299|300|301) */
    /* JADX WARN: Can't wrap try/catch for region: R(46:245|(1:247)|248|249|(1:251)|252|253|(1:255)|256|257|(1:259)|260|261|(1:263)|264|265|(1:267)|268|269|270|271|272|273|274|275|276|277|(2:279|(18:281|282|283|284|(1:286)(2:312|(1:314)(1:315))|287|288|289|290|291|292|293|294|295|(2:297|298)(1:303)|299|300|301))|320|282|283|284|(0)(0)|287|288|289|290|291|292|293|294|295|(0)(0)|299|300|301) */
    /* JADX WARN: Code restructure failed: missing block: B:305:0x0a45, code lost:
    
        r44 = Float.NaN;
     */
    /* JADX WARN: Code restructure failed: missing block: B:308:0x0a20, code lost:
    
        r43 = new float[0];
     */
    /* JADX WARN: Code restructure failed: missing block: B:311:0x0a09, code lost:
    
        r42 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:318:0x09f1, code lost:
    
        r41 = 0;
     */
    /* JADX WARN: Removed duplicated region for block: B:286:0x09e0  */
    /* JADX WARN: Removed duplicated region for block: B:297:0x0a34 A[Catch: all -> 0x0a44, TRY_LEAVE, TryCatch #4 {all -> 0x0a44, blocks: (B:295:0x0a25, B:297:0x0a34), top: B:294:0x0a25 }] */
    /* JADX WARN: Removed duplicated region for block: B:303:0x0a3f  */
    /* JADX WARN: Removed duplicated region for block: B:312:0x09e2 A[Catch: all -> 0x09f0, TRY_LEAVE, TryCatch #9 {all -> 0x09f0, blocks: (B:284:0x09cc, B:312:0x09e2), top: B:283:0x09cc }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void apply(Log.Event.Reader evt) {
        boolean z;
        float s;
        boolean z2;
        boolean z3;
        float f;
        boolean z4;
        String it;
        boolean z5;
        PrimitiveList.Float.Reader v;
        boolean z6;
        String str;
        String str2;
        Intrinsics.checkNotNullParameter(evt, "evt");
        this.lastEventMonoNs = evt.getLogMonoTime();
        Log.Event.Which which = evt.which();
        double d = 0.0d;
        int i = 0;
        switch (which == null ? -1 : WhenMappings.$EnumSwitchMapping$1[which.ordinal()]) {
            case 1:
                Car.CarState.Reader cs = evt.getCarState();
                this.vEgo = cs.getVEgo();
                this.vEgoCluster = cs.getVEgoCluster();
                this.aEgo = cs.getAEgo();
                this.yawRate = cs.getYawRate();
                this.steeringAngleDeg = cs.getSteeringAngleDeg();
                try {
                    Odometry.INSTANCE.onCarState(evt.getLogMonoTime(), this);
                    GpsBridge.INSTANCE.onCarState(Intrinsics.areEqual(this.gear, "reverse") ? -this.vEgo : this.vEgo);
                } catch (Exception e) {
                }
                tick("carState");
                if (this.turnCsMono != 0) {
                    double it2 = (evt.getLogMonoTime() - this.turnCsMono) / 1.0E9d;
                    if (it2 >= 0.0d && it2 <= 1.0d) {
                        d = it2;
                    }
                }
                double it3 = d;
                this.turnCsMono = evt.getLogMonoTime();
                if (this.vEgo <= this.TURN_MIN_V || Intrinsics.areEqual(this.gear, "reverse")) {
                    z = true;
                } else {
                    double sr = this.steerRatioLive > 5.0f ? this.steerRatioLive : 15.5d;
                    double v2 = this.vEgo;
                    z = true;
                    this.turnSteerDeg += Math.toDegrees((((Math.tan(Math.toRadians(this.steeringAngleDeg - this.angleOffsetDeg) / sr) * v2) / 3.03d) / (1 + ((v2 * v2) / 900.0d))) * it3);
                }
                this.gasPressed = cs.getGasPressed();
                this.brakePressed = cs.getBrakePressed();
                this.steeringPressed = cs.getSteeringPressed();
                this.steeringTorque = cs.getSteeringTorque();
                this.leftBlinker = cs.getLeftBlinker();
                this.rightBlinker = cs.getRightBlinker();
                this.leftBlindspot = cs.getLeftBlindspot();
                this.rightBlindspot = cs.getRightBlindspot();
                this.standstill = cs.getStandstill();
                String lowerCase = cs.getGearShifter().toString().toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
                this.gear = lowerCase;
                this.vCruiseCluster = cs.getVCruiseCluster();
                this.vCruise = cs.getVCruise();
                Car.CarState.CruiseState.Reader cr = cs.getCruiseState();
                this.cruiseEnabled = cr.getEnabled();
                this.cruiseSpeed = cr.getSpeed();
                if (this.started && !this.gasPressed) {
                    float f2 = this.aEgo;
                    if (Float.isInfinite(f2) || Float.isNaN(f2)) {
                        z = false;
                    }
                    if (z) {
                        this.maxAccel = Float.isNaN(this.maxAccel) ? Math.max(0.0f, this.aEgo) : Math.max(this.maxAccel, this.aEgo);
                    }
                }
                Unit unit = Unit.INSTANCE;
                return;
            case 2:
                Log.SelfdriveState.Reader s2 = evt.getSelfdriveState();
                this.enabled = s2.getEnabled();
                this.active = s2.getActive();
                this.engageable = s2.getEngageable();
                this.experimentalMode = s2.getExperimentalMode();
                String lowerCase2 = s2.getState().toString().toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue(lowerCase2, "toLowerCase(...)");
                this.opState = lowerCase2;
                this.alertText1 = s2.getAlertText1().toString();
                this.alertText2 = s2.getAlertText2().toString();
                String lowerCase3 = s2.getAlertSize().toString().toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue(lowerCase3, "toLowerCase(...)");
                this.alertSize = lowerCase3;
                String lowerCase4 = s2.getAlertStatus().toString().toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue(lowerCase4, "toLowerCase(...)");
                this.alertStatus = lowerCase4;
                String lowerCase5 = s2.getPersonality().toString().toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue(lowerCase5, "toLowerCase(...)");
                this.personality = lowerCase5;
                DriveJournal.INSTANCE.onState(this);
                Unit unit2 = Unit.INSTANCE;
                return;
            case 3:
                Log.ControlsState.Reader c = evt.getControlsState();
                this.curvature = c.getCurvature();
                this.desiredCurvature = c.getDesiredCurvature();
                Unit unit3 = Unit.INSTANCE;
                return;
            case 4:
                Car.CarControl.Reader cc = evt.getCarControl();
                Car.CarControl.Actuators.Reader a = cc.getActuators();
                this.actTorque = a.getTorque();
                this.actAccel = a.getAccel();
                this.latActive = cc.getLatActive();
                this.longActive = cc.getLongActive();
                Unit unit4 = Unit.INSTANCE;
                return;
            case 5:
                this.outTorque = evt.getCarOutput().getActuatorsOutput().getTorque();
                Unit unit5 = Unit.INSTANCE;
                return;
            case 6:
                Log.LiveParametersData.Reader lp = evt.getLiveParameters();
                this.roll = lp.getRoll();
                this.angleOffsetDeg = lp.getAngleOffsetDeg();
                if (lp.getSteerRatio() > 5.0f) {
                    this.steerRatioLive = lp.getSteerRatio();
                }
                this.stiffness = lp.getStiffnessFactor();
                Unit unit6 = Unit.INSTANCE;
                return;
            case 7:
                Log.LongitudinalPlan.Reader lp2 = evt.getLongitudinalPlan();
                this.allowThrottle = lp2.getAllowThrottle();
                this.aTarget = lp2.getATarget();
                this.fcw = lp2.getFcw();
                String lowerCase6 = lp2.getLongitudinalPlanSource().toString().toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue(lowerCase6, "toLowerCase(...)");
                this.planSource = lowerCase6;
                boolean z7 = this.enabled;
                float f3 = this.vEgo;
                float f4 = this.aTarget;
                float f5 = this.actAccel;
                boolean z8 = this.brakePressed;
                boolean z9 = this.gasPressed;
                String str3 = this.planSource;
                boolean z10 = this.fcw;
                Lead lead = this.leadOne;
                Lead lead2 = this.leadTwo;
                boolean z11 = this.experimentalMode;
                boolean z12 = this.forcingStop;
                float f6 = this.forcingStopLength;
                boolean z13 = this.redLight;
                boolean z14 = this.stopSignConfirmed;
                float f7 = this.approachStopLength;
                boolean z15 = this.cscControlling;
                float f8 = this.cscSpeed;
                float f9 = this.slcSpeedLimit;
                float f10 = this.slcOffset;
                String str4 = this.slcSource;
                float f11 = this.planVCruise;
                float f12 = this.vCruise;
                SlowReason.In sr2 = new SlowReason.In(z7, f3, f4, f5, z8, z9, str3, z10, lead, lead2, z11, z12, f6, z13, z14, f7, z15, f8, f9, f10, str4, f11, (1.0f > f12 ? 1 : (1.0f == f12 ? 0 : -1)) <= 0 && (f12 > 250.0f ? 1 : (f12 == 250.0f ? 0 : -1)) <= 0 ? this.vCruise / 3.6f : this.cruiseSpeed, this.pulseGlideCoasting, this.forceCoast, isMetric(), this.trackingLead);
                synchronized (this.slow) {
                    this.slow.update(sr2, System.currentTimeMillis());
                    Unit unit7 = Unit.INSTANCE;
                }
                Unit unit8 = Unit.INSTANCE;
                return;
            case 8:
                Log.DeviceState.Reader d2 = evt.getDeviceState();
                if (d2.getStarted() && !this.started) {
                    this.maxAccel = Float.NaN;
                }
                this.started = d2.getStarted();
                String lowerCase7 = d2.getDeviceType().toString().toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue(lowerCase7, "toLowerCase(...)");
                this.deviceType = lowerCase7;
                this.cpuTempC = d2.getCpuTempC().size() > 0 ? d2.getCpuTempC().get(0) : 0.0f;
                if (d2.getCpuUsagePercent().size() > 0) {
                    float s3 = 0.0f;
                    for (int i2 = 0; i2 < d2.getCpuUsagePercent().size(); i2++) {
                        s3 += d2.getCpuUsagePercent().get(i2);
                    }
                    s = s3 / d2.getCpuUsagePercent().size();
                } else {
                    s = 0.0f;
                }
                this.cpuUsage = s;
                this.memUsage = d2.getMemoryUsagePercent();
                this.freeSpacePct = d2.getFreeSpacePercent();
                String lowerCase8 = d2.getThermalStatus().toString().toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue(lowerCase8, "toLowerCase(...)");
                this.thermalStatus = lowerCase8;
                String lowerCase9 = d2.getNetworkType().toString().toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue(lowerCase9, "toLowerCase(...)");
                this.networkType = lowerCase9;
                this.maxTempC = d2.getMaxTempC();
                this.networkStrength = d2.getNetworkStrength().ordinal();
                this.lastAthenaPing = d2.getLastAthenaPingTime();
                try {
                    z2 = d2.getChestnutPresent();
                } catch (Throwable th) {
                    z2 = false;
                }
                this.chestnutPresent = z2;
                if (!this.started) {
                    this.modelSeen = false;
                }
                HealthHistory.INSTANCE.add(this.memUsage, this.cpuTempC);
                Unit unit9 = Unit.INSTANCE;
                return;
            case 9:
                String lowerCase10 = evt.getRoadCameraState().getSensor().toString().toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue(lowerCase10, "toLowerCase(...)");
                this.sensor = lowerCase10;
                Unit unit10 = Unit.INSTANCE;
                return;
            case 10:
                Log.LiveCalibrationData.Reader c2 = evt.getLiveCalibration();
                if (c2.getRpyCalib().size() == 3) {
                    PrimitiveList.Float.Reader rpyCalib = c2.getRpyCalib();
                    Intrinsics.checkNotNullExpressionValue(rpyCalib, "getRpyCalib(...)");
                    this.calRpy = fl(rpyCalib);
                }
                this.calValid = Intrinsics.areEqual(c2.getCalStatus().toString(), "CALIBRATED");
                if (c2.getHeight().size() > 0) {
                    this.calHeight = c2.getHeight().get(0);
                }
                long j = this.calFrame;
                this.calFrame = 1 + j;
                Long.valueOf(j);
                return;
            case 11:
                Log.ModelDataV2.Reader m = evt.getModelV2();
                this.lastModelRxNs = System.nanoTime();
                this.modelSeen = true;
                StructList.Reader ll = m.getLaneLines();
                StructList.Reader re = m.getRoadEdges();
                int nL = ll.size();
                int nE = re.size();
                long frameId = m.getFrameId() & 4294967295L;
                PrimitiveList.Float.Reader x = m.getPosition().getX();
                Intrinsics.checkNotNullExpressionValue(x, "getX(...)");
                float[] fl = fl(x);
                PrimitiveList.Float.Reader y = m.getPosition().getY();
                Intrinsics.checkNotNullExpressionValue(y, "getY(...)");
                float[] fl2 = fl(y);
                PrimitiveList.Float.Reader z16 = m.getPosition().getZ();
                Intrinsics.checkNotNullExpressionValue(z16, "getZ(...)");
                float[] fl3 = fl(z16);
                PrimitiveList.Float.Reader x2 = m.getAcceleration().getX();
                Intrinsics.checkNotNullExpressionValue(x2, "getX(...)");
                float[] fl4 = fl(x2);
                float[][] fArr = new float[nL][];
                for (int i3 = 0; i3 < nL; i3++) {
                    PrimitiveList.Float.Reader x3 = ((View) ll.get(i3)).getX();
                    Intrinsics.checkNotNullExpressionValue(x3, "getX(...)");
                    fArr[i3] = fl(x3);
                }
                float[][] fArr2 = new float[nL][];
                for (int i4 = 0; i4 < nL; i4++) {
                    PrimitiveList.Float.Reader y2 = ((View) ll.get(i4)).getY();
                    Intrinsics.checkNotNullExpressionValue(y2, "getY(...)");
                    fArr2[i4] = fl(y2);
                }
                float[][] fArr3 = new float[nL][];
                for (int i5 = 0; i5 < nL; i5++) {
                    PrimitiveList.Float.Reader z17 = ((View) ll.get(i5)).getZ();
                    Intrinsics.checkNotNullExpressionValue(z17, "getZ(...)");
                    fArr3[i5] = fl(z17);
                }
                PrimitiveList.Float.Reader laneLineProbs = m.getLaneLineProbs();
                Intrinsics.checkNotNullExpressionValue(laneLineProbs, "getLaneLineProbs(...)");
                float[] fl5 = fl(laneLineProbs);
                float[][] fArr4 = new float[nE][];
                for (int i6 = 0; i6 < nE; i6++) {
                    PrimitiveList.Float.Reader x4 = ((View) re.get(i6)).getX();
                    Intrinsics.checkNotNullExpressionValue(x4, "getX(...)");
                    fArr4[i6] = fl(x4);
                }
                float[][] fArr5 = new float[nE][];
                for (int i7 = 0; i7 < nE; i7++) {
                    PrimitiveList.Float.Reader y3 = ((View) re.get(i7)).getY();
                    Intrinsics.checkNotNullExpressionValue(y3, "getY(...)");
                    fArr5[i7] = fl(y3);
                }
                float[][] fArr6 = new float[nE][];
                for (int i8 = 0; i8 < nE; i8++) {
                    PrimitiveList.Float.Reader z18 = ((View) re.get(i8)).getZ();
                    Intrinsics.checkNotNullExpressionValue(z18, "getZ(...)");
                    fArr6[i8] = fl(z18);
                }
                PrimitiveList.Float.Reader roadEdgeStds = m.getRoadEdgeStds();
                Intrinsics.checkNotNullExpressionValue(roadEdgeStds, "getRoadEdgeStds(...)");
                float[] fl6 = fl(roadEdgeStds);
                PrimitiveList.Float.Reader laneLineStds = m.getLaneLineStds();
                Intrinsics.checkNotNullExpressionValue(laneLineStds, "getLaneLineStds(...)");
                float[] fl7 = fl(laneLineStds);
                PrimitiveList.Float.Reader yStd = m.getPosition().getYStd();
                Intrinsics.checkNotNullExpressionValue(yStd, "getYStd(...)");
                float[] fl8 = fl(yStd);
                try {
                    z3 = Intrinsics.areEqual(m.getMeta().getLaneChangeState().toString(), "OFF");
                } catch (Throwable th2) {
                    z3 = true;
                }
                try {
                    f = m.getAction().getDesiredCurvature();
                } catch (Throwable th3) {
                    f = 0.0f;
                }
                try {
                    it = m.getMeta().getLaneChangeState().toString();
                } catch (Throwable th4) {
                    z4 = false;
                }
                if (!Intrinsics.areEqual(it, "LANE_CHANGE_STARTING")) {
                    if (!Intrinsics.areEqual(it, "LANE_CHANGE_FINISHING")) {
                        z5 = false;
                        z4 = z5;
                        String laneChangeDirection = m.getMeta().getLaneChangeDirection().toString();
                        int i9 = !Intrinsics.areEqual(laneChangeDirection, "LEFT") ? -1 : Intrinsics.areEqual(laneChangeDirection, "RIGHT") ? 1 : 0;
                        boolean z19 = Intrinsics.areEqual(m.getMeta().getLaneChangeState().toString(), "LANE_CHANGE_FINISHING");
                        PrimitiveList.Float.Reader desirePrediction = m.getMeta().getDesirePrediction();
                        Intrinsics.checkNotNullExpressionValue(desirePrediction, "getDesirePrediction(...)");
                        float[] fArr7 = fl(desirePrediction);
                        PrimitiveList.Float.Reader d3 = m.getMeta().getDesireState();
                        float f13 = d3.size() <= 4 ? d3.get(3) + d3.get(4) : Float.NaN;
                        this.model = new ModelSnapshot(frameId, fl, fl2, fl3, fl4, fArr, fArr2, fArr3, fl5, fArr4, fArr5, fArr6, fl6, fl7, fl8, z3, f, z4, i9, z19, fArr7, f13);
                        Unit unit11 = Unit.INSTANCE;
                        return;
                    }
                }
                z5 = true;
                z4 = z5;
                String laneChangeDirection2 = m.getMeta().getLaneChangeDirection().toString();
                int i92 = !Intrinsics.areEqual(laneChangeDirection2, "LEFT") ? -1 : Intrinsics.areEqual(laneChangeDirection2, "RIGHT") ? 1 : 0;
                boolean z192 = Intrinsics.areEqual(m.getMeta().getLaneChangeState().toString(), "LANE_CHANGE_FINISHING");
                PrimitiveList.Float.Reader desirePrediction2 = m.getMeta().getDesirePrediction();
                Intrinsics.checkNotNullExpressionValue(desirePrediction2, "getDesirePrediction(...)");
                float[] fArr72 = fl(desirePrediction2);
                PrimitiveList.Float.Reader d32 = m.getMeta().getDesireState();
                float f132 = d32.size() <= 4 ? d32.get(3) + d32.get(4) : Float.NaN;
                this.model = new ModelSnapshot(frameId, fl, fl2, fl3, fl4, fArr, fArr2, fArr3, fl5, fArr4, fArr5, fArr6, fl6, fl7, fl8, z3, f, z4, i92, z192, fArr72, f132);
                Unit unit112 = Unit.INSTANCE;
                return;
            case 12:
                Log.RadarState.Reader r = evt.getRadarState();
                Log.RadarState.LeadData.Reader a2 = r.getLeadOne();
                this.leadOne = new Lead(a2.getStatus(), a2.getDRel(), a2.getYRel(), a2.getVRel(), a2.getVLead(), a2.getModelProb(), a2.getRadar());
                Log.RadarState.LeadData.Reader b = r.getLeadTwo();
                this.leadTwo = new Lead(b.getStatus(), b.getDRel(), b.getYRel(), b.getVRel(), b.getVLead(), b.getModelProb(), b.getRadar());
                Unit unit12 = Unit.INSTANCE;
                return;
            case 13:
                Custom.StarPilotRadarState.Reader r2 = evt.getStarpilotRadarState();
                Custom.StarPilotRadarState.LeadData.Reader a3 = r2.getLeadLeft();
                this.leadLeft = new Lead(a3.getStatus(), a3.getDRel(), a3.getYRel(), a3.getVRel(), a3.getVLead(), a3.getModelProb(), false, 64, null);
                Custom.StarPilotRadarState.LeadData.Reader b2 = r2.getLeadRight();
                this.leadRight = new Lead(b2.getStatus(), b2.getDRel(), b2.getYRel(), b2.getVRel(), b2.getVLead(), b2.getModelProb(), false, 64, null);
                Unit unit13 = Unit.INSTANCE;
                return;
            case 14:
                Log.DriverMonitoringState.Reader d4 = evt.getDriverMonitoringState();
                this.dmPolicyVision = Intrinsics.areEqual(d4.getActivePolicy().toString(), "VISION");
                this.dmRhd = d4.getIsRHD();
                Log.DriverMonitoringState.VisionPolicyState.Reader v3 = d4.getVisionPolicyState();
                this.dmFace = v3.getFaceDetected();
                this.dmAwareness = v3.getAwarenessPercent() & 255;
                Log.DriverMonitoringState.VisionPolicyState.Pose.Reader p = v3.getPose();
                this.dmPitch = p.getPitch();
                this.dmYaw = p.getYaw();
                Unit unit14 = Unit.INSTANCE;
                return;
            case 15:
                Custom.StarPilotPlan.Reader p2 = evt.getStarpilotPlan();
                this.slcSpeedLimit = p2.getSlcSpeedLimit();
                this.slcOffset = p2.getSlcSpeedLimitOffset();
                this.slcSource = p2.getSlcSpeedLimitSource().toString();
                this.slcMapbox = p2.getSlcMapboxSpeedLimit();
                this.slcMapSpeedLimit = p2.getSlcMapSpeedLimit();
                this.slcUnconfirmed = p2.getUnconfirmedSlcSpeedLimit();
                this.slcChanged = p2.getSpeedLimitChanged();
                this.slcOverridden = p2.getSlcOverriddenSpeed();
                this.slcNext = p2.getSlcNextSpeedLimit();
                this.forcingStop = p2.getForcingStop();
                this.forcingStopLength = p2.getForcingStopLength();
                this.redLight = p2.getRedLight();
                this.approachStopLength = p2.getApproachStopLength();
                this.stopSignConfirmed = p2.getStopSignConfirmed();
                this.cscSpeed = p2.getCscSpeed();
                this.cscTraining = p2.getCscTraining();
                this.cscControlling = p2.getCscControllingSpeed();
                this.roadCurvature = p2.getRoadCurvature();
                this.planVCruise = p2.getVCruise();
                this.trackingLead = p2.getTrackingLead();
                this.laneWidthLeft = p2.getLaneWidthLeft();
                this.laneWidthRight = p2.getLaneWidthRight();
                this.desiredFollowDistance = p2.getDesiredFollowDistance();
                this.pulseGlideCoasting = p2.getPulseGlideCoasting();
                if (getSp7()) {
                    float f14 = this.vCruise;
                    float set = (1.0f > f14 ? 1 : (1.0f == f14 ? 0 : -1)) <= 0 && (f14 > 250.0f ? 1 : (f14 == 250.0f ? 0 : -1)) <= 0 ? this.vCruise / 3.6f : this.cruiseSpeed;
                    float tgt = (!this.cruiseEnabled || set <= 0.0f) ? 0.0f : set;
                    if (tgt > 0.0f && this.slcSpeedLimit > 0.0f) {
                        tgt = Math.min(tgt, this.slcSpeedLimit + this.slcOffset);
                    }
                    if (tgt > 0.0f && this.cscControlling && this.cscSpeed > 0.0f) {
                        tgt = Math.min(tgt, this.cscSpeed);
                    }
                    this.planVCruise = tgt;
                    this.trackingLead = this.leadOne.getStatus();
                    boolean pending = this.slcNext > 0.0f && Math.abs(this.slcNext - this.slcSpeedLimit) > 0.3f;
                    this.slcChanged = pending;
                    this.slcUnconfirmed = pending ? this.slcNext : 0.0f;
                }
                this.weatherId = p2.getWeatherId();
                this.weatherDay = p2.getWeatherDaytime();
                String ts = p2.getStarpilotToggles().toString();
                Intrinsics.checkNotNullExpressionValue(ts, "toString(...)");
                if ((ts.length() > 0) && !Intrinsics.areEqual(ts, this.lastTogglesStr)) {
                    this.lastTogglesStr = ts;
                    try {
                        JSONObject j2 = new JSONObject(ts);
                        HashMap m2 = new HashMap();
                        Iterator<String> keys = j2.keys();
                        Intrinsics.checkNotNullExpressionValue(keys, "keys(...)");
                        while (keys.hasNext()) {
                            String k = keys.next();
                            m2.put(k, j2.opt(k));
                        }
                        this.toggles = m2;
                    } catch (Exception e2) {
                    }
                }
                Unit unit15 = Unit.INSTANCE;
                return;
            case 16:
                try {
                    SlcPeek.LaneCentering it4 = SlcPeek.laneCentering(evt.getStarpilotLateralState());
                    if (it4 != null) {
                        this.lc = it4;
                        this.lcAtNs = System.nanoTime();
                        Unit unit16 = Unit.INSTANCE;
                        Unit unit17 = Unit.INSTANCE;
                        return;
                    }
                    return;
                } catch (Throwable th5) {
                    Unit unit18 = Unit.INSTANCE;
                    return;
                }
            case 17:
                this.learnedDelay = evt.getLiveDelay().getLateralDelay();
                this.delayAtNs = System.nanoTime();
                Unit unit19 = Unit.INSTANCE;
                return;
            case 18:
                Log.LiveTorqueParametersData.Reader q = evt.getLiveTorqueParameters();
                this.torqueUseParams = q.getUseParams();
                this.frictionLearned = q.getFrictionCoefficientFiltered();
                this.latAccelLearned = q.getLatAccelFactorFiltered();
                this.torqueAtNs = System.nanoTime();
                Unit unit20 = Unit.INSTANCE;
                return;
            case 19:
                try {
                    Car.CarParams.Reader cp = evt.getCarParams();
                    this.baseDelay = cp.getSteerActuatorDelay();
                    Car.CarParams.LateralTuning.Reader lt = cp.getLateralTuning();
                    if (lt.which() == Car.CarParams.LateralTuning.Which.TORQUE) {
                        this.baseFriction = lt.getTorque().getFriction();
                        this.baseLatAccel = lt.getTorque().getLatAccelFactor();
                    }
                } catch (Throwable th6) {
                }
                Unit unit21 = Unit.INSTANCE;
                return;
            case 20:
                Custom.StarPilotCarState.Reader c3 = evt.getStarpilotCarState();
                this.pauseLateral = c3.getPauseLateral();
                this.pauseLongitudinal = c3.getPauseLongitudinal();
                this.forceCoast = c3.getForceCoast();
                this.pulseAndGlide = c3.getPulseAndGlide();
                this.brakeLights = c3.getBrakeLights();
                this.dashSpeedLimit = c3.getDashboardSpeedLimit();
                this.trafficMode = c3.getTrafficModeEnabled();
                this.aolEnabled = c3.getAlwaysOnLateralEnabled();
                Unit unit22 = Unit.INSTANCE;
                return;
            case 21:
                Log.LivePose.Reader lp3 = evt.getLivePose();
                Log.LivePose.XYZMeasurement.Reader av = lp3.getAngularVelocityDevice();
                if (lp3.getSensorsOK() && av.getValid()) {
                    float z20 = av.getZ();
                    if ((Float.isInfinite(z20) || Float.isNaN(z20)) ? false : true) {
                        this.poseYawZ = av.getZ();
                        this.poseAtNs = System.nanoTime();
                    }
                }
                tick("livePose");
                this.poseOK = lp3.getSensorsOK();
                this.poseValid = av.getValid();
                this.poseGyro = new float[]{av.getX(), av.getY(), av.getZ()};
                Log.LivePose.XYZMeasurement.Reader ac = lp3.getAccelerationDevice();
                this.poseAccel = new float[]{ac.getX(), ac.getY(), ac.getZ()};
                if (this.turnPoseMono != 0) {
                    double it5 = (evt.getLogMonoTime() - this.turnPoseMono) / 1.0E9d;
                    if (it5 >= 0.0d && it5 <= 0.5d) {
                        d = it5;
                    }
                }
                double dtp = d;
                this.turnPoseMono = evt.getLogMonoTime();
                if (lp3.getSensorsOK() && av.getValid()) {
                    float z21 = av.getZ();
                    if (((Float.isInfinite(z21) || Float.isNaN(z21)) ? false : true) && this.vEgo > this.TURN_MIN_V && !Intrinsics.areEqual(this.gear, "reverse")) {
                        this.turnGyroDeg += Math.toDegrees(Odometry.INSTANCE.getGyroSign() * av.getZ() * dtp);
                    }
                }
                Unit unit23 = Unit.INSTANCE;
                return;
            case 22:
                Log.SensorEventData.Reader a4 = evt.getAccelerometer();
                tick("accelerometer");
                if (a4.which() == Log.SensorEventData.Which.ACCELERATION) {
                    PrimitiveList.Float.Reader v4 = a4.getAcceleration().getV();
                    Intrinsics.checkNotNullExpressionValue(v4, "getV(...)");
                    this.accelRaw = fl(v4);
                    String lowerCase11 = a4.getSource().name().toLowerCase(Locale.ROOT);
                    Intrinsics.checkNotNullExpressionValue(lowerCase11, "toLowerCase(...)");
                    this.accelSrc = lowerCase11;
                }
                Unit unit24 = Unit.INSTANCE;
                return;
            case 23:
                Log.SensorEventData.Reader g = evt.getGyroscope();
                tick("gyroscope");
                Log.SensorEventData.Which which2 = g.which();
                switch (which2 == null ? -1 : WhenMappings.$EnumSwitchMapping$0[which2.ordinal()]) {
                    case 1:
                        v = g.getGyro().getV();
                        break;
                    case 2:
                        v = g.getGyroUncalibrated().getV();
                        break;
                    default:
                        v = null;
                        break;
                }
                if (v != null) {
                    this.gyroRaw = fl(v);
                    String lowerCase12 = g.getSource().name().toLowerCase(Locale.ROOT);
                    Intrinsics.checkNotNullExpressionValue(lowerCase12, "toLowerCase(...)");
                    this.gyroSrc = lowerCase12;
                }
                Unit unit25 = Unit.INSTANCE;
                return;
            case 24:
                Custom.MapdOut.Reader o = evt.getMapdOut();
                this.roadName = o.getRoadName().toString();
                this.mapdWayName = o.getWayName().toString();
                this.mapdWayRef = o.getWayRef().toString();
                this.mapdLanes = o.getLanes() & 255;
                this.mapdOneWay = o.getOneWay();
                try {
                    z6 = Intrinsics.areEqual(o.getRoadContext().toString(), "FREEWAY");
                } catch (Throwable th7) {
                    z6 = false;
                }
                this.mapdFreeway = z6;
                if (!(this.mapdWayName.length() > 0)) {
                    if (!(this.roadName.length() > 0)) {
                        r13 = false;
                    }
                }
                this.mapdMatched = r13;
                this.mapdAtNs = System.nanoTime();
                Unit unit26 = Unit.INSTANCE;
                return;
            case 25:
            case 26:
                Log.GpsLocationData.Reader g2 = evt.which() == Log.Event.Which.GPS_LOCATION ? evt.getGpsLocation() : evt.getGpsLocationExternal();
                boolean ext = evt.which() == Log.Event.Which.GPS_LOCATION_EXTERNAL;
                long nowMs = System.currentTimeMillis();
                try {
                    str = g2.getSource().name().toLowerCase(Locale.ROOT);
                    Intrinsics.checkNotNullExpressionValue(str, "toLowerCase(...)");
                } catch (Exception e3) {
                    str = "?";
                }
                String srcName = str;
                String key = (ext ? "gpsLocationExternal " : "gpsLocation ") + srcName;
                this.gpsFixes.put(key, new GpsFixInfo(ext ? "gpsLocationExternal" : "gpsLocation", srcName, g2.getHasFix(), g2.getSatelliteCount(), g2.getLatitude(), g2.getLongitude(), g2.getHorizontalAccuracy(), g2.getSpeed(), g2.getBearingDeg(), g2.getBearingAccuracyDeg(), System.nanoTime(), g2.getAltitude(), g2.getVerticalAccuracy(), g2.getUnixTimestampMillis()));
                tick(key);
                if (ext && (this.turnGpsSrc == null || Intrinsics.areEqual(this.turnGpsSrc, srcName))) {
                    if (g2.getHasFix() && g2.getSpeed() > this.TURN_MIN_V && this.vEgo > this.TURN_MIN_V && !Intrinsics.areEqual(this.gear, "reverse")) {
                        float bearingDeg = g2.getBearingDeg();
                        if ((Float.isInfinite(bearingDeg) || Float.isNaN(bearingDeg)) ? false : true) {
                            this.turnGpsSrc = srcName;
                            if (!Float.isNaN(this.turnLastBearing)) {
                                this.turnGpsDeg -= (((g2.getBearingDeg() - r0) + 540.0f) % 360.0f) - 180.0f;
                            }
                            this.turnLastBearing = g2.getBearingDeg();
                        }
                    }
                    this.turnLastBearing = Float.NaN;
                }
                if (ext && g2.getHasFix()) {
                    this.lastExtFixMs = nowMs;
                }
                if (ext || nowMs - this.lastExtFixMs > 2000) {
                    this.bearingDeg = g2.getBearingDeg();
                    if (g2.getHasFix()) {
                        this.gpsLat = g2.getLatitude();
                        this.gpsLon = g2.getLongitude();
                    }
                    if (g2.getHasFix()) {
                        GpsBridge.INSTANCE.onFix(g2.getLatitude(), g2.getLongitude(), g2.getAltitude(), g2.getSpeed(), g2.getBearingDeg(), g2.getHorizontalAccuracy(), g2.getVerticalAccuracy(), g2.getBearingAccuracyDeg(), g2.getSpeedAccuracy(), srcName, evt.getLogMonoTime());
                    }
                }
                if (evt.which() == Log.Event.Which.GPS_LOCATION_EXTERNAL) {
                    OsmRoads osmRoads = OsmRoads.INSTANCE;
                    try {
                        String lowerCase13 = g2.getSource().name().toLowerCase(Locale.ROOT);
                        Intrinsics.checkNotNullExpressionValue(lowerCase13, "toLowerCase(...)");
                        str2 = lowerCase13 + " " + ((int) g2.getSatelliteCount()) + " sats" + (!g2.getHasFix() ? " NO FIX" : "");
                    } catch (Exception e4) {
                        str2 = "";
                    }
                    osmRoads.setGpsSrc(str2);
                }
                if (g2.getHasFix() && evt.which() == Log.Event.Which.GPS_LOCATION_EXTERNAL) {
                    OsmRoads.onFix$default(OsmRoads.INSTANCE, g2.getLatitude(), g2.getLongitude(), g2.getBearingDeg(), g2.getSpeed(), g2.getHorizontalAccuracy(), g2.getBearingAccuracyDeg(), g2.getUnixTimestampMillis(), evt.getLogMonoTime(), false, 256, null);
                }
                Unit unit27 = Unit.INSTANCE;
                return;
            case 27:
                Log.UbloxGnss.Reader u = evt.getUbloxGnss();
                if (u.which() == Log.UbloxGnss.Which.SAT_REPORT) {
                    StructList.Reader svs = u.getSatReport().getSvs();
                    int size = svs.size();
                    ArrayList arrayList = new ArrayList(size);
                    while (i < size) {
                        int i10 = i;
                        Log.UbloxGnss.SatReport.SatInfo.Reader v5 = ((Reader) svs.get(i10));
                        arrayList.add(new GnssSat(v5.getGnssId() & 255, v5.getSvId() & 255, v5.getCno() & 255, v5.getElevationDeg(), v5.getAzimuthDeg(), v5.getFlagsBitfield()));
                        i++;
                        u = u;
                    }
                    this.gnssSats = arrayList;
                    this.gnssAtNs = System.nanoTime();
                }
                Unit unit28 = Unit.INSTANCE;
                return;
            case 28:
                StructList.Reader pts = evt.getLiveTracks().getPoints();
                int n = pts.size();
                float[] arr = new float[n * 2];
                ArrayList rt = new ArrayList(n);
                for (int i11 = 0; i11 < n; i11++) {
                    Car.RadarData.RadarPoint.Reader q2 = ((Reader) pts.get(i11));
                    arr[i11 * 2] = q2.getDRel();
                    arr[(i11 * 2) + 1] = q2.getYRel();
                    rt.add(new RadarTrack(q2.getTrackId(), q2.getDRel(), q2.getYRel(), q2.getVRel(), q2.getMeasured()));
                }
                this.tracks = arr;
                ArrayList $this$toTypedArray$iv = rt;
                this.radar = (RadarTrack[]) $this$toTypedArray$iv.toArray(new RadarTrack[0]);
                long j3 = this.radarFrame;
                this.radarFrame = 1 + j3;
                Long.valueOf(j3);
                return;
            default:
                try {
                    SlcPeek.Slc it6 = SlcPeek.slcState(evt);
                    if (it6 != null) {
                        this.slc = it6;
                        this.slcAtNs = System.nanoTime();
                        Unit unit29 = Unit.INSTANCE;
                        Unit unit30 = Unit.INSTANCE;
                        return;
                    }
                    return;
                } catch (Throwable th8) {
                    Unit unit31 = Unit.INSTANCE;
                    return;
                }
        }
    }
}
