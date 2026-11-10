package com.starpilot.starview;

import java.util.List;
import kotlin.Unit;
import kotlin.collections.ArrayDeque;
import kotlin.collections.CollectionsKt;

/* compiled from: JournalPages.kt */
public final class HealthHistory {
    private static long lastMs;
    public static final HealthHistory INSTANCE = new HealthHistory();
    private static final ArrayDeque<Point> ring = new ArrayDeque<>();

    /* compiled from: JournalPages.kt */
    public static final class Point {
        private final int memPct;
        private final long tMs;
        private final float tempC;

        public Point(long tMs, int memPct, float tempC) {
            this.tMs = tMs;
            this.memPct = memPct;
            this.tempC = tempC;
        }

        public final int getMemPct() {
            return this.memPct;
        }

        public final long getTMs() {
            return this.tMs;
        }

        public final float getTempC() {
            return this.tempC;
        }
    }

    private HealthHistory() {
    }

    public final void add(int memPct, float tempC) {
        long now = System.currentTimeMillis();
        if (now - lastMs < 10000) {
            return;
        }
        lastMs = now;
        synchronized (ring) {
            ring.addLast(new Point(now, memPct, tempC));
            while (ring.size() > 720) {
                ring.removeFirst();
            }
            Unit unit = Unit.INSTANCE;
        }
    }

    public final List<Point> points() {
        List<Point> list;
        synchronized (ring) {
            list = CollectionsKt.toList(ring);
        }
        return list;
    }
}
