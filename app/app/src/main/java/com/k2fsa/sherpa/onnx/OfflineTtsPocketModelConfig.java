package com.k2fsa.sherpa.onnx;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: Tts.kt */
public final /* data */ class OfflineTtsPocketModelConfig {
    private String decoder;
    private String encoder;
    private String lmFlow;
    private String lmMain;
    private String textConditioner;
    private String tokenScoresJson;
    private String vocabJson;
    private int voiceEmbeddingCacheCapacity;

    public OfflineTtsPocketModelConfig() {
        this(null, null, null, null, null, null, null, 0, 255, null);
    }








    public final OfflineTtsPocketModelConfig copy(String lmFlow, String lmMain, String encoder, String decoder, String textConditioner, String vocabJson, String tokenScoresJson, int voiceEmbeddingCacheCapacity) {
        Intrinsics.checkNotNullParameter(lmFlow, "lmFlow");
        Intrinsics.checkNotNullParameter(lmMain, "lmMain");
        Intrinsics.checkNotNullParameter(encoder, "encoder");
        Intrinsics.checkNotNullParameter(decoder, "decoder");
        Intrinsics.checkNotNullParameter(textConditioner, "textConditioner");
        Intrinsics.checkNotNullParameter(vocabJson, "vocabJson");
        Intrinsics.checkNotNullParameter(tokenScoresJson, "tokenScoresJson");
        return new OfflineTtsPocketModelConfig(lmFlow, lmMain, encoder, decoder, textConditioner, vocabJson, tokenScoresJson, voiceEmbeddingCacheCapacity);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof OfflineTtsPocketModelConfig)) {
            return false;
        }
        OfflineTtsPocketModelConfig offlineTtsPocketModelConfig = (OfflineTtsPocketModelConfig) other;
        return Intrinsics.areEqual(this.lmFlow, offlineTtsPocketModelConfig.lmFlow) && Intrinsics.areEqual(this.lmMain, offlineTtsPocketModelConfig.lmMain) && Intrinsics.areEqual(this.encoder, offlineTtsPocketModelConfig.encoder) && Intrinsics.areEqual(this.decoder, offlineTtsPocketModelConfig.decoder) && Intrinsics.areEqual(this.textConditioner, offlineTtsPocketModelConfig.textConditioner) && Intrinsics.areEqual(this.vocabJson, offlineTtsPocketModelConfig.vocabJson) && Intrinsics.areEqual(this.tokenScoresJson, offlineTtsPocketModelConfig.tokenScoresJson) && this.voiceEmbeddingCacheCapacity == offlineTtsPocketModelConfig.voiceEmbeddingCacheCapacity;
    }

    public int hashCode() {
        return (((((((((((((this.lmFlow.hashCode() * 31) + this.lmMain.hashCode()) * 31) + this.encoder.hashCode()) * 31) + this.decoder.hashCode()) * 31) + this.textConditioner.hashCode()) * 31) + this.vocabJson.hashCode()) * 31) + this.tokenScoresJson.hashCode()) * 31) + Integer.hashCode(this.voiceEmbeddingCacheCapacity);
    }

    public String toString() {
        return "OfflineTtsPocketModelConfig(lmFlow=" + this.lmFlow + ", lmMain=" + this.lmMain + ", encoder=" + this.encoder + ", decoder=" + this.decoder + ", textConditioner=" + this.textConditioner + ", vocabJson=" + this.vocabJson + ", tokenScoresJson=" + this.tokenScoresJson + ", voiceEmbeddingCacheCapacity=" + this.voiceEmbeddingCacheCapacity + ")";
    }

    public OfflineTtsPocketModelConfig(String lmFlow, String lmMain, String encoder, String decoder, String textConditioner, String vocabJson, String tokenScoresJson, int voiceEmbeddingCacheCapacity) {
        Intrinsics.checkNotNullParameter(lmFlow, "lmFlow");
        Intrinsics.checkNotNullParameter(lmMain, "lmMain");
        Intrinsics.checkNotNullParameter(encoder, "encoder");
        Intrinsics.checkNotNullParameter(decoder, "decoder");
        Intrinsics.checkNotNullParameter(textConditioner, "textConditioner");
        Intrinsics.checkNotNullParameter(vocabJson, "vocabJson");
        Intrinsics.checkNotNullParameter(tokenScoresJson, "tokenScoresJson");
        this.lmFlow = lmFlow;
        this.lmMain = lmMain;
        this.encoder = encoder;
        this.decoder = decoder;
        this.textConditioner = textConditioner;
        this.vocabJson = vocabJson;
        this.tokenScoresJson = tokenScoresJson;
        this.voiceEmbeddingCacheCapacity = voiceEmbeddingCacheCapacity;
    }

    public /* synthetic */ OfflineTtsPocketModelConfig(String str, String str2, String str3, String str4, String str5, String str6, String str7, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? "" : str, (i2 & 2) != 0 ? "" : str2, (i2 & 4) != 0 ? "" : str3, (i2 & 8) != 0 ? "" : str4, (i2 & 16) != 0 ? "" : str5, (i2 & 32) != 0 ? "" : str6, (i2 & 64) != 0 ? "" : str7, (i2 & 128) != 0 ? 50 : i);
    }

    public final String getLmFlow() {
        return this.lmFlow;
    }

    public final void setLmFlow(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.lmFlow = str;
    }

    public final String getLmMain() {
        return this.lmMain;
    }

    public final void setLmMain(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.lmMain = str;
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

    public final String getTextConditioner() {
        return this.textConditioner;
    }

    public final void setTextConditioner(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.textConditioner = str;
    }

    public final String getVocabJson() {
        return this.vocabJson;
    }

    public final void setVocabJson(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.vocabJson = str;
    }

    public final String getTokenScoresJson() {
        return this.tokenScoresJson;
    }

    public final void setTokenScoresJson(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.tokenScoresJson = str;
    }

    public final int getVoiceEmbeddingCacheCapacity() {
        return this.voiceEmbeddingCacheCapacity;
    }

    public final void setVoiceEmbeddingCacheCapacity(int i) {
        this.voiceEmbeddingCacheCapacity = i;
    }
}
