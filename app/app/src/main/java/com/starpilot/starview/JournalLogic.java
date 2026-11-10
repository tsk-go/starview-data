package com.starpilot.starview;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.collections.MapsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import org.json.JSONObject;

/* compiled from: DriveJournal.kt */
public final class JournalLogic {
    public static final Companion Companion = new Companion(null);
    private int alerts;
    private boolean brakeHeld;
    private long driveStart;
    private boolean driving;
    private long engagedMs;
    private int hardBrakes;
    private Sample last;
    private double meters;
    private int takeovers;
    private final ArrayList<JSONObject> out = new ArrayList<>();
    private String lastAlert = "";
    private double startLat = Double.NaN;
    private double startLon = Double.NaN;
    private String startRoad = "";

    /* compiled from: DriveJournal.kt */
    public static final class Sample {
        private final float aEgo;
        private final String alert1;
        private final String alert2;
        private final String alertStatus;
        private final boolean brake;
        private final boolean enabled;
        private final boolean gas;
        private final double lat;
        private final double lon;
        private final String road;
        private final boolean started;
        private final boolean steering;
        private final long tMs;
        private final float vEgo;

        public Sample(long tMs, boolean started, boolean enabled, float vEgo, float aEgo, String alert1, String alert2, String alertStatus, boolean brake, boolean gas, boolean steering, double lat, double lon, String road) {
            Intrinsics.checkNotNullParameter(alert1, "alert1");
            Intrinsics.checkNotNullParameter(alert2, "alert2");
            Intrinsics.checkNotNullParameter(alertStatus, "alertStatus");
            Intrinsics.checkNotNullParameter(road, "road");
            this.tMs = tMs;
            this.started = started;
            this.enabled = enabled;
            this.vEgo = vEgo;
            this.aEgo = aEgo;
            this.alert1 = alert1;
            this.alert2 = alert2;
            this.alertStatus = alertStatus;
            this.brake = brake;
            this.gas = gas;
            this.steering = steering;
            this.lat = lat;
            this.lon = lon;
            this.road = road;
        }

        public final float getAEgo() {
            return this.aEgo;
        }

        public final String getAlert1() {
            return this.alert1;
        }

        public final String getAlert2() {
            return this.alert2;
        }

        public final boolean getEnabled() {
            return this.enabled;
        }

        public final boolean getStarted() {
            return this.started;
        }

        public final long getTMs() {
            return this.tMs;
        }

        public final float getVEgo() {
            return this.vEgo;
        }

        public final String getAlertStatus() {
            return this.alertStatus;
        }

        public final boolean getBrake() {
            return this.brake;
        }

        public final boolean getGas() {
            return this.gas;
        }

        public final double getLat() {
            return this.lat;
        }

        public final double getLon() {
            return this.lon;
        }

        public final String getRoad() {
            return this.road;
        }

        public final boolean getSteering() {
            return this.steering;
        }
    }

    public final ArrayList<JSONObject> getOut() {
        return this.out;
    }

