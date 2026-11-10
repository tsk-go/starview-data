package com.starpilot.starview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import kotlin.text.StringsKt;
import org.capnproto.SlcPeek;

/* compiled from: HudViews.kt */
public final class SlcView extends View {
    public static final Companion Companion = new Companion(null);
    private static final Map<String, String> LABELS = MapsKt.mapOf(TuplesKt.to("dashboard", "DASH"), TuplesKt.to("map", "OSM"), TuplesKt.to("vision", "VISION"), TuplesKt.to("mapbox", "NAV"));
    private final Rect bounds;
    private final Paint p;
    public Telemetry t;
    private final Paint tp;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SlcView(Context ctx, AttributeSet attrs) {
        super(ctx, attrs);
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        this.p = new Paint(1);
        Paint $this$tp_u24lambda_u240 = new Paint(1);
        $this$tp_u24lambda_u240.setTextAlign(Paint.Align.CENTER);
        $this$tp_u24lambda_u240.setTypeface(Typeface.DEFAULT_BOLD);
        this.tp = $this$tp_u24lambda_u240;
        this.bounds = new Rect();
    }

    public /* synthetic */ SlcView(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
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

    /* compiled from: HudViews.kt */
    public static final class Shown {
        private final int arrow;
        private final String heading;
        private final String offset;
        private final boolean override;
        private final boolean pending;
        private final String posted;

        public Shown(String heading, String posted, String offset, int arrow, boolean override, boolean pending) {
            Intrinsics.checkNotNullParameter(heading, "heading");
            Intrinsics.checkNotNullParameter(posted, "posted");
            this.heading = heading;
            this.posted = posted;
            this.offset = offset;
            this.arrow = arrow;
            this.override = override;
            this.pending = pending;
        }

        public final int getArrow() {
            return this.arrow;
        }

        public final String getHeading() {
            return this.heading;
        }

        public final String getOffset() {
            return this.offset;
        }

        public final boolean getOverride() {
            return this.override;
        }

        public final boolean getPending() {
            return this.pending;
        }

        public final String getPosted() {
            return this.posted;
        }
    }

    /* compiled from: HudViews.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX WARN: Removed duplicated region for block: B:28:0x0071  */
        /* JADX WARN: Removed duplicated region for block: B:40:0x0093  */
        /* JADX WARN: Removed duplicated region for block: B:56:0x00ba  */
        /* JADX WARN: Removed duplicated region for block: B:59:0x00da  */
        /* JADX WARN: Removed duplicated region for block: B:63:0x00bd  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Shown shown(SlcPeek.Slc s, boolean metric, float vEgo) {
            float adjusted;
            int adj;
            boolean pending;
            int arrow;
            String str;
            Intrinsics.checkNotNullParameter(s, "s");
            float k = metric ? 3.6f : 2.2369363f;
            boolean valid = Intrinsics.areEqual(s.kind, "valid");
            float posted = valid ? s.speedMps : s.hasAccepted ? s.acceptedMps : Float.NaN;
            int i = 0;
            String str2 = null;
            if (!((Float.isInfinite(posted) || Float.isNaN(posted)) ? false : true) || posted <= 0.0f) {
                return null;
            }
            if (valid) {
                float f = s.adjustedMps;
                if (((Float.isInfinite(f) || Float.isNaN(f)) ? false : true) && s.adjustedMps > 0.0f) {
                    adjusted = s.adjustedMps;
                    adj = MathKt.roundToInt(adjusted * k) - MathKt.roundToInt(posted * k);
                    if (valid) {
                        float f2 = s.pendingMps;
                        if (((Float.isInfinite(f2) || Float.isNaN(f2)) ? false : true) && s.pendingMps > 0.0f && !s.autoAdoptionPending) {
                            pending = true;
                            if (pending) {
                                if ((Float.isInfinite(adjusted) || Float.isNaN(adjusted)) ? false : true) {
                                    if (!(adjusted == vEgo)) {
                                        if (adjusted <= vEgo) {
                                            arrow = 1;
                                            if (valid) {
                                                str = "LAST";
                                            } else {
                                                str = (String) SlcView.LABELS.get(s.source);
                                                if (str == null) {
                                                    str = "SPEED";
                                                }
                                            }
                                            String str3 = str;
                                            String valueOf = String.valueOf(MathKt.roundToInt(posted * k));
                                            if (adj != 0) {
                                                str2 = String.format("%+d", Arrays.copyOf(new Object[]{Integer.valueOf(adj)}, 1));
                                                Intrinsics.checkNotNullExpressionValue(str2, "format(...)");
                                            }
                                            return new Shown(str3, valueOf, str2, arrow, s.driverOverride, pending);
                                        }
                                        i = -1;
                                    }
                                }
                            }
                            arrow = i;
                            if (valid) {
                            }
                            String str32 = str;
                            String valueOf2 = String.valueOf(MathKt.roundToInt(posted * k));
                            if (adj != 0) {
                            }
                            return new Shown(str32, valueOf2, str2, arrow, s.driverOverride, pending);
                        }
                    }
                    pending = false;
                    if (pending) {
                    }
                    arrow = i;
                    if (valid) {
                    }
                    String str322 = str;
                    String valueOf22 = String.valueOf(MathKt.roundToInt(posted * k));
                    if (adj != 0) {
                    }
                    return new Shown(str322, valueOf22, str2, arrow, s.driverOverride, pending);
                }
            }
            adjusted = s.offsetMps + posted;
            adj = MathKt.roundToInt(adjusted * k) - MathKt.roundToInt(posted * k);
            if (valid) {
            }
            pending = false;
            if (pending) {
            }
            arrow = i;
            if (valid) {
            }
            String str3222 = str;
            String valueOf222 = String.valueOf(MathKt.roundToInt(posted * k));
            if (adj != 0) {
            }
            return new Shown(str3222, valueOf222, str2, arrow, s.driverOverride, pending);
        }
    }

    private final float toDisplay(float ms) {
        return (getT().isMetric() ? 3.6f : 2.23694f) * ms;
    }

    @Override // android.view.View
    protected void onDraw(Canvas c) {
        String take;
        Shown shown;
        float rr;
        float dp;
        float dp2;
        float dp3;
        float dp4;
        float dp5;
        RectF numBox;
        float dp6;
        float i;
        float dp7;
        float dp8;
        Intrinsics.checkNotNullParameter(c, "c");
        if (this.t == null) {
            return;
        }
        String str = null;
        SlcPeek.Slc live = Telemetry.slcLive$default(getT(), 0L, 1, null);
        if (live != null) {
            shown = Companion.shown(live, getT().isMetric(), getT().getVEgo());
            if (shown == null) {
                return;
            }
        } else {
            boolean pending = getT().getSlcChanged() && getT().getSlcUnconfirmed() > 0.0f;
            Telemetry t = getT();
            float v = pending ? t.getSlcUnconfirmed() : t.getSlcSpeedLimit();
            if (v <= 0.0f) {
                return;
            }
            int off = MathKt.roundToInt(toDisplay(getT().getSlcOffset()));
            if (pending) {
                take = "PENDING";
            } else {
                String slcSource = getT().getSlcSource();
                if (slcSource.length() == 0) {
                    slcSource = "LIMIT";
                }
                String upperCase = slcSource.toUpperCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue(upperCase, "toUpperCase(...)");
                take = StringsKt.take(upperCase, 6);
            }
            String str2 = take;
            String valueOf = String.valueOf(MathKt.roundToInt(toDisplay(v)));
            if (off != 0 && !pending) {
                str = String.format("%+d", Arrays.copyOf(new Object[]{Integer.valueOf(off)}, 1));
                Intrinsics.checkNotNullExpressionValue(str, "format(...)");
            }
            shown = new Shown(str2, valueOf, str, 0, false, pending);
        }
        Shown sh = shown;
        float w = getWidth();
        float h = getHeight();
        boolean blinkOn = (System.nanoTime() / 500000000) % ((long) 2) == 0;
        if (!getT().s("vienna")) {
            rr = HudViewsKt.dp(this, 14.0f);
            this.p.setStyle(Paint.Style.FILL);
            this.p.setColor(Color.argb(166, 0, 0, 0));
            c.drawRoundRect(new RectF(0.0f, 0.0f, w, h), rr, rr, this.p);
            this.p.setStyle(Paint.Style.STROKE);
            Paint paint = this.p;
            dp = HudViewsKt.dp(this, 3.0f);
            paint.setStrokeWidth(dp);
            this.p.setColor(sh.getPending() ? blinkOn ? -1 : Color.rgb(201, 34, 49) : Color.argb(180, 196, 205, 208));
            dp2 = HudViewsKt.dp(this, 1.5f);
            dp3 = HudViewsKt.dp(this, 1.5f);
            dp4 = HudViewsKt.dp(this, 1.5f);
            dp5 = HudViewsKt.dp(this, 1.5f);
            c.drawRoundRect(new RectF(dp2, dp3, w - dp4, h - dp5), rr, rr, this.p);
            this.tp.setColor(-1);
            this.tp.setTextSize(0.16f * h);
            float hw = this.tp.measureText(sh.getHeading());
            float aw = sh.getArrow() != 0 ? h * 0.12f : 0.0f;
            float hy = (sh.getOffset() != null ? 0.2f : 0.24f) * h;
            float f = 2;
            c.drawText(sh.getHeading(), (w / f) - (aw / f), hy, this.tp);
            if (sh.getArrow() != 0) {
                float ax = (w / f) + (hw / f) + (0.1f * aw);
                float ay = hy - (this.tp.getTextSize() * 0.36f);
                float a = 0.45f * aw;
                Path $this$onDraw_u24lambda_u242 = new Path();
                if (sh.getArrow() < 0) {
                    $this$onDraw_u24lambda_u242.moveTo(ax, ay + a);
                    $this$onDraw_u24lambda_u242.lineTo(ax + a, ay - (0.3f * a));
                    $this$onDraw_u24lambda_u242.lineTo((f * a) + ax, ay + a);
                } else {
                    $this$onDraw_u24lambda_u242.moveTo(ax, ay - a);
                    $this$onDraw_u24lambda_u242.lineTo(ax + a, ay + (0.3f * a));
                    $this$onDraw_u24lambda_u242.lineTo((f * a) + ax, ay - a);
                }
                this.p.setStyle(Paint.Style.STROKE);
                Paint paint2 = this.p;
                dp6 = HudViewsKt.dp(this, 2.0f);
                paint2.setStrokeWidth(dp6);
                this.p.setColor(-1);
                c.drawPath($this$onDraw_u24lambda_u242, this.p);
            }
            this.tp.setTextSize(0.42f * h);
            float ny = (sh.getOffset() != null ? 0.6f : 0.72f) * h;
            c.drawText(sh.getPosted(), w / f, ny, this.tp);
            numBox = new RectF((w / f) - (this.tp.measureText(sh.getPosted()) / f), ny - (this.tp.getTextSize() * 0.72f), (w / f) + (this.tp.measureText(sh.getPosted()) / f), ny);
            if (sh.getOffset() != null) {
                this.tp.setTextSize(0.17f * h);
                this.tp.setColor(Color.rgb(223, 232, 227));
                c.drawText(sh.getOffset(), w / f, 0.86f * h, this.tp);
            }
        } else {
            float f2 = 2;
            float r = Math.min(w, h) / f2;
            this.p.setStyle(Paint.Style.FILL);
            this.p.setColor(-1);
            c.drawCircle(w / f2, h / f2, r, this.p);
            this.p.setStyle(Paint.Style.STROKE);
            this.p.setStrokeWidth(0.23f * r);
            this.p.setColor((!sh.getPending() || blinkOn) ? Color.rgb(201, 34, 49) : -1);
            c.drawCircle(w / f2, h / f2, r - (this.p.getStrokeWidth() / f2), this.p);
            this.tp.setColor(-16777216);
            this.tp.setTextSize((sh.getPosted().length() <= 2 ? 0.8f : 0.68f) * r * (sh.getOffset() != null ? 0.85f : 1.0f));
            float ny2 = ((h / f2) + (this.tp.getTextSize() * 0.36f)) - (sh.getOffset() != null ? 0.12f * r : 0.0f);
            c.drawText(sh.getPosted(), w / f2, ny2, this.tp);
            numBox = new RectF((w / f2) - (this.tp.measureText(sh.getPosted()) / f2), ny2 - (this.tp.getTextSize() * 0.72f), (w / f2) + (this.tp.measureText(sh.getPosted()) / f2), ny2);
            if (sh.getOffset() != null) {
                this.tp.setTextSize(r * 0.3f);
                c.drawText(sh.getOffset(), w / f2, ny2 + (0.36f * r), this.tp);
            }
        }
        RectF numBox2 = numBox;
        if (sh.getOverride()) {
            i = HudViewsKt.dp(this, 3.0f);
            dp7 = HudViewsKt.dp(this, 4.5f);
            dp8 = HudViewsKt.dp(this, 2.5f);
            for (Pair pair : CollectionsKt.listOf((Object[]) new Pair[]{TuplesKt.to(Float.valueOf(dp7), Integer.valueOf(Color.argb(220, 6, 14, 10))), TuplesKt.to(Float.valueOf(dp8), Integer.valueOf(Color.argb(240, 197, 163, 255)))})) {
                float sw = ((Number) pair.component1()).floatValue();
                int col = ((Number) pair.component2()).intValue();
                this.p.setStyle(Paint.Style.STROKE);
                this.p.setStrokeWidth(sw);
                this.p.setColor(col);
                this.p.setStrokeCap(Paint.Cap.ROUND);
                c.drawLine(numBox2.left + i, numBox2.top + i, numBox2.right - i, numBox2.bottom - i, this.p);
                c.drawLine(numBox2.right - i, numBox2.top + i, numBox2.left + i, numBox2.bottom - i, this.p);
            }
            this.p.setStrokeCap(Paint.Cap.BUTT);
        }
    }
}
