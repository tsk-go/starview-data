package com.starpilot.starview;

import android.content.Context;
import com.starpilot.starview.BlinkerTurn;
import com.starpilot.starview.Ideas;
import com.starpilot.starview.OsmRoads;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.collections.MapsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: IdeasMemory.kt */
public final class IdeasMemory {
    private long cornerCheckMs;
    private File dir;
    private boolean lanesDirty;
    private volatile double nudgeOffset;
    private volatile long nudgeUntil;
    private volatile long roomUntil;
    private int swayN;
    private double swaySum;
    private volatile ArrayList<float[]> teaching;
    private long turnSentMs;
    private volatile int turnSide;
    private long turnStartMs;
    private final Lights lights = new Lights();
    private final HashMap<String, float[]> lanes = new HashMap<>();
    private float leadY = Float.NaN;
    private double turnYaw = Double.NaN;
    private float prevYaw = Float.NaN;
    private float prevPitch = Float.NaN;
    private volatile String comfortText = "";
    private ArrayList<JSONObject> teachPaths = new ArrayList<>();
    private final DayView dayView = new DayView();
    private float[] lastTeach = new float[0];

    public final Lights getLights() {
        return this.lights;
    }

    public final double getNudgeOffset() {
        return this.nudgeOffset;
    }

    public final long getNudgeUntil() {
        return this.nudgeUntil;
    }

    public final void setNudgeOffset(double d) {
        this.nudgeOffset = d;
    }

    public final void setNudgeUntil(long j) {
        this.nudgeUntil = j;
    }

    public final long getRoomUntil() {
        return this.roomUntil;
    }

    public final void setRoomUntil(long j) {
        this.roomUntil = j;
    }

    public final void voiceTurn(int side) {
        this.turnSide = side;
        this.turnStartMs = System.currentTimeMillis();
        this.turnYaw = Double.NaN;
    }

    public final String getComfortText() {
        return this.comfortText;
    }

    public final void setComfortText(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.comfortText = str;
    }

    public final ArrayList<float[]> getTeaching() {
        return this.teaching;
    }

    public final void setTeaching(ArrayList<float[]> arrayList) {
        this.teaching = arrayList;
    }

    public final DayView getDayView() {
        return this.dayView;
    }

    public final void init(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        File $this$init_u24lambda_u240 = new File(ctx.getFilesDir(), "crazy");
        $this$init_u24lambda_u240.mkdirs();
        this.dir = $this$init_u24lambda_u240;
        try {
            File file = this.dir;
            if (file == null) {
                Intrinsics.throwUninitializedPropertyAccessException("dir");
                file = null;
            }
            File it = new File(file, "lanes.json");
            if (!it.isFile()) {
                it = null;
            }
            if (it != null) {
                JSONObject o = new JSONObject(FilesKt.readText$default(it, null, 1, null));
                Iterator<String> keys = o.keys();
                Intrinsics.checkNotNullExpressionValue(keys, "keys(...)");
                while (keys.hasNext()) {
                    String k = keys.next();
                    JSONArray a = o.getJSONArray(k);
                    this.lanes.put(k, new float[]{(float) a.getDouble(0), (float) a.getDouble(1)});
                }
            }
        } catch (Exception e) {
        }
        try {
            File file2 = this.dir;
            if (file2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("dir");
                file2 = null;
            }
            File it2 = new File(file2, "teach.json");
            if (!it2.isFile()) {
                it2 = null;
            }
            if (it2 != null) {
                JSONArray a2 = new JSONArray(FilesKt.readText$default(it2, null, 1, null));
                int length = a2.length();
                for (int i = 0; i < length; i++) {
                    this.teachPaths.add(a2.getJSONObject(i));
                }
            }
        } catch (Exception e2) {
        }
        Lights lights = this.lights;
        File file3 = this.dir;
        if (file3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("dir");
            file3 = null;
        }
        lights.load(new File(file3, "lights.json"));
        DayView dayView = this.dayView;
        File externalFilesDir = ctx.getExternalFilesDir(null);
        if (externalFilesDir == null) {
            externalFilesDir = ctx.getFilesDir();
        }
        dayView.init(new File(externalFilesDir, "dayview"));
    }

