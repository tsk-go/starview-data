package com.starpilot.starview;

import android.graphics.Insets;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowInsets;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: EdgeGuard.kt */
public final class EdgeGuard {
    public static final EdgeGuard INSTANCE = new EdgeGuard();

    private EdgeGuard() {
    }

    public final boolean inSystemGestureZone(View v, float x, float y) {
        Insets g;
        Intrinsics.checkNotNullParameter(v, "v");
        float d = v.getResources().getDisplayMetrics().density;
        float bottom = 64 * d;
        float side = 28 * d;
        float top = 36 * d;
        try {
            WindowInsets rootWindowInsets = v.getRootWindowInsets();
            if (rootWindowInsets != null && (g = rootWindowInsets.getSystemGestureInsets()) != null) {
                bottom = Math.max(bottom, g.bottom * 2.0f);
                side = Math.max(side, Math.max(g.left, g.right));
                top = Math.max(top, g.top);
            }
        } catch (Throwable th) {
        }
        int[] loc = new int[2];
        v.getLocationOnScreen(loc);
        float sx = loc[0] + x;
        float sy = loc[1] + y;
        DisplayMetrics dm = v.getResources().getDisplayMetrics();
        return sy > ((float) dm.heightPixels) - bottom || sy < top || sx < side || sx > ((float) dm.widthPixels) - side;
    }
}
