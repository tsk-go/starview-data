package com.starpilot.starview;

import kotlin.collections.ArrayDeque;

/* compiled from: Telemetry.kt */
public final class Rate {
    private final ArrayDeque<Long> q = new ArrayDeque<>();

    public final synchronized void tick(long now) {
        this.q.addLast(Long.valueOf(now));
        trim(now);
    }

    public final synchronized float hz(long now) {
        trim(now);
        return this.q.size() / 5.0f;
    }

    private final void trim(long now) {
        while (!this.q.isEmpty() && now - this.q.first().longValue() > 5000000000L) {
            this.q.removeFirst();
        }
    }
}
