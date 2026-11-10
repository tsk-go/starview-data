package com.starpilot.starview;

import android.content.Context;
import android.content.res.Configuration;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

/* compiled from: NarrowDensity.kt */
public final class NarrowDensity {
    public static final NarrowDensity INSTANCE = new NarrowDensity();

    private NarrowDensity() {
    }

    public static /* synthetic */ Context wrap$default(NarrowDensity narrowDensity, Context context, int i, float f, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = 1000;
        }
        if ((i2 & 4) != 0) {
            f = 0.62f;
        }
        return narrowDensity.wrap(context, i, f);
    }

    public final Context wrap(Context base, int targetDp, float minScale) {
        Intrinsics.checkNotNullParameter(base, "base");
        Configuration cfg = new Configuration(base.getResources().getConfiguration());
        int w = cfg.screenWidthDp;
        if (w <= 0 || w >= targetDp || cfg.densityDpi <= 0) {
            return base;
        }
        float k = Math.max(minScale, w / targetDp);
        cfg.densityDpi = RangesKt.coerceAtLeast((int) (cfg.densityDpi * k), 80);
        cfg.screenWidthDp = (int) (w / k);
        cfg.screenHeightDp = (int) (cfg.screenHeightDp / k);
        cfg.smallestScreenWidthDp = (int) (cfg.smallestScreenWidthDp / k);
        Context createConfigurationContext = base.createConfigurationContext(cfg);
        Intrinsics.checkNotNullExpressionValue(createConfigurationContext, "createConfigurationContext(...)");
        return createConfigurationContext;
    }
}
