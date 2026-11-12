package com.starpilot.starview;

import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Build.VERSION;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.SurfaceHolder.Callback;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts.OpenDocument;
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.ColorUtils;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.core.view.WindowInsetsCompat.Type;
import java.io.File;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Triple;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.Grouping;
import kotlin.collections.GroupingKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref.BooleanRef;
import kotlin.jvm.internal.Ref.FloatRef;
import kotlin.jvm.internal.Ref.IntRef;
import kotlin.jvm.internal.Ref.LongRef;
import kotlin.jvm.internal.Ref.ObjectRef;
import kotlin.math.MathKt;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import okhttp3.OkHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: MainActivity.kt */
/* recovered from Vineflower's decompilation (jadx's run() lost blocks) */
public final class MainActivity$uiTick$1 implements Runnable {
    final /* synthetic */ MainActivity this$0;

    MainActivity$uiTick$1(MainActivity $receiver) {
        this.this$0 = $receiver;
    }





    public static final CharSequence run$lambda$27$lambda$24(Entry var0) {
        Intrinsics.checkNotNullParameter(var0, "it");
        Object var1x = var0.getValue();
        Object var2 = var0.getKey();
        return var1x + " " + var2;
    }

    public static final String run$lambda$5(MainActivity var0, Telemetry var1x) {
        return LaneShots.Companion.caption(var0.laneTracker, "every 30 s", var1x.getVEgo() * 2.23694F);
    }

