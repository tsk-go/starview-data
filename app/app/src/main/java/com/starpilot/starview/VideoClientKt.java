package com.starpilot.starview;

import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.text.StringsKt;

/* compiled from: VideoClient.kt */
public final class VideoClientKt {
    private static final Lazy hevcDecoder$delegate = LazyKt.lazy(new Function0() { // from class: com.starpilot.starview.VideoClientKt$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            boolean hevcDecoder_delegate$lambda$2;
            hevcDecoder_delegate$lambda$2 = VideoClientKt.hevcDecoder_delegate$lambda$2();
            return Boolean.valueOf(hevcDecoder_delegate$lambda$2);
        }
    });

    public static final boolean getHevcDecoder() {
        return ((Boolean) hevcDecoder$delegate.getValue()).booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean hevcDecoder_delegate$lambda$2() {
        try {
            Object[] codecInfos = new MediaCodecList(0).getCodecInfos();
            Intrinsics.checkNotNullExpressionValue(codecInfos, "getCodecInfos(...)");
            for (Object element$iv : codecInfos) {
                MediaCodecInfo it = (MediaCodecInfo) element$iv;
                if (it.isEncoder()) {
                    continue;
                }
                Object[] supportedTypes = it.getSupportedTypes();
                Intrinsics.checkNotNullExpressionValue(supportedTypes, "getSupportedTypes(...)");
                for (Object element$iv2 : supportedTypes) {
                    if (StringsKt.equals((String) element$iv2, "video/hevc", true)) {
                        return true;
                    }
                }
            }
            return false;
        } catch (Throwable th) {
            return false;
        }
    }

    public static /* synthetic */ byte[] nalOf$default(byte[] bArr, int i, boolean z, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            z = false;
        }
        return nalOf(bArr, i, z);
    }

    public static final byte[] nalOf(byte[] d, int type, boolean hevc) {
        Intrinsics.checkNotNullParameter(d, "d");
        int i = 0;
        int n = d.length;
        Ref.IntRef start = new Ref.IntRef();
        start.element = -1;
        Ref.IntRef hdrLen = new Ref.IntRef();
        while (i + 3 < n) {
            if (d[i] == 0 && d[i + 1] == 0 && (d[i + 2] == 1 || (d[i + 2] == 0 && i + 3 < n && d[i + 3] == 1))) {
                int sc = d[i + 2] == 1 ? 3 : 4;
                byte[] it = nalOf$flush(start, hdrLen, n, d, hevc, type, i);
                if (it != null) {
                    return it;
                }
                start.element = i;
                hdrLen.element = sc;
                i += sc;
            } else {
                i++;
            }
        }
        return nalOf$flush(start, hdrLen, n, d, hevc, type, n);
    }

    private static final byte[] nalOf$flush(Ref.IntRef start, Ref.IntRef hdrLen, int n, byte[] $d, boolean $hevc, int $type, int end) {
        if (start.element < 0 || start.element + hdrLen.element >= n) {
            return null;
        }
        int b0 = $d[start.element + hdrLen.element] & 255;
        int nalType = $hevc ? (b0 >> 1) & 63 : b0 & 31;
        if (nalType == $type) {
            return ArraysKt.copyOfRange($d, start.element, end);
        }
        return null;
    }
}
