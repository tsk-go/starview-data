package com.starpilot.starlock;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.app.NotificationCompat;
import com.google.mlkit.common.MlKitException;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup.LayoutParams;

/* compiled from: ShortcutActivity.kt */
public final class ShortcutActivity extends Activity {
    private boolean answered;
    private TextView caption;
    private GradientDrawable circle;
    private ImageView icon;
    private final Handler main = new Handler(Looper.getMainLooper());
    private ObjectAnimator pulse;
    private static final int GREY = Color.rgb(97, 97, 97);
    private static final int GREEN = Color.rgb(31, 178, 4);
    private static final int RED = Color.rgb(230, 0, 0);
    private static final int AMBER = Color.rgb(242, 157, 0);
    private static final int DARK = Color.rgb(60, 60, 60);

    /* compiled from: ShortcutActivity.kt */
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

    private final int dp(int v) {
        return (int) TypedValue.applyDimension(1, v, getResources().getDisplayMetrics());
    }

    /* VF: from Vineflower (jadx: Removed duplicated region;incorrect) */
    protected void onCreate(Bundle var1) {
        String var10 = null; // declarations Vineflower left out
        super.onCreate(var1);
        if (KeeperService.Companion.background((Context)this)) {
            KeeperService.Companion.start((Context)this);
        }

        Intent var8 = this.getIntent();
        Object var4 = null;
        String var9;
        if (var8 != null) {
            var9 = var8.getAction();
        } else {
            var9 = null;
        }

        label56: {
            if (var9 != null) {
                switch (var9.hashCode()) {
                    case 359090247:
                        if (var9.equals("com.starpilot.starlock.LOCK")) {
                            var10 = "lock";
                            break label56;
                        }
                        break;
                    case 1745356640:
                        if (var9.equals("com.starpilot.starlock.UNLOCK")) {
                            var10 = "unlock";
                            break label56;
                        }
                }
            }

            var10 = null;
        }

        if (var10 == null) {
            this.close();
        } else {
            GradientDrawable var3 = new GradientDrawable();
            var3.setShape(1);
            var3.setColor(GREY);
            this.circle = var3;
            ImageView var6 = new ImageView((Context)this);
            GradientDrawable var5 = this.circle;
            var3 = var5;
            if (var5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("circle");
                var3 = null;
            }

            var6.setBackground((Drawable)var3);
            var6.setPadding(this.dp(40), this.dp(40), this.dp(40), this.dp(40));
            int var2;
            if (Intrinsics.areEqual(var10, "lock")) {
                var2 = R.drawable.ic_lock;
            } else {
                var2 = R.drawable.ic_unlock;
            }

            var6.setImageResource(var2);
            var6.setImageTintList(ColorStateList.valueOf(-1));
            var6.setElevation(this.dp(8));
            this.icon = var6;
            TextView var19 = new TextView((Context)this);
            var19.setTextColor(-1);
            var19.setTextSize(15.0F);
            var19.setGravity(17);
            var19.setVisibility(8);
            var19.setPadding(this.dp(14), this.dp(8), this.dp(14), this.dp(8));
            var3 = new GradientDrawable();
            var3.setCornerRadius(this.dp(14));
            var3.setColor(-870309856);
            var19.setBackground((Drawable)var3);
            this.caption = var19;
            LinearLayout var23 = new LinearLayout((Context)this);
            var23.setOrientation(1);
            var23.setGravity(1);
            ImageView var20 = this.icon;
            ImageView var13 = var20;
            if (var20 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("icon");
                var13 = null;
            }

            var23.addView((View)var13, (android.view.ViewGroup.LayoutParams)(new android.widget.LinearLayout.LayoutParams(this.dp(150), this.dp(150))));
            TextView var21 = this.caption;
            TextView var14 = var21;
            if (var21 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("caption");
                var14 = null;
            }

            View var15 = (View)var14;
            android.widget.LinearLayout.LayoutParams var22 = new android.widget.LinearLayout.LayoutParams(-2, -2);
            var22.topMargin = this.dp(14);
            Unit var7 = Unit.INSTANCE;
            var23.addView(var15, (android.view.ViewGroup.LayoutParams)var22);
            FrameLayout var16 = new FrameLayout((Context)this);
            var16.addView((View)var23, (android.view.ViewGroup.LayoutParams)(new android.widget.FrameLayout.LayoutParams(this.dp(300), -2, 17)));
            var16.setOnClickListener(new ShortcutActivity$$ExternalSyntheticLambda0(this));
            this.setContentView((View)var16);
            ImageView var17 = this.icon;
            if (var17 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("icon");
                var17 = (ImageView)var4;
            }

            ObjectAnimator var18 = ObjectAnimator.ofFloat(var17, "alpha", new float[]{1.0F, 0.45F});
            var18.setDuration(450L);
            var18.setRepeatMode(2);
            var18.setRepeatCount(-1);
            var18.start();
            this.pulse = var18;
            if (!Door.INSTANCE
                .run((Context)this, var10, new ShortcutActivity$$ExternalSyntheticLambda1(), new ShortcutActivity$$ExternalSyntheticLambda2(this, var10))) {
                this.show(var10, Outcome.FAILED, "Still working on the last one");
            }

            this.main.postDelayed(new ShortcutActivity$$ExternalSyntheticLambda3(this), 15000L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onCreate$lambda$10(ShortcutActivity this$0, String $cmd, Outcome outcome, String msg) {
        Intrinsics.checkNotNullParameter(outcome, "outcome");
        Intrinsics.checkNotNullParameter(msg, "msg");
        this$0.show($cmd, outcome, msg);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onCreate$lambda$9(String it) {
        Intrinsics.checkNotNullParameter(it, "it");
        AppLog.INSTANCE.log(it);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$11(ShortcutActivity this$0) {
        if (this$0.answered) {
            return;
        }
        this$0.close();
    }

    private final void show(String cmd, Outcome outcome, String msg) {
        this.answered = true;
        if (isFinishing() || isDestroyed()) {
            Notify notify = Notify.INSTANCE;
            Context applicationContext = getApplicationContext();
            Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
            notify.show(applicationContext, msg, true);
            return;
        }
        ObjectAnimator objectAnimator = this.pulse;
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
        ImageView imageView = this.icon;
        ImageView imageView2 = null;
        if (imageView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("icon");
            imageView = null;
        }
        imageView.setAlpha(1.0f);
        switch (WhenMappings.$EnumSwitchMapping$0[outcome.ordinal()]) {
            case 1:
                GradientDrawable gradientDrawable = this.circle;
                if (gradientDrawable == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("circle");
                    gradientDrawable = null;
                }
                gradientDrawable.setColor(Intrinsics.areEqual(cmd, "lock") ? GREEN : RED);
                ImageView imageView3 = this.icon;
                if (imageView3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("icon");
                    imageView3 = null;
                }
                imageView3.setImageResource(Intrinsics.areEqual(cmd, "lock") ? R.drawable.ic_lock : R.drawable.ic_unlock);
                break;
            case 2:
                GradientDrawable gradientDrawable2 = this.circle;
                if (gradientDrawable2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("circle");
                    gradientDrawable2 = null;
                }
                gradientDrawable2.setColor(AMBER);
                caption(StringsKt.substringBefore(msg, "  (", msg));
                break;
            case 3:
                GradientDrawable gradientDrawable3 = this.circle;
                if (gradientDrawable3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("circle");
                    gradientDrawable3 = null;
                }
                gradientDrawable3.setColor(DARK);
                ImageView imageView4 = this.icon;
                if (imageView4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("icon");
                    imageView4 = null;
                }
                imageView4.setImageResource(R.drawable.ic_alert);
                caption(msg);
                break;
            default:
                throw new NoWhenBranchMatchedException();
        }
        ImageView imageView5 = this.icon;
        if (imageView5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("icon");
            imageView5 = null;
        }
        imageView5.setScaleX(0.85f);
        ImageView imageView6 = this.icon;
        if (imageView6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("icon");
            imageView6 = null;
        }
        imageView6.setScaleY(0.85f);
        ImageView imageView7 = this.icon;
        if (imageView7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("icon");
            imageView7 = null;
        }
        imageView7.animate().scaleX(1.0f).scaleY(1.0f).setDuration(160L).start();
        if (Build.VERSION.SDK_INT >= 30) {
            ImageView imageView8 = this.icon;
            if (imageView8 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("icon");
            } else {
                imageView2 = imageView8;
            }
            imageView2.performHapticFeedback(outcome == Outcome.CONFIRMED ? 16 : 17);
        }
        this.main.postDelayed(new Runnable() { // from class: com.starpilot.starlock.ShortcutActivity$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                ShortcutActivity.this.close();
            }
        }, outcome == Outcome.CONFIRMED ? 1300L : 3000L);
    }

    private final void caption(String text) {
        TextView textView = this.caption;
        TextView textView2 = null;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("caption");
            textView = null;
        }
        textView.setText(text);
        TextView textView3 = this.caption;
        if (textView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("caption");
        } else {
            textView2 = textView3;
        }
        textView2.setVisibility(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void close() {
        if (isFinishing()) {
            return;
        }
        finish();
        overridePendingTransition(0, android.R.anim.fade_out);
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        ObjectAnimator objectAnimator = this.pulse;
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
        this.main.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    // recovered from Vineflower (R8 lambda body jadx inlined)
   static final void onCreate$lambda$7$lambda$6(ShortcutActivity var0, View var1) {
       var0.close();
    }
}
