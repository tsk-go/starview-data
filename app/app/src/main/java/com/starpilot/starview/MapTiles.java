package com.starpilot.starview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.LruCache;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.Triple;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/* compiled from: MapTiles.kt */
public final class MapTiles {
    private static final long DISK_MAX = 262144000;
    private static final long STREET_TTL_MS = 604800000;
    private static final long TRAFFIC_TTL_MS = 900000;
    private final ThreadPoolExecutor ahead;
    private final File dir;
    private final AtomicInteger direct;
    private volatile long directDownUntil;
    private volatile String directKey;
    private final ConcurrentHashMap<String, Long> failedUntil;
    private volatile int failures;
    private volatile String host;
    private final OkHttpClient http;
    private volatile String lastError;
    private final ConcurrentHashMap<String, Long> madeAt;
    private final OkHttpClient mapbox;
    private final MapTiles$mem$1 mem;
    private final Function0<Unit> onLoaded;
    private final ConcurrentHashMap.KeySetView<String, Boolean> pending;
    private final ThreadPoolExecutor pool;
    private volatile Set<String> wanted;
    public static final Companion Companion = new Companion(null);
    private static final Map<String, String> MAPBOX_STYLES = MapsKt.mapOf(TuplesKt.to("light", "mapbox/light-v11"), TuplesKt.to("dark", "mapbox/dark-v11"), TuplesKt.to("trafficLight", "mapbox/navigation-day-v1"), TuplesKt.to("trafficDark", "mapbox/navigation-night-v1"));

