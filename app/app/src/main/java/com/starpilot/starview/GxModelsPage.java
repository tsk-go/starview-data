package com.starpilot.starview;

import android.app.Activity;
import android.content.SharedPreferences;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.starpilot.starview.GxPage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.MatchResult;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import org.json.JSONObject;
import android.view.View;
import android.view.ViewGroup;
import org.json.JSONArray;

/* compiled from: GxModels.kt */
public final class GxModelsPage extends GxPage {
    private String activeBigModel;
    private String activeSmallModel;
    private String busy;
    private boolean cancelling;
    private String communityFilter;
    private LinearLayout controlsBox;
    private String currentModel;
    private boolean downloadAll;
    private boolean downloading;
    private String error;
    private int generation;
    private String hardwareFilter;
    private boolean isOnroad;
    private String lastSig;
    private LinearLayout listBox;
    private boolean loading;
    private String modelToDownload;
    private List<? extends JSONObject> models;
    private final boolean needsOffroad;
    private boolean polling;
    private final SharedPreferences prefs;
    private String progressText;
    private int sInstalled;
    private int sMissing;
    private int sTotal;
    private boolean selectionUncertain;
    private boolean selectionWriting;
    private String sortMode;
    private LinearLayout statusBox;
    private String userFilter;

    /* VF: from Vineflower (jadx: ) */
    public GxModelsPage(Activity var1, GxHost var2) {
        super(var1, var2);
        Intrinsics.checkNotNullParameter(var1, "act");
        Intrinsics.checkNotNullParameter(var2, "gx");
        this.needsOffroad = true;
        this.prefs = var1.getSharedPreferences("starview_gx_models", 0);
        this.loading = true;
        this.error = "";
        this.busy = "";
        this.selectionUncertain = true;
        this.sortMode = "release_date";
        this.userFilter = "all";
        this.communityFilter = "all";
        SharedPreferences var4 = this.prefs;
        String var6 = "both";
        String var3 = var4.getString("hardwareFilter", "both");
        String var5 = var6;
        if (var3 != null) {
            if (SetsKt.setOf(new String[]{"both", "gpu", "comma"}).contains(var3)) {
                var5 = var3;
            } else {
                var5 = null;
            }

            if (var5 == null) {
                var5 = var6;
            }
        }

        this.hardwareFilter = var5;
        this.models = CollectionsKt.emptyList();
        this.currentModel = "";
        this.activeSmallModel = "";
        this.activeBigModel = "";
        this.modelToDownload = "";
        this.progressText = "";
        this.lastSig = "";
    }

    @Override // com.starpilot.starview.GxPage
    public boolean getNeedsOffroad() {
        return this.needsOffroad;
    }

