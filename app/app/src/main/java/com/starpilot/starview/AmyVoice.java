package com.starpilot.starview;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioTrack;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.starpilot.starview.AmySynth;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.io.ByteStreamsKt;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.StringsKt;

/* compiled from: AmyVoice.kt */
public final class AmyVoice {
    private static Context app;
    private static volatile long busyUntil;
    private static volatile int demoGen;
    private static volatile File dirC;
    private static volatile int gen;
    private static volatile long probedAt;
    private static volatile AudioTrack track;
    public static final AmyVoice INSTANCE = new AmyVoice();
    private static final HashMap<String, float[]> cache = new HashMap<>();
    private static final ExecutorService exec = Executors.newSingleThreadExecutor();
    private static final Handler main = new Handler(Looper.getMainLooper());

    private AmyVoice() {
    }

    public final long getBusyUntil() {
        return busyUntil;
    }

    public final void init(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        if (app == null) {
            app = ctx.getApplicationContext();
        }
    }

    /* compiled from: AmyVoice.kt */
    public static final /* data */ class Pack {
        private final File dir;
        private final String id;
        private final String label;

        public static /* synthetic */ Pack copy$default(Pack pack, String str, String str2, File file, int i, Object obj) {
            if ((i & 1) != 0) {
                str = pack.id;
            }
            if ((i & 2) != 0) {
                str2 = pack.label;
            }
            if ((i & 4) != 0) {
                file = pack.dir;
            }
            return pack.copy(str, str2, file);
        }



        public final Pack copy(String id, String label, File dir) {
            Intrinsics.checkNotNullParameter(id, "id");
            Intrinsics.checkNotNullParameter(label, "label");
            Intrinsics.checkNotNullParameter(dir, "dir");
            return new Pack(id, label, dir);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Pack)) {
                return false;
            }
            Pack pack = (Pack) other;
            return Intrinsics.areEqual(this.id, pack.id) && Intrinsics.areEqual(this.label, pack.label) && Intrinsics.areEqual(this.dir, pack.dir);
        }

        public int hashCode() {
            return (((this.id.hashCode() * 31) + this.label.hashCode()) * 31) + this.dir.hashCode();
        }

        public String toString() {
            return "Pack(id=" + this.id + ", label=" + this.label + ", dir=" + this.dir + ")";
        }

        public Pack(String id, String label, File dir) {
            Intrinsics.checkNotNullParameter(id, "id");
            Intrinsics.checkNotNullParameter(label, "label");
            Intrinsics.checkNotNullParameter(dir, "dir");
            this.id = id;
            this.label = label;
            this.dir = dir;
        }

        public final File getDir() {
            return this.dir;
        }

        public final String getId() {
            return this.id;
        }

        public final String getLabel() {
            return this.label;
        }
    }

    private final boolean ok(File d) {
        return d != null && new File(d, "ask_left.wav").isFile();
    }

    public final List<Pack> packs() {
        Object element$iv;
        String name;
        String readText$default;
        Context c = app;
        if (c == null) {
            return CollectionsKt.emptyList();
        }
        ArrayList out = new ArrayList();
        File[] fileArr = new File[2];
        fileArr[0] = new File(c.getFilesDir(), "amy");
        File it = c.getExternalFilesDir(null);
        fileArr[1] = it != null ? new File(it, "amy") : null;
        Iterable $this$firstOrNull$iv = CollectionsKt.listOf((Object[]) fileArr);
        Iterator it2 = $this$firstOrNull$iv.iterator();
        while (true) {
            if (!it2.hasNext()) {
                element$iv = null;
                break;
            }
            element$iv = it2.next();
            if (INSTANCE.ok((File) element$iv)) {
                break;
            }
        }
        File it3 = (File) element$iv;
        if (it3 != null) {
            out.add(new Pack("amy", "Amy · recorded on the PC", it3));
        }
        File[] listFiles = new File(c.getFilesDir(), "voices").listFiles();
        if (listFiles != null) {
            Collection destination$iv$iv = new ArrayList();
            for (File file : listFiles) {
                if (INSTANCE.ok(file)) {
                    destination$iv$iv.add(file);
                }
            }
            Iterable $this$sortedBy$iv = CollectionsKt.sortedWith((List) destination$iv$iv, new Comparator() { // from class: com.starpilot.starview.AmyVoice$packs$$inlined$sortedBy$1
                /* JADX WARN: Multi-variable type inference failed */
                @Override // java.util.Comparator
                public final int compare(Object t, Object t2) {
                    File it4 = (File) t;
                    File it5 = (File) t2;
                    return ComparisonsKt.compareValues(Long.valueOf(it4.lastModified()), Long.valueOf(it5.lastModified()));
                }
            });
            if ($this$sortedBy$iv != null) {
                Iterable $this$forEach$iv = $this$sortedBy$iv;
                for (Object element$iv2 : $this$forEach$iv) {
                    File d = (File) element$iv2;
                    String str = "gen:" + d.getName();
                    File it4 = new File(d, "label.txt");
                    if (!it4.isFile()) {
                        it4 = null;
                    }
                    if (it4 == null || (readText$default = FilesKt.readText(it4, kotlin.text.Charsets.UTF_8)) == null || (name = StringsKt.trim((CharSequence) readText$default).toString()) == null) {
                        name = d.getName();
                    }
                    Intrinsics.checkNotNull(name);
                    Intrinsics.checkNotNull(d);
                    out.add(new Pack(str, name, d));
                }
            }
        }
        return out;
    }

    private final SharedPreferences getPrefs() {
        Context context = app;
        Intrinsics.checkNotNull(context);
        return context.getSharedPreferences("speech", 0);
    }

    public final String getActiveId() {
        String string;
        return (app == null || (string = getPrefs().getString("pack", "")) == null) ? "" : string;
    }

    public final void setActiveId(String v) {
        Intrinsics.checkNotNullParameter(v, "v");
        getPrefs().edit().putString("pack", v).apply();
        dirC = null;
        probedAt = 0L;
        synchronized (cache) {
            cache.clear();
            Unit unit = Unit.INSTANCE;
        }
    }

    private final File dir() {
        Object element$iv;
        File it = dirC;
        if (it != null && INSTANCE.ok(it)) {
            return it;
        }
        if (app == null) {
            return null;
        }
        long now = System.currentTimeMillis();
        if (now - probedAt < 5000) {
            return null;
        }
        probedAt = now;
        List ps = packs();
        List $this$firstOrNull$iv = ps;
        Iterator it2 = $this$firstOrNull$iv.iterator();
        while (true) {
            if (it2.hasNext()) {
                element$iv = it2.next();
                if (Intrinsics.areEqual(((Pack) element$iv).getId(), INSTANCE.getActiveId())) {
                    break;
                }
            } else {
                element$iv = null;
                break;
            }
        }
        Pack pack = (Pack) element$iv;
        if (pack == null) {
            pack = (Pack) CollectionsKt.firstOrNull(ps);
        }
        dirC = pack != null ? pack.getDir() : null;
        return dirC;
    }

    public final Pack active() {
        File d = dir();
        Object obj = null;
        if (d == null) {
            return null;
        }
        Iterable $this$firstOrNull$iv = packs();
        Iterator it = $this$firstOrNull$iv.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Object element$iv = it.next();
            Pack it2 = (Pack) element$iv;
            if (Intrinsics.areEqual(it2.getDir(), d)) {
                obj = element$iv;
                break;
            }
        }
        return (Pack) obj;
    }

    public final boolean getInstalled() {
        return dir() != null;
    }

    public final boolean getReady() {
        return getInstalled();
    }

    public static /* synthetic */ boolean busy$default(AmyVoice amyVoice, long j, int i, Object obj) {
        if ((i & 1) != 0) {
            j = System.currentTimeMillis();
        }
        return amyVoice.busy(j);
    }

    public final boolean busy(long now) {
        return now < busyUntil;
    }

    public final void use(String id) {
        Intrinsics.checkNotNullParameter(id, "id");
        setActiveId(id);
        Speech.INSTANCE.setUseAmy(true);
    }

    public final void delete(String id) {
        Object element$iv;
        Intrinsics.checkNotNullParameter(id, "id");
        Iterable $this$firstOrNull$iv = packs();
        Iterator it = $this$firstOrNull$iv.iterator();
        while (true) {
            if (it.hasNext()) {
                element$iv = it.next();
                Pack it2 = (Pack) element$iv;
                if (Intrinsics.areEqual(it2.getId(), id)) {
                    break;
                }
            } else {
                element$iv = null;
                break;
            }
        }
        Pack p = (Pack) element$iv;
        if (p == null) {
            return;
        }
        StringsKt.startsWith(p.getId(), "gen:", false);
        FilesKt.deleteRecursively(p.getDir());
        setActiveId(Intrinsics.areEqual(getActiveId(), id) ? "" : getActiveId());
    }

    private final float[] clip(String key) {
        float[] fArr;
        byte[] it;
        float[] it2;
        synchronized (cache) {
            fArr = cache.get(key);
            if (fArr == null) {
                fArr = null;
                try {
                    File it3 = INSTANCE.dir();
                    if (it3 != null) {
                        File it4 = new File(it3, key + ".wav");
                        if (!it4.isFile()) {
                            it4 = null;
                        }
                        if (it4 != null && (it = FilesKt.readBytes(it4)) != null && (it2 = AmySynth.INSTANCE.wavFloats(it)) != null) {
                            cache.put(key, it2);
                            fArr = it2;
                        }
                    }
                } catch (Exception e) {
                }
            }
        }
        return fArr;
    }

    public final String install(Context ctx, Uri uri) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(uri, "uri");
        File tmp = new File(ctx.getFilesDir(), "amy.tmp");
        FilesKt.deleteRecursively(tmp);
        tmp.mkdirs();
        int n = 0;
        try {
            InputStream ins = ctx.getContentResolver().openInputStream(uri);
            if (ins == null) {
                return "Couldn't open that file";
            }
            Throwable insCause = null;
            try {
                ZipInputStream z = new ZipInputStream(ins instanceof BufferedInputStream ? (BufferedInputStream) ins : new BufferedInputStream(ins, 8192));
                Throwable zCause = null;
                try {
                    while (true) {
                        ZipEntry e = z.getNextEntry();
                        if (e == null) {
                            break;
                        }
                        String name0 = e.getName();
                        Intrinsics.checkNotNullExpressionValue(name0, "getName(...)");
                        String name = StringsKt.substringAfterLast(name0, '/', name0);
                        if (!e.isDirectory() && new Regex("[a-z0-9_]+\\.wav").matches(name)) {
                            FileOutputStream it = new FileOutputStream(new File(tmp, name));
                            Throwable itCause = null;
                            try {
                                ByteStreamsKt.copyTo(z, it, 8192);
                            } catch (Throwable th) {
                                itCause = th;
                                throw th;
                            } finally {
                                CloseableKt.closeFinally(it, itCause);
                            }
                            n++;
                        }
                    }
                } catch (Throwable th2) {
                    zCause = th2;
                    throw th2;
                } finally {
                    CloseableKt.closeFinally(z, zCause);
                }
            } catch (Throwable th3) {
                insCause = th3;
                throw th3;
            } finally {
                CloseableKt.closeFinally(ins, insCause);
            }
        } catch (Exception e2) {
            FilesKt.deleteRecursively(tmp);
            return "Not a voice pack zip (" + e2.getMessage() + ")";
        }
        if (!new File(tmp, "ask_left.wav").isFile()) {
            FilesKt.deleteRecursively(tmp);
            return "That zip has no Amy recordings in it - pick StarView-Amy-voice.zip";
        }
        File dst = new File(ctx.getFilesDir(), "amy");
        FilesKt.deleteRecursively(dst);
        if (!tmp.renameTo(dst)) {
            FilesKt.deleteRecursively(tmp);
            return "Couldn't save it";
        }
        Log.i("AmyVoice", "installed " + n + " recordings");
        use("amy");
        return null;
    }

    public static /* synthetic */ int recordings$default(AmyVoice amyVoice, Pack pack, int i, Object obj) {
        if ((i & 1) != 0) {
            pack = amyVoice.active();
        }
        return amyVoice.recordings(pack);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean recordings$lambda$20(File f) {
        String name = f.getName();
        Intrinsics.checkNotNullExpressionValue(name, "getName(...)");
        return StringsKt.endsWith(name, ".wav", false);
    }

    public final int recordings(Pack p) {
        File dir;
        File[] listFiles;
        if (p == null || (dir = p.getDir()) == null || (listFiles = dir.listFiles(new FileFilter() { // from class: com.starpilot.starview.AmyVoice$$ExternalSyntheticLambda8
            @Override // java.io.FileFilter
            public final boolean accept(File file) {
                boolean recordings$lambda$20;
                recordings$lambda$20 = AmyVoice.recordings$lambda$20(file);
                return recordings$lambda$20;
            }
        })) == null) {
            return 0;
        }
        return listFiles.length;
    }

    public final void play(AmySynth.Style style, final List<String> keys) {
        Intrinsics.checkNotNullParameter(style, "style");
        Intrinsics.checkNotNullParameter(keys, "keys");
        if (getInstalled()) {
            playWith(style, new Function0() { // from class: com.starpilot.starview.AmyVoice$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    List play$lambda$22;
                    play$lambda$22 = AmyVoice.play$lambda$22(keys);
                    return play$lambda$22;
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final List play$lambda$22(List $keys) {
        List $this$mapNotNull$iv = $keys;
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv$iv : $this$mapNotNull$iv) {
            String it = (String) element$iv$iv$iv;
            float[] clip = INSTANCE.clip(it);
            if (clip != null) {
                destination$iv$iv.add(clip);
            }
        }
        return (List) destination$iv$iv;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final List playClips$lambda$23(List $clips) {
        return $clips;
    }

    public final void playClips(AmySynth.Style style, final List<float[]> clips) {
        Intrinsics.checkNotNullParameter(style, "style");
        Intrinsics.checkNotNullParameter(clips, "clips");
        playWith(style, new Function0() { // from class: com.starpilot.starview.AmyVoice$$ExternalSyntheticLambda7
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                List playClips$lambda$23;
                playClips$lambda$23 = AmyVoice.playClips$lambda$23(clips);
                return playClips$lambda$23;
            }
        });
    }

    private final void playWith(final AmySynth.Style style, final Function0<? extends List<float[]>> clips) {
        gen++;
        final int g = gen;
        busyUntil = System.currentTimeMillis() + 3000;
        exec.execute(new Runnable() { // from class: com.starpilot.starview.AmyVoice$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                AmyVoice.playWith$lambda$24(g, clips, style);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void playWith$lambda$24(int $g, Function0 $clips, AmySynth.Style $style) {
        if ($g != gen) {
            return;
        }
        try {
            List cs = (List) $clips.invoke();
            float[] v = cs.isEmpty() ? new float[0] : AmySynth.INSTANCE.voice(cs, Speech.INSTANCE.getSpeed(), Speech.INSTANCE.getPitch());
            short[] pcm = AmySynth.INSTANCE.render($style, v, Speech.INSTANCE.getChime(), Speech.INSTANCE.getPad());
            if ($g != gen) {
                return;
            }
            INSTANCE.start(pcm, $g);
        } catch (Exception e) {
            if ($g == gen) {
                busyUntil = 0L;
            }
        }
    }

    public final void ping() {
        playWith(AmySynth.Style.PING, new Function0() { // from class: com.starpilot.starview.AmyVoice$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                List emptyList;
                emptyList = CollectionsKt.emptyList();
                return emptyList;
            }
        });
    }

    public final void stop() {
        gen++;
        demoGen++;
        release();
        busyUntil = 0L;
    }

    public final void demo() {
        stop();
        int dg = demoGen;
        List seq = CollectionsKt.listOf(new Pair[]{TuplesKt.to(AmySynth.Style.ASK, AmyPrompts.INSTANCE.ask(1, "exit on the right in 0.6 mi - 2 lanes to go")), TuplesKt.to(AmySynth.Style.SHORT, CollectionsKt.listOf("sig_right")), TuplesKt.to(AmySynth.Style.SHORT, CollectionsKt.listOf("changing")), TuplesKt.to(AmySynth.Style.DONE, CollectionsKt.listOf("done"))});
        demo$step(dg, seq, 0);
    }

    private static final void demo$step(final int dg, final List<? extends Pair<? extends AmySynth.Style, ? extends List<String>>> list, final int i) {
        if (dg != demoGen || i >= list.size()) {
            return;
        }
        INSTANCE.play(list.get(i).getFirst(), list.get(i).getSecond());
        main.postDelayed(new Runnable() { // from class: com.starpilot.starview.AmyVoice$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                AmyVoice.demo$step$next(dg, i, list);
            }
        }, 400L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void demo$step$next(final int dg, final int $i, final List<? extends Pair<? extends AmySynth.Style, ? extends List<String>>> list) {
        Handler handler;
        Runnable runnable;
        long j;
        if (dg != demoGen) {
            return;
        }
        if (busy$default(INSTANCE, 0L, 1, null)) {
            handler = main;
            runnable = new Runnable() { // from class: com.starpilot.starview.AmyVoice$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    AmyVoice.demo$step$next(dg, $i, list);
                }
            };
            j = 100;
        } else {
            handler = main;
            runnable = new Runnable() { // from class: com.starpilot.starview.AmyVoice$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    AmyVoice.demo$step$next$lambda$27($i, dg, list);
                }
            };
            j = 700;
        }
        handler.postDelayed(runnable, j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void demo$step$next$lambda$27(int $i, int $dg, List $seq) {
        demo$step($dg, $seq, $i + 1);
    }

    private final void release() {
        AudioTrack t = track;
        track = null;
        if (t != null) {
            try {
                t.stop();
            } catch (Exception e) {
            }
        }
        if (t != null) {
            try {
                t.release();
            } catch (Exception e2) {
            }
        }
    }

    private final void start(short[] pcm, final int g) {
        release();
        final AudioTrack t = new AudioTrack.Builder().setAudioAttributes(new AudioAttributes.Builder().setUsage(4).setContentType(4).build()).setAudioFormat(new AudioFormat.Builder().setEncoding(2).setSampleRate(48000).setChannelMask(12).build()).setTransferMode(0).setBufferSizeInBytes(pcm.length * 2).build();
        Intrinsics.checkNotNullExpressionValue(t, "build(...)");
        t.write(pcm, 0, pcm.length);
        track = t;
        long ms = (((long) (pcm.length / 2)) * 1000L) / 48000L;
        busyUntil = System.currentTimeMillis() + ms;
        t.play();
        main.postDelayed(new Runnable() { // from class: com.starpilot.starview.AmyVoice$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                AmyVoice.start$lambda$29(g, t);
            }
        }, 300 + ms);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void start$lambda$29(int $g, AudioTrack $t) {
        if ($g == gen && track == $t) {
            INSTANCE.release();
        }
    }
}
