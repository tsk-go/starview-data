package com.starpilot.starview;

import com.starpilot.starview.Traffic;
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

/* compiled from: Traffic.kt */
public final class Traffic {
    public static final double BUMPER = 2.3d;
    public static final Companion Companion = new Companion(null);
    private static final long KEEP_MS = 600;
    private static final long LEAD_ID_BASE = -100;
    private static final double MAX_X = 110.0d;
    private static final double MAX_Y = 9.0d;
    private static final int MIN_HITS = 3;
    private final HashMap<Long, Vehicle> byId = new HashMap<>();
    private long lastRadarFrame = -1;
    private final ArrayList<Vehicle> vehicles = new ArrayList<>();

    /* compiled from: Traffic.kt */
    public static final class Vehicle {
        private float accel;
        private boolean brake;
        private int hits;
        private final long id;
        private int kind;
        private long lastSeen;
        private float lastV = Float.NaN;
        private long lastVTime;
        private boolean lead;
        private boolean remembered;
        private boolean stationary;
        private float vAbs;
        private float vRel;
        private double x;
        private double y;

        public Vehicle(long id) {
            this.id = id;
        }

        public final long getId() {
            return this.id;
        }

        public final double getX() {
            return this.x;
        }

        public final double getY() {
            return this.y;
        }

        public final void setX(double d) {
            this.x = d;
        }

        public final void setY(double d) {
            this.y = d;
        }

        public final float getVRel() {
            return this.vRel;
        }

        public final void setVRel(float f) {
            this.vRel = f;
        }

        public final float getVAbs() {
            return this.vAbs;
        }

        public final void setVAbs(float f) {
            this.vAbs = f;
        }

        public final boolean getBrake() {
            return this.brake;
        }

        public final void setBrake(boolean z) {
            this.brake = z;
        }

        public final boolean getStationary() {
            return this.stationary;
        }

        public final void setStationary(boolean z) {
            this.stationary = z;
        }

        public final boolean getLead() {
            return this.lead;
        }

        public final void setLead(boolean z) {
            this.lead = z;
        }

        public final int getKind() {
            return this.kind;
        }

        public final void setKind(int i) {
            this.kind = i;
        }

        public final boolean getRemembered() {
            return this.remembered;
        }

        public final void setRemembered(boolean z) {
            this.remembered = z;
        }

        /* renamed from: getAccel$app_debug, reason: from getter */
        public final float getAccel() {
            return this.accel;
        }

        /* renamed from: getHits$app_debug, reason: from getter */
        public final int getHits() {
            return this.hits;
        }

        /* renamed from: getLastSeen$app_debug, reason: from getter */
        public final long getLastSeen() {
            return this.lastSeen;
        }

        /* renamed from: getLastV$app_debug, reason: from getter */
        public final float getLastV() {
            return this.lastV;
        }

        /* renamed from: getLastVTime$app_debug, reason: from getter */
        public final long getLastVTime() {
            return this.lastVTime;
        }

        public final void setAccel$app_debug(float f) {
            this.accel = f;
        }

        public final void setHits$app_debug(int i) {
            this.hits = i;
        }

        public final void setLastSeen$app_debug(long j) {
            this.lastSeen = j;
        }

        public final void setLastV$app_debug(float f) {
            this.lastV = f;
        }

        public final void setLastVTime$app_debug(long j) {
            this.lastVTime = j;
        }
    }

    public final ArrayList<Vehicle> getVehicles() {
        return this.vehicles;
    }

