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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
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

    /* JADX WARN: Code restructure failed: missing block: B:13:0x00a7, code lost:
    
        r8 = kotlin.text.StringsKt.split((java.lang.CharSequence) r7, new java.lang.String[]{" "}, false, 0);
        r9 = (java.lang.String) kotlin.collections.CollectionsKt.getOrNull(r8, 1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x00c0, code lost:
    
        if (r9 != null) goto L35;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x00c2, code lost:
    
        r9 = "";
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x00c4, code lost:
    
        r13 = kotlin.text.StringsKt.substringBeforeLast(r9, ":", r9);
        r0 = kotlin.text.StringsKt.toIntOrNull(kotlin.text.StringsKt.substringAfterLast(r9, ":", r9));
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x00de, code lost:
    
        if (kotlin.jvm.internal.Intrinsics.areEqual(kotlin.collections.CollectionsKt.firstOrNull(r8), "CONNECT") == false) goto L46;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x00e0, code lost:
    
        if (r0 != null) goto L39;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x00e7, code lost:
    
        if (r0.intValue() != 443) goto L46;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x00ef, code lost:
    
        if (kotlin.jvm.internal.Intrinsics.areEqual(r13, "mapbox.com") != false) goto L45;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x00f7, code lost:
    
        if (kotlin.text.StringsKt.endsWith(r13, ".mapbox.com", false) == false) goto L46;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x00f9, code lost:
    
        r10 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x00fc, code lost:
    
        if (r10 != false) goto L59;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x00fe, code lost:
    
        r11 = "HTTP/1.1 403 Forbidden\r\nContent-Length: 0\r\n\r\n".getBytes(kotlin.text.Charsets.UTF_8);
        kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r11, "getBytes(...)");
        r6.write(r11);
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x010c, code lost:
    
        r0 = kotlin.Result.Companion;
        r0 = r18;
        kotlin.KtCompat.resultOf(r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x0117, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0118, code lost:
    
        r5 = kotlin.Result.Companion;
        kotlin.KtCompat.resultOf(kotlin.ResultKt.createFailure(r0));
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x0144, code lost:
    
        r12 = new java.net.Socket();
        r12.connect(new java.net.InetSocketAddress(r13, 443), 8000);
        r12.setSoTimeout(60000);
        r3 = r12;
        r11 = "HTTP/1.1 200 Connection established\r\n\r\n".getBytes(kotlin.text.Charsets.UTF_8);
        kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r11, "getBytes(...)");
        r6.write(r11);
        r6.flush();
        r19.setSoTimeout(60000);
        com.starpilot.starview.MapboxRelay.served++;
        r0 = com.starpilot.starview.MapboxRelay.pool.submit(new com.starpilot.starview.MapboxRelay$$ExternalSyntheticLambda3(r3, r6, r19));
        r0 = r3.getOutputStream();
        kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r0, "getOutputStream(...)");
        pipe(r4, r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x0195, code lost:
    
        r0 = kotlin.Result.Companion;
        r0 = r18;
        r3.shutdownOutput();
        kotlin.KtCompat.resultOf(kotlin.Unit.INSTANCE);
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x01a4, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x01a5, code lost:
    
        r12 = kotlin.Result.Companion;
        kotlin.KtCompat.resultOf(kotlin.ResultKt.createFailure(r0));
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final void serve(final Socket c) {
        Unit unit;
        Unit unit2;
        Future back;
        active.incrementAndGet();
        final Socket socket = null;
        String str = null;
        try {
            c.setSoTimeout(15000);
            InputStream ci = c.getInputStream();
            final OutputStream co = c.getOutputStream();
            Intrinsics.checkNotNull(ci);
            String req = readLine(ci);
            if (req == null) {
                try {
                    Result.Companion companion = Result.Companion;
                    MapboxRelay mapboxRelay = this;
                    KtCompat.resultOf(null);
                } catch (Throwable th) {
                    Result.Companion companion2 = Result.Companion;
                    KtCompat.resultOf(ResultKt.createFailure(th));
                }
                try {
                    Result.Companion companion3 = Result.Companion;
                    MapboxRelay mapboxRelay2 = this;
                    c.close();
                    KtCompat.resultOf(Unit.INSTANCE);
                } catch (Throwable th2) {
                    Result.Companion companion4 = Result.Companion;
                    KtCompat.resultOf(ResultKt.createFailure(th2));
                }
                active.decrementAndGet();
                return;
            }
            while (true) {
                String h = readLine(ci);
                if (h == null) {
                    try {
                        Result.Companion companion5 = Result.Companion;
                        MapboxRelay mapboxRelay3 = this;
                        KtCompat.resultOf(str);
                    } catch (Throwable th3) {
                        Result.Companion companion6 = Result.Companion;
                        KtCompat.resultOf(ResultKt.createFailure(th3));
                    }
                    try {
                        Result.Companion companion7 = Result.Companion;
                        MapboxRelay mapboxRelay4 = this;
                        c.close();
                        KtCompat.resultOf(Unit.INSTANCE);
                    } catch (Throwable th4) {
                        Result.Companion companion8 = Result.Companion;
                        KtCompat.resultOf(ResultKt.createFailure(th4));
                    }
                    active.decrementAndGet();
                    return;
                }
                boolean allowed = false;
                if (h.length() == 0) {
                    break;
                } else {
                    str = null;
                }
            }
        } catch (Exception e) {
            try {
                Result.Companion companion9 = Result.Companion;
                Socket socket2 = socket;
                if (socket2 != null) {
                    socket2.close();
                    unit2 = Unit.INSTANCE;
                } else {
                    unit2 = null;
                }
                KtCompat.resultOf(unit2);
            } catch (Throwable th5) {
                Result.Companion companion10 = Result.Companion;
                KtCompat.resultOf(ResultKt.createFailure(th5));
            }
            try {
                Result.Companion companion11 = Result.Companion;
                c.close();
                KtCompat.resultOf(Unit.INSTANCE);
            } catch (Throwable th6) {
                th = th6;
                Result.Companion companion12 = Result.Companion;
                KtCompat.resultOf(ResultKt.createFailure(th));
                active.decrementAndGet();
            }
            active.decrementAndGet();
        } catch (Throwable th7) {
            try {
                Result.Companion companion13 = Result.Companion;
                Socket socket3 = socket;
                if (socket3 != null) {
                    socket3.close();
                    unit = Unit.INSTANCE;
                } else {
                    unit = null;
                }
                KtCompat.resultOf(unit);
            } catch (Throwable th8) {
                Result.Companion companion14 = Result.Companion;
                KtCompat.resultOf(ResultKt.createFailure(th8));
            }
            try {
                Result.Companion companion15 = Result.Companion;
                c.close();
                KtCompat.resultOf(Unit.INSTANCE);
            } catch (Throwable th9) {
                Result.Companion companion16 = Result.Companion;
                KtCompat.resultOf(ResultKt.createFailure(th9));
            }
            active.decrementAndGet();
            throw th7;
        }
        try {
            Result.Companion companion17 = Result.Companion;
            MapboxRelay mapboxRelay5 = this;
            c.close();
            KtCompat.resultOf(Unit.INSTANCE);
        } catch (Throwable th10) {
            Result.Companion companion18 = Result.Companion;
            KtCompat.resultOf(ResultKt.createFailure(th10));
        }
        active.decrementAndGet();
        return;
        try {
            Result.Companion companion19 = Result.Companion;
            MapboxRelay mapboxRelay6 = this;
            c.close();
            KtCompat.resultOf(Unit.INSTANCE);
        } catch (Throwable th11) {
            th = th11;
            Result.Companion companion122 = Result.Companion;
            KtCompat.resultOf(ResultKt.createFailure(th));
            active.decrementAndGet();
        }
        active.decrementAndGet();
        active.decrementAndGet();
        return;
        back.get();
        try {
            Result.Companion companion20 = Result.Companion;
            MapboxRelay mapboxRelay7 = this;
            socket.close();
            KtCompat.resultOf(Unit.INSTANCE);
        } catch (Throwable th12) {
            Result.Companion companion21 = Result.Companion;
            KtCompat.resultOf(ResultKt.createFailure(th12));
        }
        Result.Companion companion192 = Result.Companion;
        MapboxRelay mapboxRelay62 = this;
        c.close();
        KtCompat.resultOf(Unit.INSTANCE);
        active.decrementAndGet();
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
