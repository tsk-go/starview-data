package com.starpilot.starview;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.collections.ArraysKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.json.JSONObject;

/* compiled from: Link.kt */
public final class Link {
    public static final int DAEMON_PORT = 8090;
    public static final int GALAXY_PORT = 8082;
    public static final String SESSION_HEADER = "X-StarView-Session";
    private static volatile DataClient data;
    private static volatile String host;
    private static volatile boolean pairingRequired;
    private static volatile String sessionHost;
    private static volatile long sessionUntilMs;
    private static volatile Telemetry telemetry;
    public static final Link INSTANCE = new Link();
    private static volatile String termKey = "";
    private static volatile String pairedDongle = "";
    private static volatile String session = "";
    private static final SecureRandom rng = new SecureRandom();

    /* renamed from: http$delegate, reason: from kotlin metadata */
    private static final Lazy http = LazyKt.lazy(new Function0() { // from class: com.starpilot.starview.Link$$ExternalSyntheticLambda1
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            OkHttpClient http_delegate$lambda$4;
            http_delegate$lambda$4 = Link.http_delegate$lambda$4();
            return http_delegate$lambda$4;
        }
    });
    private static final Interceptor authInterceptor = new Interceptor() { // from class: com.starpilot.starview.Link$$ExternalSyntheticLambda2
        @Override // okhttp3.Interceptor
        public final Response intercept(Interceptor.Chain chain) {
            Response authInterceptor$lambda$8;
            authInterceptor$lambda$8 = Link.authInterceptor$lambda$8(chain);
            return authInterceptor$lambda$8;
        }
    };

    private Link() {
    }

    public final Telemetry getTelemetry() {
        return telemetry;
    }

    public final void setTelemetry(Telemetry telemetry2) {
        telemetry = telemetry2;
    }

    public final DataClient getData() {
        return data;
    }

    public final void setData(DataClient dataClient) {
        data = dataClient;
    }

    public final String getHost() {
        return host;
    }

    public final void setHost(String str) {
        host = str;
    }

    public final String getTermKey() {
        return termKey;
    }

    public final String getPairedDongle() {
        return pairedDongle;
    }

    public final boolean getPairingRequired() {
        return pairingRequired;
    }

    public final void setPairingRequired(boolean z) {
        pairingRequired = z;
    }

    public final boolean getPaired() {
        return termKey.length() > 0;
    }

    public final boolean pairFromUri(Context ctx, String text) {
        String k;
        CharSequence $this$all$iv;
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(text, "text");
        try {
            Uri uri = Uri.parse(StringsKt.trim((CharSequence) text).toString());
            if (Intrinsics.areEqual(uri.getScheme(), "starview") && Intrinsics.areEqual(uri.getHost(), "pair") && (k = uri.getQueryParameter("k")) != null && k.length() >= 16) {
                String $this$all$iv2 = k;
                int i = 0;
                while (true) {
                    if (i >= $this$all$iv2.length()) {
                        $this$all$iv = 1;
                        break;
                    }
                    char element$iv = $this$all$iv2.charAt(i);
                    if (!Character.isLetterOrDigit(element$iv)) {
                        $this$all$iv = null;
                        break;
                    }
                    i++;
                }
                if ($this$all$iv != null) {
                    String d = uri.getQueryParameter("d");
                    if (d == null) {
                        d = "";
                    }
                    termKey = k;
                    pairedDongle = d;
                    forgetSession();
                    pairingRequired = false;
                    SharedPreferences.Editor putString = ctx.getSharedPreferences("starview", 0).edit().putString("term_key", k).putString("paired_dongle", d);
                    String queryParameter = uri.getQueryParameter("h");
                    putString.putString("paired_hosts", queryParameter != null ? queryParameter : "").apply();
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public final void loadKey(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        if (termKey.length() > 0) {
            return;
        }
        SharedPreferences prefs = ctx.getSharedPreferences("starview", 0);
        String string = prefs.getString("term_key", "");
        if (string == null) {
            string = "";
        }
        termKey = string;
        String string2 = prefs.getString("paired_dongle", "");
        pairedDongle = string2 != null ? string2 : "";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CharSequence nonce$lambda$2(byte it) {
        String format = String.format("%02x", Arrays.copyOf(new Object[]{Byte.valueOf(it)}, 1));
        Intrinsics.checkNotNullExpressionValue(format, "format(...)");
        return format;
    }

    public final String nonce() {
        byte[] it = new byte[16];
        rng.nextBytes(it);
        return ArraysKt.joinToString$default(it, (CharSequence) "", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, new Function1() { // from class: com.starpilot.starview.Link$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                CharSequence nonce$lambda$2;
                nonce$lambda$2 = Link.nonce$lambda$2(((Byte) obj).byteValue());
                return nonce$lambda$2;
            }
        }, 30, (Object) null);
    }

    private final String mac(String... parts) {
        Mac m = Mac.getInstance("HmacSHA256");
        byte[] bytes = termKey.getBytes(Charsets.UTF_8);
        Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
        m.init(new SecretKeySpec(bytes, "HmacSHA256"));
        byte[] bytes2 = ArraysKt.joinToString$default(parts, "|", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 62, (Object) null).getBytes(Charsets.UTF_8);
        Intrinsics.checkNotNullExpressionValue(bytes2, "getBytes(...)");
        byte[] doFinal = m.doFinal(bytes2);
        Intrinsics.checkNotNullExpressionValue(doFinal, "doFinal(...)");
        return ArraysKt.joinToString$default(doFinal, (CharSequence) "", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, new Function1() { // from class: com.starpilot.starview.Link$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                CharSequence mac$lambda$3;
                mac$lambda$3 = Link.mac$lambda$3(((Byte) obj).byteValue());
                return mac$lambda$3;
            }
        }, 30, (Object) null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CharSequence mac$lambda$3(byte it) {
        String format = String.format("%02x", Arrays.copyOf(new Object[]{Byte.valueOf(it)}, 1));
        Intrinsics.checkNotNullExpressionValue(format, "format(...)");
        return format;
    }

    private final boolean same(String a, String b) {
        byte[] bytes = a.getBytes(Charsets.UTF_8);
        Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
        byte[] bytes2 = b.getBytes(Charsets.UTF_8);
        Intrinsics.checkNotNullExpressionValue(bytes2, "getBytes(...)");
        return MessageDigest.isEqual(bytes, bytes2);
    }

    public final boolean commaProofOk(String dongleId, String cn, String sn, String proof) {
        Intrinsics.checkNotNullParameter(dongleId, "dongleId");
        Intrinsics.checkNotNullParameter(cn, "cn");
        Intrinsics.checkNotNullParameter(sn, "sn");
        Intrinsics.checkNotNullParameter(proof, "proof");
        if (getPaired()) {
            if (!(proof.length() == 0)) {
                if (!(pairedDongle.length() > 0) || Intrinsics.areEqual(dongleId, pairedDongle)) {
                    return same(proof, mac("srv", dongleId, cn, sn));
                }
                return false;
            }
        }
        return false;
    }

    /* compiled from: Link.kt */
    public enum Auth {
        OK,
        UNPAIRED,
        NOT_OUR_COMMA,
        OLD_BRIDGE,
        UNREACHABLE;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries($VALUES);

        public static EnumEntries<Auth> getEntries() {
            return $ENTRIES;
        }
    }

    private final OkHttpClient getHttp() {
        return (OkHttpClient) http.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final OkHttpClient http_delegate$lambda$4() {
        return new OkHttpClient.Builder().connectTimeout(3L, TimeUnit.SECONDS).readTimeout(3L, TimeUnit.SECONDS).build();
    }

    public static /* synthetic */ String sessionFor$default(Link link, String str, int i, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = 8090;
        }
        return link.sessionFor(str, i);
    }

    public final String sessionFor(String h, int port) {
        return (h == null || !Intrinsics.areEqual(new StringBuilder().append(h).append(":").append(port).toString(), sessionHost) || System.currentTimeMillis() >= sessionUntilMs) ? "" : session;
    }

    public final void forgetSession() {
        session = "";
        sessionHost = null;
        sessionUntilMs = 0L;
    }

    public static /* synthetic */ Request.Builder withSession$default(Link link, Request.Builder builder, String str, int i, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            i = 8090;
        }
        return link.withSession(builder, str, i);
    }

    public final Request.Builder withSession(Request.Builder b, String h, int port) {
        Intrinsics.checkNotNullParameter(b, "b");
        String it = sessionFor(h, port);
        return it.length() > 0 ? b.header("X-StarView-Session", it) : b;
    }

    public static /* synthetic */ Auth ensureSession$default(Link link, String str, int i, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = 8090;
        }
        return link.ensureSession(str, i);
    }

    public final synchronized Auth ensureSession(String h, int port) {
        String string;
        String string2;
        Intrinsics.checkNotNullParameter(h, "h");
        if (!getPaired()) {
            return Auth.UNPAIRED;
        }
        boolean z = true;
        if (sessionFor(h, port).length() > 0) {
            return Auth.OK;
        }
        forgetSession();
        try {
            String cn = nonce();
            Response execute = getHttp().newCall(new Request.Builder().url("http://" + h + ":" + port + "/hello?n=" + cn).build()).execute();
            try {
                Response r = execute;
                if (r.code() == 404) {
                    Auth auth = Auth.OLD_BRIDGE;
                    CloseableKt.closeFinally(execute, null);
                    return auth;
                }
                if (!r.isSuccessful()) {
                    Auth auth2 = Auth.UNREACHABLE;
                    CloseableKt.closeFinally(execute, null);
                    return auth2;
                }
                ResponseBody body = r.body();
                if (body != null && (string = body.string()) != null) {
                    JSONObject hello = new JSONObject(string);
                    CloseableKt.closeFinally(execute, null);
                    String dongle = hello.optString("dongleId");
                    String sn = hello.optString("sn");
                    Intrinsics.checkNotNull(dongle);
                    Intrinsics.checkNotNull(sn);
                    String optString = hello.optString("proof");
                    Intrinsics.checkNotNullExpressionValue(optString, "optString(...)");
                    if (!commaProofOk(dongle, cn, sn, optString)) {
                        pairingRequired = true;
                        return Auth.NOT_OUR_COMMA;
                    }
                    JSONObject body2 = new JSONObject().put("sn", sn).put("cn", cn).put("proof", mac("cli", dongle, sn, cn));
                    OkHttpClient http2 = getHttp();
                    Request.Builder url = new Request.Builder().url("http://" + h + ":" + port + "/auth");
                    RequestBody.Companion companion = RequestBody.Companion;
                    String jSONObject = body2.toString();
                    Intrinsics.checkNotNullExpressionValue(jSONObject, "toString(...)");
                    execute = http2.newCall(url.post(companion.create(jSONObject, MediaType.Companion.get("application/json"))).build()).execute();
                    try {
                        Response r2 = execute;
                        if (r2.code() == 401) {
                            pairingRequired = true;
                            Auth auth3 = Auth.NOT_OUR_COMMA;
                            CloseableKt.closeFinally(execute, null);
                            return auth3;
                        }
                        if (!r2.isSuccessful()) {
                            Auth auth4 = Auth.UNREACHABLE;
                            CloseableKt.closeFinally(execute, null);
                            return auth4;
                        }
                        ResponseBody body3 = r2.body();
                        if (body3 != null && (string2 = body3.string()) != null) {
                            JSONObject j = new JSONObject(string2);
                            String s = j.optString("session");
                            Intrinsics.checkNotNull(s);
                            if (s.length() != 0) {
                                z = false;
                            }
                            if (z) {
                                Auth auth5 = Auth.UNREACHABLE;
                                CloseableKt.closeFinally(execute, null);
                                return auth5;
                            }
                            session = s;
                            sessionHost = h + ":" + port;
                            sessionUntilMs = System.currentTimeMillis() + (((j.optLong("expires_s", 3600L) * 1000) * 9) / 10);
                            pairingRequired = false;
                            Auth auth6 = Auth.OK;
                            CloseableKt.closeFinally(execute, null);
                            return auth6;
                        }
                        Auth auth7 = Auth.UNREACHABLE;
                        CloseableKt.closeFinally(execute, null);
                        return auth7;
                    } finally {
                    }
                }
                Auth auth8 = Auth.UNREACHABLE;
                CloseableKt.closeFinally(execute, null);
                return auth8;
            } finally {
                try {
                    throw th;
                } finally {
                }
            }
        } catch (Exception e) {
            return Auth.UNREACHABLE;
        }
    }

    public final Interceptor getAuthInterceptor() {
        return authInterceptor;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Response authInterceptor$lambda$8(Interceptor.Chain ch) {
        Intrinsics.checkNotNullParameter(ch, "ch");
        Request req = ch.request();
        String h = req.url().host();
        int port = req.url().port();
        if (INSTANCE.getPaired()) {
            if (INSTANCE.sessionFor(h, port).length() == 0) {
                INSTANCE.ensureSession(h, port);
            }
        }
        Response first = ch.proceed(INSTANCE.withSession(req.newBuilder(), h, port).build());
        if (first.code() == 401 && INSTANCE.getPaired()) {
            RequestBody body = req.body();
            if (!(body != null && body.isOneShot())) {
                INSTANCE.forgetSession();
                if (INSTANCE.ensureSession(h, port) != Auth.OK) {
                    return first;
                }
                first.close();
                return ch.proceed(INSTANCE.withSession(req.newBuilder(), h, port).build());
            }
        }
        return first;
    }
}
