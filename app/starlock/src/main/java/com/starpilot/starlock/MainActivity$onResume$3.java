package com.starpilot.starlock;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.AdaptedFunctionReference;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MainActivity.kt */
/* synthetic */ class MainActivity$onResume$3 extends AdaptedFunctionReference implements Function1 {
    MainActivity$onResume$3(Object obj) {
        super(1, obj, MainActivity.class, "log", "log(Ljava/lang/String;)Z", 8);
    }

    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ Object invoke(Object str) {
        invoke2((String) str);
        return Unit.INSTANCE;
    }

    /* renamed from: invoke, reason: avoid collision after fix types in other method */
    public final void invoke2(String p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        ((MainActivity) this.receiver).log(p0);
    }
}
