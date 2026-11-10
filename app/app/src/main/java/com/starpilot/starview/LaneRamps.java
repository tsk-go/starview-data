package com.starpilot.starview;

import com.starpilot.starview.LaneRamps;
import com.starpilot.starview.OsmRoads;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;

/* compiled from: LaneRamps.kt */
public final class LaneRamps {
    public static final double EXIT_AHEAD = 320.0d;
    public static final LaneRamps INSTANCE = new LaneRamps();
    public static final double MERGE_BEHIND = 260.0d;

    private LaneRamps() {
    }

    /* compiled from: LaneRamps.kt */
    public static final class Ramp {
        private final double dist;
        private final String kind;
        private final int side;

        public Ramp(String kind, int side, double dist) {
            Intrinsics.checkNotNullParameter(kind, "kind");
            this.kind = kind;
            this.side = side;
            this.dist = dist;
        }

        public final double getDist() {
            return this.dist;
        }

        public final String getKind() {
            return this.kind;
        }

        public final int getSide() {
            return this.side;
        }

        public final long getFeet() {
            return Math.round((Math.abs(this.dist) * 3.28084d) / 10.0d) * 10;
        }

        public final String text() {
            StringBuilder append;
            String str;
            String str2 = Intrinsics.areEqual(this.kind, "exit") ? "exit" : "merge";
            String str3 = this.side < 0 ? "left" : "right";
            double d = this.dist;
            long feet = getFeet();
            if (d >= 0.0d) {
                append = new StringBuilder().append(", ").append(feet);
                str = " ft ahead";
            } else {
                append = new StringBuilder().append(", ").append(feet);
                str = " ft behind";
            }
            return str2 + " on the " + str3 + append.append(str).toString();
        }

        public final String code() {
            return this.kind + "," + (this.side < 0 ? "L" : "R") + "," + Math.round(this.dist);
        }
    }

    public final List<Ramp> scan(List<OsmRoads.Way> ways, OsmRoads.Way our, double e, double n, double yaw) {
        Intrinsics.checkNotNullParameter(ways, "ways");
        Intrinsics.checkNotNullParameter(our, "our");
        long ourId = our.getId();
        // our road: our way, and the other pieces of the same street of the same kind
        List<OsmRoads.Way> mains = new ArrayList<>();
        for (OsmRoads.Way w : ways) {
            boolean same = w.getId() == ourId || (!StringsKt.endsWith$default(w.getKind(), "_link", false, 2, (Object) null) && Intrinsics.areEqual(w.getKind(), our.getKind()) && our.getName().length() > 0 && OsmRoads.INSTANCE.sameStreet(w.getName(), our.getName()));
            if (same) {
                mains.add(w);
            }
        }
        List<OsmRoads.Way> road = mains.isEmpty() ? CollectionsKt.listOf(our) : mains;
        double hx = Math.cos(yaw);
        double hy = Math.sin(yaw);
        HashMap<Integer, Ramp> best = new HashMap<>();
        for (OsmRoads.Way w : ways) {
            if (w.getId() == ourId || !StringsKt.endsWith$default(w.getKind(), "_link", false, 2, (Object) null) || w.getE().length < 2) {
                continue;
            }
            for (int end = 0; end < 2; end++) {
                int i = end == 0 ? 0 : w.getE().length - 1;
                double de = w.getE()[i] - e;
                double dn = w.getN()[i] - n;
                double along = (de * hx) + (dn * hy);
                if (Math.abs((de * hy) - (dn * hx)) > 40.0d) {
                    continue;
                }
                Iterator<OsmRoads.Way> it = road.iterator();
                if (!it.hasNext()) {
                    throw new NoSuchElementException();
                }
                double d = INSTANCE.distTo(it.next(), w.getE()[i], w.getN()[i], hx, hy);
                while (it.hasNext()) {
                    d = Math.min(d, INSTANCE.distTo(it.next(), w.getE()[i], w.getN()[i], hx, hy));
                }
                if (d > 10.0d) {
                    continue;
                }
                int far = end == 0 ? w.getE().length - 1 : 0;
                int next = end == 0 ? Math.min(w.getE().length - 1, 1) : Math.max(0, w.getE().length - 2);
                double fe = w.getE()[far] - w.getE()[i];
                double fn = w.getN()[far] - w.getN()[i];
                if (Math.hypot(fe, fn) < 30.0d) {
                    continue;
                }
                double cross = ((w.getE()[next] - w.getE()[i]) * hy) - ((w.getN()[next] - w.getN()[i]) * hx);
                if (Math.abs(cross) < 1.0d) {
                    cross = (fe * hy) - (fn * hx);
                }
                int side = cross > 0.0d ? 1 : -1;
                String kind = (fe * hx) + (fn * hy) > 0.0d ? "exit" : "merge";
                boolean inRange = Intrinsics.areEqual(kind, "exit") ? (-20.0d <= along && along <= 320.0d) : (-260.0d <= along && along <= 60.0d);
                if (!inRange) {
                    continue;
                }
                Ramp r = new Ramp(kind, side, along);
                Ramp have = best.get(Integer.valueOf(side));
                if (have != null && !(Math.abs(r.getDist()) < Math.abs(have.getDist()))) {
                    continue;
                }
                best.put(Integer.valueOf(side), r);
            }
        }
        Collection<Ramp> values = best.values();
        Intrinsics.checkNotNullExpressionValue(values, "<get-values>(...)");
        return CollectionsKt.sortedWith(values, new Comparator<Ramp>() {
            @Override // java.util.Comparator
            public final int compare(Ramp a, Ramp b) {
                return ComparisonsKt.compareValues(Integer.valueOf(a.getSide()), Integer.valueOf(b.getSide()));
            }
        });
    }

