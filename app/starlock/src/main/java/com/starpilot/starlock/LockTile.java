package com.starpilot.starlock;

import android.app.KeyguardManager;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.drawable.Icon;
import android.os.Handler;
import android.os.Looper;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import com.starpilot.starlock.CarLocks;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* compiled from: LockTile.kt */
public final class LockTile extends TileService {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion Companion = new Companion(null);
    private static volatile String busyCmd;
    private static volatile String note;
    private static volatile long noteUntil;
    private boolean listening;
    private final Handler main = new Handler(Looper.getMainLooper());

    /* compiled from: LockTile.kt */
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
            try {
                iArr[Outcome.FAILED.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    @Override // android.service.quicksettings.TileService
    public void onStartListening() {
        this.listening = true;
        render();
    }

    @Override // android.service.quicksettings.TileService
    public void onStopListening() {
        this.listening = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void redraw() {
        if (this.listening) {
            render();
        } else {
            Companion.refresh(this);
        }
    }

    @Override // android.service.quicksettings.TileService
    public void onClick() {
        if (busyCmd != null) {
            return;
        }
        final String cmd = Intrinsics.areEqual((Object) Companion.locked(this), (Object) true) ? "unlock" : "lock";
        KeyguardManager km = (KeyguardManager) getSystemService(KeyguardManager.class);
        if (!Intrinsics.areEqual(cmd, "unlock") || !km.isKeyguardLocked() || !km.isKeyguardSecure()) {
            go(cmd);
        } else {
            unlockAndRun(new Runnable() { // from class: com.starpilot.starlock.LockTile$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    LockTile.this.go(cmd);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void go(String cmd) {
        if (KeeperService.Companion.background(this)) {
            KeeperService.Companion.start(this);
        }
        busyCmd = cmd;
        note = null;
        render();
        boolean started = Door.INSTANCE.run(this, cmd, new Function1() { // from class: com.starpilot.starlock.LockTile$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit go$lambda$1;
                go$lambda$1 = LockTile.go$lambda$1((String) obj);
                return go$lambda$1;
            }
        }, new Function2() { // from class: com.starpilot.starlock.LockTile$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit go$lambda$3;
                go$lambda$3 = LockTile.go$lambda$3(LockTile.this, (Outcome) obj, (String) obj2);
                return go$lambda$3;
            }
        });
        if (!started) {
            busyCmd = null;
            redraw();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit go$lambda$1(String it) {
        Intrinsics.checkNotNullParameter(it, "it");
        AppLog.INSTANCE.log(it);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit go$lambda$3(final LockTile this$0, Outcome outcome, String msg) {
        String str;
        Intrinsics.checkNotNullParameter(outcome, "outcome");
        Intrinsics.checkNotNullParameter(msg, "msg");
        busyCmd = null;
        switch (WhenMappings.$EnumSwitchMapping$0[outcome.ordinal()]) {
            case 1:
                str = "Car confirmed ✓";
                break;
            case 2:
                str = "Sent, car didn't confirm";
                break;
            case 3:
                str = StringsKt.take(StringsKt.substringBefore(msg, "  (", msg), 40);
                break;
            default:
                throw new NoWhenBranchMatchedException();
        }
        note = str;
        noteUntil = System.currentTimeMillis() + (outcome == Outcome.CONFIRMED ? 4000 : 8000);
        this$0.redraw();
        this$0.main.postDelayed(new Runnable() { // from class: com.starpilot.starlock.LockTile$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this$0.redraw();
            }
        }, (noteUntil - System.currentTimeMillis()) + 100);
        return Unit.INSTANCE;
    }

    /* VF: from Vineflower (jadx: Code restructure failed;Removed duplicated region;incorrect) */
    private final void render() {
        String var15 = null; String var16 = null; // declarations Vineflower left out
        Tile var9 = this.getQsTile();
        if (var9 != null) {
            Boolean var4 = Companion.locked((Context)this);
            boolean var2 = true;
            boolean var3 = Intrinsics.areEqual(var4, true);
            String var7 = busyCmd;
            int var1;
            if (var3) {
                var1 = 2;
            } else {
                var1 = 1;
            }

            var9.setState(var1);
            Context var5 = (Context)this;
            String var13;
            if (var7 == null) {
                if (var3) {
                    var13 = "lock";
                } else {
                    var13 = "unlock";
                }
            } else {
                var13 = var7;
            }

            if (Intrinsics.areEqual(var13, "lock")) {
                var1 = R.drawable.ic_lock;
            } else {
                var1 = R.drawable.ic_unlock;
            }

            label91: {
                var9.setIcon(Icon.createWithResource(var5, var1));
                String var14;
                if (Intrinsics.areEqual(var7, "lock")) {
                    var14 = "Locking…";
                } else if (Intrinsics.areEqual(var7, "unlock")) {
                    var14 = "Unlocking…";
                } else {
                    if (Companion.locked((Context)this) != null) {
                        if (var3) {
                            var15 = "Locked";
                        } else {
                            var15 = "Unlocked";
                        }
                        break label91;
                    }

                    var14 = "StarLock";
                }

                var15 = var14;
            }

            Object var8;
            label84: {
                var9.setLabel(var15);
                var16 = note;
                var8 = null;
                if (var16 != null) {
                    boolean var11;
                    if (System.currentTimeMillis() < noteUntil) {
                        var11 = true;
                    } else {
                        var11 = false;
                    }

                    if (var11) {
                        break label84;
                    }
                }

                var16 = null;
            }

            CarLocks.State var6 = CarLocks.INSTANCE.state((Context)this);
            String var17 = (String)var8;
            if (var6 != null) {
                if (!var6.getLive()) {
                    var6 = null;
                }

                var17 = (String)var8;
                if (var6 != null) {
                    String var19 = CarLocks.INSTANCE.summary(var6);
                    var17 = (String)var8;
                    if (var19 != null) {
                        boolean var12;
                        if (!Intrinsics.areEqual(var19, "Locked") && !Intrinsics.areEqual(var19, "Unlocked")) {
                            var12 = var2;
                        } else {
                            var12 = false;
                        }

                        var17 = (String)var8;
                        if (var12) {
                            var17 = var19;
                        }
                    }
                }
            }

            if (var7 != null) {
                var16 = "waiting for the car";
            } else if (var16 == null) {
                if (var17 == null) {
                    if (var3) {
                        var16 = "Tap to unlock";
                    } else {
                        var16 = "Tap to lock";
                    }
                } else {
                    var16 = var17;
                }
            }

            var9.setSubtitle(var16);
            CharSequence var18 = var9.getLabel();
            var9.setContentDescription(var18 + ". " + var16);
            var9.updateTile();
        }
    }

    /* compiled from: LockTile.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final Boolean locked(Context ctx) {
            Intrinsics.checkNotNullParameter(ctx, "ctx");
            String it = Door.INSTANCE.prefs(ctx).getString("state", null);
            if (it != null) {
                return Boolean.valueOf(Intrinsics.areEqual(it, "locked"));
            }
            return null;
        }

        public final void remember(Context ctx, String cmd) {
            Intrinsics.checkNotNullParameter(ctx, "ctx");
            Intrinsics.checkNotNullParameter(cmd, "cmd");
            Door.INSTANCE.prefs(ctx).edit().putString("state", Intrinsics.areEqual(cmd, "lock") ? "locked" : "unlocked").apply();
            refresh(ctx);
        }

        public final void refresh(Context ctx) {
            Intrinsics.checkNotNullParameter(ctx, "ctx");
            try {
                Result.Companion companion = Result.Companion;
                Companion companion2 = this;
                TileService.requestListeningState(ctx, new ComponentName(ctx, (Class<?>) LockTile.class));
                KtCompat.resultOf(Unit.INSTANCE);
            } catch (Throwable th) {
                Result.Companion companion3 = Result.Companion;
                KtCompat.resultOf(ResultKt.createFailure(th));
            }
        }
    }
}
