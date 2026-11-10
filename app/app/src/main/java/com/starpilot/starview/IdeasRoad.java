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
        Iterable $this$filter$iv = IdeaSpots.around$default(IdeaSpots.INSTANCE, h.getLat(), h.getLon(), h.getYaw(), SetsKt.setOf(new String[]{"bump", "rough", "map_bump"}), 140.0d, 6.0d, 7.0d, 0, false, 384, null);
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

    /* VF: from Vineflower (jadx: ) */
    private final void learnJolts(UiSettings var1, Ideas.Here var2, TabletMotion var3, long var4) {
        while (true) {
            TabletMotion.Jolt var16 = var3.poll();
            if (var16 == null) {
                return;
            }

            if (var2 != null && !Double.isNaN(var2.getYaw())) {
                String var17 = var16.getKind();
                switch (var17.hashCode()) {
                    case -390630251:
                        if (!var17.equals("pothole") || !Ideas.INSTANCE.on(var1, "potholes")) {
                            continue;
                        }

                        double var19 = Ideas.INSTANCE.lateralOnStreet(var2);
                        IdeaSpots var26 = IdeaSpots.INSTANCE;
                        double var20 = var2.getLat();
                        double var12 = var2.getLon();
                        double var21 = var2.getHdgDeg();
                        if (!Double.isNaN(var19)) {
                            var19 += var16.getSide() * 0.8;
                        }

                        String var23;
                        if (var16.getSide() < 0) {
                            var23 = "left wheel";
                        } else {
                            var23 = "right wheel";
                        }

                        IdeaSpots.record$default(var26, "pothole", var20, var12, var21, var19, var23, 0L, 4.0, null, 320, null);
                        continue;
                    case 3035446:
                        if (!var17.equals("bump")) {
                            continue;
                        }
                        break;
                    case 108703929:
                        if (!var17.equals("rough")) {
                            continue;
                        }
                        break;
                    default:
                        continue;
                }

                if (Ideas.INSTANCE.on(var1, "speed_bumps") && var2.getV() < 13.5) {
                    OsmRoads.Way var24 = Ideas.INSTANCE.street(var2);
                    boolean var15 = false;
                    boolean var14 = var15;
                    if (var24 != null) {
                        var14 = var15;
                        if (var24.getHighway()) {
                            var14 = true;
                        }
                    }

                    if (!var14) {
                        IdeaSpots var25 = IdeaSpots.INSTANCE;
                        String var18 = var16.getKind();
                        double var10 = var2.getLat();
                        double var6 = var2.getLon();
                        double var8 = var2.getHdgDeg();
                        String var22 = String.format("felt %.1f", Arrays.copyOf(new Object[]{var16.getSize()}, 1));
                        Intrinsics.checkNotNullExpressionValue(var22, "format(...)");
                        IdeaSpots.record$default(var25, var18, var10, var6, var8, 0.0, var22, 0L, 0.0, null, 464, null);
                    }
                }
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

    /* VF: from Vineflower (jadx: Code restructure failed;Removed duplicated region;incorrect) */
    private final void cutins(Telemetry var1, UiSettings var2, Ideas.Here var3, long var4, List<Ideas.Part> var6) {
        double var7;
        int var29;
        label151: {
            label150: {
                var7 = var1.getVEgo();
                if (!(var7 > 15.0)) {
                    if (var3 == null) {
                        break label150;
                    }

                    OsmRoads.Way var15 = Ideas.INSTANCE.street(var3);
                    if (var15 != null && var15.getHighway()) {
                        var29 = (((boolean)true) ? 1 : 0);
                    } else {
                        var29 = (((boolean)false) ? 1 : 0);
                    }

                    if ((var29 == 0)) {
                        break label150;
                    }
                }

                var29 = 1;
                break label151;
            }

            var29 = 0;
        }

        HashSet var18 = new HashSet();
        String var32 = null;
        ModelSnapshot var19 = var1.getModel();
        RadarTrack[] var16 = var1.getRadar();
        int var12 = var16.length;
        int var13 = 0;

        while (var13 < var12) {
            RadarTrack var20 = var16[var13];
            var18.add(var20.getId());
            Map var22 = this.trackHist;
            Long var21 = var20.getId();
            ArrayDeque var17 = (ArrayDeque)var22.get(var21);
            if (var17 == null) {
                var17 = new ArrayDeque();
                var22.put(var21, var17);
            }

            var17 = var17;
            float var9;
            if (var19 != null && var19.getPosX().length > 2 && var20.getDRel() > 0.0F) {
                var9 = Companion.interp(var20.getDRel(), var19.getPosX(), var19.getPosY());
            } else {
                var9 = 0.0F;
            }

            var17.addLast(new float[]{(float)(var4 % 1000000L), var20.getDRel(), Math.abs(var20.getYRel() + var9)});

            while (var17.size() > 12) {
                var17.removeFirst();
            }

            String var35;
            if ((var29 != 0) && var17.size() >= 6) {
                var9 = var20.getDRel();
                boolean var14;
                if (-3.0F <= var9 && var9 <= 40.0F) {
                    var14 = true;
                } else {
                    var14 = false;
                }

                if (!var14) {
                    var35 = var32;
                } else {
                    float[] var36 = (float[])var17.first();
                    float[] var37 = (float[])var17.last();
                    var9 = (var37[0] - var36[0]) / 1000.0F;
                    var35 = var32;
                    if (!(var9 < 0.4F)) {
                        float var10 = (var36[2] - var37[2]) / var9;
                        var9 = var37[2];
                        if (1.6F <= var9 && var9 <= 4.6F) {
                            var14 = true;
                        } else {
                            var14 = false;
                        }

                        var35 = var32;
                        if (var14) {
                            var35 = var32;
                            if (var10 > 0.45F) {
                                var35 = "Car drifting into your lane - making room";
                            }
                        }
                    }
                }
            } else {
                var35 = var32;
            }

            var13++;
            var32 = var35;
        }

        this.trackHist.keySet().retainAll(var18);
        String var33;
        if (var32 == null) {
            var33 = var32;
            if (Ideas.INSTANCE.on(var2, "wheels")) {
                String var25 = Ideas.INSTANCE.getSense().cameraDrift(var4);
                var33 = var32;
                if (var25 != null) {
                    var33 = var25;
                }
            }
        } else {
            var33 = var32;
        }

        if (var33 != null && (var29 != 0)) {
            this.cutinUntil = 3000L + var4;
            if (Double.isNaN(this.cutinFrom)) {
                this.cutinFrom = var7;
            }

            this.cutinText = var33;
        }

        Lead var23 = var1.getLeadOne();
        if (var23.getStatus()) {
            if (!Float.isNaN(this.leadD)
                && var4 - this.leadMs < 400L
                && this.leadD - var23.getDRel() > 8.0F
                && var23.getDRel() < 30.0F
                && (var29 != 0)
                && var3 != null
                && !Double.isNaN(var3.getYaw())) {
                IdeaSpots.record$default(IdeaSpots.INSTANCE, "cutin", var3.getLat(), var3.getLon(), var3.getHdgDeg(), 0.0, null, 0L, 60.0, null, 368, null);
            }

            this.leadD = var23.getDRel();
            this.leadMs = var4;
        } else {
            this.leadD = Float.NaN;
        }

        if (var4 < this.cutinUntil) {
            var6.add(new Ideas.Part("nyc_cutins", Math.max(4.4704, this.cutinFrom - 1.5), 0.5, 0.0, this.cutinText, 70, 8, null));
        } else {
            this.cutinFrom = Double.NaN;
            if ((var29 != 0) && (var3) != null && !Double.isNaN(var3.getYaw())) {
                IdeaSpots.Seen var24 = (Seen) CollectionsKt.firstOrNull(
                    IdeaSpots.around$default(
                        IdeaSpots.INSTANCE, var3.getLat(), var3.getLon(), var3.getYaw(), SetsKt.setOf("cutin"), 400.0, 50.0, 25.0, 2, false, 256, null
                    )
                );
                if (var24 != null) {
                    var29 = var24.getSpot().getCount();
                    var6.add(new Ideas.Part("nyc_cutins", null, 0.3, 0.0, "Cut-in spot - extra room (" + var29 + "x)", 20, 10, null));
                }
            }
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
                        FilesKt.writeText(f, "time,lat,lon,mph,why\n", kotlin.text.Charsets.UTF_8);
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
                    FilesKt.appendText(f, format + "," + valueOf + "," + valueOf2 + "," + obj + "," + str + "\n", kotlin.text.Charsets.UTF_8);
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
