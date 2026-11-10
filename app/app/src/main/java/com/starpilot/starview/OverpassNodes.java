package com.starpilot.starview;

import android.content.Context;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: IdeasMore.kt */
public class OverpassNodes {
    private File dir;
    private final String dirName;
    private final ConcurrentHashMap<String, Boolean> done;

    /* renamed from: http$delegate, reason: from kotlin metadata */
    private final Lazy http$delegate;
    private final String kind;
    private final ExecutorService pool;
    private final String query;

    public OverpassNodes(String dirName, String query, String kind) {
        Intrinsics.checkNotNullParameter(dirName, "dirName");
        Intrinsics.checkNotNullParameter(query, "query");
        Intrinsics.checkNotNullParameter(kind, "kind");
        this.dirName = dirName;
        this.query = query;
        this.kind = kind;
        this.done = new ConcurrentHashMap<>();
        this.pool = Executors.newSingleThreadExecutor();
        this.http$delegate = LazyKt.lazy(new Function0() { // from class: com.starpilot.starview.OverpassNodes$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                OkHttpClient http_delegate$lambda$0;
                http_delegate$lambda$0 = OverpassNodes.http_delegate$lambda$0();
                return http_delegate$lambda$0;
            }
        });
    }

    private final OkHttpClient getHttp() {
        return (OkHttpClient) this.http$delegate.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final OkHttpClient http_delegate$lambda$0() {
        return new OkHttpClient.Builder().callTimeout(40L, TimeUnit.SECONDS).build();
    }

    public final void init(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        File $this$init_u24lambda_u241 = new File(ctx.getFilesDir(), this.dirName);
        $this$init_u24lambda_u241.mkdirs();
        this.dir = $this$init_u24lambda_u241;
    }

    public final void want(double lat, double lon) {
        final int ty = (int) Math.floor(lat / 0.01d);
        final int tx = (int) Math.floor(lon / 0.01d);
        final String k = ty + "_" + tx;
        if (this.done.putIfAbsent(k, true) != null) {
            return;
        }
        this.pool.execute(new Runnable() { // from class: com.starpilot.starview.OverpassNodes$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                OverpassNodes.want$lambda$4(OverpassNodes.this, k, ty, tx);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static final void want$lambda$4(OverpassNodes overpassNodes, String str, int i, int i2) {
        String str2;
        try {
            File file = overpassNodes.dir;
            if (file == null) {
                return;
            }
            File file2 = new File(file, str + ".json");
            if (file2.isFile()) {
                str2 = FilesKt.readText(file2, kotlin.text.Charsets.UTF_8);
            } else {
                String str3 = overpassNodes.query;
                StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
                String format = String.format(Locale.US, "%.4f,%.4f,%.4f,%.4f", Arrays.copyOf(new Object[]{Double.valueOf(i * 0.01d), Double.valueOf(i2 * 0.01d), Double.valueOf((i * 0.01d) + 0.01d), Double.valueOf((i2 * 0.01d) + 0.01d)}, 4));
                Intrinsics.checkNotNullExpressionValue(format, "format(...)");
                Response execute = overpassNodes.getHttp().newCall(new Request.Builder().url("https://overpass-api.de/api/interpreter").header("User-Agent", "StarView (openpilot tablet display)").post(new FormBody.Builder().add("data", "[out:json][timeout:25];" + str3 + "(" + format + ");out;").build()).build()).execute();
                try {
                    Response response = execute;
                    if (!response.isSuccessful()) {
                        throw new IOException("HTTP " + response.code());
                    }
                    ResponseBody body = response.body();
                    Intrinsics.checkNotNull(body);
                    String string = body.string();
                    CloseableKt.closeFinally(execute, null);
                    FilesKt.writeText(file2, string, kotlin.text.Charsets.UTF_8);
                    str2 = string;
                } finally {
                }
            }
            JSONArray optJSONArray = new JSONObject(str2).optJSONArray("elements");
            if (optJSONArray == null) {
                return;
            }
            int length = optJSONArray.length();
            for (int i3 = 0; i3 < length; i3++) {
                JSONObject jSONObject = optJSONArray.getJSONObject(i3);
                IdeaSpots.INSTANCE.record(overpassNodes.kind, jSONObject.getDouble("lat"), jSONObject.getDouble("lon"), Double.NaN, (r35 & 16) != 0 ? Double.NaN : 0.0d, (r35 & 32) != 0 ? "" : null, (r35 & 64) != 0 ? System.currentTimeMillis() : 0L, (r35 & 128) != 0 ? 10.0d : 3.0d, (r35 & 256) != 0 ? "me" : "osm");
            }
        } catch (Exception e) {
            overpassNodes.done.remove(str);
        }
    }
}
