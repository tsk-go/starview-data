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
            return StringsKt.startsWith$default(this.kind, "motorway", false, 2, (Object) null) || StringsKt.startsWith$default(this.kind, "trunk", false, 2, (Object) null);
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
            List p = StringsKt.split$default((CharSequence) k2, new char[]{'_'}, false, 0, 6, (Object) null);
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
            Iterator<T> it = values.iterator();
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
        final int dy = -1;
        while (true) {
            int i2 = 2;
            if (dy >= 2) {
                return;
            }
            final int dx = -1;
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
                    str = FilesKt.readText$default(file2, null, 1, null);
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
                                            if (StringsKt.startsWith$default(StringsKt.trimStart((CharSequence) string).toString(), "{", false, 2, (Object) null)) {
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
                        FilesKt.writeText$default(file3, str, null, 2, null);
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

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x0145, code lost:
    
        if (r1.equals("reverse") == false) goto L62;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0163, code lost:
    
        r11 = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x014e, code lost:
    
        if (r1.equals("true") == false) goto L62;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x0157, code lost:
    
        if (r1.equals("yes") == false) goto L62;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x0160, code lost:
    
        if (r1.equals("-1") == false) goto L62;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x016b, code lost:
    
        if (r1.equals("1") == false) goto L62;
     */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
     */
    /* JADX WARN: Removed duplicated region for block: B:56:0x01bb  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x01c2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int parse$app_debug(String text) {
        JSONArray els;
        int i;
        int i2;
        Intrinsics.checkNotNullParameter(text, "text");
        JSONArray els2 = new JSONObject(text).optJSONArray("elements");
        char c = 0;
        if (els2 == null) {
            return 0;
        }
        int added = 0;
        int i3 = 0;
        int length = els2.length();
        while (i3 < length) {
            JSONObject o = els2.optJSONObject(i3);
            if (o == null) {
                els = els2;
                i = length;
            } else if (!Intrinsics.areEqual(o.optString("type"), "way")) {
                els = els2;
                i = length;
            } else {
                long id = o.optLong("id");
                if (ways.containsKey(Long.valueOf(id))) {
                    els = els2;
                    i = length;
                } else {
                    JSONObject tags = o.optJSONObject("tags");
                    if (tags == null) {
                        els = els2;
                        i = length;
                    } else {
                        String it = tags.optString("highway");
                        if (Intrinsics.areEqual(it, "footway") && Intrinsics.areEqual(tags.optString("footway"), "sidewalk")) {
                            it = "sidewalk";
                        }
                        String kind = it;
                        if (KINDS.contains(kind) || Intrinsics.areEqual(kind, "sidewalk")) {
                            String svc = tags.optString("service");
                            if (Intrinsics.areEqual(kind, "service")) {
                                String[] strArr = new String[4];
                                strArr[c] = "driveway";
                                strArr[1] = "parking_aisle";
                                strArr[2] = "drive-through";
                                strArr[3] = "emergency_access";
                                if (SetsKt.setOf((Object[]) strArr).contains(svc)) {
                                    els = els2;
                                    i = length;
                                }
                            }
                            JSONArray g = o.optJSONArray("geometry");
                            if (g == null) {
                                els = els2;
                                i = length;
                            } else if (g.length() < 2) {
                                els = els2;
                                i = length;
                            } else {
                                double[] e = new double[g.length()];
                                double[] n = new double[g.length()];
                                int j = 0;
                                int length2 = g.length();
                                while (j < length2) {
                                    JSONObject p = g.getJSONObject(j);
                                    JSONArray els3 = els2;
                                    int i4 = length;
                                    Pair<Double, Double> local = toLocal(p.getDouble("lat"), p.getDouble("lon"));
                                    double x = local.component1().doubleValue();
                                    double y = local.component2().doubleValue();
                                    e[j] = x;
                                    n[j] = y;
                                    j++;
                                    length2 = length2;
                                    els2 = els3;
                                    length = i4;
                                    o = o;
                                }
                                els = els2;
                                i = length;
                                String optString = tags.optString("oneway");
                                if (optString != null) {
                                    switch (optString.hashCode()) {
                                        case 49:
                                            break;
                                        case 1444:
                                            break;
                                        case 119527:
                                            break;
                                        case 3569038:
                                            break;
                                        case 1099846370:
                                            break;
                                    }
                                    int ow = i2;
                                    ConcurrentHashMap<Long, Way> concurrentHashMap = ways;
                                    Long valueOf = Long.valueOf(id);
                                    String optString2 = tags.optString("name", tags.optString("ref", ""));
                                    Intrinsics.checkNotNullExpressionValue(optString2, "optString(...)");
                                    Intrinsics.checkNotNull(kind);
                                    float widthOf = widthOf(kind, ow, tags);
                                    String optString3 = tags.optString("lanes");
                                    Intrinsics.checkNotNullExpressionValue(optString3, "optString(...)");
                                    Integer intOrNull = StringsKt.toIntOrNull(optString3);
                                    concurrentHashMap.put(valueOf, new Way(id, optString2, kind, ow, widthOf, e, n, intOrNull != null ? intOrNull.intValue() : 0));
                                    added++;
                                }
                                if (!Intrinsics.areEqual(kind, "motorway") && !Intrinsics.areEqual(tags.optString("junction"), "roundabout")) {
                                    i2 = 0;
                                    int ow2 = i2;
                                    ConcurrentHashMap<Long, Way> concurrentHashMap2 = ways;
                                    Long valueOf2 = Long.valueOf(id);
                                    String optString22 = tags.optString("name", tags.optString("ref", ""));
                                    Intrinsics.checkNotNullExpressionValue(optString22, "optString(...)");
                                    Intrinsics.checkNotNull(kind);
                                    float widthOf2 = widthOf(kind, ow2, tags);
                                    String optString32 = tags.optString("lanes");
                                    Intrinsics.checkNotNullExpressionValue(optString32, "optString(...)");
                                    Integer intOrNull2 = StringsKt.toIntOrNull(optString32);
                                    concurrentHashMap2.put(valueOf2, new Way(id, optString22, kind, ow2, widthOf2, e, n, intOrNull2 != null ? intOrNull2.intValue() : 0));
                                    added++;
                                }
                                i2 = 1;
                                int ow22 = i2;
                                ConcurrentHashMap<Long, Way> concurrentHashMap22 = ways;
                                Long valueOf22 = Long.valueOf(id);
                                String optString222 = tags.optString("name", tags.optString("ref", ""));
                                Intrinsics.checkNotNullExpressionValue(optString222, "optString(...)");
                                Intrinsics.checkNotNull(kind);
                                float widthOf22 = widthOf(kind, ow22, tags);
                                String optString322 = tags.optString("lanes");
                                Intrinsics.checkNotNullExpressionValue(optString322, "optString(...)");
                                Integer intOrNull22 = StringsKt.toIntOrNull(optString322);
                                concurrentHashMap22.put(valueOf22, new Way(id, optString222, kind, ow22, widthOf22, e, n, intOrNull22 != null ? intOrNull22.intValue() : 0));
                                added++;
                            }
                        } else {
                            els = els2;
                            i = length;
                        }
                    }
                }
            }
            i3++;
            els2 = els;
            length = i;
            c = 0;
        }
        return added;
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
        Float floatOrNull = StringsKt.toFloatOrNull(StringsKt.trim((CharSequence) StringsKt.replace$default(optString2, "m", "", false, 4, (Object) null)).toString());
        String optString3 = tags.optString("parking:both", tags.optString("parking:lane:both", ""));
        Intrinsics.checkNotNullExpressionValue(optString3, "optString(...)");
        return widthOf(kind, oneway, intValue, floatOrNull, optString3);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x00a1, code lost:
    
        if (r15.equals("unclassified") == false) goto L75;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x00ab, code lost:
    
        if (r16 == 0) goto L46;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x00ad, code lost:
    
        return 9.0f;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x00b0, code lost:
    
        return 10.0f;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x00a8, code lost:
    
        if (r15.equals("residential") == false) goto L75;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x006e, code lost:
    
        if (r5 != false) goto L23;
     */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:19:0x007b  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0087  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00fe A[ORIG_RETURN, RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final float widthOf(String kind, int oneway, int lanes, Float widthTag, String parkingTag) {
        Intrinsics.checkNotNullParameter(kind, "kind");
        Intrinsics.checkNotNullParameter(parkingTag, "parkingTag");
        if (widthTag != null) {
            float it = widthTag.floatValue();
            if (2.0f <= it && it <= 40.0f) {
                return it;
            }
        }
        int parkSides = 2;
        boolean urban = SetsKt.setOf((Object[]) new String[]{"residential", "tertiary", "secondary", "primary", "unclassified", "living_street"}).contains(kind);
        if (!Intrinsics.areEqual(parkingTag, "no")) {
            if (!(parkingTag.length() > 0)) {
            }
            if (!(1 > lanes && lanes < 11)) {
                return (lanes * 3.2f) + (parkSides * 2.3f);
            }
            switch (kind.hashCode()) {
                case -2014090663:
                    return !kind.equals("living_street") ? 6.5f : 7.0f;
                case -1174796206:
                    if (kind.equals("tertiary")) {
                        return 12.0f;
                    }
                    break;
                case -817598092:
                    if (kind.equals("secondary")) {
                        return 13.0f;
                    }
                    break;
                case -314765822:
                    if (kind.equals("primary")) {
                        return 15.0f;
                    }
                    break;
                case -151535014:
                    if (kind.equals("motorway")) {
                        return 11.0f;
                    }
                    break;
                case 110640564:
                    if (kind.equals("trunk")) {
                        return 14.0f;
                    }
                    break;
                case 218164128:
                    if (kind.equals("sidewalk")) {
                        return 0.0f;
                    }
                    break;
                case 1098352388:
                    break;
                case 1840151916:
                    break;
                case 1984153269:
                    if (kind.equals("service")) {
                        return 5.0f;
                    }
                    break;
            }
        }
        parkSides = 0;
        if (!(1 > lanes && lanes < 11)) {
        }
    }

    public final Pair<Double, Double> toLatLon(double e, double n) {
        return new Pair<>(Double.valueOf(lat0 + (n / 110540.0d)), Double.valueOf(lon0 + (e / kx)));
    }

    /* JADX WARN: Code restructure failed: missing block: B:33:0x00d7, code lost:
    
        if (r0 != false) goto L31;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Set<String> nameTokens(String name) {
        Intrinsics.checkNotNullParameter(name, "name");
        String lowerCase = name.toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
        String replace = new Regex("[^a-z0-9 ]").replace(lowerCase, " ");
        boolean z = true;
        Iterable $this$filter$iv = StringsKt.split$default((CharSequence) replace, new char[]{' '}, false, 0, 6, (Object) null);
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            if (!StringsKt.isBlank((String) element$iv$iv)) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        Iterable $this$map$iv = (List) destination$iv$iv;
        Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        for (Object item$iv$iv : $this$map$iv) {
            String it = (String) item$iv$iv;
            String str = ABBR.get(it);
            if (str != null) {
                it = str;
            }
            destination$iv$iv2.add(it);
        }
        Set all = CollectionsKt.toSet((List) destination$iv$iv2);
        Set core = SetsKt.minus(all, (Iterable) DIRS);
        if (core.size() < 2) {
            if (core.size() == 1) {
                CharSequence $this$any$iv = (CharSequence) CollectionsKt.first(core);
                int i = 0;
                while (true) {
                    if (i >= $this$any$iv.length()) {
                        z = false;
                        break;
                    }
                    char element$iv = $this$any$iv.charAt(i);
                    if (Character.isDigit(element$iv)) {
                        break;
                    }
                    i++;
                }
            }
            return all;
        }
        return core;
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
            for (Way w2 : l) {
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

    /* JADX WARN: Code restructure failed: missing block: B:213:0x03c9, code lost:
    
        r41 = "get(...)";
        r8 = r7;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final List<Pair<Double, String>> crossStreetsAhead(double e, double n, double yaw, double maxM) {
        double bd;
        int bi;
        String str;
        OsmRoads osmRoads;
        int k;
        String str2;
        int i;
        Iterator<Way> it;
        int i2;
        boolean z;
        double sx;
        int i3;
        int k2;
        String str3;
        int i4;
        int last;
        Iterator it2;
        Match m;
        OsmRoads osmRoads2 = this;
        Match it3 = matchSeg(e, n, yaw);
        if (it3 != null) {
            if ((it3.getDist() < 15.0d ? 1 : null) == null) {
                it3 = null;
            }
            if (it3 != null) {
                Match m2 = it3;
                ArrayList px = new ArrayList();
                ArrayList py = new ArrayList();
                HashSet chain = new HashSet();
                Way w = m2.getWay();
                chain.add(Long.valueOf(w.getId()));
                int k3 = 0;
                int length = w.getE().length - 1;
                int bi2 = 0;
                double bd2 = Double.MAX_VALUE;
                while (k3 < length) {
                    double ax = w.getE()[k3];
                    double ay = w.getN()[k3];
                    double dx = w.getE()[k3 + 1] - ax;
                    double dy = w.getN()[k3 + 1] - ay;
                    double len2 = (dx * dx) + (dy * dy);
                    if (len2 >= 0.01d) {
                        double t = RangesKt.coerceIn((((e - ax) * dx) + ((n - ay) * dy)) / len2, 0.0d, 1.0d);
                        m = m2;
                        double d = Math.hypot(e - (ax + (t * dx)), n - (ay + (t * dy)));
                        if (d < bd2) {
                            bi2 = k3;
                            bd2 = d;
                        }
                    } else {
                        m = m2;
                    }
                    k3++;
                    m2 = m;
                }
                Match m3 = m2;
                boolean fwd0 = Math.cos(Math.atan2(w.getN()[bi2 + 1] - w.getN()[bi2], w.getE()[bi2 + 1] - w.getE()[bi2]) - yaw) >= 0.0d;
                IntRange verts = fwd0 ? RangesKt.until(bi2 + 1, w.getE().length) : RangesKt.downTo(bi2, 0);
                px.add(Double.valueOf(e));
                py.add(Double.valueOf(n));
                double len = 0.0d;
                Way w2 = w;
                IntProgression verts2 = verts;
                int hops = 0;
                while (true) {
                    int k4 = verts2.getFirst();
                    int last2 = verts2.getLast();
                    int step = verts2.getStep();
                    if ((step > 0 && k4 <= last2) || (step < 0 && last2 <= k4)) {
                        while (true) {
                            bd = bd2;
                            bi = bi2;
                            len += Math.hypot(w2.getE()[k4] - ((Number) CollectionsKt.last((List) px)).doubleValue(), w2.getN()[k4] - ((Number) CollectionsKt.last((List) py)).doubleValue());
                            px.add(Double.valueOf(w2.getE()[k4]));
                            py.add(Double.valueOf(w2.getN()[k4]));
                            if (len >= maxM || k4 == last2) {
                                break;
                            }
                            k4 += step;
                            bi2 = bi;
                            bd2 = bd;
                        }
                    } else {
                        bd = bd2;
                        bi = bi2;
                    }
                    double len3 = len;
                    if (len3 >= maxM || hops >= 8) {
                        break;
                    }
                    if (px.size() < 2) {
                        str = "get(...)";
                        osmRoads = osmRoads2;
                        break;
                    }
                    double ex = ((Number) CollectionsKt.last((List) px)).doubleValue();
                    double ey = ((Number) CollectionsKt.last((List) py)).doubleValue();
                    Object obj = py.get(py.size() - 2);
                    Intrinsics.checkNotNullExpressionValue(obj, "get(...)");
                    double doubleValue = ey - ((Number) obj).doubleValue();
                    Object obj2 = px.get(px.size() - 2);
                    Intrinsics.checkNotNullExpressionValue(obj2, "get(...)");
                    double hd = Math.atan2(doubleValue, ex - ((Number) obj2).doubleValue());
                    Way next = null;
                    boolean nf = true;
                    double best = Double.MAX_VALUE;
                    int hops2 = hops;
                    str = "get(...)";
                    int i5 = 2;
                    for (Way o : near(ex, ey, 6.0d)) {
                        if (chain.contains(Long.valueOf(o.getId())) || Intrinsics.areEqual(o.getKind(), "sidewalk") || Intrinsics.areEqual(o.getKind(), "service")) {
                            len3 = len3;
                            i5 = 2;
                        } else {
                            int last3 = o.getE().length - 1;
                            Pair[] pairArr = new Pair[i5];
                            pairArr[0] = TuplesKt.to(0, true);
                            pairArr[1] = TuplesKt.to(Integer.valueOf(last3), false);
                            Iterator it4 = CollectionsKt.listOf((Object[]) pairArr).iterator();
                            while (it4.hasNext()) {
                                Pair pair = (Pair) it4.next();
                                int end = ((Number) pair.component1()).intValue();
                                boolean f = ((Boolean) pair.component2()).booleanValue();
                                Way next2 = next;
                                double len4 = len3;
                                if (Math.hypot(o.getE()[end] - ex, o.getN()[end] - ey) > 4.0d) {
                                    last = last3;
                                    it2 = it4;
                                } else if ((f || o.getOneway() <= 0) && (!f || o.getOneway() >= 0)) {
                                    double[] e2 = o.getE();
                                    double nx = f ? e2[1] - o.getE()[0] : e2[last3 - 1] - o.getE()[last3];
                                    double[] n2 = o.getN();
                                    double ny = f ? n2[1] - o.getN()[0] : n2[last3 - 1] - o.getN()[last3];
                                    last = last3;
                                    it2 = it4;
                                    double dh = Math.abs(Math.IEEEremainder(Math.atan2(ny, nx) - hd, 6.283185307179586d));
                                    if (dh <= Math.toRadians(35.0d)) {
                                        double score = (sameStreet(o.getName(), w2.getName()) ? 0.0d : 0.3d) + dh;
                                        if (score < best) {
                                            best = score;
                                            nf = f;
                                            next = o;
                                            last3 = last;
                                            len3 = len4;
                                            it4 = it2;
                                        }
                                    }
                                } else {
                                    last = last3;
                                    it2 = it4;
                                }
                                next = next2;
                                last3 = last;
                                len3 = len4;
                                it4 = it2;
                            }
                            next = next;
                            len3 = len3;
                            i5 = 2;
                        }
                    }
                    osmRoads = this;
                    double len5 = len3;
                    if (next == null) {
                        break;
                    }
                    w2 = next;
                    chain.add(Long.valueOf(w2.getId()));
                    hops = hops2 + 1;
                    double[] e3 = w2.getE();
                    verts2 = nf ? RangesKt.until(1, e3.length) : RangesKt.downTo(e3.length - 2, 0);
                    osmRoads2 = osmRoads;
                    bi2 = bi;
                    bd2 = bd;
                    len = len5;
                }
                double[] cum = new double[px.size()];
                int size = px.size();
                for (int k5 = 1; k5 < size; k5++) {
                    double d2 = cum[k5 - 1];
                    double doubleValue2 = ((Number) px.get(k5)).doubleValue();
                    Object obj3 = px.get(k5 - 1);
                    String str4 = str;
                    Intrinsics.checkNotNullExpressionValue(obj3, str4);
                    double doubleValue3 = doubleValue2 - ((Number) obj3).doubleValue();
                    double doubleValue4 = ((Number) py.get(k5)).doubleValue();
                    Object obj4 = py.get(k5 - 1);
                    Intrinsics.checkNotNullExpressionValue(obj4, str4);
                    cum[k5] = d2 + Math.hypot(doubleValue3, doubleValue4 - ((Number) obj4).doubleValue());
                }
                String ourName = m3.getWay().getName();
                ArrayList out = new ArrayList();
                Iterator<Way> it5 = near(e, n, maxM + 30.0d).iterator();
                while (it5.hasNext()) {
                    Way o2 = it5.next();
                    if (chain.contains(Long.valueOf(o2.getId())) || Intrinsics.areEqual(o2.getKind(), "sidewalk")) {
                        osmRoads = this;
                        it5 = it5;
                    } else if (!Intrinsics.areEqual(o2.getKind(), "service")) {
                        if (!(ourName.length() > 0) || !osmRoads.sameStreet(o2.getName(), ourName)) {
                            int size2 = px.size() - 1;
                            int k6 = 0;
                            while (k6 < size2) {
                                Object obj5 = px.get(k6);
                                String str5 = str;
                                Intrinsics.checkNotNullExpressionValue(obj5, str5);
                                double ax2 = ((Number) obj5).doubleValue();
                                Object obj6 = py.get(k6);
                                Intrinsics.checkNotNullExpressionValue(obj6, str5);
                                double ay2 = ((Number) obj6).doubleValue();
                                Object obj7 = px.get(k6 + 1);
                                Intrinsics.checkNotNullExpressionValue(obj7, str5);
                                double bx = ((Number) obj7).doubleValue();
                                Object obj8 = py.get(k6 + 1);
                                Intrinsics.checkNotNullExpressionValue(obj8, str5);
                                double by = ((Number) obj8).doubleValue();
                                double sx2 = bx - ax2;
                                String ourName2 = ourName;
                                double sy = by - ay2;
                                double sl = Math.hypot(sx2, sy);
                                if (sl >= 0.01d) {
                                    int length2 = o2.getE().length - 1;
                                    it = it5;
                                    int j = 0;
                                    while (j < length2) {
                                        double cx = o2.getE()[j];
                                        double cy = o2.getN()[j];
                                        double tx = o2.getE()[j + 1] - cx;
                                        double ty = o2.getN()[j + 1] - cy;
                                        double den = (sx2 * ty) - (sy * tx);
                                        if (Math.abs(den) <= 1.0E-9d) {
                                            sx = sx2;
                                            i3 = length2;
                                            k2 = k6;
                                            str3 = str5;
                                            i4 = size2;
                                        } else {
                                            double u = (((cx - ax2) * ty) - ((cy - ay2) * tx)) / den;
                                            double v = (((cx - ax2) * sy) - ((cy - ay2) * sx2)) / den;
                                            if (0.0d <= u && u <= 1.0d) {
                                                if (0.0d <= v && v <= 1.0d) {
                                                    sx = sx2;
                                                    i3 = length2;
                                                    k2 = k6;
                                                    str3 = str5;
                                                    i4 = size2;
                                                    crossStreetsAhead$add(maxM, out, cum[k6] + (u * sl), o2.getName());
                                                }
                                            }
                                            sx = sx2;
                                            i3 = length2;
                                            k2 = k6;
                                            str3 = str5;
                                            i4 = size2;
                                        }
                                        j++;
                                        length2 = i3;
                                        sx2 = sx;
                                        k6 = k2;
                                        str5 = str3;
                                        size2 = i4;
                                    }
                                    double sx3 = sx2;
                                    k = k6;
                                    str2 = str5;
                                    i = size2;
                                    boolean z2 = false;
                                    int[] iArr = {0, o2.getE().length - 1};
                                    int i6 = 0;
                                    while (i6 < 2) {
                                        int end2 = iArr[i6];
                                        double t2 = (((o2.getE()[end2] - ax2) * sx3) + ((o2.getN()[end2] - ay2) * sy)) / (sl * sl);
                                        if (!((0.0d > t2 || t2 > 1.0d) ? z2 : true)) {
                                            i2 = i6;
                                            z = z2;
                                        } else if (Math.hypot(o2.getE()[end2] - (ax2 + (t2 * sx3)), o2.getN()[end2] - (ay2 + (t2 * sy))) < 6.0d) {
                                            i2 = i6;
                                            z = false;
                                            crossStreetsAhead$add(maxM, out, (t2 * sl) + cum[k], o2.getName());
                                        } else {
                                            i2 = i6;
                                            z = false;
                                        }
                                        i6 = i2 + 1;
                                        z2 = z;
                                    }
                                } else {
                                    k = k6;
                                    str2 = str5;
                                    i = size2;
                                    it = it5;
                                }
                                k6 = k + 1;
                                ourName = ourName2;
                                it5 = it;
                                str = str2;
                                size2 = i;
                            }
                            osmRoads = this;
                            it5 = it5;
                        }
                    }
                }
                ArrayList $this$sortedBy$iv = out;
                return CollectionsKt.sortedWith($this$sortedBy$iv, new Comparator() { // from class: com.starpilot.starview.OsmRoads$crossStreetsAhead$$inlined$sortedBy$1
                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // java.util.Comparator
                    public final int compare(T t3, T t4) {
                        Pair it6 = (Pair) t3;
                        Double d3 = (Double) it6.getFirst();
                        Pair it7 = (Pair) t4;
                        return ComparisonsKt.compareValues(d3, (Double) it7.getFirst());
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

    /* JADX WARN: Removed duplicated region for block: B:34:0x004f  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x00a0  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x00aa  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x00af  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x00a2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int laneIndex(int lanes, boolean yellowLeft, double dLeftLine, double dLeftEdge, double dRightLine, double dRightEdge, boolean leftDashed, boolean rightDashed) {
        double gl;
        double gr;
        int fromL;
        int idx;
        boolean z = false;
        if (lanes <= 1 || yellowLeft) {
            return 0;
        }
        int hi = lanes - 1;
        int lo = leftDashed ? 1 : 0;
        if (rightDashed) {
            hi = lanes - 2;
        }
        if (lo > hi) {
            hi = lo;
        }
        double d = Double.NaN;
        if ((Double.isInfinite(dLeftEdge) || Double.isNaN(dLeftEdge)) ? false : true) {
            if ((Double.isInfinite(dLeftLine) || Double.isNaN(dLeftLine)) ? false : true) {
                gl = dLeftEdge - dLeftLine;
                if (Double.isInfinite(dRightEdge) && !Double.isNaN(dRightEdge)) {
                    if ((Double.isInfinite(dRightLine) || Double.isNaN(dRightLine)) ? false : true) {
                        d = dRightEdge - dRightLine;
                    }
                }
                gr = d;
                fromL = ((Double.isInfinite(gl) && !Double.isNaN(gl)) || gl <= -0.5d) ? -1 : laneIndex$count(gl);
                if (!Double.isInfinite(gr) && !Double.isNaN(gr)) {
                    z = true;
                }
                int fromR = (z || gr <= -0.5d) ? -1 : (lanes - 1) - laneIndex$count(gr);
                if (fromL < 0) {
                    idx = fromL;
                } else {
                    idx = fromR >= 0 ? fromR : -1;
                }
                if (idx < 0) {
                    return RangesKt.coerceIn(idx, lo, hi);
                }
                if (lo == hi) {
                    return lo;
                }
                return -1;
            }
        }
        gl = Double.NaN;
        if (Double.isInfinite(dRightEdge) && !Double.isNaN(dRightEdge)) {
        }
        gr = d;
        if (Double.isInfinite(gl) && !Double.isNaN(gl)) {
        }
        if (!Double.isInfinite(gr)) {
            z = true;
        }
        if (z) {
        }
        if (fromL < 0) {
        }
        if (idx < 0) {
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
            public final int compare(T t2, T t3) {
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

        /* JADX WARN: Code restructure failed: missing block: B:137:0x0505, code lost:
        
            if ((0.0f <= r2 && r2 <= 25.0f) != false) goto L117;
         */
        /* JADX WARN: Removed duplicated region for block: B:143:0x051b  */
        /* JADX WARN: Removed duplicated region for block: B:145:0x0529  */
        /* JADX WARN: Removed duplicated region for block: B:166:0x058b A[RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:167:0x058c  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void add(double ox, double oy, double oyaw, Fix f, long nowNs, double vox, double voy) {
            boolean z;
            boolean bearingOk;
            Align align;
            Double d;
            double d2;
            Pair sh;
            double nty;
            double ntx;
            Intrinsics.checkNotNullParameter(f, "f");
            this.lagResid = Double.NaN;
            int i = 1;
            boolean gap = this.lastSeenNs != 0 && nowNs - this.lastSeenNs > 30000000000L;
            this.lastSeenNs = nowNs;
            if (Double.isNaN(OsmRoads.INSTANCE.getLat0()) || f.getHAcc() <= 0.0f) {
                return;
            }
            if (f.getHAcc() > 30.0f) {
                return;
            }
            Pair<Double, Double> local = OsmRoads.INSTANCE.toLocal(f.getLat(), f.getLon());
            double e = local.component1().doubleValue();
            double n = local.component2().doubleValue();
            double w = 1.0d / RangesKt.coerceAtLeast(f.getHAcc() * f.getHAcc(), 1.0f);
            double moved = this.pts.isEmpty() ? Double.MAX_VALUE : Math.hypot(ox - this.pts.last().getOx(), oy - this.pts.last().getOy());
            if (!this.ok || moved > 0.5d) {
                if (0.05d <= moved && moved <= 0.5d) {
                    return;
                }
                if (this.ok && !getGuessing()) {
                    Pair apply$default = apply$default(this, ox, oy, 0.0d, 0.0d, 0.0d, 28, null);
                    double pe = ((Number) apply$default.component1()).doubleValue();
                    double pn = ((Number) apply$default.component2()).doubleValue();
                    double miss = Math.hypot(e - pe, n - pn);
                    if (miss > Math.max(10.0d, f.getHAcc() * 3.0d)) {
                        if (this.rejectSince == 0) {
                            this.rejectSince = nowNs;
                        }
                        this.rejectMiss = miss;
                        this.rejected++;
                        if (gap || nowNs - this.rejectSince >= 10000000000L) {
                            this.pts.clear();
                            this.rejectSince = 0L;
                            this.tx = e - ((Math.cos(this.th) * ox) - (Math.sin(this.th) * oy));
                            this.ty = n - ((Math.sin(this.th) * ox) + (Math.cos(this.th) * oy));
                            this.jumps++;
                            this.pts.addLast(new P(ox, oy, e, n, w, nowNs, 0.0d, 0.0d, 192, null));
                            this.spread = 0.0d;
                            this.resid = f.getHAcc();
                            return;
                        }
                        return;
                    }
                    i = 1;
                    this.rejectSince = 0L;
                }
                while (!this.pts.isEmpty() && (nowNs - this.pts.first().getTNs() > 25000000000L || this.pts.size() >= 400)) {
                    this.pts.removeFirst();
                }
                if (this.pts.isEmpty() || moved > 0.5d) {
                    this.pts.addLast(new P(ox, oy, e, n, w, nowNs, vox, voy));
                } else {
                    this.pts.set(this.pts.size() - i, new P(ox, oy, e, n, w, nowNs, vox, voy));
                }
                double nth = this.th;
                P first = this.pts.first();
                P last = this.pts.last();
                this.spread = Math.hypot(last.getOx() - first.getOx(), last.getOy() - first.getOy());
                if (this.spread >= 25.0d && this.pts.size() >= 8) {
                    double px = 0.0d;
                    double qx = 0.0d;
                    double qy = 0.0d;
                    Iterator it = this.pts.iterator();
                    double sw = 0.0d;
                    double py = 0.0d;
                    while (it.hasNext()) {
                        P p = (P) it.next();
                        py += p.getW();
                        px += p.getW() * p.getOx();
                        sw += p.getW() * p.getOy();
                        qx += p.getW() * p.getE();
                        qy += p.getW() * p.getN();
                    }
                    double px2 = px / py;
                    double py2 = sw / py;
                    double qx2 = qx / py;
                    double qy2 = qy / py;
                    Iterator it2 = this.pts.iterator();
                    double sxy = 0.0d;
                    double sxx = 0.0d;
                    while (it2.hasNext()) {
                        P p2 = (P) it2.next();
                        double ax = p2.getOx() - px2;
                        double ay = p2.getOy() - py2;
                        double bx = p2.getE() - qx2;
                        double by = p2.getN() - qy2;
                        sxx += p2.getW() * ((ax * bx) + (ay * by));
                        sxy += p2.getW() * ((ax * by) - (ay * bx));
                    }
                    double nth2 = Math.atan2(sxy, sxx);
                    this.guess = "";
                    double ntx2 = qx2 - ((Math.cos(nth2) * px2) - (Math.sin(nth2) * py2));
                    nty = qy2 - ((Math.sin(nth2) * px2) + (Math.cos(nth2) * py2));
                    Iterator it3 = this.pts.iterator();
                    double r = 0.0d;
                    while (it3.hasNext()) {
                        P p3 = (P) it3.next();
                        Pair<Double, Double> apply = apply(p3.getOx(), p3.getOy(), nth2, ntx2, nty);
                        double ae = apply.component1().doubleValue();
                        double an = apply.component2().doubleValue();
                        r += Math.hypot(ae - p3.getE(), an - p3.getN());
                        sxx = sxx;
                    }
                    this.resid = r / this.pts.size();
                    Align $this$add_u24lambda_u240 = this;
                    double sv = 0.0d;
                    double swv = 0.0d;
                    Iterator it4 = $this$add_u24lambda_u240.pts.iterator();
                    while (it4.hasNext()) {
                        P p4 = (P) it4.next();
                        double v = Math.hypot(p4.getVx(), p4.getVy());
                        sv += p4.getW();
                        swv += p4.getW() * v;
                        sxy = sxy;
                    }
                    double vm = swv / sv;
                    double num = 0.0d;
                    double den = 0.0d;
                    double c = Math.cos(nth2);
                    double sn = Math.sin(nth2);
                    Iterator it5 = $this$add_u24lambda_u240.pts.iterator();
                    while (it5.hasNext()) {
                        P p5 = (P) it5.next();
                        double swv2 = swv;
                        double swv3 = p5.getVx();
                        Iterator it6 = it5;
                        double v2 = Math.hypot(swv3, p5.getVy());
                        if (v2 >= 0.5d) {
                            double ue = ((p5.getVx() * c) - (p5.getVy() * sn)) / v2;
                            double un = ((p5.getVx() * sn) + (p5.getVy() * c)) / v2;
                            Pair<Double, Double> apply2 = $this$add_u24lambda_u240.apply(p5.getOx(), p5.getOy(), nth2, ntx2, nty);
                            double ae2 = apply2.component1().doubleValue();
                            double an2 = apply2.component2().doubleValue();
                            double along = ((p5.getE() - ae2) * ue) + ((p5.getN() - an2) * un);
                            num += p5.getW() * along * (v2 - vm);
                            den += p5.getW() * (v2 - vm) * (v2 - vm);
                            it5 = it6;
                            swv = swv2;
                        } else {
                            it5 = it6;
                            swv = swv2;
                        }
                    }
                    if (den > 2.0d * sv) {
                        $this$add_u24lambda_u240.lagResid = RangesKt.coerceIn((-num) / den, -1.0d, 1.0d);
                    }
                    Unit unit = Unit.INSTANCE;
                    align = this;
                    nth = nth2;
                    ntx = ntx2;
                } else {
                    float bearingDeg = f.getBearingDeg();
                    if ((Float.isInfinite(bearingDeg) || Float.isNaN(bearingDeg)) ? false : true) {
                        if (f.getSpeed() <= 3.0f) {
                            float bearingAcc = f.getBearingAcc();
                            if ((Float.isInfinite(bearingAcc) || Float.isNaN(bearingAcc)) ? false : true) {
                                float bearingAcc2 = f.getBearingAcc();
                            }
                        }
                        z = true;
                        bearingOk = z;
                        boolean wasGuessing = getGuessing();
                        align = this;
                        align.guess = "";
                        if (!bearingOk) {
                            nth = Math.toRadians(90.0d - f.getBearingDeg()) - oyaw;
                        } else if (!align.ok || wasGuessing) {
                            float bearingDeg2 = f.getBearingDeg();
                            if ((Float.isInfinite(bearingDeg2) || Float.isNaN(bearingDeg2)) ? false : true) {
                                float bearingAcc3 = f.getBearingAcc();
                                if (((Float.isInfinite(bearingAcc3) || Float.isNaN(bearingAcc3)) ? false : true) && f.getBearingAcc() < 90.0f) {
                                    d2 = Math.toRadians(90.0d - f.getBearingDeg());
                                    d = Double.valueOf(d2);
                                    Double hint = d;
                                    sh = OsmRoads.INSTANCE.streetHeading(e, n, hint);
                                    if (sh == null) {
                                        return;
                                    }
                                    nth = sh.getSecond().doubleValue() - oyaw;
                                    String name = sh.getFirst().getName();
                                    if (name.length() == 0) {
                                        name = sh.getFirst().getKind();
                                    }
                                    align.guess = name;
                                }
                            }
                            if (align.ok) {
                                d2 = align.th + oyaw;
                                d = Double.valueOf(d2);
                                Double hint2 = d;
                                sh = OsmRoads.INSTANCE.streetHeading(e, n, hint2);
                                if (sh == null) {
                                }
                            } else {
                                d = null;
                                Double hint22 = d;
                                sh = OsmRoads.INSTANCE.streetHeading(e, n, hint22);
                                if (sh == null) {
                                }
                            }
                        }
                        double ntx3 = e - ((Math.cos(nth) * ox) - (Math.sin(nth) * oy));
                        nty = n - ((Math.sin(nth) * ox) + (Math.cos(nth) * oy));
                        align.resid = f.getHAcc();
                        ntx = ntx3;
                    }
                    z = false;
                    bearingOk = z;
                    boolean wasGuessing2 = getGuessing();
                    align = this;
                    align.guess = "";
                    if (!bearingOk) {
                    }
                    double ntx32 = e - ((Math.cos(nth) * ox) - (Math.sin(nth) * oy));
                    nty = n - ((Math.sin(nth) * ox) + (Math.cos(nth) * oy));
                    align.resid = f.getHAcc();
                    ntx = ntx32;
                }
                if (!align.ok) {
                    align.th = nth;
                    align.tx = ntx;
                    align.ty = nty;
                    align.ok = true;
                    align.jumps++;
                    return;
                }
                double dth = Math.IEEEremainder(nth - align.th, 6.283185307179586d);
                boolean jump = Math.abs(dth) > 0.5d || Math.hypot(ntx - align.tx, nty - align.ty) > 40.0d;
                double k = jump ? 1.0d : 0.05d;
                if (jump) {
                    align.jumps++;
                }
                align.th += k * dth;
                double d3 = align.tx;
                double nth3 = align.tx;
                align.tx = d3 + ((ntx - nth3) * k);
                align.ty += (nty - align.ty) * k;
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
