package com.starpilot.starview;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: GxLogs.kt */
final /* data */ class GxLogsProc {
    private final Double cpu;
    private final String feature;
    private final boolean kernel;
    private final Double mem;
    private final String name;
    private final int pid;
    private final String state;
    private final String user;








    public final GxLogsProc copy(int pid, String name, String user, boolean kernel, String state, Double cpu, Double mem, String feature) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(user, "user");
        Intrinsics.checkNotNullParameter(state, "state");
        Intrinsics.checkNotNullParameter(feature, "feature");
        return new GxLogsProc(pid, name, user, kernel, state, cpu, mem, feature);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof GxLogsProc)) {
            return false;
        }
        GxLogsProc gxLogsProc = (GxLogsProc) other;
        return this.pid == gxLogsProc.pid && Intrinsics.areEqual(this.name, gxLogsProc.name) && Intrinsics.areEqual(this.user, gxLogsProc.user) && this.kernel == gxLogsProc.kernel && Intrinsics.areEqual(this.state, gxLogsProc.state) && Intrinsics.areEqual((Object) this.cpu, (Object) gxLogsProc.cpu) && Intrinsics.areEqual((Object) this.mem, (Object) gxLogsProc.mem) && Intrinsics.areEqual(this.feature, gxLogsProc.feature);
    }

    public int hashCode() {
        return (((((((((((((Integer.hashCode(this.pid) * 31) + this.name.hashCode()) * 31) + this.user.hashCode()) * 31) + Boolean.hashCode(this.kernel)) * 31) + this.state.hashCode()) * 31) + (this.cpu == null ? 0 : this.cpu.hashCode())) * 31) + (this.mem != null ? this.mem.hashCode() : 0)) * 31) + this.feature.hashCode();
    }

    public String toString() {
        return "GxLogsProc(pid=" + this.pid + ", name=" + this.name + ", user=" + this.user + ", kernel=" + this.kernel + ", state=" + this.state + ", cpu=" + this.cpu + ", mem=" + this.mem + ", feature=" + this.feature + ")";
    }

    public GxLogsProc(int pid, String name, String user, boolean kernel, String state, Double cpu, Double mem, String feature) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(user, "user");
        Intrinsics.checkNotNullParameter(state, "state");
        Intrinsics.checkNotNullParameter(feature, "feature");
        this.pid = pid;
        this.name = name;
        this.user = user;
        this.kernel = kernel;
        this.state = state;
        this.cpu = cpu;
        this.mem = mem;
        this.feature = feature;
    }

    public final Double getCpu() {
        return this.cpu;
    }

    public final String getFeature() {
        return this.feature;
    }

    public final boolean getKernel() {
        return this.kernel;
    }

    public final Double getMem() {
        return this.mem;
    }

    public final String getName() {
        return this.name;
    }

    public final int getPid() {
        return this.pid;
    }

    public final String getState() {
        return this.state;
    }

    public final String getUser() {
        return this.user;
    }
}
