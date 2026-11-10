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
            public final int compare(T t, T t2) {
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

    /* JADX WARN: Removed duplicated region for block: B:13:0x003f A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:17:0x001d A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Pair<String, Integer> export(Context ctx, boolean onlyNew) {
        Throwable th;
        Throwable th2;
        Throwable th3;
        OutputStream os;
        Throwable th4;
        boolean z;
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        int i = 0;
        try {
            Iterable $this$filter$iv = list(ctx);
            Collection destination$iv$iv = new ArrayList();
            for (Object element$iv$iv : $this$filter$iv) {
                Item it = (Item) element$iv$iv;
                if (onlyNew && it.getJson().optBoolean("exported")) {
                    z = false;
                    if (!z) {
                        destination$iv$iv.add(element$iv$iv);
                    }
                }
                z = true;
                if (!z) {
                }
            }
            List items = (List) destination$iv$iv;
            if (items.isEmpty()) {
                return TuplesKt.to(onlyNew ? "No new bookmarks since the last export." : "No bookmarks yet.", 0);
            }
            String name = "bookmarks_" + new SimpleDateFormat("yyyy-MM-dd_HH-mm", Locale.US).format(new Date()) + ".zip";
            ContentValues $this$export_u24lambda_u246 = new ContentValues();
            $this$export_u24lambda_u246.put("_display_name", name);
            $this$export_u24lambda_u246.put("mime_type", "application/zip");
            $this$export_u24lambda_u246.put("relative_path", "Download/StarView/Bookmarks");
            ContentValues cv = $this$export_u24lambda_u246;
            Uri insert = ctx.getContentResolver().insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, cv);
            try {
                if (insert == null) {
                    throw new IllegalStateException("couldn't create " + name);
                }
                Uri uri = insert;
                OutputStream openOutputStream = ctx.getContentResolver().openOutputStream(uri);
                Intrinsics.checkNotNull(openOutputStream);
                FileInputStream fileInputStream = openOutputStream;
                try {
                    OutputStream os2 = fileInputStream;
                    ZipOutputStream zipOutputStream = new ZipOutputStream(os2);
                    try {
                        ZipOutputStream z2 = zipOutputStream;
                        Iterator it2 = items.iterator();
                        while (it2.hasNext()) {
                            Item it3 = (Item) it2.next();
                            File[] listFiles = it3.getDir().listFiles();
                            if (listFiles == null) {
                                try {
                                    listFiles = new File[i];
                                } catch (Throwable th5) {
                                    th2 = th5;
                                    try {
                                        throw th2;
                                    } catch (Throwable th6) {
                                        try {
                                            CloseableKt.closeFinally(zipOutputStream, th2);
                                            throw th6;
                                        } catch (Throwable th7) {
                                            th3 = th7;
                                            th = th3;
                                            try {
                                                throw th;
                                            } finally {
                                            }
                                        }
                                    }
                                }
                            }
                            int length = listFiles.length;
                            int i2 = 0;
                            while (i2 < length) {
                                File f = listFiles[i2];
                                Iterator it4 = it2;
                                String name2 = f.getName();
                                Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                                int i3 = length;
                                ContentValues cv2 = cv;
                                Uri uri2 = uri;
                                try {
                                    if (StringsKt.endsWith(name2, ".tmp", false)) {
                                        os = os2;
                                    } else {
                                        z2.putNextEntry(new ZipEntry(it3.getId() + "/" + f.getName()));
                                        Intrinsics.checkNotNull(f);
                                        fileInputStream = new FileInputStream(f);
                                        try {
                                            FileInputStream s = fileInputStream;
                                            os = os2;
                                            try {
                                                ByteStreamsKt.copyTo(s, z2, 8192);
                                                try {
                                                    CloseableKt.closeFinally(fileInputStream, null);
                                                    z2.closeEntry();
                                                } catch (Throwable th8) {
                                                    th2 = th8;
                                                    throw th2;
                                                }
                                            } catch (Throwable th9) {
                                                th4 = th9;
                                                try {
                                                    throw th;
                                                } finally {
                                                }
                                            }
                                        } catch (Throwable th10) {
                                            th4 = th10;
                                        }
                                    }
                                    i2++;
                                    it2 = it4;
                                    os2 = os;
                                    length = i3;
                                    cv = cv2;
                                    uri = uri2;
                                } catch (Throwable th11) {
                                    th2 = th11;
                                }
                            }
                            i = 0;
                        }
                        Unit unit = Unit.INSTANCE;
                        try {
                            CloseableKt.closeFinally(zipOutputStream, null);
                            Unit unit2 = Unit.INSTANCE;
                            CloseableKt.closeFinally(fileInputStream, null);
                            Iterator it5 = items.iterator();
                            while (it5.hasNext()) {
                                update((Item) it5.next(), new Function1() { // from class: com.starpilot.starview.Bookmarks$$ExternalSyntheticLambda1
                                    @Override // kotlin.jvm.functions.Function1
                                    public final Object invoke(Object obj) {
                                        Unit export$lambda$10;
                                        export$lambda$10 = Bookmarks.export$lambda$10((JSONObject) obj);
                                        return export$lambda$10;
                                    }
                                });
                            }
                            return TuplesKt.to(null, Integer.valueOf(items.size()));
                        } catch (Throwable th12) {
                            th3 = th12;
                            th = th3;
                            throw th;
                        }
                    } catch (Throwable th13) {
                        th2 = th13;
                    }
                } catch (Throwable th14) {
                    th = th14;
                }
            } catch (Exception e) {
                e = e;
                return TuplesKt.to("Couldn't export: " + e.getMessage(), 0);
            }
        } catch (Exception e2) {
            e = e2;
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
            try {
                RandomAccessFile randomAccessFile = new RandomAccessFile(f, "r");
                try {
                    RandomAccessFile r = randomAccessFile;
                    long len = r.length();
                    long from = Math.max(0L, len - 64000);
                    r.seek(from);
                    byte[] b = new byte[(int) (len - from)];
                    r.readFully(b);
                    Iterable $this$filter$iv = StringsKt.split((CharSequence) new String(b, Charsets.UTF_8), new String[]{"\n"}, false, 0);
                    Collection destination$iv$iv = new ArrayList();
                    for (Object element$iv$iv : $this$filter$iv) {
                        String it = (String) element$iv$iv;
                        if (!StringsKt.isBlank(it)) {
                            destination$iv$iv.add(element$iv$iv);
                        }
                    }
                    List l = (List) destination$iv$iv;
                    try {
                        Iterable $this$forEach$iv = CollectionsKt.takeLast(from > 0 ? CollectionsKt.drop(l, 1) : l, n);
                        for (Object element$iv : $this$forEach$iv) {
                            String it2 = (String) element$iv;
                            a.put(it2);
                            r = r;
                        }
                        Unit unit = Unit.INSTANCE;
                        CloseableKt.closeFinally(randomAccessFile, null);
                    } catch (Throwable th) {
                        th = th;
                        Throwable th2 = th;
                        try {
                            throw th2;
                        } catch (Throwable th3) {
                            CloseableKt.closeFinally(randomAccessFile, th2);
                            throw th3;
                        }
                    }
                } catch (Throwable th4) {
                    th = th4;
                }
            } catch (Exception e) {
            }
        } catch (Exception e2) {
        }
        return a;
    }
}