    public final void tick(Telemetry t, UiSettings s, Ideas.Here h, Detector det, long nowMs, List<Ideas.Part> out) {
        boolean noLines;
        double v;
        double v2;
        boolean z;
        double e;
        double n;
        OsmRoads.Way our;
        boolean z2;
        boolean z3;
        boolean z4;
        Ideas.Part it;
        String it2;
        Intrinsics.checkNotNullParameter(t, "t");
        Intrinsics.checkNotNullParameter(s, "s");
        Intrinsics.checkNotNullParameter(out, "out");
        ModelSnapshot m = t.getModel();
        double v3 = t.getVEgo();
        boolean bothLines = m != null && m.getLaneProb().length > 2 && m.getLaneProb()[1] > 0.7f && m.getLaneProb()[2] > 0.7f;
        boolean noLines2 = m != null && m.getLaneProb().length > 2 && m.getLaneProb()[1] < 0.4f && m.getLaneProb()[2] < 0.4f;
        if (Ideas.INSTANCE.on(s, "green_countdown") && (it2 = this.lights.chip(h, nowMs)) != null) {
            out.add(new Ideas.Part("green_countdown", null, 0.0d, 0.0d, it2, 35, 14, null));
        }
        if (Ideas.INSTANCE.on(s, "hybrid") && (it = this.lights.coast(t, h, nowMs)) != null) {
            out.add(it);
        }
        if (!Ideas.INSTANCE.on(s, "lane_memory") || h == null) {
            noLines = noLines2;
            v = v3;
        } else {
            noLines = noLines2;
            v = v3;
            laneMemory(t, h, bothLines, m, out);
        }
        if (Ideas.INSTANCE.on(s, "crowd_line")) {
            crowd(t, noLines, out);
        } else {
            this.leadY = Float.NaN;
        }
        if (Ideas.INSTANCE.on(s, "teach") && h != null) {
            teach(h, out);
        }
        if (Ideas.INSTANCE.on(s, "voice_nudges")) {
            if (nowMs < this.nudgeUntil) {
                if (!(this.nudgeOffset == 0.0d)) {
                    out.add(new Ideas.Part("voice_nudges", null, 0.0d, this.nudgeOffset, "Moved over " + (this.nudgeOffset < 0.0d ? "left" : "right") + " (voice)", 15, 6, null));
                }
            }
            if (nowMs < this.roomUntil) {
                out.add(new Ideas.Part("voice_nudges", null, 0.5d, 0.0d, "Extra room (voice)", 14, 10, null));
            }
        }
        if (Ideas.INSTANCE.on(s, "carsick")) {
            v2 = v;
            carsick(t, v2);
        } else {
            v2 = v;
        }
        if (this.turnSide != 0) {
            voiceTurnTick(h, nowMs);
        }
        if (Ideas.INSTANCE.on(s, "shadows") && h != null && !Double.isNaN(h.getYaw()) && nowMs - this.cornerCheckMs > 1000) {
            this.cornerCheckMs = nowMs;
            ShadowWatch shadowWatch = ShadowWatch.INSTANCE;
            try {
                Pair<Double, Double> local = OsmRoads.INSTANCE.toLocal(h.getLat(), h.getLon());
                e = local.component1().doubleValue();
                n = local.component2().doubleValue();
                OsmRoads.Match it3 = OsmRoads.INSTANCE.matchSeg(e, n, h.getYaw());
                OsmRoads.Way way = null;
                if (it3 != null) {
                    if (!(it3.getDist() < 15.0d)) {
                        it3 = null;
                    }
                    if (it3 != null) {
                        way = it3.getWay();
                    }
                }
                our = way;
            } catch (Exception e2) {
                z = false;
                shadowWatch.setNearCorner(z);
                ShadowWatch.INSTANCE.setWanted(Ideas.INSTANCE.on(s, "shadows"));
                this.dayView.setWanted(Ideas.INSTANCE.on(s, "day_view"));
                if (this.dayView.getWanted()) {
                    return;
                } else {
                    return;
                }
            }
            if (our != null) {
                Iterable $this$any$iv = RangesKt.step(new IntRange(-1, 1), 2);
                if (!($this$any$iv instanceof Collection) || !((Collection) $this$any$iv).isEmpty()) {
                    Iterator it4 = $this$any$iv.iterator();
                    while (true) {
                        if (!it4.hasNext()) {
                            z3 = false;
                            break;
                        }
                        int element$iv = ((IntIterator) it4).nextInt();
                        Iterable $this$any$iv2 = BlinkerTurn.INSTANCE.corners(OsmRoads.INSTANCE.near(e, n, 80.0d), our, e, n, h.getYaw(), element$iv);
                        OsmRoads.Way our2 = our;
                        if (!($this$any$iv2 instanceof Collection) || !((Collection) $this$any$iv2).isEmpty()) {
                            Iterator it5 = $this$any$iv2.iterator();
                            while (true) {
                                if (!it5.hasNext()) {
                                    z4 = false;
                                    break;
                                }
                                Object element$iv2 = it5.next();
                                double ahead = ((BlinkerTurn.Corner) element$iv2).getAhead();
                                if (-5.0d <= ahead && ahead <= 40.0d) {
                                    z4 = true;
                                    break;
                                }
                            }
                        } else {
                            z4 = false;
                        }
                        if (z4) {
                            z3 = true;
                            break;
                        }
                        our = our2;
                    }
                } else {
                    z3 = false;
                }
                if (z3) {
                    z2 = true;
                    z = z2;
                    shadowWatch.setNearCorner(z);
                }
            }
            z2 = false;
            z = z2;
            shadowWatch.setNearCorner(z);
        }
        ShadowWatch.INSTANCE.setWanted(Ideas.INSTANCE.on(s, "shadows"));
        this.dayView.setWanted(Ideas.INSTANCE.on(s, "day_view"));
        if (this.dayView.getWanted() || h == null) {
            return;
        }
        this.dayView.setHere(h);
    }

