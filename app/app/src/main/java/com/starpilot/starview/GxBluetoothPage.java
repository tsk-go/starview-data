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
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import com.starpilot.starview.CarUi;
import com.starpilot.starview.GxPage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import kotlin.Pair;
import kotlin.Triple;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import org.json.JSONArray;
import org.json.JSONObject;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import kotlin.jvm.internal.Ref.ObjectRef;

/* compiled from: GxVehicle.kt */
public final class GxBluetoothPage extends GxPage {
    private LinearLayout btAvailBox;
    private boolean btAvailable;
    private boolean btBuilt;
    private String btBusy;
    private List<? extends JSONObject> btDevices;
    private boolean btDiscovering;
    private boolean btEnabled;
    private TextView btErrNote;
    private String btError;
    private boolean btInflight;
    private LinearLayout btKnownBox;
    private String btListSig;
    private boolean btLoaded;
    private boolean btOffroad;
    private TextView btOffroadNote;
    private EditText btPairEdit;
    private String btPairingAddress;
    private TextView btPowerBtn;
    private GxPage.Row btPowerRow;
    private JSONObject btPrompt;
    private LinearLayout btPromptBox;
    private String btPromptSig;
    private TextView btRefreshBtn;
    private TextView btScanBtn;
    private String btSelectedAudio;
    private final boolean needsOffroad;
    private int tab;
    private boolean wcAvailable;
    private LinearLayout wcBox;
    private String wcBusy;
    private List<? extends JSONObject> wcControllerSlots;
    private List<? extends JSONObject> wcDevices;
    private boolean wcDisconnectOffroad;
    private String wcError;
    private boolean wcInflight;
    private String wcJoystick;
    private Map<String, String> wcKeySection;
    private JSONObject wcLastTested;
    private boolean wcLearning;
    private Integer wcLearningSlot;
    private boolean wcLoaded;
    private List<? extends JSONObject> wcMappings;
    private boolean wcOffroad;
    private List<? extends JSONObject> wcOptions;
    private String wcRaw;
    private double wcRemaining;
    private List<String> wcSectionOrder;
    private String wcSig;
    private List<? extends JSONObject> wcSlots;
    private double wcSpeedMax;
    private double wcSpeedMin;
    private String wcSpeedUnit;
    private boolean wcTesting;

    /* VF: from Vineflower (jadx: ) */
    public GxBluetoothPage(Activity var1, GxHost var2) {
        super(var1, var2);
        Intrinsics.checkNotNullParameter(var1, "act");
        Intrinsics.checkNotNullParameter(var2, "gx");
        this.needsOffroad = true;
        this.btBusy = "";
        this.btSelectedAudio = "";
        this.btPairingAddress = "";
        this.btDevices = CollectionsKt.emptyList();
        this.btError = "";
        this.btPromptSig = "";
        this.btListSig = "";
        this.wcBusy = "";
        this.wcDevices = CollectionsKt.emptyList();
        this.wcMappings = CollectionsKt.emptyList();
        this.wcSlots = CollectionsKt.emptyList();
        this.wcControllerSlots = CollectionsKt.emptyList();
        this.wcOptions = CollectionsKt.emptyList();
        this.wcJoystick = "";
        this.wcSpeedUnit = "mph";
        this.wcSpeedMin = 5.0;
        this.wcSpeedMax = 90.0;
        this.wcError = "";
        this.wcRaw = "";
        this.wcSig = "";
        this.wcKeySection = MapsKt.emptyMap();
        this.wcSectionOrder = CollectionsKt.emptyList();
    }

    @Override // com.starpilot.starview.GxPage
    public boolean getNeedsOffroad() {
        return this.needsOffroad;
    }

