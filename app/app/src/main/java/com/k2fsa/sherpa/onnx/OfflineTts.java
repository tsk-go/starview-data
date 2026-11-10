package com.k2fsa.sherpa.onnx;

import android.content.res.AssetManager;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: Tts.kt */
public final class OfflineTts {
    private OfflineTtsConfig config;
    private long ptr;

    private final native void delete(long ptr);

    private final native GeneratedAudio generateImpl(long ptr, String text, int sid, float speed);

    private final native GeneratedAudio generateWithCallbackImpl(long ptr, String text, int sid, float speed, Function1<? super float[], Integer> callback);

    private final native GeneratedAudio generateWithConfigImpl(long ptr, String text, GenerationConfig config, Function1<? super float[], Integer> callback);

    private final native int getNumSpeakers(long ptr);

    private final native int getSampleRate(long ptr);

    private final native long newFromAsset(AssetManager assetManager, OfflineTtsConfig config);

    private final native long newFromFile(OfflineTtsConfig config);

    public OfflineTts(AssetManager assetManager, OfflineTtsConfig config) {
        long newFromFile;
        Intrinsics.checkNotNullParameter(config, "config");
        this.config = config;
        if (assetManager != null) {
            newFromFile = newFromAsset(assetManager, this.config);
        } else {
            newFromFile = newFromFile(this.config);
        }
        this.ptr = newFromFile;
        if (this.ptr != 0) {
        } else {
            throw new IllegalArgumentException("Invalid OfflineTtsConfig: failed to create native OfflineTts".toString());
        }
    }

    public /* synthetic */ OfflineTts(AssetManager assetManager, OfflineTtsConfig offlineTtsConfig, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : assetManager, offlineTtsConfig);
    }

    public final OfflineTtsConfig getConfig() {
        return this.config;
    }

    public final void setConfig(OfflineTtsConfig offlineTtsConfig) {
        Intrinsics.checkNotNullParameter(offlineTtsConfig, "<set-?>");
        this.config = offlineTtsConfig;
    }

    public final int sampleRate() {
        return getSampleRate(this.ptr);
    }

    public final int numSpeakers() {
        return getNumSpeakers(this.ptr);
    }

    public static /* synthetic */ GeneratedAudio generate$default(OfflineTts offlineTts, String str, int i, float f, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = 0;
        }
        if ((i2 & 4) != 0) {
            f = 1.0f;
        }
        return offlineTts.generate(str, i, f);
    }

    public final GeneratedAudio generate(String text, int sid, float speed) {
        Intrinsics.checkNotNullParameter(text, "text");
        return generateImpl(this.ptr, text, sid, speed);
    }

    public static /* synthetic */ GeneratedAudio generateWithCallback$default(OfflineTts offlineTts, String str, int i, float f, Function1 function1, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = 0;
        }
        if ((i2 & 4) != 0) {
            f = 1.0f;
        }
        return offlineTts.generateWithCallback(str, i, f, function1);
    }

    public final GeneratedAudio generateWithCallback(String text, int sid, float speed, Function1<? super float[], Integer> callback) {
        Intrinsics.checkNotNullParameter(text, "text");
        Intrinsics.checkNotNullParameter(callback, "callback");
        return generateWithCallbackImpl(this.ptr, text, sid, speed, callback);
    }

    public final GeneratedAudio generateWithConfig(String text, GenerationConfig config) {
        Intrinsics.checkNotNullParameter(text, "text");
        Intrinsics.checkNotNullParameter(config, "config");
        return generateWithConfigImpl(this.ptr, text, config, null);
    }

    public final GeneratedAudio generateWithConfigAndCallback(String text, GenerationConfig config, Function1<? super float[], Integer> callback) {
        Intrinsics.checkNotNullParameter(text, "text");
        Intrinsics.checkNotNullParameter(config, "config");
        Intrinsics.checkNotNullParameter(callback, "callback");
        return generateWithConfigImpl(this.ptr, text, config, callback);
    }

    public static /* synthetic */ void allocate$default(OfflineTts offlineTts, AssetManager assetManager, int i, Object obj) {
        if ((i & 1) != 0) {
            assetManager = null;
        }
        offlineTts.allocate(assetManager);
    }

    public final void allocate(AssetManager assetManager) {
        long newFromFile;
        if (this.ptr == 0) {
            if (assetManager != null) {
                newFromFile = newFromAsset(assetManager, this.config);
            } else {
                newFromFile = newFromFile(this.config);
            }
            this.ptr = newFromFile;
            if (!(this.ptr != 0)) {
                throw new IllegalArgumentException("Invalid OfflineTtsConfig: failed to create native OfflineTts".toString());
            }
        }
    }

    public final void free() {
        if (this.ptr != 0) {
            delete(this.ptr);
            this.ptr = 0L;
        }
    }

    protected final void finalize() {
        if (this.ptr != 0) {
            delete(this.ptr);
            this.ptr = 0L;
        }
    }

    public final void release() {
        finalize();
    }

    static /* synthetic */ GeneratedAudio generateImpl$default(OfflineTts offlineTts, long j, String str, int i, float f, int i2, Object obj) {
        int i3;
        float f2;
        if ((i2 & 4) == 0) {
            i3 = i;
        } else {
            i3 = 0;
        }
        if ((i2 & 8) == 0) {
            f2 = f;
        } else {
            f2 = 1.0f;
        }
        return offlineTts.generateImpl(j, str, i3, f2);
    }

    static /* synthetic */ GeneratedAudio generateWithCallbackImpl$default(OfflineTts offlineTts, long j, String str, int i, float f, Function1 function1, int i2, Object obj) {
        int i3;
        float f2;
        if ((i2 & 4) == 0) {
            i3 = i;
        } else {
            i3 = 0;
        }
        if ((i2 & 8) == 0) {
            f2 = f;
        } else {
            f2 = 1.0f;
        }
        return offlineTts.generateWithCallbackImpl(j, str, i3, f2, function1);
    }
}
