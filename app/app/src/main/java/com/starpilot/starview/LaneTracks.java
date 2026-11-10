package com.starpilot.starview;

import com.starpilot.starview.LaneMarks;
import com.starpilot.starview.LaneTracks;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Pair;
import kotlin.Triple;
import kotlin.TuplesKt;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.reflect.KFunction;

/* compiled from: LaneTracks.kt */
public final class LaneTracks {
    public static final Companion Companion = new Companion(null);
    public static final float HIGH_EFFORT = 0.65f;
    public static final long MARK_HOLD_MS = 4000;
    public static final float MATCH_M = 1.5f;
    public static final float MAX_LAT_ACCEL = 3.0f;
    private static final int ORANGE = -34786;
    public static final float OUTER_LINE_PROB = 0.3f;
    private static final int RED = -1364701;
    private boolean approaching;
    private float changeAlpha;
    private int changeDir;
    private boolean crossed;
    private float effort;
    private float laneWidth;
    private long lastFrame;
    private final ArrayList<Line> lines;
    private Line refAhead;
    private Line refLeft;
    private Line refRight;
    private final float smoothRc;

    public LaneTracks() {
        this(0.0f, 1, null);
    }

    /* compiled from: LaneTracks.kt */
    public static final class Line {
        private float alpha;
        private LaneMarks.Mark mark;
        private long markMs;
        private boolean on;
        private float prob;
        private boolean seen;
        private int slot;
        private float[] x;
        private float[] y;
        private float[] z;

        public Line(float[] x, float[] y, float[] z) {
            Intrinsics.checkNotNullParameter(x, "x");
            Intrinsics.checkNotNullParameter(y, "y");
            Intrinsics.checkNotNullParameter(z, "z");
            this.x = x;
            this.y = y;
            this.z = z;
            this.seen = true;
            this.slot = -1;
        }

        public final float[] getX() {
            return this.x;
        }

        public final float[] getY() {
            return this.y;
        }

        public final float[] getZ() {
            return this.z;
        }

        public final void setX(float[] fArr) {
            Intrinsics.checkNotNullParameter(fArr, "<set-?>");
            this.x = fArr;
        }

        public final void setY(float[] fArr) {
            Intrinsics.checkNotNullParameter(fArr, "<set-?>");
            this.y = fArr;
        }

        public final void setZ(float[] fArr) {
            Intrinsics.checkNotNullParameter(fArr, "<set-?>");
            this.z = fArr;
        }

        public final float getAlpha() {
            return this.alpha;
        }

        public final boolean getOn() {
            return this.on;
        }

        public final float getProb() {
            return this.prob;
        }

        public final boolean getSeen() {
            return this.seen;
        }

        public final void setAlpha(float f) {
            this.alpha = f;
        }

        public final void setOn(boolean z) {
            this.on = z;
        }

        public final void setProb(float f) {
            this.prob = f;
        }

        public final void setSeen(boolean z) {
            this.seen = z;
        }

        public final int getSlot() {
            return this.slot;
        }

        public final void setSlot(int i) {
            this.slot = i;
        }

        public final LaneMarks.Mark getMark() {
            return this.mark;
        }

        public final long getMarkMs() {
            return this.markMs;
        }

        public final void setMark(LaneMarks.Mark mark) {
            this.mark = mark;
        }

        public final void setMarkMs(long j) {
            this.markMs = j;
        }

        public final float getY0() {
            return at(5.0f);
        }

        public final float at(float xq) {
            if (this.x.length == 0) {
                return 0.0f;
            }
            int j = 1;
            while (j < this.x.length - 1 && this.x[j] < xq) {
                j++;
            }
            if (this.x.length < 2) {
                return this.y[0];
            }
            float f = RangesKt.coerceIn((xq - this.x[j - 1]) / Math.max(0.01f, this.x[j] - this.x[j - 1]), 0.0f, 1.0f);
            return this.y[j - 1] + ((this.y[j] - this.y[j - 1]) * f);
        }
    }

