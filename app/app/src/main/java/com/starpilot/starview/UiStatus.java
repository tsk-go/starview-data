package com.starpilot.starview;

import android.graphics.Color;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: OverlayView.kt */
public enum UiStatus {
    DISENGAGED(Color.rgb(18, 40, 57)),
    ENGAGED(Color.rgb(22, 127, 64)),
    OVERRIDE(Color.rgb(137, 146, 141)),
    EXPERIMENTAL(Color.rgb(218, 111, 37)),
    AOL(Color.rgb(10, 186, 181)),
    TRAFFIC(Color.rgb(201, 34, 49)),
    LONG_ONLY(Color.rgb(255, 105, 180)),
    PULSE_GLIDE(Color.rgb(24, 72, 150));

    private final int color;
    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries($VALUES);
    public static final Companion Companion = new Companion(null);

    UiStatus(int color) {
        this.color = color;
    }

    public final int getColor() {
        return this.color;
    }

    /* compiled from: OverlayView.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final UiStatus from(Telemetry t) {
            Intrinsics.checkNotNullParameter(t, "t");
            return !t.getEnabled() ? (t.getAolEnabled() && t.getStarted()) ? UiStatus.AOL : UiStatus.DISENGAGED : t.getPulseGlideCoasting() ? UiStatus.PULSE_GLIDE : (!t.getEnabled() || t.getActive()) ? t.getPauseLateral() ? UiStatus.LONG_ONLY : t.getTrafficMode() ? UiStatus.TRAFFIC : t.getExperimentalMode() ? UiStatus.EXPERIMENTAL : UiStatus.ENGAGED : UiStatus.OVERRIDE;
        }
    }

    public static EnumEntries<UiStatus> getEntries() {
        return $ENTRIES;
    }
}
