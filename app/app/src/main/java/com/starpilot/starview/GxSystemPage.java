package com.starpilot.starview;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import com.starpilot.starview.CarUi;
import com.starpilot.starview.GxPage;
import com.starpilot.starview.GxSystemPage;
import java.io.OutputStream;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import kotlin.Pair;
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
import kotlin.jvm.internal.Ref;
import kotlin.ranges.RangesKt;
import kotlin.text.Charsets;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/* compiled from: GxSystem.kt */
public final class GxSystemPage extends GxPage {
    private LinearLayout advBox;
    private TextView advBtn;
    private boolean advOpen;
    private String agnosBusyFor;
    private final HashMap<String, JSONObject> agnosByBranch;
    private final HashMap<String, String> agnosErrByBranch;
    private boolean autoBusy;
    private GxPage.Row autoRow;
    private Switch autoSwitch;
    private boolean branchBusy;
    private boolean branchListFallback;
    private boolean branchLoading;
    private List<String> branches;
    private String busy;
    private TextView checkBtn;
    private boolean checkedForUpdates;
    private String currentBranch;
    private final DateTimeFormatter dayFmt;
    private final HashMap<String, Boolean> expanded;
    private LinearLayout factoryBox;
    private GxPage.Row fastRow;
    private JSONObject fastStatus;
    private boolean isOnroad;
    private final DateTimeFormatter keyFmt;
    private TextView loadingNote;
    private final boolean needsOffroad;
    private LinearLayout noticeBox;
    private boolean otherOpen;
    private boolean pickerOpen;
    private String profileBusy;
    private LinearLayout profileNotice;
    private List<? extends JSONObject> profiles;
    private LinearLayout profilesBox;
    private boolean rebootOfflineSeen;
    private boolean rebootPending;
    private long rebootStartedAt;
    private boolean reconnectedNotice;
    private TextView recoverBtn;
    private TextView rollbackBtn;
    private String selectedCommit;
    private final DateTimeFormatter stampFmt;
    private LinearLayout statusBox;
    private boolean statusInFlight;
    private boolean statusUnavailable;
    private LinearLayout swWrap;
    private String targetBranch;
    private final DateTimeFormatter timeFmt;
    private LinearLayout tsBox;
    private boolean tsBusy;
    private boolean tsInstalled;
    private boolean tsLoaded;
    private TextView upToDateNote;
    private final ArrayList<JSONObject> versionCommits;
    private String versionError;
    private int versionGen;
    private boolean versionHasMore;
    private String versionHead;
    private boolean versionLoading;
    private String versionMode;
    private String versionNotice;
    private int versionPage;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GxSystemPage(Activity act, GxHost gx) {
        super(act, gx);
        Intrinsics.checkNotNullParameter(act, "act");
        Intrinsics.checkNotNullParameter(gx, "gx");
        this.needsOffroad = true;
        this.branches = CollectionsKt.emptyList();
        this.currentBranch = "";
        this.targetBranch = "";
        this.versionMode = "latest";
        this.selectedCommit = "";
        this.versionCommits = new ArrayList<>();
        this.versionHead = "";
        this.versionError = "";
        this.versionNotice = "";
        this.branchLoading = true;
        this.expanded = new HashMap<>();
        this.busy = "";
        this.agnosByBranch = new HashMap<>();
        this.agnosErrByBranch = new HashMap<>();
        this.agnosBusyFor = "";
        this.profiles = CollectionsKt.emptyList();
        this.profileBusy = "";
        this.dayFmt = DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.getDefault()).withZone(ZoneId.systemDefault());
        this.timeFmt = DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM).withLocale(Locale.getDefault()).withZone(ZoneId.systemDefault());
        this.keyFmt = DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(ZoneId.systemDefault());
        this.stampFmt = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).withLocale(Locale.getDefault()).withZone(ZoneId.systemDefault());
    }

    @Override // com.starpilot.starview.GxPage
    public boolean getNeedsOffroad() {
        return this.needsOffroad;
    }

    private final boolean getStatusRebooting() {
        String str;
        String str$default;
        String obj;
        JSONObject jSONObject = this.fastStatus;
        if (jSONObject == null || (str$default = GxPage.str$default(this, jSONObject, "stage", null, 2, null)) == null || (obj = StringsKt.trim((CharSequence) str$default).toString()) == null) {
            str = null;
        } else {
            str = obj.toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(str, "toLowerCase(...)");
        }
        return Intrinsics.areEqual(str, "rebooting");
    }

    private final boolean getUpdateInProgress() {
        JSONObject jSONObject = this.fastStatus;
        return (jSONObject != null && jSONObject.optBoolean("running")) || getStatusRebooting() || this.rebootPending;
    }

    private final boolean getStatusPollingNeeded() {
        return this.fastStatus == null || getUpdateInProgress() || this.rebootPending;
    }

    private final boolean getBranchSwitchBlocked() {
        if (this.branchLoading || this.isOnroad) {
            return true;
        }
        JSONObject jSONObject = this.fastStatus;
        if ((jSONObject != null && jSONObject.optBoolean("isOnroad")) || getUpdateInProgress()) {
            return true;
        }
        return this.busy.length() > 0;
    }

    private final boolean getUpdateAvailable() {
        if (!this.checkedForUpdates) {
            return false;
        }
        JSONObject jSONObject = this.fastStatus;
        return (jSONObject != null && jSONObject.optBoolean("updateAvailable")) && !getUpdateInProgress();
    }

    private final List<String> getOtherBranches() {
        List list;
        List list2;
        Iterable $this$filter$iv = this.branches;
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            String it = (String) element$iv$iv;
            list2 = GxSystemKt.GxSystem_CORE;
            if (!list2.contains(it)) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        ArrayList list3 = new ArrayList((List) destination$iv$iv);
        if (this.currentBranch.length() > 0) {
            list = GxSystemKt.GxSystem_CORE;
            if (!list.contains(this.currentBranch) && !list3.contains(this.currentBranch)) {
                list3.add(0, this.currentBranch);
            }
        }
        return list3;
    }

    private final List<JSONObject> versionChoices() {
        return Intrinsics.areEqual(this.targetBranch, "StarPilot") ? releaseVersions(this.versionCommits) : this.versionCommits;
    }

    private final boolean getInstallVersionBlocked() {
        Regex regex;
        Iterable $this$none$iv;
        if (getBranchSwitchBlocked() || this.branchBusy || !this.branches.contains(this.targetBranch)) {
            return true;
        }
        if (Intrinsics.areEqual(this.versionMode, "earlier")) {
            if (this.versionLoading) {
                return true;
            }
            regex = GxSystemKt.GxSystem_SHA;
            if (!regex.matches(this.selectedCommit)) {
                return true;
            }
            Iterable $this$none$iv2 = versionChoices();
            if (!($this$none$iv2 instanceof Collection) || !((Collection) $this$none$iv2).isEmpty()) {
                Iterator it = $this$none$iv2.iterator();
                while (true) {
                    if (it.hasNext()) {
                        Object element$iv = it.next();
                        JSONObject it2 = (JSONObject) element$iv;
                        if (Intrinsics.areEqual(GxPage.str$default(this, it2, "sha", null, 2, null), this.selectedCommit)) {
                            $this$none$iv = null;
                            break;
                        }
                    } else {
                        $this$none$iv = 1;
                        break;
                    }
                }
            } else {
                $this$none$iv = 1;
            }
            if ($this$none$iv != null) {
                return true;
            }
        }
        return false;
    }

    @Override // com.starpilot.starview.GxPage
    public void build() {
        int i;
        Long l;
        String it = getHost();
        this.rebootStartedAt = (it == null || (l = GxSystemReboot.INSTANCE.getStartedAt().get(it)) == null) ? 0L : l.longValue();
        this.rebootPending = this.rebootStartedAt > 0;
        GxPage.header$default(this, "Software & Updates", null, 2, null);
        this.loadingNote = GxPage.note$default(this, "Loading update info...", null, 2, null);
        this.swWrap = GxPage.box$default(this, null, 1, null);
        LinearLayout linearLayout = this.swWrap;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("swWrap");
            linearLayout = null;
        }
        this.noticeBox = box(linearLayout);
        LinearLayout linearLayout2 = this.swWrap;
        if (linearLayout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("swWrap");
            linearLayout2 = null;
        }
        this.statusBox = box(linearLayout2);
        GxSystemPage gxSystemPage = this;
        LinearLayout linearLayout3 = this.swWrap;
        if (linearLayout3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("swWrap");
            linearLayout3 = null;
        }
        this.autoRow = GxPage.row$default(gxSystemPage, "Automatically Install Updates", "Install updates automatically while parked with an active internet connection.", linearLayout3, 0, 8, null);
        GxPage.Row row = this.autoRow;
        if (row == null) {
            Intrinsics.throwUninitializedPropertyAccessException("autoRow");
            row = null;
        }
        this.autoSwitch = row.m806switch(false, new Function1() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit build$lambda$3;
                build$lambda$3 = GxSystemPage.build$lambda$3(GxSystemPage.this, ((Boolean) obj).booleanValue());
                return build$lambda$3;
            }
        });
        GxSystemPage gxSystemPage2 = this;
        LinearLayout linearLayout4 = this.swWrap;
        if (linearLayout4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("swWrap");
            linearLayout4 = null;
        }
        this.fastRow = GxPage.row$default(gxSystemPage2, "Fast Update", "Check for and install the latest commit on this branch. Fast update lives on StarView's Software page.", linearLayout4, 0, 8, null);
        GxPage.Row row2 = this.fastRow;
        if (row2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("fastRow");
            row2 = null;
        }
        row2.button("Open", true, new Function0() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda7
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit build$lambda$4;
                build$lambda$4 = GxSystemPage.build$lambda$4(GxSystemPage.this);
                return build$lambda$4;
            }
        });
        GxSystemPage gxSystemPage3 = this;
        LinearLayout linearLayout5 = this.swWrap;
        if (linearLayout5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("swWrap");
            linearLayout5 = null;
        }
        GxPage.Row chk = GxPage.row$default(gxSystemPage3, "Check for Updates", "Check for Updates scans for a newer commit. Use Fast Update on the Software page to install it.", linearLayout5, 0, 8, null);
        this.checkBtn = GxPage.Row.button$default(chk, "Check", false, new Function0() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda8
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit build$lambda$5;
                build$lambda$5 = GxSystemPage.build$lambda$5(GxSystemPage.this);
                return build$lambda$5;
            }
        }, 2, null);
        LinearLayout linearLayout6 = this.swWrap;
        if (linearLayout6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("swWrap");
            linearLayout6 = null;
        }
        this.upToDateNote = note("The device is up to date. Update becomes available only after a check finds a newer commit.", linearLayout6);
        GxSystemPage gxSystemPage4 = this;
        LinearLayout linearLayout7 = this.swWrap;
        if (linearLayout7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("swWrap");
            linearLayout7 = null;
        }
        GxPage.Row rec = GxPage.row$default(gxSystemPage4, "Recover", "Recover continues an update that was interrupted (for example, by power loss mid-install).", linearLayout7, 0, 8, null);
        this.recoverBtn = GxPage.Row.button$default(rec, "Recover", false, new Function0() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda9
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit build$lambda$6;
                build$lambda$6 = GxSystemPage.build$lambda$6(GxSystemPage.this);
                return build$lambda$6;
            }
        }, 2, null);
        GxSystemPage gxSystemPage5 = this;
        LinearLayout linearLayout8 = this.swWrap;
        if (linearLayout8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("swWrap");
            linearLayout8 = null;
        }
        GxPage.Row rb = GxPage.row$default(gxSystemPage5, "Rollback", "Rollback returns the device to the previously installed version if the current one has a problem.", linearLayout8, 0, 8, null);
        this.rollbackBtn = GxPage.Row.button$default(rb, "Rollback", false, new Function0() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda10
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit build$lambda$7;
                build$lambda$7 = GxSystemPage.build$lambda$7(GxSystemPage.this);
                return build$lambda$7;
            }
        }, 2, null);
        GxSystemPage gxSystemPage6 = this;
        LinearLayout linearLayout9 = this.swWrap;
        if (linearLayout9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("swWrap");
            linearLayout9 = null;
        }
        GxPage.Row adv = GxPage.row$default(gxSystemPage6, "Advanced update options", "Install a specific branch or historical version.", linearLayout9, 0, 8, null);
        this.advBtn = GxPage.Row.button$default(adv, "Show", false, new Function0() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda12
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit build$lambda$8;
                build$lambda$8 = GxSystemPage.build$lambda$8(GxSystemPage.this);
                return build$lambda$8;
            }
        }, 2, null);
        LinearLayout linearLayout10 = this.swWrap;
        if (linearLayout10 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("swWrap");
            linearLayout10 = null;
        }
        this.advBox = box(linearLayout10);
        GxPage.header$default(this, "Backup & Restore", null, 2, null);
        LinearLayout pc = GxPage.card$default(this, null, 1, null);
        GxPage.text$default(this, "Settings Profiles", pc, 21.0f, CarUi.Companion.getTEXT(), false, 16, null);
        GxPage.text$default(this, "Keep two local configurations for different vehicles, drivers, or troubleshooting. Profiles never include pairing or sensitive device data.", pc, 0.0f, 0, false, 28, null);
        this.profileNotice = GxPage.box$default(this, null, 1, null);
        this.profilesBox = GxPage.box$default(this, null, 1, null);
        GxPage.Row.button$default(GxPage.row$default(this, "Backup Toggles", "Downloads your toggle settings as a JSON file (Downloads/StarView).", null, 0, 12, null), "Backup", false, new Function0() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda13
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit build$lambda$9;
                build$lambda$9 = GxSystemPage.build$lambda$9(GxSystemPage.this);
                return build$lambda$9;
            }
        }, 2, null);
        GxPage.Row.button$default(GxPage.row$default(this, "Restore Toggles", "Re-applies the toggle settings from a backup file.", null, 0, 12, null), "Restore", false, new Function0() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda14
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit build$lambda$10;
                build$lambda$10 = GxSystemPage.build$lambda$10(GxSystemPage.this);
                return build$lambda$10;
            }
        }, 2, null);
        GxPage.Row.button$default(GxPage.row$default(this, "Reset to Default", "Clears all toggles back to stock and reboots.", null, 0, 12, null), "Reset", false, new Function0() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda15
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit build$lambda$11;
                build$lambda$11 = GxSystemPage.build$lambda$11(GxSystemPage.this);
                return build$lambda$11;
            }
        }, 2, null);
        GxPage.Row.button$default(GxPage.row$default(this, "Delete All Driving Routes", "Permanently deletes all local routes from standard, high-resolution, and alternate footage storage.", null, 0, 12, null), "Delete", false, new Function0() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda16
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit build$lambda$12;
                build$lambda$12 = GxSystemPage.build$lambda$12(GxSystemPage.this);
                return build$lambda$12;
            }
        }, 2, null);
        GxPage.note$default(this, "Backup downloads your toggle settings as a JSON file. Restore re-applies one, and Reset to Default clears them back to stock and reboots.", null, 2, null);
        this.factoryBox = GxPage.box$default(this, null, 1, null);
        GxPage.header$default(this, "Tailscale", null, 2, null);
        this.tsBox = GxPage.box$default(this, null, 1, null);
        GxPage.Row.button$default(GxPage.row$default(this, "Tailscale remote access", "Install, sign in and uninstall are on the Galaxy & remote access page.", null, 0, 12, null), "Open", false, new Function0() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit build$lambda$13;
                build$lambda$13 = GxSystemPage.build$lambda$13(GxSystemPage.this);
                return build$lambda$13;
            }
        }, 2, null);
        GxPage.header$default(this, "Danger Zone", null, 2, null);
        GxPage.note$default(this, "Last resort only. Factory Reset (also known as SAVE ME) wipes params, backups, themes, models, maps, and route data, then reboots the device. This cannot be undone.", null, 2, null);
        TextView button$default = GxPage.Row.button$default(GxPage.row$default(this, "Factory Reset Device (SAVE ME)", "Wipes params, backups, themes, models, maps, and route data, then reboots.", null, 0, 12, null), "Factory Reset", false, new Function0() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit build$lambda$14;
                build$lambda$14 = GxSystemPage.build$lambda$14(GxSystemPage.this);
                return build$lambda$14;
            }
        }, 2, null);
        i = GxSystemKt.GxSystem_DANGER;
        button$default.setTextColor(i);
        every(1000L, new Function0() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit build$lambda$15;
                build$lambda$15 = GxSystemPage.build$lambda$15(GxSystemPage.this);
                return build$lambda$15;
            }
        });
        paint();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit build$lambda$3(GxSystemPage this$0, boolean v) {
        this$0.setAutomaticUpdates(v);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit build$lambda$4(GxSystemPage this$0) {
        this$0.getGx().openPage("native:software");
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit build$lambda$5(GxSystemPage this$0) {
        this$0.checkUpdates();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit build$lambda$6(GxSystemPage this$0) {
        this$0.runUpdate("recover");
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit build$lambda$7(GxSystemPage this$0) {
        this$0.runUpdate("rollback");
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit build$lambda$8(GxSystemPage this$0) {
        this$0.advOpen = !this$0.advOpen;
        TextView textView = this$0.advBtn;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("advBtn");
            textView = null;
        }
        textView.setText(this$0.advOpen ? "Hide" : "Show");
        this$0.forceAdv();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit build$lambda$9(GxSystemPage this$0) {
        this$0.backupToggles();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit build$lambda$10(GxSystemPage this$0) {
        this$0.restoreToggles();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit build$lambda$11(GxSystemPage this$0) {
        this$0.resetDefault();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit build$lambda$12(GxSystemPage this$0) {
        this$0.deleteAllRoutes();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit build$lambda$13(GxSystemPage this$0) {
        this$0.getGx().openPage("gx:galaxy");
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit build$lambda$14(GxSystemPage this$0) {
        this$0.factoryReset();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit build$lambda$15(GxSystemPage this$0) {
        if (this$0.getStatusPollingNeeded() && !this$0.statusInFlight) {
            loadFastStatus$default(this$0, null, 1, null);
        }
        return Unit.INSTANCE;
    }

    @Override // com.starpilot.starview.GxPage
    public void onShow() {
        loadBranches();
        loadProfiles();
        if (this.fastStatus == null || this.statusInFlight) {
            return;
        }
        loadFastStatus$default(this, null, 1, null);
    }

    private final void en(View v, boolean on) {
        if (v == null) {
            return;
        }
        v.setEnabled(on);
        v.setAlpha(on ? 1.0f : 0.4f);
    }

    private final void fill(LinearLayout b, String sig, Function1<? super LinearLayout, Unit> block) {
        if (Intrinsics.areEqual(b.getTag(), sig)) {
            return;
        }
        b.setTag(sig);
        b.removeAllViews();
        block.invoke(b);
    }

    private final void forceAdv() {
        LinearLayout linearLayout = this.advBox;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("advBox");
            linearLayout = null;
        }
        linearLayout.setTag(null);
        paint();
    }

    private final String shortCommit(String c) {
        String take = StringsKt.take(c == null ? "" : c, 10);
        if (take.length() == 0) {
            take = "—";
        }
        return take;
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:13:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final double pct(Object v) {
        double n;
        double doubleValue;
        String obj;
        Double d = null;
        Number number = v instanceof Number ? (Number) v : null;
        if (number != null) {
            doubleValue = number.doubleValue();
        } else {
            if (v != null && (obj = v.toString()) != null) {
                d = StringsKt.toDoubleOrNull(obj);
            }
            if (d == null) {
                n = 0.0d;
                if (Double.isNaN(n)) {
                    return RangesKt.coerceIn(n, 0.0d, 100.0d);
                }
                return 0.0d;
            }
            doubleValue = d.doubleValue();
        }
        n = doubleValue;
        if (Double.isNaN(n)) {
        }
    }

    private final void openUrl(String u) {
        try {
            getAct().startActivity(new Intent("android.intent.action.VIEW", Uri.parse(u)));
        } catch (Exception e) {
            toast("No browser available");
        }
    }

    static /* synthetic */ void noticeCard$default(GxSystemPage gxSystemPage, ViewGroup viewGroup, String str, String str2, int i, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            str2 = null;
        }
        if ((i2 & 8) != 0) {
            i = CarUi.Companion.getWARN();
        }
        gxSystemPage.noticeCard(viewGroup, str, str2, i);
    }

    private final void noticeCard(ViewGroup into, String msg, String title, int color) {
        LinearLayout c = card(into);
        if (title != null) {
            text(title, c, 21.0f, color, true);
        }
        GxPage.text$default(this, msg, c, 15.0f, title == null ? color : CarUi.Companion.getTEXT(), false, 16, null);
    }

    static /* synthetic */ void kv$default(GxSystemPage gxSystemPage, ViewGroup viewGroup, String str, String str2, boolean z, Function0 function0, int i, Object obj) {
        if ((i & 8) != 0) {
            z = false;
        }
        boolean z2 = z;
        if ((i & 16) != 0) {
            function0 = null;
        }
        gxSystemPage.kv(viewGroup, str, str2, z2, function0);
    }

    private final void kv(ViewGroup into, String label, String value, boolean mono, final Function0<Unit> onClick) {
        LinearLayout l = new LinearLayout(getAct());
        l.setOrientation(0);
        l.setGravity(16);
        l.setPadding(0, getUi().dp(4), 0, getUi().dp(4));
        TextView $this$kv_u24lambda_u2418 = new TextView(getAct());
        $this$kv_u24lambda_u2418.setText(label);
        $this$kv_u24lambda_u2418.setTextSize(17.0f);
        $this$kv_u24lambda_u2418.setTextColor(CarUi.Companion.getDIM());
        l.addView($this$kv_u24lambda_u2418, new LinearLayout.LayoutParams(0, -2, 1.0f));
        TextView $this$kv_u24lambda_u2420 = new TextView(getAct());
        $this$kv_u24lambda_u2420.setText(value);
        $this$kv_u24lambda_u2420.setTextSize(17.0f);
        CarUi.Companion companion = CarUi.Companion;
        $this$kv_u24lambda_u2420.setTextColor(onClick != null ? companion.getACCENT() : companion.getTEXT());
        $this$kv_u24lambda_u2420.setTypeface(mono ? Typeface.MONOSPACE : Typeface.DEFAULT_BOLD);
        if (onClick != null) {
            $this$kv_u24lambda_u2420.setPaintFlags($this$kv_u24lambda_u2420.getPaintFlags() | 8);
            $this$kv_u24lambda_u2420.setPadding(getUi().dp(8), getUi().dp(8), 0, getUi().dp(8));
            $this$kv_u24lambda_u2420.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda66
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    Function0.this.invoke();
                }
            });
        }
        l.addView($this$kv_u24lambda_u2420);
        into.addView(l);
    }

    private final Instant parseDate(String s) {
        Object m1191constructorimpl;
        Object m1191constructorimpl2;
        String str = s;
        if (str == null || StringsKt.isBlank(str)) {
            return null;
        }
        try {
            Result.Companion companion = Result.Companion;
            GxSystemPage gxSystemPage = this;
            m1191constructorimpl = Result.m1191constructorimpl(OffsetDateTime.parse(s).toInstant());
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            m1191constructorimpl = Result.m1191constructorimpl(ResultKt.createFailure(th));
        }
        if (Result.m1197isFailureimpl(m1191constructorimpl)) {
            m1191constructorimpl = null;
        }
        Instant instant = (Instant) m1191constructorimpl;
        if (instant != null) {
            return instant;
        }
        try {
            Result.Companion companion3 = Result.Companion;
            GxSystemPage gxSystemPage2 = this;
            m1191constructorimpl2 = Result.m1191constructorimpl(Instant.parse(s));
        } catch (Throwable th2) {
            Result.Companion companion4 = Result.Companion;
            m1191constructorimpl2 = Result.m1191constructorimpl(ResultKt.createFailure(th2));
        }
        return (Instant) (Result.m1197isFailureimpl(m1191constructorimpl2) ? null : m1191constructorimpl2);
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0065, code lost:
    
        if (r0 != null) goto L22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x009f, code lost:
    
        if (r0 != null) goto L34;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final String versionTitle(JSONObject c, boolean release) {
        String str;
        String str2;
        String str$default;
        String str3;
        String str$default2;
        String str4 = null;
        Instant d = parseDate(c != null ? GxPage.str$default(this, c, "date", null, 2, null) : null);
        String whenS = d != null ? this.dayFmt.format(d) + " · " + this.timeFmt.format(d) : "Unknown date";
        if (release) {
            if (c != null && (str$default2 = GxPage.str$default(this, c, "version", null, 2, null)) != null) {
                String str5 = str$default2;
                if (!StringsKt.isBlank(str5)) {
                    str4 = str5;
                }
                str3 = str4;
            }
            str3 = "Unnumbered version";
            return str3 + " · " + whenS;
        }
        if (c != null && (str$default = GxPage.str$default(this, c, "subject", null, 2, null)) != null) {
            String str6 = str$default;
            if (!StringsKt.isBlank(str6)) {
                str4 = str6;
            }
            str = str4;
        }
        str = "Untitled change";
        if (c == null || (str2 = GxPage.str$default(this, c, "sha", null, 2, null)) == null) {
            str2 = "";
        }
        return str + " · " + whenS + " · " + StringsKt.take(str2, 10);
    }

    private final List<JSONObject> releaseVersions(List<? extends JSONObject> commits) {
        LinkedHashMap m = new LinkedHashMap();
        for (JSONObject c : commits) {
            String k = GxPage.str$default(this, c, "version", null, 2, null);
            if (!m.containsKey(k)) {
                m.put(k, c);
            }
        }
        Collection values = m.values();
        Intrinsics.checkNotNullExpressionValue(values, "<get-values>(...)");
        final Function2 function2 = new Function2() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda19
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                int releaseVersions$lambda$28;
                releaseVersions$lambda$28 = GxSystemPage.releaseVersions$lambda$28(GxSystemPage.this, (JSONObject) obj, (JSONObject) obj2);
                return Integer.valueOf(releaseVersions$lambda$28);
            }
        };
        return CollectionsKt.sortedWith(values, new Comparator() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda20
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                int releaseVersions$lambda$29;
                releaseVersions$lambda$29 = GxSystemPage.releaseVersions$lambda$29(Function2.this, obj, obj2);
                return releaseVersions$lambda$29;
            }
        });
    }

    private static final List<Integer> releaseVersions$parts(String v) {
        Iterable $this$map$iv = StringsKt.split$default((CharSequence) v, new String[]{"."}, false, 0, 6, (Object) null);
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        for (Object item$iv$iv : $this$map$iv) {
            String it = (String) item$iv$iv;
            Integer intOrNull = StringsKt.toIntOrNull(it);
            destination$iv$iv.add(Integer.valueOf(intOrNull != null ? intOrNull.intValue() : 0));
        }
        return (List) destination$iv$iv;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int releaseVersions$lambda$29(Function2 $tmp0, Object p0, Object p1) {
        return ((Number) $tmp0.invoke(p0, p1)).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int releaseVersions$lambda$28(GxSystemPage this$0, JSONObject a, JSONObject b) {
        Intrinsics.checkNotNull(a);
        String av = GxPage.str$default(this$0, a, "version", null, 2, null);
        Intrinsics.checkNotNull(b);
        String bv = GxPage.str$default(this$0, b, "version", null, 2, null);
        if (av.length() == 0) {
            return bv.length() > 0 ? 1 : 0;
        }
        if (bv.length() == 0) {
            return -1;
        }
        List l = releaseVersions$parts(av);
        List r = releaseVersions$parts(bv);
        int i = 0;
        while (i < 3) {
            int d = (i >= 0 && i < r.size() ? r.get(i) : 0).intValue() - (i >= 0 && i < l.size() ? l.get(i) : 0).intValue();
            if (d != 0) {
                return d;
            }
            i++;
        }
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: GxSystem.kt */
    static final /* data */ class DayGroup {
        private final ArrayList<JSONObject> commits;
        private final String key;
        private final String label;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ DayGroup copy$default(DayGroup dayGroup, String str, String str2, ArrayList arrayList, int i, Object obj) {
            if ((i & 1) != 0) {
                str = dayGroup.key;
            }
            if ((i & 2) != 0) {
                str2 = dayGroup.label;
            }
            if ((i & 4) != 0) {
                arrayList = dayGroup.commits;
            }
            return dayGroup.copy(str, str2, arrayList);
        }

        /* renamed from: component1, reason: from getter */
        public final String getKey() {
            return this.key;
        }

        /* renamed from: component2, reason: from getter */
        public final String getLabel() {
            return this.label;
        }

        public final ArrayList<JSONObject> component3() {
            return this.commits;
        }

        public final DayGroup copy(String key, String label, ArrayList<JSONObject> commits) {
            Intrinsics.checkNotNullParameter(key, "key");
            Intrinsics.checkNotNullParameter(label, "label");
            Intrinsics.checkNotNullParameter(commits, "commits");
            return new DayGroup(key, label, commits);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof DayGroup)) {
                return false;
            }
            DayGroup dayGroup = (DayGroup) other;
            return Intrinsics.areEqual(this.key, dayGroup.key) && Intrinsics.areEqual(this.label, dayGroup.label) && Intrinsics.areEqual(this.commits, dayGroup.commits);
        }

        public int hashCode() {
            return (((this.key.hashCode() * 31) + this.label.hashCode()) * 31) + this.commits.hashCode();
        }

        public String toString() {
            return "DayGroup(key=" + this.key + ", label=" + this.label + ", commits=" + this.commits + ")";
        }

        public DayGroup(String key, String label, ArrayList<JSONObject> commits) {
            Intrinsics.checkNotNullParameter(key, "key");
            Intrinsics.checkNotNullParameter(label, "label");
            Intrinsics.checkNotNullParameter(commits, "commits");
            this.key = key;
            this.label = label;
            this.commits = commits;
        }

        public final ArrayList<JSONObject> getCommits() {
            return this.commits;
        }

        public final String getKey() {
            return this.key;
        }

        public final String getLabel() {
            return this.label;
        }
    }

    private final List<DayGroup> groupVersionHistory(List<? extends JSONObject> commits) {
        Object answer$iv;
        LinkedHashMap g = new LinkedHashMap();
        for (JSONObject c : commits) {
            Instant d = parseDate(GxPage.str$default(this, c, "date", null, 2, null));
            String key = d != null ? this.keyFmt.format(d) : "unknown";
            LinkedHashMap $this$getOrPut$iv = g;
            Object value$iv = $this$getOrPut$iv.get(key);
            if (value$iv == null) {
                Intrinsics.checkNotNull(key);
                String format = d != null ? this.dayFmt.format(d) : "Unknown date";
                Intrinsics.checkNotNull(format);
                answer$iv = new DayGroup(key, format, new ArrayList());
                $this$getOrPut$iv.put(key, answer$iv);
            } else {
                answer$iv = value$iv;
            }
            ((DayGroup) answer$iv).getCommits().add(c);
        }
        Collection values = g.values();
        Intrinsics.checkNotNullExpressionValue(values, "<get-values>(...)");
        final Function2 function2 = new Function2() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda56
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                int groupVersionHistory$lambda$31;
                groupVersionHistory$lambda$31 = GxSystemPage.groupVersionHistory$lambda$31((GxSystemPage.DayGroup) obj, (GxSystemPage.DayGroup) obj2);
                return Integer.valueOf(groupVersionHistory$lambda$31);
            }
        };
        return CollectionsKt.sortedWith(values, new Comparator() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda57
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                int groupVersionHistory$lambda$32;
                groupVersionHistory$lambda$32 = GxSystemPage.groupVersionHistory$lambda$32(Function2.this, obj, obj2);
                return groupVersionHistory$lambda$32;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int groupVersionHistory$lambda$31(DayGroup a, DayGroup b) {
        if (Intrinsics.areEqual(a.getKey(), "unknown")) {
            return 1;
        }
        if (Intrinsics.areEqual(b.getKey(), "unknown")) {
            return -1;
        }
        return b.getKey().compareTo(a.getKey());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int groupVersionHistory$lambda$32(Function2 $tmp0, Object p0, Object p1) {
        return ((Number) $tmp0.invoke(p0, p1)).intValue();
    }

    private final String githubRemoteUrl(String remote, String commitsUrl) {
        String v = StringsKt.trim((CharSequence) remote).toString();
        if (v.length() == 0) {
            String c = StringsKt.trim((CharSequence) commitsUrl).toString();
            int i = StringsKt.indexOf$default((CharSequence) c, "/commits/", 0, false, 6, (Object) null);
            if (!StringsKt.startsWith$default(c, "https://github.com/", false, 2, (Object) null) || i <= 0) {
                return "";
            }
            String substring = c.substring(0, i);
            Intrinsics.checkNotNullExpressionValue(substring, "substring(...)");
            v = substring;
        }
        if (StringsKt.startsWith$default(v, "git@github.com:", false, 2, (Object) null)) {
            v = "https://github.com/" + StringsKt.substringAfter$default(v, ":", (String) null, 2, (Object) null);
        } else if (StringsKt.startsWith$default(v, "ssh://git@github.com/", false, 2, (Object) null)) {
            v = "https://github.com/" + StringsKt.substringAfter$default(v, "ssh://git@github.com/", (String) null, 2, (Object) null);
        } else if (StringsKt.startsWith$default(v, "http://github.com/", false, 2, (Object) null)) {
            v = "https://github.com/" + StringsKt.substringAfter$default(v, "http://github.com/", (String) null, 2, (Object) null);
        }
        if (!StringsKt.startsWith$default(v, "https://github.com/", false, 2, (Object) null)) {
            return "";
        }
        String v2 = StringsKt.trimEnd(v, '/');
        return StringsKt.endsWith$default(v2, ".git", false, 2, (Object) null) ? StringsKt.dropLast(v2, 4) : v2;
    }

    private final String remoteCommitUrl() {
        JSONObject s = this.fastStatus;
        if (s == null) {
            return "";
        }
        String sha = StringsKt.trim((CharSequence) GxPage.str$default(this, s, "remoteCommit", null, 2, null)).toString();
        if (!new Regex("^[a-fA-F0-9]{7,40}$").matches(sha)) {
            return "";
        }
        String base = githubRemoteUrl(GxPage.str$default(this, s, "originRemote", null, 2, null), GxPage.str$default(this, s, "commitsUrl", null, 2, null));
        return base.length() > 0 ? base + "/commit/" + sha : "";
    }

    private final String recentCommitsUrl() {
        JSONObject s = this.fastStatus;
        if (s == null) {
            return "";
        }
        String supplied = StringsKt.trim((CharSequence) GxPage.str$default(this, s, "commitsUrl", null, 2, null)).toString();
        if (StringsKt.startsWith$default(supplied, "https://github.com/", false, 2, (Object) null) && StringsKt.contains$default((CharSequence) supplied, (CharSequence) "/commits/", false, 2, (Object) null)) {
            return supplied;
        }
        String base = githubRemoteUrl(GxPage.str$default(this, s, "originRemote", null, 2, null), supplied);
        String str$default = GxPage.str$default(this, s, "branch", null, 2, null);
        if (StringsKt.isBlank(str$default)) {
            str$default = this.currentBranch;
        }
        String br = StringsKt.trim((CharSequence) str$default).toString();
        if (!(base.length() == 0)) {
            if (!(br.length() == 0)) {
                return base + "/commits/" + enc(br) + "/";
            }
        }
        return "";
    }

    private final void markRebootPending() {
        this.rebootPending = true;
        this.rebootStartedAt = System.currentTimeMillis();
        this.rebootOfflineSeen = false;
        this.reconnectedNotice = false;
        String it = getHost();
        if (it != null) {
            GxSystemReboot.INSTANCE.getStartedAt().put(it, Long.valueOf(this.rebootStartedAt));
        }
    }

    static /* synthetic */ void clearRebootPending$default(GxSystemPage gxSystemPage, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = true;
        }
        gxSystemPage.clearRebootPending(z);
    }

    private final void clearRebootPending(boolean showNotice) {
        this.rebootPending = false;
        this.rebootStartedAt = 0L;
        this.rebootOfflineSeen = false;
        String it = getHost();
        if (it != null) {
            GxSystemReboot.INSTANCE.getStartedAt().remove(it);
        }
        if (showNotice) {
            this.reconnectedNotice = true;
        }
    }

    private final void loadBranches() {
        json("GET", "/api/update/branches", null, 45L, new Function2() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda67
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit loadBranches$lambda$42;
                loadBranches$lambda$42 = GxSystemPage.loadBranches$lambda$42(GxSystemPage.this, (String) obj, obj2);
                return loadBranches$lambda$42;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0092, code lost:
    
        if (r4 == null) goto L27;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00f0, code lost:
    
        if (r4.contains(r16.targetBranch) == false) goto L46;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Unit loadBranches$lambda$42(final GxSystemPage this$0, String e, Object v) {
        List emptyList;
        List list;
        Iterable strings;
        JSONObject o = v instanceof JSONObject ? (JSONObject) v : null;
        JSONArray arr = o != null ? o.optJSONArray("branches") : null;
        boolean z = true;
        if (arr != null && (strings = this$0.strings(arr)) != null) {
            Iterable $this$map$iv = strings;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            for (Object item$iv$iv : $this$map$iv) {
                String it = (String) item$iv$iv;
                destination$iv$iv.add(StringsKt.trim((CharSequence) it).toString());
            }
            Iterable $this$filter$iv = (List) destination$iv$iv;
            Collection destination$iv$iv2 = new ArrayList();
            for (Object element$iv$iv : $this$filter$iv) {
                String it2 = (String) element$iv$iv;
                if (it2.length() > 0) {
                    destination$iv$iv2.add(element$iv$iv);
                }
            }
            emptyList = CollectionsKt.distinct((List) destination$iv$iv2);
        }
        emptyList = CollectionsKt.emptyList();
        List list2 = emptyList;
        if (e == null && !list2.isEmpty()) {
            this$0.branches = list2;
            Intrinsics.checkNotNull(o);
            this$0.currentBranch = StringsKt.trim((CharSequence) GxPage.str$default(this$0, o, "currentBranch", null, 2, null)).toString();
            this$0.branchListFallback = false;
            if (this$0.targetBranch.length() == 0) {
                this$0.targetBranch = this$0.currentBranch;
                if (this$0.targetBranch.length() > 0) {
                    list = GxSystemKt.GxSystem_CORE;
                }
                z = false;
                this$0.otherOpen = z;
            }
            this$0.isOnroad = o.optBoolean("isOnroad");
            this$0.branchLoading = false;
            this$0.paint();
        } else if (this$0.fastStatus == null) {
            this$0.loadFastStatus(new Function1() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda17
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    Unit loadBranches$lambda$42$lambda$41;
                    loadBranches$lambda$42$lambda$41 = GxSystemPage.loadBranches$lambda$42$lambda$41(GxSystemPage.this, (String) obj);
                    return loadBranches$lambda$42$lambda$41;
                }
            });
        } else {
            loadBranches$lambda$42$fallback(this$0);
        }
        return Unit.INSTANCE;
    }

    private static final void loadBranches$lambda$42$fallback(GxSystemPage this$0) {
        String str;
        List list;
        List list2;
        JSONObject jSONObject = this$0.fastStatus;
        if (jSONObject == null || (str = GxPage.str$default(this$0, jSONObject, "branch", null, 2, null)) == null) {
            str = this$0.currentBranch;
        }
        String str2 = str;
        if (StringsKt.isBlank(str2)) {
            str2 = this$0.currentBranch;
        }
        String cur = StringsKt.trim((CharSequence) str2).toString();
        this$0.currentBranch = cur;
        List listOf = CollectionsKt.listOf(cur);
        list = GxSystemKt.GxSystem_CORE;
        Iterable $this$filter$iv = CollectionsKt.plus((Collection) listOf, (Iterable) list);
        Collection destination$iv$iv = new ArrayList();
        Iterator it = $this$filter$iv.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Object element$iv$iv = it.next();
            String it2 = (String) element$iv$iv;
            if (it2.length() > 0) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        this$0.branches = CollectionsKt.distinct((List) destination$iv$iv);
        this$0.branchListFallback = true;
        if (this$0.targetBranch.length() == 0) {
            String str3 = cur;
            if (str3.length() == 0) {
                str3 = "Dom";
            }
            this$0.targetBranch = str3;
            list2 = GxSystemKt.GxSystem_CORE;
            this$0.otherOpen = !list2.contains(this$0.targetBranch);
        }
        this$0.toast("Could not refresh the full branch list. Standard branches are still available.");
        this$0.branchLoading = false;
        this$0.paint();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit loadBranches$lambda$42$lambda$41(GxSystemPage this$0, String it) {
        loadBranches$lambda$42$fallback(this$0);
        return Unit.INSTANCE;
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ void loadFastStatus$default(GxSystemPage gxSystemPage, Function1 function1, int i, Object obj) {
        if ((i & 1) != 0) {
            function1 = null;
        }
        gxSystemPage.loadFastStatus(function1);
    }

    private final void loadFastStatus(final Function1<? super String, Unit> then) {
        this.statusInFlight = true;
        json("GET", "/api/update/fast/status", null, 45L, new Function2() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda69
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit loadFastStatus$lambda$45;
                loadFastStatus$lambda$45 = GxSystemPage.loadFastStatus$lambda$45(GxSystemPage.this, then, (String) obj, obj2);
                return loadFastStatus$lambda$45;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:18:0x007a  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00bd  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00c2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Unit loadFastStatus$lambda$45(GxSystemPage this$0, Function1 $then, String e, Object v) {
        String str;
        String str$default;
        String stage;
        boolean deviceReturned;
        this$0.statusInFlight = false;
        JSONObject st = v instanceof JSONObject ? (JSONObject) v : null;
        String err = null;
        if (e == null && st != null && (st.opt("running") instanceof Boolean)) {
            String stage2 = StringsKt.trim((CharSequence) GxPage.str$default(this$0, st, "stage", null, 2, null)).toString().toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(stage2, "toLowerCase(...)");
            if (Intrinsics.areEqual(stage2, "rebooting") && !this$0.rebootPending) {
                this$0.rebootPending = true;
                this$0.rebootStartedAt = System.currentTimeMillis();
                String it = this$0.getHost();
                if (it != null) {
                    stage = stage2;
                    GxSystemReboot.INSTANCE.getStartedAt().put(it, Long.valueOf(this$0.rebootStartedAt));
                    long pendingAge = this$0.rebootStartedAt > 0 ? System.currentTimeMillis() - this$0.rebootStartedAt : 0L;
                    deviceReturned = (this$0.rebootPending || st.optBoolean("running") || Intrinsics.areEqual(stage, "rebooting") || (!this$0.statusUnavailable && pendingAge < 30000)) ? false : true;
                    boolean updateFailed = !this$0.rebootPending && Intrinsics.areEqual(stage, "error");
                    this$0.fastStatus = st;
                    this$0.statusUnavailable = false;
                    this$0.isOnroad = st.optBoolean("isOnroad");
                    if (!deviceReturned) {
                        clearRebootPending$default(this$0, false, 1, null);
                    } else if (updateFailed) {
                        this$0.clearRebootPending(false);
                    }
                }
            }
            stage = stage2;
            long pendingAge2 = this$0.rebootStartedAt > 0 ? System.currentTimeMillis() - this$0.rebootStartedAt : 0L;
            if (this$0.rebootPending) {
            }
            if (this$0.rebootPending) {
            }
            this$0.fastStatus = st;
            this$0.statusUnavailable = false;
            this$0.isOnroad = st.optBoolean("isOnroad");
            if (!deviceReturned) {
            }
        } else {
            if (e == null) {
                if (st == null || (str$default = GxPage.str$default(this$0, st, "error", null, 2, null)) == null) {
                    str = null;
                } else {
                    String str2 = str$default;
                    if (StringsKt.isBlank(str2)) {
                        str2 = null;
                    }
                    str = str2;
                }
                if (str == null) {
                    str = "Update status unavailable.";
                }
            } else {
                str = e;
            }
            err = str;
            this$0.statusUnavailable = true;
            if (this$0.rebootPending) {
                this$0.rebootOfflineSeen = true;
            } else {
                this$0.fastStatus = null;
            }
        }
        this$0.paint();
        if ($then != null) {
            $then.invoke(err);
        }
        return Unit.INSTANCE;
    }

    private final void loadProfiles() {
        getJson("/api/toggles/profiles", new Function2() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda18
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit loadProfiles$lambda$46;
                loadProfiles$lambda$46 = GxSystemPage.loadProfiles$lambda$46(GxSystemPage.this, (String) obj, (JSONObject) obj2);
                return loadProfiles$lambda$46;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit loadProfiles$lambda$46(GxSystemPage this$0, String e, JSONObject o) {
        List<JSONObject> emptyList;
        JSONArray optJSONArray;
        if (e != null || o == null || (optJSONArray = o.optJSONArray("slots")) == null || (emptyList = this$0.objects(optJSONArray)) == null) {
            emptyList = CollectionsKt.emptyList();
        }
        this$0.profiles = emptyList;
        if (e == null && o != null) {
            this$0.isOnroad = o.optBoolean("isOnroad");
        }
        this$0.paint();
        return Unit.INSTANCE;
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ void loadTailscale$default(GxSystemPage gxSystemPage, Function0 function0, int i, Object obj) {
        if ((i & 1) != 0) {
            function0 = null;
        }
        gxSystemPage.loadTailscale(function0);
    }

    private final void loadTailscale(final Function0<Unit> then) {
        getJson("/api/tailscale/installed", new Function2() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda55
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit loadTailscale$lambda$47;
                loadTailscale$lambda$47 = GxSystemPage.loadTailscale$lambda$47(GxSystemPage.this, then, (String) obj, (JSONObject) obj2);
                return loadTailscale$lambda$47;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit loadTailscale$lambda$47(GxSystemPage this$0, Function0 $then, String e, JSONObject o) {
        boolean z = false;
        if (e == null) {
            if (o != null && o.optBoolean("installed")) {
                z = true;
            }
        }
        this$0.tsInstalled = z;
        this$0.tsLoaded = true;
        this$0.paint();
        if ($then != null) {
            $then.invoke();
        }
        return Unit.INSTANCE;
    }

    private final void maybeLoadAgnos() {
        final String b = this.targetBranch;
        if ((b.length() == 0) || Intrinsics.areEqual(b, this.currentBranch) || !this.branches.contains(b) || !Intrinsics.areEqual(this.versionMode, "latest")) {
            return;
        }
        JSONObject jSONObject = this.fastStatus;
        if ((jSONObject != null && jSONObject.optBoolean("running")) || this.agnosByBranch.containsKey(b) || this.agnosErrByBranch.containsKey(b) || Intrinsics.areEqual(this.agnosBusyFor, b)) {
            return;
        }
        this.agnosBusyFor = b;
        json("GET", "/api/update/agnos_status?branch=" + enc(b), null, 45L, new Function2() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda65
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit maybeLoadAgnos$lambda$50;
                maybeLoadAgnos$lambda$50 = GxSystemPage.maybeLoadAgnos$lambda$50(GxSystemPage.this, b, (String) obj, obj2);
                return maybeLoadAgnos$lambda$50;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit maybeLoadAgnos$lambda$50(GxSystemPage this$0, String $b, String e, Object v) {
        String str$default;
        if (Intrinsics.areEqual(this$0.agnosBusyFor, $b)) {
            this$0.agnosBusyFor = "";
        }
        String str = null;
        JSONObject jSONObject = v instanceof JSONObject ? (JSONObject) v : null;
        JSONObject a = jSONObject != null ? jSONObject.optJSONObject("agnosUpdate") : null;
        if (e != null) {
            this$0.agnosErrByBranch.put($b, e);
        } else {
            this$0.agnosByBranch.put($b, a);
            if (a != null && (str$default = GxPage.str$default(this$0, a, "error", null, 2, null)) != null) {
                String str2 = str$default;
                if (!StringsKt.isBlank(str2)) {
                    str = str2;
                }
                String it = str;
                if (it != null) {
                    this$0.agnosErrByBranch.put($b, it);
                }
            }
        }
        if (e == null) {
            boolean z = false;
            if (a != null && a.optBoolean("available")) {
                z = true;
            }
            if (z && Intrinsics.areEqual(this$0.targetBranch, $b)) {
                this$0.toast("Selected branch includes an AGNOS firmware update.");
            }
        }
        this$0.forceAdv();
        return Unit.INSTANCE;
    }

    private final void paint() {
        if (this.swWrap == null) {
            return;
        }
        TextView textView = this.loadingNote;
        LinearLayout linearLayout = null;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("loadingNote");
            textView = null;
        }
        textView.setVisibility(this.branchLoading ? 0 : 8);
        LinearLayout linearLayout2 = this.swWrap;
        if (linearLayout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("swWrap");
        } else {
            linearLayout = linearLayout2;
        }
        linearLayout.setVisibility(this.branchLoading ? 8 : 0);
        paintNotices();
        paintStatus();
        paintControls();
        paintAdv();
        paintProfiles();
        paintFactory();
    }

    private final void paintNotices() {
        LinearLayout linearLayout = this.noticeBox;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("noticeBox");
            linearLayout = null;
        }
        fill(linearLayout, this.isOnroad + "|" + this.rebootPending + "|" + this.statusUnavailable + "|" + this.reconnectedNotice, new Function1() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda40
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit paintNotices$lambda$51;
                paintNotices$lambda$51 = GxSystemPage.paintNotices$lambda$51(GxSystemPage.this, (LinearLayout) obj);
                return paintNotices$lambda$51;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paintNotices$lambda$51(GxSystemPage this$0, LinearLayout b) {
        Intrinsics.checkNotNullParameter(b, "b");
        if (this$0.isOnroad) {
            noticeCard$default(this$0, b, "Updates and branch switching are only available while offroad.", null, 0, 12, null);
        }
        if (this$0.rebootPending) {
            this$0.noticeCard(b, this$0.statusUnavailable ? "The device is temporarily offline. StarView will keep checking until it reconnects." : "The update is complete. Waiting for the device to reconnect…", "Device rebooting", CarUi.Companion.getACCENT());
        } else if (this$0.reconnectedNotice) {
            this$0.noticeCard(b, "Galaxy is connected again and the update status is current.", "Device reconnected", CarUi.Companion.getACCENT());
        }
        return Unit.INSTANCE;
    }

    private final void paintStatus() {
        final JSONObject s = this.fastStatus;
        LinearLayout linearLayout = this.statusBox;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("statusBox");
            linearLayout = null;
        }
        String jSONObject = s != null ? s.toString() : null;
        fill(linearLayout, jSONObject + "|" + getUpdateInProgress() + "|" + this.checkedForUpdates + "|" + this.statusUnavailable + "|" + this.currentBranch, new Function1() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda71
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit paintStatus$lambda$64;
                paintStatus$lambda$64 = GxSystemPage.paintStatus$lambda$64(s, this, (LinearLayout) obj);
                return paintStatus$lambda$64;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paintStatus$lambda$64(JSONObject $s, final GxSystemPage this$0, LinearLayout b) {
        int i;
        String str;
        String str2;
        List emptyList;
        Collection destination$iv$iv;
        Iterable strings;
        int i2;
        Iterable strings2;
        int i3;
        int i4;
        Intrinsics.checkNotNullParameter(b, "b");
        if ($s == null) {
            return Unit.INSTANCE;
        }
        LinearLayout c = this$0.card(b);
        LinearLayout $this$paintStatus_u24lambda_u2464_u24lambda_u2452 = new LinearLayout(this$0.getAct());
        $this$paintStatus_u24lambda_u2464_u24lambda_u2452.setOrientation(0);
        $this$paintStatus_u24lambda_u2464_u24lambda_u2452.setGravity(16);
        TextView $this$paintStatus_u24lambda_u2464_u24lambda_u2453 = new TextView(this$0.getAct());
        $this$paintStatus_u24lambda_u2464_u24lambda_u2453.setText("Update Status");
        $this$paintStatus_u24lambda_u2464_u24lambda_u2453.setTextSize(21.0f);
        $this$paintStatus_u24lambda_u2464_u24lambda_u2453.setTextColor(CarUi.Companion.getTEXT());
        $this$paintStatus_u24lambda_u2464_u24lambda_u2452.addView($this$paintStatus_u24lambda_u2464_u24lambda_u2453, new LinearLayout.LayoutParams(0, -2, 1.0f));
        Pair pair = this$0.getUpdateInProgress() ? TuplesKt.to((this$0.rebootPending || this$0.getStatusRebooting()) ? "Reconnecting…" : Math.round(this$0.pct($s.opt("progressPercent"))) + "%", Integer.valueOf(CarUi.Companion.getACCENT())) : this$0.getUpdateAvailable() ? TuplesKt.to("Update available", Integer.valueOf(CarUi.Companion.getWARN())) : this$0.checkedForUpdates ? TuplesKt.to("Up to date", Integer.valueOf(CarUi.Companion.getDIM())) : TuplesKt.to("Not checked", Integer.valueOf(CarUi.Companion.getDIM()));
        String chip = (String) pair.component1();
        int chipColor = ((Number) pair.component2()).intValue();
        TextView $this$paintStatus_u24lambda_u2464_u24lambda_u2454 = new TextView(this$0.getAct());
        $this$paintStatus_u24lambda_u2464_u24lambda_u2454.setText(chip);
        $this$paintStatus_u24lambda_u2464_u24lambda_u2454.setTextSize(16.0f);
        $this$paintStatus_u24lambda_u2464_u24lambda_u2454.setTypeface(Typeface.DEFAULT_BOLD);
        $this$paintStatus_u24lambda_u2464_u24lambda_u2454.setTextColor(chipColor);
        $this$paintStatus_u24lambda_u2464_u24lambda_u2454.setBackground(this$0.getUi().rounded(CarUi.Companion.getCARD_ON(), 12, Integer.valueOf(chipColor)));
        $this$paintStatus_u24lambda_u2464_u24lambda_u2454.setPadding(this$0.getUi().dp(14), this$0.getUi().dp(6), this$0.getUi().dp(14), this$0.getUi().dp(6));
        $this$paintStatus_u24lambda_u2464_u24lambda_u2452.addView($this$paintStatus_u24lambda_u2464_u24lambda_u2454);
        c.addView($this$paintStatus_u24lambda_u2464_u24lambda_u2452);
        LinearLayout linearLayout = c;
        String str$default = GxPage.str$default(this$0, $s, "branch", null, 2, null);
        if (StringsKt.isBlank(str$default)) {
            str$default = this$0.currentBranch;
        }
        String str3 = str$default;
        if (StringsKt.isBlank(str3)) {
            str3 = "—";
        }
        kv$default(this$0, linearLayout, "Installed branch", str3, false, null, 24, null);
        if (!this$0.getUpdateInProgress() || this$0.rebootPending) {
            i = 0;
            str = "%";
            str2 = "progressPercent";
        } else {
            i = 0;
            str = "%";
            str2 = "progressPercent";
            kv$default(this$0, c, "Stage", GxPage.str$default(this$0, $s, "stage", null, 2, null) + " · " + GxPage.str$default(this$0, $s, "progressLabel", null, 2, null), false, null, 24, null);
        }
        LinearLayout linearLayout2 = c;
        String str4 = str2;
        String str5 = str;
        int i5 = i;
        kv$default(this$0, linearLayout2, "Local", this$0.shortCommit(GxPage.str$default(this$0, $s, "localCommit", null, 2, null)), true, null, 16, null);
        final String rcu = this$0.remoteCommitUrl();
        this$0.kv(c, "Remote", this$0.shortCommit(GxPage.str$default(this$0, $s, "remoteCommit", null, 2, null)), true, (rcu.length() > 0 ? 1 : i5) != 0 ? new Function0() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit paintStatus$lambda$64$lambda$57;
                paintStatus$lambda$64$lambda$57 = GxSystemPage.paintStatus$lambda$64$lambda$57(GxSystemPage.this, rcu);
                return paintStatus$lambda$64$lambda$57;
            }
        } : null);
        final String rec = this$0.recentCommitsUrl();
        if ((rec.length() > 0 ? 1 : i5) != 0) {
            Pair[] pairArr = new Pair[1];
            pairArr[i5] = TuplesKt.to("Show recent commits on GitHub", new Function0() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda2
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit paintStatus$lambda$64$lambda$58;
                    paintStatus$lambda$64$lambda$58 = GxSystemPage.paintStatus$lambda$64$lambda$58(GxSystemPage.this, rec);
                    return paintStatus$lambda$64$lambda$58;
                }
            });
            GxPage.buttons$default(this$0, pairArr, c, false, 4, null);
        }
        if (this$0.getUpdateInProgress() && !this$0.rebootPending) {
            ProgressBar p = this$0.progress(c);
            double v = this$0.pct($s.opt(str4));
            this$0.setProgress(p, v);
            if (Intrinsics.areEqual(GxPage.str$default(this$0, $s, "stage", null, 2, null), "error")) {
                i4 = GxSystemKt.GxSystem_DANGER;
                p.setProgressTintList(ColorStateList.valueOf(i4));
            }
            int it = $s.optInt("progressTotalSteps", i5);
            if (it == 0) {
                it = 5;
            }
            int steps = it;
            int optInt = $s.optInt("progressStep", i5);
            String str$default2 = GxPage.str$default(this$0, $s, "progressLabel", null, 2, null);
            if (StringsKt.isBlank(str$default2)) {
                str$default2 = GxPage.str$default(this$0, $s, "stage", null, 2, null);
            }
            String str6 = str$default2;
            if (StringsKt.isBlank(str6)) {
                str6 = "Updating";
            }
            this$0.text("Step " + optInt + "/" + steps + ": " + ((Object) str6) + "   " + Math.round(v) + str5, c, 15.0f, CarUi.Companion.getTEXT(), true);
            if (!StringsKt.isBlank(GxPage.str$default(this$0, $s, "progressDetail", null, 2, null))) {
                GxPage.text$default(this$0, GxPage.str$default(this$0, $s, "progressDetail", null, 2, null), c, 0.0f, 0, false, 28, null);
            }
        }
        if (!StringsKt.isBlank(GxPage.str$default(this$0, $s, "message", null, 2, null))) {
            GxPage.text$default(this$0, GxPage.str$default(this$0, $s, "message", null, 2, null), c, 0.0f, 0, false, 28, null);
        }
        if (this$0.statusUnavailable) {
            GxPage.text$default(this$0, "Waiting for the device to reconnect. The last update status is being kept on screen.", c, 0.0f, 0, false, 28, null);
        }
        if (!StringsKt.isBlank(GxPage.str$default(this$0, $s, "warning", null, 2, null)) && (this$0.getUpdateInProgress() || $s.optBoolean("updateAvailable"))) {
            i3 = GxSystemKt.GxSystem_DANGER;
            GxPage.text$default(this$0, GxPage.str$default(this$0, $s, "warning", null, 2, null), c, 15.0f, i3, false, 16, null);
        }
        JSONObject ag = $s.optJSONObject("agnosUpdate");
        if (((ag == null || !ag.optBoolean("available")) ? i5 : 1) != 0) {
            JSONArray optJSONArray = ag.optJSONArray("warnings");
            if (optJSONArray == null || (strings2 = this$0.strings(optJSONArray)) == null) {
                emptyList = CollectionsKt.emptyList();
            } else {
                Iterable $this$filter$iv = strings2;
                Collection destination$iv$iv2 = new ArrayList();
                for (Object element$iv$iv : $this$filter$iv) {
                    String it2 = (String) element$iv$iv;
                    if (!StringsKt.isBlank(it2)) {
                        destination$iv$iv2.add(element$iv$iv);
                    }
                }
                emptyList = (List) destination$iv$iv2;
            }
            List<String> ws = emptyList;
            for (String w : ws) {
                i2 = GxSystemKt.GxSystem_DANGER;
                GxPage.text$default(this$0, "⚠ " + w, c, 15.0f, i2, false, 16, null);
            }
            JSONArray optJSONArray2 = ag.optJSONArray("changedPartitions");
            if (optJSONArray2 == null || (strings = this$0.strings(optJSONArray2)) == null) {
                destination$iv$iv = CollectionsKt.emptyList();
            } else {
                Iterable $this$filter$iv2 = strings;
                Collection destination$iv$iv3 = new ArrayList();
                for (Object element$iv$iv2 : $this$filter$iv2) {
                    String it3 = (String) element$iv$iv2;
                    if (!StringsKt.isBlank(it3)) {
                        destination$iv$iv3.add(element$iv$iv2);
                    }
                }
                destination$iv$iv = (List) destination$iv$iv3;
            }
            Collection parts = destination$iv$iv;
            if (!parts.isEmpty()) {
                GxPage.text$default(this$0, "Changed AGNOS partitions: " + CollectionsKt.joinToString$default(parts, ", ", null, null, 0, null, null, 62, null), c, 0.0f, 0, false, 28, null);
            }
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paintStatus$lambda$64$lambda$57(GxSystemPage this$0, String $rcu) {
        this$0.openUrl($rcu);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paintStatus$lambda$64$lambda$58(GxSystemPage this$0, String $rec) {
        this$0.openUrl($rec);
        return Unit.INSTANCE;
    }

    private final void paintControls() {
        JSONObject s = this.fastStatus;
        Switch r1 = this.autoSwitch;
        TextView textView = null;
        if (r1 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("autoSwitch");
            r1 = null;
        }
        r1.setChecked(s != null && s.optBoolean("automaticUpdates"));
        Switch r12 = this.autoSwitch;
        if (r12 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("autoSwitch");
            r12 = null;
        }
        en(r12, (s == null || this.isOnroad || this.autoBusy || getUpdateInProgress()) ? false : true);
        GxPage.Row row = this.fastRow;
        if (row == null) {
            Intrinsics.throwUninitializedPropertyAccessException("fastRow");
            row = null;
        }
        row.setDesc(getUpdateAvailable() ? "An update is available. Install it with Fast Update on StarView's Software page." : "Check for and install the latest commit on this branch. Fast update lives on StarView's Software page.");
        boolean actOk = (!(this.busy.length() == 0) || this.isOnroad || getUpdateInProgress()) ? false : true;
        TextView textView2 = this.checkBtn;
        if (textView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("checkBtn");
            textView2 = null;
        }
        textView2.setText(Intrinsics.areEqual(this.busy, "check") ? "Checking..." : "Check");
        TextView textView3 = this.recoverBtn;
        if (textView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("recoverBtn");
            textView3 = null;
        }
        textView3.setText(Intrinsics.areEqual(this.busy, "recover") ? "Working..." : "Recover");
        TextView textView4 = this.rollbackBtn;
        if (textView4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rollbackBtn");
            textView4 = null;
        }
        textView4.setText(Intrinsics.areEqual(this.busy, "rollback") ? "Working..." : "Rollback");
        TextView textView5 = this.checkBtn;
        if (textView5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("checkBtn");
            textView5 = null;
        }
        en(textView5, actOk);
        TextView textView6 = this.recoverBtn;
        if (textView6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("recoverBtn");
            textView6 = null;
        }
        en(textView6, actOk);
        TextView textView7 = this.rollbackBtn;
        if (textView7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rollbackBtn");
            textView7 = null;
        }
        en(textView7, actOk);
        TextView textView8 = this.upToDateNote;
        if (textView8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("upToDateNote");
        } else {
            textView = textView8;
        }
        textView.setVisibility((!this.checkedForUpdates || getUpdateAvailable() || getUpdateInProgress()) ? 8 : 0);
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x009f  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x00d7  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x00a6  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final void paintAdv() {
        boolean blocked;
        String str;
        LinearLayout linearLayout;
        LinearLayout linearLayout2;
        if (!this.advOpen) {
            LinearLayout linearLayout3 = this.advBox;
            if (linearLayout3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("advBox");
                linearLayout2 = null;
            } else {
                linearLayout2 = linearLayout3;
            }
            fill(linearLayout2, "closed", new Function1() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda78
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    Unit paintAdv$lambda$65;
                    paintAdv$lambda$65 = GxSystemPage.paintAdv$lambda$65((LinearLayout) obj);
                    return paintAdv$lambda$65;
                }
            });
            return;
        }
        maybeLoadAgnos();
        boolean blocked2 = getBranchSwitchBlocked();
        List<String> list = this.branches;
        String str2 = this.currentBranch;
        String str3 = this.targetBranch;
        Boolean valueOf = Boolean.valueOf(this.otherOpen);
        String str4 = this.versionMode;
        String str5 = this.selectedCommit;
        Integer valueOf2 = Integer.valueOf(this.versionCommits.size());
        Boolean valueOf3 = Boolean.valueOf(this.versionHasMore);
        Boolean valueOf4 = Boolean.valueOf(this.versionLoading);
        String str6 = this.versionError;
        String str7 = this.versionNotice;
        Boolean valueOf5 = Boolean.valueOf(this.branchBusy);
        Boolean valueOf6 = Boolean.valueOf(blocked2);
        Boolean valueOf7 = Boolean.valueOf(getInstallVersionBlocked());
        Boolean valueOf8 = Boolean.valueOf(this.branchListFallback);
        Boolean valueOf9 = Boolean.valueOf(this.pickerOpen);
        String hashMap = this.expanded.toString();
        JSONObject jSONObject = this.fastStatus;
        if (jSONObject != null) {
            blocked = blocked2;
            JSONObject optJSONObject = jSONObject.optJSONObject("versionPin");
            if (optJSONObject != null) {
                str = optJSONObject.toString();
                JSONObject jSONObject2 = this.agnosByBranch.get(this.targetBranch);
                String sig = CollectionsKt.joinToString$default(CollectionsKt.listOf(list, str2, str3, valueOf, str4, str5, valueOf2, valueOf3, valueOf4, str6, str7, valueOf5, valueOf6, valueOf7, valueOf8, valueOf9, hashMap, str, jSONObject2 == null ? jSONObject2.toString() : null, this.agnosErrByBranch.get(this.targetBranch), this.agnosBusyFor), "|", null, null, 0, null, null, 62, null);
                linearLayout = this.advBox;
                if (linearLayout == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("advBox");
                    linearLayout = null;
                }
                final boolean blocked3 = blocked;
                fill(linearLayout, sig, new Function1() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda79
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        Unit paintAdv$lambda$74;
                        paintAdv$lambda$74 = GxSystemPage.paintAdv$lambda$74(GxSystemPage.this, blocked3, (LinearLayout) obj);
                        return paintAdv$lambda$74;
                    }
                });
            }
        } else {
            blocked = blocked2;
        }
        str = null;
        JSONObject jSONObject22 = this.agnosByBranch.get(this.targetBranch);
        String sig2 = CollectionsKt.joinToString$default(CollectionsKt.listOf(list, str2, str3, valueOf, str4, str5, valueOf2, valueOf3, valueOf4, str6, str7, valueOf5, valueOf6, valueOf7, valueOf8, valueOf9, hashMap, str, jSONObject22 == null ? jSONObject22.toString() : null, this.agnosErrByBranch.get(this.targetBranch), this.agnosBusyFor), "|", null, null, 0, null, null, 62, null);
        linearLayout = this.advBox;
        if (linearLayout == null) {
        }
        final boolean blocked32 = blocked;
        fill(linearLayout, sig2, new Function1() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda79
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit paintAdv$lambda$74;
                paintAdv$lambda$74 = GxSystemPage.paintAdv$lambda$74(GxSystemPage.this, blocked32, (LinearLayout) obj);
                return paintAdv$lambda$74;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paintAdv$lambda$65(LinearLayout it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static final Unit paintAdv$lambda$74(final GxSystemPage gxSystemPage, final boolean z, LinearLayout b) {
        int i;
        int i2;
        Intrinsics.checkNotNullParameter(b, "b");
        gxSystemPage.header("Install a Version", b);
        gxSystemPage.note("Most people should stay on the latest version. Use this only to install a specific branch or historical version while troubleshooting.", b);
        String str = gxSystemPage.currentBranch;
        if (StringsKt.isBlank(str)) {
            str = "Unknown";
        }
        gxSystemPage.note("Installed branch: " + ((Object) str), b);
        boolean z2 = false;
        if (gxSystemPage.otherOpen) {
            i = 2;
        } else {
            String str2 = gxSystemPage.targetBranch;
            i = Intrinsics.areEqual(str2, "StarPilot") ? 0 : Intrinsics.areEqual(str2, "Dom") ? 1 : -1;
        }
        gxSystemPage.choice("Target branch", "StarPilot — Stable releases. Recommended for most users.\nDom — Latest features and fixes under development. Updates regularly and may introduce bugs.", CollectionsKt.listOf((Object[]) new String[]{"StarPilot — Release", "Dom — Development", "Other branches…"}), i, b, new Function1() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda72
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit paintAdv$lambda$74$lambda$67;
                paintAdv$lambda$74$lambda$67 = GxSystemPage.paintAdv$lambda$74$lambda$67(z, gxSystemPage, ((Integer) obj).intValue());
                return paintAdv$lambda$74$lambda$67;
            }
        });
        float f = 0.4f;
        b.getChildAt(b.getChildCount() - 1).setAlpha((z || gxSystemPage.branchBusy) ? 0.4f : 1.0f);
        if (gxSystemPage.otherOpen) {
            final List<String> otherBranches = gxSystemPage.getOtherBranches();
            gxSystemPage.en(GxPage.Row.button$default(gxSystemPage.row("Other branches", "Additional branches from this installation's repository.", b, 1), otherBranches.contains(gxSystemPage.targetBranch) ? gxSystemPage.targetBranch : !otherBranches.isEmpty() ? "Select another branch" : "No other branches available", false, new Function0() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda73
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit paintAdv$lambda$74$lambda$70;
                    paintAdv$lambda$74$lambda$70 = GxSystemPage.paintAdv$lambda$74$lambda$70(otherBranches, gxSystemPage);
                    return paintAdv$lambda$74$lambda$70;
                }
            }, 2, null), (z || gxSystemPage.branchBusy || otherBranches.isEmpty()) ? false : true);
        }
        if (gxSystemPage.branchListFallback) {
            gxSystemPage.note("The full branch list is temporarily unavailable. Standard branches are shown; selecting a version still verifies it with the device.", b);
        }
        if ((gxSystemPage.targetBranch.length() > 0) == false) {
            i2 = 1;
        } else {
            final boolean contains = gxSystemPage.branches.contains(gxSystemPage.targetBranch);
            i2 = 1;
            gxSystemPage.choice("Version", null, CollectionsKt.listOf((Object[]) new String[]{"Latest", "Choose earlier…"}), Intrinsics.areEqual(gxSystemPage.versionMode, "earlier") ? 1 : 0, b, new Function1() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda74
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    Unit paintAdv$lambda$74$lambda$71;
                    paintAdv$lambda$74$lambda$71 = GxSystemPage.paintAdv$lambda$74$lambda$71(z, gxSystemPage, contains, ((Integer) obj).intValue());
                    return paintAdv$lambda$74$lambda$71;
                }
            });
            View childAt = b.getChildAt(b.getChildCount() - 1);
            if (!z && !gxSystemPage.branchBusy && contains) {
                f = 1.0f;
            }
            childAt.setAlpha(f);
            if (Intrinsics.areEqual(gxSystemPage.versionMode, "earlier")) {
                gxSystemPage.buildPicker(b, z || gxSystemPage.branchBusy);
            }
            if (Intrinsics.areEqual(gxSystemPage.versionMode, "latest") && !Intrinsics.areEqual(gxSystemPage.targetBranch, gxSystemPage.currentBranch) && contains) {
                gxSystemPage.agnosNote(b);
            }
            if (!contains) {
                gxSystemPage.note("This installed branch is no longer listed by the repository. Select an available target branch to install a version.", b);
            }
            Pair<String, ? extends Function0<Unit>>[] pairArr = new Pair[1];
            pairArr[0] = TuplesKt.to(gxSystemPage.branchBusy ? "Starting installation…" : "Install selected version", new Function0() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda75
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit paintAdv$lambda$74$lambda$72;
                    paintAdv$lambda$74$lambda$72 = GxSystemPage.paintAdv$lambda$74$lambda$72(GxSystemPage.this);
                    return paintAdv$lambda$74$lambda$72;
                }
            });
            gxSystemPage.en(gxSystemPage.buttons(pairArr, b, true).getChildAt(0), !gxSystemPage.getInstallVersionBlocked());
        }
        JSONObject jSONObject = gxSystemPage.fastStatus;
        JSONObject optJSONObject = jSONObject != null ? jSONObject.optJSONObject("versionPin") : null;
        if (optJSONObject != null) {
            LinearLayout card = gxSystemPage.card(b);
            JSONObject jSONObject2 = optJSONObject;
            gxSystemPage.text("Pinned version: " + GxPage.str$default(gxSystemPage, jSONObject2, "branch", null, 2, null) + " · " + gxSystemPage.shortCommit(GxPage.str$default(gxSystemPage, jSONObject2, "commit", null, 2, null)), card, 15.0f, CarUi.Companion.getTEXT(), true);
            GxPage.text$default(gxSystemPage, "Automatic updates were paused at installation.", card, 0.0f, 0, false, 28, null);
            Pair[] pairArr2 = new Pair[i2];
            pairArr2[0] = TuplesKt.to("Return to Latest", new Function0() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda76
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit paintAdv$lambda$74$lambda$73;
                    paintAdv$lambda$74$lambda$73 = GxSystemPage.paintAdv$lambda$74$lambda$73(GxSystemPage.this);
                    return paintAdv$lambda$74$lambda$73;
                }
            });
            View childAt2 = GxPage.buttons$default(gxSystemPage, pairArr2, card, false, 4, null).getChildAt(0);
            if (!z && !gxSystemPage.branchBusy && gxSystemPage.branches.contains(GxPage.str$default(gxSystemPage, optJSONObject, "branch", null, 2, null))) {
                z2 = true;
            }
            gxSystemPage.en(childAt2, z2);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paintAdv$lambda$74$lambda$67(boolean $blocked, GxSystemPage this$0, int i) {
        if ($blocked || this$0.branchBusy) {
            this$0.forceAdv();
            return Unit.INSTANCE;
        }
        this$0.otherOpen = i == 2;
        if (this$0.otherOpen) {
            this$0.targetBranch = "";
            this$0.resetVersions();
            this$0.forceAdv();
        } else {
            this$0.selectTargetBranch(i == 0 ? "StarPilot" : "Dom");
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paintAdv$lambda$74$lambda$70(final List $ob, final GxSystemPage this$0) {
        if ($ob.isEmpty()) {
            return Unit.INSTANCE;
        }
        List $this$map$iv = $ob;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        for (Object item$iv$iv : $this$map$iv) {
            String it = (String) item$iv$iv;
            if (Intrinsics.areEqual(it, this$0.currentBranch)) {
                it = it + " (current)";
            }
            destination$iv$iv.add(it);
        }
        this$0.pick("Other branches", (List) destination$iv$iv, new Function1() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda44
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit paintAdv$lambda$74$lambda$70$lambda$69;
                paintAdv$lambda$74$lambda$70$lambda$69 = GxSystemPage.paintAdv$lambda$74$lambda$70$lambda$69(GxSystemPage.this, $ob, ((Integer) obj).intValue());
                return paintAdv$lambda$74$lambda$70$lambda$69;
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paintAdv$lambda$74$lambda$70$lambda$69(GxSystemPage this$0, List $ob, int i) {
        this$0.selectTargetBranch((String) $ob.get(i));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paintAdv$lambda$74$lambda$71(boolean $blocked, GxSystemPage this$0, boolean $listed, int i) {
        if ($blocked || this$0.branchBusy || !$listed) {
            this$0.forceAdv();
            return Unit.INSTANCE;
        }
        String mode = i == 1 ? "earlier" : "latest";
        this$0.resetVersions();
        this$0.versionMode = mode;
        if (Intrinsics.areEqual(mode, "earlier")) {
            this$0.loadVersions(false);
        } else {
            this$0.forceAdv();
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paintAdv$lambda$74$lambda$72(GxSystemPage this$0) {
        this$0.installSelectedVersion();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paintAdv$lambda$74$lambda$73(GxSystemPage this$0) {
        this$0.returnToLatest();
        return Unit.INSTANCE;
    }

    private final void agnosNote(LinearLayout b) {
        List emptyList;
        Collection destination$iv$iv;
        Iterable strings;
        Iterable strings2;
        JSONObject a = this.agnosByBranch.get(this.targetBranch);
        String err = this.agnosErrByBranch.get(this.targetBranch);
        if (Intrinsics.areEqual(this.agnosBusyFor, this.targetBranch)) {
            note("Checking whether " + this.targetBranch + " includes an AGNOS firmware update…", b);
            return;
        }
        if (a != null && a.optBoolean("available")) {
            LinearLayout c = card(b);
            text("AGNOS firmware update included", c, 21.0f, CarUi.Companion.getWARN(), true);
            JSONArray optJSONArray = a.optJSONArray("warnings");
            if (optJSONArray == null || (strings2 = strings(optJSONArray)) == null) {
                emptyList = CollectionsKt.emptyList();
            } else {
                Iterable $this$filter$iv = strings2;
                Collection destination$iv$iv2 = new ArrayList();
                for (Object element$iv$iv : $this$filter$iv) {
                    String it = (String) element$iv$iv;
                    if (!StringsKt.isBlank(it)) {
                        destination$iv$iv2.add(element$iv$iv);
                    }
                }
                emptyList = (List) destination$iv$iv2;
            }
            List<String> ws = emptyList;
            for (String w : ws) {
                GxPage.text$default(this, "• " + w, c, 15.0f, CarUi.Companion.getTEXT(), false, 16, null);
            }
            JSONArray optJSONArray2 = a.optJSONArray("changedPartitions");
            if (optJSONArray2 == null || (strings = strings(optJSONArray2)) == null) {
                destination$iv$iv = CollectionsKt.emptyList();
            } else {
                Iterable $this$filter$iv2 = strings;
                Collection destination$iv$iv3 = new ArrayList();
                for (Object element$iv$iv2 : $this$filter$iv2) {
                    String it2 = (String) element$iv$iv2;
                    if (!StringsKt.isBlank(it2)) {
                        destination$iv$iv3.add(element$iv$iv2);
                    }
                }
                destination$iv$iv = (List) destination$iv$iv3;
            }
            Collection parts = destination$iv$iv;
            if (!parts.isEmpty()) {
                GxPage.text$default(this, "Changed AGNOS partitions: " + CollectionsKt.joinToString$default(parts, ", ", null, null, 0, null, null, 62, null), c, 0.0f, 0, false, 28, null);
                return;
            }
            return;
        }
        String str = err;
        if (!(str == null || StringsKt.isBlank(str))) {
            note("AGNOS update check: " + err, b);
            return;
        }
        if (a != null && a.optBoolean("checked")) {
            note("No AGNOS firmware update is needed for " + this.targetBranch + ".", b);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:89:0x04c0  */
    /* JADX WARN: Removed duplicated region for block: B:96:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final void buildPicker(LinearLayout b, final boolean disabled) {
        Object element$iv;
        String str;
        String str2;
        boolean z;
        boolean z2;
        int i;
        boolean z3;
        boolean z4;
        int i2;
        boolean release = Intrinsics.areEqual(this.targetBranch, "StarPilot");
        List choices = versionChoices();
        List groups = groupVersionHistory(choices);
        if (this.expanded.isEmpty() && !groups.isEmpty()) {
            this.expanded.put(groups.get(0).getKey(), true);
        }
        List $this$firstOrNull$iv = choices;
        Iterator it = $this$firstOrNull$iv.iterator();
        while (true) {
            if (it.hasNext()) {
                element$iv = it.next();
                JSONObject it2 = (JSONObject) element$iv;
                if (Intrinsics.areEqual(GxPage.str$default(this, it2, "sha", null, 2, null), this.selectedCommit)) {
                    break;
                }
            } else {
                element$iv = null;
                break;
            }
        }
        JSONObject selected = (JSONObject) element$iv;
        if (release) {
            str = choices.size() + " " + (choices.size() == 1 ? "release" : "releases") + " · " + this.versionCommits.size() + " builds checked";
        } else {
            List $this$filter$iv = groups;
            Collection destination$iv$iv = new ArrayList();
            for (Object element$iv$iv : $this$filter$iv) {
                DayGroup it3 = (DayGroup) element$iv$iv;
                Iterable $this$filter$iv2 = $this$filter$iv;
                if (!Intrinsics.areEqual(it3.getKey(), "unknown")) {
                    destination$iv$iv.add(element$iv$iv);
                }
                $this$filter$iv = $this$filter$iv2;
            }
            List known = (List) destination$iv$iv;
            if (known.isEmpty()) {
                str = !this.versionCommits.isEmpty() ? this.versionCommits.size() + " versions loaded" : "No history loaded yet";
            } else {
                str = this.versionCommits.size() + " versions · " + ((DayGroup) CollectionsKt.last(known)).getLabel() + (known.size() > 1 ? " – " + ((DayGroup) known.get(0)).getLabel() : "");
            }
        }
        String range = str;
        LinearLayout c = card(b);
        GxPage.text$default(this, "Earlier version", c, 15.0f, CarUi.Companion.getDIM(), false, 16, null);
        text(selected != null ? versionTitle(selected, release) : "Select an earlier version", c, 21.0f, CarUi.Companion.getTEXT(), true);
        GxSystemPage gxSystemPage = this;
        if (this.versionHasMore) {
            str2 = release ? "Open the picker to load older versions." : "Open the picker to load older days.";
        } else {
            if (!this.versionCommits.isEmpty()) {
                if (this.versionError.length() == 0) {
                    str2 = "All available history loaded.";
                }
            }
            str2 = "Open the picker to browse history.";
        }
        GxPage.text$default(gxSystemPage, range + ". " + str2, c, 0.0f, 0, false, 28, null);
        if (this.versionNotice.length() > 0) {
            GxPage.text$default(this, this.versionNotice, c, 0.0f, 0, false, 28, null);
        }
        GxSystemPage gxSystemPage2 = this;
        Pair[] pairArr = new Pair[1];
        pairArr[0] = TuplesKt.to(this.pickerOpen ? "Hide versions" : "Browse versions", new Function0() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda46
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildPicker$lambda$79;
                buildPicker$lambda$79 = GxSystemPage.buildPicker$lambda$79(disabled, this);
                return buildPicker$lambda$79;
            }
        });
        LinearLayout bar = GxPage.buttons$default(gxSystemPage2, pairArr, c, false, 4, null);
        en(bar.getChildAt(0), !disabled);
        if (!this.pickerOpen) {
            z = false;
            z2 = true;
        } else if (disabled) {
            z = false;
            z2 = true;
        } else {
            LinearLayout hc = card(b);
            text(release ? "Earlier StarPilot versions" : "Earlier versions by day", hc, 21.0f, CarUi.Companion.getTEXT(), true);
            GxPage.text$default(this, range, hc, 0.0f, 0, false, 28, null);
            if (this.versionNotice.length() > 0) {
                GxPage.text$default(this, this.versionNotice, hc, 0.0f, 0, false, 28, null);
            }
            GxPage.text$default(this, release ? "Each version uses its newest build. Build dates are shown in your local time." : "Expand a day to choose a build. Build dates are shown in your local time.", hc, 0.0f, 0, false, 28, null);
            if (release) {
                for (JSONObject cm : choices) {
                    buildPicker$versionRow(this, release, b, disabled, cm, 0);
                }
            } else {
                Iterator<DayGroup> it4 = groups.iterator();
                while (it4.hasNext()) {
                    final DayGroup g = it4.next();
                    final boolean open = Intrinsics.areEqual((Object) this.expanded.get(g.getKey()), (Object) true);
                    GxPage.Row gr = GxPage.row$default(this, g.getLabel() + "  · " + g.getCommits().size() + " " + (g.getCommits().size() == 1 ? "build" : "builds"), null, b, 0, 8, null);
                    gr.value(open ? "▲" : "▼");
                    gr.getView().setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda47
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            GxSystemPage.buildPicker$lambda$82(GxSystemPage.this, g, open, view);
                        }
                    });
                    if (open) {
                        Iterator<JSONObject> it5 = g.getCommits().iterator();
                        Intrinsics.checkNotNullExpressionValue(it5, "iterator(...)");
                        while (it5.hasNext()) {
                            JSONObject next = it5.next();
                            Intrinsics.checkNotNullExpressionValue(next, "next(...)");
                            JSONObject cm2 = next;
                            buildPicker$versionRow(this, release, b, disabled, cm2, 1);
                            it5 = it5;
                            gr = gr;
                            open = open;
                            g = g;
                        }
                    }
                }
            }
            LinearLayout f = card(b);
            if (this.versionError.length() > 0) {
                i2 = GxSystemKt.GxSystem_DANGER;
                GxPage.text$default(this, this.versionError, f, 15.0f, i2, false, 16, null);
            }
            if (this.versionLoading) {
                GxPage.text$default(this, "Loading older history…", f, 0.0f, 0, false, 28, null);
            }
            if (!this.versionHasMore) {
                if (!(this.versionError.length() > 0)) {
                    if (!this.versionLoading) {
                        GxPage.text$default(this, this.versionCommits.isEmpty() ? "No history available." : "All available history loaded.", f, 0.0f, 0, false, 28, null);
                    }
                    z4 = false;
                    z3 = true;
                    if (!this.versionHasMore) {
                        if (this.versionError.length() == 0) {
                            z4 = z3;
                        }
                        if (z4) {
                            GxPage.text$default(this, "Keep loading to browse further back.", f, 0.0f, 0, false, 28, null);
                            return;
                        }
                        return;
                    }
                    return;
                }
            }
            GxSystemPage gxSystemPage3 = this;
            z3 = true;
            Pair[] pairArr2 = new Pair[1];
            pairArr2[0] = TuplesKt.to(this.versionError.length() > 0 ? "Retry history" : release ? "Load older versions" : "Load older days", new Function0() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda48
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit buildPicker$lambda$83;
                    buildPicker$lambda$83 = GxSystemPage.buildPicker$lambda$83(disabled, this);
                    return buildPicker$lambda$83;
                }
            });
            LinearLayout lb = GxPage.buttons$default(gxSystemPage3, pairArr2, f, false, 4, null);
            z4 = false;
            en(lb.getChildAt(0), (this.versionLoading || disabled) ? false : true);
            if (!this.versionHasMore) {
            }
        }
        if (this.versionError.length() > 0) {
            z = z2;
        }
        if (z) {
            i = GxSystemKt.GxSystem_DANGER;
            GxPage.text$default(this, this.versionError, c, 15.0f, i, false, 16, null);
        }
        if (this.versionLoading) {
            GxPage.text$default(this, "Loading older history…", c, 0.0f, 0, false, 28, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildPicker$lambda$79(boolean $disabled, GxSystemPage this$0) {
        if (!$disabled) {
            this$0.pickerOpen = !this$0.pickerOpen;
            this$0.forceAdv();
        }
        return Unit.INSTANCE;
    }

    private static final void buildPicker$versionRow(final GxSystemPage this$0, boolean release, LinearLayout $b, final boolean $disabled, JSONObject cm, int depth) {
        final String sha = GxPage.str$default(this$0, cm, "sha", null, 2, null);
        boolean on = Intrinsics.areEqual(sha, this$0.selectedCommit);
        GxPage.Row r = this$0.row(this$0.versionTitle(cm, release), null, $b, depth);
        r.getTitleView().setTextSize(18.0f);
        if (on) {
            r.getView().setBackground(this$0.getUi().rounded(CarUi.Companion.getCARD_ON(), 14, Integer.valueOf(CarUi.Companion.getACCENT())));
            r.value("✓").setTextColor(CarUi.Companion.getACCENT());
        }
        r.getView().setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda35
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                GxSystemPage.buildPicker$versionRow$lambda$81($disabled, sha, this$0, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void buildPicker$versionRow$lambda$81(boolean $disabled, String $sha, GxSystemPage this$0, View it) {
        Regex regex;
        if ($disabled) {
            return;
        }
        regex = GxSystemKt.GxSystem_SHA;
        if (regex.matches($sha)) {
            Iterable $this$none$iv = this$0.versionChoices();
            boolean z = true;
            if (!($this$none$iv instanceof Collection) || !((Collection) $this$none$iv).isEmpty()) {
                Iterator it2 = $this$none$iv.iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        break;
                    }
                    Object element$iv = it2.next();
                    JSONObject it3 = (JSONObject) element$iv;
                    if (Intrinsics.areEqual(GxPage.str$default(this$0, it3, "sha", null, 2, null), $sha)) {
                        z = false;
                        break;
                    }
                }
            }
            if (z) {
                return;
            }
            this$0.selectedCommit = $sha;
            this$0.pickerOpen = false;
            this$0.forceAdv();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void buildPicker$lambda$82(GxSystemPage this$0, DayGroup $g, boolean $open, View it) {
        this$0.expanded.put($g.getKey(), Boolean.valueOf(!$open));
        this$0.forceAdv();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildPicker$lambda$83(boolean $disabled, GxSystemPage this$0) {
        if (!$disabled && !this$0.versionLoading) {
            this$0.loadVersions(!this$0.versionCommits.isEmpty() && this$0.versionHasMore);
        }
        return Unit.INSTANCE;
    }

    private final void paintProfiles() {
        LinearLayout linearLayout = this.profileNotice;
        LinearLayout linearLayout2 = null;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("profileNotice");
            linearLayout = null;
        }
        fill(linearLayout, String.valueOf(this.isOnroad), new Function1() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda59
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit paintProfiles$lambda$84;
                paintProfiles$lambda$84 = GxSystemPage.paintProfiles$lambda$84(GxSystemPage.this, (LinearLayout) obj);
                return paintProfiles$lambda$84;
            }
        });
        LinearLayout linearLayout3 = this.profilesBox;
        if (linearLayout3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("profilesBox");
        } else {
            linearLayout2 = linearLayout3;
        }
        fill(linearLayout2, CollectionsKt.joinToString$default(this.profiles, null, null, null, 0, null, new Function1() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda60
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                CharSequence paintProfiles$lambda$85;
                paintProfiles$lambda$85 = GxSystemPage.paintProfiles$lambda$85((JSONObject) obj);
                return paintProfiles$lambda$85;
            }
        }, 31, null) + "|" + this.profileBusy + "|" + this.isOnroad, new Function1() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda61
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit paintProfiles$lambda$89;
                paintProfiles$lambda$89 = GxSystemPage.paintProfiles$lambda$89(GxSystemPage.this, (LinearLayout) obj);
                return paintProfiles$lambda$89;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paintProfiles$lambda$84(GxSystemPage this$0, LinearLayout b) {
        Intrinsics.checkNotNullParameter(b, "b");
        if (this$0.isOnroad) {
            noticeCard$default(this$0, b, "Park the vehicle to save or load a profile.", null, 0, 12, null);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CharSequence paintProfiles$lambda$85(JSONObject it) {
        Intrinsics.checkNotNullParameter(it, "it");
        String jSONObject = it.toString();
        Intrinsics.checkNotNullExpressionValue(jSONObject, "toString(...)");
        return jSONObject;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paintProfiles$lambda$89(final GxSystemPage this$0, LinearLayout b) {
        Intrinsics.checkNotNullParameter(b, "b");
        for (final JSONObject p : this$0.profiles) {
            String slot = GxPage.str$default(this$0, p, "slot", null, 2, null);
            String str$default = GxPage.str$default(this$0, p, "label", null, 2, null);
            if (StringsKt.isBlank(str$default)) {
                str$default = slot;
            }
            String label = str$default;
            boolean saved = p.optBoolean("saved");
            boolean invalid = p.optBoolean("invalid");
            GxPage.Row r = GxPage.row$default(this$0, label, invalid ? "Damaged" : saved ? p.optInt("settingsCount") + " settings" : "Empty", b, 0, 8, null);
            boolean z = false;
            this$0.en(GxPage.Row.button$default(r, Intrinsics.areEqual(this$0.profileBusy, new StringBuilder().append("save-").append(slot).toString()) ? "Saving..." : saved ? "Overwrite" : "Save Current", false, new Function0() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda11
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit paintProfiles$lambda$89$lambda$87;
                    paintProfiles$lambda$89$lambda$87 = GxSystemPage.paintProfiles$lambda$89$lambda$87(GxSystemPage.this, p);
                    return paintProfiles$lambda$89$lambda$87;
                }
            }, 2, null), (this$0.profileBusy.length() == 0) && !this$0.isOnroad);
            TextView button = r.button(Intrinsics.areEqual(this$0.profileBusy, new StringBuilder().append("load-").append(slot).toString()) ? "Loading..." : "Load", true, new Function0() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda22
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit paintProfiles$lambda$89$lambda$88;
                    paintProfiles$lambda$89$lambda$88 = GxSystemPage.paintProfiles$lambda$89$lambda$88(GxSystemPage.this, p);
                    return paintProfiles$lambda$89$lambda$88;
                }
            });
            if ((this$0.profileBusy.length() == 0) && !this$0.isOnroad && saved && !invalid) {
                z = true;
            }
            this$0.en(button, z);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paintProfiles$lambda$89$lambda$87(GxSystemPage this$0, JSONObject $p) {
        this$0.saveProfile($p);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paintProfiles$lambda$89$lambda$88(GxSystemPage this$0, JSONObject $p) {
        this$0.loadProfile($p);
        return Unit.INSTANCE;
    }

    private final void paintFactory() {
        String jSONObject;
        final JSONObject s = this.fastStatus;
        final boolean fr = s != null && Intrinsics.areEqual(StringsKt.trim((CharSequence) GxPage.str$default(this, s, "lastMode", null, 2, null)).toString(), "factory-reset");
        LinearLayout linearLayout = this.factoryBox;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("factoryBox");
            linearLayout = null;
        }
        if (fr) {
            jSONObject = s.toString();
            Intrinsics.checkNotNullExpressionValue(jSONObject, "toString(...)");
        } else {
            jSONObject = "none";
        }
        fill(linearLayout, jSONObject, new Function1() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda45
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit paintFactory$lambda$92;
                paintFactory$lambda$92 = GxSystemPage.paintFactory$lambda$92(fr, s, this, (LinearLayout) obj);
                return paintFactory$lambda$92;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paintFactory$lambda$92(boolean $fr, JSONObject $s, GxSystemPage this$0, LinearLayout b) {
        String str;
        int i;
        int i2;
        Intrinsics.checkNotNullParameter(b, "b");
        if (!$fr || $s == null) {
            return Unit.INSTANCE;
        }
        this$0.header("Factory Reset Status", b);
        LinearLayout c = this$0.card(b);
        int it = $s.optInt("progressTotalSteps", 0);
        if (it == 0) {
            it = 5;
        }
        int steps = it;
        String lbl = GxPage.str$default(this$0, $s, "progressLabel", null, 2, null);
        if ($s.optBoolean("running")) {
            str = "Running";
        } else {
            String str$default = GxPage.str$default(this$0, $s, "stage", null, 2, null);
            if (StringsKt.isBlank(str$default)) {
                str$default = "idle";
            }
            str = str$default;
        }
        this$0.text(str + " · " + $s.optInt("progressStep", 0) + "/" + steps + (lbl.length() > 0 ? " · " + lbl : ""), c, 15.0f, CarUi.Companion.getTEXT(), true);
        ProgressBar p = this$0.progress(c);
        this$0.setProgress(p, this$0.pct($s.opt("progressPercent")));
        if (Intrinsics.areEqual(GxPage.str$default(this$0, $s, "stage", null, 2, null), "error")) {
            i2 = GxSystemKt.GxSystem_DANGER;
            p.setProgressTintList(ColorStateList.valueOf(i2));
        }
        if (!StringsKt.isBlank(GxPage.str$default(this$0, $s, "message", null, 2, null))) {
            GxPage.text$default(this$0, GxPage.str$default(this$0, $s, "message", null, 2, null), c, 15.0f, CarUi.Companion.getTEXT(), false, 16, null);
        }
        if (!StringsKt.isBlank(GxPage.str$default(this$0, $s, "progressDetail", null, 2, null))) {
            GxPage.text$default(this$0, GxPage.str$default(this$0, $s, "progressDetail", null, 2, null), c, 0.0f, 0, false, 28, null);
        }
        if (!StringsKt.isBlank(GxPage.str$default(this$0, $s, "lastError", null, 2, null))) {
            String str$default2 = GxPage.str$default(this$0, $s, "lastError", null, 2, null);
            i = GxSystemKt.GxSystem_DANGER;
            GxPage.text$default(this$0, "Last Error: " + str$default2, c, 15.0f, i, false, 16, null);
        }
        return Unit.INSTANCE;
    }

    private final void paintTailscale() {
        LinearLayout linearLayout = this.tsBox;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tsBox");
            linearLayout = null;
        }
        fill(linearLayout, this.tsLoaded + "|" + this.tsInstalled + "|" + this.tsBusy, new Function1() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda25
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit paintTailscale$lambda$97;
                paintTailscale$lambda$97 = GxSystemPage.paintTailscale$lambda$97(GxSystemPage.this, (LinearLayout) obj);
                return paintTailscale$lambda$97;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paintTailscale$lambda$97(final GxSystemPage this$0, LinearLayout b) {
        int i;
        Intrinsics.checkNotNullParameter(b, "b");
        if (!this$0.tsLoaded) {
            this$0.noticeCard(b, "Checking Tailscale install status...", null, CarUi.Companion.getDIM());
            return Unit.INSTANCE;
        }
        noticeCard$default(this$0, b, "Not recommended. Using Galaxy Tunnel is the preferred remote connection method.", null, 0, 12, null);
        if (!this$0.tsInstalled) {
            GxPage.Row r = GxPage.row$default(this$0, "Install Tailscale", "Installing opens the Tailscale login page to authenticate this device.", b, 0, 8, null);
            this$0.en(r.button(this$0.tsBusy ? "Installing..." : "Install", true, new Function0() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda49
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit paintTailscale$lambda$97$lambda$93;
                    paintTailscale$lambda$97$lambda$93 = GxSystemPage.paintTailscale$lambda$97$lambda$93(GxSystemPage.this);
                    return paintTailscale$lambda$97$lambda$93;
                }
            }), true ^ this$0.tsBusy);
        } else {
            GxPage.Row r2 = GxPage.row$default(this$0, "Uninstall Tailscale", "Disconnects the device from your tailnet and removes the Tailscale binaries and state.", b, 0, 8, null);
            TextView it = GxPage.Row.button$default(r2, this$0.tsBusy ? "Uninstalling..." : "Uninstall", false, new Function0() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda50
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit paintTailscale$lambda$97$lambda$94;
                    paintTailscale$lambda$97$lambda$94 = GxSystemPage.paintTailscale$lambda$97$lambda$94(GxSystemPage.this);
                    return paintTailscale$lambda$97$lambda$94;
                }
            }, 2, null);
            i = GxSystemKt.GxSystem_DANGER;
            it.setTextColor(i);
            this$0.en(it, true ^ this$0.tsBusy);
        }
        GxPage.Row.button$default(GxPage.row$default(this$0, "Download for your other devices", "tailscale.com/download", b, 0, 8, null), "Open", false, new Function0() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda51
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit paintTailscale$lambda$97$lambda$96;
                paintTailscale$lambda$97$lambda$96 = GxSystemPage.paintTailscale$lambda$97$lambda$96(GxSystemPage.this);
                return paintTailscale$lambda$97$lambda$96;
            }
        }, 2, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paintTailscale$lambda$97$lambda$93(GxSystemPage this$0) {
        this$0.installTailscale();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paintTailscale$lambda$97$lambda$94(GxSystemPage this$0) {
        this$0.uninstallTailscale();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paintTailscale$lambda$97$lambda$96(GxSystemPage this$0) {
        this$0.openUrl("https://tailscale.com/download");
        return Unit.INSTANCE;
    }

    private final void selectTargetBranch(String branch) {
        List list;
        List list2;
        if ((branch.length() == 0) || !this.branches.contains(branch) || this.branchBusy || getBranchSwitchBlocked()) {
            forceAdv();
            return;
        }
        if (Intrinsics.areEqual(branch, this.targetBranch)) {
            list2 = GxSystemKt.GxSystem_CORE;
            this.otherOpen = !list2.contains(branch);
            forceAdv();
        } else {
            this.targetBranch = branch;
            list = GxSystemKt.GxSystem_CORE;
            this.otherOpen = !list.contains(branch);
            resetVersions();
            forceAdv();
        }
    }

    private final void resetVersions() {
        this.versionGen++;
        this.versionMode = "latest";
        this.selectedCommit = "";
        this.versionCommits.clear();
        this.versionHead = "";
        this.versionPage = 0;
        this.versionHasMore = false;
        this.versionLoading = false;
        this.versionError = "";
        this.versionNotice = "";
        this.pickerOpen = false;
        this.expanded.clear();
    }

    private final void loadVersions(boolean more) {
        if ((this.targetBranch.length() == 0) || !this.branches.contains(this.targetBranch) || this.versionLoading || !Intrinsics.areEqual(this.versionMode, "earlier")) {
            return;
        }
        if (more && !this.versionHasMore) {
            return;
        }
        String branch = this.targetBranch;
        int gen = this.versionGen;
        int originalCount = versionChoices().size();
        int budget = (more && Intrinsics.areEqual(branch, "StarPilot")) ? 4 : 1;
        this.versionLoading = true;
        this.versionError = "";
        forceAdv();
        loadVersions$step(this, branch, gen, originalCount, budget, 0, more ? 1 + this.versionPage : 1, more ? this.versionHead : "");
    }

    private static final void loadVersions$finish(int gen, GxSystemPage this$0) {
        if (gen == this$0.versionGen) {
            this$0.versionLoading = false;
            this$0.forceAdv();
        }
    }

    private static final void loadVersions$step(final GxSystemPage this$0, final String branch, final int gen, final int originalCount, final int budget, final int scanned, final int page, final String head) {
        String path = "/api/update/versions?branch=" + this$0.enc(branch) + "&page=" + page + (head.length() > 0 ? "&head=" + head : "");
        this$0.json("GET", path, null, 60L, new Function2() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda39
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit loadVersions$step$lambda$101;
                loadVersions$step$lambda$101 = GxSystemPage.loadVersions$step$lambda$101(gen, this$0, branch, page, head, originalCount, scanned, budget, (String) obj, obj2);
                return loadVersions$step$lambda$101;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x009a, code lost:
    
        if (kotlin.jvm.internal.Intrinsics.areEqual(com.starpilot.starview.GxPage.str$default(r26, r13, "head", null, 2, null), r29) == false) goto L88;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Unit loadVersions$step$lambda$101(int $gen, GxSystemPage this$0, String $branch, int $page, String $head, int $originalCount, int $scanned, int $budget, String e, Object v) {
        Regex regex;
        boolean z;
        Regex regex2;
        if ($gen != this$0.versionGen) {
            return Unit.INSTANCE;
        }
        JSONObject d = v instanceof JSONObject ? (JSONObject) v : null;
        if (e != null) {
            String str = e;
            if (StringsKt.isBlank(str)) {
                str = "Failed to load version history. Try again.";
            }
            this$0.versionError = str;
            loadVersions$finish($gen, this$0);
            return Unit.INSTANCE;
        }
        JSONArray arr = d != null ? d.optJSONArray("commits") : null;
        if (d != null && Intrinsics.areEqual(GxPage.str$default(this$0, d, "branch", null, 2, null), $branch) && d.optInt("page", -1) == $page) {
            regex = GxSystemKt.GxSystem_SHA;
            if (regex.matches(GxPage.str$default(this$0, d, "head", null, 2, null))) {
                if (!($head.length() > 0)) {
                }
                if (arr != null) {
                    Iterable $this$filter$iv = this$0.objects(arr);
                    Collection destination$iv$iv = new ArrayList();
                    for (Object element$iv$iv : $this$filter$iv) {
                        JSONObject it = (JSONObject) element$iv$iv;
                        regex2 = GxSystemKt.GxSystem_SHA;
                        Iterable $this$filter$iv2 = $this$filter$iv;
                        if (regex2.matches(GxPage.str$default(this$0, it, "sha", null, 2, null))) {
                            destination$iv$iv.add(element$iv$iv);
                        }
                        $this$filter$iv = $this$filter$iv2;
                    }
                    List<JSONObject> commits = (List) destination$iv$iv;
                    if ($page > 1) {
                        Iterable $this$map$iv = this$0.versionCommits;
                        Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                        for (Object item$iv$iv : $this$map$iv) {
                            JSONObject it2 = (JSONObject) item$iv$iv;
                            destination$iv$iv2.add(GxPage.str$default(this$0, it2, "sha", null, 2, null));
                        }
                        HashSet have = CollectionsKt.toHashSet((List) destination$iv$iv2);
                        for (JSONObject cm : commits) {
                            if (have.add(GxPage.str$default(this$0, cm, "sha", null, 2, null))) {
                                this$0.versionCommits.add(cm);
                            }
                        }
                    } else {
                        this$0.versionCommits.clear();
                        this$0.versionCommits.addAll(commits);
                    }
                    if (d.optBoolean("cached")) {
                        Instant saved = this$0.parseDate(GxPage.str$default(this$0, d, "cachedAt", null, 2, null));
                        this$0.versionNotice = "Showing saved history" + (saved != null ? " from " + this$0.stampFmt.format(saved) : "") + ". Installation still needs an online check.";
                        z = true;
                    } else {
                        z = true;
                        if ($page == 1) {
                            this$0.versionNotice = "";
                        }
                    }
                    this$0.versionHead = GxPage.str$default(this$0, d, "head", null, 2, null);
                    this$0.versionPage = $page;
                    this$0.versionHasMore = (!d.optBoolean("hasMore") || commits.isEmpty()) ? false : z;
                    if (this$0.versionCommits.isEmpty()) {
                        this$0.versionError = "No versions are available for this branch. Choose Latest or another branch.";
                    }
                    if (this$0.versionHasMore && this$0.versionChoices().size() <= $originalCount && $scanned + 1 < $budget) {
                        loadVersions$step(this$0, $branch, $gen, $originalCount, $budget, $scanned + 1, $page + 1, this$0.versionHead);
                    } else {
                        loadVersions$finish($gen, this$0);
                    }
                    return Unit.INSTANCE;
                }
            }
        }
        this$0.versionError = "Version history changed. Choose Latest and try again.";
        loadVersions$finish($gen, this$0);
        return Unit.INSTANCE;
    }

    private final void returnToLatest() {
        JSONObject optJSONObject;
        String branch;
        List list;
        JSONObject jSONObject = this.fastStatus;
        if (jSONObject == null || (optJSONObject = jSONObject.optJSONObject("versionPin")) == null || (branch = GxPage.str$default(this, optJSONObject, "branch", null, 2, null)) == null || getBranchSwitchBlocked() || this.branchBusy || !this.branches.contains(branch)) {
            return;
        }
        if (!Intrinsics.areEqual(branch, this.targetBranch)) {
            this.targetBranch = branch;
            list = GxSystemKt.GxSystem_CORE;
            this.otherOpen = !list.contains(branch);
        }
        resetVersions();
        forceAdv();
        installSelectedVersion();
    }

    private final void installSelectedVersion() {
        JSONObject jSONObject;
        Object element$iv;
        JSONObject jSONObject2;
        String str;
        Collection destination$iv$iv;
        Iterable strings;
        if (getInstallVersionBlocked()) {
            return;
        }
        final String branch = this.targetBranch;
        final String commit = Intrinsics.areEqual(this.versionMode, "latest") ? "latest" : this.selectedCommit;
        final int gen = this.versionGen;
        Iterable $this$firstOrNull$iv = this.versionCommits;
        Iterator it = $this$firstOrNull$iv.iterator();
        while (true) {
            jSONObject = null;
            if (it.hasNext()) {
                element$iv = it.next();
                JSONObject it2 = (JSONObject) element$iv;
                if (Intrinsics.areEqual(GxPage.str$default(this, it2, "sha", null, 2, null), commit)) {
                    break;
                }
            } else {
                element$iv = null;
                break;
            }
        }
        JSONObject selected = (JSONObject) element$iv;
        final String version = Intrinsics.areEqual(commit, "latest") ? "Latest" : versionTitle(selected, Intrinsics.areEqual(branch, "StarPilot")) + "\nCommit: " + commit;
        String policy = Intrinsics.areEqual(commit, "latest") ? "This uses the normal branch updater, including any required OS update during startup. Your automatic-update setting is unchanged." : "Automatic updates will be paused for this earlier version.";
        String recovery = Intrinsics.areEqual(commit, "latest") ? "This replaces the current software. Settings and statistics are kept. Local code changes may be overwritten; standard Rollback saves the previous committed version." : "This replaces the current software. Settings and statistics are kept, and local code changes are backed up. Older versions may remove this picker; an SSH recovery copy is saved on the device.";
        if (Intrinsics.areEqual(commit, "latest") && !Intrinsics.areEqual(branch, this.currentBranch)) {
            jSONObject = this.agnosByBranch.get(branch);
        } else if (Intrinsics.areEqual(commit, "latest") && (jSONObject2 = this.fastStatus) != null) {
            jSONObject = jSONObject2.optJSONObject("agnosUpdate");
        }
        JSONObject ag = jSONObject;
        boolean z = false;
        if (ag != null && ag.optBoolean("available")) {
            z = true;
        }
        if (z) {
            JSONArray optJSONArray = ag.optJSONArray("warnings");
            if (optJSONArray == null || (strings = strings(optJSONArray)) == null) {
                destination$iv$iv = CollectionsKt.emptyList();
            } else {
                Iterable $this$filter$iv = strings;
                Collection destination$iv$iv2 = new ArrayList();
                for (Object element$iv$iv : $this$filter$iv) {
                    String it3 = (String) element$iv$iv;
                    if (!StringsKt.isBlank(it3)) {
                        destination$iv$iv2.add(element$iv$iv);
                    }
                }
                destination$iv$iv = (List) destination$iv$iv2;
            }
            str = "\n\nAGNOS firmware update included:\n\n" + CollectionsKt.joinToString$default(destination$iv$iv, "\n", null, null, 0, null, new Function1() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda63
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    CharSequence installSelectedVersion$lambda$104;
                    installSelectedVersion$lambda$104 = GxSystemPage.installSelectedVersion$lambda$104((String) obj);
                    return installSelectedVersion$lambda$104;
                }
            }, 30, null);
        } else {
            str = "";
        }
        String agnosText = str;
        confirm("Install selected version?", "Branch: " + branch + "\nVersion: " + version + "\n\n" + policy + "\n\n" + recovery + agnosText + "\n\nYour device will reboot when installation finishes.", "Install & Reboot", new Function0() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda64
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit installSelectedVersion$lambda$109;
                installSelectedVersion$lambda$109 = GxSystemPage.installSelectedVersion$lambda$109(GxSystemPage.this, branch, gen, commit, version);
                return installSelectedVersion$lambda$109;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CharSequence installSelectedVersion$lambda$104(String it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return "- " + it;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit installSelectedVersion$lambda$109(final GxSystemPage this$0, final String $branch, final int $gen, final String $commit, final String $version) {
        if (this$0.branchBusy) {
            return Unit.INSTANCE;
        }
        this$0.branchBusy = true;
        this$0.paint();
        this$0.loadFastStatus(new Function1() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda41
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit installSelectedVersion$lambda$109$lambda$108;
                installSelectedVersion$lambda$109$lambda$108 = GxSystemPage.installSelectedVersion$lambda$109$lambda$108(GxSystemPage.this, $branch, $gen, $commit, $version, (String) obj);
                return installSelectedVersion$lambda$109$lambda$108;
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit installSelectedVersion$lambda$109$lambda$108(final GxSystemPage this$0, final String $branch, int $gen, String $commit, final String $version, String err) {
        if (err != null) {
            this$0.toast(err);
            this$0.branchBusy = false;
            this$0.paint();
            return Unit.INSTANCE;
        }
        if (!this$0.getBranchSwitchBlocked() && Intrinsics.areEqual(this$0.targetBranch, $branch)) {
            if ($gen == this$0.versionGen) {
                GxPage.send$default(this$0, "POST", "/api/update/version", new JSONObject().put("branch", $branch).put("commit", $commit).put("confirmed", true), 0L, new Function2() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda32
                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        Unit installSelectedVersion$lambda$109$lambda$108$lambda$107;
                        installSelectedVersion$lambda$109$lambda$108$lambda$107 = GxSystemPage.installSelectedVersion$lambda$109$lambda$108$lambda$107(GxSystemPage.this, $version, $branch, (String) obj, (JSONObject) obj2);
                        return installSelectedVersion$lambda$109$lambda$108$lambda$107;
                    }
                }, 8, null);
                return Unit.INSTANCE;
            }
        }
        this$0.toast("Installation is unavailable while driving or updating, or the selection has changed.");
        this$0.branchBusy = false;
        this$0.paint();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x003e, code lost:
    
        if (r1 != null) goto L20;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Unit installSelectedVersion$lambda$109$lambda$108$lambda$107(GxSystemPage this$0, String $version, String $branch, String e, JSONObject o) {
        String str;
        String str$default;
        this$0.branchBusy = false;
        if (e != null) {
            String str2 = e;
            if (StringsKt.isBlank(str2)) {
                str2 = "Installation failed.";
            }
            this$0.toast(str2);
            this$0.paint();
            return Unit.INSTANCE;
        }
        this$0.markRebootPending();
        if (o != null && (str$default = GxPage.str$default(this$0, o, "message", null, 2, null)) != null) {
            String str3 = str$default;
            if (StringsKt.isBlank(str3)) {
                str3 = null;
            }
            str = str3;
        }
        str = "Installing " + StringsKt.substringBefore$default($version, "\n", (String) null, 2, (Object) null) + " on " + $branch + "...";
        this$0.toast(str);
        this$0.paint();
        loadFastStatus$default(this$0, null, 1, null);
        return Unit.INSTANCE;
    }

    private final void checkUpdates() {
        if ((this.busy.length() > 0) || getUpdateInProgress()) {
            return;
        }
        this.busy = "check";
        paint();
        loadFastStatus(new Function1() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda38
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit checkUpdates$lambda$112;
                checkUpdates$lambda$112 = GxSystemPage.checkUpdates$lambda$112(GxSystemPage.this, (String) obj);
                return checkUpdates$lambda$112;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0069, code lost:
    
        if (r0 != null) goto L29;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Unit checkUpdates$lambda$112(GxSystemPage this$0, String err) {
        String str;
        String str$default;
        this$0.busy = "";
        if (err != null) {
            this$0.toast("Failed to check for updates.");
        } else {
            boolean z = true;
            this$0.checkedForUpdates = true;
            JSONObject st = this$0.fastStatus;
            if (this$0.getUpdateInProgress()) {
                this$0.toast("An update is already running.");
            } else {
                if (st == null || !st.optBoolean("updateAvailable")) {
                    z = false;
                }
                if (z) {
                    String str$default2 = GxPage.str$default(this$0, st, "message", null, 2, null);
                    if (StringsKt.isBlank(str$default2)) {
                        str$default2 = "Update available.";
                    }
                    this$0.toast(str$default2);
                } else {
                    if (st != null && (str$default = GxPage.str$default(this$0, st, "message", null, 2, null)) != null) {
                        String str2 = str$default;
                        if (StringsKt.isBlank(str2)) {
                            str2 = null;
                        }
                        str = str2;
                    }
                    str = "No update available — you're up to date.";
                    this$0.toast(str);
                }
            }
        }
        this$0.paint();
        return Unit.INSTANCE;
    }

    private final void setAutomaticUpdates(final boolean enabled) {
        JSONObject st = this.fastStatus;
        if (this.autoBusy || this.isOnroad || getUpdateInProgress() || st == null) {
            paintControls();
            return;
        }
        GalaxyApi a = getApi();
        if (a == null) {
            GxSystemPage $this$setAutomaticUpdates_u24lambda_u24113 = this;
            $this$setAutomaticUpdates_u24lambda_u24113.toast("Not connected");
            $this$setAutomaticUpdates_u24lambda_u24113.paintControls();
            return;
        }
        final boolean previous = st.optBoolean("automaticUpdates");
        this.autoBusy = true;
        st.put("automaticUpdates", enabled);
        LinearLayout linearLayout = this.statusBox;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("statusBox");
            linearLayout = null;
        }
        linearLayout.setTag(null);
        paint();
        a.put("AutomaticUpdates", Boolean.valueOf(enabled), MapsKt.mapOf(TuplesKt.to("label", "Automatic Updates")), new Function1() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda27
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit automaticUpdates$lambda$115;
                automaticUpdates$lambda$115 = GxSystemPage.setAutomaticUpdates$lambda$115(GxSystemPage.this, previous, enabled, (String) obj);
                return automaticUpdates$lambda$115;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit setAutomaticUpdates$lambda$115(GxSystemPage this$0, boolean $previous, boolean $enabled, String e) {
        this$0.autoBusy = false;
        if (e != null) {
            JSONObject jSONObject = this$0.fastStatus;
            if (jSONObject != null) {
                jSONObject.put("automaticUpdates", $previous);
            }
            String str = e;
            if (StringsKt.isBlank(str)) {
                str = "Failed to update Automatic Updates.";
            }
            this$0.toast(str);
        } else {
            this$0.toast("Automatic updates " + ($enabled ? "enabled" : "disabled") + ".");
        }
        LinearLayout linearLayout = this$0.statusBox;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("statusBox");
            linearLayout = null;
        }
        linearLayout.setTag(null);
        this$0.paint();
        return Unit.INSTANCE;
    }

    private final void runUpdate(final String action) {
        JSONObject st;
        if ((this.busy.length() > 0) || getUpdateInProgress() || this.isOnroad) {
            return;
        }
        if (Intrinsics.areEqual(action, "rollback") && (st = this.fastStatus) != null && !st.optBoolean("rollbackAvailable")) {
            toast("No previous installed version is available to roll back to.");
        } else {
            String title = Intrinsics.areEqual(action, "recover") ? "Recover the interrupted update?" : "Roll back to the previous installed version?";
            confirm(title, "Your device will reboot when the operation is done.", "Continue", new Function0() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda37
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit runUpdate$lambda$119;
                    runUpdate$lambda$119 = GxSystemPage.runUpdate$lambda$119(GxSystemPage.this, action);
                    return runUpdate$lambda$119;
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit runUpdate$lambda$119(final GxSystemPage this$0, String $action) {
        if ((this$0.busy.length() > 0) || this$0.getUpdateInProgress()) {
            return Unit.INSTANCE;
        }
        this$0.busy = $action;
        this$0.paint();
        GxPage.send$default(this$0, "POST", Intrinsics.areEqual($action, "recover") ? "/api/update/recover" : "/api/update/rollback", null, 0L, new Function2() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda26
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit runUpdate$lambda$119$lambda$118;
                runUpdate$lambda$119$lambda$118 = GxSystemPage.runUpdate$lambda$119$lambda$118(GxSystemPage.this, (String) obj, (JSONObject) obj2);
                return runUpdate$lambda$119$lambda$118;
            }
        }, 12, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x003f, code lost:
    
        if (r1 != null) goto L20;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Unit runUpdate$lambda$119$lambda$118(GxSystemPage this$0, String e, JSONObject o) {
        String str;
        String str$default;
        this$0.busy = "";
        if (e != null) {
            String str2 = e;
            if (StringsKt.isBlank(str2)) {
                str2 = "Update failed.";
            }
            this$0.toast(str2);
            this$0.paint();
            return Unit.INSTANCE;
        }
        this$0.markRebootPending();
        if (o != null && (str$default = GxPage.str$default(this$0, o, "message", null, 2, null)) != null) {
            String str3 = str$default;
            if (StringsKt.isBlank(str3)) {
                str3 = null;
            }
            str = str3;
        }
        str = "Update started.";
        this$0.toast(str);
        this$0.paint();
        loadFastStatus$default(this$0, null, 1, null);
        return Unit.INSTANCE;
    }

    private final void factoryReset() {
        confirm("Factory reset (SAVE ME)?", "This wipes params, backups, themes, models, maps, and route data, then reboots. This cannot be undone.", "Factory Reset", new Function0() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda21
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit factoryReset$lambda$122;
                factoryReset$lambda$122 = GxSystemPage.factoryReset$lambda$122(GxSystemPage.this);
                return factoryReset$lambda$122;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit factoryReset$lambda$122(final GxSystemPage this$0) {
        GxPage.send$default(this$0, "POST", "/api/update/factory_reset", null, 0L, new Function2() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda28
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit factoryReset$lambda$122$lambda$121;
                factoryReset$lambda$122$lambda$121 = GxSystemPage.factoryReset$lambda$122$lambda$121(GxSystemPage.this, (String) obj, (JSONObject) obj2);
                return factoryReset$lambda$122$lambda$121;
            }
        }, 12, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit factoryReset$lambda$122$lambda$121(GxSystemPage this$0, String e, JSONObject jSONObject) {
        if (e != null) {
            String str = e;
            if (StringsKt.isBlank(str)) {
                str = "Factory reset failed.";
            }
            this$0.toast(str);
            return Unit.INSTANCE;
        }
        this$0.markRebootPending();
        this$0.toast("SAVE ME initiated — factory resetting...");
        this$0.paint();
        loadFastStatus$default(this$0, null, 1, null);
        return Unit.INSTANCE;
    }

    private final void saveProfile(JSONObject p) {
        if ((this.profileBusy.length() > 0) || this.isOnroad) {
            return;
        }
        final String slot = GxPage.str$default(this, p, "slot", null, 2, null);
        String str$default = GxPage.str$default(this, p, "label", null, 2, null);
        if (StringsKt.isBlank(str$default)) {
            str$default = slot;
        }
        final String label = str$default;
        final Function0 go = new Function0() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda53
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit saveProfile$lambda$127;
                saveProfile$lambda$127 = GxSystemPage.saveProfile$lambda$127(GxSystemPage.this, slot, label);
                return saveProfile$lambda$127;
            }
        };
        if (p.optBoolean("saved")) {
            confirm("Overwrite " + label + "?", "This replaces the settings currently stored in this slot.", "Overwrite", new Function0() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda54
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit saveProfile$lambda$128;
                    saveProfile$lambda$128 = GxSystemPage.saveProfile$lambda$128(Function0.this);
                    return saveProfile$lambda$128;
                }
            });
        } else {
            go.invoke();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit saveProfile$lambda$127(final GxSystemPage this$0, String $slot, final String $label) {
        this$0.profileBusy = "save-" + $slot;
        this$0.paint();
        GxPage.send$default(this$0, "POST", "/api/toggles/profiles/" + this$0.enc($slot) + "/save", null, 0L, new Function2() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda29
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit saveProfile$lambda$127$lambda$126;
                saveProfile$lambda$127$lambda$126 = GxSystemPage.saveProfile$lambda$127$lambda$126(GxSystemPage.this, $label, (String) obj, (JSONObject) obj2);
                return saveProfile$lambda$127$lambda$126;
            }
        }, 12, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0034, code lost:
    
        if (r0 != null) goto L18;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Unit saveProfile$lambda$127$lambda$126(GxSystemPage this$0, String $label, String e, JSONObject o) {
        String str;
        String str$default;
        this$0.profileBusy = "";
        if (e != null) {
            String str2 = e;
            if (StringsKt.isBlank(str2)) {
                str2 = "Failed to save settings profile.";
            }
            str = str2;
        } else {
            if (o != null && (str$default = GxPage.str$default(this$0, o, "message", null, 2, null)) != null) {
                String str3 = str$default;
                if (StringsKt.isBlank(str3)) {
                    str3 = null;
                }
                str = str3;
            }
            str = "Saved " + $label + ".";
        }
        this$0.toast(str);
        this$0.paint();
        this$0.loadProfiles();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit saveProfile$lambda$128(Function0 $go) {
        $go.invoke();
        return Unit.INSTANCE;
    }

    private final void loadProfile(JSONObject p) {
        if ((this.profileBusy.length() > 0) || this.isOnroad || !p.optBoolean("saved") || p.optBoolean("invalid")) {
            return;
        }
        final String slot = GxPage.str$default(this, p, "slot", null, 2, null);
        String str$default = GxPage.str$default(this, p, "label", null, 2, null);
        if (StringsKt.isBlank(str$default)) {
            str$default = slot;
        }
        final String label = str$default;
        confirm("Load " + label + "?", "This applies every saved setting in the slot to the device.", "Load Settings", new Function0() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda34
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit loadProfile$lambda$133;
                loadProfile$lambda$133 = GxSystemPage.loadProfile$lambda$133(GxSystemPage.this, slot, label);
                return loadProfile$lambda$133;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit loadProfile$lambda$133(final GxSystemPage this$0, String $slot, final String $label) {
        this$0.profileBusy = "load-" + $slot;
        this$0.paint();
        GxPage.send$default(this$0, "POST", "/api/toggles/profiles/" + this$0.enc($slot) + "/load", null, 0L, new Function2() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda52
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit loadProfile$lambda$133$lambda$132;
                loadProfile$lambda$133$lambda$132 = GxSystemPage.loadProfile$lambda$133$lambda$132(GxSystemPage.this, $label, (String) obj, (JSONObject) obj2);
                return loadProfile$lambda$133$lambda$132;
            }
        }, 12, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0037, code lost:
    
        if (r1 != null) goto L19;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Unit loadProfile$lambda$133$lambda$132(GxSystemPage this$0, String $label, String e, JSONObject o) {
        String str;
        String str$default;
        this$0.profileBusy = "";
        if (e != null) {
            String str2 = e;
            if (StringsKt.isBlank(str2)) {
                str2 = "Failed to load settings profile.";
            }
            this$0.toast(str2);
        } else {
            if (o != null && (str$default = GxPage.str$default(this$0, o, "message", null, 2, null)) != null) {
                String str3 = str$default;
                if (StringsKt.isBlank(str3)) {
                    str3 = null;
                }
                str = str3;
            }
            str = "Loaded " + $label + ".";
            this$0.toast(str);
            GalaxyApi api = this$0.getApi();
            if (api != null) {
                GalaxyApi.refreshValues$default(api, null, 1, null);
            }
        }
        this$0.paint();
        return Unit.INSTANCE;
    }

    private final void backupToggles() {
        if (getHost() == null) {
            toast("Not connected");
            return;
        }
        final String target = url("/api/toggles/backup");
        final String fileName = "toggle-backup.json";
        new Thread(new Runnable() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda70
            @Override // java.lang.Runnable
            public final void run() {
                GxSystemPage.backupToggles$lambda$140(target, fileName, this);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x008f, code lost:
    
        if (r6 == null) goto L27;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v10, types: [T, java.lang.String] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void backupToggles$lambda$140(String str, String str2, final GxSystemPage gxSystemPage) {
        Response execute;
        Response response;
        byte[] bArr;
        String str3;
        Object m1191constructorimpl;
        String str4;
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        try {
            execute = GxPage.Companion.client(60L).newCall(new Request.Builder().url(str).post(RequestBody.Companion.create$default(RequestBody.Companion, new byte[0], (MediaType) null, 0, 0, 6, (Object) null)).build()).execute();
            try {
                response = execute;
                ResponseBody body = response.body();
                if (body == null || (bArr = body.bytes()) == null) {
                    bArr = new byte[0];
                }
                str3 = null;
            } finally {
            }
        } catch (Exception e) {
            String message = e.getMessage();
            T t = message;
            if (message == null) {
                t = "Backup failed.";
            }
            objectRef.element = t;
        }
        if (!response.isSuccessful()) {
            try {
                Result.Companion companion = Result.Companion;
                m1191constructorimpl = Result.m1191constructorimpl(new JSONObject(new String(bArr, Charsets.UTF_8)).optString("message"));
            } catch (Throwable th) {
                Result.Companion companion2 = Result.Companion;
                m1191constructorimpl = Result.m1191constructorimpl(ResultKt.createFailure(th));
            }
            if (Result.m1197isFailureimpl(m1191constructorimpl)) {
                m1191constructorimpl = null;
            }
            String str5 = (String) m1191constructorimpl;
            if (str5 != null) {
                String str6 = str5;
                if (!StringsKt.isBlank(str6)) {
                    str3 = str6;
                }
                str4 = str3;
            }
            str4 = "Failed to create toggle backup.";
            throw new IllegalStateException(str4);
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("_display_name", str2);
        contentValues.put("mime_type", "application/json");
        contentValues.put("relative_path", "Download/StarView");
        Uri insert = gxSystemPage.getAct().getContentResolver().insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues);
        if (insert == null) {
            throw new IllegalStateException("cannot create file");
        }
        OutputStream openOutputStream = gxSystemPage.getAct().getContentResolver().openOutputStream(insert);
        if (openOutputStream == null) {
            throw new IllegalStateException("cannot write file");
        }
        OutputStream outputStream = openOutputStream;
        try {
            outputStream.write(bArr);
            Unit unit = Unit.INSTANCE;
            CloseableKt.closeFinally(outputStream, null);
            Unit unit2 = Unit.INSTANCE;
            CloseableKt.closeFinally(execute, null);
            objectRef.element = "Toggle backup downloaded to Downloads/StarView/" + str2;
            gxSystemPage.getMain().post(new Runnable() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda30
                @Override // java.lang.Runnable
                public final void run() {
                    GxSystemPage.backupToggles$lambda$140$lambda$139(GxSystemPage.this, objectRef);
                }
            });
        } finally {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static final void backupToggles$lambda$140$lambda$139(GxSystemPage this$0, Ref.ObjectRef $msg) {
        this$0.toast((String) $msg.element);
    }

    private final void restoreToggles() {
        getGx().pickFile(new String[]{"application/json", "text/plain", "application/octet-stream", "*/*"}, new Function1() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda42
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit restoreToggles$lambda$146;
                restoreToggles$lambda$146 = GxSystemPage.restoreToggles$lambda$146(GxSystemPage.this, (Uri) obj);
                return restoreToggles$lambda$146;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit restoreToggles$lambda$146(final GxSystemPage this$0, Uri uri) {
        if (uri == null) {
            return Unit.INSTANCE;
        }
        this$0.readUri(uri, new Function2() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda31
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit restoreToggles$lambda$146$lambda$145;
                restoreToggles$lambda$146$lambda$145 = GxSystemPage.restoreToggles$lambda$146$lambda$145(GxSystemPage.this, (byte[]) obj, (String) obj2);
                return restoreToggles$lambda$146$lambda$145;
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit restoreToggles$lambda$146$lambda$145(final GxSystemPage this$0, byte[] bytes, String str) {
        Object data;
        Intrinsics.checkNotNullParameter(str, "<unused var>");
        if (bytes == null) {
            this$0.toast("That file is not a valid toggle backup.");
            return Unit.INSTANCE;
        }
        if (bytes.length > 5000000) {
            this$0.toast("That toggle backup file is too large.");
            return Unit.INSTANCE;
        }
        try {
            Result.Companion companion = Result.Companion;
            data = Result.m1191constructorimpl(new JSONTokener(new String(bytes, Charsets.UTF_8)).nextValue());
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            data = Result.m1191constructorimpl(ResultKt.createFailure(th));
        }
        if (Result.m1197isFailureimpl(data)) {
            data = null;
        }
        if (!(data instanceof JSONObject)) {
            this$0.toast("That file is not a valid toggle backup.");
            return Unit.INSTANCE;
        }
        this$0.send("POST", "/api/toggles/restore", (JSONObject) data, 60L, new Function2() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda24
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit restoreToggles$lambda$146$lambda$145$lambda$144;
                restoreToggles$lambda$146$lambda$145$lambda$144 = GxSystemPage.restoreToggles$lambda$146$lambda$145$lambda$144(GxSystemPage.this, (String) obj, (JSONObject) obj2);
                return restoreToggles$lambda$146$lambda$145$lambda$144;
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0033, code lost:
    
        if (r1 != null) goto L18;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Unit restoreToggles$lambda$146$lambda$145$lambda$144(GxSystemPage this$0, String e, JSONObject o) {
        String str;
        String str$default;
        if (e != null) {
            String str2 = e;
            if (StringsKt.isBlank(str2)) {
                str2 = "Failed to restore toggles.";
            }
            this$0.toast(str2);
        } else {
            if (o != null && (str$default = GxPage.str$default(this$0, o, "message", null, 2, null)) != null) {
                String str3 = str$default;
                if (StringsKt.isBlank(str3)) {
                    str3 = null;
                }
                str = str3;
            }
            str = "Toggles restored!";
            this$0.toast(str);
            GalaxyApi api = this$0.getApi();
            if (api != null) {
                GalaxyApi.refreshValues$default(api, null, 1, null);
            }
        }
        return Unit.INSTANCE;
    }

    private final void resetDefault() {
        confirm("Reset toggles to default?", "This resets all toggles to their default values and reboots.", "Reset", new Function0() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda33
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit resetDefault$lambda$148;
                resetDefault$lambda$148 = GxSystemPage.resetDefault$lambda$148(GxSystemPage.this);
                return resetDefault$lambda$148;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit resetDefault$lambda$148(final GxSystemPage this$0) {
        GxPage.send$default(this$0, "POST", "/api/toggles/reset_default", null, 0L, new Function2() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda43
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit resetDefault$lambda$148$lambda$147;
                resetDefault$lambda$148$lambda$147 = GxSystemPage.resetDefault$lambda$148$lambda$147(GxSystemPage.this, (String) obj, (JSONObject) obj2);
                return resetDefault$lambda$148$lambda$147;
            }
        }, 12, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit resetDefault$lambda$148$lambda$147(GxSystemPage this$0, String e, JSONObject jSONObject) {
        if (e != null) {
            this$0.toast("Reset failed.");
            return Unit.INSTANCE;
        }
        this$0.markRebootPending();
        this$0.toast("Resetting toggles to default... rebooting.");
        this$0.paint();
        return Unit.INSTANCE;
    }

    private final void deleteAllRoutes() {
        confirm("Delete All Driving Routes", "This permanently deletes all local routes from standard, high-resolution, and alternate footage storage. It does not reset settings or reboot the device.", "Delete Routes", new Function0() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda68
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit deleteAllRoutes$lambda$152;
                deleteAllRoutes$lambda$152 = GxSystemPage.deleteAllRoutes$lambda$152(GxSystemPage.this);
                return deleteAllRoutes$lambda$152;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit deleteAllRoutes$lambda$152(final GxSystemPage this$0) {
        this$0.send("DELETE", "/api/routes/delete_all?include_preserved=true", null, 120L, new Function2() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda23
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit deleteAllRoutes$lambda$152$lambda$151;
                deleteAllRoutes$lambda$152$lambda$151 = GxSystemPage.deleteAllRoutes$lambda$152$lambda$151(GxSystemPage.this, (String) obj, (JSONObject) obj2);
                return deleteAllRoutes$lambda$152$lambda$151;
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0030, code lost:
    
        if (r0 != null) goto L17;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Unit deleteAllRoutes$lambda$152$lambda$151(GxSystemPage this$0, String e, JSONObject o) {
        String str;
        String str$default;
        if (e != null) {
            String str2 = e;
            if (StringsKt.isBlank(str2)) {
                str2 = "Failed to delete driving routes.";
            }
            str = str2;
        } else {
            if (o != null && (str$default = GxPage.str$default(this$0, o, "message", null, 2, null)) != null) {
                String str3 = str$default;
                if (StringsKt.isBlank(str3)) {
                    str3 = null;
                }
                str = str3;
            }
            str = "All local driving routes deleted.";
        }
        this$0.toast(str);
        return Unit.INSTANCE;
    }

    private final void installTailscale() {
        if (this.tsBusy) {
            return;
        }
        this.tsBusy = true;
        paint();
        toast("Install started...");
        send("POST", "/api/tailscale/setup", null, 300L, new Function2() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda36
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit installTailscale$lambda$158;
                installTailscale$lambda$158 = GxSystemPage.installTailscale$lambda$158(GxSystemPage.this, (String) obj, (JSONObject) obj2);
                return installTailscale$lambda$158;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0033, code lost:
    
        if (r1 != null) goto L18;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Unit installTailscale$lambda$158(final GxSystemPage this$0, String e, JSONObject o) {
        String str;
        String str$default;
        String str$default2;
        if (e != null) {
            String str2 = e;
            if (StringsKt.isBlank(str2)) {
                str2 = "Failed to install Tailscale.";
            }
            this$0.toast(str2);
        } else {
            String str3 = null;
            if (o != null && (str$default2 = GxPage.str$default(this$0, o, "message", null, 2, null)) != null) {
                String str4 = str$default2;
                if (StringsKt.isBlank(str4)) {
                    str4 = null;
                }
                str = str4;
            }
            str = "Tailscale setup started.";
            this$0.toast(str);
            if (o != null && (str$default = GxPage.str$default(this$0, o, "auth_url", null, 2, null)) != null) {
                String str5 = str$default;
                if (!StringsKt.isBlank(str5)) {
                    str3 = str5;
                }
                String it = str3;
                if (it != null) {
                    this$0.openUrl(it);
                }
            }
        }
        this$0.loadTailscale(new Function0() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda77
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit installTailscale$lambda$158$lambda$157;
                installTailscale$lambda$158$lambda$157 = GxSystemPage.installTailscale$lambda$158$lambda$157(GxSystemPage.this);
                return installTailscale$lambda$158$lambda$157;
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit installTailscale$lambda$158$lambda$157(GxSystemPage this$0) {
        this$0.tsBusy = false;
        this$0.paint();
        return Unit.INSTANCE;
    }

    private final void uninstallTailscale() {
        if (this.tsBusy) {
            return;
        }
        confirm("Uninstall Tailscale?", "This disconnects the device from your tailnet and removes the Tailscale binaries and state.", "Uninstall", new Function0() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda58
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit uninstallTailscale$lambda$163;
                uninstallTailscale$lambda$163 = GxSystemPage.uninstallTailscale$lambda$163(GxSystemPage.this);
                return uninstallTailscale$lambda$163;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit uninstallTailscale$lambda$163(final GxSystemPage this$0) {
        this$0.tsBusy = true;
        this$0.paint();
        this$0.toast("Uninstall started...");
        this$0.send("POST", "/api/tailscale/uninstall", null, 120L, new Function2() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit uninstallTailscale$lambda$163$lambda$162;
                uninstallTailscale$lambda$163$lambda$162 = GxSystemPage.uninstallTailscale$lambda$163$lambda$162(GxSystemPage.this, (String) obj, (JSONObject) obj2);
                return uninstallTailscale$lambda$163$lambda$162;
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0030, code lost:
    
        if (r0 != null) goto L17;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Unit uninstallTailscale$lambda$163$lambda$162(final GxSystemPage this$0, String e, JSONObject o) {
        String str;
        String str$default;
        if (e != null) {
            String str2 = e;
            if (StringsKt.isBlank(str2)) {
                str2 = "Failed to uninstall Tailscale.";
            }
            str = str2;
        } else {
            if (o != null && (str$default = GxPage.str$default(this$0, o, "message", null, 2, null)) != null) {
                String str3 = str$default;
                if (StringsKt.isBlank(str3)) {
                    str3 = null;
                }
                str = str3;
            }
            str = "Tailscale uninstalled.";
        }
        this$0.toast(str);
        this$0.loadTailscale(new Function0() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda62
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit uninstallTailscale$lambda$163$lambda$162$lambda$161;
                uninstallTailscale$lambda$163$lambda$162$lambda$161 = GxSystemPage.uninstallTailscale$lambda$163$lambda$162$lambda$161(GxSystemPage.this);
                return uninstallTailscale$lambda$163$lambda$162$lambda$161;
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit uninstallTailscale$lambda$163$lambda$162$lambda$161(GxSystemPage this$0) {
        this$0.tsBusy = false;
        this$0.paint();
        return Unit.INSTANCE;
    }
}
