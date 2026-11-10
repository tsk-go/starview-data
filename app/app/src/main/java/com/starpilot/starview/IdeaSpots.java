package com.starpilot.starview;

import android.content.Context;
import android.os.Environment;
import com.starpilot.starview.IdeaSpots;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: IdeaSpots.kt */
public final class IdeaSpots {
    private static volatile boolean dirty;
    private static File file;
    private static long savedMs;
    public static final IdeaSpots INSTANCE = new IdeaSpots();
    private static final ArrayList<Spot> spots = new ArrayList<>();

    private IdeaSpots() {
    }

    /* compiled from: IdeaSpots.kt */
    public static final class Spot {
        public static final Companion Companion = new Companion(null);
        private int count;
        private double hdg;
        private final String kind;
        private long lastMs;
        private double lat;
        private double lateral;
        private double lon;
        private String note;
        private final String src;

        public Spot(String kind, double lat, double lon, double hdg, double lateral, int count, long lastMs, String note, String src) {
            Intrinsics.checkNotNullParameter(kind, "kind");
            Intrinsics.checkNotNullParameter(note, "note");
            Intrinsics.checkNotNullParameter(src, "src");
            this.kind = kind;
            this.lat = lat;
            this.lon = lon;
            this.hdg = hdg;
            this.lateral = lateral;
            this.count = count;
            this.lastMs = lastMs;
            this.note = note;
            this.src = src;
        }

        public /* synthetic */ Spot(String str, double d, double d2, double d3, double d4, int i, long j, String str2, String str3, int i2, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, d, d2, d3, d4, i, j, str2, (i2 & 256) != 0 ? "me" : str3);
        }

        public final int getCount() {
            return this.count;
        }

        public final double getHdg() {
            return this.hdg;
        }

        public final String getKind() {
            return this.kind;
        }

        public final double getLat() {
            return this.lat;
        }

        public final double getLateral() {
            return this.lateral;
        }

        public final double getLon() {
            return this.lon;
        }

        public final void setCount(int i) {
            this.count = i;
        }

        public final void setHdg(double d) {
            this.hdg = d;
        }

        public final void setLat(double d) {
            this.lat = d;
        }

        public final void setLateral(double d) {
            this.lateral = d;
        }

        public final void setLon(double d) {
            this.lon = d;
        }

        public final long getLastMs() {
            return this.lastMs;
        }

        public final String getNote() {
            return this.note;
        }

        public final String getSrc() {
            return this.src;
        }

        public final void setLastMs(long j) {
            this.lastMs = j;
        }

        public final void setNote(String str) {
            Intrinsics.checkNotNullParameter(str, "<set-?>");
            this.note = str;
        }

        public final JSONObject json() {
            JSONObject put = new JSONObject().put("k", this.kind).put("lat", this.lat).put("lon", this.lon).put("h", Double.isNaN(this.hdg) ? null : Double.valueOf(this.hdg)).put("x", Double.isNaN(this.lateral) ? null : Double.valueOf(this.lateral)).put("n", this.count).put("t", this.lastMs).put("note", this.note).put("src", this.src);
            Intrinsics.checkNotNullExpressionValue(put, "put(...)");
            return put;
        }

