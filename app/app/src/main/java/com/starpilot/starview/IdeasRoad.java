package com.starpilot.starview;

import android.content.Context;
import com.starpilot.starview.IdeaSpots;
import com.starpilot.starview.Ideas;
import com.starpilot.starview.OsmRoads;
import com.starpilot.starview.TabletMotion;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import kotlin.collections.ArrayDeque;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: Ideas.kt */
public final class IdeasRoad {
    public static final double BUMP_MPS = 5.36448d;
    public static final Companion Companion = new Companion(null);
    public static final float UNSURE_STD = 0.6f;
    private Ideas.Here askHere;
    private volatile long askWhy;
    private long bumpSlowUntil;
    private long cutinUntil;
    private long leadMs;
    private long sureSince;
    private long unsureSince;
    private boolean wasEnabled;
    private double unsureFrom = Double.NaN;
    private final HashMap<Long, ArrayDeque<float[]>> trackHist = new HashMap<>();
    private double cutinFrom = Double.NaN;
    private String cutinText = "";
    private float leadD = Float.NaN;

    public final long getAskWhy() {
        return this.askWhy;
    }

    public final void tick(Telemetry t, UiSettings s, Ideas.Here h, TabletMotion motion, long nowMs, List<Ideas.Part> out) {
        Intrinsics.checkNotNullParameter(t, "t");
        Intrinsics.checkNotNullParameter(s, "s");
        Intrinsics.checkNotNullParameter(out, "out");
        if (Ideas.INSTANCE.on(s, "speed_bumps") && h != null) {
            MapBumps.INSTANCE.want(h.getLat(), h.getLon());
            bumps(t, h, nowMs, out);
        }
        if (motion != null) {
            learnJolts(s, h, motion, nowMs);
        }
        if (Ideas.INSTANCE.on(s, "potholes") && h != null) {
            potholes(t, h, out);
        }
        if (Ideas.INSTANCE.on(s, "nyc_cutins")) {
            cutins(t, s, h, nowMs, out);
        }
        if (Ideas.INSTANCE.on(s, "unsure_slow")) {
            unsure(t, nowMs, out);
        }
        if (Ideas.INSTANCE.on(s, "spot_settings") && h != null) {
            spots(t, h, out);
        }
        takeovers(t, s, h, nowMs);
    }

