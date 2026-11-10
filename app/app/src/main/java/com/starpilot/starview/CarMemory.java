package com.starpilot.starview;

import com.starpilot.starview.CarMemory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

/* compiled from: CarMemory.kt */
public final class CarMemory {
    public static final double BSM_FROM = -9.0d;
    public static final long BSM_QUIET_MS = 800;
    public static final double BSM_TO = -1.0d;
    public static final double BUMPER = 2.3d;
    public static final Companion Companion = new Companion(null);
    public static final long KEEP_MS = 8000;
    public static final long SEEN_MS = 350;
    public static final double Y_MAX = 6.5d;
    public static final double Y_MIN = 1.8d;
    private long lastMs;
    private final HashMap<Long, Car> byId = new HashMap<>();
    private long lastRadarFrame = -1;
    private volatile List<Car> ghosts = CollectionsKt.emptyList();

    /* compiled from: CarMemory.kt */
    public static final class Car {
        private long bsmQuietSinceMs;
        private int hits;
        private final long id;
        private long lastSeenMs;
        private boolean remembered;
        private long rememberedSinceMs;
        private float vAbs;
        private float vRel;
        private double x;
        private double y;

        public Car(long id) {
            this.id = id;
        }

        public final long getId() {
            return this.id;
        }

        public final float getVAbs() {
            return this.vAbs;
        }

        public final float getVRel() {
            return this.vRel;
        }

        public final double getX() {
            return this.x;
        }

        public final double getY() {
            return this.y;
        }

        public final void setVAbs(float f) {
            this.vAbs = f;
        }

        public final void setVRel(float f) {
            this.vRel = f;
        }

        public final void setX(double d) {
            this.x = d;
        }

        public final void setY(double d) {
            this.y = d;
        }

        public final int getHits() {
            return this.hits;
        }

        public final long getLastSeenMs() {
            return this.lastSeenMs;
        }

        public final void setHits(int i) {
            this.hits = i;
        }

        public final void setLastSeenMs(long j) {
            this.lastSeenMs = j;
        }

        public final boolean getRemembered() {
            return this.remembered;
        }

        public final void setRemembered(boolean z) {
            this.remembered = z;
        }

        public final long getRememberedSinceMs() {
            return this.rememberedSinceMs;
        }

        public final void setRememberedSinceMs(long j) {
            this.rememberedSinceMs = j;
        }

        public final long getBsmQuietSinceMs() {
            return this.bsmQuietSinceMs;
        }

        public final void setBsmQuietSinceMs(long j) {
            this.bsmQuietSinceMs = j;
        }
    }

    public final List<Car> getGhosts() {
        return this.ghosts;
    }

