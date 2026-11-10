package com.starpilot.starview;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.starpilot.starview.GxPage;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Triple;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: GxVehicle.kt */
public final class GxVehiclePage extends GxPage {
    private String checking;
    private int curTab;
    private String doorBusy;
    private final HashMap<String, TextView> featureValues;
    private final List<Feature> features;
    private final HashMap<String, Gate> gates;
    private LinearLayout keyBox;
    private List<? extends JSONObject> keys;
    private boolean keysLoaded;
    private TextView lockBtn;
    private Function1<? super Integer, Unit> selectTab;
    private final HashMap<String, String> status;
    private TextView unlockBtn;

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: GxVehicle.kt */
    static final class Feature {
        private final String desc;
        private final String key;
        private final String name;
        private final int tab;

        public Feature(String key, String name, String desc, int tab) {
            Intrinsics.checkNotNullParameter(key, "key");
            Intrinsics.checkNotNullParameter(name, "name");
            Intrinsics.checkNotNullParameter(desc, "desc");
            this.key = key;
            this.name = name;
            this.desc = desc;
            this.tab = tab;
        }

        public final String getDesc() {
            return this.desc;
        }

        public final String getKey() {
            return this.key;
        }

        public final String getName() {
            return this.name;
        }

        public final int getTab() {
            return this.tab;
        }
    }

    /* VF: from Vineflower (jadx: ) */
    public GxVehiclePage(Activity var1, GxHost var2) {
        super(var1, var2);
        Intrinsics.checkNotNullParameter(var1, "act");
        Intrinsics.checkNotNullParameter(var2, "gx");
        GxVehiclePage.Feature var4 = new GxVehiclePage.Feature("doors", "Lock/Unlock Doors", "Send lock or unlock commands remotely to your vehicle.", 1);
        GxVehiclePage.Feature var3 = new GxVehiclePage.Feature("tsk", "Toyota Security Keys", "Manage and apply security keys for secOC protected devices.", 2);
        this.features = CollectionsKt.listOf(new GxVehiclePage.Feature[]{var4, var3});
        this.status = new HashMap<>();
        this.checking = "";
        this.featureValues = new HashMap<>();
        this.gates = new HashMap<>();
        this.selectTab = new GxVehiclePage$$ExternalSyntheticLambda9();
        this.doorBusy = "";
        this.keys = CollectionsKt.emptyList();
    }

    /* compiled from: GxVehicle.kt */
    private static final class Gate {
        private final View card;
        private final LinearLayout content;
        private final TextView text;

        public Gate(View card, TextView text, LinearLayout content) {
            Intrinsics.checkNotNullParameter(card, "card");
            Intrinsics.checkNotNullParameter(text, "text");
            Intrinsics.checkNotNullParameter(content, "content");
            this.card = card;
            this.text = text;
            this.content = content;
        }

        public final View getCard() {
            return this.card;
        }

        public final LinearLayout getContent() {
            return this.content;
        }

