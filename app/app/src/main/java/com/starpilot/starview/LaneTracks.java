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

    /* VF: from Vineflower (jadx: Code restructure failed;Removed duplicated region;incorrect) */
    public final void update(ModelSnapshot var1, Telemetry var2, float var3, boolean var4, LaneMarks.Mark[] var5) {
        Intrinsics.checkNotNullParameter(var1, "m");
        Intrinsics.checkNotNullParameter(var2, "t");
        if (var1.getFrameId() != this.lastFrame) {
            this.lastFrame = var1.getFrameId();
            float var7 = RangesKt.coerceIn(var3 / (this.smoothRc + var3), 0.0F, 1.0F);
            float var6;
            if (!var4) {
                var6 = 1.0F;
            } else if (var2.getVEgo() < 0.5F) {
                var6 = 0.0F;
            } else if (var2.getVEgo() < 3.0F) {
                var6 = 0.3F * var7;
            } else {
                var6 = var7;
            }

            Iterator var19 = this.lines.iterator();
            Intrinsics.checkNotNullExpressionValue(var19, "iterator(...)");

            while (var19.hasNext()) {
                LaneTracks.Line var20 = (LaneTracks.Line)var19.next();
                Intrinsics.checkNotNullExpressionValue(var20, "next(...)");
                var20 = var20;
                var20.setSeen(false);
                var20.setSlot(-1);
            }

            long var17 = System.currentTimeMillis();
            int var14 = 0;
            int var13 = Math.min(4, ((Object[])var1.getLaneX()).length);
            float var8 = var6;

            while (true) {
                Object var41 = null;
                Object var21 = null;
                if (var14 >= var13) {
                    Iterator var32 = this.lines.iterator();
                    Intrinsics.checkNotNullExpressionValue(var32, "iterator(...)");

                    while (var32.hasNext()) {
                        LaneTracks.Line var60;
                        label145: {
                            Object var59 = var32.next();
                            Intrinsics.checkNotNullExpressionValue(var59, "next(...)");
                            var60 = (LaneTracks.Line)var59;
                            if (var60.getSeen()) {
                                boolean var37;
                                if (var60.getOn() ? !(var60.getProb() > 0.04F) : !(var60.getProb() > 0.1F)) {
                                    var37 = false;
                                } else {
                                    var37 = true;
                                }

                                if (var37) {
                                    var4 = true;
                                    break label145;
                                }
                            }

                            var4 = false;
                        }

                        var60.setOn(var4);
                        if (var60.getOn()) {
                            var6 = RangesKt.coerceIn(var60.getProb() * 2.0F, 0.4F, 1.0F);
                        } else {
                            var6 = 0.0F;
                        }

                        if (var6 > var60.getAlpha()) {
                            var8 = var7;
                        } else {
                            var8 = var3 / (var3 + 1.0F);
                        }

                        var60.setAlpha(var60.getAlpha() + (var6 - var60.getAlpha()) * var8);
                    }

                    CollectionsKt.removeAll(this.lines, new LaneTracks$$ExternalSyntheticLambda0());
                    this.updateLaneChange(var1, var7);
                    Float var33 = var1.getModelDesiredCurvature();
                    boolean var38;
                    if (var33.floatValue() == 0.0F) {
                        var38 = true;
                    } else {
                        var38 = false;
                    }

                    Object var26 = var41;
                    if (!var38) {
                        var26 = var33;
                    }

                    if (var26 != null) {
                        var3 = var26;
                    } else {
                        var3 = var2.getDesiredCurvature();
                    }

                    var3 = Math.abs(var3) * var2.getVEgo() * var2.getVEgo() / 3.0F;
                    if (Companion.pathState(var2) == LaneTracks.PathState.ENGAGED) {
                        var3 = RangesKt.coerceIn(Math.max(Math.abs(var2.getOutTorque()), var3), 0.0F, 1.0F);
                    } else {
                        var3 = 0.0F;
                    }

                    this.effort = this.effort + (var3 - this.effort) * var7;
                    return;
                }

                float[] var23 = var1.getLaneX()[var14];
                if (var23.length >= 2 && var14 < ((Object[])var1.getLaneY()).length) {
                    var41 = var1.getLaneProb();
                    int var15;
                    if (var14 >= 0 && var14 < ((Object[])var41).length) {
                        var15 = 1;
                    } else {
                        var15 = 0;
                    }

                    float var9;
                    if ((var15 != 0)) {
                        var9 = (float)((Object[])var41)[var14];
                    } else {
                        var9 = 0.0F;
                    }

                    if (((Object[])var1.getLaneX()).length != 4 || var14 != 0 && var14 != 3 || Companion.roadThere(var1, var14, var9)) {
                        float[] var50 = var1.getLaneY()[var14];
                        var41 = (Object[])var1.getLaneZ();
                        if (var14 >= 0 && var14 < ((Object[])var41).length) {
                            var15 = (((boolean)true) ? 1 : 0);
                        } else {
                            var15 = (((boolean)false) ? 1 : 0);
                        }

                        if ((var15 != 0)) {
                            var41 = ((Object[])var41)[var14];
                        } else {
                            var41 = new float[var23.length];
                        }

                        LaneTracks.Line var41ss = new LaneTracks.Line(var23, var50, (float[])var41);
                        var6 = var41ss.getY0();
                        Iterable var51 = this.lines;
                        var var22 = new ArrayList();

                        for (Object var25 : var51) {
                            if (!((LaneTracks.Line)var25).getSeen()) {
                                var22.add(var25);
                            }
                        }

                        List var52 = var22;
                        Iterable var53 = var52;
                        var4 = false;
                        Iterator var64 = var53.iterator();
                        if (!var64.hasNext()) {
                            var50 = null;
                        } else {
                            var50 = (float[])var64.next();
                            if (var64.hasNext()) {
                                float var10 = Math.abs(((LaneTracks.Line)(Object)var50).getY0() - var6);

                                while (true) {
                                    Object var61 = var64.next();
                                    float var12 = Math.abs(((LaneTracks.Line)var61).getY0() - var6);
                                    float var11 = var10;
                                    if (Float.compare(var10, var12) > 0) {
                                        var50 = (float[])var61;
                                        var11 = var12;
                                    }

                                    if (!var64.hasNext()) {
                                        break;
                                    }

                                    var10 = var11;
                                }
                            }
                        }

                        LaneTracks.Line var55 = (LaneTracks.Line)(Object)var50;
                        if (var55 != null && Math.abs(var55.getY0() - var6) < 1.5F && var55.getY().length == var41ss.getY().length) {
                            var15 = 0;

                            for (int var16 = var55.getY().length; var15 < var16; var15++) {
                                float[] var62 = var55.getY();
                                var62[var15] += (var41ss.getY()[var15] - var55.getY()[var15]) * var8;
                                float[] var63 = var55.getZ();
                                var63[var15] += (var41ss.getZ()[var15] - var55.getZ()[var15]) * var8;
                            }

                            var55.setX(var23);
                            var41 = var55;
                        } else {
                            var50 = var41.getY();
                            var50 = Arrays.copyOf(var50, var50.length);
                            Intrinsics.checkNotNullExpressionValue(var50, "copyOf(...)");
                            float[] var46 = var41.getZ();
                            float[] var47 = Arrays.copyOf(var46, var46.length);
                            Intrinsics.checkNotNullExpressionValue(var47, "copyOf(...)");
                            LaneTracks.Line var41s = new LaneTracks.Line(var23, var50, var47);
                            this.lines.add(((Line) var41s));
                        }

                        var41.setSeen(true);
                        var41.setSlot(var14);
                        var41.setProb(var41.getProb() + (var9 - var41.getProb()) * var7);
                        LaneMarks.Mark var58 = (LaneMarks.Mark)var21;
                        if (var5 != null) {
                            var58 = ArraysKt.getOrNull(var5, var14);
                        }

                        if (var58 == null || var58.getKind() != 2 && var58.getKind() != 3) {
                            if (var58 != null && var58.getKind() == 1 && var17 - var41.getMarkMs() > 4000L) {
                                var41.setMark(var58);
                                var41.setMarkMs(var17);
                            }
                        } else {
                            var41.setMark(var58);
                            var41.setMarkMs(var17);
                        }
                    }
                }

                var14++;
            }
        }
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
            KFunction b = new LaneTracks$Companion$effortColor$b$1(this);
            return effort < 0.65f ? ((Function3) b).invoke((LaneTracks$Companion$effortColor$b$1) Integer.valueOf(base), (Integer) (-34786), (int) Float.valueOf(RangesKt.coerceIn(((effort - 0.65f) + 0.1f) / 0.1f, 0.0f, 1.0f))).intValue() : ((Function3) b).invoke((LaneTracks$Companion$effortColor$b$1) (-34786), -1364701, (int) Float.valueOf(RangesKt.coerceIn((effort - 0.65f) / 0.35000002f, 0.0f, 1.0f))).intValue();
        }
    }
}
