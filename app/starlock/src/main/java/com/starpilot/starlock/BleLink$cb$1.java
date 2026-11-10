package com.starpilot.starlock;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import androidx.core.app.NotificationCompat;
import java.util.concurrent.LinkedBlockingQueue;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import org.json.JSONObject;

/* compiled from: BleLink.kt */
public final class BleLink$cb$1 extends BluetoothGattCallback {
    final /* synthetic */ BleLink this$0;

    BleLink$cb$1(BleLink $receiver) {
        this.this$0 = $receiver;
    }

    @Override // android.bluetooth.BluetoothGattCallback
    public void onConnectionStateChange(BluetoothGatt g, int status, int newState) {
        LinkedBlockingQueue linkedBlockingQueue;
        Function1 function1;
        boolean z;
        Intrinsics.checkNotNullParameter(g, "g");
        switch (newState) {
            case 0:
                boolean was = this.this$0.getReady();
                this.this$0.ready = false;
                linkedBlockingQueue = this.this$0.events;
                linkedBlockingQueue.offer(new Exception("disconnected"));
                if (was) {
                    function1 = this.this$0.log;
                    z = this.this$0.persistent;
                    function1.invoke("Bluetooth: connection dropped" + (z ? " - reconnects when in range" : ""));
                    Function0<Unit> onDrop = this.this$0.getOnDrop();
                    if (onDrop != null) {
                        onDrop.invoke();
                    }
                    Function0<Unit> onState = this.this$0.getOnState();
                    if (onState != null) {
                        onState.invoke();
                        break;
                    }
                }
                break;
            case 2:
                g.requestMtu(247);
                break;
        }
    }

    @Override // android.bluetooth.BluetoothGattCallback
    public void onMtuChanged(BluetoothGatt g, int mtu, int status) {
        Intrinsics.checkNotNullParameter(g, "g");
        g.discoverServices();
    }

    @Override // android.bluetooth.BluetoothGattCallback
    public void onReadRemoteRssi(BluetoothGatt g, int rssi, int status) {
        Function1<Integer, Unit> onRssi;
        Intrinsics.checkNotNullParameter(g, "g");
        if (status != 0 || (onRssi = this.this$0.getOnRssi()) == null) {
            return;
        }
        onRssi.invoke(Integer.valueOf(rssi));
    }