    public LaneTracks(float smoothRc) {
        this.smoothRc = smoothRc;
        this.lines = new ArrayList<>();
        this.lastFrame = -1L;
        this.laneWidth = 3.5f;
    }

    public /* synthetic */ LaneTracks(float f, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? 0.25f : f);
    }

    public final ArrayList<Line> getLines() {
        return this.lines;
    }

    public final float getEffort() {
        return this.effort;
    }

    public static /* synthetic */ void update$default(LaneTracks laneTracks, ModelSnapshot modelSnapshot, Telemetry telemetry, float f, boolean z, LaneMarks.Mark[] markArr, int i, Object obj) {
        if ((i & 16) != 0) {
            markArr = null;
        }
        laneTracks.update(modelSnapshot, telemetry, f, z, markArr);
    }

    /* VF: from Vineflower (jadx: Code restructure failed), retyped by hand */
    public final void update(ModelSnapshot m, Telemetry t, float dt, boolean follow, LaneMarks.Mark[] marks) {
        Intrinsics.checkNotNullParameter(m, "m");
        Intrinsics.checkNotNullParameter(t, "t");
        if (m.getFrameId() == this.lastFrame) {
            return;
        }
        this.lastFrame = m.getFrameId();
        float a = RangesKt.coerceIn(dt / (this.smoothRc + dt), 0.0F, 1.0F);
        float k;
        if (!follow) {
            k = 1.0F;
        } else if (t.getVEgo() < 0.5F) {
            k = 0.0F;
        } else if (t.getVEgo() < 3.0F) {
            k = 0.3F * a;
        } else {
            k = a;
        }
        Iterator it0 = this.lines.iterator();
        Intrinsics.checkNotNullExpressionValue(it0, "iterator(...)");
        while (it0.hasNext()) {
            Object nx = it0.next();
            Intrinsics.checkNotNullExpressionValue(nx, "next(...)");
            Line l = (Line) nx;
            l.setSeen(false);
            l.setSlot(-1);
        }
        long now = System.currentTimeMillis();
        int n = Math.min(4, m.getLaneX().length);
        for (int i = 0; i < n; i++) {
            float[] xs = m.getLaneX()[i];
            if (xs.length < 2 || i >= m.getLaneY().length) {
                continue;
            }
            float[] probs = m.getLaneProb();
            float prob = (i >= 0 && i < probs.length) ? probs[i] : 0.0F;
            if (m.getLaneX().length == 4 && (i == 0 || i == 3) && !Companion.roadThere(m, i, prob)) {
                continue;
            }
            float[] ys = m.getLaneY()[i];
            float[][] zz = m.getLaneZ();
            float[] zs = (i >= 0 && i < zz.length) ? zz[i] : new float[xs.length];
            Line fresh = new Line(xs, ys, zs);
            float y0 = fresh.getY0();
            ArrayList free = new ArrayList();
            for (Object o : this.lines) {
                if (!((Line) o).getSeen()) {
                    free.add(o);
                }
            }
            Iterator fi = free.iterator();
            Object best = null;
            if (fi.hasNext()) {
                best = fi.next();
                if (fi.hasNext()) {
                    float bestD = Math.abs(((Line) best).getY0() - y0);
                    do {
                        Object o = fi.next();
                        float d = Math.abs(((Line) o).getY0() - y0);
                        if (Float.compare(bestD, d) > 0) {
                            best = o;
                            bestD = d;
                        }
                    } while (fi.hasNext());
                }
            }
            Line match = (Line) best;
            Line line;
            if (match != null && Math.abs(match.getY0() - y0) < 1.5F && match.getY().length == fresh.getY().length) {
                int len = match.getY().length;
                for (int j = 0; j < len; j++) {
                    float[] my = match.getY();
                    my[j] += (fresh.getY()[j] - match.getY()[j]) * k;
                    float[] mz = match.getZ();
                    mz[j] += (fresh.getZ()[j] - match.getZ()[j]) * k;
                }
                match.setX(xs);
                line = match;
            } else {
                float[] fy = fresh.getY();
                float[] cy = Arrays.copyOf(fy, fy.length);
                Intrinsics.checkNotNullExpressionValue(cy, "copyOf(...)");
                float[] fz = fresh.getZ();
                float[] cz = Arrays.copyOf(fz, fz.length);
                Intrinsics.checkNotNullExpressionValue(cz, "copyOf(...)");
                line = new Line(xs, cy, cz);
                this.lines.add(line);
            }
            line.setSeen(true);
            line.setSlot(i);
            line.setProb(line.getProb() + (prob - line.getProb()) * a);
            LaneMarks.Mark mark = null;
            if (marks != null) {
                mark = (LaneMarks.Mark) ArraysKt.getOrNull(marks, i);
            }
            if (mark != null && (mark.getKind() == 2 || mark.getKind() == 3)) {
                line.setMark(mark);
                line.setMarkMs(now);
            } else if (mark != null && mark.getKind() == 1 && now - line.getMarkMs() > 4000L) {
                line.setMark(mark);
                line.setMarkMs(now);
            }
        }
        Iterator it1 = this.lines.iterator();
        Intrinsics.checkNotNullExpressionValue(it1, "iterator(...)");
        while (it1.hasNext()) {
            Object nx = it1.next();
            Intrinsics.checkNotNullExpressionValue(nx, "next(...)");
            Line l = (Line) nx;
            boolean on = l.getSeen() && (l.getOn() ? l.getProb() > 0.04F : l.getProb() > 0.1F);
            l.setOn(on);
            float target = l.getOn() ? RangesKt.coerceIn(l.getProb() * 2.0F, 0.4F, 1.0F) : 0.0F;
            float r = target > l.getAlpha() ? a : dt / (dt + 1.0F);
            l.setAlpha(l.getAlpha() + (target - l.getAlpha()) * r);
        }
        CollectionsKt.removeAll(this.lines, new LaneTracks$$ExternalSyntheticLambda0());
        updateLaneChange(m, a);
        Float c = Float.valueOf(m.getModelDesiredCurvature());
        Float cv = c.floatValue() == 0.0F ? null : c;
        float curv = cv != null ? cv.floatValue() : t.getDesiredCurvature();
        float e = Math.abs(curv) * t.getVEgo() * t.getVEgo() / 3.0F;
        if (Companion.pathState(t) == PathState.ENGAGED) {
            e = RangesKt.coerceIn(Math.max(Math.abs(t.getOutTorque()), e), 0.0F, 1.0F);
        } else {
            e = 0.0F;
        }
        this.effort = this.effort + (e - this.effort) * a;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean update$lambda$5(Line it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return !it.getSeen() && it.getAlpha() < 0.02f;
    }

    public final float getChangeAlpha() {
        return this.changeAlpha;
    }

    public final int getChangeDir() {
        return this.changeDir;
    }

    public final boolean getCrossed() {
        return this.crossed;
    }

    public final float getLaneWidth() {
        return this.laneWidth;
    }

    private final void updateLaneChange(ModelSnapshot m, float k) {
        Object maxElem$iv;
        boolean z = true;
        boolean changing = m.getLaneChanging() && m.getLaneChangeDir() != 0;
        this.changeAlpha += ((changing ? 1.0f : 0.0f) - this.changeAlpha) * k;
        Iterable $this$filter$iv = this.lines;
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            Line it = (Line) element$iv$iv;
            boolean it2 = !((!it.getSeen() || it.getY0() >= 0.0f));
            if (it2) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        Iterable $this$maxByOrNull$iv = (List) destination$iv$iv;
        Iterator iterator$iv = $this$maxByOrNull$iv.iterator();
        Object minElem$iv = null;
        if (iterator$iv.hasNext()) {
            maxElem$iv = iterator$iv.next();
            if (iterator$iv.hasNext()) {
                Line it3 = (Line) maxElem$iv;
                float maxValue$iv = it3.getY0();
                do {
                    Object e$iv = iterator$iv.next();
                    Line it4 = (Line) e$iv;
                    float v$iv = it4.getY0();
                    if (Float.compare(maxValue$iv, v$iv) < 0) {
                        maxElem$iv = e$iv;
                        maxValue$iv = v$iv;
                    }
                } while (iterator$iv.hasNext());
            }
        } else {
            maxElem$iv = null;
        }
        Line left = (Line) maxElem$iv;
        Iterable $this$filter$iv2 = this.lines;
        Collection destination$iv$iv2 = new ArrayList();
        for (Object element$iv$iv2 : $this$filter$iv2) {
            Line it5 = (Line) element$iv$iv2;
            boolean it6 = !((!it5.getSeen() || it5.getY0() < 0.0f));
            if (it6) {
                destination$iv$iv2.add(element$iv$iv2);
            }
        }
        Iterable $this$minByOrNull$iv = (List) destination$iv$iv2;
        Iterator iterator$iv2 = $this$minByOrNull$iv.iterator();
        if (iterator$iv2.hasNext()) {
            minElem$iv = iterator$iv2.next();
            if (iterator$iv2.hasNext()) {
                Line it7 = (Line) minElem$iv;
                float minValue$iv = it7.getY0();
                do {
                    Object e$iv2 = iterator$iv2.next();
                    Line it8 = (Line) e$iv2;
                    float v$iv2 = it8.getY0();
                    if (Float.compare(minValue$iv, v$iv2) > 0) {
                        minValue$iv = v$iv2;
                        minElem$iv = e$iv2;
                    }
                } while (iterator$iv2.hasNext());
            }
        }
        Line right = (Line) minElem$iv;
        if (left == null || right == null) {
            return;
        }
        this.refLeft = left;
        this.refRight = right;
        if (!changing) {
            this.approaching = false;
            this.crossed = false;
            this.laneWidth = RangesKt.coerceIn(right.getY0() - left.getY0(), 2.5f, 4.5f);
            return;
        }
        this.changeDir = m.getLaneChangeDir();
        Pair pair = this.changeDir < 0 ? TuplesKt.to(left, right) : TuplesKt.to(right, left);
        Line ahead = (Line) pair.component1();
        Line behind = (Line) pair.component2();
        boolean aheadNearest = Math.abs(ahead.getY0()) < Math.abs(behind.getY0());
        this.crossed = this.crossed || (this.approaching && !aheadNearest);
        if (!this.approaching && !aheadNearest) {
            z = false;
        }
        this.approaching = z;
        this.refAhead = ahead;
    }

    /* VF: from Vineflower (jadx: ) */
    public static float targetCenter$default(LaneTracks var0, float var1, Function1 var2, int var3, Object var4) {
        if ((var3 & 2) != 0) {
            var2 = new LaneTracks$$ExternalSyntheticLambda1();
        }

        return var0.targetCenter(var1, (Function1<? super LaneTracks.Line, float[]>)var2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final float[] targetCenter$lambda$11(Line it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return it.getY();
    }

    private static final float targetCenter$at(Function1<? super Line, float[]> function1, float $x, Line l) {
        float[] ys = function1.invoke(l);
        float[] xs = l.getX();
        if (xs.length < 2 || ys.length != xs.length) {
            return l.at($x);
        }
        int j = 1;
        while (j < xs.length - 1 && xs[j] < $x) {
            j++;
        }
        float f = RangesKt.coerceIn(($x - xs[j - 1]) / Math.max(0.01f, xs[j] - xs[j - 1]), 0.0f, 1.0f);
        return ys[j - 1] + ((ys[j] - ys[j - 1]) * f);
    }

    public final float targetCenter(float x, Function1<? super Line, float[]> shown) {
        Intrinsics.checkNotNullParameter(shown, "shown");
        Line l = this.refLeft;
        Line r = this.refRight;
        if (this.changeDir == 0 || l == null || r == null) {
            return Float.NaN;
        }
        if (this.crossed) {
            return (targetCenter$at(shown, x, l) + targetCenter$at(shown, x, r)) / 2.0f;
        }
        Line a = this.refAhead;
        if (a == null) {
            return Float.NaN;
        }
        return targetCenter$at(shown, x, a) + ((this.changeDir * this.laneWidth) / 2.0f);
    }

    public final Triple<Line, Line, Line> inner() {
        Object maxElem$iv;
        Object minElem$iv;
        Iterable $this$filter$iv = this.lines;
        Collection destination$iv$iv = new ArrayList();
        Iterator it = $this$filter$iv.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Object element$iv$iv = it.next();
            Line it2 = (Line) element$iv$iv;
            if (it2.getAlpha() > 0.05f && it2.getY0() < 0.0f) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        Iterable $this$maxByOrNull$iv = (List) destination$iv$iv;
        Iterator iterator$iv = $this$maxByOrNull$iv.iterator();
        Object minElem$iv2 = null;
        if (iterator$iv.hasNext()) {
            maxElem$iv = iterator$iv.next();
            if (iterator$iv.hasNext()) {
                Line it3 = (Line) maxElem$iv;
                float maxValue$iv = it3.getY0();
                do {
                    Object e$iv = iterator$iv.next();
                    Line it4 = (Line) e$iv;
                    float v$iv = it4.getY0();
                    if (Float.compare(maxValue$iv, v$iv) < 0) {
                        maxElem$iv = e$iv;
                        maxValue$iv = v$iv;
                    }
                } while (iterator$iv.hasNext());
            }
        } else {
            maxElem$iv = null;
        }
        Line left = (Line) maxElem$iv;
        Iterable $this$filter$iv2 = this.lines;
        Collection destination$iv$iv2 = new ArrayList();
        for (Object element$iv$iv2 : $this$filter$iv2) {
            Line it5 = (Line) element$iv$iv2;
            boolean it6 = !((it5.getAlpha() <= 0.05f || it5.getY0() < 0.0f));
            if (it6) {
                destination$iv$iv2.add(element$iv$iv2);
            }
        }
        Iterable $this$minByOrNull$iv = (List) destination$iv$iv2;
        Iterator iterator$iv2 = $this$minByOrNull$iv.iterator();
        if (iterator$iv2.hasNext()) {
            minElem$iv = iterator$iv2.next();
            if (iterator$iv2.hasNext()) {
                Line it7 = (Line) minElem$iv;
                float minValue$iv = it7.getY0();
                do {
                    Object e$iv2 = iterator$iv2.next();
                    Line it8 = (Line) e$iv2;
                    float v$iv2 = it8.getY0();
                    if (Float.compare(minValue$iv, v$iv2) > 0) {
                        minElem$iv = e$iv2;
                        minValue$iv = v$iv2;
                    }
                } while (iterator$iv2.hasNext());
            }
        } else {
            minElem$iv = null;
        }
        Line right = (Line) minElem$iv;
        Iterable $this$minByOrNull$iv2 = CollectionsKt.listOfNotNull((Object[]) new Line[]{left, right});
        Iterator iterator$iv3 = $this$minByOrNull$iv2.iterator();
        if (iterator$iv3.hasNext()) {
            minElem$iv2 = iterator$iv3.next();
            if (iterator$iv3.hasNext()) {
                Line it9 = (Line) minElem$iv2;
                float minValue$iv2 = Math.abs(it9.getY0());
                do {
                    Object e$iv3 = iterator$iv3.next();
                    Line it10 = (Line) e$iv3;
                    float v$iv3 = Math.abs(it10.getY0());
                    if (Float.compare(minValue$iv2, v$iv3) > 0) {
                        minElem$iv2 = e$iv3;
                        minValue$iv2 = v$iv3;
                    }
                } while (iterator$iv3.hasNext());
            }
        }
        Line near = (Line) minElem$iv2;
        return new Triple<>(left, right, near);
    }

    /* compiled from: LaneTracks.kt */
    public enum PathState {
        ENGAGED,
        OVERRIDE,
        OFF;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<PathState> getEntries() {
            return $ENTRIES;
        }
    }

    /* compiled from: LaneTracks.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final boolean roadThere(ModelSnapshot m, int i, float prob) {
            Intrinsics.checkNotNullParameter(m, "m");
            if (prob < 0.3f) {
                return false;
            }
            if (m.getEdgeY().length < 2 || m.getEdgeX().length < 2) {
                return true;
            }
            float le = roadThere$y5(m.getEdgeX()[0], m.getEdgeY()[0]);
            float re = roadThere$y5(m.getEdgeX()[1], m.getEdgeY()[1]);
            if ((Float.isInfinite(le) || Float.isNaN(le)) ? false : true) {
                if (((Float.isInfinite(re) || Float.isNaN(re)) ? false : true) && re - le >= 2.0f) {
                    float line = roadThere$y5(m.getLaneX()[i], m.getLaneY()[i]);
                    if (!((Float.isInfinite(line) || Float.isNaN(line)) ? false : true)) {
                        return true;
                    }
                    if (i == 0) {
                        if (line <= le) {
                            return false;
                        }
                    } else if (line >= re) {
                        return false;
                    }
                    return true;
                }
            }
            return true;
        }

        private static final float roadThere$y5(float[] xs, float[] ys) {
            if (xs.length < 2 || ys.length != xs.length) {
                return Float.NaN;
            }
            int j = 1;
            while (j < xs.length - 1 && xs[j] < 5.0f) {
                j++;
            }
            float f = RangesKt.coerceIn((5.0f - xs[j - 1]) / Math.max(0.01f, xs[j] - xs[j - 1]), 0.0f, 1.0f);
            return ys[j - 1] + ((ys[j] - ys[j - 1]) * f);
        }

        public final int blend(int c1, int c2, float t) {
            float u = RangesKt.coerceIn(t, 0.0f, 1.0f);
            return blend$ch(c1, c2, u, 24) | blend$ch(c1, c2, u, 16) | blend$ch(c1, c2, u, 8) | blend$ch(c1, c2, u, 0);
        }

        private static final int blend$ch(int $c1, int $c2, float u, int sh) {
            int a = ($c1 >>> sh) & 255;
            int bb = ($c2 >>> sh) & 255;
            return RangesKt.coerceIn((int) ((a + ((bb - a) * u)) + 0.5f), 0, 255) << sh;
        }

        public final PathState pathState(Telemetry t) {
            Intrinsics.checkNotNullParameter(t, "t");
            if (!t.getEnabled() && !t.getAolEnabled()) {
                return PathState.OFF;
            }
            if (t.getSteeringPressed() || Intrinsics.areEqual(t.getOpState(), "overriding") || (t.getEnabled() && !t.getActive())) {
                return PathState.OVERRIDE;
            }
            return PathState.ENGAGED;
        }

        public final int effortColor(int base, float effort) {
            KFunction b = (KFunction) new LaneTracks$Companion$effortColor$b$1(this);
            Integer amber = Integer.valueOf(-34786);
            if (effort < 0.65f) {
                return ((Number) ((Function3) b).invoke(Integer.valueOf(base), amber, Float.valueOf(RangesKt.coerceIn(((effort - 0.65f) + 0.1f) / 0.1f, 0.0f, 1.0f)))).intValue();
            }
            return ((Number) ((Function3) b).invoke(amber, Integer.valueOf(-1364701), Float.valueOf(RangesKt.coerceIn((effort - 0.65f) / 0.35000002f, 0.0f, 1.0f)))).intValue();
        }
    }
}
