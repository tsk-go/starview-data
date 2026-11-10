package com.k2fsa.sherpa.onnx;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: Tts.kt */
public final /* data */ class OfflineTtsModelConfig {
    private boolean debug;
    private OfflineTtsKittenModelConfig kitten;
    private OfflineTtsKokoroModelConfig kokoro;
    private OfflineTtsMatchaModelConfig matcha;
    private int numThreads;
    private OfflineTtsPocketModelConfig pocket;
    private String provider;
    private OfflineTtsSupertonicModelConfig supertonic;
    private OfflineTtsVitsModelConfig vits;
    private OfflineTtsZipVoiceModelConfig zipvoice;

    public OfflineTtsModelConfig() {
        this(null, null, null, null, null, null, null, 0, false, null, 1023, null);
    }










    public final OfflineTtsModelConfig copy(OfflineTtsVitsModelConfig vits, OfflineTtsMatchaModelConfig matcha, OfflineTtsKokoroModelConfig kokoro, OfflineTtsZipVoiceModelConfig zipvoice, OfflineTtsKittenModelConfig kitten, OfflineTtsPocketModelConfig pocket, OfflineTtsSupertonicModelConfig supertonic, int numThreads, boolean debug, String provider) {
        Intrinsics.checkNotNullParameter(vits, "vits");
        Intrinsics.checkNotNullParameter(matcha, "matcha");
        Intrinsics.checkNotNullParameter(kokoro, "kokoro");
        Intrinsics.checkNotNullParameter(zipvoice, "zipvoice");
        Intrinsics.checkNotNullParameter(kitten, "kitten");
        Intrinsics.checkNotNullParameter(pocket, "pocket");
        Intrinsics.checkNotNullParameter(supertonic, "supertonic");
        Intrinsics.checkNotNullParameter(provider, "provider");
        return new OfflineTtsModelConfig(vits, matcha, kokoro, zipvoice, kitten, pocket, supertonic, numThreads, debug, provider);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof OfflineTtsModelConfig)) {
            return false;
        }
        OfflineTtsModelConfig offlineTtsModelConfig = (OfflineTtsModelConfig) other;
        return Intrinsics.areEqual(this.vits, offlineTtsModelConfig.vits) && Intrinsics.areEqual(this.matcha, offlineTtsModelConfig.matcha) && Intrinsics.areEqual(this.kokoro, offlineTtsModelConfig.kokoro) && Intrinsics.areEqual(this.zipvoice, offlineTtsModelConfig.zipvoice) && Intrinsics.areEqual(this.kitten, offlineTtsModelConfig.kitten) && Intrinsics.areEqual(this.pocket, offlineTtsModelConfig.pocket) && Intrinsics.areEqual(this.supertonic, offlineTtsModelConfig.supertonic) && this.numThreads == offlineTtsModelConfig.numThreads && this.debug == offlineTtsModelConfig.debug && Intrinsics.areEqual(this.provider, offlineTtsModelConfig.provider);
    }

    public int hashCode() {
        return (((((((((((((((((this.vits.hashCode() * 31) + this.matcha.hashCode()) * 31) + this.kokoro.hashCode()) * 31) + this.zipvoice.hashCode()) * 31) + this.kitten.hashCode()) * 31) + this.pocket.hashCode()) * 31) + this.supertonic.hashCode()) * 31) + Integer.hashCode(this.numThreads)) * 31) + Boolean.hashCode(this.debug)) * 31) + this.provider.hashCode();
    }

    public String toString() {
        return "OfflineTtsModelConfig(vits=" + this.vits + ", matcha=" + this.matcha + ", kokoro=" + this.kokoro + ", zipvoice=" + this.zipvoice + ", kitten=" + this.kitten + ", pocket=" + this.pocket + ", supertonic=" + this.supertonic + ", numThreads=" + this.numThreads + ", debug=" + this.debug + ", provider=" + this.provider + ")";
    }

    public OfflineTtsModelConfig(OfflineTtsVitsModelConfig vits, OfflineTtsMatchaModelConfig matcha, OfflineTtsKokoroModelConfig kokoro, OfflineTtsZipVoiceModelConfig zipvoice, OfflineTtsKittenModelConfig kitten, OfflineTtsPocketModelConfig pocket, OfflineTtsSupertonicModelConfig supertonic, int numThreads, boolean debug, String provider) {
        Intrinsics.checkNotNullParameter(vits, "vits");
        Intrinsics.checkNotNullParameter(matcha, "matcha");
        Intrinsics.checkNotNullParameter(kokoro, "kokoro");
        Intrinsics.checkNotNullParameter(zipvoice, "zipvoice");
        Intrinsics.checkNotNullParameter(kitten, "kitten");
        Intrinsics.checkNotNullParameter(pocket, "pocket");
        Intrinsics.checkNotNullParameter(supertonic, "supertonic");
        Intrinsics.checkNotNullParameter(provider, "provider");
        this.vits = vits;
        this.matcha = matcha;
        this.kokoro = kokoro;
        this.zipvoice = zipvoice;
        this.kitten = kitten;
        this.pocket = pocket;
        this.supertonic = supertonic;
        this.numThreads = numThreads;
        this.debug = debug;
        this.provider = provider;
    }

    public /* synthetic */ OfflineTtsModelConfig(OfflineTtsVitsModelConfig offlineTtsVitsModelConfig, OfflineTtsMatchaModelConfig offlineTtsMatchaModelConfig, OfflineTtsKokoroModelConfig offlineTtsKokoroModelConfig, OfflineTtsZipVoiceModelConfig offlineTtsZipVoiceModelConfig, OfflineTtsKittenModelConfig offlineTtsKittenModelConfig, OfflineTtsPocketModelConfig offlineTtsPocketModelConfig, OfflineTtsSupertonicModelConfig offlineTtsSupertonicModelConfig, int i, boolean z, String str, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? new OfflineTtsVitsModelConfig(null, null, null, null, null, 0.0f, 0.0f, 0.0f, 255, null) : offlineTtsVitsModelConfig, (i2 & 2) != 0 ? new OfflineTtsMatchaModelConfig(null, null, null, null, null, null, 0.0f, 0.0f, 255, null) : offlineTtsMatchaModelConfig, (i2 & 4) != 0 ? new OfflineTtsKokoroModelConfig(null, null, null, null, null, null, null, 0.0f, 255, null) : offlineTtsKokoroModelConfig, (i2 & 8) != 0 ? new OfflineTtsZipVoiceModelConfig(null, null, null, null, null, null, 0.0f, 0.0f, 0.0f, 0.0f, 1023, null) : offlineTtsZipVoiceModelConfig, (i2 & 16) != 0 ? new OfflineTtsKittenModelConfig(null, null, null, null, 0.0f, 31, null) : offlineTtsKittenModelConfig, (i2 & 32) != 0 ? new OfflineTtsPocketModelConfig(null, null, null, null, null, null, null, 0, 255, null) : offlineTtsPocketModelConfig, (i2 & 64) != 0 ? new OfflineTtsSupertonicModelConfig(null, null, null, null, null, null, null, 127, null) : offlineTtsSupertonicModelConfig, (i2 & 128) != 0 ? 1 : i, (i2 & 256) != 0 ? false : z, (i2 & 512) != 0 ? "cpu" : str);
    }

    public final OfflineTtsVitsModelConfig getVits() {
        return this.vits;
    }

    public final void setVits(OfflineTtsVitsModelConfig offlineTtsVitsModelConfig) {
        Intrinsics.checkNotNullParameter(offlineTtsVitsModelConfig, "<set-?>");
        this.vits = offlineTtsVitsModelConfig;
    }

    public final OfflineTtsMatchaModelConfig getMatcha() {
        return this.matcha;
    }

    public final void setMatcha(OfflineTtsMatchaModelConfig offlineTtsMatchaModelConfig) {
        Intrinsics.checkNotNullParameter(offlineTtsMatchaModelConfig, "<set-?>");
        this.matcha = offlineTtsMatchaModelConfig;
    }

    public final OfflineTtsKokoroModelConfig getKokoro() {
        return this.kokoro;
    }

    public final void setKokoro(OfflineTtsKokoroModelConfig offlineTtsKokoroModelConfig) {
        Intrinsics.checkNotNullParameter(offlineTtsKokoroModelConfig, "<set-?>");
        this.kokoro = offlineTtsKokoroModelConfig;
    }

    public final OfflineTtsZipVoiceModelConfig getZipvoice() {
        return this.zipvoice;
    }

    public final void setZipvoice(OfflineTtsZipVoiceModelConfig offlineTtsZipVoiceModelConfig) {
        Intrinsics.checkNotNullParameter(offlineTtsZipVoiceModelConfig, "<set-?>");
        this.zipvoice = offlineTtsZipVoiceModelConfig;
    }

    public final OfflineTtsKittenModelConfig getKitten() {
        return this.kitten;
    }

    public final void setKitten(OfflineTtsKittenModelConfig offlineTtsKittenModelConfig) {
        Intrinsics.checkNotNullParameter(offlineTtsKittenModelConfig, "<set-?>");
        this.kitten = offlineTtsKittenModelConfig;
    }

    public final OfflineTtsPocketModelConfig getPocket() {
        return this.pocket;
    }

    public final void setPocket(OfflineTtsPocketModelConfig offlineTtsPocketModelConfig) {
        Intrinsics.checkNotNullParameter(offlineTtsPocketModelConfig, "<set-?>");
        this.pocket = offlineTtsPocketModelConfig;
    }

    public final OfflineTtsSupertonicModelConfig getSupertonic() {
        return this.supertonic;
    }

    public final void setSupertonic(OfflineTtsSupertonicModelConfig offlineTtsSupertonicModelConfig) {
        Intrinsics.checkNotNullParameter(offlineTtsSupertonicModelConfig, "<set-?>");
        this.supertonic = offlineTtsSupertonicModelConfig;
    }

    public final int getNumThreads() {
        return this.numThreads;
    }

    public final void setNumThreads(int i) {
        this.numThreads = i;
    }

    public final boolean getDebug() {
        return this.debug;
    }

    public final void setDebug(boolean z) {
        this.debug = z;
    }

    public final String getProvider() {
        return this.provider;
    }

    public final void setProvider(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.provider = str;
    }
}
