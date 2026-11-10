package com.starpilot.starview;

import android.content.Context;
import java.io.File;
import java.io.FileFilter;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Triple;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.math.MathKt;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import kotlin.text.Charsets;
import kotlin.text.Regex;
import kotlin.text.StringsKt;

/* compiled from: Hpms.kt */
public final class Hpms {
    public static final int BRIDGE = 1;
    public static final int TUNNEL = 2;
    private static volatile File dir;
    private static long listedAt;
    public static final Hpms INSTANCE = new Hpms();
    private static final HashMap<String, Pack> packs = new HashMap<>();

    private Hpms() {
    }

    /* compiled from: Hpms.kt */
    public static final class Section {
        private final int access;
        private final int dirLanes;
        private final int fSystem;
        private final int facility;
        private final int laneWidthFt;
        private final int lanes;
        private final double[] lat;
        private final double[] lon;
        private final int median;
        private final String name;
        private final int structure;

        public Section(int lanes, int dirLanes, int facility, int structure, int fSystem, int laneWidthFt, int access, int median, String name, double[] lon, double[] lat) {
            Intrinsics.checkNotNullParameter(name, "name");
            Intrinsics.checkNotNullParameter(lon, "lon");
            Intrinsics.checkNotNullParameter(lat, "lat");
            this.lanes = lanes;
            this.dirLanes = dirLanes;
            this.facility = facility;
            this.structure = structure;
            this.fSystem = fSystem;
            this.laneWidthFt = laneWidthFt;
            this.access = access;
            this.median = median;
            this.name = name;
            this.lon = lon;
            this.lat = lat;
        }

        public final int getDirLanes() {
            return this.dirLanes;
        }

        public final int getFSystem() {
            return this.fSystem;
        }

        public final int getFacility() {
            return this.facility;
        }

        public final int getLaneWidthFt() {
            return this.laneWidthFt;
        }

        public final int getLanes() {
            return this.lanes;
        }

        public final int getStructure() {
            return this.structure;
        }

        public final int getAccess() {
            return this.access;
        }

        public final double[] getLat() {
            return this.lat;
        }

        public final double[] getLon() {
            return this.lon;
        }

        public final int getMedian() {
            return this.median;
        }

        public final String getName() {
            return this.name;
        }

        public final boolean getRamp() {
            return this.facility == 4;
        }

        public final boolean getTunnel() {
            return this.structure == 2;
        }

        public final boolean getBridge() {
            return this.structure == 1;
        }

        public final boolean getOurLanesSure() {
            int i = this.dirLanes;
            return (1 <= i && i < 21) || this.facility != 2 || this.lanes % 2 == 0;
        }

        public final int ourLanes() {
            int i = this.dirLanes;
            if (1 <= i && i < 21) {
                return this.dirLanes;
            }
            int i2 = this.lanes;
            if (1 <= i2 && i2 < 41) {
                return this.facility == 2 ? (this.lanes + 1) / 2 : this.lanes;
            }
            return 0;
        }
    }

    /* compiled from: Hpms.kt */
    public static final class Match {
        private final double angleDeg;
        private final double distM;
        private final Section s;
        private final String state;

        public Match(Section s, double distM, double angleDeg, String state) {
            Intrinsics.checkNotNullParameter(s, "s");
            Intrinsics.checkNotNullParameter(state, "state");
            this.s = s;
            this.distM = distM;
            this.angleDeg = angleDeg;
            this.state = state;
        }

        public final double getAngleDeg() {
            return this.angleDeg;
        }

        public final double getDistM() {
            return this.distM;
        }

        public final Section getS() {
            return this.s;
        }

        public final String getState() {
            return this.state;
        }
    }

    /* compiled from: Hpms.kt */
    public static final class Pack {
        private final String abbr;
        private final ByteBuffer buf;
        private final File file;
        private final int maxTx;
        private final int maxTy;
        private final int minTx;
        private final int minTy;
        private final int nChunks;
        private final int nNames;
        private final int nTiles;
        private final HashMap<Integer, String> names;
        private final int oChunks;
        private final int oCidx;
        private final int oNames;
        private final int oTiles;
        private final int state;
        private final int tile;
        private final int year;

