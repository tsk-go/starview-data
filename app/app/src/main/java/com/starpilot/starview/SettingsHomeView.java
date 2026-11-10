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
import android.graphics.drawable.Drawable;
import android.view.ViewGroup.LayoutParams;

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

    public /* synthetic */ SettingsHomeView(Context context, CarUi carUi, View view, Function0 function0, Function1 function1, Function1 function12, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, carUi, view, function0, (i & 16) != 0 ? null : function1, function12);
    }

    /* VF: from Vineflower (jadx: ) */
    public SettingsHomeView(
        Context var1,
        CarUi var2,
        View var3,
        Function0<? extends List<SettingsIndex.Entry>> var4,
        Function1<? super SettingsIndex.Entry, Unit> var5,
        Function1<? super String, Unit> var6
    ) {
        Intrinsics.checkNotNullParameter(var1, "ctx");
        Intrinsics.checkNotNullParameter(var2, "ui");
        Intrinsics.checkNotNullParameter(var3, "grid");
        Intrinsics.checkNotNullParameter(var4, "extraEntries");
        Intrinsics.checkNotNullParameter(var6, "open");
        this.ui = var2;
        this.grid = var3;
        this.extraEntries = (Function0) var4;
        this.addShortcut = (Function1) var5;
        this.open = (Function1) var6;
        LinearLayout var8 = new LinearLayout(var1);
        var8.setOrientation(1);
        this.results = var8;
        ScrollView var9 = new ScrollView(var1);
        var9.addView((View)this.results);
        var9.setVisibility(8);
        this.resultsScroll = var9;
        this.entries = CollectionsKt.emptyList();
        this.main = new Handler(Looper.getMainLooper());
        this.prefs = new UiSettings(var1);
        this.history = new SearchHistory(this.prefs.getStr("settings_search_history", "[]"));
        this.setOrientation(1);
        LinearLayout var13 = new LinearLayout(var1);
        var13.setOrientation(0);
        var13.setGravity(16);
        var13.setBackground((Drawable)CarUi.rounded$default(this.ui, CarUi.Companion.getCARD(), 18, null, 4, null));
        var13.setPadding(this.ui.dp(18), 0, this.ui.dp(10), 0);
        View var10 = new View(var1);
        var10.setBackground(SvIcon.drawable$default(SvIcon.INSTANCE, "eye", CarUi.Companion.getDIM(), 0, 4, null));
        var13.addView(var10, (android.view.ViewGroup.LayoutParams)(new android.widget.LinearLayout.LayoutParams(this.ui.dp(26), this.ui.dp(26))));
        EditText var11 = new EditText(var1);
        var11.setHint("Search every setting - comma and tablet");
        var11.setHintTextColor(CarUi.Companion.getDIM());
        var11.setTextColor(CarUi.Companion.getTEXT());
        var11.setTextSize(20.0F);
        var11.setBackground(null);
        var11.setSingleLine(true);
        var11.setImeOptions(3);
        var11.setPadding(this.ui.dp(14), this.ui.dp(16), this.ui.dp(8), this.ui.dp(16));
        var11.setOnFocusChangeListener(new SettingsHomeView$$ExternalSyntheticLambda0(this));
        var11.setOnClickListener(new SettingsHomeView$$ExternalSyntheticLambda2(this));
        var11.setOnEditorActionListener(new SettingsHomeView$$ExternalSyntheticLambda3(this));
        this.field = var11;
        var13.addView((View)this.field, (android.view.ViewGroup.LayoutParams)(new android.widget.LinearLayout.LayoutParams(0, -2, 1.0F)));
        TextView var12 = new TextView(var1);
        var12.setText("✕");
        var12.setTextSize(22.0F);
        var12.setTextColor(CarUi.Companion.getDIM());
        var12.setPadding(this.ui.dp(14), this.ui.dp(8), this.ui.dp(10), this.ui.dp(8));
        var12.setVisibility(8);
        var12.setOnClickListener(new SettingsHomeView$$ExternalSyntheticLambda4(this));
        var13.addView((View)var12);
        View var15 = (View)var13;
        android.widget.LinearLayout.LayoutParams var16 = new android.widget.LinearLayout.LayoutParams(-1, -2);
        var16.bottomMargin = this.ui.dp(16);
        Unit var14 = Unit.INSTANCE;
        this.addView(var15, (android.view.ViewGroup.LayoutParams)var16);
        this.field.addTextChangedListener(new TextWatcher() {
            final TextView $clear = var12;
            final SettingsHomeView this$0 = SettingsHomeView.this;

            public void afterTextChanged(Editable var1) {
                TextView var4x = this.$clear;
                CharSequence var5x = (CharSequence)var1;
                byte var3x = 0;
                boolean var2x;
                if (var5x != null && var5x.length() != 0) {
                    var2x = false;
                } else {
                    var2x = true;
                }

                if (var2x) {
                    var3x = 8;
                }

                var4x.setVisibility(var3x);
                this.this$0.startIndex();
                this.this$0.showResults();
            }

            public void beforeTextChanged(CharSequence var1, int var2x, int var3x, int var4x) {
            }

            public void onTextChanged(CharSequence var1, int var2x, int var3x, int var4x) {
            }
        });
        FrameLayout var7 = new FrameLayout(var1);
        var7.addView(this.grid, (android.view.ViewGroup.LayoutParams)(new android.widget.FrameLayout.LayoutParams(-1, -1)));
        var7.addView((View)this.resultsScroll, (android.view.ViewGroup.LayoutParams)(new android.widget.FrameLayout.LayoutParams(-1, -1)));
        this.addView((View)var7, (android.view.ViewGroup.LayoutParams)(new android.widget.LinearLayout.LayoutParams(-1, 0, 1.0F)));
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

    /* VF: from Vineflower (jadx: ) */
    public static void query$default(SettingsHomeView var0, String var1, List var2, int var3, Object var4) {
        if ((var3 & 2) != 0) {
            var2 = null;
        }

        var0.query(var1, var2);
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
                    SettingsHomeView.showHistory$lambda$16$lambda$15($this$showHistory_u24lambda_u2416, past, SettingsHomeView.this, view);
                }
            });
            head.addView($this$showHistory_u24lambda_u2416);
        }
        this.results.addView(head);
        if (past.isEmpty()) {
            this.results.addView(this.ui.note("What you search for here is kept on the tablet, so it's one tap next time."));
            return;
        }
        for (final SearchHistory.Item h : (Iterable<SearchHistory.Item>) past) {
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
            Iterable $this$filter$iv = CollectionsKt.listOf(new String[]{e.getWhere(), e.getValue()});
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
