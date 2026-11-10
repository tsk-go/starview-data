package com.starpilot.starview;

import com.starpilot.starview.Detector;
import com.starpilot.starview.IdeaSpots;
import com.starpilot.starview.Ideas;
import com.starpilot.starview.OsmRoads;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.ArrayDeque;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import org.json.JSONObject;

/* compiled from: IdeasMore.kt */
public final class IdeasMore {
    public static final Companion Companion = new Companion(null);
    private volatile long busAtMs;
    private volatile float busX;
    private long dparkMs;
    private final ArrayDeque<float[]> flow = new ArrayDeque<>();
    private volatile long hornAtMs;
    private int lastMile;
    private long lastMs;
    private long parkSeenMs;
    private long streakChipUntil;
    private float streakMem;
    private boolean wasEnabled;
    private volatile long wipersAtMs;

    public final long getWipersAtMs() {
        return this.wipersAtMs;
    }

    public final void setWipersAtMs(long j) {
        this.wipersAtMs = j;
    }

    public final long getBusAtMs() {
        return this.busAtMs;
    }

    public final float getBusX() {
        return this.busX;
    }

    public final void setBusAtMs(long j) {
        this.busAtMs = j;
    }

    public final void setBusX(float f) {
        this.busX = f;
    }

    public final long getHornAtMs() {
        return this.hornAtMs;
    }

