package com.starpilot.starview;

import com.starpilot.starview.BlinkerTurn;
import com.starpilot.starview.OsmRoads;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.json.JSONObject;

/* compiled from: BlinkerTurn.kt */
public final class BlinkerTurn {
    public static final double AHEAD_M = 76.0d;
    public static final double BEHIND_M = 8.0d;
    public static final double KEEP_MPS = 11.176d;
    public static final String KEY = "blinker_turn";
    public static final double PASSED_M = 25.0d;
    public static final double START_MPS = 8.9408d;
    private static volatile Boolean commaOk;
    private static long lastSentMs;
    private static volatile boolean mapped;
    private static String sent;
    public static final BlinkerTurn INSTANCE = new BlinkerTurn();
    private static final Set<String> SKIP = SetsKt.setOf((Object[]) new String[]{"service", "track", "path", "footway", "cycleway", "pedestrian", "steps", "bridleway", "construction", "proposed"});
    private static final Keeper keeper = new Keeper();

    private BlinkerTurn() {
    }

    /* compiled from: BlinkerTurn.kt */
    public static final class Corner {
        private final double ahead;
        private final double e;
        private final double n;
        private final String name;
        private final int side;

        public Corner(double e, double n, double ahead, int side, String name) {
            Intrinsics.checkNotNullParameter(name, "name");
            this.e = e;
            this.n = n;
            this.ahead = ahead;
            this.side = side;
            this.name = name;
        }

        public final double getAhead() {
            return this.ahead;
        }

        public final double getE() {
            return this.e;
        }

        public final double getN() {
            return this.n;
        }

        public final String getName() {
            return this.name;
        }

