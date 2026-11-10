package com.starpilot.starview;

import android.graphics.Bitmap;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: GxTheme.kt */
final class GxThemeFile {
    private final byte[] bytes;
    private final String fileName;
    private final String mime;
    private Bitmap thumb;
    private boolean thumbDone;

    public GxThemeFile(String fileName, byte[] bytes, String mime) {
        Intrinsics.checkNotNullParameter(fileName, "fileName");
        Intrinsics.checkNotNullParameter(bytes, "bytes");
        Intrinsics.checkNotNullParameter(mime, "mime");
        this.fileName = fileName;
        this.bytes = bytes;
        this.mime = mime;
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

    public final boolean getThumbDone() {
        return this.thumbDone;
    }

    public final void setThumbDone(boolean z) {
        this.thumbDone = z;
    }

    public final Bitmap getThumb() {
        return this.thumb;
    }

    public final void setThumb(Bitmap bitmap) {
        this.thumb = bitmap;
    }
}
