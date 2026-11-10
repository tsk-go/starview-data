package com.k2fsa.sherpa.onnx;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: Tts.kt */
public final /* data */ class OfflineTtsMatchaModelConfig {
    private String acousticModel;
    private String dataDir;
    private String dictDir;
    private float lengthScale;
    private String lexicon;
    private float noiseScale;
    private String tokens;
    private String vocoder;

    public OfflineTtsMatchaModelConfig() {
        this(null, null, null, null, null, null, 0.0f, 0.0f, 255, null);
    }

    /* renamed from: component1, reason: from getter */
    public final String getAcousticModel() {
        return this.acousticModel;
    }

    /* renamed from: component2, reason: from getter */
    public final String getVocoder() {
        return this.vocoder;
    }

    /* renamed from: component3, reason: from getter */
    public final String getLexicon() {
        return this.lexicon;
    }

    /* renamed from: component4, reason: from getter */
    public final String getTokens() {
        return this.tokens;
    }

    /* renamed from: component5, reason: from getter */
    public final String getDataDir() {
        return this.dataDir;
    }

    /* renamed from: component6, reason: from getter */
    public final String getDictDir() {
        return this.dictDir;
    }

    /* renamed from: component7, reason: from getter */
    public final float getNoiseScale() {
        return this.noiseScale;
    }

    /* renamed from: component8, reason: from getter */
    public final float getLengthScale() {
        return this.lengthScale;
    }

    public final OfflineTtsMatchaModelConfig copy(String acousticModel, String vocoder, String lexicon, String tokens, String dataDir, String dictDir, float noiseScale, float lengthScale) {
        Intrinsics.checkNotNullParameter(acousticModel, "acousticModel");
        Intrinsics.checkNotNullParameter(vocoder, "vocoder");
        Intrinsics.checkNotNullParameter(lexicon, "lexicon");
        Intrinsics.checkNotNullParameter(tokens, "tokens");
        Intrinsics.checkNotNullParameter(dataDir, "dataDir");
        Intrinsics.checkNotNullParameter(dictDir, "dictDir");
        return new OfflineTtsMatchaModelConfig(acousticModel, vocoder, lexicon, tokens, dataDir, dictDir, noiseScale, lengthScale);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof OfflineTtsMatchaModelConfig)) {
            return false;
        }
        OfflineTtsMatchaModelConfig offlineTtsMatchaModelConfig = (OfflineTtsMatchaModelConfig) other;
        return Intrinsics.areEqual(this.acousticModel, offlineTtsMatchaModelConfig.acousticModel) && Intrinsics.areEqual(this.vocoder, offlineTtsMatchaModelConfig.vocoder) && Intrinsics.areEqual(this.lexicon, offlineTtsMatchaModelConfig.lexicon) && Intrinsics.areEqual(this.tokens, offlineTtsMatchaModelConfig.tokens) && Intrinsics.areEqual(this.dataDir, offlineTtsMatchaModelConfig.dataDir) && Intrinsics.areEqual(this.dictDir, offlineTtsMatchaModelConfig.dictDir) && Float.compare(this.noiseScale, offlineTtsMatchaModelConfig.noiseScale) == 0 && Float.compare(this.lengthScale, offlineTtsMatchaModelConfig.lengthScale) == 0;
    }

    public int hashCode() {
        return (((((((((((((this.acousticModel.hashCode() * 31) + this.vocoder.hashCode()) * 31) + this.lexicon.hashCode()) * 31) + this.tokens.hashCode()) * 31) + this.dataDir.hashCode()) * 31) + this.dictDir.hashCode()) * 31) + Float.hashCode(this.noiseScale)) * 31) + Float.hashCode(this.lengthScale);
    }

    public String toString() {
        return "OfflineTtsMatchaModelConfig(acousticModel=" + this.acousticModel + ", vocoder=" + this.vocoder + ", lexicon=" + this.lexicon + ", tokens=" + this.tokens + ", dataDir=" + this.dataDir + ", dictDir=" + this.dictDir + ", noiseScale=" + this.noiseScale + ", lengthScale=" + this.lengthScale + ")";
    }

    public OfflineTtsMatchaModelConfig(String acousticModel, String vocoder, String lexicon, String tokens, String dataDir, String dictDir, float noiseScale, float lengthScale) {
        Intrinsics.checkNotNullParameter(acousticModel, "acousticModel");
        Intrinsics.checkNotNullParameter(vocoder, "vocoder");
        Intrinsics.checkNotNullParameter(lexicon, "lexicon");
        Intrinsics.checkNotNullParameter(tokens, "tokens");
        Intrinsics.checkNotNullParameter(dataDir, "dataDir");
        Intrinsics.checkNotNullParameter(dictDir, "dictDir");
        this.acousticModel = acousticModel;
        this.vocoder = vocoder;
        this.lexicon = lexicon;
        this.tokens = tokens;
        this.dataDir = dataDir;
        this.dictDir = dictDir;
        this.noiseScale = noiseScale;
        this.lengthScale = lengthScale;
    }

    public /* synthetic */ OfflineTtsMatchaModelConfig(String str, String str2, String str3, String str4, String str5, String str6, float f, float f2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? "" : str, (i & 2) != 0 ? "" : str2, (i & 4) != 0 ? "" : str3, (i & 8) != 0 ? "" : str4, (i & 16) != 0 ? "" : str5, (i & 32) != 0 ? "" : str6, (i & 64) != 0 ? 1.0f : f, (i & 128) != 0 ? 1.0f : f2);
    }

    public final String getAcousticModel() {
        return this.acousticModel;
    }

    public final void setAcousticModel(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.acousticModel = str;
    }

    public final String getVocoder() {
        return this.vocoder;
    }

    public final void setVocoder(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.vocoder = str;
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

    public final float getLengthScale() {
        return this.lengthScale;
    }

    public final void setLengthScale(float f) {
        this.lengthScale = f;
    }
}
