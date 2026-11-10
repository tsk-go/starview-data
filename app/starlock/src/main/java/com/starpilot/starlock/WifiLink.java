package com.starpilot.starlock;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import javax.net.SocketFactory;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: WifiLink.kt */
public final class WifiLink {
    private final Context ctx;
    private final Function1<String, Unit> log;
    private final Pairing p;

    /* VF: from Vineflower (jadx: ) */
    public WifiLink(Context var1, Pairing var2, Function1<? super String, Unit> var3) {
        super();
        Intrinsics.checkNotNullParameter(var1, "ctx");
        Intrinsics.checkNotNullParameter(var2, "p");
        Intrinsics.checkNotNullParameter(var3, "log");
        this.ctx = var1;
        this.p = var2;
        this.log = (Function1) var3;
    }

    private final Network wifi() {
        ConnectivityManager var5 = (ConnectivityManager)this.ctx.getSystemService(ConnectivityManager.class);
        Network[] var4 = var5.getAllNetworks();
        Intrinsics.checkNotNullExpressionValue(var4, "getAllNetworks(...)");
        for (Network n : var4) {
            NetworkCapabilities c = var5.getNetworkCapabilities(n);
            if (c != null && c.hasTransport(1)) {
                return n;
            }
        }
        return null;
    }

    private final OkHttpClient client(Network net, long readS) {
        OkHttpClient.Builder $this$client_u24lambda_u241 = new OkHttpClient.Builder().connectTimeout(1500L, TimeUnit.MILLISECONDS).readTimeout(readS, TimeUnit.SECONDS);
        if (net != null) {
            SocketFactory socketFactory = net.getSocketFactory();
            Intrinsics.checkNotNullExpressionValue(socketFactory, "getSocketFactory(...)");
            $this$client_u24lambda_u241.socketFactory(socketFactory);
        }
        return $this$client_u24lambda_u241.build();
    }

    /* recovered from bytecode (jadx's version kept answers that failed to parse) */
    private final List<String> discover(Network net) {
        ArrayList found = new ArrayList();
        try {
            DatagramSocket datagramSocket = new DatagramSocket();
            Throwable datagramSocket$c = null;
            try {
                DatagramSocket s = datagramSocket;
                if (net != null) {
                    net.bindSocket(s);
                }
                s.setBroadcast(true);
                s.setSoTimeout(400);
                String cn = Pairing.Companion.nonce();
                byte[] msg = ("STARVIEW? " + cn).getBytes(Charsets.UTF_8);
                Intrinsics.checkNotNullExpressionValue(msg, "getBytes(...)");
                s.send(new DatagramPacket(msg, msg.length, InetAddress.getByName("255.255.255.255"), 8091));
                byte[] buf = new byte[2048];
                long end = System.currentTimeMillis() + 1200;
                while (System.currentTimeMillis() < end) {
                    DatagramPacket pk = new DatagramPacket(buf, buf.length);
                    try {
                        s.receive(pk);
                    } catch (SocketTimeoutException e) {
                        continue;
                    }
                    JSONObject j;
                    try {
                        byte[] data = pk.getData();
                        Intrinsics.checkNotNullExpressionValue(data, "getData(...)");
                        j = new JSONObject(new String(data, 0, pk.getLength(), Charsets.UTF_8));
                    } catch (Throwable th) {
                        j = null;
                    }
                    if (j == null) {
                        continue;
                    }
                    String d = j.optString("dongleId");
                    String optString = j.optString("proof");
                    Pairing pairing = this.p;
                    Intrinsics.checkNotNull(d);
                    if (Intrinsics.areEqual(optString, Pairing.serverProof$default(pairing, d, cn, null, 4, null))) {
                        if ((this.p.getDongle().length() == 0) || Intrinsics.areEqual(d, this.p.getDongle())) {
                            String hostAddress = pk.getAddress().getHostAddress();
                            Intrinsics.checkNotNull(hostAddress);
                            found.add(hostAddress);
                        }
                    }
                }
                Unit unit = Unit.INSTANCE;
            } catch (Throwable datagramSocket$t) {
                datagramSocket$c = datagramSocket$t;
                throw datagramSocket$t;
            } finally {
                CloseableKt.closeFinally(datagramSocket, datagramSocket$c);
            }
        } catch (Exception e2) {
            this.log.invoke("discovery: " + e2.getMessage());
        }
        return found;
    }