    public final void feed(Sample s) {
        Intrinsics.checkNotNullParameter(s, "s");
        Sample p = this.last;
        this.last = s;
        if (s.getStarted() && !this.driving) {
            this.driving = true;
            this.driveStart = s.getTMs();
            this.meters = 0.0d;
            this.engagedMs = 0L;
            this.takeovers = 0;
            this.alerts = 0;
            this.hardBrakes = 0;
            this.lastAlert = "";
            this.startLat = s.getLat();
            this.startLon = s.getLon();
            this.startRoad = s.getRoad();
        }
        if (p != null && this.driving) {
            long dt = RangesKt.coerceIn(s.getTMs() - p.getTMs(), 0L, 2000L);
            this.meters += (s.getVEgo() * dt) / 1000.0d;
            if (s.getEnabled() && p.getEnabled()) {
                this.engagedMs += dt;
            }
            if (p.getEnabled() && !s.getEnabled() && p.getVEgo() > 2.5f) {
                this.takeovers++;
                String cause = (s.getBrake() || p.getBrake()) ? "brake" : (s.getGas() || p.getGas()) ? "gas" : (s.getSteering() || p.getSteering()) ? "steering" : !StringsKt.isBlank(s.getAlert1()) ? "alert" : "button";
                ArrayList<JSONObject> arrayList = this.out;
                JSONObject put = new JSONObject().put("k", "takeover").put("t", s.getTMs()).put("cause", cause).put("mph", Math.round(p.getVEgo() * 2.23694d)).put("road", s.getRoad());
                String alert1 = s.getAlert1();
                if (StringsKt.isBlank(alert1)) {
                    alert1 = p.getAlert1();
                }
                JSONObject $this$feed_u24lambda_u241 = put.put("alert", alert1);
                double lat = s.getLat();
                if ((Double.isInfinite(lat) || Double.isNaN(lat)) ? false : true) {
                    double lon = s.getLon();
                    if ((Double.isInfinite(lon) || Double.isNaN(lon)) ? false : true) {
                        if (!(s.getLat() == 0.0d)) {
                            $this$feed_u24lambda_u241.put("lat", s.getLat()).put("lon", s.getLon());
                            arrayList.add($this$feed_u24lambda_u241);
                        }
                    }
                }
                arrayList.add($this$feed_u24lambda_u241);
            }
            if (s.getAEgo() >= -3.0f || s.getVEgo() <= 3.0f) {
                if (s.getAEgo() > -1.5f) {
                    this.brakeHeld = false;
                }
            } else if (!this.brakeHeld) {
                this.hardBrakes++;
                this.brakeHeld = true;
            }
        }
        String a = StringsKt.trim((CharSequence) s.getAlert1()).toString();
        if (this.driving) {
            if ((a.length() > 0) && !Intrinsics.areEqual(a, this.lastAlert)) {
                this.alerts++;
                ArrayList<JSONObject> arrayList2 = this.out;
                JSONObject $this$feed_u24lambda_u242 = new JSONObject().put("k", "alert").put("t", s.getTMs()).put("text", a).put("why", StringsKt.trim((CharSequence) s.getAlert2()).toString()).put("status", s.getAlertStatus()).put("mph", Math.round(s.getVEgo() * 2.23694d)).put("road", s.getRoad());
                double lat2 = s.getLat();
                if ((Double.isInfinite(lat2) || Double.isNaN(lat2)) ? false : true) {
                    if (!(s.getLat() == 0.0d)) {
                        $this$feed_u24lambda_u242.put("lat", s.getLat()).put("lon", s.getLon());
                    }
                }
                arrayList2.add($this$feed_u24lambda_u242);
            }
        }
        this.lastAlert = a;
        if (s.getStarted() || !this.driving) {
            return;
        }
        this.driving = false;
        if (this.meters > 200.0d || s.getTMs() - this.driveStart > 120000) {
            ArrayList<JSONObject> arrayList3 = this.out;
            JSONObject $this$feed_u24lambda_u243 = new JSONObject().put("k", "drive").put("t", this.driveStart).put("end", s.getTMs()).put("meters", Math.round(this.meters)).put("engagedPct", s.getTMs() > this.driveStart ? Math.round((this.engagedMs * 100.0d) / (s.getTMs() - this.driveStart)) : 0L).put("takeovers", this.takeovers).put("alerts", this.alerts).put("hardBrakes", this.hardBrakes).put("from", this.startRoad).put("to", s.getRoad());
            double d = this.startLat;
            if ((Double.isInfinite(d) || Double.isNaN(d)) ? false : true) {
                if (!(this.startLat == 0.0d)) {
                    $this$feed_u24lambda_u243.put("lat", this.startLat).put("lon", this.startLon);
                }
            }
            arrayList3.add($this$feed_u24lambda_u243);
        }
    }

    /* compiled from: DriveJournal.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* compiled from: DriveJournal.kt */
        public static final class Spot {
            private final Map<String, Integer> causes;
            private final int count;
            private final long lastT;
            private final double lat;
            private final double lon;
            private final String road;

            public Spot(double lat, double lon, int count, String road, long lastT, Map<String, Integer> causes) {
                Intrinsics.checkNotNullParameter(road, "road");
                Intrinsics.checkNotNullParameter(causes, "causes");
                this.lat = lat;
                this.lon = lon;
                this.count = count;
                this.road = road;
                this.lastT = lastT;
                this.causes = causes;
            }

            public final Map<String, Integer> getCauses() {
                return this.causes;
            }

            public final int getCount() {
                return this.count;
            }

            public final long getLastT() {
                return this.lastT;
            }

            public final double getLat() {
                return this.lat;
            }

            public final double getLon() {
                return this.lon;
            }

            public final String getRoad() {
                return this.road;
            }
        }

