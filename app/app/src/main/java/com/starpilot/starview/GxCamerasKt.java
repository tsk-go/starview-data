package com.starpilot.starview;

import android.graphics.Color;
import kotlin.math.MathKt;

/* compiled from: GxCameras.kt */
public final class GxCamerasKt {
    private static final String GX_CAM_LEFT = "LEFT SIDE OF VEHICLE - LEFT HERE";
    private static final String GX_CAM_RIGHT = "RIGHT SIDE OF VEHICLE - RIGHT HERE";
    static final int GX_CAM_BLUE = Color.rgb(13, 110, 253);
    static final int GX_CAM_ORANGE = Color.rgb(253, 126, 20);
    static final int GX_CAM_RED = Color.rgb(255, 99, 99);

    /* JADX INFO: Access modifiers changed from: private */
    public static final int gxCamRound(double $this$gxCamRound) {
        if ((Double.isInfinite($this$gxCamRound) || Double.isNaN($this$gxCamRound)) ? false : true) {
            return MathKt.roundToInt($this$gxCamRound);
        }
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int gxCamRound(float $this$gxCamRound) {
        if ((Float.isInfinite($this$gxCamRound) || Float.isNaN($this$gxCamRound)) ? false : true) {
            return MathKt.roundToInt($this$gxCamRound);
        }
        return 0;
    }
}