    /* compiled from: CarMemory.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final void update(Telemetry t, long nowMs, boolean leftBsm, boolean rightBsm) {
        Intrinsics.checkNotNullParameter(t, "t");
        double dt = this.lastMs == 0 ? 0.0d : RangesKt.coerceIn((nowMs - this.lastMs) / 1000.0d, 0.0d, 0.5d);
        this.lastMs = nowMs;
        if (!t.getStarted() || t.getVEgo() < 2.0f) {
            this.byId.clear();
            this.ghosts = CollectionsKt.emptyList();
            return;
        }
        if (t.getRadarFrame() != this.lastRadarFrame) {
            this.lastRadarFrame = t.getRadarFrame();
            for (RadarTrack p : t.getRadar()) {
                if (p.getMeasured()) {
                    see(p.getId(), p.getDRel() + 2.3d, -p.getYRel(), p.getVRel(), t.getVEgo() + p.getVRel(), nowMs);
                }
            }
        }
        int li = 0;
        for (Lead l : CollectionsKt.listOf(new Lead[]{t.getLeadLeft(), t.getLeadRight()})) {
            if (l.getStatus()) {
                see((-200L) - li, l.getDRel() + 2.3d, -l.getYRel(), l.getVRel(), l.getVLead(), nowMs);
            }
            li++;
        }
        ArrayList<Car> out = new ArrayList<>();
        final List<Car> fresh = new ArrayList<>();
        for (Car c : this.byId.values()) {
            if (nowMs - c.getLastSeenMs() <= 350 && c.getHits() >= 3) {
                fresh.add(c);
            }
        }
        CollectionsKt.removeAll(this.byId.values(), new Function1() { // from class: com.starpilot.starview.CarMemory$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Boolean.valueOf(CarMemory.update$lambda$2(fresh, (CarMemory.Car) obj));
            }
        });
        Iterator<Car> it = this.byId.values().iterator();
        while (it.hasNext()) {
            Car car = it.next();
            if (nowMs - car.getLastSeenMs() <= 350) {
                car.setRemembered(false);
                continue;
            }
            double ay = Math.abs(car.getY());
            boolean beside = 1.8d <= ay && ay <= 6.5d && car.getX() < 30.0d && car.getVAbs() > 3.0f && car.getHits() >= 5;
            if (!beside) {
                if (nowMs - car.getLastSeenMs() > 1500) {
                    it.remove();
                }
                continue;
            }
            if (!car.getRemembered()) {
                car.setRemembered(true);
                car.setRememberedSinceMs(nowMs);
                car.setBsmQuietSinceMs(0L);
            }
            car.setX(car.getX() + (car.getVRel() * dt));
            boolean bsm = car.getY() > 0.0d ? leftBsm : rightBsm;
            double x = car.getX();
            boolean inZone = -9.0d <= x && x <= -1.0d;
            if (inZone && bsm) {
                car.setRememberedSinceMs(nowMs);
                car.setBsmQuietSinceMs(0L);
            } else if (inZone) {
                if (car.getBsmQuietSinceMs() == 0) {
                    car.setBsmQuietSinceMs(nowMs);
                }
            } else {
                car.setBsmQuietSinceMs(0L);
            }
            boolean keep = (car.getBsmQuietSinceMs() == 0 || nowMs - car.getBsmQuietSinceMs() <= 800) && nowMs - car.getRememberedSinceMs() <= 8000 && !(car.getX() < -25.0d) && !(car.getX() > 60.0d);
            if (keep) {
                out.add(car);
            } else {
                it.remove();
            }
        }
        ArrayList<Car> ghosts = new ArrayList<>(out.size());
        for (Car c : out) {
            Car g = new Car(c.getId());
            g.setX(c.getX());
            g.setY(c.getY());
            g.setVRel(c.getVRel());
            g.setVAbs(c.getVAbs());
            g.setRemembered(true);
            ghosts.add(g);
        }
        this.ghosts = ghosts;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean update$lambda$2(List $fresh, Car c) {
        Iterable $this$any$iv;
        Intrinsics.checkNotNullParameter(c, "c");
        if (!c.getRemembered()) {
            return false;
        }
        List $this$any$iv2 = $fresh;
        if (!($this$any$iv2 instanceof Collection) || !$this$any$iv2.isEmpty()) {
            Iterator it = $this$any$iv2.iterator();
            while (true) {
                if (it.hasNext()) {
                    Object element$iv = it.next();
                    Car f = (Car) element$iv;
                    if (((f == c || Math.abs(f.getX() - c.getX()) >= 6.0d || Math.abs(f.getY() - c.getY()) >= 1.5d) ? null : 1) != null) {
                        $this$any$iv = 1;
                        break;
                    }
                } else {
                    $this$any$iv = null;
                    break;
                }
            }
        } else {
            $this$any$iv = null;
        }
        return $this$any$iv != null;
    }

    private final void see(long id, double x, double y, float vRel, float vAbs, long nowMs) {
        Car car;
        if (x <= 110.0d && Math.abs(y) <= 9.0d) {
            Map $this$getOrPut$iv = this.byId;
            Long valueOf = Long.valueOf(id);
            Car car2 = $this$getOrPut$iv.get(valueOf);
            if (car2 == null) {
                car = new Car(id);
                $this$getOrPut$iv.put(valueOf, car);
            } else {
                car = car2;
            }
            Car c = car;
            c.setX(x);
            c.setY(y);
            c.setVRel(vRel);
            c.setVAbs(vAbs);
            c.setLastSeenMs(nowMs);
            c.setHits(c.getHits() + 1);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x0063 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:30:? A[LOOP:0: B:8:0x0018->B:30:?, LOOP_END, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean beside(int side) {
        Car it;
        Iterable $this$any$iv = this.ghosts;
        if (($this$any$iv instanceof Collection) && ((Collection) $this$any$iv).isEmpty()) {
            return false;
        }
        for (Object element$iv : $this$any$iv) {
            Car it2 = (Car) element$iv;
            double y = it2.getY();
            if (side >= 0 ? y < 0.0d : y > 0.0d) {
                double x = it2.getX();
                if ((-10.0d <= x && x <= 5.0d) && Math.abs(it2.getY()) <= 5.0d) {
                    it = 1;
                    if (it == null) {
                        return true;
                    }
                }
            }
            it = null;
            if (it == null) {
            }
        }
        return false;
    }
}
