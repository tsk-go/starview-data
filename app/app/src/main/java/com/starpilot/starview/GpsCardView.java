package com.starpilot.starview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.starpilot.starview.GpsSummary;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;

/* compiled from: GpsCardView.kt */
public final class GpsCardView extends View {
    private final Paint bg;
    private final Paint big;
    private final SimpleDateFormat clock;
    private final float d;
    private float downX;
    private float downY;
    private final Paint fill;
    private final Paint label;
    private final Paint line;
    private final Paint num;
    private final Paint panel;
    private final Path path;
    private final RectF r;
    private final Paint small;
    private final Paint status;
    public Telemetry t;
    private final Paint tag;
    private final Paint value;
    public static final Companion Companion = new Companion(null);
    private static final int CYAN = Color.rgb(0, 238, 255);

    /* VF: from Vineflower (jadx: ) */
    public GpsCardView(Context var1, AttributeSet var2) {
        super(var1, var2);
        Intrinsics.checkNotNullParameter(var1, "ctx");
        this.d = this.getResources().getDisplayMetrics().density;
        Paint var3 = new Paint(1);
        var3.setColor(Color.argb(205, 0, 0, 0));
        this.bg = var3;
        Paint var4 = new Paint(1);
        var4.setColor(Color.argb(255, 4, 4, 52));
        this.panel = var4;
        Paint var5 = new Paint(1);
        var5.setStyle(android.graphics.Paint.Style.STROKE);
        var5.setStrokeWidth(this.dp(1.0F));
        var5.setColor(Color.argb(150, 255, 255, 255));
        this.line = var5;
        this.fill = new Paint(1);
        Paint var6 = new Paint(1);
        var6.setColor(-1);
        var6.setTextSize(this.dp(12.0F));
        this.label = var6;
        Paint var7 = new Paint(1);
        var7.setColor(Color.rgb(190, 198, 214));
        var7.setTextSize(this.dp(11.0F));
        this.small = var7;
        Paint var8 = new Paint(1);
        var8.setColor(-1);
        var8.setTextSize(this.dp(22.0F));
        var8.setTypeface(Typeface.DEFAULT_BOLD);
        this.status = var8;
        Paint var9 = new Paint(1);
        var9.setColor(CYAN);
        var9.setTextSize(this.dp(30.0F));
        var9.setTypeface(Typeface.DEFAULT_BOLD);
        var9.setTextAlign(android.graphics.Paint.Align.RIGHT);
        this.big = var9;
        Paint var10 = new Paint(1);
        var10.setColor(-1);
        var10.setTextSize(this.dp(10.0F));
        var10.setTextAlign(android.graphics.Paint.Align.CENTER);
        this.num = var10;
        Paint var11 = new Paint(1);
        var11.setTextSize(this.dp(9.0F));
        var11.setTextAlign(android.graphics.Paint.Align.CENTER);
        var11.setTypeface(Typeface.DEFAULT_BOLD);
        this.tag = var11;
        Paint var12 = new Paint(1);
        var12.setColor(CYAN);
        var12.setTextSize(this.dp(20.0F));
        var12.setTypeface(Typeface.DEFAULT_BOLD);
        var12.setTextAlign(android.graphics.Paint.Align.CENTER);
        this.value = var12;
        this.r = new RectF();
        this.path = new Path();
        this.clock = new SimpleDateFormat("h:mm:ss", Locale.US);
    }

