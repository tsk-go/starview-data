package com.k2fsa.sherpa.onnx;

import java.util.Arrays;
import java.util.Map;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: Tts.kt */
public final /* data */ class GenerationConfig {
    private Map<String, String> extra;
    private int numSteps;
    private float[] referenceAudio;
    private int referenceSampleRate;
    private String referenceText;
    private int sid;
    private float silenceScale;
    private float speed;

    public GenerationConfig() {
        this(0.0f, 0.0f, 0, null, 0, null, 0, null, 255, null);
    }







    public final Map<String, String> component8() {
        return this.extra;
    }

    public final GenerationConfig copy(float silenceScale, float speed, int sid, float[] referenceAudio, int referenceSampleRate, String referenceText, int numSteps, Map<String, String> extra) {
        return new GenerationConfig(silenceScale, speed, sid, referenceAudio, referenceSampleRate, referenceText, numSteps, extra);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof GenerationConfig)) {
            return false;
        }
        GenerationConfig generationConfig = (GenerationConfig) other;
        return Float.compare(this.silenceScale, generationConfig.silenceScale) == 0 && Float.compare(this.speed, generationConfig.speed) == 0 && this.sid == generationConfig.sid && Intrinsics.areEqual(this.referenceAudio, generationConfig.referenceAudio) && this.referenceSampleRate == generationConfig.referenceSampleRate && Intrinsics.areEqual(this.referenceText, generationConfig.referenceText) && this.numSteps == generationConfig.numSteps && Intrinsics.areEqual(this.extra, generationConfig.extra);
    }

    public int hashCode() {
        return (((((((((((((Float.hashCode(this.silenceScale) * 31) + Float.hashCode(this.speed)) * 31) + Integer.hashCode(this.sid)) * 31) + (this.referenceAudio == null ? 0 : Arrays.hashCode(this.referenceAudio))) * 31) + Integer.hashCode(this.referenceSampleRate)) * 31) + (this.referenceText == null ? 0 : this.referenceText.hashCode())) * 31) + Integer.hashCode(this.numSteps)) * 31) + (this.extra != null ? this.extra.hashCode() : 0);
    }

    public String toString() {
        return "GenerationConfig(silenceScale=" + this.silenceScale + ", speed=" + this.speed + ", sid=" + this.sid + ", referenceAudio=" + Arrays.toString(this.referenceAudio) + ", referenceSampleRate=" + this.referenceSampleRate + ", referenceText=" + this.referenceText + ", numSteps=" + this.numSteps + ", extra=" + this.extra + ")";
    }

    public GenerationConfig(float silenceScale, float speed, int sid, float[] referenceAudio, int referenceSampleRate, String referenceText, int numSteps, Map<String, String> map) {
        this.silenceScale = silenceScale;
        this.speed = speed;
        this.sid = sid;
        this.referenceAudio = referenceAudio;
        this.referenceSampleRate = referenceSampleRate;
        this.referenceText = referenceText;
        this.numSteps = numSteps;
        this.extra = map;
    }

    public /* synthetic */ GenerationConfig(float f, float f2, int i, float[] fArr, int i2, String str, int i3, Map map, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        this((i4 & 1) != 0 ? 0.2f : f, (i4 & 2) != 0 ? 1.0f : f2, (i4 & 4) != 0 ? 0 : i, (i4 & 8) != 0 ? null : fArr, (i4 & 16) == 0 ? i2 : 0, (i4 & 32) != 0 ? null : str, (i4 & 64) != 0 ? 5 : i3, (i4 & 128) == 0 ? map : null);
    }

    public final float getSilenceScale() {
        return this.silenceScale;
    }

    public final void setSilenceScale(float f) {
        this.silenceScale = f;
    }

    public final float getSpeed() {
        return this.speed;
    }

    public final void setSpeed(float f) {
        this.speed = f;
    }

    public final int getSid() {
        return this.sid;
    }

    public final void setSid(int i) {
        this.sid = i;
    }

    public final float[] getReferenceAudio() {
        return this.referenceAudio;
    }

    public final void setReferenceAudio(float[] fArr) {
        this.referenceAudio = fArr;
    }

    public final int getReferenceSampleRate() {
        return this.referenceSampleRate;
    }

    public final void setReferenceSampleRate(int i) {
        this.referenceSampleRate = i;
    }

    public final String getReferenceText() {
        return this.referenceText;
    }

    public final void setReferenceText(String str) {
        this.referenceText = str;
    }

    public final int getNumSteps() {
        return this.numSteps;
    }

    public final void setNumSteps(int i) {
        this.numSteps = i;
    }

    public final Map<String, String> getExtra() {
        return this.extra;
    }

    public final void setExtra(Map<String, String> map) {
        this.extra = map;
    }
}
