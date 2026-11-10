package com.starpilot.starview;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: IdeasMore.kt */
public final class City311 {
    public static final City311 INSTANCE = new City311();
    private static final ConcurrentHashMap<String, Boolean> done = new ConcurrentHashMap<>();
    private static final ExecutorService pool = Executors.newSingleThreadExecutor();

    /* renamed from: http$delegate, reason: from kotlin metadata */
    private static final Lazy http = LazyKt.lazy(new Function0() { // from class: com.starpilot.starview.City311$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            OkHttpClient http_delegate$lambda$0;
            http_delegate$lambda$0 = City311.http_delegate$lambda$0();
            return http_delegate$lambda$0;
        }
    });

    private City311() {
    }

    private final OkHttpClient getHttp() {
        return (OkHttpClient) http.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final OkHttpClient http_delegate$lambda$0() {
        return new OkHttpClient.Builder().callTimeout(30L, TimeUnit.SECONDS).build();
    }

    public final void want(double lat, double lon) {
        boolean z = false;
        if (40.49d <= lat && lat <= 40.92d) {
            if (-74.27d <= lon && lon <= -73.68d) {
                z = true;
            }
            if (!z) {
                return;
            }
            final int ty = (int) Math.floor(lat / 0.01d);
            final int tx = (int) Math.floor(lon / 0.01d);
            final String k = ty + "_" + tx;
            if (done.putIfAbsent(k, true) != null) {
                return;
            }
            pool.execute(new Runnable() { // from class: com.starpilot.starview.City311$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    City311.want$lambda$2(ty, tx, k);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void want$lambda$2(int $ty, int $tx, String $k) {
        String since;
        double s;
        double w;
        try {
            since = new SimpleDateFormat("yyyy-MM-dd'T'00:00:00", Locale.US).format(new Date(System.currentTimeMillis() - 3888000000L));
            s = $ty * 0.01d;
            w = $tx * 0.01d;
        } catch (Exception e) {
        }
        try {
            String where = "descriptor='Pothole' AND status='Open' AND created_date>'" + since + "' AND latitude between " + s + " and " + (s + 0.01d) + " AND longitude between " + w + " and " + (0.01d + w);
            HttpUrl url = new HttpUrl.Builder().scheme("https").host("data.cityofnewyork.us").addPathSegments("resource/erm2-nwe9.json").addQueryParameter("$select", "latitude,longitude").addQueryParameter("$where", where).addQueryParameter("$limit", "500").build();
            Response execute = INSTANCE.getHttp().newCall(new Request.Builder().url(url).build()).execute();
            try {
                Response r = execute;
                if (!r.isSuccessful()) {
                    throw new IOException("HTTP " + r.code());
                }
                ResponseBody body = r.body();
                Intrinsics.checkNotNull(body);
                String body2 = body.string();
                CloseableKt.closeFinally(execute, null);
                JSONArray a = new JSONArray(body2);
                int length = a.length();
                for (int i = 0; i < length; i++) {
                    JSONObject o = a.getJSONObject(i);
                    double la = o.optDouble("latitude");
                    double lo = o.optDouble("longitude");
                    if (!Double.isNaN(la) && !Double.isNaN(lo)) {
                        IdeaSpots.INSTANCE.record("pothole", la, lo, Double.NaN, (r35 & 16) != 0 ? Double.NaN : 0.0d, (r35 & 32) != 0 ? "" : "NYC 311", (r35 & 64) != 0 ? System.currentTimeMillis() : 0L, (r35 & 128) != 0 ? 10.0d : 8.0d, (r35 & 256) != 0 ? "me" : "nyc311");
                    }
                }
            } finally {
            }
        } catch (Exception e2) {
            done.remove($k);
        }
    }
}
