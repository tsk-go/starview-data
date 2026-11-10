package com.starpilot.starview;

import com.starpilot.starview.LaneMemory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import kotlin.Pair;
import kotlin.Triple;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

/* compiled from: LaneMemory.kt */
public final class LaneMemory {
    private double lastOdo;
    private final ArrayList<Mark> marks = new ArrayList<>();
    private volatile List<Pair<Float, Float>> lines = CollectionsKt.emptyList();
    private final float lineView = 5.6f;

    /* compiled from: LaneMemory.kt */
    public static final class Mark {
        private float conf;
        private final boolean edge;
        private double lastOdo;
        private int seen;
        private float y;

        public Mark(float y, boolean edge, float conf, double lastOdo) {
            this.y = y;
            this.edge = edge;
            this.conf = conf;
            this.lastOdo = lastOdo;
        }

        public final float getConf() {
            return this.conf;
        }

        public final boolean getEdge() {
            return this.edge;
        }

        public final double getLastOdo() {
            return this.lastOdo;
        }

        public final float getY() {
            return this.y;
        }

        public final void setConf(float f) {
            this.conf = f;
        }

        public final void setLastOdo(double d) {
            this.lastOdo = d;
        }

        public final void setY(float f) {
            this.y = f;
        }

        public final int getSeen() {
            return this.seen;
        }

        public final void setSeen(int i) {
            this.seen = i;
        }
    }

    public final ArrayList<Mark> getMarks() {
        return this.marks;
    }

    public final List<Pair<Float, Float>> getLines() {
        return this.lines;
    }

    public final void clear() {
        this.marks.clear();
        this.lines = CollectionsKt.emptyList();
    }

    public final void shift(float dy) {
        Iterator<Mark> it = this.marks.iterator();
        Intrinsics.checkNotNullExpressionValue(it, "iterator(...)");
        while (it.hasNext()) {
            Mark next = it.next();
            Intrinsics.checkNotNullExpressionValue(next, "next(...)");
            Mark m = next;
            m.setY(m.getY() - dy);
        }
    }

