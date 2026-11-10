package com.starpilot.starlock;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelUuid;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: BleLink.kt */
public final class BleLink {
    private static final UUID CCCD;

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion Companion = new Companion(null);
    private static final UUID RX;
    private static final UUID SERVICE;
    private static final UUID TX;
    private volatile String address;
    private final BleLink$cb$1 cb;
    final Context ctx;
    final LinkedBlockingQueue<Object> events;
    volatile BluetoothGatt gatt;
    private final Object io;
    final Function1<String, Unit> log;
    private volatile Function0<Unit> onDrop;
    private volatile Function1<? super Integer, Unit> onRssi;
    private volatile Function0<Unit> onState;
    private final Pairing p;
    volatile boolean persistent;
    volatile boolean ready;
    private volatile boolean relearning;

    /* compiled from: BleLink.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final UUID getSERVICE() {
            return BleLink.SERVICE;
        }

        public final UUID getRX() {
            return BleLink.RX;
        }

        public final UUID getTX() {
            return BleLink.TX;
        }

        public final UUID getCCCD() {
            return BleLink.CCCD;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public BleLink(Context ctx, Pairing p, Function1<? super String, Unit> log) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(p, "p");
        Intrinsics.checkNotNullParameter(log, "log");
        this.ctx = ctx;
        this.p = p;
        this.log = (Function1) log;
        this.events = new LinkedBlockingQueue<>();
        this.cb = new BleLink$cb$1(this);
        this.io = new Object();
    }

    static {
        UUID fromString = UUID.fromString("5a1c0001-7374-6172-6c6f-636b00000000");
        Intrinsics.checkNotNullExpressionValue(fromString, "fromString(...)");
        SERVICE = fromString;
        UUID fromString2 = UUID.fromString("5a1c0002-7374-6172-6c6f-636b00000000");
        Intrinsics.checkNotNullExpressionValue(fromString2, "fromString(...)");
        RX = fromString2;
        UUID fromString3 = UUID.fromString("5a1c0003-7374-6172-6c6f-636b00000000");
        Intrinsics.checkNotNullExpressionValue(fromString3, "fromString(...)");
        TX = fromString3;
        UUID fromString4 = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
        Intrinsics.checkNotNullExpressionValue(fromString4, "fromString(...)");
        CCCD = fromString4;
    }

    private final BluetoothDevice find(Function0<Boolean> stop) {
        BluetoothAdapter bt;
        BluetoothManager bluetoothManager = (BluetoothManager) this.ctx.getSystemService(BluetoothManager.class);
        if (bluetoothManager == null || (bt = bluetoothManager.getAdapter()) == null) {
            return null;
        }
        if (!bt.isEnabled()) {
            this.log.invoke("Bluetooth is off");
            return null;
        }
        BluetoothLeScanner scanner = bt.getBluetoothLeScanner();
        if (scanner == null) {
            return null;
        }
        final LinkedBlockingQueue q = new LinkedBlockingQueue();
        ScanCallback scanCallback = new ScanCallback() { // from class: com.starpilot.starlock.BleLink$find$cb$1
            @Override // android.bluetooth.le.ScanCallback
            public void onScanResult(int callbackType, ScanResult result) {
                Intrinsics.checkNotNullParameter(result, "result");
                q.offer(result.getDevice());
            }
        };
        scanner.startScan(CollectionsKt.listOf(new ScanFilter.Builder().setServiceUuid(new ParcelUuid(SERVICE)).build()), new ScanSettings.Builder().setScanMode(2).build(), scanCallback);
        try {
            long end = System.currentTimeMillis() + 8000;
            while (System.currentTimeMillis() < end && !stop.invoke().booleanValue()) {
                BluetoothDevice bluetoothDevice = (BluetoothDevice) q.poll(200L, TimeUnit.MILLISECONDS);
                if (bluetoothDevice != null) {
                    Result.Companion companion = Result.Companion;
                    scanner.stopScan(scanCallback);
                    KtCompat.resultOf(Unit.INSTANCE);
                    return bluetoothDevice;
                }
            }
            try {
                Result.Companion companion2 = Result.Companion;
                BleLink bleLink = this;
                scanner.stopScan(scanCallback);
                KtCompat.resultOf(Unit.INSTANCE);
            } catch (Throwable th) {
                Result.Companion companion3 = Result.Companion;
                KtCompat.resultOf(ResultKt.createFailure(th));
            }
            return null;
        } catch (Throwable th2) {
            try {
                Result.Companion companion4 = Result.Companion;
                scanner.stopScan(scanCallback);
                KtCompat.resultOf(Unit.INSTANCE);
            } catch (Throwable th3) {
                Result.Companion companion5 = Result.Companion;
                KtCompat.resultOf(ResultKt.createFailure(th3));
            }
            throw th2;
        }
    }

    public final boolean getReady() {
        return this.ready;
    }

    public final String getAddress() {
        return this.address;
    }

    public final Function0<Unit> getOnDrop() {
        return this.onDrop;
    }

    public final void setOnDrop(Function0<Unit> function0) {
        this.onDrop = function0;
    }

    public final Function0<Unit> getOnState() {
        return this.onState;
    }

    public final void setOnState(Function0<Unit> function0) {
        this.onState = function0;
    }

    public final Function1<Integer, Unit> getOnRssi() {
        return (Function1) this.onRssi;
    }

    public final void setOnRssi(Function1<? super Integer, Unit> function1) {
        this.onRssi = function1;
    }

    public final boolean readRssi() {
        Object m61constructorimpl;
        if (!this.ready) {
            return false;
        }
        try {
            Result.Companion companion = Result.Companion;
            BleLink $this$readRssi_u24lambda_u242 = this;
            BluetoothGatt bluetoothGatt = $this$readRssi_u24lambda_u242.gatt;
            m61constructorimpl = KtCompat.resultOf(Boolean.valueOf(bluetoothGatt != null && bluetoothGatt.readRemoteRssi()));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            m61constructorimpl = KtCompat.resultOf(ResultKt.createFailure(th));
        }
        if (KtCompat.isFailure(m61constructorimpl)) {
            m61constructorimpl = false;
        }
        return ((Boolean) m61constructorimpl).booleanValue();
    }

    private final Object next(long ms) {
        return this.events.poll(ms, TimeUnit.MILLISECONDS);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void relearn(final BluetoothGatt g, String why) {
        if (this.relearning) {
            return;
        }
        this.relearning = true;
        this.log.invoke("Bluetooth: " + why + " - reading it again");
        this.ready = false;
        Function0<Unit> function0 = this.onState;
        if (function0 != null) {
            function0.invoke();
        }
        this.events.offer(new Exception("relearning"));
        try {
            Result.Companion companion = Result.Companion;
            BleLink bleLink = this;
            KtCompat.resultOf(g.getClass().getMethod("refresh", new Class[0]).invoke(g, new Object[0]));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            KtCompat.resultOf(ResultKt.createFailure(th));
        }
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.starpilot.starlock.BleLink$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                BleLink.relearn$lambda$5(BleLink.this, g);
            }
        }, 600L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void relearn$lambda$5(BleLink this$0, BluetoothGatt $g) {
        this$0.relearning = false;
        if (this$0.gatt == $g) {
            try {
                Result.Companion companion = Result.Companion;
                KtCompat.resultOf(Boolean.valueOf($g.discoverServices()));
            } catch (Throwable th) {
                Result.Companion companion2 = Result.Companion;
                KtCompat.resultOf(ResultKt.createFailure(th));
            }
        }
    }

    private final boolean connect(BluetoothDevice dev, long ms) {
        close();
        this.events.clear();
        this.gatt = dev.connectGatt(this.ctx, false, this.cb, 2);
        this.ready = Intrinsics.areEqual(next(ms), "ready");
        if (this.ready) {
            this.address = dev.getAddress();
            this.log.invoke("Bluetooth: connected to the comma");
        } else {
            close();
        }
        return this.ready;
    }

    public final boolean open(String known, Function0<Boolean> stop) {
        BluetoothAdapter bt;
        Object m61constructorimpl;
        BluetoothDevice dev;
        Intrinsics.checkNotNullParameter(stop, "stop");
        BluetoothManager bluetoothManager = (BluetoothManager) this.ctx.getSystemService(BluetoothManager.class);
        if (bluetoothManager == null || (bt = bluetoothManager.getAdapter()) == null || !bt.isEnabled()) {
            return false;
        }
        if (known != null) {
            try {
                Result.Companion companion = Result.Companion;
                BleLink bleLink = this;
                m61constructorimpl = KtCompat.resultOf(bt.getRemoteDevice(known));
            } catch (Throwable th) {
                Result.Companion companion2 = Result.Companion;
                m61constructorimpl = KtCompat.resultOf(ResultKt.createFailure(th));
            }
            if (KtCompat.isFailure(m61constructorimpl)) {
                m61constructorimpl = null;
            }
            BluetoothDevice it = (BluetoothDevice) m61constructorimpl;
            if (it != null && connect(it, 1500L)) {
                return true;
            }
        }
        return (stop.invoke().booleanValue() || (dev = find(stop)) == null || stop.invoke().booleanValue() || !connect(dev, 8000L)) ? false : true;
    }

    public static /* synthetic */ void keep$default(BleLink bleLink, BluetoothDevice bluetoothDevice, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        bleLink.keep(bluetoothDevice, z);
    }

