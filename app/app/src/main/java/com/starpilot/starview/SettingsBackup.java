package com.starpilot.starview;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.io.ByteStreamsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.Charsets;
import kotlin.text.MatchResult;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: SettingsBackup.kt */
public final class SettingsBackup {
    public static final String AUTO_NAME = "starview-settings-auto.json";
    public static final String FOLDER = "Download/StarView";
    private static final String FORMAT = "starview-settings";
    private static final String META = "starview_backup_meta";
    private static long lastAutoMs;
    private static Runnable pending;
    public static final SettingsBackup INSTANCE = new SettingsBackup();
    private static final Map<String, Set<String>> SKIP = MapsKt.mapOf(TuplesKt.to("starview", SetsKt.setOf((Object[]) new String[]{"term_key", "paired_dongle", "paired_hosts"})), TuplesKt.to("starview_ntrip", SetsKt.setOf((Object[]) new String[]{"pass", "b_pass"})));
    private static final Handler main = new Handler(Looper.getMainLooper());

    private SettingsBackup() {
    }

    private final List<String> prefFiles(Context ctx) {
        File[] listFiles = new File(ctx.getApplicationInfo().dataDir, "shared_prefs").listFiles();
        if (listFiles != null) {
            Collection destination$iv$iv = new ArrayList();
            for (File file : listFiles) {
                String name = file.getName();
                Intrinsics.checkNotNullExpressionValue(name, "getName(...)");
                if (StringsKt.endsWith$default(name, ".xml", false, 2, (Object) null)) {
                    destination$iv$iv.add(file);
                }
            }
            Iterable $this$map$iv = (List) destination$iv$iv;
            Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            for (Object item$iv$iv : $this$map$iv) {
                File it = (File) item$iv$iv;
                String name2 = it.getName();
                Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                destination$iv$iv2.add(StringsKt.removeSuffix(name2, (CharSequence) ".xml"));
            }
            Iterable $this$filter$iv = (List) destination$iv$iv2;
            Collection destination$iv$iv3 = new ArrayList();
            for (Object element$iv$iv : $this$filter$iv) {
                String it2 = (String) element$iv$iv;
                if (!Intrinsics.areEqual(it2, "starview_backup_meta")) {
                    destination$iv$iv3.add(element$iv$iv);
                }
            }
            List<String> sorted = CollectionsKt.sorted((List) destination$iv$iv3);
            if (sorted != null) {
                return sorted;
            }
        }
        return CollectionsKt.emptyList();
    }

