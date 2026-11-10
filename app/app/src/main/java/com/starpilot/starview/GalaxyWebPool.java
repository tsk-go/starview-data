package com.starpilot.starview;

import android.app.DownloadManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.MutableContextWrapper;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Base64;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.JavascriptInterface;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import com.starpilot.starview.GalaxyWebPool;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import kotlin.Unit;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.StringsKt;
import org.json.JSONObject;

/* compiled from: GalaxyWeb.kt */
public final class GalaxyWebPool {
    public static final String QUIET = "/tools";
    private static String lastPath;
    private static String loadedHost;
    private static String pendingPath;
    private static volatile boolean ready;
    private static WebView web;
    private static MutableContextWrapper wrapper;
    public static final GalaxyWebPool INSTANCE = new GalaxyWebPool();
    private static final ArrayList<Function0<Unit>> waiting = new ArrayList<>();
    private static final Handler main = new Handler(Looper.getMainLooper());

    private GalaxyWebPool() {
    }

    public final WebView getWeb() {
        return web;
    }

    public final boolean getReady() {
        return ready;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String base(String host) {
        return "http://" + host + ":8082/";
    }

    public final void warm(Context ctx, String host) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(host, "host");
        get(ctx, host);
    }

    public final WebView get(Context ctx, String host) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(host, "host");
        WebView w = web;
        if (w == null) {
            final GalaxyWebPool $this$get_u24lambda_u244 = this;
            MutableContextWrapper it = new MutableContextWrapper(ctx);
            wrapper = it;
            final WebView $this$get_u24lambda_u244_u24lambda_u242 = new WebView(it);
            $this$get_u24lambda_u244_u24lambda_u242.setBackgroundColor(CarUi.Companion.getFRAME());
            $this$get_u24lambda_u244_u24lambda_u242.getSettings().setJavaScriptEnabled(true);
            $this$get_u24lambda_u244_u24lambda_u242.getSettings().setDomStorageEnabled(true);
            $this$get_u24lambda_u244_u24lambda_u242.getSettings().setMediaPlaybackRequiresUserGesture(false);
            $this$get_u24lambda_u244_u24lambda_u242.setWebChromeClient(new WebChromeClient());
            $this$get_u24lambda_u244_u24lambda_u242.addJavascriptInterface(new SaveBridge(it), "StarViewSave");
            $this$get_u24lambda_u244_u24lambda_u242.setDownloadListener(new DownloadListener() { // from class: com.starpilot.starview.GalaxyWebPool$$ExternalSyntheticLambda0
                @Override // android.webkit.DownloadListener
                public final void onDownloadStart(String str, String str2, String str3, String str4, long j) {
                    GalaxyWebPool.get$lambda$4$lambda$2$lambda$1(GalaxyWebPool.this, $this$get_u24lambda_u244_u24lambda_u242, str, str2, str3, str4, j);
                }
            });
            $this$get_u24lambda_u244_u24lambda_u242.setWebViewClient(new WebViewClient() { // from class: com.starpilot.starview.GalaxyWebPool$get$w$1$1$2
                @Override // android.webkit.WebViewClient
                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                    String str;
                    String base;
                    Intrinsics.checkNotNullParameter(view, "view");
                    Intrinsics.checkNotNullParameter(request, "request");
                    Intrinsics.checkNotNullExpressionValue(request.getUrl().toString(), "toString(...)");
                    GalaxyWebPool galaxyWebPool = GalaxyWebPool.this;
                    str = GalaxyWebPool.loadedHost;
                    if (str == null) {
                        str = "";
                    }
                    base = galaxyWebPool.base(str);
                    return !StringsKt.startsWith(r0, base, false);
                }

                @Override // android.webkit.WebViewClient
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    Intrinsics.checkNotNullParameter(view, "view");
                    GalaxyWebPool.ready = false;
                    GalaxyWebPool.this.inject(view);
                }

                @Override // android.webkit.WebViewClient
                public void onPageFinished(WebView view, String url) {
                    Intrinsics.checkNotNullParameter(view, "view");
                    GalaxyWebPool.this.inject(view);
                    GalaxyWebPool.this.pollReady(view, 0);
                }
            });
            web = $this$get_u24lambda_u244_u24lambda_u242;
            w = $this$get_u24lambda_u244_u24lambda_u242;
        }
        MutableContextWrapper mutableContextWrapper = wrapper;
        if (mutableContextWrapper != null) {
            mutableContextWrapper.setBaseContext(ctx);
        }
        if (!Intrinsics.areEqual(loadedHost, host)) {
            loadedHost = host;
            ready = false;
            w.loadUrl(base(host) + "#/tools");
        }
        return w;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void get$lambda$4$lambda$2$lambda$1(GalaxyWebPool $this_run, WebView $this_apply, String url, String str, String disposition, String mime, long j) {
        Intrinsics.checkNotNull(url);
        $this_run.download($this_apply, url, disposition, mime);
    }

    public final void go(String path) {
        Intrinsics.checkNotNullParameter(path, "path");
        WebView w = web;
        if (w == null) {
            return;
        }
        lastPath = path;
        if (!ready) {
            pendingPath = path;
        } else {
            String p = JSONObject.quote(path);
            w.evaluateJavascript("(function(){var p=" + p + ";if(location.hash.slice(1)!==p){location.hash=p;}})();", null);
        }
    }

    public final void reload() {
        ready = false;
        WebView webView = web;
        if (webView != null) {
            webView.reload();
        }
    }

    public final void park() {
        WebView w = web;
        if (w == null) {
            return;
        }
        if (ready) {
            w.evaluateJavascript("(function(){if(location.hash.slice(1)!=='/tools'){location.hash='/tools';}})();", null);
        } else {
            pendingPath = "/tools";
        }
    }

    private final void download(WebView w, String url, String disposition, String mime) {
        String e;
        Context ctx = w.getContext();
        String it = URLUtil.guessFileName(url, disposition, mime);
        Intrinsics.checkNotNull(it);
        if (StringsKt.startsWith(it, "downloadfile", false)) {
            long currentTimeMillis = System.currentTimeMillis();
            String e2 = MimeTypeMap.getSingleton().getExtensionFromMimeType(mime);
            if (e2 == null || (e = "." + e2) == null) {
                e = "";
            }
            it = "galaxy-" + currentTimeMillis + e;
        }
        String name = it;
        if (StringsKt.startsWith(url, "blob:", false)) {
            String q = JSONObject.quote(url);
            String n = JSONObject.quote(name);
            w.evaluateJavascript("(function(){fetch(" + q + ").then(r=>r.blob()).then(b=>{var f=new FileReader();f.onload=function(){StarViewSave.save(" + n + ",f.result.split(',')[1]||'')};f.readAsDataURL(b)})})();", null);
            return;
        }
        try {
            DownloadManager.Request req = new DownloadManager.Request(Uri.parse(url)).setTitle(name).setDescription("From Galaxy").setNotificationVisibility(1).setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "StarView/" + name);
            String it2 = CookieManager.getInstance().getCookie(url);
            if (it2 != null) {
                req.addRequestHeader("Cookie", it2);
            }
            Object systemService = ctx.getSystemService("download");
            Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.app.DownloadManager");
            ((DownloadManager) systemService).enqueue(req);
            Toast.makeText(ctx, "Saving " + name + " to Download/StarView", 1).show();
        } catch (Exception e3) {
            Toast.makeText(ctx, "Download failed: " + e3.getMessage(), 1).show();
        }
    }

    /* compiled from: GalaxyWeb.kt */
    public static final class SaveBridge {
        private final Context ctx;

        public SaveBridge(Context ctx) {
            Intrinsics.checkNotNullParameter(ctx, "ctx");
            this.ctx = ctx;
        }

        @JavascriptInterface
        public final void save(final String name, String b64) {
            Intrinsics.checkNotNullParameter(name, "name");
            Intrinsics.checkNotNullParameter(b64, "b64");
            try {
                byte[] bytes = Base64.decode(b64, 0);
                ContentValues values = new ContentValues();
                values.put("_display_name", name);
                values.put("relative_path", Environment.DIRECTORY_DOWNLOADS + "/StarView");
                Uri uri = this.ctx.getContentResolver().insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values);
                if (uri == null) {
                    throw new RuntimeException("no storage");
                }
                OutputStream openOutputStream = this.ctx.getContentResolver().openOutputStream(uri);
                if (openOutputStream != null) {
                    OutputStream outputStream = openOutputStream;
                    try {
                        OutputStream it = outputStream;
                        it.write(bytes);
                        Unit unit = Unit.INSTANCE;
                        CloseableKt.closeFinally(outputStream, null);
                    } finally {
                    }
                }
                new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.starpilot.starview.GalaxyWebPool$SaveBridge$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        GalaxyWebPool.SaveBridge.save$lambda$2(GalaxyWebPool.SaveBridge.this, name);
                    }
                });
            } catch (Exception e) {
                new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.starpilot.starview.GalaxyWebPool$SaveBridge$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        GalaxyWebPool.SaveBridge.save$lambda$3(GalaxyWebPool.SaveBridge.this, e);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void save$lambda$2(SaveBridge this$0, String $name) {
            Toast.makeText(this$0.ctx, "Saved " + $name + " to Download/StarView", 1).show();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void save$lambda$3(SaveBridge this$0, Exception $e) {
            Toast.makeText(this$0.ctx, "Save failed: " + $e.getMessage(), 1).show();
        }
    }

    public final void whenReady(Function0<Unit> fn) {
        Intrinsics.checkNotNullParameter(fn, "fn");
        if (ready) {
            fn.invoke();
        } else {
            waiting.add(fn);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void pollReady(final WebView view, final int tries) {
        view.evaluateJavascript("(function(){var b=document.getElementById('galaxy-boot');return !!document.querySelector('.gx-content')&&(!b||!b.isConnected||b.hidden);})()", new ValueCallback() { // from class: com.starpilot.starview.GalaxyWebPool$$ExternalSyntheticLambda2
            @Override // android.webkit.ValueCallback
            public final void onReceiveValue(Object obj) {
                GalaxyWebPool.pollReady$lambda$11(tries, view, (String) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void pollReady$lambda$11(final int $tries, final WebView $view, String r) {
        if (Intrinsics.areEqual(r, "true") || $tries >= 60) {
            ready = true;
            String it = pendingPath;
            if (it != null) {
                pendingPath = null;
                INSTANCE.go(it);
            }
            Iterable fns = new ArrayList(waiting);
            waiting.clear();
            Iterable $this$forEach$iv = fns;
            for (Object element$iv : $this$forEach$iv) {
                ((Function0) element$iv).invoke();
            }
            return;
        }
        main.postDelayed(new Runnable() { // from class: com.starpilot.starview.GalaxyWebPool$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                GalaxyWebPool.pollReady$lambda$11$lambda$10($view, $tries);
            }
        }, 100L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void pollReady$lambda$11$lambda$10(WebView $view, int $tries) {
        INSTANCE.pollReady($view, $tries + 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void inject(WebView view) {
        String css = StringsKt.replace(StringsKt.replace(getCSS(), "\\", "\\\\", false), "`", "\\`", false);
        view.evaluateJavascript("(function(){try{localStorage.setItem('galaxy-preview-theme','dark');}catch(e){}\n      var s=document.getElementById('starview-theme');if(!s){s=document.createElement('style');s.id='starview-theme';(document.head||document.documentElement).appendChild(s);}\n      s.textContent=`" + css + "`;})();", null);
    }

    private final String hex(int c) {
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        String format = String.format("#%06X", Arrays.copyOf(new Object[]{Integer.valueOf(16777215 & c)}, 1));
        Intrinsics.checkNotNullExpressionValue(format, "format(...)");
        return format;
    }

    public final String getCSS() {
        return StringsKt.trimIndent("\n    :root, [data-theme=\"light\"] { color-scheme: dark;\n      --primary: " + hex(CarUi.Companion.getACCENT()) + "; --primary-hover: " + hex(CarUi.Companion.getACCENT()) + "; --primary-glow: rgba(111,227,161,.25); --on-primary: " + hex(CarUi.Companion.getBG()) + ";\n      --primary-container: rgba(111,227,161,.16); --on-primary-container: #d8ffe9;\n      --secondary: " + hex(CarUi.Companion.getACCENT()) + "; --secondary-glow: rgba(111,227,161,.2); --success: " + hex(CarUi.Companion.getACCENT()) + "; --warning: " + hex(CarUi.Companion.getWARN()) + ";\n      --background: " + hex(CarUi.Companion.getFRAME()) + "; --surface: " + hex(CarUi.Companion.getCARD()) + "; --surface-container: " + hex(CarUi.Companion.getCARD()) + "; --surface-container-high: " + hex(CarUi.Companion.getCARD_ON()) + ";\n      --on-surface: #FFFFFF; --on-surface-variant: " + hex(CarUi.Companion.getDIM()) + "; --text-muted: " + hex(CarUi.Companion.getDIM()) + ";\n      --glass-bg: " + hex(CarUi.Companion.getCARD()) + "; --glass-bg-hover: " + hex(CarUi.Companion.getCARD_ON()) + "; --glass-active-bg: rgba(111,227,161,.18); --glass-border: #3C424E;\n      --glass-shadow: none; --glass-blur: none; --glass-text: #FFFFFF; --outline: #323742; --outline-variant: #262A33;\n      --font-body: Roboto, \"Noto Sans\", sans-serif; --radius-sm: 14px; --radius-md: 14px; --radius-lg: 18px;\n      --scrollbar-thumb: #3A404C; --scrollbar-thumb-hover: #4A515E; --scrollbar-border: transparent;\n      --bottomnav-height: 0px; --content-width: 100%; }\n    html, body { background: " + hex(CarUi.Companion.getFRAME()) + " !important; font-size: 17px; }\n    #galaxy-bg, .gx-appbar, .gx-drawer, .gx-underlay, .blur-nav, .gx-nav-indicator, #galaxy-boot, .gx-boot { display: none !important; }\n    * { -webkit-backdrop-filter: none !important; backdrop-filter: none !important; }\n    .gx-content { max-width: none !important; margin-top: 0 !important; padding-top: 4px !important; }\n  ");
    }
}
