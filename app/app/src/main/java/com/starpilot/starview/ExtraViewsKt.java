package com.starpilot.starview;

import android.view.View;

/* compiled from: ExtraViews.kt */
public final class ExtraViewsKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final float dpf(View $this$dpf, float v) {
        return $this$dpf.getResources().getDisplayMetrics().density * v;
    }
}
