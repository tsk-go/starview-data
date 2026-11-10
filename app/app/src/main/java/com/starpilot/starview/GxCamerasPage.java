package com.starpilot.starview;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.starpilot.starview.GxPage;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.ranges.RangesKt;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.json.JSONArray;
import org.json.JSONObject;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup.LayoutParams;

/* compiled from: GxCameras.kt */
public final class GxCamerasPage extends GxPage {
    private TextView pArmNote;
    private TextView pCaption;
    private JSONObject pConfig;
    private boolean pConfigExists;
    private boolean pConfigSaved;
    private TextView pDeleteBtn;
    private TextView pLeftBtn;
    private boolean pLoading;
    private TextView pMsg;
    private TextView pRightBtn;
    private TextView pSaveBtn;
    private TextView pSnapBtn;
    private boolean pSnapLoading;
    private GxCamerasPipView pView;
    private Function1<? super Double, Unit> pZoomSet;
    private LinearLayout sCfgBox;
    private TextView sDelBtn;
    private boolean sDeleteBusy;
    private JSONObject sEvent;
    private LinearLayout sEventBox;
    private String sEventSig;
    private LinearLayout sHistBox;
    private TextView sHistBtn;
    private List<? extends JSONObject> sHistory;
    private boolean sHistoryBusy;
    private String sHistorySig;
    private boolean sHistoryVisible;
    private LinearLayout sLiveBox;
    private TextView sLiveBtn;
    private boolean sLiveBusy;
    private boolean sLoaded;
    private TextView sNotifyBtn;
    private boolean sPushBusy;
    private TextView sState;
    private boolean sStatusFailed;
    private TextView sTestBtn;
    private boolean sTestBusy;
    private int tab;
    private TextView vClearLeftBtn;
    private TextView vClearRightBtn;
    private JSONObject vConfig;
    private boolean vConfigExists;
    private boolean vConfigSaved;
    private TextView vCounts;
    private TextView vDeleteBtn;
    private GxPage.Row vEnabledRow;
    private TextView vFinishBtn;
    private TextView vHint;
    private TextView vLeftBtn;
    private TextView vMsg;
    private TextView vRightBtn;
    private TextView vSaveBtn;
    private boolean vSaving;
    private TextView vSnapBtn;
    private boolean vSnapLoading;
    private final HashMap<String, TextView> vStat;
    private TextView vUndoBtn;
    private GxCamerasRegionView vView;

    /* VF: from Vineflower (jadx: ) */
    public GxCamerasPage(Activity var1, GxHost var2) {
        super(var1, var2);
        Intrinsics.checkNotNullParameter(var1, "act");
        Intrinsics.checkNotNullParameter(var2, "gx");
        this.sEvent = new JSONObject();
        this.sEventSig = "?";
        this.sHistory = CollectionsKt.emptyList();
        this.sHistorySig = "?";
        this.vStat = new HashMap<>();
    }