    public final void update(double odo, List<Pair<Float, Float>> lines, float edgeL, float edgeR, boolean frozen) {
        boolean z;
        double d = odo;
        Intrinsics.checkNotNullParameter(lines, "lines");
        double dOdo = RangesKt.coerceAtLeast(d - this.lastOdo, 0.0d);
        this.lastOdo = d;
        if (dOdo > 0.0d) {
            float k = (float) Math.exp((-dOdo) / 1500.0d);
            Iterator<Mark> it = this.marks.iterator();
            Intrinsics.checkNotNullExpressionValue(it, "iterator(...)");
            while (it.hasNext()) {
                Mark next = it.next();
                Intrinsics.checkNotNullExpressionValue(next, "next(...)");
                Mark m = next;
                if (Math.abs(m.getY()) > this.lineView || m.getEdge()) {
                    m.setConf(m.getConf() * k);
                }
            }
        }
        if (frozen) {
            prune();
            return;
        }
        ArrayList obs = new ArrayList();
        Iterator<Pair<Float, Float>> it2 = lines.iterator();
        while (true) {
            z = false;
            if (!it2.hasNext()) {
                break;
            }
            Pair<Float, Float> next2 = it2.next();
            float y = next2.component1().floatValue();
            float p = next2.component2().floatValue();
            if (((Float.isInfinite(y) || Float.isNaN(y)) ? false : true) && p > 0.5f) {
                obs.add(new Triple(Float.valueOf(y), false, Float.valueOf(p)));
                it2 = it2;
            }
        }
        if ((Float.isInfinite(edgeL) || Float.isNaN(edgeL)) ? false : true) {
            obs.add(new Triple(Float.valueOf(-edgeL), true, Float.valueOf(0.8f)));
        }
        if ((Float.isInfinite(edgeR) || Float.isNaN(edgeR)) ? false : true) {
            obs.add(new Triple(Float.valueOf(edgeR), true, Float.valueOf(0.8f)));
        }
        if (obs.isEmpty()) {
            prune();
            return;
        }
        ArrayList d2 = new ArrayList();
        Iterator it3 = obs.iterator();
        Intrinsics.checkNotNullExpressionValue(it3, "iterator(...)");
        while (it3.hasNext()) {
            Object next3 = it3.next();
            Intrinsics.checkNotNullExpressionValue(next3, "next(...)");
            Triple triple = (Triple) next3;
            float y2 = ((Number) triple.component1()).floatValue();
            if (((Boolean) triple.component2()).booleanValue()) {
                z = false;
            } else {
                Mark it4 = nearest(y2, z, 0.7f);
                if (it4 != null) {
                    d2.add(Float.valueOf(y2 - it4.getY()));
                    z = false;
                } else {
                    z = false;
                }
            }
        }
        if (!d2.isEmpty()) {
            CollectionsKt.sort(d2);
            Object obj = d2.get(d2.size() / 2);
            Intrinsics.checkNotNullExpressionValue(obj, "get(...)");
            float s = ((Number) obj).floatValue();
            if (d2.size() >= 2 || Math.abs(s) < 0.3f) {
                Iterator<Mark> it5 = this.marks.iterator();
                Intrinsics.checkNotNullExpressionValue(it5, "iterator(...)");
                while (it5.hasNext()) {
                    Mark next4 = it5.next();
                    Intrinsics.checkNotNullExpressionValue(next4, "next(...)");
                    Mark m2 = next4;
                    m2.setY(m2.getY() + s);
                }
            }
        }
        HashSet matched = new HashSet();
        Iterator it6 = obs.iterator();
        Intrinsics.checkNotNullExpressionValue(it6, "iterator(...)");
        while (it6.hasNext()) {
            Object next5 = it6.next();
            Intrinsics.checkNotNullExpressionValue(next5, "next(...)");
            Triple triple2 = (Triple) next5;
            float y3 = ((Number) triple2.component1()).floatValue();
            boolean e = ((Boolean) triple2.component2()).booleanValue();
            float p2 = ((Number) triple2.component3()).floatValue();
            Mark it7 = nearest(y3, e, e ? 0.8f : 0.45f);
            Mark m3 = (it7 == null || matched.contains(it7)) ? null : it7;
            if (m3 != null) {
                m3.setY(m3.getY() + ((e ? 0.1f : 0.25f) * (y3 - m3.getY())));
                m3.setConf(Math.min(1.0f, m3.getConf() + (0.04f * p2)));
                m3.setLastOdo(d);
                m3.setSeen(m3.getSeen() + 1);
                matched.add(m3);
                d2 = d2;
            } else {
                ArrayList d3 = d2;
                if (p2 > 0.6f) {
                    HashSet matched2 = matched;
                    Mark n = new Mark(y3, e, 0.25f, odo);
                    n.setSeen(1);
                    this.marks.add(n);
                    matched2.add(n);
                    matched = matched2;
                    d2 = d3;
                    it6 = it6;
                    d = odo;
                } else {
                    d2 = d3;
                    d = odo;
                }
            }
        }
        HashSet matched3 = matched;
        Iterator<Mark> it8 = this.marks.iterator();
        Intrinsics.checkNotNullExpressionValue(it8, "iterator(...)");
        while (it8.hasNext()) {
            Mark next6 = it8.next();
            Intrinsics.checkNotNullExpressionValue(next6, "next(...)");
            Mark m4 = next6;
            if (!matched3.contains(m4) && !m4.getEdge() && Math.abs(m4.getY()) <= this.lineView) {
                m4.setConf(m4.getConf() - 0.01f);
            }
        }
        Iterator<Mark> it9 = this.marks.iterator();
        Intrinsics.checkNotNullExpressionValue(it9, "iterator(...)");
        while (it9.hasNext()) {
            Mark next7 = it9.next();
            Intrinsics.checkNotNullExpressionValue(next7, "next(...)");
            Mark m5 = next7;
            if (!matched3.contains(m5) && m5.getEdge()) {
                if (((Float.isInfinite(edgeL) || Float.isNaN(edgeL)) ? false : true) && m5.getY() < 0.0f && m5.getY() > (-edgeL) + 0.8f) {
                    m5.setConf(m5.getConf() - 0.03f);
                }
            }
        }
        Iterator<Mark> it10 = this.marks.iterator();
        Intrinsics.checkNotNullExpressionValue(it10, "iterator(...)");
        while (it10.hasNext()) {
            Mark next8 = it10.next();
            Intrinsics.checkNotNullExpressionValue(next8, "next(...)");
            Mark m6 = next8;
            if (!matched3.contains(m6) && m6.getEdge()) {
                if (((Float.isInfinite(edgeR) || Float.isNaN(edgeR)) ? false : true) && m6.getY() > 0.0f && m6.getY() < edgeR - 0.8f) {
                    m6.setConf(m6.getConf() - 0.03f);
                }
            }
        }
        prune();
    }

