package com.starpilot.starlock;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import androidx.core.app.NotificationCompat;
import com.starpilot.starlock.LockTile;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import org.json.JSONArray;
import org.json.JSONObject;
import kotlin.Result.Companion;
import kotlin.jvm.internal.Ref.IntRef;

/* compiled from: Door.kt */
public final class Door {
    public static final Door INSTANCE = new Door();
    private static final Handler main = new Handler(Looper.getMainLooper());
    private static final AtomicBoolean busy = new AtomicBoolean(false);

    private Door() {
    }

    public final SharedPreferences prefs(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        return ctx.getSharedPreferences("starlock", 0);
    }

    public final Pairing pairing(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        String it = prefs(ctx).getString("link", null);
        if (it != null) {
            return Pairing.Companion.parse(it);
        }
        return null;
    }

    public final String sound(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        String it = prefs(ctx).getString("sound", "horn");
        if (!CollectionsKt.contains(SetsKt.setOf((Object[]) new String[]{"horn", "buzzer", "none"}), it)) {
            it = null;
        }
        return it == null ? "horn" : it;
    }

    /* compiled from: Door.kt */
    public static final /* data */ class Pattern {
        private final int gapMs;
        private final int n;
        private final int onMs;

        public static /* synthetic */ Pattern copy$default(Pattern pattern, int i, int i2, int i3, int i4, Object obj) {
            if ((i4 & 1) != 0) {
                i = pattern.n;
            }
            if ((i4 & 2) != 0) {
                i2 = pattern.onMs;
            }
            if ((i4 & 4) != 0) {
                i3 = pattern.gapMs;
            }
            return pattern.copy(i, i2, i3);
        }

        /* renamed from: component1, reason: from getter */
        public final int component1() {
            return this.n;
        }

        /* renamed from: component2, reason: from getter */
        public final int component2() {
            return this.onMs;
        }

        /* renamed from: component3, reason: from getter */
        public final int component3() {
            return this.gapMs;
        }

        public final Pattern copy(int n, int onMs, int gapMs) {
            return new Pattern(n, onMs, gapMs);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Pattern)) {
                return false;
            }
            Pattern pattern = (Pattern) other;
            return this.n == pattern.n && this.onMs == pattern.onMs && this.gapMs == pattern.gapMs;
        }

        public int hashCode() {
            return (((Integer.hashCode(this.n) * 31) + Integer.hashCode(this.onMs)) * 31) + Integer.hashCode(this.gapMs);
        }

        public String toString() {
            return "Pattern(n=" + this.n + ", onMs=" + this.onMs + ", gapMs=" + this.gapMs + ")";
        }

        public Pattern(int n, int onMs, int gapMs) {
            this.n = n;
            this.onMs = onMs;
            this.gapMs = gapMs;
        }

        public final int getGapMs() {
            return this.gapMs;
        }

        public final int getN() {
            return this.n;
        }

        public final int getOnMs() {
            return this.onMs;
        }
    }

    public final Pattern defaultPattern(String cmd) {
        Intrinsics.checkNotNullParameter(cmd, "cmd");
        return Intrinsics.areEqual(cmd, "lock") ? new Pattern(1, 80, 170) : new Pattern(2, 80, 170);
    }

    public final Pattern pattern(Context ctx, String cmd) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(cmd, "cmd");
        Pattern d = defaultPattern(cmd);
        SharedPreferences p = prefs(ctx);
        return new Pattern(RangesKt.coerceIn(p.getInt(cmd + "_n", d.getN()), 1, 5), RangesKt.coerceIn(p.getInt(cmd + "_on", d.getOnMs()), 30, 1000), RangesKt.coerceIn(p.getInt(cmd + "_gap", d.getGapMs()), 50, 1000));
    }

    public final void savePattern(Context ctx, String cmd, Pattern pt) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(cmd, "cmd");
        Intrinsics.checkNotNullParameter(pt, "pt");
        prefs(ctx).edit().putInt(cmd + "_n", pt.getN()).putInt(cmd + "_on", pt.getOnMs()).putInt(cmd + "_gap", pt.getGapMs()).apply();
        AutoEvents.INSTANCE.syncSound(ctx);
    }

    private final JSONArray beeps(Context ctx, String cmd, String sound) {
        if (!Intrinsics.areEqual(sound, "horn")) {
            return null;
        }
        Pattern it = pattern(ctx, cmd);
        return new JSONArray().put(it.getN()).put(it.getOnMs()).put(it.getGapMs());
    }

    public final boolean bleAllowed(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        return Build.VERSION.SDK_INT >= 31 ? ctx.checkSelfPermission("android.permission.BLUETOOTH_SCAN") == 0 && ctx.checkSelfPermission("android.permission.BLUETOOTH_CONNECT") == 0 : ctx.checkSelfPermission("android.permission.ACCESS_FINE_LOCATION") == 0;
    }

    public final String explain(String cmd, String err) {
        Intrinsics.checkNotNullParameter(cmd, "cmd");
        Intrinsics.checkNotNullParameter(err, "err");
        String what = Intrinsics.areEqual(cmd, "lock") ? "Lock" : "Unlock";
        if (StringsKt.contains((CharSequence) err, (CharSequence) "no answer from the car", false)) {
            return what + " sent, but the car didn't confirm it";
        }
        if (StringsKt.contains((CharSequence) err, (CharSequence) "refused by the car", false)) {
            return "The car refused: " + StringsKt.removeSurrounding(StringsKt.trim((CharSequence) StringsKt.substringAfter(err, "refused by the car", err)).toString(), (CharSequence) "(", (CharSequence) ")");
        }
        if (StringsKt.contains((CharSequence) err, (CharSequence) "not sent (", false)) {
            return "Not done: " + StringsKt.removeSuffix(StringsKt.substringAfter(err, "not sent (", err), (CharSequence) ")");
        }
        if (StringsKt.contains((CharSequence) err, (CharSequence) "stopped (", false)) {
            return "Stopped: " + StringsKt.removeSuffix(StringsKt.substringAfter(err, "stopped (", err), (CharSequence) ")");
        }
        return "Not done: " + err;
    }

    public final boolean run(Context context, final String cmd, final Function1<? super String, Unit> logTo, final Function2<? super Outcome, ? super String, Unit> done) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(cmd, "cmd");
        Intrinsics.checkNotNullParameter(logTo, "logTo");
        Intrinsics.checkNotNullParameter(done, "done");
        final Context ctx = context.getApplicationContext();
        final long t0 = System.currentTimeMillis();
        final Function1 log = new Function1() { // from class: com.starpilot.starlock.Door$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit run$lambda$3;
                run$lambda$3 = Door.run$lambda$3(logTo, t0, (String) obj);
                return run$lambda$3;
            }
        };
        Intrinsics.checkNotNull(ctx);
        final Pairing p = pairing(ctx);
        if (p == null) {
            main.post(new Runnable() { // from class: com.starpilot.starlock.Door$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    Door.run$lambda$5$lambda$4(done);
                }
            });
            return true;
        }
        if (!busy.compareAndSet(false, true)) {
            return false;
        }
        final SharedPreferences prefs = prefs(ctx);
        final String sound = sound(ctx);
        final JSONArray beeps = beeps(ctx, cmd, sound);
        boolean ble = bleAllowed(ctx);
        final AtomicBoolean won = new AtomicBoolean(false);
        final Ref.IntRef left = new Ref.IntRef();
        left.element = ble ? 2 : 1;
        new Thread(new Runnable() { // from class: com.starpilot.starlock.Door$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                Door.run$lambda$11(prefs, ctx, p, log, cmd, sound, beeps, won, t0, done, left);
            }
        }).start();
        if (ble) {
            new Thread(new Runnable() { // from class: com.starpilot.starlock.Door$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    Door.run$lambda$17(won, cmd, sound, beeps, log, prefs, ctx, p, t0, done, left);
                }
            }).start();
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit run$lambda$3(Function1 $logTo, long $t0, String s) {
        Intrinsics.checkNotNullParameter(s, "s");
        $logTo.invoke("+" + (System.currentTimeMillis() - $t0) + " ms  " + s);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void run$lambda$5$lambda$4(Function2 $done) {
        $done.invoke(Outcome.FAILED, "Pair StarLock with the comma first");
    }

    private static final boolean run$finish(final Function1<? super String, Unit> function1, final AtomicBoolean won, final long t0, final Context ctx, final String $cmd, final Function2<? super Outcome, ? super String, Unit> function2, final Ref.IntRef left, final String via, final JSONObject answer) {
        return main.post(new Runnable() { // from class: com.starpilot.starlock.Door$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                Door.run$finish$lambda$8(answer, function1, via, won, t0, ctx, $cmd, function2, left);
            }
        });
    }

    /* VF: from Vineflower (jadx: Removed duplicated region;incorrect) */
    public static final void run$finish$lambda$8(
        JSONObject var0, Function1 var1, String var2, AtomicBoolean var3, long var4, Context var6, String var7, Function2 var8, IntRef var9
    ) {
        String var18 = null; Object var26 = null; // declarations Vineflower left out
        boolean var13;
        JSONObject var28;
        label83: {
            var13 = false;
            if (var0 != null) {
                String var15 = var0.optString("error");
                Intrinsics.checkNotNullExpressionValue(var15, "optString(...)");
                if (!StringsKt.startsWith(var15, "unknown action", false)) {
                    var28 = var0;
                    break label83;
                }
            }

            var28 = null;
        }

        if (var0 != null && var28 == null) {
            var1.invoke(var2 + ": the comma's StarView needs the update with the door action");
        }

        if (var28 != null && var3.compareAndSet(false, true)) {
            boolean var12;
            boolean var14;
            label72: {
                busy.set(false);
                var14 = var28.optBoolean("ok");
                var1.invoke(var2 + " answered");
                double var10 = (System.currentTimeMillis() - var4) / 100 / 10.0;
                var26 = var10 + " s";
                var18 = var28.optString("result");
                if (var14) {
                    Intrinsics.checkNotNull(var18);
                    if (StringsKt.endsWith(var18, ": confirmed", false)) {
                        var12 = true;
                        break label72;
                    }
                }

                var12 = false;
            }

            Outcome var17;
            if (var12) {
                var17 = Outcome.CONFIRMED;
            } else {
                label90: {
                    if (!var14) {
                        String var16 = var28.optString("error");
                        Intrinsics.checkNotNullExpressionValue(var16, "optString(...)");
                        if (!StringsKt.contains(var16, "no answer from the car", false)) {
                            var17 = Outcome.FAILED;
                            break label90;
                        }
                    }

                    var17 = Outcome.SENT;
                }
            }

            if (var17 != Outcome.FAILED) {
                LockTile.Companion var24 = LockTile.Companion;
                Intrinsics.checkNotNull(var6);
                var24.remember(var6, var7);
            }

            String var20;
            if (var14) {
                String var19;
                if (Intrinsics.areEqual(var7, "lock")) {
                    var19 = "Locked";
                } else {
                    var19 = "Unlocked";
                }

                String var25;
                if (var12) {
                    var25 = " ✓ - the car confirmed it";
                } else {
                    var25 = " - sent, the car didn't confirm";
                }

                var20 = var19 + var25 + "  (" + var2 + ", " + var26 + ")";
            } else {
                Door var23 = INSTANCE;
                var var21 = var18;
                var12 = var13;
                if (var21.length() == 0) {
                    var12 = true;
                }

                if (var12) {
                    var21 = "refused";
                }

                String var22 = var28.optString("error", var21);
                Intrinsics.checkNotNullExpressionValue(var22, "optString(...)");
                var20 = var23.explain(var7, var22);
            }

            var8.invoke(var17, var20);
        } else if (var28 == null) {
            var9.element--;
            if (var9.element == 0 && !var3.get()) {
                busy.set(false);
                var8.invoke(Outcome.FAILED, "Couldn't reach the comma (Wi-Fi or Bluetooth)");
            }
        }
    }

    /* VF: from Vineflower (jadx: Removed duplicated region;incorrect) */
    public static final void run$lambda$11(
        SharedPreferences var0,
        Context var1,
        Pairing var2,
        Function1 var3,
        String var4,
        String var5,
        JSONArray var6,
        AtomicBoolean var7,
        long var8,
        Function2 var10,
        IntRef var11
    ) {
        Pair var36;
        try {
            Intrinsics.checkNotNull(var1);
            var36 = new WifiLink(var1, var2, var3).door(var4, var5, var6, var0.getString("host", null), new Door$$ExternalSyntheticLambda2(var7));
        } catch (Throwable t) {
            var36 = null;
        }
        if (var36 != null) {
            var0.edit().putString("host", (String)var36.getFirst()).apply();
        }

        JSONObject var35 = null;
        if (var36 != null) {
            var35 = (JSONObject)var36.getSecond();
        }

        run$finish(var3, var7, var8, var1, var4, var10, var11, "Wi-Fi", var35);
    }

    /* VF: from Vineflower (jadx: Removed duplicated region;incorrect) */
    public static final void run$lambda$17(
        AtomicBoolean var0,
        String var1,
        String var2,
        JSONArray var3,
        Function1 var4,
        SharedPreferences var5,
        Context var6,
        Pairing var7,
        long var8,
        Function2 var10,
        IntRef var11
    ) {
        long var13 = System.currentTimeMillis();
        long var15 = 4000;

        while (KeeperService.Companion.getReconnecting()) {
            BleLink var17 = KeeperService.Companion.getLink();
            boolean var12;
            if (var17 != null && var17.getReady()) {
                var12 = true;
            } else {
                var12 = false;
            }

            if (var12 || System.currentTimeMillis() >= var13 + var15 || var0.get()) {
                break;
            }

            Thread.sleep(50L);
        }

        BleLink var18 = KeeperService.Companion.getLink();
        if (var18 == null || !var18.getReady()) {
            var18 = null;
        }

        Pair var39;
        label278: {
            if (var18 != null) {
                JSONObject var20 = var18.send(var1, (String)var2, var3, new Door$$ExternalSyntheticLambda0(var0));
                if (var20 != null) {
                    String var19 = var18.getAddress();
                    String var40 = var19;
                    if (var19 == null) {
                        var40 = "";
                    }

                    var39 = TuplesKt.to(var40, var20);
                    break label278;
                }
            }

            var39 = null;
        }

        if (var18 != null && var39 == null) {
            var4.invoke("Bluetooth: the open connection didn't answer, reconnecting");
        }

        Pair var33;
        if (var39 == null) {
            if (var0.get()) {
                var33 = null;
            } else {
                Pair var36;
                try {
                    Intrinsics.checkNotNull(var6);
                    var36 = new BleLink(var6, var7, var4).door(var1, var2, var3, var5.getString("ble", null), new Door$$ExternalSyntheticLambda1(var0));
                } catch (Throwable t) {
                    var36 = null;
                }

                var33 = (Pair)var36;
            }
        } else {
            var33 = var39;
        }

        if (var33 != null) {
            boolean var38;
            if (((CharSequence)var33.getFirst()).length() > 0) {
                var38 = true;
            } else {
                var38 = false;
            }

            if (var38) {
                var5.edit().putString("ble", (String)var33.getFirst()).apply();
            }
        }

        JSONObject var34;
        if (var33 != null) {
            var34 = (JSONObject)var33.getSecond();
        } else {
            var34 = null;
        }

        run$finish(var4, var0, var8, var6, var1, var10, var11, "Bluetooth", var34);
    }

    // recovered from Vineflower (R8 lambda body jadx inlined)
   static final boolean run$lambda$11$lambda$10$lambda$9(AtomicBoolean var0) {
       return var0.get();
    }

    // recovered from Vineflower (R8 lambda body jadx inlined)
   static final boolean run$lambda$17$lambda$13(AtomicBoolean var0) {
       return var0.get();
    }

    // recovered from Vineflower (R8 lambda body jadx inlined)
   static final boolean run$lambda$17$lambda$16$lambda$15(AtomicBoolean var0) {
       return var0.get();
    }
}
