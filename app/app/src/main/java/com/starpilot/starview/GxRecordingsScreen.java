package com.starpilot.starview;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: GxRecordings.kt */
final class GxRecordingsScreen {
    private final String filename;
    private final boolean isCustomName;
    private final String png;
    private final String timestamp;

    public GxRecordingsScreen(String filename, String png, String timestamp, boolean isCustomName) {
        Intrinsics.checkNotNullParameter(filename, "filename");
        Intrinsics.checkNotNullParameter(png, "png");
        Intrinsics.checkNotNullParameter(timestamp, "timestamp");
        this.filename = filename;
        this.png = png;
        this.timestamp = timestamp;
        this.isCustomName = isCustomName;
    }

    public final String getFilename() {
        return this.filename;
    }

    public final String getPng() {
        return this.png;
    }

    public final String getTimestamp() {
        return this.timestamp;
    }

    /* recovered: the original JVM name (callers taken from Vineflower use it) */

    public final boolean isCustomName() {

        return getIsCustomName();

    }


    /* renamed from: isCustomName, reason: from getter */
    public final boolean getIsCustomName() {
        return this.isCustomName;
    }
}
