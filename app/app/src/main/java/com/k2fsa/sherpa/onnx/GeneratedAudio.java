package com.k2fsa.sherpa.onnx;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: Tts.kt */
public final class GeneratedAudio {
    private final int sampleRate;
    private final float[] samples;

    private final native boolean saveImpl(String filename, float[] samples, int sampleRate);

    public GeneratedAudio(float[] samples, int sampleRate) {
        Intrinsics.checkNotNullParameter(samples, "samples");
        this.samples = samples;
        this.sampleRate = sampleRate;
    }

    public final float[] getSamples() {
        return this.samples;
    }

    public final int getSampleRate() {
        return this.sampleRate;
    }

    public final boolean save(String filename) {
        Intrinsics.checkNotNullParameter(filename, "filename");
        return saveImpl(filename, this.samples, this.sampleRate);
    }
}
