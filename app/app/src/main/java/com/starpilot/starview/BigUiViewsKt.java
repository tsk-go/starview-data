package com.starpilot.starview;

import android.view.View;

/* compiled from: BigUiViews.kt */
public final class BigUiViewsKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final float dpf(View $this$dpf, float v) {
        return $this$dpf.getResources().getDisplayMetrics().density * v;
    }

    public static final float ease(float cur, float target, float dt, float tau) {
        return ((target - cur) * (1.0f - ((float) Math.exp((-dt) / tau)))) + cur;
    }
}
