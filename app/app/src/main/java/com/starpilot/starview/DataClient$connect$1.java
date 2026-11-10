package com.starpilot.starview;

import ai.comma.cereal.Log;
import android.os.Handler;
import android.util.Log;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import org.capnproto.MessageReader;
import org.capnproto.ReaderOptions;
import org.capnproto.Serialize;
import org.json.JSONObject;

/* compiled from: DataClient.kt */
public final class DataClient$connect$1 extends WebSocketListener {
    final /* synthetic */ DataClient this$0;

    DataClient$connect$1(DataClient $receiver) {
        this.this$0 = $receiver;
    }

    @Override // okhttp3.WebSocketListener
    public void onOpen(WebSocket webSocket, Response response) {
        Function2 function2;
        Intrinsics.checkNotNullParameter(webSocket, "webSocket");
        Intrinsics.checkNotNullParameter(response, "response");
        this.this$0.connected = true;
        String jSONObject = new JSONObject(MapsKt.mapOf(TuplesKt.to("subscribe", new JSONObject(DataClient.Companion.getSUBSCRIBE())))).toString();
        Intrinsics.checkNotNullExpressionValue(jSONObject, "toString(...)");
        webSocket.send(jSONObject);
        function2 = this.this$0.onStatus;
        function2.invoke("data: connected", true);
    }