    @Override // android.bluetooth.BluetoothGattCallback
    public void onServicesDiscovered(final BluetoothGatt g, int status) {
        Function1 function1;
        LinkedBlockingQueue linkedBlockingQueue;
        BluetoothGattCharacteristic tx;
        Intrinsics.checkNotNullParameter(g, "g");
        BluetoothGattService service = g.getService(BleLink.Companion.getSERVICE());
        if (service == null || (tx = service.getCharacteristic(BleLink.Companion.getTX())) == null) {
            final BleLink bleLink = this.this$0;
            final BleLink$cb$1 $this$onServicesDiscovered_u24lambda_u243 = this;
            function1 = bleLink.log;
            function1.invoke("Bluetooth: reached the comma, but no StarLock service there (" + g.getServices().size() + " services) - looking again");
            linkedBlockingQueue = bleLink.events;
            linkedBlockingQueue.offer(new Exception("no StarLock service"));
            try {
                Result.Companion companion = Result.Companion;
                KtCompat.resultOf(g.getClass().getMethod("refresh", new Class[0]).invoke(g, new Object[0]));
            } catch (Throwable th) {
                Result.Companion companion2 = Result.Companion;
                KtCompat.resultOf(ResultKt.createFailure(th));
            }
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.starpilot.starlock.BleLink$cb$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    BleLink$cb$1.onServicesDiscovered$lambda$3$lambda$2(bleLink, g, $this$onServicesDiscovered_u24lambda_u243);
                }
            }, 4000L);
            return;
        }
        g.setCharacteristicNotification(tx, true);
        g.writeDescriptor(tx.getDescriptor(BleLink.Companion.getCCCD()), BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onServicesDiscovered$lambda$3$lambda$2(BleLink this$0, BluetoothGatt $g, BleLink$cb$1 $this_run) {
        BluetoothGatt bluetoothGatt;
        bluetoothGatt = this$0.gatt;
        if (bluetoothGatt == $g && !this$0.getReady()) {
            try {
                Result.Companion companion = Result.Companion;
                KtCompat.resultOf(Boolean.valueOf($g.discoverServices()));
            } catch (Throwable th) {
                Result.Companion companion2 = Result.Companion;
                KtCompat.resultOf(ResultKt.createFailure(th));
            }
        }
    }

    @Override // android.bluetooth.BluetoothGattCallback
    public void onDescriptorWrite(BluetoothGatt g, BluetoothGattDescriptor d, int status) {
        boolean z;
        LinkedBlockingQueue linkedBlockingQueue;
        Function1 function1;
        Function1 function12;
        Intrinsics.checkNotNullParameter(g, "g");
        Intrinsics.checkNotNullParameter(d, "d");
        if (status != 0) {
            function12 = this.this$0.log;
            function12.invoke("Bluetooth: subscribing failed (status " + status + ")");
        }
        z = this.this$0.persistent;
        if (z) {
            this.this$0.ready = true;
            function1 = this.this$0.log;
            function1.invoke("Bluetooth: connected to the comma (background)");
            Function0<Unit> onState = this.this$0.getOnState();
            if (onState != null) {
                onState.invoke();
            }
        }
        linkedBlockingQueue = this.this$0.events;
        linkedBlockingQueue.offer("ready");
    }

    @Override // android.bluetooth.BluetoothGattCallback
    public void onCharacteristicWrite(BluetoothGatt g, BluetoothGattCharacteristic c, int status) {
        Function1 function1;
        Intrinsics.checkNotNullParameter(g, "g");
        Intrinsics.checkNotNullParameter(c, "c");
        if (status == 0) {
            return;
        }
        if (status == 1) {
            this.this$0.relearn(g, "the comma's StarLock service restarted");
        } else {
            function1 = this.this$0.log;
            function1.invoke("Bluetooth: write failed (status " + status + ")");
        }
    }

    @Override // android.bluetooth.BluetoothGattCallback
    public void onServiceChanged(BluetoothGatt g) {
        Intrinsics.checkNotNullParameter(g, "g");
        this.this$0.relearn(g, "the comma's StarLock service changed");
    }

    @Override // android.bluetooth.BluetoothGattCallback
    public void onCharacteristicChanged(BluetoothGatt g, BluetoothGattCharacteristic c, byte[] value) {
        Object m61constructorimpl;
        LinkedBlockingQueue linkedBlockingQueue;
        Context context;
        Context context2;
        Intrinsics.checkNotNullParameter(g, "g");
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(value, "value");
        try {
            Result.Companion companion = Result.Companion;
            BleLink$cb$1 bleLink$cb$1 = this;
            m61constructorimpl = KtCompat.resultOf(new JSONObject(new String(value, Charsets.UTF_8)));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            m61constructorimpl = KtCompat.resultOf(ResultKt.createFailure(th));
        }
        if (KtCompat.isFailure(m61constructorimpl)) {
            m61constructorimpl = null;
        }
        JSONObject j = (JSONObject) m61constructorimpl;
        if (j == null) {
            return;
        }
        String optString = j.optString("op");
        if (Intrinsics.areEqual(optString, "auto")) {
            AutoEvents autoEvents = AutoEvents.INSTANCE;
            context2 = this.this$0.ctx;
            autoEvents.on(context2, j);
        } else if (Intrinsics.areEqual(optString, "locks")) {
            CarLocks carLocks = CarLocks.INSTANCE;
            context = this.this$0.ctx;
            carLocks.on(context, j);
        } else {
            linkedBlockingQueue = this.this$0.events;
            linkedBlockingQueue.offer(j);
        }
    }
}