    private final void voiceTurnTick(Ideas.Here h, long nowMs) {
        if (Double.isNaN(this.turnYaw) && h != null && !Double.isNaN(h.getYaw())) {
            this.turnYaw = h.getYaw();
        }
        boolean turned = (h == null || Double.isNaN(h.getYaw()) || Double.isNaN(this.turnYaw) || Math.abs(Math.IEEEremainder(h.getYaw() - this.turnYaw, 6.283185307179586d)) <= Math.toRadians(60.0d)) ? false : true;
        DataClient dc = Link.INSTANCE.getData();
        if (turned || nowMs - this.turnStartMs > 60000) {
            this.turnSide = 0;
            if (dc != null) {
                DataClient.request$default(dc, "blink", MapsKt.mapOf(TuplesKt.to("side", "off")), null, 4, null);
                return;
            }
            return;
        }
        if (nowMs - this.turnSentMs >= 250 && dc != null && dc.getConnected()) {
            this.turnSentMs = nowMs;
            Pair[] pairArr = new Pair[2];
            pairArr[0] = TuplesKt.to("side", this.turnSide < 0 ? "left" : "right");
            pairArr[1] = TuplesKt.to("why", "turn");
            DataClient.request$default(dc, "blink", MapsKt.mapOf(pairArr), null, 4, null);
        }
    }

    private final String cell(Ideas.Here h) {
        int hb = ((int) ((h.getHdgDeg() + 22.5d) / 45.0d)) % 8;
        double d = 25000;
        return Math.round(h.getLat() * d) + "_" + Math.round(h.getLon() * d) + "_" + hb;
    }

    private final void laneMemory(Telemetry t, Ideas.Here h, boolean both, ModelSnapshot m, List<Ideas.Part> out) {
        float[] fArr;
        if (!Double.isNaN(h.getYaw()) && h.getV() >= 3.0d) {
            double me = Ideas.INSTANCE.lateralOnStreet(h);
            if (Double.isNaN(me)) {
                return;
            }
            String k = cell(h);
            boolean z = false;
            if (both && t.getEnabled()) {
                if (!((m == null || m.getLaneChangeOff()) ? false : true)) {
                    Map $this$getOrPut$iv = this.lanes;
                    float[] fArr2 = $this$getOrPut$iv.get(k);
                    if (fArr2 == null) {
                        fArr = new float[2];
                        $this$getOrPut$iv.put(k, fArr);
                    } else {
                        fArr = fArr2;
                    }
                    float[] c = fArr;
                    if (c[1] < 50.0f) {
                        c[0] = c[0] + ((float) me);
                        c[1] = c[1] + 1.0f;
                        this.lanesDirty = true;
                        return;
                    }
                    return;
                }
            }
            float[] c2 = this.lanes.get(k);
            if (c2 != null && c2[1] >= 3.0f) {
                double want = (c2[0] / c2[1]) - me;
                double abs = Math.abs(want);
                if (0.08d <= abs && abs <= 1.2d) {
                    z = true;
                }
                if (z) {
                    out.add(new Ideas.Part("lane_memory", null, 0.0d, RangesKt.coerceIn(want, -0.3d, 0.3d), "Lane from memory", 10, 6, null));
                }
            }
        }
    }

