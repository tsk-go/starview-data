package com.starpilot.starlock;

import android.widget.TextView;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: AutoLockActivity.kt */
/* synthetic */ class AutoLockActivity$onCreate$stepper$4 extends FunctionReferenceImpl implements Function0 {
    final /* synthetic */ String $key;
    final /* synthetic */ String $unit;
    final /* synthetic */ TextView $v;
    final /* synthetic */ AutoLockActivity this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    AutoLockActivity$onCreate$stepper$4(TextView textView, AutoLockActivity autoLockActivity, String str, String str2) {
        super(0, Intrinsics.Kotlin.class, "show", "onCreate$stepper$show(Landroid/widget/TextView;Lcom/starpilot/starlock/AutoLockActivity;Ljava/lang/String;Ljava/lang/String;)V", 0);
        this.$v = textView;
        this.this$0 = autoLockActivity;
        this.$key = str;
        this.$unit = str2;
    }

    @Override // kotlin.jvm.functions.Function0
    public /* bridge */ Object invoke() {
        invoke2();
        return Unit.INSTANCE;
    }

    /* renamed from: invoke, reason: avoid collision after fix types in other method */
    public final void invoke2() {
        AutoLockActivity.onCreate$stepper$show(this.$v, this.this$0, this.$key, this.$unit);
    }
}
