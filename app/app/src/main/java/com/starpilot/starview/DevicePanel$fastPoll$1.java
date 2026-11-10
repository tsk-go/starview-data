package com.starpilot.starview;

import android.app.Activity;
import android.os.Handler;
import com.starpilot.starview.GalaxyApi;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.json.JSONObject;

/* compiled from: DevicePanels.kt */
public final class DevicePanel$fastPoll$1 implements Runnable {
    final /* synthetic */ DevicePanel this$0;

    DevicePanel$fastPoll$1(DevicePanel $receiver) {
        this.this$0 = $receiver;
    }

    @Override // java.lang.Runnable
    public void run() {
        boolean z;
        DataClient dc;
        Boolean bool;
        final String h;
        Handler handler;
        z = this.this$0.fastPolling;
        if (z) {
            dc = this.this$0.getData();
            bool = this.this$0.seven;
            if (Intrinsics.areEqual((Object) bool, (Object) false) || dc == null || !dc.getConnected()) {
                h = this.this$0.getHost();
                if (h == null) {
                    handler = this.this$0.fastHandler;
                    handler.postDelayed(this, 5000L);
                    return;
                } else {
                    final DevicePanel devicePanel = this.this$0;
                    new Thread(new Runnable() { // from class: com.starpilot.starview.DevicePanel$fastPoll$1$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            DevicePanel$fastPoll$1.run$lambda$4(DevicePanel$fastPoll$1.this, devicePanel, h);
                        }
                    }).start();
                    return;
                }
            }
            final DevicePanel devicePanel2 = this.this$0;
            dc.request("software_status", kotlin.collections.MapsKt.emptyMap(), new Function1() { // from class: com.starpilot.starview.DevicePanel$fastPoll$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    Unit run$lambda$1;
                    run$lambda$1 = DevicePanel$fastPoll$1.run$lambda$1(devicePanel2, again, (JSONObject) obj);
                    return run$lambda$1;
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit run$lambda$1(final DevicePanel this$0, final DevicePanel$fastPoll$1 $again, final JSONObject r) {
        Handler handler;
        Intrinsics.checkNotNullParameter(r, "r");
        handler = this$0.fastHandler;
        handler.post(new Runnable() { // from class: com.starpilot.starview.DevicePanel$fastPoll$1$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                DevicePanel$fastPoll$1.run$lambda$1$lambda$0(r, this$0, $again);
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void run$lambda$1$lambda$0(JSONObject $r, DevicePanel this$0, DevicePanel$fastPoll$1 $again) {
        Handler handler;
        boolean z;
        Handler handler2;
        JSONObject jSONObject;
        long j;
        JSONObject jSONObject2;
        if ($r.optBoolean("ok")) {
            this$0.seven = true;
            this$0.fastStatus = FastUpdate.INSTANCE.from70($r);
        } else {
            String optString = $r.optString("error");
            Intrinsics.checkNotNullExpressionValue(optString, "optString(...)");
            if (StringsKt.startsWith(optString, "unknown action", false)) {
                this$0.seven = false;
                handler = this$0.fastHandler;
                handler.post($again);
                return;
            }
        }
        this$0.refresh(true);
        z = this$0.fastPolling;
        if (z) {
            handler2 = this$0.fastHandler;
            DevicePanel$fastPoll$1 devicePanel$fastPoll$1 = $again;
            FastUpdate fastUpdate = FastUpdate.INSTANCE;
            jSONObject = this$0.fastStatus;
            if (!fastUpdate.running(jSONObject)) {
                jSONObject2 = this$0.fastStatus;
                if (!Intrinsics.areEqual(jSONObject2 != null ? jSONObject2.optString("stage") : null, "rebooting")) {
                    j = 10000;
                    handler2.postDelayed(devicePanel$fastPoll$1, j);
                }
            }
            j = 1000;
            handler2.postDelayed(devicePanel$fastPoll$1, j);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void run$lambda$4(final DevicePanel$fastPoll$1 this$0, final DevicePanel this$1, String $h) {
        Object m1191constructorimpl;
        Handler handler;
        Activity activity;
        try {
            Result.Companion companion = Result.Companion;
            GalaxyApi.Companion companion2 = GalaxyApi.Companion;
            activity = this$1.act;
            m1191constructorimpl = KtCompat.resultOf(new JSONObject(companion2.get(activity, $h).get("/api/update/fast/status")));
        } catch (Throwable th) {
            Result.Companion companion3 = Result.Companion;
            m1191constructorimpl = KtCompat.resultOf(ResultKt.createFailure(th));
        }
        if (KtCompat.isFailure(m1191constructorimpl)) {
            m1191constructorimpl = null;
        }
        final JSONObject st = (JSONObject) m1191constructorimpl;
        handler = this$1.fastHandler;
        handler.post(new Runnable() { // from class: com.starpilot.starview.DevicePanel$fastPoll$1$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                DevicePanel$fastPoll$1.run$lambda$4$lambda$3(st, this$1, this$0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void run$lambda$4$lambda$3(JSONObject $st, DevicePanel this$0, DevicePanel$fastPoll$1 this$1) {
        boolean z;
        Handler handler;
        JSONObject jSONObject;
        if ($st != null) {
            this$0.fastStatus = $st;
        }
        this$0.refresh(true);
        z = this$0.fastPolling;
        if (z) {
            handler = this$0.fastHandler;
            DevicePanel$fastPoll$1 devicePanel$fastPoll$1 = this$1;
            FastUpdate fastUpdate = FastUpdate.INSTANCE;
            jSONObject = this$0.fastStatus;
            handler.postDelayed(devicePanel$fastPoll$1, fastUpdate.running(jSONObject) ? 1000L : 60000L);
        }
    }
}
