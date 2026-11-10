package com.starpilot.starlock;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.widget.Toast;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: Notify.kt */
public final class Notify {
    private static final String CHANNEL = "starlock_result";
    private static final int ID = 2;
    public static final Notify INSTANCE = new Notify();

    private Notify() {
    }

    public final void show(Context ctx, String text, boolean done) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(text, "text");
        NotificationManager nm = (NotificationManager) ctx.getSystemService(NotificationManager.class);
        boolean z = false;
        if (nm.getNotificationChannel(CHANNEL) == null) {
            NotificationChannel $this$show_u24lambda_u240 = new NotificationChannel(CHANNEL, "Lock / unlock result", 4);
            $this$show_u24lambda_u240.setSound(null, null);
            $this$show_u24lambda_u240.enableVibration(false);
            $this$show_u24lambda_u240.setShowBadge(false);
            nm.createNotificationChannel($this$show_u24lambda_u240);
        }
        if (nm.areNotificationsEnabled()) {
            NotificationChannel notificationChannel = nm.getNotificationChannel(CHANNEL);
            if (!(notificationChannel != null && notificationChannel.getImportance() == 0)) {
                z = true;
            }
        }
        boolean allowed = z;
        if (!allowed) {
            Toast.makeText(ctx, text, 1).show();
            return;
        }
        Notification n = new Notification.Builder(ctx, CHANNEL).setSmallIcon(R.drawable.ic_lock).setContentTitle(text).setAutoCancel(true).setTimeoutAfter(done ? 6000L : 20000L).build();
        Intrinsics.checkNotNullExpressionValue(n, "build(...)");
        nm.notify(2, n);
    }
}
