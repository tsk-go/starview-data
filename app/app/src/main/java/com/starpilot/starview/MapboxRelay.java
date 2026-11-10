package com.starpilot.starview;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.util.Log;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import org.json.JSONObject;

/* compiled from: MapboxRelay.kt */
public final class MapboxRelay {
    public static final int PORT = 8099;
    private static final String TAG = "StarView/Relay";
    private static boolean announced;
    private static volatile String commaIp;
    private static long lastAnnounceMs;
    private static volatile int served;
    private static volatile ServerSocket server;
    public static final MapboxRelay INSTANCE = new MapboxRelay();
    private static final AtomicInteger active = new AtomicInteger();
    private static final ExecutorService pool = Executors.newCachedThreadPool();

    private MapboxRelay() {
    }

    public final String getCommaIp() {
        return commaIp;
    }

    public final void setCommaIp(String str) {
        commaIp = str;
    }

    public final int getServed() {
        return served;
    }

    public final boolean online(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        try {
            Object systemService = ctx.getSystemService("connectivity");
            Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.net.ConnectivityManager");
            ConnectivityManager cm = (ConnectivityManager) systemService;
            NetworkCapabilities caps = cm.getNetworkCapabilities(cm.getActiveNetwork());
            if (caps == null || !caps.hasCapability(12)) {
                return false;
            }
            return caps.hasCapability(16);
        } catch (Exception e) {
            return false;
        }
    }

