package com.starpilot.starlock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: KeeperService.kt */
public final class BootReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context ctx, Intent intent) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(intent, "intent");
        if (!Intrinsics.areEqual(intent.getAction(), "android.intent.action.BOOT_COMPLETED") || !KeeperService.Companion.background(ctx)) {
            return;
        }
        KeeperService.Companion.start(ctx);
    }
}
