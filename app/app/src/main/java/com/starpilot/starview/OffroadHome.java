package com.starpilot.starview;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.starpilot.starview.OffroadHome;
import com.starpilot.starview.StorageInfo;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.math.MathKt;
import kotlin.text.StringsKt;
import org.json.JSONObject;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.text.TextUtils.TruncateAt;
import android.view.ViewGroup.LayoutParams;

/* compiled from: OffroadHome.kt */
public final class OffroadHome extends FrameLayout {
    public static final long AMBIENT_MS = 180000;
    public static final Companion Companion = new Companion(null);
    private final Actions actions;
    private final TextView alertsBtn;
    private final StatBlock allTime;
    private final TextView ambClock;
    private final TextView ambDate;
    private final TextView ambLine;
    private final LinearLayout ambient;
    private final float d;
    private final TextView detailAction;
    private final LinearLayout detailBody;
    private final LinearLayout detailContent;
    private final TextView detailTitle;
    private final TextView devLines;
    private final TextView devName;
    private final TextView expBtn;
    private final LinearLayout homeContent;
    private final TextView lastDrive;
    private long lastDriveAt;
    private String lastDriveLine;
    private int lastGen;
    private Telemetry lastT;
    private long lastTouch;
    private final StatBlock pastWeek;
    private final List<TextView> persBtns;
    private boolean prevAlerts;
    private boolean prevUpdate;
    private State state;
    private final TextView statsNote;
    private JSONObject storage;
    private final TextView updateBtn;
    private final TextView version;

    /* compiled from: OffroadHome.kt */
    public interface Actions {
        void control(JSONObject req);

        void openFiles();

        void openSettings(String tab);

        void openTerminal();
    }

