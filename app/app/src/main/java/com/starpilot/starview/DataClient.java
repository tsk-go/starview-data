package com.starpilot.starview;

import android.os.Handler;
import android.os.Looper;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import org.capnproto.ReaderOptions;
import org.json.JSONObject;

/* compiled from: DataClient.kt */
public final class DataClient {
    private static final String TAG = "StarView/Data";
    private volatile boolean connected;
    private JSONObject hello;
    private final OkHttpClient http;
    private final Handler main;
    private final Function2<String, Boolean, Unit> onStatus;
    private final ConcurrentHashMap<String, Function1<JSONObject, Unit>> pending;
    private long pingSentNs;
    private final AtomicInteger reqCounter;
    private final Telemetry telemetry;
    private WebSocket ws;
    public static final Companion Companion = new Companion(null);
    private static final Map<String, Integer> SUBSCRIBE = MapsKt.mapOf(TuplesKt.to("carState", 20), TuplesKt.to("selfdriveState", 20), TuplesKt.to("controlsState", 10), TuplesKt.to("carControl", 10), TuplesKt.to("carOutput", 10), TuplesKt.to("liveParameters", 5), TuplesKt.to("longitudinalPlan", 5), TuplesKt.to("deviceState", 0), TuplesKt.to("roadCameraState", 1), TuplesKt.to("liveCalibration", 0), TuplesKt.to("modelV2", 0), TuplesKt.to("radarState", 0), TuplesKt.to("driverMonitoringState", 10), TuplesKt.to("slcState", 5), TuplesKt.to("starpilotLateralState", 10), TuplesKt.to("liveDelay", 1), TuplesKt.to("liveTorqueParameters", 1), TuplesKt.to("carParams", 0), TuplesKt.to("starpilotPlan", 0), TuplesKt.to("starpilotCarState", 10), TuplesKt.to("starpilotRadarState", 10), TuplesKt.to("mapdOut", 0), TuplesKt.to("liveTracks", 10), TuplesKt.to("gpsLocationExternal", 0), TuplesKt.to("gpsLocation", 0), TuplesKt.to("livePose", 20), TuplesKt.to("accelerometer", 10), TuplesKt.to("gyroscope", 10), TuplesKt.to("ubloxGnss", 0));
    private static final ReaderOptions READER_OPTS = new ReaderOptions(8388608, 64);

    /* compiled from: DataClient.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final Map<String, Integer> getSUBSCRIBE() {
            return DataClient.SUBSCRIBE;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public DataClient(Telemetry telemetry, Function2<? super String, ? super Boolean, Unit> onStatus) {
        Intrinsics.checkNotNullParameter(telemetry, "telemetry");
        Intrinsics.checkNotNullParameter(onStatus, "onStatus");
        this.telemetry = telemetry;
        this.onStatus = onStatus;
        this.http = new OkHttpClient.Builder().pingInterval(5L, TimeUnit.SECONDS).readTimeout(0L, TimeUnit.MILLISECONDS).build();
        this.pending = new ConcurrentHashMap<>();
        this.main = new Handler(Looper.getMainLooper());
        this.reqCounter = new AtomicInteger(1);
    }

    public final boolean getConnected() {
        return this.connected;
    }

    public final JSONObject getHello() {
        return this.hello;
    }

    public final void connect(String host, int port) {
        Intrinsics.checkNotNullParameter(host, "host");
        close();
        Request req = Link.INSTANCE.withSession(new Request.Builder().url("ws://" + host + ":" + port + "/data"), host, port).build();
        this.ws = this.http.newWebSocket(req, new DataClient$connect$1(this));
    }

    public final void control(JSONObject req) {
        Intrinsics.checkNotNullParameter(req, "req");
        WebSocket webSocket = this.ws;
        if (webSocket != null) {
            String jSONObject = req.toString();
            Intrinsics.checkNotNullExpressionValue(jSONObject, "toString(...)");
            webSocket.send(jSONObject);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void request$default(DataClient dataClient, String str, Map map, Function1 function1, int i, Object obj) {
        if ((i & 2) != 0) {
            map = MapsKt.emptyMap();
        }
        if ((i & 4) != 0) {
            function1 = null;
        }
        dataClient.request(str, map, function1);
    }

    public final void request(String action, Map<String, ? extends Object> args, final Function1<? super JSONObject, Unit> cb) {
        boolean sent;
        Intrinsics.checkNotNullParameter(action, "action");
        Intrinsics.checkNotNullParameter(args, "args");
        final String id = "r" + this.reqCounter.getAndIncrement();
        JSONObject j = new JSONObject();
        j.put("action", action);
        j.put("id", id);
        for (Map.Entry<String, ? extends Object> entry : args.entrySet()) {
            String k = entry.getKey();
            Object v = entry.getValue();
            j.put(k, v);
        }
        if (cb != null) {
            this.pending.put(id, cb);
            this.main.postDelayed(new Runnable() { // from class: com.starpilot.starview.DataClient$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    DataClient.request$lambda$0(DataClient.this, id);
                }
            }, 90000L);
        }
        WebSocket webSocket = this.ws;
        if (webSocket != null) {
            String jSONObject = j.toString();
            Intrinsics.checkNotNullExpressionValue(jSONObject, "toString(...)");
            sent = webSocket.send(jSONObject);
        } else {
            sent = false;
        }
        if (sent || cb == null) {
            return;
        }
        this.pending.remove(id);
        this.main.post(new Runnable() { // from class: com.starpilot.starview.DataClient$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                DataClient.request$lambda$1(Function1.this);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void request$lambda$0(DataClient this$0, String $id) {
        Function1<JSONObject, Unit> remove = this$0.pending.remove($id);
        if (remove != null) {
            JSONObject put = new JSONObject().put("ok", false).put("error", "timeout");
            Intrinsics.checkNotNullExpressionValue(put, "put(...)");
            remove.invoke(put);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void request$lambda$1(Function1 $cb) {
        JSONObject put = new JSONObject().put("ok", false).put("error", "not connected");
        Intrinsics.checkNotNullExpressionValue(put, "put(...)");
        $cb.invoke(put);
    }

    public final void ping() {
        this.pingSentNs = System.nanoTime();
        WebSocket webSocket = this.ws;
        if (webSocket != null) {
            webSocket.send("{\"ping\":1}");
        }
    }

    public final void close() {
        WebSocket webSocket = this.ws;
        if (webSocket != null) {
            webSocket.close(1000, "bye");
        }
        this.ws = null;
        this.connected = false;
    }
}