    @Override // okhttp3.WebSocketListener
    public void onMessage(WebSocket webSocket, String text) {
        final Function1 cb;
        String str;
        Function2 function2;
        Handler handler;
        ConcurrentHashMap concurrentHashMap;
        Telemetry telemetry;
        Telemetry telemetry2;
        long j;
        Function2 function22;
        Intrinsics.checkNotNullParameter(webSocket, "webSocket");
        Intrinsics.checkNotNullParameter(text, "text");
        try {
            final JSONObject j2 = new JSONObject(text);
            if (Intrinsics.areEqual(j2.optString("type"), "hello")) {
                this.this$0.hello = j2;
                function22 = this.this$0.onStatus;
                function22.invoke("data: hello from " + j2.optString("dongleId") + " " + j2.optString("branch") + " " + j2.optString("commit"), true);
                return;
            }
            if (j2.has("pong")) {
                telemetry2 = this.this$0.telemetry;
                long nanoTime = System.nanoTime();
                j = this.this$0.pingSentNs;
                telemetry2.setRttMs((nanoTime - j) / 1000000.0d);
                return;
            }
            if (Intrinsics.areEqual(j2.optString("type"), "params")) {
                telemetry = this.this$0.telemetry;
                telemetry.applyParams(j2);
                return;
            }
            if (Intrinsics.areEqual(j2.optString("type"), "rtk")) {
                long now = System.currentTimeMillis();
                double lat = j2.optDouble("lat", Double.NaN);
                double lon = j2.optDouble("lon", Double.NaN);
                double acc = j2.optDouble("hAccM", Double.NaN);
                double hd = j2.optDouble("headingDeg", Double.NaN);
                double v = j2.optDouble("speedMs", Double.NaN);
                RtkPosition rtkPosition = RtkPosition.INSTANCE;
                String optString = j2.optString("rtk", "none");
                Intrinsics.checkNotNullExpressionValue(optString, "optString(...)");
                rtkPosition.feedFast(lat, lon, acc, hd, v, optString, j2.optLong("ageMs", 0L), now);
                if ((Double.isInfinite(lat) || Double.isNaN(lat)) ? false : true) {
                    if ((Double.isInfinite(acc) || Double.isNaN(acc)) ? false : true) {
                        if (acc < 3.0d) {
                            OsmRoads.INSTANCE.onFix(lat, lon, (float) hd, (float) v, (float) acc, (float) j2.optDouble("headingAccDeg", Double.NaN), 0L, 0L, true);
                            return;
                        }
                        return;
                    }
                }
                return;
            }
            if (Intrinsics.areEqual(j2.optString("type"), "ack")) {
                String id = j2.optString("id", "");
                Intrinsics.checkNotNull(id);
                if (id.length() > 0) {
                    concurrentHashMap = this.this$0.pending;
                    cb = (Function1) concurrentHashMap.remove(id);
                } else {
                    cb = null;
                }
                if (cb != null) {
                    handler = this.this$0.main;
                    handler.post(new Runnable() { // from class: com.starpilot.starview.DataClient$connect$1$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            Function1.this.invoke(j2);
                        }
                    });
                    return;
                }
                boolean ok = j2.optBoolean("ok");
                if (ok) {
                    Iterator<String> keys = j2.keys();
                    Intrinsics.checkNotNullExpressionValue(keys, "keys(...)");
                    List keys2 = SequencesKt.toList(SequencesKt.filter(SequencesKt.asSequence(keys), new Function1() { // from class: com.starpilot.starview.DataClient$connect$1$$ExternalSyntheticLambda1
                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj) {
                            boolean onMessage$lambda$1;
                            onMessage$lambda$1 = DataClient$connect$1.onMessage$lambda$1((String) obj);
                            return Boolean.valueOf(onMessage$lambda$1);
                        }
                    }));
                    str = "OK " + CollectionsKt.joinToString$default(keys2, " ", null, null, 0, null, new Function1() { // from class: com.starpilot.starview.DataClient$connect$1$$ExternalSyntheticLambda2
                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj) {
                            CharSequence onMessage$lambda$2;
                            onMessage$lambda$2 = DataClient$connect$1.onMessage$lambda$2(j2, (String) obj);
                            return onMessage$lambda$2;
                        }
                    }, 30, null);
                } else {
                    str = "Rejected: " + j2.optString("error");
                }
                String msg = str;
                if (!ok) {
                    Log.w("StarView/Data", msg);
                }
                function2 = this.this$0.onStatus;
                function2.invoke("ack: " + msg, true);
            }
        } catch (Exception e) {
            Log.w("StarView/Data", "text: " + e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean onMessage$lambda$1(String it) {
        return !SetsKt.setOf((Object[]) new String[]{"ok", "type", "id"}).contains(it);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CharSequence onMessage$lambda$2(JSONObject $j, String it) {
        return it + "=" + $j.opt(it);
    }

    @Override // okhttp3.WebSocketListener
    public void onMessage(WebSocket webSocket, ByteString bytes) {
        Telemetry telemetry;
        ReaderOptions readerOptions;
        Telemetry telemetry2;
        Intrinsics.checkNotNullParameter(webSocket, "webSocket");
        Intrinsics.checkNotNullParameter(bytes, "bytes");
        try {
            int n = bytes.getByte(0) & 255;
            String name = bytes.substring(1, n + 1).utf8();
            ByteBuffer $this$onMessage_u24lambda_u243 = bytes.asByteBuffer();
            $this$onMessage_u24lambda_u243.position(n + 1);
            ByteBuffer payload = $this$onMessage_u24lambda_u243.slice();
            telemetry = this.this$0.telemetry;
            telemetry.count(name, payload.remaining());
            ByteBuffer order = payload.order(ByteOrder.LITTLE_ENDIAN);
            readerOptions = DataClient.READER_OPTS;
            MessageReader msg = Serialize.read(order, readerOptions);
            Log.Event.Reader evt = (Log.Event.Reader) msg.getRoot(Log.Event.factory);
            telemetry2 = this.this$0.telemetry;
            Intrinsics.checkNotNull(evt);
            telemetry2.apply(evt);
        } catch (Exception e) {
            android.util.Log.w("StarView/Data", "frame: " + e);
        }
    }

    @Override // okhttp3.WebSocketListener
    public void onClosing(WebSocket webSocket, int code, String reason) {
        Intrinsics.checkNotNullParameter(webSocket, "webSocket");
        Intrinsics.checkNotNullParameter(reason, "reason");
        webSocket.close(1000, null);
    }

    @Override // okhttp3.WebSocketListener
    public void onClosed(WebSocket webSocket, int code, String reason) {
        WebSocket cur;
        Function2 function2;
        Intrinsics.checkNotNullParameter(webSocket, "webSocket");
        Intrinsics.checkNotNullParameter(reason, "reason");
        cur = this.this$0.ws;
        if (cur == null || webSocket == cur) {
            this.this$0.connected = false;
            function2 = this.this$0.onStatus;
            function2.invoke("data: closed (" + code + ")", false);
        }
    }

    @Override // okhttp3.WebSocketListener
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        WebSocket cur;
        Function2 function2;
        Function2 function22;
        Function2 function23;
        Intrinsics.checkNotNullParameter(webSocket, "webSocket");
        Intrinsics.checkNotNullParameter(t, "t");
        cur = this.this$0.ws;
        if (cur == null || webSocket == cur) {
            this.this$0.connected = false;
            if ((response != null && response.code() == 401) && Link.INSTANCE.getPaired()) {
                Link.INSTANCE.forgetSession();
                function23 = this.this$0.onStatus;
                function23.invoke("data: signing in again", false);
                return;
            }
            if (!(response != null && response.code() == 401)) {
                function2 = this.this$0.onStatus;
                function2.invoke("data: " + t.getMessage(), false);
            } else {
                Link.INSTANCE.setPairingRequired(true);
                function22 = this.this$0.onStatus;
                function22.invoke("data: pairing required - on the comma open Settings › starview › show qr, then scan it", false);
            }
        }
    }
}
