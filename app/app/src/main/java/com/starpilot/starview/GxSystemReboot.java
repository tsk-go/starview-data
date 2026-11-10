package com.starpilot.starview;

import java.util.HashMap;

/* compiled from: GxSystem.kt */
final class GxSystemReboot {
    public static final GxSystemReboot INSTANCE = new GxSystemReboot();
    private static final HashMap<String, Long> startedAt = new HashMap<>();

    private GxSystemReboot() {
    }

    public final HashMap<String, Long> getStartedAt() {
        return startedAt;
    }
}
