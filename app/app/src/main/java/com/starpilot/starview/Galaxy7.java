package com.starpilot.starview;

import android.os.Handler;
import android.os.Looper;
import java.io.InterruptedIOException;
import java.util.Iterator;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.json.JSONObject;

/* compiled from: Galaxy7.kt */
public final class Galaxy7 {
    public static final Companion Companion = new Companion(null);
    private static volatile Galaxy7 inst;
    private final String base;
    private final OkHttpClient client;
    private volatile String cookie;
    private final String host;
    private final ExecutorService io;
    private final MediaType json;
    private final Handler main;

    /* renamed from: slow$delegate, reason: from kotlin metadata */
    private final Lazy slow$delegate;

    public /* synthetic */ Galaxy7(String str, DefaultConstructorMarker defaultConstructorMarker) {
        this(str);
    }

    /* compiled from: Galaxy7.kt */
    public static final class Error extends Exception {
        private final int code;

        /* VF: from Vineflower (jadx: ) */
        public Error(int var1, String var2) {
            super(var2);
            Intrinsics.checkNotNullParameter(var2, "message");
            this.code = var1;
        }

        public final int getCode() {
            return this.code;
        }
    }

    private Galaxy7(String host) {
        this.host = host;
        this.base = "http://" + this.host + ":8082";
        this.client = new OkHttpClient.Builder().connectTimeout(4L, TimeUnit.SECONDS).readTimeout(12L, TimeUnit.SECONDS).build();
        this.io = Executors.newFixedThreadPool(3);
        this.main = new Handler(Looper.getMainLooper());
        this.cookie = "";
        this.json = MediaType.Companion.get("application/json");
        this.slow$delegate = LazyKt.lazy(new Function0() { // from class: com.starpilot.starview.Galaxy7$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                OkHttpClient slow_delegate$lambda$7;
                slow_delegate$lambda$7 = Galaxy7.slow_delegate$lambda$7(Galaxy7.this);
                return slow_delegate$lambda$7;
            }
        });
    }

    public final String getHost() {
        return this.host;
    }

    private final void session(boolean force) {
        Object element$iv;
        synchronized (this) {
            if ((this.cookie.length() > 0) && !force) {
                return;
            }
            Request.Builder $this$session_u24lambda_u243_u24lambda_u240 = new Request.Builder().url(this.base + "/api/auth/session");
            if (this.cookie.length() > 0) {
                $this$session_u24lambda_u243_u24lambda_u240.header("Cookie", this.cookie);
            }
            Request req = $this$session_u24lambda_u243_u24lambda_u240.build();
            Response execute = this.client.newCall(req).execute();
            Throwable execute$c = null;
            try {
                Response r = execute;
                Iterable $this$firstOrNull$iv = r.headers("Set-Cookie");
                Iterator it = $this$firstOrNull$iv.iterator();
                while (true) {
                    if (it.hasNext()) {
                        element$iv = it.next();
                        String it2 = (String) element$iv;
                        if (StringsKt.startsWith(it2, "galaxy_session=", false)) {
                            break;
                        }
                    } else {
                        element$iv = null;
                        break;
                    }
                }
                String set = (String) element$iv;
                if (set != null) {
                    this.cookie = StringsKt.substringBefore(set, ";", set);
                }
                if (!r.isSuccessful()) {
                    throw new Error(r.code(), "Galaxy sign-in failed (HTTP " + r.code() + ")");
                }
                if (!(this.cookie.length() == 0)) {
                    Unit unit = Unit.INSTANCE;
                    Unit unit2 = Unit.INSTANCE;
                    return;
                }
                throw new Error(401, "Galaxy gave no local session (is the tablet on the comma's own network?)");
            } catch (Throwable execute$t) {
                execute$c = execute$t;
                throw execute$t;
            } finally {
                CloseableKt.closeFinally(execute, execute$c);
            }
        }
    }

    static /* synthetic */ JSONObject call$default(Galaxy7 galaxy7, String str, String str2, JSONObject jSONObject, boolean z, int i, Object obj) {
        if ((i & 8) != 0) {
            z = true;
        }
        return galaxy7.call(str, str2, jSONObject, z);
    }

    /* JADX WARN: Code restructure failed: missing block: B:32:0x00d8, code lost:
    
        if (r8 == null) goto L37;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final JSONObject call(String method, String path, JSONObject body, boolean retry) {
        String text;
        Object m1191constructorimpl;
        String str;
        String optString;
        session(false);
        Request.Builder b = new Request.Builder().url(this.base + path).header("Cookie", this.cookie);
        if (body != null) {
            Request.Builder header = b.header("Origin", this.base);
            RequestBody.Companion companion = RequestBody.Companion;
            String jSONObject = body.toString();
            Intrinsics.checkNotNullExpressionValue(jSONObject, "toString(...)");
            byte[] bytes = jSONObject.getBytes(Charsets.UTF_8);
            Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
            header.method(method, RequestBody.Companion.create(bytes, this.json, 0, bytes.length));
        } else {
            b.get();
        }
        Response execute = this.client.newCall(b.build()).execute();
        Throwable execute$c = null;
        try {
            Response r = execute;
            ResponseBody body2 = r.body();
            if (body2 == null || (text = body2.string()) == null) {
                text = "";
            }
            String str2 = null;
            if (r.code() == 401 && retry) {
                session(true);
                JSONObject call = call(method, path, body, false);
                return call;
            }
            try {
                Result.Companion companion2 = Result.Companion;
                Galaxy7 galaxy7 = this;
                m1191constructorimpl = KtCompat.resultOf(new JSONObject(text));
            } catch (Throwable th) {
                Result.Companion companion3 = Result.Companion;
                m1191constructorimpl = KtCompat.resultOf(ResultKt.createFailure(th));
            }
            if (KtCompat.isFailure(m1191constructorimpl)) {
                m1191constructorimpl = null;
            }
            JSONObject obj = (JSONObject) m1191constructorimpl;
            if (r.isSuccessful()) {
                if (obj == null) {
                    obj = new JSONObject();
                }
                return obj;
            }
            int code = r.code();
            if (obj != null && (optString = obj.optString("error")) != null) {
                String str3 = optString;
                if (!StringsKt.isBlank(str3)) {
                    str2 = str3;
                }
                str = str2;
            }
            str = "Galaxy answered HTTP " + r.code();
            throw new Error(code, str);
        } catch (Throwable execute$t) {
            execute$c = execute$t;
            throw execute$t;
        } finally {
            CloseableKt.closeFinally(execute, execute$c);
        }
    }

    private final OkHttpClient getSlow() {
        return (OkHttpClient) this.slow$delegate.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final OkHttpClient slow_delegate$lambda$7(Galaxy7 this$0) {
        return this$0.client.newBuilder().readTimeout(30L, TimeUnit.SECONDS).build();
    }

    public final void postImage(final String path, final JSONObject body, final Function2<? super byte[], ? super String, Unit> done) {
        Intrinsics.checkNotNullParameter(path, "path");
        Intrinsics.checkNotNullParameter(body, "body");
        Intrinsics.checkNotNullParameter(done, "done");
        this.io.execute(new Runnable() { // from class: com.starpilot.starview.Galaxy7$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                Galaxy7.postImage$lambda$12(Galaxy7.this, path, body, done);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x00a6, code lost:
    
        if (r6 == null) goto L39;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void postImage$lambda$12(Galaxy7 this$0, String $path, JSONObject $body, final Function2 $done) {
        Pair pair;
        String type;
        Object m1191constructorimpl;
        String str;
        String str2;
        byte[] bArr;
        String substringBefore$default;
        String obj;
        try {
            Response r = postImage$lambda$12$once(this$0, $path, $body);
            if (r.code() == 401) {
                r.close();
                this$0.session(true);
                r = postImage$lambda$12$once(this$0, $path, $body);
            }
            Response response = r;
            Throwable response$c = null;
            try {
                Response it = response;
                String header$default = it.header("Content-Type", null);
                if (header$default == null || (substringBefore$default = StringsKt.substringBefore(header$default, ";", header$default)) == null || (obj = StringsKt.trim((CharSequence) substringBefore$default).toString()) == null) {
                    type = null;
                } else {
                    type = obj.toLowerCase(Locale.ROOT);
                    Intrinsics.checkNotNullExpressionValue(type, "toLowerCase(...)");
                }
                if (it.code() == 409) {
                    pair = TuplesKt.to(null, "Turn the vehicle off before taking a snapshot.");
                } else if (!it.isSuccessful()) {
                    try {
                        Result.Companion companion = Result.Companion;
                        ResponseBody body = it.body();
                        if (body == null || (str2 = body.string()) == null) {
                            str2 = "";
                        }
                        m1191constructorimpl = KtCompat.resultOf(new JSONObject(str2).optString("error"));
                    } catch (Throwable th) {
                        Result.Companion companion2 = Result.Companion;
                        m1191constructorimpl = KtCompat.resultOf(ResultKt.createFailure(th));
                    }
                    if (KtCompat.isFailure(m1191constructorimpl)) {
                        m1191constructorimpl = null;
                    }
                    String str3 = (String) m1191constructorimpl;
                    if (str3 != null) {
                        String str4 = str3;
                        if (StringsKt.isBlank(str4)) {
                            str4 = null;
                        }
                        str = str4;
                    }
                    str = "Camera frame unavailable. Try taking a new snapshot.";
                    pair = TuplesKt.to(null, str);
                } else if (!Intrinsics.areEqual(type, "image/jpeg")) {
                    pair = TuplesKt.to(null, "Camera image is unavailable.");
                } else {
                    ResponseBody body2 = it.body();
                    if (body2 == null || (bArr = body2.bytes()) == null) {
                        bArr = new byte[0];
                    }
                    pair = TuplesKt.to(bArr, null);
                }
            } catch (Throwable response$t) {
                response$c = response$t;
                throw response$t;
            } finally {
                CloseableKt.closeFinally(response, response$c);
            }
        } catch (Error e) {
            String message = e.getMessage();
            if (message == null) {
                message = "Galaxy error";
            }
            pair = TuplesKt.to(null, message);
        } catch (InterruptedIOException e2) {
            pair = TuplesKt.to(null, "Snapshot timed out. Try taking a new snapshot.");
        } catch (Exception e3) {
            pair = TuplesKt.to(null, "Can't reach Galaxy on the comma (" + e3.getClass().getSimpleName() + ")");
        }
        final Pair res = pair;
        this$0.main.post(new Runnable() { // from class: com.starpilot.starview.Galaxy7$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                Galaxy7.postImage$lambda$12$lambda$11($done, res);
            }
        });
    }

    private static final Response postImage$lambda$12$once(Galaxy7 this$0, String $path, JSONObject $body) {
        this$0.session(false);
        Request.Builder header = new Request.Builder().url(this$0.base + $path).header("Cookie", this$0.cookie).header("Origin", this$0.base);
        RequestBody.Companion companion = RequestBody.Companion;
        String jSONObject = $body.toString();
        Intrinsics.checkNotNullExpressionValue(jSONObject, "toString(...)");
        byte[] bytes = jSONObject.getBytes(Charsets.UTF_8);
        Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
        Request req = header.post(RequestBody.Companion.create(bytes, this$0.json, 0, bytes.length)).build();
        return this$0.getSlow().newCall(req).execute();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void postImage$lambda$12$lambda$11(Function2 $done, Pair $res) {
        $done.invoke($res.getFirst(), $res.getSecond());
    }

    public final JSONObject getBlocking(String path) {
        Intrinsics.checkNotNullParameter(path, "path");
        return call$default(this, "GET", path, null, false, 8, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final JSONObject get$lambda$13(Galaxy7 this$0, String $path) {
        return call$default(this$0, "GET", $path, null, false, 8, null);
    }

    public final void get(final String path, Function2<? super JSONObject, ? super String, Unit> done) {
        Intrinsics.checkNotNullParameter(path, "path");
        Intrinsics.checkNotNullParameter(done, "done");
        async(new Function0() { // from class: com.starpilot.starview.Galaxy7$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                JSONObject jSONObject;
                jSONObject = Galaxy7.get$lambda$13(Galaxy7.this, path);
                return jSONObject;
            }
        }, done);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final JSONObject post$lambda$14(Galaxy7 this$0, String $path, JSONObject $body) {
        return call$default(this$0, "POST", $path, $body, false, 8, null);
    }

    public final void post(final String path, final JSONObject body, Function2<? super JSONObject, ? super String, Unit> done) {
        Intrinsics.checkNotNullParameter(path, "path");
        Intrinsics.checkNotNullParameter(body, "body");
        Intrinsics.checkNotNullParameter(done, "done");
        async(new Function0() { // from class: com.starpilot.starview.Galaxy7$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                JSONObject post$lambda$14;
                post$lambda$14 = Galaxy7.post$lambda$14(Galaxy7.this, path, body);
                return post$lambda$14;
            }
        }, done);
    }

    private final void async(final Function0<? extends JSONObject> work, final Function2<? super JSONObject, ? super String, Unit> done) {
        this.io.execute(new Runnable() { // from class: com.starpilot.starview.Galaxy7$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                Galaxy7.async$lambda$16(work, Galaxy7.this, done);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void async$lambda$16(Function0 $work, Galaxy7 this$0, final Function2 $done) {
        Pair res;
        try {
            res = TuplesKt.to($work.invoke(), null);
        } catch (Error e) {
            String message = e.getMessage();
            if (message == null) {
                message = "Galaxy error";
            }
            res = TuplesKt.to(null, message);
        } catch (Exception e2) {
            res = TuplesKt.to(null, "Can't reach Galaxy on the comma (" + e2.getClass().getSimpleName() + ")");
        }
        final var res$f = res;
        this$0.main.post(new Runnable() { // from class: com.starpilot.starview.Galaxy7$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                Galaxy7.async$lambda$16$lambda$15($done, res$f);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void async$lambda$16$lambda$15(Function2 $done, Pair $res) {
        $done.invoke($res.getFirst(), $res.getSecond());
    }

    /* compiled from: Galaxy7.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final Galaxy7 get(String host) {
            Intrinsics.checkNotNullParameter(host, "host");
            Galaxy7 i = Galaxy7.inst;
            if (i != null && Intrinsics.areEqual(i.getHost(), host)) {
                return i;
            }
            Galaxy7 it = new Galaxy7(host, null);
            Companion companion = Galaxy7.Companion;
            Galaxy7.inst = it;
            return it;
        }

        public final Galaxy7 current() {
            String it = Link.INSTANCE.getHost();
            if (it != null) {
                return Galaxy7.Companion.get(it);
            }
            return null;
        }
    }
}
