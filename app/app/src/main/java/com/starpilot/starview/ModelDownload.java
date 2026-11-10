package com.starpilot.starview;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.util.Arrays;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* compiled from: ModelDownload.kt */
public final class ModelDownload {
    private static final String BASE = "https://raw.githubusercontent.com/tsk-go/openpilot/starview-models/";
    public static final String DET_NAME = "yolox_s_384x640_fp16.lite";
    public static final String DET_SHA = "bca6acaba042dba16a5020521c707603d579f2061165dd9aa76929211744a587";
    public static final long DET_SIZE = 17995664;
    private static final String TAG = "StarView/Models";
    private static volatile boolean busy;
    private static volatile long lastTryMs;
    private static volatile boolean notThere;
    public static final ModelDownload INSTANCE = new ModelDownload();
    private static volatile String status = "detector model not downloaded yet";

    /* compiled from: ModelDownload.kt */
    public static final class Missing extends Exception {
        /* VF: from Vineflower (jadx: ) */
        public Missing(String var1) {
            super(var1);
            Intrinsics.checkNotNullParameter(var1, "msg");
        }
    }

    private ModelDownload() {
    }

    public final String getStatus() {
        return status;
    }

    private final File file(Context ctx) {
        File $this$file_u24lambda_u240 = new File(ctx.getFilesDir(), "det");
        $this$file_u24lambda_u240.mkdirs();
        return new File($this$file_u24lambda_u240, "yolox_s_384x640_fp16.lite");
    }

    private final File mark(Context ctx) {
        return new File(new File(ctx.getFilesDir(), "det"), "yolox_s_384x640_fp16.lite.sha256");
    }

    public final File detector(Context ctx) {
        boolean z;
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        File f = file(ctx);
        if (f.length() == 17995664) {
            try {
                z = Intrinsics.areEqual(StringsKt.trim((CharSequence) FilesKt.readText(mark(ctx), kotlin.text.Charsets.UTF_8)).toString(), "bca6acaba042dba16a5020521c707603d579f2061165dd9aa76929211744a587");
            } catch (Exception e) {
                z = false;
            }
            if (z) {
                return f;
            }
        }
        Context applicationContext = ctx.getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
        ensure(applicationContext);
        return null;
    }

