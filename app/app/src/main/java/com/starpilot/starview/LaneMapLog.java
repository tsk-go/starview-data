package com.starpilot.starview;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.provider.MediaStore;
import com.starpilot.starview.LaneBrain;
import com.starpilot.starview.LaneMapLog;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import kotlin.Pair;
import kotlin.Triple;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.io.ByteStreamsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;

/* compiled from: LaneMapLog.kt */
public final class LaneMapLog {
    public static final Companion Companion = new Companion(null);
    private static final long MAX_BYTES = 314572800;
    public static final int REC = 48;
    private static final double STEP_M = 0.9d;
    private final ByteBuffer buf;
    private final Context ctx;
    private File file;
    private long lastFlushMs;
    private double lastLat;
    private double lastLon;
    private BufferedOutputStream out;
    private volatile long points;

    /* compiled from: LaneMapLog.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final File dir(Context ctx) {
            Intrinsics.checkNotNullParameter(ctx, "ctx");
            File $this$dir_u24lambda_u240 = new File(ctx.getFilesDir(), "lanemap");
            $this$dir_u24lambda_u240.mkdirs();
            return $this$dir_u24lambda_u240;
        }

        private static final int encode$cm(float v) {
            if ((Float.isInfinite(v) || Float.isNaN(v)) ? false : true) {
                return RangesKt.coerceIn((int) (100 * v), -32000, 32000);
            }
            return -32768;
        }

        private static final int encode$pct(float v) {
            if ((Float.isInfinite(v) || Float.isNaN(v)) ? false : true) {
                return RangesKt.coerceIn((int) (100 * v), 0, 100);
            }
            return 255;
        }

        public final void encode(ByteBuffer buf, long unixMs, double lat, double lon, double headingDeg, float speed, boolean fixed, double accM, int lane, int lanes, double conf, int flags, float yL, float yR, float pL, float pR, int roadLanes, long wayId) {
            int h;
            Intrinsics.checkNotNullParameter(buf, "buf");
            if ((Double.isInfinite(headingDeg) || Double.isNaN(headingDeg)) ? false : true) {
                double d = 360;
                h = (int) ((((headingDeg % d) + d) % d) * 100);
            } else {
                h = 65535;
            }
            buf.clear();
            buf.putLong(unixMs);
            buf.putInt((int) Math.round(lat * 1.0E7d));
            buf.putInt((int) Math.round(1.0E7d * lon));
            buf.putShort((short) h);
            buf.putShort((short) RangesKt.coerceIn((int) (100 * speed), 0, 65535));
            buf.put((byte) (fixed ? 2 : 1));
            double d2 = 100;
            buf.put((byte) RangesKt.coerceIn((int) (accM * d2), 0, 255));
            buf.put((byte) RangesKt.coerceIn(lane, 0, 255));
            buf.put((byte) RangesKt.coerceIn(lanes, 0, 255));
            buf.put((byte) RangesKt.coerceIn((int) (d2 * conf), 0, 100));
            buf.put((byte) flags);
            buf.putShort((short) encode$cm(yL));
            buf.putShort((short) encode$cm(yR));
            buf.put((byte) encode$pct(pL));
            buf.put((byte) encode$pct(pR));
            buf.put((byte) RangesKt.coerceIn(roadLanes, 0, 255));
            buf.putLong(wayId);
            while (buf.position() < 48) {
                buf.put((byte) 0);
            }
        }

        /* compiled from: LaneMapLog.kt */
        public static final class Point {
            private final double accM;
            private final int conf;
            private final boolean fixed;
            private final int flags;
            private final double headingDeg;
            private final int lane;
            private final int lanes;
            private final double lat;
            private final double lon;
            private final int roadLanes;
            private final double speed;
            private final long unixMs;
            private final long wayId;
            private final double yL;
            private final double yR;

            public Point(long unixMs, double lat, double lon, double headingDeg, double speed, boolean fixed, double accM, int lane, int lanes, int conf, int flags, double yL, double yR, int roadLanes, long wayId) {
                this.unixMs = unixMs;
                this.lat = lat;
                this.lon = lon;
                this.headingDeg = headingDeg;
                this.speed = speed;
                this.fixed = fixed;
                this.accM = accM;
                this.lane = lane;
                this.lanes = lanes;
                this.conf = conf;
                this.flags = flags;
                this.yL = yL;
                this.yR = yR;
                this.roadLanes = roadLanes;
                this.wayId = wayId;
            }

            public final boolean getFixed() {
                return this.fixed;
            }