    @Override // com.starpilot.starview.GxPage
    public void build() {
        GxPage.header$default(this, "Cameras & Monitoring", null, 2, null);
        tabs(CollectionsKt.listOf(new String[]{"Sentry Mode", "V-ASM Spot Monitor", "PiP Side Camera"}), this.tab, new Function1() { // from class: com.starpilot.starview.GxCamerasPage$$ExternalSyntheticLambda17
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit build$lambda$0;
                build$lambda$0 = GxCamerasPage.build$lambda$0(GxCamerasPage.this, ((Integer) obj).intValue());
                return build$lambda$0;
            }
        }, new Function2() { // from class: com.starpilot.starview.GxCamerasPage$$ExternalSyntheticLambda18
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit build$lambda$1;
                build$lambda$1 = GxCamerasPage.build$lambda$1(GxCamerasPage.this, ((Integer) obj).intValue(), (LinearLayout) obj2);
                return build$lambda$1;
            }
        });
        every(5000L, new Function0() { // from class: com.starpilot.starview.GxCamerasPage$$ExternalSyntheticLambda19
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit build$lambda$2;
                build$lambda$2 = GxCamerasPage.build$lambda$2(GxCamerasPage.this);
                return build$lambda$2;
            }
        });
        every(3000L, new Function0() { // from class: com.starpilot.starview.GxCamerasPage$$ExternalSyntheticLambda20
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit build$lambda$3;
                build$lambda$3 = GxCamerasPage.build$lambda$3(GxCamerasPage.this);
                return build$lambda$3;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit build$lambda$0(GxCamerasPage this$0, int i) {
        this$0.tab = i;
        if (this$0.getShown()) {
            loadTab$default(this$0, i, false, 2, null);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit build$lambda$1(GxCamerasPage this$0, int i, LinearLayout box) {
        Intrinsics.checkNotNullParameter(box, "box");
        switch (i) {
            case 0:
                this$0.buildSentry(box);
                break;
            case 1:
                this$0.buildVasm(box);
                break;
            default:
                this$0.buildPip(box);
                break;
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit build$lambda$2(GxCamerasPage this$0) {
        if (this$0.tab == 0) {
            this$0.loadSentryStatus();
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit build$lambda$3(GxCamerasPage this$0) {
        if (this$0.tab == 1) {
            this$0.pollVasm();
        }
        return Unit.INSTANCE;
    }

    @Override // com.starpilot.starview.GxPage
    public void onShow() {
        loadTab(this.tab, true);
    }

    static /* synthetic */ void loadTab$default(GxCamerasPage gxCamerasPage, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = false;
        }
        gxCamerasPage.loadTab(i, z);
    }

    private final void loadTab(int i, boolean fromShow) {
        switch (i) {
            case 0:
                loadSentryParams();
                if (!fromShow) {
                    loadSentryStatus();
                    break;
                }
                break;
            case 1:
                loadVasmConfig();
                loadVasmSnapshot();
                break;
            default:
                loadPipConfig();
                loadPipSnapshot();
                break;
        }
    }

    private final void buildSentry(LinearLayout box) {
        int i;
        GxPage.Row top = GxPage.row$default(this, "Sentry Mode", "Monitor the parked vehicle and review movement captures.", box, 0, 8, null);
        this.sState = top.value("unknown");
        header("Configuration", box);
        this.sCfgBox = box(box);
        LinearLayout linearLayout = this.sCfgBox;
        LinearLayout linearLayout2 = null;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sCfgBox");
            linearLayout = null;
        }
        linearLayout.addView(empty("Loading Sentry settings..."));
        LinearLayout nb = GxPage.buttons$default(this, new Pair[]{TuplesKt.to("Send test notification", new Function0() { // from class: com.starpilot.starview.GxCamerasPage$$ExternalSyntheticLambda9
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildSentry$lambda$4;
                buildSentry$lambda$4 = GxCamerasPage.buildSentry$lambda$4(GxCamerasPage.this);
                return buildSentry$lambda$4;
            }
        })}, box, false, 4, null);
        View childAt = nb.getChildAt(0);
        Intrinsics.checkNotNull(childAt, "null cannot be cast to non-null type android.widget.TextView");
        this.sNotifyBtn = (TextView) childAt;
        note("The test notification uses every configured channel: browser Web Push, ntfy, and webhook. Browser notifications are enabled from Galaxy in a phone browser.", box);
        header("Live view", box);
        GxPage.Row lr = GxPage.row$default(this, "View live", "Capture one still from both cameras while parked.", box, 0, 8, null);
        this.sLiveBtn = GxPage.Row.button$default(lr, "View live", false, new Function0() { // from class: com.starpilot.starview.GxCamerasPage$$ExternalSyntheticLambda10
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildSentry$lambda$5;
                buildSentry$lambda$5 = GxCamerasPage.buildSentry$lambda$5(GxCamerasPage.this);
                return buildSentry$lambda$5;
            }
        }, 2, null);
        this.sLiveBox = box(box);
        LinearLayout linearLayout3 = this.sLiveBox;
        if (linearLayout3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sLiveBox");
            linearLayout3 = null;
        }
        linearLayout3.addView(empty("No live snapshot captured yet."));
        header("Latest Event", box);
        LinearLayout bar = GxPage.buttons$default(this, new Pair[]{TuplesKt.to("View history", new Function0() { // from class: com.starpilot.starview.GxCamerasPage$$ExternalSyntheticLambda12
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildSentry$lambda$6;
                buildSentry$lambda$6 = GxCamerasPage.buildSentry$lambda$6(GxCamerasPage.this);
                return buildSentry$lambda$6;
            }
        }), TuplesKt.to("Send test capture", new Function0() { // from class: com.starpilot.starview.GxCamerasPage$$ExternalSyntheticLambda13
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildSentry$lambda$7;
                buildSentry$lambda$7 = GxCamerasPage.buildSentry$lambda$7(GxCamerasPage.this);
                return buildSentry$lambda$7;
            }
        }), TuplesKt.to("Delete event", new Function0() { // from class: com.starpilot.starview.GxCamerasPage$$ExternalSyntheticLambda14
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildSentry$lambda$8;
                buildSentry$lambda$8 = GxCamerasPage.buildSentry$lambda$8(GxCamerasPage.this);
                return buildSentry$lambda$8;
            }
        })}, box, false, 4, null);
        View childAt2 = bar.getChildAt(0);
        Intrinsics.checkNotNull(childAt2, "null cannot be cast to non-null type android.widget.TextView");
        this.sHistBtn = (TextView) childAt2;
        View childAt3 = bar.getChildAt(1);
        Intrinsics.checkNotNull(childAt3, "null cannot be cast to non-null type android.widget.TextView");
        this.sTestBtn = (TextView) childAt3;
        View childAt4 = bar.getChildAt(2);
        Intrinsics.checkNotNull(childAt4, "null cannot be cast to non-null type android.widget.TextView");
        this.sDelBtn = (TextView) childAt4;
        TextView textView = this.sDelBtn;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sDelBtn");
            textView = null;
        }
        i = GxCamerasKt.GX_CAM_RED;
        textView.setTextColor(i);
        TextView textView2 = this.sDelBtn;
        if (textView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sDelBtn");
            textView2 = null;
        }
        textView2.setVisibility(8);
        note("Events refresh automatically every five seconds.", box);
        this.sEventBox = box(box);
        this.sHistBox = box(box);
        LinearLayout linearLayout4 = this.sHistBox;
        if (linearLayout4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sHistBox");
        } else {
            linearLayout2 = linearLayout4;
        }
        linearLayout2.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildSentry$lambda$4(GxCamerasPage this$0) {
        this$0.sendTestNotification();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildSentry$lambda$5(GxCamerasPage this$0) {
        this$0.viewLive();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildSentry$lambda$6(GxCamerasPage this$0) {
        this$0.toggleHistory();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildSentry$lambda$7(GxCamerasPage this$0) {
        this$0.sendTestEvent();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildSentry$lambda$8(GxCamerasPage this$0) {
        String id = GxPage.str$default(this$0, this$0.sEvent, "eventId", null, 2, null);
        if (!StringsKt.isBlank(id)) {
            this$0.deleteEvent(id);
        }
        return Unit.INSTANCE;
    }

    private final void loadSentryParams() {
        GalaxyApi a = getApi();
        if (a == null) {
            return;
        }
        a.refreshValues(new Function1() { // from class: com.starpilot.starview.GxCamerasPage$$ExternalSyntheticLambda26
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit loadSentryParams$lambda$9;
                loadSentryParams$lambda$9 = GxCamerasPage.loadSentryParams$lambda$9(GxCamerasPage.this, ((Boolean) obj).booleanValue());
                return loadSentryParams$lambda$9;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit loadSentryParams$lambda$9(GxCamerasPage this$0, boolean ok) {
        if (!ok && !this$0.sLoaded) {
            this$0.toast("Failed to load Sentry settings.");
        }
        this$0.sLoaded = true;
        this$0.fillSentryConfig();
        return Unit.INSTANCE;
    }

    /* VF: from Vineflower (jadx: ) */
    static void saveSentryParam$default(GxCamerasPage var0, String var1, Object var2, Function0 var3, int var4, Object var5) {
        if ((var4 & 4) != 0) {
            var3 = null;
        }

        var0.saveSentryParam(var1, var2, var3);
    }

    private final void saveSentryParam(String key, Object value, final Function0<Unit> after) {
        putParam(key, value, new Function1() { // from class: com.starpilot.starview.GxCamerasPage$$ExternalSyntheticLambda21
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit saveSentryParam$lambda$10;
                saveSentryParam$lambda$10 = GxCamerasPage.saveSentryParam$lambda$10(GxCamerasPage.this, after, (String) obj);
                return saveSentryParam$lambda$10;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit saveSentryParam$lambda$10(GxCamerasPage this$0, Function0 $after, String e) {
        if (e == null) {
            this$0.toast("Sentry setting saved.");
        }
        if ($after != null) {
            $after.invoke();
        }
        return Unit.INSTANCE;
    }

    private final void fillSentryConfig() {
        if (this.sCfgBox == null) {
            return;
        }
        LinearLayout linearLayout = this.sCfgBox;
        LinearLayout linearLayout2 = null;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sCfgBox");
            linearLayout = null;
        }
        linearLayout.removeAllViews();
        boolean pBool = pBool("SentryModeEnabled");
        LinearLayout linearLayout3 = this.sCfgBox;
        if (linearLayout3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sCfgBox");
            linearLayout3 = null;
        }
        toggle("Enable Sentry Mode", "Detect sustained movement while the vehicle is parked.", pBool, linearLayout3, new Function1() { // from class: com.starpilot.starview.GxCamerasPage$$ExternalSyntheticLambda48
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit fillSentryConfig$lambda$11;
                fillSentryConfig$lambda$11 = GxCamerasPage.fillSentryConfig$lambda$11(GxCamerasPage.this, ((Boolean) obj).booleanValue());
                return fillSentryConfig$lambda$11;
            }
        });
        fillSentryConfig$urlRow(this, "SentryModeWebhook", "Webhook URL", "Optional Discord-compatible or custom webhook.", "https://...");
        fillSentryConfig$urlRow(this, "SentryModeNtfyUrl", "ntfy URL", "Optional ntfy topic URL for phone notifications.", "https://ntfy.sh/...");
        LinearLayout linearLayout4 = this.sCfgBox;
        if (linearLayout4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sCfgBox");
            linearLayout4 = null;
        }
        LinearLayout linearLayout5 = linearLayout4;
        Double pNum = pNum("SentryModeSensitivity");
        numRow(linearLayout5, "Motion sensitivity", "Lower values detect smaller acceleration changes. Default: 0.04.", pNum != null ? pNum.doubleValue() : 0.04d, 0.005d, 1.0d, 0.005d, 3, "", new Function1() { // from class: com.starpilot.starview.GxCamerasPage$$ExternalSyntheticLambda49
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit fillSentryConfig$lambda$17;
                fillSentryConfig$lambda$17 = GxCamerasPage.fillSentryConfig$lambda$17(GxCamerasPage.this, ((Double) obj).doubleValue());
                return fillSentryConfig$lambda$17;
            }
        });
        LinearLayout linearLayout6 = this.sCfgBox;
        if (linearLayout6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sCfgBox");
        } else {
            linearLayout2 = linearLayout6;
        }
        LinearLayout linearLayout7 = linearLayout2;
        Double pNum2 = pNum("SentryModeWarningTime");
        numRow(linearLayout7, "Warning persistence", "How long movement must continue before the first alert. Default: 1 second.", pNum2 != null ? pNum2.doubleValue() : 1.0d, 0.1d, 10.0d, 0.1d, 1, " seconds", new Function1() { // from class: com.starpilot.starview.GxCamerasPage$$ExternalSyntheticLambda50
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit fillSentryConfig$lambda$18;
                fillSentryConfig$lambda$18 = GxCamerasPage.fillSentryConfig$lambda$18(GxCamerasPage.this, ((Double) obj).doubleValue());
                return fillSentryConfig$lambda$18;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit fillSentryConfig$lambda$11(GxCamerasPage this$0, boolean v) {
        saveSentryParam$default(this$0, "SentryModeEnabled", Boolean.valueOf(v), null, 4, null);
        return Unit.INSTANCE;
    }

    private static final void fillSentryConfig$urlRow(final GxCamerasPage this$0, final String key, final String title, final String desc, final String hint) {
        String cur = StringsKt.trim((CharSequence) this$0.pStr(key)).toString();
        GxCamerasPage gxCamerasPage = this$0;
        String str = (StringsKt.isBlank(cur) ? "Not set" : cur) + "\n" + desc;
        LinearLayout linearLayout = this$0.sCfgBox;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sCfgBox");
            linearLayout = null;
        }
        GxPage.Row r = GxPage.row$default(gxCamerasPage, title, str, linearLayout, 0, 8, null);
        GxPage.Row.button$default(r, "Edit", false, new Function0() { // from class: com.starpilot.starview.GxCamerasPage$$ExternalSyntheticLambda24
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit fillSentryConfig$urlRow$lambda$14;
                fillSentryConfig$urlRow$lambda$14 = GxCamerasPage.fillSentryConfig$urlRow$lambda$14(this$0, title, hint, key, desc);
                return fillSentryConfig$urlRow$lambda$14;
            }
        }, 2, null);
        if (!StringsKt.isBlank(cur)) {
            GxPage.Row.button$default(r, "Clear", false, new Function0() { // from class: com.starpilot.starview.GxCamerasPage$$ExternalSyntheticLambda25
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit fillSentryConfig$urlRow$lambda$16;
                    fillSentryConfig$urlRow$lambda$16 = GxCamerasPage.fillSentryConfig$urlRow$lambda$16(this$0, key);
                    return fillSentryConfig$urlRow$lambda$16;
                }
            }, 2, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit fillSentryConfig$urlRow$lambda$14(final GxCamerasPage this$0, String $title, String $hint, final String $key, String $desc) {
        this$0.prompt($title, $hint, StringsKt.trim((CharSequence) this$0.pStr($key)).toString(), 17, $desc, new Function1() { // from class: com.starpilot.starview.GxCamerasPage$$ExternalSyntheticLambda51
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit fillSentryConfig$urlRow$lambda$14$lambda$13;
                fillSentryConfig$urlRow$lambda$14$lambda$13 = GxCamerasPage.fillSentryConfig$urlRow$lambda$14$lambda$13(this$0, $key, (String) obj);
                return fillSentryConfig$urlRow$lambda$14$lambda$13;
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit fillSentryConfig$urlRow$lambda$14$lambda$13(final GxCamerasPage this$0, String $key, String v) {
        Intrinsics.checkNotNullParameter(v, "v");
        this$0.saveSentryParam($key, StringsKt.trim((CharSequence) v).toString(), new Function0() { // from class: com.starpilot.starview.GxCamerasPage$$ExternalSyntheticLambda69
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit fillSentryConfig$urlRow$lambda$14$lambda$13$lambda$12;
                fillSentryConfig$urlRow$lambda$14$lambda$13$lambda$12 = GxCamerasPage.fillSentryConfig$urlRow$lambda$14$lambda$13$lambda$12(this$0);
                return fillSentryConfig$urlRow$lambda$14$lambda$13$lambda$12;
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit fillSentryConfig$urlRow$lambda$14$lambda$13$lambda$12(GxCamerasPage this$0) {
        this$0.fillSentryConfig();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit fillSentryConfig$urlRow$lambda$16(final GxCamerasPage this$0, String $key) {
        this$0.saveSentryParam($key, "", new Function0() { // from class: com.starpilot.starview.GxCamerasPage$$ExternalSyntheticLambda55
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit fillSentryConfig$urlRow$lambda$16$lambda$15;
                fillSentryConfig$urlRow$lambda$16$lambda$15 = GxCamerasPage.fillSentryConfig$urlRow$lambda$16$lambda$15(this$0);
                return fillSentryConfig$urlRow$lambda$16$lambda$15;
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit fillSentryConfig$urlRow$lambda$16$lambda$15(GxCamerasPage this$0) {
        this$0.fillSentryConfig();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit fillSentryConfig$lambda$17(GxCamerasPage this$0, double v) {
        saveSentryParam$default(this$0, "SentryModeSensitivity", Double.valueOf(v), null, 4, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit fillSentryConfig$lambda$18(GxCamerasPage this$0, double v) {
        saveSentryParam$default(this$0, "SentryModeWarningTime", Double.valueOf(v), null, 4, null);
        return Unit.INSTANCE;
    }

    private final void numRow(ViewGroup into, final String title, final String desc, double value, final double mn, final double mx, final double step, final int decimals, final String suffix, final Function1<? super Double, Unit> onSet) {
        GxPage.Row r = GxPage.row$default(this, title, desc, into, 0, 8, null);
        final Ref.DoubleRef cur = new Ref.DoubleRef();
        cur.element = value;
        final TextView $this$numRow_u24lambda_u2419 = new TextView(getAct());
        $this$numRow_u24lambda_u2419.setTextSize(22.0f);
        $this$numRow_u24lambda_u2419.setTextColor(CarUi.Companion.getTEXT());
        $this$numRow_u24lambda_u2419.setTypeface(Typeface.DEFAULT_BOLD);
        $this$numRow_u24lambda_u2419.setMinWidth(getUi().dp(110));
        $this$numRow_u24lambda_u2419.setGravity(17);
        $this$numRow_u24lambda_u2419.setPadding(getUi().dp(6), getUi().dp(10), getUi().dp(6), getUi().dp(10));
        $this$numRow_u24lambda_u2419.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.GxCamerasPage$$ExternalSyntheticLambda22
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                GxCamerasPage.numRow$lambda$22(GxCamerasPage.this, title, mn, mx, cur, desc, decimals, onSet, $this$numRow_u24lambda_u2419, suffix, view);
            }
        });
        r.add(getUi().stepper("−", new Function0() { // from class: com.starpilot.starview.GxCamerasPage$$ExternalSyntheticLambda33
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit numRow$lambda$23;
                numRow$lambda$23 = GxCamerasPage.numRow$lambda$23(cur, step, decimals, mn, mx, onSet, $this$numRow_u24lambda_u2419, suffix);
                return numRow$lambda$23;
            }
        }));
        r.add($this$numRow_u24lambda_u2419);
        r.add(getUi().stepper("+", new Function0() { // from class: com.starpilot.starview.GxCamerasPage$$ExternalSyntheticLambda44
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit numRow$lambda$24;
                numRow$lambda$24 = GxCamerasPage.numRow$lambda$24(cur, step, decimals, mn, mx, onSet, $this$numRow_u24lambda_u2419, suffix);
                return numRow$lambda$24;
            }
        }));
        numRow$paint($this$numRow_u24lambda_u2419, cur, suffix, decimals);
    }

    private static final String numRow$fmt(int $decimals, double v) {
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        String format = String.format(Locale.US, "%." + $decimals + "f", Arrays.copyOf(new Object[]{Double.valueOf(v)}, 1));
        Intrinsics.checkNotNullExpressionValue(format, "format(...)");
        return format;
    }

    private static final void numRow$paint(TextView tv, Ref.DoubleRef cur, String $suffix, int $decimals) {
        tv.setText(numRow$fmt($decimals, cur.element) + $suffix);
    }

    private static final void numRow$write(int $decimals, Ref.DoubleRef cur, double $mn, double $mx, Function1<? super Double, Unit> function1, TextView tv, String $suffix, double v) {
        double p = Math.pow(10.0d, $decimals);
        cur.element = RangesKt.coerceIn(Math.round(RangesKt.coerceIn(v, $mn, $mx) * p) / p, $mn, $mx);
        numRow$paint(tv, cur, $suffix, $decimals);
        function1.invoke(Double.valueOf(cur.element));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void numRow$lambda$22(final GxCamerasPage this$0, String $title, final double $mn, final double $mx, final Ref.DoubleRef $cur, String $desc, final int $decimals, final Function1 $onSet, final TextView $tv, final String $suffix, View it) {
        this$0.prompt($title, numRow$fmt($decimals, $mn) + " – " + numRow$fmt($decimals, $mx), numRow$fmt($decimals, $cur.element), 8194, $desc, new Function1() { // from class: com.starpilot.starview.GxCamerasPage$$ExternalSyntheticLambda37
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit numRow$lambda$22$lambda$21;
                numRow$lambda$22$lambda$21 = GxCamerasPage.numRow$lambda$22$lambda$21(this$0, $decimals, $cur, $mn, $mx, $onSet, $tv, $suffix, (String) obj);
                return numRow$lambda$22$lambda$21;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit numRow$lambda$22$lambda$21(GxCamerasPage this$0, int $decimals, Ref.DoubleRef $cur, double $mn, double $mx, Function1 $onSet, TextView $tv, String $suffix, String s) {
        Intrinsics.checkNotNullParameter(s, "s");
        Double doubleOrNull = StringsKt.toDoubleOrNull(StringsKt.trim((CharSequence) s).toString());
        if (doubleOrNull != null) {
            double it = doubleOrNull.doubleValue();
            numRow$write($decimals, $cur, $mn, $mx, $onSet, $tv, $suffix, it);
        } else {
            this$0.toast("Not a number");
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit numRow$lambda$23(Ref.DoubleRef $cur, double $step, int $decimals, double $mn, double $mx, Function1 $onSet, TextView $tv, String $suffix) {
        numRow$write($decimals, $cur, $mn, $mx, $onSet, $tv, $suffix, $cur.element - $step);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit numRow$lambda$24(Ref.DoubleRef $cur, double $step, int $decimals, double $mn, double $mx, Function1 $onSet, TextView $tv, String $suffix) {
        numRow$write($decimals, $cur, $mn, $mx, $onSet, $tv, $suffix, $cur.element + $step);
        return Unit.INSTANCE;
    }

    private final void loadSentryStatus() {
        if (this.sState == null) {
            return;
        }
        getJson("/api/sentry/status", new Function2() { // from class: com.starpilot.starview.GxCamerasPage$$ExternalSyntheticLambda16
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit loadSentryStatus$lambda$26;
                loadSentryStatus$lambda$26 = GxCamerasPage.loadSentryStatus$lambda$26(GxCamerasPage.this, (String) obj, (JSONObject) obj2);
                return loadSentryStatus$lambda$26;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit loadSentryStatus$lambda$26(GxCamerasPage this$0, String e, JSONObject o) {
        if (e != null || o == null) {
            if (this$0.sLoaded && !this$0.sStatusFailed) {
                this$0.toast("Failed to load Sentry status.");
            }
            this$0.sStatusFailed = true;
            return Unit.INSTANCE;
        }
        this$0.sStatusFailed = false;
        JSONObject st = o.optJSONObject("status");
        if (st == null) {
            st = new JSONObject();
        }
        TextView textView = this$0.sState;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sState");
            textView = null;
        }
        String str = "unknown";
        String str2 = this$0.str(st, "state", "unknown");
        if (!StringsKt.isBlank(str2)) {
            str = str2;
        }
        textView.setText(str);
        JSONObject optJSONObject = o.optJSONObject("lastEvent");
        if (optJSONObject == null) {
            optJSONObject = new JSONObject();
        }
        this$0.sEvent = optJSONObject;
        renderLatest$default(this$0, false, 1, null);
        if (this$0.sHistoryVisible && !this$0.sHistoryBusy) {
            this$0.loadHistory();
        }
        return Unit.INSTANCE;
    }

    private final String eventSig(JSONObject ev) {
        String str;
        List<String> strings;
        String str$default = GxPage.str$default(this, ev, "eventId", null, 2, null);
        JSONArray optJSONArray = ev.optJSONArray("imageUrls");
        if (optJSONArray == null || (strings = strings(optJSONArray)) == null || (str = CollectionsKt.joinToString(strings, ",", "", "", -1, "...", null)) == null) {
            str = "";
        }
        return str$default + "|" + str + "|" + GxPage.str$default(this, ev, "message", null, 2, null);
    }

    static /* synthetic */ void renderLatest$default(GxCamerasPage gxCamerasPage, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        gxCamerasPage.renderLatest(z);
    }

    private final void renderLatest(boolean force) {
        List urls;
        boolean has = !StringsKt.isBlank(GxPage.str$default(this, this.sEvent, "eventId", null, 2, null));
        TextView textView = this.sDelBtn;
        LinearLayout linearLayout = null;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sDelBtn");
            textView = null;
        }
        textView.setVisibility(has ? 0 : 8);
        String sig = has ? eventSig(this.sEvent) : "";
        if (force || !Intrinsics.areEqual(sig, this.sEventSig)) {
            this.sEventSig = sig;
            LinearLayout linearLayout2 = this.sEventBox;
            if (linearLayout2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("sEventBox");
                linearLayout2 = null;
            }
            linearLayout2.removeAllViews();
            if (!has) {
                LinearLayout linearLayout3 = this.sEventBox;
                if (linearLayout3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("sEventBox");
                } else {
                    linearLayout = linearLayout3;
                }
                linearLayout.addView(empty("No Sentry events recorded yet."));
                return;
            }
            LinearLayout linearLayout4 = this.sEventBox;
            if (linearLayout4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("sEventBox");
            } else {
                linearLayout = linearLayout4;
            }
            LinearLayout c = card(linearLayout);
            JSONObject jSONObject = this.sEvent;
            String it = GxPage.str$default(this, this.sEvent, "detectedAt", null, 2, null);
            kindLine$default(this, c, jSONObject, fmtTime(it), null, 8, null);
            String str$default = GxPage.str$default(this, this.sEvent, "message", null, 2, null);
            if (StringsKt.isBlank(str$default)) {
                str$default = "Movement detected while parked.";
            }
            text(str$default, c, 21.0f, CarUi.Companion.getTEXT(), true);
            JSONArray optJSONArray = this.sEvent.optJSONArray("imageUrls");
            if (optJSONArray == null || (urls = strings(optJSONArray)) == null) {
                urls = CollectionsKt.emptyList();
            }
            if (!urls.isEmpty()) {
                imageStrip(c, urls, "Sentry capture");
                Unit unit = Unit.INSTANCE;
            } else if (Intrinsics.areEqual(GxPage.str$default(this, this.sEvent, "kind", null, 2, null), "power_off")) {
                GxPage.text$default(this, "Power-off alerts do not include camera captures because the device is shutting down.", c, 0.0f, 0, false, 28, null);
            } else {
                GxPage.text$default(this, "No camera images were available for this event.", c, 0.0f, 0, false, 28, null);
            }
        }
    }

    /* VF: from Vineflower (jadx: ) */
    static void kindLine$default(GxCamerasPage var0, LinearLayout var1, JSONObject var2, String var3, Function0 var4, int var5, Object var6) {
        if ((var5 & 8) != 0) {
            var4 = null;
        }

        var0.kindLine(var1, var2, var3, var4);
    }

    /* VF: from Vineflower (jadx: ) */
    private final void kindLine(LinearLayout var1, JSONObject var2, String var3, Function0<Unit> var4) {
        LinearLayout var7 = new LinearLayout((Context)this.getAct());
        var7.setOrientation(0);
        var7.setGravity(16);
        var7.setPadding(0, 0, 0, this.getUi().dp(6));
        CharSequence var6 = GxPage.str$default(this, var2, "kind", null, 2, null);
        Object var10 = var6;
        if (StringsKt.isBlank(var6)) {
            var10 = "event";
        }

        int var5;
        Object var8;
        label35: {
            var8 = (String)var10;
            switch (var8.hashCode()) {
                case -906020504:
                    if (var8.equals("selfie")) {
                        var5 = CarUi.Companion.getACCENT();
                        break label35;
                    }
                    break;
                case 92895825:
                    if (var8.equals("alarm")) {
                        var5 = GxCamerasKt.GX_CAM_RED;
                        break label35;
                    }
                    break;
                case 1124446108:
                    if (var8.equals("warning")) {
                        var5 = CarUi.Companion.getWARN();
                        break label35;
                    }
            }

            var5 = CarUi.Companion.getDIM();
        }

        TextView var10s = new TextView((Context)this.getAct());
        Locale var17 = Locale.US;
        Intrinsics.checkNotNullExpressionValue(var17, "US");
        String var18 = ((String) var8).toUpperCase(var17);
        Intrinsics.checkNotNullExpressionValue(var18, "toUpperCase(...)");
        ((TextView) var10s).setText(var18);
        ((TextView) var10s).setTextSize(14.0F);
        ((TextView) var10s).setTypeface(Typeface.DEFAULT_BOLD);
        ((TextView) var10s).setTextColor(var5);
        var10s.setBackground((Drawable)this.getUi().rounded(CarUi.Companion.getCARD_ON(), 10, var5));
        ((TextView) var10s).setPadding(this.getUi().dp(12), this.getUi().dp(5), this.getUi().dp(12), this.getUi().dp(5));
        TextView var19 = new TextView((Context)this.getAct());
        var19.setText(var3);
        var19.setTextSize(17.0F);
        var19.setTextColor(CarUi.Companion.getTEXT());
        var19.setPadding(this.getUi().dp(12), 0, 0, 0);
        if (var4 != null) {
            var19.setTypeface(Typeface.DEFAULT_BOLD);
            View var14 = (View)var19;
            android.widget.LinearLayout.LayoutParams var9 = new android.widget.LinearLayout.LayoutParams(0, -2, 1.0F);
            Unit var20 = Unit.INSTANCE;
            var7.addView(var14, (android.view.ViewGroup.LayoutParams)var9);
            var19.setPadding(0, 0, this.getUi().dp(12), 0);
            var7.addView((View)var10s);
        } else {
            var7.addView((View)var10s);
            var7.addView((View)var19, (android.view.ViewGroup.LayoutParams)(new android.widget.LinearLayout.LayoutParams(0, -2, 1.0F)));
        }

        if (var4 != null) {
            var10 = CarUi.button$default(this.getUi(), "Delete", false, new GxCamerasPage$$ExternalSyntheticLambda70(var4), 2, null);
            ((TextView) var10).setTextColor(GxCamerasKt.GX_CAM_RED);
            View var15 = (View)var10;
            android.widget.LinearLayout.LayoutParams var16 = new android.widget.LinearLayout.LayoutParams(-2, -2);
            var16.leftMargin = this.getUi().dp(12);
            Unit var13 = Unit.INSTANCE;
            var7.addView(var15, (android.view.ViewGroup.LayoutParams)var16);
        }

        var1.addView((View)var7);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit kindLine$lambda$34(Function0 $onDelete) {
        $onDelete.invoke();
        return Unit.INSTANCE;
    }

    private final void imageStrip(LinearLayout into, List<String> urls, final String alt) {
        LinearLayout strip = new LinearLayout(getAct());
        strip.setOrientation(0);
        strip.setPadding(0, getUi().dp(8), 0, getUi().dp(4));
        int i = 0;
        for (final String u : urls) {
            final int i2 = i;
            i++;
            ImageView $this$imageStrip_u24lambda_u2438 = new ImageView(getAct());
            $this$imageStrip_u24lambda_u2438.setScaleType(ImageView.ScaleType.FIT_CENTER);
            $this$imageStrip_u24lambda_u2438.setBackground(CarUi.rounded$default(getUi(), Color.rgb(6, 8, 11), 12, null, 4, null));
            $this$imageStrip_u24lambda_u2438.setContentDescription(alt + " " + (i2 + 1));
            $this$imageStrip_u24lambda_u2438.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.GxCamerasPage$$ExternalSyntheticLambda32
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    GxCamerasPage.imageStrip$lambda$39(GxCamerasPage.this, u, alt, i2, view);
                }
            });
            ImageView imageView = $this$imageStrip_u24lambda_u2438;
            LinearLayout.LayoutParams $this$imageStrip_u24lambda_u2440 = new LinearLayout.LayoutParams(0, getUi().dp(220), 1.0f);
            if (i2 < urls.size() - 1) {
                $this$imageStrip_u24lambda_u2440.rightMargin = getUi().dp(10);
            }
            Unit unit = Unit.INSTANCE;
            strip.addView(imageView, $this$imageStrip_u24lambda_u2440);
            GxPage.loadImage$default(this, u, $this$imageStrip_u24lambda_u2438, null, 4, null);
        }
        into.addView(strip);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void imageStrip$lambda$39(GxCamerasPage this$0, String $u, String $alt, int $i, View it) {
        this$0.openImage($u, $alt + " " + ($i + 1));
    }

    private final void openImage(String src, String alt) {
        final Dialog d = new Dialog(getAct(), 16973834);
        FrameLayout frame = new FrameLayout(getAct());
        frame.setBackgroundColor(-16777216);
        ImageView iv = new ImageView(getAct());
        iv.setScaleType(ImageView.ScaleType.FIT_CENTER);
        frame.addView(iv, new FrameLayout.LayoutParams(-1, -1));
        LinearLayout bar = new LinearLayout(getAct());
        bar.setOrientation(0);
        bar.setGravity(16);
        bar.setPadding(getUi().dp(20), getUi().dp(14), getUi().dp(20), getUi().dp(14));
        bar.setBackgroundColor(Color.argb(160, 0, 0, 0));
        TextView $this$openImage_u24lambda_u2444 = new TextView(getAct());
        $this$openImage_u24lambda_u2444.setText(alt);
        $this$openImage_u24lambda_u2444.setTextSize(20.0f);
        $this$openImage_u24lambda_u2444.setTextColor(-1);
        $this$openImage_u24lambda_u2444.setTypeface(Typeface.DEFAULT_BOLD);
        bar.addView($this$openImage_u24lambda_u2444, new LinearLayout.LayoutParams(0, -2, 1.0f));
        bar.addView(CarUi.button$default(getUi(), "Close", false, new Function0() { // from class: com.starpilot.starview.GxCamerasPage$$ExternalSyntheticLambda43
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit openImage$lambda$45;
                openImage$lambda$45 = GxCamerasPage.openImage$lambda$45(d);
                return openImage$lambda$45;
            }
        }, 2, null));
        frame.addView(bar, new FrameLayout.LayoutParams(-1, -2, 48));
        iv.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.GxCamerasPage$$ExternalSyntheticLambda45
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                d.dismiss();
            }
        });
        d.setContentView(frame);
        d.show();
        loadImage(src, iv, new Function1() { // from class: com.starpilot.starview.GxCamerasPage$$ExternalSyntheticLambda46
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit openImage$lambda$47;
                openImage$lambda$47 = GxCamerasPage.openImage$lambda$47(GxCamerasPage.this, (Bitmap) obj);
                return openImage$lambda$47;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit openImage$lambda$45(Dialog $d) {
        $d.dismiss();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit openImage$lambda$47(GxCamerasPage this$0, Bitmap b) {
        if (b == null) {
            this$0.toast("Could not load the image.");
        }
        return Unit.INSTANCE;
    }

    private final void busy(TextView b, boolean on, String idle, String working) {
        b.setText(on ? working : idle);
        b.setEnabled(!on);
        b.setAlpha(on ? 0.55f : 1.0f);
    }

    private final void sendTestNotification() {
        if (this.sPushBusy) {
            return;
        }
        this.sPushBusy = true;
        TextView textView = this.sNotifyBtn;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sNotifyBtn");
            textView = null;
        }
        busy(textView, true, "Send test notification", "Sending...");
        GxPage.send$default(this, "POST", "/api/sentry/test-notification", null, 0L, new Function2() { // from class: com.starpilot.starview.GxCamerasPage$$ExternalSyntheticLambda38
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit sendTestNotification$lambda$49;
                sendTestNotification$lambda$49 = GxCamerasPage.sendTestNotification$lambda$49(GxCamerasPage.this, (String) obj, (JSONObject) obj2);
                return sendTestNotification$lambda$49;
            }
        }, 12, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit sendTestNotification$lambda$49(GxCamerasPage this$0, String e, JSONObject o) {
        this$0.sPushBusy = false;
        TextView textView = this$0.sNotifyBtn;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sNotifyBtn");
            textView = null;
        }
        this$0.busy(textView, false, "Send test notification", "Sending...");
        if (e != null) {
            String str = e;
            if (StringsKt.isBlank(str)) {
                str = "Could not send the test notification.";
            }
            this$0.toast(str);
            return Unit.INSTANCE;
        }
        JSONObject ch = o != null ? o.optJSONObject("channels") : null;
        ArrayList names = new ArrayList();
        if (ch != null) {
            Iterator<String> keys = ch.keys();
            Intrinsics.checkNotNullExpressionValue(keys, "keys(...)");
            while (keys.hasNext()) {
                String k = keys.next();
                if (ch.optBoolean(k)) {
                    names.add(Intrinsics.areEqual(k, "webPush") ? "browser" : k);
                }
            }
        }
        this$0.toast(!names.isEmpty() ? "Test notification sent through " + CollectionsKt.joinToString(names, ", ", "", "", -1, "...", null) + "." : "Test notification sent.");
        return Unit.INSTANCE;
    }

    private final void viewLive() {
        if (this.sLiveBusy) {
            return;
        }
        this.sLiveBusy = true;
        TextView textView = this.sLiveBtn;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sLiveBtn");
            textView = null;
        }
        busy(textView, true, "View live", "Capturing...");
        GxPage.json$default(this, "GET", "/api/sentry/live", null, 60L, new Function2() { // from class: com.starpilot.starview.GxCamerasPage$$ExternalSyntheticLambda40
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit viewLive$lambda$51;
                viewLive$lambda$51 = GxCamerasPage.viewLive$lambda$51(GxCamerasPage.this, (String) obj, obj2);
                return viewLive$lambda$51;
            }
        }, 4, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit viewLive$lambda$51(GxCamerasPage this$0, String e, Object v) {
        List urls;
        JSONObject o;
        this$0.sLiveBusy = false;
        TextView textView = this$0.sLiveBtn;
        LinearLayout linearLayout = null;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sLiveBtn");
            textView = null;
        }
        this$0.busy(textView, false, "View live", "Capturing...");
        JSONObject o2 = v instanceof JSONObject ? (JSONObject) v : null;
        if (e == null && o2 != null) {
            this$0.toast("Live camera snapshot captured.");
            LinearLayout linearLayout2 = this$0.sLiveBox;
            if (linearLayout2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("sLiveBox");
                linearLayout2 = null;
            }
            linearLayout2.removeAllViews();
            JSONArray optJSONArray = o2.optJSONArray("imageUrls");
            if (optJSONArray == null || (urls = this$0.strings(optJSONArray)) == null) {
                urls = CollectionsKt.emptyList();
            }
            if (urls.isEmpty()) {
                LinearLayout linearLayout3 = this$0.sLiveBox;
                if (linearLayout3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("sLiveBox");
                } else {
                    linearLayout = linearLayout3;
                }
                linearLayout.addView(this$0.empty("No live snapshot captured yet."));
                return Unit.INSTANCE;
            }
            String at = GxPage.str$default(this$0, o2, "capturedAt", null, 2, null);
            LinearLayout linearLayout4 = this$0.sLiveBox;
            if (linearLayout4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("sLiveBox");
            } else {
                linearLayout = linearLayout4;
            }
            LinearLayout c = this$0.card(linearLayout);
            GxPage.text$default(this$0, "Captured " + (StringsKt.isBlank(at) ? "just now" : this$0.fmtTime(at)) + ".", c, 0.0f, 0, false, 28, null);
            String key = this$0.enc(at);
            Iterable $this$map$iv = urls;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            for (Object item$iv$iv : $this$map$iv) {
                String it = (String) item$iv$iv;
                if (StringsKt.isBlank(key)) {
                    o = o2;
                } else {
                    o = o2;
                    it = it + "?t=" + key;
                }
                destination$iv$iv.add(it);
                o2 = o;
            }
            this$0.imageStrip(c, (List) destination$iv$iv, "Live Sentry camera");
            return Unit.INSTANCE;
        }
        this$0.toast(e == null ? "Live camera capture failed." : e);
        return Unit.INSTANCE;
    }

    private final void sendTestEvent() {
        if (this.sTestBusy) {
            return;
        }
        this.sTestBusy = true;
        TextView textView = this.sTestBtn;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sTestBtn");
            textView = null;
        }
        busy(textView, true, "Send test capture", "Capturing...");
        GxPage.send$default(this, "POST", "/api/sentry/test", null, 0L, new Function2() { // from class: com.starpilot.starview.GxCamerasPage$$ExternalSyntheticLambda41
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit sendTestEvent$lambda$54;
                sendTestEvent$lambda$54 = GxCamerasPage.sendTestEvent$lambda$54(GxCamerasPage.this, (String) obj, (JSONObject) obj2);
                return sendTestEvent$lambda$54;
            }
        }, 12, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit sendTestEvent$lambda$54(final GxCamerasPage this$0, String e, JSONObject jSONObject) {
        this$0.sTestBusy = false;
        TextView textView = this$0.sTestBtn;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sTestBtn");
            textView = null;
        }
        this$0.busy(textView, false, "Send test capture", "Capturing...");
        if (e != null) {
            String str = e;
            if (StringsKt.isBlank(str)) {
                str = "Sentry test failed.";
            }
            this$0.toast(str);
        } else {
            this$0.toast("Test capture started. The images will appear here shortly.");
            this$0.getMain().postDelayed(new Runnable() { // from class: com.starpilot.starview.GxCamerasPage$$ExternalSyntheticLambda34
                @Override // java.lang.Runnable
                public final void run() {
                    GxCamerasPage.sendTestEvent$lambda$54$lambda$53(this$0);
                }
            }, 2500L);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void sendTestEvent$lambda$54$lambda$53(GxCamerasPage this$0) {
        if (this$0.getShown() && this$0.tab == 0) {
            this$0.loadSentryStatus();
        }
    }

    private final void toggleHistory() {
        this.sHistoryVisible = !this.sHistoryVisible;
        TextView textView = this.sHistBtn;
        LinearLayout linearLayout = null;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sHistBtn");
            textView = null;
        }
        textView.setText(this.sHistoryVisible ? "Hide history" : "View history");
        LinearLayout linearLayout2 = this.sHistBox;
        if (linearLayout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sHistBox");
            linearLayout2 = null;
        }
        linearLayout2.setVisibility(this.sHistoryVisible ? 0 : 8);
        if (this.sHistoryVisible) {
            this.sHistorySig = "?";
            LinearLayout linearLayout3 = this.sHistBox;
            if (linearLayout3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("sHistBox");
                linearLayout3 = null;
            }
            linearLayout3.removeAllViews();
            LinearLayout linearLayout4 = this.sHistBox;
            if (linearLayout4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("sHistBox");
            } else {
                linearLayout = linearLayout4;
            }
            linearLayout.addView(empty("Loading Sentry history..."));
            loadHistory();
        }
    }

    private final void loadHistory() {
        this.sHistoryBusy = true;
        if (this.sHistoryVisible) {
            TextView textView = this.sHistBtn;
            if (textView == null) {
                Intrinsics.throwUninitializedPropertyAccessException("sHistBtn");
                textView = null;
            }
            busy(textView, true, "Hide history", "Loading...");
        }
        getJson("/api/sentry/events", new Function2() { // from class: com.starpilot.starview.GxCamerasPage$$ExternalSyntheticLambda52
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit loadHistory$lambda$55;
                loadHistory$lambda$55 = GxCamerasPage.loadHistory$lambda$55(GxCamerasPage.this, (String) obj, (JSONObject) obj2);
                return loadHistory$lambda$55;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit loadHistory$lambda$55(GxCamerasPage this$0, String e, JSONObject o) {
        List<JSONObject> emptyList;
        this$0.sHistoryBusy = false;
        TextView textView = this$0.sHistBtn;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sHistBtn");
            textView = null;
        }
        this$0.busy(textView, false, this$0.sHistoryVisible ? "Hide history" : "View history", "Loading...");
        if (e != null || o == null) {
            this$0.toast("Failed to load Sentry history.");
            return Unit.INSTANCE;
        }
        JSONArray optJSONArray = o.optJSONArray("events");
        if (optJSONArray == null || (emptyList = this$0.objects(optJSONArray)) == null) {
            emptyList = CollectionsKt.emptyList();
        }
        this$0.sHistory = emptyList;
        renderHistory$default(this$0, false, 1, null);
        return Unit.INSTANCE;
    }

    static /* synthetic */ void renderHistory$default(GxCamerasPage gxCamerasPage, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        gxCamerasPage.renderHistory(z);
    }

    private final void renderHistory(boolean force) {
        List urls;
        String sig = CollectionsKt.joinToString(this.sHistory, ";", "", "", -1, "...", new Function1() { // from class: com.starpilot.starview.GxCamerasPage$$ExternalSyntheticLambda27
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                CharSequence renderHistory$lambda$56;
                renderHistory$lambda$56 = GxCamerasPage.renderHistory$lambda$56(GxCamerasPage.this, (JSONObject) obj);
                return renderHistory$lambda$56;
            }
        });
        if (force || !Intrinsics.areEqual(sig, this.sHistorySig)) {
            this.sHistorySig = sig;
            LinearLayout linearLayout = this.sHistBox;
            LinearLayout linearLayout2 = null;
            if (linearLayout == null) {
                Intrinsics.throwUninitializedPropertyAccessException("sHistBox");
                linearLayout = null;
            }
            linearLayout.removeAllViews();
            if (this.sHistory.isEmpty()) {
                LinearLayout linearLayout3 = this.sHistBox;
                if (linearLayout3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("sHistBox");
                } else {
                    linearLayout2 = linearLayout3;
                }
                linearLayout2.addView(empty("No retained Sentry events."));
                return;
            }
            String str = this.sHistory.size() + " event" + (this.sHistory.size() == 1 ? "" : "s") + " retained. Events stay here until you delete them.";
            LinearLayout linearLayout4 = this.sHistBox;
            if (linearLayout4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("sHistBox");
                linearLayout4 = null;
            }
            note(str, linearLayout4);
            for (JSONObject ev : this.sHistory) {
                LinearLayout linearLayout5 = this.sHistBox;
                if (linearLayout5 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("sHistBox");
                    linearLayout5 = null;
                }
                LinearLayout c = card(linearLayout5);
                final String id = GxPage.str$default(this, ev, "eventId", null, 2, null);
                String it = GxPage.str$default(this, ev, "detectedAt", null, 2, null);
                kindLine(c, ev, StringsKt.isBlank(it) ? "Sentry event" : fmtTime(it), new Function0() { // from class: com.starpilot.starview.GxCamerasPage$$ExternalSyntheticLambda28
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        Unit renderHistory$lambda$58;
                        renderHistory$lambda$58 = GxCamerasPage.renderHistory$lambda$58(GxCamerasPage.this, id);
                        return renderHistory$lambda$58;
                    }
                });
                String str$default = GxPage.str$default(this, ev, "message", null, 2, null);
                if (StringsKt.isBlank(str$default)) {
                    str$default = "Movement detected while parked.";
                }
                text(str$default, c, 21.0f, CarUi.Companion.getTEXT(), true);
                JSONArray optJSONArray = ev.optJSONArray("imageUrls");
                if (optJSONArray == null || (urls = strings(optJSONArray)) == null) {
                    urls = CollectionsKt.emptyList();
                }
                if (!urls.isEmpty()) {
                    imageStrip(c, urls, "Sentry capture");
                } else {
                    GxPage.text$default(this, "No camera images were available for this event.", c, 0.0f, 0, false, 28, null);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CharSequence renderHistory$lambda$56(GxCamerasPage this$0, JSONObject it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return this$0.eventSig(it);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderHistory$lambda$58(GxCamerasPage this$0, String $id) {
        this$0.deleteEvent($id);
        return Unit.INSTANCE;
    }

    private final void deleteEvent(final String eventId) {
        if (StringsKt.isBlank(eventId) || this.sDeleteBusy) {
            return;
        }
        confirm("Delete Sentry event?", "Delete this Sentry event and its camera images? This cannot be undone.", "Delete", new Function0() { // from class: com.starpilot.starview.GxCamerasPage$$ExternalSyntheticLambda68
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit deleteEvent$lambda$63;
                deleteEvent$lambda$63 = GxCamerasPage.deleteEvent$lambda$63(GxCamerasPage.this, eventId);
                return deleteEvent$lambda$63;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit deleteEvent$lambda$63(final GxCamerasPage this$0, final String $eventId) {
        this$0.sDeleteBusy = true;
        GxPage.send$default(this$0, "DELETE", "/api/sentry/events/" + this$0.enc($eventId), null, 0L, new Function2() { // from class: com.starpilot.starview.GxCamerasPage$$ExternalSyntheticLambda15
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit deleteEvent$lambda$63$lambda$62;
                deleteEvent$lambda$63$lambda$62 = GxCamerasPage.deleteEvent$lambda$63$lambda$62(this$0, $eventId, (String) obj, (JSONObject) obj2);
                return deleteEvent$lambda$63$lambda$62;
            }
        }, 12, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit deleteEvent$lambda$63$lambda$62(GxCamerasPage this$0, String $eventId, String e, JSONObject jSONObject) {
        this$0.sDeleteBusy = false;
        if (e != null) {
            String str = e;
            if (StringsKt.isBlank(str)) {
                str = "Sentry event deletion failed.";
            }
            this$0.toast(str);
            return Unit.INSTANCE;
        }
        Iterable $this$filter$iv = this$0.sHistory;
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            JSONObject it = (JSONObject) element$iv$iv;
            if (!Intrinsics.areEqual(GxPage.str$default(this$0, it, "eventId", null, 2, null), $eventId)) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        this$0.sHistory = (List) destination$iv$iv;
        if (this$0.sHistoryVisible) {
            renderHistory$default(this$0, false, 1, null);
        }
        if (Intrinsics.areEqual(GxPage.str$default(this$0, this$0.sEvent, "eventId", null, 2, null), $eventId)) {
            this$0.sEvent = new JSONObject();
            renderLatest$default(this$0, false, 1, null);
            this$0.loadSentryStatus();
        }
        this$0.toast("Sentry event deleted.");
        return Unit.INSTANCE;
    }

    private final String fmtTime(String s) {
        Object m1191constructorimpl;
        try {
            Result.Companion companion = Result.Companion;
            GxCamerasPage gxCamerasPage = this;
            m1191constructorimpl = KtCompat.resultOf(OffsetDateTime.parse(s).atZoneSameInstant(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("MMM d, yyyy h:mm:ss a", Locale.US)));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            m1191constructorimpl = KtCompat.resultOf(ResultKt.createFailure(th));
        }
        if (KtCompat.isFailure(m1191constructorimpl)) {
            m1191constructorimpl = null;
        }
        String str = (String) m1191constructorimpl;
        return str == null ? s : str;
    }

    private final void gxCamLoadBitmap(final String path, final Function2<? super String, ? super Bitmap, Unit> then) {
        final String h = getHost();
        if (h == null) {
            then.invoke("Not connected", null);
        } else {
            new Thread(new Runnable() { // from class: com.starpilot.starview.GxCamerasPage$$ExternalSyntheticLambda35
                @Override // java.lang.Runnable
                public final void run() {
                    GxCamerasPage.gxCamLoadBitmap$lambda$73(h, path, GxCamerasPage.this, then);
                }
            }).start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x00d0, code lost:
    
        if (r0 == null) goto L37;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:86:0x017b  */
    /* JADX WARN: Type inference failed for: r0v61, types: [T, android.graphics.Bitmap] */
    /* JADX WARN: Type inference failed for: r9v2, types: [T, android.graphics.Bitmap] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void gxCamLoadBitmap$lambda$73(String str, String str2, GxCamerasPage gxCamerasPage, final Function2 function2) {
        String message;
        byte[] bArr;
        Object m1191constructorimpl;
        T t;
        Object m1191constructorimpl2;
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        final Ref.ObjectRef objectRef2 = new Ref.ObjectRef();
        try {
        } catch (Exception e) {
            e = e;
        }
        try {
        } catch (Exception e2) {
            e = e2;
            message = e.getMessage();
            T t2 = message;
            if (message == null) {
                t2 = e.toString();
            }
            objectRef.element = t2;
            gxCamerasPage.getMain().post(new Runnable() { // from class: com.starpilot.starview.GxCamerasPage$$ExternalSyntheticLambda65
                @Override // java.lang.Runnable
                public final void run() {
                    GxCamerasPage.gxCamLoadBitmap$lambda$73$lambda$72(function2, objectRef, objectRef2);
                }
            });
        }
        try {
            Response execute = GxPage.Companion.client(30L).newCall(new Request.Builder().url("http://" + str + ":8082" + str2).header("Cache-Control", "no-store").build()).execute();
            Throwable execute$c = null;
            try {
                Response response = execute;
                String header$default = response.header("Content-Type", null);
                String str3 = "";
                if (header$default == null) {
                    header$default = "";
                }
                String str4 = header$default;
                ResponseBody body = response.body();
                if (body == null || (bArr = body.bytes()) == null) {
                    bArr = new byte[0];
                }
                byte[] bArr2 = bArr;
                if (response.isSuccessful()) {
                    if (StringsKt.contains((CharSequence) str4, (CharSequence) "json", false)) {
                        try {
                            Result.Companion companion = Result.Companion;
                            m1191constructorimpl2 = KtCompat.resultOf(new JSONObject(new String(bArr2, Charsets.UTF_8)).optString("jpeg"));
                        } catch (Throwable th) {
                            Result.Companion companion2 = Result.Companion;
                            m1191constructorimpl2 = KtCompat.resultOf(ResultKt.createFailure(th));
                        }
                        if (KtCompat.isFailure(m1191constructorimpl2)) {
                            m1191constructorimpl2 = null;
                        }
                        String str5 = (String) m1191constructorimpl2;
                        if (str5 != null) {
                            str3 = str5;
                        }
                        String str6 = str3;
                        if (StringsKt.isBlank(str6)) {
                            objectRef.element = "Snapshot missing image data";
                        } else {
                            byte[] decode = Base64.decode(str6, 0);
                            objectRef2.element = BitmapFactory.decodeByteArray(decode, 0, decode.length);
                        }
                    } else {
                        objectRef2.element = BitmapFactory.decodeByteArray(bArr2, 0, bArr2.length);
                    }
                    if (objectRef.element == null) {
                        objectRef.element = "Failed to decode image";
                    }
                    Unit unit = Unit.INSTANCE;
                } else {
                    try {
                        Result.Companion companion3 = Result.Companion;
                        JSONObject jSONObject = new JSONObject(new String(bArr2, Charsets.UTF_8));
                        String optString = jSONObject.optString("error");
                        if (StringsKt.isBlank(optString)) {
                            optString = jSONObject.optString("message");
                        }
                        m1191constructorimpl = KtCompat.resultOf(optString);
                    } catch (Throwable th2) {
                        Result.Companion companion4 = Result.Companion;
                        m1191constructorimpl = KtCompat.resultOf(ResultKt.createFailure(th2));
                    }
                    if (KtCompat.isFailure(m1191constructorimpl)) {
                        m1191constructorimpl = null;
                    }
                    String str7 = (String) m1191constructorimpl;
                    if (str7 != null) {
                        String str8 = str7;
                        if (StringsKt.isBlank(str8)) {
                            str8 = null;
                        }
                        String str9 = str8;
                        t = str9;
                    }
                    t = "HTTP " + response.code();
                    objectRef.element = t;
                    if (objectRef.element == null && objectRef2.element == null) {
                        objectRef.element = "Failed to decode image";
                    }
                    Unit unit2 = Unit.INSTANCE;
                }
            } catch (Throwable execute$t) {
                execute$c = execute$t;
                throw execute$t;
            } finally {
                CloseableKt.closeFinally(execute, execute$c);
            }
        } catch (Exception e3) {
            e = e3;
            message = e.getMessage();
            T t22 = message;
            if (message == null) {
            }
            objectRef.element = t22;
            gxCamerasPage.getMain().post(new Runnable() { // from class: com.starpilot.starview.GxCamerasPage$$ExternalSyntheticLambda65
                @Override // java.lang.Runnable
                public final void run() {
                    GxCamerasPage.gxCamLoadBitmap$lambda$73$lambda$72(function2, objectRef, objectRef2);
                }
            });
        }
        gxCamerasPage.getMain().post(new Runnable() { // from class: com.starpilot.starview.GxCamerasPage$$ExternalSyntheticLambda65
            @Override // java.lang.Runnable
            public final void run() {
                GxCamerasPage.gxCamLoadBitmap$lambda$73$lambda$72(function2, objectRef, objectRef2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void gxCamLoadBitmap$lambda$73$lambda$72(Function2 $then, Ref.ObjectRef $err, Ref.ObjectRef $bmp) {
        $then.invoke($err.element, $bmp.element);
    }

    private final void msg(TextView tv, String text, boolean error) {
        tv.setText(text);
        tv.setTextColor(error ? GxCamerasKt.GX_CAM_RED : CarUi.Companion.getTEXT());
        tv.setBackground(getUi().rounded(CarUi.Companion.getCARD(), 12, Integer.valueOf(error ? GxCamerasKt.GX_CAM_RED : CarUi.Companion.getACCENT())));
        tv.setVisibility(StringsKt.isBlank(text) ? 8 : 0);
    }

    private final TextView msgView(LinearLayout into) {
        TextView t = new TextView(getAct());
        t.setTextSize(17.0f);
        t.setPadding(getUi().dp(18), getUi().dp(12), getUi().dp(18), getUi().dp(12));
        t.setVisibility(8);
        into.addView(t, CarUi.lp$default(getUi(), 0, 0, 3, null));
        return t;
    }

    private final void tint(TextView b, int color, boolean on) {
        b.setBackground(getUi().rounded(on ? color : CarUi.Companion.getCARD_ON(), 12, Integer.valueOf(color)));
        b.setTextColor(on ? -1 : color);
    }

    private final void showIf(View v, boolean on) {
        v.setVisibility(on ? 0 : 8);
    }

    private final void buildVasm(LinearLayout box) {
        int i;
        int i2;
        int i3;
        this.vMsg = msgView(box);
        this.vEnabledRow = GxPage.row$default(this, "Annotate Windows", "Trace the visible side-window glass the driver camera sees so V-ASM knows where to look.", box, 0, 8, null);
        GxPage.Row row = this.vEnabledRow;
        if (row == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vEnabledRow");
            row = null;
        }
        row.value("V-ASM enabled").setTextColor(CarUi.Companion.getACCENT());
        LinearLayout bar1 = GxPage.buttons$default(this, new Pair[]{TuplesKt.to("Annotate Left Side of Vehicle - Left Here", new Function0() { // from class: com.starpilot.starview.GxCamerasPage$$ExternalSyntheticLambda66
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildVasm$lambda$75;
                buildVasm$lambda$75 = GxCamerasPage.buildVasm$lambda$75(GxCamerasPage.this);
                return buildVasm$lambda$75;
            }
        }), TuplesKt.to("Annotate Right Side of Vehicle - Right Here", new Function0() { // from class: com.starpilot.starview.GxCamerasPage$$ExternalSyntheticLambda73
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildVasm$lambda$76;
                buildVasm$lambda$76 = GxCamerasPage.buildVasm$lambda$76(GxCamerasPage.this);
                return buildVasm$lambda$76;
            }
        }), TuplesKt.to("Finish", new Function0() { // from class: com.starpilot.starview.GxCamerasPage$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildVasm$lambda$77;
                buildVasm$lambda$77 = GxCamerasPage.buildVasm$lambda$77(GxCamerasPage.this);
                return buildVasm$lambda$77;
            }
        }), TuplesKt.to("Undo last point", new Function0() { // from class: com.starpilot.starview.GxCamerasPage$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildVasm$lambda$78;
                buildVasm$lambda$78 = GxCamerasPage.buildVasm$lambda$78(GxCamerasPage.this);
                return buildVasm$lambda$78;
            }
        })}, box, false, 4, null);
        View childAt = bar1.getChildAt(0);
        Intrinsics.checkNotNull(childAt, "null cannot be cast to non-null type android.widget.TextView");
        this.vLeftBtn = (TextView) childAt;
        View childAt2 = bar1.getChildAt(1);
        Intrinsics.checkNotNull(childAt2, "null cannot be cast to non-null type android.widget.TextView");
        this.vRightBtn = (TextView) childAt2;
        View childAt3 = bar1.getChildAt(2);
        Intrinsics.checkNotNull(childAt3, "null cannot be cast to non-null type android.widget.TextView");
        this.vFinishBtn = (TextView) childAt3;
        View childAt4 = bar1.getChildAt(3);
        Intrinsics.checkNotNull(childAt4, "null cannot be cast to non-null type android.widget.TextView");
        this.vUndoBtn = (TextView) childAt4;
        LinearLayout bar2 = buttons(new Pair[]{TuplesKt.to("Save Config", new Function0() { // from class: com.starpilot.starview.GxCamerasPage$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildVasm$lambda$79;
                buildVasm$lambda$79 = GxCamerasPage.buildVasm$lambda$79(GxCamerasPage.this);
                return buildVasm$lambda$79;
            }
        }), TuplesKt.to("Delete Config", new Function0() { // from class: com.starpilot.starview.GxCamerasPage$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildVasm$lambda$80;
                buildVasm$lambda$80 = GxCamerasPage.buildVasm$lambda$80(GxCamerasPage.this);
                return buildVasm$lambda$80;
            }
        }), TuplesKt.to("Clear All", new Function0() { // from class: com.starpilot.starview.GxCamerasPage$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildVasm$lambda$81;
                buildVasm$lambda$81 = GxCamerasPage.buildVasm$lambda$81(GxCamerasPage.this);
                return buildVasm$lambda$81;
            }
        }), TuplesKt.to("Get a new Snapshot", new Function0() { // from class: com.starpilot.starview.GxCamerasPage$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildVasm$lambda$82;
                buildVasm$lambda$82 = GxCamerasPage.buildVasm$lambda$82(GxCamerasPage.this);
                return buildVasm$lambda$82;
            }
        })}, box, true);
        View childAt5 = bar2.getChildAt(0);
        Intrinsics.checkNotNull(childAt5, "null cannot be cast to non-null type android.widget.TextView");
        this.vSaveBtn = (TextView) childAt5;
        View childAt6 = bar2.getChildAt(1);
        Intrinsics.checkNotNull(childAt6, "null cannot be cast to non-null type android.widget.TextView");
        this.vDeleteBtn = (TextView) childAt6;
        TextView textView = this.vDeleteBtn;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vDeleteBtn");
            textView = null;
        }
        i = GxCamerasKt.GX_CAM_RED;
        textView.setTextColor(i);
        View childAt7 = bar2.getChildAt(3);
        Intrinsics.checkNotNull(childAt7, "null cannot be cast to non-null type android.widget.TextView");
        this.vSnapBtn = (TextView) childAt7;
        LinearLayout cl = new LinearLayout(getAct());
        cl.setOrientation(0);
        cl.setGravity(16);
        cl.setPadding(getUi().dp(6), 0, 0, getUi().dp(6));
        TextView $this$buildVasm_u24lambda_u2484 = new TextView(getAct());
        $this$buildVasm_u24lambda_u2484.setTextSize(18.0f);
        $this$buildVasm_u24lambda_u2484.setTextColor(CarUi.Companion.getTEXT());
        this.vCounts = $this$buildVasm_u24lambda_u2484;
        TextView textView2 = this.vCounts;
        if (textView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vCounts");
            textView2 = null;
        }
        cl.addView(textView2, new LinearLayout.LayoutParams(0, -2, 1.0f));
        TextView it = CarUi.button$default(getUi(), "Clear Left", false, new Function0() { // from class: com.starpilot.starview.GxCamerasPage$$ExternalSyntheticLambda7
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildVasm$lambda$85;
                buildVasm$lambda$85 = GxCamerasPage.buildVasm$lambda$85(GxCamerasPage.this);
                return buildVasm$lambda$85;
            }
        }, 2, null);
        i2 = GxCamerasKt.GX_CAM_BLUE;
        tint(it, i2, false);
        this.vClearLeftBtn = it;
        TextView it2 = CarUi.button$default(getUi(), "Clear Right", false, new Function0() { // from class: com.starpilot.starview.GxCamerasPage$$ExternalSyntheticLambda8
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildVasm$lambda$87;
                buildVasm$lambda$87 = GxCamerasPage.buildVasm$lambda$87(GxCamerasPage.this);
                return buildVasm$lambda$87;
            }
        }, 2, null);
        i3 = GxCamerasKt.GX_CAM_ORANGE;
        tint(it2, i3, false);
        this.vClearRightBtn = it2;
        TextView textView3 = this.vClearLeftBtn;
        if (textView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vClearLeftBtn");
            textView3 = null;
        }
        LinearLayout.LayoutParams $this$buildVasm_u24lambda_u2489 = new LinearLayout.LayoutParams(-2, -2);
        $this$buildVasm_u24lambda_u2489.rightMargin = getUi().dp(10);
        Unit unit = Unit.INSTANCE;
        cl.addView(textView3, $this$buildVasm_u24lambda_u2489);
        TextView textView4 = this.vClearRightBtn;
        if (textView4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vClearRightBtn");
            textView4 = null;
        }
        cl.addView(textView4);
        box.addView(cl);
        this.vHint = note("Loading camera snapshot... The snapshot will load automatically.", box);
        this.vView = new GxCamerasRegionView(getAct());
        GxCamerasRegionView gxCamerasRegionView = this.vView;
        if (gxCamerasRegionView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vView");
            gxCamerasRegionView = null;
        }
        gxCamerasRegionView.setOnChanged(new Function0() { // from class: com.starpilot.starview.GxCamerasPage$$ExternalSyntheticLambda71
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildVasm$lambda$90;
                buildVasm$lambda$90 = GxCamerasPage.buildVasm$lambda$90(GxCamerasPage.this);
                return buildVasm$lambda$90;
            }
        });
        GxCamerasRegionView gxCamerasRegionView2 = this.vView;
        if (gxCamerasRegionView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vView");
            gxCamerasRegionView2 = null;
        }
        gxCamerasRegionView2.setOnClose(new Function0() { // from class: com.starpilot.starview.GxCamerasPage$$ExternalSyntheticLambda72
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildVasm$lambda$91;
                buildVasm$lambda$91 = GxCamerasPage.buildVasm$lambda$91(GxCamerasPage.this);
                return buildVasm$lambda$91;
            }
        });
        GxCamerasRegionView gxCamerasRegionView3 = this.vView;
        if (gxCamerasRegionView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vView");
            gxCamerasRegionView3 = null;
        }
        LinearLayout.LayoutParams $this$buildVasm_u24lambda_u2492 = new LinearLayout.LayoutParams(-1, -2);
        $this$buildVasm_u24lambda_u2492.bottomMargin = getUi().dp(8);
        Unit unit2 = Unit.INSTANCE;
        box.addView(gxCamerasRegionView3, $this$buildVasm_u24lambda_u2492);
        note("The preview is mirrored: the LEFT side of the vehicle is always on the LEFT here; the RIGHT side is always on the RIGHT. Trace the visible glass (front and rear side windows), exclude A-pillars, door frames, and interior. Drag a point to move it.", box);
        header("Current Status", box);
        for (Pair pair : (Iterable<Pair>) (Iterable) CollectionsKt.listOf(new Pair[]{TuplesKt.to("VASMLeftActive", "Left Active"), TuplesKt.to("VASMRightActive", "Right Active"), TuplesKt.to("VASMLeftConfidence", "Left Confidence"), TuplesKt.to("VASMRightConfidence", "Right Confidence")})) {
            String k = (String) pair.component1();
            String label = (String) pair.component2();
            this.vStat.put(k, GxPage.row$default(this, label, null, box, 0, 8, null).value("-"));
        }
        vPaint();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildVasm$lambda$75(GxCamerasPage this$0) {
        this$0.vStart("left");
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildVasm$lambda$76(GxCamerasPage this$0) {
        this$0.vStart("right");
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildVasm$lambda$77(GxCamerasPage this$0) {
        this$0.vFinish();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildVasm$lambda$78(GxCamerasPage this$0) {
        this$0.vUndo();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildVasm$lambda$79(GxCamerasPage this$0) {
        this$0.vSave();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildVasm$lambda$80(GxCamerasPage this$0) {
        this$0.vDelete();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildVasm$lambda$81(GxCamerasPage this$0) {
        this$0.vClearAll();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildVasm$lambda$82(GxCamerasPage this$0) {
        this$0.loadVasmSnapshot();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildVasm$lambda$85(GxCamerasPage this$0) {
        this$0.vClearSide("left");
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildVasm$lambda$87(GxCamerasPage this$0) {
        this$0.vClearSide("right");
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildVasm$lambda$90(GxCamerasPage this$0) {
        this$0.vPaint();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildVasm$lambda$91(GxCamerasPage this$0) {
        this$0.vFinish();
        return Unit.INSTANCE;
    }

    private final void vPaint() {
        int i;
        int i2;
        String str;
        if (this.vView == null) {
            return;
        }
        GxCamerasRegionView gxCamerasRegionView = this.vView;
        if (gxCamerasRegionView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vView");
            gxCamerasRegionView = null;
        }
        String a = gxCamerasRegionView.getAnnotating();
        GxCamerasRegionView gxCamerasRegionView2 = this.vView;
        if (gxCamerasRegionView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vView");
            gxCamerasRegionView2 = null;
        }
        int ln = gxCamerasRegionView2.getLeft().size();
        GxCamerasRegionView gxCamerasRegionView3 = this.vView;
        if (gxCamerasRegionView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vView");
            gxCamerasRegionView3 = null;
        }
        int rn = gxCamerasRegionView3.getRight().size();
        TextView textView = this.vLeftBtn;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vLeftBtn");
            textView = null;
        }
        i = GxCamerasKt.GX_CAM_BLUE;
        tint(textView, i, Intrinsics.areEqual(a, "left"));
        TextView textView2 = this.vRightBtn;
        if (textView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vRightBtn");
            textView2 = null;
        }
        i2 = GxCamerasKt.GX_CAM_ORANGE;
        tint(textView2, i2, Intrinsics.areEqual(a, "right"));
        TextView textView3 = this.vLeftBtn;
        if (textView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vLeftBtn");
            textView3 = null;
        }
        textView3.setText(Intrinsics.areEqual(a, "left") ? "Annotating Left Side of Vehicle - Left Here..." : "Annotate Left Side of Vehicle - Left Here");
        TextView textView4 = this.vRightBtn;
        if (textView4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vRightBtn");
            textView4 = null;
        }
        textView4.setText(Intrinsics.areEqual(a, "right") ? "Annotating Right Side of Vehicle - Right Here..." : "Annotate Right Side of Vehicle - Right Here");
        TextView textView5 = this.vFinishBtn;
        if (textView5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vFinishBtn");
            textView5 = null;
        }
        showIf(textView5, a != null);
        TextView textView6 = this.vUndoBtn;
        if (textView6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vUndoBtn");
            textView6 = null;
        }
        showIf(textView6, a != null);
        TextView textView7 = this.vFinishBtn;
        if (textView7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vFinishBtn");
            textView7 = null;
        }
        textView7.setText("Finish " + (Intrinsics.areEqual(a, "left") ? "Left" : "Right"));
        boolean canSave = ln >= 3 || rn >= 3;
        TextView textView8 = this.vSaveBtn;
        if (textView8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vSaveBtn");
            textView8 = null;
        }
        textView8.setText(this.vSaving ? "Saving..." : "Save Config");
        TextView textView9 = this.vSaveBtn;
        if (textView9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vSaveBtn");
            textView9 = null;
        }
        textView9.setEnabled(!this.vSaving && canSave);
        TextView textView10 = this.vSaveBtn;
        if (textView10 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vSaveBtn");
            textView10 = null;
        }
        TextView textView11 = this.vSaveBtn;
        if (textView11 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vSaveBtn");
            textView11 = null;
        }
        textView10.setAlpha(textView11.isEnabled() ? 1.0f : 0.45f);
        TextView textView12 = this.vDeleteBtn;
        if (textView12 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vDeleteBtn");
            textView12 = null;
        }
        showIf(textView12, this.vConfigExists);
        TextView textView13 = this.vDeleteBtn;
        if (textView13 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vDeleteBtn");
            textView13 = null;
        }
        textView13.setEnabled(!this.vSaving);
        TextView textView14 = this.vSnapBtn;
        if (textView14 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vSnapBtn");
            textView14 = null;
        }
        busy(textView14, this.vSnapLoading, "Get a new Snapshot", "Loading...");
        GxPage.Row row = this.vEnabledRow;
        if (row == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vEnabledRow");
            row = null;
        }
        LinearLayout view = row.getView();
        GxPage.Row row2 = this.vEnabledRow;
        if (row2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vEnabledRow");
            row2 = null;
        }
        view.getChildAt(row2.getView().getChildCount() - 1).setVisibility((this.vConfigSaved || this.vConfigExists) ? 0 : 4);
        TextView textView15 = this.vCounts;
        if (textView15 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vCounts");
            textView15 = null;
        }
        textView15.setText("Left: " + ln + " pt" + (ln != 1 ? "s" : "") + (ln >= 3 ? " ✓" : "") + "     Right: " + rn + " pt" + (rn == 1 ? "" : "s") + (rn >= 3 ? " ✓" : ""));
        TextView textView16 = this.vClearLeftBtn;
        if (textView16 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vClearLeftBtn");
            textView16 = null;
        }
        showIf(textView16, ln > 0);
        TextView textView17 = this.vClearRightBtn;
        if (textView17 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vClearRightBtn");
            textView17 = null;
        }
        showIf(textView17, rn > 0);
        TextView textView18 = this.vHint;
        if (textView18 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vHint");
            textView18 = null;
        }
        textView18.setTextColor(Intrinsics.areEqual(a, "left") ? GxCamerasKt.GX_CAM_BLUE : Intrinsics.areEqual(a, "right") ? GxCamerasKt.GX_CAM_ORANGE : CarUi.Companion.getDIM());
        TextView textView19 = this.vHint;
        if (textView19 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vHint");
            textView19 = null;
        }
        if (a != null) {
            str = (Intrinsics.areEqual(a, "left") ? "LEFT SIDE OF VEHICLE - LEFT HERE" : "RIGHT SIDE OF VEHICLE - RIGHT HERE") + " — Tap the picture to place points around the visible glass. Tap near the first point to close the polygon. Use Undo to remove the last point.";
        } else {
            GxCamerasRegionView gxCamerasRegionView4 = this.vView;
            if (gxCamerasRegionView4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("vView");
                gxCamerasRegionView4 = null;
            }
            str = gxCamerasRegionView4.getBmp() == null ? "Loading camera snapshot... The snapshot will load automatically." : "Idle — tap a window button above to configure your active canvas boundaries.";
        }
        textView19.setText(str);
        GxCamerasRegionView gxCamerasRegionView5 = this.vView;
        if (gxCamerasRegionView5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vView");
            gxCamerasRegionView5 = null;
        }
        gxCamerasRegionView5.invalidate();
    }

    private final void vSuccess(String s) {
        TextView textView = this.vMsg;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vMsg");
            textView = null;
        }
        msg(textView, s, false);
    }

    private final void vError(String s) {
        TextView textView = this.vMsg;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vMsg");
            textView = null;
        }
        msg(textView, s, true);
        String str = s;
        if (StringsKt.isBlank(str)) {
            str = "Something went wrong.";
        }
        toast(str);
    }

    private final void vStart(String side) {
        GxCamerasRegionView gxCamerasRegionView = this.vView;
        TextView textView = null;
        if (gxCamerasRegionView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vView");
            gxCamerasRegionView = null;
        }
        gxCamerasRegionView.setAnnotating(side);
        TextView textView2 = this.vMsg;
        if (textView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vMsg");
        } else {
            textView = textView2;
        }
        msg(textView, "", false);
        vPaint();
    }

    private final void vFinish() {
        ArrayList pts;
        GxCamerasRegionView gxCamerasRegionView = this.vView;
        if (gxCamerasRegionView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vView");
            gxCamerasRegionView = null;
        }
        String side = gxCamerasRegionView.getAnnotating();
        if (side == null) {
            return;
        }
        if (Intrinsics.areEqual(side, "left")) {
            GxCamerasRegionView gxCamerasRegionView2 = this.vView;
            if (gxCamerasRegionView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("vView");
                gxCamerasRegionView2 = null;
            }
            pts = gxCamerasRegionView2.getLeft();
        } else {
            GxCamerasRegionView gxCamerasRegionView3 = this.vView;
            if (gxCamerasRegionView3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("vView");
                gxCamerasRegionView3 = null;
            }
            pts = gxCamerasRegionView3.getRight();
        }
        if (pts.size() < 3) {
            vError("Need at least 3 points to define a region.");
            return;
        }
        GxCamerasRegionView gxCamerasRegionView4 = this.vView;
        if (gxCamerasRegionView4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vView");
            gxCamerasRegionView4 = null;
        }
        gxCamerasRegionView4.setAnnotating(null);
        vSuccess((Intrinsics.areEqual(side, "left") ? "Left" : "Right") + " window annotated (" + pts.size() + " points)!");
        vPaint();
    }

    private final void vUndo() {
        ArrayList pts;
        GxCamerasRegionView gxCamerasRegionView = this.vView;
        GxCamerasRegionView gxCamerasRegionView2 = null;
        if (gxCamerasRegionView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vView");
            gxCamerasRegionView = null;
        }
        String side = gxCamerasRegionView.getAnnotating();
        if (side == null) {
            return;
        }
        if (Intrinsics.areEqual(side, "left")) {
            GxCamerasRegionView gxCamerasRegionView3 = this.vView;
            if (gxCamerasRegionView3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("vView");
            } else {
                gxCamerasRegionView2 = gxCamerasRegionView3;
            }
            pts = gxCamerasRegionView2.getLeft();
        } else {
            GxCamerasRegionView gxCamerasRegionView4 = this.vView;
            if (gxCamerasRegionView4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("vView");
            } else {
                gxCamerasRegionView2 = gxCamerasRegionView4;
            }
            pts = gxCamerasRegionView2.getRight();
        }
        if (!pts.isEmpty()) {
            pts.remove(pts.size() - 1);
        }
        vPaint();
    }

    private final void vClearSide(String side) {
        ArrayList<float[]> right;
        if (Intrinsics.areEqual(side, "left")) {
            GxCamerasRegionView gxCamerasRegionView = this.vView;
            if (gxCamerasRegionView == null) {
                Intrinsics.throwUninitializedPropertyAccessException("vView");
                gxCamerasRegionView = null;
            }
            right = gxCamerasRegionView.getLeft();
        } else {
            GxCamerasRegionView gxCamerasRegionView2 = this.vView;
            if (gxCamerasRegionView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("vView");
                gxCamerasRegionView2 = null;
            }
            right = gxCamerasRegionView2.getRight();
        }
        right.clear();
        GxCamerasRegionView gxCamerasRegionView3 = this.vView;
        if (gxCamerasRegionView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vView");
            gxCamerasRegionView3 = null;
        }
        gxCamerasRegionView3.setAnnotating(null);
        vPaint();
    }

    private final void vClearAll() {
        GxCamerasRegionView gxCamerasRegionView = this.vView;
        TextView textView = null;
        if (gxCamerasRegionView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vView");
            gxCamerasRegionView = null;
        }
        gxCamerasRegionView.getLeft().clear();
        GxCamerasRegionView gxCamerasRegionView2 = this.vView;
        if (gxCamerasRegionView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vView");
            gxCamerasRegionView2 = null;
        }
        gxCamerasRegionView2.getRight().clear();
        GxCamerasRegionView gxCamerasRegionView3 = this.vView;
        if (gxCamerasRegionView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vView");
            gxCamerasRegionView3 = null;
        }
        gxCamerasRegionView3.setAnnotating(null);
        this.vConfigSaved = false;
        TextView textView2 = this.vMsg;
        if (textView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vMsg");
        } else {
            textView = textView2;
        }
        msg(textView, "", false);
        vPaint();
    }

    private final void vSave() {
        GxCamerasRegionView v = this.vView;
        TextView textView = null;
        if (v == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vView");
            v = null;
        }
        if (v.getLeft().size() < 3 && v.getRight().size() < 3) {
            vError("Annotate at least one window with 3+ points.");
            return;
        }
        Bitmap bmp = v.getBmp();
        int nW = bmp != null ? bmp.getWidth() : 1920;
        Bitmap bmp2 = v.getBmp();
        int nH = bmp2 != null ? bmp2.getHeight() : 1080;
        float cw = v.getCw();
        float ch = v.getCh();
        JSONObject cfg = new JSONObject().put("width", nW).put("height", nH).put("poly_right", v.getLeft().size() >= 3 ? vSave$scale(cw, nW, nH, ch, v.getLeft()) : new JSONArray()).put("poly_left", v.getRight().size() >= 3 ? vSave$scale(cw, nW, nH, ch, v.getRight()) : new JSONArray());
        this.vSaving = true;
        TextView textView2 = this.vMsg;
        if (textView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vMsg");
        } else {
            textView = textView2;
        }
        msg(textView, "", false);
        vPaint();
        GxPage.send$default(this, "POST", "/api/v_asm/config", cfg, 0L, new Function2() { // from class: com.starpilot.starview.GxCamerasPage$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit vSave$lambda$97;
                vSave$lambda$97 = GxCamerasPage.vSave$lambda$97(GxCamerasPage.this, (String) obj, (JSONObject) obj2);
                return vSave$lambda$97;
            }
        }, 8, null);
    }

    private static final JSONArray vSave$scale(float cw, int nW, int nH, float ch, List<float[]> list) {
        int gxCamRound;
        int gxCamRound2;
        JSONArray $this$vSave_u24scale_u24lambda_u2494 = new JSONArray();
        for (float[] p : list) {
            JSONArray jSONArray = new JSONArray();
            gxCamRound = GxCamerasKt.gxCamRound(((cw - p[0]) * nW) / cw);
            JSONArray put = jSONArray.put(gxCamRound);
            gxCamRound2 = GxCamerasKt.gxCamRound((p[1] * nH) / ch);
            $this$vSave_u24scale_u24lambda_u2494.put(put.put(gxCamRound2));
        }
        return $this$vSave_u24scale_u24lambda_u2494;
    }

    /* VF: from Vineflower (jadx: Code restructure failed;incorrect) */
    public static final Unit vSave$lambda$97(GxCamerasPage var0, String var1, JSONObject var2) {
        var0.vSaving = false;
        if (var1 != null) {
            CharSequence var9 = var1;
            Object var6 = var9;
            if (StringsKt.isBlank(var9)) {
                var6 = "Failed to save";
            }

            var0.vError((String)var6);
            var0.vPaint();
            return Unit.INSTANCE;
        } else {
            label24: {
                var0.vConfigSaved = true;
                var0.vConfigExists = true;
                if (var2 != null) {
                    var1 = var2.optString("message");
                    if (var1 != null) {
                        CharSequence var7 = var1;
                        CharSequence var4 = var7;
                        if (StringsKt.isBlank(var7)) {
                            var4 = null;
                        }

                        String var8 = (String)var4;
                        var1 = var8;
                        if (var8 != null) {
                            break label24;
                        }
                    }
                }

                var1 = "Annotation config saved! V-ASM is now enabled.";
            }

            var0.vSuccess(var1);
            var0.toast("Annotation config saved! V-ASM is now enabled.");
            var0.loadVasmConfig();
            var0.vPaint();
            return Unit.INSTANCE;
        }
    }

    private final void vDelete() {
        confirm("Delete V-ASM Config?", "This clears your window annotations and disables V-ASM.", "Delete Config", new Function0() { // from class: com.starpilot.starview.GxCamerasPage$$ExternalSyntheticLambda29
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit vDelete$lambda$101;
                vDelete$lambda$101 = GxCamerasPage.vDelete$lambda$101(GxCamerasPage.this);
                return vDelete$lambda$101;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit vDelete$lambda$101(final GxCamerasPage this$0) {
        this$0.vSaving = true;
        TextView textView = this$0.vMsg;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vMsg");
            textView = null;
        }
        this$0.msg(textView, "", false);
        this$0.vPaint();
        GxPage.send$default(this$0, "DELETE", "/api/v_asm/config", null, 0L, new Function2() { // from class: com.starpilot.starview.GxCamerasPage$$ExternalSyntheticLambda47
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit vDelete$lambda$101$lambda$100;
                vDelete$lambda$101$lambda$100 = GxCamerasPage.vDelete$lambda$101$lambda$100(this$0, (String) obj, (JSONObject) obj2);
                return vDelete$lambda$101$lambda$100;
            }
        }, 12, null);
        return Unit.INSTANCE;
    }

    /* VF: from Vineflower (jadx: Code restructure failed;incorrect) */
    public static final Unit vDelete$lambda$101$lambda$100(GxCamerasPage var0, String var1, JSONObject var2) {
        var0.vSaving = false;
        if (var1 != null) {
            CharSequence var13 = var1;
            Object var11 = var13;
            if (StringsKt.isBlank(var13)) {
                var11 = "Failed to delete";
            }

            var0.vError((String)var11);
            var0.vPaint();
            return Unit.INSTANCE;
        } else {
            GxCamerasRegionView var4 = var0.vView;
            Object var3 = null;
            GxCamerasRegionView var5 = var4;
            if (var4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("vView");
                var5 = null;
            }

            var5.getLeft().clear();
            var4 = var0.vView;
            GxCamerasRegionView var6 = var4;
            if (var4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("vView");
                var6 = null;
            }

            var6.getRight().clear();
            var4 = var0.vView;
            GxCamerasRegionView var7 = var4;
            if (var4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("vView");
                var7 = null;
            }

            label31: {
                var7.setAnnotating(null);
                var0.vConfigSaved = false;
                var0.vConfigExists = false;
                var0.vConfig = null;
                if (var2 != null) {
                    var1 = var2.optString("message");
                    if (var1 != null) {
                        var1 = var1;
                        if (StringsKt.isBlank(var1)) {
                            var1 = (String)var3;
                        }

                        String var12 = var1;
                        var1 = var12;
                        if (var12 != null) {
                            break label31;
                        }
                    }
                }

                var1 = "Annotation config cleared.";
            }

            var0.vSuccess(var1);
            var0.toast("Annotation config cleared.");
            var0.vPaint();
            return Unit.INSTANCE;
        }
    }

    private final void loadVasmConfig() {
        if (this.vView == null) {
            return;
        }
        GxPage.json$default(this, "GET", "/api/v_asm/config", null, 0L, new Function2() { // from class: com.starpilot.starview.GxCamerasPage$$ExternalSyntheticLambda30
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit loadVasmConfig$lambda$102;
                loadVasmConfig$lambda$102 = GxCamerasPage.loadVasmConfig$lambda$102(GxCamerasPage.this, (String) obj, obj2);
                return loadVasmConfig$lambda$102;
            }
        }, 12, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit loadVasmConfig$lambda$102(GxCamerasPage this$0, String e, Object v) {
        JSONObject jSONObject = null;
        if (e == null && (v instanceof JSONObject)) {
            jSONObject = (JSONObject) v;
        }
        this$0.vConfig = jSONObject;
        this$0.vApplyConfig();
        return Unit.INSTANCE;
    }

    private final void vApplyConfig() {
        int intValue;
        int intValue2;
        GxCamerasRegionView v = this.vView;
        if (v == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vView");
            v = null;
        }
        JSONObject cfg = this.vConfig;
        v.getLeft().clear();
        v.getRight().clear();
        if (cfg == null) {
            this.vConfigExists = false;
            vPaint();
            return;
        }
        float cw = v.getCw();
        float ch = v.getCh();
        Bitmap bmp = v.getBmp();
        if (bmp != null) {
            intValue = bmp.getWidth();
        } else {
            Integer valueOf = Integer.valueOf(cfg.optInt("width", 1920));
            int it = valueOf.intValue();
            int it2 = it > 0 ? 1 : 0;
            if (it2 == 0) {
                valueOf = null;
            }
            intValue = valueOf != null ? valueOf.intValue() : 1920;
        }
        float nW = intValue;
        Bitmap bmp2 = v.getBmp();
        if (bmp2 != null) {
            intValue2 = bmp2.getHeight();
        } else {
            Integer valueOf2 = Integer.valueOf(cfg.optInt("height", 1080));
            int it3 = valueOf2.intValue();
            int it4 = it3 > 0 ? 1 : 0;
            Integer num = it4 != 0 ? valueOf2 : null;
            intValue2 = num != null ? num.intValue() : 1080;
        }
        float nH = intValue2;
        vApplyConfig$toCanvas(cw, nW, ch, nH, cfg.optJSONArray("poly_right"), v.getLeft());
        vApplyConfig$toCanvas(cw, nW, ch, nH, cfg.optJSONArray("poly_left"), v.getRight());
        this.vConfigExists = (v.getLeft().isEmpty() && v.getRight().isEmpty()) ? false : true;
        vPaint();
    }

    private static final void vApplyConfig$toCanvas(float cw, float nW, float ch, float nH, JSONArray a, List<float[]> list) {
        int gxCamRound;
        int gxCamRound2;
        if (a == null || a.length() < 3) {
            return;
        }
        int length = a.length();
        for (int i = 0; i < length; i++) {
            JSONArray p = a.optJSONArray(i);
            if (p != null) {
                gxCamRound = GxCamerasKt.gxCamRound(cw - ((p.optDouble(0) * cw) / nW));
                gxCamRound2 = GxCamerasKt.gxCamRound((p.optDouble(1) * ch) / nH);
                list.add(new float[]{gxCamRound, gxCamRound2});
            }
        }
    }

    private final void loadVasmSnapshot() {
        if (this.vView == null || this.vSnapLoading) {
            return;
        }
        TextView textView = this.vMsg;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vMsg");
            textView = null;
        }
        msg(textView, "", false);
        this.vSnapLoading = true;
        GxCamerasRegionView gxCamerasRegionView = this.vView;
        if (gxCamerasRegionView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vView");
            gxCamerasRegionView = null;
        }
        gxCamerasRegionView.setPlaceholder("Loading camera snapshot...");
        GxCamerasRegionView gxCamerasRegionView2 = this.vView;
        if (gxCamerasRegionView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vView");
            gxCamerasRegionView2 = null;
        }
        gxCamerasRegionView2.setBmp(null);
        vPaint();
        gxCamLoadBitmap("/api/v_asm/snapshot", new Function2() { // from class: com.starpilot.starview.GxCamerasPage$$ExternalSyntheticLambda31
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit loadVasmSnapshot$lambda$105;
                loadVasmSnapshot$lambda$105 = GxCamerasPage.loadVasmSnapshot$lambda$105(GxCamerasPage.this, (String) obj, (Bitmap) obj2);
                return loadVasmSnapshot$lambda$105;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit loadVasmSnapshot$lambda$105(GxCamerasPage this$0, String e, Bitmap b) {
        this$0.vSnapLoading = false;
        GxCamerasRegionView gxCamerasRegionView = null;
        if (b == null) {
            GxCamerasRegionView gxCamerasRegionView2 = this$0.vView;
            if (gxCamerasRegionView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("vView");
            } else {
                gxCamerasRegionView = gxCamerasRegionView2;
            }
            gxCamerasRegionView.setPlaceholder(e == null ? "Failed to load snapshot" : e);
            this$0.vError(e != null ? e : "Failed to load snapshot");
            this$0.vPaint();
            return Unit.INSTANCE;
        }
        GxCamerasRegionView gxCamerasRegionView3 = this$0.vView;
        if (gxCamerasRegionView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vView");
        } else {
            gxCamerasRegionView = gxCamerasRegionView3;
        }
        gxCamerasRegionView.setBmp(b);
        this$0.vSuccess("Camera snapshot loaded. Tap a window button above to start annotating.");
        this$0.vApplyConfig();
        return Unit.INSTANCE;
    }

    private final void pollVasm() {
        if (this.vStat.isEmpty()) {
            return;
        }
        for (String str : this.vStat.keySet()) {
            Intrinsics.checkNotNullExpressionValue(str, "next(...)");
            final String k = str;
            getText("/api/params_memory?key=" + enc(k), new Function2() { // from class: com.starpilot.starview.GxCamerasPage$$ExternalSyntheticLambda11
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    Unit pollVasm$lambda$108;
                    pollVasm$lambda$108 = GxCamerasPage.pollVasm$lambda$108(GxCamerasPage.this, k, (String) obj, (String) obj2);
                    return pollVasm$lambda$108;
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit pollVasm$lambda$108(GxCamerasPage this$0, String $k, String e, String t) {
        TextView tv;
        String str;
        int accent;
        Intrinsics.checkNotNullParameter(t, "t");
        if (e == null && (tv = this$0.vStat.get($k)) != null) {
            boolean z = false;
            if (StringsKt.endsWith($k, "Active", false)) {
                boolean on = Intrinsics.areEqual(StringsKt.trim((CharSequence) t).toString(), "1");
                tv.setText(on ? "YES" : "no");
                if (on) {
                    accent = Intrinsics.areEqual($k, "VASMLeftActive") ? CarUi.Companion.getACCENT() : GxCamerasKt.GX_CAM_ORANGE;
                } else {
                    accent = CarUi.Companion.getDIM();
                }
                tv.setTextColor(accent);
            } else {
                Double doubleOrNull = StringsKt.toDoubleOrNull(StringsKt.trim((CharSequence) t).toString());
                if (doubleOrNull != null) {
                    double it = doubleOrNull.doubleValue();
                    if (!Double.isInfinite(it) && !Double.isNaN(it)) {
                        z = true;
                    }
                    Double d = z ? doubleOrNull : null;
                    if (d != null) {
                        double it2 = d.doubleValue();
                        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
                        String format = String.format(Locale.US, "%.3f", Arrays.copyOf(new Object[]{Double.valueOf(it2)}, 1));
                        Intrinsics.checkNotNullExpressionValue(format, "format(...)");
                        if (format != null) {
                            str = format;
                            tv.setText(str);
                        }
                    }
                }
                tv.setText(str);
            }
            return Unit.INSTANCE;
        }
        return Unit.INSTANCE;
    }

    private final void buildPip(LinearLayout box) {
        int i;
        this.pMsg = msgView(box);
        LinearLayout bar1 = GxPage.buttons$default(this, new Pair[]{TuplesKt.to("Set Left Side of Vehicle - Left Here", new Function0() { // from class: com.starpilot.starview.GxCamerasPage$$ExternalSyntheticLambda53
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildPip$lambda$109;
                buildPip$lambda$109 = GxCamerasPage.buildPip$lambda$109(GxCamerasPage.this);
                return buildPip$lambda$109;
            }
        }), TuplesKt.to("Set Right Side of Vehicle - Right Here", new Function0() { // from class: com.starpilot.starview.GxCamerasPage$$ExternalSyntheticLambda56
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildPip$lambda$110;
                buildPip$lambda$110 = GxCamerasPage.buildPip$lambda$110(GxCamerasPage.this);
                return buildPip$lambda$110;
            }
        })}, box, false, 4, null);
        View childAt = bar1.getChildAt(0);
        Intrinsics.checkNotNull(childAt, "null cannot be cast to non-null type android.widget.TextView");
        this.pLeftBtn = (TextView) childAt;
        View childAt2 = bar1.getChildAt(1);
        Intrinsics.checkNotNull(childAt2, "null cannot be cast to non-null type android.widget.TextView");
        this.pRightBtn = (TextView) childAt2;
        LinearLayout bar2 = buttons(new Pair[]{TuplesKt.to("Save Mask", new Function0() { // from class: com.starpilot.starview.GxCamerasPage$$ExternalSyntheticLambda57
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildPip$lambda$111;
                buildPip$lambda$111 = GxCamerasPage.buildPip$lambda$111(GxCamerasPage.this);
                return buildPip$lambda$111;
            }
        }), TuplesKt.to("Delete Mask", new Function0() { // from class: com.starpilot.starview.GxCamerasPage$$ExternalSyntheticLambda58
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildPip$lambda$112;
                buildPip$lambda$112 = GxCamerasPage.buildPip$lambda$112(GxCamerasPage.this);
                return buildPip$lambda$112;
            }
        }), TuplesKt.to("Clear All", new Function0() { // from class: com.starpilot.starview.GxCamerasPage$$ExternalSyntheticLambda59
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildPip$lambda$113;
                buildPip$lambda$113 = GxCamerasPage.buildPip$lambda$113(GxCamerasPage.this);
                return buildPip$lambda$113;
            }
        }), TuplesKt.to("Get a new Snapshot", new Function0() { // from class: com.starpilot.starview.GxCamerasPage$$ExternalSyntheticLambda60
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildPip$lambda$114;
                buildPip$lambda$114 = GxCamerasPage.buildPip$lambda$114(GxCamerasPage.this);
                return buildPip$lambda$114;
            }
        })}, box, true);
        View childAt3 = bar2.getChildAt(0);
        Intrinsics.checkNotNull(childAt3, "null cannot be cast to non-null type android.widget.TextView");
        this.pSaveBtn = (TextView) childAt3;
        View childAt4 = bar2.getChildAt(1);
        Intrinsics.checkNotNull(childAt4, "null cannot be cast to non-null type android.widget.TextView");
        this.pDeleteBtn = (TextView) childAt4;
        TextView textView = this.pDeleteBtn;
        TextView textView2 = null;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pDeleteBtn");
            textView = null;
        }
        i = GxCamerasKt.GX_CAM_RED;
        textView.setTextColor(i);
        View childAt5 = bar2.getChildAt(3);
        Intrinsics.checkNotNull(childAt5, "null cannot be cast to non-null type android.widget.TextView");
        this.pSnapBtn = (TextView) childAt5;
        TextView $this$buildPip_u24lambda_u24115 = new TextView(getAct());
        $this$buildPip_u24lambda_u24115.setTextSize(17.0f);
        $this$buildPip_u24lambda_u24115.setPadding(getUi().dp(18), getUi().dp(12), getUi().dp(18), getUi().dp(12));
        $this$buildPip_u24lambda_u24115.setVisibility(8);
        this.pArmNote = $this$buildPip_u24lambda_u24115;
        TextView textView3 = this.pArmNote;
        if (textView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pArmNote");
            textView3 = null;
        }
        box.addView(textView3, CarUi.lp$default(getUi(), 0, 0, 3, null));
        this.pView = new GxCamerasPipView(getAct());
        GxCamerasPipView gxCamerasPipView = this.pView;
        if (gxCamerasPipView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pView");
            gxCamerasPipView = null;
        }
        gxCamerasPipView.setOnChanged(new Function0() { // from class: com.starpilot.starview.GxCamerasPage$$ExternalSyntheticLambda61
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildPip$lambda$116;
                buildPip$lambda$116 = GxCamerasPage.buildPip$lambda$116(GxCamerasPage.this);
                return buildPip$lambda$116;
            }
        });
        GxCamerasPipView gxCamerasPipView2 = this.pView;
        if (gxCamerasPipView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pView");
            gxCamerasPipView2 = null;
        }
        gxCamerasPipView2.setOnPlaced(new Function0() { // from class: com.starpilot.starview.GxCamerasPage$$ExternalSyntheticLambda62
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildPip$lambda$117;
                buildPip$lambda$117 = GxCamerasPage.buildPip$lambda$117(GxCamerasPage.this);
                return buildPip$lambda$117;
            }
        });
        GxCamerasPipView gxCamerasPipView3 = this.pView;
        if (gxCamerasPipView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pView");
            gxCamerasPipView3 = null;
        }
        gxCamerasPipView3.setOnWrongHalf(new Function1() { // from class: com.starpilot.starview.GxCamerasPage$$ExternalSyntheticLambda63
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit buildPip$lambda$118;
                buildPip$lambda$118 = GxCamerasPage.buildPip$lambda$118(GxCamerasPage.this, (String) obj);
                return buildPip$lambda$118;
            }
        });
        GxCamerasPipView gxCamerasPipView4 = this.pView;
        if (gxCamerasPipView4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pView");
            gxCamerasPipView4 = null;
        }
        LinearLayout.LayoutParams $this$buildPip_u24lambda_u24119 = new LinearLayout.LayoutParams(-1, -2);
        $this$buildPip_u24lambda_u24119.bottomMargin = getUi().dp(4);
        Unit unit = Unit.INSTANCE;
        box.addView(gxCamerasPipView4, $this$buildPip_u24lambda_u24119);
        TextView $this$buildPip_u24lambda_u24120 = new TextView(getAct());
        $this$buildPip_u24lambda_u24120.setTextSize(15.0f);
        $this$buildPip_u24lambda_u24120.setTextColor(CarUi.Companion.getDIM());
        $this$buildPip_u24lambda_u24120.setGravity(17);
        $this$buildPip_u24lambda_u24120.setPadding(0, getUi().dp(2), 0, getUi().dp(10));
        this.pCaption = $this$buildPip_u24lambda_u24120;
        TextView textView4 = this.pCaption;
        if (textView4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pCaption");
        } else {
            textView2 = textView4;
        }
        box.addView(textView2);
        this.pZoomSet = stepper("Zoom", "Applied to both windows so the preview stays consistent. Lower is wider, higher is closer.", 240.0d, 60.0d, 640.0d, 5.0d, new Function1() { // from class: com.starpilot.starview.GxCamerasPage$$ExternalSyntheticLambda64
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                String buildPip$lambda$121;
                buildPip$lambda$121 = GxCamerasPage.buildPip$lambda$121(((Double) obj).doubleValue());
                return buildPip$lambda$121;
            }
        }, box, new Function1() { // from class: com.starpilot.starview.GxCamerasPage$$ExternalSyntheticLambda54
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit buildPip$lambda$122;
                buildPip$lambda$122 = GxCamerasPage.buildPip$lambda$122(GxCamerasPage.this, ((Double) obj).doubleValue());
                return buildPip$lambda$122;
            }
        });
        note("This preview is mirrored to match the normal on-road driver-camera view: the LEFT side of the vehicle is always on the LEFT here. Place a center point on each window (drag a point to move it); at least one window center is required to enable the preview. Enable \"PiP Side Preview\" in Toggles -> Visual (Display & UI) -> Driving Screen Widgets.", box);
        pPaint();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildPip$lambda$109(GxCamerasPage this$0) {
        this$0.pArm("left");
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildPip$lambda$110(GxCamerasPage this$0) {
        this$0.pArm("right");
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildPip$lambda$111(GxCamerasPage this$0) {
        this$0.pSave();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildPip$lambda$112(GxCamerasPage this$0) {
        this$0.pDelete();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildPip$lambda$113(GxCamerasPage this$0) {
        this$0.pClearAll();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildPip$lambda$114(GxCamerasPage this$0) {
        this$0.loadPipSnapshot();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildPip$lambda$116(GxCamerasPage this$0) {
        this$0.pPaint();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildPip$lambda$117(GxCamerasPage this$0) {
        TextView textView = this$0.pMsg;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pMsg");
            textView = null;
        }
        this$0.msg(textView, "", false);
        this$0.pPaint();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildPip$lambda$118(GxCamerasPage this$0, String side) {
        Intrinsics.checkNotNullParameter(side, "side");
        TextView textView = this$0.pMsg;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pMsg");
            textView = null;
        }
        this$0.msg(textView, Intrinsics.areEqual(side, "left") ? "LEFT SIDE OF VEHICLE is on the LEFT. Tap the left half of the preview." : "RIGHT SIDE OF VEHICLE is on the RIGHT. Tap the right half of the preview.", true);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String buildPip$lambda$121(double it) {
        int gxCamRound;
        gxCamRound = GxCamerasKt.gxCamRound(it);
        return gxCamRound + " px";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildPip$lambda$122(GxCamerasPage this$0, double v) {
        GxCamerasPipView gxCamerasPipView = this$0.pView;
        GxCamerasPipView gxCamerasPipView2 = null;
        if (gxCamerasPipView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pView");
            gxCamerasPipView = null;
        }
        gxCamerasPipView.setZoom((float) v);
        GxCamerasPipView gxCamerasPipView3 = this$0.pView;
        if (gxCamerasPipView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pView");
        } else {
            gxCamerasPipView2 = gxCamerasPipView3;
        }
        gxCamerasPipView2.invalidate();
        return Unit.INSTANCE;
    }

    /* VF: from Vineflower (jadx: Removed duplicated region;incorrect) */
    private final void pPaint() {
        if (this.pView != null) {
            GxCamerasPipView var5 = this.pView;
            Object var6 = null;
            GxCamerasPipView var4 = var5;
            if (var5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("pView");
                var4 = null;
            }

            String var8 = var4.getArmSide();
            TextView var29 = this.pLeftBtn;
            TextView var10 = var29;
            if (var29 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("pLeftBtn");
                var10 = null;
            }

            this.tint(var10, GxCamerasKt.GX_CAM_BLUE, Intrinsics.areEqual(var8, "left"));
            TextView var30 = this.pRightBtn;
            TextView var11 = var30;
            if (var30 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("pRightBtn");
                var11 = null;
            }

            this.tint(var11, GxCamerasKt.GX_CAM_ORANGE, Intrinsics.areEqual(var8, "right"));
            TextView var31 = this.pLeftBtn;
            TextView var12 = var31;
            if (var31 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("pLeftBtn");
                var12 = null;
            }

            GxCamerasPipView var7 = this.pView;
            var5 = var7;
            if (var7 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("pView");
                var5 = null;
            }

            String var33;
            if (var5.getLeftC() != null) {
                var33 = "Move Left Side of Vehicle - Left Here";
            } else {
                var33 = "Set Left Side of Vehicle - Left Here";
            }

            var12.setText(var33);
            TextView var34 = this.pRightBtn;
            TextView var13 = var34;
            if (var34 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("pRightBtn");
                var13 = null;
            }

            var7 = this.pView;
            var5 = var7;
            if (var7 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("pView");
                var5 = null;
            }

            String var36;
            if (var5.getRightC() != null) {
                var36 = "Move Right Side of Vehicle - Right Here";
            } else {
                var36 = "Set Right Side of Vehicle - Right Here";
            }

            var13.setText(var36);
            var5 = this.pView;
            var4 = var5;
            if (var5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("pView");
                var4 = null;
            }

            int var2;
            label145: {
                if (var4.getLeftC() == null) {
                    var5 = this.pView;
                    var4 = var5;
                    if (var5 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("pView");
                        var4 = null;
                    }

                    if (var4.getRightC() == null) {
                        var2 = 0;
                        break label145;
                    }
                }

                var2 = 1;
            }

            TextView var39 = this.pSaveBtn;
            TextView var16 = var39;
            if (var39 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("pSaveBtn");
                var16 = null;
            }

            String var40;
            if (this.pLoading) {
                var40 = "Saving...";
            } else {
                var40 = "Save Mask";
            }

            var16.setText(var40);
            TextView var41 = this.pSaveBtn;
            TextView var17 = var41;
            if (var41 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("pSaveBtn");
                var17 = null;
            }

            boolean var3;
            if (!this.pLoading && (var2 != 0)) {
                var3 = true;
            } else {
                var3 = false;
            }

            var17.setEnabled(var3);
            TextView var42 = this.pSaveBtn;
            TextView var18 = var42;
            if (var42 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("pSaveBtn");
                var18 = null;
            }

            TextView var56 = this.pSaveBtn;
            TextView var43 = var56;
            if (var56 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("pSaveBtn");
                var43 = null;
            }

            float var1;
            if (var43.isEnabled()) {
                var1 = 1.0F;
            } else {
                var1 = 0.45F;
            }

            var18.setAlpha(var1);
            TextView var44 = this.pDeleteBtn;
            TextView var19 = var44;
            if (var44 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("pDeleteBtn");
                var19 = null;
            }

            this.showIf((View)var19, this.pConfigExists);
            TextView var45 = this.pDeleteBtn;
            TextView var20 = var45;
            if (var45 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("pDeleteBtn");
                var20 = null;
            }

            var20.setEnabled(true ^ this.pLoading);
            TextView var46 = this.pSnapBtn;
            TextView var21 = var46;
            if (var46 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("pSnapBtn");
                var21 = null;
            }

            this.busy(var21, this.pSnapLoading, "Get a new Snapshot", "Loading...");
            if (var8 != null) {
                if (Intrinsics.areEqual(var8, "left")) {
                    var2 = GxCamerasKt.GX_CAM_BLUE;
                } else {
                    var2 = GxCamerasKt.GX_CAM_ORANGE;
                }

                TextView var47 = this.pArmNote;
                TextView var22 = var47;
                if (var47 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("pArmNote");
                    var22 = null;
                }

                String var48;
                if (Intrinsics.areEqual(var8, "left")) {
                    var48 = "LEFT SIDE OF VEHICLE - LEFT HERE";
                } else {
                    var48 = "RIGHT SIDE OF VEHICLE - RIGHT HERE";
                }

                var22.setText(var48 + "\nTap the matching side of the mirrored preview");
                TextView var49 = this.pArmNote;
                TextView var23 = var49;
                if (var49 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("pArmNote");
                    var23 = null;
                }

                var23.setTextColor(var2);
                TextView var50 = this.pArmNote;
                TextView var24 = var50;
                if (var50 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("pArmNote");
                    var24 = null;
                }

                var24.setBackground((Drawable)this.getUi().rounded(CarUi.Companion.getCARD(), 12, var2));
                TextView var51 = this.pArmNote;
                TextView var25 = var51;
                if (var51 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("pArmNote");
                    var25 = null;
                }

                var25.setVisibility(0);
            } else {
                TextView var52 = this.pArmNote;
                TextView var26 = var52;
                if (var52 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("pArmNote");
                    var26 = null;
                }

                var26.setVisibility(8);
            }

            TextView var53 = this.pCaption;
            TextView var27 = var53;
            if (var53 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("pCaption");
                var27 = null;
            }

            String var54;
            if (var8 != null) {
                var54 = "Tap the window to place its center point.";
            } else {
                var54 = "Place a center point on each window, then use the zoom control below.";
            }

            var27.setText(var54);
            var4 = this.pView;
            if (var4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("pView");
                var4 = (GxCamerasPipView)var6;
            }

            var4.invalidate();
        }
    }

    private final void pArm(String side) {
        GxCamerasPipView gxCamerasPipView = this.pView;
        TextView textView = null;
        if (gxCamerasPipView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pView");
            gxCamerasPipView = null;
        }
        gxCamerasPipView.setArmSide(side);
        TextView textView2 = this.pMsg;
        if (textView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pMsg");
        } else {
            textView = textView2;
        }
        msg(textView, "", false);
        pPaint();
    }

    private final void pClearAll() {
        GxCamerasPipView gxCamerasPipView = this.pView;
        if (gxCamerasPipView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pView");
            gxCamerasPipView = null;
        }
        gxCamerasPipView.setLeftC(null);
        GxCamerasPipView gxCamerasPipView2 = this.pView;
        if (gxCamerasPipView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pView");
            gxCamerasPipView2 = null;
        }
        gxCamerasPipView2.setRightC(null);
        GxCamerasPipView gxCamerasPipView3 = this.pView;
        if (gxCamerasPipView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pView");
            gxCamerasPipView3 = null;
        }
        gxCamerasPipView3.setArmSide(null);
        this.pConfigSaved = false;
        pPaint();
    }

    private final Pair<Float, Float> pNative() {
        GxCamerasPipView gxCamerasPipView = this.pView;
        GxCamerasPipView gxCamerasPipView2 = null;
        if (gxCamerasPipView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pView");
            gxCamerasPipView = null;
        }
        Float valueOf = Float.valueOf(gxCamerasPipView.getBmp() != null ? r0.getWidth() : 1920);
        GxCamerasPipView gxCamerasPipView3 = this.pView;
        if (gxCamerasPipView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pView");
        } else {
            gxCamerasPipView2 = gxCamerasPipView3;
        }
        return TuplesKt.to(valueOf, Float.valueOf(gxCamerasPipView2.getBmp() != null ? r1.getHeight() : 1080));
    }

    private final void pSave() {
        int gxCamRound;
        int gxCamRound2;
        int gxCamRound3;
        GxCamerasPipView v = this.pView;
        TextView textView = null;
        if (v == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pView");
            v = null;
        }
        if (v.getLeftC() == null && v.getRightC() == null) {
            TextView textView2 = this.pMsg;
            if (textView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("pMsg");
            } else {
                textView = textView2;
            }
            msg(textView, "Place at least one center point.", true);
            return;
        }
        Pair<Float, Float> pNative = pNative();
        float nW = pNative.component1().floatValue();
        float nH = pNative.component2().floatValue();
        float cw = v.getCw();
        float ch = v.getCh();
        JSONObject jSONObject = new JSONObject();
        gxCamRound = GxCamerasKt.gxCamRound(nW);
        JSONObject put = jSONObject.put("width", gxCamRound);
        gxCamRound2 = GxCamerasKt.gxCamRound(nH);
        JSONObject put2 = put.put("height", gxCamRound2).put("center_left", pSave$toNative(cw, nW, nH, ch, v.getRightC())).put("center_right", pSave$toNative(cw, nW, nH, ch, v.getLeftC()));
        gxCamRound3 = GxCamerasKt.gxCamRound((v.getZoom() * nW) / cw);
        JSONObject cfg = put2.put("crop_size", gxCamRound3);
        this.pLoading = true;
        TextView textView3 = this.pMsg;
        if (textView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pMsg");
        } else {
            textView = textView3;
        }
        msg(textView, "", false);
        pPaint();
        GxPage.send$default(this, "POST", "/api/pip_preview/config", cfg, 0L, new Function2() { // from class: com.starpilot.starview.GxCamerasPage$$ExternalSyntheticLambda67
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit pSave$lambda$125;
                pSave$lambda$125 = GxCamerasPage.pSave$lambda$125(GxCamerasPage.this, (String) obj, (JSONObject) obj2);
                return pSave$lambda$125;
            }
        }, 8, null);
    }

    private static final JSONArray pSave$toNative(float cw, float nW, float nH, float ch, float[] c) {
        int gxCamRound;
        int gxCamRound2;
        if (c != null) {
            JSONArray jSONArray = new JSONArray();
            gxCamRound = GxCamerasKt.gxCamRound(((cw - c[0]) * nW) / cw);
            JSONArray put = jSONArray.put(gxCamRound);
            gxCamRound2 = GxCamerasKt.gxCamRound((c[1] * nH) / ch);
            JSONArray put2 = put.put(gxCamRound2);
            Intrinsics.checkNotNullExpressionValue(put2, "put(...)");
            return put2;
        }
        return new JSONArray();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit pSave$lambda$125(GxCamerasPage this$0, String e, JSONObject jSONObject) {
        this$0.pLoading = false;
        TextView textView = null;
        if (e == null) {
            this$0.pConfigSaved = true;
            this$0.pConfigExists = true;
            TextView textView2 = this$0.pMsg;
            if (textView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("pMsg");
            } else {
                textView = textView2;
            }
            this$0.msg(textView, "PiP Preview mask saved!", false);
            this$0.toast("PiP Preview mask saved!");
            this$0.loadPipConfig();
            this$0.pPaint();
            return Unit.INSTANCE;
        }
        TextView textView3 = this$0.pMsg;
        if (textView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pMsg");
        } else {
            textView = textView3;
        }
        String str = e;
        if (StringsKt.isBlank(str)) {
            str = "Failed to save";
        }
        this$0.msg(textView, str, true);
        String str2 = e;
        if (StringsKt.isBlank(str2)) {
            str2 = "Save failed...";
        }
        this$0.toast(str2);
        this$0.pPaint();
        return Unit.INSTANCE;
    }

    private final void pDelete() {
        confirm("Confirm Delete", "Are you sure you want to delete the PiP Side Camera mask?", "Yes, Delete", new Function0() { // from class: com.starpilot.starview.GxCamerasPage$$ExternalSyntheticLambda36
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit pDelete$lambda$129;
                pDelete$lambda$129 = GxCamerasPage.pDelete$lambda$129(GxCamerasPage.this);
                return pDelete$lambda$129;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit pDelete$lambda$129(final GxCamerasPage this$0) {
        this$0.pLoading = true;
        TextView textView = this$0.pMsg;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pMsg");
            textView = null;
        }
        this$0.msg(textView, "", false);
        this$0.pPaint();
        GxPage.send$default(this$0, "DELETE", "/api/pip_preview/config", null, 0L, new Function2() { // from class: com.starpilot.starview.GxCamerasPage$$ExternalSyntheticLambda23
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit pDelete$lambda$129$lambda$128;
                pDelete$lambda$129$lambda$128 = GxCamerasPage.pDelete$lambda$129$lambda$128(this$0, (String) obj, (JSONObject) obj2);
                return pDelete$lambda$129$lambda$128;
            }
        }, 12, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit pDelete$lambda$129$lambda$128(GxCamerasPage this$0, String e, JSONObject jSONObject) {
        this$0.pLoading = false;
        TextView textView = null;
        if (e != null) {
            TextView textView2 = this$0.pMsg;
            if (textView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("pMsg");
            } else {
                textView = textView2;
            }
            String str = e;
            if (StringsKt.isBlank(str)) {
                str = "Failed to delete";
            }
            this$0.msg(textView, str, true);
            String str2 = e;
            if (StringsKt.isBlank(str2)) {
                str2 = "Delete failed...";
            }
            this$0.toast(str2);
            this$0.pPaint();
            return Unit.INSTANCE;
        }
        GxCamerasPipView gxCamerasPipView = this$0.pView;
        if (gxCamerasPipView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pView");
            gxCamerasPipView = null;
        }
        gxCamerasPipView.setLeftC(null);
        GxCamerasPipView gxCamerasPipView2 = this$0.pView;
        if (gxCamerasPipView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pView");
            gxCamerasPipView2 = null;
        }
        gxCamerasPipView2.setRightC(null);
        GxCamerasPipView gxCamerasPipView3 = this$0.pView;
        if (gxCamerasPipView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pView");
            gxCamerasPipView3 = null;
        }
        gxCamerasPipView3.setArmSide(null);
        this$0.pConfigSaved = false;
        this$0.pConfigExists = false;
        this$0.pConfig = null;
        TextView textView3 = this$0.pMsg;
        if (textView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pMsg");
        } else {
            textView = textView3;
        }
        this$0.msg(textView, "PiP Preview mask cleared.", false);
        this$0.toast("PiP Preview mask cleared.");
        this$0.pPaint();
        return Unit.INSTANCE;
    }

    private final void loadPipConfig() {
        if (this.pView == null) {
            return;
        }
        getJson("/api/pip_preview/config", new Function2() { // from class: com.starpilot.starview.GxCamerasPage$$ExternalSyntheticLambda39
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit loadPipConfig$lambda$132;
                loadPipConfig$lambda$132 = GxCamerasPage.loadPipConfig$lambda$132(GxCamerasPage.this, (String) obj, (JSONObject) obj2);
                return loadPipConfig$lambda$132;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit loadPipConfig$lambda$132(GxCamerasPage gxCamerasPage, String str, JSONObject jSONObject) {
        TextView textView = null;
        GxCamerasPipView gxCamerasPipView = null;
        if (str != null || jSONObject == null) {
            if (str != null) {
                TextView textView2 = gxCamerasPage.pMsg;
                if (textView2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("pMsg");
                    textView2 = null;
                }
                if (textView2.getVisibility() != 0) {
                    TextView textView3 = gxCamerasPage.pMsg;
                    if (textView3 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("pMsg");
                    } else {
                        textView = textView3;
                    }
                    gxCamerasPage.msg(textView, str, true);
                }
            }
            return Unit.INSTANCE;
        }
        String str$default = GxPage.str$default(gxCamerasPage, jSONObject, "device_type", null, 2, null);
        if (StringsKt.isBlank(str$default)) {
            str$default = null;
        }
        String str2 = str$default;
        if (str2 != null) {
            GxCamerasPipView gxCamerasPipView2 = gxCamerasPage.pView;
            if (gxCamerasPipView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("pView");
            } else {
                gxCamerasPipView = gxCamerasPipView2;
            }
            Locale US = Locale.US;
            Intrinsics.checkNotNullExpressionValue(US, "US");
            String lowerCase = str2.toLowerCase(US);
            Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
            gxCamerasPipView.setC4(Intrinsics.areEqual(lowerCase, "mici"));
        }
        gxCamerasPage.pConfig = jSONObject.optJSONObject("mask");
        gxCamerasPage.pApplyConfig();
        return Unit.INSTANCE;
    }

    private final void pApplyConfig() {
        int gxCamRound;
        Function1<? super Double, Unit> function1;
        GxCamerasPipView v = this.pView;
        if (v == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pView");
            v = null;
        }
        JSONObject cfg = this.pConfig;
        if (cfg == null) {
            v.setLeftC(null);
            v.setRightC(null);
            this.pConfigExists = false;
            pPaint();
            return;
        }
        Pair<Float, Float> pNative = pNative();
        float nW = pNative.component1().floatValue();
        float nH = pNative.component2().floatValue();
        float cw = v.getCw();
        float ch = v.getCh();
        v.setLeftC(pApplyConfig$toCanvas$133(cw, nW, ch, nH, cfg.optJSONArray("center_right")));
        v.setRightC(pApplyConfig$toCanvas$133(cw, nW, ch, nH, cfg.optJSONArray("center_left")));
        if (cfg.has("crop_size")) {
            Double valueOf = Double.valueOf(cfg.optDouble("crop_size"));
            double it = valueOf.doubleValue();
            if (!((Double.isInfinite(it) || Double.isNaN(it)) ? false : true)) {
                valueOf = null;
            }
            if (valueOf != null) {
                double cs = valueOf.doubleValue();
                gxCamRound = GxCamerasKt.gxCamRound((cw * cs) / nW);
                double z = gxCamRound;
                v.setZoom((float) z);
                Function1<? super Double, Unit> function12 = this.pZoomSet;
                if (function12 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("pZoomSet");
                    function1 = null;
                } else {
                    function1 = function12;
                }
                function1.invoke(Double.valueOf(z));
                this.pConfigExists = v.getLeftC() == null || v.getRightC() != null;
                pPaint();
            }
        }
        this.pConfigExists = v.getLeftC() == null || v.getRightC() != null;
        pPaint();
    }

    private static final float[] pApplyConfig$toCanvas$133(float cw, float nW, float ch, float nH, JSONArray a) {
        int gxCamRound;
        int gxCamRound2;
        if (a == null || a.length() < 2) {
            return null;
        }
        gxCamRound = GxCamerasKt.gxCamRound(cw - ((a.optDouble(0) * cw) / nW));
        gxCamRound2 = GxCamerasKt.gxCamRound((a.optDouble(1) * ch) / nH);
        return new float[]{gxCamRound, gxCamRound2};
    }

    private final void loadPipSnapshot() {
        if (this.pView == null || this.pSnapLoading) {
            return;
        }
        TextView textView = this.pMsg;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pMsg");
            textView = null;
        }
        msg(textView, "", false);
        this.pSnapLoading = true;
        GxCamerasPipView gxCamerasPipView = this.pView;
        if (gxCamerasPipView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pView");
            gxCamerasPipView = null;
        }
        gxCamerasPipView.setPlaceholder("Loading camera snapshot...");
        GxCamerasPipView gxCamerasPipView2 = this.pView;
        if (gxCamerasPipView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pView");
            gxCamerasPipView2 = null;
        }
        gxCamerasPipView2.setBmp(null);
        pPaint();
        gxCamLoadBitmap("/api/pip_preview/snapshot", new Function2() { // from class: com.starpilot.starview.GxCamerasPage$$ExternalSyntheticLambda42
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit loadPipSnapshot$lambda$136;
                loadPipSnapshot$lambda$136 = GxCamerasPage.loadPipSnapshot$lambda$136(GxCamerasPage.this, (String) obj, (Bitmap) obj2);
                return loadPipSnapshot$lambda$136;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit loadPipSnapshot$lambda$136(GxCamerasPage this$0, String e, Bitmap b) {
        this$0.pSnapLoading = false;
        TextView textView = null;
        if (b == null) {
            GxCamerasPipView gxCamerasPipView = this$0.pView;
            if (gxCamerasPipView == null) {
                Intrinsics.throwUninitializedPropertyAccessException("pView");
                gxCamerasPipView = null;
            }
            gxCamerasPipView.setPlaceholder(e == null ? "Failed to load snapshot" : e);
            TextView textView2 = this$0.pMsg;
            if (textView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("pMsg");
            } else {
                textView = textView2;
            }
            this$0.msg(textView, e != null ? e : "Failed to load snapshot", true);
            this$0.pPaint();
            return Unit.INSTANCE;
        }
        GxCamerasPipView gxCamerasPipView2 = this$0.pView;
        if (gxCamerasPipView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pView");
            gxCamerasPipView2 = null;
        }
        gxCamerasPipView2.setBmp(b);
        TextView textView3 = this$0.pMsg;
        if (textView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pMsg");
        } else {
            textView = textView3;
        }
        this$0.msg(textView, "Camera snapshot loaded. Place a center point on each window, then adjust the zoom.", false);
        this$0.pApplyConfig();
        return Unit.INSTANCE;
    }
}