        public Pack(File file) {
            Intrinsics.checkNotNullParameter(file, "file");
            this.file = file;
            String name = this.file.getName();
            Intrinsics.checkNotNullExpressionValue(name, "getName(...)");
            this.abbr = StringsKt.take(StringsKt.removePrefix(name, (CharSequence) "hpms_"), 2);
            this.names = new HashMap<>();
            RandomAccessFile randomAccessFile = new RandomAccessFile(this.file, "r");
            Throwable randomAccessFile$c = null;
            try {
                RandomAccessFile raf = randomAccessFile;
                this.buf = raf.getChannel().map(FileChannel.MapMode.READ_ONLY, 0L, raf.length());
                Unit unit = Unit.INSTANCE;
                this.buf.order(ByteOrder.LITTLE_ENDIAN);
                byte[] magic = new byte[8];
                this.buf.position(0);
                this.buf.get(magic);
                if (!Intrinsics.areEqual(new String(magic, Charsets.US_ASCII), "SVHPMS1\u0000")) {
                    throw new IllegalArgumentException("not an HPMS pack".toString());
                }
                if (!(this.buf.getShort(8) == 1)) {
                    throw new IllegalArgumentException("unknown version".toString());
                }
                this.state = this.buf.getShort(10) & 65535;
                this.year = this.buf.getShort(12) & 65535;
                this.tile = this.buf.getShort(14) & 65535;
                this.nNames = this.buf.getInt(16);
                this.nTiles = this.buf.getInt(20);
                this.nChunks = this.buf.getInt(24);
                this.oNames = this.buf.getInt(28);
                this.oTiles = this.buf.getInt(32);
                this.oCidx = this.buf.getInt(36);
                this.oChunks = this.buf.getInt(40);
                this.minTx = this.buf.getInt(48);
                this.minTy = this.buf.getInt(52);
                this.maxTx = this.buf.getInt(56);
                this.maxTy = this.buf.getInt(60);
            } catch (Throwable randomAccessFile$t) {
                randomAccessFile$c = randomAccessFile$t;
                throw randomAccessFile$t;
            } finally {
                CloseableKt.closeFinally(randomAccessFile, randomAccessFile$c);
            }
        }

        public final File getFile() {
            return this.file;
        }

        public final String getAbbr() {
            return this.abbr;
        }

        public final int getState() {
            return this.state;
        }

        public final int getYear() {
            return this.year;
        }

        public final int getMaxTx() {
            return this.maxTx;
        }

        public final int getMaxTy() {
            return this.maxTy;
        }

        public final int getMinTx() {
            return this.minTx;
        }

        public final int getMinTy() {
            return this.minTy;
        }

        public final boolean covers(double lat, double lon) {
            int tx = (int) Math.floorDiv(e5(lon), this.tile);
            int ty = (int) Math.floorDiv(e5(lat), this.tile);
            if (tx <= this.maxTx + 1 && this.minTx - 1 <= tx) {
                if (ty <= this.maxTy + 1 && this.minTy - 1 <= ty) {
                    return true;
                }
            }
            return false;
        }

        private final String name(int i) {
            String str;
            Map $this$getOrPut$iv = this.names;
            Integer valueOf = Integer.valueOf(i);
            String str2 = ((String) $this$getOrPut$iv.get(valueOf));
            if (str2 == null) {
                int base = this.oNames + ((this.nNames + 1) * 4);
                int a = this.buf.getInt(this.oNames + (i * 4));
                int b = this.buf.getInt(this.oNames + ((i + 1) * 4));
                byte[] bytes = new byte[b - a];
                int length = bytes.length;
                for (int k = 0; k < length; k++) {
                    bytes[k] = this.buf.get(base + a + k);
                }
                str = new String(bytes, Charsets.UTF_8);
                $this$getOrPut$iv.put(valueOf, str);
            } else {
                str = str2;
            }
            return str;
        }

        private final IntRange tileRange(int tx, int ty) {
            int lo = 0;
            int hi = this.nTiles;
            while (lo < hi) {
                int m = (lo + hi) >>> 1;
                int o = this.oTiles + (m * 16);
                int x = this.buf.getInt(o);
                int y = this.buf.getInt(o + 4);
                if (x < tx || (x == tx && y < ty)) {
                    lo = m + 1;
                } else {
                    hi = m;
                }
            }
            if (lo >= this.nTiles) {
                return IntRange.Companion.getEMPTY();
            }
            int o2 = this.oTiles + (lo * 16);
            if (this.buf.getInt(o2) != tx || this.buf.getInt(o2 + 4) != ty) {
                return IntRange.Companion.getEMPTY();
            }
            int f = this.buf.getInt(o2 + 8);
            return RangesKt.until(f, this.buf.getInt(o2 + 12) + f);
        }