        public static /* synthetic */ List spots$default(Companion companion, List list, double d, int i, Object obj) {
            if ((i & 2) != 0) {
                d = 150.0d;
            }
            return companion.spots(list, d);
        }

        public final List<Spot> spots(List<? extends JSONObject> takeovers, double radiusM) {
            Object minElem$iv;
            Iterator<? extends JSONObject> it;
            Intrinsics.checkNotNullParameter(takeovers, "takeovers");
            ArrayList acc = new ArrayList();
            Iterator<? extends JSONObject> it2 = takeovers.iterator();
            while (it2.hasNext()) {
                JSONObject o = it2.next();
                if (o.has("lat")) {
                    double lat = o.getDouble("lat");
                    double lon = o.getDouble("lon");
                    ArrayList $this$minByOrNull$iv = acc;
                    Iterator iterator$iv = $this$minByOrNull$iv.iterator();
                    JournalLogic$Companion$spots$Acc journalLogic$Companion$spots$Acc = null;
                    if (iterator$iv.hasNext()) {
                        minElem$iv = iterator$iv.next();
                        if (iterator$iv.hasNext()) {
                            JournalLogic$Companion$spots$Acc it3 = (JournalLogic$Companion$spots$Acc) minElem$iv;
                            double minValue$iv = JournalLogic.Companion.meters(it3.getLat(), it3.getLon(), lat, lon);
                            while (true) {
                                Object e$iv = iterator$iv.next();
                                JournalLogic$Companion$spots$Acc it4 = (JournalLogic$Companion$spots$Acc) e$iv;
                                it = it2;
                                double minValue$iv2 = minValue$iv;
                                double v$iv = JournalLogic.Companion.meters(it4.getLat(), it4.getLon(), lat, lon);
                                if (Double.compare(minValue$iv2, v$iv) > 0) {
                                    minValue$iv = v$iv;
                                    minElem$iv = e$iv;
                                } else {
                                    minValue$iv = minValue$iv2;
                                }
                                if (!iterator$iv.hasNext()) {
                                    break;
                                }
                                it2 = it;
                            }
                        } else {
                            it = it2;
                        }
                    } else {
                        it = it2;
                        minElem$iv = null;
                    }
                    JournalLogic$Companion$spots$Acc it5 = (JournalLogic$Companion$spots$Acc) minElem$iv;
                    if (it5 != null) {
                        if (JournalLogic.Companion.meters(it5.getLat(), it5.getLon(), lat, lon) < radiusM) {
                            journalLogic$Companion$spots$Acc = it5;
                        }
                    }
                    JournalLogic$Companion$spots$Acc hit = journalLogic$Companion$spots$Acc;
                    String cause = o.optString("cause", "?");
                    if (hit != null) {
                        hit.setLat(((hit.getLat() * hit.getN()) + lat) / (hit.getN() + 1));
                        hit.setLon(((hit.getLon() * hit.getN()) + lon) / (hit.getN() + 1));
                        hit.setN(hit.getN() + 1);
                        if (o.optLong("t") > hit.getLastT()) {
                            hit.setLastT(o.optLong("t"));
                            String optString = o.optString("road");
                            Intrinsics.checkNotNullExpressionValue(optString, "optString(...)");
                            if (!StringsKt.isBlank(optString)) {
                                hit.setRoad(o.optString("road"));
                            }
                        }
                        HashMap<String, Integer> causes = hit.getCauses();
                        Integer num = hit.getCauses().get(cause);
                        causes.put(cause, Integer.valueOf((num != null ? num.intValue() : 0) + 1));
                        it2 = it;
                    } else {
                        String optString2 = o.optString("road");
                        Intrinsics.checkNotNullExpressionValue(optString2, "optString(...)");
                        acc.add(new JournalLogic$Companion$spots$Acc(lat, lon, 1, optString2, o.optLong("t"), MapsKt.hashMapOf(TuplesKt.to(cause, 1))));
                        it2 = it;
                    }
                }
            }
            final Comparator comparator = new Comparator() { // from class: com.starpilot.starview.JournalLogic$Companion$spots$$inlined$compareByDescending$1
                /* JADX WARN: Multi-variable type inference failed */
                @Override // java.util.Comparator
                public final int compare(Object t, Object t2) {
                    JournalLogic$Companion$spots$Acc it6 = (JournalLogic$Companion$spots$Acc) t2;
                    Integer valueOf = Integer.valueOf(it6.getN());
                    JournalLogic$Companion$spots$Acc it7 = (JournalLogic$Companion$spots$Acc) t;
                    return ComparisonsKt.compareValues(valueOf, Integer.valueOf(it7.getN()));
                }
            };
            Iterable $this$map$iv = CollectionsKt.sortedWith(acc, new Comparator() { // from class: com.starpilot.starview.JournalLogic$Companion$spots$$inlined$thenByDescending$1
                /* JADX WARN: Multi-variable type inference failed */
                @Override // java.util.Comparator
                public final int compare(Object t, Object t2) {
                    int previousCompare = comparator.compare(t, t2);
                    if (previousCompare != 0) {
                        return previousCompare;
                    }
                    JournalLogic$Companion$spots$Acc it6 = (JournalLogic$Companion$spots$Acc) t2;
                    Long valueOf = Long.valueOf(it6.getLastT());
                    JournalLogic$Companion$spots$Acc it7 = (JournalLogic$Companion$spots$Acc) t;
                    return ComparisonsKt.compareValues(valueOf, Long.valueOf(it7.getLastT()));
                }
            });
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            for (Object item$iv$iv : $this$map$iv) {
                JournalLogic$Companion$spots$Acc it6 = (JournalLogic$Companion$spots$Acc) item$iv$iv;
                destination$iv$iv.add(new Spot(it6.getLat(), it6.getLon(), it6.getN(), it6.getRoad(), it6.getLastT(), it6.getCauses()));
                acc = acc;
            }
            return (List) destination$iv$iv;
        }

