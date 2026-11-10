package com.starpilot.starview;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.PowerManager;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: LinkService.kt */
public final class LinkService extends Service {
    private static final String CHANNEL = "starview_link";
    public static final Companion Companion = new Companion(null);
    private static final int ID = 7;
    private PowerManager.WakeLock wake;

    /* compiled from: LinkService.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final void start(Context ctx) {
            Intrinsics.checkNotNullParameter(ctx, "ctx");
            try {
                ctx.startForegroundService(new Intent(ctx, (Class<?>) LinkService.class));
            } catch (Exception e) {
            }
        }

        public final void stop(Context ctx) {
            Intrinsics.checkNotNullParameter(ctx, "ctx");
            try {
                ctx.stopService(new Intent(ctx, (Class<?>) LinkService.class));
            } catch (Exception e) {
            }
        }
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int flags, int startId) {
        NotificationManager nm = (NotificationManager) getSystemService(NotificationManager.class);
        NotificationChannel $this$onStartCommand_u24lambda_u240 = new NotificationChannel("starview_link", "StarView running", 2);
        $this$onStartCommand_u24lambda_u240.setDescription("Keeps the comma link, turn signals and lane prompts working while StarView isn't on screen");
        $this$onStartCommand_u24lambda_u240.setShowBadge(false);
        nm.createNotificationChannel($this$onStartCommand_u24lambda_u240);
        PendingIntent open = PendingIntent.getActivity(this, 0, new Intent(this, (Class<?>) MainActivity.class).addFlags(536870912), 201326592);
        Notification n = new Notification.Builder(this, "starview_link").setSmallIcon(R.mipmap.ic_launcher).setContentTitle("StarView is running").setContentText("Turn signals, lane prompts and the comma link keep working in the background").setOngoing(true).setContentIntent(open).build();
        Intrinsics.checkNotNullExpressionValue(n, "build(...)");
        try {
            startForeground(7, n, 16);
            if (this.wake == null) {
                Object systemService = getSystemService("power");
                Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.os.PowerManager");
                PowerManager.WakeLock $this$onStartCommand_u24lambda_u241 = ((PowerManager) systemService).newWakeLock(1, "StarView:link");
                $this$onStartCommand_u24lambda_u241.setReferenceCounted(false);
                $this$onStartCommand_u24lambda_u241.acquire();
                this.wake = $this$onStartCommand_u24lambda_u241;
            }
            return 2;
        } catch (Exception e) {
            stopSelf();
            return 2;
        }
    }

    @Override // android.app.Service
    public void onTaskRemoved(Intent rootIntent) {
        stopSelf();
        super.onTaskRemoved(rootIntent);
    }

    @Override // android.app.Service
    public void onDestroy() {
        try {
            PowerManager.WakeLock wakeLock = this.wake;
            if (wakeLock != null) {
                wakeLock.release();
            }
        } catch (Exception e) {
        }
        this.wake = null;
        super.onDestroy();
    }
}
