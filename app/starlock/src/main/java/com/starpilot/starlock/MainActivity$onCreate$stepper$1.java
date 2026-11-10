package com.starpilot.starlock;

import android.widget.TextView;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MainActivity.kt */
/* synthetic */ class MainActivity$onCreate$stepper$1 extends FunctionReferenceImpl implements Function0 {
    final /* synthetic */ Function0<Integer> $get;
    final /* synthetic */ Function1<Integer, String> $show;
    final /* synthetic */ TextView $v;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    MainActivity$onCreate$stepper$1(TextView textView, Function1<? super Integer, String> function1, Function0<Integer> function0) {
        super(0, Intrinsics.Kotlin.class, "refresh", "onCreate$stepper$refresh(Landroid/widget/TextView;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function0;)V", 0);
        this.$v = textView;
        this.$show = (Function1) function1;
        this.$get = function0;
    }

    @Override // kotlin.jvm.functions.Function0
    public /* bridge */ Object invoke() {
        invoke2();
        return Unit.INSTANCE;
    }

    /* renamed from: invoke, reason: avoid collision after fix types in other method */
    public final void invoke2() {
        MainActivity.onCreate$stepper$refresh(this.$v, this.$show, this.$get);
    }
}
