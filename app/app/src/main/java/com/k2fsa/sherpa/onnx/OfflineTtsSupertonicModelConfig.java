package com.k2fsa.sherpa.onnx;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: Tts.kt */
public final /* data */ class OfflineTtsSupertonicModelConfig {
    private String durationPredictor;
    private String textEncoder;
    private String ttsJson;
    private String unicodeIndexer;
    private String vectorEstimator;
    private String vocoder;
    private String voiceStyle;

    public OfflineTtsSupertonicModelConfig() {
        this(null, null, null, null, null, null, null, 127, null);
    }

    public static /* synthetic */ OfflineTtsSupertonicModelConfig copy$default(OfflineTtsSupertonicModelConfig offlineTtsSupertonicModelConfig, String str, String str2, String str3, String str4, String str5, String str6, String str7, int i, Object obj) {
        if ((i & 1) != 0) {
            str = offlineTtsSupertonicModelConfig.durationPredictor;
        }
        if ((i & 2) != 0) {
            str2 = offlineTtsSupertonicModelConfig.textEncoder;
        }
        String str8 = str2;
        if ((i & 4) != 0) {
            str3 = offlineTtsSupertonicModelConfig.vectorEstimator;
        }
        String str9 = str3;
        if ((i & 8) != 0) {
            str4 = offlineTtsSupertonicModelConfig.vocoder;
        }
        String str10 = str4;
        if ((i & 16) != 0) {
            str5 = offlineTtsSupertonicModelConfig.ttsJson;
        }
        String str11 = str5;
        if ((i & 32) != 0) {
            str6 = offlineTtsSupertonicModelConfig.unicodeIndexer;
        }
        String str12 = str6;
        if ((i & 64) != 0) {
            str7 = offlineTtsSupertonicModelConfig.voiceStyle;
        }
        return offlineTtsSupertonicModelConfig.copy(str, str8, str9, str10, str11, str12, str7);
    }

    /* renamed from: component1, reason: from getter */
    public final String getDurationPredictor() {
        return this.durationPredictor;
    }

    /* renamed from: component2, reason: from getter */
    public final String getTextEncoder() {
        return this.textEncoder;
    }

    /* renamed from: component3, reason: from getter */
    public final String getVectorEstimator() {
        return this.vectorEstimator;
    }

    /* renamed from: component4, reason: from getter */
    public final String getVocoder() {
        return this.vocoder;
    }

    /* renamed from: component5, reason: from getter */
    public final String getTtsJson() {
        return this.ttsJson;
    }

    /* renamed from: component6, reason: from getter */
    public final String getUnicodeIndexer() {
        return this.unicodeIndexer;
    }

    /* renamed from: component7, reason: from getter */
    public final String getVoiceStyle() {
        return this.voiceStyle;
    }

    public final OfflineTtsSupertonicModelConfig copy(String durationPredictor, String textEncoder, String vectorEstimator, String vocoder, String ttsJson, String unicodeIndexer, String voiceStyle) {
        Intrinsics.checkNotNullParameter(durationPredictor, "durationPredictor");
        Intrinsics.checkNotNullParameter(textEncoder, "textEncoder");
        Intrinsics.checkNotNullParameter(vectorEstimator, "vectorEstimator");
        Intrinsics.checkNotNullParameter(vocoder, "vocoder");
        Intrinsics.checkNotNullParameter(ttsJson, "ttsJson");
        Intrinsics.checkNotNullParameter(unicodeIndexer, "unicodeIndexer");
        Intrinsics.checkNotNullParameter(voiceStyle, "voiceStyle");
        return new OfflineTtsSupertonicModelConfig(durationPredictor, textEncoder, vectorEstimator, vocoder, ttsJson, unicodeIndexer, voiceStyle);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof OfflineTtsSupertonicModelConfig)) {
            return false;
        }
        OfflineTtsSupertonicModelConfig offlineTtsSupertonicModelConfig = (OfflineTtsSupertonicModelConfig) other;
        return Intrinsics.areEqual(this.durationPredictor, offlineTtsSupertonicModelConfig.durationPredictor) && Intrinsics.areEqual(this.textEncoder, offlineTtsSupertonicModelConfig.textEncoder) && Intrinsics.areEqual(this.vectorEstimator, offlineTtsSupertonicModelConfig.vectorEstimator) && Intrinsics.areEqual(this.vocoder, offlineTtsSupertonicModelConfig.vocoder) && Intrinsics.areEqual(this.ttsJson, offlineTtsSupertonicModelConfig.ttsJson) && Intrinsics.areEqual(this.unicodeIndexer, offlineTtsSupertonicModelConfig.unicodeIndexer) && Intrinsics.areEqual(this.voiceStyle, offlineTtsSupertonicModelConfig.voiceStyle);
    }

    public int hashCode() {
        return (((((((((((this.durationPredictor.hashCode() * 31) + this.textEncoder.hashCode()) * 31) + this.vectorEstimator.hashCode()) * 31) + this.vocoder.hashCode()) * 31) + this.ttsJson.hashCode()) * 31) + this.unicodeIndexer.hashCode()) * 31) + this.voiceStyle.hashCode();
    }

    public String toString() {
        return "OfflineTtsSupertonicModelConfig(durationPredictor=" + this.durationPredictor + ", textEncoder=" + this.textEncoder + ", vectorEstimator=" + this.vectorEstimator + ", vocoder=" + this.vocoder + ", ttsJson=" + this.ttsJson + ", unicodeIndexer=" + this.unicodeIndexer + ", voiceStyle=" + this.voiceStyle + ")";
    }

    public OfflineTtsSupertonicModelConfig(String durationPredictor, String textEncoder, String vectorEstimator, String vocoder, String ttsJson, String unicodeIndexer, String voiceStyle) {
        Intrinsics.checkNotNullParameter(durationPredictor, "durationPredictor");
        Intrinsics.checkNotNullParameter(textEncoder, "textEncoder");
        Intrinsics.checkNotNullParameter(vectorEstimator, "vectorEstimator");
        Intrinsics.checkNotNullParameter(vocoder, "vocoder");
        Intrinsics.checkNotNullParameter(ttsJson, "ttsJson");
        Intrinsics.checkNotNullParameter(unicodeIndexer, "unicodeIndexer");
        Intrinsics.checkNotNullParameter(voiceStyle, "voiceStyle");
        this.durationPredictor = durationPredictor;
        this.textEncoder = textEncoder;
        this.vectorEstimator = vectorEstimator;
        this.vocoder = vocoder;
        this.ttsJson = ttsJson;
        this.unicodeIndexer = unicodeIndexer;
        this.voiceStyle = voiceStyle;
    }

    public /* synthetic */ OfflineTtsSupertonicModelConfig(String str, String str2, String str3, String str4, String str5, String str6, String str7, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? "" : str, (i & 2) != 0 ? "" : str2, (i & 4) != 0 ? "" : str3, (i & 8) != 0 ? "" : str4, (i & 16) != 0 ? "" : str5, (i & 32) != 0 ? "" : str6, (i & 64) != 0 ? "" : str7);
    }

    public final String getDurationPredictor() {
        return this.durationPredictor;
    }

    public final void setDurationPredictor(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.durationPredictor = str;
    }

    public final String getTextEncoder() {
        return this.textEncoder;
    }

    public final void setTextEncoder(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.textEncoder = str;
    }

    public final String getVectorEstimator() {
        return this.vectorEstimator;
    }

    public final void setVectorEstimator(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.vectorEstimator = str;
    }

    public final String getVocoder() {
        return this.vocoder;
    }

    public final void setVocoder(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.vocoder = str;
    }

    public final String getTtsJson() {
        return this.ttsJson;
    }

    public final void setTtsJson(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.ttsJson = str;
    }

    public final String getUnicodeIndexer() {
        return this.unicodeIndexer;
    }

    public final void setUnicodeIndexer(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.unicodeIndexer = str;
    }

    public final String getVoiceStyle() {
        return this.voiceStyle;
    }

    public final void setVoiceStyle(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.voiceStyle = str;
    }
}
