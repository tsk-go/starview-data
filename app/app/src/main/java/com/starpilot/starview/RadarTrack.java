package com.starpilot.starview;


/* compiled from: Telemetry.kt */
public final class RadarTrack {
    private final float dRel;
    private final long id;
    private final boolean measured;
    private final float vRel;
    private final float yRel;

    public RadarTrack(long id, float dRel, float yRel, float vRel, boolean measured) {
        this.id = id;
        this.dRel = dRel;
        this.yRel = yRel;
        this.vRel = vRel;
        this.measured = measured;
    }

    public final float getDRel() {
        return this.dRel;
    }

    public final long getId() {
        return this.id;
    }

    public final boolean getMeasured() {
        return this.measured;
    }

    public final float getVRel() {
        return this.vRel;
    }

    public final float getYRel() {
        return this.yRel;
    }
}