        public final double meters(double lat1, double lon1, double lat2, double lon2) {
            double dLat = Math.toRadians(lat2 - lat1);
            double dLon = Math.toRadians(lon2 - lon1);
            double d = 2;
            double a = (Math.sin(dLat / d) * Math.sin(dLat / d)) + (Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / d) * Math.sin(dLon / d));
            return d * 6371000.0d * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        }

        public static /* synthetic */ List weekly$default(Companion companion, List list, long j, int i, int i2, Object obj) {
            if ((i2 & 4) != 0) {
                i = 8;
            }
            return companion.weekly(list, j, i);
        }

        public final List<Pair<Double, Integer>> weekly(List<? extends JSONObject> drives, long nowMs, int weeks) {
            Iterable drives2 = drives;
            Intrinsics.checkNotNullParameter(drives2, "drives");
            long wk = 604800000;
            Iterable $this$map$iv = RangesKt.downTo(weeks - 1, 0);
            int $i$f$map = 0;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            Iterator<Integer> it = $this$map$iv.iterator();
            while (it.hasNext()) {
                int item$iv$iv = ((IntIterator) it).nextInt();
                long from = nowMs - ((item$iv$iv + 1) * wk);
                int $i$f$map2 = $i$f$map;
                long to = nowMs - (item$iv$iv * wk);
                Iterable $this$filter$iv = drives2;
                Collection destination$iv$iv2 = new ArrayList();
                for (Object element$iv$iv : $this$filter$iv) {
                    long wk2 = wk;
                    JSONObject it2 = (JSONObject) element$iv$iv;
                    long optLong = it2.optLong("t");
                    if (from <= optLong && optLong < to) {
                        destination$iv$iv2.add(element$iv$iv);
                    }
                    wk = wk2;
                }
                long wk3 = wk;
                List<JSONObject> ds = (List) destination$iv$iv2;
                Iterator it3 = ds.iterator();
                Iterable $this$map$iv2 = $this$map$iv;
                long j = 0;
                while (it3.hasNext()) {
                    JSONObject it4 = (JSONObject) it3.next();
                    j += it4.optLong("meters");
                    it3 = it3;
                    to = to;
                }
                double miles = j / 1609.344d;
                int i = 0;
                for (JSONObject it5 : ds) {
                    i += it5.optInt("takeovers");
                    ds = ds;
                }
                int tk = i;
                destination$iv$iv.add(TuplesKt.to(Double.valueOf(miles >= 1.0d ? (tk * 100.0d) / miles : Double.NaN), Integer.valueOf((int) Math.round(miles))));
                drives2 = drives;
                $this$map$iv = $this$map$iv2;
                $i$f$map = $i$f$map2;
                wk = wk3;
            }
            return (List) destination$iv$iv;
        }
    }
}
