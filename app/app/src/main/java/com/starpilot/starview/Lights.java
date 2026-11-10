package com.starpilot.starview;

import com.starpilot.starview.Detector;
import com.starpilot.starview.Ideas;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Pair;
import kotlin.Triple;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: IdeasMemory.kt */
public final class Lights {
    private volatile boolean actuated;
    private boolean dirty;
    private Triple<String, Double, Long> redAhead;
    private final HashMap<String, ArrayList<Long>> seen = new HashMap<>();
    private final HashMap<String, Integer> lastLamp = new HashMap<>();
    private final HashMap<String, Long> redSince = new HashMap<>();
    private final HashMap<String, ArrayList<Integer>> redLens = new HashMap<>();

    public final boolean getActuated() {
        return this.actuated;
    }

    public final void setActuated(boolean z) {
        this.actuated = z;
    }

    public final int known() {
        return this.seen.size();
    }

    public final Unit load(File f) {
        Intrinsics.checkNotNullParameter(f, "f");
        try {
            File it = f.isFile() ? f : null;
            if (it == null) {
                return null;
            }
            JSONObject o = new JSONObject(FilesKt.readText(it, kotlin.text.Charsets.UTF_8));
            Iterator<String> keys = o.keys();
            Intrinsics.checkNotNullExpressionValue(keys, "keys(...)");
            while (keys.hasNext()) {
                String k = keys.next();
                JSONArray a = o.getJSONArray(k);
                HashMap<String, ArrayList<Long>> hashMap = this.seen;
                int length = a.length();
                ArrayList arrayList = new ArrayList(length);
                for (int it2 = 0; it2 < length; it2++) {
                    arrayList.add(Long.valueOf(a.getLong(it2)));
                }
                hashMap.put(k, new ArrayList<>(arrayList));
            }
            return Unit.INSTANCE;
        } catch (Exception e) {
            return Unit.INSTANCE;
        }
    }

    public final void save(File f) {
        Intrinsics.checkNotNullParameter(f, "f");
        if (this.dirty) {
            try {
                JSONObject o = new JSONObject();
                for (Map.Entry<String, ArrayList<Long>> entry : this.seen.entrySet()) {
                    String k = entry.getKey();
                    ArrayList l = entry.getValue();
                    o.put(k, new JSONArray((Collection) l));
                }
                String jSONObject = o.toString();
                Intrinsics.checkNotNullExpressionValue(jSONObject, "toString(...)");
                FilesKt.writeText(f, jSONObject, kotlin.text.Charsets.UTF_8);
                this.dirty = false;
            } catch (Exception e) {
            }
        }
    }

    /* VF: from Vineflower (jadx: Code restructure failed;incorrect) */
    public final void see(Ideas.Here var1, List<Detector.Obj> var2, long var3) {
        Intrinsics.checkNotNullParameter(var2, "objs");
        if (var1 != null && !Double.isNaN(var1.getYaw())) {
            Pair var30 = null;

            for (Detector.Obj var34 : var2) {
                if (var34.getCls() == 9 && var34.getLamp() >= 0 && !var34.getWideCam()) {
                    float var23 = var34.getX();
                    int var24;
                    if (5.0F <= var23 && var23 <= 130.0F) {
                        var24 = 1;
                    } else {
                        var24 = 0;
                    }

                    if ((var24 != 0) && !(Math.abs(var34.getY()) > 12.0F)) {
                        double var9 = var34.getX();
                        double var7 = Math.cos(var1.getYaw());
                        double var11 = var34.getY();
                        double var13 = Math.sin(var1.getYaw());
                        double var19 = var34.getX();
                        double var17 = Math.sin(var1.getYaw());
                        double var21 = var34.getY();
                        double var15 = Math.cos(var1.getYaw());
                        double var5 = var1.getLat();
                        var15 = (var19 * var17 - var21 * var15) / 111195.0;
                        var17 = var1.getLon();
                        var7 = (var9 * var7 + var11 * var13) / (Math.cos(Math.toRadians(var1.getLat())) * 111320.0);
                        long var26 = Math.round((var5 + var15) / 3.0E-4);
                        long var28 = Math.round((var17 + var7) / 4.0E-4);
                        var24 = (int)((var1.getHdgDeg() + 45) / 90);
                        String var32 = var26 + "_" + var28 + "_" + var24 % 4;
                        Integer var33 = this.lastLamp.put(var32, var34.getLamp());
                        if (var33 != null && var33 == 0 && var34.getLamp() == 2) {
                            Map var35 = this.seen;
                            Object var31 = var35.get(var32);
                            if (var31 == null) {
                                var31 = new ArrayList();
                                var35.put(var32, var31);
                            }

                            ((ArrayList)var31).add(var3);
                            this.dirty = true;
                            var31 = this.redSince.remove(var32);
                            if (var31 != null) {
                                int var25 = (int)((var3 - ((Number)var31).longValue()) / 1000);
                                boolean var42;
                                if (8 <= var25 && var25 < 241) {
                                    var42 = true;
                                } else {
                                    var42 = false;
                                }

                                if (var42) {
                                    var35 = this.redLens;
                                    var31 = var35.get(var32);
                                    if (var31 == null) {
                                        var31 = new ArrayList();
                                        var35.put(var32, var31);
                                    }

                                    ((ArrayList)var31).add(var25);
                                }
                            }
                        }

                        if (var33 != null && var33 != 0 && var34.getLamp() == 0) {
                            this.redSince.put(var32, var3);
                        }

                        if (var34.getLamp() == 0 && (var30 == null || var34.getX() < ((Number)var30.getSecond()).doubleValue())) {
                            var30 = TuplesKt.to(var32, (double)var34.getX());
                        }
                    }
                }
            }

            Triple var36;
            if (var30 != null) {
                var36 = new Triple<>(var30.getFirst(), var30.getSecond(), var3);
            } else {
                label67: {
                    var36 = this.redAhead;
                    if (var36 != null) {
                        boolean var43;
                        if (var3 - ((Number)var36.getThird()).longValue() < 3000L) {
                            var43 = true;
                        } else {
                            var43 = false;
                        }

                        if (var43) {
                            break label67;
                        }
                    }

                    var36 = null;
                }
            }

            this.redAhead = var36;
        }
    }

