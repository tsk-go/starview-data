package com.starpilot.starlock;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.google.mlkit.common.MlKitException;
import com.starpilot.starlock.SignalTestActivity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Triple;
import kotlin.Unit;
import kotlin.collections.ArrayDeque;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;

/* compiled from: SignalTestActivity.kt */
public final class SignalTestActivity extends Activity {
    private Integer last;
    private int left;
    private TextView live;
    private Phase phase;
    private String recording;
    private TextView results;
    private TextView stepView;
    private TextToSpeech tts;
    private final Handler main = new Handler(Looper.getMainLooper());
    private final LinkedHashMap<String, List<Integer>> samples = new LinkedHashMap<>();
    private boolean running = true;
    private final ArrayDeque<Phase> queue = new ArrayDeque<>();
    private final List<Triple<String, String, Integer>> steps = (List) CollectionsKt.listOf(new Triple[]{new Triple("inside", "1  Inside, doors closed", 20), new Triple("outside", "2  Outside, next to the closed driver's door", 20), new Triple("away", "3  Walking away (about 30 ft)", 30)});
    private final Runnable countdown = new Runnable() { // from class: com.starpilot.starlock.SignalTestActivity$countdown$1
        @Override // java.lang.Runnable
        public void run() {
            SignalTestActivity.Phase p;
            int i;
            TextView textView;
            int i2;
            int i3;
            Handler handler;
            p = SignalTestActivity.this.phase;
            if (p == null) {
                return;
            }
            i = SignalTestActivity.this.left;
            if (i <= 0) {
                SignalTestActivity.this.next();
                return;
            }
            textView = SignalTestActivity.this.stepView;
            if (textView == null) {
                Intrinsics.throwUninitializedPropertyAccessException("stepView");
                textView = null;
            }
            String str = p.getKey() != null ? "● Recording: " : "";
            String show = p.getShow();
            i2 = SignalTestActivity.this.left;
            textView.setText(str + show + "\n" + i2 + " s");
            i3 = SignalTestActivity.this.left;
            SignalTestActivity.this.left = i3 - 1;
            handler = SignalTestActivity.this.main;
            handler.postDelayed(this, 1000L);
        }
    };

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: SignalTestActivity.kt */
    static final /* data */ class Phase {
        private final String key;
        private final String say;
        private final int secs;
        private final String show;

        public static /* synthetic */ Phase copy$default(Phase phase, String str, String str2, String str3, int i, int i2, Object obj) {
            if ((i2 & 1) != 0) {
                str = phase.show;
            }
            if ((i2 & 2) != 0) {
                str2 = phase.say;
            }
            if ((i2 & 4) != 0) {
                str3 = phase.key;
            }
            if ((i2 & 8) != 0) {
                i = phase.secs;
            }
            return phase.copy(str, str2, str3, i);
        }

        /* renamed from: component1, reason: from getter */
        public final String component1() {
            return this.show;
        }

        /* renamed from: component2, reason: from getter */
        public final String component2() {
            return this.say;
        }

        /* renamed from: component3, reason: from getter */
        public final String component3() {
            return this.key;
        }

        /* renamed from: component4, reason: from getter */
        public final int component4() {
            return this.secs;
        }

        public final Phase copy(String show, String say, String key, int secs) {
            Intrinsics.checkNotNullParameter(show, "show");
            Intrinsics.checkNotNullParameter(say, "say");
            return new Phase(show, say, key, secs);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Phase)) {
                return false;
            }
            Phase phase = (Phase) other;
            return Intrinsics.areEqual(this.show, phase.show) && Intrinsics.areEqual(this.say, phase.say) && Intrinsics.areEqual(this.key, phase.key) && this.secs == phase.secs;
        }

        public int hashCode() {
            return (((((this.show.hashCode() * 31) + this.say.hashCode()) * 31) + (this.key == null ? 0 : this.key.hashCode())) * 31) + Integer.hashCode(this.secs);
        }

        public String toString() {
            return "Phase(show=" + this.show + ", say=" + this.say + ", key=" + this.key + ", secs=" + this.secs + ")";
        }

        public Phase(String show, String say, String key, int secs) {
            Intrinsics.checkNotNullParameter(show, "show");
            Intrinsics.checkNotNullParameter(say, "say");
            this.show = show;
            this.say = say;
            this.key = key;
            this.secs = secs;
        }

        public final String getKey() {
            return this.key;
        }

        public final String getSay() {
            return this.say;
        }

        public final int getSecs() {
            return this.secs;
        }

        public final String getShow() {
            return this.show;
        }
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle b) {
        TextView textView;
        super.onCreate(b);
        getWindow().addFlags(128);
        float d = getResources().getDisplayMetrics().density;
        LinearLayout col = new LinearLayout(this);
        col.setOrientation(1);
        col.setPadding(onCreate$dp(d, 24), onCreate$dp(d, 40), onCreate$dp(d, 24), onCreate$dp(d, 24));
        TextView $this$onCreate_u24lambda_u241 = new TextView(this);
        $this$onCreate_u24lambda_u241.setText("Signal test");
        $this$onCreate_u24lambda_u241.setTextSize(26.0f);
        $this$onCreate_u24lambda_u241.setTypeface(Typeface.DEFAULT_BOLD);
        int i = -1;
        $this$onCreate_u24lambda_u241.setTextColor(-1);
        col.addView($this$onCreate_u24lambda_u241);
        TextView $this$onCreate_u24lambda_u242 = new TextView(this);
        $this$onCreate_u24lambda_u242.setText("Car in Park, engine on, StarLock connected over Bluetooth. Tap a step, put the phone where you normally carry it; it starts 3 s later and buzzes when the step is done.");
        $this$onCreate_u24lambda_u242.setTextSize(14.0f);
        $this$onCreate_u24lambda_u242.setTextColor(Color.rgb(170, 180, 190));
        $this$onCreate_u24lambda_u242.setPadding(0, onCreate$dp(d, 6), 0, onCreate$dp(d, 12));
        col.addView($this$onCreate_u24lambda_u242);
        TextView $this$onCreate_u24lambda_u243 = new TextView(this);
        $this$onCreate_u24lambda_u243.setTextSize(44.0f);
        $this$onCreate_u24lambda_u243.setTypeface(Typeface.DEFAULT_BOLD);
        $this$onCreate_u24lambda_u243.setTextColor(-1);
        $this$onCreate_u24lambda_u243.setGravity(17);
        this.live = $this$onCreate_u24lambda_u243;
        TextView textView2 = this.live;
        if (textView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("live");
            textView2 = null;
        }
        col.addView(textView2);
        TextView $this$onCreate_u24lambda_u244 = new TextView(this);
        $this$onCreate_u24lambda_u244.setTextSize(16.0f);
        $this$onCreate_u24lambda_u244.setTextColor(Color.rgb(255, 190, 80));
        $this$onCreate_u24lambda_u244.setGravity(17);
        $this$onCreate_u24lambda_u244.setPadding(0, 0, 0, onCreate$dp(d, 12));
        this.stepView = $this$onCreate_u24lambda_u244;
        TextView textView3 = this.stepView;
        if (textView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("stepView");
            textView3 = null;
        }
        col.addView(textView3);
        Button $this$onCreate_u24lambda_u246 = new Button(this);
        $this$onCreate_u24lambda_u246.setText("Run all 3 (spoken)");
        $this$onCreate_u24lambda_u246.setAllCaps(false);
        $this$onCreate_u24lambda_u246.setTextSize(18.0f);
        $this$onCreate_u24lambda_u246.setTypeface(Typeface.DEFAULT_BOLD);
        $this$onCreate_u24lambda_u246.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starlock.SignalTestActivity$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SignalTestActivity.this.runAll();
            }
        });
        LinearLayout.LayoutParams $this$onCreate_u24lambda_u247 = new LinearLayout.LayoutParams(-1, -2);
        $this$onCreate_u24lambda_u247.bottomMargin = onCreate$dp(d, 10);
        Unit unit = Unit.INSTANCE;
        col.addView($this$onCreate_u24lambda_u246, $this$onCreate_u24lambda_u247);
        for (Triple<String, String, Integer> triple : this.steps) {
            final String key = triple.component1();
            final String label = triple.component2();
            final int secs = triple.component3().intValue();
            Button $this$onCreate_u24lambda_u249 = new Button(this);
            $this$onCreate_u24lambda_u249.setText(label);
            $this$onCreate_u24lambda_u249.setAllCaps(false);
            $this$onCreate_u24lambda_u249.setTextSize(16.0f);
            $this$onCreate_u24lambda_u249.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starlock.SignalTestActivity$$ExternalSyntheticLambda4
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SignalTestActivity.onCreate$lambda$9$lambda$8(SignalTestActivity.this, label, key, secs, view);
                }
            });
            LinearLayout.LayoutParams $this$onCreate_u24lambda_u2410 = new LinearLayout.LayoutParams(i, -2);
            $this$onCreate_u24lambda_u2410.bottomMargin = onCreate$dp(d, 6);
            Unit unit2 = Unit.INSTANCE;
            col.addView($this$onCreate_u24lambda_u249, $this$onCreate_u24lambda_u2410);
            i = -1;
        }
        TextView $this$onCreate_u24lambda_u2411 = new TextView(this);
        $this$onCreate_u24lambda_u2411.setTextSize(15.0f);
        $this$onCreate_u24lambda_u2411.setTextColor(Color.rgb(200, 205, 212));
        $this$onCreate_u24lambda_u2411.setTypeface(Typeface.MONOSPACE);
        $this$onCreate_u24lambda_u2411.setPadding(0, onCreate$dp(d, 12), 0, onCreate$dp(d, 12));
        this.results = $this$onCreate_u24lambda_u2411;
        TextView textView4 = this.results;
        if (textView4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("results");
            textView = null;
        } else {
            textView = textView4;
        }
        col.addView(textView);
        LinearLayout $this$onCreate_u24lambda_u2416 = new LinearLayout(this);
        Button $this$onCreate_u24lambda_u2416_u24lambda_u2413 = new Button(this);
        $this$onCreate_u24lambda_u2416_u24lambda_u2413.setText("Copy results");
        $this$onCreate_u24lambda_u2416_u24lambda_u2413.setAllCaps(false);
        $this$onCreate_u24lambda_u2416_u24lambda_u2413.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starlock.SignalTestActivity$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SignalTestActivity.onCreate$lambda$16$lambda$13$lambda$12(SignalTestActivity.this, view);
            }
        });
        $this$onCreate_u24lambda_u2416.addView($this$onCreate_u24lambda_u2416_u24lambda_u2413);
        Button $this$onCreate_u24lambda_u2416_u24lambda_u2415 = new Button(this);
        $this$onCreate_u24lambda_u2416_u24lambda_u2415.setText("Start over");
        $this$onCreate_u24lambda_u2416_u24lambda_u2415.setAllCaps(false);
        $this$onCreate_u24lambda_u2416_u24lambda_u2415.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starlock.SignalTestActivity$$ExternalSyntheticLambda6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SignalTestActivity.onCreate$lambda$16$lambda$15$lambda$14(SignalTestActivity.this, view);
            }
        });
        $this$onCreate_u24lambda_u2416.addView($this$onCreate_u24lambda_u2416_u24lambda_u2415);
        col.addView($this$onCreate_u24lambda_u2416);
        ScrollView $this$onCreate_u24lambda_u2417 = new ScrollView(this);
        $this$onCreate_u24lambda_u2417.setBackgroundColor(Color.rgb(10, 12, 16));
        $this$onCreate_u24lambda_u2417.addView(col);
        setContentView($this$onCreate_u24lambda_u2417);
        KeeperService.Companion.start(this);
        this.tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() { // from class: com.starpilot.starlock.SignalTestActivity$$ExternalSyntheticLambda7
            @Override // android.speech.tts.TextToSpeech.OnInitListener
            public final void onInit(int i2) {
                SignalTestActivity.onCreate$lambda$18(SignalTestActivity.this, i2);
            }
        });
        tick();
    }

    private static final int onCreate$dp(float d, int v) {
        return (int) (v * d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$9$lambda$8(SignalTestActivity this$0, String $label, String $key, int $secs, View it) {
        this$0.start((List<Phase>) (List) CollectionsKt.listOf(new Phase[]{new Phase("Get ready: " + $label, "Get ready", null, 3), new Phase($label, "Recording", $key, $secs)}));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$16$lambda$13$lambda$12(SignalTestActivity this$0, View it) {
        ClipboardManager clipboardManager = (ClipboardManager) this$0.getSystemService(ClipboardManager.class);
        TextView textView = this$0.results;
        TextView textView2 = null;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("results");
            textView = null;
        }
        clipboardManager.setPrimaryClip(ClipData.newPlainText("StarLock signal test", textView.getText()));
        TextView textView3 = this$0.stepView;
        if (textView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("stepView");
        } else {
            textView2 = textView3;
        }
        textView2.setText("Copied");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$16$lambda$15$lambda$14(SignalTestActivity this$0, View it) {
        this$0.main.removeCallbacks(this$0.countdown);
        this$0.queue.clear();
        TextView textView = null;
        this$0.phase = null;
        this$0.samples.clear();
        this$0.recording = null;
        TextView textView2 = this$0.stepView;
        if (textView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("stepView");
        } else {
            textView = textView2;
        }
        textView.setText("");
        this$0.showResults();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$18(SignalTestActivity this$0, int st) {
        TextToSpeech textToSpeech;
        if (st != 0 || (textToSpeech = this$0.tts) == null) {
            return;
        }
        textToSpeech.setLanguage(Locale.US);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void tick() {
        if (this.running) {
            BleLink link = KeeperService.Companion.getLink();
            if (link != null) {
                link.setOnRssi(new Function1() { // from class: com.starpilot.starlock.SignalTestActivity$$ExternalSyntheticLambda1
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        Unit tick$lambda$20;
                        tick$lambda$20 = SignalTestActivity.tick$lambda$20(SignalTestActivity.this, ((Integer) obj).intValue());
                        return tick$lambda$20;
                    }
                });
            }
            if (!(link != null && link.readRssi())) {
                TextView textView = this.live;
                if (textView == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("live");
                    textView = null;
                }
                textView.setText(link != null && link.getReady() ? "…" : "not connected");
            }
            this.main.postDelayed(new Runnable() { // from class: com.starpilot.starlock.SignalTestActivity$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    SignalTestActivity.this.tick();
                }
            }, 500L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tick$lambda$20(final SignalTestActivity this$0, final int r) {
        this$0.main.post(new Runnable() { // from class: com.starpilot.starlock.SignalTestActivity$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this$0.onSample(r);
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onSample(int r) {
        List<Integer> list;
        this.last = Integer.valueOf(r);
        TextView textView = this.live;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("live");
            textView = null;
        }
        textView.setText(r + " dBm");
        String it = this.recording;
        if (it != null) {
            Map $this$getOrPut$iv = this.samples;
            List<Integer> list2 = (List<Integer>) $this$getOrPut$iv.get(it);
            if (list2 == null) {
                list = new ArrayList();
                $this$getOrPut$iv.put(it, list);
            } else {
                list = list2;
            }
            list.add(Integer.valueOf(r));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void runAll() {
        start((List<Phase>) (List) CollectionsKt.listOf(new Phase[]{new Phase("Sit inside, doors closed, phone where you carry it", "Sit inside with the doors closed", null, 5), new Phase(this.steps.get(0).getSecond(), "Recording inside", "inside", this.steps.get(0).getThird().intValue()), new Phase("Get out and close the driver's door", "Done. Get out and close the driver's door", null, 15), new Phase(this.steps.get(1).getSecond(), "Recording outside. Stay next to the door", "outside", this.steps.get(1).getThird().intValue()), new Phase("Start walking away", "Done. Start walking away", null, 3), new Phase(this.steps.get(2).getSecond(), "Recording. Keep walking, about thirty feet", "away", this.steps.get(2).getThird().intValue())}));
    }

    private final void start(List<Phase> phases) {
        this.main.removeCallbacks(this.countdown);
        this.queue.clear();
        this.queue.addAll(phases);
        this.recording = null;
        next();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void next() {
        if (this.recording != null) {
            showResults();
        }
        TextView textView = null;
        this.recording = null;
        Phase p = this.queue.removeFirstOrNull();
        this.phase = p;
        if (p == null) {
            TextView textView2 = this.stepView;
            if (textView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("stepView");
            } else {
                textView = textView2;
            }
            textView.setText("All done");
            say("All done");
            m55buzzIoAF18A(true);
            showResults();
            return;
        }
        String it = p.getKey();
        if (it != null) {
            this.samples.put(it, new ArrayList());
        }
        this.recording = p.getKey();
        this.left = p.getSecs();
        say(p.getSay());
        m56buzzIoAF18A$default(this, false, 1, null);
        this.main.post(this.countdown);
    }

    private final void say(String words) {
        TextToSpeech textToSpeech = this.tts;
        if (textToSpeech != null) {
            textToSpeech.speak(words, 0, null, "starlock");
        }
    }

    /* renamed from: buzz-IoAF18A, reason: not valid java name */
    private final Object m55buzzIoAF18A(boolean r7) {
        try {
            Result.Companion companion = Result.Companion;
            SignalTestActivity $this$buzz_IoAF18A_u24lambda_u2426 = this;
            ((Vibrator) $this$buzz_IoAF18A_u24lambda_u2426.getSystemService(Vibrator.class)).vibrate(r7 ? VibrationEffect.createOneShot(600L, -1) : VibrationEffect.createWaveform(new long[]{0, 120, 120, 120}, -1));
            return KtCompat.resultOf(Unit.INSTANCE);
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            return KtCompat.resultOf(ResultKt.createFailure(th));
        }
    }

    /* renamed from: buzz-IoAF18A$default, reason: not valid java name */
    static /* synthetic */ Object m56buzzIoAF18A$default(SignalTestActivity signalTestActivity, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        return signalTestActivity.m55buzzIoAF18A(z);
    }

    private final void showResults() {
        List s;
        StringBuilder sb = new StringBuilder("step      n   min  median  max   (dBm, higher = stronger)\n");
        Map med = new LinkedHashMap();
        Iterator<Triple<String, String, Integer>> it = this.steps.iterator();
        while (it.hasNext()) {
            String key = it.next().component1();
            List<Integer> list = this.samples.get(key);
            if (list != null && (s = CollectionsKt.sorted(list)) != null && !s.isEmpty()) {
                med.put(key, s.get(s.size() / 2));
                StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
                String format = String.format("%-8s %3d  %4d  %5d  %4d\n", Arrays.copyOf(new Object[]{key, Integer.valueOf(s.size()), CollectionsKt.first(s), s.get(s.size() / 2), CollectionsKt.last(s)}, 5));
                Intrinsics.checkNotNullExpressionValue(format, "format(...)");
                sb.append(format);
            }
        }
        List inside = this.samples.get("inside");
        TextView textView = null;
        if (inside == null || inside.isEmpty()) {
            inside = null;
        }
        List outside = this.samples.get("outside");
        if (outside == null || outside.isEmpty()) {
            outside = null;
        }
        if (inside != null && outside != null) {
            int gap = ((Number) CollectionsKt.minOrThrow((Iterable<Double>) inside)).intValue() - ((Number) CollectionsKt.maxOrThrow((Iterable<Double>) outside)).intValue();
            Object obj = med.get("inside");
            Object obj2 = med.get("outside");
            Object obj3 = med.get("inside");
            Intrinsics.checkNotNull(obj3);
            int intValue = ((Number) obj3).intValue();
            Object obj4 = med.get("outside");
            Intrinsics.checkNotNull(obj4);
            sb.append("\nInside median " + obj + ", outside median " + obj2 + ": " + (intValue - ((Number) obj4).intValue()) + " dB apart.\n");
            sb.append(gap > 0 ? "Clean gap: the weakest inside reading is " + gap + " dB above the strongest outside one.\nSuggested line: " + ((((Number) CollectionsKt.minOrThrow((Iterable<Double>) inside)).intValue() + ((Number) CollectionsKt.maxOrThrow((Iterable<Double>) outside)).intValue()) / 2) + " dBm" : "They overlap by " + (-gap) + " dB: one reading alone can't tell inside from outside.");
            SharedPreferences.Editor edit = Door.INSTANCE.prefs(this).edit();
            Object obj5 = med.get("inside");
            Intrinsics.checkNotNull(obj5);
            SharedPreferences.Editor putInt = edit.putInt("rssi_inside", ((Number) obj5).intValue());
            Object obj6 = med.get("outside");
            Intrinsics.checkNotNull(obj6);
            putInt.putInt("rssi_outside", ((Number) obj6).intValue()).apply();
        }
        TextView textView2 = this.results;
        if (textView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("results");
        } else {
            textView = textView2;
        }
        textView.setText(sb);
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        this.running = false;
        TextToSpeech textToSpeech = this.tts;
        if (textToSpeech != null) {
            textToSpeech.shutdown();
        }
        BleLink link = KeeperService.Companion.getLink();
        if (link != null) {
            link.setOnRssi(null);
        }
        this.main.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}
