package com.starpilot.starview;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: RtkCore.kt */
public final /* data */ class RtkSensor {
    public static final Companion Companion = new Companion(null);
    private final String calib;
    private final List<String> faults;
    private final int hz;
    private final boolean ready;
    private final String type;
    private final boolean used;

    public static /* synthetic */ RtkSensor copy$default(RtkSensor rtkSensor, String str, boolean z, boolean z2, String str2, int i, List list, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = rtkSensor.type;
        }
        if ((i2 & 2) != 0) {
            z = rtkSensor.used;
        }
        boolean z3 = z;
        if ((i2 & 4) != 0) {
            z2 = rtkSensor.ready;
        }
        boolean z4 = z2;
        if ((i2 & 8) != 0) {
            str2 = rtkSensor.calib;
        }
        String str3 = str2;
        if ((i2 & 16) != 0) {
            i = rtkSensor.hz;
        }
        int i3 = i;
        if ((i2 & 32) != 0) {
            list = rtkSensor.faults;
        }
        return rtkSensor.copy(str, z3, z4, str3, i3, list);
    }

    /* renamed from: component1, reason: from getter */
    public final String getType() {
        return this.type;
    }

    /* renamed from: component2, reason: from getter */
    public final boolean getUsed() {
        return this.used;
    }

    /* renamed from: component3, reason: from getter */
    public final boolean getReady() {
        return this.ready;
    }

    /* renamed from: component4, reason: from getter */
    public final String getCalib() {
        return this.calib;
    }

    /* renamed from: component5, reason: from getter */
    public final int getHz() {
        return this.hz;
    }

    public final List<String> component6() {
        return this.faults;
    }

    public final RtkSensor copy(String type, boolean used, boolean ready, String calib, int hz, List<String> faults) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(calib, "calib");
        Intrinsics.checkNotNullParameter(faults, "faults");
        return new RtkSensor(type, used, ready, calib, hz, faults);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof RtkSensor)) {
            return false;
        }
        RtkSensor rtkSensor = (RtkSensor) other;
        return Intrinsics.areEqual(this.type, rtkSensor.type) && this.used == rtkSensor.used && this.ready == rtkSensor.ready && Intrinsics.areEqual(this.calib, rtkSensor.calib) && this.hz == rtkSensor.hz && Intrinsics.areEqual(this.faults, rtkSensor.faults);
    }

    public int hashCode() {
        return (((((((((this.type.hashCode() * 31) + Boolean.hashCode(this.used)) * 31) + Boolean.hashCode(this.ready)) * 31) + this.calib.hashCode()) * 31) + Integer.hashCode(this.hz)) * 31) + this.faults.hashCode();
    }

    public String toString() {
        return "RtkSensor(type=" + this.type + ", used=" + this.used + ", ready=" + this.ready + ", calib=" + this.calib + ", hz=" + this.hz + ", faults=" + this.faults + ")";
    }

    /* compiled from: RtkCore.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* VF: from Vineflower (jadx: Type inference failed) */
        public final RtkSensor parse(JSONObject var1) {
            if (var1 == null) {
                return null;
            } else {
                JSONArray var7 = var1.optJSONArray("faults");
                String var5 = var1.optString("type", "?");
                Intrinsics.checkNotNullExpressionValue(var5, "optString(...)");
                boolean var3 = var1.optBoolean("used");
                boolean var4 = var1.optBoolean("ready");
                String var6 = var1.optString("calib", "");
                Intrinsics.checkNotNullExpressionValue(var6, "optString(...)");
                int var2 = var1.optInt("hz", 0);
                List var9;
                if (var7 == null) {
                    var9 = CollectionsKt.emptyList();
                } else {
                    Iterable var8 = RangesKt.until(0, var7.length());
                    var var10 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var8, 10));
                    Iterator var11 = var8.iterator();

                    while (var11.hasNext()) {
                        var10.add(var7.optString(((IntIterator)var11).nextInt()));
                    }

                    var9 = var10;
                }

                return new RtkSensor(var5, var3, var4, var6, var2, var9);
            }
        }
    }

    public RtkSensor(String type, boolean used, boolean ready, String calib, int hz, List<String> faults) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(calib, "calib");
        Intrinsics.checkNotNullParameter(faults, "faults");
        this.type = type;
        this.used = used;
        this.ready = ready;
        this.calib = calib;
        this.hz = hz;
        this.faults = faults;
    }

    public final String getCalib() {
        return this.calib;
    }

    public final List<String> getFaults() {
        return this.faults;
    }

    public final int getHz() {
        return this.hz;
    }

    public final boolean getReady() {
        return this.ready;
    }

    public final String getType() {
        return this.type;
    }

    public final boolean getUsed() {
        return this.used;
    }
}