        public final int getSide() {
            return this.side;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0078  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x007b A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final List<Corner> corners(List<OsmRoads.Way> ways, OsmRoads.Way our, double e, double n, double yaw, int side) {
        List road;
        boolean z;
        List road2;
        int i;
        int i2;
        int i3;
        ArrayList out;
        ArrayList out2;
        boolean z2;
        Intrinsics.checkNotNullParameter(ways, "ways");
        Intrinsics.checkNotNullParameter(our, "our");
        double hx = Math.cos(yaw);
        double hy = Math.sin(yaw);
        List<OsmRoads.Way> $this$filter$iv = ways;
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            OsmRoads.Way it = (OsmRoads.Way) element$iv$iv;
            if (it.getId() != our.getId()) {
                if (!(our.getName().length() > 0) || !Intrinsics.areEqual(it.getKind(), our.getKind()) || !OsmRoads.INSTANCE.sameStreet(it.getName(), our.getName())) {
                    z2 = false;
                    if (!z2) {
                        destination$iv$iv.add(element$iv$iv);
                    }
                }
            }
            z2 = true;
            if (!z2) {
            }
        }
        ArrayList arrayList = (List) destination$iv$iv;
        if (arrayList.isEmpty()) {
            arrayList = CollectionsKt.listOf(our);
        }
        List road3 = arrayList;
        ArrayList out3 = new ArrayList();
        for (OsmRoads.Way w : ways) {
            if (w.getE().length < 2 || SKIP.contains(w.getKind())) {
                road = road3;
            } else {
                int i4 = 0;
                if (StringsKt.startsWith(w.getKind(), "motorway", false)) {
                    road = road3;
                } else {
                    List $this$any$iv = road3;
                    if (!($this$any$iv instanceof Collection) || !$this$any$iv.isEmpty()) {
                        Iterator it2 = $this$any$iv.iterator();
                        while (true) {
                            if (!it2.hasNext()) {
                                z = false;
                                break;
                            }
                            Object element$iv = it2.next();
                            OsmRoads.Way it3 = (OsmRoads.Way) element$iv;
                            if (it3.getId() == w.getId()) {
                                z = true;
                                break;
                            }
                        }
                    } else {
                        z = false;
                    }
                    if (z) {
                        continue;
                    } else {
                        int length = w.getE().length;
                        int i5 = 0;
                        while (i5 < length) {
                            double px = w.getE()[i5] - e;
                            double py = w.getN()[i5] - n;
                            double ahead = (px * hx) + (py * hy);
                            if (ahead < -8.0d || ahead > 76.0d) {
                                road2 = road3;
                                i = length;
                                i2 = i5;
                                i3 = i4;
                                out = out3;
                            } else if (Math.abs((px * hy) - (py * hx)) > 25.0d) {
                                road2 = road3;
                                i = length;
                                i2 = i5;
                                i3 = i4;
                                out = out3;
                            } else {
                                Iterator it4 = road3.iterator();
                                if (!it4.hasNext()) {
                                    throw new NoSuchElementException();
                                }
                                OsmRoads.Way it5 = (OsmRoads.Way) it4.next();
                                i = length;
                                i2 = i5;
                                road2 = road3;
                                i3 = i4;
                                double distTo = LaneRamps.INSTANCE.distTo(it5, w.getE()[i5], w.getN()[i5], hx, hy);
                                while (it4.hasNext()) {
                                    OsmRoads.Way it6 = (OsmRoads.Way) it4.next();
                                    distTo = Math.min(distTo, LaneRamps.INSTANCE.distTo(it6, w.getE()[i2], w.getN()[i2], hx, hy));
                                    out3 = out3;
                                }
                                ArrayList out4 = out3;
                                if (distTo <= 4.0d) {
                                    int i6 = -1;
                                    int i7 = 1;
                                    int[] iArr = {-1, 1};
                                    int i8 = i3;
                                    while (i8 < 2) {
                                        int dir = iArr[i8];
                                        if ((w.getOneway() != i7 || dir >= 0) && (w.getOneway() != i6 || dir <= 0)) {
                                            int j = i2 + dir;
                                            if (((j < 0 || j >= w.getE().length) ? i3 : i7) != 0) {
                                                while (true) {
                                                    int i9 = j + dir;
                                                    if (((i9 < 0 || i9 >= w.getE().length) ? i3 : i7) == 0 || Math.hypot(w.getE()[j] - w.getE()[i2], w.getN()[j] - w.getN()[i2]) >= 10.0d) {
                                                        break;
                                                    }
                                                    j += dir;
                                                }
                                                double vx = w.getE()[j] - w.getE()[i2];
                                                double vy = w.getN()[j] - w.getN()[i2];
                                                double l = Math.hypot(vx, vy);
                                                if (l >= 3.0d) {
                                                    double c = ((vx * hx) + (vy * hy)) / l;
                                                    if (Math.abs(c) <= 0.85d) {
                                                        int s = (vx * hy) - (vy * hx) > 0.0d ? i7 : i6;
                                                        if (s == side) {
                                                            out2 = out4;
                                                            out2.add(new Corner(w.getE()[i2], w.getN()[i2], ahead, s, w.getName()));
                                                        } else {
                                                            out2 = out4;
                                                        }
                                                    } else {
                                                        out2 = out4;
                                                    }
                                                } else {
                                                    out2 = out4;
                                                }
                                            } else {
                                                out2 = out4;
                                            }
                                        } else {
                                            out2 = out4;
                                        }
                                        i8++;
                                        out4 = out2;
                                        i7 = 1;
                                        i6 = -1;
                                    }
                                    out = out4;
                                } else {
                                    out = out4;
                                }
                            }
                            i5 = i2 + 1;
                            out3 = out;
                            i4 = i3;
                            length = i;
                            road3 = road2;
                        }
                        road3 = road3;
                    }
                }
            }
            road3 = road;
        }
        Iterable $this$sortedBy$iv = out3;
        return CollectionsKt.sortedWith($this$sortedBy$iv, new Comparator() { // from class: com.starpilot.starview.BlinkerTurn$corners$$inlined$sortedBy$1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.Comparator
            public final int compare(T t, T t2) {
                BlinkerTurn.Corner it7 = (BlinkerTurn.Corner) t;
                Double valueOf = Double.valueOf(Math.abs(it7.getAhead()));
                BlinkerTurn.Corner it8 = (BlinkerTurn.Corner) t2;
                return ComparisonsKt.compareValues(valueOf, Double.valueOf(Math.abs(it8.getAhead())));
            }
        });
    }

    /* compiled from: BlinkerTurn.kt */
    public static final class Keeper {
        private Corner corner;
        private double passedE;
        private double passedN;
        private int passedSide;
        private int side;
        private boolean turned;
        private double yaw0 = Double.NaN;

        public final int getSide() {
            return this.side;
        }

        public final Corner getCorner() {
            return this.corner;
        }

        public final void clear() {
            this.side = 0;
            this.corner = null;
            this.yaw0 = Double.NaN;
        }

