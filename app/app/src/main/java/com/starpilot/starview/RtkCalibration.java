package com.starpilot.starview;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: RtkCore.kt */
public final /* data */ class RtkCalibration {
    public static final Companion Companion = new Companion(null);
    private final List<Event> events;
    private final int fastS;
    private final int left;
    private final int leftFast;
    private final int longestStillS;
    private final int right;
    private final int rightFast;
    private final int sinceAgoS;
    private final Integer speedBadPct;
    private final int stillNowS;

    public final List<Event> component1() {
        return this.events;
    }

    /* renamed from: component10, reason: from getter */
    public final Integer getSpeedBadPct() {
        return this.speedBadPct;
    }

    /* renamed from: component2, reason: from getter */
    public final int getSinceAgoS() {
        return this.sinceAgoS;
    }

    /* renamed from: component3, reason: from getter */
    public final int getLeft() {
        return this.left;
    }

    /* renamed from: component4, reason: from getter */
    public final int getRight() {
        return this.right;
    }

    /* renamed from: component5, reason: from getter */
    public final int getLeftFast() {
        return this.leftFast;
    }

    /* renamed from: component6, reason: from getter */
    public final int getRightFast() {
        return this.rightFast;
    }

    /* renamed from: component7, reason: from getter */
    public final int getFastS() {
        return this.fastS;
    }

    /* renamed from: component8, reason: from getter */
    public final int getLongestStillS() {
        return this.longestStillS;
    }

    /* renamed from: component9, reason: from getter */
    public final int getStillNowS() {
        return this.stillNowS;
    }

    public final RtkCalibration copy(List<Event> events, int sinceAgoS, int left, int right, int leftFast, int rightFast, int fastS, int longestStillS, int stillNowS, Integer speedBadPct) {
        Intrinsics.checkNotNullParameter(events, "events");
        return new RtkCalibration(events, sinceAgoS, left, right, leftFast, rightFast, fastS, longestStillS, stillNowS, speedBadPct);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof RtkCalibration)) {
            return false;
        }
        RtkCalibration rtkCalibration = (RtkCalibration) other;
        return Intrinsics.areEqual(this.events, rtkCalibration.events) && this.sinceAgoS == rtkCalibration.sinceAgoS && this.left == rtkCalibration.left && this.right == rtkCalibration.right && this.leftFast == rtkCalibration.leftFast && this.rightFast == rtkCalibration.rightFast && this.fastS == rtkCalibration.fastS && this.longestStillS == rtkCalibration.longestStillS && this.stillNowS == rtkCalibration.stillNowS && Intrinsics.areEqual(this.speedBadPct, rtkCalibration.speedBadPct);
    }

    public int hashCode() {
        return (((((((((((((((((this.events.hashCode() * 31) + Integer.hashCode(this.sinceAgoS)) * 31) + Integer.hashCode(this.left)) * 31) + Integer.hashCode(this.right)) * 31) + Integer.hashCode(this.leftFast)) * 31) + Integer.hashCode(this.rightFast)) * 31) + Integer.hashCode(this.fastS)) * 31) + Integer.hashCode(this.longestStillS)) * 31) + Integer.hashCode(this.stillNowS)) * 31) + (this.speedBadPct == null ? 0 : this.speedBadPct.hashCode());
    }

    public String toString() {
        return "RtkCalibration(events=" + this.events + ", sinceAgoS=" + this.sinceAgoS + ", left=" + this.left + ", right=" + this.right + ", leftFast=" + this.leftFast + ", rightFast=" + this.rightFast + ", fastS=" + this.fastS + ", longestStillS=" + this.longestStillS + ", stillNowS=" + this.stillNowS + ", speedBadPct=" + this.speedBadPct + ")";
    }

    public RtkCalibration(List<Event> events, int sinceAgoS, int left, int right, int leftFast, int rightFast, int fastS, int longestStillS, int stillNowS, Integer speedBadPct) {
        Intrinsics.checkNotNullParameter(events, "events");
        this.events = events;
        this.sinceAgoS = sinceAgoS;
        this.left = left;
        this.right = right;
        this.leftFast = leftFast;
        this.rightFast = rightFast;
        this.fastS = fastS;
        this.longestStillS = longestStillS;
        this.stillNowS = stillNowS;
        this.speedBadPct = speedBadPct;
    }

    public final List<Event> getEvents() {
        return this.events;
    }

    public final int getLeft() {
        return this.left;
    }

    public final int getLeftFast() {
        return this.leftFast;
    }

    public final int getRight() {
        return this.right;
    }

    public final int getRightFast() {
        return this.rightFast;
    }

    public final int getSinceAgoS() {
        return this.sinceAgoS;
    }

    public final int getFastS() {
        return this.fastS;
    }

    public final int getLongestStillS() {
        return this.longestStillS;
    }

    public final Integer getSpeedBadPct() {
        return this.speedBadPct;
    }

    public final int getStillNowS() {
        return this.stillNowS;
    }

    /* compiled from: RtkCore.kt */
    public static final /* data */ class Event {
        private final double agoS;
        private final String from;
        private final String key;
        private final String to;

        public static /* synthetic */ Event copy$default(Event event, double d, String str, String str2, String str3, int i, Object obj) {
            if ((i & 1) != 0) {
                d = event.agoS;
            }
            double d2 = d;
            if ((i & 2) != 0) {
                str = event.key;
            }
            String str4 = str;
            if ((i & 4) != 0) {
                str2 = event.from;
            }
            String str5 = str2;
            if ((i & 8) != 0) {
                str3 = event.to;
            }
            return event.copy(d2, str4, str5, str3);
        }

        /* renamed from: component1, reason: from getter */
        public final double getAgoS() {
            return this.agoS;
        }

        /* renamed from: component2, reason: from getter */
        public final String getKey() {
            return this.key;
        }

        /* renamed from: component3, reason: from getter */
        public final String getFrom() {
            return this.from;
        }

        /* renamed from: component4, reason: from getter */
        public final String getTo() {
            return this.to;
        }

        public final Event copy(double agoS, String key, String from, String to) {
            Intrinsics.checkNotNullParameter(key, "key");
            return new Event(agoS, key, from, to);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Event)) {
                return false;
            }
            Event event = (Event) other;
            return Double.compare(this.agoS, event.agoS) == 0 && Intrinsics.areEqual(this.key, event.key) && Intrinsics.areEqual(this.from, event.from) && Intrinsics.areEqual(this.to, event.to);
        }

        public int hashCode() {
            return (((((Double.hashCode(this.agoS) * 31) + this.key.hashCode()) * 31) + (this.from == null ? 0 : this.from.hashCode())) * 31) + (this.to != null ? this.to.hashCode() : 0);
        }

        public String toString() {
            return "Event(agoS=" + this.agoS + ", key=" + this.key + ", from=" + this.from + ", to=" + this.to + ")";
        }

        public Event(double agoS, String key, String from, String to) {
            Intrinsics.checkNotNullParameter(key, "key");
            this.agoS = agoS;
            this.key = key;
            this.from = from;
            this.to = to;
        }

        public final double getAgoS() {
            return this.agoS;
        }

        public final String getFrom() {
            return this.from;
        }

        public final String getKey() {
            return this.key;
        }

        public final String getTo() {
            return this.to;
        }
    }

    /* compiled from: RtkCore.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r5v8, types: [java.util.List] */
        public final RtkCalibration parse(JSONObject o) {
            ArrayList arrayList;
            JSONArray ev;
            Iterable $this$mapNotNull$iv;
            Event event;
            if (o == null) {
                return null;
            }
            JSONArray ev2 = o.optJSONArray("events");
            JSONObject d = o.optJSONObject("drive");
            if (d == null) {
                d = new JSONObject();
            }
            if (ev2 == null) {
                arrayList = CollectionsKt.emptyList();
            } else {
                Iterable $this$mapNotNull$iv2 = RangesKt.until(0, ev2.length());
                Collection destination$iv$iv = new ArrayList();
                Iterator<Integer> it = $this$mapNotNull$iv2.iterator();
                while (it.hasNext()) {
                    int element$iv$iv$iv = ((IntIterator) it).nextInt();
                    JSONObject it2 = ev2.optJSONObject(element$iv$iv$iv);
                    if (it2 != null) {
                        ev = ev2;
                        $this$mapNotNull$iv = $this$mapNotNull$iv2;
                        double optDouble = it2.optDouble("agoS", 0.0d);
                        String optString = it2.optString("k");
                        Intrinsics.checkNotNullExpressionValue(optString, "optString(...)");
                        event = new Event(optDouble, optString, parse$s(it2, "from"), parse$s(it2, "to"));
                    } else {
                        ev = ev2;
                        $this$mapNotNull$iv = $this$mapNotNull$iv2;
                        event = null;
                    }
                    if (event != null) {
                        destination$iv$iv.add(event);
                    }
                    ev2 = ev;
                    $this$mapNotNull$iv2 = $this$mapNotNull$iv;
                }
                arrayList = (List) destination$iv$iv;
            }
            return new RtkCalibration(arrayList, d.optInt("sinceAgoS", 0), d.optInt("left", 0), d.optInt("right", 0), d.optInt("leftFast", 0), d.optInt("rightFast", 0), d.optInt("fastS", 0), d.optInt("longestStillS", 0), d.optInt("stillNowS", 0), o.has("speedBadPct") ? Integer.valueOf(o.optInt("speedBadPct")) : null);
        }

        private static final String parse$s(JSONObject j, String k) {
            if (!j.has(k) || j.isNull(k)) {
                return null;
            }
            return j.optString(k);
        }
    }
}
