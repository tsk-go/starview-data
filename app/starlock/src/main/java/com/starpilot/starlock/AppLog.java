package com.starpilot.starlock;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: Door.kt */
public final class AppLog {
    public static final AppLog INSTANCE = new AppLog();
    private static volatile Function1<? super String, Unit> sink;

    private AppLog() {
    }

    public final Function1<String, Unit> getSink() {
        return (Function1) sink;
    }

    public final void log(String s) {
        Intrinsics.checkNotNullParameter(s, "s");
        Function1<? super String, Unit> function1 = sink;
        if (function1 != null) {
            function1.invoke(s);
        }
    }

    public final void setSink(Function1<? super String, Unit> function1) {
        sink = function1;
    }
}
