package com.starpilot.starview;

import com.starpilot.starview.Detector;
import com.starpilot.starview.Ideas;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.collections.ArrayDeque;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

/* compiled from: IdeasSense.kt */
public final class IdeasSense {
    public static final Companion Companion = new Companion(null);
    private static final Set<Integer> VEHICLES = SetsKt.setOf((Object[]) new Integer[]{2, 3, 5, 7});
    private long driftMs;
    private String driftText;
    private int eyesSide;
    private long eyesUntil;
    private long lastMs;
    private long lookSince;
    private volatile long shadowAtMs;
    private volatile long sirenUntil;
    private final HashMap<Long, Person> people = new HashMap<>();
    private final ArrayList<Ghost> ghosts = new ArrayList<>();
    private double eyesFrom = Double.NaN;
    private final ArrayDeque<Float> restYaw = new ArrayDeque<>();
    private final ArrayDeque<Float> restPitch = new ArrayDeque<>();
    private final LeadLamps leadLamps = new LeadLamps();
    private double leadSigFrom = Double.NaN;

    /* compiled from: IdeasSense.kt */
    private static final class Person {
        private boolean behind;
        private long seenMs;
        private float vx;
        private float vy;
        private float x;
        private float y;

        public Person(float x, float y, float vx, float vy, long seenMs, boolean behind) {
            this.x = x;
            this.y = y;
            this.vx = vx;
            this.vy = vy;
            this.seenMs = seenMs;
            this.behind = behind;
        }

        public final boolean getBehind() {
            return this.behind;
        }

        public final long getSeenMs() {
            return this.seenMs;
        }

        public final float getVx() {
            return this.vx;
        }

        public final float getVy() {
            return this.vy;
        }

        public final float getX() {
            return this.x;
        }

        public final float getY() {
            return this.y;
        }

        public final void setBehind(boolean z) {
            this.behind = z;
        }

        public final void setSeenMs(long j) {
            this.seenMs = j;
        }

        public final void setVx(float f) {
            this.vx = f;
        }

        public final void setVy(float f) {
            this.vy = f;
        }

        public final void setX(float f) {
            this.x = f;
        }

        public final void setY(float f) {
            this.y = f;
        }
    }

    /* compiled from: IdeasSense.kt */
    private static final class Ghost {
        private final long bornMs;
        private final float vy;
        private float x;
        private float y;

        public Ghost(float x, float y, float vy, long bornMs) {
            this.x = x;
            this.y = y;
            this.vy = vy;
            this.bornMs = bornMs;
        }

        public final long getBornMs() {
            return this.bornMs;
        }

        public final float getVy() {
            return this.vy;
        }

        public final float getX() {
            return this.x;
        }

        public final float getY() {
            return this.y;
        }

        public final void setX(float f) {
            this.x = f;
        }

        public final void setY(float f) {
            this.y = f;
        }
    }

    public final LeadLamps getLeadLamps() {
        return this.leadLamps;
    }

    public final long getSirenUntil() {
        return this.sirenUntil;
    }

    public final void setSirenUntil(long j) {
        this.sirenUntil = j;
    }

    public final long getShadowAtMs() {
        return this.shadowAtMs;
    }

