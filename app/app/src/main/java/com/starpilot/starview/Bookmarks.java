package com.starpilot.starview;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.provider.MediaStore;
import com.starpilot.starview.Bookmarks;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.io.ByteStreamsKt;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: Bookmarks.kt */
public final class Bookmarks {
    public static final Bookmarks INSTANCE = new Bookmarks();

    private Bookmarks() {
    }

    public final File dir(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        File $this$dir_u24lambda_u240 = new File(ctx.getFilesDir(), "bookmarks");
        $this$dir_u24lambda_u240.mkdirs();
        return $this$dir_u24lambda_u240;
    }

    /* compiled from: Bookmarks.kt */
    public static final class Item {
        private final File dir;
        private final String id;
        private final JSONObject json;

        public Item(String id, File dir, JSONObject json) {
            Intrinsics.checkNotNullParameter(id, "id");
            Intrinsics.checkNotNullParameter(dir, "dir");
            Intrinsics.checkNotNullParameter(json, "json");
            this.id = id;
            this.dir = dir;
            this.json = json;
        }

        public final File getDir() {
            return this.dir;
        }

        public final String getId() {
            return this.id;
        }

        public final JSONObject getJson() {
            return this.json;
        }

        public final long getAtMs() {
            return this.json.optLong("at_ms");
        }

        public final String getNote() {
            return this.json.optString("note");
        }

        public final String getTranscript() {
            return this.json.optString("voice_text");
        }

        public final boolean getHasVoice() {
            File it = new File(this.dir, "voice.wav");
            return it.exists() && it.length() > 44;
        }

        public final boolean getHasPicture() {
            return new File(this.dir, "picture.jpg").exists();
        }

        public final String getTitle() {
            String optString;
            JSONObject optJSONObject = this.json.optJSONObject("lane");
            return (optJSONObject == null || (optString = optJSONObject.optString("label")) == null) ? "" : optString;
        }

        public final String getPlace() {
            return this.json.optString("place");
        }
    }

