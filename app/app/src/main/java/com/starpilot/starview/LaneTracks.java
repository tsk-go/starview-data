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

    /* JADX WARN: Code restructure failed: missing block: B:133:0x02f7, code lost:
    
        r6 = true;
     */
    /* JADX WARN: Removed duplicated region for block: B:124:0x0308  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x0322  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x0324  */
    /* JADX WARN: Removed duplicated region for block: B:132:0x0319  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0257  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void update(ModelSnapshot m, Telemetry t, float dt, boolean smooth, LaneMarks.Mark[] marks) {
        boolean z;
        float f;
        int i;
        Object next;
        Line line;
        Line line2;
        LaneMarks.Mark mark;
        Intrinsics.checkNotNullParameter(m, "m");
        Intrinsics.checkNotNullParameter(t, "t");
        if (m.getFrameId() == this.lastFrame) {
            return;
        }
        this.lastFrame = m.getFrameId();
        float coerceIn = RangesKt.coerceIn(dt / (this.smoothRc + dt), 0.0f, 1.0f);
        float f2 = !smooth ? 1.0f : t.getVEgo() < 0.5f ? 0.0f : t.getVEgo() < 3.0f ? 0.3f * coerceIn : coerceIn;
        Iterator<Line> it = this.lines.iterator();
        Intrinsics.checkNotNullExpressionValue(it, "iterator(...)");
        while (it.hasNext()) {
            Line next2 = it.next();
            Intrinsics.checkNotNullExpressionValue(next2, "next(...)");
            Line line3 = next2;
            line3.setSeen(false);
            line3.setSlot(-1);
        }
        long currentTimeMillis = System.currentTimeMillis();
        int i2 = 0;
        int i3 = 4;
        int min = Math.min(4, m.getLaneX().length);
        while (true) {
            if (i2 >= min) {
                break;
            }
            float[] fArr = m.getLaneX()[i2];
            if (fArr.length < 2) {
                f = f2;
                i = min;
            } else if (i2 >= m.getLaneY().length) {
                f = f2;
                i = min;
            } else {
                float[] laneProb = m.getLaneProb();
                float f3 = i2 >= 0 && i2 < laneProb.length ? laneProb[i2] : 0.0f;
                i = min;
                if (m.getLaneX().length != i3 || (!(i2 == 0 || i2 == 3) || Companion.roadThere(m, i2, f3))) {
                    float[] fArr2 = m.getLaneY()[i2];
                    float[][] laneZ = m.getLaneZ();
                    Line line4 = new Line(fArr, fArr2, i2 >= 0 && i2 < laneZ.length ? laneZ[i2] : new float[fArr.length]);
                    float y0 = line4.getY0();
                    ArrayList<Line> arrayList = this.lines;
                    ArrayList arrayList2 = new ArrayList();
                    for (Object obj : arrayList) {
                        ArrayList<Line> arrayList3 = arrayList;
                        if (!((Line) obj).getSeen()) {
                            arrayList2.add(obj);
                        }
                        arrayList = arrayList3;
                    }
                    int i4 = 0;
                    Iterator it2 = arrayList2.iterator();
                    if (it2.hasNext()) {
                        next = it2.next();
                        if (it2.hasNext()) {
                            float abs = Math.abs(((Line) next).getY0() - y0);
                            while (true) {
                                Object next3 = it2.next();
                                float abs2 = Math.abs(((Line) next3).getY0() - y0);
                                int i5 = i4;
                                if (Float.compare(abs, abs2) > 0) {
                                    next = next3;
                                    abs = abs2;
                                }
                                if (!it2.hasNext()) {
                                    break;
                                }
                                y0 = y0;
                                i4 = i5;
                                line4 = line4;
                            }
                        }
                    } else {
                        next = null;
                    }
                    Line line5 = (Line) next;
                    if (line5 != null && Math.abs(line5.getY0() - y0) < 1.5f) {
                        if (line5.getY().length == line4.getY().length) {
                            int length = line5.getY().length;
                            for (int i6 = 0; i6 < length; i6++) {
                                float[] y = line5.getY();
                                y[i6] = y[i6] + ((line4.getY()[i6] - line5.getY()[i6]) * f2);
                                float[] z2 = line5.getZ();
                                z2[i6] = z2[i6] + ((line4.getZ()[i6] - line5.getZ()[i6]) * f2);
                            }
                            line5.setX(fArr);
                            line = line5;
                            f = f2;
                            line2 = line;
                            line2.setSeen(true);
                            line2.setSlot(i2);
                            line2.setProb(line2.getProb() + ((f3 - line2.getProb()) * coerceIn));
                            mark = marks != null ? (LaneMarks.Mark) ArraysKt.getOrNull(marks, i2) : null;
                            if (mark == null && (mark.getKind() == 2 || mark.getKind() == 3)) {
                                line2.setMark(mark);
                                line2.setMarkMs(currentTimeMillis);
                            } else if (mark != null && mark.getKind() == 1 && currentTimeMillis - line2.getMarkMs() > 4000) {
                                line2.setMark(mark);
                                line2.setMarkMs(currentTimeMillis);
                            }
                        }
                    }
                    float[] y2 = line4.getY();
                    float[] copyOf = Arrays.copyOf(y2, y2.length);
                    Intrinsics.checkNotNullExpressionValue(copyOf, "copyOf(...)");
                    f = f2;
                    float[] z3 = line4.getZ();
                    float[] copyOf2 = Arrays.copyOf(z3, z3.length);
                    Intrinsics.checkNotNullExpressionValue(copyOf2, "copyOf(...)");
                    line = new Line(fArr, copyOf, copyOf2);
                    this.lines.add(line);
                    line2 = line;
                    line2.setSeen(true);
                    line2.setSlot(i2);
                    line2.setProb(line2.getProb() + ((f3 - line2.getProb()) * coerceIn));
                    mark = marks != null ? (LaneMarks.Mark) ArraysKt.getOrNull(marks, i2) : null;
                    if (mark == null) {
                    }
                    if (mark != null) {
                        line2.setMark(mark);
                        line2.setMarkMs(currentTimeMillis);
                    }
                } else {
                    f = f2;
                }
            }
            i2++;
            min = i;
            f2 = f;
            i3 = 4;
        }
        Iterator<Line> it3 = this.lines.iterator();
        Intrinsics.checkNotNullExpressionValue(it3, "iterator(...)");
        while (it3.hasNext()) {
            Line next4 = it3.next();
            Intrinsics.checkNotNullExpressionValue(next4, "next(...)");
            Line line6 = next4;
            if (line6.getSeen()) {
                boolean z4 = line6.getOn() ? false : false;
                if (z4) {
                    z = true;
                    line6.setOn(z);
                    float coerceIn2 = !line6.getOn() ? RangesKt.coerceIn(line6.getProb() * 2.0f, 0.4f, 1.0f) : 0.0f;
                    line6.setAlpha(line6.getAlpha() + ((coerceIn2 - line6.getAlpha()) * (coerceIn2 <= line6.getAlpha() ? coerceIn : dt / (dt + 1.0f))));
                }
            }
            z = false;
            line6.setOn(z);
            if (!line6.getOn()) {
            }
            line6.setAlpha(line6.getAlpha() + ((coerceIn2 - line6.getAlpha()) * (coerceIn2 <= line6.getAlpha() ? coerceIn : dt / (dt + 1.0f))));
        }
        CollectionsKt.removeAll((List) this.lines, new Function1() { // from class: com.starpilot.starview.LaneTracks$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj2) {
                boolean update$lambda$5;
                update$lambda$5 = LaneTracks.update$lambda$5((LaneTracks.Line) obj2);
                return Boolean.valueOf(update$lambda$5);
            }
        });
        updateLaneChange(m, coerceIn);
        Float valueOf = Float.valueOf(m.getModelDesiredCurvature());
        Float f4 = (valueOf.floatValue() > 0.0f ? 1 : (valueOf.floatValue() == 0.0f ? 0 : -1)) == 0 ? null : valueOf;
        this.effort += ((Companion.pathState(t) == PathState.ENGAGED ? RangesKt.coerceIn(Math.max(Math.abs(t.getOutTorque()), ((Math.abs(f4 != null ? f4.floatValue() : t.getDesiredCurvature()) * t.getVEgo()) * t.getVEgo()) / 3.0f), 0.0f, 1.0f) : 0.0f) - this.effort) * coerceIn;
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
            Line it2 = (!it.getSeen() || it.getY0() >= 0.0f) ? null : 1;
            if (it2 != null) {
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
            Line it6 = (!it5.getSeen() || it5.getY0() < 0.0f) ? null : 1;
            if (it6 != null) {
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

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ float targetCenter$default(LaneTracks laneTracks, float f, Function1 function1, int i, Object obj) {
        if ((i & 2) != 0) {
            function1 = new Function1() { // from class: com.starpilot.starview.LaneTracks$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj2) {
                    float[] targetCenter$lambda$11;
                    targetCenter$lambda$11 = LaneTracks.targetCenter$lambda$11((LaneTracks.Line) obj2);
                    return targetCenter$lambda$11;
                }
            };
        }
        return laneTracks.targetCenter(f, function1);
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
            Line it6 = (it5.getAlpha() <= 0.05f || it5.getY0() < 0.0f) ? null : 1;
            if (it6 != null) {
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

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries($VALUES);

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
            KFunction b = new LaneTracks$Companion$effortColor$b$1(this);
            return effort < 0.65f ? ((Function3) b).invoke((LaneTracks$Companion$effortColor$b$1) Integer.valueOf(base), (Integer) (-34786), (int) Float.valueOf(RangesKt.coerceIn(((effort - 0.65f) + 0.1f) / 0.1f, 0.0f, 1.0f))).intValue() : ((Function3) b).invoke((LaneTracks$Companion$effortColor$b$1) (-34786), -1364701, (int) Float.valueOf(RangesKt.coerceIn((effort - 0.65f) / 0.35000002f, 0.0f, 1.0f))).intValue();
        }
    }
}