    /* compiled from: Traffic.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final List<Vehicle> update(Telemetry t, final long nowMs, boolean includeRadar, boolean includeAdjacent) {
        boolean z;
        ArrayList leads;
        RadarTrack[] radarTrackArr;
        int i;
        boolean z2;
        Vehicle it;
        Vehicle it2;
        Intrinsics.checkNotNullParameter(t, "t");
        ArrayList leads2 = new ArrayList(4);
        leads2.add(t.getLeadOne());
        leads2.add(t.getLeadTwo());
        if (includeAdjacent) {
            leads2.add(t.getLeadLeft());
            leads2.add(t.getLeadRight());
        }
        Iterator it3 = leads2.iterator();
        int i2 = 0;
        while (true) {
            if (!it3.hasNext()) {
                break;
            }
            int i3 = i2;
            int i4 = i2 + 1;
            Lead l = (Lead) it3.next();
            long id = (-100) - i3;
            if (!l.getStatus() || l.getDRel() <= 0.5f) {
                this.byId.remove(Long.valueOf(id));
            } else {
                Map $this$getOrPut$iv = this.byId;
                Long valueOf = Long.valueOf(id);
                Vehicle vehicle = $this$getOrPut$iv.get(valueOf);
                if (vehicle == null) {
                    it2 = new Vehicle(id);
                    it2.setKind(i3 % 4);
                    it2.setLead(true);
                    $this$getOrPut$iv.put(valueOf, it2);
                } else {
                    it2 = vehicle;
                }
                Vehicle v = it2;
                v.setX(l.getDRel() + 2.3d);
                v.setY(-l.getYRel());
                v.setVRel(l.getVRel());
                v.setVAbs(l.getVLead());
                v.setStationary(l.getVLead() < 0.5f);
                v.setLastSeen$app_debug(nowMs);
                v.setHits$app_debug(3);
                updateBrake(v, l.getVLead(), nowMs);
            }
            i2 = i4;
        }
        if (includeRadar && t.getRadarFrame() != this.lastRadarFrame) {
            this.lastRadarFrame = t.getRadarFrame();
            float vEgo = t.getVEgo();
            RadarTrack[] radar = t.getRadar();
            int length = radar.length;
            int i5 = 0;
            while (i5 < length) {
                RadarTrack p = radar[i5];
                if (p.getDRel() <= 0.0f || p.getDRel() > 110.0d) {
                    leads = leads2;
                    radarTrackArr = radar;
                    i = length;
                    z2 = z;
                } else if (Math.abs(p.getYRel()) > 9.0d) {
                    leads = leads2;
                    radarTrackArr = radar;
                    i = length;
                    z2 = z;
                } else {
                    Map $this$getOrPut$iv2 = this.byId;
                    Long valueOf2 = Long.valueOf(p.getId());
                    Vehicle vehicle2 = $this$getOrPut$iv2.get(valueOf2);
                    if (vehicle2 == null) {
                        leads = leads2;
                        it = new Vehicle(p.getId());
                        radarTrackArr = radar;
                        i = length;
                        it.setKind((int) ((p.getId() ^ (p.getId() >>> 7)) & 3));
                        $this$getOrPut$iv2.put(valueOf2, it);
                    } else {
                        leads = leads2;
                        radarTrackArr = radar;
                        i = length;
                        it = vehicle2;
                    }
                    Vehicle v2 = it;
                    v2.setX(p.getDRel() + 2.3d);
                    v2.setY(-p.getYRel());
                    v2.setVRel(p.getVRel());
                    v2.setVAbs(p.getVRel() + vEgo);
                    v2.setStationary(Math.abs(v2.getVAbs()) < 1.2f);
                    z2 = true;
                    v2.setHits$app_debug(v2.getHits() + 1);
                    v2.setLastSeen$app_debug(nowMs);
                    updateBrake(v2, v2.getVAbs(), nowMs);
                }
                i5++;
                z = z2;
                leads2 = leads;
                radar = radarTrackArr;
                length = i;
            }
        } else if (!includeRadar) {
            Collection<Vehicle> values = this.byId.values();
            Intrinsics.checkNotNullExpressionValue(values, "<get-values>(...)");
            CollectionsKt.removeAll(values, new Function1() { // from class: com.starpilot.starview.Traffic$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    boolean update$lambda$4;
                    update$lambda$4 = Traffic.update$lambda$4((Traffic.Vehicle) obj);
                    return Boolean.valueOf(update$lambda$4);
                }
            });
        }
        Collection<Vehicle> values2 = this.byId.values();
        Intrinsics.checkNotNullExpressionValue(values2, "<get-values>(...)");
        CollectionsKt.removeAll(values2, new Function1() { // from class: com.starpilot.starview.Traffic$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                boolean update$lambda$5;
                update$lambda$5 = Traffic.update$lambda$5(nowMs, (Traffic.Vehicle) obj);
                return Boolean.valueOf(update$lambda$5);
            }
        });
        this.vehicles.clear();
        Iterable values3 = this.byId.values();
        Intrinsics.checkNotNullExpressionValue(values3, "<get-values>(...)");
        Iterable $this$filter$iv = values3;
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            Vehicle it4 = (Vehicle) element$iv$iv;
            if (it4.getLead()) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        List leadsOut = (List) destination$iv$iv;
        for (Vehicle vehicle3 : this.byId.values()) {
            Intrinsics.checkNotNullExpressionValue(vehicle3, "next(...)");
            Vehicle v3 = vehicle3;
            if (v3.getHits() >= 3) {
                if (!v3.getLead()) {
                    boolean dup = false;
                    Iterator it5 = leadsOut.iterator();
                    while (true) {
                        if (!it5.hasNext()) {
                            break;
                        }
                        Vehicle l2 = (Vehicle) it5.next();
                        if (Math.hypot(l2.getX() - v3.getX(), l2.getY() - v3.getY()) < 3.0d) {
                            dup = true;
                            break;
                        }
                    }
                    if (!dup) {
                        if (v3.getStationary() && Math.abs(v3.getY()) < 1.0d && v3.getX() > 60.0d) {
                        }
                    }
                }
                this.vehicles.add(v3);
            }
        }
        return this.vehicles;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean update$lambda$4(Vehicle it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return !it.getLead();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean update$lambda$5(long $nowMs, Vehicle it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return $nowMs - it.getLastSeen() > 600;
    }

    private final void updateBrake(Vehicle v, float speed, long nowMs) {
        if (!Float.isNaN(v.getLastV()) && nowMs > v.getLastVTime()) {
            float dt = (nowMs - v.getLastVTime()) / 1000.0f;
            if (0.02f <= dt && dt <= 1.0f) {
                float a = (speed - v.getLastV()) / dt;
                v.setAccel$app_debug((v.getAccel() * 0.7f) + (a * 0.3f));
            }
        }
        v.setLastV$app_debug(speed);
        v.setLastVTime$app_debug(nowMs);
        v.setBrake(v.getAccel() < -0.9f && speed > 0.3f);
    }
}
