package com.starpilot.starview;

import com.starpilot.starview.LaneTracks;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: LaneTracks.kt */
/* synthetic */ class LaneTracks$Companion$effortColor$b$1 extends FunctionReferenceImpl implements Function3 {
    LaneTracks$Companion$effortColor$b$1(Object obj) {
        super(3, obj, LaneTracks.Companion.class, "blend", "blend(IIF)I", 0);
    }

    public final Integer invoke(int p0, int p1, float p2) {
        return Integer.valueOf(((LaneTracks.Companion) this.receiver).blend(p0, p1, p2));
    }

    @Override // kotlin.jvm.functions.Function3
    public /* bridge */ /* synthetic */ Object invoke(Object num$o, Object num2$o, Object f$o) {
        Integer num = (Integer) num$o;
        Integer num2 = (Integer) num2$o;
        Float f = (Float) f$o;
        return invoke(num.intValue(), num2.intValue(), f.floatValue());
    }
}
