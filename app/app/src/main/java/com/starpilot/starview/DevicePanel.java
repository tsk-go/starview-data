package com.starpilot.starview;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.starpilot.starview.SettingIcons;
import com.starpilot.starview.SvIcon;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: DevicePanels.kt */
public final class DevicePanel extends ScrollView {
    final Activity act;
    private boolean autoCan;
    private Boolean autoDl;
    private final LinearLayout col;
    private final float d;
    private final int dim;
    private final String e2eDesc;
    final Handler fastHandler;
    private final DevicePanel$fastPoll$1 fastPoll;
    boolean fastPolling;
    JSONObject fastStatus;
    private final String kind;
    private int lastGen;
    private int meteredSel;
    private final LinearLayout netList;
    private final TextView netStatus;
    private JSONObject offline;
    private final String personalityDesc;
    private int pollTick;
    private boolean scanning;
    Boolean seven;
    private boolean syncing;
    private Switch teth;
    private final ArrayList<Function0<Unit>> updaters;
    private JSONObject wifi;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterative(DepthRegionTraversal.java:31)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visit(SwitchOverStringVisitor.java:60)
     */
    public DevicePanel(Activity act, String kind) {
        super(act);
        Intrinsics.checkNotNullParameter(act, "act");
        Intrinsics.checkNotNullParameter(kind, "kind");
        this.act = act;
        this.kind = kind;
        this.d = this.act.getResources().getDisplayMetrics().density;
        LinearLayout $this$col_u24lambda_u240 = new LinearLayout(this.act);
        $this$col_u24lambda_u240.setOrientation(1);
        $this$col_u24lambda_u240.setPadding(dp(8), dp(4), dp(20), dp(40));
        this.col = $this$col_u24lambda_u240;
        this.updaters = new ArrayList<>();
        this.lastGen = -1;
        this.dim = Color.rgb(143, 163, 199);
        this.personalityDesc = "Standard is recommended. In aggressive mode, openpilot will follow lead cars closer and be more aggressive with the gas and brake. In relaxed mode openpilot will stay further away from lead cars. On supported cars, you can cycle through these personalities with your steering wheel distance button.";
        this.e2eDesc = "openpilot defaults to driving in chill mode. Experimental mode enables alpha-level features that aren't ready for chill mode: end-to-end longitudinal control (the driving model controls gas and brakes, stopping for red lights and stop signs; the set speed is only an upper bound) and the new driving visualization. This is an alpha quality feature; mistakes should be expected.";
        LinearLayout $this$netList_u24lambda_u24115 = new LinearLayout(this.act);
        $this$netList_u24lambda_u24115.setOrientation(1);
        this.netList = $this$netList_u24lambda_u24115;
        TextView $this$netStatus_u24lambda_u24116 = new TextView(this.act);
        $this$netStatus_u24lambda_u24116.setTextColor(this.dim);
        $this$netStatus_u24lambda_u24116.setTextSize(14.0f);
        $this$netStatus_u24lambda_u24116.setPadding(dp(4), dp(4), dp(4), dp(8));
        this.netStatus = $this$netStatus_u24lambda_u24116;
        this.wifi = new JSONObject();
        this.offline = new JSONObject();
        this.fastHandler = new Handler(Looper.getMainLooper());
        this.fastPoll = new DevicePanel$fastPoll$1(this);
        addView(this.col, new ViewGroup.LayoutParams(-1, -2));
        String str = this.kind;
        switch (str.hashCode()) {
            case -1335157162:
                if (str.equals("device")) {
                    buildDevice();
                    break;
                }
                break;
            case -1147621473:
                if (str.equals("toggles")) {
                    buildToggles();
                    break;
                }
                break;
            case -80681014:
                if (str.equals("developer")) {
                    buildDeveloper();
                    break;
                }
                break;
            case 1319330215:
                if (str.equals("software")) {
                    buildSoftware();
                    break;
                }
                break;
            case 1843485230:
                if (str.equals("network")) {
                    buildNetwork();
                    break;
                }
                break;
        }
        refresh(true);
    }

    public final String getKind() {
        return this.kind;
    }

    private final int dp(int v) {
        return (int) (v * this.d);
    }

    private final Telemetry getT() {
        return Link.INSTANCE.getTelemetry();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final DataClient getData() {
        return Link.INSTANCE.getData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String getHost() {
        return Link.INSTANCE.getHost();
    }

    private final LinearLayout card() {
        LinearLayout $this$card_u24lambda_u241 = new LinearLayout(this.act);
        $this$card_u24lambda_u241.setOrientation(0);
        $this$card_u24lambda_u241.setGravity(16);
        $this$card_u24lambda_u241.setMinimumHeight(dp(76));
        $this$card_u24lambda_u241.setPadding(dp(20), dp(14), dp(18), dp(14));
        $this$card_u24lambda_u241.setBackgroundResource(R.drawable.card_bg);
        return $this$card_u24lambda_u241;
    }

    private final void add(View v) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-1, -2);
        lp.bottomMargin = dp(6);
        this.col.addView(v, lp);
    }

    private final void header(String text) {
        LinearLayout linearLayout = this.col;
        TextView $this$header_u24lambda_u242 = new TextView(this.act);
        String upperCase = text.toUpperCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(upperCase, "toUpperCase(...)");
        $this$header_u24lambda_u242.setText(upperCase);
        $this$header_u24lambda_u242.setTextColor(this.dim);
        $this$header_u24lambda_u242.setTextSize(14.0f);
        $this$header_u24lambda_u242.setLetterSpacing(0.1f);
        $this$header_u24lambda_u242.setPadding(dp(6), dp(22), 0, dp(8));
        linearLayout.addView($this$header_u24lambda_u242);
    }

    private final TextView noteView(String text) {
        TextView $this$noteView_u24lambda_u243 = new TextView(this.act);
        $this$noteView_u24lambda_u243.setText(text);
        $this$noteView_u24lambda_u243.setTextColor(this.dim);
        $this$noteView_u24lambda_u243.setTextSize(15.0f);
        $this$noteView_u24lambda_u243.setPadding(dp(4), dp(4), dp(4), dp(10));
        return $this$noteView_u24lambda_u243;
    }

    private final void note(String text) {
        this.col.addView(noteView(text));
    }

    private final View chip(String title) {
        SettingIcons.Icon ic = SettingIcons.INSTANCE.forLabel(title, "device");
        return SvIcon.chip$default(SvIcon.INSTANCE, this.act, ic.getName(), ic.getGroup(), 46, null, 16, null);
    }

    private final LinearLayout.LayoutParams chipLp() {
        LinearLayout.LayoutParams $this$chipLp_u24lambda_u244 = new LinearLayout.LayoutParams(dp(46), dp(46));
        $this$chipLp_u24lambda_u244.rightMargin = dp(16);
        return $this$chipLp_u24lambda_u244;
    }

    private final LinearLayout titleBlock(String title, String desc) {
        LinearLayout tb = new LinearLayout(this.act);
        boolean z = true;
        tb.setOrientation(1);
        TextView $this$titleBlock_u24lambda_u246 = new TextView(this.act);
        $this$titleBlock_u24lambda_u246.setText(title);
        $this$titleBlock_u24lambda_u246.setTextColor(-1);
        $this$titleBlock_u24lambda_u246.setTextSize(21.0f);
        tb.addView($this$titleBlock_u24lambda_u246);
        String str = desc;
        if (str != null && str.length() != 0) {
            z = false;
        }
        if (!z) {
            TextView $this$titleBlock_u24lambda_u247 = new TextView(this.act);
            $this$titleBlock_u24lambda_u247.setText(desc);
            $this$titleBlock_u24lambda_u247.setTextColor(this.dim);
            $this$titleBlock_u24lambda_u247.setTextSize(15.0f);
            $this$titleBlock_u24lambda_u247.setPadding(0, dp(4), 0, 0);
            tb.addView($this$titleBlock_u24lambda_u247);
        }
        return tb;
    }