    private final void bumps(Telemetry t, Ideas.Here h, long nowMs, List<Ideas.Part> out) {
        if (Double.isNaN(h.getYaw())) {
            return;
        }
        OsmRoads.Way street = Ideas.INSTANCE.street(h);
        if (street != null && street.getHighway()) {
            return;
        }
        Iterable $this$filter$iv = IdeaSpots.around$default(IdeaSpots.INSTANCE, h.getLat(), h.getLon(), h.getYaw(), SetsKt.setOf((Object[]) new String[]{"bump", "rough", "map_bump"}), 140.0d, 6.0d, 7.0d, 0, false, 384, null);
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            IdeaSpots.Seen it = (IdeaSpots.Seen) element$iv$iv;
            if (Intrinsics.areEqual(it.getSpot().getKind(), "map_bump") || it.getSpot().getCount() >= 2) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        List seen = (List) destination$iv$iv;
        IdeaSpots.Seen next = (IdeaSpots.Seen) CollectionsKt.firstOrNull(seen);
        if (next == null) {
            IdeasRoad $this$bumps_u24lambda_u241 = this;
            if (nowMs < $this$bumps_u24lambda_u241.bumpSlowUntil) {
                out.add(new Ideas.Part("speed_bumps", Double.valueOf(5.36448d), 0.0d, 0.0d, "Speed bump", 60, 12, null));
                return;
            }
            return;
        }
        double target = Intrinsics.areEqual(next.getSpot().getNote(), "cushion") ? 6.7056d : 5.36448d;
        double cap = Ideas.approach$default(Ideas.INSTANCE, target, next.getAhead() - 3.0d, 0.0d, 4, null);
        if (next.getAhead() <= 3.0d) {
            this.bumpSlowUntil = nowMs + 1500;
        }
        if (cap < h.getV() + 2.0d || next.getAhead() < 40.0d) {
            out.add(new Ideas.Part("speed_bumps", Double.valueOf(Math.max(cap, target)), 0.0d, 0.0d, "Speed bump in " + Ideas.INSTANCE.ft(Math.max(0.0d, next.getAhead())) + " ft", 60, 12, null));
        }
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    private final void learnJolts(UiSettings s, Ideas.Here h, TabletMotion m, long nowMs) {
        while (true) {
            TabletMotion.Jolt j = m.poll();
            if (j != null) {
                if (h != null && !Double.isNaN(h.getYaw())) {
                    String kind = j.getKind();
                    switch (kind.hashCode()) {
                        case -390630251:
                            if (!kind.equals("pothole")) {
                                continue;
                            } else if (!Ideas.INSTANCE.on(s, "potholes")) {
                                break;
                            } else {
                                double lat = Ideas.INSTANCE.lateralOnStreet(h);
                                IdeaSpots.INSTANCE.record("pothole", h.getLat(), h.getLon(), h.getHdgDeg(), (r35 & 16) != 0 ? Double.NaN : Double.isNaN(lat) ? lat : (j.getSide() * 0.8d) + lat, (r35 & 32) != 0 ? "" : j.getSide() < 0 ? "left wheel" : "right wheel", (r35 & 64) != 0 ? System.currentTimeMillis() : 0L, (r35 & 128) != 0 ? 10.0d : 4.0d, (r35 & 256) != 0 ? "me" : null);
                                break;
                            }
                        case 3035446:
                            if (!kind.equals("bump")) {
                                break;
                            } else {
                                break;
                            }
                        case 108703929:
                            if (!kind.equals("rough")) {
                                break;
                            } else {
                                break;
                            }
                    }
                    if (Ideas.INSTANCE.on(s, "speed_bumps") && h.getV() < 13.5d) {
                        OsmRoads.Way street = Ideas.INSTANCE.street(h);
                        boolean z = false;
                        if (street != null && street.getHighway()) {
                            z = true;
                        }
                        if (!z) {
                            IdeaSpots ideaSpots = IdeaSpots.INSTANCE;
                            String kind2 = j.getKind();
                            double lat2 = h.getLat();
                            double lon = h.getLon();
                            double hdgDeg = h.getHdgDeg();
                            String format = String.format("felt %.1f", Arrays.copyOf(new Object[]{Float.valueOf(j.getSize())}, 1));
                            Intrinsics.checkNotNullExpressionValue(format, "format(...)");
                            ideaSpots.record(kind2, lat2, lon, hdgDeg, (r35 & 16) != 0 ? Double.NaN : 0.0d, (r35 & 32) != 0 ? "" : format, (r35 & 64) != 0 ? System.currentTimeMillis() : 0L, (r35 & 128) != 0 ? 10.0d : 0.0d, (r35 & 256) != 0 ? "me" : null);
                        }
                    }
                }
            } else {
                return;
            }
        }
    }

    private final void potholes(Telemetry t, Ideas.Here h, List<Ideas.Part> out) {
        IdeaSpots.Seen next;
        if (Double.isNaN(h.getYaw()) || (next = (IdeaSpots.Seen) CollectionsKt.firstOrNull(IdeaSpots.around$default(IdeaSpots.INSTANCE, h.getLat(), h.getLon(), h.getYaw(), SetsKt.setOf("pothole"), 60.0d, 2.0d, 4.0d, 0, false, 384, null))) == null) {
            return;
        }
        double me = Ideas.INSTANCE.lateralOnStreet(h);
        double hole = next.getSpot().getLateral();
        if (!Double.isNaN(me) && !Double.isNaN(hole)) {
            double gap = hole - me;
            if (Math.abs(Math.abs(gap) - 0.8d) < 0.45d) {
                out.add(new Ideas.Part("potholes", null, 0.0d, gap > 0.0d ? -0.3d : 0.3d, "Pothole ahead - moving over a little", 40, 6, null));
                return;
            } else if (Math.abs(gap) > 1.4d) {
                return;
            }
        }
        Double valueOf = Double.valueOf(Math.max(6.7056d, h.getV() - 2.2d));
        valueOf.doubleValue();
        if (!(next.getAhead() < 40.0d)) {
            valueOf = null;
        }
        out.add(new Ideas.Part("potholes", valueOf, 0.0d, 0.0d, "Pothole ahead", 40, 12, null));
    }

    /* JADX WARN: Code restructure failed: missing block: B:73:0x0184, code lost:
    
        if (r7 == false) goto L74;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x0186, code lost:
    
        r31.cutinUntil = 3000 + r35;
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x0191, code lost:
    
        if (java.lang.Double.isNaN(r31.cutinFrom) == false) goto L73;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x0193, code lost:
    
        r31.cutinFrom = r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x0195, code lost:
    
        r31.cutinText = r11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x0027, code lost:
    
        if ((r7 != null && r7.getHighway()) != false) goto L14;
     */
    /* JADX WARN: Removed duplicated region for block: B:112:0x0202  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x003f  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x00db A[LOOP:1: B:25:0x00d3->B:27:0x00db, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:80:0x01a1  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x020c  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x023a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final void cutins(Telemetry t, UiSettings s, Ideas.Here h, long nowMs, List<Ideas.Part> out) {
        boolean highway;
        String str;
        int length;
        int i;
        Lead l;
        IdeaSpots.Seen spot;
        String it;
        RadarTrack[] radarTrackArr;
        ArrayDeque arrayDeque;
        int i2;
        float f;
        float f2;
        double v = t.getVEgo();
        if (v <= 15.0d) {
            if (h != null) {
                OsmRoads.Way street = Ideas.INSTANCE.street(h);
            }
            highway = false;
            HashSet seenIds = new HashSet();
            str = null;
            ModelSnapshot m = t.getModel();
            RadarTrack[] radar = t.getRadar();
            length = radar.length;
            i = 0;
            while (i < length) {
                RadarTrack r = radar[i];
                seenIds.add(Long.valueOf(r.getId()));
                Map $this$getOrPut$iv = this.trackHist;
                Long valueOf = Long.valueOf(r.getId());
                ArrayDeque<float[]> arrayDeque2 = $this$getOrPut$iv.get(valueOf);
                if (arrayDeque2 == null) {
                    arrayDeque = new ArrayDeque();
                    radarTrackArr = radar;
                    $this$getOrPut$iv.put(valueOf, arrayDeque);
                } else {
                    radarTrackArr = radar;
                    arrayDeque = arrayDeque2;
                }
                ArrayDeque q = arrayDeque;
                if (m == null || m.getPosX().length <= 2) {
                    i2 = length;
                    f = 0.0f;
                } else {
                    f = 0.0f;
                    if (r.getDRel() > 0.0f) {
                        i2 = length;
                        f2 = Companion.interp(r.getDRel(), m.getPosX(), m.getPosY());
                        float pathY = f2;
                        q.addLast(new float[]{nowMs % 1000000, r.getDRel(), Math.abs(r.getYRel() + pathY)});
                        while (q.size() > 12) {
                            q.removeFirst();
                        }
                        if (!highway && q.size() >= 6) {
                            float dRel = r.getDRel();
                            if (-3.0f <= dRel && dRel <= 40.0f) {
                                float[] a = q.first();
                                float[] b = q.last();
                                float dt = (b[0] - a[0]) / 1000.0f;
                                if (dt >= 0.4f) {
                                    float toward = (a[2] - b[2]) / dt;
                                    float f3 = b[2];
                                    if ((1.6f <= f3 && f3 <= 4.6f) && toward > 0.45f) {
                                        str = "Car drifting into your lane - making room";
                                    }
                                }
                            }
                        }
                        i++;
                        length = i2;
                        radar = radarTrackArr;
                    } else {
                        i2 = length;
                    }
                }
                f2 = f;
                float pathY2 = f2;
                q.addLast(new float[]{nowMs % 1000000, r.getDRel(), Math.abs(r.getYRel() + pathY2)});
                while (q.size() > 12) {
                }
                if (!highway) {
                }
                i++;
                length = i2;
                radar = radarTrackArr;
            }
            this.trackHist.keySet().retainAll(seenIds);
            if (str == null && Ideas.INSTANCE.on(s, "wheels") && (it = Ideas.INSTANCE.getSense().cameraDrift(nowMs)) != null) {
                str = it;
            }
            l = t.getLeadOne();
            if (l.getStatus()) {
                this.leadD = Float.NaN;
            } else {
                if (!Float.isNaN(this.leadD) && nowMs - this.leadMs < 400 && this.leadD - l.getDRel() > 8.0f && l.getDRel() < 30.0f && highway && h != null && !Double.isNaN(h.getYaw())) {
                    IdeaSpots.INSTANCE.record("cutin", h.getLat(), h.getLon(), h.getHdgDeg(), (r35 & 16) != 0 ? Double.NaN : 0.0d, (r35 & 32) != 0 ? "" : null, (r35 & 64) != 0 ? System.currentTimeMillis() : 0L, (r35 & 128) != 0 ? 10.0d : 60.0d, (r35 & 256) != 0 ? "me" : null);
                }
                this.leadD = l.getDRel();
                this.leadMs = nowMs;
            }
            if (nowMs < this.cutinUntil) {
                this.cutinFrom = Double.NaN;
                if (highway && h != null && !Double.isNaN(h.getYaw()) && (spot = (IdeaSpots.Seen) CollectionsKt.firstOrNull(IdeaSpots.around$default(IdeaSpots.INSTANCE, h.getLat(), h.getLon(), h.getYaw(), SetsKt.setOf("cutin"), 400.0d, 50.0d, 25.0d, 2, false, 256, null))) != null) {
                    out.add(new Ideas.Part("nyc_cutins", null, 0.3d, 0.0d, "Cut-in spot - extra room (" + spot.getSpot().getCount() + "x)", 20, 10, null));
                    return;
                }
                return;
            }
            out.add(new Ideas.Part("nyc_cutins", Double.valueOf(Math.max(4.4704d, this.cutinFrom - 1.5d)), 0.5d, 0.0d, this.cutinText, 70, 8, null));
            return;
        }
        highway = true;
        HashSet seenIds2 = new HashSet();
        str = null;
        ModelSnapshot m2 = t.getModel();
        RadarTrack[] radar2 = t.getRadar();
        length = radar2.length;
        i = 0;
        while (i < length) {
        }
        this.trackHist.keySet().retainAll(seenIds2);
        if (str == null) {
            str = it;
        }
        l = t.getLeadOne();
        if (l.getStatus()) {
        }
        if (nowMs < this.cutinUntil) {
        }
    }

    private final void unsure(Telemetry t, long nowMs, List<Ideas.Part> out) {
        ModelSnapshot m = t.getModel();
        if (m == null) {
            return;
        }
        double v = t.getVEgo();
        float std = (m.getPosYStd().length != m.getPosX().length || m.getPosX().length <= 2) ? Float.NaN : Companion.interp(20.0f, m.getPosX(), m.getPosYStd());
        boolean unsureNow = (!Float.isInfinite(std) && !Float.isNaN(std)) && std > 0.6f && v > 6.0d && t.getEnabled();
        if (unsureNow) {
            if (this.unsureSince == 0) {
                this.unsureSince = nowMs;
            }
            this.sureSince = 0L;
        } else {
            if (this.sureSince == 0) {
                this.sureSince = nowMs;
            }
            if (nowMs - this.sureSince > 3000) {
                this.unsureSince = 0L;
                this.unsureFrom = Double.NaN;
            }
        }
        if (this.unsureSince != 0 && nowMs - this.unsureSince > 1000) {
            if (Double.isNaN(this.unsureFrom)) {
                this.unsureFrom = v;
            }
            out.add(new Ideas.Part("unsure_slow", Double.valueOf(Math.max(4.4704d, this.unsureFrom - 2.2d)), 0.0d, 0.0d, "Model unsure - 5 mph slower", 30, 12, null));
        }
    }

    private final void spots(Telemetry t, Ideas.Here h, List<Ideas.Part> out) {
        IdeaSpots.Seen sp;
        String it;
        if (Double.isNaN(h.getYaw()) || (sp = (IdeaSpots.Seen) CollectionsKt.firstOrNull(IdeaSpots.around$default(IdeaSpots.INSTANCE, h.getLat(), h.getLon(), h.getYaw(), SetsKt.setOf("takeover"), 150.0d, 20.0d, 15.0d, 2, false, 256, null))) == null) {
            return;
        }
        Double valueOf = Double.valueOf(Math.max(4.4704d, h.getV() - 1.3d));
        int count = sp.getSpot().getCount();
        String it2 = sp.getSpot().getNote();
        if (!(it2.length() > 0)) {
            it2 = null;
        }
        if (it2 == null || (it = " (" + it2 + ")") == null) {
            it = "";
        }
        out.add(new Ideas.Part("spot_settings", valueOf, 0.3d, 0.0d, "You took over here " + count + "x" + it, 25, 8, null));
    }

    private final void takeovers(Telemetry t, UiSettings s, Ideas.Here h, long nowMs) {
        boolean en = t.getEnabled();
        if (this.wasEnabled && !en && t.getVEgo() > 2.5f) {
            if (h != null && (Ideas.INSTANCE.on(s, "spot_settings") || Ideas.INSTANCE.on(s, "takeover_why"))) {
                IdeaSpots.INSTANCE.record("takeover", h.getLat(), h.getLon(), h.getHdgDeg(), (r35 & 16) != 0 ? Double.NaN : 0.0d, (r35 & 32) != 0 ? "" : null, (r35 & 64) != 0 ? System.currentTimeMillis() : 0L, (r35 & 128) != 0 ? 10.0d : 60.0d, (r35 & 256) != 0 ? "me" : null);
            }
            if (Ideas.INSTANCE.on(s, "takeover_why")) {
                this.askWhy = nowMs;
                this.askHere = h;
            }
        }
        this.wasEnabled = en;
    }

    public final void answerWhy(Context ctx, String why) {
        Ideas.Here h;
        String str;
        long at;
        IdeaSpots.Spot record;
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        long at2 = this.askWhy;
        this.askWhy = 0L;
        if (why != null && at2 != 0) {
            Ideas.Here h2 = this.askHere;
            if (h2 != null) {
                h = h2;
                at = at2;
                str = why;
                record = IdeaSpots.INSTANCE.record("takeover", h2.getLat(), h2.getLon(), h2.getHdgDeg(), (r35 & 16) != 0 ? Double.NaN : 0.0d, (r35 & 32) != 0 ? "" : why, (r35 & 64) != 0 ? System.currentTimeMillis() : 0L, (r35 & 128) != 0 ? 10.0d : 60.0d, (r35 & 256) != 0 ? "me" : null);
                record.setCount(record.getCount() - 1);
            } else {
                h = h2;
                str = why;
                at = at2;
            }
            try {
                File f = new File(ctx.getFilesDir(), "takeover_why.csv");
                if (!f.exists()) {
                    try {
                        FilesKt.writeText$default(f, "time,lat,lon,mph,why\n", null, 2, null);
                    } catch (Exception e) {
                        return;
                    }
                }
                try {
                    String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(new Date(at));
                    Object obj = "";
                    String valueOf = h != null ? Double.valueOf(h.getLat()) : "";
                    String valueOf2 = h != null ? Double.valueOf(h.getLon()) : "";
                    if (h != null) {
                        Ideas.Here it = h;
                        obj = Long.valueOf(Math.round(Ideas.INSTANCE.mph(it.getV())));
                    }
                    FilesKt.appendText$default(f, format + "," + valueOf + "," + valueOf2 + "," + obj + "," + str + "\n", null, 2, null);
                } catch (Exception e2) {
                }
            } catch (Exception e3) {
            }
        }
    }

    /* compiled from: Ideas.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final float interp(float x, float[] xs, float[] ys) {
            Intrinsics.checkNotNullParameter(xs, "xs");
            Intrinsics.checkNotNullParameter(ys, "ys");
            if (x <= xs[0]) {
                return ys[0];
            }
            int length = xs.length;
            for (int i = 1; i < length; i++) {
                if (xs[i] >= x) {
                    float f = (x - xs[i - 1]) / Math.max(0.001f, xs[i] - xs[i - 1]);
                    return ys[i - 1] + ((ys[i] - ys[i - 1]) * f);
                }
            }
            return ArraysKt.last(ys);
        }
    }
}