        public final TextView getText() {
            return this.text;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit selectTab$lambda$0(int it) {
        return Unit.INSTANCE;
    }

    @Override // com.starpilot.starview.GxPage
    public void build() {
        this.selectTab = GxPage.tabs$default(this, CollectionsKt.listOf(new String[]{"Vehicle Features", "Lock/Unlock Doors", "Toyota Security Keys"}), 0, new Function1() { // from class: com.starpilot.starview.GxVehiclePage$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit build$lambda$1;
                build$lambda$1 = GxVehiclePage.build$lambda$1(GxVehiclePage.this, ((Number) obj).intValue());
                return build$lambda$1;
            }
        }, new Function2() { // from class: com.starpilot.starview.GxVehiclePage$$ExternalSyntheticLambda7
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit build$lambda$2;
                build$lambda$2 = GxVehiclePage.build$lambda$2(GxVehiclePage.this, ((Number) obj).intValue(), (LinearLayout) obj2);
                return build$lambda$2;
            }
        }, 2, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit build$lambda$1(GxVehiclePage this$0, int i) {
        this$0.curTab = i;
        if (i > 0) {
            this$0.gateFor(this$0.features.get(i - 1));
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit build$lambda$2(GxVehiclePage this$0, int i, LinearLayout box) {
        Intrinsics.checkNotNullParameter(box, "box");
        switch (i) {
            case 0:
                this$0.buildFeatures(box);
                break;
            case 1:
                this$0.buildDoors(box);
                break;
            default:
                this$0.buildTsk(box);
                break;
        }
        return Unit.INSTANCE;
    }

    @Override // com.starpilot.starview.GxPage
    public void onShow() {
        this.status.clear();
        this.checking = "";
        paintFeatures();
        if (this.curTab > 0) {
            gateFor(this.features.get(this.curTab - 1));
        }
        if (this.keyBox != null) {
            loadKeys();
        }
    }

    private final void buildFeatures(LinearLayout box) {
        header("Vehicle Features", box);
        for (final Feature f : this.features) {
            GxPage.Row r = GxPage.row$default(this, f.getName(), f.getDesc(), box, 0, 8, null);
            this.featureValues.put(f.getKey(), r.value(""));
            GxPage.Row.button$default(r, "Open", false, new Function0() { // from class: com.starpilot.starview.GxVehiclePage$$ExternalSyntheticLambda11
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit buildFeatures$lambda$3;
                    buildFeatures$lambda$3 = GxVehiclePage.buildFeatures$lambda$3(GxVehiclePage.this, f);
                    return buildFeatures$lambda$3;
                }
            }, 2, null);
        }
        note("These features verify vehicle compatibility when launched.", box);
        paintFeatures();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildFeatures$lambda$3(GxVehiclePage this$0, Feature $f) {
        this$0.openFeature($f);
        return Unit.INSTANCE;
    }

    private final void paintFeatures() {
        int i;
        for (Feature f : this.features) {
            TextView v = this.featureValues.get(f.getKey());
            if (v != null) {
                if (Intrinsics.areEqual(this.checking, f.getKey())) {
                    v.setText("Checking...");
                    v.setTextColor(CarUi.Companion.getDIM());
                } else if (Intrinsics.areEqual(this.status.get(f.getKey()), "denied")) {
                    v.setText("Not supported");
                    i = GxVehicleKt.GX_VEHICLE_ERR;
                    v.setTextColor(i);
                } else if (Intrinsics.areEqual(this.status.get(f.getKey()), "allowed")) {
                    v.setText("Supported");
                    v.setTextColor(CarUi.Companion.getACCENT());
                } else {
                    v.setText("");
                }
            }
        }
    }

    private final String notSupported(Feature f) {
        return f.getName() + " is not supported for your current vehicle.";
    }

    private final void check(final Feature f, final Function1<? super Boolean, Unit> then) {
        if (this.checking.length() > 0) {
            return;
        }
        this.checking = f.getKey();
        paintFeatures();
        GxPage.call$default(this, "GET", "/api/car_features_check?tool=" + enc(f.getKey()), null, 0L, new Function3() { // from class: com.starpilot.starview.GxVehiclePage$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                Unit check$lambda$5;
                check$lambda$5 = GxVehiclePage.check$lambda$5(GxVehiclePage.this, f, then, ((Number) obj).intValue(), (String) obj2, (String) obj3);
                return check$lambda$5;
            }
        }, 12, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit check$lambda$5(GxVehiclePage this$0, Feature $f, Function1 $then, int code, String text, String str) {
        Object m1191constructorimpl;
        Intrinsics.checkNotNullParameter(text, "text");
        this$0.checking = "";
        boolean ok = false;
        boolean netFail = code < 0;
        if (200 <= code && code < 300) {
            try {
                Result.Companion companion = Result.Companion;
                m1191constructorimpl = KtCompat.resultOf(Boolean.valueOf(new JSONObject(text).optBoolean("result", false)));
            } catch (Throwable th) {
                Result.Companion companion2 = Result.Companion;
                m1191constructorimpl = KtCompat.resultOf(ResultKt.createFailure(th));
            }
            if (KtCompat.isFailure(m1191constructorimpl)) {
                m1191constructorimpl = false;
            }
            if (((Boolean) m1191constructorimpl).booleanValue()) {
                ok = true;
            }
        }
        this$0.status.put($f.getKey(), ok ? "allowed" : "denied");
        this$0.paintFeatures();
        $then.invoke(Boolean.valueOf(netFail));
        if (this$0.curTab > 0 && this$0.features.get(this$0.curTab - 1) != $f) {
            this$0.gateFor(this$0.features.get(this$0.curTab - 1));
        }
        return Unit.INSTANCE;
    }

    /* VF: from Vineflower (jadx: ) */
    private final void openFeature(GxVehiclePage.Feature var1) {
        boolean var2;
        if (this.checking.length() > 0) {
            var2 = true;
        } else {
            var2 = false;
        }

        if (!var2) {
            String var3 = this.status.get(var1.getKey());
            if (var3 != null) {
                switch (var3.hashCode()) {
                    case -1335395429:
                        if (var3.equals("denied")) {
                            this.toast(this.notSupported(var1));
                            return;
                        }
                        break;
                    case -911343192:
                        if (var3.equals("allowed")) {
                            this.selectTab.invoke(var1.getTab());
                            return;
                        }
                }
            }

            this.check(var1, new GxVehiclePage$$ExternalSyntheticLambda8(this, var1));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit openFeature$lambda$6(GxVehiclePage this$0, Feature $f, boolean netFail) {
        if (Intrinsics.areEqual(this$0.status.get($f.getKey()), "allowed")) {
            this$0.selectTab.invoke(Integer.valueOf($f.getTab()));
        } else if (netFail) {
            this$0.toast("Could not check vehicle compatibility.");
        } else {
            this$0.toast(this$0.notSupported($f));
        }
        return Unit.INSTANCE;
    }

    /* VF: from Vineflower (jadx: ) */
    private final void gateFor(GxVehiclePage.Feature var1) {
        GxVehiclePage.Gate var4 = this.gates.get(var1.getKey());
        if (var4 != null) {
            String var3 = this.status.get(var1.getKey());
            boolean var2 = false;
            if (var3 != null) {
                switch (var3.hashCode()) {
                    case -1335395429:
                        if (var3.equals("denied")) {
                            var4.getText().setText(this.notSupported(var1));
                            var4.getCard().setVisibility(0);
                            var4.getContent().setVisibility(8);
                            return;
                        }
                        break;
                    case -911343192:
                        if (var3.equals("allowed")) {
                            var4.getCard().setVisibility(8);
                            var4.getContent().setVisibility(0);
                            return;
                        }
                }
            }

            var4.getText().setText("Checking vehicle compatibility...");
            var4.getCard().setVisibility(0);
            var4.getContent().setVisibility(8);
            if (this.checking.length() == 0) {
                var2 = true;
            }

            if (var2) {
                this.check(var1, new GxVehiclePage$$ExternalSyntheticLambda19(this, var1));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit gateFor$lambda$7(GxVehiclePage this$0, Feature $f, boolean it) {
        this$0.gateFor($f);
        return Unit.INSTANCE;
    }

    private final LinearLayout makeGate(String key, LinearLayout box) {
        LinearLayout c = card(box);
        TextView t = GxPage.text$default(this, "", c, 18.0f, CarUi.Companion.getDIM(), false, 16, null);
        LinearLayout content = box(box);
        this.gates.put(key, new Gate(c, t, content));
        return content;
    }

    private final void buildDoors(LinearLayout box) {
        List bs;
        LinearLayout content = makeGate("doors", box);
        header("Lock/Unlock Doors", content);
        LinearLayout c = card(content);
        GxPage.text$default(this, "Remotely lock or unlock your car doors using the buttons below.", c, 18.0f, 0, false, 24, null);
        bs = GxVehicleKt.gxVehicleButtons(this, c, (List) CollectionsKt.listOf(new Triple[]{new Triple("Lock Doors", true, new Function0() { // from class: com.starpilot.starview.GxVehiclePage$$ExternalSyntheticLambda17
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildDoors$lambda$8;
                buildDoors$lambda$8 = GxVehiclePage.buildDoors$lambda$8(GxVehiclePage.this);
                return buildDoors$lambda$8;
            }
        }), new Triple("Unlock Doors", true, new Function0() { // from class: com.starpilot.starview.GxVehiclePage$$ExternalSyntheticLambda18
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildDoors$lambda$9;
                buildDoors$lambda$9 = GxVehiclePage.buildDoors$lambda$9(GxVehiclePage.this);
                return buildDoors$lambda$9;
            }
        })}), true);
        this.lockBtn = (TextView) bs.get(0);
        this.unlockBtn = (TextView) bs.get(1);
        gateFor(this.features.get(0));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildDoors$lambda$8(GxVehiclePage this$0) {
        this$0.doorAct("lock");
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildDoors$lambda$9(GxVehiclePage this$0) {
        this$0.doorAct("unlock");
        return Unit.INSTANCE;
    }

    private final void paintDoors() {
        TextView it = this.lockBtn;
        if (it != null) {
            it.setText(Intrinsics.areEqual(this.doorBusy, "lock") ? "Locking..." : "Lock Doors");
            GxVehicleKt.gxVehicleEnable(it, this.doorBusy.length() == 0);
        }
        TextView it2 = this.unlockBtn;
        if (it2 != null) {
            it2.setText(Intrinsics.areEqual(this.doorBusy, "unlock") ? "Unlocking..." : "Unlock Doors");
            GxVehicleKt.gxVehicleEnable(it2, this.doorBusy.length() == 0);
        }
    }

    private final void doorAct(final String action) {
        if (this.doorBusy.length() > 0) {
            return;
        }
        this.doorBusy = action;
        paintDoors();
        send("POST", "/api/doors/" + action, null, 60L, new Function2() { // from class: com.starpilot.starview.GxVehiclePage$$ExternalSyntheticLambda16
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit doorAct$lambda$14;
                doorAct$lambda$14 = GxVehiclePage.doorAct$lambda$14(GxVehiclePage.this, action, (String) obj, (JSONObject) obj2);
                return doorAct$lambda$14;
            }
        });
    }

    /* VF: from Vineflower (jadx: Code restructure failed;incorrect) */
    public static final Unit doorAct$lambda$14(GxVehiclePage var0, String var1, String var2, JSONObject var3) {
        var0.doorBusy = "";
        var0.paintDoors();
        if (var2 != null) {
            CharSequence var10 = var2;
            Object var6 = var10;
            if (StringsKt.isBlank(var10)) {
                if (Intrinsics.areEqual(var1, "lock")) {
                    var1 = "Failed to lock doors.";
                } else {
                    var1 = "Failed to unlock doors.";
                }

                var6 = var1;
            }

            var0.toast((String)var6);
        } else {
            label33: {
                if (var3 != null) {
                    var2 = GxPage.str$default(var0, var3, "message", null, 2, null);
                    if (var2 != null) {
                        CharSequence var11 = var2;
                        CharSequence var8 = var11;
                        if (StringsKt.isBlank(var11)) {
                            var8 = null;
                        }

                        var2 = (String)var8;
                        if (var2 != null) {
                            var1 = var2;
                            break label33;
                        }
                    }
                }

                if (Intrinsics.areEqual(var1, "lock")) {
                    var1 = "Doors locked!";
                } else {
                    var1 = "Doors unlocked!";
                }
            }

            var0.toast(var1);
        }

        return Unit.INSTANCE;
    }

    private final void buildTsk(LinearLayout box) {
        LinearLayout content = makeGate("tsk", box);
        header("Toyota Security Keys", content);
        this.keyBox = box(content);
        buttons(new Pair[]{TuplesKt.to("Add Key", new Function0() { // from class: com.starpilot.starview.GxVehiclePage$$ExternalSyntheticLambda12
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildTsk$lambda$15;
                buildTsk$lambda$15 = GxVehiclePage.buildTsk$lambda$15(GxVehiclePage.this);
                return buildTsk$lambda$15;
            }
        })}, content, true);
        note("Apply Key sets the selected key as the active security key.", content);
        paintKeys();
        loadKeys();
        gateFor(this.features.get(1));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildTsk$lambda$15(GxVehiclePage this$0) {
        this$0.editKey(null, "", "");
        return Unit.INSTANCE;
    }

    private final void loadKeys() {
        getArray("/api/tsk_keys", new Function2() { // from class: com.starpilot.starview.GxVehiclePage$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit loadKeys$lambda$16;
                loadKeys$lambda$16 = GxVehiclePage.loadKeys$lambda$16(GxVehiclePage.this, (String) obj, (JSONArray) obj2);
                return loadKeys$lambda$16;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit loadKeys$lambda$16(GxVehiclePage this$0, String e, JSONArray arr) {
        if (e != null || arr == null) {
            this$0.toast("Failed to load keys...");
        } else {
            this$0.keys = this$0.objects(arr);
        }
        this$0.keysLoaded = true;
        this$0.paintKeys();
        return Unit.INSTANCE;
    }

    private final void paintKeys() {
        LinearLayout b = this.keyBox;
        if (b == null) {
            return;
        }
        b.removeAllViews();
        if (!this.keysLoaded) {
            b.addView(empty("Loading keys..."));
            return;
        }
        if (this.keys.isEmpty()) {
            note("No saved keys yet.", b);
            return;
        }
        for (final JSONObject k : this.keys) {
            final String name = GxPage.str$default(this, k, "name", null, 2, null);
            final String value = GxPage.str$default(this, k, "value", null, 2, null);
            GxVehiclePage gxVehiclePage = this;
            String str = name;
            if (StringsKt.isBlank(str)) {
                str = "(no name)";
            }
            GxPage.Row r = GxPage.row$default(gxVehiclePage, str, value, b, 0, 8, null);
            r.getDesc().setTypeface(Typeface.MONOSPACE);
            r.button("Apply Key", true, new Function0() { // from class: com.starpilot.starview.GxVehiclePage$$ExternalSyntheticLambda13
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit paintKeys$lambda$18;
                    paintKeys$lambda$18 = GxVehiclePage.paintKeys$lambda$18(GxVehiclePage.this, k);
                    return paintKeys$lambda$18;
                }
            });
            GxPage.Row.button$default(r, "Edit", false, new Function0() { // from class: com.starpilot.starview.GxVehiclePage$$ExternalSyntheticLambda14
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit paintKeys$lambda$19;
                    paintKeys$lambda$19 = GxVehiclePage.paintKeys$lambda$19(GxVehiclePage.this, k, name, value);
                    return paintKeys$lambda$19;
                }
            }, 2, null);
            GxPage.Row.button$default(r, "Delete", false, new Function0() { // from class: com.starpilot.starview.GxVehiclePage$$ExternalSyntheticLambda15
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit paintKeys$lambda$20;
                    paintKeys$lambda$20 = GxVehiclePage.paintKeys$lambda$20(GxVehiclePage.this, name);
                    return paintKeys$lambda$20;
                }
            }, 2, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paintKeys$lambda$18(GxVehiclePage this$0, JSONObject $k) {
        this$0.applyKey($k);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paintKeys$lambda$19(GxVehiclePage this$0, JSONObject $k, String $name, String $value) {
        this$0.editKey($k, $name, $value);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paintKeys$lambda$20(GxVehiclePage this$0, String $name) {
        this$0.removeKey($name);
        return Unit.INSTANCE;
    }

    private final void editKey(final JSONObject orig, String name0, String value0) {
        int i;
        final EditText nameE = GxVehicleKt.gxVehicleEdit$default(this, "Enter key name...", name0, 0, 8, null);
        final EditText valueE = GxVehicleKt.gxVehicleEdit$default(this, "Enter key value...", value0, 0, 8, null);
        final TextView $this$editKey_u24lambda_u2421 = new TextView(getAct());
        $this$editKey_u24lambda_u2421.setText("A key with this name already exists.");
        i = GxVehicleKt.GX_VEHICLE_ERR;
        $this$editKey_u24lambda_u2421.setTextColor(i);
        $this$editKey_u24lambda_u2421.setTextSize(15.0f);
        $this$editKey_u24lambda_u2421.setVisibility(8);
        final String origName = orig != null ? GxPage.str$default(this, orig, "name", null, 2, null) : null;
        nameE.addTextChangedListener(new TextWatcher() { // from class: com.starpilot.starview.GxVehiclePage$editKey$1
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int a, int b, int c) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int a, int b, int c) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s) {
                String str;
                boolean editKey$isDup;
                String obj;
                TextView textView = $this$editKey_u24lambda_u2421;
                GxVehiclePage gxVehiclePage = GxVehiclePage.this;
                String str2 = origName;
                if (s == null || (obj = s.toString()) == null || (str = StringsKt.trim((CharSequence) obj).toString()) == null) {
                    str = "";
                }
                editKey$isDup = GxVehiclePage.editKey$isDup(gxVehiclePage, str2, str);
                textView.setVisibility(editKey$isDup ? 0 : 8);
            }
        });
        LinearLayout $this$editKey_u24lambda_u2423 = new LinearLayout(getAct());
        $this$editKey_u24lambda_u2423.setOrientation(1);
        $this$editKey_u24lambda_u2423.setPadding(getUi().dp(22), getUi().dp(8), getUi().dp(22), 0);
        TextView $this$editKey_u24lambda_u2424 = new TextView(getAct());
        $this$editKey_u24lambda_u2424.setText("Key Name");
        $this$editKey_u24lambda_u2424.setTextColor(CarUi.Companion.getDIM());
        $this$editKey_u24lambda_u2424.setTextSize(14.0f);
        $this$editKey_u24lambda_u2423.addView($this$editKey_u24lambda_u2424);
        $this$editKey_u24lambda_u2423.addView(nameE);
        $this$editKey_u24lambda_u2423.addView($this$editKey_u24lambda_u2421);
        TextView $this$editKey_u24lambda_u2425 = new TextView(getAct());
        $this$editKey_u24lambda_u2425.setText("Key Value");
        $this$editKey_u24lambda_u2425.setTextColor(CarUi.Companion.getDIM());
        $this$editKey_u24lambda_u2425.setTextSize(14.0f);
        $this$editKey_u24lambda_u2425.setPadding(0, getUi().dp(12), 0, 0);
        $this$editKey_u24lambda_u2423.addView($this$editKey_u24lambda_u2425);
        $this$editKey_u24lambda_u2423.addView(valueE);
        new AlertDialog.Builder(getAct()).setTitle(orig == null ? "Add Key" : "Edit Key").setView($this$editKey_u24lambda_u2423).setPositiveButton("Save", new DialogInterface.OnClickListener() { // from class: com.starpilot.starview.GxVehiclePage$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                GxVehiclePage.editKey$lambda$28(nameE, valueE, orig, GxVehiclePage.this, origName, dialogInterface, i2);
            }
        }).setNegativeButton("Cancel", (DialogInterface.OnClickListener) null).show();
    }

    /* VF: from Vineflower (jadx: Removed duplicated region;incorrect) */
    public static final boolean editKey$isDup(GxVehiclePage var0, String var1, String var2) {
        var var6 = var0.keys;
        boolean var5 = var6 instanceof Collection;
        boolean var4 = false;
        if (!var5 || !((Collection)var6).isEmpty()) {
            for (JSONObject var7 : var6) {
                boolean var3;
                if (Intrinsics.areEqual(GxPage.str$default(var0, var7, "name", null, 2, null), var2)
                    && !Intrinsics.areEqual(GxPage.str$default(var0, var7, "name", null, 2, null), var1)) {
                    var3 = true;
                } else {
                    var3 = false;
                }

                if (var3) {
                    var4 = true;
                    break;
                }
            }
        }

        return var4;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void editKey$lambda$28(EditText $nameE, EditText $valueE, JSONObject $orig, final GxVehiclePage this$0, String $origName, DialogInterface dialogInterface, int i) {
        String problem;
        String name = StringsKt.trim((CharSequence) $nameE.getText().toString()).toString();
        String value = StringsKt.trim((CharSequence) $valueE.getText().toString()).toString();
        Regex ws = new Regex("\\s");
        boolean unchanged = $orig != null && Intrinsics.areEqual(name, GxPage.str$default(this$0, $orig, "name", null, 2, null)) && Intrinsics.areEqual(value, GxPage.str$default(this$0, $orig, "value", null, 2, null));
        if (editKey$isDup(this$0, $origName, name)) {
            problem = "A key with this name already exists.";
        } else if ((name.length() == 0) || value.length() < 10 || ws.containsMatchIn(name) || ws.containsMatchIn(value)) {
            problem = "Invalid input or duplicate name. The key needs a name and a value of at least 10 characters, without spaces.";
        } else {
            problem = unchanged ? "Nothing changed." : null;
        }
        if (problem != null) {
            this$0.toast(problem);
            if (unchanged) {
                return;
            }
            this$0.editKey($orig, name, value);
            return;
        }
        JSONArray updated = new JSONArray();
        for (JSONObject k : this$0.keys) {
            if ($orig == null || !Intrinsics.areEqual(GxPage.str$default(this$0, k, "name", null, 2, null), $origName)) {
                updated.put(k);
            }
        }
        updated.put(new JSONObject().put("name", name).put("value", value));
        GxPage.json$default(this$0, "POST", "/api/tsk_keys", updated, 0L, new Function2() { // from class: com.starpilot.starview.GxVehiclePage$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit editKey$lambda$28$lambda$27;
                editKey$lambda$28$lambda$27 = GxVehiclePage.editKey$lambda$28$lambda$27(this$0, (String) obj, obj2);
                return editKey$lambda$28$lambda$27;
            }
        }, 8, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit editKey$lambda$28$lambda$27(GxVehiclePage this$0, String e, Object v) {
        if (e != null) {
            this$0.toast("Save failed... (" + e + ")");
            return Unit.INSTANCE;
        }
        JSONArray it = v instanceof JSONArray ? (JSONArray) v : null;
        if (it != null) {
            this$0.keys = this$0.objects(it);
        }
        this$0.paintKeys();
        this$0.toast("Saved key!");
        return Unit.INSTANCE;
    }

    private final void removeKey(final String name) {
        confirm("Confirm Delete", "Are you sure you want to delete the key \"" + name + "\"?", "Yes, Delete", new Function0() { // from class: com.starpilot.starview.GxVehiclePage$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit removeKey$lambda$31;
                removeKey$lambda$31 = GxVehiclePage.removeKey$lambda$31(GxVehiclePage.this, name);
                return removeKey$lambda$31;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit removeKey$lambda$31(final GxVehiclePage this$0, String $name) {
        GxPage.json$default(this$0, "DELETE", "/api/tsk_keys?name=" + this$0.enc($name), null, 0L, new Function2() { // from class: com.starpilot.starview.GxVehiclePage$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit removeKey$lambda$31$lambda$30;
                removeKey$lambda$31$lambda$30 = GxVehiclePage.removeKey$lambda$31$lambda$30(this$0, (String) obj, obj2);
                return removeKey$lambda$31$lambda$30;
            }
        }, 12, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit removeKey$lambda$31$lambda$30(GxVehiclePage this$0, String e, Object v) {
        if (e != null) {
            this$0.toast("Delete failed...");
            return Unit.INSTANCE;
        }
        JSONArray it = v instanceof JSONArray ? (JSONArray) v : null;
        if (it != null) {
            this$0.keys = this$0.objects(it);
        } else {
            this$0.loadKeys();
        }
        this$0.paintKeys();
        this$0.toast("Deleted key!");
        return Unit.INSTANCE;
    }

    private final void applyKey(JSONObject k) {
        GxPage.send$default(this, "POST", "/api/tsk_key_set", new JSONObject().put("name", GxPage.str$default(this, k, "name", null, 2, null)).put("value", GxPage.str$default(this, k, "value", null, 2, null)), 0L, new Function2() { // from class: com.starpilot.starview.GxVehiclePage$$ExternalSyntheticLambda10
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit applyKey$lambda$32;
                applyKey$lambda$32 = GxVehiclePage.applyKey$lambda$32(GxVehiclePage.this, (String) obj, (JSONObject) obj2);
                return applyKey$lambda$32;
            }
        }, 8, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit applyKey$lambda$32(GxVehiclePage this$0, String e, JSONObject jSONObject) {
        this$0.toast(e == null ? "Key applied!" : "Apply failed...");
        return Unit.INSTANCE;
    }
}
