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

/* compiled from: Ideas.kt */
public final class MapBumps {
    private static File dir;
    public static final MapBumps INSTANCE = new MapBumps();
    private static final ConcurrentHashMap<String, Boolean> done = new ConcurrentHashMap<>();
    private static final ExecutorService pool = Executors.newSingleThreadExecutor();

    /* renamed from: http$delegate, reason: from kotlin metadata */
    private static final Lazy http = LazyKt.lazy(new Function0() { // from class: com.starpilot.starview.MapBumps$$ExternalSyntheticLambda1
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            OkHttpClient http_delegate$lambda$0;
            http_delegate$lambda$0 = MapBumps.http_delegate$lambda$0();
            return http_delegate$lambda$0;
        }
    });

    private MapBumps() {
    }

    private final OkHttpClient getHttp() {
        return (OkHttpClient) http.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final OkHttpClient http_delegate$lambda$0() {
        return new OkHttpClient.Builder().callTimeout(40L, TimeUnit.SECONDS).build();
    }

    public final void init(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        File $this$init_u24lambda_u241 = new File(ctx.getFilesDir(), "map_bumps");
        $this$init_u24lambda_u241.mkdirs();
        dir = $this$init_u24lambda_u241;
    }

    public final void want(double lat, double lon) {
        final int ty = (int) Math.floor(lat / 0.01d);
        final int tx = (int) Math.floor(lon / 0.01d);
        final String k = ty + "_" + tx;
        if (done.putIfAbsent(k, true) != null) {
            return;
        }
        pool.execute(new Runnable() { // from class: com.starpilot.starview.MapBumps$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                MapBumps.want$lambda$2(k, ty, tx);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void want$lambda$2(String $k, int $ty, int $tx) {
        INSTANCE.load($k, $ty * 0.01d, $tx * 0.01d);
    }

    private final void load(String k, double s, double w) {
        String b;
        String str;
        try {
            File file = dir;
            if (file == null) {
                return;
            }
            File f = new File(file, k + ".json");
            if (f.isFile()) {
                b = FilesKt.readText$default(f, null, 1, null);
            } else {
                StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
                String format = String.format(Locale.US, "%.4f,%.4f,%.4f,%.4f", Arrays.copyOf(new Object[]{Double.valueOf(s), Double.valueOf(w), Double.valueOf(s + 0.01d), Double.valueOf(w + 0.01d)}, 4));
                Intrinsics.checkNotNullExpressionValue(format, "format(...)");
                String q = "[out:json][timeout:25];node[\"traffic_calming\"~\"^(bump|hump|table|cushion|yes)$\"](" + format + ");out;";
                Request req = new Request.Builder().url("https://overpass-api.de/api/interpreter").header("User-Agent", "StarView (openpilot tablet display)").post(new FormBody.Builder(null, 1, null).add("data", q).build()).build();
                Response execute = getHttp().newCall(req).execute();
                try {
                    Response r = execute;
                    if (!r.isSuccessful()) {
                        throw new IOException("HTTP " + r.code());
                    }
                    ResponseBody body = r.body();
                    Intrinsics.checkNotNull(body);
                    String string = body.string();
                    CloseableKt.closeFinally(execute, null);
                    b = string;
                    FilesKt.writeText$default(f, b, null, 2, null);
                } finally {
                }
            }
            JSONArray els = new JSONObject(b).optJSONArray("elements");
            if (els == null) {
                return;
            }
            int length = els.length();
            for (int i = 0; i < length; i++) {
                JSONObject o = els.getJSONObject(i);
                IdeaSpots ideaSpots = IdeaSpots.INSTANCE;
                double d = o.getDouble("lat");
                double d2 = o.getDouble("lon");
                JSONObject optJSONObject = o.optJSONObject("tags");
                if (optJSONObject == null || (str = optJSONObject.optString("traffic_calming")) == null) {
                    str = "";
                }
                ideaSpots.record("map_bump", d, d2, Double.NaN, (r35 & 16) != 0 ? Double.NaN : 0.0d, (r35 & 32) != 0 ? "" : str, (r35 & 64) != 0 ? System.currentTimeMillis() : 0L, (r35 & 128) != 0 ? 10.0d : 3.0d, (r35 & 256) != 0 ? "me" : null);
            }
        } catch (Exception e) {
            done.remove(k);
        }
    }
}
