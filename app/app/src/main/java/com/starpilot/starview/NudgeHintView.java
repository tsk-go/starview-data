package com.starpilot.starview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import com.starpilot.starview.NudgeLogic;
import java.util.Arrays;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;

/* compiled from: NudgeHint.kt */
public final class NudgeHintView extends View {
    private final float d;
    private boolean metric;
    private final Paint p;
    private NudgeLogic.Result result;
    private final Paint tp;

    /* compiled from: NudgeHint.kt */
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[NudgeLogic.State.values().length];
            try {
                iArr[NudgeLogic.State.READY.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[NudgeLogic.State.WAIT.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NudgeHintView(Context ctx, AttributeSet attrs) {
        super(ctx, attrs);
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        this.d = getResources().getDisplayMetrics().density;
        this.p = new Paint(1);
        Paint $this$tp_u24lambda_u240 = new Paint(1);
        $this$tp_u24lambda_u240.setColor(-1);
        $this$tp_u24lambda_u240.setTypeface(Typeface.DEFAULT_BOLD);
        this.tp = $this$tp_u24lambda_u240;
        this.result = new NudgeLogic.Result(NudgeLogic.State.NONE, false, 0.0d, 0.0d, null, 30, null);
    }

    public /* synthetic */ NudgeHintView(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet);
    }

    private final float dp(float v) {
        return this.d * v;
    }

    public final NudgeLogic.Result getResult() {
        return this.result;
    }

    public final void setResult(NudgeLogic.Result result) {
        Intrinsics.checkNotNullParameter(result, "<set-?>");
        this.result = result;
    }

    public final boolean getMetric() {
        return this.metric;
    }

    public final void setMetric(boolean z) {
        this.metric = z;
    }

    private final String fmt(double m) {
        String format = this.metric ? String.format("%d m", Arrays.copyOf(new Object[]{Integer.valueOf((int) m)}, 1)) : String.format("%d ft", Arrays.copyOf(new Object[]{Integer.valueOf(((int) ((3.28084d * m) / 10)) * 10)}, 1));
        Intrinsics.checkNotNullExpressionValue(format, "format(...)");
        return format;
    }

    @Override // android.view.View
    protected void onDraw(Canvas c) {
        Pair pair;
        String sub;
        Intrinsics.checkNotNullParameter(c, "c");
        NudgeLogic.Result r = this.result;
        if (r.getState() == NudgeLogic.State.NONE) {
            return;
        }
        float w = getWidth();
        float h = getHeight();
        switch (WhenMappings.$EnumSwitchMapping$0[r.getState().ordinal()]) {
            case 1:
                pair = TuplesKt.to(Integer.valueOf(Color.rgb(28, 150, 70)), "Nudge " + (r.getRight() ? "RIGHT" : "LEFT") + " now");
                break;
            case 2:
                pair = TuplesKt.to(Integer.valueOf(Color.rgb(200, 60, 50)), "Wait: " + r.getWhy());
                break;
            default:
                pair = TuplesKt.to(Integer.valueOf(Color.argb(225, 210, 140, 20)), (r.getRight() ? "Exit right" : "Exit left") + " in " + fmt(r.getDistanceM()) + ": get ready");
                break;
        }
        int bg = ((Number) pair.component1()).intValue();
        String title = (String) pair.component2();
        int alpha = r.getState() == NudgeLogic.State.READY ? (int) (200 + (55 * Math.sin(System.currentTimeMillis() / 150.0d))) : 230;
        this.p.setColor(bg);
        this.p.setAlpha(RangesKt.coerceIn(alpha, 0, 255));
        c.drawRoundRect(new RectF(0.0f, 0.0f, w, h), dp(18.0f), dp(18.0f), this.p);
        float ax = r.getRight() ? w - dp(46.0f) : dp(46.0f);
        float ay = 0.42f * h;
        float s = dp(18.0f);
        Path $this$onDraw_u24lambda_u241 = new Path();
        if (r.getRight()) {
            $this$onDraw_u24lambda_u241.moveTo(ax - s, ay - (s * 0.6f));
            $this$onDraw_u24lambda_u241.lineTo((s * 0.2f) + ax, ay - (s * 0.6f));
            $this$onDraw_u24lambda_u241.lineTo((s * 0.2f) + ax, ay - (s * 1.2f));
            $this$onDraw_u24lambda_u241.lineTo((s * 1.2f) + ax, ay);
            $this$onDraw_u24lambda_u241.lineTo((s * 0.2f) + ax, ay + (1.2f * s));
            $this$onDraw_u24lambda_u241.lineTo(ax + (0.2f * s), (s * 0.6f) + ay);
            $this$onDraw_u24lambda_u241.lineTo(ax - s, ay + (0.6f * s));
        } else {
            $this$onDraw_u24lambda_u241.moveTo(ax + s, ay - (s * 0.6f));
            $this$onDraw_u24lambda_u241.lineTo(ax - (s * 0.2f), ay - (s * 0.6f));
            $this$onDraw_u24lambda_u241.lineTo(ax - (s * 0.2f), ay - (s * 1.2f));
            $this$onDraw_u24lambda_u241.lineTo(ax - (s * 1.2f), ay);
            $this$onDraw_u24lambda_u241.lineTo(ax - (s * 0.2f), ay + (1.2f * s));
            $this$onDraw_u24lambda_u241.lineTo(ax - (0.2f * s), (s * 0.6f) + ay);
            $this$onDraw_u24lambda_u241.lineTo(ax + s, ay + (0.6f * s));
        }
        $this$onDraw_u24lambda_u241.close();
        this.p.setColor(-1);
        this.p.setAlpha(255);
        c.drawPath($this$onDraw_u24lambda_u241, this.p);
        this.tp.setTextAlign(Paint.Align.CENTER);
        this.tp.setTextSize(dp(21.0f));
        String t = title;
        while (t.length() > 4 && this.tp.measureText(t) > w - dp(120.0f)) {
            t = StringsKt.dropLast(t, 2) + "…";
        }
        float f = 2;
        c.drawText(t, w / f, 0.47f * h, this.tp);
        this.tp.setTextSize(dp(13.0f));
        this.tp.setTypeface(Typeface.DEFAULT);
        switch (WhenMappings.$EnumSwitchMapping$0[r.getState().ordinal()]) {
            case 1:
                sub = "push the wheel toward the exit: StarPilot moves over (" + fmt(r.getDistanceM()) + " left)";
                break;
            case 2:
                sub = "the nudge won't be taken until this clears";
                break;
            default:
                sub = "the nudge window opens " + fmt(r.getWindowM()) + " before the split";
                break;
        }
        c.drawText(sub, w / f, 0.72f * h, this.tp);
        this.tp.setTypeface(Typeface.DEFAULT_BOLD);
        if (r.getState() != NudgeLogic.State.SOON && r.getWindowM() > 0.0d) {
            float f2 = RangesKt.coerceIn((float) (r.getDistanceM() / r.getWindowM()), 0.0f, 1.0f);
            this.p.setColor(Color.argb(90, 255, 255, 255));
            c.drawRoundRect(new RectF(dp(16.0f), h - dp(12.0f), w - dp(16.0f), h - dp(7.0f)), dp(3.0f), dp(3.0f), this.p);
            this.p.setColor(-1);
            c.drawRoundRect(new RectF(dp(16.0f), h - dp(12.0f), dp(16.0f) + ((w - dp(32.0f)) * f2), h - dp(7.0f)), dp(3.0f), dp(3.0f), this.p);
        }
        if (r.getState() == NudgeLogic.State.READY) {
            postInvalidateDelayed(50L);
        }
    }
}
