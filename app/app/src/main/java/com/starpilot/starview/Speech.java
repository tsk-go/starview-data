package com.starpilot.starview;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.os.Handler;
import android.os.Looper;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.MatchResult;
import kotlin.text.Regex;
import kotlin.text.StringsKt;

/* compiled from: Speech.kt */
public final class Speech {
    public static final Speech INSTANCE = new Speech();
    public static final float MAX_SPEED = 1.8f;
    public static final float MIN_SPEED = 0.6f;
    private static Context app;
    private static String pending;
    private static volatile boolean ready;
    private static TextToSpeech tts;

    private Speech() {
    }

    public final boolean getReady() {
        return ready;
    }

    private final SharedPreferences getPrefs() {
        Context context = app;
        if (context == null) {
            Intrinsics.throwUninitializedPropertyAccessException("app");
            context = null;
        }
        return context.getSharedPreferences("speech", 0);
    }

    public final boolean getEnabled() {
        return getPrefs().getBoolean("on", true);
    }

    public final void setEnabled(boolean v) {
        getPrefs().edit().putBoolean("on", v).apply();
    }

    public final String getVoiceName() {
        String string = getPrefs().getString("voice", "");
        return string == null ? "" : string;
    }

    public final void setVoiceName(String v) {
        Intrinsics.checkNotNullParameter(v, "v");
        getPrefs().edit().putString("voice", v).apply();
        setUseAmy(false);
        applyVoice();
    }

    public final float getSpeed() {
        return getPrefs().getFloat("speed", 1.0f);
    }

    public final void setSpeed(float v) {
        getPrefs().edit().putFloat("speed", RangesKt.coerceIn(v, 0.6f, 1.8f)).apply();
        TextToSpeech textToSpeech = tts;
        if (textToSpeech != null) {
            textToSpeech.setSpeechRate(getRate());
        }
    }

    public final float getRate() {
        return getSpeed() * 1.05f;
    }

    public final float getPitch() {
        return getPrefs().getFloat("pitch", 0.0f);
    }

    public final void setPitch(float v) {
        getPrefs().edit().putFloat("pitch", RangesKt.coerceIn(v, -6.0f, 6.0f)).apply();
    }

    public final float getChime() {
        return getPrefs().getFloat("chime", 1.0f);
    }

    public final void setChime(float v) {
        getPrefs().edit().putFloat("chime", RangesKt.coerceIn(v, 0.0f, 2.0f)).apply();
    }

    public final float getPad() {
        return getPrefs().getFloat("pad", 1.0f);
    }

    public final void setPad(float v) {
        getPrefs().edit().putFloat("pad", RangesKt.coerceIn(v, 0.0f, 2.0f)).apply();
    }

    public final boolean getUseAmy() {
        return Intrinsics.areEqual(getPrefs().getString("engine", "amy"), "amy");
    }