    @Override // com.starpilot.starview.GxPage
    public void build() {
        GxPage.tabs$default(this, CollectionsKt.listOf(new String[]{"Bluetooth", "Controllers"}), 0, new Function1() { // from class: com.starpilot.starview.GxBluetoothPage$$ExternalSyntheticLambda16
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit build$lambda$0;
                build$lambda$0 = GxBluetoothPage.build$lambda$0(GxBluetoothPage.this, ((Integer) obj).intValue());
                return build$lambda$0;
            }
        }, new Function2() { // from class: com.starpilot.starview.GxBluetoothPage$$ExternalSyntheticLambda17
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit build$lambda$1;
                build$lambda$1 = GxBluetoothPage.build$lambda$1(GxBluetoothPage.this, ((Integer) obj).intValue(), (LinearLayout) obj2);
                return build$lambda$1;
            }
        }, 2, null);
        every(2000L, new Function0() { // from class: com.starpilot.starview.GxBluetoothPage$$ExternalSyntheticLambda18
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit build$lambda$2;
                build$lambda$2 = GxBluetoothPage.build$lambda$2(GxBluetoothPage.this);
                return build$lambda$2;
            }
        });
        every(1000L, new Function0() { // from class: com.starpilot.starview.GxBluetoothPage$$ExternalSyntheticLambda19
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit build$lambda$3;
                build$lambda$3 = GxBluetoothPage.build$lambda$3(GxBluetoothPage.this);
                return build$lambda$3;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit build$lambda$0(GxBluetoothPage this$0, int i) {
        this$0.tab = i;
        if (this$0.getShown()) {
            if (i == 0) {
                this$0.btRefresh();
            } else {
                this$0.wcRefresh();
            }
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit build$lambda$1(GxBluetoothPage this$0, int i, LinearLayout box) {
        Intrinsics.checkNotNullParameter(box, "box");
        if (i == 0) {
            this$0.buildBt(box);
        } else {
            this$0.buildWc(box);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit build$lambda$2(GxBluetoothPage this$0) {
        if (this$0.tab == 0) {
            this$0.btRefresh();
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit build$lambda$3(GxBluetoothPage this$0) {
        if (this$0.tab == 1) {
            this$0.wcRefresh();
        }
        return Unit.INSTANCE;
    }

    private final void buildBt(LinearLayout box) {
        TextView gxVehicleNotice;
        int i;
        TextView gxVehicleNotice2;
        List bs;
        header("Bluetooth Devices", box);
        this.btPowerRow = GxPage.row$default(this, "Bluetooth", null, box, 0, 8, null);
        GxPage.Row row = this.btPowerRow;
        if (row == null) {
            Intrinsics.throwUninitializedPropertyAccessException("btPowerRow");
            row = null;
        }
        this.btPowerBtn = GxPage.Row.button$default(row, "Turn On", false, new Function0() { // from class: com.starpilot.starview.GxBluetoothPage$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildBt$lambda$4;
                buildBt$lambda$4 = GxBluetoothPage.buildBt$lambda$4(GxBluetoothPage.this);
                return buildBt$lambda$4;
            }
        }, 2, null);
        gxVehicleNotice = GxVehicleKt.gxVehicleNotice(this, "Scanning, pairing, and forgetting devices are available offroad only.", CarUi.Companion.getWARN(), box);
        this.btOffroadNote = gxVehicleNotice;
        i = GxVehicleKt.GX_VEHICLE_ERR;
        gxVehicleNotice2 = GxVehicleKt.gxVehicleNotice(this, "", i, box);
        this.btErrNote = gxVehicleNotice2;
        this.btPromptBox = box(box);
        bs = GxVehicleKt.gxVehicleButtons(this, box, CollectionsKt.listOf(new Triple[]{new Triple("Search for Devices", true, new Function0() { // from class: com.starpilot.starview.GxBluetoothPage$$ExternalSyntheticLambda7
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildBt$lambda$5;
                buildBt$lambda$5 = GxBluetoothPage.buildBt$lambda$5(GxBluetoothPage.this);
                return buildBt$lambda$5;
            }
        }), new Triple("Refresh", true, new Function0() { // from class: com.starpilot.starview.GxBluetoothPage$$ExternalSyntheticLambda8
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildBt$lambda$6;
                buildBt$lambda$6 = GxBluetoothPage.buildBt$lambda$6(GxBluetoothPage.this);
                return buildBt$lambda$6;
            }
        })}), true);
        this.btScanBtn = (TextView) bs.get(0);
        this.btRefreshBtn = (TextView) bs.get(1);
        header("My Devices", box);
        this.btKnownBox = box(box);
        header("Available Devices", box);
        this.btAvailBox = box(box);
        this.btBuilt = true;
        btPaint();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildBt$lambda$4(GxBluetoothPage this$0) {
        JSONObject put = new JSONObject().put("enabled", !this$0.btEnabled);
        Intrinsics.checkNotNullExpressionValue(put, "put(...)");
        this$0.btRequest("power", put);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildBt$lambda$5(GxBluetoothPage this$0) {
        btRequest$default(this$0, this$0.btDiscovering ? "stop_scan" : "scan", null, 2, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildBt$lambda$6(GxBluetoothPage this$0) {
        this$0.btRefresh();
        return Unit.INSTANCE;
    }

    private final void btRefresh() {
        if (this.btInflight) {
            return;
        }
        this.btInflight = true;
        getJson("/api/bluetooth/status", new Function2() { // from class: com.starpilot.starview.GxBluetoothPage$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit btRefresh$lambda$8;
                btRefresh$lambda$8 = GxBluetoothPage.btRefresh$lambda$8(GxBluetoothPage.this, (String) obj, (JSONObject) obj2);
                return btRefresh$lambda$8;
            }
        });
    }

    /* VF: from Vineflower (jadx: Code restructure failed;incorrect) */
    public static final Unit btRefresh$lambda$8(GxBluetoothPage var0, String var1, JSONObject var2) {
        List var5 = null; // declarations Vineflower left out
        var0.btInflight = false;
        if (var1 == null && var2 != null) {
            label33: {
                var0.btAvailable = var2.optBoolean("available");
                var0.btEnabled = var2.optBoolean("enabled");
                var0.btDiscovering = var2.optBoolean("discovering");
                var0.btOffroad = var2.optBoolean("offroad");
                var0.btSelectedAudio = GxPage.str$default(var0, var2, "selected_audio", null, 2, null);
                var0.btPairingAddress = GxPage.str$default(var0, var2, "pairing_address", null, 2, null);
                JSONArray var4 = var2.optJSONArray("devices");
                if (var4 != null) {
                    List var9 = var0.objects(var4);
                    var5 = var9;
                    if (var9 != null) {
                        break label33;
                    }
                }

                var5 = CollectionsKt.emptyList();
            }

            var0.btDevices = var5;
            var0.btPrompt = var2.optJSONObject("prompt");
            var0.btError = GxPage.str$default(var0, var2, "error", null, 2, null);
        } else {
            label28: {
                var0.btAvailable = false;
                if (var2 != null) {
                    String var6 = GxPage.str$default(var0, var2, "error", null, 2, null);
                    if (var6 != null) {
                        CharSequence var3 = var6;
                        CharSequence var7 = var3;
                        if (StringsKt.isBlank(var3)) {
                            var7 = null;
                        }

                        String var8 = (String)var7;
                        if (var8 != null) {
                            var1 = var8;
                            break label28;
                        }
                    }
                }

                if (var1 == null) {
                    var1 = "Bluetooth service unavailable";
                }
            }

            var0.btError = var1;
        }

        var0.btLoaded = true;
        var0.btPaint();
        return Unit.INSTANCE;
    }

    static /* synthetic */ void btRequest$default(GxBluetoothPage gxBluetoothPage, String str, JSONObject jSONObject, int i, Object obj) {
        if ((i & 2) != 0) {
            jSONObject = new JSONObject();
        }
        gxBluetoothPage.btRequest(str, jSONObject);
    }

    private final void btRequest(final String op, JSONObject body) {
        if (this.btBusy.length() > 0) {
            return;
        }
        this.btBusy = op;
        btPaint();
        GxPage.send$default(this, "POST", "/api/bluetooth/" + op, body, 0L, new Function2() { // from class: com.starpilot.starview.GxBluetoothPage$$ExternalSyntheticLambda24
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit btRequest$lambda$9;
                btRequest$lambda$9 = GxBluetoothPage.btRequest$lambda$9(GxBluetoothPage.this, op, (String) obj, (JSONObject) obj2);
                return btRequest$lambda$9;
            }
        }, 8, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit btRequest$lambda$9(GxBluetoothPage this$0, String $op, String e, JSONObject o) {
        this$0.btBusy = "";
        if (e != null) {
            this$0.btError = e;
            this$0.toast(e);
        } else {
            this$0.btError = "";
            if (Intrinsics.areEqual($op, "test_audio")) {
                double ms = o != null ? o.optDouble("audio_test_delay_ms", 3000.0d) : 3000.0d;
                this$0.toast("Test sound plays in " + Math.round(ms / 1000.0d) + " s");
            }
        }
        this$0.btPaint();
        if (e == null) {
            this$0.btRefresh();
        }
        return Unit.INSTANCE;
    }

    private final String addr(JSONObject d) {
        String upperCase = GxPage.str$default(this, d, "address", null, 2, null).toUpperCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(upperCase, "toUpperCase(...)");
        return upperCase;
    }

    private final boolean isPairing(JSONObject d) {
        if (this.btPairingAddress.length() > 0) {
            String upperCase = this.btPairingAddress.toUpperCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(upperCase, "toUpperCase(...)");
            if (Intrinsics.areEqual(upperCase, addr(d))) {
                return true;
            }
        }
        return false;
    }

    private final boolean isAudioSel(JSONObject d) {
        String upperCase = this.btSelectedAudio.toUpperCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(upperCase, "toUpperCase(...)");
        return Intrinsics.areEqual(upperCase, addr(d));
    }

    private final String statusOf(JSONObject d) {
        return isPairing(d) ? "Pairing…" : d.optBoolean("connected") ? isAudioSel(d) ? "Connected · Audio output" : "Connected" : d.optBoolean("paired") ? "Saved" : "Ready to pair";
    }

    private final boolean offroadOk() {
        if (this.btOffroad) {
            return this.btBusy.length() == 0;
        }
        return false;
    }

    private final boolean needsPairValue(JSONObject p) {
        return p != null && (Intrinsics.areEqual(GxPage.str$default(this, p, "kind", null, 2, null), "pin") || Intrinsics.areEqual(GxPage.str$default(this, p, "kind", null, 2, null), "passkey"));
    }

    private final void btPaint() {
        String str;
        String str2;
        String str3;
        boolean z;
        String str4;
        Iterator it;
        LinearLayout linearLayout;
        if (this.btBuilt) {
            GxPage.Row row = this.btPowerRow;
            if (row == null) {
                Intrinsics.throwUninitializedPropertyAccessException("btPowerRow");
                row = null;
            }
            row.setTitle("Bluetooth " + (this.btEnabled ? "On" : "Off"));
            TextView textView = this.btPowerBtn;
            if (textView == null) {
                Intrinsics.throwUninitializedPropertyAccessException("btPowerBtn");
                textView = null;
            }
            textView.setText(this.btEnabled ? "Turn Off" : "Turn On");
            TextView textView2 = this.btPowerBtn;
            if (textView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("btPowerBtn");
                textView2 = null;
            }
            GxVehicleKt.gxVehicleEnable(textView2, this.btAvailable && offroadOk());
            TextView textView3 = this.btOffroadNote;
            if (textView3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("btOffroadNote");
                textView3 = null;
            }
            textView3.setVisibility((!this.btLoaded || this.btOffroad) ? 8 : 0);
            TextView textView4 = this.btErrNote;
            if (textView4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("btErrNote");
                textView4 = null;
            }
            textView4.setText(this.btError);
            TextView textView5 = this.btErrNote;
            if (textView5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("btErrNote");
                textView5 = null;
            }
            textView5.setVisibility(StringsKt.isBlank(this.btError) ? 8 : 0);
            TextView textView6 = this.btScanBtn;
            if (textView6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("btScanBtn");
                textView6 = null;
            }
            textView6.setText(this.btDiscovering ? "Searching…" : "Search for Devices");
            TextView textView7 = this.btScanBtn;
            if (textView7 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("btScanBtn");
                textView7 = null;
            }
            GxVehicleKt.gxVehicleEnable(textView7, this.btOffroad && this.btEnabled && offroadOk());
            TextView textView8 = this.btRefreshBtn;
            if (textView8 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("btRefreshBtn");
                textView8 = null;
            }
            GxVehicleKt.gxVehicleEnable(textView8, this.btBusy.length() == 0);
            JSONObject jSONObject = this.btPrompt;
            if (jSONObject == null || (str = jSONObject.toString()) == null) {
                str = "";
            }
            String psig = str + "|" + Intrinsics.areEqual(this.btBusy, "pairing_response");
            if (!Intrinsics.areEqual(psig, this.btPromptSig)) {
                this.btPromptSig = psig;
                paintPrompt();
            }
            String lsig = CollectionsKt.joinToString(this.btDevices, ", ", "", "", -1, "...", new Function1() { // from class: com.starpilot.starview.GxBluetoothPage$$ExternalSyntheticLambda9
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    CharSequence btPaint$lambda$10;
                    btPaint$lambda$10 = GxBluetoothPage.btPaint$lambda$10((JSONObject) obj);
                    return btPaint$lambda$10;
                }
            }) + "|" + this.btSelectedAudio + "|" + this.btPairingAddress + "|" + this.btBusy + "|" + this.btOffroad + "|" + this.btDiscovering + "|" + this.btLoaded;
            if (Intrinsics.areEqual(lsig, this.btListSig)) {
                return;
            }
            this.btListSig = lsig;
            LinearLayout linearLayout2 = this.btKnownBox;
            if (linearLayout2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("btKnownBox");
                linearLayout2 = null;
            }
            linearLayout2.removeAllViews();
            LinearLayout linearLayout3 = this.btAvailBox;
            if (linearLayout3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("btAvailBox");
                linearLayout3 = null;
            }
            linearLayout3.removeAllViews();
            if (!this.btLoaded) {
                LinearLayout linearLayout4 = this.btKnownBox;
                if (linearLayout4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("btKnownBox");
                    linearLayout = null;
                } else {
                    linearLayout = linearLayout4;
                }
                linearLayout.addView(empty("Loading..."));
                return;
            }
            Iterable $this$filter$iv = this.btDevices;
            Collection destination$iv$iv = new ArrayList();
            Iterator it2 = $this$filter$iv.iterator();
            while (true) {
                str2 = "connected";
                str3 = "paired";
                if (!it2.hasNext()) {
                    break;
                }
                Object element$iv$iv = it2.next();
                JSONObject it3 = (JSONObject) element$iv$iv;
                if (it3.optBoolean("paired") || it3.optBoolean("trusted") || it3.optBoolean("connected")) {
                    destination$iv$iv.add(element$iv$iv);
                }
            }
            List known = (List) destination$iv$iv;
            Iterable $this$filter$iv2 = this.btDevices;
            Collection destination$iv$iv2 = new ArrayList();
            for (Object element$iv$iv2 : $this$filter$iv2) {
                String psig2 = psig;
                JSONObject it4 = (JSONObject) element$iv$iv2;
                if ((it4.optBoolean("paired") || it4.optBoolean("trusted") || it4.optBoolean("connected")) ? false : true) {
                    destination$iv$iv2.add(element$iv$iv2);
                }
                psig = psig2;
            }
            List<JSONObject> avail = (List) destination$iv$iv2;
            if (known.isEmpty()) {
                LinearLayout linearLayout5 = this.btKnownBox;
                if (linearLayout5 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("btKnownBox");
                    linearLayout5 = null;
                }
                note("No saved devices yet.", linearLayout5);
            }
            Iterator it5 = known.iterator();
            while (it5.hasNext()) {
                final JSONObject d = (JSONObject) it5.next();
                String str$default = GxPage.str$default(this, d, "name", null, 2, null);
                if (StringsKt.isBlank(str$default)) {
                    str$default = GxPage.str$default(this, d, "address", null, 2, null);
                }
                final String name = str$default;
                boolean audio = d.optBoolean("audio");
                boolean ctl = d.optBoolean("controller");
                boolean connected = d.optBoolean(str2);
                boolean paired = d.optBoolean(str3);
                LinearLayout linearLayout6 = this.btKnownBox;
                if (linearLayout6 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("btKnownBox");
                    linearLayout6 = null;
                }
                LinearLayout c = card(linearLayout6);
                GxBluetoothPage gxBluetoothPage = this;
                LinearLayout linearLayout7 = c;
                CarUi.Companion_ companion = CarUi.Companion;
                GxPage.text$default(gxBluetoothPage, name, linearLayout7, 21.0f, connected ? companion.getACCENT() : companion.getTEXT(), false, 16, null);
                String str5 = str2;
                String lsig2 = lsig;
                List known2 = known;
                GxPage.text$default(this, ((audio && ctl) ? "Audio · Controller" : audio ? "Audio" : ctl ? "Controller" : "Bluetooth") + " · " + statusOf(d), c, 0.0f, 0, false, 28, null);
                ArrayList items = new ArrayList();
                if (paired || connected) {
                    str4 = str3;
                    it = it5;
                    items.add(new Triple(connected ? "Disconnect" : "Connect", Boolean.valueOf(this.btBusy.length() == 0), new Function0() { // from class: com.starpilot.starview.GxBluetoothPage$$ExternalSyntheticLambda10
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            Unit btPaint$lambda$14;
                            btPaint$lambda$14 = GxBluetoothPage.btPaint$lambda$14(GxBluetoothPage.this, d);
                            return btPaint$lambda$14;
                        }
                    }));
                } else {
                    str4 = str3;
                    it = it5;
                }
                if (audio) {
                    items.add(new Triple(isAudioSel(d) ? "Stop Using for Audio" : "Use for Audio", Boolean.valueOf(this.btBusy.length() == 0), new Function0() { // from class: com.starpilot.starview.GxBluetoothPage$$ExternalSyntheticLambda12
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            Unit btPaint$lambda$15;
                            btPaint$lambda$15 = GxBluetoothPage.btPaint$lambda$15(GxBluetoothPage.this, d);
                            return btPaint$lambda$15;
                        }
                    }));
                }
                if (audio && connected) {
                    items.add(new Triple("Test Audio", Boolean.valueOf(offroadOk()), new Function0() { // from class: com.starpilot.starview.GxBluetoothPage$$ExternalSyntheticLambda13
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            Unit btPaint$lambda$16;
                            btPaint$lambda$16 = GxBluetoothPage.btPaint$lambda$16(GxBluetoothPage.this, d);
                            return btPaint$lambda$16;
                        }
                    }));
                }
                if (paired) {
                    items.add(new Triple("Forget", Boolean.valueOf(offroadOk()), new Function0() { // from class: com.starpilot.starview.GxBluetoothPage$$ExternalSyntheticLambda14
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            Unit btPaint$lambda$18;
                            btPaint$lambda$18 = GxBluetoothPage.btPaint$lambda$18(GxBluetoothPage.this, name, d);
                            return btPaint$lambda$18;
                        }
                    }));
                }
                if (!items.isEmpty()) {
                    GxVehicleKt.gxVehicleButtons$default(this, c, items, false, 8, null);
                }
                lsig = lsig2;
                str2 = str5;
                known = known2;
                str3 = str4;
                it5 = it;
            }
            if (avail.isEmpty()) {
                String str6 = this.btDiscovering ? "Searching for nearby devices…" : "No nearby devices found.";
                LinearLayout linearLayout8 = this.btAvailBox;
                if (linearLayout8 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("btAvailBox");
                    linearLayout8 = null;
                }
                note(str6, linearLayout8);
            }
            for (final JSONObject d2 : avail) {
                GxBluetoothPage gxBluetoothPage2 = this;
                String str$default2 = GxPage.str$default(this, d2, "name", null, 2, null);
                if (StringsKt.isBlank(str$default2)) {
                    str$default2 = GxPage.str$default(this, d2, "address", null, 2, null);
                }
                String str7 = str$default2;
                String statusOf = statusOf(d2);
                LinearLayout linearLayout9 = this.btAvailBox;
                if (linearLayout9 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("btAvailBox");
                    linearLayout9 = null;
                }
                GxPage.Row r = GxPage.row$default(gxBluetoothPage2, str7, statusOf, linearLayout9, 0, 8, null);
                TextView b = r.button(isPairing(d2) ? "Pairing…" : "Pair", true, new Function0() { // from class: com.starpilot.starview.GxBluetoothPage$$ExternalSyntheticLambda15
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        Unit btPaint$lambda$20;
                        btPaint$lambda$20 = GxBluetoothPage.btPaint$lambda$20(GxBluetoothPage.this, d2);
                        return btPaint$lambda$20;
                    }
                });
                TextView textView9 = b;
                if (this.btOffroad) {
                    if ((this.btBusy.length() == 0) && !isPairing(d2)) {
                        z = true;
                        GxVehicleKt.gxVehicleEnable(textView9, z);
                    }
                }
                z = false;
                GxVehicleKt.gxVehicleEnable(textView9, z);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CharSequence btPaint$lambda$10(JSONObject it) {
        Intrinsics.checkNotNullParameter(it, "it");
        String jSONObject = it.toString();
        Intrinsics.checkNotNullExpressionValue(jSONObject, "toString(...)");
        return jSONObject;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit btPaint$lambda$14(GxBluetoothPage this$0, JSONObject $d) {
        String str = $d.optBoolean("connected") ? "disconnect" : "connect";
        JSONObject put = new JSONObject().put("address", GxPage.str$default(this$0, $d, "address", null, 2, null));
        Intrinsics.checkNotNullExpressionValue(put, "put(...)");
        this$0.btRequest(str, put);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit btPaint$lambda$15(GxBluetoothPage this$0, JSONObject $d) {
        JSONObject put = new JSONObject().put("address", this$0.isAudioSel($d) ? "" : GxPage.str$default(this$0, $d, "address", null, 2, null));
        Intrinsics.checkNotNullExpressionValue(put, "put(...)");
        this$0.btRequest("select_audio", put);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit btPaint$lambda$16(GxBluetoothPage this$0, JSONObject $d) {
        JSONObject put = new JSONObject().put("address", GxPage.str$default(this$0, $d, "address", null, 2, null));
        Intrinsics.checkNotNullExpressionValue(put, "put(...)");
        this$0.btRequest("test_audio", put);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit btPaint$lambda$18(final GxBluetoothPage this$0, String $name, final JSONObject $d) {
        this$0.confirm("Forget " + $name + "?", null, "Forget", new Function0() { // from class: com.starpilot.starview.GxBluetoothPage$$ExternalSyntheticLambda23
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit btPaint$lambda$18$lambda$17;
                btPaint$lambda$18$lambda$17 = GxBluetoothPage.btPaint$lambda$18$lambda$17(this$0, $d);
                return btPaint$lambda$18$lambda$17;
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit btPaint$lambda$18$lambda$17(GxBluetoothPage this$0, JSONObject $d) {
        JSONObject put = new JSONObject().put("address", GxPage.str$default(this$0, $d, "address", null, 2, null));
        Intrinsics.checkNotNullExpressionValue(put, "put(...)");
        this$0.btRequest("forget", put);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit btPaint$lambda$20(GxBluetoothPage this$0, JSONObject $d) {
        JSONObject put = new JSONObject().put("address", GxPage.str$default(this$0, $d, "address", null, 2, null));
        Intrinsics.checkNotNullExpressionValue(put, "put(...)");
        this$0.btRequest("pair", put);
        return Unit.INSTANCE;
    }

    /* VF: from Vineflower (jadx: ) */
    private final void paintPrompt() {
        LinearLayout var4 = this.btPromptBox;
        Object var3 = null;
        LinearLayout var2 = var4;
        if (var4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("btPromptBox");
            var2 = null;
        }

        var2.removeAllViews();
        this.btPairEdit = null;
        JSONObject var17 = this.btPrompt;
        if (var17 != null) {
            String var6 = GxPage.str$default(this, var17, "kind", null, 2, null);
            var2 = this.btPromptBox;
            if (var2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("btPromptBox");
                var2 = (LinearLayout)var3;
            }

            LinearLayout var5 = this.card((ViewGroup)var2);
            var5.setBackground((Drawable)this.getUi().rounded(CarUi.Companion.getCARD_ON(), 14, CarUi.Companion.getACCENT()));
            GxPage var7 = this;
            var3 = GxPage.str$default(this, var17, "name", null, 2, null);
            Object var9 = var3;
            if (StringsKt.isBlank((CharSequence)var3)) {
                var9 = "Bluetooth device";
            }

            label53: {
                GxPage.text$default(var7, "Pairing request · " + var9, (ViewGroup)var5, 21.0F, CarUi.Companion.getTEXT(), false, 16, null);
                var3 = this;
                switch (var6.hashCode()) {
                    case -1385570183:
                        if (var6.equals("authorization")) {
                            var9 = "Allow this device to connect?";
                            break label53;
                        }
                        break;
                    case -792038226:
                        if (var6.equals("passkey")) {
                            var9 = "Enter the device passkey.";
                            break label53;
                        }
                        break;
                    case 110997:
                        if (var6.equals("pin")) {
                            var9 = "Enter the PIN supplied by the device.";
                            break label53;
                        }
                        break;
                    case 2099153973:
                        if (var6.equals("confirmation")) {
                            var9 = "Confirm the pairing request.";
                            break label53;
                        }
                }

                var9 = "Follow the instructions on the device.";
            }

            GxPage.text$default((GxPage)var3, (String)var9, (ViewGroup)var5, 17.0F, 0, false, 24, null);
            var9 = GxPage.str$default(this, var17, "value", null, 2, null);
            if (!StringsKt.isBlank((CharSequence)var9)) {
                this.text("Passkey: " + var9, (ViewGroup)var5, 24.0F, CarUi.Companion.getACCENT(), true);
            }

            if (this.needsPairValue(var17)) {
                var3 = this;
                if (Intrinsics.areEqual(var6, "pin")) {
                    var9 = "PIN";
                } else {
                    var9 = "Passkey";
                }

                var9 = GxVehicleKt.gxVehicleEdit((GxPage)var3, (String)var9, "", 2);
                var5.addView((View)var9, (android.view.ViewGroup.LayoutParams)(new android.widget.LinearLayout.LayoutParams(-1, -2)));
                this.btPairEdit = (EditText)var9;
            }

            if (!var17.optBoolean("display_only")) {
                boolean var1 = Intrinsics.areEqual(this.btBusy, "pairing_response");
                GxVehicleKt.gxVehicleButtons(this, (ViewGroup)var5, CollectionsKt.listOf(
                        new Triple[]{
                            new Triple<>("Allow", var1 ^ true, new GxBluetoothPage$$ExternalSyntheticLambda20(this)),
                            new Triple<>("Cancel", var1 ^ true, new GxBluetoothPage$$ExternalSyntheticLambda21(this))
                        }
                    ), true);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paintPrompt$lambda$22(GxBluetoothPage this$0) {
        this$0.respondPairing(true);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paintPrompt$lambda$23(GxBluetoothPage this$0) {
        this$0.respondPairing(false);
        return Unit.INSTANCE;
    }

    private final void respondPairing(boolean accepted) {
        String str;
        Editable text;
        String obj;
        JSONObject p = this.btPrompt;
        if (p == null || Intrinsics.areEqual(this.btBusy, "pairing_response")) {
            return;
        }
        EditText editText = this.btPairEdit;
        if (editText == null || (text = editText.getText()) == null || (obj = text.toString()) == null || (str = StringsKt.trim((CharSequence) obj).toString()) == null) {
            str = "";
        }
        String value = str;
        if (accepted && needsPairValue(p)) {
            if (value.length() == 0) {
                this.btError = "Enter the value to continue pairing.";
                btPaint();
                return;
            }
        }
        JSONObject put = new JSONObject().put("prompt_id", GxPage.str$default(this, p, "id", null, 2, null)).put("accepted", accepted).put("value", value);
        Intrinsics.checkNotNullExpressionValue(put, "put(...)");
        btRequest("pairing_response", put);
    }

    private final void buildWc(LinearLayout box) {
        note("Map buttons to favorites or controller-only actions, or explicitly select one gamepad for Joystick Mode.", box);
        this.wcBox = box(box);
        wcPaint();
        getArray("/assets/components/tools/device_settings_layout.json", new Function2() { // from class: com.starpilot.starview.GxBluetoothPage$$ExternalSyntheticLambda22
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit buildWc$lambda$27;
                buildWc$lambda$27 = GxBluetoothPage.buildWc$lambda$27(GxBluetoothPage.this, (String) obj, (JSONArray) obj2);
                return buildWc$lambda$27;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildWc$lambda$27(GxBluetoothPage this$0, String e, JSONArray arr) {
        JSONArray ps;
        if (e != null || arr == null) {
            return Unit.INSTANCE;
        }
        HashMap map = new HashMap();
        ArrayList order = new ArrayList();
        for (JSONObject s : this$0.objects(arr)) {
            String optString = s.optString("name");
            if (StringsKt.isBlank(optString)) {
                optString = null;
            }
            String name = optString;
            if (name != null && (ps = s.optJSONArray("params")) != null) {
                order.add(name);
                for (JSONObject p : this$0.objects(ps)) {
                    String optString2 = p.optString("key");
                    if (StringsKt.isBlank(optString2)) {
                        optString2 = null;
                    }
                    String it = optString2;
                    if (it != null) {
                        map.put(it, name);
                    }
                }
            }
        }
        this$0.wcKeySection = map;
        this$0.wcSectionOrder = order;
        return Unit.INSTANCE;
    }

    private final void wcRefresh() {
        if (this.wcInflight) {
            return;
        }
        this.wcInflight = true;
        getJson("/api/wheel-controls/status", new Function2() { // from class: com.starpilot.starview.GxBluetoothPage$$ExternalSyntheticLambda25
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit wcRefresh$lambda$32;
                wcRefresh$lambda$32 = GxBluetoothPage.wcRefresh$lambda$32(GxBluetoothPage.this, (String) obj, (JSONObject) obj2);
                return wcRefresh$lambda$32;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit wcRefresh$lambda$32(GxBluetoothPage this$0, String e, JSONObject p) {
        List<JSONObject> emptyList;
        List<JSONObject> emptyList2;
        List<JSONObject> emptyList3;
        List<JSONObject> emptyList4;
        List<JSONObject> emptyList5;
        this$0.wcInflight = false;
        if (e != null || p == null) {
            this$0.wcAvailable = false;
            this$0.wcError = e == null ? "Wheel controls are unavailable" : e;
        } else {
            this$0.wcRaw = p.toString();
            this$0.wcAvailable = p.optBoolean("available");
            this$0.wcOffroad = p.optBoolean("offroad");
            this$0.wcLearning = p.optBoolean("learning");
            JSONArray optJSONArray = p.optJSONArray("devices");
            if (optJSONArray == null || (emptyList = this$0.objects(optJSONArray)) == null) {
                emptyList = CollectionsKt.emptyList();
            }
            this$0.wcDevices = emptyList;
            JSONArray optJSONArray2 = p.optJSONArray("mappings");
            if (optJSONArray2 == null || (emptyList2 = this$0.objects(optJSONArray2)) == null) {
                emptyList2 = CollectionsKt.emptyList();
            }
            this$0.wcMappings = emptyList2;
            JSONArray optJSONArray3 = p.optJSONArray("slots");
            if (optJSONArray3 == null || (emptyList3 = this$0.objects(optJSONArray3)) == null) {
                emptyList3 = CollectionsKt.emptyList();
            }
            this$0.wcSlots = emptyList3;
            JSONArray optJSONArray4 = p.optJSONArray("controller_slots");
            if (optJSONArray4 == null || (emptyList4 = this$0.objects(optJSONArray4)) == null) {
                emptyList4 = CollectionsKt.emptyList();
            }
            this$0.wcControllerSlots = emptyList4;
            JSONArray optJSONArray5 = p.optJSONArray("controller_options");
            if (optJSONArray5 == null || (emptyList5 = this$0.objects(optJSONArray5)) == null) {
                emptyList5 = CollectionsKt.emptyList();
            }
            this$0.wcOptions = emptyList5;
            this$0.wcDisconnectOffroad = p.optBoolean("disconnect_controllers_offroad");
            Object opt = p.opt("joystick_device");
            String str = opt instanceof String ? (String) opt : null;
            if (str == null) {
                str = "";
            }
            this$0.wcJoystick = str;
            Object opt2 = p.opt("learning_slot");
            Number number = opt2 instanceof Number ? (Number) opt2 : null;
            this$0.wcLearningSlot = number != null ? Integer.valueOf(number.intValue()) : null;
            double it = p.optDouble("remaining_seconds", 0.0d);
            if (Double.isNaN(it)) {
                it = 0.0d;
            }
            this$0.wcRemaining = it;
            this$0.wcTesting = p.optBoolean("testing");
            this$0.wcLastTested = p.optJSONObject("last_tested");
            String str2 = "mph";
            String str3 = this$0.str(p, "speed_unit", "mph");
            if (!StringsKt.isBlank(str3)) {
                str2 = str3;
            }
            this$0.wcSpeedUnit = str2;
            double d = 5.0d;
            double it2 = p.optDouble("speed_minimum", 5.0d);
            if (!Double.isNaN(it2)) {
                if (!(it2 == 0.0d)) {
                    d = it2;
                }
            }
            this$0.wcSpeedMin = d;
            double d2 = 90.0d;
            double it3 = p.optDouble("speed_maximum", 90.0d);
            if (!Double.isNaN(it3)) {
                if (!(it3 == 0.0d)) {
                    d2 = it3;
                }
            }
            this$0.wcSpeedMax = d2;
            this$0.wcError = "";
        }
        this$0.wcLoaded = true;
        this$0.wcPaint();
        return Unit.INSTANCE;
    }

    static /* synthetic */ void wcRequest$default(GxBluetoothPage gxBluetoothPage, String str, JSONObject jSONObject, int i, Object obj) {
        if ((i & 2) != 0) {
            jSONObject = new JSONObject();
        }
        gxBluetoothPage.wcRequest(str, jSONObject);
    }

    private final void wcRequest(String op, JSONObject body) {
        if (this.wcBusy.length() > 0) {
            this.wcSig = "";
            wcPaint();
        } else {
            this.wcBusy = op;
            wcPaint();
            GxPage.send$default(this, "POST", "/api/wheel-controls/" + op, body, 0L, new Function2() { // from class: com.starpilot.starview.GxBluetoothPage$$ExternalSyntheticLambda28
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    Unit wcRequest$lambda$33;
                    wcRequest$lambda$33 = GxBluetoothPage.wcRequest$lambda$33(GxBluetoothPage.this, (String) obj, (JSONObject) obj2);
                    return wcRequest$lambda$33;
                }
            }, 8, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit wcRequest$lambda$33(GxBluetoothPage this$0, String e, JSONObject jSONObject) {
        this$0.wcBusy = "";
        if (e != null) {
            this$0.wcError = e;
            this$0.toast(e);
        } else {
            this$0.wcError = "";
        }
        this$0.wcSig = "";
        this$0.wcPaint();
        if (e == null) {
            this$0.wcRefresh();
        }
        return Unit.INSTANCE;
    }

    private final boolean wcDisabled() {
        if (this.wcOffroad) {
            return this.wcBusy.length() > 0;
        }
        return true;
    }

    private final boolean configured(JSONObject s) {
        if (s == null || !s.optBoolean("enabled")) {
            return false;
        }
        return GxPage.str$default(this, s, "key", null, 2, null).length() > 0;
    }

    private final JSONObject optionByKey(String key) {
        Object element$iv;
        Iterable $this$firstOrNull$iv = this.wcOptions;
        Iterator it = $this$firstOrNull$iv.iterator();
        while (true) {
            if (it.hasNext()) {
                element$iv = it.next();
                JSONObject it2 = (JSONObject) element$iv;
                if (Intrinsics.areEqual(GxPage.str$default(this, it2, "key", null, 2, null), key)) {
                    break;
                }
            } else {
                element$iv = null;
                break;
            }
        }
        return (JSONObject) element$iv;
    }

    private final boolean learningAt(int slot) {
        Integer num;
        return this.wcLearning && (num = this.wcLearningSlot) != null && num.intValue() == slot;
    }

    private final List<JSONObject> mappingsOf(int slot) {
        Iterable $this$filter$iv = this.wcMappings;
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            JSONObject it = (JSONObject) element$iv$iv;
            Object opt = it.opt("slot");
            Number number = opt instanceof Number ? (Number) opt : null;
            boolean z = false;
            if (number != null && number.intValue() == slot) {
                z = true;
            }
            if (z) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        return (List) destination$iv$iv;
    }

    private final String listenLabel(int slot) {
        if (!learningAt(slot)) {
            return "Learn Button";
        }
        int s = Math.max(0, (int) Math.ceil(this.wcRemaining));
        return s > 0 ? "Listening (" + s + "s)" : "Listening...";
    }

    private final String eventName(JSONObject m) {
        String str$default = GxPage.str$default(this, m, "event_name", null, 2, null);
        if (StringsKt.isBlank(str$default)) {
            Object opt = m.opt("event_code");
            if (opt == null) {
                opt = "";
            }
            str$default = "Button " + opt;
        }
        return str$default;
    }

    private final String targetName(int slot) {
        int i;
        StringBuilder sb;
        String str;
        if (slot < 3) {
            i = slot + 1;
            sb = new StringBuilder();
            str = "Favorite #";
        } else {
            i = slot - 2;
            sb = new StringBuilder();
            str = "Controller Action #";
        }
        return sb.append(str).append(i).toString();
    }

    /* VF: from Vineflower (jadx: Code restructure failed;incorrect) */
    private final void wcPaint() {
        LinearLayout var95 = null; // declarations Vineflower left out
        LinearLayout var10 = this.wcBox;
        if (var10 != null) {
            String var11 = this.wcRaw;
            Object var8 = this.wcBusy;
            String var9 = this.wcError;
            boolean var5 = this.wcLoaded;
            var11 = var11 + "|" + var8 + "|" + var9 + "|" + var5;
            if (!Intrinsics.areEqual(var11, this.wcSig)) {
                this.wcSig = var11;
                var10.removeAllViews();
                if (!this.wcLoaded) {
                    var10.addView((View)this.empty("Loading..."));
                } else {
                    if (!this.wcOffroad) {
                        GxVehicleKt.gxVehicleNotice(this, "Mappings can only be changed while offroad. Mapped buttons continue working onroad.", CarUi.Companion.getWARN(), (ViewGroup)var10);
                    }

                    if (!StringsKt.isBlank(this.wcError)) {
                        GxVehicleKt.gxVehicleNotice(this, this.wcError, GxVehicleKt.GX_VEHICLE_ERR, (ViewGroup)var10);
                    }

                    if (!this.wcAvailable && this.wcMappings.isEmpty()) {
                        this.note("The wheel control service is starting.", (ViewGroup)var10);
                    }

                    if (!this.wcDisabled() && !this.wcMappings.isEmpty()) {
                        var5 = true;
                    } else {
                        var5 = false;
                    }

                    GxPage var12 = this;
                    ViewGroup var57 = (ViewGroup)var10;
                    if (this.wcTesting) {
                        var8 = "Stop Testing";
                    } else {
                        var8 = "Test Buttons";
                    }

                    Triple var13 = new Triple<>(var8, var5, new GxBluetoothPage$$ExternalSyntheticLambda29(this));
                    Triple var27 = new Triple<>("Clear All", var5, new GxBluetoothPage$$ExternalSyntheticLambda30(this));
                    List var28 = CollectionsKt.listOf(new Triple[]{var13, var27});
                    GxVehicleKt.gxVehicleButtons(var12, var57, var28, true);
                    if (this.wcTesting) {
                        LinearLayout var81 = this.card((ViewGroup)var10);
                        JSONObject var14 = this.wcLastTested;
                        Integer var29;
                        if (var14 == null) {
                            var29 = CarUi.Companion.getDIM();
                            var9 = "Waiting for a button";
                        } else if (var14.optBoolean("mapped")) {
                            var29 = CarUi.Companion.getACCENT();
                            var9 = "Successful";
                        } else {
                            var29 = GxVehicleKt.GX_VEHICLE_ERR;
                            var9 = "Not mapped";
                        }

                        Pair var30 = TuplesKt.to(var9, var29);
                        var9 = (String)var30.component1();
                        int var3 = ((Number)var30.component2()).intValue();
                        var81.setBackground((Drawable)this.getUi().rounded(CarUi.Companion.getCARD(), 14, var3));
                        this.text(var9, (ViewGroup)var81, 21.0F, var3, true);
                        GxPage var86 = this;
                        if (var14 == null) {
                            var8 = "Press a button to verify its mapping. Controller inputs are temporarily consumed while testing is enabled.";
                        } else {
                            CharSequence var60 = GxPage.str$default(this, var14, "device_name", null, 2, null);
                            var8 = var60;
                            if (StringsKt.isBlank(var60)) {
                                var8 = "External input";
                            }

                            String var15 = ((String) var8);
                            var9 = this.eventName(var14);
                            StringBuilder var62;
                            if (var14.optBoolean("mapped")) {
                                var8 = (String)var14.opt("slot");
                                Object var34;
                                if (var8 instanceof Number) {
                                    var34 = (Number)var8;
                                } else {
                                    var34 = null;
                                }

                                if (var34 != null) {
                                    var3 = ((Number) var34).intValue();
                                } else {
                                    var3 = 0;
                                }

                                var8 = this.targetName(var3);
                                var62 = new StringBuilder().append(var9).append(" on ").append(var15).append(" is mapped to ").append(var8);
                                var8 = ".";
                            } else {
                                var62 = new StringBuilder().append(var9).append(" on ").append(var15);
                                var8 = " does not have a mapping.";
                            }

                            var8 = var62.append(var8).toString();
                        }

                        GxPage.text$default(var86, ((String) var8), (ViewGroup)var81, 17.0F, 0, false, 24, null);
                    }

                    GxVehicleKt.gxVehicleEnable((View & Switch)this.toggle(
                                "Disconnect controllers when offroad",
                                "After two minutes offroad, paired controllers disconnect to save battery and reconnect when the car starts. Bluetooth and audio-only devices stay connected.",
                                this.wcDisconnectOffroad,
                                (ViewGroup)var10,
                                new GxBluetoothPage$$ExternalSyntheticLambda31(this)
                            )
                            .component2(), this.wcDisabled() ^ true);
                    this.header("Connected input devices", (ViewGroup)var10);
                    this.note(
                        "Favorite buttons are the default, with controller-only actions available below. Only the selected gamepad controls Joystick Mode.",
                        (ViewGroup)var10
                    );
                    if (this.wcDevices.isEmpty()) {
                        this.note("Connect or pair a controller, macropad, or keyboard.", (ViewGroup)var10);
                    }

                    for (JSONObject var87 : this.wcDevices) {
                        boolean var6 = var87.optBoolean("joystick_capable");
                        GxPage var92 = this;
                        CharSequence var63 = GxPage.str$default(this, var87, "name", null, 2, null);
                        var8 = var63;
                        if (StringsKt.isBlank(var63)) {
                            var8 = GxPage.str$default(this, var87, "device_id", null, 2, null);
                        }

                        var9 = ((String) var8);
                        if (var6) {
                            var8 = "Buttons and joystick axes";
                        } else {
                            var8 = "Buttons only";
                        }

                        GxPage.Row var65 = GxPage.row$default(var92, var9, ((String) var8), (ViewGroup)var10, 0, 8, null);
                        if (var6) {
                            String var88 = GxPage.str$default(this, var87, "device_id", null, 2, null);
                            var6 = Intrinsics.areEqual(var88, this.wcJoystick);
                            if (var6) {
                                var8 = "Enabled for Joystick Mode";
                            } else {
                                var8 = "Enable for Joystick Mode";
                            }

                            GxVehicleKt.gxVehicleEnable((View)var65.button(((String) var8), var6, new GxBluetoothPage$$ExternalSyntheticLambda32(this, var88, var6)), this.wcDisabled() ^ true);
                        }
                    }

                    this.header("On-screen Favorites", (ViewGroup)var10);
                    this.note("These remain linked to the three Favorite Slots shown elsewhere in StarPilot.", (ViewGroup)var10);
                    Iterator var83 = this.wcSlots.iterator();

                    for (int var18 = 0; var83.hasNext(); var18++) {
                        JSONObject var93 = (JSONObject)var83.next();
                        boolean var7 = this.configured(var93);
                        String var89 = "Favorite #" + (var18 + 1);
                        if (var7) {
                            CharSequence var66 = GxPage.str$default(this, var93, "label", null, 2, null);
                            var8 = var66;
                            if (StringsKt.isBlank(var66)) {
                                var8 = GxPage.str$default(this, var93, "key", null, 2, null);
                            }

                            var8 = var8;
                        } else {
                            var8 = "Not configured";
                        }

                        if (var7) {
                            var9 = this.listenLabel(var18);
                        } else {
                            var9 = null;
                        }

                        boolean var23;
                        if (var7 && !this.wcDisabled() && !this.wcTesting) {
                            var23 = true;
                        } else {
                            var23 = false;
                        }

                        LinearLayout var68 = this.slotCard(var10, var89, ((String) var8), var9, var23, new GxBluetoothPage$$ExternalSyntheticLambda33(this, var18));
                        GxPage var90 = this;
                        if (var7) {
                            var8 = "Pressing any mapped button will run this favorite.";
                        } else {
                            var8 = "Choose and enable this slot in Toggles → Favorites.";
                        }

                        GxPage.text$default(var90, ((String) var8), (ViewGroup)var68, 0.0F, 0, false, 28, null);
                        this.slotTail(var68, var18);
                    }

                    var var43 = CollectionsKt.take(this.wcSlots, 3);
                    boolean var19;
                    if (var43 instanceof Collection && ((Collection)var43).isEmpty()) {
                        var19 = true;
                    } else {
                        Iterator var44 = var43.iterator();

                        while (true) {
                            if (!var44.hasNext()) {
                                var19 = true;
                                break;
                            }

                            if (this.configured((JSONObject)var44.next())) {
                                var19 = false;
                                break;
                            }
                        }
                    }

                    if (var19) {
                        this.note("Choose and enable these slots in Toggles to map buttons to them.", (ViewGroup)var10);
                        var12 = this;
                        Pair var69 = TuplesKt.to("Open Favorites", new GxBluetoothPage$$ExternalSyntheticLambda34(this));
                        ViewGroup var45 = (ViewGroup)var10;
                        GxPage.buttons$default(var12, new Pair[]{var69}, var45, false, 4, null);
                    }

                    this.header("Controller-only Actions", (ViewGroup)var10);
                    this.note("Ten additional actions for physical buttons. These never appear as on-screen Favorites.", (ViewGroup)var10);
                    Iterator var85 = this.wcControllerSlots.iterator();

                    for (int var20 = 0; var85.hasNext(); var20++) {
                        JSONObject var96 = (JSONObject)var85.next();
                        int var4 = var20 + 3;
                        String var91 = GxPage.str$default(this, var96, "key", null, 2, null);
                        JSONObject var16 = this.optionByKey(var91);
                        String var94 = "Controller Action #" + (var20 + 1);
                        if (var96.optBoolean("enabled")) {
                            CharSequence var71 = GxPage.str$default(this, var96, "label", null, 2, null);
                            var8 = var71;
                            if (StringsKt.isBlank(var71)) {
                                var8 = "Configured";
                            }

                            var8 = var8;
                        } else {
                            var8 = "Not configured";
                        }

                        var11 = this.listenLabel(var4);
                        boolean var24;
                        if (var96.optBoolean("enabled") && !this.wcDisabled() && !this.wcTesting) {
                            var24 = true;
                        } else {
                            var24 = false;
                        }

                        label249: {
                            var95 = this.slotCard(var10, var94, ((String) var8), var11, var24, new GxBluetoothPage$$ExternalSyntheticLambda35(this, var4));
                            if (var16 != null) {
                                var8 = GxPage.str$default(this, var16, "label", null, 2, null);
                                if (var8 != null) {
                                    CharSequence var73 = ((CharSequence) var8);
                                    CharSequence var49 = var73;
                                    if (StringsKt.isBlank(var73)) {
                                        var49 = null;
                                    }

                                    var8 = (String)var49;
                                    if (var8 != null) {
                                        break label249;
                                    }
                                }
                            }

                            CharSequence var74 = GxPage.str$default(this, var96, "label", null, 2, null);
                            var8 = var74;
                            if (StringsKt.isBlank(var74)) {
                                CharSequence var75 = var91;
                                var8 = var75;
                                if (StringsKt.isBlank(var75)) {
                                    var8 = "Not configured";
                                }

                                var8 = var8;
                            }

                            var8 = var8;
                        }

                        GxVehicleKt.gxVehicleButtons$default(
                            this,
                            (ViewGroup)var95,
                            CollectionsKt.listOf(
                                new Triple<>(var8 + " · Choose action", this.wcDisabled() ^ true, new GxBluetoothPage$$ExternalSyntheticLambda1(this, var20))
                            ),
                            false,
                            8,
                            null
                        );
                        if (var16 != null) {
                            var8 = GxPage.str$default(this, var16, "value_type", null, 2, null);
                        } else {
                            var8 = null;
                        }

                        var24 = Intrinsics.areEqual(var8, "speed");
                        if (var24) {
                            double var1 = this.slotValue(var96, var16);
                            LinearLayout var54 = new LinearLayout((Context)this.getAct());
                            var54.setOrientation(0);
                            var54.setGravity(16);
                            TextView var76 = new TextView((Context)this.getAct());
                            String var97 = this.wcSpeedUnit;
                            var76.setText("Set speed (" + var97 + ")");
                            var76.setTextColor(CarUi.Companion.getTEXT());
                            var76.setTextSize(18.0F);
                            var54.addView((View)var76, (android.view.ViewGroup.LayoutParams)(new android.widget.LinearLayout.LayoutParams(0, -2, 1.0F)));
                            TextView var77 = new TextView((Context)this.getAct());
                            var77.setText(this.fmtNum(var1));
                            var77.setTextColor(CarUi.Companion.getTEXT());
                            var77.setTextSize(22.0F);
                            var77.setTypeface(Typeface.DEFAULT_BOLD);
                            var77.setPadding(this.getUi().dp(12), 0, this.getUi().dp(12), 0);
                            var54.addView((View)var77);
                            TextView var78 = CarUi.button$default(
                                this.getUi(), "Change", false, new GxBluetoothPage$$ExternalSyntheticLambda2(this, var1, var20, var91), 2, null
                            );
                            GxVehicleKt.gxVehicleEnable((View)var78, this.wcDisabled() ^ true);
                            var54.addView((View)var78);
                            View var98 = (View)var54;
                            android.widget.LinearLayout.LayoutParams var55 = new android.widget.LinearLayout.LayoutParams(-1, -2);
                            var55.bottomMargin = this.getUi().dp(6);
                            Unit var79 = Unit.INSTANCE;
                            var95.addView(var98, (android.view.ViewGroup.LayoutParams)var55);
                        }

                        GxPage var80 = this;
                        if (var24) {
                            var8 = "Uses the current mph/km/h setting and applies while openpilot is engaged on software-controlled cruise.";
                        } else if (Intrinsics.areEqual(var91, "__starpilot_controller_action__:selfie")) {
                            var8 = "Captures in the background and saves the driver-camera image in Galaxy → Sentry Mode.";
                        } else {
                            var8 = "Controller-only action. It does not create an on-screen Favorite button.";
                        }

                        GxPage.text$default(var80, ((String) var8), (ViewGroup)var95, 0.0F, 0, false, 28, null);
                        this.slotTail(var95, var4);
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit wcPaint$lambda$37(GxBluetoothPage this$0) {
        wcRequest$default(this$0, this$0.wcTesting ? "test-stop" : "test", null, 2, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit wcPaint$lambda$39(final GxBluetoothPage this$0) {
        this$0.confirm("Clear All", "Remove every controller mapping?", "Clear All", new Function0() { // from class: com.starpilot.starview.GxBluetoothPage$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit wcPaint$lambda$39$lambda$38;
                wcPaint$lambda$39$lambda$38 = GxBluetoothPage.wcPaint$lambda$39$lambda$38(this$0);
                return wcPaint$lambda$39$lambda$38;
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit wcPaint$lambda$39$lambda$38(GxBluetoothPage this$0) {
        wcRequest$default(this$0, "clear", null, 2, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit wcPaint$lambda$41(GxBluetoothPage this$0, boolean v) {
        JSONObject put = new JSONObject().put("enabled", v);
        Intrinsics.checkNotNullExpressionValue(put, "put(...)");
        this$0.wcRequest("offroad-disconnect", put);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit wcPaint$lambda$43(GxBluetoothPage this$0, String $id, boolean $sel) {
        JSONObject put = new JSONObject().put("device_id", $id).put("enabled", !$sel);
        Intrinsics.checkNotNullExpressionValue(put, "put(...)");
        this$0.wcRequest("joystick", put);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit wcPaint$lambda$45(GxBluetoothPage this$0, int $i) {
        String str = this$0.learningAt($i) ? "cancel" : "learn";
        JSONObject put = new JSONObject().put("slot", $i);
        Intrinsics.checkNotNullExpressionValue(put, "put(...)");
        this$0.wcRequest(str, put);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit wcPaint$lambda$47(GxBluetoothPage this$0) {
        this$0.getGx().openPage("gx:favorites");
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit wcPaint$lambda$49(GxBluetoothPage this$0, int $target) {
        String str = this$0.learningAt($target) ? "cancel" : "learn";
        JSONObject put = new JSONObject().put("slot", $target);
        Intrinsics.checkNotNullExpressionValue(put, "put(...)");
        this$0.wcRequest(str, put);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit wcPaint$lambda$53(GxBluetoothPage this$0, int $i) {
        this$0.pickAction($i);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit wcPaint$lambda$58(final GxBluetoothPage this$0, double $cur, final int $i, final String $key) {
        if (this$0.wcDisabled()) {
            return Unit.INSTANCE;
        }
        GxPage.prompt$default(this$0, "Set speed (" + this$0.wcSpeedUnit + ")", this$0.fmtNum(this$0.wcSpeedMin) + " – " + this$0.fmtNum(this$0.wcSpeedMax), this$0.fmtNum($cur), 8194, null, new Function1() { // from class: com.starpilot.starview.GxBluetoothPage$$ExternalSyntheticLambda26
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit wcPaint$lambda$58$lambda$57;
                wcPaint$lambda$58$lambda$57 = GxBluetoothPage.wcPaint$lambda$58$lambda$57(this$0, $i, $key, (String) obj);
                return wcPaint$lambda$58$lambda$57;
            }
        }, 16, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit wcPaint$lambda$58$lambda$57(GxBluetoothPage this$0, int $i, String $key, String t) {
        Intrinsics.checkNotNullParameter(t, "t");
        Double v = StringsKt.toDoubleOrNull(StringsKt.trim((CharSequence) t).toString());
        if (v == null || Double.isNaN(v.doubleValue()) || Double.isInfinite(v.doubleValue())) {
            this$0.toast("Enter a valid set speed.");
            return Unit.INSTANCE;
        }
        JSONObject put = new JSONObject().put("slot", $i).put("key", $key).put("value", v.doubleValue());
        Intrinsics.checkNotNullExpressionValue(put, "put(...)");
        this$0.wcRequest("action", put);
        return Unit.INSTANCE;
    }

    private final String fmtNum(double v) {
        return (v > Math.floor(v) ? 1 : (v == Math.floor(v) ? 0 : -1)) == 0 ? String.valueOf((long) v) : String.valueOf(v);
    }

    private final double slotValue(JSONObject s, JSONObject opt) {
        if (s != null && s.has("value") && !s.isNull("value")) {
            double v = s.optDouble("value");
            if (!Double.isNaN(v)) {
                return v;
            }
        }
        if (opt == null || !opt.has("default_value") || opt.isNull("default_value")) {
            return 30.0d;
        }
        double v2 = opt.optDouble("default_value");
        if (Double.isNaN(v2)) {
            return 30.0d;
        }
        return v2;
    }

    private final LinearLayout slotCard(LinearLayout into, String title, String sub, String learnLabel, boolean learnEnabled, final Function0<Unit> onLearn) {
        LinearLayout c = card(into);
        LinearLayout top = new LinearLayout(getAct());
        top.setOrientation(0);
        top.setGravity(16);
        LinearLayout tb = new LinearLayout(getAct());
        tb.setOrientation(1);
        TextView $this$slotCard_u24lambda_u2462 = new TextView(getAct());
        $this$slotCard_u24lambda_u2462.setText(title);
        $this$slotCard_u24lambda_u2462.setTextColor(CarUi.Companion.getDIM());
        $this$slotCard_u24lambda_u2462.setTextSize(14.0f);
        tb.addView($this$slotCard_u24lambda_u2462);
        TextView $this$slotCard_u24lambda_u2463 = new TextView(getAct());
        $this$slotCard_u24lambda_u2463.setText(sub);
        $this$slotCard_u24lambda_u2463.setTextColor(CarUi.Companion.getTEXT());
        $this$slotCard_u24lambda_u2463.setTextSize(21.0f);
        tb.addView($this$slotCard_u24lambda_u2463);
        top.addView(tb, new LinearLayout.LayoutParams(0, -2, 1.0f));
        if (learnLabel != null) {
            TextView b = getUi().button(learnLabel, !Intrinsics.areEqual(learnLabel, "Learn Button"), new Function0() { // from class: com.starpilot.starview.GxBluetoothPage$$ExternalSyntheticLambda27
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit slotCard$lambda$64;
                    slotCard$lambda$64 = GxBluetoothPage.slotCard$lambda$64(onLearn);
                    return slotCard$lambda$64;
                }
            });
            GxVehicleKt.gxVehicleEnable(b, learnEnabled);
            top.addView(b);
        }
        LinearLayout.LayoutParams $this$slotCard_u24lambda_u2465 = new LinearLayout.LayoutParams(-1, -2);
        $this$slotCard_u24lambda_u2465.bottomMargin = getUi().dp(8);
        Unit unit = Unit.INSTANCE;
        c.addView(top, $this$slotCard_u24lambda_u2465);
        return c;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit slotCard$lambda$64(Function0 $onLearn) {
        $onLearn.invoke();
        return Unit.INSTANCE;
    }

    private final void slotTail(LinearLayout c, int slot) {
        if (learningAt(slot)) {
            text("Press one button on your controller, macropad, or keyboard.", c, 17.0f, CarUi.Companion.getACCENT(), true);
        }
        List ms = mappingsOf(slot);
        if (ms.isEmpty()) {
            GxPage.text$default(this, "No buttons mapped.", c, 15.0f, CarUi.Companion.getDIM(), false, 16, null);
            return;
        }
        for (final JSONObject m : (Iterable<JSONObject>) ms) {
            LinearLayout line = new LinearLayout(getAct());
            boolean z = false;
            line.setOrientation(0);
            line.setGravity(16);
            line.setPadding(getUi().dp(12), getUi().dp(6), getUi().dp(6), getUi().dp(6));
            line.setBackground(CarUi.rounded$default(getUi(), CarUi.Companion.getCARD_ON(), 10, null, 4, null));
            LinearLayout tb = new LinearLayout(getAct());
            tb.setOrientation(1);
            TextView $this$slotTail_u24lambda_u2468 = new TextView(getAct());
            $this$slotTail_u24lambda_u2468.setText(eventName(m));
            $this$slotTail_u24lambda_u2468.setTextColor(CarUi.Companion.getTEXT());
            $this$slotTail_u24lambda_u2468.setTextSize(18.0f);
            $this$slotTail_u24lambda_u2468.setTypeface(Typeface.DEFAULT_BOLD);
            tb.addView($this$slotTail_u24lambda_u2468);
            String dev = GxPage.str$default(this, m, "device_name", null, 2, null);
            if (!StringsKt.isBlank(dev)) {
                TextView $this$slotTail_u24lambda_u2469 = new TextView(getAct());
                $this$slotTail_u24lambda_u2469.setText(dev);
                $this$slotTail_u24lambda_u2469.setTextColor(CarUi.Companion.getDIM());
                $this$slotTail_u24lambda_u2469.setTextSize(14.0f);
                tb.addView($this$slotTail_u24lambda_u2469);
            }
            line.addView(tb, new LinearLayout.LayoutParams(0, -2, 1.0f));
            TextView b = CarUi.button$default(getUi(), "Remove", false, new Function0() { // from class: com.starpilot.starview.GxBluetoothPage$$ExternalSyntheticLambda4
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit slotTail$lambda$70;
                    slotTail$lambda$70 = GxBluetoothPage.slotTail$lambda$70(GxBluetoothPage.this, m);
                    return slotTail$lambda$70;
                }
            }, 2, null);
            TextView textView = b;
            if (!wcDisabled() && !this.wcTesting) {
                z = true;
            }
            GxVehicleKt.gxVehicleEnable(textView, z);
            line.addView(b);
            LinearLayout.LayoutParams $this$slotTail_u24lambda_u2471 = new LinearLayout.LayoutParams(-1, -2);
            $this$slotTail_u24lambda_u2471.topMargin = getUi().dp(6);
            Unit unit = Unit.INSTANCE;
            c.addView(line, $this$slotTail_u24lambda_u2471);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit slotTail$lambda$70(GxBluetoothPage this$0, JSONObject $m) {
        JSONObject put = new JSONObject().put("id", GxPage.str$default(this$0, $m, "id", null, 2, null));
        Intrinsics.checkNotNullExpressionValue(put, "put(...)");
        this$0.wcRequest("delete", put);
        return Unit.INSTANCE;
    }

    /* VF: from Vineflower (jadx: Code restructure failed;incorrect) */
    private final String anchorKey(String var1) {
        int var2 = var1.hashCode();
        String var3 = "ConditionalExperimental";
        switch (var2) {
            case -1223291282:
                if (!var1.equals("ConditionalExperimental")) {
                    return var1;
                }
                break;
            case -1119957410:
                if (!var1.equals("ConditionalChill")) {
                    return var1;
                }
                break;
            case -878798333:
                if (!var1.equals("__starpilot_controller_action__:cycle_driving_personality")) {
                    return var1;
                }

                return "CustomPersonalities";
            case -370744618:
                if (!var1.equals("__starpilot_favorite_action__:toggle_traffic_mode")) {
                    return var1;
                }

                return "CustomPersonalities";
            case 499325643:
                if (!var1.equals("ExperimentalMode")) {
                    return var1;
                }
                break;
            default:
                return var1;
        }

        return var3;
    }

    private final String category(JSONObject o) {
        String str = this.wcKeySection.get(anchorKey(GxPage.str$default(this, o, "key", null, 2, null)));
        if (str != null) {
            return str;
        }
        if (Intrinsics.areEqual(GxPage.str$default(this, o, "section", null, 2, null), "Actions")) {
            return "Controller Actions";
        }
        String str$default = GxPage.str$default(this, o, "section", null, 2, null);
        if (StringsKt.isBlank(str$default)) {
            str$default = "Other";
        }
        return str$default;
    }

    /* VF: from Vineflower (jadx: Code restructure failed;Type inference failed;incorrect) */
    private final void pickAction(int var1) {
        String var19 = null; LinearLayout var38 = null; // declarations Vineflower left out
        if (!this.wcDisabled()) {
            List var6 = this.wcOptions;
            JSONObject var4 = CollectionsKt.getOrNull(this.wcControllerSlots, var1);
            Object var3 = var4;
            if (var4 == null) {
                var3 = new JSONObject();
            }

            String var7 = GxPage.str$default(this, ((JSONObject) var3), "key", null, 2, null);
            Iterator var5 = var6.iterator();

            JSONObject var8;
            do {
                if (!var5.hasNext()) {
                    var4 = null;
                    break;
                }

                var4 = (JSONObject)var5.next();
                var8 = var4;
            } while (!Intrinsics.areEqual(GxPage.str$default(this, var8, "key", null, 2, null), var7));

            HashSet var9;
            TextView var10;
            JSONObject var11;
            label98: {
                var11 = var4;
                var9 = new HashSet();
                var38 = new LinearLayout((Context)this.getAct());
                var38.setOrientation(1);
                var38.setPadding(this.getUi().dp(22), this.getUi().dp(4), this.getUi().dp(22), 0);
                var10 = new TextView((Context)this.getAct());
                if (var11 != null) {
                    String var29 = GxPage.str$default(this, var11, "label", null, 2, null);
                    if (var29 != null) {
                        CharSequence var36 = var29;
                        CharSequence var30 = var36;
                        if (StringsKt.isBlank(var36)) {
                            var30 = null;
                        }

                        String var31 = (String)var30;
                        if (var31 != null) {
                            var19 = var31;
                            break label98;
                        }
                    }
                }

                CharSequence var32 = GxPage.str$default(this, ((JSONObject) var3), "label", null, 2, null);
                var3 = var32;
                if (StringsKt.isBlank(var32)) {
                    CharSequence var33 = var7;
                    var3 = var33;
                    if (StringsKt.isBlank(var33)) {
                        var3 = "Not configured";
                    }

                    var3 = (String)var3;
                }

                var19 = (String)var3;
            }

            boolean var2;
            if (var7.length() > 0) {
                var2 = true;
            } else {
                var2 = false;
            }

            String var34;
            if (var2 && var11 == null) {
                var34 = " (unavailable)";
            } else {
                var34 = "";
            }

            var10.setText("Current: " + var19 + var34);
            var10.setTextColor(CarUi.Companion.getDIM());
            var10.setTextSize(16.0F);
            var38.addView((View)var10);
            EditText var40 = GxVehicleKt.gxVehicleEdit$default(this, "Search by name, setting or description", "", 0, 8, null);
            var38.addView((View)var40);
            TextView var12 = new TextView((Context)this.getAct());
            var12.setTextColor(CarUi.Companion.getDIM());
            var12.setTextSize(14.0F);
            var12.setPadding(0, this.getUi().dp(4), 0, this.getUi().dp(4));
            var38.addView((View)var12);
            LinearLayout var37 = new LinearLayout((Context)this.getAct());
            var37.setOrientation(1);
            ScrollView var35 = new ScrollView((Context)this.getAct());
            var35.addView((View)var37);
            var38.addView(
                (View)var35,
                (android.view.ViewGroup.LayoutParams)(new android.widget.LinearLayout.LayoutParams(-1, (int)(this.getAct().getResources().getDisplayMetrics().heightPixels * 0.5)))
            );
            ObjectRef var39 = new ObjectRef();
            Iterable var13 = var6;
            var var20 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var13, 10));
            Iterator var41 = var13.iterator();

            while (var41.hasNext()) {
                var20.add(this.category((JSONObject)var41.next()));
            }

            List var21 = var20;
            Set var14 = CollectionsKt.toSet(var21);
            Iterable var22 = this.wcSectionOrder;
            var var42 = new ArrayList();

            for (Object var16 : var22) {
                if (var14.contains((String)var16)) {
                    var42.add(var16);
                }
            }

            List var23 = var42;
            Collection var43 = var23;
            Iterable var24 = var6;
            var var44 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var24, 10));
            Iterator var45 = var24.iterator();

            while (var45.hasNext()) {
                var44.add(this.category((JSONObject)var45.next()));
            }

            List var25 = var44;
            List var26 = CollectionsKt.distinct(CollectionsKt.plus(var43, var25));
            var40.addTextChangedListener(
                new TextWatcher() {
                    final List<String> $catOrder = var26;
                    final TextView $count = var12;
                    final ObjectRef<AlertDialog> $dialog = var39;
                    final HashSet<String> $expanded = var9;
                    final int $i = var1;
                    final String $key = var7;
                    final List<JSONObject> $options = var6;
                    final LinearLayout $results = var37;
                    final EditText $search = var40;
                    final ScrollView $sv = var35;
                    final GxBluetoothPage this$0 = GxBluetoothPage.this;

                    public void afterTextChanged(Editable var1) {
                        GxBluetoothPage.pickAction$render(
                            this.$results, this.$search, this.$options, this.$count, this.$catOrder, this.$expanded, this.this$0, this.$key, this.$dialog, this.$i
                        );
                        this.$sv.scrollTo(0, 0);
                    }

                    public void beforeTextChanged(CharSequence var1, int var2x, int var3x, int var4x) {
                    }

                    public void onTextChanged(CharSequence var1, int var2x, int var3x, int var4x) {
                    }
                }
            );
            var39.element = new Builder((Context)this.getAct())
                .setTitle("Controller Action #" + (var1 + 1))
                .setView((View)var38)
                .setNegativeButton("Cancel", null)
                .create();
            pickAction$render(var37, var40, var6, var12, var26, var9, this, var7, var39, var1);
            AlertDialog var27;
            if (var39.element == null) {
                Intrinsics.throwUninitializedPropertyAccessException("dialog");
                var27 = null;
            } else {
                var27 = (AlertDialog)var39.element;
            }

            var27.show();
        }
    }

    private static final View pickAction$optionButton(final GxBluetoothPage this$0, String key, final Ref.ObjectRef<AlertDialog> objectRef, final int $i, JSONObject o, String detail) {
        String str;
        String str2;
        if (o == null || (str = GxPage.str$default(this$0, o, "key", null, 2, null)) == null) {
            str = "";
        }
        final String k = str;
        LinearLayout $this$pickAction_u24optionButton_u24lambda_u2485 = new LinearLayout(this$0.getAct());
        $this$pickAction_u24optionButton_u24lambda_u2485.setOrientation(1);
        $this$pickAction_u24optionButton_u24lambda_u2485.setPadding(this$0.getUi().dp(16), this$0.getUi().dp(10), this$0.getUi().dp(16), this$0.getUi().dp(10));
        $this$pickAction_u24optionButton_u24lambda_u2485.setMinimumHeight(this$0.getUi().dp(60));
        $this$pickAction_u24optionButton_u24lambda_u2485.setBackground(this$0.getUi().rounded(Intrinsics.areEqual(k, key) ? CarUi.Companion.getCARD_ON() : CarUi.Companion.getCARD(), 10, Intrinsics.areEqual(k, key) ? Integer.valueOf(CarUi.Companion.getACCENT()) : null));
        TextView $this$pickAction_u24optionButton_u24lambda_u2486 = new TextView(this$0.getAct());
        String str3 = Intrinsics.areEqual(k, key) ? "✓ " : "";
        if (o == null || (str2 = GxPage.str$default(this$0, o, "label", null, 2, null)) == null) {
            str2 = "Not configured";
        }
        $this$pickAction_u24optionButton_u24lambda_u2486.setText(str3 + str2);
        $this$pickAction_u24optionButton_u24lambda_u2486.setTextColor(Intrinsics.areEqual(k, key) ? CarUi.Companion.getACCENT() : CarUi.Companion.getTEXT());
        $this$pickAction_u24optionButton_u24lambda_u2486.setTextSize(18.0f);
        $this$pickAction_u24optionButton_u24lambda_u2486.setTypeface(Typeface.DEFAULT_BOLD);
        $this$pickAction_u24optionButton_u24lambda_u2485.addView($this$pickAction_u24optionButton_u24lambda_u2486);
        if (!StringsKt.isBlank(detail)) {
            TextView $this$pickAction_u24optionButton_u24lambda_u2487 = new TextView(this$0.getAct());
            $this$pickAction_u24optionButton_u24lambda_u2487.setText(detail);
            $this$pickAction_u24optionButton_u24lambda_u2487.setTextColor(CarUi.Companion.getDIM());
            $this$pickAction_u24optionButton_u24lambda_u2487.setTextSize(14.0f);
            $this$pickAction_u24optionButton_u24lambda_u2485.addView($this$pickAction_u24optionButton_u24lambda_u2487);
        }
        $this$pickAction_u24optionButton_u24lambda_u2485.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.GxBluetoothPage$$ExternalSyntheticLambda11
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                GxBluetoothPage.pickAction$optionButton$lambda$88(objectRef, this$0, $i, k, view);
            }
        });
        LinearLayout.LayoutParams $this$pickAction_u24optionButton_u24lambda_u2489 = new LinearLayout.LayoutParams(-1, -2);
        $this$pickAction_u24optionButton_u24lambda_u2489.bottomMargin = this$0.getUi().dp(6);
        $this$pickAction_u24optionButton_u24lambda_u2485.setLayoutParams($this$pickAction_u24optionButton_u24lambda_u2489);
        return $this$pickAction_u24optionButton_u24lambda_u2485;
    }

    /* VF: from Vineflower (jadx: ) */
    public static final void pickAction$optionButton$lambda$88(ObjectRef var0, GxBluetoothPage var1, int var2, String var3, View var4) {
        AlertDialog var5;
        if (var0.element == null) {
            Intrinsics.throwUninitializedPropertyAccessException("dialog");
            var5 = null;
        } else {
            var5 = (AlertDialog)var0.element;
        }

        var5.dismiss();
        var1.onActionSelect(var2, var3);
    }

    /* VF: from Vineflower (jadx: Removed duplicated region;incorrect) */
    public static final void pickAction$render(
        LinearLayout var0,
        EditText var1,
        List<? extends JSONObject> var2,
        TextView var3,
        List<String> var4,
        HashSet<String> var5,
        GxBluetoothPage var6,
        String var7,
        ObjectRef<AlertDialog> var8,
        int var9
    ) {
        var0.removeAllViews();
        String var20 = StringsKt.trim(var1.getText().toString()).toString().toLowerCase(Locale.ROOT);
        String var17 = "toLowerCase(...)";
        Intrinsics.checkNotNullExpressionValue(var20, "toLowerCase(...)");
        CharSequence var16 = var20;
        Regex var18 = new Regex("\\s+");
        int var11 = 0;
        Iterable var47 = var18.split(var16, 0);
        var var38 = new ArrayList();
        Iterator var48 = var47.iterator();

        while (true) {
            boolean var15 = var48.hasNext();
            byte var10 = 1;
            if (!var15) {
                List var22 = var38;
                Iterable var49 = var2;
                var var23 = new ArrayList();
                Iterable var39 = var49;
                Iterator var24 = var49.iterator();
                var10 = (byte)var11;

                while (var24.hasNext()) {
                    Object var25 = var24.next();
                    JSONObject var52 = (JSONObject)var25;
                    String[] var21 = new String[6];
                    var21[var10] = GxPage.str$default(var6, var52, "label", null, 2, null);
                    var21[1] = GxPage.str$default(var6, var52, "key", null, 2, null);
                    var21[2] = GxPage.str$default(var6, var52, "description", null, 2, null);
                    var21[3] = GxPage.str$default(var6, var52, "picker_label", null, 2, null);
                    var21[4] = GxPage.str$default(var6, var52, "picker_description", null, 2, null);
                    var21[5] = var6.category(var52);
                    String var27 = CollectionsKt.joinToString(CollectionsKt.listOf(var21), " ", "", "", -1, "...", null)
                        .toLowerCase(Locale.ROOT);
                    Intrinsics.checkNotNullExpressionValue(var27, var17);
                    var var60 = var22;
                    boolean var33;
                    Iterable var40;
                    Iterable var43;
                    String var50;
                    if (var60 instanceof Collection && ((Collection)var60).isEmpty()) {
                        var33 = true;
                        var43 = var39;
                        var40 = var49;
                        var50 = var17;
                    } else {
                        Iterator var26 = var60.iterator();

                        while (true) {
                            if (!var26.hasNext()) {
                                var50 = var17;
                                var43 = var39;
                                var33 = true;
                                var40 = var49;
                                break;
                            }

                            String var53 = (String)var26.next();
                            CharSequence var28 = var27;
                            CharSequence var29 = var53;
                            var10 = 0;
                            if (!StringsKt.contains(var28, var29, false)) {
                                var33 = false;
                                var43 = var39;
                                var40 = var49;
                                var50 = var17;
                                break;
                            }

                            var10 = 0;
                            var17 = var17;
                        }
                    }

                    if (var33) {
                        var23.add(var25);
                    }

                    var17 = var50;
                    var49 = var40;
                    var39 = var43;
                }

                byte var12 = 6;
                List var51 = var23;
                int var14 = var51.size();
                var11 = var51.size();
                int var13 = 1;
                String var41;
                if (var11 == 1) {
                    var41 = "action";
                } else {
                    var41 = "actions";
                }

                var3.setText(var14 + " " + var41);
                var0.addView(pickAction$optionButton(var6, var7, var8, var9, null, "Clear this assignment"));
                Iterator var58 = var4.iterator();
                byte var35 = var10;
                String var42 = var20;
                var10 = (byte)var13;

                while (var58.hasNext()) {
                    var20 = (String)var58.next();
                    Iterable var61 = var51;
                    var var44 = new ArrayList();

                    for (Object var65 : var61) {
                        if (Intrinsics.areEqual(var6.category((JSONObject)var65), var20)) {
                            var44.add(var65);
                        }
                    }

                    List var63 = var44;
                    if (!var63.isEmpty()) {
                        if (var42.length() > 0) {
                            var13 = var10;
                        } else {
                            var13 = var35;
                        }

                        if (var13 != 0 || var5.contains(var20)) {
                            var35 = var10;
                        }

                        TextView var66 = new TextView((Context)var6.getAct());
                        if (var35 != 0) {
                            var17 = "▾";
                        } else {
                            var17 = "▸";
                        }

                        var13 = var63.size();
                        var66.setText(var17 + "  " + var20 + " (" + var13 + ")");
                        var66.setTextColor(CarUi.Companion.getTEXT());
                        var66.setTextSize(18.0F);
                        var66.setTypeface(Typeface.DEFAULT_BOLD);
                        var66.setMinHeight(var6.getUi().dp(56));
                        var66.setGravity(16);
                        var66.setPadding(var6.getUi().dp(var12), var6.getUi().dp(8), var6.getUi().dp(var12), var6.getUi().dp(8));
                        var66.setOnClickListener(
                            new GxBluetoothPage$$ExternalSyntheticLambda3(var42, var5, var20, var0, var1, var2, var3, var4, var6, var7, var8, var9)
                        );
                        var0.addView((View)var66);
                        if (var35 != 0) {
                            for (JSONObject var46 : (Iterable<JSONObject>) var63) {
                                String[] var67 = new String[]{var20, null};
                                var67[var10] = GxPage.str$default(var6, var46, "description", null, 2, null);
                                Iterable var70 = CollectionsKt.listOf(var67);
                                var var68 = new ArrayList();

                                for (Object var72 : var70) {
                                    if (!StringsKt.isBlank((String & CharSequence)var72)) {
                                        var68.add(var72);
                                    }
                                }

                                var22 = var68;
                                var0.addView(
                                    pickAction$optionButton(
                                        var6, var7, var8, var9, var46, CollectionsKt.joinToString(var22, " · ", "", "", -1, "...", null)
                                    )
                                );
                            }
                        }

                        var35 = 0;
                    }
                }

                if (var51.isEmpty()) {
                    TextView var30 = new TextView((Context)var6.getAct());
                    var30.setText("No matching actions. Try another search or category.");
                    var30.setTextColor(CarUi.Companion.getDIM());
                    var30.setTextSize(16.0F);
                    var30.setPadding(var35, var6.getUi().dp(10), var35, var6.getUi().dp(10));
                    var0.addView((View)var30);
                }

                return;
            }

            Object var19 = var48.next();
            if (((String)var19).length() <= 0) {
                var10 = 0;
            }

            if ((var10 != 0)) {
                var38.add(var19);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void pickAction$render$lambda$95$lambda$94(String $q, HashSet $expanded, String $cat, LinearLayout $results, EditText $search, List $options, TextView $count, List $catOrder, GxBluetoothPage this$0, String $key, Ref.ObjectRef $dialog, int $i, View it) {
        if ($q.length() == 0) {
            if ($expanded.contains($cat)) {
                $expanded.remove($cat);
            } else {
                $expanded.add($cat);
            }
            pickAction$render($results, $search, $options, $count, $catOrder, $expanded, this$0, $key, $dialog, $i);
        }
    }

    private final void onActionSelect(int i, String key) {
        if (wcDisabled()) {
            return;
        }
        JSONObject opt = optionByKey(key);
        Object value = Intrinsics.areEqual(opt != null ? GxPage.str$default(this, opt, "value_type", null, 2, null) : null, "speed") ? Double.valueOf(slotValue((JSONObject) CollectionsKt.getOrNull(this.wcControllerSlots, i), opt)) : JSONObject.NULL;
        Intrinsics.checkNotNull(value);
        JSONObject put = new JSONObject().put("slot", i).put("key", key).put("value", value);
        Intrinsics.checkNotNullExpressionValue(put, "put(...)");
        wcRequest("action", put);
    }
}