    public final Pair<Integer, Long> fit(List<Long> times, long nowMs) {
        Object maxElem$iv;
        Object maxElem$iv2;
        Intrinsics.checkNotNullParameter(times, "times");
        List<Long> $this$filter$iv = times;
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            long it = ((Number) element$iv$iv).longValue();
            if (nowMs - it < 1209600000) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        List recent = (List) destination$iv$iv;
        if (recent.size() < 3) {
            return null;
        }
        ArrayList fits = new ArrayList();
        int c = 45;
        while (c < 151) {
            double cm = c * 1000.0d;
            double sx = 0.0d;
            double sy = 0.0d;
            Iterator it2 = recent.iterator();
            while (it2.hasNext()) {
                long t = ((Number) it2.next()).longValue();
                double a = ((t % cm) / cm) * 6.283185307179586d;
                sx += Math.cos(a);
                sy += Math.sin(a);
                recent = recent;
            }
            List recent2 = recent;
            double it3 = (Math.atan2(sy, sx) / 6.283185307179586d) * cm;
            if (it3 < 0.0d) {
                it3 += cm;
            }
            fits.add(new Triple(Integer.valueOf(c), Double.valueOf(Math.hypot(sx, sy) / recent2.size()), Long.valueOf((long) it3)));
            c++;
            recent = recent2;
        }
        ArrayList $this$maxByOrNull$iv = fits;
        Iterator iterator$iv = $this$maxByOrNull$iv.iterator();
        if (iterator$iv.hasNext()) {
            maxElem$iv = iterator$iv.next();
            if (iterator$iv.hasNext()) {
                Triple it4 = (Triple) maxElem$iv;
                double maxValue$iv = ((Number) it4.getSecond()).doubleValue();
                do {
                    Object e$iv = iterator$iv.next();
                    Triple it5 = (Triple) e$iv;
                    double v$iv = ((Number) it5.getSecond()).doubleValue();
                    if (Double.compare(maxValue$iv, v$iv) < 0) {
                        maxElem$iv = e$iv;
                        maxValue$iv = v$iv;
                    }
                } while (iterator$iv.hasNext());
            }
        } else {
            maxElem$iv = null;
        }
        Triple top = (Triple) maxElem$iv;
        if (top == null) {
            return null;
        }
        double bestR = ((Number) top.getSecond()).doubleValue();
        ArrayList $this$filter$iv2 = fits;
        Collection destination$iv$iv2 = new ArrayList();
        for (Object element$iv$iv2 : $this$filter$iv2) {
            Triple it6 = (Triple) element$iv$iv2;
            if (((Number) it6.getFirst()).intValue() % ((Number) top.getFirst()).intValue() == 0 && ((Number) it6.getSecond()).doubleValue() >= bestR - 0.02d) {
                destination$iv$iv2.add(element$iv$iv2);
            }
        }
        Iterable $this$maxByOrNull$iv2 = (List) destination$iv$iv2;
        Iterator iterator$iv2 = $this$maxByOrNull$iv2.iterator();
        if (iterator$iv2.hasNext()) {
            maxElem$iv2 = iterator$iv2.next();
            if (iterator$iv2.hasNext()) {
                Triple it7 = (Triple) maxElem$iv2;
                int maxValue$iv2 = ((Number) it7.getFirst()).intValue();
                do {
                    Object e$iv2 = iterator$iv2.next();
                    Triple it8 = (Triple) e$iv2;
                    int v$iv2 = ((Number) it8.getFirst()).intValue();
                    if (maxValue$iv2 < v$iv2) {
                        maxElem$iv2 = e$iv2;
                        maxValue$iv2 = v$iv2;
                    }
                } while (iterator$iv2.hasNext());
            }
        } else {
            maxElem$iv2 = null;
        }
        Triple pick = (Triple) maxElem$iv2;
        Pair best = pick != null ? TuplesKt.to(pick.getFirst(), pick.getThird()) : null;
        if (best != null) {
            if (bestR > 0.93d) {
                return best;
            }
        }
        return null;
    }

