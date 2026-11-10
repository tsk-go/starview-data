package com.starpilot.starview;

import kotlin.text.StringsKt;

/* compiled from: Telemetry.kt */
public final class GnssSat {
    private final int azimuth;
    private final int cno;
    private final int elevation;
    private final int flags;
    private final int gnssId;
    private final int svId;

    public GnssSat(int gnssId, int svId, int cno, int elevation, int azimuth, int flags) {
        this.gnssId = gnssId;
        this.svId = svId;
        this.cno = cno;
        this.elevation = elevation;
        this.azimuth = azimuth;
        this.flags = flags;
    }

    public final int getAzimuth() {
        return this.azimuth;
    }

    public final int getCno() {
        return this.cno;
    }

    public final int getElevation() {
        return this.elevation;
    }

    public final int getFlags() {
        return this.flags;
    }

    public final int getGnssId() {
        return this.gnssId;
    }

    public final int getSvId() {
        return this.svId;
    }

    public final boolean getUsed() {
        return (this.flags & 8) != 0;
    }

    public final String getSystem() {
        switch (this.gnssId) {
            case 0:
                return "G";
            case 1:
                return "S";
            case 2:
                return "E";
            case 3:
                return "C";
            case 4:
                return "I";
            case 5:
                return "J";
            case 6:
                return "R";
            default:
                return "?";
        }
    }

    public final String getLabel() {
        return getSystem() + StringsKt.padStart(String.valueOf(this.svId), 2, '0');
    }
}