    public /* synthetic */ GpsCardView(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
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

    private final float dp(float v) {
        return this.d * v;
    }

    /* compiled from: GpsCardView.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final int getCYAN() {
            return GpsCardView.CYAN;
        }

        public final int cnoColor(int cno) {
            return cno < 10 ? Color.rgb(255, 32, 32) : cno < 20 ? Color.rgb(255, 128, 0) : cno < 30 ? Color.rgb(255, 238, 0) : cno < 50 ? Color.rgb(170, 255, 0) : Color.rgb(0, 230, 64);
        }

        /* VF: from Vineflower (jadx: ) */
        public final int systemColor(String var1) {
            Intrinsics.checkNotNullParameter(var1, "sys");
            switch (var1.hashCode()) {
                case 67:
                    if (var1.equals("C")) {
                        return Color.rgb(255, 200, 80);
                    }
                    break;
                case 69:
                    if (var1.equals("E")) {
                        return Color.rgb(140, 255, 140);
                    }
                    break;
                case 71:
                    if (var1.equals("G")) {
                        return Color.rgb(120, 200, 255);
                    }
                    break;
                case 74:
                    if (var1.equals("J")) {
                        return Color.rgb(220, 140, 255);
                    }
                    break;
                case 82:
                    if (var1.equals("R")) {
                        return Color.rgb(255, 110, 110);
                    }
            }

            return Color.rgb(200, 200, 200);
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent e) {
        Intrinsics.checkNotNullParameter(e, "e");
        switch (e.getActionMasked()) {
            case 0:
                this.downX = e.getX();
                this.downY = e.getY();
                return true;
            case 1:
                if (Math.abs(e.getX() - this.downX) < dp(12.0f) && Math.abs(e.getY() - this.downY) < dp(12.0f)) {
                    performClick();
                }
                return true;
            default:
                return true;
        }
    }

    @Override // android.view.View
    public boolean performClick() {
        super.performClick();
        try {
            getContext().startActivity(new Intent(getContext(), (Class<?>) SensorsActivity.class));
            return true;
        } catch (Exception e) {
            return true;
        }
    }

    private final String fit(String text, float maxW, Paint p) {
        if (maxW <= 0.0f) {
            return "";
        }
        if (p.measureText(text) <= maxW) {
            return text;
        }
        int n = text.length();
        while (n > 1 && p.measureText(text, 0, n) + p.measureText("…") > maxW) {
            n--;
        }
        String substring = text.substring(0, n);
        Intrinsics.checkNotNullExpressionValue(substring, "substring(...)");
        return StringsKt.trimEnd((CharSequence) substring).toString() + "…";
    }

    /* VF: from Vineflower (jadx: ) */
    protected void onDraw(Canvas var1) {
        Pair var73 = null; // declarations Vineflower left out
        Intrinsics.checkNotNullParameter(var1, "cv");
        if (this.t != null) {
            float var13 = this.getWidth();
            float var7 = this.getHeight();
            GpsSummary.Companion var19 = GpsSummary.Companion;
            Collection var18 = this.getT().getGpsFixes().values();
            Intrinsics.checkNotNullExpressionValue(var18, "<get-values>(...)");
            GpsSummary var21 = var19.of(var18, this.getT().getGnssSats(), this.getT().getGnssAtNs(), this.getT().getStarted(), System.nanoTime());
            boolean var17 = this.getT().isMetric();
            this.r.set(0.0F, 0.0F, var13, var7);
            var1.drawRoundRect(this.r, this.dp(14.0F), this.dp(14.0F), this.bg);
            float var11 = this.dp(10.0F);
            RtkStatus var22 = Ntrip.INSTANCE.getRtk();
            float var4 = var21.getAvgCno();
            int var15;
            if (!Float.isInfinite(var4) && !Float.isNaN(var4)) {
                var15 = 1;
            } else {
                var15 = 0;
            }

            String var79;
            if ((var15 != 0)) {
                String var67 = String.format("%.1f", Arrays.copyOf(new Object[]{var21.getAvgCno()}, 1));
                Intrinsics.checkNotNullExpressionValue(var67, "format(...)");
                var79 = "   avg signal " + var67;
            } else {
                var79 = "";
            }

            var15 = -7829368;
            if (var22 != null) {
                if (var22.getFresh()) {
                    if (Intrinsics.areEqual(var22.getRtk(), "fixed")) {
                        var15 = Color.rgb(0, 230, 64);
                    } else if (Intrinsics.areEqual(var22.getRtk(), "float")) {
                        var15 = Color.rgb(190, 230, 0);
                    } else if (Intrinsics.areEqual(var22.getFixType(), "dead reckoning")) {
                        var15 = Color.rgb(255, 170, 0);
                    } else if (var22.getHasFix()) {
                        var15 = CYAN;
                    } else {
                        var15 = Color.rgb(255, 170, 0);
                    }
                }

                this.fill.setColor(var15);
                float var5 = this.dp(9.0F);
                var4 = this.dp(26.0F);
                String var20 = "";
                var1.drawCircle(var5 + var11, var4, this.dp(8.0F), this.fill);
                String var69;
                if (var22.getHasFix()) {
                    String var68 = RtkStatus.Companion.accuracy(var22.getHAccM(), var17);
                    var69 = "± " + var68;
                } else {
                    var69 = "";
                }

                int var48;
                if (var69.length() > 0) {
                    var48 = 1;
                } else {
                    var48 = 0;
                }

                if ((var48 != 0)) {
                    var1.drawText(var69, var13 - var11, this.dp(37.0F), this.big);
                }

                String var23 = var22.getWords();
                var4 = 2;
                var1.drawText(
                    this.fit(var23, var13 - var4 * var11 - this.dp(32.0F) - this.big.measureText(var69), this.status),
                    this.dp(24.0F) + var11,
                    this.dp(34.0F),
                    this.status
                );
                String var70;
                if (var22.getRtcmAgeS() == null) {
                    if (Intrinsics.areEqual(Ntrip.INSTANCE.getState(), "off")) {
                        var70 = "corrections off";
                    } else {
                        var70 = "no corrections";
                    }
                } else if (var22.getCorrectionAgeS() != null) {
                    Integer var71 = var22.getCorrectionAgeS();
                    var70 = "corrections " + var71 + " s";
                } else {
                    var70 = "corrections not used yet";
                }

                if (var22.getFusion().length() > 0) {
                    var48 = (((boolean)true) ? 1 : 0);
                } else {
                    var48 = (((boolean)false) ? 1 : 0);
                }

                if ((var48 != 0)) {
                    var20 = var22.getFusion();
                    var20 = "   sensors " + var20;
                }

                var48 = var22.getUsed();
                var1.drawText(this.fit("RTK  " + var48 + " sats   " + var70 + var20, var13 - var4 * var11, this.small), var11, this.dp(54.0F), this.small);
                var48 = var21.getInView();
                int var16 = var21.getInUse();
                var1.drawText(
                    this.fit("comma GPS  in view " + var48 + "   in use " + var16 + var79, var13 - var4 * var11, this.small), var11, this.dp(69.0F), this.small
                );
            } else {
                label211: {
                    String var72 = var21.getState();
                    switch (var72.hashCode()) {
                        case 101397:
                            if (var72.equals("fix")) {
                                var15 = Color.rgb(0, 230, 64);
                                String var74;
                                if (var21.getInUse() >= 4) {
                                    var74 = "3D Fix";
                                } else {
                                    var74 = "Fix";
                                }

                                var73 = TuplesKt.to(var15, var74);
                                break label211;
                            }
                            break;
                        case 109935:
                            if (var72.equals("off")) {
                                var73 = TuplesKt.to(-7829368, "GPS off");
                                break label211;
                            }
                            break;
                        case 1778217274:
                            if (var72.equals("searching")) {
                                var73 = TuplesKt.to(Color.rgb(255, 170, 0), "Searching");
                                break label211;
                            }
                    }

                    var73 = TuplesKt.to(-7829368, "No GPS data");
                }

                var15 = ((Number)var73.component1()).intValue();
                String var75 = (String)var73.component2();
                this.fill.setColor(var15);
                var1.drawCircle(this.dp(9.0F) + var11, this.dp(26.0F), this.dp(8.0F), this.fill);
                var1.drawText(var75, this.dp(24.0F) + var11, this.dp(34.0F), this.status);
                var4 = var21.getAccuracyM();
                int var54;
                if (!Float.isInfinite(var4) && !Float.isNaN(var4)) {
                    var54 = 1;
                } else {
                    var54 = 0;
                }

                if ((var54 != 0)) {
                    var4 = var21.getAccuracyM();
                    StringBuilder var76;
                    String var85;
                    if (var17) {
                        var54 = MathKt.roundToInt(var4);
                        var76 = new StringBuilder().append("± ").append(var54);
                        var85 = " m";
                    } else {
                        var54 = MathKt.roundToInt(var4 * 3.28084F);
                        var76 = new StringBuilder().append("± ").append(var54);
                        var85 = " ft";
                    }

                    var1.drawText(var76.append(var85).toString(), var13 - var11, this.dp(37.0F), this.big);
                } else if (Intrinsics.areEqual(var21.getState(), "off")) {
                    this.small.setTextAlign(android.graphics.Paint.Align.RIGHT);
                    var1.drawText("starts when the car is on", var13 - var11, this.dp(32.0F), this.small);
                    this.small.setTextAlign(android.graphics.Paint.Align.LEFT);
                }

                var54 = var21.getInView();
                int var65 = var21.getInUse();
                var1.drawText("In view " + var54 + "   In use " + var65 + var79, var11, this.dp(58.0F), this.small);
            }

            if (var22 != null) {
                var4 = 76.0F;
            } else {
                var4 = 66.0F;
            }

            float var9 = this.dp(var4);
            float var8 = var7 - this.dp(78.0F);
            this.r.set(var11, var9, var13 - var11, var8);
            var1.drawRoundRect(this.r, this.dp(8.0F), this.dp(8.0F), this.panel);
            float var6 = this.dp(16.0F) + var9;
            float var10 = var8 - this.dp(20.0F);
            float var33 = this.dp(19.0F);
            var15 = var21.getBars().size();
            var4 = 2;
            int var66 = Math.min(var15, (int)((var13 - var4 * var11 - this.dp(8.0F)) / var33));
            if (var66 == 0) {
                this.small.setTextAlign(android.graphics.Paint.Align.CENTER);
                String var77;
                if (Intrinsics.areEqual(var21.getState(), "off")) {
                    var77 = "The comma's GPS runs while the car is on";
                } else {
                    var77 = "No satellites reported yet";
                }

                var1.drawText(var77, var13 / var4, (var9 + var8) / var4, this.small);
                this.small.setTextAlign(android.graphics.Paint.Align.LEFT);
            }

            var8 = (var13 - var66 * var33) / var4;

            for (int var59 = 0; var59 < var66; var59++) {
                GnssSat var78 = var21.getBars().get(var59);
                float var14 = var59 * var33 + var8 + var33 / var4;
                float var12 = (var10 - var6) * (RangesKt.coerceIn(var78.getCno(), 0, 50) / 50.0F);
                this.fill.setColor(Companion.cnoColor(var78.getCno()));
                this.r.set(var14 - 0.36F * var33, var10 - var12, var14 + 0.36F * var33, var10);
                var1.drawRect(this.r, this.fill);
                var1.drawText(String.valueOf(var78.getCno()), var14, var10 - var12 - this.dp(3.0F), this.num);
                this.r.set(var14 - 0.47F * var33, this.dp(4.0F) + var10, 0.47F * var33 + var14, this.dp(17.0F) + var10);
                if (var78.getUsed()) {
                    this.fill.setColor(-1);
                    var1.drawRoundRect(this.r, this.dp(3.0F), this.dp(3.0F), this.fill);
                    this.tag.setColor(-16777216);
                } else {
                    var1.drawRoundRect(this.r, this.dp(3.0F), this.dp(3.0F), this.line);
                    this.tag.setColor(-1);
                }

                var1.drawText(var78.getLabel(), var14, this.dp(14.0F) + var10, this.tag);
            }

            if (var21.getBars().size() > var66 && var66 > 0) {
                this.small.setTextAlign(android.graphics.Paint.Align.RIGHT);
                var15 = var21.getBars().size();
                var1.drawText("+" + (var15 - var66) + " more", var13 - var11 - this.dp(6.0F), this.dp(13.0F) + var9, this.small);
                this.small.setTextAlign(android.graphics.Paint.Align.LEFT);
            }

            var8 = var7 - this.dp(70.0F);
            float var44 = this.dp(62.0F);
            float var46 = (var13 - var4 * var11 - this.dp(18.0F)) / 4;

            for (int var61 = 0; var61 < 4; var61++) {
                this.r.set(var61 * (var46 + this.dp(6.0F)) + var11, var8, var61 * (var46 + this.dp(6.0F)) + var11 + var46, var8 + var44);
                var1.drawRoundRect(this.r, this.dp(8.0F), this.dp(8.0F), this.panel);
            }

            var13 = var44 / var4 + var8;
            var6 = Math.min(var46, var44) / var4 - this.dp(4.0F);
            var33 = onDraw$cellX(var11, var46, this, 0);
            var1.drawCircle(var33, var13, var6, this.line);
            var7 = 3;
            var1.drawCircle(var33, var13, var6 * var4 / var7, this.line);
            var1.drawCircle(var33, var13, var6 / var7, this.line);

            for (GnssSat var86 : var21.getSky()) {
                var10 = (90 - var86.getElevation()) * var6 / 90.0F;
                double var2 = Math.toRadians(var86.getAzimuth());
                var9 = (float)(var10 * Math.sin(var2)) + var33;
                var10 = var13 - (float)(var10 * Math.cos(var2));
                this.fill.setColor(Companion.systemColor(var86.getSystem()));
                if (var86.getUsed()) {
                    var1.drawCircle(var9, var10, this.dp(3.2F), this.fill);
                } else {
                    this.line.setColor(this.fill.getColor());
                    var1.drawCircle(var9, var10, this.dp(3.0F), this.line);
                    this.line.setColor(Color.argb(150, 255, 255, 255));
                }
            }

            var33 = onDraw$cellX(var11, var46, this, 1);
            this.small.setTextAlign(android.graphics.Paint.Align.CENTER);
            var1.drawText("N", var33, this.dp(12.0F) + var8, this.small);
            var4 = var21.getBearing();
            int var62;
            if (!Float.isInfinite(var4) && !Float.isNaN(var4)) {
                var62 = 1;
            } else {
                var62 = 0;
            }

            String var87 = "–";
            if ((var62 != 0)) {
                var1.save();
                var1.rotate(var21.getBearing(), var33, this.dp(4.0F) + var13);
                this.path.reset();
                this.path.moveTo(var33, var13 - this.dp(16.0F));
                this.path.lineTo(this.dp(9.0F) + var33, this.dp(18.0F) + var13);
                this.path.lineTo(var33, this.dp(11.0F) + var13);
                this.path.lineTo(var33 - this.dp(9.0F), this.dp(18.0F) + var13);
                this.path.close();
                this.fill.setColor(CYAN);
                var1.drawPath(this.path, this.fill);
                var1.restore();
                var62 = MathKt.roundToInt(var21.getBearing());
                var1.drawText(var62 + "°", var33, var8 + var44 - this.dp(4.0F), this.small);
            } else {
                var1.drawText("–", var33, this.dp(8.0F) + var13, this.value);
            }

            var4 = var21.getSpeed();
            boolean var64;
            if (!Float.isInfinite(var4) && !Float.isNaN(var4)) {
                var64 = true;
            } else {
                var64 = false;
            }

            String var81;
            if (var64) {
                var33 = var21.getSpeed();
                if (var17) {
                    var4 = 3.6F;
                } else {
                    var4 = 2.23694F;
                }

                var81 = String.valueOf(MathKt.roundToInt(var33 * var4));
            } else {
                var81 = "–";
            }

            var1.drawText(var81, onDraw$cellX(var11, var46, this, 2), this.dp(6.0F) + var13, this.value);
            String var82;
            if (var17) {
                var82 = "km/h";
            } else {
                var82 = "mph";
            }

            var1.drawText(var82, onDraw$cellX(var11, var46, this, 2), var8 + var44 - this.dp(5.0F), this.small);
            this.value.setTextSize(this.dp(14.0F));
            String var83 = var87;
            if (var21.getUnixMs() > 0L) {
                var83 = this.clock.format(new Date(var21.getUnixMs()));
            }

            var1.drawText(var83, onDraw$cellX(var11, var46, this, 3), this.dp(5.0F) + var13, this.value);
            this.value.setTextSize(this.dp(20.0F));
            var1.drawText("GPS time", onDraw$cellX(var11, var46, this, 3), var8 + var44 - this.dp(5.0F), this.small);
            this.small.setTextAlign(android.graphics.Paint.Align.LEFT);
        }
    }

    private static final float onDraw$cellX(float pad, float cell, GpsCardView this$0, int k) {
        return (k * (this$0.dp(6.0f) + cell)) + pad + (cell / 2);
    }
}
