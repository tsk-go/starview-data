package com.starpilot.starview;

import android.content.Context;
import android.net.Uri;
import com.k2fsa.sherpa.onnx.GeneratedAudio;
import com.k2fsa.sherpa.onnx.OfflineTts;
import com.k2fsa.sherpa.onnx.OfflineTtsConfig;
import com.k2fsa.sherpa.onnx.OfflineTtsModelConfig;
import com.k2fsa.sherpa.onnx.OfflineTtsVitsModelConfig;
import com.starpilot.starview.VoiceStudio;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.io.ByteStreamsKt;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.io.TextStreamsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import kotlin.text.Charsets;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.json.JSONObject;

/* compiled from: VoiceStudio.kt */
public final class VoiceStudio {
    private static final String HF = "https://huggingface.co/rhasspy/piper-voices/resolve/main/";
    private static volatile boolean loaded;
    private static OfflineTts tts;
    public static final VoiceStudio INSTANCE = new VoiceStudio();

    /* renamed from: http$delegate, reason: from kotlin metadata */
    private static final Lazy http = LazyKt.lazy(new Function0() { // from class: com.starpilot.starview.VoiceStudio$$ExternalSyntheticLambda3
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            OkHttpClient http_delegate$lambda$0;
            http_delegate$lambda$0 = VoiceStudio.http_delegate$lambda$0();
            return http_delegate$lambda$0;
        }
    });
    private static String ttsKey = "";

    private VoiceStudio() {
    }

    private final OkHttpClient getHttp() {
        return (OkHttpClient) http.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final OkHttpClient http_delegate$lambda$0() {
        return new OkHttpClient.Builder().connectTimeout(20L, TimeUnit.SECONDS).readTimeout(60L, TimeUnit.SECONDS).build();
    }

    private final File kit(Context c) {
        return new File(c.getFilesDir(), "voicekit");
    }

    public final boolean engineInstalled(Context c) {
        Intrinsics.checkNotNullParameter(c, "c");
        return new File(kit(c), "lib/libsherpa-onnx-jni.so").isFile() && new File(kit(c), "lib/libonnxruntime.so").isFile() && new File(kit(c), "espeak-ng-data/phontab").isFile();
    }

    public final synchronized String load(Context c) {
        Intrinsics.checkNotNullParameter(c, "c");
        String str = null;
        if (loaded) {
            return null;
        }
        if (!engineInstalled(c)) {
            return "The voice engine isn't installed";
        }
        try {
            System.load(new File(kit(c), "lib/libonnxruntime.so").getAbsolutePath());
            System.load(new File(kit(c), "lib/libsherpa-onnx-jni.so").getAbsolutePath());
            loaded = true;
        } catch (Throwable e) {
            str = "Couldn't start the voice engine: " + e.getMessage();
        }
        return str;
    }

    public final String installEngine(Context c, Uri uri, Function1<? super String, Unit> progress) {
        Throwable th;
        ZipInputStream z;
        InputStream ins;
        Throwable th2;
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(uri, "uri");
        Intrinsics.checkNotNullParameter(progress, "progress");
        if (loaded) {
            return "Already installed and running - restart the app to replace it";
        }
        File tmp = new File(c.getFilesDir(), "voicekit.tmp");
        FilesKt.deleteRecursively(tmp);
        tmp.mkdirs();
        int n = 0;
        try {
            InputStream openInputStream = c.getContentResolver().openInputStream(uri);
            if (openInputStream == null) {
                return "Couldn't open that file";
            }
            InputStream inputStream = openInputStream;
            try {
                InputStream ins2 = inputStream;
                ZipInputStream zipInputStream = new ZipInputStream(ins2 instanceof BufferedInputStream ? (BufferedInputStream) ins2 : new BufferedInputStream(ins2, 8192));
                try {
                    z = zipInputStream;
                } catch (Throwable th3) {
                    th = th3;
                }
                while (true) {
                    ZipEntry e = z.getNextEntry();
                    if (e == null) {
                        break;
                    }
                    String name = e.getName();
                    Intrinsics.checkNotNullExpressionValue(name, "getName(...)");
                    String rel = StringsKt.removePrefix(name, (CharSequence) "voicekit/");
                    if ((rel.length() == 0) || StringsKt.contains((CharSequence) rel, (CharSequence) "..", false)) {
                        ins = ins2;
                    } else if (e.isDirectory()) {
                        ins = ins2;
                    } else {
                        File f = new File(tmp, rel);
                        File parentFile = f.getParentFile();
                        if (parentFile != null) {
                            try {
                                parentFile.mkdirs();
                            } catch (Throwable th4) {
                                th = th4;
                            }
                        }
                        FileOutputStream fileOutputStream = new FileOutputStream(f);
                        try {
                            FileOutputStream it = fileOutputStream;
                            ins = ins2;
                            try {
                                ByteStreamsKt.copyTo(z, it, 8192);
                                try {
                                    CloseableKt.closeFinally(fileOutputStream, null);
                                    n++;
                                    if (n % 50 == 0) {
                                        progress.invoke("Unpacking… " + n + " files");
                                    }
                                } catch (Throwable th5) {
                                    th = th5;
                                }
                            } catch (Throwable th6) {
                                th2 = th6;
                                try {
                                    throw th2;
                                } catch (Throwable th7) {
                                    CloseableKt.closeFinally(fileOutputStream, th2);
                                    throw th7;
                                }
                            }
                        } catch (Throwable th8) {
                            th2 = th8;
                        }
                    }
                    ins2 = ins;
                    th = th4;
                    try {
                        throw th;
                    } catch (Throwable th9) {
                        CloseableKt.closeFinally(zipInputStream, th);
                        throw th9;
                    }
                }
                Unit unit = Unit.INSTANCE;
                CloseableKt.closeFinally(zipInputStream, null);
                Unit unit2 = Unit.INSTANCE;
                CloseableKt.closeFinally(inputStream, null);
                if (!new File(tmp, "lib/libsherpa-onnx-jni.so").isFile() || !new File(tmp, "espeak-ng-data/phontab").isFile()) {
                    FilesKt.deleteRecursively(tmp);
                    return "That zip isn't StarView-Voice-engine.zip";
                }
                FilesKt.deleteRecursively(kit(c));
                if (tmp.renameTo(kit(c))) {
                    return null;
                }
                return "Couldn't save it";
            } finally {
            }
        } catch (Exception e2) {
            FilesKt.deleteRecursively(tmp);
            return "Not the engine zip (" + e2.getMessage() + ")";
        }
    }

    /* compiled from: VoiceStudio.kt */
    public static final /* data */ class Voice {
        private final long bytes;
        private final String json;
        private final String key;
        private final String name;
        private final String onnx;
        private final String quality;
        private final String region;
        private final List<String> speakerNames;
        private final int speakers;

        /* renamed from: component1, reason: from getter */
        public final String getKey() {
            return this.key;
        }

        /* renamed from: component2, reason: from getter */
        public final String getName() {
            return this.name;
        }

        /* renamed from: component3, reason: from getter */
        public final String getRegion() {
            return this.region;
        }

        /* renamed from: component4, reason: from getter */
        public final String getQuality() {
            return this.quality;
        }

        /* renamed from: component5, reason: from getter */
        public final int getSpeakers() {
            return this.speakers;
        }

        public final List<String> component6() {
            return this.speakerNames;
        }

        /* renamed from: component7, reason: from getter */
        public final String getOnnx() {
            return this.onnx;
        }

        /* renamed from: component8, reason: from getter */
        public final String getJson() {
            return this.json;
        }

        /* renamed from: component9, reason: from getter */
        public final long getBytes() {
            return this.bytes;
        }

        public final Voice copy(String key, String name, String region, String quality, int speakers, List<String> speakerNames, String onnx, String json, long bytes) {
            Intrinsics.checkNotNullParameter(key, "key");
            Intrinsics.checkNotNullParameter(name, "name");
            Intrinsics.checkNotNullParameter(region, "region");
            Intrinsics.checkNotNullParameter(quality, "quality");
            Intrinsics.checkNotNullParameter(speakerNames, "speakerNames");
            Intrinsics.checkNotNullParameter(onnx, "onnx");
            Intrinsics.checkNotNullParameter(json, "json");
            return new Voice(key, name, region, quality, speakers, speakerNames, onnx, json, bytes);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Voice)) {
                return false;
            }
            Voice voice = (Voice) other;
            return Intrinsics.areEqual(this.key, voice.key) && Intrinsics.areEqual(this.name, voice.name) && Intrinsics.areEqual(this.region, voice.region) && Intrinsics.areEqual(this.quality, voice.quality) && this.speakers == voice.speakers && Intrinsics.areEqual(this.speakerNames, voice.speakerNames) && Intrinsics.areEqual(this.onnx, voice.onnx) && Intrinsics.areEqual(this.json, voice.json) && this.bytes == voice.bytes;
        }

        public int hashCode() {
            return (((((((((((((((this.key.hashCode() * 31) + this.name.hashCode()) * 31) + this.region.hashCode()) * 31) + this.quality.hashCode()) * 31) + Integer.hashCode(this.speakers)) * 31) + this.speakerNames.hashCode()) * 31) + this.onnx.hashCode()) * 31) + this.json.hashCode()) * 31) + Long.hashCode(this.bytes);
        }

        public String toString() {
            return "Voice(key=" + this.key + ", name=" + this.name + ", region=" + this.region + ", quality=" + this.quality + ", speakers=" + this.speakers + ", speakerNames=" + this.speakerNames + ", onnx=" + this.onnx + ", json=" + this.json + ", bytes=" + this.bytes + ")";
        }

        public Voice(String key, String name, String region, String quality, int speakers, List<String> speakerNames, String onnx, String json, long bytes) {
            Intrinsics.checkNotNullParameter(key, "key");
            Intrinsics.checkNotNullParameter(name, "name");
            Intrinsics.checkNotNullParameter(region, "region");
            Intrinsics.checkNotNullParameter(quality, "quality");
            Intrinsics.checkNotNullParameter(speakerNames, "speakerNames");
            Intrinsics.checkNotNullParameter(onnx, "onnx");
            Intrinsics.checkNotNullParameter(json, "json");
            this.key = key;
            this.name = name;
            this.region = region;
            this.quality = quality;
            this.speakers = speakers;
            this.speakerNames = speakerNames;
            this.onnx = onnx;
            this.json = json;
            this.bytes = bytes;
        }

        public final String getKey() {
            return this.key;
        }

        public final String getName() {
            return this.name;
        }

        public final String getQuality() {
            return this.quality;
        }

        public final String getRegion() {
            return this.region;
        }

        public final int getSpeakers() {
            return this.speakers;
        }

        public final long getBytes() {
            return this.bytes;
        }

        public final String getJson() {
            return this.json;
        }

        public final String getOnnx() {
            return this.onnx;
        }

        public final List<String> getSpeakerNames() {
            return this.speakerNames;
        }

        public final String getAccent() {
            String str = this.region;
            return Intrinsics.areEqual(str, "US") ? "American" : Intrinsics.areEqual(str, "GB") ? "British" : this.region;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final CharSequence _get_title_$lambda$1(String it) {
            String str;
            Intrinsics.checkNotNullParameter(it, "it");
            if (it.length() > 0) {
                StringBuilder sb = new StringBuilder();
                char c = it.charAt(0);
                String valueOf = String.valueOf(c);
                Intrinsics.checkNotNull(valueOf, "null cannot be cast to non-null type java.lang.String");
                String upperCase = valueOf.toUpperCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue(upperCase, "toUpperCase(...)");
                StringBuilder append = sb.append((Object) upperCase);
                String substring = it.substring(1);
                Intrinsics.checkNotNullExpressionValue(substring, "substring(...)");
                str = append.append(substring).toString();
            } else {
                str = it;
            }
            return str;
        }

        public final String getTitle() {
            return CollectionsKt.joinToString(StringsKt.split((CharSequence) StringsKt.replace(this.name, '_', ' ', false), new char[]{' '}, false, 0), " ", "", "", -1, "...", new Function1() { // from class: com.starpilot.starview.VoiceStudio$Voice$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    CharSequence _get_title_$lambda$1;
                    _get_title_$lambda$1 = VoiceStudio.Voice._get_title_$lambda$1((String) obj);
                    return _get_title_$lambda$1;
                }
            });
        }
    }

    private final File catFile(Context c) {
        return new File(c.getFilesDir(), "piper/voices.json");
    }

    public static /* synthetic */ List catalog$default(VoiceStudio voiceStudio, Context context, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return voiceStudio.catalog(context, z);
    }

    public final List<Voice> catalog(Context c, boolean refresh) {
        Intrinsics.checkNotNullParameter(c, "c");
        File f = catFile(c);
        int i = 2;
        ArrayList names = null;
        if (refresh || !f.isFile()) {
            File parentFile = f.getParentFile();
            if (parentFile != null) {
                parentFile.mkdirs();
            }
            Response execute = getHttp().newCall(new Request.Builder().url("https://huggingface.co/rhasspy/piper-voices/resolve/main/voices.json").build()).execute();
            try {
                Response r = execute;
                if (!r.isSuccessful()) {
                    throw new IOException("Hugging Face said " + r.code());
                }
                ResponseBody body = r.body();
                Intrinsics.checkNotNull(body);
                FilesKt.writeText(f, body.string(), kotlin.text.Charsets.UTF_8);
                Unit unit = Unit.INSTANCE;
                CloseableKt.closeFinally(execute, null);
            } finally {
            }
        }
        JSONObject all = new JSONObject(FilesKt.readText(f, kotlin.text.Charsets.UTF_8));
        ArrayList out = new ArrayList();
        Iterator<String> keys = all.keys();
        Intrinsics.checkNotNullExpressionValue(keys, "keys(...)");
        while (keys.hasNext()) {
            String k = keys.next();
            JSONObject v = all.getJSONObject(k);
            JSONObject lang = v.getJSONObject("language");
            if (Intrinsics.areEqual(lang.optString("family"), "en")) {
                JSONObject files = v.getJSONObject("files");
                Iterator<String> keys2 = files.keys();
                Intrinsics.checkNotNullExpressionValue(keys2, "keys(...)");
                String onnx = "";
                String json = "";
                long bytes = 0;
                while (keys2.hasNext()) {
                    String p = keys2.next();
                    Intrinsics.checkNotNull(p);
                    if (StringsKt.endsWith(p, ".onnx", false)) {
                        onnx = p;
                        bytes = files.getJSONObject(p).optLong("size_bytes");
                    }
                    if (StringsKt.endsWith(p, ".onnx.json", false)) {
                        json = p;
                        names = null;
                        i = 2;
                    } else {
                        names = null;
                        i = 2;
                    }
                }
                JSONObject sm = v.optJSONObject("speaker_id_map");
                ArrayList names2 = new ArrayList();
                if (sm != null) {
                    Iterator<String> keys3 = sm.keys();
                    Intrinsics.checkNotNullExpressionValue(keys3, "keys(...)");
                    while (keys3.hasNext()) {
                        String s = keys3.next();
                        names2.add(TuplesKt.to(s, Integer.valueOf(sm.getInt(s))));
                    }
                }
                Intrinsics.checkNotNull(k);
                String optString = v.optString("name");
                Intrinsics.checkNotNullExpressionValue(optString, "optString(...)");
                String optString2 = lang.optString("region");
                Intrinsics.checkNotNullExpressionValue(optString2, "optString(...)");
                String optString3 = v.optString("quality");
                Intrinsics.checkNotNullExpressionValue(optString3, "optString(...)");
                int optInt = v.optInt("num_speakers", 1);
                ArrayList $this$sortedBy$iv = names2;
                Iterable $this$map$iv = CollectionsKt.sortedWith($this$sortedBy$iv, new Comparator() { // from class: com.starpilot.starview.VoiceStudio$catalog$$inlined$sortedBy$1
                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // java.util.Comparator
                    public final int compare(T t, T t2) {
                        Pair it = (Pair) t;
                        Integer num = (Integer) it.getSecond();
                        Pair it2 = (Pair) t2;
                        return ComparisonsKt.compareValues(num, (Integer) it2.getSecond());
                    }
                });
                Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                for (Object item$iv$iv : $this$map$iv) {
                    Pair it = (Pair) item$iv$iv;
                    destination$iv$iv.add((String) it.getFirst());
                    $this$map$iv = $this$map$iv;
                }
                out.add(new Voice(k, optString, optString2, optString3, optInt, (List) destination$iv$iv, onnx, json, bytes));
                names = null;
                i = 2;
            } else {
                i = 2;
            }
        }
        final Map q = MapsKt.mapOf(TuplesKt.to("high", 0), TuplesKt.to("medium", 1), TuplesKt.to("low", 2), TuplesKt.to("x_low", 3));
        return CollectionsKt.sortedWith(out, ComparisonsKt.compareBy(new Function1() { // from class: com.starpilot.starview.VoiceStudio$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Comparable catalog$lambda$7;
                catalog$lambda$7 = VoiceStudio.catalog$lambda$7((VoiceStudio.Voice) obj);
                return catalog$lambda$7;
            }
        }, new Function1() { // from class: com.starpilot.starview.VoiceStudio$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Comparable catalog$lambda$8;
                catalog$lambda$8 = VoiceStudio.catalog$lambda$8((VoiceStudio.Voice) obj);
                return catalog$lambda$8;
            }
        }, new Function1() { // from class: com.starpilot.starview.VoiceStudio$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Comparable catalog$lambda$9;
                catalog$lambda$9 = VoiceStudio.catalog$lambda$9(q, (VoiceStudio.Voice) obj);
                return catalog$lambda$9;
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Comparable catalog$lambda$7(Voice it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return Boolean.valueOf(!Intrinsics.areEqual(it.getRegion(), "US"));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Comparable catalog$lambda$8(Voice it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return it.getName();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Comparable catalog$lambda$9(Map $q, Voice it) {
        Intrinsics.checkNotNullParameter(it, "it");
        int i = (Integer) $q.get(it.getQuality());
        if (i == null) {
            i = 9;
        }
        return i;
    }

    private final File mdir(Context c, String key) {
        return new File(c.getFilesDir(), "piper/" + key);
    }

    public final boolean downloaded(Context c, String key) {
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(key, "key");
        return new File(mdir(c, key), "model.onnx").isFile() && new File(mdir(c, key), "tokens.txt").isFile();
    }

    public final void deleteModel(Context c, String key) {
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(key, "key");
        FilesKt.deleteRecursively(mdir(c, key));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean downloadedBytes$lambda$10(File it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return it.isFile() && Intrinsics.areEqual(it.getName(), "model.onnx");
    }

    public final long downloadedBytes(Context c) {
        Intrinsics.checkNotNullParameter(c, "c");
        long j = 0;
        for (File it : SequencesKt.filter(FilesKt.walk(new File(c.getFilesDir(), "piper"), kotlin.io.FileWalkDirection.TOP_DOWN), new Function1() { // from class: com.starpilot.starview.VoiceStudio$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                boolean downloadedBytes$lambda$10;
                downloadedBytes$lambda$10 = VoiceStudio.downloadedBytes$lambda$10((File) obj);
                return Boolean.valueOf(downloadedBytes$lambda$10);
            }
        })) {
            j += it.length();
        }
        return j;
    }

    public final String download(Context c, Voice v, AtomicBoolean cancel, Function2<? super Long, ? super Long, Unit> progress) {
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(v, "v");
        Intrinsics.checkNotNullParameter(cancel, "cancel");
        Intrinsics.checkNotNullParameter(progress, "progress");
        File mdir = mdir(c, v.getKey());
        mdir.mkdirs();
        File part = new File(mdir, "model.onnx.part");
        try {
            String cfgText;
            try (Response r = getHttp().newCall(new Request.Builder().url("https://huggingface.co/rhasspy/piper-voices/resolve/main/" + v.getJson()).build()).execute()) {
                if (!r.isSuccessful()) {
                    return "Hugging Face said " + r.code();
                }
                ResponseBody body = r.body();
                Intrinsics.checkNotNull(body);
                cfgText = body.string();
            }
            JSONObject cfg = new JSONObject(cfgText);
            try (Response r2 = getHttp().newCall(new Request.Builder().url("https://huggingface.co/rhasspy/piper-voices/resolve/main/" + v.getOnnx()).build()).execute()) {
                if (!r2.isSuccessful()) {
                    return "Hugging Face said " + r2.code();
                }
                ResponseBody body2 = r2.body();
                Intrinsics.checkNotNull(body2);
                long total = body2.contentLength() > 0 ? body2.contentLength() : v.getBytes();
                try (InputStream inp = body2.byteStream(); FileOutputStream out = new FileOutputStream(part)) {
                    byte[] buf = new byte[65536];
                    long got = 0;
                    long shown = 0;
                    while (true) {
                        if (cancel.get()) {
                            part.delete();
                            return "Cancelled";
                        }
                        int n = inp.read(buf);
                        if (n < 0) {
                            break;
                        }
                        out.write(buf, 0, n);
                        got += n;
                        if (got - shown > 524288) {
                            progress.invoke(Long.valueOf(got), Long.valueOf(total));
                            shown = got;
                        }
                    }
                    progress.invoke(Long.valueOf(got), Long.valueOf(total));
                }
            }
            FilesKt.appendBytes(part, PiperPrep.INSTANCE.metadataBytes(cfg));
            FilesKt.writeText(new File(mdir, "tokens.txt"), PiperPrep.INSTANCE.tokens(cfg), kotlin.text.Charsets.UTF_8);
            FilesKt.writeText(new File(mdir, "config.json"), cfgText, kotlin.text.Charsets.UTF_8);
            return part.renameTo(new File(mdir, "model.onnx")) ? null : "Couldn't save it";
        } catch (Exception e) {
            part.delete();
            return "Download failed: " + e.getMessage();
        }
    }

    /* compiled from: VoiceStudio.kt */
    public static final /* data */ class Style {
        private final float expressive;
        private final float pace;
        private final float rhythm;

        public Style() {
            this(0.0f, 0.0f, 0.0f, 7, null);
        }

        public static /* synthetic */ Style copy$default(Style style, float f, float f2, float f3, int i, Object obj) {
            if ((i & 1) != 0) {
                f = style.expressive;
            }
            if ((i & 2) != 0) {
                f2 = style.rhythm;
            }
            if ((i & 4) != 0) {
                f3 = style.pace;
            }
            return style.copy(f, f2, f3);
        }

        /* renamed from: component1, reason: from getter */
        public final float getExpressive() {
            return this.expressive;
        }

        /* renamed from: component2, reason: from getter */
        public final float getRhythm() {
            return this.rhythm;
        }

        /* renamed from: component3, reason: from getter */
        public final float getPace() {
            return this.pace;
        }

        public final Style copy(float expressive, float rhythm, float pace) {
            return new Style(expressive, rhythm, pace);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Style)) {
                return false;
            }
            Style style = (Style) other;
            return Float.compare(this.expressive, style.expressive) == 0 && Float.compare(this.rhythm, style.rhythm) == 0 && Float.compare(this.pace, style.pace) == 0;
        }

        public int hashCode() {
            return (((Float.hashCode(this.expressive) * 31) + Float.hashCode(this.rhythm)) * 31) + Float.hashCode(this.pace);
        }

        public String toString() {
            return "Style(expressive=" + this.expressive + ", rhythm=" + this.rhythm + ", pace=" + this.pace + ")";
        }

        public Style(float expressive, float rhythm, float pace) {
            this.expressive = expressive;
            this.rhythm = rhythm;
            this.pace = pace;
        }

        public /* synthetic */ Style(float f, float f2, float f3, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this((i & 1) != 0 ? 0.667f : f, (i & 2) != 0 ? 0.8f : f2, (i & 4) != 0 ? 1.08f : f3);
        }

        public final float getExpressive() {
            return this.expressive;
        }

        public final float getPace() {
            return this.pace;
        }

        public final float getRhythm() {
            return this.rhythm;
        }
    }

    private final synchronized OfflineTts engine(Context c, String key, Style st) {
        String it = load(c);
        if (it != null) {
            throw new IllegalStateException(it);
        }
        String want = key + "|" + st.getExpressive() + "|" + st.getRhythm() + "|" + st.getPace();
        OfflineTts it2 = tts;
        if (it2 != null) {
            if (Intrinsics.areEqual(ttsKey, want)) {
                return it2;
            }
            it2.release();
        }
        File d = mdir(c, key);
        String absolutePath = new File(d, "model.onnx").getAbsolutePath();
        Intrinsics.checkNotNullExpressionValue(absolutePath, "getAbsolutePath(...)");
        String absolutePath2 = new File(d, "tokens.txt").getAbsolutePath();
        Intrinsics.checkNotNullExpressionValue(absolutePath2, "getAbsolutePath(...)");
        String absolutePath3 = new File(kit(c), "espeak-ng-data").getAbsolutePath();
        Intrinsics.checkNotNullExpressionValue(absolutePath3, "getAbsolutePath(...)");
        OfflineTtsConfig cfg = new OfflineTtsConfig(new OfflineTtsModelConfig(new OfflineTtsVitsModelConfig(absolutePath, null, absolutePath2, absolutePath3, null, st.getExpressive(), st.getRhythm(), st.getPace(), 18, null), null, null, null, null, null, null, 4, false, "cpu", 382, null), null, null, 0, 0.0f, 30, null);
        OfflineTts it3 = new OfflineTts(null, cfg, 1, null);
        tts = it3;
        ttsKey = want;
        return it3;
    }

    public final synchronized void freeEngine() {
        OfflineTts offlineTts = tts;
        if (offlineTts != null) {
            offlineTts.release();
        }
        tts = null;
        ttsKey = "";
    }

    public final synchronized List<float[]> say(Context c, String key, int sid, Style st, List<String> texts) {
        Collection destination$iv$iv;
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(st, "st");
        Intrinsics.checkNotNullParameter(texts, "texts");
        OfflineTts t = engine(c, key, st);
        List<String> $this$map$iv = texts;
        destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        for (Object item$iv$iv : $this$map$iv) {
            String it = (String) item$iv$iv;
            GeneratedAudio a = t.generate(it, sid, 1.0f);
            destination$iv$iv.add(VoiceDsp.INSTANCE.clean(a.getSamples(), a.getSampleRate()));
        }
        return (List) destination$iv$iv;
    }

    public final List<Pair<String, String>> phrases(Context c) {
        Intrinsics.checkNotNullParameter(c, "c");
        InputStream open = c.getAssets().open("voice_phrases.txt");
        Intrinsics.checkNotNullExpressionValue(open, "open(...)");
        Reader inputStreamReader = new InputStreamReader(open, Charsets.UTF_8);
        Iterable $this$mapNotNull$iv = TextStreamsKt.readLines(inputStreamReader instanceof BufferedReader ? (BufferedReader) inputStreamReader : new BufferedReader(inputStreamReader, 8192));
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv$iv : $this$mapNotNull$iv) {
            String l = (String) element$iv$iv$iv;
            Iterable $this$mapNotNull$iv2 = $this$mapNotNull$iv;
            List it = StringsKt.split((CharSequence) l, new char[]{'|'}, false, 2);
            List list = it;
            int i = (it.size() != 2 || StringsKt.isBlank((CharSequence) it.get(0))) ? 0 : 1;
            Pair pair = null;
            if (i == 0) {
                list = null;
            }
            if (list != null) {
                List it2 = list;
                pair = TuplesKt.to(it2.get(0), it2.get(1));
            }
            if (pair != null) {
                destination$iv$iv.add(pair);
            }
            $this$mapNotNull$iv = $this$mapNotNull$iv2;
        }
        return (List) destination$iv$iv;
    }

    public final String make(Context c, Voice v, int sid, Style st, String label, AtomicBoolean cancel, Function2<? super Integer, ? super Integer, Unit> progress) {
        File tmp;
        List<Pair> ps;
        int i;
        int i2;
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(v, "v");
        Intrinsics.checkNotNullParameter(st, "st");
        Intrinsics.checkNotNullParameter(label, "label");
        Intrinsics.checkNotNullParameter(cancel, "cancel");
        Intrinsics.checkNotNullParameter(progress, "progress");
        String id = new Regex("[^A-Za-z0-9_-]").replace(v.getKey() + (v.getSpeakers() > 1 ? "_s" + sid : "") + "_" + (System.currentTimeMillis() / 1000), "_");
        String str = "voices/";
        File tmp2 = new File(c.getFilesDir(), "voices/" + id + ".tmp");
        FilesKt.deleteRecursively(tmp2);
        tmp2.mkdirs();
        try {
            ps = phrases(c);
            i = 0;
            i2 = 0;
        } catch (Throwable th) {
            e = th;
            tmp = tmp2;
        }
        for (Pair p : ps) {
            int i3 = i2;
            int i4 = i2 + 1;
            if (cancel.get()) {
                try {
                    FilesKt.deleteRecursively(tmp2);
                    return "Cancelled";
                } catch (Throwable th2) {
                    e = th2;
                    tmp = tmp2;
                }
            } else {
                int i5 = i;
                File tmp3 = tmp2;
                String str2 = str;
                try {
                    float[] clip = say(c, v.getKey(), sid, st, CollectionsKt.listOf(p.getSecond())).get(i5);
                    tmp = tmp3;
                    try {
                        FilesKt.writeBytes(new File(tmp, p.getFirst() + ".wav"), VoiceDsp.INSTANCE.wav16(clip, 24000));
                        progress.invoke(Integer.valueOf(i3 + 1), Integer.valueOf(ps.size()));
                        tmp2 = tmp;
                        i = i5;
                        i2 = i4;
                        str = str2;
                    } catch (Throwable th3) {
                        e = th3;
                    }
                } catch (Throwable th4) {
                    e = th4;
                    tmp = tmp3;
                }
            }
            try {
                FilesKt.deleteRecursively(tmp);
                return "Couldn't make the voice: " + e.getMessage();
            } finally {
                freeEngine();
            }
        }
        File tmp4 = tmp2;
        FilesKt.writeText(new File(tmp4, "label.txt"), label, kotlin.text.Charsets.UTF_8);
        File file = new File(tmp4, "style.json");
        String jSONObject = new JSONObject().put("voice", v.getKey()).put("speaker", sid).put("expressive", st.getExpressive()).put("rhythm", st.getRhythm()).put("pace", st.getPace()).toString();
        Intrinsics.checkNotNullExpressionValue(jSONObject, "toString(...)");
        FilesKt.writeText(file, jSONObject, kotlin.text.Charsets.UTF_8);
        File dst = new File(c.getFilesDir(), str + id);
        if (!tmp4.renameTo(dst)) {
            FilesKt.deleteRecursively(tmp4);
            return "Couldn't save it";
        }
        AmyVoice.INSTANCE.use("gen:" + id);
        freeEngine();
        return null;
    }
}
