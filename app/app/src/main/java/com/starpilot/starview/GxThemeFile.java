package com.starpilot.starview;

import android.graphics.Bitmap;
import kotlin.jvm.internal.Intrinsics;

final class GxThemeFile {
   private final byte[] bytes;
   private final String fileName;
   private final String mime;
   private Bitmap thumb;
   private boolean thumbDone;

   public GxThemeFile(String var1, byte[] var2, String var3) {
      Intrinsics.checkNotNullParameter(var1, "fileName");
      Intrinsics.checkNotNullParameter(var2, "bytes");
      Intrinsics.checkNotNullParameter(var3, "mime");
      super();
      this.fileName = var1;
      this.bytes = var2;
      this.mime = var3;
   }

   public final byte[] getBytes() {
      return this.bytes;
   }

   public final String getFileName() {
      return this.fileName;
   }

   public final String getMime() {
      return this.mime;
   }

   public final Bitmap getThumb() {
      return this.thumb;
   }

   public final boolean getThumbDone() {
      return this.thumbDone;
   }

   public final void setThumb(Bitmap var1) {
      this.thumb = var1;
   }

   public final void setThumbDone(boolean var1) {
      this.thumbDone = var1;
   }
}