    public final double distTo(OsmRoads.Way w, double x, double y, double hx, double hy) {
        int i;
        int i2;
        Intrinsics.checkNotNullParameter(w, "w");
        double d = Double.MAX_VALUE;
        int i3 = 0;
        int length = w.getE().length - 1;
        while (i3 < length) {
            double ax = w.getE()[i3];
            double ay = w.getN()[i3];
            double dx = w.getE()[i3 + 1] - ax;
            double dy = w.getN()[i3 + 1] - ay;
            double l2 = (dx * dx) + (dy * dy);
            if (w.getOneway() == 0 || l2 <= 1.0E-6d) {
                i = i3;
            } else {
                i = i3;
                if ((w.getOneway() * ((dx * hx) + (dy * hy))) / Math.sqrt(l2) < 0.3d) {
                    i2 = length;
                    i3 = i + 1;
                    length = i2;
                }
            }
            double t = l2 < 1.0E-6d ? 0.0d : RangesKt.coerceIn((((x - ax) * dx) + ((y - ay) * dy)) / l2, 0.0d, 1.0d);
            i2 = length;
            d = Math.min(d, Math.hypot(x - ((t * dx) + ax), y - ((t * dy) + ay)));
            i3 = i + 1;
            length = i2;
        }
        return d;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CharSequence codes$lambda$4(Ramp it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return it.code();
    }

    public final String codes(List<Ramp> l) {
        Intrinsics.checkNotNullParameter(l, "l");
        return CollectionsKt.joinToString(l, ";", "", "", -1, "...", new Function1() { // from class: com.starpilot.starview.LaneRamps$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                CharSequence codes$lambda$4;
                codes$lambda$4 = LaneRamps.codes$lambda$4((LaneRamps.Ramp) obj);
                return codes$lambda$4;
            }
        });
    }

    public final List<Ramp> parse(String s) {
        Ramp ramp;
        Iterable $this$mapNotNull$iv;
        int $i$f$mapNotNull;
        Iterable $this$mapNotNullTo$iv$iv;
        int $i$f$mapNotNullTo;
        Intrinsics.checkNotNullParameter(s, "s");
        int i = 1;
        char c = 0;
        Iterable $this$mapNotNull$iv2 = StringsKt.split((CharSequence) s, new String[]{";"}, false, 0);
        int $i$f$mapNotNull2 = 0;
        Collection destination$iv$iv = new ArrayList();
        Iterable $this$mapNotNullTo$iv$iv2 = $this$mapNotNull$iv2;
        int $i$f$mapNotNullTo2 = 0;
        for (Object element$iv$iv$iv : $this$mapNotNullTo$iv$iv2) {
            String p = (String) element$iv$iv$iv;
            String[] strArr = new String[i];
            strArr[c] = ",";
            List f = StringsKt.split((CharSequence) p, strArr, false, 0);
            if (f.size() < 3) {
                ramp = null;
                $this$mapNotNull$iv = $this$mapNotNull$iv2;
                $i$f$mapNotNull = $i$f$mapNotNull2;
                $this$mapNotNullTo$iv$iv = $this$mapNotNullTo$iv$iv2;
                $i$f$mapNotNullTo = $i$f$mapNotNullTo2;
            } else {
                String str = (String) f.get(0);
                $this$mapNotNull$iv = $this$mapNotNull$iv2;
                $i$f$mapNotNull = $i$f$mapNotNull2;
                int i2 = Intrinsics.areEqual(f.get(1), "L") ? -1 : 1;
                Double doubleOrNull = StringsKt.toDoubleOrNull((String) f.get(2));
                double doubleValue = doubleOrNull != null ? doubleOrNull.doubleValue() : 0.0d;
                $this$mapNotNullTo$iv$iv = $this$mapNotNullTo$iv$iv2;
                $i$f$mapNotNullTo = $i$f$mapNotNullTo2;
                ramp = new Ramp(str, i2, doubleValue);
            }
            if (ramp != null) {
                destination$iv$iv.add(ramp);
            }
            $this$mapNotNullTo$iv$iv2 = $this$mapNotNullTo$iv$iv;
            $i$f$mapNotNull2 = $i$f$mapNotNull;
            $this$mapNotNull$iv2 = $this$mapNotNull$iv;
            $i$f$mapNotNullTo2 = $i$f$mapNotNullTo;
            i = 1;
            c = 0;
        }
        return (List) destination$iv$iv;
    }
}