    public final Item create(Context ctx, JSONObject snap) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(snap, "snap");
        long now = System.currentTimeMillis();
        String id = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date(now));
        File d = new File(dir(ctx), id);
        int k = 2;
        while (d.exists()) {
            d = new File(dir(ctx), id + "_" + k);
            k++;
        }
        String id2 = d.getName();
        d.mkdirs();
        snap.put("id", id2);
        snap.put("at_ms", now);
        snap.put("at", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(new Date(now)));
        write(d, snap);
        Intrinsics.checkNotNull(id2);
        return new Item(id2, d, snap);
    }

    private final void write(File d, JSONObject j) {
        File tmp = new File(d, "bookmark.json.tmp");
        String jSONObject = j.toString(2);
        Intrinsics.checkNotNullExpressionValue(jSONObject, "toString(...)");
        FilesKt.writeText(tmp, jSONObject, kotlin.text.Charsets.UTF_8);
        tmp.renameTo(new File(d, "bookmark.json"));
    }

    public final Item load(File d) {
        Intrinsics.checkNotNullParameter(d, "d");
        try {
            String name = d.getName();
            Intrinsics.checkNotNullExpressionValue(name, "getName(...)");
            return new Item(name, d, new JSONObject(FilesKt.readText(new File(d, "bookmark.json"), kotlin.text.Charsets.UTF_8)));
        } catch (Exception e) {
            return null;
        }
    }

    public final List<Item> list(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        File[] listFiles = dir(ctx).listFiles(new FileFilter() { // from class: com.starpilot.starview.Bookmarks$$ExternalSyntheticLambda0
            @Override // java.io.FileFilter
            public final boolean accept(File file) {
                boolean isDirectory;
                isDirectory = file.isDirectory();
                return isDirectory;
            }
        });
        if (listFiles == null) {
            listFiles = new File[0];
        }
        Collection destination$iv$iv = new ArrayList();
        for (File file : listFiles) {
            Bookmarks bookmarks = INSTANCE;
            Intrinsics.checkNotNull(file);
            Item load = bookmarks.load(file);
            if (load != null) {
                destination$iv$iv.add(load);
            }
        }
        Iterable $this$sortedByDescending$iv = (List) destination$iv$iv;
        return CollectionsKt.sortedWith($this$sortedByDescending$iv, new Comparator() { // from class: com.starpilot.starview.Bookmarks$list$$inlined$sortedByDescending$1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.Comparator
            public final int compare(Object t, Object t2) {
                Bookmarks.Item it = (Bookmarks.Item) t2;
                Bookmarks.Item it2 = (Bookmarks.Item) t;
                return ComparisonsKt.compareValues(Long.valueOf(it.getAtMs()), Long.valueOf(it2.getAtMs()));
            }
        });
    }

    public final void update(Item item, Function1<? super JSONObject, Unit> change) {
        JSONObject j;
        Intrinsics.checkNotNullParameter(item, "item");
        Intrinsics.checkNotNullParameter(change, "change");
        synchronized (this) {
            Item load = INSTANCE.load(item.getDir());
            if (load == null || (j = load.getJson()) == null) {
                j = item.getJson();
            }
            change.invoke(j);
            INSTANCE.write(item.getDir(), j);
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void delete(Item item) {
        Intrinsics.checkNotNullParameter(item, "item");
        FilesKt.deleteRecursively(item.getDir());
    }

    public final Pair<String, Integer> export(Context ctx, boolean onlyNew) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        try {
            List<Item> all = list(ctx);
            ArrayList<Item> items = new ArrayList();
            for (Item it : all) {
                if (!onlyNew || !it.getJson().optBoolean("exported")) {
                    items.add(it);
                }
            }
            if (items.isEmpty()) {
                return TuplesKt.to(onlyNew ? "No new bookmarks since the last export." : "No bookmarks yet.", 0);
            }
            String name = "bookmarks_" + new SimpleDateFormat("yyyy-MM-dd_HH-mm", Locale.US).format(new Date()) + ".zip";
            ContentValues cv = new ContentValues();
            cv.put("_display_name", name);
            cv.put("mime_type", "application/zip");
            cv.put("relative_path", "Download/StarView/Bookmarks");
            Uri uri = ctx.getContentResolver().insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, cv);
            if (uri == null) {
                throw new IllegalStateException("couldn't create " + name);
            }
            OutputStream os = ctx.getContentResolver().openOutputStream(uri);
            Intrinsics.checkNotNull(os);
            Throwable osCause = null;
            try {
                ZipOutputStream z = new ZipOutputStream(os);
                Throwable zCause = null;
                try {
                    for (Item it2 : items) {
                        File[] files = it2.getDir().listFiles();
                        if (files == null) {
                            files = new File[0];
                        }
                        for (File f : files) {
                            String fn = f.getName();
                            Intrinsics.checkNotNullExpressionValue(fn, "getName(...)");
                            if (!StringsKt.endsWith(fn, ".tmp", false)) {
                                z.putNextEntry(new ZipEntry(it2.getId() + "/" + f.getName()));
                                Intrinsics.checkNotNull(f);
                                FileInputStream in = new FileInputStream(f);
                                Throwable inCause = null;
                                try {
                                    ByteStreamsKt.copyTo(in, z, 8192);
                                } catch (Throwable th) {
                                    inCause = th;
                                    throw th;
                                } finally {
                                    CloseableKt.closeFinally(in, inCause);
                                }
                                z.closeEntry();
                            }
                        }
                    }
                } catch (Throwable th2) {
                    zCause = th2;
                    throw th2;
                } finally {
                    CloseableKt.closeFinally(z, zCause);
                }
            } catch (Throwable th3) {
                osCause = th3;
                throw th3;
            } finally {
                CloseableKt.closeFinally(os, osCause);
            }
            for (Item it3 : items) {
                update(it3, new Function1() { // from class: com.starpilot.starview.Bookmarks$$ExternalSyntheticLambda1
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        return Bookmarks.export$lambda$10((JSONObject) obj);
                    }
                });
            }
            return TuplesKt.to(null, Integer.valueOf(items.size()));
        } catch (Exception e) {
            return TuplesKt.to("Couldn't export: " + e.getMessage(), 0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit export$lambda$10(JSONObject j) {
        Intrinsics.checkNotNullParameter(j, "j");
        j.put("exported", true);
        return Unit.INSTANCE;
    }

    public final JSONArray tail(File f, int n) {
        Intrinsics.checkNotNullParameter(f, "f");
        JSONArray a = new JSONArray();
        try {
            RandomAccessFile r = new RandomAccessFile(f, "r");
            Throwable rCause = null;
            try {
                long len = r.length();
                long from = Math.max(0L, len - 64000);
                r.seek(from);
                byte[] b = new byte[(int) (len - from)];
                r.readFully(b);
                List<String> parts = StringsKt.split((CharSequence) new String(b, Charsets.UTF_8), new String[]{"\n"}, false, 0);
                ArrayList<String> l = new ArrayList();
                for (String it : parts) {
                    if (!StringsKt.isBlank(it)) {
                        l.add(it);
                    }
                }
                for (String it2 : CollectionsKt.takeLast(from > 0 ? CollectionsKt.drop(l, 1) : l, n)) {
                    a.put(it2);
                }
            } catch (Throwable th) {
                rCause = th;
                throw th;
            } finally {
                CloseableKt.closeFinally(r, rCause);
            }
        } catch (Exception e) {
        }
        return a;
    }
}
