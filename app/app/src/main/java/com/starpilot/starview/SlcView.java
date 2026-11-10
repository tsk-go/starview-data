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
import android.graphics.Paint.Align;
import org.capnproto.SlcPeek.Slc;

/* compiled from: HudViews.kt */
public final class SlcView extends View {
    public static final Companion Companion = new Companion(null);
    private static final Map<String, String> LABELS = MapsKt.mapOf(TuplesKt.to("dashboard", "DASH"), TuplesKt.to("map", "OSM"), TuplesKt.to("vision", "VISION"), TuplesKt.to("mapbox", "NAV"));
    private final Rect bounds;
    private final Paint p;
    public Telemetry t;
    private final Paint tp;

    /* VF: from Vineflower (jadx: ) */
    public SlcView(Context var1, AttributeSet var2) {
        super(var1, var2);
        Intrinsics.checkNotNullParameter(var1, "ctx");
        this.p = new Paint(1);
        Paint var3 = new Paint(1);
        var3.setTextAlign(android.graphics.Paint.Align.CENTER);
        var3.setTypeface(Typeface.DEFAULT_BOLD);
        this.tp = var3;
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

        /* VF: from Vineflower (jadx: Removed duplicated region;incorrect) */
        public final SlcView.Shown shown(Slc var1, boolean var2, float var3) {
            Intrinsics.checkNotNullParameter(var1, "s");
            float var5;
            if (var2) {
                var5 = 3.6F;
            } else {
                var5 = 2.2369363F;
            }

            boolean var12 = Intrinsics.areEqual(var1.kind, "valid");
            float var4;
            if (var12) {
                var4 = var1.speedMps;
            } else if (var1.hasAccepted) {
                var4 = var1.acceptedMps;
            } else {
                var4 = Float.NaN;
            }

            var2 = Float.isInfinite(var4);
            byte var10 = 0;
            int var8;
            if (!var2 && !Float.isNaN(var4)) {
                var8 = 1;
            } else {
                var8 = 0;
            }

            String var14 = null;
            if (var8 && !(var4 <= 0.0F)) {
                float var18;
                label101: {
                    if (var12) {
                        var18 = var1.adjustedMps;
                        if (!Float.isInfinite(var18) && !Float.isNaN(var18)) {
                            var8 = (((boolean)true) ? 1 : 0);
                        } else {
                            var8 = (((boolean)false) ? 1 : 0);
                        }

                        if (var8 && var1.adjustedMps > 0.0F) {
                            var18 = var1.adjustedMps;
                            break label101;
                        }
                    }

                    var18 = var1.offsetMps + var4;
                }

                int var11;
                label90: {
                    var11 = MathKt.roundToInt(var18 * var5) - MathKt.roundToInt(var4 * var5);
                    if (var12) {
                        float var7 = var1.pendingMps;
                        if (!Float.isInfinite(var7) && !Float.isNaN(var7)) {
                            var8 = (((boolean)true) ? 1 : 0);
                        } else {
                            var8 = (((boolean)false) ? 1 : 0);
                        }

                        if (var8 && var1.pendingMps > 0.0F && !var1.autoAdoptionPending) {
                            var2 = true;
                            break label90;
                        }
                    }

                    var2 = false;
                }

                var8 = var10;
                if (var2) {
                    boolean var9;
                    if (!Float.isInfinite(var18) && !Float.isNaN(var18)) {
                        var9 = true;
                    } else {
                        var9 = false;
                    }

                    var8 = var10;
                    if (var9) {
                        if (var18 == var3) {
                            var9 = true;
                        } else {
                            var9 = false;
                        }

                        var8 = var10;
                        if (!var9) {
                            if (var18 > var3) {
                                var8 = -1;
                            } else {
                                var8 = 1;
                            }
                        }
                    }
                }

                String var13;
                if (!var12) {
                    var13 = "LAST";
                } else {
                    String var15 = SlcView.LABELS.get(var1.source);
                    var13 = var15;
                    if (var15 == null) {
                        var13 = "SPEED";
                    }
                }

                int var23 = MathKt.roundToInt(var4 * var5);
                if (var11 != 0) {
                    var14 = String.format("%+d", Arrays.copyOf(new Object[]{var11}, 1));
                    Intrinsics.checkNotNullExpressionValue(var14, "format(...)");
                }

                return new SlcView.Shown(var13, String.valueOf(var23), var14, (((var8 != 0)) ? 1 : 0), var1.driverOverride, var2);
            } else {
                return null;
            }
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