    static /* synthetic */ LinearLayout toggle$default(DevicePanel devicePanel, String str, String str2, Function0 function0, Function0 function02, Function0 function03, Function0 function04, Function2 function2, int i, Object obj) {
        Function0 function05;
        Function0 function06 = (i & 8) != 0 ? new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda82
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean z;
                z = DevicePanel.toggle$lambda$8();
                return Boolean.valueOf(z);
            }
        } : function02;
        Function0 function07 = (i & 16) != 0 ? new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda83
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean z;
                z = DevicePanel.toggle$lambda$9();
                return Boolean.valueOf(z);
            }
        } : function03;
        if ((i & 32) == 0) {
            function05 = function04;
        } else {
            function05 = null;
        }
        return devicePanel.toggle(str, str2, function0, function06, function07, function05, function2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean toggle$lambda$8() {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean toggle$lambda$9() {
        return true;
    }

    private final LinearLayout toggle(String title, String desc, final Function0<Boolean> get, final Function0<Boolean> enabled, final Function0<Boolean> visible, final Function0<String> descFn, final Function2<? super Boolean, ? super Switch, Unit> onChange) {
        final LinearLayout row = card();
        final LinearLayout tb = titleBlock(title, desc == null ? descFn != null ? descFn.invoke() : null : desc);
        row.addView(chip(title), chipLp());
        row.addView(tb, new LinearLayout.LayoutParams(0, -2, 1.0f));
        final Switch $this$toggle_u24lambda_u2410 = new Switch(this.act);
        $this$toggle_u24lambda_u2410.setScaleX(1.45f);
        $this$toggle_u24lambda_u2410.setScaleY(1.45f);
        $this$toggle_u24lambda_u2410.setPadding(dp(14), 0, dp(14), 0);
        $this$toggle_u24lambda_u2410.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda175
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                DevicePanel.toggle$lambda$11(DevicePanel.this, onChange, $this$toggle_u24lambda_u2410, compoundButton, z);
            }
        });
        row.addView($this$toggle_u24lambda_u2410);
        add(row);
        this.updaters.add(new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda11
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit unit;
                unit = DevicePanel.toggle$lambda$12(DevicePanel.this, $this$toggle_u24lambda_u2410, get, enabled, row, visible, descFn, tb);
                return unit;
            }
        });
        return row;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void toggle$lambda$11(DevicePanel this$0, Function2 $onChange, Switch $sw, CompoundButton compoundButton, boolean checked) {
        if (this$0.syncing) {
            return;
        }
        $onChange.invoke(Boolean.valueOf(checked), $sw);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit toggle$lambda$12(DevicePanel this$0, Switch $sw, Function0 $get, Function0 $enabled, LinearLayout $row, Function0 $visible, Function0 $descFn, LinearLayout $tb) {
        this$0.syncing = true;
        $sw.setChecked(((Boolean) $get.invoke()).booleanValue());
        $sw.setEnabled(((Boolean) $enabled.invoke()).booleanValue());
        $row.setAlpha($sw.isEnabled() ? 1.0f : 0.5f);
        $row.setVisibility(((Boolean) $visible.invoke()).booleanValue() ? 0 : 8);
        if ($descFn != null && $tb.getChildCount() > 1) {
            View childAt = $tb.getChildAt(1);
            Intrinsics.checkNotNull(childAt, "null cannot be cast to non-null type android.widget.TextView");
            ((TextView) childAt).setText((CharSequence) $descFn.invoke());
        }
        this$0.syncing = false;
        return Unit.INSTANCE;
    }

    static /* synthetic */ LinearLayout button$default(DevicePanel devicePanel, String str, Function0 function0, String str2, Function0 function02, Function0 function03, Function0 function04, Function0 function05, Function0 function06, int i, Object obj) {
        Function0 function07;
        Function0 function08;
        String str3 = (i & 4) != 0 ? null : str2;
        Function0 function09 = (i & 8) != 0 ? new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda70
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                String button$lambda$13;
                button$lambda$13 = DevicePanel.button$lambda$13();
                return button$lambda$13;
            }
        } : function02;
        Function0 function010 = (i & 16) != 0 ? new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda71
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean button$lambda$14;
                button$lambda$14 = DevicePanel.button$lambda$14();
                return Boolean.valueOf(button$lambda$14);
            }
        } : function03;
        if ((i & 32) == 0) {
            function07 = function04;
        } else {
            function07 = new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda72
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    boolean button$lambda$15;
                    button$lambda$15 = DevicePanel.button$lambda$15();
                    return Boolean.valueOf(button$lambda$15);
                }
            };
        }
        if ((i & 64) == 0) {
            function08 = function05;
        } else {
            function08 = null;
        }
        return devicePanel.button(str, function0, str3, function09, function010, function07, function08, function06);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String button$lambda$13() {
        return "";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean button$lambda$14() {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean button$lambda$15() {
        return true;
    }

    private final LinearLayout button(String title, final Function0<String> label, String desc, final Function0<String> value, final Function0<Boolean> enabled, final Function0<Boolean> visible, final Function0<String> descFn, final Function0<Unit> onClick) {
        final LinearLayout row = card();
        final LinearLayout tb = titleBlock(title, desc == null ? descFn != null ? descFn.invoke() : null : desc);
        row.addView(chip(title), chipLp());
        row.addView(tb, new LinearLayout.LayoutParams(0, -2, 1.0f));
        final TextView $this$button_u24lambda_u2416 = new TextView(this.act);
        $this$button_u24lambda_u2416.setTextColor(this.dim);
        $this$button_u24lambda_u2416.setTextSize(17.0f);
        $this$button_u24lambda_u2416.setPadding(dp(8), 0, dp(12), 0);
        $this$button_u24lambda_u2416.setGravity(8388613);
        row.addView($this$button_u24lambda_u2416, new LinearLayout.LayoutParams(0, -2, 0.8f));
        final Button $this$button_u24lambda_u2418 = new Button(this.act);
        $this$button_u24lambda_u2418.setTextSize(17.0f);
        $this$button_u24lambda_u2418.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda39
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                onClick.invoke();
            }
        });
        $this$button_u24lambda_u2418.setMinWidth(dp(130));
        $this$button_u24lambda_u2418.setMinHeight(dp(56));
        row.addView($this$button_u24lambda_u2418);
        add(row);
        this.updaters.add(new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda40
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit button$lambda$20;
                button$lambda$20 = DevicePanel.button$lambda$20($this$button_u24lambda_u2418, label, enabled, value, $this$button_u24lambda_u2416, row, visible, descFn, tb, DevicePanel.this);
                return button$lambda$20;
            }
        });
        return row;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit button$lambda$20(Button $btn, Function0 $label, Function0 $enabled, Function0 $value, TextView $valTv, LinearLayout $row, Function0 $visible, Function0 $descFn, LinearLayout $tb, DevicePanel this$0) {
        $btn.setText((CharSequence) $label.invoke());
        $btn.setEnabled(((Boolean) $enabled.invoke()).booleanValue());
        $btn.setAlpha($btn.isEnabled() ? 1.0f : 0.5f);
        String v = (String) $value.invoke();
        $valTv.setText(v);
        $valTv.setVisibility(v.length() == 0 ? 8 : 0);
        $row.setVisibility(((Boolean) $visible.invoke()).booleanValue() ? 0 : 8);
        if ($descFn != null) {
            if ($tb.getChildCount() > 1) {
                View childAt = $tb.getChildAt(1);
                Intrinsics.checkNotNull(childAt, "null cannot be cast to non-null type android.widget.TextView");
                ((TextView) childAt).setText((CharSequence) $descFn.invoke());
            } else {
                TextView $this$button_u24lambda_u2420_u24lambda_u2419 = new TextView(this$0.act);
                $this$button_u24lambda_u2420_u24lambda_u2419.setText((CharSequence) $descFn.invoke());
                $this$button_u24lambda_u2420_u24lambda_u2419.setTextColor(this$0.dim);
                $this$button_u24lambda_u2420_u24lambda_u2419.setTextSize(15.0f);
                $tb.addView($this$button_u24lambda_u2420_u24lambda_u2419);
            }
        }
        return Unit.INSTANCE;
    }

    private final void text(String title, final Function0<String> value) {
        LinearLayout row = card();
        row.addView(chip(title), chipLp());
        row.addView(titleBlock(title, null), new LinearLayout.LayoutParams(0, -2, 1.0f));
        final TextView $this$text_u24lambda_u2421 = new TextView(this.act);
        $this$text_u24lambda_u2421.setTextColor(this.dim);
        $this$text_u24lambda_u2421.setTextSize(19.0f);
        $this$text_u24lambda_u2421.setTypeface(Typeface.MONOSPACE);
        row.addView($this$text_u24lambda_u2421);
        add(row);
        this.updaters.add(new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda155
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit text$lambda$22;
                text$lambda$22 = DevicePanel.text$lambda$22($this$text_u24lambda_u2421, value);
                return text$lambda$22;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit text$lambda$22(TextView $tv, Function0 $value) {
        $tv.setText((CharSequence) $value.invoke());
        return Unit.INSTANCE;
    }

    static /* synthetic */ void multi$default(DevicePanel devicePanel, String str, String str2, List list, Function0 function0, Function0 function02, Function1 function1, int i, Object obj) {
        if ((i & 16) != 0) {
            function02 = new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda164
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    boolean multi$lambda$23;
                    multi$lambda$23 = DevicePanel.multi$lambda$23();
                    return Boolean.valueOf(multi$lambda$23);
                }
            };
        }
        devicePanel.multi(str, str2, list, function0, function02, function1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean multi$lambda$23() {
        return true;
    }

    private final void multi(String title, String desc, List<String> options, final Function0<Integer> selected, final Function0<Boolean> enabled, final Function1<? super Integer, Unit> onSelect) {
        LinearLayout outer = new LinearLayout(this.act);
        outer.setOrientation(1);
        outer.setMinimumHeight(dp(76));
        outer.setPadding(dp(20), dp(14), dp(18), dp(14));
        outer.setBackgroundResource(R.drawable.card_bg);
        LinearLayout $this$multi_u24lambda_u2425 = new LinearLayout(this.act);
        $this$multi_u24lambda_u2425.setOrientation(0);
        $this$multi_u24lambda_u2425.setGravity(16);
        $this$multi_u24lambda_u2425.addView(chip(title), chipLp());
        $this$multi_u24lambda_u2425.addView(titleBlock(title, desc), new LinearLayout.LayoutParams(0, -2, 1.0f));
        outer.addView($this$multi_u24lambda_u2425);
        LinearLayout bar = new LinearLayout(this.act);
        bar.setOrientation(0);
        bar.setPadding(0, dp(8), 0, 0);
        List<String> $this$mapIndexed$iv = options;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$mapIndexed$iv, 10));
        int index$iv$iv = 0;
        for (Object item$iv$iv : $this$mapIndexed$iv) {
            int index$iv$iv2 = index$iv$iv + 1;
            if (index$iv$iv < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            String o = (String) item$iv$iv;
            Button $this$multi_u24lambda_u2429_u24lambda_u2428 = new Button(this.act);
            $this$multi_u24lambda_u2429_u24lambda_u2428.setText(o);
            $this$multi_u24lambda_u2429_u24lambda_u2428.setTextSize(17.0f);
            $this$multi_u24lambda_u2429_u24lambda_u2428.setMinHeight(dp(56));
            final var index$iv$iv$f = index$iv$iv;
            $this$multi_u24lambda_u2429_u24lambda_u2428.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda170
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    DevicePanel.multi$lambda$29$lambda$28$lambda$27(onSelect, index$iv$iv$f, view);
                }
            });
            destination$iv$iv.add($this$multi_u24lambda_u2429_u24lambda_u2428);
            index$iv$iv = index$iv$iv2;
            $this$mapIndexed$iv = $this$mapIndexed$iv;
        }
        final List<Button> btns = (List) destination$iv$iv;
        for (Button b : btns) {
            LinearLayout.LayoutParams $this$multi_u24lambda_u2430 = new LinearLayout.LayoutParams(0, -2, 1.0f);
            $this$multi_u24lambda_u2430.rightMargin = dp(6);
            Unit unit = Unit.INSTANCE;
            bar.addView(b, $this$multi_u24lambda_u2430);
        }
        outer.addView(bar);
        add(outer);
        this.updaters.add(new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda171
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit multi$lambda$32;
                multi$lambda$32 = DevicePanel.multi$lambda$32(selected, enabled, btns);
                return multi$lambda$32;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void multi$lambda$29$lambda$28$lambda$27(Function1 $onSelect, int $i, View it) {
        $onSelect.invoke(Integer.valueOf($i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit multi$lambda$32(Function0 $selected, Function0 $enabled, List $btns) {
        int s = ((Number) $selected.invoke()).intValue();
        boolean en = ((Boolean) $enabled.invoke()).booleanValue();
        List $this$forEachIndexed$iv = $btns;
        int i = 0;
        for (Object item$iv : $this$forEachIndexed$iv) {
            int index$iv = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            Button b = (Button) item$iv;
            b.setAlpha(!en ? 0.4f : i == s ? 1.0f : 0.55f);
            b.setEnabled(en);
            b.setTextColor(i == s ? Color.rgb(111, 227, 161) : -1);
            i = index$iv;
        }
        return Unit.INSTANCE;
    }

    static /* synthetic */ void dual$default(DevicePanel devicePanel, String str, String str2, Function0 function0, Function0 function02, Function0 function03, int i, Object obj) {
        if ((i & 16) != 0) {
            function03 = new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda49
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    boolean dual$lambda$33;
                    dual$lambda$33 = DevicePanel.dual$lambda$33();
                    return Boolean.valueOf(dual$lambda$33);
                }
            };
        }
        devicePanel.dual(str, str2, function0, function02, function03);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean dual$lambda$33() {
        return true;
    }

    private final void dual(String left, String right, final Function0<Unit> onLeft, final Function0<Unit> onRight, final Function0<Boolean> rightVisible) {
        LinearLayout row = new LinearLayout(this.act);
        row.setOrientation(0);
        row.setMinimumHeight(dp(76));
        row.setPadding(dp(20), dp(14), dp(18), dp(14));
        row.setBackgroundResource(R.drawable.card_bg);
        Button $this$dual_u24lambda_u2436 = new Button(this.act);
        $this$dual_u24lambda_u2436.setText(left);
        $this$dual_u24lambda_u2436.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda76
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                onLeft.invoke();
            }
        });
        final Button $this$dual_u24lambda_u2438 = new Button(this.act);
        $this$dual_u24lambda_u2438.setText(right);
        $this$dual_u24lambda_u2438.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda78
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                onRight.invoke();
            }
        });
        $this$dual_u24lambda_u2438.setTextColor(Color.rgb(255, 120, 120));
        LinearLayout.LayoutParams $this$dual_u24lambda_u2439 = new LinearLayout.LayoutParams(0, -2, 1.0f);
        $this$dual_u24lambda_u2439.rightMargin = dp(8);
        Unit unit = Unit.INSTANCE;
        row.addView($this$dual_u24lambda_u2436, $this$dual_u24lambda_u2439);
        row.addView($this$dual_u24lambda_u2438, new LinearLayout.LayoutParams(0, -2, 1.0f));
        add(row);
        this.updaters.add(new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda79
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit dual$lambda$40;
                dual$lambda$40 = DevicePanel.dual$lambda$40($this$dual_u24lambda_u2438, rightVisible);
                return dual$lambda$40;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit dual$lambda$40(Button $r, Function0 $rightVisible) {
        $r.setVisibility(((Boolean) $rightVisible.invoke()).booleanValue() ? 0 : 4);
        return Unit.INSTANCE;
    }

    private final boolean p(String key) {
        Telemetry t = getT();
        if (t != null) {
            return t.pBool(key, false);
        }
        return false;
    }

    private final String pStr(String key) {
        String pStr$default;
        Telemetry t = getT();
        return (t == null || (pStr$default = Telemetry.pStr$default(t, key, null, 2, null)) == null) ? "" : pStr$default;
    }

    private final boolean i(String key) {
        Telemetry t = getT();
        if (t != null) {
            return t.iBool(key, false);
        }
        return false;
    }

    private final boolean engaged() {
        return i("engaged");
    }

    private final boolean onroad() {
        return i("onroad");
    }

    private final boolean locked(String key) {
        Map<String, Object> info;
        Telemetry t = getT();
        Object obj = (t == null || (info = t.getInfo()) == null) ? null : info.get("lock");
        JSONArray l = obj instanceof JSONArray ? (JSONArray) obj : null;
        if (l == null) {
            return false;
        }
        int length = l.length();
        for (int k = 0; k < length; k++) {
            if (Intrinsics.areEqual(l.optString(k), key)) {
                return true;
            }
        }
        return false;
    }

    private final boolean connected() {
        DataClient data = getData();
        return data != null && data.getConnected();
    }

    /* VF: from Vineflower (jadx: ) */
    static void setParam$default(DevicePanel var0, String var1, Object var2, boolean var3, Function1 var4, int var5, Object var6) {
        if ((var5 & 4) != 0) {
            var3 = false;
        }

        if ((var5 & 8) != 0) {
            var4 = null;
        }

        var0.setParam(var1, var2, var3, var4);
    }

    private final void setParam(String key, Object value, boolean memory, final Function1<? super JSONObject, Unit> then) {
        DataClient data = getData();
        if (data == null) {
            toast("Not connected");
        } else {
            data.request("param", MapsKt.mapOf(TuplesKt.to("key", key), TuplesKt.to("value", value), TuplesKt.to("memory", Boolean.valueOf(memory))), new Function1() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda43
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    Unit param$lambda$41;
                    param$lambda$41 = DevicePanel.setParam$lambda$41(DevicePanel.this, then, (JSONObject) obj);
                    return param$lambda$41;
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit setParam$lambda$41(DevicePanel this$0, Function1 $then, JSONObject ack) {
        Intrinsics.checkNotNullParameter(ack, "ack");
        this$0.ackToast(ack);
        if ($then != null) {
            $then.invoke(ack);
        }
        this$0.refresh(true);
        return Unit.INSTANCE;
    }

    /* VF: from Vineflower (jadx: ) */
    static void call$default(DevicePanel var0, String var1, Map var2, Function1 var3, int var4, Object var5) {
        if ((var4 & 2) != 0) {
            var2 = MapsKt.emptyMap();
        }

        if ((var4 & 4) != 0) {
            var3 = null;
        }

        var0.call(var1, var2, var3);
    }

    private final void call(String action, Map<String, ? extends Object> args, final Function1<? super JSONObject, Unit> then) {
        DataClient data = getData();
        if (data != null) {
            data.request(action, args, new Function1() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda163
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    Unit call$lambda$42;
                    call$lambda$42 = DevicePanel.call$lambda$42(then, DevicePanel.this, (JSONObject) obj);
                    return call$lambda$42;
                }
            });
        } else {
            toast("Not connected");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit call$lambda$42(Function1 $then, DevicePanel this$0, JSONObject ack) {
        Intrinsics.checkNotNullParameter(ack, "ack");
        if ($then == null) {
            this$0.ackToast(ack);
        }
        if ($then != null) {
            $then.invoke(ack);
        }
        this$0.refresh(true);
        return Unit.INSTANCE;
    }

    private final void ackToast(JSONObject ack) {
        if (ack.optBoolean("ok")) {
            return;
        }
        String optString = ack.optString("error", "rejected");
        Intrinsics.checkNotNullExpressionValue(optString, "optString(...)");
        toast(optString);
    }

    private final void toast(String s) {
        Toast.makeText(this.act, s, 0).show();
    }

    static /* synthetic */ void confirm$default(DevicePanel devicePanel, String str, String str2, boolean z, Function0 function0, int i, Object obj) {
        if ((i & 4) != 0) {
            z = false;
        }
        devicePanel.confirm(str, str2, z, function0);
    }

    private final void confirm(String msg, String yes, boolean danger, final Function0<Unit> onYes) {
        new AlertDialog.Builder(this.act).setMessage(msg).setPositiveButton(yes, new DialogInterface.OnClickListener() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda173
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                onYes.invoke();
            }
        }).setNegativeButton("Cancel", (DialogInterface.OnClickListener) null).show();
    }

    private final void alert(String title, CharSequence msg) {
        new AlertDialog.Builder(this.act).setTitle(title).setMessage(msg).setPositiveButton("OK", (DialogInterface.OnClickListener) null).show();
    }

    static /* synthetic */ void input$default(DevicePanel devicePanel, String str, String str2, String str3, boolean z, int i, Function1 function1, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            str3 = "";
        }
        devicePanel.input(str, str2, str3, (i2 & 8) != 0 ? false : z, (i2 & 16) != 0 ? 0 : i, function1);
    }

    private final void input(String title, String hint, String initial, boolean password, final int minLen, final Function1<? super String, Unit> onDone) {
        final EditText $this$input_u24lambda_u2444 = new EditText(this.act);
        $this$input_u24lambda_u2444.setText(initial);
        $this$input_u24lambda_u2444.setHint(hint);
        $this$input_u24lambda_u2444.setInputType(password ? 145 : 1);
        $this$input_u24lambda_u2444.setPadding(dp(20), dp(16), dp(20), dp(16));
        new AlertDialog.Builder(this.act).setTitle(title).setView($this$input_u24lambda_u2444).setPositiveButton("OK", new DialogInterface.OnClickListener() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda166
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                DevicePanel.input$lambda$45($this$input_u24lambda_u2444, minLen, DevicePanel.this, onDone, dialogInterface, i);
            }
        }).setNegativeButton("Cancel", (DialogInterface.OnClickListener) null).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void input$lambda$45(EditText $e, int $minLen, DevicePanel this$0, Function1 $onDone, DialogInterface dialogInterface, int i) {
        String s = $e.getText().toString();
        if (s.length() < $minLen) {
            this$0.toast("Must be at least " + $minLen + " characters");
        } else {
            $onDone.invoke(s);
        }
    }

    private final void choose(String title, final List<String> options, String current, final Function1<? super String, Unit> onPick) {
        int idx = options.indexOf(current);
        List<String> $this$toTypedArray$iv = options;
        new AlertDialog.Builder(this.act).setTitle(title).setSingleChoiceItems((CharSequence[]) $this$toTypedArray$iv.toArray(new String[0]), idx, new DialogInterface.OnClickListener() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda142
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                DevicePanel.choose$lambda$46(onPick, options, dialogInterface, i);
            }
        }).setNegativeButton("Cancel", (DialogInterface.OnClickListener) null).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void choose$lambda$46(Function1 $onPick, List $options, DialogInterface dlg, int which) {
        dlg.dismiss();
        $onPick.invoke($options.get(which));
    }

    private final void showQr(String title, String url) {
        Bitmap bitmap;
        int size = dp(320);
        try {
            BitMatrix m = new QRCodeWriter().encode(url, BarcodeFormat.QR_CODE, size, size);
            int i = size * size;
            int[] px = new int[i];
            for (int i2 = 0; i2 < i; i2++) {
                px[i2] = m.get(i2 % size, i2 / size) ? -16777216 : -1;
            }
            bitmap = Bitmap.createBitmap(px, size, size, Bitmap.Config.RGB_565);
        } catch (Exception e) {
            bitmap = null;
        }
        Bitmap bm = bitmap;
        LinearLayout box = new LinearLayout(this.act);
        box.setOrientation(1);
        box.setGravity(17);
        box.setPadding(dp(20), dp(20), dp(20), dp(10));
        if (bm != null) {
            ImageView $this$showQr_u24lambda_u2448 = new ImageView(this.act);
            $this$showQr_u24lambda_u2448.setImageBitmap(bm);
            box.addView($this$showQr_u24lambda_u2448, new LinearLayout.LayoutParams(size, size));
        }
        TextView $this$showQr_u24lambda_u2449 = new TextView(this.act);
        $this$showQr_u24lambda_u2449.setText(url);
        $this$showQr_u24lambda_u2449.setTextSize(13.0f);
        $this$showQr_u24lambda_u2449.setPadding(0, dp(12), 0, 0);
        $this$showQr_u24lambda_u2449.setGravity(17);
        $this$showQr_u24lambda_u2449.setTextIsSelectable(true);
        box.addView($this$showQr_u24lambda_u2449);
        new AlertDialog.Builder(this.act).setTitle(title).setView(box).setPositiveButton("Close", (DialogInterface.OnClickListener) null).show();
    }

    public static /* synthetic */ void refresh$default(DevicePanel devicePanel, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        devicePanel.refresh(z);
    }

    public final void refresh(boolean force) {
        Telemetry t = getT();
        int gen = t != null ? t.getParamsGeneration() : -1;
        if (force || gen != this.lastGen) {
            this.lastGen = gen;
            Iterator<Function0<Unit>> it = this.updaters.iterator();
            Intrinsics.checkNotNullExpressionValue(it, "iterator(...)");
            while (it.hasNext()) {
                Function0 next = it.next();
                Intrinsics.checkNotNullExpressionValue(next, "next(...)");
                Function0 u = next;
                u.invoke();
            }
        }
    }

    public final void onTick() {
        refresh$default(this, false, 1, null);
        if (Intrinsics.areEqual(this.kind, "network") && connected()) {
            this.pollTick++;
            if (this.pollTick % 6 == 1) {
                call$default(this, "wifi_status", null, new Function1() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda120
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        Unit onTick$lambda$50;
                        onTick$lambda$50 = DevicePanel.onTick$lambda$50(DevicePanel.this, (JSONObject) obj);
                        return onTick$lambda$50;
                    }
                }, 2, null);
            }
            if (this.pollTick % 10 == 5 && this.offline.optBoolean("on")) {
                call$default(this, "offline", null, new Function1() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda131
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        Unit onTick$lambda$51;
                        onTick$lambda$51 = DevicePanel.onTick$lambda$51(DevicePanel.this, (JSONObject) obj);
                        return onTick$lambda$51;
                    }
                }, 2, null);
            }
            if (this.pollTick % 20 == 2) {
                scan(false);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onTick$lambda$50(DevicePanel this$0, JSONObject st) {
        Intrinsics.checkNotNullParameter(st, "st");
        this$0.applyWifiStatus(st);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onTick$lambda$51(DevicePanel this$0, JSONObject r) {
        Intrinsics.checkNotNullParameter(r, "r");
        if (r.optBoolean("ok")) {
            this$0.offline = r;
        }
        return Unit.INSTANCE;
    }

    private final void buildToggles() {
        note("Same toggles as the comma's own Toggles panel. Toggles that restart openpilot are disabled while engaged.");
        toggle$default(this, "Enable openpilot", buildToggles$restartDesc("Use the openpilot system for adaptive cruise control and lane keep driver assistance. Your attention is required at all times to use this feature."), new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda8
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean buildToggles$lambda$52;
                buildToggles$lambda$52 = DevicePanel.buildToggles$lambda$52(DevicePanel.this);
                return Boolean.valueOf(buildToggles$lambda$52);
            }
        }, new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda20
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean buildToggles$lambda$53;
                buildToggles$lambda$53 = DevicePanel.buildToggles$lambda$53(DevicePanel.this);
                return Boolean.valueOf(buildToggles$lambda$53);
            }
        }, null, null, new Function2() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda29
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit buildToggles$lambda$54;
                buildToggles$lambda$54 = DevicePanel.buildToggles$lambda$54(DevicePanel.this, ((Boolean) obj).booleanValue(), (Switch) obj2);
                return buildToggles$lambda$54;
            }
        }, 48, null);
        toggle$default(this, "Safe Mode", buildToggles$restartDesc("Temporarily force driving-affecting StarPilot settings back to safe defaults, stock tuning, and the branch default model until disabled."), new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda30
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean buildToggles$lambda$55;
                buildToggles$lambda$55 = DevicePanel.buildToggles$lambda$55(DevicePanel.this);
                return Boolean.valueOf(buildToggles$lambda$55);
            }
        }, new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda31
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean buildToggles$lambda$56;
                buildToggles$lambda$56 = DevicePanel.buildToggles$lambda$56(DevicePanel.this);
                return Boolean.valueOf(buildToggles$lambda$56);
            }
        }, null, null, new Function2() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda32
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit buildToggles$lambda$57;
                buildToggles$lambda$57 = DevicePanel.buildToggles$lambda$57(DevicePanel.this, ((Boolean) obj).booleanValue(), (Switch) obj2);
                return buildToggles$lambda$57;
            }
        }, 48, null);
        toggle$default(this, "Disengage on Accelerator Pedal", "When enabled, pressing the accelerator pedal will disengage openpilot.", new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda34
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean buildToggles$lambda$58;
                buildToggles$lambda$58 = DevicePanel.buildToggles$lambda$58(DevicePanel.this);
                return Boolean.valueOf(buildToggles$lambda$58);
            }
        }, new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda35
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean buildToggles$lambda$59;
                buildToggles$lambda$59 = DevicePanel.buildToggles$lambda$59(DevicePanel.this);
                return Boolean.valueOf(buildToggles$lambda$59);
            }
        }, null, null, new Function2() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda36
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit buildToggles$lambda$60;
                buildToggles$lambda$60 = DevicePanel.buildToggles$lambda$60(DevicePanel.this, ((Boolean) obj).booleanValue(), (Switch) obj2);
                return buildToggles$lambda$60;
            }
        }, 48, null);
        multi("Driving Personality", this.personalityDesc, CollectionsKt.listOf(new String[]{"Aggressive", "Standard", "Relaxed"}), new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda37
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int buildToggles$lambda$61;
                buildToggles$lambda$61 = DevicePanel.buildToggles$lambda$61(DevicePanel.this);
                return Integer.valueOf(buildToggles$lambda$61);
            }
        }, new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda9
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean buildToggles$lambda$62;
                buildToggles$lambda$62 = DevicePanel.buildToggles$lambda$62(DevicePanel.this);
                return Boolean.valueOf(buildToggles$lambda$62);
            }
        }, new Function1() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda10
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit buildToggles$lambda$63;
                buildToggles$lambda$63 = DevicePanel.buildToggles$lambda$63(DevicePanel.this, ((Integer) obj).intValue());
                return buildToggles$lambda$63;
            }
        });
        toggle$default(this, "Enable Lane Departure Warnings", "Receive alerts to steer back into the lane when your vehicle drifts over a detected lane line without a turn signal activated while driving over 31 mph (50 km/h).", new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda12
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean buildToggles$lambda$64;
                buildToggles$lambda$64 = DevicePanel.buildToggles$lambda$64(DevicePanel.this);
                return Boolean.valueOf(buildToggles$lambda$64);
            }
        }, new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda13
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean buildToggles$lambda$65;
                buildToggles$lambda$65 = DevicePanel.buildToggles$lambda$65(DevicePanel.this);
                return Boolean.valueOf(buildToggles$lambda$65);
            }
        }, null, null, new Function2() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda14
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit buildToggles$lambda$66;
                buildToggles$lambda$66 = DevicePanel.buildToggles$lambda$66(DevicePanel.this, ((Boolean) obj).booleanValue(), (Switch) obj2);
                return buildToggles$lambda$66;
            }
        }, 48, null);
        toggle$default(this, "Always-On Driver Monitoring", "Enable driver monitoring even when openpilot is not engaged.", new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda15
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean buildToggles$lambda$67;
                buildToggles$lambda$67 = DevicePanel.buildToggles$lambda$67(DevicePanel.this);
                return Boolean.valueOf(buildToggles$lambda$67);
            }
        }, new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda16
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean buildToggles$lambda$68;
                buildToggles$lambda$68 = DevicePanel.buildToggles$lambda$68(DevicePanel.this);
                return Boolean.valueOf(buildToggles$lambda$68);
            }
        }, null, null, new Function2() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda17
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit buildToggles$lambda$69;
                buildToggles$lambda$69 = DevicePanel.buildToggles$lambda$69(DevicePanel.this, ((Boolean) obj).booleanValue(), (Switch) obj2);
                return buildToggles$lambda$69;
            }
        }, 48, null);
        toggle$default(this, "Record and Upload Driver Camera", buildToggles$restartDesc("Upload data from the driver facing camera and help improve the driver monitoring algorithm."), new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda18
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean buildToggles$lambda$70;
                buildToggles$lambda$70 = DevicePanel.buildToggles$lambda$70(DevicePanel.this);
                return Boolean.valueOf(buildToggles$lambda$70);
            }
        }, new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda19
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean buildToggles$lambda$71;
                buildToggles$lambda$71 = DevicePanel.buildToggles$lambda$71(DevicePanel.this);
                return Boolean.valueOf(buildToggles$lambda$71);
            }
        }, null, null, new Function2() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda21
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit buildToggles$lambda$72;
                buildToggles$lambda$72 = DevicePanel.buildToggles$lambda$72(DevicePanel.this, ((Boolean) obj).booleanValue(), (Switch) obj2);
                return buildToggles$lambda$72;
            }
        }, 48, null);
        toggle$default(this, "Record and Upload Microphone Audio", buildToggles$restartDesc("Record and store microphone audio while driving. The audio will be included in the dashcam video in comma connect."), new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda23
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean buildToggles$lambda$73;
                buildToggles$lambda$73 = DevicePanel.buildToggles$lambda$73(DevicePanel.this);
                return Boolean.valueOf(buildToggles$lambda$73);
            }
        }, new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda24
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean buildToggles$lambda$74;
                buildToggles$lambda$74 = DevicePanel.buildToggles$lambda$74(DevicePanel.this);
                return Boolean.valueOf(buildToggles$lambda$74);
            }
        }, null, null, new Function2() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda25
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit buildToggles$lambda$75;
                buildToggles$lambda$75 = DevicePanel.buildToggles$lambda$75(DevicePanel.this, ((Boolean) obj).booleanValue(), (Switch) obj2);
                return buildToggles$lambda$75;
            }
        }, 48, null);
        toggle$default(this, "Use Metric System", "Display speed in km/h instead of mph.", new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda26
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean buildToggles$lambda$76;
                buildToggles$lambda$76 = DevicePanel.buildToggles$lambda$76(DevicePanel.this);
                return Boolean.valueOf(buildToggles$lambda$76);
            }
        }, new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda27
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean buildToggles$lambda$77;
                buildToggles$lambda$77 = DevicePanel.buildToggles$lambda$77(DevicePanel.this);
                return Boolean.valueOf(buildToggles$lambda$77);
            }
        }, null, null, new Function2() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda28
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit buildToggles$lambda$78;
                buildToggles$lambda$78 = DevicePanel.buildToggles$lambda$78(DevicePanel.this, ((Boolean) obj).booleanValue(), (Switch) obj2);
                return buildToggles$lambda$78;
            }
        }, 48, null);
    }

    private static final String buildToggles$restartDesc(String base) {
        return base + " Changing this setting will restart openpilot if the car is powered on.";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean buildToggles$lambda$52(DevicePanel this$0) {
        return this$0.p("OpenpilotEnabledToggle");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean buildToggles$lambda$53(DevicePanel this$0) {
        return (this$0.engaged() || this$0.locked("OpenpilotEnabledToggle")) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildToggles$lambda$54(DevicePanel this$0, boolean v, Switch r10) {
        Intrinsics.checkNotNullParameter(r10, "<unused var>");
        setParam$default(this$0, "OpenpilotEnabledToggle", Boolean.valueOf(v), false, null, 12, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean buildToggles$lambda$55(DevicePanel this$0) {
        return this$0.p("SafeMode");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean buildToggles$lambda$56(DevicePanel this$0) {
        return (this$0.engaged() || this$0.locked("SafeMode")) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildToggles$lambda$57(DevicePanel this$0, boolean v, Switch r10) {
        Intrinsics.checkNotNullParameter(r10, "<unused var>");
        setParam$default(this$0, "SafeMode", Boolean.valueOf(v), false, null, 12, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean buildToggles$lambda$58(DevicePanel this$0) {
        return this$0.p("DisengageOnAccelerator");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean buildToggles$lambda$59(DevicePanel this$0) {
        return !this$0.locked("DisengageOnAccelerator");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildToggles$lambda$60(DevicePanel this$0, boolean v, Switch r10) {
        Intrinsics.checkNotNullParameter(r10, "<unused var>");
        setParam$default(this$0, "DisengageOnAccelerator", Boolean.valueOf(v), false, null, 12, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int buildToggles$lambda$61(DevicePanel this$0) {
        Telemetry t = this$0.getT();
        if (t != null) {
            return t.pInt("LongitudinalPersonality", 1);
        }
        return 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean buildToggles$lambda$62(DevicePanel this$0) {
        return !this$0.p("SafeMode") && this$0.i("has_longitudinal_control");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildToggles$lambda$63(DevicePanel this$0, int idx) {
        setParam$default(this$0, "LongitudinalPersonality", Integer.valueOf(idx), false, null, 12, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean buildToggles$lambda$64(DevicePanel this$0) {
        return this$0.p("IsLdwEnabled");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean buildToggles$lambda$65(DevicePanel this$0) {
        return !this$0.locked("IsLdwEnabled");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildToggles$lambda$66(DevicePanel this$0, boolean v, Switch r10) {
        Intrinsics.checkNotNullParameter(r10, "<unused var>");
        setParam$default(this$0, "IsLdwEnabled", Boolean.valueOf(v), false, null, 12, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean buildToggles$lambda$67(DevicePanel this$0) {
        return this$0.p("AlwaysOnDM");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean buildToggles$lambda$68(DevicePanel this$0) {
        return !this$0.locked("AlwaysOnDM");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildToggles$lambda$69(DevicePanel this$0, boolean v, Switch r10) {
        Intrinsics.checkNotNullParameter(r10, "<unused var>");
        setParam$default(this$0, "AlwaysOnDM", Boolean.valueOf(v), false, null, 12, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean buildToggles$lambda$70(DevicePanel this$0) {
        return this$0.p("RecordFront");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean buildToggles$lambda$71(DevicePanel this$0) {
        return (this$0.engaged() || this$0.locked("RecordFront")) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildToggles$lambda$72(DevicePanel this$0, boolean v, Switch r10) {
        Intrinsics.checkNotNullParameter(r10, "<unused var>");
        setParam$default(this$0, "RecordFront", Boolean.valueOf(v), false, null, 12, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean buildToggles$lambda$73(DevicePanel this$0) {
        return this$0.p("RecordAudio");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean buildToggles$lambda$74(DevicePanel this$0) {
        return (this$0.engaged() || this$0.locked("RecordAudio")) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildToggles$lambda$75(DevicePanel this$0, boolean v, Switch r10) {
        Intrinsics.checkNotNullParameter(r10, "<unused var>");
        setParam$default(this$0, "RecordAudio", Boolean.valueOf(v), false, null, 12, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean buildToggles$lambda$76(DevicePanel this$0) {
        return this$0.p("IsMetric");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean buildToggles$lambda$77(DevicePanel this$0) {
        return !this$0.locked("IsMetric");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildToggles$lambda$78(DevicePanel this$0, boolean v, Switch r10) {
        Intrinsics.checkNotNullParameter(r10, "<unused var>");
        setParam$default(this$0, "IsMetric", Boolean.valueOf(v), false, null, 12, null);
        return Unit.INSTANCE;
    }

    private final void buildDevice() {
        text("Dongle ID", new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda22
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                String buildDevice$lambda$80;
                buildDevice$lambda$80 = DevicePanel.buildDevice$lambda$80(DevicePanel.this);
                return buildDevice$lambda$80;
            }
        });
        text("Serial", new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda90
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                String buildDevice$lambda$82;
                buildDevice$lambda$82 = DevicePanel.buildDevice$lambda$82(DevicePanel.this);
                return buildDevice$lambda$82;
            }
        });
        button$default(this, "Pair Device", new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda92
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                String buildDevice$lambda$83;
                buildDevice$lambda$83 = DevicePanel.buildDevice$lambda$83();
                return buildDevice$lambda$83;
            }
        }, "Pair your device with comma connect (connect.comma.ai) and claim your comma prime offer.", null, null, new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda93
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean buildDevice$lambda$84;
                buildDevice$lambda$84 = DevicePanel.buildDevice$lambda$84(DevicePanel.this);
                return Boolean.valueOf(buildDevice$lambda$84);
            }
        }, null, new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda94
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildDevice$lambda$86;
                buildDevice$lambda$86 = DevicePanel.buildDevice$lambda$86(DevicePanel.this);
                return buildDevice$lambda$86;
            }
        }, 88, null);
        button$default(this, "Pair this tablet", new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda95
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                String buildDevice$lambda$87;
                buildDevice$lambda$87 = DevicePanel.buildDevice$lambda$87();
                return buildDevice$lambda$87;
            }
        }, "Scan the StarView QR code on the comma (Settings › starview › show qr) so this tablet also works over Wi-Fi and after the comma's \"usb needs key\" is turned on.", null, null, null, null, new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda96
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildDevice$lambda$88;
                buildDevice$lambda$88 = DevicePanel.buildDevice$lambda$88(DevicePanel.this);
                return buildDevice$lambda$88;
            }
        }, 120, null);
        button$default(this, "Driver Camera", new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda97
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                String buildDevice$lambda$89;
                buildDevice$lambda$89 = DevicePanel.buildDevice$lambda$89();
                return buildDevice$lambda$89;
            }
        }, "Preview the driver facing camera to ensure that driver monitoring has good visibility.", null, null, null, null, new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda99
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildDevice$lambda$90;
                buildDevice$lambda$90 = DevicePanel.buildDevice$lambda$90(DevicePanel.this);
                return buildDevice$lambda$90;
            }
        }, 120, null);
        button$default(this, "Reset Driver Monitoring", new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda100
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                String buildDevice$lambda$91;
                buildDevice$lambda$91 = DevicePanel.buildDevice$lambda$91();
                return buildDevice$lambda$91;
            }
        }, "Clears the saved driver monitoring wheel-side calibration and any manual right-hand-driving override.", null, new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda33
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean buildDevice$lambda$92;
                buildDevice$lambda$92 = DevicePanel.buildDevice$lambda$92(DevicePanel.this);
                return Boolean.valueOf(buildDevice$lambda$92);
            }
        }, null, null, new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda44
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildDevice$lambda$94;
                buildDevice$lambda$94 = DevicePanel.buildDevice$lambda$94(DevicePanel.this);
                return buildDevice$lambda$94;
            }
        }, 104, null);
        button$default(this, "Reset Calibration", new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda55
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                String buildDevice$lambda$95;
                buildDevice$lambda$95 = DevicePanel.buildDevice$lambda$95();
                return buildDevice$lambda$95;
            }
        }, null, null, new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda66
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean buildDevice$lambda$96;
                buildDevice$lambda$96 = DevicePanel.buildDevice$lambda$96(DevicePanel.this);
                return Boolean.valueOf(buildDevice$lambda$96);
            }
        }, null, new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda77
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                String buildDevice$lambda$97;
                buildDevice$lambda$97 = DevicePanel.buildDevice$lambda$97(DevicePanel.this);
                return buildDevice$lambda$97;
            }
        }, new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda84
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildDevice$lambda$99;
                buildDevice$lambda$99 = DevicePanel.buildDevice$lambda$99(DevicePanel.this);
                return buildDevice$lambda$99;
            }
        }, 44, null);
        button$default(this, "Regulatory", new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda85
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                String buildDevice$lambda$100;
                buildDevice$lambda$100 = DevicePanel.buildDevice$lambda$100();
                return buildDevice$lambda$100;
            }
        }, null, null, null, null, null, new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda86
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildDevice$lambda$102;
                buildDevice$lambda$102 = DevicePanel.buildDevice$lambda$102(DevicePanel.this);
                return buildDevice$lambda$102;
            }
        }, 124, null);
        dual("Reboot", "Power Off", new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda88
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildDevice$lambda$104;
                buildDevice$lambda$104 = DevicePanel.buildDevice$lambda$104(DevicePanel.this);
                return buildDevice$lambda$104;
            }
        }, new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda89
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildDevice$lambda$106;
                buildDevice$lambda$106 = DevicePanel.buildDevice$lambda$106(DevicePanel.this);
                return buildDevice$lambda$106;
            }
        }, new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda91
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean buildDevice$lambda$107;
                buildDevice$lambda$107 = DevicePanel.buildDevice$lambda$107(DevicePanel.this);
                return Boolean.valueOf(buildDevice$lambda$107);
            }
        });
        note("Review Training Guide and Change Language only exist on the comma's own screen.");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String buildDevice$lambda$80(DevicePanel this$0) {
        String pStr = this$0.pStr("DongleId");
        if (pStr.length() == 0) {
            pStr = "N/A";
        }
        return pStr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String buildDevice$lambda$82(DevicePanel this$0) {
        String pStr = this$0.pStr("HardwareSerial");
        if (pStr.length() == 0) {
            pStr = "N/A";
        }
        return pStr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String buildDevice$lambda$83() {
        return "PAIR";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean buildDevice$lambda$84(DevicePanel this$0) {
        return !this$0.i("comma_paired");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildDevice$lambda$86(final DevicePanel this$0) {
        call$default(this$0, "comma_pair_url", null, new Function1() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda138
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit buildDevice$lambda$86$lambda$85;
                buildDevice$lambda$86$lambda$85 = DevicePanel.buildDevice$lambda$86$lambda$85(this$0, (JSONObject) obj);
                return buildDevice$lambda$86$lambda$85;
            }
        }, 2, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildDevice$lambda$86$lambda$85(DevicePanel this$0, JSONObject r) {
        Intrinsics.checkNotNullParameter(r, "r");
        if (r.optBoolean("ok")) {
            String optString = r.optString("url");
            Intrinsics.checkNotNullExpressionValue(optString, "optString(...)");
            this$0.showQr("Scan with the comma connect app", optString);
        } else {
            this$0.ackToast(r);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String buildDevice$lambda$87() {
        return Link.INSTANCE.getTermKey().length() > 0 ? "RE-PAIR" : "PAIR";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildDevice$lambda$88(DevicePanel this$0) {
        PairScanner.INSTANCE.scan(this$0.act, () -> Unit.INSTANCE);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String buildDevice$lambda$89() {
        return "PREVIEW";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildDevice$lambda$90(DevicePanel this$0) {
        this$0.showDriverCamera();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String buildDevice$lambda$91() {
        return "RESET";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean buildDevice$lambda$92(DevicePanel this$0) {
        return !this$0.engaged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildDevice$lambda$94(final DevicePanel this$0) {
        confirm$default(this$0, "Are you sure you want to reset driver monitoring calibration?", "Reset", false, new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda153
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildDevice$lambda$94$lambda$93;
                buildDevice$lambda$94$lambda$93 = DevicePanel.buildDevice$lambda$94$lambda$93(this$0);
                return buildDevice$lambda$94$lambda$93;
            }
        }, 4, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildDevice$lambda$94$lambda$93(DevicePanel this$0) {
        call$default(this$0, "reset_dm", null, null, 6, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String buildDevice$lambda$95() {
        return "RESET";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean buildDevice$lambda$96(DevicePanel this$0) {
        return !this$0.engaged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String buildDevice$lambda$97(DevicePanel this$0) {
        String str;
        Telemetry t = this$0.getT();
        if (t == null || (str = Telemetry.iStr$default(t, "calib", null, 2, null)) == null) {
            str = "";
        }
        return "openpilot requires the device to be mounted within 4° left or right and within 5° up or 9° down. " + str + "\n\nopenpilot is continuously calibrating, resetting is rarely required. Resetting calibration will restart openpilot if the car is powered on.";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildDevice$lambda$99(final DevicePanel this$0) {
        confirm$default(this$0, "Are you sure you want to reset calibration?", "Reset", false, new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda157
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildDevice$lambda$99$lambda$98;
                buildDevice$lambda$99$lambda$98 = DevicePanel.buildDevice$lambda$99$lambda$98(this$0);
                return buildDevice$lambda$99$lambda$98;
            }
        }, 4, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildDevice$lambda$99$lambda$98(DevicePanel this$0) {
        call$default(this$0, "reset_calibration", null, null, 6, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String buildDevice$lambda$100() {
        return "VIEW";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildDevice$lambda$102(DevicePanel this$0) {
        String h = this$0.getHost();
        if (h == null) {
            return Unit.INSTANCE;
        }
        WebView $this$buildDevice_u24lambda_u24102_u24lambda_u24101 = new WebView(this$0.act);
        $this$buildDevice_u24lambda_u24102_u24lambda_u24101.getSettings().setJavaScriptEnabled(false);
        $this$buildDevice_u24lambda_u24102_u24lambda_u24101.loadUrl("http://" + h + ":8090/regulatory");
        new AlertDialog.Builder(this$0.act).setView($this$buildDevice_u24lambda_u24102_u24lambda_u24101).setPositiveButton("Close", (DialogInterface.OnClickListener) null).show();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildDevice$lambda$104(final DevicePanel this$0) {
        confirm$default(this$0, "Are you sure you want to reboot?", "Reboot", false, new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda50
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildDevice$lambda$104$lambda$103;
                buildDevice$lambda$104$lambda$103 = DevicePanel.buildDevice$lambda$104$lambda$103(this$0);
                return buildDevice$lambda$104$lambda$103;
            }
        }, 4, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildDevice$lambda$104$lambda$103(DevicePanel this$0) {
        call$default(this$0, "reboot", null, null, 6, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildDevice$lambda$106(final DevicePanel this$0) {
        confirm$default(this$0, "Are you sure you want to power off?", "Power Off", false, new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda172
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildDevice$lambda$106$lambda$105;
                buildDevice$lambda$106$lambda$105 = DevicePanel.buildDevice$lambda$106$lambda$105(this$0);
                return buildDevice$lambda$106$lambda$105;
            }
        }, 4, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildDevice$lambda$106$lambda$105(DevicePanel this$0) {
        call$default(this$0, "power_off", null, null, 6, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean buildDevice$lambda$107(DevicePanel this$0) {
        return !this$0.onroad();
    }

    private final void showDriverCamera() {
        final String h = getHost();
        if (h == null) {
            DevicePanel $this$showDriverCamera_u24lambda_u24108 = this;
            $this$showDriverCamera_u24lambda_u24108.toast("Not connected");
            return;
        }
        SurfaceView sv = new SurfaceView(this.act);
        final TextView $this$showDriverCamera_u24lambda_u24109 = new TextView(this.act);
        $this$showDriverCamera_u24lambda_u24109.setText("connecting to driver camera…");
        $this$showDriverCamera_u24lambda_u24109.setTextColor(this.dim);
        $this$showDriverCamera_u24lambda_u24109.setPadding(dp(12), dp(8), dp(12), dp(8));
        LinearLayout $this$showDriverCamera_u24lambda_u24110 = new LinearLayout(this.act);
        $this$showDriverCamera_u24lambda_u24110.setOrientation(1);
        $this$showDriverCamera_u24lambda_u24110.addView(sv, new LinearLayout.LayoutParams(dp(640), dp(400)));
        $this$showDriverCamera_u24lambda_u24110.addView($this$showDriverCamera_u24lambda_u24109);
        final VideoClient vc = new VideoClient(new Function1() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit showDriverCamera$lambda$112;
                showDriverCamera$lambda$112 = DevicePanel.showDriverCamera$lambda$112(DevicePanel.this, $this$showDriverCamera_u24lambda_u24109, (String) obj);
                return showDriverCamera$lambda$112;
            }
        }, new Function2() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit showDriverCamera$lambda$113;
                showDriverCamera$lambda$113 = DevicePanel.showDriverCamera$lambda$113(((Integer) obj).intValue(), ((Integer) obj2).intValue());
                return showDriverCamera$lambda$113;
            }
        });
        sv.getHolder().addCallback(new SurfaceHolder.Callback() { // from class: com.starpilot.starview.DevicePanel$showDriverCamera$1
            @Override // android.view.SurfaceHolder.Callback
            public void surfaceCreated(SurfaceHolder holder) {
                Intrinsics.checkNotNullParameter(holder, "holder");
                vc.connect(h, 8090, "driver", holder.getSurface());
            }

            @Override // android.view.SurfaceHolder.Callback
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                Intrinsics.checkNotNullParameter(holder, "holder");
            }

            @Override // android.view.SurfaceHolder.Callback
            public void surfaceDestroyed(SurfaceHolder holder) {
                Intrinsics.checkNotNullParameter(holder, "holder");
                vc.close();
            }
        });
        new AlertDialog.Builder(this.act).setTitle("Driver camera").setView($this$showDriverCamera_u24lambda_u24110).setPositiveButton("Close", (DialogInterface.OnClickListener) null).setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda4
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                vc.close();
            }
        }).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit showDriverCamera$lambda$112(DevicePanel this$0, final TextView $status, final String s) {
        Intrinsics.checkNotNullParameter(s, "s");
        this$0.act.runOnUiThread(new Runnable() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda87
            @Override // java.lang.Runnable
            public final void run() {
                DevicePanel.showDriverCamera$lambda$112$lambda$111($status, s);
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showDriverCamera$lambda$112$lambda$111(TextView $status, String $s) {
        $status.setText($s);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit showDriverCamera$lambda$113(int i, int i2) {
        return Unit.INSTANCE;
    }

    private final JSONObject door() {
        return this.offline.optJSONObject("door");
    }

    private final void applyWifiStatus(JSONObject st) {
        String str;
        this.wifi = st;
        String ssid = st.optString("ssid");
        String ip = st.optString("ip");
        TextView textView = this.netStatus;
        int i = 0;
        if (st.optBoolean("tethering")) {
            str = "Tethering active (" + st.optString("tether_ssid") + ") · " + ip;
        } else if (!st.optBoolean("connecting")) {
            Intrinsics.checkNotNull(ssid);
            str = ssid.length() > 0 ? "Connected to " + ssid + " · " + ip : "Not connected to Wi-Fi";
        }
        textView.setText(str);
        this.syncing = true;
        Switch r2 = this.teth;
        if (r2 != null) {
            r2.setChecked(st.optBoolean("tethering"));
        }
        Switch r22 = this.teth;
        if (r22 != null) {
            r22.setEnabled(true);
        }
        this.syncing = false;
        String optString = st.optString("metered");
        if (Intrinsics.areEqual(optString, "yes")) {
            i = 1;
        } else if (Intrinsics.areEqual(optString, "no")) {
            i = 2;
        }
        this.meteredSel = i;
        refresh(true);
    }

    private final void scan(boolean rescan) {
        if (this.scanning) {
            return;
        }
        this.scanning = true;
        call("wifi_scan", MapsKt.mapOf(TuplesKt.to("rescan", Boolean.valueOf(rescan))), new Function1() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda174
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit scan$lambda$127;
                scan$lambda$127 = DevicePanel.scan$lambda$127(DevicePanel.this, (JSONObject) obj);
                return scan$lambda$127;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit scan$lambda$127(final DevicePanel this$0, JSONObject r) {
        Intrinsics.checkNotNullParameter(r, "r");
        this$0.scanning = false;
        if (!r.optBoolean("ok")) {
            this$0.ackToast(r);
            return Unit.INSTANCE;
        }
        this$0.applyWifiStatus(r);
        this$0.netList.removeAllViews();
        JSONArray arr = r.optJSONArray("networks");
        if (arr == null) {
            return Unit.INSTANCE;
        }
        int k = 0;
        int length = arr.length();
        while (k < length) {
            JSONObject n = arr.getJSONObject(k);
            final String ssid = n.optString("ssid");
            boolean conn = n.optBoolean("connected");
            final boolean saved = n.optBoolean("saved");
            final boolean sec = n.optBoolean("secured");
            LinearLayout row = this$0.card();
            int s = n.optInt("strength");
            int i = length;
            int q = s > 75 ? 4 : s > 50 ? 3 : s > 25 ? 2 : 1;
            String bars = "▂▄▆█".substring(0, q);
            Intrinsics.checkNotNullExpressionValue(bars, "substring(...)");
            String str = (sec ? "🔒 " : "") + ssid;
            String[] strArr = new String[2];
            String str2 = null;
            strArr[0] = conn ? "connected" : null;
            strArr[1] = (!saved || conn) ? null : "saved";
            String joinToString$default = CollectionsKt.joinToString(CollectionsKt.listOfNotNull((Object[]) strArr), " · ", "", "", -1, "...", null);
            if (!(joinToString$default.length() == 0)) {
                str2 = joinToString$default;
            }
            row.addView(this$0.titleBlock(str, str2), new LinearLayout.LayoutParams(0, -2, 1.0f));
            TextView $this$scan_u24lambda_u24127_u24lambda_u24119 = new TextView(this$0.act);
            $this$scan_u24lambda_u24127_u24lambda_u24119.setText(bars);
            $this$scan_u24lambda_u24127_u24lambda_u24119.setTextColor(conn ? Color.rgb(111, 227, 161) : this$0.dim);
            $this$scan_u24lambda_u24127_u24lambda_u24119.setTextSize(16.0f);
            $this$scan_u24lambda_u24127_u24lambda_u24119.setPadding(0, 0, this$0.dp(14), 0);
            row.addView($this$scan_u24lambda_u24127_u24lambda_u24119);
            if (saved || conn) {
                Button $this$scan_u24lambda_u24127_u24lambda_u24123 = new Button(this$0.act);
                $this$scan_u24lambda_u24127_u24lambda_u24123.setText("FORGET");
                $this$scan_u24lambda_u24127_u24lambda_u24123.setTextSize(12.0f);
                $this$scan_u24lambda_u24127_u24lambda_u24123.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda6
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        DevicePanel.scan$lambda$127$lambda$123$lambda$122(this$0, ssid, view);
                    }
                });
                row.addView($this$scan_u24lambda_u24127_u24lambda_u24123);
            }
            if (!conn) {
                Button $this$scan_u24lambda_u24127_u24lambda_u24125 = new Button(this$0.act);
                $this$scan_u24lambda_u24127_u24lambda_u24125.setText("CONNECT");
                $this$scan_u24lambda_u24127_u24lambda_u24125.setTextSize(12.0f);
                $this$scan_u24lambda_u24127_u24lambda_u24125.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda7
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        DevicePanel.scan$lambda$127$lambda$125$lambda$124(this$0, ssid, sec, saved, view);
                    }
                });
                LinearLayout.LayoutParams $this$scan_u24lambda_u24127_u24lambda_u24126 = new LinearLayout.LayoutParams(-2, -2);
                $this$scan_u24lambda_u24127_u24lambda_u24126.leftMargin = this$0.dp(6);
                Unit unit = Unit.INSTANCE;
                row.addView($this$scan_u24lambda_u24127_u24lambda_u24125, $this$scan_u24lambda_u24127_u24lambda_u24126);
            }
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-1, -2);
            lp.bottomMargin = this$0.dp(6);
            this$0.netList.addView(row, lp);
            k++;
            length = i;
        }
        if (arr.length() == 0) {
            this$0.netList.addView(this$0.noteView("No networks found."));
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void scan$lambda$127$lambda$123$lambda$122(final DevicePanel this$0, final String $ssid, View it) {
        confirm$default(this$0, "Forget Wi-Fi network \"" + $ssid + "\"?", "Forget", false, new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda156
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit scan$lambda$127$lambda$123$lambda$122$lambda$121;
                scan$lambda$127$lambda$123$lambda$122$lambda$121 = DevicePanel.scan$lambda$127$lambda$123$lambda$122$lambda$121(this$0, $ssid);
                return scan$lambda$127$lambda$123$lambda$122$lambda$121;
            }
        }, 4, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit scan$lambda$127$lambda$123$lambda$122$lambda$121(final DevicePanel this$0, String $ssid) {
        this$0.call("wifi_forget", MapsKt.mapOf(TuplesKt.to("ssid", $ssid)), new Function1() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda41
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit scan$lambda$127$lambda$123$lambda$122$lambda$121$lambda$120;
                scan$lambda$127$lambda$123$lambda$122$lambda$121$lambda$120 = DevicePanel.scan$lambda$127$lambda$123$lambda$122$lambda$121$lambda$120(this$0, (JSONObject) obj);
                return scan$lambda$127$lambda$123$lambda$122$lambda$121$lambda$120;
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit scan$lambda$127$lambda$123$lambda$122$lambda$121$lambda$120(DevicePanel this$0, JSONObject it) {
        Intrinsics.checkNotNullParameter(it, "it");
        this$0.scan(false);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void scan$lambda$127$lambda$125$lambda$124(DevicePanel this$0, String $ssid, boolean $sec, boolean $saved, View it) {
        Intrinsics.checkNotNull($ssid);
        this$0.connectWifi($ssid, $sec && !$saved, false);
    }

    private static final void connectWifi$go(final DevicePanel this$0, final String $ssid, final boolean $hidden, String pw) {
        this$0.netStatus.setText("Connecting to " + $ssid + "…");
        this$0.call("wifi_connect", MapsKt.mapOf(TuplesKt.to("ssid", $ssid), TuplesKt.to("password", pw), TuplesKt.to("hidden", Boolean.valueOf($hidden))), new Function1() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit connectWifi$go$lambda$129;
                connectWifi$go$lambda$129 = DevicePanel.connectWifi$go$lambda$129(this$0, $ssid, $hidden, (JSONObject) obj);
                return connectWifi$go$lambda$129;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit connectWifi$go$lambda$129(final DevicePanel this$0, final String $ssid, final boolean $hidden, JSONObject r) {
        Intrinsics.checkNotNullParameter(r, "r");
        if (r.optBoolean("ok")) {
            this$0.toast("Connected to " + $ssid);
        } else if (r.optBoolean("need_auth")) {
            input$default(this$0, "Enter password", "for \"" + $ssid + "\"", null, true, 8, new Function1() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    Unit connectWifi$go$lambda$129$lambda$128;
                    connectWifi$go$lambda$129$lambda$128 = DevicePanel.connectWifi$go$lambda$129$lambda$128(this$0, $ssid, $hidden, (String) obj);
                    return connectWifi$go$lambda$129$lambda$128;
                }
            }, 4, null);
        } else {
            this$0.ackToast(r);
        }
        this$0.scan(false);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit connectWifi$go$lambda$129$lambda$128(DevicePanel this$0, String $ssid, boolean $hidden, String it) {
        Intrinsics.checkNotNullParameter(it, "it");
        connectWifi$go(this$0, $ssid, $hidden, it);
        return Unit.INSTANCE;
    }

    private final void connectWifi(final String ssid, boolean askPassword, final boolean hidden) {
        if (askPassword) {
            input$default(this, "Enter password", "for \"" + ssid + "\"", null, true, 8, new Function1() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda169
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    Unit connectWifi$lambda$130;
                    connectWifi$lambda$130 = DevicePanel.connectWifi$lambda$130(DevicePanel.this, ssid, hidden, (String) obj);
                    return connectWifi$lambda$130;
                }
            }, 4, null);
        } else {
            connectWifi$go(this, ssid, hidden, "");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit connectWifi$lambda$130(DevicePanel this$0, String $ssid, boolean $hidden, String it) {
        Intrinsics.checkNotNullParameter(it, "it");
        connectWifi$go(this$0, $ssid, $hidden, it);
        return Unit.INSTANCE;
    }

    private final void buildNetwork() {
        this.col.addView(this.netStatus);
        LinearLayout $this$buildNetwork_u24lambda_u24131 = new LinearLayout(this.act);
        $this$buildNetwork_u24lambda_u24131.setOrientation(0);
        $this$buildNetwork_u24lambda_u24131.setGravity(16);
        TextView $this$buildNetwork_u24lambda_u24132 = new TextView(this.act);
        $this$buildNetwork_u24lambda_u24132.setText("WI-FI NETWORKS");
        $this$buildNetwork_u24lambda_u24132.setTextColor(this.dim);
        $this$buildNetwork_u24lambda_u24132.setTextSize(13.0f);
        $this$buildNetwork_u24lambda_u24132.setLetterSpacing(0.1f);
        $this$buildNetwork_u24lambda_u24132.setPadding(dp(4), 0, 0, 0);
        $this$buildNetwork_u24lambda_u24131.addView($this$buildNetwork_u24lambda_u24132, new LinearLayout.LayoutParams(0, -2, 1.0f));
        Button $this$buildNetwork_u24lambda_u24134 = new Button(this.act);
        $this$buildNetwork_u24lambda_u24134.setText("RESCAN");
        $this$buildNetwork_u24lambda_u24134.setTextSize(12.0f);
        $this$buildNetwork_u24lambda_u24134.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda104
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DevicePanel.buildNetwork$lambda$134$lambda$133(DevicePanel.this, view);
            }
        });
        $this$buildNetwork_u24lambda_u24131.addView($this$buildNetwork_u24lambda_u24134);
        this.col.addView($this$buildNetwork_u24lambda_u24131);
        this.col.addView(this.netList);
        header("Privacy");
        toggle$default(this, "Comma offline", null, new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda116
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean buildNetwork$lambda$135;
                buildNetwork$lambda$135 = DevicePanel.buildNetwork$lambda$135(DevicePanel.this);
                return Boolean.valueOf(buildNetwork$lambda$135);
            }
        }, new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda128
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean buildNetwork$lambda$136;
                buildNetwork$lambda$136 = DevicePanel.buildNetwork$lambda$136(DevicePanel.this);
                return Boolean.valueOf(buildNetwork$lambda$136);
            }
        }, null, new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda130
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                String buildNetwork$lambda$137;
                buildNetwork$lambda$137 = DevicePanel.buildNetwork$lambda$137(DevicePanel.this);
                return buildNetwork$lambda$137;
            }
        }, new Function2() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda132
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit buildNetwork$lambda$139;
                buildNetwork$lambda$139 = DevicePanel.buildNetwork$lambda$139(DevicePanel.this, ((Boolean) obj).booleanValue(), (Switch) obj2);
                return buildNetwork$lambda$139;
            }
        }, 16, null);
        call$default(this, "offline", null, new Function1() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda133
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit buildNetwork$lambda$140;
                buildNetwork$lambda$140 = DevicePanel.buildNetwork$lambda$140(DevicePanel.this, (JSONObject) obj);
                return buildNetwork$lambda$140;
            }
        }, 2, null);
        Function0 doorShown = new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda134
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean buildNetwork$lambda$141;
                buildNetwork$lambda$141 = DevicePanel.buildNetwork$lambda$141(DevicePanel.this);
                return Boolean.valueOf(buildNetwork$lambda$141);
            }
        };
        toggle$default(this, "Close after downloads", null, new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda135
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean buildNetwork$lambda$142;
                buildNetwork$lambda$142 = DevicePanel.buildNetwork$lambda$142(DevicePanel.this);
                return Boolean.valueOf(buildNetwork$lambda$142);
            }
        }, null, doorShown, new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda136
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                String buildNetwork$lambda$143;
                buildNetwork$lambda$143 = DevicePanel.buildNetwork$lambda$143(DevicePanel.this);
                return buildNetwork$lambda$143;
            }
        }, new Function2() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda137
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit buildNetwork$lambda$145;
                buildNetwork$lambda$145 = DevicePanel.buildNetwork$lambda$145(DevicePanel.this, ((Boolean) obj).booleanValue(), (Switch) obj2);
                return buildNetwork$lambda$145;
            }
        }, 8, null);
        button$default(this, "Download door", new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda105
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                String buildNetwork$lambda$146;
                buildNetwork$lambda$146 = DevicePanel.buildNetwork$lambda$146();
                return buildNetwork$lambda$146;
            }
        }, null, null, new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda106
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean buildNetwork$lambda$147;
                buildNetwork$lambda$147 = DevicePanel.buildNetwork$lambda$147(DevicePanel.this);
                return Boolean.valueOf(buildNetwork$lambda$147);
            }
        }, doorShown, new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda107
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                String buildNetwork$lambda$149;
                buildNetwork$lambda$149 = DevicePanel.buildNetwork$lambda$149(DevicePanel.this);
                return buildNetwork$lambda$149;
            }
        }, new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda108
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildNetwork$lambda$151;
                buildNetwork$lambda$151 = DevicePanel.buildNetwork$lambda$151(DevicePanel.this);
                return buildNetwork$lambda$151;
            }
        }, 12, null);
        header("Advanced");
        LinearLayout tRow = toggle$default(this, "Enable Tethering", null, new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda110
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean buildNetwork$lambda$152;
                buildNetwork$lambda$152 = DevicePanel.buildNetwork$lambda$152(DevicePanel.this);
                return Boolean.valueOf(buildNetwork$lambda$152);
            }
        }, null, null, null, new Function2() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda111
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit buildNetwork$lambda$155;
                buildNetwork$lambda$155 = DevicePanel.buildNetwork$lambda$155(DevicePanel.this, ((Boolean) obj).booleanValue(), (Switch) obj2);
                return buildNetwork$lambda$155;
            }
        }, 56, null);
        View childAt = tRow.getChildAt(tRow.getChildCount() - 1);
        Intrinsics.checkNotNull(childAt, "null cannot be cast to non-null type android.widget.Switch");
        this.teth = (Switch) childAt;
        button$default(this, "Tethering Password", new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda112
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                String buildNetwork$lambda$156;
                buildNetwork$lambda$156 = DevicePanel.buildNetwork$lambda$156();
                return buildNetwork$lambda$156;
            }
        }, null, null, null, null, null, new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda113
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildNetwork$lambda$159;
                buildNetwork$lambda$159 = DevicePanel.buildNetwork$lambda$159(DevicePanel.this);
                return buildNetwork$lambda$159;
            }
        }, 124, null);
        text("IP Address", new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda114
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                String buildNetwork$lambda$161;
                buildNetwork$lambda$161 = DevicePanel.buildNetwork$lambda$161(DevicePanel.this);
                return buildNetwork$lambda$161;
            }
        });
        toggle$default(this, "Enable Roaming", null, new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda115
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean buildNetwork$lambda$162;
                buildNetwork$lambda$162 = DevicePanel.buildNetwork$lambda$162(DevicePanel.this);
                return Boolean.valueOf(buildNetwork$lambda$162);
            }
        }, null, null, null, new Function2() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda117
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit buildNetwork$lambda$163;
                buildNetwork$lambda$163 = DevicePanel.buildNetwork$lambda$163(DevicePanel.this, ((Boolean) obj).booleanValue(), (Switch) obj2);
                return buildNetwork$lambda$163;
            }
        }, 56, null);
        button$default(this, "APN Setting", new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda118
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                String buildNetwork$lambda$164;
                buildNetwork$lambda$164 = DevicePanel.buildNetwork$lambda$164();
                return buildNetwork$lambda$164;
            }
        }, null, new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda119
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                String buildNetwork$lambda$166;
                buildNetwork$lambda$166 = DevicePanel.buildNetwork$lambda$166(DevicePanel.this);
                return buildNetwork$lambda$166;
            }
        }, null, null, null, new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda121
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildNetwork$lambda$168;
                buildNetwork$lambda$168 = DevicePanel.buildNetwork$lambda$168(DevicePanel.this);
                return buildNetwork$lambda$168;
            }
        }, 116, null);
        toggle$default(this, "Cellular Metered", "Prevent large data uploads when on a metered cellular connection", new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda122
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean buildNetwork$lambda$169;
                buildNetwork$lambda$169 = DevicePanel.buildNetwork$lambda$169(DevicePanel.this);
                return Boolean.valueOf(buildNetwork$lambda$169);
            }
        }, null, null, null, new Function2() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda123
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit buildNetwork$lambda$170;
                buildNetwork$lambda$170 = DevicePanel.buildNetwork$lambda$170(DevicePanel.this, ((Boolean) obj).booleanValue(), (Switch) obj2);
                return buildNetwork$lambda$170;
            }
        }, 56, null);
        multi("Wi-Fi Network Metered", "Prevent large data uploads when on a metered Wi-Fi connection", CollectionsKt.listOf(new String[]{"default", "metered", "unmetered"}), new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda124
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int i;
                i = DevicePanel.this.meteredSel;
                return Integer.valueOf(i);
            }
        }, new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda125
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean buildNetwork$lambda$172;
                buildNetwork$lambda$172 = DevicePanel.buildNetwork$lambda$172(DevicePanel.this);
                return Boolean.valueOf(buildNetwork$lambda$172);
            }
        }, new Function1() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda126
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit buildNetwork$lambda$175;
                buildNetwork$lambda$175 = DevicePanel.buildNetwork$lambda$175(DevicePanel.this, ((Integer) obj).intValue());
                return buildNetwork$lambda$175;
            }
        });
        button$default(this, "Hidden Network", new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda127
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                String buildNetwork$lambda$176;
                buildNetwork$lambda$176 = DevicePanel.buildNetwork$lambda$176();
                return buildNetwork$lambda$176;
            }
        }, null, null, null, null, null, new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda129
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildNetwork$lambda$180;
                buildNetwork$lambda$180 = DevicePanel.buildNetwork$lambda$180(DevicePanel.this);
                return buildNetwork$lambda$180;
            }
        }, 124, null);
        note("The tablet's own USB link is not affected by these settings.");
        if (connected()) {
            this.netStatus.setText("Scanning…");
            scan(false);
        } else {
            this.netStatus.setText("Not connected to the comma.");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void buildNetwork$lambda$134$lambda$133(DevicePanel this$0, View it) {
        this$0.netStatus.setText("Scanning…");
        this$0.scan(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean buildNetwork$lambda$135(DevicePanel this$0) {
        return this$0.offline.optBoolean("on");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean buildNetwork$lambda$136(DevicePanel this$0) {
        return this$0.offline.optBoolean("ok");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String buildNetwork$lambda$137(DevicePanel this$0) {
        if (!this$0.offline.has("ok")) {
            return "Checking…";
        }
        if (!this$0.offline.optBoolean("ok")) {
            return "Needs offline.py and the comma update for this switch (" + this$0.offline.optString("error") + ")";
        }
        if (this$0.offline.optBoolean("on")) {
            return "On: the comma reaches only the tablet, the phone, GitHub (updates) and Mapbox (maps, traffic). No uploads, no comma connect, no telemetry." + (this$0.offline.optBoolean("boot") ? " Stays on after a restart." : " Boot service missing: it won't come back after a restart.");
        }
        return "Off: the comma uses the internet normally. Turn on to block everything except the tablet, the phone and GitHub updates.";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildNetwork$lambda$139(final DevicePanel this$0, boolean v, Switch sw) {
        Intrinsics.checkNotNullParameter(sw, "sw");
        sw.setEnabled(false);
        this$0.call("offline", MapsKt.mapOf(TuplesKt.to("on", Boolean.valueOf(v))), new Function1() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda109
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit buildNetwork$lambda$139$lambda$138;
                buildNetwork$lambda$139$lambda$138 = DevicePanel.buildNetwork$lambda$139$lambda$138(this$0, (JSONObject) obj);
                return buildNetwork$lambda$139$lambda$138;
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildNetwork$lambda$139$lambda$138(DevicePanel this$0, JSONObject r) {
        Intrinsics.checkNotNullParameter(r, "r");
        if (r.optBoolean("ok")) {
            this$0.offline = r;
            this$0.toast(r.optBoolean("on") ? "Comma offline" : "Comma online");
        } else {
            this$0.ackToast(r);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildNetwork$lambda$140(DevicePanel this$0, JSONObject r) {
        Intrinsics.checkNotNullParameter(r, "r");
        this$0.offline = r;
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean buildNetwork$lambda$141(DevicePanel this$0) {
        return this$0.offline.optBoolean("ok") && this$0.offline.optBoolean("on") && this$0.door() != null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean buildNetwork$lambda$142(DevicePanel this$0) {
        JSONObject door = this$0.door();
        if (door != null) {
            return door.optBoolean("autoClose", true);
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String buildNetwork$lambda$143(DevicePanel this$0) {
        JSONObject door = this$0.door();
        boolean z = false;
        if (door != null && !door.optBoolean("autoClose", true)) {
            z = true;
        }
        return !z ? "On: Hugging Face is let through only while a model or sound download runs, then shut again." : "Off: after a download Hugging Face stays reachable until you tap Close now (or an hour passes).";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildNetwork$lambda$145(final DevicePanel this$0, boolean v, Switch sw) {
        Intrinsics.checkNotNullParameter(sw, "sw");
        sw.setEnabled(false);
        this$0.call("offline_door", MapsKt.mapOf(TuplesKt.to("autoClose", Boolean.valueOf(v))), new Function1() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda45
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit buildNetwork$lambda$145$lambda$144;
                buildNetwork$lambda$145$lambda$144 = DevicePanel.buildNetwork$lambda$145$lambda$144(this$0, (JSONObject) obj);
                return buildNetwork$lambda$145$lambda$144;
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildNetwork$lambda$145$lambda$144(DevicePanel this$0, JSONObject r) {
        Intrinsics.checkNotNullParameter(r, "r");
        if (r.optBoolean("ok")) {
            this$0.offline.put("door", r.optJSONObject("door"));
        } else {
            this$0.ackToast(r);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String buildNetwork$lambda$146() {
        return "CLOSE NOW";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean buildNetwork$lambda$147(DevicePanel this$0) {
        JSONObject door = this$0.door();
        return door != null && door.optBoolean("open");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String buildNetwork$lambda$149(DevicePanel this$0) {
        JSONObject d = this$0.door();
        final JSONArray downloads = d != null ? d.optJSONArray("downloads") : null;
        String what = (downloads == null || downloads.length() <= 0) ? "" : CollectionsKt.joinToString(RangesKt.until(0, downloads.length()), " and ", "", "", -1, "...", new Function1() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda167
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                CharSequence buildNetwork$lambda$149$lambda$148;
                buildNetwork$lambda$149$lambda$148 = DevicePanel.buildNetwork$lambda$149$lambda$148(downloads, ((Integer) obj).intValue());
                return buildNetwork$lambda$149$lambda$148;
            }
        });
        if (d == null) {
            return "";
        }
        if (!d.optBoolean("open")) {
            return "Closed. It opens by itself when you refresh or download models or sounds.";
        }
        if (what.length() > 0) {
            return "Open: downloading " + what + " (Hugging Face only).";
        }
        if (d.isNull("closesIn")) {
            return "Open (Hugging Face only).";
        }
        return "Open (Hugging Face only), closes by itself in " + ((d.optInt("closesIn") + 59) / 60) + " min.";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CharSequence buildNetwork$lambda$149$lambda$148(JSONArray $downloads, int it) {
        String optString = $downloads.optString(it);
        Intrinsics.checkNotNullExpressionValue(optString, "optString(...)");
        return optString;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildNetwork$lambda$151(final DevicePanel this$0) {
        this$0.call("offline_door", MapsKt.mapOf(TuplesKt.to("close", true)), new Function1() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda101
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit buildNetwork$lambda$151$lambda$150;
                buildNetwork$lambda$151$lambda$150 = DevicePanel.buildNetwork$lambda$151$lambda$150(this$0, (JSONObject) obj);
                return buildNetwork$lambda$151$lambda$150;
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildNetwork$lambda$151$lambda$150(DevicePanel this$0, JSONObject r) {
        Intrinsics.checkNotNullParameter(r, "r");
        if (r.optBoolean("ok")) {
            this$0.offline.put("door", r.optJSONObject("door"));
            this$0.toast("Download door closed");
        } else {
            this$0.ackToast(r);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean buildNetwork$lambda$152(DevicePanel this$0) {
        return this$0.wifi.optBoolean("tethering");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildNetwork$lambda$155(final DevicePanel this$0, boolean v, Switch sw) {
        Intrinsics.checkNotNullParameter(sw, "sw");
        sw.setEnabled(false);
        this$0.call("tether", MapsKt.mapOf(TuplesKt.to("on", Boolean.valueOf(v))), new Function1() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda98
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit buildNetwork$lambda$155$lambda$154;
                buildNetwork$lambda$155$lambda$154 = DevicePanel.buildNetwork$lambda$155$lambda$154(this$0, (JSONObject) obj);
                return buildNetwork$lambda$155$lambda$154;
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildNetwork$lambda$155$lambda$154(final DevicePanel this$0, JSONObject r) {
        Intrinsics.checkNotNullParameter(r, "r");
        this$0.ackToast(r);
        call$default(this$0, "wifi_status", null, new Function1() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda46
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit buildNetwork$lambda$155$lambda$154$lambda$153;
                buildNetwork$lambda$155$lambda$154$lambda$153 = DevicePanel.buildNetwork$lambda$155$lambda$154$lambda$153(this$0, (JSONObject) obj);
                return buildNetwork$lambda$155$lambda$154$lambda$153;
            }
        }, 2, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildNetwork$lambda$155$lambda$154$lambda$153(DevicePanel this$0, JSONObject it) {
        Intrinsics.checkNotNullParameter(it, "it");
        this$0.applyWifiStatus(it);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String buildNetwork$lambda$156() {
        return "EDIT";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildNetwork$lambda$159(final DevicePanel this$0) {
        call$default(this$0, "tether_password", null, new Function1() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda103
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit buildNetwork$lambda$159$lambda$158;
                buildNetwork$lambda$159$lambda$158 = DevicePanel.buildNetwork$lambda$159$lambda$158(this$0, (JSONObject) obj);
                return buildNetwork$lambda$159$lambda$158;
            }
        }, 2, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildNetwork$lambda$159$lambda$158(final DevicePanel this$0, JSONObject r) {
        Intrinsics.checkNotNullParameter(r, "r");
        String optString = r.optString("password");
        Intrinsics.checkNotNullExpressionValue(optString, "optString(...)");
        input$default(this$0, "Enter new tethering password", "min 8 characters", optString, false, 8, new Function1() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda159
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit buildNetwork$lambda$159$lambda$158$lambda$157;
                buildNetwork$lambda$159$lambda$158$lambda$157 = DevicePanel.buildNetwork$lambda$159$lambda$158$lambda$157(this$0, (String) obj);
                return buildNetwork$lambda$159$lambda$158$lambda$157;
            }
        }, 8, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildNetwork$lambda$159$lambda$158$lambda$157(DevicePanel this$0, String pw) {
        Intrinsics.checkNotNullParameter(pw, "pw");
        call$default(this$0, "tether_password", MapsKt.mapOf(TuplesKt.to("password", pw)), null, 4, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String buildNetwork$lambda$161(DevicePanel this$0) {
        String optString = this$0.wifi.optString("ip");
        if (optString.length() == 0) {
            optString = "—";
        }
        return optString;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean buildNetwork$lambda$162(DevicePanel this$0) {
        return this$0.p("GsmRoaming");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildNetwork$lambda$163(DevicePanel this$0, boolean v, Switch r10) {
        Intrinsics.checkNotNullParameter(r10, "<unused var>");
        setParam$default(this$0, "GsmRoaming", Boolean.valueOf(v), false, null, 12, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String buildNetwork$lambda$164() {
        return "EDIT";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String buildNetwork$lambda$166(DevicePanel this$0) {
        String pStr = this$0.pStr("GsmApn");
        if (pStr.length() == 0) {
            pStr = "automatic";
        }
        return pStr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildNetwork$lambda$168(final DevicePanel this$0) {
        input$default(this$0, "Enter APN", "leave blank for automatic configuration", this$0.pStr("GsmApn"), false, 0, new Function1() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda42
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit buildNetwork$lambda$168$lambda$167;
                buildNetwork$lambda$168$lambda$167 = DevicePanel.buildNetwork$lambda$168$lambda$167(this$0, (String) obj);
                return buildNetwork$lambda$168$lambda$167;
            }
        }, 24, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildNetwork$lambda$168$lambda$167(DevicePanel this$0, String apn) {
        Intrinsics.checkNotNullParameter(apn, "apn");
        setParam$default(this$0, "GsmApn", apn, false, null, 12, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean buildNetwork$lambda$169(DevicePanel this$0) {
        return this$0.p("GsmMetered");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildNetwork$lambda$170(DevicePanel this$0, boolean v, Switch r10) {
        Intrinsics.checkNotNullParameter(r10, "<unused var>");
        setParam$default(this$0, "GsmMetered", Boolean.valueOf(v), false, null, 12, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean buildNetwork$lambda$172(DevicePanel this$0) {
        String optString = this$0.wifi.optString("ssid");
        Intrinsics.checkNotNullExpressionValue(optString, "optString(...)");
        return (optString.length() > 0) && !this$0.wifi.optBoolean("tethering");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildNetwork$lambda$175(final DevicePanel this$0, int idx) {
        this$0.call("wifi_metered", MapsKt.mapOf(TuplesKt.to("mode", CollectionsKt.listOf(new String[]{"unknown", "yes", "no"}).get(idx))), new Function1() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda168
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit buildNetwork$lambda$175$lambda$174;
                buildNetwork$lambda$175$lambda$174 = DevicePanel.buildNetwork$lambda$175$lambda$174(this$0, (JSONObject) obj);
                return buildNetwork$lambda$175$lambda$174;
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildNetwork$lambda$175$lambda$174(final DevicePanel this$0, JSONObject r) {
        Intrinsics.checkNotNullParameter(r, "r");
        this$0.ackToast(r);
        call$default(this$0, "wifi_status", null, new Function1() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit buildNetwork$lambda$175$lambda$174$lambda$173;
                buildNetwork$lambda$175$lambda$174$lambda$173 = DevicePanel.buildNetwork$lambda$175$lambda$174$lambda$173(this$0, (JSONObject) obj);
                return buildNetwork$lambda$175$lambda$174$lambda$173;
            }
        }, 2, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildNetwork$lambda$175$lambda$174$lambda$173(DevicePanel this$0, JSONObject it) {
        Intrinsics.checkNotNullParameter(it, "it");
        this$0.applyWifiStatus(it);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String buildNetwork$lambda$176() {
        return "CONNECT";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildNetwork$lambda$180(final DevicePanel this$0) {
        input$default(this$0, "Enter SSID", "network name", null, false, 0, new Function1() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda102
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit buildNetwork$lambda$180$lambda$179;
                buildNetwork$lambda$180$lambda$179 = DevicePanel.buildNetwork$lambda$180$lambda$179(this$0, (String) obj);
                return buildNetwork$lambda$180$lambda$179;
            }
        }, 28, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildNetwork$lambda$180$lambda$179(final DevicePanel this$0, final String ssid) {
        Intrinsics.checkNotNullParameter(ssid, "ssid");
        input$default(this$0, "Enter password", "leave blank for an open network", null, true, 0, new Function1() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda161
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit buildNetwork$lambda$180$lambda$179$lambda$178;
                buildNetwork$lambda$180$lambda$179$lambda$178 = DevicePanel.buildNetwork$lambda$180$lambda$179$lambda$178(this$0, ssid, (String) obj);
                return buildNetwork$lambda$180$lambda$179$lambda$178;
            }
        }, 20, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildNetwork$lambda$180$lambda$179$lambda$178(final DevicePanel this$0, String $ssid, String pw) {
        Intrinsics.checkNotNullParameter(pw, "pw");
        this$0.netStatus.setText("Connecting to " + $ssid + "…");
        this$0.call("wifi_connect", MapsKt.mapOf(TuplesKt.to("ssid", $ssid), TuplesKt.to("password", pw), TuplesKt.to("hidden", true)), new Function1() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda160
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit buildNetwork$lambda$180$lambda$179$lambda$178$lambda$177;
                buildNetwork$lambda$180$lambda$179$lambda$178$lambda$177 = DevicePanel.buildNetwork$lambda$180$lambda$179$lambda$178$lambda$177(this$0, (JSONObject) obj);
                return buildNetwork$lambda$180$lambda$179$lambda$178$lambda$177;
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildNetwork$lambda$180$lambda$179$lambda$178$lambda$177(DevicePanel this$0, JSONObject r) {
        Intrinsics.checkNotNullParameter(r, "r");
        this$0.ackToast(r);
        this$0.scan(false);
        return Unit.INSTANCE;
    }

    private final void buildDeveloper() {
        toggle$default(this, "Enable ADB", "ADB (Android Debug Bridge) allows connecting to your device over USB or over the network. See https://docs.comma.ai/how-to/connect-to-comma for more info.", new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda51
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean buildDeveloper$lambda$181;
                buildDeveloper$lambda$181 = DevicePanel.buildDeveloper$lambda$181(DevicePanel.this);
                return Boolean.valueOf(buildDeveloper$lambda$181);
            }
        }, new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda60
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean buildDeveloper$lambda$182;
                buildDeveloper$lambda$182 = DevicePanel.buildDeveloper$lambda$182(DevicePanel.this);
                return Boolean.valueOf(buildDeveloper$lambda$182);
            }
        }, null, null, new Function2() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda61
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit buildDeveloper$lambda$183;
                buildDeveloper$lambda$183 = DevicePanel.buildDeveloper$lambda$183(DevicePanel.this, ((Boolean) obj).booleanValue(), (Switch) obj2);
                return buildDeveloper$lambda$183;
            }
        }, 48, null);
        toggle$default(this, "Enable SSH", null, new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda62
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean buildDeveloper$lambda$184;
                buildDeveloper$lambda$184 = DevicePanel.buildDeveloper$lambda$184(DevicePanel.this);
                return Boolean.valueOf(buildDeveloper$lambda$184);
            }
        }, null, null, null, new Function2() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda63
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit buildDeveloper$lambda$185;
                buildDeveloper$lambda$185 = DevicePanel.buildDeveloper$lambda$185(DevicePanel.this, ((Boolean) obj).booleanValue(), (Switch) obj2);
                return buildDeveloper$lambda$185;
            }
        }, 56, null);
        button$default(this, "SSH Keys", new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda64
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                String buildDeveloper$lambda$186;
                buildDeveloper$lambda$186 = DevicePanel.buildDeveloper$lambda$186(DevicePanel.this);
                return buildDeveloper$lambda$186;
            }
        }, "Warning: This grants SSH access to all public keys in your GitHub settings. Never enter a GitHub username other than your own. A comma employee will NEVER ask you to add their GitHub username.", new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda65
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                String buildDeveloper$lambda$187;
                buildDeveloper$lambda$187 = DevicePanel.buildDeveloper$lambda$187(DevicePanel.this);
                return buildDeveloper$lambda$187;
            }
        }, null, null, null, new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda67
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildDeveloper$lambda$190;
                buildDeveloper$lambda$190 = DevicePanel.buildDeveloper$lambda$190(DevicePanel.this);
                return buildDeveloper$lambda$190;
            }
        }, 112, null);
        toggle$default(this, "Joystick Debug Mode", null, new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda68
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean buildDeveloper$lambda$191;
                buildDeveloper$lambda$191 = DevicePanel.buildDeveloper$lambda$191(DevicePanel.this);
                return Boolean.valueOf(buildDeveloper$lambda$191);
            }
        }, new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda69
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean buildDeveloper$lambda$192;
                buildDeveloper$lambda$192 = DevicePanel.buildDeveloper$lambda$192(DevicePanel.this);
                return Boolean.valueOf(buildDeveloper$lambda$192);
            }
        }, null, null, new Function2() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda52
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit buildDeveloper$lambda$193;
                buildDeveloper$lambda$193 = DevicePanel.buildDeveloper$lambda$193(DevicePanel.this, ((Boolean) obj).booleanValue(), (Switch) obj2);
                return buildDeveloper$lambda$193;
            }
        }, 48, null);
        toggle$default(this, "openpilot Longitudinal Control (Alpha)", "WARNING: openpilot longitudinal control is in alpha for this car and will disable Automatic Emergency Braking (AEB). On this car, openpilot defaults to the car's built-in ACC instead of openpilot's longitudinal control. Enable this to switch to openpilot longitudinal control. Enabling Experimental mode is recommended when enabling openpilot longitudinal control alpha. Changing this setting will restart openpilot if the car is powered on.", new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda53
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean buildDeveloper$lambda$194;
                buildDeveloper$lambda$194 = DevicePanel.buildDeveloper$lambda$194(DevicePanel.this);
                return Boolean.valueOf(buildDeveloper$lambda$194);
            }
        }, new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda54
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean buildDeveloper$lambda$195;
                buildDeveloper$lambda$195 = DevicePanel.buildDeveloper$lambda$195(DevicePanel.this);
                return Boolean.valueOf(buildDeveloper$lambda$195);
            }
        }, new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda56
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean buildDeveloper$lambda$196;
                buildDeveloper$lambda$196 = DevicePanel.buildDeveloper$lambda$196(DevicePanel.this);
                return Boolean.valueOf(buildDeveloper$lambda$196);
            }
        }, null, new Function2() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda57
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit buildDeveloper$lambda$198;
                buildDeveloper$lambda$198 = DevicePanel.buildDeveloper$lambda$198(DevicePanel.this, ((Boolean) obj).booleanValue(), (Switch) obj2);
                return buildDeveloper$lambda$198;
            }
        }, 32, null);
        toggle$default(this, "UI Debug Mode", "Shows touches and FPS on the comma's own screen.", new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda58
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean buildDeveloper$lambda$199;
                buildDeveloper$lambda$199 = DevicePanel.buildDeveloper$lambda$199(DevicePanel.this);
                return Boolean.valueOf(buildDeveloper$lambda$199);
            }
        }, null, null, null, new Function2() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda59
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit buildDeveloper$lambda$200;
                buildDeveloper$lambda$200 = DevicePanel.buildDeveloper$lambda$200(DevicePanel.this, ((Boolean) obj).booleanValue(), (Switch) obj2);
                return buildDeveloper$lambda$200;
            }
        }, 56, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean buildDeveloper$lambda$181(DevicePanel this$0) {
        return this$0.p("AdbEnabled");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean buildDeveloper$lambda$182(DevicePanel this$0) {
        return !this$0.onroad();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildDeveloper$lambda$183(DevicePanel this$0, boolean v, Switch r10) {
        Intrinsics.checkNotNullParameter(r10, "<unused var>");
        setParam$default(this$0, "AdbEnabled", Boolean.valueOf(v), false, null, 12, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean buildDeveloper$lambda$184(DevicePanel this$0) {
        return this$0.p("SshEnabled");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildDeveloper$lambda$185(DevicePanel this$0, boolean v, Switch r10) {
        Intrinsics.checkNotNullParameter(r10, "<unused var>");
        setParam$default(this$0, "SshEnabled", Boolean.valueOf(v), false, null, 12, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String buildDeveloper$lambda$186(DevicePanel this$0) {
        return this$0.i("has_ssh_keys") ? "REMOVE" : "ADD";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String buildDeveloper$lambda$187(DevicePanel this$0) {
        return this$0.pStr("GithubUsername");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildDeveloper$lambda$190(final DevicePanel this$0) {
        if (this$0.i("has_ssh_keys")) {
            call$default(this$0, "ssh_remove", null, null, 6, null);
        } else {
            input$default(this$0, "Enter your GitHub username", "username", null, false, 0, new Function1() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda80
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    Unit buildDeveloper$lambda$190$lambda$189;
                    buildDeveloper$lambda$190$lambda$189 = DevicePanel.buildDeveloper$lambda$190$lambda$189(this$0, (String) obj);
                    return buildDeveloper$lambda$190$lambda$189;
                }
            }, 28, null);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildDeveloper$lambda$190$lambda$189(final DevicePanel this$0, String u) {
        Intrinsics.checkNotNullParameter(u, "u");
        this$0.toast("Fetching keys…");
        this$0.call("ssh_add", MapsKt.mapOf(TuplesKt.to("username", u)), new Function1() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda165
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit buildDeveloper$lambda$190$lambda$189$lambda$188;
                buildDeveloper$lambda$190$lambda$189$lambda$188 = DevicePanel.buildDeveloper$lambda$190$lambda$189$lambda$188(this$0, (JSONObject) obj);
                return buildDeveloper$lambda$190$lambda$189$lambda$188;
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildDeveloper$lambda$190$lambda$189$lambda$188(DevicePanel this$0, JSONObject r) {
        Intrinsics.checkNotNullParameter(r, "r");
        if (r.optBoolean("ok")) {
            this$0.toast("Added " + r.optInt("keys") + " key(s) for " + r.optString("username"));
        } else {
            this$0.ackToast(r);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean buildDeveloper$lambda$191(DevicePanel this$0) {
        return this$0.p("JoystickDebugMode");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean buildDeveloper$lambda$192(DevicePanel this$0) {
        return !this$0.onroad();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildDeveloper$lambda$193(DevicePanel this$0, boolean v, Switch r10) {
        Intrinsics.checkNotNullParameter(r10, "<unused var>");
        setParam$default(this$0, "JoystickDebugMode", Boolean.valueOf(v), false, null, 12, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean buildDeveloper$lambda$194(DevicePanel this$0) {
        return this$0.p("AlphaLongitudinalEnabled");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean buildDeveloper$lambda$195(DevicePanel this$0) {
        return !this$0.engaged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean buildDeveloper$lambda$196(DevicePanel this$0) {
        return this$0.i("alpha_long_available");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildDeveloper$lambda$198(final DevicePanel this$0, boolean v, Switch sw) {
        Intrinsics.checkNotNullParameter(sw, "sw");
        if (v) {
            this$0.syncing = true;
            sw.setChecked(false);
            this$0.syncing = false;
            confirm$default(this$0, "Enable openpilot longitudinal control (alpha)? This disables AEB on this car.", "Enable", false, new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda47
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit buildDeveloper$lambda$198$lambda$197;
                    buildDeveloper$lambda$198$lambda$197 = DevicePanel.buildDeveloper$lambda$198$lambda$197(this$0);
                    return buildDeveloper$lambda$198$lambda$197;
                }
            }, 4, null);
        } else {
            setParam$default(this$0, "AlphaLongitudinalEnabled", false, false, null, 12, null);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildDeveloper$lambda$198$lambda$197(DevicePanel this$0) {
        setParam$default(this$0, "AlphaLongitudinalEnabled", true, false, null, 12, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean buildDeveloper$lambda$199(DevicePanel this$0) {
        return this$0.p("ShowDebugInfo");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildDeveloper$lambda$200(DevicePanel this$0, boolean v, Switch r10) {
        Intrinsics.checkNotNullParameter(r10, "<unused var>");
        setParam$default(this$0, "ShowDebugInfo", Boolean.valueOf(v), false, null, 12, null);
        return Unit.INSTANCE;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!Intrinsics.areEqual(this.kind, "software") || this.fastPolling) {
            return;
        }
        this.fastPolling = true;
        this.fastHandler.post(this.fastPoll);
        loadAuto();
    }

    @Override // android.widget.ScrollView, android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        this.fastPolling = false;
        this.fastHandler.removeCallbacks(this.fastPoll);
        super.onDetachedFromWindow();
    }

    private final void loadAuto() {
        Galaxy7 current = Galaxy7.Companion.current();
        if (current != null) {
            current.get("/api/software/status?history=0", new Function2() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda73
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    Unit loadAuto$lambda$202;
                    loadAuto$lambda$202 = DevicePanel.loadAuto$lambda$202(DevicePanel.this, (JSONObject) obj, (String) obj2);
                    return loadAuto$lambda$202;
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit loadAuto$lambda$202(DevicePanel this$0, JSONObject o, String str) {
        JSONObject ops;
        if (o != null && (ops = o.optJSONObject("operations")) != null) {
            this$0.autoDl = ops.isNull("automaticDownloads") ? null : Boolean.valueOf(ops.optBoolean("automaticDownloads"));
            this$0.autoCan = ops.optBoolean("canConfigure");
        }
        this$0.refresh(true);
        return Unit.INSTANCE;
    }

    private final void startFastUpdate() {
        if (Intrinsics.areEqual((Object) this.seven, (Object) true)) {
            call$default(this, "software_fast", null, new Function1() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda74
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    Unit startFastUpdate$lambda$203;
                    startFastUpdate$lambda$203 = DevicePanel.startFastUpdate$lambda$203(DevicePanel.this, (JSONObject) obj);
                    return startFastUpdate$lambda$203;
                }
            }, 2, null);
            return;
        }
        String h = getHost();
        if (h == null) {
            DevicePanel $this$startFastUpdate_u24lambda_u24204 = this;
            $this$startFastUpdate_u24lambda_u24204.toast("Not connected");
        } else {
            GalaxyApi.post$default(GalaxyApi.Companion.get(this.act, h), "/api/update/fast", null, new Function2() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda75
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    Unit startFastUpdate$lambda$205;
                    startFastUpdate$lambda$205 = DevicePanel.startFastUpdate$lambda$205(DevicePanel.this, (String) obj, (JSONObject) obj2);
                    return startFastUpdate$lambda$205;
                }
            }, 2, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit startFastUpdate$lambda$203(DevicePanel this$0, JSONObject r) {
        String optString;
        Intrinsics.checkNotNullParameter(r, "r");
        if (r.optBoolean("ok")) {
            optString = "Updating - the comma reboots when it's done";
        } else {
            optString = r.optString("error", "Update refused");
            Intrinsics.checkNotNullExpressionValue(optString, "optString(...)");
        }
        this$0.toast(optString);
        this$0.fastHandler.removeCallbacks(this$0.fastPoll);
        if (this$0.fastPolling) {
            this$0.fastHandler.post(this$0.fastPoll);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit startFastUpdate$lambda$205(DevicePanel this$0, String err, JSONObject jSONObject) {
        if (err != null) {
            this$0.toast(err);
        } else {
            this$0.toast("Updating - the comma reboots when it's done");
        }
        this$0.fastHandler.removeCallbacks(this$0.fastPoll);
        if (this$0.fastPolling) {
            this$0.fastHandler.post(this$0.fastPoll);
        }
        return Unit.INSTANCE;
    }

    private final void buildSoftware() {
        text("Current Version", new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda139
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                String buildSoftware$lambda$207;
                buildSoftware$lambda$207 = DevicePanel.buildSoftware$lambda$207(DevicePanel.this);
                return buildSoftware$lambda$207;
            }
        });
        text("Branch", new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda145
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                String buildSoftware$lambda$209;
                buildSoftware$lambda$209 = DevicePanel.buildSoftware$lambda$209(DevicePanel.this);
                return buildSoftware$lambda$209;
            }
        });
        button$default(this, "Update", new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda146
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                String buildSoftware$lambda$210;
                buildSoftware$lambda$210 = DevicePanel.buildSoftware$lambda$210(DevicePanel.this);
                return buildSoftware$lambda$210;
            }
        }, null, new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda147
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                String buildSoftware$lambda$211;
                buildSoftware$lambda$211 = DevicePanel.buildSoftware$lambda$211(DevicePanel.this);
                return buildSoftware$lambda$211;
            }
        }, new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda148
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean buildSoftware$lambda$212;
                buildSoftware$lambda$212 = DevicePanel.buildSoftware$lambda$212(DevicePanel.this);
                return Boolean.valueOf(buildSoftware$lambda$212);
            }
        }, null, new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda149
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                String buildSoftware$lambda$214;
                buildSoftware$lambda$214 = DevicePanel.buildSoftware$lambda$214(DevicePanel.this);
                return buildSoftware$lambda$214;
            }
        }, new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda150
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildSoftware$lambda$217;
                buildSoftware$lambda$217 = DevicePanel.buildSoftware$lambda$217(DevicePanel.this);
                return buildSoftware$lambda$217;
            }
        }, 36, null);
        toggle$default(this, "Automatically Install Updates", "Download and install updates by themselves while parked with an internet connection.", new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda151
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean buildSoftware$lambda$218;
                buildSoftware$lambda$218 = DevicePanel.buildSoftware$lambda$218(DevicePanel.this);
                return Boolean.valueOf(buildSoftware$lambda$218);
            }
        }, new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda152
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean buildSoftware$lambda$219;
                buildSoftware$lambda$219 = DevicePanel.buildSoftware$lambda$219(DevicePanel.this);
                return Boolean.valueOf(buildSoftware$lambda$219);
            }
        }, null, null, new Function2() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda154
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit buildSoftware$lambda$222;
                buildSoftware$lambda$222 = DevicePanel.buildSoftware$lambda$222(DevicePanel.this, ((Boolean) obj).booleanValue(), (Switch) obj2);
                return buildSoftware$lambda$222;
            }
        }, 48, null);
        button$default(this, "Error Log", new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda140
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                String buildSoftware$lambda$223;
                buildSoftware$lambda$223 = DevicePanel.buildSoftware$lambda$223();
                return buildSoftware$lambda$223;
            }
        }, null, null, null, null, null, new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda141
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildSoftware$lambda$226;
                buildSoftware$lambda$226 = DevicePanel.buildSoftware$lambda$226(DevicePanel.this);
                return buildSoftware$lambda$226;
            }
        }, 124, null);
        button$default(this, "Branches, versions & backup", new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda143
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                String buildSoftware$lambda$227;
                buildSoftware$lambda$227 = DevicePanel.buildSoftware$lambda$227();
                return buildSoftware$lambda$227;
            }
        }, "Galaxy's System Tools: switch branch, go back to an earlier version, back up or restore all StarPilot settings.", null, null, null, null, new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda144
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildSoftware$lambda$228;
                buildSoftware$lambda$228 = DevicePanel.buildSoftware$lambda$228(DevicePanel.this);
                return buildSoftware$lambda$228;
            }
        }, 120, null);
        note("Uninstall / factory reset stay on the comma's own screen on purpose.");
        header("Fork fixes waiting on upstream");
        LinearLayout fixes = new LinearLayout(this.act);
        fixes.setOrientation(1);
        LinearLayout $this$buildSoftware_u24lambda_u24229 = this.col;
        $this$buildSoftware_u24lambda_u24229.addView(fixes);
        loadForkFixes(fixes);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String buildSoftware$lambda$207(DevicePanel this$0) {
        String pStr = this$0.pStr("UpdaterCurrentDescription");
        if (pStr.length() == 0) {
            pStr = "—";
        }
        return pStr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String buildSoftware$lambda$209(DevicePanel this$0) {
        String pStr = this$0.pStr("GitBranch");
        if (pStr.length() == 0) {
            pStr = "—";
        }
        return pStr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String buildSoftware$lambda$210(DevicePanel this$0) {
        return FastUpdate.INSTANCE.running(this$0.fastStatus) ? "UPDATING" : "UPDATE";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String buildSoftware$lambda$214(DevicePanel this$0) {
        String pStr = this$0.pStr("GitBranch");
        if (pStr.length() == 0) {
            pStr = "branch";
        }
        return "Fast update, the same as Galaxy: gets the newest " + ((Object) pStr) + " and reboots. Anything applied only on the comma is replaced.";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String buildSoftware$lambda$211(DevicePanel this$0) {
        return FastUpdate.INSTANCE.summary(this$0.fastStatus);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean buildSoftware$lambda$212(DevicePanel this$0) {
        return (this$0.onroad() || FastUpdate.INSTANCE.running(this$0.fastStatus)) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildSoftware$lambda$217(final DevicePanel this$0) {
        String pStr = this$0.pStr("GitBranch");
        if (pStr.length() == 0) {
            pStr = "this branch";
        }
        confirm$default(this$0, "Update now? The comma downloads the newest version of " + ((Object) pStr) + " and reboots.", "Update", false, new Function0() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda38
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildSoftware$lambda$217$lambda$216;
                buildSoftware$lambda$217$lambda$216 = DevicePanel.buildSoftware$lambda$217$lambda$216(this$0);
                return buildSoftware$lambda$217$lambda$216;
            }
        }, 4, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildSoftware$lambda$217$lambda$216(DevicePanel this$0) {
        this$0.startFastUpdate();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean buildSoftware$lambda$218(DevicePanel this$0) {
        return Intrinsics.areEqual((Object) this$0.autoDl, (Object) true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean buildSoftware$lambda$219(DevicePanel this$0) {
        return this$0.autoCan && this$0.autoDl != null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildSoftware$lambda$222(final DevicePanel this$0, boolean v, Switch r5) {
        Intrinsics.checkNotNullParameter(r5, "<unused var>");
        Galaxy7 g = Galaxy7.Companion.current();
        if (g == null) {
            this$0.toast("Not connected");
            return Unit.INSTANCE;
        }
        JSONObject put = new JSONObject().put("action", "preferences").put("automaticDownloads", v);
        Object obj = this$0.autoDl;
        if (obj == null) {
            obj = JSONObject.NULL;
        }
        JSONObject put2 = put.put("expectedAutomaticDownloads", obj);
        Intrinsics.checkNotNullExpressionValue(put2, "put(...)");
        g.post("/api/software/action", put2, new Function2() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda162
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj2, Object obj3) {
                Unit buildSoftware$lambda$222$lambda$221;
                buildSoftware$lambda$222$lambda$221 = DevicePanel.buildSoftware$lambda$222$lambda$221(this$0, (JSONObject) obj2, (String) obj3);
                return buildSoftware$lambda$222$lambda$221;
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildSoftware$lambda$222$lambda$221(DevicePanel this$0, JSONObject jSONObject, String e) {
        if (e != null) {
            this$0.toast(e);
        }
        this$0.loadAuto();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String buildSoftware$lambda$223() {
        return "VIEW";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildSoftware$lambda$226(final DevicePanel this$0) {
        call$default(this$0, "error_log", null, new Function1() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda81
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit buildSoftware$lambda$226$lambda$225;
                buildSoftware$lambda$226$lambda$225 = DevicePanel.buildSoftware$lambda$226$lambda$225(this$0, (JSONObject) obj);
                return buildSoftware$lambda$226$lambda$225;
            }
        }, 2, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildSoftware$lambda$226$lambda$225(DevicePanel this$0, JSONObject r) {
        Intrinsics.checkNotNullParameter(r, "r");
        String optString = r.optString("text");
        if (optString.length() == 0) {
            optString = "No error log found.";
        }
        Intrinsics.checkNotNullExpressionValue(optString, "ifEmpty(...)");
        this$0.alert("Error log", optString);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String buildSoftware$lambda$227() {
        return "OPEN";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildSoftware$lambda$228(DevicePanel this$0) {
        Activity activity = this$0.act;
        SettingsActivity settingsActivity = activity instanceof SettingsActivity ? (SettingsActivity) activity : null;
        if (settingsActivity != null) {
            settingsActivity.openGalaxyWeb("/system", "Branches, versions & backup");
        }
        return Unit.INSTANCE;
    }

    private final void loadForkFixes(final LinearLayout box) {
        final String h = getHost();
        if (h == null) {
            return;
        }
        new Thread(new Runnable() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda48
            @Override // java.lang.Runnable
            public final void run() {
                DevicePanel.loadForkFixes$lambda$238(DevicePanel.this, h, box);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void loadForkFixes$lambda$238(final DevicePanel this$0, String $h, final LinearLayout $box) {
        Object m1191constructorimpl;
        List list;
        ResponseBody body;
        try {
            Result.Companion companion = Result.Companion;
            OkHttpClient http = new OkHttpClient.Builder().addInterceptor(Link.INSTANCE.getAuthInterceptor()).connectTimeout(4L, TimeUnit.SECONDS).readTimeout(4L, TimeUnit.SECONDS).build();
            Response execute = http.newCall(new Request.Builder().url("http://" + $h + ":8090/fs/get?path=" + Uri.encode("/data/openpilot/CLAUDE.md")).build()).execute();
            Throwable execute$c = null;
            try {
                Response r = execute;
                String string = (!r.isSuccessful() || (body = r.body()) == null) ? null : body.string();
                m1191constructorimpl = KtCompat.resultOf(string);
            } catch (Throwable execute$t) {
                execute$c = execute$t;
                throw execute$t;
            } finally {
                CloseableKt.closeFinally(execute, execute$c);
            }
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            m1191constructorimpl = KtCompat.resultOf(ResultKt.createFailure(th));
        }
        final String md = (String) (KtCompat.isFailure(m1191constructorimpl) ? null : m1191constructorimpl);
        if (md == null || (list = ForkFixes.INSTANCE.parse(md)) == null) {
            list = CollectionsKt.emptyList();
        }
        final var list$f = list;
        this$0.act.runOnUiThread(new Runnable() { // from class: com.starpilot.starview.DevicePanel$$ExternalSyntheticLambda158
            @Override // java.lang.Runnable
            public final void run() {
                DevicePanel.loadForkFixes$lambda$238$lambda$237($box, list$f, this$0, md);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void loadForkFixes$lambda$238$lambda$237(LinearLayout $box, List $list, DevicePanel this$0, String $md) {
        $box.removeAllViews();
        if ($list.isEmpty()) {
            $box.addView(this$0.noteView($md == null ? "Couldn't read the fork's notes from the comma." : "None: everything this fork fixed is fixed upstream."));
        }
        Iterator it = $list.iterator();
        int i = 0;
        while (it.hasNext()) {
            int i2 = i;
            i++;
            String f = (String) it.next();
            LinearLayout $this$loadForkFixes_u24lambda_u24238_u24lambda_u24237_u24lambda_u24235 = this$0.card();
            View it2 = this$0.chip(f);
            SvIcon.Chip chip = it2 instanceof SvIcon.Chip ? (SvIcon.Chip) it2 : null;
            if (chip != null) {
                SvIcon.Chip.set$default(chip, "wrench", null, null, 6, null);
            }
            LinearLayout.LayoutParams $this$loadForkFixes_u24lambda_u24238_u24lambda_u24237_u24lambda_u24235_u24lambda_u24234 = new LinearLayout.LayoutParams(this$0.dp(46), this$0.dp(46));
            $this$loadForkFixes_u24lambda_u24238_u24lambda_u24237_u24lambda_u24235_u24lambda_u24234.rightMargin = this$0.dp(16);
            Unit unit = Unit.INSTANCE;
            $this$loadForkFixes_u24lambda_u24238_u24lambda_u24237_u24lambda_u24235.addView(it2, $this$loadForkFixes_u24lambda_u24238_u24lambda_u24237_u24lambda_u24235_u24lambda_u24234);
            $this$loadForkFixes_u24lambda_u24238_u24lambda_u24237_u24lambda_u24235.addView(this$0.titleBlock((i2 + 1) + ". " + f, "Carried by this fork until upstream fixes it; dropped then."), new LinearLayout.LayoutParams(0, -2, 1.0f));
            LinearLayout.LayoutParams $this$loadForkFixes_u24lambda_u24238_u24lambda_u24237_u24lambda_u24236 = new LinearLayout.LayoutParams(-1, -2);
            $this$loadForkFixes_u24lambda_u24238_u24lambda_u24237_u24lambda_u24236.bottomMargin = this$0.dp(6);
            Unit unit2 = Unit.INSTANCE;
            $box.addView($this$loadForkFixes_u24lambda_u24238_u24lambda_u24237_u24lambda_u24235, $this$loadForkFixes_u24lambda_u24238_u24lambda_u24237_u24lambda_u24236);
        }
    }
}
