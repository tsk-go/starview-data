package com.starpilot.starlock;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: KeeperService.kt */
public final class KeeperService extends Service {
    private static final String CHANNEL = "starlock";
    private static final int ID = 1;
    private static volatile Context appCtx;
    private static volatile KeeperService instance;
    private static volatile BleLink link;
    private static Function0<Unit> onState;
    private static volatile boolean reconnecting;
    private volatile boolean alive;
    private final KeeperService$btSwitch$1 btSwitch = new KeeperService$btSwitch$1(this);
    private final AtomicBoolean busy = new AtomicBoolean(false);
    private volatile BluetoothDevice device;
    private boolean wasReady;

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion Companion = new Companion(null);
    private static final Handler main = new Handler(Looper.getMainLooper());
    private static final Runnable stopNow = new Runnable() { // from class: com.starpilot.starlock.KeeperService$$ExternalSyntheticLambda5
        @Override // java.lang.Runnable
        public final void run() {
            KeeperService.stopNow$lambda$23();
        }
    };

    /* compiled from: KeeperService.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final BleLink getLink() {
            return KeeperService.link;
        }

        public final void setLink(BleLink bleLink) {
            KeeperService.link = bleLink;
        }

        public final boolean getReconnecting() {
            return KeeperService.reconnecting;
        }

        public final void setReconnecting(boolean z) {
            KeeperService.reconnecting = z;
        }

        public final KeeperService getInstance() {
            return KeeperService.instance;
        }

        public final void setInstance(KeeperService keeperService) {
            KeeperService.instance = keeperService;
        }

        public final Function0<Unit> getOnState() {
            return KeeperService.onState;
        }

        public final void setOnState(Function0<Unit> function0) {
            KeeperService.onState = function0;
        }

        public final boolean background(Context ctx) {
            Intrinsics.checkNotNullParameter(ctx, "ctx");
            return Door.INSTANCE.prefs(ctx).getBoolean("background", true);
        }

        public final void start(Context ctx) {
            Intrinsics.checkNotNullParameter(ctx, "ctx");
            if (Door.INSTANCE.pairing(ctx) != null && Door.INSTANCE.bleAllowed(ctx)) {
                KeeperService.appCtx = ctx.getApplicationContext();
                KeeperService.main.removeCallbacks(KeeperService.stopNow);
                try {
                    Result.Companion companion = Result.Companion;
                    Companion companion2 = this;
                    KtCompat.resultOf(ctx.startForegroundService(new Intent(ctx, (Class<?>) KeeperService.class)));
                } catch (Throwable th) {
                    Result.Companion companion3 = Result.Companion;
                    KtCompat.resultOf(ResultKt.createFailure(th));
                }
            }
        }

        public final void appLeft(Context ctx) {
            Intrinsics.checkNotNullParameter(ctx, "ctx");
            KeeperService.appCtx = ctx.getApplicationContext();
            KeeperService.main.removeCallbacks(KeeperService.stopNow);
            if (!background(ctx)) {
                KeeperService.main.postDelayed(KeeperService.stopNow, 600000L);
            }
        }

        public final void stop(Context ctx) {
            Intrinsics.checkNotNullParameter(ctx, "ctx");
            KeeperService.main.removeCallbacks(KeeperService.stopNow);
            ctx.stopService(new Intent(ctx, (Class<?>) KeeperService.class));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void stopNow$lambda$23() {
        Context it = appCtx;
        if (it != null) {
            it.stopService(new Intent(it, (Class<?>) KeeperService.class));
        }
    }

    public final void reconnect(String why) {
        final BluetoothDevice d;
        BluetoothAdapter adapter;
        Intrinsics.checkNotNullParameter(why, "why");
        final BleLink l = link;
        if (l == null || (d = this.device) == null) {
            return;
        }
        BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(BluetoothManager.class);
        if (!((bluetoothManager == null || (adapter = bluetoothManager.getAdapter()) == null || !adapter.isEnabled()) ? false : true) || l.getReady() || !this.busy.compareAndSet(false, true)) {
            return;
        }
        reconnecting = true;
        AppLog.INSTANCE.log("Bluetooth: " + why + " - connecting");
        new Thread(new Runnable() { // from class: com.starpilot.starlock.KeeperService$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                KeeperService.reconnect$lambda$8(l, d, KeeperService.this);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void reconnect$lambda$8(final BleLink $l, BluetoothDevice $d, final KeeperService this$0) {
        Object m61constructorimpl;
        try {
            $l.keep($d, true);
            $l.setOnState(new Function0() { // from class: com.starpilot.starlock.KeeperService$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit reconnect$lambda$8$lambda$0;
                    reconnect$lambda$8$lambda$0 = KeeperService.reconnect$lambda$8$lambda$0(this$0);
                    return reconnect$lambda$8$lambda$0;
                }
            });
            $l.setOnDrop(new Function0() { // from class: com.starpilot.starlock.KeeperService$$ExternalSyntheticLambda8
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit reconnect$lambda$8$lambda$1;
                    reconnect$lambda$8$lambda$1 = KeeperService.reconnect$lambda$8$lambda$1(this$0);
                    return reconnect$lambda$8$lambda$1;
                }
            });
            long j = 8000;
            long end = System.currentTimeMillis() + j;
            while (!$l.getReady() && this$0.alive && System.currentTimeMillis() < end) {
                Thread.sleep(100L);
            }
            if (!$l.getReady() && this$0.alive) {
                try {
                    Result.Companion companion = Result.Companion;
                    m61constructorimpl = KtCompat.resultOf($l.scan(new Function0() { // from class: com.starpilot.starlock.KeeperService$$ExternalSyntheticLambda9
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            boolean reconnect$lambda$8$lambda$3$lambda$2;
                            reconnect$lambda$8$lambda$3$lambda$2 = KeeperService.reconnect$lambda$8$lambda$3$lambda$2(this$0, $l);
                            return Boolean.valueOf(reconnect$lambda$8$lambda$3$lambda$2);
                        }
                    }));
                } catch (Throwable th) {
                    Result.Companion companion2 = Result.Companion;
                    m61constructorimpl = KtCompat.resultOf(ResultKt.createFailure(th));
                }
                if (KtCompat.isFailure(m61constructorimpl)) {
                    m61constructorimpl = null;
                }
                BluetoothDevice found = (BluetoothDevice) m61constructorimpl;
                if (found != null && !Intrinsics.areEqual(found.getAddress(), $d.getAddress()) && this$0.alive) {
                    AppLog.INSTANCE.log("Bluetooth: the comma is at a new address now");
                    Door.INSTANCE.prefs(this$0).edit().putString("ble", found.getAddress()).apply();
                    this$0.device = found;
                    $l.keep(found, true);
                    $l.setOnState(new Function0() { // from class: com.starpilot.starlock.KeeperService$$ExternalSyntheticLambda10
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            Unit reconnect$lambda$8$lambda$4;
                            reconnect$lambda$8$lambda$4 = KeeperService.reconnect$lambda$8$lambda$4(this$0);
                            return reconnect$lambda$8$lambda$4;
                        }
                    });
                    $l.setOnDrop(new Function0() { // from class: com.starpilot.starlock.KeeperService$$ExternalSyntheticLambda11
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            Unit reconnect$lambda$8$lambda$5;
                            reconnect$lambda$8$lambda$5 = KeeperService.reconnect$lambda$8$lambda$5(this$0);
                            return reconnect$lambda$8$lambda$5;
                        }
                    });
                    long end2 = System.currentTimeMillis() + j;
                    while (!$l.getReady() && this$0.alive && System.currentTimeMillis() < end2) {
                        Thread.sleep(100L);
                    }
                } else if (found == null) {
                    AppLog.INSTANCE.log("Bluetooth: the comma isn't advertising StarLock right now");
                }
            }
            if (!$l.getReady() && this$0.alive) {
                AppLog.INSTANCE.log("Bluetooth: the comma isn't near - connects by itself when it is");
                BluetoothDevice bluetoothDevice = this$0.device;
                if (bluetoothDevice == null) {
                    bluetoothDevice = $d;
                }
                $l.keep(bluetoothDevice, false);
                $l.setOnState(new Function0() { // from class: com.starpilot.starlock.KeeperService$$ExternalSyntheticLambda12
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        Unit reconnect$lambda$8$lambda$6;
                        reconnect$lambda$8$lambda$6 = KeeperService.reconnect$lambda$8$lambda$6(this$0);
                        return reconnect$lambda$8$lambda$6;
                    }
                });
                $l.setOnDrop(new Function0() { // from class: com.starpilot.starlock.KeeperService$$ExternalSyntheticLambda13
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        Unit reconnect$lambda$8$lambda$7;
                        reconnect$lambda$8$lambda$7 = KeeperService.reconnect$lambda$8$lambda$7(this$0);
                        return reconnect$lambda$8$lambda$7;
                    }
                });
            }
        } finally {
            reconnecting = false;
            this$0.busy.set(false);
            this$0.update();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit reconnect$lambda$8$lambda$0(KeeperService this$0) {
        this$0.update();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit reconnect$lambda$8$lambda$1(KeeperService this$0) {
        this$0.reconnect("connection dropped");
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean reconnect$lambda$8$lambda$3$lambda$2(KeeperService $this_runCatching, BleLink $l) {
        return !$this_runCatching.alive || $l.getReady();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit reconnect$lambda$8$lambda$4(KeeperService this$0) {
        this$0.update();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit reconnect$lambda$8$lambda$5(KeeperService this$0) {
        this$0.reconnect("connection dropped");
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit reconnect$lambda$8$lambda$6(KeeperService this$0) {
        this$0.update();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit reconnect$lambda$8$lambda$7(KeeperService this$0) {
        this$0.reconnect("connection dropped");
        return Unit.INSTANCE;
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        instance = this;
        registerReceiver(this.btSwitch, new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED"));
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    private final Notification note(String text) {
        NotificationManager nm = (NotificationManager) getSystemService(NotificationManager.class);
        if (nm.getNotificationChannel(CHANNEL) == null) {
            NotificationChannel $this$note_u24lambda_u249 = new NotificationChannel(CHANNEL, "StarLock connection", 1);
            $this$note_u24lambda_u249.setShowBadge(false);
            nm.createNotificationChannel($this$note_u24lambda_u249);
        }
        PendingIntent open = PendingIntent.getActivity(this, 0, new Intent(this, (Class<?>) MainActivity.class), 67108864);
        Notification build = new Notification.Builder(this, CHANNEL).setSmallIcon(R.drawable.ic_lock).setContentTitle("StarLock").setContentText(text).setOngoing(true).setContentIntent(open).build();
        Intrinsics.checkNotNullExpressionValue(build, "build(...)");
        return build;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void update() {
        BleLink bleLink = link;
        boolean z = false;
        if (bleLink != null && bleLink.getReady()) {
            z = true;
        }
        boolean ready = z;
        if (ready && !this.wasReady) {
            Hotspot.INSTANCE.onComma(this);
            AutoEvents.INSTANCE.syncSound(this);
            main.postDelayed(new Runnable() { // from class: com.starpilot.starlock.KeeperService$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    KeeperService.update$lambda$10();
                }
            }, 300L);
        }
        this.wasReady = ready;
        String t = ready ? "Connected to the comma - lock / unlock is instant" : "Waiting for the comma (connects when in range)";
        try {
            Result.Companion companion = Result.Companion;
            KeeperService $this$update_u24lambda_u2411 = this;
            ((NotificationManager) $this$update_u24lambda_u2411.getSystemService(NotificationManager.class)).notify(1, $this$update_u24lambda_u2411.note(t));
            KtCompat.resultOf(Unit.INSTANCE);
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            KtCompat.resultOf(ResultKt.createFailure(th));
        }
        main.post(new Runnable() { // from class: com.starpilot.starlock.KeeperService$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                KeeperService.update$lambda$12();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void update$lambda$10() {
        CarLocks.INSTANCE.ask();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void update$lambda$12() {
        Function0<Unit> function0 = onState;
        if (function0 != null) {
            function0.invoke();
        }
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int flags, int startId) {
        Notification n = note("Waiting for the comma (connects when in range)");
        startForeground(1, n, 16);
        if (!this.alive) {
            this.alive = true;
            connect();
        }
        return 1;
    }

    private final void connect() {
        new Thread(new Runnable() { // from class: com.starpilot.starlock.KeeperService$$ExternalSyntheticLambda14
            @Override // java.lang.Runnable
            public final void run() {
                KeeperService.connect$lambda$19(KeeperService.this);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void connect$lambda$19(final KeeperService this$0) {
        BluetoothAdapter bt;
        Object m61constructorimpl;
        Object m61constructorimpl2;
        Pairing p = Door.INSTANCE.pairing(this$0);
        if (p == null) {
            this$0.stopSelf();
            return;
        }
        SharedPreferences prefs = Door.INSTANCE.prefs(this$0);
        BluetoothManager bluetoothManager = (BluetoothManager) this$0.getSystemService(BluetoothManager.class);
        if (bluetoothManager == null || (bt = bluetoothManager.getAdapter()) == null) {
            this$0.stopSelf();
            return;
        }
        final BleLink l = new BleLink(this$0, p, new Function1() { // from class: com.starpilot.starlock.KeeperService$$ExternalSyntheticLambda16
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit connect$lambda$19$lambda$13;
                connect$lambda$19$lambda$13 = KeeperService.connect$lambda$19$lambda$13((String) obj);
                return connect$lambda$19$lambda$13;
            }
        });
        l.setOnState(new Function0() { // from class: com.starpilot.starlock.KeeperService$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit connect$lambda$19$lambda$14;
                connect$lambda$19$lambda$14 = KeeperService.connect$lambda$19$lambda$14(this$0);
                return connect$lambda$19$lambda$14;
            }
        });
        String addr = prefs.getString("ble", null);
        while (addr == null && this$0.alive) {
            try {
                Result.Companion companion = Result.Companion;
                m61constructorimpl2 = KtCompat.resultOf(l.scan(new Function0() { // from class: com.starpilot.starlock.KeeperService$$ExternalSyntheticLambda2
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        boolean connect$lambda$19$lambda$16$lambda$15;
                        connect$lambda$19$lambda$16$lambda$15 = KeeperService.connect$lambda$19$lambda$16$lambda$15(this$0);
                        return Boolean.valueOf(connect$lambda$19$lambda$16$lambda$15);
                    }
                }));
            } catch (Throwable th) {
                Result.Companion companion2 = Result.Companion;
                m61constructorimpl2 = KtCompat.resultOf(ResultKt.createFailure(th));
            }
            if (KtCompat.isFailure(m61constructorimpl2)) {
                m61constructorimpl2 = null;
            }
            BluetoothDevice bluetoothDevice = (BluetoothDevice) m61constructorimpl2;
            addr = bluetoothDevice != null ? bluetoothDevice.getAddress() : null;
            if (addr == null) {
                Thread.sleep(15000L);
            }
        }
        if (this$0.alive && addr != null) {
            prefs.edit().putString("ble", addr).apply();
            this$0.device = bt.getRemoteDevice(addr);
            link = l;
            this$0.reconnect("starting");
            this$0.update();
            int waited = 0;
            while (this$0.alive) {
                Thread.sleep(60000L);
                waited++;
                if (l.getReady() || !this$0.alive) {
                    waited = 0;
                } else if (waited == 1 || waited % 10 == 0) {
                    try {
                        Result.Companion companion3 = Result.Companion;
                        m61constructorimpl = KtCompat.resultOf(l.scan(new Function0() { // from class: com.starpilot.starlock.KeeperService$$ExternalSyntheticLambda3
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                boolean connect$lambda$19$lambda$18$lambda$17;
                                connect$lambda$19$lambda$18$lambda$17 = KeeperService.connect$lambda$19$lambda$18$lambda$17(this$0, l);
                                return Boolean.valueOf(connect$lambda$19$lambda$18$lambda$17);
                            }
                        }));
                    } catch (Throwable th2) {
                        Result.Companion companion4 = Result.Companion;
                        m61constructorimpl = KtCompat.resultOf(ResultKt.createFailure(th2));
                    }
                    if (KtCompat.isFailure(m61constructorimpl)) {
                        m61constructorimpl = null;
                    }
                    BluetoothDevice found = (BluetoothDevice) m61constructorimpl;
                    if (found != null && !Intrinsics.areEqual(found.getAddress(), addr) && this$0.alive) {
                        addr = found.getAddress();
                        prefs.edit().putString("ble", addr).apply();
                        AppLog.INSTANCE.log("Bluetooth: the comma is at a new address now");
                        this$0.device = found;
                        this$0.reconnect("new address");
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit connect$lambda$19$lambda$13(String it) {
        Intrinsics.checkNotNullParameter(it, "it");
        AppLog.INSTANCE.log(it);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit connect$lambda$19$lambda$14(KeeperService this$0) {
        this$0.update();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean connect$lambda$19$lambda$16$lambda$15(KeeperService $this_runCatching) {
        return !$this_runCatching.alive;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean connect$lambda$19$lambda$18$lambda$17(KeeperService $this_runCatching, BleLink $l) {
        return !$this_runCatching.alive || $l.getReady();
    }

    @Override // android.app.Service
    public void onDestroy() {
        this.alive = false;
        instance = null;
        try {
            Result.Companion companion = Result.Companion;
            KeeperService $this$onDestroy_u24lambda_u2420 = this;
            $this$onDestroy_u24lambda_u2420.unregisterReceiver($this$onDestroy_u24lambda_u2420.btSwitch);
            KtCompat.resultOf(Unit.INSTANCE);
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            KtCompat.resultOf(ResultKt.createFailure(th));
        }
        BleLink bleLink = link;
        if (bleLink != null) {
            bleLink.close();
        }
        link = null;
        main.post(new Runnable() { // from class: com.starpilot.starlock.KeeperService$$ExternalSyntheticLambda15
            @Override // java.lang.Runnable
            public final void run() {
                KeeperService.onDestroy$lambda$21();
            }
        });
        super.onDestroy();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onDestroy$lambda$21() {
        Function0<Unit> function0 = onState;
        if (function0 != null) {
            function0.invoke();
        }
    }
}
