package com.starpilot.starview;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: Telemetry.kt */
public final class Lead {
    private final float dRel;
    private final float modelProb;
    private final boolean radar;
    private final boolean status;
    private final float vLead;
    private final float vRel;
    private final float yRel;

    public Lead(boolean status, float dRel, float yRel, float vRel, float vLead, float modelProb, boolean radar) {
        this.status = status;
        this.dRel = dRel;
        this.yRel = yRel;
        this.vRel = vRel;
        this.vLead = vLead;
        this.modelProb = modelProb;
        this.radar = radar;
    }

    public /* synthetic */ Lead(boolean z, float f, float f2, float f3, float f4, float f5, boolean z2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(z, f, f2, f3, f4, f5, (i & 64) != 0 ? false : z2);
    }

    public final float getDRel() {
        return this.dRel;
    }

    public final float getModelProb() {
        return this.modelProb;
    }

    public final boolean getRadar() {
        return this.radar;
    }

    public final boolean getStatus() {
        return this.status;
    }

    public final float getVLead() {
        return this.vLead;
    }

    public final float getVRel() {
        return this.vRel;
    }

    public final float getYRel() {
        return this.yRel;
    }
}
