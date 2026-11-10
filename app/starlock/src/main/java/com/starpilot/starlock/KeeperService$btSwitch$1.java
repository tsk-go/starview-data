package com.starpilot.starlock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: KeeperService.kt */
public final class KeeperService$btSwitch$1 extends BroadcastReceiver {
    final /* synthetic */ KeeperService this$0;

    KeeperService$btSwitch$1(KeeperService $receiver) {
        this.this$0 = $receiver;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context c, Intent i) {
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(i, "i");
        switch (i.getIntExtra("android.bluetooth.adapter.extra.STATE", -1)) {
            case 10:
            case 13:
                AppLog.INSTANCE.log("Bluetooth: switched off on the phone");
                final BleLink l = KeeperService.Companion.getLink();
                if (l != null) {
                    new Thread(new Runnable() { // from class: com.starpilot.starlock.KeeperService$btSwitch$1$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            l.close();
                        }
                    }).start();
                }
                this.this$0.update();
                break;
            case 12:
                this.this$0.reconnect("back on");
                this.this$0.update();
                break;
        }
    }
}
