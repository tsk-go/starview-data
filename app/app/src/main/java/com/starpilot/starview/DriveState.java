package com.starpilot.starview;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Looper;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import com.starpilot.starview.DriveState;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.json.JSONObject;

/* compiled from: DriveState.kt */
public final class DriveState {
    private static volatile long fetchedAt;
    private static volatile boolean restarting;
    public static final DriveState INSTANCE = new DriveState();
    private static volatile String modeNow = "default";

    private DriveState() {
    }

    public final Boolean onroad() {
        Object v;
        Telemetry t = Link.INSTANCE.getTelemetry();
        if (t == null) {
            return null;
        }
        DataClient data = Link.INSTANCE.getData();
        boolean z = false;
        if (data != null && data.getConnected()) {
            z = true;
        }
        if (z && (v = t.getInfo().get("onroad")) != null) {
            return Boolean.valueOf(t.iBool("onroad", Intrinsics.areEqual(v, (Object) true)));
        }
        return null;
    }

    public final boolean engaged() {
        Telemetry it = Link.INSTANCE.getTelemetry();
        if (it != null) {
            return it.getEnabled() || it.iBool("engaged", false);
        }
        return false;
    }

    /* VF: from Vineflower (jadx: ) */
    public static String mode$default(DriveState var0, boolean var1, Function0 var2, int var3, Object var4) {
        if ((var3 & 1) != 0) {
            var1 = false;
        }

        if ((var3 & 2) != 0) {
            var2 = null;
        }

        return var0.mode(var1, var2);
    }

    public final String mode(boolean force, final Function0<Unit> onChange) {
        long now = System.currentTimeMillis();
        if (force || now - fetchedAt >= 3000) {
            fetchedAt = now;
            Galaxy7 current = Galaxy7.Companion.current();
            if (current != null) {
                current.get("/api/drive-state/status", new Function2() { // from class: com.starpilot.starview.DriveState$$ExternalSyntheticLambda1
                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        Unit mode$lambda$1;
                        mode$lambda$1 = DriveState.mode$lambda$1(onChange, (JSONObject) obj, (String) obj2);
                        return mode$lambda$1;
                    }
                });
            }
        }
        return modeNow;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit mode$lambda$1(Function0 $onChange, JSONObject o, String str) {
        String optString = o != null ? o.optString("mode") : null;
        String str2 = "onroad";
        if (!Intrinsics.areEqual(optString, "onroad")) {
            str2 = "offroad";
            if (!Intrinsics.areEqual(optString, "offroad")) {
                if (optString == null) {
                    return Unit.INSTANCE;
                }
                str2 = "default";
            }
        }
        String m = str2;
        if (!Intrinsics.areEqual(m, modeNow)) {
            modeNow = m;
            if ($onChange != null) {
                $onChange.invoke();
            }
        }
        return Unit.INSTANCE;
    }

    public final boolean forcedOffroad() {
        return Intrinsics.areEqual(mode$default(this, false, null, 3, null), "offroad");
    }

    public final boolean forcedOnroad() {
        return Intrinsics.areEqual(mode$default(this, false, null, 3, null), "onroad");
    }

    /* VF: from Vineflower (jadx: ) */
    public static void request$default(DriveState var0, Activity var1, String var2, Function1 var3, int var4, Object var5) {
        if ((var4 & 4) != 0) {
            var3 = null;
        }

        var0.request(var1, var2, var3);
    }