            public final double getHeadingDeg() {
                return this.headingDeg;
            }

            public final double getLat() {
                return this.lat;
            }

            public final double getLon() {
                return this.lon;
            }

            public final double getSpeed() {
                return this.speed;
            }

            public final long getUnixMs() {
                return this.unixMs;
            }

            public final double getAccM() {
                return this.accM;
            }

            public final int getConf() {
                return this.conf;
            }

            public final int getFlags() {
                return this.flags;
            }

            public final int getLane() {
                return this.lane;
            }

            public final int getLanes() {
                return this.lanes;
            }

            public final int getRoadLanes() {
                return this.roadLanes;
            }

            public final long getWayId() {
                return this.wayId;
            }

            public final double getYL() {
                return this.yL;
            }

            public final double getYR() {
                return this.yR;
            }
        }

        public final Point decode(ByteBuffer b, int at) {
            double d;
            double d2;
            Intrinsics.checkNotNullParameter(b, "b");
            b.order(ByteOrder.LITTLE_ENDIAN);
            int h = b.getShort(at + 16) & 65535;
            int yl = b.getShort(at + 26);
            int yr = b.getShort(at + 28);
            long j = b.getLong(at);
            double d3 = b.getInt(at + 8) / 1.0E7d;
            double d4 = b.getInt(at + 12) / 1.0E7d;
            double d5 = h == 65535 ? Double.NaN : h / 100.0d;
            double d6 = (65535 & b.getShort(at + 18)) / 100.0d;
            boolean z = decode$u8(b, at, 20) == 2;
            double decode$u8 = decode$u8(b, at, 21) / 100.0d;
            int decode$u82 = decode$u8(b, at, 22);
            int decode$u83 = decode$u8(b, at, 23);
            int decode$u84 = decode$u8(b, at, 24);
            int decode$u85 = decode$u8(b, at, 25);
            if (yl == -32768) {
                d = 100.0d;
                d2 = Double.NaN;
            } else {
                d = 100.0d;
                d2 = yl / 100.0d;
            }
            return new Point(j, d3, d4, d5, d6, z, decode$u8, decode$u82, decode$u83, decode$u84, decode$u85, d2, yr == -32768 ? Double.NaN : yr / d, decode$u8(b, at, 32), b.getLong(at + 33));
        }

