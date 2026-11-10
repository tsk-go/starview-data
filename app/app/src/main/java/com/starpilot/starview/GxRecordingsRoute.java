package com.starpilot.starview;

import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

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

   public GxRecordingsRoute(String var1, String var2, String var3, boolean var4, boolean var5, int var6, int var7, String var8) {
      Intrinsics.checkNotNullParameter(var1, "name");
      Intrinsics.checkNotNullParameter(var8, "png");
      super();
      this.name = var1;
      this.timestamp = var2;
      this.startedAt = var3;
      this.isCustomName = var4;
      this.isPreserved = var5;
      this.segmentCount = var6;
      this.approxDurationSeconds = var7;
      this.png = var8;
      this.displayDate = "Unknown date";
      this.displayName = "";
      this.ids = CollectionsKt.emptyList();
      this.titles = CollectionsKt.emptyList();
      this.dates = CollectionsKt.emptyList();
      this.times = CollectionsKt.emptyList();
   }

   public final int getApproxDurationSeconds() {
      return this.approxDurationSeconds;
   }

   public final List<String> getDates() {
      return this.dates;
   }

   public final String getDisplayDate() {
      return this.displayDate;
   }

   public final String getDisplayName() {
      return this.displayName;
   }

   public final List<String> getIds() {
      return this.ids;
   }

   public final String getName() {
      return this.name;
   }

   public final String getPng() {
      return this.png;
   }

   public final int getSegmentCount() {
      return this.segmentCount;
   }

   public final String getStartedAt() {
      return this.startedAt;
   }

   public final Long getStartedAtMs() {
      return this.startedAtMs;
   }

   public final List<String> getTimes() {
      return this.times;
   }

   public final String getTimestamp() {
      return this.timestamp;
   }

   public final List<String> getTitles() {
      return this.titles;
   }

   public final boolean isCustomName() {
      return this.isCustomName;
   }

   public final boolean isPreserved() {
      return this.isPreserved;
   }

   public final void setCustomName(boolean var1) {
      this.isCustomName = var1;
   }

   public final void setDates(List<String> var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.dates = var1;
   }

   public final void setDisplayDate(String var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.displayDate = var1;
   }

   public final void setDisplayName(String var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.displayName = var1;
   }

   public final void setIds(List<String> var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.ids = var1;
   }

   public final void setPreserved(boolean var1) {
      this.isPreserved = var1;
   }

   public final void setStartedAtMs(Long var1) {
      this.startedAtMs = var1;
   }

   public final void setTimes(List<String> var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.times = var1;
   }

   public final void setTimestamp(String var1) {
      this.timestamp = var1;
   }

   public final void setTitles(List<String> var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.titles = var1;
   }
}
