package com.starpilot.starview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.json.JSONObject;
import android.graphics.Paint.Align;

/* compiled from: BlinkerCardView.kt */
public final class BlinkerCardView extends View {
    private final Paint bg;
    private final Paint big;
    private final float d;
    private final RectF leftR;
    private final Handler main;
    private final Paint p;
    private final HashMap<Integer, Integer> pointers;
    private final Runnable repeat;
    private final RectF rightR;
    private String sent;
    private String status;
    private boolean statusErr;
    public Telemetry t;
    private final Paint txt;

    /* VF: from Vineflower (jadx: Type inference failed) */
    public BlinkerCardView(Context var1, AttributeSet var2) {
        super(var1, var2);
        Intrinsics.checkNotNullParameter(var1, "ctx");
        this.d = this.getResources().getDisplayMetrics().density;
        Paint var3 = new Paint(1);
        var3.setColor(Color.argb(200, 0, 0, 0));
        this.bg = var3;
        this.p = new Paint(1);
        Paint var4 = new Paint(1);
        var4.setColor(Color.rgb(200, 208, 222));
        var4.setTextSize(this.d * 13.0F);
        var4.setTextAlign(android.graphics.Paint.Align.CENTER);
        this.txt = var4;
        Paint var5 = new Paint(1);
        var5.setColor(-1);
        var5.setTextSize(this.d * 15.0F);
        var5.setTypeface(Typeface.DEFAULT_BOLD);
        var5.setTextAlign(android.graphics.Paint.Align.CENTER);
        this.big = var5;
        this.leftR = new RectF();
        this.rightR = new RectF();
        this.pointers = new HashMap<>();
        this.main = new Handler(Looper.getMainLooper());
        this.sent = "off";
        this.status = "hold a button to signal";
        this.repeat = new Runnable() {
            final BlinkerCardView this$0 = BlinkerCardView.this;

            @Override
            public void run() {
                String var1x = this.this$0.held();
                if (Intrinsics.areEqual(var1x, "left") || Intrinsics.areEqual(var1x, "right")) {
                    this.this$0.send(var1x);
                    this.this$0.main.postDelayed(this, 250L);
                }
            }
        };
    }

