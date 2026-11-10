package com.starpilot.starview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.animation.Animator.AnimatorListener;
import android.content.Context;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.View.MeasureSpec;
import android.view.animation.DecelerateInterpolator;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

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

   // $VF: synthetic method
   public static void $r8$lambda$PoGKVb70OEEnORgAh3ZuGWVWwTc(SwipePager var0, ValueAnimator var1) {
      go$lambda$1$lambda$0(var0, var1);
   }

   public SwipePager(Context var1, int var2) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      super(var1);
      this.homeIndex = var2;
      this.index = this.homeIndex;
      this.slop = ViewConfiguration.get(var1).getScaledTouchSlop();
   }

   // $VF: synthetic method
   public static void go$default(SwipePager var0, int var1, boolean var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = true;
      }

      var0.go(var1, var2);
   }

   private static final void go$lambda$1$lambda$0(SwipePager var0, ValueAnimator var1) {
      Intrinsics.checkNotNullParameter(var1, "it");
      Object var2 = var1.getAnimatedValue();
      Intrinsics.checkNotNull(var2, "null cannot be cast to non-null type kotlin.Int");
      var0.scrollTo((Integer)var2, 0);
   }

   public final int getIndex() {
      return this.index;
   }

   public final Function1<Integer, Unit> getOnPage() {
      return this.onPage;
   }

   public final Function0<Unit> getOnSwipeUp() {
      return this.onSwipeUp;
   }

   public final void go(int var1, boolean var2) {
      int var4 = this.getChildCount();
      boolean var3 = true;
      var4 = RangesKt.coerceIn(var1, 0, var4 - 1);
      boolean var6;
      if (var4 != this.index) {
         var6 = var3;
      } else {
         var6 = false;
      }

      this.index = var4;
      ValueAnimator var5 = this.anim;
      if (var5 != null) {
         var5.cancel();
      }

      if (var2 && this.getWidth() != 0) {
         var5 = ValueAnimator.ofInt(new int[]{this.getScrollX(), this.index * this.getWidth()});
         var5.setDuration(220L);
         var5.setInterpolator((TimeInterpolator)(new DecelerateInterpolator()));
         var5.addUpdateListener(new SwipePager$$ExternalSyntheticLambda0(this));
         var5.addListener((AnimatorListener)(new AnimatorListenerAdapter() {

            public void onAnimationEnd(Animator var1) {
               Intrinsics.checkNotNullParameter(var1, "a");
               SwipePager.this.anim = null;
            }
         }));
         var5.start();
         this.anim = var5;
      } else {
         this.scrollTo(this.index * this.getWidth(), 0);
      }

      if (var6) {
         Function1 var9 = this.onPage;
         if (var9 != null) {
            var9.invoke(this.index);
         }
      }
   }

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

   protected void onLayout(boolean var1, int var2, int var3, int var4, int var5) {
      var4 -= var2;
      var2 = 0;

      for (int var6 = this.getChildCount(); var2 < var6; var2++) {
         this.getChildAt(var2).layout(var2 * var4, 0, (var2 + 1) * var4, var5 - var3);
      }

      if (this.anim == null && this.mode != 1) {
         this.scrollTo(this.index * var4, 0);
      }
   }

   protected void onMeasure(int var1, int var2) {
      int var3 = MeasureSpec.getSize(var1);
      int var4 = MeasureSpec.getSize(var2);
      var1 = 0;

      for (int var6 = this.getChildCount(); var1 < var6; var1++) {
         this.getChildAt(var1).measure(MeasureSpec.makeMeasureSpec(var3, 1073741824), MeasureSpec.makeMeasureSpec(var4, 1073741824));
      }

      this.setMeasuredDimension(var3, var4);
   }

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

   public final void setOnPage(Function1<? super Integer, Unit> var1) {
      this.onPage = var1;
   }

   public final void setOnSwipeUp(Function0<Unit> var1) {
      this.onSwipeUp = var1;
   }
}
