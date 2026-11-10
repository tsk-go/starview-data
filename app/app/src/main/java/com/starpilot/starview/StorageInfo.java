package com.starpilot.starview;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.json.JSONObject;

/* compiled from: StorageInfo.kt */
public final class StorageInfo {
    public static final StorageInfo INSTANCE = new StorageInfo();

    /* renamed from: http$delegate, reason: from kotlin metadata */
    private static final Lazy http = LazyKt.lazy(new Function0() { // from class: com.starpilot.starview.StorageInfo$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            OkHttpClient http_delegate$lambda$0;
            http_delegate$lambda$0 = StorageInfo.http_delegate$lambda$0();
            return http_delegate$lambda$0;
        }
    });

    private StorageInfo() {
    }

    private final OkHttpClient getHttp() {
        return (OkHttpClient) http.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final OkHttpClient http_delegate$lambda$0() {
        return new OkHttpClient.Builder().addInterceptor(Link.INSTANCE.getAuthInterceptor()).connectTimeout(3L, TimeUnit.SECONDS).readTimeout(5L, TimeUnit.SECONDS).build();
    }

    public static /* synthetic */ JSONObject fetch$default(StorageInfo storageInfo, String str, int i, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = 8090;
        }
        return storageInfo.fetch(str, i);
    }

    public final JSONObject fetch(String host, int port) {
        JSONObject jSONObject;
        Intrinsics.checkNotNullParameter(host, "host");
        try {
            Response execute = getHttp().newCall(new Request.Builder().url("http://" + host + ":" + port + "/storage").build()).execute();
            try {
                Response r = execute;
                if (r.isSuccessful()) {
                    ResponseBody body = r.body();
                    Intrinsics.checkNotNull(body);
                    jSONObject = new JSONObject(body.string());
                } else {
                    jSONObject = null;
                }
                CloseableKt.closeFinally(execute, null);
                return jSONObject;
            } finally {
            }
        } catch (Exception e) {
            return null;
        }
    }

    public final String gb(JSONObject o, String k) {
        Intrinsics.checkNotNullParameter(o, "o");
        Intrinsics.checkNotNullParameter(k, "k");
        double v = o.optDouble(k, Double.NaN);
        if (Double.isNaN(v)) {
            return "?";
        }
        if (v >= 100.0d) {
            return String.valueOf(MathKt.roundToInt(v));
        }
        String format = String.format("%.1f", Arrays.copyOf(new Object[]{Double.valueOf(v)}, 1));
        Intrinsics.checkNotNullExpressionValue(format, "format(...)");
        return format;
    }

    public final String whenStr(double epoch) {
        StringBuilder sb;
        String str;
        if (Double.isNaN(epoch) || epoch <= 0.0d) {
            return "–";
        }
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis((long) (1000 * epoch));
        Calendar now = Calendar.getInstance();
        String hm = String.format("%02d:%02d", Arrays.copyOf(new Object[]{Integer.valueOf(c.get(11)), Integer.valueOf(c.get(12))}, 2));
        Intrinsics.checkNotNullExpressionValue(hm, "format(...)");
        int dayDiff = (((now.get(1) - c.get(1)) * 366) + now.get(6)) - c.get(6);
        switch (dayDiff) {
            case 0:
                sb = new StringBuilder();
                str = "today ";
                break;
            case 1:
                sb = new StringBuilder();
                str = "yesterday ";
                break;
            default:
                String format = new SimpleDateFormat("MMM d HH:mm", Locale.US).format(c.getTime());
                Intrinsics.checkNotNullExpressionValue(format, "format(...)");
                return format;
        }
        return sb.append(str).append(hm).toString();
    }

    /* compiled from: StorageInfo.kt */
    public static final class Copying {
        private final String line;
        private final Integer pct;
        private final String phase;

        public Copying(String phase, Integer pct, String line) {
            Intrinsics.checkNotNullParameter(phase, "phase");
            Intrinsics.checkNotNullParameter(line, "line");
            this.phase = phase;
            this.pct = pct;
            this.line = line;
        }

        public final String getLine() {
            return this.line;
        }

        public final Integer getPct() {
            return this.pct;
        }

        public final String getPhase() {
            return this.phase;
        }
    }

    public final Copying copying(JSONObject j) {
        JSONObject optJSONObject;
        JSONObject p;
        String str;
        if (j == null || (optJSONObject = j.optJSONObject("stick")) == null || (p = optJSONObject.optJSONObject("progress")) == null) {
            return null;
        }
        String phase = p.optString("phase", "copying");
        if (!Intrinsics.areEqual(phase, "copying")) {
            Intrinsics.checkNotNull(phase);
            if (phase.length() > 0) {
                StringBuilder sb = new StringBuilder();
                char it = phase.charAt(0);
                String valueOf = String.valueOf(it);
                Intrinsics.checkNotNull(valueOf, "null cannot be cast to non-null type java.lang.String");
                String upperCase = valueOf.toUpperCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue(upperCase, "toUpperCase(...)");
                StringBuilder append = sb.append((Object) upperCase);
                String substring = phase.substring(1);
                Intrinsics.checkNotNullExpressionValue(substring, "substring(...)");
                str = append.append(substring).toString();
            } else {
                str = phase;
            }
            return new Copying(phase, null, str + "…");
        }
        long tot = p.optLong("total_bytes");
        long done = p.optLong("done_bytes");
        if (tot <= 0) {
            return new Copying(phase, 100, "Nothing new to copy");
        }
        int pct = RangesKt.coerceIn((int) ((100 * done) / tot), 0, 100);
        double eta = p.optDouble("eta_s", Double.NaN);
        double rate = p.optDouble("rate_MBps", Double.NaN);
        Integer valueOf2 = Integer.valueOf(pct);
        StringBuilder $this$copying_u24lambda_u244 = new StringBuilder();
        $this$copying_u24lambda_u244.append(pct + "%  ·  " + INSTANCE.size(done) + " of " + INSTANCE.size(tot) + "  ·  " + p.optInt("done_files") + " of " + p.optInt("total_files") + " files");
        if (!Double.isNaN(rate) && rate > 0.0d) {
            String format = String.format("%.0f", Arrays.copyOf(new Object[]{Double.valueOf(rate)}, 1));
            Intrinsics.checkNotNullExpressionValue(format, "format(...)");
            $this$copying_u24lambda_u244.append("  ·  " + format + " MB/s");
        }
        if (!Double.isNaN(eta) && eta > 0.0d) {
            $this$copying_u24lambda_u244.append("  ·  " + INSTANCE.dur(eta) + " left");
        }
        Unit unit = Unit.INSTANCE;
        String sb2 = $this$copying_u24lambda_u244.toString();
        Intrinsics.checkNotNullExpressionValue(sb2, "toString(...)");
        return new Copying(phase, valueOf2, sb2);
    }

    public final String size(long b) {
        if (b < 1000000000) {
            return (b / 1000000) + " MB";
        }
        String format = String.format("%.1f GB", Arrays.copyOf(new Object[]{Double.valueOf(b / 1.0E9d)}, 1));
        Intrinsics.checkNotNullExpressionValue(format, "format(...)");
        return format;
    }

    public final String mb(int m) {
        return size(m * 1000000);
    }

    public final String runLine(String out) {
        Intrinsics.checkNotNullParameter(out, "out");
        if (!StringsKt.startsWith$default(StringsKt.trimStart((CharSequence) out).toString(), "{", false, 2, (Object) null)) {
            return out;
        }
        try {
            JSONObject o = new JSONObject(out);
            String optString = o.optString("result");
            if (optString.length() == 0) {
                optString = "done";
            }
            String it = optString;
            if (Intrinsics.areEqual(it, "ok")) {
                it = "finished";
            }
            if (!o.has("copied_MB")) {
                return it;
            }
            return it + "  ·  " + mb(o.optInt("copied_MB")) + " copied, " + o.optInt("files") + " files";
        } catch (Exception e) {
            return StringsKt.take(out, 80);
        }
    }

    public final String dur(double s) {
        StringBuilder append;
        int m = MathKt.roundToInt(s / 60);
        if (m < 1) {
            return "under a minute";
        }
        if (m < 60) {
            append = new StringBuilder().append("about ").append(m);
        } else {
            append = new StringBuilder().append("about ").append(m / 60).append(" h ").append(m % 60);
        }
        return append.append(" min").toString();
    }

    public final List<Pair<String, String>> rows(JSONObject j) {
        String str;
        if (j == null) {
            return CollectionsKt.listOf(TuplesKt.to("Storage", "comma not reachable (or starviewd too old: install the latest starviewd.py)"));
        }
        ArrayList out = new ArrayList();
        JSONObject inn = j.optJSONObject("internal");
        if (inn != null) {
            out.add(TuplesKt.to("Internal storage", gb(inn, "free_GB") + " GB free of " + gb(inn, "total_GB") + " GB  ·  openpilot records here"));
            out.add(TuplesKt.to("Drives on the comma", inn.optInt("segments") + " segments  ·  newest " + whenStr(inn.optDouble("newest_time")) + "  ·  oldest " + whenStr(inn.optDouble("oldest_time"))));
        }
        JSONObject s = j.optJSONObject("stick");
        if (s == null || !s.optBoolean("present")) {
            out.add(TuplesKt.to("USB stick", "not connected"));
            return out;
        }
        out.add(TuplesKt.to("USB stick", s.has("stick_free_GB") ? gb(s, "stick_free_GB") + " GB free of " + gb(s, "stick_size_GB") + " GB  ·  archive copy, only used while parked" : "connected, no copy finished yet"));
        Copying it = copying(j);
        if (it != null) {
            out.add(TuplesKt.to("Copying to the stick now", it.getLine()));
        }
        double lc = s.optDouble("last_copy_time", Double.NaN);
        String str2 = "–";
        out.add(TuplesKt.to("Last copy to the stick", Double.isNaN(lc) ? "–" : whenStr(lc) + "  ·  " + mb(s.optInt("last_copy_MB"))));
        if (s.has("stick_segments")) {
            out.add(TuplesKt.to("Drives on the stick", s.optInt("stick_segments") + " segments  ·  newest " + whenStr(s.optDouble("stick_newest_time")) + "  ·  oldest " + whenStr(s.optDouble("stick_oldest_time"))));
        }
        double run = s.optDouble("run_time", Double.NaN);
        String res = s.optString("result");
        ArrayList arrayList = out;
        String optString = Double.isNaN(run) ? s.has("time") ? s.optString("time") : "never" : whenStr(run);
        String str3 = res;
        if (!(str3.length() == 0)) {
            str2 = str3;
        }
        arrayList.add(TuplesKt.to("Last check", optString + "  ·  " + ((Object) str2) + (!s.optBoolean("auto", true) ? "  ·  auto copy OFF" : "  ·  auto copy every 5 min while parked")));
        JSONObject a = s.optJSONObject("auto_last");
        if (a != null) {
            double t = a.optDouble("time", Double.NaN);
            if (a.optBoolean("running")) {
                str = "running now";
            } else if (a.isNull("rc")) {
                StorageInfo storageInfo = INSTANCE;
                String optString2 = a.optString("out");
                Intrinsics.checkNotNullExpressionValue(optString2, "optString(...)");
                String runLine = storageInfo.runLine(optString2);
                if (runLine.length() == 0) {
                    runLine = "stopped";
                }
                str = runLine;
            } else {
                StorageInfo storageInfo2 = INSTANCE;
                String optString3 = a.optString("out");
                Intrinsics.checkNotNullExpressionValue(optString3, "optString(...)");
                String runLine2 = storageInfo2.runLine(optString3);
                if (runLine2.length() == 0) {
                    runLine2 = "exit " + a.optInt("rc");
                }
                str = runLine2;
            }
            String what = str;
            out.add(TuplesKt.to("Last automatic copy", INSTANCE.whenStr(t) + "  ·  " + what));
        }
        return out;
    }
}
