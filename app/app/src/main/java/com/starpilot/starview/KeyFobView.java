package com.starpilot.starview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.json.JSONArray;
import org.json.JSONObject;
import android.widget.LinearLayout.LayoutParams;

/* compiled from: KeyFob.kt */
public final class KeyFobView extends CardPage {
    private String err;
    private JSONArray events;
    private long eventsAt;
    private final Runnable poll;
    private final int red;
    private boolean shown;
    private JSONObject status;
    private final SimpleDateFormat timeFmt;

    /* VF: from Vineflower (jadx: Type inference failed) */
    public KeyFobView(Context var1, CarUi var2) {
        super(var1, var2);
        Intrinsics.checkNotNullParameter(var1, "ctx");
        Intrinsics.checkNotNullParameter(var2, "ui");
        this.timeFmt = new SimpleDateFormat("EEE h:mm:ss a", Locale.US);
        this.events = new JSONArray();
        this.red = Color.rgb(255, 138, 128);
        this.poll = new Runnable() {
            final KeyFobView this$0 = KeyFobView.this;

            @Override
            public void run() {
                if (this.this$0.shown) {
                    this.this$0.load();
                    Handler var4 = this.this$0.getMain();
                    Runnable var6 = this;
                    JSONObject var5 = this.this$0.status;
                    int var1x;
                    if (var5 != null) {
                        var1x = var5.optInt("learning");
                    } else {
                        var1x = 0;
                    }

                    long var2x;
                    if (var1x > 0) {
                        var2x = 1500L;
                    } else {
                        var2x = 5000L;
                    }

                    var4.postDelayed(var6, var2x);
                }
            }
        };
    }

