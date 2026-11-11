package com.starpilot.starview;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.starpilot.starview.CarUi;
import com.starpilot.starview.HudLayout;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.ArrayDeque;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import android.graphics.Paint.Style;
import android.graphics.PathEffect;
import android.view.ViewGroup.LayoutParams;

/* compiled from: HudEditor.kt */
public final class HudEditor extends FrameLayout {
    private final LinearLayout content;
    private final float d;
    private final HudLayout layout;
    private final Function0<Unit> onChange;
    private final Function0<Unit> onDone;
    private final Preview preview;
    private final FrameLayout root;
    private HudLayout.Item selected;
    private int tab;
    private final ArrayList<TextView> tabBtns;
    private final CarUi ui;
    private final ArrayDeque<String> undo;
    private TextView undoBtn;

    /* VF: from Vineflower (jadx: ) */
    public HudEditor(Context var1, FrameLayout var2, HudLayout var3, Function0<Unit> var4, Function0<Unit> var5) {
        super(var1);
        Intrinsics.checkNotNullParameter(var1, "ctx");
        Intrinsics.checkNotNullParameter(var2, "root");
        Intrinsics.checkNotNullParameter(var3, "layout");
        Intrinsics.checkNotNullParameter(var4, "onChange");
        Intrinsics.checkNotNullParameter(var5, "onDone");
        this.root = var2;
        this.layout = var3;
        this.onChange = var4;
        this.onDone = var5;
        this.ui = new CarUi(var1, false, 2, null);
        this.d = var1.getResources().getDisplayMetrics().density;
        this.undo = new ArrayDeque<>();
        this.preview = new HudEditor.Preview(this, var1);
        LinearLayout var6 = new LinearLayout(var1);
        var6.setOrientation(1);
        this.content = var6;
        this.tabBtns = new ArrayList<>();
        this.setBackgroundColor(CarUi.Companion.getBG());
        this.setClickable(true);
        this.setTranslationZ(this.dpf(120.0F));
        LinearLayout var7 = new LinearLayout(var1);
        var7.setOrientation(0);
        // 2.85.4: a bigger preview of the driving screen, a narrower panel beside it
        var7.addView(this.preview, (android.view.ViewGroup.LayoutParams)(new android.widget.LinearLayout.LayoutParams(0, -1, 0.72F)));
        var7.addView(this.panel(var1), (android.view.ViewGroup.LayoutParams)(new android.widget.LinearLayout.LayoutParams(0, -1, 0.28F)));
        this.addView((View)var7, (android.view.ViewGroup.LayoutParams)(new android.widget.FrameLayout.LayoutParams(-1, -1)));
        this.refresh();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final float dpf(float v) {
        return this.d * v;
    }

    public final void finish() {
        this.layout.save();
        this.onDone.invoke();
    }

    private final void presets() {
        HudPresets hudPresets = HudPresets.INSTANCE;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        final Map saved = hudPresets.saved(context);
        Iterable $this$map$iv = this.layout.getItems();
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        for (Object item$iv$iv : $this$map$iv) {
            HudLayout.Item it = (HudLayout.Item) item$iv$iv;
            destination$iv$iv.add(it.getKey());
        }
        final List keys = (List) destination$iv$iv;
        List listOf = CollectionsKt.listOf(new String[]{"Highway focus (only what matters)", "Everything on", "Cockpit (cards in a rail on the right)"});
        Iterable $this$map$iv2 = saved.keySet();
        Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv2, 10));
        for (Object item$iv$iv2 : $this$map$iv2) {
            String it2 = (String) item$iv$iv2;
            destination$iv$iv2.add("★ " + it2);
        }
        final List names = CollectionsKt.plus((Collection) CollectionsKt.plus((Collection) CollectionsKt.plus((Collection) listOf, destination$iv$iv2), (Iterable) CollectionsKt.listOf("Save this layout as…")), (Iterable) (!saved.isEmpty() ? CollectionsKt.listOf("Delete a saved layout…") : CollectionsKt.emptyList()));
        List $this$toTypedArray$iv = names;
        new AlertDialog.Builder(getContext()).setTitle("Driving screen layouts").setItems((CharSequence[]) $this$toTypedArray$iv.toArray(new String[0]), new DialogInterface.OnClickListener() { // from class: com.starpilot.starview.HudEditor$$ExternalSyntheticLambda4
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                HudEditor.presets$lambda$10(HudEditor.this, keys, saved, names, dialogInterface, i);
            }
        }).setNegativeButton("Cancel", (DialogInterface.OnClickListener) null).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void presets$lambda$10(final HudEditor this$0, List $keys, final Map $saved, List $names, DialogInterface dialogInterface, int i) {
        Function1 apply = new Function1() { // from class: com.starpilot.starview.HudEditor$$ExternalSyntheticLambda13
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit presets$lambda$10$lambda$4;
                presets$lambda$10$lambda$4 = HudEditor.presets$lambda$10$lambda$4(this$0, (String) obj);
                return presets$lambda$10$lambda$4;
            }
        };
        if (i != 0) {
            if (i != 1) {
                if (i != 2) {
                    if (i >= $saved.size() + 3) {
                        if (StringsKt.startsWith((String) $names.get(i), "Save", false)) {
                            final EditText $this$presets_u24lambda_u2410_u24lambda_u245 = new EditText(this$0.getContext());
                            $this$presets_u24lambda_u2410_u24lambda_u245.setHint("Name, e.g. Night highway");
                            new AlertDialog.Builder(this$0.getContext()).setTitle("Save layout").setView($this$presets_u24lambda_u2410_u24lambda_u245).setPositiveButton("Save", new DialogInterface.OnClickListener() { // from class: com.starpilot.starview.HudEditor$$ExternalSyntheticLambda14
                                @Override // android.content.DialogInterface.OnClickListener
                                public final void onClick(DialogInterface dialogInterface2, int i2) {
                                    HudEditor.presets$lambda$10$lambda$8($this$presets_u24lambda_u2410_u24lambda_u245, this$0, dialogInterface2, i2);
                                }
                            }).setNegativeButton("Cancel", (DialogInterface.OnClickListener) null).show();
                            return;
                        } else {
                            AlertDialog.Builder title = new AlertDialog.Builder(this$0.getContext()).setTitle("Delete which?");
                            Collection $this$toTypedArray$iv = $saved.keySet();
                            title.setItems((CharSequence[]) $this$toTypedArray$iv.toArray(new String[0]), new DialogInterface.OnClickListener() { // from class: com.starpilot.starview.HudEditor$$ExternalSyntheticLambda15
                                @Override // android.content.DialogInterface.OnClickListener
                                public final void onClick(DialogInterface dialogInterface2, int i2) {
                                    HudEditor.presets$lambda$10$lambda$9(this$0, $saved, dialogInterface2, i2);
                                }
                            }).show();
                            return;
                        }
                    }
                    apply.invoke(CollectionsKt.elementAt($saved.values(), i - 3));
                    return;
                }
                apply.invoke(HudPresets.INSTANCE.cockpit(this$0.layout.snapshot(), $keys));
                return;
            }
            apply.invoke(HudPresets.INSTANCE.everything(this$0.layout.snapshot(), $keys));
            return;
        }
        apply.invoke(HudPresets.INSTANCE.only(this$0.layout.snapshot(), $keys, HudPresets.INSTANCE.getFOCUS_KEYS()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit presets$lambda$10$lambda$4(HudEditor this$0, String j) {
        Intrinsics.checkNotNullParameter(j, "j");
        this$0.remember();
        this$0.layout.restore(j);
        this$0.layout.save();
        this$0.selected = null;
        this$0.changed();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void presets$lambda$10$lambda$8(EditText $et, HudEditor this$0, DialogInterface dialogInterface, int i) {
        String it = StringsKt.trim((CharSequence) $et.getText().toString()).toString();
        if (!(it.length() > 0)) {
            it = null;
        }
        if (it != null) {
            HudPresets hudPresets = HudPresets.INSTANCE;
            Context context = this$0.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            hudPresets.save(context, it, this$0.layout.snapshot());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void presets$lambda$10$lambda$9(HudEditor this$0, Map $saved, DialogInterface dialogInterface, int k) {
        HudPresets hudPresets = HudPresets.INSTANCE;
        Context context = this$0.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        hudPresets.delete(context, (String) CollectionsKt.elementAt($saved.keySet(), k));
    }

    private final View panel(final Context ctx) {
        char c;
        LinearLayout p = new LinearLayout(ctx);
        int i = 1;
        p.setOrientation(1);
        int i2 = 12;
        p.setPadding(this.ui.dp(18), this.ui.dp(16), this.ui.dp(18), this.ui.dp(12));
        p.setBackgroundColor(CarUi.Companion.getFRAME());
        TextView $this$panel_u24lambda_u2412 = new TextView(ctx);
        $this$panel_u24lambda_u2412.setText("Driving screen");
        $this$panel_u24lambda_u2412.setTextSize(24.0f);
        $this$panel_u24lambda_u2412.setTextColor(-1);
        $this$panel_u24lambda_u2412.setTypeface(Typeface.DEFAULT_BOLD);
        p.addView($this$panel_u24lambda_u2412);
        TextView $this$panel_u24lambda_u2413 = new TextView(ctx);
        $this$panel_u24lambda_u2413.setText("Drag a widget in the preview to move it, its corner to resize it. Changes save as you go.");
        $this$panel_u24lambda_u2413.setTextSize(14.0f);
        $this$panel_u24lambda_u2413.setTextColor(CarUi.Companion.getDIM());
        $this$panel_u24lambda_u2413.setPadding(0, this.ui.dp(4), 0, this.ui.dp(12));
        p.addView($this$panel_u24lambda_u2413);
        LinearLayout actions = new LinearLayout(ctx);
        actions.setOrientation(0);
        actions.setGravity(16);
        this.undoBtn = CarUi.button$default(this.ui, "Undo", false, new Function0() { // from class: com.starpilot.starview.HudEditor$$ExternalSyntheticLambda16
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit panel$lambda$15;
                panel$lambda$15 = HudEditor.panel$lambda$15(HudEditor.this);
                return panel$lambda$15;
            }
        }, 2, null);
        TextView textView = this.undoBtn;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("undoBtn");
            textView = null;
        }
        actions.addView(textView);
        actions.addView(new View(ctx), new LinearLayout.LayoutParams(this.ui.dp(8), 1));
        actions.addView(CarUi.button$default(this.ui, "Reset", false, new Function0() { // from class: com.starpilot.starview.HudEditor$$ExternalSyntheticLambda17
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit panel$lambda$17;
                panel$lambda$17 = HudEditor.panel$lambda$17(ctx, HudEditor.this);
                return panel$lambda$17;
            }
        }, 2, null));
        actions.addView(new View(ctx), new LinearLayout.LayoutParams(this.ui.dp(8), 1));
        actions.addView(CarUi.button$default(this.ui, "Presets", false, new Function0() { // from class: com.starpilot.starview.HudEditor$$ExternalSyntheticLambda18
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit panel$lambda$18;
                panel$lambda$18 = HudEditor.panel$lambda$18(HudEditor.this);
                return panel$lambda$18;
            }
        }, 2, null));
        actions.addView(new View(ctx), new LinearLayout.LayoutParams(0, 1, 1.0f));
        actions.addView(this.ui.button("Done", true, new Function0() { // from class: com.starpilot.starview.HudEditor$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit panel$lambda$19;
                panel$lambda$19 = HudEditor.panel$lambda$19(HudEditor.this);
                return panel$lambda$19;
            }
        }));
        // 2.85.4: the narrower panel has the four buttons in two rows of two (Undo Reset / Presets Done)
        java.util.ArrayList<TextView> btns = new java.util.ArrayList<>();
        for (int bi = 0; bi < actions.getChildCount(); bi++) {
            View child = actions.getChildAt(bi);
            if (child instanceof TextView) {
                btns.add((TextView) child);
            }
        }
        actions.removeAllViews();
        actions.setOrientation(1);
        for (int r = 0; r * 2 < btns.size(); r++) {
            LinearLayout row = new LinearLayout(ctx);
            row.setOrientation(0);
            for (int ci = 0; ci < 2 && r * 2 + ci < btns.size(); ci++) {
                TextView b = btns.get(r * 2 + ci);
                b.setMinWidth(0);
                b.setMinimumWidth(0);
                b.setPadding(this.ui.dp(10), this.ui.dp(10), this.ui.dp(10), this.ui.dp(10));
                LinearLayout.LayoutParams blp = new LinearLayout.LayoutParams(0, -2, 1.0f);
                if (ci == 0) {
                    blp.setMarginEnd(this.ui.dp(8));
                }
                row.addView(b, blp);
            }
            LinearLayout.LayoutParams rlp = new LinearLayout.LayoutParams(-1, -2);
            if (r > 0) {
                rlp.topMargin = this.ui.dp(8);
            }
            actions.addView(row, rlp);
        }
        p.addView(actions);
        LinearLayout tabs = new LinearLayout(ctx);
        tabs.setOrientation(0);
        int i3 = 10;
        tabs.setPadding(0, this.ui.dp(14), 0, this.ui.dp(10));
        int i4 = 0;
        for (String label : (Iterable<String>) (Iterable) CollectionsKt.listOf(new String[]{"Widgets", "Edit widget"})) {
            final int i5 = i4;
            i4 += i;
            TextView b = new TextView(ctx);
            b.setText(label);
            b.setTextSize(17.0f);
            b.setGravity(17);
            b.setTypeface(Typeface.DEFAULT_BOLD);
            b.setPadding(this.ui.dp(i3), this.ui.dp(i2), this.ui.dp(i3), this.ui.dp(i2));
            b.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.HudEditor$$ExternalSyntheticLambda2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    HudEditor.panel$lambda$22$lambda$21(HudEditor.this, i5, view);
                }
            });
            this.tabBtns.add(b);
            TextView textView2 = b;
            LinearLayout.LayoutParams $this$panel_u24lambda_u2423 = new LinearLayout.LayoutParams(0, -2, 1.0f);
            if (i5 == 0) {
                c = '\b';
                $this$panel_u24lambda_u2423.setMarginEnd(this.ui.dp(8));
            } else {
                c = '\b';
            }
            Unit unit = Unit.INSTANCE;
            tabs.addView(textView2, $this$panel_u24lambda_u2423);
            i = 1;
            i2 = 12;
            i3 = 10;
        }
        p.addView(tabs);
        ScrollView $this$panel_u24lambda_u2424 = new ScrollView(ctx);
        $this$panel_u24lambda_u2424.addView(this.content);
        p.addView($this$panel_u24lambda_u2424, new LinearLayout.LayoutParams(-1, 0, 1.0f));
        return p;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit panel$lambda$15(HudEditor this$0) {
        this$0.doUndo();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit panel$lambda$17(Context $ctx, final HudEditor this$0) {
        new AlertDialog.Builder($ctx).setMessage("Put every widget back where it started and show all of them?").setPositiveButton("Reset", new DialogInterface.OnClickListener() { // from class: com.starpilot.starview.HudEditor$$ExternalSyntheticLambda3
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                HudEditor.panel$lambda$17$lambda$16(this$0, dialogInterface, i);
            }
        }).setNegativeButton("Cancel", (DialogInterface.OnClickListener) null).show();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void panel$lambda$17$lambda$16(HudEditor this$0, DialogInterface dialogInterface, int i) {
        this$0.remember();
        this$0.layout.reset();
        this$0.layout.save();
        this$0.selected = null;
        this$0.changed();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit panel$lambda$18(HudEditor this$0) {
        this$0.presets();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit panel$lambda$19(HudEditor this$0) {
        this$0.finish();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void panel$lambda$22$lambda$21(HudEditor this$0, int $i, View it) {
        this$0.tab = $i;
        this$0.refresh();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void refresh() {
        int i = 0;
        for (TextView b : this.tabBtns) {
            int i2 = i;
            i++;
            boolean on = i2 == this.tab;
            CarUi carUi = this.ui;
            CarUi.Companion_ companion = CarUi.Companion;
            b.setBackground(CarUi.rounded$default(carUi, on ? companion.getACCENT() : companion.getCARD(), 22, null, 4, null));
            b.setTextColor(on ? CarUi.Companion.getBG() : -1);
        }
        TextView textView = this.undoBtn;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("undoBtn");
            textView = null;
        }
        textView.setAlpha(this.undo.isEmpty() ? 0.4f : 1.0f);
        this.content.removeAllViews();
        if (this.tab == 0) {
            widgetsTab();
        } else {
            editTab();
        }
        this.preview.invalidate();
    }

    private final void widgetsTab() {
        Iterable $this$filter$iv = this.layout.getItems();
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            HudLayout.Item it = (HudLayout.Item) element$iv$iv;
            if (this.layout.enabled(it.getKey())) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        List on = (List) destination$iv$iv;
        Iterable $this$filter$iv2 = this.layout.getItems();
        Collection destination$iv$iv2 = new ArrayList();
        for (Object element$iv$iv2 : $this$filter$iv2) {
            HudLayout.Item it2 = (HudLayout.Item) element$iv$iv2;
            if (!this.layout.enabled(it2.getKey())) {
                destination$iv$iv2.add(element$iv$iv2);
            }
        }
        List off = (List) destination$iv$iv2;
        this.content.addView(this.ui.header("On screen  ·  " + on.size()));
        List $this$sortedBy$iv = on;
        for (HudLayout.Item it3 : (Iterable<HudLayout.Item>) CollectionsKt.sortedWith($this$sortedBy$iv, new Comparator() { // from class: com.starpilot.starview.HudEditor$widgetsTab$$inlined$sortedBy$1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.Comparator
            public final int compare(Object t, Object t2) {
                float order;
                float order2;
                HudLayout.Item it4 = (HudLayout.Item) t;
                order = HudEditor.this.order(it4);
                Float valueOf = Float.valueOf(order);
                HudLayout.Item it5 = (HudLayout.Item) t2;
                order2 = HudEditor.this.order(it5);
                return ComparisonsKt.compareValues(valueOf, Float.valueOf(order2));
            }
        })) {
            this.content.addView(listRow(it3, true), CarUi.lp$default(this.ui, 0, this.ui.dp(8), 1, null));
        }
        this.content.addView(this.ui.header("Not on screen  ·  " + off.size()));
        if (off.isEmpty()) {
            this.content.addView(this.ui.note("Every widget is on the screen."));
        }
        List $this$sortedBy$iv2 = off;
        for (HudLayout.Item it4 : (Iterable<HudLayout.Item>) CollectionsKt.sortedWith($this$sortedBy$iv2, new Comparator() { // from class: com.starpilot.starview.HudEditor$widgetsTab$$inlined$sortedBy$2
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.Comparator
            public final int compare(Object t, Object t2) {
                HudLayout.Item it5 = (HudLayout.Item) t;
                HudLayout.Item it6 = (HudLayout.Item) t2;
                return ComparisonsKt.compareValues(it5.getLabel(), it6.getLabel());
            }
        })) {
            this.content.addView(listRow(it4, false), CarUi.lp$default(this.ui, 0, this.ui.dp(8), 1, null));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final float order(HudLayout.Item it) {
        RectF r = this.layout.rect(this.root, it);
        if (r != null) {
            return ((r.top / Math.max(1, this.root.getHeight())) * 4.0f) + (r.left / Math.max(1, this.root.getWidth()));
        }
        return 99.0f;
    }

    private final View listRow(final HudLayout.Item item, final boolean on) {
        boolean sel = item == this.selected;
        LinearLayout r = new LinearLayout(getContext());
        r.setOrientation(0);
        r.setGravity(16);
        CarUi carUi = this.ui;
        CarUi.Companion_ companion = CarUi.Companion;
        r.setBackground(carUi.rounded(sel ? companion.getCARD_ON() : companion.getCARD(), 14, sel ? Integer.valueOf(CarUi.Companion.getACCENT()) : null));
        r.setPadding(this.ui.dp(16), this.ui.dp(6), this.ui.dp(8), this.ui.dp(6));
        r.setMinimumHeight(this.ui.dp(58));
        r.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.HudEditor$$ExternalSyntheticLambda10
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                HudEditor.listRow$lambda$31$lambda$30(on, HudEditor.this, item, view);
            }
        });
        TextView $this$listRow_u24lambda_u2432 = new TextView(getContext());
        $this$listRow_u24lambda_u2432.setText(item.getLabel());
        $this$listRow_u24lambda_u2432.setTextSize(18.0f);
        $this$listRow_u24lambda_u2432.setTextColor(on ? -1 : CarUi.Companion.getDIM());
        r.addView($this$listRow_u24lambda_u2432, new LinearLayout.LayoutParams(0, -2, 1.0f));
        if (!on) {
            r.addView(roundBtn("+", CarUi.Companion.getACCENT(), new Function0() { // from class: com.starpilot.starview.HudEditor$$ExternalSyntheticLambda11
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit listRow$lambda$33;
                    listRow$lambda$33 = HudEditor.listRow$lambda$33(HudEditor.this, item);
                    return listRow$lambda$33;
                }
            }));
        } else if (item.getCanHide()) {
            r.addView(roundBtn("−", Color.rgb(235, 90, 110), new Function0() { // from class: com.starpilot.starview.HudEditor$$ExternalSyntheticLambda12
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit listRow$lambda$34;
                    listRow$lambda$34 = HudEditor.listRow$lambda$34(HudEditor.this, item);
                    return listRow$lambda$34;
                }
            }));
        }
        return r;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void listRow$lambda$31$lambda$30(boolean $on, HudEditor this$0, HudLayout.Item $item, View it) {
        if (!$on) {
            this$0.add($item);
            return;
        }
        this$0.selected = $item;
        this$0.tab = 1;
        this$0.refresh();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit listRow$lambda$33(HudEditor this$0, HudLayout.Item $item) {
        this$0.add($item);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit listRow$lambda$34(HudEditor this$0, HudLayout.Item $item) {
        this$0.remove($item);
        return Unit.INSTANCE;
    }

    private final TextView roundBtn(String label, int color, final Function0<Unit> onClick) {
        TextView $this$roundBtn_u24lambda_u2436 = new TextView(getContext());
        $this$roundBtn_u24lambda_u2436.setText(label);
        $this$roundBtn_u24lambda_u2436.setTextSize(26.0f);
        $this$roundBtn_u24lambda_u2436.setGravity(17);
        $this$roundBtn_u24lambda_u2436.setTextColor(color);
        $this$roundBtn_u24lambda_u2436.setTypeface(Typeface.DEFAULT_BOLD);
        $this$roundBtn_u24lambda_u2436.setLayoutParams(new LinearLayout.LayoutParams(this.ui.dp(52), this.ui.dp(46)));
        $this$roundBtn_u24lambda_u2436.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.HudEditor$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                onClick.invoke();
            }
        });
        return $this$roundBtn_u24lambda_u2436;
    }

    private final void editTab() {
        final HudLayout.Item it = this.selected;
        if (it == null) {
            this.content.addView(this.ui.note("Pick a widget in the preview or in the list."));
            return;
        }
        final HudLayout.State s = this.layout.state(it.getKey());
        LinearLayout linearLayout = this.content;
        TextView $this$editTab_u24lambda_u2437 = new TextView(getContext());
        $this$editTab_u24lambda_u2437.setText(it.getLabel());
        $this$editTab_u24lambda_u2437.setTextSize(22.0f);
        $this$editTab_u24lambda_u2437.setTextColor(-1);
        $this$editTab_u24lambda_u2437.setTypeface(Typeface.DEFAULT_BOLD);
        $this$editTab_u24lambda_u2437.setPadding(this.ui.dp(4), this.ui.dp(4), 0, this.ui.dp(12));
        linearLayout.addView($this$editTab_u24lambda_u2437);
        this.content.addView(this.ui.header("Size"));
        LinearLayout size = new LinearLayout(getContext());
        size.setOrientation(0);
        size.setGravity(16);
        size.addView(this.ui.stepper("−", new Function0() { // from class: com.starpilot.starview.HudEditor$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit editTab$lambda$39;
                editTab$lambda$39 = HudEditor.editTab$lambda$39(HudEditor.this, it, s);
                return editTab$lambda$39;
            }
        }));
        TextView $this$editTab_u24lambda_u2440 = new TextView(getContext());
        $this$editTab_u24lambda_u2440.setText(MathKt.roundToInt(s.getScale() * 100) + " %");
        $this$editTab_u24lambda_u2440.setTextSize(22.0f);
        $this$editTab_u24lambda_u2440.setTextColor(-1);
        $this$editTab_u24lambda_u2440.setGravity(17);
        size.addView($this$editTab_u24lambda_u2440, new LinearLayout.LayoutParams(0, -2, 1.0f));
        size.addView(this.ui.stepper("+", new Function0() { // from class: com.starpilot.starview.HudEditor$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit editTab$lambda$41;
                editTab$lambda$41 = HudEditor.editTab$lambda$41(HudEditor.this, it, s);
                return editTab$lambda$41;
            }
        }));
        this.content.addView(size, CarUi.lp$default(this.ui, 0, this.ui.dp(12), 1, null));
        this.content.addView(this.ui.header("Position"));
        this.content.addView(CarUi.button$default(this.ui, "Center it left to right", false, new Function0() { // from class: com.starpilot.starview.HudEditor$$ExternalSyntheticLambda7
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit editTab$lambda$42;
                editTab$lambda$42 = HudEditor.editTab$lambda$42(HudEditor.this, it);
                return editTab$lambda$42;
            }
        }, 2, null), CarUi.lp$default(this.ui, 0, this.ui.dp(8), 1, null));
        this.content.addView(CarUi.button$default(this.ui, "Back to its first spot and size", false, new Function0() { // from class: com.starpilot.starview.HudEditor$$ExternalSyntheticLambda8
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit editTab$lambda$43;
                editTab$lambda$43 = HudEditor.editTab$lambda$43(HudEditor.this, s);
                return editTab$lambda$43;
            }
        }, 2, null), CarUi.lp$default(this.ui, 0, this.ui.dp(8), 1, null));
        if (it.getCanHide()) {
            this.content.addView(this.ui.header("Screen"));
            this.content.addView(CarUi.button$default(this.ui, "Take it off the screen", false, new Function0() { // from class: com.starpilot.starview.HudEditor$$ExternalSyntheticLambda9
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit editTab$lambda$44;
                    editTab$lambda$44 = HudEditor.editTab$lambda$44(HudEditor.this, it);
                    return editTab$lambda$44;
                }
            }, 2, null), CarUi.lp$default(this.ui, 0, this.ui.dp(8), 1, null));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit editTab$lambda$39(HudEditor this$0, HudLayout.Item $it, HudLayout.State $s) {
        this$0.setScale($it, $s.getScale() - 0.1f);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit editTab$lambda$41(HudEditor this$0, HudLayout.Item $it, HudLayout.State $s) {
        this$0.setScale($it, $s.getScale() + 0.1f);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit editTab$lambda$42(HudEditor this$0, HudLayout.Item $it) {
        this$0.center($it);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit editTab$lambda$43(HudEditor this$0, HudLayout.State $s) {
        this$0.remember();
        $s.setX(Float.NaN);
        $s.setY(Float.NaN);
        $s.setCw(Float.NaN);
        $s.setCh(Float.NaN);
        $s.setScale(1.0f);
        this$0.changed();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit editTab$lambda$44(HudEditor this$0, HudLayout.Item $it) {
        this$0.remove($it);
        return Unit.INSTANCE;
    }

    private final void remember() {
        this.undo.addLast(this.layout.snapshot());
        while (this.undo.size() > 40) {
            this.undo.removeFirst();
        }
    }

    private final void doUndo() {
        String snap = this.undo.removeLastOrNull();
        if (snap == null) {
            return;
        }
        this.layout.restore(snap);
        HudLayout.Item it = this.selected;
        if (it != null && !this.layout.enabled(it.getKey())) {
            this.selected = null;
        }
        changed();
    }

    private final void changed() {
        this.layout.apply(this.root);
        this.layout.save();
        this.onChange.invoke();
        refresh();
    }

    private final void add(HudLayout.Item item) {
        remember();
        this.layout.state(item.getKey()).setOn(true);
        this.selected = item;
        changed();
    }

    private final void remove(HudLayout.Item item) {
        remember();
        this.layout.state(item.getKey()).setOn(false);
        if (this.selected == item) {
            this.selected = null;
        }
        this.tab = 0;
        changed();
    }

    private final void setScale(HudLayout.Item item, float v) {
        remember();
        this.layout.state(item.getKey()).setScale(RangesKt.coerceIn(MathKt.roundToInt(10 * v) / 10.0f, 0.5f, 2.5f));
        changed();
    }

    private final void center(HudLayout.Item item) {
        RectF r = this.layout.rect(this.root, item);
        if (r == null) {
            return;
        }
        remember();
        this.layout.place(this.layout.state(item.getKey()), (this.root.getWidth() - r.width()) / 2.0f, r.top, this.root.getWidth(), this.root.getHeight());
        changed();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Pair<Float, Float> snap(HudLayout.Item me, float x, float y, float w, float h) {
        RectF r;
        float tol = dpf(14.0f);
        float m = dpf(24.0f);
        ArrayList xs = new ArrayList();
        ArrayList ys = new ArrayList();
        char c = 1;
        float f = 2;
        CollectionsKt.addAll(xs, CollectionsKt.listOf(new Float[]{Float.valueOf(m), Float.valueOf((this.root.getWidth() - m) - w), Float.valueOf((this.root.getWidth() - w) / f)}));
        CollectionsKt.addAll(ys, CollectionsKt.listOf(new Float[]{Float.valueOf(m), Float.valueOf((this.root.getHeight() - m) - h), Float.valueOf((this.root.getHeight() - h) / f)}));
        for (HudLayout.Item o : this.layout.getItems()) {
            if (o != me && this.layout.enabled(o.getKey()) && (r = this.layout.rect(this.root, o)) != null) {
                Float[] fArr = new Float[4];
                fArr[0] = Float.valueOf(r.left);
                fArr[c] = Float.valueOf(r.right - w);
                fArr[2] = Float.valueOf(r.right + dpf(10.0f));
                fArr[3] = Float.valueOf((r.left - w) - dpf(10.0f));
                CollectionsKt.addAll(xs, CollectionsKt.listOf((Object[]) fArr));
                CollectionsKt.addAll(ys, CollectionsKt.listOf(new Float[]{Float.valueOf(r.top), Float.valueOf(r.bottom - h), Float.valueOf(r.bottom + dpf(10.0f)), Float.valueOf((r.top - h) - dpf(10.0f))}));
                c = 1;
            }
        }
        float bx = x;
        float by = y;
        float bdx = tol;
        float bdy = tol;
        Iterator it = xs.iterator();
        Intrinsics.checkNotNullExpressionValue(it, "iterator(...)");
        while (it.hasNext()) {
            Object next = it.next();
            Intrinsics.checkNotNullExpressionValue(next, "next(...)");
            float c2 = ((Number) next).floatValue();
            float dd = Math.abs(c2 - x);
            if (dd < bdx) {
                bdx = dd;
                bx = c2;
            }
        }
        Iterator it2 = ys.iterator();
        Intrinsics.checkNotNullExpressionValue(it2, "iterator(...)");
        while (it2.hasNext()) {
            Object next2 = it2.next();
            Intrinsics.checkNotNullExpressionValue(next2, "next(...)");
            float c3 = ((Number) next2).floatValue();
            float dd2 = Math.abs(c3 - y);
            if (dd2 < bdy) {
                bdy = dd2;
                by = c3;
            }
        }
        return TuplesKt.to(Float.valueOf(RangesKt.coerceIn(bx, 0.0f, Math.max(0.0f, this.root.getWidth() - w))), Float.valueOf(RangesKt.coerceIn(by, 0.0f, Math.max(0.0f, this.root.getHeight() - h))));
    }

    /* compiled from: HudEditor.kt */
    private static final class Preview extends View {
        private String before;
        private final Paint bg;
        private final Paint dash;
        private float downX;
        private float downY;
        private final Paint fill;
        private final RectF frame;
        private float k;
        private final Paint label;
        private final Paint line;
        private int mode;
        private boolean moved;
        private final Path road;
        private final Paint selP;
        private float startL;
        private float startScale;
        private float startT;
        private float startW;
        final /* synthetic */ HudEditor this$0;

        /* VF: from Vineflower (jadx: ) */
        public Preview(HudEditor var1, Context var2) {
            super(var2);
            Intrinsics.checkNotNullParameter(var2, "ctx");
            this.this$0 = var1;
            this.frame = new RectF();
            this.k = 1.0F;
            this.bg = new Paint(1);
            Paint var3 = new Paint(1);
            var3.setStyle(android.graphics.Paint.Style.STROKE);
            this.line = var3;
            Paint var7 = new Paint(1);
            var1 = this.this$0;
            var7.setStyle(android.graphics.Paint.Style.STROKE);
            var7.setStrokeWidth(var1.dpf(1.5F));
            var7.setPathEffect((PathEffect)(new DashPathEffect(new float[]{var1.dpf(7.0F), var1.dpf(5.0F)}, 0.0F)));
            var7.setColor(Color.argb(150, 255, 255, 255));
            this.dash = var7;
            Paint var8 = new Paint(1);
            var1 = this.this$0;
            var8.setStyle(android.graphics.Paint.Style.STROKE);
            var8.setStrokeWidth(var1.dpf(3.0F));
            var8.setColor(CarUi.Companion.getACCENT());
            this.selP = var8;
            this.fill = new Paint(1);
            Paint var9 = new Paint(1);
            var1 = this.this$0;
            var9.setColor(-1);
            var9.setTextSize(var1.dpf(12.0F));
            var9.setTypeface(Typeface.DEFAULT_BOLD);
            this.label = var9;
            this.road = new Path();
            this.startScale = 1.0F;
        }

        private final RectF box(HudLayout.Item it) {
            RectF r = this.this$0.layout.rect(this.this$0.root, it);
            if (r == null) {
                return null;
            }
            float minS = this.this$0.dpf(26.0f);
            return new RectF(this.frame.left + (r.left * this.k), this.frame.top + (r.top * this.k), this.frame.left + Math.max(r.right * this.k, (r.left * this.k) + minS), this.frame.top + Math.max(r.bottom * this.k, (r.top * this.k) + minS));
        }

        @Override // android.view.View
        protected void onDraw(Canvas c) {
            RectF b;
            Iterator<HudLayout.Item> it;
            int argb;
            RectF r;
            Intrinsics.checkNotNullParameter(c, "c");
            float rw = Math.max(1, this.this$0.root.getWidth());
            float rh = Math.max(1, this.this$0.root.getHeight());
            float pad = this.this$0.dpf(20.0f);
            float f = 2;
            this.k = Math.min((getWidth() - (f * pad)) / rw, (getHeight() - (f * pad)) / rh);
            float f2 = 0.0f;
            if (this.k <= 0.0f) {
                return;
            }
            float fw = rw * this.k;
            float fh = rh * this.k;
            this.frame.set((getWidth() - fw) / f, (getHeight() - fh) / f, (getWidth() + fw) / f, (getHeight() + fh) / f);
            this.bg.setColor(Color.rgb(26, 29, 36));
            c.drawRoundRect(this.frame, this.this$0.dpf(14.0f), this.this$0.dpf(14.0f), this.bg);
            float cx = this.frame.centerX();
            float hy = this.frame.top + (0.42f * fh);
            this.road.reset();
            this.road.moveTo(cx - (fw * 0.06f), hy);
            this.road.lineTo(cx + (fw * 0.06f), hy);
            this.road.lineTo(this.frame.left + (fw * 0.95f), this.frame.bottom);
            this.road.lineTo(this.frame.left + (fw * 0.05f), this.frame.bottom);
            this.road.close();
            this.bg.setColor(Color.rgb(44, 48, 56));
            c.drawPath(this.road, this.bg);
            this.road.reset();
            this.road.moveTo(cx - (fw * 0.025f), hy);
            this.road.lineTo(cx + (0.025f * fw), hy);
            this.road.lineTo(cx + (fw * 0.17f), this.frame.bottom);
            this.road.lineTo(cx - (0.17f * fw), this.frame.bottom);
            this.road.close();
            this.bg.setColor(Color.argb(150, 40, 170, 90));
            c.drawPath(this.road, this.bg);
            this.line.setColor(Color.argb(200, 220, 220, 220));
            this.line.setStrokeWidth(this.this$0.dpf(2.0f));
            c.drawLine(cx - (fw * 0.06f), hy, this.frame.left + (0.05f * fw), this.frame.bottom, this.line);
            c.drawLine(cx + (0.06f * fw), hy, this.frame.left + (0.95f * fw), this.frame.bottom, this.line);
            this.line.setColor(Color.argb(220, 60, 210, 110));
            this.line.setStrokeWidth(this.this$0.dpf(3.0f));
            c.drawRoundRect(this.frame, this.this$0.dpf(14.0f), this.this$0.dpf(14.0f), this.line);
            c.save();
            c.clipRect(this.frame);
            for (HudLayout.Item it2 : this.this$0.layout.getItems()) {
                if (this.this$0.layout.enabled(it2.getKey())) {
                    View v = this.this$0.root.findViewById(it2.getId());
                    if (v != null && (r = this.this$0.layout.rect(this.this$0.root, it2)) != null) {
                        float sc = (v.getScaleX() > f2 ? v.getScaleX() : 1.0f) * this.k;
                        c.save();
                        c.translate(this.frame.left + (r.left * this.k), this.frame.top + (r.top * this.k));
                        c.scale(sc, sc);
                        try {
                            v.draw(c);
                        } catch (Exception e) {
                        }
                        c.restore();
                        f2 = 0.0f;
                    }
                } else {
                    f2 = 0.0f;
                }
            }
            Iterator<HudLayout.Item> it3 = this.this$0.layout.getItems().iterator();
            while (it3.hasNext()) {
                HudLayout.Item it4 = it3.next();
                if (this.this$0.layout.enabled(it4.getKey()) && (b = box(it4)) != null) {
                    boolean sel = it4 == this.this$0.selected;
                    c.drawRoundRect(b, this.this$0.dpf(8.0f), this.this$0.dpf(8.0f), sel ? this.selP : this.dash);
                    this.label.setTextSize(this.this$0.dpf(sel ? 14.0f : 11.5f));
                    float tw = this.label.measureText(it4.getLabel());
                    Paint paint = this.fill;
                    if (sel) {
                        argb = CarUi.Companion.getACCENT();
                        it = it3;
                    } else {
                        it = it3;
                        argb = Color.argb(190, 16, 18, 24);
                    }
                    paint.setColor(argb);
                    float rw2 = rw;
                    c.drawRoundRect(new RectF(b.left + this.this$0.dpf(3.0f), b.top + this.this$0.dpf(3.0f), b.left + this.this$0.dpf(11.0f) + tw, b.top + this.this$0.dpf(5.0f) + (this.label.getTextSize() * 1.25f)), this.this$0.dpf(5.0f), this.this$0.dpf(5.0f), this.fill);
                    this.label.setColor(sel ? CarUi.Companion.getBG() : -1);
                    c.drawText(it4.getLabel(), b.left + this.this$0.dpf(7.0f), b.top + this.this$0.dpf(3.0f) + (this.label.getTextSize() * 1.05f), this.label);
                    if (!sel) {
                        it3 = it;
                        rw = rw2;
                    } else {
                        this.fill.setColor(CarUi.Companion.getACCENT());
                        c.drawCircle(b.right, b.bottom, this.this$0.dpf(11.0f), this.fill);
                        this.fill.setColor(CarUi.Companion.getBG());
                        c.drawCircle(b.right, b.bottom, this.this$0.dpf(4.0f), this.fill);
                        it3 = it;
                        rw = rw2;
                    }
                }
            }
            c.restore();
            postInvalidateDelayed(150L);
        }

        private final HudLayout.Item hit(float x, float y) {
            RectF b;
            if (this.k <= 0.0f) {
                return null;
            }
            HudLayout.Item s = this.this$0.selected;
            if (s != null) {
                if (!this.this$0.layout.enabled(s.getKey())) {
                    s = null;
                }
                if (s != null) {
                    HudEditor hudEditor = this.this$0;
                    RectF b2 = box(s);
                    if (b2 != null && Math.abs(x - b2.right) < hudEditor.dpf(26.0f) && Math.abs(y - b2.bottom) < hudEditor.dpf(26.0f)) {
                        return s;
                    }
                }
            }
            for (HudLayout.Item it : CollectionsKt.reversed(this.this$0.layout.getItems())) {
                if (this.this$0.layout.enabled(it.getKey()) && (b = box(it)) != null) {
                    b.inset(-this.this$0.dpf(6.0f), -this.this$0.dpf(6.0f));
                    if (b.contains(x, y)) {
                        return it;
                    }
                }
            }
            return null;
        }

        @Override // android.view.View
        public boolean onTouchEvent(MotionEvent e) {
            RectF r0;
            Intrinsics.checkNotNullParameter(e, "e");
            switch (e.getActionMasked()) {
                case 0:
                    this.downX = e.getX();
                    this.downY = e.getY();
                    this.moved = false;
                    HudLayout.Item it = hit(e.getX(), e.getY());
                    if (it != null) {
                        RectF r = this.this$0.layout.rect(this.this$0.root, it);
                        if (r != null) {
                            HudLayout.State s = this.this$0.layout.state(it.getKey());
                            RectF b = box(it);
                            Intrinsics.checkNotNull(b);
                            this.mode = (it != this.this$0.selected || Math.abs(e.getX() - b.right) >= this.this$0.dpf(26.0f) || Math.abs(e.getY() - b.bottom) >= this.this$0.dpf(26.0f)) ? 1 : 2;
                            if (this.this$0.selected != it) {
                                this.this$0.selected = it;
                                this.this$0.refresh();
                            }
                            this.before = this.this$0.layout.snapshot();
                            this.startL = r.left;
                            this.startT = r.top;
                            this.startScale = s.getScale();
                            this.startW = r.width();
                            ViewParent parent = getParent();
                            if (parent != null) {
                                parent.requestDisallowInterceptTouchEvent(true);
                                break;
                            }
                        }
                    } else {
                        this.mode = 0;
                        break;
                    }
                    break;
                case 1:
                case 3:
                    if (this.mode == 0 && !this.moved && e.getActionMasked() == 1 && this.this$0.selected != null) {
                        this.this$0.selected = null;
                        this.this$0.refresh();
                        break;
                    } else {
                        if (this.moved) {
                            String it2 = this.before;
                            if (it2 != null) {
                                HudEditor hudEditor = this.this$0;
                                hudEditor.undo.addLast(it2);
                                while (hudEditor.undo.size() > 40) {
                                    hudEditor.undo.removeFirst();
                                }
                            }
                            this.this$0.layout.save();
                            this.this$0.refresh();
                        }
                        this.mode = 0;
                        this.before = null;
                        break;
                    }
                case 2:
                    HudLayout.Item it3 = this.this$0.selected;
                    if (it3 != null && this.mode != 0) {
                        float dx = (e.getX() - this.downX) / this.k;
                        float dy = (e.getY() - this.downY) / this.k;
                        if (Math.abs(e.getX() - this.downX) > this.this$0.dpf(4.0f) || Math.abs(e.getY() - this.downY) > this.this$0.dpf(4.0f)) {
                            this.moved = true;
                        }
                        if (this.moved) {
                            HudLayout.State s2 = this.this$0.layout.state(it3.getKey());
                            if (Float.isNaN(s2.getX()) && (r0 = this.this$0.layout.rect(this.this$0.root, it3)) != null) {
                                this.this$0.layout.place(s2, r0.left, r0.top, this.this$0.root.getWidth(), this.this$0.root.getHeight());
                            }
                            if (this.mode == 1) {
                                RectF r2 = this.this$0.layout.rect(this.this$0.root, it3);
                                if (r2 != null) {
                                    float nx = RangesKt.coerceIn(this.startL + dx, 0.0f, Math.max(0.0f, this.this$0.root.getWidth() - r2.width()));
                                    float ny = RangesKt.coerceIn(this.startT + dy, 0.0f, Math.max(0.0f, this.this$0.root.getHeight() - r2.height()));
                                    Pair snap = this.this$0.snap(it3, nx, ny, r2.width(), r2.height());
                                    float sx = ((Number) snap.component1()).floatValue();
                                    float sy = ((Number) snap.component2()).floatValue();
                                    this.this$0.layout.place(s2, sx, sy, this.this$0.root.getWidth(), this.this$0.root.getHeight());
                                }
                            } else {
                                s2.setScale(RangesKt.coerceIn((this.startScale * (this.startW + dx)) / Math.max(1.0f, this.startW), 0.5f, 2.5f));
                            }
                            this.this$0.layout.apply(this.this$0.root);
                            this.this$0.onChange.invoke();
                            invalidate();
                            break;
                        }
                    }
                    break;
            }
            return true;
        }
    }
}
