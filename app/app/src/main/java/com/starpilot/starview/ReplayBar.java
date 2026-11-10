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
import android.graphics.Paint.Align;

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

    /* VF: from Vineflower (jadx: ) */
    public ReplayBar(Context var1, AttributeSet var2) {
        super(var1, var2);
        Intrinsics.checkNotNullParameter(var1, "ctx");
        this.d = this.getResources().getDisplayMetrics().density;
        Paint var6 = new Paint(1);
        var6.setColor(Color.argb(215, 18, 20, 26));
        this.bg = var6;
        Paint var7 = new Paint(1);
        var7.setColor(Color.rgb(205, 40, 40));
        this.pill = var7;
        Paint var8 = new Paint(1);
        var8.setColor(-1);
        var8.setTextSize(this.d * 15.0F);
        var8.setTypeface(Typeface.DEFAULT_BOLD);
        this.txt = var8;
        Paint var9 = new Paint(1);
        var9.setColor(Color.rgb(200, 205, 215));
        var9.setTextSize(this.d * 13.0F);
        this.small = var9;
        Paint var10 = new Paint(1);
        var10.setColor(-1);
        var10.setTextSize(this.d * 17.0F);
        var10.setTypeface(Typeface.DEFAULT_BOLD);
        var10.setTextAlign(android.graphics.Paint.Align.CENTER);
        this.btn = var10;
        Paint var11 = new Paint(1);
        var11.setColor(Color.argb(70, 255, 255, 255));
        this.btnBg = var11;
        Paint var12 = new Paint(1);
        var12.setColor(Color.argb(80, 255, 255, 255));
        this.track = var12;
        Paint var13 = new Paint(1);
        var13.setColor(Color.rgb(230, 70, 70));
        this.done = var13;
        this.keys = CollectionsKt.listOf(new String[]{"back", "play", "fwd", "speed", "stop"});
        Iterable var16 = this.keys;
        LinkedHashMap var14 = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(var16, 10)), 16));

        for (Object var3 : var16) {
            Map var5 = var14;
            String var4 = (String)var3;
            var5.put(var3, new RectF());
        }

        Map var15 = var14;
        this.rects = var15;
        this.bar = new RectF();
    }

    public /* synthetic */ ReplayBar(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet);
    }

    @Override // android.view.View
    protected void onMeasure(int wSpec, int hSpec) {
        setMeasuredDimension((int) (560 * this.d), (int) (78 * this.d));
    }

    /* VF: from Vineflower (jadx: Removed duplicated region;incorrect) */
    protected void onDraw(Canvas var1) {
        Object var29 = null; // declarations Vineflower left out
        Intrinsics.checkNotNullParameter(var1, "c");
        JSONObject var16 = Replay.INSTANCE.getStatus();
        if (var16 != null) {
            float var11 = this.getWidth();
            float var10 = this.d * 10.0F;
            var1.drawRoundRect(new RectF(0.0F, 0.0F, var11, this.getHeight()), this.d * 16.0F, this.d * 16.0F, this.bg);
            RectF var17 = new RectF(var10, var10, this.d * 78.0F + var10, this.d * 26.0F + var10);
            var1.drawRoundRect(var17, this.d * 13.0F, this.d * 13.0F, this.pill);
            this.txt.setTextAlign(android.graphics.Paint.Align.CENTER);
            var1.drawText("REPLAY", var17.centerX(), var17.centerY() + this.d * 5.0F, this.txt);
            this.txt.setTextAlign(android.graphics.Paint.Align.LEFT);
            double var6 = Replay.position$default(Replay.INSTANCE, 0L, 1, null);
            double var2 = var16.optDouble("dur", 0.0);
            double var4 = var16.optDouble("speed", 1.0);
            String var19 = Replay.INSTANCE.clock(var6);
            String var18 = Replay.INSTANCE.clock(var2);
            boolean var14;
            if (var4 == 1.0) {
                var14 = true;
            } else {
                var14 = false;
            }

            String var23;
            if (!var14) {
                if (var4 % 1.0 == 0.0) {
                    var14 = true;
                } else {
                    var14 = false;
                }

                Object var23o;
                if (var14) {
                    var23o = Integer.valueOf((int)var4);
                } else {
                    var23o = Double.valueOf(var4);
                }

                var23 = "  ·  " + var23o + "x";
            } else {
                var23 = "";
            }

            var1.drawText(var19 + " / " + var18 + var23, var17.right + this.d * 10.0F, var17.centerY() + this.d * 5.0F, this.txt);
            if (var16.optBoolean("loading")) {
                var23 = "loading…";
            } else if (var16.optBoolean("paused")) {
                var23 = "paused";
            } else if (!var16.optBoolean("video")) {
                var23 = "no video in this part";
            } else {
                var23 = "";
            }

            if (var23.length() > 0) {
                var14 = true;
            } else {
                var14 = false;
            }

            if (var14) {
                var1.drawText(var23, var17.right + this.d * 10.0F, var17.bottom + this.d * 16.0F, this.small);
            }

            float var9 = this.d * 46.0F;
            float var12 = this.d;
            Iterator var28 = CollectionsKt.reversed(this.keys).iterator();
            float var8 = var11 - var10 - var9;

            while (var28.hasNext()) {
                float var13;
                label100: {
                    var23 = (String)var28.next();
                    var29 = MapsKt.getValue(this.rects, var23);
                    ((RectF) var29).set(var8, var10, var8 + var9, var10 + var12 * 32.0F);
                    var13 = this.d;
                    var1.drawRoundRect(((RectF) var29), this.d * 10.0F, this.d * 10.0F, this.btnBg);
                    switch (var23.hashCode()) {
                        case 101811:
                            if (var23.equals("fwd")) {
                                var23 = "+30";
                                break label100;
                            }
                            break;
                        case 3015911:
                            if (var23.equals("back")) {
                                var23 = "−10";
                                break label100;
                            }
                            break;
                        case 3443508:
                            if (var23.equals("play")) {
                                if (var16.optBoolean("paused")) {
                                    var23 = "▶";
                                } else {
                                    var23 = "❚❚";
                                }
                                break label100;
                            }
                            break;
                        case 3540994:
                            if (var23.equals("stop")) {
                                var23 = "✕";
                                break label100;
                            }
                    }

                    if (var4 % 1.0 == 0.0) {
                        var14 = true;
                    } else {
                        var14 = false;
                    }

                    Object var23o2;
                    if (var14) {
                        var23o2 = Integer.valueOf((int)var4);
                    } else {
                        var23o2 = Double.valueOf(var4);
                    }

                    var23 = var23o2 + "x";
                }

                var1.drawText(var23, ((RectF) var29).centerX(), ((RectF) var29).centerY() + this.d * 6.0F, this.btn);
                var8 -= var13 * 6.0F + var9;
            }

            this.bar.set(var10, this.getHeight() - var10 - this.d * 8.0F, var11 - var10, this.getHeight() - var10);
            var1.drawRoundRect(this.bar, this.d * 4.0F, this.d * 4.0F, this.track);
            if (var2 > 0.0) {
                var1.drawRoundRect(
                    new RectF(this.bar.left, this.bar.top, this.bar.left + this.bar.width() * RangesKt.coerceIn((float)(var6 / var2), 0.0F, 1.0F), this.bar.bottom),
                    this.d * 4.0F,
                    this.d * 4.0F,
                    this.done
                );
            }

            this.postInvalidateDelayed(250L);
        }
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
                if (optDouble == 1.0d) {
                    d = 2.0d;
                } else if (optDouble == 2.0d) {
                    d = 4.0d;
                } else {
                    d = optDouble == 4.0d ? 0.5d : 1.0d;
                }
                Replay.control$default(replay, "speed", d, null, 4, null);
            } else {
                if (!Intrinsics.areEqual(hit, "stop")) {
                    if (e.getY() >= this.bar.top - (this.d * 14.0f)) {
                        float f = this.bar.left;
                        float f2 = this.bar.right;
                        float x = e.getX();
                        boolean r9 = false;
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
