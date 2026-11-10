package com.starpilot.starview;

import android.graphics.Color;
import java.util.Arrays;
import java.util.Calendar;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import org.capnproto.SlcPeek;

/* compiled from: BigUiViews.kt */
public final class DrivingMode {
    public static final DrivingMode INSTANCE = new DrivingMode();

    private DrivingMode() {
    }

    public final String describe(Telemetry t) {
        Intrinsics.checkNotNullParameter(t, "t");
        boolean longOverridden = t.getGasPressed() && t.getEnabled() && Intrinsics.areEqual(t.getOpState(), "overriding");
        boolean latActive = t.getLatActive();
        boolean longActive = t.getLongActive();
        return longOverridden ? "ACC - Override" : (latActive && t.mBool("SwitchbackModeEnabled", false)) ? "Switchback Mode" : longActive ? t.getTrafficMode() ? "Traffic Mode" : t.getExperimentalMode() ? "ACC - Experimental" : "ACC - Chill" : latActive ? "Always On Lateral" : t.getCruiseEnabled() ? "Stock ACC" : "Disengaged";
    }

    public static /* synthetic */ String clock$default(DrivingMode drivingMode, Calendar calendar, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            calendar = Calendar.getInstance();
        }
        return drivingMode.clock(calendar, z);
    }

    public final String clock(Calendar now, boolean h24) {
        Intrinsics.checkNotNullParameter(now, "now");
        if (h24) {
            String format = String.format("%02d:%02d", Arrays.copyOf(new Object[]{Integer.valueOf(now.get(11)), Integer.valueOf(now.get(12))}, 2));
            Intrinsics.checkNotNullExpressionValue(format, "format(...)");
            return format;
        }
        String format2 = String.format("%d:%02d %s", Arrays.copyOf(new Object[]{Integer.valueOf(((now.get(10) + 11) % 12) + 1), Integer.valueOf(now.get(12)), now.get(9) == 0 ? "AM" : "PM"}, 3));
        Intrinsics.checkNotNullExpressionValue(format2, "format(...)");
        return format2;
    }

    public static /* synthetic */ Pair curveLabel$default(DrivingMode drivingMode, Telemetry telemetry, long j, int i, Object obj) {
        if ((i & 2) != 0) {
            j = System.nanoTime();
        }
        return drivingMode.curveLabel(telemetry, j);
    }

    public final Pair<String, Integer> curveLabel(Telemetry t, long nowNs) {
        SlcPeek.Curve c;
        Intrinsics.checkNotNullParameter(t, "t");
        SlcPeek.Slc slcLive = t.slcLive(nowNs);
        if (slcLive == null || (c = slcLive.curve) == null || !c.configured || !Intrinsics.areEqual(t.getAlertSize(), "none")) {
            return null;
        }
        boolean metric = t.isMetric();
        if (c.applied && c.controlling && t.getLongActive() && c.hasCeiling) {
            int v = MathKt.roundToInt(c.ceilingMps * (metric ? 3.6f : 2.2369363f));
            return TuplesKt.to("CURVE " + v + " " + (metric ? "KM/H" : "MPH"), Integer.valueOf(OverlayView.Companion.cscColor(c.hasRoadCurvature ? c.roadCurvature : 0.0f)));
        }
        if (c.training && t.getStarted()) {
            return TuplesKt.to("CURVE LEARNING " + MathKt.roundToInt(c.progress) + "%", Integer.valueOf(Color.rgb(112, 192, 216)));
        }
        return null;
    }
}