    public /* synthetic */ BlinkerCardView(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet);
    }

    public final Telemetry getT() {
        Telemetry telemetry = this.t;
        if (telemetry != null) {
            return telemetry;
        }
        Intrinsics.throwUninitializedPropertyAccessException("t");
        return null;
    }

    public final void setT(Telemetry telemetry) {
        Intrinsics.checkNotNullParameter(telemetry, "<set-?>");
        this.t = telemetry;
    }

    public final String held() {
        boolean left = false, right = false;
        for (Integer v : this.pointers.values()) {
            if (v.intValue() < 0) left = true;
            if (v.intValue() > 0) right = true;
        }
        return (left && right) ? "both" : left ? "left" : right ? "right" : "off";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void send(final String side) {
        DataClient dc = Link.INSTANCE.getData();
        if (dc == null || !dc.getConnected()) {
            this.status = "not connected to the comma";
            this.statusErr = true;
            invalidate();
        } else {
            final boolean first = true ^ Intrinsics.areEqual(side, this.sent);
            this.sent = side;
            dc.request("blink", MapsKt.mapOf(TuplesKt.to("side", side)), new Function1() { // from class: com.starpilot.starview.BlinkerCardView$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    Unit send$lambda$5;
                    send$lambda$5 = BlinkerCardView.send$lambda$5(BlinkerCardView.this, first, side, (JSONObject) obj);
                    return send$lambda$5;
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit send$lambda$5(BlinkerCardView this$0, boolean $first, String $side, JSONObject ack) {
        Intrinsics.checkNotNullParameter(ack, "ack");
        if (!ack.optBoolean("ok")) {
            this$0.status = ack.optString("error", "rejected");
            this$0.statusErr = true;
        } else if ($first) {
            this$0.status = Intrinsics.areEqual($side, "off") ? "released" : $side + " signal on - let go to stop";
            this$0.statusErr = false;
        }
        this$0.invalidate();
        return Unit.INSTANCE;
    }

    private final void update() {
        String h = held();
        this.main.removeCallbacks(this.repeat);
        if (Intrinsics.areEqual(h, "both")) {
            send("off");
            this.status = "both = off (the relay box never closes both sides)";
            this.statusErr = true;
        } else if (!Intrinsics.areEqual(h, "off")) {
            send(h);
            this.main.postDelayed(this.repeat, 250L);
        } else if (!Intrinsics.areEqual(this.sent, "off")) {
            send("off");
        }
        invalidate();
    }

    /* VF: from Vineflower (jadx: Code restructure failed;incorrect) */
    public boolean onTouchEvent(MotionEvent var1) {
        Intrinsics.checkNotNullParameter(var1, "e");
        int var3 = var1.getActionIndex();
        switch (var1.getActionMasked()) {
            case 0:
            case 5:
                byte var2;
                if (this.leftR.contains(var1.getX(var3), var1.getY(var3))) {
                    var2 = -1;
                } else if (this.rightR.contains(var1.getX(var3), var1.getY(var3))) {
                    var2 = 1;
                } else {
                    var2 = 0;
                }

                if (var2 != 0) {
                    this.pointers.put(var1.getPointerId(var3), Integer.valueOf(var2));
                    this.performHapticFeedback(1);
                    this.update();
                }
                break;
            case 1:
            case 6:
                if (this.pointers.remove(var1.getPointerId(var3)) != null) {
                    this.update();
                }
            case 2:
            case 4:
            default:
                break;
            case 3:
                this.pointers.clear();
                this.update();
        }

        return true;
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.pointers.clear();
        this.main.removeCallbacks(this.repeat);
        if (Intrinsics.areEqual(this.sent, "off")) {
            return;
        }
        send("off");
    }

    @Override // android.view.View
    protected void onVisibilityChanged(View changedView, int visibility) {
        Intrinsics.checkNotNullParameter(changedView, "changedView");
        super.onVisibilityChanged(changedView, visibility);
        if (visibility == 0 || this.pointers.isEmpty()) {
            return;
        }
        this.pointers.clear();
        update();
    }

    @Override // android.view.View
    protected void onDraw(Canvas c) {
        boolean z;
        boolean z2;
        int i;
        int i2;
        int i3;
        String it;
        Intrinsics.checkNotNullParameter(c, "c");
        float w = getWidth();
        float h = getHeight();
        float pad = this.d * 10.0f;
        c.drawRoundRect(new RectF(0.0f, 0.0f, w, h), this.d * 16.0f, this.d * 16.0f, this.bg);
        float f = 2;
        float bw = (w - (3 * pad)) / f;
        float bh = (h - pad) - (this.d * 26.0f);
        this.leftR.set(pad, pad, pad + bw, pad + bh);
        this.rightR.set((f * pad) + bw, pad, w - pad, pad + bh);
        String h0 = held();
        boolean blink = (System.currentTimeMillis() / ((long) 350)) % ((long) 2) == 0;
        boolean seenL = this.t != null && getT().getLeftBlinker();
        boolean seenR = this.t != null && getT().getRightBlinker();
        RectF rectF = this.leftR;
        Iterable values = this.pointers.values();
        Intrinsics.checkNotNullExpressionValue(values, "<get-values>(...)");
        Iterable $this$any$iv = values;
        if (!((Collection) $this$any$iv).isEmpty()) {
            Iterator it2 = $this$any$iv.iterator();
            while (true) {
                if (it2.hasNext()) {
                    Object element$iv = it2.next();
                    Integer it3 = (Integer) element$iv;
                    if (it3.intValue() < 0) {
                        z = true;
                        break;
                    }
                } else {
                    z = false;
                    break;
                }
            }
        } else {
            z = false;
        }
        onDraw$button(this, h0, c, blink, rectF, -1, z, seenL);
        RectF rectF2 = this.rightR;
        Iterable values2 = this.pointers.values();
        Intrinsics.checkNotNullExpressionValue(values2, "<get-values>(...)");
        Iterable $this$any$iv2 = values2;
        if (!((Collection) $this$any$iv2).isEmpty()) {
            Iterator it4 = $this$any$iv2.iterator();
            while (true) {
                if (it4.hasNext()) {
                    Object element$iv2 = it4.next();
                    Integer it5 = (Integer) element$iv2;
                    if (it5.intValue() > 0) {
                        z2 = true;
                        break;
                    }
                } else {
                    z2 = false;
                    break;
                }
            }
        } else {
            z2 = false;
        }
        onDraw$button(this, h0, c, blink, rectF2, 1, z2, seenR);
        Paint paint = this.txt;
        if (this.statusErr) {
            i = 130;
            i2 = 120;
            i3 = 255;
        } else {
            i = 208;
            i2 = 222;
            i3 = 200;
        }
        paint.setColor(Color.rgb(i3, i, i2));
        String it6 = BlinkerTurn.INSTANCE.label();
        if (it6 == null || (it = "openpilot: " + it6) == null) {
            it = (seenL && seenR) ? "openpilot sees: both" : seenL ? "openpilot sees: LEFT" : seenR ? "openpilot sees: RIGHT" : "openpilot sees: none";
        }
        c.drawText(this.status + "  ·  " + it, w / f, h - (this.d * 9.0f), this.txt);
        if (!this.pointers.isEmpty() || seenL || seenR) {
            postInvalidateDelayed(120L);
        }
    }

    private static final void onDraw$button(BlinkerCardView this$0, String h0, Canvas $c, boolean blink, RectF r, int side, boolean held, boolean seen) {
        int i;
        int i2;
        int i3;
        int rgb;
        int i4;
        int i5;
        int i6;
        Paint paint = this$0.p;
        if (held && Intrinsics.areEqual(h0, "both")) {
            rgb = Color.rgb(120, 40, 40);
        } else {
            if (held) {
                i = 140;
                i2 = 20;
                i3 = 200;
            } else {
                i = 54;
                i2 = 66;
                i3 = 48;
            }
            rgb = Color.rgb(i3, i, i2);
        }
        paint.setColor(rgb);
        $c.drawRoundRect(r, this$0.d * 14.0f, this$0.d * 14.0f, this$0.p);
        Paint paint2 = this$0.p;
        if (seen && blink) {
            i4 = 220;
            i5 = 100;
            i6 = 60;
        } else if (seen) {
            i4 = 110;
            i5 = 50;
            i6 = 30;
        } else {
            i4 = 176;
            i5 = 188;
            i6 = 170;
        }
        paint2.setColor(Color.rgb(i6, i4, i5));
        float cx = r.centerX();
        float cy = r.centerY() - (this$0.d * 6.0f);
        float s = Math.min(r.width(), r.height()) * 0.28f;
        Path path = new Path();
        if (side < 0) {
            path.moveTo(cx - s, cy);
            path.lineTo(cx, cy - (s * 0.8f));
            path.lineTo(cx, cy - (s * 0.35f));
            path.lineTo(cx + s, cy - (s * 0.35f));
            path.lineTo(cx + s, (s * 0.35f) + cy);
            path.lineTo(cx, (0.35f * s) + cy);
            path.lineTo(cx, (0.8f * s) + cy);
        } else {
            path.moveTo(cx + s, cy);
            path.lineTo(cx, cy - (s * 0.8f));
            path.lineTo(cx, cy - (s * 0.35f));
            path.lineTo(cx - s, cy - (s * 0.35f));
            path.lineTo(cx - s, (s * 0.35f) + cy);
            path.lineTo(cx, (0.35f * s) + cy);
            path.lineTo(cx, (0.8f * s) + cy);
        }
        path.close();
        $c.drawPath(path, this$0.p);
        $c.drawText(held ? "HOLDING" : side < 0 ? "LEFT" : "RIGHT", cx, r.bottom - (this$0.d * 8.0f), this$0.big);
    }
}
