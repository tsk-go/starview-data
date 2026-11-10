package com.starpilot.starview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

/* compiled from: SettingsLauncher.kt */
public final class SwipePager extends ViewGroup {
    private ValueAnimator anim;
    private float downX;
    private float downY;
    private final int homeIndex;
    private int index;
    private int mode;
    private Function1<? super Integer, Unit> onPage;
    private Function0<Unit> onSwipeUp;
    private final int slop;
    private int startScroll;

    /* VF: from Vineflower (jadx: ) */
    public SwipePager(Context var1, int var2) {
        super(var1);
        Intrinsics.checkNotNullParameter(var1, "ctx");
        this.homeIndex = var2;
        this.index = this.homeIndex;
        this.slop = ViewConfiguration.get(var1).getScaledTouchSlop();
    }

    public final int getIndex() {
        return this.index;
    }

    public final Function1<Integer, Unit> getOnPage() {
        return (Function1) this.onPage;
    }

    public final void setOnPage(Function1<? super Integer, Unit> function1) {
        this.onPage = function1;
    }

    public final Function0<Unit> getOnSwipeUp() {
        return this.onSwipeUp;
    }

    public final void setOnSwipeUp(Function0<Unit> function0) {
        this.onSwipeUp = function0;
    }

    @Override // android.view.View
    protected void onMeasure(int w, int h) {
        int ws = View.MeasureSpec.getSize(w);
        int hs = View.MeasureSpec.getSize(h);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            getChildAt(i).measure(View.MeasureSpec.makeMeasureSpec(ws, 1073741824), View.MeasureSpec.makeMeasureSpec(hs, 1073741824));
        }
        setMeasuredDimension(ws, hs);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int w = r - l;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            getChildAt(i).layout(i * w, 0, (i + 1) * w, b - t);
        }
        if (this.anim != null || this.mode == 1) {
            return;
        }
        scrollTo(this.index * w, 0);
    }

    public static /* synthetic */ void go$default(SwipePager swipePager, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = true;
        }
        swipePager.go(i, z);
    }

    public final void go(int i, boolean animate) {
        Function1<? super Integer, Unit> function1;
        int to = RangesKt.coerceIn(i, 0, getChildCount() - 1);
        boolean changed = to != this.index;
        this.index = to;
        ValueAnimator valueAnimator = this.anim;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        if (!animate || getWidth() == 0) {
            scrollTo(this.index * getWidth(), 0);
        } else {
            ValueAnimator $this$go_u24lambda_u241 = ValueAnimator.ofInt(getScrollX(), this.index * getWidth());
            $this$go_u24lambda_u241.setDuration(220L);
            $this$go_u24lambda_u241.setInterpolator(new DecelerateInterpolator());
            $this$go_u24lambda_u241.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.starpilot.starview.SwipePager$$ExternalSyntheticLambda0
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                    SwipePager.go$lambda$1$lambda$0(SwipePager.this, valueAnimator2);
                }
            });
            $this$go_u24lambda_u241.addListener(new AnimatorListenerAdapter() { // from class: com.starpilot.starview.SwipePager$go$1$2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator a) {
                    Intrinsics.checkNotNullParameter(a, "a");
                    SwipePager.this.anim = null;
                }
            });
            $this$go_u24lambda_u241.start();
            this.anim = $this$go_u24lambda_u241;
        }
        if (!changed || (function1 = this.onPage) == null) {
            return;
        }
        function1.invoke(Integer.valueOf(this.index));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void go$lambda$1$lambda$0(SwipePager this$0, ValueAnimator it) {
        Intrinsics.checkNotNullParameter(it, "it");
        Object animatedValue = it.getAnimatedValue();
        Intrinsics.checkNotNull(animatedValue, "null cannot be cast to non-null type kotlin.Int");
        this$0.scrollTo(((Number) animatedValue).intValue(), 0);
    }

    /* VF: from Vineflower (jadx: ) */
    public boolean onInterceptTouchEvent(MotionEvent var1) {
        Intrinsics.checkNotNullParameter(var1, "e");
        switch (var1.getActionMasked()) {
            case 0:
                this.downX = var1.getX();
                this.downY = var1.getY();
                this.startScroll = this.getScrollX();
                this.mode = 0;
            case 1:
            default:
                break;
            case 2:
                float var2 = var1.getX() - this.downX;
                float var3 = var1.getY() - this.downY;
                if (this.mode == 0 && Math.abs(var2) > this.slop && Math.abs(var2) > Math.abs(var3)) {
                    this.mode = 1;
                    ValueAnimator var4 = this.anim;
                    if (var4 != null) {
                        var4.cancel();
                    }

                    this.anim = null;
                    return true;
                }

                if (this.mode == 0 && -var3 > this.slop * 2 && Math.abs(var3) > Math.abs(var2) * 1.5F && this.index == this.homeIndex) {
                    this.mode = 2;
                    return true;
                }
        }

        return false;
    }

    /* VF: from Vineflower (jadx: ) */
    public boolean onTouchEvent(MotionEvent var1) {
        Intrinsics.checkNotNullParameter(var1, "e");
        float var3 = var1.getX() - this.downX;
        float var2 = var1.getY() - this.downY;
        switch (var1.getActionMasked()) {
            case 0:
                this.downX = var1.getX();
                this.downY = var1.getY();
                this.startScroll = this.getScrollX();
                this.mode = 0;
                return true;
            case 1:
            case 3:
                if (this.mode == 1) {
                    int var9;
                    if (var3 < -this.getWidth() / 6.0F) {
                        var9 = this.index + 1;
                    } else if (var3 > this.getWidth() / 6.0F) {
                        var9 = this.index - 1;
                    } else {
                        var9 = this.index;
                    }

                    go$default(this, var9, false, 2, null);
                } else if (this.mode == 2 && -var2 > this.getHeight() / 8.0F) {
                    Function0 var6 = this.onSwipeUp;
                    if (var6 != null) {
                        var6.invoke();
                    }
                }

                this.mode = 0;
                break;
            case 2:
                if (this.mode == 0) {
                    if (Math.abs(var3) > this.slop && Math.abs(var3) > Math.abs(var2)) {
                        this.mode = 1;
                        ValueAnimator var5 = this.anim;
                        if (var5 != null) {
                            var5.cancel();
                        }

                        this.anim = null;
                    } else if (-var2 > this.slop * 2 && Math.abs(var2) > Math.abs(var3) * 1.5F && this.index == this.homeIndex) {
                        this.mode = 2;
                    }
                } else if (this.mode == 1) {
                    int var4 = (this.getChildCount() - 1) * this.getWidth();
                    var3 = this.startScroll - var3;
                    if (var3 < 0.0F) {
                        var2 = var3 / 3.0F;
                    } else {
                        var2 = var3;
                        if (var3 > var4) {
                            var2 = var4 + (var3 - var4) / 3.0F;
                        }
                    }

                    this.scrollTo((int)var2, 0);
                }
        }

        return true;
    }
}