        /* compiled from: IdeaSpots.kt */
        public static final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }

            public static /* synthetic */ Spot of$default(Companion companion, JSONObject jSONObject, String str, int i, Object obj) {
                if ((i & 2) != 0) {
                    str = null;
                }
                return companion.of(jSONObject, str);
            }

            public final Spot of(JSONObject o, String src) {
                Intrinsics.checkNotNullParameter(o, "o");
                String optString = o.optString("k");
                Intrinsics.checkNotNullExpressionValue(optString, "optString(...)");
                double optDouble = o.optDouble("lat");
                double optDouble2 = o.optDouble("lon");
                double optDouble3 = o.optDouble("h", Double.NaN);
                double optDouble4 = o.optDouble("x", Double.NaN);
                int optInt = o.optInt("n", 1);
                long optLong = o.optLong("t");
                String optString2 = o.optString("note");
                Intrinsics.checkNotNullExpressionValue(optString2, "optString(...)");
                String optString3 = src == null ? o.optString("src", "me") : src;
                Intrinsics.checkNotNull(optString3);
                return new Spot(optString, optDouble, optDouble2, optDouble3, optDouble4, optInt, optLong, optString2, optString3);
            }
        }
    }

    /* compiled from: IdeaSpots.kt */
    public static final class Seen {
        private final double ahead;
        private final double right;
        private final Spot spot;

        public Seen(Spot spot, double ahead, double right) {
            Intrinsics.checkNotNullParameter(spot, "spot");
            this.spot = spot;
            this.ahead = ahead;
            this.right = right;
        }

        public final double getAhead() {
            return this.ahead;
        }

        public final double getRight() {
            return this.right;
        }

        public final Spot getSpot() {
            return this.spot;
        }
    }

    public final synchronized void init(Context ctx) {
        String t;
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        if (file != null) {
            return;
        }
        file = new File(ctx.getApplicationContext().getFilesDir(), "crazy_spots.json");
        try {
            File it = file;
            Intrinsics.checkNotNull(it);
            if (!it.isFile()) {
                it = null;
            }
            if (it != null && (t = FilesKt.readText(it, kotlin.text.Charsets.UTF_8)) != null) {
                JSONArray a = new JSONArray(t);
                int length = a.length();
                for (int i = 0; i < length; i++) {
                    ArrayList<Spot> arrayList = spots;
                    Spot.Companion companion = Spot.Companion;
                    JSONObject jSONObject = a.getJSONObject(i);
                    Intrinsics.checkNotNullExpressionValue(jSONObject, "getJSONObject(...)");
                    arrayList.add(Spot.Companion.of$default(companion, jSONObject, null, 2, null));
                }
            }
        } catch (Exception e) {
        }
    }

    public final synchronized List<Spot> all() {
        return new ArrayList(spots);
    }

    public final synchronized int count(String kind) {
        int count$iv;
        Intrinsics.checkNotNullParameter(kind, "kind");
        Iterable $this$count$iv = spots;
        if (($this$count$iv instanceof Collection) && ((Collection) $this$count$iv).isEmpty()) {
            count$iv = 0;
        } else {
            count$iv = 0;
            for (Object element$iv : $this$count$iv) {
                Spot it = (Spot) element$iv;
                if (Intrinsics.areEqual(it.getKind(), kind) && (count$iv = count$iv + 1) < 0) {
                    CollectionsKt.throwCountOverflow();
                }
            }
        }
        return count$iv;
    }

    public final Pair<Double, Double> local(double lat0, double lon0, double lat, double lon) {
        return new Pair<>(Double.valueOf((lon - lon0) * 111320.0d * Math.cos(Math.toRadians(lat0))), Double.valueOf((lat - lat0) * 111195.0d));
    }

    public static /* synthetic */ boolean headingOk$default(IdeaSpots ideaSpots, double d, double d2, double d3, int i, Object obj) {
        if ((i & 4) != 0) {
            d3 = 50.0d;
        }
        return ideaSpots.headingOk(d, d2, d3);
    }

    public final boolean headingOk(double a, double b, double tolDeg) {
        return Double.isNaN(a) || Double.isNaN(b) || Math.abs(Math.IEEEremainder(a - b, 360.0d)) <= tolDeg;
    }

    public final synchronized Spot record(String kind, double lat, double lon, double hdgDeg, double lateral, String note, long nowMs, double mergeM, String src) {
        Intrinsics.checkNotNullParameter(kind, "kind");
        Intrinsics.checkNotNullParameter(note, "note");
        Intrinsics.checkNotNullParameter(src, "src");
        Iterator<Spot> it = spots.iterator();
        Intrinsics.checkNotNullExpressionValue(it, "iterator(...)");
        while (it.hasNext()) {
            Spot next = it.next();
            Intrinsics.checkNotNullExpressionValue(next, "next(...)");
            Spot s = next;
            if (Intrinsics.areEqual(s.getKind(), kind) && Intrinsics.areEqual(s.getSrc(), src)) {
                if (headingOk$default(this, s.getHdg(), hdgDeg, 0.0d, 4, null)) {
                    Pair<Double, Double> local = local(lat, lon, s.getLat(), s.getLon());
                    double dx = local.component1().doubleValue();
                    double dy = local.component2().doubleValue();
                    if ((dx * dx) + (dy * dy) <= mergeM * mergeM) {
                        double w = 1.0d / (s.getCount() + 1);
                        s.setLat(s.getLat() + ((lat - s.getLat()) * w));
                        s.setLon(s.getLon() + ((lon - s.getLon()) * w));
                        if (!Double.isNaN(lateral)) {
                            s.setLateral(Double.isNaN(s.getLateral()) ? lateral : s.getLateral() + ((lateral - s.getLateral()) * w));
                        }
                        s.setCount(s.getCount() + 1);
                        s.setLastMs(nowMs);
                        if (note.length() > 0) {
                            s.setNote(note);
                        }
                        dirty = true;
                        return s;
                    }
                }
            }
        }
        Spot s2 = new Spot(kind, lat, lon, hdgDeg, lateral, 1, nowMs, note, src);
        spots.add(s2);
        dirty = true;
        return s2;
    }

    public static /* synthetic */ List around$default(IdeaSpots ideaSpots, double d, double d2, double d3, Set set, double d4, double d5, double d6, int i, boolean z, int i2, Object obj) {
        int i3;
        boolean z2;
        double d7 = (i2 & 32) != 0 ? 0.0d : d5;
        double d8 = (i2 & 64) != 0 ? 8.0d : d6;
        if ((i2 & 128) == 0) {
            i3 = i;
        } else {
            i3 = 1;
        }
        if ((i2 & 256) == 0) {
            z2 = z;
        } else {
            z2 = true;
        }
        return ideaSpots.around(d, d2, d3, set, d4, d7, d8, i3, z2);
    }

    public final synchronized List<Seen> around(double lat, double lon, double yaw, Set<String> kinds, double ahead, double behind, double side, int minCount, boolean needHeading) {
        List list;
        Iterator<Spot> it;
        ArrayList out;
        Set<String> kinds2 = kinds;
        synchronized (this) {
            Intrinsics.checkNotNullParameter(kinds2, "kinds");
            double hx = Math.cos(yaw);
            double hy = Math.sin(yaw);
            double hdg = ((90.0d - Math.toDegrees(yaw)) + 360.0d) % 360.0d;
            ArrayList out2 = new ArrayList();
            Iterator<Spot> it2 = spots.iterator();
            Intrinsics.checkNotNullExpressionValue(it2, "iterator(...)");
            while (it2.hasNext()) {
                Spot next = it2.next();
                Intrinsics.checkNotNullExpressionValue(next, "next(...)");
                Spot s = next;
                if (!kinds2.contains(s.getKind())) {
                    out2 = out2;
                    it2 = it2;
                    kinds2 = kinds;
                } else if (s.getCount() >= minCount) {
                    if (needHeading) {
                        it = it2;
                        out = out2;
                        if (!headingOk$default(this, s.getHdg(), hdg, 0.0d, 4, null)) {
                            out2 = out;
                            it2 = it;
                            kinds2 = kinds;
                        }
                    } else {
                        it = it2;
                        out = out2;
                    }
                    Pair<Double, Double> local = local(lat, lon, s.getLat(), s.getLon());
                    double dx = local.component1().doubleValue();
                    double dy = local.component2().doubleValue();
                    double a = (dx * hx) + (dy * hy);
                    double hdg2 = hdg;
                    double hdg3 = -behind;
                    if (a >= hdg3 && a <= ahead) {
                        double r = (dx * hy) - (dy * hx);
                        if (Math.abs(r) > side) {
                            out2 = out;
                            it2 = it;
                            hdg = hdg2;
                            kinds2 = kinds;
                        } else {
                            out.add(new Seen(s, a, r));
                            out2 = out;
                            it2 = it;
                            hdg = hdg2;
                            kinds2 = kinds;
                        }
                    }
                    out2 = out;
                    it2 = it;
                    hdg = hdg2;
                    kinds2 = kinds;
                }
            }
            List out3 = out2;
            List $this$sortBy$iv = out3;
            if ($this$sortBy$iv.size() > 1) {
                CollectionsKt.sortWith($this$sortBy$iv, new Comparator() { // from class: com.starpilot.starview.IdeaSpots$around$$inlined$sortBy$1
                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // java.util.Comparator
                    public final int compare(Object t, Object t2) {
                        IdeaSpots.Seen it3 = (IdeaSpots.Seen) t;
                        IdeaSpots.Seen it4 = (IdeaSpots.Seen) t2;
                        return ComparisonsKt.compareValues(Double.valueOf(it3.getAhead()), Double.valueOf(it4.getAhead()));
                    }
                });
            }
            list = out3;
        }
        return list;
    }

    public static /* synthetic */ void saveSoon$default(IdeaSpots ideaSpots, long j, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            j = System.currentTimeMillis();
        }
        if ((i & 2) != 0) {
            z = false;
        }
        ideaSpots.saveSoon(j, z);
    }

    public final void saveSoon(long nowMs, boolean force) {
        final File f;
        final String text;
        if (dirty) {
            if ((force || nowMs - savedMs >= 30000) && (f = file) != null) {
                synchronized (this) {
                    dirty = false;
                    JSONArray $this$saveSoon_u24lambda_u245_u24lambda_u244 = new JSONArray();
                    Iterator<Spot> it = spots.iterator();
                    Intrinsics.checkNotNullExpressionValue(it, "iterator(...)");
                    while (it.hasNext()) {
                        Spot next = it.next();
                        Intrinsics.checkNotNullExpressionValue(next, "next(...)");
                        Spot s = next;
                        $this$saveSoon_u24lambda_u245_u24lambda_u244.put(s.json());
                    }
                    text = $this$saveSoon_u24lambda_u245_u24lambda_u244.toString();
                }
                Intrinsics.checkNotNullExpressionValue(text, "synchronized(...)");
                savedMs = nowMs;
                new Thread(new Runnable() { // from class: com.starpilot.starview.IdeaSpots$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        IdeaSpots.saveSoon$lambda$7(f, text);
                    }
                }, "crazy-spots").start();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void saveSoon$lambda$7(File $f, String $text) {
        try {
            File $this$saveSoon_u24lambda_u247_u24lambda_u246 = new File($f.getPath() + ".tmp");
            FilesKt.writeText($this$saveSoon_u24lambda_u247_u24lambda_u246, $text, kotlin.text.Charsets.UTF_8);
            $this$saveSoon_u24lambda_u247_u24lambda_u246.renameTo($f);
        } catch (Exception e) {
        }
    }

    public final File fleetDir() {
        return new File(Environment.getExternalStorageDirectory(), "Download/StarView/fleet");
    }

    public final int exportFleet(String name) {
        List<Spot> mine;
        Intrinsics.checkNotNullParameter(name, "name");
        synchronized (this) {
            Iterable $this$filter$iv = spots;
            Collection destination$iv$iv = new ArrayList();
            for (Object element$iv$iv : $this$filter$iv) {
                Spot it = (Spot) element$iv$iv;
                if (Intrinsics.areEqual(it.getSrc(), "me") && !Intrinsics.areEqual(it.getKind(), "map_bump")) {
                    destination$iv$iv.add(element$iv$iv);
                }
            }
            mine = (List) destination$iv$iv;
        }
        try {
            File d = fleetDir();
            d.mkdirs();
            File file2 = new File(d, name + ".json");
            JSONObject put = new JSONObject().put("from", name).put("t", System.currentTimeMillis());
            JSONArray $this$exportFleet_u24lambda_u2410 = new JSONArray();
            for (Spot s : mine) {
                $this$exportFleet_u24lambda_u2410.put(s.json());
            }
            Unit unit = Unit.INSTANCE;
            String jSONObject = put.put("spots", $this$exportFleet_u24lambda_u2410).toString();
            Intrinsics.checkNotNullExpressionValue(jSONObject, "toString(...)");
            FilesKt.writeText(file2, jSONObject, kotlin.text.Charsets.UTF_8);
            return mine.size();
        } catch (Exception e) {
            return -1;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean importFleet$lambda$11(String $myName, File f) {
        String name = f.getName();
        Intrinsics.checkNotNullExpressionValue(name, "getName(...)");
        return StringsKt.endsWith(name, ".json", false) && !Intrinsics.areEqual(f.getName(), new StringBuilder().append($myName).append(".json").toString());
    }

    public final int importFleet(final String myName) {
        File[] fileArr;
        Intrinsics.checkNotNullParameter(myName, "myName");
        try {
            fileArr = fleetDir().listFiles(new FileFilter() { // from class: com.starpilot.starview.IdeaSpots$$ExternalSyntheticLambda0
                @Override // java.io.FileFilter
                public final boolean accept(File file2) {
                    boolean importFleet$lambda$11;
                    importFleet$lambda$11 = IdeaSpots.importFleet$lambda$11(myName, file2);
                    return importFleet$lambda$11;
                }
            });
            if (fileArr == null) {
                fileArr = new File[0];
            }
        } catch (Exception e) {
            fileArr = new File[0];
        }
        File[] files = fileArr;
        int n = 0;
        for (File f : files) {
            try {
                Intrinsics.checkNotNull(f);
                JSONObject o = new JSONObject(FilesKt.readText(f, kotlin.text.Charsets.UTF_8));
                final String from = o.optString("from", FilesKt.getNameWithoutExtension(f));
                JSONArray a = o.optJSONArray("spots");
                if (a != null) {
                    synchronized (this) {
                        CollectionsKt.removeAll((List) spots, new Function1() { // from class: com.starpilot.starview.IdeaSpots$$ExternalSyntheticLambda1
                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj) {
                                boolean importFleet$lambda$13$lambda$12;
                                importFleet$lambda$13$lambda$12 = IdeaSpots.importFleet$lambda$13$lambda$12(from, (IdeaSpots.Spot) obj);
                                return Boolean.valueOf(importFleet$lambda$13$lambda$12);
                            }
                        });
                        int length = a.length();
                        for (int i = 0; i < length; i++) {
                            ArrayList<Spot> arrayList = spots;
                            Spot.Companion companion = Spot.Companion;
                            JSONObject jSONObject = a.getJSONObject(i);
                            Intrinsics.checkNotNullExpressionValue(jSONObject, "getJSONObject(...)");
                            arrayList.add(companion.of(jSONObject, from));
                            n++;
                        }
                        dirty = true;
                        Unit unit = Unit.INSTANCE;
                    }
                }
            } catch (Exception e2) {
            }
        }
        return n;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean importFleet$lambda$13$lambda$12(String $from, Spot it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return Intrinsics.areEqual(it.getSrc(), $from);
    }

    public static /* synthetic */ void expire$default(IdeaSpots ideaSpots, String str, long j, long j2, int i, Object obj) {
        if ((i & 4) != 0) {
            j2 = System.currentTimeMillis();
        }
        ideaSpots.expire(str, j, j2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean expire$lambda$14(String $kind, long $nowMs, long $maxAgeMs, Spot it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return Intrinsics.areEqual(it.getKind(), $kind) && $nowMs - it.getLastMs() > $maxAgeMs;
    }

    public final synchronized void expire(final String kind, final long maxAgeMs, final long nowMs) {
        Intrinsics.checkNotNullParameter(kind, "kind");
        if (CollectionsKt.removeAll((List) spots, new Function1() { // from class: com.starpilot.starview.IdeaSpots$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                boolean expire$lambda$14;
                expire$lambda$14 = IdeaSpots.expire$lambda$14(kind, nowMs, maxAgeMs, (IdeaSpots.Spot) obj);
                return Boolean.valueOf(expire$lambda$14);
            }
        })) {
            dirty = true;
        }
    }

    public final synchronized void clearForTest$app_debug() {
        spots.clear();
    }
}
