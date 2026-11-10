package com.starpilot.starview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.starpilot.starview.SettingIcons;
import com.starpilot.starview.SettingsIndex;
import com.starpilot.starview.SvIcon;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.json.JSONObject;

/* compiled from: SettingsHome.kt */
public final class DrivingMapView extends ScrollView {
    private HashMap<String, String> hubTitles;
    private final Function2<String, String, Unit> open;
    private final HashMap<String, Shown> shown;
    private final CarUi ui;
    public static final Companion Companion = new Companion(null);
    private static final List<Pair<String, List<Card>>> COLUMNS = CollectionsKt.listOf((Object[]) new Pair[]{TuplesKt.to("Speed", CollectionsKt.listOf((Object[]) new Card[]{new Card("profiles", "Long Planner"), new Card("slc", "Speed Limit Controller"), new Card("curve", "Curve Speed Controller"), new Card("conditional", "Conditional Driving Modes")})), TuplesKt.to("Steering", CollectionsKt.listOf((Object[]) new Card[]{new Card("lane", "Lane Centering"), new Card("lane_change", "Lane Changes"), new Card("torque", "Steering and Torque"), new Card("aol", "Always On Lateral")})), TuplesKt.to("Personalities", CollectionsKt.listOf((Object[]) new Card[]{new Card("traffic", "Traffic"), new Card("aggressive", "Aggressive"), new Card("standard", "Standard"), new Card("relaxed", "Relaxed")})), TuplesKt.to("Buttons", CollectionsKt.listOf(new Card("wheel", "Wheel Controls")))});

    /* compiled from: SettingsHome.kt */
    public static final class Card {
        private final String page;
        private final String title;

        public Card(String page, String title) {
            Intrinsics.checkNotNullParameter(page, "page");
            Intrinsics.checkNotNullParameter(title, "title");
            this.page = page;
            this.title = title;
        }

        public final String getPage() {
            return this.page;
        }