        public final Section section(int i) {
            Ref.IntRef o = new Ref.IntRef();
            o.element = this.oChunks + this.buf.getInt(this.oCidx + (i * 4));
            int[] a = new int[8];
            for (int i2 = 0; i2 < 8; i2++) {
                a[i2] = section$u8(this, o, i2);
            }
            int ni = this.buf.getInt(o.element + 8);
            int n = this.buf.getShort(o.element + 12) & 65535;
            long x = this.buf.getInt(o.element + 14);
            long y = this.buf.getInt(o.element + 18);
            o.element += 22;
            double[] lon = new double[n];
            double[] lat = new double[n];
            lon[0] = x / 100000.0d;
            lat[0] = y / 100000.0d;
            int k = 1;
            while (k < n) {
                x += section$varint(this, o);
                y += section$varint(this, o);
                lon[k] = x / 100000.0d;
                lat[k] = y / 100000.0d;
                k++;
                n = n;
            }
            return new Section(a[0], a[1], a[2], a[3], a[4], a[5], a[6], a[7], name(ni), lon, lat);
        }

        private static final int section$u8(Pack this$0, Ref.IntRef o, int k) {
            int it = this$0.buf.get(o.element + k) & 255;
            if (it == 255) {
                return -1;
            }
            return it;
        }

        private static final long section$varint(Pack this$0, Ref.IntRef o) {
            int b;
            long v = 0;
            int s = 0;
            do {
                ByteBuffer byteBuffer = this$0.buf;
                int i = o.element;
                o.element = i + 1;
                b = byteBuffer.get(i) & 255;
                v |= ((long) (b & 127)) << s;
                s += 7;
            } while (b >= 128);
            return (v >>> 1) ^ (-(1 & v));
        }

        public final List<Triple<Section, Double, Double>> near(double lat, double lon, double r) {
            int tx;
            int ty;
            double kx;
            double ky;
            double kx2;
            double coerceIn;
            Pack pack = this;
            double d = lon;
            int tx2 = (int) Math.floorDiv(pack.e5(d), pack.tile);
            int ty2 = (int) Math.floorDiv(e5(lat), pack.tile);
            double kx3 = Math.cos(Math.toRadians(lat)) * 111320.0d;
            double ky2 = 110574.0d;
            ArrayList out = new ArrayList();
            int dx = -1;
            while (true) {
                if (dx >= 2) {
                    return out;
                }
                int dy = -1;
                for (int i = 2; dy < i; i = 2) {
                    IntRange tileRange = pack.tileRange(tx2 + dx, ty2 + dy);
                    int i2 = tileRange.getFirst();
                    int last = tileRange.getLast();
                    if (i2 <= last) {
                        while (true) {
                            Section s = pack.section(i2);
                            double best = Double.MAX_VALUE;
                            double dir = 0.0d;
                            int length = s.getLon().length - 1;
                            int k = 0;
                            while (k < length) {
                                double ax = (s.getLon()[k] - d) * kx3;
                                double ay = (s.getLat()[k] - lat) * ky2;
                                double bx = (s.getLon()[k + 1] - d) * kx3;
                                double by = (s.getLat()[k + 1] - lat) * ky2;
                                double vx = bx - ax;
                                int tx3 = tx2;
                                int ty3 = ty2;
                                double vy = by - ay;
                                double l2 = (vx * vx) + (vy * vy);
                                if (l2 < 1.0E-6d) {
                                    coerceIn = 0.0d;
                                    kx2 = kx3;
                                } else {
                                    kx2 = kx3;
                                    double kx4 = (ax * vx) + (ay * vy);
                                    coerceIn = RangesKt.coerceIn((-kx4) / l2, 0.0d, 1.0d);
                                }
                                double t = coerceIn;
                                double ky3 = ky2;
                                double ky4 = ax + (t * vx);
                                double d2 = Math.hypot(ky4, ay + (t * vy));
                                if (d2 < best) {
                                    double best2 = Math.atan2(vy, vx);
                                    dir = best2;
                                    best = d2;
                                }
                                k++;
                                d = lon;
                                tx2 = tx3;
                                ty2 = ty3;
                                ky2 = ky3;
                                kx3 = kx2;
                            }
                            tx = tx2;
                            ty = ty2;
                            kx = kx3;
                            ky = ky2;
                            if (best <= r) {
                                out.add(new Triple(s, Double.valueOf(best), Double.valueOf(dir)));
                            }
                            if (i2 != last) {
                                i2++;
                                pack = this;
                                d = lon;
                                tx2 = tx;
                                ty2 = ty;
                                ky2 = ky;
                                kx3 = kx;
                            } else {
                                break;
                            }
                        }
                    } else {
                        tx = tx2;
                        ty = ty2;
                        kx = kx3;
                        ky = ky2;
                    }
                    dy++;
                    pack = this;
                    d = lon;
                    tx2 = tx;
                    ty2 = ty;
                    ky2 = ky;
                    kx3 = kx;
                }
                dx++;
                pack = this;
                d = lon;
            }
        }

        private final long e5(double v) {
            return MathKt.roundToLong(100000.0d * v);
        }
    }

    public final File getDir() {
        return dir;
    }

