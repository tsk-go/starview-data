package com.starpilot.starview;

import androidx.core.graphics.ColorUtils;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: OverlayView.kt */
/* synthetic */ class OverlayView$Companion$cscColor$b$1 extends FunctionReferenceImpl implements Function3<Integer, Integer, Float, Integer> {
    public static final OverlayView$Companion$cscColor$b$1 INSTANCE = new OverlayView$Companion$cscColor$b$1();

    OverlayView$Companion$cscColor$b$1() {
        super(3, ColorUtils.class, "blendARGB", "blendARGB(IIF)I", 0);
    }

    public final Integer invoke(int p0, int p1, float p2) {
        return Integer.valueOf(ColorUtils.blendARGB(p0, p1, p2));
    }

    @Override // kotlin.jvm.functions.Function3
    public /* bridge */ /* synthetic */ Integer invoke(Integer num, Integer num2, Float f) {
        return invoke(num.intValue(), num2.intValue(), f.floatValue());
    }
}
