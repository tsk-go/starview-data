package com.starpilot.starview;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: JournalPages.kt */
public class CardPage extends ScrollView {
    private final LinearLayout col;
    private final Handler main;
    private final CarUi ui;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CardPage(Context ctx, CarUi ui) {
        super(ctx);
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(ui, "ui");
        this.ui = ui;
        LinearLayout $this$col_u24lambda_u240 = new LinearLayout(ctx);
        $this$col_u24lambda_u240.setOrientation(1);
        $this$col_u24lambda_u240.setPadding(this.ui.dp(4), this.ui.dp(4), this.ui.dp(4), this.ui.dp(40));
        this.col = $this$col_u24lambda_u240;
        this.main = new Handler(Looper.getMainLooper());
        addView(this.col);
    }

    protected final CarUi getUi() {
        return this.ui;
    }

    protected final LinearLayout getCol() {
        return this.col;
    }

    protected final Handler getMain() {
        return this.main;
    }

    protected final LinearLayout card() {
        LinearLayout $this$card_u24lambda_u241 = new LinearLayout(getContext());
        $this$card_u24lambda_u241.setOrientation(1);
        $this$card_u24lambda_u241.setBackground(CarUi.rounded$default(this.ui, CarUi.Companion.getCARD(), 18, null, 4, null));
        $this$card_u24lambda_u241.setPadding(this.ui.dp(20), this.ui.dp(16), this.ui.dp(20), this.ui.dp(16));
        return $this$card_u24lambda_u241;
    }

    protected final View title(String text, String icon, String group) {
        Intrinsics.checkNotNullParameter(text, "text");
        Intrinsics.checkNotNullParameter(icon, "icon");
        Intrinsics.checkNotNullParameter(group, "group");
        LinearLayout $this$title_u24lambda_u244 = new LinearLayout(getContext());
        $this$title_u24lambda_u244.setOrientation(0);
        $this$title_u24lambda_u244.setGravity(16);
        $this$title_u24lambda_u244.setPadding(0, 0, 0, this.ui.dp(8));
        SvIcon svIcon = SvIcon.INSTANCE;
        Context context = $this$title_u24lambda_u244.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        View chip$default = SvIcon.chip$default(svIcon, context, icon, group, 40, null, 16, null);
        LinearLayout.LayoutParams $this$title_u24lambda_u244_u24lambda_u242 = new LinearLayout.LayoutParams(this.ui.dp(40), this.ui.dp(40));
        $this$title_u24lambda_u244_u24lambda_u242.rightMargin = this.ui.dp(12);
        Unit unit = Unit.INSTANCE;
        $this$title_u24lambda_u244.addView(chip$default, $this$title_u24lambda_u244_u24lambda_u242);
        TextView $this$title_u24lambda_u244_u24lambda_u243 = new TextView($this$title_u24lambda_u244.getContext());
        $this$title_u24lambda_u244_u24lambda_u243.setText(text);
        $this$title_u24lambda_u244_u24lambda_u243.setTextColor(CarUi.Companion.getTEXT());
        $this$title_u24lambda_u244_u24lambda_u243.setTextSize(20.0f);
        $this$title_u24lambda_u244_u24lambda_u243.setTypeface(Typeface.DEFAULT_BOLD);
        $this$title_u24lambda_u244.addView($this$title_u24lambda_u244_u24lambda_u243);
        return $this$title_u24lambda_u244;
    }

    public static /* synthetic */ TextView line$default(CardPage cardPage, String str, int i, float f, int i2, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: line");
        }
        if ((i2 & 2) != 0) {
            i = CarUi.Companion.getDIM();
        }
        if ((i2 & 4) != 0) {
            f = 16.0f;
        }
        return cardPage.line(str, i, f);
    }

    protected final TextView line(String text, int color, float size) {
        Intrinsics.checkNotNullParameter(text, "text");
        TextView $this$line_u24lambda_u245 = new TextView(getContext());
        $this$line_u24lambda_u245.setText(text);
        $this$line_u24lambda_u245.setTextColor(color);
        $this$line_u24lambda_u245.setTextSize(size);
        $this$line_u24lambda_u245.setPadding(0, this.ui.dp(3), 0, this.ui.dp(3));
        return $this$line_u24lambda_u245;
    }

    protected final View stat(String big, String small) {
        Intrinsics.checkNotNullParameter(big, "big");
        Intrinsics.checkNotNullParameter(small, "small");
        LinearLayout $this$stat_u24lambda_u248 = new LinearLayout(getContext());
        $this$stat_u24lambda_u248.setOrientation(1);
        $this$stat_u24lambda_u248.setPadding(0, 0, this.ui.dp(30), 0);
        TextView $this$stat_u24lambda_u248_u24lambda_u246 = new TextView($this$stat_u24lambda_u248.getContext());
        $this$stat_u24lambda_u248_u24lambda_u246.setText(big);
        $this$stat_u24lambda_u248_u24lambda_u246.setTextColor(CarUi.Companion.getTEXT());
        $this$stat_u24lambda_u248_u24lambda_u246.setTextSize(28.0f);
        $this$stat_u24lambda_u248_u24lambda_u246.setTypeface(Typeface.DEFAULT_BOLD);
        $this$stat_u24lambda_u248.addView($this$stat_u24lambda_u248_u24lambda_u246);
        TextView $this$stat_u24lambda_u248_u24lambda_u247 = new TextView($this$stat_u24lambda_u248.getContext());
        $this$stat_u24lambda_u248_u24lambda_u247.setText(small);
        $this$stat_u24lambda_u248_u24lambda_u247.setTextColor(CarUi.Companion.getDIM());
        $this$stat_u24lambda_u248_u24lambda_u247.setTextSize(14.0f);
        $this$stat_u24lambda_u248.addView($this$stat_u24lambda_u248_u24lambda_u247);
        return $this$stat_u24lambda_u248;
    }

    protected final View stats(Pair<String, String>... s) {
        Intrinsics.checkNotNullParameter(s, "s");
        LinearLayout $this$stats_u24lambda_u249 = new LinearLayout(getContext());
        $this$stats_u24lambda_u249.setOrientation(0);
        for (Pair<String, String> pair : s) {
            String a = pair.component1();
            String b = pair.component2();
            $this$stats_u24lambda_u249.addView(stat(a, b));
        }
        return $this$stats_u24lambda_u249;
    }

    protected final void add(View v) {
        Intrinsics.checkNotNullParameter(v, "v");
        this.col.addView(v, CarUi.lp$default(this.ui, 0, 12, 1, null));
    }

    public void refresh() {
    }
}
