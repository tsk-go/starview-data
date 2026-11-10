package com.starpilot.starview;

import com.starpilot.starview.OsmRoads;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import kotlin.jvm.internal.Ref.ObjectRef;

/* compiled from: RoadShape.kt */
public final class RoadShape {
    public static final double CORNER = 8.0d;
    public static final RoadShape INSTANCE = new RoadShape();

    private RoadShape() {
    }

    public final boolean spline(String kind) {
        Intrinsics.checkNotNullParameter(kind, "kind");
        return StringsKt.startsWith(kind, "motorway", false) || StringsKt.startsWith(kind, "trunk", false) || StringsKt.startsWith(kind, "primary", false) || StringsKt.startsWith(kind, "secondary", false) || StringsKt.endsWith(kind, "_link", false);
    }

    private final long key(double e, double n) {
        return (Math.round(e * 10.0d) * 4000003) + Math.round(10.0d * n);
    }

    /* compiled from: RoadShape.kt */
    public static final class Rounded {
        private final List<double[]> openEnds;
        private final OsmRoads.Way way;

        public Rounded(OsmRoads.Way way, List<double[]> openEnds) {
            Intrinsics.checkNotNullParameter(way, "way");
            Intrinsics.checkNotNullParameter(openEnds, "openEnds");
            this.way = way;
            this.openEnds = openEnds;
        }

        public final List<double[]> getOpenEnds() {
            return this.openEnds;
        }

        public final OsmRoads.Way getWay() {
            return this.way;
        }
    }