    private final String session(OkHttpClient http, String host) {
        String cn = Pairing.Companion.nonce();
        JSONObject h;
        Response execute = http.newCall(new Request.Builder().url("http://" + host + ":" + this.p.getPort() + "/hello?n=" + cn).build()).execute();
        Throwable execute$c = null;
        try {
            Response r = execute;
            if (!r.isSuccessful()) {
                return null;
            }
            ResponseBody body = r.body();
            Intrinsics.checkNotNull(body);
            h = new JSONObject(body.string());
        } catch (Throwable execute$t) {
            execute$c = execute$t;
            throw execute$t;
        } finally {
            CloseableKt.closeFinally(execute, execute$c);
        }
        String dongle = h.optString("dongleId");
        String sn = h.optString("sn");
        String optString = h.optString("proof");
        Pairing pairing = this.p;
        Intrinsics.checkNotNull(dongle);
        Intrinsics.checkNotNull(sn);
        if (Intrinsics.areEqual(optString, pairing.serverProof(dongle, cn, sn))) {
            if (!(this.p.getDongle().length() > 0) || Intrinsics.areEqual(dongle, this.p.getDongle())) {
                String body2 = new JSONObject().put("sn", sn).put("cn", cn).put("proof", this.p.clientProof(dongle, sn, cn)).toString();
                Intrinsics.checkNotNullExpressionValue(body2, "toString(...)");
                Response execute2 = http.newCall(new Request.Builder().url("http://" + host + ":" + this.p.getPort() + "/auth").post(RequestBody.Companion.create(body2, MediaType.Companion.get("application/json"))).build()).execute();
                Throwable execute2$c = null;
                try {
                    Response r2 = execute2;
                    if (!r2.isSuccessful()) {
                        this.log.invoke(host + ": pairing refused - pair again");
                        return null;
                    }
                    ResponseBody body3 = r2.body();
                    Intrinsics.checkNotNull(body3);
                    String optString2 = new JSONObject(body3.string()).optString("session");
                    return optString2.length() == 0 ? null : optString2;
                } catch (Throwable execute2$t) {
                    execute2$c = execute2$t;
                    throw execute2$t;
                } finally {
                    CloseableKt.closeFinally(execute2, execute2$c);
                }
            }
        }
        this.log.invoke(host + ": not your comma");
        return null;
    }

