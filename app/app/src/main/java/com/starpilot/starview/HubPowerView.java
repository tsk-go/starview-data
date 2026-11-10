package com.starpilot.starview;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import com.starpilot.starview.CarUi;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.ranges.RangesKt;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: HubPower.kt */
public final class HubPowerView extends CardPage {
    private String err;
    private final HubPowerView$poll$1 poll;
    private boolean shown;
    private JSONObject status;
    private final SimpleDateFormat timeFmt;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.starpilot.starview.HubPowerView$poll$1] */
    public HubPowerView(Context ctx, CarUi ui) {
        super(ctx, ui);
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(ui, "ui");
        this.timeFmt = new SimpleDateFormat("EEE h:mm a", Locale.US);
        this.poll = new Runnable() { // from class: com.starpilot.starview.HubPowerView$poll$1
            @Override // java.lang.Runnable
            public void run() {
                boolean z;
                z = HubPowerView.this.shown;
                if (z) {
                    HubPowerView.this.load();
                    HubPowerView.this.getMain().postDelayed(this, 5000L);
                }
            }
        };
    }

    public final void start() {
        this.shown = true;
        getMain().removeCallbacks(this.poll);
        getMain().post(this.poll);
    }

    public final void stop() {
        this.shown = false;
        getMain().removeCallbacks(this.poll);
    }

    @Override // com.starpilot.starview.CardPage
    public void refresh() {
        start();
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ void req$default(HubPowerView hubPowerView, String str, Map map, Function1 function1, int i, Object obj) {
        if ((i & 2) != 0) {
            map = MapsKt.emptyMap();
        }
        hubPowerView.req(str, map, function1);
    }

    private final void req(String op, Map<String, ? extends Object> extra, Function1<? super JSONObject, Unit> cb) {
        DataClient d = Link.INSTANCE.getData();
        if (d != null) {
            d.request("hub_power", MapsKt.plus(MapsKt.mapOf(TuplesKt.to("op", op)), extra), cb);
            return;
        }
        JSONObject put = new JSONObject().put("ok", false).put("error", "Not connected to the comma.");
        Intrinsics.checkNotNullExpressionValue(put, "put(...)");
        cb.invoke(put);
    }

    private final void toast(String s) {
        Toast.makeText(getContext(), s, 1).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void load() {
        req$default(this, "status", null, new Function1() { // from class: com.starpilot.starview.HubPowerView$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit load$lambda$0;
                load$lambda$0 = HubPowerView.load$lambda$0(HubPowerView.this, (JSONObject) obj);
                return load$lambda$0;
            }
        }, 2, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit load$lambda$0(HubPowerView this$0, JSONObject r) {
        String optString;
        Intrinsics.checkNotNullParameter(r, "r");
        if (r.has("delay_min")) {
            this$0.status = r;
            optString = null;
        } else {
            optString = r.optString("error", "The comma didn't answer");
        }
        this$0.err = optString;
        this$0.paint();
        return Unit.INSTANCE;
    }

    private final void send(String op, Map<String, ? extends Object> extra) {
        req(op, extra, new Function1() { // from class: com.starpilot.starview.HubPowerView$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit send$lambda$1;
                send$lambda$1 = HubPowerView.send$lambda$1(HubPowerView.this, (JSONObject) obj);
                return send$lambda$1;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit send$lambda$1(HubPowerView this$0, JSONObject r) {
        Intrinsics.checkNotNullParameter(r, "r");
        if (r.has("delay_min")) {
            this$0.status = r;
        }
        if (!r.optBoolean("ok")) {
            String optString = r.optString("error", "The comma didn't take it");
            Intrinsics.checkNotNullExpressionValue(optString, "optString(...)");
            this$0.toast(optString);
        }
        this$0.paint();
        return Unit.INSTANCE;
    }

    private final View switchRow(String label, String desc, boolean checked, boolean enabled, final Function1<? super Boolean, Unit> onFlip) {
        LinearLayout $this$switchRow_u24lambda_u247 = new LinearLayout(getContext());
        $this$switchRow_u24lambda_u247.setOrientation(0);
        $this$switchRow_u24lambda_u247.setGravity(16);
        $this$switchRow_u24lambda_u247.setPadding(0, getUi().dp(8), 0, getUi().dp(8));
        LinearLayout $this$switchRow_u24lambda_u247_u24lambda_u244 = new LinearLayout($this$switchRow_u24lambda_u247.getContext());
        $this$switchRow_u24lambda_u247_u24lambda_u244.setOrientation(1);
        TextView $this$switchRow_u24lambda_u247_u24lambda_u244_u24lambda_u242 = new TextView($this$switchRow_u24lambda_u247_u24lambda_u244.getContext());
        $this$switchRow_u24lambda_u247_u24lambda_u244_u24lambda_u242.setText(label);
        CarUi.Companion companion = CarUi.Companion;
        $this$switchRow_u24lambda_u247_u24lambda_u244_u24lambda_u242.setTextColor(enabled ? companion.getTEXT() : companion.getDIM());
        $this$switchRow_u24lambda_u247_u24lambda_u244_u24lambda_u242.setTextSize(19.0f);
        $this$switchRow_u24lambda_u247_u24lambda_u244_u24lambda_u242.setTypeface(Typeface.DEFAULT_BOLD);
        $this$switchRow_u24lambda_u247_u24lambda_u244.addView($this$switchRow_u24lambda_u247_u24lambda_u244_u24lambda_u242);
        TextView $this$switchRow_u24lambda_u247_u24lambda_u244_u24lambda_u243 = new TextView($this$switchRow_u24lambda_u247_u24lambda_u244.getContext());
        $this$switchRow_u24lambda_u247_u24lambda_u244_u24lambda_u243.setText(desc);
        $this$switchRow_u24lambda_u247_u24lambda_u244_u24lambda_u243.setTextColor(CarUi.Companion.getDIM());
        $this$switchRow_u24lambda_u247_u24lambda_u244_u24lambda_u243.setTextSize(15.0f);
        $this$switchRow_u24lambda_u247_u24lambda_u244_u24lambda_u243.setPadding(0, getUi().dp(2), getUi().dp(12), 0);
        $this$switchRow_u24lambda_u247_u24lambda_u244.addView($this$switchRow_u24lambda_u247_u24lambda_u244_u24lambda_u243);
        $this$switchRow_u24lambda_u247.addView($this$switchRow_u24lambda_u247_u24lambda_u244, new LinearLayout.LayoutParams(0, -2, 1.0f));
        Switch $this$switchRow_u24lambda_u247_u24lambda_u246 = getUi().bigSwitch();
        $this$switchRow_u24lambda_u247_u24lambda_u246.setChecked(checked);
        $this$switchRow_u24lambda_u247_u24lambda_u246.setEnabled(enabled);
        $this$switchRow_u24lambda_u247_u24lambda_u246.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.starpilot.starview.HubPowerView$$ExternalSyntheticLambda3
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                HubPowerView.switchRow$lambda$7$lambda$6$lambda$5(Function1.this, compoundButton, z);
            }
        });
        $this$switchRow_u24lambda_u247.addView($this$switchRow_u24lambda_u247_u24lambda_u246);
        return $this$switchRow_u24lambda_u247;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void switchRow$lambda$7$lambda$6$lambda$5(Function1 $onFlip, CompoundButton compoundButton, boolean on) {
        $onFlip.invoke(Boolean.valueOf(on));
    }

    private final View stepRow(String label, String value, final boolean enabled, final Function0<Unit> minus, final Function0<Unit> plus) {
        LinearLayout $this$stepRow_u24lambda_u2414 = new LinearLayout(getContext());
        $this$stepRow_u24lambda_u2414.setOrientation(0);
        $this$stepRow_u24lambda_u2414.setGravity(16);
        $this$stepRow_u24lambda_u2414.setPadding(0, getUi().dp(8), 0, getUi().dp(8));
        TextView $this$stepRow_u24lambda_u2414_u24lambda_u248 = new TextView($this$stepRow_u24lambda_u2414.getContext());
        $this$stepRow_u24lambda_u2414_u24lambda_u248.setText(label);
        CarUi.Companion companion = CarUi.Companion;
        $this$stepRow_u24lambda_u2414_u24lambda_u248.setTextColor(enabled ? companion.getTEXT() : companion.getDIM());
        $this$stepRow_u24lambda_u2414_u24lambda_u248.setTextSize(18.0f);
        $this$stepRow_u24lambda_u2414.addView($this$stepRow_u24lambda_u2414_u24lambda_u248, new LinearLayout.LayoutParams(0, -2, 1.0f));
        TextView $this$stepRow_u24lambda_u2414_u24lambda_u2410 = getUi().stepper("−", new Function0() { // from class: com.starpilot.starview.HubPowerView$$ExternalSyntheticLambda13
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit stepRow$lambda$14$lambda$9;
                stepRow$lambda$14$lambda$9 = HubPowerView.stepRow$lambda$14$lambda$9(enabled, minus);
                return stepRow$lambda$14$lambda$9;
            }
        });
        $this$stepRow_u24lambda_u2414_u24lambda_u2410.setAlpha(enabled ? 1.0f : 0.35f);
        TextView $this$stepRow_u24lambda_u2414_u24lambda_u2412 = getUi().stepper("+", new Function0() { // from class: com.starpilot.starview.HubPowerView$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit stepRow$lambda$14$lambda$11;
                stepRow$lambda$14$lambda$11 = HubPowerView.stepRow$lambda$14$lambda$11(enabled, plus);
                return stepRow$lambda$14$lambda$11;
            }
        });
        $this$stepRow_u24lambda_u2414_u24lambda_u2412.setAlpha(enabled ? 1.0f : 0.35f);
        $this$stepRow_u24lambda_u2414.addView($this$stepRow_u24lambda_u2414_u24lambda_u2410);
        TextView $this$stepRow_u24lambda_u2414_u24lambda_u2413 = new TextView($this$stepRow_u24lambda_u2414.getContext());
        $this$stepRow_u24lambda_u2414_u24lambda_u2413.setText(value);
        CarUi.Companion companion2 = CarUi.Companion;
        $this$stepRow_u24lambda_u2414_u24lambda_u2413.setTextColor(enabled ? companion2.getTEXT() : companion2.getDIM());
        $this$stepRow_u24lambda_u2414_u24lambda_u2413.setTextSize(24.0f);
        $this$stepRow_u24lambda_u2414_u24lambda_u2413.setTypeface(Typeface.DEFAULT_BOLD);
        $this$stepRow_u24lambda_u2414_u24lambda_u2413.setGravity(17);
        $this$stepRow_u24lambda_u2414.addView($this$stepRow_u24lambda_u2414_u24lambda_u2413, new LinearLayout.LayoutParams(getUi().dp(150), -2));
        $this$stepRow_u24lambda_u2414.addView($this$stepRow_u24lambda_u2414_u24lambda_u2412);
        return $this$stepRow_u24lambda_u2414;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit stepRow$lambda$14$lambda$9(boolean $enabled, Function0 $minus) {
        if ($enabled) {
            $minus.invoke();
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit stepRow$lambda$14$lambda$11(boolean $enabled, Function0 $plus) {
        if ($enabled) {
            $plus.invoke();
        }
        return Unit.INSTANCE;
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x0293, code lost:
    
        if ((r0.length() > 0) != false) goto L59;
     */
    /* JADX WARN: Removed duplicated region for block: B:25:0x02a8  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x040b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final void paint() {
        String str;
        JSONArray ev;
        int i;
        int length;
        JSONObject s;
        String bat;
        getCol().removeAllViews();
        JSONObject s2 = this.status;
        LinearLayout head = card();
        add(head);
        head.addView(title("Power management", "power", "device"));
        if (s2 == null) {
            HubPowerView hubPowerView = this;
            String str2 = this.err;
            if (str2 == null) {
                str2 = "Asking the comma…";
            }
            head.addView(CardPage.line$default(hubPowerView, str2, 0, 0.0f, 6, null));
            return;
        }
        head.addView(CardPage.line$default(this, "What keeps running on the car battery after you turn the car off - the comma itself, and the USB hub with everything on it. Only turning the car off counts; going offroad with the car on doesn't.", 0, 0.0f, 6, null));
        JSONObject c = s2.optJSONObject("comma");
        LinearLayout cc = card();
        add(cc);
        cc.addView(title("Comma", "device", "device"));
        if (c == null) {
            cc.addView(CardPage.line$default(this, "Not available on this comma.", 0, 0.0f, 6, null));
        } else {
            boolean editable = c.optBoolean("editable");
            boolean enabled = c.optBoolean("enabled");
            final int hours = c.optInt("hours", 30);
            final double volts = c.optDouble("volts", 11.8d);
            if (!c.optBoolean("valid", true)) {
                cc.addView(line("Galaxy's saved Parked Power can't be read - switch it off to go back to stock.", CarUi.Companion.getWARN(), 15.0f));
            }
            cc.addView(switchRow("Parked Power", enabled ? "Your limits below. The comma turns itself off after the time, or sooner if the battery drops to the cutoff." : "Off = stock: the comma stays on up to 30 h after you park and turns off only at 11.8 V.", enabled, editable, new Function1() { // from class: com.starpilot.starview.HubPowerView$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    Unit paint$lambda$15;
                    paint$lambda$15 = HubPowerView.paint$lambda$15(HubPowerView.this, ((Boolean) obj).booleanValue());
                    return paint$lambda$15;
                }
            }));
            if (enabled) {
                cc.addView(stepRow("Maximum parked time", hours + " h", editable, new Function0() { // from class: com.starpilot.starview.HubPowerView$$ExternalSyntheticLambda5
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        Unit paint$lambda$16;
                        paint$lambda$16 = HubPowerView.paint$lambda$16(HubPowerView.this, hours);
                        return paint$lambda$16;
                    }
                }, new Function0() { // from class: com.starpilot.starview.HubPowerView$$ExternalSyntheticLambda6
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        Unit paint$lambda$17;
                        paint$lambda$17 = HubPowerView.paint$lambda$17(HubPowerView.this, hours);
                        return paint$lambda$17;
                    }
                }));
                StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
                String format = String.format(Locale.US, "%.1f V", Arrays.copyOf(new Object[]{Double.valueOf(volts)}, 1));
                Intrinsics.checkNotNullExpressionValue(format, "format(...)");
                cc.addView(stepRow("Low-voltage cutoff", format, editable, new Function0() { // from class: com.starpilot.starview.HubPowerView$$ExternalSyntheticLambda7
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        Unit paint$lambda$18;
                        paint$lambda$18 = HubPowerView.paint$lambda$18(HubPowerView.this, volts);
                        return paint$lambda$18;
                    }
                }, new Function0() { // from class: com.starpilot.starview.HubPowerView$$ExternalSyntheticLambda8
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        Unit paint$lambda$19;
                        paint$lambda$19 = HubPowerView.paint$lambda$19(HubPowerView.this, volts);
                        return paint$lambda$19;
                    }
                }));
            }
            if (!editable) {
                cc.addView(line("These change with the car parked (openpilot offroad), like in Galaxy.", CarUi.Companion.getDIM(), 14.0f));
            }
        }
        boolean on = s2.optBoolean("on");
        final int delay = s2.optInt("delay_min", 5);
        LinearLayout hc = card();
        add(hc);
        hc.addView(title("USB hub", "link", "device"));
        if (s2.optBoolean("can_switch")) {
            if (!s2.isNull("error")) {
                String optString = s2.optString("error");
                Intrinsics.checkNotNullExpressionValue(optString, "optString(...)");
                if (optString.length() > 0) {
                    str = "Couldn't switch the port: " + s2.optString("error");
                }
            }
            if (!on) {
                str = "Off: the hub stays on until the comma itself turns off.";
            } else if (Intrinsics.areEqual(s2.optString("car"), "on")) {
                str = "Car on - the hub stays on.";
            } else if (!Intrinsics.areEqual(s2.optString("car"), "off") || s2.isNull("cut_in_s")) {
                str = Intrinsics.areEqual(s2.optString("port"), "off") ? "The hub is off. It comes back when you start the car (or unlock it / open a door)." : "Turns the hub off " + HubDelay.INSTANCE.text(delay) + " after the car.";
            } else {
                str = "Car off - the hub turns off in " + HubDelay.INSTANCE.text(Math.max(1, (s2.optInt("cut_in_s") + 59) / 60)) + ".";
            }
        } else {
            str = "The comma can't switch its USB port - the hub stays on until the comma turns off.";
        }
        String state = str;
        Object obj = "off";
        hc.addView(switchRow("Turn the hub off after the car", state, on, true, new Function1() { // from class: com.starpilot.starview.HubPowerView$$ExternalSyntheticLambda9
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj2) {
                Unit paint$lambda$20;
                paint$lambda$20 = HubPowerView.paint$lambda$20(HubPowerView.this, ((Boolean) obj2).booleanValue());
                return paint$lambda$20;
            }
        }));
        if (s2.optBoolean("can_switch")) {
            if (!s2.isNull("error")) {
                String optString2 = s2.optString("error");
                Intrinsics.checkNotNullExpressionValue(optString2, "optString(...)");
            }
            if (on) {
                hc.addView(stepRow("Turn off after", HubDelay.INSTANCE.text(delay), true, new Function0() { // from class: com.starpilot.starview.HubPowerView$$ExternalSyntheticLambda10
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        Unit paint$lambda$21;
                        paint$lambda$21 = HubPowerView.paint$lambda$21(HubPowerView.this, delay);
                        return paint$lambda$21;
                    }
                }, new Function0() { // from class: com.starpilot.starview.HubPowerView$$ExternalSyntheticLambda11
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        Unit paint$lambda$22;
                        paint$lambda$22 = HubPowerView.paint$lambda$22(HubPowerView.this, delay);
                        return paint$lambda$22;
                    }
                }));
                CarUi ui = getUi();
                Iterable $this$map$iv = HubDelay.INSTANCE.getPRESETS();
                int $i$f$map = 0;
                Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                for (Object item$iv$iv : $this$map$iv) {
                    Iterable $this$map$iv2 = $this$map$iv;
                    int it = ((Number) item$iv$iv).intValue();
                    destination$iv$iv.add(HubDelay.INSTANCE.text(it));
                    $this$map$iv = $this$map$iv2;
                    $i$f$map = $i$f$map;
                }
                Pair<LinearLayout, Function1<Integer, Unit>> chips = ui.chips((List) destination$iv$iv, new Function1() { // from class: com.starpilot.starview.HubPowerView$$ExternalSyntheticLambda12
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj2) {
                        Unit paint$lambda$24;
                        paint$lambda$24 = HubPowerView.paint$lambda$24(HubPowerView.this, ((Integer) obj2).intValue());
                        return paint$lambda$24;
                    }
                });
                LinearLayout chips2 = chips.component1();
                Function1 paintChips = chips.component2();
                paintChips.invoke(Integer.valueOf(HubDelay.INSTANCE.getPRESETS().indexOf(Integer.valueOf(delay))));
                LinearLayout.LayoutParams $this$paint_u24lambda_u2425 = CarUi.lp$default(getUi(), 0, 4, 1, null);
                $this$paint_u24lambda_u2425.topMargin = getUi().dp(6);
                Unit unit = Unit.INSTANCE;
                hc.addView(chips2, $this$paint_u24lambda_u2425);
                double cut = s2.optDouble("hub_cutoff_v", 12.0d);
                if (s2.isNull("battery_v")) {
                    bat = "";
                } else {
                    StringCompanionObject stringCompanionObject2 = StringCompanionObject.INSTANCE;
                    bat = String.format(Locale.US, " Battery now %.1f V.", Arrays.copyOf(new Object[]{Double.valueOf(s2.optDouble("battery_v"))}, 1));
                    Intrinsics.checkNotNullExpressionValue(bat, "format(...)");
                }
                StringCompanionObject stringCompanionObject3 = StringCompanionObject.INSTANCE;
                String format2 = String.format(Locale.US, "Also off right away if the battery drops below %.1f V - 0.2 V above the comma's own cutoff, so the hub goes first.", Arrays.copyOf(new Object[]{Double.valueOf(cut)}, 1));
                Intrinsics.checkNotNullExpressionValue(format2, "format(...)");
                hc.addView(line(format2 + bat, CarUi.Companion.getDIM(), 14.0f));
                hc.addView(line("The key fob's window closing and StarLock's window close need the hub (the radio dongle and relay box are on it), so after parking they work for as long as the hub is on.", CarUi.Companion.getDIM(), 14.0f));
            }
            LinearLayout log = card();
            add(log);
            log.addView(title("Recent", "history", "device"));
            ev = s2.optJSONArray("events");
            if (ev != null && ev.length() != 0) {
                i = 0;
                length = ev.length();
                while (i < length) {
                    JSONObject e = ev.optJSONObject(i);
                    if (e == null) {
                        s = s2;
                    } else {
                        Object obj2 = obj;
                        String what = Intrinsics.areEqual(e.optString("what"), obj2) ? "Hub off" : "Hub on";
                        s = s2;
                        obj = obj2;
                        log.addView(line(this.timeFmt.format(new Date((long) (1000 * e.optDouble("t")))) + "  ·  " + what + "  ·  " + e.optString("why"), CarUi.Companion.getTEXT(), 15.0f));
                    }
                    i++;
                    s2 = s;
                }
                return;
            }
            log.addView(CardPage.line$default(this, "Nothing yet.", 0, 0.0f, 6, null));
        }
        hc.addView(line(state, CarUi.Companion.getWARN(), 15.0f));
        if (on) {
        }
        LinearLayout log2 = card();
        add(log2);
        log2.addView(title("Recent", "history", "device"));
        ev = s2.optJSONArray("events");
        if (ev != null) {
            i = 0;
            length = ev.length();
            while (i < length) {
            }
            return;
        }
        log2.addView(CardPage.line$default(this, "Nothing yet.", 0, 0.0f, 6, null));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paint$lambda$15(HubPowerView this$0, boolean on) {
        this$0.send("comma_set", MapsKt.mapOf(TuplesKt.to("enabled", Boolean.valueOf(on))));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paint$lambda$16(HubPowerView this$0, int $hours) {
        this$0.send("comma_set", MapsKt.mapOf(TuplesKt.to("hours", Integer.valueOf(RangesKt.coerceAtLeast($hours - 1, 1)))));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paint$lambda$17(HubPowerView this$0, int $hours) {
        this$0.send("comma_set", MapsKt.mapOf(TuplesKt.to("hours", Integer.valueOf(RangesKt.coerceAtMost($hours + 1, 30)))));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paint$lambda$18(HubPowerView this$0, double $volts) {
        this$0.send("comma_set", MapsKt.mapOf(TuplesKt.to("volts", Double.valueOf(Math.max(11.8d, $volts - 0.1d)))));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paint$lambda$19(HubPowerView this$0, double $volts) {
        this$0.send("comma_set", MapsKt.mapOf(TuplesKt.to("volts", Double.valueOf(Math.min(12.5d, 0.1d + $volts)))));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paint$lambda$20(HubPowerView this$0, boolean v) {
        this$0.send("set", MapsKt.mapOf(TuplesKt.to("on", Boolean.valueOf(v))));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paint$lambda$21(HubPowerView this$0, int $delay) {
        this$0.send("set", MapsKt.mapOf(TuplesKt.to("delay_min", Integer.valueOf(HubDelay.INSTANCE.step($delay, false)))));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paint$lambda$22(HubPowerView this$0, int $delay) {
        this$0.send("set", MapsKt.mapOf(TuplesKt.to("delay_min", Integer.valueOf(HubDelay.INSTANCE.step($delay, true)))));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paint$lambda$24(HubPowerView this$0, int i) {
        this$0.send("set", MapsKt.mapOf(TuplesKt.to("delay_min", HubDelay.INSTANCE.getPRESETS().get(i))));
        return Unit.INSTANCE;
    }
}
