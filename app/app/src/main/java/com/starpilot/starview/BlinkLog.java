package com.starpilot.starview;

import android.content.Context;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import kotlin.collections.ArrayDeque;
import kotlin.collections.CollectionsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* compiled from: BlinkLog.kt */
public final class BlinkLog {
    private static final long MAX_BYTES = 262144;
    private static final int MAX_LINES = 60;
    private static File file;
    public static final BlinkLog INSTANCE = new BlinkLog();
    private static final ArrayDeque<String> lines = new ArrayDeque<>();
    private static final SimpleDateFormat fmt = new SimpleDateFormat("MMM d HH:mm:ss", Locale.US);

    private BlinkLog() {
    }

    public final void init(Context ctx) {
        List readLines$default;
        Iterable takeLast;
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        if (file != null) {
            return;
        }
        file = new File(ctx.getFilesDir(), "blinker_log.txt");
        try {
            File it = file;
            if (it != null) {
                if (!it.isFile()) {
                    it = null;
                }
                if (it == null || (readLines$default = FilesKt.readLines$default(it, null, 1, null)) == null || (takeLast = CollectionsKt.takeLast(readLines$default, 60)) == null) {
                    return;
                }
                Iterable $this$forEach$iv = takeLast;
                for (Object element$iv : $this$forEach$iv) {
                    lines.addLast((String) element$iv);
                }
            }
        } catch (Exception e) {
        }
    }

    public final synchronized void add(String text) {
        Intrinsics.checkNotNullParameter(text, "text");
        String line = fmt.format(new Date()) + "  " + ((!Replay.INSTANCE.getActive() || StringsKt.startsWith$default(text, "Drive replay", false, 2, (Object) null)) ? "" : "[replay] ") + text;
        lines.addLast(line);
        while (lines.size() > 60) {
            lines.removeFirst();
        }
        File f = file;
        if (f == null) {
            return;
        }
        try {
            if (f.length() > 262144) {
                FilesKt.writeText$default(f, CollectionsKt.joinToString$default(lines, "\n", null, "\n", 0, null, null, 58, null), null, 2, null);
            } else {
                FilesKt.appendText$default(f, line + "\n", null, 2, null);
            }
        } catch (Exception e) {
        }
    }

    public static /* synthetic */ List recent$default(BlinkLog blinkLog, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = 30;
        }
        return blinkLog.recent(i);
    }

    public final synchronized List<String> recent(int n) {
        return CollectionsKt.reversed(CollectionsKt.takeLast(CollectionsKt.toList(lines), n));
    }
}
