package com.starpilot.starview;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.inputmethod.InputMethodManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import com.starpilot.starview.TermWeb;
import java.io.File;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.comparisons.ComparisonsKt;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: TermWeb.kt */
public final class TermWeb {
    public static final TermWeb INSTANCE = new TermWeb();
    private static String loadedHost;
    private static WebView web;

    private TermWeb() {
    }

    public final WebView getWeb() {
        return web;
    }

    /* compiled from: TermWeb.kt */
    public static final class Bridge {
        private final Context ctx;

        /* renamed from: http$delegate, reason: from kotlin metadata */
        private final Lazy http;
        private final WebView web;

        public Bridge(Context ctx, WebView web) {
            Intrinsics.checkNotNullParameter(ctx, "ctx");
            Intrinsics.checkNotNullParameter(web, "web");
            this.ctx = ctx;
            this.web = web;
            this.http = LazyKt.lazy(new Function0() { // from class: com.starpilot.starview.TermWeb$Bridge$$ExternalSyntheticLambda5
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    OkHttpClient http_delegate$lambda$0;
                    http_delegate$lambda$0 = TermWeb.Bridge.http_delegate$lambda$0();
                    return http_delegate$lambda$0;
                }
            });
        }

        @JavascriptInterface
        public final String clipboard() {
            ClipData.Item itemAt;
            CharSequence coerceToText;
            String obj;
            Object systemService = this.ctx.getSystemService("clipboard");
            Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.content.ClipboardManager");
            ClipboardManager cm = (ClipboardManager) systemService;
            ClipData primaryClip = cm.getPrimaryClip();
            return (primaryClip == null || (itemAt = primaryClip.getItemAt(0)) == null || (coerceToText = itemAt.coerceToText(this.ctx)) == null || (obj = coerceToText.toString()) == null) ? "" : obj;
        }

        @JavascriptInterface
        public final void setClipboard(String text) {
            Intrinsics.checkNotNullParameter(text, "text");
            Object systemService = this.ctx.getSystemService("clipboard");
            Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.content.ClipboardManager");
            ClipboardManager cm = (ClipboardManager) systemService;
            cm.setPrimaryClip(ClipData.newPlainText("StarView terminal", text));
        }

        @JavascriptInterface
        public final String host() {
            String host = Link.INSTANCE.getHost();
            return host == null ? "" : host;
        }

        @JavascriptInterface
        public final String session() {
            Link.INSTANCE.loadKey(this.ctx);
            String h = Link.INSTANCE.getHost();
            if (h == null) {
                return "";
            }
            Link.INSTANCE.ensureSession(h, Link.DAEMON_PORT);
            return Link.INSTANCE.sessionFor(h, Link.DAEMON_PORT);
        }

        @JavascriptInterface
        public final void sessionRefused() {
            Link.INSTANCE.forgetSession();
        }

        @JavascriptInterface
        public final String savedCommands() {
            String string = this.ctx.getSharedPreferences("starview_term", 0).getString("saved", "");
            return string == null ? "" : string;
        }

        @JavascriptInterface
        public final void setSavedCommands(String json) {
            Intrinsics.checkNotNullParameter(json, "json");
            this.ctx.getSharedPreferences("starview_term", 0).edit().putString("saved", json).apply();
        }

        @JavascriptInterface
        public final boolean onroad() {
            Telemetry telemetry = Link.INSTANCE.getTelemetry();
            return telemetry != null && telemetry.getStarted();
        }

