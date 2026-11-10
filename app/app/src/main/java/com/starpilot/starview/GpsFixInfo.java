package com.starpilot.starview;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: Telemetry.kt */
public final class GpsFixInfo {
    private final double altitude;
    private final long atNs;
    private final float bAcc;
    private final float bearing;
    private final float hAcc;
    private final boolean hasFix;
    private final double lat;
    private final double lon;
    private final int sats;
    private final String service;
    private final String source;
    private final float speed;
    private final long unixMs;
    private final float vAcc;

    public GpsFixInfo(String service, String source, boolean hasFix, int sats, double lat, double lon, float hAcc, float speed, float bearing, float bAcc, long atNs, double altitude, float vAcc, long unixMs) {
        Intrinsics.checkNotNullParameter(service, "service");
        Intrinsics.checkNotNullParameter(source, "source");
        this.service = service;
        this.source = source;
        this.hasFix = hasFix;
        this.sats = sats;
        this.lat = lat;
        this.lon = lon;
        this.hAcc = hAcc;
        this.speed = speed;
        this.bearing = bearing;
        this.bAcc = bAcc;
        this.atNs = atNs;
        this.altitude = altitude;
        this.vAcc = vAcc;
        this.unixMs = unixMs;
    }

    public /* synthetic */ GpsFixInfo(String str, String str2, boolean z, int i, double d, double d2, float f, float f2, float f3, float f4, long j, double d3, float f5, long j2, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, z, i, d, d2, f, f2, f3, f4, j, (i2 & 2048) != 0 ? Double.NaN : d3, (i2 & 4096) != 0 ? Float.NaN : f5, (i2 & 8192) != 0 ? 0L : j2);
    }

    public final boolean getHasFix() {
        return this.hasFix;
    }

    public final double getLat() {
        return this.lat;
    }

    public final double getLon() {
        return this.lon;
    }

    public final int getSats() {
        return this.sats;
    }

    public final String getService() {
        return this.service;
    }

    public final String getSource() {
        return this.source;
    }

    public final long getAtNs() {
        return this.atNs;
    }

    public final float getBAcc() {
        return this.bAcc;
    }

    public final float getBearing() {
        return this.bearing;
    }

    public final float getHAcc() {
        return this.hAcc;
    }

    public final float getSpeed() {
        return this.speed;
    }

    public final double getAltitude() {
        return this.altitude;
    }

    public final long getUnixMs() {
        return this.unixMs;
    }

    public final float getVAcc() {
        return this.vAcc;
    }
}