    public final void tick(Context ctx, DataClient data, String host, long nowMs) {
        String str;
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(data, "data");
        if (!data.getConnected() || host == null) {
            return;
        }
        try {
            str = InetAddress.getByName(host).getHostAddress();
        } catch (Exception e) {
            str = host;
        }
        commaIp = str;
        if (nowMs - lastAnnounceMs < 30000) {
            return;
        }
        lastAnnounceMs = nowMs;
        final boolean on = online(ctx);
        if (on) {
            start();
        }
        if (on || announced) {
            data.request("nav_relay", MapsKt.mapOf(TuplesKt.to("port", Integer.valueOf(on ? 8099 : 0))), new Function1() { // from class: com.starpilot.starview.MapboxRelay$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    Unit tick$lambda$0;
                    tick$lambda$0 = MapboxRelay.tick$lambda$0(on, (JSONObject) obj);
                    return tick$lambda$0;
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tick$lambda$0(boolean $on, JSONObject r) {
        Intrinsics.checkNotNullParameter(r, "r");
        if (r.optBoolean("ok")) {
            announced = $on;
        }
        return Unit.INSTANCE;
    }

    private final synchronized void start() {
        ServerSocket serverSocket = server;
        boolean z = false;
        if (serverSocket != null && !serverSocket.isClosed()) {
            z = true;
        }
        if (z) {
            return;
        }
        try {
            final ServerSocket s = new ServerSocket();
            s.setReuseAddress(true);
            s.bind(new InetSocketAddress(8099), 16);
            server = s;
            Thread $this$start_u24lambda_u242 = new Thread(new Runnable() { // from class: com.starpilot.starview.MapboxRelay$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    MapboxRelay.start$lambda$1(s);
                }
            }, "starview-relay");
            $this$start_u24lambda_u242.setDaemon(true);
            $this$start_u24lambda_u242.start();
            Log.i("StarView/Relay", "relay on :8099");
        } catch (Exception e) {
            Log.w("StarView/Relay", "relay: " + e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void start$lambda$1(ServerSocket $s) {
        INSTANCE.accept($s);
    }

    private final void accept(ServerSocket s) {
        while (!s.isClosed()) {
            try {
                final Socket c = s.accept();
                if (!Intrinsics.areEqual(c.getInetAddress().getHostAddress(), commaIp) || active.get() >= 24) {
                    try {
                        Result.Companion companion = Result.Companion;
                        MapboxRelay mapboxRelay = this;
                        c.close();
                        KtCompat.resultOf(Unit.INSTANCE);
                    } catch (Throwable th) {
                        Result.Companion companion2 = Result.Companion;
                        KtCompat.resultOf(ResultKt.createFailure(th));
                    }
                } else {
                    pool.execute(new Runnable() { // from class: com.starpilot.starview.MapboxRelay$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            MapboxRelay.accept$lambda$4(c);
                        }
                    });
                }
            } catch (Exception e) {
                return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void accept$lambda$4(Socket $c) {
        MapboxRelay mapboxRelay = INSTANCE;
        Intrinsics.checkNotNull($c);
        mapboxRelay.serve($c);
    }

    private final String readLine(InputStream i) {
        int b;
        StringBuilder sb = new StringBuilder();
        while (sb.length() < 1024 && (b = i.read()) >= 0) {
            if (b == 10) {
                String sb2 = sb.toString();
                Intrinsics.checkNotNullExpressionValue(sb2, "toString(...)");
                return StringsKt.trimEnd(sb2, '\r');
            }
            sb.append((char) b);
        }
        return null;
    }

    private final void serve(final Socket c) {
        active.incrementAndGet();
        Socket up = null;
        try {
            c.setSoTimeout(15000);
            InputStream ci = c.getInputStream();
            final OutputStream co = c.getOutputStream();
            Intrinsics.checkNotNull(ci);
            String req = readLine(ci);
            if (req == null) {
                return;
            }
            while (true) {
                String h = readLine(ci);
                if (h == null) {
                    return;
                }
                if (h.length() == 0) {
                    break;
                }
            }
            List<String> parts = StringsKt.split(req, new String[]{" "}, false, 0);
            String target = (String) CollectionsKt.getOrNull(parts, 1);
            if (target == null) {
                target = "";
            }
            String host = StringsKt.substringBeforeLast(target, ":", target);
            Integer port = StringsKt.toIntOrNull(StringsKt.substringAfterLast(target, ":", target));
            boolean allowed = false;
            if (Intrinsics.areEqual(CollectionsKt.firstOrNull(parts), "CONNECT") && port != null && port.intValue() == 443 && (Intrinsics.areEqual(host, "mapbox.com") || StringsKt.endsWith(host, ".mapbox.com", false))) {
                allowed = true;
            }
            if (!allowed) {
                byte[] no = "HTTP/1.1 403 Forbidden\r\nContent-Length: 0\r\n\r\n".getBytes(Charsets.UTF_8);
                Intrinsics.checkNotNullExpressionValue(no, "getBytes(...)");
                co.write(no);
                return;
            }
            final Socket s = new Socket();
            s.connect(new InetSocketAddress(host, 443), 8000);
            s.setSoTimeout(60000);
            up = s;
            byte[] ok = "HTTP/1.1 200 Connection established\r\n\r\n".getBytes(Charsets.UTF_8);
            Intrinsics.checkNotNullExpressionValue(ok, "getBytes(...)");
            co.write(ok);
            co.flush();
            c.setSoTimeout(60000);
            served++;
            Future back = pool.submit(new Runnable() { // from class: com.starpilot.starview.MapboxRelay$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    MapboxRelay.serve$lambda$7(s, co, c);
                }
            });
            OutputStream so = s.getOutputStream();
            Intrinsics.checkNotNullExpressionValue(so, "getOutputStream(...)");
            pipe(ci, so);
            try {
                s.shutdownOutput();
                KtCompat.resultOf(Unit.INSTANCE);
            } catch (Throwable th) {
                KtCompat.resultOf(ResultKt.createFailure(th));
            }
            back.get();
        } catch (Exception e) {
        } finally {
            try {
                Socket u = up;
                Unit closed;
                if (u != null) {
                    u.close();
                    closed = Unit.INSTANCE;
                } else {
                    closed = null;
                }
                KtCompat.resultOf(closed);
            } catch (Throwable th2) {
                KtCompat.resultOf(ResultKt.createFailure(th2));
            }
            try {
                c.close();
                KtCompat.resultOf(Unit.INSTANCE);
            } catch (Throwable th3) {
                KtCompat.resultOf(ResultKt.createFailure(th3));
            }
            active.decrementAndGet();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void serve$lambda$7(Socket $u, OutputStream $co, Socket $c) {
        MapboxRelay mapboxRelay = INSTANCE;
        InputStream inputStream = $u.getInputStream();
        Intrinsics.checkNotNullExpressionValue(inputStream, "getInputStream(...)");
        Intrinsics.checkNotNull($co);
        mapboxRelay.pipe(inputStream, $co);
        MapboxRelay mapboxRelay2 = INSTANCE;
        try {
            Result.Companion companion = Result.Companion;
            $c.shutdownOutput();
            KtCompat.resultOf(Unit.INSTANCE);
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            KtCompat.resultOf(ResultKt.createFailure(th));
        }
    }

    private final void pipe(InputStream i, OutputStream o) {
        byte[] buf = new byte[32768];
        while (true) {
            try {
                int n = i.read(buf);
                if (n < 0) {
                    return;
                }
                o.write(buf, 0, n);
                o.flush();
            } catch (Exception e) {
                return;
            }
        }
    }
}
