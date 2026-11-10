package com.starpilot.starview;

import java.util.Locale;
import java.util.Set;
import kotlin.collections.SetsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.StringsKt;

/* compiled from: FileKinds.kt */
public final class FileKinds {
    private static final int CODE = -8604417;
    private static final int DOC = -5589304;
    private static final int FOLDER = -11549705;
    private static final int LOGC = -9508952;
    private static final int MEDIA = -28728;
    private static final int PACK = -22180;
    public static final FileKinds INSTANCE = new FileKinds();
    private static final Set<String> TEXT = SetsKt.setOf((Object[]) new String[]{"txt", "log", "md", "py", "sh", "conf", "cfg", "ini", "csv", "toml", "yaml", "yml", "service", "env", "kt", "js", "html", "css", "c", "h", "cc", "cpp", "patch", "diff", "xml"});
    private static final Set<String> IMAGE = SetsKt.setOf((Object[]) new String[]{"jpg", "jpeg", "png", "gif", "webp", "bmp"});
    private static final Set<String> VIDEO = SetsKt.setOf((Object[]) new String[]{"mp4", "hevc", "ts", "mkv", "mov", "264", "265"});
    private static final Set<String> ARCHIVE = SetsKt.setOf((Object[]) new String[]{"zip", "tar", "gz", "zst", "bz2", "xz", "7z", "apk", "tgz"});

    /* compiled from: FileKinds.kt */
    public enum Preview {
        TEXT,
        JSON,
        IMAGE,
        NONE;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<Preview> getEntries() {
            return $ENTRIES;
        }
    }

    private FileKinds() {
    }

    /* compiled from: FileKinds.kt */
    public static final class Kind {
        private final int color;
        private final String icon;
        private final Preview preview;

        public Kind(String icon, int color, Preview preview) {
            Intrinsics.checkNotNullParameter(icon, "icon");
            Intrinsics.checkNotNullParameter(preview, "preview");
            this.icon = icon;
            this.color = color;
            this.preview = preview;
        }

        public final int getColor() {
            return this.color;
        }

        public final String getIcon() {
            return this.icon;
        }

        public final Preview getPreview() {
            return this.preview;
        }
    }

    public final Kind of(String name, boolean dir) {
        Intrinsics.checkNotNullParameter(name, "name");
        if (dir) {
            return new Kind("folder", -11549705, Preview.NONE);
        }
        String n = name.toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(n, "toLowerCase(...)");
        String ext = StringsKt.substringAfterLast(n, '.', "");
        if (new Regex("^(rlog|qlog)(\\.(zst|bz2))?$").matches(n)) {
            return new Kind("console", -9508952, Preview.NONE);
        }
        if (Intrinsics.areEqual(ext, "json")) {
            return new Kind("code", -8604417, Preview.JSON);
        }
        if (Intrinsics.areEqual(ext, "log")) {
            return new Kind("console", -9508952, Preview.TEXT);
        }
        if (SetsKt.setOf((Object[]) new String[]{"py", "sh", "kt", "js", "c", "h", "cc", "cpp", "patch", "diff"}).contains(ext)) {
            return new Kind("code", -8604417, Preview.TEXT);
        }
        if (TEXT.contains(ext)) {
            return new Kind("file", -5589304, Preview.TEXT);
        }
        if (IMAGE.contains(ext)) {
            return new Kind("image", -28728, Preview.IMAGE);
        }
        if (VIDEO.contains(ext) || Intrinsics.areEqual(n, "fcamera.hevc") || Intrinsics.areEqual(n, "ecamera.hevc")) {
            return new Kind("film", -28728, Preview.NONE);
        }
        if (ARCHIVE.contains(ext)) {
            return new Kind("archive", -22180, Preview.NONE);
        }
        return ext.length() == 0 ? new Kind("file", -5589304, Preview.TEXT) : new Kind("file", -5589304, Preview.NONE);
    }

    public final boolean showable(byte[] bytes) {
        int i;
        Intrinsics.checkNotNullParameter(bytes, "bytes");
        int n = Math.min(bytes.length, 4096);
        int bad = 0;
        while (true) {
            boolean z = false;
            if (i >= n) {
                return n == 0 || bad * 20 < n;
            }
            int b = bytes[i] & 255;
            if (b == 0) {
                return false;
            }
            if (b >= 9) {
                if (14 <= b && b < 32) {
                    z = true;
                }
                i = z ? 0 : i + 1;
            }
            bad++;
        }
    }
}
