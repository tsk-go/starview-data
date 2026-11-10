package com.starpilot.starlock;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.json.JSONObject;

/* compiled from: CarLocks.kt */
public final class CarLocks {
    private static volatile State cached;
    private static volatile Function0<Unit> listener;
    public static final CarLocks INSTANCE = new CarLocks();
    private static final Handler main = new Handler(Looper.getMainLooper());
    private static final LinkedHashMap<String, String> NAMES = MapsKt.linkedMapOf(TuplesKt.to("dr", "Driver's door"), TuplesKt.to("pa", "Passenger door"), TuplesKt.to("s1", "Sliding door"), TuplesKt.to("s2", "Sliding door"), TuplesKt.to("bk", "Back door"));

    /* compiled from: CarLocks.kt */
    public static final /* data */ class State {
        private final long at;
        private final Map<String, String> doors;
        private final boolean live;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ State copy$default(State state, Map map, boolean z, long j, int i, Object obj) {
            if ((i & 1) != 0) {
                map = state.doors;
            }
            if ((i & 2) != 0) {
                z = state.live;
            }
            if ((i & 4) != 0) {
                j = state.at;
            }
            return state.copy(map, z, j);
        }

        public final Map<String, String> component1() {
            return this.doors;
        }

        /* renamed from: component2, reason: from getter */
        public final boolean component2() {
            return this.live;
        }

        /* renamed from: component3, reason: from getter */
        public final long component3() {
            return this.at;
        }

        public final State copy(Map<String, String> doors, boolean live, long at) {
            Intrinsics.checkNotNullParameter(doors, "doors");
            return new State(doors, live, at);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof State)) {
                return false;
            }
            State state = (State) other;
            return Intrinsics.areEqual(this.doors, state.doors) && this.live == state.live && this.at == state.at;
        }

        public int hashCode() {
            return (((this.doors.hashCode() * 31) + Boolean.hashCode(this.live)) * 31) + Long.hashCode(this.at);
        }

        public String toString() {
            return "State(doors=" + this.doors + ", live=" + this.live + ", at=" + this.at + ")";
        }

        public State(Map<String, String> doors, boolean live, long at) {
            Intrinsics.checkNotNullParameter(doors, "doors");
            this.doors = doors;
            this.live = live;
            this.at = at;
        }

        public final long getAt() {
            return this.at;
        }

        public final Map<String, String> getDoors() {
            return this.doors;
        }

        public final boolean getLive() {
            return this.live;
        }
    }

    private CarLocks() {
    }

    public final Function0<Unit> getListener() {
        return listener;
    }

    public final void setListener(Function0<Unit> function0) {
        listener = function0;
    }

    public final State state(Context ctx) {
        State it;
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        State state = cached;
        if (state != null) {
            return state;
        }
        String it2 = Door.INSTANCE.prefs(ctx).getString("locks", null);
        if (it2 == null || (it = INSTANCE.parse(it2, false)) == null) {
            return null;
        }
        cached = it;
        return it;
    }

    private final State parse(String text, Boolean live) {
        try {
            JSONObject j = new JSONObject(text);
            JSONObject d = j.optJSONObject("d");
            if (d == null) {
                return null;
            }
            long at = j.has("t") ? j.optLong("t") * 1000 : j.optLong("at", System.currentTimeMillis());
            Iterable keySet = NAMES.keySet();
            Intrinsics.checkNotNullExpressionValue(keySet, "<get-keys>(...)");
            LinkedHashMap result$iv = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(keySet, 10)), 16));
            for (Object element$iv$iv : keySet) {
                result$iv.put(element$iv$iv, d.optString((String) element$iv$iv, "?"));
            }
            return new State(result$iv, live != null ? live.booleanValue() : j.optBoolean("live"), at);
        } catch (Throwable th) {
            return null;
        }
    }

    public final void on(Context ctx, JSONObject j) {
        boolean z;
        State copy$default;
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(j, "j");
        String jSONObject = j.toString();
        Intrinsics.checkNotNullExpressionValue(jSONObject, "toString(...)");
        State s = parse(jSONObject, null);
        if (s == null) {
            return;
        }
        Iterable $this$all$iv = s.getDoors().values();
        if (!($this$all$iv instanceof Collection) || !((Collection) $this$all$iv).isEmpty()) {
            Iterator it = $this$all$iv.iterator();
            while (true) {
                if (it.hasNext()) {
                    Object element$iv = it.next();
                    String it2 = (String) element$iv;
                    if (!Intrinsics.areEqual(it2, "?")) {
                        z = false;
                        break;
                    }
                } else {
                    z = true;
                    break;
                }
            }
        } else {
            z = true;
        }
        if (z) {
            State state = state(ctx);
            if (state == null || (copy$default = State.copy$default(state, null, false, 0L, 5, null)) == null) {
                copy$default = State.copy$default(s, null, false, 0L, 5, null);
            }
            cached = copy$default;
            main.post(new Runnable() { // from class: com.starpilot.starlock.CarLocks$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    CarLocks.on$lambda$5();
                }
            });
            return;
        }
        cached = s;
        JSONObject saved = new JSONObject().put("d", new JSONObject(s.getDoors())).put("live", s.getLive()).put("at", s.getAt());
        SharedPreferences.Editor e = Door.INSTANCE.prefs(ctx).edit().putString("locks", saved.toString());
        if (s.getLive()) {
            e.putString("state", Intrinsics.areEqual((Object) allLocked(s), (Object) true) ? "locked" : "unlocked");
        }
        e.apply();
        LockTile.Companion.refresh(ctx);
        main.post(new Runnable() { // from class: com.starpilot.starlock.CarLocks$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                CarLocks.on$lambda$6();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void on$lambda$5() {
        Function0<Unit> function0 = listener;
        if (function0 != null) {
            function0.invoke();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void on$lambda$6() {
        Function0<Unit> function0 = listener;
        if (function0 != null) {
            function0.invoke();
        }
    }

    public final Boolean allLocked(State s) {
        Map<String, String> doors;
        Iterable values;
        if (s == null || (doors = s.getDoors()) == null || (values = doors.values()) == null) {
            return null;
        }
        Iterable $this$filter$iv = values;
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            String it = (String) element$iv$iv;
            if (!Intrinsics.areEqual(it, "?")) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        List known = (List) destination$iv$iv;
        if (known.isEmpty()) {
            return null;
        }
        List $this$all$iv = known;
        boolean z = true;
        if (!($this$all$iv instanceof Collection) || !$this$all$iv.isEmpty()) {
            Iterator it2 = $this$all$iv.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    break;
                }
                Object element$iv = it2.next();
                String it3 = (String) element$iv;
                if (!Intrinsics.areEqual(it3, "L")) {
                    z = false;
                    break;
                }
            }
        }
        return Boolean.valueOf(z);
    }

    public final String summary(State s) {
        Map<String, String> $this$filterValues$iv;
        int count$iv;
        if (s == null || ($this$filterValues$iv = s.getDoors()) == null) {
            return null;
        }
        LinkedHashMap result$iv = new LinkedHashMap();
        for (Map.Entry<String, String> entry$iv : $this$filterValues$iv.entrySet()) {
            String it = entry$iv.getValue();
            if (!Intrinsics.areEqual(it, "?")) {
                result$iv.put(entry$iv.getKey(), entry$iv.getValue());
            }
        }
        LinkedHashMap known = result$iv;
        if (known.isEmpty()) {
            return null;
        }
        LinkedHashMap result$iv2 = new LinkedHashMap();
        for (Map.Entry entry$iv2 : (Set<Map.Entry>) (Set) known.entrySet()) {
            String it2 = (String) entry$iv2.getValue();
            if (Intrinsics.areEqual(it2, "U")) {
                result$iv2.put(entry$iv2.getKey(), entry$iv2.getValue());
            }
        }
        Set open = result$iv2.keySet();
        if (open.isEmpty()) {
            return "Locked";
        }
        if (open.size() == known.size()) {
            return "Unlocked";
        }
        Set $this$map$iv = open;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        for (Object item$iv$iv : $this$map$iv) {
            String it3 = (String) item$iv$iv;
            String str = NAMES.get(it3);
            Intrinsics.checkNotNull(str);
            destination$iv$iv.add(str);
        }
        List names = CollectionsKt.toMutableList(destination$iv$iv);
        List $this$count$iv = names;
        if (($this$count$iv instanceof Collection) && $this$count$iv.isEmpty()) {
            count$iv = 0;
        } else {
            count$iv = 0;
            for (Object element$iv : $this$count$iv) {
                String it4 = (String) element$iv;
                if (Intrinsics.areEqual(it4, "Sliding door") && (count$iv = count$iv + 1) < 0) {
                    CollectionsKt.throwCountOverflow();
                }
            }
        }
        if (count$iv == 2) {
            CollectionsKt.removeAll(names, new Function1() { // from class: com.starpilot.starlock.CarLocks$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    boolean summary$lambda$13;
                    summary$lambda$13 = CarLocks.summary$lambda$13((String) obj);
                    return Boolean.valueOf(summary$lambda$13);
                }
            });
            names.add("Both sliding doors");
        }
        String lowerCase = CollectionsKt.joinToString(names, ", ", "", "", -1, "...", null).toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
        if (lowerCase.length() > 0) {
            StringBuilder sb = new StringBuilder();
            char it5 = lowerCase.charAt(0);
            String valueOf = String.valueOf(it5);
            Intrinsics.checkNotNull(valueOf, "null cannot be cast to non-null type java.lang.String");
            String upperCase = valueOf.toUpperCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(upperCase, "toUpperCase(...)");
            StringBuilder append = sb.append((Object) upperCase);
            String substring = lowerCase.substring(1);
            Intrinsics.checkNotNullExpressionValue(substring, "substring(...)");
            lowerCase = append.append(substring).toString();
        }
        return lowerCase + " unlocked";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean summary$lambda$13(String it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return Intrinsics.areEqual(it, "Sliding door");
    }

    public final void ask() {
        BleLink l = KeeperService.Companion.getLink();
        if (l != null) {
            if (!l.getReady()) {
                l = null;
            }
            if (l == null) {
                return;
            }
            final BleLink l2 = l;
            new Thread(new Runnable() { // from class: com.starpilot.starlock.CarLocks$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    BleLink.post$default(l2, "locks", null, 2, null);
                }
            }).start();
        }
    }
}
