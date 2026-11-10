package com.starpilot.starview;

import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.json.JSONObject;

/* compiled from: NudgeSync.kt */
public final class NudgeSync {
    private static volatile Integer commaPct;
    private static volatile long retryAt;
    private static volatile boolean wasConnected;
    public static final NudgeSync INSTANCE = new NudgeSync();
    private static volatile int sent = -1;

    private NudgeSync() {
    }

    public final Integer getCommaPct() {
        return commaPct;
    }

    public final void push(float pct) {
        sent = -1;
        retryAt = 0L;
        tick(pct);
    }

    public final void tick(float pct) {
        DataClient dc = Link.INSTANCE.getData();
        boolean up = dc != null && dc.getConnected();
        if (!up) {
            wasConnected = false;
            return;
        }
        final int p = RangesKt.coerceIn(Math.round(pct), 30, 150);
        long now = System.currentTimeMillis();
        if (!(wasConnected && sent == p) && now >= retryAt) {
            wasConnected = true;
            sent = p;
            Intrinsics.checkNotNull(dc);
            dc.request("nudge_set", MapsKt.mapOf(TuplesKt.to("pct", Integer.valueOf(p))), new Function1() { // from class: com.starpilot.starview.NudgeSync$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    Unit tick$lambda$0;
                    tick$lambda$0 = NudgeSync.tick$lambda$0(p, (JSONObject) obj);
                    return tick$lambda$0;
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tick$lambda$0(int $p, JSONObject ack) {
        Intrinsics.checkNotNullParameter(ack, "ack");
        if (ack.optBoolean("ok")) {
            commaPct = Integer.valueOf(ack.optInt("pct", $p));
        } else {
            commaPct = null;
            sent = -1;
            retryAt = System.currentTimeMillis() + 60000;
        }
        return Unit.INSTANCE;
    }
}