    public final void keep(BluetoothDevice dev, boolean fast) {
        Intrinsics.checkNotNullParameter(dev, "dev");
        close();
        this.persistent = true;
        this.events.clear();
        this.address = dev.getAddress();
        this.gatt = dev.connectGatt(this.ctx, !fast, this.cb, 2);
    }

    private final void kick() {
        Object m61constructorimpl;
        this.ready = false;
        Function0<Unit> function0 = this.onState;
        if (function0 != null) {
            function0.invoke();
        }
        if (this.persistent) {
            try {
                Result.Companion companion = Result.Companion;
                BleLink $this$kick_u24lambda_u248 = this;
                BluetoothGatt bluetoothGatt = $this$kick_u24lambda_u248.gatt;
                if (bluetoothGatt != null) {
                    bluetoothGatt.disconnect();
                }
                BluetoothGatt bluetoothGatt2 = $this$kick_u24lambda_u248.gatt;
                m61constructorimpl = KtCompat.resultOf(bluetoothGatt2 != null ? Boolean.valueOf(bluetoothGatt2.connect()) : null);
            } catch (Throwable th) {
                Result.Companion companion2 = Result.Companion;
                m61constructorimpl = KtCompat.resultOf(ResultKt.createFailure(th));
            }
            KtCompat.box(m61constructorimpl);
            return;
        }
        close();
    }

