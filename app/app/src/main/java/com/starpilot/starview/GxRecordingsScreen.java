package com.starpilot.starview;

import kotlin.jvm.internal.Intrinsics;

final class GxRecordingsScreen {
   private final String filename;
   private final boolean isCustomName;
   private final String png;
   private final String timestamp;

   public GxRecordingsScreen(String var1, String var2, String var3, boolean var4) {
      Intrinsics.checkNotNullParameter(var1, "filename");
      Intrinsics.checkNotNullParameter(var2, "png");
      Intrinsics.checkNotNullParameter(var3, "timestamp");
      super();
      this.filename = var1;
      this.png = var2;
      this.timestamp = var3;
      this.isCustomName = var4;
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

   public final boolean isCustomName() {
      return this.isCustomName;
   }
}
