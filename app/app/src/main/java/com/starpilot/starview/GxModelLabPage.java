package com.starpilot.starview;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.starpilot.starview.GxPage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.json.JSONObject;

/* compiled from: GxModels.kt */
public final class GxModelLabPage extends GxPage {
    private LinearLayout availBox;
    private boolean cfgEnabled;
    private boolean chestnutReady;
    private boolean dirty;
    private JSONObject download;
    private String error;
    private int gen;
    private LinearLayout headBox;
    private boolean isOnroad;
    private String lastSig;
    private String lateral;
    private boolean loading;
    private String longitudinal;
    private String message;
    private List<? extends JSONObject> models;
    private final boolean needsOffroad;
    private LinearLayout pairBox;
    private boolean polling;
    private JSONObject runtime;
    private LinearLayout runtimeBox;
    private boolean saving;
    private JSONObject summary;

    /* VF: from Vineflower (jadx: ) */
    public GxModelLabPage(Activity var1, GxHost var2) {
        super(var1, var2);
        Intrinsics.checkNotNullParameter(var1, "act");
        Intrinsics.checkNotNullParameter(var2, "gx");
        this.needsOffroad = true;
        this.loading = true;
        this.error = "";
        this.message = "";
        this.lateral = "";
        this.longitudinal = "";
        this.runtime = new JSONObject();
        this.download = new JSONObject();
        this.summary = new JSONObject();
        this.models = CollectionsKt.emptyList();
        this.lastSig = "";
    }

    @Override // com.starpilot.starview.GxPage
    public boolean getNeedsOffroad() {
        return this.needsOffroad;
    }

