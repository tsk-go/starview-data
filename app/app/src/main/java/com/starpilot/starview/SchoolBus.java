package com.starpilot.starview;

import java.util.HashMap;
import kotlin.collections.SetsKt;

/* compiled from: IdeasMore.kt */
public final class SchoolBus {
    private final LeadLamps lamps = new LeadLamps();
    private final HashMap<Long, Float> yellow = new HashMap<>();

    public final void feed(long id, long nowMs, float x, float yellowFrac, float redL, float redR) {
        Float f = this.yellow.get(Long.valueOf(id));
        float y = ((f != null ? f.floatValue() : yellowFrac) * 0.8f) + (0.2f * yellowFrac);
        this.yellow.put(Long.valueOf(id), Float.valueOf(y));
        if (this.yellow.size() > 20) {
            this.yellow.keySet().retainAll(SetsKt.setOf(Long.valueOf(id)));
        }
        this.lamps.feed(id, nowMs, redL, redR);
        if (y <= 0.3f || !this.lamps.anyBlink(nowMs)) {
            return;
        }
        Ideas.INSTANCE.getMore().setBusAtMs(nowMs);
        Ideas.INSTANCE.getMore().setBusX(x);
    }
}
