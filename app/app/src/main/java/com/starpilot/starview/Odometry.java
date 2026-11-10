package com.starpilot.starview;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.Iterator;
import kotlin.Unit;
import kotlin.collections.ArrayDeque;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

/* compiled from: Odometry.kt */
public final class Odometry {
    private static final long HIST_NS = 8000000000L;
    private static double dist;
    private static int gyroAgree;
    private static long lastMono;
    private static volatile SharedPreferences prefs;
    private static int savedAgree;
    private static double x;
    private static double y;
    private static double yaw;
    public static final Odometry INSTANCE = new Odometry();
    private static final ArrayDeque<Pose> hist = new ArrayDeque<>();
    private static final Object lock = new Object();
    private static volatile String yawSrc = "steering";

    private Odometry() {
    }

    /* compiled from: Odometry.kt */
    private static final class Pose {
        private final double dist;
        private final long mono;
        private final long tNs;
        private final double v;
        private final double x;
        private final double y;
        private final double yaw;
        private final double yawRate;

        public Pose(long tNs, double x, double y, double yaw, double v, double yawRate, double dist, long mono) {
            this.tNs = tNs;
            this.x = x;
            this.y = y;
            this.yaw = yaw;
            this.v = v;
            this.yawRate = yawRate;
            this.dist = dist;
            this.mono = mono;
        }

        public /* synthetic */ Pose(long j, double d, double d2, double d3, double d4, double d5, double d6, long j2, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(j, d, d2, d3, d4, d5, d6, (i & 128) != 0 ? 0L : j2);
        }

        public final double getDist() {
            return this.dist;
        }

        public final long getMono() {
            return this.mono;
        }

        public final long getTNs() {
            return this.tNs;
        }

        public final double getV() {
            return this.v;
        }

        public final double getX() {
            return this.x;
        }

        public final double getY() {
            return this.y;
        }

        public final double getYaw() {
            return this.yaw;
        }

        public final double getYawRate() {
            return this.yawRate;
        }
    }

    public final String getYawSrc() {
        return yawSrc;
    }

    public final void init(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        SharedPreferences p = ctx.getApplicationContext().getSharedPreferences("starview", 0);
        prefs = p;
        gyroAgree = RangesKt.coerceIn(p.getInt("gyro_agree", 0), -50, 50);
        savedAgree = gyroAgree;
    }

    private final void saveAgree() {
        SharedPreferences p = prefs;
        if (p == null) {
            return;
        }
        if ((gyroAgree > 5) == (savedAgree > 5)) {
            if ((gyroAgree < -5) == (savedAgree < -5) && Math.abs(gyroAgree - savedAgree) < 20) {
                return;
            }
        }
        savedAgree = gyroAgree;
        p.edit().putInt("gyro_agree", gyroAgree).apply();
    }

    public final int getGyroSign() {
        return gyroAgree > 5 ? 1 : -1;
    }

    public final void onCarState(long monoNs, Telemetry t) {
        double it;
        String src;
        Intrinsics.checkNotNullParameter(t, "t");
        long now = System.nanoTime();
        if (lastMono == 0) {
            it = 0.0d;
        } else {
            it = (monoNs - lastMono) / 1.0E9d;
            if (it < 0.0d || it > 1.0d) {
                it = 0.0d;
            }
        }
        double dt = it;
        lastMono = monoNs;
        double v = Intrinsics.areEqual(t.getGear(), "reverse") ? -Math.abs(t.getVEgo()) : RangesKt.coerceAtLeast(t.getVEgo(), 0.0d);
        double sr = t.getSteerRatioLive() > 5.0f ? t.getSteerRatioLive() : 15.5d;
        double steerYaw = ((Math.tan(Math.toRadians(t.getSteeringAngleDeg() - t.getAngleOffsetDeg()) / sr) * v) / 3.03d) / (1 + ((v * v) / 900.0d));
        double gz = t.getPoseYawZ();
        double yawRate = steerYaw;
        if (((Double.isInfinite(gz) || Double.isNaN(gz)) ? false : true) && now - t.getPoseAtNs() < 500000000) {
            if (v > 3.0d && Math.abs(steerYaw) > 0.04d && Math.abs(gz) > 0.04d) {
                gyroAgree = RangesKt.coerceIn(gyroAgree + (gz * steerYaw <= 0.0d ? -1 : 1), -50, 50);
                saveAgree();
            }
            yawRate = gyroAgree > 5 ? gz : -gz;
            String src2 = Math.abs(gyroAgree) > 5 ? "gyro" : "gyro (direction not learned yet)";
            src = src2;
        } else {
            src = "steering";
        }
        if (Math.abs(v) < 0.2d) {
            yawRate = 0.0d;
        }
        double yawRate2 = yawRate;
        yawSrc = src;
        synchronized (lock) {
            try {
                yaw += yawRate2 * dt;
                x += Math.cos(yaw) * v * dt;
                y += Math.sin(yaw) * v * dt;
                dist += Math.abs(v) * dt;
                try {
                    hist.addLast(new Pose(now, x, y, yaw, v, yawRate2, dist, monoNs));
                    while (hist.size() > 2 && now - hist.first().getTNs() > 8000000000L) {
                        hist.removeFirst();
                    }
                    Unit unit = Unit.INSTANCE;
                } catch (Throwable th) {
                    th = th;
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        }
    }

    public final double[] motion() {
        double[] dArr;
        synchronized (lock) {
            Pose it = hist.lastOrNull();
            dArr = it != null ? new double[]{it.getV(), it.getYawRate()} : new double[]{0.0d, 0.0d};
        }
        return dArr;
    }

    public final Long monoToLocal(long monoNs) {
        Long valueOf;
        synchronized (lock) {
            long off = Long.MAX_VALUE;
            Iterator it = hist.iterator();
            while (it.hasNext()) {
                Pose p = (Pose) it.next();
                if (p.getMono() != 0) {
                    off = Math.min(off, p.getTNs() - p.getMono());
                }
            }
            valueOf = off == Long.MAX_VALUE ? null : Long.valueOf(monoNs + off);
        }
        return valueOf;
    }

    public final double[] at(long t) {
        synchronized (lock) {
            if (hist.isEmpty()) {
                return new double[]{0.0d, 0.0d, 0.0d, 0.0d};
            }
            Pose last = hist.last();
            if (t >= last.getTNs()) {
                double dt = RangesKt.coerceIn((t - last.getTNs()) / 1.0E9d, 0.0d, 0.25d);
                double yw = last.getYaw() + (last.getYawRate() * dt);
                double mid = last.getYaw() + ((last.getYawRate() * dt) / 2);
                return new double[]{last.getX() + (last.getV() * Math.cos(mid) * dt), last.getY() + (last.getV() * Math.sin(mid) * dt), yw, last.getDist() + (last.getV() * dt)};
            }
            Pose first = hist.first();
            if (t <= first.getTNs()) {
                return new double[]{first.getX(), first.getY(), first.getYaw(), first.getDist()};
            }
            int i = hist.size() - 1;
            while (i > 0 && hist.get(i - 1).getTNs() > t) {
                i--;
            }
            Pose p = hist.get(i - 1);
            Pose q = hist.get(i);
            double k = (t - p.getTNs()) / Math.max(1L, q.getTNs() - p.getTNs());
            return new double[]{p.getX() + ((q.getX() - p.getX()) * k), p.getY() + ((q.getY() - p.getY()) * k), p.getYaw() + (Math.IEEEremainder(q.getYaw() - p.getYaw(), 6.283185307179586d) * k), p.getDist() + ((q.getDist() - p.getDist()) * k)};
        }
    }
}
