package com.starpilot.starlock;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import androidx.core.app.NotificationCompat;
import com.starpilot.starlock.Door;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: AutoLockActivity.kt */
public final class AutoLockActivity extends Activity {
    private TextView live;
    private Switch lockSw;
    private TextView state;
    private Switch unlockSw;
    private final Handler main = new Handler(Looper.getMainLooper());
    private final LinkedHashMap<String, Integer> values = MapsKt.linkedMapOf(TuplesKt.to("lock_dbm", -52), TuplesKt.to("away_dbm", -75), TuplesKt.to("unlock_dbm", -62), TuplesKt.to("unlock_min", 30));
    private final List<Function0<Unit>> refreshers = new ArrayList();
    private boolean running = true;

    @Override // android.app.Activity
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        float d = getResources().getDisplayMetrics().density;
        LinearLayout $this$onCreate_u24lambda_u240 = new LinearLayout(this);
        $this$onCreate_u24lambda_u240.setOrientation(1);
        $this$onCreate_u24lambda_u240.setPadding(onCreate$dp(d, 24), onCreate$dp(d, 40), onCreate$dp(d, 24), onCreate$dp(d, 24));
        TextView $this$onCreate_u24lambda_u241 = new TextView(this);
        $this$onCreate_u24lambda_u241.setText("Auto-lock");
        $this$onCreate_u24lambda_u241.setTextSize(26.0f);
        $this$onCreate_u24lambda_u241.setTypeface(Typeface.DEFAULT_BOLD);
        $this$onCreate_u24lambda_u241.setTextColor(-1);
        $this$onCreate_u24lambda_u240.addView($this$onCreate_u24lambda_u241);
        TextView $this$onCreate_u24lambda_u242 = new TextView(this);
        $this$onCreate_u24lambda_u242.setText("In Park with the engine on: when the driver's door closes with your phone connected, the car locks as soon as the comma's reading of your phone drops below the lock line (or the phone drops off). A door opening or leaving Park cancels it. Numbers are the comma's reading of your phone (dBm; higher = closer).");
        $this$onCreate_u24lambda_u242.setTextSize(14.0f);
        $this$onCreate_u24lambda_u242.setTextColor(Color.rgb(170, 180, 190));
        $this$onCreate_u24lambda_u242.setPadding(0, onCreate$dp(d, 6), 0, onCreate$dp(d, 12));
        $this$onCreate_u24lambda_u240.addView($this$onCreate_u24lambda_u242);
        TextView $this$onCreate_u24lambda_u243 = new TextView(this);
        $this$onCreate_u24lambda_u243.setTextSize(36.0f);
        $this$onCreate_u24lambda_u243.setTypeface(Typeface.DEFAULT_BOLD);
        $this$onCreate_u24lambda_u243.setTextColor(-1);
        $this$onCreate_u24lambda_u243.setGravity(17);
        this.live = $this$onCreate_u24lambda_u243;
        TextView textView = this.live;
        Switch r16 = null;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("live");
            textView = null;
        }
        $this$onCreate_u24lambda_u240.addView(textView);
        TextView $this$onCreate_u24lambda_u244 = new TextView(this);
        $this$onCreate_u24lambda_u244.setText("the comma's reading of your phone, live");
        $this$onCreate_u24lambda_u244.setTextSize(13.0f);
        $this$onCreate_u24lambda_u244.setGravity(17);
        $this$onCreate_u24lambda_u244.setTextColor(Color.rgb(140, 148, 160));
        $this$onCreate_u24lambda_u240.addView($this$onCreate_u24lambda_u244);
        TextView $this$onCreate_u24lambda_u245 = new TextView(this);
        $this$onCreate_u24lambda_u245.setTextSize(14.0f);
        $this$onCreate_u24lambda_u245.setTextColor(Color.rgb(255, 190, 80));
        $this$onCreate_u24lambda_u245.setGravity(17);
        $this$onCreate_u24lambda_u245.setPadding(0, onCreate$dp(d, 6), 0, onCreate$dp(d, 12));
        this.state = $this$onCreate_u24lambda_u245;
        TextView textView2 = this.state;
        if (textView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("state");
            textView2 = null;
        }
        $this$onCreate_u24lambda_u240.addView(textView2);
        Switch $this$onCreate_u24lambda_u2412 = new Switch(this);
        $this$onCreate_u24lambda_u2412.setText("Auto-lock when I walk away");
        $this$onCreate_u24lambda_u2412.setTextSize(17.0f);
        $this$onCreate_u24lambda_u2412.setTextColor(-1);
        this.lockSw = $this$onCreate_u24lambda_u2412;
        Switch r0 = this.lockSw;
        if (r0 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("lockSw");
            r0 = null;
        }
        $this$onCreate_u24lambda_u240.addView(r0);
        onCreate$stepper(this, $this$onCreate_u24lambda_u240, d, "Lock line", "lock_dbm", 1, new IntRange(-90, -35), "dBm", "Locks at the first reading below this. Your test: inside about −45, by the door about −60.");
        Switch $this$onCreate_u24lambda_u2413 = new Switch(this);
        $this$onCreate_u24lambda_u2413.setText("Auto-unlock when I come back");
        $this$onCreate_u24lambda_u2413.setTextSize(17.0f);
        $this$onCreate_u24lambda_u2413.setTextColor(-1);
        $this$onCreate_u24lambda_u2413.setPadding(0, onCreate$dp(d, 18), 0, 0);
        this.unlockSw = $this$onCreate_u24lambda_u2413;
        Switch r02 = this.unlockSw;
        if (r02 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("unlockSw");
        } else {
            r16 = r02;
        }
        $this$onCreate_u24lambda_u240.addView(r16);
        onCreate$stepper(this, $this$onCreate_u24lambda_u240, d, "Walk-away line", "away_dbm", 1, new IntRange(-100, -40), "dBm", "You must go below this (or out of range) before it can unlock.");
        onCreate$stepper(this, $this$onCreate_u24lambda_u240, d, "Unlock line", "unlock_dbm", 1, new IntRange(-90, -35), "dBm", "Unlocks when you come back above this. Must be stronger than the walk-away line.");
        onCreate$stepper(this, $this$onCreate_u24lambda_u240, d, "Time limit", "unlock_min", 5, new IntRange(5, 120), "min", "No auto-unlock after this long; use the tile.");
        Button $this$onCreate_u24lambda_u2415 = new Button(this);
        $this$onCreate_u24lambda_u2415.setText("Save to the comma");
        $this$onCreate_u24lambda_u2415.setAllCaps(false);
        $this$onCreate_u24lambda_u2415.setTextSize(18.0f);
        $this$onCreate_u24lambda_u2415.setTypeface(Typeface.DEFAULT_BOLD);
        $this$onCreate_u24lambda_u2415.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starlock.AutoLockActivity$$ExternalSyntheticLambda8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AutoLockActivity.this.save();
            }
        });
        LinearLayout.LayoutParams $this$onCreate_u24lambda_u2416 = new LinearLayout.LayoutParams(-1, -2);
        $this$onCreate_u24lambda_u2416.topMargin = onCreate$dp(d, 18);
        Unit unit = Unit.INSTANCE;
        $this$onCreate_u24lambda_u240.addView($this$onCreate_u24lambda_u2415, $this$onCreate_u24lambda_u2416);
        TextView $this$onCreate_u24lambda_u2417 = new TextView(this);
        $this$onCreate_u24lambda_u2417.setText("Saving also sends your current answer-back (horn / beeper / silent and the horn patterns). Save again here after changing them. Needs StarLock's Bluetooth connection.");
        $this$onCreate_u24lambda_u2417.setTextSize(12.0f);
        $this$onCreate_u24lambda_u2417.setTextColor(Color.rgb(130, 138, 150));
        $this$onCreate_u24lambda_u2417.setPadding(0, onCreate$dp(d, 6), 0, 0);
        $this$onCreate_u24lambda_u240.addView($this$onCreate_u24lambda_u2417);
        ScrollView $this$onCreate_u24lambda_u2418 = new ScrollView(this);
        $this$onCreate_u24lambda_u2418.setBackgroundColor(Color.rgb(10, 12, 16));
        $this$onCreate_u24lambda_u2418.addView($this$onCreate_u24lambda_u240);
        setContentView($this$onCreate_u24lambda_u2418);
        KeeperService.Companion.start(this);
        load();
        poll();
    }

    private static final int onCreate$dp(float d, int v) {
        return (int) (v * d);
    }

    private static final void onCreate$stepper(AutoLockActivity this$0, LinearLayout col, float d, String label, String key, int step, IntRange range, String unit, String hint) {
        TextView $this$onCreate_u24stepper_u24lambda_u246 = new TextView(this$0);
        $this$onCreate_u24stepper_u24lambda_u246.setTextSize(18.0f);
        $this$onCreate_u24stepper_u24lambda_u246.setTextColor(-1);
        $this$onCreate_u24stepper_u24lambda_u246.setGravity(17);
        TextView $this$onCreate_u24stepper_u24lambda_u249 = new TextView(this$0);
        $this$onCreate_u24stepper_u24lambda_u249.setText(label);
        $this$onCreate_u24stepper_u24lambda_u249.setTextSize(15.0f);
        $this$onCreate_u24stepper_u24lambda_u249.setTextColor(Color.rgb(220, 224, 230));
        $this$onCreate_u24stepper_u24lambda_u249.setPadding(0, onCreate$dp(d, 10), 0, 0);
        col.addView($this$onCreate_u24stepper_u24lambda_u249);
        TextView $this$onCreate_u24stepper_u24lambda_u2410 = new TextView(this$0);
        $this$onCreate_u24stepper_u24lambda_u2410.setText(hint);
        $this$onCreate_u24stepper_u24lambda_u2410.setTextSize(12.0f);
        $this$onCreate_u24stepper_u24lambda_u2410.setTextColor(Color.rgb(130, 138, 150));
        col.addView($this$onCreate_u24stepper_u24lambda_u2410);
        LinearLayout $this$onCreate_u24stepper_u24lambda_u2411 = new LinearLayout(this$0);
        $this$onCreate_u24stepper_u24lambda_u2411.setGravity(16);
        $this$onCreate_u24stepper_u24lambda_u2411.addView(onCreate$stepper$btn(this$0, key, range, $this$onCreate_u24stepper_u24lambda_u246, unit, "−", -step), new LinearLayout.LayoutParams(onCreate$dp(d, 56), onCreate$dp(d, 48)));
        $this$onCreate_u24stepper_u24lambda_u2411.addView($this$onCreate_u24stepper_u24lambda_u246, new LinearLayout.LayoutParams(0, -2, 1.0f));
        $this$onCreate_u24stepper_u24lambda_u2411.addView(onCreate$stepper$btn(this$0, key, range, $this$onCreate_u24stepper_u24lambda_u246, unit, "+", step), new LinearLayout.LayoutParams(onCreate$dp(d, 56), onCreate$dp(d, 48)));
        col.addView($this$onCreate_u24stepper_u24lambda_u2411);
        this$0.refreshers.add(new AutoLockActivity$onCreate$stepper$4($this$onCreate_u24stepper_u24lambda_u246, this$0, key, unit));
        onCreate$stepper$show($this$onCreate_u24stepper_u24lambda_u246, this$0, key, unit);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$stepper$show(TextView v, AutoLockActivity this$0, String $key, String $unit) {
        v.setText(this$0.values.get($key) + " " + $unit);
    }

    private static final Button onCreate$stepper$btn(final AutoLockActivity this$0, final String $key, final IntRange $range, final TextView v, final String $unit, String t, final int delta) {
        Button $this$onCreate_u24stepper_u24btn_u24lambda_u248 = new Button(this$0);
        $this$onCreate_u24stepper_u24btn_u24lambda_u248.setText(t);
        $this$onCreate_u24stepper_u24btn_u24lambda_u248.setTextSize(20.0f);
        $this$onCreate_u24stepper_u24btn_u24lambda_u248.setMinWidth(0);
        $this$onCreate_u24stepper_u24btn_u24lambda_u248.setMinimumWidth(0);
        $this$onCreate_u24stepper_u24btn_u24lambda_u248.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starlock.AutoLockActivity$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AutoLockActivity.onCreate$stepper$btn$lambda$8$lambda$7(this$0, $key, delta, $range, v, $unit, view);
            }
        });
        return $this$onCreate_u24stepper_u24btn_u24lambda_u248;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$stepper$btn$lambda$8$lambda$7(AutoLockActivity this$0, String $key, int $delta, IntRange $range, TextView $v, String $unit, View it) {
        LinkedHashMap<String, Integer> linkedHashMap = this$0.values;
        Integer num = this$0.values.get($key);
        Intrinsics.checkNotNull(num);
        linkedHashMap.put($key, Integer.valueOf(RangesKt.coerceIn(num.intValue() + $delta, $range.getFirst(), $range.getLast())));
        onCreate$stepper$show($v, this$0, $key, $unit);
    }

    private final BleLink link() {
        BleLink it = KeeperService.Companion.getLink();
        if (it == null || !it.getReady()) {
            return null;
        }
        return it;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void poll() {
        if (this.running) {
            final BleLink l = link();
            if (l == null) {
                TextView textView = this.live;
                if (textView == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("live");
                    textView = null;
                }
                textView.setText("not connected");
            } else {
                new Thread(new Runnable() { // from class: com.starpilot.starlock.AutoLockActivity$$ExternalSyntheticLambda6
                    @Override // java.lang.Runnable
                    public final void run() {
                        AutoLockActivity.poll$lambda$21(l, AutoLockActivity.this);
                    }
                }).start();
            }
            this.main.postDelayed(new Runnable() { // from class: com.starpilot.starlock.AutoLockActivity$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    AutoLockActivity.this.poll();
                }
            }, 1000L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void poll$lambda$21(BleLink $l, final AutoLockActivity this$0) {
        final JSONObject r = BleLink.request$default($l, "rssi", null, false, 1500L, 2, null);
        this$0.main.post(new Runnable() { // from class: com.starpilot.starlock.AutoLockActivity$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                AutoLockActivity.poll$lambda$21$lambda$20(r, this$0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void poll$lambda$21$lambda$20(JSONObject $r, AutoLockActivity this$0) {
        String str;
        if ($r != null) {
            TextView textView = this$0.live;
            if (textView == null) {
                Intrinsics.throwUninitializedPropertyAccessException("live");
                textView = null;
            }
            if ($r.isNull("dbm")) {
                str = "no reading";
            } else {
                str = $r.optInt("dbm") + " dBm";
            }
            textView.setText(str);
        }
    }

    /* VF: from Vineflower (jadx: Code restructure failed;incorrect) */
    public final void show(JSONObject var1) {
        String var17 = null; // declarations Vineflower left out
        Object var5 = null;
        String var4 = null;
        TextView var3 = null;
        if (var1 == null) {
            TextView var11 = this.state;
            if (var11 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("state");
                var11 = var3;
            }

            var11.setText("No answer - is the comma on, and the update with auto-lock installed?");
        } else if (!var1.optBoolean("ok")) {
            var3 = this.state;
            if (var3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("state");
                var3 = (TextView)var5;
            }

            String var10 = var1.optString("error");
            var3.setText("Not saved: " + var10);
        } else {
            JSONObject var6 = var1.optJSONObject("config");
            if (var6 != null) {
                var5 = this.lockSw;
                Switch var12 = (Switch)var5;
                if (var5 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("lockSw");
                    var12 = null;
                }

                var12.setChecked(var6.optBoolean("lock"));
                var5 = this.unlockSw;
                Switch var13 = (Switch)var5;
                if (var5 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("unlockSw");
                    var13 = null;
                }

                var13.setChecked(var6.optBoolean("unlock"));

                for (Object var5$it : (Iterable) this.values.keySet()) {
                    var5 = var5$it;
                    Intrinsics.checkNotNullExpressionValue(var5, "next(...)");
                    var5 = (String)var5;
                    Map var8 = this.values;
                    Object var7 = this.values.get(var5);
                    Intrinsics.checkNotNull(var7);
                    var8.put(var5, var6.optInt((String)var5, ((Number)var7).intValue()));
                }

                Iterator var15 = this.refreshers.iterator();

                while (var15.hasNext()) {
                    ((Function0)var15.next()).invoke();
                }

                var6 = var1.optJSONObject("status");
                var3 = this.state;
                TextView var9 = var3;
                if (var3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("state");
                    var9 = null;
                }

                label69: {
                    if (var6 != null) {
                        var5 = var6.optString("phase");
                        var17 = (String)var5;
                        if (var5 != null) {
                            break label69;
                        }
                    }

                    var17 = "?";
                }

                label64: {
                    if (var6 != null) {
                        var5 = var6.optString("event");
                        if (var5 != null) {
                            boolean var2;
                            if (((CharSequence)var5).length() > 0) {
                                var2 = true;
                            } else {
                                var2 = false;
                            }

                            if (var2) {
                                var4 = (String)var5;
                            }

                            if (var4 != null) {
                                var5 = "  ·  last: " + var4;
                                var4 = (String)var5;
                                if (var5 != null) {
                                    break label64;
                                }
                            }
                        }
                    }

                    var4 = "";
                }

                var9.setText("Now: " + var17 + var4);
            }
        }
    }

    private final void load() {
        final BleLink l = link();
        if (l == null) {
            final AutoLockActivity $this$load_u24lambda_u2427 = this;
            TextView textView = $this$load_u24lambda_u2427.state;
            if (textView == null) {
                Intrinsics.throwUninitializedPropertyAccessException("state");
                textView = null;
            }
            textView.setText("Waiting for the Bluetooth connection…");
            $this$load_u24lambda_u2427.main.postDelayed(new Runnable() { // from class: com.starpilot.starlock.AutoLockActivity$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    AutoLockActivity.load$lambda$27$lambda$26(AutoLockActivity.this);
                }
            }, 2000L);
            return;
        }
        new Thread(new Runnable() { // from class: com.starpilot.starlock.AutoLockActivity$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                AutoLockActivity.load$lambda$29(l, AutoLockActivity.this);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void load$lambda$27$lambda$26(AutoLockActivity $this_run) {
        if ($this_run.running) {
            $this_run.load();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void load$lambda$29(BleLink $l, final AutoLockActivity this$0) {
        final JSONObject r = BleLink.request$default($l, "autolock", null, false, 0L, 14, null);
        this$0.main.post(new Runnable() { // from class: com.starpilot.starlock.AutoLockActivity$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this$0.show(r);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void save() {
        final BleLink l = link();
        TextView textView = null;
        if (l == null) {
            AutoLockActivity $this$save_u24lambda_u2430 = this;
            TextView textView2 = $this$save_u24lambda_u2430.state;
            if (textView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("state");
            } else {
                textView = textView2;
            }
            textView.setText("Not connected to the comma over Bluetooth");
            return;
        }
        JSONObject jSONObject = new JSONObject();
        Switch r4 = this.lockSw;
        if (r4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("lockSw");
            r4 = null;
        }
        JSONObject put = jSONObject.put("lock", r4.isChecked());
        Switch r42 = this.unlockSw;
        if (r42 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("unlockSw");
            r42 = null;
        }
        final JSONObject set = put.put("unlock", r42.isChecked()).put("sound", Door.INSTANCE.sound(this)).put("lock_beeps", save$beeps(this, "lock")).put("unlock_beeps", save$beeps(this, "unlock"));
        for (Map.Entry<String, Integer> entry : this.values.entrySet()) {
            String k = entry.getKey();
            int v = entry.getValue().intValue();
            set.put(k, v);
        }
        TextView textView3 = this.state;
        if (textView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("state");
        } else {
            textView = textView3;
        }
        textView.setText("Saving…");
        new Thread(new Runnable() { // from class: com.starpilot.starlock.AutoLockActivity$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                AutoLockActivity.save$lambda$33(l, set, AutoLockActivity.this);
            }
        }).start();
    }

    private static final JSONArray save$beeps(AutoLockActivity this$0, String cmd) {
        Door.Pattern it = Door.INSTANCE.pattern(this$0, cmd);
        return new JSONArray().put(it.getN()).put(it.getOnMs()).put(it.getGapMs());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void save$lambda$33(BleLink $l, JSONObject $set, final AutoLockActivity this$0) {
        JSONObject put = new JSONObject().put("set", $set);
        Intrinsics.checkNotNullExpressionValue(put, "put(...)");
        final JSONObject r = BleLink.request$default($l, "autolock", put, false, 0L, 12, null);
        this$0.main.post(new Runnable() { // from class: com.starpilot.starlock.AutoLockActivity$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                AutoLockActivity.save$lambda$33$lambda$32(this$0, r);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void save$lambda$33$lambda$32(AutoLockActivity this$0, JSONObject $r) {
        this$0.show($r);
        boolean z = false;
        if ($r != null && $r.optBoolean("ok")) {
            z = true;
        }
        if (z) {
            TextView textView = this$0.state;
            TextView textView2 = null;
            if (textView == null) {
                Intrinsics.throwUninitializedPropertyAccessException("state");
                textView = null;
            }
            TextView textView3 = this$0.state;
            if (textView3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("state");
            } else {
                textView2 = textView3;
            }
            textView.setText("Saved ✓  " + ((Object) textView2.getText()));
        }
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        this.running = false;
        this.main.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}
