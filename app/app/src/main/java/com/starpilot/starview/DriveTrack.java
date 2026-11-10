package com.starpilot.starview;

import android.content.Context;
import com.starpilot.starview.DriveTrack;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* compiled from: DriveTrack.kt */
public final class DriveTrack {
    private static File dir;
    private static File file;
    private static long lastT;
    public static final DriveTrack INSTANCE = new DriveTrack();
    private static double lastLat = Double.NaN;
    private static double lastLon = Double.NaN;
    private static final StringBuilder buf = new StringBuilder();

    /* compiled from: DriveTrack.kt */
    public static final class Pt {
        private final double lat;
        private final double lon;
        private final long t;

        public Pt(long t, double lat, double lon) {
            this.t = t;
            this.lat = lat;
            this.lon = lon;
        }

        public final double getLat() {
            return this.lat;
        }

        public final double getLon() {
            return this.lon;
        }

        public final long getT() {
            return this.t;
        }
    }

    private DriveTrack() {
    }

    public final void init(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        if (dir == null) {
            File $this$init_u24lambda_u240 = new File(ctx.getFilesDir(), "tracks");
            $this$init_u24lambda_u240.mkdirs();
            INSTANCE.prune($this$init_u24lambda_u240);
            dir = $this$init_u24lambda_u240;
        }
    }

    public static /* synthetic */ void tick$default(DriveTrack driveTrack, Telemetry telemetry, long j, int i, Object obj) {
        if ((i & 2) != 0) {
            j = System.currentTimeMillis();
        }
        driveTrack.tick(telemetry, j);
    }

    public final void tick(Telemetry t, long nowMs) {
        Intrinsics.checkNotNullParameter(t, "t");
        File d = dir;
        if (d == null) {
            return;
        }
        if (!t.getStarted()) {
            if (file != null) {
                flush();
                file = null;
                return;
            }
            return;
        }
        if (file == null) {
            file = new File(d, nowMs + ".csv");
            lastT = 0L;
        }
        if (nowMs - lastT < 2000) {
            return;
        }
        double[] rtk = RtkPosition.INSTANCE.now(nowMs, t.getVEgo());
        double lat = rtk != null ? rtk[0] : t.getGpsLat();
        double lon = rtk != null ? rtk[1] : t.getGpsLon();
        if ((Double.isInfinite(lat) || Double.isNaN(lat)) ? false : true) {
            if ((Double.isInfinite(lon) || Double.isNaN(lon)) ? false : true) {
                if (lat == 0.0d) {
                    if (lon == 0.0d) {
                        return;
                    }
                }
                double moved = Double.isNaN(lastLat) ? 1.0E9d : Math.hypot((lat - lastLat) * 111195.0d, (lon - lastLon) * 111320.0d * Math.cos(Math.toRadians(lat)));
                if (moved >= 15.0d || nowMs - lastT >= 20000) {
                    lastT = nowMs;
                    lastLat = lat;
                    lastLon = lon;
                    StringBuilder append = buf.append(nowMs).append(',');
                    String format = String.format(Locale.US, "%.6f", Arrays.copyOf(new Object[]{Double.valueOf(lat)}, 1));
                    Intrinsics.checkNotNullExpressionValue(format, "format(...)");
                    StringBuilder append2 = append.append(format).append(',');
                    String format2 = String.format(Locale.US, "%.6f", Arrays.copyOf(new Object[]{Double.valueOf(lon)}, 1));
                    Intrinsics.checkNotNullExpressionValue(format2, "format(...)");
                    append2.append(format2).append('\n');
                    if (buf.length() > 4000) {
                        flush();
                    }
                }
            }
        }
    }

    private final void flush() {
        File f = file;
        if (f == null) {
            return;
        }
        if (buf.length() == 0) {
            return;
        }
        String text = buf.toString();
        Intrinsics.checkNotNullExpressionValue(text, "toString(...)");
        buf.setLength(0);
        try {
            FilesKt.appendText(f, text, kotlin.text.Charsets.UTF_8);
        } catch (Exception e) {
        }
    }

    public final List<Pt> between(Context ctx, final long fromMs, final long toMs) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        flush();
        File file2 = dir;
        if (file2 == null) {
            file2 = new File(ctx.getFilesDir(), "tracks");
        }
        File d = file2;
        final ArrayList out = new ArrayList();
        File[] listFiles = d.listFiles(new FileFilter() { // from class: com.starpilot.starview.DriveTrack$$ExternalSyntheticLambda0
            @Override // java.io.FileFilter
            public final boolean accept(File file3) {
                boolean between$lambda$1;
                between$lambda$1 = DriveTrack.between$lambda$1(file3);
                return between$lambda$1;
            }
        });
        if (listFiles == null) {
            listFiles = new File[0];
        }
        for (File f : listFiles) {
            Intrinsics.checkNotNull(f);
            Long longOrNull = StringsKt.toLongOrNull(FilesKt.getNameWithoutExtension(f));
            if (longOrNull != null) {
                long start = longOrNull.longValue();
                if (start <= toMs && f.lastModified() >= fromMs) {
                    try {
                        FilesKt.forEachLine(f, kotlin.text.Charsets.UTF_8, new Function1() { // from class: com.starpilot.starview.DriveTrack$$ExternalSyntheticLambda1
                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj) {
                                Unit between$lambda$2;
                                between$lambda$2 = DriveTrack.between$lambda$2(fromMs, toMs, out, (String) obj);
                                return between$lambda$2;
                            }
                        });
                    } catch (Exception e) {
                    }
                }
            }
        }
        ArrayList $this$sortBy$iv = out;
        if ($this$sortBy$iv.size() > 1) {
            CollectionsKt.sortWith($this$sortBy$iv, new Comparator() { // from class: com.starpilot.starview.DriveTrack$between$$inlined$sortBy$1
                /* JADX WARN: Multi-variable type inference failed */
                @Override // java.util.Comparator
                public final int compare(Object t, Object t2) {
                    DriveTrack.Pt it = (DriveTrack.Pt) t;
                    DriveTrack.Pt it2 = (DriveTrack.Pt) t2;
                    return ComparisonsKt.compareValues(Long.valueOf(it.getT()), Long.valueOf(it2.getT()));
                }
            });
        }
        return out;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean between$lambda$1(File x) {
        String name = x.getName();
        Intrinsics.checkNotNullExpressionValue(name, "getName(...)");
        return StringsKt.endsWith(name, ".csv", false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit between$lambda$2(long $fromMs, long $toMs, ArrayList $out, String l) {
        Long longOrNull;
        Intrinsics.checkNotNullParameter(l, "l");
        boolean z = false;
        List p = StringsKt.split((CharSequence) l, new char[]{','}, false, 0);
        if (p.size() >= 3 && (longOrNull = StringsKt.toLongOrNull((String) p.get(0))) != null) {
            long t = longOrNull.longValue();
            if ($fromMs <= t && t <= $toMs) {
                z = true;
            }
            if (z) {
                $out.add(new Pt(t, Double.parseDouble((String) p.get(1)), Double.parseDouble((String) p.get(2))));
            }
            return Unit.INSTANCE;
        }
        return Unit.INSTANCE;
    }

    private final void prune(File d) {
        long old = System.currentTimeMillis() - 7776000000L;
        File[] listFiles = d.listFiles();
        if (listFiles == null) {
            return;
        }
        for (File file2 : listFiles) {
            if (file2.lastModified() < old) {
                file2.delete();
            }
        }
    }
}
