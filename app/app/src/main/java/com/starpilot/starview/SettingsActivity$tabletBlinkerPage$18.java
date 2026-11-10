package com.starpilot.starview;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SettingsActivity.kt */
/* synthetic */ class SettingsActivity$tabletBlinkerPage$18 extends FunctionReferenceImpl implements Function1<Float, String> {
    public static final SettingsActivity$tabletBlinkerPage$18 INSTANCE = new SettingsActivity$tabletBlinkerPage$18();

    SettingsActivity$tabletBlinkerPage$18() {
        super(1, Intrinsics.Kotlin.class, "pctText", "tabletBlinkerPage$pctText(F)Ljava/lang/String;", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ String invoke(Float f) {
        return invoke(f.floatValue());
    }

    public final String invoke(float p0) {
        String tabletBlinkerPage$pctText;
        tabletBlinkerPage$pctText = SettingsActivity.tabletBlinkerPage$pctText(p0);
        return tabletBlinkerPage$pctText;
    }
}