        public final String getTitle() {
            return this.title;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public DrivingMapView(Context ctx, CarUi ui, Function2<? super String, ? super String, Unit> open) {
        super(ctx);
        Context ctx2 = ctx;
        Intrinsics.checkNotNullParameter(ctx2, "ctx");
        Intrinsics.checkNotNullParameter(ui, "ui");
        Intrinsics.checkNotNullParameter(open, "open");
        this.ui = ui;
        this.open = open;
        this.shown = new HashMap<>();
        this.hubTitles = new HashMap<>();
        LinearLayout cols = new LinearLayout(ctx2);
        int i = 0;
        cols.setOrientation(0);
        int i2 = 4;
        cols.setPadding(this.ui.dp(4), this.ui.dp(4), this.ui.dp(4), this.ui.dp(40));
        for (Pair<String, List<Card>> pair : COLUMNS) {
            String head = pair.component1();
            List cards = pair.component2();
            LinearLayout c = new LinearLayout(ctx2);
            c.setOrientation(1);
            TextView $this$_init__u24lambda_u242 = this.ui.header(head);
            $this$_init__u24lambda_u242.setPadding(this.ui.dp(6), this.ui.dp(i2), i, this.ui.dp(8));
            c.addView($this$_init__u24lambda_u242);
            for (Card card : cards) {
                View card2 = card(card);
                LinearLayout.LayoutParams $this$_init__u24lambda_u243 = new LinearLayout.LayoutParams(-1, -2);
                $this$_init__u24lambda_u243.bottomMargin = this.ui.dp(10);
                Unit unit = Unit.INSTANCE;
                c.addView(card2, $this$_init__u24lambda_u243);
            }
            LinearLayout.LayoutParams $this$_init__u24lambda_u244 = new LinearLayout.LayoutParams(0, -2, 1.0f);
            $this$_init__u24lambda_u244.leftMargin = this.ui.dp(6);
            $this$_init__u24lambda_u244.rightMargin = this.ui.dp(6);
            Unit unit2 = Unit.INSTANCE;
            cols.addView(c, $this$_init__u24lambda_u244);
            ctx2 = ctx;
            i = 0;
            i2 = 4;
        }
        addView(cols);
    }

    /* compiled from: SettingsHome.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final List<Pair<String, List<Card>>> getCOLUMNS() {
            return DrivingMapView.COLUMNS;
        }
    }

    /* compiled from: SettingsHome.kt */
    private static final class Shown {
        private final LinearLayout box;
        private final SvIcon.Chip chip;
        private final TextView line;

        public Shown(LinearLayout box, SvIcon.Chip chip, TextView line) {
            Intrinsics.checkNotNullParameter(box, "box");
            Intrinsics.checkNotNullParameter(chip, "chip");
            Intrinsics.checkNotNullParameter(line, "line");
            this.box = box;
            this.chip = chip;
            this.line = line;
        }

        public final LinearLayout getBox() {
            return this.box;
        }

        public final SvIcon.Chip getChip() {
            return this.chip;
        }

        public final TextView getLine() {
            return this.line;
        }
    }

    private final View card(final Card c) {
        SettingIcons.Icon ic = SettingIcons.INSTANCE.forPage(c.getPage());
        SvIcon svIcon = SvIcon.INSTANCE;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        View chip$default = SvIcon.chip$default(svIcon, context, ic.getName(), ic.getGroup(), 46, null, 16, null);
        Intrinsics.checkNotNull(chip$default, "null cannot be cast to non-null type com.starpilot.starview.SvIcon.Chip");
        SvIcon.Chip chip = (SvIcon.Chip) chip$default;
        TextView $this$card_u24lambda_u245 = new TextView(getContext());
        $this$card_u24lambda_u245.setText(c.getTitle());
        $this$card_u24lambda_u245.setTextColor(CarUi.Companion.getTEXT());
        $this$card_u24lambda_u245.setTextSize(19.0f);
        $this$card_u24lambda_u245.setTypeface(Typeface.DEFAULT_BOLD);
        TextView $this$card_u24lambda_u246 = new TextView(getContext());
        $this$card_u24lambda_u246.setText("…");
        $this$card_u24lambda_u246.setTextColor(CarUi.Companion.getDIM());
        $this$card_u24lambda_u246.setTextSize(15.0f);
        $this$card_u24lambda_u246.setMaxLines(2);
        LinearLayout $this$card_u24lambda_u2410 = new LinearLayout(getContext());
        $this$card_u24lambda_u2410.setOrientation(0);
        $this$card_u24lambda_u2410.setGravity(16);
        $this$card_u24lambda_u2410.setPadding(this.ui.dp(14), this.ui.dp(14), this.ui.dp(12), this.ui.dp(14));
        $this$card_u24lambda_u2410.setBackground(this.ui.rounded(CarUi.Companion.getCARD(), 18, Integer.valueOf(Color.rgb(34, 40, 55))));
        $this$card_u24lambda_u2410.setMinimumHeight(this.ui.dp(92));
        LinearLayout.LayoutParams $this$card_u24lambda_u2410_u24lambda_u247 = new LinearLayout.LayoutParams(this.ui.dp(46), this.ui.dp(46));
        $this$card_u24lambda_u2410_u24lambda_u247.rightMargin = this.ui.dp(14);
        Unit unit = Unit.INSTANCE;
        $this$card_u24lambda_u2410.addView(chip, $this$card_u24lambda_u2410_u24lambda_u247);
        LinearLayout $this$card_u24lambda_u2410_u24lambda_u248 = new LinearLayout($this$card_u24lambda_u2410.getContext());
        $this$card_u24lambda_u2410_u24lambda_u248.setOrientation(1);
        $this$card_u24lambda_u2410_u24lambda_u248.addView($this$card_u24lambda_u245);
        $this$card_u24lambda_u2410_u24lambda_u248.addView($this$card_u24lambda_u246);
        $this$card_u24lambda_u2410.addView($this$card_u24lambda_u2410_u24lambda_u248, new LinearLayout.LayoutParams(0, -2, 1.0f));
        $this$card_u24lambda_u2410.setClickable(true);
        $this$card_u24lambda_u2410.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.DrivingMapView$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DrivingMapView.card$lambda$10$lambda$9(DrivingMapView.this, c, view);
            }
        });
        this.shown.put(c.getPage(), new Shown($this$card_u24lambda_u2410, chip, $this$card_u24lambda_u246));
        return $this$card_u24lambda_u2410;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void card$lambda$10$lambda$9(DrivingMapView this$0, Card $c, View it) {
        Function2<String, String, Unit> function2 = this$0.open;
        String page = $c.getPage();
        String str = this$0.hubTitles.get($c.getPage());
        if (str == null) {
            str = $c.getTitle();
        }
        function2.invoke(page, str);
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0029, code lost:
    
        if (r3 != null) goto L18;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void paint(String page, SettingsIndex.Summary s) {
        String str;
        String line;
        Intrinsics.checkNotNullParameter(page, "page");
        Shown v = this.shown.get(page);
        if (v == null) {
            return;
        }
        TextView line2 = v.getLine();
        if (s != null && (line = s.getLine()) != null) {
            String str2 = line;
            if (StringsKt.isBlank(str2)) {
                str2 = null;
            }
            str = str2;
        }
        str = s == null ? "Not available" : "Open to adjust";
        line2.setText(str);
        Boolean on = s != null ? s.getOn() : null;
        v.getBox().setBackground(Intrinsics.areEqual((Object) on, (Object) true) ? this.ui.rounded(Color.rgb(19, 34, 25), 18, Integer.valueOf(Color.rgb(43, 107, 75))) : this.ui.rounded(CarUi.Companion.getCARD(), 18, Integer.valueOf(Color.rgb(34, 40, 55))));
        SvIcon.Chip.set$default(v.getChip(), null, null, Intrinsics.areEqual((Object) on, (Object) false) ? SvIcon.State.OFF : s == null ? SvIcon.State.NA : SvIcon.State.ON, 3, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit refresh$lambda$13(DrivingMapView this$0, JSONObject o) {
        if (o != null) {
            for (GalaxyRow r : GalaxyRow.Companion.parse(o)) {
                if (r.getPage().length() > 0) {
                    this$0.hubTitles.put(r.getPage(), r.getLabel());
                }
            }
        }
        return Unit.INSTANCE;
    }

    public final void refresh() {
        GalaxyPages.get$default(GalaxyPages.INSTANCE, "hub", 0L, new Function1() { // from class: com.starpilot.starview.DrivingMapView$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit refresh$lambda$13;
                refresh$lambda$13 = DrivingMapView.refresh$lambda$13(DrivingMapView.this, (JSONObject) obj);
                return refresh$lambda$13;
            }
        }, 2, null);
        Set<String> keySet = this.shown.keySet();
        Intrinsics.checkNotNullExpressionValue(keySet, "<get-keys>(...)");
        for (final String p : CollectionsKt.toList(keySet)) {
            GalaxyPages.INSTANCE.summary(p, new Function1() { // from class: com.starpilot.starview.DrivingMapView$$ExternalSyntheticLambda2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    Unit refresh$lambda$14;
                    refresh$lambda$14 = DrivingMapView.refresh$lambda$14(DrivingMapView.this, p, (SettingsIndex.Summary) obj);
                    return refresh$lambda$14;
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit refresh$lambda$14(DrivingMapView this$0, String $p, SettingsIndex.Summary s) {
        this$0.paint($p, s);
        return Unit.INSTANCE;
    }
}
