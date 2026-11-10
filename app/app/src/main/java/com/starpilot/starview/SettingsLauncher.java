package com.starpilot.starview;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.starpilot.starview.SettingIcons;
import com.starpilot.starview.SettingsIndex;
import com.starpilot.starview.ShortcutBoard;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup.LayoutParams;

/* compiled from: SettingsLauncher.kt */
public final class SettingsLauncher extends FrameLayout {
    public static final Companion Companion = new Companion(null);
    private static int lastPage = 1;
    private final Function0<List<SettingsIndex.Entry>> allPages;
    private final ShortcutBoard board;
    private final LinearLayout dots;
    private final LinearLayout drawer;
    private float drawerDownY;
    private final LinearLayout drawerList;
    private final ScrollView drawerScroll;
    private final View home;
    private final Function1<String, Unit> open;
    private final SwipePager pager;
    private int pickFor;
    private final UiSettings prefs;
    private final CarUi ui;

    /* compiled from: SettingsLauncher.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final int getLastPage() {
            return SettingsLauncher.lastPage;
        }

        public final void setLastPage(int i) {
            SettingsLauncher.lastPage = i;
        }
    }

    /* VF: from Vineflower (jadx: ) */
    public SettingsLauncher(Context var1, CarUi var2, View var3, Function0<? extends List<SettingsIndex.Entry>> var4, Function1<? super String, Unit> var5) {
        super(var1);
        Intrinsics.checkNotNullParameter(var1, "ctx");
        Intrinsics.checkNotNullParameter(var2, "ui");
        Intrinsics.checkNotNullParameter(var3, "home");
        Intrinsics.checkNotNullParameter(var4, "allPages");
        Intrinsics.checkNotNullParameter(var5, "open");
        this.ui = var2;
        this.home = var3;
        this.allPages = (Function0) var4;
        this.open = (Function1) var5;
        this.prefs = new UiSettings(var1);
        this.board = new ShortcutBoard(this.prefs.getStr("settings_shortcuts", "{}"));
        this.pager = new SwipePager(var1, 1);
        LinearLayout var7 = new LinearLayout(var1);
        var7.setOrientation(0);
        var7.setGravity(17);
        this.dots = var7;
        LinearLayout var8 = new LinearLayout(var1);
        var8.setOrientation(1);
        var8.setBackground((Drawable)CarUi.rounded$default(this.ui, CarUi.Companion.getFRAME(), 24, null, 4, null));
        var8.setVisibility(8);
        var8.setClickable(true);
        this.drawer = var8;
        LinearLayout var9 = new LinearLayout(var1);
        var9.setOrientation(1);
        this.drawerList = var9;
        ScrollView var10 = new ScrollView(var1);
        var10.addView((View)this.drawerList);
        this.drawerScroll = var10;
        this.pickFor = -1;
        LinearLayout var11 = new LinearLayout(var1);
        var11.setOrientation(1);
        var11.addView((View)this.pager, (android.view.ViewGroup.LayoutParams)(new android.widget.LinearLayout.LayoutParams(-1, 0, 1.0F)));
        var11.addView((View)this.dots, (android.view.ViewGroup.LayoutParams)(new android.widget.LinearLayout.LayoutParams(-1, this.ui.dp(30))));
        TextView var6 = new TextView(var1);
        var6.setText("Swipe sideways for your shortcuts  ·  swipe up for every setting");
        var6.setTextColor(CarUi.Companion.getDIM());
        var6.setTextSize(14.0F);
        var6.setGravity(17);
        var6.setOnClickListener(new SettingsLauncher$$ExternalSyntheticLambda2(this));
        var11.addView((View)var6, (android.view.ViewGroup.LayoutParams)(new android.widget.LinearLayout.LayoutParams(-1, -2)));
        this.addView((View)var11, (android.view.ViewGroup.LayoutParams)(new android.widget.FrameLayout.LayoutParams(-1, -1)));
        this.buildDrawer();
        this.addView((View)this.drawer, (android.view.ViewGroup.LayoutParams)(new android.widget.FrameLayout.LayoutParams(-1, -1)));
        this.pager.setOnPage(new SettingsLauncher$$ExternalSyntheticLambda3(this));
        this.pager.setOnSwipeUp(new SettingsLauncher$$ExternalSyntheticLambda4(this));
        this.rebuild(RangesKt.coerceAtLeast(lastPage, 0));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit _init_$lambda$7(SettingsLauncher this$0, int it) {
        lastPage = it;
        this$0.paintDots();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit _init_$lambda$8(SettingsLauncher this$0) {
        this$0.openDrawer(-1);
        return Unit.INSTANCE;
    }

    private final void save() {
        this.prefs.setStr("settings_shortcuts", this.board.json());
    }

    private final int boardPage(int view) {
        if (view == 0) {
            return 0;
        }
        return view - 1;
    }

    private final int viewPage(int boardPage) {
        if (boardPage == 0) {
            return 0;
        }
        return boardPage + 1;
    }

    private final void rebuild(final int show) {
        ViewParent parent = this.home.getParent();
        ViewGroup viewGroup = parent instanceof ViewGroup ? (ViewGroup) parent : null;
        if (viewGroup != null) {
            viewGroup.removeView(this.home);
        }
        this.pager.removeAllViews();
        this.pager.addView(shortcutPage(0));
        this.pager.addView(this.home);
        int size = this.board.getPages().size();
        for (int p = 1; p < size; p++) {
            this.pager.addView(shortcutPage(p));
        }
        this.pager.post(new Runnable() { // from class: com.starpilot.starview.SettingsLauncher$$ExternalSyntheticLambda12
            @Override // java.lang.Runnable
            public final void run() {
                SettingsLauncher.rebuild$lambda$9(SettingsLauncher.this, show);
            }
        });
        paintDots();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void rebuild$lambda$9(SettingsLauncher this$0, int $show) {
        this$0.pager.go(RangesKt.coerceIn($show, 0, this$0.pager.getChildCount() - 1), false);
        lastPage = this$0.pager.getIndex();
        this$0.paintDots();
    }

    private final void paintDots() {
        this.dots.removeAllViews();
        int i = 0;
        int childCount = this.pager.getChildCount();
        while (i < childCount) {
            LinearLayout linearLayout = this.dots;
            View $this$paintDots_u24lambda_u2411 = new View(getContext());
            $this$paintDots_u24lambda_u2411.setBackground(CarUi.rounded$default(this.ui, i == this.pager.getIndex() ? CarUi.Companion.getTEXT() : CarUi.Companion.getDIM(), 99, null, 4, null));
            $this$paintDots_u24lambda_u2411.setAlpha(i == this.pager.getIndex() ? 1.0f : 0.5f);
            $this$paintDots_u24lambda_u2411.setContentDescription(i == 1 ? "Settings home" : "Shortcut page");
            final var i$f = i;
            $this$paintDots_u24lambda_u2411.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.SettingsLauncher$$ExternalSyntheticLambda11
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SettingsLauncher.paintDots$lambda$11$lambda$10(SettingsLauncher.this, i$f, view);
                }
            });
            int i2 = 14;
            int dp = this.ui.dp(i == 1 ? 14 : 10);
            CarUi carUi = this.ui;
            if (i != 1) {
                i2 = 10;
            }
            LinearLayout.LayoutParams $this$paintDots_u24lambda_u2412 = new LinearLayout.LayoutParams(dp, carUi.dp(i2));
            $this$paintDots_u24lambda_u2412.setMargins(this.ui.dp(6), 0, this.ui.dp(6), 0);
            Unit unit = Unit.INSTANCE;
            linearLayout.addView($this$paintDots_u24lambda_u2411, $this$paintDots_u24lambda_u2412);
            i++;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void paintDots$lambda$11$lambda$10(SettingsLauncher this$0, int $i, View it) {
        SwipePager.go$default(this$0.pager, $i, false, 2, null);
    }

    private final View shortcutPage(final int p) {
        List<ShortcutBoard.Item> items = this.board.getPages().get(p);
        GridLayout grid = new GridLayout(getContext());
        grid.setColumnCount(4);
        grid.setRowCount(3);
        grid.setUseDefaultMargins(false);
        int i = 0;
        for (final ShortcutBoard.Item s : items) {
            final int i2 = i;
            i++;
            LinearLayout v = (LinearLayout) this.ui.iconTile(s.getLabel(), s.getWhere(), s.getIcon(), s.getGroup(), new Function0() { // from class: com.starpilot.starview.SettingsLauncher$$ExternalSyntheticLambda8
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit shortcutPage$lambda$14;
                    shortcutPage$lambda$14 = SettingsLauncher.shortcutPage$lambda$14(SettingsLauncher.this, s);
                    return shortcutPage$lambda$14;
                }
            }).component1();
            v.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.starpilot.starview.SettingsLauncher$$ExternalSyntheticLambda9
                @Override // android.view.View.OnLongClickListener
                public final boolean onLongClick(View view) {
                    boolean shortcutPage$lambda$15;
                    shortcutPage$lambda$15 = SettingsLauncher.shortcutPage$lambda$15(SettingsLauncher.this, p, i2, view);
                    return shortcutPage$lambda$15;
                }
            });
            grid.addView(v, cell(i2, 4));
        }
        if (items.size() < 12) {
            LinearLayout $this$shortcutPage_u24lambda_u2419 = new LinearLayout(getContext());
            $this$shortcutPage_u24lambda_u2419.setOrientation(1);
            $this$shortcutPage_u24lambda_u2419.setGravity(17);
            $this$shortcutPage_u24lambda_u2419.setBackground(this.ui.rounded(CarUi.Companion.getCARD(), 20, Integer.valueOf(CarUi.Companion.getDIM())));
            TextView $this$shortcutPage_u24lambda_u2419_u24lambda_u2416 = new TextView($this$shortcutPage_u24lambda_u2419.getContext());
            $this$shortcutPage_u24lambda_u2419_u24lambda_u2416.setText("+");
            $this$shortcutPage_u24lambda_u2419_u24lambda_u2416.setTextSize(46.0f);
            $this$shortcutPage_u24lambda_u2419_u24lambda_u2416.setTextColor(CarUi.Companion.getDIM());
            $this$shortcutPage_u24lambda_u2419_u24lambda_u2416.setGravity(17);
            $this$shortcutPage_u24lambda_u2419.addView($this$shortcutPage_u24lambda_u2419_u24lambda_u2416);
            TextView $this$shortcutPage_u24lambda_u2419_u24lambda_u2417 = new TextView($this$shortcutPage_u24lambda_u2419.getContext());
            $this$shortcutPage_u24lambda_u2419_u24lambda_u2417.setText(items.isEmpty() ? "Add a shortcut" : "Add");
            $this$shortcutPage_u24lambda_u2419_u24lambda_u2417.setTextSize(17.0f);
            $this$shortcutPage_u24lambda_u2419_u24lambda_u2417.setTextColor(CarUi.Companion.getDIM());
            $this$shortcutPage_u24lambda_u2419_u24lambda_u2417.setGravity(17);
            $this$shortcutPage_u24lambda_u2419.addView($this$shortcutPage_u24lambda_u2419_u24lambda_u2417);
            $this$shortcutPage_u24lambda_u2419.setClickable(true);
            $this$shortcutPage_u24lambda_u2419.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.SettingsLauncher$$ExternalSyntheticLambda10
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SettingsLauncher.this.openDrawer(p);
                }
            });
            $this$shortcutPage_u24lambda_u2419.setContentDescription("Add a shortcut to this page");
            grid.addView($this$shortcutPage_u24lambda_u2419, cell(items.size(), 4));
            for (int k = items.size() + 1; k < 12; k++) {
                grid.addView(new View(getContext()), cell(k, 4));
            }
        }
        LinearLayout $this$shortcutPage_u24lambda_u2421 = new LinearLayout(getContext());
        $this$shortcutPage_u24lambda_u2421.setOrientation(1);
        if (items.isEmpty()) {
            TextView $this$shortcutPage_u24lambda_u2421_u24lambda_u2420 = new TextView($this$shortcutPage_u24lambda_u2421.getContext());
            $this$shortcutPage_u24lambda_u2421_u24lambda_u2420.setText("Your shortcuts: tap + and pick any setting, or hold one in the list that slides up. Hold a shortcut to move or remove it.");
            $this$shortcutPage_u24lambda_u2421_u24lambda_u2420.setTextColor(CarUi.Companion.getDIM());
            $this$shortcutPage_u24lambda_u2421_u24lambda_u2420.setTextSize(16.0f);
            $this$shortcutPage_u24lambda_u2421_u24lambda_u2420.setPadding(this.ui.dp(10), 0, this.ui.dp(10), this.ui.dp(8));
            $this$shortcutPage_u24lambda_u2421.addView($this$shortcutPage_u24lambda_u2421_u24lambda_u2420);
        }
        $this$shortcutPage_u24lambda_u2421.addView(grid, new LinearLayout.LayoutParams(-1, 0, 1.0f));
        return $this$shortcutPage_u24lambda_u2421;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit shortcutPage$lambda$14(SettingsLauncher this$0, ShortcutBoard.Item $s) {
        this$0.open.invoke($s.getTarget());
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean shortcutPage$lambda$15(SettingsLauncher this$0, int $p, int $i, View it) {
        this$0.editShortcut($p, $i);
        return true;
    }

    private final GridLayout.LayoutParams cell(int i, int cols) {
        GridLayout.LayoutParams $this$cell_u24lambda_u2422 = new GridLayout.LayoutParams(GridLayout.spec(i / cols, 1.0f), GridLayout.spec(i % cols, 1.0f));
        $this$cell_u24lambda_u2422.width = 0;
        $this$cell_u24lambda_u2422.height = 0;
        $this$cell_u24lambda_u2422.setMargins(this.ui.dp(7), this.ui.dp(7), this.ui.dp(7), this.ui.dp(7));
        return $this$cell_u24lambda_u2422;
    }

    private final void editShortcut(final int p, final int i) {
        final ShortcutBoard.Item s = (ShortcutBoard.Item) CollectionsKt.getOrNull(this.board.getPages().get(p), i);
        if (s == null) {
            return;
        }
        final ArrayList opts = new ArrayList();
        if (i > 0) {
            opts.add(TuplesKt.to("Move earlier", new Function0() { // from class: com.starpilot.starview.SettingsLauncher$$ExternalSyntheticLambda15
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit editShortcut$lambda$23;
                    editShortcut$lambda$23 = SettingsLauncher.editShortcut$lambda$23(SettingsLauncher.this, p, i);
                    return editShortcut$lambda$23;
                }
            }));
        }
        if (i < this.board.getPages().get(p).size() - 1) {
            opts.add(TuplesKt.to("Move later", new Function0() { // from class: com.starpilot.starview.SettingsLauncher$$ExternalSyntheticLambda16
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit editShortcut$lambda$24;
                    editShortcut$lambda$24 = SettingsLauncher.editShortcut$lambda$24(SettingsLauncher.this, p, i);
                    return editShortcut$lambda$24;
                }
            }));
        }
        final int leftOf = p == 1 ? 0 : p > 1 ? p - 1 : -1;
        final int rightOf = p != 0 ? p + 1 : 1;
        if (leftOf >= 0 && this.board.getPages().get(leftOf).size() < 12) {
            opts.add(TuplesKt.to("Move to the page on the left", new Function0() { // from class: com.starpilot.starview.SettingsLauncher$$ExternalSyntheticLambda17
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit editShortcut$lambda$25;
                    editShortcut$lambda$25 = SettingsLauncher.editShortcut$lambda$25(SettingsLauncher.this, p, i, leftOf);
                    return editShortcut$lambda$25;
                }
            }));
        }
        if (rightOf < this.board.getPages().size() && this.board.getPages().get(rightOf).size() < 12) {
            opts.add(TuplesKt.to("Move to the page on the right", new Function0() { // from class: com.starpilot.starview.SettingsLauncher$$ExternalSyntheticLambda18
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit editShortcut$lambda$26;
                    editShortcut$lambda$26 = SettingsLauncher.editShortcut$lambda$26(SettingsLauncher.this, p, i, rightOf);
                    return editShortcut$lambda$26;
                }
            }));
        }
        opts.add(TuplesKt.to("Remove", new Function0() { // from class: com.starpilot.starview.SettingsLauncher$$ExternalSyntheticLambda19
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit editShortcut$lambda$27;
                editShortcut$lambda$27 = SettingsLauncher.editShortcut$lambda$27(SettingsLauncher.this, p, i);
                return editShortcut$lambda$27;
            }
        }));
        AlertDialog.Builder title = new AlertDialog.Builder(getContext()).setTitle(s.getLabel());
        ArrayList $this$map$iv = opts;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        for (Object item$iv$iv : $this$map$iv) {
            Pair it = (Pair) item$iv$iv;
            destination$iv$iv.add((String) it.getFirst());
        }
        Collection $this$toTypedArray$iv = (List) destination$iv$iv;
        title.setItems((CharSequence[]) $this$toTypedArray$iv.toArray(new String[0]), new DialogInterface.OnClickListener() { // from class: com.starpilot.starview.SettingsLauncher$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                SettingsLauncher.editShortcut$lambda$31(opts, SettingsLauncher.this, p, s, dialogInterface, i2);
            }
        }).setNegativeButton("Cancel", (DialogInterface.OnClickListener) null).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit editShortcut$lambda$23(SettingsLauncher this$0, int $p, int $i) {
        this$0.board.move($p, $i, -1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit editShortcut$lambda$24(SettingsLauncher this$0, int $p, int $i) {
        this$0.board.move($p, $i, 1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit editShortcut$lambda$25(SettingsLauncher this$0, int $p, int $i, int $leftOf) {
        this$0.board.toPage($p, $i, $leftOf - $p);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit editShortcut$lambda$26(SettingsLauncher this$0, int $p, int $i, int $rightOf) {
        this$0.board.toPage($p, $i, $rightOf - $p);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit editShortcut$lambda$27(SettingsLauncher this$0, int $p, int $i) {
        this$0.board.remove($p, $i);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void editShortcut$lambda$31(ArrayList $opts, SettingsLauncher this$0, int $p, ShortcutBoard.Item $s, DialogInterface dialogInterface, int w) {
        ((Function0) ((Pair) $opts.get(w)).getSecond()).invoke();
        this$0.save();
        List $this$indexOfFirst$iv = this$0.board.getPages();
        int index$iv = 0;
        Iterator<List<ShortcutBoard.Item>> it = $this$indexOfFirst$iv.iterator();
        while (true) {
            if (!it.hasNext()) {
                index$iv = -1;
                break;
            }
            List item$iv = it.next();
            List it2 = item$iv;
            if (it2.contains($s)) {
                break;
            } else {
                index$iv++;
            }
        }
        Integer valueOf = Integer.valueOf(index$iv);
        int it3 = valueOf.intValue();
        if (!(it3 >= 0)) {
            valueOf = null;
        }
        int stay = valueOf != null ? valueOf.intValue() : RangesKt.coerceAtMost($p, this$0.board.getPages().size() - 1);
        this$0.rebuild(this$0.viewPage(stay));
    }

    public static /* synthetic */ void addShortcut$default(SettingsLauncher settingsLauncher, SettingsIndex.Entry entry, int i, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = -1;
        }
        settingsLauncher.addShortcut(entry, i);
    }

    public final void addShortcut(SettingsIndex.Entry e, int page) {
        Intrinsics.checkNotNullParameter(e, "e");
        if (this.board.contains(e.getTarget())) {
            Toast.makeText(getContext(), e.getLabel() + " is already a shortcut", 0).show();
            return;
        }
        SettingIcons.Icon ic = e.getIcon();
        if (ic == null) {
            ic = new SettingIcons.Icon("new", "device", false, 4, null);
        }
        int want = page >= 0 ? page : this.pager.getIndex() != 1 ? boardPage(this.pager.getIndex()) : -1;
        int at = this.board.add(new ShortcutBoard.Item(e.getLabel(), e.getWhere(), e.getTarget(), ic.getName(), ic.getGroup()), want);
        save();
        Toast.makeText(getContext(), e.getLabel() + " added to your shortcuts", 0).show();
        rebuild(viewPage(at));
    }

    private final void buildDrawer() {
        LinearLayout $this$buildDrawer_u24lambda_u2435 = new LinearLayout(getContext());
        $this$buildDrawer_u24lambda_u2435.setOrientation(1);
        $this$buildDrawer_u24lambda_u2435.setGravity(1);
        $this$buildDrawer_u24lambda_u2435.setPadding(this.ui.dp(16), this.ui.dp(10), this.ui.dp(16), this.ui.dp(6));
        View $this$buildDrawer_u24lambda_u2435_u24lambda_u2432 = new View($this$buildDrawer_u24lambda_u2435.getContext());
        $this$buildDrawer_u24lambda_u2435_u24lambda_u2432.setBackground(CarUi.rounded$default(this.ui, CarUi.Companion.getDIM(), 99, null, 4, null));
        $this$buildDrawer_u24lambda_u2435.addView($this$buildDrawer_u24lambda_u2435_u24lambda_u2432, new LinearLayout.LayoutParams(this.ui.dp(70), this.ui.dp(6)));
        $this$buildDrawer_u24lambda_u2435.setClickable(true);
        $this$buildDrawer_u24lambda_u2435.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.SettingsLauncher$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SettingsLauncher.this.closeDrawer();
            }
        });
        $this$buildDrawer_u24lambda_u2435.setOnTouchListener(new View.OnTouchListener() { // from class: com.starpilot.starview.SettingsLauncher$$ExternalSyntheticLambda6
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                boolean buildDrawer$lambda$35$lambda$34;
                buildDrawer$lambda$35$lambda$34 = SettingsLauncher.buildDrawer$lambda$35$lambda$34(SettingsLauncher.this, view, motionEvent);
                return buildDrawer$lambda$35$lambda$34;
            }
        });
        this.drawer.addView($this$buildDrawer_u24lambda_u2435);
        LinearLayout linearLayout = this.drawer;
        ScrollView scrollView = this.drawerScroll;
        LinearLayout.LayoutParams $this$buildDrawer_u24lambda_u2436 = new LinearLayout.LayoutParams(-1, 0, 1.0f);
        $this$buildDrawer_u24lambda_u2436.setMargins(this.ui.dp(14), 0, this.ui.dp(14), this.ui.dp(10));
        Unit unit = Unit.INSTANCE;
        linearLayout.addView(scrollView, $this$buildDrawer_u24lambda_u2436);
        this.drawerScroll.setOnTouchListener(new View.OnTouchListener() { // from class: com.starpilot.starview.SettingsLauncher$$ExternalSyntheticLambda7
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                boolean buildDrawer$lambda$37;
                buildDrawer$lambda$37 = SettingsLauncher.buildDrawer$lambda$37(SettingsLauncher.this, view, motionEvent);
                return buildDrawer$lambda$37;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean buildDrawer$lambda$35$lambda$34(SettingsLauncher this$0, View view, MotionEvent e) {
        switch (e.getActionMasked()) {
            case 0:
                this$0.drawerDownY = e.getRawY();
                return false;
            case 1:
                if (e.getRawY() - this$0.drawerDownY <= this$0.ui.dp(40)) {
                    return false;
                }
                this$0.closeDrawer();
                return true;
            default:
                return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean buildDrawer$lambda$37(SettingsLauncher this$0, View view, MotionEvent e) {
        switch (e.getActionMasked()) {
            case 0:
                this$0.drawerDownY = e.getRawY();
                return false;
            case 1:
                if (this$0.drawerScroll.getScrollY() != 0 || e.getRawY() - this$0.drawerDownY <= this$0.ui.dp(90)) {
                    return false;
                }
                this$0.closeDrawer();
                return true;
            default:
                return false;
        }
    }

    public final boolean getDrawerOpen() {
        return this.drawer.getVisibility() == 0;
    }

    public final void openDrawer(int forPage) {
        this.pickFor = forPage;
        fillDrawer();
        this.drawer.setVisibility(0);
        this.drawer.setTranslationY(RangesKt.coerceAtLeast(getHeight(), 1.0f));
        this.drawer.animate().translationY(0.0f).setDuration(220L).setInterpolator(new DecelerateInterpolator()).start();
    }

    public final void closeDrawer() {
        if (getDrawerOpen()) {
            this.drawer.animate().translationY(getHeight()).setDuration(180L).withEndAction(new Runnable() { // from class: com.starpilot.starview.SettingsLauncher$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    SettingsLauncher.closeDrawer$lambda$38(SettingsLauncher.this);
                }
            }).start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void closeDrawer$lambda$38(SettingsLauncher this$0) {
        this$0.drawer.setVisibility(8);
        this$0.pickFor = -1;
    }

    private final void fillDrawer() {
        String str;
        Object answer$iv$iv$iv;
        this.drawerList.removeAllViews();
        this.drawerScroll.scrollTo(0, 0);
        LinearLayout linearLayout = this.drawerList;
        TextView $this$fillDrawer_u24lambda_u2439 = new TextView(getContext());
        $this$fillDrawer_u24lambda_u2439.setText(this.pickFor >= 0 ? "Pick a setting for your shortcut page" : "Every setting");
        $this$fillDrawer_u24lambda_u2439.setTextColor(CarUi.Companion.getTEXT());
        $this$fillDrawer_u24lambda_u2439.setTextSize(22.0f);
        $this$fillDrawer_u24lambda_u2439.setTypeface(Typeface.DEFAULT_BOLD);
        $this$fillDrawer_u24lambda_u2439.setPadding(this.ui.dp(6), this.ui.dp(4), this.ui.dp(6), this.ui.dp(2));
        linearLayout.addView($this$fillDrawer_u24lambda_u2439);
        this.drawerList.addView(this.ui.note(this.pickFor >= 0 ? "Tap one to add it. The search bar above finds single switches too." : "Tap to open. Hold to add it to your shortcuts. The search bar above finds single switches too."));
        Iterable $this$distinctBy$iv = this.allPages.invoke();
        HashSet set$iv = new HashSet();
        ArrayList list$iv = new ArrayList();
        for (Object e$iv : $this$distinctBy$iv) {
            SettingsIndex.Entry it = (SettingsIndex.Entry) e$iv;
            if (set$iv.add(TuplesKt.to(it.getTarget(), it.getLabel()))) {
                list$iv.add(e$iv);
            }
        }
        ArrayList entries = list$iv;
        ArrayList $this$groupBy$iv = entries;
        Map destination$iv$iv = new LinkedHashMap();
        Iterator it2 = $this$groupBy$iv.iterator();
        while (true) {
            str = null;
            if (!it2.hasNext()) {
                break;
            }
            Object element$iv$iv = it2.next();
            SettingsIndex.Entry it3 = (SettingsIndex.Entry) element$iv$iv;
            String str2 = StringsKt.substringBefore(it3.getWhere(), " · ", it3.getWhere()) + " · " + StringsKt.substringBefore(StringsKt.substringAfter(it3.getWhere(), " · ", it3.getWhere()), " · ", (StringsKt.substringAfter(it3.getWhere(), " · ", it3.getWhere())));
            Object value$iv$iv$iv = destination$iv$iv.get(str2);
            if (value$iv$iv$iv == null) {
                answer$iv$iv$iv = new ArrayList();
                destination$iv$iv.put(str2, answer$iv$iv$iv);
            } else {
                answer$iv$iv$iv = value$iv$iv$iv;
            }
            List list$iv$iv = (List) answer$iv$iv$iv;
            list$iv$iv.add(element$iv$iv);
        }
        for (Map.Entry entry : (Iterable<Map.Entry>) destination$iv$iv.entrySet()) {
            String where = (String) entry.getKey();
            Iterable group = (List) entry.getValue();
            LinearLayout linearLayout2 = this.drawerList;
            TextView $this$fillDrawer_u24lambda_u2442 = new TextView(getContext());
            $this$fillDrawer_u24lambda_u2442.setText(where);
            $this$fillDrawer_u24lambda_u2442.setTextColor(CarUi.Companion.getACCENT());
            $this$fillDrawer_u24lambda_u2442.setTextSize(17.0f);
            $this$fillDrawer_u24lambda_u2442.setTypeface(Typeface.DEFAULT_BOLD);
            $this$fillDrawer_u24lambda_u2442.setPadding(this.ui.dp(6), this.ui.dp(16), this.ui.dp(6), this.ui.dp(6));
            linearLayout2.addView($this$fillDrawer_u24lambda_u2442);
            Iterable $this$sortedBy$iv = group;
            for (final SettingsIndex.Entry e : (Iterable<SettingsIndex.Entry>) CollectionsKt.sortedWith($this$sortedBy$iv, new Comparator() { // from class: com.starpilot.starview.SettingsLauncher$fillDrawer$$inlined$sortedBy$1
                /* JADX WARN: Multi-variable type inference failed */
                @Override // java.util.Comparator
                public final int compare(Object t, Object t2) {
                    SettingsIndex.Entry it4 = (SettingsIndex.Entry) t;
                    String lowerCase = it4.getLabel().toLowerCase(Locale.ROOT);
                    Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
                    SettingsIndex.Entry it5 = (SettingsIndex.Entry) t2;
                    String lowerCase2 = it5.getLabel().toLowerCase(Locale.ROOT);
                    Intrinsics.checkNotNullExpressionValue(lowerCase2, "toLowerCase(...)");
                    return ComparisonsKt.compareValues(lowerCase, lowerCase2);
                }
            })) {
                SettingIcons.Icon ic = e.getIcon();
                if (ic == null) {
                    ic = new SettingIcons.Icon("new", "device", false, 4, null);
                }
                CarUi carUi = this.ui;
                String label = e.getLabel();
                String substringAfter = StringsKt.substringAfter(StringsKt.substringAfter(e.getWhere(), " · ", e.getWhere()), " · ", "");
                if (substringAfter.length() == 0) {
                    substringAfter = str;
                }
                String str3 = substringAfter;
                SvIcon svIcon = SvIcon.INSTANCE;
                Context context = getContext();
                Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
                LinearLayout row = (LinearLayout) CarUi.row$default(carUi, label, str3, 0, SvIcon.chip$default(svIcon, context, ic.getName(), ic.getGroup(), 46, null, 16, null), null, 20, null).component1();
                if (this.board.contains(e.getTarget())) {
                    TextView $this$fillDrawer_u24lambda_u2445 = new TextView(getContext());
                    $this$fillDrawer_u24lambda_u2445.setText("on a page");
                    $this$fillDrawer_u24lambda_u2445.setTextSize(14.0f);
                    $this$fillDrawer_u24lambda_u2445.setTextColor(CarUi.Companion.getDIM());
                    $this$fillDrawer_u24lambda_u2445.setPadding(this.ui.dp(10), 0, this.ui.dp(10), 0);
                    row.addView($this$fillDrawer_u24lambda_u2445);
                }
                row.setClickable(true);
                row.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.SettingsLauncher$$ExternalSyntheticLambda13
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        SettingsLauncher.fillDrawer$lambda$46(SettingsLauncher.this, e, view);
                    }
                });
                row.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.starpilot.starview.SettingsLauncher$$ExternalSyntheticLambda14
                    @Override // android.view.View.OnLongClickListener
                    public final boolean onLongClick(View view) {
                        boolean fillDrawer$lambda$47;
                        fillDrawer$lambda$47 = SettingsLauncher.fillDrawer$lambda$47(SettingsLauncher.this, e, view);
                        return fillDrawer$lambda$47;
                    }
                });
                this.drawerList.addView(row, CarUi.lp$default(this.ui, 0, 0, 3, null));
                str = null;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void fillDrawer$lambda$46(SettingsLauncher this$0, SettingsIndex.Entry $e, View it) {
        if (this$0.pickFor < 0) {
            this$0.closeDrawer();
            this$0.open.invoke($e.getTarget());
        } else {
            int p = this$0.pickFor;
            this$0.closeDrawer();
            this$0.addShortcut($e, p);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean fillDrawer$lambda$47(SettingsLauncher this$0, SettingsIndex.Entry $e, View it) {
        this$0.closeDrawer();
        this$0.addShortcut($e, this$0.pickFor);
        return true;
    }

    // recovered from Vineflower (R8 lambda body jadx inlined)
    static final void lambda$6$lambda$5(SettingsLauncher var0, View var1) {
       var0.openDrawer(-1);
    }
}