    public final JSONObject export(Context ctx) {
        String ver;
        JSONObject put;
        Iterator<String> it;
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        JSONObject prefs = new JSONObject();
        Iterator<String> it2 = prefFiles(ctx).iterator();
        while (it2.hasNext()) {
            String name = it2.next();
            Set skip = SKIP.get(name);
            if (skip == null) {
                skip = SetsKt.emptySet();
            }
            JSONObject o = new JSONObject();
            Map<String, ?> all = ctx.getSharedPreferences(name, 0).getAll();
            Intrinsics.checkNotNullExpressionValue(all, "getAll(...)");
            for (Map.Entry entry : MapsKt.toSortedMap(all).entrySet()) {
                String k = (String) entry.getKey();
                Object v = entry.getValue();
                if (!skip.contains(k) && v != null) {
                    if (v instanceof Boolean) {
                        put = new JSONObject().put("b", ((Boolean) v).booleanValue());
                        it = it2;
                    } else if (v instanceof Integer) {
                        put = new JSONObject().put("i", ((Number) v).intValue());
                        it = it2;
                    } else if (v instanceof Long) {
                        put = new JSONObject().put("l", ((Number) v).longValue());
                        it = it2;
                    } else if (v instanceof Float) {
                        put = new JSONObject().put("f", ((Number) v).floatValue());
                        it = it2;
                    } else if (v instanceof String) {
                        put = new JSONObject().put("s", v);
                        it = it2;
                    } else if (v instanceof Set) {
                        JSONObject jSONObject = new JSONObject();
                        Iterable $this$map$iv = (Iterable) v;
                        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                        for (Object item$iv$iv : $this$map$iv) {
                            destination$iv$iv.add(String.valueOf(item$iv$iv));
                            it2 = it2;
                        }
                        it = it2;
                        put = jSONObject.put("set", new JSONArray(destination$iv$iv));
                    }
                    o.put(k, put);
                    it2 = it;
                }
            }
            Iterator<String> it3 = it2;
            if (o.length() > 0) {
                prefs.put(name, o);
            }
            it2 = it3;
        }
        try {
            ver = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0).versionName;
        } catch (Exception e) {
            ver = "?";
        }
        JSONObject put2 = new JSONObject().put("format", "starview-settings").put("version", 1).put("app", ver).put("created", new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US).format(new Date())).put("prefs", prefs);
        Intrinsics.checkNotNullExpressionValue(put2, "put(...)");
        return put2;
    }

    /* compiled from: SettingsBackup.kt */
    private static abstract class Op {
        public /* synthetic */ Op(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public abstract String getKey();

        private Op() {
        }
    }

    /* compiled from: SettingsBackup.kt */
    private static final class Put extends Op {
        private final String key;
        private final Object v;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Put(String key, Object v) {
            super(null);
            Intrinsics.checkNotNullParameter(key, "key");
            Intrinsics.checkNotNullParameter(v, "v");
            this.key = key;
            this.v = v;
        }

        @Override // com.starpilot.starview.SettingsBackup.Op
        public String getKey() {
            return this.key;
        }

        public final Object getV() {
            return this.v;
        }
    }

    /* renamed from: import, reason: not valid java name */
    public final int m1159import(Context ctx, JSONObject j) {
        Object valueOf;
        JSONObject prefs;
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(j, "j");
        if (!Intrinsics.areEqual(j.optString("format"), "starview-settings")) {
            throw new IllegalArgumentException("not a StarView settings backup".toString());
        }
        JSONObject prefs2 = j.getJSONObject("prefs");
        LinkedHashMap plan = new LinkedHashMap();
        Iterator<String> keys = prefs2.keys();
        Intrinsics.checkNotNullExpressionValue(keys, "keys(...)");
        while (true) {
            int i = 0;
            if (keys.hasNext()) {
                String name = keys.next();
                Intrinsics.checkNotNull(name);
                if (!((!(name.length() > 0) || Intrinsics.areEqual(name, "starview_backup_meta") || StringsKt.contains$default((CharSequence) name, '/', false, 2, (Object) null)) ? false : true)) {
                    throw new IllegalArgumentException("bad settings file name".toString());
                }
                JSONObject o = prefs2.getJSONObject(name);
                Set skip = SKIP.get(name);
                if (skip == null) {
                    skip = SetsKt.emptySet();
                }
                ArrayList puts = new ArrayList();
                Iterator<String> keys2 = o.keys();
                Intrinsics.checkNotNullExpressionValue(keys2, "keys(...)");
                while (keys2.hasNext()) {
                    String k = keys2.next();
                    if (skip.contains(k)) {
                        i = 0;
                    } else {
                        JSONObject e = o.getJSONObject(k);
                        Intrinsics.checkNotNull(k);
                        if (e.has("b")) {
                            valueOf = Boolean.valueOf(e.getBoolean("b"));
                            prefs = prefs2;
                        } else if (e.has("i")) {
                            valueOf = Integer.valueOf(e.getInt("i"));
                            prefs = prefs2;
                        } else if (e.has("l")) {
                            valueOf = Long.valueOf(e.getLong("l"));
                            prefs = prefs2;
                        } else if (e.has("f")) {
                            valueOf = Float.valueOf((float) e.getDouble("f"));
                            prefs = prefs2;
                        } else if (e.has("s")) {
                            valueOf = e.getString("s");
                            prefs = prefs2;
                        } else if (e.has("set")) {
                            JSONArray a = e.getJSONArray("set");
                            Iterable $this$map$iv = RangesKt.until(i, a.length());
                            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                            Iterator<Integer> it = $this$map$iv.iterator();
                            while (it.hasNext()) {
                                int item$iv$iv = ((IntIterator) it).nextInt();
                                destination$iv$iv.add(a.getString(item$iv$iv));
                                prefs2 = prefs2;
                            }
                            prefs = prefs2;
                            valueOf = CollectionsKt.toSet((List) destination$iv$iv);
                        } else {
                            i = 0;
                        }
                        Intrinsics.checkNotNull(valueOf);
                        puts.add(new Put(k, valueOf));
                        prefs2 = prefs;
                        i = 0;
                    }
                }
                plan.put(name, puts);
            } else {
                int n = 0;
                for (Map.Entry entry : plan.entrySet()) {
                    String name2 = (String) entry.getKey();
                    List<Put> puts2 = (List) entry.getValue();
                    Set skip2 = SKIP.get(name2);
                    if (skip2 == null) {
                        skip2 = SetsKt.emptySet();
                    }
                    SharedPreferences sp = ctx.getSharedPreferences(name2, 0);
                    SharedPreferences.Editor ed = sp.edit();
                    for (String k2 : sp.getAll().keySet()) {
                        if (!skip2.contains(k2)) {
                            ed.remove(k2);
                        }
                    }
                    for (Put p : puts2) {
                        Object v = p.getV();
                        if (v instanceof Boolean) {
                            ed.putBoolean(p.getKey(), ((Boolean) v).booleanValue());
                        } else if (v instanceof Integer) {
                            ed.putInt(p.getKey(), ((Number) v).intValue());
                        } else if (v instanceof Long) {
                            ed.putLong(p.getKey(), ((Number) v).longValue());
                        } else if (v instanceof Float) {
                            ed.putFloat(p.getKey(), ((Number) v).floatValue());
                        } else if (v instanceof String) {
                            ed.putString(p.getKey(), (String) v);
                        } else if (v instanceof Set) {
                            ed.putStringSet(p.getKey(), (Set) v);
                        }
                        n++;
                    }
                    ed.commit();
                }
                ctx.getSharedPreferences("starview_backup_meta", 0).edit().remove("sig").commit();
                return n;
            }
        }
    }

    private final Uri downloadsUri(long id) {
        Uri withAppendedId = ContentUris.withAppendedId(MediaStore.Downloads.EXTERNAL_CONTENT_URI, id);
        Intrinsics.checkNotNullExpressionValue(withAppendedId, "withAppendedId(...)");
        return withAppendedId;
    }

    private final Uri ownRow(Context ctx, String fileName) {
        Cursor cursor;
        Object m1191constructorimpl;
        Boolean bool;
        SharedPreferences meta = ctx.getSharedPreferences("starview_backup_meta", 0);
        String s = meta.getString("row_" + fileName, null);
        if (s != null) {
            Uri u = Uri.parse(s);
            SettingsBackup settingsBackup = INSTANCE;
            try {
                Result.Companion companion = Result.Companion;
                Cursor query = ctx.getContentResolver().query(u, new String[]{"_id"}, null, null, null);
                if (query != null) {
                    cursor = query;
                    try {
                        Cursor it = cursor;
                        bool = Boolean.valueOf(it.moveToFirst());
                        CloseableKt.closeFinally(cursor, null);
                    } finally {
                    }
                } else {
                    bool = null;
                }
                m1191constructorimpl = Result.m1191constructorimpl(bool);
            } catch (Throwable th) {
                Result.Companion companion2 = Result.Companion;
                m1191constructorimpl = Result.m1191constructorimpl(ResultKt.createFailure(th));
            }
            if (Result.m1197isFailureimpl(m1191constructorimpl)) {
                m1191constructorimpl = null;
            }
            boolean alive = Intrinsics.areEqual(m1191constructorimpl, (Object) true);
            if (alive) {
                return u;
            }
        }
        Cursor query2 = ctx.getContentResolver().query(MediaStore.Downloads.EXTERNAL_CONTENT_URI, new String[]{"_id"}, "_display_name=? AND relative_path=?", new String[]{fileName, "Download/StarView/"}, null);
        if (query2 == null) {
            return null;
        }
        cursor = query2;
        try {
            Cursor c = cursor;
            Uri downloadsUri = c.moveToFirst() ? INSTANCE.downloadsUri(c.getLong(0)) : null;
            CloseableKt.closeFinally(cursor, null);
            return downloadsUri;
        } catch (Throwable th2) {
            try {
                throw th2;
            } finally {
            }
        }
    }

    public static /* synthetic */ String save$default(SettingsBackup settingsBackup, Context context, String str, JSONObject jSONObject, int i, Object obj) {
        if ((i & 4) != 0) {
            jSONObject = settingsBackup.export(context);
        }
        return settingsBackup.save(context, str, jSONObject);
    }

    public final String save(Context ctx, String fileName, JSONObject j) {
        Uri uri;
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(fileName, "fileName");
        Intrinsics.checkNotNullParameter(j, "j");
        String jSONObject = j.toString(1);
        Intrinsics.checkNotNullExpressionValue(jSONObject, "toString(...)");
        byte[] bytes = jSONObject.getBytes(Charsets.UTF_8);
        Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
        ContentResolver cr = ctx.getContentResolver();
        Uri existing = ownRow(ctx, fileName);
        if (existing != null) {
            uri = existing;
        } else {
            Uri uri2 = MediaStore.Downloads.EXTERNAL_CONTENT_URI;
            ContentValues $this$save_u24lambda_u2411 = new ContentValues();
            $this$save_u24lambda_u2411.put("_display_name", fileName);
            $this$save_u24lambda_u2411.put("mime_type", "application/json");
            $this$save_u24lambda_u2411.put("relative_path", "Download/StarView");
            Unit unit = Unit.INSTANCE;
            uri = cr.insert(uri2, $this$save_u24lambda_u2411);
            if (uri == null) {
                throw new IllegalStateException("can't create " + fileName);
            }
        }
        OutputStream openOutputStream = cr.openOutputStream(uri, "wt");
        if (openOutputStream == null) {
            throw new IllegalStateException("can't write " + fileName);
        }
        OutputStream outputStream = openOutputStream;
        try {
            OutputStream it = outputStream;
            it.write(bytes);
            Unit unit2 = Unit.INSTANCE;
            CloseableKt.closeFinally(outputStream, null);
            ctx.getSharedPreferences("starview_backup_meta", 0).edit().putString("row_" + fileName, uri.toString()).apply();
            return "Download/StarView/" + fileName;
        } finally {
        }
    }

    public final String saveCopy(Context ctx, File src, String name) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(src, "src");
        Intrinsics.checkNotNullParameter(name, "name");
        if (!src.isFile()) {
            throw new IllegalArgumentException((src.getName() + " doesn't exist yet").toString());
        }
        ContentResolver cr = ctx.getContentResolver();
        Uri uri = ownRow(ctx, name);
        if (uri == null) {
            Uri uri2 = MediaStore.Downloads.EXTERNAL_CONTENT_URI;
            ContentValues $this$saveCopy_u24lambda_u2414 = new ContentValues();
            $this$saveCopy_u24lambda_u2414.put("_display_name", name);
            $this$saveCopy_u24lambda_u2414.put("mime_type", "text/plain");
            $this$saveCopy_u24lambda_u2414.put("relative_path", "Download/StarView");
            Unit unit = Unit.INSTANCE;
            uri = cr.insert(uri2, $this$saveCopy_u24lambda_u2414);
            if (uri == null) {
                throw new IllegalStateException("can't create " + name);
            }
        }
        OutputStream openOutputStream = cr.openOutputStream(uri, "wt");
        if (openOutputStream == null) {
            throw new IllegalStateException("can't write " + name);
        }
        FileInputStream fileInputStream = openOutputStream;
        try {
            OutputStream out = fileInputStream;
            fileInputStream = new FileInputStream(src);
            try {
                FileInputStream it = fileInputStream;
                ByteStreamsKt.copyTo$default(it, out, 0, 2, null);
                CloseableKt.closeFinally(fileInputStream, null);
                CloseableKt.closeFinally(fileInputStream, null);
                ctx.getSharedPreferences("starview_backup_meta", 0).edit().putString("row_" + name, uri.toString()).apply();
                return "Download/StarView/" + name;
            } finally {
            }
        } finally {
        }
    }

    public final String manual(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        return save$default(this, ctx, "starview-settings-" + new SimpleDateFormat("yyyy-MM-dd-HHmm", Locale.US).format(new Date()) + ".json", null, 4, null);
    }

    public final void auto(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        final Context app = ctx.getApplicationContext();
        long now = System.currentTimeMillis();
        long wait = (lastAutoMs + 60000) - now;
        if (wait > 0) {
            if (pending == null) {
                Runnable r = new Runnable() { // from class: com.starpilot.starview.SettingsBackup$$ExternalSyntheticLambda6
                    @Override // java.lang.Runnable
                    public final void run() {
                        SettingsBackup.auto$lambda$17(app);
                    }
                };
                pending = r;
                main.postDelayed(r, wait);
                return;
            }
            return;
        }
        lastAutoMs = now;
        new Thread(new Runnable() { // from class: com.starpilot.starview.SettingsBackup$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                SettingsBackup.auto$lambda$18(app);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void auto$lambda$17(Context $app) {
        pending = null;
        SettingsBackup settingsBackup = INSTANCE;
        Intrinsics.checkNotNull($app);
        settingsBackup.auto($app);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void auto$lambda$18(Context $app) {
        try {
            SettingsBackup settingsBackup = INSTANCE;
            Intrinsics.checkNotNull($app);
            JSONObject j = settingsBackup.export($app);
            int sig = j.getJSONObject("prefs").toString().hashCode();
            SharedPreferences meta = $app.getSharedPreferences("starview_backup_meta", 0);
            if (meta.getInt("sig", 0) == sig && meta.contains("sig")) {
                return;
            }
            INSTANCE.save($app, "starview-settings-auto.json", j);
            String day = new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(new Date());
            INSTANCE.save($app, "starview-settings-" + day + ".json", j);
            INSTANCE.prune($app);
            meta.edit().putInt("sig", sig).putLong("at", System.currentTimeMillis()).apply();
        } catch (Exception e) {
            Log.w("StarView/Backup", "auto backup: " + e);
        }
    }

    private final void prune(Context ctx) {
        MatchResult m;
        Regex rx = new Regex("starview-settings-(\\d{4}-\\d{2}-\\d{2})( \\(\\d+\\))?\\.json");
        ArrayList found = new ArrayList();
        Cursor query = ctx.getContentResolver().query(MediaStore.Downloads.EXTERNAL_CONTENT_URI, new String[]{"_id", "_display_name"}, "relative_path=?", new String[]{"Download/StarView/"}, null);
        if (query != null) {
            Cursor cursor = query;
            try {
                Cursor c = cursor;
                while (c.moveToNext()) {
                    String name = c.getString(1);
                    if (name != null && (m = rx.matchEntire(name)) != null) {
                        found.add(TuplesKt.to(m.getGroupValues().get(1), INSTANCE.downloadsUri(c.getLong(0))));
                    }
                }
                Unit unit = Unit.INSTANCE;
                CloseableKt.closeFinally(cursor, null);
            } finally {
            }
        }
        ArrayList $this$map$iv = found;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        for (Object item$iv$iv : $this$map$iv) {
            Pair it = (Pair) item$iv$iv;
            destination$iv$iv.add((String) it.getFirst());
        }
        Set keep = CollectionsKt.toSet(CollectionsKt.take(CollectionsKt.sortedDescending(CollectionsKt.distinct((List) destination$iv$iv)), 7));
        Iterator it2 = found.iterator();
        Intrinsics.checkNotNullExpressionValue(it2, "iterator(...)");
        while (it2.hasNext()) {
            Object next = it2.next();
            Intrinsics.checkNotNullExpressionValue(next, "next(...)");
            Pair pair = (Pair) next;
            String day = (String) pair.component1();
            Uri uri = (Uri) pair.component2();
            if (!keep.contains(day)) {
                try {
                    Result.Companion companion = Result.Companion;
                    SettingsBackup settingsBackup = this;
                    Result.m1191constructorimpl(Integer.valueOf(ctx.getContentResolver().delete(uri, null, null)));
                } catch (Throwable th) {
                    Result.Companion companion2 = Result.Companion;
                    Result.m1191constructorimpl(ResultKt.createFailure(th));
                }
            }
        }
    }

    public final long lastAuto(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        return ctx.getSharedPreferences("starview_backup_meta", 0).getLong("at", 0L);
    }

    public final boolean looksFresh(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        return ctx.getSharedPreferences("starview_ui", 0).getAll().isEmpty() && ctx.getSharedPreferences("starview_hud", 0).getAll().isEmpty();
    }

    public final void restart(Activity act) {
        Intrinsics.checkNotNullParameter(act, "act");
        Intent i = new Intent(act, (Class<?>) MainActivity.class).addFlags(268468224);
        Intrinsics.checkNotNullExpressionValue(i, "addFlags(...)");
        act.startActivity(i);
        act.finishAffinity();
        Runtime.getRuntime().exit(0);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void restoreFrom$default(SettingsBackup settingsBackup, Activity activity, Uri uri, Function0 function0, int i, Object obj) {
        if ((i & 4) != 0) {
            function0 = new Function0() { // from class: com.starpilot.starview.SettingsBackup$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit unit;
                    unit = Unit.INSTANCE;
                    return unit;
                }
            };
        }
        settingsBackup.restoreFrom(activity, uri, function0);
    }

    public final void restoreFrom(final Activity act, Uri uri, final Function0<Unit> onCancel) {
        final JSONObject j;
        Intrinsics.checkNotNullParameter(act, "act");
        Intrinsics.checkNotNullParameter(uri, "uri");
        Intrinsics.checkNotNullParameter(onCancel, "onCancel");
        try {
            j = read(act, uri);
        } catch (Exception e) {
            j = null;
        }
        if (j == null || !Intrinsics.areEqual(j.optString("format"), "starview-settings")) {
            new AlertDialog.Builder(act).setMessage("That file isn't a StarView settings backup.").setPositiveButton("OK", (DialogInterface.OnClickListener) null).setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.starpilot.starview.SettingsBackup$$ExternalSyntheticLambda1
                @Override // android.content.DialogInterface.OnDismissListener
                public final void onDismiss(DialogInterface dialogInterface) {
                    Function0.this.invoke();
                }
            }).show();
            return;
        }
        new AlertDialog.Builder(act).setTitle("Restore settings?").setMessage("Backup from " + j.optString("created", "?") + " (StarView " + j.optString("app", "?") + "). Every tablet setting and the driving-screen layout are replaced, then StarView starts again. The comma pairing stays as it is.").setPositiveButton("Restore", new DialogInterface.OnClickListener() { // from class: com.starpilot.starview.SettingsBackup$$ExternalSyntheticLambda2
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                SettingsBackup.restoreFrom$lambda$26(act, j, onCancel, dialogInterface, i);
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() { // from class: com.starpilot.starview.SettingsBackup$$ExternalSyntheticLambda3
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                Function0.this.invoke();
            }
        }).setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.starpilot.starview.SettingsBackup$$ExternalSyntheticLambda4
            @Override // android.content.DialogInterface.OnCancelListener
            public final void onCancel(DialogInterface dialogInterface) {
                Function0.this.invoke();
            }
        }).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void restoreFrom$lambda$26(Activity $act, JSONObject $j, final Function0 $onCancel, DialogInterface dialogInterface, int i) {
        try {
            INSTANCE.m1159import($act, $j);
            INSTANCE.restart($act);
        } catch (Exception e) {
            new AlertDialog.Builder($act).setMessage("Restore failed, nothing was changed: " + e.getMessage()).setPositiveButton("OK", (DialogInterface.OnClickListener) null).setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.starpilot.starview.SettingsBackup$$ExternalSyntheticLambda5
                @Override // android.content.DialogInterface.OnDismissListener
                public final void onDismiss(DialogInterface dialogInterface2) {
                    Function0.this.invoke();
                }
            }).show();
        }
    }

    public final JSONObject read(Context ctx, Uri uri) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(uri, "uri");
        InputStream openInputStream = ctx.getContentResolver().openInputStream(uri);
        if (openInputStream == null) {
            throw new IllegalStateException("can't read the file");
        }
        InputStream inputStream = openInputStream;
        try {
            InputStream it = inputStream;
            String str = new String(ByteStreamsKt.readBytes(it), Charsets.UTF_8);
            CloseableKt.closeFinally(inputStream, null);
            return new JSONObject(str);
        } finally {
        }
    }
}
