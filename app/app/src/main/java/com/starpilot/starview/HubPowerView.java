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
import android.widget.LinearLayout.LayoutParams;
import java.util.Iterator;

/* compiled from: HubPower.kt */
public final class HubPowerView extends CardPage {
    private String err;
    private final Runnable poll;
    private boolean shown;
    private JSONObject status;
    private final SimpleDateFormat timeFmt;

    /* VF: from Vineflower (jadx: Type inference failed) */
    public HubPowerView(Context var1, CarUi var2) {
        super(var1, var2);
        Intrinsics.checkNotNullParameter(var1, "ctx");
        Intrinsics.checkNotNullParameter(var2, "ui");
        this.timeFmt = new SimpleDateFormat("EEE h:mm a", Locale.US);
        this.poll = new Runnable() {
            final HubPowerView this$0 = HubPowerView.this;

            @Override
            public void run() {
                if (this.this$0.shown) {
                    this.this$0.load();
                    this.this$0.getMain().postDelayed(this, 5000L);
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

    /* VF: from Vineflower (jadx: ) */
    static void req$default(HubPowerView var0, String var1, Map var2, Function1 var3, int var4, Object var5) {
        if ((var4 & 2) != 0) {
            var2 = MapsKt.emptyMap();
        }

        var0.req(var1, var2, var3);
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
        CarUi.Companion_ companion = CarUi.Companion;
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
                HubPowerView.switchRow$lambda$7$lambda$6$lambda$5(onFlip, compoundButton, z);
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
        CarUi.Companion_ companion = CarUi.Companion;
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
        CarUi.Companion_ companion2 = CarUi.Companion;
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

    /* VF: from Vineflower (jadx: Code restructure failed;Removed duplicated region;incorrect) */
    private final void paint() {
        String var41 = null; // declarations Vineflower left out
        this.getCol().removeAllViews();
        JSONObject var8 = this.status;
        LinearLayout var9 = this.card();
        this.add((View)var9);
        var9.addView(this.title("Power management", "power", "device"));
        if (var8 == null) {
            CardPage var44 = this;
            String var39 = this.err;
            String var38 = var39;
            if (var39 == null) {
                var38 = "Asking the comma…";
            }

            var9.addView((View)CardPage.line$default(var44, var38, 0, 0.0F, 6, null));
        } else {
            var9.addView(
                (View)CardPage.line$default(
                    this,
                    "What keeps running on the car battery after you turn the car off - the comma itself, and the USB hub with everything on it. Only turning the car off counts; going offroad with the car on doesn't.",
                    0,
                    0.0F,
                    6,
                    null
                )
            );
            JSONObject var7 = var8.optJSONObject("comma");
            var9 = this.card();
            this.add((View)var9);
            var9.addView(this.title("Comma", "device", "device"));
            if (var7 == null) {
                var9.addView((View)CardPage.line$default(this, "Not available on this comma.", 0, 0.0F, 6, null));
            } else {
                boolean var6 = var7.optBoolean("editable");
                boolean var5 = var7.optBoolean("enabled");
                int var3 = var7.optInt("hours", 30);
                double var1 = var7.optDouble("volts", 11.8);
                if (!var7.optBoolean("valid", true)) {
                    var9.addView((View)this.line("Galaxy's saved Parked Power can't be read - switch it off to go back to stock.", CarUi.Companion.getWARN(), 15.0F));
                }

                String var23;
                if (var5) {
                    var23 = "Your limits below. The comma turns itself off after the time, or sooner if the battery drops to the cutoff.";
                } else {
                    var23 = "Off = stock: the comma stays on up to 30 h after you park and turns off only at 11.8 V.";
                }

                var9.addView(this.switchRow("Parked Power", var23, var5, var6, new HubPowerView$$ExternalSyntheticLambda0(this)));
                if (var5) {
                    var9.addView(
                        this.stepRow(
                            "Maximum parked time",
                            var3 + " h",
                            var6,
                            new HubPowerView$$ExternalSyntheticLambda5(this, var3),
                            new HubPowerView$$ExternalSyntheticLambda6(this, var3)
                        )
                    );
                    StringCompanionObject var24 = StringCompanionObject.INSTANCE;
                    String var25 = String.format(Locale.US, "%.1f V", Arrays.copyOf(new Object[]{var1}, 1));
                    Intrinsics.checkNotNullExpressionValue(var25, "format(...)");
                    var9.addView(
                        this.stepRow(
                            "Low-voltage cutoff",
                            var25,
                            var6,
                            new HubPowerView$$ExternalSyntheticLambda7(this, var1),
                            new HubPowerView$$ExternalSyntheticLambda8(this, var1)
                        )
                    );
                }

                if (!var6) {
                    var9.addView((View)this.line("These change with the car parked (openpilot offroad), like in Galaxy.", CarUi.Companion.getDIM(), 14.0F));
                }
            }

            boolean var21 = var8.optBoolean("on");
            int var4 = var8.optInt("delay_min", 5);
            LinearLayout var10 = this.card();
            this.add((View)var10);
            var10.addView(this.title("USB hub", "link", "device"));
            String var26;
            if (!var8.optBoolean("can_switch")) {
                var26 = "The comma can't switch its USB port - the hub stays on until the comma turns off.";
            } else {
                label122: {
                    if (!var8.isNull("error")) {
                        String var27 = var8.optString("error");
                        Intrinsics.checkNotNullExpressionValue(var27, "optString(...)");
                        boolean var16;
                        if (var27.length() > 0) {
                            var16 = true;
                        } else {
                            var16 = false;
                        }

                        if (var16) {
                            String var30 = var8.optString("error");
                            var26 = "Couldn't switch the port: " + var30;
                            break label122;
                        }
                    }

                    if (!var21) {
                        var26 = "Off: the hub stays on until the comma itself turns off.";
                    } else if (Intrinsics.areEqual(var8.optString("car"), "on")) {
                        var26 = "Car on - the hub stays on.";
                    } else if (Intrinsics.areEqual(var8.optString("car"), "off") && !var8.isNull("cut_in_s")) {
                        String var29 = HubDelay.INSTANCE.text(Math.max(1, (var8.optInt("cut_in_s") + 59) / 60));
                        var26 = "Car off - the hub turns off in " + var29 + ".";
                    } else if (Intrinsics.areEqual(var8.optString("port"), "off")) {
                        var26 = "The hub is off. It comes back when you start the car (or unlock it / open a door).";
                    } else {
                        String var28 = HubDelay.INSTANCE.text(var4);
                        var26 = "Turns the hub off " + var28 + " after the car.";
                    }
                }
            }

            label110: {
                HubPowerView$$ExternalSyntheticLambda9 var11 = new HubPowerView$$ExternalSyntheticLambda9(this);
                var41 = "off";
                var10.addView(this.switchRow("Turn the hub off after the car", var26, var21, true, var11));
                if (var8.optBoolean("can_switch")) {
                    if (var8.isNull("error")) {
                        break label110;
                    }

                    String var45 = var8.optString("error");
                    Intrinsics.checkNotNullExpressionValue(var45, "optString(...)");
                    boolean var17;
                    if (var45.length() > 0) {
                        var17 = true;
                    } else {
                        var17 = false;
                    }

                    if (!var17) {
                        break label110;
                    }
                }

                var10.addView((View)this.line(var26, CarUi.Companion.getWARN(), 15.0F));
            }

            if (var21) {
                var10.addView(
                    this.stepRow(
                        "Turn off after",
                        HubDelay.INSTANCE.text(var4),
                        true,
                        new HubPowerView$$ExternalSyntheticLambda10(this, var4),
                        new HubPowerView$$ExternalSyntheticLambda11(this, var4)
                    )
                );
                CarUi var46 = this.getUi();
                Iterable var31 = HubDelay.INSTANCE.getPRESETS();
                var21 = false;
                var var13 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var31, 10));
                Iterator var12 = var31.iterator();

                while (var12.hasNext()) {
                    int var18 = ((Number)var12.next()).intValue();
                    var13.add(HubDelay.INSTANCE.text(var18));
                }

                List var32 = var13;
                Pair var47 = var46.chips(var32, new HubPowerView$$ExternalSyntheticLambda12(this));
                LinearLayout var33 = (LinearLayout)var47.component1();
                ((Function1)var47.component2()).invoke(HubDelay.INSTANCE.getPRESETS().indexOf(var4));
                View var52 = (View)var33;
                android.widget.LinearLayout.LayoutParams var48 = CarUi.lp$default(this.getUi(), 0, 4, 1, null);
                var48.topMargin = this.getUi().dp(6);
                Unit var34 = Unit.INSTANCE;
                var10.addView(var52, (android.view.ViewGroup.LayoutParams)var48);
                double var14 = var8.optDouble("hub_cutoff_v", 12.0);
                String var35;
                if (var8.isNull("battery_v")) {
                    var35 = "";
                } else {
                    StringCompanionObject var36 = StringCompanionObject.INSTANCE;
                    var35 = String.format(Locale.US, " Battery now %.1f V.", Arrays.copyOf(new Object[]{var8.optDouble("battery_v")}, 1));
                    Intrinsics.checkNotNullExpressionValue(var35, "format(...)");
                }

                StringCompanionObject var49 = StringCompanionObject.INSTANCE;
                String var50 = String.format(
                    Locale.US,
                    "Also off right away if the battery drops below %.1f V - 0.2 V above the comma's own cutoff, so the hub goes first.",
                    Arrays.copyOf(new Object[]{var14}, 1)
                );
                Intrinsics.checkNotNullExpressionValue(var50, "format(...)");
                var10.addView((View)this.line(var50 + var35, CarUi.Companion.getDIM(), 14.0F));
                var10.addView(
                    (View)this.line(
                        "The key fob's window closing and StarLock's window close need the hub (the radio dongle and relay box are on it), so after parking they work for as long as the hub is on.",
                        CarUi.Companion.getDIM(),
                        14.0F
                    )
                );
            }

            LinearLayout var51 = this.card();
            this.add((View)var51);
            var51.addView(this.title("Recent", "history", "device"));
            JSONArray var43 = var8.optJSONArray("events");
            if (var43 != null && var43.length() != 0) {
                int var19 = 0;
                var4 = var43.length();

                for (String var37 = var41; var19 < var4; var19++) {
                    JSONObject var53 = var43.optJSONObject(var19);
                    if (var53 != null) {
                        String var42;
                        if (Intrinsics.areEqual(var53.optString("what"), var37)) {
                            var42 = "Hub off";
                        } else {
                            var42 = "Hub on";
                        }

                        SimpleDateFormat var55 = this.timeFmt;
                        double var15 = var53.optDouble("t");
                        String var56 = var55.format(new Date((long)(1000 * var15)));
                        String var54 = var53.optString("why");
                        var51.addView((View)this.line(var56 + "  ·  " + var42 + "  ·  " + var54, CarUi.Companion.getTEXT(), 15.0F));
                    }
                }
            } else {
                var51.addView((View)CardPage.line$default(this, "Nothing yet.", 0, 0.0F, 6, null));
            }
        }
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