    public final void setUseAmy(boolean v) {
        getPrefs().edit().putString("engine", v ? "amy" : "tts").apply();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void init$default(Speech speech, Context context, Function0 function0, int i, Object obj) {
        if ((i & 2) != 0) {
            function0 = null;
        }
        speech.init(context, function0);
    }

    public final void init(Context ctx, final Function0<Unit> then) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        AmyVoice.INSTANCE.init(ctx);
        if (tts != null) {
            if (!ready || then == null) {
                return;
            }
            then.invoke();
            return;
        }
        app = ctx.getApplicationContext();
        Context context = app;
        if (context == null) {
            Intrinsics.throwUninitializedPropertyAccessException("app");
            context = null;
        }
        tts = new TextToSpeech(context, new TextToSpeech.OnInitListener() { // from class: com.starpilot.starview.Speech$$ExternalSyntheticLambda3
            @Override // android.speech.tts.TextToSpeech.OnInitListener
            public final void onInit(int i) {
                Speech.init$lambda$1(Function0.this, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void init$lambda$1(Function0 $then, int status) {
        ready = status == 0;
        if (ready) {
            TextToSpeech textToSpeech = tts;
            if (textToSpeech != null) {
                textToSpeech.setAudioAttributes(new AudioAttributes.Builder().setUsage(4).setContentType(1).build());
            }
            TextToSpeech textToSpeech2 = tts;
            if (textToSpeech2 != null) {
                textToSpeech2.setLanguage(Locale.US);
            }
            INSTANCE.applyVoice();
            TextToSpeech textToSpeech3 = tts;
            if (textToSpeech3 != null) {
                textToSpeech3.setSpeechRate(INSTANCE.getRate());
            }
            String it = pending;
            if (it != null) {
                speak$default(INSTANCE, it, false, 2, null);
            }
            pending = null;
            if ($then != null) {
                $then.invoke();
            }
        }
    }

    private final void applyVoice() {
        Iterable voices;
        Object element$iv;
        TextToSpeech t = tts;
        if (t == null) {
            return;
        }
        String name = getVoiceName();
        if ((name.length() == 0) || (voices = t.getVoices()) == null) {
            return;
        }
        Iterable $this$firstOrNull$iv = voices;
        Iterator it = $this$firstOrNull$iv.iterator();
        while (true) {
            if (!it.hasNext()) {
                element$iv = null;
                break;
            } else {
                element$iv = it.next();
                if (Intrinsics.areEqual(((Voice) element$iv).getName(), name)) {
                    break;
                }
            }
        }
        Voice it2 = (Voice) element$iv;
        if (it2 != null) {
            t.setVoice(it2);
        }
    }

    public final List<Voice> voices() {
        Iterable emptySet;
        TextToSpeech textToSpeech = tts;
        if (textToSpeech == null || (emptySet = textToSpeech.getVoices()) == null) {
            emptySet = SetsKt.emptySet();
        }
        Iterable $this$filter$iv = emptySet;
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            Voice it = (Voice) element$iv$iv;
            if (Intrinsics.areEqual(it.getLocale().getLanguage(), "en")) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        return CollectionsKt.sortedWith((List) destination$iv$iv, ComparisonsKt.compareBy(new Function1() { // from class: com.starpilot.starview.Speech$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Comparable voices$lambda$5;
                voices$lambda$5 = Speech.voices$lambda$5((Voice) obj);
                return voices$lambda$5;
            }
        }, new Function1() { // from class: com.starpilot.starview.Speech$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Comparable voices$lambda$6;
                voices$lambda$6 = Speech.voices$lambda$6((Voice) obj);
                return voices$lambda$6;
            }
        }, new Function1() { // from class: com.starpilot.starview.Speech$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Comparable voices$lambda$7;
                voices$lambda$7 = Speech.voices$lambda$7((Voice) obj);
                return voices$lambda$7;
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Comparable voices$lambda$5(Voice it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return Boolean.valueOf(it.isNetworkConnectionRequired());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Comparable voices$lambda$6(Voice it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return Boolean.valueOf(!Intrinsics.areEqual(it.getLocale().getCountry(), "US"));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Comparable voices$lambda$7(Voice it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return it.getName();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
     */
    /* JADX WARN: Removed duplicated region for block: B:10:0x0096  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0099  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final String label(Voice v) {
        String where;
        Intrinsics.checkNotNullParameter(v, "v");
        String country = v.getLocale().getCountry();
        if (country != null) {
            switch (country.hashCode()) {
                case 2100:
                    if (country.equals("AU")) {
                        where = "Australian";
                        break;
                    }
                    break;
                case 2142:
                    if (country.equals("CA")) {
                        where = "Canadian";
                        break;
                    }
                    break;
                case 2267:
                    if (country.equals("GB")) {
                        where = "British";
                        break;
                    }
                    break;
                case 2341:
                    if (country.equals("IN")) {
                        where = "Indian";
                        break;
                    }
                    break;
                case 2489:
                    if (country.equals("NG")) {
                        where = "Nigerian";
                        break;
                    }
                    break;
                case 2718:
                    if (country.equals("US")) {
                        where = "American";
                        break;
                    }
                    break;
            }
            String name = v.getName();
            Intrinsics.checkNotNullExpressionValue(name, "getName(...)");
            String name2 = v.getName();
            Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
            String id = StringsKt.substringBefore$default(StringsKt.substringAfter(name, "-x-", name2), "-", (String) null, 2, (Object) null);
            String upperCase = id.toUpperCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(upperCase, "toUpperCase(...)");
            return where + " · " + upperCase + (!v.isNetworkConnectionRequired() ? " · needs internet" : "");
        }
        where = v.getLocale().getDisplayCountry();
        String name3 = v.getName();
        Intrinsics.checkNotNullExpressionValue(name3, "getName(...)");
        String name22 = v.getName();
        Intrinsics.checkNotNullExpressionValue(name22, "getName(...)");
        String id2 = StringsKt.substringBefore$default(StringsKt.substringAfter(name3, "-x-", name22), "-", (String) null, 2, (Object) null);
        String upperCase2 = id2.toUpperCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(upperCase2, "toUpperCase(...)");
        return where + " · " + upperCase2 + (!v.isNetworkConnectionRequired() ? " · needs internet" : "");
    }

    public static /* synthetic */ void speak$default(Speech speech, String str, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = true;
        }
        speech.speak(str, z);
    }

    public final void speak(String text, boolean flush) {
        Intrinsics.checkNotNullParameter(text, "text");
        if (getEnabled()) {
            TextToSpeech textToSpeech = tts;
            if (textToSpeech == null || !ready) {
                pending = text;
                return;
            }
            textToSpeech.speak(text, !flush ? 1 : 0, null, "sv" + System.nanoTime());
        }
    }

    public final void preview(Voice v, String text) {
        Intrinsics.checkNotNullParameter(v, "v");
        Intrinsics.checkNotNullParameter(text, "text");
        final TextToSpeech t = tts;
        if (t == null) {
            return;
        }
        final Voice keep = t.getVoice();
        t.setVoice(v);
        t.setSpeechRate(getRate());
        t.speak(text, 0, null, "preview");
        if (keep != null) {
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.starpilot.starview.Speech$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    Speech.preview$lambda$9$lambda$8(t, keep);
                }
            }, 6000L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void preview$lambda$9$lambda$8(TextToSpeech $t, Voice $k) {
        if (INSTANCE.getVoiceName().length() > 0) {
            INSTANCE.applyVoice();
        } else {
            $t.setVoice($k);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CharSequence spoken$lambda$10(MatchResult it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return ((Object) it.getGroupValues().get(1)) + " miles";
    }

    public final String spoken(String s) {
        Intrinsics.checkNotNullParameter(s, "s");
        return StringsKt.replace$default(new Regex("(\\d+(?:\\.\\d+)?) km\\b").replace(new Regex("(\\d+) ft\\b").replace(new Regex("(\\d+(?:\\.\\d+)?) mi\\b").replace(s, new Function1() { // from class: com.starpilot.starview.Speech$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                CharSequence spoken$lambda$10;
                spoken$lambda$10 = Speech.spoken$lambda$10((MatchResult) obj);
                return spoken$lambda$10;
            }
        }), new Function1() { // from class: com.starpilot.starview.Speech$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                CharSequence spoken$lambda$11;
                spoken$lambda$11 = Speech.spoken$lambda$11((MatchResult) obj);
                return spoken$lambda$11;
            }
        }), new Function1() { // from class: com.starpilot.starview.Speech$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                CharSequence spoken$lambda$12;
                spoken$lambda$12 = Speech.spoken$lambda$12((MatchResult) obj);
                return spoken$lambda$12;
            }
        }), " - ", ", ", false, 4, (Object) null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CharSequence spoken$lambda$11(MatchResult it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return ((Object) it.getGroupValues().get(1)) + " feet";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CharSequence spoken$lambda$12(MatchResult it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return ((Object) it.getGroupValues().get(1)) + " kilometers";
    }
}