    public final Pair<String, JSONObject> door(String cmd, String sound, JSONArray beeps, String lastHost, Function0<Boolean> stop) {
        JSONObject it;
        Intrinsics.checkNotNullParameter(cmd, "cmd");
        Intrinsics.checkNotNullParameter(sound, "sound");
        Intrinsics.checkNotNullParameter(stop, "stop");
        Network net = wifi();
        OkHttpClient http = client(net, 10L);
        HashSet tried = new HashSet();
        if (lastHost != null && (it = door$attempt(stop, tried, this, http, cmd, sound, beeps, lastHost)) != null) {
            return TuplesKt.to(lastHost, it);
        }
        for (String h : discover(net)) {
            JSONObject it2 = door$attempt(stop, tried, this, http, cmd, sound, beeps, h);
            if (it2 != null) {
                return TuplesKt.to(h, it2);
            }
        }
        Iterable $this$filterNot$iv = this.p.getHosts();
        int $i$f$filterNot = 0;
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filterNot$iv) {
            Iterable $this$filterNot$iv2 = $this$filterNot$iv;
            int $i$f$filterNot2 = $i$f$filterNot;
            if (!StringsKt.startsWith((String) element$iv$iv, "192.168.42.", false)) {
                destination$iv$iv.add(element$iv$iv);
            }
            $this$filterNot$iv = $this$filterNot$iv2;
            $i$f$filterNot = $i$f$filterNot2;
        }
        Pair<String, JSONObject> pair = null;
        for (String h2 : (List<String>) (List) destination$iv$iv) {
            Pair<String, JSONObject> pair2 = pair;
            JSONObject it3 = door$attempt(stop, tried, this, http, cmd, sound, beeps, h2);
            if (it3 != null) {
                return TuplesKt.to(h2, it3);
            }
            pair = pair2;
        }
        return pair;
    }

    private static final JSONObject door$attempt(Function0<Boolean> function0, HashSet<String> hashSet, WifiLink this$0, OkHttpClient http, String $cmd, String $sound, JSONArray $beeps, String host) {
        if (!function0.invoke().booleanValue() && hashSet.add(host)) {
            try {
                String s = this$0.session(http, host);
                if (s == null) {
                    return null;
                }
                this$0.log.invoke("Wi-Fi: connected to " + host);
                return this$0.send(http, host, s, $cmd, $sound, $beeps);
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final JSONObject send(OkHttpClient http, String host, String session, final String cmd, final String sound, final JSONArray beeps) {
        final CountDownLatch done = new CountDownLatch(1);
        final Ref.ObjectRef ack = new Ref.ObjectRef();
        final String id = "phone-" + System.currentTimeMillis();
        WebSocket ws = http.newWebSocket(new Request.Builder().url("ws://" + host + ":" + this.p.getPort() + "/data?s=" + session).header("X-StarView-Session", session).build(), new WebSocketListener() { // from class: com.starpilot.starlock.WifiLink$send$ws$1
            @Override // okhttp3.WebSocketListener
            public void onOpen(WebSocket webSocket, Response response) {
                Intrinsics.checkNotNullParameter(webSocket, "webSocket");
                Intrinsics.checkNotNullParameter(response, "response");
                JSONObject $this$onOpen_u24lambda_u240 = new JSONObject().put("action", "door").put("cmd", cmd).put("sound", sound);
                JSONArray jSONArray = beeps;
                if (jSONArray != null) {
                    $this$onOpen_u24lambda_u240.put("beeps", jSONArray);
                }
                String jSONObject = $this$onOpen_u24lambda_u240.put("id", id).toString();
                Intrinsics.checkNotNullExpressionValue(jSONObject, "toString(...)");
                webSocket.send(jSONObject);
            }

            /* JADX WARN: Type inference failed for: r0v7, types: [T, org.json.JSONObject] */
            @Override // okhttp3.WebSocketListener
            public void onMessage(WebSocket webSocket, String text) {
                Object m61constructorimpl;
                Intrinsics.checkNotNullParameter(webSocket, "webSocket");
                Intrinsics.checkNotNullParameter(text, "text");
                try {
                    Result.Companion companion = Result.Companion;
                    m61constructorimpl = KtCompat.resultOf(new JSONObject(text));
                } catch (Throwable th) {
                    Result.Companion companion2 = Result.Companion;
                    m61constructorimpl = KtCompat.resultOf(ResultKt.createFailure(th));
                }
                if (KtCompat.isFailure(m61constructorimpl)) {
                    m61constructorimpl = null;
                }
                JSONObject r0 = (JSONObject) m61constructorimpl;
                if (r0 != null && Intrinsics.areEqual(r0.optString("type"), "ack") && Intrinsics.areEqual(r0.optString("id"), id)) {
                    ack.element = r0;
                    done.countDown();
                }
            }

            @Override // okhttp3.WebSocketListener
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                Intrinsics.checkNotNullParameter(webSocket, "webSocket");
                Intrinsics.checkNotNullParameter(t, "t");
                done.countDown();
            }
        });
        done.await(8L, TimeUnit.SECONDS);
        ws.close(1000, null);
        return (JSONObject) ack.element;
    }
}
