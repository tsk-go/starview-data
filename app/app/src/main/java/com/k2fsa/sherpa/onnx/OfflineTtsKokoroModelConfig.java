package com.k2fsa.sherpa.onnx;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: Tts.kt */
public final /* data */ class OfflineTtsKokoroModelConfig {
    private String dataDir;
    private String dictDir;
    private String lang;
    private float lengthScale;
    private String lexicon;
    private String model;
    private String tokens;
    private String voices;

    public OfflineTtsKokoroModelConfig() {
        this(null, null, null, null, null, null, null, 0.0f, 255, null);
    }








    public final OfflineTtsKokoroModelConfig copy(String model, String voices, String tokens, String dataDir, String lexicon, String lang, String dictDir, float lengthScale) {
        Intrinsics.checkNotNullParameter(model, "model");
        Intrinsics.checkNotNullParameter(voices, "voices");
        Intrinsics.checkNotNullParameter(tokens, "tokens");
        Intrinsics.checkNotNullParameter(dataDir, "dataDir");
        Intrinsics.checkNotNullParameter(lexicon, "lexicon");
        Intrinsics.checkNotNullParameter(lang, "lang");
        Intrinsics.checkNotNullParameter(dictDir, "dictDir");
        return new OfflineTtsKokoroModelConfig(model, voices, tokens, dataDir, lexicon, lang, dictDir, lengthScale);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof OfflineTtsKokoroModelConfig)) {
            return false;
        }
        OfflineTtsKokoroModelConfig offlineTtsKokoroModelConfig = (OfflineTtsKokoroModelConfig) other;
        return Intrinsics.areEqual(this.model, offlineTtsKokoroModelConfig.model) && Intrinsics.areEqual(this.voices, offlineTtsKokoroModelConfig.voices) && Intrinsics.areEqual(this.tokens, offlineTtsKokoroModelConfig.tokens) && Intrinsics.areEqual(this.dataDir, offlineTtsKokoroModelConfig.dataDir) && Intrinsics.areEqual(this.lexicon, offlineTtsKokoroModelConfig.lexicon) && Intrinsics.areEqual(this.lang, offlineTtsKokoroModelConfig.lang) && Intrinsics.areEqual(this.dictDir, offlineTtsKokoroModelConfig.dictDir) && Float.compare(this.lengthScale, offlineTtsKokoroModelConfig.lengthScale) == 0;
    }

    public int hashCode() {
        return (((((((((((((this.model.hashCode() * 31) + this.voices.hashCode()) * 31) + this.tokens.hashCode()) * 31) + this.dataDir.hashCode()) * 31) + this.lexicon.hashCode()) * 31) + this.lang.hashCode()) * 31) + this.dictDir.hashCode()) * 31) + Float.hashCode(this.lengthScale);
    }

    public String toString() {
        return "OfflineTtsKokoroModelConfig(model=" + this.model + ", voices=" + this.voices + ", tokens=" + this.tokens + ", dataDir=" + this.dataDir + ", lexicon=" + this.lexicon + ", lang=" + this.lang + ", dictDir=" + this.dictDir + ", lengthScale=" + this.lengthScale + ")";
    }

    public OfflineTtsKokoroModelConfig(String model, String voices, String tokens, String dataDir, String lexicon, String lang, String dictDir, float lengthScale) {
        Intrinsics.checkNotNullParameter(model, "model");
        Intrinsics.checkNotNullParameter(voices, "voices");
        Intrinsics.checkNotNullParameter(tokens, "tokens");
        Intrinsics.checkNotNullParameter(dataDir, "dataDir");
        Intrinsics.checkNotNullParameter(lexicon, "lexicon");
        Intrinsics.checkNotNullParameter(lang, "lang");
        Intrinsics.checkNotNullParameter(dictDir, "dictDir");
        this.model = model;
        this.voices = voices;
        this.tokens = tokens;
        this.dataDir = dataDir;
        this.lexicon = lexicon;
        this.lang = lang;
        this.dictDir = dictDir;
        this.lengthScale = lengthScale;
    }

    public /* synthetic */ OfflineTtsKokoroModelConfig(String str, String str2, String str3, String str4, String str5, String str6, String str7, float f, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? "" : str, (i & 2) != 0 ? "" : str2, (i & 4) != 0 ? "" : str3, (i & 8) != 0 ? "" : str4, (i & 16) != 0 ? "" : str5, (i & 32) != 0 ? "" : str6, (i & 64) != 0 ? "" : str7, (i & 128) != 0 ? 1.0f : f);
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

    public final String getLexicon() {
        return this.lexicon;
    }

    public final void setLexicon(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.lexicon = str;
    }

    public final String getLang() {
        return this.lang;
    }

    public final void setLang(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.lang = str;
    }

    public final String getDictDir() {
        return this.dictDir;
    }

    public final void setDictDir(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.dictDir = str;
    }

    public final float getLengthScale() {
        return this.lengthScale;
    }

    public final void setLengthScale(float f) {
        this.lengthScale = f;
    }
}
