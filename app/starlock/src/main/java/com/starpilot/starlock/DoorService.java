package com.starpilot.starlock;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: DoorService.kt */
public final class DoorService extends Service {
    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int flags, final int startId) {
        String cmd = intent != null ? intent.getStringExtra("cmd") : null;
        if (!Intrinsics.areEqual(cmd, "lock") && !Intrinsics.areEqual(cmd, "unlock")) {
            stopSelf(startId);
            return 2;
        }
        Notify.INSTANCE.show(this, Intrinsics.areEqual(cmd, "lock") ? "Locking…" : "Unlocking…", false);
        boolean started = Door.INSTANCE.run(this, cmd, new Function1() { // from class: com.starpilot.starlock.DoorService$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit onStartCommand$lambda$0;
                onStartCommand$lambda$0 = DoorService.onStartCommand$lambda$0((String) obj);
                return onStartCommand$lambda$0;
            }
        }, new Function2() { // from class: com.starpilot.starlock.DoorService$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit onStartCommand$lambda$1;
                onStartCommand$lambda$1 = DoorService.onStartCommand$lambda$1(DoorService.this, startId, (Outcome) obj, (String) obj2);
                return onStartCommand$lambda$1;
            }
        });
        if (!started) {
            Notify.INSTANCE.show(this, "Still working on the last one", true);
            stopSelf(startId);
        }
        return 2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onStartCommand$lambda$0(String it) {
        Intrinsics.checkNotNullParameter(it, "it");
        AppLog.INSTANCE.log(it);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onStartCommand$lambda$1(DoorService this$0, int $startId, Outcome outcome, String msg) {
        Intrinsics.checkNotNullParameter(outcome, "<unused var>");
        Intrinsics.checkNotNullParameter(msg, "msg");
        Notify.INSTANCE.show(this$0, msg, true);
        this$0.stopSelf($startId);
        return Unit.INSTANCE;
    }
}
