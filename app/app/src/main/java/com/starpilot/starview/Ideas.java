package com.starpilot.starview;

import android.content.Context;
import com.starpilot.starview.NewIdeas;
import com.starpilot.starview.OsmRoads;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.json.JSONObject;

/* compiled from: Ideas.kt */
public final class Ideas {
    private static Context app;
    private static volatile String chip;
    private static volatile Boolean commaOk;
    private static long lastSendMs;
    private static TabletMotion motion;
    private static boolean sentSomething;
    private static long streetMs;
    private static OsmRoads.Way streetWay;
    private static long tick;
    public static final Ideas INSTANCE = new Ideas();
    private static volatile List<Part> parts = CollectionsKt.emptyList();
    private static double lastYaw = Double.NaN;
    private static final IdeasRoad road = new IdeasRoad();
    private static final IdeasSense sense = new IdeasSense();
    private static final IdeasMemory memory = new IdeasMemory();
    private static final IdeasMore more = new IdeasMore();

    private Ideas() {
    }

    /* compiled from: Ideas.kt */
    public static final class Here {
        private final double lat;
        private final double lon;
        private final String rtk;
        private final long tMs;
        private final double v;
        private final double yaw;

        public Here(double lat, double lon, double yaw, double v, String rtk, long tMs) {
            Intrinsics.checkNotNullParameter(rtk, "rtk");
            this.lat = lat;
            this.lon = lon;
            this.yaw = yaw;
            this.v = v;
            this.rtk = rtk;
            this.tMs = tMs;
        }

        public final double getLat() {
            return this.lat;
        }

        public final double getLon() {
            return this.lon;
        }

        public final String getRtk() {
            return this.rtk;
        }

        public final long getTMs() {
            return this.tMs;
        }

        public final double getV() {
            return this.v;
        }

        public final double getYaw() {
            return this.yaw;
        }

        public final double getHdgDeg() {
            if (Double.isNaN(this.yaw)) {
                return Double.NaN;
            }
            return ((90.0d - Math.toDegrees(this.yaw)) + 360.0d) % 360.0d;
        }
    }

    /* compiled from: Ideas.kt */
    public static final class Part {
        private final Double cap;
        private final String chip;
        private final double follow;
        private final String id;
        private final double offset;
        private final int prio;

        public Part(String id, Double cap, double follow, double offset, String chip, int prio) {
            Intrinsics.checkNotNullParameter(id, "id");
            Intrinsics.checkNotNullParameter(chip, "chip");
            this.id = id;
            this.cap = cap;
            this.follow = follow;
            this.offset = offset;
            this.chip = chip;
            this.prio = prio;
        }

        public /* synthetic */ Part(String str, Double d, double d2, double d3, String str2, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, (i2 & 2) != 0 ? null : d, (i2 & 4) != 0 ? 0.0d : d2, (i2 & 8) == 0 ? d3 : 0.0d, (i2 & 16) != 0 ? "" : str2, (i2 & 32) != 0 ? 0 : i);
        }

        public final Double getCap() {
            return this.cap;
        }

        public final String getChip() {
            return this.chip;
        }

        public final double getFollow() {
            return this.follow;
        }

        public final String getId() {
            return this.id;
        }

        public final double getOffset() {
            return this.offset;
        }

