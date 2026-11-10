package com.k2fsa.sherpa.onnx;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: Tts.kt */
public final /* data */ class OfflineTtsVitsModelConfig {
    private String dataDir;
    private String dictDir;
    private float lengthScale;
    private String lexicon;
    private String model;
    private float noiseScale;
    private float noiseScaleW;
    private String tokens;

    public OfflineTtsVitsModelConfig() {
        this(null, null, null, null, null, 0.0f, 0.0f, 0.0f, 255, null);
    }








    public final OfflineTtsVitsModelConfig copy(String model, String lexicon, String tokens, String dataDir, String dictDir, float noiseScale, float noiseScaleW, float lengthScale) {
        Intrinsics.checkNotNullParameter(model, "model");
        Intrinsics.checkNotNullParameter(lexicon, "lexicon");
        Intrinsics.checkNotNullParameter(tokens, "tokens");
        Intrinsics.checkNotNullParameter(dataDir, "dataDir");
        Intrinsics.checkNotNullParameter(dictDir, "dictDir");
        return new OfflineTtsVitsModelConfig(model, lexicon, tokens, dataDir, dictDir, noiseScale, noiseScaleW, lengthScale);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof OfflineTtsVitsModelConfig)) {
            return false;
        }
        OfflineTtsVitsModelConfig offlineTtsVitsModelConfig = (OfflineTtsVitsModelConfig) other;
        return Intrinsics.areEqual(this.model, offlineTtsVitsModelConfig.model) && Intrinsics.areEqual(this.lexicon, offlineTtsVitsModelConfig.lexicon) && Intrinsics.areEqual(this.tokens, offlineTtsVitsModelConfig.tokens) && Intrinsics.areEqual(this.dataDir, offlineTtsVitsModelConfig.dataDir) && Intrinsics.areEqual(this.dictDir, offlineTtsVitsModelConfig.dictDir) && Float.compare(this.noiseScale, offlineTtsVitsModelConfig.noiseScale) == 0 && Float.compare(this.noiseScaleW, offlineTtsVitsModelConfig.noiseScaleW) == 0 && Float.compare(this.lengthScale, offlineTtsVitsModelConfig.lengthScale) == 0;
    }

    public int hashCode() {
        return (((((((((((((this.model.hashCode() * 31) + this.lexicon.hashCode()) * 31) + this.tokens.hashCode()) * 31) + this.dataDir.hashCode()) * 31) + this.dictDir.hashCode()) * 31) + Float.hashCode(this.noiseScale)) * 31) + Float.hashCode(this.noiseScaleW)) * 31) + Float.hashCode(this.lengthScale);
    }

    public String toString() {
        return "OfflineTtsVitsModelConfig(model=" + this.model + ", lexicon=" + this.lexicon + ", tokens=" + this.tokens + ", dataDir=" + this.dataDir + ", dictDir=" + this.dictDir + ", noiseScale=" + this.noiseScale + ", noiseScaleW=" + this.noiseScaleW + ", lengthScale=" + this.lengthScale + ")";
    }

    public OfflineTtsVitsModelConfig(String model, String lexicon, String tokens, String dataDir, String dictDir, float noiseScale, float noiseScaleW, float lengthScale) {
        Intrinsics.checkNotNullParameter(model, "model");
        Intrinsics.checkNotNullParameter(lexicon, "lexicon");
        Intrinsics.checkNotNullParameter(tokens, "tokens");
        Intrinsics.checkNotNullParameter(dataDir, "dataDir");
        Intrinsics.checkNotNullParameter(dictDir, "dictDir");
        this.model = model;
        this.lexicon = lexicon;
        this.tokens = tokens;
        this.dataDir = dataDir;
        this.dictDir = dictDir;
        this.noiseScale = noiseScale;
        this.noiseScaleW = noiseScaleW;
        this.lengthScale = lengthScale;
    }

    public /* synthetic */ OfflineTtsVitsModelConfig(String str, String str2, String str3, String str4, String str5, float f, float f2, float f3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? "" : str, (i & 2) != 0 ? "" : str2, (i & 4) != 0 ? "" : str3, (i & 8) != 0 ? "" : str4, (i & 16) != 0 ? "" : str5, (i & 32) != 0 ? 0.667f : f, (i & 64) != 0 ? 0.8f : f2, (i & 128) != 0 ? 1.0f : f3);
    }

    public final String getModel() {
        return this.model;
    }

    public final void setModel(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.model = str;
    }

    public final String getLexicon() {
        return this.lexicon;
    }

    public final void setLexicon(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.lexicon = str;
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

    public final String getDictDir() {
        return this.dictDir;
    }

    public final void setDictDir(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.dictDir = str;
    }

    public final float getNoiseScale() {
        return this.noiseScale;
    }

    public final void setNoiseScale(float f) {
        this.noiseScale = f;
    }

    public final float getNoiseScaleW() {
        return this.noiseScaleW;
    }

    public final void setNoiseScaleW(float f) {
        this.noiseScaleW = f;
    }

    public final float getLengthScale() {
        return this.lengthScale;
    }

    public final void setLengthScale(float f) {
        this.lengthScale = f;
    }
}
