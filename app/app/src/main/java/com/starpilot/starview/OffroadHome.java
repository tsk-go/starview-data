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

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public OffroadHome(Context ctx, Actions actions) {
        super(ctx);
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(actions, "actions");
        this.actions = actions;
        this.d = getResources().getDisplayMetrics().density;
        this.state = State.HOME;
        TextView $this$updateBtn_u24lambda_u242 = tv$default(this, 22.0f, 0, true, 2, null);
        $this$updateBtn_u24lambda_u242.setText("UPDATE");
        $this$updateBtn_u24lambda_u242.setGravity(17);
        $this$updateBtn_u24lambda_u242.setPadding(dp(26), dp(12), dp(26), dp(12));
        $this$updateBtn_u24lambda_u242.setBackground(rounded(Color.rgb(54, 77, 239), 14));
        Unit unit = Unit.INSTANCE;
        this.updateBtn = $this$updateBtn_u24lambda_u242;
        TextView $this$alertsBtn_u24lambda_u243 = tv$default(this, 22.0f, 0, true, 2, null);
        $this$alertsBtn_u24lambda_u243.setGravity(17);
        $this$alertsBtn_u24lambda_u243.setPadding(dp(26), dp(12), dp(26), dp(12));
        $this$alertsBtn_u24lambda_u243.setBackground(rounded(Color.rgb(226, 44, 44), 14));
        Unit unit2 = Unit.INSTANCE;
        this.alertsBtn = $this$alertsBtn_u24lambda_u243;
        TextView $this$version_u24lambda_u244 = tv$default(this, 26.0f, 0, false, 6, null);
        $this$version_u24lambda_u244.setGravity(8388629);
        $this$version_u24lambda_u244.setMaxLines(1);
        $this$version_u24lambda_u244.setEllipsize(TextUtils.TruncateAt.END);
        Unit unit3 = Unit.INSTANCE;
        this.version = $this$version_u24lambda_u244;
        this.allTime = new StatBlock(this, "ALL TIME", Color.rgb(145, 110, 255));
        this.pastWeek = new StatBlock(this, "PAST WEEK", Color.rgb(40, 200, 190));
        this.statsNote = tv$default(this, 15.0f, Color.rgb(150, 160, 180), false, 4, null);
        TextView $this$expBtn_u24lambda_u245 = tv$default(this, 26.0f, 0, true, 2, null);
        $this$expBtn_u24lambda_u245.setGravity(16);
        $this$expBtn_u24lambda_u245.setPadding(dp(30), 0, dp(30), 0);
        Unit unit4 = Unit.INSTANCE;
        this.expBtn = $this$expBtn_u24lambda_u245;
        Iterable $this$map$iv = CollectionsKt.listOf((Object[]) new Pair[]{TuplesKt.to("Aggressive", 0), TuplesKt.to("Standard", 1), TuplesKt.to("Relaxed", 2)});
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        for (Object item$iv$iv : $this$map$iv) {
            Pair pair = (Pair) item$iv$iv;
            String label = (String) pair.component1();
            int v = ((Number) pair.component2()).intValue();
            Collection destination$iv$iv2 = destination$iv$iv;
            TextView $this$persBtns_u24lambda_u247_u24lambda_u246 = tv$default(this, 19.0f, 0, true, 2, null);
            $this$persBtns_u24lambda_u247_u24lambda_u246.setText(label);
            $this$persBtns_u24lambda_u247_u24lambda_u246.setGravity(17);
            $this$persBtns_u24lambda_u247_u24lambda_u246.setPadding(0, dp(16), 0, dp(16));
            $this$persBtns_u24lambda_u247_u24lambda_u246.setTag(Integer.valueOf(v));
            destination$iv$iv2.add($this$persBtns_u24lambda_u247_u24lambda_u246);
            destination$iv$iv = destination$iv$iv2;
            $this$map$iv = $this$map$iv;
        }
        this.persBtns = (List) destination$iv$iv;
        this.devName = tv$default(this, 24.0f, 0, true, 2, null);
        TextView $this$devLines_u24lambda_u248 = tv$default(this, 17.0f, Color.rgb(190, 198, 214), false, 4, null);
        $this$devLines_u24lambda_u248.setLineSpacing(dp(6), 1.0f);
        Unit unit5 = Unit.INSTANCE;
        this.devLines = $this$devLines_u24lambda_u248;
        this.detailTitle = tv$default(this, 34.0f, 0, true, 2, null);
        LinearLayout $this$detailBody_u24lambda_u249 = new LinearLayout(ctx);
        $this$detailBody_u24lambda_u249.setOrientation(1);
        Unit unit6 = Unit.INSTANCE;
        this.detailBody = $this$detailBody_u24lambda_u249;
        TextView $this$detailAction_u24lambda_u2410 = tv$default(this, 20.0f, 0, true, 2, null);
        $this$detailAction_u24lambda_u2410.setGravity(17);
        $this$detailAction_u24lambda_u2410.setPadding(dp(28), dp(16), dp(28), dp(16));
        $this$detailAction_u24lambda_u2410.setBackground(rounded(Color.rgb(54, 77, 239), 14));
        Unit unit7 = Unit.INSTANCE;
        this.detailAction = $this$detailAction_u24lambda_u2410;
        TextView $this$lastDrive_u24lambda_u2411 = tv$default(this, 17.0f, Color.rgb(190, 198, 214), false, 4, null);
        $this$lastDrive_u24lambda_u2411.setPadding(dp(18), dp(14), dp(18), dp(14));
        $this$lastDrive_u24lambda_u2411.setBackground(rounded(Color.rgb(34, 36, 44), 14));
        $this$lastDrive_u24lambda_u2411.setLineSpacing(dp(4), 1.0f);
        $this$lastDrive_u24lambda_u2411.setVisibility(8);
        Unit unit8 = Unit.INSTANCE;
        this.lastDrive = $this$lastDrive_u24lambda_u2411;
        LinearLayout $this$ambient_u24lambda_u2412 = new LinearLayout(ctx);
        $this$ambient_u24lambda_u2412.setOrientation(1);
        $this$ambient_u24lambda_u2412.setGravity(17);
        $this$ambient_u24lambda_u2412.setBackgroundColor(-16777216);
        $this$ambient_u24lambda_u2412.setVisibility(8);
        $this$ambient_u24lambda_u2412.setClickable(true);
        Unit unit9 = Unit.INSTANCE;
        this.ambient = $this$ambient_u24lambda_u2412;
        TextView $this$ambClock_u24lambda_u2413 = tv$default(this, 150.0f, Color.rgb(205, 210, 220), false, 4, null);
        $this$ambClock_u24lambda_u2413.setGravity(17);
        $this$ambClock_u24lambda_u2413.setTypeface(Typeface.create("sans-serif-thin", 0));
        Unit unit10 = Unit.INSTANCE;
        this.ambClock = $this$ambClock_u24lambda_u2413;
        TextView $this$ambDate_u24lambda_u2414 = tv$default(this, 28.0f, Color.rgb(130, 138, 155), false, 4, null);
        $this$ambDate_u24lambda_u2414.setGravity(17);
        Unit unit11 = Unit.INSTANCE;
        this.ambDate = $this$ambDate_u24lambda_u2414;
        TextView $this$ambLine_u24lambda_u2415 = tv$default(this, 20.0f, Color.rgb(100, 108, 124), false, 4, null);
        $this$ambLine_u24lambda_u2415.setGravity(17);
        Unit unit12 = Unit.INSTANCE;
        this.ambLine = $this$ambLine_u24lambda_u2415;
        this.lastTouch = System.currentTimeMillis();
        setBackgroundColor(-16777216);
        LinearLayout $this$_init__u24lambda_u2418 = new LinearLayout(ctx);
        $this$_init__u24lambda_u2418.setOrientation(1);
        $this$_init__u24lambda_u2418.setPadding(dp(36), dp(28), dp(36), dp(28));
        addView($this$_init__u24lambda_u2418, new FrameLayout.LayoutParams(-1, -1));
        LinearLayout $this$_init__u24lambda_u2419 = new LinearLayout(ctx);
        $this$_init__u24lambda_u2419.setOrientation(0);
        $this$_init__u24lambda_u2419.setGravity(16);
        TextView textView = this.updateBtn;
        LinearLayout.LayoutParams $this$_init__u24lambda_u2420 = new LinearLayout.LayoutParams(-2, -2);
        $this$_init__u24lambda_u2420.setMarginEnd(dp(16));
        Unit unit13 = Unit.INSTANCE;
        $this$_init__u24lambda_u2419.addView(textView, $this$_init__u24lambda_u2420);
        TextView textView2 = this.alertsBtn;
        LinearLayout.LayoutParams $this$_init__u24lambda_u2421 = new LinearLayout.LayoutParams(-2, -2);
        $this$_init__u24lambda_u2421.setMarginEnd(dp(16));
        Unit unit14 = Unit.INSTANCE;
        $this$_init__u24lambda_u2419.addView(textView2, $this$_init__u24lambda_u2421);
        $this$_init__u24lambda_u2419.addView(this.version, new LinearLayout.LayoutParams(0, -2, 1.0f));
        $this$_init__u24lambda_u2418.addView($this$_init__u24lambda_u2419, new LinearLayout.LayoutParams(-1, dp(72)));
        this.updateBtn.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.OffroadHome$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                OffroadHome._init_$lambda$22(OffroadHome.this, view);
            }
        });
        this.alertsBtn.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.OffroadHome$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                OffroadHome._init_$lambda$23(OffroadHome.this, view);
            }
        });
        FrameLayout body = new FrameLayout(ctx);
        LinearLayout.LayoutParams $this$_init__u24lambda_u2424 = new LinearLayout.LayoutParams(-1, 0, 1.0f);
        $this$_init__u24lambda_u2424.topMargin = dp(20);
        Unit unit15 = Unit.INSTANCE;
        $this$_init__u24lambda_u2418.addView(body, $this$_init__u24lambda_u2424);
        LinearLayout $this$_init__u24lambda_u2425 = new LinearLayout(ctx);
        $this$_init__u24lambda_u2425.setOrientation(0);
        Unit unit16 = Unit.INSTANCE;
        this.homeContent = $this$_init__u24lambda_u2425;
        LinearLayout $this$_init__u24lambda_u2426 = new LinearLayout(ctx);
        $this$_init__u24lambda_u2426.setOrientation(1);
        LinearLayout pers = $this$_init__u24lambda_u2419;
        $this$_init__u24lambda_u2426.setBackground(rounded$default(this, Color.rgb(24, 24, 28), 0, 2, null));
        $this$_init__u24lambda_u2426.setPadding(dp(32), dp(28), dp(32), dp(28));
        TextView $this$_init__u24lambda_u2427 = tv(16.0f, Color.rgb(150, 160, 180), true);
        $this$_init__u24lambda_u2427.setText("DRIVE STATS");
        $this$_init__u24lambda_u2427.setLetterSpacing(0.12f);
        Unit unit17 = Unit.INSTANCE;
        $this$_init__u24lambda_u2426.addView($this$_init__u24lambda_u2427);
        LinearLayout view = this.allTime.getView();
        LinearLayout.LayoutParams $this$_init__u24lambda_u2428 = new LinearLayout.LayoutParams(-1, 0, 1.0f);
        $this$_init__u24lambda_u2428.topMargin = dp(18);
        Unit unit18 = Unit.INSTANCE;
        $this$_init__u24lambda_u2426.addView(view, $this$_init__u24lambda_u2428);
        LinearLayout view2 = this.pastWeek.getView();
        LinearLayout.LayoutParams $this$_init__u24lambda_u2429 = new LinearLayout.LayoutParams(-1, 0, 1.0f);
        $this$_init__u24lambda_u2429.topMargin = dp(18);
        Unit unit19 = Unit.INSTANCE;
        $this$_init__u24lambda_u2426.addView(view2, $this$_init__u24lambda_u2429);
        TextView textView3 = this.statsNote;
        LinearLayout.LayoutParams $this$_init__u24lambda_u2430 = new LinearLayout.LayoutParams(-2, -2);
        $this$_init__u24lambda_u2430.topMargin = dp(12);
        Unit unit20 = Unit.INSTANCE;
        $this$_init__u24lambda_u2426.addView(textView3, $this$_init__u24lambda_u2430);
        TextView textView4 = this.lastDrive;
        LinearLayout.LayoutParams $this$_init__u24lambda_u2431 = new LinearLayout.LayoutParams(-1, -2);
        $this$_init__u24lambda_u2431.topMargin = dp(14);
        Unit unit21 = Unit.INSTANCE;
        $this$_init__u24lambda_u2426.addView(textView4, $this$_init__u24lambda_u2431);
        this.lastDrive.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.OffroadHome$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view3) {
                OffroadHome._init_$lambda$32(OffroadHome.this, view3);
            }
        });
        this.homeContent.addView($this$_init__u24lambda_u2426, new LinearLayout.LayoutParams(0, -1, 1.25f));
        LinearLayout $this$_init__u24lambda_u2433 = new LinearLayout(ctx);
        $this$_init__u24lambda_u2433.setOrientation(1);
        $this$_init__u24lambda_u2433.addView(this.expBtn, new LinearLayout.LayoutParams(-1, dp(110)));
        this.expBtn.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.OffroadHome$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view3) {
                OffroadHome.this.toggleExperimental();
            }
        });
        LinearLayout $this$_init__u24lambda_u2435 = new LinearLayout(ctx);
        $this$_init__u24lambda_u2435.setOrientation(1);
        $this$_init__u24lambda_u2435.setBackground(rounded$default(this, Color.rgb(24, 24, 28), 0, 2, null));
        $this$_init__u24lambda_u2435.setPadding(dp(22), dp(20), dp(22), dp(22));
        LinearLayout pers2 = $this$_init__u24lambda_u2435;
        TextView $this$_init__u24lambda_u2436 = tv(16.0f, Color.rgb(150, 160, 180), true);
        $this$_init__u24lambda_u2436.setText("DRIVING PERSONALITY");
        $this$_init__u24lambda_u2436.setLetterSpacing(0.12f);
        Unit unit22 = Unit.INSTANCE;
        pers2.addView($this$_init__u24lambda_u2436);
        LinearLayout $this$_init__u24lambda_u2437 = new LinearLayout(ctx);
        $this$_init__u24lambda_u2437.setOrientation(0);
        Iterable $this$forEachIndexed$iv = this.persBtns;
        int $i$f$forEachIndexed = 0;
        int i = 0;
        Iterator it = $this$forEachIndexed$iv.iterator();
        while (it.hasNext()) {
            Object item$iv = it.next();
            int index$iv = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            final TextView b = (TextView) item$iv;
            TextView textView5 = b;
            Iterable $this$forEachIndexed$iv2 = $this$forEachIndexed$iv;
            int $i$f$forEachIndexed2 = $i$f$forEachIndexed;
            Iterator it2 = it;
            LinearLayout.LayoutParams $this$lambda_u2441_u24lambda_u2438 = new LinearLayout.LayoutParams(0, -2, 1.0f);
            if (i > 0) {
                $this$lambda_u2441_u24lambda_u2438.setMarginStart(dp(10));
            }
            Unit unit23 = Unit.INSTANCE;
            $this$_init__u24lambda_u2437.addView(textView5, $this$lambda_u2441_u24lambda_u2438);
            b.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.OffroadHome$$ExternalSyntheticLambda6
                @Override // android.view.View.OnClickListener
                public final void onClick(View view3) {
                    OffroadHome.lambda$41$lambda$40(OffroadHome.this, b, view3);
                }
            });
            i = index$iv;
            $this$forEachIndexed$iv = $this$forEachIndexed$iv2;
            $i$f$forEachIndexed = $i$f$forEachIndexed2;
            it = it2;
        }
        LinearLayout.LayoutParams $this$_init__u24lambda_u2442 = new LinearLayout.LayoutParams(-1, -2);
        $this$_init__u24lambda_u2442.topMargin = dp(14);
        Unit unit24 = Unit.INSTANCE;
        pers2.addView($this$_init__u24lambda_u2437, $this$_init__u24lambda_u2442);
        LinearLayout.LayoutParams $this$_init__u24lambda_u2443 = new LinearLayout.LayoutParams(-1, -2);
        $this$_init__u24lambda_u2443.topMargin = dp(20);
        Unit unit25 = Unit.INSTANCE;
        $this$_init__u24lambda_u2433.addView(pers2, $this$_init__u24lambda_u2443);
        LinearLayout $this$_init__u24lambda_u2444 = new LinearLayout(ctx);
        $this$_init__u24lambda_u2444.setOrientation(1);
        $this$_init__u24lambda_u2444.setBackground(rounded$default(this, Color.rgb(24, 24, 28), 0, 2, null));
        $this$_init__u24lambda_u2444.setPadding(dp(26), dp(22), dp(26), dp(22));
        $this$_init__u24lambda_u2444.addView(this.devName);
        TextView textView6 = this.devLines;
        LinearLayout.LayoutParams $this$_init__u24lambda_u2445 = new LinearLayout.LayoutParams(-1, 0, 1.0f);
        $this$_init__u24lambda_u2445.topMargin = dp(12);
        Unit unit26 = Unit.INSTANCE;
        $this$_init__u24lambda_u2444.addView(textView6, $this$_init__u24lambda_u2445);
        LinearLayout $this$_init__u24lambda_u2446 = new LinearLayout(ctx);
        $this$_init__u24lambda_u2446.setOrientation(0);
        LinearLayout buttons = $this$_init__u24lambda_u2446;
        int index$iv2 = 0;
        for (Object item$iv2 : CollectionsKt.listOf((Object[]) new Pair[]{TuplesKt.to("Settings", new Function0() { // from class: com.starpilot.starview.OffroadHome$$ExternalSyntheticLambda7
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit _init_$lambda$47;
                _init_$lambda$47 = OffroadHome._init_$lambda$47(OffroadHome.this);
                return _init_$lambda$47;
            }
        }), TuplesKt.to("Files", new Function0() { // from class: com.starpilot.starview.OffroadHome$$ExternalSyntheticLambda8
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit _init_$lambda$48;
                _init_$lambda$48 = OffroadHome._init_$lambda$48(OffroadHome.this);
                return _init_$lambda$48;
            }
        }), TuplesKt.to("Terminal", new Function0() { // from class: com.starpilot.starview.OffroadHome$$ExternalSyntheticLambda9
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit _init_$lambda$49;
                _init_$lambda$49 = OffroadHome._init_$lambda$49(OffroadHome.this);
                return _init_$lambda$49;
            }
        })})) {
            int index$iv3 = index$iv2 + 1;
            if (index$iv2 < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            Pair pair2 = (Pair) item$iv2;
            int i2 = index$iv2;
            String label2 = (String) pair2.component1();
            final Function0 f = (Function0) pair2.component2();
            LinearLayout header = pers;
            LinearLayout buttons2 = buttons;
            LinearLayout pers3 = pers2;
            TextView $this$lambda_u2453_u24lambda_u2451 = tv$default(this, 18.0f, 0, true, 2, null);
            $this$lambda_u2453_u24lambda_u2451.setText(label2);
            $this$lambda_u2453_u24lambda_u2451.setGravity(17);
            $this$lambda_u2453_u24lambda_u2451.setPadding(0, dp(16), 0, dp(16));
            $this$lambda_u2453_u24lambda_u2451.setBackground(rounded(Color.rgb(44, 48, 60), 14));
            $this$lambda_u2453_u24lambda_u2451.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.OffroadHome$$ExternalSyntheticLambda10
                @Override // android.view.View.OnClickListener
                public final void onClick(View view3) {
                    Function0.this.invoke();
                }
            });
            TextView textView7 = $this$lambda_u2453_u24lambda_u2451;
            LinearLayout.LayoutParams $this$lambda_u2453_u24lambda_u2452 = new LinearLayout.LayoutParams(0, -2, 1.0f);
            if (i2 > 0) {
                $this$lambda_u2453_u24lambda_u2452.setMarginStart(dp(10));
            }
            Unit unit27 = Unit.INSTANCE;
            buttons2.addView(textView7, $this$lambda_u2453_u24lambda_u2452);
            buttons = buttons2;
            pers2 = pers3;
            index$iv2 = index$iv3;
            pers = header;
        }
        LinearLayout.LayoutParams $this$_init__u24lambda_u2454 = new LinearLayout.LayoutParams(-1, -2);
        $this$_init__u24lambda_u2454.topMargin = dp(12);
        Unit unit28 = Unit.INSTANCE;
        $this$_init__u24lambda_u2444.addView(buttons, $this$_init__u24lambda_u2454);
        LinearLayout.LayoutParams $this$_init__u24lambda_u2455 = new LinearLayout.LayoutParams(-1, 0, 1.0f);
        $this$_init__u24lambda_u2455.topMargin = dp(20);
        Unit unit29 = Unit.INSTANCE;
        $this$_init__u24lambda_u2433.addView($this$_init__u24lambda_u2444, $this$_init__u24lambda_u2455);
        LinearLayout.LayoutParams $this$_init__u24lambda_u2456 = new LinearLayout.LayoutParams(0, -1, 1.0f);
        $this$_init__u24lambda_u2456.setMarginStart(dp(24));
        Unit unit30 = Unit.INSTANCE;
        this.homeContent.addView($this$_init__u24lambda_u2433, $this$_init__u24lambda_u2456);
        body.addView(this.homeContent, new FrameLayout.LayoutParams(-1, -1));
        LinearLayout $this$_init__u24lambda_u2457 = new LinearLayout(ctx);
        $this$_init__u24lambda_u2457.setOrientation(1);
        $this$_init__u24lambda_u2457.setBackground(rounded$default(this, Color.rgb(24, 24, 28), 0, 2, null));
        $this$_init__u24lambda_u2457.setPadding(dp(36), dp(30), dp(36), dp(30));
        $this$_init__u24lambda_u2457.setVisibility(8);
        Unit unit31 = Unit.INSTANCE;
        this.detailContent = $this$_init__u24lambda_u2457;
        this.detailContent.addView(this.detailTitle);
        LinearLayout linearLayout = this.detailContent;
        ScrollView $this$_init__u24lambda_u2458 = new ScrollView(ctx);
        $this$_init__u24lambda_u2458.addView(this.detailBody);
        Unit unit32 = Unit.INSTANCE;
        LinearLayout.LayoutParams $this$_init__u24lambda_u2459 = new LinearLayout.LayoutParams(-1, 0, 1.0f);
        $this$_init__u24lambda_u2459.topMargin = dp(20);
        $this$_init__u24lambda_u2459.bottomMargin = dp(20);
        Unit unit33 = Unit.INSTANCE;
        linearLayout.addView($this$_init__u24lambda_u2458, $this$_init__u24lambda_u2459);
        LinearLayout $this$_init__u24lambda_u2460 = new LinearLayout(ctx);
        $this$_init__u24lambda_u2460.setOrientation(0);
        $this$_init__u24lambda_u2460.setGravity(8388613);
        TextView $this$_init__u24lambda_u2462 = tv$default(this, 20.0f, 0, true, 2, null);
        $this$_init__u24lambda_u2462.setText("Close");
        $this$_init__u24lambda_u2462.setGravity(17);
        $this$_init__u24lambda_u2462.setPadding(dp(28), dp(16), dp(28), dp(16));
        $this$_init__u24lambda_u2462.setBackground(rounded(Color.rgb(44, 48, 60), 14));
        $this$_init__u24lambda_u2462.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.OffroadHome$$ExternalSyntheticLambda11
            @Override // android.view.View.OnClickListener
            public final void onClick(View view3) {
                OffroadHome.this.setState(OffroadHome.State.HOME);
            }
        });
        Unit unit34 = Unit.INSTANCE;
        $this$_init__u24lambda_u2460.addView($this$_init__u24lambda_u2462);
        TextView textView8 = this.detailAction;
        LinearLayout.LayoutParams $this$_init__u24lambda_u2463 = new LinearLayout.LayoutParams(-2, -2);
        $this$_init__u24lambda_u2463.setMarginStart(dp(14));
        Unit unit35 = Unit.INSTANCE;
        $this$_init__u24lambda_u2460.addView(textView8, $this$_init__u24lambda_u2463);
        this.detailContent.addView($this$_init__u24lambda_u2460, new LinearLayout.LayoutParams(-1, -2));
        body.addView(this.detailContent, new FrameLayout.LayoutParams(-1, -1));
        this.ambient.addView(this.ambClock);
        LinearLayout linearLayout2 = this.ambient;
        TextView textView9 = this.ambDate;
        LinearLayout.LayoutParams $this$_init__u24lambda_u2464 = new LinearLayout.LayoutParams(-2, -2);
        $this$_init__u24lambda_u2464.topMargin = dp(8);
        Unit unit36 = Unit.INSTANCE;
        linearLayout2.addView(textView9, $this$_init__u24lambda_u2464);
        LinearLayout linearLayout3 = this.ambient;
        TextView textView10 = this.ambLine;
        LinearLayout.LayoutParams $this$_init__u24lambda_u2465 = new LinearLayout.LayoutParams(-2, -2);
        $this$_init__u24lambda_u2465.topMargin = dp(30);
        Unit unit37 = Unit.INSTANCE;
        linearLayout3.addView(textView10, $this$_init__u24lambda_u2465);
        addView(this.ambient, new FrameLayout.LayoutParams(-1, -1));
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

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries($VALUES);

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
            textView.setText(CollectionsKt.joinToString$default(CollectionsKt.listOfNotNull((Object[]) strArr), "   ·   ", null, null, 0, null, null, 62, null));
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
            for (Pair pair : CollectionsKt.listOf((Object[]) new Pair[]{TuplesKt.to(textView, $this$view_u24lambda_u247_u24lambda_u242), TuplesKt.to(this.dist, this.distUnit), TuplesKt.to(textView2, $this$view_u24lambda_u247_u24lambda_u243)})) {
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

        /* renamed from: component1, reason: from getter */
        public final String getText() {
            return this.text;
        }

        /* renamed from: component2, reason: from getter */
        public final int getSeverity() {
            return this.severity;
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
            String it2 = entry$iv.getKey();
            if (StringsKt.startsWith$default(it2, "Offroad_", false, 2, (Object) null)) {
                result$iv.put(entry$iv.getKey(), entry$iv.getValue());
            }
        }
        LinkedHashMap $this$mapNotNull$iv2 = result$iv;
        int $i$f$mapNotNull2 = 0;
        Collection destination$iv$iv = new ArrayList();
        Map $this$mapNotNullTo$iv$iv3 = $this$mapNotNull$iv2;
        for (Map.Entry element$iv$iv$iv : $this$mapNotNullTo$iv$iv3.entrySet()) {
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
                String text = StringsKt.replace$default(optString, "%1", optString2, false, 4, (Object) null);
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
            public final int compare(T t2, T t3) {
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

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:211:0x058e, code lost:
    
        if (r1.equals("disconnected") == false) goto L176;
     */
    /* JADX WARN: Removed duplicated region for block: B:217:0x05a5  */
    /* JADX WARN: Removed duplicated region for block: B:218:0x05a7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void refresh(Telemetry t, boolean force) {
        JSONObject week;
        boolean metric;
        int p;
        boolean metric2;
        int rgb;
        char c;
        int rgb2;
        Intrinsics.checkNotNullParameter(t, "t");
        this.lastT = t;
        ambientTick(t);
        paintLastDrive();
        if (force || t.getParamsGeneration() != this.lastGen) {
            this.lastGen = t.getParamsGeneration();
            boolean metric3 = t.pBool("IsMetric", false);
            List al = alerts(t);
            boolean update = t.pBool("UpdateAvailable", false);
            this.updateBtn.setVisibility(update ? 0 : 8);
            this.alertsBtn.setVisibility(!al.isEmpty() ? 0 : 8);
            this.alertsBtn.setText(al.size() == 1 ? "1 ALERT" : al.size() + " ALERTS");
            String desc = StringsKt.trim((CharSequence) Telemetry.pStr$default(t, "UpdaterCurrentDescription", null, 2, null)).toString();
            String pStr$default = Telemetry.pStr$default(t, "DrivingModelName", null, 2, null);
            if (StringsKt.isBlank(pStr$default)) {
                pStr$default = Telemetry.pStr$default(t, "Model", null, 2, null);
            }
            String model = StringsKt.trim((CharSequence) StringsKt.replace$default(StringsKt.replace$default(pStr$default, "_default", "", false, 4, (Object) null), "(Default)", "", false, 4, (Object) null)).toString();
            SpannableStringBuilder sb = new SpannableStringBuilder();
            sb.append((CharSequence) "StarPilot");
            sb.setSpan(new StyleSpan(1), 0, sb.length(), 0);
            if (desc.length() > 0) {
                sb.append((CharSequence) "  ").append((CharSequence) desc);
            }
            if (model.length() > 0) {
                sb.append((CharSequence) "  -  ").append((CharSequence) model);
            }
            this.version.setText(sb);
            if (update && !this.prevUpdate) {
                this.state = State.UPDATE;
            } else if (!al.isEmpty() && !this.prevAlerts) {
                this.state = State.ALERTS;
            }
            if (!update && this.state == State.UPDATE) {
                this.state = !al.isEmpty() ? State.ALERTS : State.HOME;
            }
            if (al.isEmpty() && this.state == State.ALERTS) {
                this.state = update ? State.UPDATE : State.HOME;
            }
            this.prevUpdate = update;
            this.prevAlerts = !al.isEmpty();
            this.homeContent.setVisibility(this.state == State.HOME ? 0 : 8);
            this.detailContent.setVisibility(this.state == State.HOME ? 8 : 0);
            float f = 1.0f;
            switch (WhenMappings.$EnumSwitchMapping$0[this.state.ordinal()]) {
                case 1:
                    JSONObject cloud = jobj(t.getParams().get("ApiCache_DriveStats"));
                    JSONObject sp = jobj(t.getParams().get("StarPilotStats"));
                    JSONObject all = cloud != null ? cloud.optJSONObject("all") : null;
                    JSONObject week2 = cloud != null ? cloud.optJSONObject("week") : null;
                    double kmPerMile = 1.609344d;
                    if (all == null || num(all, "routes") <= 0.0d) {
                        week = week2;
                        metric = metric3;
                        if (sp != null) {
                            StatBlock statBlock = this.allTime;
                            int num = (int) num(sp, "StarPilotDrives");
                            double num2 = num(sp, "StarPilotMeters");
                            double d = metric ? 1000.0d : 1609.344d;
                            metric = metric;
                            statBlock.set(num, num2 / d, num(sp, "StarPilotSeconds") / 3600.0d, metric);
                        } else {
                            this.allTime.set(0, 0.0d, 0.0d, metric);
                        }
                    } else {
                        StatBlock statBlock2 = this.allTime;
                        int num3 = (int) num(all, "routes");
                        double refresh$cloudDist = refresh$cloudDist(this, metric3, 1.609344d, all);
                        double num4 = num(all, "minutes") / 60.0d;
                        week = week2;
                        metric = metric3;
                        statBlock2.set(num3, refresh$cloudDist, num4, metric3);
                    }
                    boolean metric4 = metric;
                    this.pastWeek.set((int) num(week, "routes"), refresh$cloudDist(this, metric4, 1.609344d, week), num(week, "minutes") / 60.0d, metric4);
                    String str = "";
                    this.statsNote.setText(cloud != null ? "" : t.getSp7() ? "StarPilot 7 doesn't keep drive stats yet." : "Past week needs comma connect drive stats (the comma caches them when online).");
                    boolean exp = t.pBool("ExperimentalMode", false);
                    this.expBtn.setText(exp ? "EXPERIMENTAL MODE ON" : "CHILL MODE ON");
                    this.expBtn.setTextColor(exp ? -1 : Color.rgb(20, 30, 28));
                    TextView textView = this.expBtn;
                    GradientDrawable $this$refresh_u24lambda_u2475 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, exp ? new int[]{Color.rgb(255, 155, 63), Color.rgb(219, 56, 34)} : new int[]{Color.rgb(20, 255, 171), Color.rgb(35, 149, 255)});
                    $this$refresh_u24lambda_u2475.setCornerRadius(dp(20));
                    textView.setBackground($this$refresh_u24lambda_u2475);
                    int p2 = t.pInt("LongitudinalPersonality", 1);
                    Iterable $this$forEach$iv = this.persBtns;
                    for (Object element$iv : $this$forEach$iv) {
                        TextView b = (TextView) element$iv;
                        double kmPerMile2 = kmPerMile;
                        boolean on = Intrinsics.areEqual(b.getTag(), Integer.valueOf(p2));
                        if (on) {
                            p = p2;
                            metric2 = metric4;
                            rgb = Color.rgb(54, 77, 239);
                        } else {
                            p = p2;
                            metric2 = metric4;
                            rgb = Color.rgb(44, 48, 60);
                        }
                        b.setBackground(rounded(rgb, 14));
                        b.setTextColor(on ? -1 : Color.rgb(190, 198, 214));
                        kmPerMile = kmPerMile2;
                        p2 = p;
                        metric4 = metric2;
                    }
                    TextView textView2 = this.devName;
                    String pStr$default2 = Telemetry.pStr$default(t, "GalaxyDeviceName", null, 2, null);
                    if (pStr$default2.length() == 0) {
                        pStr$default2 = "comma";
                    }
                    textView2.setText(pStr$default2);
                    String why = Telemetry.Companion.chestnutWhy(Telemetry.pStr$default(t, "ChestnutWhy", null, 2, null));
                    String pStr$default3 = Telemetry.pStr$default(t, "ChestnutState", null, 2, null);
                    String str2 = "not detected";
                    switch (pStr$default3.hashCode()) {
                        case -1422950650:
                            if (pStr$default3.equals("active")) {
                                str2 = "active";
                                break;
                            }
                            if (t.pBool("UsbGpuActive", false)) {
                                str2 = "active";
                                break;
                            } else if (!t.getChestnutPresent() || !t.pBool("UsbGpuCompiled", false)) {
                                if (t.getChestnutPresent()) {
                                    str2 = "connected";
                                    break;
                                }
                            } else {
                                str2 = "connected, model ready";
                                break;
                            }
                            break;
                        case -1381388741:
                            break;
                        case -1281977283:
                            if (pStr$default3.equals("failed")) {
                                str2 = "FAILED" + (why.length() > 0 ? " - " + why : "");
                                break;
                            }
                            if (t.pBool("UsbGpuActive", false)) {
                            }
                            break;
                        case 108386723:
                            if (pStr$default3.equals("ready")) {
                                str2 = "connected, model ready";
                                break;
                            }
                            if (t.pBool("UsbGpuActive", false)) {
                            }
                            break;
                        case 336650556:
                            if (pStr$default3.equals("loading")) {
                                str2 = "loading";
                                break;
                            }
                            if (t.pBool("UsbGpuActive", false)) {
                            }
                            break;
                        default:
                            if (t.pBool("UsbGpuActive", false)) {
                            }
                            break;
                    }
                    String egpu = str2;
                    boolean sshOn = t.pBool("SshEnabled", false);
                    String branch = Telemetry.pStr$default(t, "GitBranch", null, 2, null);
                    TextView textView3 = this.devLines;
                    StringBuilder $this$refresh_u24lambda_u2480 = new StringBuilder();
                    String pStr$default4 = Telemetry.pStr$default(t, "DongleId", null, 2, null);
                    if (pStr$default4.length() == 0) {
                        pStr$default4 = "–";
                    }
                    $this$refresh_u24lambda_u2480.append("Dongle ID   " + ((Object) pStr$default4) + "\n");
                    if (branch.length() > 0) {
                        $this$refresh_u24lambda_u2480.append("Branch      " + branch + "\n");
                    }
                    $this$refresh_u24lambda_u2480.append("eGPU        " + egpu + "\n");
                    JSONObject sto = this.storage;
                    JSONObject inn = sto != null ? sto.optJSONObject("internal") : null;
                    JSONObject stick = sto != null ? sto.optJSONObject("stick") : null;
                    if (inn != null) {
                        $this$refresh_u24lambda_u2480.append("Internal    " + gb(inn, "free_GB") + " free of " + gb(inn, "total_GB") + " GB");
                        if (inn.has("newest_time")) {
                            $this$refresh_u24lambda_u2480.append("  ·  newest drive " + whenStr(inn.optDouble("newest_time")));
                        }
                        $this$refresh_u24lambda_u2480.append("\n");
                    } else {
                        $this$refresh_u24lambda_u2480.append("Storage     " + MathKt.roundToInt(t.getFreeSpacePct()) + "% free\n");
                    }
                    if (stick != null) {
                        $this$refresh_u24lambda_u2480.append("USB stick   ");
                        if (!stick.optBoolean("present")) {
                            $this$refresh_u24lambda_u2480.append("not connected");
                        } else if (stick.has("stick_free_GB")) {
                            $this$refresh_u24lambda_u2480.append(gb(stick, "stick_free_GB") + " free of " + gb(stick, "stick_size_GB") + " GB");
                        } else {
                            $this$refresh_u24lambda_u2480.append("connected, no copy finished yet");
                        }
                        $this$refresh_u24lambda_u2480.append("\n");
                        StorageInfo.Copying it = StorageInfo.INSTANCE.copying(sto);
                        if (it != null) {
                            $this$refresh_u24lambda_u2480.append("  copying now: " + it.getLine() + "\n");
                        }
                        if (stick.optBoolean("present")) {
                            double lc = stick.optDouble("last_copy_time", Double.NaN);
                            double run = stick.optDouble("run_time", Double.NaN);
                            $this$refresh_u24lambda_u2480.append("  last copy " + (Double.isNaN(lc) ? "–" : whenStr(lc) + " (" + StorageInfo.INSTANCE.mb(stick.optInt("last_copy_MB")) + ")"));
                            if (stick.has("stick_newest_time")) {
                                $this$refresh_u24lambda_u2480.append("  ·  newest on stick " + whenStr(stick.optDouble("stick_newest_time")));
                            }
                            $this$refresh_u24lambda_u2480.append("\n");
                            String res = stick.optString("result");
                            String whenStr = Double.isNaN(run) ? "never" : whenStr(run);
                            Intrinsics.checkNotNull(res);
                            if ((res.length() > 0) && !Intrinsics.areEqual(res, "ok")) {
                                str = "  ·  " + res;
                            }
                            $this$refresh_u24lambda_u2480.append("  checked " + whenStr + str);
                            if (!stick.optBoolean("auto", true)) {
                                $this$refresh_u24lambda_u2480.append("  ·  auto copy OFF");
                            }
                            $this$refresh_u24lambda_u2480.append("\n");
                        }
                    }
                    $this$refresh_u24lambda_u2480.append("SSH         " + (sshOn ? "on" : "off"));
                    String sb2 = $this$refresh_u24lambda_u2480.toString();
                    Intrinsics.checkNotNullExpressionValue(sb2, "toString(...)");
                    textView3.setText(sb2);
                    Unit unit = Unit.INSTANCE;
                    return;
                case 2:
                    this.detailTitle.setText("Update available");
                    this.detailBody.removeAllViews();
                    String pStr$default5 = Telemetry.pStr$default(t, "UpdaterNewDescription", null, 2, null);
                    if (StringsKt.isBlank(pStr$default5)) {
                        pStr$default5 = "A new version is ready to install.";
                    }
                    String newDesc = pStr$default5;
                    LinearLayout linearLayout = this.detailBody;
                    TextView $this$refresh_u24lambda_u2482 = tv$default(this, 22.0f, Color.rgb(210, 216, 228), false, 4, null);
                    $this$refresh_u24lambda_u2482.setText(newDesc);
                    $this$refresh_u24lambda_u2482.setLineSpacing(dp(6), 1.0f);
                    linearLayout.addView($this$refresh_u24lambda_u2482);
                    this.detailAction.setText("Open Software settings");
                    this.detailAction.setVisibility(0);
                    this.detailAction.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.OffroadHome$$ExternalSyntheticLambda1
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            OffroadHome.refresh$lambda$83(OffroadHome.this, view);
                        }
                    });
                    Unit unit2 = Unit.INSTANCE;
                    return;
                case 3:
                    this.detailTitle.setText(al.size() == 1 ? "1 alert" : al.size() + " alerts");
                    this.detailBody.removeAllViews();
                    for (Alert a : al) {
                        LinearLayout linearLayout2 = this.detailBody;
                        TextView $this$refresh_u24lambda_u2484 = tv$default(this, 21.0f, -1, false, 4, null);
                        $this$refresh_u24lambda_u2484.setText(a.getText());
                        $this$refresh_u24lambda_u2484.setLineSpacing(dp(5), f);
                        $this$refresh_u24lambda_u2484.setPadding(dp(24), dp(20), dp(24), dp(20));
                        if (a.getSeverity() > 0) {
                            rgb2 = Color.rgb(201, 34, 49);
                            c = ',';
                        } else {
                            c = ',';
                            rgb2 = Color.rgb(44, 48, 60);
                        }
                        $this$refresh_u24lambda_u2484.setBackground(rounded(rgb2, 16));
                        LinearLayout.LayoutParams $this$refresh_u24lambda_u2485 = new LinearLayout.LayoutParams(-1, -2);
                        $this$refresh_u24lambda_u2485.bottomMargin = dp(12);
                        Unit unit3 = Unit.INSTANCE;
                        linearLayout2.addView($this$refresh_u24lambda_u2484, $this$refresh_u24lambda_u2485);
                        f = 1.0f;
                    }
                    this.detailAction.setVisibility(8);
                    Unit unit4 = Unit.INSTANCE;
                    return;
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
