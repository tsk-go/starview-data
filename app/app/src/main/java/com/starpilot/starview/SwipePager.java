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

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SwipePager(Context ctx, int homeIndex) {
        super(ctx);
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        this.homeIndex = homeIndex;
        this.index = this.homeIndex;
        this.slop = ViewConfiguration.get(ctx).getScaledTouchSlop();
    }

    public final int getIndex() {
        return this.index;
    }

    public final Function1<Integer, Unit> getOnPage() {
        return this.onPage;
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
        this$0.scrollTo(((Integer) animatedValue).intValue(), 0);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent e) {
        Intrinsics.checkNotNullParameter(e, "e");
        switch (e.getActionMasked()) {
            case 0:
                this.downX = e.getX();
                this.downY = e.getY();
                this.startScroll = getScrollX();
                this.mode = 0;
                return false;
            case 1:
            default:
                return false;
            case 2:
                float dx = e.getX() - this.downX;
                float dy = e.getY() - this.downY;
                if (this.mode == 0 && Math.abs(dx) > this.slop && Math.abs(dx) > Math.abs(dy)) {
                    this.mode = 1;
                    ValueAnimator valueAnimator = this.anim;
                    if (valueAnimator != null) {
                        valueAnimator.cancel();
                    }
                    this.anim = null;
                    return true;
                }
                if (this.mode == 0 && (-dy) > this.slop * 2 && Math.abs(dy) > Math.abs(dx) * 1.5f && this.index == this.homeIndex) {
                    this.mode = 2;
                    return true;
                }
                return false;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent e) {
        Function0<Unit> function0;
        Intrinsics.checkNotNullParameter(e, "e");
        float dx = e.getX() - this.downX;
        float dy = e.getY() - this.downY;
        switch (e.getActionMasked()) {
            case 0:
                this.downX = e.getX();
                this.downY = e.getY();
                this.startScroll = getScrollX();
                this.mode = 0;
                return true;
            case 1:
            case 3:
                if (this.mode == 1) {
                    go$default(this, dx < ((float) (-getWidth())) / 6.0f ? this.index + 1 : dx > ((float) getWidth()) / 6.0f ? this.index - 1 : this.index, false, 2, null);
                } else if (this.mode == 2 && (-dy) > getHeight() / 8.0f && (function0 = this.onSwipeUp) != null) {
                    function0.invoke();
                }
                this.mode = 0;
                return true;
            case 2:
                if (this.mode == 0) {
                    if (Math.abs(dx) > this.slop && Math.abs(dx) > Math.abs(dy)) {
                        this.mode = 1;
                        ValueAnimator valueAnimator = this.anim;
                        if (valueAnimator != null) {
                            valueAnimator.cancel();
                        }
                        this.anim = null;
                    } else if ((-dy) > this.slop * 2 && Math.abs(dy) > Math.abs(dx) * 1.5f && this.index == this.homeIndex) {
                        this.mode = 2;
                    }
                } else if (this.mode == 1) {
                    int max = (getChildCount() - 1) * getWidth();
                    float x = this.startScroll - dx;
                    if (x < 0.0f) {
                        x /= 3.0f;
                    } else if (x > max) {
                        x = max + ((x - max) / 3.0f);
                    }
                    scrollTo((int) x, 0);
                }
                return true;
            default:
                return true;
        }
    }
}