        private static final int decode$u8(ByteBuffer $b, int $at, int o) {
            return $b.get($at + o) & 255;
        }
    }

    public LaneMapLog(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        this.ctx = ctx;
        this.lastLat = Double.NaN;
        this.lastLon = Double.NaN;
        this.buf = ByteBuffer.allocate(48).order(ByteOrder.LITTLE_ENDIAN);
    }

    public final long getPoints() {
        return this.points;
    }

    public final void frame(long nowMs, float vEgo, LaneBrain.Frame f, LaneBrain b, boolean turning) {
        double[] pos;
        Intrinsics.checkNotNullParameter(b, "b");
        if (vEgo >= 1.3f && RtkPosition.INSTANCE.fast(nowMs)) {
            String k = RtkPosition.INSTANCE.getKind();
            if (!Intrinsics.areEqual(k, "fixed") && !Intrinsics.areEqual(k, "float")) {
                return;
            }
            double acc = RtkPosition.INSTANCE.getAccM();
            if (!((Double.isInfinite(acc) || Double.isNaN(acc)) ? false : true) || acc > 0.5d || (pos = RtkPosition.INSTANCE.now(nowMs, vEgo)) == null) {
                return;
            }
            double lat = pos[0];
            double lon = pos[1];
            double d = this.lastLat;
            if ((Double.isInfinite(d) || Double.isNaN(d)) ? false : true) {
                double dy = (lat - this.lastLat) * 111195.0d;
                double dx = (lon - this.lastLon) * 111195.0d * Math.cos(Math.toRadians(lat));
                if ((dx * dx) + (dy * dy) < 0.81d) {
                    return;
                }
            }
            this.lastLat = lat;
            this.lastLon = lon;
            BufferedOutputStream bufferedOutputStream = this.out;
            if (bufferedOutputStream == null && (bufferedOutputStream = open(nowMs)) == null) {
                return;
            }
            BufferedOutputStream o = bufferedOutputStream;
            Pair<Integer, Double> laneFromLeft = b.laneFromLeft();
            int n = laneFromLeft.component1().intValue();
            double q = laneFromLeft.component2().doubleValue();
            int m = b.laneCount().component1().intValue();
            LaneBrain.RoadModel rm = b.getModel();
            LaneBrain.RoadInfo i = b.getInfo();
            int flags = turning ? 0 | 1 : 0;
            if ((f != null ? f.getOpChange() : 0) != 0) {
                flags |= 2;
            }
            if (i != null && i.getHpmsRamp()) {
                flags |= 4;
            }
            if (rm.getTunnel()) {
                flags |= 8;
            }
            if (rm.getBridge()) {
                flags |= 16;
            }
            if (rm.getExtraL() > 0) {
                flags |= 32;
            }
            if (rm.getExtraR() > 0) {
                flags |= 64;
            }
            int flags2 = flags;
            Companion companion = Companion;
            ByteBuffer buf = this.buf;
            Intrinsics.checkNotNullExpressionValue(buf, "buf");
            long currentTimeMillis = System.currentTimeMillis();
            double heading = RtkPosition.INSTANCE.getHeading();
            boolean areEqual = Intrinsics.areEqual(k, "fixed");
            float yl = f != null ? f.getYL() : Float.NaN;
            float yr = f != null ? f.getYR() : Float.NaN;
            float pl = f != null ? f.getPL() : Float.NaN;
            float pr = f != null ? f.getPR() : Float.NaN;
            int lanes = rm.getLanes();
            LaneBrain.Road road = b.getRoad();
            companion.encode(buf, currentTimeMillis, lat, lon, heading, vEgo, areEqual, acc, n, m, q, flags2, yl, yr, pl, pr, lanes, road != null ? road.getId() : 0L);
            try {
                o.write(this.buf.array(), 0, 48);
                this.points++;
                if (nowMs - this.lastFlushMs > 10000) {
                    try {
                        o.flush();
                        this.lastFlushMs = nowMs;
                    } catch (Exception e) {
                        close();
                    }
                }
            } catch (Exception e2) {
            }
        }
    }

    private final BufferedOutputStream open(long nowMs) {
        try {
            File d = Companion.dir(this.ctx);
            prune(d);
            File f = new File(d, new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date()) + ".lm");
            BufferedOutputStream s = new BufferedOutputStream(new FileOutputStream(f), 32768);
            byte[] bytes = "SVLM1\u0000".getBytes(Charsets.US_ASCII);
            Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
            s.write(bytes);
            s.write(new byte[]{1, 0, 48, 0});
            this.file = f;
            this.out = s;
            this.lastFlushMs = nowMs;
            return s;
        } catch (Exception e) {
            return null;
        }
    }

    private final void prune(File d) {
        List<File> fs;
        Object[] $this$sortedBy$iv = d.listFiles(new FileFilter() { // from class: com.starpilot.starview.LaneMapLog$$ExternalSyntheticLambda0
            @Override // java.io.FileFilter
            public final boolean accept(File file) {
                boolean prune$lambda$0;
                prune$lambda$0 = LaneMapLog.prune$lambda$0(file);
                return prune$lambda$0;
            }
        });
        if ($this$sortedBy$iv == null || (fs = ArraysKt.sortedWith($this$sortedBy$iv, new Comparator() { // from class: com.starpilot.starview.LaneMapLog$prune$$inlined$sortedBy$1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.Comparator
            public final int compare(Object t, Object t2) {
                File it = (File) t;
                File it2 = (File) t2;
                return ComparisonsKt.compareValues(it.getName(), it2.getName());
            }
        })) == null) {
            return;
        }
        long j = 0;
        for (File it : fs) {
            j += it.length();
        }
        long total = j;
        for (File f : fs) {
            if (total <= 314572800) {
                return;
            }
            total -= f.length();
            f.delete();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean prune$lambda$0(File f) {
        String name = f.getName();
        Intrinsics.checkNotNullExpressionValue(name, "getName(...)");
        return StringsKt.endsWith(name, ".lm", false);
    }

    public final void close() {
        try {
            BufferedOutputStream bufferedOutputStream = this.out;
            if (bufferedOutputStream != null) {
                bufferedOutputStream.flush();
            }
            BufferedOutputStream bufferedOutputStream2 = this.out;
            if (bufferedOutputStream2 != null) {
                bufferedOutputStream2.close();
            }
        } catch (Exception e) {
        }
        File f = this.file;
        if (f != null && f.length() <= 10) {
            f.delete();
        }
        this.out = null;
        this.file = null;
        this.lastLat = Double.NaN;
        this.lastLon = Double.NaN;
    }

    /* compiled from: LaneMapLog.kt */
    public static final class Export {
        public static final Export INSTANCE = new Export();

        private Export() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final boolean summary$lambda$0(File f) {
            String name = f.getName();
            Intrinsics.checkNotNullExpressionValue(name, "getName(...)");
            return StringsKt.endsWith(name, ".lm", false);
        }

        public final Triple<Integer, Long, Long> summary(Context ctx) {
            Intrinsics.checkNotNullParameter(ctx, "ctx");
            File[] fs = LaneMapLog.Companion.dir(ctx).listFiles(new FileFilter() { // from class: com.starpilot.starview.LaneMapLog$Export$$ExternalSyntheticLambda0
                @Override // java.io.FileFilter
                public final boolean accept(File file) {
                    boolean summary$lambda$0;
                    summary$lambda$0 = LaneMapLog.Export.summary$lambda$0(file);
                    return summary$lambda$0;
                }
            });
            if (fs == null) {
                return new Triple<>(0, 0L, 0L);
            }
            long j = 0;
            for (File it : fs) {
                j += it.length();
            }
            long bytes = j;
            Integer valueOf = Integer.valueOf(fs.length);
            long j2 = 0;
            for (File it2 : fs) {
                j2 += Math.max(0L, (it2.length() - 10) / 48);
            }
            return new Triple<>(valueOf, Long.valueOf(j2), Long.valueOf(bytes));
        }

        /* recovered by hand (jadx lost its use blocks and inverted the empty check) */
        public final String toDownloads(Context ctx) {
            Intrinsics.checkNotNullParameter(ctx, "ctx");
            try {
                File[] files = LaneMapLog.Companion.dir(ctx).listFiles(new FileFilter() { // from class: com.starpilot.starview.LaneMapLog$Export$$ExternalSyntheticLambda1
                    @Override // java.io.FileFilter
                    public final boolean accept(File file) {
                        return LaneMapLog.Export.toDownloads$lambda$3(file);
                    }
                });
                List fs = files != null ? ArraysKt.sortedWith(files, new Comparator() { // from class: com.starpilot.starview.LaneMapLog$Export$toDownloads$$inlined$sortedBy$1
                    @Override // java.util.Comparator
                    public final int compare(Object t, Object t2) {
                        return ComparisonsKt.compareValues(((File) t).getName(), ((File) t2).getName());
                    }
                }) : null;
                if (fs == null || fs.isEmpty()) {
                    return "No lane traces yet - they're logged while driving with an RTK fix.";
                }
                String name = "lanemap_" + new SimpleDateFormat("yyyy-MM-dd_HH-mm", Locale.US).format(new Date()) + ".zip";
                ContentValues cv = new ContentValues();
                cv.put("_display_name", name);
                cv.put("mime_type", "application/zip");
                cv.put("relative_path", "Download/StarView/Lane data");
                Uri uri = ctx.getContentResolver().insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, cv);
                if (uri == null) {
                    throw new IllegalStateException("couldn't create " + name);
                }
                OutputStream os = ctx.getContentResolver().openOutputStream(uri);
                Intrinsics.checkNotNull(os);
                Throwable osCause = null;
                try {
                    ZipOutputStream z = new ZipOutputStream(os);
                    Throwable zCause = null;
                    try {
                        Iterator it = fs.iterator();
                        while (it.hasNext()) {
                            File f = (File) it.next();
                            z.putNextEntry(new ZipEntry(f.getName()));
                            Intrinsics.checkNotNull(f);
                            FileInputStream in = new FileInputStream(f);
                            Throwable inCause = null;
                            try {
                                ByteStreamsKt.copyTo(in, z, 8192);
                            } catch (Throwable th) {
                                inCause = th;
                                throw th;
                            } finally {
                                CloseableKt.closeFinally(in, inCause);
                            }
                            z.closeEntry();
                        }
                    } catch (Throwable th) {
                        zCause = th;
                        throw th;
                    } finally {
                        CloseableKt.closeFinally(z, zCause);
                    }
                } catch (Throwable th) {
                    osCause = th;
                    throw th;
                } finally {
                    CloseableKt.closeFinally(os, osCause);
                }
                return null;
            } catch (Exception e) {
                return "Couldn't export: " + e.getMessage();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final boolean toDownloads$lambda$3(File f) {
            String name = f.getName();
            Intrinsics.checkNotNullExpressionValue(name, "getName(...)");
            return StringsKt.endsWith(name, ".lm", false) && f.length() > 10;
        }
    }
}