    /* JADX WARN: Type inference failed for: r2v6, types: [com.starpilot.starview.MapTiles$mem$1] */
    public MapTiles(Context ctx, Function0<Unit> onLoaded) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(onLoaded, "onLoaded");
        this.onLoaded = onLoaded;
        this.wanted = SetsKt.emptySet();
        this.lastError = "";
        this.direct = new AtomicInteger();
        this.dir = new File(ctx.getCacheDir(), "maptiles");
        this.mem = new LruCache<String, Bitmap>() { // from class: com.starpilot.starview.MapTiles$mem$1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.util.LruCache
            public int sizeOf(String key, Bitmap value) {
                Intrinsics.checkNotNullParameter(key, "key");
                Intrinsics.checkNotNullParameter(value, "value");
                return value.getByteCount();
            }
        };
        this.pending = ConcurrentHashMap.newKeySet();
        this.failedUntil = new ConcurrentHashMap<>();
        this.madeAt = new ConcurrentHashMap<>();
        this.http = new OkHttpClient.Builder().addInterceptor(Link.INSTANCE.getAuthInterceptor()).connectTimeout(4L, TimeUnit.SECONDS).readTimeout(10L, TimeUnit.SECONDS).build();
        this.mapbox = new OkHttpClient.Builder().connectTimeout(3L, TimeUnit.SECONDS).readTimeout(8L, TimeUnit.SECONDS).build();
        this.pool = new ThreadPoolExecutor(6, 6, 30L, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>() { // from class: com.starpilot.starview.MapTiles$pool$1
            @Override // java.util.concurrent.LinkedBlockingDeque, java.util.AbstractCollection, java.util.Collection, java.util.concurrent.BlockingDeque, java.util.concurrent.BlockingQueue, java.util.Deque
            public final /* bridge */ boolean contains(Object element) {
                if (element == null ? true : element instanceof Runnable) {
                    return contains((Runnable) element);
                }
                return false;
            }

            public /* bridge */ boolean contains(Runnable p0) {
                return super.contains((Object) p0);
            }

            public /* bridge */ int getSize() {
                return super.size();
            }

            @Override // java.util.concurrent.LinkedBlockingDeque, java.util.AbstractCollection, java.util.Collection, java.util.concurrent.BlockingDeque, java.util.concurrent.BlockingQueue, java.util.Deque
            public final /* bridge */ boolean remove(Object element) {
                if (element == null ? true : element instanceof Runnable) {
                    return remove((Runnable) element);
                }
                return false;
            }

            public /* bridge */ boolean remove(Runnable p0) {
                return super.remove((Object) p0);
            }

            @Override // java.util.concurrent.LinkedBlockingDeque, java.util.AbstractCollection, java.util.Collection, java.util.concurrent.BlockingDeque, java.util.Deque
            public final /* bridge */ int size() {
                return getSize();
            }

            @Override // java.util.concurrent.LinkedBlockingDeque, java.util.Queue, java.util.concurrent.BlockingDeque, java.util.concurrent.BlockingQueue, java.util.Deque
            public boolean offer(Runnable e) {
                Intrinsics.checkNotNullParameter(e, "e");
                return offerFirst(e);
            }
        });
        this.ahead = new ThreadPoolExecutor(2, 2, 30L, TimeUnit.SECONDS, new LinkedBlockingDeque(400));
        this.pool.allowCoreThreadTimeOut(true);
        this.ahead.allowCoreThreadTimeOut(true);
        Thread $this$_init__u24lambda_u241 = new Thread(new Runnable() { // from class: com.starpilot.starview.MapTiles$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                MapTiles.this.prune();
            }
        });
        $this$_init__u24lambda_u241.setDaemon(true);
        $this$_init__u24lambda_u241.setPriority(1);
        $this$_init__u24lambda_u241.start();
    }

    public final String getHost() {
        return this.host;
    }

    public final void setHost(String str) {
        this.host = str;
    }

    public final Set<String> getWanted() {
        return this.wanted;
    }

    public final void setWanted(Set<String> set) {
        Intrinsics.checkNotNullParameter(set, "<set-?>");
        this.wanted = set;
    }

    public final int getFailures() {
        return this.failures;
    }

    public final String getLastError() {
        return this.lastError;
    }

    public final String getDirectKey() {
        return this.directKey;
    }

    public final void setDirectKey(String str) {
        this.directKey = str;
    }

    public final int takeDirectCount() {
        return this.direct.getAndSet(0);
    }

    /* compiled from: MapTiles.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final String key(String style, int z, int x, int y) {
            Intrinsics.checkNotNullParameter(style, "style");
            return style + "/" + z + "/" + x + "/" + y;
        }

        public final Map<String, String> getMAPBOX_STYLES() {
            return MapTiles.MAPBOX_STYLES;
        }
    }

    public final Bitmap peek(String style, int z, int x, int y) {
        Intrinsics.checkNotNullParameter(style, "style");
        return get(Companion.key(style, z, x, y));
    }

    /* JADX WARN: Code restructure failed: missing block: B:9:0x003c, code lost:
    
        if ((r1 - (r3 != null ? r3.longValue() : 0)) <= 900000) goto L12;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Bitmap get(String style, int z, int x, int y) {
        Intrinsics.checkNotNullParameter(style, "style");
        String k = Companion.key(style, z, x, y);
        Bitmap m = get(k);
        if (m != null) {
            if (StringsKt.startsWith(style, "traffic", false)) {
                long currentTimeMillis = System.currentTimeMillis();
                Long l = this.madeAt.get(k);
            }
            return m;
        }
        request(style, z, x, y, k);
        return m;
    }

    private final File file(String style, int z, int x, int y) {
        return new File(this.dir, style + "/" + z + "/" + x + "_" + y + ".png");
    }

    public final void prefetch(final String style, Collection<Triple<Integer, Integer, Integer>> tiles) {
        Intrinsics.checkNotNullParameter(style, "style");
        Intrinsics.checkNotNullParameter(tiles, "tiles");
        Iterator<Triple<Integer, Integer, Integer>> it = tiles.iterator();
        while (it.hasNext()) {
            Triple<Integer, Integer, Integer> next = it.next();
            final int z = next.component1().intValue();
            final int x = next.component2().intValue();
            final int y = next.component3().intValue();
            final String k = Companion.key(style, z, x, y);
            if (get(k) == null) {
                Long l = this.failedUntil.get(k);
                if ((l != null ? l.longValue() : 0L) <= System.currentTimeMillis()) {
                    if (this.pending.add(k)) {
                        try {
                            Iterator<Triple<Integer, Integer, Integer>> it2 = it;
                            this.ahead.execute(new Runnable() { // from class: com.starpilot.starview.MapTiles$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    MapTiles.this.load(style, z, x, y, k, true);
                                }
                            });
                            it = it2;
                        } catch (RejectedExecutionException e) {
                            this.pending.remove(k);
                            return;
                        }
                    } else {
                        continue;
                    }
                }
            }
        }
    }

    private final void request(final String style, final int z, final int x, final int y, final String k) {
        Long l = this.failedUntil.get(k);
        if ((l != null ? l.longValue() : 0L) > System.currentTimeMillis() || !this.pending.add(k)) {
            return;
        }
        this.pool.execute(new Runnable() { // from class: com.starpilot.starview.MapTiles$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                MapTiles.this.load(style, z, x, y, k, false);
            }
        });
    }

    private final byte[] fromMapbox(String style, int z, int x, int y) {
        String id;
        byte[] bArr;
        ResponseBody body;
        String key = this.directKey;
        if (key == null || (id = MAPBOX_STYLES.get(style)) == null || System.currentTimeMillis() < this.directDownUntil) {
            return null;
        }
        try {
            Response execute = this.mapbox.newCall(new Request.Builder().url("https://api.mapbox.com/styles/v1/" + id + "/tiles/512/" + z + "/" + x + "/" + y + "?access_token=" + key).build()).execute();
            try {
                Response r = execute;
                if (r.code() != 401 && r.code() != 403 && r.code() != 429) {
                    if (!r.isSuccessful() || (body = r.body()) == null || (bArr = body.bytes()) == null) {
                        bArr = null;
                    } else {
                        this.direct.incrementAndGet();
                    }
                    CloseableKt.closeFinally(execute, null);
                    return bArr;
                }
                this.directDownUntil = System.currentTimeMillis() + 300000;
                bArr = null;
                CloseableKt.closeFinally(execute, null);
                return bArr;
            } finally {
            }
        } catch (IOException e) {
            this.directDownUntil = System.currentTimeMillis() + 60000;
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:112:0x025a A[Catch: all -> 0x027a, TryCatch #3 {all -> 0x027a, blocks: (B:34:0x00bc, B:47:0x0136, B:51:0x014a, B:54:0x016c, B:56:0x0171, B:58:0x0178, B:65:0x0189, B:68:0x0191, B:70:0x0197, B:71:0x019a, B:76:0x01c6, B:78:0x01ce, B:81:0x01f1, B:83:0x0209, B:85:0x020f, B:86:0x0212, B:88:0x0237, B:92:0x023f, B:107:0x0141, B:108:0x0144, B:110:0x024e, B:112:0x025a, B:113:0x025e), top: B:2:0x0008 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void load(String style, int z, int x, int y, String k, boolean prefetch) {
        String message;
        byte[] bytes;
        String str;
        String string;
        Bitmap it;
        try {
            if (!prefetch) {
                try {
                    if (!this.wanted.contains(k)) {
                        this.pending.remove(k);
                        return;
                    }
                } catch (Exception e) {
                    e = e;
                    this.failures++;
                    message = e.getMessage();
                    if (message == null) {
                    }
                    this.lastError = message;
                    this.failedUntil.put(k, Long.valueOf(System.currentTimeMillis() + 15000));
                    this.pending.remove(k);
                } catch (Throwable th) {
                    th = th;
                    this.pending.remove(k);
                    throw th;
                }
            }
            File f = file(style, z, x, y);
            long ttl = StringsKt.startsWith(style, "traffic", false) ? 900000L : 604800000L;
            boolean fresh = false;
            if (f.exists()) {
                if (!prefetch && get(k) == null && (it = BitmapFactory.decodeFile(f.getPath())) != null) {
                    put(k, it);
                    this.madeAt.put(k, Long.valueOf(f.lastModified()));
                    this.onLoaded.invoke();
                }
                fresh = System.currentTimeMillis() - f.lastModified() < ttl;
            }
            if (fresh) {
                this.pending.remove(k);
                return;
            }
            byte[] fromMapbox = fromMapbox(style, z, x, y);
            if (fromMapbox == null) {
                MapTiles $this$load_u24lambda_u248 = this;
                String h = $this$load_u24lambda_u248.host;
                if (h == null) {
                    this.pending.remove(k);
                    return;
                }
                try {
                    Response execute = $this$load_u24lambda_u248.http.newCall(new Request.Builder().url("http://" + h + ":8090/nav/tile/" + style + "/" + z + "/" + x + "/" + y + ".png").build()).execute();
                    try {
                        Response r = execute;
                        if (r.isSuccessful()) {
                            ResponseBody body = r.body();
                            if (body != null) {
                                bytes = body.bytes();
                                CloseableKt.closeFinally(execute, null);
                                fromMapbox = bytes;
                            }
                        } else {
                            ResponseBody body2 = r.body();
                            if (body2 == null || (string = body2.string()) == null || (str = StringsKt.take(string, 200)) == null) {
                                str = "HTTP " + r.code();
                            }
                            $this$load_u24lambda_u248.lastError = str;
                        }
                        bytes = null;
                        CloseableKt.closeFinally(execute, null);
                        fromMapbox = bytes;
                    } finally {
                    }
                } catch (Exception e2) {
                    e = e2;
                    this.failures++;
                    message = e.getMessage();
                    if (message == null) {
                        message = e.toString();
                    }
                    this.lastError = message;
                    this.failedUntil.put(k, Long.valueOf(System.currentTimeMillis() + 15000));
                    this.pending.remove(k);
                }
            }
            byte[] bytes2 = fromMapbox;
            if (bytes2 == null) {
                this.failures++;
                this.failedUntil.put(k, Long.valueOf(System.currentTimeMillis() + 30000));
                this.pending.remove(k);
                return;
            }
            boolean png = bytes2.length > 8 && bytes2[0] == -119 && bytes2[1] == 80;
            if (prefetch && png && !this.wanted.contains(k)) {
                try {
                    File parentFile = f.getParentFile();
                    if (parentFile != null) {
                        parentFile.mkdirs();
                    }
                    File tmp = new File(f.getPath() + ".tmp");
                    FilesKt.writeBytes(tmp, bytes2);
                    tmp.renameTo(f);
                } catch (Exception e3) {
                }
                this.pending.remove(k);
                return;
            }
            Bitmap bmp = BitmapFactory.decodeByteArray(bytes2, 0, bytes2.length);
            if (bmp == null) {
                this.failures++;
                this.failedUntil.put(k, Long.valueOf(System.currentTimeMillis() + 60000));
                this.pending.remove(k);
                return;
            }
            put(k, bmp);
            this.madeAt.put(k, Long.valueOf(System.currentTimeMillis()));
            this.lastError = "";
            try {
                File parentFile2 = f.getParentFile();
                if (parentFile2 != null) {
                    parentFile2.mkdirs();
                }
                File tmp2 = new File(f.getPath() + ".tmp");
                FilesKt.writeBytes(tmp2, bytes2);
                tmp2.renameTo(f);
            } catch (Exception e4) {
            }
            if (!prefetch || this.wanted.contains(k)) {
                this.onLoaded.invoke();
            }
            this.pending.remove(k);
        } catch (Throwable th2) {
            th = th2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void prune() {
        try {
            List<File> files = SequencesKt.toMutableList(SequencesKt.filter(FilesKt.walkTopDown(this.dir), new Function1() { // from class: com.starpilot.starview.MapTiles$$ExternalSyntheticLambda2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    boolean prune$lambda$9;
                    prune$lambda$9 = MapTiles.prune$lambda$9((File) obj);
                    return Boolean.valueOf(prune$lambda$9);
                }
            }));
            long old = System.currentTimeMillis() - 2592000000L;
            List $this$filter$iv = files;
            Collection destination$iv$iv = new ArrayList();
            for (Object element$iv$iv : $this$filter$iv) {
                File it = (File) element$iv$iv;
                if (it.lastModified() < old) {
                    destination$iv$iv.add(element$iv$iv);
                }
            }
            Iterable $this$forEach$iv = (List) destination$iv$iv;
            for (Object element$iv : $this$forEach$iv) {
                File it2 = (File) element$iv;
                it2.delete();
            }
            CollectionsKt.removeAll(files, new Function1() { // from class: com.starpilot.starview.MapTiles$$ExternalSyntheticLambda3
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    boolean prune$lambda$12;
                    prune$lambda$12 = MapTiles.prune$lambda$12((File) obj);
                    return Boolean.valueOf(prune$lambda$12);
                }
            });
            long j = 0;
            for (File it3 : files) {
                j += it3.length();
            }
            long total = j;
            if (total > 262144000) {
                List $this$sortedBy$iv = files;
                for (File f : CollectionsKt.sortedWith($this$sortedBy$iv, new Comparator() { // from class: com.starpilot.starview.MapTiles$prune$$inlined$sortedBy$1
                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // java.util.Comparator
                    public final int compare(T t, T t2) {
                        File it4 = (File) t;
                        File it5 = (File) t2;
                        return ComparisonsKt.compareValues(Long.valueOf(it4.lastModified()), Long.valueOf(it5.lastModified()));
                    }
                })) {
                    if (total <= 2.097152E8d) {
                        return;
                    }
                    total -= f.length();
                    f.delete();
                }
            }
        } catch (Exception e) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean prune$lambda$9(File it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return it.isFile();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean prune$lambda$12(File it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return !it.exists();
    }

    public final void clearMemory() {
        evictAll();
    }
}