        /* JADX WARN: Removed duplicated region for block: B:50:0x0144 A[LOOP:0: B:42:0x010d->B:50:0x0144, LOOP_END] */
        /* JADX WARN: Removed duplicated region for block: B:51:0x014a A[EDGE_INSN: B:51:0x014a->B:52:0x014a BREAK  A[LOOP:0: B:42:0x010d->B:50:0x0144], SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final int tick(boolean on, int blinker, double vEgo, boolean routed, Pair<Double, Double> pos, double yaw, Function4<? super Double, ? super Double, ? super Double, ? super Integer, ? extends List<Corner>> find) {
            Object element$iv;
            Iterable $this$firstOrNull$iv;
            boolean z;
            Intrinsics.checkNotNullParameter(find, "find");
            if (!on || blinker == 0 || routed || vEgo > 11.176d || (blinker != this.side && this.side != 0)) {
                clear();
                if (blinker == 0) {
                    this.passedSide = 0;
                    this.turned = false;
                }
            }
            if (!on || blinker == 0 || routed || this.turned) {
                return this.side;
            }
            Corner c = this.corner;
            if (this.side != 0 && c != null) {
                if (pos == null) {
                    return this.side;
                }
                double e = pos.component1().doubleValue();
                double n = pos.component2().doubleValue();
                double hx = Math.cos(this.yaw0);
                double hy = Math.sin(this.yaw0);
                double along = ((e - c.getE()) * hx) + ((n - c.getN()) * hy);
                if (Math.hypot(e - c.getE(), n - c.getN()) <= 25.0d || along <= -10.0d) {
                    return this.side;
                }
                this.turned = Double.isNaN(yaw) || Math.abs(Math.IEEEremainder(yaw - this.yaw0, 6.283185307179586d)) > Math.toRadians(30.0d);
                this.passedSide = this.side;
                this.passedE = c.getE();
                this.passedN = c.getN();
                clear();
                if (this.turned) {
                    return this.side;
                }
            }
            if (vEgo > 8.9408d || pos == null || Double.isNaN(yaw)) {
                return this.side;
            }
            Iterable $this$firstOrNull$iv2 = find.invoke(pos.getFirst(), pos.getSecond(), Double.valueOf(yaw), Integer.valueOf(blinker));
            Iterator it = $this$firstOrNull$iv2.iterator();
            while (true) {
                if (!it.hasNext()) {
                    element$iv = null;
                    break;
                }
                element$iv = it.next();
                Corner it2 = (Corner) element$iv;
                if (this.passedSide == blinker) {
                    $this$firstOrNull$iv = $this$firstOrNull$iv2;
                    if (Math.hypot(it2.getE() - this.passedE, it2.getN() - this.passedN) < 15.0d) {
                        z = false;
                        if (!z) {
                            break;
                        }
                        $this$firstOrNull$iv2 = $this$firstOrNull$iv;
                    }
                } else {
                    $this$firstOrNull$iv = $this$firstOrNull$iv2;
                }
                z = true;
                if (!z) {
                }
            }
            Corner found = (Corner) element$iv;
            if (found == null) {
                return this.side;
            }
            this.side = blinker;
            this.corner = found;
            this.yaw0 = yaw;
            return this.side;
        }
    }

    public final Keeper getKeeper() {
        return keeper;
    }

    public final Boolean getCommaOk() {
        return commaOk;
    }

    public final boolean getMapped() {
        return mapped;
    }

    public static /* synthetic */ void tick$default(BlinkerTurn blinkerTurn, Telemetry telemetry, UiSettings uiSettings, long j, int i, Object obj) {
        if ((i & 4) != 0) {
            j = System.currentTimeMillis();
        }
        blinkerTurn.tick(telemetry, uiSettings, j);
    }