        private final OkHttpClient getHttp() {
            return (OkHttpClient) this.http.getValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final OkHttpClient http_delegate$lambda$0() {
            return new OkHttpClient.Builder().addInterceptor(Link.INSTANCE.getAuthInterceptor()).connectTimeout(5L, TimeUnit.SECONDS).readTimeout(0L, TimeUnit.MILLISECONDS).build();
        }

        private final boolean js(final String code) {
            return this.web.post(new Runnable() { // from class: com.starpilot.starview.TermWeb$Bridge$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    TermWeb.Bridge.js$lambda$1(TermWeb.Bridge.this, code);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void js$lambda$1(Bridge this$0, String $code) {
            this$0.web.evaluateJavascript($code, null);
        }

        private final JSONArray list(String dir) {
            String body;
            String h = Link.INSTANCE.getHost();
            if (h == null) {
                throw new RuntimeException("not connected");
            }
            Response execute = getHttp().newCall(new Request.Builder().url("http://" + h + ":8090/fs/list?path=" + Uri.encode(dir)).build()).execute();
            try {
                Response r = execute;
                ResponseBody body2 = r.body();
                if (body2 == null || (body = body2.string()) == null) {
                    body = "";
                }
                if (r.isSuccessful()) {
                    JSONArray jSONArray = new JSONObject(body).getJSONArray("entries");
                    Intrinsics.checkNotNullExpressionValue(jSONArray, "getJSONArray(...)");
                    CloseableKt.closeFinally(execute, null);
                    return jSONArray;
                }
                String str = body;
                if (str.length() == 0) {
                    str = "HTTP " + r.code();
                }
                throw new RuntimeException(str);
            } finally {
            }
        }

        @JavascriptInterface
        public final void checkFile(final String path) {
            Intrinsics.checkNotNullParameter(path, "path");
            new Thread(new Runnable() { // from class: com.starpilot.starview.TermWeb$Bridge$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    TermWeb.Bridge.checkFile$lambda$6(TermWeb.Bridge.this, path);
                }
            }).start();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void checkFile$lambda$6(Bridge this$0, String $path) {
            try {
                Result.Companion companion = Result.Companion;
                String substringBeforeLast$default = StringsKt.substringBeforeLast($path, '/', $path);
                if (substringBeforeLast$default.length() == 0) {
                    substringBeforeLast$default = "/";
                }
                String dir = substringBeforeLast$default;
                String name = StringsKt.substringAfterLast($path, '/', $path);
                JSONArray a = this$0.list(dir);
                int i = 0;
                int length = a.length();
                while (true) {
                    if (i >= length) {
                        break;
                    }
                    JSONObject e = a.getJSONObject(i);
                    if (!Intrinsics.areEqual(e.optString("name"), name) || e.optBoolean("dir")) {
                        i++;
                    } else {
                        this$0.js("window.fileSeen(" + JSONObject.quote($path) + ", " + e.optLong("size") + ")");
                        break;
                    }
                }
                KtCompat.resultOf(Unit.INSTANCE);
            } catch (Throwable th) {
                Result.Companion companion2 = Result.Companion;
                KtCompat.resultOf(ResultKt.createFailure(th));
            }
        }

        @JavascriptInterface
        public final void listRecent(final String dir) {
            Intrinsics.checkNotNullParameter(dir, "dir");
            new Thread(new Runnable() { // from class: com.starpilot.starview.TermWeb$Bridge$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    TermWeb.Bridge.listRecent$lambda$12(TermWeb.Bridge.this, dir);
                }
            }).start();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void listRecent$lambda$12(Bridge this$0, String $dir) {
            String str = $dir;
            JSONArray out = new JSONArray();
            try {
                Result.Companion companion = Result.Companion;
                JSONArray a = this$0.list(str);
                Iterable $this$map$iv = RangesKt.until(0, a.length());
                Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                Iterator<Integer> it = $this$map$iv.iterator();
                while (it.hasNext()) {
                    int item$iv$iv = ((IntIterator) it).nextInt();
                    destination$iv$iv.add(a.getJSONObject(item$iv$iv));
                }
                Iterable $this$filter$iv = (List) destination$iv$iv;
                Collection destination$iv$iv2 = new ArrayList();
                for (Object element$iv$iv : $this$filter$iv) {
                    if (!((JSONObject) element$iv$iv).optBoolean("dir")) {
                        destination$iv$iv2.add(element$iv$iv);
                    }
                }
                Iterable $this$sortedByDescending$iv = (List) destination$iv$iv2;
                Iterable $this$forEach$iv = CollectionsKt.take(CollectionsKt.sortedWith($this$sortedByDescending$iv, new Comparator() { // from class: com.starpilot.starview.TermWeb$Bridge$listRecent$lambda$12$lambda$11$$inlined$sortedByDescending$1
                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // java.util.Comparator
                    public final int compare(T t, T t2) {
                        JSONObject it2 = (JSONObject) t2;
                        JSONObject it3 = (JSONObject) t;
                        return ComparisonsKt.compareValues(Long.valueOf(it2.optLong("mtime")), Long.valueOf(it3.optLong("mtime")));
                    }
                }), 10);
                for (Object element$iv : $this$forEach$iv) {
                    JSONObject it2 = (JSONObject) element$iv;
                    out.put(new JSONObject().put("path", StringsKt.trimEnd(str, '/') + "/" + it2.optString("name")).put("size", it2.optLong("size")));
                    str = $dir;
                }
                KtCompat.resultOf(Unit.INSTANCE);
            } catch (Throwable th) {
                Result.Companion companion2 = Result.Companion;
                KtCompat.resultOf(ResultKt.createFailure(th));
            }
            this$0.js("window.recentFiles(" + JSONObject.quote(out.toString()) + ")");
        }

        @JavascriptInterface
        public final void saveToDownloads(final String path) {
            Intrinsics.checkNotNullParameter(path, "path");
            new Thread(new Runnable() { // from class: com.starpilot.starview.TermWeb$Bridge$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    TermWeb.Bridge.saveToDownloads$lambda$17(path, this);
                }
            }).start();
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Code restructure failed: missing block: B:20:0x0092, code lost:
        
            if (r2 == null) goto L21;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public static final void saveToDownloads$lambda$17(String $path, Bridge this$0) {
            Pair pair;
            String h;
            Object m1191constructorimpl;
            String str;
            String string;
            String name = StringsKt.substringAfterLast($path, '/', $path);
            try {
                h = Link.INSTANCE.getHost();
            } catch (Exception e) {
                pair = TuplesKt.to(false, "Couldn't save " + name + ": " + e.getMessage());
            }
            if (h == null) {
                throw new RuntimeException("not connected to the comma");
            }
            File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            Response execute = this$0.getHttp().newCall(new Request.Builder().url("http://" + h + ":8090/fs/get?path=" + Uri.encode($path)).build()).execute();
            try {
                Response r = execute;
                boolean z = true;
                if (!r.isSuccessful()) {
                    ResponseBody body = r.body();
                    if (body != null && (string = body.string()) != null) {
                        String str2 = string;
                        if (str2.length() != 0) {
                            z = false;
                        }
                        if (z) {
                            str2 = "HTTP " + r.code();
                        }
                        str = str2;
                    }
                    str = "HTTP " + r.code();
                    throw new RuntimeException(str);
                }
                ResponseBody body2 = r.body();
                Intrinsics.checkNotNull(body2);
                byte[] bytes = body2.bytes();
                try {
                    Result.Companion companion = Result.Companion;
                    Intrinsics.checkNotNull(dir);
                    m1191constructorimpl = KtCompat.resultOf(this$0.writeFile(dir, name, bytes));
                } catch (Throwable th) {
                    Result.Companion companion2 = Result.Companion;
                    m1191constructorimpl = KtCompat.resultOf(ResultKt.createFailure(th));
                }
                if (KtCompat.exceptionOrNull(m1191constructorimpl) != null) {
                    m1191constructorimpl = this$0.writeMediaStore(name, bytes);
                }
                String saved = (String) m1191constructorimpl;
                CloseableKt.closeFinally(execute, null);
                pair = TuplesKt.to(true, "Saved to Downloads ✓  " + saved);
                boolean ok = ((Boolean) pair.component1()).booleanValue();
                String msg = (String) pair.component2();
                this$0.js("window.dlDone(" + JSONObject.quote($path) + ", " + ok + ", " + JSONObject.quote(msg) + ")");
            } finally {
            }
        }

        private final String freeName(File dir, String name) {
            if (!new File(dir, name).exists()) {
                return name;
            }
            Integer valueOf = Integer.valueOf(StringsKt.lastIndexOf((CharSequence) name, '.', kotlin.text.StringsKt.getLastIndex(((CharSequence) name)), false));
            int it = valueOf.intValue();
            if (!(it > 0)) {
                valueOf = null;
            }
            int dot = valueOf != null ? valueOf.intValue() : name.length();
            int i = 1;
            while (true) {
                String substring = name.substring(0, dot);
                Intrinsics.checkNotNullExpressionValue(substring, "substring(...)");
                String substring2 = name.substring(dot);
                Intrinsics.checkNotNullExpressionValue(substring2, "substring(...)");
                if (!new File(dir, substring + " (" + i + ")" + substring2).exists()) {
                    String substring3 = name.substring(0, dot);
                    Intrinsics.checkNotNullExpressionValue(substring3, "substring(...)");
                    String substring4 = name.substring(dot);
                    Intrinsics.checkNotNullExpressionValue(substring4, "substring(...)");
                    return substring3 + " (" + i + ")" + substring4;
                }
                i++;
            }
        }

        private final String writeFile(File dir, String name, byte[] bytes) {
            dir.mkdirs();
            String n = freeName(dir, name);
            FilesKt.writeBytes(new File(dir, n), bytes);
            return n;
        }

        private final String writeMediaStore(String name, byte[] bytes) {
            ContentValues cv = new ContentValues();
            cv.put("_display_name", name);
            Uri uri = this.ctx.getContentResolver().insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, cv);
            if (uri == null) {
                throw new RuntimeException("Downloads not writable");
            }
            OutputStream openOutputStream = this.ctx.getContentResolver().openOutputStream(uri);
            Intrinsics.checkNotNull(openOutputStream);
            OutputStream outputStream = openOutputStream;
            try {
                OutputStream it = outputStream;
                it.write(bytes);
                Unit unit = Unit.INSTANCE;
                CloseableKt.closeFinally(outputStream, null);
                return name;
            } finally {
            }
        }

        @JavascriptInterface
        public final void showKeyboard() {
            this.web.post(new Runnable() { // from class: com.starpilot.starview.TermWeb$Bridge$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    TermWeb.Bridge.showKeyboard$lambda$21(TermWeb.Bridge.this);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void showKeyboard$lambda$21(Bridge this$0) {
            this$0.web.requestFocus();
            Object systemService = this$0.ctx.getSystemService("input_method");
            Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.view.inputmethod.InputMethodManager");
            ((InputMethodManager) systemService).showSoftInput(this$0.web, 1);
        }
    }

    public final WebView get(Context ctx, String host) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(host, "host");
        WebView $this$get_u24lambda_u240 = web;
        if ($this$get_u24lambda_u240 == null) {
            $this$get_u24lambda_u240 = new WebView(ctx.getApplicationContext());
            $this$get_u24lambda_u240.getSettings().setJavaScriptEnabled(true);
            $this$get_u24lambda_u240.getSettings().setDomStorageEnabled(true);
            $this$get_u24lambda_u240.getSettings().setAllowFileAccess(true);
            $this$get_u24lambda_u240.getSettings().setAllowContentAccess(true);
            $this$get_u24lambda_u240.getSettings().setSupportZoom(false);
            $this$get_u24lambda_u240.getSettings().setMediaPlaybackRequiresUserGesture(false);
            $this$get_u24lambda_u240.setFocusable(true);
            $this$get_u24lambda_u240.setFocusableInTouchMode(true);
            $this$get_u24lambda_u240.setWebChromeClient(new WebChromeClient());
            $this$get_u24lambda_u240.setBackgroundColor(Color.rgb(11, 13, 18));
            Context applicationContext = ctx.getApplicationContext();
            Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
            $this$get_u24lambda_u240.addJavascriptInterface(new Bridge(applicationContext, $this$get_u24lambda_u240), "StarView");
            web = $this$get_u24lambda_u240;
        }
        if (!Intrinsics.areEqual(loadedHost, host)) {
            loadedHost = host;
            $this$get_u24lambda_u240.loadUrl("file:///android_asset/term.html#" + URLEncoder.encode(host, "UTF-8"));
        }
        return $this$get_u24lambda_u240;
    }

    public final void detach() {
        WebView webView = web;
        ViewParent parent = webView != null ? webView.getParent() : null;
        ViewGroup viewGroup = parent instanceof ViewGroup ? (ViewGroup) parent : null;
        if (viewGroup != null) {
            viewGroup.removeView(web);
        }
    }
}
