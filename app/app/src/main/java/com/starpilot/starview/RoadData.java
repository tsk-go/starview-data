package com.starpilot.starview;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.Uri;
import com.starpilot.starview.Hpms;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.collections.MapsKt;
import kotlin.io.ByteStreamsKt;
import kotlin.io.CloseableKt;
import kotlin.io.TextStreamsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.ranges.RangesKt;
import kotlin.text.Charsets;
import kotlin.text.MatchResult;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: RoadData.kt */
public final class RoadData {
    public static final String DEFAULT_BASE = "https://raw.githubusercontent.com/tsk-go/starview-data/main/hpms-2024/";
    private static volatile boolean busy;
    private static volatile int done;
    private static volatile boolean stop;
    private static volatile int total;
    public static final RoadData INSTANCE = new RoadData();
    private static volatile String status = "";
    private static final Map<Integer, String> STATES = MapsKt.mapOf(TuplesKt.to(1, "AL"), TuplesKt.to(2, "AK"), TuplesKt.to(4, "AZ"), TuplesKt.to(5, "AR"), TuplesKt.to(6, "CA"), TuplesKt.to(8, "CO"), TuplesKt.to(9, "CT"), TuplesKt.to(10, "DE"), TuplesKt.to(11, "DC"), TuplesKt.to(12, "FL"), TuplesKt.to(13, "GA"), TuplesKt.to(15, "HI"), TuplesKt.to(16, "ID"), TuplesKt.to(17, "IL"), TuplesKt.to(18, "IN"), TuplesKt.to(19, "IA"), TuplesKt.to(20, "KS"), TuplesKt.to(21, "KY"), TuplesKt.to(22, "LA"), TuplesKt.to(23, "ME"), TuplesKt.to(24, "MD"), TuplesKt.to(25, "MA"), TuplesKt.to(26, "MI"), TuplesKt.to(27, "MN"), TuplesKt.to(28, "MS"), TuplesKt.to(29, "MO"), TuplesKt.to(30, "MT"), TuplesKt.to(31, "NE"), TuplesKt.to(32, "NV"), TuplesKt.to(33, "NH"), TuplesKt.to(34, "NJ"), TuplesKt.to(35, "NM"), TuplesKt.to(36, "NY"), TuplesKt.to(37, "NC"), TuplesKt.to(38, "ND"), TuplesKt.to(39, "OH"), TuplesKt.to(40, "OK"), TuplesKt.to(41, "OR"), TuplesKt.to(42, "PA"), TuplesKt.to(44, "RI"), TuplesKt.to(45, "SC"), TuplesKt.to(46, "SD"), TuplesKt.to(47, "TN"), TuplesKt.to(48, "TX"), TuplesKt.to(49, "UT"), TuplesKt.to(50, "VT"), TuplesKt.to(51, "VA"), TuplesKt.to(53, "WA"), TuplesKt.to(54, "WV"), TuplesKt.to(55, "WI"), TuplesKt.to(56, "WY"), TuplesKt.to(72, "PR"));

    private RoadData() {
    }

    /* compiled from: RoadData.kt */
    public static final class Item {
        private final long bytes;
        private final String file;
        private final String sha;
        private final String st;

        public Item(String st, String file, long bytes, String sha) {
            Intrinsics.checkNotNullParameter(st, "st");
            Intrinsics.checkNotNullParameter(file, "file");
            Intrinsics.checkNotNullParameter(sha, "sha");
            this.st = st;
            this.file = file;
            this.bytes = bytes;
            this.sha = sha;
        }

        public final long getBytes() {
            return this.bytes;
        }

        public final String getFile() {
            return this.file;
        }

        public final String getSha() {
            return this.sha;
        }

        public final String getSt() {
            return this.st;
        }
    }

    public final boolean getBusy() {
        return busy;
    }

    public final String getStatus() {
        return status;
    }

    public final int getDone() {
        return done;
    }

    public final int getTotal() {
        return total;
    }

    public final String base(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        String string = ctx.getSharedPreferences("road_data", 0).getString("base", "https://raw.githubusercontent.com/tsk-go/starview-data/main/hpms-2024/");
        return string == null ? "https://raw.githubusercontent.com/tsk-go/starview-data/main/hpms-2024/" : string;
    }

