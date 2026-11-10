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

    /* JADX WARN: Removed duplicated region for block: B:28:0x008d  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x010a  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0123  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00d3  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static final void soundPacks$draw(LinearLayout box, CarUi $ui, Handler main, final Galaxy7 g, final Context $ctx, final JSONObject o, String err) {
        int i;
        int i2;
        int i3;
        int i4;
        JSONArray packs;
        String desc;
        int i5;
        box.removeAllViews();
        if (o == null) {
            box.addView($ui.note(err == null ? "Loading…" : err));
            return;
        }
        final JSONObject job = o.optJSONObject("job");
        int i6 = 0;
        boolean running = job != null && SetsKt.setOf((Object[]) new String[]{"downloading", "verifying"}).contains(job.optString("state"));
        JSONArray optJSONArray = o.optJSONArray("packs");
        if (optJSONArray == null) {
            optJSONArray = new JSONArray();
        }
        JSONArray packs2 = optJSONArray;
        int length = packs2.length();
        int i7 = 0;
        while (i7 < length) {
            JSONObject p = packs2.getJSONObject(i7);
            final String id = p.optString("id");
            boolean installed = p.optBoolean("installed");
            if (running) {
                if (Intrinsics.areEqual(job != null ? job.optString("pack") : null, id)) {
                    i = 1;
                    i2 = i;
                    if (i2 == 0) {
                        Intrinsics.checkNotNull(job);
                        i3 = i7;
                        i4 = length;
                        packs = packs2;
                        desc = job.optString("state") + "… " + ((int) ((job.optLong("bytes") * 100.0d) / RangesKt.coerceAtLeast(job.optLong("total"), 1L))) + " %";
                    } else {
                        i3 = i7;
                        i4 = length;
                        packs = packs2;
                        desc = installed ? "Installed - choose it with Sound Pack above" : null;
                    }
                    String optString = p.optString("name", id);
                    Intrinsics.checkNotNullExpressionValue(optString, "optString(...)");
                    LinearLayout r = (LinearLayout) CarUi.row$default($ui, optString, desc, 0, null, null, 28, null).component1();
                    if (i2 == 0) {
                        i5 = 0;
                        r.addView(CarUi.button$default($ui, "Cancel", false, new Function0() { // from class: com.starpilot.starview.GalaxyExtras$$ExternalSyntheticLambda6
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                Unit soundPacks$draw$lambda$2;
                                soundPacks$draw$lambda$2 = GalaxyExtras.soundPacks$draw$lambda$2(g, job, $ctx);
                                return soundPacks$draw$lambda$2;
                            }
                        }, 2, null));
                    } else {
                        i5 = 0;
                        if (!installed) {
                            TextView $this$soundPacks_u24draw_u24lambda_u245 = CarUi.button$default($ui, "Install", false, new Function0() { // from class: com.starpilot.starview.GalaxyExtras$$ExternalSyntheticLambda7
                                @Override // kotlin.jvm.functions.Function0
                                public final Object invoke() {
                                    Unit soundPacks$draw$lambda$4;
                                    soundPacks$draw$lambda$4 = GalaxyExtras.soundPacks$draw$lambda$4(o, $ctx, g, id);
                                    return soundPacks$draw$lambda$4;
                                }
                            }, 2, null);
                            $this$soundPacks_u24draw_u24lambda_u245.setAlpha(running ? 0.45f : 1.0f);
                            $this$soundPacks_u24draw_u24lambda_u245.setEnabled(!running);
                            r.addView($this$soundPacks_u24draw_u24lambda_u245);
                        }
                    }
                    box.addView(r, CarUi.lp$default($ui, i5, i5, 3, null));
                    i7 = i3 + 1;
                    i6 = i5;
                    length = i4;
                    packs2 = packs;
                }
            }
            i = i6;
            i2 = i;
            if (i2 == 0) {
            }
            String optString2 = p.optString("name", id);
            Intrinsics.checkNotNullExpressionValue(optString2, "optString(...)");
            LinearLayout r2 = (LinearLayout) CarUi.row$default($ui, optString2, desc, 0, null, null, 28, null).component1();
            if (i2 == 0) {
            }
            box.addView(r2, CarUi.lp$default($ui, i5, i5, 3, null));
            i7 = i3 + 1;
            i6 = i5;
            length = i4;
            packs2 = packs;
        }
        if (job != null && Intrinsics.areEqual(job.optString("state"), "failed")) {
            box.addView($ui.note("Last download failed: " + job.optString("error")));
        }
        if (!running || !box.isAttachedToWindow()) {
            return;
        }
        main.postDelayed(new Runnable() { // from class: com.starpilot.starview.GalaxyExtras$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                GalaxyExtras.soundPacks$draw$lambda$6();
            }
        }, 1000L);
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
                cloudProvider$load$lambda$17$lambda$16 = GalaxyExtras.cloudProvider$load$lambda$17$lambda$16(can, ids, selected, $ctx, labels, $g, o, $box, $ui, ((Integer) obj).intValue());
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
        private final GalaxyExtras$QuickSelect$poll$1 poll;
        private boolean saving;
        private final CarUi ui;
        private boolean visible;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Type inference failed for: r0v7, types: [com.starpilot.starview.GalaxyExtras$QuickSelect$poll$1] */
        public QuickSelect(Context ctx, CarUi ui) {
            super(ctx);
            Intrinsics.checkNotNullParameter(ctx, "ctx");
            Intrinsics.checkNotNullParameter(ui, "ui");
            this.ui = ui;
            this.g = Galaxy7.Companion.current();
            LinearLayout $this$col_u24lambda_u240 = new LinearLayout(ctx);
            $this$col_u24lambda_u240.setOrientation(1);
            $this$col_u24lambda_u240.setPadding(this.ui.dp(10), this.ui.dp(4), this.ui.dp(24), this.ui.dp(60));
            this.col = $this$col_u24lambda_u240;
            this.last = "";
            this.main = new Handler(Looper.getMainLooper());
            this.poll = new Runnable() { // from class: com.starpilot.starview.GalaxyExtras$QuickSelect$poll$1
                @Override // java.lang.Runnable
                public void run() {
                    boolean z;
                    boolean z2;
                    Handler handler;
                    z = GalaxyExtras.QuickSelect.this.visible;
                    if (z) {
                        z2 = GalaxyExtras.QuickSelect.this.saving;
                        if (!z2) {
                            GalaxyExtras.QuickSelect.this.load();
                        }
                        handler = GalaxyExtras.QuickSelect.this.main;
                        handler.postDelayed(this, 3000L);
                    }
                }
            };
            ScrollView $this$_init__u24lambda_u241 = new ScrollView(ctx);
            $this$_init__u24lambda_u241.addView(this.col);
            addView($this$_init__u24lambda_u241, new FrameLayout.LayoutParams(-1, -1));
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
            final int i2 = 0;
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
                TextView $this$draw_u24lambda_u2411 = CarUi.button$default(carUi2, str3, false, new Function0() { // from class: com.starpilot.starview.GalaxyExtras$QuickSelect$$ExternalSyntheticLambda1
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        Unit draw$lambda$10;
                        draw$lambda$10 = GalaxyExtras.QuickSelect.draw$lambda$10(edit, this, i2);
                        return draw$lambda$10;
                    }
                }, 2, null);
                $this$draw_u24lambda_u2411.setMinWidth(this.ui.dp(220));
                r.addView($this$draw_u24lambda_u2411);
                char c = 3;
                JSONObject d2 = d;
                this.col.addView(r, CarUi.lp$default(this.ui, 0, 0, 3, null));
                if (key != null) {
                    for (Iterator it3 = CollectionsKt.listOf((Object[]) new Pair[]{TuplesKt.to("enabled", "Use this button"), TuplesKt.to("show_onroad", "Show on the driving screen")}).iterator(); it3.hasNext(); it3 = it3) {
                        Pair pair = (Pair) it3.next();
                        final String field = (String) pair.component1();
                        String title = (String) pair.component2();
                        LinearLayout sr = (LinearLayout) CarUi.row$default(this.ui, title, null, 0, null, null, 30, null).component1();
                        Switch sw = this.ui.bigSwitch();
                        sw.setChecked(s.optBoolean(field));
                        sw.setEnabled(edit);
                        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.starpilot.starview.GalaxyExtras$QuickSelect$$ExternalSyntheticLambda2
                            @Override // android.widget.CompoundButton.OnCheckedChangeListener
                            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                                GalaxyExtras.QuickSelect.draw$lambda$12(GalaxyExtras.QuickSelect.this, i2, field, compoundButton, z);
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
                                draw$lambda$14 = GalaxyExtras.QuickSelect.draw$lambda$14(edit, v, this, i3);
                                return draw$lambda$14;
                            }
                        }));
                        vr.addView($this$draw_u24lambda_u2413);
                        vr.addView(this.ui.stepper("+", new Function0() { // from class: com.starpilot.starview.GalaxyExtras$QuickSelect$$ExternalSyntheticLambda4
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                Unit draw$lambda$15;
                                draw$lambda$15 = GalaxyExtras.QuickSelect.draw$lambda$15(edit, v, this, i3);
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
                $this$map$iv2 = $this$map$iv;
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

        /* JADX WARN: Code restructure failed: missing block: B:42:0x00a8, code lost:
        
            if (r8 != null) goto L47;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        private final void update(int i, JSONObject patch) {
            Galaxy7 gx;
            JSONArray src;
            JSONArray src2;
            int i2;
            Object obj;
            String str;
            JSONObject d = this.data;
            if (d == null || (gx = this.g) == null || !d.optBoolean("editable") || this.saving || (src = d.optJSONArray("slots")) == null) {
                return;
            }
            JSONArray slots = new JSONArray();
            int k = 0;
            int length = src.length();
            while (k < length) {
                JSONObject s = new JSONObject(src.getJSONObject(k).toString());
                if (k != i) {
                    src2 = src;
                    i2 = length;
                } else {
                    if (!patch.has("key")) {
                        src2 = src;
                        i2 = length;
                    } else {
                        String nk = patch.isNull("key") ? null : patch.optString("key");
                        Iterable $this$firstOrNull$iv = options();
                        Iterator it = $this$firstOrNull$iv.iterator();
                        while (true) {
                            if (it.hasNext()) {
                                Object element$iv = it.next();
                                JSONObject it2 = (JSONObject) element$iv;
                                src2 = src;
                                if (Intrinsics.areEqual(it2.optString("key"), nk)) {
                                    obj = element$iv;
                                    break;
                                }
                                src = src2;
                            } else {
                                src2 = src;
                                obj = null;
                                break;
                            }
                        }
                        JSONObject opt = (JSONObject) obj;
                        if (nk != null && opt == null) {
                            return;
                        }
                        s.remove("value");
                        if (opt != null) {
                            i2 = length;
                            String optString = opt.optString("label");
                            if (optString != null) {
                                str = StringsKt.take(optString, 32);
                            }
                        } else {
                            i2 = length;
                        }
                        str = "";
                        s.put("label", str);
                        if (opt == null) {
                            s.put("enabled", false);
                            s.put("show_onroad", false);
                        }
                        if (Intrinsics.areEqual(nk, "__starpilot_controller_action__:set_speed")) {
                            s.put("value", 50);
                        }
                    }
                    Iterator<String> keys = patch.keys();
                    Intrinsics.checkNotNullExpressionValue(keys, "keys(...)");
                    while (keys.hasNext()) {
                        String f = keys.next();
                        s.put(f, patch.get(f));
                    }
                }
                if (!Intrinsics.areEqual(s.optString("key"), "__starpilot_controller_action__:set_speed")) {
                    s.remove("value");
                }
                slots.put(s);
                k++;
                length = i2;
                src = src2;
            }
            this.saving = true;
            JSONObject put = new JSONObject().put("revision", d.optString("revision")).put("slots", slots);
            Intrinsics.checkNotNullExpressionValue(put, "put(...)");
            gx.post("/api/favorites/slots", put, new Function2() { // from class: com.starpilot.starview.GalaxyExtras$QuickSelect$$ExternalSyntheticLambda6
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj2, Object obj3) {
                    Unit update$lambda$21;
                    update$lambda$21 = GalaxyExtras.QuickSelect.update$lambda$21(GalaxyExtras.QuickSelect.this, (JSONObject) obj2, (String) obj3);
                    return update$lambda$21;
                }
            });
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
