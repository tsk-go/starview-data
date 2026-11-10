package com.starpilot.starview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: CarUi.kt */
public final class FlowRow extends ViewGroup {
    private final int hGap;
    private final int vGap;

    /* VF: from Vineflower (jadx: ) */
    public FlowRow(Context var1, int var2, int var3) {
        super(var1);
        Intrinsics.checkNotNullParameter(var1, "ctx");
        this.hGap = var2;
        this.vGap = var3;
    }

    @Override // android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int maxW = (View.MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft()) - getPaddingRight();
        int x = 0;
        int y = 0;
        int lineH = 0;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View c = getChildAt(i);
            if (c.getVisibility() != 8) {
                measureChild(c, View.MeasureSpec.makeMeasureSpec(maxW, Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(0, 0));
                if (x > 0 && c.getMeasuredWidth() + x > maxW) {
                    x = 0;
                    y += this.vGap + lineH;
                    lineH = 0;
                }
                x += c.getMeasuredWidth() + this.hGap;
                lineH = Math.max(lineH, c.getMeasuredHeight());
            }
        }
        int i2 = View.MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(i2, y + lineH + getPaddingTop() + getPaddingBottom());
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int maxW = ((r - l) - getPaddingLeft()) - getPaddingRight();
        int x = 0;
        int y = 0;
        int lineH = 0;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View c = getChildAt(i);
            if (c.getVisibility() != 8) {
                if (x > 0 && c.getMeasuredWidth() + x > maxW) {
                    x = 0;
                    y += this.vGap + lineH;
                    lineH = 0;
                }
                c.layout(getPaddingLeft() + x, getPaddingTop() + y, getPaddingLeft() + x + c.getMeasuredWidth(), getPaddingTop() + y + c.getMeasuredHeight());
                x += c.getMeasuredWidth() + this.hGap;
                lineH = Math.max(lineH, c.getMeasuredHeight());
            }
        }
    }
}