    @Override // com.starpilot.starview.GxPage
    public void build() {
        GxPage.header$default(this, "Model Manager", null, 2, null);
        this.statusBox = GxPage.box$default(this, null, 1, null);
        GxPage.header$default(this, "Controls", null, 2, null);
        this.controlsBox = GxPage.box$default(this, null, 1, null);
        GxPage.choice$default(this, "Sort", null, CollectionsKt.listOf((Object[]) new String[]{"Release Date", "Alphabetical"}), !Intrinsics.areEqual(this.sortMode, "release_date") ? 1 : 0, null, new Function1() { // from class: com.starpilot.starview.GxModelsPage$$ExternalSyntheticLambda29
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit build$lambda$1;
                build$lambda$1 = GxModelsPage.build$lambda$1(GxModelsPage.this, ((Integer) obj).intValue());
                return build$lambda$1;
            }
        }, 16, null);
        final List listOf = CollectionsKt.listOf((Object[]) new String[]{"both", "gpu", "comma"});
        GxPage.choice$default(this, "Model hardware", null, CollectionsKt.listOf((Object[]) new String[]{"Both", "GPU models only", "Comma models only"}), RangesKt.coerceAtLeast(listOf.indexOf(this.hardwareFilter), 0), null, new Function1() { // from class: com.starpilot.starview.GxModelsPage$$ExternalSyntheticLambda30
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit build$lambda$3;
                build$lambda$3 = GxModelsPage.build$lambda$3(GxModelsPage.this, listOf, ((Integer) obj).intValue());
                return build$lambda$3;
            }
        }, 16, null);
        final List listOf2 = CollectionsKt.listOf((Object[]) new String[]{"all", "yes", "no"});
        GxPage.choice$default(this, "Your Favorite", null, CollectionsKt.listOf((Object[]) new String[]{"All", "Yes", "No"}), 0, null, new Function1() { // from class: com.starpilot.starview.GxModelsPage$$ExternalSyntheticLambda31
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit build$lambda$4;
                build$lambda$4 = GxModelsPage.build$lambda$4(GxModelsPage.this, listOf2, ((Integer) obj).intValue());
                return build$lambda$4;
            }
        }, 16, null);
        GxPage.choice$default(this, "Community Favorite", null, CollectionsKt.listOf((Object[]) new String[]{"All", "Yes", "No"}), 0, null, new Function1() { // from class: com.starpilot.starview.GxModelsPage$$ExternalSyntheticLambda32
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit build$lambda$5;
                build$lambda$5 = GxModelsPage.build$lambda$5(GxModelsPage.this, listOf2, ((Integer) obj).intValue());
                return build$lambda$5;
            }
        }, 16, null);
        GxPage.header$default(this, "Models", null, 2, null);
        this.listBox = GxPage.box$default(this, null, 1, null);
        render(true);
        every(2000L, new Function0() { // from class: com.starpilot.starview.GxModelsPage$$ExternalSyntheticLambda33
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit build$lambda$7;
                build$lambda$7 = GxModelsPage.build$lambda$7(GxModelsPage.this);
                return build$lambda$7;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit build$lambda$1(GxModelsPage this$0, int i) {
        this$0.sortMode = i == 0 ? "release_date" : "alphabetical";
        this$0.render(true);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit build$lambda$3(GxModelsPage this$0, List $hw, int i) {
        this$0.hardwareFilter = (String) $hw.get(i);
        try {
            Result.Companion companion = Result.Companion;
            this$0.prefs.edit().putString("hardwareFilter", this$0.hardwareFilter).apply();
            KtCompat.resultOf(Unit.INSTANCE);
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            KtCompat.resultOf(ResultKt.createFailure(th));
        }
        this$0.render(true);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit build$lambda$4(GxModelsPage this$0, List $yn, int i) {
        this$0.userFilter = (String) $yn.get(i);
        this$0.render(true);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit build$lambda$5(GxModelsPage this$0, List $yn, int i) {
        this$0.communityFilter = (String) $yn.get(i);
        this$0.render(true);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit build$lambda$7(final GxModelsPage this$0) {
        if (!this$0.selectionWriting) {
            if ((this$0.busy.length() == 0) && !this$0.polling) {
                this$0.polling = true;
                this$0.refresh(new Function1() { // from class: com.starpilot.starview.GxModelsPage$$ExternalSyntheticLambda28
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        Unit build$lambda$7$lambda$6;
                        build$lambda$7$lambda$6 = GxModelsPage.build$lambda$7$lambda$6(this$0, ((Boolean) obj).booleanValue());
                        return build$lambda$7$lambda$6;
                    }
                });
            }
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit build$lambda$7$lambda$6(GxModelsPage this$0, boolean it) {
        this$0.polling = false;
        return Unit.INSTANCE;
    }

    @Override // com.starpilot.starview.GxPage
    public void onHide() {
        this.generation++;
        this.polling = false;
    }

    /* VF: from Vineflower (jadx: ) */
    static void refresh$default(GxModelsPage var0, Function1 var1, int var2, Object var3) {
        if ((var2 & 1) != 0) {
            var1 = null;
        }

        var0.refresh(var1);
    }

    private final void refresh(final Function1<? super Boolean, Unit> then) {
        this.generation++;
        final int gen = this.generation;
        getJson("/api/models/status", new Function2() { // from class: com.starpilot.starview.GxModelsPage$$ExternalSyntheticLambda18
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit refresh$lambda$8;
                refresh$lambda$8 = GxModelsPage.refresh$lambda$8(GxModelsPage.this, gen, then, (String) obj, (JSONObject) obj2);
                return refresh$lambda$8;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit refresh$lambda$8(GxModelsPage this$0, int $gen, Function1 $then, String e, JSONObject p) {
        List<? extends JSONObject> gxModelsObjects;
        if (!this$0.getShown() || $gen != this$0.generation) {
            if ($then != null) {
                $then.invoke(false);
            }
            return Unit.INSTANCE;
        }
        if (e == null && p != null) {
            gxModelsObjects = GxModelsKt.gxModelsObjects(p.optJSONArray("models"));
            this$0.models = gxModelsObjects;
            this$0.currentModel = GxModelsKt.gxModelsText$default(p, "currentModel", null, 4, null);
            this$0.activeSmallModel = GxModelsKt.gxModelsText$default(p, "activeSmallModel", null, 4, null);
            this$0.activeBigModel = GxModelsKt.gxModelsText$default(p, "activeBigModel", null, 4, null);
            JSONObject s = p.optJSONObject("summary");
            this$0.sInstalled = s != null ? s.optInt("installed") : 0;
            this$0.sMissing = s != null ? s.optInt("missing") : 0;
            this$0.sTotal = s != null ? s.optInt("total") : 0;
            this$0.modelToDownload = GxModelsKt.gxModelsText$default(p, "modelToDownload", null, 4, null);
            this$0.downloadAll = p.optBoolean("downloadAll");
            this$0.downloading = p.optBoolean("downloading");
            this$0.cancelling = p.optBoolean("cancelling");
            this$0.progressText = GxModelsKt.gxModelsText$default(p, "progress", null, 4, null);
            this$0.isOnroad = p.optBoolean("isOnroad");
            this$0.error = "";
            this$0.selectionUncertain = false;
            this$0.loading = false;
            render$default(this$0, false, 1, null);
            if ($then != null) {
                $then.invoke(true);
            }
            return Unit.INSTANCE;
        }
        this$0.error = e == null ? "bad reply" : e;
        this$0.loading = false;
        render$default(this$0, false, 1, null);
        if ($then != null) {
            $then.invoke(false);
        }
        return Unit.INSTANCE;
    }

    private final String labelOf(String key) {
        Object element$iv;
        String gxModelsText;
        Iterable $this$firstOrNull$iv = this.models;
        Iterator it = $this$firstOrNull$iv.iterator();
        while (true) {
            if (!it.hasNext()) {
                element$iv = null;
                break;
            }
            element$iv = it.next();
            if (Intrinsics.areEqual(GxModelsKt.gxModelsText$default((JSONObject) element$iv, "value", null, 4, null), key)) {
                break;
            }
        }
        JSONObject it2 = (JSONObject) element$iv;
        if (it2 == null) {
            return null;
        }
        gxModelsText = GxModelsKt.gxModelsText(it2, "label", key);
        String str = gxModelsText;
        if (StringsKt.isBlank(str)) {
            str = key;
        }
        return str;
    }

    private final String currentLabel() {
        if (StringsKt.isBlank(this.currentModel)) {
            return "none";
        }
        String labelOf = labelOf(this.currentModel);
        return labelOf == null ? this.currentModel : labelOf;
    }

    private final String downloadTargetLabel() {
        if (this.downloadAll) {
            return "all missing models";
        }
        String labelOf = labelOf(this.modelToDownload);
        if (labelOf != null) {
            return labelOf;
        }
        String str = this.modelToDownload;
        if (StringsKt.isBlank(str)) {
            str = "a model";
        }
        return str;
    }

    private final List<JSONObject> installedSmall() {
        Iterable $this$filter$iv = this.models;
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            JSONObject it = (JSONObject) element$iv$iv;
            if (it.optBoolean("installed") && !it.optBoolean("requiresGpu")) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        return (List) destination$iv$iv;
    }

    private final List<JSONObject> installedBig() {
        Iterable $this$filter$iv = this.models;
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            JSONObject it = (JSONObject) element$iv$iv;
            if (it.optBoolean("installed") && it.optBoolean("requiresGpu")) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        return (List) destination$iv$iv;
    }

    private final List<JSONObject> sorted() {
        boolean gxModelsMatchesHardware;
        Iterable $this$filter$iv = this.models;
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            JSONObject it = (JSONObject) element$iv$iv;
            gxModelsMatchesHardware = GxModelsKt.gxModelsMatchesHardware(it, this.hardwareFilter);
            if (gxModelsMatchesHardware) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        Iterable $this$filter$iv2 = (List) destination$iv$iv;
        Collection destination$iv$iv2 = new ArrayList();
        Iterator it2 = $this$filter$iv2.iterator();
        while (true) {
            if (!it2.hasNext()) {
                break;
            }
            Object element$iv$iv2 = it2.next();
            JSONObject it3 = (JSONObject) element$iv$iv2;
            if (Intrinsics.areEqual(this.userFilter, "all") || it3.optBoolean("userFavorite") == Intrinsics.areEqual(this.userFilter, "yes")) {
                destination$iv$iv2.add(element$iv$iv2);
            }
        }
        Iterable $this$filter$iv3 = (List) destination$iv$iv2;
        Collection destination$iv$iv3 = new ArrayList();
        for (Object element$iv$iv3 : $this$filter$iv3) {
            JSONObject it4 = (JSONObject) element$iv$iv3;
            JSONObject it5 = (Intrinsics.areEqual(this.communityFilter, "all") || it4.optBoolean("communityFavorite") == Intrinsics.areEqual(this.communityFilter, "yes")) ? 1 : null;
            if (it5 != null) {
                destination$iv$iv3.add(element$iv$iv3);
            }
        }
        List rows = (List) destination$iv$iv3;
        final Function1 name = new Function1() { // from class: com.starpilot.starview.GxModelsPage$$ExternalSyntheticLambda8
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                String sorted$lambda$18;
                sorted$lambda$18 = GxModelsPage.sorted$lambda$18((JSONObject) obj);
                return sorted$lambda$18;
            }
        };
        final Function2 function2 = new Function2() { // from class: com.starpilot.starview.GxModelsPage$$ExternalSyntheticLambda9
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                int sorted$lambda$19;
                sorted$lambda$19 = GxModelsPage.sorted$lambda$19(GxModelsPage.this, name, (JSONObject) obj, (JSONObject) obj2);
                return Integer.valueOf(sorted$lambda$19);
            }
        };
        return CollectionsKt.sortedWith(rows, new Comparator() { // from class: com.starpilot.starview.GxModelsPage$$ExternalSyntheticLambda10
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                int sorted$lambda$20;
                sorted$lambda$20 = GxModelsPage.sorted$lambda$20(function2, obj, obj2);
                return sorted$lambda$20;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String sorted$lambda$18(JSONObject m) {
        String gxModelsText;
        Intrinsics.checkNotNullParameter(m, "m");
        gxModelsText = GxModelsKt.gxModelsText(m, "label", GxModelsKt.gxModelsText$default(m, "value", null, 4, null));
        return gxModelsText;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int sorted$lambda$20(Function2 $tmp0, Object p0, Object p1) {
        return ((Number) $tmp0.invoke(p0, p1)).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int sorted$lambda$19(GxModelsPage this$0, Function1 $name, JSONObject a, JSONObject b) {
        int gxModelsNatCompare;
        long gxModelsReleasedTs;
        long gxModelsReleasedTs2;
        if (Intrinsics.areEqual(this$0.sortMode, "release_date")) {
            gxModelsReleasedTs = GxModelsKt.gxModelsReleasedTs(GxModelsKt.gxModelsText$default(b, "released", null, 4, null));
            gxModelsReleasedTs2 = GxModelsKt.gxModelsReleasedTs(GxModelsKt.gxModelsText$default(a, "released", null, 4, null));
            int d = Intrinsics.compare(gxModelsReleasedTs, gxModelsReleasedTs2);
            if (d != 0) {
                return d;
            }
        }
        Intrinsics.checkNotNull(a);
        String str = (String) $name.invoke(a);
        Intrinsics.checkNotNull(b);
        gxModelsNatCompare = GxModelsKt.gxModelsNatCompare(str, (String) $name.invoke(b));
        return gxModelsNatCompare;
    }

    private final String rowState(JSONObject m) {
        String key = GxModelsKt.gxModelsText$default(m, "value", null, 4, null);
        if ((key.length() > 0) && Intrinsics.areEqual(key, this.currentModel)) {
            return "active";
        }
        if (!this.downloading) {
            return m.optBoolean("installed") ? "installed" : "available";
        }
        boolean isTarget = !this.downloadAll && Intrinsics.areEqual(this.modelToDownload, key);
        return (this.downloadAll || isTarget) ? "cancellable" : "busy";
    }

    private final boolean allowedOnroad(String action) {
        return Intrinsics.areEqual(action, "refresh") || Intrinsics.areEqual(action, "favorite") || Intrinsics.areEqual(action, "unfavorite") || !this.isOnroad;
    }

    static /* synthetic */ void runAction$default(GxModelsPage gxModelsPage, String str, JSONObject jSONObject, int i, Object obj) {
        if ((i & 2) != 0) {
            jSONObject = null;
        }
        gxModelsPage.runAction(str, jSONObject);
    }

    private final void runAction(final String action, JSONObject m) {
        String gxModelsText;
        boolean z;
        boolean selecting = Intrinsics.areEqual(action, "select-small") || Intrinsics.areEqual(action, "select-big");
        if (selecting && this.selectionUncertain) {
            return;
        }
        if (this.busy.length() > 0) {
            toast("Please wait for the current action to finish.");
            return;
        }
        if (!allowedOnroad(action)) {
            toast("Actions are blocked while onroad.");
            return;
        }
        final String key = GxModelsKt.gxModelsText$default(m, "value", null, 4, null);
        String str = key;
        String str2 = "model";
        if (StringsKt.isBlank(str)) {
            str = "model";
        }
        gxModelsText = GxModelsKt.gxModelsText(m, "label", str);
        String str3 = gxModelsText;
        if (StringsKt.isBlank(str3)) {
            String str4 = key;
            if (!StringsKt.isBlank(str4)) {
                str2 = str4;
            }
            str3 = str2;
        }
        final String label = str3;
        if (Intrinsics.areEqual(action, "download")) {
            z = (m == null || !m.optBoolean("requiresGpu") || m.optBoolean("gpuAvailable")) ? false : true;
        } else if (Intrinsics.areEqual(action, "downloadAll")) {
            Iterable $this$any$iv = this.models;
            if (!($this$any$iv instanceof Collection) || !((Collection) $this$any$iv).isEmpty()) {
                Iterator it = $this$any$iv.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        z = false;
                        break;
                    }
                    Object element$iv = it.next();
                    JSONObject it2 = (JSONObject) element$iv;
                    if ((it2.optBoolean("installed") || !it2.optBoolean("requiresGpu") || it2.optBoolean("gpuAvailable")) ? false : true) {
                        z = true;
                        break;
                    }
                }
            } else {
                z = false;
            }
        } else {
            z = false;
        }
        boolean needsGpuPrompt = z;
        if (needsGpuPrompt) {
            String p1 = Intrinsics.areEqual(action, "downloadAll") ? "This download includes models that require an external GPU. You can download them now, but they cannot run until a compatible GPU is connected and detected." : "“" + label + "” requires an external GPU. You can download it now, but it cannot run until a compatible GPU is connected and detected.";
            confirm("No external GPU detected", p1 + "\n\nGPU model files can be large. Downloading does not activate a model or change the model currently in use.", "Download anyway", new Function0() { // from class: com.starpilot.starview.GxModelsPage$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit runAction$lambda$26;
                    runAction$lambda$26 = GxModelsPage.runAction$lambda$26(GxModelsPage.this, action, key, label);
                    return runAction$lambda$26;
                }
            });
        } else {
            if (Intrinsics.areEqual(action, "delete")) {
                confirm("Delete model", "Delete local files for \"" + label + "\"?", "Delete", new Function0() { // from class: com.starpilot.starview.GxModelsPage$$ExternalSyntheticLambda2
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        Unit runAction$lambda$27;
                        runAction$lambda$27 = GxModelsPage.runAction$lambda$27(GxModelsPage.this, action, key, label);
                        return runAction$lambda$27;
                    }
                });
                return;
            }
            this.busy = action + ":" + key;
            render$default(this, false, 1, null);
            perform(action, key, label, false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit runAction$lambda$26(final GxModelsPage this$0, final String $action, final String $key, final String $label) {
        if (this$0.busy.length() > 0) {
            this$0.toast("Please wait for the current action to finish.");
            return Unit.INSTANCE;
        }
        this$0.busy = $action + ":" + $key;
        render$default(this$0, false, 1, null);
        this$0.refresh(new Function1() { // from class: com.starpilot.starview.GxModelsPage$$ExternalSyntheticLambda22
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit runAction$lambda$26$lambda$25;
                runAction$lambda$26$lambda$25 = GxModelsPage.runAction$lambda$26$lambda$25(this$0, $action, $key, $label, ((Boolean) obj).booleanValue());
                return runAction$lambda$26$lambda$25;
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit runAction$lambda$26$lambda$25(GxModelsPage this$0, String $action, String $key, String $label, boolean ok) {
        if (!this$0.getShown()) {
            this$0.busy = "";
            return Unit.INSTANCE;
        }
        if (ok) {
            if (!(this$0.error.length() > 0) && this$0.allowedOnroad($action) && !this$0.downloading) {
                this$0.perform($action, $key, $label, true);
                return Unit.INSTANCE;
            }
        }
        this$0.toast("Download not started. Check the device status and try again while parked.");
        this$0.busy = "";
        render$default(this$0, false, 1, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit runAction$lambda$27(GxModelsPage this$0, String $action, String $key, String $label) {
        if (this$0.busy.length() > 0) {
            this$0.toast("Please wait for the current action to finish.");
            return Unit.INSTANCE;
        }
        this$0.busy = $action + ":" + $key;
        render$default(this$0, false, 1, null);
        this$0.perform($action, $key, $label, false);
        return Unit.INSTANCE;
    }

    /* VF: from Vineflower (jadx: Code restructure failed;incorrect) */
    private static final void perform$finish(GxModelsPage var0, String var1, JSONObject var2, String var3) {
        if (!var0.getShown()) {
            var0.busy = "";
        } else {
            if (var1 != null) {
                var0.toast(var1);
            } else {
                label23: {
                    if (var2 != null) {
                        var1 = var2.optString("message");
                        if (var1 != null) {
                            CharSequence var7 = var1;
                            CharSequence var5 = var7;
                            if (StringsKt.isBlank(var7)) {
                                var5 = null;
                            }

                            String var8 = (String)var5;
                            var1 = var8;
                            if (var8 != null) {
                                break label23;
                            }
                        }
                    }

                    var1 = var3;
                }

                var0.toast(var1);
            }

            var0.refresh(new GxModelsPage$$ExternalSyntheticLambda0(var0));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit perform$finish$lambda$29(GxModelsPage this$0, boolean it) {
        this$0.busy = "";
        this$0.render(true);
        return Unit.INSTANCE;
    }

    /* VF: from Vineflower (jadx: Code restructure failed;incorrect) */
    private final void perform(String var1, String var2, String var3, boolean var4) {
        label97: {
            label98: {
                switch (var1.hashCode()) {
                    case -1717948619:
                        if (!var1.equals("unfavorite")) {
                            break label97;
                        }
                        break;
                    case -1657124689:
                        if (!var1.equals("select-big")) {
                            break label97;
                        }
                        break label98;
                    case -1367724422:
                        if (var1.equals("cancel")) {
                            GxPage.send$default(this, "POST", "/api/models/cancel", null, 0L, new GxModelsPage$$ExternalSyntheticLambda23(this), 12, null);
                            return;
                        }
                        break label97;
                    case -1335458389:
                        if (var1.equals("delete")) {
                            GxPage.send$default(
                                this,
                                "POST",
                                "/api/models/delete",
                                new JSONObject().put("model", var2),
                                0L,
                                new GxModelsPage$$ExternalSyntheticLambda24(var3, this),
                                8,
                                null
                            );
                            return;
                        }
                        break label97;
                    case -1211167751:
                        if (var1.equals("downloadAll")) {
                            GxPage.send$default(
                                this,
                                "POST",
                                "/api/models/download_all",
                                new JSONObject().put("allowGpuWithoutGpu", var4),
                                0L,
                                new GxModelsPage$$ExternalSyntheticLambda21(this),
                                8,
                                null
                            );
                            return;
                        }
                        break label97;
                    case 951857398:
                        if (!var1.equals("select-small")) {
                            break label97;
                        }
                        break label98;
                    case 1050790300:
                        if (!var1.equals("favorite")) {
                            break label97;
                        }
                        break;
                    case 1085444827:
                        if (var1.equals("refresh")) {
                            GxPage.send$default(this, "POST", "/api/models/refresh_manifest", null, 120L, new GxModelsPage$$ExternalSyntheticLambda26(this), 4, null);
                            return;
                        }
                        break label97;
                    case 1427818632:
                        if (var1.equals("download")) {
                            GxPage.send$default(
                                this,
                                "POST",
                                "/api/models/download",
                                new JSONObject().put("model", var2).put("allowGpuWithoutGpu", var4),
                                0L,
                                new GxModelsPage$$ExternalSyntheticLambda20(var3, this),
                                8,
                                null
                            );
                            return;
                        }
                    default:
                        break label97;
                }

                var4 = Intrinsics.areEqual(var1, "favorite");
                JSONArray var10 = new JSONArray();

                for (JSONObject var6 : this.models) {
                    var1 = GxModelsKt.gxModelsText$default(var6, "value", null, 4, null);
                    if (var6.optBoolean("userFavorite") && !Intrinsics.areEqual(var1, var2)) {
                        boolean var5;
                        if (var1.length() > 0) {
                            var5 = true;
                        } else {
                            var5 = false;
                        }

                        if (var5) {
                            var10.put(var1);
                        }
                    }
                }

                if (var4) {
                    var10.put(var2);
                }

                GxPage.send$default(
                    this,
                    "PUT",
                    "/api/models/preferences",
                    new JSONObject().put("userFavorites", var10),
                    0L,
                    new GxModelsPage$$ExternalSyntheticLambda25(var4, this),
                    8,
                    null
                );
                return;
            }

            if (Intrinsics.areEqual(var1, "select-big")) {
                var1 = "big";
            } else {
                var1 = "small";
            }

            this.selectionUncertain = true;
            this.selectionWriting = true;
            this.generation++;
            GxPage.send$default(
                this,
                "PUT",
                "/api/models/active",
                new JSONObject().put("profile", var1).put("model", var2),
                0L,
                new GxModelsPage$$ExternalSyntheticLambda19(this, var3),
                8,
                null
            );
            return;
        }

        this.busy = "";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit perform$lambda$30(GxModelsPage this$0, String $label, String e, JSONObject o) {
        this$0.selectionWriting = false;
        perform$finish(this$0, e, o, "Selected \"" + $label + "\".");
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit perform$lambda$31(String $label, GxModelsPage this$0, String e, JSONObject o) {
        perform$finish(this$0, e, o, "Downloading \"" + $label + "\"...");
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit perform$lambda$32(GxModelsPage this$0, String e, JSONObject o) {
        perform$finish(this$0, e, o, "Started downloading all models.");
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit perform$lambda$33(GxModelsPage this$0, String e, JSONObject o) {
        perform$finish(this$0, e, o, "Cancellation requested.");
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit perform$lambda$34(String $label, GxModelsPage this$0, String e, JSONObject o) {
        perform$finish(this$0, e, o, "Deleted files for \"" + $label + "\".");
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit perform$lambda$35(boolean $add, GxModelsPage this$0, String e, JSONObject o) {
        perform$finish(this$0, e, o, $add ? "Model added to your favorites." : "Model removed from your favorites.");
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit perform$lambda$36(GxModelsPage this$0, String e, JSONObject o) {
        perform$finish(this$0, e, o, "Model manifest refreshed.");
        return Unit.INSTANCE;
    }

    static /* synthetic */ void render$default(GxModelsPage gxModelsPage, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        gxModelsPage.render(z);
    }

    private final void render(boolean force) {
        if (this.listBox == null) {
            return;
        }
        String sig = CollectionsKt.joinToString(CollectionsKt.listOf(Boolean.valueOf(this.loading), this.error, this.busy, Boolean.valueOf(this.selectionUncertain), this.sortMode, this.userFilter, this.communityFilter, this.hardwareFilter, this.currentModel, this.activeSmallModel, this.activeBigModel, Integer.valueOf(this.sInstalled), Integer.valueOf(this.sMissing), Integer.valueOf(this.sTotal), this.modelToDownload, Boolean.valueOf(this.downloadAll), Boolean.valueOf(this.downloading), Boolean.valueOf(this.cancelling), this.progressText, Boolean.valueOf(this.isOnroad), CollectionsKt.joinToString(this.models, "|", "", "", -1, "...", new Function1() { // from class: com.starpilot.starview.GxModelsPage$$ExternalSyntheticLambda27
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                CharSequence render$lambda$37;
                render$lambda$37 = GxModelsPage.render$lambda$37((JSONObject) obj);
                return render$lambda$37;
            }
        })), "\u0001", "", "", -1, "...", null);
        if (force || !Intrinsics.areEqual(sig, this.lastSig)) {
            this.lastSig = sig;
            renderStatus();
            renderControls();
            renderList();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CharSequence render$lambda$37(JSONObject it) {
        Intrinsics.checkNotNullParameter(it, "it");
        String jSONObject = it.toString();
        Intrinsics.checkNotNullExpressionValue(jSONObject, "toString(...)");
        return jSONObject;
    }

    /* VF: from Vineflower (jadx: ) */
    private final void renderStatus() {
        LinearLayout var9 = this.statusBox;
        Object var7 = null;
        Object var8 = null;
        LinearLayout var6 = var9;
        if (var9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("statusBox");
            var6 = null;
        }

        var6.removeAllViews();
        if (this.loading) {
            var6 = this.statusBox;
            if (var6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("statusBox");
                var6 = (LinearLayout)var8;
            }

            var6.addView((View)this.empty("Loading models..."));
        } else {
            var8 = this.statusBox;
            var6 = (LinearLayout)var8;
            if (var8 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("statusBox");
                var6 = null;
            }

            var6 = this.card((ViewGroup)var6);
            var8 = this;
            int var5 = this.sInstalled;
            int var3 = this.sMissing;
            int var4 = this.sTotal;
            GxPage.text$default(
                (GxPage)var8, var5 + " installed · " + var3 + " missing · " + var4 + " total", (ViewGroup)var6, 21.0F, CarUi.Companion.getTEXT(), false, 16, null
            );
            var8 = this.currentLabel();
            this.text("Selected: " + var8, (ViewGroup)var6, 18.0F, CarUi.Companion.getACCENT(), true);
            boolean var10;
            if (this.error.length() > 0) {
                var10 = true;
            } else {
                var10 = false;
            }

            if (var10) {
                this.text("Status load failed", (ViewGroup)var6, 17.0F, CarUi.Companion.getWARN(), true);
                GxPage.text$default(this, this.error, (ViewGroup)var6, 15.0F, CarUi.Companion.getWARN(), false, 16, null);
            }

            if (this.isOnroad) {
                GxPage.text$default(
                    this, "Onroad: switching models and downloads are disabled.", (ViewGroup)var6, 17.0F, CarUi.Companion.getWARN(), false, 16, null
                );
            }

            if (this.downloading) {
                var8 = this.downloadTargetLabel();
                this.text("Downloading " + var8, (ViewGroup)var6, 18.0F, CarUi.Companion.getTEXT(), true);
                if (this.progressText.length() > 0) {
                    var10 = true;
                } else {
                    var10 = false;
                }

                if (var10) {
                    GxPage.text$default(this, this.progressText, (ViewGroup)var6, 0.0F, 0, false, 28, null);
                }

                if (this.cancelling) {
                    GxPage.text$default(this, "Cancellation requested.", (ViewGroup)var6, 15.0F, CarUi.Companion.getWARN(), false, 16, null);
                }

                var8 = this.progress((ViewGroup)var6);
                MatchResult var21 = (new Regex("(\\d+(?:\\.\\d+)?)\\s*%")).find(this.progressText, 0);
                Double var14 = (Double)var7;
                if (var21 != null) {
                    List var22 = var21.getGroupValues();
                    var14 = (Double)var7;
                    if (var22 != null) {
                        String var23 = (String)var22.get(1);
                        var14 = (Double)var7;
                        if (var23 != null) {
                            var14 = StringsKt.toDoubleOrNull(var23);
                        }
                    }
                }

                double var1;
                if (var14 != null) {
                    var1 = var14;
                } else {
                    var1 = -1.0;
                }

                this.setProgress((ProgressBar)var8, var1);
            }
        }
    }

    private final void renderControls() {
        String labelOf;
        LinearLayout linearLayout = this.controlsBox;
        LinearLayout linearLayout2 = null;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("controlsBox");
            linearLayout = null;
        }
        linearLayout.removeAllViews();
        if (this.loading) {
            return;
        }
        boolean notBusy = this.busy.length() == 0;
        GxModelsPage gxModelsPage = this;
        String str = this.downloading ? "Download in progress" : "Downloads";
        String str2 = this.downloading ? "Stop the current model download." : "Download every missing model in the catalogue, or refresh the model manifest.";
        LinearLayout linearLayout3 = this.controlsBox;
        if (linearLayout3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("controlsBox");
            linearLayout3 = null;
        }
        GxPage.Row r = GxPage.row$default(gxModelsPage, str, str2, linearLayout3, 0, 8, null);
        if (this.downloading) {
            GxModelsKt.gxModelsEnable(GxPage.Row.button$default(r, "Cancel Download", false, new Function0() { // from class: com.starpilot.starview.GxModelsPage$$ExternalSyntheticLambda3
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit renderControls$lambda$38;
                    renderControls$lambda$38 = GxModelsPage.renderControls$lambda$38(GxModelsPage.this);
                    return renderControls$lambda$38;
                }
            }, 2, null), notBusy);
        } else {
            GxModelsKt.gxModelsEnable(GxPage.Row.button$default(r, "Download all missing — entire catalogue", false, new Function0() { // from class: com.starpilot.starview.GxModelsPage$$ExternalSyntheticLambda4
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit renderControls$lambda$39;
                    renderControls$lambda$39 = GxModelsPage.renderControls$lambda$39(GxModelsPage.this);
                    return renderControls$lambda$39;
                }
            }, 2, null), notBusy);
        }
        GxModelsKt.gxModelsEnable(GxPage.Row.button$default(r, Intrinsics.areEqual(this.busy, "refresh:") ? "Refreshing…" : "Refresh", false, new Function0() { // from class: com.starpilot.starview.GxModelsPage$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit renderControls$lambda$40;
                renderControls$lambda$40 = GxModelsPage.renderControls$lambda$40(GxModelsPage.this);
                return renderControls$lambda$40;
            }
        }, 2, null), notBusy);
        boolean selOk = (!notBusy || this.selectionUncertain || this.isOnroad) ? false : true;
        final List small = installedSmall();
        final List big = installedBig();
        GxModelsPage gxModelsPage2 = this;
        String labelOf2 = labelOf(this.activeSmallModel);
        if (labelOf2 == null) {
            String str3 = this.activeSmallModel;
            if (StringsKt.isBlank(str3)) {
                str3 = "none";
            }
            labelOf2 = str3;
        }
        String str4 = labelOf2;
        LinearLayout linearLayout4 = this.controlsBox;
        if (linearLayout4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("controlsBox");
            linearLayout4 = null;
        }
        GxPage.Row rs = GxPage.row$default(gxModelsPage2, "Active Small", str4, linearLayout4, 0, 8, null);
        GxModelsKt.gxModelsEnable(GxPage.Row.button$default(rs, "Change", false, new Function0() { // from class: com.starpilot.starview.GxModelsPage$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit renderControls$lambda$45;
                renderControls$lambda$45 = GxModelsPage.renderControls$lambda$45(small, GxModelsPage.this);
                return renderControls$lambda$45;
            }
        }, 2, null), selOk);
        GxModelsPage gxModelsPage3 = this;
        if (StringsKt.isBlank(this.activeBigModel)) {
            labelOf = "None — always use Active Small";
        } else {
            labelOf = labelOf(this.activeBigModel);
            if (labelOf == null) {
                labelOf = this.activeBigModel;
            }
        }
        String str5 = labelOf;
        LinearLayout linearLayout5 = this.controlsBox;
        if (linearLayout5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("controlsBox");
        } else {
            linearLayout2 = linearLayout5;
        }
        GxPage.Row rb = GxPage.row$default(gxModelsPage3, "Active Big", str5, linearLayout2, 0, 8, null);
        GxModelsKt.gxModelsEnable(GxPage.Row.button$default(rb, "Change", false, new Function0() { // from class: com.starpilot.starview.GxModelsPage$$ExternalSyntheticLambda7
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit renderControls$lambda$49;
                renderControls$lambda$49 = GxModelsPage.renderControls$lambda$49(GxModelsPage.this, big);
                return renderControls$lambda$49;
            }
        }, 2, null), selOk);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderControls$lambda$38(GxModelsPage this$0) {
        runAction$default(this$0, "cancel", null, 2, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderControls$lambda$39(GxModelsPage this$0) {
        runAction$default(this$0, "downloadAll", null, 2, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderControls$lambda$40(GxModelsPage this$0) {
        runAction$default(this$0, "refresh", null, 2, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderControls$lambda$45(final List $small, final GxModelsPage this$0) {
        String gxModelsText;
        if ($small.isEmpty()) {
            this$0.toast("No installed on-device models.");
            return Unit.INSTANCE;
        }
        List $this$map$iv = $small;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        for (Object item$iv$iv : $this$map$iv) {
            JSONObject it = (JSONObject) item$iv$iv;
            gxModelsText = GxModelsKt.gxModelsText(it, "label", GxModelsKt.gxModelsText$default(it, "value", null, 4, null));
            String str = gxModelsText;
            if (StringsKt.isBlank(str)) {
                str = GxModelsKt.gxModelsText$default(it, "value", null, 4, null);
            }
            destination$iv$iv.add(((Object) str) + (Intrinsics.areEqual(GxModelsKt.gxModelsText$default(it, "value", null, 4, null), this$0.activeSmallModel) ? "  ✓" : ""));
        }
        this$0.pick("Active Small", (List) destination$iv$iv, new Function1() { // from class: com.starpilot.starview.GxModelsPage$$ExternalSyntheticLambda17
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit renderControls$lambda$45$lambda$44;
                renderControls$lambda$45$lambda$44 = GxModelsPage.renderControls$lambda$45$lambda$44($small, this$0, ((Integer) obj).intValue());
                return renderControls$lambda$45$lambda$44;
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderControls$lambda$45$lambda$44(List $small, GxModelsPage this$0, int i) {
        JSONObject m = (JSONObject) $small.get(i);
        if (!Intrinsics.areEqual(GxModelsKt.gxModelsText$default(m, "value", null, 4, null), this$0.activeSmallModel)) {
            this$0.runAction("select-small", m);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderControls$lambda$49(final GxModelsPage this$0, final List $big) {
        String gxModelsText;
        String str = "  ✓";
        String str2 = "";
        List listOf = CollectionsKt.listOf("None — always use Active Small" + (StringsKt.isBlank(this$0.activeBigModel) ? "  ✓" : ""));
        List $this$map$iv = $big;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        for (Object item$iv$iv : $this$map$iv) {
            JSONObject it = (JSONObject) item$iv$iv;
            String str3 = str;
            String str4 = str2;
            Iterable $this$map$iv2 = $this$map$iv;
            gxModelsText = GxModelsKt.gxModelsText(it, "label", GxModelsKt.gxModelsText$default(it, "value", null, 4, null));
            String str5 = gxModelsText;
            if (StringsKt.isBlank(str5)) {
                str5 = GxModelsKt.gxModelsText$default(it, "value", null, 4, null);
            }
            destination$iv$iv.add(((Object) str5) + (Intrinsics.areEqual(GxModelsKt.gxModelsText$default(it, "value", null, 4, null), this$0.activeBigModel) ? str3 : str4));
            str = str3;
            str2 = str4;
            $this$map$iv = ((List) $this$map$iv2);
        }
        List labels = CollectionsKt.plus((Collection) listOf, destination$iv$iv);
        this$0.pick("Active Big", labels, new Function1() { // from class: com.starpilot.starview.GxModelsPage$$ExternalSyntheticLambda11
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit renderControls$lambda$49$lambda$48;
                renderControls$lambda$49$lambda$48 = GxModelsPage.renderControls$lambda$49$lambda$48(this$0, $big, ((Integer) obj).intValue());
                return renderControls$lambda$49$lambda$48;
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderControls$lambda$49$lambda$48(GxModelsPage this$0, List $big, int i) {
        if (i != 0) {
            JSONObject m = (JSONObject) $big.get(i - 1);
            if (!Intrinsics.areEqual(GxModelsKt.gxModelsText$default(m, "value", null, 4, null), this$0.activeBigModel)) {
                this$0.runAction("select-big", m);
            }
        } else if (!StringsKt.isBlank(this$0.activeBigModel)) {
            runAction$default(this$0, "select-big", null, 2, null);
        }
        return Unit.INSTANCE;
    }

    /* VF: from Vineflower (jadx: Removed duplicated region;incorrect) */
    private final void renderList() {
        Row var33 = null; // declarations Vineflower left out
        LinearLayout var5 = this.listBox;
        Object var4 = null;
        Object var3 = var5;
        if (var5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("listBox");
            var3 = null;
        }

        var3.removeAllViews();
        if (!this.loading) {
            List var14 = this.sorted();
            if (var14.isEmpty()) {
                var3 = this.listBox;
                if (var3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("listBox");
                    var3 = var4;
                }

                var3.addView((View)this.empty("No models match these filters. Try Both or clear the favourite filters."));
            } else {
                boolean var1;
                if (this.busy.length() == 0) {
                    var1 = true;
                } else {
                    var1 = false;
                }

                for (JSONObject var8 : (Iterable<JSONObject>) var14) {
                    String var30 = GxModelsKt.gxModelsText$default(var8, "value", null, 4, null);
                    CharSequence var6 = GxModelsKt.gxModelsText(var8, "label", var30);
                    var4 = var6;
                    if (StringsKt.isBlank(var6)) {
                        var4 = var30;
                    }

                    Object var35 = (String)var4;
                    ArrayList var10 = new ArrayList();
                    if (var8.optBoolean("userFavorite")) {
                        var10.add("★ Your Favorite");
                    }

                    if (var8.optBoolean("communityFavorite")) {
                        var10.add("Community Favorite");
                    }

                    ArrayList var9 = new ArrayList();
                    var9.add(var30);
                    if (var8.optBoolean("builtin")) {
                        var9.add("Built-in");
                    }

                    var9.add(GxModelsKt.gxModelsHardwareLabel(var8));
                    var4 = GxModelsKt.gxModelsText$default(var8, "version", null, 4, null);
                    if (StringsKt.isBlank((CharSequence)var4)) {
                        var4 = null;
                    }

                    if (var4 != null) {
                        var9.add("Version " + var4);
                    }

                    var4 = GxModelsKt.gxModelsText$default(var8, "released", null, 4, null);
                    if (StringsKt.isBlank((CharSequence)var4)) {
                        var4 = null;
                    }

                    if (var4 != null) {
                        var9.add("Released " + var4);
                    }

                    if (var8.optBoolean("partial")) {
                        var9.add("Partial Files");
                    }

                    var4 = GxModelsKt.gxModelsFileSizeText(var8);
                    var9.add("File size: " + var4);
                    if (!var10.isEmpty()) {
                        var4 = CollectionsKt.joinToString(var10, " · ", "", "", -1, "...", null);
                        var4 = var4 + "\n";
                    } else {
                        var4 = "";
                    }

                    String var31 = CollectionsKt.joinToString(var9, " · ", "", "", -1, "...", null);
                    String var37 = var4 + var31;
                    GxPage var36 = this;
                    var5 = this.listBox;
                    var4 = var5;
                    if (var5 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("listBox");
                        var4 = null;
                    }

                    label150: {
                        var33 = GxPage.row$default(var36, ((String) var35), var37, (ViewGroup)var4, 0, 8, null);
                        var4 = this.rowState(var8);
                        switch (var4.hashCode()) {
                            case -1560195700:
                                if (var4.equals("cancellable")) {
                                    GxModelsKt.gxModelsEnable((View)GxPage.Row.button$default(((GxPage.Row) var33), "Cancel", false, new GxModelsPage$$ExternalSyntheticLambda12(this, var8), 2, null), var1);
                                    break label150;
                                }
                                break;
                            case -1422950650:
                                if (var4.equals("active")) {
                                    var33.value("Active model").setTextColor(CarUi.Companion.getACCENT());
                                    break label150;
                                }
                                break;
                            case 3035641:
                                if (var4.equals("busy")) {
                                    var33.value("Busy").setTextColor(CarUi.Companion.getDIM());
                                    break label150;
                                }
                                break;
                            case 29046650:
                                if (var4.equals("installed")) {
                                    boolean var2 = var8.optBoolean("requiresGpu");
                                    if (var2) {
                                        var4 = "Big";
                                    } else {
                                        var4 = "Small";
                                    }

                                    var4 = (View)var33.button("Set Active " + var4, true, new GxModelsPage$$ExternalSyntheticLambda13(this, var2, var8));
                                    if (var1 && !this.selectionUncertain) {
                                        var2 = true;
                                    } else {
                                        var2 = false;
                                    }

                                    GxModelsKt.gxModelsEnable((View)var4, var2);
                                    if (!var8.optBoolean("builtin")) {
                                        var4 = GxPage.Row.button$default(((GxPage.Row) var33), "Delete", false, new GxModelsPage$$ExternalSyntheticLambda14(this, var8), 2, null);
                                        ((TextView) var4).setTextColor(CarUi.Companion.getWARN());
                                        GxModelsKt.gxModelsEnable((View)var4, var1);
                                    }
                                    break label150;
                                }
                        }

                        View var27 = (View)GxPage.Row.button$default(((GxPage.Row) var33), "Download", false, new GxModelsPage$$ExternalSyntheticLambda15(this, var8), 2, null);
                        boolean var12;
                        if (var1 && !this.isOnroad) {
                            var12 = true;
                        } else {
                            var12 = false;
                        }

                        GxModelsKt.gxModelsEnable(var27, var12);
                    }

                    boolean var13 = var8.optBoolean("userFavorite");
                    String var28;
                    if (var13) {
                        var28 = "★";
                    } else {
                        var28 = "☆";
                    }

                    TextView var34 = GxPage.Row.button$default(((GxPage.Row) var33), var28, false, new GxModelsPage$$ExternalSyntheticLambda16(this, var13, var8), 2, null);
                    var34.setMinWidth(this.getUi().dp(64));
                    var34.setTextSize(24.0F);
                    String var29;
                    if (var13) {
                        var29 = "Remove from your favorites";
                    } else {
                        var29 = "Add to your favorites";
                    }

                    var34.setContentDescription(var29);
                    if (var13) {
                        var34.setTextColor(CarUi.Companion.getACCENT());
                    }

                    GxModelsKt.gxModelsEnable((View)var34, var1);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderList$lambda$55(GxModelsPage this$0, JSONObject $m) {
        this$0.runAction("cancel", $m);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderList$lambda$56(GxModelsPage this$0, boolean $gpu, JSONObject $m) {
        this$0.runAction($gpu ? "select-big" : "select-small", $m);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderList$lambda$57(GxModelsPage this$0, JSONObject $m) {
        this$0.runAction("delete", $m);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderList$lambda$59(GxModelsPage this$0, JSONObject $m) {
        this$0.runAction("download", $m);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderList$lambda$60(GxModelsPage this$0, boolean $fav, JSONObject $m) {
        this$0.runAction($fav ? "unfavorite" : "favorite", $m);
        return Unit.INSTANCE;
    }
}
