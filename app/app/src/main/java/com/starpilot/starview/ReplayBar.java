package com.starpilot.starview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.json.JSONObject;

/* compiled from: Replay.kt */
public final class ReplayBar extends View {
    private final RectF bar;
    private final Paint bg;
    private final Paint btn;
    private final Paint btnBg;
    private final float d;
    private final Paint done;
    private final List<String> keys;
    private final Paint pill;
    private final Map<String, RectF> rects;
    private final Paint small;
    private final Paint track;
    private final Paint txt;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ReplayBar(Context ctx, AttributeSet attrs) {
        super(ctx, attrs);
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        this.d = getResources().getDisplayMetrics().density;
        Paint $this$bg_u24lambda_u240 = new Paint(1);
        $this$bg_u24lambda_u240.setColor(Color.argb(215, 18, 20, 26));
        this.bg = $this$bg_u24lambda_u240;
        Paint $this$pill_u24lambda_u241 = new Paint(1);
        $this$pill_u24lambda_u241.setColor(Color.rgb(205, 40, 40));
        this.pill = $this$pill_u24lambda_u241;
        Paint $this$txt_u24lambda_u242 = new Paint(1);
        $this$txt_u24lambda_u242.setColor(-1);
        $this$txt_u24lambda_u242.setTextSize(this.d * 15.0f);
        $this$txt_u24lambda_u242.setTypeface(Typeface.DEFAULT_BOLD);
        this.txt = $this$txt_u24lambda_u242;
        Paint $this$small_u24lambda_u243 = new Paint(1);
        $this$small_u24lambda_u243.setColor(Color.rgb(200, 205, 215));
        $this$small_u24lambda_u243.setTextSize(this.d * 13.0f);
        this.small = $this$small_u24lambda_u243;
        Paint $this$btn_u24lambda_u244 = new Paint(1);
        $this$btn_u24lambda_u244.setColor(-1);
        $this$btn_u24lambda_u244.setTextSize(this.d * 17.0f);
        $this$btn_u24lambda_u244.setTypeface(Typeface.DEFAULT_BOLD);
        $this$btn_u24lambda_u244.setTextAlign(Paint.Align.CENTER);
        this.btn = $this$btn_u24lambda_u244;
        Paint $this$btnBg_u24lambda_u245 = new Paint(1);
        $this$btnBg_u24lambda_u245.setColor(Color.argb(70, 255, 255, 255));
        this.btnBg = $this$btnBg_u24lambda_u245;
        Paint $this$track_u24lambda_u246 = new Paint(1);
        $this$track_u24lambda_u246.setColor(Color.argb(80, 255, 255, 255));
        this.track = $this$track_u24lambda_u246;
        Paint $this$done_u24lambda_u247 = new Paint(1);
        $this$done_u24lambda_u247.setColor(Color.rgb(230, 70, 70));
        this.done = $this$done_u24lambda_u247;
        this.keys = CollectionsKt.listOf((Object[]) new String[]{"back", "play", "fwd", "speed", "stop"});
        Iterable $this$associateWith$iv = this.keys;
        LinkedHashMap result$iv = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault($this$associateWith$iv, 10)), 16));
        for (Object element$iv$iv : $this$associateWith$iv) {
            result$iv.put(element$iv$iv, new RectF());
        }
        this.rects = result$iv;
        this.bar = new RectF();
    }

    public /* synthetic */ ReplayBar(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet);
    }

    @Override // android.view.View
    protected void onMeasure(int wSpec, int hSpec) {
        setMeasuredDimension((int) (560 * this.d), (int) (78 * this.d));
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:52:0x020e  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0214  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x021a  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0210  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected void onDraw(Canvas c) {
        double pos;
        String str;
        String str2;
        String label;
        Intrinsics.checkNotNullParameter(c, "c");
        JSONObject s = Replay.INSTANCE.getStatus();
        if (s == null) {
            return;
        }
        float w = getWidth();
        float pad = this.d * 10.0f;
        c.drawRoundRect(new RectF(0.0f, 0.0f, w, getHeight()), this.d * 16.0f, this.d * 16.0f, this.bg);
        RectF p = new RectF(pad, pad, (this.d * 78.0f) + pad, (this.d * 26.0f) + pad);
        c.drawRoundRect(p, this.d * 13.0f, this.d * 13.0f, this.pill);
        this.txt.setTextAlign(Paint.Align.CENTER);
        c.drawText("REPLAY", p.centerX(), p.centerY() + (this.d * 5.0f), this.txt);
        this.txt.setTextAlign(Paint.Align.LEFT);
        double pos2 = Replay.position$default(Replay.INSTANCE, 0L, 1, null);
        double dur = s.optDouble("dur", 0.0d);
        double speed = s.optDouble("speed", 1.0d);
        String clock = Replay.INSTANCE.clock(pos2);
        String clock2 = Replay.INSTANCE.clock(dur);
        if (speed == 1.0d) {
            pos = pos2;
            str = "";
            str2 = str;
        } else {
            str = "";
            pos = pos2;
            str2 = "  ·  " + (((speed % 1.0d) > 0.0d ? 1 : ((speed % 1.0d) == 0.0d ? 0 : -1)) == 0 ? Integer.valueOf((int) speed) : Double.valueOf(speed)) + "x";
        }
        String head = clock + " / " + clock2 + str2;
        c.drawText(head, p.right + (this.d * 10.0f), p.centerY() + (this.d * 5.0f), this.txt);
        String sub = s.optBoolean("loading") ? "loading…" : s.optBoolean("paused") ? "paused" : !s.optBoolean("video") ? "no video in this part" : str;
        if (sub.length() > 0) {
            c.drawText(sub, p.right + (this.d * 10.0f), p.bottom + (this.d * 16.0f), this.small);
        }
        float bw = this.d * 46.0f;
        float bh = this.d * 32.0f;
        float x = (w - pad) - bw;
        Iterator it = CollectionsKt.reversed(this.keys).iterator();
        float x2 = x;
        while (it.hasNext()) {
            Iterator it2 = it;
            String k = (String) it.next();
            String sub2 = sub;
            RectF r = (RectF) MapsKt.getValue(this.rects, k);
            double dur2 = dur;
            r.set(x2, pad, x2 + bw, pad + bh);
            float x3 = x2 - ((this.d * 6.0f) + bw);
            c.drawRoundRect(r, this.d * 10.0f, this.d * 10.0f, this.btnBg);
            switch (k.hashCode()) {
                case 101811:
                    if (k.equals("fwd")) {
                        label = "+30";
                        break;
                    }
                    label = (((speed % 1.0d) > 0.0d ? 1 : ((speed % 1.0d) == 0.0d ? 0 : -1)) == 0 ? Integer.valueOf((int) speed) : Double.valueOf(speed)) + "x";
                    break;
                case 3015911:
                    if (k.equals("back")) {
                        label = "−10";
                        break;
                    }
                    label = (((speed % 1.0d) > 0.0d ? 1 : ((speed % 1.0d) == 0.0d ? 0 : -1)) == 0 ? Integer.valueOf((int) speed) : Double.valueOf(speed)) + "x";
                    break;
                case 3443508:
                    if (k.equals("play")) {
                        if (!s.optBoolean("paused")) {
                            label = "❚❚";
                            break;
                        } else {
                            label = "▶";
                            break;
                        }
                    }
                    label = (((speed % 1.0d) > 0.0d ? 1 : ((speed % 1.0d) == 0.0d ? 0 : -1)) == 0 ? Integer.valueOf((int) speed) : Double.valueOf(speed)) + "x";
                    break;
                case 3540994:
                    if (k.equals("stop")) {
                        label = "✕";
                        break;
                    }
                    label = (((speed % 1.0d) > 0.0d ? 1 : ((speed % 1.0d) == 0.0d ? 0 : -1)) == 0 ? Integer.valueOf((int) speed) : Double.valueOf(speed)) + "x";
                    break;
                default:
                    label = (((speed % 1.0d) > 0.0d ? 1 : ((speed % 1.0d) == 0.0d ? 0 : -1)) == 0 ? Integer.valueOf((int) speed) : Double.valueOf(speed)) + "x";
                    break;
            }
            c.drawText(label, r.centerX(), r.centerY() + (this.d * 6.0f), this.btn);
            sub = sub2;
            it = it2;
            dur = dur2;
            x2 = x3;
            s = s;
        }
        double dur3 = dur;
        this.bar.set(pad, (getHeight() - pad) - (this.d * 8.0f), w - pad, getHeight() - pad);
        c.drawRoundRect(this.bar, this.d * 4.0f, this.d * 4.0f, this.track);
        if (dur3 > 0.0d) {
            c.drawRoundRect(new RectF(this.bar.left, this.bar.top, this.bar.left + (this.bar.width() * RangesKt.coerceIn((float) (pos / dur3), 0.0f, 1.0f)), this.bar.bottom), this.d * 4.0f, this.d * 4.0f, this.done);
        }
        postInvalidateDelayed(250L);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent e) {
        JSONObject s;
        Object element$iv;
        double d;
        Intrinsics.checkNotNullParameter(e, "e");
        if (e.getActionMasked() != 1 || (s = Replay.INSTANCE.getStatus()) == null) {
            return true;
        }
        Iterable $this$firstOrNull$iv = this.rects.entrySet();
        Iterator it = $this$firstOrNull$iv.iterator();
        while (true) {
            if (it.hasNext()) {
                element$iv = it.next();
                Map.Entry it2 = (Map.Entry) element$iv;
                if (((RectF) it2.getValue()).contains(e.getX(), e.getY())) {
                    break;
                }
            } else {
                element$iv = null;
                break;
            }
        }
        Map.Entry entry = (Map.Entry) element$iv;
        String hit = entry != null ? (String) entry.getKey() : null;
        double pos = Replay.position$default(Replay.INSTANCE, 0L, 1, null);
        if (Intrinsics.areEqual(hit, "back")) {
            Replay.control$default(Replay.INSTANCE, "skip", -10.0d, null, 4, null);
        } else if (Intrinsics.areEqual(hit, "fwd")) {
            Replay.control$default(Replay.INSTANCE, "skip", 30.0d, null, 4, null);
        } else if (Intrinsics.areEqual(hit, "play")) {
            Replay.control$default(Replay.INSTANCE, "toggle", 0.0d, null, 6, null);
        } else {
            if (Intrinsics.areEqual(hit, "speed")) {
                Replay replay = Replay.INSTANCE;
                double optDouble = s.optDouble("speed", 1.0d);
                double d2 = 2.0d;
                if (!(optDouble == 1.0d)) {
                    d2 = 4.0d;
                    if (!(optDouble == 2.0d)) {
                        d = optDouble == 4.0d ? 0.5d : 1.0d;
                        Replay.control$default(replay, "speed", d, null, 4, null);
                    }
                }
                d = d2;
                Replay.control$default(replay, "speed", d, null, 4, null);
            } else {
                if (!Intrinsics.areEqual(hit, "stop")) {
                    if (e.getY() >= this.bar.top - (this.d * 14.0f)) {
                        float f = this.bar.left;
                        float f2 = this.bar.right;
                        float x = e.getX();
                        if (f <= x && x <= f2) {
                            r9 = true;
                        }
                        if (r9) {
                            Replay.control$default(Replay.INSTANCE, "seek", s.optDouble("dur", 0.0d) * ((e.getX() - this.bar.left) / this.bar.width()), null, 4, null);
                        }
                    }
                    return true;
                }
                Replay.control$default(Replay.INSTANCE, "stop", 0.0d, null, 6, null);
                BlinkLog.INSTANCE.add("Drive replay stopped at " + Replay.INSTANCE.clock(pos));
            }
        }
        performHapticFeedback(1);
        return true;
    }
}
