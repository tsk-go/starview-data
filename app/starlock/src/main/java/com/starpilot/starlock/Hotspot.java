package com.starpilot.starlock;

import android.content.Context;
import android.content.ContextWrapper;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.provider.Settings;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.lsposed.hiddenapibypass.HiddenApiBypass;
import rikka.shizuku.Shizuku;
import rikka.shizuku.ShizukuBinderWrapper;
import rikka.shizuku.SystemServiceHelper;
import kotlin.jvm.internal.Ref.ObjectRef;

/* compiled from: Hotspot.kt */
public final class Hotspot {
    private static final int TETHERING_WIFI = 0;
    public static final Hotspot INSTANCE = new Hotspot();
    private static final AtomicBoolean running = new AtomicBoolean(false);

    /* renamed from: exempt$delegate, reason: from kotlin metadata */
    private static final Lazy exempt = LazyKt.lazy(new Function0() { // from class: com.starpilot.starlock.Hotspot$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            boolean exempt_delegate$lambda$1;
            exempt_delegate$lambda$1 = Hotspot.exempt_delegate$lambda$1();
            return Boolean.valueOf(exempt_delegate$lambda$1);
        }
    });

    private Hotspot() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean exempt_delegate$lambda$1() {
        Object m61constructorimpl;
        Hotspot hotspot = INSTANCE;
        try {
            Result.Companion companion = Result.Companion;
            m61constructorimpl = KtCompat.resultOf(Boolean.valueOf(HiddenApiBypass.addHiddenApiExemptions("L")));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            m61constructorimpl = KtCompat.resultOf(ResultKt.createFailure(th));
        }
        return KtCompat.isSuccess(m61constructorimpl);
    }

    private final boolean getExempt() {
        return ((Boolean) exempt.getValue()).booleanValue();
    }

    public final boolean enabled(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        return Door.INSTANCE.prefs(ctx).getBoolean("hotspot", false);
    }

    public final boolean canWrite(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        return Settings.System.canWrite(ctx);
    }

    public final boolean shizukuRunning() {
        Object m61constructorimpl;
        try {
            Result.Companion companion = Result.Companion;
            Hotspot hotspot = this;
            m61constructorimpl = KtCompat.resultOf(Boolean.valueOf(Shizuku.pingBinder()));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            m61constructorimpl = KtCompat.resultOf(ResultKt.createFailure(th));
        }
        if (KtCompat.isFailure(m61constructorimpl)) {
            m61constructorimpl = false;
        }
        return ((Boolean) m61constructorimpl).booleanValue();
    }

    public final boolean shizukuAllowed() {
        Object m61constructorimpl;
        if (!shizukuRunning()) {
            return false;
        }
        try {
            Result.Companion companion = Result.Companion;
            Hotspot hotspot = this;
            m61constructorimpl = KtCompat.resultOf(Boolean.valueOf(Shizuku.checkSelfPermission() == 0));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            m61constructorimpl = KtCompat.resultOf(ResultKt.createFailure(th));
        }
        if (KtCompat.isFailure(m61constructorimpl)) {
            m61constructorimpl = false;
        }
        return ((Boolean) m61constructorimpl).booleanValue();
    }

    public final Boolean isOn(Context ctx) {
        Object m61constructorimpl;
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        try {
            Result.Companion companion = Result.Companion;
            Hotspot $this$isOn_u24lambda_u244 = this;
            $this$isOn_u24lambda_u244.getExempt();
            WifiManager wm = (WifiManager) ctx.getApplicationContext().getSystemService(WifiManager.class);
            Object invoke = WifiManager.class.getMethod("isWifiApEnabled", new Class[0]).invoke(wm, new Object[0]);
            Intrinsics.checkNotNull(invoke, "null cannot be cast to non-null type kotlin.Boolean");
            m61constructorimpl = KtCompat.resultOf(Boolean.valueOf(((Boolean) invoke).booleanValue()));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            m61constructorimpl = KtCompat.resultOf(ResultKt.createFailure(th));
        }
        if (KtCompat.isFailure(m61constructorimpl)) {
            m61constructorimpl = null;
        }
        return (Boolean) m61constructorimpl;
    }

    public final void onComma(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        if (enabled(ctx)) {
            turnOn$default(this, ctx, "the comma connected", null, 4, null);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void turnOn$default(Hotspot hotspot, Context context, String str, Function1 function1, int i, Object obj) {
        if ((i & 4) != 0) {
            function1 = null;
        }
        hotspot.turnOn(context, str, function1);
    }

    public final void turnOn(Context context, final String why, final Function1<? super String, Unit> done) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(why, "why");
        final Context ctx = context.getApplicationContext();
        if (running.compareAndSet(false, true)) {
            new Thread(new Runnable() { // from class: com.starpilot.starlock.Hotspot$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    Hotspot.turnOn$lambda$5(ctx, why, done);
                }
            }).start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void turnOn$lambda$5(Context $ctx, String $why, Function1 $done) {
        try {
            Hotspot hotspot = INSTANCE;
            Intrinsics.checkNotNull($ctx);
            String msg = hotspot.attempt($ctx);
            running.set(false);
            AppLog.INSTANCE.log("Hotspot (" + $why + "): " + msg);
            if ($done != null) {
                $done.invoke(msg);
            }
        } catch (Throwable th) {
            running.set(false);
            throw th;
        }
    }

    private final String attempt(Context ctx) {
        String why;
        Object m61constructorimpl;
        String message;
        if (Intrinsics.areEqual((Object) isOn(ctx), (Object) true)) {
            return "already on";
        }
        if (canWrite(ctx)) {
            String r = start(ctx.getSystemService("tethering"));
            if (r == null || Intrinsics.areEqual((Object) isOn(ctx), (Object) true)) {
                return "turned on";
            }
            why = r;
        } else {
            why = "\"Modify system settings\" not allowed";
        }
        if (!shizukuAllowed()) {
            return "couldn't: " + why + (shizukuRunning() ? "; Shizuku not allowed yet" : "; Shizuku isn't running");
        }
        try {
            Result.Companion companion = Result.Companion;
            Hotspot $this$attempt_u24lambda_u246 = this;
            m61constructorimpl = KtCompat.resultOf($this$attempt_u24lambda_u246.start($this$attempt_u24lambda_u246.shellTethering(ctx)));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            m61constructorimpl = KtCompat.resultOf(ResultKt.createFailure(th));
        }
        Throwable it = KtCompat.exceptionOrNull(m61constructorimpl);
        if (it != null) {
            Throwable cause = it.getCause();
            if (cause == null || (message = cause.getMessage()) == null) {
                message = it.getMessage();
            }
            m61constructorimpl = "Shizuku: " + message;
        }
        String r2 = (String) m61constructorimpl;
        if (r2 == null || Intrinsics.areEqual((Object) isOn(ctx), (Object) true)) {
            return "turned on (Shizuku)";
        }
        return "couldn't: " + why + "; Shizuku: " + r2;
    }

    private final Object shellTethering(final Context ctx) {
        getExempt();
        Class cls = Class.forName("android.net.TetheringManager");
        ContextWrapper contextWrapper = new ContextWrapper(ctx) { // from class: com.starpilot.starlock.Hotspot$shellTethering$shellCtx$1
            @Override // android.content.ContextWrapper, android.content.Context
            public String getOpPackageName() {
                return "com.android.shell";
            }

            @Override // android.content.ContextWrapper, android.content.Context
            public String getAttributionTag() {
                return null;
            }
        };
        final IBinder binder = SystemServiceHelper.getSystemService("tethering");
        if (binder == null) {
            throw new IllegalStateException("no tethering service".toString());
        }
        Supplier supplier = new Supplier() { // from class: com.starpilot.starlock.Hotspot$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                IBinder shellTethering$lambda$8;
                shellTethering$lambda$8 = Hotspot.shellTethering$lambda$8(binder);
                return shellTethering$lambda$8;
            }
        };
        Constructor $this$shellTethering_u24lambda_u249 = cls.getDeclaredConstructor(Context.class, Supplier.class);
        $this$shellTethering_u24lambda_u249.setAccessible(true);
        Object newInstance = $this$shellTethering_u24lambda_u249.newInstance(contextWrapper, supplier);
        Intrinsics.checkNotNullExpressionValue(newInstance, "newInstance(...)");
        return newInstance;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final IBinder shellTethering$lambda$8(IBinder $binder) {
        return new ShizukuBinderWrapper($binder);
    }

    /* recovered from bytecode (Vineflower's version had the catch regions wrong) */
    private final String start(Object var1) {
        if (var1 == null) {
            return "no tethering service";
        }
        this.getExempt();
        try {
            Class var6 = Class.forName("android.net.TetheringManager");
            Class var27 = Class.forName("android.net.TetheringManager$TetheringRequest$Builder");
            Class var7 = Class.forName("android.net.TetheringManager$TetheringRequest");
            Class var8 = Class.forName("android.net.TetheringManager$StartTetheringCallback");
            Object var3 = var27.getMethod("build").invoke(var27.getConstructor(int.class).newInstance(0));
            CountDownLatch var4 = new CountDownLatch(1);
            ObjectRef var5 = new ObjectRef();
            var5.element = "no answer from Android";
            ObjectRef var28 = new ObjectRef();
            var28.element = Proxy.newProxyInstance(var8.getClassLoader(), new Class[]{var8}, new Hotspot$$ExternalSyntheticLambda3(var5, var4, var28));
            Method var32 = var6.getMethod("startTethering", var7, Executor.class, var8);
            Hotspot$$ExternalSyntheticLambda4 var33 = new Hotspot$$ExternalSyntheticLambda4();
            Object cb;
            if (var28.element == null) {
                Intrinsics.throwUninitializedPropertyAccessException("cb");
                cb = Unit.INSTANCE;
            } else {
                cb = var28.element;
            }
            var32.invoke(var1, var3, var33, cb);
            var4.await(6L, TimeUnit.SECONDS);
            return (String)var5.element;
        } catch (Throwable var2) {
            Throwable t = null;
            if (var2 instanceof InvocationTargetException) {
                t = ((InvocationTargetException)var2).getTargetException();
            }
            if (t == null) {
                t = var2;
            }
            return t.getClass().getSimpleName() + ": " + t.getMessage();
        }
    }

    /* VF: from Vineflower (jadx: Type inference failed) */
    public static final Object start$lambda$10(ObjectRef var0, CountDownLatch var1, ObjectRef var2, Object var3, Method var4, Object[] var5) {
        String var8 = var4.getName();
        var3 = null;
        Hotspot var16 = null;
        if (var8 != null) {
            int var6 = var8.hashCode();
            boolean var7 = false;
            switch (var6) {
                case -1785200688:
                    if (var8.equals("onTetheringStarted")) {
                        var0.element = null;
                        var1.countDown();
                        return var3;
                    }
                    break;
                case -1776922004:
                    if (var8.equals("toString")) {
                        return "StarLockTetheringCallback";
                    }
                    break;
                case -1295482945:
                    if (var8.equals("equals")) {
                        Object var11 = var16;
                        if (var5 != null) {
                            var11 = ArraysKt.getOrNull(var5, 0);
                        }

                        Object var12;
                        if (var2.element == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("cb");
                            var12 = Unit.INSTANCE;
                        } else {
                            var12 = var2.element;
                        }

                        if (var11 == var12) {
                            var7 = true;
                        }

                        return var7;
                    }
                    break;
                case 147696667:
                    if (var8.equals("hashCode")) {
                        Object var10;
                        if (var2.element == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("cb");
                            var10 = Unit.INSTANCE;
                        } else {
                            var10 = var2.element;
                        }

                        return System.identityHashCode(var10);
                    }
                    break;
                case 1908223758:
                    if (var8.equals("onTetheringFailed")) {
                        var16 = INSTANCE;
                        Object var13;
                        if (var5 != null) {
                            var13 = ArraysKt.getOrNull(var5, 0);
                        } else {
                            var13 = null;
                        }

                        if (var13 instanceof Integer) {
                            var6 = (Integer)var13;
                        } else {
                            var6 = -1;
                        }

                        var0.element = var16.error(var6);
                        var1.countDown();
                        return var3;
                    }
            }
        }

        return var3;
    }

    private final String error(int code) {
        switch (code) {
            case 11:
            case 13:
                return "the carrier's hotspot check said no";
            case 12:
            default:
                return "Android error " + code;
            case 14:
                return "not allowed (needs Shizuku)";
        }
    }

    // recovered from Vineflower (R8 lambda body jadx inlined)
   static final void start$lambda$11(Runnable var0) {
       var0.run();
    }
}
