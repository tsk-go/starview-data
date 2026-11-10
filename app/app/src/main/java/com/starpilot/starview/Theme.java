package com.starpilot.starview;


/* compiled from: Theme.kt */
public final class Theme {
    public static final Theme INSTANCE = new Theme();

    private Theme() {
    }

    public final double sunElevationDeg(double latDeg, double lonDeg, long epochMs) {
        double jd = (epochMs / 8.64E7d) + 2440587.5d;
        double d = jd - 2451545.0d;
        double g = Math.toRadians(((0.98560028d * d) + 357.529d) % 360.0d);
        double q = ((0.98564736d * d) + 280.459d) % 360.0d;
        double l = Math.toRadians((((Math.sin(g) * 1.915d) + q) + (Math.sin(2 * g) * 0.02d)) % 360.0d);
        double e = Math.toRadians(23.439d - (3.6E-7d * d));
        double ra = Math.toDegrees(Math.atan2(Math.cos(e) * Math.sin(l), Math.cos(l)));
        double dec = Math.asin(Math.sin(e) * Math.sin(l));
        double gmst = ((24.06570982441908d * d) + 18.697374558d) % 24.0d;
        double lst = ((15.0d * gmst) + lonDeg) % 360.0d;
        double ha = Math.toRadians(((((lst - ra) % 360.0d) + 540.0d) % 360.0d) - 180.0d);
        double lat = Math.toRadians(latDeg);
        double el = Math.asin((Math.sin(lat) * Math.sin(dec)) + (Math.cos(lat) * Math.cos(dec) * Math.cos(ha)));
        return Math.toDegrees(el);
    }

    public static /* synthetic */ boolean isNight$default(Theme theme, double d, double d2, long j, int i, Object obj) {
        if ((i & 4) != 0) {
            j = System.currentTimeMillis();
        }
        return theme.isNight(d, d2, j);
    }

    public final boolean isNight(double latDeg, double lonDeg, long epochMs) {
        return sunElevationDeg(latDeg, lonDeg, epochMs) < -6.0d;
    }
}
