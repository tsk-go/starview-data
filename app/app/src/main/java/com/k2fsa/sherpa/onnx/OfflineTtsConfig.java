package com.k2fsa.sherpa.onnx;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: Tts.kt */
public final /* data */ class OfflineTtsConfig {
    private int maxNumSentences;
    private OfflineTtsModelConfig model;
    private String ruleFars;
    private String ruleFsts;
    private float silenceScale;

    public OfflineTtsConfig() {
        this(null, null, null, 0, 0.0f, 31, null);
    }

    public static /* synthetic */ OfflineTtsConfig copy$default(OfflineTtsConfig offlineTtsConfig, OfflineTtsModelConfig offlineTtsModelConfig, String str, String str2, int i, float f, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            offlineTtsModelConfig = offlineTtsConfig.model;
        }
        if ((i2 & 2) != 0) {
            str = offlineTtsConfig.ruleFsts;
        }
        String str3 = str;
        if ((i2 & 4) != 0) {
            str2 = offlineTtsConfig.ruleFars;
        }
        String str4 = str2;
        if ((i2 & 8) != 0) {
            i = offlineTtsConfig.maxNumSentences;
        }
        int i3 = i;
        if ((i2 & 16) != 0) {
            f = offlineTtsConfig.silenceScale;
        }
        return offlineTtsConfig.copy(offlineTtsModelConfig, str3, str4, i3, f);
    }





    public final OfflineTtsConfig copy(OfflineTtsModelConfig model, String ruleFsts, String ruleFars, int maxNumSentences, float silenceScale) {
        Intrinsics.checkNotNullParameter(model, "model");
        Intrinsics.checkNotNullParameter(ruleFsts, "ruleFsts");
        Intrinsics.checkNotNullParameter(ruleFars, "ruleFars");
        return new OfflineTtsConfig(model, ruleFsts, ruleFars, maxNumSentences, silenceScale);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof OfflineTtsConfig)) {
            return false;
        }
        OfflineTtsConfig offlineTtsConfig = (OfflineTtsConfig) other;
        return Intrinsics.areEqual(this.model, offlineTtsConfig.model) && Intrinsics.areEqual(this.ruleFsts, offlineTtsConfig.ruleFsts) && Intrinsics.areEqual(this.ruleFars, offlineTtsConfig.ruleFars) && this.maxNumSentences == offlineTtsConfig.maxNumSentences && Float.compare(this.silenceScale, offlineTtsConfig.silenceScale) == 0;
    }

    public int hashCode() {
        return (((((((this.model.hashCode() * 31) + this.ruleFsts.hashCode()) * 31) + this.ruleFars.hashCode()) * 31) + Integer.hashCode(this.maxNumSentences)) * 31) + Float.hashCode(this.silenceScale);
    }

    public String toString() {
        return "OfflineTtsConfig(model=" + this.model + ", ruleFsts=" + this.ruleFsts + ", ruleFars=" + this.ruleFars + ", maxNumSentences=" + this.maxNumSentences + ", silenceScale=" + this.silenceScale + ")";
    }

    public OfflineTtsConfig(OfflineTtsModelConfig model, String ruleFsts, String ruleFars, int maxNumSentences, float silenceScale) {
        Intrinsics.checkNotNullParameter(model, "model");
        Intrinsics.checkNotNullParameter(ruleFsts, "ruleFsts");
        Intrinsics.checkNotNullParameter(ruleFars, "ruleFars");
        this.model = model;
        this.ruleFsts = ruleFsts;
        this.ruleFars = ruleFars;
        this.maxNumSentences = maxNumSentences;
        this.silenceScale = silenceScale;
    }

    public /* synthetic */ OfflineTtsConfig(OfflineTtsModelConfig offlineTtsModelConfig, String str, String str2, int i, float f, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? new OfflineTtsModelConfig(null, null, null, null, null, null, null, 0, false, null, 1023, null) : offlineTtsModelConfig, (i2 & 2) != 0 ? "" : str, (i2 & 4) == 0 ? str2 : "", (i2 & 8) != 0 ? 1 : i, (i2 & 16) != 0 ? 0.2f : f);
    }

    public final OfflineTtsModelConfig getModel() {
        return this.model;
    }

    public final void setModel(OfflineTtsModelConfig offlineTtsModelConfig) {
        Intrinsics.checkNotNullParameter(offlineTtsModelConfig, "<set-?>");
        this.model = offlineTtsModelConfig;
    }

    public final String getRuleFsts() {
        return this.ruleFsts;
    }

    public final void setRuleFsts(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.ruleFsts = str;
    }

    public final String getRuleFars() {
        return this.ruleFars;
    }

    public final void setRuleFars(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.ruleFars = str;
    }

    public final int getMaxNumSentences() {
        return this.maxNumSentences;
    }

    public final void setMaxNumSentences(int i) {
        this.maxNumSentences = i;
    }

    public final float getSilenceScale() {
        return this.silenceScale;
    }

    public final void setSilenceScale(float f) {
        this.silenceScale = f;
    }
}
