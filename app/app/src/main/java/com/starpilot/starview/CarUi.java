package com.starpilot.starview;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import com.starpilot.starview.SettingIcons;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import android.widget.LinearLayout.LayoutParams;

/* compiled from: CarUi.kt */
public final class CarUi {
    public static final float DESC = 15.0f;
    public static final float HEADER = 14.0f;
    public static final int ROW_MIN = 76;
    public static final float TITLE = 21.0f;
    private Function1<? super String, SettingIcons.Icon> autoIcon;
    private final int card;
    private final Context ctx;
    private final float d;
    private final boolean flat;
    private final int gap;
    public static final Companion_ Companion = new Companion_(null);
    private static final int BG = Color.rgb(11, 13, 18);
    private static final int CARD = Color.rgb(24, 27, 34);
    private static final int CARD_ON = Color.rgb(30, 38, 47);
    private static final int TEXT = -1;
    private static final int DIM = Color.rgb(150, 160, 180);
    private static final int ACCENT = Color.rgb(111, 227, 161);
    private static final int WARN = Color.rgb(255, 170, 60);
    private static final int FRAME = Color.rgb(17, 20, 25);
    private static final int STRIP = Color.rgb(5, 6, 8);
    private static final int SECTION = Color.rgb(30, 35, 44);
    private static final int PAGE = Color.rgb(34, 50, 80);
    private static final int PAGE_EDGE = Color.rgb(127, 180, 255);
    private static final int PAGE_DIV = Color.rgb(48, 65, 95);
    private static final int TAB_TEXT = Color.rgb(154, 163, 181);
    private static final int TAB_SEP = Color.rgb(38, 43, 53);

