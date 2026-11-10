package com.starpilot.starview;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import com.starpilot.starview.GalaxyExtras;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.WeakHashMap;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import org.json.JSONArray;
import org.json.JSONObject;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

/* compiled from: GalaxyExtras.kt */
public final class GalaxyExtras {
    public static final GalaxyExtras INSTANCE = new GalaxyExtras();
    private static final WeakHashMap<LinearLayout, Function0<Unit>> refreshers = new WeakHashMap<>();

    private GalaxyExtras() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void toast(Context ctx, String s) {
        Toast.makeText(ctx, s, 1).show();
    }

    public final void soundPacks(final Context ctx, final CarUi ui, LinearLayout col) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(ui, "ui");
        Intrinsics.checkNotNullParameter(col, "col");
        final Galaxy7 g = Galaxy7.Companion.current();
        if (g == null) {
            return;
        }
        final LinearLayout box = new LinearLayout(ctx);
        box.setOrientation(1);
        col.addView(ui.header("Sound packs"));
        col.addView(box, CarUi.lp$default(ui, 0, 0, 3, null));
        final Handler main = new Handler(Looper.getMainLooper());
        refreshers.put(box, new Function0() { // from class: com.starpilot.starview.GalaxyExtras$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit soundPacks$lambda$8;
                soundPacks$lambda$8 = GalaxyExtras.soundPacks$lambda$8(g, box, ui, main, ctx);
                return soundPacks$lambda$8;
            }
        });
        refresh(box);
    }

    /* VF: from Vineflower (jadx: Removed duplicated region;incorrect) */
    private static final void soundPacks$draw(LinearLayout var0, CarUi var1, Handler var2, Galaxy7 var3, Context var4, JSONObject var5, String var6) {
        var0.removeAllViews();
        if (var5 == null) {
            if (var6 == null) {
                var6 = "Loading…";
            }

            var0.addView((View)var1.note(var6));
        } else {
            JSONObject var15 = var5.optJSONObject("job");
            boolean var8;
            if (var15 != null && SetsKt.setOf(new String[]{"downloading", "verifying"}).contains(var15.optString("state"))) {
                var8 = true;
            } else {
                var8 = false;
            }

            JSONArray var14 = var5.optJSONArray("packs");
            JSONArray var19 = var14;
            if (var14 == null) {
                var19 = new JSONArray();
            }

            int var9 = var19.length();
            int var10 = 0;

            for (JSONArray var25 = var19; var10 < var9; var10++) {
                boolean var11;
                boolean var13;
                String var16;
                JSONObject var17;
                label74: {
                    var17 = var25.getJSONObject(var10);
                    var16 = var17.optString("id");
                    var13 = var17.optBoolean("installed");
                    if (var8) {
                        if (var15 != null) {
                            var6 = var15.optString("pack");
                        } else {
                            var6 = null;
                        }

                        if (Intrinsics.areEqual(var6, var16)) {
                            var11 = true;
                            break label74;
                        }
                    }

                    var11 = false;
                }

                if (var11) {
                    Intrinsics.checkNotNull(var15);
                    var6 = var15.optString("state");
                    int var12 = (int)(var15.optLong("bytes") * 100.0 / RangesKt.coerceAtLeast(var15.optLong("total"), 1L));
                    var6 = var6 + "… " + var12 + " %";
                } else if (var13) {
                    var6 = "Installed - choose it with Sound Pack above";
                } else {
                    var6 = null;
                }

                String var27 = var17.optString("name", var16);
                Intrinsics.checkNotNullExpressionValue(var27, "optString(...)");
                LinearLayout var23 = (LinearLayout)CarUi.row$default(var1, var27, var6, 0, null, null, 28, null).component1();
                if (var11) {
                    var23.addView((View)CarUi.button$default(var1, "Cancel", false, new GalaxyExtras$$ExternalSyntheticLambda6(var3, var15, var4), 2, null));
                } else if (!var13) {
                    TextView var26 = CarUi.button$default(var1, "Install", false, new GalaxyExtras$$ExternalSyntheticLambda7(var5, var4, var3, var16), 2, null);
                    float var7;
                    if (var8) {
                        var7 = 0.45F;
                    } else {
                        var7 = 1.0F;
                    }

                    var26.setAlpha(var7);
                    if (!var8) {
                        var13 = true;
                    } else {
                        var13 = false;
                    }

                    var26.setEnabled(var13);
                    var23.addView((View)var26);
                }

                var0.addView((View)var23, (android.view.ViewGroup.LayoutParams)CarUi.lp$default(var1, 0, 0, 3, null));
            }

            if (var15 != null && Intrinsics.areEqual(var15.optString("state"), "failed")) {
                String var18 = var15.optString("error");
                var0.addView((View)var1.note("Last download failed: " + var18));
            }

            if (var8 && var0.isAttachedToWindow()) {
                var2.postDelayed(new GalaxyExtras$$ExternalSyntheticLambda8(), 1000L);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit soundPacks$draw$lambda$2(Galaxy7 $g, JSONObject $job, final Context $ctx) {
        JSONObject jSONObject = new JSONObject();
        Intrinsics.checkNotNull($job);
        JSONObject put = jSONObject.put("job", $job.optString("id"));
        Intrinsics.checkNotNullExpressionValue(put, "put(...)");
        $g.post("/api/sounds/cancel", put, new Function2() { // from class: com.starpilot.starview.GalaxyExtras$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit soundPacks$draw$lambda$2$lambda$1;
                soundPacks$draw$lambda$2$lambda$1 = GalaxyExtras.soundPacks$draw$lambda$2$lambda$1($ctx, (JSONObject) obj, (String) obj2);
                return soundPacks$draw$lambda$2$lambda$1;
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit soundPacks$draw$lambda$2$lambda$1(Context $ctx, JSONObject jSONObject, String e) {
        if (e != null) {
            INSTANCE.toast($ctx, e);
        }
        refresh$default(INSTANCE, null, 1, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit soundPacks$draw$lambda$4(JSONObject $o, final Context $ctx, Galaxy7 $g, String $id) {
        if (!$o.optBoolean("parked")) {
            INSTANCE.toast($ctx, "Sound packs are installed while parked");
            return Unit.INSTANCE;
        }
        JSONObject put = new JSONObject().put("pack", $id);
        Intrinsics.checkNotNullExpressionValue(put, "put(...)");
        $g.post("/api/sounds/download", put, new Function2() { // from class: com.starpilot.starview.GalaxyExtras$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit soundPacks$draw$lambda$4$lambda$3;
                soundPacks$draw$lambda$4$lambda$3 = GalaxyExtras.soundPacks$draw$lambda$4$lambda$3($ctx, (JSONObject) obj, (String) obj2);
                return soundPacks$draw$lambda$4$lambda$3;
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit soundPacks$draw$lambda$4$lambda$3(Context $ctx, JSONObject jSONObject, String e) {
        if (e != null) {
            INSTANCE.toast($ctx, e);
        }
        refresh$default(INSTANCE, null, 1, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void soundPacks$draw$lambda$6() {
        refresh$default(INSTANCE, null, 1, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit soundPacks$lambda$8(final Galaxy7 $g, final LinearLayout $box, final CarUi $ui, final Handler $main, final Context $ctx) {
        $g.get("/api/sounds", new Function2() { // from class: com.starpilot.starview.GalaxyExtras$$ExternalSyntheticLambda10
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit soundPacks$lambda$8$lambda$7;
                soundPacks$lambda$8$lambda$7 = GalaxyExtras.soundPacks$lambda$8$lambda$7($box, $ui, $main, $g, $ctx, (JSONObject) obj, (String) obj2);
                return soundPacks$lambda$8$lambda$7;
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit soundPacks$lambda$8$lambda$7(LinearLayout $box, CarUi $ui, Handler $main, Galaxy7 $g, Context $ctx, JSONObject o, String e) {
        soundPacks$draw($box, $ui, $main, $g, $ctx, o, e);
        return Unit.INSTANCE;
    }

    private final void refresh(LinearLayout box) {
        WeakHashMap<LinearLayout, Function0<Unit>> weakHashMap = refreshers;
        if (box != null) {
            Function0<Unit> function0 = weakHashMap.get(box);
            if (function0 != null) {
                function0.invoke();
                return;
            }
            return;
        }
        Collection<Function0<Unit>> values = weakHashMap.values();
        Intrinsics.checkNotNullExpressionValue(values, "<get-values>(...)");
        Iterable $this$forEach$iv = CollectionsKt.toList(values);
        for (Object element$iv : $this$forEach$iv) {
            Function0 it = (Function0) element$iv;
            it.invoke();
        }
    }

    static /* synthetic */ void refresh$default(GalaxyExtras galaxyExtras, LinearLayout linearLayout, int i, Object obj) {
        if ((i & 1) != 0) {
            linearLayout = null;
        }
        galaxyExtras.refresh(linearLayout);
    }

    public final void cloudProvider(Context ctx, CarUi ui, LinearLayout col) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(ui, "ui");
        Intrinsics.checkNotNullParameter(col, "col");
        Galaxy7 g = Galaxy7.Companion.current();
        if (g == null) {
            return;
        }
        LinearLayout box = new LinearLayout(ctx);
        box.setOrientation(1);
        col.addView(ui.header("Cloud provider"));
        col.addView(box, CarUi.lp$default(ui, 0, 0, 3, null));
        cloudProvider$load(g, box, ui, ctx);
    }

    private static final void cloudProvider$load(final Galaxy7 g, final LinearLayout box, final CarUi $ui, final Context $ctx) {
        g.get("/api/connect/provider", new Function2() { // from class: com.starpilot.starview.GalaxyExtras$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit cloudProvider$load$lambda$17;
                cloudProvider$load$lambda$17 = GalaxyExtras.cloudProvider$load$lambda$17(box, $ui, $ctx, g, (JSONObject) obj, (String) obj2);
                return cloudProvider$load$lambda$17;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit cloudProvider$load$lambda$17(final LinearLayout $box, final CarUi $ui, final Context $ctx, final Galaxy7 $g, final JSONObject o, String err) {
        Function1 function1;
        $box.removeAllViews();
        if (o == null) {
            $box.addView($ui.note(err == null ? "Cloud provider is unavailable" : err));
            return Unit.INSTANCE;
        }
        JSONArray optJSONArray = o.optJSONArray("providers");
        if (optJSONArray == null) {
            optJSONArray = new JSONArray();
        }
        JSONArray provs = optJSONArray;
        Iterable $this$map$iv = RangesKt.until(0, provs.length());
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        Iterator<Integer> it = $this$map$iv.iterator();
        while (it.hasNext()) {
            int item$iv$iv = ((IntIterator) it).nextInt();
            destination$iv$iv.add(provs.getJSONObject(item$iv$iv).optString("id"));
        }
        final List ids = (List) destination$iv$iv;
        Iterable $this$map$iv2 = RangesKt.until(0, provs.length());
        Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv2, 10));
        Iterator<Integer> it2 = $this$map$iv2.iterator();
        while (it2.hasNext()) {
            int item$iv$iv2 = ((IntIterator) it2).nextInt();
            destination$iv$iv2.add(provs.getJSONObject(item$iv$iv2).optString("label"));
        }
        final List labels = (List) destination$iv$iv2;
        final String selected = o.optString("selected");
        final boolean can = o.optBoolean("canSelect");
        LinearLayout $this$cloudProvider_u24load_u24lambda_u2417_u24lambda_u2413 = new LinearLayout($ctx);
        $this$cloudProvider_u24load_u24lambda_u2417_u24lambda_u2413.setOrientation(1);
        $this$cloudProvider_u24load_u24lambda_u2417_u24lambda_u2413.setBackground(CarUi.rounded$default($ui, CarUi.Companion.getCARD(), 0, null, 6, null));
        $this$cloudProvider_u24load_u24lambda_u2417_u24lambda_u2413.setPadding($ui.dp(20), $ui.dp(14), $ui.dp(18), $ui.dp(14));
        LinearLayout tr = (LinearLayout) CarUi.row$default($ui, "Upload drives to", can ? "Applies after the next reboot (no automatic reboot)." : "Change it while parked with the car off.", 0, null, null, 28, null).component1();
        tr.setBackground(null);
        tr.setPadding(0, 0, 0, 0);
        tr.setMinimumHeight(0);
        $this$cloudProvider_u24load_u24lambda_u2417_u24lambda_u2413.addView(tr);
        Pair<LinearLayout, Function1<Integer, Unit>> chips = $ui.chips(labels, new Function1() { // from class: com.starpilot.starview.GalaxyExtras$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit cloudProvider$load$lambda$17$lambda$16;
                cloudProvider$load$lambda$17$lambda$16 = GalaxyExtras.cloudProvider$load$lambda$17$lambda$16(can, ids, selected, $ctx, labels, $g, o, $box, $ui, ((Number) obj).intValue());
                return cloudProvider$load$lambda$17$lambda$16;
            }
        });
        LinearLayout bar = chips.component1();
        Function1 pt = chips.component2();
        bar.setPadding(0, $ui.dp(10), 0, 0);
        $this$cloudProvider_u24load_u24lambda_u2417_u24lambda_u2413.addView(bar);
        if (pt == null) {
            Intrinsics.throwUninitializedPropertyAccessException("paint");
            function1 = null;
        } else {
            function1 = pt;
        }
        function1.invoke(Integer.valueOf(ids.indexOf(selected)));
        $this$cloudProvider_u24load_u24lambda_u2417_u24lambda_u2413.setAlpha(can ? 1.0f : 0.6f);
        $box.addView($this$cloudProvider_u24load_u24lambda_u2417_u24lambda_u2413);
        if (o.optBoolean("restartRequired")) {
            String str = (String) CollectionsKt.getOrNull(labels, ids.indexOf(selected));
            if (str == null) {
                str = selected;
            }
            $box.addView($ui.note(str + " applies after the next reboot."));
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit cloudProvider$load$lambda$17$lambda$16(boolean $can, final List $ids, String $selected, final Context $ctx, List $labels, final Galaxy7 $g, final JSONObject $o, final LinearLayout $box, final CarUi $ui, final int i) {
        if ($can && !Intrinsics.areEqual($ids.get(i), $selected)) {
            new AlertDialog.Builder($ctx).setMessage("Use " + $labels.get(i) + " after the next device reboot? Going back to comma restores its saved cloud identity.").setPositiveButton("Use " + $labels.get(i), new DialogInterface.OnClickListener() { // from class: com.starpilot.starview.GalaxyExtras$$ExternalSyntheticLambda5
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i2) {
                    GalaxyExtras.cloudProvider$load$lambda$17$lambda$16$lambda$15($g, $ids, i, $o, $ctx, $box, $ui, dialogInterface, i2);
                }
            }).setNegativeButton("Cancel", (DialogInterface.OnClickListener) null).show();
            return Unit.INSTANCE;
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void cloudProvider$load$lambda$17$lambda$16$lambda$15(final Galaxy7 $g, List $ids, int $i, JSONObject $o, final Context $ctx, final LinearLayout $box, final CarUi $ui, DialogInterface dialogInterface, int i) {
        JSONObject put = new JSONObject().put("provider", $ids.get($i)).put("revision", $o.optString("revision")).put("confirmed", true);
        Intrinsics.checkNotNullExpressionValue(put, "put(...)");
        $g.post("/api/connect/provider", put, new Function2() { // from class: com.starpilot.starview.GalaxyExtras$$ExternalSyntheticLambda9
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit cloudProvider$load$lambda$17$lambda$16$lambda$15$lambda$14;
                cloudProvider$load$lambda$17$lambda$16$lambda$15$lambda$14 = GalaxyExtras.cloudProvider$load$lambda$17$lambda$16$lambda$15$lambda$14($ctx, $g, $box, $ui, (JSONObject) obj, (String) obj2);
                return cloudProvider$load$lambda$17$lambda$16$lambda$15$lambda$14;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit cloudProvider$load$lambda$17$lambda$16$lambda$15$lambda$14(Context $ctx, Galaxy7 $g, LinearLayout $box, CarUi $ui, JSONObject jSONObject, String e) {
        if (e != null) {
            INSTANCE.toast($ctx, e);
        }
        cloudProvider$load($g, $box, $ui, $ctx);
        return Unit.INSTANCE;
    }

    /* compiled from: GalaxyExtras.kt */
    public static final class QuickSelect extends FrameLayout {
        public static final Companion Companion = new Companion(null);
        public static final String SET_SPEED = "__starpilot_controller_action__:set_speed";
        private final LinearLayout col;
        private JSONObject data;
        private final Galaxy7 g;
        private String last;
        private final Handler main;
        private final Runnable poll;
        private boolean saving;
        private final CarUi ui;
        private boolean visible;

        /* VF: from Vineflower (jadx: Type inference failed) */
        public QuickSelect(Context var1, CarUi var2) {
            super(var1);
            Intrinsics.checkNotNullParameter(var1, "ctx");
            Intrinsics.checkNotNullParameter(var2, "ui");
            this.ui = var2;
            this.g = Galaxy7.Companion.current();
            LinearLayout var4 = new LinearLayout(var1);
            var4.setOrientation(1);
            var4.setPadding(this.ui.dp(10), this.ui.dp(4), this.ui.dp(24), this.ui.dp(60));
            this.col = var4;
            this.last = "";
            this.main = new Handler(Looper.getMainLooper());
            this.poll = new Runnable() {
                final GalaxyExtras.QuickSelect this$0 = GalaxyExtras.QuickSelect.this;

                @Override
                public void run() {
                    if (this.this$0.visible) {
                        if (!this.this$0.saving) {
                            this.this$0.load();
                        }

                        this.this$0.main.postDelayed(this, 3000L);
                    }
                }
            };
            ScrollView var3 = new ScrollView(var1);
            var3.addView((View)this.col);
            this.addView((View)var3, (android.view.ViewGroup.LayoutParams)(new android.widget.FrameLayout.LayoutParams(-1, -1)));
        }

        public final void show() {
            this.visible = true;
            load();
            this.main.removeCallbacks(this.poll);
            this.main.postDelayed(this.poll, 3000L);
        }

        public final void hide() {
            this.visible = false;
            this.main.removeCallbacks(this.poll);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void load() {
            Galaxy7 gx = this.g;
            if (gx == null) {
                QuickSelect $this$load_u24lambda_u242 = this;
                $this$load_u24lambda_u242.col.removeAllViews();
                $this$load_u24lambda_u242.col.addView($this$load_u24lambda_u242.ui.note("Not connected to the comma."));
                return;
            }
            gx.get("/api/favorites/slots", new Function2() { // from class: com.starpilot.starview.GalaxyExtras$QuickSelect$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    Unit load$lambda$3;
                    load$lambda$3 = GalaxyExtras.QuickSelect.load$lambda$3(GalaxyExtras.QuickSelect.this, (JSONObject) obj, (String) obj2);
                    return load$lambda$3;
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit load$lambda$3(QuickSelect this$0, JSONObject o, String err) {
            if (o != null) {
                this$0.data = o;
                this$0.draw();
            } else if (this$0.data == null) {
                this$0.col.removeAllViews();
                this$0.col.addView(this$0.ui.note(err == null ? "Galaxy didn't answer" : err));
            }
            return Unit.INSTANCE;
        }

        private final List<JSONObject> options() {
            JSONArray a;
            JSONObject jSONObject = this.data;
            if (jSONObject == null || (a = jSONObject.optJSONArray("options")) == null) {
                return CollectionsKt.emptyList();
            }
            Iterable $this$map$iv = RangesKt.until(0, a.length());
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            Iterator<Integer> it = $this$map$iv.iterator();
            while (it.hasNext()) {
                int item$iv$iv = ((IntIterator) it).nextInt();
                destination$iv$iv.add(a.getJSONObject(item$iv$iv));
            }
            return (List) destination$iv$iv;
        }

        private final void draw() {
            Object element$iv;
            String str;
            String str2;
            String str3;
            String sig;
            JSONArray slots;
            JSONArray states;
            int i;
            String optString;
            String optString2;
            JSONObject jSONObject = this.data;
            if (jSONObject == null) {
                return;
            }
            JSONObject d = jSONObject;
            String sig2 = new StringBuilder().append(d.optJSONArray("slots")).append(d.optJSONArray("states")).append(d.optBoolean("editable")).toString();
            if (Intrinsics.areEqual(sig2, this.last)) {
                return;
            }
            this.last = sig2;
            this.col.removeAllViews();
            this.col.addView(this.ui.note("Three buttons on the comma's driving screen (and wheel buttons set to Quick Select 1-3). Pick what each one does."));
            if (!d.optBoolean("valid", true)) {
                this.col.addView(this.ui.note("The saved Quick Select was damaged; saving fixes it."));
            }
            JSONArray optJSONArray = d.optJSONArray("slots");
            if (optJSONArray == null) {
                return;
            }
            JSONArray slots2 = optJSONArray;
            JSONArray states2 = d.optJSONArray("states");
            final boolean edit = d.optBoolean("editable");
            int length = slots2.length();
            int i2 = 0;
            while (i2 < length) {
                JSONObject s = slots2.getJSONObject(i2);
                JSONObject st = states2 != null ? states2.optJSONObject(i2) : null;
                this.col.addView(this.ui.header("Quick Select " + (i2 + 1)));
                String key = s.isNull("key") ? null : s.optString("key");
                Iterable $this$firstOrNull$iv = options();
                Iterator it = $this$firstOrNull$iv.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        element$iv = null;
                        break;
                    }
                    element$iv = it.next();
                    JSONObject it2 = (JSONObject) element$iv;
                    Iterable $this$firstOrNull$iv2 = $this$firstOrNull$iv;
                    if (Intrinsics.areEqual(it2.optString("key"), key)) {
                        break;
                    } else {
                        $this$firstOrNull$iv = $this$firstOrNull$iv2;
                    }
                }
                JSONObject opt = (JSONObject) element$iv;
                CarUi carUi = this.ui;
                String[] strArr = new String[2];
                if (st == null || (optString2 = st.optString("stateLabel")) == null) {
                    str = null;
                } else {
                    String str4 = optString2;
                    if (StringsKt.isBlank(str4)) {
                        str4 = null;
                    }
                    str = str4;
                }
                strArr[0] = str;
                if (st == null || (optString = st.optString("reason")) == null) {
                    str2 = null;
                } else {
                    String str5 = optString;
                    if (StringsKt.isBlank(str5)) {
                        str5 = null;
                    }
                    str2 = str5;
                }
                strArr[1] = str2;
                String joinToString$default = CollectionsKt.joinToString(CollectionsKt.listOfNotNull((Object[]) strArr), " · ", "", "", -1, "...", null);
                if (StringsKt.isBlank(joinToString$default)) {
                    joinToString$default = null;
                }
                LinearLayout r = (LinearLayout) CarUi.row$default(carUi, "Action", joinToString$default, 0, null, null, 28, null).component1();
                CarUi carUi2 = this.ui;
                if (opt == null || (str3 = opt.optString("label")) == null) {
                    str3 = "None";
                }
                final var i2$f1 = i2;
                TextView $this$draw_u24lambda_u2411 = CarUi.button$default(carUi2, str3, false, new Function0() { // from class: com.starpilot.starview.GalaxyExtras$QuickSelect$$ExternalSyntheticLambda1
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        Unit draw$lambda$10;
                        draw$lambda$10 = GalaxyExtras.QuickSelect.draw$lambda$10(edit, QuickSelect.this, i2$f1);
                        return draw$lambda$10;
                    }
                }, 2, null);
                $this$draw_u24lambda_u2411.setMinWidth(this.ui.dp(220));
                r.addView($this$draw_u24lambda_u2411);
                char c = 3;
                JSONObject d2 = d;
                this.col.addView(r, CarUi.lp$default(this.ui, 0, 0, 3, null));
                if (key != null) {
                    for (Iterator it3 = CollectionsKt.listOf(new Pair[]{TuplesKt.to("enabled", "Use this button"), TuplesKt.to("show_onroad", "Show on the driving screen")}).iterator(); it3.hasNext(); it3 = it3) {
                        Pair pair = (Pair) it3.next();
                        final String field = (String) pair.component1();
                        String title = (String) pair.component2();
                        LinearLayout sr = (LinearLayout) CarUi.row$default(this.ui, title, null, 0, null, null, 30, null).component1();
                        Switch sw = this.ui.bigSwitch();
                        sw.setChecked(s.optBoolean(field));
                        sw.setEnabled(edit);
                        final var i2$f = i2;
                        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.starpilot.starview.GalaxyExtras$QuickSelect$$ExternalSyntheticLambda2
                            @Override // android.widget.CompoundButton.OnCheckedChangeListener
                            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                                GalaxyExtras.QuickSelect.draw$lambda$12(GalaxyExtras.QuickSelect.this, i2$f, field, compoundButton, z);
                            }
                        });
                        sr.addView(sw);
                        this.col.addView(sr, CarUi.lp$default(this.ui, 0, 0, 3, null));
                        c = 3;
                        sig2 = sig2;
                    }
                    sig = sig2;
                    if (Intrinsics.areEqual(key, "__starpilot_controller_action__:set_speed")) {
                        LinearLayout vr = (LinearLayout) CarUi.row$default(this.ui, "Set speed", "5 to 145", 0, null, null, 28, null).component1();
                        final double v = s.optDouble("value", 50.0d);
                        TextView $this$draw_u24lambda_u2413 = new TextView(getContext());
                        $this$draw_u24lambda_u2413.setText(GalaxyRow.Companion.fmt(v));
                        $this$draw_u24lambda_u2413.setTextSize(22.0f);
                        $this$draw_u24lambda_u2413.setTextColor(CarUi.Companion.getTEXT());
                        $this$draw_u24lambda_u2413.setMinWidth(this.ui.dp(70));
                        $this$draw_u24lambda_u2413.setGravity(17);
                        slots = slots2;
                        states = states2;
                        i = length;
                        final int i3 = i2;
                        vr.addView(this.ui.stepper("−", new Function0() { // from class: com.starpilot.starview.GalaxyExtras$QuickSelect$$ExternalSyntheticLambda3
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                Unit draw$lambda$14;
                                draw$lambda$14 = GalaxyExtras.QuickSelect.draw$lambda$14(edit, v, QuickSelect.this, i3);
                                return draw$lambda$14;
                            }
                        }));
                        vr.addView($this$draw_u24lambda_u2413);
                        vr.addView(this.ui.stepper("+", new Function0() { // from class: com.starpilot.starview.GalaxyExtras$QuickSelect$$ExternalSyntheticLambda4
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                Unit draw$lambda$15;
                                draw$lambda$15 = GalaxyExtras.QuickSelect.draw$lambda$15(edit, v, QuickSelect.this, i3);
                                return draw$lambda$15;
                            }
                        }));
                        this.col.addView(vr, CarUi.lp$default(this.ui, 0, 0, 3, null));
                    } else {
                        slots = slots2;
                        states = states2;
                        i = length;
                    }
                } else {
                    sig = sig2;
                    slots = slots2;
                    states = states2;
                    i = length;
                }
                i2++;
                sig2 = sig;
                slots2 = slots;
                d = d2;
                states2 = states;
                length = i;
            }
            if (edit) {
                return;
            }
            this.col.addView(this.ui.note("Quick Select can't be changed right now."));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit draw$lambda$10(boolean $edit, QuickSelect this$0, int $i) {
            if ($edit) {
                this$0.pick($i);
            }
            return Unit.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void draw$lambda$12(QuickSelect this$0, int $i, String $field, CompoundButton b, boolean on) {
            if (this$0.saving) {
                b.setChecked(!on);
                return;
            }
            JSONObject put = new JSONObject().put($field, on);
            Intrinsics.checkNotNullExpressionValue(put, "put(...)");
            this$0.update($i, put);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit draw$lambda$14(boolean $edit, double $v, QuickSelect this$0, int $i) {
            if ($edit) {
                double d = 5;
                if ($v - d >= 5.0d) {
                    JSONObject put = new JSONObject().put("value", $v - d);
                    Intrinsics.checkNotNullExpressionValue(put, "put(...)");
                    this$0.update($i, put);
                }
            }
            return Unit.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit draw$lambda$15(boolean $edit, double $v, QuickSelect this$0, int $i) {
            if ($edit) {
                double d = 5;
                if ($v + d <= 145.0d) {
                    JSONObject put = new JSONObject().put("value", d + $v);
                    Intrinsics.checkNotNullExpressionValue(put, "put(...)");
                    this$0.update($i, put);
                }
            }
            return Unit.INSTANCE;
        }

        private final void pick(final int i) {
            Iterable $this$map$iv;
            String str;
            Iterable $this$filter$iv = options();
            Collection destination$iv$iv = new ArrayList();
            for (Object element$iv$iv : $this$filter$iv) {
                if (((JSONObject) element$iv$iv).optBoolean("available", true)) {
                    destination$iv$iv.add(element$iv$iv);
                }
            }
            final List opts = (List) destination$iv$iv;
            List listOf = CollectionsKt.listOf("None");
            List $this$map$iv2 = opts;
            Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv2, 10));
            for (Object item$iv$iv : $this$map$iv2) {
                JSONObject o = (JSONObject) item$iv$iv;
                String optString = o.optString("label");
                String it = o.optString("section");
                Intrinsics.checkNotNull(it);
                if (StringsKt.isBlank(it)) {
                    $this$map$iv = $this$map$iv2;
                    str = "";
                } else {
                    $this$map$iv = $this$map$iv2;
                    str = "  (" + it + ")";
                }
                destination$iv$iv2.add(optString + ((Object) str));
                $this$map$iv2 = ((List) $this$map$iv);
            }
            Collection labels = CollectionsKt.plus((Collection) listOf, destination$iv$iv2);
            Collection $this$toTypedArray$iv = labels;
            new AlertDialog.Builder(getContext()).setTitle("Quick Select " + (i + 1)).setItems((CharSequence[]) $this$toTypedArray$iv.toArray(new String[0]), new DialogInterface.OnClickListener() { // from class: com.starpilot.starview.GalaxyExtras$QuickSelect$$ExternalSyntheticLambda5
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i2) {
                    GalaxyExtras.QuickSelect.pick$lambda$19(GalaxyExtras.QuickSelect.this, i, opts, dialogInterface, i2);
                }
            }).setNegativeButton("Cancel", (DialogInterface.OnClickListener) null).show();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void pick$lambda$19(QuickSelect this$0, int $i, List $opts, DialogInterface dialogInterface, int w) {
            JSONObject put = new JSONObject().put("key", w == 0 ? JSONObject.NULL : ((JSONObject) $opts.get(w - 1)).optString("key"));
            Intrinsics.checkNotNullExpressionValue(put, "put(...)");
            this$0.update($i, put);
        }

        /* VF: from Vineflower (jadx: Code restructure failed;incorrect) */
        private final void update(int var1, JSONObject var2) {
        String var16 = null; // declarations Vineflower left out
            JSONObject var10 = this.data;
            if (var10 != null) {
                Galaxy7 var9 = this.g;
                if (var9 != null) {
                    if (var10.optBoolean("editable") && !this.saving) {
                        JSONArray var5 = var10.optJSONArray("slots");
                        if (var5 != null) {
                            JSONArray var11 = new JSONArray();
                            int var4 = 0;
                            int var3 = var5.length();

                            while (var4 < var3) {
                                JSONObject var12 = new JSONObject(var5.getJSONObject(var4).toString());
                                JSONArray var17;
                                if (var4 != var1) {
                                    var17 = var5;
                                } else {
                                    if (var2.has("key")) {
                                        String var6;
                                        if (var2.isNull("key")) {
                                            var6 = null;
                                        } else {
                                            var6 = var2.optString("key");
                                        }

                                        Iterator var7 = this.options().iterator();

                                        Object var8;
                                        do {
                                            if (!var7.hasNext()) {
                                                var8 = null;
                                                break;
                                            }

                                            var8 = var7.next();
                                        } while (!Intrinsics.areEqual(((JSONObject)var8).optString("key"), var6));

                                        JSONArray var19 = var5;
                                        JSONObject var13 = (JSONObject)var8;
                                        if (var6 != null && var13 == null) {
                                            return;
                                        }

                                        label71: {
                                            var12.remove("value");
                                            if (var13 != null) {
                                                String var15 = var13.optString("label");
                                                if (var15 != null) {
                                                    var8 = StringsKt.take(var15, 32);
                                                    var16 = (String)var8;
                                                    if (var8 != null) {
                                                        break label71;
                                                    }
                                                }
                                            }

                                            var16 = "";
                                        }

                                        var12.put("label", var16);
                                        if (var13 == null) {
                                            var12.put("enabled", false);
                                            var12.put("show_onroad", false);
                                        }

                                        var5 = var5;
                                        if (Intrinsics.areEqual(var6, "__starpilot_controller_action__:set_speed")) {
                                            var12.put("value", 50);
                                            var5 = var19;
                                        }
                                    }

                                    Iterator var20 = var2.keys();
                                    Intrinsics.checkNotNullExpressionValue(var20, "keys(...)");

                                    while (true) {
                                        var17 = var5;
                                        if (!var20.hasNext()) {
                                            break;
                                        }

                                        String var18 = (String)var20.next();
                                        var12.put(var18, var2.get(var18));
                                    }
                                }

                                if (!Intrinsics.areEqual(var12.optString("key"), "__starpilot_controller_action__:set_speed")) {
                                    var12.remove("value");
                                }

                                var11.put(var12);
                                var4++;
                                var5 = var17;
                            }

                            this.saving = true;
                            var2 = new JSONObject().put("revision", var10.optString("revision")).put("slots", var11);
                            Intrinsics.checkNotNullExpressionValue(var2, "put(...)");
                            var9.post("/api/favorites/slots", var2, new GalaxyExtras$QuickSelect$$ExternalSyntheticLambda6(this));
                        }
                    }
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit update$lambda$21(QuickSelect this$0, JSONObject o, String err) {
            this$0.saving = false;
            if (o == null || !o.has("slots")) {
                GalaxyExtras galaxyExtras = GalaxyExtras.INSTANCE;
                Context context = this$0.getContext();
                Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
                galaxyExtras.toast(context, err == null ? "Not saved" : err);
                this$0.last = "";
                this$0.load();
            } else {
                this$0.data = o;
                this$0.last = "";
                this$0.draw();
            }
            return Unit.INSTANCE;
        }

        /* compiled from: GalaxyExtras.kt */
        public static final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }
        }
    }
}