    public final BluetoothDevice scan(Function0<Boolean> stop) {
        Intrinsics.checkNotNullParameter(stop, "stop");
        return find(stop);
    }

    public final void close() {
        Unit unit;
        this.persistent = false;
        this.onDrop = null;
        this.ready = false;
        try {
            Result.Companion companion = Result.Companion;
            BleLink $this$close_u24lambda_u249 = this;
            BluetoothGatt bluetoothGatt = $this$close_u24lambda_u249.gatt;
            if (bluetoothGatt != null) {
                bluetoothGatt.disconnect();
            }
            BluetoothGatt bluetoothGatt2 = $this$close_u24lambda_u249.gatt;
            if (bluetoothGatt2 != null) {
                bluetoothGatt2.close();
                unit = Unit.INSTANCE;
            } else {
                unit = null;
            }
            KtCompat.resultOf(unit);
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            KtCompat.resultOf(ResultKt.createFailure(th));
        }
        this.gatt = null;
    }

    public static /* synthetic */ JSONObject request$default(BleLink bleLink, String str, JSONObject jSONObject, boolean z, long j, int i, Object obj) {
        if ((i & 2) != 0) {
            jSONObject = new JSONObject();
        }
        JSONObject jSONObject2 = jSONObject;
        if ((i & 4) != 0) {
            z = true;
        }
        boolean z2 = z;
        if ((i & 8) != 0) {
            j = 3000;
        }
        return bleLink.request(str, jSONObject2, z2, j);
    }

