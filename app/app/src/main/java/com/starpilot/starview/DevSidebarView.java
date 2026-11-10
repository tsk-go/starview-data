package com.starpilot.starview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import java.util.Arrays;
import java.util.List;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import java.util.Iterator;

/* compiled from: BigUiViews.kt */
public final class DevSidebarView extends View {
    private final Paint bg;
    private final Paint box;
    public Telemetry t;
    private final Paint tp;

    /* VF: from Vineflower (jadx: ) */
    public DevSidebarView(Context var1, AttributeSet var2) {
        super(var1, var2);
        Intrinsics.checkNotNullParameter(var1, "ctx");
        Paint var3 = new Paint(1);
        var3.setStyle(android.graphics.Paint.Style.STROKE);
        var3.setColor(Color.argb(85, 255, 255, 255));
        this.box = var3;
        Paint var4 = new Paint(1);
        var4.setColor(Color.argb(200, 0, 0, 0));
        this.bg = var4;
        Paint var5 = new Paint(1);
        var5.setColor(-1);
        var5.setTextAlign(android.graphics.Paint.Align.CENTER);
        var5.setTypeface(Typeface.create(Typeface.DEFAULT, 1));
        this.tp = var5;
    }

    public /* synthetic */ DevSidebarView(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
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

    public static /* synthetic */ List rows$default(DevSidebarView devSidebarView, long j, int i, Object obj) {
        if ((i & 1) != 0) {
            j = System.nanoTime();
        }
        return devSidebarView.rows(j);
    }

    public final List<Pair<String, String>> rows(long nowNs) {
        boolean metric = getT().isMetric();
        float k = metric ? 1.0f : 3.28084f;
        String unit = metric ? "m/s²" : "ft/s²";
        boolean drive = getT().getStarted();
        boolean delay = drive && getT().getDelayAtNs() != 0 && nowNs - getT().getDelayAtNs() < 10000000000L;
        boolean torque = drive && getT().getTorqueAtNs() != 0 && nowNs - getT().getTorqueAtNs() < 10000000000L && getT().getTorqueUseParams();
        Pair[] pairArr = new Pair[7];
        pairArr[0] = TuplesKt.to("ACCEL", rows$shown(drive ? getT().getAEgo() * k : Float.NaN, 2, " " + unit));
        pairArr[1] = TuplesKt.to("MAX ACCEL", rows$shown(drive ? getT().getMaxAccel() * k : Float.NaN, 2, " " + unit));
        String str = delay ? "LEARNED DELAY" : "BASE DELAY";
        Telemetry t = getT();
        pairArr[2] = TuplesKt.to(str, rows$shown$default(delay ? t.getLearnedDelay() : t.getBaseDelay(), 0, null, 6, null));
        String str2 = torque ? "LEARNED FRICTION" : "BASE FRICTION";
        Telemetry t2 = getT();
        pairArr[3] = TuplesKt.to(str2, rows$shown$default(torque ? t2.getFrictionLearned() : t2.getBaseFriction(), 0, null, 6, null));
        String str3 = torque ? "LEARNED LAT ACCEL" : "BASE LAT ACCEL";
        Telemetry t3 = getT();
        pairArr[4] = TuplesKt.to(str3, rows$shown$default(torque ? t3.getLatAccelLearned() : t3.getBaseLatAccel(), 0, null, 6, null));
        pairArr[5] = TuplesKt.to("STEER RATIO", rows$shown$default((!drive || getT().getSteerRatioLive() <= 0.0f) ? Float.NaN : getT().getSteerRatioLive(), 0, null, 6, null));
        pairArr[6] = TuplesKt.to("STEER STIFF", rows$shown$default(drive ? getT().getStiffness() : Float.NaN, 0, null, 6, null));
        return CollectionsKt.listOf((Object[]) pairArr);
    }

    private static final String rows$shown(float v, int dec, String suffix) {
        if (!((Float.isInfinite(v) || Float.isNaN(v)) ? false : true)) {
            return "—";
        }
        String format = String.format("%." + dec + "f", Arrays.copyOf(new Object[]{Float.valueOf(v)}, 1));
        Intrinsics.checkNotNullExpressionValue(format, "format(...)");
        return format + suffix;
    }

    static /* synthetic */ String rows$shown$default(float f, int i, String str, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = 5;
        }
        if ((i2 & 4) != 0) {
            str = "";
        }
        return rows$shown(f, i, str);
    }

    /* VF: from Vineflower (jadx: ) */
    protected void onDraw(Canvas var1) {
        Intrinsics.checkNotNullParameter(var1, "c");
        if (this.t != null) {
            List var12 = rows$default(this, 0L, 1, null);
            float var3 = BigUiViewsKt.dpf(this, 6.0F);
            int var10 = var12.size();
            float var5 = (this.getHeight() - (var10 - 1) * var3) / var10;
            this.box.setStrokeWidth(BigUiViewsKt.dpf(this, 1.5F));
            Iterator var13 = var12.iterator();
            int var8 = 0;

            while (var13.hasNext()) {
                int var11 = var8 + 1;
                Pair var15 = (Pair)var13.next();
                float var6 = var8 * (var5 + var3);
                RectF var14 = new RectF(
                    this.box.getStrokeWidth(),
                    this.box.getStrokeWidth() + var6,
                    this.getWidth() - this.box.getStrokeWidth(),
                    var6 + var5 - this.box.getStrokeWidth()
                );
                float var2 = Math.min(var14.height(), var14.width()) * 0.3F;
                var1.drawRoundRect(var14, var2, var2, this.bg);
                var1.drawRoundRect(var14, var2, var2, this.box);
                Iterator var20 = CollectionsKt.listOf(new String[]{(String)var15.getFirst(), (String)var15.getSecond()}).iterator();
                int var9 = 0;

                while (true) {
                    var10 = var9;
                    if (!var20.hasNext()) {
                        var10 = var10;
                        var8 = var11;
                        break;
                    }

                    var9++;
                    String var16 = (String)var20.next();
                    float var4 = var5 * 0.26F;
                    this.tp.setTextSize(var4);

                    while (this.tp.measureText(var16) > var14.width() - BigUiViewsKt.dpf(this, 10.0F) && var4 > BigUiViewsKt.dpf(this, 8.0F)) {
                        this.tp.setTextSize(--var4);
                    }

                    float var7 = var14.centerX();
                    if (var10 == 0) {
                        var4 = 0.4F;
                    } else {
                        var4 = 0.78F;
                    }

                    var1.drawText(var16, var7, var4 * var5 + var6, this.tp);
                }
            }
        }
    }
}