    public final void request(final Activity act, String mode, final Function1<? super Boolean, Unit> then) {
        Intrinsics.checkNotNullParameter(act, "act");
        Intrinsics.checkNotNullParameter(mode, "mode");
        DataClient d = Link.INSTANCE.getData();
        if (d == null || !d.getConnected()) {
            Toast.makeText(act, "Not connected to the comma", 0).show();
            if (then != null) {
                then.invoke(false);
                return;
            }
            return;
        }
        d.request("drive_state", MapsKt.mapOf(TuplesKt.to("mode", mode)), new Function1() { // from class: com.starpilot.starview.DriveState$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit request$lambda$2;
                request$lambda$2 = DriveState.request$lambda$2(act, then, (JSONObject) obj);
                return request$lambda$2;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit request$lambda$2(Activity $act, Function1 $then, JSONObject ack) {
        Intrinsics.checkNotNullParameter(ack, "ack");
        boolean ok = ack.optBoolean("ok");
        if (!ok) {
            Toast.makeText($act, ack.optString("error", "rejected"), 0).show();
        }
        mode$default(INSTANCE, true, null, 2, null);
        if ($then != null) {
            $then.invoke(Boolean.valueOf(ok));
        }
        return Unit.INSTANCE;
    }

    public final boolean getRestarting() {
        return restarting;
    }

    /* VF: from Vineflower (jadx: ) */
    public static void restart$default(DriveState var0, Activity var1, Function1 var2, int var3, Object var4) {
        if ((var3 & 2) != 0) {
            var2 = null;
        }

        var0.restart(var1, var2);
    }

    public final void restart(final Activity act, final Function1<? super Boolean, Unit> then) {
        Intrinsics.checkNotNullParameter(act, "act");
        if (restarting) {
            return;
        }
        DataClient d = Link.INSTANCE.getData();
        if (d == null || !d.getConnected()) {
            Toast.makeText(act, "Not connected to the comma", 0).show();
            if (then != null) {
                then.invoke(false);
                return;
            }
            return;
        }
        final String back = modeNow;
        if (!Intrinsics.areEqual(back, "onroad")) {
            back = null;
        }
        if (back == null) {
            back = "default";
        }
        final Handler main = new Handler(Looper.getMainLooper());
        restarting = true;
        d.request("drive_state", MapsKt.mapOf(TuplesKt.to("mode", "offroad")), new Function1() { // from class: com.starpilot.starview.DriveState$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit restart$lambda$4;
                restart$lambda$4 = DriveState.restart$lambda$4(act, main, then, back, (JSONObject) obj);
                return restart$lambda$4;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void restart$done(Activity $act, Function1<? super Boolean, Unit> function1, boolean ok, String msg) {
        restarting = false;
        Toast.makeText($act, msg, 1).show();
        mode$default(INSTANCE, true, null, 2, null);
        if (function1 != null) {
            function1.invoke(Boolean.valueOf(ok));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit restart$lambda$4(Activity $act, Handler $main, Function1 $then, String $back, JSONObject ack) {
        Intrinsics.checkNotNullParameter(ack, "ack");
        if (!ack.optBoolean("ok")) {
            String optString = ack.optString("error", "The comma refused to go offroad");
            Intrinsics.checkNotNullExpressionValue(optString, "optString(...)");
            restart$done($act, $then, false, optString);
            return Unit.INSTANCE;
        }
        Toast.makeText($act, "Restarting openpilot: going offroad…", 0).show();
        long t0 = System.currentTimeMillis();
        DriveState$restart$1$wait$1 wait = new DriveState$restart$1$wait$1(t0, $main, $back, $act, $then);
        $main.postDelayed(wait, 1000L);
        return Unit.INSTANCE;
    }

    /* compiled from: DriveState.kt */
    public static final class OffroadBar {
        private final Activity act;
        private final TextView desc;
        private String lastLook;
        private boolean pending;
        private final Switch sw;
        private boolean syncing;
        private final TextView title;
        private final CarUi ui;
        private final LinearLayout view;

        public OffroadBar(Activity act) {
            Intrinsics.checkNotNullParameter(act, "act");
            this.act = act;
            this.ui = new CarUi(this.act, false, 2, null);
            TextView $this$title_u24lambda_u240 = new TextView(this.act);
            $this$title_u24lambda_u240.setTextColor(CarUi.Companion.getTEXT());
            $this$title_u24lambda_u240.setTextSize(17.0f);
            $this$title_u24lambda_u240.setTypeface(Typeface.DEFAULT_BOLD);
            $this$title_u24lambda_u240.setText("Offroad");
            this.title = $this$title_u24lambda_u240;
            TextView $this$desc_u24lambda_u241 = new TextView(this.act);
            $this$desc_u24lambda_u241.setTextColor(CarUi.Companion.getDIM());
            $this$desc_u24lambda_u241.setTextSize(13.0f);
            this.desc = $this$desc_u24lambda_u241;
            Switch $this$sw_u24lambda_u242 = new Switch(this.act);
            $this$sw_u24lambda_u242.setScaleX(1.15f);
            $this$sw_u24lambda_u242.setScaleY(1.15f);
            $this$sw_u24lambda_u242.setPadding(this.ui.dp(12), 0, 0, 0);
            this.sw = $this$sw_u24lambda_u242;
            this.lastLook = "";
            LinearLayout $this$view_u24lambda_u244 = new LinearLayout(this.act);
            $this$view_u24lambda_u244.setOrientation(0);
            $this$view_u24lambda_u244.setGravity(16);
            $this$view_u24lambda_u244.setPadding(this.ui.dp(16), this.ui.dp(4), this.ui.dp(12), this.ui.dp(4));
            $this$view_u24lambda_u244.setVisibility(8);
            $this$view_u24lambda_u244.setMinimumWidth(this.ui.dp(240));
            LinearLayout $this$view_u24lambda_u244_u24lambda_u243 = new LinearLayout(this.act);
            $this$view_u24lambda_u244_u24lambda_u243.setOrientation(1);
            $this$view_u24lambda_u244_u24lambda_u243.addView(this.title);
            $this$view_u24lambda_u244_u24lambda_u243.addView(this.desc);
            $this$view_u24lambda_u244.addView($this$view_u24lambda_u244_u24lambda_u243, new LinearLayout.LayoutParams(0, -2, 1.0f));
            $this$view_u24lambda_u244.addView(this.sw);
            this.view = $this$view_u24lambda_u244;
            this.sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.starpilot.starview.DriveState$OffroadBar$$ExternalSyntheticLambda3
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    DriveState.OffroadBar._init_$lambda$8(DriveState.OffroadBar.this, compoundButton, z);
                }
            });
        }

        public final LinearLayout getView() {
            return this.view;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void _init_$lambda$8(final OffroadBar this$0, CompoundButton compoundButton, boolean on) {
            if (this$0.syncing) {
                return;
            }
            if (on) {
                new AlertDialog.Builder(this$0.act).setTitle("Go offroad?").setMessage("openpilot stops all driving processes and will NOT drive until you switch this back off (Auto).").setPositiveButton("Go offroad", new DialogInterface.OnClickListener() { // from class: com.starpilot.starview.DriveState$OffroadBar$$ExternalSyntheticLambda0
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        this$0.send("offroad");
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() { // from class: com.starpilot.starview.DriveState$OffroadBar$$ExternalSyntheticLambda1
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        this$0.sync(true);
                    }
                }).setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.starpilot.starview.DriveState$OffroadBar$$ExternalSyntheticLambda2
                    @Override // android.content.DialogInterface.OnCancelListener
                    public final void onCancel(DialogInterface dialogInterface) {
                        this$0.sync(true);
                    }
                }).show();
            } else {
                this$0.send("default");
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void send(String mode) {
            this.pending = true;
            DriveState.INSTANCE.request(this.act, mode, new Function1() { // from class: com.starpilot.starview.DriveState$OffroadBar$$ExternalSyntheticLambda4
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    Unit send$lambda$9;
                    send$lambda$9 = DriveState.OffroadBar.send$lambda$9(DriveState.OffroadBar.this, ((Boolean) obj).booleanValue());
                    return send$lambda$9;
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit send$lambda$9(OffroadBar this$0, boolean it) {
            this$0.pending = false;
            this$0.sync(true);
            return Unit.INSTANCE;
        }

        public final void sync(boolean needed) {
            boolean forced = DriveState.INSTANCE.forcedOffroad();
            Boolean on = DriveState.INSTANCE.onroad();
            boolean z = true;
            boolean show = needed && (forced || Intrinsics.areEqual((Object) on, (Object) true));
            int vis = show ? 0 : 8;
            if (this.view.getVisibility() != vis) {
                this.view.setVisibility(vis);
            }
            if (show) {
                boolean eng = DriveState.INSTANCE.engaged();
                String look = forced + "|" + eng;
                if (!Intrinsics.areEqual(look, this.lastLook)) {
                    this.lastLook = look;
                    this.view.setBackground(this.ui.rounded(forced ? Color.rgb(70, 22, 28) : CarUi.Companion.getCARD_ON(), 12, Integer.valueOf(forced ? Color.rgb(201, 34, 49) : CarUi.Companion.getWARN())));
                    this.title.setText(forced ? "Forced offroad" : "Offroad");
                    this.desc.setText(forced ? "won't drive · tap to return" : eng ? "disengage first" : "tap to go offroad");
                }
                this.syncing = true;
                if (this.sw.isChecked() != forced) {
                    this.sw.setChecked(forced);
                }
                Switch r8 = this.sw;
                if (this.pending || (!forced && eng)) {
                    z = false;
                }
                r8.setEnabled(z);
                this.syncing = false;
            }
        }
    }
}