    public final List<OsmRoads.Way> rounded(List<OsmRoads.Way> ways, List<OsmRoads.Way> near, double ce, double cn, double r, Map<Long, Rounded> cache) {
        Intrinsics.checkNotNullParameter(ways, "ways");
        Intrinsics.checkNotNullParameter(near, "near");
        Intrinsics.checkNotNullParameter(cache, "cache");
        Ref.ObjectRef at = new Ref.ObjectRef();
        ArrayList out = new ArrayList(ways.size());
        for (OsmRoads.Way w : ways) {
            Rounded c = cache.get(Long.valueOf(w.getId()));
            if (c != null) {
                Iterable $this$none$iv = c.getOpenEnds();
                char c2 = 1;
                if (!($this$none$iv instanceof Collection) || !((Collection) $this$none$iv).isEmpty()) {
                    Iterator it = $this$none$iv.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            Object element$iv = it.next();
                            double[] it2 = (double[]) element$iv;
                            if (Math.hypot(it2[0] - ce, it2[c2] - cn) < r) {
                                c2 = 0;
                                break;
                            }
                            c2 = 1;
                        } else {
                            c2 = 1;
                            break;
                        }
                    }
                }
                if (c2 != 0) {
                    out.add(c.getWay());
                }
            }
            ArrayList open = new ArrayList();
            double[] q = rounded$goesOn(w, ce, cn, r, open, at, near, 0);
            double[] rr = rounded$goesOn(w, ce, cn, r, open, at, near, 1);
            Pair<double[], double[]> through$default = spline(w.getKind()) ? through$default(this, w.getE(), w.getN(), q, rr, 0.0d, 16, null) : round(w.getE(), w.getN(), q, rr, 8.0d);
            double[] e = through$default.component1();
            double[] n = through$default.component2();
            OsmRoads.Way rw = new OsmRoads.Way(w.getId(), w.getName(), w.getKind(), w.getOneway(), w.getWidth(), e, n, w.getLanes());
            cache.put(Long.valueOf(w.getId()), new Rounded(rw, open));
            out.add(rw);
        }
        if (cache.size() > 6000) {
            cache.clear();
        }
        return out;
    }

    private static final HashMap<Long, List<Pair<OsmRoads.Way, Integer>>> rounded$index(Ref.ObjectRef<HashMap<Long, List<Pair<OsmRoads.Way, Integer>>>> objectRef, List<OsmRoads.Way> list) {
        Object answer$iv;
        HashMap<Long, List<Pair<OsmRoads.Way, Integer>>> it = objectRef.element;
        if (it != null) {
            return it;
        }
        HashMap<Long, List<Pair<OsmRoads.Way, Integer>>> hashMap = new HashMap<>();
        for (OsmRoads.Way w : list) {
            int length = w.getE().length;
            for (int i = 0; i < length; i++) {
                Map $this$getOrPut$iv = (Map) hashMap;
                Long valueOf = Long.valueOf(INSTANCE.key(w.getE()[i], w.getN()[i]));
                Object value$iv = $this$getOrPut$iv.get(valueOf);
                if (value$iv == null) {
                    answer$iv = new ArrayList(2);
                    $this$getOrPut$iv.put(valueOf, answer$iv);
                } else {
                    answer$iv = value$iv;
                }
                ((List) answer$iv).add(TuplesKt.to(w, Integer.valueOf(i)));
            }
        }
        objectRef.element = hashMap;
        return hashMap;
    }

    private static final double[] rounded$goesOn(OsmRoads.Way w, double $ce, double $cn, double $r, ArrayList<double[]> arrayList, Ref.ObjectRef<HashMap<Long, List<Pair<OsmRoads.Way, Integer>>>> objectRef, List<OsmRoads.Way> list, int end) {
        int i = end == 0 ? 0 : w.getE().length - 1;
        if (Math.hypot(w.getE()[i] - $ce, w.getN()[i] - $cn) >= $r) {
            arrayList.add(new double[]{w.getE()[i], w.getN()[i]});
            return null;
        }
        Iterable iterable = (List) rounded$index(objectRef, list).get(Long.valueOf(INSTANCE.key(w.getE()[i], w.getN()[i])));
        if (iterable == null) {
            return null;
        }
        Iterable $this$filter$iv = iterable;
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            Pair it = (Pair) element$iv$iv;
            Pair it2 = ((OsmRoads.Way) it.getFirst()).getId() != w.getId() ? 1 : null;
            if (it2 != null) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        List there = (List) destination$iv$iv;
        if (there.size() != 1) {
            return null;
        }
        Pair pair = (Pair) there.get(0);
        OsmRoads.Way u = (OsmRoads.Way) pair.component1();
        int j = ((Number) pair.component2()).intValue();
        if (j != 0 && j != u.getE().length - 1) {
            return null;
        }
        if (!Intrinsics.areEqual(StringsKt.removeSuffix(u.getKind(), (CharSequence) "_link"), StringsKt.removeSuffix(w.getKind(), (CharSequence) "_link")) && (!u.getHighway() || !w.getHighway())) {
            return null;
        }
        if (u.getName().length() > 0) {
            if ((w.getName().length() > 0) && !OsmRoads.INSTANCE.sameStreet(u.getName(), w.getName()) && !StringsKt.endsWith(u.getKind(), "_link", false) && !StringsKt.endsWith(w.getKind(), "_link", false)) {
                return null;
            }
        }
        int k = j == 0 ? 1 : u.getE().length - 2;
        if (k >= 0 && k < u.getE().length) {
            return new double[]{u.getE()[k], u.getN()[k]};
        }
        return null;
    }

    public final Pair<double[], double[]> round(double[] e, double[] n, double[] prev, double[] next, double dmax) {
        String str;
        int first;
        int last;
        int m;
        int first2;
        ArrayList oe;
        int i;
        int i2;
        int last2;
        Intrinsics.checkNotNullParameter(e, "e");
        Intrinsics.checkNotNullParameter(n, "n");
        int m2 = e.length;
        if (m2 < 2) {
            return TuplesKt.to(e, n);
        }
        ArrayList xe = new ArrayList(m2 + 2);
        ArrayList xn = new ArrayList(m2 + 2);
        if (prev != null) {
            xe.add(Double.valueOf(prev[0]));
            xn.add(Double.valueOf(prev[1]));
        }
        for (int i3 = 0; i3 < m2; i3++) {
            xe.add(Double.valueOf(e[i3]));
            xn.add(Double.valueOf(n[i3]));
        }
        if (next != null) {
            xe.add(Double.valueOf(next[0]));
            xn.add(Double.valueOf(next[1]));
        }
        ArrayList oe2 = new ArrayList(m2 * 3);
        ArrayList on = new ArrayList(m2 * 3);
        int first3 = prev != null ? 1 : 0;
        int last3 = (xe.size() - 1) - (next != null ? 1 : 0);
        if (prev == null) {
            Object obj = xe.get(0);
            Intrinsics.checkNotNullExpressionValue(obj, "get(...)");
            double doubleValue = ((Number) obj).doubleValue();
            Object obj2 = xn.get(0);
            Intrinsics.checkNotNullExpressionValue(obj2, "get(...)");
            str = "get(...)";
            first = first3;
            last = last3;
            round$add(oe2, on, doubleValue, ((Number) obj2).doubleValue());
        } else {
            str = "get(...)";
            first = first3;
            last = last3;
        }
        int size = xe.size() - 1;
        int i4 = 1;
        while (i4 < size) {
            if (i4 >= first) {
                int last4 = last;
                if (i4 > last4) {
                    m = m2;
                    first2 = first;
                    oe = oe2;
                    last2 = last4;
                    i = size;
                    i2 = i4;
                } else {
                    Object obj3 = xe.get(i4 - 1);
                    Intrinsics.checkNotNullExpressionValue(obj3, str);
                    double ax = ((Number) obj3).doubleValue();
                    Object obj4 = xn.get(i4 - 1);
                    Intrinsics.checkNotNullExpressionValue(obj4, str);
                    double ay = ((Number) obj4).doubleValue();
                    Object obj5 = xe.get(i4);
                    Intrinsics.checkNotNullExpressionValue(obj5, str);
                    double bx = ((Number) obj5).doubleValue();
                    Object obj6 = xn.get(i4);
                    Intrinsics.checkNotNullExpressionValue(obj6, str);
                    double by = ((Number) obj6).doubleValue();
                    Object obj7 = xe.get(i4 + 1);
                    Intrinsics.checkNotNullExpressionValue(obj7, str);
                    double cx = ((Number) obj7).doubleValue();
                    Object obj8 = xn.get(i4 + 1);
                    Intrinsics.checkNotNullExpressionValue(obj8, str);
                    double cy = ((Number) obj8).doubleValue();
                    oe = oe2;
                    double l1 = Math.hypot(bx - ax, by - ay);
                    double l2 = Math.hypot(cx - bx, cy - by);
                    int half = (prev == null || i4 != first) ? (next == null || i4 != last4) ? 0 : 2 : 1;
                    if (l1 < 1.0E-6d) {
                        m = m2;
                        first2 = first;
                        last2 = last4;
                        i = size;
                        i2 = i4;
                    } else if (l2 < 1.0E-6d) {
                        m = m2;
                        first2 = first;
                        last2 = last4;
                        i = size;
                        i2 = i4;
                    } else {
                        double cosT = RangesKt.coerceIn((((bx - ax) * (cx - bx)) + ((by - ay) * (cy - by))) / (l1 * l2), -1.0d, 1.0d);
                        double deg = Math.toDegrees(Math.acos(cosT));
                        if (deg < 2.0d) {
                            m = m2;
                            first2 = first;
                            last2 = last4;
                            i = size;
                            i2 = i4;
                        } else if (deg > 150.0d) {
                            m = m2;
                            first2 = first;
                            last2 = last4;
                            i = size;
                            i2 = i4;
                        } else {
                            m = m2;
                            first2 = first;
                            double d = Math.min(dmax, Math.min(l1 * 0.45d, 0.45d * l2));
                            double sx = bx + (((ax - bx) / l1) * d);
                            double sy = by + (((ay - by) / l1) * d);
                            double ex = bx + (((cx - bx) / l2) * d);
                            double ey = by + (((cy - by) / l2) * d);
                            int k = RangesKt.coerceIn((int) Math.ceil(deg / 5.0d), 2, 18) * 2;
                            int t0 = half == 1 ? k / 2 : 0;
                            int t1 = half == 2 ? k / 2 : k;
                            int s = t0;
                            if (s <= t1) {
                                while (true) {
                                    double l12 = l1;
                                    double t = s / k;
                                    int k2 = k;
                                    int t02 = t0;
                                    double d2 = 1;
                                    double a = (d2 - t) * (d2 - t);
                                    last2 = last4;
                                    double b = (d2 - t) * 2 * t;
                                    double c = t * t;
                                    int half2 = half;
                                    i = size;
                                    i2 = i4;
                                    round$add(oe, on, (a * sx) + (b * bx) + (c * ex), (a * sy) + (b * by) + (c * ey));
                                    if (s != t1) {
                                        s++;
                                        l1 = l12;
                                        k = k2;
                                        t0 = t02;
                                        last4 = last2;
                                        size = i;
                                        i4 = i2;
                                        half = half2;
                                    }
                                }
                            } else {
                                last2 = last4;
                                i = size;
                                i2 = i4;
                            }
                        }
                        round$add(oe, on, bx, by);
                    }
                    round$add(oe, on, bx, by);
                }
            } else {
                m = m2;
                first2 = first;
                oe = oe2;
                i = size;
                i2 = i4;
                last2 = last;
            }
            i4 = i2 + 1;
            oe2 = oe;
            m2 = m;
            first = first2;
            last = last2;
            size = i;
        }
        ArrayList oe3 = oe2;
        if (next == null) {
            Object obj9 = xe.get(xe.size() - 1);
            Intrinsics.checkNotNullExpressionValue(obj9, str);
            double doubleValue2 = ((Number) obj9).doubleValue();
            Object obj10 = xn.get(xn.size() - 1);
            Intrinsics.checkNotNullExpressionValue(obj10, str);
            round$add(oe3, on, doubleValue2, ((Number) obj10).doubleValue());
        }
        if (oe3.size() < 2) {
            return TuplesKt.to(e, n);
        }
        int size2 = oe3.size();
        double[] dArr = new double[size2];
        for (int i5 = 0; i5 < size2; i5++) {
            Object obj11 = oe3.get(i5);
            Intrinsics.checkNotNullExpressionValue(obj11, str);
            dArr[i5] = ((Number) obj11).doubleValue();
        }
        int size3 = on.size();
        double[] dArr2 = new double[size3];
        for (int i6 = 0; i6 < size3; i6++) {
            Object obj12 = on.get(i6);
            Intrinsics.checkNotNullExpressionValue(obj12, str);
            dArr2[i6] = ((Number) obj12).doubleValue();
        }
        return TuplesKt.to(dArr, dArr2);
    }

    private static final void round$add(ArrayList<Double> arrayList, ArrayList<Double> arrayList2, double x, double y) {
        if (arrayList.isEmpty() || Math.abs(((Number) CollectionsKt.last((List) arrayList)).doubleValue() - x) + Math.abs(((Number) CollectionsKt.last((List) arrayList2)).doubleValue() - y) > 1.0E-6d) {
            arrayList.add(Double.valueOf(x));
            arrayList2.add(Double.valueOf(y));
        }
    }

    public static /* synthetic */ Pair through$default(RoadShape roadShape, double[] dArr, double[] dArr2, double[] dArr3, double[] dArr4, double d, int i, Object obj) {
        if ((i & 16) != 0) {
            d = 3.0d;
        }
        return roadShape.through(dArr, dArr2, dArr3, dArr4, d);
    }

    public final Pair<double[], double[]> through(double[] e, double[] n, double[] prev, double[] next, double step) {
        int i;
        double x0;
        double y0;
        ArrayList oe;
        double x3;
        double y3;
        char c;
        int m;
        char c2;
        ArrayList oe2;
        double[] e2 = e;
        double[] n2 = n;
        Intrinsics.checkNotNullParameter(e2, "e");
        Intrinsics.checkNotNullParameter(n2, "n");
        int m2 = e2.length;
        if (m2 < 2) {
            return TuplesKt.to(e, n);
        }
        ArrayList oe3 = new ArrayList(m2 * 4);
        ArrayList on = new ArrayList(m2 * 4);
        char c3 = 0;
        oe3.add(Double.valueOf(e2[0]));
        on.add(Double.valueOf(n2[0]));
        int i2 = 0;
        int i3 = m2 - 1;
        while (i2 < i3) {
            double x1 = e2[i2];
            double y1 = n2[i2];
            double x2 = e2[i2 + 1];
            double y2 = n2[i2 + 1];
            if (i2 > 0) {
                double x02 = e2[i2 - 1];
                double y02 = n2[i2 - 1];
                i = i2;
                x0 = x02;
                y0 = y02;
            } else if (prev != null) {
                double x03 = prev[c3];
                double y03 = prev[1];
                i = i2;
                x0 = x03;
                y0 = y03;
            } else {
                i = i2;
                double d = 2;
                double x04 = (d * x1) - x2;
                double y04 = (d * y1) - y2;
                x0 = x04;
                y0 = y04;
            }
            int i4 = i;
            if (i4 < m2 - 2) {
                double x32 = e2[i4 + 2];
                oe = oe3;
                x3 = x32;
                y3 = n2[i4 + 2];
            } else if (next != null) {
                double x33 = next[0];
                double x34 = next[1];
                oe = oe3;
                x3 = x33;
                y3 = x34;
            } else {
                oe = oe3;
                double d2 = 2;
                double x35 = (d2 * x2) - x1;
                double d3 = (d2 * y2) - y1;
                x3 = x35;
                y3 = d3;
            }
            double x36 = x0;
            if (through$sharp(x36, y0, x1, y1, x2, y2)) {
                double d4 = 2;
                x0 = (d4 * x1) - x2;
                y0 = (d4 * y1) - y2;
            }
            if (through$sharp(x1, y1, x2, y2, x3, y3)) {
                c = 2;
                double d5 = 2;
                x3 = (d5 * x2) - x1;
                y3 = (d5 * y2) - y1;
            } else {
                c = 2;
            }
            double len = Math.hypot(x2 - x1, y2 - y1);
            int k = RangesKt.coerceIn((int) Math.ceil(len / step), 1, 60);
            if (k == 1) {
                m = m2;
                c2 = c;
                oe2 = oe;
            } else if (len < 1.0E-6d) {
                m = m2;
                c2 = c;
                oe2 = oe;
            } else {
                c2 = c;
                double t1 = 0.0d + through$kn(x0, y0, x1, y1);
                double t2 = t1 + through$kn(x1, y1, x2, y2);
                double t3 = t2 + through$kn(x2, y2, x3, y3);
                int s = 1;
                if (1 <= k) {
                    while (true) {
                        double t = t1 + (((t2 - t1) * s) / k);
                        double a1x = (((t1 - t) / (t1 - 0.0d)) * x0) + (((t - 0.0d) / (t1 - 0.0d)) * x1);
                        double a1y = (((t1 - t) / (t1 - 0.0d)) * y0) + (((t - 0.0d) / (t1 - 0.0d)) * y1);
                        double a2x = (((t2 - t) / (t2 - t1)) * x1) + (((t - t1) / (t2 - t1)) * x2);
                        double a2y = (((t2 - t) / (t2 - t1)) * y1) + (((t - t1) / (t2 - t1)) * y2);
                        double a3x = (((t3 - t) / (t3 - t2)) * x2) + (((t - t2) / (t3 - t2)) * x3);
                        double a3y = (((t3 - t) / (t3 - t2)) * y2) + (((t - t2) / (t3 - t2)) * y3);
                        double b1x = (((t2 - t) / (t2 - 0.0d)) * a1x) + (((t - 0.0d) / (t2 - 0.0d)) * a2x);
                        double b1y = (((t2 - t) / (t2 - 0.0d)) * a1y) + (((t - 0.0d) / (t2 - 0.0d)) * a2y);
                        double b2x = (((t3 - t) / (t3 - t1)) * a2x) + (((t - t1) / (t3 - t1)) * a3x);
                        double b2y = (((t3 - t) / (t3 - t1)) * a2y) + (((t - t1) / (t3 - t1)) * a3y);
                        m = m2;
                        oe2 = oe;
                        oe2.add(Double.valueOf((((t2 - t) / (t2 - t1)) * b1x) + (((t - t1) / (t2 - t1)) * b2x)));
                        on.add(Double.valueOf((((t2 - t) / (t2 - t1)) * b1y) + (((t - t1) / (t2 - t1)) * b2y)));
                        if (s != k) {
                            s++;
                            oe = oe2;
                            m2 = m;
                        }
                    }
                } else {
                    m = m2;
                    oe2 = oe;
                }
                i2 = i4 + 1;
                e2 = e;
                n2 = n;
                oe3 = oe2;
                m2 = m;
                c3 = 0;
            }
            oe2.add(Double.valueOf(x2));
            on.add(Double.valueOf(y2));
            i2 = i4 + 1;
            e2 = e;
            n2 = n;
            oe3 = oe2;
            m2 = m;
            c3 = 0;
        }
        ArrayList oe4 = oe3;
        int size = oe4.size();
        double[] dArr = new double[size];
        for (int i5 = 0; i5 < size; i5++) {
            Object obj = oe4.get(i5);
            Intrinsics.checkNotNullExpressionValue(obj, "get(...)");
            dArr[i5] = ((Number) obj).doubleValue();
        }
        int size2 = on.size();
        double[] dArr2 = new double[size2];
        for (int i6 = 0; i6 < size2; i6++) {
            Object obj2 = on.get(i6);
            Intrinsics.checkNotNullExpressionValue(obj2, "get(...)");
            dArr2[i6] = ((Number) obj2).doubleValue();
        }
        return TuplesKt.to(dArr, dArr2);
    }

    private static final boolean through$sharp(double ax, double ay, double bx, double by, double cx, double cy) {
        double l1 = Math.hypot(bx - ax, by - ay);
        double l2 = Math.hypot(cx - bx, cy - by);
        return l1 < 1.0E-6d || l2 < 1.0E-6d || (((bx - ax) * (cx - bx)) + ((by - ay) * (cy - by))) / (l1 * l2) < 0.17d;
    }

    private static final double through$kn(double ax, double ay, double bx, double by) {
        return RangesKt.coerceAtLeast(Math.sqrt(Math.hypot(bx - ax, by - ay)), 1.0E-4d);
    }

    public final List<double[]> crossings(OsmRoads.Way a, OsmRoads.Way b) {
        boolean z;
        boolean z2;
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(b, "b");
        if (a.getMaxE() < b.getMinE() || b.getMaxE() < a.getMinE() || a.getMaxN() < b.getMinN() || b.getMaxN() < a.getMinN()) {
            return CollectionsKt.emptyList();
        }
        ArrayList out = new ArrayList();
        boolean z3 = true;
        int length = a.getE().length - 1;
        for (int i = 0; i < length; i++) {
            double ax = a.getE()[i];
            double ay = a.getN()[i];
            double dx = a.getE()[i + 1] - ax;
            double dy = a.getN()[i + 1] - ay;
            int length2 = b.getE().length - 1;
            int j = 0;
            while (j < length2) {
                double bx = b.getE()[j];
                double by = b.getN()[j];
                double fx = b.getE()[j + 1] - bx;
                double fy = b.getN()[j + 1] - by;
                int i2 = length;
                int i3 = length2;
                int j2 = j;
                if (Math.max(ax, ax + dx) < Math.min(bx, bx + fx) || Math.max(bx, bx + fx) < Math.min(ax, ax + dx)) {
                    z = true;
                } else if (Math.max(ay, ay + dy) < Math.min(by, by + fy)) {
                    z = true;
                } else if (Math.max(by, by + fy) < Math.min(ay, ay + dy)) {
                    z = true;
                } else {
                    double den = (dx * fy) - (dy * fx);
                    if (Math.abs(den) >= 1.0E-9d) {
                        double t = (((bx - ax) * fy) - ((by - ay) * fx)) / den;
                        double u = (((bx - ax) * dy) - ((by - ay) * dx)) / den;
                        if (t < -1.0E-9d || t > 1.000000001d || u < -1.0E-9d) {
                            z = true;
                        } else if (u > 1.000000001d) {
                            z = true;
                        } else {
                            double px = ax + (t * dx);
                            double py = ay + (t * dy);
                            if (near(a, px, py) && near(b, px, py)) {
                                z = true;
                            } else {
                                ArrayList $this$none$iv = out;
                                int $i$f$none = 0;
                                char c = 0;
                                if (($this$none$iv instanceof Collection) && $this$none$iv.isEmpty()) {
                                    z2 = true;
                                } else {
                                    Iterator it = $this$none$iv.iterator();
                                    while (true) {
                                        if (!it.hasNext()) {
                                            z2 = true;
                                            break;
                                        }
                                        Object element$iv = it.next();
                                        double[] it2 = (double[]) element$iv;
                                        Iterable $this$none$iv2 = $this$none$iv;
                                        int $i$f$none2 = $i$f$none;
                                        if (Math.hypot(it2[c] - px, it2[1] - py) < 5.0d) {
                                            z2 = false;
                                            break;
                                        }
                                        $this$none$iv = $this$none$iv2;
                                        $i$f$none = $i$f$none2;
                                        c = 0;
                                    }
                                }
                                if (z2) {
                                    z = true;
                                    out.add(new double[]{px, py});
                                } else {
                                    z = true;
                                }
                            }
                        }
                    } else {
                        z = true;
                    }
                }
                j = j2 + 1;
                z3 = z;
                length = i2;
                length2 = i3;
            }
        }
        return out;
    }

    private final boolean near(OsmRoads.Way w, double x, double y) {
        int length = w.getE().length;
        for (int i = 0; i < length; i++) {
            if (Math.hypot(w.getE()[i] - x, w.getN()[i] - y) < 1.0d) {
                return true;
            }
        }
        return false;
    }

    public final double[] heights(double[] xs, double[] ys, int n, List<double[]> cross, double flat, double ramp, double top) {
        Intrinsics.checkNotNullParameter(xs, "xs");
        Intrinsics.checkNotNullParameter(ys, "ys");
        Intrinsics.checkNotNullParameter(cross, "cross");
        double[] h = new double[n];
        for (int i = 0; i < n; i++) {
            double d = Double.MAX_VALUE;
            for (double[] c : cross) {
                d = Math.min(d, Math.hypot(xs[i] - c[0], ys[i] - c[1]));
            }
            double f = RangesKt.coerceIn(((flat + ramp) - d) / ramp, 0.0d, 1.0d);
            h[i] = top * f * f * (3 - (2 * f));
        }
        return h;
    }
}