    public final void init(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        if (dir == null) {
            File $this$init_u24lambda_u240 = new File(ctx.getFilesDir(), "hpms");
            $this$init_u24lambda_u240.mkdirs();
            dir = $this$init_u24lambda_u240;
        }
    }

    public final void useDir(File d) {
        Intrinsics.checkNotNullParameter(d, "d");
        dir = d;
        synchronized (packs) {
            packs.clear();
            Unit unit = Unit.INSTANCE;
        }
        listedAt = 0L;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean files$lambda$2(File f) {
        String name = f.getName();
        Intrinsics.checkNotNullExpressionValue(name, "getName(...)");
        return new Regex("hpms_[A-Z]{2}\\.bin").matches(name);
    }

    public final Map<String, File> files() {
        File[] listFiles;
        File file = dir;
        if (file == null || (listFiles = file.listFiles(new FileFilter() { // from class: com.starpilot.starview.Hpms$$ExternalSyntheticLambda0
            @Override // java.io.FileFilter
            public final boolean accept(File file2) {
                boolean files$lambda$2;
                files$lambda$2 = Hpms.files$lambda$2(file2);
                return files$lambda$2;
            }
        })) == null) {
            return MapsKt.emptyMap();
        }
        int capacity$iv = RangesKt.coerceAtLeast(MapsKt.mapCapacity(listFiles.length), 16);
        Map destination$iv$iv = new LinkedHashMap(capacity$iv);
        for (File file2 : listFiles) {
            String name = file2.getName();
            Intrinsics.checkNotNullExpressionValue(name, "getName(...)");
            String substring = name.substring(5, 7);
            Intrinsics.checkNotNullExpressionValue(substring, "substring(...)");
            destination$iv$iv.put(substring, file2);
        }
        return destination$iv$iv;
    }

    public final void reload() {
        synchronized (packs) {
            packs.clear();
            Unit unit = Unit.INSTANCE;
        }
        listedAt = 0L;
    }

    private final List<Pack> open() {
        List<Pack> list;
        synchronized (packs) {
            long now = System.currentTimeMillis();
            if (now - listedAt > 60000) {
                listedAt = now;
                for (Map.Entry<String, File> entry : INSTANCE.files().entrySet()) {
                    String st = entry.getKey();
                    File f = entry.getValue();
                    if (!packs.containsKey(st)) {
                        try {
                            packs.put(st, new Pack(f));
                        } catch (Exception e) {
                        }
                    }
                }
            }
            Collection<Pack> values = packs.values();
            Intrinsics.checkNotNullExpressionValue(values, "<get-values>(...)");
            list = CollectionsKt.toList(values);
        }
        return list;
    }

    public static /* synthetic */ Match match$default(Hpms hpms, double lat, double lon, double headingRad, Boolean rampWanted, double maxM, String preferName, int i, Object obj) {
        return hpms.match(lat, lon, headingRad, rampWanted, (i & 16) != 0 ? 45.0d : maxM, (i & 32) != 0 ? "" : preferName);
    }

    public final Match match(double lat, double lon, double headingRad, Boolean rampWanted, double maxM, String preferName) {
        Match bn;
        Intrinsics.checkNotNullParameter(preferName, "preferName");
        Match best = null;
        Match bestNamed = null;
        for (Pack p : open()) {
            if (p.covers(lat, lon)) {
                for (Triple<Section, Double, Double> triple : p.near(lat, lon, maxM)) {
                    Section s = triple.component1();
                    double d = triple.component2().doubleValue();
                    double dir2 = triple.component3().doubleValue();
                    if (rampWanted == null || Intrinsics.areEqual(Boolean.valueOf(s.getRamp()), rampWanted)) {
                        double a = Math.toDegrees(Math.abs(angleDiff(dir2, headingRad)));
                        if (a > 90.0d) {
                            a = 180.0d - a;
                        }
                        double a2 = a;
                        if (a2 <= 30.0d) {
                            double score = d + (a2 * 0.5d);
                            Match b = best;
                            if (b == null || score < b.getDistM() + (b.getAngleDeg() * 0.5d)) {
                                best = new Match(s, d, a2, p.getAbbr());
                            }
                            if ((preferName.length() > 0) && Intrinsics.areEqual(s.getName(), preferName) && ((bn = bestNamed) == null || score < bn.getDistM() + (bn.getAngleDeg() * 0.5d))) {
                                bestNamed = new Match(s, d, a2, p.getAbbr());
                            }
                        }
                    }
                }
            }
        }
        return bestNamed == null ? best : bestNamed;
    }

    private final double angleDiff(double a, double b) {
        double d = a - b;
        while (d > 3.141592653589793d) {
            d -= 6.283185307179586d;
        }
        while (d < -3.141592653589793d) {
            d += 6.283185307179586d;
        }
        return d;
    }
}