    public CarUi(Context ctx, boolean flat) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        this.ctx = ctx;
        this.flat = flat;
        this.card = this.flat ? PAGE : CARD;
        this.gap = this.flat ? 0 : 8;
        this.d = this.ctx.getResources().getDisplayMetrics().density;
    }

    public /* synthetic */ CarUi(Context context, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? false : z);
    }

    public final Context getCtx() {
        return this.ctx;
    }

    public final boolean getFlat() {
        return this.flat;
    }

    public final int getCard() {
        return this.card;
    }

    public final int getGap() {
        return this.gap;
    }

    public final float getD() {
        return this.d;
    }

    public final Function1<String, SettingIcons.Icon> getAutoIcon() {
        return this.autoIcon;
    }

    public final void setAutoIcon(Function1<? super String, SettingIcons.Icon> function1) {
        this.autoIcon = function1;
    }

    public final int dp(int v) {
        return (int) (v * this.d);
    }

    /* compiled from: CarUi.kt */
    public static final class Companion_ {
        public /* synthetic */ Companion_(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion_() {
        }

        public final int getBG() {
            return CarUi.BG;
        }

        public final int getCARD() {
            return CarUi.CARD;
        }

        public final int getCARD_ON() {
            return CarUi.CARD_ON;
        }

        public final int getTEXT() {
            return CarUi.TEXT;
        }

        public final int getDIM() {
            return CarUi.DIM;
        }

        public final int getACCENT() {
            return CarUi.ACCENT;
        }

        public final int getWARN() {
            return CarUi.WARN;
        }

        public final int getFRAME() {
            return CarUi.FRAME;
        }

        public final int getSTRIP() {
            return CarUi.STRIP;
        }

        public final int getSECTION() {
            return CarUi.SECTION;
        }

        public final int getPAGE() {
            return CarUi.PAGE;
        }

        public final int getPAGE_EDGE() {
            return CarUi.PAGE_EDGE;
        }

        public final int getPAGE_DIV() {
            return CarUi.PAGE_DIV;
        }

        public final int getTAB_TEXT() {
            return CarUi.TAB_TEXT;
        }

        public final int getTAB_SEP() {
            return CarUi.TAB_SEP;
        }
    }

    public final Drawable rowBg() {
        if (!this.flat) {
            return rounded$default(this, this.card, 0, null, 6, null);
        }
        GradientDrawable line = new GradientDrawable();
        line.setColor(PAGE_DIV);
        GradientDrawable fill = new GradientDrawable();
        fill.setColor(PAGE);
        LayerDrawable $this$rowBg_u24lambda_u242 = new LayerDrawable(new GradientDrawable[]{line, fill});
        $this$rowBg_u24lambda_u242.setLayerInset(1, 0, 0, 0, dp(1));
        return $this$rowBg_u24lambda_u242;
    }

    public final GradientDrawable corners(int color, int tl, int tr, int br, int bl) {
        GradientDrawable $this$corners_u24lambda_u243 = new GradientDrawable();
        $this$corners_u24lambda_u243.setColor(color);
        $this$corners_u24lambda_u243.setCornerRadii(new float[]{dp(tl), dp(tl), dp(tr), dp(tr), dp(br), dp(br), dp(bl), dp(bl)});
        return $this$corners_u24lambda_u243;
    }

    /* VF: from Vineflower (jadx: Type inference failed) */
    public final Pair<View, Function1<Integer, Unit>> browserTabs(List<String> var1, int var2, Function1<? super Integer, Unit> var3) {
        Intrinsics.checkNotNullParameter(var1, "labels");
        Intrinsics.checkNotNullParameter(var3, "onPick");
        boolean var10;
        if (var2 == 1) {
            var10 = true;
        } else {
            var10 = false;
        }

        ArrayList var12 = new ArrayList();
        ViewGroup var30;
        if (var10) {
            LinearLayout var11 = new LinearLayout(this.ctx);
            var11.setOrientation(0);
            var11.setPadding(this.dp(8), this.dp(8), this.dp(8), 0);
            var30 = (ViewGroup)var11;
        } else {
            FlowRow var31 = new FlowRow(this.ctx, this.dp(2), this.dp(4));
            var31.setPadding(this.dp(14), this.dp(12), this.dp(14), 0);
            var30 = var31;
        }

        Iterator var13 = var1.iterator();
        var2 = 0;

        while (var13.hasNext()) {
            int var6 = var2 + 1;
            String var16 = (String)var13.next();
            TextView var14 = new TextView(this.ctx);
            var14.setText(var16);
            float var4;
            if (var10) {
                var4 = 18.0F;
            } else {
                var4 = 15.0F;
            }

            var14.setTextSize(var4);
            var14.setGravity(17);
            var14.setTextColor(TAB_TEXT);
            var14.setSingleLine(true);
            byte var22;
            if (var10) {
                var22 = 20;
            } else {
                var22 = 16;
            }

            int var7 = this.dp(var22);
            if (var10) {
                var22 = 14;
            } else {
                var22 = 10;
            }

            int var8 = this.dp(var22);
            if (var10) {
                var22 = 20;
            } else {
                var22 = 16;
            }

            int var9 = this.dp(var22);
            if (var10) {
                var22 = 15;
            } else {
                var22 = 11;
            }

            var14.setPadding(var7, var8, var9, this.dp(var22));
            var14.setOnClickListener(new CarUi$$ExternalSyntheticLambda4(var3, var2));
            var12.add(var14);
            View var32 = (View)var14;
            android.view.ViewGroup.LayoutParams var18;
            if (var10) {
                android.widget.LinearLayout.LayoutParams var15 = new LayoutParams(-2, -2);
                var15.rightMargin = this.dp(2);
                Unit var17 = Unit.INSTANCE;
                var18 = (android.view.ViewGroup.LayoutParams)var15;
            } else {
                var18 = new android.view.ViewGroup.LayoutParams(-2, -2);
            }

            var30.addView(var32, var18);
            var2 = var6;
        }

        if (var10) {
            var2 = SECTION;
        } else {
            var2 = PAGE;
        }

        int var28;
        if (var10) {
            var28 = ACCENT;
        } else {
            var28 = PAGE_EDGE;
        }

        byte var29;
        if (var10) {
            var29 = 14;
        } else {
            var29 = 10;
        }

        CarUi$$ExternalSyntheticLambda5 var27 = new CarUi$$ExternalSyntheticLambda5(var12, this, var28, var29, var2, var10);
        View var20;
        if (var10) {
            HorizontalScrollView var19 = new HorizontalScrollView(this.ctx);
            var19.setHorizontalScrollBarEnabled(false);
            var19.setFillViewport(true);
            var19.setBackground((Drawable)this.corners(STRIP, 14, 14, 0, 0));
            var19.addView((View)var30);
            var20 = (View)var19;
        } else {
            var30.setBackgroundColor(SECTION);
            var20 = (View)var30;
        }

        return TuplesKt.to(var20, var27);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void browserTabs$lambda$7$lambda$6(Function1 $onPick, int $i, View it) {
        $onPick.invoke(Integer.valueOf($i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit browserTabs$lambda$12(ArrayList $tabs, CarUi this$0, int $edge, int $r, int $selBg, boolean $big, int sel) {
        LayerDrawable layerDrawable;
        Iterator it = $tabs.iterator();
        int i = 0;
        while (it.hasNext()) {
            int i2 = i;
            int i3 = i + 1;
            TextView t = (TextView) it.next();
            boolean on = i2 == sel;
            if (!on) {
                if (i2 >= $tabs.size() - 1 || i2 + 1 == sel) {
                    layerDrawable = null;
                } else {
                    GradientDrawable $this$browserTabs_u24lambda_u2412_u24lambda_u2410 = new GradientDrawable();
                    $this$browserTabs_u24lambda_u2412_u24lambda_u2410.setColor(TAB_SEP);
                    Unit unit = Unit.INSTANCE;
                    LayerDrawable $this$browserTabs_u24lambda_u2412_u24lambda_u2411 = new LayerDrawable(new GradientDrawable[]{$this$browserTabs_u24lambda_u2412_u24lambda_u2410});
                    $this$browserTabs_u24lambda_u2412_u24lambda_u2411.setLayerInset(0, 0, 0, 0, 0);
                    $this$browserTabs_u24lambda_u2412_u24lambda_u2411.setLayerGravity(0, 8388629);
                    $this$browserTabs_u24lambda_u2412_u24lambda_u2411.setLayerWidth(0, this$0.dp(1));
                    $this$browserTabs_u24lambda_u2412_u24lambda_u2411.setLayerHeight(0, this$0.dp($big ? 22 : 18));
                    layerDrawable = $this$browserTabs_u24lambda_u2412_u24lambda_u2411;
                }
            } else {
                LayerDrawable $this$browserTabs_u24lambda_u2412_u24lambda_u249 = new LayerDrawable(new GradientDrawable[]{this$0.corners($edge, $r, $r, 0, 0), this$0.corners($selBg, $r, $r, 0, 0)});
                $this$browserTabs_u24lambda_u2412_u24lambda_u249.setLayerInset(1, 0, this$0.dp(3), 0, 0);
                layerDrawable = $this$browserTabs_u24lambda_u2412_u24lambda_u249;
            }
            t.setBackground(layerDrawable);
            t.setTextColor(on ? $big ? ACCENT : TEXT : TAB_TEXT);
            t.setTypeface(on ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
            i = i3;
        }
        return Unit.INSTANCE;
    }

    public static /* synthetic */ GradientDrawable rounded$default(CarUi carUi, int i, int i2, Integer num, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i2 = 14;
        }
        if ((i3 & 4) != 0) {
            num = null;
        }
        return carUi.rounded(i, i2, num);
    }

    public final GradientDrawable rounded(int color, int radius, Integer stroke) {
        GradientDrawable $this$rounded_u24lambda_u2415 = new GradientDrawable();
        $this$rounded_u24lambda_u2415.setColor(color);
        $this$rounded_u24lambda_u2415.setCornerRadius(dp(radius));
        if (stroke != null) {
            $this$rounded_u24lambda_u2415.setStroke(dp(2), stroke.intValue());
        }
        return $this$rounded_u24lambda_u2415;
    }

    public static /* synthetic */ Pair row$default(CarUi carUi, String str, String str2, int i, View view, String str3, int i2, Object obj) {
        String str4 = (i2 & 2) != 0 ? null : str2;
        if ((i2 & 4) != 0) {
            i = 0;
        }
        return carUi.row(str, str4, i, (i2 & 8) != 0 ? null : view, (i2 & 16) != 0 ? null : str3);
    }

    public final Pair<LinearLayout, TextView> row(String title, String desc, int depth, View icon, String tag) {
        View view;
        int i;
        SettingIcons.Icon it;
        Intrinsics.checkNotNullParameter(title, "title");
        LinearLayout $this$row_u24lambda_u2416 = new LinearLayout(this.ctx);
        $this$row_u24lambda_u2416.setOrientation(0);
        $this$row_u24lambda_u2416.setGravity(16);
        $this$row_u24lambda_u2416.setMinimumHeight(dp(76));
        $this$row_u24lambda_u2416.setPadding(dp(20), dp(14), dp(18), dp(14));
        $this$row_u24lambda_u2416.setBackground(rowBg());
        if (icon == null) {
            Function1<? super String, SettingIcons.Icon> function1 = this.autoIcon;
            view = (function1 == null || (it = function1.invoke(title)) == null) ? null : SvIcon.chip$default(SvIcon.INSTANCE, this.ctx, it.getName(), it.getGroup(), 46, null, 16, null);
        } else {
            view = icon;
        }
        View icon2 = view;
        if (icon2 != null) {
            LinearLayout.LayoutParams $this$row_u24lambda_u2418 = new LinearLayout.LayoutParams(dp(46), dp(46));
            $this$row_u24lambda_u2418.rightMargin = dp(16);
            Unit unit = Unit.INSTANCE;
            $this$row_u24lambda_u2416.addView(icon2, $this$row_u24lambda_u2418);
        }
        LinearLayout $this$row_u24lambda_u2419 = new LinearLayout(this.ctx);
        $this$row_u24lambda_u2419.setOrientation(1);
        if (tag == null) {
            TextView $this$row_u24lambda_u2420 = new TextView(this.ctx);
            $this$row_u24lambda_u2420.setText(title);
            $this$row_u24lambda_u2420.setTextColor(TEXT);
            $this$row_u24lambda_u2420.setTextSize(21.0f);
            $this$row_u24lambda_u2419.addView($this$row_u24lambda_u2420);
        } else {
            LinearLayout $this$row_u24lambda_u2424 = new LinearLayout(this.ctx);
            $this$row_u24lambda_u2424.setOrientation(0);
            $this$row_u24lambda_u2424.setGravity(16);
            TextView $this$row_u24lambda_u2424_u24lambda_u2421 = new TextView(this.ctx);
            $this$row_u24lambda_u2424_u24lambda_u2421.setText(title);
            $this$row_u24lambda_u2424_u24lambda_u2421.setTextColor(TEXT);
            $this$row_u24lambda_u2424_u24lambda_u2421.setTextSize(21.0f);
            $this$row_u24lambda_u2424.addView($this$row_u24lambda_u2424_u24lambda_u2421);
            TextView $this$row_u24lambda_u2424_u24lambda_u2422 = new TextView(this.ctx);
            $this$row_u24lambda_u2424_u24lambda_u2422.setText(tag);
            $this$row_u24lambda_u2424_u24lambda_u2422.setTextColor(WARN);
            $this$row_u24lambda_u2424_u24lambda_u2422.setTextSize(13.0f);
            $this$row_u24lambda_u2424_u24lambda_u2422.setTypeface(Typeface.DEFAULT_BOLD);
            $this$row_u24lambda_u2424_u24lambda_u2422.setPadding(dp(8), dp(2), dp(8), dp(2));
            $this$row_u24lambda_u2424_u24lambda_u2422.setBackground(rounded$default(this, Color.rgb(61, 46, 23), 8, null, 4, null));
            LinearLayout.LayoutParams $this$row_u24lambda_u2424_u24lambda_u2423 = new LinearLayout.LayoutParams(-2, -2);
            $this$row_u24lambda_u2424_u24lambda_u2423.leftMargin = dp(10);
            Unit unit2 = Unit.INSTANCE;
            $this$row_u24lambda_u2424.addView($this$row_u24lambda_u2424_u24lambda_u2422, $this$row_u24lambda_u2424_u24lambda_u2423);
            $this$row_u24lambda_u2419.addView($this$row_u24lambda_u2424);
        }
        TextView descTv = null;
        String str = desc;
        if (str == null || StringsKt.isBlank(str)) {
            i = 0;
        } else {
            TextView $this$row_u24lambda_u2425 = new TextView(this.ctx);
            $this$row_u24lambda_u2425.setText(desc);
            $this$row_u24lambda_u2425.setTextColor(DIM);
            $this$row_u24lambda_u2425.setTextSize(15.0f);
            i = 0;
            $this$row_u24lambda_u2425.setPadding(0, dp(4), 0, 0);
            descTv = $this$row_u24lambda_u2425;
            $this$row_u24lambda_u2419.addView(descTv);
        }
        $this$row_u24lambda_u2416.addView($this$row_u24lambda_u2419, new LinearLayout.LayoutParams(i, -2, 1.0f));
        return TuplesKt.to($this$row_u24lambda_u2416, descTv);
    }

    public static /* synthetic */ LinearLayout.LayoutParams lp$default(CarUi carUi, int i, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = 0;
        }
        if ((i3 & 2) != 0) {
            i2 = carUi.gap;
        }
        return carUi.lp(i, i2);
    }

    public final LinearLayout.LayoutParams lp(int depth, int bottom) {
        LinearLayout.LayoutParams $this$lp_u24lambda_u2426 = new LinearLayout.LayoutParams(-1, -2);
        $this$lp_u24lambda_u2426.bottomMargin = dp(bottom);
        $this$lp_u24lambda_u2426.leftMargin = dp(depth * 26);
        return $this$lp_u24lambda_u2426;
    }

    public final TextView header(String text) {
        Intrinsics.checkNotNullParameter(text, "text");
        TextView $this$header_u24lambda_u2427 = new TextView(this.ctx);
        String upperCase = text.toUpperCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(upperCase, "toUpperCase(...)");
        $this$header_u24lambda_u2427.setText(upperCase);
        $this$header_u24lambda_u2427.setTextColor(DIM);
        $this$header_u24lambda_u2427.setTextSize(14.0f);
        $this$header_u24lambda_u2427.setLetterSpacing(0.1f);
        $this$header_u24lambda_u2427.setPadding(dp(6), dp(22), 0, dp(8));
        return $this$header_u24lambda_u2427;
    }

    public final TextView note(String text) {
        Intrinsics.checkNotNullParameter(text, "text");
        TextView $this$note_u24lambda_u2428 = new TextView(this.ctx);
        $this$note_u24lambda_u2428.setText(text);
        $this$note_u24lambda_u2428.setTextColor(DIM);
        $this$note_u24lambda_u2428.setTextSize(15.0f);
        $this$note_u24lambda_u2428.setPadding(dp(6), dp(4), dp(6), dp(10));
        return $this$note_u24lambda_u2428;
    }

    public final Switch bigSwitch() {
        Switch $this$bigSwitch_u24lambda_u2429 = new Switch(this.ctx);
        $this$bigSwitch_u24lambda_u2429.setScaleX(1.45f);
        $this$bigSwitch_u24lambda_u2429.setScaleY(1.45f);
        $this$bigSwitch_u24lambda_u2429.setPadding(dp(14), 0, dp(14), 0);
        return $this$bigSwitch_u24lambda_u2429;
    }

    public static /* synthetic */ TextView button$default(CarUi carUi, String str, boolean z, Function0 function0, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return carUi.button(str, z, function0);
    }

    public final TextView button(String label, boolean accent, final Function0<Unit> onClick) {
        Intrinsics.checkNotNullParameter(label, "label");
        Intrinsics.checkNotNullParameter(onClick, "onClick");
        TextView $this$button_u24lambda_u2431 = new TextView(this.ctx);
        $this$button_u24lambda_u2431.setText(label);
        $this$button_u24lambda_u2431.setTextSize(18.0f);
        $this$button_u24lambda_u2431.setTextColor(accent ? BG : TEXT);
        $this$button_u24lambda_u2431.setTypeface(Typeface.DEFAULT_BOLD);
        $this$button_u24lambda_u2431.setGravity(17);
        $this$button_u24lambda_u2431.setMinWidth(dp(120));
        $this$button_u24lambda_u2431.setMinHeight(dp(56));
        $this$button_u24lambda_u2431.setPadding(dp(22), dp(10), dp(22), dp(10));
        $this$button_u24lambda_u2431.setBackground(rounded(accent ? ACCENT : CARD_ON, 12, accent ? null : Integer.valueOf(Color.rgb(60, 66, 78))));
        $this$button_u24lambda_u2431.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.CarUi$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                onClick.invoke();
            }
        });
        return $this$button_u24lambda_u2431;
    }

    public final TextView stepper(String label, final Function0<Unit> onClick) {
        Intrinsics.checkNotNullParameter(label, "label");
        Intrinsics.checkNotNullParameter(onClick, "onClick");
        TextView $this$stepper_u24lambda_u2433 = new TextView(this.ctx);
        $this$stepper_u24lambda_u2433.setText(label);
        $this$stepper_u24lambda_u2433.setTextSize(26.0f);
        $this$stepper_u24lambda_u2433.setTextColor(TEXT);
        $this$stepper_u24lambda_u2433.setGravity(17);
        $this$stepper_u24lambda_u2433.setTypeface(Typeface.DEFAULT_BOLD);
        $this$stepper_u24lambda_u2433.setLayoutParams(new LinearLayout.LayoutParams(dp(62), dp(62)));
        $this$stepper_u24lambda_u2433.setBackground(rounded(CARD_ON, 31, Integer.valueOf(Color.rgb(60, 66, 78))));
        $this$stepper_u24lambda_u2433.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.CarUi$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                onClick.invoke();
            }
        });
        return $this$stepper_u24lambda_u2433;
    }

    public final Pair<LinearLayout, Function1<Integer, Unit>> chips(List<String> labels, final Function1<? super Integer, Unit> onPick) {
        Intrinsics.checkNotNullParameter(labels, "labels");
        Intrinsics.checkNotNullParameter(onPick, "onPick");
        LinearLayout bar = new LinearLayout(this.ctx);
        bar.setOrientation(0);
        final ArrayList views = new ArrayList();
        int i = 0;
        for (String l : labels) {
            final int i2 = i;
            i++;
            TextView $this$chips_u24lambda_u2436 = new TextView(this.ctx);
            $this$chips_u24lambda_u2436.setText(l);
            $this$chips_u24lambda_u2436.setTextSize(17.0f);
            $this$chips_u24lambda_u2436.setGravity(17);
            $this$chips_u24lambda_u2436.setMinHeight(dp(54));
            $this$chips_u24lambda_u2436.setPadding(dp(12), dp(8), dp(12), dp(8));
            $this$chips_u24lambda_u2436.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.CarUi$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    CarUi.chips$lambda$36$lambda$35(onPick, i2, view);
                }
            });
            views.add($this$chips_u24lambda_u2436);
            TextView textView = $this$chips_u24lambda_u2436;
            LinearLayout.LayoutParams $this$chips_u24lambda_u2437 = new LinearLayout.LayoutParams(0, -2, 1.0f);
            $this$chips_u24lambda_u2437.rightMargin = i2 < labels.size() + (-1) ? dp(8) : 0;
            Unit unit = Unit.INSTANCE;
            bar.addView(textView, $this$chips_u24lambda_u2437);
        }
        Function1 paint = new Function1() { // from class: com.starpilot.starview.CarUi$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit chips$lambda$38;
                chips$lambda$38 = CarUi.chips$lambda$38(views, this, ((Integer) obj).intValue());
                return chips$lambda$38;
            }
        };
        return TuplesKt.to(bar, paint);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void chips$lambda$36$lambda$35(Function1 $onPick, int $i, View it) {
        $onPick.invoke(Integer.valueOf($i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit chips$lambda$38(ArrayList $views, CarUi this$0, int sel) {
        Iterator it = $views.iterator();
        int i = 0;
        while (it.hasNext()) {
            int i2 = i;
            i++;
            TextView v = (TextView) it.next();
            boolean on = i2 == sel;
            v.setBackground(this$0.rounded(on ? CARD_ON : CARD, 12, Integer.valueOf(on ? ACCENT : Color.rgb(50, 55, 66))));
            v.setTextColor(on ? ACCENT : TEXT);
            v.setTypeface(on ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
        }
        return Unit.INSTANCE;
    }

    public final LinearLayout tile(String label, String sub, int icon, int size, final Function0<Unit> onClick) {
        Intrinsics.checkNotNullParameter(label, "label");
        Intrinsics.checkNotNullParameter(onClick, "onClick");
        LinearLayout $this$tile_u24lambda_u2443 = new LinearLayout(this.ctx);
        $this$tile_u24lambda_u2443.setOrientation(1);
        $this$tile_u24lambda_u2443.setGravity(17);
        $this$tile_u24lambda_u2443.setBackground(rounded$default(this, CARD, 18, null, 4, null));
        $this$tile_u24lambda_u2443.setPadding(dp(16), dp(18), dp(16), dp(18));
        ImageView $this$tile_u24lambda_u2443_u24lambda_u2439 = new ImageView(this.ctx);
        $this$tile_u24lambda_u2443_u24lambda_u2439.setImageResource(icon);
        $this$tile_u24lambda_u2443_u24lambda_u2439.setColorFilter(ACCENT);
        $this$tile_u24lambda_u2443.addView($this$tile_u24lambda_u2443_u24lambda_u2439, new LinearLayout.LayoutParams(dp(size), dp(size)));
        TextView $this$tile_u24lambda_u2443_u24lambda_u2440 = new TextView(this.ctx);
        $this$tile_u24lambda_u2443_u24lambda_u2440.setText(label);
        $this$tile_u24lambda_u2443_u24lambda_u2440.setTextColor(TEXT);
        $this$tile_u24lambda_u2443_u24lambda_u2440.setTextSize(22.0f);
        $this$tile_u24lambda_u2443_u24lambda_u2440.setTypeface(Typeface.DEFAULT_BOLD);
        $this$tile_u24lambda_u2443_u24lambda_u2440.setGravity(17);
        $this$tile_u24lambda_u2443_u24lambda_u2440.setPadding(0, dp(12), 0, 0);
        $this$tile_u24lambda_u2443.addView($this$tile_u24lambda_u2443_u24lambda_u2440);
        if (sub != null) {
            TextView $this$tile_u24lambda_u2443_u24lambda_u2441 = new TextView(this.ctx);
            $this$tile_u24lambda_u2443_u24lambda_u2441.setText(sub);
            $this$tile_u24lambda_u2443_u24lambda_u2441.setTextColor(DIM);
            $this$tile_u24lambda_u2443_u24lambda_u2441.setTextSize(15.0f);
            $this$tile_u24lambda_u2443_u24lambda_u2441.setGravity(17);
            $this$tile_u24lambda_u2443_u24lambda_u2441.setPadding(0, dp(4), 0, 0);
            $this$tile_u24lambda_u2443.addView($this$tile_u24lambda_u2443_u24lambda_u2441);
        }
        $this$tile_u24lambda_u2443.setClickable(true);
        $this$tile_u24lambda_u2443.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.CarUi$$ExternalSyntheticLambda6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                onClick.invoke();
            }
        });
        return $this$tile_u24lambda_u2443;
    }

    public final Pair<LinearLayout, TextView> iconTile(String label, String sub, String icon, String group, final Function0<Unit> onClick) {
        Intrinsics.checkNotNullParameter(label, "label");
        Intrinsics.checkNotNullParameter(icon, "icon");
        Intrinsics.checkNotNullParameter(group, "group");
        Intrinsics.checkNotNullParameter(onClick, "onClick");
        TextView $this$iconTile_u24lambda_u2444 = new TextView(this.ctx);
        $this$iconTile_u24lambda_u2444.setText(sub != null ? sub : "");
        $this$iconTile_u24lambda_u2444.setTextColor(DIM);
        $this$iconTile_u24lambda_u2444.setTextSize(15.0f);
        $this$iconTile_u24lambda_u2444.setMaxLines(2);
        $this$iconTile_u24lambda_u2444.setEllipsize(TextUtils.TruncateAt.END);
        LinearLayout $this$iconTile_u24lambda_u2447 = new LinearLayout(this.ctx);
        $this$iconTile_u24lambda_u2447.setOrientation(1);
        $this$iconTile_u24lambda_u2447.setBackground(rounded$default(this, CARD, 20, null, 4, null));
        $this$iconTile_u24lambda_u2447.setPadding(dp(20), dp(18), dp(18), dp(16));
        $this$iconTile_u24lambda_u2447.addView(SvIcon.chip$default(SvIcon.INSTANCE, this.ctx, icon, group, 58, null, 16, null), new LinearLayout.LayoutParams(dp(58), dp(58)));
        $this$iconTile_u24lambda_u2447.addView(new View(this.ctx), new LinearLayout.LayoutParams(1, 0, 1.0f));
        TextView $this$iconTile_u24lambda_u2447_u24lambda_u2445 = new TextView(this.ctx);
        $this$iconTile_u24lambda_u2447_u24lambda_u2445.setText(label);
        $this$iconTile_u24lambda_u2447_u24lambda_u2445.setTextColor(TEXT);
        $this$iconTile_u24lambda_u2447_u24lambda_u2445.setTextSize(21.0f);
        $this$iconTile_u24lambda_u2447_u24lambda_u2445.setTypeface(Typeface.DEFAULT_BOLD);
        $this$iconTile_u24lambda_u2447_u24lambda_u2445.setPadding(0, dp(10), 0, dp(2));
        $this$iconTile_u24lambda_u2447_u24lambda_u2445.setSingleLine(true);
        $this$iconTile_u24lambda_u2447_u24lambda_u2445.setEllipsize(TextUtils.TruncateAt.END);
        $this$iconTile_u24lambda_u2447.addView($this$iconTile_u24lambda_u2447_u24lambda_u2445);
        $this$iconTile_u24lambda_u2447.addView($this$iconTile_u24lambda_u2444);
        $this$iconTile_u24lambda_u2447.setClickable(true);
        $this$iconTile_u24lambda_u2447.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.CarUi$$ExternalSyntheticLambda7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                onClick.invoke();
            }
        });
        return TuplesKt.to($this$iconTile_u24lambda_u2447, $this$iconTile_u24lambda_u2444);
    }

    public final TextView railItem(String label, String icon, String group) {
        Intrinsics.checkNotNullParameter(label, "label");
        Intrinsics.checkNotNullParameter(icon, "icon");
        Intrinsics.checkNotNullParameter(group, "group");
        TextView $this$railItem_u24lambda_u2448 = railItem(label, null);
        $this$railItem_u24lambda_u2448.setCompoundDrawablesRelativeWithIntrinsicBounds(SvIcon.INSTANCE.drawable(icon, SvIcon.INSTANCE.fg(group), dp(26)), (Drawable) null, (Drawable) null, (Drawable) null);
        $this$railItem_u24lambda_u2448.setCompoundDrawablePadding(dp(14));
        return $this$railItem_u24lambda_u2448;
    }

    public final TextView railItem(String label, Integer icon) {
        Intrinsics.checkNotNullParameter(label, "label");
        TextView $this$railItem_u24lambda_u2449 = new TextView(this.ctx);
        $this$railItem_u24lambda_u2449.setText(label);
        $this$railItem_u24lambda_u2449.setTextSize(18.0f);
        $this$railItem_u24lambda_u2449.setTextColor(TEXT);
        $this$railItem_u24lambda_u2449.setGravity(16);
        $this$railItem_u24lambda_u2449.setMinHeight(dp(66));
        $this$railItem_u24lambda_u2449.setPadding(dp(16), dp(8), dp(12), dp(8));
        if (icon != null) {
            $this$railItem_u24lambda_u2449.setCompoundDrawablesRelativeWithIntrinsicBounds(icon.intValue(), 0, 0, 0);
            $this$railItem_u24lambda_u2449.setCompoundDrawablePadding(dp(14));
            $this$railItem_u24lambda_u2449.setCompoundDrawableTintList(ColorStateList.valueOf(DIM));
        }
        $this$railItem_u24lambda_u2449.setBackground(rounded$default(this, CARD, 0, null, 6, null));
        return $this$railItem_u24lambda_u2449;
    }

    public final void railSelect(TextView v, boolean on) {
        GradientDrawable rounded$default;
        Intrinsics.checkNotNullParameter(v, "v");
        if (!on) {
            rounded$default = rounded$default(this, CARD, 0, null, 6, null);
        } else {
            LayerDrawable $this$railSelect_u24lambda_u2450 = new LayerDrawable(new GradientDrawable[]{corners(ACCENT, 14, 0, 0, 14), corners(FRAME, 14, 0, 0, 14)});
            $this$railSelect_u24lambda_u2450.setLayerInset(1, dp(4), 0, 0, 0);
            rounded$default = $this$railSelect_u24lambda_u2450;
        }
        v.setBackground(rounded$default);
        ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
        LinearLayout.LayoutParams lp = layoutParams instanceof LinearLayout.LayoutParams ? (LinearLayout.LayoutParams) layoutParams : null;
        if (lp != null) {
            int m = on ? 0 : dp(18);
            if (lp.rightMargin != m) {
                lp.rightMargin = m;
                v.setLayoutParams(lp);
            }
        }
        v.setTextColor(on ? ACCENT : TEXT);
        v.setTypeface(on ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
        v.setCompoundDrawableTintList(ColorStateList.valueOf(on ? ACCENT : DIM));
    }

    public final View spacer(int h) {
        View $this$spacer_u24lambda_u2452 = new View(this.ctx);
        $this$spacer_u24lambda_u2452.setLayoutParams(new LinearLayout.LayoutParams(1, dp(h)));
        return $this$spacer_u24lambda_u2452;
    }
}