    public final JSONObject request(String op, JSONObject fields, boolean prove, long waitMs) {
        Intrinsics.checkNotNullParameter(op, "op");
        Intrinsics.checkNotNullParameter(fields, "fields");
        synchronized (this.io) {
            try {
                BluetoothGatt g = this.gatt;
                if (g == null) {
                    return null;
                }
                if (!this.ready) {
                    return null;
                }
                do {
                } while (this.events.poll() != null);
                JSONObject msg = new JSONObject(fields.toString()).put("op", op);
                if (prove) {
                    String cn = Pairing.Companion.nonce();
                    JSONObject put = new JSONObject().put("op", "hello").put("cn", cn);
                    Intrinsics.checkNotNullExpressionValue(put, "put(...)");
                    request$lambda$10$write(g, put);
                    Object next = next(1500L);
                    JSONObject h = next instanceof JSONObject ? (JSONObject) next : null;
                    if (h == null) {
                        return null;
                    }
                    String d = h.optString("dongle");
                    String sn = h.optString("sn");
                    String optString = h.optString("proof");
                    Pairing pairing = this.p;
                    Intrinsics.checkNotNull(d);
                    Intrinsics.checkNotNull(sn);
                    if (!Intrinsics.areEqual(optString, pairing.serverProof(d, cn, sn))) {
                        return null;
                    }
                    if ((this.p.getDongle().length() > 0) && !Intrinsics.areEqual(d, this.p.getDongle())) {
                        return null;
                    }
                    msg.put("sn", sn).put("cn", cn).put("proof", this.p.clientProof(d, sn, cn));
                }
                Intrinsics.checkNotNull(msg);
                request$lambda$10$write(g, msg);
                try {
                    Object next2 = next(waitMs);
                    return next2 instanceof JSONObject ? (JSONObject) next2 : null;
                } catch (Exception e) {
                    return null;
                }
            } catch (Exception e) {
                return null;
            }
        }
    }

    static final void request$lambda$10$write(BluetoothGatt g, JSONObject j) {
        BluetoothGattCharacteristic rx;
        BluetoothGattService service = g.getService(SERVICE);
        if (service == null || (rx = service.getCharacteristic(RX)) == null) {
            throw new IllegalStateException("no StarLock service");
        }
        String jSONObject = j.toString();
        Intrinsics.checkNotNullExpressionValue(jSONObject, "toString(...)");
        byte[] bytes = jSONObject.getBytes(Charsets.UTF_8);
        Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
        g.writeCharacteristic(rx, bytes, 2);
    }

    public static /* synthetic */ boolean post$default(BleLink bleLink, String str, JSONObject jSONObject, int i, Object obj) {
        if ((i & 2) != 0) {
            jSONObject = new JSONObject();
        }
        return bleLink.post(str, jSONObject);
    }

    public final boolean post(String op, JSONObject fields) {
        Intrinsics.checkNotNullParameter(op, "op");
        Intrinsics.checkNotNullParameter(fields, "fields");
        synchronized (this.io) {
            BluetoothGatt g = this.gatt;
            if (g == null) {
                return false;
            }
            if (!this.ready) {
                return false;
            }
            try {
                BluetoothGattService service = g.getService(SERVICE);
                BluetoothGattCharacteristic rx = service != null ? service.getCharacteristic(RX) : null;
                if (rx == null) {
                    return false;
                }
                String jSONObject = new JSONObject(fields.toString()).put("op", op).toString();
                Intrinsics.checkNotNullExpressionValue(jSONObject, "toString(...)");
                byte[] bytes = jSONObject.getBytes(Charsets.UTF_8);
                Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
                g.writeCharacteristic(rx, bytes, 2);
                Thread.sleep(150L);
                return true;
            } catch (Throwable th) {
                return false;
            }
        }
    }

