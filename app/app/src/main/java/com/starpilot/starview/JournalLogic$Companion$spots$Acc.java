package com.starpilot.starview;

import java.util.HashMap;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: DriveJournal.kt */
public final class JournalLogic$Companion$spots$Acc {
    private final HashMap<String, Integer> causes;
    private long lastT;
    private double lat;
    private double lon;
    private int n;
    private String road;

    public JournalLogic$Companion$spots$Acc(double lat, double lon, int n, String road, long lastT, HashMap<String, Integer> causes) {
        Intrinsics.checkNotNullParameter(road, "road");
        Intrinsics.checkNotNullParameter(causes, "causes");
        this.lat = lat;
        this.lon = lon;
        this.n = n;
        this.road = road;
        this.lastT = lastT;
        this.causes = causes;
    }

    public final HashMap<String, Integer> getCauses() {
        return this.causes;
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

    public final int getN() {
        return this.n;
    }

    public final String getRoad() {
        return this.road;
    }

    public final void setLastT(long j) {
        this.lastT = j;
    }

    public final void setLat(double d) {
        this.lat = d;
    }

    public final void setLon(double d) {
        this.lon = d;
    }

    public final void setN(int i) {
        this.n = i;
    }

    public final void setRoad(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.road = str;
    }
}