    /* JADX WARN: Code restructure failed: missing block: B:39:0x0104, code lost:
    
        if ((r6.getDist() < 15.0d) == true) goto L55;
     */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0136 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0137  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x010d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void tick(Telemetry t, UiSettings s, long nowMs) {
        Pair pair;
        double yaw;
        boolean on;
        String say;
        DataClient dc;
        Intrinsics.checkNotNullParameter(t, "t");
        Intrinsics.checkNotNullParameter(s, "s");
        boolean z = true;
        int blinker = (!t.getLeftBlinker() || t.getRightBlinker()) ? (!t.getRightBlinker() || t.getLeftBlinker()) ? 0 : 1 : -1;
        boolean routed = t.navState() != null;
        OsmRoads.Fix f = OsmRoads.INSTANCE.getLastFix();
        boolean fresh = (f == null || Double.isNaN(OsmRoads.INSTANCE.getLat0()) || System.nanoTime() - f.getTNs() >= 3000000000L) ? false : true;
        if (fresh) {
            OsmRoads osmRoads = OsmRoads.INSTANCE;
            Intrinsics.checkNotNull(f);
            pair = osmRoads.toLocal(f.getLat(), f.getLon());
        } else {
            pair = null;
        }
        Pair pos = pair;
        if (fresh) {
            Intrinsics.checkNotNull(f);
            if (f.getSpeed() > 2.0f) {
                float bearingDeg = f.getBearingDeg();
                if ((Float.isInfinite(bearingDeg) || Float.isNaN(bearingDeg)) ? false : true) {
                    yaw = 1.5707963267948966d - Math.toRadians(f.getBearingDeg());
                    on = s.get("blinker_turn");
                    int side = keeper.tick(on, blinker, t.getVEgo(), routed, pos, yaw, new Function4() { // from class: com.starpilot.starview.BlinkerTurn$$ExternalSyntheticLambda0
                        @Override // kotlin.jvm.functions.Function4
                        public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
                            List tick$lambda$6;
                            tick$lambda$6 = BlinkerTurn.tick$lambda$6(((Double) obj).doubleValue(), ((Double) obj2).doubleValue(), ((Double) obj3).doubleValue(), ((Integer) obj4).intValue());
                            return tick$lambda$6;
                        }
                    });
                    if (pos == null && !Double.isNaN(yaw)) {
                        OsmRoads.Match it = OsmRoads.INSTANCE.matchSeg(pos.getFirst().doubleValue(), pos.getSecond().doubleValue(), yaw);
                        if (it != null) {
                        }
                        z = false;
                        mapped = z;
                    } else if (pos == null) {
                        mapped = false;
                    }
                    if (on || blinker == 0 || routed) {
                        say = null;
                    } else if (side != 0) {
                        say = side < 0 ? "left" : "right";
                    } else {
                        say = mapped ? "none" : null;
                    }
                    dc = Link.INSTANCE.getData();
                    if (dc != null) {
                        return;
                    }
                    if (!dc.getConnected()) {
                        sent = null;
                        return;
                    }
                    if (!Intrinsics.areEqual(say, sent) || (say != null && nowMs - lastSentMs >= 500)) {
                        sent = say;
                        lastSentMs = nowMs;
                        dc.request("turn_intent", MapsKt.mapOf(TuplesKt.to("side", say)), new Function1() { // from class: com.starpilot.starview.BlinkerTurn$$ExternalSyntheticLambda1
                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj) {
                                Unit tick$lambda$8;
                                tick$lambda$8 = BlinkerTurn.tick$lambda$8((JSONObject) obj);
                                return tick$lambda$8;
                            }
                        });
                        return;
                    }
                    return;
                }
            }
        }
        yaw = Double.NaN;
        on = s.get("blinker_turn");
        int side2 = keeper.tick(on, blinker, t.getVEgo(), routed, pos, yaw, new Function4() { // from class: com.starpilot.starview.BlinkerTurn$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function4
            public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
                List tick$lambda$6;
                tick$lambda$6 = BlinkerTurn.tick$lambda$6(((Double) obj).doubleValue(), ((Double) obj2).doubleValue(), ((Double) obj3).doubleValue(), ((Integer) obj4).intValue());
                return tick$lambda$6;
            }
        });
        if (pos == null) {
        }
        if (pos == null) {
        }
        if (on) {
        }
        say = null;
        dc = Link.INSTANCE.getData();
        if (dc != null) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final List tick$lambda$6(double e, double n, double y, int sd) {
        OsmRoads.Way our;
        OsmRoads.Match it = OsmRoads.INSTANCE.matchSeg(e, n, y);
        if (it != null) {
            if (!(it.getDist() < 15.0d)) {
                it = null;
            }
            if (it != null && (our = it.getWay()) != null) {
                try {
                    return INSTANCE.corners(OsmRoads.INSTANCE.near(e, n, 150.0d), our, e, n, y, sd);
                } catch (Exception e2) {
                    return CollectionsKt.emptyList();
                }
            }
        }
        return CollectionsKt.emptyList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tick$lambda$8(JSONObject ack) {
        Intrinsics.checkNotNullParameter(ack, "ack");
        commaOk = Boolean.valueOf(ack.optBoolean("ok"));
        return Unit.INSTANCE;
    }

    public final String label() {
        switch (keeper.getSide()) {
            case -1:
                return "turn LEFT kept";
            case 0:
            default:
                return null;
            case 1:
                return "turn RIGHT kept";
        }
    }
}
