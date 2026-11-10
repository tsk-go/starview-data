package com.starpilot.starview;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.starpilot.starview.SearchHistory;
import com.starpilot.starview.SettingIcons;
import com.starpilot.starview.SettingsIndex;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* compiled from: SettingsHome.kt */
public final class SettingsHomeView extends LinearLayout {
    private final Function1<SettingsIndex.Entry, Unit> addShortcut;
    private boolean crawled;
    private List<SettingsIndex.Entry> entries;
    private final Function0<List<SettingsIndex.Entry>> extraEntries;
    private final EditText field;
    private final View grid;
    private final SearchHistory history;
    private final Handler main;
    private final Function1<String, Unit> open;
    private final UiSettings prefs;
    private final LinearLayout results;
    private final ScrollView resultsScroll;
    private final CarUi ui;

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public /* synthetic */ SettingsHomeView(Context context, CarUi carUi, View view, Function0 function0, Function1 function1, Function1 function12, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, carUi, view, function0, r5, function12);
        Function1 function13;
        if ((i & 16) == 0) {
            function13 = function1;
        } else {
            function13 = null;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public SettingsHomeView(Context ctx, CarUi ui, View grid, Function0<? extends List<SettingsIndex.Entry>> extraEntries, Function1<? super SettingsIndex.Entry, Unit> function1, Function1<? super String, Unit> open) {
        super(ctx);
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(ui, "ui");
        Intrinsics.checkNotNullParameter(grid, "grid");
        Intrinsics.checkNotNullParameter(extraEntries, "extraEntries");
        Intrinsics.checkNotNullParameter(open, "open");
        this.ui = ui;
        this.grid = grid;
        this.extraEntries = extraEntries;
        this.addShortcut = function1;
        this.open = open;
        LinearLayout $this$results_u24lambda_u240 = new LinearLayout(ctx);
        $this$results_u24lambda_u240.setOrientation(1);
        this.results = $this$results_u24lambda_u240;
        ScrollView $this$resultsScroll_u24lambda_u241 = new ScrollView(ctx);
        $this$resultsScroll_u24lambda_u241.addView(this.results);
        $this$resultsScroll_u24lambda_u241.setVisibility(8);
        this.resultsScroll = $this$resultsScroll_u24lambda_u241;
        this.entries = CollectionsKt.emptyList();
        this.main = new Handler(Looper.getMainLooper());
        this.prefs = new UiSettings(ctx);
        this.history = new SearchHistory(this.prefs.getStr("settings_search_history", "[]"));
        setOrientation(1);
        LinearLayout bar = new LinearLayout(ctx);
        bar.setOrientation(0);
        bar.setGravity(16);
        bar.setBackground(CarUi.rounded$default(this.ui, CarUi.Companion.getCARD(), 18, null, 4, null));
        bar.setPadding(this.ui.dp(18), 0, this.ui.dp(10), 0);
        View $this$_init__u24lambda_u243 = new View(ctx);
        $this$_init__u24lambda_u243.setBackground(SvIcon.drawable$default(SvIcon.INSTANCE, "eye", CarUi.Companion.getDIM(), 0, 4, null));
        bar.addView($this$_init__u24lambda_u243, new LinearLayout.LayoutParams(this.ui.dp(26), this.ui.dp(26)));
        EditText $this$_init__u24lambda_u247 = new EditText(ctx);
        $this$_init__u24lambda_u247.setHint("Search every setting - comma and tablet");
        $this$_init__u24lambda_u247.setHintTextColor(CarUi.Companion.getDIM());
        $this$_init__u24lambda_u247.setTextColor(CarUi.Companion.getTEXT());
        $this$_init__u24lambda_u247.setTextSize(20.0f);
        $this$_init__u24lambda_u247.setBackground(null);
        $this$_init__u24lambda_u247.setSingleLine(true);
        $this$_init__u24lambda_u247.setImeOptions(3);
        $this$_init__u24lambda_u247.setPadding(this.ui.dp(14), this.ui.dp(16), this.ui.dp(8), this.ui.dp(16));
        $this$_init__u24lambda_u247.setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: com.starpilot.starview.SettingsHomeView$$ExternalSyntheticLambda0
            @Override // android.view.View.OnFocusChangeListener
            public final void onFocusChange(View view, boolean z) {
                SettingsHomeView.lambda$7$lambda$4(SettingsHomeView.this, view, z);
            }
        });
        $this$_init__u24lambda_u247.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.SettingsHomeView$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SettingsHomeView.this.showResults();
            }
        });
        $this$_init__u24lambda_u247.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: com.starpilot.starview.SettingsHomeView$$ExternalSyntheticLambda3
            @Override // android.widget.TextView.OnEditorActionListener
            public final boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                return SettingsHomeView.lambda$7$lambda$6(SettingsHomeView.this, textView, i, keyEvent);
            }
        });
        this.field = $this$_init__u24lambda_u247;
        bar.addView(this.field, new LinearLayout.LayoutParams(0, -2, 1.0f));
        final TextView $this$_init__u24lambda_u249 = new TextView(ctx);
        $this$_init__u24lambda_u249.setText("✕");
        $this$_init__u24lambda_u249.setTextSize(22.0f);
        $this$_init__u24lambda_u249.setTextColor(CarUi.Companion.getDIM());
        $this$_init__u24lambda_u249.setPadding(this.ui.dp(14), this.ui.dp(8), this.ui.dp(10), this.ui.dp(8));
        $this$_init__u24lambda_u249.setVisibility(8);
        $this$_init__u24lambda_u249.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.SettingsHomeView$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SettingsHomeView.this.field.setText("");
            }
        });
        bar.addView($this$_init__u24lambda_u249);
        LinearLayout.LayoutParams $this$_init__u24lambda_u2410 = new LinearLayout.LayoutParams(-1, -2);
        $this$_init__u24lambda_u2410.bottomMargin = this.ui.dp(16);
        Unit unit = Unit.INSTANCE;
        addView(bar, $this$_init__u24lambda_u2410);
        this.field.addTextChangedListener(new TextWatcher() { // from class: com.starpilot.starview.SettingsHomeView.4
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int a, int b, int c) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int a, int b, int c) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable e) {
                Editable editable = e;
                $this$_init__u24lambda_u249.setVisibility(editable == null || editable.length() == 0 ? 8 : 0);
                this.startIndex();
                this.showResults();
            }
        });
        FrameLayout frame = new FrameLayout(ctx);
        frame.addView(this.grid, new FrameLayout.LayoutParams(-1, -1));
        frame.addView(this.resultsScroll, new FrameLayout.LayoutParams(-1, -1));
        addView(frame, new LinearLayout.LayoutParams(-1, 0, 1.0f));
    }

    private final void saveHistory() {
        this.prefs.setStr("settings_search_history", this.history.json());
    }

    static final void lambda$7$lambda$4(SettingsHomeView this$0, View view, boolean f) {
        if (f) {
            this$0.startIndex();
        }
        this$0.showResults();
    }

    static final boolean lambda$7$lambda$6(SettingsHomeView this$0, TextView textView, int a, KeyEvent keyEvent) {
        if (a != 3) {
            return false;
        }
        this$0.remember();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void startIndex() {
        if (this.crawled) {
            return;
        }
        this.crawled = true;
        this.entries = this.extraEntries.invoke();
        GalaxyPages.INSTANCE.crawl(new Function1() { // from class: com.starpilot.starview.SettingsHomeView$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit startIndex$lambda$11;
                startIndex$lambda$11 = SettingsHomeView.startIndex$lambda$11(SettingsHomeView.this, (List) obj);
                return startIndex$lambda$11;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit startIndex$lambda$11(SettingsHomeView this$0, List galaxy) {
        Intrinsics.checkNotNullParameter(galaxy, "galaxy");
        this$0.entries = CollectionsKt.plus((Collection) galaxy, (Iterable) this$0.extraEntries.invoke());
        Editable text = this$0.field.getText();
        Intrinsics.checkNotNullExpressionValue(text, "getText(...)");
        if (text.length() > 0) {
            this$0.showResults();
        }
        return Unit.INSTANCE;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void query$default(SettingsHomeView settingsHomeView, String str, List list, int i, Object obj) {
        if ((i & 2) != 0) {
            list = null;
        }
        settingsHomeView.query(str, list);
    }

    public final void query(String q, List<SettingsIndex.Entry> with) {
        Intrinsics.checkNotNullParameter(q, "q");
        if (with != null) {
            this.entries = with;
            this.crawled = true;
        }
        this.field.setText(q);
    }

    private final void remember() {
        String q = this.field.getText().toString();
        if (StringsKt.isBlank(q)) {
            return;
        }
        this.history.record(q, System.currentTimeMillis());
        saveHistory();
    }

    private final void showHistory() {
        this.grid.setVisibility(8);
        this.resultsScroll.setVisibility(0);
        this.results.removeAllViews();
        final List past = this.history.list();
        LinearLayout head = new LinearLayout(getContext());
        head.setOrientation(0);
        head.setGravity(16);
        head.setPadding(this.ui.dp(6), 0, this.ui.dp(6), this.ui.dp(8));
        TextView $this$showHistory_u24lambda_u2413 = new TextView(getContext());
        $this$showHistory_u24lambda_u2413.setText(past.isEmpty() ? "No searches yet" : "Your searches - most used first");
        $this$showHistory_u24lambda_u2413.setTextColor(CarUi.Companion.getDIM());
        $this$showHistory_u24lambda_u2413.setTextSize(16.0f);
        head.addView($this$showHistory_u24lambda_u2413, new LinearLayout.LayoutParams(0, -2, 1.0f));
        if (past.size() > 1) {
            final TextView $this$showHistory_u24lambda_u2416 = new TextView(getContext());
            $this$showHistory_u24lambda_u2416.setText("Clear all");
            $this$showHistory_u24lambda_u2416.setTextColor(CarUi.Companion.getDIM());
            $this$showHistory_u24lambda_u2416.setTextSize(16.0f);
            $this$showHistory_u24lambda_u2416.setPadding(this.ui.dp(12), this.ui.dp(8), this.ui.dp(4), this.ui.dp(8));
            $this$showHistory_u24lambda_u2416.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.SettingsHomeView$$ExternalSyntheticLambda6
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SettingsHomeView.showHistory$lambda$16$lambda$15($this$showHistory_u24lambda_u2416, past, this, view);
                }
            });
            head.addView($this$showHistory_u24lambda_u2416);
        }
        this.results.addView(head);
        if (past.isEmpty()) {
            this.results.addView(this.ui.note("What you search for here is kept on the tablet, so it's one tap next time."));
            return;
        }
        for (final SearchHistory.Item h : past) {
            CarUi carUi = this.ui;
            String text = h.getText();
            String str = h.getCount() > 1 ? "searched " + h.getCount() + " times" : "searched once";
            SvIcon svIcon = SvIcon.INSTANCE;
            Context context = getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            LinearLayout row = (LinearLayout) CarUi.row$default(carUi, text, str, 0, SvIcon.chip$default(svIcon, context, "history", "tablet", 46, null, 16, null), null, 20, null).component1();
            TextView $this$showHistory_u24lambda_u2418 = new TextView(getContext());
            $this$showHistory_u24lambda_u2418.setText("✕");
            $this$showHistory_u24lambda_u2418.setTextSize(22.0f);
            $this$showHistory_u24lambda_u2418.setTextColor(CarUi.Companion.getDIM());
            $this$showHistory_u24lambda_u2418.setPadding(this.ui.dp(18), this.ui.dp(10), this.ui.dp(10), this.ui.dp(10));
            $this$showHistory_u24lambda_u2418.setContentDescription("Remove " + h.getText() + " from the history");
            $this$showHistory_u24lambda_u2418.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.SettingsHomeView$$ExternalSyntheticLambda7
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SettingsHomeView.showHistory$lambda$18$lambda$17(SettingsHomeView.this, h, view);
                }
            });
            row.addView($this$showHistory_u24lambda_u2418);
            row.setClickable(true);
            row.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.SettingsHomeView$$ExternalSyntheticLambda8
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SettingsHomeView.showHistory$lambda$19(SettingsHomeView.this, h, view);
                }
            });
            this.results.addView(row, CarUi.lp$default(this.ui, 0, 0, 3, null));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showHistory$lambda$16$lambda$15(TextView $this_apply, List $past, final SettingsHomeView this$0, View it) {
        new AlertDialog.Builder($this_apply.getContext()).setMessage("Clear all " + $past.size() + " searches?").setPositiveButton("Clear", new DialogInterface.OnClickListener() { // from class: com.starpilot.starview.SettingsHomeView$$ExternalSyntheticLambda9
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                SettingsHomeView.showHistory$lambda$16$lambda$15$lambda$14(this$0, dialogInterface, i);
            }
        }).setNegativeButton("Keep", (DialogInterface.OnClickListener) null).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showHistory$lambda$16$lambda$15$lambda$14(SettingsHomeView this$0, DialogInterface dialogInterface, int i) {
        this$0.history.clear();
        this$0.saveHistory();
        this$0.showHistory();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showHistory$lambda$18$lambda$17(SettingsHomeView this$0, SearchHistory.Item $h, View it) {
        this$0.history.remove($h.getText());
        this$0.saveHistory();
        this$0.showHistory();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showHistory$lambda$19(SettingsHomeView this$0, SearchHistory.Item $h, View it) {
        this$0.field.setText($h.getText());
        this$0.field.setSelection($h.getText().length());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showResults() {
        String q = this.field.getText().toString();
        if (StringsKt.isBlank(q) && this.field.hasFocus()) {
            showHistory();
            return;
        }
        if (StringsKt.isBlank(q)) {
            this.resultsScroll.setVisibility(8);
            this.grid.setVisibility(0);
            return;
        }
        this.grid.setVisibility(8);
        this.resultsScroll.setVisibility(0);
        this.results.removeAllViews();
        List<SettingsIndex.Entry> hits = SettingsIndex.search$default(SettingsIndex.INSTANCE, this.entries, q, 0, 4, null);
        if (hits.isEmpty()) {
            this.results.addView(this.ui.note(this.entries.isEmpty() ? "Reading the settings…" : "Nothing matches \"" + q + "\""));
            return;
        }
        for (final SettingsIndex.Entry e : hits) {
            SettingIcons.Icon ic = e.getIcon();
            if (ic == null) {
                ic = new SettingIcons.Icon("info", "device", false, 4, null);
            }
            CarUi carUi = this.ui;
            String label = e.getLabel();
            Iterable $this$filter$iv = CollectionsKt.listOf((Object[]) new String[]{e.getWhere(), e.getValue()});
            Collection destination$iv$iv = new ArrayList();
            for (Object element$iv$iv : $this$filter$iv) {
                String it = (String) element$iv$iv;
                if (!StringsKt.isBlank(it)) {
                    destination$iv$iv.add(element$iv$iv);
                }
            }
            String joinToString$default = CollectionsKt.joinToString((List) destination$iv$iv, "  ·  ", "", "", -1, "...", null);
            SvIcon svIcon = SvIcon.INSTANCE;
            Context context = getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            LinearLayout row = (LinearLayout) CarUi.row$default(carUi, label, joinToString$default, 0, SvIcon.chip$default(svIcon, context, ic.getName(), ic.getGroup(), 46, null, 16, null), null, 20, null).component1();
            TextView $this$showResults_u24lambda_u2421 = new TextView(getContext());
            $this$showResults_u24lambda_u2421.setText("›");
            $this$showResults_u24lambda_u2421.setTextSize(30.0f);
            $this$showResults_u24lambda_u2421.setTextColor(CarUi.Companion.getDIM());
            $this$showResults_u24lambda_u2421.setPadding(this.ui.dp(14), 0, this.ui.dp(6), 0);
            row.addView($this$showResults_u24lambda_u2421);
            row.setClickable(true);
            row.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.SettingsHomeView$$ExternalSyntheticLambda10
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SettingsHomeView.showResults$lambda$22(SettingsHomeView.this, e, view);
                }
            });
            if (this.addShortcut != null) {
                row.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.starpilot.starview.SettingsHomeView$$ExternalSyntheticLambda1
                    @Override // android.view.View.OnLongClickListener
                    public final boolean onLongClick(View view) {
                        boolean showResults$lambda$23;
                        showResults$lambda$23 = SettingsHomeView.showResults$lambda$23(SettingsHomeView.this, e, view);
                        return showResults$lambda$23;
                    }
                });
            }
            this.results.addView(row, CarUi.lp$default(this.ui, 0, 0, 3, null));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showResults$lambda$22(SettingsHomeView this$0, SettingsIndex.Entry $e, View it) {
        this$0.remember();
        this$0.open.invoke($e.getTarget());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean showResults$lambda$23(SettingsHomeView this$0, SettingsIndex.Entry $e, View it) {
        this$0.remember();
        this$0.addShortcut.invoke($e);
        this$0.field.setText("");
        this$0.field.clearFocus();
        return true;
    }
}