    /* VF: from Vineflower (jadx: Code restructure failed) */
    @Override
    public void run() {
        boolean var164 = false; LaneMapLog var312 = null; List var341 = null; String var415 = null; String var454 = null; List var547 = null; String var551 = null; TextView var626 = null; String var639 = null; Object var653 = null; String var667 = null; List var698 = null; String var710 = null; // declarations Vineflower left out
        int var19 = this.this$0.tick;
        this.this$0.tick = var19 + 1;
        Telemetry var58 = this.this$0.telemetry;
        DataClient var54 = this.this$0.data;
        DataClient var53 = var54;
        if (var54 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("data");
            var53 = null;
        }

        boolean var29 = var53.getConnected();
        this.this$0.saverTick(var29);
        LinearLayout var461 = this.this$0.connectBar;
        LinearLayout var242 = var461;
        if (var461 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("connectBar");
            var242 = null;
        }

        int var107;
        if (var29) {
            var107 = 8;
        } else {
            var107 = 0;
        }

        var242.setVisibility(var107);
        Object var243 = (Number)var58.getInfo().get("encStall");
        if (var243 instanceof Number) {
            var243 = var243;
        } else {
            var243 = null;
        }

        if (var243 != null) {
            var107 = ((Number) var243).intValue();
        } else {
            var107 = 0;
        }

        if (var29 && var107 > 0) {
            this.this$0
                .getStallBanner()
                .setText("Comma video encoder stopped " + var107 + " s ago: no drive recording, no camera video.\nReboot the comma when parked.");
            this.this$0.getStallBanner().setVisibility(0);
        } else if (this.this$0.getStallBanner().getVisibility() != 8) {
            this.this$0.getStallBanner().setVisibility(8);
        }

        int var109;
        if (var29 && var58.getStarted() && var58.pBool("GpuQuiet", false)) {
            var109 = 1;
        } else {
            var109 = 0;
        }

        int var20;
        if (!var29 || !var58.getStarted() || (var109 == 0) && !Intrinsics.areEqual(Telemetry.pStr$default(var58, "ChestnutState", null, 2, null), "loading")) {
            var20 = 0;
        } else {
            var20 = 1;
        }

        if ((var20 != 0)) {
            long var51 = System.currentTimeMillis();
            if (this.this$0.gpuLoadSince == 0L) {
                this.this$0.gpuLoadSince = var51;
            }

            long var49 = (var51 - this.this$0.gpuLoadSince) / 1000;
            TextView var462 = this.this$0.getGpuBanner();
            String var245;
            if ((var109 != 0)) {
                var245 = "\ncamera video back when it's ready";
            } else {
                var245 = "";
            }

            var462.setText("⏳  Big model still loading… " + var49 + " s" + var245);
            this.this$0.getGpuBanner().setAlpha((float)((Math.cos(var51 / 1000.0 * 4.0) * 0.5 + 0.5) * 0.55 + 0.45));
            this.this$0.getGpuBanner().setVisibility(0);
        } else {
            this.this$0.gpuLoadSince = 0L;
            if (this.this$0.getGpuBanner().getVisibility() != 8) {
                this.this$0.getGpuBanner().setVisibility(8);
            }
        }

        if (this.this$0.tick % 20 == 0) {
            Function0 var246 = this.this$0.tabletModePusher;
            if (var246 != null) {
                var246.invoke();
                Unit var247 = Unit.INSTANCE;
            }
        }

        if (this.this$0.tick % 100 == 50) {
            this.this$0.checkUsbReturn();
        }

        if (var29) {
            this.this$0.navTick(System.currentTimeMillis());
        }

        MainActivity var55 = this.this$0;
        UiSettings var463 = this.this$0.settings;
        UiSettings var248 = var463;
        if (var463 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("settings");
            var248 = null;
        }

        var55.statsVisible = var248.get("stats");
        TextView var464 = this.this$0.statsTv;
        TextView var249 = var464;
        if (var464 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("statsTv");
            var249 = null;
        }

        if (this.this$0.statsVisible) {
            var109 = 0;
        } else {
            var109 = 8;
        }

        boolean var28;
        label2294: {
            var249.setVisibility(var109);
            if (var29 && !var58.getStarted()) {
                UiSettings var465 = this.this$0.settings;
                UiSettings var250 = var465;
                if (var465 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("settings");
                    var250 = null;
                }

                if (var250.get("offroad_home") && this.this$0.hudEditor == null) {
                    var28 = true;
                    break label2294;
                }
            }

            var28 = false;
        }

        if (var28 != this.this$0.homeShown) {
            this.this$0.homeShown = var28;
            OffroadHome var466 = this.this$0.offroadHome;
            OffroadHome var251 = var466;
            if (var466 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("offroadHome");
                var251 = null;
            }

            if (var28) {
                var109 = 0;
            } else {
                var109 = 8;
            }

            var251.setVisibility(var109);
            LinearLayout var467 = this.this$0.sidebar;
            LinearLayout var252 = var467;
            if (var467 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("sidebar");
                var252 = null;
            }

            if (var28) {
                var109 = 0;
            } else {
                var109 = 8;
            }

            var252.setVisibility(var109);
        }

        if (var28) {
            OffroadHome var468 = this.this$0.offroadHome;
            OffroadHome var253 = var468;
            if (var468 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("offroadHome");
                var253 = null;
            }

            OffroadHome.refresh$default(var253, var58, false, 2, null);
        }

        if (var28 && this.this$0.tick % 600 == 30) {
            KeepDrive.INSTANCE.applyPending((Context)this.this$0);
        }

        StorageInfo var632 = StorageInfo.INSTANCE;
        OffroadHome var469 = this.this$0.offroadHome;
        OffroadHome var254 = var469;
        if (var469 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("offroadHome");
            var254 = null;
        }

        int var113;
        if (var632.copying(var254.getStorage()) != null) {
            var113 = 1;
        } else {
            var113 = 0;
        }

        label2279:
        if (var28) {
            if (this.this$0.tick % 300 != 5) {
                OffroadHome var470 = this.this$0.offroadHome;
                OffroadHome var255 = var470;
                if (var470 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("offroadHome");
                    var255 = null;
                }

                if (var255.getStorage() != null && (var113 == 0) || this.this$0.tick % 40 != 5) {
                    break label2279;
                }
            }

            this.this$0.fetchStorage();
        }

        TextView var471 = this.this$0.statusTv;
        TextView var256 = var471;
        if (var471 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("statusTv");
            var256 = null;
        }

        if ((!var29 || !var58.getStarted()) && !var28) {
            var113 = 0;
        } else {
            var113 = 8;
        }

        var256.setVisibility(var113);
        MainActivity var257 = this.this$0;
        if (var29 && var58.getStarted()) {
            var28 = true;
        } else {
            var28 = false;
        }

        var257.syncScene(var28);
        int var115;
        if (var29 && var58.getStarted() && !this.this$0.sceneOn && this.this$0.visible) {
            var115 = 1;
        } else {
            var115 = 0;
        }

        if ((var115 != 0) && !this.this$0.videoConnected) {
            this.this$0.startVideo();
        }

        if ((var115 == 0) && this.this$0.videoConnected) {
            this.this$0.stopVideo();
        }

        label2245: {
            label2244: {
                var55 = this.this$0;
                if (this.this$0.visible && var29 && var58.getStarted()) {
                    UiSettings var472 = this.this$0.settings;
                    UiSettings var258 = var472;
                    if (var472 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("settings");
                        var258 = null;
                    }

                    if (var258.get("detector_test") || this.this$0.sceneOn || this.this$0.maskEditor != null) {
                        break label2244;
                    }

                    NewIdeas var56 = NewIdeas.INSTANCE;
                    UiSettings var473 = this.this$0.settings;
                    UiSettings var259 = var473;
                    if (var473 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("settings");
                        var259 = null;
                    }

                    if (var56.needsCamera(var259)) {
                        break label2244;
                    }
                }

                var28 = false;
                break label2245;
            }

            var28 = true;
        }

        var55.syncDetector(var28);
        OsmRoads var634 = OsmRoads.INSTANCE;
        UiSettings var474 = this.this$0.settings;
        UiSettings var260 = var474;
        if (var474 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("settings");
            var260 = null;
        }

        var634.setEnabled(var260.get("map_roads"));
        OverlayView var475 = this.this$0.overlay;
        OverlayView var261 = var475;
        if (var475 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("overlay");
            var261 = null;
        }

        if (var261.updateCalibration()) {
            this.this$0.placeVideo();
        }

        OverlayView var476 = this.this$0.overlay;
        OverlayView var262 = var476;
        if (var476 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("overlay");
            var262 = null;
        }

        var262.setUiStatus(UiStatus.Companion.from(var58));
        if (System.currentTimeMillis() - this.this$0.lastThemeCheck > 15000L) {
            this.this$0.lastThemeCheck = System.currentTimeMillis();
            this.this$0.updateTheme();
        }

        var28 = var58.isMetric();
        float var9;
        if (var28) {
            var9 = 3.6F;
        } else {
            var9 = 2.23694F;
        }

        float var10;
        if (!var58.tBool("use_wheel_speed", false) && !(var58.getVEgoCluster() <= 0.0F)) {
            var10 = var58.getVEgoCluster();
        } else {
            var10 = var58.getVEgo();
        }
        var10 = SpeedShown.mps(var58, var10);

        TextView var477 = this.this$0.speedTv;
        TextView var263 = var477;
        if (var477 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("speedTv");
            var263 = null;
        }

        var263.setText(String.valueOf(RangesKt.coerceAtLeast(MathKt.roundToInt(var10 * var9), 0)));
        TextView var478 = this.this$0.speedUnit;
        TextView var264 = var478;
        if (var478 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("speedUnit");
            var264 = null;
        }

        String var479;
        if (var28) {
            var479 = "km/h";
        } else {
            var479 = "mph";
        }

        var264.setText(var479);
        if (var58.s("show_speed") && var58.getStarted()) {
            var115 = (((boolean)false) ? 1 : 0);
        } else {
            var115 = (((boolean)true) ? 1 : 0);
        }

        TextView var480 = this.this$0.speedTv;
        TextView var265 = var480;
        if (var480 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("speedTv");
            var265 = null;
        }

        if ((var115 != 0)) {
            var115 = 4;
        } else {
            var115 = 0;
        }

        var265.setVisibility(var115);
        TextView var481 = this.this$0.speedUnit;
        TextView var266 = var481;
        if (var481 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("speedUnit");
            var266 = null;
        }

        TextView var635 = this.this$0.speedTv;
        TextView var482 = var635;
        if (var635 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("speedTv");
            var482 = null;
        }

        var266.setVisibility(var482.getVisibility());
        if (var58.getVCruiseCluster() > 0.0F) {
            var9 = var58.getVCruiseCluster();
        } else {
            var9 = var58.getVCruise();
        }

        int var118;
        if (var58.getCruiseEnabled() && var9 > 0.0F && var9 < 200.0F) {
            var118 = 1;
        } else {
            var118 = 0;
        }

        float var11 = var58.tFloat("set_speed_offset", 0.0F);
        TextView var483 = this.this$0.setSpeedTv;
        TextView var267 = var483;
        if (var483 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("setSpeedTv");
            var267 = null;
        }

        String var484;
        if ((var118 != 0)) {
            if (var28) {
                var10 = 1.0F;
            } else {
                var10 = 0.621371F;
            }

            var484 = String.valueOf(MathKt.roundToInt((var9 + var11) * var10));
        } else {
            var484 = "–";
        }

        var267.setText(var484);
        TextView var485 = this.this$0.setSpeedTv;
        TextView var268 = var485;
        if (var485 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("setSpeedTv");
            var268 = null;
        }

        if ((var118 != 0)) {
            var20 = -1;
        } else {
            var20 = Color.rgb(114, 114, 114);
        }

        var268.setTextColor(var20);
        TextView var486 = this.this$0.maxLabel;
        TextView var269 = var486;
        if (var486 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("maxLabel");
            var269 = null;
        }

        if ((var118 == 0)) {
            var118 = Color.rgb(166, 166, 166);
        } else if (var58.getEnabled() && var58.getActive()) {
            var118 = Color.rgb(128, 216, 166);
        } else {
            var118 = Color.rgb(145, 155, 149);
        }

        var269.setTextColor(var118);
        LinearLayout var487 = this.this$0.maxCard;
        LinearLayout var270 = var487;
        if (var487 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("maxCard");
            var270 = null;
        }

        int var120;
        if (var58.s("show_max") && var58.getStarted()) {
            var120 = 0;
        } else {
            var120 = 4;
        }

        var270.setVisibility(var120);
        SlcView var488 = this.this$0.slc;
        SlcView var271 = var488;
        if (var488 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("slc");
            var271 = null;
        }

        if (var58.getStarted() && var58.s("speed_limit")) {
            var120 = (byte)0;
        } else {
            var120 = (byte)4;
        }

        var271.setVisibility(var120);
        WheelButton var489 = this.this$0.wheel;
        WheelButton var272 = var489;
        if (var489 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("wheel");
            var272 = null;
        }

        if (var58.s("wheel_button") && var58.getStarted()) {
            var120 = (byte)0;
        } else {
            var120 = (byte)4;
        }

        var272.setVisibility(var120);
        MiciDistanceView var490 = this.this$0.personality;
        MiciDistanceView var273 = var490;
        if (var490 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("personality");
            var273 = null;
        }

        if (var58.getStarted() && var58.s("personality_button")) {
            var120 = (byte)0;
        } else {
            var120 = (byte)8;
        }

        var273.setVisibility(var120);
        SlcActionsView var491 = this.this$0.slcActions;
        SlcActionsView var274 = var491;
        if (var491 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("slcActions");
            var274 = null;
        }

        label2156: {
            if (var58.getStarted() && var58.s("speed_limit")) {
                SlcActionsView var636 = this.this$0.slcActions;
                SlcActionsView var492 = var636;
                if (var636 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("slcActions");
                    var492 = null;
                }

                if (!SlcActionsView.controls$default(var492, 0L, 1, null).isEmpty()) {
                    var120 = (byte)0;
                    break label2156;
                }
            }

            var120 = (byte)8;
        }

        var274.setVisibility(var120);
        Pair var275;
        if (var58.getStarted() && var58.s("csc_glow")) {
            var275 = DrivingMode.curveLabel$default(DrivingMode.INSTANCE, var58, 0L, 2, null);
        } else {
            var275 = null;
        }

        TextView var637 = this.this$0.curveLabel;
        TextView var493 = var637;
        if (var637 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("curveLabel");
            var493 = null;
        }

        label2143: {
            if (var275 != null) {
                String var638 = (String)var275.getFirst();
                if (var638 != null) {
                    var639 = var638;
                    break label2143;
                }
            }

            var639 = "";
        }

        var493.setText(var639);
        if (var275 != null) {
            TextView var640 = this.this$0.curveLabel;
            TextView var494 = var640;
            if (var640 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("curveLabel");
                var494 = null;
            }

            var494.setTextColor(((Number)var275.getSecond()).intValue());
            Unit var495 = Unit.INSTANCE;
            Unit var496 = Unit.INSTANCE;
        }

        TextView var641 = this.this$0.curveLabel;
        TextView var497 = var641;
        if (var641 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("curveLabel");
            var497 = null;
        }

        if (var275 != null) {
            var120 = (byte)0;
        } else {
            var120 = (byte)8;
        }

        var497.setVisibility(var120);
        var28 = Intrinsics.areEqual(var58.getAlertSize(), "none");
        TextView var498 = this.this$0.clockTv;
        TextView var276 = var498;
        if (var498 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("clockTv");
            var276 = null;
        }

        if (var58.getStarted() && var58.s("clock") && var28) {
            var120 = (byte)0;
        } else {
            var120 = (byte)8;
        }

        var276.setVisibility(var120);
        TextView var499 = this.this$0.clockTv;
        TextView var277 = var499;
        if (var499 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("clockTv");
            var277 = null;
        }

        if (var277.getVisibility() == 0) {
            TextView var500 = this.this$0.clockTv;
            TextView var278 = var500;
            if (var500 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("clockTv");
                var278 = null;
            }

            var278.setText(DrivingMode.clock$default(DrivingMode.INSTANCE, null, var58.s("clock_24h"), 1, null));
        }

        TextView var501 = this.this$0.modeText;
        TextView var279 = var501;
        if (var501 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("modeText");
            var279 = null;
        }

        if (var58.getStarted() && var58.s("mode_text") && var28) {
            var120 = (byte)0;
        } else {
            var120 = (byte)8;
        }

        var279.setVisibility(var120);
        TextView var502 = this.this$0.modeText;
        TextView var280 = var502;
        if (var502 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("modeText");
            var280 = null;
        }

        if (var280.getVisibility() == 0) {
            TextView var503 = this.this$0.modeText;
            TextView var281 = var503;
            if (var503 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("modeText");
                var281 = null;
            }

            var281.setText(DrivingMode.INSTANCE.describe(var58));
        }

        DevSidebarView var504 = this.this$0.devSidebar;
        DevSidebarView var282 = var504;
        if (var504 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("devSidebar");
            var282 = null;
        }

        if (var58.getStarted() && var58.s("dev_sidebar")) {
            var120 = (byte)0;
        } else {
            var120 = (byte)8;
        }

        var282.setVisibility(var120);
        SideCam var505 = SideCam.INSTANCE;
        String var283 = this.this$0.host;
        var120 = this.this$0.port;
        var11 = 0.0F;
        SideCam.tick$default(var505, var58, var283, var120, 0L, 8, null);
        List var642 = SideCam.INSTANCE.wanted(var58);
        SideView var506 = this.this$0.pipLeft;
        SideView var284 = var506;
        if (var506 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pipLeft");
            var284 = null;
        }

        byte var130;
        if (var642.contains("left")) {
            var130 = 0;
        } else {
            var130 = 8;
        }

        var284.setVisibility(var130);
        SideView var507 = this.this$0.pipRight;
        SideView var285 = var507;
        if (var507 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pipRight");
            var285 = null;
        }

        if (var642.contains("right")) {
            var130 = 0;
        } else {
            var130 = 8;
        }

        var285.setVisibility(var130);
        String var643 = Telemetry.pStr$default(var58, "ChestnutState", null, 2, null);
        int var132;
        if (var643.length() > 0) {
            var132 = 1;
        } else {
            var132 = 0;
        }

        if ((var132 != 0)) {
            label2061: {
                if (var58.getStarted() && var58.s("egpu_status")) {
                    String[] var286 = new String[]{"active", "loading", "failed"};
                    if (SetsKt.setOf(var286).contains(var643)) {
                        var132 = (((boolean)true) ? 1 : 0);
                        break label2061;
                    }
                }

                var132 = (((boolean)false) ? 1 : 0);
            }

            ImageView var508 = this.this$0.egpu;
            ImageView var287 = var508;
            if (var508 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("egpu");
                var287 = null;
            }

            byte var187;
            if ((var132 != 0)) {
                var187 = 0;
            } else {
                var187 = 8;
            }

            var287.setVisibility(var187);
            if ((var132 != 0)) {
                if (Intrinsics.areEqual(var643, "loading")) {
                    ImageView var509 = this.this$0.egpu;
                    ImageView var288 = var509;
                    if (var509 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("egpu");
                        var288 = null;
                    }

                    var288.setImageResource(R.drawable.egpu_loading);
                    ImageView var510 = this.this$0.egpu;
                    ImageView var289 = var510;
                    if (var510 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("egpu");
                        var289 = null;
                    }

                    var289.setAlpha((float)((0.5 - Math.cos(System.nanoTime() / 1.0E9 * 6.0) * 0.5) * 0.65 + 0.35));
                } else if (Intrinsics.areEqual(var643, "failed")) {
                    ImageView var511 = this.this$0.egpu;
                    ImageView var290 = var511;
                    if (var511 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("egpu");
                        var290 = null;
                    }

                    var290.setImageResource(R.drawable.egpu_orange);
                    ImageView var512 = this.this$0.egpu;
                    ImageView var291 = var512;
                    if (var512 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("egpu");
                        var291 = null;
                    }

                    var291.setAlpha(1.0F);
                } else {
                    ImageView var513 = this.this$0.egpu;
                    ImageView var292 = var513;
                    if (var513 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("egpu");
                        var292 = null;
                    }

                    var292.setImageResource(R.drawable.egpu_green);
                    ImageView var514 = this.this$0.egpu;
                    ImageView var293 = var514;
                    if (var514 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("egpu");
                        var293 = null;
                    }

                    var293.setAlpha(1.0F);
                }
            }
        } else {
            var55 = this.this$0;
            MainActivity$uiTick$1 var294 = this;
            if (var58.getChestnutPresent() || var58.getStarted() && var55.egpuSeen) {
                var28 = true;
            } else {
                var28 = false;
            }

            var55.egpuSeen = var28;
            if (var58.getStarted() && var58.s("egpu_status") && var28 && var58.pBool("UsbGpuCompiled", false)) {
                var132 = (((boolean)true) ? 1 : 0);
            } else {
                var132 = (((boolean)false) ? 1 : 0);
            }

            if ((var132 == 0)) {
                ImageView var304 = var55.egpu;
                if (var304 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("egpu");
                    var304 = null;
                }

                var304.setVisibility(8);
                var55.egpuFallback = false;
            } else {
                var29 = var58.pBool("UsbGpuLoading", false);
                var28 = var58.pBool("UsbGpuActive", false);
                if (var58.getModelSeen() && System.nanoTime() - var58.getLastModelRxNs() < 1000000000L) {
                    var132 = (((boolean)true) ? 1 : 0);
                } else {
                    var132 = (((boolean)false) ? 1 : 0);
                }

                if (!var28 || var58.getModelSeen() && (var132 == 0)) {
                    var132 = (((boolean)true) ? 1 : 0);
                } else {
                    var132 = (((boolean)false) ? 1 : 0);
                }

                if (var58.getEnabled() && !var55.egpuWasEngaged && !var29 && !var28 && var58.getModelSeen()) {
                    var55.egpuFallback = true;
                }

                var55.egpuWasEngaged = var58.getEnabled();
                if (var55.egpuFallback && (var132 != 0)) {
                    var28 = true;
                } else {
                    var28 = false;
                }

                var55.egpuFallback = var28;
                ImageView var515 = var55.egpu;
                ImageView var295 = var515;
                if (var515 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("egpu");
                    var295 = null;
                }

                var295.setVisibility(0);
                if (var29) {
                    ImageView var516 = var55.egpu;
                    ImageView var296 = var516;
                    if (var516 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("egpu");
                        var296 = null;
                    }

                    var296.setImageResource(R.drawable.egpu_loading);
                    ImageView var517 = var55.egpu;
                    ImageView var297 = var517;
                    if (var517 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("egpu");
                        var297 = null;
                    }

                    var297.setAlpha((float)((0.5 - Math.cos(System.nanoTime() / 1.0E9 * 6.0) * 0.5) * 0.65 + 0.35));
                } else if (var55.egpuFallback) {
                    ImageView var518 = var55.egpu;
                    ImageView var298 = var518;
                    if (var518 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("egpu");
                        var298 = null;
                    }

                    var298.setImageResource(R.drawable.egpu_crossed);
                    ImageView var519 = var55.egpu;
                    ImageView var299 = var519;
                    if (var519 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("egpu");
                        var299 = null;
                    }

                    var299.setAlpha(0.65F);
                } else if ((var132 != 0)) {
                    ImageView var520 = var55.egpu;
                    ImageView var300 = var520;
                    if (var520 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("egpu");
                        var300 = null;
                    }

                    var300.setImageResource(R.drawable.egpu_orange);
                    ImageView var521 = var55.egpu;
                    ImageView var301 = var521;
                    if (var521 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("egpu");
                        var301 = null;
                    }

                    var301.setAlpha(1.0F);
                } else {
                    ImageView var522 = var55.egpu;
                    ImageView var302 = var522;
                    if (var522 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("egpu");
                        var302 = null;
                    }

                    var302.setImageResource(R.drawable.egpu_green);
                    ImageView var523 = var55.egpu;
                    ImageView var303 = var523;
                    if (var523 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("egpu");
                        var303 = null;
                    }

                    var303.setAlpha(1.0F);
                }
            }

            Unit var305 = Unit.INSTANCE;
        }

        TextView var524 = this.this$0.roadName;
        TextView var306 = var524;
        if (var524 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("roadName");
            var306 = null;
        }

        CharSequence var525;
        if (var58.getStarted() && var58.s("road_name")) {
            var525 = var58.getRoadName();
        } else {
            var525 = "";
        }

        var306.setText(var525);
        AetherGaugeView var526 = this.this$0.gauge;
        AetherGaugeView var307 = var526;
        if (var526 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("gauge");
            var307 = null;
        }

        if (var58.getStarted() && var58.s("aether_gauge")) {
            var132 = 0;
        } else {
            var132 = 8;
        }

        var307.setVisibility(var132);
        SlcSourcesView var527 = this.this$0.slcSources;
        SlcSourcesView var308 = var527;
        if (var527 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("slcSources");
            var308 = null;
        }

        label2042: {
            if (var58.getStarted() && var58.s("speed_limit")) {
                if (var58.getSp7()) {
                    var28 = var58.s("slc_sources_sp7");
                } else {
                    var28 = var58.pBool("SpeedLimitSources", false);
                }

                if (var28) {
                    var132 = 0;
                    break label2042;
                }
            }

            var132 = 8;
        }

        var308.setVisibility(var132);
        Pair var645 = Telemetry.navCard$default(var58, 0L, 1, null);
        NavCardView var528 = this.this$0.navCard;
        NavCardView var309 = var528;
        if (var528 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("navCard");
            var309 = null;
        }

        if ((var58.getStarted() && var58.s("nav_card") && var645 != null && !this.this$0.getMapOpen()) || this.this$0.hybCardForced()) {
            var132 = 0;
        } else {
            var132 = 8;
        }

        var309.setVisibility(var132);
        this.this$0.hybNavMapTick();
        NavCardView var529 = this.this$0.navCard;
        NavCardView var310 = var529;
        if (var529 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("navCard");
            var310 = null;
        }

        int var140;
        if (var645 != null && (Boolean)var645.getSecond()) {
            var140 = 1;
        } else {
            var140 = 0;
        }

        if ((var140 != 0)) {
            var9 = 0.55F;
        } else {
            var9 = 1.0F;
        }

        var310.setAlpha(var9);
        SlowReasonView var530 = this.this$0.slowCard;
        SlowReasonView var311 = var530;
        if (var530 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("slowCard");
            var311 = null;
        }

        label2020: {
            if (var58.getStarted() && var58.s("slow_card")) {
                SlowReasonView var646 = this.this$0.slowCard;
                SlowReasonView var531 = var646;
                if (var646 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("slowCard");
                    var531 = null;
                }

                if (var531.current() != null) {
                    var140 = (byte)0;
                    break label2020;
                }
            }

            var140 = (byte)8;
        }

        var311.setVisibility(var140);
        if (var58.getStarted()) {
            LaneBrain.Frame var532 = this.this$0.laneFeed.frame(var58, this.this$0.laneMarks, System.currentTimeMillis());
            if (var532 != null) {
                var55 = this.this$0;
                var55.laneTracker.update(var532);
                long var234 = var532.getNowMs();

                label2009: {
                    try {
                        var312 = var55.laneMapLog;
                    } catch (Exception var71) {
                        break label2009;
                    }

                    if (var312 != null) {
                        try {
                            var312.frame(var234, var58.getVEgo(), var532, var55.laneTracker, var55.laneTracker.turning(var234));
                            Unit var313 = Unit.INSTANCE;
                        } catch (Exception var70) {
                        }
                    }
                }

                double[] var314;
                if (RtkPosition.INSTANCE.fast(var234)) {
                    var314 = RtkPosition.INSTANCE.now(var234, var58.getVEgo());
                } else {
                    var314 = null;
                }

                OsmRoads.Fix var692 = OsmRoads.INSTANCE.getLastFix();
                double var1x;
                if (var314 != null) {
                    var1x = var314[0];
                } else if (var692 != null) {
                    var1x = var692.getLat();
                } else {
                    var1x = Double.NaN;
                }

                double var3;
                if (var314 != null) {
                    var3 = var314[1];
                } else if (var692 != null) {
                    var3 = var692.getLon();
                } else {
                    var3 = Double.NaN;
                }

                double var85;
                label2004: {
                    if (var314 != null) {
                        var85 = RtkPosition.INSTANCE.getHeading();
                        boolean var142;
                        if (!Double.isInfinite(var85) && !Double.isNaN(var85)) {
                            var142 = true;
                        } else {
                            var142 = false;
                        }

                        if (var142) {
                            var85 = RtkPosition.INSTANCE.getHeading();
                            break label2004;
                        }
                    }

                    if (var692 != null) {
                        var85 = var692.getBearingDeg();
                    } else {
                        var85 = Double.NaN;
                    }
                }

                try {
                    var55.laneAhead
                        .update(
                            var234, var55.laneTracker.getOdo(), var1x, var3, (Math.PI / 2) - Math.toRadians(var85), var58.getVEgo(), var55.laneTracker, var532
                        );
                } catch (Exception var69) {
                }

                Unit var315 = Unit.INSTANCE;
                Unit var316 = Unit.INSTANCE;
            }
        } else if (this.this$0.laneWasStarted) {
            this.this$0.laneTracker.endDrive();
            this.this$0.laneAhead.reset();
            LaneMapLog var317 = this.this$0.laneMapLog;
            if (var317 != null) {
                var317.close();
                Unit var318 = Unit.INSTANCE;
            }
        }

        if (var58.getStarted()) {
            Detector var319 = this.this$0.detectorRef;
            if (var319 != null) {
                LaneModel var320 = var319.getLaneModel();
                if (var320 != null) {
                    LaneModel.Answer var533 = var320.getAnswer();
                    if (var533 != null) {
                        MainActivity var321 = this.this$0;
                        if (System.currentTimeMillis() - var533.getAtMs() < 5000L) {
                            LaneBrain.modelSaw$default(var321.laneTracker, var533.getPL(), var533.getPR(), var533.getAtMs(), 0L, 8, null);
                        }

                        Unit var322 = Unit.INSTANCE;
                        Unit var323 = Unit.INSTANCE;
                    }
                }
            }
        }

        SceneView var534 = this.this$0.scene;
        if (var534 != null) {
            var55 = this.this$0;
            LaneBrain.Road var324 = var55.laneTracker.getRoad();
            Pair var693 = var55.laneTracker.laneFromLeft();
            var140 = ((Number)var693.component1()).intValue();
            double var72 = ((Number)var693.component2()).doubleValue();
            var20 = var55.laneTracker.laneCount().component1().intValue();
            SceneView.LaneHint var325;
            if (var58.getStarted() && var324 != null && var140 > 0) {
                var325 = new SceneView.LaneHint(var324.getId(), var140 - 1, var20, var72, Double.NaN, System.nanoTime());
            } else {
                var325 = null;
            }

            var534.setLaneHint(var325);
            Unit var326 = Unit.INSTANCE;
            Unit var327 = Unit.INSTANCE;
        }

        if (var58.getStarted() && !this.this$0.laneWasStarted) {
            this.this$0.getLaneShots().newDrive();
        }

        this.this$0.laneWasStarted = var58.getStarted();
        LaneCardView var535 = this.this$0.laneCard;
        LaneCardView var328 = var535;
        if (var535 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("laneCard");
            var328 = null;
        }

        int var144;
        if (var58.getStarted() && var58.s("lane_card")) {
            var144 = 0;
        } else {
            var144 = 8;
        }

        var328.setVisibility(var144);
        this.this$0.getLaneShots().setDetector(this.this$0.detectorRef);
        LaneCardView var536 = this.this$0.laneCard;
        LaneCardView var329 = var536;
        if (var536 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("laneCard");
            var329 = null;
        }

        if (var329.getVisibility() == 0 && var58.getVEgo() > 3.0F) {
            this.this$0.getLaneShots().tick(new MainActivity$uiTick$1$$ExternalSyntheticLambda0(this.this$0, var58));
        }

        BlinkerCardView var537 = this.this$0.blinkCard;
        BlinkerCardView var330 = var537;
        if (var537 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("blinkCard");
            var330 = null;
        }

        DataClient var649 = this.this$0.data;
        var54 = var649;
        if (var649 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("data");
            var54 = null;
        }

        if (var54.getConnected() && var58.s("blink_card")) {
            var144 = (byte)0;
        } else {
            var144 = (byte)8;
        }

        var330.setVisibility(var144);
        GpsCardView var539 = this.this$0.gpsCard;
        GpsCardView var331 = var539;
        if (var539 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("gpsCard");
            var331 = null;
        }

        DataClient var650 = this.this$0.data;
        var54 = var650;
        if (var650 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("data");
            var54 = null;
        }

        if (var54.getConnected() && var58.s("gps_card")) {
            var144 = (byte)0;
        } else {
            var144 = (byte)8;
        }

        var331.setVisibility(var144);
        boolean var31 = var58.s("lane_suggest");
        boolean var35 = var58.s("turn_signals");
        if (var58.s("car_memory")) {
            this.this$0.carMemory.update(var58, System.currentTimeMillis(), var58.getLeftBlindspot(), var58.getRightBlindspot());
        }

        if (var58.getLeftBlindspot() || var58.s("car_memory") && this.this$0.carMemory.beside(-1)) {
            var28 = true;
        } else {
            var28 = false;
        }

        if (var58.getRightBlindspot() || var58.s("car_memory") && this.this$0.carMemory.beside(1)) {
            var29 = true;
        } else {
            var29 = false;
        }

        if (Replay.INSTANCE.getActive() != this.this$0.replayWas) {
            this.this$0.replayWas = Replay.INSTANCE.getActive();
            LaneSuggestView var541 = this.this$0.laneSuggest;
            LaneSuggestView var332 = var541;
            if (var541 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("laneSuggest");
                var332 = null;
            }

            var332.stop();
        }

        label2348: {
            label2349: {
                if (!var58.getStarted()) {
                    LaneSuggestView var542 = this.this$0.laneSuggest;
                    LaneSuggestView var333 = var542;
                    if (var542 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("laneSuggest");
                        var333 = null;
                    }

                    if (!var333.getLogic().getTest()) {
                        LaneSuggestView var543 = this.this$0.laneSuggest;
                        LaneSuggestView var334 = var543;
                        if (var543 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("laneSuggest");
                            var334 = null;
                        }

                        if (var334.getLogic().getState() == LaneSuggest.State.IDLE) {
                            break label2349;
                        }
                    }
                }

                var54 = this.this$0.data;
                DataClient var335 = var54;
                if (var54 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("data");
                    var335 = null;
                }

                if (var335.getConnected() && (var31 || var35)) {
                    ModelSnapshot var651 = var58.getModel();
                    long var235 = System.currentTimeMillis();
                    JSONObject var59 = var58.navState();
                    var10 = var58.tFloat("minimum_lane_change_speed", 8.9F);
                    LaneSuggestView var545 = this.this$0.laneSuggest;
                    LaneSuggestView var336 = var545;
                    if (var545 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("laneSuggest");
                        var336 = null;
                    }

                    var336.setMetric(var58.isMetric());
                    LaneSuggestView var546 = this.this$0.laneSuggest;
                    LaneSuggestView var337 = var546;
                    if (var546 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("laneSuggest");
                        var337 = null;
                    }

                    boolean var30;
                    if (var337.getTurn().getState() == TurnSignal.State.SIGNALING) {
                        var30 = true;
                    } else {
                        var30 = false;
                    }

                    label1911: {
                        if (var59 != null) {
                            Double var338 = var59.optDouble("maneuverDistance", Double.NaN);
                            double var73 = var338.doubleValue();
                            boolean var147;
                            if (!Double.isInfinite(var73) && !Double.isNaN(var73)) {
                                var147 = true;
                            } else {
                                var147 = false;
                            }

                            if (!var147) {
                                var338 = null;
                            }

                            if (var338 != null) {
                                label1901: {
                                    var73 = var338.doubleValue();
                                    Pair var339 = Nav.INSTANCE.nextLanes();
                                    if (var339 != null) {
                                        if (Math.abs(((Number)var339.getSecond()).doubleValue() - var73) < 200.0) {
                                            var147 = true;
                                        } else {
                                            var147 = false;
                                        }

                                        if (!var147) {
                                            var339 = null;
                                        }

                                        if (var339 != null) {
                                            Nav.Step var340 = (Nav.Step)var339.getFirst();
                                            if (var340 != null) {
                                                var341 = var340.getLanes();
                                                break label1901;
                                            }
                                        }
                                    }

                                    var341 = null;
                                }

                                var547 = var341;
                                if (var341 != null) {
                                    break label1911;
                                }
                            }
                        }

                        var547 = CollectionsKt.emptyList();
                    }

                    LaneSuggest.In var344;
                    if (!var31) {
                        var344 = null;
                    } else {
                        Pair var342 = LaneSuggest.Companion.routeNeed(var59, this.this$0.laneTracker, var58.isMetric(), ((List<Nav.Lane>) var547));
                        int var26 = ((Number)var342.component1()).intValue();
                        String var60 = (String)var342.component2();
                        LaneSuggest.Companion.Exit var61 = LaneSuggest.Companion.exitNeed(var59, var58.isMetric());
                        if (var58.getEnabled() && var58.getActive()) {
                            var31 = true;
                        } else {
                            var31 = false;
                        }

                        float var12 = var58.getVEgo();
                        float[] var343;
                        if (var651 != null) {
                            var343 = var651.getDesirePred();
                        } else {
                            var343 = null;
                        }

                        boolean var32;
                        if (var651 != null) {
                            var32 = var651.getLaneChanging();
                        } else {
                            var32 = false;
                        }

                        if (var651 != null) {
                            var144 = var651.getLaneChangeDir();
                        } else {
                            var144 = 0;
                        }

                        boolean var44 = var58.getLeftBlinker();
                        boolean var43 = var58.getRightBlinker();
                        boolean var38 = var58.getBrakePressed();
                        boolean var37 = var58.getSteeringPressed();
                        float var13 = var58.getSteeringTorque();
                        boolean var36 = var58.s("lane_nudge_confirm");
                        boolean var42 = var58.s("lane_auto");
                        LaneSuggestView var652 = this.this$0.laneSuggest;
                        LaneSuggestView var548 = var652;
                        if (var652 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("laneSuggest");
                            var548 = null;
                        }

                        boolean var45 = Intrinsics.areEqual(var548.getCommaConfirms(), true);
                        boolean var40 = var58.s("lane_pass");
                        if (var58.getVCruiseCluster() > 0.0F) {
                            var9 = var58.getVCruiseCluster();
                        } else {
                            var9 = var58.getVCruise();
                        }

                        label1881: {
                            if (var58.getCruiseEnabled()) {
                                boolean var189;
                                if (1.0F <= var9 && var9 <= 200.0F) {
                                    var189 = true;
                                } else {
                                    var189 = false;
                                }

                                if (var189) {
                                    var9 /= 3.6F;
                                    break label1881;
                                }
                            }

                            var9 = 0.0F;
                        }

                        boolean var46 = var58.getLeadOne().getStatus();
                        float var14 = var58.getLeadOne().getDRel();
                        float var18 = var58.getLeadOne().getVLead();
                        LaneBrain var549 = this.this$0.laneTracker;
                        int var23 = -1;
                        Pair var550 = var549.sideLanes(-1);
                        var20 = ((Number)var550.component1()).intValue();
                        double var75 = ((Number)var550.component2()).doubleValue();
                        if (var20 < 0 || !(var75 >= 0.5)) {
                            var20 = -1;
                        }

                        int var27 = LaneSuggest.Companion.routeSoon(var59);
                        boolean var41 = var58.isMetric();
                        boolean var39 = var58.s("exit_signals");
                        int var21;
                        if (var61 != null) {
                            var21 = var61.getDir();
                        } else {
                            var21 = 0;
                        }

                        if (var61 != null) {
                            var75 = var61.getDist();
                        } else {
                            var75 = Double.NaN;
                        }

                        label1865: {
                            if (var61 != null) {
                                var551 = var61.getWhy();
                                if (var551 != null) {
                                    break label1865;
                                }
                            }

                            var551 = "";
                        }

                        label1860: {
                            if (var61 != null) {
                                var653 = var61.getKey();
                                if (var653 != null) {
                                    break label1860;
                                }
                            }

                            var653 = "";
                        }

                        LaneBrain var694 = this.this$0.laneTracker;
                        int var22;
                        if (var61 != null) {
                            var22 = var61.getDir();
                        } else {
                            var22 = 0;
                        }

                        int var202;
                        if (var22 < 0) {
                            var202 = var23;
                        } else {
                            var202 = 1;
                        }

                        Pair var695 = var694.sideLanes(var202);
                        var202 = ((Number)var695.component1()).intValue();
                        double var80 = ((Number)var695.component2()).doubleValue();
                        if (var202 < 0 || !(var80 >= 0.5)) {
                            var202 = -1;
                        }
                        // 2.87.1: a lane that "appears" on the exit's side right before it doesn't signal (ExitGuard)
                        var202 = ExitGuard.side(var22, var202);

                        boolean var33;
                        label1849: {
                            if (var61 != null) {
                                LaneSuggest.Companion var62;
                                LaneBrain var63;
                                label1846: {
                                    var62 = LaneSuggest.Companion;
                                    var63 = this.this$0.laneTracker;
                                    Pair var696 = Nav.INSTANCE.nextLanes();
                                    if (var696 != null) {
                                        var80 = ((Number)var696.getSecond()).doubleValue();
                                        boolean var205;
                                        if (0.0 <= var80 && var80 <= 800.0) {
                                            var205 = true;
                                        } else {
                                            var205 = false;
                                        }

                                        if (!var205) {
                                            var696 = null;
                                        }

                                        if (var696 != null) {
                                            Nav.Step var697 = (Nav.Step)var696.getFirst();
                                            if (var697 != null) {
                                                List var57 = var697.getLanes();
                                                var698 = var57;
                                                if (var57 != null) {
                                                    break label1846;
                                                }
                                            }
                                        }
                                    }

                                    var698 = CollectionsKt.emptyList();
                                }

                                if (var62.inLaneFor(var61, var63, var698)) {
                                    var33 = true;
                                    break label1849;
                                }
                            }

                            var33 = false;
                        }

                        if (var58.s("lane_ends_ask")) {
                            var23 = this.this$0.laneAhead.need(this.this$0.laneTracker).getFirst().intValue();
                        } else {
                            var23 = 0;
                        }

                        String var719 = this.this$0.laneAhead.need(this.this$0.laneTracker).getSecond();
                        UiSettings var713 = this.this$0.settings;
                        UiSettings var699 = var713;
                        if (var713 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("settings");
                            var699 = null;
                        }

                        float var16 = var699.getFloat("nudge_card_pct", 60.0F) * 100.0F / 100.0F;
                        boolean var47 = var58.s("lane_return");
                        Pair var700 = this.this$0.laneTracker.sideLanes(1);
                        int var24 = ((Number)var700.component1()).intValue();
                        var80 = ((Number)var700.component2()).doubleValue();
                        if (var24 < 0 || !(var80 >= 0.5)) {
                            var24 = -1;
                        }

                        boolean var48 = var58.getLeadRight().getStatus();
                        float var17 = var58.getLeadRight().getDRel();
                        float var15 = var58.getLeadRight().getVLead();
                        int var25 = this.this$0.laneTracker.laneCount().getFirst().intValue();
                        if (var25 <= 0) {
                            var25 = -1;
                        }

                        LaneBrain.Road var701 = this.this$0.laneTracker.getRoad();
                        boolean var34;
                        if (var701 != null && var701.getHighway()) {
                            var34 = true;
                        } else {
                            var34 = false;
                        }

                        var344 = new LaneSuggest.In(
                            var235,
                            var31,
                            var12,
                            var10,
                            var343,
                            var32,
                            var144,
                            var44,
                            var43,
                            var28,
                            var29,
                            var38,
                            var26,
                            var60,
                            var37,
                            var13,
                            var36,
                            var42,
                            var45,
                            var30,
                            var40,
                            var9,
                            var46,
                            var14,
                            var18,
                            var20,
                            var27,
                            var41,
                            var39,
                            var21,
                            var75,
                            var551,
                            ((String) var653),
                            var202,
                            var33,
                            var23,
                            var719,
                            var16,
                            var47,
                            var24,
                            var48,
                            var17,
                            var15,
                            var25,
                            var34
                        );
                    }

                    var9 = var58.getPoseYawZ();
                    boolean var150;
                    if (!Float.isInfinite(var9) && !Float.isNaN(var9)) {
                        var150 = true;
                    } else {
                        var150 = false;
                    }

                    if (var150 && System.nanoTime() - var58.getPoseAtNs() < 1000000000L) {
                        var9 = Odometry.INSTANCE.getGyroSign() * var58.getPoseYawZ();
                    } else {
                        var9 = var58.getYawRate();
                    }

                    TurnSignal.In var552;
                    if (var35 && var58.getStarted()) {
                        float var106 = var58.getVEgo();
                        var30 = var58.getLeftBlinker();
                        var31 = var58.getRightBlinker();
                        var29 = var58.tBool("use_turn_desires", false);
                        LaneSuggestView var654 = this.this$0.laneSuggest;
                        LaneSuggestView var553 = var654;
                        if (var654 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("laneSuggest");
                            var553 = null;
                        }

                        double var77 = var553.crossGapFor(var59, var235);
                        LaneSuggestView var655 = this.this$0.laneSuggest;
                        LaneSuggestView var554 = var655;
                        if (var655 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("laneSuggest");
                            var554 = null;
                        }

                        if (var554.getLogic().getState() != LaneSuggest.State.IDLE) {
                            var28 = true;
                        } else {
                            var28 = false;
                        }

                        var552 = new TurnSignal.In(
                            var235,
                            var59,
                            var106,
                            var9,
                            var30,
                            var31,
                            var10,
                            var29,
                            var77,
                            var28,
                            Intrinsics.areEqual(var58.getGear(), "park"),
                            var58.getSteeringAngleDeg(),
                            LaneSuggest.Companion.notTurnLane(var59, this.this$0.laneTracker, Nav.INSTANCE.nextLanes())
                        );
                    } else {
                        var552 = null;
                    }

                    LaneSuggestView var702 = this.this$0.laneSuggest;
                    LaneSuggestView var656 = var702;
                    if (var702 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("laneSuggest");
                        var656 = null;
                    }

                    var656.tick(var344, var552);
                    break label2348;
                }
            }

            var28 = false;
            LaneSuggestView var555 = this.this$0.laneSuggest;
            LaneSuggestView var345 = var555;
            if (var555 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("laneSuggest");
                var345 = null;
            }

            if (var345.getVisibility() == 8) {
                LaneSuggestView var556 = this.this$0.laneSuggest;
                LaneSuggestView var346 = var556;
                if (var556 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("laneSuggest");
                    var346 = null;
                }

                if (var346.getTurn().getHoldSide() == 0) {
                    break label2348;
                }
            }

            LaneSuggestView var557 = this.this$0.laneSuggest;
            LaneSuggestView var347 = var557;
            if (var557 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("laneSuggest");
                var347 = null;
            }

            var347.stop();
        }

        ReplayBar var558 = this.this$0.replayBar;
        ReplayBar var348 = var558;
        if (var558 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("replayBar");
            var348 = null;
        }

        DataClient var657 = this.this$0.data;
        var54 = var657;
        if (var657 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("data");
            var54 = null;
        }

        int var151;
        if (var54.getConnected() && Replay.INSTANCE.getActive()) {
            var151 = 0;
        } else {
            var151 = 8;
        }

        var348.setVisibility(var151);
        TextView var560 = this.this$0.whereBtn;
        TextView var349 = var560;
        if (var560 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("whereBtn");
            var349 = null;
        }

        DataClient var658 = this.this$0.data;
        var54 = var658;
        if (var658 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("data");
            var54 = null;
        }

        if (var54.getConnected()
            && var58.navState() == null
            && !this.this$0.getMapOpen()
            && this.this$0.hudEditor == null
            && this.this$0.maskEditor == null) {
            var151 = (byte)0;
        } else {
            var151 = (byte)8;
        }

        var349.setVisibility(var151);
        TextView var562 = this.this$0.mapBtn;
        TextView var350 = var562;
        if (var562 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mapBtn");
            var350 = null;
        }

        DataClient var659 = this.this$0.data;
        var54 = var659;
        if (var659 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("data");
            var54 = null;
        }

        if (!var54.getConnected()
            || this.this$0.getMapOpen()
            || this.this$0.hudEditor != null
            || this.this$0.maskEditor != null
            || !var58.getStarted() && this.this$0.homeShown) {
            var151 = (byte)8;
        } else {
            var151 = (byte)0;
        }

        var350.setVisibility(var151);
        if (this.this$0.tick % 40 == 0) {
            NudgeSync var660 = NudgeSync.INSTANCE;
            UiSettings var564 = this.this$0.settings;
            UiSettings var351 = var564;
            if (var564 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("settings");
                var351 = null;
            }

            var660.tick(var351.getFloat("nudge_lc_pct", 100.0F));
        }

        BlinkerTurn var565 = BlinkerTurn.INSTANCE;
        UiSettings var352 = this.this$0.settings;
        if (var352 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("settings");
            var352 = null;
        }

        BlinkerTurn.tick$default(var565, var58, var352, 0L, 4, null);
        this.this$0.ideasTick(var58);
        DriveTrack.tick$default(DriveTrack.INSTANCE, var58, 0L, 2, null);
        if (this.this$0.sharedPending != null) {
            var54 = this.this$0.data;
            DataClient var353 = var54;
            if (var54 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("data");
                var353 = null;
            }

            if (var353.getConnected() && this.this$0.tick % 20 == 0) {
                this.this$0.useShared();
            }
        }

        if (this.this$0.tick % 20 == 0) {
            Ntrip.INSTANCE.tick((Context)this.this$0);
        }

        if (this.this$0.tick % 4 == 0) {
            var55 = this.this$0;
            var54 = this.this$0.data;
            DataClient var354 = var54;
            if (var54 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("data");
                var354 = null;
            }

            if (!var354.getConnected()
                || Nav.INSTANCE.getActive()
                || var58.navState() != null
                || this.this$0.hudEditor != null
                || this.this$0.maskEditor != null
                || !var58.getStarted() && this.this$0.homeShown) {
                var28 = false;
            } else {
                var28 = true;
            }

            var55.syncFavBar(var28);
        }

        // 2.85.3: the map button is a screen-editor widget now (it used to move beside "Where to?")
        MainActivity var703 = this.this$0;
        Unit var359 = Unit.INSTANCE;
        var703 = this.this$0;
        MainActivity$uiTick$1 var360 = this;
        if (var58.getParams().get("NavLanePositioningAllowed") != null) {
            var28 = var58.pBool("NavLanePositioningAllowed", true);
        } else {
            var28 = true;
        }

        NudgeLogic.Result var361;
        if (var58.getStarted() && var58.getActive() && var28 && var58.s("nudge_hint")) {
            Double var362 = StringsKt.toDoubleOrNull(Telemetry.pStr$default(var58, "LaneDetectionWidth", null, 2, null));
            double var78;
            if (var362 != null) {
                var78 = var362;
            } else {
                var78 = 0.0;
            }

            NudgeLogic var363 = NudgeLogic.INSTANCE;
            JSONObject var571 = var58.navState();
            double var83 = var58.getVEgo();
            var31 = var58.getLeftBlinker();
            var29 = var58.getRightBlinker();
            boolean var230 = var58.getLeftBlindspot();
            var28 = var58.getRightBlindspot();
            double var7 = var58.getLaneWidthLeft();
            double var86 = var58.getLaneWidthRight();
            if (!var58.isMetric()) {
                var78 = 0.3048 * var78;
            }

            var361 = var363.evaluate(var571, var83, var31, var29, var230, var28, var7, var86, var78);
        } else {
            var361 = new NudgeLogic.Result(NudgeLogic.State.NONE, false, 0.0, 0.0, "");
        }

        NudgeHintView var663 = var703.nudgeHint;
        NudgeHintView var572 = var663;
        if (var663 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("nudgeHint");
            var572 = null;
        }

        var572.setResult(var361);
        NudgeHintView var664 = var703.nudgeHint;
        NudgeHintView var573 = var664;
        if (var664 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("nudgeHint");
            var573 = null;
        }

        var573.setMetric(var58.isMetric());
        NudgeHintView var665 = var703.nudgeHint;
        NudgeHintView var574 = var665;
        if (var665 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("nudgeHint");
            var574 = null;
        }

        int var156;
        if (var361.getState() == NudgeLogic.State.NONE) {
            var156 = 8;
        } else {
            var156 = 0;
        }

        var574.setVisibility(var156);
        Unit var364 = Unit.INSTANCE;
        WeatherView var575 = this.this$0.weather;
        WeatherView var365 = var575;
        if (var575 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("weather");
            var365 = null;
        }

        if (var58.getStarted() && var58.s("weather") && var58.getWeatherId() > 0) {
            var156 = (byte)0;
        } else {
            var156 = (byte)8;
        }

        var365.setVisibility(var156);
        TextView var576 = this.this$0.compass;
        TextView var366 = var576;
        if (var576 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("compass");
            var366 = null;
        }

        CharSequence var577;
        if (var58.getStarted() && var58.s("compass") && !Float.isNaN(var58.getBearingDeg())) {
            var156 = (int)(((var58.getBearingDeg() + 22.5F) % 360.0F + 360.0F) % 360.0F / 45.0F);
            String var578 = new String[]{"N", "NE", "E", "SE", "S", "SW", "W", "NW"}[var156 % 8];
            var577 = "- " + var578 + " -";
        } else {
            var577 = "";
        }

        var366.setText(var577);
        TextView var579 = this.this$0.devMetrics;
        TextView var367 = var579;
        if (var579 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("devMetrics");
            var367 = null;
        }

        CharSequence var580;
        if (var58.getStarted() && var58.s("dev_metrics")) {
            int var200 = MathKt.roundToInt(var58.getCpuUsage());
            var156 = MathKt.roundToInt(var58.getCpuTempC());
            var20 = var58.getMemUsage();
            int var204 = MathKt.roundToInt(var58.getFreeSpacePct());
            var580 = "CPU " + var200 + "%  |  TEMP " + var156 + "°C  |  RAM " + var20 + "%  |  DISK " + var204 + "% free";
        } else {
            var580 = "";
        }

        var367.setText(var580);
        long var236 = System.nanoTime();
        if (var58.getStarted()) {
            if (this.this$0.onroadSinceNs == 0L) {
                this.this$0.onroadSinceNs = var236;
            }
        } else {
            this.this$0.onroadSinceNs = 0L;
        }

        if (var58.getStarted() && var58.getStandstill()) {
            if (this.this$0.standstillSinceNs == 0L) {
                this.this$0.standstillSinceNs = var236;
            }
        } else {
            this.this$0.standstillSinceNs = 0L;
        }

        if (this.this$0.standstillSinceNs != 0L) {
            var9 = (float)(var236 - this.this$0.standstillSinceNs) / 1.0E9F;
        } else {
            var9 = 0.0F;
        }

        if (this.this$0.onroadSinceNs != 0L) {
            var10 = (float)(var236 - this.this$0.onroadSinceNs) / 1.0E9F;
        } else {
            var10 = 0.0F;
        }

        if (var58.s("stopped_timer") && var9 >= 60.0F && var10 >= 60.0F) {
            var10 = 60;
            var156 = (int)(var9 / var10);
            var20 = (int)(var9 % var10);
            TextView var582 = this.this$0.speedTv;
            TextView var369 = var582;
            if (var582 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("speedTv");
                var369 = null;
            }

            String var583 = String.format("%d:%02d", Arrays.copyOf(new Object[]{var156, var20}, 2));
            Intrinsics.checkNotNullExpressionValue(var583, "format(...)");
            var369.setText(var583);
            TextView var584 = this.this$0.speedUnit;
            TextView var370 = var584;
            if (var584 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("speedUnit");
                var370 = null;
            }

            var370.setText("stopped");
            if (var9 < 150.0F) {
                var156 = ColorUtils.blendARGB(Color.rgb(22, 127, 64), Color.rgb(218, 111, 37), (var9 - 60.0F) / 90.0F);
            } else if (var9 < 300.0F) {
                var156 = ColorUtils.blendARGB(Color.rgb(218, 111, 37), Color.rgb(201, 34, 49), (var9 - 150.0F) / 150.0F);
            } else {
                var156 = Color.rgb(201, 34, 49);
            }

            TextView var585 = this.this$0.speedTv;
            TextView var371 = var585;
            if (var585 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("speedTv");
                var371 = null;
            }

            var371.setTextColor(var156);
        } else {
            TextView var581 = this.this$0.speedTv;
            TextView var368 = var581;
            if (var581 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("speedTv");
                var368 = null;
            }

            if (this.this$0.night) {
                var156 = -1;
            } else {
                var156 = Color.rgb(29, 29, 31);
            }

            var368.setTextColor(var156);
        }

        label1694: {
            var28 = var58.s("alerts");
            if (var58.getStarted()) {
                boolean var163;
                if (var58.getAlertText1().length() > 0) {
                    var163 = true;
                } else {
                    var163 = false;
                }

                if (var163 && !Intrinsics.areEqual(var58.getAlertSize(), "none") && !(var28 ^ true)) {
                    var164 = true;
                    break label1694;
                }
            }

            var164 = false;
        }

        MainActivity var372 = this.this$0;
        var10 = this.this$0.alertAlpha;
        var9 = var11;
        if (var164) {
            var9 = 1.0F;
        }

        var372.alertAlpha = var10 + (var9 - this.this$0.alertAlpha) * 0.35F;
        if (this.this$0.alertAlpha > 0.02F) {
            LinearLayout var586 = this.this$0.alertBox;
            LinearLayout var373 = var586;
            if (var586 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("alertBox");
                var373 = null;
            }

            var373.setVisibility(0);
            LinearLayout var587 = this.this$0.alertBox;
            LinearLayout var374 = var587;
            if (var587 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("alertBox");
                var374 = null;
            }

            var374.setAlpha(this.this$0.alertAlpha);
            if (var164) {
                TextView var588 = this.this$0.alert1;
                TextView var375 = var588;
                if (var588 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("alert1");
                    var375 = null;
                }

                var375.setText(var58.getAlertText1());
                TextView var589 = this.this$0.alert2;
                TextView var376 = var589;
                if (var589 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("alert2");
                    var376 = null;
                }

                var376.setText(var58.getAlertText2());
                TextView var590 = this.this$0.alert2;
                TextView var377 = var590;
                if (var590 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("alert2");
                    var377 = null;
                }

                int var165;
                if (var58.getAlertText2().length() == 0) {
                    var165 = 1;
                } else {
                    var165 = 0;
                }

                if ((var165 != 0)) {
                    var165 = 8;
                } else {
                    var165 = 0;
                }

                var377.setVisibility(var165);
            }

            var28 = Intrinsics.areEqual(var58.getAlertSize(), "full");
            TextView var591 = this.this$0.alert1;
            TextView var378 = var591;
            if (var591 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("alert1");
                var378 = null;
            }

            String var592 = var58.getAlertSize();
            if (Intrinsics.areEqual(var592, "full")) {
                var9 = 52.0F;
            } else if (Intrinsics.areEqual(var592, "mid")) {
                var9 = 36.0F;
            } else {
                var9 = 26.0F;
            }

            var378.setTextSize(var9);
            TextView var593 = this.this$0.alert2;
            TextView var379 = var593;
            if (var593 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("alert2");
                var379 = null;
            }

            if (var28) {
                var9 = 24.0F;
            } else {
                var9 = 20.0F;
            }

            var379.setTextSize(var9);
            LinearLayout var594 = this.this$0.alertBox;
            LinearLayout var380 = var594;
            if (var594 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("alertBox");
                var380 = null;
            }

            android.view.ViewGroup.LayoutParams var381 = var380.getLayoutParams();
            Intrinsics.checkNotNull(var381, "null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
            android.widget.FrameLayout.LayoutParams var666 = (android.widget.FrameLayout.LayoutParams)var381;
            int var167;
            if (var28) {
                var167 = -1;
            } else {
                var167 = -2;
            }

            var666.height = var167;
            LinearLayout var595 = this.this$0.alertBox;
            LinearLayout var382 = var595;
            if (var595 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("alertBox");
                var382 = null;
            }

            label1670: {
                var382.setLayoutParams((android.view.ViewGroup.LayoutParams)var666);
                String var383 = var58.getAlertStatus();
                switch (var383.hashCode()) {
                    case -2132360282:
                        if (var383.equals("starpilot")) {
                            var167 = Color.rgb(23, 134, 68);
                            break label1670;
                        }
                        break;
                    case -1874525969:
                        if (var383.equals("userprompt")) {
                            var167 = Color.rgb(255, 115, 0);
                            break label1670;
                        }
                        break;
                    case 1952151455:
                        if (var383.equals("critical")) {
                            var167 = Color.rgb(255, 0, 21);
                            break label1670;
                        }
                }

                var167 = -16777216;
            }

            int var193;
            if (var58.s("alert_tint") && !Intrinsics.areEqual(var58.getAlertStatus(), "normal")) {
                var193 = 1;
            } else {
                var193 = 0;
            }

            if ((var193 != 0)) {
                View var596 = this.this$0.alertTint;
                View var384 = var596;
                if (var596 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("alertTint");
                    var384 = null;
                }

                var384.setVisibility(0);
                View var597 = this.this$0.alertTint;
                View var385 = var597;
                if (var597 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("alertTint");
                    var385 = null;
                }

                var385.setAlpha(this.this$0.alertAlpha);
                this.this$0.tintDrawable.setOrientation(Orientation.TOP_BOTTOM);
                GradientDrawable var598 = this.this$0.tintDrawable;
                int[] var386;
                if (var28) {
                    var386 = new int[]{OverlayView.Companion.withAlpha(var167, 230), OverlayView.Companion.withAlpha(var167, 230)};
                } else {
                    var386 = new int[]{
                        OverlayView.Companion.withAlpha(var167, 0), OverlayView.Companion.withAlpha(var167, 60), OverlayView.Companion.withAlpha(var167, 230)
                    };
                }

                var598.setColors(var386);
                LinearLayout var599 = this.this$0.alertBox;
                LinearLayout var387 = var599;
                if (var599 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("alertBox");
                    var387 = null;
                }

                if (var28) {
                    var167 = 0;
                } else {
                    var167 = OverlayView.Companion.withAlpha(var167, 120);
                }

                var387.setBackgroundColor(var167);
            } else {
                View var600 = this.this$0.alertTint;
                View var388 = var600;
                if (var600 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("alertTint");
                    var388 = null;
                }

                var388.setVisibility(8);
                LinearLayout var601 = this.this$0.alertBox;
                LinearLayout var389 = var601;
                if (var601 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("alertBox");
                    var389 = null;
                }

                OverlayView.Companion_ var602 = OverlayView.Companion;
                if (var28) {
                    var193 = 230;
                } else {
                    var193 = 200;
                }

                var389.setBackgroundColor(var602.withAlpha(var167, var193));
            }
        } else {
            LinearLayout var603 = this.this$0.alertBox;
            LinearLayout var390 = var603;
            if (var603 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("alertBox");
                var390 = null;
            }

            var390.setVisibility(8);
            View var604 = this.this$0.alertTint;
            View var391 = var604;
            if (var604 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("alertTint");
                var391 = null;
            }

            var391.setVisibility(8);
        }

        ModelSnapshot var392 = var58.getModel();
        if (var392 != null) {
            var236 = var392.getFrameId();
        } else {
            var236 = -1L;
        }

        if (var236 != this.this$0.lastModelFrame || this.this$0.tick % 1 == 0) {
            this.this$0.lastModelFrame = var236;
            OverlayView var605 = this.this$0.overlay;
            OverlayView var393 = var605;
            if (var605 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("overlay");
                var393 = null;
            }

            var393.invalidate();
        }

        WheelButton var606 = this.this$0.wheel;
        WheelButton var394 = var606;
        if (var606 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("wheel");
            var394 = null;
        }

        var394.invalidate();
        DmView var607 = this.this$0.dm;
        DmView var395 = var607;
        if (var607 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("dm");
            var395 = null;
        }

        var395.invalidate();
        MiciDistanceView var608 = this.this$0.personality;
        MiciDistanceView var396 = var608;
        if (var608 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("personality");
            var396 = null;
        }

        var396.invalidate();
        SlcView var609 = this.this$0.slc;
        SlcView var397 = var609;
        if (var609 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("slc");
            var397 = null;
        }

        var397.invalidate();
        SlcActionsView var610 = this.this$0.slcActions;
        SlcActionsView var398 = var610;
        if (var610 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("slcActions");
            var398 = null;
        }

        var398.invalidate();
        DevSidebarView var611 = this.this$0.devSidebar;
        DevSidebarView var399 = var611;
        if (var611 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("devSidebar");
            var399 = null;
        }

        if (var399.getVisibility() == 0 && this.this$0.tick % 4 == 0) {
            DevSidebarView var612 = this.this$0.devSidebar;
            DevSidebarView var400 = var612;
            if (var612 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("devSidebar");
                var400 = null;
            }

            var400.invalidate();
        }

        AetherGaugeView var613 = this.this$0.gauge;
        AetherGaugeView var401 = var613;
        if (var613 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("gauge");
            var401 = null;
        }

        var401.invalidate();
        NavCardView var614 = this.this$0.navCard;
        NavCardView var402 = var614;
        if (var614 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("navCard");
            var402 = null;
        }

        var402.invalidate();
        SlowReasonView var615 = this.this$0.slowCard;
        SlowReasonView var403 = var615;
        if (var615 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("slowCard");
            var403 = null;
        }

        var403.invalidate();
        LaneCardView var616 = this.this$0.laneCard;
        LaneCardView var404 = var616;
        if (var616 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("laneCard");
            var404 = null;
        }

        var404.invalidate();
        BlinkerCardView var617 = this.this$0.blinkCard;
        BlinkerCardView var405 = var617;
        if (var617 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("blinkCard");
            var405 = null;
        }

        var405.invalidate();
        GpsCardView var618 = this.this$0.gpsCard;
        GpsCardView var406 = var618;
        if (var618 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("gpsCard");
            var406 = null;
        }

        if (var406.getVisibility() == 0) {
            GpsCardView var619 = this.this$0.gpsCard;
            GpsCardView var407 = var619;
            if (var619 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("gpsCard");
                var407 = null;
            }

            var407.invalidate();
        }

        NudgeHintView var620 = this.this$0.nudgeHint;
        NudgeHintView var408 = var620;
        if (var620 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("nudgeHint");
            var408 = null;
        }

        var408.invalidate();
        WeatherView var621 = this.this$0.weather;
        WeatherView var409 = var621;
        if (var621 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("weather");
            var409 = null;
        }

        var409.invalidate();
        SlcSourcesView var622 = this.this$0.slcSources;
        SlcSourcesView var410 = var622;
        if (var622 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("slcSources");
            var410 = null;
        }

        var410.invalidate();
        LinearLayout var623 = this.this$0.sidebar;
        LinearLayout var411 = var623;
        if (var623 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sidebar");
            var411 = null;
        }

        if (var411.getVisibility() == 0 && this.this$0.tick % 5 == 0) {
            TextView var624 = this.this$0.findViewById(R.id.sbName);
            var243 = Telemetry.pStr$default(var58, "GalaxyDeviceName", null, 2, null);
            int var170;
            if (((String) var243).length() == 0) {
                var170 = 1;
            } else {
                var170 = 0;
            }

            if ((var170 != 0)) {
                var243 = var58.pStr("DongleId", "comma");
            }

            label1620: {
                var624.setText((CharSequence)var243);
                String var625 = StringsKt.repeat("●", RangesKt.coerceIn(var58.getNetworkStrength(), 0, 4));
                String var413 = StringsKt.repeat("○", 4 - RangesKt.coerceIn(var58.getNetworkStrength(), 0, 4));
                var667 = var625 + var413;
                var626 = this.this$0.findViewById(R.id.sbNet);
                String var414 = var58.getNetworkType();
                switch (var414.hashCode()) {
                    case -1419358249:
                        if (var414.equals("ethernet")) {
                            var415 = "ETH";
                            break label1620;
                        }
                        break;
                    case -1364082857:
                        if (var414.equals("cell2g")) {
                            var415 = "2G";
                            break label1620;
                        }
                        break;
                    case -1364082826:
                        if (var414.equals("cell3g")) {
                            var415 = "3G";
                            break label1620;
                        }
                        break;
                    case -1364082795:
                        if (var414.equals("cell4g")) {
                            var415 = "LTE";
                            break label1620;
                        }
                        break;
                    case -1364082764:
                        if (var414.equals("cell5g")) {
                            var415 = "5G";
                            break label1620;
                        }
                        break;
                    case 3649301:
                        if (var414.equals("wifi")) {
                            var415 = "Wi-Fi";
                            break label1620;
                        }
                }

                var415 = "--";
            }

            var626.setText(var667 + "  " + var415);
            TextView var416 = this.this$0.findViewById(R.id.sbTempV);
            var170 = MathKt.roundToInt(var58.getMaxTempC());
            var416.setText(var170 + "°C");
            String var627 = var58.getThermalStatus();
            if (Intrinsics.areEqual(var627, "green")) {
                var170 = -1;
            } else {
                short var173;
                short var195;
                byte var201;
                if (Intrinsics.areEqual(var627, "yellow")) {
                    var173 = 202;
                    var201 = 37;
                    var195 = 218;
                } else {
                    var173 = 34;
                    var201 = 49;
                    var195 = 201;
                }

                var170 = Color.rgb(var195, var173, var201);
            }

            var416.setTextColor(var170);
            TextView var628 = this.this$0.findViewById(R.id.sbVehV);
            String var417;
            if (var58.getStarted()) {
                var417 = "ONLINE";
            } else {
                var417 = "OFFROAD";
            }

            var628.setText(var417);
            if (var58.getStarted()) {
                var170 = -1;
            } else {
                var170 = Color.rgb(166, 166, 166);
            }

            var628.setTextColor(var170);
            int var175;
            if (var58.getLastAthenaPing() > 0L && System.currentTimeMillis() / 1000 - var58.getLastAthenaPing() < 80L) {
                var175 = 1;
            } else {
                var175 = 0;
            }

            TextView var629 = this.this$0.findViewById(R.id.sbConnV);
            String var418;
            if ((var175 != 0)) {
                var418 = "ONLINE";
            } else {
                var418 = "OFFLINE";
            }

            var629.setText(var418);
            if ((var175 != 0)) {
                var175 = -1;
            } else {
                var175 = Color.rgb(201, 34, 49);
            }

            var629.setTextColor(var175);
            var29 = DriveState.INSTANCE.forcedOnroad();
            var28 = DriveState.INSTANCE.forcedOffroad();
            Button var419 = this.this$0.findViewById(R.id.dsDefault);
            if (!var29 && !var28) {
                var9 = 1.0F;
            } else {
                var9 = 0.5F;
            }

            var419.setAlpha(var9);
            Button var420 = this.this$0.findViewById(R.id.dsOnroad);
            if (var29) {
                var9 = 1.0F;
            } else {
                var9 = 0.5F;
            }

            var420.setAlpha(var9);
            Button var421 = this.this$0.findViewById(R.id.dsOffroad);
            if (var28) {
                var9 = 1.0F;
            } else {
                var9 = 0.5F;
            }

            var421.setAlpha(var9);
        }

        if (this.this$0.tick % 10 == 0) {
            var54 = this.this$0.data;
            DataClient var422 = var54;
            if (var54 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("data");
                var422 = null;
            }

            var422.ping();
            if (this.this$0.statsVisible) {
                Collection var423 = var58.getByteCount().values();
                Intrinsics.checkNotNullExpressionValue(var423, "<get-values>(...)");
                var236 = CollectionsKt.sumOfLong(var423) / 1024;
                TextView var424 = this.this$0.statsTv;
                TextView var631 = var424;
                if (var424 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("statsTv");
                    var631 = null;
                }

                MainActivity var715 = this.this$0;
                StringBuilder var714 = new StringBuilder();
                String var668 = var715.linkIf;
                String var425;
                if (var58.getRttMs() >= 0.0) {
                    var425 = String.format("%.1f", Arrays.copyOf(new Object[]{var58.getRttMs()}, 1));
                    Intrinsics.checkNotNullExpressionValue(var425, "format(...)");
                } else {
                    var425 = "–";
                }

                var714.append("link   " + var668 + "  rtt " + var425 + " ms  " + var236 + " kB\n");
                var20 = MathKt.roundToInt(var58.getCpuUsage());
                var156 = MathKt.roundToInt(var58.getCpuTempC());
                String var669 = var58.getThermalStatus();
                String var705 = var58.getDeviceType();
                String var426 = var58.getSensor();
                var714.append("comma  cpu " + var20 + "%  " + var156 + "°C  " + var669 + "  " + var705 + "/" + var426 + "\n");
                VideoClient var670 = var715.video;
                VideoClient var427 = var670;
                if (var670 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("video");
                    var427 = null;
                }

                String var706 = String.format("%.1f", Arrays.copyOf(new Object[]{var427.getFps()}, 1));
                Intrinsics.checkNotNullExpressionValue(var706, "format(...)");
                VideoClient var671 = var715.video;
                VideoClient var428 = var671;
                if (var671 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("video");
                    var428 = null;
                }

                var156 = var428.getLastW();
                VideoClient var672 = var715.video;
                VideoClient var429 = var672;
                if (var672 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("video");
                    var429 = null;
                }

                var20 = var429.getLastH();
                VideoClient var673 = var715.video;
                VideoClient var430 = var673;
                if (var673 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("video");
                    var430 = null;
                }

                String var716 = String.format("%.1f", Arrays.copyOf(new Object[]{var430.getDecodeMs()}, 1));
                Intrinsics.checkNotNullExpressionValue(var716, "format(...)");
                VideoClient var674 = var715.video;
                VideoClient var431 = var674;
                if (var674 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("video");
                    var431 = null;
                }

                var236 = var431.getDropped();
                var714.append("video  " + var706 + " fps  " + var156 + "x" + var20 + "  dec " + var716 + " ms  drop " + var236 + "\n");
                Detector var717 = var715.detectorRef;
                if (var717 != null && var717.isRunning()) {
                    String var720 = CollectionsKt.joinToString(CollectionsKt.sortedWith(
                            GroupingKt.eachCount(new Grouping<Detector.Det, String>() {
                                final Iterable $this_groupingBy = (Iterable) var717.getDets();

                                @Override
                                public String keyOf(Detector.Det var1) {
                                    return ((Detector.Det)var1).getName();
                                }

                                @Override
                                public Iterator<Detector.Det> sourceIterator() {
                                    return this.$this_groupingBy.iterator();
                                }
                            }).entrySet(),
                            new Comparator() {
                                @Override
                                public final int compare(Object var1, Object var2) {
                                    return ComparisonsKt.compareValues(
                                        (Comparable)((java.util.Map.Entry)var2).getValue(), (Comparable)((java.util.Map.Entry)var1).getValue()
                                    );
                                }
                            }
                        ), " ", "", "", -1, "...", new MainActivity$uiTick$1$$ExternalSyntheticLambda1());
                    String var64 = var717.getStatus();
                    String var68 = var717.getAccel();
                    String var66 = String.format("%.0f", Arrays.copyOf(new Object[]{var717.getInferMs()}, 1));
                    Intrinsics.checkNotNullExpressionValue(var66, "format(...)");
                    String var67 = String.format("%.0f", Arrays.copyOf(new Object[]{var717.getPrepMs()}, 1));
                    Intrinsics.checkNotNullExpressionValue(var67, "format(...)");
                    String var65 = String.format("%.1f", Arrays.copyOf(new Object[]{var717.getFps()}, 1));
                    Intrinsics.checkNotNullExpressionValue(var65, "format(...)");
                    String var725 = String.format("%.0f", Arrays.copyOf(new Object[]{var717.getRoadFps()}, 1));
                    Intrinsics.checkNotNullExpressionValue(var725, "format(...)");
                    String var722 = String.format("%.0f", Arrays.copyOf(new Object[]{var717.getWideFps()}, 1));
                    Intrinsics.checkNotNullExpressionValue(var722, "format(...)");
                    VideoClient var675 = var715.detRoad;
                    VideoClient var432 = var675;
                    if (var675 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("detRoad");
                        var432 = null;
                    }

                    String var433;
                    if (var432.isRunning()) {
                        var433 = "road ";
                    } else {
                        var433 = "";
                    }

                    VideoClient var707 = var715.detWide;
                    VideoClient var676 = var707;
                    if (var707 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("detWide");
                        var676 = null;
                    }

                    String var677;
                    if (var676.isRunning()) {
                        var677 = "wide";
                    } else {
                        var677 = "";
                    }

                    var714.append(
                        "detect "
                            + var64
                            + "  "
                            + var68
                            + "  model "
                            + var66
                            + " ms  prep "
                            + var67
                            + " ms  "
                            + var65
                            + " /s (road "
                            + var725
                            + " wide "
                            + var722
                            + ")  cams "
                            + var433
                            + var677
                            + "\n"
                    );
                    if (var717.getLaneMarks() != null) {
                        String var434 = var715.laneMarks.getDebug();
                        var714.append("lines  " + var434 + "\n");
                    }

                    LayoutSeg var678 = var715.layoutSeg;
                    LayoutSeg var435 = var678;
                    if (var678 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("layoutSeg");
                        var435 = null;
                    }

                    String var443;
                    if (var435.getEnabled()) {
                        LayoutSeg var679 = var715.layoutSeg;
                        LayoutSeg var436 = var679;
                        if (var679 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("layoutSeg");
                            var436 = null;
                        }

                        String var708 = var436.getStatus();
                        LayoutSeg var680 = var715.layoutSeg;
                        LayoutSeg var437 = var680;
                        if (var680 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("layoutSeg");
                            var437 = null;
                        }

                        var722 = var437.getAccel();
                        LayoutSeg var681 = var715.layoutSeg;
                        LayoutSeg var438 = var681;
                        if (var681 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("layoutSeg");
                            var438 = null;
                        }

                        var725 = String.format("%.0f", Arrays.copyOf(new Object[]{var438.getMs()}, 1));
                        Intrinsics.checkNotNullExpressionValue(var725, "format(...)");
                        LayoutSeg var682 = var715.layoutSeg;
                        LayoutSeg var439 = var682;
                        if (var682 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("layoutSeg");
                            var439 = null;
                        }

                        boolean var179;
                        if (var439.getGpuError().length() > 0) {
                            var179 = true;
                        } else {
                            var179 = false;
                        }

                        String var442;
                        if (var179) {
                            LayoutSeg var683 = var715.layoutSeg;
                            LayoutSeg var440 = var683;
                            if (var683 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("layoutSeg");
                                var440 = null;
                            }

                            String var441 = var440.getGpuError();
                            var442 = "  gpu error: " + var441;
                        } else {
                            var442 = "";
                        }

                        var443 = var708 + " " + var722 + " " + var725 + " ms" + var442;
                    } else {
                        LayoutSeg var684 = var715.layoutSeg;
                        LayoutSeg var444 = var684;
                        if (var684 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("layoutSeg");
                            var444 = null;
                        }

                        if (!var444.getHasModel()) {
                            var443 = "no model";
                        } else {
                            var443 = "off";
                        }
                    }

                    var714.append("layout " + var443 + "\n");
                    boolean var180;
                    if (var717.getOrientError().length() > 0) {
                        var180 = true;
                    } else {
                        var180 = false;
                    }

                    String var446;
                    if (var180) {
                        String var445 = StringsKt.take(var717.getOrientError(), 60);
                        var446 = "ERROR " + var445;
                    } else {
                        var446 = String.format("%.1f ms/car", Arrays.copyOf(new Object[]{var717.getOrientMs()}, 1));
                        Intrinsics.checkNotNullExpressionValue(var446, "format(...)");
                    }

                    var714.append("orient " + var446 + "\n");
                    double var79 = var717.getLoadMs();
                    double var84 = 1000;
                    String var709 = String.format("%.0f", Arrays.copyOf(new Object[]{var79 / var84}, 1));
                    Intrinsics.checkNotNullExpressionValue(var709, "format(...)");
                    var722 = String.format("%.1f", Arrays.copyOf(new Object[]{var717.getFirstRunMs() / var84}, 1));
                    Intrinsics.checkNotNullExpressionValue(var722, "format(...)");
                    var243 = var720;
                    if (((String) var243).length() == 0) {
                        var180 = true;
                    } else {
                        var180 = false;
                    }

                    if (var180) {
                        var243 = "nothing";
                    }

                    if (var717.getGpuError().length() > 0) {
                        var180 = true;
                    } else {
                        var180 = false;
                    }

                    String var686;
                    if (var180) {
                        String var685 = StringsKt.take(var717.getGpuError(), 90);
                        var686 = "   gpu: " + var685;
                    } else {
                        var686 = "";
                    }

                    var714.append("  load " + var709 + " s  first run " + var722 + " s  found: " + var243 + var686 + "\n");
                }

                int var183;
                if (var715.lastCrash.length() > 0) {
                    var183 = 1;
                } else {
                    var183 = 0;
                }

                if ((var183 != 0)) {
                    String var448 = StringsKt.take(StringsKt.replace(var715.lastCrash, "\n", " | ", false), 300);
                    var714.append("LAST CRASH  " + var448 + "\n");
                }

                SceneView var449 = var715.scene;
                if (var449 != null) {
                    var20 = var449.getShownCars();
                    if (var449.getFailure().length() > 0) {
                        var183 = (((boolean)true) ? 1 : 0);
                    } else {
                        var183 = (((boolean)false) ? 1 : 0);
                    }

                    String var451;
                    if ((var183 != 0)) {
                        String var450 = StringsKt.take(var449.getFailure(), 90);
                        var451 = "ERROR " + var450;
                    } else {
                        var451 = "ok";
                    }

                    var714.append("scene  cars " + var20 + "  " + var451 + "\n");
                }

                UiSettings var687 = var715.settings;
                UiSettings var452 = var687;
                if (var687 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("settings");
                    var452 = null;
                }

                if (var452.get("map_roads")) {
                    label1576: {
                        var710 = OsmRoads.INSTANCE.getTileStatus();
                        SceneView var453 = var715.scene;
                        if (var453 != null) {
                            String var688 = var453.getMapStatus();
                            var454 = var688;
                            if (var688 != null) {
                                break label1576;
                            }
                        }

                        var454 = "";
                    }

                    var714.append("map    " + var710 + "  " + var454 + "\n");
                }

                String var455;
                if (var58.getCalValid()) {
                    var455 = "ok";
                } else {
                    var455 = "NO";
                }

                var716 = String.format("%.3f %.3f %.3f", Arrays.copyOf(new Object[]{var58.getCalRpy()[0], var58.getCalRpy()[1], var58.getCalRpy()[2]}, 3));
                Intrinsics.checkNotNullExpressionValue(var716, "format(...)");
                String var721 = String.format("%.2f", Arrays.copyOf(new Object[]{var58.getCalHeight()}, 1));
                Intrinsics.checkNotNullExpressionValue(var721, "format(...)");
                OverlayView var711 = var715.overlay;
                OverlayView var689 = var711;
                if (var711 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("overlay");
                    var689 = null;
                }

                String var690 = String.format("%.2f", Arrays.copyOf(new Object[]{var689.getProj().getZoom()}, 1));
                Intrinsics.checkNotNullExpressionValue(var690, "format(...)");
                var714.append("calib  " + var455 + "  rpy " + var716 + "  h " + var721 + "  zoom " + var690 + "\n");
                Long var456 = var58.getMsgCount().get("modelV2");
                if (var456 != null) {
                    var236 = var456;
                } else {
                    var236 = 0L;
                }

                Long var457 = var58.getMsgCount().get("carState");
                long var241;
                if (var457 != null) {
                    var241 = var457;
                } else {
                    var241 = 0L;
                }

                var20 = var58.getToggles().size();
                var183 = var58.getParams().size();
                var714.append("model  " + var236 + "  cs " + var241 + "  toggles " + var20 + "  params " + var183 + "\n");
                String var458;
                if (var58.getLeadOne().getStatus()) {
                    var458 = String.format("%.1f m  %.1f m/s", Arrays.copyOf(new Object[]{var58.getLeadOne().getDRel(), var58.getLeadOne().getVRel()}, 2));
                    Intrinsics.checkNotNullExpressionValue(var458, "format(...)");
                } else {
                    var458 = "none";
                }

                String var691 = String.format("%.1f", Arrays.copyOf(new Object[]{var58.getSteeringAngleDeg()}, 1));
                Intrinsics.checkNotNullExpressionValue(var691, "format(...)");
                String var712 = String.format("%.2f", Arrays.copyOf(new Object[]{var58.getOutTorque()}, 1));
                Intrinsics.checkNotNullExpressionValue(var712, "format(...)");
                var714.append("lead   " + var458 + "  steer " + var691 + "°  torque " + var712);
                Unit var459 = Unit.INSTANCE;
                String var460 = var714.toString();
                Intrinsics.checkNotNullExpressionValue(var460, "toString(...)");
                var631.setText(var460);
            }
        }

        this.this$0.applyHud();
        GpsBridge.INSTANCE.fallback(Telemetry.pStr$default(this.this$0.telemetry, "LastGPSPosition", null, 2, null), this.this$0.telemetry.getVEgo());
        this.this$0.ui.postDelayed(this, 50L);
    }
}
