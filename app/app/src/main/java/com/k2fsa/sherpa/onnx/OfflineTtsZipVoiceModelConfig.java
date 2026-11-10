package com.k2fsa.sherpa.onnx;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: Tts.kt */
public final /* data */ class OfflineTtsZipVoiceModelConfig {
    private String dataDir;
    private String decoder;
    private String encoder;
    private float featScale;
    private float guidanceScale;
    private String lexicon;
    private float tShift;
    private float targetRms;
    private String tokens;
    private String vocoder;

    public OfflineTtsZipVoiceModelConfig() {
        this(null, null, null, null, null, null, 0.0f, 0.0f, 0.0f, 0.0f, 1023, null);
    }










    public final OfflineTtsZipVoiceModelConfig copy(String tokens, String encoder, String decoder, String vocoder, String dataDir, String lexicon, float featScale, float tShift, float targetRms, float guidanceScale) {
        Intrinsics.checkNotNullParameter(tokens, "tokens");
        Intrinsics.checkNotNullParameter(encoder, "encoder");
        Intrinsics.checkNotNullParameter(decoder, "decoder");
        Intrinsics.checkNotNullParameter(vocoder, "vocoder");
        Intrinsics.checkNotNullParameter(dataDir, "dataDir");
        Intrinsics.checkNotNullParameter(lexicon, "lexicon");
        return new OfflineTtsZipVoiceModelConfig(tokens, encoder, decoder, vocoder, dataDir, lexicon, featScale, tShift, targetRms, guidanceScale);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof OfflineTtsZipVoiceModelConfig)) {
            return false;
        }
        OfflineTtsZipVoiceModelConfig offlineTtsZipVoiceModelConfig = (OfflineTtsZipVoiceModelConfig) other;
        return Intrinsics.areEqual(this.tokens, offlineTtsZipVoiceModelConfig.tokens) && Intrinsics.areEqual(this.encoder, offlineTtsZipVoiceModelConfig.encoder) && Intrinsics.areEqual(this.decoder, offlineTtsZipVoiceModelConfig.decoder) && Intrinsics.areEqual(this.vocoder, offlineTtsZipVoiceModelConfig.vocoder) && Intrinsics.areEqual(this.dataDir, offlineTtsZipVoiceModelConfig.dataDir) && Intrinsics.areEqual(this.lexicon, offlineTtsZipVoiceModelConfig.lexicon) && Float.compare(this.featScale, offlineTtsZipVoiceModelConfig.featScale) == 0 && Float.compare(this.tShift, offlineTtsZipVoiceModelConfig.tShift) == 0 && Float.compare(this.targetRms, offlineTtsZipVoiceModelConfig.targetRms) == 0 && Float.compare(this.guidanceScale, offlineTtsZipVoiceModelConfig.guidanceScale) == 0;
    }

    public int hashCode() {
        return (((((((((((((((((this.tokens.hashCode() * 31) + this.encoder.hashCode()) * 31) + this.decoder.hashCode()) * 31) + this.vocoder.hashCode()) * 31) + this.dataDir.hashCode()) * 31) + this.lexicon.hashCode()) * 31) + Float.hashCode(this.featScale)) * 31) + Float.hashCode(this.tShift)) * 31) + Float.hashCode(this.targetRms)) * 31) + Float.hashCode(this.guidanceScale);
    }

    public String toString() {
        return "OfflineTtsZipVoiceModelConfig(tokens=" + this.tokens + ", encoder=" + this.encoder + ", decoder=" + this.decoder + ", vocoder=" + this.vocoder + ", dataDir=" + this.dataDir + ", lexicon=" + this.lexicon + ", featScale=" + this.featScale + ", tShift=" + this.tShift + ", targetRms=" + this.targetRms + ", guidanceScale=" + this.guidanceScale + ")";
    }

    public OfflineTtsZipVoiceModelConfig(String tokens, String encoder, String decoder, String vocoder, String dataDir, String lexicon, float featScale, float tShift, float targetRms, float guidanceScale) {
        Intrinsics.checkNotNullParameter(tokens, "tokens");
        Intrinsics.checkNotNullParameter(encoder, "encoder");
        Intrinsics.checkNotNullParameter(decoder, "decoder");
        Intrinsics.checkNotNullParameter(vocoder, "vocoder");
        Intrinsics.checkNotNullParameter(dataDir, "dataDir");
        Intrinsics.checkNotNullParameter(lexicon, "lexicon");
        this.tokens = tokens;
        this.encoder = encoder;
        this.decoder = decoder;
        this.vocoder = vocoder;
        this.dataDir = dataDir;
        this.lexicon = lexicon;
        this.featScale = featScale;
        this.tShift = tShift;
        this.targetRms = targetRms;
        this.guidanceScale = guidanceScale;
    }

    public /* synthetic */ OfflineTtsZipVoiceModelConfig(String str, String str2, String str3, String str4, String str5, String str6, float f, float f2, float f3, float f4, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? "" : str, (i & 2) != 0 ? "" : str2, (i & 4) != 0 ? "" : str3, (i & 8) != 0 ? "" : str4, (i & 16) != 0 ? "" : str5, (i & 32) != 0 ? "" : str6, (i & 64) != 0 ? 0.1f : f, (i & 128) != 0 ? 0.5f : f2, (i & 256) != 0 ? 0.1f : f3, (i & 512) != 0 ? 1.0f : f4);
    }

    public final String getTokens() {
        return this.tokens;
    }

    public final void setTokens(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.tokens = str;
    }

    public final String getEncoder() {
        return this.encoder;
    }

    public final void setEncoder(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.encoder = str;
    }

    public final String getDecoder() {
        return this.decoder;
    }

    public final void setDecoder(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.decoder = str;
    }

    public final String getVocoder() {
        return this.vocoder;
    }

    public final void setVocoder(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.vocoder = str;
    }

    public final String getDataDir() {
        return this.dataDir;
    }

    public final void setDataDir(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.dataDir = str;
    }

    public final String getLexicon() {
        return this.lexicon;
    }

    public final void setLexicon(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.lexicon = str;
    }

    public final float getFeatScale() {
        return this.featScale;
    }

    public final void setFeatScale(float f) {
        this.featScale = f;
    }

    public final float getTShift() {
        return this.tShift;
    }

    public final void setTShift(float f) {
        this.tShift = f;
    }

    public final float getTargetRms() {
        return this.targetRms;
    }

    public final void setTargetRms(float f) {
        this.targetRms = f;
    }

    public final float getGuidanceScale() {
        return this.guidanceScale;
    }

    public final void setGuidanceScale(float f) {
        this.guidanceScale = f;
    }
}
