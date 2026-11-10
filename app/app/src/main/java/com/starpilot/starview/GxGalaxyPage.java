package com.starpilot.starview;

import android.app.Activity;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.starpilot.starview.GxPage;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.json.JSONObject;

/* compiled from: GxVehicle.kt */
public final class GxGalaxyPage extends GxPage {
    private String gUrl;
    private boolean loading;
    private LinearLayout pairBox;
    private boolean paired;
    private boolean submitting;
    private String tsAuthUrl;
    private LinearLayout tsBox;
    private boolean tsBusy;
    private boolean tsInstalled;
    private boolean tsLoaded;

    /* VF: from Vineflower (jadx: ) */
    public GxGalaxyPage(Activity var1, GxHost var2) {
        super(var1, var2);
        Intrinsics.checkNotNullParameter(var1, "act");
        Intrinsics.checkNotNullParameter(var2, "gx");
        this.loading = true;
        this.gUrl = "";
        this.tsAuthUrl = "";
    }

    @Override // com.starpilot.starview.GxPage
    public void build() {
        GxPage.header$default(this, "Galaxy Pairing", null, 2, null);
        this.pairBox = GxPage.box$default(this, null, 1, null);
        GxPage.header$default(this, "Tailscale", null, 2, null);
        GxPage.note$default(this, "Tailscale creates a secure, private connection between your openpilot device and your phone or PC so you can access and control it from anywhere!", null, 2, null);
        this.tsBox = GxPage.box$default(this, null, 1, null);
        paintPair();
        paintTs();
    }

    @Override // com.starpilot.starview.GxPage
    public void onShow() {
        loadPair();
        loadTs();
    }

    private final void open(String url) {
        boolean gxVehicleOpenUrl;
        gxVehicleOpenUrl = GxVehicleKt.gxVehicleOpenUrl(getAct(), url);
        if (gxVehicleOpenUrl) {
            return;
        }
        toast("No browser on this tablet can open the link.");
    }

