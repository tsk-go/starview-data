package com.starpilot.starview;

import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: Telemetry.kt */
/* synthetic */ class Telemetry$count$1 extends FunctionReferenceImpl implements Function2<Long, Long, Long> {
    public static final Telemetry$count$1 INSTANCE = new Telemetry$count$1();

    Telemetry$count$1() {
        super(2, Long.TYPE, "plus", "plus(J)J", 0);
    }

    public final Long invoke(long p0, long p1) {
        return Long.valueOf(p0 + p1);
    }

    @Override // kotlin.jvm.functions.Function2
    public /* bridge */ /* synthetic */ Long invoke(Long l, Long l2) {
        return invoke(l.longValue(), l2.longValue());
    }
}