    private final Mark nearest(float y, boolean edge, float tol) {
        Mark best = null;
        float bd = tol;
        Iterator<Mark> it = this.marks.iterator();
        Intrinsics.checkNotNullExpressionValue(it, "iterator(...)");
        while (it.hasNext()) {
            Mark next = it.next();
            Intrinsics.checkNotNullExpressionValue(next, "next(...)");
            Mark m = next;
            if (m.getEdge() == edge) {
                float dd = Math.abs(m.getY() - y);
                if (dd < bd) {
                    bd = dd;
                    best = m;
                }
            }
        }
        return best;
    }

    private final void prune() {
        pruneMarks();
        Iterable $this$filter$iv = this.marks;
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            if (!((Mark) element$iv$iv).getEdge()) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        Iterable $this$map$iv = (List) destination$iv$iv;
        Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        for (Object item$iv$iv : $this$map$iv) {
            Mark it = (Mark) item$iv$iv;
            destination$iv$iv2.add(TuplesKt.to(Float.valueOf(it.getY()), Float.valueOf(it.getConf())));
        }
        this.lines = (List) destination$iv$iv2;
    }

    private final void pruneMarks() {
        Object maxElem$iv;
        CollectionsKt.removeAll((List) this.marks, new Function1() { // from class: com.starpilot.starview.LaneMemory$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                boolean pruneMarks$lambda$4;
                pruneMarks$lambda$4 = LaneMemory.pruneMarks$lambda$4((LaneMemory.Mark) obj);
                return Boolean.valueOf(pruneMarks$lambda$4);
            }
        });
        List $this$sortBy$iv = this.marks;
        if ($this$sortBy$iv.size() > 1) {
            CollectionsKt.sortWith($this$sortBy$iv, new Comparator() { // from class: com.starpilot.starview.LaneMemory$pruneMarks$$inlined$sortBy$1
                /* JADX WARN: Multi-variable type inference failed */
                @Override // java.util.Comparator
                public final int compare(Object t, Object t2) {
                    LaneMemory.Mark it = (LaneMemory.Mark) t;
                    LaneMemory.Mark it2 = (LaneMemory.Mark) t2;
                    return ComparisonsKt.compareValues(Float.valueOf(it.getY()), Float.valueOf(it2.getY()));
                }
            });
        }
        int i = 1;
        while (i < this.marks.size()) {
            Mark mark = this.marks.get(i - 1);
            Intrinsics.checkNotNullExpressionValue(mark, "get(...)");
            Mark a = mark;
            Mark mark2 = this.marks.get(i);
            Intrinsics.checkNotNullExpressionValue(mark2, "get(...)");
            Mark b = mark2;
            if (a.getEdge() != b.getEdge() || Math.abs(a.getY() - b.getY()) >= 0.6f) {
                i++;
            } else {
                Intrinsics.checkNotNull(a.getConf() >= b.getConf() ? this.marks.remove(i) : this.marks.remove(i - 1));
            }
        }
        Iterable $this$filter$iv = this.marks;
        Collection destination$iv$iv = new ArrayList();
        Iterator it = $this$filter$iv.iterator();
        while (true) {
            boolean z = false;
            if (!it.hasNext()) {
                break;
            }
            Object element$iv$iv = it.next();
            Mark it2 = (Mark) element$iv$iv;
            if (it2.getEdge() && it2.getY() < 0.0f && it2.getConf() >= 0.3f) {
                z = true;
            }
            if (z) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        Iterable $this$maxByOrNull$iv = (List) destination$iv$iv;
        Iterator iterator$iv = $this$maxByOrNull$iv.iterator();
        Object minElem$iv = null;
        if (iterator$iv.hasNext()) {
            maxElem$iv = iterator$iv.next();
            if (iterator$iv.hasNext()) {
                Mark it3 = (Mark) maxElem$iv;
                float maxValue$iv = it3.getY();
                do {
                    Object e$iv = iterator$iv.next();
                    Mark it4 = (Mark) e$iv;
                    float v$iv = it4.getY();
                    if (Float.compare(maxValue$iv, v$iv) < 0) {
                        maxElem$iv = e$iv;
                        maxValue$iv = v$iv;
                    }
                } while (iterator$iv.hasNext());
            }
        } else {
            maxElem$iv = null;
        }
        final Mark eL = (Mark) maxElem$iv;
        Iterable $this$filter$iv2 = this.marks;
        Collection destination$iv$iv2 = new ArrayList();
        for (Object element$iv$iv2 : $this$filter$iv2) {
            Mark it5 = (Mark) element$iv$iv2;
            Mark it6 = (!it5.getEdge() || it5.getY() <= 0.0f || it5.getConf() < 0.3f) ? null : 1;
            if (it6 != null) {
                destination$iv$iv2.add(element$iv$iv2);
            }
        }
        Iterable $this$minByOrNull$iv = (List) destination$iv$iv2;
        Iterator iterator$iv2 = $this$minByOrNull$iv.iterator();
        if (iterator$iv2.hasNext()) {
            minElem$iv = iterator$iv2.next();
            if (iterator$iv2.hasNext()) {
                Mark it7 = (Mark) minElem$iv;
                float minValue$iv = it7.getY();
                do {
                    Object e$iv2 = iterator$iv2.next();
                    Mark it8 = (Mark) e$iv2;
                    float v$iv2 = it8.getY();
                    if (Float.compare(minValue$iv, v$iv2) > 0) {
                        minValue$iv = v$iv2;
                        minElem$iv = e$iv2;
                    }
                } while (iterator$iv2.hasNext());
            }
        }
        final Mark eR = (Mark) minElem$iv;
        CollectionsKt.removeAll((List) this.marks, new Function1() { // from class: com.starpilot.starview.LaneMemory$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                boolean pruneMarks$lambda$10;
                pruneMarks$lambda$10 = LaneMemory.pruneMarks$lambda$10(eL, eR, (LaneMemory.Mark) obj);
                return Boolean.valueOf(pruneMarks$lambda$10);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean pruneMarks$lambda$4(Mark it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return it.getConf() < 0.1f || Math.abs(it.getY()) > 30.0f;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean pruneMarks$lambda$10(Mark $eL, Mark $eR, Mark it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return (($eL == null || it == $eL || it.getY() >= $eL.getY() - 0.8f) && ($eR == null || it == $eR || it.getY() <= $eR.getY() + 0.8f)) ? false : true;
    }

    public static /* synthetic */ Pair lanes$default(LaneMemory laneMemory, int i, float f, float f2, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            f2 = 0.45f;
        }
        return laneMemory.lanes(i, f, f2);
    }

    /* VF: from Vineflower (jadx: Removed duplicated region;incorrect) */
    public final Pair<Integer, Boolean> lanes(int var1, float var2, float var3) {
        boolean var8 = Float.isInfinite(var2);
        boolean var9 = false;
        int var6;
        if (!var8 && !Float.isNaN(var2)) {
            var6 = 1;
        } else {
            var6 = 0;
        }

        if ((var6 == 0)) {
            return null;
        } else {
            Iterable var11 = this.marks;
            var var10 = new ArrayList();

            for (Object var13 : var11) {
                label138: {
                    LaneMemory.Mark var12 = (LaneMemory.Mark)var13;
                    if (!var12.getEdge() && var12.getConf() >= var3) {
                        float var4 = var12.getY();
                        if (var1 < 0 ? !(var4 < var2 - 1.5F) : !(var4 > 1.5F + var2)) {
                            var6 = (((boolean)false) ? 1 : 0);
                        } else {
                            var6 = (((boolean)true) ? 1 : 0);
                        }

                        if ((var6 != 0)) {
                            var6 = (((boolean)true) ? 1 : 0);
                            break label138;
                        }
                    }

                    var6 = (((boolean)false) ? 1 : 0);
                }

                if ((var6 != 0)) {
                    var10.add(var13);
                }
            }

            List var25 = var10;
            final var var2$f = var2;
            List var26 = CollectionsKt.sortedWith(
                var25,
                new Comparator() {
                    final float $ourLine$inlined = var2$f;

                    @Override
                    public final int compare(Object var1, Object var2x) {
                        return ComparisonsKt.compareValues(
                            Math.abs(((LaneMemory.Mark)var1).getY() - this.$ourLine$inlined), Math.abs(((LaneMemory.Mark)var2x).getY() - this.$ourLine$inlined)
                        );
                    }
                }
            );
            var6 = 0;

            for (LaneMemory.Mark var33 : (Iterable<LaneMemory.Mark>) var26) {
                var3 = Math.abs(var33.getY() - var2);
                if (!(var3 < 2.3F)) {
                    if (var3 > 4.6F) {
                        break;
                    }

                    var6++;
                    var2 = var33.getY();
                }
            }

            var11 = this.marks;
            var10 = new ArrayList();

            for (Object var37 : var11) {
                boolean var23;
                label109: {
                    LaneMemory.Mark var39 = (LaneMemory.Mark)var37;
                    if (var39.getEdge() && var39.getConf() >= 0.35F) {
                        var3 = var39.getY();
                        if (var1 < 0 ? !(var3 < 0.0F) : !(var3 > 0.0F)) {
                            var23 = false;
                        } else {
                            var23 = true;
                        }

                        if (var23) {
                            var23 = true;
                            break label109;
                        }
                    }

                    var23 = false;
                }

                if (var23) {
                    var10.add(var37);
                }
            }

            List var29 = var10;
            Iterator var40 = var29.iterator();
            Object var30;
            if (!var40.hasNext()) {
                var30 = null;
            } else {
                LaneMemory.Mark var30ss = (LaneMemory.Mark)var40.next();
                if (var40.hasNext()) {
                    float var19 = Math.abs(var30ss.getY());
                    var11 = ((Iterable) var30ss);

                    do {
                        Object var38 = var40.next();
                        float var5 = Math.abs(((LaneMemory.Mark)var38).getY());
                        var30 = var11;
                        var3 = var19;
                        if (Float.compare(var19, var5) > 0) {
                            LaneMemory.Mark var30s = (LaneMemory.Mark)var38;
                            var3 = var5;
                        }

                        var11 = ((Iterable) var30);
                        var19 = var3;
                    } while (var40.hasNext());
                }
            }

            var30 = var30;
            var8 = var9;
            if (var30 != null) {
                var8 = var9;
                if (Math.abs(var30.getY() - var2) < 1.8F) {
                    var3 = var30.getY();
                    boolean var14;
                    if (var1 < 0 ? !(var3 <= 0.3F + var2) : !(var3 >= var2 - 0.3F)) {
                        var14 = false;
                    } else {
                        var14 = true;
                    }

                    var8 = var9;
                    if (var14) {
                        var8 = true;
                    }
                }
            }

            return var6 == 0 && !var8 ? null : TuplesKt.to(var6, var8);
        }
    }
}
