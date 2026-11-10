package com.starpilot.starview;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SettingsActivity.kt */
/* synthetic */ class SettingsActivity$tabletBlinkerPage$15 extends FunctionReferenceImpl implements Function1 {
    public static final SettingsActivity$tabletBlinkerPage$15 INSTANCE = new SettingsActivity$tabletBlinkerPage$15();

    SettingsActivity$tabletBlinkerPage$15() {
        super(1, Intrinsics.Kotlin.class, "pctText", "tabletBlinkerPage$pctText(F)Ljava/lang/String;", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Object invoke(Object f$o) {
        Float f = (Float) f$o;
        return invoke(f.floatValue());
    }

    public final String invoke(float p0) {
        String tabletBlinkerPage$pctText;
        tabletBlinkerPage$pctText = SettingsActivity.tabletBlinkerPage$pctText(p0);
        return tabletBlinkerPage$pctText;
    }
}
