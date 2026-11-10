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
import kotlin.jvm.internal.Ref.ObjectRef;

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

    /* VF: from Vineflower (jadx: ) */
    public GxSystemPage(Activity var1, GxHost var2) {
        super(var1, var2);
        Intrinsics.checkNotNullParameter(var1, "act");
        Intrinsics.checkNotNullParameter(var2, "gx");
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

    /* VF: from Vineflower (jadx: Removed duplicated region;incorrect) */
    private final double pct(Object var1) {
        boolean var6 = var1 instanceof Number;
        Object var8 = null;
        Number var7;
        if (var6) {
            var7 = (Number)var1;
        } else {
            var7 = null;
        }

        double var4 = 0.0;
        double var2;
        if (var7 != null) {
            var2 = var7.doubleValue();
        } else {
            Double var11 = (Double)var8;
            if (var1 != null) {
                var1 = var1.toString();
                var11 = (Double)var8;
                if (var1 != null) {
                    var11 = StringsKt.toDoubleOrNull(((String) var1));
                }
            }

            if (var11 != null) {
                var2 = var11;
            } else {
                var2 = 0.0;
            }
        }

        if (Double.isNaN(var2)) {
            var2 = var4;
        } else {
            var2 = RangesKt.coerceIn(var2, 0.0, 100.0);
        }

        return var2;
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
        CarUi.Companion_ companion = CarUi.Companion;
        $this$kv_u24lambda_u2420.setTextColor(onClick != null ? companion.getACCENT() : companion.getTEXT());
        $this$kv_u24lambda_u2420.setTypeface(mono ? Typeface.MONOSPACE : Typeface.DEFAULT_BOLD);
        if (onClick != null) {
            $this$kv_u24lambda_u2420.setPaintFlags($this$kv_u24lambda_u2420.getPaintFlags() | 8);
            $this$kv_u24lambda_u2420.setPadding(getUi().dp(8), getUi().dp(8), 0, getUi().dp(8));
            $this$kv_u24lambda_u2420.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda66
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    onClick.invoke();
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
            m1191constructorimpl = KtCompat.resultOf(OffsetDateTime.parse(s).toInstant());
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            m1191constructorimpl = KtCompat.resultOf(ResultKt.createFailure(th));
        }
        if (KtCompat.isFailure(m1191constructorimpl)) {
            m1191constructorimpl = null;
        }
        Instant instant = (Instant) m1191constructorimpl;
        if (instant != null) {
            return instant;
        }
        try {
            Result.Companion companion3 = Result.Companion;
            GxSystemPage gxSystemPage2 = this;
            m1191constructorimpl2 = KtCompat.resultOf(Instant.parse(s));
        } catch (Throwable th2) {
            Result.Companion companion4 = Result.Companion;
            m1191constructorimpl2 = KtCompat.resultOf(ResultKt.createFailure(th2));
        }
        return (Instant) (KtCompat.isFailure(m1191constructorimpl2) ? null : m1191constructorimpl2);
    }

    /* VF: from Vineflower (jadx: Code restructure failed;incorrect) */
    private final String versionTitle(JSONObject var1, boolean var2) {
        String var11 = null; Object var9 = null; // declarations Vineflower left out
        CharSequence var4 = null;
        Object var5 = null;
        String var3;
        if (var1 != null) {
            var3 = GxPage.str$default(this, var1, "date", null, 2, null);
        } else {
            var3 = null;
        }

        Instant var6 = this.parseDate(var3);
        if (var6 != null) {
            var3 = this.dayFmt.format(var6);
            String var18 = this.timeFmt.format(var6);
            var3 = var3 + " · " + var18;
        } else {
            var3 = "Unknown date";
        }

        String var10;
        if (var2) {
            label55: {
                if (var1 != null) {
                    String var7 = GxPage.str$default(this, var1, "version", null, 2, null);
                    if (var7 != null) {
                        var var8 = var7;
                        if (StringsKt.isBlank(var8)) {
                            var8 = (String)var5;
                        }

                        var9 = var8;
                        if (var9 != null) {
                            break label55;
                        }
                    }
                }

                var9 = "Unnumbered version";
            }

            var10 = var9 + " · " + var3;
        } else {
            label48: {
                if (var1 != null) {
                    var5 = GxPage.str$default(this, var1, "subject", null, 2, null);
                    if (var5 != null) {
                        var5 = (CharSequence)var5;
                        if (!StringsKt.isBlank((CharSequence)var5)) {
                            var4 = (CharSequence)var5;
                        }

                        var4 = (String)var4;
                        if (var4 != null) {
                            break label48;
                        }
                    }
                }

                var4 = "Untitled change";
            }

            label41: {
                if (var1 != null) {
                    var11 = GxPage.str$default(this, var1, "sha", null, 2, null);
                    if (var11 != null) {
                        break label41;
                    }
                }

                var11 = "";
            }

            String var12 = StringsKt.take(var11, 10);
            var10 = var4 + " · " + var3 + " · " + var12;
        }

        return var10;
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
                releaseVersions$lambda$29 = GxSystemPage.releaseVersions$lambda$29(function2, obj, obj2);
                return releaseVersions$lambda$29;
            }
        });
    }

    private static final List<Integer> releaseVersions$parts(String v) {
        Iterable $this$map$iv = StringsKt.split((CharSequence) v, new String[]{"."}, false, 0);
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

        /* VF: from Vineflower (jadx: ) */
        public static GxSystemPage.DayGroup copy$default(GxSystemPage.DayGroup var0, String var1, String var2, ArrayList var3, int var4, Object var5) {
            if ((var4 & 1) != 0) {
                var1 = var0.key;
            }

            if ((var4 & 2) != 0) {
                var2 = var0.label;
            }

            if ((var4 & 4) != 0) {
                var3 = var0.commits;
            }

            return var0.copy(var1, var2, var3);
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
                groupVersionHistory$lambda$32 = GxSystemPage.groupVersionHistory$lambda$32(function2, obj, obj2);
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
            int i = StringsKt.indexOf((CharSequence) c, "/commits/", 0, false);
            if (!StringsKt.startsWith(c, "https://github.com/", false) || i <= 0) {
                return "";
            }
            String substring = c.substring(0, i);
            Intrinsics.checkNotNullExpressionValue(substring, "substring(...)");
            v = substring;
        }
        if (StringsKt.startsWith(v, "git@github.com:", false)) {
            v = "https://github.com/" + StringsKt.substringAfter(v, ":", v);
        } else if (StringsKt.startsWith(v, "ssh://git@github.com/", false)) {
            v = "https://github.com/" + StringsKt.substringAfter(v, "ssh://git@github.com/", v);
        } else if (StringsKt.startsWith(v, "http://github.com/", false)) {
            v = "https://github.com/" + StringsKt.substringAfter(v, "http://github.com/", v);
        }
        if (!StringsKt.startsWith(v, "https://github.com/", false)) {
            return "";
        }
        String v2 = StringsKt.trimEnd(v, '/');
        return StringsKt.endsWith(v2, ".git", false) ? StringsKt.dropLast(v2, 4) : v2;
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
        if (StringsKt.startsWith(supplied, "https://github.com/", false) && StringsKt.contains((CharSequence) supplied, (CharSequence) "/commits/", false)) {
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

    /* VF: from Vineflower (jadx: Code restructure failed;incorrect) */
    public static final Unit loadBranches$lambda$42(GxSystemPage var0, String var1, Object var2) {
        List var18 = null; // declarations Vineflower left out
        boolean var4 = var2 instanceof JSONObject;
        JSONArray var5 = null;
        if (var4) {
            var2 = var2;
        } else {
            var2 = null;
        }

        if (var2 != null) {
            var5 = var2.optJSONArray("branches");
        }

        label83: {
            var4 = true;
            if (var5 != null) {
                List var13 = var0.strings(var5);
                if (var13 != null) {
                    Iterable var6 = var13;
                    var var14 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var6, 10));
                    Iterator var19 = var6.iterator();

                    while (var19.hasNext()) {
                        var14.add(StringsKt.trim((String & CharSequence)var19.next()).toString());
                    }

                    List var15 = var14;
                    var6 = var15;
                    var var16 = new ArrayList();

                    for (Object var21 : var6) {
                        boolean var3;
                        if (((String)var21).length() > 0) {
                            var3 = true;
                        } else {
                            var3 = false;
                        }

                        if (var3) {
                            var16.add(var21);
                        }
                    }

                    List var17 = var16;
                    List var22 = CollectionsKt.distinct(var17);
                    var18 = var22;
                    if (var22 != null) {
                        break label83;
                    }
                }
            }

            var18 = CollectionsKt.emptyList();
        }

        if (var1 == null && !var18.isEmpty()) {
            var0.branches = var18;
            GxPage var8 = var0;
            Intrinsics.checkNotNull(var2);
            var0.currentBranch = StringsKt.trim(GxPage.str$default(var8, ((JSONObject) var2), "currentBranch", null, 2, null)).toString();
            var0.branchListFallback = false;
            boolean var10;
            if (var0.targetBranch.length() == 0) {
                var10 = true;
            } else {
                var10 = false;
            }

            if (var10) {
                var0.targetBranch = var0.currentBranch;
                if (var0.targetBranch.length() > 0) {
                    var10 = true;
                } else {
                    var10 = false;
                }

                if (!var10 || GxSystemKt.GxSystem_CORE.contains(var0.targetBranch)) {
                    var4 = false;
                }

                var0.otherOpen = var4;
            }

            var0.isOnroad = var2.optBoolean("isOnroad");
            var0.branchLoading = false;
            var0.paint();
        } else if (var0.fastStatus == null) {
            var0.loadFastStatus(new GxSystemPage$$ExternalSyntheticLambda17(var0));
        } else {
            loadBranches$lambda$42$fallback(var0);
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

    /* VF: from Vineflower (jadx: ) */
    static void loadFastStatus$default(GxSystemPage var0, Function1 var1, int var2, Object var3) {
        if ((var2 & 1) != 0) {
            var1 = null;
        }

        var0.loadFastStatus(var1);
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

    /* VF: from Vineflower (jadx: Removed duplicated region;incorrect) */
    public static final Unit loadFastStatus$lambda$45(GxSystemPage var0, Function1 var1, String var2, Object var3) {
        var0.statusInFlight = false;
        if (var3 instanceof JSONObject) {
            var3 = (JSONObject)var3;
        } else {
            var3 = null;
        }

        Object var10 = null;
        if (var2 == null && var3 != null && var3.opt("running") instanceof Boolean) {
            String var11 = StringsKt.trim(GxPage.str$default(var0, ((JSONObject) var3), "stage", null, 2, null)).toString().toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(var11, "toLowerCase(...)");
            if (Intrinsics.areEqual(var11, "rebooting") && !var0.rebootPending) {
                var0.rebootPending = true;
                var0.rebootStartedAt = System.currentTimeMillis();
                var2 = var0.getHost();
                if (var2 != null) {
                    GxSystemReboot.INSTANCE.getStartedAt().put(var2, var0.rebootStartedAt);
                }
            }

            long var8 = var0.rebootStartedAt;
            long var6 = 0L;
            if (var8 > 0L) {
                var6 = System.currentTimeMillis() - var0.rebootStartedAt;
            }

            boolean var4;
            if (!var0.rebootPending || var3.optBoolean("running") || Intrinsics.areEqual(var11, "rebooting") || !var0.statusUnavailable && var6 < 30000L) {
                var4 = false;
            } else {
                var4 = true;
            }

            boolean var5;
            if (var0.rebootPending && Intrinsics.areEqual(var11, "error")) {
                var5 = true;
            } else {
                var5 = false;
            }

            var0.fastStatus = ((JSONObject) var3);
            var0.statusUnavailable = false;
            var0.isOnroad = var3.optBoolean("isOnroad");
            if (var4) {
                clearRebootPending$default(var0, false, 1, null);
                var2 = (String)var10;
            } else {
                var2 = (String)var10;
                if (var5) {
                    var0.clearRebootPending(false);
                    var2 = (String)var10;
                }
            }
        } else {
            if (var2 == null) {
                label82: {
                    if (var3 != null) {
                        var2 = GxPage.str$default(var0, ((JSONObject) var3), "error", null, 2, null);
                        if (var2 != null) {
                            CharSequence var17 = var2;
                            CharSequence var13 = var17;
                            if (StringsKt.isBlank(var17)) {
                                var13 = null;
                            }

                            var3 = (String)var13;
                            break label82;
                        }
                    }

                    var3 = null;
                }

                var2 = ((String) var3);
                if (var3 == null) {
                    var2 = "Update status unavailable.";
                }
            }

            var0.statusUnavailable = true;
            if (var0.rebootPending) {
                var0.rebootOfflineSeen = true;
            } else {
                var0.fastStatus = null;
            }
        }

        var0.paint();
        if (var1 != null) {
            var1.invoke(var2);
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

    /* VF: from Vineflower (jadx: ) */
    static void loadTailscale$default(GxSystemPage var0, Function0 var1, int var2, Object var3) {
        if ((var2 & 1) != 0) {
            var1 = null;
        }

        var0.loadTailscale(var1);
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
                paintStatus$lambda$64 = GxSystemPage.paintStatus$lambda$64(s, GxSystemPage.this, (LinearLayout) obj);
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
                paintStatus$lambda$64$lambda$57 = GxSystemPage.paintStatus$lambda$64$lambda$57(this$0, rcu);
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
                    paintStatus$lambda$64$lambda$58 = GxSystemPage.paintStatus$lambda$64$lambda$58(this$0, rec);
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
                GxPage.text$default(this$0, "Changed AGNOS partitions: " + CollectionsKt.joinToString(parts, ", ", "", "", -1, "...", null), c, 0.0f, 0, false, 28, null);
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

    /* VF: from Vineflower (jadx: Removed duplicated region;incorrect) */
    private final void paintAdv() {
        if (!this.advOpen) {
            LinearLayout var24 = this.advBox;
            if (var24 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("advBox");
                var24 = null;
            }

            this.fill(var24, "closed", new GxSystemPage$$ExternalSyntheticLambda78());
        } else {
            int var1;
            boolean var2;
            boolean var3;
            boolean var4;
            boolean var5;
            boolean var6;
            boolean var7;
            boolean var8;
            boolean var9;
            String var12;
            String var13;
            String var14;
            String var15;
            String var16;
            String var17;
            String var18;
            List var19;
            String var22;
            label28: {
                this.maybeLoadAgnos();
                var6 = this.getBranchSwitchBlocked();
                var19 = this.branches;
                var16 = this.currentBranch;
                var14 = this.targetBranch;
                var3 = this.otherOpen;
                var12 = this.versionMode;
                var17 = this.selectedCommit;
                var1 = this.versionCommits.size();
                var2 = this.versionHasMore;
                var5 = this.versionLoading;
                var13 = this.versionError;
                var15 = this.versionNotice;
                var4 = this.branchBusy;
                var9 = this.getInstallVersionBlocked();
                var7 = this.branchListFallback;
                var8 = this.pickerOpen;
                var18 = this.expanded.toString();
                JSONObject var10 = this.fastStatus;
                if (var10 != null) {
                    JSONObject var21 = var10.optJSONObject("versionPin");
                    if (var21 != null) {
                        var22 = var21.toString();
                        break label28;
                    }
                }

                var22 = null;
            }

            JSONObject var11 = this.agnosByBranch.get(this.targetBranch);
            String var25;
            if (var11 != null) {
                var25 = var11.toString();
            } else {
                var25 = null;
            }

            Object var20 = this.agnosErrByBranch.get(this.targetBranch);
            var12 = CollectionsKt.joinToString(CollectionsKt.listOf(
                    new Object[]{
                        var19,
                        var16,
                        var14,
                        var3,
                        var12,
                        var17,
                        var1,
                        var2,
                        var5,
                        var13,
                        var15,
                        var4,
                        var6,
                        var9,
                        var7,
                        var8,
                        var18,
                        var22,
                        var25,
                        var20,
                        this.agnosBusyFor
                    }
                ), "|", "", "", -1, "...", null);
            LinearLayout var26 = this.advBox;
            LinearLayout var23 = var26;
            if (var26 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("advBox");
                var23 = null;
            }

            this.fill(var23, var12, new GxSystemPage$$ExternalSyntheticLambda79(this, var6));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paintAdv$lambda$65(LinearLayout it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return Unit.INSTANCE;
    }

    /* VF: from Vineflower (jadx: ) */
    public static final Unit paintAdv$lambda$74(GxSystemPage var0, boolean var1, LinearLayout var2) {
        Intrinsics.checkNotNullParameter(var2, "b");
        var0.header("Install a Version", (ViewGroup)var2);
        var0.note(
            "Most people should stay on the latest version. Use this only to install a specific branch or historical version while troubleshooting.",
            (ViewGroup)var2
        );
        CharSequence var10 = var0.currentBranch;
        Object var9 = var10;
        if (StringsKt.isBlank(var10)) {
            var9 = "Unknown";
        }

        var0.note("Installed branch: " + var9, (ViewGroup)var2);
        boolean var6 = var0.otherOpen;
        boolean var7 = false;
        byte var5;
        if (var6) {
            var5 = 2;
        } else {
            var9 = var0.targetBranch;
            if (Intrinsics.areEqual(var9, "StarPilot")) {
                var5 = 0;
            } else if (Intrinsics.areEqual(var9, "Dom")) {
                var5 = 1;
            } else {
                var5 = -1;
            }
        }

        var0.choice(
            "Target branch",
            "StarPilot — Stable releases. Recommended for most users.\nDom — Latest features and fixes under development. Updates regularly and may introduce bugs.",
            CollectionsKt.listOf(new String[]{"StarPilot — Release", "Dom — Development", "Other branches…"}),
            var5,
            (ViewGroup)var2,
            new GxSystemPage$$ExternalSyntheticLambda72(var1, var0)
        );
        var9 = var2.getChildAt(var2.getChildCount() - 1);
        float var4 = 0.4F;
        float var3;
        if (!var1 && !var0.branchBusy) {
            var3 = 1.0F;
        } else {
            var3 = 0.4F;
        }

        ((View) var9).setAlpha(var3);
        if (var0.otherOpen) {
            List var30 = var0.getOtherBranches();
            GxPage.Row var11 = var0.row("Other branches", "Additional branches from this installation's repository.", (ViewGroup)var2, 1);
            String var23;
            if (var30.contains(var0.targetBranch)) {
                var23 = var0.targetBranch;
            } else if (!var30.isEmpty()) {
                var23 = "Select another branch";
            } else {
                var23 = "No other branches available";
            }

            var9 = (View)GxPage.Row.button$default(var11, var23, false, new GxSystemPage$$ExternalSyntheticLambda73(var30, var0), 2, null);
            if (!var1 && !var0.branchBusy && !var30.isEmpty()) {
                var6 = true;
            } else {
                var6 = false;
            }

            var0.en(((View) var9), var6);
        }

        if (var0.branchListFallback) {
            var0.note(
                "The full branch list is temporarily unavailable. Standard branches are shown; selecting a version still verifies it with the device.",
                (ViewGroup)var2
            );
        }

        boolean var16;
        if (var0.targetBranch.length() > 0) {
            var16 = true;
        } else {
            var16 = false;
        }

        if (var16) {
            boolean var8 = var0.branches.contains(var0.targetBranch);
            List var31 = CollectionsKt.listOf(new String[]{"Latest", "Choose earlier…"});
            var16 = Intrinsics.areEqual(var0.versionMode, "earlier");
            ViewGroup var35 = (ViewGroup)var2;
            GxSystemPage$$ExternalSyntheticLambda74 var25 = new GxSystemPage$$ExternalSyntheticLambda74(var1, var0, var8);
            var0.choice("Version", null, var31, ((var16) ? 1 : 0), var35, var25);
            var9 = var2.getChildAt(var2.getChildCount() - 1);
            var3 = var4;
            if (!var1) {
                var3 = var4;
                if (!var0.branchBusy) {
                    if (!var8) {
                        var3 = var4;
                    } else {
                        var3 = 1.0F;
                    }
                }
            }

            ((View) var9).setAlpha(var3);
            if (Intrinsics.areEqual(var0.versionMode, "earlier")) {
                if (!var1 && !var0.branchBusy) {
                    var6 = false;
                } else {
                    var6 = true;
                }

                var0.buildPicker(var2, var6);
            }

            if (Intrinsics.areEqual(var0.versionMode, "latest") && !Intrinsics.areEqual(var0.targetBranch, var0.currentBranch) && var8) {
                var0.agnosNote(var2);
            }

            if (!var8) {
                var0.note("This installed branch is no longer listed by the repository. Select an available target branch to install a version.", (ViewGroup)var2);
            }

            Pair[] var32 = new Pair[1];
            String var27;
            if (var0.branchBusy) {
                var27 = "Starting installation…";
            } else {
                var27 = "Install selected version";
            }

            var32[0] = TuplesKt.to(var27, new GxSystemPage$$ExternalSyntheticLambda75(var0));
            var0.en(var0.buttons(var32, (ViewGroup)var2, true).getChildAt(0), var0.getInstallVersionBlocked() ^ true);
        }

        JSONObject var28 = var0.fastStatus;
        JSONObject var29;
        if (var28 != null) {
            var29 = var28.optJSONObject("versionPin");
        } else {
            var29 = null;
        }

        if (var29 != null) {
            var2 = var0.card((ViewGroup)var2);
            String var36 = GxPage.str$default(var0, var29, "branch", null, 2, null);
            String var33 = var0.shortCommit(GxPage.str$default(var0, var29, "commit", null, 2, null));
            var0.text("Pinned version: " + var36 + " · " + var33, (ViewGroup)var2, 15.0F, CarUi.Companion.getTEXT(), true);
            GxPage.text$default(var0, "Automatic updates were paused at installation.", (ViewGroup)var2, 0.0F, 0, false, 28, null);
            GxPage var37 = var0;
            Pair var34 = TuplesKt.to("Return to Latest", new GxSystemPage$$ExternalSyntheticLambda76(var0));
            ViewGroup var13 = (ViewGroup)var2;
            View var14 = GxPage.buttons$default(var37, new Pair[]{var34}, var13, false, 4, null).getChildAt(0);
            var6 = var7;
            if (!var1) {
                var6 = var7;
                if (!var0.branchBusy) {
                    var6 = var7;
                    if (var0.branches.contains(GxPage.str$default(var0, var29, "branch", null, 2, null))) {
                        var6 = true;
                    }
                }
            }

            var0.en(var14, var6);
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
                paintAdv$lambda$74$lambda$70$lambda$69 = GxSystemPage.paintAdv$lambda$74$lambda$70$lambda$69(this$0, $ob, ((Integer) obj).intValue());
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
                GxPage.text$default(this, "Changed AGNOS partitions: " + CollectionsKt.joinToString(parts, ", ", "", "", -1, "...", null), c, 0.0f, 0, false, 28, null);
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

    /* VF: from Vineflower (jadx: Removed duplicated region;incorrect) */
    private final void buildPicker(LinearLayout var1, boolean var2) {
        boolean var7 = Intrinsics.areEqual(this.targetBranch, "StarPilot");
        List var11 = this.versionChoices();
        List var10 = this.groupVersionHistory(var11);
        boolean var5 = this.expanded.isEmpty();
        Boolean var13 = true;
        if (var5 && !var10.isEmpty()) {
            this.expanded.put(((GxSystemPage.DayGroup)var10.get(0)).getKey(), var13);
        }

        Iterator var9 = var11.iterator();

        Object var8;
        JSONObject var12;
        do {
            if (!var9.hasNext()) {
                var8 = null;
                break;
            }

            var8 = var9.next();
            var12 = (JSONObject)var8;
        } while (!Intrinsics.areEqual(GxPage.str$default(this, var12, "sha", null, 2, null), this.selectedCommit));

        var12 = (JSONObject)var8;
        String var50;
        if (var7) {
            int var4 = var11.size();
            if (var11.size() == 1) {
                var8 = "release";
            } else {
                var8 = "releases";
            }

            int var3 = this.versionCommits.size();
            var50 = var4 + " " + var8 + " · " + var3 + " builds checked";
        } else {
            var8 = var10;
            var var14 = new ArrayList();

            for (Object var15 : (Iterable) var8) {
                if (!Intrinsics.areEqual(((GxSystemPage.DayGroup)var15).getKey(), "unknown")) {
                    var14.add(var15);
                }
            }

            var8 = var14;
            if (var8.isEmpty()) {
                if (!this.versionCommits.isEmpty()) {
                    int var22 = this.versionCommits.size();
                    var8 = var22 + " versions loaded";
                } else {
                    var8 = "No history loaded yet";
                }

                var50 = (String)var8;
            } else {
                int var23 = this.versionCommits.size();
                String var52 = CollectionsKt.<GxSystemPage.DayGroup>last((List<? extends GxSystemPage.DayGroup>)var8).getLabel();
                if (var8.size() > 1) {
                    var8 = ((GxSystemPage.DayGroup)var8.get(0)).getLabel();
                    var8 = " – " + var8;
                } else {
                    var8 = "";
                }

                var50 = var23 + " versions · " + var52 + var8;
            }
        }

        LinearLayout var66 = this.card((ViewGroup)var1);
        GxPage.text$default(this, "Earlier version", (ViewGroup)var66, 15.0F, CarUi.Companion.getDIM(), false, 16, null);
        if (var12 != null) {
            var8 = this.versionTitle(var12, var7);
        } else {
            var8 = "Select an earlier version";
        }

        this.text((String)var8, (ViewGroup)var66, 21.0F, CarUi.Companion.getTEXT(), true);
        GxPage var69 = this;
        var5 = this.versionHasMore;
        String var65 = "All available history loaded.";
        if (var5) {
            if (var7) {
                var8 = "Open the picker to load older versions.";
            } else {
                var8 = "Open the picker to load older days.";
            }
        } else {
            label226: {
                if (!this.versionCommits.isEmpty()) {
                    boolean var24;
                    if (this.versionError.length() == 0) {
                        var24 = true;
                    } else {
                        var24 = false;
                    }

                    if (var24) {
                        var8 = "All available history loaded.";
                        break label226;
                    }
                }

                var8 = "Open the picker to browse history.";
            }
        }

        GxPage.text$default(var69, var50 + ". " + var8, (ViewGroup)var66, 0.0F, 0, false, 28, null);
        int var25;
        if (this.versionNotice.length() > 0) {
            var25 = 1;
        } else {
            var25 = 0;
        }

        if ((var25 != 0)) {
            GxPage.text$default(this, this.versionNotice, (ViewGroup)var66, 0.0F, 0, false, 28, null);
        }

        var69 = this;
        if (this.pickerOpen) {
            var8 = "Hide versions";
        } else {
            var8 = "Browse versions";
        }

        var8 = TuplesKt.to(var8, new GxSystemPage$$ExternalSyntheticLambda46(var2, this));
        ViewGroup var16 = (ViewGroup)var66;
        this.en(GxPage.buttons$default(var69, new Pair[]{(Pair)var8}, var16, false, 4, null).getChildAt(0), var2 ^ true);
        if (this.pickerOpen && !var2) {
            var66 = this.card((ViewGroup)var1);
            if (var7) {
                var8 = "Earlier StarPilot versions";
            } else {
                var8 = "Earlier versions by day";
            }

            this.text((String)var8, (ViewGroup)var66, 21.0F, CarUi.Companion.getTEXT(), true);
            GxPage.text$default(this, var50, (ViewGroup)var66, 0.0F, 0, false, 28, null);
            if (this.versionNotice.length() > 0) {
                var25 = (((boolean)true) ? 1 : 0);
            } else {
                var25 = (((boolean)false) ? 1 : 0);
            }

            if ((var25 != 0)) {
                GxPage.text$default(this, this.versionNotice, (ViewGroup)var66, 0.0F, 0, false, 28, null);
            }

            GxPage var53 = this;
            if (var7) {
                var8 = "Each version uses its newest build. Build dates are shown in your local time.";
            } else {
                var8 = "Expand a day to choose a build. Build dates are shown in your local time.";
            }

            GxPage.text$default(var53, (String)var8, (ViewGroup)var66, 0.0F, 0, false, 28, null);
            if (var7) {
                var8 = var11.iterator();

                while (var8.hasNext()) {
                    buildPicker$versionRow(this, var7, var1, var2, (JSONObject)var8.next(), 0);
                }
            } else {
                for (GxSystemPage.DayGroup var48 : ((DayGroup) var10)) {
                    var5 = Intrinsics.areEqual(this.expanded.get(var48.getKey()), var13);
                    GxPage var58 = this;
                    String var62 = var48.getLabel();
                    var25 = var48.getCommits().size();
                    String var54;
                    if (var48.getCommits().size() == 1) {
                        var54 = "build";
                    } else {
                        var54 = "builds";
                    }

                    GxPage.Row var55 = GxPage.row$default(var58, var62 + "  · " + var25 + " " + var54, null, (ViewGroup)var1, 0, 8, null);
                    String var59;
                    if (var5) {
                        var59 = "▲";
                    } else {
                        var59 = "▼";
                    }

                    var55.value(var59);
                    var55.getView().setOnClickListener(new GxSystemPage$$ExternalSyntheticLambda47(this, var48, var5));
                    if (var5) {
                        Iterator var60 = var48.getCommits().iterator();
                        Intrinsics.checkNotNullExpressionValue(var60, "iterator(...)");

                        while (var60.hasNext()) {
                            Object var63 = var60.next();
                            Intrinsics.checkNotNullExpressionValue(var63, "next(...)");
                            buildPicker$versionRow(this, var7, var1, var2, (JSONObject)var63, 1);
                        }
                    }
                }
            }

            var8 = this.card((ViewGroup)var1);
            boolean var29;
            if (this.versionError.length() > 0) {
                var29 = true;
            } else {
                var29 = false;
            }

            if (var29) {
                GxPage.text$default(this, this.versionError, (ViewGroup)var8, 15.0F, GxSystemKt.GxSystem_DANGER, false, 16, null);
            }

            if (this.versionLoading) {
                GxPage.text$default(this, "Loading older history…", (ViewGroup)var8, 0.0F, 0, false, 28, null);
            }

            label252: {
                if (!this.versionHasMore) {
                    if (this.versionError.length() > 0) {
                        var29 = true;
                    } else {
                        var29 = false;
                    }

                    if (!var29) {
                        if (!this.versionLoading) {
                            GxPage var57 = this;
                            String var20;
                            if (!this.versionCommits.isEmpty()) {
                                var20 = var65;
                            } else {
                                var20 = "No history available.";
                            }

                            GxPage.text$default(var57, var20, (ViewGroup)var8, 0.0F, 0, false, 28, null);
                        }
                        break label252;
                    }
                }

                GxPage var56 = this;
                if (this.versionError.length() > 0) {
                    var29 = true;
                } else {
                    var29 = false;
                }

                String var17;
                if (var29) {
                    var17 = "Retry history";
                } else if (var7) {
                    var17 = "Load older versions";
                } else {
                    var17 = "Load older days";
                }

                Pair var18 = TuplesKt.to(var17, new GxSystemPage$$ExternalSyntheticLambda48(var2, this));
                ViewGroup var61 = (ViewGroup)var8;
                View var19 = GxPage.buttons$default(var56, new Pair[]{var18}, var61, false, 4, null).getChildAt(0);
                if (!this.versionLoading && !var2) {
                    var2 = true;
                } else {
                    var2 = false;
                }

                this.en(var19, var2);
            }

            var29 = false;
            if (this.versionHasMore) {
                if (this.versionError.length() == 0) {
                    var29 = true;
                }

                if (var29) {
                    GxPage.text$default(this, "Keep loading to browse further back.", (ViewGroup)var8, 0.0F, 0, false, 28, null);
                }
            }
        } else {
            boolean var26 = false;
            if (this.versionError.length() > 0) {
                var26 = true;
            }

            if (var26) {
                GxPage.text$default(this, this.versionError, (ViewGroup)var66, 15.0F, GxSystemKt.GxSystem_DANGER, false, 16, null);
            }

            if (this.versionLoading) {
                GxPage.text$default(this, "Loading older history…", (ViewGroup)var66, 0.0F, 0, false, 28, null);
            }
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
        fill(linearLayout2, CollectionsKt.joinToString(this.profiles, ", ", "", "", -1, "...", new Function1() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda60
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                CharSequence paintProfiles$lambda$85;
                paintProfiles$lambda$85 = GxSystemPage.paintProfiles$lambda$85((JSONObject) obj);
                return paintProfiles$lambda$85;
            }
        }) + "|" + this.profileBusy + "|" + this.isOnroad, new Function1() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda61
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
                    paintProfiles$lambda$89$lambda$87 = GxSystemPage.paintProfiles$lambda$89$lambda$87(this$0, p);
                    return paintProfiles$lambda$89$lambda$87;
                }
            }, 2, null), (this$0.profileBusy.length() == 0) && !this$0.isOnroad);
            TextView button = r.button(Intrinsics.areEqual(this$0.profileBusy, new StringBuilder().append("load-").append(slot).toString()) ? "Loading..." : "Load", true, new Function0() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda22
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit paintProfiles$lambda$89$lambda$88;
                    paintProfiles$lambda$89$lambda$88 = GxSystemPage.paintProfiles$lambda$89$lambda$88(this$0, p);
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
                paintFactory$lambda$92 = GxSystemPage.paintFactory$lambda$92(fr, s, GxSystemPage.this, (LinearLayout) obj);
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
                    paintTailscale$lambda$97$lambda$93 = GxSystemPage.paintTailscale$lambda$97$lambda$93(this$0);
                    return paintTailscale$lambda$97$lambda$93;
                }
            }), true ^ this$0.tsBusy);
        } else {
            GxPage.Row r2 = GxPage.row$default(this$0, "Uninstall Tailscale", "Disconnects the device from your tailnet and removes the Tailscale binaries and state.", b, 0, 8, null);
            TextView it = GxPage.Row.button$default(r2, this$0.tsBusy ? "Uninstalling..." : "Uninstall", false, new Function0() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda50
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit paintTailscale$lambda$97$lambda$94;
                    paintTailscale$lambda$97$lambda$94 = GxSystemPage.paintTailscale$lambda$97$lambda$94(this$0);
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
                paintTailscale$lambda$97$lambda$96 = GxSystemPage.paintTailscale$lambda$97$lambda$96(this$0);
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

    /* VF: from Vineflower (jadx: Code restructure failed;incorrect) */
    public static final Unit loadVersions$step$lambda$101(
        int var0, GxSystemPage var1, String var2, int var3, String var4, int var5, int var6, int var7, String var8, Object var9
    ) {
        if (var0 != var1.versionGen) {
            return Unit.INSTANCE;
        } else {
            boolean var11 = var9 instanceof JSONObject;
            Collection var12 = null;
            if (var11) {
                var9 = var9;
            } else {
                var9 = null;
            }

            if (var8 != null) {
                CharSequence var22 = var8;
                Object var15 = var22;
                if (StringsKt.isBlank(var22)) {
                    var15 = "Failed to load version history. Try again.";
                }

                var1.versionError = (String)var15;
                loadVersions$finish(var0, var1);
                return Unit.INSTANCE;
            } else {
                Object var23 = var12;
                if (var9 != null) {
                    var23 = var9.optJSONArray("commits");
                }

                if (var9 != null
                    && Intrinsics.areEqual(GxPage.str$default(var1, ((JSONObject) var9), "branch", null, 2, null), var2)
                    && var9.optInt("page", -1) == var3
                    && GxSystemKt.GxSystem_SHA.matches(GxPage.str$default(var1, ((JSONObject) var9), "head", null, 2, null))) {
                    boolean var10;
                    if (var4.length() > 0) {
                        var10 = true;
                    } else {
                        var10 = false;
                    }

                    if ((!var10 || Intrinsics.areEqual(GxPage.str$default(var1, ((JSONObject) var9), "head", null, 2, null), var4)) && var23 != null) {
                        Iterable var16 = var1.objects(((JSONArray) var23));
                        var12 = new ArrayList();

                        for (Object var13 : var16) {
                            JSONObject var14 = (JSONObject)var13;
                            if (GxSystemKt.GxSystem_SHA.matches(GxPage.str$default(var1, var14, "sha", null, 2, null))) {
                                var12.add(var13);
                            }
                        }

                        List var25 = (List)var12;
                        if (var3 > 1) {
                            Iterable var30 = var1.versionCommits;
                            var var17 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var30, 10));

                            for (JSONObject var34 : (Iterable) (JSONObject) ((JSONObject) var30)) {
                                var17.add(GxPage.str$default(var1, var34, "sha", null, 2, null));
                            }

                            List var18 = var17;
                            HashSet var32 = CollectionsKt.toHashSet(var18);

                            for (JSONObject var35 : (Iterable) (JSONObject) ((JSONObject) var25)) {
                                if (var32.add(GxPage.str$default(var1, var35, "sha", null, 2, null))) {
                                    var1.versionCommits.add(var35);
                                }
                            }
                        } else {
                            var1.versionCommits.clear();
                            var1.versionCommits.addAll(var25);
                        }

                        var11 = var9.optBoolean("cached");
                        var4 = "";
                        if (var11) {
                            Instant var33 = var1.parseDate(GxPage.str$default(var1, ((JSONObject) var9), "cachedAt", null, 2, null));
                            if (var33 != null) {
                                var4 = var1.stampFmt.format(var33);
                                var4 = " from " + var4;
                            }

                            var1.versionNotice = "Showing saved history" + var4 + ". Installation still needs an online check.";
                        } else if (var3 == 1) {
                            var1.versionNotice = "";
                        }

                        var1.versionHead = GxPage.str$default(var1, ((JSONObject) var9), "head", null, 2, null);
                        var1.versionPage = var3;
                        if (var9.optBoolean("hasMore") && !var25.isEmpty()) {
                            var11 = true;
                        } else {
                            var11 = false;
                        }

                        var1.versionHasMore = var11;
                        if (var1.versionCommits.isEmpty()) {
                            var1.versionError = "No versions are available for this branch. Choose Latest or another branch.";
                        }

                        if (var1.versionHasMore && var1.versionChoices().size() <= var5 && var6 + 1 < var7) {
                            loadVersions$step(var1, var2, var0, var5, var7, var6 + 1, var3 + 1, var1.versionHead);
                        } else {
                            loadVersions$finish(var0, var1);
                        }

                        return Unit.INSTANCE;
                    }
                }

                var1.versionError = "Version history changed. Choose Latest and try again.";
                loadVersions$finish(var0, var1);
                return Unit.INSTANCE;
            }
        }
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
            str = "\n\nAGNOS firmware update included:\n\n" + CollectionsKt.joinToString(destination$iv$iv, "\n", "", "", -1, "...", new Function1() { // from class: com.starpilot.starview.GxSystemPage$$ExternalSyntheticLambda63
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    CharSequence installSelectedVersion$lambda$104;
                    installSelectedVersion$lambda$104 = GxSystemPage.installSelectedVersion$lambda$104((String) obj);
                    return installSelectedVersion$lambda$104;
                }
            });
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
                installSelectedVersion$lambda$109$lambda$108 = GxSystemPage.installSelectedVersion$lambda$109$lambda$108(this$0, $branch, $gen, $commit, $version, (String) obj);
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
                        installSelectedVersion$lambda$109$lambda$108$lambda$107 = GxSystemPage.installSelectedVersion$lambda$109$lambda$108$lambda$107(this$0, $version, $branch, (String) obj, (JSONObject) obj2);
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

    /* VF: from Vineflower (jadx: Code restructure failed;incorrect) */
    public static final Unit installSelectedVersion$lambda$109$lambda$108$lambda$107(GxSystemPage var0, String var1, String var2, String var3, JSONObject var4) {
        var0.branchBusy = false;
        if (var3 != null) {
            CharSequence var8 = var3;
            Object var7 = var8;
            if (StringsKt.isBlank(var8)) {
                var7 = "Installation failed.";
            }

            var0.toast((String)var7);
            var0.paint();
            return Unit.INSTANCE;
        } else {
            label25: {
                var0.markRebootPending();
                if (var4 != null) {
                    var3 = GxPage.str$default(var0, var4, "message", null, 2, null);
                    if (var3 != null) {
                        CharSequence var12 = var3;
                        CharSequence var10 = var12;
                        if (StringsKt.isBlank(var12)) {
                            var10 = null;
                        }

                        var3 = (String)var10;
                        if (var3 != null) {
                            var1 = var3;
                            break label25;
                        }
                    }
                }

                var1 = StringsKt.substringBefore(var1, "\n", var1);
                var1 = "Installing " + var1 + " on " + var2 + "...";
            }

            var0.toast(var1);
            var0.paint();
            loadFastStatus$default(var0, null, 1, null);
            return Unit.INSTANCE;
        }
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

    /* VF: from Vineflower (jadx: Code restructure failed;incorrect) */
    public static final Unit checkUpdates$lambda$112(GxSystemPage var0, String var1) {
        var0.busy = "";
        if (var1 != null) {
            var0.toast("Failed to check for updates.");
        } else {
            boolean var2 = true;
            var0.checkedForUpdates = true;
            JSONObject var4 = var0.fastStatus;
            if (var0.getUpdateInProgress()) {
                var0.toast("An update is already running.");
            } else {
                if (var4 == null || !var4.optBoolean("updateAvailable")) {
                    var2 = false;
                }

                if (var2) {
                    CharSequence var3 = GxPage.str$default(var0, var4, "message", null, 2, null);
                    Object var5 = var3;
                    if (StringsKt.isBlank(var3)) {
                        var5 = "Update available.";
                    }

                    var0.toast((String)var5);
                } else {
                    label33: {
                        if (var4 != null) {
                            var1 = GxPage.str$default(var0, var4, "message", null, 2, null);
                            if (var1 != null) {
                                CharSequence var9 = var1;
                                CharSequence var7 = var9;
                                if (StringsKt.isBlank(var9)) {
                                    var7 = null;
                                }

                                var1 = (String)var7;
                                if (var1 != null) {
                                    break label33;
                                }
                            }
                        }

                        var1 = "No update available — you're up to date.";
                    }

                    var0.toast(var1);
                }
            }
        }

        var0.paint();
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
                runUpdate$lambda$119$lambda$118 = GxSystemPage.runUpdate$lambda$119$lambda$118(this$0, (String) obj, (JSONObject) obj2);
                return runUpdate$lambda$119$lambda$118;
            }
        }, 12, null);
        return Unit.INSTANCE;
    }

    /* VF: from Vineflower (jadx: Code restructure failed;incorrect) */
    public static final Unit runUpdate$lambda$119$lambda$118(GxSystemPage var0, String var1, JSONObject var2) {
        var0.busy = "";
        if (var1 != null) {
            CharSequence var8 = var1;
            Object var6 = var8;
            if (StringsKt.isBlank(var8)) {
                var6 = "Update failed.";
            }

            var0.toast((String)var6);
            var0.paint();
            return Unit.INSTANCE;
        } else {
            label25: {
                var0.markRebootPending();
                if (var2 != null) {
                    var1 = GxPage.str$default(var0, var2, "message", null, 2, null);
                    if (var1 != null) {
                        CharSequence var7 = var1;
                        CharSequence var4 = var7;
                        if (StringsKt.isBlank(var7)) {
                            var4 = null;
                        }

                        var1 = (String)var4;
                        if (var1 != null) {
                            break label25;
                        }
                    }
                }

                var1 = "Update started.";
            }

            var0.toast(var1);
            var0.paint();
            loadFastStatus$default(var0, null, 1, null);
            return Unit.INSTANCE;
        }
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
                factoryReset$lambda$122$lambda$121 = GxSystemPage.factoryReset$lambda$122$lambda$121(this$0, (String) obj, (JSONObject) obj2);
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
                    saveProfile$lambda$128 = GxSystemPage.saveProfile$lambda$128(go);
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
                saveProfile$lambda$127$lambda$126 = GxSystemPage.saveProfile$lambda$127$lambda$126(this$0, $label, (String) obj, (JSONObject) obj2);
                return saveProfile$lambda$127$lambda$126;
            }
        }, 12, null);
        return Unit.INSTANCE;
    }

    /* VF: from Vineflower (jadx: Code restructure failed;incorrect) */
    public static final Unit saveProfile$lambda$127$lambda$126(GxSystemPage var0, String var1, String var2, JSONObject var3) {
        var0.profileBusy = "";
        if (var2 != null) {
            CharSequence var6 = var2;
            Object var4 = var6;
            if (StringsKt.isBlank(var6)) {
                var4 = "Failed to save settings profile.";
            }

            var1 = (String)var4;
        } else {
            label24: {
                if (var3 != null) {
                    var2 = GxPage.str$default(var0, var3, "message", null, 2, null);
                    if (var2 != null) {
                        CharSequence var10 = var2;
                        CharSequence var8 = var10;
                        if (StringsKt.isBlank(var10)) {
                            var8 = null;
                        }

                        var2 = (String)var8;
                        if (var2 != null) {
                            var1 = var2;
                            break label24;
                        }
                    }
                }

                var1 = "Saved " + var1 + ".";
            }
        }

        var0.toast(var1);
        var0.paint();
        var0.loadProfiles();
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
                loadProfile$lambda$133$lambda$132 = GxSystemPage.loadProfile$lambda$133$lambda$132(this$0, $label, (String) obj, (JSONObject) obj2);
                return loadProfile$lambda$133$lambda$132;
            }
        }, 12, null);
        return Unit.INSTANCE;
    }

    /* VF: from Vineflower (jadx: Code restructure failed;incorrect) */
    public static final Unit loadProfile$lambda$133$lambda$132(GxSystemPage var0, String var1, String var2, JSONObject var3) {
        var0.profileBusy = "";
        if (var2 != null) {
            CharSequence var7 = var2;
            Object var4 = var7;
            if (StringsKt.isBlank(var7)) {
                var4 = "Failed to load settings profile.";
            }

            var0.toast((String)var4);
        } else {
            label27: {
                if (var3 != null) {
                    var2 = GxPage.str$default(var0, var3, "message", null, 2, null);
                    if (var2 != null) {
                        CharSequence var11 = var2;
                        CharSequence var9 = var11;
                        if (StringsKt.isBlank(var11)) {
                            var9 = null;
                        }

                        var2 = (String)var9;
                        if (var2 != null) {
                            var1 = var2;
                            break label27;
                        }
                    }
                }

                var1 = "Loaded " + var1 + ".";
            }

            var0.toast(var1);
            GalaxyApi var6 = var0.getApi();
            if (var6 != null) {
                GalaxyApi.refreshValues$default(var6, null, 1, null);
            }
        }

        var0.paint();
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
                GxSystemPage.backupToggles$lambda$140(target, fileName, GxSystemPage.this);
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
            execute = GxPage.Companion.client(60L).newCall(new Request.Builder().url(str).post(RequestBody.Companion.create(new byte[0], (MediaType) null, 0, 0)).build()).execute();
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
                m1191constructorimpl = KtCompat.resultOf(new JSONObject(new String(bArr, Charsets.UTF_8)).optString("message"));
            } catch (Throwable th) {
                Result.Companion companion2 = Result.Companion;
                m1191constructorimpl = KtCompat.resultOf(ResultKt.createFailure(th));
            }
            if (KtCompat.isFailure(m1191constructorimpl)) {
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
                    GxSystemPage.backupToggles$lambda$140$lambda$139(gxSystemPage, objectRef);
                }
            });
        } finally {
        }
    }

    /* VF: from Vineflower (jadx: ) */
    public static final void backupToggles$lambda$140$lambda$139(GxSystemPage var0, ObjectRef var1) {
        var0.toast((String)var1.element);
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
                restoreToggles$lambda$146$lambda$145 = GxSystemPage.restoreToggles$lambda$146$lambda$145(this$0, (byte[]) obj, (String) obj2);
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
            data = KtCompat.resultOf(new JSONTokener(new String(bytes, Charsets.UTF_8)).nextValue());
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            data = KtCompat.resultOf(ResultKt.createFailure(th));
        }
        if (KtCompat.isFailure(data)) {
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
                restoreToggles$lambda$146$lambda$145$lambda$144 = GxSystemPage.restoreToggles$lambda$146$lambda$145$lambda$144(this$0, (String) obj, (JSONObject) obj2);
                return restoreToggles$lambda$146$lambda$145$lambda$144;
            }
        });
        return Unit.INSTANCE;
    }

    /* VF: from Vineflower (jadx: Code restructure failed;incorrect) */
    public static final Unit restoreToggles$lambda$146$lambda$145$lambda$144(GxSystemPage var0, String var1, JSONObject var2) {
        if (var1 != null) {
            CharSequence var8 = var1;
            Object var4 = var8;
            if (StringsKt.isBlank(var8)) {
                var4 = "Failed to restore toggles.";
            }

            var0.toast((String)var4);
        } else {
            label27: {
                if (var2 != null) {
                    var1 = GxPage.str$default(var0, var2, "message", null, 2, null);
                    if (var1 != null) {
                        CharSequence var9 = var1;
                        CharSequence var6 = var9;
                        if (StringsKt.isBlank(var9)) {
                            var6 = null;
                        }

                        var1 = (String)var6;
                        if (var1 != null) {
                            break label27;
                        }
                    }
                }

                var1 = "Toggles restored!";
            }

            var0.toast(var1);
            GalaxyApi var3 = var0.getApi();
            if (var3 != null) {
                GalaxyApi.refreshValues$default(var3, null, 1, null);
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
                resetDefault$lambda$148$lambda$147 = GxSystemPage.resetDefault$lambda$148$lambda$147(this$0, (String) obj, (JSONObject) obj2);
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
                deleteAllRoutes$lambda$152$lambda$151 = GxSystemPage.deleteAllRoutes$lambda$152$lambda$151(this$0, (String) obj, (JSONObject) obj2);
                return deleteAllRoutes$lambda$152$lambda$151;
            }
        });
        return Unit.INSTANCE;
    }

    /* VF: from Vineflower (jadx: Code restructure failed;incorrect) */
    public static final Unit deleteAllRoutes$lambda$152$lambda$151(GxSystemPage var0, String var1, JSONObject var2) {
        if (var1 != null) {
            CharSequence var7 = var1;
            Object var3 = var7;
            if (StringsKt.isBlank(var7)) {
                var3 = "Failed to delete driving routes.";
            }

            var1 = (String)var3;
        } else {
            label24: {
                if (var2 != null) {
                    var1 = GxPage.str$default(var0, var2, "message", null, 2, null);
                    if (var1 != null) {
                        CharSequence var8 = var1;
                        CharSequence var6 = var8;
                        if (StringsKt.isBlank(var8)) {
                            var6 = null;
                        }

                        var1 = (String)var6;
                        if (var1 != null) {
                            break label24;
                        }
                    }
                }

                var1 = "All local driving routes deleted.";
            }
        }

        var0.toast(var1);
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

    /* VF: from Vineflower (jadx: Code restructure failed;incorrect) */
    public static final Unit installTailscale$lambda$158(GxSystemPage var0, String var1, JSONObject var2) {
        if (var1 != null) {
            CharSequence var12 = var1;
            Object var5 = var12;
            if (StringsKt.isBlank(var12)) {
                var5 = "Failed to install Tailscale.";
            }

            var0.toast((String)var5);
        } else {
            Object var3;
            label35: {
                var3 = null;
                if (var2 != null) {
                    var1 = GxPage.str$default(var0, var2, "message", null, 2, null);
                    if (var1 != null) {
                        CharSequence var4 = var1;
                        CharSequence var7 = var4;
                        if (StringsKt.isBlank(var4)) {
                            var7 = null;
                        }

                        var1 = (String)var7;
                        if (var1 != null) {
                            break label35;
                        }
                    }
                }

                var1 = "Tailscale setup started.";
            }

            var0.toast(var1);
            if (var2 != null) {
                var1 = GxPage.str$default(var0, var2, "auth_url", null, 2, null);
                if (var1 != null) {
                    var1 = var1;
                    if (StringsKt.isBlank(var1)) {
                        var1 = (String)var3;
                    }

                    var1 = var1;
                    if (var1 != null) {
                        var0.openUrl(var1);
                    }
                }
            }
        }

        var0.loadTailscale(new GxSystemPage$$ExternalSyntheticLambda77(var0));
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
                uninstallTailscale$lambda$163$lambda$162 = GxSystemPage.uninstallTailscale$lambda$163$lambda$162(this$0, (String) obj, (JSONObject) obj2);
                return uninstallTailscale$lambda$163$lambda$162;
            }
        });
        return Unit.INSTANCE;
    }

    /* VF: from Vineflower (jadx: Code restructure failed;incorrect) */
    public static final Unit uninstallTailscale$lambda$163$lambda$162(GxSystemPage var0, String var1, JSONObject var2) {
        if (var1 != null) {
            CharSequence var7 = var1;
            Object var3 = var7;
            if (StringsKt.isBlank(var7)) {
                var3 = "Failed to uninstall Tailscale.";
            }

            var1 = (String)var3;
        } else {
            label24: {
                if (var2 != null) {
                    var1 = GxPage.str$default(var0, var2, "message", null, 2, null);
                    if (var1 != null) {
                        CharSequence var8 = var1;
                        CharSequence var6 = var8;
                        if (StringsKt.isBlank(var8)) {
                            var6 = null;
                        }

                        var1 = (String)var6;
                        if (var1 != null) {
                            break label24;
                        }
                    }
                }

                var1 = "Tailscale uninstalled.";
            }
        }

        var0.toast(var1);
        var0.loadTailscale(new GxSystemPage$$ExternalSyntheticLambda62(var0));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit uninstallTailscale$lambda$163$lambda$162$lambda$161(GxSystemPage this$0) {
        this$0.tsBusy = false;
        this$0.paint();
        return Unit.INSTANCE;
    }
}