    public final void start() {
        this.shown = true;
        getMain().removeCallbacks(this.poll);
        this.eventsAt = 0L;
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
    static void req$default(KeyFobView var0, String var1, Map var2, Function1 var3, int var4, Object var5) {
        if ((var4 & 2) != 0) {
            var2 = MapsKt.emptyMap();
        }

        var0.req(var1, var2, var3);
    }

    private final void req(String op, Map<String, ? extends Object> extra, Function1<? super JSONObject, Unit> cb) {
        DataClient d = Link.INSTANCE.getData();
        if (d != null) {
            d.request("fob", MapsKt.plus(MapsKt.mapOf(TuplesKt.to("op", op)), extra), cb);
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
        req$default(this, "status", null, new Function1() { // from class: com.starpilot.starview.KeyFobView$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit load$lambda$2;
                load$lambda$2 = KeyFobView.load$lambda$2(KeyFobView.this, (JSONObject) obj);
                return load$lambda$2;
            }
        }, 2, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit load$lambda$2(final KeyFobView this$0, JSONObject r) {
        JSONObject optJSONObject;
        Intrinsics.checkNotNullParameter(r, "r");
        Double d = null;
        if (r.optBoolean("ok")) {
            this$0.status = r;
            this$0.err = null;
        } else {
            this$0.err = r.optString("error", "The comma didn't answer");
        }
        long now = System.currentTimeMillis();
        JSONArray a = r.optJSONArray("recent");
        if (a != null && a.length() > 0 && (optJSONObject = a.optJSONObject(a.length() - 1)) != null) {
            d = Double.valueOf(optJSONObject.optDouble("t"));
        }
        Double newest = d;
        if (now - this$0.eventsAt > 30000 || ((newest != null && this$0.events.length() > 0 && newest.doubleValue() > this$0.events.optJSONObject(0).optDouble("t")) || (newest != null && this$0.events.length() == 0))) {
            this$0.eventsAt = now;
            req$default(this$0, "history", null, new Function1() { // from class: com.starpilot.starview.KeyFobView$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    Unit load$lambda$2$lambda$1;
                    load$lambda$2$lambda$1 = KeyFobView.load$lambda$2$lambda$1(this$0, (JSONObject) obj);
                    return load$lambda$2$lambda$1;
                }
            }, 2, null);
        } else {
            this$0.paint();
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit load$lambda$2$lambda$1(KeyFobView this$0, JSONObject h) {
        Intrinsics.checkNotNullParameter(h, "h");
        if (h.optBoolean("ok")) {
            JSONArray optJSONArray = h.optJSONArray("events");
            if (optJSONArray == null) {
                optJSONArray = new JSONArray();
            }
            this$0.events = optJSONArray;
        }
        this$0.paint();
        return Unit.INSTANCE;
    }

    private final View buttons(View... b) {
        LinearLayout $this$buttons_u24lambda_u244 = new LinearLayout(getContext());
        $this$buttons_u24lambda_u244.setOrientation(0);
        $this$buttons_u24lambda_u244.setPadding(0, getUi().dp(10), 0, 0);
        for (View v : b) {
            LinearLayout.LayoutParams $this$buttons_u24lambda_u244_u24lambda_u243 = new LinearLayout.LayoutParams(-2, -2);
            $this$buttons_u24lambda_u244_u24lambda_u243.rightMargin = getUi().dp(12);
            Unit unit = Unit.INSTANCE;
            $this$buttons_u24lambda_u244.addView(v, $this$buttons_u24lambda_u244_u24lambda_u243);
        }
        return $this$buttons_u24lambda_u244;
    }

    /* VF: from Vineflower (jadx: ) */
    private final Pair<String, Integer> radioWords(JSONObject var1) {
        String var2 = var1.optString("mode", "");
        String var3 = var1.optString("radio");
        if (var3 != null) {
            switch (var3.hashCode()) {
                case -1775170701:
                    if (var3.equals("restarting")) {
                        return TuplesKt.to("The radio stopped - starting it again", CarUi.Companion.getWARN());
                    }
                    break;
                case -1745372787:
                    if (var3.equals("no rtl_sdr")) {
                        return TuplesKt.to("The radio tools aren't on the comma (run /data/tools/rtl_build.sh)", CarUi.Companion.getWARN());
                    }
                    break;
                case -1218715461:
                    if (var3.equals("listening")) {
                        String var5;
                        if (Intrinsics.areEqual(var2, "off")) {
                            var5 = "Listening - hold Lock 2½ s to close the windows";
                        } else {
                            var5 = "Listening - a press locks or unlocks";
                        }

                        return TuplesKt.to(var5, CarUi.Companion.getACCENT());
                    }
                    break;
                case 231075644:
                    if (var3.equals("no dongle")) {
                        return TuplesKt.to("The radio dongle isn't plugged into the comma", CarUi.Companion.getWARN());
                    }
                    break;
                case 1316806720:
                    if (var3.equals("starting")) {
                        return TuplesKt.to("Starting the radio…", CarUi.Companion.getTEXT());
                    }
                    break;
                case 1856461213:
                    if (var3.equals("no fob learned")) {
                        return TuplesKt.to("No fob learned yet - add one below", CarUi.Companion.getWARN());
                    }
            }
        }

        String var6;
        if (!var1.optBoolean("on")) {
            var6 = "Switched off";
        } else {
            var6 = "Resting - it listens with the car on in Park, and for 30 minutes after it's off";
        }

        return TuplesKt.to(var6, CarUi.Companion.getDIM());
    }

    /* VF: from Vineflower (jadx: Removed duplicated region;incorrect) */
    private final void paint() {
        String var29 = null; // declarations Vineflower left out
        this.getCol().removeAllViews();
        JSONObject var16 = this.status;
        LinearLayout var12 = this.card();
        this.add((View)var12);
        var12.addView(this.title("Key fob", "key", "device"));
        if (var16 == null) {
            CardPage var38 = this;
            String var35 = this.err;
            String var32 = var35;
            if (var35 == null) {
                var32 = "Asking the comma…";
            }

            var12.addView((View)CardPage.line$default(var38, var32, 0, 0.0F, 6, null));
        } else {
            var12.addView(
                (View)CardPage.line$default(
                    this,
                    "Car on, in Park: a press of Lock or Unlock flips the locks (the car ignores its fob while running). Car just turned off (in Park): hold Lock 2½ seconds - the windows close for 15 seconds (a short press stops them). Driving: the radio is off.",
                    0,
                    0.0F,
                    6,
                    null
                )
            );
            Pair var11 = this.radioWords(var16);
            String var15 = (String)var11.component1();
            var12.addView((View)this.line(var15, ((Number)var11.component2()).intValue(), 18.0F));
            int var5 = var16.optInt("others");
            String var13 = "";
            if (var5 > 0) {
                String var26;
                if (var5 == 1) {
                    var26 = "";
                } else {
                    var26 = "s";
                }

                var12.addView(
                    (View)this.line(
                        "Ignored " + var5 + " signal" + var26 + " that weren't your fob (other transmitters nearby) since StarView started.",
                        CarUi.Companion.getDIM(),
                        14.0F
                    )
                );
            }

            boolean var10 = var16.optBoolean("on");
            LinearLayout var27 = new LinearLayout(this.getContext());
            var27.setOrientation(0);
            var27.setGravity(16);
            var27.setPadding(0, this.getUi().dp(10), 0, 0);
            TextView var14 = new TextView(var27.getContext());
            var14.setText("Use the key fob");
            var14.setTextColor(CarUi.Companion.getTEXT());
            var14.setTextSize(19.0F);
            var14.setTypeface(Typeface.DEFAULT_BOLD);
            var27.addView((View)var14, (android.view.ViewGroup.LayoutParams)(new LayoutParams(0, -2, 1.0F)));
            Switch var36 = this.getUi().bigSwitch();
            var36.setChecked(var10);
            var36.setOnCheckedChangeListener(new KeyFobView$$ExternalSyntheticLambda1(this));
            var27.addView((View)var36);
            var12.addView((View)var27);
            LinearLayout var37 = this.card();
            this.add((View)var37);
            var37.addView(this.title("Fobs", "lock", "device"));
            JSONArray var17 = var16.optJSONArray("ids");
            JSONArray var28 = var17;
            if (var17 == null) {
                var28 = new JSONArray();
            }

            if (var28.length() == 0) {
                var37.addView((View)CardPage.line$default(this, "None yet.", 0, 0.0F, 6, null));
            }

            int var3 = 0;

            for (int var4 = var28.length(); var3 < var4; var3++) {
                String var39 = var28.optString(var3);
                var37.addView((View)this.line("Fob " + (var3 + 1) + "  ·  " + var39, CarUi.Companion.getTEXT(), 17.0F));
                var37.addView(this.buttons(CarUi.button$default(this.getUi(), "Forget", false, new KeyFobView$$ExternalSyntheticLambda2(this, var39), 2, null)));
            }

            int var6 = var16.optInt("learning");
            if (var6 > 0) {
                var37.addView(
                    (View)this.line("Press Lock on the fob twice, a few seconds apart, near the van (" + var6 + "s left).", CarUi.Companion.getACCENT(), 18.0F)
                );
            } else {
                CarUi var33 = this.getUi();
                boolean var9;
                if (var28.length() == 0) {
                    var9 = true;
                } else {
                    var9 = false;
                }

                var37.addView(this.buttons(var33.button("Add a fob…", var9, new KeyFobView$$ExternalSyntheticLambda3(this))));
            }

            LinearLayout var20 = this.card();
            this.add((View)var20);
            var20.addView(this.title("What it heard", "history", "device"));
            if (this.events.length() == 0) {
                var20.addView((View)CardPage.line$default(this, "Nothing yet.", 0, 0.0F, 6, null));
            }

            int var7 = 0;
            int var25 = Math.min(this.events.length(), 60);

            for (String var34 = var13; var7 < var25; var7++) {
                JSONObject var21 = this.events.optJSONObject(var7);
                if (var21 != null) {
                    String var19;
                    label136: {
                        var19 = var21.optString("did");
                        if (var19 != null) {
                            switch (var19.hashCode()) {
                                case -1097452790:
                                    if (var19.equals("locked")) {
                                        var29 = "Locked";
                                        break label136;
                                    }
                                    break;
                                case -210949405:
                                    if (var19.equals("unlocked")) {
                                        var29 = "Unlocked";
                                        break label136;
                                    }
                                    break;
                                case 50780643:
                                    if (var19.equals("learned")) {
                                        var29 = "Learned";
                                        break label136;
                                    }
                                    break;
                                case 1349493379:
                                    if (var19.equals("windows")) {
                                        var29 = "Closed the windows";
                                        break label136;
                                    }
                                    break;
                                case 2129323981:
                                    if (var19.equals("nothing")) {
                                        var29 = "Nothing";
                                        break label136;
                                    }
                            }
                        }

                        Intrinsics.checkNotNull(var19);
                        boolean var23;
                        if (var19.length() > 0) {
                            var23 = true;
                        } else {
                            var23 = false;
                        }

                        if (var23) {
                            StringBuilder var30 = new StringBuilder();
                            String var40 = String.valueOf(var19.charAt(0));
                            Intrinsics.checkNotNull(var40, "null cannot be cast to non-null type java.lang.String");
                            String var41 = var40.toUpperCase(Locale.ROOT);
                            Intrinsics.checkNotNullExpressionValue(var41, "toUpperCase(...)");
                            StringBuilder var31 = var30.append(var41);
                            String var42 = var19.substring(1);
                            Intrinsics.checkNotNullExpressionValue(var42, "substring(...)");
                            var29 = var31.append(var42).toString();
                        } else {
                            var29 = var19;
                        }
                    }

                    String var43;
                    if (Intrinsics.areEqual(var21.optString("kind"), "hold")) {
                        var43 = "Hold";
                    } else {
                        var43 = "Press";
                    }

                    String var18;
                    if (var21.optBoolean("ours")) {
                        var18 = var34;
                    } else {
                        var18 = "  ·  not yours";
                    }

                    String var22 = var21.optString("why", var34);
                    if (Intrinsics.areEqual(var19, "nothing")) {
                        var3 = CarUi.Companion.getDIM();
                    } else {
                        Intrinsics.checkNotNull(var19);
                        if (StringsKt.endsWith(var19, "failed", false)) {
                            var3 = this.red;
                        } else {
                            var3 = CarUi.Companion.getTEXT();
                        }
                    }

                    SimpleDateFormat var44 = this.timeFmt;
                    double var1 = var21.optDouble("t");
                    String var46 = var44.format(new Date((long)(1000 * var1)));
                    Intrinsics.checkNotNull(var22);
                    boolean var8;
                    if (var22.length() > 0) {
                        var8 = true;
                    } else {
                        var8 = false;
                    }

                    if (var8) {
                        var19 = " - " + var22;
                    } else {
                        var19 = var34;
                    }

                    var20.addView((View)this.line(var46 + "  ·  " + var43 + var18 + "  ·  " + var29 + var19, var3, 15.0F));
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void paint$lambda$9$lambda$8$lambda$7(final KeyFobView this$0, CompoundButton compoundButton, boolean v) {
        this$0.req("set", MapsKt.mapOf(TuplesKt.to("on", Boolean.valueOf(v))), new Function1() { // from class: com.starpilot.starview.KeyFobView$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit paint$lambda$9$lambda$8$lambda$7$lambda$6;
                paint$lambda$9$lambda$8$lambda$7$lambda$6 = KeyFobView.paint$lambda$9$lambda$8$lambda$7$lambda$6(this$0, (JSONObject) obj);
                return paint$lambda$9$lambda$8$lambda$7$lambda$6;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paint$lambda$9$lambda$8$lambda$7$lambda$6(KeyFobView this$0, JSONObject r) {
        Intrinsics.checkNotNullParameter(r, "r");
        if (r.optBoolean("ok")) {
            this$0.status = r;
        } else {
            String optString = r.optString("error");
            Intrinsics.checkNotNullExpressionValue(optString, "optString(...)");
            this$0.toast(optString);
        }
        this$0.paint();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paint$lambda$11(final KeyFobView this$0, String $id) {
        this$0.req("forget", MapsKt.mapOf(TuplesKt.to("id", $id)), new Function1() { // from class: com.starpilot.starview.KeyFobView$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit paint$lambda$11$lambda$10;
                paint$lambda$11$lambda$10 = KeyFobView.paint$lambda$11$lambda$10(this$0, (JSONObject) obj);
                return paint$lambda$11$lambda$10;
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paint$lambda$11$lambda$10(KeyFobView this$0, JSONObject r) {
        Intrinsics.checkNotNullParameter(r, "r");
        if (r.optBoolean("ok")) {
            this$0.status = r;
            this$0.paint();
        } else {
            String optString = r.optString("error");
            Intrinsics.checkNotNullExpressionValue(optString, "optString(...)");
            this$0.toast(optString);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paint$lambda$13(final KeyFobView this$0) {
        req$default(this$0, "learn", null, new Function1() { // from class: com.starpilot.starview.KeyFobView$$ExternalSyntheticLambda7
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit paint$lambda$13$lambda$12;
                paint$lambda$13$lambda$12 = KeyFobView.paint$lambda$13$lambda$12(this$0, (JSONObject) obj);
                return paint$lambda$13$lambda$12;
            }
        }, 2, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paint$lambda$13$lambda$12(KeyFobView this$0, JSONObject r) {
        Intrinsics.checkNotNullParameter(r, "r");
        if (r.optBoolean("ok")) {
            this$0.status = r;
            this$0.paint();
            this$0.getMain().removeCallbacks(this$0.poll);
            this$0.getMain().postDelayed(this$0.poll, 1500L);
        } else {
            String optString = r.optString("error");
            Intrinsics.checkNotNullExpressionValue(optString, "optString(...)");
            this$0.toast(optString);
        }
        return Unit.INSTANCE;
    }
}
