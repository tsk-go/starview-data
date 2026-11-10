package com.starpilot.starview;

import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: Telemetry.kt */
/* synthetic */ class Telemetry$count$2 extends FunctionReferenceImpl implements Function2 {
    public static final Telemetry$count$2 INSTANCE = new Telemetry$count$2();

    Telemetry$count$2() {
        super(2, Long.TYPE, "plus", "plus(J)J", 0);
    }

    public final Long invoke(long p0, long p1) {
        return Long.valueOf(p0 + p1);
    }

    @Override // kotlin.jvm.functions.Function2
    public /* bridge */ /* synthetic */ Object invoke(Object l$o, Object l2$o) {
        Long l = (Long) l$o;
        Long l2 = (Long) l2$o;
        return invoke(l.longValue(), l2.longValue());
    }
}
