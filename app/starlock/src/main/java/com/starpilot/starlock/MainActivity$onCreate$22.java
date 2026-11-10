package com.starpilot.starlock;

import android.widget.TextView;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MainActivity.kt */
/* synthetic */ class MainActivity$onCreate$22 extends FunctionReferenceImpl implements Function0 {
    final /* synthetic */ TextView $hotState;
    final /* synthetic */ MainActivity this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    MainActivity$onCreate$22(TextView textView, MainActivity mainActivity) {
        super(0, Intrinsics.Kotlin.class, "showHot", "onCreate$showHot(Landroid/widget/TextView;Lcom/starpilot/starlock/MainActivity;)V", 0);
        this.$hotState = textView;
        this.this$0 = mainActivity;
    }

    @Override // kotlin.jvm.functions.Function0
    public /* bridge */ Object invoke() {
        invoke2();
        return Unit.INSTANCE;
    }

    /* renamed from: invoke, reason: avoid collision after fix types in other method */
    public final void invoke2() {
        MainActivity.onCreate$showHot(this.$hotState, this.this$0);
    }
}
