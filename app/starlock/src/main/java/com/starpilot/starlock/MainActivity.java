package com.starpilot.starlock;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.StatusBarManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.Icon;
import android.graphics.drawable.RippleDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.text.style.ForegroundColorSpan;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.app.NotificationCompat;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.common.MlKitException;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning;
import com.starpilot.starlock.CarLocks;
import com.starpilot.starlock.Door;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import kotlin.Deprecated;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import org.json.JSONObject;
import rikka.shizuku.Shizuku;

/* compiled from: MainActivity.kt */
public final class MainActivity extends Activity {
    private TextView bleState;
    private TextView carLine;
    private volatile boolean closing;
    private volatile boolean fingerDown;
    private TextView heroText;
    private Function0<Unit> hotRefresh;
    private GradientDrawable lockBg;
    private TextView logView;
    private ScrollView scroller;
    private LinearLayout settingsPanel;
    private TextView status;
    private GradientDrawable unlockBg;
    private VanView van;
    private GradientDrawable windowBg;
    private final Handler main = new Handler(Looper.getMainLooper());
    private final AtomicBoolean busy = new AtomicBoolean(false);

    /* renamed from: prefs$delegate, reason: from kotlin metadata */
    private final Lazy prefs = LazyKt.lazy(new Function0() { // from class: com.starpilot.starlock.MainActivity$$ExternalSyntheticLambda58
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            SharedPreferences prefs_delegate$lambda$0;
            prefs_delegate$lambda$0 = MainActivity.prefs_delegate$lambda$0(MainActivity.this);
            return prefs_delegate$lambda$0;
        }
    });

    /* renamed from: dot$delegate, reason: from kotlin metadata */
    private final Lazy dot = LazyKt.lazy(new Function0() { // from class: com.starpilot.starlock.MainActivity$$ExternalSyntheticLambda1
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            int dot_delegate$lambda$3;
            dot_delegate$lambda$3 = MainActivity.dot_delegate$lambda$3(MainActivity.this);
            return Integer.valueOf(dot_delegate$lambda$3);
        }
    });
    private final Shizuku.OnRequestPermissionResultListener shizukuListener = new Shizuku.OnRequestPermissionResultListener() { // from class: com.starpilot.starlock.MainActivity$$ExternalSyntheticLambda2
        @Override // rikka.shizuku.Shizuku.OnRequestPermissionResultListener
        public final void onRequestPermissionResult(int i, int i2) {
            MainActivity.shizukuListener$lambda$10(MainActivity.this, i, i2);
        }
    };
    private final AtomicInteger press = new AtomicInteger(0);

    /* compiled from: MainActivity.kt */
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Outcome.values().length];
            try {
                iArr[Outcome.CONFIRMED.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[Outcome.SENT.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    private final SharedPreferences getPrefs() {
        return (SharedPreferences) this.prefs.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final SharedPreferences prefs_delegate$lambda$0(MainActivity this$0) {
        return this$0.getSharedPreferences("starlock", 0);
    }

    private final Pairing pairing() {
        String it = getPrefs().getString("link", null);
        if (it != null) {
            return Pairing.Companion.parse(it);
        }
        return null;
    }

    private final String sound() {
        String it = getPrefs().getString("sound", "horn");
        if (!CollectionsKt.contains(SetsKt.setOf((Object[]) new String[]{"horn", "buzzer", "none"}), it)) {
            it = null;
        }
        return it == null ? "horn" : it;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int dot_delegate$lambda$3(MainActivity this$0) {
        return (int) (9 * this$0.getResources().getDisplayMetrics().density);
    }

    private final int getDot() {
        return ((Number) this.dot.getValue()).intValue();
    }

    private final void showBle() {
        String str;
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        if (this.bleState == null) {
            return;
        }
        BleLink l = KeeperService.Companion.getLink();
        TextView textView = this.bleState;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("bleState");
            textView = null;
        }
        boolean z = false;
        if (!bleAllowed()) {
            str = "Bluetooth not allowed";
        } else if (l != null && l.getReady()) {
            str = "Connected";
        } else if (KeeperService.Companion.getReconnecting()) {
            str = "Connecting…";
        } else {
            str = l != null ? "Waiting for the comma" : "Starting…";
        }
        textView.setText(str);
        if (l != null && l.getReady()) {
            z = true;
        }
        boolean on = z;
        TextView textView2 = this.bleState;
        if (textView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("bleState");
            textView2 = null;
        }
        if (on) {
            i = 247;
            i2 = 207;
            i3 = 183;
        } else {
            i = 198;
            i2 = 208;
            i3 = 190;
        }
        textView2.setTextColor(Color.rgb(i3, i, i2));
        TextView textView3 = this.bleState;
        if (textView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("bleState");
            textView3 = null;
        }
        Drawable background = textView3.getBackground();
        GradientDrawable $this$showBle_u24lambda_u244 = background instanceof GradientDrawable ? (GradientDrawable) background : null;
        if ($this$showBle_u24lambda_u244 != null) {
            $this$showBle_u24lambda_u244.setColor(on ? Color.argb(26, 74, 222, 128) : Color.argb(26, 255, 255, 255));
            $this$showBle_u24lambda_u244.setStroke(1, on ? Color.argb(64, 74, 222, 128) : Color.argb(30, 255, 255, 255));
        }
        TextView textView4 = this.bleState;
        if (textView4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("bleState");
            textView4 = null;
        }
        GradientDrawable $this$showBle_u24lambda_u245 = new GradientDrawable();
        $this$showBle_u24lambda_u245.setShape(1);
        $this$showBle_u24lambda_u245.setSize((getDot() * 3) / 4, (getDot() * 3) / 4);
        if (on) {
            i4 = 211;
            i5 = 120;
            i6 = 52;
        } else {
            i4 = 170;
            i5 = 60;
            i6 = 240;
        }
        $this$showBle_u24lambda_u245.setColor(Color.rgb(i6, i4, i5));
        textView4.setCompoundDrawablesRelativeWithIntrinsicBounds($this$showBle_u24lambda_u245, (Drawable) null, (Drawable) null, (Drawable) null);
        showHero$default(this, null, 1, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onResume$lambda$6(MainActivity this$0, String msg) {
        Intrinsics.checkNotNullParameter(msg, "msg");
        TextView textView = this$0.status;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("status");
            textView = null;
        }
        textView.setText(msg);
        TextView textView2 = this$0.status;
        if (textView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("status");
            textView2 = null;
        }
        textView2.setTextColor(Color.rgb(94, 229, 160));
        showHero$default(this$0, null, 1, null);
        return Unit.INSTANCE;
    }

    @Override // android.app.Activity
    protected void onResume() {
        AutoEvents.INSTANCE.setListener(new Function1() { // from class: com.starpilot.starlock.MainActivity$$ExternalSyntheticLambda44
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit onResume$lambda$6;
                onResume$lambda$6 = MainActivity.onResume$lambda$6(MainActivity.this, (String) obj);
                return onResume$lambda$6;
            }
        });
        CarLocks.INSTANCE.setListener(new Function0() { // from class: com.starpilot.starlock.MainActivity$$ExternalSyntheticLambda54
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit onResume$lambda$7;
                onResume$lambda$7 = MainActivity.onResume$lambda$7(MainActivity.this);
                return onResume$lambda$7;
            }
        });
        CarLocks.INSTANCE.ask();
        super.onResume();
        AppLog.INSTANCE.setSink(new MainActivity$onResume$3(this));
        KeeperService.Companion.setOnState(new Function0() { // from class: com.starpilot.starlock.MainActivity$$ExternalSyntheticLambda55
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit onResume$lambda$8;
                onResume$lambda$8 = MainActivity.onResume$lambda$8(MainActivity.this);
                return onResume$lambda$8;
            }
        });
        KeeperService.Companion.start(this);
        KeeperService companion = KeeperService.Companion.getInstance();
        if (companion != null) {
            companion.reconnect("app opened");
        }
        showBle();
        Function0<Unit> function0 = this.hotRefresh;
        if (function0 != null) {
            function0.invoke();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onResume$lambda$7(MainActivity this$0) {
        showHero$default(this$0, null, 1, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onResume$lambda$8(MainActivity this$0) {
        this$0.showBle();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void shizukuListener$lambda$10(final MainActivity this$0, int i, int i2) {
        this$0.main.post(new Runnable() { // from class: com.starpilot.starlock.MainActivity$$ExternalSyntheticLambda47
            @Override // java.lang.Runnable
            public final void run() {
                MainActivity.shizukuListener$lambda$10$lambda$9(this$0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void shizukuListener$lambda$10$lambda$9(MainActivity this$0) {
        Function0<Unit> function0 = this$0.hotRefresh;
        if (function0 != null) {
            function0.invoke();
        }
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        try {
            Result.Companion companion = Result.Companion;
            MainActivity $this$onDestroy_u24lambda_u2411 = this;
            KtCompat.resultOf(Boolean.valueOf(Shizuku.removeRequestPermissionResultListener($this$onDestroy_u24lambda_u2411.shizukuListener)));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            KtCompat.resultOf(ResultKt.createFailure(th));
        }
        super.onDestroy();
    }

    @Override // android.app.Activity
    protected void onPause() {
        super.onPause();
        this.fingerDown = false;
        KeeperService.Companion.setOnState(null);
        AutoEvents.INSTANCE.setListener(null);
        CarLocks.INSTANCE.setListener(null);
        KeeperService.Companion.appLeft(this);
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle b) {
        GradientDrawable gradientDrawable;
        GradientDrawable gradientDrawable2;
        GradientDrawable gradientDrawable3;
        Object m61constructorimpl;
        super.onCreate(b);
        float d = getResources().getDisplayMetrics().density;
        LinearLayout $this$onCreate_u24lambda_u2412 = new LinearLayout(this);
        $this$onCreate_u24lambda_u2412.setOrientation(1);
        $this$onCreate_u24lambda_u2412.setPadding(onCreate$dp(d, 20), onCreate$dp(d, 40), onCreate$dp(d, 20), onCreate$dp(d, 28));
        LinearLayout page = $this$onCreate_u24lambda_u2412;
        LinearLayout $this$onCreate_u24lambda_u2413 = new LinearLayout(this);
        $this$onCreate_u24lambda_u2413.setOrientation(1);
        $this$onCreate_u24lambda_u2413.setMinimumHeight($this$onCreate_u24lambda_u2413.getResources().getDisplayMetrics().heightPixels - onCreate$dp(d, 84));
        page.addView($this$onCreate_u24lambda_u2413);
        LinearLayout $this$onCreate_u24lambda_u2414 = new LinearLayout(this);
        $this$onCreate_u24lambda_u2414.setOrientation(0);
        $this$onCreate_u24lambda_u2414.setGravity(16);
        TextView $this$onCreate_u24lambda_u2416 = new TextView(this);
        SpannableString $this$onCreate_u24lambda_u2416_u24lambda_u2415 = new SpannableString("StarLock");
        $this$onCreate_u24lambda_u2416_u24lambda_u2415.setSpan(new ForegroundColorSpan(Color.rgb(74, 222, 128)), 4, 8, 0);
        $this$onCreate_u24lambda_u2416.setText($this$onCreate_u24lambda_u2416_u24lambda_u2415);
        $this$onCreate_u24lambda_u2416.setTextSize(25.0f);
        $this$onCreate_u24lambda_u2416.setTypeface(Typeface.create("sans-serif-medium", 1));
        $this$onCreate_u24lambda_u2416.setTextColor(-1);
        $this$onCreate_u24lambda_u2414.addView($this$onCreate_u24lambda_u2416, new LinearLayout.LayoutParams(0, -2, 1.0f));
        TextView $this$onCreate_u24lambda_u2418 = new TextView(this);
        $this$onCreate_u24lambda_u2418.setTextSize(12.0f);
        $this$onCreate_u24lambda_u2418.setPadding(onCreate$dp(d, 11), onCreate$dp(d, 5), onCreate$dp(d, 12), onCreate$dp(d, 5));
        $this$onCreate_u24lambda_u2418.setCompoundDrawablePadding(onCreate$dp(d, 7));
        GradientDrawable $this$onCreate_u24lambda_u2418_u24lambda_u2417 = new GradientDrawable();
        $this$onCreate_u24lambda_u2418_u24lambda_u2417.setCornerRadius(onCreate$dp(d, 20));
        $this$onCreate_u24lambda_u2418_u24lambda_u2417.setColor(Color.argb(26, 255, 255, 255));
        $this$onCreate_u24lambda_u2418.setBackground($this$onCreate_u24lambda_u2418_u24lambda_u2417);
        this.bleState = $this$onCreate_u24lambda_u2418;
        TextView textView = this.bleState;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("bleState");
            textView = null;
        }
        LinearLayout.LayoutParams $this$onCreate_u24lambda_u2419 = new LinearLayout.LayoutParams(-2, -2);
        $this$onCreate_u24lambda_u2419.rightMargin = onCreate$dp(d, 10);
        Unit unit = Unit.INSTANCE;
        $this$onCreate_u24lambda_u2414.addView(textView, $this$onCreate_u24lambda_u2419);
        ImageButton $this$onCreate_u24lambda_u2422 = new ImageButton(this);
        $this$onCreate_u24lambda_u2422.setImageResource(R.drawable.ic_settings);
        $this$onCreate_u24lambda_u2422.setImageTintList(ColorStateList.valueOf(Color.rgb(200, 206, 214)));
        GradientDrawable $this$onCreate_u24lambda_u2422_u24lambda_u2420 = new GradientDrawable();
        $this$onCreate_u24lambda_u2422_u24lambda_u2420.setShape(1);
        $this$onCreate_u24lambda_u2422_u24lambda_u2420.setColor(Color.argb(20, 255, 255, 255));
        $this$onCreate_u24lambda_u2422.setBackground($this$onCreate_u24lambda_u2422_u24lambda_u2420);
        $this$onCreate_u24lambda_u2422.setContentDescription("Settings");
        $this$onCreate_u24lambda_u2422.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starlock.MainActivity$$ExternalSyntheticLambda12
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.this.toggleSettings();
            }
        });
        $this$onCreate_u24lambda_u2414.addView($this$onCreate_u24lambda_u2422, new LinearLayout.LayoutParams(onCreate$dp(d, 42), onCreate$dp(d, 42)));
        $this$onCreate_u24lambda_u2413.addView($this$onCreate_u24lambda_u2414);
        this.van = new VanView(this);
        VanView vanView = this.van;
        if (vanView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("van");
            vanView = null;
        }
        LinearLayout.LayoutParams $this$onCreate_u24lambda_u2423 = new LinearLayout.LayoutParams(-1, onCreate$dp(d, 330));
        $this$onCreate_u24lambda_u2423.topMargin = onCreate$dp(d, 10);
        Unit unit2 = Unit.INSTANCE;
        $this$onCreate_u24lambda_u2413.addView(vanView, $this$onCreate_u24lambda_u2423);
        TextView $this$onCreate_u24lambda_u2424 = new TextView(this);
        $this$onCreate_u24lambda_u2424.setTextSize(28.0f);
        $this$onCreate_u24lambda_u2424.setTypeface(Typeface.DEFAULT_BOLD);
        $this$onCreate_u24lambda_u2424.setTextColor(-1);
        $this$onCreate_u24lambda_u2424.setGravity(17);
        this.heroText = $this$onCreate_u24lambda_u2424;
        TextView textView2 = this.heroText;
        if (textView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("heroText");
            textView2 = null;
        }
        $this$onCreate_u24lambda_u2413.addView(textView2, new LinearLayout.LayoutParams(-1, -2));
        TextView $this$onCreate_u24lambda_u2425 = new TextView(this);
        $this$onCreate_u24lambda_u2425.setTextSize(13.0f);
        $this$onCreate_u24lambda_u2425.setTextColor(Color.rgb(139, 147, 161));
        $this$onCreate_u24lambda_u2425.setGravity(17);
        $this$onCreate_u24lambda_u2425.setPadding(0, onCreate$dp(d, 2), 0, 0);
        this.carLine = $this$onCreate_u24lambda_u2425;
        TextView textView3 = this.carLine;
        if (textView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("carLine");
            textView3 = null;
        }
        $this$onCreate_u24lambda_u2413.addView(textView3, new LinearLayout.LayoutParams(-1, -2));
        final TextView $this$onCreate_u24lambda_u2427 = new TextView(this);
        $this$onCreate_u24lambda_u2427.setTextSize(13.0f);
        $this$onCreate_u24lambda_u2427.setGravity(17);
        $this$onCreate_u24lambda_u2427.setPadding(onCreate$dp(d, 14), onCreate$dp(d, 7), onCreate$dp(d, 14), onCreate$dp(d, 7));
        $this$onCreate_u24lambda_u2427.setVisibility(8);
        GradientDrawable $this$onCreate_u24lambda_u2427_u24lambda_u2426 = new GradientDrawable();
        $this$onCreate_u24lambda_u2427_u24lambda_u2426.setCornerRadius(onCreate$dp(d, 16));
        $this$onCreate_u24lambda_u2427_u24lambda_u2426.setColor(Color.argb(18, 255, 255, 255));
        $this$onCreate_u24lambda_u2427.setBackground($this$onCreate_u24lambda_u2427_u24lambda_u2426);
        $this$onCreate_u24lambda_u2427.addTextChangedListener(new TextWatcher() { // from class: com.starpilot.starlock.MainActivity$onCreate$8$2
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence t, int a, int b2, int c) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence t, int a, int b2, int c) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable e) {
                Editable editable = e;
                $this$onCreate_u24lambda_u2427.setVisibility(editable == null || editable.length() == 0 ? 8 : 0);
            }
        });
        this.status = $this$onCreate_u24lambda_u2427;
        TextView textView4 = this.status;
        if (textView4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("status");
            textView4 = null;
        }
        LinearLayout.LayoutParams $this$onCreate_u24lambda_u2428 = new LinearLayout.LayoutParams(-2, -2);
        $this$onCreate_u24lambda_u2428.gravity = 1;
        $this$onCreate_u24lambda_u2428.topMargin = onCreate$dp(d, 14);
        Unit unit3 = Unit.INSTANCE;
        $this$onCreate_u24lambda_u2413.addView(textView4, $this$onCreate_u24lambda_u2428);
        $this$onCreate_u24lambda_u2413.addView(new View(this), new LinearLayout.LayoutParams(1, 0, 1.0f));
        LinearLayout $this$onCreate_u24lambda_u2430 = new LinearLayout(this);
        $this$onCreate_u24lambda_u2430.setOrientation(0);
        $this$onCreate_u24lambda_u2430.setGravity(17);
        $this$onCreate_u24lambda_u2430.setPadding(onCreate$dp(d, 6), onCreate$dp(d, 20), onCreate$dp(d, 6), onCreate$dp(d, 14));
        GradientDrawable $this$onCreate_u24lambda_u2430_u24lambda_u2429 = new GradientDrawable();
        $this$onCreate_u24lambda_u2430_u24lambda_u2429.setCornerRadius(onCreate$dp(d, 34));
        $this$onCreate_u24lambda_u2430_u24lambda_u2429.setColor(Color.argb(13, 255, 255, 255));
        $this$onCreate_u24lambda_u2430_u24lambda_u2429.setStroke(1, Color.argb(20, 255, 255, 255));
        $this$onCreate_u24lambda_u2430.setBackground($this$onCreate_u24lambda_u2430_u24lambda_u2429);
        LinearLayout dock = $this$onCreate_u24lambda_u2430;
        this.lockBg = onCreate$keyBg(d);
        this.unlockBg = onCreate$keyBg(d);
        this.windowBg = onCreate$keyBg(d);
        int i = R.drawable.ic_lock;
        GradientDrawable gradientDrawable4 = this.lockBg;
        if (gradientDrawable4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("lockBg");
            gradientDrawable = null;
        } else {
            gradientDrawable = gradientDrawable4;
        }
        Pair<LinearLayout, ImageButton> onCreate$key = onCreate$key(this, d, i, "LOCK", null, gradientDrawable);
        LinearLayout lockCol = onCreate$key.component1();
        ImageButton lockBtn = onCreate$key.component2();
        int i2 = R.drawable.ic_unlock;
        GradientDrawable gradientDrawable5 = this.unlockBg;
        if (gradientDrawable5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("unlockBg");
            gradientDrawable2 = null;
        } else {
            gradientDrawable2 = gradientDrawable5;
        }
        Pair<LinearLayout, ImageButton> onCreate$key2 = onCreate$key(this, d, i2, "UNLOCK", null, gradientDrawable2);
        LinearLayout unlockCol = onCreate$key2.component1();
        ImageButton unlockBtn = onCreate$key2.component2();
        int i3 = R.drawable.ic_window;
        GradientDrawable gradientDrawable6 = this.windowBg;
        if (gradientDrawable6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("windowBg");
            gradientDrawable3 = null;
        } else {
            gradientDrawable3 = gradientDrawable6;
        }
        Pair<LinearLayout, ImageButton> onCreate$key3 = onCreate$key(this, d, i3, "WINDOWS", "hold", gradientDrawable3);
        LinearLayout winCol = onCreate$key3.component1();
        ImageButton winBtn = onCreate$key3.component2();
        onCreate$tap(this, lockBtn, "lock");
        onCreate$tap(this, unlockBtn, "unlock");
        winBtn.setContentDescription("Windows: hold to close the windows and sunroof (car off)");
        winBtn.setOnTouchListener(new View.OnTouchListener() { // from class: com.starpilot.starlock.MainActivity$$ExternalSyntheticLambda24
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                boolean onCreate$lambda$38;
                onCreate$lambda$38 = MainActivity.onCreate$lambda$38(MainActivity.this, view, motionEvent);
                return onCreate$lambda$38;
            }
        });
        for (LinearLayout c : CollectionsKt.listOf(new LinearLayout[]{lockCol, unlockCol, winCol})) {
            dock.addView(c, new LinearLayout.LayoutParams(0, -2, 1.0f));
        }
        LinearLayout.LayoutParams $this$onCreate_u24lambda_u2439 = new LinearLayout.LayoutParams(-1, -2);
        $this$onCreate_u24lambda_u2439.topMargin = onCreate$dp(d, 18);
        Unit unit4 = Unit.INSTANCE;
        $this$onCreate_u24lambda_u2413.addView(dock, $this$onCreate_u24lambda_u2439);
        LinearLayout $this$onCreate_u24lambda_u2441 = new LinearLayout(this);
        $this$onCreate_u24lambda_u2441.setOrientation(1);
        $this$onCreate_u24lambda_u2441.setPadding(onCreate$dp(d, 18), onCreate$dp(d, 18), onCreate$dp(d, 18), onCreate$dp(d, 18));
        $this$onCreate_u24lambda_u2441.setVisibility(8);
        GradientDrawable $this$onCreate_u24lambda_u2441_u24lambda_u2440 = new GradientDrawable();
        $this$onCreate_u24lambda_u2441_u24lambda_u2440.setCornerRadius(onCreate$dp(d, 22));
        $this$onCreate_u24lambda_u2441_u24lambda_u2440.setColor(Color.argb(28, 255, 255, 255));
        $this$onCreate_u24lambda_u2441.setBackground($this$onCreate_u24lambda_u2441_u24lambda_u2440);
        this.settingsPanel = $this$onCreate_u24lambda_u2441;
        TextView $this$onCreate_u24lambda_u2442 = new TextView(this);
        $this$onCreate_u24lambda_u2442.setText("Settings");
        $this$onCreate_u24lambda_u2442.setTextSize(20.0f);
        $this$onCreate_u24lambda_u2442.setTypeface(Typeface.DEFAULT_BOLD);
        $this$onCreate_u24lambda_u2442.setTextColor(-1);
        $this$onCreate_u24lambda_u2442.setPadding(0, 0, 0, onCreate$dp(d, 10));
        $this$onCreate_u24lambda_u2441.addView($this$onCreate_u24lambda_u2442);
        LinearLayout.LayoutParams $this$onCreate_u24lambda_u2443 = new LinearLayout.LayoutParams(-1, -2);
        $this$onCreate_u24lambda_u2443.topMargin = onCreate$dp(d, 28);
        Unit unit5 = Unit.INSTANCE;
        page.addView($this$onCreate_u24lambda_u2441, $this$onCreate_u24lambda_u2443);
        int rowText = Color.rgb(221, 225, 230);
        int muted = Color.rgb(140, 150, 165);
        List openers = new ArrayList();
        final List sounds = CollectionsKt.listOf(new Pair[]{TuplesKt.to("horn", "Horn"), TuplesKt.to("buzzer", "Beeper"), TuplesKt.to("none", "Silent")});
        List sounds2 = sounds;
        LinearLayout answer = onCreate$group(this, $this$onCreate_u24lambda_u2441, openers, muted, d, rowText, "Answer-back", new Function0() { // from class: com.starpilot.starlock.MainActivity$$ExternalSyntheticLambda36
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                String onCreate$lambda$61;
                onCreate$lambda$61 = MainActivity.onCreate$lambda$61(sounds, MainActivity.this);
                return onCreate$lambda$61;
            }
        });
        RadioGroup $this$onCreate_u24lambda_u2464 = new RadioGroup(this);
        int i4 = 0;
        $this$onCreate_u24lambda_u2464.setOrientation(0);
        String cur = sound();
        int i5 = 0;
        for (Iterator it = sounds2.iterator(); it.hasNext(); it = it) {
            int i6 = i5;
            i5++;
            Pair pair = (Pair) it.next();
            RadioButton $this$onCreate_u24lambda_u2464_u24lambda_u2462 = new RadioButton(this);
            $this$onCreate_u24lambda_u2464_u24lambda_u2462.setId(i6 + 100);
            $this$onCreate_u24lambda_u2464_u24lambda_u2462.setText((CharSequence) pair.getSecond());
            $this$onCreate_u24lambda_u2464_u24lambda_u2462.setTextSize(16.0f);
            $this$onCreate_u24lambda_u2464_u24lambda_u2462.setTextColor(-1);
            $this$onCreate_u24lambda_u2464_u24lambda_u2462.setChecked(Intrinsics.areEqual(pair.getFirst(), cur));
            $this$onCreate_u24lambda_u2464.addView($this$onCreate_u24lambda_u2464_u24lambda_u2462);
            i4 = i4;
        }
        $this$onCreate_u24lambda_u2464.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() { // from class: com.starpilot.starlock.MainActivity$$ExternalSyntheticLambda37
            @Override // android.widget.RadioGroup.OnCheckedChangeListener
            public final void onCheckedChanged(RadioGroup radioGroup, int i7) {
                MainActivity.onCreate$lambda$64$lambda$63(MainActivity.this, sounds, radioGroup, i7);
            }
        });
        answer.addView($this$onCreate_u24lambda_u2464);
        LinearLayout horn = onCreate$group(this, $this$onCreate_u24lambda_u2441, openers, muted, d, rowText, "Horn pattern", new Function0() { // from class: com.starpilot.starlock.MainActivity$$ExternalSyntheticLambda38
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                String onCreate$lambda$66;
                onCreate$lambda$66 = MainActivity.onCreate$lambda$66(MainActivity.this);
                return onCreate$lambda$66;
            }
        });
        List counts = CollectionsKt.toList(new IntRange(1, 5));
        List lengths = CollectionsKt.listOf(new Integer[]{30, 50, 80, 100, 150, Integer.valueOf(200), Integer.valueOf(300), 400, 500, 700, 1000});
        List gaps = CollectionsKt.listOf(new Integer[]{50, 100, 170, Integer.valueOf(200), Integer.valueOf(300), 400, 500, 700, 1000});
        List patternRows = new ArrayList();
        LinearLayout $this$onCreate_u24lambda_u2475 = new LinearLayout(this);
        int muted2 = 0;
        for (String h : CollectionsKt.listOf(new String[]{"Beeps", "Each", "Gap"})) {
            TextView $this$onCreate_u24lambda_u2475_u24lambda_u2474 = new TextView(this);
            $this$onCreate_u24lambda_u2475_u24lambda_u2474.setText(h);
            $this$onCreate_u24lambda_u2475_u24lambda_u2474.setTextSize(12.0f);
            $this$onCreate_u24lambda_u2475_u24lambda_u2474.setGravity(17);
            $this$onCreate_u24lambda_u2475_u24lambda_u2474.setTextColor(muted);
            $this$onCreate_u24lambda_u2475.addView($this$onCreate_u24lambda_u2475_u24lambda_u2474, onCreate$third());
            muted2 = muted2;
        }
        int muted3 = muted;
        horn.addView($this$onCreate_u24lambda_u2475);
        Iterator it2 = CollectionsKt.listOf(new String[]{"lock", "unlock"}).iterator();
        while (it2.hasNext()) {
            final String cmd = (String) it2.next();
            TextView $this$onCreate_u24lambda_u2476 = new TextView(this);
            $this$onCreate_u24lambda_u2476.setText(Intrinsics.areEqual(cmd, "lock") ? "Lock" : "Unlock");
            $this$onCreate_u24lambda_u2476.setTextSize(14.0f);
            $this$onCreate_u24lambda_u2476.setTextColor(rowText);
            Iterator it3 = it2;
            $this$onCreate_u24lambda_u2476.setPadding(0, onCreate$dp(d, 8), 0, onCreate$dp(d, 2));
            horn.addView($this$onCreate_u24lambda_u2476);
            LinearLayout $this$onCreate_u24lambda_u2486 = new LinearLayout(this);
            $this$onCreate_u24lambda_u2486.setOrientation(0);
            List patternRows2 = patternRows;
            $this$onCreate_u24lambda_u2486.addView(onCreate$stepper(this, patternRows, d, counts, new Function0() { // from class: com.starpilot.starlock.MainActivity$$ExternalSyntheticLambda39
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    int onCreate$lambda$86$lambda$77;
                    onCreate$lambda$86$lambda$77 = MainActivity.onCreate$lambda$86$lambda$77(MainActivity.this, cmd);
                    return Integer.valueOf(onCreate$lambda$86$lambda$77);
                }
            }, new Function1() { // from class: com.starpilot.starlock.MainActivity$$ExternalSyntheticLambda40
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    Unit onCreate$lambda$86$lambda$78;
                    onCreate$lambda$86$lambda$78 = MainActivity.onCreate$lambda$86$lambda$78(MainActivity.this, cmd, ((Integer) obj).intValue());
                    return onCreate$lambda$86$lambda$78;
                }
            }, new Function1() { // from class: com.starpilot.starlock.MainActivity$$ExternalSyntheticLambda41
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    String valueOf;
                    valueOf = String.valueOf(((Integer) obj).intValue());
                    return valueOf;
                }
            }), onCreate$third());
            $this$onCreate_u24lambda_u2486.addView(onCreate$stepper(this, patternRows2, d, lengths, new Function0() { // from class: com.starpilot.starlock.MainActivity$$ExternalSyntheticLambda42
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    int onCreate$lambda$86$lambda$80;
                    onCreate$lambda$86$lambda$80 = MainActivity.onCreate$lambda$86$lambda$80(MainActivity.this, cmd);
                    return Integer.valueOf(onCreate$lambda$86$lambda$80);
                }
            }, new Function1() { // from class: com.starpilot.starlock.MainActivity$$ExternalSyntheticLambda43
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    Unit onCreate$lambda$86$lambda$81;
                    onCreate$lambda$86$lambda$81 = MainActivity.onCreate$lambda$86$lambda$81(MainActivity.this, cmd, ((Integer) obj).intValue());
                    return onCreate$lambda$86$lambda$81;
                }
            }, new Function1() { // from class: com.starpilot.starlock.MainActivity$$ExternalSyntheticLambda13
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    String onCreate$lambda$86$lambda$82;
                    onCreate$lambda$86$lambda$82 = MainActivity.onCreate$lambda$86$lambda$82(((Integer) obj).intValue());
                    return onCreate$lambda$86$lambda$82;
                }
            }), onCreate$third());
            $this$onCreate_u24lambda_u2486.addView(onCreate$stepper(this, patternRows2, d, gaps, new Function0() { // from class: com.starpilot.starlock.MainActivity$$ExternalSyntheticLambda14
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    int onCreate$lambda$86$lambda$83;
                    onCreate$lambda$86$lambda$83 = MainActivity.onCreate$lambda$86$lambda$83(MainActivity.this, cmd);
                    return Integer.valueOf(onCreate$lambda$86$lambda$83);
                }
            }, new Function1() { // from class: com.starpilot.starlock.MainActivity$$ExternalSyntheticLambda15
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    Unit onCreate$lambda$86$lambda$84;
                    onCreate$lambda$86$lambda$84 = MainActivity.onCreate$lambda$86$lambda$84(MainActivity.this, cmd, ((Integer) obj).intValue());
                    return onCreate$lambda$86$lambda$84;
                }
            }, new Function1() { // from class: com.starpilot.starlock.MainActivity$$ExternalSyntheticLambda16
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    String onCreate$lambda$86$lambda$85;
                    onCreate$lambda$86$lambda$85 = MainActivity.onCreate$lambda$86$lambda$85(((Integer) obj).intValue());
                    return onCreate$lambda$86$lambda$85;
                }
            }), onCreate$third());
            horn.addView($this$onCreate_u24lambda_u2486);
            muted3 = muted3;
            it2 = it3;
            page = page;

            dock = dock;
            patternRows = patternRows2;
        }
        final List patternRows3 = patternRows;
        LinearLayout page2 = page;
        int muted4 = muted3;
        TextView $this$onCreate_u24lambda_u2487 = new TextView(this);
        $this$onCreate_u24lambda_u2487.setText("Horn only. The car never sounds shorter than about 100 ms.");
        $this$onCreate_u24lambda_u2487.setTextSize(12.0f);
        $this$onCreate_u24lambda_u2487.setTextColor(muted4);
        $this$onCreate_u24lambda_u2487.setPadding(0, onCreate$dp(d, 8), 0, 0);
        horn.addView($this$onCreate_u24lambda_u2487);
        horn.addView(onCreate$soft(this, d, "Reset horn pattern", new Function0() { // from class: com.starpilot.starlock.MainActivity$$ExternalSyntheticLambda17
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit onCreate$lambda$89;
                onCreate$lambda$89 = MainActivity.onCreate$lambda$89(MainActivity.this, patternRows3);
                return onCreate$lambda$89;
            }
        }), onCreate$softParams(d));
        onCreate$row(this, $this$onCreate_u24lambda_u2441, d, rowText, "Auto-lock / auto-unlock", onCreate$chevron(this, muted4), new Function0() { // from class: com.starpilot.starlock.MainActivity$$ExternalSyntheticLambda18
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit onCreate$lambda$90;
                onCreate$lambda$90 = MainActivity.onCreate$lambda$90(MainActivity.this);
                return onCreate$lambda$90;
            }
        });
        onCreate$row(this, $this$onCreate_u24lambda_u2441, d, rowText, "Stay connected in the background", onCreate$styledSwitch(this, KeeperService.Companion.background(this), new Function1() { // from class: com.starpilot.starlock.MainActivity$$ExternalSyntheticLambda19
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit onCreate$lambda$91;
                onCreate$lambda$91 = MainActivity.onCreate$lambda$91(MainActivity.this, ((Boolean) obj).booleanValue());
                return onCreate$lambda$91;
            }
        }), null);
        LinearLayout hot = onCreate$group(this, $this$onCreate_u24lambda_u2441, openers, muted4, d, rowText, "Hotspot on when the comma connects", new Function0() { // from class: com.starpilot.starlock.MainActivity$$ExternalSyntheticLambda20
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                String onCreate$lambda$92;
                onCreate$lambda$92 = MainActivity.onCreate$lambda$92(MainActivity.this);
                return onCreate$lambda$92;
            }
        });
        final TextView $this$onCreate_u24lambda_u2493 = new TextView(this);
        $this$onCreate_u24lambda_u2493.setTextSize(13.0f);
        $this$onCreate_u24lambda_u2493.setTextColor(muted4);
        $this$onCreate_u24lambda_u2493.setPadding(0, onCreate$dp(d, 6), 0, 0);
        this.hotRefresh = new MainActivity$onCreate$22($this$onCreate_u24lambda_u2493, this);
        Switch $this$onCreate_u24lambda_u2495 = new Switch(this);
        $this$onCreate_u24lambda_u2495.setText("Turn the hotspot on");
        $this$onCreate_u24lambda_u2495.setTextSize(15.0f);
        $this$onCreate_u24lambda_u2495.setTextColor(rowText);
        $this$onCreate_u24lambda_u2495.setChecked(Hotspot.INSTANCE.enabled(this));
        $this$onCreate_u24lambda_u2495.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.starpilot.starlock.MainActivity$$ExternalSyntheticLambda21
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                MainActivity.onCreate$lambda$95$lambda$94(MainActivity.this, compoundButton, z);
            }
        });
        hot.addView($this$onCreate_u24lambda_u2495);
        hot.addView($this$onCreate_u24lambda_u2493);
        final LinearLayout $this$onCreate_u24lambda_u24102 = new LinearLayout(this);
        $this$onCreate_u24lambda_u24102.setOrientation(0);
        $this$onCreate_u24lambda_u24102.addView(onCreate$soft(this, d, "Allow settings", new Function0() { // from class: com.starpilot.starlock.MainActivity$$ExternalSyntheticLambda23
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit onCreate$lambda$102$lambda$96;
                onCreate$lambda$102$lambda$96 = MainActivity.onCreate$lambda$102$lambda$96(MainActivity.this);
                return onCreate$lambda$102$lambda$96;
            }
        }), onCreate$softParams(d));
        $this$onCreate_u24lambda_u24102.addView(onCreate$soft(this, d, "Allow Shizuku", new Function0() { // from class: com.starpilot.starlock.MainActivity$$ExternalSyntheticLambda25
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit onCreate$lambda$102$lambda$98;
                onCreate$lambda$102$lambda$98 = MainActivity.onCreate$lambda$102$lambda$98(MainActivity.this, $this$onCreate_u24lambda_u24102);
                return onCreate$lambda$102$lambda$98;
            }
        }), onCreate$softParams(d));
        $this$onCreate_u24lambda_u24102.addView(onCreate$soft(this, d, "Test", new Function0() { // from class: com.starpilot.starlock.MainActivity$$ExternalSyntheticLambda26
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit onCreate$lambda$102$lambda$101;
                onCreate$lambda$102$lambda$101 = MainActivity.onCreate$lambda$102$lambda$101(MainActivity.this, $this$onCreate_u24lambda_u2493);
                return onCreate$lambda$102$lambda$101;
            }
        }), onCreate$softParams(d));
        hot.addView($this$onCreate_u24lambda_u24102);
        try {
            Result.Companion companion = Result.Companion;
            MainActivity $this$onCreate_u24lambda_u24103 = this;
            Shizuku.addRequestPermissionResultListener($this$onCreate_u24lambda_u24103.shizukuListener);
            KtCompat.resultOf(Unit.INSTANCE);
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            KtCompat.resultOf(ResultKt.createFailure(th));
        }
        if (Build.VERSION.SDK_INT >= 33) {
            onCreate$row(this, $this$onCreate_u24lambda_u2441, d, rowText, "Quick-settings tile", onCreate$summary(this, muted4, new Function0() { // from class: com.starpilot.starlock.MainActivity$$ExternalSyntheticLambda27
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    String onCreate$lambda$104;
                    onCreate$lambda$104 = MainActivity.onCreate$lambda$104();
                    return onCreate$lambda$104;
                }
            }), new Function0() { // from class: com.starpilot.starlock.MainActivity$$ExternalSyntheticLambda28
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit onCreate$lambda$106;
                    onCreate$lambda$106 = MainActivity.onCreate$lambda$106(MainActivity.this);
                    return onCreate$lambda$106;
                }
            });
        }
        onCreate$row(this, $this$onCreate_u24lambda_u2441, d, rowText, "Signal test", onCreate$chevron(this, muted4), new Function0() { // from class: com.starpilot.starlock.MainActivity$$ExternalSyntheticLambda29
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit onCreate$lambda$107;
                onCreate$lambda$107 = MainActivity.onCreate$lambda$107(MainActivity.this);
                return onCreate$lambda$107;
            }
        });
        LinearLayout pair2 = onCreate$group(this, $this$onCreate_u24lambda_u2441, openers, muted4, d, rowText, "Pairing", new Function0() { // from class: com.starpilot.starlock.MainActivity$$ExternalSyntheticLambda30
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                String onCreate$lambda$108;
                onCreate$lambda$108 = MainActivity.onCreate$lambda$108(MainActivity.this);
                return onCreate$lambda$108;
            }
        });
        LinearLayout $this$onCreate_u24lambda_u24111 = new LinearLayout(this);
        $this$onCreate_u24lambda_u24111.addView(onCreate$soft(this, d, "Scan the QR code", new Function0() { // from class: com.starpilot.starlock.MainActivity$$ExternalSyntheticLambda31
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit onCreate$lambda$111$lambda$109;
                onCreate$lambda$111$lambda$109 = MainActivity.onCreate$lambda$111$lambda$109(MainActivity.this);
                return onCreate$lambda$111$lambda$109;
            }
        }), onCreate$softParams(d));
        $this$onCreate_u24lambda_u24111.addView(onCreate$soft(this, d, "Paste the link", new Function0() { // from class: com.starpilot.starlock.MainActivity$$ExternalSyntheticLambda32
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit onCreate$lambda$111$lambda$110;
                onCreate$lambda$111$lambda$110 = MainActivity.onCreate$lambda$111$lambda$110(MainActivity.this);
                return onCreate$lambda$111$lambda$110;
            }
        }), onCreate$softParams(d));
        pair2.addView($this$onCreate_u24lambda_u24111);
        LinearLayout logBox = onCreate$group(this, $this$onCreate_u24lambda_u2441, openers, muted4, d, rowText, "Log", new Function0() { // from class: com.starpilot.starlock.MainActivity$$ExternalSyntheticLambda34
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                String onCreate$lambda$112;
                onCreate$lambda$112 = MainActivity.onCreate$lambda$112();
                return onCreate$lambda$112;
            }
        });
        TextView $this$onCreate_u24lambda_u24113 = new TextView(this);
        $this$onCreate_u24lambda_u24113.setTextSize(12.0f);
        $this$onCreate_u24lambda_u24113.setTextColor(muted4);
        $this$onCreate_u24lambda_u24113.setPadding(0, onCreate$dp(d, 10), 0, 0);
        $this$onCreate_u24lambda_u24113.setTypeface(Typeface.MONOSPACE);
        this.logView = $this$onCreate_u24lambda_u24113;
        logBox.addView(onCreate$soft(this, d, "Copy log", new Function0() { // from class: com.starpilot.starlock.MainActivity$$ExternalSyntheticLambda35
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit onCreate$lambda$114;
                onCreate$lambda$114 = MainActivity.onCreate$lambda$114(MainActivity.this);
                return onCreate$lambda$114;
            }
        }), onCreate$softParams(d));
        TextView textView5 = this.logView;
        if (textView5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("logView");
            textView5 = null;
        }
        textView5.setTextIsSelectable(true);
        TextView textView6 = this.logView;
        if (textView6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("logView");
            textView6 = null;
        }
        logBox.addView(textView6);
        TextView $this$onCreate_u24lambda_u24116 = new TextView(this);
        try {
            Result.Companion companion3 = Result.Companion;
            m61constructorimpl = KtCompat.resultOf(getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
        } catch (Throwable th2) {
            Result.Companion companion4 = Result.Companion;
            m61constructorimpl = KtCompat.resultOf(ResultKt.createFailure(th2));
        }
        if (KtCompat.isFailure(m61constructorimpl)) {
            m61constructorimpl = "";
        }
        $this$onCreate_u24lambda_u24116.setText("StarLock " + m61constructorimpl);
        $this$onCreate_u24lambda_u24116.setTextSize(12.0f);
        $this$onCreate_u24lambda_u24116.setTextColor(Color.rgb(100, 108, 120));
        $this$onCreate_u24lambda_u24116.setGravity(17);
        $this$onCreate_u24lambda_u24116.setPadding(0, onCreate$dp(d, 12), 0, 0);
        $this$onCreate_u24lambda_u2441.addView($this$onCreate_u24lambda_u24116);
        ScrollView $this$onCreate_u24lambda_u24117 = new ScrollView(this);
        $this$onCreate_u24lambda_u24117.setFillViewport(true);
        $this$onCreate_u24lambda_u24117.setBackground(new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[]{Color.rgb(28, 33, 41), Color.rgb(9, 11, 14)}));
        $this$onCreate_u24lambda_u24117.addView(page2);
        this.scroller = $this$onCreate_u24lambda_u24117;
        ScrollView scrollView = this.scroller;
        if (scrollView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("scroller");
            scrollView = null;
        }
        setContentView(scrollView);
        showHero$default(this, null, 1, null);
        askBluetooth();
        showState();
        handle(getIntent());
    }

    private static final int onCreate$dp(float d, int v) {
        return (int) (v * d);
    }

    private static final GradientDrawable onCreate$keyBg(float d) {
        GradientDrawable $this$onCreate_u24keyBg_u24lambda_u2431 = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[]{Color.rgb(45, 51, 61), Color.rgb(27, 31, 38)});
        $this$onCreate_u24keyBg_u24lambda_u2431.setShape(1);
        $this$onCreate_u24keyBg_u24lambda_u2431.setStroke(onCreate$dp(d, 1), Color.rgb(58, 65, 77));
        return $this$onCreate_u24keyBg_u24lambda_u2431;
    }

    private static final Pair<LinearLayout, ImageButton> onCreate$key(MainActivity this$0, float d, int icon, String label, String hint, GradientDrawable bg) {
        ImageButton b = new ImageButton(this$0);
        b.setImageResource(icon);
        b.setImageTintList(ColorStateList.valueOf(-1));
        b.setScaleType(ImageView.ScaleType.FIT_CENTER);
        b.setPadding(onCreate$dp(d, 24), onCreate$dp(d, 24), onCreate$dp(d, 24), onCreate$dp(d, 24));
        b.setBackground(bg);
        b.setElevation(onCreate$dp(d, 6));
        b.setContentDescription(label);
        LinearLayout col = new LinearLayout(this$0);
        col.setOrientation(1);
        col.setGravity(1);
        col.addView(b, new LinearLayout.LayoutParams(onCreate$dp(d, 80), onCreate$dp(d, 80)));
        TextView $this$onCreate_u24key_u24lambda_u2435_u24lambda_u2433 = new TextView(this$0);
        $this$onCreate_u24key_u24lambda_u2435_u24lambda_u2433.setText(label);
        $this$onCreate_u24key_u24lambda_u2435_u24lambda_u2433.setTextSize(12.0f);
        $this$onCreate_u24key_u24lambda_u2435_u24lambda_u2433.setLetterSpacing(0.12f);
        $this$onCreate_u24key_u24lambda_u2435_u24lambda_u2433.setTypeface(Typeface.DEFAULT_BOLD);
        $this$onCreate_u24key_u24lambda_u2435_u24lambda_u2433.setTextColor(Color.rgb(201, 207, 216));
        $this$onCreate_u24key_u24lambda_u2435_u24lambda_u2433.setGravity(17);
        $this$onCreate_u24key_u24lambda_u2435_u24lambda_u2433.setPadding(0, onCreate$dp(d, 9), 0, 0);
        col.addView($this$onCreate_u24key_u24lambda_u2435_u24lambda_u2433);
        TextView $this$onCreate_u24key_u24lambda_u2435_u24lambda_u2434 = new TextView(this$0);
        $this$onCreate_u24key_u24lambda_u2435_u24lambda_u2434.setText(hint != null ? hint : " ");
        $this$onCreate_u24key_u24lambda_u2435_u24lambda_u2434.setTextSize(10.0f);
        $this$onCreate_u24key_u24lambda_u2435_u24lambda_u2434.setTextColor(Color.rgb(107, 115, 128));
        $this$onCreate_u24key_u24lambda_u2435_u24lambda_u2434.setGravity(17);
        col.addView($this$onCreate_u24key_u24lambda_u2435_u24lambda_u2434);
        return TuplesKt.to(col, b);
    }

    private static final void onCreate$tap(final MainActivity this$0, View b, final String cmd) {
        b.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starlock.MainActivity$$ExternalSyntheticLambda57
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.onCreate$tap$lambda$37(this$0, cmd, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$tap$lambda$37(MainActivity this$0, String $cmd, final View v) {
        v.performHapticFeedback(1);
        v.animate().scaleX(0.9f).scaleY(0.9f).setDuration(70L).withEndAction(new Runnable() { // from class: com.starpilot.starlock.MainActivity$$ExternalSyntheticLambda51
            @Override // java.lang.Runnable
            public final void run() {
                MainActivity.onCreate$tap$lambda$37$lambda$36(v);
            }
        }).start();
        this$0.run($cmd);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$tap$lambda$37$lambda$36(View $v) {
        $v.animate().scaleX(1.0f).scaleY(1.0f).setDuration(120L).start();
    }

    /* VF: from Vineflower (jadx: Code restructure failed;incorrect) */
    public static final boolean onCreate$lambda$38(MainActivity var0, View var1, MotionEvent var2) {
        switch (var2.getActionMasked()) {
            case 0:
                var1.performHapticFeedback(1);
                var0.startHold();
                break;
            case 1:
            case 3:
                var0.fingerDown = false;
            case 2:
        }

        return true;
    }

    private static final void onCreate$divider(LinearLayout col, MainActivity this$0) {
        View $this$onCreate_u24divider_u24lambda_u2444 = new View(this$0);
        $this$onCreate_u24divider_u24lambda_u2444.setBackgroundColor(Color.argb(22, 255, 255, 255));
        col.addView($this$onCreate_u24divider_u24lambda_u2444, new LinearLayout.LayoutParams(-1, 1));
    }

    private static final LinearLayout onCreate$row(MainActivity this$0, LinearLayout col, float d, int rowText, String title, View right, final Function0<Unit> function0) {
        LinearLayout $this$onCreate_u24row_u24lambda_u2447 = new LinearLayout(this$0);
        $this$onCreate_u24row_u24lambda_u2447.setOrientation(0);
        $this$onCreate_u24row_u24lambda_u2447.setGravity(16);
        $this$onCreate_u24row_u24lambda_u2447.setPadding(0, onCreate$dp(d, 12), 0, onCreate$dp(d, 12));
        TextView $this$onCreate_u24row_u24lambda_u2447_u24lambda_u2445 = new TextView(this$0);
        $this$onCreate_u24row_u24lambda_u2447_u24lambda_u2445.setText(title);
        $this$onCreate_u24row_u24lambda_u2447_u24lambda_u2445.setTextSize(16.0f);
        $this$onCreate_u24row_u24lambda_u2447_u24lambda_u2445.setTextColor(rowText);
        $this$onCreate_u24row_u24lambda_u2447.addView($this$onCreate_u24row_u24lambda_u2447_u24lambda_u2445, new LinearLayout.LayoutParams(0, -2, 1.0f));
        if (right != null) {
            $this$onCreate_u24row_u24lambda_u2447.addView(right);
        }
        if (function0 != null) {
            $this$onCreate_u24row_u24lambda_u2447.setClickable(true);
            $this$onCreate_u24row_u24lambda_u2447.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starlock.MainActivity$$ExternalSyntheticLambda56
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    function0.invoke();
                }
            });
            $this$onCreate_u24row_u24lambda_u2447.setBackground(new RippleDrawable(ColorStateList.valueOf(Color.argb(40, 255, 255, 255)), null, new ColorDrawable(-1)));
        }
        col.addView($this$onCreate_u24row_u24lambda_u2447);
        onCreate$divider(col, this$0);
        return $this$onCreate_u24row_u24lambda_u2447;
    }

    private static final TextView onCreate$summary(MainActivity this$0, int muted, Function0<String> function0) {
        TextView $this$onCreate_u24summary_u24lambda_u2448 = new TextView(this$0);
        $this$onCreate_u24summary_u24lambda_u2448.setTextSize(13.0f);
        $this$onCreate_u24summary_u24lambda_u2448.setTextColor(muted);
        $this$onCreate_u24summary_u24lambda_u2448.setText(function0.invoke());
        return $this$onCreate_u24summary_u24lambda_u2448;
    }

    private static final LinearLayout onCreate$group(MainActivity this$0, LinearLayout col, final List<Function0<Unit>> list, int muted, float d, int rowText, String title, final Function0<String> function0) {
        final TextView s = onCreate$summary(this$0, muted, function0);
        final LinearLayout $this$onCreate_u24group_u24lambda_u2449 = new LinearLayout(this$0);
        $this$onCreate_u24group_u24lambda_u2449.setOrientation(1);
        $this$onCreate_u24group_u24lambda_u2449.setVisibility(8);
        $this$onCreate_u24group_u24lambda_u2449.setPadding(0, 0, 0, onCreate$dp(d, 12));
        onCreate$row(this$0, col, d, rowText, title, s, new Function0() { // from class: com.starpilot.starlock.MainActivity$$ExternalSyntheticLambda8
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit onCreate$group$lambda$51;
                onCreate$group$lambda$51 = MainActivity.onCreate$group$lambda$51($this$onCreate_u24group_u24lambda_u2449, list, s, function0);
                return onCreate$group$lambda$51;
            }
        });
        col.removeViewAt(col.getChildCount() - 1);
        col.addView($this$onCreate_u24group_u24lambda_u2449);
        onCreate$divider(col, this$0);
        list.add(new Function0() { // from class: com.starpilot.starlock.MainActivity$$ExternalSyntheticLambda9
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit onCreate$group$lambda$52;
                onCreate$group$lambda$52 = MainActivity.onCreate$group$lambda$52($this$onCreate_u24group_u24lambda_u2449, s, function0);
                return onCreate$group$lambda$52;
            }
        });
        return $this$onCreate_u24group_u24lambda_u2449;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onCreate$group$lambda$51(LinearLayout $body, List $openers, TextView $s, Function0 $sum) {
        boolean open = $body.getVisibility() != 0;
        List $this$forEach$iv = $openers;
        for (Object element$iv : $this$forEach$iv) {
            Function0 it = (Function0) element$iv;
            it.invoke();
        }
        $body.setVisibility(open ? 0 : 8);
        $s.setText((CharSequence) $sum.invoke());
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onCreate$group$lambda$52(LinearLayout $body, TextView $s, Function0 $sum) {
        $body.setVisibility(8);
        $s.setText((CharSequence) $sum.invoke());
        return Unit.INSTANCE;
    }

    private static final TextView onCreate$chevron(MainActivity this$0, int muted) {
        TextView $this$onCreate_u24chevron_u24lambda_u2453 = new TextView(this$0);
        $this$onCreate_u24chevron_u24lambda_u2453.setText("›");
        $this$onCreate_u24chevron_u24lambda_u2453.setTextSize(22.0f);
        $this$onCreate_u24chevron_u24lambda_u2453.setTextColor(muted);
        return $this$onCreate_u24chevron_u24lambda_u2453;
    }

    private static final Switch onCreate$styledSwitch(MainActivity this$0, boolean on, final Function1<? super Boolean, Unit> function1) {
        Switch $this$onCreate_u24styledSwitch_u24lambda_u2455 = new Switch(this$0);
        $this$onCreate_u24styledSwitch_u24lambda_u2455.setChecked(on);
        $this$onCreate_u24styledSwitch_u24lambda_u2455.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.starpilot.starlock.MainActivity$$ExternalSyntheticLambda22
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                MainActivity.onCreate$styledSwitch$lambda$55$lambda$54(function1, compoundButton, z);
            }
        });
        return $this$onCreate_u24styledSwitch_u24lambda_u2455;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$styledSwitch$lambda$55$lambda$54(Function1 $change, CompoundButton compoundButton, boolean v) {
        $change.invoke(Boolean.valueOf(v));
    }

    private static final Button onCreate$soft(MainActivity this$0, float d, String t, final Function0<Unit> function0) {
        Button $this$onCreate_u24soft_u24lambda_u2458 = new Button(this$0);
        $this$onCreate_u24soft_u24lambda_u2458.setText(t);
        $this$onCreate_u24soft_u24lambda_u2458.setAllCaps(false);
        $this$onCreate_u24soft_u24lambda_u2458.setTextSize(14.0f);
        $this$onCreate_u24soft_u24lambda_u2458.setTextColor(-1);
        GradientDrawable $this$onCreate_u24soft_u24lambda_u2458_u24lambda_u2456 = new GradientDrawable();
        $this$onCreate_u24soft_u24lambda_u2458_u24lambda_u2456.setCornerRadius(onCreate$dp(d, 14));
        $this$onCreate_u24soft_u24lambda_u2458_u24lambda_u2456.setColor(Color.argb(38, 255, 255, 255));
        $this$onCreate_u24soft_u24lambda_u2458.setBackground($this$onCreate_u24soft_u24lambda_u2458_u24lambda_u2456);
        $this$onCreate_u24soft_u24lambda_u2458.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starlock.MainActivity$$ExternalSyntheticLambda46
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                function0.invoke();
            }
        });
        return $this$onCreate_u24soft_u24lambda_u2458;
    }

    private static final LinearLayout.LayoutParams onCreate$softParams(float d) {
        LinearLayout.LayoutParams $this$onCreate_u24softParams_u24lambda_u2459 = new LinearLayout.LayoutParams(-2, onCreate$dp(d, 40));
        $this$onCreate_u24softParams_u24lambda_u2459.rightMargin = onCreate$dp(d, 8);
        $this$onCreate_u24softParams_u24lambda_u2459.topMargin = onCreate$dp(d, 6);
        return $this$onCreate_u24softParams_u24lambda_u2459;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String onCreate$lambda$61(List $sounds, MainActivity this$0) {
        List $this$first$iv = $sounds;
        for (Object element$iv : $this$first$iv) {
            Pair it = (Pair) element$iv;
            if (Intrinsics.areEqual(it.getFirst(), this$0.sound())) {
                return (String) ((Pair) element$iv).getSecond();
            }
        }
        throw new NoSuchElementException("Collection contains no element matching the predicate.");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$64$lambda$63(MainActivity this$0, List $sounds, RadioGroup radioGroup, int id) {
        this$0.getPrefs().edit().putString("sound", (String) ((Pair) $sounds.get(id - 100)).getFirst()).apply();
        AutoEvents.INSTANCE.syncSound(this$0);
    }

    private static final String onCreate$patSum(MainActivity this$0, String c) {
        Door.Pattern it = Door.INSTANCE.pattern(this$0, c);
        return it.getN() + "×" + it.getOnMs();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String onCreate$lambda$66(MainActivity this$0) {
        return "Lock " + onCreate$patSum(this$0, "lock") + " · Unlock " + onCreate$patSum(this$0, "unlock");
    }

    private static final LinearLayout onCreate$stepper(MainActivity this$0, List<Function0<Unit>> list, float d, List<Integer> list2, Function0<Integer> function0, Function1<? super Integer, Unit> function1, Function1<? super Integer, String> function12) {
        TextView $this$onCreate_u24stepper_u24lambda_u2467 = new TextView(this$0);
        $this$onCreate_u24stepper_u24lambda_u2467.setTextSize(15.0f);
        $this$onCreate_u24stepper_u24lambda_u2467.setTextColor(-1);
        $this$onCreate_u24stepper_u24lambda_u2467.setGravity(17);
        $this$onCreate_u24stepper_u24lambda_u2467.setMaxLines(1);
        list.add(new MainActivity$onCreate$stepper$1($this$onCreate_u24stepper_u24lambda_u2467, function12, function0));
        onCreate$stepper$refresh($this$onCreate_u24stepper_u24lambda_u2467, function12, function0);
        LinearLayout $this$onCreate_u24stepper_u24lambda_u2473 = new LinearLayout(this$0);
        $this$onCreate_u24stepper_u24lambda_u2473.setOrientation(0);
        $this$onCreate_u24stepper_u24lambda_u2473.setGravity(16);
        $this$onCreate_u24stepper_u24lambda_u2473.addView(onCreate$stepper$btn(this$0, function0, list2, function1, $this$onCreate_u24stepper_u24lambda_u2467, function12, "−", -1), new LinearLayout.LayoutParams(onCreate$dp(d, 32), onCreate$dp(d, 32)));
        $this$onCreate_u24stepper_u24lambda_u2473.addView($this$onCreate_u24stepper_u24lambda_u2467, new LinearLayout.LayoutParams(0, -2, 1.0f));
        $this$onCreate_u24stepper_u24lambda_u2473.addView(onCreate$stepper$btn(this$0, function0, list2, function1, $this$onCreate_u24stepper_u24lambda_u2467, function12, "+", 1), new LinearLayout.LayoutParams(onCreate$dp(d, 32), onCreate$dp(d, 32)));
        return $this$onCreate_u24stepper_u24lambda_u2473;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$stepper$refresh(TextView v, Function1<? super Integer, String> function1, Function0<Integer> function0) {
        v.setText(function1.invoke(function0.invoke()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$stepper$move(Function0<Integer> function0, List<Integer> list, Function1<? super Integer, Unit> function1, TextView v, Function1<? super Integer, String> function12, int d) {
        Integer num;
        Object first;
        int next;
        int cur = function0.invoke().intValue();
        Object obj = null;
        if (d > 0) {
            List<Integer> $this$firstOrNull$iv = list;
            Iterator it = $this$firstOrNull$iv.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Object element$iv = it.next();
                int it2 = ((Number) element$iv).intValue();
                int it3 = it2 > cur ? 1 : 0;
                if (it3 != 0) {
                    obj = element$iv;
                    break;
                }
            }
            num = (Integer) obj;
            if (num == null) {
                first = CollectionsKt.last((List<? extends Object>) list);
                next = ((Number) first).intValue();
            }
            next = num.intValue();
        } else {
            ListIterator iterator$iv = list.listIterator(list.size());
            while (true) {
                if (!iterator$iv.hasPrevious()) {
                    break;
                }
                Object element$iv2 = iterator$iv.previous();
                int it4 = ((Number) element$iv2).intValue();
                int it5 = it4 < cur ? 1 : 0;
                if (it5 != 0) {
                    obj = element$iv2;
                    break;
                }
            }
            num = (Integer) obj;
            if (num == null) {
                first = CollectionsKt.first((List<? extends Object>) list);
                next = ((Number) first).intValue();
            }
            next = num.intValue();
        }
        function1.invoke(Integer.valueOf(next));
        onCreate$stepper$refresh(v, function12, function0);
    }

    private static final Button onCreate$stepper$btn(MainActivity this$0, final Function0<Integer> function0, final List<Integer> list, final Function1<? super Integer, Unit> function1, final TextView v, final Function1<? super Integer, String> function12, String t, final int d) {
        Button $this$onCreate_u24stepper_u24btn_u24lambda_u2472 = new Button(this$0);
        $this$onCreate_u24stepper_u24btn_u24lambda_u2472.setText(t);
        $this$onCreate_u24stepper_u24btn_u24lambda_u2472.setTextSize(18.0f);
        $this$onCreate_u24stepper_u24btn_u24lambda_u2472.setMinWidth(0);
        $this$onCreate_u24stepper_u24btn_u24lambda_u2472.setMinimumWidth(0);
        $this$onCreate_u24stepper_u24btn_u24lambda_u2472.setPadding(0, 0, 0, 0);
        $this$onCreate_u24stepper_u24btn_u24lambda_u2472.setTextColor(-1);
        GradientDrawable $this$onCreate_u24stepper_u24btn_u24lambda_u2472_u24lambda_u2470 = new GradientDrawable();
        $this$onCreate_u24stepper_u24btn_u24lambda_u2472_u24lambda_u2470.setShape(1);
        $this$onCreate_u24stepper_u24btn_u24lambda_u2472_u24lambda_u2470.setColor(Color.argb(38, 255, 255, 255));
        $this$onCreate_u24stepper_u24btn_u24lambda_u2472.setBackground($this$onCreate_u24stepper_u24btn_u24lambda_u2472_u24lambda_u2470);
        $this$onCreate_u24stepper_u24btn_u24lambda_u2472.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starlock.MainActivity$$ExternalSyntheticLambda11
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.onCreate$stepper$move(function0, list, function1, v, function12, d);
            }
        });
        return $this$onCreate_u24stepper_u24btn_u24lambda_u2472;
    }

    private static final LinearLayout.LayoutParams onCreate$third() {
        return new LinearLayout.LayoutParams(0, -2, 1.0f);
    }

    private static final Door.Pattern onCreate$pt(MainActivity this$0, String cmd) {
        return Door.INSTANCE.pattern(this$0, cmd);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int onCreate$lambda$86$lambda$77(MainActivity this$0, String $cmd) {
        return onCreate$pt(this$0, $cmd).getN();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onCreate$lambda$86$lambda$78(MainActivity this$0, String $cmd, int it) {
        Door.INSTANCE.savePattern(this$0, $cmd, Door.Pattern.copy$default(onCreate$pt(this$0, $cmd), it, 0, 0, 6, null));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int onCreate$lambda$86$lambda$80(MainActivity this$0, String $cmd) {
        return onCreate$pt(this$0, $cmd).getOnMs();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onCreate$lambda$86$lambda$81(MainActivity this$0, String $cmd, int it) {
        Door.INSTANCE.savePattern(this$0, $cmd, Door.Pattern.copy$default(onCreate$pt(this$0, $cmd), 0, it, 0, 5, null));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String onCreate$lambda$86$lambda$82(int it) {
        return it + " ms";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int onCreate$lambda$86$lambda$83(MainActivity this$0, String $cmd) {
        return onCreate$pt(this$0, $cmd).getGapMs();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onCreate$lambda$86$lambda$84(MainActivity this$0, String $cmd, int it) {
        Door.INSTANCE.savePattern(this$0, $cmd, Door.Pattern.copy$default(onCreate$pt(this$0, $cmd), 0, 0, it, 3, null));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String onCreate$lambda$86$lambda$85(int it) {
        return it + " ms";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onCreate$lambda$89(MainActivity this$0, List $patternRows) {
        for (String c : CollectionsKt.listOf(new String[]{"lock", "unlock"})) {
            Door.INSTANCE.savePattern(this$0, c, Door.INSTANCE.defaultPattern(c));
        }
        List $this$forEach$iv = $patternRows;
        for (Object element$iv : $this$forEach$iv) {
            Function0 it = (Function0) element$iv;
            it.invoke();
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onCreate$lambda$90(MainActivity this$0) {
        this$0.startActivity(new Intent(this$0, (Class<?>) AutoLockActivity.class));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onCreate$lambda$91(MainActivity this$0, boolean on) {
        this$0.getPrefs().edit().putBoolean("background", on).apply();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String onCreate$lambda$92(MainActivity this$0) {
        return Hotspot.INSTANCE.enabled(this$0) ? "On" : "Off";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$showHot(TextView hotState, MainActivity this$0) {
        String str;
        String str2 = Hotspot.INSTANCE.canWrite(this$0) ? "allowed" : "not allowed";
        String str3 = Hotspot.INSTANCE.shizukuAllowed() ? "allowed" : Hotspot.INSTANCE.shizukuRunning() ? "running, not allowed yet" : "not running";
        Boolean isOn = Hotspot.INSTANCE.isOn(this$0);
        if (Intrinsics.areEqual((Object) isOn, (Object) true)) {
            str = "on";
        } else if (Intrinsics.areEqual((Object) isOn, (Object) false)) {
            str = "off";
        } else {
            if (isOn != null) {
                throw new NoWhenBranchMatchedException();
            }
            str = "?";
        }
        hotState.setText("Modify system settings: " + str2 + "\nShizuku: " + str3 + "\nHotspot now: " + str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$95$lambda$94(MainActivity this$0, CompoundButton compoundButton, boolean on) {
        this$0.getPrefs().edit().putBoolean("hotspot", on).apply();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onCreate$lambda$102$lambda$96(MainActivity this$0) {
        this$0.startActivity(new Intent("android.settings.action.MANAGE_WRITE_SETTINGS", Uri.parse("package:" + this$0.getPackageName())));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onCreate$lambda$102$lambda$98(MainActivity this$0, LinearLayout $this_apply) {
        Object m61constructorimpl;
        if (Hotspot.INSTANCE.shizukuRunning()) {
            try {
                Result.Companion companion = Result.Companion;
                Shizuku.requestPermission(7);
                m61constructorimpl = KtCompat.resultOf(Unit.INSTANCE);
            } catch (Throwable th) {
                Result.Companion companion2 = Result.Companion;
                m61constructorimpl = KtCompat.resultOf(ResultKt.createFailure(th));
            }
            KtCompat.box(m61constructorimpl);
        } else {
            TextView textView = this$0.status;
            if (textView == null) {
                Intrinsics.throwUninitializedPropertyAccessException("status");
                textView = null;
            }
            textView.setText("Start Shizuku first (its app: Start via Wireless debugging)");
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onCreate$lambda$102$lambda$101(final MainActivity this$0, final TextView $hotState) {
        TextView textView = this$0.status;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("status");
            textView = null;
        }
        textView.setText("Hotspot…");
        Hotspot.INSTANCE.turnOn(this$0, "test", new Function1() { // from class: com.starpilot.starlock.MainActivity$$ExternalSyntheticLambda33
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit onCreate$lambda$102$lambda$101$lambda$100;
                onCreate$lambda$102$lambda$101$lambda$100 = MainActivity.onCreate$lambda$102$lambda$101$lambda$100(this$0, $hotState, (String) obj);
                return onCreate$lambda$102$lambda$101$lambda$100;
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onCreate$lambda$102$lambda$101$lambda$100(final MainActivity this$0, final TextView $hotState, final String m) {
        Intrinsics.checkNotNullParameter(m, "m");
        this$0.main.post(new Runnable() { // from class: com.starpilot.starlock.MainActivity$$ExternalSyntheticLambda53
            @Override // java.lang.Runnable
            public final void run() {
                MainActivity.onCreate$lambda$102$lambda$101$lambda$100$lambda$99(this$0, m, $hotState);
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$102$lambda$101$lambda$100$lambda$99(MainActivity this$0, String $m, TextView $hotState) {
        TextView textView = this$0.status;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("status");
            textView = null;
        }
        textView.setText("Hotspot: " + $m);
        onCreate$showHot($hotState, this$0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String onCreate$lambda$104() {
        return "Add";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onCreate$lambda$106(final MainActivity this$0) {
        ((StatusBarManager) this$0.getSystemService(StatusBarManager.class)).requestAddTileService(new ComponentName(this$0, (Class<?>) LockTile.class), "StarLock", Icon.createWithResource(this$0, R.drawable.ic_lock), this$0.getMainExecutor(), new Consumer() { // from class: com.starpilot.starlock.MainActivity$$ExternalSyntheticLambda52
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                MainActivity.onCreate$lambda$106$lambda$105(this$0, (Integer) obj);
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$106$lambda$105(MainActivity this$0, Integer r) {
        TextView textView = this$0.status;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("status");
            textView = null;
        }
        textView.setText((r != null && r.intValue() == 2) ? "Tile added: pull down quick settings" : (r != null && r.intValue() == 1) ? "Tile already there: pull down quick settings" : (r != null && r.intValue() == 0) ? "Tile not added" : "Couldn't add it here: edit quick settings (pencil) and drag StarLock in");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onCreate$lambda$107(MainActivity this$0) {
        this$0.startActivity(new Intent(this$0, (Class<?>) SignalTestActivity.class));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String onCreate$lambda$108(MainActivity this$0) {
        return this$0.pairing() == null ? "Not paired" : "Scan QR · Paste link";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onCreate$lambda$111$lambda$109(MainActivity this$0) {
        this$0.scan();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onCreate$lambda$111$lambda$110(MainActivity this$0) {
        this$0.pairDialog();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String onCreate$lambda$112() {
        return "Copy";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onCreate$lambda$114(MainActivity this$0) {
        ClipboardManager clipboardManager = (ClipboardManager) this$0.getSystemService(ClipboardManager.class);
        String str;
        try {
            str = this$0.getPackageManager().getPackageInfo(this$0.getPackageName(), 0).versionName;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            throw KtCompat.sneaky(e);
        }
        TextView textView = this$0.logView;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("logView");
            textView = null;
        }
        clipboardManager.setPrimaryClip(ClipData.newPlainText("StarLock log", "StarLock " + str + "\n" + ((Object) textView.getText())));
        Toast.makeText(this$0, "Log copied", 0).show();
        return Unit.INSTANCE;
    }

    @Override // android.app.Activity
    protected void onNewIntent(Intent intent) {
        Intrinsics.checkNotNullParameter(intent, "intent");
        super.onNewIntent(intent);
        handle(intent);
    }

    private final void handle(Intent i) {
        String str;
        String action = i != null ? i.getAction() : null;
        if (action != null) {
            switch (action.hashCode()) {
                case 359090247:
                    if (action.equals("com.starpilot.starlock.LOCK")) {
                        str = "lock";
                        break;
                    } else {
                        return;
                    }
                case 1745356640:
                    if (action.equals("com.starpilot.starlock.UNLOCK")) {
                        str = "unlock";
                        break;
                    } else {
                        return;
                    }
                default:
                    return;
            }
            run(str);
        }
    }

    private final void askBluetooth() {
        Iterable perms = CollectionsKt.plus((Collection) (Build.VERSION.SDK_INT >= 31 ? CollectionsKt.listOf(new String[]{"android.permission.BLUETOOTH_SCAN", "android.permission.BLUETOOTH_CONNECT"}) : CollectionsKt.listOf("android.permission.ACCESS_FINE_LOCATION")), (Iterable) (Build.VERSION.SDK_INT >= 33 ? CollectionsKt.listOf("android.permission.POST_NOTIFICATIONS") : CollectionsKt.emptyList()));
        Iterable $this$filter$iv = perms;
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            String it = (String) element$iv$iv;
            if (checkSelfPermission(it) != 0) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        Collection missing = (List) destination$iv$iv;
        if (!missing.isEmpty()) {
            Collection $this$toTypedArray$iv = missing;
            requestPermissions((String[]) $this$toTypedArray$iv.toArray(new String[0]), 1);
        }
    }

    @Override // android.app.Activity
    public void onRequestPermissionsResult(int code, String[] perms, int[] results) {
        Intrinsics.checkNotNullParameter(perms, "perms");
        Intrinsics.checkNotNullParameter(results, "results");
        KeeperService.Companion.start(this);
        showBle();
    }

    private final boolean bleAllowed() {
        return Build.VERSION.SDK_INT >= 31 ? checkSelfPermission("android.permission.BLUETOOTH_SCAN") == 0 && checkSelfPermission("android.permission.BLUETOOTH_CONNECT") == 0 : checkSelfPermission("android.permission.ACCESS_FINE_LOCATION") == 0;
    }

    private final void showState() {
        TextView textView = this.status;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("status");
            textView = null;
        }
        textView.setText(pairing() == null ? "Not paired yet: open settings (gear) › Pairing" : "");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean log(final String s) {
        return this.main.post(new Runnable() { // from class: com.starpilot.starlock.MainActivity$$ExternalSyntheticLambda45
            @Override // java.lang.Runnable
            public final void run() {
                MainActivity.log$lambda$119(MainActivity.this, s);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void log$lambda$119(MainActivity this$0, String $s) {
        TextView textView = this$0.logView;
        TextView textView2 = null;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("logView");
            textView = null;
        }
        String format = new SimpleDateFormat("HH:mm:ss", Locale.US).format(new Date());
        TextView textView3 = this$0.logView;
        if (textView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("logView");
        } else {
            textView2 = textView3;
        }
        textView.setText(StringsKt.take(format + "  " + $s + "\n" + ((Object) textView2.getText()), 4000));
    }

    private final void pairDialog() {
        final EditText $this$pairDialog_u24lambda_u24120 = new EditText(this);
        $this$pairDialog_u24lambda_u24120.setHint("starview://pair?k=…");
        String clip = clip();
        if (clip == null) {
            clip = "";
        }
        $this$pairDialog_u24lambda_u24120.setText(clip);
        new AlertDialog.Builder(this).setTitle("Pairing link").setMessage("Paste the link the comma prints (see the command from Claude). It holds the key, so keep it private.").setView($this$pairDialog_u24lambda_u24120).setPositiveButton("Save", new DialogInterface.OnClickListener() { // from class: com.starpilot.starlock.MainActivity$$ExternalSyntheticLambda10
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                MainActivity.pairDialog$lambda$121(MainActivity.this, $this$pairDialog_u24lambda_u24120, dialogInterface, i);
            }
        }).setNegativeButton("Cancel", (DialogInterface.OnClickListener) null).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void pairDialog$lambda$121(MainActivity this$0, EditText $box, DialogInterface dialogInterface, int i) {
        this$0.save($box.getText().toString());
    }

    private final void scan() {
        Task<Barcode> startScan = GmsBarcodeScanning.getClient(this).startScan();
        final Function1 function1 = new Function1() { // from class: com.starpilot.starlock.MainActivity$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit scan$lambda$122;
                scan$lambda$122 = MainActivity.scan$lambda$122(MainActivity.this, (Barcode) obj);
                return scan$lambda$122;
            }
        };
        startScan.addOnSuccessListener(new OnSuccessListener() { // from class: com.starpilot.starlock.MainActivity$$ExternalSyntheticLambda5
            @Override // com.google.android.gms.tasks.OnSuccessListener
            public final void onSuccess(Object obj) {
                function1.invoke(obj);
            }
        }).addOnFailureListener(new OnFailureListener() { // from class: com.starpilot.starlock.MainActivity$$ExternalSyntheticLambda6
            @Override // com.google.android.gms.tasks.OnFailureListener
            public final void onFailure(Exception exc) {
                MainActivity.scan$lambda$124(MainActivity.this, exc);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit scan$lambda$122(MainActivity this$0, Barcode b) {
        String rawValue = b.getRawValue();
        if (rawValue == null) {
            rawValue = "";
        }
        this$0.save(rawValue);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void scan$lambda$124(MainActivity this$0, Exception e) {
        Intrinsics.checkNotNullParameter(e, "e");
        TextView textView = this$0.status;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("status");
            textView = null;
        }
        textView.setText("Scanner: " + e.getMessage());
    }

    private final void save(String link) {
        Pairing p = Pairing.Companion.parse(link);
        if (p != null) {
            getPrefs().edit().putString("link", p.encode()).remove("host").remove("ble").apply();
            showState();
            String dongle = p.getDongle();
            if (dongle.length() == 0) {
                dongle = "the comma";
            }
            log("paired with " + ((Object) dongle));
            KeeperService.Companion.stop(this);
            KeeperService.Companion.start(this);
            return;
        }
        TextView textView = this.status;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("status");
            textView = null;
        }
        textView.setText("That isn't a StarView pairing link");
    }

    private final String clip() {
        Object m61constructorimpl;
        String it;
        ClipData.Item itemAt;
        CharSequence text;
        try {
            Result.Companion companion = Result.Companion;
            MainActivity $this$clip_u24lambda_u24127 = this;
            ClipData primaryClip = ((ClipboardManager) $this$clip_u24lambda_u24127.getSystemService(ClipboardManager.class)).getPrimaryClip();
            if (primaryClip == null || (itemAt = primaryClip.getItemAt(0)) == null || (text = itemAt.getText()) == null || (it = text.toString()) == null || !StringsKt.startsWith(it, "starview://", false)) {
                it = null;
            }
            m61constructorimpl = KtCompat.resultOf(it);
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            m61constructorimpl = KtCompat.resultOf(ResultKt.createFailure(th));
        }
        return (String) (KtCompat.isFailure(m61constructorimpl) ? null : m61constructorimpl);
    }

    private final void run(String cmd) {
        TextView textView = this.status;
        TextView textView2 = null;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("status");
            textView = null;
        }
        textView.setTextColor(Color.rgb(170, 180, 190));
        boolean started = Door.INSTANCE.run(this, cmd, new MainActivity$run$started$1(this), new Function2() { // from class: com.starpilot.starlock.MainActivity$$ExternalSyntheticLambda7
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit run$lambda$129;
                run$lambda$129 = MainActivity.run$lambda$129(MainActivity.this, (Outcome) obj, (String) obj2);
                return run$lambda$129;
            }
        });
        if (started) {
            TextView textView3 = this.status;
            if (textView3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("status");
            } else {
                textView2 = textView3;
            }
            textView2.setText(Intrinsics.areEqual(cmd, "lock") ? "Locking…" : "Unlocking…");
            showHero(cmd);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit run$lambda$129(MainActivity this$0, Outcome outcome, String msg) {
        int rgb;
        int i;
        int i2;
        Intrinsics.checkNotNullParameter(outcome, "outcome");
        Intrinsics.checkNotNullParameter(msg, "msg");
        TextView textView = this$0.status;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("status");
            textView = null;
        }
        textView.setText(msg);
        TextView textView2 = this$0.status;
        if (textView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("status");
            textView2 = null;
        }
        switch (WhenMappings.$EnumSwitchMapping$0[outcome.ordinal()]) {
            case 1:
                rgb = Color.rgb(94, 229, 160);
                break;
            case 2:
                i = 190;
                i2 = 80;
                rgb = Color.rgb(255, i, i2);
                break;
            default:
                i = 120;
                i2 = 100;
                rgb = Color.rgb(255, i, i2);
                break;
        }
        textView2.setTextColor(rgb);
        showHero$default(this$0, null, 1, null);
        return Unit.INSTANCE;
    }

    static /* synthetic */ void showHero$default(MainActivity mainActivity, String str, int i, Object obj) {
        if ((i & 1) != 0) {
            str = null;
        }
        mainActivity.showHero(str);
    }

    private final void showHero(String working) {
        CarLocks.State car;
        int c;
        String slide;
        Map d;
        Map<String, Integer> map;
        boolean z;
        String title;
        int tint;
        int glow;
        GradientDrawable gradientDrawable;
        GradientDrawable gradientDrawable2;
        GradientDrawable gradientDrawable3;
        if (this.van == null) {
            return;
        }
        int green = Color.rgb(52, 211, 120);
        int red = Color.rgb(240, 82, 82);
        int grey = Color.rgb(90, 98, 110);
        Boolean locked = LockTile.Companion.locked(this);
        CarLocks.State car2 = CarLocks.INSTANCE.state(this);
        String said = CarLocks.INSTANCE.summary(car2);
        boolean live = (car2 != null && car2.getLive()) && said != null;
        Map doors = car2 != null ? car2.getDoors() : null;
        if (doors == null) {
            doors = MapsKt.emptyMap();
        }
        Map d2 = doors;
        String str = "U";
        if (!Intrinsics.areEqual(d2.get("s1"), "U") && !Intrinsics.areEqual(d2.get("s2"), "U")) {
            str = (Intrinsics.areEqual(d2.get("s1"), "L") || Intrinsics.areEqual(d2.get("s2"), "L")) ? "L" : "?";
        }
        String slide2 = str;
        VanView vanView = this.van;
        if (vanView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("van");
            vanView = null;
        }
        if (car2 == null || said == null) {
            car = car2;
            if (Intrinsics.areEqual((Object) locked, (Object) true)) {
                c = green;
            } else if (Intrinsics.areEqual((Object) locked, (Object) false)) {
                c = red;
            } else {
                if (locked != null) {
                    throw new NoWhenBranchMatchedException();
                }
                c = grey;
            }
            slide = slide2;
            Iterable $this$associateWith$iv = CollectionsKt.listOf(new String[]{"driver", "passenger", "slide_l", "slide_r", "back"});
            LinkedHashMap result$iv = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault($this$associateWith$iv, 10)), 16));
            for (Object element$iv$iv : $this$associateWith$iv) {
                Iterable $this$associateWith$iv2 = $this$associateWith$iv;
                result$iv.put(element$iv$iv, Integer.valueOf(c));
                $this$associateWith$iv = $this$associateWith$iv2;
                d2 = d2;
                green = green;
            }
            d = d2;
            map = result$iv;
        } else {
            car = car2;
            map = MapsKt.mapOf(TuplesKt.to("driver", Integer.valueOf(showHero$col(green, red, grey, live, (String) d2.get("dr")))), TuplesKt.to("passenger", Integer.valueOf(showHero$col(green, red, grey, live, (String) d2.get("pa")))), TuplesKt.to("slide_l", Integer.valueOf(showHero$col(green, red, grey, live, slide2))), TuplesKt.to("slide_r", Integer.valueOf(showHero$col(green, red, grey, live, slide2))), TuplesKt.to("back", Integer.valueOf(showHero$col(green, red, grey, live, (String) d2.get("bk")))));
            slide = slide2;
            d = d2;
        }
        vanView.setDoors(map);
        boolean isLocked = live ? Intrinsics.areEqual(said, "Locked") : Intrinsics.areEqual((Object) locked, (Object) true);
        if (this.closing) {
            int tint2 = Color.rgb(191, 219, 254);
            title = "Closing windows…";
            tint = tint2;
            glow = Color.rgb(59, 130, 246);
            z = true;
        } else if (Intrinsics.areEqual(working, "lock")) {
            int tint3 = Color.rgb(90, 98, 110);
            title = "Locking…";
            tint = -1;
            glow = tint3;
            z = true;
        } else if (Intrinsics.areEqual(working, "unlock")) {
            int tint4 = Color.rgb(90, 98, 110);
            title = "Unlocking…";
            tint = -1;
            glow = tint4;
            z = true;
        } else if (live) {
            Intrinsics.checkNotNull(said);
            int tint5 = Intrinsics.areEqual(said, "Locked") ? -1 : Color.rgb(252, 165, 165);
            glow = Intrinsics.areEqual(said, "Locked") ? Color.rgb(34, 197, 94) : Color.rgb(239, 68, 68);
            title = said;
            tint = tint5;
            z = true;
        } else {
            z = true;
            if (Intrinsics.areEqual((Object) locked, (Object) true)) {
                title = "Locked";
                tint = -1;
                glow = Color.rgb(34, 197, 94);
            } else if (Intrinsics.areEqual((Object) locked, (Object) false)) {
                int tint6 = Color.rgb(252, 165, 165);
                title = "Unlocked";
                glow = Color.rgb(239, 68, 68);
                tint = tint6;
            } else {
                String title2 = pairing() == null ? "Not paired" : "Ready";
                title = title2;
                tint = -1;
                glow = 0;
            }
        }
        TextView textView = this.heroText;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("heroText");
            textView = null;
        }
        textView.setText(title);
        TextView textView2 = this.heroText;
        if (textView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("heroText");
            textView2 = null;
        }
        textView2.setTextColor(tint);
        VanView vanView2 = this.van;
        if (vanView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("van");
            vanView2 = null;
        }
        vanView2.setGlow(glow);
        TextView textView3 = this.carLine;
        if (textView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("carLine");
            textView3 = null;
        }
        textView3.setText(this.closing ? "Car off · keep holding" : live ? "Car on · as the car reports it" : (car == null || said == null) ? locked != null ? "the last lock / unlock StarLock sent" : "" : "Car off · last seen " + ((Object) DateUtils.getRelativeTimeSpanString(car.getAt())));
        boolean known = (live || locked != null) ? z : false;
        GradientDrawable gradientDrawable4 = this.lockBg;
        if (gradientDrawable4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("lockBg");
            gradientDrawable = null;
        } else {
            gradientDrawable = gradientDrawable4;
        }
        showHero$paint(this, gradientDrawable, (known && isLocked && working == null) ? z : false, Color.rgb(31, 157, 76), Color.rgb(21, 122, 57), Color.rgb(74, 222, 128));
        GradientDrawable gradientDrawable5 = this.unlockBg;
        if (gradientDrawable5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("unlockBg");
            gradientDrawable2 = null;
        } else {
            gradientDrawable2 = gradientDrawable5;
        }
        showHero$paint(this, gradientDrawable2, known && !isLocked && working == null, Color.rgb(200, 60, 60), Color.rgb(150, 38, 38), Color.rgb(252, 165, 165));
        GradientDrawable gradientDrawable6 = this.windowBg;
        if (gradientDrawable6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("windowBg");
            gradientDrawable3 = null;
        } else {
            gradientDrawable3 = gradientDrawable6;
        }
        showHero$paint(this, gradientDrawable3, this.closing, Color.rgb(37, 99, 235), Color.rgb(29, 78, 216), Color.rgb(147, 197, 253));
    }

    private static final int showHero$col(int green, int red, int grey, boolean live, String v) {
        int it = Intrinsics.areEqual(v, "L") ? green : Intrinsics.areEqual(v, "U") ? red : grey;
        return live ? it : Color.argb(150, Color.red(it), Color.green(it), Color.blue(it));
    }

    private static final void showHero$paint(MainActivity this$0, GradientDrawable bg, boolean on, int top, int bottom, int ring) {
        if (on) {
            bg.setColors(new int[]{top, bottom});
            bg.setStroke((int) (1 * this$0.getResources().getDisplayMetrics().density), ring);
        } else {
            bg.setColors(new int[]{Color.rgb(45, 51, 61), Color.rgb(27, 31, 38)});
            bg.setStroke((int) (1 * this$0.getResources().getDisplayMetrics().density), Color.rgb(58, 65, 77));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showWindowBtn(boolean on) {
        this.closing = on;
        showHero$default(this, null, 1, null);
    }

    private final boolean say(final String text, final int color) {
        return this.main.post(new Runnable() { // from class: com.starpilot.starlock.MainActivity$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                MainActivity.say$lambda$133(MainActivity.this, text, color);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void say$lambda$133(MainActivity this$0, String $text, int $color) {
        TextView textView = this$0.status;
        TextView textView2 = null;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("status");
            textView = null;
        }
        textView.setText($text);
        TextView textView3 = this$0.status;
        if (textView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("status");
        } else {
            textView2 = textView3;
        }
        textView2.setTextColor($color);
    }

    private final void startHold() {
        final int me = this.press.incrementAndGet();
        this.fingerDown = true;
        BleLink l = KeeperService.Companion.getLink();
        if (l == null || !l.getReady()) {
            l = null;
        }
        if (l == null) {
            say("Not connected over Bluetooth - the windows close through Bluetooth", Color.rgb(255, 120, 100));
        } else {
            showWindowBtn(true);
            final BleLink l2 = l;
            new Thread(new Runnable() { // from class: com.starpilot.starlock.MainActivity$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    MainActivity.startHold$lambda$140(l2, MainActivity.this, me);
                }
            }).start();
        }
    }

    private static final boolean startHold$mine(MainActivity this$0, int me) {
        return this$0.fingerDown && this$0.press.get() == me;
    }

    /* VF: from Vineflower (jadx: Code restructure failed;incorrect) */
    public static final void startHold$lambda$140(BleLink var0, MainActivity var1, int var2) {
        String var15 = null; String var18 = null; // declarations Vineflower left out
        JSONObject var6 = new JSONObject();
        boolean var5 = true;
        var6 = var6.put("hold", true);
        Intrinsics.checkNotNullExpressionValue(var6, "put(...)");
        JSONObject var8 = BleLink.request$default(var0, "window", var6, false, 2000L, 4, null);
        boolean var4 = false;
        boolean var3;
        if (var8 != null && var8.optBoolean("ok")) {
            var3 = true;
        } else {
            var3 = false;
        }

        String var14 = null;
        Object var7 = null;
        if (!var3) {
            label55: {
                if (var8 != null) {
                    String var17 = var8.optString("error");
                    if (var17 != null) {
                        if (var17.length() > 0) {
                            var3 = var5;
                        } else {
                            var3 = false;
                        }

                        String var9 = (String)var7;
                        if (var3) {
                            var9 = var17;
                        }

                        var18 = var9;
                        if (var9 != null) {
                            break label55;
                        }
                    }
                }

                var18 = "No answer from the comma";
            }

            var1.say(var18, Color.rgb(255, 120, 100));
            if (var1.press.get() == var2) {
                var1.main.post(new MainActivity$$ExternalSyntheticLambda48(var1));
            }
        } else {
            var1.log("Window close: holding");
            var1.say("Closing the windows… keep holding", Color.rgb(140, 190, 255));
            var7 = var8.optString("k");

            while (startHold$mine(var1, var2)) {
                Thread.sleep(250L);
                if (!startHold$mine(var1, var2)) {
                    break;
                }

                var8 = new JSONObject().put("k", var7);
                Intrinsics.checkNotNullExpressionValue(var8, "put(...)");
                var8 = var0.request("window", var8, false, 500L);
                if (var8 != null && var8.optBoolean("ok")) {
                    var3 = true;
                } else {
                    var3 = false;
                }

                if (!var3) {
                    label67: {
                        if (var8 != null) {
                            var7 = var8.optString("error");
                            if (var7 != null) {
                                var3 = var4;
                                if (((CharSequence)var7).length() > 0) {
                                    var3 = true;
                                }

                                if (var3) {
                                    var14 = (String)var7;
                                }

                                if (var14 != null) {
                                    var7 = "Stopped: " + var14;
                                    var15 = (String)var7;
                                    if (var7 != null) {
                                        break label67;
                                    }
                                }
                            }
                        }

                        var15 = "Lost the comma - let go and hold again";
                    }

                    var1.say(var15, Color.rgb(255, 190, 80));
                    break;
                }
            }

            if (var1.press.get() == var2) {
                var6 = new JSONObject().put("stop", true);
                Intrinsics.checkNotNullExpressionValue(var6, "put(...)");
                var0.request("window", var6, false, 500L);
                var1.log("Window close: let go");
                var1.main.post(new MainActivity$$ExternalSyntheticLambda49(var1));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void startHold$lambda$140$lambda$139(MainActivity this$0) {
        this$0.showWindowBtn(false);
        TextView textView = this$0.status;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("status");
            textView = null;
        }
        CharSequence text = textView.getText();
        Intrinsics.checkNotNullExpressionValue(text, "getText(...)");
        if (StringsKt.startsWith(text, (CharSequence) "Closing", false)) {
            this$0.say("Let go - the windows stop where they are", Color.rgb(170, 180, 190));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void toggleSettings() {
        LinearLayout linearLayout = this.settingsPanel;
        ScrollView scrollView = null;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("settingsPanel");
            linearLayout = null;
        }
        boolean show = linearLayout.getVisibility() != 0;
        LinearLayout linearLayout2 = this.settingsPanel;
        if (linearLayout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("settingsPanel");
            linearLayout2 = null;
        }
        linearLayout2.setVisibility(show ? 0 : 8);
        if (!show) {
            ScrollView scrollView2 = this.scroller;
            if (scrollView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("scroller");
            } else {
                scrollView = scrollView2;
            }
            scrollView.smoothScrollTo(0, 0);
            return;
        }
        ScrollView scrollView3 = this.scroller;
        if (scrollView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("scroller");
        } else {
            scrollView = scrollView3;
        }
        scrollView.post(new Runnable() { // from class: com.starpilot.starlock.MainActivity$$ExternalSyntheticLambda50
            @Override // java.lang.Runnable
            public final void run() {
                MainActivity.toggleSettings$lambda$141(MainActivity.this);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void toggleSettings$lambda$141(MainActivity this$0) {
        ScrollView scrollView = this$0.scroller;
        LinearLayout linearLayout = null;
        if (scrollView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("scroller");
            scrollView = null;
        }
        LinearLayout linearLayout2 = this$0.settingsPanel;
        if (linearLayout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("settingsPanel");
        } else {
            linearLayout = linearLayout2;
        }
        scrollView.smoothScrollTo(0, linearLayout.getTop() - ((int) (12 * this$0.getResources().getDisplayMetrics().density)));
    }

    @Override // android.app.Activity
    @Deprecated(message = "Deprecated in Java")
    public void onBackPressed() {
        if (this.settingsPanel != null) {
            LinearLayout linearLayout = this.settingsPanel;
            if (linearLayout == null) {
                Intrinsics.throwUninitializedPropertyAccessException("settingsPanel");
                linearLayout = null;
            }
            if (linearLayout.getVisibility() == 0) {
                toggleSettings();
                return;
            }
        }
        super.onBackPressed();
    }

    // recovered from Vineflower (R8 lambda body jadx inlined)
   static final void startHold$lambda$140$lambda$136(MainActivity var0) {
       var0.showWindowBtn(false);
    }
}
