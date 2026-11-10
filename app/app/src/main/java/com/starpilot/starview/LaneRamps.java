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

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0077, code lost:
    
        if (com.starpilot.starview.OsmRoads.INSTANCE.sameStreet(r16.getName(), r51.getName()) != false) goto L20;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:19:0x007f A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0023 A[SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r15v4, types: [int] */
    /* JADX WARN: Type inference failed for: r15v6 */
    /* JADX WARN: Type inference failed for: r15v7 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final List<Ramp> scan(List<OsmRoads.Way> ways, OsmRoads.Way our, double e, double n, double yaw) {
        Object obj;
        int i;
        boolean z;
        List list;
        long j;
        double d;
        int i2;
        int i3;
        boolean z2;
        Intrinsics.checkNotNullParameter(ways, "ways");
        Intrinsics.checkNotNullParameter(our, "our");
        long id = our.getId();
        ArrayList arrayList = new ArrayList();
        Iterator it = ways.iterator();
        while (true) {
            obj = null;
            i = 2;
            z = false;
            if (!it.hasNext()) {
                break;
            }
            Object next = it.next();
            OsmRoads.Way way = (OsmRoads.Way) next;
            if (way.getId() != id) {
                if (!StringsKt.endsWith$default(way.getKind(), "_link", false, 2, (Object) null) && Intrinsics.areEqual(way.getKind(), our.getKind())) {
                    if (our.getName().length() > 0) {
                    }
                }
                z2 = false;
                if (!z2) {
                    arrayList.add(next);
                }
            }
            z2 = true;
            if (!z2) {
            }
        }
        ArrayList arrayList2 = arrayList;
        if (arrayList2.isEmpty()) {
            arrayList2 = CollectionsKt.listOf(our);
        }
        List list2 = arrayList2;
        double cos = Math.cos(yaw);
        double sin = Math.sin(yaw);
        HashMap hashMap = new HashMap();
        for (OsmRoads.Way way2 : ways) {
            if (way2.getId() == id || !StringsKt.endsWith$default(way2.getKind(), "_link", z, i, obj)) {
                obj = null;
                i = 2;
                z = false;
            } else if (way2.getE().length >= i) {
                int i4 = 0;
                ?? r15 = z;
                while (i4 < i) {
                    int length = i4 == 0 ? r15 : way2.getE().length - 1;
                    double d2 = way2.getE()[length] - e;
                    double d3 = way2.getN()[length] - n;
                    OsmRoads.Way way3 = way2;
                    double d4 = (d2 * cos) + (d3 * sin);
                    if (Math.abs((d2 * sin) - (d3 * cos)) <= 40.0d) {
                        Iterator it2 = list2.iterator();
                        if (!it2.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        list = list2;
                        double distTo = INSTANCE.distTo((OsmRoads.Way) it2.next(), way3.getE()[length], way3.getN()[length], cos, sin);
                        while (it2.hasNext()) {
                            distTo = Math.min(distTo, INSTANCE.distTo((OsmRoads.Way) it2.next(), way3.getE()[length], way3.getN()[length], cos, sin));
                            id = id;
                        }
                        j = id;
                        if (distTo > 10.0d) {
                            d = cos;
                        } else {
                            if (i4 == 0) {
                                i2 = 1;
                                i3 = way3.getE().length - 1;
                            } else {
                                i2 = 1;
                                i3 = r15;
                            }
                            double[] e2 = way3.getE();
                            int min = i4 == 0 ? Math.min(e2.length - i2, i2) : Math.max((int) r15, e2.length - 2);
                            double d5 = way3.getE()[i3] - way3.getE()[length];
                            d = cos;
                            double d6 = way3.getN()[i3] - way3.getN()[length];
                            if (Math.hypot(d5, d6) >= 30.0d) {
                                double d7 = (d5 * d) + (d6 * sin);
                                double d8 = ((way3.getE()[min] - way3.getE()[length]) * sin) - ((way3.getN()[min] - way3.getN()[length]) * d);
                                if (Math.abs(d8) < 1.0d) {
                                    d8 = (d5 * sin) - (d6 * d);
                                }
                                int i5 = d8 > 0.0d ? 1 : -1;
                                String str = d7 > 0.0d ? "exit" : "merge";
                                if (!Intrinsics.areEqual(str, "exit") ? -260.0d > d4 || d4 > 60.0d : -20.0d > d4 || d4 > 320.0d) {
                                    Ramp ramp = new Ramp(str, i5, d4);
                                    Ramp ramp2 = (Ramp) hashMap.get(Integer.valueOf(i5));
                                    if (ramp2 == null || Math.abs(ramp.getDist()) < Math.abs(ramp2.getDist())) {
                                        hashMap.put(Integer.valueOf(i5), ramp);
                                    }
                                }
                            }
                        }
                    } else {
                        list = list2;
                        j = id;
                        d = cos;
                    }
                    i4++;
                    cos = d;
                    way2 = way3;
                    list2 = list;
                    id = j;
                    i = 2;
                    r15 = 0;
                }
                obj = null;
                i = 2;
                z = false;
            }
        }
        Collection values = hashMap.values();
        Intrinsics.checkNotNullExpressionValue(values, "<get-values>(...)");
        return CollectionsKt.sortedWith(values, new Comparator() { // from class: com.starpilot.starview.LaneRamps$scan$$inlined$sortedBy$1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.Comparator
            public final int compare(T t, T t2) {
                LaneRamps.Ramp it3 = (LaneRamps.Ramp) t;
                LaneRamps.Ramp it4 = (LaneRamps.Ramp) t2;
                return ComparisonsKt.compareValues(Integer.valueOf(it3.getSide()), Integer.valueOf(it4.getSide()));
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
        return CollectionsKt.joinToString$default(l, ";", null, null, 0, null, new Function1() { // from class: com.starpilot.starview.LaneRamps$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                CharSequence codes$lambda$4;
                codes$lambda$4 = LaneRamps.codes$lambda$4((LaneRamps.Ramp) obj);
                return codes$lambda$4;
            }
        }, 30, null);
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
        Iterable $this$mapNotNull$iv2 = StringsKt.split$default((CharSequence) s, new String[]{";"}, false, 0, 6, (Object) null);
        int $i$f$mapNotNull2 = 0;
        Collection destination$iv$iv = new ArrayList();
        Iterable $this$mapNotNullTo$iv$iv2 = $this$mapNotNull$iv2;
        int $i$f$mapNotNullTo2 = 0;
        for (Object element$iv$iv$iv : $this$mapNotNullTo$iv$iv2) {
            String p = (String) element$iv$iv$iv;
            String[] strArr = new String[i];
            strArr[c] = ",";
            List f = StringsKt.split$default((CharSequence) p, strArr, false, 0, 6, (Object) null);
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