    @Override // com.starpilot.starview.GxPage
    public void build() {
        this.headBox = GxPage.box$default(this, null, 1, null);
        GxPage.header$default(this, "Available models", null, 2, null);
        this.availBox = GxPage.box$default(this, null, 1, null);
        GxPage.header$default(this, "Compose a pair", null, 2, null);
        this.pairBox = GxPage.box$default(this, null, 1, null);
        GxPage.header$default(this, "Runtime", null, 2, null);
        this.runtimeBox = GxPage.box$default(this, null, 1, null);
        render(true);
        every(2000L, new Function0() { // from class: com.starpilot.starview.GxModelLabPage$$ExternalSyntheticLambda11
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit build$lambda$1;
                build$lambda$1 = GxModelLabPage.build$lambda$1(GxModelLabPage.this);
                return build$lambda$1;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit build$lambda$1(final GxModelLabPage this$0) {
        if (!this$0.polling) {
            this$0.polling = true;
            this$0.refresh(new Function0() { // from class: com.starpilot.starview.GxModelLabPage$$ExternalSyntheticLambda8
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit build$lambda$1$lambda$0;
                    build$lambda$1$lambda$0 = GxModelLabPage.build$lambda$1$lambda$0(this$0);
                    return build$lambda$1$lambda$0;
                }
            });
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit build$lambda$1$lambda$0(GxModelLabPage this$0) {
        this$0.polling = false;
        return Unit.INSTANCE;
    }

    @Override // com.starpilot.starview.GxPage
    public void onHide() {
        this.gen++;
        this.polling = false;
    }

    private final List<JSONObject> available() {
        Iterable $this$filter$iv = this.models;
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            JSONObject it = (JSONObject) element$iv$iv;
            if (it.optBoolean("modelLabArtifactAvailable")) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        return (List) destination$iv$iv;
    }

    private final List<JSONObject> ready() {
        Iterable $this$filter$iv = available();
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            JSONObject it = (JSONObject) element$iv$iv;
            if (it.optBoolean("modelLabArtifactInstalled")) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        return (List) destination$iv$iv;
    }

    private final List<JSONObject> candidates() {
        Iterable $this$filter$iv = ready();
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            JSONObject it = (JSONObject) element$iv$iv;
            boolean z = true;
            if (!(this.lateral.length() == 0) && Intrinsics.areEqual(GxModelsKt.gxModelsText$default(it, "value", null, 4, null), this.lateral)) {
                z = false;
            }
            if (z) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        return (List) destination$iv$iv;
    }

    private final JSONObject byId(String id) {
        Object obj;
        Iterable $this$firstOrNull$iv = this.models;
        Iterator it = $this$firstOrNull$iv.iterator();
        while (true) {
            obj = null;
            if (!it.hasNext()) {
                break;
            }
            Object element$iv = it.next();
            JSONObject it2 = (JSONObject) element$iv;
            if (Intrinsics.areEqual(GxModelsKt.gxModelsText$default(it2, "value", null, 4, null), id)) {
                obj = element$iv;
                break;
            }
        }
        return (JSONObject) obj;
    }

    private final String modelLabel(String id) {
        JSONObject it = byId(id);
        if (it != null) {
            String str = null;
            String gxModelsText$default = GxModelsKt.gxModelsText$default(it, "label", null, 4, null);
            if (!StringsKt.isBlank(gxModelsText$default)) {
                str = gxModelsText$default;
            }
            String str2 = str;
            if (str2 != null) {
                return str2;
            }
        }
        String str3 = id;
        if (StringsKt.isBlank(str3)) {
            str3 = "not selected";
        }
        return str3;
    }

    private final String selectionError() {
        if (this.isOnroad) {
            return "Park before changing the laboratory pair.";
        }
        if (ready().size() < 2) {
            return "Download at least two eGPU variants before composing a pair.";
        }
        JSONObject lat = byId(this.lateral);
        JSONObject lon = byId(this.longitudinal);
        if (lat == null || lon == null) {
            return "Choose two downloaded eGPU variants.";
        }
        if (Intrinsics.areEqual(GxModelsKt.gxModelsText$default(lat, "value", null, 4, null), GxModelsKt.gxModelsText$default(lon, "value", null, 4, null))) {
            return "Lateral and longitudinal models must be different.";
        }
        if (lat.optBoolean("modelLabArtifactInstalled") && lon.optBoolean("modelLabArtifactInstalled")) {
            return !this.chestnutReady ? "Connect a firmware-ready Chestnut to enable this pair." : "";
        }
        return "Download both eGPU variants first.";
    }

    private final String runtimeState() {
        return this.runtime.optBoolean("active") ? "Pair active" : this.runtime.optBoolean("requested") ? "Pair requested" : "Inactive";
    }

    /* VF: from Vineflower (jadx: ) */
    static void refresh$default(GxModelLabPage var0, Function0 var1, int var2, Object var3) {
        if ((var2 & 1) != 0) {
            var1 = null;
        }

        var0.refresh(var1);
    }

    private final void refresh(final Function0<Unit> then) {
        this.gen++;
        final int g = this.gen;
        getJson("/api/model-laboratory", new Function2() { // from class: com.starpilot.starview.GxModelLabPage$$ExternalSyntheticLambda9
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit refresh$lambda$9;
                refresh$lambda$9 = GxModelLabPage.refresh$lambda$9(GxModelLabPage.this, g, then, (String) obj, (JSONObject) obj2);
                return refresh$lambda$9;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit refresh$lambda$9(GxModelLabPage this$0, int $g, Function0 $then, String e, JSONObject p) {
        if (!this$0.getShown() || $g != this$0.gen) {
            if ($then != null) {
                $then.invoke();
            }
            return Unit.INSTANCE;
        }
        if (e != null || p == null) {
            this$0.error = e == null ? "bad reply" : e;
        } else {
            this$0.applyPayload(p);
        }
        this$0.loading = false;
        render$default(this$0, false, 1, null);
        if ($then != null) {
            $then.invoke();
        }
        return Unit.INSTANCE;
    }

    private final void applyPayload(JSONObject p) {
        List<? extends JSONObject> gxModelsObjects;
        this.chestnutReady = p.optBoolean("chestnutReady");
        this.isOnroad = p.optBoolean("isOnroad");
        this.error = GxModelsKt.gxModelsText$default(p, "configurationError", null, 4, null);
        JSONObject optJSONObject = p.optJSONObject("runtime");
        if (optJSONObject == null) {
            optJSONObject = new JSONObject();
        }
        this.runtime = optJSONObject;
        JSONObject optJSONObject2 = p.optJSONObject("download");
        if (optJSONObject2 == null) {
            optJSONObject2 = new JSONObject();
        }
        this.download = optJSONObject2;
        JSONObject optJSONObject3 = p.optJSONObject("summary");
        if (optJSONObject3 == null) {
            optJSONObject3 = new JSONObject();
        }
        this.summary = optJSONObject3;
        gxModelsObjects = GxModelsKt.gxModelsObjects(p.optJSONArray("models"));
        this.models = gxModelsObjects;
        JSONObject cfg = p.optJSONObject("configuration");
        if (cfg == null) {
            cfg = new JSONObject();
        }
        this.cfgEnabled = cfg.optBoolean("enabled");
        if (!this.dirty) {
            this.lateral = GxModelsKt.gxModelsText$default(cfg, "lateralModel", null, 4, null);
            this.longitudinal = GxModelsKt.gxModelsText$default(cfg, "longitudinalModel", null, 4, null);
        }
        normalizeSelection();
    }

    private final void normalizeSelection() {
        Iterable $this$none$iv;
        Object element$iv;
        String gxModelsText$default;
        String str;
        List r = ready();
        List $this$none$iv2 = r;
        boolean z = false;
        if (!($this$none$iv2 instanceof Collection) || !$this$none$iv2.isEmpty()) {
            Iterator it = $this$none$iv2.iterator();
            while (true) {
                if (!it.hasNext()) {
                    $this$none$iv = 1;
                    break;
                }
                Object element$iv2 = it.next();
                JSONObject it2 = (JSONObject) element$iv2;
                if (Intrinsics.areEqual(GxModelsKt.gxModelsText$default(it2, "value", null, 4, null), this.lateral)) {
                    $this$none$iv = null;
                    break;
                }
            }
        } else {
            $this$none$iv = 1;
        }
        String str2 = "";
        if ($this$none$iv != null) {
            JSONObject it3 = (JSONObject) CollectionsKt.firstOrNull(r);
            if (it3 == null || (str = GxModelsKt.gxModelsText$default(it3, "value", null, 4, null)) == null) {
                str = "";
            }
            this.lateral = str;
        }
        List $this$none$iv3 = r;
        if (!($this$none$iv3 instanceof Collection) || !$this$none$iv3.isEmpty()) {
            Iterator it4 = $this$none$iv3.iterator();
            while (true) {
                if (!it4.hasNext()) {
                    z = true;
                    break;
                }
                Object element$iv3 = it4.next();
                JSONObject it5 = (JSONObject) element$iv3;
                if (Intrinsics.areEqual(GxModelsKt.gxModelsText$default(it5, "value", null, 4, null), this.longitudinal)) {
                    break;
                }
            }
        } else {
            z = true;
        }
        if (z) {
            List $this$firstOrNull$iv = r;
            Iterator it6 = $this$firstOrNull$iv.iterator();
            while (true) {
                if (!it6.hasNext()) {
                    element$iv = null;
                    break;
                }
                element$iv = it6.next();
                JSONObject it7 = (JSONObject) element$iv;
                if (!Intrinsics.areEqual(GxModelsKt.gxModelsText$default(it7, "value", null, 4, null), this.lateral)) {
                    break;
                }
            }
            JSONObject it8 = (JSONObject) element$iv;
            if (it8 != null && (gxModelsText$default = GxModelsKt.gxModelsText$default(it8, "value", null, 4, null)) != null) {
                str2 = gxModelsText$default;
            }
            this.longitudinal = str2;
        }
    }

    private final void onLateralChange() {
        String str;
        this.dirty = true;
        JSONObject lon = byId(this.longitudinal);
        JSONObject lat = byId(this.lateral);
        if (lon == null || lat == null || !Intrinsics.areEqual(GxModelsKt.gxModelsText$default(lon, "value", null, 4, null), GxModelsKt.gxModelsText$default(lat, "value", null, 4, null))) {
            return;
        }
        JSONObject it = (JSONObject) CollectionsKt.firstOrNull((List) candidates());
        if (it == null || (str = GxModelsKt.gxModelsText$default(it, "value", null, 4, null)) == null) {
            str = "";
        }
        this.longitudinal = str;
    }

    private final void save(boolean enabled) {
        if (this.saving) {
            return;
        }
        if (enabled) {
            String se = selectionError();
            if (se.length() > 0) {
                this.error = se;
                render(true);
                return;
            }
        }
        this.saving = true;
        this.error = "";
        this.message = "";
        render(true);
        this.gen++;
        GxPage.send$default(this, "PUT", "/api/model-laboratory", new JSONObject().put("enabled", enabled).put("lateralModel", this.lateral).put("longitudinalModel", this.longitudinal), 0L, new Function2() { // from class: com.starpilot.starview.GxModelLabPage$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit save$lambda$17;
                save$lambda$17 = GxModelLabPage.save$lambda$17(GxModelLabPage.this, (String) obj, (JSONObject) obj2);
                return save$lambda$17;
            }
        }, 8, null);
    }

    /* VF: from Vineflower (jadx: Code restructure failed;incorrect) */
    public static final Unit save$lambda$17(GxModelLabPage var0, String var1, JSONObject var2) {
        var0.saving = false;
        if (var1 != null) {
            var0.error = var1;
            var0.toast(var1);
        } else {
            var0.dirty = false;
            if (var2 != null) {
                var0.applyPayload(var2);
            }

            label23: {
                if (var2 != null) {
                    var1 = var2.optString("message");
                    if (var1 != null) {
                        CharSequence var6 = var1;
                        CharSequence var4 = var6;
                        if (StringsKt.isBlank(var6)) {
                            var4 = null;
                        }

                        String var7 = (String)var4;
                        var1 = var7;
                        if (var7 != null) {
                            break label23;
                        }
                    }
                }

                var1 = "Model Laboratory configuration saved.";
            }

            var0.message = var1;
            var0.toast("Model Laboratory saved");
        }

        var0.render(true);
        return Unit.INSTANCE;
    }

    private final void prepareModel(String id) {
        if (this.saving) {
            return;
        }
        if (id.length() == 0) {
            return;
        }
        this.saving = true;
        this.error = "";
        this.message = "";
        render(true);
        GxPage.send$default(this, "POST", "/api/model-laboratory/download", new JSONObject().put("model", id), 0L, new Function2() { // from class: com.starpilot.starview.GxModelLabPage$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit prepareModel$lambda$20;
                prepareModel$lambda$20 = GxModelLabPage.prepareModel$lambda$20(GxModelLabPage.this, (String) obj, (JSONObject) obj2);
                return prepareModel$lambda$20;
            }
        }, 8, null);
    }

    /* VF: from Vineflower (jadx: Code restructure failed;incorrect) */
    public static final Unit prepareModel$lambda$20(GxModelLabPage var0, String var1, JSONObject var2) {
        if (var1 != null) {
            var0.saving = false;
            var0.error = var1;
            var0.toast(var1);
            var0.render(true);
            return Unit.INSTANCE;
        } else {
            label20: {
                if (var2 != null) {
                    var1 = var2.optString("message");
                    if (var1 != null) {
                        CharSequence var6 = var1;
                        CharSequence var4 = var6;
                        if (StringsKt.isBlank(var6)) {
                            var4 = null;
                        }

                        String var7 = (String)var4;
                        var1 = var7;
                        if (var7 != null) {
                            break label20;
                        }
                    }
                }

                var1 = "eGPU variant download queued.";
            }

            var0.message = var1;
            var0.toast("eGPU variant download queued");
            var0.refresh(new GxModelLabPage$$ExternalSyntheticLambda12(var0));
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit prepareModel$lambda$20$lambda$19(GxModelLabPage this$0) {
        this$0.saving = false;
        this$0.render(true);
        return Unit.INSTANCE;
    }

    /* VF: from Vineflower (jadx: Code restructure failed;incorrect) */
    private final void deleteModel(String var1) {
        if (!this.saving) {
            boolean var2;
            if (var1.length() == 0) {
                var2 = true;
            } else {
                var2 = false;
            }

            if (!var2) {
                String var5;
                label24: {
                    JSONObject var4 = this.byId(var1);
                    if (var4 != null) {
                        CharSequence var3 = null;
                        CharSequence var6 = GxModelsKt.gxModelsText$default(var4, "label", null, 4, null);
                        if (!StringsKt.isBlank(var6)) {
                            var3 = var6;
                        }

                        String var7 = (String)var3;
                        var5 = var7;
                        if (var7 != null) {
                            break label24;
                        }
                    }

                    var5 = var1;
                }

                this.confirm(
                    "Delete eGPU variant",
                    "Delete the eGPU variant for \"" + var5 + "\"? The normal on-device model will not be removed.",
                    "Delete",
                    new GxModelLabPage$$ExternalSyntheticLambda5(this, var1)
                );
                return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit deleteModel$lambda$25(final GxModelLabPage this$0, String $id) {
        if (this$0.saving) {
            return Unit.INSTANCE;
        }
        this$0.saving = true;
        this$0.error = "";
        this$0.message = "";
        this$0.render(true);
        this$0.gen++;
        GxPage.send$default(this$0, "DELETE", "/api/model-laboratory/artifact", new JSONObject().put("model", $id), 0L, new Function2() { // from class: com.starpilot.starview.GxModelLabPage$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit deleteModel$lambda$25$lambda$24;
                deleteModel$lambda$25$lambda$24 = GxModelLabPage.deleteModel$lambda$25$lambda$24(this$0, (String) obj, (JSONObject) obj2);
                return deleteModel$lambda$25$lambda$24;
            }
        }, 8, null);
        return Unit.INSTANCE;
    }

    /* VF: from Vineflower (jadx: Code restructure failed;incorrect) */
    public static final Unit deleteModel$lambda$25$lambda$24(GxModelLabPage var0, String var1, JSONObject var2) {
        var0.saving = false;
        if (var1 != null) {
            var0.error = var1;
            var0.toast(var1);
        } else {
            var0.dirty = false;
            if (var2 != null) {
                var0.applyPayload(var2);
            }

            label23: {
                if (var2 != null) {
                    var1 = var2.optString("message");
                    if (var1 != null) {
                        CharSequence var6 = var1;
                        CharSequence var4 = var6;
                        if (StringsKt.isBlank(var6)) {
                            var4 = null;
                        }

                        String var7 = (String)var4;
                        var1 = var7;
                        if (var7 != null) {
                            break label23;
                        }
                    }
                }

                var1 = "eGPU variant deleted.";
            }

            var0.message = var1;
            var0.toast("eGPU variant deleted");
        }

        var0.render(true);
        return Unit.INSTANCE;
    }

    static /* synthetic */ void render$default(GxModelLabPage gxModelLabPage, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        gxModelLabPage.render(z);
    }

    private final void render(boolean force) {
        if (this.runtimeBox == null) {
            return;
        }
        String sig = CollectionsKt.joinToString(CollectionsKt.listOf(Boolean.valueOf(this.loading), Boolean.valueOf(this.saving), this.error, this.message, Boolean.valueOf(this.dirty), Boolean.valueOf(this.chestnutReady), Boolean.valueOf(this.isOnroad), Boolean.valueOf(this.cfgEnabled), this.lateral, this.longitudinal, this.runtime.toString(), this.download.toString(), this.summary.toString(), CollectionsKt.joinToString(this.models, "|", "", "", -1, "...", new Function1() { // from class: com.starpilot.starview.GxModelLabPage$$ExternalSyntheticLambda7
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                CharSequence render$lambda$26;
                render$lambda$26 = GxModelLabPage.render$lambda$26((JSONObject) obj);
                return render$lambda$26;
            }
        })), "\u0001", "", "", -1, "...", null);
        if (force || !Intrinsics.areEqual(sig, this.lastSig)) {
            this.lastSig = sig;
            renderHead();
            renderAvailable();
            renderPair();
            renderRuntime();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CharSequence render$lambda$26(JSONObject it) {
        Intrinsics.checkNotNullParameter(it, "it");
        String jSONObject = it.toString();
        Intrinsics.checkNotNullExpressionValue(jSONObject, "toString(...)");
        return jSONObject;
    }

    private final void renderHead() {
        LinearLayout linearLayout = this.headBox;
        LinearLayout linearLayout2 = null;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("headBox");
            linearLayout = null;
        }
        linearLayout.removeAllViews();
        if (this.loading) {
            LinearLayout linearLayout3 = this.headBox;
            if (linearLayout3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("headBox");
            } else {
                linearLayout2 = linearLayout3;
            }
            linearLayout2.addView(empty("Loading laboratory status..."));
            return;
        }
        LinearLayout linearLayout4 = this.headBox;
        if (linearLayout4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("headBox");
        } else {
            linearLayout2 = linearLayout4;
        }
        LinearLayout c = card(linearLayout2);
        text("Model Laboratory", c, 21.0f, CarUi.Companion.getTEXT(), true);
        LinearLayout $this$renderHead_u24lambda_u2427 = new LinearLayout(getAct());
        $this$renderHead_u24lambda_u2427.setOrientation(0);
        c.addView($this$renderHead_u24lambda_u2427);
        text(this.chestnutReady ? "Chestnut ready" : "Chestnut required", $this$renderHead_u24lambda_u2427, 17.0f, this.chestnutReady ? CarUi.Companion.getACCENT() : CarUi.Companion.getWARN(), true).setPadding(0, getUi().dp(4), getUi().dp(24), getUi().dp(4));
        text(this.isOnroad ? "Onroad · locked" : "Parked · configurable", $this$renderHead_u24lambda_u2427, 17.0f, this.isOnroad ? CarUi.Companion.getWARN() : CarUi.Companion.getACCENT(), true);
        GxPage.text$default(this, "Use the lateral judgment of one small model and the longitudinal judgment of another.", c, 0.0f, 0, false, 28, null);
        if (this.error.length() > 0) {
            GxPage.text$default(this, this.error, c, 17.0f, CarUi.Companion.getWARN(), false, 16, null);
        }
        if (this.message.length() > 0) {
            GxPage.text$default(this, this.message, c, 17.0f, CarUi.Companion.getACCENT(), false, 16, null);
        }
    }

    private final void renderAvailable() {
        LinearLayout box = this.availBox;
        if (box == null) {
            Intrinsics.throwUninitializedPropertyAccessException("availBox");
            box = null;
        }
        box.removeAllViews();
        if (this.loading) {
            return;
        }
        int ready = this.summary.optInt("ready");
        note(ready + " downloaded · " + Math.max(this.summary.optInt("published") - ready, 0) + " available to download\nDownload eGPU-compatible small models. These are separate from the small models in Model Manager because they are compiled for the eGPU.", box);
        List<JSONObject> available = available();
        if (available.isEmpty()) {
            box.addView(empty("No eGPU variants are published in the model manifest."));
            return;
        }
        String downloading = GxModelsKt.gxModelsText$default(this.download, "model", null, 4, null);
        boolean enabled = !this.saving && !this.isOnroad && downloading.length() == 0;
        for (JSONObject m : available) {
            final String value = GxModelsKt.gxModelsText$default(m, "value", null, 4, null);
            boolean installed = m.optBoolean("modelLabArtifactInstalled");
            String series = GxModelsKt.gxModelsText$default(m, "series", null, 4, null);
            if (StringsKt.isBlank(series)) {
                series = "Unknown series";
            }
            String version = GxModelsKt.gxModelsText$default(m, "version", null, 4, null);
            if (StringsKt.isBlank(version)) {
                version = "unknown version";
            }
            String size = GxModelsKt.gxModelsText$default(m, "modelSize", null, 4, null);
            if (StringsKt.isBlank(size)) {
                size = "small";
            }
            String desc = value + " · " + series + "\n" + version + " · " + size + " · " + (installed ? "eGPU variant downloaded" : "eGPU variant not downloaded");
            String label = GxModelsKt.gxModelsText$default(m, "label", null, 4, null);
            if (StringsKt.isBlank(label)) {
                label = value;
            }
            GxPage.Row row = GxPage.row$default(this, label, desc, box, 0, 8, null);
            if (installed) {
                TextView del = GxPage.Row.button$default(row, "Delete eGPU variant", false, new Function0() { // from class: com.starpilot.starview.GxModelLabPage$$ExternalSyntheticLambda3
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return GxModelLabPage.renderAvailable$lambda$34(GxModelLabPage.this, value);
                    }
                }, 2, null);
                del.setTextColor(CarUi.Companion.getWARN());
                GxModelsKt.gxModelsEnable(del, enabled);
            } else {
                String btn;
                if (Intrinsics.areEqual(downloading, value)) {
                    String prog = GxModelsKt.gxModelsText$default(this.download, "progress", null, 4, null);
                    if (StringsKt.isBlank(prog)) {
                        prog = "starting…";
                    }
                    btn = "Downloading · " + prog;
                } else {
                    btn = "Download eGPU variant";
                }
                GxModelsKt.gxModelsEnable(GxPage.Row.button$default(row, btn, false, new Function0() { // from class: com.starpilot.starview.GxModelLabPage$$ExternalSyntheticLambda2
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return GxModelLabPage.renderAvailable$lambda$33(GxModelLabPage.this, value);
                    }
                }, 2, null), enabled);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderAvailable$lambda$33(GxModelLabPage this$0, String $id) {
        this$0.prepareModel($id);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderAvailable$lambda$34(GxModelLabPage this$0, String $id) {
        this$0.deleteModel($id);
        return Unit.INSTANCE;
    }

    private final String pickLabel(JSONObject m) {
        return GxModelsKt.gxModelsText$default(m, "label", null, 4, null) + " · " + GxModelsKt.gxModelsText$default(m, "version", null, 4, null);
    }

    private final void renderPair() {
        String str;
        boolean z;
        JSONObject it;
        LinearLayout linearLayout = this.pairBox;
        LinearLayout linearLayout2 = null;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pairBox");
            linearLayout = null;
        }
        linearLayout.removeAllViews();
        if (this.loading) {
            return;
        }
        GxModelLabPage gxModelLabPage = this;
        LinearLayout linearLayout3 = this.pairBox;
        if (linearLayout3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pairBox");
            linearLayout3 = null;
        }
        GxPage.Row st = GxPage.row$default(gxModelLabPage, "Model Laboratory pair", "Choose from downloaded eGPU variant combinations below.", linearLayout3, 0, 8, null);
        st.value(this.cfgEnabled ? "Enabled" : "Disabled").setTextColor(this.cfgEnabled ? CarUi.Companion.getACCENT() : CarUi.Companion.getDIM());
        GxModelLabPage gxModelLabPage2 = this;
        boolean z2 = false;
        String str2 = "Choose a model";
        if (this.lateral.length() == 0) {
            str = "Choose a model";
        } else {
            JSONObject it2 = byId(this.lateral);
            if (it2 == null || (str = pickLabel(it2)) == null) {
                str = this.lateral;
            }
        }
        String str3 = "Path shape, curvature, lane geometry, and driving desire\nSelected: " + str;
        LinearLayout linearLayout4 = this.pairBox;
        if (linearLayout4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pairBox");
            linearLayout4 = null;
        }
        GxPage.Row rl = GxPage.row$default(gxModelLabPage2, "Lateral model", str3, linearLayout4, 0, 8, null);
        GxPage.Row.button$default(rl, "Choose", false, new Function0() { // from class: com.starpilot.starview.GxModelLabPage$$ExternalSyntheticLambda13
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit renderPair$lambda$39;
                renderPair$lambda$39 = GxModelLabPage.renderPair$lambda$39(GxModelLabPage.this);
                return renderPair$lambda$39;
            }
        }, 2, null);
        GxModelLabPage gxModelLabPage3 = this;
        if (!(this.longitudinal.length() == 0) && ((it = byId(this.longitudinal)) == null || (str2 = pickLabel(it)) == null)) {
            str2 = this.longitudinal;
        }
        String str4 = "Speed, acceleration, stopping, leads, and scene confidence\nSelected: " + str2;
        LinearLayout linearLayout5 = this.pairBox;
        if (linearLayout5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pairBox");
            linearLayout5 = null;
        }
        GxPage.Row rn = GxPage.row$default(gxModelLabPage3, "Longitudinal model", str4, linearLayout5, 0, 8, null);
        GxPage.Row.button$default(rn, "Choose", false, new Function0() { // from class: com.starpilot.starview.GxModelLabPage$$ExternalSyntheticLambda14
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit renderPair$lambda$43;
                renderPair$lambda$43 = GxModelLabPage.renderPair$lambda$43(GxModelLabPage.this);
                return renderPair$lambda$43;
            }
        }, 2, null);
        LinearLayout linearLayout6 = this.pairBox;
        if (linearLayout6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pairBox");
        } else {
            linearLayout2 = linearLayout6;
        }
        LinearLayout c = card(linearLayout2);
        TextView $this$renderPair_u24lambda_u2444 = new TextView(getAct());
        $this$renderPair_u24lambda_u2444.setText(modelLabel(this.lateral) + "  steers   ⇄   " + modelLabel(this.longitudinal) + "  paces");
        $this$renderPair_u24lambda_u2444.setTextSize(19.0f);
        $this$renderPair_u24lambda_u2444.setTextColor(CarUi.Companion.getTEXT());
        $this$renderPair_u24lambda_u2444.setTypeface(Typeface.DEFAULT_BOLD);
        c.addView($this$renderPair_u24lambda_u2444);
        String se = selectionError();
        if (se.length() > 0) {
            GxPage.text$default(this, se, c, 15.0f, CarUi.Companion.getWARN(), false, 16, null);
        }
        LinearLayout bar = buttons(new Pair[]{TuplesKt.to("Enable for next drive", new Function0() { // from class: com.starpilot.starview.GxModelLabPage$$ExternalSyntheticLambda15
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit renderPair$lambda$45;
                renderPair$lambda$45 = GxModelLabPage.renderPair$lambda$45(GxModelLabPage.this);
                return renderPair$lambda$45;
            }
        }), TuplesKt.to("Disable", new Function0() { // from class: com.starpilot.starview.GxModelLabPage$$ExternalSyntheticLambda16
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit renderPair$lambda$46;
                renderPair$lambda$46 = GxModelLabPage.renderPair$lambda$46(GxModelLabPage.this);
                return renderPair$lambda$46;
            }
        }), TuplesKt.to("Refresh", new Function0() { // from class: com.starpilot.starview.GxModelLabPage$$ExternalSyntheticLambda17
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit renderPair$lambda$47;
                renderPair$lambda$47 = GxModelLabPage.renderPair$lambda$47(GxModelLabPage.this);
                return renderPair$lambda$47;
            }
        })}, c, true);
        View childAt = bar.getChildAt(0);
        Intrinsics.checkNotNullExpressionValue(childAt, "getChildAt(...)");
        if (!this.saving) {
            if (se.length() == 0) {
                z = true;
                GxModelsKt.gxModelsEnable(childAt, z);
                View childAt2 = bar.getChildAt(1);
                Intrinsics.checkNotNullExpressionValue(childAt2, "getChildAt(...)");
                if (!this.saving && !this.isOnroad && this.cfgEnabled) {
                    z2 = true;
                }
                GxModelsKt.gxModelsEnable(childAt2, z2);
                View childAt3 = bar.getChildAt(2);
                Intrinsics.checkNotNullExpressionValue(childAt3, "getChildAt(...)");
                GxModelsKt.gxModelsEnable(childAt3, !this.saving);
            }
        }
        z = false;
        GxModelsKt.gxModelsEnable(childAt, z);
        View childAt22 = bar.getChildAt(1);
        Intrinsics.checkNotNullExpressionValue(childAt22, "getChildAt(...)");
        if (!this.saving) {
            z2 = true;
        }
        GxModelsKt.gxModelsEnable(childAt22, z2);
        View childAt32 = bar.getChildAt(2);
        Intrinsics.checkNotNullExpressionValue(childAt32, "getChildAt(...)");
        GxModelsKt.gxModelsEnable(childAt32, !this.saving);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderPair$lambda$39(final GxModelLabPage this$0) {
        final List opts = this$0.ready();
        List listOf = CollectionsKt.listOf("Choose a model");
        List $this$map$iv = opts;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        for (Object item$iv$iv : $this$map$iv) {
            JSONObject it = (JSONObject) item$iv$iv;
            destination$iv$iv.add(this$0.pickLabel(it) + (Intrinsics.areEqual(GxModelsKt.gxModelsText$default(it, "value", null, 4, null), this$0.lateral) ? "  ✓" : ""));
        }
        this$0.pick("Lateral model", CollectionsKt.plus((Collection) listOf, destination$iv$iv), new Function1() { // from class: com.starpilot.starview.GxModelLabPage$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit renderPair$lambda$39$lambda$38;
                renderPair$lambda$39$lambda$38 = GxModelLabPage.renderPair$lambda$39$lambda$38(this$0, opts, ((Integer) obj).intValue());
                return renderPair$lambda$39$lambda$38;
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderPair$lambda$39$lambda$38(GxModelLabPage this$0, List $opts, int i) {
        this$0.lateral = i == 0 ? "" : GxModelsKt.gxModelsText$default((JSONObject) $opts.get(i - 1), "value", null, 4, null);
        this$0.onLateralChange();
        this$0.render(true);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderPair$lambda$43(final GxModelLabPage this$0) {
        final List opts = this$0.candidates();
        List listOf = CollectionsKt.listOf("Choose a model");
        List $this$map$iv = opts;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        for (Object item$iv$iv : $this$map$iv) {
            JSONObject it = (JSONObject) item$iv$iv;
            destination$iv$iv.add(this$0.pickLabel(it) + (Intrinsics.areEqual(GxModelsKt.gxModelsText$default(it, "value", null, 4, null), this$0.longitudinal) ? "  ✓" : ""));
        }
        this$0.pick("Longitudinal model", CollectionsKt.plus((Collection) listOf, destination$iv$iv), new Function1() { // from class: com.starpilot.starview.GxModelLabPage$$ExternalSyntheticLambda10
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit renderPair$lambda$43$lambda$42;
                renderPair$lambda$43$lambda$42 = GxModelLabPage.renderPair$lambda$43$lambda$42(this$0, opts, ((Integer) obj).intValue());
                return renderPair$lambda$43$lambda$42;
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderPair$lambda$43$lambda$42(GxModelLabPage this$0, List $opts, int i) {
        this$0.longitudinal = i == 0 ? "" : GxModelsKt.gxModelsText$default((JSONObject) $opts.get(i - 1), "value", null, 4, null);
        this$0.dirty = true;
        this$0.render(true);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderPair$lambda$45(GxModelLabPage this$0) {
        this$0.save(true);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderPair$lambda$46(GxModelLabPage this$0) {
        this$0.save(false);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderPair$lambda$47(GxModelLabPage this$0) {
        refresh$default(this$0, null, 1, null);
        return Unit.INSTANCE;
    }

    private final void renderRuntime() {
        LinearLayout linearLayout = this.runtimeBox;
        LinearLayout linearLayout2 = null;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("runtimeBox");
            linearLayout = null;
        }
        linearLayout.removeAllViews();
        if (this.loading) {
            return;
        }
        GxModelLabPage gxModelLabPage = this;
        LinearLayout linearLayout3 = this.runtimeBox;
        if (linearLayout3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("runtimeBox");
            linearLayout3 = null;
        }
        GxPage.Row s = GxPage.row$default(gxModelLabPage, "State", null, linearLayout3, 0, 8, null);
        s.value(runtimeState()).setTextColor(this.runtime.optBoolean("active") ? CarUi.Companion.getACCENT() : CarUi.Companion.getTEXT());
        GxModelLabPage gxModelLabPage2 = this;
        LinearLayout linearLayout4 = this.runtimeBox;
        if (linearLayout4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("runtimeBox");
            linearLayout4 = null;
        }
        GxPage.row$default(gxModelLabPage2, "Lateral", null, linearLayout4, 0, 8, null).value(modelLabel(GxModelsKt.gxModelsText$default(this.runtime, "lateralModel", null, 4, null)));
        GxModelLabPage gxModelLabPage3 = this;
        LinearLayout linearLayout5 = this.runtimeBox;
        if (linearLayout5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("runtimeBox");
            linearLayout5 = null;
        }
        GxPage.row$default(gxModelLabPage3, "Longitudinal", null, linearLayout5, 0, 8, null).value(modelLabel(GxModelsKt.gxModelsText$default(this.runtime, "longitudinalModel", null, 4, null)));
        String it = GxModelsKt.gxModelsText$default(this.runtime, "error", null, 4, null);
        String e = !StringsKt.isBlank(it) ? it : null;
        if (e != null) {
            LinearLayout linearLayout6 = this.runtimeBox;
            if (linearLayout6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("runtimeBox");
                linearLayout6 = null;
            }
            LinearLayout c = card(linearLayout6);
            GxPage.text$default(this, e, c, 17.0f, CarUi.Companion.getWARN(), false, 16, null);
        }
        LinearLayout linearLayout7 = this.runtimeBox;
        if (linearLayout7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("runtimeBox");
        } else {
            linearLayout2 = linearLayout7;
        }
        note("Both roles evaluate the same frame at 20 Hz. A runtime failure suppresses that frame and falls back to the built-in QCOM model.", linearLayout2);
    }
}
