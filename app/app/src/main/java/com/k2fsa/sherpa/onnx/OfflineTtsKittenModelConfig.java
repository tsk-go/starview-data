package com.k2fsa.sherpa.onnx;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: Tts.kt */
public final /* data */ class OfflineTtsKittenModelConfig {
    private String dataDir;
    private float lengthScale;
    private String model;
    private String tokens;
    private String voices;

    public OfflineTtsKittenModelConfig() {
        this(null, null, null, null, 0.0f, 31, null);
    }

    public static /* synthetic */ OfflineTtsKittenModelConfig copy$default(OfflineTtsKittenModelConfig offlineTtsKittenModelConfig, String str, String str2, String str3, String str4, float f, int i, Object obj) {
        if ((i & 1) != 0) {
            str = offlineTtsKittenModelConfig.model;
        }
        if ((i & 2) != 0) {
            str2 = offlineTtsKittenModelConfig.voices;
        }
        String str5 = str2;
        if ((i & 4) != 0) {
            str3 = offlineTtsKittenModelConfig.tokens;
        }
        String str6 = str3;
        if ((i & 8) != 0) {
            str4 = offlineTtsKittenModelConfig.dataDir;
        }
        String str7 = str4;
        if ((i & 16) != 0) {
            f = offlineTtsKittenModelConfig.lengthScale;
        }
        return offlineTtsKittenModelConfig.copy(str, str5, str6, str7, f);
    }

    /* renamed from: component1, reason: from getter */
    public final String getModel() {
        return this.model;
    }

    /* renamed from: component2, reason: from getter */
    public final String getVoices() {
        return this.voices;
    }

    /* renamed from: component3, reason: from getter */
    public final String getTokens() {
        return this.tokens;
    }

    /* renamed from: component4, reason: from getter */
    public final String getDataDir() {
        return this.dataDir;
    }

    /* renamed from: component5, reason: from getter */
    public final float getLengthScale() {
        return this.lengthScale;
    }

    public final OfflineTtsKittenModelConfig copy(String model, String voices, String tokens, String dataDir, float lengthScale) {
        Intrinsics.checkNotNullParameter(model, "model");
        Intrinsics.checkNotNullParameter(voices, "voices");
        Intrinsics.checkNotNullParameter(tokens, "tokens");
        Intrinsics.checkNotNullParameter(dataDir, "dataDir");
        return new OfflineTtsKittenModelConfig(model, voices, tokens, dataDir, lengthScale);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof OfflineTtsKittenModelConfig)) {
            return false;
        }
        OfflineTtsKittenModelConfig offlineTtsKittenModelConfig = (OfflineTtsKittenModelConfig) other;
        return Intrinsics.areEqual(this.model, offlineTtsKittenModelConfig.model) && Intrinsics.areEqual(this.voices, offlineTtsKittenModelConfig.voices) && Intrinsics.areEqual(this.tokens, offlineTtsKittenModelConfig.tokens) && Intrinsics.areEqual(this.dataDir, offlineTtsKittenModelConfig.dataDir) && Float.compare(this.lengthScale, offlineTtsKittenModelConfig.lengthScale) == 0;
    }

    public int hashCode() {
        return (((((((this.model.hashCode() * 31) + this.voices.hashCode()) * 31) + this.tokens.hashCode()) * 31) + this.dataDir.hashCode()) * 31) + Float.hashCode(this.lengthScale);
    }

    public String toString() {
        return "OfflineTtsKittenModelConfig(model=" + this.model + ", voices=" + this.voices + ", tokens=" + this.tokens + ", dataDir=" + this.dataDir + ", lengthScale=" + this.lengthScale + ")";
    }

    public OfflineTtsKittenModelConfig(String model, String voices, String tokens, String dataDir, float lengthScale) {
        Intrinsics.checkNotNullParameter(model, "model");
        Intrinsics.checkNotNullParameter(voices, "voices");
        Intrinsics.checkNotNullParameter(tokens, "tokens");
        Intrinsics.checkNotNullParameter(dataDir, "dataDir");
        this.model = model;
        this.voices = voices;
        this.tokens = tokens;
        this.dataDir = dataDir;
        this.lengthScale = lengthScale;
    }

    public /* synthetic */ OfflineTtsKittenModelConfig(String str, String str2, String str3, String str4, float f, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? "" : str, (i & 2) != 0 ? "" : str2, (i & 4) != 0 ? "" : str3, (i & 8) != 0 ? "" : str4, (i & 16) != 0 ? 1.0f : f);
    }

    public final String getModel() {
        return this.model;
    }

    public final void setModel(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.model = str;
    }

    public final String getVoices() {
        return this.voices;
    }

    public final void setVoices(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.voices = str;
    }

    public final String getTokens() {
        return this.tokens;
    }

    public final void setTokens(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.tokens = str;
    }

    public final String getDataDir() {
        return this.dataDir;
    }

    public final void setDataDir(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.dataDir = str;
    }

    public final float getLengthScale() {
        return this.lengthScale;
    }

    public final void setLengthScale(float f) {
        this.lengthScale = f;
    }
}
