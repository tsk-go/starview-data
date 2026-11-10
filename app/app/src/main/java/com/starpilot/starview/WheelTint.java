package com.starpilot.starview;

import kotlin.math.MathKt;
import kotlin.ranges.RangesKt;

/* compiled from: BigUiViews.kt */
public final class WheelTint {
    private float amount;
    private long lastNs;
    private float target;

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0022, code lost:
    
        if (r13 >= r10.lastNs) goto L15;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int update(float accel, boolean enabled, long nowNs) {
        if (this.lastNs != 0 && enabled) {
            if ((Float.isInfinite(accel) || Float.isNaN(accel)) ? false : true) {
            }
        }
        this.target = 0.0f;
        this.amount = 0.0f;
        this.lastNs = nowNs;
        float tgt = 0.0f;
        if (enabled) {
            if ((Float.isInfinite(accel) || Float.isNaN(accel)) ? false : true) {
                float threshold = this.target * accel > 0.0f ? 0.15f : 0.25f;
                if (Math.abs(accel) > threshold) {
                    tgt = RangesKt.coerceIn(accel / 2.0f, -1.0f, 1.0f);
                }
            }
        }
        float dt = (nowNs - this.lastNs) / 1.0E9f;
        this.lastNs = nowNs;
        this.target = tgt;
        this.amount += (tgt - this.amount) * (1.0f - ((float) Math.exp((-dt) / 0.08f)));
        int[] end = this.amount < 0.0f ? new int[]{255, 0, 0} : new int[]{22, 127, 64};
        float a = Math.abs(this.amount);
        return (update$ch(end, a, 1) << 8) | (update$ch(end, a, 0) << 16) | (-16777216) | update$ch(end, a, 2);
    }

    private static final int update$ch(int[] end, float a, int i) {
        return RangesKt.coerceIn(MathKt.roundToInt(255 + ((end[i] - 255) * a)), 0, 255);
    }
}