    private final void crowd(Telemetry t, boolean noLines, List<Ideas.Part> out) {
        Lead l = t.getLeadOne();
        if (noLines && l.getStatus() && l.getDRel() <= 50.0f) {
            if (Math.abs(l.getYRel()) <= 1.6f) {
                this.leadY = Float.isNaN(this.leadY) ? l.getYRel() : this.leadY + ((l.getYRel() - this.leadY) * 0.05f);
                if (Math.abs(this.leadY) > 0.15f) {
                    out.add(new Ideas.Part("crowd_line", null, 0.0d, RangesKt.coerceIn((-this.leadY) * 0.6d, -0.3d, 0.3d), "Following the car ahead's line", 9, 6, null));
                    return;
                }
                return;
            }
        }
        this.leadY = Float.NaN;
    }

    public final void startTeaching() {
        this.teaching = new ArrayList<>();
    }

    public final int stopTeaching(String name) {
        Intrinsics.checkNotNullParameter(name, "name");
        ArrayList pts = this.teaching;
        if (pts == null) {
            return 0;
        }
        this.teaching = null;
        if (pts.size() < 10) {
            return 0;
        }
        ArrayList<JSONObject> arrayList = this.teachPaths;
        JSONObject put = new JSONObject().put("name", name);
        JSONArray $this$stopTeaching_u24lambda_u2412 = new JSONArray();
        int i = 0;
        Iterator<float[]> it = pts.iterator();
        Intrinsics.checkNotNullExpressionValue(it, "iterator(...)");
        while (it.hasNext()) {
            float[] next = it.next();
            Intrinsics.checkNotNullExpressionValue(next, "next(...)");
            float[] p = next;
            Collection destination$iv$iv = new ArrayList(p.length);
            int length = p.length;
            int i2 = 0;
            while (i2 < length) {
                float item$iv$iv = p[i2];
                int it2 = i;
                destination$iv$iv.add(Double.valueOf(item$iv$iv));
                i2++;
                i = it2;
                it = it;
            }
            $this$stopTeaching_u24lambda_u2412.put(new JSONArray(destination$iv$iv));
        }
        Unit unit = Unit.INSTANCE;
        arrayList.add(put.put("pts", $this$stopTeaching_u24lambda_u2412));
        try {
            File file = this.dir;
            if (file == null) {
                Intrinsics.throwUninitializedPropertyAccessException("dir");
                file = null;
            }
            File file2 = new File(file, "teach.json");
            String jSONArray = new JSONArray((Collection) this.teachPaths).toString();
            Intrinsics.checkNotNullExpressionValue(jSONArray, "toString(...)");
            FilesKt.writeText$default(file2, jSONArray, null, 2, null);
        } catch (Exception e) {
        }
        return pts.size();
    }

    public final int taughtCount() {
        return this.teachPaths.size();
    }

    public final void forgetTaught() {
        this.teachPaths.clear();
        try {
            File file = this.dir;
            if (file == null) {
                Intrinsics.throwUninitializedPropertyAccessException("dir");
                file = null;
            }
            new File(file, "teach.json").delete();
        } catch (Exception e) {
        }
    }

