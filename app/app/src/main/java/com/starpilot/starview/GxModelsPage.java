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

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GxModelsPage(Activity act, GxHost gx) {
        super(act, gx);
        Intrinsics.checkNotNullParameter(act, "act");
        Intrinsics.checkNotNullParameter(gx, "gx");
        this.needsOffroad = true;
        this.prefs = act.getSharedPreferences("starview_gx_models", 0);
        this.loading = true;
        this.error = "";
        this.busy = "";
        this.selectionUncertain = true;
        this.sortMode = "release_date";
        this.userFilter = "all";
        this.communityFilter = "all";
        String str = "both";
        String it = this.prefs.getString("hardwareFilter", "both");
        if (it != null) {
            it = SetsKt.setOf((Object[]) new String[]{"both", "gpu", "comma"}).contains(it) ? it : null;
            if (it != null) {
                str = it;
            }
        }
        this.hardwareFilter = str;
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

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ void refresh$default(GxModelsPage gxModelsPage, Function1 function1, int i, Object obj) {
        if ((i & 1) != 0) {
            function1 = null;
        }
        gxModelsPage.refresh(function1);
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

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0028, code lost:
    
        if (r0 == null) goto L16;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static final void perform$finish(final GxModelsPage this$0, String e, JSONObject o, String fallback) {
        String str;
        String optString;
        if (!this$0.getShown()) {
            this$0.busy = "";
            return;
        }
        if (e != null) {
            this$0.toast(e);
        } else {
            if (o != null && (optString = o.optString("message")) != null) {
                String str2 = optString;
                if (StringsKt.isBlank(str2)) {
                    str2 = null;
                }
                str = str2;
            }
            str = fallback;
            this$0.toast(str);
        }
        this$0.refresh(new Function1() { // from class: com.starpilot.starview.GxModelsPage$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit perform$finish$lambda$29;
                perform$finish$lambda$29 = GxModelsPage.perform$finish$lambda$29(this$0, ((Boolean) obj).booleanValue());
                return perform$finish$lambda$29;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit perform$finish$lambda$29(GxModelsPage this$0, boolean it) {
        this$0.busy = "";
        this$0.render(true);
        return Unit.INSTANCE;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x006e, code lost:
    
        if (r22.equals("favorite") == false) goto L58;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x013d, code lost:
    
        r5 = kotlin.jvm.internal.Intrinsics.areEqual(r22, "favorite");
        r6 = new org.json.JSONArray();
        r7 = r21.models.iterator();
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0150, code lost:
    
        if (r7.hasNext() == false) goto L60;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0152, code lost:
    
        r8 = r7.next();
        r9 = com.starpilot.starview.GxModelsKt.gxModelsText$default(r8, "value", null, 4, null);
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0166, code lost:
    
        if (r8.optBoolean("userFavorite") == false) goto L65;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x016c, code lost:
    
        if (kotlin.jvm.internal.Intrinsics.areEqual(r9, r23) != false) goto L66;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0175, code lost:
    
        if (r9.length() <= 0) goto L52;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0177, code lost:
    
        r11 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x017a, code lost:
    
        if (r11 == false) goto L67;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x017c, code lost:
    
        r6.put(r9);
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0179, code lost:
    
        r11 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0180, code lost:
    
        if (r5 == false) goto L57;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0182, code lost:
    
        r6.put(r23);
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0185, code lost:
    
        com.starpilot.starview.GxPage.send$default(r21, "PUT", "/api/models/preferences", new org.json.JSONObject().put("userFavorites", r6), 0, new com.starpilot.starview.GxModelsPage$$ExternalSyntheticLambda25(r5, r21), 8, null);
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x0077, code lost:
    
        if (r22.equals("select-small") == false) goto L58;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x00fb, code lost:
    
        if (kotlin.jvm.internal.Intrinsics.areEqual(r22, "select-big") == false) goto L37;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00fd, code lost:
    
        r5 = "big";
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x0102, code lost:
    
        r21.selectionUncertain = true;
        r21.selectionWriting = true;
        r21.generation++;
        com.starpilot.starview.GxPage.send$default(r21, "PUT", "/api/models/active", new org.json.JSONObject().put("profile", r5).put("model", r23), 0, new com.starpilot.starview.GxModelsPage$$ExternalSyntheticLambda19(r21, r24), 8, null);
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x0100, code lost:
    
        r5 = "small";
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x00f3, code lost:
    
        if (r22.equals("select-big") == false) goto L58;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x0139, code lost:
    
        if (r22.equals("unfavorite") == false) goto L58;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final void perform(String action, String key, final String label, boolean allowGpu) {
        switch (action.hashCode()) {
            case -1717948619:
                break;
            case -1657124689:
                break;
            case -1367724422:
                if (action.equals("cancel")) {
                    GxPage.send$default(this, "POST", "/api/models/cancel", null, 0L, new Function2() { // from class: com.starpilot.starview.GxModelsPage$$ExternalSyntheticLambda23
                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            Unit perform$lambda$33;
                            perform$lambda$33 = GxModelsPage.perform$lambda$33(GxModelsPage.this, (String) obj, (JSONObject) obj2);
                            return perform$lambda$33;
                        }
                    }, 12, null);
                    break;
                }
                this.busy = "";
                break;
            case -1335458389:
                if (action.equals("delete")) {
                    GxPage.send$default(this, "POST", "/api/models/delete", new JSONObject().put("model", key), 0L, new Function2() { // from class: com.starpilot.starview.GxModelsPage$$ExternalSyntheticLambda24
                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            Unit perform$lambda$34;
                            perform$lambda$34 = GxModelsPage.perform$lambda$34(label, this, (String) obj, (JSONObject) obj2);
                            return perform$lambda$34;
                        }
                    }, 8, null);
                    break;
                }
                this.busy = "";
                break;
            case -1211167751:
                if (action.equals("downloadAll")) {
                    GxPage.send$default(this, "POST", "/api/models/download_all", new JSONObject().put("allowGpuWithoutGpu", allowGpu), 0L, new Function2() { // from class: com.starpilot.starview.GxModelsPage$$ExternalSyntheticLambda21
                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            Unit perform$lambda$32;
                            perform$lambda$32 = GxModelsPage.perform$lambda$32(GxModelsPage.this, (String) obj, (JSONObject) obj2);
                            return perform$lambda$32;
                        }
                    }, 8, null);
                    break;
                }
                this.busy = "";
                break;
            case 951857398:
                break;
            case 1050790300:
                break;
            case 1085444827:
                if (action.equals("refresh")) {
                    GxPage.send$default(this, "POST", "/api/models/refresh_manifest", null, 120L, new Function2() { // from class: com.starpilot.starview.GxModelsPage$$ExternalSyntheticLambda26
                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            Unit perform$lambda$36;
                            perform$lambda$36 = GxModelsPage.perform$lambda$36(GxModelsPage.this, (String) obj, (JSONObject) obj2);
                            return perform$lambda$36;
                        }
                    }, 4, null);
                    break;
                }
                this.busy = "";
                break;
            case 1427818632:
                if (action.equals("download")) {
                    GxPage.send$default(this, "POST", "/api/models/download", new JSONObject().put("model", key).put("allowGpuWithoutGpu", allowGpu), 0L, new Function2() { // from class: com.starpilot.starview.GxModelsPage$$ExternalSyntheticLambda20
                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            Unit perform$lambda$31;
                            perform$lambda$31 = GxModelsPage.perform$lambda$31(label, this, (String) obj, (JSONObject) obj2);
                            return perform$lambda$31;
                        }
                    }, 8, null);
                    break;
                }
                this.busy = "";
                break;
            default:
                this.busy = "";
                break;
        }
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

    /* JADX WARN: Multi-variable type inference failed */
    private final void renderStatus() {
        List<String> groupValues;
        String str;
        LinearLayout linearLayout = this.statusBox;
        Double d = null;
        LinearLayout linearLayout2 = null;
        d = null;
        d = null;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("statusBox");
            linearLayout = null;
        }
        linearLayout.removeAllViews();
        if (this.loading) {
            LinearLayout linearLayout3 = this.statusBox;
            if (linearLayout3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("statusBox");
            } else {
                linearLayout2 = linearLayout3;
            }
            linearLayout2.addView(empty("Loading models..."));
            return;
        }
        LinearLayout linearLayout4 = this.statusBox;
        if (linearLayout4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("statusBox");
            linearLayout4 = null;
        }
        LinearLayout card = card(linearLayout4);
        GxPage.text$default(this, this.sInstalled + " installed · " + this.sMissing + " missing · " + this.sTotal + " total", card, 21.0f, CarUi.Companion.getTEXT(), false, 16, null);
        text("Selected: " + currentLabel(), card, 18.0f, CarUi.Companion.getACCENT(), true);
        if ((this.error.length() > 0) != false) {
            text("Status load failed", card, 17.0f, CarUi.Companion.getWARN(), true);
            GxPage.text$default(this, this.error, card, 15.0f, CarUi.Companion.getWARN(), false, 16, null);
        }
        if (this.isOnroad) {
            GxPage.text$default(this, "Onroad: switching models and downloads are disabled.", card, 17.0f, CarUi.Companion.getWARN(), false, 16, null);
        }
        if (this.downloading) {
            text("Downloading " + downloadTargetLabel(), card, 18.0f, CarUi.Companion.getTEXT(), true);
            if ((this.progressText.length() > 0) != false) {
                GxPage.text$default(this, this.progressText, card, 0.0f, 0, false, 28, null);
            }
            if (this.cancelling) {
                GxPage.text$default(this, "Cancellation requested.", card, 15.0f, CarUi.Companion.getWARN(), false, 16, null);
            }
            ProgressBar progress = progress(card);
            MatchResult find$default = (new Regex("(\\d+(?:\\.\\d+)?)\\s*%")).find(this.progressText, 0);
            if (find$default != null && (groupValues = find$default.getGroupValues()) != null && (str = groupValues.get(1)) != null) {
                d = StringsKt.toDoubleOrNull(str);
            }
            Double d2 = d;
            setProgress(progress, d2 != null ? d2.doubleValue() : -1.0d);
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
                renderControls$lambda$45 = GxModelsPage.renderControls$lambda$45(small, this);
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
            $this$map$iv = $this$map$iv2;
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

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:95:0x02a0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final void renderList() {
        String gxModelsText;
        String gxModelsHardwareLabel;
        String gxModelsFileSizeText;
        LinearLayout linearLayout = this.listBox;
        String str = null;
        LinearLayout linearLayout2 = null;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("listBox");
            linearLayout = null;
        }
        linearLayout.removeAllViews();
        if (this.loading) {
            return;
        }
        List<JSONObject> sorted = sorted();
        if (sorted.isEmpty()) {
            LinearLayout linearLayout3 = this.listBox;
            if (linearLayout3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("listBox");
            } else {
                linearLayout2 = linearLayout3;
            }
            linearLayout2.addView(empty("No models match these filters. Try Both or clear the favourite filters."));
            return;
        }
        boolean z = this.busy.length() == 0;
        for (final JSONObject jSONObject : sorted) {
            String gxModelsText$default = GxModelsKt.gxModelsText$default(jSONObject, "value", str, 4, str);
            gxModelsText = GxModelsKt.gxModelsText(jSONObject, "label", gxModelsText$default);
            String str2 = gxModelsText;
            if (StringsKt.isBlank(str2)) {
                str2 = gxModelsText$default;
            }
            String str3 = str2;
            ArrayList arrayList = new ArrayList();
            if (jSONObject.optBoolean("userFavorite")) {
                arrayList.add("★ Your Favorite");
            }
            if (jSONObject.optBoolean("communityFavorite")) {
                arrayList.add("Community Favorite");
            }
            ArrayList arrayList2 = new ArrayList();
            arrayList2.add(gxModelsText$default);
            if (jSONObject.optBoolean("builtin")) {
                arrayList2.add("Built-in");
            }
            gxModelsHardwareLabel = GxModelsKt.gxModelsHardwareLabel(jSONObject);
            arrayList2.add(gxModelsHardwareLabel);
            String gxModelsText$default2 = GxModelsKt.gxModelsText$default(jSONObject, "version", str, 4, str);
            if (StringsKt.isBlank(gxModelsText$default2)) {
                gxModelsText$default2 = str;
            }
            if (gxModelsText$default2 != null) {
                arrayList2.add("Version " + gxModelsText$default2);
            }
            String gxModelsText$default3 = GxModelsKt.gxModelsText$default(jSONObject, "released", null, 4, null);
            if (StringsKt.isBlank(gxModelsText$default3)) {
                gxModelsText$default3 = null;
            }
            if (gxModelsText$default3 != null) {
                arrayList2.add("Released " + gxModelsText$default3);
            }
            if (jSONObject.optBoolean("partial")) {
                arrayList2.add("Partial Files");
            }
            gxModelsFileSizeText = GxModelsKt.gxModelsFileSizeText(jSONObject);
            arrayList2.add("File size: " + gxModelsFileSizeText);
            String str4 = (!arrayList.isEmpty() ? CollectionsKt.joinToString(arrayList, " · ", "", "", -1, "...", null) + "\n" : "") + CollectionsKt.joinToString(arrayList2, " · ", "", "", -1, "...", null);
            GxModelsPage gxModelsPage = this;
            LinearLayout linearLayout4 = this.listBox;
            if (linearLayout4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("listBox");
                linearLayout4 = null;
            }
            List<JSONObject> list = sorted;
            GxPage.Row row$default = GxPage.row$default(gxModelsPage, str3, str4, linearLayout4, 0, 8, null);
            String rowState = rowState(jSONObject);
            switch (rowState.hashCode()) {
                case -1560195700:
                    if (rowState.equals("cancellable")) {
                        GxModelsKt.gxModelsEnable(GxPage.Row.button$default(row$default, "Cancel", false, new Function0() { // from class: com.starpilot.starview.GxModelsPage$$ExternalSyntheticLambda12
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                Unit renderList$lambda$55;
                                renderList$lambda$55 = GxModelsPage.renderList$lambda$55(GxModelsPage.this, jSONObject);
                                return renderList$lambda$55;
                            }
                        }, 2, null), z);
                        break;
                    }
                    GxModelsKt.gxModelsEnable(GxPage.Row.button$default(row$default, "Download", false, new Function0() { // from class: com.starpilot.starview.GxModelsPage$$ExternalSyntheticLambda15
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            Unit renderList$lambda$59;
                            renderList$lambda$59 = GxModelsPage.renderList$lambda$59(GxModelsPage.this, jSONObject);
                            return renderList$lambda$59;
                        }
                    }, 2, null), (z || this.isOnroad) ? false : true);
                    break;
                case -1422950650:
                    if (rowState.equals("active")) {
                        row$default.value("Active model").setTextColor(CarUi.Companion.getACCENT());
                        break;
                    }
                    if (z) {
                        break;
                    }
                    GxModelsKt.gxModelsEnable(GxPage.Row.button$default(row$default, "Download", false, new Function0() { // from class: com.starpilot.starview.GxModelsPage$$ExternalSyntheticLambda15
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            Unit renderList$lambda$59;
                            renderList$lambda$59 = GxModelsPage.renderList$lambda$59(GxModelsPage.this, jSONObject);
                            return renderList$lambda$59;
                        }
                    }, 2, null), (z || this.isOnroad) ? false : true);
                    break;
                case 3035641:
                    if (rowState.equals("busy")) {
                        row$default.value("Busy").setTextColor(CarUi.Companion.getDIM());
                        break;
                    }
                    GxModelsKt.gxModelsEnable(GxPage.Row.button$default(row$default, "Download", false, new Function0() { // from class: com.starpilot.starview.GxModelsPage$$ExternalSyntheticLambda15
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            Unit renderList$lambda$59;
                            renderList$lambda$59 = GxModelsPage.renderList$lambda$59(GxModelsPage.this, jSONObject);
                            return renderList$lambda$59;
                        }
                    }, 2, null), (z || this.isOnroad) ? false : true);
                    break;
                case 29046650:
                    if (rowState.equals("installed")) {
                        final boolean optBoolean = jSONObject.optBoolean("requiresGpu");
                        GxModelsKt.gxModelsEnable(row$default.button("Set Active " + (optBoolean ? "Big" : "Small"), true, new Function0() { // from class: com.starpilot.starview.GxModelsPage$$ExternalSyntheticLambda13
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                Unit renderList$lambda$56;
                                renderList$lambda$56 = GxModelsPage.renderList$lambda$56(GxModelsPage.this, optBoolean, jSONObject);
                                return renderList$lambda$56;
                            }
                        }), z && !this.selectionUncertain);
                        if (!jSONObject.optBoolean("builtin")) {
                            TextView button$default = GxPage.Row.button$default(row$default, "Delete", false, new Function0() { // from class: com.starpilot.starview.GxModelsPage$$ExternalSyntheticLambda14
                                @Override // kotlin.jvm.functions.Function0
                                public final Object invoke() {
                                    Unit renderList$lambda$57;
                                    renderList$lambda$57 = GxModelsPage.renderList$lambda$57(GxModelsPage.this, jSONObject);
                                    return renderList$lambda$57;
                                }
                            }, 2, null);
                            button$default.setTextColor(CarUi.Companion.getWARN());
                            GxModelsKt.gxModelsEnable(button$default, z);
                            break;
                        }
                    }
                    GxModelsKt.gxModelsEnable(GxPage.Row.button$default(row$default, "Download", false, new Function0() { // from class: com.starpilot.starview.GxModelsPage$$ExternalSyntheticLambda15
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            Unit renderList$lambda$59;
                            renderList$lambda$59 = GxModelsPage.renderList$lambda$59(GxModelsPage.this, jSONObject);
                            return renderList$lambda$59;
                        }
                    }, 2, null), (z || this.isOnroad) ? false : true);
                    break;
                default:
                    GxModelsKt.gxModelsEnable(GxPage.Row.button$default(row$default, "Download", false, new Function0() { // from class: com.starpilot.starview.GxModelsPage$$ExternalSyntheticLambda15
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            Unit renderList$lambda$59;
                            renderList$lambda$59 = GxModelsPage.renderList$lambda$59(GxModelsPage.this, jSONObject);
                            return renderList$lambda$59;
                        }
                    }, 2, null), (z || this.isOnroad) ? false : true);
                    break;
            }
            final boolean optBoolean2 = jSONObject.optBoolean("userFavorite");
            TextView button$default2 = GxPage.Row.button$default(row$default, optBoolean2 ? "★" : "☆", false, new Function0() { // from class: com.starpilot.starview.GxModelsPage$$ExternalSyntheticLambda16
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit renderList$lambda$60;
                    renderList$lambda$60 = GxModelsPage.renderList$lambda$60(GxModelsPage.this, optBoolean2, jSONObject);
                    return renderList$lambda$60;
                }
            }, 2, null);
            button$default2.setMinWidth(getUi().dp(64));
            button$default2.setTextSize(24.0f);
            button$default2.setContentDescription(optBoolean2 ? "Remove from your favorites" : "Add to your favorites");
            if (optBoolean2) {
                button$default2.setTextColor(CarUi.Companion.getACCENT());
            }
            GxModelsKt.gxModelsEnable(button$default2, z);
            sorted = list;
            str = null;
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