    private final void loadPair() {
        GxPage.call$default(this, "GET", "/api/galaxy/status", null, 0L, new Function3() { // from class: com.starpilot.starview.GxGalaxyPage$$ExternalSyntheticLambda11
            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                Unit loadPair$lambda$2;
                loadPair$lambda$2 = GxGalaxyPage.loadPair$lambda$2(GxGalaxyPage.this, ((Number) obj).intValue(), (String) obj2, (String) obj3);
                return loadPair$lambda$2;
            }
        }, 12, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit loadPair$lambda$2(GxGalaxyPage this$0, int code, String text, String str) {
        Object m1191constructorimpl;
        Intrinsics.checkNotNullParameter(text, "text");
        if (code < 0) {
            this$0.toast("Failed to check pairing status.");
        } else {
            if (200 <= code && code < 300) {
                try {
                    Result.Companion companion = Result.Companion;
                    m1191constructorimpl = KtCompat.resultOf(new JSONObject(text));
                } catch (Throwable th) {
                    Result.Companion companion2 = Result.Companion;
                    m1191constructorimpl = KtCompat.resultOf(ResultKt.createFailure(th));
                }
                if (KtCompat.isFailure(m1191constructorimpl)) {
                    m1191constructorimpl = null;
                }
                JSONObject it = (JSONObject) m1191constructorimpl;
                if (it != null) {
                    this$0.paired = it.optBoolean("paired");
                    this$0.gUrl = GxPage.str$default(this$0, it, "url", null, 2, null);
                }
            }
        }
        this$0.loading = false;
        this$0.paintPair();
        return Unit.INSTANCE;
    }

    private final void paintPair() {
        LinearLayout linearLayout = this.pairBox;
        LinearLayout linearLayout2 = null;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pairBox");
            linearLayout = null;
        }
        linearLayout.removeAllViews();
        if (this.loading) {
            LinearLayout linearLayout3 = this.pairBox;
            if (linearLayout3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("pairBox");
            } else {
                linearLayout2 = linearLayout3;
            }
            linearLayout2.addView(empty("Checking pairing status…"));
            return;
        }
        if (this.paired) {
            GxGalaxyPage gxGalaxyPage = this;
            LinearLayout linearLayout4 = this.pairBox;
            if (linearLayout4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("pairBox");
                linearLayout4 = null;
            }
            GxPage.Row r = GxPage.row$default(gxGalaxyPage, "Paired", "Your device is paired with Galaxy. Access it remotely at:", linearLayout4, 0, 8, null);
            r.getTitleView().setTextColor(CarUi.Companion.getACCENT());
            TextView b = GxPage.Row.button$default(r, this.submitting ? "Unpairing…" : "Unpair", false, new Function0() { // from class: com.starpilot.starview.GxGalaxyPage$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit paintPair$lambda$3;
                    paintPair$lambda$3 = GxGalaxyPage.paintPair$lambda$3(GxGalaxyPage.this);
                    return paintPair$lambda$3;
                }
            }, 2, null);
            GxVehicleKt.gxVehicleEnable(b, !this.submitting);
            if (!StringsKt.isBlank(this.gUrl)) {
                LinearLayout linearLayout5 = this.pairBox;
                if (linearLayout5 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("pairBox");
                } else {
                    linearLayout2 = linearLayout5;
                }
                LinearLayout c = card(linearLayout2);
                text(this.gUrl, c, 20.0f, CarUi.Companion.getACCENT(), true).setTextIsSelectable(true);
                GxPage.buttons$default(this, new Pair[]{TuplesKt.to("Open Link", new Function0() { // from class: com.starpilot.starview.GxGalaxyPage$$ExternalSyntheticLambda7
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        Unit paintPair$lambda$4;
                        paintPair$lambda$4 = GxGalaxyPage.paintPair$lambda$4(GxGalaxyPage.this);
                        return paintPair$lambda$4;
                    }
                })}, c, false, 4, null);
                return;
            }
            return;
        }
        GxGalaxyPage gxGalaxyPage2 = this;
        LinearLayout linearLayout6 = this.pairBox;
        if (linearLayout6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pairBox");
        } else {
            linearLayout2 = linearLayout6;
        }
        TextView b2 = GxPage.row$default(gxGalaxyPage2, "Not Paired", "Pair your device with Galaxy to access The Galaxy remotely from anywhere. Set a password to secure your connection.", linearLayout2, 0, 8, null).button(this.submitting ? "Pairing…" : "Pair", true, new Function0() { // from class: com.starpilot.starview.GxGalaxyPage$$ExternalSyntheticLambda8
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit paintPair$lambda$5;
                paintPair$lambda$5 = GxGalaxyPage.paintPair$lambda$5(GxGalaxyPage.this);
                return paintPair$lambda$5;
            }
        });
        GxVehicleKt.gxVehicleEnable(b2, !this.submitting);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paintPair$lambda$3(GxGalaxyPage this$0) {
        this$0.unpair();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paintPair$lambda$4(GxGalaxyPage this$0) {
        this$0.open(this$0.gUrl);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paintPair$lambda$5(GxGalaxyPage this$0) {
        this$0.askPassword();
        return Unit.INSTANCE;
    }

    private final void askPassword() {
        if (this.submitting) {
            return;
        }
        prompt("Pair with Galaxy", "Password (min 6 characters)", "", 129, "Set a password to secure your connection.", new Function1() { // from class: com.starpilot.starview.GxGalaxyPage$$ExternalSyntheticLambda9
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit askPassword$lambda$6;
                askPassword$lambda$6 = GxGalaxyPage.askPassword$lambda$6(GxGalaxyPage.this, (String) obj);
                return askPassword$lambda$6;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit askPassword$lambda$6(GxGalaxyPage this$0, String pw) {
        Intrinsics.checkNotNullParameter(pw, "pw");
        this$0.pair(pw);
        return Unit.INSTANCE;
    }

    private final void pair(String password) {
        String pw = StringsKt.trim((CharSequence) password).toString();
        if (pw.length() < 6) {
            toast("Password must be at least 6 characters.");
            return;
        }
        this.submitting = true;
        paintPair();
        GxPage.send$default(this, "POST", "/api/galaxy/pair", new JSONObject().put("password", pw), 0L, new Function2() { // from class: com.starpilot.starview.GxGalaxyPage$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit pair$lambda$9;
                pair$lambda$9 = GxGalaxyPage.pair$lambda$9(GxGalaxyPage.this, (String) obj, (JSONObject) obj2);
                return pair$lambda$9;
            }
        }, 8, null);
    }

    /* VF: from Vineflower (jadx: Code restructure failed;incorrect) */
    public static final Unit pair$lambda$9(GxGalaxyPage var0, String var1, JSONObject var2) {
        var0.submitting = false;
        if (var1 != null) {
            CharSequence var8 = var1;
            Object var3 = var8;
            if (StringsKt.isBlank(var8)) {
                var3 = "Network error — is the device reachable?";
            }

            var0.toast((String)var3);
        } else {
            label34: {
                var0.paired = true;
                if (var2 != null) {
                    var1 = GxPage.str$default(var0, var2, "url", null, 2, null);
                    if (var1 != null) {
                        break label34;
                    }
                }

                var1 = "";
            }

            label29: {
                var0.gUrl = var1;
                if (var2 != null) {
                    var1 = GxPage.str$default(var0, var2, "message", null, 2, null);
                    if (var1 != null) {
                        CharSequence var9 = var1;
                        CharSequence var6 = var9;
                        if (StringsKt.isBlank(var9)) {
                            var6 = null;
                        }

                        var1 = (String)var6;
                        if (var1 != null) {
                            break label29;
                        }
                    }
                }

                var1 = "Paired!";
            }

            var0.toast(var1);
        }

        var0.paintPair();
        return Unit.INSTANCE;
    }

    private final void unpair() {
        if (this.submitting) {
            return;
        }
        confirm("Confirm Unpair", "Are you sure you want to unpair from Galaxy? You will lose remote access until you pair again.", "Unpair", new Function0() { // from class: com.starpilot.starview.GxGalaxyPage$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit unpair$lambda$13;
                unpair$lambda$13 = GxGalaxyPage.unpair$lambda$13(GxGalaxyPage.this);
                return unpair$lambda$13;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit unpair$lambda$13(final GxGalaxyPage this$0) {
        this$0.submitting = true;
        this$0.paintPair();
        GxPage.send$default(this$0, "POST", "/api/galaxy/unpair", null, 0L, new Function2() { // from class: com.starpilot.starview.GxGalaxyPage$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit unpair$lambda$13$lambda$12;
                unpair$lambda$13$lambda$12 = GxGalaxyPage.unpair$lambda$13$lambda$12(this$0, (String) obj, (JSONObject) obj2);
                return unpair$lambda$13$lambda$12;
            }
        }, 12, null);
        return Unit.INSTANCE;
    }

    /* VF: from Vineflower (jadx: Code restructure failed;incorrect) */
    public static final Unit unpair$lambda$13$lambda$12(GxGalaxyPage var0, String var1, JSONObject var2) {
        var0.submitting = false;
        if (var1 != null) {
            CharSequence var7 = var1;
            Object var3 = var7;
            if (StringsKt.isBlank(var7)) {
                var3 = "Network error — is the device reachable?";
            }

            var0.toast((String)var3);
        } else {
            label25: {
                var0.paired = false;
                var0.gUrl = "";
                if (var2 != null) {
                    var1 = GxPage.str$default(var0, var2, "message", null, 2, null);
                    if (var1 != null) {
                        CharSequence var8 = var1;
                        CharSequence var5 = var8;
                        if (StringsKt.isBlank(var8)) {
                            var5 = null;
                        }

                        var1 = (String)var5;
                        if (var1 != null) {
                            break label25;
                        }
                    }
                }

                var1 = "Unpaired!";
            }

            var0.toast(var1);
        }

        var0.paintPair();
        return Unit.INSTANCE;
    }

    private final void loadTs() {
        getJson("/api/tailscale/installed", new Function2() { // from class: com.starpilot.starview.GxGalaxyPage$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit loadTs$lambda$14;
                loadTs$lambda$14 = GxGalaxyPage.loadTs$lambda$14(GxGalaxyPage.this, (String) obj, (JSONObject) obj2);
                return loadTs$lambda$14;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit loadTs$lambda$14(GxGalaxyPage this$0, String e, JSONObject o) {
        boolean z = false;
        if (e == null) {
            if (o != null && o.optBoolean("installed")) {
                z = true;
            }
        }
        this$0.tsInstalled = z;
        this$0.tsLoaded = true;
        this$0.paintTs();
        return Unit.INSTANCE;
    }

    private final void paintTs() {
        TextView button$default;
        LinearLayout linearLayout = this.tsBox;
        LinearLayout linearLayout2 = null;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tsBox");
            linearLayout = null;
        }
        linearLayout.removeAllViews();
        if (!this.tsLoaded) {
            LinearLayout linearLayout3 = this.tsBox;
            if (linearLayout3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tsBox");
            } else {
                linearLayout2 = linearLayout3;
            }
            note("Checking Tailscale install status...", linearLayout2);
            return;
        }
        GxGalaxyPage gxGalaxyPage = this;
        int warn = CarUi.Companion.getWARN();
        LinearLayout linearLayout4 = this.tsBox;
        if (linearLayout4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tsBox");
            linearLayout4 = null;
        }
        GxVehicleKt.gxVehicleNotice(gxGalaxyPage, "Not recommended. Using Galaxy Tunnel is the preferred remote connection method.", warn, linearLayout4);
        GxGalaxyPage gxGalaxyPage2 = this;
        String str = this.tsInstalled ? "Installed on this device." : "Not installed.";
        LinearLayout linearLayout5 = this.tsBox;
        if (linearLayout5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tsBox");
            linearLayout5 = null;
        }
        GxPage.Row r = GxPage.row$default(gxGalaxyPage2, "Tailscale", str, linearLayout5, 0, 8, null);
        if (!this.tsInstalled) {
            button$default = r.button(this.tsBusy ? "Installing..." : "Install Tailscale", true, new Function0() { // from class: com.starpilot.starview.GxGalaxyPage$$ExternalSyntheticLambda13
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit paintTs$lambda$15;
                    paintTs$lambda$15 = GxGalaxyPage.paintTs$lambda$15(GxGalaxyPage.this);
                    return paintTs$lambda$15;
                }
            });
        } else {
            button$default = GxPage.Row.button$default(r, this.tsBusy ? "Uninstalling..." : "Uninstall Tailscale", false, new Function0() { // from class: com.starpilot.starview.GxGalaxyPage$$ExternalSyntheticLambda14
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit paintTs$lambda$16;
                    paintTs$lambda$16 = GxGalaxyPage.paintTs$lambda$16(GxGalaxyPage.this);
                    return paintTs$lambda$16;
                }
            }, 2, null);
        }
        TextView b = button$default;
        GxVehicleKt.gxVehicleEnable(b, !this.tsBusy);
        if (!StringsKt.isBlank(this.tsAuthUrl)) {
            LinearLayout linearLayout6 = this.tsBox;
            if (linearLayout6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tsBox");
                linearLayout6 = null;
            }
            LinearLayout c = card(linearLayout6);
            GxPage.text$default(this, "Sign in to Tailscale to authenticate this device:", c, 17.0f, CarUi.Companion.getTEXT(), false, 16, null);
            text(this.tsAuthUrl, c, 18.0f, CarUi.Companion.getACCENT(), true).setTextIsSelectable(true);
            GxPage.buttons$default(this, new Pair[]{TuplesKt.to("Open Login Page", new Function0() { // from class: com.starpilot.starview.GxGalaxyPage$$ExternalSyntheticLambda15
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit paintTs$lambda$17;
                    paintTs$lambda$17 = GxGalaxyPage.paintTs$lambda$17(GxGalaxyPage.this);
                    return paintTs$lambda$17;
                }
            })}, c, false, 4, null);
        }
        GxGalaxyPage gxGalaxyPage3 = this;
        LinearLayout linearLayout7 = this.tsBox;
        if (linearLayout7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tsBox");
            linearLayout7 = null;
        }
        GxPage.Row.button$default(GxPage.row$default(gxGalaxyPage3, "Download for your other devices", "tailscale.com/download", linearLayout7, 0, 8, null), "Open", false, new Function0() { // from class: com.starpilot.starview.GxGalaxyPage$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit paintTs$lambda$18;
                paintTs$lambda$18 = GxGalaxyPage.paintTs$lambda$18(GxGalaxyPage.this);
                return paintTs$lambda$18;
            }
        }, 2, null);
        LinearLayout linearLayout8 = this.tsBox;
        if (linearLayout8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tsBox");
        } else {
            linearLayout2 = linearLayout8;
        }
        note("Installing opens the Tailscale login page to authenticate this device.", linearLayout2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paintTs$lambda$15(GxGalaxyPage this$0) {
        this$0.install();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paintTs$lambda$16(GxGalaxyPage this$0) {
        this$0.uninstall();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paintTs$lambda$17(GxGalaxyPage this$0) {
        this$0.open(this$0.tsAuthUrl);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paintTs$lambda$18(GxGalaxyPage this$0) {
        this$0.open("https://tailscale.com/download");
        return Unit.INSTANCE;
    }

    private final void install() {
        if (this.tsBusy) {
            return;
        }
        this.tsBusy = true;
        paintTs();
        toast("Install started...");
        send("POST", "/api/tailscale/setup", null, 300L, new Function2() { // from class: com.starpilot.starview.GxGalaxyPage$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit install$lambda$21;
                install$lambda$21 = GxGalaxyPage.install$lambda$21(GxGalaxyPage.this, (String) obj, (JSONObject) obj2);
                return install$lambda$21;
            }
        });
    }

    /* VF: from Vineflower (jadx: Code restructure failed;incorrect) */
    public static final Unit install$lambda$21(GxGalaxyPage var0, String var1, JSONObject var2) {
        var0.tsBusy = false;
        if (var1 != null) {
            CharSequence var9 = var1;
            Object var4 = var9;
            if (StringsKt.isBlank(var9)) {
                var4 = "Failed to install Tailscale.";
            }

            var0.toast((String)var4);
        } else {
            label38: {
                if (var2 != null) {
                    var1 = GxPage.str$default(var0, var2, "message", null, 2, null);
                    if (var1 != null) {
                        CharSequence var3 = var1;
                        CharSequence var6 = var3;
                        if (StringsKt.isBlank(var3)) {
                            var6 = null;
                        }

                        var1 = (String)var6;
                        if (var1 != null) {
                            break label38;
                        }
                    }
                }

                var1 = "Tailscale setup started.";
            }

            label31: {
                var0.toast(var1);
                if (var2 != null) {
                    var1 = GxPage.str$default(var0, var2, "auth_url", null, 2, null);
                    if (var1 != null) {
                        break label31;
                    }
                }

                var1 = "";
            }

            if (!StringsKt.isBlank(var1) && !Intrinsics.areEqual(var1, "null")) {
                var0.tsAuthUrl = var1;
                var0.open(var1);
            }
        }

        var0.paintTs();
        var0.loadTs();
        return Unit.INSTANCE;
    }

    private final void uninstall() {
        if (this.tsBusy) {
            return;
        }
        confirm("Uninstall Tailscale?", "This disconnects the device from your tailnet and removes the Tailscale binaries and state.", "Uninstall", new Function0() { // from class: com.starpilot.starview.GxGalaxyPage$$ExternalSyntheticLambda12
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit uninstall$lambda$25;
                uninstall$lambda$25 = GxGalaxyPage.uninstall$lambda$25(GxGalaxyPage.this);
                return uninstall$lambda$25;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit uninstall$lambda$25(final GxGalaxyPage this$0) {
        this$0.tsBusy = true;
        this$0.paintTs();
        this$0.toast("Uninstall started...");
        this$0.send("POST", "/api/tailscale/uninstall", null, 180L, new Function2() { // from class: com.starpilot.starview.GxGalaxyPage$$ExternalSyntheticLambda10
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit uninstall$lambda$25$lambda$24;
                uninstall$lambda$25$lambda$24 = GxGalaxyPage.uninstall$lambda$25$lambda$24(this$0, (String) obj, (JSONObject) obj2);
                return uninstall$lambda$25$lambda$24;
            }
        });
        return Unit.INSTANCE;
    }

    /* VF: from Vineflower (jadx: Code restructure failed;incorrect) */
    public static final Unit uninstall$lambda$25$lambda$24(GxGalaxyPage var0, String var1, JSONObject var2) {
        var0.tsBusy = false;
        if (var1 != null) {
            CharSequence var7 = var1;
            Object var3 = var7;
            if (StringsKt.isBlank(var7)) {
                var3 = "Failed to uninstall Tailscale.";
            }

            var0.toast((String)var3);
        } else {
            label25: {
                var0.tsAuthUrl = "";
                if (var2 != null) {
                    var1 = GxPage.str$default(var0, var2, "message", null, 2, null);
                    if (var1 != null) {
                        CharSequence var8 = var1;
                        CharSequence var5 = var8;
                        if (StringsKt.isBlank(var8)) {
                            var5 = null;
                        }

                        var1 = (String)var5;
                        if (var1 != null) {
                            break label25;
                        }
                    }
                }

                var1 = "Tailscale uninstalled.";
            }

            var0.toast(var1);
        }

        var0.paintTs();
        var0.loadTs();
        return Unit.INSTANCE;
    }
}