        public final int getPrio() {
            return this.prio;
        }
    }

    public final String getChip() {
        return chip;
    }

    public final List<Part> getParts() {
        return parts;
    }

    public final Boolean getCommaOk() {
        return commaOk;
    }

    public final IdeasRoad road() {
        return road;
    }

    public final IdeasSense getSense() {
        return sense;
    }

    public final IdeasMemory getMemory() {
        return memory;
    }

    public final IdeasMore getMore() {
        return more;
    }

    public final void init(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        app = ctx.getApplicationContext();
        IdeaSpots ideaSpots = IdeaSpots.INSTANCE;
        Context context = app;
        Context context2 = null;
        if (context == null) {
            Intrinsics.throwUninitializedPropertyAccessException("app");
            context = null;
        }
        ideaSpots.init(context);
        IdeasMemory ideasMemory = memory;
        Context context3 = app;
        if (context3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("app");
        } else {
            context2 = context3;
        }
        ideasMemory.init(context2);
    }

    public final boolean on(UiSettings s, String id) {
        Intrinsics.checkNotNullParameter(s, "s");
        Intrinsics.checkNotNullParameter(id, "id");
        return NewIdeas.INSTANCE.on(s, id);
    }

    public final Here here(Telemetry t, long nowMs) {
        double lat;
        double lon;
        String kind;
        double hdg;
        Intrinsics.checkNotNullParameter(t, "t");
        double v = Math.max(0.0d, t.getVEgo());
        double[] rtk = RtkPosition.INSTANCE.now(nowMs, v);
        if (rtk != null) {
            double lat2 = rtk[0];
            double lon2 = rtk[1];
            String kind2 = RtkPosition.INSTANCE.getKind();
            double hdg2 = RtkPosition.INSTANCE.getHeading();
            lat = lat2;
            lon = lon2;
            kind = kind2;
            hdg = hdg2;
        } else {
            OsmRoads.Fix f = OsmRoads.INSTANCE.getLastFix();
            if (f == null || System.nanoTime() - f.getTNs() > 3000000000L) {
                return null;
            }
            double lat3 = f.getLat();
            double lon3 = f.getLon();
            if (f.getSpeed() > 2.0f) {
                float bearingDeg = f.getBearingDeg();
                if ((Float.isInfinite(bearingDeg) || Float.isNaN(bearingDeg)) ? false : true) {
                    double hdg3 = f.getBearingDeg();
                    lat = lat3;
                    lon = lon3;
                    kind = "none";
                    hdg = hdg3;
                }
            }
            lat = lat3;
            lon = lon3;
            kind = "none";
            hdg = Double.NaN;
        }
        if (((Double.isInfinite(hdg) || Double.isNaN(hdg)) ? false : true) && v > 2.0d) {
            lastYaw = 1.5707963267948966d - Math.toRadians(hdg);
        }
        return new Here(lat, lon, lastYaw, v, kind, nowMs);
    }

    public static /* synthetic */ void tick$default(Ideas ideas, Telemetry telemetry, UiSettings uiSettings, Detector detector, long j, int i, Object obj) {
        if ((i & 8) != 0) {
            j = System.currentTimeMillis();
        }
        ideas.tick(telemetry, uiSettings, detector, j);
    }

    public final void tick(Telemetry t, UiSettings s, Detector det, long nowMs) {
        boolean $this$any$ivB = false;
        Iterable $this$any$iv;
        Object maxElem$iv;
        Intrinsics.checkNotNullParameter(t, "t");
        Intrinsics.checkNotNullParameter(s, "s");
        if (app == null) {
            return;
        }
        tick++;
        Iterable $this$any$iv2 = NewIdeas.INSTANCE.getALL();
        if (!($this$any$iv2 instanceof Collection) || !((Collection) $this$any$iv2).isEmpty()) {
            Iterator it = $this$any$iv2.iterator();
            while (true) {
                if (!it.hasNext()) {
                    $this$any$ivB = false;
                    break;
                }
                Object element$iv = it.next();
                if (s.get(NewIdeas.INSTANCE.key(((NewIdeas.Idea) element$iv).getId()))) {
                    $this$any$ivB = true;
                    break;
                }
            }
        } else {
            $this$any$ivB = false;
        }
        if (!$this$any$ivB) {
            if (sentSomething) {
                send(CollectionsKt.emptyList(), nowMs);
            }
            TabletMotion tabletMotion = motion;
            if (tabletMotion != null) {
                tabletMotion.stop();
            }
            chip = null;
            parts = CollectionsKt.emptyList();
            return;
        }
        if (on(s, "speed_bumps") || on(s, "potholes")) {
            TabletMotion it2 = motion;
            if (it2 == null) {
                Context context = app;
                if (context == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("app");
                    context = null;
                }
                it2 = new TabletMotion(context);
                motion = it2;
            }
            it2.setSpeed(t.getVEgo());
            it2.setAEgo(t.getAEgo());
            it2.setYawRate(t.getYawRate());
            it2.start();
        } else {
            TabletMotion tabletMotion2 = motion;
            if (tabletMotion2 != null) {
                tabletMotion2.stop();
            }
        }
        Here here = here(t, nowMs);
        ArrayList out = new ArrayList();
        road.tick(t, s, here, motion, nowMs, out);
        sense.tick(t, s, here, det, nowMs, out);
        memory.tick(t, s, here, det, nowMs, out);
        more.tick(t, s, here, det, nowMs, out);
        parts = out;
        ArrayList $this$filter$iv = out;
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            if (((Part) element$iv$iv).getChip().length() > 0) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        Iterable $this$maxByOrNull$iv = (List) destination$iv$iv;
        Iterator iterator$iv = $this$maxByOrNull$iv.iterator();
        if (iterator$iv.hasNext()) {
            maxElem$iv = iterator$iv.next();
            if (iterator$iv.hasNext()) {
                int maxValue$iv = ((Part) maxElem$iv).getPrio();
                do {
                    Object e$iv = iterator$iv.next();
                    int v$iv = ((Part) e$iv).getPrio();
                    if (maxValue$iv < v$iv) {
                        maxElem$iv = e$iv;
                        maxValue$iv = v$iv;
                    }
                } while (iterator$iv.hasNext());
            }
        } else {
            maxElem$iv = null;
        }
        Part part = (Part) maxElem$iv;
        chip = part != null ? part.getChip() : null;
        if (nowMs - lastSendMs >= 500 || (out.isEmpty() && sentSomething)) {
            send(out, nowMs);
        }
        if (tick % 200 == 0) {
            IdeaSpots.saveSoon$default(IdeaSpots.INSTANCE, nowMs, false, 2, null);
        }
    }

    private final void send(List<Part> out, long nowMs) {
        Object maxElem$iv;
        DataClient dc = Link.INSTANCE.getData();
        if (dc == null) {
            return;
        }
        if (!dc.getConnected()) {
            sentSomething = false;
            return;
        }
        List<Part> $this$filter$iv = out;
        Collection destination$iv$iv = new ArrayList();
        Iterator it = $this$filter$iv.iterator();
        while (true) {
            boolean z = true;
            if (!it.hasNext()) {
                break;
            }
            Object element$iv$iv = it.next();
            Part it2 = (Part) element$iv$iv;
            if (it2.getCap() == null && it2.getFollow() <= 0.0d) {
                if (it2.getOffset() == 0.0d) {
                    z = false;
                }
            }
            if (z) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        List act = (List) destination$iv$iv;
        if (!act.isEmpty() || sentSomething) {
            lastSendMs = nowMs;
            HashMap args = new HashMap();
            if (!act.isEmpty()) {
                List $this$mapNotNull$iv = act;
                Collection destination$iv$iv2 = new ArrayList();
                for (Object element$iv$iv$iv : $this$mapNotNull$iv) {
                    Part it3 = (Part) element$iv$iv$iv;
                    Double cap = it3.getCap();
                    if (cap != null) {
                        destination$iv$iv2.add(cap);
                    }
                }
                Double minOrNull = CollectionsKt.minOrNull(destination$iv$iv2);
                if (minOrNull != null) {
                    double it4 = minOrNull.doubleValue();
                    args.put("cap", Double.valueOf(it4));
                }
                Iterator it5 = act.iterator();
                if (!it5.hasNext()) {
                    throw new NoSuchElementException();
                }
                Part it6 = (Part) it5.next();
                double follow = it6.getFollow();
                while (it5.hasNext()) {
                    Part it7 = (Part) it5.next();
                    follow = Math.max(follow, it7.getFollow());
                }
                Double valueOf = Double.valueOf(follow);
                double it8 = valueOf.doubleValue();
                Object obj = null;
                if (!(it8 > 0.0d)) {
                    valueOf = null;
                }
                if (valueOf != null) {
                    double it9 = valueOf.doubleValue();
                    args.put("follow", Double.valueOf(it9));
                }
                List $this$maxByOrNull$iv = act;
                Iterator iterator$iv = $this$maxByOrNull$iv.iterator();
                if (iterator$iv.hasNext()) {
                    maxElem$iv = iterator$iv.next();
                    if (iterator$iv.hasNext()) {
                        Part it10 = (Part) maxElem$iv;
                        Comparable maxValue$iv = Double.valueOf(Math.abs(it10.getOffset()));
                        do {
                            Object e$iv = iterator$iv.next();
                            Part it11 = (Part) e$iv;
                            Double valueOf2 = Double.valueOf(Math.abs(it11.getOffset()));
                            if (maxValue$iv.compareTo(valueOf2) < 0) {
                                maxElem$iv = e$iv;
                                maxValue$iv = valueOf2;
                            }
                        } while (iterator$iv.hasNext());
                    }
                } else {
                    maxElem$iv = null;
                }
                Part part = (Part) maxElem$iv;
                if (part != null) {
                    Double valueOf3 = Double.valueOf(part.getOffset());
                    double it12 = valueOf3.doubleValue();
                    if (it12 == 0.0d) {
                        valueOf3 = null;
                    }
                    if (valueOf3 != null) {
                        double it13 = valueOf3.doubleValue();
                        args.put("offset", Double.valueOf(it13));
                    }
                }
                HashMap hashMap = args;
                List $this$firstOrNull$iv = act;
                Iterator it14 = $this$firstOrNull$iv.iterator();
                while (true) {
                    if (!it14.hasNext()) {
                        break;
                    }
                    Object element$iv = it14.next();
                    Part it15 = (Part) element$iv;
                    boolean it16 = it15.getCap() != null;
                    if (it16) {
                        obj = element$iv;
                        break;
                    }
                }
                Part part2 = (Part) obj;
                if (part2 == null) {
                    part2 = (Part) CollectionsKt.first(act);
                }
                hashMap.put("why", part2.getId());
            }
            sentSomething = !act.isEmpty();
            dc.request("advice", args, new Function1() { // from class: com.starpilot.starview.Ideas$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj2) {
                    Unit send$lambda$14;
                    send$lambda$14 = Ideas.send$lambda$14((JSONObject) obj2);
                    return send$lambda$14;
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit send$lambda$14(JSONObject ack) {
        Intrinsics.checkNotNullParameter(ack, "ack");
        commaOk = Boolean.valueOf(ack.optBoolean("ok"));
        return Unit.INSTANCE;
    }

    public static /* synthetic */ double approach$default(Ideas ideas, double d, double d2, double d3, int i, Object obj) {
        if ((i & 4) != 0) {
            d3 = 0.8d;
        }
        return ideas.approach(d, d2, d3);
    }

    public final double approach(double target, double d, double a) {
        return Math.sqrt((target * target) + (2 * a * Math.max(0.0d, d)));
    }

    public final double mph(double v) {
        return v / 0.44704d;
    }

    public final long ft(double m) {
        return Math.round((3.28084d * m) / 10.0d) * 10;
    }

    public final OsmRoads.Way street(Here h) {
        Intrinsics.checkNotNullParameter(h, "h");
        if (h.getTMs() - streetMs < 1000) {
            return streetWay;
        }
        streetMs = h.getTMs();
        OsmRoads.Way way = null;
        if (!Double.isNaN(h.getYaw()) && !Double.isNaN(OsmRoads.INSTANCE.getLat0())) {
            try {
                Pair<Double, Double> local = OsmRoads.INSTANCE.toLocal(h.getLat(), h.getLon());
                double e = local.component1().doubleValue();
                double n = local.component2().doubleValue();
                OsmRoads.Match it = OsmRoads.INSTANCE.matchSeg(e, n, h.getYaw());
                if (it != null) {
                    if (!(it.getDist() < 15.0d)) {
                        it = null;
                    }
                    if (it != null) {
                        way = it.getWay();
                    }
                }
            } catch (Exception e2) {
            }
        }
        streetWay = way;
        return streetWay;
    }

    public final double lateralOnStreet(Here h) {
        Intrinsics.checkNotNullParameter(h, "h");
        if (!Intrinsics.areEqual(h.getRtk(), "fixed") || Double.isNaN(h.getYaw()) || Double.isNaN(OsmRoads.INSTANCE.getLat0())) {
            return Double.NaN;
        }
        Pair<Double, Double> local = OsmRoads.INSTANCE.toLocal(h.getLat(), h.getLon());
        double e = local.component1().doubleValue();
        double n = local.component2().doubleValue();
        OsmRoads.Match m = OsmRoads.INSTANCE.matchSeg(e, n, h.getYaw());
        if (m != null) {
            if (!(m.getDist() < 15.0d)) {
                m = null;
            }
            if (m != null) {
                return m.rightOf(h.getYaw());
            }
        }
        return Double.NaN;
    }

    public final void endOfDrive() {
        IdeaSpots.saveSoon$default(IdeaSpots.INSTANCE, 0L, true, 1, null);
        memory.endOfDrive();
    }
}