    public final Pair<Integer, Integer> nextGreenS(long nowMs) {
        Triple r = this.redAhead;
        if (r == null) {
            return null;
        }
        ArrayList times = this.seen.get(r.getFirst());
        if (times == null) {
            return TuplesKt.to(null, 0);
        }
        Pair f = fit(times, nowMs);
        if (f == null) {
            return TuplesKt.to(null, Integer.valueOf(times.size()));
        }
        int c = f.component1().intValue();
        long ph = f.component2().longValue();
        long cm = c * 1000;
        long next = (nowMs - (nowMs % cm)) + ph;
        if (next < nowMs) {
            next += cm;
        }
        return TuplesKt.to(Integer.valueOf((int) ((next - nowMs) / 1000)), Integer.valueOf(times.size()));
    }

    public final String chip(Ideas.Here h, long nowMs) {
        ArrayList lens;
        Triple r = this.redAhead;
        if (r == null || nowMs - r.getThird().longValue() > 3000) {
            return null;
        }
        if (this.actuated) {
            Pair<Integer, Integer> nextGreenS = nextGreenS(nowMs);
            if ((nextGreenS != null ? nextGreenS.getFirst() : null) == null && (lens = this.redLens.get(r.getFirst())) != null && lens.size() >= 2) {
                int med = ((Number) CollectionsKt.sorted(lens).get(lens.size() / 2)).intValue();
                Long since = this.redSince.get(r.getFirst());
                return (since != null ? new StringBuilder().append("Green in about ").append(Math.max(0, med - ((int) ((nowMs - since.longValue()) / 1000)))) : new StringBuilder().append("Red here usually lasts about ").append(med)).append(" s").toString();
            }
        }
        Pair<Integer, Integer> nextGreenS2 = nextGreenS(nowMs);
        if (nextGreenS2 == null) {
            return null;
        }
        Integer s = nextGreenS2.component1();
        int n = nextGreenS2.component2().intValue();
        return (s != null ? new StringBuilder().append("Green in ").append(s).append(" s") : new StringBuilder().append("Learning this light (").append(Math.min(n, 2)).append(" of 3 greens seen)")).toString();
    }

    public final Ideas.Part coast(Telemetry t, Ideas.Here h, long nowMs) {
        Intrinsics.checkNotNullParameter(t, "t");
        Triple r = this.redAhead;
        if (r == null || nowMs - r.getThird().longValue() > 1500 || h == null) {
            return null;
        }
        double d = r.getSecond().doubleValue();
        if (d < 25.0d || d > 160.0d) {
            return null;
        }
        if (t.getLeadOne().getStatus() && t.getLeadOne().getDRel() < d - 10.0d) {
            return null;
        }
        Pair<Integer, Integer> nextGreenS = nextGreenS(nowMs);
        Integer greenIn = nextGreenS != null ? nextGreenS.getFirst() : null;
        if (greenIn != null && h.getV() > 1.0d && greenIn.intValue() < d / h.getV()) {
            return null;
        }
        double cap = Ideas.INSTANCE.approach(4.4704d, d - 20.0d, 0.5d);
        if (cap < h.getV() - 0.5d) {
            return new Ideas.Part("hybrid", Double.valueOf(cap), 0.0d, 0.0d, "Red ahead - coasting", 18, 12, null);
        }
        return null;
    }
}
