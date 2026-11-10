package com.starpilot.starview;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.starpilot.starview.MapdMaps;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPInputStream;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.collections.SetsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.io.TextStreamsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.DoubleCompanionObject;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.ranges.RangesKt;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlin.text.CharsKt;
import kotlin.text.Charsets;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: MapdMaps.kt */
public final class MapdMaps {
    public static final double BOX = 0.25d;
    public static final int GROUP = 2;
    private static final String TAG = "StarView/Maps";
    private static final String URL = "https://map-data.pfeifer.dev/offline/%d/%d.tar.gz";
    private static volatile boolean cancel;
    private static File dir;
    private static Function0<Unit> onChange;
    private static SharedPreferences prefs;
    public static final MapdMaps INSTANCE = new MapdMaps();
    private static final String[] CLASSES = {"unknown", "motorway", "motorway_link", "trunk", "trunk_link", "primary", "primary_link", "secondary", "secondary_link", "tertiary", "tertiary_link", "unclassified", "residential", "living_street"};
    private static List<State> states = CollectionsKt.emptyList();
    private static final ConcurrentHashMap<Pair<Integer, Integer>, Long> doneCache = new ConcurrentHashMap<>();
    private static volatile String busy = "";
    private static volatile String lastError = "";

    /* renamed from: http$delegate, reason: from kotlin metadata */
    private static final Lazy http = LazyKt.lazy(new Function0() { // from class: com.starpilot.starview.MapdMaps$$ExternalSyntheticLambda4
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            OkHttpClient http_delegate$lambda$15;
            http_delegate$lambda$15 = MapdMaps.http_delegate$lambda$15();
            return http_delegate$lambda$15;
        }
    });
    private static final ExecutorService worker = Executors.newSingleThreadExecutor(new ThreadFactory() { // from class: com.starpilot.starview.MapdMaps$$ExternalSyntheticLambda5
        @Override // java.util.concurrent.ThreadFactory
        public final Thread newThread(Runnable runnable) {
            Thread worker$lambda$17;
            worker$lambda$17 = MapdMaps.worker$lambda$17(runnable);
            return worker$lambda$17;
        }
    });

    private MapdMaps() {
    }

    public final String[] getCLASSES() {
        return CLASSES;
    }

    /* compiled from: MapdMaps.kt */
    public static final class W {
        private final long id;
        private final String kind;
        private final int lanes;
        private final double[] lat;
        private final double[] lon;
        private final String name;
        private final boolean oneway;

        public W(long id, String name, String kind, int lanes, boolean oneway, double[] lat, double[] lon) {
            Intrinsics.checkNotNullParameter(name, "name");
            Intrinsics.checkNotNullParameter(kind, "kind");
            Intrinsics.checkNotNullParameter(lat, "lat");
            Intrinsics.checkNotNullParameter(lon, "lon");
            this.id = id;
            this.name = name;
            this.kind = kind;
            this.lanes = lanes;
            this.oneway = oneway;
            this.lat = lat;
            this.lon = lon;
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

        public final double[] getLat() {
            return this.lat;
        }

        public final double[] getLon() {
            return this.lon;
        }

        public final String getName() {
            return this.name;
        }

        public final boolean getOneway() {
            return this.oneway;
        }
    }

    /* compiled from: MapdMaps.kt */
    public static final class State {
        private final String code;
        private final double maxLat;
        private final double maxLon;
        private final double minLat;
        private final double minLon;
        private final String name;
        private final List<int[]> rows;

        public State(String code, String name, double minLat, double minLon, double maxLat, double maxLon, List<int[]> rows) {
            Intrinsics.checkNotNullParameter(code, "code");
            Intrinsics.checkNotNullParameter(name, "name");
            Intrinsics.checkNotNullParameter(rows, "rows");
            this.code = code;
            this.name = name;
            this.minLat = minLat;
            this.minLon = minLon;
            this.maxLat = maxLat;
            this.maxLon = maxLon;
            this.rows = rows;
        }

        public /* synthetic */ State(String str, String str2, double d, double d2, double d3, double d4, List list, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, str2, d, d2, d3, d4, (i & 64) != 0 ? CollectionsKt.emptyList() : list);
        }

        public final String getCode() {
            return this.code;
        }

        public final double getMaxLat() {
            return this.maxLat;
        }

        public final double getMaxLon() {
            return this.maxLon;
        }

        public final double getMinLat() {
            return this.minLat;
        }

        public final double getMinLon() {
            return this.minLon;
        }

        public final String getName() {
            return this.name;
        }

        public final List<int[]> getRows() {
            return this.rows;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v3, types: [T, byte[]] */
    public final byte[] unpack(byte[] src) {
        Intrinsics.checkNotNullParameter(src, "src");
        Ref.ObjectRef out = new Ref.ObjectRef();
        out.element = new byte[Math.max(64, src.length * 3)];
        Ref.IntRef o = new Ref.IntRef();
        int tag = 0;
        while (tag < src.length) {
            int i = tag + 1;
            int i2 = src[tag];
            int tag2 = i2 & 255;
            unpack$need(o, out, 8);
            int b = 0;
            while (true) {
                byte b2 = 0;
                if (b >= 8) {
                    int b3 = o.element;
                    o.element = b3 + 8;
                    switch (tag2) {
                        case 0:
                            int i3 = i + 1;
                            int n = (src[i] & 255) * 8;
                            unpack$need(o, out, n);
                            Arrays.fill((byte[]) out.element, o.element, o.element + n, (byte) 0);
                            o.element += n;
                            tag = i3;
                            break;
                        case 255:
                            int i4 = i + 1;
                            int n2 = (src[i] & 255) * 8;
                            unpack$need(o, out, n2);
                            System.arraycopy(src, i4, out.element, o.element, n2);
                            o.element += n2;
                            tag = i4 + n2;
                            break;
                        default:
                            tag = i;
                            break;
                    }
                } else {
                    byte[] bArr = (byte[]) out.element;
                    int i5 = o.element + b;
                    if (((1 << b) & tag2) != 0) {
                        b2 = src[i];
                        i++;
                    }
                    bArr[i5] = b2;
                    b++;
                }
            }
        }
        byte[] copyOf = Arrays.copyOf((byte[]) out.element, o.element);
        Intrinsics.checkNotNullExpressionValue(copyOf, "copyOf(...)");
        return copyOf;
    }

    /* JADX WARN: Type inference failed for: r0v4, types: [T, byte[], java.lang.Object] */
    private static final void unpack$need(Ref.IntRef o, Ref.ObjectRef<byte[]> objectRef, int n) {
        if (o.element + n > objectRef.element.length) {
            ?? copyOf = Arrays.copyOf(objectRef.element, Math.max(objectRef.element.length * 2, o.element + n));
            Intrinsics.checkNotNullExpressionValue(copyOf, "copyOf(...)");
            objectRef.element = copyOf;
        }
    }

    /* compiled from: MapdMaps.kt */
    private static final class Msg {
        private final ByteBuffer b;
        private final int[] seg;

        public Msg(byte[] raw) {
            Intrinsics.checkNotNullParameter(raw, "raw");
            ByteBuffer order = ByteBuffer.wrap(raw).order(ByteOrder.LITTLE_ENDIAN);
            Intrinsics.checkNotNullExpressionValue(order, "order(...)");
            this.b = order;
            int n = this.b.getInt(0) + 1;
            this.seg = new int[n];
            int w = (((n + 1) * 4) + 7) / 8;
            for (int k = 0; k < n; k++) {
                this.seg[k] = w;
                w += this.b.getInt((k * 4) + 4);
            }
        }

        public final ByteBuffer getB() {
            return this.b;
        }

        public final int[] getSeg() {
            return this.seg;
        }

        public final long w(int s, int i) {
            return this.b.getLong((this.seg[s] + i) * 8);
        }
    }

    /* compiled from: MapdMaps.kt */
    private static final class Ref {
        private final int at;
        private final long p;
        private final int s;

        public Ref(int s, long p, int at) {
            this.s = s;
            this.p = p;
            this.at = at;
        }

        public final int getAt() {
            return this.at;
        }

        public final long getP() {
            return this.p;
        }

        public final int getS() {
            return this.s;
        }
    }

    private final int off(long p) {
        return ((int) p) >> 2;
    }

    private final Ref follow(Msg m, int s, int at) {
        long p = m.w(s, at);
        if (p == 0) {
            return null;
        }
        if ((3 & p) != 2) {
            return new Ref(s, p, at + 1 + off(p));
        }
        int ts = (int) (p >>> 32);
        int pos = (int) ((p >>> 3) & 536870911);
        if ((4 & p) == 0) {
            long q = m.w(ts, pos);
            return new Ref(ts, q, pos + 1 + off(q));
        }
        long pad = m.w(ts, pos);
        long tag = m.w(ts, pos + 1);
        return new Ref((int) (pad >>> 32), tag, (int) ((pad >>> 3) & 536870911));
    }

    /* compiled from: MapdMaps.kt */
    private static final class Lst {
        private final int count;
        private final int dw;
        private final int pc;
        private final int s;
        private final int start;

        public Lst(int s, int start, int count, int dw, int pc) {
            this.s = s;
            this.start = start;
            this.count = count;
            this.dw = dw;
            this.pc = pc;
        }

        public final int getCount() {
            return this.count;
        }

        public final int getDw() {
            return this.dw;
        }

        public final int getPc() {
            return this.pc;
        }

        public final int getS() {
            return this.s;
        }

        public final int getStart() {
            return this.start;
        }
    }

    private final Lst compositeList(Msg m, Ref r) {
        if (r == null || (r.getP() & 3) != 1 || ((int) ((r.getP() >>> 32) & 7)) != 7) {
            return null;
        }
        long tag = m.w(r.getS(), r.getAt());
        return new Lst(r.getS(), r.getAt() + 1, off(tag), (int) ((tag >>> 32) & 65535), (int) (65535 & (tag >>> 48)));
    }

    private final String text(Msg m, Ref r) {
        int n;
        if (r == null || (r.getP() & 3) != 1 || ((int) ((r.getP() >>> 32) & 7)) != 2 || ((int) (r.getP() >>> 35)) - 1 <= 0) {
            return "";
        }
        byte[] a = new byte[n];
        int pos = (m.getSeg()[r.getS()] + r.getAt()) * 8;
        for (int k = 0; k < n; k++) {
            a[k] = m.getB().get(pos + k);
        }
        return new String(a, Charsets.UTF_8);
    }

    public final List<W> readBox(byte[] packed) {
        Ref root;
        int rdw;
        boolean z;
        MapdMaps mapdMaps = this;
        Intrinsics.checkNotNullParameter(packed, "packed");
        Msg m = new Msg(unpack(packed));
        Ref root2 = mapdMaps.follow(m, 0, 0);
        if (root2 == null) {
            return CollectionsKt.emptyList();
        }
        int rdw2 = (int) ((root2.getP() >>> 32) & 65535);
        Lst ways = mapdMaps.compositeList(m, mapdMaps.follow(m, root2.getS(), root2.getAt() + rdw2));
        if (ways == null) {
            return CollectionsKt.emptyList();
        }
        ArrayList out = new ArrayList(ways.getCount());
        int k = 0;
        int count = ways.getCount();
        while (k < count) {
            int e = ways.getStart() + ((ways.getDw() + ways.getPc()) * k);
            int s = ways.getS();
            int dw = ways.getDw();
            if (ways.getPc() < 3) {
                root = root2;
                rdw = rdw2;
            } else {
                Lst nodes = mapdMaps.compositeList(m, mapdMaps.follow(m, s, e + dw + 2));
                if (nodes == null) {
                    root = root2;
                    rdw = rdw2;
                } else if (nodes.getCount() < 2) {
                    root = root2;
                    rdw = rdw2;
                } else if (nodes.getDw() < 2) {
                    root = root2;
                    rdw = rdw2;
                } else {
                    double[] lat = new double[nodes.getCount()];
                    double[] lon = new double[nodes.getCount()];
                    int count2 = nodes.getCount();
                    int j = 0;
                    while (j < count2) {
                        int i = count2;
                        int ne = nodes.getStart() + ((nodes.getDw() + nodes.getPc()) * j);
                        DoubleCompanionObject doubleCompanionObject = DoubleCompanionObject.INSTANCE;
                        Ref root3 = root2;
                        lat[j] = Double.longBitsToDouble(m.w(nodes.getS(), ne));
                        DoubleCompanionObject doubleCompanionObject2 = DoubleCompanionObject.INSTANCE;
                        lon[j] = Double.longBitsToDouble(m.w(nodes.getS(), ne + 1));
                        j++;
                        count2 = i;
                        root2 = root3;
                        rdw2 = rdw2;
                    }
                    root = root2;
                    rdw = rdw2;
                    int base = (m.getSeg()[s] + e) * 8;
                    int lanes = dw > 5 ? m.getB().get(base + 40) & 255 : 0;
                    boolean oneway = dw > 5 && (m.getB().get(base + 41) & 1) != 0;
                    int cls = dw > 5 ? m.getB().getShort(base + 42) & 65535 : 0;
                    long id = dw > 9 ? m.w(s, e + 9) : 0L;
                    String text = mapdMaps.text(m, mapdMaps.follow(m, s, e + dw));
                    if (!(text.length() == 0)) {
                        z = true;
                    } else {
                        z = true;
                        text = INSTANCE.text(m, INSTANCE.follow(m, s, e + dw + 1));
                    }
                    String str = text;
                    String[] strArr = CLASSES;
                    out.add(new W(id, str, (cls < 0 || cls >= strArr.length) ? false : z ? strArr[cls] : "unknown", lanes, oneway, lat, lon));
                }
            }
            k++;
            mapdMaps = this;
            root2 = root;
            rdw2 = rdw;
        }
        return out;
    }

    public final List<State> getStates() {
        return states;
    }

    public final void init(Context ctx) {
        List<State> emptyList;
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        if (dir != null) {
            return;
        }
        File $this$init_u24lambda_u242 = new File(ctx.getFilesDir(), "mapd");
        $this$init_u24lambda_u242.mkdirs();
        dir = $this$init_u24lambda_u242;
        prefs = ctx.getApplicationContext().getSharedPreferences("starview_maps", 0);
        try {
            InputStream open = ctx.getAssets().open("maps/us_states.json");
            Intrinsics.checkNotNullExpressionValue(open, "open(...)");
            Reader inputStreamReader = new InputStreamReader(open, Charsets.UTF_8);
            final JSONObject o = new JSONObject(TextStreamsKt.readText(inputStreamReader instanceof BufferedReader ? (BufferedReader) inputStreamReader : new BufferedReader(inputStreamReader, 8192)));
            Iterator<String> keys = o.keys();
            Intrinsics.checkNotNullExpressionValue(keys, "keys(...)");
            Sequence $this$sortedBy$iv = SequencesKt.map(SequencesKt.asSequence(keys), new Function1() { // from class: com.starpilot.starview.MapdMaps$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    MapdMaps.State init$lambda$5;
                    init$lambda$5 = MapdMaps.init$lambda$5(o, (String) obj);
                    return init$lambda$5;
                }
            });
            emptyList = SequencesKt.toList(SequencesKt.sortedWith($this$sortedBy$iv, new Comparator() { // from class: com.starpilot.starview.MapdMaps$init$$inlined$sortedBy$1
                /* JADX WARN: Multi-variable type inference failed */
                @Override // java.util.Comparator
                public final int compare(T t, T t2) {
                    MapdMaps.State it = (MapdMaps.State) t;
                    MapdMaps.State it2 = (MapdMaps.State) t2;
                    return ComparisonsKt.compareValues(it.getName(), it2.getName());
                }
            }));
        } catch (Exception e) {
            Log.e("StarView/Maps", "states", e);
            emptyList = CollectionsKt.emptyList();
        }
        states = emptyList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final State init$lambda$5(JSONObject $o, String k) {
        List rows;
        JSONObject s = $o.getJSONObject(k);
        JSONObject b = s.getJSONObject("bounding_box");
        JSONArray a = s.optJSONArray("download_rows");
        if (a != null) {
            int i = 0;
            Iterable $this$map$iv = RangesKt.until(0, a.length());
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            Iterator<Integer> it = $this$map$iv.iterator();
            while (it.hasNext()) {
                int item$iv$iv = ((IntIterator) it).nextInt();
                JSONArray r = a.getJSONArray(item$iv$iv);
                destination$iv$iv.add(new int[]{r.getInt(i), r.getInt(1), r.getInt(2)});
                a = a;
                i = 0;
            }
            rows = (List) destination$iv$iv;
        } else {
            rows = CollectionsKt.emptyList();
        }
        Intrinsics.checkNotNull(k);
        String string = s.getString("full_name");
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        return new State(k, string, b.getDouble("min_lat"), b.getDouble("min_lon"), b.getDouble("max_lat"), b.getDouble("max_lon"), rows);
    }

    public final Set<String> selected() {
        Set<String> stringSet;
        SharedPreferences sharedPreferences = prefs;
        return (sharedPreferences == null || (stringSet = sharedPreferences.getStringSet("states", SetsKt.emptySet())) == null) ? SetsKt.emptySet() : stringSet;
    }

    private final void setSelected(Set<String> s) {
        SharedPreferences.Editor edit;
        SharedPreferences.Editor putStringSet;
        SharedPreferences sharedPreferences = prefs;
        if (sharedPreferences == null || (edit = sharedPreferences.edit()) == null || (putStringSet = edit.putStringSet("states", new HashSet(s))) == null) {
            return;
        }
        putStringSet.apply();
    }

    public final List<Pair<Integer, Integer>> groupsOf(State st) {
        Intrinsics.checkNotNullParameter(st, "st");
        int i = 2;
        if (st.getRows().isEmpty()) {
            double d = 2;
            int la0 = ((int) Math.floor(st.getMinLat() / d)) * 2;
            int lo0 = ((int) Math.floor(st.getMinLon() / d)) * 2;
            int la1 = ((int) Math.floor(st.getMaxLat() / d)) * 2;
            int lo1 = ((int) Math.floor(st.getMaxLon() / d)) * 2;
            if (st.getMaxLat() > la1) {
                la1 += 2;
            }
            if (st.getMaxLon() > lo1) {
                lo1 += 2;
            }
            ArrayList out = new ArrayList();
            for (int a = la0; a < la1; a += 2) {
                for (int o = lo0; o < lo1; o += 2) {
                    out.add(TuplesKt.to(Integer.valueOf(a), Integer.valueOf(o)));
                }
            }
            return out;
        }
        Iterable $this$flatMap$iv = st.getRows();
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$flatMap$iv) {
            int[] r = (int[]) element$iv$iv;
            Iterable $this$map$iv = RangesKt.step(RangesKt.until(r[1], r[i]), i);
            Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            Iterator<Integer> it = $this$map$iv.iterator();
            while (it.hasNext()) {
                int item$iv$iv = ((IntIterator) it).nextInt();
                destination$iv$iv2.add(TuplesKt.to(Integer.valueOf(r[0]), Integer.valueOf(item$iv$iv)));
                $this$flatMap$iv = $this$flatMap$iv;
            }
            Iterable $this$flatMap$iv2 = $this$flatMap$iv;
            Iterable list$iv$iv = (List) destination$iv$iv2;
            CollectionsKt.addAll(destination$iv$iv, list$iv$iv);
            $this$flatMap$iv = $this$flatMap$iv2;
            i = 2;
        }
        return (List) destination$iv$iv;
    }

    private final File groupDir(Pair<Integer, Integer> g) {
        return new File(dir, g.getFirst() + "/" + g.getSecond());
    }

    public final long groupDone(Pair<Integer, Integer> g) {
        long j;
        Intrinsics.checkNotNullParameter(g, "g");
        ConcurrentMap $this$getOrPut$iv = doneCache;
        Long l = $this$getOrPut$iv.get(g);
        if (l == null) {
            try {
                j = Long.parseLong(StringsKt.trim((CharSequence) FilesKt.readText$default(new File(INSTANCE.groupDir(g), ".done"), null, 1, null)).toString());
            } catch (Exception e) {
                j = 0;
            }
            l = Long.valueOf(j);
            Long putIfAbsent = $this$getOrPut$iv.putIfAbsent(g, l);
            if (putIfAbsent != null) {
                l = putIfAbsent;
            }
        }
        Intrinsics.checkNotNullExpressionValue(l, "getOrPut(...)");
        return l.longValue();
    }

    public final long stateBytes(State st) {
        long j;
        Intrinsics.checkNotNullParameter(st, "st");
        Iterator<T> it = groupsOf(st).iterator();
        long j2 = 0;
        while (it.hasNext()) {
            Pair g = (Pair) it.next();
            File[] listFiles = INSTANCE.groupDir(g).listFiles();
            if (listFiles != null) {
                j = 0;
                for (File it2 : listFiles) {
                    j += it2.length();
                }
            } else {
                j = 0;
            }
            j2 += j;
        }
        return j2;
    }

    public final Pair<Integer, Long> stateStatus(State st) {
        int count$iv;
        int i;
        Intrinsics.checkNotNullParameter(st, "st");
        Iterable $this$map$iv = groupsOf(st);
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        for (Object item$iv$iv : $this$map$iv) {
            Pair it = (Pair) item$iv$iv;
            destination$iv$iv.add(Long.valueOf(INSTANCE.groupDone(it)));
        }
        List t = (List) destination$iv$iv;
        List $this$count$iv = t;
        if (($this$count$iv instanceof Collection) && $this$count$iv.isEmpty()) {
            count$iv = 0;
        } else {
            count$iv = 0;
            for (Object element$iv : $this$count$iv) {
                long it2 = ((Number) element$iv).longValue();
                if ((it2 > 0) && (count$iv = count$iv + 1) < 0) {
                    CollectionsKt.throwCountOverflow();
                }
            }
        }
        int n = count$iv;
        if (n == 0) {
            i = 0;
        } else {
            i = n == t.size() ? 2 : 1;
        }
        Integer valueOf = Integer.valueOf(i);
        List $this$filter$iv = t;
        Collection destination$iv$iv2 = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            long it3 = ((Number) element$iv$iv).longValue();
            if (it3 > 0) {
                destination$iv$iv2.add(element$iv$iv);
            }
        }
        Long l = (Long) CollectionsKt.minOrNull(destination$iv$iv2);
        return new Pair<>(valueOf, Long.valueOf(l != null ? l.longValue() : 0L));
    }

    public final Pair<Boolean, File> boxFileFor(double lat, double lon) {
        double d = 2;
        Pair g = new Pair(Integer.valueOf(((int) Math.floor(lat / d)) * 2), Integer.valueOf(((int) Math.floor(lon / d)) * 2));
        if (dir == null || groupDone(g) == 0) {
            return new Pair<>(false, null);
        }
        double la = Math.floor(lat / 0.25d) * 0.25d;
        double lo = Math.floor(lon / 0.25d) * 0.25d;
        File groupDir = groupDir(g);
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        String format = String.format(Locale.US, "%f_%f_%f_%f", Arrays.copyOf(new Object[]{Double.valueOf(la), Double.valueOf(lo), Double.valueOf(la + 0.25d), Double.valueOf(0.25d + lo)}, 4));
        Intrinsics.checkNotNullExpressionValue(format, "format(...)");
        File f = new File(groupDir, format);
        return new Pair<>(true, f.isFile() ? f : null);
    }

    public final String getBusy() {
        return busy;
    }

    public final String getLastError() {
        return lastError;
    }

    public final Function0<Unit> getOnChange() {
        return onChange;
    }

    public final void setOnChange(Function0<Unit> function0) {
        onChange = function0;
    }

    private final OkHttpClient getHttp() {
        return (OkHttpClient) http.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final OkHttpClient http_delegate$lambda$15() {
        return new OkHttpClient.Builder().connectTimeout(15L, TimeUnit.SECONDS).readTimeout(60L, TimeUnit.SECONDS).build();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Thread worker$lambda$17(Runnable r) {
        Thread $this$worker_u24lambda_u2417_u24lambda_u2416 = new Thread(r, "starview-maps");
        $this$worker_u24lambda_u2417_u24lambda_u2416.setDaemon(true);
        $this$worker_u24lambda_u2417_u24lambda_u2416.setPriority(1);
        return $this$worker_u24lambda_u2417_u24lambda_u2416;
    }

    private final void changed() {
        try {
            Function0<Unit> function0 = onChange;
            if (function0 != null) {
                function0.invoke();
            }
        } catch (Exception e) {
        }
    }

    public static /* synthetic */ void download$default(MapdMaps mapdMaps, State state, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        mapdMaps.download(state, z);
    }

    public final void download(final State st, final boolean force) {
        Intrinsics.checkNotNullParameter(st, "st");
        setSelected(SetsKt.plus(selected(), st.getCode()));
        worker.execute(new Runnable() { // from class: com.starpilot.starview.MapdMaps$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                MapdMaps.download$lambda$20(MapdMaps.State.this, force);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void download$lambda$20(final State $st, boolean $force) {
        int i = 0;
        cancel = false;
        lastError = "";
        Iterable $this$filter$iv = INSTANCE.groupsOf($st);
        Collection destination$iv$iv = new ArrayList();
        Iterator it = $this$filter$iv.iterator();
        while (true) {
            boolean z = true;
            if (!it.hasNext()) {
                break;
            }
            Object element$iv$iv = it.next();
            Pair it2 = (Pair) element$iv$iv;
            if (!$force && INSTANCE.groupDone(it2) != 0) {
                z = false;
            }
            if (z) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        final List<Pair> gs = (List) destination$iv$iv;
        for (Pair g : gs) {
            final int i2 = i;
            int i3 = i + 1;
            if (cancel) {
                break;
            }
            busy = $st.getName() + ": area " + (i2 + 1) + " of " + gs.size();
            INSTANCE.changed();
            try {
                INSTANCE.fetchGroup(g, new Function1() { // from class: com.starpilot.starview.MapdMaps$$ExternalSyntheticLambda2
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        Unit download$lambda$20$lambda$19;
                        download$lambda$20$lambda$19 = MapdMaps.download$lambda$20$lambda$19(MapdMaps.State.this, i2, gs, ((Double) obj).doubleValue());
                        return download$lambda$20$lambda$19;
                    }
                });
            } catch (Exception e) {
                Log.w("StarView/Maps", "group " + g, e);
                String name = $st.getName();
                String message = e.getMessage();
                lastError = name + ": " + (message != null ? StringsKt.take(message, 60) : null);
            }
            i = i3;
        }
        busy = "";
        INSTANCE.changed();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit download$lambda$20$lambda$19(State $st, int $i, List $gs, double mb) {
        String name = $st.getName();
        int size = $gs.size();
        String format = String.format("%.0f", Arrays.copyOf(new Object[]{Double.valueOf(mb)}, 1));
        Intrinsics.checkNotNullExpressionValue(format, "format(...)");
        busy = name + ": area " + ($i + 1) + " of " + size + ", " + format + " MB";
        INSTANCE.changed();
        return Unit.INSTANCE;
    }

    public final void cancelAll() {
        cancel = true;
    }

    public final void delete(final State st) {
        Intrinsics.checkNotNullParameter(st, "st");
        final Set keep = SetsKt.minus(selected(), st.getCode());
        setSelected(keep);
        worker.execute(new Runnable() { // from class: com.starpilot.starview.MapdMaps$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                MapdMaps.delete$lambda$23(MapdMaps.State.this, keep);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void delete$lambda$23(State $st, Set $keep) {
        Iterable $this$filter$iv = states;
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            State it = (State) element$iv$iv;
            if ($keep.contains(it.getCode())) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        Iterable<State> $this$flatMap$iv = (List) destination$iv$iv;
        Collection destination$iv$iv2 = new ArrayList();
        for (State it2 : $this$flatMap$iv) {
            Iterable list$iv$iv = INSTANCE.groupsOf(it2);
            CollectionsKt.addAll(destination$iv$iv2, list$iv$iv);
        }
        Set needed = CollectionsKt.toSet((List) destination$iv$iv2);
        for (Pair g : INSTANCE.groupsOf($st)) {
            if (!needed.contains(g)) {
                FilesKt.deleteRecursively(INSTANCE.groupDir(g));
                doneCache.remove(g);
            }
        }
        INSTANCE.changed();
    }

    private final void fetchGroup(Pair<Integer, Integer> g, final Function1<? super Double, Unit> progress) {
        File target = groupDir(g);
        File tmp = new File(dir, "tmp_" + g.getFirst() + "_" + g.getSecond());
        FilesKt.deleteRecursively(tmp);
        tmp.mkdirs();
        Request.Builder builder = new Request.Builder();
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        String format = String.format(Locale.US, "https://map-data.pfeifer.dev/offline/%d/%d.tar.gz", Arrays.copyOf(new Object[]{g.getFirst(), g.getSecond()}, 2));
        Intrinsics.checkNotNullExpressionValue(format, "format(...)");
        Request req = builder.url(format).header("User-Agent", "StarView (openpilot tablet display)").build();
        Response execute = getHttp().newCall(req).execute();
        try {
            Response r = execute;
            if (r.code() == 404) {
                FilesKt.deleteRecursively(target);
                target.mkdirs();
                FilesKt.writeText$default(new File(target, ".done"), String.valueOf(System.currentTimeMillis()), null, 2, null);
                FilesKt.deleteRecursively(tmp);
                doneCache.remove(g);
                CloseableKt.closeFinally(execute, null);
                return;
            }
            try {
                if (!r.isSuccessful()) {
                    throw new IOException("HTTP " + r.code());
                }
                ResponseBody body = r.body();
                Intrinsics.checkNotNull(body);
                final InputStream byteStream = body.byteStream();
                INSTANCE.untar$app_debug(new GZIPInputStream(new FilterInputStream(byteStream) { // from class: com.starpilot.starview.MapdMaps$fetchGroup$1$counted$1
                    private long last;
                    private long n;

                    public final long getLast() {
                        return this.last;
                    }

                    public final long getN() {
                        return this.n;
                    }

                    public final void setLast(long j) {
                        this.last = j;
                    }

                    public final void setN(long j) {
                        this.n = j;
                    }

                    @Override // java.io.FilterInputStream, java.io.InputStream
                    public int read(byte[] b, int off, int len) {
                        Intrinsics.checkNotNullParameter(b, "b");
                        int k = super.read(b, off, len);
                        if (k > 0) {
                            this.n += k;
                            if (this.n - this.last > 2000000) {
                                this.last = this.n;
                                progress.invoke(Double.valueOf(this.n / 1000000.0d));
                            }
                        }
                        return k;
                    }
                }, 65536), tmp);
                Unit unit = Unit.INSTANCE;
                CloseableKt.closeFinally(execute, null);
                FilesKt.writeText$default(new File(tmp, ".done"), String.valueOf(System.currentTimeMillis()), null, 2, null);
                File parentFile = target.getParentFile();
                if (parentFile != null) {
                    parentFile.mkdirs();
                }
                FilesKt.deleteRecursively(target);
                doneCache.remove(g);
                if (tmp.renameTo(target)) {
                    doneCache.remove(g);
                    return;
                }
                throw new IOException("could not store the map files");
            } catch (Throwable th) {
                th = th;
                Throwable th2 = th;
                try {
                    throw th2;
                } catch (Throwable th3) {
                    CloseableKt.closeFinally(execute, th2);
                    throw th3;
                }
            }
        } catch (Throwable th4) {
            th = th4;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x00f3  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0115 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:61:0x014a  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0107  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void untar$app_debug(InputStream inp, File into) {
        byte[] $this$all$iv;
        boolean z;
        BufferedOutputStream out;
        long left;
        int pad;
        Intrinsics.checkNotNullParameter(inp, "inp");
        Intrinsics.checkNotNullParameter(into, "into");
        int i = 512;
        byte[] h = new byte[512];
        byte[] buf = new byte[65536];
        while (untar$readFully(inp, h, i)) {
            int length = h.length;
            int i2 = 0;
            while (true) {
                if (i2 < length) {
                    byte element$iv = h[i2];
                    byte it = element$iv == 0 ? (byte) 1 : (byte) 0;
                    if (it == 0) {
                        $this$all$iv = null;
                        break;
                    }
                    i2++;
                } else {
                    $this$all$iv = 1;
                    break;
                }
            }
            if ($this$all$iv == null) {
                String name = StringsKt.trimEnd(new String(h, 0, 100, Charsets.US_ASCII), 0);
                String prefix = StringsKt.trimEnd(new String(h, 345, 155, Charsets.US_ASCII), 0);
                String trim = StringsKt.trim(new String(h, 124, 12, Charsets.US_ASCII), 0, ' ');
                if (trim.length() == 0) {
                    trim = "0";
                }
                long size = Long.parseLong(trim, CharsKt.checkRadix(8));
                char type = (char) h[156];
                String full = prefix.length() > 0 ? prefix + "/" + name : name;
                String base = StringsKt.substringAfterLast$default(full, '/', (String) null, 2, (Object) null);
                if (type == '0' || type == 0) {
                    if ((base.length() > 0) && !StringsKt.startsWith$default(base, ".", false, 2, (Object) null) && new Regex("[-0-9._]+").matches(base)) {
                        z = true;
                        boolean keep = z;
                        out = !keep ? new BufferedOutputStream(new FileOutputStream(new File(into, base))) : null;
                        String name2 = name;
                        left = size;
                        while (left > 0) {
                            try {
                                boolean keep2 = keep;
                                String name3 = name2;
                                try {
                                    int k = inp.read(buf, 0, (int) Math.min(buf.length, left));
                                    if (k < 0) {
                                        throw new EOFException("tar cut short");
                                    }
                                    if (out != null) {
                                        out.write(buf, 0, k);
                                    }
                                    left -= k;
                                    keep = keep2;
                                    name2 = name3;
                                } catch (Throwable th) {
                                    th = th;
                                    if (out != null) {
                                        out.close();
                                    }
                                    throw th;
                                }
                            } catch (Throwable th2) {
                                th = th2;
                            }
                        }
                        if (out != null) {
                            out.close();
                        }
                        i = 512;
                        long j = 512;
                        pad = (int) ((j - (size % j)) % j);
                        if (pad <= 0 && !untar$readFully(inp, h, pad)) {
                            return;
                        }
                    }
                }
                z = false;
                boolean keep3 = z;
                out = !keep3 ? new BufferedOutputStream(new FileOutputStream(new File(into, base))) : null;
                String name22 = name;
                left = size;
                while (left > 0) {
                }
                if (out != null) {
                }
                i = 512;
                long j2 = 512;
                pad = (int) ((j2 - (size % j2)) % j2);
                if (pad <= 0) {
                }
            } else {
                return;
            }
        }
    }

    private static final boolean untar$readFully(InputStream $inp, byte[] a, int n) {
        int o = 0;
        while (o < n) {
            int k = $inp.read(a, o, n - o);
            if (k < 0) {
                return false;
            }
            o += k;
        }
        return true;
    }
}
