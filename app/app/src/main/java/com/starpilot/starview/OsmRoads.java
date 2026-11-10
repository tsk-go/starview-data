package com.starpilot.starview;

import android.content.Context;
import android.util.Log;
import com.starpilot.starview.MapdMaps;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Pair;
import kotlin.Triple;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.ArrayDeque;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.ranges.IntProgression;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: OsmRoads.kt */
public final class OsmRoads {
    private static final double KY = 110540.0d;
    private static final String TAG = "StarView/Osm";
    public static final double TILE = 0.01d;
    private static File dir;
    private static double kx;
    private static long lastEnsureMs;
    private static volatile Fix lastFix;
    private static volatile double lon0;
    private static volatile long rtkFixNs;
    public static final OsmRoads INSTANCE = new OsmRoads();
    private static final List<String> URLS = CollectionsKt.listOf((Object[]) new String[]{"https://overpass-api.de/api/interpreter", "https://overpass.private.coffee/api/interpreter", "https://overpass.kumi.systems/api/interpreter"});
    private static final Set<String> KINDS = SetsKt.setOf((Object[]) new String[]{"motorway", "trunk", "primary", "secondary", "tertiary", "unclassified", "residential", "living_street", "service", "motorway_link", "trunk_link", "primary_link", "secondary_link", "tertiary_link"});
    private static volatile double lat0 = Double.NaN;
    private static final ConcurrentHashMap<Long, Way> ways = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, List<Way>> boxWays = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, Integer> boxState = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, Integer> tiles = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, Long> failedAt = new ConcurrentHashMap<>();
    private static final ConcurrentLinkedQueue<Fix> fixes = new ConcurrentLinkedQueue<>();
    private static volatile String tileStatus = "no GPS yet";
    private static volatile String gpsSrc = "";
    private static volatile String matchStatus = "";

