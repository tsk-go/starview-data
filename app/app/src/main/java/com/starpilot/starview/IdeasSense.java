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

    /* VF: from Vineflower (jadx: Code restructure failed;incorrect) */
    public final void tick(Telemetry var1, UiSettings var2, Ideas.Here var3, Detector var4, long var5, List<Ideas.Part> var7) {
        Intrinsics.checkNotNullParameter(var1, "t");
        Intrinsics.checkNotNullParameter(var2, "s");
        Intrinsics.checkNotNullParameter(var7, "out");
        float var10;
        if (this.lastMs == 0L) {
            var10 = 0.0F;
        } else {
            var10 = RangesKt.coerceIn((float)(var5 - this.lastMs) / 1000.0F, 0.0F, 0.5F);
        }

        List var14;
        label73: {
            this.lastMs = var5;
            if (var4 != null) {
                var14 = var4.getObjects();
                if (var14 != null) {
                    boolean var11;
                    if (var5 - var4.getObjectsAtMs() < 800L) {
                        var11 = true;
                    } else {
                        var11 = false;
                    }

                    List var13;
                    if (var11) {
                        var13 = var14;
                    } else {
                        var13 = null;
                    }

                    var14 = var13;
                    if (var13 != null) {
                        break label73;
                    }
                }
            }

            var14 = CollectionsKt.emptyList();
        }

        double var8 = var1.getVEgo();
        this.leadLamps.setEnabled(Ideas.INSTANCE.on(var2, "lead_blinkers"));
        if (Ideas.INSTANCE.on(var2, "ghosts")) {
            this.ghosts(var14, var8, var10, var5, var7);
        }

        if (Ideas.INSTANCE.on(var2, "dooring")) {
            this.dooring(var14, var8, var7);
        }

        if (Ideas.INSTANCE.on(var2, "wheels")) {
            this.drift(var14, var5);
        } else {
            this.driftText = null;
        }

        if (Ideas.INSTANCE.on(var2, "lead_blinkers")) {
            this.leadBlinkers(var1, var8, var5, var7);
        } else {
            this.leadSigFrom = Double.NaN;
        }

        if (Ideas.INSTANCE.on(var2, "sirens")) {
            SirenEar.INSTANCE.want(true);
            if (var5 < this.sirenUntil) {
                var7.add(new Ideas.Part("sirens", null, 0.3, 0.0, "Siren nearby - be ready to pull over", 90, 10, null));
            }
        } else {
            SirenEar.INSTANCE.want(false);
        }

        if (Ideas.INSTANCE.on(var2, "shadows") && var5 - this.shadowAtMs < 2500L) {
            var7.add(new Ideas.Part("shadows", null, 0.0, 0.0, "Moving shadow at the corner - someone may be coming", 50, 14, null));
        }

        if (Ideas.INSTANCE.on(var2, "eyes")) {
            this.eyes(var1, var8, var5, var7);
        }

        if (Ideas.INSTANCE.on(var2, "green_countdown") || Ideas.INSTANCE.on(var2, "hybrid")) {
            Ideas.INSTANCE.getMemory().getLights().see(var3, var14, var5);
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
            Map.Entry<Long, Person> next = ((Entry<Long,Person>) it5.next());
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
            Ghost next2 = ((Ghost) gi.next());
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

    /* VF: from Vineflower (jadx: Type inference failed) */
    private final void dooring(List<Detector.Obj> var1, double var2, List<Ideas.Part> var4) {
        if (!(var2 < 3.0)) {
            for (Detector.Obj var11 : var1) {
                if (VEHICLES.contains(var11.getCls()) && !var11.getMoving()) {
                    float var7 = var11.getX();
                    boolean var9 = true;
                    boolean var8;
                    if (2.0F <= var7 && var7 <= 25.0F) {
                        var8 = true;
                    } else {
                        var8 = false;
                    }

                    if (var8) {
                        var7 = Math.abs(var11.getCy());
                        if (1.6F <= var7 && var7 <= 4.2F) {
                            var8 = true;
                        } else {
                            var8 = false;
                        }

                        if (var8) {
                            var var12 = var1;
                            if (var12 instanceof Collection && ((Collection)var12).isEmpty()) {
                                var8 = false;
                            } else {
                                Iterator var13 = var12.iterator();

                                while (true) {
                                    if (!var13.hasNext()) {
                                        var8 = false;
                                        break;
                                    }

                                    Detector.Obj var19 = (Detector.Obj)var13.next();
                                    if (var19.getCls() == 0
                                        && Math.abs(var19.getX() - var11.getCx()) < 3.0F
                                        && Math.abs(var19.getY()) < Math.abs(var11.getCy()) + 0.3F
                                        && Math.abs(var19.getY()) > Math.abs(var11.getCy()) - 2.0F) {
                                        var8 = true;
                                    } else {
                                        var8 = false;
                                    }

                                    if (var8) {
                                        var8 = var9;
                                        break;
                                    }
                                }
                            }

                            if (var8) {
                                double var5 = Math.max(4.4704, var2 - 1.3);
                                if (var11.getCy() > 0.0F) {
                                    var2 = -0.3;
                                } else {
                                    var2 = 0.3;
                                }

                                var4.add(new Ideas.Part("dooring", var5, 0.0, var2, "Door zone - giving room", 75, 4, null));
                                return;
                            }
                        }
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