    private final void teach(Ideas.Here h, List<Ideas.Part> out) {
        double me = Ideas.INSTANCE.lateralOnStreet(h);
        ArrayList pts = this.teaching;
        double d = 111320.0d;
        if (pts != null) {
            if (Intrinsics.areEqual(h.getRtk(), "fixed") && !Double.isNaN(me)) {
                float[] last = (float[]) CollectionsKt.lastOrNull((List) pts);
                if (last != null && Math.hypot((h.getLat() - last[0]) * 111195.0d, (h.getLon() - last[1]) * 111320.0d * Math.cos(Math.toRadians(h.getLat()))) < 1.0d) {
                    return;
                }
                pts.add(new float[]{(float) h.getLat(), (float) h.getLon(), (float) me, (float) h.getV()});
                return;
            }
            return;
        }
        if (!Double.isNaN(me) && !Double.isNaN(h.getYaw())) {
            JSONArray best = null;
            double bd = 3.0d;
            Iterator<JSONObject> it = this.teachPaths.iterator();
            Intrinsics.checkNotNullExpressionValue(it, "iterator(...)");
            while (it.hasNext()) {
                JSONObject next = it.next();
                Intrinsics.checkNotNullExpressionValue(next, "next(...)");
                JSONObject p = next;
                JSONArray a = p.getJSONArray("pts");
                int i = 0;
                int i2 = 1;
                int length = a.length() - 1;
                while (i < length) {
                    JSONArray q = a.getJSONArray(i);
                    double dx = (q.getDouble(i2) - h.getLon()) * d * Math.cos(Math.toRadians(h.getLat()));
                    int i3 = length;
                    double dy = (q.getDouble(0) - h.getLat()) * 111195.0d;
                    double ahead = (Math.cos(h.getYaw()) * dx) + (Math.sin(h.getYaw()) * dy);
                    double d2 = Math.hypot(dx, dy);
                    if ((0.0d <= ahead && ahead <= 6.0d) && d2 < bd) {
                        bd = d2;
                        best = q;
                    }
                    i++;
                    length = i3;
                    d = 111320.0d;
                    i2 = 1;
                }
                d = 111320.0d;
            }
            if (best == null) {
                return;
            }
            JSONArray q2 = best;
            double want = q2.getDouble(2) - me;
            out.add(new Ideas.Part("teach", Double.valueOf(q2.getDouble(3) + 1.0d), 0.0d, RangesKt.coerceIn(want, -0.3d, 0.3d), "Teach: following your path", 12, 4, null));
        }
    }

    private final void carsick(Telemetry t, double v) {
        if (!t.getDmFace() || v < 3.0d) {
            this.prevYaw = Float.NaN;
            return;
        }
        if (!Float.isNaN(this.prevYaw)) {
            float dy = t.getDmYaw() - this.prevYaw;
            float dp = t.getDmPitch() - this.prevPitch;
            this.swaySum += (dy * dy) + (dp * dp);
            this.swayN++;
        }
        this.prevYaw = t.getDmYaw();
        this.prevPitch = t.getDmPitch();
        if (this.swayN > 0 && this.swayN % 600 == 0) {
            double rms = Math.sqrt(this.swaySum / this.swayN);
            int score = RangesKt.coerceIn((int) (100 - (4000 * rms)), 0, 100);
            this.comfortText = "Comfort " + score + "/100";
        }
    }

    public final void endOfDrive() {
        if (this.lanesDirty) {
            try {
                JSONObject o = new JSONObject();
                for (Map.Entry<String, float[]> entry : this.lanes.entrySet()) {
                    String k = entry.getKey();
                    float[] c = entry.getValue();
                    o.put(k, new JSONArray((Collection) CollectionsKt.listOf((Object[]) new Double[]{Double.valueOf(c[0]), Double.valueOf(c[1])})));
                }
                File file = this.dir;
                if (file == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("dir");
                    file = null;
                }
                File file2 = new File(file, "lanes.json");
                String jSONObject = o.toString();
                Intrinsics.checkNotNullExpressionValue(jSONObject, "toString(...)");
                FilesKt.writeText$default(file2, jSONObject, null, 2, null);
                this.lanesDirty = false;
            } catch (Exception e) {
            }
        }
        Lights lights = this.lights;
        File file3 = this.dir;
        if (file3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("dir");
            file3 = null;
        }
        lights.save(new File(file3, "lights.json"));
        if (this.swayN > 600) {
            int score = RangesKt.coerceIn((int) (100 - (Math.sqrt(this.swaySum / this.swayN) * 4000)), 0, 100);
            try {
                File file4 = this.dir;
                if (file4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("dir");
                    file4 = null;
                }
                FilesKt.appendText$default(new File(file4, "comfort.csv"), System.currentTimeMillis() + "," + score + "\n", null, 2, null);
            } catch (Exception e2) {
            }
        }
        this.swayN = 0;
        this.swaySum = 0.0d;
    }

    public final String lastComfort() {
        List split$default;
        String it;
        try {
            File file = this.dir;
            if (file == null) {
                Intrinsics.throwUninitializedPropertyAccessException("dir");
                file = null;
            }
            String str = (String) CollectionsKt.lastOrNull(FilesKt.readLines$default(new File(file, "comfort.csv"), null, 1, null));
            if (str == null || (split$default = StringsKt.split$default((CharSequence) str, new String[]{","}, false, 0, 6, (Object) null)) == null || (it = (String) CollectionsKt.getOrNull(split$default, 1)) == null) {
                return "";
            }
            String str2 = "last drive " + it + "/100";
            return str2 == null ? "" : str2;
        } catch (Exception e) {
            return "";
        }
    }
}