    /* renamed from: http$delegate, reason: from kotlin metadata */
    private static final Lazy http = LazyKt.lazy(new Function0() { // from class: com.starpilot.starview.OsmRoads$$ExternalSyntheticLambda2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            OkHttpClient http_delegate$lambda$0;
            http_delegate$lambda$0 = OsmRoads.http_delegate$lambda$0();
            return http_delegate$lambda$0;
        }
    });
    private static final ExecutorService loader = Executors.newSingleThreadExecutor(new ThreadFactory() { // from class: com.starpilot.starview.OsmRoads$$ExternalSyntheticLambda3
        @Override // java.util.concurrent.ThreadFactory
        public final Thread newThread(Runnable runnable) {
            Thread loader$lambda$2;
            loader$lambda$2 = OsmRoads.loader$lambda$2(runnable);
            return loader$lambda$2;
        }
    });
    private static volatile boolean enabled = true;
    private static final Map<String, String> ABBR = MapsKt.mapOf(TuplesKt.to("st", "street"), TuplesKt.to("ave", "avenue"), TuplesKt.to("av", "avenue"), TuplesKt.to("rd", "road"), TuplesKt.to("blvd", "boulevard"), TuplesKt.to("dr", "drive"), TuplesKt.to("ln", "lane"), TuplesKt.to("pl", "place"), TuplesKt.to("ct", "court"), TuplesKt.to("hwy", "highway"), TuplesKt.to("pkwy", "parkway"), TuplesKt.to("expy", "expressway"), TuplesKt.to("tpke", "turnpike"), TuplesKt.to("e", "east"), TuplesKt.to("w", "west"), TuplesKt.to("n", "north"), TuplesKt.to("s", "south"), TuplesKt.to("ter", "terrace"), TuplesKt.to("sq", "square"), TuplesKt.to("rte", "route"), TuplesKt.to("rt", "route"));
    private static final Set<String> DIRS = SetsKt.setOf((Object[]) new String[]{"east", "west", "north", "south", "eastbound", "westbound", "northbound", "southbound"});

    private OsmRoads() {
    }

    /* compiled from: OsmRoads.kt */
    public static final class Way {
        private final double[] e;
        private final long id;
        private final String kind;
        private final int lanes;
        private final double maxE;
        private final double maxN;
        private final double minE;
        private final double minN;
        private final double[] n;
        private final String name;
        private final int oneway;
        private final float width;

        public Way(long id, String name, String kind, int oneway, float width, double[] e, double[] n, int lanes) {
            Intrinsics.checkNotNullParameter(name, "name");
            Intrinsics.checkNotNullParameter(kind, "kind");
            Intrinsics.checkNotNullParameter(e, "e");
            Intrinsics.checkNotNullParameter(n, "n");
            this.id = id;
            this.name = name;
            this.kind = kind;
            this.oneway = oneway;
            this.width = width;
            this.e = e;
            this.n = n;
            this.lanes = lanes;
            this.minE = ArraysKt.minOrThrow(this.e);
            this.maxE = ArraysKt.maxOrThrow(this.e);
            this.minN = ArraysKt.minOrThrow(this.n);
            this.maxN = ArraysKt.maxOrThrow(this.n);
        }

        public /* synthetic */ Way(long j, String str, String str2, int i, float f, double[] dArr, double[] dArr2, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
            this(j, str, str2, i, f, dArr, dArr2, (i3 & 128) != 0 ? 0 : i2);
        }

        public final double[] getE() {
            return this.e;
        }

        public final long getId() {
            return this.id;
        }

        public final String getKind() {
            return this.kind;
        }

        public final int getLanes() {
            return this.lanes;
        }

        public final double[] getN() {
            return this.n;
        }

        public final String getName() {
            return this.name;
        }

        public final int getOneway() {
            return this.oneway;
        }

        public final float getWidth() {
            return this.width;
        }

        public final double getMaxE() {
            return this.maxE;
        }

        public final double getMaxN() {
            return this.maxN;
        }

        public final double getMinE() {
            return this.minE;
        }

        public final double getMinN() {
            return this.minN;
        }

        public final boolean getHighway() {
            return StringsKt.startsWith(this.kind, "motorway", false) || StringsKt.startsWith(this.kind, "trunk", false);
        }

        public final int getLaneCount() {
            if (this.lanes > 0) {
                return this.oneway == 0 ? Math.max(1, this.lanes / 2) : this.lanes;
            }
            if (getHighway()) {
                return Math.max(1, Math.round(this.width / (this.oneway == 0 ? 6.8f : 3.4f)));
            }
            return 1;
        }

        public final double getLanesWidth() {
            if (getHighway()) {
                return this.width;
            }
            return Math.min(this.width, getLaneCount() * (this.oneway == 0 ? 2 : 1) * 3.3d);
        }
    }

    /* compiled from: OsmRoads.kt */
    public static final class Fix {
        private final double ageS;
        private final float bearingAcc;
        private final float bearingDeg;
        private final float hAcc;
        private final double lat;
        private final double lon;
        private final long monoNs;
        private final float speed;
        private final long tNs;

        public Fix(double lat, double lon, float bearingDeg, float speed, float hAcc, long tNs, float bearingAcc, double ageS, long monoNs) {
            this.lat = lat;
            this.lon = lon;
            this.bearingDeg = bearingDeg;
            this.speed = speed;
            this.hAcc = hAcc;
            this.tNs = tNs;
            this.bearingAcc = bearingAcc;
            this.ageS = ageS;
            this.monoNs = monoNs;
        }

        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public /* synthetic */ Fix(double d, double d2, float f, float f2, float f3, long j, float f4, double d3, long j2, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(d, d2, f, f2, f3, j, r12, r13, r15);
            double d4;
            long j3;
            float f5 = (i & 64) != 0 ? Float.NaN : f4;
            if ((i & 128) == 0) {
                d4 = d3;
            } else {
                d4 = Double.NaN;
            }
            if ((i & 256) == 0) {
                j3 = j2;
            } else {
                j3 = 0;
            }
        }

        public final float getBearingAcc() {
            return this.bearingAcc;
        }

        public final float getBearingDeg() {
            return this.bearingDeg;
        }

        public final float getHAcc() {
            return this.hAcc;
        }

        public final double getLat() {
            return this.lat;
        }

        public final double getLon() {
            return this.lon;
        }

        public final float getSpeed() {
            return this.speed;
        }

        public final long getTNs() {
            return this.tNs;
        }

        public final double getAgeS() {
            return this.ageS;
        }

        public final long getMonoNs() {
            return this.monoNs;
        }
    }

    public final double getLat0() {
        return lat0;
    }

    public final double getLon0() {
        return lon0;
    }

    public final ConcurrentLinkedQueue<Fix> getFixes() {
        return fixes;
    }

    public final Fix getLastFix() {
        return lastFix;
    }

    public final String getTileStatus() {
        return tileStatus;
    }

    public final String getGpsSrc() {
        return gpsSrc;
    }

    public final void setGpsSrc(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        gpsSrc = str;
    }

    public final String getMatchStatus() {
        return matchStatus;
    }

    public final void setMatchStatus(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        matchStatus = str;
    }

    private final OkHttpClient getHttp() {
        return (OkHttpClient) http.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final OkHttpClient http_delegate$lambda$0() {
        return new OkHttpClient.Builder().connectTimeout(10L, TimeUnit.SECONDS).readTimeout(40L, TimeUnit.SECONDS).build();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Thread loader$lambda$2(Runnable r) {
        Thread $this$loader_u24lambda_u242_u24lambda_u241 = new Thread(r, "starview-osm");
        $this$loader_u24lambda_u242_u24lambda_u241.setDaemon(true);
        return $this$loader_u24lambda_u242_u24lambda_u241;
    }

    public final void init(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        if (dir == null) {
            File $this$init_u24lambda_u243 = new File(ctx.getFilesDir(), "osm");
            $this$init_u24lambda_u243.mkdirs();
            dir = $this$init_u24lambda_u243;
        }
    }

    public final void setOrigin(double lat, double lon) {
        lon0 = lon;
        kx = Math.cos(Math.toRadians(lat)) * 111320.0d;
        lat0 = lat;
    }

    public final Pair<Double, Double> toLocal(double lat, double lon) {
        return new Pair<>(Double.valueOf((lon - lon0) * kx), Double.valueOf((lat - lat0) * 110540.0d));
    }

    public final boolean getEnabled() {
        return enabled;
    }

    public final void setEnabled(boolean z) {
        enabled = z;
    }

    public static /* synthetic */ void onFix$default(OsmRoads osmRoads, double d, double d2, float f, float f2, float f3, float f4, long j, long j2, boolean z, int i, Object obj) {
        boolean z2;
        float f5 = (i & 32) != 0 ? Float.NaN : f4;
        long j3 = (i & 64) != 0 ? 0L : j;
        long j4 = (i & 128) != 0 ? 0L : j2;
        if ((i & 256) == 0) {
            z2 = z;
        } else {
            z2 = false;
        }
        osmRoads.onFix(d, d2, f, f2, f3, f5, j3, j4, z2);
    }

    public final void onFix(double lat, double lon, float bearingDeg, float speed, float hAcc, float bearingAcc, long fixUnixMs, long monoNs, boolean rtk) {
        if (enabled) {
            long nanoTime = System.nanoTime();
            if (rtk) {
                rtkFixNs = nanoTime;
            } else if (nanoTime - rtkFixNs < 1000000000) {
                return;
            }
            if ((Double.isInfinite(lat) || Double.isNaN(lat)) ? false : true) {
                if (!((Double.isInfinite(lon) || Double.isNaN(lon)) ? false : true)) {
                    return;
                }
                if (lat == 0.0d) {
                    if (lon == 0.0d) {
                        return;
                    }
                }
                if (Double.isNaN(lat0)) {
                    setOrigin(lat, lon);
                }
                double age = fixUnixMs > 0 ? (System.currentTimeMillis() - fixUnixMs) / 1000.0d : Double.NaN;
                Fix f = new Fix(lat, lon, bearingDeg, speed, hAcc, System.nanoTime(), bearingAcc, -0.5d <= age && age <= 3.0d ? RangesKt.coerceAtLeast(age, 0.0d) : Double.NaN, monoNs);
                lastFix = f;
                fixes.add(f);
                while (fixes.size() > 50) {
                    fixes.poll();
                }
                long now = System.currentTimeMillis();
                if (now - lastEnsureMs < 2000) {
                    return;
                }
                lastEnsureMs = now;
                try {
                    ensureTiles(lat, lon);
                } catch (Exception e) {
                    Log.e("StarView/Osm", "tiles", e);
                    tileStatus = StringsKt.take("map error: " + e, 80);
                }
            }
        }
    }

    private final String key(int ty, int tx) {
        return ty + "_" + tx;
    }

    private final void ensureBoxes(double lat, double lon) {
        double dLat;
        double dLat2 = 0.002713949701465533d;
        double dLon = 300.0d / kx;
        int i = 3;
        char c = 1;
        char c2 = 2;
        double[] dArr = {lat - 0.002713949701465533d, lat, lat + 0.002713949701465533d};
        int i2 = 0;
        while (i2 < i) {
            double la = dArr[i2];
            double[] dArr2 = new double[i];
            dArr2[0] = lon - dLon;
            dArr2[c] = lon;
            dArr2[c2] = lon + dLon;
            int i3 = 0;
            while (i3 < i) {
                double lo = dArr2[i3];
                Pair<Boolean, File> boxFileFor = MapdMaps.INSTANCE.boxFileFor(la, lo);
                boolean have = boxFileFor.component1().booleanValue();
                final File f = boxFileFor.component2();
                if (!have) {
                    dLat = dLat2;
                } else if (f == null) {
                    dLat = dLat2;
                } else {
                    final String k = f.getName();
                    if (boxState.containsKey(k)) {
                        dLat = dLat2;
                    } else {
                        dLat = dLat2;
                        boxState.put(k, 0);
                        loader.execute(new Runnable() { // from class: com.starpilot.starview.OsmRoads$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                OsmRoads.ensureBoxes$lambda$6(f, k);
                            }
                        });
                    }
                }
                i3++;
                dLat2 = dLat;
                i = 3;
            }
            i2++;
            c = 1;
            c2 = 2;
            i = 3;
        }
        Set<String> keySet = boxWays.keySet();
        Intrinsics.checkNotNullExpressionValue(keySet, "<get-keys>(...)");
        for (String k2 : CollectionsKt.toList(keySet)) {
            List p = StringsKt.split((CharSequence) k2, new char[]{'_'}, false, 0);
            double d = 2;
            double cla = (Double.parseDouble((String) p.get(0)) + Double.parseDouble((String) p.get(2))) / d;
            double clo = (Double.parseDouble((String) p.get(1)) + Double.parseDouble((String) p.get(3))) / d;
            if (Math.abs(cla - lat) > 0.5d || Math.abs(clo - lon) > 0.5d) {
                boxWays.remove(k2);
                boxState.remove(k2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void ensureBoxes$lambda$6(File $f, String $k) {
        try {
            Iterable $this$map$iv = MapdMaps.INSTANCE.readBox(FilesKt.readBytes($f));
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            Iterable $this$mapTo$iv$iv = $this$map$iv;
            int $i$f$mapTo = 0;
            for (Object item$iv$iv : $this$mapTo$iv$iv) {
                MapdMaps.W w = (MapdMaps.W) item$iv$iv;
                double[] e = new double[w.getLat().length];
                double[] n = new double[w.getLat().length];
                int j = 0;
                int length = w.getLat().length;
                while (j < length) {
                    Iterable $this$mapTo$iv$iv2 = $this$mapTo$iv$iv;
                    Pair<Double, Double> local = INSTANCE.toLocal(w.getLat()[j], w.getLon()[j]);
                    double x = local.component1().doubleValue();
                    double y = local.component2().doubleValue();
                    e[j] = x;
                    n[j] = y;
                    j++;
                    $this$mapTo$iv$iv = $this$mapTo$iv$iv2;
                    $i$f$mapTo = $i$f$mapTo;
                }
                Iterable $this$mapTo$iv$iv3 = $this$mapTo$iv$iv;
                int $i$f$mapTo2 = $i$f$mapTo;
                String kind = Intrinsics.areEqual(w.getKind(), "unknown") ? "residential" : w.getKind();
                int ow = w.getOneway() ? 1 : 0;
                destination$iv$iv.add(new Way(w.getId(), w.getName(), kind, ow, INSTANCE.widthOf(kind, ow, w.getLanes(), null, ""), e, n, w.getLanes()));
                $this$mapTo$iv$iv = $this$mapTo$iv$iv3;
                $i$f$mapTo = $i$f$mapTo2;
            }
            List list = (List) destination$iv$iv;
            boxWays.put($k, list);
            boxState.put($k, 1);
            Collection<List<Way>> values = boxWays.values();
            Intrinsics.checkNotNullExpressionValue(values, "<get-values>(...)");
            Iterator it = values.iterator();
            int i = 0;
            while (it.hasNext()) {
                List it2 = (List) it.next();
                i += it2.size();
            }
            tileStatus = "offline map: " + i + " streets";
        } catch (Exception e2) {
            Log.e("StarView/Osm", "box " + $k, e2);
            String message = e2.getMessage();
            tileStatus = "offline map error: " + (message != null ? StringsKt.take(message, 50) : null);
        }
    }

    private final void ensureTiles(double lat, double lon) {
        double fy;
        long j;
        double fx;
        int i;
        ensureBoxes(lat, lon);
        long j2 = 4576918229304087675L;
        final int ty = (int) Math.floor(lat / 0.01d);
        final int tx = (int) Math.floor(lon / 0.01d);
        double fy2 = (lat / 0.01d) - ty;
        double fx2 = (lon / 0.01d) - tx;
        double mx = 300.0d / (kx * 0.01d);
        int dy = -1;
        while (true) {
            int i2 = 2;
            if (dy >= 2) {
                return;
            }
            int dx = -1;
            while (dx < i2) {
                if (dy == -1 && fy2 > 0.2713949701465533d) {
                    j = j2;
                    fy = fy2;
                    fx = fx2;
                    i = i2;
                } else if ((dy != 1 || fy2 >= 1 - 0.2713949701465533d) && ((dx != -1 || fx2 <= mx) && (dx != 1 || fx2 >= 1 - mx))) {
                    String k = key(ty + dy, tx + dx);
                    fy = fy2;
                    double fy3 = ty + dy;
                    j = 4576918229304087675L;
                    fx = fx2;
                    double fx3 = tx + dx;
                    if (MapdMaps.INSTANCE.boxFileFor((fy3 + 0.5d) * 0.01d, (fx3 + 0.5d) * 0.01d).getFirst().booleanValue()) {
                        i = 2;
                    } else {
                        Integer st = tiles.get(k);
                        if (st != null && st.intValue() == 0) {
                            i = 2;
                        } else if (st != null && st.intValue() == 1) {
                            i = 2;
                        } else {
                            if (st == null) {
                                i = 2;
                            } else {
                                i = 2;
                                if (st.intValue() == 2) {
                                    long currentTimeMillis = System.currentTimeMillis();
                                    Long l = failedAt.get(k);
                                    if (currentTimeMillis - (l != null ? l.longValue() : 0L) < 30000) {
                                    }
                                }
                            }
                            tiles.put(k, 0);
                            loader.execute(new Runnable() { // from class: com.starpilot.starview.OsmRoads$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    OsmRoads.ensureTiles$lambda$7(ty, dy, tx, dx);
                                }
                            });
                        }
                    }
                } else {
                    fy = fy2;
                    fx = fx2;
                    i = 2;
                    j = 4576918229304087675L;
                }
                dx++;
                i2 = i;
                j2 = j;
                fy2 = fy;
                fx2 = fx;
            }
            dy++;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void ensureTiles$lambda$7(int $ty, int $dy, int $tx, int $dx) {
        INSTANCE.load($ty + $dy, $tx + $dx);
    }

    /* JADX WARN: Can't wrap try/catch for region: R(7:(10:58|59|60|61|62|63|64|65|66|(7:68|69|70|71|72|73|(2:76|77)(1:75)))(1:108)|69|70|71|72|73|(0)(0)) */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x01e0, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x01ee, code lost:
    
        r1 = r0.getMessage();
     */
    /* JADX WARN: Code restructure failed: missing block: B:85:0x01f2, code lost:
    
        if (r1 == null) goto L59;
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x01f4, code lost:
    
        r1 = r0.toString();
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x01f8, code lost:
    
        r5 = r1;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:31:0x028b A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0281 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:75:0x01fc A[LOOP:1: B:49:0x00fa->B:75:0x01fc, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:76:0x01fb A[SYNTHETIC] */
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 8 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final void load(int ty, int tx) {
        int i;
        String str;
        String str2;
        Iterator<String> it;
        Response execute;
        Throwable th;
        Response response;
        String string;
        String str3;
        int i2;
        boolean z;
        String key = key(ty, tx);
        try {
            File file = dir;
            if (file == null) {
                return;
            }
            File file2 = new File(file, key + ".json");
            try {
                if (file2.isFile() && file2.length() > 20) {
                    str = FilesKt.readText(file2, kotlin.text.Charsets.UTF_8);
                    i = 0;
                } else {
                    tileStatus = "downloading streets…";
                    double d = ty * 0.01d;
                    double d2 = tx * 0.01d;
                    try {
                        String format = String.format(Locale.US, "%.4f", Arrays.copyOf(new Object[]{Double.valueOf(d)}, 1));
                        Intrinsics.checkNotNullExpressionValue(format, "format(...)");
                        String format2 = String.format(Locale.US, "%.4f", Arrays.copyOf(new Object[]{Double.valueOf(d2)}, 1));
                        Intrinsics.checkNotNullExpressionValue(format2, "format(...)");
                        String format3 = String.format(Locale.US, "%.4f", Arrays.copyOf(new Object[]{Double.valueOf(d + 0.01d)}, 1));
                        Intrinsics.checkNotNullExpressionValue(format3, "format(...)");
                        String format4 = String.format(Locale.US, "%.4f", Arrays.copyOf(new Object[]{Double.valueOf(d2 + 0.01d)}, 1));
                        Intrinsics.checkNotNullExpressionValue(format4, "format(...)");
                        String str4 = "[out:json][timeout:30];way[\"highway\"][\"area\"!=\"yes\"](" + format + "," + format2 + "," + format3 + "," + format4 + ");out tags geom;";
                        Iterator<String> it2 = URLS.iterator();
                        String str5 = "";
                        String str6 = null;
                        while (true) {
                            if (!it2.hasNext()) {
                                i = 0;
                                break;
                            }
                            try {
                                execute = getHttp().newCall(new Request.Builder().url(it2.next()).header("User-Agent", "StarView/2.25 (openpilot tablet display)").post(new FormBody.Builder(null, 1, null == true ? 1 : 0).add("data", str4).build()).build()).execute();
                                try {
                                    response = execute;
                                    ResponseBody body = response.body();
                                    Intrinsics.checkNotNull(body);
                                    string = body.string();
                                } catch (Throwable th2) {
                                    str2 = str4;
                                    it = it2;
                                    i = 0;
                                    th = th2;
                                }
                            } catch (Exception e) {
                                e = e;
                                str2 = str4;
                                it = it2;
                                i = 0;
                            }
                            try {
                                if (response.isSuccessful()) {
                                    str2 = str4;
                                    try {
                                        str3 = str6;
                                        it = it2;
                                        String str7 = str5;
                                        i = 0;
                                        try {
                                            if (StringsKt.startsWith(StringsKt.trimStart((CharSequence) string).toString(), "{", false)) {
                                                str6 = string;
                                                str5 = str7;
                                                Unit unit = Unit.INSTANCE;
                                                CloseableKt.closeFinally(execute, null);
                                                if (str6 != null) {
                                                    break;
                                                }
                                                str4 = str2;
                                                it2 = it;
                                            }
                                        } catch (Throwable th3) {
                                            th = th3;
                                            str6 = str3;
                                            try {
                                                throw th;
                                            } catch (Throwable th4) {
                                                CloseableKt.closeFinally(execute, th);
                                                throw th4;
                                            }
                                        }
                                    } catch (Throwable th5) {
                                        it = it2;
                                        i = 0;
                                        th = th5;
                                    }
                                } else {
                                    str2 = str4;
                                    str3 = str6;
                                    it = it2;
                                    i = 0;
                                }
                                Unit unit2 = Unit.INSTANCE;
                                CloseableKt.closeFinally(execute, null);
                                if (str6 != null) {
                                }
                            } catch (Throwable th6) {
                                th = th6;
                                throw th;
                            }
                            str5 = "HTTP " + response.code();
                            str6 = str3;
                        }
                        if (str6 == null) {
                            throw new IOException(str5);
                        }
                        str = str6;
                        File file3 = new File(file2.getPath() + ".tmp");
                        FilesKt.writeText(file3, str, kotlin.text.Charsets.UTF_8);
                        file3.renameTo(file2);
                    } catch (Exception e2) {
                        e = e2;
                        Log.w("StarView/Osm", "tile " + key + ": " + e);
                        tiles.put(key, 2);
                        failedAt.put(key, Long.valueOf(System.currentTimeMillis()));
                        String message = e.getMessage();
                        tileStatus = "street download failed: " + (message != null ? StringsKt.take(message, 40) : null) + " (retrying)";
                        return;
                    }
                }
                int parse$app_debug = parse$app_debug(str);
                tiles.put(key, 1);
                Collection<Integer> values = tiles.values();
                Intrinsics.checkNotNullExpressionValue(values, "<get-values>(...)");
                Collection<Integer> collection = values;
                if (collection.isEmpty()) {
                    i2 = i;
                } else {
                    i2 = 0;
                    for (Integer num : collection) {
                        if (num != null && num.intValue() == 1) {
                            z = true;
                            if (z) {
                                i2++;
                                if (i2 < 0) {
                                    CollectionsKt.throwCountOverflow();
                                }
                            }
                        }
                        z = false;
                        if (z) {
                        }
                    }
                }
                tileStatus = i2 + " tiles, " + ways.size() + " streets" + (parse$app_debug == 0 ? " (tile " + key + " empty)" : "");
            } catch (Exception e3) {
                e = e3;
            }
        } catch (Exception e4) {
            e = e4;
        }
    }

    /* VF: from Vineflower (jadx: Code restructure failed;Removed duplicated region;incorrect) */
    public final int parse$app_debug(String var1) {
        int var22 = 0; // declarations Vineflower left out
        Intrinsics.checkNotNullParameter(var1, "text");
        JSONArray var21 = new JSONObject(var1).optJSONArray("elements");
        if (var21 == null) {
            return 0;
        } else {
            int var9 = 0;
            int var8 = 0;

            for (int var7 = var21.length(); var8 < var7; var8++) {
                JSONObject var15 = var21.optJSONObject(var8);
                if (var15 != null && Intrinsics.areEqual(var15.optString("type"), "way")) {
                    long var12 = var15.optLong("id");
                    if (!ways.containsKey(var12)) {
                        JSONObject var17 = var15.optJSONObject("tags");
                        if (var17 != null) {
                            String var16 = var17.optString("highway");
                            String var14 = var16;
                            if (Intrinsics.areEqual(var16, "footway")) {
                                var14 = var16;
                                if (Intrinsics.areEqual(var17.optString("footway"), "sidewalk")) {
                                    var14 = "sidewalk";
                                }
                            }

                            if (KINDS.contains(var14) || Intrinsics.areEqual(var14, "sidewalk")) {
                                var16 = var17.optString("service");
                                if (!Intrinsics.areEqual(var14, "service")
                                    || !SetsKt.setOf(new String[]{"driveway", "parking_aisle", "drive-through", "emergency_access"}).contains(var16)) {
                                    JSONArray var19 = var15.optJSONArray("geometry");
                                    if (var19 != null && var19.length() >= 2) {
                                        double[] var28 = new double[var19.length()];
                                        double[] var18 = new double[var19.length()];
                                        int var11 = 0;

                                        for (int var10 = var19.length(); var11 < var10; var11++) {
                                            JSONObject var20 = var19.getJSONObject(var11);
                                            Pair var32 = this.toLocal(var20.getDouble("lat"), var20.getDouble("lon"));
                                            double var2 = ((Number)var32.component1()).doubleValue();
                                            double var4 = ((Number)var32.component2()).doubleValue();
                                            var28[var11] = var2;
                                            var18[var11] = var4;
                                        }

                                        label100: {
                                            label99: {
                                                label98: {
                                                    String var25 = var17.optString("oneway");
                                                    if (var25 != null) {
                                                        switch (var25.hashCode()) {
                                                            case 49:
                                                                if (var25.equals("1")) {
                                                                    break label98;
                                                                }
                                                                break;
                                                            case 1444:
                                                                if (var25.equals("-1")) {
                                                                    break label99;
                                                                }
                                                                break;
                                                            case 119527:
                                                                if (var25.equals("yes")) {
                                                                    break label98;
                                                                }
                                                                break;
                                                            case 3569038:
                                                                if (var25.equals("true")) {
                                                                    break label98;
                                                                }
                                                                break;
                                                            case 1099846370:
                                                                if (var25.equals("reverse")) {
                                                                    break label99;
                                                                }
                                                        }
                                                    }

                                                    if (!Intrinsics.areEqual(var14, "motorway") && !Intrinsics.areEqual(var17.optString("junction"), "roundabout")) {
                                                        var22 = 0;
                                                        break label100;
                                                    }
                                                }

                                                var22 = 1;
                                                break label100;
                                            }

                                            var22 = -1;
                                        }

                                        Map var31 = ways;
                                        String var26 = var17.optString("name", var17.optString("ref", ""));
                                        Intrinsics.checkNotNullExpressionValue(var26, "optString(...)");
                                        Intrinsics.checkNotNull(var14);
                                        float var6 = this.widthOf(var14, var22, var17);
                                        String var29 = var17.optString("lanes");
                                        Intrinsics.checkNotNullExpressionValue(var29, "optString(...)");
                                        Integer var30 = StringsKt.toIntOrNull(var29);
                                        if (var30 != null) {
                                            var11 = var30;
                                        } else {
                                            var11 = 0;
                                        }

                                        var31.put(var12, new OsmRoads.Way(var12, var26, var14, var22, var6, var28, var18, var11));
                                        var9++;
                                        var7 = var7;
                                    }
                                }
                            }
                        }
                    }
                }
            }

            return var9;
        }
    }

    public final float widthOf(String kind, int oneway, JSONObject tags) {
        Intrinsics.checkNotNullParameter(kind, "kind");
        Intrinsics.checkNotNullParameter(tags, "tags");
        String optString = tags.optString("lanes");
        Intrinsics.checkNotNullExpressionValue(optString, "optString(...)");
        Integer intOrNull = StringsKt.toIntOrNull(optString);
        int intValue = intOrNull != null ? intOrNull.intValue() : 0;
        String optString2 = tags.optString("width");
        Intrinsics.checkNotNullExpressionValue(optString2, "optString(...)");
        Float floatOrNull = StringsKt.toFloatOrNull(StringsKt.trim((CharSequence) StringsKt.replace(optString2, "m", "", false)).toString());
        String optString3 = tags.optString("parking:both", tags.optString("parking:lane:both", ""));
        Intrinsics.checkNotNullExpressionValue(optString3, "optString(...)");
        return widthOf(kind, oneway, intValue, floatOrNull, optString3);
    }

    /* VF: from Vineflower (jadx: Code restructure failed;Removed duplicated region;incorrect) */
    public final float widthOf(String var1, int var2, int var3, Float var4, String var5) {
        Intrinsics.checkNotNullParameter(var1, "kind");
        Intrinsics.checkNotNullParameter(var5, "parkingTag");
        boolean var8 = true;
        if (var4 != null) {
            float var6 = var4.floatValue();
            boolean var7;
            if (2.0F <= var6 && var6 <= 40.0F) {
                var7 = true;
            } else {
                var7 = false;
            }

            if (var7) {
                return var6;
            }
        }

        byte var13;
        label109: {
            byte var9 = 2;
            boolean var10 = SetsKt.setOf(new String[]{"residential", "tertiary", "secondary", "primary", "unclassified", "living_street"}).contains(var1);
            if (!Intrinsics.areEqual(var5, "no")) {
                boolean var12;
                if (var5.length() > 0) {
                    var12 = true;
                } else {
                    var12 = false;
                }

                if (var12 || var10) {
                    var13 = var9;
                    break label109;
                }
            }

            var13 = 0;
        }

        if (1 > var3 || var3 >= 11) {
            var8 = false;
        }

        if (var8) {
            return var3 * 3.2F + var13 * 2.3F;
        } else {
            switch (var1.hashCode()) {
                case -2014090663:
                    if (var1.equals("living_street")) {
                        return 7.0F;
                    }

                    return 6.5F;
                case -1174796206:
                    if (var1.equals("tertiary")) {
                        return 12.0F;
                    }

                    return 6.5F;
                case -817598092:
                    if (var1.equals("secondary")) {
                        return 13.0F;
                    }

                    return 6.5F;
                case -314765822:
                    if (var1.equals("primary")) {
                        return 15.0F;
                    }

                    return 6.5F;
                case -151535014:
                    if (var1.equals("motorway")) {
                        return 11.0F;
                    }

                    return 6.5F;
                case 110640564:
                    if (var1.equals("trunk")) {
                        return 14.0F;
                    }

                    return 6.5F;
                case 218164128:
                    if (var1.equals("sidewalk")) {
                        return 0.0F;
                    }

                    return 6.5F;
                case 1098352388:
                    if (!var1.equals("residential")) {
                        return 6.5F;
                    }
                    break;
                case 1840151916:
                    if (!var1.equals("unclassified")) {
                        return 6.5F;
                    }
                    break;
                case 1984153269:
                    if (var1.equals("service")) {
                        return 5.0F;
                    }

                    return 6.5F;
                default:
                    return 6.5F;
            }

            float var11;
            if (var2 != 0) {
                var11 = 9.0F;
            } else {
                var11 = 10.0F;
            }

            return var11;
        }
    }

    public final Pair<Double, Double> toLatLon(double e, double n) {
        return new Pair<>(Double.valueOf(lat0 + (n / 110540.0d)), Double.valueOf(lon0 + (e / kx)));
    }

    /* VF: from Vineflower (jadx: Code restructure failed;incorrect) */
    public final Set<String> nameTokens(String var1) {
        boolean var16 = false; // declarations Vineflower left out
        Intrinsics.checkNotNullParameter(var1, "name");
        var1 = var1.toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(var1, "toLowerCase(...)");
        CharSequence var8 = var1;
        CharSequence var9 = new Regex("[^a-z0-9 ]").replace(var8, " ");
        boolean var3 = true;
        Iterable var4 = StringsKt.split(var9, new char[]{' '}, false, 0);
        var var10 = new ArrayList();

        for (Object var5 : var4) {
            if (!StringsKt.isBlank((String & CharSequence)var5)) {
                var10.add(var5);
            }
        }

        List var11 = var10;
        Iterable var12 = var11;
        var var20 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var12, 10));

        for (String var13 : ((String) var12)) {
            String var18 = ABBR.get(var13);
            if (var18 != null) {
                var13 = var18;
            }

            var20.add(var13);
        }

        List var14 = var20;
        Set var15 = CollectionsKt.toSet(var14);
        Set var19 = SetsKt.minus(var15, DIRS);
        if (var19.size() < 2) {
            if (var19.size() != 1) {
                return var15;
            }

            CharSequence var21 = ((CharSequence) CollectionsKt.first(var19));
            int var2 = 0;

            while (true) {
                if (var2 >= var21.length()) {
                    var16 = false;
                    break;
                }

                if (Character.isDigit(var21.charAt(var2))) {
                    var16 = var3;
                    break;
                }

                var2++;
            }

            if (!var16) {
                return var15;
            }
        }

        return var19;
    }

    public final boolean sameStreet(String a, String b) {
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(b, "b");
        Set x = nameTokens(a);
        Set y = nameTokens(b);
        return !x.isEmpty() && (Intrinsics.areEqual(x, y) || ((x.size() >= 2 && y.containsAll(x)) || (y.size() >= 2 && x.containsAll(y))));
    }

    public final List<Way> near(double e, double n, double r) {
        ArrayList out = new ArrayList();
        HashSet seen = new HashSet();
        for (Way way : ways.values()) {
            Intrinsics.checkNotNullExpressionValue(way, "next(...)");
            Way w = way;
            near$take(e, r, n, seen, out, w);
        }
        for (List list : boxWays.values()) {
            Intrinsics.checkNotNullExpressionValue(list, "next(...)");
            List l = list;
            for (Way w2 : ((Way) l)) {
                near$take(e, r, n, seen, out, w2);
            }
        }
        return out;
    }

    private static final void near$take(double $e, double $r, double $n, HashSet<Long> hashSet, ArrayList<Way> arrayList, Way it) {
        if (it.getMaxE() < $e - $r || it.getMinE() > $e + $r || it.getMaxN() < $n - $r || it.getMinN() > $n + $r || !hashSet.add(Long.valueOf(it.getId()))) {
            return;
        }
        arrayList.add(it);
    }

    public final Pair<Way, Double> streetHeading(double e, double n, Double hintYaw) {
        double dir2;
        Way best = null;
        double bd = 15.0d;
        double d = 0.0d;
        for (Way w : near(e, n, 20.0d)) {
            if (!Intrinsics.areEqual(w.getKind(), "sidewalk")) {
                int i = 0;
                int length = w.getE().length - 1;
                while (i < length) {
                    double ax = w.getE()[i];
                    double ay = w.getN()[i];
                    double dx = w.getE()[i + 1] - ax;
                    int i2 = length;
                    double dy = w.getN()[i + 1] - ay;
                    double len2 = (dx * dx) + (dy * dy);
                    if (len2 < 0.01d) {
                        dir2 = d;
                    } else {
                        double t = RangesKt.coerceIn((((e - ax) * dx) + ((n - ay) * dy)) / len2, 0.0d, 1.0d);
                        dir2 = d;
                        double dir3 = e - (ax + (t * dx));
                        double d2 = Math.hypot(dir3, n - (ay + (t * dy)));
                        if (d2 < bd) {
                            bd = d2;
                            best = w;
                            d = Math.atan2(dy, dx) + (w.getOneway() < 0 ? 3.141592653589793d : 0.0d);
                            i++;
                            length = i2;
                        }
                    }
                    d = dir2;
                    i++;
                    length = i2;
                }
            }
        }
        if (best == null) {
            return null;
        }
        Way w2 = best;
        if (w2.getOneway() == 0 && hintYaw != null && Math.abs(Math.IEEEremainder(d - hintYaw.doubleValue(), 6.283185307179586d)) > 1.5707963267948966d) {
            d += 3.141592653589793d;
        }
        return new Pair<>(w2, Double.valueOf(d));
    }

    /* compiled from: OsmRoads.kt */
    public static final class Match {
        private final double dir;
        private final double dist;
        private final double signedLeft;
        private final Way way;

        public Match(Way way, double dist, double signedLeft, double dir) {
            Intrinsics.checkNotNullParameter(way, "way");
            this.way = way;
            this.dist = dist;
            this.signedLeft = signedLeft;
            this.dir = dir;
        }

        public final double getDir() {
            return this.dir;
        }

        public final double getDist() {
            return this.dist;
        }

        public final double getSignedLeft() {
            return this.signedLeft;
        }

        public final Way getWay() {
            return this.way;
        }

        public final double rightOf(double yaw) {
            return Math.cos(this.dir - yaw) >= 0.0d ? -this.signedLeft : this.signedLeft;
        }
    }

    public final Match matchSeg(double e, double n, double yaw) {
        int i;
        int i2;
        Match best;
        Match best2 = null;
        double bs = Double.MAX_VALUE;
        for (Way w : near(e, n, 30.0d)) {
            if (!Intrinsics.areEqual(w.getKind(), "sidewalk")) {
                int i3 = 0;
                int length = w.getE().length - 1;
                while (i3 < length) {
                    double ax = w.getE()[i3];
                    double ay = w.getN()[i3];
                    double dx = w.getE()[i3 + 1] - ax;
                    double dy = w.getN()[i3 + 1] - ay;
                    double len2 = (dx * dx) + (dy * dy);
                    if (len2 < 0.01d) {
                        i = i3;
                        i2 = length;
                        best = best2;
                    } else {
                        double t = RangesKt.coerceIn((((e - ax) * dx) + ((n - ay) * dy)) / len2, 0.0d, 1.0d);
                        best = best2;
                        double d = Math.hypot(e - ((t * dx) + ax), n - (ay + (t * dy)));
                        if (d > 25.0d) {
                            i = i3;
                            i2 = length;
                        } else {
                            i = i3;
                            i2 = length;
                            double dir2 = Math.atan2(dy, dx) + (w.getOneway() < 0 ? 3.141592653589793d : 0.0d);
                            double dh = Math.abs(w.getOneway() == 0 ? Math.IEEEremainder(dir2 - yaw, 3.141592653589793d) : Math.IEEEremainder(dir2 - yaw, 6.283185307179586d));
                            double score = d + (15.0d * dh);
                            if (score < bs) {
                                bs = score;
                                best2 = new Match(w, d, (((n - ay) * dx) - ((e - ax) * dy)) / Math.sqrt(len2), Math.atan2(dy, dx));
                                i3 = i + 1;
                                length = i2;
                            }
                        }
                    }
                    best2 = best;
                    i3 = i + 1;
                    length = i2;
                }
            }
        }
        return best2;
    }

    /* VF: from Vineflower (jadx: Code restructure failed;incorrect) */
    public final List<Pair<Double, String>> crossStreetsAhead(double var1, double var3, double var5, double var7) {
        OsmRoads.Match var32 = this.matchSeg(var1, var3, var5);
        if (var32 != null) {
            int var23;
            if (var32.getDist() < 15.0) {
                var23 = 1;
            } else {
                var23 = 0;
            }

            if (!var23) {
                var32 = null;
            }

            if (var32 != null) {
                ArrayList var38 = new ArrayList();
                ArrayList var39 = new ArrayList();
                HashSet var37 = new HashSet();
                OsmRoads.Way var35 = var32.getWay();
                var37.add(var35.getId());
                int var24 = 0;
                int var25 = var35.getE().length;
                var23 = 0;
                double var9 = Double.MAX_VALUE;

                while (var24 < var25 - 1) {
                    double var11 = var35.getE()[var24];
                    double var17 = var35.getN()[var24];
                    double var13 = var35.getE()[var24 + 1] - var11;
                    double var15 = var35.getN()[var24 + 1] - var17;
                    double var19 = var13 * var13 + var15 * var15;
                    if (!(var19 < 0.01)) {
                        var19 = RangesKt.coerceIn(((var1 - var11) * var13 + (var3 - var17) * var15) / var19, 0.0, 1.0);
                        var13 = Math.hypot(var1 - (var11 + var19 * var13), var3 - (var17 + var19 * var15));
                        var11 = var9;
                        if (var13 < var9) {
                            var23 = var24;
                            var11 = var13;
                        }
                    } else {
                        var11 = var9;
                    }

                    var24++;
                    var9 = var11;
                }

                int var88;
                if (Math.cos(Math.atan2(var35.getN()[var23 + 1] - var35.getN()[(((var23 != 0)) ? 1 : 0)], var35.getE()[var23 + 1] - var35.getE()[var23]) - var5) >= 0.0) {
                    var88 = 1;
                } else {
                    var88 = 0;
                }

                IntProgression var104;
                if ((var88 != 0)) {
                    var104 = RangesKt.until(var23 + 1, var35.getE().length);
                } else {
                    var104 = RangesKt.downTo(var23, 0);
                }

                var38.add(var1);
                var39.add(var3);
                var5 = 0.0;
                var25 = 0;

                while (true) {
                    double var55;
                    label233: {
                        int var26;
                        int var28;
                        int var29;
                        double var61;
                        label290: {
                            int var27 = var104.getFirst();
                            var28 = var104.getLast();
                            var29 = var104.getStep();
                            if (var29 > 0) {
                                var26 = var27;
                                var61 = var5;
                                var88 = var23;
                                var55 = var9;
                                if (var27 <= var28) {
                                    break label290;
                                }
                            }

                            if (var29 >= 0 || var28 > var27) {
                                var55 = var5;
                                break label233;
                            }

                            var55 = var9;
                            var88 = var23;
                            var61 = var5;
                            var26 = var27;
                        }

                        while (true) {
                            double var69 = var35.getE()[var26];
                            var9 = CollectionsKt.last(var38).doubleValue();
                            double var74 = var35.getN()[var26];
                            double var78 = CollectionsKt.last(var39).doubleValue();
                            var61 += Math.hypot(var69 - var9, var74 - var78);
                            var38.add(var35.getE()[var26]);
                            var39.add(var35.getN()[var26]);
                            var55 = var61;
                            var23 = var88;
                            var9 = var55;
                            if (var61 >= var7) {
                                break;
                            }

                            var55 = var61;
                            var23 = var88;
                            var9 = var55;
                            if (var26 == var28) {
                                break;
                            }

                            var26 += var29;
                            var55 = var55;
                        }
                    }

                    var5 = var55;
                    if (!(var55 >= var7) && var25 < 8 && var38.size() >= 2) {
                        double var79 = CollectionsKt.last(var38).doubleValue();
                        double var75 = CollectionsKt.last(var39).doubleValue();
                        OsmRoads.Way var105 = (OsmRoads.Way)var39.get(var39.size() - 2);
                        Intrinsics.checkNotNullExpressionValue(var105, "get(...)");
                        var55 = ((Number)var105).doubleValue();
                        var105 = ((Way) var38.get(var38.size() - 2));
                        Intrinsics.checkNotNullExpressionValue(var105, "get(...)");
                        double var21 = Math.atan2(var75 - var55, var79 - ((Number)var105).doubleValue());
                        var105 = null;
                        boolean var30 = true;
                        var55 = Double.MAX_VALUE;

                        for (OsmRoads.Way var36 : this.near(var79, var75, 6.0)) {
                            if (!var37.contains(var36.getId())
                                && !Intrinsics.areEqual(var36.getKind(), "sidewalk")
                                && !Intrinsics.areEqual(var36.getKind(), "service")) {
                                var88 = var36.getE().length - 1;

                                for (Pair var41 : CollectionsKt.listOf(new Pair[]{TuplesKt.to(0, true), TuplesKt.to(var88, false)})) {
                                    int var96 = ((Number)var41.component1()).intValue();
                                    boolean var31 = (Boolean)var41.component2();
                                    if (!(Math.hypot(var36.getE()[var96] - var79, var36.getN()[var96] - var75) > 4.0)
                                        && (var31 || var36.getOneway() <= 0)
                                        && (!var31 || var36.getOneway() >= 0)) {
                                        double[] var121 = var36.getE();
                                        double var62;
                                        if (var31) {
                                            var62 = var121[1] - var36.getE()[0];
                                        } else {
                                            var62 = var121[var88 - 1] - var36.getE()[(((var88 != 0)) ? 1 : 0)];
                                        }

                                        double[] var122 = var36.getN();
                                        double var70;
                                        if (var31) {
                                            var70 = var122[1] - var36.getN()[0];
                                        } else {
                                            var70 = var122[var88 - 1] - var36.getN()[(((var88 != 0)) ? 1 : 0)];
                                        }

                                        var70 = Math.abs(Math.IEEEremainder(Math.atan2(var70, var62) - var21, Math.PI * 2));
                                        if (!(var70 > Math.toRadians(35.0))) {
                                            if (this.sameStreet(var36.getName(), var35.getName())) {
                                                var62 = 0.0;
                                            } else {
                                                var62 = 0.3;
                                            }

                                            var62 += var70;
                                            if (var62 < var55) {
                                                var55 = var62;
                                                var30 = var31;
                                                var105 = var36;
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        if (var105 != null) {
                            var35 = var105;
                            var37.add(var105.getId());
                            var25++;
                            double[] var108 = var105.getE();
                            if (var30) {
                                var104 = RangesKt.until(1, var108.length);
                            } else {
                                var104 = RangesKt.downTo(var108.length - 2, 0);
                            }
                            continue;
                        }
                    }
                    break;
                }

                String var112 = "get(...)";
                double[] var119 = new double[var38.size()];
                var23 = 1;

                for (int var91 = var38.size(); var23 < var91; var23++) {
                    var9 = var119[var23 - 1];
                    double var65 = ((Number)var38.get((((var23 != 0)) ? 1 : 0))).doubleValue();
                    Object var109 = var38.get(var23 - 1);
                    Intrinsics.checkNotNullExpressionValue(var109, "get(...)");
                    double var58 = ((Number)var109).doubleValue();
                    var5 = ((Number)var39.get((((var23 != 0)) ? 1 : 0))).doubleValue();
                    var109 = var39.get(var23 - 1);
                    Intrinsics.checkNotNullExpressionValue(var109, "get(...)");
                    var119[(((var23 != 0)) ? 1 : 0)] = var9 + Math.hypot(var65 - var58, var5 - ((Number)var109).doubleValue());
                }

                String var113 = var32.getWay().getName();
                ArrayList var120 = new ArrayList();

                for (OsmRoads.Way var123 : this.near(var1, var3, var7 + 30.0)) {
                    if (!var37.contains(var123.getId()) && !Intrinsics.areEqual(var123.getKind(), "sidewalk") && !Intrinsics.areEqual(var123.getKind(), "service")) {
                        int var85;
                        if (var113.length() > 0) {
                            var85 = 1;
                        } else {
                            var85 = 0;
                        }

                        if (!var85 || !this.sameStreet(var123.getName(), var113)) {
                            var85 = var38.size() - 1;

                            for (int var92 = 0; var92 < var85; var92++) {
                                Object var114 = var38.get(var92);
                                Intrinsics.checkNotNullExpressionValue(var114, var112);
                                var5 = ((Number)var114).doubleValue();
                                var114 = var39.get(var92);
                                Intrinsics.checkNotNullExpressionValue(var114, var112);
                                var3 = ((Number)var114).doubleValue();
                                var114 = var38.get(var92 + 1);
                                Intrinsics.checkNotNullExpressionValue(var114, var112);
                                var1 = ((Number)var114).doubleValue();
                                var114 = var39.get(var92 + 1);
                                Intrinsics.checkNotNullExpressionValue(var114, var112);
                                var9 = ((Number)var114).doubleValue();
                                var1 -= var5;
                                var9 -= var3;
                                double var59 = Math.hypot(var1, var9);
                                if (!(var59 < 0.01)) {
                                    var25 = var123.getE().length - 1;

                                    for (int var97 = 0; var97 < var25; var97++) {
                                        double var66 = var123.getE()[var97];
                                        double var72 = var123.getN()[var97];
                                        double var82 = var123.getE()[var97 + 1] - var66;
                                        double var80 = var123.getN()[var97 + 1] - var72;
                                        double var76 = var1 * var80 - var9 * var82;
                                        if (Math.abs(var76) > 1.0E-9) {
                                            var80 = ((var66 - var5) * var80 - (var72 - var3) * var82) / var76;
                                            var66 = ((var66 - var5) * var9 - (var72 - var3) * var1) / var76;
                                            boolean var99;
                                            if (0.0 <= var80 && var80 <= 1.0) {
                                                var99 = true;
                                            } else {
                                                var99 = false;
                                            }

                                            if (var99) {
                                                if (0.0 <= var66 && var66 <= 1.0) {
                                                    var99 = true;
                                                } else {
                                                    var99 = false;
                                                }

                                                if (var99) {
                                                    crossStreetsAhead$add(var7, var120, var119[var92] + var80 * var59, var123.getName());
                                                }
                                            }
                                        }
                                    }

                                    int var102 = var123.getE().length;
                                    boolean var87 = false;

                                    for (int var98 = 0; var98 < 2; var98++) {
                                        int var103 = new int[]{0, var102 - 1}[var98];
                                        double var68 = ((var123.getE()[var103] - var5) * var1 + (var123.getN()[var103] - var3) * var9) / (var59 * var59);
                                        boolean var101;
                                        if (0.0 <= var68 && var68 <= 1.0) {
                                            var101 = true;
                                        } else {
                                            var101 = var87;
                                        }

                                        if (var101) {
                                            if (Math.hypot(var123.getE()[var103] - (var5 + var68 * var1), var123.getN()[var103] - (var3 + var68 * var9)) < 6.0) {
                                                double var73 = var119[var92];
                                                var114 = var123.getName();
                                                var87 = false;
                                                crossStreetsAhead$add(var7, var120, var68 * var59 + var73, (String)var114);
                                            } else {
                                                var87 = false;
                                            }
                                        }
                                    }

                                    var85 = var85;
                                }
                            }
                        }
                    }
                }

                return CollectionsKt.sortedWith(var120, new Comparator() {
                    @Override
                    public final int compare(Object var1, Object var2) {
                        return ComparisonsKt.compareValues((T)((Double & Comparable)((Pair)var1).getFirst()), (T)((Double & Comparable)((Pair)var2).getFirst()));
                    }
                });
            }
        }

        return CollectionsKt.emptyList();
    }

    private static final void crossStreetsAhead$add(double $maxM, ArrayList<Pair<Double, String>> arrayList, double s, String name) {
        if (s > 2.0d && s <= $maxM) {
            ArrayList<Pair<Double, String>> $this$none$iv = arrayList;
            boolean z = true;
            if (!($this$none$iv instanceof Collection) || !$this$none$iv.isEmpty()) {
                Iterator it = $this$none$iv.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    Object element$iv = it.next();
                    Pair it2 = (Pair) element$iv;
                    Pair it3 = Math.abs(((Number) it2.getFirst()).doubleValue() - s) < 10.0d ? 1 : null;
                    if (it3 != null) {
                        z = false;
                        break;
                    }
                }
            }
            if (z) {
                arrayList.add(TuplesKt.to(Double.valueOf(s), name));
            }
        }
    }

    public final Pair<Double, Double> laneBand(double width, boolean oneway, double dL, double dR) {
        return laneBand(width, oneway, dL, Double.NaN, dR, Double.NaN);
    }

    public final Pair<Double, Double> laneBand(double width, boolean oneway, double lLo, double lHi, double rLo, double rHi) {
        double h = width / 2.0d;
        double lo = oneway ? (-h) + 0.9d : 0.3d;
        double hi = h - 0.9d;
        if (hi < lo) {
            double c = oneway ? 0.0d : h / 2;
            lo = c;
            hi = c;
        }
        double cl = Math.max(!Double.isInfinite(lLo) && !Double.isNaN(lLo) ? (-h) + lLo : Double.NEGATIVE_INFINITY, !Double.isInfinite(rHi) && !Double.isNaN(rHi) ? h - rHi : Double.NEGATIVE_INFINITY);
        double ch = Math.min(!Double.isInfinite(rLo) && !Double.isNaN(rLo) ? h - rLo : Double.POSITIVE_INFINITY, (Double.isInfinite(lHi) || Double.isNaN(lHi)) ? false : true ? (-h) + lHi : Double.POSITIVE_INFINITY);
        if (cl > ch) {
            double c2 = (cl + ch) / 2;
            return new Pair<>(Double.valueOf(c2), Double.valueOf(c2));
        }
        double nlo = Math.max(lo, cl);
        double nhi = Math.min(hi, ch);
        if (nlo <= nhi) {
            return new Pair<>(Double.valueOf(nlo), Double.valueOf(nhi));
        }
        Pair it = new Pair(Double.valueOf(Math.max(cl, -h)), Double.valueOf(Math.min(ch, h)));
        return it.getFirst().doubleValue() <= it.getSecond().doubleValue() ? it : new Pair(Double.valueOf(cl), Double.valueOf(ch));
    }

    public static /* synthetic */ double laneCenter$default(OsmRoads osmRoads, double d, int i, int i2, boolean z, int i3, Object obj) {
        if ((i3 & 8) != 0) {
            z = true;
        }
        return osmRoads.laneCenter(d, i, i2, z);
    }

    public final double laneCenter(double width, int laneCount, int idx, boolean oneway) {
        int n = Math.max(1, laneCount);
        int i = RangesKt.coerceIn(idx, 0, n - 1);
        return oneway ? ((-width) / 2) + (((i + 0.5d) * width) / n) : ((i + 0.5d) * (width / 2)) / n;
    }

    public static /* synthetic */ int laneIndex$default(OsmRoads osmRoads, int i, boolean z, double d, double d2, double d3, double d4, boolean z2, boolean z3, int i2, Object obj) {
        boolean z4;
        boolean z5;
        if ((i2 & 64) == 0) {
            z4 = z2;
        } else {
            z4 = false;
        }
        if ((i2 & 128) == 0) {
            z5 = z3;
        } else {
            z5 = false;
        }
        return osmRoads.laneIndex(i, z, d, d2, d3, d4, z4, z5);
    }

    /* VF: from Vineflower (jadx: Removed duplicated region;incorrect) */
    public final int laneIndex(int var1, boolean var2, double var3, double var5, double var7, double var9, boolean var11, boolean var12) {
        boolean var19 = false;
        if (var1 <= 1) {
            return 0;
        } else if (var2) {
            return 0;
        } else {
            byte var15 = 0;
            int var16 = var1 - 1;
            if (var11) {
                var15 = 1;
            }

            if (var12) {
                var16 = var1 - 2;
            }

            int var17 = var16;
            if (var15 > var16) {
                var17 = var15;
            }

            int var25;
            if (!Double.isInfinite(var5) && !Double.isNaN(var5)) {
                var25 = 1;
            } else {
                var25 = 0;
            }

            double var13;
            label110: {
                var13 = Double.NaN;
                if ((var25 != 0)) {
                    if (!Double.isInfinite(var3) && !Double.isNaN(var3)) {
                        var25 = (((boolean)true) ? 1 : 0);
                    } else {
                        var25 = (((boolean)false) ? 1 : 0);
                    }

                    if ((var25 != 0)) {
                        var3 = var5 - var3;
                        break label110;
                    }
                }

                var3 = Double.NaN;
            }

            if (!Double.isInfinite(var9) && !Double.isNaN(var9)) {
                var25 = (((boolean)true) ? 1 : 0);
            } else {
                var25 = (((boolean)false) ? 1 : 0);
            }

            var5 = var13;
            if ((var25 != 0)) {
                if (!Double.isInfinite(var7) && !Double.isNaN(var7)) {
                    var25 = (((boolean)true) ? 1 : 0);
                } else {
                    var25 = (((boolean)false) ? 1 : 0);
                }

                var5 = var13;
                if ((var25 != 0)) {
                    var5 = var9 - var7;
                }
            }

            if (!Double.isInfinite(var3) && !Double.isNaN(var3)) {
                var25 = (((boolean)true) ? 1 : 0);
            } else {
                var25 = (((boolean)false) ? 1 : 0);
            }

            byte var20 = -1;
            if (var25 && var3 > -0.5) {
                var25 = laneIndex$count(var3);
            } else {
                var25 = -1;
            }

            boolean var18 = var19;
            if (!Double.isInfinite(var5)) {
                var18 = var19;
                if (!Double.isNaN(var5)) {
                    var18 = true;
                }
            }

            if (var18 && var5 > -0.5) {
                var1 = var1 - 1 - laneIndex$count(var5);
            } else {
                var1 = -1;
            }

            if (var25 >= 0) {
                var1 = (((var25 != 0)) ? 1 : 0);
            } else if (var1 < 0) {
                var1 = -1;
            }

            if (var1 >= 0) {
                var1 = RangesKt.coerceIn(var1, var15, var17);
            } else {
                var1 = var20;
                if (var15 == var17) {
                    var1 = var15;
                }
            }

            return var1;
        }
    }

    private static final int laneIndex$count(double gap) {
        return RangesKt.coerceAtLeast((int) Math.floor((gap - 0.6d) / 3.4d), 0);
    }

    public final Pair<Double, Double> turn(double x, double yRight, double a) {
        double c = Math.cos(a);
        double s = Math.sin(a);
        double yl = -yRight;
        return new Pair<>(Double.valueOf((c * x) - (s * yl)), Double.valueOf(-((s * x) + (c * yl))));
    }

    /* compiled from: OsmRoads.kt */
    public static final class Piece {
        private final Way way;
        private final double[] x;
        private final double[] y;

        public Piece(Way way, double[] x, double[] y) {
            Intrinsics.checkNotNullParameter(way, "way");
            Intrinsics.checkNotNullParameter(x, "x");
            Intrinsics.checkNotNullParameter(y, "y");
            this.way = way;
            this.x = x;
            this.y = y;
        }

        public final Way getWay() {
            return this.way;
        }

        public final double[] getX() {
            return this.x;
        }

        public final double[] getY() {
            return this.y;
        }
    }

    public final List<Piece> carFrame(List<Way> ways2, Function2<? super Double, ? super Double, Pair<Double, Double>> toCar, double r, double step) {
        ArrayList out;
        Iterator<Way> it;
        Way w;
        int m;
        double[] px;
        int i;
        int i2;
        Function2<? super Double, ? super Double, Pair<Double, Double>> toCar2 = toCar;
        Intrinsics.checkNotNullParameter(ways2, "ways");
        Intrinsics.checkNotNullParameter(toCar2, "toCar");
        ArrayList out2 = new ArrayList();
        ArrayList xs = new ArrayList();
        ArrayList ys = new ArrayList();
        Iterator<Way> it2 = ways2.iterator();
        while (it2.hasNext()) {
            Way w2 = it2.next();
            int m2 = w2.getE().length;
            if (m2 >= 2) {
                double[] px2 = new double[m2];
                double[] py = new double[m2];
                for (int i3 = 0; i3 < m2; i3++) {
                    Pair<Double, Double> invoke = toCar2.invoke(Double.valueOf(w2.getE()[i3]), Double.valueOf(w2.getN()[i3]));
                    double x = invoke.component1().doubleValue();
                    double y = invoke.component2().doubleValue();
                    px2[i3] = x;
                    py[i3] = y;
                }
                Ref.DoubleRef dmin = new Ref.DoubleRef();
                dmin.element = Double.MAX_VALUE;
                int i4 = 0;
                int i5 = m2 - 1;
                while (i4 < i5) {
                    double ax = px2[i4];
                    double ay = py[i4];
                    double dx = px2[i4 + 1] - ax;
                    double dy = py[i4 + 1] - ay;
                    double a = (dx * dx) + (dy * dy);
                    if (a >= 1.0E-6d) {
                        m = m2;
                        px = px2;
                        double b = (ax * dx) + (ay * dy);
                        double c = ((ax * ax) + (ay * ay)) - (r * r);
                        double disc = (b * b) - (a * c);
                        i = i4;
                        i2 = i5;
                        if (disc <= 0.0d) {
                            carFrame$flush(xs, out2, dmin, w2, ys);
                            out = out2;
                            it = it2;
                            w = w2;
                        } else {
                            double sq = Math.sqrt(disc);
                            double t0 = Math.max(0.0d, ((-b) - sq) / a);
                            double t1 = Math.min(1.0d, ((-b) + sq) / a);
                            if (t0 >= t1) {
                                carFrame$flush(xs, out2, dmin, w2, ys);
                                out = out2;
                                it = it2;
                                w = w2;
                            } else {
                                if (t0 > 0.0d) {
                                    carFrame$flush(xs, out2, dmin, w2, ys);
                                }
                                ArrayList out3 = out2;
                                double tc = RangesKt.coerceIn((-b) / a, 0.0d, 1.0d);
                                it = it2;
                                Way w3 = w2;
                                dmin.element = Math.min(dmin.element, Math.hypot(ax + (tc * dx), ay + (tc * dy)));
                                double len = Math.sqrt(a) * (t1 - t0);
                                int k = Math.max(1, (int) Math.ceil(len / step));
                                if (xs.isEmpty()) {
                                    xs.add(Double.valueOf(ax + (t0 * dx)));
                                    ys.add(Double.valueOf((t0 * dy) + ay));
                                }
                                int s = 1;
                                if (1 <= k) {
                                    while (true) {
                                        double len2 = len;
                                        double t = (((t1 - t0) * s) / k) + t0;
                                        xs.add(Double.valueOf(ax + (t * dx)));
                                        ys.add(Double.valueOf((t * dy) + ay));
                                        if (s == k) {
                                            break;
                                        }
                                        s++;
                                        len = len2;
                                    }
                                }
                                if (t1 < 1.0d) {
                                    out = out3;
                                    w = w3;
                                    carFrame$flush(xs, out, dmin, w, ys);
                                } else {
                                    out = out3;
                                    w = w3;
                                }
                            }
                        }
                    } else {
                        out = out2;
                        it = it2;
                        w = w2;
                        m = m2;
                        px = px2;
                        i = i4;
                        i2 = i5;
                    }
                    i4 = i + 1;
                    w2 = w;
                    m2 = m;
                    px2 = px;
                    i5 = i2;
                    it2 = it;
                    out2 = out;
                }
                ArrayList out4 = out2;
                carFrame$flush(xs, out4, dmin, w2, ys);
                out2 = out4;
                toCar2 = toCar;
            } else {
                out2 = out2;
                toCar2 = toCar;
            }
        }
        Iterable $this$sortedBy$iv = out2;
        Iterable $this$map$iv = CollectionsKt.sortedWith($this$sortedBy$iv, new Comparator() { // from class: com.starpilot.starview.OsmRoads$carFrame$$inlined$sortedBy$1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.Comparator
            public final int compare(Object t2, Object t3) {
                Pair it3 = (Pair) t2;
                Double d = (Double) it3.getFirst();
                Pair it4 = (Pair) t3;
                return ComparisonsKt.compareValues(d, (Double) it4.getFirst());
            }
        });
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        for (Object item$iv$iv : $this$map$iv) {
            Pair it3 = (Pair) item$iv$iv;
            destination$iv$iv.add((Piece) it3.getSecond());
        }
        return (List) destination$iv$iv;
    }

    private static final void carFrame$flush(ArrayList<Double> arrayList, ArrayList<Pair<Double, Piece>> arrayList2, Ref.DoubleRef dmin, Way w, ArrayList<Double> arrayList3) {
        if (arrayList.size() >= 2) {
            arrayList2.add(new Pair<>(Double.valueOf(dmin.element), new Piece(w, CollectionsKt.toDoubleArray(arrayList), CollectionsKt.toDoubleArray(arrayList3))));
        }
        arrayList.clear();
        arrayList3.clear();
        dmin.element = Double.MAX_VALUE;
    }

    public final Triple<Way, Double, Double> match(double e, double n, double yaw) {
        int i;
        int i2;
        double signed;
        double ax;
        double ax2;
        Way best = null;
        double bd = 0.0d;
        double bs = Double.MAX_VALUE;
        double signed2 = 0.0d;
        for (Way w : near(e, n, 30.0d)) {
            if (!Intrinsics.areEqual(w.getKind(), "sidewalk")) {
                int i3 = 0;
                int length = w.getE().length - 1;
                while (i3 < length) {
                    double ax3 = w.getE()[i3];
                    double ay = w.getN()[i3];
                    Way best2 = best;
                    double dx = w.getE()[i3 + 1] - ax3;
                    double bd2 = bd;
                    double bd3 = w.getN()[i3 + 1] - ay;
                    double len2 = (dx * dx) + (bd3 * bd3);
                    if (len2 < 0.01d) {
                        i = i3;
                        i2 = length;
                        signed = signed2;
                    } else {
                        double t = RangesKt.coerceIn((((e - ax3) * dx) + ((n - ay) * bd3)) / len2, 0.0d, 1.0d);
                        signed = signed2;
                        double signed3 = e - (ax3 + (t * dx));
                        i = i3;
                        i2 = length;
                        double d = Math.hypot(signed3, n - (ay + (t * bd3)));
                        if (d <= 25.0d) {
                            double atan2 = Math.atan2(bd3, dx);
                            double dy = 3.141592653589793d;
                            double dir2 = atan2 + (w.getOneway() < 0 ? 3.141592653589793d : 0.0d);
                            if (w.getOneway() == 0) {
                                ax = ax3;
                                ax2 = dir2 - yaw;
                            } else {
                                ax = ax3;
                                ax2 = dir2 - yaw;
                                dy = 6.283185307179586d;
                            }
                            double dh = Math.abs(Math.IEEEremainder(ax2, dy));
                            double score = (15.0d * dh) + d;
                            if (score < bs) {
                                bs = score;
                                best2 = w;
                                bd2 = d;
                                signed = (((n - ay) * dx) - ((e - ax) * bd3)) / Math.sqrt(len2);
                            }
                        }
                    }
                    best = best2;
                    bd = bd2;
                    signed2 = signed;
                    i3 = i + 1;
                    length = i2;
                }
            }
        }
        if (best == null) {
            return null;
        }
        Way it = best;
        return new Triple<>(it, Double.valueOf(bd), Double.valueOf(signed2));
    }

    /* compiled from: OsmRoads.kt */
    public static final class Align {
        private int jumps;
        private long lastSeenNs;
        private boolean ok;
        private volatile double rejectMiss;
        private volatile long rejectSince;
        private int rejected;
        private double resid;
        private double spread;
        private double th;
        private double tx;
        private double ty;
        private final ArrayDeque<P> pts = new ArrayDeque<>();
        private double lagResid = Double.NaN;
        private volatile String guess = "";

        /* compiled from: OsmRoads.kt */
        private static final class P {
            private final double e;
            private final double n;
            private final double ox;
            private final double oy;
            private final long tNs;
            private final double vx;
            private final double vy;
            private final double w;

            public P(double ox, double oy, double e, double n, double w, long tNs, double vx, double vy) {
                this.ox = ox;
                this.oy = oy;
                this.e = e;
                this.n = n;
                this.w = w;
                this.tNs = tNs;
                this.vx = vx;
                this.vy = vy;
            }

            public /* synthetic */ P(double d, double d2, double d3, double d4, double d5, long j, double d6, double d7, int i, DefaultConstructorMarker defaultConstructorMarker) {
                this(d, d2, d3, d4, d5, j, (i & 64) != 0 ? 0.0d : d6, (i & 128) != 0 ? 0.0d : d7);
            }

            public final double getE() {
                return this.e;
            }

            public final double getN() {
                return this.n;
            }

            public final double getOx() {
                return this.ox;
            }

            public final double getOy() {
                return this.oy;
            }

            public final long getTNs() {
                return this.tNs;
            }

            public final double getVx() {
                return this.vx;
            }

            public final double getVy() {
                return this.vy;
            }

            public final double getW() {
                return this.w;
            }
        }

        public final boolean getOk() {
            return this.ok;
        }

        public final double getTh() {
            return this.th;
        }

        public final double getTx() {
            return this.tx;
        }

        public final double getTy() {
            return this.ty;
        }

        public final double getSpread() {
            return this.spread;
        }

        public final double getResid() {
            return this.resid;
        }

        public final int getJumps() {
            return this.jumps;
        }

        public final long getRejectSince() {
            return this.rejectSince;
        }

        public final double getRejectMiss() {
            return this.rejectMiss;
        }

        public final int getRejected() {
            return this.rejected;
        }

        public final double getLagResid() {
            return this.lagResid;
        }

        public final String getGuess() {
            return this.guess;
        }

        private final boolean getGuessing() {
            return this.guess.length() > 0;
        }

        /* VF: from Vineflower (jadx: Code restructure failed;Removed duplicated region;incorrect) */
        public final void add(double var1, double var3, double var5, OsmRoads.Fix var7, long var8, double var10, double var12) {
        Object var110 = null; // declarations Vineflower left out
            Intrinsics.checkNotNullParameter(var7, "f");
            this.lagResid = Double.NaN;
            long var45 = this.lastSeenNs;
            boolean var43 = false;
            boolean var41;
            if (var45 != 0L && var8 - this.lastSeenNs > 30000000000L) {
                var41 = true;
            } else {
                var41 = false;
            }

            this.lastSeenNs = var8;
            if (!Double.isNaN(OsmRoads.INSTANCE.getLat0()) && var7.getHAcc() > 0.0F && !(var7.getHAcc() > 30.0F)) {
                Pair var47 = OsmRoads.INSTANCE.toLocal(var7.getLat(), var7.getLon());
                double var18 = ((Number)var47.component1()).doubleValue();
                double var20 = ((Number)var47.component2()).doubleValue();
                double var14 = RangesKt.coerceAtLeast(var7.getHAcc() * var7.getHAcc(), 1.0F);
                double var16 = 1.0;
                double var22 = 1.0 / var14;
                if (this.pts.isEmpty()) {
                    var14 = Double.MAX_VALUE;
                } else {
                    var14 = Math.hypot(var1 - this.pts.last().getOx(), var3 - this.pts.last().getOy());
                }

                if (!this.ok || !(var14 <= 0.5)) {
                    boolean var42;
                    if (0.05 <= var14 && var14 <= 0.5) {
                        var42 = true;
                    } else {
                        var42 = false;
                    }

                    if (!var42) {
                        if (this.ok && !this.getGuessing()) {
                            var47 = apply$default(this, var1, var3, 0.0, 0.0, 0.0, 28, null);
                            double var24 = Math.hypot(var18 - ((Number)var47.component1()).doubleValue(), var20 - ((Number)var47.component2()).doubleValue());
                            if (var24 > Math.max(10.0, var7.getHAcc() * 3.0)) {
                                if (this.rejectSince == 0L) {
                                    this.rejectSince = var8;
                                }

                                this.rejectMiss = var24;
                                this.rejected++;
                                if (!var41 && var8 - this.rejectSince < 10000000000L) {
                                    return;
                                }

                                this.pts.clear();
                                this.rejectSince = 0L;
                                this.tx = var18 - (Math.cos(this.th) * var1 - Math.sin(this.th) * var3);
                                this.ty = var20 - (Math.sin(this.th) * var1 + Math.cos(this.th) * var3);
                                this.jumps++;
                                this.pts.addLast(new OsmRoads.Align.P(var1, var3, var18, var20, var22, var8, 0.0, 0.0, 192, null));
                                this.spread = 0.0;
                                this.resid = var7.getHAcc();
                                return;
                            }

                            this.rejectSince = 0L;
                        }

                        while (!this.pts.isEmpty() && (var8 - this.pts.first().getTNs() > 25000000000L || this.pts.size() >= 400)) {
                            this.pts.removeFirst();
                        }

                        if (!this.pts.isEmpty() && !(var14 > 0.5)) {
                            this.pts.set(this.pts.size() - 1, new OsmRoads.Align.P(var1, var3, var18, var20, var22, var8, var10, var12));
                        } else {
                            this.pts.addLast(new OsmRoads.Align.P(var1, var3, var18, var20, var22, var8, var10, var12));
                        }

                        var10 = this.th;
                        OsmRoads.Align.P var109 = this.pts.first();
                        OsmRoads.Align.P var48 = this.pts.last();
                        this.spread = Math.hypot(var48.getOx() - var109.getOx(), var48.getOy() - var109.getOy());
                        if (this.spread >= 25.0 && this.pts.size() >= 8) {
                            var12 = 0.0;
                            var3 = 0.0;
                            var1 = 0.0;
                            Iterator var64 = this.pts.iterator();
                            var10 = 0.0;
                            var5 = 0.0;

                            while (var64.hasNext()) {
                                OsmRoads.Align.P var112 = (OsmRoads.Align.P)var64.next();
                                var5 += var112.getW();
                                var12 += var112.getW() * var112.getOx();
                                var10 += var112.getW() * var112.getOy();
                                var3 += var112.getW() * var112.getE();
                                var1 += var112.getW() * var112.getN();
                            }

                            var18 = var12 / var5;
                            var10 /= var5;
                            var12 = var3 / var5;
                            var5 = var1 / var5;
                            Iterator var113 = this.pts.iterator();
                            var1 = 0.0;
                            var3 = 0.0;

                            while (var113.hasNext()) {
                                OsmRoads.Align.P var65 = (OsmRoads.Align.P)var113.next();
                                var14 = var65.getOx() - var18;
                                var22 = var65.getOy() - var10;
                                var20 = var65.getE() - var12;
                                double var94 = var65.getN() - var5;
                                var3 += var65.getW() * (var14 * var20 + var22 * var94);
                                var1 += var65.getW() * (var14 * var94 - var22 * var20);
                            }

                            var14 = Math.atan2(var1, var3);
                            this.guess = "";
                            var12 -= Math.cos(var14) * var18 - Math.sin(var14) * var10;
                            var18 = var5 - (Math.sin(var14) * var18 + Math.cos(var14) * var10);
                            Iterator var114 = this.pts.iterator();
                            var5 = 0.0;

                            while (var114.hasNext()) {
                                var48 = (OsmRoads.Align.P)var114.next();
                                Pair var66 = this.apply(var48.getOx(), var48.getOy(), var14, var12, var18);
                                var10 = ((Number)var66.component1()).doubleValue();
                                var20 = ((Number)var66.component2()).doubleValue();
                                var5 += Math.hypot(var10 - var48.getE(), var20 - var48.getN());
                            }

                            this.resid = var5 / this.pts.size();
                            OsmRoads.Align var115 = this;
                            var3 = 0.0;
                            var10 = 0.0;
                            Iterator var67 = var115.pts.iterator();
                            var1 = var10;

                            while (var67.hasNext()) {
                                var48 = (OsmRoads.Align.P)var67.next();
                                var10 = Math.hypot(var48.getVx(), var48.getVy());
                                var3 += var48.getW();
                                var1 += var48.getW() * var10;
                            }

                            double var95 = var1 / var3;
                            var10 = 0.0;
                            var5 = 0.0;
                            var22 = Math.cos(var14);
                            var20 = Math.sin(var14);

                            for (OsmRoads.Align.P var49 : var115.pts) {
                                double var26 = Math.hypot(var49.getVx(), var49.getVy());
                                if (!(var26 < 0.5)) {
                                    double var36 = (var49.getVx() * var22 - var49.getVy() * var20) / var26;
                                    double var32 = (var49.getVx() * var20 + var49.getVy() * var22) / var26;
                                    Pair var119 = var115.apply(var49.getOx(), var49.getOy(), var14, var12, var18);
                                    double var28 = ((Number)var119.component1()).doubleValue();
                                    double var30 = ((Number)var119.component2()).doubleValue();
                                    double var34 = var49.getE();
                                    double var38 = var49.getN();
                                    var10 += var49.getW() * ((var34 - var28) * var36 + (var38 - var30) * var32) * (var26 - var95);
                                    var5 += var49.getW() * (var26 - var95) * (var26 - var95);
                                }
                            }

                            if (var5 > 2.0 * var3) {
                                var115.lagResid = RangesKt.coerceIn(-var10 / var5, -1.0, 1.0);
                            }

                            Unit var69 = Unit.INSTANCE;
                            var10 = var14;
                            var3 = var12;
                            var1 = var18;
                        } else {
                            float var40 = var7.getBearingDeg();
                            if (!Float.isInfinite(var40) && !Float.isNaN(var40)) {
                                var41 = true;
                            } else {
                                var41 = false;
                            }

                            label238: {
                                label292: {
                                    if (var41) {
                                        if (var7.getSpeed() > 3.0F) {
                                            break label292;
                                        }

                                        var40 = var7.getBearingAcc();
                                        if (!Float.isInfinite(var40) && !Float.isNaN(var40)) {
                                            var41 = true;
                                        } else {
                                            var41 = false;
                                        }

                                        if (var41) {
                                            var40 = var7.getBearingAcc();
                                            if (0.0F <= var40 && var40 <= 25.0F) {
                                                var41 = true;
                                            } else {
                                                var41 = false;
                                            }

                                            if (var41) {
                                                break label292;
                                            }
                                        }
                                    }

                                    var41 = false;
                                    break label238;
                                }

                                var41 = true;
                            }

                            boolean var44 = this.getGuessing();
                            this.guess = "";
                            if (var41) {
                                var10 = Math.toRadians(90.0 - var7.getBearingDeg()) - var5;
                            } else if (!this.ok || var44) {
                                var40 = var7.getBearingDeg();
                                if (!Float.isInfinite(var40) && !Float.isNaN(var40)) {
                                    var41 = true;
                                } else {
                                    var41 = false;
                                }

                                label205: {
                                    label287: {
                                        if (var41) {
                                            var40 = var7.getBearingAcc();
                                            if (!Float.isInfinite(var40) && !Float.isNaN(var40)) {
                                                var41 = true;
                                            } else {
                                                var41 = false;
                                            }

                                            if (var41 && var7.getBearingAcc() < 90.0F) {
                                                var10 = Math.toRadians(90.0 - var7.getBearingDeg());
                                                break label287;
                                            }
                                        }

                                        if (!this.ok) {
                                            var110 = null;
                                            break label205;
                                        }

                                        var10 = this.th + var5;
                                    }

                                    var110 = var10;
                                }

                                Pair var116 = OsmRoads.INSTANCE.streetHeading(var18, var20, ((Double) var110));
                                if (var116 == null) {
                                    return;
                                }

                                var10 = ((Number)var116.getSecond()).doubleValue() - var5;
                                var var111 = ((OsmRoads.Way)var116.getFirst()).getName();
                                if (var111.length() == 0) {
                                    var41 = true;
                                } else {
                                    var41 = false;
                                }

                                if (var41) {
                                    var111 = ((OsmRoads.Way)var116.getFirst()).getKind();
                                }

                                this.guess = var111;
                            }

                            var12 = Math.cos(var10);
                            var14 = Math.sin(var10);
                            var5 = var20 - (Math.sin(var10) * var1 + Math.cos(var10) * var3);
                            this.resid = var7.getHAcc();
                            var3 = var18 - (var12 * var1 - var14 * var3);
                            var1 = var5;
                        }

                        if (!this.ok) {
                            this.th = var10;
                            this.tx = var3;
                            this.ty = var1;
                            this.ok = true;
                            this.jumps++;
                        } else {
                            label156: {
                                var10 = Math.IEEEremainder(var10 - this.th, Math.PI * 2);
                                if (!(Math.abs(var10) > 0.5)) {
                                    var41 = var43;
                                    if (!(Math.hypot(var3 - this.tx, var1 - this.ty) > 40.0)) {
                                        break label156;
                                    }
                                }

                                var41 = true;
                            }

                            if (var41) {
                                var5 = var16;
                            } else {
                                var5 = 0.05;
                            }

                            if (var41) {
                                this.jumps++;
                            }

                            this.th += var5 * var10;
                            this.tx = this.tx + (var3 - this.tx) * var5;
                            this.ty = this.ty + (var1 - this.ty) * var5;
                        }
                    }
                }
            }
        }

        public static /* synthetic */ Pair apply$default(Align align, double d, double d2, double d3, double d4, double d5, int i, Object obj) {
            return align.apply(d, d2, (i & 4) != 0 ? align.th : d3, (i & 8) != 0 ? align.tx : d4, (i & 16) != 0 ? align.ty : d5);
        }

        public final Pair<Double, Double> apply(double ox, double oy, double th, double tx, double ty) {
            return new Pair<>(Double.valueOf(((Math.cos(th) * ox) - (Math.sin(th) * oy)) + tx), Double.valueOf((Math.sin(th) * ox) + (Math.cos(th) * oy) + ty));
        }

        public final Pair<Double, Double> inverse(double e, double n) {
            double dx = e - this.tx;
            double dy = n - this.ty;
            return new Pair<>(Double.valueOf((Math.cos(this.th) * dx) + (Math.sin(this.th) * dy)), Double.valueOf(((-Math.sin(this.th)) * dx) + (Math.cos(this.th) * dy)));
        }

        public final void reset() {
            this.pts.clear();
            this.ok = false;
            this.rejectSince = 0L;
        }
    }
}
