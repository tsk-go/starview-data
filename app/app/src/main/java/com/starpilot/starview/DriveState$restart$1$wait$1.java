package com.starpilot.starview;

import android.app.Activity;
import android.os.Handler;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.json.JSONObject;

/* compiled from: DriveState.kt */
public final class DriveState$restart$1$wait$1 implements Runnable {
    final /* synthetic */ Activity $act;
    final /* synthetic */ String $back;
    final /* synthetic */ Handler $main;
    final /* synthetic */ long $t0;
    final /* synthetic */ Function1<Boolean, Unit> $then;

    /* JADX WARN: Multi-variable type inference failed */
    DriveState$restart$1$wait$1(long $t0, Handler $main, String $back, Activity $act, Function1<? super Boolean, Unit> function1) {
        this.$t0 = $t0;
        this.$main = $main;
        this.$back = $back;
        this.$act = $act;
        this.$then = function1;
    }

    @Override // java.lang.Runnable
    public void run() {
        boolean off = Intrinsics.areEqual((Object) DriveState.INSTANCE.onroad(), (Object) false);
        long waited = System.currentTimeMillis() - this.$t0;
        if (!off && waited < 25000) {
            this.$main.postDelayed(this, 500L);
            return;
        }
        Handler handler = this.$main;
        final String str = this.$back;
        final Activity activity = this.$act;
        final Function1<Boolean, Unit> function1 = this.$then;
        handler.postDelayed(new Runnable() { // from class: com.starpilot.starview.DriveState$restart$1$wait$1$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                DriveState$restart$1$wait$1.run$lambda$1(str, activity, function1);
            }
        }, off ? 3000L : 0L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void run$lambda$1(final String $back, final Activity $act, final Function1 $then) {
        DataClient dd = Link.INSTANCE.getData();
        if (dd == null || !dd.getConnected()) {
            DriveState.restart$done($act, $then, false, "Lost the comma - set the drive state back to " + (Intrinsics.areEqual($back, "onroad") ? "Onroad" : "Auto") + " in Settings");
        } else {
            dd.request("drive_state", MapsKt.mapOf(TuplesKt.to("mode", $back)), new Function1() { // from class: com.starpilot.starview.DriveState$restart$1$wait$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    Unit run$lambda$1$lambda$0;
                    run$lambda$1$lambda$0 = DriveState$restart$1$wait$1.run$lambda$1$lambda$0($back, $act, $then, (JSONObject) obj);
                    return run$lambda$1$lambda$0;
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit run$lambda$1$lambda$0(String $back, Activity $act, Function1 $then, JSONObject a2) {
        Intrinsics.checkNotNullParameter(a2, "a2");
        if (a2.optBoolean("ok")) {
            DriveState.restart$done($act, $then, true, "openpilot is starting again (" + (Intrinsics.areEqual($back, "onroad") ? "Onroad" : "Auto") + ") - the big model reloads in a minute or so");
        } else {
            DriveState.restart$done($act, $then, false, "Couldn't switch back: " + a2.optString("error", "rejected") + " - set it in Settings › Drive state");
        }
        return Unit.INSTANCE;
    }
}