    public final JSONObject send(String cmd, String sound, JSONArray beeps, Function0<Boolean> stop) {
        Intrinsics.checkNotNullParameter(cmd, "cmd");
        Intrinsics.checkNotNullParameter(sound, "sound");
        Intrinsics.checkNotNullParameter(stop, "stop");
        synchronized (this.io) {
            BluetoothGatt g = this.gatt;
            if (g == null) {
                return null;
            }
            if (!this.ready) {
                return null;
            }
            try {
                do {
                } while (this.events.poll() != null);
                String cn = Pairing.Companion.nonce();
                JSONObject put = new JSONObject().put("op", "hello").put("cn", cn);
                Intrinsics.checkNotNullExpressionValue(put, "put(...)");
                send$lambda$17$write$13(g, put);
                Object next = next(1500L);
                JSONObject h = next instanceof JSONObject ? (JSONObject) next : null;
                if (h == null) {
                    this.log.invoke("Bluetooth: no answer to hello");
                    kick();
                    return null;
                }
                String d = h.optString("dongle");
                String sn = h.optString("sn");
                String optString = h.optString("proof");
                Pairing pairing = this.p;
                Intrinsics.checkNotNull(d);
                Intrinsics.checkNotNull(sn);
                if (Intrinsics.areEqual(optString, pairing.serverProof(d, cn, sn))
                        && (!(this.p.getDongle().length() > 0) || Intrinsics.areEqual(d, this.p.getDongle()))) {
                    if (stop.invoke().booleanValue()) {
                        return null;
                    }
                    JSONObject $this$send_u24lambda_u2417_u24lambda_u2415 = new JSONObject().put("op", "door").put("cmd", cmd).put("sound", sound);
                    if (beeps != null) {
                        $this$send_u24lambda_u2417_u24lambda_u2415.put("beeps", beeps);
                    }
                    JSONObject put2 = $this$send_u24lambda_u2417_u24lambda_u2415.put("sn", sn).put("cn", cn).put("proof", this.p.clientProof(d, sn, cn));
                    Intrinsics.checkNotNullExpressionValue(put2, "put(...)");
                    send$lambda$17$write$13(g, put2);
                    Object next2 = next(6000L);
                    JSONObject reply = next2 instanceof JSONObject ? (JSONObject) next2 : null;
                    if (reply == null) {
                        this.log.invoke("Bluetooth: no answer to lock/unlock");
                        kick();
                    }
                    return reply;
                }
                this.log.invoke("Bluetooth: not your comma");
                return null;
            } catch (Exception e) {
                this.log.invoke("Bluetooth: " + e.getMessage());
                kick();
                return null;
            }
        }
    }

    static final void send$lambda$17$write$13(BluetoothGatt g, JSONObject j) {
        BluetoothGattCharacteristic rx;
        BluetoothGattService service = g.getService(SERVICE);
        if (service == null || (rx = service.getCharacteristic(RX)) == null) {
            throw new IllegalStateException("no StarLock service");
        }
        String jSONObject = j.toString();
        Intrinsics.checkNotNullExpressionValue(jSONObject, "toString(...)");
        byte[] bytes = jSONObject.getBytes(Charsets.UTF_8);
        Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
        g.writeCharacteristic(rx, bytes, 2);
    }

    public final Pair<String, JSONObject> door(String cmd, String sound, JSONArray beeps, String known, Function0<Boolean> stop) {
        Intrinsics.checkNotNullParameter(cmd, "cmd");
        Intrinsics.checkNotNullParameter(sound, "sound");
        Intrinsics.checkNotNullParameter(stop, "stop");
        Pair<String, JSONObject> pair = null;
        if (!open(known, stop)) {
            return null;
        }
        try {
            JSONObject it = send(cmd, sound, beeps, stop);
            if (it != null) {
                String str = this.address;
                if (str == null) {
                    str = "";
                }
                pair = TuplesKt.to(str, it);
            }
            return pair;
        } finally {
            close();
        }
    }
}
