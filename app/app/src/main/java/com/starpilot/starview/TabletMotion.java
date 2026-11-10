package com.starpilot.starview;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedQueue;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

/* compiled from: TabletMotion.kt */
public final class TabletMotion implements SensorEventListener {
    public static final Companion Companion = new Companion(null);
    public static final float THRESHOLD = 3.2f;
    private volatile float aEgo;
    private final float[] fwd;
    private float fwdW;
    private final float[] g;
    private boolean gInit;
    private float hp;
    private final ConcurrentLinkedQueue<Jolt> jolts;
    private float[] lastGyro;
    private long lastJoltMs;
    private boolean on;
    private long peakAtMs;
    private float peakPitch;
    private float peakRoll;
    private float peakV;
    private float prevV;
    private final SensorManager sm;
    private volatile float speed;
    private volatile float yawRate;

    public TabletMotion(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Object systemService = ctx.getSystemService("sensor");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.hardware.SensorManager");
        this.sm = (SensorManager) systemService;
        this.g = new float[3];
        this.fwd = new float[3];
        this.lastGyro = new float[3];
        this.jolts = new ConcurrentLinkedQueue<>();
    }

    /* compiled from: TabletMotion.kt */
    public static final class Jolt {
        private final long atMs;
        private final String kind;
        private final int side;
        private final float size;

        public Jolt(String kind, int side, float size, long atMs) {
            Intrinsics.checkNotNullParameter(kind, "kind");
            this.kind = kind;
            this.side = side;
            this.size = size;
            this.atMs = atMs;
        }

        public final long getAtMs() {
            return this.atMs;
        }

        public final String getKind() {
            return this.kind;
        }

        public final int getSide() {
            return this.side;
        }

        public final float getSize() {
            return this.size;
        }
    }

    public final float getAEgo() {
        return this.aEgo;
    }

    public final float getSpeed() {
        return this.speed;
    }

    public final float getYawRate() {
        return this.yawRate;
    }

    public final void setAEgo(float f) {
        this.aEgo = f;
    }

    public final void setSpeed(float f) {
        this.speed = f;
    }

    public final void setYawRate(float f) {
        this.yawRate = f;
    }

    public final boolean getOriented() {
        return this.fwdW > 20.0f;
    }

    public final void start() {
        if (this.on) {
            return;
        }
        this.on = true;
        Sensor it = this.sm.getDefaultSensor(1);
        if (it != null) {
            this.sm.registerListener(this, it, 1);
        }
        Sensor it2 = this.sm.getDefaultSensor(4);
        if (it2 != null) {
            this.sm.registerListener(this, it2, 1);
        }
    }

    public final void stop() {
        if (this.on) {
            this.on = false;
            this.sm.unregisterListener(this);
            this.jolts.clear();
        }
    }

    public final Jolt poll() {
        return this.jolts.poll();
    }

    @Override // android.hardware.SensorEventListener
    public void onAccuracyChanged(Sensor s, int a) {
    }

    @Override // android.hardware.SensorEventListener
    public void onSensorChanged(SensorEvent e) {
        int i;
        Intrinsics.checkNotNullParameter(e, "e");
        if (e.sensor.getType() == 4) {
            float[] values = e.values;
            Intrinsics.checkNotNullExpressionValue(values, "values");
            float[] copyOf = Arrays.copyOf(values, values.length);
            Intrinsics.checkNotNullExpressionValue(copyOf, "copyOf(...)");
            this.lastGyro = copyOf;
            return;
        }
        float[] a = e.values;
        if (!this.gInit) {
            Intrinsics.checkNotNull(a);
            ArraysKt.copyInto(a, this.g, 0, 0, a.length);
            this.gInit = true;
            return;
        }
        int i2 = 0;
        while (true) {
            if (i2 >= 3) {
                break;
            }
            float[] fArr = this.g;
            fArr[i2] = fArr[i2] + ((a[i2] - this.g[i2]) * 0.01f);
            i2++;
        }
        float gn = RangesKt.coerceAtLeast((float) Math.sqrt((this.g[0] * this.g[0]) + (this.g[1] * this.g[1]) + (this.g[2] * this.g[2])), 1.0f);
        float[] up = new float[3];
        for (int i3 = 0; i3 < 3; i3++) {
            up[i3] = this.g[i3] / gn;
        }
        float vert = (((a[0] * up[0]) + (a[1] * up[1])) + (a[2] * up[2])) - gn;
        if (Math.abs(this.aEgo) > 0.8f && Math.abs(this.yawRate) < 0.05f && this.speed > 3.0f) {
            float[] h = new float[3];
            for (int i4 = 0; i4 < 3; i4++) {
                h[i4] = a[i4] - ((vert + gn) * up[i4]);
            }
            float s = this.aEgo <= 0.0f ? -1.0f : 1.0f;
            for (int i5 = 0; i5 < 3; i5++) {
                float[] fArr2 = this.fwd;
                fArr2[i5] = fArr2[i5] + (h[i5] * s * 0.02f);
            }
            this.fwdW = RangesKt.coerceAtMost(this.fwdW + (Math.abs(this.aEgo) * 0.02f), 1000.0f);
        }
        float s2 = this.hp;
        this.hp = ((s2 + vert) - this.prevV) * 0.8f;
        this.prevV = vert;
        long now = System.currentTimeMillis();
        if (this.speed < 2.5f || now - this.lastJoltMs < 1200) {
            return;
        }
        float pitch = 0.0f;
        float roll = 0.0f;
        if (getOriented()) {
            float fl = RangesKt.coerceAtLeast((float) Math.sqrt((this.fwd[0] * this.fwd[0]) + (this.fwd[1] * this.fwd[1]) + (this.fwd[2] * this.fwd[2])), 0.001f);
            float[] f = new float[3];
            int i6 = 0;
            for (i = 3; i6 < i; i = 3) {
                f[i6] = this.fwd[i6] / fl;
                i6++;
            }
            float[] left = {(up[1] * f[2]) - (up[2] * f[1]), (up[2] * f[0]) - (up[0] * f[2]), (up[0] * f[1]) - (up[1] * f[0])};
            roll = (this.lastGyro[0] * f[0]) + (this.lastGyro[1] * f[1]) + (this.lastGyro[2] * f[2]);
            pitch = (this.lastGyro[0] * left[0]) + (this.lastGyro[1] * left[1]) + (this.lastGyro[2] * left[2]);
        }
        if (Math.abs(this.hp) > Math.abs(this.peakV)) {
            this.peakV = this.hp;
            this.peakAtMs = now;
        }
        if (Math.abs(pitch) > Math.abs(this.peakPitch)) {
            this.peakPitch = pitch;
        }
        if (Math.abs(roll) > Math.abs(this.peakRoll)) {
            this.peakRoll = roll;
        }
        if (now - this.peakAtMs > 250) {
            if (Math.abs(this.peakV) > 3.2f) {
                String kind = !getOriented() ? "rough" : Math.abs(this.peakRoll) > Math.abs(this.peakPitch) * 1.3f ? "pothole" : "bump";
                int side = Intrinsics.areEqual(kind, "pothole") ? this.peakRoll <= 0.0f ? -1 : 1 : 0;
                this.jolts.add(new Jolt(kind, side, Math.abs(this.peakV), this.peakAtMs));
                this.lastJoltMs = now;
            }
            this.peakV = 0.0f;
            this.peakPitch = 0.0f;
            this.peakRoll = 0.0f;
            this.peakAtMs = now;
        }
    }

    /* compiled from: TabletMotion.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
