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
    /* JADX WARN: Removed duplicated region for block: B:17:0x0053 A[LOOP:0: B:4:0x0016->B:17:0x0053, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0051 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final boolean hevcDecoder_delegate$lambda$2() {
        boolean z;
        boolean z2;
        int i = 0;
        try {
            Object[] codecInfos = new MediaCodecList(0).getCodecInfos();
            Intrinsics.checkNotNullExpressionValue(codecInfos, "getCodecInfos(...)");
            Object[] $this$any$iv = codecInfos;
            int length = $this$any$iv.length;
            int i2 = 0;
            while (i2 < length) {
                Object element$iv = $this$any$iv[i2];
                MediaCodecInfo it = (MediaCodecInfo) element$iv;
                if (!it.isEncoder()) {
                    Object[] supportedTypes = it.getSupportedTypes();
                    Intrinsics.checkNotNullExpressionValue(supportedTypes, "getSupportedTypes(...)");
                    Object[] $this$any$iv2 = supportedTypes;
                    int length2 = $this$any$iv2.length;
                    int i3 = i;
                    while (true) {
                        if (i3 >= length2) {
                            z2 = false;
                            break;
                        }
                        Object element$iv2 = $this$any$iv2[i3];
                        String t = (String) element$iv2;
                        if (StringsKt.equals(t, "video/hevc", true)) {
                            z2 = true;
                            break;
                        }
                        i3++;
                    }
                    if (z2) {
                        z = true;
                        if (!z) {
                            return true;
                        }
                        i2++;
                        i = 0;
                    }
                }
                z = false;
                if (!z) {
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