    public final void setHornAtMs(long j) {
        this.hornAtMs = j;
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x005b, code lost:
    
        if (r0 == null) goto L18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0080, code lost:
    
        if ((r0 != null && r0.getHighway()) != false) goto L31;
     */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0127  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0139  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x01f9  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x022c  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x014b  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x012d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void tick(Telemetry t, UiSettings s, Ideas.Here h, Detector det, long nowMs, List<Ideas.Part> out) {
        List emptyList;
        boolean z;
        double v;
        double v2;
        double v3;
        List<Ideas.Part> list;
        UiSettings uiSettings;
        Ideas.Here here;
        Telemetry telemetry;
        Telemetry telemetry2;
        Ideas.Here here2;
        List<Ideas.Part> list2;
        UiSettings uiSettings2;
        double v4;
        double v5;
        String it;
        Intrinsics.checkNotNullParameter(t, "t");
        Intrinsics.checkNotNullParameter(s, "s");
        Intrinsics.checkNotNullParameter(out, "out");
        double dt = this.lastMs == 0 ? 0.0d : RangesKt.coerceIn((nowMs - this.lastMs) / 1000.0d, 0.0d, 0.5d);
        this.lastMs = nowMs;
        if (det != null && (emptyList = det.getObjects()) != null) {
            List it2 = nowMs - det.getObjectsAtMs() < 800 ? 1 : null;
            if (it2 == null) {
                emptyList = null;
            }
        }
        emptyList = CollectionsKt.emptyList();
        List objs = emptyList;
        double v6 = t.getVEgo();
        if (v6 <= 15.0d) {
            if (h != null) {
                OsmRoads.Way street = Ideas.INSTANCE.street(h);
            }
            z = false;
            boolean highway = z;
            WantIdeas.INSTANCE.setSchoolBus(Ideas.INSTANCE.on(s, "school_bus"));
            WantIdeas.INSTANCE.setWipers(Ideas.INSTANCE.on(s, "rain_camera"));
            if (Ideas.INSTANCE.on(s, "school_bus") || nowMs - this.busAtMs >= 2500) {
                v = v6;
            } else {
                v = v6;
                out.add(new Ideas.Part("school_bus", Double.valueOf(Math.max(4.4704d, Ideas.INSTANCE.approach(4.4704d, this.busX - 15.0d, 1.0d))), 0.0d, 0.0d, "School bus - red lights flashing: STOP", 99, 12, null));
            }
            v2 = v;
            weather(t, s, nowMs, out);
            if (Ideas.INSTANCE.on(s, "follow_flow") && highway) {
                followFlow(t, v2, nowMs, out);
            }
            if (Ideas.INSTANCE.on(s, "far_lead")) {
                v3 = v2;
            } else {
                v3 = v2;
                farLead(t, v3, out);
            }
            if (Ideas.INSTANCE.on(s, "streak")) {
                list = out;
                uiSettings = s;
                here = h;
                telemetry = t;
            } else {
                list = out;
                uiSettings = s;
                here = h;
                telemetry = t;
                streak(t, s, dt, nowMs, out);
            }
            if (Ideas.INSTANCE.on(uiSettings, "parking") || here == null) {
                telemetry2 = telemetry;
                here2 = here;
                list2 = list;
                uiSettings2 = uiSettings;
                v4 = v3;
            } else {
                telemetry2 = telemetry;
                here2 = here;
                list2 = list;
                uiSettings2 = uiSettings;
                v4 = v3;
                parking(h, objs, nowMs, out);
            }
            if (Ideas.INSTANCE.on(uiSettings2, "speed_cams") && here2 != null) {
                MapCams.INSTANCE.want(h.getLat(), h.getLon());
                speedCams(telemetry2, here2, list2);
            }
            if (Ideas.INSTANCE.on(uiSettings2, "city_potholes") && here2 != null) {
                City311.INSTANCE.want(h.getLat(), h.getLon());
            }
            Ideas.INSTANCE.getMemory().getLights().setActuated(Ideas.INSTANCE.on(uiSettings2, "actuated_lights"));
            if (Ideas.INSTANCE.on(uiSettings2, "sun_glare") && here2 != null) {
                glare(here2, list2);
            }
            if (Ideas.INSTANCE.on(uiSettings2, "double_parked") || here2 == null || highway) {
                v5 = v4;
            } else {
                v5 = v4;
                doubleParked(h, objs, v4, nowMs, out);
            }
            if (Ideas.INSTANCE.on(uiSettings2, "horn")) {
                SirenEar.INSTANCE.setWantHorn(false);
            } else {
                SirenEar.INSTANCE.setWantHorn(true);
                SirenEar.INSTANCE.want(true);
                if (nowMs - this.hornAtMs < 3000) {
                    list2.add(new Ideas.Part("horn", null, 0.3d, 0.0d, "Someone honked - check around you", 80, 10, null));
                }
            }
            if (!Ideas.INSTANCE.on(uiSettings2, "left_gap") && (it = leftGap(telemetry2, objs, v5)) != null) {
                list2.add(new Ideas.Part("left_gap", null, 0.0d, 0.0d, it, 5, 14, null));
                return;
            }
        }
        z = true;
        boolean highway2 = z;
        WantIdeas.INSTANCE.setSchoolBus(Ideas.INSTANCE.on(s, "school_bus"));
        WantIdeas.INSTANCE.setWipers(Ideas.INSTANCE.on(s, "rain_camera"));
        if (Ideas.INSTANCE.on(s, "school_bus")) {
        }
        v = v6;
        v2 = v;
        weather(t, s, nowMs, out);
        if (Ideas.INSTANCE.on(s, "follow_flow")) {
            followFlow(t, v2, nowMs, out);
        }
        if (Ideas.INSTANCE.on(s, "far_lead")) {
        }
        if (Ideas.INSTANCE.on(s, "streak")) {
        }
        if (Ideas.INSTANCE.on(uiSettings, "parking")) {
        }
        telemetry2 = telemetry;
        here2 = here;
        list2 = list;
        uiSettings2 = uiSettings;
        v4 = v3;
        if (Ideas.INSTANCE.on(uiSettings2, "speed_cams")) {
            MapCams.INSTANCE.want(h.getLat(), h.getLon());
            speedCams(telemetry2, here2, list2);
        }
        if (Ideas.INSTANCE.on(uiSettings2, "city_potholes")) {
            City311.INSTANCE.want(h.getLat(), h.getLon());
        }
        Ideas.INSTANCE.getMemory().getLights().setActuated(Ideas.INSTANCE.on(uiSettings2, "actuated_lights"));
        if (Ideas.INSTANCE.on(uiSettings2, "sun_glare")) {
            glare(here2, list2);
        }
        if (Ideas.INSTANCE.on(uiSettings2, "double_parked")) {
        }
        v5 = v4;
        if (Ideas.INSTANCE.on(uiSettings2, "horn")) {
        }
        if (!Ideas.INSTANCE.on(uiSettings2, "left_gap")) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0037  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x004b  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x006d  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x003c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final void weather(Telemetry t, UiSettings s, long nowMs, List<Ideas.Part> out) {
        boolean wipers;
        int id;
        String str;
        if (Ideas.INSTANCE.on(s, "rain_camera") && nowMs - this.wipersAtMs < 120000) {
            wipers = true;
            if (!Ideas.INSTANCE.on(s, "weather") || wipers) {
                id = !Ideas.INSTANCE.on(s, "weather") ? t.getWeatherId() : 0;
                if (600 > id && id < 700) {
                    if (!(700 <= id && id < 800)) {
                        if ((200 <= id && id < 600) || wipers) {
                            if (wipers) {
                                if (!(200 <= id && id < 600)) {
                                    str = "Wipers on - more room";
                                    out.add(new Ideas.Part("weather", null, 0.3d, 0.0d, str, 20, 10, null));
                                    return;
                                }
                            }
                            str = "Rain - more room";
                            out.add(new Ideas.Part("weather", null, 0.3d, 0.0d, str, 20, 10, null));
                            return;
                        }
                        return;
                    }
                    out.add(new Ideas.Part("weather", null, 0.4d, 0.0d, "Fog - more room", 21, 10, null));
                    return;
                }
                out.add(new Ideas.Part("weather", Double.valueOf(17.8816d), 0.5d, 0.0d, "Snow - slower, more room", 22, 8, null));
                return;
            }
            return;
        }
        wipers = false;
        if (Ideas.INSTANCE.on(s, "weather")) {
        }
        if (!Ideas.INSTANCE.on(s, "weather")) {
        }
        if (600 > id && id < 700) {
        }
    }

    private final void followFlow(Telemetry t, double v, long nowMs, List<Ideas.Part> out) {
        ModelSnapshot m = t.getModel();
        ArrayList speeds = new ArrayList();
        RadarTrack[] radar = t.getRadar();
        int length = radar.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            RadarTrack r = radar[i];
            if (r.getMeasured()) {
                float dRel = r.getDRel();
                if (-10.0f <= dRel && dRel <= 100.0f) {
                    float f = 0.0f;
                    if (m != null && m.getPosX().length > 2 && r.getDRel() > 0.0f) {
                        f = IdeasRoad.Companion.interp(r.getDRel(), m.getPosX(), m.getPosY());
                    }
                    float pathY = f;
                    float abs = Math.abs(r.getYRel() + pathY);
                    if (2.0f <= abs && abs <= 7.5f) {
                        speeds.add(Float.valueOf((float) (v + r.getVRel())));
                    }
                }
            }
            i++;
        }
        if (speeds.size() >= 3) {
            this.flow.addLast(new float[]{nowMs % 10000000, ((Number) CollectionsKt.sorted(speeds).get(speeds.size() / 2)).floatValue()});
        }
        while (!this.flow.isEmpty() && ((nowMs % 10000000) - this.flow.first()[0] > 5000.0f || nowMs % 10000000 < this.flow.first()[0])) {
            this.flow.removeFirst();
        }
        if (this.flow.size() < 10) {
            return;
        }
        Iterable $this$map$iv = this.flow;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        for (Object item$iv$iv : $this$map$iv) {
            float[] it = (float[]) item$iv$iv;
            destination$iv$iv.add(Float.valueOf(it[1]));
        }
        double f2 = ((Number) CollectionsKt.sorted((List) destination$iv$iv).get(this.flow.size() / 2)).floatValue();
        if (f2 <= 10.0d || f2 <= 0.7d * v || v <= 2.2d + f2) {
            return;
        }
        out.add(new Ideas.Part("follow_flow", Double.valueOf(1.3d + f2), 0.0d, 0.0d, "Following the flow: " + Math.round(Ideas.INSTANCE.mph(f2)) + " mph", 16, 12, null));
    }

    private final void farLead(Telemetry t, double v, List<Ideas.Part> out) {
        if (v < 12.0d) {
            return;
        }
        ModelSnapshot m = t.getModel();
        RadarTrack best = null;
        for (RadarTrack r : t.getRadar()) {
            if (r.getMeasured()) {
                float dRel = r.getDRel();
                if (50.0f <= dRel && dRel <= 170.0f) {
                    float pathY = (m == null || m.getPosX().length <= 2) ? 0.0f : IdeasRoad.Companion.interp(r.getDRel(), m.getPosX(), m.getPosY());
                    if (Math.abs(r.getYRel() + pathY) <= 1.3f && r.getVRel() <= -6.0f && ((!t.getLeadOne().getStatus() || t.getLeadOne().getDRel() >= r.getDRel() - 10.0f) && (best == null || r.getDRel() < best.getDRel()))) {
                        best = r;
                    }
                }
            }
        }
        if (best == null) {
            return;
        }
        RadarTrack b = best;
        double vLead = Math.max(0.0d, v + b.getVRel());
        double cap = Ideas.INSTANCE.approach(vLead + 2.0d, (b.getDRel() - 40.0d) - (2.0d * vLead), 0.5d);
        if (cap < v - 0.5d) {
            out.add(new Ideas.Part("far_lead", Double.valueOf(Math.max(4.4704d, cap)), 0.0d, 0.0d, "Slow traffic " + Ideas.INSTANCE.ft(b.getDRel()) + " ft ahead - easing off", 65, 12, null));
        }
    }

    private final void streak(Telemetry t, UiSettings s, double dt, long nowMs, List<Ideas.Part> out) {
        boolean en = t.getEnabled();
        if (this.wasEnabled && !en && t.getVEgo() > 2.5f) {
            float cur = s.getFloat("streak_m", 0.0f);
            if (cur > s.getFloat("streak_best_m", 0.0f)) {
                s.setFloat("streak_best_m", cur);
            }
            s.setFloat("streak_m", 0.0f);
            this.lastMile = 0;
        }
        this.wasEnabled = en;
        if (en && dt > 0.0d) {
            float m = s.getFloat("streak_m", 0.0f) + ((float) (t.getVEgo() * dt));
            if ((nowMs / 1000) % 5 == 0) {
                s.setFloat("streak_m", m);
            } else {
                this.streakMem = m;
            }
            int miles = (int) (Math.max(m, this.streakMem) / 1609.34f);
            if (miles >= 1 && miles != this.lastMile && miles % 5 == 0) {
                this.lastMile = miles;
                this.streakChipUntil = nowMs + 6000;
            }
        }
        if (nowMs < this.streakChipUntil) {
            out.add(new Ideas.Part("streak", null, 0.0d, 0.0d, this.lastMile + " miles without a takeover", 1, 14, null));
        }
    }

    public final String streakText(UiSettings s) {
        Intrinsics.checkNotNullParameter(s, "s");
        String format = String.format("Now: %.1f mi - best: %.1f mi", Arrays.copyOf(new Object[]{Float.valueOf(s.getFloat("streak_m", 0.0f) / 1609.34f), Float.valueOf(Math.max(s.getFloat("streak_best_m", 0.0f), s.getFloat("streak_m", 0.0f)) / 1609.34f)}, 2));
        Intrinsics.checkNotNullExpressionValue(format, "format(...)");
        return format;
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x00a6 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0048 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final void parking(Ideas.Here h, List<Detector.Obj> objs, long nowMs, List<Ideas.Part> out) {
        Object element$iv;
        int i;
        List parked;
        int i2;
        Detector.Obj it;
        if (Double.isNaN(h.getYaw())) {
            return;
        }
        if (nowMs - this.parkSeenMs > 1000) {
            double v = h.getV();
            if (1.0d <= v && v <= 12.0d) {
                this.parkSeenMs = nowMs;
                List<Detector.Obj> $this$filter$iv = objs;
                Collection destination$iv$iv = new ArrayList();
                for (Object element$iv$iv : $this$filter$iv) {
                    Detector.Obj it2 = (Detector.Obj) element$iv$iv;
                    if (IdeasSense.Companion.getVEHICLES().contains(Integer.valueOf(it2.getCls())) && !it2.getMoving()) {
                        float cy = it2.getCy();
                        if (1.8f <= cy && cy <= 4.5f) {
                            float cx = it2.getCx();
                            if (3.0f <= cx && cx <= 35.0f) {
                                it = 1;
                                if (it == null) {
                                    destination$iv$iv.add(element$iv$iv);
                                }
                            }
                        }
                    }
                    it = null;
                    if (it == null) {
                    }
                }
                Iterable $this$sortedBy$iv = (List) destination$iv$iv;
                List parked2 = CollectionsKt.sortedWith($this$sortedBy$iv, new Comparator() { // from class: com.starpilot.starview.IdeasMore$parking$$inlined$sortedBy$1
                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // java.util.Comparator
                    public final int compare(T t, T t2) {
                        Detector.Obj it3 = (Detector.Obj) t;
                        Detector.Obj it4 = (Detector.Obj) t2;
                        return ComparisonsKt.compareValues(Float.valueOf(it3.getCx()), Float.valueOf(it4.getCx()));
                    }
                });
                int size = parked2.size();
                int i3 = 1;
                while (i3 < size) {
                    float gap = (((Detector.Obj) parked2.get(i3)).getCx() - 2.35f) - (((Detector.Obj) parked2.get(i3 - 1)).getCx() + 2.35f);
                    if (6.5f <= gap && gap <= 14.0f) {
                        float ax = (((Detector.Obj) parked2.get(i3 - 1)).getCx() + ((Detector.Obj) parked2.get(i3)).getCx()) / 2.0f;
                        float ay = (((Detector.Obj) parked2.get(i3 - 1)).getCy() + ((Detector.Obj) parked2.get(i3)).getCy()) / 2.0f;
                        double e = (ax * Math.cos(h.getYaw())) + (ay * Math.sin(h.getYaw()));
                        double n = (ax * Math.sin(h.getYaw())) - (ay * Math.cos(h.getYaw()));
                        i = i3;
                        parked = parked2;
                        i2 = size;
                        IdeaSpots.INSTANCE.record("parking", h.getLat() + (n / 111195.0d), h.getLon() + (e / (Math.cos(Math.toRadians(h.getLat())) * 111320.0d)), h.getHdgDeg(), (r35 & 16) != 0 ? Double.NaN : 0.0d, (r35 & 32) != 0 ? "" : null, (r35 & 64) != 0 ? System.currentTimeMillis() : nowMs, (r35 & 128) != 0 ? 10.0d : 6.0d, (r35 & 256) != 0 ? "me" : null);
                    } else {
                        i = i3;
                        parked = parked2;
                        i2 = size;
                    }
                    i3 = i + 1;
                    parked2 = parked;
                    size = i2;
                }
                IdeaSpots.INSTANCE.expire("parking", 1200000L, nowMs);
            }
        }
        JSONObject d = Nav.INSTANCE.getDestination();
        if (d == null) {
            return;
        }
        double dl = d.optDouble("latitude", d.optDouble("lat", Double.NaN));
        double dn = d.optDouble("longitude", d.optDouble("lon", Double.NaN));
        if (!Double.isNaN(dl) && !Double.isNaN(dn)) {
            Pair<Double, Double> local = IdeaSpots.INSTANCE.local(h.getLat(), h.getLon(), dl, dn);
            double dx = local.component1().doubleValue();
            double dy = local.component2().doubleValue();
            if ((dx * dx) + (dy * dy) > 250000.0d) {
                return;
            }
            Iterable $this$firstOrNull$iv = IdeaSpots.around$default(IdeaSpots.INSTANCE, h.getLat(), h.getLon(), h.getYaw(), SetsKt.setOf("parking"), 300.0d, 0.0d, 12.0d, 0, false, 416, null);
            Iterator it3 = $this$firstOrNull$iv.iterator();
            while (true) {
                if (!it3.hasNext()) {
                    element$iv = null;
                    break;
                }
                element$iv = it3.next();
                IdeaSpots.Seen it4 = (IdeaSpots.Seen) element$iv;
                IdeaSpots.Seen it5 = it4.getAhead() > 10.0d ? 1 : null;
                if (it5 != null) {
                    break;
                }
            }
            IdeaSpots.Seen spot = (IdeaSpots.Seen) element$iv;
            if (spot == null) {
                return;
            }
            out.add(new Ideas.Part("parking", null, 0.0d, 0.0d, "Open spot ~" + Ideas.INSTANCE.ft(spot.getAhead()) + " ft ahead on the right (seen " + ((nowMs - spot.getSpot().getLastMs()) / 60000) + " min ago)", 8, 14, null));
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0086  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x00aa  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final void speedCams(Telemetry t, Ideas.Here h, List<Ideas.Part> out) {
        IdeaSpots.Seen cam;
        Double d;
        if (Double.isNaN(h.getYaw()) || (cam = (IdeaSpots.Seen) CollectionsKt.firstOrNull(IdeaSpots.around$default(IdeaSpots.INSTANCE, h.getLat(), h.getLon(), h.getYaw(), SetsKt.setOf("speed_cam"), 250.0d, 10.0d, 20.0d, 0, false, 128, null))) == null) {
            return;
        }
        double limit = t.getSlcSpeedLimit();
        Double cap = limit > 4.0d ? Double.valueOf(0.89408d + limit) : null;
        if (cap != null) {
            double it = cap.doubleValue();
            if (it < h.getV() + 3.0d) {
                d = cap;
                out.add(new Ideas.Part("speed_cams", d, 0.0d, 0.0d, "Speed camera " + Ideas.INSTANCE.ft(Math.max(0.0d, cam.getAhead())) + " ft" + (limit <= 4.0d ? " - " + Math.round(Ideas.INSTANCE.mph(limit)) + " mph" : ""), 62, 12, null));
            }
        }
        d = null;
        out.add(new Ideas.Part("speed_cams", d, 0.0d, 0.0d, "Speed camera " + Ideas.INSTANCE.ft(Math.max(0.0d, cam.getAhead())) + " ft" + (limit <= 4.0d ? " - " + Math.round(Ideas.INSTANCE.mph(limit)) + " mph" : ""), 62, 12, null));
    }

    private final void glare(Ideas.Here h, List<Ideas.Part> out) {
        if (Double.isNaN(h.getYaw())) {
            return;
        }
        Pair<Double, Double> sun = Companion.sun(h.getLat(), h.getLon(), h.getTMs());
        double el = sun.component1().doubleValue();
        double az = sun.component2().doubleValue();
        boolean z = false;
        if (0.0d <= el && el <= 22.0d) {
            z = true;
        }
        if (z && Math.abs(Math.IEEEremainder(az - h.getHdgDeg(), 360.0d)) <= 22.0d) {
            out.add(new Ideas.Part("sun_glare", null, 0.3d, 0.0d, "Sun glare - more room", 26, 10, null));
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x00a0 A[EDGE_INSN: B:31:0x00a0->B:32:0x00a0 BREAK  A[LOOP:0: B:14:0x004d->B:70:?], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:54:0x01cb A[EDGE_INSN: B:54:0x01cb->B:55:0x01cb BREAK  A[LOOP:1: B:39:0x0182->B:60:?], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:60:? A[LOOP:1: B:39:0x0182->B:60:?, LOOP_END, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:70:? A[LOOP:0: B:14:0x004d->B:70:?, LOOP_END, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final void doubleParked(Ideas.Here h, List<Detector.Obj> objs, double v, long nowMs, List<Ideas.Part> out) {
        int hour;
        Object element$iv;
        IdeaSpots.Seen sp;
        Object element$iv2;
        boolean z;
        if (Double.isNaN(h.getYaw())) {
            return;
        }
        Calendar $this$doubleParked_u24lambda_u247 = Calendar.getInstance();
        $this$doubleParked_u24lambda_u247.setTimeInMillis(nowMs);
        int dow = $this$doubleParked_u24lambda_u247.get(7);
        boolean weekend = dow == 7 || dow == 1;
        int hour2 = $this$doubleParked_u24lambda_u247.get(11);
        if (v <= 2.0d || nowMs - this.dparkMs <= 5000) {
            hour = hour2;
        } else {
            List<Detector.Obj> $this$firstOrNull$iv = objs;
            Iterator it = $this$firstOrNull$iv.iterator();
            while (true) {
                if (it.hasNext()) {
                    element$iv2 = it.next();
                    Detector.Obj it2 = (Detector.Obj) element$iv2;
                    if (IdeasSense.Companion.getVEHICLES().contains(Integer.valueOf(it2.getCls())) && !it2.getMoving() && Math.abs(it2.getCy()) < 1.2f) {
                        float cx = it2.getCx();
                        if (10.0f <= cx && cx <= 50.0f) {
                            z = true;
                            if (!z) {
                                break;
                            }
                        }
                    }
                    z = false;
                    if (!z) {
                    }
                } else {
                    element$iv2 = null;
                    break;
                }
            }
            Detector.Obj blocker = (Detector.Obj) element$iv2;
            if (blocker == null) {
                hour = hour2;
            } else {
                this.dparkMs = nowMs;
                double e = (blocker.getCx() * Math.cos(h.getYaw())) + (blocker.getCy() * Math.sin(h.getYaw()));
                hour = hour2;
                IdeaSpots.INSTANCE.record("dpark", h.getLat() + (((blocker.getCx() * Math.sin(h.getYaw())) - (blocker.getCy() * Math.cos(h.getYaw()))) / 111195.0d), h.getLon() + (e / (Math.cos(Math.toRadians(h.getLat())) * 111320.0d)), h.getHdgDeg(), (r35 & 16) != 0 ? Double.NaN : 0.0d, (r35 & 32) != 0 ? "" : (weekend ? "we" : "wd") + hour2, (r35 & 64) != 0 ? System.currentTimeMillis() : nowMs, (r35 & 128) != 0 ? 10.0d : 25.0d, (r35 & 256) != 0 ? "me" : null);
            }
        }
        Iterable $this$firstOrNull$iv2 = IdeaSpots.around$default(IdeaSpots.INSTANCE, h.getLat(), h.getLon(), h.getYaw(), SetsKt.setOf("dpark"), 200.0d, 0.0d, 6.0d, 2, false, 288, null);
        Iterator it3 = $this$firstOrNull$iv2.iterator();
        while (true) {
            if (it3.hasNext()) {
                element$iv = it3.next();
                IdeaSpots.Seen sp2 = (IdeaSpots.Seen) element$iv;
                String n = sp2.getSpot().getNote();
                if (StringsKt.startsWith$default(n, weekend ? "we" : "wd", false, 2, (Object) null)) {
                    Integer intOrNull = StringsKt.toIntOrNull(StringsKt.drop(n, 2));
                    if (Math.abs((intOrNull != null ? intOrNull.intValue() : -9) - hour) <= 1) {
                        sp = 1;
                        if (sp == null) {
                            break;
                        }
                    }
                }
                sp = null;
                if (sp == null) {
                }
            } else {
                element$iv = null;
                break;
            }
        }
        if (((IdeaSpots.Seen) element$iv) == null) {
            return;
        }
        out.add(new Ideas.Part("double_parked", Double.valueOf(Math.max(4.4704d, v - 1.3d)), 0.0d, 0.0d, "Double-parked cars usually here now", 24, 12, null));
    }

    /* JADX WARN: Code restructure failed: missing block: B:27:0x0081, code lost:
    
        if ((5.0f <= r11 && r11 <= 150.0f) != false) goto L34;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final String leftGap(Telemetry t, List<Detector.Obj> objs, double v) {
        Float f = null;
        if (!t.getLeftBlinker() || t.getRightBlinker() || v > 3.0d) {
            return null;
        }
        List<Detector.Obj> $this$filter$iv = objs;
        Collection destination$iv$iv = new ArrayList();
        Iterator it = $this$filter$iv.iterator();
        while (true) {
            boolean z = true;
            if (!it.hasNext()) {
                break;
            }
            Object element$iv$iv = it.next();
            Detector.Obj it2 = (Detector.Obj) element$iv$iv;
            if (IdeasSense.Companion.getVEHICLES().contains(Integer.valueOf(it2.getCls())) && it2.getVelValid() && it2.getVx() < -2.0f) {
                float cy = it2.getCy();
                if (-15.0f <= cy && cy <= -2.0f) {
                    float x = it2.getX();
                }
            }
            z = false;
            if (z) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        Iterator it3 = ((List) destination$iv$iv).iterator();
        if (it3.hasNext()) {
            Detector.Obj it4 = (Detector.Obj) it3.next();
            float x2 = it4.getX() / (-it4.getVx());
            while (it3.hasNext()) {
                Detector.Obj it5 = (Detector.Obj) it3.next();
                x2 = Math.min(x2, it5.getX() / (-it5.getVx()));
            }
            f = Float.valueOf(x2);
        }
        if (f == null) {
            return "Oncoming lanes look clear";
        }
        float next = f.floatValue();
        String format = String.format("%.0f", Arrays.copyOf(new Object[]{Float.valueOf(next)}, 1));
        Intrinsics.checkNotNullExpressionValue(format, "format(...)");
        return "Next oncoming car in " + format + " s";
    }

    /* compiled from: IdeasMore.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final Pair<Double, Double> sun(double lat, double lon, long tMs) {
            double d = ((tMs / 8.64E7d) + 2440587.5d) - 2451545.0d;
            double g = Math.toRadians((0.98560028d * d) + 357.529d);
            double q = (0.98564736d * d) + 280.459d;
            double l = Math.toRadians((Math.sin(g) * 1.915d) + q + (Math.sin(2 * g) * 0.02d));
            double e = Math.toRadians(23.439d - (3.6E-7d * d));
            double ra = Math.atan2(Math.cos(e) * Math.sin(l), Math.cos(l));
            double dec = Math.asin(Math.sin(e) * Math.sin(l));
            double gmst = ((24.06570982441908d * d) + 18.697374558d) % 24.0d;
            double ha = Math.toRadians((15.0d * gmst) + lon) - ra;
            double la = Math.toRadians(lat);
            double el = Math.asin((Math.sin(la) * Math.sin(dec)) + (Math.cos(la) * Math.cos(dec) * Math.cos(ha)));
            double az = Math.atan2(-Math.sin(ha), (Math.tan(dec) * Math.cos(la)) - (Math.sin(la) * Math.cos(ha)));
            return TuplesKt.to(Double.valueOf(Math.toDegrees(el)), Double.valueOf((Math.toDegrees(az) + 360.0d) % 360.0d));
        }
    }
}
