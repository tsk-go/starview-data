package com.starpilot.starview;

import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: GxRecordings.kt */
final class GxRecordingsRoute {
    private final int approxDurationSeconds;
    private List<String> dates;
    private String displayDate;
    private String displayName;
    private List<String> ids;
    private boolean isCustomName;
    private boolean isPreserved;
    private final String name;
    private final String png;
    private final int segmentCount;
    private final String startedAt;
    private Long startedAtMs;
    private List<String> times;
    private String timestamp;
    private List<String> titles;

    public GxRecordingsRoute(String name, String timestamp, String startedAt, boolean isCustomName, boolean isPreserved, int segmentCount, int approxDurationSeconds, String png) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(png, "png");
        this.name = name;
        this.timestamp = timestamp;
        this.startedAt = startedAt;
        this.isCustomName = isCustomName;
        this.isPreserved = isPreserved;
        this.segmentCount = segmentCount;
        this.approxDurationSeconds = approxDurationSeconds;
        this.png = png;
        this.displayDate = "Unknown date";
        this.displayName = "";
        this.ids = CollectionsKt.emptyList();
        this.titles = CollectionsKt.emptyList();
        this.dates = CollectionsKt.emptyList();
        this.times = CollectionsKt.emptyList();
    }

    public final String getName() {
        return this.name;
    }

    public final String getStartedAt() {
        return this.startedAt;
    }

    public final String getTimestamp() {
        return this.timestamp;
    }

    /* renamed from: isCustomName, reason: from getter */
    public final boolean getIsCustomName() {
        return this.isCustomName;
    }

    /* renamed from: isPreserved, reason: from getter */
    public final boolean getIsPreserved() {
        return this.isPreserved;
    }

    public final void setCustomName(boolean z) {
        this.isCustomName = z;
    }

    public final void setPreserved(boolean z) {
        this.isPreserved = z;
    }

    public final void setTimestamp(String str) {
        this.timestamp = str;
    }

    public final int getApproxDurationSeconds() {
        return this.approxDurationSeconds;
    }

    public final String getPng() {
        return this.png;
    }

    public final int getSegmentCount() {
        return this.segmentCount;
    }

    public final String getDisplayDate() {
        return this.displayDate;
    }

    public final void setDisplayDate(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.displayDate = str;
    }

    public final String getDisplayName() {
        return this.displayName;
    }

    public final void setDisplayName(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.displayName = str;
    }

    public final Long getStartedAtMs() {
        return this.startedAtMs;
    }

    public final void setStartedAtMs(Long l) {
        this.startedAtMs = l;
    }

    public final List<String> getDates() {
        return this.dates;
    }

    public final List<String> getIds() {
        return this.ids;
    }

    public final List<String> getTimes() {
        return this.times;
    }

    public final List<String> getTitles() {
        return this.titles;
    }

    public final void setDates(List<String> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.dates = list;
    }

    public final void setIds(List<String> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.ids = list;
    }

    public final void setTimes(List<String> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.times = list;
    }

    public final void setTitles(List<String> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.titles = list;
    }
}