    /* compiled from: OffroadHome.kt */
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[State.values().length];
            try {
                iArr[State.HOME.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[State.UPDATE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[State.ALERTS.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* VF: from Vineflower (jadx: ) */
    public OffroadHome(Context var1, OffroadHome.Actions var2) {
        super(var1);
        Intrinsics.checkNotNullParameter(var1, "ctx");
        Intrinsics.checkNotNullParameter(var2, "actions");
        this.actions = var2;
        this.d = this.getResources().getDisplayMetrics().density;
        this.state = OffroadHome.State.HOME;
        TextView var5 = tv$default(this, 22.0F, 0, true, 2, null);
        var5.setText("UPDATE");
        var5.setGravity(17);
        var5.setPadding(this.dp(26), this.dp(12), this.dp(26), this.dp(12));
        var5.setBackground((Drawable)this.rounded(Color.rgb(54, 77, 239), 14));
        Unit var18 = Unit.INSTANCE;
        this.updateBtn = var5;
        var5 = tv$default(this, 22.0F, 0, true, 2, null);
        var5.setGravity(17);
        var5.setPadding(this.dp(26), this.dp(12), this.dp(26), this.dp(12));
        var5.setBackground((Drawable)this.rounded(Color.rgb(226, 44, 44), 14));
        Unit var19 = Unit.INSTANCE;
        this.alertsBtn = var5;
        var5 = tv$default(this, 26.0F, 0, false, 6, null);
        var5.setGravity(8388629);
        var5.setMaxLines(1);
        var5.setEllipsize(TruncateAt.END);
        Unit var20 = Unit.INSTANCE;
        this.version = var5;
        this.allTime = new OffroadHome.StatBlock(this, (int)"ALL TIME", Color.rgb(145, 110, 255));
        this.pastWeek = new OffroadHome.StatBlock(this, (int)"PAST WEEK", Color.rgb(40, 200, 190));
        this.statsNote = tv$default(this, 15.0F, Color.rgb(150, 160, 180), false, 4, null);
        var5 = tv$default(this, 26.0F, 0, true, 2, null);
        var5.setGravity(16);
        var5.setPadding(this.dp(30), 0, this.dp(30), 0);
        Unit var21 = Unit.INSTANCE;
        this.expBtn = var5;
        Iterable var22 = CollectionsKt.listOf(new Pair[]{TuplesKt.to("Aggressive", 0), TuplesKt.to("Standard", 1), TuplesKt.to("Relaxed", 2)});
        var var48 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var22, 10));

        for (Pair var8 : (Iterable<Pair>) var22) {
            String var7 = (String)var8.component1();
            int var3 = ((Number)var8.component2()).intValue();
            TextView var109 = tv$default(this, 19.0F, 0, true, 2, null);
            var109.setText(var7);
            var109.setGravity(17);
            var109.setPadding(0, this.dp(16), 0, this.dp(16));
            var109.setTag(var3);
            var48.add(var109);
        }

        List var23 = var48;
        this.persBtns = var23;
        this.devName = tv$default(this, 24.0F, 0, true, 2, null);
        var5 = tv$default(this, 17.0F, Color.rgb(190, 198, 214), false, 4, null);
        var5.setLineSpacing(this.dp(6), 1.0F);
        Unit var24 = Unit.INSTANCE;
        this.devLines = var5;
        this.detailTitle = tv$default(this, 34.0F, 0, true, 2, null);
        LinearLayout var25 = new LinearLayout(var1);
        var25.setOrientation(1);
        Unit var50 = Unit.INSTANCE;
        this.detailBody = var25;
        var5 = tv$default(this, 20.0F, 0, true, 2, null);
        var5.setGravity(17);
        var5.setPadding(this.dp(28), this.dp(16), this.dp(28), this.dp(16));
        var5.setBackground((Drawable)this.rounded(Color.rgb(54, 77, 239), 14));
        Unit var26 = Unit.INSTANCE;
        this.detailAction = var5;
        TextView var27 = tv$default(this, 17.0F, Color.rgb(190, 198, 214), false, 4, null);
        var27.setPadding(this.dp(18), this.dp(14), this.dp(18), this.dp(14));
        var27.setBackground((Drawable)this.rounded(Color.rgb(34, 36, 44), 14));
        var27.setLineSpacing(this.dp(4), 1.0F);
        var27.setVisibility(8);
        Unit var52 = Unit.INSTANCE;
        this.lastDrive = var27;
        LinearLayout var53 = new LinearLayout(var1);
        var53.setOrientation(1);
        var53.setGravity(17);
        var53.setBackgroundColor(-16777216);
        var53.setVisibility(8);
        var53.setClickable(true);
        Unit var28 = Unit.INSTANCE;
        this.ambient = var53;
        TextView var29 = tv$default(this, 150.0F, Color.rgb(205, 210, 220), false, 4, null);
        var29.setGravity(17);
        var29.setTypeface(Typeface.create("sans-serif-thin", 0));
        Unit var54 = Unit.INSTANCE;
        this.ambClock = var29;
        var5 = tv$default(this, 28.0F, Color.rgb(130, 138, 155), false, 4, null);
        var5.setGravity(17);
        Unit var30 = Unit.INSTANCE;
        this.ambDate = var5;
        TextView var31 = tv$default(this, 20.0F, Color.rgb(100, 108, 124), false, 4, null);
        var31.setGravity(17);
        Unit var56 = Unit.INSTANCE;
        this.ambLine = var31;
        this.lastTouch = System.currentTimeMillis();
        this.setBackgroundColor(-16777216);
        LinearLayout var57 = new LinearLayout(var1);
        var57.setOrientation(1);
        var57.setPadding(this.dp(36), this.dp(28), this.dp(36), this.dp(28));
        this.addView((View)var57, (android.view.ViewGroup.LayoutParams)(new android.widget.FrameLayout.LayoutParams(-1, -1)));
        LinearLayout var32 = new LinearLayout(var1);
        var32.setOrientation(0);
        var32.setGravity(16);
        View var71 = (View)this.updateBtn;
        android.widget.LinearLayout.LayoutParams var110 = new android.widget.LinearLayout.LayoutParams(-2, -2);
        var110.setMarginEnd(this.dp(16));
        Unit var94 = Unit.INSTANCE;
        var32.addView(var71, (android.view.ViewGroup.LayoutParams)var110);
        var71 = (View)this.alertsBtn;
        android.widget.LinearLayout.LayoutParams var95 = new android.widget.LinearLayout.LayoutParams(-2, -2);
        var95.setMarginEnd(this.dp(16));
        Unit var111 = Unit.INSTANCE;
        var32.addView(var71, (android.view.ViewGroup.LayoutParams)var95);
        var32.addView((View)this.version, (android.view.ViewGroup.LayoutParams)(new android.widget.LinearLayout.LayoutParams(0, -2, 1.0F)));
        var57.addView((View)var32, (android.view.ViewGroup.LayoutParams)(new android.widget.LinearLayout.LayoutParams(-1, this.dp(72))));
        this.updateBtn.setOnClickListener(new OffroadHome$$ExternalSyntheticLambda0(this));
        this.alertsBtn.setOnClickListener(new OffroadHome$$ExternalSyntheticLambda3(this));
        FrameLayout var112 = new FrameLayout(var1);
        View var9 = (View)var112;
        android.widget.LinearLayout.LayoutParams var73 = new android.widget.LinearLayout.LayoutParams(-1, 0, 1.0F);
        var73.topMargin = this.dp(20);
        var94 = Unit.INSTANCE;
        var57.addView(var9, (android.view.ViewGroup.LayoutParams)var73);
        LinearLayout var58 = new LinearLayout(var1);
        var58.setOrientation(0);
        Unit var74 = Unit.INSTANCE;
        this.homeContent = var58;
        LinearLayout var59 = new LinearLayout(var1);
        var59.setOrientation(1);
        int var42 = Color.rgb(24, 24, 28);
        var59.setBackground((Drawable)rounded$default(this, var42, 0, 2, null));
        var59.setPadding(this.dp(32), this.dp(28), this.dp(32), this.dp(28));
        TextView var97 = this.tv(16.0F, Color.rgb(150, 160, 180), true);
        var97.setText("DRIVE STATS");
        var97.setLetterSpacing(0.12F);
        Unit var75 = Unit.INSTANCE;
        var59.addView((View)var97);
        var71 = (View)this.allTime.getView();
        android.widget.LinearLayout.LayoutParams var113 = new android.widget.LinearLayout.LayoutParams(-1, 0, 1.0F);
        var113.topMargin = this.dp(18);
        var94 = Unit.INSTANCE;
        var59.addView(var71, (android.view.ViewGroup.LayoutParams)var113);
        var9 = (View)this.pastWeek.getView();
        android.widget.LinearLayout.LayoutParams var99 = new android.widget.LinearLayout.LayoutParams(-1, 0, 1.0F);
        var99.topMargin = this.dp(18);
        Unit var77 = Unit.INSTANCE;
        var59.addView(var9, (android.view.ViewGroup.LayoutParams)var99);
        var71 = (View)this.statsNote;
        android.widget.LinearLayout.LayoutParams var100 = new android.widget.LinearLayout.LayoutParams(-2, -2);
        var100.topMargin = this.dp(12);
        Unit var115 = Unit.INSTANCE;
        var59.addView(var71, (android.view.ViewGroup.LayoutParams)var100);
        var9 = (View)this.lastDrive;
        android.widget.LinearLayout.LayoutParams var101 = new android.widget.LinearLayout.LayoutParams(-1, -2);
        var101.topMargin = this.dp(14);
        Unit var79 = Unit.INSTANCE;
        var59.addView(var9, (android.view.ViewGroup.LayoutParams)var101);
        this.lastDrive.setOnClickListener(new OffroadHome$$ExternalSyntheticLambda4(this));
        this.homeContent.addView((View)var59, (android.view.ViewGroup.LayoutParams)(new android.widget.LinearLayout.LayoutParams(0, -1, 1.25F)));
        LinearLayout var117 = new LinearLayout(var1);
        var117.setOrientation(1);
        var117.addView((View)this.expBtn, (android.view.ViewGroup.LayoutParams)(new android.widget.LinearLayout.LayoutParams(-1, this.dp(110))));
        this.expBtn.setOnClickListener(new OffroadHome$$ExternalSyntheticLambda5(this));
        LinearLayout var60 = new LinearLayout(var1);
        var60.setOrientation(1);
        var60.setBackground((Drawable)rounded$default(this, Color.rgb(24, 24, 28), 0, 2, null));
        var60.setPadding(this.dp(22), this.dp(20), this.dp(22), this.dp(22));
        TextView var102 = this.tv(16.0F, Color.rgb(150, 160, 180), true);
        var102.setText("DRIVING PERSONALITY");
        var102.setLetterSpacing(0.12F);
        Unit var80 = Unit.INSTANCE;
        var60.addView((View)var102);
        LinearLayout var10 = new LinearLayout(var1);
        var10.setOrientation(0);
        Iterable var103 = this.persBtns;
        boolean var4 = false;
        var42 = 0;

        for (Object var11 : var103) {
            if (var42 < 0) {
                CollectionsKt.throwIndexOverflow();
            }

            TextView var14 = (TextView)var11;
            View var13 = (View)var14;
            android.widget.LinearLayout.LayoutParams var12 = new android.widget.LinearLayout.LayoutParams(0, -2, 1.0F);
            if (var42 > 0) {
                var12.setMarginStart(this.dp(10));
            }

            var11 = Unit.INSTANCE;
            var10.addView(var13, (android.view.ViewGroup.LayoutParams)var12);
            var14.setOnClickListener(new OffroadHome$$ExternalSyntheticLambda6(this, var14));
            var42++;
        }

        View var118 = (View)var10;
        android.widget.LinearLayout.LayoutParams var104 = new android.widget.LinearLayout.LayoutParams(-1, -2);
        var104.topMargin = this.dp(14);
        Unit var82 = Unit.INSTANCE;
        var60.addView(var118, (android.view.ViewGroup.LayoutParams)var104);
        View var119 = (View)var60;
        android.widget.LinearLayout.LayoutParams var83 = new android.widget.LinearLayout.LayoutParams(-1, -2);
        var83.topMargin = this.dp(20);
        var94 = Unit.INSTANCE;
        var117.addView(var119, (android.view.ViewGroup.LayoutParams)var83);
        LinearLayout var106 = new LinearLayout(var1);
        var106.setOrientation(1);
        var106.setBackground((Drawable)rounded$default(this, Color.rgb(24, 24, 28), 0, 2, null));
        var106.setPadding(this.dp(26), this.dp(22), this.dp(26), this.dp(22));
        var106.addView((View)this.devName);
        View var120 = (View)this.devLines;
        android.widget.LinearLayout.LayoutParams var124 = new android.widget.LinearLayout.LayoutParams(-1, 0, 1.0F);
        var124.topMargin = this.dp(12);
        Unit var84 = Unit.INSTANCE;
        var106.addView(var120, (android.view.ViewGroup.LayoutParams)var124);
        LinearLayout var85 = new LinearLayout(var1);
        var85.setOrientation(0);
        Iterable var121 = CollectionsKt.listOf(
            new Pair[]{
                TuplesKt.to("Settings", new OffroadHome$$ExternalSyntheticLambda7(this)),
                TuplesKt.to("Files", new OffroadHome$$ExternalSyntheticLambda8(this)),
                TuplesKt.to("Terminal", new OffroadHome$$ExternalSyntheticLambda9(this))
            }
        );
        var42 = 0;

        for (Object var125 : var121) {
            if (var42 < 0) {
                CollectionsKt.throwIndexOverflow();
            }

            Pair var128 = (Pair)var125;
            String var126 = (String)var128.component1();
            Function0 var131 = (Function0)var128.component2();
            TextView var129 = tv$default(this, 18.0F, 0, true, 2, null);
            var129.setText(var126);
            var129.setGravity(17);
            var129.setPadding(0, this.dp(16), 0, this.dp(16));
            var129.setBackground((Drawable)this.rounded(Color.rgb(44, 48, 60), 14));
            var129.setOnClickListener(new OffroadHome$$ExternalSyntheticLambda10(var131));
            View var127 = (View)var129;
            android.widget.LinearLayout.LayoutParams var132 = new android.widget.LinearLayout.LayoutParams(0, -2, 1.0F);
            if (var42 > 0) {
                var132.setMarginStart(this.dp(10));
            }

            Unit var130 = Unit.INSTANCE;
            var85.addView(var127, (android.view.ViewGroup.LayoutParams)var132);
            var42++;
        }

        var71 = (View)var85;
        android.widget.LinearLayout.LayoutParams var33 = new android.widget.LinearLayout.LayoutParams(-1, -2);
        var33.topMargin = this.dp(12);
        Unit var61 = Unit.INSTANCE;
        var106.addView(var71, (android.view.ViewGroup.LayoutParams)var33);
        View var62 = (View)var106;
        android.widget.LinearLayout.LayoutParams var87 = new android.widget.LinearLayout.LayoutParams(-1, 0, 1.0F);
        var87.topMargin = this.dp(20);
        Unit var34 = Unit.INSTANCE;
        var117.addView(var62, (android.view.ViewGroup.LayoutParams)var87);
        LinearLayout var35 = this.homeContent;
        var71 = (View)var117;
        android.widget.LinearLayout.LayoutParams var107 = new android.widget.LinearLayout.LayoutParams(0, -1, 1.0F);
        var107.setMarginStart(this.dp(24));
        Unit var63 = Unit.INSTANCE;
        var35.addView(var71, (android.view.ViewGroup.LayoutParams)var107);
        var112.addView((View)this.homeContent, (android.view.ViewGroup.LayoutParams)(new android.widget.FrameLayout.LayoutParams(-1, -1)));
        LinearLayout var36 = new LinearLayout(var1);
        var36.setOrientation(1);
        var36.setBackground((Drawable)rounded$default(this, Color.rgb(24, 24, 28), 0, 2, null));
        var36.setPadding(this.dp(36), this.dp(30), this.dp(36), this.dp(30));
        var36.setVisibility(8);
        Unit var64 = Unit.INSTANCE;
        this.detailContent = var36;
        this.detailContent.addView((View)this.detailTitle);
        LinearLayout var37 = this.detailContent;
        ScrollView var89 = new ScrollView(var1);
        var89.addView((View)this.detailBody);
        Unit var65 = Unit.INSTANCE;
        View var108 = (View)var89;
        android.widget.LinearLayout.LayoutParams var66 = new android.widget.LinearLayout.LayoutParams(-1, 0, 1.0F);
        var66.topMargin = this.dp(20);
        var66.bottomMargin = this.dp(20);
        Unit var90 = Unit.INSTANCE;
        var37.addView(var108, (android.view.ViewGroup.LayoutParams)var66);
        LinearLayout var15 = new LinearLayout(var1);
        var15.setOrientation(0);
        var15.setGravity(8388613);
        var5 = tv$default(this, 20.0F, 0, true, 2, null);
        var5.setText("Close");
        var5.setGravity(17);
        var5.setPadding(this.dp(28), this.dp(16), this.dp(28), this.dp(16));
        var5.setBackground((Drawable)this.rounded(Color.rgb(44, 48, 60), 14));
        var5.setOnClickListener(new OffroadHome$$ExternalSyntheticLambda11(this));
        Unit var38 = Unit.INSTANCE;
        var15.addView((View)var5);
        View var68 = (View)this.detailAction;
        android.widget.LinearLayout.LayoutParams var91 = new android.widget.LinearLayout.LayoutParams(-2, -2);
        var91.setMarginStart(this.dp(14));
        Unit var39 = Unit.INSTANCE;
        var15.addView(var68, (android.view.ViewGroup.LayoutParams)var91);
        this.detailContent.addView((View)var15, (android.view.ViewGroup.LayoutParams)(new android.widget.LinearLayout.LayoutParams(-1, -2)));
        var112.addView((View)this.detailContent, (android.view.ViewGroup.LayoutParams)(new android.widget.FrameLayout.LayoutParams(-1, -1)));
        this.ambient.addView((View)this.ambClock);
        LinearLayout var40 = this.ambient;
        View var69 = (View)this.ambDate;
        android.widget.LinearLayout.LayoutParams var92 = new android.widget.LinearLayout.LayoutParams(-2, -2);
        var92.topMargin = this.dp(8);
        Unit var16 = Unit.INSTANCE;
        var40.addView(var69, (android.view.ViewGroup.LayoutParams)var92);
        LinearLayout var93 = this.ambient;
        View var41 = (View)this.ambLine;
        android.widget.LinearLayout.LayoutParams var17 = new android.widget.LinearLayout.LayoutParams(-2, -2);
        var17.topMargin = this.dp(30);
        Unit var70 = Unit.INSTANCE;
        var93.addView(var41, (android.view.ViewGroup.LayoutParams)var17);
        this.addView((View)this.ambient, (android.view.ViewGroup.LayoutParams)(new android.widget.FrameLayout.LayoutParams(-1, -1)));
        this.lastGen = -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final int dp(int v) {
        return (int) (v * this.d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final GradientDrawable rounded(int color, int r) {
        GradientDrawable $this$rounded_u24lambda_u240 = new GradientDrawable();
        $this$rounded_u24lambda_u240.setColor(color);
        $this$rounded_u24lambda_u240.setCornerRadius(dp(r));
        return $this$rounded_u24lambda_u240;
    }

    static /* synthetic */ GradientDrawable rounded$default(OffroadHome offroadHome, int i, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i2 = 20;
        }
        return offroadHome.rounded(i, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final TextView tv(float size, int color, boolean bold) {
        TextView $this$tv_u24lambda_u241 = new TextView(getContext());
        $this$tv_u24lambda_u241.setTextSize(size);
        $this$tv_u24lambda_u241.setTextColor(color);
        if (bold) {
            $this$tv_u24lambda_u241.setTypeface(Typeface.DEFAULT_BOLD);
        }
        $this$tv_u24lambda_u241.setIncludeFontPadding(false);
        return $this$tv_u24lambda_u241;
    }

    static /* synthetic */ TextView tv$default(OffroadHome offroadHome, float f, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = -1;
        }
        if ((i2 & 4) != 0) {
            z = false;
        }
        return offroadHome.tv(f, i, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: OffroadHome.kt */
    enum State {
        HOME,
        UPDATE,
        ALERTS;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<State> getEntries() {
            return $ENTRIES;
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Intrinsics.checkNotNullParameter(ev, "ev");
        this.lastTouch = System.currentTimeMillis();
        if (this.ambient.getVisibility() != 0) {
            return super.dispatchTouchEvent(ev);
        }
        if (ev.getActionMasked() == 1) {
            this.ambient.setVisibility(8);
        }
        return true;
    }

    private final void ambientTick(Telemetry t) {
        if (!isShown()) {
            this.lastTouch = System.currentTimeMillis();
            return;
        }
        boolean idle = System.currentTimeMillis() - this.lastTouch > 180000;
        if (idle && this.ambient.getVisibility() != 0) {
            this.ambient.setVisibility(0);
        }
        if (this.ambient.getVisibility() == 0) {
            Date now = new Date();
            this.ambClock.setText(new SimpleDateFormat("h:mm", Locale.US).format(now));
            this.ambDate.setText(new SimpleDateFormat("EEEE, MMMM d", Locale.US).format(now));
            TextView textView = this.ambLine;
            String[] strArr = new String[2];
            strArr[0] = this.lastDriveLine;
            Float valueOf = Float.valueOf(t.getCpuTempC());
            float it = valueOf.floatValue();
            boolean z = it > 0.0f;
            String str = null;
            if (!z) {
                valueOf = null;
            }
            if (valueOf != null) {
                float it2 = valueOf.floatValue();
                str = "comma " + Math.round(((9 * it2) / 5) + 32) + "°F";
            }
            strArr[1] = str;
            textView.setText(CollectionsKt.joinToString(CollectionsKt.listOfNotNull((Object[]) strArr), "   ·   ", "", "", -1, "...", null));
        }
    }

    /* compiled from: OffroadHome.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void _init_$lambda$22(OffroadHome this$0, View it) {
        this$0.setState(this$0.state == State.UPDATE ? State.HOME : State.UPDATE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void _init_$lambda$23(OffroadHome this$0, View it) {
        this$0.setState(this$0.state == State.ALERTS ? State.HOME : State.ALERTS);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void _init_$lambda$32(OffroadHome this$0, View it) {
        this$0.actions.openSettings("journal:last");
    }

    static final void lambda$41$lambda$40(OffroadHome this$0, TextView $b, View it) {
        Actions actions = this$0.actions;
        Object tag = $b.getTag();
        Intrinsics.checkNotNull(tag, "null cannot be cast to non-null type kotlin.Int");
        actions.control(new JSONObject(MapsKt.mapOf(TuplesKt.to("action", "param"), TuplesKt.to("key", "LongitudinalPersonality"), TuplesKt.to("value", (Integer) tag))));
        Telemetry t = this$0.lastT;
        if (t != null) {
            this$0.refresh(t, true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit _init_$lambda$47(OffroadHome this$0) {
        this$0.actions.openSettings(null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit _init_$lambda$48(OffroadHome this$0) {
        this$0.actions.openFiles();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit _init_$lambda$49(OffroadHome this$0) {
        this$0.actions.openTerminal();
        return Unit.INSTANCE;
    }

    private final void paintLastDrive() {
        long now = System.currentTimeMillis();
        if (now - this.lastDriveAt < 30000) {
            return;
        }
        this.lastDriveAt = now;
        JSONObject d = (JSONObject) CollectionsKt.lastOrNull((List) DriveJournal.INSTANCE.read("drive"));
        if (d == null) {
            return;
        }
        double mi = d.optDouble("meters") / 1609.344d;
        long min = (d.optLong("end") - d.optLong("t")) / 60000;
        String whenTxt = new SimpleDateFormat("EEE h:mm a", Locale.US).format(new Date(d.optLong("t")));
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        String format = String.format(Locale.US, "last drive %.1f mi", Arrays.copyOf(new Object[]{Double.valueOf(mi)}, 1));
        Intrinsics.checkNotNullExpressionValue(format, "format(...)");
        this.lastDriveLine = format;
        TextView textView = this.lastDrive;
        StringCompanionObject stringCompanionObject2 = StringCompanionObject.INSTANCE;
        String format2 = String.format(Locale.US, "LAST DRIVE  ·  %s\n%.1f mi  ·  %d min  ·  %d%% openpilot  ·  %d takeovers  ·  %d alerts   ›", Arrays.copyOf(new Object[]{whenTxt, Double.valueOf(mi), Long.valueOf(min), Integer.valueOf(d.optInt("engagedPct")), Integer.valueOf(d.optInt("takeovers")), Integer.valueOf(d.optInt("alerts"))}, 6));
        Intrinsics.checkNotNullExpressionValue(format2, "format(...)");
        textView.setText(format2);
        this.lastDrive.setVisibility(0);
    }

    /* compiled from: OffroadHome.kt */
    private final class StatBlock {
        private final TextView dist;
        private final TextView distUnit;
        private final TextView drives;
        private final TextView hours;
        final /* synthetic */ OffroadHome this$0;
        private final LinearLayout view;

        public StatBlock(OffroadHome this$0, String title, int accent) {
            Intrinsics.checkNotNullParameter(title, "title");
            this.this$0 = this$0;
            this.drives = OffroadHome.tv$default(this.this$0, 44.0f, 0, true, 2, null);
            this.dist = OffroadHome.tv$default(this.this$0, 44.0f, 0, true, 2, null);
            this.hours = OffroadHome.tv$default(this.this$0, 44.0f, 0, true, 2, null);
            this.distUnit = OffroadHome.tv$default(this.this$0, 16.0f, Color.rgb(150, 160, 180), false, 4, null);
            LinearLayout $this$view_u24lambda_u247 = new LinearLayout(this.this$0.getContext());
            OffroadHome offroadHome = this.this$0;
            $this$view_u24lambda_u247.setOrientation(1);
            $this$view_u24lambda_u247.setBackground(offroadHome.rounded(Color.rgb(34, 34, 40), 16));
            $this$view_u24lambda_u247.setPadding(offroadHome.dp(26), offroadHome.dp(18), offroadHome.dp(26), offroadHome.dp(18));
            TextView $this$view_u24lambda_u247_u24lambda_u240 = offroadHome.tv(15.0f, accent, true);
            $this$view_u24lambda_u247_u24lambda_u240.setText(title);
            $this$view_u24lambda_u247_u24lambda_u240.setLetterSpacing(0.12f);
            $this$view_u24lambda_u247.addView($this$view_u24lambda_u247_u24lambda_u240);
            LinearLayout row = new LinearLayout($this$view_u24lambda_u247.getContext());
            row.setOrientation(0);
            row.setGravity(16);
            TextView textView = this.drives;
            int i = 0;
            TextView $this$view_u24lambda_u247_u24lambda_u242 = OffroadHome.tv$default(offroadHome, 16.0f, Color.rgb(150, 160, 180), false, 4, null);
            $this$view_u24lambda_u247_u24lambda_u242.setText("drives");
            Unit unit = Unit.INSTANCE;
            TextView textView2 = this.hours;
            TextView $this$view_u24lambda_u247_u24lambda_u243 = OffroadHome.tv$default(offroadHome, 16.0f, Color.rgb(150, 160, 180), false, 4, null);
            $this$view_u24lambda_u247_u24lambda_u243.setText("hours");
            Unit unit2 = Unit.INSTANCE;
            for (Pair pair : (Iterable<Pair>) (Iterable) CollectionsKt.listOf((Object[]) new Pair[]{TuplesKt.to(textView, $this$view_u24lambda_u247_u24lambda_u242), TuplesKt.to(this.dist, this.distUnit), TuplesKt.to(textView2, $this$view_u24lambda_u247_u24lambda_u243)})) {
                TextView v = (TextView) pair.component1();
                TextView unit3 = (TextView) pair.component2();
                LinearLayout $this$view_u24lambda_u247_u24lambda_u245 = new LinearLayout($this$view_u24lambda_u247.getContext());
                $this$view_u24lambda_u247_u24lambda_u245.setOrientation(1);
                $this$view_u24lambda_u247_u24lambda_u245.addView(v);
                LinearLayout.LayoutParams $this$view_u24lambda_u247_u24lambda_u245_u24lambda_u244 = new LinearLayout.LayoutParams(-2, -2);
                $this$view_u24lambda_u247_u24lambda_u245_u24lambda_u244.topMargin = offroadHome.dp(6);
                Unit unit4 = Unit.INSTANCE;
                $this$view_u24lambda_u247_u24lambda_u245.addView(unit3, $this$view_u24lambda_u247_u24lambda_u245_u24lambda_u244);
                row.addView($this$view_u24lambda_u247_u24lambda_u245, new LinearLayout.LayoutParams(0, -2, 1.0f));
                i = 0;
            }
            LinearLayout.LayoutParams $this$view_u24lambda_u247_u24lambda_u246 = new LinearLayout.LayoutParams(-1, i, 1.0f);
            $this$view_u24lambda_u247_u24lambda_u246.topMargin = offroadHome.dp(10);
            Unit unit5 = Unit.INSTANCE;
            $this$view_u24lambda_u247.addView(row, $this$view_u24lambda_u247_u24lambda_u246);
            this.view = $this$view_u24lambda_u247;
        }

        public final TextView getDist() {
            return this.dist;
        }

        public final TextView getDrives() {
            return this.drives;
        }

        public final TextView getHours() {
            return this.hours;
        }

        public final TextView getDistUnit() {
            return this.distUnit;
        }

        public final LinearLayout getView() {
            return this.view;
        }

        public final void set(int n, double distance, double h, boolean metric) {
            TextView textView = this.drives;
            String format = String.format("%,d", Arrays.copyOf(new Object[]{Integer.valueOf(n)}, 1));
            Intrinsics.checkNotNullExpressionValue(format, "format(...)");
            textView.setText(format);
            this.dist.setText(this.this$0.fmt(distance));
            this.hours.setText(this.this$0.fmt(h));
            this.distUnit.setText(metric ? "km" : "miles");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String fmt(double v) {
        String format = v >= 100.0d ? String.format("%,d", Arrays.copyOf(new Object[]{Integer.valueOf(MathKt.roundToInt(v))}, 1)) : String.format("%.1f", Arrays.copyOf(new Object[]{Double.valueOf(v)}, 1));
        Intrinsics.checkNotNullExpressionValue(format, "format(...)");
        return format;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setState(State s) {
        this.state = s;
        this.homeContent.setVisibility(s == State.HOME ? 0 : 8);
        this.detailContent.setVisibility(s == State.HOME ? 8 : 0);
        Telemetry it = this.lastT;
        if (it != null) {
            refresh(it, true);
        }
    }

    private final JSONObject jobj(Object v) {
        if (v instanceof JSONObject) {
            return (JSONObject) v;
        }
        if (v instanceof Map) {
            return new JSONObject((Map) v);
        }
        if (!(v instanceof String)) {
            return null;
        }
        try {
            return new JSONObject((String) v);
        } catch (Exception e) {
            return null;
        }
    }

    private final double num(JSONObject o, String k) {
        if (o != null) {
            Double valueOf = Double.valueOf(o.optDouble(k, 0.0d));
            double it = valueOf.doubleValue();
            if (Double.isNaN(it)) {
                valueOf = null;
            }
            if (valueOf != null) {
                return valueOf.doubleValue();
            }
            return 0.0d;
        }
        return 0.0d;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: OffroadHome.kt */
    static final /* data */ class Alert {
        private final int severity;
        private final String text;

        public static /* synthetic */ Alert copy$default(Alert alert, String str, int i, int i2, Object obj) {
            if ((i2 & 1) != 0) {
                str = alert.text;
            }
            if ((i2 & 2) != 0) {
                i = alert.severity;
            }
            return alert.copy(str, i);
        }


        public final Alert copy(String text, int severity) {
            Intrinsics.checkNotNullParameter(text, "text");
            return new Alert(text, severity);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Alert)) {
                return false;
            }
            Alert alert = (Alert) other;
            return Intrinsics.areEqual(this.text, alert.text) && this.severity == alert.severity;
        }

        public int hashCode() {
            return (this.text.hashCode() * 31) + Integer.hashCode(this.severity);
        }

        public String toString() {
            return "Alert(text=" + this.text + ", severity=" + this.severity + ")";
        }

        public Alert(String text, int severity) {
            Intrinsics.checkNotNullParameter(text, "text");
            this.text = text;
            this.severity = severity;
        }

        public final int getSeverity() {
            return this.severity;
        }

        public final String getText() {
            return this.text;
        }
    }

    private final List<Alert> alerts(Telemetry t) {
        Map map;
        Alert alert;
        Map $this$mapNotNull$iv;
        int $i$f$mapNotNull;
        Alert alert2;
        Map $this$mapNotNullTo$iv$iv;
        Map $this$mapNotNullTo$iv$iv2;
        Map $this$filterKeys$iv = t.getParams();
        LinkedHashMap result$iv = new LinkedHashMap();
        Iterator<Map.Entry<String, Object>> it = $this$filterKeys$iv.entrySet().iterator();
        while (true) {
            map = null;
            alert = null;
            if (!it.hasNext()) {
                break;
            }
            Map.Entry entry$iv = it.next();
            String it2 = ((String) entry$iv.getKey());
            if (StringsKt.startsWith(it2, "Offroad_", false)) {
                result$iv.put(entry$iv.getKey(), entry$iv.getValue());
            }
        }
        LinkedHashMap $this$mapNotNull$iv2 = result$iv;
        int $i$f$mapNotNull2 = 0;
        Collection destination$iv$iv = new ArrayList();
        Map $this$mapNotNullTo$iv$iv3 = $this$mapNotNull$iv2;
        for (Map.Entry element$iv$iv$iv : (Iterable<Map.Entry>) $this$mapNotNullTo$iv$iv3.entrySet()) {
            Object v = element$iv$iv$iv.getValue();
            JSONObject o = jobj(v);
            if (o == null) {
                $this$mapNotNull$iv = $this$mapNotNull$iv2;
                $i$f$mapNotNull = $i$f$mapNotNull2;
                $this$mapNotNullTo$iv$iv = $this$mapNotNullTo$iv$iv3;
                $this$mapNotNullTo$iv$iv2 = map;
                alert2 = alert;
            } else {
                String optString = o.optString("text");
                $this$mapNotNull$iv = $this$mapNotNull$iv2;
                Intrinsics.checkNotNullExpressionValue(optString, "optString(...)");
                $i$f$mapNotNull = $i$f$mapNotNull2;
                String optString2 = o.optString("extra");
                Intrinsics.checkNotNullExpressionValue(optString2, "optString(...)");
                String text = StringsKt.replace(optString, "%1", optString2, false);
                if (StringsKt.isBlank(text)) {
                    $this$mapNotNullTo$iv$iv = $this$mapNotNullTo$iv$iv3;
                    alert2 = null;
                    $this$mapNotNullTo$iv$iv2 = null;
                } else {
                    $this$mapNotNullTo$iv$iv = $this$mapNotNullTo$iv$iv3;
                    $this$mapNotNullTo$iv$iv2 = null;
                    alert2 = new Alert(text, o.optInt("severity", 0));
                }
            }
            if (alert2 != null) {
                destination$iv$iv.add(alert2);
            }
            map = $this$mapNotNullTo$iv$iv2;
            $this$mapNotNull$iv2 = $this$mapNotNull$iv;
            $this$mapNotNullTo$iv$iv3 = $this$mapNotNullTo$iv$iv;
            $i$f$mapNotNull2 = $i$f$mapNotNull;
            alert = null;
        }
        Iterable $this$sortedByDescending$iv = (List) destination$iv$iv;
        return CollectionsKt.sortedWith($this$sortedByDescending$iv, new Comparator() { // from class: com.starpilot.starview.OffroadHome$alerts$$inlined$sortedByDescending$1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.Comparator
            public final int compare(Object t2, Object t3) {
                OffroadHome.Alert it3 = (OffroadHome.Alert) t3;
                OffroadHome.Alert it4 = (OffroadHome.Alert) t2;
                return ComparisonsKt.compareValues(Integer.valueOf(it3.getSeverity()), Integer.valueOf(it4.getSeverity()));
            }
        });
    }

    public final JSONObject getStorage() {
        return this.storage;
    }

    public final void setStorage(JSONObject v) {
        this.storage = v;
        Telemetry it = this.lastT;
        if (it != null) {
            refresh(it, true);
        }
    }

    private final String gb(JSONObject o, String k) {
        double v = o.optDouble(k, Double.NaN);
        if (Double.isNaN(v)) {
            return "?";
        }
        if (v >= 100.0d) {
            return String.valueOf(MathKt.roundToInt(v));
        }
        String format = String.format("%.1f", Arrays.copyOf(new Object[]{Double.valueOf(v)}, 1));
        Intrinsics.checkNotNullExpressionValue(format, "format(...)");
        return format;
    }

    private final String whenStr(double epoch) {
        StringBuilder sb;
        String str;
        if (Double.isNaN(epoch) || epoch <= 0.0d) {
            return "–";
        }
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis((long) (1000 * epoch));
        Calendar now = Calendar.getInstance();
        String hm = String.format("%02d:%02d", Arrays.copyOf(new Object[]{Integer.valueOf(c.get(11)), Integer.valueOf(c.get(12))}, 2));
        Intrinsics.checkNotNullExpressionValue(hm, "format(...)");
        int dayDiff = (((now.get(1) - c.get(1)) * 366) + now.get(6)) - c.get(6);
        switch (dayDiff) {
            case 0:
                sb = new StringBuilder();
                str = "today ";
                break;
            case 1:
                sb = new StringBuilder();
                str = "yesterday ";
                break;
            default:
                String format = new SimpleDateFormat("MMM d HH:mm", Locale.US).format(c.getTime());
                Intrinsics.checkNotNullExpressionValue(format, "format(...)");
                return format;
        }
        return sb.append(str).append(hm).toString();
    }

    public static /* synthetic */ void refresh$default(OffroadHome offroadHome, Telemetry telemetry, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        offroadHome.refresh(telemetry, z);
    }

    /* VF: from Vineflower (jadx: Code restructure failed;Removed duplicated region;incorrect) */
    public final void refresh(Telemetry var1, boolean var2) {
        String var84 = null; // declarations Vineflower left out
        Intrinsics.checkNotNullParameter(var1, "t");
        this.lastT = var1;
        this.ambientTick(var1);
        this.paintLastDrive();
        if (var2 || var1.getParamsGeneration() != this.lastGen) {
            this.lastGen = var1.getParamsGeneration();
            var2 = var1.pBool("IsMetric", false);
            List var14 = this.alerts(var1);
            boolean var9 = var1.pBool("UpdateAvailable", false);
            TextView var12 = this.updateBtn;
            int var7;
            if (var9) {
                var7 = 0;
            } else {
                var7 = 8;
            }

            var12.setVisibility(var7);
            var12 = this.alertsBtn;
            if (!var14.isEmpty()) {
                var7 = (byte)0;
            } else {
                var7 = (byte)8;
            }

            var12.setVisibility(var7);
            TextView var13 = this.alertsBtn;
            String var64;
            if (var14.size() == 1) {
                var64 = "1 ALERT";
            } else {
                var7 = var14.size();
                var64 = var7 + " ALERTS";
            }

            var13.setText(var64);
            String var15 = StringsKt.trim(Telemetry.pStr$default(var1, "UpdaterCurrentDescription", null, 2, null)).toString();
            CharSequence var87 = Telemetry.pStr$default(var1, "DrivingModelName", null, 2, null);
            Object var65 = var87;
            if (StringsKt.isBlank(var87)) {
                var65 = Telemetry.pStr$default(var1, "Model", null, 2, null);
            }

            var65 = StringsKt.trim(
                    StringsKt.replace(StringsKt.replace((String)var65, "_default", "", false), "(Default)", "", false)
                )
                .toString();
            SpannableStringBuilder var88 = new SpannableStringBuilder();
            var88.append("StarPilot");
            var88.setSpan(new StyleSpan(1), 0, var88.length(), 0);
            int var43;
            if (var15.length() > 0) {
                var43 = 1;
            } else {
                var43 = 0;
            }

            if ((var43 != 0)) {
                var88.append("  ").append(var15);
            }

            if (((CharSequence)var65).length() > 0) {
                var43 = (((boolean)true) ? 1 : 0);
            } else {
                var43 = (((boolean)false) ? 1 : 0);
            }

            if ((var43 != 0)) {
                var88.append("  -  ").append((CharSequence)var65);
            }

            this.version.setText((CharSequence)var88);
            if (var9 && !this.prevUpdate) {
                this.state = OffroadHome.State.UPDATE;
            } else if (!var14.isEmpty() && !this.prevAlerts) {
                this.state = OffroadHome.State.ALERTS;
            }

            if (!var9 && this.state == OffroadHome.State.UPDATE) {
                if (!var14.isEmpty()) {
                    var65 = OffroadHome.State.ALERTS;
                } else {
                    var65 = OffroadHome.State.HOME;
                }

                this.state = (OffroadHome.State)var65;
            }

            if (var14.isEmpty() && this.state == OffroadHome.State.ALERTS) {
                if (var9) {
                    var65 = OffroadHome.State.UPDATE;
                } else {
                    var65 = OffroadHome.State.HOME;
                }

                this.state = (OffroadHome.State)var65;
            }

            this.prevUpdate = var9;
            this.prevAlerts = var14.isEmpty() ^ true;
            var65 = this.homeContent;
            if (this.state == OffroadHome.State.HOME) {
                var43 = (byte)0;
            } else {
                var43 = (byte)8;
            }

            var65.setVisibility(var43);
            var65 = this.detailContent;
            if (this.state == OffroadHome.State.HOME) {
                var43 = (byte)8;
            } else {
                var43 = (byte)0;
            }

            var65.setVisibility(var43);
            OffroadHome.State var71 = this.state;
            switch (OffroadHome.WhenMappings.$EnumSwitchMapping$0[var71.ordinal()]) {
                case 1:
                    JSONObject var111 = this.jobj(var1.getParams().get("ApiCache_DriveStats"));
                    JSONObject var108 = this.jobj(var1.getParams().get("StarPilotStats"));
                    JSONObject var92;
                    if (var111 != null) {
                        var92 = var111.optJSONObject("all");
                    } else {
                        var92 = null;
                    }

                    JSONObject var76;
                    if (var111 != null) {
                        var76 = var111.optJSONObject("week");
                    } else {
                        var76 = null;
                    }

                    long var10 = 4609926660211357856L;
                    if (var92 != null && this.num(var92, "routes") > 0.0) {
                        this.allTime.set((int)this.num(var92, "routes"), refresh$cloudDist(this, var2, 1.609344, var92), this.num(var92, "minutes") / 60.0, var2);
                    } else if (var108 != null) {
                        OffroadHome.StatBlock var93 = this.allTime;
                        var43 = (int)this.num(var108, "StarPilotDrives");
                        double var5 = this.num(var108, "StarPilotMeters");
                        double var3;
                        if (var2) {
                            var3 = 1000.0;
                        } else {
                            var3 = 1609.344;
                        }

                        var93.set(var43, var5 / var3, this.num(var108, "StarPilotSeconds") / 3600.0, var2);
                    } else {
                        this.allTime.set(0, 0.0, 0.0, var2);
                    }

                    OffroadHome.StatBlock var94 = this.pastWeek;
                    var43 = (int)this.num(var76, "routes");
                    var94.set(var43, refresh$cloudDist(this, var2, 1.609344, var76), this.num(var76, "minutes") / 60.0, var2);
                    var13 = this.statsNote;
                    String var109 = "";
                    CharSequence var77;
                    if (var111 != null) {
                        var77 = "";
                    } else if (var1.getSp7()) {
                        var77 = "StarPilot 7 doesn't keep drive stats yet.";
                    } else {
                        var77 = "Past week needs comma connect drive stats (the comma caches them when online).";
                    }

                    var13.setText(var77);
                    var9 = var1.pBool("ExperimentalMode", false);
                    var13 = this.expBtn;
                    String var78;
                    if (var9) {
                        var78 = "EXPERIMENTAL MODE ON";
                    } else {
                        var78 = "CHILL MODE ON";
                    }

                    var13.setText(var78);
                    TextView var79 = this.expBtn;
                    if (var9) {
                        var43 = -1;
                    } else {
                        var43 = Color.rgb(20, 30, 28);
                    }

                    var79.setTextColor(var43);
                    var13 = this.expBtn;
                    Orientation var112 = Orientation.LEFT_RIGHT;
                    int[] var80;
                    if (var9) {
                        var80 = new int[]{Color.rgb(255, 155, 63), Color.rgb(219, 56, 34)};
                    } else {
                        var80 = new int[]{Color.rgb(20, 255, 171), Color.rgb(35, 149, 255)};
                    }

                    GradientDrawable var81 = new GradientDrawable(var112, var80);
                    var81.setCornerRadius(this.dp(20));
                    var13.setBackground((Drawable)var81);
                    var43 = var1.pInt("LongitudinalPersonality", 1);

                    for (TextView var98 : this.persBtns) {
                        var9 = Intrinsics.areEqual(var98.getTag(), var43);
                        int var8;
                        if (var9) {
                            var8 = Color.rgb(54, 77, 239);
                        } else {
                            var8 = Color.rgb(44, 48, 60);
                        }

                        var98.setBackground((Drawable)this.rounded(var8, 14));
                        if (var9) {
                            var8 = -1;
                        } else {
                            var8 = Color.rgb(190, 198, 214);
                        }

                        var98.setTextColor(var8);
                    }

                    var13 = this.devName;
                    var65 = Telemetry.pStr$default(var1, "GalaxyDeviceName", null, 2, null);
                    int var53;
                    if (((CharSequence) var65).length() == 0) {
                        var53 = 1;
                    } else {
                        var53 = 0;
                    }

                    if ((var53 != 0)) {
                        var65 = "comma";
                    }

                    label344: {
                        var13.setText((CharSequence)var65);
                        var15 = Telemetry.Companion.chestnutWhy(Telemetry.pStr$default(var1, "ChestnutWhy", null, 2, null));
                        String var100 = Telemetry.pStr$default(var1, "ChestnutState", null, 2, null);
                        var53 = var100.hashCode();
                        var84 = "not detected";
                        switch (var53) {
                            case -1422950650:
                                if (var100.equals("active")) {
                                    var84 = "active";
                                    break label344;
                                }
                                break;
                            case -1381388741:
                                if (var100.equals("disconnected")) {
                                    break label344;
                                }
                                break;
                            case -1281977283:
                                if (var100.equals("failed")) {
                                    boolean var55;
                                    if (var15.length() > 0) {
                                        var55 = true;
                                    } else {
                                        var55 = false;
                                    }

                                    String var85;
                                    if (var55) {
                                        var85 = " - " + var15;
                                    } else {
                                        var85 = "";
                                    }

                                    var84 = "FAILED" + var85;
                                    break label344;
                                }
                                break;
                            case 108386723:
                                if (var100.equals("ready")) {
                                    var84 = "connected, model ready";
                                    break label344;
                                }
                                break;
                            case 336650556:
                                if (var100.equals("loading")) {
                                    var84 = "loading";
                                    break label344;
                                }
                        }

                        if (var1.pBool("UsbGpuActive", false)) {
                            var84 = "active";
                        } else if (var1.getChestnutPresent() && var1.pBool("UsbGpuCompiled", false)) {
                            var84 = "connected, model ready";
                        } else if (var1.getChestnutPresent()) {
                            var84 = "connected";
                        }
                    }

                    var2 = var1.pBool("SshEnabled", false);
                    String var17 = Telemetry.pStr$default(var1, "GitBranch", null, 2, null);
                    TextView var114 = this.devLines;
                    StringBuilder var16 = new StringBuilder();
                    Object var101 = Telemetry.pStr$default(var1, "DongleId", null, 2, null);
                    int var56;
                    if (var101.length() == 0) {
                        var56 = 1;
                    } else {
                        var56 = 0;
                    }

                    if ((var56 != 0)) {
                        var101 = "–";
                    }

                    var16.append("Dongle ID   " + var101 + "\n");
                    if (var17.length() > 0) {
                        var56 = (((boolean)true) ? 1 : 0);
                    } else {
                        var56 = (((boolean)false) ? 1 : 0);
                    }

                    if ((var56 != 0)) {
                        var16.append("Branch      " + var17 + "\n");
                    }

                    var16.append("eGPU        " + var84 + "\n");
                    JSONObject var115 = this.storage;
                    if (var115 != null) {
                        var101 = var115.optJSONObject("internal");
                    } else {
                        var101 = null;
                    }

                    JSONObject var86;
                    if (var115 != null) {
                        var86 = var115.optJSONObject("stick");
                    } else {
                        var86 = null;
                    }

                    if (var101 != null) {
                        String var25 = this.gb((JSONObject)var101, "free_GB");
                        String var18 = this.gb((JSONObject)var101, "total_GB");
                        var16.append("Internal    " + var25 + " free of " + var18 + " GB");
                        if (var101.has("newest_time")) {
                            String var26 = this.whenStr(var101.optDouble("newest_time"));
                            var16.append("  ·  newest drive " + var26);
                        }

                        var16.append("\n");
                    } else {
                        var56 = MathKt.roundToInt(var1.getFreeSpacePct());
                        var16.append("Storage     " + var56 + "% free\n");
                    }

                    if (var86 != null) {
                        var16.append("USB stick   ");
                        if (!var86.optBoolean("present")) {
                            var16.append("not connected");
                        } else if (var86.has("stick_free_GB")) {
                            String var27 = this.gb(var86, "stick_free_GB");
                            var101 = this.gb(var86, "stick_size_GB");
                            var16.append(var27 + " free of " + var101 + " GB");
                        } else {
                            var16.append("connected, no copy finished yet");
                        }

                        var16.append("\n");
                        StorageInfo.Copying var28 = StorageInfo.INSTANCE.copying(var115);
                        if (var28 != null) {
                            String var29 = var28.getLine();
                            var16.append("  copying now: " + var29 + "\n");
                        }

                        if (var86.optBoolean("present")) {
                            double var40 = var86.optDouble("last_copy_time", Double.NaN);
                            double var39 = var86.optDouble("run_time", Double.NaN);
                            String var30;
                            if (Double.isNaN(var40)) {
                                var30 = "–";
                            } else {
                                var101 = this.whenStr(var40);
                                String var31 = StorageInfo.INSTANCE.mb(var86.optInt("last_copy_MB"));
                                var30 = var101 + " (" + var31 + ")";
                            }

                            var16.append("  last copy " + var30);
                            if (var86.has("stick_newest_time")) {
                                String var32 = this.whenStr(var86.optDouble("stick_newest_time"));
                                var16.append("  ·  newest on stick " + var32);
                            }

                            var16.append("\n");
                            var17 = var86.optString("result");
                            String var33;
                            if (Double.isNaN(var39)) {
                                var33 = "never";
                            } else {
                                var33 = this.whenStr(var39);
                            }

                            Intrinsics.checkNotNull(var17);
                            boolean var59;
                            if (var17.length() > 0) {
                                var59 = true;
                            } else {
                                var59 = false;
                            }

                            var101 = var109;
                            if (var59) {
                                var101 = var109;
                                if (!Intrinsics.areEqual(var17, "ok")) {
                                    var101 = "  ·  " + var17;
                                }
                            }

                            var16.append("  checked " + var33 + var101);
                            if (!var86.optBoolean("auto", true)) {
                                var16.append("  ·  auto copy OFF");
                            }

                            var16.append("\n");
                        }
                    }

                    String var34;
                    if (var2) {
                        var34 = "on";
                    } else {
                        var34 = "off";
                    }

                    var16.append("SSH         " + var34);
                    String var35 = var16.toString();
                    Intrinsics.checkNotNullExpressionValue(var35, "toString(...)");
                    var114.setText(var35);
                    Unit var36 = Unit.INSTANCE;
                    break;
                case 2:
                    this.detailTitle.setText("Update available");
                    this.detailBody.removeAllViews();
                    CharSequence var74 = Telemetry.pStr$default(var1, "UpdaterNewDescription", null, 2, null);
                    Object var22 = var74;
                    if (StringsKt.isBlank(var74)) {
                        var22 = "A new version is ready to install.";
                    }

                    Object var75 = (String)var22;
                    var22 = this.detailBody;
                    var13 = tv$default(this, 22.0F, Color.rgb(210, 216, 228), false, 4, null);
                    var13.setText(var75);
                    var13.setLineSpacing(this.dp(6), 1.0F);
                    var22.addView((View)var13);
                    this.detailAction.setText("Open Software settings");
                    this.detailAction.setVisibility(0);
                    this.detailAction.setOnClickListener(new OffroadHome$$ExternalSyntheticLambda1(this));
                    Unit var24 = Unit.INSTANCE;
                    break;
                case 3:
                    TextView var72 = this.detailTitle;
                    String var19;
                    if (var14.size() == 1) {
                        var19 = "1 alert";
                    } else {
                        var43 = var14.size();
                        var19 = var43 + " alerts";
                    }

                    var72.setText(var19);
                    this.detailBody.removeAllViews();

                    for (OffroadHome.Alert var89 : (Iterable<OffroadHome.Alert>) var14) {
                        LinearLayout var20 = this.detailBody;
                        TextView var106 = tv$default(this, 21.0F, -1, false, 4, null);
                        var106.setText(var89.getText());
                        var106.setLineSpacing(this.dp(5), 1.0F);
                        var106.setPadding(this.dp(24), this.dp(20), this.dp(24), this.dp(20));
                        if (var89.getSeverity() > 0) {
                            var43 = Color.rgb(201, 34, 49);
                        } else {
                            var43 = Color.rgb(44, 48, 60);
                        }

                        var106.setBackground((Drawable)this.rounded(var43, 16));
                        View var110 = (View)var106;
                        android.widget.LinearLayout.LayoutParams var90 = new android.widget.LinearLayout.LayoutParams(-1, -2);
                        var90.bottomMargin = this.dp(12);
                        Unit var107 = Unit.INSTANCE;
                        var20.addView(var110, (android.view.ViewGroup.LayoutParams)var90);
                    }

                    this.detailAction.setVisibility(8);
                    Unit var21 = Unit.INSTANCE;
                    break;
                default:
                    throw new NoWhenBranchMatchedException();
            }
        }
    }

    private static final double refresh$cloudDist(OffroadHome this$0, boolean metric, double kmPerMile, JSONObject o) {
        double it = this$0.num(o, "distance");
        return metric ? it * kmPerMile : it;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void refresh$lambda$83(OffroadHome this$0, View it) {
        this$0.actions.openSettings("software");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void toggleExperimental() {
        Telemetry t = this.lastT;
        if (t == null) {
            return;
        }
        boolean exp = t.pBool("ExperimentalMode", false);
        if (!exp && !t.pBool("ExperimentalModeConfirmed", false)) {
            new AlertDialog.Builder(getContext()).setTitle("Experimental mode").setMessage("openpilot defaults to driving in chill mode. Experimental mode enables alpha-level features that aren't ready for chill mode: end-to-end longitudinal control (openpilot drives the speed, including stopping for red lights and stop signs) and more. Expect mistakes and keep your attention on the road.").setPositiveButton("Enable", new DialogInterface.OnClickListener() { // from class: com.starpilot.starview.OffroadHome$$ExternalSyntheticLambda2
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    OffroadHome.toggleExperimental$lambda$86(OffroadHome.this, dialogInterface, i);
                }
            }).setNegativeButton("Cancel", (DialogInterface.OnClickListener) null).show();
        } else {
            this.actions.control(new JSONObject(MapsKt.mapOf(TuplesKt.to("action", "exp_toggle"))));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void toggleExperimental$lambda$86(OffroadHome this$0, DialogInterface dialogInterface, int i) {
        this$0.actions.control(new JSONObject(MapsKt.mapOf(TuplesKt.to("action", "param"), TuplesKt.to("key", "ExperimentalMode"), TuplesKt.to("value", true))));
    }
}
