package com.starpilot.starview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.MeasureSpec;
import kotlin.jvm.internal.Intrinsics;

public final class FlowRow extends ViewGroup {
   private final int hGap;
   private final int vGap;

   public FlowRow(Context var1, int var2, int var3) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      super(var1);
      this.hGap = var2;
      this.vGap = var3;
   }

   protected void onLayout(boolean var1, int var2, int var3, int var4, int var5) {
      int var11 = this.getPaddingLeft();
      int var12 = this.getPaddingRight();
      int var7 = 0;
      var5 = 0;
      var3 = 0;
      int var8 = 0;
      int var13 = this.getChildCount();

      while (var8 < var13) {
         View var14 = this.getChildAt(var8);
         int var10 = var7;
         int var6 = var5;
         int var9 = var3;
         if (var14.getVisibility() != 8) {
            var10 = var7;
            var6 = var5;
            var9 = var3;
            if (var7 > 0) {
               var10 = var7;
               var6 = var5;
               var9 = var3;
               if (var14.getMeasuredWidth() + var7 > var4 - var2 - var11 - var12) {
                  var10 = 0;
                  var6 = var5 + this.vGap + var3;
                  var9 = 0;
               }
            }

            var14.layout(
               this.getPaddingLeft() + var10,
               this.getPaddingTop() + var6,
               this.getPaddingLeft() + var10 + var14.getMeasuredWidth(),
               this.getPaddingTop() + var6 + var14.getMeasuredHeight()
            );
            var10 += var14.getMeasuredWidth() + this.hGap;
            var9 = Math.max(var9, var14.getMeasuredHeight());
         }

         var8++;
         var7 = var10;
         var5 = var6;
         var3 = var9;
      }
   }

   protected void onMeasure(int var1, int var2) {
      int var10 = MeasureSpec.getSize(var1) - this.getPaddingLeft() - this.getPaddingRight();
      int var5 = 0;
      int var3 = 0;
      var2 = 0;
      int var6 = 0;
      int var9 = this.getChildCount();

      while (var6 < var9) {
         View var11 = this.getChildAt(var6);
         int var8 = var5;
         int var4 = var3;
         int var7 = var2;
         if (var11.getVisibility() != 8) {
            this.measureChild(var11, MeasureSpec.makeMeasureSpec(var10, Integer.MIN_VALUE), MeasureSpec.makeMeasureSpec(0, 0));
            var8 = var5;
            var4 = var3;
            var7 = var2;
            if (var5 > 0) {
               var8 = var5;
               var4 = var3;
               var7 = var2;
               if (var11.getMeasuredWidth() + var5 > var10) {
                  var8 = 0;
                  var4 = var3 + this.vGap + var2;
                  var7 = 0;
               }
            }

            var8 += var11.getMeasuredWidth() + this.hGap;
            var7 = Math.max(var7, var11.getMeasuredHeight());
         }

         var6++;
         var5 = var8;
         var3 = var4;
         var2 = var7;
      }

      this.setMeasuredDimension(MeasureSpec.getSize(var1), var3 + var2 + this.getPaddingTop() + this.getPaddingBottom());
   }
}
