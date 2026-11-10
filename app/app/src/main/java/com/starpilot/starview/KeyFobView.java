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

/* compiled from: KeyFob.kt */
public final class KeyFobView extends CardPage {
    private String err;
    private JSONArray events;
    private long eventsAt;
    private final KeyFobView$poll$1 poll;
    private final int red;
    private boolean shown;
    private JSONObject status;
    private final SimpleDateFormat timeFmt;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Type inference failed for: r0v6, types: [com.starpilot.starview.KeyFobView$poll$1] */
    public KeyFobView(Context ctx, CarUi ui) {
        super(ctx, ui);
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(ui, "ui");
        this.timeFmt = new SimpleDateFormat("EEE h:mm:ss a", Locale.US);
        this.events = new JSONArray();
        this.red = Color.rgb(255, 138, 128);
        this.poll = new Runnable() { // from class: com.starpilot.starview.KeyFobView$poll$1
            @Override // java.lang.Runnable
            public void run() {
                boolean z;
                JSONObject jSONObject;
                z = KeyFobView.this.shown;
                if (z) {
                    KeyFobView.this.load();
                    Handler main = KeyFobView.this.getMain();
                    KeyFobView$poll$1 keyFobView$poll$1 = this;
                    jSONObject = KeyFobView.this.status;
                    main.postDelayed(keyFobView$poll$1, (jSONObject != null ? jSONObject.optInt("learning") : 0) > 0 ? 1500L : 5000L);
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

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ void req$default(KeyFobView keyFobView, String str, Map map, Function1 function1, int i, Object obj) {
        if ((i & 2) != 0) {
            map = MapsKt.emptyMap();
        }
        keyFobView.req(str, map, function1);
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
                    load$lambda$2$lambda$1 = KeyFobView.load$lambda$2$lambda$1(KeyFobView.this, (JSONObject) obj);
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

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
     */
    private final Pair<String, Integer> radioWords(JSONObject s) {
        String mode = s.optString("mode", "");
        String optString = s.optString("radio");
        if (optString != null) {
            switch (optString.hashCode()) {
                case -1775170701:
                    if (optString.equals("restarting")) {
                        return TuplesKt.to("The radio stopped - starting it again", Integer.valueOf(CarUi.Companion.getWARN()));
                    }
                    break;
                case -1745372787:
                    if (optString.equals("no rtl_sdr")) {
                        return TuplesKt.to("The radio tools aren't on the comma (run /data/tools/rtl_build.sh)", Integer.valueOf(CarUi.Companion.getWARN()));
                    }
                    break;
                case -1218715461:
                    if (optString.equals("listening")) {
                        return TuplesKt.to(Intrinsics.areEqual(mode, "off") ? "Listening - hold Lock 2½ s to close the windows" : "Listening - a press locks or unlocks", Integer.valueOf(CarUi.Companion.getACCENT()));
                    }
                    break;
                case 231075644:
                    if (optString.equals("no dongle")) {
                        return TuplesKt.to("The radio dongle isn't plugged into the comma", Integer.valueOf(CarUi.Companion.getWARN()));
                    }
                    break;
                case 1316806720:
                    if (optString.equals("starting")) {
                        return TuplesKt.to("Starting the radio…", Integer.valueOf(CarUi.Companion.getTEXT()));
                    }
                    break;
                case 1856461213:
                    if (optString.equals("no fob learned")) {
                        return TuplesKt.to("No fob learned yet - add one below", Integer.valueOf(CarUi.Companion.getWARN()));
                    }
                    break;
            }
        }
        return TuplesKt.to(!s.optBoolean("on") ? "Switched off" : "Resting - it listens with the car on in Park, and for 30 minutes after it's off", Integer.valueOf(CarUi.Companion.getDIM()));
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0305  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x030a  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x035f  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x036c  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x037e  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x03d1  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x03d6  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x03ea  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x03d3  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x038c  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x036e  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0362  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x034b  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0307  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final void paint() {
        JSONObject s;
        LinearLayout fobs;
        int learning;
        String what;
        String words;
        JSONArray ids;
        int others;
        String str;
        int text;
        boolean on;
        int i;
        getCol().removeAllViews();
        JSONObject s2 = this.status;
        LinearLayout head = card();
        add(head);
        head.addView(title("Key fob", "key", "device"));
        if (s2 == null) {
            KeyFobView keyFobView = this;
            String str2 = this.err;
            if (str2 == null) {
                str2 = "Asking the comma…";
            }
            head.addView(CardPage.line$default(keyFobView, str2, 0, 0.0f, 6, null));
            return;
        }
        head.addView(CardPage.line$default(this, "Car on, in Park: a press of Lock or Unlock flips the locks (the car ignores its fob while running). Car just turned off (in Park): hold Lock 2½ seconds - the windows close for 15 seconds (a short press stops them). Driving: the radio is off.", 0, 0.0f, 6, null));
        Pair<String, Integer> radioWords = radioWords(s2);
        String words2 = radioWords.component1();
        int color = radioWords.component2().intValue();
        head.addView(line(words2, color, 18.0f));
        int others2 = s2.optInt("others");
        String str3 = "";
        if (others2 > 0) {
            head.addView(line("Ignored " + others2 + " signal" + (others2 == 1 ? "" : "s") + " that weren't your fob (other transmitters nearby) since StarView started.", CarUi.Companion.getDIM(), 14.0f));
        }
        boolean on2 = s2.optBoolean("on");
        LinearLayout $this$paint_u24lambda_u249 = new LinearLayout(getContext());
        $this$paint_u24lambda_u249.setOrientation(0);
        $this$paint_u24lambda_u249.setGravity(16);
        $this$paint_u24lambda_u249.setPadding(0, getUi().dp(10), 0, 0);
        TextView $this$paint_u24lambda_u249_u24lambda_u245 = new TextView($this$paint_u24lambda_u249.getContext());
        $this$paint_u24lambda_u249_u24lambda_u245.setText("Use the key fob");
        $this$paint_u24lambda_u249_u24lambda_u245.setTextColor(CarUi.Companion.getTEXT());
        $this$paint_u24lambda_u249_u24lambda_u245.setTextSize(19.0f);
        $this$paint_u24lambda_u249_u24lambda_u245.setTypeface(Typeface.DEFAULT_BOLD);
        $this$paint_u24lambda_u249.addView($this$paint_u24lambda_u249_u24lambda_u245, new LinearLayout.LayoutParams(0, -2, 1.0f));
        Switch $this$paint_u24lambda_u249_u24lambda_u248 = getUi().bigSwitch();
        $this$paint_u24lambda_u249_u24lambda_u248.setChecked(on2);
        $this$paint_u24lambda_u249_u24lambda_u248.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.starpilot.starview.KeyFobView$$ExternalSyntheticLambda1
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                KeyFobView.paint$lambda$9$lambda$8$lambda$7(KeyFobView.this, compoundButton, z);
            }
        });
        $this$paint_u24lambda_u249.addView($this$paint_u24lambda_u249_u24lambda_u248);
        head.addView($this$paint_u24lambda_u249);
        LinearLayout fobs2 = card();
        add(fobs2);
        fobs2.addView(title("Fobs", "lock", "device"));
        JSONArray ids2 = s2.optJSONArray("ids");
        if (ids2 == null) {
            ids2 = new JSONArray();
        }
        if (ids2.length() == 0) {
            fobs2.addView(CardPage.line$default(this, "None yet.", 0, 0.0f, 6, null));
        }
        int i2 = 0;
        int length = ids2.length();
        while (i2 < length) {
            final String id = ids2.optString(i2);
            fobs2.addView(line("Fob " + (i2 + 1) + "  ·  " + id, CarUi.Companion.getTEXT(), 17.0f));
            fobs2.addView(buttons(CarUi.button$default(getUi(), "Forget", false, new Function0() { // from class: com.starpilot.starview.KeyFobView$$ExternalSyntheticLambda2
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit paint$lambda$11;
                    paint$lambda$11 = KeyFobView.paint$lambda$11(KeyFobView.this, id);
                    return paint$lambda$11;
                }
            }, 2, null)));
            i2++;
            head = head;
        }
        int learning2 = s2.optInt("learning");
        if (learning2 > 0) {
            fobs2.addView(line("Press Lock on the fob twice, a few seconds apart, near the van (" + learning2 + "s left).", CarUi.Companion.getACCENT(), 18.0f));
        } else {
            View[] viewArr = new View[1];
            viewArr[0] = getUi().button("Add a fob…", ids2.length() == 0, new Function0() { // from class: com.starpilot.starview.KeyFobView$$ExternalSyntheticLambda3
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit paint$lambda$13;
                    paint$lambda$13 = KeyFobView.paint$lambda$13(KeyFobView.this);
                    return paint$lambda$13;
                }
            });
            fobs2.addView(buttons(viewArr));
        }
        LinearLayout log = card();
        add(log);
        log.addView(title("What it heard", "history", "device"));
        if (this.events.length() == 0) {
            log.addView(CardPage.line$default(this, "Nothing yet.", 0, 0.0f, 6, null));
        }
        int i3 = 0;
        int min = Math.min(this.events.length(), 60);
        while (i3 < min) {
            JSONObject e = this.events.optJSONObject(i3);
            if (e == null) {
                s = s2;
                learning = learning2;
                fobs = fobs2;
                words = words2;
                ids = ids2;
                others = others2;
                str = str3;
                on = on2;
                i = min;
            } else {
                String did = e.optString("did");
                if (did != null) {
                    switch (did.hashCode()) {
                        case -1097452790:
                            s = s2;
                            if (did.equals("locked")) {
                                what = "Locked";
                                learning = learning2;
                                fobs = fobs2;
                                break;
                            }
                            Intrinsics.checkNotNull(did);
                            if (!(did.length() <= 0)) {
                                StringBuilder sb = new StringBuilder();
                                learning = learning2;
                                char it = did.charAt(0);
                                fobs = fobs2;
                                String valueOf = String.valueOf(it);
                                Intrinsics.checkNotNull(valueOf, "null cannot be cast to non-null type java.lang.String");
                                String upperCase = valueOf.toUpperCase(Locale.ROOT);
                                Intrinsics.checkNotNullExpressionValue(upperCase, "toUpperCase(...)");
                                StringBuilder append = sb.append((Object) upperCase);
                                String substring = did.substring(1);
                                Intrinsics.checkNotNullExpressionValue(substring, "substring(...)");
                                what = append.append(substring).toString();
                                break;
                            } else {
                                learning = learning2;
                                fobs = fobs2;
                                what = did;
                                break;
                            }
                        case -210949405:
                            s = s2;
                            if (did.equals("unlocked")) {
                                what = "Unlocked";
                                learning = learning2;
                                fobs = fobs2;
                                break;
                            }
                            Intrinsics.checkNotNull(did);
                            if (!(did.length() <= 0)) {
                            }
                            break;
                        case 50780643:
                            s = s2;
                            if (did.equals("learned")) {
                                what = "Learned";
                                learning = learning2;
                                fobs = fobs2;
                                break;
                            }
                            Intrinsics.checkNotNull(did);
                            if (!(did.length() <= 0)) {
                            }
                            break;
                        case 1349493379:
                            s = s2;
                            if (did.equals("windows")) {
                                what = "Closed the windows";
                                learning = learning2;
                                fobs = fobs2;
                                break;
                            }
                            Intrinsics.checkNotNull(did);
                            if (!(did.length() <= 0)) {
                            }
                            break;
                        case 2129323981:
                            if (did.equals("nothing")) {
                                learning = learning2;
                                fobs = fobs2;
                                s = s2;
                                what = "Nothing";
                                break;
                            } else {
                                s = s2;
                                Intrinsics.checkNotNull(did);
                                if (!(did.length() <= 0)) {
                                }
                            }
                            break;
                    }
                    String kind = !Intrinsics.areEqual(e.optString("kind"), "hold") ? "Hold" : "Press";
                    String who = !e.optBoolean("ours") ? str3 : "  ·  not yours";
                    words = words2;
                    String why = e.optString("why", str3);
                    if (Intrinsics.areEqual(did, "nothing")) {
                        Intrinsics.checkNotNull(did);
                        ids = ids2;
                        others = others2;
                        str = str3;
                        text = StringsKt.endsWith$default(did, "failed", false, 2, (Object) null) ? this.red : CarUi.Companion.getTEXT();
                    } else {
                        text = CarUi.Companion.getDIM();
                        ids = ids2;
                        others = others2;
                        str = str3;
                    }
                    int c = text;
                    on = on2;
                    i = min;
                    String format = this.timeFmt.format(new Date((long) (1000 * e.optDouble("t"))));
                    Intrinsics.checkNotNull(why);
                    log.addView(line(format + "  ·  " + kind + who + "  ·  " + what + (!(why.length() <= 0) ? " - " + why : str), c, 15.0f));
                }
                s = s2;
                Intrinsics.checkNotNull(did);
                if (!(did.length() <= 0)) {
                }
                if (!Intrinsics.areEqual(e.optString("kind"), "hold")) {
                }
                if (!e.optBoolean("ours")) {
                }
                words = words2;
                String why2 = e.optString("why", str3);
                if (Intrinsics.areEqual(did, "nothing")) {
                }
                int c2 = text;
                on = on2;
                i = min;
                String format2 = this.timeFmt.format(new Date((long) (1000 * e.optDouble("t"))));
                Intrinsics.checkNotNull(why2);
                log.addView(line(format2 + "  ·  " + kind + who + "  ·  " + what + (!(why2.length() <= 0) ? " - " + why2 : str), c2, 15.0f));
            }
            i3++;
            s2 = s;
            learning2 = learning;
            words2 = words;
            fobs2 = fobs;
            ids2 = ids;
            others2 = others;
            str3 = str;
            on2 = on;
            min = i;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void paint$lambda$9$lambda$8$lambda$7(final KeyFobView this$0, CompoundButton compoundButton, boolean v) {
        this$0.req("set", MapsKt.mapOf(TuplesKt.to("on", Boolean.valueOf(v))), new Function1() { // from class: com.starpilot.starview.KeyFobView$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit paint$lambda$9$lambda$8$lambda$7$lambda$6;
                paint$lambda$9$lambda$8$lambda$7$lambda$6 = KeyFobView.paint$lambda$9$lambda$8$lambda$7$lambda$6(KeyFobView.this, (JSONObject) obj);
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
                paint$lambda$11$lambda$10 = KeyFobView.paint$lambda$11$lambda$10(KeyFobView.this, (JSONObject) obj);
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
                paint$lambda$13$lambda$12 = KeyFobView.paint$lambda$13$lambda$12(KeyFobView.this, (JSONObject) obj);
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
