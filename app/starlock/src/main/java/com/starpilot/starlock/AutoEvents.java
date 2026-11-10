package com.starpilot.starlock;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.starpilot.starlock.Door;
import com.starpilot.starlock.LockTile;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: AutoEvents.kt */
public final class AutoEvents {
    private static volatile Context appCtx;
    private static volatile Function1<? super String, Unit> listener;
    public static final AutoEvents INSTANCE = new AutoEvents();
    private static final Handler main = new Handler(Looper.getMainLooper());
    private static final Runnable pending = new Runnable() { // from class: com.starpilot.starlock.AutoEvents$$ExternalSyntheticLambda1
        @Override // java.lang.Runnable
        public final void run() {
            AutoEvents.pending$lambda$1();
        }
    };

    private AutoEvents() {
    }

    public final Function1<String, Unit> getListener() {
        return (Function1) listener;
    }

    public final void setListener(Function1<? super String, Unit> function1) {
        listener = function1;
    }

    public final void on(Context ctx, JSONObject j) {
        final String msg;
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(j, "j");
        String cmd = j.optString("cmd");
        if (Intrinsics.areEqual(cmd, "lock") || Intrinsics.areEqual(cmd, "unlock")) {
            String what = Intrinsics.areEqual(cmd, "lock") ? "Auto-locked" : "Auto-unlocked";
            if (j.optBoolean("ok")) {
                msg = what + " ✓ - the car confirmed it";
            } else {
                msg = j.optBoolean("done") ? what + " - sent, the car didn't confirm" : "Auto-" + cmd + " didn't go through: " + j.optString("result");
            }
            if (j.optBoolean("done")) {
                LockTile.Companion companion = LockTile.Companion;
                Context applicationContext = ctx.getApplicationContext();
                Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
                companion.remember(applicationContext, cmd);
            }
            AppLog.INSTANCE.log(msg);
            main.post(new Runnable() { // from class: com.starpilot.starlock.AutoEvents$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    AutoEvents.on$lambda$0(msg);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void on$lambda$0(String $msg) {
        Function1<? super String, Unit> function1 = listener;
        if (function1 != null) {
            function1.invoke($msg);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void pending$lambda$1() {
        INSTANCE.push();
    }

    public final void syncSound(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        appCtx = ctx.getApplicationContext();
        main.removeCallbacks(pending);
        main.postDelayed(pending, 1500L);
    }

    private final void push() {
        BleLink link;
        final Context ctx = appCtx;
        if (ctx != null && (link = KeeperService.Companion.getLink()) != null) {
            if (!link.getReady()) {
                link = null;
            }
            if (link == null) {
                return;
            }
            final JSONObject set = new JSONObject().put("sound", Door.INSTANCE.sound(ctx)).put("lock_beeps", push$beeps(ctx, "lock")).put("unlock_beeps", push$beeps(ctx, "unlock"));
            final BleLink link2 = link;
            new Thread(new Runnable() { // from class: com.starpilot.starlock.AutoEvents$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    AutoEvents.push$lambda$4(link2, set, ctx);
                }
            }).start();
        }
    }

    private static final JSONArray push$beeps(Context ctx, String cmd) {
        Door.Pattern it = Door.INSTANCE.pattern(ctx, cmd);
        return new JSONArray().put(it.getN()).put(it.getOnMs()).put(it.getGapMs());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void push$lambda$4(BleLink $link, JSONObject $set, Context $ctx) {
        JSONObject put = new JSONObject().put("set", $set);
        Intrinsics.checkNotNullExpressionValue(put, "put(...)");
        JSONObject r = BleLink.request$default($link, "autolock", put, false, 0L, 12, null);
        boolean z = false;
        if (r != null && r.optBoolean("ok")) {
            z = true;
        }
        if (z) {
            AppLog.INSTANCE.log("Auto-lock answer-back: " + Door.INSTANCE.sound($ctx) + " (sent to the comma)");
        }
    }
}
