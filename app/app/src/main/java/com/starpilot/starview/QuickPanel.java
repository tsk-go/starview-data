package com.starpilot.starview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.starpilot.starview.CarUi;
import com.starpilot.starview.SvIcon;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: QuickPanel.kt */
public final class QuickPanel extends FrameLayout {
    private final Actions a;
    private final GridLayout grid;
    private final CarUi ui;

    /* compiled from: QuickPanel.kt */
    public interface Actions {
        void close();

        void door(String cmd);

        boolean experimental();

        boolean focus();

        void listen();

        boolean mapOpen();

        int personality();

        boolean saver();

        boolean sceneOn();

        void setPersonality(int p);

        void toggleExperimental();

        void toggleFocus();

        void toggleMap();

        void toggleSaver();

        void toggleScene();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public QuickPanel(Context ctx, Actions a) {
        super(ctx);
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(a, "a");
        this.a = a;
        this.ui = new CarUi(ctx, false, 2, null);
        GridLayout $this$grid_u24lambda_u240 = new GridLayout(ctx);
        $this$grid_u24lambda_u240.setColumnCount(3);
        this.grid = $this$grid_u24lambda_u240;
        setBackgroundColor(Color.argb(215, 6, 8, 12));
        setClickable(true);
        setTranslationZ(this.ui.dp(150));
        setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.QuickPanel$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                QuickPanel._init_$lambda$1(QuickPanel.this, view);
            }
        });
        LinearLayout box = new LinearLayout(ctx);
        box.setOrientation(1);
        box.setBackground(CarUi.rounded$default(this.ui, CarUi.Companion.getFRAME(), 26, null, 4, null));
        box.setPadding(this.ui.dp(24), this.ui.dp(20), this.ui.dp(24), this.ui.dp(24));
        box.setClickable(true);
        LinearLayout $this$_init__u24lambda_u245 = new LinearLayout(ctx);
        $this$_init__u24lambda_u245.setOrientation(0);
        $this$_init__u24lambda_u245.setGravity(16);
        TextView $this$lambda_u245_u24lambda_u243 = new TextView(ctx);
        $this$lambda_u245_u24lambda_u243.setText("Quick settings");
        $this$lambda_u245_u24lambda_u243.setTextColor(CarUi.Companion.getTEXT());
        $this$lambda_u245_u24lambda_u243.setTextSize(26.0f);
        $this$lambda_u245_u24lambda_u243.setTypeface(Typeface.DEFAULT_BOLD);
        $this$_init__u24lambda_u245.addView($this$lambda_u245_u24lambda_u243, new LinearLayout.LayoutParams(0, -2, 1.0f));
        $this$_init__u24lambda_u245.addView(this.ui.button("Close", true, new Function0() { // from class: com.starpilot.starview.QuickPanel$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return QuickPanel.lambda$5$lambda$4(QuickPanel.this);
            }
        }));
        box.addView($this$_init__u24lambda_u245);
        GridLayout gridLayout = this.grid;
        LinearLayout.LayoutParams $this$_init__u24lambda_u246 = new LinearLayout.LayoutParams(-1, -2);
        $this$_init__u24lambda_u246.topMargin = this.ui.dp(14);
        Unit unit = Unit.INSTANCE;
        box.addView(gridLayout, $this$_init__u24lambda_u246);
        addView(box, new FrameLayout.LayoutParams(this.ui.dp(920), -2, 17));
        paint();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void _init_$lambda$1(QuickPanel this$0, View it) {
        this$0.a.close();
    }

    static final Unit lambda$5$lambda$4(QuickPanel this$0) {
        this$0.a.close();
        return Unit.INSTANCE;
    }

    private final View tile(String label, String sub, String icon, String group, Boolean on, final Function0<Unit> onTap) {
        int i;
        int i2;
        int i3;
        boolean lit = Intrinsics.areEqual((Object) on, (Object) true);
        final LinearLayout $this$tile_u24lambda_u2413 = new LinearLayout(getContext());
        $this$tile_u24lambda_u2413.setOrientation(0);
        $this$tile_u24lambda_u2413.setGravity(16);
        $this$tile_u24lambda_u2413.setMinimumHeight(this.ui.dp(112));
        $this$tile_u24lambda_u2413.setPadding(this.ui.dp(18), this.ui.dp(14), this.ui.dp(14), this.ui.dp(14));
        CarUi carUi = this.ui;
        int rgb = lit ? Color.rgb(19, 34, 25) : CarUi.Companion.getCARD();
        if (lit) {
            i = 107;
            i2 = 75;
            i3 = 43;
        } else {
            i = 46;
            i2 = 58;
            i3 = 40;
        }
        $this$tile_u24lambda_u2413.setBackground(carUi.rounded(rgb, 20, Integer.valueOf(Color.rgb(i3, i, i2))));
        SvIcon svIcon = SvIcon.INSTANCE;
        Context context = $this$tile_u24lambda_u2413.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        View chip = svIcon.chip(context, icon, group, 56, Intrinsics.areEqual((Object) on, (Object) false) ? SvIcon.State.OFF : SvIcon.State.ON);
        LinearLayout.LayoutParams $this$tile_u24lambda_u2413_u24lambda_u247 = new LinearLayout.LayoutParams(this.ui.dp(56), this.ui.dp(56));
        $this$tile_u24lambda_u2413_u24lambda_u247.rightMargin = this.ui.dp(14);
        Unit unit = Unit.INSTANCE;
        $this$tile_u24lambda_u2413.addView(chip, $this$tile_u24lambda_u2413_u24lambda_u247);
        LinearLayout $this$tile_u24lambda_u2413_u24lambda_u2410 = new LinearLayout($this$tile_u24lambda_u2413.getContext());
        $this$tile_u24lambda_u2413_u24lambda_u2410.setOrientation(1);
        TextView $this$tile_u24lambda_u2413_u24lambda_u2410_u24lambda_u248 = new TextView($this$tile_u24lambda_u2413_u24lambda_u2410.getContext());
        $this$tile_u24lambda_u2413_u24lambda_u2410_u24lambda_u248.setText(label);
        $this$tile_u24lambda_u2413_u24lambda_u2410_u24lambda_u248.setTextColor(CarUi.Companion.getTEXT());
        $this$tile_u24lambda_u2413_u24lambda_u2410_u24lambda_u248.setTextSize(21.0f);
        $this$tile_u24lambda_u2413_u24lambda_u2410_u24lambda_u248.setTypeface(Typeface.DEFAULT_BOLD);
        $this$tile_u24lambda_u2413_u24lambda_u2410.addView($this$tile_u24lambda_u2413_u24lambda_u2410_u24lambda_u248);
        TextView $this$tile_u24lambda_u2413_u24lambda_u2410_u24lambda_u249 = new TextView($this$tile_u24lambda_u2413_u24lambda_u2410.getContext());
        $this$tile_u24lambda_u2413_u24lambda_u2410_u24lambda_u249.setText(sub);
        CarUi.Companion_ companion = CarUi.Companion;
        $this$tile_u24lambda_u2413_u24lambda_u2410_u24lambda_u249.setTextColor(lit ? companion.getACCENT() : companion.getDIM());
        $this$tile_u24lambda_u2413_u24lambda_u2410_u24lambda_u249.setTextSize(16.0f);
        $this$tile_u24lambda_u2413_u24lambda_u2410.addView($this$tile_u24lambda_u2413_u24lambda_u2410_u24lambda_u249);
        $this$tile_u24lambda_u2413.addView($this$tile_u24lambda_u2413_u24lambda_u2410, new LinearLayout.LayoutParams(0, -2, 1.0f));
        $this$tile_u24lambda_u2413.setClickable(true);
        $this$tile_u24lambda_u2413.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.QuickPanel$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                QuickPanel.tile$lambda$13$lambda$12(Function0.this, $this$tile_u24lambda_u2413, this, view);
            }
        });
        return $this$tile_u24lambda_u2413;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void tile$lambda$13$lambda$12(Function0 $onTap, LinearLayout $this_apply, final QuickPanel this$0, View it) {
        $onTap.invoke();
        $this_apply.postDelayed(new Runnable() { // from class: com.starpilot.starview.QuickPanel$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                QuickPanel.this.paint();
            }
        }, 400L);
    }

    public final void paint() {
        this.grid.removeAllViews();
        final int p = this.a.personality();
        int i = 0;
        int i2 = 1;
        List names = CollectionsKt.listOf((Object[]) new String[]{"Aggressive", "Standard", "Relaxed"});
        View[] viewArr = new View[10];
        viewArr[0] = tile("Experimental", this.a.experimental() ? "On" : "Off - Chill", "conditional", "speed", Boolean.valueOf(this.a.experimental()), new Function0() { // from class: com.starpilot.starview.QuickPanel$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit paint$lambda$14;
                paint$lambda$14 = QuickPanel.paint$lambda$14(QuickPanel.this);
                return paint$lambda$14;
            }
        });
        String str = (p >= 0 && p < names.size() ? names.get(p) : "–") + "  ·  tap for next";
        Object obj = "standard";
        List listOf = CollectionsKt.listOf((Object[]) new String[]{"aggressive", "standard", "relaxed"});
        if (p >= 0 && p < listOf.size()) {
            obj = listOf.get(p);
        }
        viewArr[1] = tile("Following", str, (String) obj, "person", null, new Function0() { // from class: com.starpilot.starview.QuickPanel$$ExternalSyntheticLambda7
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit paint$lambda$17;
                paint$lambda$17 = QuickPanel.paint$lambda$17(QuickPanel.this, p);
                return paint$lambda$17;
            }
        });
        viewArr[2] = tile("Highway focus", this.a.focus() ? "On - only what matters" : "Off - all widgets", "eye", "display", Boolean.valueOf(this.a.focus()), new Function0() { // from class: com.starpilot.starview.QuickPanel$$ExternalSyntheticLambda8
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit paint$lambda$18;
                paint$lambda$18 = QuickPanel.paint$lambda$18(QuickPanel.this);
                return paint$lambda$18;
            }
        });
        viewArr[3] = tile("Map", this.a.mapOpen() ? "Open" : "Closed", "map", "tablet", Boolean.valueOf(this.a.mapOpen()), new Function0() { // from class: com.starpilot.starview.QuickPanel$$ExternalSyntheticLambda9
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit paint$lambda$19;
                paint$lambda$19 = QuickPanel.paint$lambda$19(QuickPanel.this);
                return paint$lambda$19;
            }
        });
        viewArr[4] = tile("Voice", Speech.INSTANCE.getEnabled() ? "Speaks alerts and turns" : "Chimes only", "speaker", "sound", Boolean.valueOf(Speech.INSTANCE.getEnabled()), new Function0() { // from class: com.starpilot.starview.QuickPanel$$ExternalSyntheticLambda10
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit paint$lambda$20;
                paint$lambda$20 = QuickPanel.paint$lambda$20();
                return paint$lambda$20;
            }
        });
        viewArr[5] = tile("View", this.a.sceneOn() ? "Scene (3D)" : "Camera", "camera", "cam", null, new Function0() { // from class: com.starpilot.starview.QuickPanel$$ExternalSyntheticLambda11
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit paint$lambda$21;
                paint$lambda$21 = QuickPanel.paint$lambda$21(QuickPanel.this);
                return paint$lambda$21;
            }
        });
        viewArr[6] = tile("Keep this drive", "Galaxy keeps its recording", "bookmark", "display", null, new Function0() { // from class: com.starpilot.starview.QuickPanel$$ExternalSyntheticLambda12
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit paint$lambda$22;
                paint$lambda$22 = QuickPanel.paint$lambda$22(QuickPanel.this);
                return paint$lambda$22;
            }
        });
        viewArr[7] = tile("Battery saver", this.a.saver() ? "On - other tablet apps asleep" : "Off", "battery", "tablet", Boolean.valueOf(this.a.saver()), new Function0() { // from class: com.starpilot.starview.QuickPanel$$ExternalSyntheticLambda13
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit paint$lambda$23;
                paint$lambda$23 = QuickPanel.paint$lambda$23(QuickPanel.this);
                return paint$lambda$23;
            }
        });
        viewArr[8] = tile("Lock doors", "In Park", "lock", "device", null, new Function0() { // from class: com.starpilot.starview.QuickPanel$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit paint$lambda$24;
                paint$lambda$24 = QuickPanel.paint$lambda$24(QuickPanel.this);
                return paint$lambda$24;
            }
        });
        viewArr[9] = tile("Unlock doors", "In Park", "lock", "device", null, new Function0() { // from class: com.starpilot.starview.QuickPanel$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit paint$lambda$25;
                paint$lambda$25 = QuickPanel.paint$lambda$25(QuickPanel.this);
                return paint$lambda$25;
            }
        });
        List<View> tiles = CollectionsKt.listOf((Object[]) viewArr);
        int i3 = 0;
        for (View t : tiles) {
            int i4 = i3;
            i3 += i2;
            GridLayout gridLayout = this.grid;
            GridLayout.LayoutParams $this$paint_u24lambda_u2426 = new GridLayout.LayoutParams(GridLayout.spec(i4 / 3), GridLayout.spec(i4 % 3, 1.0f));
            $this$paint_u24lambda_u2426.width = i;
            List tiles2 = tiles;
            $this$paint_u24lambda_u2426.setMargins(this.ui.dp(7), this.ui.dp(7), this.ui.dp(7), this.ui.dp(7));
            Unit unit = Unit.INSTANCE;
            gridLayout.addView(t, $this$paint_u24lambda_u2426);
            tiles = tiles2;
            i = 0;
            i2 = 1;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paint$lambda$14(QuickPanel this$0) {
        this$0.a.toggleExperimental();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paint$lambda$17(QuickPanel this$0, int $p) {
        this$0.a.setPersonality(($p + 1) % 3);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paint$lambda$18(QuickPanel this$0) {
        this$0.a.toggleFocus();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paint$lambda$19(QuickPanel this$0) {
        this$0.a.toggleMap();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paint$lambda$20() {
        Speech.INSTANCE.setEnabled(!Speech.INSTANCE.getEnabled());
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paint$lambda$21(QuickPanel this$0) {
        this$0.a.toggleScene();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paint$lambda$22(QuickPanel this$0) {
        this$0.keepDrive();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paint$lambda$23(QuickPanel this$0) {
        this$0.a.toggleSaver();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paint$lambda$24(QuickPanel this$0) {
        this$0.a.door("lock");
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paint$lambda$25(QuickPanel this$0) {
        this$0.a.door("unlock");
        return Unit.INSTANCE;
    }

    public final void keepDrive() {
        KeepDrive keepDrive = KeepDrive.INSTANCE;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        keepDrive.keepNewest(context);
    }

    private final void toast(String s) {
        Toast.makeText(getContext(), s, 1).show();
    }
}
