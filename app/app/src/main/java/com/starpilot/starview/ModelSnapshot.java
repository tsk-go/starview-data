package com.starpilot.starview;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: Telemetry.kt */
public final class ModelSnapshot {
    private final float[] accelX;
    private final float[] desirePred;
    private final float[] edgeStd;
    private final float[][] edgeX;
    private final float[][] edgeY;
    private final float[][] edgeZ;
    private final long frameId;
    private final int laneChangeDir;
    private final boolean laneChangeFinishing;
    private final boolean laneChangeOff;
    private final float laneChangeProb;
    private final boolean laneChanging;
    private final float[] laneProb;
    private final float[] laneStd;
    private final float[][] laneX;
    private final float[][] laneY;
    private final float[][] laneZ;
    private final float modelDesiredCurvature;
    private final float[] posX;
    private final float[] posY;
    private final float[] posYStd;
    private final float[] posZ;

    public ModelSnapshot(long frameId, float[] posX, float[] posY, float[] posZ, float[] accelX, float[][] laneX, float[][] laneY, float[][] laneZ, float[] laneProb, float[][] edgeX, float[][] edgeY, float[][] edgeZ, float[] edgeStd, float[] laneStd, float[] posYStd, boolean laneChangeOff, float modelDesiredCurvature, boolean laneChanging, int laneChangeDir, boolean laneChangeFinishing, float[] desirePred, float laneChangeProb) {
        Intrinsics.checkNotNullParameter(posX, "posX");
        Intrinsics.checkNotNullParameter(posY, "posY");
        Intrinsics.checkNotNullParameter(posZ, "posZ");
        Intrinsics.checkNotNullParameter(accelX, "accelX");
        Intrinsics.checkNotNullParameter(laneX, "laneX");
        Intrinsics.checkNotNullParameter(laneY, "laneY");
        Intrinsics.checkNotNullParameter(laneZ, "laneZ");
        Intrinsics.checkNotNullParameter(laneProb, "laneProb");
        Intrinsics.checkNotNullParameter(edgeX, "edgeX");
        Intrinsics.checkNotNullParameter(edgeY, "edgeY");
        Intrinsics.checkNotNullParameter(edgeZ, "edgeZ");
        Intrinsics.checkNotNullParameter(edgeStd, "edgeStd");
        Intrinsics.checkNotNullParameter(laneStd, "laneStd");
        Intrinsics.checkNotNullParameter(posYStd, "posYStd");
        Intrinsics.checkNotNullParameter(desirePred, "desirePred");
        this.frameId = frameId;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.accelX = accelX;
        this.laneX = laneX;
        this.laneY = laneY;
        this.laneZ = laneZ;
        this.laneProb = laneProb;
        this.edgeX = edgeX;
        this.edgeY = edgeY;
        this.edgeZ = edgeZ;
        this.edgeStd = edgeStd;
        this.laneStd = laneStd;
        this.posYStd = posYStd;
        this.laneChangeOff = laneChangeOff;
        this.modelDesiredCurvature = modelDesiredCurvature;
        this.laneChanging = laneChanging;
        this.laneChangeDir = laneChangeDir;
        this.laneChangeFinishing = laneChangeFinishing;
        this.desirePred = desirePred;
        this.laneChangeProb = laneChangeProb;
    }

    public /* synthetic */ ModelSnapshot(long j, float[] fArr, float[] fArr2, float[] fArr3, float[] fArr4, float[][] fArr5, float[][] fArr6, float[][] fArr7, float[] fArr8, float[][] fArr9, float[][] fArr10, float[][] fArr11, float[] fArr12, float[] fArr13, float[] fArr14, boolean z, float f, boolean z2, int i, boolean z3, float[] fArr15, float f2, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, fArr, fArr2, fArr3, fArr4, fArr5, fArr6, fArr7, fArr8, fArr9, fArr10, fArr11, fArr12, (i2 & 8192) != 0 ? new float[0] : fArr13, (i2 & 16384) != 0 ? new float[0] : fArr14, (32768 & i2) != 0 ? true : z, (65536 & i2) != 0 ? 0.0f : f, (131072 & i2) != 0 ? false : z2, (262144 & i2) != 0 ? 0 : i, (524288 & i2) != 0 ? false : z3, (1048576 & i2) != 0 ? new float[0] : fArr15, (i2 & 2097152) != 0 ? Float.NaN : f2);
    }

    public final long getFrameId() {
        return this.frameId;
    }

    public final float[] getPosX() {
        return this.posX;
    }

    public final float[] getPosY() {
        return this.posY;
    }

    public final float[] getPosZ() {
        return this.posZ;
    }

    public final float[] getAccelX() {
        return this.accelX;
    }

    public final float[] getLaneProb() {
        return this.laneProb;
    }

    public final float[][] getLaneX() {
        return this.laneX;
    }

    public final float[][] getLaneY() {
        return this.laneY;
    }

    public final float[][] getLaneZ() {
        return this.laneZ;
    }

    public final float[] getEdgeStd() {
        return this.edgeStd;
    }

    public final float[][] getEdgeX() {
        return this.edgeX;
    }

    public final float[][] getEdgeY() {
        return this.edgeY;
    }

    public final float[][] getEdgeZ() {
        return this.edgeZ;
    }

    public final float[] getLaneStd() {
        return this.laneStd;
    }

    public final float[] getPosYStd() {
        return this.posYStd;
    }

    public final boolean getLaneChangeOff() {
        return this.laneChangeOff;
    }

    public final float getModelDesiredCurvature() {
        return this.modelDesiredCurvature;
    }

    public final int getLaneChangeDir() {
        return this.laneChangeDir;
    }

    public final boolean getLaneChanging() {
        return this.laneChanging;
    }

    public final boolean getLaneChangeFinishing() {
        return this.laneChangeFinishing;
    }

    public final float[] getDesirePred() {
        return this.desirePred;
    }

    public final float getLaneChangeProb() {
        return this.laneChangeProb;
    }
}
