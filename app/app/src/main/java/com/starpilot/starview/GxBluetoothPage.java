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

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GxBluetoothPage(Activity act, GxHost gx) {
        super(act, gx);
        Intrinsics.checkNotNullParameter(act, "act");
        Intrinsics.checkNotNullParameter(gx, "gx");
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
        this.wcSpeedMin = 5.0d;
        this.wcSpeedMax = 90.0d;
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
        GxPage.tabs$default(this, CollectionsKt.listOf((Object[]) new String[]{"Bluetooth", "Controllers"}), 0, new Function1() { // from class: com.starpilot.starview.GxBluetoothPage$$ExternalSyntheticLambda16
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
        bs = GxVehicleKt.gxVehicleButtons(this, box, CollectionsKt.listOf((Object[]) new Triple[]{new Triple("Search for Devices", true, new Function0() { // from class: com.starpilot.starview.GxBluetoothPage$$ExternalSyntheticLambda7
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

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x008e, code lost:
    
        if (r0 != null) goto L25;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Unit btRefresh$lambda$8(GxBluetoothPage this$0, String e, JSONObject p) {
        String str;
        String str$default;
        List<JSONObject> emptyList;
        this$0.btInflight = false;
        if (e != null || p == null) {
            this$0.btAvailable = false;
            if (p != null && (str$default = GxPage.str$default(this$0, p, "error", null, 2, null)) != null) {
                String str2 = str$default;
                if (StringsKt.isBlank(str2)) {
                    str2 = null;
                }
                str = str2;
            }
            str = e == null ? "Bluetooth service unavailable" : e;
            this$0.btError = str;
        } else {
            this$0.btAvailable = p.optBoolean("available");
            this$0.btEnabled = p.optBoolean("enabled");
            this$0.btDiscovering = p.optBoolean("discovering");
            this$0.btOffroad = p.optBoolean("offroad");
            this$0.btSelectedAudio = GxPage.str$default(this$0, p, "selected_audio", null, 2, null);
            this$0.btPairingAddress = GxPage.str$default(this$0, p, "pairing_address", null, 2, null);
            JSONArray optJSONArray = p.optJSONArray("devices");
            if (optJSONArray == null || (emptyList = this$0.objects(optJSONArray)) == null) {
                emptyList = CollectionsKt.emptyList();
            }
            this$0.btDevices = emptyList;
            this$0.btPrompt = p.optJSONObject("prompt");
            this$0.btError = GxPage.str$default(this$0, p, "error", null, 2, null);
        }
        this$0.btLoaded = true;
        this$0.btPaint();
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

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private final void paintPrompt() {
        String str;
        Object obj;
        EditText e;
        LinearLayout linearLayout = this.btPromptBox;
        LinearLayout linearLayout2 = null;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("btPromptBox");
            linearLayout = null;
        }
        linearLayout.removeAllViews();
        this.btPairEdit = null;
        JSONObject p = this.btPrompt;
        if (p == null) {
            return;
        }
        String kind = GxPage.str$default(this, p, "kind", null, 2, null);
        LinearLayout linearLayout3 = this.btPromptBox;
        if (linearLayout3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("btPromptBox");
        } else {
            linearLayout2 = linearLayout3;
        }
        LinearLayout c = card(linearLayout2);
        c.setBackground(getUi().rounded(CarUi.Companion.getCARD_ON(), 14, Integer.valueOf(CarUi.Companion.getACCENT())));
        GxBluetoothPage gxBluetoothPage = this;
        String str$default = GxPage.str$default(this, p, "name", null, 2, null);
        if (StringsKt.isBlank(str$default)) {
            str$default = "Bluetooth device";
        }
        GxPage.text$default(gxBluetoothPage, "Pairing request · " + ((Object) str$default), c, 21.0f, CarUi.Companion.getTEXT(), false, 16, null);
        GxBluetoothPage gxBluetoothPage2 = this;
        switch (kind.hashCode()) {
            case -1385570183:
                if (kind.equals("authorization")) {
                    str = "Allow this device to connect?";
                    break;
                }
                str = "Follow the instructions on the device.";
                break;
            case -792038226:
                if (kind.equals("passkey")) {
                    str = "Enter the device passkey.";
                    break;
                }
                str = "Follow the instructions on the device.";
                break;
            case 110997:
                if (kind.equals("pin")) {
                    str = "Enter the PIN supplied by the device.";
                    break;
                }
                str = "Follow the instructions on the device.";
                break;
            case 2099153973:
                if (kind.equals("confirmation")) {
                    str = "Confirm the pairing request.";
                    break;
                }
                str = "Follow the instructions on the device.";
                break;
            default:
                str = "Follow the instructions on the device.";
                break;
        }
        GxPage.text$default(gxBluetoothPage2, str, c, 17.0f, 0, false, 24, null);
        String passkey = GxPage.str$default(this, p, "value", null, 2, null);
        if (StringsKt.isBlank(passkey)) {
            obj = "pin";
        } else {
            obj = "pin";
            text("Passkey: " + passkey, c, 24.0f, CarUi.Companion.getACCENT(), true);
        }
        if (needsPairValue(p)) {
            e = GxVehicleKt.gxVehicleEdit(this, Intrinsics.areEqual(kind, obj) ? "PIN" : "Passkey", "", 2);
            c.addView(e, new LinearLayout.LayoutParams(-1, -2));
            this.btPairEdit = e;
        }
        if (p.optBoolean("display_only")) {
            return;
        }
        boolean busy = Intrinsics.areEqual(this.btBusy, "pairing_response");
        GxVehicleKt.gxVehicleButtons(this, c, CollectionsKt.listOf((Object[]) new Triple[]{new Triple("Allow", Boolean.valueOf(!busy), new Function0() { // from class: com.starpilot.starview.GxBluetoothPage$$ExternalSyntheticLambda20
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit paintPrompt$lambda$22;
                paintPrompt$lambda$22 = GxBluetoothPage.paintPrompt$lambda$22(GxBluetoothPage.this);
                return paintPrompt$lambda$22;
            }
        }), new Triple("Cancel", Boolean.valueOf(!busy), new Function0() { // from class: com.starpilot.starview.GxBluetoothPage$$ExternalSyntheticLambda21
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit paintPrompt$lambda$23;
                paintPrompt$lambda$23 = GxBluetoothPage.paintPrompt$lambda$23(GxBluetoothPage.this);
                return paintPrompt$lambda$23;
            }
        })}), true);
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

    /* JADX WARN: Code restructure failed: missing block: B:144:0x04e6, code lost:
    
        if (r0 != null) goto L156;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final void wcPaint() {
        Iterable $this$none$iv;
        String key;
        String str;
        boolean canTest;
        String str$default;
        String str2;
        int i;
        Integer valueOf;
        String str3;
        StringBuilder append;
        String str4;
        String sb;
        int i2;
        LinearLayout linearLayout = this.wcBox;
        if (linearLayout == null) {
            return;
        }
        LinearLayout box = linearLayout;
        String sig = this.wcRaw + "|" + this.wcBusy + "|" + this.wcError + "|" + this.wcLoaded;
        if (Intrinsics.areEqual(sig, this.wcSig)) {
            return;
        }
        this.wcSig = sig;
        box.removeAllViews();
        if (!this.wcLoaded) {
            box.addView(empty("Loading..."));
            return;
        }
        if (!this.wcOffroad) {
            GxVehicleKt.gxVehicleNotice(this, "Mappings can only be changed while offroad. Mapped buttons continue working onroad.", CarUi.Companion.getWARN(), box);
        }
        if (!StringsKt.isBlank(this.wcError)) {
            String str5 = this.wcError;
            i2 = GxVehicleKt.GX_VEHICLE_ERR;
            GxVehicleKt.gxVehicleNotice(this, str5, i2, box);
        }
        if (!this.wcAvailable && this.wcMappings.isEmpty()) {
            note("The wheel control service is starting.", box);
        }
        boolean z = true;
        boolean canTest2 = (wcDisabled() || this.wcMappings.isEmpty()) ? false : true;
        GxBluetoothPage gxBluetoothPage = this;
        LinearLayout linearLayout2 = box;
        Triple[] tripleArr = new Triple[2];
        tripleArr[0] = new Triple(this.wcTesting ? "Stop Testing" : "Test Buttons", Boolean.valueOf(canTest2), new Function0() { // from class: com.starpilot.starview.GxBluetoothPage$$ExternalSyntheticLambda29
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit wcPaint$lambda$37;
                wcPaint$lambda$37 = GxBluetoothPage.wcPaint$lambda$37(GxBluetoothPage.this);
                return wcPaint$lambda$37;
            }
        });
        tripleArr[1] = new Triple("Clear All", Boolean.valueOf(canTest2), new Function0() { // from class: com.starpilot.starview.GxBluetoothPage$$ExternalSyntheticLambda30
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit wcPaint$lambda$39;
                wcPaint$lambda$39 = GxBluetoothPage.wcPaint$lambda$39(GxBluetoothPage.this);
                return wcPaint$lambda$39;
            }
        });
        GxVehicleKt.gxVehicleButtons(gxBluetoothPage, linearLayout2, CollectionsKt.listOf((Object[]) tripleArr), true);
        if (this.wcTesting) {
            LinearLayout c = card(box);
            JSONObject lt = this.wcLastTested;
            if (lt == null) {
                valueOf = Integer.valueOf(CarUi.Companion.getDIM());
                str3 = "Waiting for a button";
            } else if (lt.optBoolean("mapped")) {
                valueOf = Integer.valueOf(CarUi.Companion.getACCENT());
                str3 = "Successful";
            } else {
                i = GxVehicleKt.GX_VEHICLE_ERR;
                valueOf = Integer.valueOf(i);
                str3 = "Not mapped";
            }
            Pair pair = TuplesKt.to(str3, valueOf);
            String label = (String) pair.component1();
            int color = ((Number) pair.component2()).intValue();
            c.setBackground(getUi().rounded(CarUi.Companion.getCARD(), 14, Integer.valueOf(color)));
            text(label, c, 21.0f, color, true);
            GxBluetoothPage gxBluetoothPage2 = this;
            if (lt == null) {
                sb = "Press a button to verify its mapping. Controller inputs are temporarily consumed while testing is enabled.";
            } else {
                String str$default2 = GxPage.str$default(this, lt, "device_name", null, 2, null);
                if (StringsKt.isBlank(str$default2)) {
                    str$default2 = "External input";
                }
                String device = str$default2;
                String button = eventName(lt);
                if (lt.optBoolean("mapped")) {
                    Object opt = lt.opt("slot");
                    Number number = opt instanceof Number ? (Number) opt : null;
                    append = new StringBuilder().append(button).append(" on ").append(device).append(" is mapped to ").append(targetName(number != null ? number.intValue() : 0));
                    str4 = ".";
                } else {
                    append = new StringBuilder().append(button).append(" on ").append(device);
                    str4 = " does not have a mapping.";
                }
                sb = append.append(str4).toString();
            }
            GxPage.text$default(gxBluetoothPage2, sb, c, 17.0f, 0, false, 24, null);
        }
        Switch sw = toggle("Disconnect controllers when offroad", "After two minutes offroad, paired controllers disconnect to save battery and reconnect when the car starts. Bluetooth and audio-only devices stay connected.", this.wcDisconnectOffroad, box, new Function1() { // from class: com.starpilot.starview.GxBluetoothPage$$ExternalSyntheticLambda31
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit wcPaint$lambda$41;
                wcPaint$lambda$41 = GxBluetoothPage.wcPaint$lambda$41(GxBluetoothPage.this, ((Boolean) obj).booleanValue());
                return wcPaint$lambda$41;
            }
        }).component2();
        GxVehicleKt.gxVehicleEnable(sw, !wcDisabled());
        header("Connected input devices", box);
        note("Favorite buttons are the default, with controller-only actions available below. Only the selected gamepad controls Joystick Mode.", box);
        if (this.wcDevices.isEmpty()) {
            note("Connect or pair a controller, macropad, or keyboard.", box);
        }
        for (JSONObject d : this.wcDevices) {
            boolean joy = d.optBoolean("joystick_capable");
            GxBluetoothPage gxBluetoothPage3 = this;
            String str$default3 = GxPage.str$default(this, d, "name", null, 2, null);
            if (StringsKt.isBlank(str$default3)) {
                str$default3 = GxPage.str$default(this, d, "device_id", null, 2, null);
            }
            GxPage.Row r = GxPage.row$default(gxBluetoothPage3, str$default3, joy ? "Buttons and joystick axes" : "Buttons only", box, 0, 8, null);
            if (joy) {
                final String id = GxPage.str$default(this, d, "device_id", null, 2, null);
                final boolean sel = Intrinsics.areEqual(id, this.wcJoystick);
                GxVehicleKt.gxVehicleEnable(r.button(sel ? "Enabled for Joystick Mode" : "Enable for Joystick Mode", sel, new Function0() { // from class: com.starpilot.starview.GxBluetoothPage$$ExternalSyntheticLambda32
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        Unit wcPaint$lambda$43;
                        wcPaint$lambda$43 = GxBluetoothPage.wcPaint$lambda$43(GxBluetoothPage.this, id, sel);
                        return wcPaint$lambda$43;
                    }
                }), !wcDisabled());
            }
        }
        header("On-screen Favorites", box);
        note("These remain linked to the three Favorite Slots shown elsewhere in StarPilot.", box);
        int i3 = 0;
        for (JSONObject s : this.wcSlots) {
            final int i4 = i3;
            int i5 = i3 + 1;
            boolean conf = configured(s);
            String str6 = "Favorite #" + (i4 + 1);
            if (conf) {
                String str$default4 = GxPage.str$default(this, s, "label", null, 2, null);
                if (StringsKt.isBlank(str$default4)) {
                    str$default4 = GxPage.str$default(this, s, "key", null, 2, null);
                }
                str2 = str$default4;
            } else {
                str2 = "Not configured";
            }
            LinearLayout c2 = slotCard(box, str6, str2, conf ? listenLabel(i4) : null, (!conf || wcDisabled() || this.wcTesting) ? false : true, new Function0() { // from class: com.starpilot.starview.GxBluetoothPage$$ExternalSyntheticLambda33
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit wcPaint$lambda$45;
                    wcPaint$lambda$45 = GxBluetoothPage.wcPaint$lambda$45(GxBluetoothPage.this, i4);
                    return wcPaint$lambda$45;
                }
            });
            GxPage.text$default(this, conf ? "Pressing any mapped button will run this favorite." : "Choose and enable this slot in Toggles → Favorites.", c2, 0.0f, 0, false, 28, null);
            slotTail(c2, i4);
            i3 = i5;
        }
        Iterable $this$none$iv2 = CollectionsKt.take(this.wcSlots, 3);
        if (!($this$none$iv2 instanceof Collection) || !((Collection) $this$none$iv2).isEmpty()) {
            Iterator it = $this$none$iv2.iterator();
            while (true) {
                if (!it.hasNext()) {
                    $this$none$iv = 1;
                    break;
                }
                Object element$iv = it.next();
                JSONObject it2 = (JSONObject) element$iv;
                if (configured(it2)) {
                    $this$none$iv = null;
                    break;
                }
            }
        } else {
            $this$none$iv = 1;
        }
        if ($this$none$iv != null) {
            note("Choose and enable these slots in Toggles to map buttons to them.", box);
            GxPage.buttons$default(this, new Pair[]{TuplesKt.to("Open Favorites", new Function0() { // from class: com.starpilot.starview.GxBluetoothPage$$ExternalSyntheticLambda34
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit wcPaint$lambda$47;
                    wcPaint$lambda$47 = GxBluetoothPage.wcPaint$lambda$47(GxBluetoothPage.this);
                    return wcPaint$lambda$47;
                }
            })}, box, false, 4, null);
        }
        header("Controller-only Actions", box);
        note("Ten additional actions for physical buttons. These never appear as on-screen Favorites.", box);
        int i6 = 0;
        for (JSONObject s2 : this.wcControllerSlots) {
            final int i7 = i6;
            int i8 = i6 + 1;
            final int target = i7 + 3;
            final String key2 = GxPage.str$default(this, s2, "key", null, 2, null);
            JSONObject opt2 = optionByKey(key2);
            String key3 = "Controller Action #" + (i7 + 1);
            if (s2.optBoolean("enabled")) {
                String str$default5 = GxPage.str$default(this, s2, "label", null, 2, null);
                if (StringsKt.isBlank(str$default5)) {
                    str$default5 = "Configured";
                }
                key = str$default5;
            } else {
                key = "Not configured";
            }
            LinearLayout box2 = box;
            String sig2 = sig;
            LinearLayout c3 = slotCard(box, key3, key, listenLabel(target), (!s2.optBoolean("enabled") || wcDisabled() || this.wcTesting) ? false : z, new Function0() { // from class: com.starpilot.starview.GxBluetoothPage$$ExternalSyntheticLambda35
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit wcPaint$lambda$49;
                    wcPaint$lambda$49 = GxBluetoothPage.wcPaint$lambda$49(GxBluetoothPage.this, target);
                    return wcPaint$lambda$49;
                }
            });
            if (opt2 != null && (str$default = GxPage.str$default(this, opt2, "label", null, 2, null)) != null) {
                String str7 = str$default;
                if (StringsKt.isBlank(str7)) {
                    str7 = null;
                }
                str = str7;
            }
            String str$default6 = GxPage.str$default(this, s2, "label", null, 2, null);
            if (StringsKt.isBlank(str$default6)) {
                String str8 = key2;
                if (StringsKt.isBlank(str8)) {
                    str8 = "Not configured";
                }
                str$default6 = str8;
            }
            str = str$default6;
            String actLabel = str;
            GxVehicleKt.gxVehicleButtons$default(this, c3, CollectionsKt.listOf(new Triple(actLabel + " · Choose action", Boolean.valueOf(!wcDisabled()), new Function0() { // from class: com.starpilot.starview.GxBluetoothPage$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit wcPaint$lambda$53;
                    wcPaint$lambda$53 = GxBluetoothPage.wcPaint$lambda$53(GxBluetoothPage.this, i7);
                    return wcPaint$lambda$53;
                }
            })), false, 8, null);
            boolean speed = Intrinsics.areEqual(opt2 != null ? GxPage.str$default(this, opt2, "value_type", null, 2, null) : null, "speed");
            if (speed) {
                final double cur = slotValue(s2, opt2);
                LinearLayout $this$wcPaint_u24lambda_u2454 = new LinearLayout(getAct());
                $this$wcPaint_u24lambda_u2454.setOrientation(0);
                $this$wcPaint_u24lambda_u2454.setGravity(16);
                TextView $this$wcPaint_u24lambda_u2455 = new TextView(getAct());
                String actLabel2 = this.wcSpeedUnit;
                canTest = canTest2;
                $this$wcPaint_u24lambda_u2455.setText("Set speed (" + actLabel2 + ")");
                $this$wcPaint_u24lambda_u2455.setTextColor(CarUi.Companion.getTEXT());
                $this$wcPaint_u24lambda_u2455.setTextSize(18.0f);
                $this$wcPaint_u24lambda_u2454.addView($this$wcPaint_u24lambda_u2455, new LinearLayout.LayoutParams(0, -2, 1.0f));
                TextView $this$wcPaint_u24lambda_u2456 = new TextView(getAct());
                $this$wcPaint_u24lambda_u2456.setText(fmtNum(cur));
                $this$wcPaint_u24lambda_u2456.setTextColor(CarUi.Companion.getTEXT());
                $this$wcPaint_u24lambda_u2456.setTextSize(22.0f);
                $this$wcPaint_u24lambda_u2456.setTypeface(Typeface.DEFAULT_BOLD);
                $this$wcPaint_u24lambda_u2456.setPadding(getUi().dp(12), 0, getUi().dp(12), 0);
                $this$wcPaint_u24lambda_u2454.addView($this$wcPaint_u24lambda_u2456);
                TextView b = CarUi.button$default(getUi(), "Change", false, new Function0() { // from class: com.starpilot.starview.GxBluetoothPage$$ExternalSyntheticLambda2
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        Unit wcPaint$lambda$58;
                        wcPaint$lambda$58 = GxBluetoothPage.wcPaint$lambda$58(GxBluetoothPage.this, cur, i7, key2);
                        return wcPaint$lambda$58;
                    }
                }, 2, null);
                GxVehicleKt.gxVehicleEnable(b, !wcDisabled());
                $this$wcPaint_u24lambda_u2454.addView(b);
                LinearLayout linearLayout3 = $this$wcPaint_u24lambda_u2454;
                LinearLayout.LayoutParams $this$wcPaint_u24lambda_u2459 = new LinearLayout.LayoutParams(-1, -2);
                $this$wcPaint_u24lambda_u2459.bottomMargin = getUi().dp(6);
                Unit unit = Unit.INSTANCE;
                c3.addView(linearLayout3, $this$wcPaint_u24lambda_u2459);
            } else {
                canTest = canTest2;
            }
            GxPage.text$default(this, speed ? "Uses the current mph/km/h setting and applies while openpilot is engaged on software-controlled cruise." : Intrinsics.areEqual(key2, "__starpilot_controller_action__:selfie") ? "Captures in the background and saves the driver-camera image in Galaxy → Sentry Mode." : "Controller-only action. It does not create an on-screen Favorite button.", c3, 0.0f, 0, false, 28, null);
            slotTail(c3, target);
            i6 = i8;
            canTest2 = canTest;
            box = box2;
            sig = sig2;
            z = true;
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
        for (final JSONObject m : ms) {
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

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x0022, code lost:
    
        if (r3.equals("__starpilot_controller_action__:cycle_driving_personality") == false) goto L22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x0019, code lost:
    
        if (r3.equals("__starpilot_favorite_action__:toggle_traffic_mode") == false) goto L22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x0025, code lost:
    
        return "CustomPersonalities";
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final String anchorKey(String key) {
        switch (key.hashCode()) {
            case -1223291282:
                if (key.equals("ConditionalExperimental")) {
                    return "ConditionalExperimental";
                }
                return key;
            case -1119957410:
                if (key.equals("ConditionalChill")) {
                    return "ConditionalExperimental";
                }
                return key;
            case -878798333:
                break;
            case -370744618:
                break;
            case 499325643:
                if (key.equals("ExperimentalMode")) {
                    return "ConditionalExperimental";
                }
                return key;
            default:
                return key;
        }
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

    /* JADX WARN: Code restructure failed: missing block: B:22:0x00c3, code lost:
    
        if (r4 != null) goto L32;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v37, types: [T, android.app.AlertDialog] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final void pickAction(final int i) {
        Object element$iv;
        String str;
        AlertDialog alertDialog;
        String str$default;
        if (wcDisabled()) {
            return;
        }
        final List options = this.wcOptions;
        JSONObject jSONObject = (JSONObject) CollectionsKt.getOrNull(this.wcControllerSlots, i);
        if (jSONObject == null) {
            jSONObject = new JSONObject();
        }
        JSONObject slot = jSONObject;
        final String key = GxPage.str$default(this, slot, "key", null, 2, null);
        List $this$firstOrNull$iv = options;
        Iterator it = $this$firstOrNull$iv.iterator();
        while (true) {
            if (!it.hasNext()) {
                element$iv = null;
                break;
            }
            element$iv = it.next();
            JSONObject it2 = (JSONObject) element$iv;
            if (Intrinsics.areEqual(GxPage.str$default(this, it2, "key", null, 2, null), key)) {
                break;
            }
        }
        JSONObject sel = (JSONObject) element$iv;
        final HashSet expanded = new HashSet();
        LinearLayout $this$pickAction_u24lambda_u2474 = new LinearLayout(getAct());
        $this$pickAction_u24lambda_u2474.setOrientation(1);
        $this$pickAction_u24lambda_u2474.setPadding(getUi().dp(22), getUi().dp(4), getUi().dp(22), 0);
        TextView $this$pickAction_u24lambda_u2478 = new TextView(getAct());
        if (sel != null && (str$default = GxPage.str$default(this, sel, "label", null, 2, null)) != null) {
            String str2 = str$default;
            if (StringsKt.isBlank(str2)) {
                str2 = null;
            }
            str = str2;
        }
        String str$default2 = GxPage.str$default(this, slot, "label", null, 2, null);
        if (StringsKt.isBlank(str$default2)) {
            String str3 = key;
            if (StringsKt.isBlank(str3)) {
                str3 = "Not configured";
            }
            str$default2 = str3;
        }
        str = str$default2;
        $this$pickAction_u24lambda_u2478.setText("Current: " + str + (((key.length() > 0) && sel == null) ? " (unavailable)" : ""));
        $this$pickAction_u24lambda_u2478.setTextColor(CarUi.Companion.getDIM());
        $this$pickAction_u24lambda_u2478.setTextSize(16.0f);
        $this$pickAction_u24lambda_u2474.addView($this$pickAction_u24lambda_u2478);
        final EditText search = GxVehicleKt.gxVehicleEdit$default(this, "Search by name, setting or description", "", 0, 8, null);
        $this$pickAction_u24lambda_u2474.addView(search);
        final TextView $this$pickAction_u24lambda_u2479 = new TextView(getAct());
        $this$pickAction_u24lambda_u2479.setTextColor(CarUi.Companion.getDIM());
        $this$pickAction_u24lambda_u2479.setTextSize(14.0f);
        $this$pickAction_u24lambda_u2479.setPadding(0, getUi().dp(4), 0, getUi().dp(4));
        $this$pickAction_u24lambda_u2474.addView($this$pickAction_u24lambda_u2479);
        final LinearLayout $this$pickAction_u24lambda_u2480 = new LinearLayout(getAct());
        $this$pickAction_u24lambda_u2480.setOrientation(1);
        final ScrollView $this$pickAction_u24lambda_u2481 = new ScrollView(getAct());
        $this$pickAction_u24lambda_u2481.addView($this$pickAction_u24lambda_u2480);
        $this$pickAction_u24lambda_u2474.addView($this$pickAction_u24lambda_u2481, new LinearLayout.LayoutParams(-1, (int) (getAct().getResources().getDisplayMetrics().heightPixels * 0.5d)));
        final Ref.ObjectRef dialog = new Ref.ObjectRef();
        List $this$map$iv = options;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        for (Object item$iv$iv : $this$map$iv) {
            JSONObject it3 = (JSONObject) item$iv$iv;
            destination$iv$iv.add(category(it3));
        }
        Set cats = CollectionsKt.toSet((List) destination$iv$iv);
        Iterable $this$filter$iv = this.wcSectionOrder;
        Collection destination$iv$iv2 = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            Iterable $this$filter$iv2 = $this$filter$iv;
            String it4 = (String) element$iv$iv;
            if (cats.contains(it4)) {
                destination$iv$iv2.add(element$iv$iv);
            }
            $this$filter$iv = $this$filter$iv2;
        }
        ArrayList arrayList = (List) destination$iv$iv2;
        List $this$map$iv2 = options;
        Collection destination$iv$iv3 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv2, 10));
        for (Object item$iv$iv2 : $this$map$iv2) {
            Iterable $this$map$iv3 = $this$map$iv2;
            JSONObject it5 = (JSONObject) item$iv$iv2;
            destination$iv$iv3.add(category(it5));
            $this$map$iv2 = $this$map$iv3;
        }
        final List catOrder = CollectionsKt.distinct(CollectionsKt.plus((Collection) arrayList, destination$iv$iv3));
        search.addTextChangedListener(new TextWatcher() { // from class: com.starpilot.starview.GxBluetoothPage$pickAction$2
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int a, int b, int c) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int a, int b, int c) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s) {
                GxBluetoothPage.pickAction$render($this$pickAction_u24lambda_u2480, search, options, $this$pickAction_u24lambda_u2479, catOrder, expanded, this, key, dialog, i);
                $this$pickAction_u24lambda_u2481.scrollTo(0, 0);
            }
        });
        dialog.element = new AlertDialog.Builder(getAct()).setTitle("Controller Action #" + (i + 1)).setView($this$pickAction_u24lambda_u2474).setNegativeButton("Cancel", (DialogInterface.OnClickListener) null).create();
        pickAction$render($this$pickAction_u24lambda_u2480, search, options, $this$pickAction_u24lambda_u2479, catOrder, expanded, this, key, dialog, i);
        if (dialog.element == 0) {
            Intrinsics.throwUninitializedPropertyAccessException("dialog");
            alertDialog = null;
        } else {
            alertDialog = (AlertDialog) dialog.element;
        }
        alertDialog.show();
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

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static final void pickAction$optionButton$lambda$88(Ref.ObjectRef $dialog, GxBluetoothPage this$0, int $i, String $k, View it) {
        AlertDialog alertDialog;
        if ($dialog.element == 0) {
            Intrinsics.throwUninitializedPropertyAccessException("dialog");
            alertDialog = null;
        } else {
            alertDialog = (AlertDialog) $dialog.element;
        }
        alertDialog.dismiss();
        this$0.onActionSelect($i, $k);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0247  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x02fc  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x024a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void pickAction$render(final LinearLayout results, final EditText search, final List<? extends JSONObject> list, final TextView count, final List<String> list2, final HashSet<String> hashSet, final GxBluetoothPage this$0, final String key, final Ref.ObjectRef<AlertDialog> objectRef, final int $i) {
        char c;
        int i;
        int i2;
        Iterable $this$filter$iv;
        String str;
        Iterable $this$filterTo$iv$iv;
        int i3;
        boolean z;
        GxBluetoothPage gxBluetoothPage = this$0;
        results.removeAllViews();
        String lowerCase = StringsKt.trim((CharSequence) search.getText().toString()).toString().toLowerCase(Locale.ROOT);
        String str2 = "toLowerCase(...)";
        Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
        String q = lowerCase;
        int i4 = 0;
        Iterable $this$filter$iv2 = new Regex("\\s+").split(q, 0);
        Collection destination$iv$iv = new ArrayList();
        Iterator it = $this$filter$iv2.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Object element$iv$iv = it.next();
            String it2 = (String) element$iv$iv;
            if ((it2.length() <= 0 ? (char) 0 : (char) 1) != 0) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        Iterable words = (List) destination$iv$iv;
        List<? extends JSONObject> $this$filter$iv3 = list;
        Collection destination$iv$iv2 = new ArrayList();
        Iterable $this$filterTo$iv$iv2 = $this$filter$iv3;
        for (Object element$iv$iv2 : $this$filterTo$iv$iv2) {
            JSONObject o = (JSONObject) element$iv$iv2;
            String[] strArr = new String[6];
            strArr[i4] = GxPage.str$default(gxBluetoothPage, o, "label", null, 2, null);
            strArr[c] = GxPage.str$default(gxBluetoothPage, o, "key", null, 2, null);
            strArr[2] = GxPage.str$default(gxBluetoothPage, o, "description", null, 2, null);
            strArr[3] = GxPage.str$default(gxBluetoothPage, o, "picker_label", null, 2, null);
            strArr[4] = GxPage.str$default(gxBluetoothPage, o, "picker_description", null, 2, null);
            strArr[5] = gxBluetoothPage.category(o);
            String hay = CollectionsKt.joinToString(CollectionsKt.listOf((Object[]) strArr), " ", "", "", -1, "...", null).toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(hay, str2);
            Iterable $this$all$iv = words;
            if (!($this$all$iv instanceof Collection) || !((Collection) $this$all$iv).isEmpty()) {
                Iterator it3 = $this$all$iv.iterator();
                while (true) {
                    if (it3.hasNext()) {
                        Object element$iv = it3.next();
                        String it4 = (String) element$iv;
                        $this$filter$iv = $this$filter$iv3;
                        str = str2;
                        $this$filterTo$iv$iv = $this$filterTo$iv$iv2;
                        Iterable $this$all$iv2 = $this$all$iv;
                        i3 = 0;
                        if (!StringsKt.contains((CharSequence) hay, (CharSequence) it4, false)) {
                            z = false;
                            break;
                        }
                        i4 = 0;
                        $this$filterTo$iv$iv2 = $this$filterTo$iv$iv;
                        $this$filter$iv3 = $this$filter$iv;
                        str2 = str;
                        $this$all$iv = $this$all$iv2;
                    } else {
                        $this$filter$iv = $this$filter$iv3;
                        str = str2;
                        $this$filterTo$iv$iv = $this$filterTo$iv$iv2;
                        i3 = i4;
                        z = true;
                        break;
                    }
                }
            } else {
                $this$filter$iv = $this$filter$iv3;
                str = str2;
                $this$filterTo$iv$iv = $this$filterTo$iv$iv2;
                i3 = i4;
                z = true;
            }
            if (z) {
                destination$iv$iv2.add(element$iv$iv2);
            }
            i4 = i3;
            $this$filterTo$iv$iv2 = $this$filterTo$iv$iv;
            $this$filter$iv3 = $this$filter$iv;
            str2 = str;
            c = 1;
        }
        int i5 = 6;
        int i6 = i4;
        List matches = (List) destination$iv$iv2;
        int i7 = 1;
        count.setText(matches.size() + " " + (matches.size() == 1 ? "action" : "actions"));
        int i8 = i6;
        results.addView(pickAction$optionButton(this$0, key, objectRef, $i, null, "Clear this assignment"));
        for (final String cat : list2) {
            List $this$filter$iv4 = matches;
            Collection destination$iv$iv3 = new ArrayList();
            for (Object element$iv$iv3 : $this$filter$iv4) {
                JSONObject it5 = (JSONObject) element$iv$iv3;
                if (Intrinsics.areEqual(gxBluetoothPage.category(it5), cat)) {
                    destination$iv$iv3.add(element$iv$iv3);
                }
            }
            List<JSONObject> items = (List) destination$iv$iv3;
            if (items.isEmpty()) {
                gxBluetoothPage = this$0;
                q = q;
            } else {
                if ((q.length() > 0 ? i7 : i8) == 0 && !hashSet.contains(cat)) {
                    i = i8;
                    i2 = i;
                    TextView $this$pickAction_u24render_u24lambda_u2495 = new TextView(this$0.getAct());
                    $this$pickAction_u24render_u24lambda_u2495.setText((i2 == 0 ? "▾" : "▸") + "  " + cat + " (" + items.size() + ")");
                    $this$pickAction_u24render_u24lambda_u2495.setTextColor(CarUi.Companion.getTEXT());
                    $this$pickAction_u24render_u24lambda_u2495.setTextSize(18.0f);
                    $this$pickAction_u24render_u24lambda_u2495.setTypeface(Typeface.DEFAULT_BOLD);
                    $this$pickAction_u24render_u24lambda_u2495.setMinHeight(this$0.getUi().dp(56));
                    $this$pickAction_u24render_u24lambda_u2495.setGravity(16);
                    $this$pickAction_u24render_u24lambda_u2495.setPadding(this$0.getUi().dp(i5), this$0.getUi().dp(8), this$0.getUi().dp(i5), this$0.getUi().dp(8));
                    final String str3 = q;
                    String q2 = q;
                    int i9 = i5;
                    int i10 = i7;
                    $this$pickAction_u24render_u24lambda_u2495.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.GxBluetoothPage$$ExternalSyntheticLambda3
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            GxBluetoothPage.pickAction$render$lambda$95$lambda$94(str3, hashSet, cat, results, search, list, count, list2, this$0, key, objectRef, $i, view);
                        }
                    });
                    results.addView($this$pickAction_u24render_u24lambda_u2495);
                    if (i2 != 0) {
                        for (JSONObject o2 : items) {
                            String[] strArr2 = new String[2];
                            strArr2[0] = cat;
                            strArr2[i10] = GxPage.str$default(this$0, o2, "description", null, 2, null);
                            Iterable $this$filter$iv5 = CollectionsKt.listOf((Object[]) strArr2);
                            Collection destination$iv$iv4 = new ArrayList();
                            for (Object element$iv$iv4 : $this$filter$iv5) {
                                String it6 = (String) element$iv$iv4;
                                if (!StringsKt.isBlank(it6)) {
                                    destination$iv$iv4.add(element$iv$iv4);
                                }
                            }
                            results.addView(pickAction$optionButton(this$0, key, objectRef, $i, o2, CollectionsKt.joinToString((List) destination$iv$iv4, " · ", "", "", -1, "...", null)));
                        }
                    }
                    gxBluetoothPage = this$0;
                    i8 = 0;
                    i5 = i9;
                    q = q2;
                    i7 = i10;
                }
                i = i7;
                i2 = i;
                TextView $this$pickAction_u24render_u24lambda_u24952 = new TextView(this$0.getAct());
                $this$pickAction_u24render_u24lambda_u24952.setText((i2 == 0 ? "▾" : "▸") + "  " + cat + " (" + items.size() + ")");
                $this$pickAction_u24render_u24lambda_u24952.setTextColor(CarUi.Companion.getTEXT());
                $this$pickAction_u24render_u24lambda_u24952.setTextSize(18.0f);
                $this$pickAction_u24render_u24lambda_u24952.setTypeface(Typeface.DEFAULT_BOLD);
                $this$pickAction_u24render_u24lambda_u24952.setMinHeight(this$0.getUi().dp(56));
                $this$pickAction_u24render_u24lambda_u24952.setGravity(16);
                $this$pickAction_u24render_u24lambda_u24952.setPadding(this$0.getUi().dp(i5), this$0.getUi().dp(8), this$0.getUi().dp(i5), this$0.getUi().dp(8));
                final String str32 = q;
                String q22 = q;
                int i92 = i5;
                int i102 = i7;
                $this$pickAction_u24render_u24lambda_u24952.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.GxBluetoothPage$$ExternalSyntheticLambda3
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        GxBluetoothPage.pickAction$render$lambda$95$lambda$94(str32, hashSet, cat, results, search, list, count, list2, this$0, key, objectRef, $i, view);
                    }
                });
                results.addView($this$pickAction_u24render_u24lambda_u24952);
                if (i2 != 0) {
                }
                gxBluetoothPage = this$0;
                i8 = 0;
                i5 = i92;
                q = q22;
                i7 = i102;
            }
        }
        int i11 = i8;
        if (!matches.isEmpty()) {
            return;
        }
        TextView $this$pickAction_u24render_u24lambda_u2497 = new TextView(this$0.getAct());
        $this$pickAction_u24render_u24lambda_u2497.setText("No matching actions. Try another search or category.");
        $this$pickAction_u24render_u24lambda_u2497.setTextColor(CarUi.Companion.getDIM());
        $this$pickAction_u24render_u24lambda_u2497.setTextSize(16.0f);
        $this$pickAction_u24render_u24lambda_u2497.setPadding(i11, this$0.getUi().dp(10), i11, this$0.getUi().dp(10));
        results.addView($this$pickAction_u24render_u24lambda_u2497);
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
