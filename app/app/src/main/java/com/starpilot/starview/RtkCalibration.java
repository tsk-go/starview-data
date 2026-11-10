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

        /* VF: from Vineflower (jadx: Type inference failed) */
        public final RtkCalibration parse(JSONObject var1) {
            if (var1 == null) {
                return null;
            } else {
                JSONArray var14 = var1.optJSONArray("events");
                JSONObject var13 = var1.optJSONObject("drive");
                JSONObject var12 = var13;
                if (var13 == null) {
                    var12 = new JSONObject();
                }

                List var20;
                if (var14 == null) {
                    var20 = CollectionsKt.emptyList();
                } else {
                    Iterable var21 = RangesKt.until(0, var14.length());
                    var var16 = new ArrayList();
                    Iterator var17 = var21.iterator();

                    while (var17.hasNext()) {
                        JSONObject var18 = var14.optJSONObject(((IntIterator)var17).nextInt());
                        RtkCalibration.Event var22;
                        if (var18 != null) {
                            double var2 = var18.optDouble("agoS", 0.0);
                            String var15 = var18.optString("k");
                            Intrinsics.checkNotNullExpressionValue(var15, "optString(...)");
                            var22 = new RtkCalibration.Event(var2, var15, parse$s(var18, "from"), parse$s(var18, "to"));
                        } else {
                            var22 = null;
                        }

                        if (var22 != null) {
                            var16.add(var22);
                        }
                    }

                    var20 = var16;
                }

                int var8 = var12.optInt("sinceAgoS", 0);
                int var6 = var12.optInt("left", 0);
                int var9 = var12.optInt("right", 0);
                int var11 = var12.optInt("leftFast", 0);
                int var4 = var12.optInt("rightFast", 0);
                int var10 = var12.optInt("fastS", 0);
                int var7 = var12.optInt("longestStillS", 0);
                int var5 = var12.optInt("stillNowS", 0);
                Integer var19;
                if (var1.has("speedBadPct")) {
                    var19 = var1.optInt("speedBadPct");
                } else {
                    var19 = null;
                }

                return new RtkCalibration(var20, var8, var6, var9, var11, var4, var10, var7, var5, var19);
            }
        }

        private static final String parse$s(JSONObject j, String k) {
            if (!j.has(k) || j.isNull(k)) {
                return null;
            }
            return j.optString(k);
        }
    }
}