    public final void setShadowAtMs(long j) {
        this.shadowAtMs = j;
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0053, code lost:
    
        if (r0 == null) goto L18;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void tick(Telemetry t, UiSettings s, Ideas.Here h, Detector det, long nowMs, List<Ideas.Part> out) {
        List emptyList;
        double v;
        List objs;
        String str;
        double v2;
        List objs2;
        boolean z;
        List<Ideas.Part> list;
        Intrinsics.checkNotNullParameter(t, "t");
        Intrinsics.checkNotNullParameter(s, "s");
        Intrinsics.checkNotNullParameter(out, "out");
        float dt = this.lastMs == 0 ? 0.0f : RangesKt.coerceIn((nowMs - this.lastMs) / 1000.0f, 0.0f, 0.5f);
        this.lastMs = nowMs;
        if (det != null && (emptyList = det.getObjects()) != null) {
            if (!(nowMs - det.getObjectsAtMs() < 800)) {
                emptyList = null;
            }
        }
        emptyList = CollectionsKt.emptyList();
        List objs3 = emptyList;
        double v3 = t.getVEgo();
        this.leadLamps.setEnabled(Ideas.INSTANCE.on(s, "lead_blinkers"));
        if (Ideas.INSTANCE.on(s, "ghosts")) {
            v = v3;
            objs = objs3;
            str = "lead_blinkers";
            ghosts(objs3, v3, dt, nowMs, out);
        } else {
            v = v3;
            objs = objs3;
            str = "lead_blinkers";
        }
        if (Ideas.INSTANCE.on(s, "dooring")) {
            v2 = v;
            dooring(objs, v2, out);
        } else {
            v2 = v;
        }
        if (Ideas.INSTANCE.on(s, "wheels")) {
            drift(objs, nowMs);
        } else {
            this.driftText = null;
        }
        if (Ideas.INSTANCE.on(s, str)) {
            objs2 = objs;
            z = true;
            leadBlinkers(t, v2, nowMs, out);
        } else {
            objs2 = objs;
            z = true;
            this.leadSigFrom = Double.NaN;
        }
        if (Ideas.INSTANCE.on(s, "sirens")) {
            SirenEar.INSTANCE.want(z);
            if (nowMs < this.sirenUntil) {
                list = out;
                list.add(new Ideas.Part("sirens", null, 0.3d, 0.0d, "Siren nearby - be ready to pull over", 90, 10, null));
            } else {
                list = out;
            }
        } else {
            list = out;
            SirenEar.INSTANCE.want(false);
        }
        if (Ideas.INSTANCE.on(s, "shadows") && nowMs - this.shadowAtMs < 2500) {
            list.add(new Ideas.Part("shadows", null, 0.0d, 0.0d, "Moving shadow at the corner - someone may be coming", 50, 14, null));
        }
        if (Ideas.INSTANCE.on(s, "eyes")) {
            eyes(t, v2, nowMs, out);
        }
        if (Ideas.INSTANCE.on(s, "green_countdown") || Ideas.INSTANCE.on(s, "hybrid")) {
            Ideas.INSTANCE.getMemory().getLights().see(h, objs2, nowMs);
        }
    }

    private final void ghosts(List<Detector.Obj> objs, double v, float dt, long nowMs, List<Ideas.Part> out) {
        float f;
        boolean z;
        List<Detector.Obj> $this$filter$iv = objs;
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            Detector.Obj it = (Detector.Obj) element$iv$iv;
            if (VEHICLES.contains(Integer.valueOf(it.getCls())) && !it.getMoving()) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        List parked = (List) destination$iv$iv;
        HashSet seen = new HashSet();
        Iterator<Detector.Obj> it2 = objs.iterator();
        while (true) {
            if (!it2.hasNext()) {
                break;
            }
            Detector.Obj o = it2.next();
            if (o.getCls() == 0 && !o.getWideCam()) {
                seen.add(Long.valueOf(o.getId()));
                List $this$any$iv = parked;
                if (!($this$any$iv instanceof Collection) || !$this$any$iv.isEmpty()) {
                    Iterator it3 = $this$any$iv.iterator();
                    while (true) {
                        if (it3.hasNext()) {
                            Object element$iv = it3.next();
                            Detector.Obj it4 = (Detector.Obj) element$iv;
                            if (Math.abs(it4.getCx() - o.getX()) < 5.0f && Math.abs(it4.getCy() - o.getY()) < 2.5f) {
                                z = true;
                                break;
                            }
                        } else {
                            z = false;
                            break;
                        }
                    }
                } else {
                    z = false;
                }
                boolean behind = z;
                Person p = this.people.get(Long.valueOf(o.getId()));
                if (p != null) {
                    p.setX(o.getX());
                    p.setY(o.getY());
                    p.setVx(o.getVelValid() ? o.getVx() : 0.0f);
                    p.setVy(o.getVelValid() ? o.getVy() : 0.0f);
                    p.setSeenMs(nowMs);
                    p.setBehind(behind);
                } else {
                    IdeasSense $this$ghosts_u24lambda_u244 = this;
                    $this$ghosts_u24lambda_u244.people.put(Long.valueOf(o.getId()), new Person(o.getX(), o.getY(), o.getVelValid() ? o.getVx() : 0.0f, o.getVelValid() ? o.getVy() : 0.0f, nowMs, behind));
                    parked = parked;
                    it2 = it2;
                }
            }
        }
        Iterator it5 = this.people.entrySet().iterator();
        while (it5.hasNext()) {
            Map.Entry<Long, Person> next = it5.next();
            Intrinsics.checkNotNullExpressionValue(next, "next(...)");
            Map.Entry<Long, Person> entry = next;
            Long key = entry.getKey();
            Intrinsics.checkNotNullExpressionValue(key, "component1(...)");
            Long id = key;
            Person value = entry.getValue();
            Intrinsics.checkNotNullExpressionValue(value, "component2(...)");
            Person p2 = value;
            if (seen.contains(id)) {
                f = 0.0f;
            } else if (nowMs - p2.getSeenMs() <= 400) {
                f = 0.0f;
            } else {
                if (p2.getBehind()) {
                    float x = p2.getX();
                    if (3.0f <= x && x <= 35.0f) {
                        float abs = Math.abs(p2.getY());
                        if ((1.0f <= abs && abs <= 7.0f) && p2.getVy() * p2.getY() < f && Math.abs(p2.getVy()) > 0.5f) {
                            this.ghosts.add(new Ghost(p2.getX(), p2.getY(), p2.getVy(), nowMs));
                        }
                    }
                }
                it5.remove();
                f = 0.0f;
            }
        }
        Iterator gi = this.ghosts.iterator();
        Intrinsics.checkNotNullExpressionValue(gi, "iterator(...)");
        Ghost nearest = null;
        while (gi.hasNext()) {
            Ghost next2 = gi.next();
            Intrinsics.checkNotNullExpressionValue(next2, "next(...)");
            Ghost g = next2;
            g.setX(g.getX() - ((float) (dt * v)));
            g.setY(g.getY() + (g.getVy() * dt));
            if (nowMs - g.getBornMs() <= 4000 && g.getX() >= 0.0f) {
                if (Math.abs(g.getY()) < 2.0f && (nearest == null || g.getX() < nearest.getX())) {
                    nearest = g;
                }
            }
            gi.remove();
        }
        if (nearest != null) {
            out.add(new Ideas.Part("ghosts", Double.valueOf(Math.max(4.4704d, Ideas.approach$default(Ideas.INSTANCE, 6.7056d, nearest.getX() - 5.0d, 0.0d, 4, null))), 0.0d, 0.0d, "Someone may step out from behind a parked car", 85, 12, null));
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v0 */
    /* JADX WARN: Type inference failed for: r5v1 */
    /* JADX WARN: Type inference failed for: r5v2 */
    /* JADX WARN: Type inference failed for: r5v5 */
    private final void dooring(List<Detector.Obj> objs, double v, List<Ideas.Part> out) {
        if (v < 3.0d) {
            return;
        }
        for (Detector.Obj c : objs) {
            if (VEHICLES.contains(Integer.valueOf(c.getCls())) && !c.getMoving()) {
                float x = c.getX();
                Iterable $this$any$iv = 1;
                if (2.0f <= x && x <= 25.0f) {
                    float abs = Math.abs(c.getCy());
                    if (1.6f <= abs && abs <= 4.2f) {
                        List<Detector.Obj> $this$any$iv2 = objs;
                        if (!($this$any$iv2 instanceof Collection) || !$this$any$iv2.isEmpty()) {
                            Iterator it = $this$any$iv2.iterator();
                            while (true) {
                                if (it.hasNext()) {
                                    Object element$iv = it.next();
                                    Detector.Obj p = (Detector.Obj) element$iv;
                                    if (((p.getCls() != 0 || Math.abs(p.getX() - c.getCx()) >= 3.0f || Math.abs(p.getY()) >= Math.abs(c.getCy()) + 0.3f || Math.abs(p.getY()) <= Math.abs(c.getCy()) - 2.0f) ? null : 1) != null) {
                                        break;
                                    }
                                } else {
                                    $this$any$iv = 0;
                                    break;
                                }
                            }
                        } else {
                            $this$any$iv = 0;
                        }
                        if ($this$any$iv != null) {
                            out.add(new Ideas.Part("dooring", Double.valueOf(Math.max(4.4704d, v - 1.3d)), 0.0d, c.getCy() > 0.0f ? -0.3d : 0.3d, "Door zone - giving room", 75, 4, null));
                            return;
                        }
                    } else {
                        continue;
                    }
                }
            }
        }
    }

    private final void drift(List<Detector.Obj> objs, long nowMs) {
        this.driftText = null;
        for (Detector.Obj o : objs) {
            if (VEHICLES.contains(Integer.valueOf(o.getCls())) && o.getVelValid()) {
                float x = o.getX();
                if (0.0f <= x && x <= 30.0f) {
                    float abs = Math.abs(o.getCy());
                    if ((1.6f <= abs && abs <= 4.8f) && (-Math.signum(o.getCy())) * o.getVy() > 0.5f) {
                        this.driftText = "Car beside you turning toward your lane - making room";
                        this.driftMs = nowMs;
                        return;
                    }
                } else {
                    continue;
                }
            }
        }
    }

    public final String cameraDrift(long nowMs) {
        String str = this.driftText;
        if (str != null) {
            if (nowMs - this.driftMs < 600) {
                return str;
            }
        }
        return null;
    }

    private final void leadBlinkers(Telemetry t, double v, long nowMs, List<Ideas.Part> out) {
        int side = this.leadLamps.side(nowMs);
        Lead l = t.getLeadOne();
        if (side != 0 && l.getStatus() && l.getDRel() <= 60.0f) {
            if (v >= 4.0d) {
                if (Double.isNaN(this.leadSigFrom)) {
                    this.leadSigFrom = v;
                }
                out.add(new Ideas.Part("lead_blinkers", Double.valueOf(Math.max(4.4704d, this.leadSigFrom - 1.0d)), 0.3d, 0.0d, "Car ahead signaling " + (side < 0 ? "left" : "right") + " - easing off", 55, 8, null));
                return;
            }
        }
        this.leadSigFrom = Double.NaN;
    }

    private final void eyes(Telemetry t, double v, long nowMs, List<Ideas.Part> out) {
        if (!t.getDmFace()) {
            this.lookSince = 0L;
            return;
        }
        float yaw = t.getDmYaw();
        float pitch = t.getDmPitch();
        if (v < 0.5d && (Math.abs(yaw) > 0.25f || pitch < -0.25f)) {
            this.restYaw.addLast(Float.valueOf(yaw));
            this.restPitch.addLast(Float.valueOf(pitch));
            while (this.restYaw.size() > 300) {
                this.restYaw.removeFirst();
                this.restPitch.removeFirst();
            }
        }
        Float ry = (Float) CollectionsKt.getOrNull(CollectionsKt.sorted(this.restYaw), this.restYaw.size() / 2);
        Float rp = (Float) CollectionsKt.getOrNull(CollectionsKt.sorted(this.restPitch), this.restPitch.size() / 2);
        boolean looking = false;
        boolean atScreen = ry != null && rp != null && Math.abs(yaw - ry.floatValue()) < 0.22f && Math.abs(pitch - rp.floatValue()) < 0.22f;
        if (Math.abs(yaw) > 0.4f && pitch > -0.2f && !atScreen) {
            if ((3.0d <= v && v <= 13.5d) && t.getEnabled()) {
                looking = true;
            }
        }
        if (!looking) {
            this.lookSince = 0L;
        } else if (this.lookSince == 0) {
            this.lookSince = nowMs;
        }
        if (this.lookSince != 0 && nowMs - this.lookSince > 1200) {
            this.eyesUntil = 4000 + nowMs;
            this.eyesSide = yaw > 0.0f ? -1 : 1;
            if (Double.isNaN(this.eyesFrom)) {
                this.eyesFrom = v;
            }
        }
        if (nowMs >= this.eyesUntil) {
            this.eyesFrom = Double.NaN;
        } else {
            out.add(new Ideas.Part("eyes", Double.valueOf(Math.max(4.4704d, this.eyesFrom - 1.3d)), 0.0d, 0.0d, "You're watching the " + (this.eyesSide < 0 ? "left" : "right") + " - easing off", 45, 12, null));
        }
    }

    /* compiled from: IdeasSense.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final Set<Integer> getVEHICLES() {
            return IdeasSense.VEHICLES;
        }
    }
}
