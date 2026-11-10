package com.starpilot.starview;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.ArrayDeque;
import kotlin.collections.CollectionsKt;

/* compiled from: IdeasSense.kt */
public final class LeadLamps {
    private volatile boolean enabled;
    private long id;
    private final ArrayDeque<float[]> l = new ArrayDeque<>();
    private final ArrayDeque<float[]> r = new ArrayDeque<>();
    private long sideMs;
    private int sideNow;

    public final boolean getEnabled() {
        return this.enabled;
    }

    public final void setEnabled(boolean z) {
        this.enabled = z;
    }

    public final synchronized void feed(long trackId, long nowMs, float left, float right) {
        if (trackId != this.id) {
            this.id = trackId;
            this.l.clear();
            this.r.clear();
        }
        int i = 0;
        this.l.addLast(new float[]{nowMs % 10000000, left});
        this.r.addLast(new float[]{nowMs % 10000000, right});
        while (this.l.size() > 60) {
            this.l.removeFirst();
            this.r.removeFirst();
        }
        Pair bl = blinks(this.l);
        Pair br = blinks(this.r);
        if (((Number) bl.getFirst()).intValue() >= 3 && ((Number) bl.getSecond()).floatValue() > ((Number) br.getSecond()).floatValue() * 2.0f) {
            i = -1;
        } else if (((Number) br.getFirst()).intValue() >= 3 && ((Number) br.getSecond()).floatValue() > ((Number) bl.getSecond()).floatValue() * 2.0f) {
            i = 1;
        }
        int s = i;
        if (s != 0) {
            this.sideNow = s;
            this.sideMs = nowMs;
        }
    }

    public final synchronized int side(long nowMs) {
        return nowMs - this.sideMs < 1500 ? this.sideNow : 0;
    }

    public final synchronized boolean anyBlink(long nowMs) {
        boolean z;
        Pair bl = blinks(this.l);
        Pair br = blinks(this.r);
        if (((Number) bl.getFirst()).intValue() < 2) {
            z = ((Number) br.getFirst()).intValue() >= 2;
        } else {
            z = true;
        }
        return z;
    }

    private final Pair<Integer, Float> blinks(ArrayDeque<float[]> q) {
        int size = q.size();
        Float valueOf = Float.valueOf(0.0f);
        if (size < 12) {
            return TuplesKt.to(0, valueOf);
        }
        float tEnd = q.last()[0];
        ArrayDeque<float[]> $this$filter$iv = q;
        Collection destination$iv$iv = new ArrayList();
        Iterator<float[]> it = $this$filter$iv.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            float[] next = it.next();
            float[] it2 = next;
            if (tEnd - it2[0] <= 2500.0f) {
                destination$iv$iv.add(next);
            }
        }
        List<float[]> pts = (List) destination$iv$iv;
        if (pts.size() < 10) {
            return TuplesKt.to(0, valueOf);
        }
        List $this$map$iv = pts;
        Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        for (Object item$iv$iv : $this$map$iv) {
            float[] it3 = (float[]) item$iv$iv;
            destination$iv$iv2.add(Float.valueOf(it3[1]));
        }
        List vals = CollectionsKt.sorted((List) destination$iv$iv2);
        float lo = ((Number) vals.get(vals.size() / 10)).floatValue();
        float hi = ((Number) vals.get((vals.size() * 9) / 10)).floatValue();
        float amp = hi - lo;
        if (amp < 14.0f) {
            return TuplesKt.to(0, Float.valueOf(amp));
        }
        float mid = (lo + hi) / 2.0f;
        int ons = 0;
        float lastOn = -1.0E9f;
        boolean above = ((float[]) pts.get(0))[1] > mid;
        for (float[] p : pts) {
            boolean a = p[1] > mid;
            if (a && !above) {
                float gap = p[0] - lastOn;
                if (lastOn >= 0.0f) {
                    if (!(350.0f <= gap && gap <= 1100.0f)) {
                        ons = 1;
                        lastOn = p[0];
                    }
                }
                ons++;
                lastOn = p[0];
            }
            above = a;
        }
        return TuplesKt.to(Integer.valueOf(ons), Float.valueOf(amp));
    }
}