    public final void setBase(Context ctx, String b) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(b, "b");
        SharedPreferences.Editor edit = ctx.getSharedPreferences("road_data", 0).edit();
        String it = StringsKt.trim((CharSequence) b).toString();
        if (!StringsKt.endsWith(it, "/", false)) {
            it = it + "/";
        }
        edit.putString("base", it).apply();
    }

    public final boolean onWifi(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Object systemService = ctx.getSystemService("connectivity");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.net.ConnectivityManager");
        ConnectivityManager cm = (ConnectivityManager) systemService;
        NetworkCapabilities c = cm.getNetworkCapabilities(cm.getActiveNetwork());
        return c != null && c.hasCapability(11) && c.hasTransport(1);
    }

    public final void cancel() {
        stop = true;
    }

    private final String sha256(File f) {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        FileInputStream fileInputStream = new FileInputStream(f);
        Throwable fileInputStream$c = null;
        try {
            FileInputStream i = fileInputStream;
            byte[] b = new byte[65536];
            while (true) {
                int n = i.read(b);
                if (n < 0) {
                    Unit unit = Unit.INSTANCE;
                    byte[] digest = md.digest();
                    Intrinsics.checkNotNullExpressionValue(digest, "digest(...)");
                    return ArraysKt.joinToString(digest, (CharSequence) "", "", "", -1, "...", new Function1() { // from class: com.starpilot.starview.RoadData$$ExternalSyntheticLambda0
                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj) {
                            CharSequence sha256$lambda$2;
                            sha256$lambda$2 = RoadData.sha256$lambda$2(((Byte) obj).byteValue());
                            return sha256$lambda$2;
                        }
                    });
                }
                md.update(b, 0, n);
            }
        } catch (Throwable fileInputStream$t) {
            fileInputStream$c = fileInputStream$t;
            throw fileInputStream$t;
        } finally {
            CloseableKt.closeFinally(fileInputStream, fileInputStream$c);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CharSequence sha256$lambda$2(byte it) {
        String format = String.format("%02x", Arrays.copyOf(new Object[]{Byte.valueOf(it)}, 1));
        Intrinsics.checkNotNullExpressionValue(format, "format(...)");
        return format;
    }

    private final HttpURLConnection get(String url) {
        URLConnection openConnection = new URL(url).openConnection();
        Intrinsics.checkNotNull(openConnection, "null cannot be cast to non-null type java.net.HttpURLConnection");
        HttpURLConnection $this$get_u24lambda_u243 = (HttpURLConnection) openConnection;
        $this$get_u24lambda_u243.setConnectTimeout(20000);
        $this$get_u24lambda_u243.setReadTimeout(60000);
        $this$get_u24lambda_u243.setInstanceFollowRedirects(true);
        $this$get_u24lambda_u243.setRequestProperty("User-Agent", "StarView");
        return $this$get_u24lambda_u243;
    }

    public final List<Item> manifest(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        HttpURLConnection c = get(base(ctx) + "manifest.json");
        if (c.getResponseCode() != 200) {
            throw new IllegalStateException("manifest: HTTP " + c.getResponseCode());
        }
        InputStream inputStream = c.getInputStream();
        Intrinsics.checkNotNullExpressionValue(inputStream, "getInputStream(...)");
        Reader inputStreamReader = new InputStreamReader(inputStream, Charsets.UTF_8);
        JSONObject j = new JSONObject(TextStreamsKt.readText(inputStreamReader instanceof BufferedReader ? (BufferedReader) inputStreamReader : new BufferedReader(inputStreamReader, 8192)));
        JSONArray a = j.getJSONArray("files");
        Iterable $this$map$iv = RangesKt.until(0, a.length());
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        Iterator<Integer> it = $this$map$iv.iterator();
        while (it.hasNext()) {
            int item$iv$iv = ((IntIterator) it).nextInt();
            destination$iv$iv.add(a.getJSONObject(item$iv$iv));
        }
        Iterable $this$map$iv2 = (List) destination$iv$iv;
        Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv2, 10));
        for (Object item$iv$iv2 : $this$map$iv2) {
            JSONObject it2 = (JSONObject) item$iv$iv2;
            String string = it2.getString("state");
            Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
            String string2 = it2.getString("file");
            Intrinsics.checkNotNullExpressionValue(string2, "getString(...)");
            JSONObject j2 = j;
            long j3 = it2.getLong("bytes");
            String string3 = it2.getString("sha256");
            Intrinsics.checkNotNullExpressionValue(string3, "getString(...)");
            destination$iv$iv2.add(new Item(string, string2, j3, string3));
            j = j2;
            a = a;
        }
        return (List) destination$iv$iv2;
    }

    public final void download(final Context ctx, final Function0<Unit> onChange) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(onChange, "onChange");
        if (busy) {
            return;
        }
        busy = true;
        stop = false;
        done = 0;
        total = 0;
        status = "Asking for the list…";
        onChange.invoke();
        new Thread(new Runnable() { // from class: com.starpilot.starview.RoadData$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                RoadData.download$lambda$10(ctx, onChange);
            }
        }).start();
    }

    public static final void download$lambda$10(Context $ctx, Function0 $onChange) {
        try {
            if (!INSTANCE.onWifi($ctx)) {
                status = "Not on Wi-Fi - the road data only downloads on Wi-Fi.";
                return;
            }
            List<Item> items = INSTANCE.manifest($ctx);
            File dir = Hpms.INSTANCE.getDir();
            if (dir == null) {
                dir = new File($ctx.getFilesDir(), "hpms");
                dir.mkdirs();
            }
            List<Item> todo = new ArrayList<>();
            for (Item it : items) {
                File f = new File(dir, it.getFile());
                if (!(f.exists() && f.length() == it.getBytes())) {
                    todo.add(it);
                }
            }
            total = todo.size();
            if (todo.isEmpty()) {
                status = "Up to date: " + items.size() + " states.";
                return;
            }
            for (Item it : todo) {
                if (stop) {
                    status = "Stopped.";
                    return;
                }
                if (!INSTANCE.onWifi($ctx)) {
                    status = "Wi-Fi lost - stopped (Download again to continue).";
                    return;
                }
                status = "Downloading " + it.getSt() + " (" + INSTANCE.mb(it.getBytes()) + ")…  " + (done + 1) + " of " + total;
                $onChange.invoke();
                File part = new File(dir, it.getFile() + ".part");
                HttpURLConnection c = INSTANCE.get(INSTANCE.base($ctx) + it.getFile());
                if (c.getResponseCode() != 200) {
                    throw new IllegalStateException(it.getFile() + ": HTTP " + c.getResponseCode());
                }
                InputStream in = c.getInputStream();
                Throwable inCause = null;
                try {
                    FileOutputStream o = new FileOutputStream(part);
                    Throwable oCause = null;
                    try {
                        byte[] b = new byte[65536];
                        while (!stop) {
                            int n = in.read(b);
                            if (n < 0) {
                                break;
                            }
                            o.write(b, 0, n);
                        }
                    } catch (Throwable th) {
                        oCause = th;
                        throw th;
                    } finally {
                        CloseableKt.closeFinally(o, oCause);
                    }
                } catch (Throwable th) {
                    inCause = th;
                    throw th;
                } finally {
                    CloseableKt.closeFinally(in, inCause);
                }
                if (stop) {
                    part.delete();
                    status = "Stopped.";
                    return;
                }
                if (!Intrinsics.areEqual(INSTANCE.sha256(part), it.getSha())) {
                    part.delete();
                    throw new IllegalStateException(it.getFile() + ": download damaged (checksum)");
                }
                part.renameTo(new File(dir, it.getFile()));
                done++;
                Hpms.INSTANCE.reload();
                $onChange.invoke();
            }
            status = "Done: " + items.size() + " states.";
        } catch (Exception e) {
            status = "Couldn't download: " + e.getMessage();
        } finally {
            busy = false;
            $onChange.invoke();
        }
    }

    /* renamed from: import, reason: not valid java name */
    public final String importFile(Context ctx, Uri uri) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(uri, "uri");
        try {
            Cursor query = ctx.getContentResolver().query(uri, null, null, null, null);
            String name = null;
            if (query != null) {
                Throwable qCause = null;
                try {
                    name = query.moveToFirst() ? query.getString(query.getColumnIndexOrThrow("_display_name")) : null;
                } catch (Throwable th) {
                    qCause = th;
                    throw th;
                } finally {
                    CloseableKt.closeFinally(query, qCause);
                }
            }
            if (name == null) {
                name = "";
            }
            File dir = Hpms.INSTANCE.getDir();
            if (dir == null) {
                dir = new File(ctx.getFilesDir(), "hpms");
                dir.mkdirs();
            }
            File tmp = new File(dir, "import.part");
            InputStream in = ctx.getContentResolver().openInputStream(uri);
            Intrinsics.checkNotNull(in);
            Throwable inCause = null;
            try {
                FileOutputStream o = new FileOutputStream(tmp);
                Throwable oCause = null;
                try {
                    ByteStreamsKt.copyTo(in, o, 8192);
                } catch (Throwable th) {
                    oCause = th;
                    throw th;
                } finally {
                    CloseableKt.closeFinally(o, oCause);
                }
            } catch (Throwable th) {
                inCause = th;
                throw th;
            } finally {
                CloseableKt.closeFinally(in, inCause);
            }
            Hpms.Pack p;
            try {
                p = new Hpms.Pack(tmp);
            } catch (Exception e) {
                p = null;
            }
            if (p == null) {
                tmp.delete();
                return "Not a StarView road data file.";
            }
            String st = null;
            MatchResult m = new Regex("hpms_([A-Z]{2})\\.bin").find(name, 0);
            if (m != null) {
                List<String> g = m.getGroupValues();
                if (g != null) {
                    st = g.get(1);
                }
            }
            if (st == null) {
                st = STATES.get(Integer.valueOf(p.getState()));
            }
            if (st == null) {
                st = "";
            }
            if (st.length() == 0) {
                tmp.delete();
                return "Unknown state in that file.";
            }
            tmp.renameTo(new File(dir, "hpms_" + st + ".bin"));
            Hpms.INSTANCE.reload();
            return null;
        } catch (Exception e2) {
            return "Couldn't import: " + e2.getMessage();
        }
    }

    public final String mb(long b) {
        String format;
        if (b >= 1000000000) {
            StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
            format = String.format(Locale.US, "%.1f GB", Arrays.copyOf(new Object[]{Double.valueOf(b / 1.0E9d)}, 1));
        } else {
            StringCompanionObject stringCompanionObject2 = StringCompanionObject.INSTANCE;
            format = String.format(Locale.US, "%.0f MB", Arrays.copyOf(new Object[]{Double.valueOf(Math.max(1.0d, b / 1000000.0d))}, 1));
        }
        Intrinsics.checkNotNullExpressionValue(format, "format(...)");
        return format;
    }

    public final Map<Integer, String> getSTATES() {
        return STATES;
    }
}