    public final String importDetector(Context ctx, Uri uri) {
        Throwable th;
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(uri, "uri");
        File tmp = new File(file(ctx).getPath() + ".part");
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            long got = 0;
            InputStream openInputStream = ctx.getContentResolver().openInputStream(uri);
            if (openInputStream == null) {
                return "Couldn't open that file";
            }
            InputStream inputStream = openInputStream;
            try {
                InputStream inp = inputStream;
                int i = 0;
                FileOutputStream fileOutputStream = new FileOutputStream(tmp);
                try {
                    FileOutputStream out = fileOutputStream;
                    byte[] buf = new byte[65536];
                    while (true) {
                        int n = inp.read(buf);
                        if (n < 0) {
                            break;
                        }
                        out.write(buf, 0, n);
                        md.update(buf, 0, n);
                        got += n;
                        inp = inp;
                        i = i;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
                try {
                    Unit unit = Unit.INSTANCE;
                    CloseableKt.closeFinally(fileOutputStream, null);
                    Unit unit2 = Unit.INSTANCE;
                    CloseableKt.closeFinally(inputStream, null);
                    byte[] digest = md.digest();
                    Intrinsics.checkNotNullExpressionValue(digest, "digest(...)");
                    String sha = ArraysKt.joinToString(digest, (CharSequence) "", "", "", -1, "...", new Function1() { // from class: com.starpilot.starview.ModelDownload$$ExternalSyntheticLambda2
                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj) {
                            CharSequence importDetector$lambda$3;
                            importDetector$lambda$3 = ModelDownload.importDetector$lambda$3(((Byte) obj).byteValue());
                            return importDetector$lambda$3;
                        }
                    });
                    if (got == 17995664 && Intrinsics.areEqual(sha, "bca6acaba042dba16a5020521c707603d579f2061165dd9aa76929211744a587")) {
                        if (!tmp.renameTo(file(ctx))) {
                            return "Couldn't save it";
                        }
                        FilesKt.writeText(mark(ctx), "bca6acaba042dba16a5020521c707603d579f2061165dd9aa76929211744a587", kotlin.text.Charsets.UTF_8);
                        status = "detector model installed";
                        return null;
                    }
                    tmp.delete();
                    return "That's not yolox_s_384x640_fp16.lite (wrong size or contents)";
                } catch (Throwable th3) {
                    th = th3;
                    try {
                        throw th;
                    } catch (Throwable th4) {
                        CloseableKt.closeFinally(fileOutputStream, th);
                        throw th4;
                    }
                }
            } finally {
            }
        } catch (Exception e) {
            tmp.delete();
            String message = e.getMessage();
            if (message == null) {
                message = e.getClass().getSimpleName();
            }
            return message;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CharSequence importDetector$lambda$3(byte it) {
        String format = String.format("%02x", Arrays.copyOf(new Object[]{Byte.valueOf(it)}, 1));
        Intrinsics.checkNotNullExpressionValue(format, "format(...)");
        return format;
    }

    public final boolean installed(Context ctx) {
        boolean z;
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        if (file(ctx).length() != 17995664) {
            return false;
        }
        try {
            z = Intrinsics.areEqual(StringsKt.trim((CharSequence) FilesKt.readText(mark(ctx), kotlin.text.Charsets.UTF_8)).toString(), "bca6acaba042dba16a5020521c707603d579f2061165dd9aa76929211744a587");
        } catch (Exception e) {
            z = false;
        }
        return z;
    }

    private final void ensure(final Context ctx) {
        long now = System.currentTimeMillis();
        if (busy) {
            return;
        }
        if (now - lastTryMs < (notThere ? 600000L : 60000L)) {
            return;
        }
        busy = true;
        lastTryMs = now;
        Thread $this$ensure_u24lambda_u248 = new Thread(new Runnable() { // from class: com.starpilot.starview.ModelDownload$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ModelDownload.ensure$lambda$7(ctx);
            }
        }, "starview-model-download");
        $this$ensure_u24lambda_u248.setDaemon(true);
        $this$ensure_u24lambda_u248.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void ensure$lambda$7(Context $ctx) {
        HttpURLConnection c;
        Throwable th;
        Throwable th2;
        FileOutputStream out;
        byte[] buf;
        File tmp = new File(INSTANCE.file($ctx).getPath() + ".part");
        int i = 0;
        try {
            try {
                URLConnection openConnection = new URL("https://raw.githubusercontent.com/tsk-go/openpilot/starview-models/yolox_s_384x640_fp16.lite").openConnection();
                Intrinsics.checkNotNull(openConnection, "null cannot be cast to non-null type java.net.HttpURLConnection");
                c = (HttpURLConnection) openConnection;
                c.setConnectTimeout(15000);
                c.setReadTimeout(30000);
                notThere = c.getResponseCode() == 404;
            } catch (Exception e) {
                Log.w("StarView/Models", "detector model: " + e);
                tmp.delete();
                String message = e.getMessage();
                if (message == null) {
                    message = e.getClass().getSimpleName();
                }
                status = "detector model not installed (" + message + "): download yolox_s_384x640_fp16.lite from the chat on this tablet, then Settings › Camera & view › Detector model › Import";
            }
            if (c.getResponseCode() != 200) {
                throw new IllegalStateException(notThere ? "not online yet" : "server answered " + c.getResponseCode());
            }
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            long got = 0;
            InputStream inputStream = c.getInputStream();
            try {
                try {
                    InputStream inp = inputStream;
                    int i2 = 0;
                    FileOutputStream fileOutputStream = new FileOutputStream(tmp);
                    try {
                        out = fileOutputStream;
                        buf = new byte[65536];
                    } catch (Throwable th3) {
                        th2 = th3;
                    }
                    while (true) {
                        int n = inp.read(buf);
                        if (n < 0) {
                            break;
                        }
                        out.write(buf, i, n);
                        md.update(buf, i, n);
                        HttpURLConnection c2 = c;
                        long got2 = got + n;
                        try {
                            InputStream inp2 = inp;
                            int i3 = i2;
                            try {
                                try {
                                    status = "downloading the detector model: " + ((100 * got2) / 17995664) + "% (" + (got2 / 1048576) + " of 17 MB)";
                                    c = c2;
                                    inp = inp2;
                                    i2 = i3;
                                    got = got2;
                                    i = 0;
                                } catch (Throwable th4) {
                                    th2 = th4;
                                }
                            } catch (Throwable th5) {
                                th2 = th5;
                            }
                        } catch (Throwable th6) {
                            th2 = th6;
                        }
                        try {
                            throw th2;
                        } catch (Throwable th7) {
                            CloseableKt.closeFinally(fileOutputStream, th2);
                            throw th7;
                        }
                    }
                    try {
                        Unit unit = Unit.INSTANCE;
                        CloseableKt.closeFinally(fileOutputStream, null);
                        Unit unit2 = Unit.INSTANCE;
                        CloseableKt.closeFinally(inputStream, null);
                        byte[] digest = md.digest();
                        Intrinsics.checkNotNullExpressionValue(digest, "digest(...)");
                        String sha = ArraysKt.joinToString(digest, (CharSequence) "", "", "", -1, "...", new Function1() { // from class: com.starpilot.starview.ModelDownload$$ExternalSyntheticLambda1
                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj) {
                                CharSequence ensure$lambda$7$lambda$6;
                                ensure$lambda$7$lambda$6 = ModelDownload.ensure$lambda$7$lambda$6(((Byte) obj).byteValue());
                                return ensure$lambda$7$lambda$6;
                            }
                        });
                        if (got != 17995664 || !Intrinsics.areEqual(sha, "bca6acaba042dba16a5020521c707603d579f2061165dd9aa76929211744a587")) {
                            throw new IllegalStateException("download damaged (" + got + " bytes) - trying again in a minute");
                        }
                        if (!tmp.renameTo(INSTANCE.file($ctx))) {
                            throw new IllegalStateException("couldn't save it");
                        }
                        FilesKt.writeText(INSTANCE.mark($ctx), "bca6acaba042dba16a5020521c707603d579f2061165dd9aa76929211744a587", kotlin.text.Charsets.UTF_8);
                        status = "detector model downloaded";
                    } catch (Throwable th8) {
                        th2 = th8;
                    }
                } catch (Throwable th9) {
                    th = th9;
                    try {
                        throw th;
                    } catch (Throwable th10) {
                        CloseableKt.closeFinally(inputStream, th);
                        throw th10;
                    }
                }
            } catch (Throwable th11) {
                th = th11;
                throw th;
            }
        } finally {
            busy = false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CharSequence ensure$lambda$7$lambda$6(byte it) {
        String format = String.format("%02x", Arrays.copyOf(new Object[]{Byte.valueOf(it)}, 1));
        Intrinsics.checkNotNullExpressionValue(format, "format(...)");
        return format;
    }
}
