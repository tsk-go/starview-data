package com.starpilot.starview;

import android.view.View;

/* compiled from: HudViews.kt */
public final class HudViewsKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final float dp(View $this$dp, float v) {
        return $this$dp.getResources().getDisplayMetrics().density * v;
    }
}
